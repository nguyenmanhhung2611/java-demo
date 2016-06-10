/*
 * Copyright (C) 2006 Transcosmos INC.
 * All Rights Reserved.
 */
package jp.co.transcosmos.dm3.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

/**
 * Applies a fixed filter to another DAO. Used for making WHERE-only views that
 * acts as wrappers around other DAOs.
 * 
 * Ideally this should write itself as a sub query if the DB supports it (for optimisation
 * sake), but for now it's a simple where clause.
 * 
 * @author <a href="mailto:rick@knowleses.org">Rick Knowles</a>
 * @version $Id: FilterDAO.java,v 1.10 2007/11/30 03:32:25 abe Exp $
 */
public class FilterDAO<E> implements DAO<E>, SQLClauseBuilder, SubQueryable, HasDataSource {
    private static final Log log = LogFactory.getLog(FilterDAO.class);

    private DataSource dataSource;
    private DAO<E> filteredDAO;
    private DAOCriteria filterCriteria;
    private boolean allowUnknownCriteriaField;
    private boolean nullResultIfAllFieldsAreNull;
    private boolean useCountQueryForMaxRows;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    
    public void setFilteredDAO(DAO<E> pFilteredDAO) {
        this.filteredDAO = pFilteredDAO;
    }

    public void setFilterCriteria(DAOCriteria pFilterCriteria) {
        this.filterCriteria = pFilterCriteria;
    }

    public void setFilterClauses(List<DAOCriteriaWhereClause> whereClauses) {
        this.filterCriteria = new DAOCriteria();
        for (Iterator<DAOCriteriaWhereClause> i = whereClauses.iterator(); i.hasNext(); ) {
            this.filterCriteria.addWhereClause(i.next());
        }
    }

    public void setOrFilterClauses(List<DAOCriteriaWhereClause> whereClauses) {
        this.filterCriteria = new OrCriteria();
        for (Iterator<DAOCriteriaWhereClause> i = whereClauses.iterator(); i.hasNext(); ) {
            this.filterCriteria.addWhereClause(i.next());
        }
    }

    public void setAllowUnknownCriteriaField(boolean pAllowUnknownCriteriaField) {
        this.allowUnknownCriteriaField = pAllowUnknownCriteriaField;
    }
    
    public void setUseCountQueryForMaxRows(boolean pUseCountQueryForMaxRows) {
        this.useCountQueryForMaxRows = pUseCountQueryForMaxRows;
    }    
    
    public void setNullResultIfAllFieldsAreNull(boolean pNullResultIfAllFieldsAreNull) {
        this.nullResultIfAllFieldsAreNull = pNullResultIfAllFieldsAreNull;
    }

    public DataSource getDataSource() {
        if (this.dataSource != null) {
            return this.dataSource;
        } else if (this.filteredDAO instanceof HasDataSource) {
            return ((HasDataSource) this.filteredDAO).getDataSource();
        } else {
            return null;
        }
    }
    
    // DAO methods

    public int getRowCountMatchingFilter(DAOCriteria criteria) {
        criteria = prepareCriteria(criteria, null);
        return getRowCountMatchingFilterInternal(criteria);
    }
    
    public int getRowCountMatchingFilterInternal(DAOCriteria criteria) {
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

    @SuppressWarnings("unchecked")
    public List<E> selectByFilter(DAOCriteria criteria) {
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
                getRowMapper("", null, this.nullResultIfAllFieldsAreNull), criteria);
        if (rse.isFragmentMode() && this.useCountQueryForMaxRows) {
            rse.setRowCountHint(getRowCountMatchingFilterInternal(criteria));
        }
        
        List<E> results = (List<E>) template.query(psc, psc, rse);
        log.info("SelectByFilter query found " + results.size() + " rows in " + 
                (System.currentTimeMillis() - start) + "ms");
        return results;
    }
    
    // subqueryable interface methods
    
    public void buildSubQuerySQL(StringBuilder sql, DAOCriteria subCriteria, 
            List<Object> params, String[] selectFields, String[] selectFieldAliases, 
            String thisAlias) {
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
        addFromClause(sql, "", params);
        
        // Add restrictions as sql
        if (subCriteria != null) {
            DAOUtils.addWhereClauses(this, subCriteria, "", sql, params, 
                    this.allowUnknownCriteriaField);
            DAOUtils.addOrderByClauses(this, subCriteria, "", sql, 
                    this.allowUnknownCriteriaField);
        }
    }

    // joinable interface methods
    
    public DAOCriteria buildImplicitCriteria() {
    	DAOCriteria implicit = null;
    	if (this.filteredDAO instanceof SQLClauseBuilder) {
    		implicit = ((SQLClauseBuilder) this.filteredDAO).buildImplicitCriteria();
    	}
    	if (this.filterCriteria == null) {
    		return implicit;
    	} else if (implicit == null) {
    		return this.filterCriteria;
    	} else {
    		DAOCriteria top = new DAOCriteria();
    		top.addSubCriteria(this.filterCriteria);
    		top.addSubCriteria(implicit);
    		return top;
    	}
	}
    
    public void addSelectClause(StringBuilder sql, String alias, String filterFields[]) {
        ((SQLClauseBuilder) this.filteredDAO).addSelectClause(sql, alias, filterFields);
    }

    public void addFromClause(StringBuilder sql, String alias, List<Object> params) {
        ((SQLClauseBuilder) this.filteredDAO).addFromClause(sql, alias, params);
    }

    public boolean addOneWhereClause(StringBuilder sql, String alias, 
            DAOCriteriaWhereClause where, List<Object> params) {
        return ((SQLClauseBuilder) this.filteredDAO).addOneWhereClause(sql, alias, where, params);
    }

    public boolean addOneOrderByClause(StringBuilder sql, String alias, 
            DAOCriteriaOrderByClause orderBy) {
        return ((SQLClauseBuilder) this.filteredDAO).addOneOrderByClause(sql, alias, orderBy);
    }

    public RowMapper getRowMapper(String fieldPrefix, String filterFields[], boolean isLeftJoinOutsideTable) {
        return ((SQLClauseBuilder) this.filteredDAO).getRowMapper(fieldPrefix, filterFields, isLeftJoinOutsideTable);
    }

    public String lookupAliasDotColumnName(String fieldName, String fieldAlias, String thisAlias) {
        return ((SQLClauseBuilder) this.filteredDAO).lookupAliasDotColumnName(fieldName, fieldAlias, thisAlias);
    }

    public String lookupLabelledName(String fieldName, String fieldAlias, String thisAlias) {
        return ((SQLClauseBuilder) this.filteredDAO).lookupLabelledName(fieldName, fieldAlias, thisAlias);
    }
    
    public DAOCriteria prepareCriteria(DAOCriteria criteria, String thisAlias) {
        return ((SQLClauseBuilder) this.filteredDAO).prepareCriteria(criteria, thisAlias);
    }

    public void deleteByFilter(DAOCriteria criteria) {
        if (criteria == null) {
            throw new RuntimeException("Can't deleteByFilter with a null criteria");
        }
        DAOCriteria anded = new DAOCriteria();
        anded.addSubCriteria(this.filterCriteria);
        anded.addSubCriteria(criteria);
        this.filteredDAO.deleteByFilter(anded);
    }
    
    public int updateByCriteria(DAOCriteria criteria, UpdateExpression[] setClauses) {
        if ((setClauses == null) || (setClauses.length == 0)) {
            return 0;
        } else if (criteria == null) {
            log.warn("WARNING: criteria is null, updateByCriteria() will modify all rows !!");
        }
        DAOCriteria anded = new DAOCriteria();
        anded.addSubCriteria(this.filterCriteria);
        anded.addSubCriteria(criteria);
        return this.filteredDAO.updateByCriteria(anded, setClauses);
    }

    // read-write dao methods - not implemented

    public List<E> selectByPK(Object[] pks) {
        throw new RuntimeException("selectByPK(keys) not implemented in filterDAO");
    }

    public E selectByPK(Object pk) {
        throw new RuntimeException("selectByPK(key) not implemented in filterDAO");
    }
    
    public void insert(E[] toBeInserted) {
        throw new RuntimeException("insert() not implemented in filterDAO");
    }

    public int update(E[] toBeUpdated) {
        throw new RuntimeException("update() not implemented in filterDAO");      
    }
    
    public Object[] allocatePrimaryKeyIds(int count) {
        throw new RuntimeException("allocatePrimaryKeyIds() not implemented in filterDAO");
    }
    
    public void delete(E[] toBeDeleted) {
        throw new RuntimeException("delete() not implemented in filterDAO");     
    }

    public void deleteByPK(Object[] pks) {
        throw new RuntimeException("deleteByPK() not implemented in filterDAO");     
    }
}
