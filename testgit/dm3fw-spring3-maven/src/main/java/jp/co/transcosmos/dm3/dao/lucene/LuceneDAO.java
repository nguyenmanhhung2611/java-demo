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

import jp.co.transcosmos.dm3.dao.DAO;
import jp.co.transcosmos.dm3.dao.DAOCriteria;
import jp.co.transcosmos.dm3.dao.DAOCriteriaOrderByClause;
import jp.co.transcosmos.dm3.dao.DAOCriteriaWhereClause;
import jp.co.transcosmos.dm3.dao.OrCriteria;
import jp.co.transcosmos.dm3.dao.UpdateExpression;
import jp.co.transcosmos.dm3.utils.ReflectionUtils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.SimpleAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.RangeQuery;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.WildcardQuery;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.Lock;
import org.apache.lucene.store.RAMDirectory;

/**
 * A Lucene implementation of the DAO interface, so we can store local DB mirrors 
 * if required. Not transactional.
 * <p>
 * This implementation is very simple, and intended for cases where high speed volatile
 * data-access is required (e.g. an optimised read-only mirror of some database content)
 * 
 * @author <a href="mailto:rick@knowleses.org">Rick Knowles</a>
 * @version $Id: LuceneDAO.java,v 1.5 2007/11/30 03:32:25 abe Exp $
 */
public class LuceneDAO<E> implements DAO<E> {
    private static final Log log = LogFactory.getLog(LuceneDAO.class);

    protected Directory directory;
    protected Analyzer analyzer;
    private IndexReader openReader;
    private boolean readerNeedsReset;
    private int readerUsageCount;
    
    protected Class<? extends E> valueObjectClass;
    protected String pkFields[];
    protected String serializedFields[];
    protected Class<?> serializedFieldTypes[];

    @SuppressWarnings("unchecked")
    public void setValueObjectClassName(String valueObjectClassName) {
        try {
            this.valueObjectClass = (Class<? extends E>) Class.forName(valueObjectClassName);
        } catch (ClassNotFoundException err) {
            throw new RuntimeException("Error: Valueobject class not found - " + 
                    valueObjectClassName, err);
        }
    }

    public void setDirectory(Directory directory) {
        this.directory = directory;
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

    public void setAnalyzer(Analyzer analyzer) {
        this.analyzer = analyzer;
    }

    public void setSerializedFields(String[] serializedFields) {
        this.serializedFields = serializedFields;
    }

    public void setSerializedFields(List<String> serializedFields) {
        this.serializedFields = serializedFields.toArray(
                new String[serializedFields.size()]);
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

    public void setPkField(String pkField) {
        setPkFields(Arrays.asList(new String[] {pkField}));
    }
   
    protected void ensureInitialization() {
        synchronized (this) {
            if (this.valueObjectClass == null) {
                throw new RuntimeException("Error: No valueObjectClassName declared for LuceneDAO");
            }
            if (this.analyzer == null) {
                setAnalyzer(new SimpleAnalyzer());
            }
            if (this.directory == null) {
                Directory ram = new RAMDirectory();
                try {
                    IndexWriter temp = new IndexWriter(ram, this.analyzer, true);
                    temp.close();
                } catch(IOException err) {}
                setDirectory(ram);
            }
            if (this.serializedFields == null) {
                this.serializedFields = ReflectionUtils.getAllFieldNamesByGetters(
                        this.valueObjectClass);
                if (this.serializedFields.length == 0) {
                    throw new RuntimeException("Error: No fields detected for " +
                            "valueobject class: " + this.valueObjectClass);
                }
                log.debug("Initializing LuceneDAO fields for " + 
                        this.valueObjectClass + " to " + 
                        Arrays.asList(this.serializedFields));
            }
            if (this.serializedFieldTypes == null) {
                this.serializedFieldTypes = new Class[this.serializedFields.length];
                for (int n = 0; n < this.serializedFieldTypes.length; n++) {
                    try {
                        this.serializedFieldTypes[n] = ReflectionUtils.getFieldTypeByGetter(
                                this.valueObjectClass, this.serializedFields[n]);
                    } catch (NoSuchMethodException err) {
                        throw new RuntimeException("Error getting field type", err);
                    }
                }
            }
            if (this.pkFields == null) {
                for (int n = 0; (n < this.serializedFields.length) && 
                                (this.pkFields == null); n++) {
                    if (this.serializedFields[n].equalsIgnoreCase("id")) {
                        this.pkFields = new String[] {this.serializedFields[n]}; 
                    }
                }
                if (this.pkFields == null) {
                    this.pkFields = new String[] {this.serializedFields[0]};
                }
                log.debug("Initializing LuceneDAO PK fields for " + 
                        this.valueObjectClass.getName() + " to " + 
                        Arrays.asList(this.pkFields));
            }
        }
    }

    public int getRowCountMatchingFilter(DAOCriteria criteria) {
        ensureInitialization();
        
        // Build the query from DAOCriteria
        Query query = parseCriteriaIntoQuery(criteria);

        boolean lockedReader = false;
        IndexSearcher searcher = null;

        try {
            // Step 1: if cached reader exists and needs a reset, wait then close it and clear flag
            synchronized (this) {
                if (this.openReader != null) {
                    if (this.readerNeedsReset) {
                        this.readerNeedsReset = false;
                        
                        // Wait for a break in usage, then close the index
                        while (this.readerUsageCount > 0) {
                            log.warn("Sleeping: usage counter=" + this.readerUsageCount);
                            try {Thread.sleep(100);} catch (InterruptedException err) {}
                        }
                        IndexReader temp = this.openReader;
                        this.openReader = null;
                        try {temp.close();} catch (IOException err) {}
                    }
                }
                
                // Step 2: if no cached reader, create one
                if (this.openReader == null) {
                    this.openReader = IndexReader.open(this.directory);
                }
                
                // lock reader for closing
                lockedReader = true;
                this.readerUsageCount++;
            }
            
            // Step 3: use reader - do the search
            searcher = new IndexSearcher(this.openReader);
            return searcher.search(query).length();
        } catch (IOException err) {
            throw new RuntimeException("Error during PK search", err);
        } finally {
            if (searcher != null) {
                try {searcher.close();} catch (IOException err) {}
            }
            
            // Step 4: unlock this reader for closing - note: don't sync here, because earlier
            // sync block is waiting for this call to finish
            if (lockedReader) {
                this.readerUsageCount--;
            }
        }
    }

    public List<E> selectByFilter(DAOCriteria criteria) {
        ensureInitialization();
        
        // Build the query from DAOCriteria
        Query query = parseCriteriaIntoQuery(criteria);
        Sort sort = parseCriteriaIntoSort(criteria);

        boolean lockedReader = false;
        IndexSearcher searcher = null;
        Hits hits = null;
        try {
            try {
                // Step 1: if cached reader exists and needs a reset, wait then close it and clear flag
                synchronized (this) {
                    if (this.openReader != null) {
                        if (this.readerNeedsReset) {
                            this.readerNeedsReset = false;
                            
                            // Wait for a break in usage, then close the index
                            while (this.readerUsageCount > 0) {
                                log.warn("Sleeping: usage counter=" + this.readerUsageCount);
                                try {Thread.sleep(100);} catch (InterruptedException err) {}
                            }
                            IndexReader temp = this.openReader;
                            this.openReader = null;
                            try {temp.close();} catch (IOException err) {}
                        }
                    }
                    
                    // Step 2: if no cached reader, create one
                    if (this.openReader == null) {
                        this.openReader = IndexReader.open(this.directory);
                    }
                    
                    // lock reader for closing
                    lockedReader = true;
                    this.readerUsageCount++;
                }
                
                // Step 3: use reader - do the search
                searcher = new IndexSearcher(this.openReader);
                hits = (sort != null) ? searcher.search(query, sort) : searcher.search(query);
                searcher.close();
                searcher = null;
            } finally {
                // Step 4: unlock this reader for closing - note: don't sync here, because earlier
                // sync block is waiting for this call to finish
                if (lockedReader) {
                    this.readerUsageCount--;
                }
            }

            List<E> results = new ArrayList<E>();
            for (int n = 0; n < hits.length(); n++) {
                E vo = (E) this.valueObjectClass.newInstance();
                deserializeValueObject(hits.doc(n), vo);
                results.add(vo);
            }
            return results;
        } catch (IOException err) {
            throw new RuntimeException("Error during PK search", err);
        } catch (IllegalAccessException err) {
            throw new RuntimeException("Error during result population", err);
        } catch (InstantiationException err) {
            throw new RuntimeException("Error during result population", err);
        } finally {
            if (searcher != null) {
                try {searcher.close();} catch (IOException err) {}
            }
        }
    }

    protected Query parseCriteriaIntoQuery(DAOCriteria criteria) {
        if (criteria != null) {
            BooleanQuery query = new BooleanQuery();
            query.setMinimumNumberShouldMatch(1);
            BooleanClause.Occur join = criteria instanceof OrCriteria ? 
                    BooleanClause.Occur.SHOULD : BooleanClause.Occur.MUST;
            for (int n = 0; n < criteria.getWhereClauseCount(); n++) {
                query.add(evaluateOneWhereClause(criteria.getWhereClause(n)), join);
            }
            for (int n = 0; n < criteria.getSubCriteriaCount(); n++) {
                query.add(parseCriteriaIntoQuery(criteria.getSubCriteria(n)), join);
            }
            return query;
        } else {
            return new TermQuery(new Term(ALWAYS_TRUE, "true"));
        }
    }

    protected Query evaluateOneWhereClause(DAOCriteriaWhereClause where) {
        int operator = where.getOperator();
        Query query = null;
        
        if (operator == DAOCriteria.ALWAYS_TRUE) {
            query = new TermQuery(new Term(ALWAYS_TRUE, "true"));
        } else if (operator == DAOCriteria.IS_NULL) {
            query = new TermQuery(new Term(where.getFieldName() + NULL_EXTENSION, "true"));
        } else if (operator == DAOCriteria.EQUALS) {
            query = new TermQuery(new Term(where.getFieldName(), convertToIndexableForm(where.getValue())));
        } else if (operator == DAOCriteria.LIKE) {
            query = new WildcardQuery(new Term(where.getFieldName(), 
                    convertToIndexableForm(where.getValue()).replace('%', '*')));
        } else if (operator == DAOCriteria.GREATER_THAN) {
            query = new RangeQuery(new Term(where.getFieldName(), 
                    convertToIndexableForm(where.getValue())), null, false);
        } else if (operator == DAOCriteria.GREATER_THAN_EQUALS) {
            query = new RangeQuery(new Term(where.getFieldName(), 
                    convertToIndexableForm(where.getValue())), null, true);
        } else if (operator == DAOCriteria.LESS_THAN) {
            query = new RangeQuery(null, new Term(where.getFieldName(), 
                    convertToIndexableForm(where.getValue())), false);
        } else if (operator == DAOCriteria.LESS_THAN_EQUALS) {
            query = new RangeQuery(null, new Term(where.getFieldName(), 
                    convertToIndexableForm(where.getValue())), true);
        } else if (operator == DAOCriteria.AGE_IN_SECONDS_GREATER_THAN) {
            long seconds = Long.parseLong("" + where.getValue());
            Date compare = new Date(System.currentTimeMillis() - (1000L * seconds));
            query = new RangeQuery(null, new Term(where.getFieldName(), 
                    convertToIndexableForm(compare)), false);
        } else if (operator == DAOCriteria.AGE_IN_SECONDS_LESS_THAN) {
            long seconds = Long.parseLong("" + where.getValue());
            Date compare = new Date(System.currentTimeMillis() - (1000L * seconds));
            query = new RangeQuery(new Term(where.getFieldName(), 
                    convertToIndexableForm(compare)), null, false);
        } else {
            throw new RuntimeException("Unknown operator in DAOCriteria: " + where);
        }
        
        if (where.isNegate()) {
            BooleanQuery notQuery = new BooleanQuery();
            notQuery.add(query, BooleanClause.Occur.MUST_NOT);
            query = notQuery;
        }
        return query;
    }

    protected Sort parseCriteriaIntoSort(DAOCriteria criteria) {
        if (criteria == null) {
            return null;
        }

// 2014.10.09 H.Mizuno 追加 start
        // ソート条件が無い場合でも、空の Sort オブジェクトを復帰する為、クエリー実行時にエラーが発生する。
        // その問題を回避する為、 ソート条件が存在しない場合は null を復帰する様に変更。
        if (criteria.getOrderByClauseCount() == 0) {
        	return null;
        }
// 2014.10.09 H.Mizuno 追加 end
        
        SortField fields[] = new SortField[criteria.getOrderByClauseCount()];
        for (int n = 0; n < fields.length; n++) {
            DAOCriteriaOrderByClause orderBy = criteria.getOrderByClause(n);
            fields[n] = new SortField(orderBy.getFieldName(), 
                    getSortTypeByFieldName(orderBy.getFieldName()),
                    !orderBy.isAscending());
        }
        return new Sort(fields);
    }
    
    protected int getSortTypeByFieldName(String fieldName) {
        Class<?> type = null;
        for (int n = 0; (n < this.serializedFields.length) && (type == null); n++) {
            if (this.serializedFields[n].equals(fieldName)) {
                type = this.serializedFieldTypes[n];
            }
        }
        if (String.class.isAssignableFrom(type)) {
            return SortField.STRING;
        } else if (Integer.class.isAssignableFrom(type) || 
                Long.class.isAssignableFrom(type) ||
                Date.class.isAssignableFrom(type) ||
                Short.class.isAssignableFrom(type) ||
                Byte.class.isAssignableFrom(type) ||
                type.equals(Integer.TYPE) || 
                type.equals(Long.TYPE) || 
                type.equals(Short.TYPE) || 
                type.equals(Byte.TYPE)) {
            return SortField.INT;
        } else if (Float.class.isAssignableFrom(type) || 
                Double.class.isAssignableFrom(type) ||
                type.equals(Float.TYPE) || 
                type.equals(Double.TYPE)) {
            return SortField.FLOAT;
        } else if (Number.class.isAssignableFrom(type)) {
            return SortField.INT;
        } else {
            return SortField.STRING;
        }
    }
    
    public E selectByPK(Object pk) {
        List<E> results = selectByPK(new Object[] {pk});
        if (results.isEmpty()) {
            return null;
        } else {
            return results.iterator().next();
        }
    }
    
    public List<E> selectByPK(Object[] pks) {
        ensureInitialization();
        
        // Build boolean term
        BooleanQuery query = new BooleanQuery();
        query.setMinimumNumberShouldMatch(1);
        for (int n = 0; n < pks.length; n++) {
            if (pks[n] != null) {
                String pkField = buildInternalPKField(pks[n]);
                if (pkField != null) {
                    query.add(new TermQuery(new Term(INTERNAL_PK_FIELD, pkField)), 
                            BooleanClause.Occur.SHOULD);
                }
            }
        }

        boolean lockedReader = false;
        IndexSearcher searcher = null;
        Hits hits = null;
        try {
            try {
                // Step 1: if cached reader exists and needs a reset, wait then close it and clear flag
                synchronized (this) {
                    if (this.openReader != null) {
                        if (this.readerNeedsReset) {
                            this.readerNeedsReset = false;
                            
                            // Wait for a break in usage, then close the index
                            while (this.readerUsageCount > 0) {
                                log.warn("Sleeping: usage counter=" + this.readerUsageCount);
                                try {Thread.sleep(100);} catch (InterruptedException err) {}
                            }
                            IndexReader temp = this.openReader;
                            this.openReader = null;
                            try {temp.close();} catch (IOException err) {}
                        }
                    }
                    
                    // Step 2: if no cached reader, create one
                    if (this.openReader == null) {
                        this.openReader = IndexReader.open(this.directory);
                    }
                    
                    // lock reader for closing
                    lockedReader = true;
                    this.readerUsageCount++;
                }
                
                // Step 3: use reader - do the search
                searcher = new IndexSearcher(this.openReader);
                hits = searcher.search(query);
                searcher.close();
                searcher = null;
            } finally {
                // Step 4: unlock this reader for closing - note: don't sync here, because earlier
                // sync block is waiting for this call to finish
                if (lockedReader) {
                    this.readerUsageCount--;
                }
            }
            
            List<E> results = new ArrayList<E>();
            for (int n = 0; n < hits.length(); n++) {
                E vo = (E) this.valueObjectClass.newInstance();
                deserializeValueObject(hits.doc(n), vo);
                results.add(vo);
            }
            return results;
        } catch (IOException err) {
            throw new RuntimeException("Error during PK search", err);
        } catch (IllegalAccessException err) {
            throw new RuntimeException("Error during result population", err);
        } catch (InstantiationException err) {
            throw new RuntimeException("Error during result population", err);
        } finally {
            if (searcher != null) {
                try {searcher.close();} catch (IOException err) {}
            }
        }
    }

    public void insert(E[] toBeInserted) {
        ensureInitialization();
        if ((toBeInserted == null) || (toBeInserted.length == 0)) {
            return;
        }
        // Allocate pks for any objects without pks
        if ((this.pkFields != null) && (this.pkFields.length == 1)) {
            List<E> needPKs = null;
            try {
                for (int n = 0; n < toBeInserted.length; n++) {
                    if (toBeInserted[n] != null) {
                        Object pk = ReflectionUtils.getFieldValueByGetter(
                                toBeInserted[n], this.pkFields[0]);
                        if (pk == null)  {
                            if (needPKs == null) {
                                needPKs = new ArrayList<E>();
                            }
                            needPKs.add(toBeInserted[n]);
                        }
                    }
                }
                if (needPKs != null) {
                    Object pks[] = this.allocatePrimaryKeyIds(needPKs.size());
                    int n = 0;
                    for (Iterator<E> i = needPKs.iterator(); i.hasNext(); n++) {
                        ReflectionUtils.setFieldValueBySetter(i.next(), this.pkFields[0], pks[n]);
                    }
                }
            } catch (Throwable err) {
                throw new RuntimeException("Error getting/setting field value", err);
            }
        }
        
        update(toBeInserted);
    }

    public int update(E[] toBeUpdated) {
        ensureInitialization();
        if ((toBeUpdated == null) || (toBeUpdated.length == 0)) {
            return 0;
        } else if (toBeUpdated.length == 1) {
            Document doc = new Document();
            doc.add(new Field(INTERNAL_PK_FIELD, 
                    buildInternalPKFieldFromBean(toBeUpdated[0]), 
                    Field.Store.YES, Field.Index.UN_TOKENIZED));
            doc.add(new Field(ALWAYS_TRUE, "true", 
                    Field.Store.YES, Field.Index.UN_TOKENIZED));
            serializeValueObject(doc, toBeUpdated[0]);
            delete(toBeUpdated);
            IndexWriter writer = null;
            try {
                writer = new IndexWriter(this.directory, this.analyzer, false);
                writer.addDocument(doc);
            } catch (IOException err) {
                throw new RuntimeException("Error adding/updating documents in LuceneDAO", err);
            } finally {
                if (writer != null) {
                    try {writer.close();} catch (IOException err) {}
                }
                this.readerNeedsReset = true;
            }
        } else {
            Directory temp = new RAMDirectory();
            IndexWriter writer = null;
            IndexWriter merger = null;
            try {
                writer = new IndexWriter(temp, this.analyzer, true);
                for (int n = 0; n < toBeUpdated.length; n++) {
                    Document doc = new Document();
                    doc.add(new Field(INTERNAL_PK_FIELD, 
                            buildInternalPKFieldFromBean(toBeUpdated[n]), 
                            Field.Store.YES, Field.Index.UN_TOKENIZED));
                    doc.add(new Field(ALWAYS_TRUE, "true", 
                            Field.Store.YES, Field.Index.UN_TOKENIZED));
                    serializeValueObject(doc, toBeUpdated[n]);
                    writer.addDocument(doc);
                }
                writer.close();
                writer = null;
                
                // Delete any previous index entries with the same pk
                delete(toBeUpdated);
                merger = new IndexWriter(this.directory, this.analyzer, false);
                merger.addIndexes(new Directory[] {temp});
            } catch (IOException err) {
                throw new RuntimeException("Error adding/updating documents in LuceneDAO", err);
            } finally { 
                if (writer != null) {
                    try {writer.close();} catch (IOException err) {}
                }
                if (merger != null) {
                    try {merger.close();} catch (IOException err) {}
                }
                this.readerNeedsReset = true;
            }
        }
        return toBeUpdated.length;
    }

    protected void serializeValueObject(Document doc, Object valueobject) {
        for (int n = 0; n < this.serializedFields.length; n++) {
            try {
                Object value = ReflectionUtils.getFieldValueByGetter(
                        valueobject, this.serializedFields[n]);
                if (value != null) {
                    doc.add(new Field(this.serializedFields[n], 
                            convertToIndexableForm(value),
                            Field.Store.YES, Field.Index.UN_TOKENIZED));
                } else {
                    doc.add(new Field(this.serializedFields[n] + NULL_EXTENSION, "true",
                            Field.Store.YES, Field.Index.UN_TOKENIZED));
                }
            } catch (Throwable err) {
                throw new RuntimeException("Error serializing valueobject, field=" + 
                        this.serializedFields[n], err);
            }
        }
    }

    protected void deserializeValueObject(Document doc, Object valueobject) {
        for (int n = 0; n < this.serializedFields.length; n++) {
            try {
                String value = doc.get(this.serializedFields[n]); // don't bother to check the null
                ReflectionUtils.setFieldValueBySetter(valueobject, 
                        this.serializedFields[n], convertFromIndexableForm(
                                value, this.serializedFieldTypes[n]));
            } catch (Throwable err) {
                throw new RuntimeException("Error deserializing valueobject, field=" + 
                        this.serializedFields[n], err);
            }
        }
    }
    
    public void delete(E[] toBeDeleted) {
        ensureInitialization();
        if ((toBeDeleted == null) || (toBeDeleted.length == 0)) {
            return;
        }
        
        // Build big boolean deletion term
        IndexReader deletionReader = null;
        try {
            deletionReader = IndexReader.open(this.directory);
            for (int n = 0; n < toBeDeleted.length; n++) {
                if (toBeDeleted[n] != null) {
                    String pkField = buildInternalPKFieldFromBean(toBeDeleted[n]);
                    if (pkField != null) {
                        Term pkTerm = new Term(INTERNAL_PK_FIELD, pkField);
                        deletionReader.deleteDocuments(pkTerm);
                    }
                }
            }
        } catch (IOException err) {
            throw new RuntimeException("Error deleting documents in LuceneDAO", err);
        } finally {
            if (deletionReader != null) {
                try {deletionReader.close();} catch (IOException err) {}
            }
            this.readerNeedsReset = true;
        }        
    }
    
    public void deleteByPK(Object pks[]) {
        ensureInitialization();
        if ((pks == null) || (pks.length == 0)) {
            return;
        }
        
        // Build big boolean deletion term
        IndexReader deletionReader = null;
        try {
            deletionReader = IndexReader.open(this.directory);
            for (int n = 0; n < pks.length; n++) {
                if (pks[n] != null) {
                    String pkField = buildInternalPKField(pks[n]);
                    if (pkField != null) {
                        Term pkTerm = new Term(INTERNAL_PK_FIELD, pkField);
                        deletionReader.deleteDocuments(pkTerm);
                    }
                }
            }
        } catch (IOException err) {
            throw new RuntimeException("Error deleting documents in LuceneDAO", err);
        } finally {
            if (deletionReader != null) {
                try {deletionReader.close();} catch (IOException err) {}
            }
            this.readerNeedsReset = true;
        }        
    }
    
    public void deleteByFilter(DAOCriteria criteria) {
        ensureInitialization();
        if (criteria == null) {
            throw new RuntimeException("Can't deleteByFilter with a null criteria object");
        }
        Query query = parseCriteriaIntoQuery(criteria);
        
        // Build big boolean deletion term
        IndexReader deletionReader = null;
        IndexSearcher deletionSearcher = null;
        try {
            deletionReader = IndexReader.open(this.directory);
            deletionSearcher = new IndexSearcher(deletionReader);
            Hits hits = deletionSearcher.search(query);
            for (int n = 0; n < hits.length(); n++) {
                deletionReader.deleteDocument(hits.id(n));
            }
        } catch (IOException err) {
            throw new RuntimeException("Error deleting documents in LuceneDAO", err);
        } finally {
            if (deletionSearcher != null) {
                try {deletionSearcher.close();} catch (IOException err) {}
            }
            if (deletionReader != null) {
                try {deletionReader.close();} catch (IOException err) {}
            }
            this.readerNeedsReset = true;
        }        
    }
    
    public int updateByCriteria(DAOCriteria criteria, UpdateExpression[] setClauses) {
        throw new RuntimeException("updateByCriteria() not implemented yet for LuceneDAO");
    }
    
    public Object[] allocatePrimaryKeyIds(final int count) {
        ensureInitialization();
        try {
            return (Object[]) new Lock.With(directory.makeLock(
                    this.valueObjectClass.getName() + ".sequence"), SEQUENCE_TIMEOUT) {
                public Object doBody() {
                    return incrementSequenceValues(count);
                }
              }.run();
        } catch (IOException err) {
            throw new RuntimeException("Error in allocatePrimaryKeyIds", err);
        }
    }
    
    protected Object[] incrementSequenceValues(int count) {
        IndexReader seqReader = null;
        IndexSearcher seqSearcher = null;
        IndexWriter seqWriter = null;
        String keyName = this.valueObjectClass.getName() + ".sequence";
        long seqValue = 0;
        try {
            // Get existing sequence value
            seqReader = IndexReader.open(this.directory);
            seqSearcher = new IndexSearcher(seqReader);
            Hits hits = seqSearcher.search(new TermQuery(new Term(keyName, "true")));
            if (hits.length() > 0) {
                seqValue = Long.parseLong(hits.doc(0).get("sequenceValue"));
                seqReader.deleteDocument(hits.id(0));
            }
            seqSearcher.close();
            seqSearcher = null;
            seqReader.close();
            seqReader = null;
            
            Object returnValues[] = new Long[count];
            for (int n = 0; n < count; n++) {
                returnValues[n] = new Long(seqValue + n);
            }
            seqValue += count;
            
            // Add new seq value
            Document doc = new Document();
            doc.add(new Field(keyName, "true", Field.Store.YES, Field.Index.UN_TOKENIZED));
            doc.add(new Field("sequenceValue", "" + seqValue, Field.Store.YES, Field.Index.UN_TOKENIZED));
            seqWriter = new IndexWriter(this.directory, this.analyzer, false);
            seqWriter.addDocument(doc);
            return returnValues;
        } catch (IOException err) {
            throw new RuntimeException("Error incrementing sequence value", err);
        } finally {
            if (seqSearcher != null) {
                try {seqSearcher.close();} catch (IOException err) {}
            }
            if (seqReader != null) {
                try {seqReader.close();} catch (IOException err) {}
            }
            if (seqWriter != null) {
                try {seqWriter.close();} catch (IOException err) {}
            }
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
    protected static final DateFormat DF = new SimpleDateFormat("yyyyMMddHHmmssSSS");
    protected static final String INTERNAL_PK_FIELD = "LuceneDAO.InternalPKField";
    protected static final String NULL_EXTENSION = "_ISNULL";
    protected static final String ALWAYS_TRUE = "alwaysTrue";
    protected static final int SEQUENCE_TIMEOUT = 500;
    
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
}
