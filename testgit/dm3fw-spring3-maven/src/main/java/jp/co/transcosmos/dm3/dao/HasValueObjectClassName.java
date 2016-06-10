/*
 * Copyright (C) 2006 Transcosmos INC.
 * All Rights Reserved.
 */
package jp.co.transcosmos.dm3.dao;

/**
 * Internal interface that defines a class having a valueobject classname defined. 
 * This is used for sharing configuration to child objects that need them.
 * 
 * @author <a href="mailto:rick@knowleses.org">Rick Knowles</a>
 * @version $Id: HasValueObjectClassName.java,v 1.3 2007/06/13 04:58:54 rick Exp $
 */
public interface HasValueObjectClassName {
    public String getValueObjectClassName();
    public void setValueObjectClassName(String valueObjectClassName);
}
