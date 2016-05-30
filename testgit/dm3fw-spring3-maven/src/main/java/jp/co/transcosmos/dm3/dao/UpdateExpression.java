/*
 * Copyright (C) 2006 Transcosmos INC.
 * All Rights Reserved.
 */
package jp.co.transcosmos.dm3.dao;

import java.util.List;


/**
 * Defines the interface for all expressions used in set clauses in updateByCriteria()
 * 
 * @author <a href="mailto:rick@knowleses.org">Rick Knowles</a>
 * @version $Id: UpdateExpression.java,v 1.1 2007/10/19 07:58:11 rick Exp $
 * @see DAO
 */
public interface UpdateExpression {    
    public String buildSQL(String thisAlias, AliasDotColumnResolver resolver, List<Object> params);
}
