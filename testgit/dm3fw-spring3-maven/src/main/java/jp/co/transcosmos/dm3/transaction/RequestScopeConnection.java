/*
 * &copy; Trans-cosmos Inc. 2006, 2007.
 * All rights reserved.
 */
package jp.co.transcosmos.dm3.transaction;

import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Struct;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Connection wrapper class which suppresses the commit/rollback calls, allowing the 
 * RequestScopeDataSource class to manage the transactions to be at request-scope.
 * <p>
 * This class should not be used raw - see the RequestScopeDataSource and 
 * RolbackOnExceptionInterceptor classes for usage instructions.
 * 
 * @author <a href="mailto:rick@knowleses.org">Rick Knowles</a>
 * @version $Id: RequestScopeConnection.java,v 1.3 2012/08/01 09:28:36 tanaka Exp $
 */
public class RequestScopeConnection implements Connection {
    private static final Log log = LogFactory.getLog(RequestScopeConnection.class);

    private Connection actualConnection;
    private boolean initialAutocommit;
    private boolean rolledBack;
    
    public RequestScopeConnection(Connection connection)
            throws SQLException {
        this.actualConnection = connection;
        this.initialAutocommit = this.actualConnection.getAutoCommit();
        if (this.initialAutocommit && !this.actualConnection.isReadOnly()) {
            this.actualConnection.setAutoCommit(false);
        }
    }
    
    public void reallyClose() throws SQLException {
        if (!this.actualConnection.isClosed()) {
            if (!this.actualConnection.isReadOnly()) {
                if (this.rolledBack) {
                    reallyRollback();
                } else {
                    reallyCommit();
                }
            }
            
            if (this.initialAutocommit) {
                this.actualConnection.setAutoCommit(true);
            }
            this.actualConnection.close();
        }
    }

    public void reallyCommit() throws SQLException {
        log.info("Committing request scoped transaction");
        this.actualConnection.commit();
        this.rolledBack = false;
    }

    public void reallyRollback() throws SQLException {
        log.info("Rolling-back request scoped transaction");
        this.actualConnection.rollback();
        this.rolledBack = false;
    }
    
    public void close() throws SQLException {
        log.trace("Suppressing close call - will " + 
                (this.rolledBack ? "rollback" : "commit") 
                    + " at the end of request");
    }

    public void commit() throws SQLException {
        log.trace("Suppressing commit call - " + 
                (this.rolledBack ? "rollback" : "commit") 
                    + " at the end of request");
    }

    public void rollback() throws SQLException {
        this.rolledBack = true;
        log.trace("Suppressing rollback call - will rollback at the end of request");
    }

    public boolean getAutoCommit() throws SQLException {
        return false; // Suppressing getAutoCommit call - always off
    }

    public void setAutoCommit(boolean pAutoCommit) throws SQLException {
        // Suppressing setAutoCommit call - always off
    }

    public boolean isClosed() throws SQLException {
        return this.actualConnection.isClosed();
    }
    
    public void releaseSavepoint(Savepoint pSavepoint) throws SQLException {
        throw new SQLException("savepoint rollbacks not supported by request scope connection");
    }

    public void rollback(Savepoint pSavepoint) throws SQLException {
        throw new SQLException("savepoint rollbacks not supported by request scope connection");
    }

    public Savepoint setSavepoint() throws SQLException {
        throw new SQLException("savepoints not supported by request scope connection");
    }

    public Savepoint setSavepoint(String pName) throws SQLException {        
        throw new SQLException("savepoints not supported by request scope connection");
    }
    
    public Connection getDelegate() {
        return this.actualConnection;
    }
    
    // Below this line is standard proxying methods - boilerplate
    
    public void clearWarnings() throws SQLException {
        this.actualConnection.clearWarnings();
    }

    public Statement createStatement() throws SQLException {
        return this.actualConnection.createStatement();
    }

    public Statement createStatement(int pResultSetType, int pResultSetConcurrency, 
            int pResultSetHoldability) throws SQLException {
        return this.actualConnection.createStatement(pResultSetType, pResultSetConcurrency, 
                pResultSetHoldability);
    }

    public Statement createStatement(int pResultSetType, int pResultSetConcurrency) 
            throws SQLException {
        return this.actualConnection.createStatement(pResultSetType, pResultSetConcurrency);
    }

    public String getCatalog() throws SQLException {
        return this.actualConnection.getCatalog();
    }

    public int getHoldability() throws SQLException {
        return this.actualConnection.getHoldability();
    }

    public DatabaseMetaData getMetaData() throws SQLException {
        return this.actualConnection.getMetaData();
    }

    public int getTransactionIsolation() throws SQLException {
        return this.actualConnection.getTransactionIsolation();
    }

    public Map<String, Class<?>> getTypeMap() throws SQLException {
        return this.actualConnection.getTypeMap();
    }

    public SQLWarning getWarnings() throws SQLException {
        return this.actualConnection.getWarnings();
    }

    public boolean isReadOnly() throws SQLException {
        return this.actualConnection.isReadOnly();
    }

    public String nativeSQL(String pSql) throws SQLException {
        return this.actualConnection.nativeSQL(pSql);
    }

    public CallableStatement prepareCall(String pSql, int pResultSetType, 
            int pResultSetConcurrency, int pResultSetHoldability) throws SQLException {
        return this.actualConnection.prepareCall(pSql, pResultSetType, 
                pResultSetConcurrency, pResultSetHoldability);
    }

    public CallableStatement prepareCall(String pSql, int pResultSetType, 
            int pResultSetConcurrency) throws SQLException {
        return this.actualConnection.prepareCall(pSql, pResultSetType, 
                pResultSetConcurrency);
    }

    public CallableStatement prepareCall(String pSql) throws SQLException {
        return this.actualConnection.prepareCall(pSql);
    }

    public PreparedStatement prepareStatement(String pSql, int pResultSetType, 
            int pResultSetConcurrency, int pResultSetHoldability) throws SQLException {
        return this.actualConnection.prepareStatement(pSql, pResultSetType, 
                pResultSetConcurrency, pResultSetHoldability);
    }

    public PreparedStatement prepareStatement(String pSql, int pResultSetType, 
            int pResultSetConcurrency) throws SQLException {
        return this.actualConnection.prepareStatement(pSql, pResultSetType, 
                pResultSetConcurrency);
    }

    public PreparedStatement prepareStatement(String pSql, int pAutoGeneratedKeys) 
            throws SQLException {
// 2013.03.25 H.Mizuno オートナンバー時に発生する問題を修正
//        return this.actualConnection.prepareStatement(pSql);
      return this.actualConnection.prepareStatement(pSql, pAutoGeneratedKeys);
    }

    public PreparedStatement prepareStatement(String pSql, int[] pColumnIndexes) 
            throws SQLException {
        return this.actualConnection.prepareStatement(pSql, pColumnIndexes);
    }

    public PreparedStatement prepareStatement(String pSql, String[] pColumnNames) 
            throws SQLException {
        return this.actualConnection.prepareStatement(pSql, pColumnNames);
    }

    public PreparedStatement prepareStatement(String pSql) throws SQLException {
        return this.actualConnection.prepareStatement(pSql);
    }

    public void setCatalog(String pCatalog) throws SQLException {
        this.actualConnection.setCatalog(pCatalog);
    }

    public void setHoldability(int pHoldability) throws SQLException {
        this.actualConnection.setHoldability(pHoldability);
    }

    public void setReadOnly(boolean pReadOnly) throws SQLException {
        this.actualConnection.setReadOnly(pReadOnly);
    }

    public void setTransactionIsolation(int pLevel) throws SQLException {
        this.actualConnection.setTransactionIsolation(pLevel);
    }

    public void setTypeMap(Map<String, Class<?>> pMap) throws SQLException {
        this.actualConnection.setTypeMap(pMap);
    }

    // jdk1.6 
    
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return this.actualConnection.isWrapperFor(iface);
    }

    public <T> T unwrap(Class<T> iface) throws SQLException {
        return this.actualConnection.unwrap(iface);
    }

    public Array createArrayOf(String str, Object[] arr) throws SQLException {
        return this.actualConnection.createArrayOf(str, arr);
    }

    public Blob createBlob() throws SQLException {
        return this.actualConnection.createBlob();
    }

    public Clob createClob() throws SQLException {
        return this.actualConnection.createClob();
    }

    public NClob createNClob() throws SQLException {
        return this.actualConnection.createNClob();
    }

    public SQLXML createSQLXML() throws SQLException {
        return this.actualConnection.createSQLXML();
    }

    public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
        return this.actualConnection.createStruct(typeName, attributes);
    }

    public Properties getClientInfo() throws SQLException {
        return this.actualConnection.getClientInfo();
    }

    public String getClientInfo(String name) throws SQLException {
        return this.actualConnection.getClientInfo(name);
    }

    public boolean isValid(int timeout) throws SQLException {
        return this.actualConnection.isValid(timeout);
    }

    public void setClientInfo(Properties properties) throws SQLClientInfoException {
         this.actualConnection.setClientInfo(properties);
    }

    public void setClientInfo(String name, String value) throws SQLClientInfoException {
         this.actualConnection.setClientInfo(name, value);
    }

    // java7 対応 H.Mizuno Start
    @Override
	public void setSchema(String schema) throws SQLException {
    	this.actualConnection.setSchema(schema);
	}

	@Override
	public String getSchema() throws SQLException {
		return this.actualConnection.getSchema();
	}

	@Override
	public void abort(Executor executor) throws SQLException {
		this.actualConnection.abort(executor);
	}

	@Override
	public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {
		this.actualConnection.setNetworkTimeout(executor, milliseconds);
	}

	@Override
	public int getNetworkTimeout() throws SQLException {
		return this.actualConnection.getNetworkTimeout();
	}

}
