/*
 * &copy; Trans-cosmos Inc. 2006, 2007.
 * All rights reserved.
 */
package jp.co.transcosmos.dm3.validation;

import jp.co.transcosmos.dm3.dao.ReadOnlyDAO;
import jp.co.transcosmos.dm3.dao.DAOCriteria;

/**
 * Goes to the database and looks up whether a field value exists. This can return
 * a validation error on presence of the value in the DB.
 * <p>
 * The dao and criteria form the basis set of rows to select from the DB. An additional
 * filter of "&lt;fieldName&gt; &lt;operator&gt; &lt;value&gt;" is then applied, and if
 * the rowCount exceeds rowLimit, the existsInDB error is thrown.
 * 
 * @author <a href="mailto:rick@knowleses.org">Rick Knowles</a>
 * @version $Id: ExistsInDBValidation.java,v 1.3 2007/05/31 05:38:56 rick Exp $
 */
public class ExistsInDBValidation implements Validation {

    private ReadOnlyDAO<?> dao;
    private DAOCriteria criteria;
    private String daoAlias;
    private String fieldName;
    private int operator;
    private int rowLimit = 0;
    private boolean negative;
    
    public ExistsInDBValidation(ReadOnlyDAO<?> dao, String fieldValue) {
        this(dao, fieldValue, DAOCriteria.EQUALS);
    }
    public ExistsInDBValidation(ReadOnlyDAO<?> dao, String fieldValue, int operator) {
        this(dao, fieldValue, operator, false);
    }
    
    public ExistsInDBValidation(ReadOnlyDAO<?> dao, String fieldName, int operator, boolean negative) {
        this(dao, null, fieldName, operator, negative);
    }
    
    public ExistsInDBValidation(ReadOnlyDAO<?> dao, String daoAlias, String fieldName, 
            int operator, boolean negative) {
        this(dao, daoAlias, fieldName, operator, negative, null);
    }
    
    public ExistsInDBValidation(ReadOnlyDAO<?> dao, String daoAlias, String fieldName, 
            int operator, boolean negative, DAOCriteria criteria) {
        this(dao, daoAlias, fieldName, operator, negative, criteria, 0);
    }
    
    public ExistsInDBValidation(ReadOnlyDAO<?> dao, String daoAlias, String fieldName, 
            int operator, boolean negative, DAOCriteria criteria, int rowLimit) {
        this.dao = dao;
        this.daoAlias = daoAlias;
        this.fieldName = fieldName;
        this.operator = operator;
        this.negative = negative;
        this.criteria = criteria;
        this.rowLimit = rowLimit;
    }
    
    public ValidationFailure validate(String pName, Object pValue) {
        if ((pValue == null) || pValue.equals("")) {
            return null;
        }
        DAOCriteria criteria = new DAOCriteria();
        if (this.criteria != null) {
            criteria.addSubCriteria(this.criteria.cloneMe());
            criteria.setFragmentLimit(this.criteria.getFragmentLimit());
            criteria.setFragmentOffset(this.criteria.getFragmentOffset());
            criteria.setMaxRows(this.criteria.getMaxRows());
        }
        criteria.addWhereClause(this.daoAlias, this.fieldName, pValue, this.operator, this.negative);
        
        if (this.dao.getRowCountMatchingFilter(criteria) > this.rowLimit) {
            return new ValidationFailure("existsInDB", pName, pValue, null);
        }
        return null;
    }

}
