/*
 * Copyright (C) 2006 Transcosmos INC.
 * All Rights Reserved.
 */
package jp.co.transcosmos.dm3.dao;

/**
 * Called by the ReflectingDAO when an insert operation is requested. See the 
 * RelfectingDAO.setInsertListener() method to assign listeners. 
 * 
 * @author <a href="mailto:rick@knowleses.org">Rick Knowles</a>
 * @version $Id: DAOInsertListener.java,v 1.2 2007/06/13 03:51:36 rick Exp $
 */
public interface DAOInsertListener<T> {
    /**
     * Called before an insert. Return true to allow, false to abort
     */
    public boolean preInsert(T item[]);
    
    /**
     * Called after an insert
     */
    public void postInsert(T item[]);
}
