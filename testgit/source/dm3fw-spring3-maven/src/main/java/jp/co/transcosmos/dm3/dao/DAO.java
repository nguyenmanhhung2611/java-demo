/*
 * Copyright (C) 2006 Transcosmos INC.
 * All Rights Reserved.
 */
package jp.co.transcosmos.dm3.dao;

/**
 * Defines the interface for the most basic write operations for data access objects,
 * layered on top of the ReadOnlyDAO.
 * 
 * This is implemented by the ReflectingDAO class using reflection across a 
 * specified valueobject bean, but custom implementations can save in custom
 * fashions if desired. 
 * 
 * @author <a href="mailto:rick_knowles@hotmail.com">Rick Knowles</a>
 * @version $Id: DAO.java,v 1.6 2007/11/30 03:32:25 abe Exp $
 */
public interface DAO<E> extends ReadOnlyDAO<E> {
    
    /**
     * Gets an array of new unique sequence numbers/values that can be used as IDs when
     * adding new valueobjects. This method can be called externally, but is often
     * called internally by the insert() method when the valueobjects to be inserted
     * have no primary key value set.
     * 
     * @param count The number of unique sequence numbers to allocate
     * @return The array sequence values allocated.
     */
    public Object[] allocatePrimaryKeyIds(int count);
    
    /**
     * Sequentially inserts each of the valueobjects supplied into the database. If the
     * valueobject already exists in the database, this method may throw a runtime exception.
     * 
     * @param toBeInserted An array of valueobjects to be inserted
     */
    public void insert(E toBeInserted[]);
    
    /**
     * Sequentially updates each of the valueobjects supplied in the database.
     * The defined primary key is used to identify which record is matched against
     * in the db, and then that row is updated to match the valueobject. 
     * 
     * @param toBeUpdated An array of valueobjects to be updated
     */
    public int update(E toBeUpdated[]);
    
    /**
     * Deletes each of the valueobjects supplied from the database.
     * The defined primary key is used to identify which record is matched against
     * in the db, and then that row is deleted. 
     * 
     * @param toBeDeleted An array of valueobjects to be deleted
     */
    public void delete(E toBeDeleted[]);
    
    /**
     * Deletes each of the rows matching the pks supplied from the database.
     * 
     * @param pks An array of primary keys whose matching valueobjects are to be deleted
     */
    public void deleteByPK(Object pks[]);
    
    /**
     * Deletes all rows matching the supplied filter supplied from the database. This is
     * effectively the same as calling selectByFilter(criteria) then calling delete() on the
     * result.
     * <p>
     * In SQL terms, this usually becomes an SQL statement of the form
     * <code>DELETE FROM &lt;table&gt; WHERE &lt;whereClause&gt;</code>
     * 
     * @param criteria A criteria object representing the conditions under which to delete
     * a row. 
     */
    public void deleteByFilter(DAOCriteria criteria);
    
    /**
     * Allows filter of a rowset by a criteria, then a number of updates to be applied. This is
     * intended to be used as a mass update function on a large number of rows.
     * 
     * Usage sample:
     *    this.jobDAO.updateByCriteria(
     *            new DAOCriteria("id", 100, DAOCriteria.LESS_THAN), 
     *            new UpdateExpression[] {
     *                  new UpdateValue("postedDate", new Date()),
     *                  new UpdateValue("body", "DELETED!!!"),
     *                  new FormulaUpdateExpression("###title### = ###title### + 'abc'")});
     * 
     * Will produce an sql like:
     * 
     *   UPDATE job SET title = title + 'abc', body = 'DELETED!!!', posted_date = (now) WHERE id &lt; 100 
     *   
     * To support 
     *   
     * @param criteria the filter for the rows to update
     * @param setClauses an array of expressions to perform as update SET clauses
     */
    public int updateByCriteria(DAOCriteria criteria, UpdateExpression setClauses[]);
}
