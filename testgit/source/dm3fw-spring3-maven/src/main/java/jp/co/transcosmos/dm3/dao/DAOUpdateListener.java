/*
 * Copyright (C) 2006 Transcosmos INC.
 * All Rights Reserved.
 */
package jp.co.transcosmos.dm3.dao;

/**
 * Called by the ReflectingDAO when an update operation is requested. See the 
 * RelfectingDAO.setUpdateListener() method to assign listeners. 
 * 
 * @author <a href="mailto:rick@knowleses.org">Rick Knowles</a>
 * @version $Id: DAOUpdateListener.java,v 1.3 2007/10/19 07:58:11 rick Exp $
 */
public interface DAOUpdateListener<T> {
    /**
     * Called before an update. Return true to allow, false to abort
     */
    public boolean preUpdate(T item[]);
    /**
     * Called after an update
     */
    public void postUpdate(T item[]);
    
    public boolean preUpdateByCriteria(DAOCriteria criteria, UpdateExpression setClauses[]);
    
    public void postUpdateByCriteria(DAOCriteria criteria, UpdateExpression setClauses[]);
}
