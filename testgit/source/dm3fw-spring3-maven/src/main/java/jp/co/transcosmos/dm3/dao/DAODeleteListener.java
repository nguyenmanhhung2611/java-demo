/*
 * Copyright (C) 2006 Transcosmos INC.
 * All Rights Reserved.
 */
package jp.co.transcosmos.dm3.dao;

/**
 * Called by the ReflectingDAO when a delete operation is requested. See the 
 * RelfectingDAO.setDeleteListener() method to assign listeners. 
 * 
 * @author <a href="mailto:rick@knowleses.org">Rick Knowles</a>
 * @version $Id: DAODeleteListener.java,v 1.2 2007/06/13 03:51:36 rick Exp $
 */
public interface DAODeleteListener<T> {
    /**
     * Called before a delete. Return true to allow, false to abort
     */
    public boolean preDelete(T item);
    /**
     * Called before a deleteByPK. Return true to allow, false to abort
     */
    public boolean preDeleteByPK(Object pks[]);
    /**
     * Called before a deleteByFilter. Return true to allow, false to abort
     */
    public boolean preDeleteByFilter(DAOCriteria criteria);

    /**
     * Called after a delete
     */
    public void postDelete(T item);
    /**
     * Called after a deleteByPK
     */
    public void postDeleteByPK(Object pks[]);
    /**
     * Called after a deleteByFilter
     */
    public void postDeleteByFilter(DAOCriteria criteria);
}
