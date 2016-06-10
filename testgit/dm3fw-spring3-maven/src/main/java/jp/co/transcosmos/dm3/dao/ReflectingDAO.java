/*
 * Copyright (C) 2006 Transcosmos INC.
 * All Rights Reserved.
 */
package jp.co.transcosmos.dm3.dao;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import javax.sql.DataSource;

import jp.co.transcosmos.dm3.utils.ReflectionUtils;
import jp.co.transcosmos.dm3.validation.Validateable;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

/**
 * Implements the basic DAO pattern, using reflection and naming conventions 
 * to derive the SQL needed for persistence to an ANSI-SQL compliant database.
 * 
 * @author <a href="mailto:rick_knowles@hotmail.com">Rick Knowles</a>
 * @version $Id: ReflectingDAO.java,v 1.20 2007/11/30 03:32:25 abe Exp $
 */
public class ReflectingDAO<E> implements DAO<E>, SQLClauseBuilder, SubQueryable, 
        HasDataSource, HasValueObjectClassName, HasPkFields, AliasDotColumnResolver {
    private static final Log log = LogFactory.getLog(ReflectingDAO.class);
    
    /** マッピングするテーブル名 */
    protected String tableName;

    /** 主キーの自動採番で使用するシーケンス名 */
    protected String pkSequenceName;

    /** テーブルの主キーカラム名 */
    protected String pkFields[];

    /** VO クラス（このクラス名がＤＢのテーブル名とマッピングされる。　また、Java 側とのデータの受け渡しはこのクラスのインスタンスを使用する。） */
    protected Class<? extends E> valueObjectClass;

    /** マッピングするバリーオブジェクトのフィールド名 */
    private String fieldNames[]; // private because we want to force override of assignFieldNameToColumnNameMappings

    /** マッピングするテーブルのカラム名 */
    private String columnNames[];

    private Map<String,String> fieldToColumnMappingCache;
    protected int inSearchThreshold = 100; // how many rows to try to batch into an IN clause for one-column-where search
    protected String upperCaseFunction;
    protected DataSource dataSource;
    private Boolean useAutonumberColumns;
    private boolean capitalizeFieldNames;

    // note
    // SELECT 文実行時の該当件数を getRow() で取得する場合、Driver によっては last() を実行しないと
    // 正確な行数を返さない場合がある。　しかし、FORWARD ONLY の ResultSet の場合、この方法は使用できない。
    // （例外が発生する。）　その様な Driver を使用する場合、このプロパティ値を true に設定する。
    // true に設定した場合、getRow() を使用せずに COUNT(*) の SELECT 文で該当件数を取得して処理する。
    /** 該当件数を別途取得する場合、true を設定する。 （デフォルト false）*/
    private boolean useCountQueryForMaxRows;

    private boolean allowUnknownCriteriaField;
    private String sequenceNextvalExpression = "SELECT ###pkSequenceName###.nextval from dual"; 
    private RowMapper resultRowMapper;
    private boolean nullResultIfAllFieldsAreNull;
    private boolean emptyStringsToNull = false;
    private boolean nullStringsToEmpty = false;

    private DerivedFieldEvaluator<E> derivableFieldEvaluator; 
    private DAOInsertListener<E> insertListeners[];
    private DAOUpdateListener<E> updateListeners[];
    private DAODeleteListener<E> deleteListeners[];


// 2013.11.15 H.Mizuno 直接的なフィールドマッピングに対応したコンストラクタを追加 start
    
    // デフォルトコンストラクタ
    public ReflectingDAO(){    }
    
    
    // VO のフィールド自動マッピングが対応出来ない場合、setFieldMappings で設定する事ができる。
    // 但し、プロパティの設定順が、setValueObjectClassName()、setFieldMappings()、
    // setDataSource() の順である必要がある。
    // XML の定義順は、厳密には保証されないので、fieldMappings　を XML で定義する場合は、
    // このコンストラクタで設定する事。
    // コンストラクタ
    public ReflectingDAO(String valueObjectClassName, Map<String,String> mappings){
    	this.setValueObjectClassName(valueObjectClassName);
    	this.setFieldMappings(mappings);
    }
// 2013.11.15 H.Mizuno 直接的なフィールドマッピングに対応したコンストラクタを追加 end

    

// 2014.12.19 H.Mizuno fieldMappings と、derivableFiedlEvaluator を併用する為、コンストラクターを追加 start
    public ReflectingDAO(String valueObjectClassName, Map<String,String> mappings, DerivedFieldEvaluator<E> derivableFieldEvaluator){
    	this.setValueObjectClassName(valueObjectClassName);
    	this.setDerivableFieldEvaluator(derivableFieldEvaluator);
    	this.setFieldMappings(mappings);
    }
 // 2014.12.19 H.Mizuno fieldMappings と、derivableFiedlEvaluator を併用する為、コンストラクターを追加 end

    
    
    /**
     * Override this in subclasses to define a forced value-object to reflect 
     * across (ie to disallow spring injection)
     */
    protected Class<? extends E> getValueObjectClass() {
        return this.valueObjectClass;
    }

    public String getValueObjectClassName() {
        return this.valueObjectClass == null ? null : this.valueObjectClass.getName();
    }
    
    /**
     * Override this in subclasses to define a forced value-object to reflect 
     * across (ie to disallow spring injection)
     */
    @SuppressWarnings("unchecked")
    public void setValueObjectClassName(String valueObjectClassName) {
        try {
            this.valueObjectClass = (Class<? extends E>) Class.forName(valueObjectClassName);
        } catch (ClassNotFoundException err) {
            throw new RuntimeException("Error loading DAO class: " + valueObjectClassName, err);
        }
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<String> getPkFields() {
        return this.pkFields == null ? null : Arrays.asList(this.pkFields);
    }

    /**
     * Set the fields which are unique to this valueobject, used as a primary key
     */
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

    /**
     * Set the fields which are unique to this valueobject, used as a primary key
     */
    public void setPkField(String pkField) {
        setPkFields(Arrays.asList(new String[] {pkField}));
    }

    public int getInSearchThreshold() {
        return inSearchThreshold;
    }

    /**
     * Set the threshold under which batched primary key selections will use an IN query
     */
    public void setInSearchThreshold(int inSearchThreshold) {
        this.inSearchThreshold = inSearchThreshold;
    }

    public String getUpperCaseFunction() {
        return upperCaseFunction;
    }

    /**
     * Override the SQL Uppercase function. Default is UPPER, might want to 
     * override with UCase() for some SQLVariants like MSSQL.
     */
    public void setUpperCaseFunction(String upperCaseFunction) {
        this.upperCaseFunction = upperCaseFunction;
    }

    /**
     * Provide a map of associations, where the key is the java valueobject attribute
     * name, and the value is the db column name. These are provided using naming
     * conventions if no mapping is supplied, so this function can be called as a manual
     * override for the mappings.
     */
    public void setFieldMappings(Map<String,String> mappings) {
        ensureInitialization(mappings);
    }
    
    public boolean isUseAutonumberColumns() {
        return (useAutonumberColumns != null ? useAutonumberColumns.booleanValue() : false);
    }

    /**
     * If true, the DAO will not attempt to use sequences, instead relying on the
     * database to automatically provide a value. The generated value will be loaded 
     * after insert and assigned to the valueobject.
     */
    public void setUseAutonumberColumns(boolean useAutonumberColumns) {
        this.useAutonumberColumns = new Boolean(useAutonumberColumns);
    }

    public String getPkSequenceName() {
        return pkSequenceName;
    }

    /**
     * Set the name of the DB sequence to get primary key values from
     */
    public void setPkSequenceName(String pkSequenceName) {
        this.pkSequenceName = pkSequenceName;
    }
    
    /**
     * Override the DB table name to save to / load from
     */
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    /**
     * If true, all field names are upper-cased, to support case-sensitive databases
     * like MySQL.
     */
    public void setCapitalizeFieldNames(boolean capitalizeFieldNames) {
        this.capitalizeFieldNames = capitalizeFieldNames;
    }


    /**
     * If true, issue an additional row count query before paging results to prevent
     * having to read in the entire contents of the resultset in finding out the
     * max rows.
     */
    public void setUseCountQueryForMaxRows(boolean pUseCountQueryForMaxRows) {
        this.useCountQueryForMaxRows = pUseCountQueryForMaxRows;
    }

    public String getSequenceNextvalExpression() {
        return this.sequenceNextvalExpression;
    }

    /**
     * Override the sequence nextval expression. This defaults to
     * "SELECT nextval('###pkSequenceName###')" if postgresql is detected, and
     * "SELECT ###pkSequenceName###.nextval from dual" for anything else.
     */
    public void setSequenceNextvalExpression(String pSequenceNextvalExpression) {
        this.sequenceNextvalExpression = pSequenceNextvalExpression;
    }

    public boolean isAllowUnknownCriteriaField() {
        return this.allowUnknownCriteriaField;
    }

    public void setAllowUnknownCriteriaField(boolean pErrorOnUnknownField) {
        this.allowUnknownCriteriaField = pErrorOnUnknownField;
    }
    
    /**
     * Set true if valueobject should be null if all the component fields for that 
     * valueobject are null. This is usually the case when an outer join has not 
     * matched anything in the outer table. Default of false will insert a blank 
     * valueobject in the JoinResult output if all fields are null.
     */
    public void setNullResultIfAllFieldsAreNull(boolean pNullResultIfAllFieldsAreNull) {
        this.nullResultIfAllFieldsAreNull = pNullResultIfAllFieldsAreNull;
    }
    
    /**
     * If true, any empty strings retrieved from the db or saved from the application to
     * the db are converted to null values automatically. This can be used to enforce 
     * DB-cleanliness policies.
     */
    public void setEmptyStringsToNull(boolean pEmptyStringsToNull) {
        this.emptyStringsToNull = pEmptyStringsToNull;
    }
    
    /**
     * If true, any null strings retrieved from the db or saved from the application to
     * the db are converted to empty strings automatically. This can be used to enforce 
     * DB-cleanliness policies.
     */
    public void setNullStringsToEmpty(boolean pNullStringsToEmpty) {
        this.nullStringsToEmpty = pNullStringsToEmpty;
    }

    /**
     * Used to assign a DerivedFieldEvaluator to this DAO. DerivedFieldEvaluators are 
     * useful for managing calculated columns, that we don't want to save or load, but
     * instead derive from other values on the same valueobject.
     * 
     * @see DerivedFieldEvaluator
     */
    public void setDerivableFieldEvaluator(DerivedFieldEvaluator<E> derivableFieldEvaluator) {
        this.derivableFieldEvaluator = derivableFieldEvaluator;
    }

    /**
     * Used to assign a set of DAODeleteListeners to this DAO. These listeners will be 
     * notified on any deletions - useful for logging or trigger style actions, application
     * server side, such as consistency checks or external indexing.
     */
    @SuppressWarnings("unchecked")
    public void setDeleteListener(Collection<DAODeleteListener<E>> deleteListeners) {
        this.deleteListeners = deleteListeners.toArray(new DAODeleteListener[0]);
        for (DAODeleteListener<E> deleteListener : deleteListeners) {
            if (deleteListener instanceof HasValueObjectClassName) {
                HasValueObjectClassName c = (HasValueObjectClassName) deleteListener;
                if (c.getValueObjectClassName() == null) {
                    c.setValueObjectClassName(this.getValueObjectClassName());
                }
            }
            if (deleteListener instanceof HasPkFields) {
                HasPkFields c = (HasPkFields) deleteListener;
                if (c.getPkFields() == null) {
                    c.setPkFields(this.getPkFields());
                }
            }
        }
    }

    /**
     * Used to assign a set of DAOInsertListener to this DAO. These listeners will be 
     * notified on any insertions - useful for logging or trigger style actions, application
     * server side, such as consistency checks or external indexing.
     */
    @SuppressWarnings("unchecked")
    public void setInsertListener(Collection<DAOInsertListener<E>> insertListeners) {
        this.insertListeners = insertListeners.toArray(new DAOInsertListener[0]);
        for (DAOInsertListener<E> insertListener : insertListeners) {
            if (insertListener instanceof HasValueObjectClassName) {
                HasValueObjectClassName c = (HasValueObjectClassName) insertListener;
                if (c.getValueObjectClassName() == null) {
                    c.setValueObjectClassName(this.getValueObjectClassName());
                }
            }
            if (insertListener instanceof HasPkFields) {
                HasPkFields c = (HasPkFields) insertListener;
                if (c.getPkFields() == null) {
                    c.setPkFields(this.getPkFields());
                }
            }
        }
    }

    /**
     * Used to assign a set of DAOInsertListener to this DAO. These listeners will be 
     * notified on any insertions - useful for logging or trigger style actions, application
     * server side, such as consistency checks or external indexing.
     */
    @SuppressWarnings("unchecked")
    public void setUpdateListener(Collection<DAOUpdateListener<E>> updateListeners) {
        this.updateListeners = updateListeners.toArray(new DAOUpdateListener[0]);
        for (DAOUpdateListener<E> updateListener : updateListeners) {
            if (updateListener instanceof HasValueObjectClassName) {
                HasValueObjectClassName c = (HasValueObjectClassName) updateListener;
                if (c.getValueObjectClassName() == null) {
                    c.setValueObjectClassName(this.getValueObjectClassName());
                }
            }
            if (updateListener instanceof HasPkFields) {
                HasPkFields c = (HasPkFields) updateListener;
                if (c.getPkFields() == null) {
                    c.setPkFields(this.getPkFields());
                }
            }
        }
    }

    /**
     * Override this to define a custom mapping method from valueobject-fields to DB-columns. 
     * The default implementation reflects across the assigned valueobject, and builds a 
     * mapping table using naming-conventions. If you want to just assign a one-off custom 
     * mapping, try using {@link setFieldMappings(Map)} instead.
     */
    protected String[][] assignFieldNameToColumnNameMappings(Map<String,String> suggestedMappings) {
        List<String[]> outputMappings = new ArrayList<String[]>();
        
        String classNameWithoutPackage = ReflectionUtils.getClassNameWithoutPackage(this.valueObjectClass);
        if (this.tableName == null) {
            this.tableName = mappingPolicyFieldNameToColumnName(classNameWithoutPackage);
            log.warn("No table declared for DAO: mapping " + 
                    classNameWithoutPackage + " --> " + this.tableName);
        }
        
        String allFieldNames[] = ReflectionUtils.getAllFieldNamesByGetters(this.valueObjectClass);
        for (int n = 0; n < allFieldNames.length; n++) {
            if (this.pkFields == null) {
                this.pkFields = new String[] {allFieldNames[n]};
                log.warn("No primary key declared for DAO: using " + 
                        this.valueObjectClass + "." + allFieldNames[n] + 
                        " as primary key for table " + this.tableName);
            }
            
            if ((this.derivableFieldEvaluator != null) && 
                    this.derivableFieldEvaluator.isFieldDerived(allFieldNames[n])) {
                log.debug("Found derived field (" + allFieldNames[n] + 
                        ") - ignoring for standard db operations"); 
            } else {
                String mappedTo = null;
                if (suggestedMappings != null) {
                    mappedTo = (String) suggestedMappings.get(allFieldNames[n]);
                }
                if ((mappedTo == null) || mappedTo.equals("")) {
                    mappedTo = mappingPolicyFieldNameToColumnName(allFieldNames[n]); 
                }
                outputMappings.add(new String[] {allFieldNames[n], mappedTo});
                log.debug("Mapping " + classNameWithoutPackage + "." + allFieldNames[n] + 
                        " --> " + this.tableName + "." + mappedTo);
            }
        }
        
        if (this.pkSequenceName == null) {
            String pkColumnName = null;
            if (suggestedMappings != null) {
                pkColumnName = (String) suggestedMappings.get(this.pkFields[0]);
            } else {
                pkColumnName = mappingPolicyFieldNameToColumnName(this.pkFields[0]); 
            }
            this.pkSequenceName = this.tableName + "_" + pkColumnName + "_seq";
            log.warn("No sequence declared for DAO: using " + this.pkSequenceName);
        }
        
        // log the mapped rows
        return outputMappings.toArray(new String[outputMappings.size()][2]);
    }
    
    /**
     * No implicit criteria - returns null
     */
    public DAOCriteria buildImplicitCriteria() {
		return null;
	}

	/**
     * This is where the default naming policy is defined. Override this to provide 
     * custom naming policies.
     */
    protected String mappingPolicyFieldNameToColumnName(String fieldName) {
    	String mappedTo = ReflectionUtils.lmcToUnderscore(fieldName);
        if (this.capitalizeFieldNames) {
        	mappedTo = mappedTo.toUpperCase();
        }
        return mappedTo;
    }
    
    /**
     * Internal method that confirms a valid initialized state before doing anything.
     */
    protected void ensureInitialization(Map<String,String> explicitMappings) {
        synchronized (this) {
            if (this.fieldNames == null) {
                String fieldNameToColumnNameMappings[][] = assignFieldNameToColumnNameMappings(explicitMappings);
                this.fieldNames = new String[fieldNameToColumnNameMappings.length];
                this.columnNames = new String[fieldNameToColumnNameMappings.length];
                Map<String,String> mapping = new HashMap<String,String>();
                for (int n = 0; n < this.fieldNames.length; n++) {
                	this.fieldNames[n] = fieldNameToColumnNameMappings[n][0];
                	this.columnNames[n] = fieldNameToColumnNameMappings[n][1];
                	mapping.put(this.fieldNames[n], this.columnNames[n]);
                }
                this.fieldToColumnMappingCache = Collections.unmodifiableMap(mapping);
                
                this.resultRowMapper = new ReflectingRowMapper<E>(
                		this.valueObjectClass, 
                        this.fieldNames,
                        getFieldAliases("", this.fieldNames), 
                        false, 
                        this.nullStringsToEmpty, 
                        this.emptyStringsToNull,
                        this.derivableFieldEvaluator);
            }
            if ((this.useAutonumberColumns == null) && (this.dataSource != null)) {
                // Get the connection metadata
                Connection connection = null;
                try {
                    connection = this.dataSource.getConnection();
                    DatabaseMetaData md = connection.getMetaData(); // just to get a driver class
                    this.useAutonumberColumns = new Boolean(
                            md.getClass().getName().toLowerCase().startsWith("com.mysql."));
                    log.info("Auto-setting useAutonumberColumns = " + this.useAutonumberColumns + 
                            " based on JDBC connection meta-data class: " + md.getClass().getName());
                    
                    if (md.getClass().getName().startsWith("org.postgresql.")) {
                        this.sequenceNextvalExpression = "SELECT nextval('###pkSequenceName###')";
                    }
                } catch (SQLException err) {
                    log.warn("Error getting a connection to auto-set userAutonumberColumns, " +
                            "ignoring and setting to false (WARNING)", err);
                    this.useAutonumberColumns = Boolean.FALSE;
                } finally {
                    if (connection != null) {
                        try {connection.close();} catch (SQLException err) {}
                    }
                }
            }
        }
    }

    /**
     * Builds the SELECT clause for this DAO, using only the fields in filterFields.
     */
    public void addSelectClause(StringBuilder sql, String alias, String filterFields[]) {
        ensureInitialization(null);
        if (filterFields == null) {
        	filterFields = this.fieldNames;
        }
        String fieldAliases[] = getFieldAliases(alias, filterFields);
        boolean addedColumn = false;
        for (int n = 0; n < filterFields.length; n++) {
            String aliasDotColumn = lookupAliasDotColumnName(filterFields[n], "", alias);
            if (aliasDotColumn != null) {
                if (addedColumn) {
                    sql.append(", ");
                }
                addedColumn = true;

                sql.append(aliasDotColumn);            
                if (!fieldAliases[n].equals(findColumnName(filterFields[n]))) {
                    sql.append(" AS ");
                    sql.append(fieldAliases[n]);
                }
            	
            }
        }
    }

    private String[] getFieldAliases(String thisAlias, String forFieldNames[]) {

    	String out[] = new String[forFieldNames.length];

    	for (int n = 0; n < forFieldNames.length; n++) {
            out[n] = lookupLabelledName(forFieldNames[n], "", thisAlias);
            if (out[n].length() > 30) {
                out[n] = out[n].substring(0, 30);
            }
            
            // Confirm that we haven't used this name before
// 2015.06.17 H.Mizuno Group By 対応により、共通化 start
/*
            boolean found = true;
            while (found) {
                found = false;
                for (int k = 0; (k < n) && !found; k++) {
                    found = out[k].equalsIgnoreCase(out[n]);
                }
                if (found) {
                    String lastThree = out[n].substring(out[n].length() - 3);
                    int index = 0;
                    try {
                        index = Integer.parseInt(lastThree);
                        index++;
                    } catch (NumberFormatException err) {
                        index = 0;
                    }
                    out[n] = out[n].substring(0, out[n].length() - 3) + 
                            (index < 10 ? ("00" + index) : 
                                (index < 100 ? ("0" + index) : index));
                }
            }
*/
            DAOUtils.dupliateAliasRename(out, n);
// 2015.06.17 H.Mizuno Group By 対応により、共通化 start

        }
        return out;
    }

    /**
     * Writes out the alias.columnname form for use in an SQL statement.
     */
    public String lookupAliasDotColumnName(String fieldName, String fieldAlias, String thisAlias) {
        ensureInitialization(null);
        if ((fieldAlias == null) || fieldAlias.equals("") || 
                (thisAlias == null) || thisAlias.equals("") || 
                fieldAlias.equalsIgnoreCase(thisAlias)) {
        	String name = findColumnName(fieldName);
            if (name == null) {
            	return null;
            } else if ((fieldAlias != null) && !fieldAlias.equals("")) {
                return fieldAlias + "." + name;
            } else if ((thisAlias != null) && !thisAlias.equals("")) {
                return thisAlias + "." + name;
            } else {
            	return name;
            }
        } else {
            return null;
        }
    }
    
    /**
     * Returns the label used for a specific column.
     */
    @Override
    public String lookupLabelledName(String fieldName, String fieldAlias, String thisAlias) {
        ensureInitialization(null);
        if ((fieldAlias == null) || fieldAlias.equals("") || 
                (thisAlias == null) || thisAlias.equals("") || 
                fieldAlias.equalsIgnoreCase(thisAlias)) {
        	String name = findColumnName(fieldName);
            if (name == null) {
            	return null;
            } else if ((fieldAlias != null) && !fieldAlias.equals("")) {
                return fieldAlias + "_" + name;
            } else if ((thisAlias != null) && !thisAlias.equals("")) {
                return thisAlias + "_" + name;
            } else {
            	return name;
            }
        } else {
            return null;
        }
    }
    
    protected String findColumnName(String fieldName) {
    	return this.fieldToColumnMappingCache.get(fieldName);
    }
    
    /**
     * Builds the SQL FROM clause for this DAO.
     */
    public void addFromClause(StringBuilder sql, String alias, List<Object> params) {
        ensureInitialization(null);
        if ((alias != null) && !alias.equals("")) {
            sql.append(this.tableName).append(' ').append(alias);
        } else {
            sql.append(this.tableName);
        }
    }
    
    /**
     * Builds a single SQL ORDER BY clause element for this DAO.
     */
    public boolean addOneOrderByClause(StringBuilder sql, String thisAlias, 
            DAOCriteriaOrderByClause orderBy) {
        ensureInitialization(null);
        if ((orderBy.getDaoAlias() != null) && (thisAlias != null) && !thisAlias.equals("") 
                && !thisAlias.equals(orderBy.getDaoAlias())) {
            return false;
        }
        String mapped = lookupAliasDotColumnName(orderBy.getFieldName(), 
        		orderBy.getDaoAlias(), thisAlias);
        if (mapped != null) {
            sql.append(mapped).append(orderBy.isAscending() ? " ASC" : " DESC");
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * Writes out the SQL expression for the supplied criteria on this DAO so that
     * it can be used in a sub-query such as an EXISTS or IN query.
     */
    public void buildSubQuerySQL(StringBuilder sql, DAOCriteria subCriteria, 
            List<Object> params, String[] selectFields, String[] selectFieldAliases, 
            String thisAlias) {
        ensureInitialization(null);
        subCriteria = prepareCriteria(subCriteria, null);
        sql.append("SELECT ");
        if (selectFields == null) {
            sql.append("1");
        } else {
            for (int n = 0; n < selectFields.length; n++) {
                if (n > 0) {
                    sql.append(", ");
                }
                String columnName = lookupAliasDotColumnName(selectFields[n], 
                        (selectFieldAliases == null || selectFieldAliases.length <= n) 
                        ? null : selectFieldAliases[n], thisAlias);
                if (columnName != null) {
                	sql.append(columnName);
                	String label = lookupLabelledName(selectFields[n], 
                            (selectFieldAliases == null || selectFieldAliases.length <= n) 
                            ? null : selectFieldAliases[n], thisAlias);
                	if ((label != null) && !label.equals(columnName)) {
                        sql.append(" AS ").append(label);
                	}
                } else {
                	log.warn("WARNING: Skipping missing column name: " + selectFields[n]);
                }
            }
        }
        sql.append(" FROM ");
// 2015.06.10 H.Mizuno thisAlias がサブクエリの From 句に適用されない問題を修正 start
//        addFromClause(sql, "", params);
        addFromClause(sql, thisAlias, params);
// 2015.06.10 H.Mizuno thisAlias がサブクエリの From 句に適用されない問題を修正 end
        
        // Add restrictions as sql
        if (subCriteria != null) {
// 2015.06.10 H.Mizuno thisAlias がサブクエリの From 句に適用されない問題を修正 start
//            DAOUtils.addWhereClauses(this, subCriteria, "", sql, params, 
//                    this.allowUnknownCriteriaField);
            DAOUtils.addWhereClauses(this, subCriteria, thisAlias, sql, params, 
                    this.allowUnknownCriteriaField);
// 2015.06.10 H.Mizuno thisAlias がサブクエリの From 句に適用されない問題を修正 end
        }
    }

    /**
     * Returns the row mapper for this DAO's valueobject using reflection to map
     * the two and populate the output.
     */
    public RowMapper getRowMapper(String fieldPrefix, String filterFields[], boolean isJoinedTable) {
        ensureInitialization(null);
        if (filterFields == null) {
        	filterFields = this.fieldNames;
        }
        return new ReflectingRowMapper<E>(this.valueObjectClass, 
        		filterFields, 
                getFieldAliases(fieldPrefix, filterFields),
                this.nullResultIfAllFieldsAreNull, 
                this.nullStringsToEmpty,
                this.emptyStringsToNull, 
                this.derivableFieldEvaluator);
    }
    
    public DAOCriteria prepareCriteria(DAOCriteria criteria, String thisAlias) {
        if (this.derivableFieldEvaluator != null) {
            criteria = this.derivableFieldEvaluator.rewriteDerivedCriteria(criteria, thisAlias);
        }
        return criteria;
    }

    @SuppressWarnings("unchecked")
    public List<E> selectByFilter(DAOCriteria criteria) {
        ensureInitialization(null);
        criteria = prepareCriteria(criteria, null);

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ");
        addSelectClause(sql, "", null);
        sql.append(" FROM ");
        addFromClause(sql, "", null);

        // Add restrictions as sql
        List<Object> params = new ArrayList<Object>();
        DAOUtils.addWhereClauses(this, criteria, "", sql, params, this.allowUnknownCriteriaField);
        DAOUtils.addOrderByClauses(this, criteria, "", sql, this.allowUnknownCriteriaField);
        DAOUtils.dumpSQL(sql.toString(), params);
        JdbcTemplate template = new JdbcTemplate(dataSource);
        if ((criteria != null) && (criteria.getMaxRows() >= 0)) {
            template.setMaxRows(criteria.getMaxRows());
        }

        // Set up the query builder and result extractor
        FragmentPreparedStatementCreator psc = new FragmentPreparedStatementCreator(
                sql.toString(), params);
        FragmentResultSetExtractor<E> rse = new FragmentResultSetExtractor<E>(
                this.resultRowMapper, criteria);
        if (rse.isFragmentMode() && this.useCountQueryForMaxRows) {
            rse.setRowCountHint(getRowCountMatchingFilterInternal(criteria));
        }

        // Evaluate the query
        long start = System.currentTimeMillis();
        List<E> results = (List<E>) template.query(psc, psc, rse);
        log.info("SelectByFilter query found " + results.size() + " rows in " + 
                (System.currentTimeMillis() - start) + "ms");
        return results;
    }

    public int getRowCountMatchingFilter(DAOCriteria criteria) {
        ensureInitialization(null);
        criteria = prepareCriteria(criteria, null);
        return getRowCountMatchingFilterInternal(criteria);
    }
    
    protected int getRowCountMatchingFilterInternal(DAOCriteria criteria) {
        ensureInitialization(null);
        JdbcTemplate template = new JdbcTemplate(getDataSource());
        
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT count(1) AS rowCount FROM ");
        addFromClause(sql, "", null);
        
        // Add restrictions as sql
        long start = System.currentTimeMillis();
        int rowCount = 0;
        List<Object> params = new ArrayList<Object>();
        DAOUtils.addWhereClauses(this, criteria, "", sql, params, this.allowUnknownCriteriaField);
        DAOUtils.dumpSQL(sql.toString(), params);
        rowCount = template.queryForInt(sql.toString(), params.toArray());
        log.info("Row count query found " + rowCount + " rows in " + 
                (System.currentTimeMillis() - start) + "ms");
        return rowCount;
    }

    public boolean addOneWhereClause(StringBuilder sql, String thisAlias, 
            DAOCriteriaWhereClause where, List<Object> params) {
        ensureInitialization(null);
        if ((where.getDaoAlias() != null) && (thisAlias != null) && !thisAlias.equals("") 
                && !thisAlias.equals(where.getDaoAlias())) {
            return false;
        }
        String mapped = lookupAliasDotColumnName(where.getFieldName(), where.getDaoAlias(), thisAlias);
        if (mapped == null) {
            return false;
        }
        int operator = where.getOperator();
//        
//        String aliasPrefix = "";
//        if (where.getDaoAlias() != null) {
//            aliasPrefix = where.getDaoAlias() + ".";
//        }
        
        if (operator == DAOCriteria.ALWAYS_TRUE) {
            sql.append("1 = ").append(where.isNegate() ? "0" : "1");
        } else if (operator == DAOCriteria.IS_NULL) {
            sql.append(mapped).append(" IS ").append(where.isNegate() ? "NOT NULL" : "NULL");
        } else  {
            Object outValue = where.getValue();

            // LHS
            if ((operator == DAOCriteria.EQUALS_CASE_INSENSITIVE) ||
                (operator == DAOCriteria.LIKE_CASE_INSENSITIVE)) { 
                sql.append(wrapInUpperCase(mapped));
            } else {
                sql.append(mapped);
            }
            
            // Operator
            if ((operator == DAOCriteria.EQUALS) || 
                       (operator == DAOCriteria.EQUALS_CASE_INSENSITIVE)) {
                sql.append(where.isNegate() ? " <> " : " = ");
            } else if ((operator == DAOCriteria.LIKE) ||
                        (operator == DAOCriteria.LIKE_CASE_INSENSITIVE))  {
                sql.append(where.isNegate() ? " NOT LIKE " : " LIKE ");
            } else if (operator == DAOCriteria.GREATER_THAN) {
                sql.append(where.isNegate() ? " <= " : " > ");
            } else if (operator == DAOCriteria.GREATER_THAN_EQUALS) {
                sql.append(where.isNegate() ? " < " : " >= ");
            } else if (operator == DAOCriteria.LESS_THAN) {
                sql.append(where.isNegate() ? " >= " : " < ");
            } else if (operator == DAOCriteria.LESS_THAN_EQUALS) {
                sql.append(where.isNegate() ? " > " : " <= ");
            } else if (operator == DAOCriteria.AGE_IN_SECONDS_GREATER_THAN) {
                long seconds = Long.parseLong("" + where.getValue());
                outValue = new Date(System.currentTimeMillis() - (1000L * seconds));
                sql.append(where.isNegate() ? " >= " : " < ");
            } else if (operator == DAOCriteria.AGE_IN_SECONDS_LESS_THAN) {
                long seconds = Long.parseLong("" + where.getValue());
                outValue = new Date(System.currentTimeMillis() - (1000L * seconds));
                sql.append(where.isNegate() ? " <= " : " > ");
            } else {
                throw new RuntimeException("Unknown operator in DAOCriteria: " + where);
            }

// 2015.06.11 H.Mizuno サブクエリ拡張 start
            if (outValue instanceof FieldExpression) {
            	// バインド変数のオブジェクトが、FieldExpression の場合、フィールド情報として
            	// 条件文を生成する。
            	FieldExpression field = (FieldExpression)outValue;
            	String fieldString = field.getAlias() + "." + ReflectionUtils.lmcToUnderscore(field.getFieldName());

                if ((operator == DAOCriteria.EQUALS_CASE_INSENSITIVE) ||
                        (operator == DAOCriteria.LIKE_CASE_INSENSITIVE)) { 
                    sql.append(wrapInUpperCase(fieldString));
                } else {
                    sql.append(fieldString);
                }
            	
            } else {
// 2015.06.11 H.Mizuno サブクエリ拡張 end

                // RHS
                if (params != null) {
                    params.add(outValue);
                }
                if ((operator == DAOCriteria.EQUALS_CASE_INSENSITIVE) ||
                        (operator == DAOCriteria.LIKE_CASE_INSENSITIVE)) { 
                    sql.append(wrapInUpperCase("?"));
                } else {
                    sql.append("?");
                }
// 2015.06.11 H.Mizuno サブクエリ拡張 start
            }
// 2015.06.11 H.Mizuno サブクエリ拡張 end
        }
        return true;
    }

    protected String wrapInUpperCase(String expression) {
        if (this.upperCaseFunction == null) {
            return "UPPER(" + expression + ")";
        } else {
            return this.upperCaseFunction + "(" + expression + ")";
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

    @SuppressWarnings("unchecked")
    public List<E> selectByPK(Object[] pks) {
        if (pks == null) {
            return null;
        }
        
        ensureInitialization(null);
        JdbcTemplate template = new JdbcTemplate(getDataSource());

        long start = System.currentTimeMillis();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ");
        addSelectClause(sql, "", null);
        sql.append(" FROM ");
        addFromClause(sql, "", null);
        sql.append(" WHERE ");
        if ((this.pkFields.length == 1) && (pks.length > 1) && 
                (pks.length < getInSearchThreshold())) {
            // do in clause
            String pkColumn = lookupAliasDotColumnName(this.pkFields[0], null, null);
            sql.append(pkColumn).append(" IN (");
            for (int n = 0; n < pks.length; n++) {
                if (n > 0) {
                    sql.append(", ");
                }
                sql.append("?");
            }
            sql.append(")");
            DAOUtils.dumpSQL(sql.toString(), Arrays.asList(pks));
            List<E> results = (List<E>) template.query(sql.toString(), 
                    pks, this.resultRowMapper);
            log.info("Query returned " + results.size() + " rows in " + 
                    (System.currentTimeMillis() - start) + "ms");
            return results;
        } else {
            // do row by row select
            addPKWhereClauseSQL(sql);
            List<Object> params = new ArrayList<Object>();
            List<E> results = new ArrayList<E>();
            for (int n = 0; n < pks.length; n++) {
                params.clear();
                buildPKParameterSource(pks[n], params);
                DAOUtils.dumpSQL(sql.toString(), params);
                List<E> oneLine = (List<E>) template.query(sql.toString(), 
                        params.toArray(), this.resultRowMapper);
                log.info("Query returned " + oneLine + " rows: pk=" + pks[n]);
                results.addAll(oneLine);
            }
            log.info("Query returned " + results.size() + " rows in " + 
                    (System.currentTimeMillis() - start) + "ms");
            return results;
        }
    }
    
    public void insert(E[] toBeInserted) {
        ensureInitialization(null);
        if ((toBeInserted == null) || (toBeInserted.length == 0)) {
            return;
        }
        
        // Assign primary key values if not assigned
        if (!isUseAutonumberColumns() && (this.pkFields != null) && (this.pkFields.length == 1)) {
            List<E> needPKs = null;
            for (int n = 0; n < toBeInserted.length; n++) {
                try {
                    if (ReflectionUtils.getFieldValueByGetter(toBeInserted[n], 
                            this.pkFields[0]) == null) {
                        if (needPKs == null) {
                            needPKs = new ArrayList<E>();
                        }
                        needPKs.add(toBeInserted[n]);
                    }
                } catch (Throwable err) {
                    throw new RuntimeException("No getter on object: " + 
                            this.valueObjectClass.getName() + " for " +
                            "member variable " + this.pkFields[0] + " on row: " + n, err);
                }
            }
            
            if (needPKs != null) {
                Object pks[] = allocatePrimaryKeyIds(needPKs.size());
                int n = 0;
                for (Iterator<E> i = needPKs.iterator(); i.hasNext(); n++) {
                    try {
                        assignPKToValueObject(i.next(), pks[n]);
                    } catch (Throwable err) {
                        throw new RuntimeException("No setter on object: " + 
                                this.valueObjectClass.getName() + " for " +
                                "member variable " + this.pkFields[0] + " row=" + n +
                                " value=" + pks[n] + (pks[n] != null ? 
                                        " type=" + pks[n].getClass().getName() : ""), err);
                    }
                }
            }
        }
        
        if (toBeInserted[0] instanceof Validateable) {
            List<ValidationFailure> errors = new ArrayList<ValidationFailure>();
            for (int n = 0; n < toBeInserted.length; n++) {
                ((Validateable) toBeInserted[n]).validate(errors);
                if (errors.size() > 0) {
                    // Filter out errors related to pk fields ?
                    throw new RuntimeException("Error validating item " + n + 
                            " of " + toBeInserted.length + " for insert: " + 
                            toBeInserted[n] + " errors: " + errors);
                }
            }
            log.debug(toBeInserted.length + " valueobject(s) validated for insert");
        }
        
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(getDataSource());

        if (this.insertListeners != null) {
            for (DAOInsertListener<E> listener : this.insertListeners) {
                if (!listener.preInsert(toBeInserted)) {
                    return;
                }
            }
        }
        
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO ").append(this.tableName).append(" (");
        for (int n = 0; n < this.columnNames.length; n++) {
            if (n > 0) {
                sql.append(", ");
            }
            sql.append(this.columnNames[n]);
        }
        sql.append(") VALUES (");
        for (int n = 0; n < this.fieldNames.length; n++) {
            if (n > 0) {
                sql.append(", ");
            }
            sql.append(':').append(this.fieldNames[n]);
        }
        sql.append(")");
        
        for (int n = 0; n < toBeInserted.length; n++) {
            if (toBeInserted[n] != null) {
                log.info("Issuing SQL: " + sql.toString());
                if (isUseAutonumberColumns() && (this.pkFields != null)) {
                    KeyHolder keyHolder = new GeneratedKeyHolder();
                    template.update(sql.toString(), 
                            new ValueObjectSqlParameterSource(toBeInserted[n], 
                                    this.nullStringsToEmpty, this.emptyStringsToNull),
                            keyHolder);
                    Map<?,?> generatedKeys = keyHolder.getKeys();
                    int index = 0;
                    for (Iterator<?> i = generatedKeys.keySet().iterator(); i.hasNext(); index++) {
                        String fieldName = (String) i.next();
                        try {
                            ReflectionUtils.setFieldValueBySetter(toBeInserted[n], 
                                    this.pkFields[index], 
                                    generatedKeys.get(fieldName));
                        } catch (Throwable err) {
                            throw new RuntimeException("Error setting generated keys onto " +
                                    "the bean: fieldName=" + fieldName + " value=" + 
                                    generatedKeys.get(fieldName), err);
                        }
                    }
                } else {
                    template.update(sql.toString(), 
                            new ValueObjectSqlParameterSource(toBeInserted[n], 
                                    this.nullStringsToEmpty, this.emptyStringsToNull));
                }
            }
        }
        if (this.insertListeners != null) {
            for (DAOInsertListener<E> listener : this.insertListeners) {
                listener.postInsert(toBeInserted);
            }
        }
    }

    protected void assignPKToValueObject(Object valueobject, Object pk) throws 
            NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        if ((this.pkFields.length > 1) && (pk instanceof Object[])) {
            Object arr[] = (Object []) pk;
            for (int n = 0; n < this.pkFields.length; n++) {
                ReflectionUtils.setFieldValueBySetter(valueobject, this.pkFields[n], arr[n]);
            }
        } else {
            ReflectionUtils.setFieldValueBySetter(valueobject, this.pkFields[0], pk);
        }
    }
    
    public void delete(E[] toBeDeleted) {
        ensureInitialization(null);
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(getDataSource());
        
        StringBuilder sql = new StringBuilder();
        sql.append("DELETE FROM ").append(this.tableName).append(" WHERE ");
        addPKWhereClauseSQLNamed(sql);
        for (int n = 0; n < toBeDeleted.length; n++) {
            if (toBeDeleted[n] != null) {
                boolean doDelete = true;
                if (this.deleteListeners != null) {
                    for (DAODeleteListener<E> listener : this.deleteListeners) {
                        if (!listener.preDelete(toBeDeleted[n])) {
                            doDelete = false;
                        }
                    }
                }
                if (doDelete) {
                    log.info("Issuing SQL: " + sql.toString());
                    template.update(sql.toString(), 
                            new ValueObjectSqlParameterSource(toBeDeleted[n], 
                                    this.nullStringsToEmpty, this.emptyStringsToNull));
                    if (this.deleteListeners != null) {
                        for (DAODeleteListener<E> listener : this.deleteListeners) {
                            listener.postDelete(toBeDeleted[n]);
                        }
                    }
                }
            }
        }
    }

    public int update(E[] toBeUpdated) {
        ensureInitialization(null);
        if ((toBeUpdated == null) || (toBeUpdated.length == 0)) {
            return 0;
        }
        
        if (toBeUpdated[0] instanceof Validateable) {
            List<ValidationFailure> errors = new ArrayList<ValidationFailure>();
            for (int n = 0; n < toBeUpdated.length; n++) {
                ((Validateable) toBeUpdated[n]).validate(errors);
                if (errors.size() > 0) {
                    throw new RuntimeException("Error validating item " + n + 
                            " of " + toBeUpdated.length + " for update: " + 
                            toBeUpdated[n] + " errors: " + errors);
                }
            }
            log.debug(toBeUpdated.length + " valueobject(s) validated for insert");
        }

        if (this.updateListeners != null) {
            for (DAOUpdateListener<E> listener : this.updateListeners) {
                if (!listener.preUpdate(toBeUpdated)) {
                    return 0;
                }
            }
        }
        
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(getDataSource());
        Set<String> pkFieldSet = new HashSet<String>(Arrays.asList(this.pkFields));
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE ").append(this.tableName).append(" SET ");
        boolean firstTermAdded = false;
        for (int n = 0; n < this.fieldNames.length; n++) {
            if (!pkFieldSet.contains(this.fieldNames[n])) {
                if (firstTermAdded) {
                    sql.append(", ");
                } else {
                    firstTermAdded = true;
                }
                sql.append(this.columnNames[n]);
                sql.append(" = :");
                sql.append(this.fieldNames[n]);
            }
        }
        sql.append(" WHERE ");
        addPKWhereClauseSQLNamed(sql);
        
        int updatedRows = 0;
        for (int n = 0; n < toBeUpdated.length; n++) {
            if (toBeUpdated[n] != null) {
                log.info("Issuing SQL: " + sql.toString());
                updatedRows += template.update(sql.toString(), 
                        new ValueObjectSqlParameterSource(toBeUpdated[n], 
                                this.nullStringsToEmpty, this.emptyStringsToNull));
            }
        }

        if (this.updateListeners != null) {
            for (DAOUpdateListener<E> listener : this.updateListeners) {
                listener.postUpdate(toBeUpdated);
            }
        }
        return updatedRows;        
    }
    
    protected void addPKWhereClauseSQL(StringBuilder sql) {
        for (int n = 0; n < this.pkFields.length; n++) {
            if (n > 0) {
                sql.append(" AND ");
            }
            String mapped = lookupAliasDotColumnName(this.pkFields[n], null, null);
            sql.append(mapped).append(" = ?");
        }
    }
    
    protected void addPKWhereClauseSQLNamed(StringBuilder sql) {
        for (int n = 0; n < this.pkFields.length; n++) {
            if (n > 0) {
                sql.append(" AND ");
            }
            String mapped = lookupAliasDotColumnName(this.pkFields[n], null, null);
            sql.append(mapped).append(" = :").append(this.pkFields[n]);
        }
    }

    @SuppressWarnings("unchecked")
    protected void buildPKParameterSource(Object pk, List<Object> params) {
        if (this.pkFields.length == 1) {
            params.add(pk);
        } else if (pk instanceof Object[]) {
            Object pkArray[] = (Object []) pk;
            params.addAll(Arrays.asList(pkArray));
        } else if (pk instanceof Map) {
            Map<String,Object> pkMap = (Map<String,Object>) pk;
            for (int n = 0; n < this.pkFields.length; n++) {
                params.add(pkMap.get(this.pkFields[n]));
            }
        } else if (pk instanceof Collection) {
            params.addAll((Collection<?>) pk);
        } else {
            throw new RuntimeException("Unknown pk object type: " + pk + " (" + pk.getClass().getName() + ")");
        }
    }
    
    /**
     * Call this to force a rollback on the current transaction. Note - this will cause
     * any further operations in the current request to be rolled back at the end of 
     * the request.
     */
    public void forceRollback() {
        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
    }
    
    /**
     * Forces an immediate commit of the the current transaction if it has not already
     * been marked for rollback.
     */
    public void forceCommit() {
        if (TransactionAspectSupport.currentTransactionStatus().isRollbackOnly()) {
            throw new RuntimeException("Can't force commit - this transaction is " +
                    "already marked as rollback-only");
        } else if (TransactionAspectSupport.currentTransactionStatus().isNewTransaction()) {
            log.warn("No transaction in progress, skipping forced commit");
        } else {
            try {
                DataSourceUtils.getConnection(this.dataSource).commit();
            } catch (SQLException err) {
                throw new RuntimeException("Error during forced commit", err);
            }
        }
    }
    
    public Object[] allocatePrimaryKeyIds(int count) {
        ensureInitialization(null);
        Object sequenceValues[] = new Object[count];
        if (!isUseAutonumberColumns() && (this.pkSequenceName != null)) {
            String sql = getPKSequenceSQL();
            Object EMPTY_OBJ[] = new Object[0];
            int EMPTY_INT[] = new int[0];
            for (int n = 0; n < count; n++) {
                JdbcTemplate template = new JdbcTemplate(getDataSource());
                template.setMaxRows(1);
                template.setFetchSize(1);
                log.info("Issuing SQL: " + sql);
                sequenceValues[n] = template.query(sql, EMPTY_OBJ, EMPTY_INT, 
                        new ResultSetExtractor() {
                                public Object extractData(ResultSet resultset) throws 
                                        SQLException, DataAccessException {
                                    Object val = null;
                                    if (resultset.next()) {
                                        try {
                                            val = resultset.getObject(1);

// 2015.02.19 H.Mizuno 追加 start
// MySQL で、sequenceNextvalExpression　プロパティにファンクションによる代替シーケンスを設定すると、
// 文字列型の戻り値であってもバイト配列が復帰されてしまう。
// 通常、バイト配列をキー値として使用する事は無いので、バイト配列の場合は文字列に変換する。
                                            if (val instanceof byte[]){
                                           		val = new String((byte[])val);
                                            }
// 2015.02.19 H.Mizuno 追加 end

                                        } catch (SQLException err) {
                                            val = resultset.getString(1);
                                        }
                                    }
                                    return val;
                                }
                        });
            }
        }
        return sequenceValues;
    }
    
    public void deleteByPK(Object pks[]) {
        ensureInitialization(null);
        if (pks == null) {
            return;
        }

        if (this.deleteListeners != null) {
            for (DAODeleteListener<E> listener : this.deleteListeners) {
                if (!listener.preDeleteByPK(pks)) {
                    return;
                }
            }
        }
        JdbcTemplate template = new JdbcTemplate(getDataSource());
        StringBuilder sql = new StringBuilder();
        sql.append("DELETE FROM ").append(this.tableName).append(" WHERE ");
        if ((this.pkFields.length == 1) && (pks.length > 1) && 
                (pks.length < getInSearchThreshold())) {
            // do in clause
            String pkColumn = lookupAliasDotColumnName(this.pkFields[0], null, null);
            sql.append(pkColumn).append(" IN (");
            for (int n = 0; n < pks.length; n++) {
                if (n > 0) {
                    sql.append(", ");
                }
                sql.append("?");
            }
            sql.append(")");
            DAOUtils.dumpSQL(sql.toString(), Arrays.asList(pks));
            int rows = template.update(sql.toString(), pks);
            log.info("Query deleted " + rows + " row(s)");
        } else {
            // do row by row select
            addPKWhereClauseSQL(sql);
            List<Object> params = new ArrayList<Object>();
            for (int n = 0; n < pks.length; n++) {
                params.clear();
                buildPKParameterSource(pks[n], params);
                DAOUtils.dumpSQL(sql.toString(), params);
                int rows = template.update(sql.toString(), params.toArray());
                log.info("Query deleted " + rows + " row(s): pk=" + pks[n]);
            }
        }

        if (this.deleteListeners != null) {
            for (DAODeleteListener<E> listener : this.deleteListeners) {
                listener.postDeleteByPK(pks);
            }
        }
    }
    
    public void deleteByFilter(DAOCriteria criteria) {
        ensureInitialization(null);
        criteria = prepareCriteria(criteria, null);
        if (criteria == null) {
            throw new RuntimeException("Can't deleteByFilter with a null criteria");
        }


        if (this.deleteListeners != null) {
            for (DAODeleteListener<E> listener : this.deleteListeners) {
                if (!listener.preDeleteByFilter(criteria)) {
                    return;
                }
            }
        }

        StringBuilder sql = new StringBuilder();
        sql.append("DELETE FROM ").append(this.tableName);
        
        // Add restrictions as sql
        List<Object> params = new ArrayList<Object>();
        DAOUtils.addWhereClauses(this, criteria, "", sql, params, this.allowUnknownCriteriaField);
        DAOUtils.dumpSQL(sql.toString(), params);
        JdbcTemplate template = new JdbcTemplate(dataSource);
        if ((criteria != null) && (criteria.getMaxRows() >= 0)) {
            template.setMaxRows(criteria.getMaxRows());
        }

        int rows = template.update(sql.toString(), params.toArray());
        log.info("Query deleted " + rows + " row(s)");

        if (this.deleteListeners != null) {
            for (DAODeleteListener<E> listener : this.deleteListeners) {
                listener.postDeleteByFilter(criteria);
            }
        }
    }
    
    public int updateByCriteria(DAOCriteria criteria, UpdateExpression[] setClauses) {
        if ((setClauses == null) || (setClauses.length == 0)) {
            return 0;
        }
        ensureInitialization(null);
        criteria = prepareCriteria(criteria, null);
        if (criteria == null) {
            log.warn("WARNING: criteria is null, updateByCriteria() will modify all rows !!");
        }

        if (this.updateListeners != null) {
            for (DAOUpdateListener<E> listener : this.updateListeners) {
                if (!listener.preUpdateByCriteria(criteria, setClauses)) {
                    return 0;
                }
            }
        }

        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE ").append(this.tableName).append(" SET ");
        List<Object> params = new ArrayList<Object>();
        int length = sql.length();
        
        // add set clauses
        for (UpdateExpression clause : setClauses) {
            if (length != sql.length()) {
                sql.append(", ");
            }
            sql.append(clause.buildSQL("", this, params));
        }
        
        // Add restrictions as sql
        DAOUtils.addWhereClauses(this, criteria, "", sql, params, this.allowUnknownCriteriaField);
        DAOUtils.dumpSQL(sql.toString(), params);
        JdbcTemplate template = new JdbcTemplate(dataSource);
        if ((criteria != null) && (criteria.getMaxRows() >= 0)) {
            template.setMaxRows(criteria.getMaxRows());
        }

        int rows = template.update(sql.toString(), params.toArray());
        log.info("Query updated " + rows + " row(s)");

        if (this.updateListeners != null) {
            for (DAOUpdateListener<E> listener : this.updateListeners) {
                listener.postUpdateByCriteria(criteria, setClauses);
            }
        }
        return rows;
    }
    
    protected String getPKSequenceSQL() {
        return this.sequenceNextvalExpression.replace(
                "###pkSequenceName###", this.pkSequenceName);
    }
}
