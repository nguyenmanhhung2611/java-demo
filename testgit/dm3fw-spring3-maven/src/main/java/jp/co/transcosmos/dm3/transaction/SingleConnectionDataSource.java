package jp.co.transcosmos.dm3.transaction;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SingleConnectionDataSource implements DataSource {
    private final Log log = LogFactory.getLog(SingleConnectionDataSource.class);
    
    private DataSource dataSource;
    private RequestScopeConnection wrapper;
    
    @Override
    public Connection getConnection() throws SQLException {
        synchronized (this) {
            if (this.wrapper == null) {
                this.wrapper = new RequestScopeConnection(this.dataSource.getConnection());
            }
            log.trace("Returning wrapper instance: " + this.wrapper);
            return wrapper;
        }
    }
    
    public void forceImmediateCommit() {
        synchronized (this) {
            if (wrapper != null) {
                try {
                    log.trace("Committing wrapper instance: " + this.wrapper);
                    wrapper.reallyCommit();
                } catch (SQLException err) {
                    throw new RuntimeException("Error forcing immediate commit", err);
                }
            } else {
                log.debug("Ignoring commit request, no connection wrapper available");
            }
        }
    }
    
    public void forceImmediateRollback() {
        synchronized (this) {
            if (wrapper != null) {
                try {
                    log.trace("Rolling back wrapper instance: " + this.wrapper);
                    wrapper.reallyRollback();
                } catch (SQLException err) {
                    throw new RuntimeException("Error forcing immediate rollback", err);
                }
            } else {
                log.debug("Ignoring rollback request, no connection wrapper available");
            }
        }
    }
    
    public void closeConnection(boolean failed) {
        synchronized (this) {
            if (wrapper != null) {
                if (failed) {
                    try {
                        log.trace("Rolling back wrapper instance: " + this.wrapper);
                        wrapper.reallyRollback();
                    } catch (SQLException err) {
                        throw new RuntimeException("Error during rollback on close", err);
                    }
                } else {
                    try {
                        log.trace("Committing wrapper instance: " + this.wrapper);
                        wrapper.reallyCommit();
                    } catch (SQLException err) {
                        throw new RuntimeException("Error during commit on close", err);
                    }
                }
                try {
                    log.trace("Closing wrapper instance: " + this.wrapper);
                    wrapper.reallyClose();
                } catch (SQLException err) {
                    throw new RuntimeException("Error forcing immediate rollback", err);
                }
                this.wrapper = null;
            } else {
                log.trace("Ignoring rollback request, no connection wrapper available");
            }
        }
    }

    public Connection getConnection(String pUsername, String pPassword) throws SQLException {
        log.info("Passing through getConnection(username, password) call to nested datasource");
        return this.dataSource.getConnection(pUsername, pPassword);
    }

    public void setDataSource(DataSource pDataSource) {
        this.dataSource = pDataSource;
    }

    public int getLoginTimeout() throws SQLException {
        return this.dataSource.getLoginTimeout();
    }

    public PrintWriter getLogWriter() throws SQLException {
        return this.dataSource.getLogWriter();
    }

    public void setLoginTimeout(int pSeconds) throws SQLException {
        this.dataSource.setLoginTimeout(pSeconds);
    }

    public void setLogWriter(PrintWriter pOut) throws SQLException {
        this.dataSource.setLogWriter(pOut);
    }

    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return this.dataSource.isWrapperFor(iface);
    }

    public <T> T unwrap(Class<T> iface) throws SQLException {
        return this.dataSource.unwrap(iface);
    }

	// 2013.3.26 H.Mizuno Java 7 ëŒâûÅ@Start
    @Override
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		return this.dataSource.getParentLogger();
	}
	// 2013.3.26 H.Mizuno Java 7 ëŒâûÅ@End

}
