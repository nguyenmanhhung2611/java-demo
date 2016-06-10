/*
 * Copyright (C) 2006 Transcosmos INC.
 * All Rights Reserved.
 */
package jp.co.transcosmos.dm3.dao;

import java.util.List;

/**
 * Defines the interface for the most basic read-only operations for data access objects.
 * This is implemented by the ReflectingDAO class using reflection across a 
 * specified valueobject bean, but custom implementations can retrieve in custom
 * fashions if desired. 
 * 
 * @author <a href="mailto:rick_knowles@hotmail.com">Rick Knowles</a>
 * @version $Id: ReadOnlyDAO.java,v 1.2 2007/06/13 03:51:36 rick Exp $
 */
public interface ReadOnlyDAO<E> {
    
    /**
     * Selects the single valueobject with a primary key matching the pk supplied.
     * <p>
     * NOTE: the pk type must be the same type as the valueobject's primary key.
     * 
     * @param pk The pk of the valueobject to retrieve
     */
    public E selectByPK(Object pk);
    
    /**
     * Selects the valueobjects with primary keys matching the pks supplied. The resulting
     * list contains as many of the matching valueobjects as were available (i.e. not 
     * found rows are ignored, and result in a smaller list size). There is no guarantee of 
     * the ordering of the results, only that all the rows in the list have a primary key
     * matching one of the pks array elements.
     * <p>
     * NOTE: the pk type must be the same type as the valueobject's primary key.
     * 
     * @param pks An array of primary keys to retrieve the valueobjects for
     */
    public List<E> selectByPK(Object pks[]);
    
    /**
     * Selects the valueobjects that match the conditions specified in the criteria object.
     * The order of the results is determined by the criteria's orderBy settings.
     * 
     * @param criteria A set of filter conditions to restrict the output by
     * @see DAOCriteria
     */
    public List<E> selectByFilter(DAOCriteria criteria);
    
    /**
     * Returns the number of valueobjects that match the conditions specified in the 
     * criteria object. This method is publicly accessible, but is also often called
     * internally by paging functions in the DAO, for the purpose of determining page
     * size, max pages, start and end indices etc.
     * 
     * @param criteria A set of filter conditions to restrict the output by
     * @see DAOCriteria
     */
    public int getRowCountMatchingFilter(DAOCriteria criteria);
}
