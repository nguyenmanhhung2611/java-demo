/*
 * Copyright (C) 2006 Transcosmos INC.
 * All Rights Reserved.
 */
package jp.co.transcosmos.dm3.dao;

/**
 * Models a single where clause in a criteria.
 * <ol>
 * <li>daoAlias: in the case of a join, this distinguishes one DAO's field from another,
 * as each DAO has a unique alias configured.
 * <li>fieldName: the valueobject's field name
 * <li>value: the value to filter by
 * <li>operator: index of the operator to use (see the operators in the {@link DAOCriteria} class)
 * <li>negate: if true make this a NOT operation
 * </ol>
 * 
 * @author <a href="mailto:rick_knowles@hotmail.com">Rick Knowles</a>
 * @version $Id: DAOCriteriaWhereClause.java,v 1.3 2007/06/13 03:51:36 rick Exp $
 */
public class DAOCriteriaWhereClause {

    private String daoAlias;
    private String fieldName;
    private Object value;
    private int operator;
    private boolean negate;

    public DAOCriteriaWhereClause() {}
    
    public DAOCriteriaWhereClause(String daoAlias, String fieldName, Object value, int operator, boolean negate) {
        this();
        this.daoAlias = daoAlias;
        this.fieldName = fieldName;
        this.value = value;
        this.operator = operator;
        this.negate = negate;
    }

    public String getFieldName() {
        return fieldName;
    }

    public Object getValue() {
        return value;
    }

    public int getOperator() {
        return operator;
    }

    public boolean isNegate() {
        return negate;
    }

    public String getDaoAlias() {
        return this.daoAlias;
    }

    public void setDaoAlias(String pDaoAlias) {
        this.daoAlias = pDaoAlias;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public void setOperator(int operator) {
        this.operator = operator;
    }

    public void setNegate(boolean negate) {
        this.negate = negate;
    }
    
    public String toString() {
        return "[DAOCriteriaWhereClause: fieldName=" + fieldName + 
                (this.daoAlias != null ? " (dao:" + daoAlias + ")" : "") +
                ",value=" + value + ", operator=" + operator + ", negate=" + negate + "]";
    }
    
    public void setOperatorMark(String mark) {
    	if (mark.equalsIgnoreCase("=")) {
    		setOperator(DAOCriteria.EQUALS);
    	} else if (mark.equalsIgnoreCase("<=>")) {
    		setOperator(DAOCriteria.EQUALS_CASE_INSENSITIVE);
    	} else if (mark.equalsIgnoreCase("LIKE")) {
    		setOperator(DAOCriteria.LIKE);
    	} else if (mark.equalsIgnoreCase(">")) {
    		setOperator(DAOCriteria.GREATER_THAN);
    	} else if (mark.equalsIgnoreCase("=>")) {
    		setOperator(DAOCriteria.GREATER_THAN_EQUALS);
    	} else if (mark.equalsIgnoreCase("<")) {
    		setOperator(DAOCriteria.LESS_THAN);
    	} else if (mark.equalsIgnoreCase("<=")) {
    		setOperator(DAOCriteria.LESS_THAN_EQUALS);
    	} else if (mark.equalsIgnoreCase("!!")) {
    		setOperator(DAOCriteria.ALWAYS_TRUE);
    	} else if (mark.equalsIgnoreCase("IS NULL")) {
    		setOperator(DAOCriteria.IS_NULL);
    	} else if (mark.equalsIgnoreCase("AGE>")) {
    		setOperator(DAOCriteria.AGE_IN_SECONDS_GREATER_THAN);
    	} else if (mark.equalsIgnoreCase("AGE<")) {
    		setOperator(DAOCriteria.AGE_IN_SECONDS_LESS_THAN);
    	} else if (mark.equalsIgnoreCase("<LIKE>")) {
    		setOperator(DAOCriteria.LIKE_CASE_INSENSITIVE);
    	} else {
    		throw new RuntimeException("Unknown operator mark: " + mark);
    	}
    }
} 
