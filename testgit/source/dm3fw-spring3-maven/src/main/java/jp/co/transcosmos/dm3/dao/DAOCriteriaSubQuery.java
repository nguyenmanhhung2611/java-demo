/*
 * Copyright (C) 2006 Transcosmos INC.
 * All Rights Reserved.
 */
package jp.co.transcosmos.dm3.dao;

import java.util.List;

/**
 * Allows a SubQueryable implementation to be used as a sub-query of another 
 * DAO's execution. 
 * 
 * For example, in the case of an EXISTS relationship, the {@link ExistsSubQuery}
 * class will accept another DAO and build the SQL for the EXISTS clause using that
 * DAO and the filter conditions supplied. 
 * 
 * @author <a href="mailto:rick_knowles@hotmail.com">Rick Knowles</a>
 * @version $Id: DAOCriteriaSubQuery.java,v 1.4 2007/06/13 03:51:36 rick Exp $
 */
public interface DAOCriteriaSubQuery {    
    public void writeSubQuerySQL(SQLClauseBuilder parent, StringBuilder sql, 
            List<Object> params);
}
