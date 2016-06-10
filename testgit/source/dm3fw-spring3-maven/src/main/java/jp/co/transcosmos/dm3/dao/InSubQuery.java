/*
 * Copyright (C) 2006 Transcosmos INC.
 * All Rights Reserved.
 */
package jp.co.transcosmos.dm3.dao;

import java.util.List;

/**
 * Implements a simple IN sub-query. This uses a DAO and criteria to create the
 * sub query SQL, so:
 * <pre> SELECT * FROM abc WHERE abc.def_id IN (SELECT id FROM def WHERE def.status = 'OK');</pre>
 * would look like this:
 * <pre> DAOCriteria crit = new DAOCriteria();
 * crit.addInSubQuery(new InSubQuery(defDAO, new DAOCriteria("status", "OK"), 
 *                              "defId", "abc", "id", "def", false));
 * List results = abcDAO.selectByFilter(crit);</pre>
 * 
 * @author <a href="mailto:rick@knowleses.org">Rick Knowles</a>
 * @version $Id: InSubQuery.java,v 1.6 2007/06/13 04:58:54 rick Exp $
 */
public class InSubQuery implements DAOCriteriaSubQuery {

    private boolean negate;
    private SubQueryable subqueryDAO;
    private DAOCriteria subCriteria;
    private String compareField;
    private String compareAlias;
    private String subField;
    private String subAlias;
    
    public InSubQuery() {}

    public InSubQuery(SubQueryable subqueryDAO, DAOCriteria subCriteria, 
            String compareField, String compareAlias, 
            String subField, String subAlias, boolean negate) {
        this();
        this.negate = negate;
        this.subqueryDAO = subqueryDAO;
        this.subCriteria = subCriteria;
        this.compareField = compareField;
        this.compareAlias = compareAlias;
        this.subField = subField;
        this.subAlias = subAlias;
    }

    public SubQueryable getSubQueryDAO() {
        return this.subqueryDAO;
    }

    public DAOCriteria getSubCriteria() {
        return this.subCriteria;
    }

    public void setSubQueryDAO(SubQueryable subqueryDAO) {
        this.subqueryDAO = subqueryDAO;
    }

    public void setSubCriteria(DAOCriteria pSubCriteria) {
        this.subCriteria = pSubCriteria;
    }

    public String getCompareField() {
        return this.compareField;
    }
    
    public String getSubField() {
        return this.subField;
    }

    public void setCompareField(String pCompareField) {
        this.compareField = pCompareField;
    }

    public void setSubField(String pSubField) {
        this.subField = pSubField;
    }
    
    public boolean isNegate() {
        return this.negate;
    }

    public void setNegate(boolean negate) {
        this.negate = negate;
    }

    public void writeSubQuerySQL(SQLClauseBuilder parent, StringBuilder sql, List<Object> params) {
        String fieldName = parent.lookupAliasDotColumnName(this.compareField, this.compareAlias, null);
        if (fieldName == null) {
            throw new RuntimeException("Can't match compareField: " + this.compareField);
        }
        sql.append(fieldName);
        if (isNegate()) {
            sql.append(" NOT");
        }
        sql.append(" IN (");
// 2015.06.10 H.Mizuno thisAlias がサブクエリの From 句に適用されない問題を修正 start
//        getSubQueryDAO().buildSubQuerySQL(sql, getSubCriteria(), params, 
//                new String[] {this.subField}, new String[] {this.subAlias}, null);
        getSubQueryDAO().buildSubQuerySQL(sql, getSubCriteria(), params, 
                new String[] {this.subField}, new String[] {this.subAlias}, this.subAlias);
// 2015.06.10 H.Mizuno thisAlias がサブクエリの From 句に適用されない問題を修正 end
        sql.append(")");
    }
}
