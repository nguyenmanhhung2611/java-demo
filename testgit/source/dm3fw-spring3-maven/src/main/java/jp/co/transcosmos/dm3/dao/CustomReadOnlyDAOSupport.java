/*
 * Copyright (C) 2006 Transcosmos INC.
 * All Rights Reserved.
 */
package jp.co.transcosmos.dm3.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

/**
 * Extend this to create simple custom DAO classes that can use the paging 
 * functions, etc and otherwise behave exactly like a normal read-only DAO.
 * <p>
 * With this DAO class, you can simply implement just the parts that build the SQL,
 * and then implement the row to valueobject mapping function in the mapRow() function.
 * Overriding the four abstract methods is enough - the support methods in this class 
 * provide the paging limiting function.
 * 
 * @author <a href="mailto:rick_knowles@hotmail.com">Rick Knowles</a>
 * @version $Id: CustomReadOnlyDAOSupport.java,v 1.2 2007/06/13 03:51:36 rick Exp $
 */
public abstract class CustomReadOnlyDAOSupport<T> implements ReadOnlyDAO<T>, 
        ParameterizedRowMapper<T>, HasDataSource {
    private static final Log log = LogFactory.getLog(CustomReadOnlyDAOSupport.class);

    ///////////////////////////////////////////////////////////////////
    
    /**
     * Implement this method to provide the SQL used to perform DAOCriteria based 
     * searches (ReadOnlyDAO.selectByFilter). Add any bind variables as question marks, 
     * and then add an entry to the params list for the value.
     * <p>
     * Note: if you don't need to call selectByFilter(), just return null for this method
     */
    protected abstract String buildSelectByFilterSQL(DAOCriteria criteria, List<Object> params) 
            throws SQLException;

    /**
     * Implement this method to provide the SQL used to perform DAOCriteria based 
     * row count searches (ReadOnlyDAO.getRowCountMatchingFilter). Add any bind variables 
     * as question marks, and then add an entry to the params list for the value.
     * <p>
     * Note: if you don't need to call getRowCountMatchingFilter() or selectByFilter(), 
     * just return null for this method
     */
    protected abstract String buildRowCountSQL(DAOCriteria criteria, List<Object> params) 
            throws SQLException;    

    /**
     * Implement this method to provide the SQL used to perform primary-key based 
     * searches (ReadOnlyDAO.selectByPK). Add any bind variables as question marks, 
     * and then add an entry to the params list for the value.
     * <p>
     * Note: if you don't need to call selectByPK(), just return null for this method
     */
    protected abstract String buildSelectByPKSQL(Object pks[], List<Object> params) 
            throws SQLException;

    /**
     * Implement this method to provide the mapping of one row to one valueobject. In
     * most cases this method will create a new valueobject, then call setXXX() on the
     * valueobject for each field in the resultset. There is no need to call 
     * resultset.next(), as this is called by the support method.
     * <p>
     * Note: This method is almost always required to be implemented.
     */
    public abstract T mapRow(ResultSet resultset, int rowIndex) 
            throws SQLException;
    
    ///////////////////////////////////////////////////////////////////

    private DataSource dataSource;
    private boolean useCountQueryForMaxRows;
    
    public int getRowCountMatchingFilter(DAOCriteria criteria) {
        criteria = prepareCriteria(criteria, null);
        return getRowCountMatchingFilterInternal(criteria);
    }
    
    protected int getRowCountMatchingFilterInternal(DAOCriteria criteria) {
        long start = System.currentTimeMillis();
        int rowCount = 0;
        List<Object> params = new ArrayList<Object>();
        JdbcTemplate template = new JdbcTemplate(getDataSource());
        String sql = null;
        try {
            sql = buildRowCountSQL(criteria, params);
            DAOUtils.dumpSQL(sql, params);
            rowCount = template.queryForInt(sql, params.toArray());
            log.info("Row count query found " + rowCount + " rows in " + 
                    (System.currentTimeMillis() - start) + "ms");
            return rowCount;
        } catch (SQLException err) {
            throw new UncategorizedSQLException("Error generating row count SQL", sql, err);
        }
    }

    @SuppressWarnings("unchecked")
    public List<T> selectByFilter(DAOCriteria criteria) {
        long start = System.currentTimeMillis();
        criteria = prepareCriteria(criteria, null);
        List<Object> params = new ArrayList<Object>();
        String sql = null;
        try {
            sql = buildSelectByFilterSQL(criteria, params);
            DAOUtils.dumpSQL(sql, params);
            JdbcTemplate template = new JdbcTemplate(getDataSource());
            if ((criteria != null) && (criteria.getMaxRows() >= 0)) {
                template.setMaxRows(criteria.getMaxRows());
            }

            FragmentPreparedStatementCreator psc = new FragmentPreparedStatementCreator(sql, params);
            FragmentResultSetExtractor<T> rse = new FragmentResultSetExtractor<T>(this, criteria);
            if (rse.isFragmentMode() && isUseCountQueryForMaxRows()) {
                rse.setRowCountHint(getRowCountMatchingFilterInternal(criteria));
            }
            
            List<T> results = (List<T>) template.query(psc, psc, rse);
            log.info("SelectByFilter query found " + results.size() + " rows in " + 
                    (System.currentTimeMillis() - start) + "ms");
            return results;
        } catch (SQLException err) {
            throw new UncategorizedSQLException("Error generating select SQL", sql, err);
        }
    }

    public T selectByPK(Object pk) {
        if (pk == null) {
            return null;
        } else {
            List<T> results = selectByPK(new Object[] {pk});
            if (results.isEmpty()) {
                return null;
            } else {
                return results.iterator().next();
            }
        }
    }

    @SuppressWarnings("unchecked")
    public List<T> selectByPK(Object[] pks) {
        if (pks == null) {
            return null;
        }
        long start = System.currentTimeMillis();
        List<Object> params = new ArrayList<Object>();
        String sql = null;
        try {
            sql = buildSelectByPKSQL(pks, params);
            DAOUtils.dumpSQL(sql, params);
            JdbcTemplate template = new JdbcTemplate(getDataSource());
            FragmentPreparedStatementCreator psc = new FragmentPreparedStatementCreator(
                    sql, params);
            
            List<T> results = (List<T>) template.query(psc, psc, new ResultSetExtractor() {
                public Object extractData(ResultSet rst) throws SQLException, DataAccessException {
                    List<T> results = new ArrayList<T>();
                    int n = 0;
                    while (rst.next()) {
                        T one = mapRow(rst, n++);
                        if (one != null) {
                            results.add(one);
                        }
                    }
                    return results;
                }                
            });
            log.info("selectByPK query found " + results.size() + " rows in " + 
                    (System.currentTimeMillis() - start) + "ms");
            return results;
        } catch (SQLException err) {
            throw new UncategorizedSQLException("Error generating select SQL", sql, err);
        }
    }
    
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    
    public void setUseCountQueryForMaxRows(boolean pUseCountQueryForMaxRows) {
        this.useCountQueryForMaxRows = pUseCountQueryForMaxRows;
    }    
    
    public boolean isUseCountQueryForMaxRows() {
        return useCountQueryForMaxRows;
    }

    public DataSource getDataSource() {
        return this.dataSource;
    }
    
    public DAOCriteria prepareCriteria(DAOCriteria criteria, String thisAlias) {
        return criteria;
    }
}
