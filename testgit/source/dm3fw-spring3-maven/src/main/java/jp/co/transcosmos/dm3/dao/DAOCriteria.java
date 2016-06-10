/*
 * Copyright (C) 2006 Transcosmos INC.
 * All Rights Reserved.
 */
package jp.co.transcosmos.dm3.dao;

import java.util.ArrayList;
import java.util.List;


/**
 * Simple configuration object for passing restrictions on a search. DAO implementations are 
 * responsible for interpreting the meaning of the elements added (and generating the SQL 
 * or storage-specific filtering API settings to match it).
 * <p>
 * The DAOCriteria object loosely models the SQL WHERE and ORDER-BY clauses. To make an 
 * SQL where clause like:
 * <p>
 * <pre>WHERE name = 'rick' AND age = 32 ORDER BY height DESC</pre>
 * the DAOCriteria would be used this way:<p>
 * <pre> DAOCriteria crit = new DAOCriteria();
 * crit.addWhereClause("name", "rick");
 * crit.addWhereClause("age", 32);
 * crit.addOrderByClause("height", false);</pre>
 * When an OR relationship is required, use the {@link OrCriteria} subclass which is 
 * effectively the same except for being interpreted to describe OR relationships 
 * instead of AND.
 * <p>
 * When combinations of AND and OR are required, the {@link addSubCriteria(DAOCriteria)} method can be 
 * used to nest combinations together. The use of brackets in SQL fairly closely parallels
 * the structure of constructed sub-criteria relationships, e.g.
 * <p>
 * <pre>WHERE (name = 'rick' AND age = 32) OR height &gt; 160 ORDER BY height DESC</pre>
 * would require:<p>
 * <pre> DAOCriteria andCrit = new DAOCriteria();
 * andCrit.addWhereClause("name", "rick");
 * andCrit.addWhereClause("age", 32);
 * 
 * DAOCriteria orCrit = new OrCriteria();
 * orCrit.addWhereClause("height", 160, DAOCriteria.GREATER_THAN);
 * orCrit.addSubCriteria(andCrit);
 * orCrit.addOrderByClause("height", false);</pre>
 * 
 * Can also accept a fragmentOffset and fragmentLimit pair, used to provide
 * hints to the underlying implementation about the fragment of the query results we 
 * are interested in, and a maxRows value, used to set a hard limit on the results we
 * retrieve from the resultset. 
 * <p>
 * See tutorials for more detailed explanation of DAOCriteria usage.
 * 
 * @author <a href="mailto:rick_knowles@hotmail.com">Rick Knowles</a>
 * @version $Id: DAOCriteria.java,v 1.6 2007/06/13 06:32:03 rick Exp $
 */
public class DAOCriteria implements OnClause {

    public static final int EQUALS = 1;
    public static final int EQUALS_CASE_INSENSITIVE = 2;
    public static final int LIKE = 3;
    public static final int GREATER_THAN = 4;
    public static final int GREATER_THAN_EQUALS = 5;
    public static final int LESS_THAN = 6;
    public static final int LESS_THAN_EQUALS = 7;
    public static final int ALWAYS_TRUE = 8;
    public static final int IS_NULL = 9;
    public static final int AGE_IN_SECONDS_GREATER_THAN = 10;
    public static final int AGE_IN_SECONDS_LESS_THAN = 11;
    public static final int LIKE_CASE_INSENSITIVE = 12;
    
    private List<DAOCriteriaWhereClause> whereClauses;
    private List<DAOCriteriaSubQuery> subQueryClauses;
    private List<DAOCriteriaOrderByClause> orderByClauses;
    private List<DAOCriteria> subCriteria;
    
    private int fragmentOffset = 0;
    private int fragmentLimit = -1;
    private int maxRows = -1;

    public DAOCriteria() {
    }
    
    public DAOCriteria(String fieldName, Object value) {
        this();
        addWhereClause(fieldName, value);
    }
    
    public DAOCriteria(String fieldName, Object value, int operator) {
        this();
        addWhereClause(fieldName, value, operator);
    }
    
    public DAOCriteria(String fieldName, Object value, int operator, boolean negate) {
        this();
        addWhereClause(fieldName, value, operator, negate);
    }
    
    public DAOCriteria(String daoAlias, String fieldName, Object value, int operator, boolean negate) {
        this();
        addWhereClause(daoAlias, fieldName, value, operator, negate);
    }
    
    public void addWhereClause(String fieldName, Object value) {
        addWhereClause(fieldName, value, EQUALS);
    }
    
    public void addWhereClause(String fieldName, Object value, int operator) {
        addWhereClause(fieldName, value, operator, false);
    }
    
    public void addWhereClause(String fieldName, Object value, int operator, boolean negate) {
        addWhereClause(null, fieldName, value, operator, negate);
    }
    
    public void addWhereClause(String daoAlias, String fieldName, Object value, int operator, boolean negate) {
        // If we compare to null, correct the operator
        if ((value == null) && ((operator == EQUALS) || (operator == EQUALS_CASE_INSENSITIVE))) {
            operator = IS_NULL;
        }
        
        addWhereClause(new DAOCriteriaWhereClause(daoAlias, fieldName, value, operator, negate));
    }
    
    public void addWhereClause(DAOCriteriaWhereClause clause) {
        if (this.whereClauses == null) {
            this.whereClauses = new ArrayList<DAOCriteriaWhereClause>();
        }
        this.whereClauses.add(clause);
    }
    
    public int getWhereClauseCount() {
        if (this.whereClauses == null) {
            return 0;
        } else {
            return this.whereClauses.size();
        }
    }
    
    public DAOCriteriaWhereClause getWhereClause(int index) {
        if (this.whereClauses == null) {
            return null;
        } else {
            return (DAOCriteriaWhereClause) this.whereClauses.get(index);
        }
    }
    
    public void addOrderByClause(String fieldName) {
        addOrderByClause(fieldName, true);
    }
    
    public void addOrderByClause(String fieldName, boolean ascending) {
        addOrderByClause(null, fieldName, ascending);
    }
    
    public void addOrderByClause(String daoAlias, String fieldName, boolean ascending) {
        addOrderByClause(new DAOCriteriaOrderByClause(daoAlias, fieldName, ascending));
    }
    
    public void addOrderByClause(DAOCriteriaOrderByClause clause) {
        if (this.orderByClauses == null) {
            this.orderByClauses = new ArrayList<DAOCriteriaOrderByClause>();
        }
        this.orderByClauses.add(clause);
    }
    
    public int getOrderByClauseCount() {
        if (this.orderByClauses == null) {
            return 0;
        } else {
            return this.orderByClauses.size();
        }
    }
    
    public DAOCriteriaOrderByClause getOrderByClause(int index) {
        if (this.orderByClauses == null) {
            return null;
        } else {
            return (DAOCriteriaOrderByClause) this.orderByClauses.get(index);
        }
    }
    
    
    public void addSubCriteria(DAOCriteria criteria) {
        if (this.subCriteria == null) {
            this.subCriteria = new ArrayList<DAOCriteria>();
        }
        this.subCriteria.add(criteria);
    }
    
    public int getSubCriteriaCount() {
        if (this.subCriteria == null) {
            return 0;
        } else {
            return this.subCriteria.size();
        }
    }
    
    public DAOCriteria getSubCriteria(int index) {
        if (this.subCriteria == null) {
            return null;
        } else {
            return (DAOCriteria) this.subCriteria.get(index);
        }
    }
    
    public void addExistsSubQuery(SubQueryable subqueryDAO) {
        addExistsSubQuery(subqueryDAO, null);
    }
    
    public void addExistsSubQuery(SubQueryable subqueryDAO, DAOCriteria subCriteria) {
        addExistsSubQuery(subqueryDAO, subCriteria, false);
    }
    
    public void addExistsSubQuery(SubQueryable subqueryDAO, DAOCriteria subCriteria, boolean negate) {
        addSubQuery(new ExistsSubQuery(subqueryDAO, subCriteria, negate));
    }

// 2015.06.12 H.Mizuno サブクエリ機能拡張 start
    public void addExistsSubQuery(SubQueryable subqueryDAO, DAOCriteria subCriteria, String alias, boolean negate) {
        addSubQuery(new ExistsSubQuery(subqueryDAO, subCriteria, alias, negate));
    }
// 2015.06.12 H.Mizuno サブクエリ機能拡張 end

    public void addInSubQuery(String compareField, String subField, SubQueryable subqueryDAO) {
        addInSubQuery(compareField, subField, subqueryDAO, null);
    }
    
    public void addInSubQuery(String compareField, String subField, SubQueryable subqueryDAO, 
            DAOCriteria subCriteria) {
        addInSubQuery(compareField, subField, subqueryDAO, subCriteria, false);
    }
    
    public void addInSubQuery(String compareField, String subField, SubQueryable subqueryDAO, 
            DAOCriteria subCriteria, boolean negate) {
        addSubQuery(new InSubQuery(subqueryDAO, subCriteria, compareField, null, subField, null, negate));
    }
    
    public void addInSubQuery(String compareField, String compareAlias, 
            String subField, String subAlias, 
            SubQueryable subqueryDAO, DAOCriteria subCriteria, boolean negate) {
        addSubQuery(new InSubQuery(subqueryDAO, subCriteria, 
                compareField, compareAlias, subField, subAlias, negate));
    }
    
    public void addInSubQuery(String compareField, Object values[]) {
        addInSubQuery(compareField, values, false);
    }
    
    public void addInSubQuery(String compareField, Object values[], boolean negate) {
        addSubQuery(new InValuesSubQuery(compareField, null, values, negate));
    }
    
    public void addInSubQuery(String compareField, String compareAlias, Object values[], boolean negate) {
        addSubQuery(new InValuesSubQuery(compareField, compareAlias, values, negate));
    }
    
    public void addSubQuery(DAOCriteriaSubQuery query) {
        if (this.subQueryClauses == null) {
            this.subQueryClauses = new ArrayList<DAOCriteriaSubQuery>();
        }
        this.subQueryClauses.add(query);
    }
    
    public int getSubQueryCount() {
        if (this.subQueryClauses == null) {
            return 0;
        } else {
            return this.subQueryClauses.size();
        }
    }
    
    public DAOCriteriaSubQuery getSubQuery(int index) {
        if (this.subQueryClauses == null) {
            return null;
        } else {
            return (DAOCriteriaSubQuery) this.subQueryClauses.get(index);
        }
    }

    public int getFragmentOffset() {
        return fragmentOffset;
    }

    public int getFragmentLimit() {
        return fragmentLimit;
    }

    public int getMaxRows() {
        return maxRows;
    }

    public void setFragmentOffset(int fragmentOffset) {
        this.fragmentOffset = fragmentOffset;
    }

    public void setFragmentLimit(int fragmentLimit) {
        this.fragmentLimit = fragmentLimit;
    }

    public void setMaxRows(int maxRows) {
        this.maxRows = maxRows;
    }
    
    public DAOCriteria cloneMe() {
        DAOCriteria criteria = blankInstance();
        for (int n = 0; n < this.getWhereClauseCount(); n++) {
            criteria.addWhereClause(this.getWhereClause(n));
        }
        for (int n = 0; n < this.getOrderByClauseCount(); n++) {
            criteria.addOrderByClause(this.getOrderByClause(n));
        }
        for (int n = 0; n < this.getSubQueryCount(); n++) {
            criteria.addSubQuery(this.getSubQuery(n));
        }
        for (int n = 0; n < this.getSubCriteriaCount(); n++) {
            criteria.addSubCriteria(this.getSubCriteria(n));
        }
        criteria.fragmentLimit = this.fragmentLimit;
        criteria.fragmentOffset = this.fragmentOffset;
// 2015.07.07 H.Mizuno コピーミスを修正 start
//        criteria.maxRows = criteria.maxRows;
        criteria.maxRows = this.maxRows;
// 2015.07.07 H.Mizuno コピーミスを修正 end
        return criteria;
    }


    
    protected DAOCriteria blankInstance() {
        return new DAOCriteria();
    }

    public void setOrderByClauses(List<DAOCriteriaOrderByClause> orderByClauses) {
        this.orderByClauses = orderByClauses;
    }

    public void setSubCriteria(List<DAOCriteria> subCriteria) {
        this.subCriteria = subCriteria;
    }

    public void setSubQueryClauses(List<DAOCriteriaSubQuery> subQueryClauses) {
        this.subQueryClauses = subQueryClauses;
    }

    public void setWhereClauses(List<DAOCriteriaWhereClause> whereClauses) {
        this.whereClauses = whereClauses;
    }
}
