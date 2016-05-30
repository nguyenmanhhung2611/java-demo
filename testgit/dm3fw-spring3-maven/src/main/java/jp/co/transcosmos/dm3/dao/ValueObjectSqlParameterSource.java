/*
 * Copyright (C) 2006 Transcosmos INC.
 * All Rights Reserved.
 */
package jp.co.transcosmos.dm3.dao;

import jp.co.transcosmos.dm3.utils.ReflectionUtils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

/**
 * Maps valueobject attributes by name into SQL queries when named bind variables
 * are used. This is only used internally.
 * 
 * @author <a href="mailto:rick@knowleses.org">Rick Knowles</a>
 * @version $Id: ValueObjectSqlParameterSource.java,v 1.6 2012/08/01 09:28:36 tanaka Exp $
 */
public class ValueObjectSqlParameterSource implements SqlParameterSource {
    private static final Log log = LogFactory.getLog(ValueObjectSqlParameterSource.class);

    private Object valueobject;
    private boolean emptyStringsToNull;
    private boolean nullStringsToEmpty;
    
    public ValueObjectSqlParameterSource(Object valueobject,
            boolean nullStringsToEmpty, boolean emptyStringsToNull) {
        this.valueobject = valueobject;
        this.nullStringsToEmpty = nullStringsToEmpty;
        this.emptyStringsToNull = emptyStringsToNull;
    }
    
    public int getSqlType(String fieldName) {
        return TYPE_UNKNOWN;
    }

    public Object getValue(String fieldName) throws IllegalArgumentException {
        try {
            Class<?> type = ReflectionUtils.getFieldTypeByGetter(this.valueobject.getClass(), fieldName);
            Object value = ReflectionUtils.getFieldValueByGetter(this.valueobject, fieldName);
            
            // If it's a string, check for the conversion cases and replace if necessary
            if (this.emptyStringsToNull && (value != null) && value.equals("")) {
                log.debug("Converting empty string to null :" + fieldName);
                value = null;
            } else if (this.nullStringsToEmpty && (value == null) && type.equals(String.class)) {
                log.debug("Converting null string to empty :" + fieldName);
                value = "";
            } else if ((value != null) && (type.isAssignableFrom(Boolean.class) 
                    || type.equals(Boolean.TYPE))) {
                log.debug("Converting boolean to one/zero");
                value = value.equals(Boolean.FALSE) ? new Integer(0) : new Integer(1);
            }
            log.info("Setting :" + fieldName + " = " + value);
            return value;
        } catch (Throwable err) {
            throw new IllegalArgumentException("Error getting field " + fieldName, err);
        }
    }

    public boolean hasValue(String fieldName) {
        try {
            ReflectionUtils.getFieldValueByGetter(this.valueobject, fieldName);
            return true;
        } catch (Throwable err) {
            return false;
        }
    }

    // 2013.3.25 H.Mizuno Spring 2.5 ëŒâûÇ≈í«â¡Å@Start
	@Override
	public String getTypeName(String paramName) {
		return null;
	}
    // 2013.3.25 H.Mizuno Spring 2.5 ëŒâûÇ≈í«â¡Å@End

}
