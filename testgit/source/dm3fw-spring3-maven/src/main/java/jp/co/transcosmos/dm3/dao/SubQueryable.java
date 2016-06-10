/*
 * Copyright (C) 2006 Transcosmos INC.
 * All Rights Reserved.
 */
package jp.co.transcosmos.dm3.dao;

import java.util.List;

/**
 * An interface defining DAOs that want to be able to be used in EXISTS or IN style 
 * subqueries. 
 * 
 * @author <a href="mailto:rick@knowleses.org">Rick Knowles</a>
 * @version $Id: SubQueryable.java,v 1.5 2007/06/13 06:32:03 rick Exp $
 */
public interface SubQueryable {
    
    /**
     * This method just builds the SQL for the subquery component to the SQL. It should
     * assume that it will be wrapped in brackets and proceeded by an operator like "IN"
     * or "EXISTS". Output SQL should be written to the sql StringBuilder and bind
     * variables written to the params list.
     */
    public void buildSubQuerySQL(StringBuilder sql, DAOCriteria subCriteria, 
            List<Object> params, String selectFields[], 
            String[] selectFieldAliases, String thisAlias);
}
