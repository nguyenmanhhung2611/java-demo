/*
 * Copyright (C) 2006 Transcosmos INC.
 * All Rights Reserved.
 */
package jp.co.transcosmos.dm3.dao;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;


/**
 * Joins two DAO instances together to build an SQL join style query. It supports 
 * INNER/LEFT/RIGHT JOIN syntax, but only between two DAOs that implement the 
 * SQLClauseBuilder interface. This DAO is itself an SQLClauseBuilder implementation,
 * so these joins can be nested inside each other (i.e. A JOIN (B JOIN C) is possible,
 * as long as it is a valid SQL join).
 * <p>
 * Each joinable DAO has an alias supplied, which is used to separate the output results
 * from each component DAO inside the JoinResult object. The type of any select query 
 * results is List&lt;JoinResult&gt;, and each JoinResult represents a single row in the output.
 * The JoinResult's getItems() call returns a map, with a single key/valueobject pair for
 * each component dao in the join. Thus, if the join is (A JOIN (B JOIN C)) as above, then 
 * each JoinResult's getItems() map will have 3 key/valueobject pairs: A, B and C.
 * 
 * @author <a href="mailto:rick@knowleses.org">Rick Knowles</a>
 * @version $Id: JoinDAO.java,v 1.18 2012/08/01 09:28:36 tanaka Exp $
 * @see JoinResult, JoinCondition, SQLClauseBuilder
 */
public class JoinDAO implements DAO<JoinResult>, SQLClauseBuilder, SubQueryable, HasDataSource {
    private static final Log log = LogFactory.getLog(JoinDAO.class);
    
    private DataSource dataSource;
    private SQLClauseBuilder joinable1;
    private SQLClauseBuilder joinable2;
    private String alias1 = "dao1";
    private String alias2 = "dao2";
    private Integer joinType;
    private OnClause conditions[];
    private boolean allowUnknownCriteriaField;
    private boolean useCountQueryForMaxRows;
    private boolean useHashMapForResults;
    private boolean nullResultIfAllFieldsAreNull;

    /**
     * Set the first DAO in the join
     */
    public void setJoinable1(SQLClauseBuilder pJoinable1) {
        this.joinable1 = pJoinable1;
    }
    /**
     * Set the second DAO in the join
     */
    public void setJoinable2(SQLClauseBuilder pJoinable2) {
        this.joinable2 = pJoinable2;
    }
    /**
     * Set the join condition descriptors
     */
    public void setConditions(OnClause conditions[]) {
        this.conditions = conditions;
    }
    /**
     * Set the join condition descriptors
     */
    public void setCondition(OnClause condition) {
        setConditions(new OnClause[] {condition});
    }
    /**
     * Set the join condition descriptors
     */
    public void setConditionList(List<OnClause> conditions) {
        setConditions(conditions.toArray(new OnClause[conditions.size()]));
    }
    public void setDataSource(DataSource pDataSource) {
        this.dataSource = pDataSource;
    }
    public void setAllowUnknownCriteriaField(boolean pAllowUnknownCriteriaField) {
        this.allowUnknownCriteriaField = pAllowUnknownCriteriaField;
    }
    /**
     * If true, when paging results issue a count query to get the max rows instead
     * of iterating to the end. This is a performance optimisation (default is false). 
     */
    public void setUseCountQueryForMaxRows(boolean pUseCountQueryForMaxRows) {
        this.useCountQueryForMaxRows = pUseCountQueryForMaxRows;
    }    
    /**
     * Set true if the join is especially large (default is false, for performance reasons)
     */
    public void setUseHashMapForResults(boolean pUseHashMapForResults) {
        this.useHashMapForResults = pUseHashMapForResults;
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
    protected int getJoinTypeIndex() {
        if ((this.joinType == null) && (this.conditions != null)) {
            this.joinType = new Integer(DAOUtils.getJoinType(this.conditions));
        }
        return this.joinType.intValue();
    }
    public String getAlias1() {
        return this.alias1;
    }
    public String getAlias2() {
        return this.alias2;
    }

    /**
     * Set the alias for the first DAO in the join
     */
    public void setAlias1(String pAlias1) {
        this.alias1 = pAlias1;
    }
    /**
     * Set the alias for the second DAO in the join
     */
    public void setAlias2(String pAlias2) {
        this.alias2 = pAlias2;
    }

    /**
     * Set the join type. Supports "INNER", "LEFT" or "RIGHT"
     */
    public void setJoinType(String pJoinType) {
        if (pJoinType != null) {
            this.joinType = new Integer(JoinCondition.lookupJoinType(pJoinType));
        }
    }
    
    public DataSource getDataSource() {
        if (this.dataSource != null) {
            return this.dataSource;
        }
        DataSource ds = null;
        if (this.joinable1 instanceof HasDataSource) {
            ds = ((HasDataSource) this.joinable1).getDataSource();
        }
        if ((ds == null) && this.joinable2 instanceof HasDataSource) {
            ds = ((HasDataSource) this.joinable2).getDataSource();
        }
        return ds;
    }

    public int getRowCountMatchingFilter(DAOCriteria criteria) {
        criteria = prepareCriteria(criteria, null);
        return getRowCountMatchingFilterInternal(criteria);
    }
    
    protected int getRowCountMatchingFilterInternal(DAOCriteria criteria) {
        long start = System.currentTimeMillis();
        int rowCount = 0;
        JdbcTemplate template = new JdbcTemplate(getDataSource());
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT count(1) FROM ");
        List<Object> params = new ArrayList<Object>();
        addFromClause(sql, "", params);
        DAOUtils.addWhereClauses(this, criteria, "", sql, params, this.allowUnknownCriteriaField);
        DAOUtils.dumpSQL(sql.toString(), params);
        rowCount = template.queryForInt(sql.toString(), params.toArray());
        log.info("Row count query found " + rowCount + " rows in " + 
                (System.currentTimeMillis() - start) + "ms");
        return rowCount;
    }
    
    public DAOCriteria buildImplicitCriteria() {
    	DAOCriteria implicit1 = this.joinable1.buildImplicitCriteria();
        DAOCriteria implicit2 = this.joinable2.buildImplicitCriteria();
    	if (implicit2 == null) {
    		return implicit1;
    	} else if (implicit1 == null) {
    		return implicit2;
    	} else {
    		DAOCriteria top = new DAOCriteria();
    		top.addSubCriteria(implicit1);
    		top.addSubCriteria(implicit2);
    		return top;
    	}
	}
    
    @SuppressWarnings("unchecked")
    public List<JoinResult> selectByFilter(DAOCriteria criteria) {
        long start = System.currentTimeMillis();
        
        criteria = prepareCriteria(criteria, null);
        List<Object> params = new ArrayList<Object>();
        
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ");
        addSelectClause(sql, "", null);
        sql.append(" FROM ");
        addFromClause(sql, "", params);
        
        // Add restrictions as sql
        DAOUtils.addWhereClauses(this, criteria, "", sql, params, 
                this.allowUnknownCriteriaField);
        DAOUtils.addOrderByClauses(this, criteria, "", sql, 
                this.allowUnknownCriteriaField);
        DAOUtils.dumpSQL(sql.toString(), params);
        JdbcTemplate template = new JdbcTemplate(getDataSource());
        if ((criteria != null) && (criteria.getMaxRows() >= 0)) {
            template.setMaxRows(criteria.getMaxRows());
        }
        
        FragmentPreparedStatementCreator psc = new FragmentPreparedStatementCreator(
                sql.toString(), params);
        FragmentResultSetExtractor<JoinResult> rse = new FragmentResultSetExtractor<JoinResult>(
                getRowMapper("", null, this.nullResultIfAllFieldsAreNull), 
                criteria);
        if (rse.isFragmentMode() && this.useCountQueryForMaxRows) {
            rse.setRowCountHint(getRowCountMatchingFilterInternal(criteria));
        }
        
        List<JoinResult> results = (List<JoinResult>) template.query(psc, psc, rse);
        log.info("SelectByFilter query found " + results.size() + " rows in " + 
                (System.currentTimeMillis() - start) + "ms");
        return results;
    }

    /**
     * Builds the select clause for the joined tables
     */
    public void addSelectClause(StringBuilder sql, String alias, String[] filterFields) {
        int startLength = sql.length();
        this.joinable1.addSelectClause(sql, this.alias1, filterFields);
        if (startLength != sql.length()) {
            sql.append(", ");
        }
        this.joinable2.addSelectClause(sql, this.alias2, filterFields);
    }
    
    /**
     * Builds the from clause for the joined tables
     */
    public void addFromClause(StringBuilder sql, String alias, List<Object> params) {
        int startLength = sql.length();
        if (this.joinable1 != null) {
            this.joinable1.addFromClause(sql, this.alias1, params);
        }
        if (this.joinable2 == null) {
            return;
        } else if (startLength == sql.length()) {
            this.joinable2.addFromClause(sql, this.alias2, params);
            return;
        } else {
            int joinType = getJoinTypeIndex();
            if (joinType == JoinCondition.RIGHT_JOIN) {
                sql.append(" RIGHT JOIN ");
            } else if (joinType == JoinCondition.LEFT_JOIN) {
                sql.append(" LEFT JOIN ");
            } else {
                sql.append(" INNER JOIN ");
            }
            this.joinable2.addFromClause(sql, this.alias2, params);
            String joinOnClause = DAOUtils.buildJoinOnCriteria(
                    this.alias1, this.joinable1, 
                    this.alias2, this.joinable2, 
                    this.conditions, params, this, 
                    this.allowUnknownCriteriaField).trim();
            if (!joinOnClause.equals("")) {
                sql.append(" ON ").append(joinOnClause);
            }
        }
    }
    
    /**
     * Builds the order-by clause for the joined tables
     */
    public RowMapper getRowMapper(String prefix, String filterFields[], boolean isLeftJoinOutsideTable) {
//        int joinType = DAOUtils.getJoinType(this.conditions);
        
        RowMapper mappers[] = new RowMapper[] {
                this.joinable1.getRowMapper(this.alias1, filterFields,
                        this.nullResultIfAllFieldsAreNull),
//                 || isLeftJoinOutsideTable || (joinType == JoinCondition.RIGHT_JOIN)),
                this.joinable2.getRowMapper(this.alias2, filterFields,
                        this.nullResultIfAllFieldsAreNull)
//                 || isLeftJoinOutsideTable || (joinType == JoinCondition.LEFT_JOIN))
        };
        
        return new JoinRowMapper(new String[] {this.alias1, this.alias2}, 
                mappers, 2, this.useHashMapForResults);
    }
    
    public DAOCriteria prepareCriteria(DAOCriteria criteria, String thisAlias) {
        criteria = this.joinable1.prepareCriteria(criteria, this.alias1);
        return this.joinable2.prepareCriteria(criteria, this.alias2);
    }

    /**
     * Builds the order-by clause for the joined tables
     */
    public boolean addOneOrderByClause(StringBuilder sql, String alias, DAOCriteriaOrderByClause orderBy) {
        if (this.joinable1.addOneOrderByClause(sql, this.alias1, orderBy)) {
            return true;
        } else if (this.joinable2.addOneOrderByClause(sql, this.alias2, orderBy)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Builds the where clause for the joined tables
     */
    public boolean addOneWhereClause(StringBuilder sql, String alias, 
            DAOCriteriaWhereClause where, List<Object> params) {
        if (this.joinable1.addOneWhereClause(sql, this.alias1, where, params)) {
            return true;
        } else if (this.joinable2.addOneWhereClause(sql, this.alias2, where, params)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Returns a mapping of a fieldname to the 
     */
    public String lookupAliasDotColumnName(String fieldName, String fieldAlias, String thisAlias) {
        String out = this.joinable1.lookupAliasDotColumnName(fieldName, fieldAlias, this.alias1);
        if (out == null) {
            out = this.joinable2.lookupAliasDotColumnName(fieldName, fieldAlias, this.alias2);
        }
        return out;
    }
    
    @Override
    public String lookupLabelledName(String fieldName, String fieldAlias, String thisAlias) {
        String out = this.joinable1.lookupLabelledName(fieldName, fieldAlias, this.alias1);
        if (out == null) {
            out = this.joinable2.lookupLabelledName(fieldName, fieldAlias, this.alias2);
        }
        return out;
    }

    public void buildSubQuerySQL(StringBuilder sql, DAOCriteria subCriteria, 
            List<Object> params, String[] selectFields, String[] selectFieldAliases, String thisAlias) {
        subCriteria = prepareCriteria(subCriteria, null);
        sql.append("SELECT ");
        if (selectFields == null) {
            sql.append("1");
        } else {
            for (int n = 0; n < selectFields.length; n++) {
                // find a matching dao, then label the field with that dao and it's alias
                
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
        addFromClause(sql, "", params);
        
        // Add restrictions as sql
        if (subCriteria != null) {
            DAOUtils.addWhereClauses(this, subCriteria, "", sql, params, 
                    this.allowUnknownCriteriaField);
        }
    }
    
    // Not implemented, because this DAO is read-only
    public JoinResult selectByPK(Object pPk) {
        throw new RuntimeException("Can't selectByPK from a JoinDAO");
    }
    public List<JoinResult> selectByPK(Object[] pPks) {
        throw new RuntimeException("Can't selectByPK from a JoinDAO");
    }    
        
    // ideally these shouldn't be implemented - this is a backwards compatibility fix
    public Object[] allocatePrimaryKeyIds(int count) {
        throw new RuntimeException("Can't allocatePrimaryKeyIds from a JoinDAO");
    }
    public void insert(JoinResult[] pArg0) {
        throw new RuntimeException("Can't insert from a JoinDAO");
    }
    public int update(JoinResult[] pArg0) {
        throw new RuntimeException("Can't update from a JoinDAO");
    }
    public void delete(JoinResult[] pArg0) {
        throw new RuntimeException("Can't delete from a JoinDAO");
    }
    public void deleteByPK(Object[] pArg0) {
        throw new RuntimeException("Can't deleteByPK from a JoinDAO");
    }
    public void deleteByFilter(DAOCriteria criteria) {
        throw new RuntimeException("Can't deleteByFilter from a JoinDAO");
    }
    public int updateByCriteria(DAOCriteria criteria, UpdateExpression[] setClauses) {
        throw new RuntimeException("Can't updateByCriteria from a JoinDAO");
    }
}
