/*
 * Copyright (C) 2006 Transcosmos INC.
 * All Rights Reserved.
 */
package jp.co.transcosmos.dm3.dao;

/**
 * A single element in a union dao. Used in Spring configuration of the UnionDAO
 * 
 * @author <a href="mailto:rick_knowles@hotmail.com">Rick Knowles</a>
 * @version $Id: UnionDAOElementDescriptor.java,v 1.5 2007/06/13 06:32:03 rick Exp $
 */
public class UnionDAOElementDescriptor<E> {

    private SQLClauseBuilder dao;
    private String selectFields[];
    
    public UnionDAOElementDescriptor() {}

    public UnionDAOElementDescriptor(SQLClauseBuilder dao) {
    	this(dao, null);
    }
    
    public UnionDAOElementDescriptor(SQLClauseBuilder dao, String selectFields[]) {
    	this.dao = dao;
    	this.selectFields = selectFields;
    }
    
    public SQLClauseBuilder getDao() {
        return this.dao;
    }
    
    public String[] getSelectFields() {
        return this.selectFields;
    }
    
    public void setDao(SQLClauseBuilder dao) {
        this.dao = dao;
    }
    
    public void setSelectFields(String[] pSelectFields) {
        this.selectFields = pSelectFields;
    }
}
