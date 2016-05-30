/*
 * Copyright (C) 2006 Transcosmos INC.
 * All Rights Reserved.
 */
package jp.co.transcosmos.dm3.dao;

/**
 * Models a single order-by clause in a criteria.
 * 
 * @author <a href="mailto:rick_knowles@hotmail.com">Rick Knowles</a>
 * @version $Id: DAOCriteriaOrderByClause.java,v 1.2 2007/06/13 03:51:36 rick Exp $
 */
public class DAOCriteriaOrderByClause {

    private String daoAlias;
    private String fieldName;
    private boolean ascending;

    public DAOCriteriaOrderByClause(String daoAlias, String fieldName, boolean ascending) {
        this.daoAlias = daoAlias;
        this.fieldName = fieldName;
        this.ascending = ascending;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }
    
    public String getDaoAlias() {
        return this.daoAlias;
    }

    public void setDaoAlias(String pDaoAlias) {
        this.daoAlias = pDaoAlias;
    }

    public boolean isAscending() {
        return ascending;
    }

    public void setAscending(boolean ascending) {
        this.ascending = ascending;
    }

    public String toString() {
        return "[DAOCriteriaOrderByClause: fieldName=" + fieldName +
                (this.daoAlias != null ? " (dao:" + daoAlias + ")" : "") +
                ", ascending=" + ascending + "]";
    }
}
