/*
 * Copyright (C) 2006 Transcosmos INC.
 * All Rights Reserved.
 */
package jp.co.transcosmos.dm3.dao;

import java.util.List;

/**
 * Used within Spring to describe the tables we want to join together in a StarJoinDAO
 * 
 * @author <a href="mailto:rick_knowles@hotmail.com">Rick Knowles</a>
 * @version $Id: StarJoinDAODescriptor.java,v 1.7 2012/08/01 09:28:36 tanaka Exp $
 */
public class StarJoinDAODescriptor {

    private String alias;
    private SQLClauseBuilder dao;
    private OnClause conditions[];
    private Integer joinType;
    
    public String getAlias() {
        return this.alias;
    }
    public SQLClauseBuilder getDao() {
        return this.dao;
    }
    public OnClause[] getConditions() {
        return this.conditions;
    }
    protected int getJoinTypeIndex() {
        if ((this.joinType == null) && (this.conditions != null)) {
            this.joinType = new Integer(DAOUtils.getJoinType(this.conditions));
        }
        return this.joinType.intValue();
    }
    public void setAlias(String pAlias) {
        this.alias = pAlias;
    }
    public void setDao(SQLClauseBuilder pDao) {
        this.dao = pDao;
    }
    public void setConditions(OnClause[] pConditions) {
        this.conditions = pConditions;
    }
    public void setConditionList(List<OnClause> pConditions) {
        setConditions(pConditions.toArray(new OnClause[pConditions.size()]));
    }
    public void setCondition(OnClause pCondition) {
        setConditions(new OnClause[] {pCondition});
    }
    public void setJoinType(String pJoinType) {
        if (pJoinType != null) {
            this.joinType = new Integer(JoinCondition.lookupJoinType(pJoinType));
        }
    } 
}
