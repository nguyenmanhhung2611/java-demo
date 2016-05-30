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
 * Allows unions across multiple valueobjects. This is intended to be used so that 
 * DAOs that represent the same valueobject but correspond to split tables (or are
 * otherwise split as filtered views) can be re-assembled together in a single select.
 * 
 * This DAO is different to other DAOs, because it has to support sub-queries to some 
 * extent, so the following must be supported:
 * 
 * SELECT   A.p, A.r, A.s, B.t, B.u, B.v
 * FROM     table1 AS A,
 *          (SELECT C.d, C.e FROM C
 *           UNION ALL 
 *           SELECT D.d, D.e FROM D) AS B
 * WHERE    A.p = 1 
 * AND      B.v = 2
 * ORDER BY B.u
 * 
 * Because of cases like this, we *must* handle the Joinable implementation sections
 * separately from the main DAO methods instead of one just calling the other
 * 
 * e.g. a single union query (or in/exists subquery) would need to specify ORDER-BY as a 
 * column index (since table aliases are unclear), while a nested UNION like the above needs 
 * to know it's external alias in order to be written correctly
 *  
 * @author <a href="mailto:rick_knowles@hotmail.com">Rick Knowles</a>
 * @version $Id: UnionDAO.java,v 1.9 2007/06/13 06:32:03 rick Exp $
 */
public class UnionDAO<E> implements ReadOnlyDAO<E>, SQLClauseBuilder, SubQueryable, HasDataSource {
    private static final Log log = LogFactory.getLog(UnionDAO.class);
    
    private DataSource dataSource;
    private UnionDAOElementDescriptor<E> childElements[];
    private boolean unionAll = false;
    private String selectFields[];
    private boolean allowUnknownCriteriaField;
    private boolean nullResultIfAllFieldsAreNull;
    private boolean useCountQueryForMaxRows;
    
    public UnionDAOElementDescriptor<E>[] getChildElements() {
        return this.childElements;
    }

    public void setChildElements(UnionDAOElementDescriptor<E>[] childElements) {
        this.childElements = childElements;
    }
    
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void setNullResultIfAllFieldsAreNull(boolean pNullResultIfAllFieldsAreNull) {
        this.nullResultIfAllFieldsAreNull = pNullResultIfAllFieldsAreNull;
    }

    public void setUnionAll(boolean unionAll) {
        this.unionAll = unionAll;
    }

    public void setSelectFields(String[] selectFields) {
        this.selectFields = selectFields;
    }
    
    public void setAllowUnknownCriteriaField(boolean allowUnknownCriteriaField) {
		this.allowUnknownCriteriaField = allowUnknownCriteriaField;
	}

	public void setUseCountQueryForMaxRows(boolean useCountQueryForMaxRows) {
		this.useCountQueryForMaxRows = useCountQueryForMaxRows;
	}

	public DataSource getDataSource() {
        if (this.dataSource != null) {
            return this.dataSource;
        }
        DataSource ds = null;
        for (int n = 0; ds == null && n < this.childElements.length; n++) {
            if (this.childElements[n] instanceof HasDataSource) {
                ds = ((HasDataSource) this.childElements[n]).getDataSource();
            }
        }
        return ds;
    }
    
    // dao methods

    public int getRowCountMatchingFilter(DAOCriteria criteria) {
        criteria = this.prepareCriteria(criteria, null);
        return getRowCountMatchingFilterInternal(criteria);
    }
    
    protected int getRowCountMatchingFilterInternal(DAOCriteria criteria) {
        long start = System.currentTimeMillis();
        
        StringBuilder sql = new StringBuilder();
        List<Object> params = new ArrayList<Object>();

        // If no criteria at all, render without outside braces
        sql.append("SELECT count(1) FROM ");
        addFromClause(sql, "", params);
        DAOUtils.addWhereClauses(this, criteria, "", sql, params, this.allowUnknownCriteriaField);        
        DAOUtils.dumpSQL(sql.toString(), params);
        
        JdbcTemplate template = new JdbcTemplate(getDataSource());
        int rowCount = template.queryForInt(sql.toString(), params.toArray());
        log.info("Row count query found " + rowCount + " rows in " + 
                (System.currentTimeMillis() - start) + "ms");
        return rowCount;
    }

    @SuppressWarnings("unchecked")
    public List<E> selectByFilter(DAOCriteria criteria) {
        long start = System.currentTimeMillis();
        criteria = this.prepareCriteria(criteria, null);
        
        StringBuilder sql = new StringBuilder();
        List<Object> params = new ArrayList<Object>();

        // If no criteria at all, render without outside braces
    	DAOCriteria implicit = buildImplicitCriteria();
    	if ((implicit != null) || (criteria != null)) {
            sql.append("SELECT ");
            addSelectClause(sql, "", null);
            sql.append(" FROM ");
            addFromClause(sql, "", params);
            
            // Add restrictions as sql
            DAOUtils.addWhereClauses(this, criteria, "", sql, params, this.allowUnknownCriteriaField);
            DAOUtils.addOrderByClauses(this, criteria, "", sql, this.allowUnknownCriteriaField);
    	} else {
            addSQLUnionPart(sql, this.selectFields, params);
    	}
        
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
        // Filter any fields for aliases that don't correspond to this dao
        if ((selectFieldAliases != null) && !thisAlias.equals("")) {            
            List<String> rewrittenSelectFields = new ArrayList<String>();
            for (int n = 0; n < selectFields.length; n++) {
                if ((selectFieldAliases.length <= n) || 
                        (selectFieldAliases[n] == null) ||
                        selectFieldAliases[n].equalsIgnoreCase(thisAlias)) {
                    rewrittenSelectFields.add(selectFields[n]);
                }
            }
            selectFields = rewrittenSelectFields.toArray(new String[rewrittenSelectFields.size()]);
        }

    	DAOCriteria implicit = buildImplicitCriteria();
    	if ((implicit != null) || (subCriteria != null)) {
            sql.append("SELECT ");
            addSelectClause(sql, "", null);
            sql.append(" FROM ");
            addFromClause(sql, "", params);
            
            // Add restrictions as sql
            DAOUtils.addWhereClauses(this, subCriteria, "", sql, params, this.allowUnknownCriteriaField);
    	} else {
            addSQLUnionPart(sql, this.selectFields, params);
    	}
    }

    private String[] checkFilterFields(String filterFields[]) {
    	if (filterFields == null) {
    		return this.selectFields;
    	} else if (this.selectFields == null) {
    		return filterFields;
    	} else {
        	// if filter fields are supplied, limit them to the ones configured
            List<String> rewrittenFilterFields = new ArrayList<String>();
            for (int n = 0; n < filterFields.length; n++) {
            	// if the field is in the member scope set, allow it
            	boolean found = false;
            	for (int k = 0; k < selectFields.length && !found; k++) {
            		found = selectFields[k].equalsIgnoreCase(filterFields[n]);
            	}
                if (found) {
                	rewrittenFilterFields.add(filterFields[n]);
                }
            }
            return rewrittenFilterFields.toArray(new String[rewrittenFilterFields.size()]);
    	}
    }
    
    protected void addSQLUnionPart(StringBuilder sql, String[] filterFields, List<Object> params) {
    	filterFields = checkFilterFields(filterFields);
        for (int n = 0; n < this.childElements.length; n++) {
        	SQLClauseBuilder childDAO = this.childElements[n].getDao();
        	
            // Add the select (pass in the alias as a way of forcing prefixing with the outside alias)
        	sql.append("SELECT ");
        	childDAO.addSelectClause(sql, "", filterFields);
            
            // Add the from
        	sql.append(" FROM ");
        	childDAO.addFromClause(sql, "", params);
            
            // Add the where (need to allow for the implicit where on the child dao)
            DAOUtils.addWhereClauses(childDAO, null, "", sql, params, 
            		this.allowUnknownCriteriaField);
            
            if (n < this.childElements.length - 1) {
                // add union clause
                sql.append(this.unionAll ? " UNION ALL " : " UNION ");
            }
        }
    }
    
    // joinable interface methods - these are the *external* SQL builders 
    // (see the example in the class javadoc)
    public DAOCriteria buildImplicitCriteria() {
		return null; // deliberately null because external sql builders aren't interested in child dao filters
	}
    
    public void addSelectClause(StringBuilder sql, String alias, String[] filterFields) {
    	// Use the first child to get the field names if no filterfields
    	this.childElements[0].getDao().addSelectClause(sql, alias, filterFields);
    }

    public void addFromClause(StringBuilder sql, String alias, List<Object> params) {
        // this is be interpreted as a sub-query, because joined unions must be implemented that way
        // Write out the full SQL wrapped in brackets without the order by and append an alias on the end
        sql.append('(');
        addSQLUnionPart(sql, null, params);
        sql.append(") ").append(alias);
    }

    public boolean addOneWhereClause(StringBuilder sql, String alias, 
            DAOCriteriaWhereClause where, List<Object> params) {
    	// use the first child to determine the where clause, but label the child as the external alias
    	return this.childElements[0].getDao().addOneWhereClause(sql, alias, where, params);
    }

    public boolean addOneOrderByClause(StringBuilder sql, String alias, 
            DAOCriteriaOrderByClause orderBy) {
    	// use the first child to determine the where clause, but label the child as the external alias
    	return this.childElements[0].getDao().addOneOrderByClause(sql, alias, orderBy);
    }

    public RowMapper getRowMapper(String fieldPrefix, String filterFields[], boolean allowNullFields) {
        // get the fields we are configured to return, then build a row mapper
        return this.childElements[0].getDao().getRowMapper(fieldPrefix, checkFilterFields(filterFields), allowNullFields);
    }

    public String lookupAliasDotColumnName(String fieldName, String fieldAlias, String thisAlias) {
        String found = null;
        for (int n = 0; found == null && n < this.childElements.length; n++) {
            if (this.childElements[n] instanceof SQLClauseBuilder) {
                found = ((SQLClauseBuilder) this.childElements[n]).lookupAliasDotColumnName(
                		fieldName, fieldAlias, thisAlias);
            }
        }
        return found;
    }

    @Override
    public String lookupLabelledName(String fieldName, String fieldAlias, String thisAlias) {
        String found = null;
        for (int n = 0; found == null && n < this.childElements.length; n++) {
            if (this.childElements[n] instanceof SQLClauseBuilder) {
                found = ((SQLClauseBuilder) this.childElements[n]).lookupLabelledName(
                		fieldName, fieldAlias, thisAlias);
            }
        }
        return found;
    }
    
    public DAOCriteria prepareCriteria(DAOCriteria criteria, String thisAlias) {
        for (int n = 0; n < this.childElements.length; n++) {
            if (this.childElements[n] instanceof SQLClauseBuilder) {
                criteria = ((SQLClauseBuilder) this.childElements[n]).prepareCriteria(criteria, null);
            }
        }
        return criteria;
    }

    // not-implemented dao methods
    
    public List<E> selectByPK(Object[] pks) {
        throw new RuntimeException("selectByPK(keys) not implemented in unionDAO");
    }

    public E selectByPK(Object pk) {
        throw new RuntimeException("selectByPK(key) not implemented in unionDAO");
    }

//    public void insert(E[] toBeInserted) {
//        throw new RuntimeException("insert() not implemented in unionDAO");
//    }
//
//    public void update(E[] toBeUpdated) {
//        throw new RuntimeException("update() not implemented in unionDAO");      
//    }
//    
//    public Object[] allocatePrimaryKeyIds(int count) {
//        throw new RuntimeException("allocatePrimaryKeyIds() not implemented in unionDAO");
//    }
//
//    public void delete(E[] toBeDeleted) {
//        throw new RuntimeException("delete() not implemented in unionDAO");
//    }
//
//    public void deleteByFilter(DAOCriteria criteria) {
//        throw new RuntimeException("deleteByFilter() not implemented in unionDAO");
//    }
//
//    public void deleteByPK(Object[] pks) {
//        throw new RuntimeException("deleteByPK() not implemented in unionDAO");
//    }
}
