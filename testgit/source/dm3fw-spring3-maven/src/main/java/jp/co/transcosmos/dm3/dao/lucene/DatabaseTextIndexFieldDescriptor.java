/*
 * Copyright (C) 2006 Transcosmos INC.
 * All Rights Reserved.
 */
package jp.co.transcosmos.dm3.dao.lucene;

/**
 * Definition of a single valueobject field to be indexed by the DatabaseTextIndex class. 
 * See lucene documentation for the meaning of the stored and tokenized fields.
 * 
 * @author <a href="mailto:rick@knowleses.org">Rick Knowles</a>
 * @version $Id: DatabaseTextIndexFieldDescriptor.java,v 1.2 2007/05/31 10:54:07 rick Exp $
 */
public class DatabaseTextIndexFieldDescriptor {

    private String fieldName;
    private boolean stored = false;
    private boolean tokenized = true;

    public DatabaseTextIndexFieldDescriptor() {}
    
    public DatabaseTextIndexFieldDescriptor(String fieldName) {
        this();
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public boolean isStored() {
        return stored;
    }

    public void setStored(boolean stored) {
        this.stored = stored;
    }

    public boolean isTokenized() {
        return tokenized;
    }

    public void setTokenized(boolean tokenized) {
        this.tokenized = tokenized;
    }
    
    
}
