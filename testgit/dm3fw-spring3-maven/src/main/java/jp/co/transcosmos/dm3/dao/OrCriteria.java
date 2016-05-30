/*
 * Copyright (C) 2006 Transcosmos INC.
 * All Rights Reserved.
 */
package jp.co.transcosmos.dm3.dao;

/**
 * Sub-class of the DAOCriteria class, recognized by the DAOUtils internal implementations
 * as meaning that the conditions in the WHERE clause section will be OR'd together instead 
 * of AND'd. This subclass is otherwise identical to it's base class (in function).
 * 
 * @author <a href="mailto:rick@knowleses.org">Rick Knowles</a>
 * @version $Id: OrCriteria.java,v 1.3 2007/06/13 06:32:03 rick Exp $
 */
public class OrCriteria extends DAOCriteria {

    public OrCriteria() {
        super();
    }

    public OrCriteria(String pFieldName, Object pValue, int pOperator, boolean pNegate) {
        super(pFieldName, pValue, pOperator, pNegate);
    }

    public OrCriteria(String pFieldName, Object pValue, int pOperator) {
        super(pFieldName, pValue, pOperator);
    }

    public OrCriteria(String pFieldName, Object pValue) {
        super(pFieldName, pValue);
    }
    
    protected DAOCriteria blankInstance() {
        return new OrCriteria();
    }
}
