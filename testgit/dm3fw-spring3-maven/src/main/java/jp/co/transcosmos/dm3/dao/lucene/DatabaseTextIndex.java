/*
 * Copyright (C) 2006 Transcosmos INC.
 * All Rights Reserved.
 */
package jp.co.transcosmos.dm3.dao.lucene;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import jp.co.transcosmos.dm3.dao.DAOCriteria;
import jp.co.transcosmos.dm3.dao.DAODeleteListener;
import jp.co.transcosmos.dm3.dao.DAOInsertListener;
import jp.co.transcosmos.dm3.dao.DAOUpdateListener;
import jp.co.transcosmos.dm3.dao.HasPkFields;
import jp.co.transcosmos.dm3.dao.HasValueObjectClassName;
import jp.co.transcosmos.dm3.dao.NotEnoughRowsException;
import jp.co.transcosmos.dm3.dao.ReadOnlyDAO;
import jp.co.transcosmos.dm3.dao.ResultFragmentList;
import jp.co.transcosmos.dm3.dao.UpdateExpression;
import jp.co.transcosmos.dm3.utils.ReflectionUtils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.Filter;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.QueryFilter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;

/**
 * Listener object that builds a lucene text index when attached to a ReflectingDAO.
 * This object is described in more detail in the text-index tutorial, but the basic 
 * principle is that whenever the ReflectingDAO inserts/updates/deletes a row, this 
 * object updates a lucene text index. That index is then searchable using the 
 * TextSearchCriteria object as a parameter. 
 * <p>
 * The DatabaseTextIndexFieldDescriptor object can be used to define which fields 
 * are indexed. The Analyzer class is used to control the word breaking when 
 * indexing text blocks (see lucene for more info).
 * 
 * @author <a href="mailto:rick@knowleses.org">Rick Knowles</a>
 * @version $Id: DatabaseTextIndex.java,v 1.7 2012/08/01 09:28:36 tanaka Exp $
 */
public class DatabaseTextIndex<T> implements DAOInsertListener<T>, 
        DAOUpdateListener<T>, DAODeleteListener<T>, HasValueObjectClassName, HasPkFields {
    private static final Log log = LogFactory.getLog(DatabaseTextIndex.class);
    
    private Analyzer analyzer;
    private Directory directory;
    private DatabaseTextIndexFieldDescriptor[] serializedFieldDescriptors;
    private String[] pkFields;
    private Class<? extends T> valueObjectClass;

    public void setDirectory(Directory directory) {
        this.directory = directory;
    }

    public List<String> getPkFields() {
        return this.pkFields == null ? null : Arrays.asList(this.pkFields);
    }

    public void setPkFields(List<String> pkFields) {
        if (pkFields == null) {
            this.pkFields = null;
        } else {
            List<String> out = new ArrayList<String>(); 
            for (Iterator<String> i = pkFields.iterator(); i.hasNext(); ) {
                StringTokenizer st = new StringTokenizer(i.next(), ",");
                while (st.hasMoreTokens()) {
                    out.add(st.nextToken());
                }
            }
            this.pkFields = (String []) out.toArray(new String[out.size()]);
        }
    }

    public void setDirectoryLocation(String filename) {
        File location = new File(filename);
        try {
            location.mkdirs();
            int childCount = location.list().length;
            Directory dir = FSDirectory.getDirectory(location);
            IndexWriter temp = new IndexWriter(dir, this.analyzer, childCount == 0);
            temp.close();
            setDirectory(dir);
        } catch (IOException err) {
            throw new RuntimeException("Error creating directory location: " + 
                    location.getAbsolutePath(), err);
        }
    }

    public String getValueObjectClassName() {
        return this.valueObjectClass == null ? null : this.valueObjectClass.getName();
    }
    
    @SuppressWarnings("unchecked")
    public void setValueObjectClassName(String valueObjectClassName) {
        try {
            this.valueObjectClass = (Class<? extends T>) Class.forName(valueObjectClassName);
        } catch (ClassNotFoundException err) {
            throw new RuntimeException("Error loading DAO class: " + valueObjectClassName, err);
        }
    }

    public Analyzer getAnalyzer() {
        return analyzer;
    }

    public void setAnalyzer(Analyzer analyzer) {
        this.analyzer = analyzer;
    }

    public void setSerializedFieldDescriptor(DatabaseTextIndexFieldDescriptor[] serializedFieldDescriptors) {
        this.serializedFieldDescriptors = serializedFieldDescriptors;
    }

    public void setSerializedFieldNames(String[] serializedFieldNames) {
        DatabaseTextIndexFieldDescriptor fields[] = new DatabaseTextIndexFieldDescriptor[serializedFieldNames.length];
        for (int n = 0; n < serializedFieldNames.length; n++) {
            fields[n] = new DatabaseTextIndexFieldDescriptor(serializedFieldNames[n]);
        }
        this.serializedFieldDescriptors = fields;
    }

    public String[] getAllIndexedFieldNames() {
        String out[] = new String[this.serializedFieldDescriptors.length];
        for (int n = 0; n < this.serializedFieldDescriptors.length; n++) {
            out[n] = this.serializedFieldDescriptors[n].getFieldName();
        }
        return out;
    }
    
    public void postInsert(T[] item) {
        postUpdate(item);
    }

    public void postUpdate(T[] items) {
        if (items == null) {
            return;
        }
        ensureInitialization();
        
        // Get lucene index
        IndexWriter writer = getWriter(false);
        try {
            Term deleteTerms[] = new Term[items.length];
            for (int n = 0; n < items.length; n++) {
                deleteTerms[n] = new Term(PK_TERM, buildInternalPKFieldFromBean(items[n]));
            }
            writer.deleteDocuments(deleteTerms);
            
            for (int n = 0; n < items.length; n++) {
                Document doc = new Document();
                addToDocument(items[n], doc);
                doc.add(new Field(PK_TERM, buildInternalPKFieldFromBean(items[n]),
                        Field.Store.NO, Field.Index.UN_TOKENIZED));
                doc.add(new Field(ALWAYS_TRUE_TERM, ALWAYS_TRUE_VALUE, 
                        Field.Store.NO, Field.Index.UN_TOKENIZED));
                writer.addDocument(doc);
            }
            writer.flush();
        } catch (IOException err) {
            throw new RuntimeException("Error updating lucene index", err);
        } finally {
            if (writer != null) {
                try {writer.close();} catch (IOException err) {}
            }
        }
    }

    public void postDelete(T item) {
        ensureInitialization();
        
        // Get lucene index
        IndexWriter writer = getWriter(false);
        
        // Delete related documents
        try {
            Term pkTerm = new Term(PK_TERM, buildInternalPKFieldFromBean(item));
            if (pkTerm != null) {
                writer.deleteDocuments(pkTerm);
            }
            writer.flush();
        } catch (IOException err) {
            throw new RuntimeException("Error deleting item " + item, err);
        } finally {
            if (writer != null) {
                try {writer.close();} catch (IOException err) {}
            }
        }
    }

    public void postDeleteByFilter(DAOCriteria criteria) {
        throw new RuntimeException("NOT IMPLEMENTED: DatabaseTextIndex.postDeleteByFilter()");
        // One implementation possibility: this could do a query in the preDeleteByFilter method, 
        // store the pks in a thread local (not great), then retrieve the id list to destroy here ?
    }

    public void postDeleteByPK(Object[] pks) {
        ensureInitialization();
        
        // Get lucene index
        IndexWriter writer = getWriter(false);
        
        // Delete related documents
        try {
            Term deleteTerms[] = new Term[pks.length];
            for (int n = 0; n < pks.length; n++) {
                deleteTerms[n] = new Term(PK_TERM, buildInternalPKField(pks[n]));
            }
            writer.deleteDocuments(deleteTerms);
            writer.flush();
        } catch (IOException err) {
            throw new RuntimeException("Error deleting item by pks " + Arrays.asList(pks), err);
        } finally {
            if (writer != null) {
                try {writer.close();} catch (IOException err) {}
            }
        }
    }
    
    protected void addToDocument(T item, Document doc) {
        for (DatabaseTextIndexFieldDescriptor descriptor : serializedFieldDescriptors) {
            Object value = getAttribute(item, descriptor.getFieldName());
            if (value != null) {
                doc.add(new Field(
                        descriptor.getFieldName(), 
                        convertToIndexableForm(value),
                        descriptor.isStored() ? Field.Store.YES : Field.Store.NO, 
                        descriptor.isTokenized() ? Field.Index.TOKENIZED : Field.Index.UN_TOKENIZED));
            }
        }        
    }

    /**
     * Dumps the contents of the index and rebuilds from scratch, using the DAO
     * supplied as the source
     */
    public void rebuildAll(Iterator<T> contents) {
        // Create a new blank indexÅ@to write to
        IndexWriter writer = getWriter(true);
        
        // Select the contents of the 
        try {
            for (; contents.hasNext(); ) {
                Document doc = new Document();
                addToDocument(contents.next(), doc);
                writer.addDocument(doc);
            }
            writer.flush();
        } catch (IOException err) {
            // close writer
            throw new RuntimeException("Error updating text index", err);
        }
    }
    
    public List<T> search(TextSearchCriteria criteria, ReadOnlyDAO<T> dao) {
        // Query the index
        long start = System.currentTimeMillis();
        IndexSearcher searcher = null;
        Hits hits = null;

        try {
            synchronized (this) {
                searcher = new IndexSearcher(this.directory);
            }
            
            if (criteria.getFilterQuery() != null) {
                Filter filter = new QueryFilter(criteria.getFilterQuery());
                if (criteria.getSort() == null) {
                    hits = searcher.search(criteria.getQuery(), filter);
                } else {
                    hits = searcher.search(criteria.getQuery(), filter, criteria.getSort());
                }
            } else if (criteria.getSort() != null) {
                hits = searcher.search(criteria.getQuery(), criteria.getSort());
            } else {
                hits = searcher.search(criteria.getQuery());
            }                
            
            // Iterate through the hits, load the list for each one
            int fragmentStart = Math.max(0, criteria.getFragmentOffset());
            int fragmentEnd = hits.length();
            if (criteria.getFragmentLimit() >= 0) {
                fragmentEnd = Math.max(fragmentEnd, fragmentStart);
            }

            List<T> fragment = null;
            if (fragmentEnd >= fragmentStart) {
                List<Object> fragmentPKs = new ArrayList<Object>();
                for (int n = fragmentStart; n < fragmentEnd; n++) {
                    Document doc = hits.doc(n);
                    String pkTerm = doc.get(PK_TERM);
                    fragmentPKs.add(reconstructPKFromInternal(pkTerm));
                }
                
                fragment = dao.selectByPK(fragmentPKs.toArray());
            } else if (fragmentStart > 0) {
                throw new NotEnoughRowsException("range=" + fragmentStart + "-" + 
                        fragmentEnd + ", rowCount=" + hits.length(), hits.length());
            }        
            
            // Return fragment wrapper
            if ((fragmentStart > 0) || (fragmentEnd < hits.length())) {
                log.info("Query found " + hits.length() + " rows (returned rows " + (fragmentStart + 1) + 
                        "-" + fragmentEnd + " as fragment, iterated in " + 
                        (System.currentTimeMillis() - start) + "ms)");
                return new ResultFragmentList<T>(fragment, fragmentStart, hits.length());
            } else {
                log.info("Query returned " + fragment.size() + " rows, " +
                        "iterated in " + (System.currentTimeMillis() - start) + "ms");
                return fragment;
            }
        } catch (IOException err) {
            throw new RuntimeException("Error during search", err);
        } finally {
            if (searcher != null) {
                try {searcher.close();} catch (IOException err) {}
            }
        }
    }
    
    protected IndexWriter getWriter(boolean clear) {
        Directory dir = null;
        Analyzer analyzer = null;
        synchronized (this) {
            dir = this.directory;
            analyzer = this.analyzer;
        }
        try {
            return new IndexWriter(dir, analyzer, clear);
        } catch (IOException err) {
            throw new RuntimeException("Error opening writer", err);
        }
    }
    
    protected void ensureInitialization() {
        synchronized (this) {
            if (this.directory == null) {
                log.warn("No lucene directory configured: using RAM directory");
                this.directory = new RAMDirectory();
                try {
                    IndexWriter temp = new IndexWriter(this.directory, this.analyzer, true);
                    temp.close();
                } catch (IOException err) {
                    throw new RuntimeException("Error initializing memory index", err);
                }
            }
            if (this.serializedFieldDescriptors == null) {
                String attNames[] = ReflectionUtils.getAllFieldNamesByGetters(this.valueObjectClass);
                this.serializedFieldDescriptors = new DatabaseTextIndexFieldDescriptor[attNames.length]; 
                for (int n = 0; n < attNames.length; n++) {
                    this.serializedFieldDescriptors[n] = new DatabaseTextIndexFieldDescriptor(attNames[n]);
                }
            }
        }
    }
    
    /**
     * Override this method to allow selective filtering of which objects are indexed. Return
     * true if the object is to be considered by the index, false if it is to be ignored.
     */
    protected boolean isIndexed(T item) {
        return true;
    }
    
    ///// In here is pre-operation methods, we don't use these

    public boolean preInsert(T[] item) {
        return true;
    }

    public boolean preUpdate(T[] item) {
        return true;
    }

    public boolean preDelete(T item) {
        return true;
    }

    public boolean preDeleteByFilter(DAOCriteria criteria) {
        return true;
    }

    public boolean preDeleteByPK(Object[] pks) {
        return true;
    }
    
    //////////// 
    
    protected String buildInternalPKFieldFromBean(Object valueobject) {
        try {
            if (this.pkFields.length == 1) {
                Object pkValue = ReflectionUtils.getFieldValueByGetter(valueobject, this.pkFields[0]);
                return (pkValue == null ? null : pkValue.toString()); 
            } else {
                StringBuffer out = new StringBuffer();
                for (int k = 0; k < this.pkFields.length; k++) {
                    Object pkValue = ReflectionUtils.getFieldValueByGetter(
                            valueobject, this.pkFields[k]);
                    out.append("[").append(this.pkFields[k]).append("=").append(pkValue).append("]");
                }
                return out.toString();
            }
        } catch (Throwable err) {
            throw new RuntimeException("Error building internal PK", err);
        }
    }
    
    protected String buildInternalPKField(Object pkValue) {
        if (this.pkFields.length == 1) {
            return (pkValue == null ? null : pkValue.toString()); 
        } else {
            Object pkValues[] = (Object []) pkValue;
            StringBuffer out = new StringBuffer();
            for (int k = 0; k < this.pkFields.length; k++) {
                out.append("[").append(this.pkFields[k]).append("=").append(pkValues[k]).append("]");
            }
            return out.toString();
        }
    }
    
    protected Object reconstructPKFromInternal(String pkTerm) {
        try {
            if (this.pkFields.length == 1) {
                Class<?> pkType = ReflectionUtils.getFieldTypeByGetter(this.valueObjectClass, this.pkFields[0]);
                return convertFromIndexableForm(pkTerm, pkType); 
            } else {
                pkTerm = pkTerm.substring(1) + "["; // trim start brackets
                Object fields[] = new Object[this.pkFields.length];
                int delimPos = pkTerm.indexOf("][");
                while (delimPos >= 0) {
                    String term = pkTerm.substring(0, delimPos);
                    pkTerm = pkTerm.substring(delimPos + 2);
                    
                    String nvPair[] = term.split("=", 2);
                    for (int n = 0; n < this.pkFields.length; n++) {
                        if (pkFields[n].equals(nvPair[0])) {
                            Class<?> pkType = ReflectionUtils.getFieldTypeByGetter(
                                    this.valueObjectClass, this.pkFields[n]);
                            fields[n] = convertFromIndexableForm(nvPair[1], pkType);
                        }
                    }
                }
                return fields;
            }
        } catch (NoSuchMethodException err) {
            throw new RuntimeException("Error re-building PK", err);
        }
    }
    
    protected Object convertFromIndexableForm(String value, Class<?> type) {
        if ((value == null) || (value.length() == 0)) {
            return null;
        } else if (String.class.isAssignableFrom(type)) {
            return value;
        } else if (Boolean.class.isAssignableFrom(type) || type.equals(Boolean.TYPE)) {
            return Boolean.valueOf(value);
        } else if (Integer.class.isAssignableFrom(type) || type.equals(Integer.TYPE)) {
            return Integer.valueOf(value);
        } else if (Long.class.isAssignableFrom(type) || type.equals(Long.TYPE)) {
            return Long.valueOf(value);
        } else if (Double.class.isAssignableFrom(type) || type.equals(Double.TYPE)) {
            return Double.valueOf(value);
        } else if (Float.class.isAssignableFrom(type) || type.equals(Float.TYPE)) {
            return Float.valueOf(value);
        } else if (Date.class.isAssignableFrom(type)) {
            try {
                synchronized (DF) {return DF.parse(value);}
            } catch (ParseException err) {
                throw new RuntimeException("Error parsing stored value: " + value + 
                        " type: " + type.getName(), err);
            } 
        } else {
            throw new RuntimeException("Can't restore value " + value + " of type " + type.getName());
        }
    }
    protected Object getAttribute(T item, String fieldName) {
        try {
            return ReflectionUtils.getFieldValueByGetter(item, fieldName);
        } catch (Throwable err) {
            throw new RuntimeException("Error getting valueobject field " + 
                    fieldName, err);
        }        
    }
    
    protected String convertToIndexableForm(Object value) {
        if (value == null) {
            return null;
        } else if (value instanceof Date) {
            synchronized (DF) {
                return DF.format((Date) value);
            }
        } else {
            return value.toString();
        }
    }
    
    public void postUpdateByCriteria(DAOCriteria criteria, UpdateExpression[] setClauses) {
        log.warn("WARNING: updated ignored - updateByCriteria() not yet implemented");
    }

    public boolean preUpdateByCriteria(DAOCriteria criteria, UpdateExpression[] setClauses) {
        log.warn("WARNING: updated ignored - updateByCriteria() not yet implemented");
        return true;
    }
    
    protected static final DateFormat DF = new SimpleDateFormat("yyyyMMddHHmmssSSS");
    protected static final String PK_TERM = "tcdm3-spring2.DatabaseTextIndex.InternalPK";
    protected static final String ALWAYS_TRUE_TERM = "tcdm3-spring2.DatabaseTextIndex.AlwaysTrue";
    protected static final String ALWAYS_TRUE_VALUE = "1";
    
}
