/*
 * Copyright (C) 2006 Transcosmos INC.
 * All Rights Reserved.
 */
package jp.co.transcosmos.dm3.dao;

import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.NClob;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Map;

import org.springframework.jdbc.support.rowset.SqlRowSet;

/**
 * Wrapper class to make the Spring SqlRowset methods interrogate-able by the
 * getColumnFromResultSet() method in ReflectingDAO. No longer used internally. 
 * 
 * @author <a href="mailto:rick_knowles@hotmail.com">Rick Knowles</a>
 * @version $Id: SqlRowsetAdapter.java,v 1.4 2012/08/01 09:28:36 tanaka Exp $
 * @deprecated
 */
public class SqlRowsetAdapter implements ResultSet {

    private SqlRowSet rowset;
    
    public SqlRowsetAdapter(SqlRowSet rowset) {
        this.rowset = rowset;
    }

    public boolean wasNull() throws SQLException {
        return this.rowset.wasNull();
    }

    public int findColumn(String pColumnName) throws SQLException {
        return this.rowset.findColumn(pColumnName);
    }

    public boolean first() throws SQLException {
        return this.rowset.first();
    }

    public boolean last() throws SQLException {
        return this.rowset.last();
    }

    public boolean next() throws SQLException {
        return this.rowset.next();
    }

    public boolean previous() throws SQLException {
        return this.rowset.previous();
    }

    public boolean relative(int pRows) throws SQLException {
        return this.rowset.relative(pRows);
    }
    
    public boolean absolute(int pRow) throws SQLException {
        return this.rowset.absolute(pRow);
    }

    public void afterLast() throws SQLException {
        this.rowset.afterLast();
    }

    public void beforeFirst() throws SQLException {
        this.rowset.beforeFirst();
    }

    public boolean getBoolean(int pColumnIndex) throws SQLException {
        return this.rowset.getBoolean(pColumnIndex);
    }

    public boolean getBoolean(String pColumnName) throws SQLException {
        return this.rowset.getBoolean(pColumnName);
    }

    public byte getByte(int pColumnIndex) throws SQLException {
        return this.rowset.getByte(pColumnIndex);
    }

    public byte getByte(String pColumnName) throws SQLException {
        return this.rowset.getByte(pColumnName);
    }

    public Date getDate(int pColumnIndex, Calendar pCal) throws SQLException {
        return this.rowset.getDate(pColumnIndex, pCal);
    }

    public Date getDate(int pColumnIndex) throws SQLException {
        return this.rowset.getDate(pColumnIndex);
    }

    public Date getDate(String pColumnName, Calendar pCal) throws SQLException {
        return this.rowset.getDate(pColumnName, pCal);
    }

    public Date getDate(String pColumnName) throws SQLException {
        return this.rowset.getDate(pColumnName);
    }

    public double getDouble(int pColumnIndex) throws SQLException {
        return this.rowset.getDouble(pColumnIndex);
    }

    public double getDouble(String pColumnName) throws SQLException {
        return this.rowset.getDouble(pColumnName);
    }

    public float getFloat(int pColumnIndex) throws SQLException {
        return this.rowset.getFloat(pColumnIndex);
    }

    public float getFloat(String pColumnName) throws SQLException {
        return this.rowset.getFloat(pColumnName);
    }

    public int getInt(int pColumnIndex) throws SQLException {
        return this.rowset.getInt(pColumnIndex);
    }

    public int getInt(String pColumnName) throws SQLException {
        return this.rowset.getInt(pColumnName);
    }

    public long getLong(int pColumnIndex) throws SQLException {
        return this.rowset.getLong(pColumnIndex);
    }

    public long getLong(String pColumnName) throws SQLException {
        return this.rowset.getLong(pColumnName);
    }

    public Object getObject(int pI, Map<String, Class<?>> pMap) throws SQLException {
        return this.rowset.getObject(pI, pMap);
    }

    public Object getObject(int pColumnIndex) throws SQLException {
        return this.rowset.getObject(pColumnIndex);
    }

    public Object getObject(String pColName, Map<String, Class<?>> pMap) throws SQLException {
        return this.rowset.getObject(pColName, pMap);
    }

    public Object getObject(String pColumnName) throws SQLException {
        return this.rowset.getObject(pColumnName);
    }

    public int getRow() throws SQLException {
        return this.rowset.getRow();
    }

    public short getShort(int pColumnIndex) throws SQLException {
        return this.rowset.getShort(pColumnIndex);
    }

    public short getShort(String pColumnName) throws SQLException {
        return this.rowset.getShort(pColumnName);
    }

    public String getString(int pColumnIndex) throws SQLException {
        return this.rowset.getString(pColumnIndex);
    }

    public String getString(String pColumnName) throws SQLException {
        return this.rowset.getString(pColumnName);
    }

    public Time getTime(int pColumnIndex, Calendar pCal) throws SQLException {
        return this.rowset.getTime(pColumnIndex, pCal);
    }

    public Time getTime(int pColumnIndex) throws SQLException {
        return this.rowset.getTime(pColumnIndex);
    }

    public Time getTime(String pColumnName, Calendar pCal) throws SQLException {
        return this.rowset.getTime(pColumnName, pCal);
    }

    public Time getTime(String pColumnName) throws SQLException {
        return this.rowset.getTime(pColumnName);
    }

    public Timestamp getTimestamp(int pColumnIndex, Calendar pCal) throws SQLException {
        return this.rowset.getTimestamp(pColumnIndex, pCal);
    }

    public Timestamp getTimestamp(int pColumnIndex) throws SQLException {
        return this.rowset.getTimestamp(pColumnIndex);
    }

    public Timestamp getTimestamp(String pColumnName, Calendar pCal) throws SQLException {
        return this.rowset.getTimestamp(pColumnName, pCal);
    }

    public Timestamp getTimestamp(String pColumnName) throws SQLException {
        return this.rowset.getTimestamp(pColumnName);
    }

    public boolean isAfterLast() throws SQLException {
        return this.rowset.isAfterLast();
    }

    public boolean isBeforeFirst() throws SQLException {
        return this.rowset.isBeforeFirst();
    }

    public boolean isFirst() throws SQLException {
        return this.rowset.isFirst();
    }

    public boolean isLast() throws SQLException {
        return this.rowset.isLast();
    }

    public Reader getCharacterStream(int pColumnIndex) throws SQLException {
        return new StringReader(this.rowset.getString(pColumnIndex));
    }

    public Reader getCharacterStream(String pColumnName) throws SQLException {
        return new StringReader(this.rowset.getString(pColumnName));
    }

    // Below here is unimplemented methods
    
    public Array getArray(int pI) throws SQLException {
        throw new IllegalArgumentException("Not supported in SqlRowsetAdapter");
    }

    public Array getArray(String pColName) throws SQLException {
        throw new IllegalArgumentException("Not supported in SqlRowsetAdapter");
    }

    public InputStream getAsciiStream(int pColumnIndex) throws SQLException {
        throw new IllegalArgumentException("Not supported in SqlRowsetAdapter");
    }

    public InputStream getAsciiStream(String pColumnName) throws SQLException {
        throw new IllegalArgumentException("Not supported in SqlRowsetAdapter");
    }

    /**
     * @deprecated
     */

    public BigDecimal getBigDecimal(int pColumnIndex, int pScale) throws SQLException {
        throw new IllegalArgumentException("Not supported in SqlRowsetAdapter");
    }
    public BigDecimal getBigDecimal(int pColumnIndex) throws SQLException {
        return this.rowset.getBigDecimal(pColumnIndex);
    }

    /**
     * @deprecated
     */
    public BigDecimal getBigDecimal(String pColumnName, int pScale) throws SQLException {
        throw new IllegalArgumentException("Not supported in SqlRowsetAdapter");
    }
    public BigDecimal getBigDecimal(String pColumnName) throws SQLException {
        return this.rowset.getBigDecimal(pColumnName);
    }

    public InputStream getBinaryStream(int pColumnIndex) throws SQLException {
        throw new IllegalArgumentException("Not supported in SqlRowsetAdapter");
    }

    public InputStream getBinaryStream(String pColumnName) throws SQLException {
        throw new IllegalArgumentException("Not supported in SqlRowsetAdapter");
    }

    public Blob getBlob(int pI) throws SQLException {
        throw new IllegalArgumentException("Not supported in SqlRowsetAdapter");
    }

    public Blob getBlob(String pColName) throws SQLException {
        throw new IllegalArgumentException("Not supported in SqlRowsetAdapter");
    }

    public byte[] getBytes(int pColumnIndex) throws SQLException {
        throw new IllegalArgumentException("Not supported in SqlRowsetAdapter");
    }

    public byte[] getBytes(String pColumnName) throws SQLException {
        throw new IllegalArgumentException("Not supported in SqlRowsetAdapter");
    }

    public Clob getClob(int pI) throws SQLException {
        throw new IllegalArgumentException("Not supported in SqlRowsetAdapter");
    }

    public Clob getClob(String pColName) throws SQLException {
        throw new IllegalArgumentException("Not supported in SqlRowsetAdapter");
    }

    public int getConcurrency() throws SQLException {
        throw new IllegalArgumentException("Not supported in SqlRowsetAdapter");
    }

    public String getCursorName() throws SQLException {
        throw new IllegalArgumentException("Not supported in SqlRowsetAdapter");
    }

    public int getFetchDirection() throws SQLException {
        throw new IllegalArgumentException("Not supported in SqlRowsetAdapter");
    }

    public int getFetchSize() throws SQLException {
        throw new IllegalArgumentException("Not supported in SqlRowsetAdapter");
    }

    public ResultSetMetaData getMetaData() throws SQLException {
        throw new IllegalArgumentException("Not supported in SqlRowsetAdapter");
    }

    public Ref getRef(int pI) throws SQLException {
        throw new IllegalArgumentException("Not supported in SqlRowsetAdapter");
    }

    public Ref getRef(String pColName) throws SQLException {
        throw new IllegalArgumentException("Not supported in SqlRowsetAdapter");
    }

    public Statement getStatement() throws SQLException {
        throw new IllegalArgumentException("Not supported in SqlRowsetAdapter");
    }

    public int getType() throws SQLException {
        throw new IllegalArgumentException("Not supported in SqlRowsetAdapter");
    }

    /**
     * @deprecated
     */
    public InputStream getUnicodeStream(int pColumnIndex) throws SQLException {
        throw new IllegalArgumentException("Not supported in SqlRowsetAdapter");
    }

    /**
     * @deprecated
     */
    public InputStream getUnicodeStream(String pColumnName) throws SQLException {
        throw new IllegalArgumentException("Not supported in SqlRowsetAdapter");
    }

    public URL getURL(int pColumnIndex) throws SQLException {
        throw new IllegalArgumentException("Not supported in SqlRowsetAdapter");
    }

    public URL getURL(String pColumnName) throws SQLException {
        throw new IllegalArgumentException("Not supported in SqlRowsetAdapter");
    }

    public SQLWarning getWarnings() throws SQLException {
        throw new IllegalArgumentException("Not supported in SqlRowsetAdapter");
    }

    public void insertRow() throws SQLException {
        throw new IllegalArgumentException("Not supported in SqlRowsetAdapter");
    }

    public void moveToCurrentRow() throws SQLException {
        throw new IllegalArgumentException("Not supported in SqlRowsetAdapter");
    }

    public void moveToInsertRow() throws SQLException {
        throw new IllegalArgumentException("Not supported in SqlRowsetAdapter");
    }

    public void refreshRow() throws SQLException {
        throw new IllegalArgumentException("Not supported in SqlRowsetAdapter");
    }

    public void cancelRowUpdates() throws SQLException {
        throw new IllegalArgumentException("Not supported in SqlRowsetAdapter");
    }

    public void clearWarnings() throws SQLException {
        throw new IllegalArgumentException("Not supported in SqlRowsetAdapter");
    }

    public void close() throws SQLException {
        throw new IllegalArgumentException("Not supported in SqlRowsetAdapter");
    }

    public void deleteRow() throws SQLException {
        throw new IllegalArgumentException("Not supported in SqlRowsetAdapter");
    }

    public boolean rowDeleted() throws SQLException {
        throw new IllegalArgumentException("Not supported in SqlRowsetAdapter");
    }

    public boolean rowInserted() throws SQLException {
        throw new IllegalArgumentException("Not supported in SqlRowsetAdapter");
    }

    public boolean rowUpdated() throws SQLException {
        throw new IllegalArgumentException("Not supported in SqlRowsetAdapter");
    }

    public void setFetchDirection(int pDirection) throws SQLException {
        throw new IllegalArgumentException("Not supported in SqlRowsetAdapter");
    }

    public void setFetchSize(int pRows) throws SQLException {
        throw new IllegalArgumentException("Not supported in SqlRowsetAdapter");        
    }

    public void updateArray(int pColumnIndex, Array pX) throws SQLException {
        throw new IllegalArgumentException("Not supported in SqlRowsetAdapter");
    }

    public void updateArray(String pColumnName, Array pX) throws SQLException {
        throw new IllegalArgumentException("Not supported in SqlRowsetAdapter");
    }

    public void updateAsciiStream(int pColumnIndex, InputStream pX, int pLength) throws SQLException {
        throw new IllegalArgumentException("Not supported in SqlRowsetAdapter");
    }

    public void updateAsciiStream(String pColumnName, InputStream pX, int pLength) throws SQLException {
        throw new IllegalArgumentException("Not supported in SqlRowsetAdapter");
    }

    public void updateBigDecimal(int pColumnIndex, BigDecimal pX) throws SQLException {
        throw new IllegalArgumentException("Not supported in SqlRowsetAdapter");
    }

    public void updateBigDecimal(String pColumnName, BigDecimal pX) throws SQLException {
        throw new IllegalArgumentException("Not supported in SqlRowsetAdapter");
    }

    public void updateBinaryStream(int pColumnIndex, InputStream pX, int pLength) throws SQLException {
        throw new IllegalArgumentException("Not supported in SqlRowsetAdapter");
    }

    public void updateBinaryStream(String pColumnName, InputStream pX, int pLength) throws SQLException {
        throw new IllegalArgumentException("Not supported in SqlRowsetAdapter");
    }

    public void updateBlob(int pColumnIndex, Blob pX) throws SQLException {
        throw new IllegalArgumentException("Not supported in SqlRowsetAdapter");
    }

    public void updateBlob(String pColumnName, Blob pX) throws SQLException {
        throw new IllegalArgumentException("Not supported in SqlRowsetAdapter");
    }

    public void updateBoolean(int pColumnIndex, boolean pX) throws SQLException {
        throw new IllegalArgumentException("Not supported in SqlRowsetAdapter");
    }

    public void updateBoolean(String pColumnName, boolean pX) throws SQLException {
        throw new IllegalArgumentException("Not supported in SqlRowsetAdapter");
    }

    public void updateByte(int pColumnIndex, byte pX) throws SQLException {
        throw new IllegalArgumentException("Not supported in SqlRowsetAdapter");
    }

    public void updateByte(String pColumnName, byte pX) throws SQLException {
        throw new IllegalArgumentException("Not supported in SqlRowsetAdapter");
    }

    public void updateBytes(int pColumnIndex, byte[] pX) throws SQLException {
        throw new IllegalArgumentException("Not supported in SqlRowsetAdapter");
    }

    public void updateBytes(String pColumnName, byte[] pX) throws SQLException {
        throw new IllegalArgumentException("Not supported in SqlRowsetAdapter");        
    }

    public void updateCharacterStream(int pColumnIndex, Reader pX, int pLength) throws SQLException {
        throw new IllegalArgumentException("Not supported in SqlRowsetAdapter");
    }

    public void updateCharacterStream(String pColumnName, Reader pReader, int pLength) throws SQLException {
        throw new IllegalArgumentException("Not supported in SqlRowsetAdapter");
    }

    public void updateClob(int pColumnIndex, Clob pX) throws SQLException {
        throw new IllegalArgumentException("Not supported in SqlRowsetAdapter");
    }

    public void updateClob(String pColumnName, Clob pX) throws SQLException {
        throw new IllegalArgumentException("Not supported in SqlRowsetAdapter");
    }

    public void updateDate(int pColumnIndex, Date pX) throws SQLException {
        throw new IllegalArgumentException("Not supported in SqlRowsetAdapter");
    }

    public void updateDate(String pColumnName, Date pX) throws SQLException {
        throw new IllegalArgumentException("Not supported in SqlRowsetAdapter");        
    }

    public void updateDouble(int pColumnIndex, double pX) throws SQLException {
        throw new IllegalArgumentException("Not supported in SqlRowsetAdapter");        
    }

    public void updateDouble(String pColumnName, double pX) throws SQLException {
        throw new IllegalArgumentException("Not supported in SqlRowsetAdapter");        
    }

    public void updateFloat(int pColumnIndex, float pX) throws SQLException {
        throw new IllegalArgumentException("Not supported in SqlRowsetAdapter");        
    }

    public void updateFloat(String pColumnName, float pX) throws SQLException {
        throw new IllegalArgumentException("Not supported in SqlRowsetAdapter");        
    }

    public void updateInt(int pColumnIndex, int pX) throws SQLException {
        throw new IllegalArgumentException("Not supported in SqlRowsetAdapter");        
    }

    public void updateInt(String pColumnName, int pX) throws SQLException {
        throw new IllegalArgumentException("Not supported in SqlRowsetAdapter");        
    }

    public void updateLong(int pColumnIndex, long pX) throws SQLException {
        throw new IllegalArgumentException("Not supported in SqlRowsetAdapter");        
    }

    public void updateLong(String pColumnName, long pX) throws SQLException {
        throw new IllegalArgumentException("Not supported in SqlRowsetAdapter");        
    }

    public void updateNull(int pColumnIndex) throws SQLException {
        throw new IllegalArgumentException("Not supported in SqlRowsetAdapter");        
    }

    public void updateNull(String pColumnName) throws SQLException {
        throw new IllegalArgumentException("Not supported in SqlRowsetAdapter");        
    }

    public void updateObject(int pColumnIndex, Object pX, int pScale) throws SQLException {
        throw new IllegalArgumentException("Not supported in SqlRowsetAdapter");        
    }

    public void updateObject(int pColumnIndex, Object pX) throws SQLException {
        throw new IllegalArgumentException("Not supported in SqlRowsetAdapter");        
    }

    public void updateObject(String pColumnName, Object pX, int pScale) throws SQLException {
        throw new IllegalArgumentException("Not supported in SqlRowsetAdapter");        
    }

    public void updateObject(String pColumnName, Object pX) throws SQLException {
        throw new IllegalArgumentException("Not supported in SqlRowsetAdapter");        
    }

    public void updateRef(int pColumnIndex, Ref pX) throws SQLException {
        throw new IllegalArgumentException("Not supported in SqlRowsetAdapter");        
    }

    public void updateRef(String pColumnName, Ref pX) throws SQLException {
        throw new IllegalArgumentException("Not supported in SqlRowsetAdapter");        
    }

    public void updateRow() throws SQLException {
        throw new IllegalArgumentException("Not supported in SqlRowsetAdapter");        
    }

    public void updateShort(int pColumnIndex, short pX) throws SQLException {
        throw new IllegalArgumentException("Not supported in SqlRowsetAdapter");        
    }

    public void updateShort(String pColumnName, short pX) throws SQLException {
        throw new IllegalArgumentException("Not supported in SqlRowsetAdapter");        
    }

    public void updateString(int pColumnIndex, String pX) throws SQLException {
        throw new IllegalArgumentException("Not supported in SqlRowsetAdapter");        
    }

    public void updateString(String pColumnName, String pX) throws SQLException {
        throw new IllegalArgumentException("Not supported in SqlRowsetAdapter");        
    }

    public void updateTime(int pColumnIndex, Time pX) throws SQLException {
        throw new IllegalArgumentException("Not supported in SqlRowsetAdapter");        
    }

    public void updateTime(String pColumnName, Time pX) throws SQLException {
        throw new IllegalArgumentException("Not supported in SqlRowsetAdapter");        
    }

    public void updateTimestamp(int pColumnIndex, Timestamp pX) throws SQLException {
        throw new IllegalArgumentException("Not supported in SqlRowsetAdapter");        
    }

    public void updateTimestamp(String pColumnName, Timestamp pX) throws SQLException {
        throw new IllegalArgumentException("Not supported in SqlRowsetAdapter");        
    }
    
    // below here is jdk1.6 / jdb3 updates. These are unused features that require api
    // completeness only. All throw exceptions if used, because we don't use them.

	public boolean isWrapperFor(Class<?> arg0) throws SQLException {
        throw new IllegalArgumentException("Not supported in SqlRowsetAdapter");
	}

	public <T> T unwrap(Class<T> iface) throws SQLException {
        throw new IllegalArgumentException("Not supported in SqlRowsetAdapter");
	}

	public int getHoldability() throws SQLException {
        throw new IllegalArgumentException("Not supported in SqlRowsetAdapter");
	}

	public Reader getNCharacterStream(int index) throws SQLException {
		return new StringReader(this.rowset.getString(index));
	}

	public Reader getNCharacterStream(String columnName) throws SQLException {
		return new StringReader(this.rowset.getString(columnName));
	}

	public NClob getNClob(int index) throws SQLException {
        throw new IllegalArgumentException("Not supported in SqlRowsetAdapter");
	}

	public NClob getNClob(String columnName) throws SQLException {
        throw new IllegalArgumentException("Not supported in SqlRowsetAdapter");
	}

	public String getNString(int index) throws SQLException {
        throw new IllegalArgumentException("Not supported in SqlRowsetAdapter");
	}

	public String getNString(String columnName) throws SQLException {
        throw new IllegalArgumentException("Not supported in SqlRowsetAdapter");
	}

	public RowId getRowId(int index) throws SQLException {
        throw new IllegalArgumentException("Not supported in SqlRowsetAdapter");
	}

	public RowId getRowId(String columnName) throws SQLException {
        throw new IllegalArgumentException("Not supported in SqlRowsetAdapter");
	}

	public SQLXML getSQLXML(int index) throws SQLException {
        throw new IllegalArgumentException("Not supported in SqlRowsetAdapter");
	}

	public SQLXML getSQLXML(String columnName) throws SQLException {
        throw new IllegalArgumentException("Not supported in SqlRowsetAdapter");
	}

	public boolean isClosed() throws SQLException {
        throw new IllegalArgumentException("Not supported in SqlRowsetAdapter");
	}

	public void updateAsciiStream(int index, InputStream ascii, long len)
			throws SQLException {
        throw new IllegalArgumentException("Not supported in SqlRowsetAdapter");		
	}

	public void updateAsciiStream(int index, InputStream ascii)
			throws SQLException {
        throw new IllegalArgumentException("Not supported in SqlRowsetAdapter");
	}

	public void updateAsciiStream(String columnName, InputStream ascii, long len)
			throws SQLException {
        throw new IllegalArgumentException("Not supported in SqlRowsetAdapter");
	}

	public void updateAsciiStream(String columnName, InputStream ascii)
			throws SQLException {
        throw new IllegalArgumentException("Not supported in SqlRowsetAdapter");
	}

	public void updateBinaryStream(int index, InputStream blob, long len)
			throws SQLException {
        throw new IllegalArgumentException("Not supported in SqlRowsetAdapter");
	}

	public void updateBinaryStream(int index, InputStream blob)
			throws SQLException {
        throw new IllegalArgumentException("Not supported in SqlRowsetAdapter");
	}

	public void updateBinaryStream(String columnName, InputStream blob, long len)
			throws SQLException {
        throw new IllegalArgumentException("Not supported in SqlRowsetAdapter");
	}

	public void updateBinaryStream(String columnName, InputStream blob)
			throws SQLException {
        throw new IllegalArgumentException("Not supported in SqlRowsetAdapter");
	}

	public void updateBlob(int index, InputStream blob, long len)
			throws SQLException {
        throw new IllegalArgumentException("Not supported in SqlRowsetAdapter");
	}

	public void updateBlob(int index, InputStream blob) throws SQLException {
        throw new IllegalArgumentException("Not supported in SqlRowsetAdapter");
	}

	public void updateBlob(String columnName, InputStream blob, long len)
			throws SQLException {
        throw new IllegalArgumentException("Not supported in SqlRowsetAdapter");
	}

	public void updateBlob(String columnName, InputStream blob) throws SQLException {
        throw new IllegalArgumentException("Not supported in SqlRowsetAdapter");
	}

	public void updateCharacterStream(int index, Reader clob, long len)
			throws SQLException {
        throw new IllegalArgumentException("Not supported in SqlRowsetAdapter");
	}

	public void updateCharacterStream(int index, Reader clob)
			throws SQLException {
        throw new IllegalArgumentException("Not supported in SqlRowsetAdapter");
	}

	public void updateCharacterStream(String columnName, Reader clob, long len)
			throws SQLException {
        throw new IllegalArgumentException("Not supported in SqlRowsetAdapter");
	}

	public void updateCharacterStream(String columnName, Reader clob)
			throws SQLException {
        throw new IllegalArgumentException("Not supported in SqlRowsetAdapter");
	}

	public void updateClob(int index, Reader clob, long len)
			throws SQLException {
        throw new IllegalArgumentException("Not supported in SqlRowsetAdapter");
	}

	public void updateClob(int index, Reader clob) throws SQLException {
        throw new IllegalArgumentException("Not supported in SqlRowsetAdapter");
	}

	public void updateClob(String columnName, Reader clob, long len)
			throws SQLException {
        throw new IllegalArgumentException("Not supported in SqlRowsetAdapter");
	}

	public void updateClob(String columnName, Reader clob) throws SQLException {
        throw new IllegalArgumentException("Not supported in SqlRowsetAdapter");
	}

	public void updateNCharacterStream(int index, Reader clob, long len)
			throws SQLException {
        throw new IllegalArgumentException("Not supported in SqlRowsetAdapter");
	}

	public void updateNCharacterStream(int index, Reader clob)
			throws SQLException {
        throw new IllegalArgumentException("Not supported in SqlRowsetAdapter");
	}

	public void updateNCharacterStream(String columnName, Reader clob, long len)
			throws SQLException {
        throw new IllegalArgumentException("Not supported in SqlRowsetAdapter");
	}

	public void updateNCharacterStream(String columnName, Reader clob)
			throws SQLException {
        throw new IllegalArgumentException("Not supported in SqlRowsetAdapter");
	}

	public void updateNClob(int index, NClob clob) throws SQLException {
        throw new IllegalArgumentException("Not supported in SqlRowsetAdapter");
	}

	public void updateNClob(int index, Reader clob, long len)
			throws SQLException {
        throw new IllegalArgumentException("Not supported in SqlRowsetAdapter");
	}

	public void updateNClob(int index, Reader clob) throws SQLException {
        throw new IllegalArgumentException("Not supported in SqlRowsetAdapter");
	}

	public void updateNClob(String columnName, NClob clob) throws SQLException {
        throw new IllegalArgumentException("Not supported in SqlRowsetAdapter");
	}

	public void updateNClob(String columnName, Reader clob, long len)
			throws SQLException {
        throw new IllegalArgumentException("Not supported in SqlRowsetAdapter");
	}

	public void updateNClob(String columnName, Reader clob) throws SQLException {
        throw new IllegalArgumentException("Not supported in SqlRowsetAdapter");
	}

	public void updateNString(int index, String nstr) throws SQLException {
        throw new IllegalArgumentException("Not supported in SqlRowsetAdapter");
	}

	public void updateNString(String columnName, String nstr) throws SQLException {
        throw new IllegalArgumentException("Not supported in SqlRowsetAdapter");
	}

	public void updateRowId(int index, RowId rowId) throws SQLException {
        throw new IllegalArgumentException("Not supported in SqlRowsetAdapter");
	}

	public void updateRowId(String columnName, RowId rowId) throws SQLException {
        throw new IllegalArgumentException("Not supported in SqlRowsetAdapter");
	}

	public void updateSQLXML(int index, SQLXML sqlxml) throws SQLException {
        throw new IllegalArgumentException("Not supported in SqlRowsetAdapter");
	}

	public void updateSQLXML(String columnName, SQLXML sqlxml) throws SQLException {
        throw new IllegalArgumentException("Not supported in SqlRowsetAdapter");
	}

	// 2013.3.26 H.Mizuno Java7 ‘Î‰ž Start
	@Override
	public <T> T getObject(int columnIndex, Class<T> type) throws SQLException {
		throw new IllegalArgumentException("Not supported in SqlRowsetAdapter");
	}

	@Override
	public <T> T getObject(String columnLabel, Class<T> type) throws SQLException {
		throw new IllegalArgumentException("Not supported in SqlRowsetAdapter");
	}
	// 2013.3.26 H.Mizuno Java7 ‘Î‰ž End

}
