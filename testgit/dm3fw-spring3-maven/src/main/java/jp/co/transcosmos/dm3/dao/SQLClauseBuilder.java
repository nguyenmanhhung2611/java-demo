/*
 * Copyright (C) 2006 Transcosmos INC.
 * All Rights Reserved.
 */
package jp.co.transcosmos.dm3.dao;

import java.util.List;

import org.springframework.jdbc.core.RowMapper;

/**
 * Defines the methods that should be implemented by a DAO that can be joined to other DAOs. 
 * This is intended as an internal only interface, but SQL generating DAO implementations 
 * that want to be able to nest inside the framework-provided implementations may need to 
 * implement this interface. 
 * 
 * @author <a href="mailto:rick_knowles@hotmail.com">Rick Knowles</a>
 * @version $Id: SQLClauseBuilder.java,v 1.4 2007/06/13 06:32:03 rick Exp $
 */
public interface SQLClauseBuilder {
        
    /**
     * Builds the SQL SELECT clause for this DAO with the alias provided, using only
     * the fields in the filterFields list if it is not null. Output SQL is appended
     * to the sql StringBuilder.
     */
    public void addSelectClause(StringBuilder sql, String alias, String filterFields[]);

    /**
     * Builds the SQL FROM clause for this DAO with the alias provided,. Output SQL is 
     * appended to the sql StringBuilder, and any bind variables added to the params list.
     */
    public void addFromClause(StringBuilder sql, String alias, List<Object> params);

    /**
     * Returns a criteria that we want to apply to every query on this DAO
     */
    public DAOCriteria buildImplicitCriteria();

    /**
     * Builds a single SQL WHERE clause component for this DAO with the alias provided,. 
     * Output SQL is appended to the sql StringBuilder, and any bind variables added to the params list.
     */
    public boolean addOneWhereClause(StringBuilder sql, String alias, 
            DAOCriteriaWhereClause where, List<Object> params);

    /**
     * Builds a single SQL ORDER-BY clause component for this DAO with the alias provided,. 
     * Output SQL is appended to the sql StringBuilder, and any bind variables added to the params list.
     */
    public boolean addOneOrderByClause(StringBuilder sql, String alias, 
            DAOCriteriaOrderByClause orderBy);

    /**
     * Returns the row mapper to associate resultsets and valueobjects with
     */
    public RowMapper getRowMapper(String fieldPrefix, String filterFields[], boolean isLeftJoinOutsideTable);

    /**
     * Returns the db column name in alias.column format
     */
    public String lookupAliasDotColumnName(String fieldName, String fieldAlias, String thisAlias);

    /**
     * Returns the label for this column for use in SQL select statements.
     */
    public String lookupLabelledName(String fieldName, String fieldAlias, String thisAlias);
    
    /**
     * A last chance step to override or modify the DAOCriteria object before applying it
     * to the DAO's select methods. Just "return criteria;" if unneeded.
     */
    public DAOCriteria prepareCriteria(DAOCriteria criteria, String thisAlias);
}
