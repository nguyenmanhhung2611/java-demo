/*
 * Copyright (C) 2006 Transcosmos INC.
 * All Rights Reserved.
 */
package jp.co.transcosmos.dm3.dao;

import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import jp.co.transcosmos.dm3.utils.ReflectionUtils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.RowMapper;

/**
 * Writes from the resultset row into a blank valueobject using reflection to map 
 * the fields. This is used internally by the {@link ReflectingDAO}
 * 
 * @author <a href="mailto:rick@knowleses.org">Rick Knowles</a>
 * @version $Id: ReflectingRowMapper.java,v 1.7 2007/06/13 06:32:03 rick Exp $
 * @see ReflectingDAO
 */
public class ReflectingRowMapper<E> implements RowMapper<E> {
    private static final Log log = LogFactory.getLog(ReflectingRowMapper.class);
    
    private Class<? extends E> valueobjectClass;
    private String fieldNames[];
    private String fieldAliases[];
    private DerivedFieldEvaluator<E> columnCalculator;
    private boolean nullResultIfAllFieldsAreNull;
    private boolean emptyStringsToNull;
    private boolean nullStringsToEmpty;

    public ReflectingRowMapper(Class<? extends E> valueobjectClass, 
            String fieldNames[], 
            String fieldAliases[], 
            boolean nullResultIfAllFieldsAreNull,
            boolean nullStringsToEmpty, 
            boolean emptyStringsToNull, 
            DerivedFieldEvaluator<E> columnCalculator) {
        this.valueobjectClass = valueobjectClass;
        this.fieldNames = fieldNames;
        this.fieldAliases = fieldAliases;
        this.nullResultIfAllFieldsAreNull = nullResultIfAllFieldsAreNull;
        this.nullStringsToEmpty = nullStringsToEmpty;
        this.emptyStringsToNull = emptyStringsToNull;
        this.columnCalculator = columnCalculator;
    }
    
    /**
     * Transfer a single resultset row into the configured valueobject.
     */
    public E mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        E result = newInstance();
        char buffer[] = new char[256]; // used to read in from text columns
        boolean foundNonNullValue = false;
        for (int n = 0; n < this.fieldNames.length; n++) {
            try {
                String fieldName = this.fieldNames[n];
                Class<?> fieldType = ReflectionUtils.getFieldTypeByGetter(
                        result.getClass(), fieldName);
                
                log.debug("Getting field: " + fieldName + " from db column: " + 
                		this.fieldAliases[n]);
                Object fieldValue = getColumnFromResultSet(fieldType, resultSet, 
                		this.fieldAliases[n], buffer);
                if (fieldValue != null) {
                    foundNonNullValue = true;
                }

                if (this.emptyStringsToNull && (fieldValue != null) && fieldValue.equals("")) {
                    log.debug("Converting empty string to null :" + fieldName);
                    fieldValue = null;
                } else if (this.nullStringsToEmpty && (fieldValue == null) && 
                        ReflectionUtils.getFieldTypeByGetter(result.getClass(), 
                                fieldName).equals(String.class)) {
                    log.debug("Converting null string to empty :" + fieldName);
                    fieldValue = "";
                }
                
                ReflectionUtils.setFieldValueBySetter(result, fieldName, fieldValue);
            } catch (SQLException err) { 
                throw err;
            } catch (Throwable err) {
                throw new RuntimeException("Error mapping output results: " + 
                        this.valueobjectClass.getName() + "." + 
                        this.fieldNames[n], err);
            }
        }
        if (foundNonNullValue || !this.nullResultIfAllFieldsAreNull) {
            if (this.columnCalculator != null) {
                this.columnCalculator.setDerivedFields(result);
            }
            return result;
        } else {
            return null;
        }
    }
    
    /**
     * Create a blank instance of the valueobject
     * @return
     */
    protected E newInstance() {
        try {
            return this.valueobjectClass.newInstance();
        } catch (Throwable err) {
            throw new RuntimeException("Error instantiating valueobject class", err);
        }        
    }

    /**
     * Maps the DB column type to a java type resultset.getXXX() command.
     */
    protected static Object getColumnFromResultSet(Class<?> fieldType, ResultSet resultSet, 
            String columnAlias, char buffer[]) throws SQLException {
        try {
            if (fieldType.isAssignableFrom(Long.class)|| fieldType.equals(Long.TYPE)) {
                long result = resultSet.getLong(columnAlias);
                return resultSet.wasNull() ? null : new Long(result);
            } else if (fieldType.isAssignableFrom(Date.class)) {
                return resultSet.getTimestamp(columnAlias);
            } else if (fieldType.isAssignableFrom(Boolean.class) || fieldType.equals(Boolean.TYPE)) {
                long result = resultSet.getLong(columnAlias);
                return resultSet.wasNull() ? null : ((result == 0) ? Boolean.FALSE : Boolean.TRUE);
            } else if (fieldType.isAssignableFrom(Integer.class)|| fieldType.equals(Integer.TYPE)) {
                int result = resultSet.getInt(columnAlias);
                return resultSet.wasNull() ? null : new Integer(result);
            } else if (fieldType.isAssignableFrom(Float.class)|| fieldType.equals(Float.TYPE)) {
                float result = resultSet.getFloat(columnAlias);
                return resultSet.wasNull() ? null : new Float(result);
            } else if (fieldType.isAssignableFrom(Double.class)|| fieldType.equals(Double.TYPE)) {
                double result = resultSet.getDouble(columnAlias);
                return resultSet.wasNull() ? null : new Double(result);
// 2013.4.1 H.Mizuno ORACLE シーケンスのバグに対応 Start
            } else if (fieldType.isAssignableFrom(BigDecimal.class)) {
            	BigDecimal result = resultSet.getBigDecimal(columnAlias);
                return resultSet.wasNull() ? null : result;
// 2013.4.1 H.Mizuno ORACLE シーケンスのバグに対応 End
            } else {
                // Get the clob contents, and read it into a string
                Reader content = resultSet.getCharacterStream(columnAlias);

                if (content == null) {
                    return null;
                } else try {
                    if (buffer == null) {
                        buffer = new char[256];
                    }
                    int readChars = content.read(buffer);
                    if (readChars == -1) {
                        return "";
                    }
                    int next = content.read();
                    if (next == -1) {
                        return new String(buffer, 0, readChars);
                    }
                    StringBuffer out = new StringBuffer();
                    out.append(buffer, 0, readChars).append((char) next);
                    while ((readChars = content.read(buffer)) != -1)
                        out.append(buffer, 0, readChars);
                    return out.toString();
                } finally {
                    try {content.close();} catch (IOException err) {}
                }
            }
        } catch (SQLException err) {
            log.error("Error on " + columnAlias + " type=" + fieldType, err);
            throw err;
        } catch (Throwable err) {
            log.error("Error on " + columnAlias + " type=" + fieldType, err);
            throw new SQLException("Error reading clob from database");
        }
    }

}
