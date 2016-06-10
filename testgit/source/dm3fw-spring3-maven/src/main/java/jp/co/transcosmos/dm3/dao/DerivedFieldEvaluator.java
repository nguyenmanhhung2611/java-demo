/*
 * Copyright (C) 2006 Transcosmos INC.
 * All Rights Reserved.
 */
package jp.co.transcosmos.dm3.dao;

/**
 * Implement this to assign extra calculated columns to a valueobject after population. 
 * Assign an instance to a ReflectingDAO object using the 
 * ReflectingDAO.setDerivableFieldEvaluator() method.
 * 
 * @author <a href="mailto:rick@knowleses.org">Rick Knowles</a>
 * @version $Id: DerivedFieldEvaluator.java,v 1.4 2007/06/13 04:58:54 rick Exp $
 */
public interface DerivedFieldEvaluator<E> {
    
    /**
     * Calculate any derived fields, then set them on the item in question. This is called
     * by the ReflectingRowMapper after the DB fields have been set on a loaded result VO.
     */
    public void setDerivedFields(E item);
    
    /**
     * Return true if the field is derived and shouldn't be retrieved during SQL calls, otherwise false
     */
    public boolean isFieldDerived(String fieldName);
    
    /**
     * Implement this method with a simple "return criteria" if you don't want to support
     * WHERE clauses containing the derived field. Otherwise, rewrite the criteria object
     * in this method to use the replaced formula for derived fields.
     */
    public DAOCriteria rewriteDerivedCriteria(DAOCriteria criteria, String thisAlias);
}
