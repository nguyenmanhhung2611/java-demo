/*
 * Copyright (C) 2006 Transcosmos INC.
 * All Rights Reserved.
 */
package jp.co.transcosmos.dm3.dao;

import java.util.List;

/**
 * Implements a simple in-constant-list sub-query. The usage for:
 * <pre> SELECT * FROM abc WHERE id IN (3, 5, 9, 12) AND status = 'OK';</pre>
 * would look like this:
 * <pre> DAOCriteria crit = new DAOCriteria();
 * crit.addExistsSubQuery(new InValuesSubQuery("id", "abc", new Integer[] {3, 5, 9, 12}, "OK"), false));
 * crit.addWhereClause("status", "OK");
 * List results = abcDAO.selectByFilter(crit);</pre>
 * 
 * @author <a href="mailto:rick@knowleses.org">Rick Knowles</a>
 * @version $Id: InValuesSubQuery.java,v 1.6 2007/06/13 06:32:03 rick Exp $
 */
public class InValuesSubQuery implements DAOCriteriaSubQuery {

    private boolean negate;
    private String compareField;
    private String compareAlias;
    private Object values[];
    
    public InValuesSubQuery() {}

    public InValuesSubQuery(String compareField, String compareAlias, Object values[], boolean negate) {
        this();
        this.negate = negate;
        this.compareField = compareField;
        this.compareAlias = compareAlias;
        this.values = values;
    }

    public String getCompareField() {
        return this.compareField;
    }

    public Object[] getValues() {
        return this.values;
    }

    public void setCompareField(String pCompareField) {
        this.compareField = pCompareField;
    }

    public void setValues(Object[] pValues) {
        this.values = pValues;
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
        for (int n = 0; n < this.values.length; n++) {
            if (n > 0) {
                sql.append(", ");
            }
            if (this.values[n] == null) {
                sql.append("NULL");
            } else {
                sql.append("?");
                params.add(this.values[n]);
            }
        }
        sql.append(")");
    }
}
