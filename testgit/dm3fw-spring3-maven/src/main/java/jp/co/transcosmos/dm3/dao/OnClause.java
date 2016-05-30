/*
 * Copyright (C) 2006 Transcosmos INC.
 * All Rights Reserved.
 */
package jp.co.transcosmos.dm3.dao;

/**
 * Empty interface used to define the types of objects supported for generating ON clauses
 * in joins.
 * <p>
 * This interface is implemented by classes that want to provide SQL segments for inclusion
 * in an SQL join's ON clause, e.g.<p>
 * <pre>SELECT * FROM a INNER JOIN b ON a.id= b.a_id AND a.field = 3</pre>
 * 
 * @author <a href="mailto:rick_knowles@hotmail.com">Rick Knowles</a>
 * @version $Id: OnClause.java,v 1.3 2007/06/13 06:32:03 rick Exp $
 */
public interface OnClause {
}
