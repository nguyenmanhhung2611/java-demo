/*
 * &copy; Trans-cosmos Inc. 2006, 2007.
 * All rights reserved.
 */
package jp.co.transcosmos.dm3.transaction;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

/**
 * An object designed to be scoped to the request, which assigns a single
 * JDBC transaction (shared across all JDBC calls within the request). Repeated calls 
 * within the same request to this object for getConnection() will return the same 
 * RequestScopeConnection instance.
 * <p>
 * NOT THREADSAFE, because synchronizing causes problems with jrockit and cglib. 
 * This object is expected to be allocated on a per-request basis by spring, and therefore
 * not be subject to concurrency problems.
 * <p>
 * This object should be mounted in Spring as follows:
 * <p><code>
 * &lt;bean id="requestScopeDataSource" class="jp.co.transcosmos.dm3.transaction.RequestScopeDataSource" scope="request"&gt;<br/>
 * &#0160;&#0160;&lt;property name="dataSource" ref="dataSource"/&gt;<br/>
 * &#0160;&#0160;&lt;aop:scoped-proxy/&gt;<br/>
 * &lt;/bean&gt;<br/></code>
 * <p>
 * where the dataSource object is the plain JDBC datasource we want to wrap so that
 * it's transactions become request-scoped. It can then be used in place of a normal 
 * JDBC datasource for allocation to DAO classes, etc.
 * <p>
 * This object should be used in conjunction with the RollbackOnExceptionInterceptor to
 * ensure that connections are properly rolled back if an error is detected.
 * 
 * @author <a href="mailto:rick@knowleses.org">Rick Knowles</a>
 * @version $Id: RequestScopeDataSource.java,v 1.3 2012/08/01 09:28:36 tanaka Exp $
 */
public class RequestScopeDataSource implements DataSource, BeanNameAware {
    private static final Log log = LogFactory.getLog(RequestScopeDataSource.class);

    public static final String ERROR_CAUGHT = "jp.co.transcosmos.dm3.transaction.errorCaught";
    
    private DataSource dataSource;
    private RequestScopeConnection wrapper;
    private String beanName;

    /**
     * �擾�����c�a�ڑ��̎����`�F�b�N���s���ꍇ�A���̃v���p�e�B�Ƀ`�F�b�N�p SQL ����ݒ肷��B<br>
     * �ʏ�A�����̋@�\�͐ڑ��v�[�����Ŏ����Ă���̂ŁA��������g�p���鎖�B<br>
     * ���̃v���p�e�B�́A�����`�F�b�N���s���Ȃ��ڑ��v�[���p�̋@�\�B<br>
     */
    private String checkBeforeGetSQL;

    // default commitOrRollbackOnDestruct=true plus the interceptor, instead of 
    // using the filter, since the filter is difficult to maintain (all those extra mappings)
    private boolean commitOrRollbackOnDestruct; // = true; 
    private boolean destructorAssigned;
    
// 2015.07.07 H.Mizuno �ڑ��`�F�b�N�����̃o�O�C�� start
    /**
     * checkBeforeGetSQL ��ݒ肵�����ɐڑ������g���C�������l�B�i�f�t�H���g 5��j<br>
     * checkBeforeGetSQL �����ݒ�̏ꍇ�͂��̐ݒ�l�͖��������B<br>
     */
    private int maxRetryCnt = 5;
    
    /**
     * �ڑ������g���C���鎞�̏���l��ݒ肷��B<br>
     * ��checkBeforeGetSQL ���ݒ肳��Ă���ꍇ�̂ݗL��<br>
     * <br>
     * @param maxRetryCnt �ڑ������g���C���鎞�̏���l
     */
    public void setMaxRetryCnt(int maxRetryCnt) {
		this.maxRetryCnt = maxRetryCnt;
	}
// 2015.07.07 H.Mizuno �ڑ��`�F�b�N�����̃o�O�C�� end
    

	public void setDataSource(DataSource pDataSource) {
        this.dataSource = pDataSource;
    }
    
    public void setCommitOrRollbackOnDestruct(boolean commitOrRollbackOnDestruct) {
        this.commitOrRollbackOnDestruct = commitOrRollbackOnDestruct;
    }

    public void setCheckBeforeGetSQL(String checkBeforeGetSQL) {
        this.checkBeforeGetSQL = checkBeforeGetSQL;
    }

    public Connection getConnection() throws SQLException {
        synchronized (this) {
            if (this.wrapper == null) {
                assignDestructorCallback();
                Connection connection = getTestedConnection();
                this.wrapper = new RequestScopeConnection(connection);
            }
            return wrapper;
        }
    }
    
    private Connection getTestedConnection() throws SQLException {
        if (this.checkBeforeGetSQL == null) {
            return this.dataSource.getConnection();
        }
        Connection tested = null;
// 2015.07.07 H.Mizuno �ڑ��`�F�b�N�����̃o�O�C�� start
        int retryCnt = 0;
// 2015.07.07 H.Mizuno �ڑ��`�F�b�N�����̃o�O�C�� start
        while (tested == null) {
            PreparedStatement qryKeepAlive = null;
            try {
                tested = this.dataSource.getConnection();
                qryKeepAlive = tested.prepareStatement(this.checkBeforeGetSQL);
                qryKeepAlive.execute();
            } catch (Throwable err) {
// 2015.07.07 H.Mizuno �ڑ��`�F�b�N�����̃o�O�C�� start
// ������A�̏����́A�ʏ�A�ڑ��v�[�����̎����`�F�b�N���s���̂ŁA���W�b�N���őΉ����鎖�͂Ȃ��B
//�@�g�p����ڑ��v�[���������̋@�\���T�|�[�g���Ă��Ȃ��ꍇ�p�ɑ΍�Ƃ��Ď����ꂽ�@�\�B
//                try {tested.close();} catch (SQLException err2) {}
                try {
                	++retryCnt;
                	// ����񐔂𒴂����ꍇ�͗�O���X���[���Ē��f����B
                	if (retryCnt > this.maxRetryCnt) {
                		throw new RuntimeException("get active connection failed.");
                	}
                	if (tested != null) tested.close();
                } catch (SQLException err2) {}
// 2015.07.07 H.Mizuno �ڑ��`�F�b�N�����̃o�O�C�� start
                log.warn("NOTE: Connection keep-alive failed - discarding and " +
                        "getting a new connection from the pool", err);
                tested = null;
            } finally {
                if (qryKeepAlive != null) {
                    try {qryKeepAlive.close();} catch (SQLException err2) {}
                }
            }
        }
        return tested;
    }

    private void assignDestructorCallback() {
        if (!this.destructorAssigned && this.commitOrRollbackOnDestruct) {
            this.destructorAssigned = true;
            RequestAttributes current = RequestContextHolder.getRequestAttributes();
            current.registerDestructionCallback(this.beanName, new Runnable() {
                public void run() {
                    synchronized (this) {
                        if (wrapper != null) {
                            try {
                                RequestAttributes current = 
                                    RequestContextHolder.getRequestAttributes();
                                if (current.getAttribute(ERROR_CAUGHT, 
                                        RequestAttributes.SCOPE_REQUEST) != null) {
                                    log.info("Beginning TX rollback and close - " + beanName);
                                    wrapper.rollback();
                                } else {
                                    log.info("Beginning TX commit and close - " + beanName);
                                }
                                wrapper.reallyClose();
                            } catch (SQLException err) {
                                throw new RuntimeException("Error closing connection - " + 
                                        beanName, err);
                            }
                        } else {
                            log.debug("Ignoring close request, no connection wrapper available - " + beanName);
                        }
                    }
                }
            }, RequestAttributes.SCOPE_REQUEST);
        }
    }
    
    public void setBeanName(String pBeanName) {
        this.beanName = pBeanName;
    }

    public Connection getConnection(String pUsername, String pPassword) throws SQLException {
        log.info("Passing through getConnection(username, password) call to nested datasource");
        return this.dataSource.getConnection(pUsername, pPassword);
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
    
    public void forceImmediateCommit() {
        synchronized (this) {
            if (wrapper != null) {
                try {
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
                    wrapper.reallyRollback();
                } catch (SQLException err) {
                    throw new RuntimeException("Error forcing immediate rollback", err);
                }
            } else {
                log.debug("Ignoring rollback request, no connection wrapper available");
            }
        }
    }
    
    public static void closeCurrentTransaction(String beanName, boolean failed) {
        RequestScopeDataSource ds = getCurrentDataSource(beanName);
        if (ds == null) {
            ds = getCurrentDataSource("scopedTarget." + beanName);
        }
        if (ds == null) {
            log.debug("No RequestScopeDataSource found at " + beanName + 
                    " - ignoring closeCurrentTransaction");
        } else {
            synchronized (ds) {
                if (ds.wrapper != null) {
                    log.info("closeCurrentTransaction(" + beanName + ", " + failed + ")");
                    try {
                        if (failed) {
                            ds.wrapper.rollback();
                        }
                        ds.wrapper.reallyClose();
                        ds.wrapper = null;
                    } catch (SQLException err) {
                        throw new RuntimeException("Error closing transaction", err);
                    }
                } else {
                    log.debug("No JDBC wrapper available - ignoring closeCurrentTransaction");
                }
            }
        }
    }
    
    public static RequestScopeDataSource getCurrentDataSource(String beanName) {
        RequestAttributes current = RequestContextHolder.getRequestAttributes();
        return (RequestScopeDataSource) current.getAttribute(beanName, RequestAttributes.SCOPE_REQUEST);        
    }

    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return this.dataSource.isWrapperFor(iface);
    }

    public <T> T unwrap(Class<T> iface) throws SQLException {
        return this.dataSource.unwrap(iface);
    }

    // 2013.3.26 H.Mizuno Java7 �Ή��@Start
    @Override
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		return this.dataSource.getParentLogger();
	}
    // 2013.3.26 H.Mizuno Java7 �Ή��@End

}
