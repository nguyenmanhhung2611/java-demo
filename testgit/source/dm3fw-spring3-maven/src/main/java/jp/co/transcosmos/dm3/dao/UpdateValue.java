/*
 * Copyright (C) 2006 Transcosmos INC.
 * All Rights Reserved.
 */
package jp.co.transcosmos.dm3.dao;

import java.util.List;

/**
 * Simple name=value update expression
 * 
 * @author <a href="mailto:rick@knowleses.org">Rick Knowles</a>
 * @version $Id: UpdateValue.java,v 1.1 2007/10/19 07:58:11 rick Exp $
 * @see UpdateExpression
 */
public class UpdateValue implements UpdateExpression {

    private String alias;
    private String fieldName;
    private Object value;
        
    public UpdateValue(String fieldName, Object value) {
        this.alias = null;
        this.fieldName = fieldName;
        this.value = value;
    }
    
    public UpdateValue(String alias, String fieldName, Object value) {
        this.alias = alias;
        this.fieldName = fieldName;
        this.value = value;
    }
    
    public String buildSQL(String thisAlias, AliasDotColumnResolver resolver, List<Object> params) {
        if (this.value != null) {
            params.add(this.value);
            return resolver.lookupAliasDotColumnName(this.fieldName, this.alias, thisAlias) + " = ?"; 
        } else {
            return resolver.lookupAliasDotColumnName(this.fieldName, this.alias, thisAlias) + " = NULL"; 
        }
    }
}

