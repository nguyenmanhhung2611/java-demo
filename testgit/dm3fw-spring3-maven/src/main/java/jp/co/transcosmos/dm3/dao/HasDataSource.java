/*
 * Copyright (C) 2006 Transcosmos INC.
 * All Rights Reserved.
 */
package jp.co.transcosmos.dm3.dao;

import javax.sql.DataSource;

/**
 * Internal interface that defines a class having an accessible data-source. Used for sharing 
 * datasources to child objects that need them.
 * 
 * @author <a href="mailto:rick@knowleses.org">Rick Knowles</a>
 * @version $Id: HasDataSource.java,v 1.3 2007/06/13 04:58:54 rick Exp $
 */
public interface HasDataSource {
    public DataSource getDataSource();
}
