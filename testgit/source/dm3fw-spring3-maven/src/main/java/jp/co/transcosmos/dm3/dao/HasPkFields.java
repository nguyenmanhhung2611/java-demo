/*
 * Copyright (C) 2006 Transcosmos INC.
 * All Rights Reserved.
 */
package jp.co.transcosmos.dm3.dao;

import java.util.List;

/**
 * Internal interface that defines a class having a set of primary key attributes defined. 
 * This is used for sharing configuration to child objects that need them.
 * 
 * @author <a href="mailto:rick@knowleses.org">Rick Knowles</a>
 * @version $Id: HasPkFields.java,v 1.3 2007/06/13 04:58:54 rick Exp $
 */
public interface HasPkFields {
    public List<String> getPkFields();
    public void setPkFields(List<String> fieldNames);
}
