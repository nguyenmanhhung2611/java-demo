package jp.co.transcosmos.dm3.login;

import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import jp.co.transcosmos.dm3.dao.DAO;
import jp.co.transcosmos.dm3.dao.DAOCriteria;
import jp.co.transcosmos.dm3.dao.ReadOnlyDAO;
import jp.co.transcosmos.dm3.login.support.LoginModificationSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

/**
 * An object that functions as a credentials object, i.e. we query this for
 * auth info, and accept it as given that the reference is our own user's
 * auth info.
 * 
 * We intend for this object to be a session or request scoped bean that Spring assigns
 * to commands (via a proxy), and they query this for their auth requirements
 * with no request or session details being passed as arguments.
 * 
 * @author <a href="mailto:rick@knowleses.org">Rick Knowles</a>
 * @version $Id: Authentication.java,v 1.7 2012/08/01 09:28:36 tanaka Exp $
 */
public class Authentication extends LoginModificationSupport {
    private static final Log log = LogFactory.getLog(Authentication.class); 

    private RequestAttributes atts;
    private ReadOnlyDAO<LoginUser> userDAO;
    private DAO<LoginUser> writeableUserDAO;
    private String orderByParameter;
    private String userIdParameter = "userId";    
    private String loginIdParameter = "loginId";    
    private long refreshIntervalSeconds = 0L;

    public Authentication() {
    }
    
    public Authentication(RequestAttributes atts) {
        this();
        this.atts = atts;
    }
    
    public void setUserDAO(ReadOnlyDAO<LoginUser> userDAO) {
        this.userDAO = userDAO;
        if ((this.writeableUserDAO == null) && (userDAO instanceof DAO)) {
            this.writeableUserDAO = (DAO<LoginUser>) userDAO; 
        }
    }
    
    public void setWriteableUserDAO(DAO<LoginUser> writeableUserDAO) {
        this.writeableUserDAO = writeableUserDAO;
        if (this.userDAO == null) {
            this.userDAO = writeableUserDAO; 
        }
    }

    public void setOrderByParameter(String pOrderByParameter) {
        this.orderByParameter = pOrderByParameter;
    }
    
    public void setLoginIdParameter(String pLoginIdParameter) {
        this.loginIdParameter = pLoginIdParameter;
    }
    
    public void setRefreshIntervalSeconds(long pRefreshIntervalSeconds) {
        this.refreshIntervalSeconds = pRefreshIntervalSeconds;
    }

    public void setUserIdParameter(String pUserIdParameter) {
        this.userIdParameter = pUserIdParameter;
    }

    public void setLoggedInUser(LoginUser user) {
        super.setLoggedInUser(getSession(), user);
        super.addRolesetToSession(getSession(), user);
        getSession().setAttribute("lastRefreshedTimestamp", new Long(System.currentTimeMillis()));
    }
    
    public void setLoggedInUserId(Object userId) {
        super.setLoggedInUserId(getSession(), userId);
    }

    public void logoutCurrentUser() {
        super.logoutCurrentUser(getSession());
    }

    public LoginUser getLoggedInUser() {
        return super.getLoggedInUser(getSession());
    }

    public Object getLoggedInUserId() {
        return super.getLoggedInUserId(getSession());
    }
    
    public UserRoleSet getRoleSet() {
        return super.getRoleSetFromSession(getSession());
    }
    
    public LoginUser login(String loginId, String password) {
        // Check DB
        DAOCriteria criteria = new DAOCriteria();
        criteria.addWhereClause(this.loginIdParameter, loginId);
        if (this.orderByParameter != null) {
            criteria.addOrderByClause(this.orderByParameter);
        }
        List<LoginUser> matchingUsers = this.userDAO.selectByFilter(criteria);
        
        // If no matching users found, return null
        for (Iterator<LoginUser> i = matchingUsers.iterator(); i.hasNext(); ) {
            LoginUser user = i.next();
            if (user.matchPassword(password)) {
                updateLastLoginTimestamp(user);
                setLoggedInUser(user);
                return user;
            }
        }
        return null;
    }
    
    /**
     * Validates the cookie login details against the DB
     */
    public CookieLoginUser cookieAutoLogin(Object userId, String loginSecretCookie) {
        
        DAOCriteria criteria = new DAOCriteria();
        criteria.addWhereClause(this.userIdParameter, userId);
        
        List<LoginUser> users = this.userDAO.selectByFilter(criteria);
        for (Iterator<LoginUser> i = users.iterator(); i.hasNext(); ) {
            LoginUser user = i.next();
            if ((user instanceof CookieLoginUser) && 
                    ((CookieLoginUser) user).matchCookieLoginPassword(
                            loginSecretCookie)) {
                updateLastLoginTimestamp(user);
                setLoggedInUser(user);
                return (CookieLoginUser) user;
            }                
        }
        log.info("No user found with ID=" + userId + 
                " and matching cookie login password - Failing auto-login");
        return null;
    }

    public String retrieveCookieLoginPassword() {
        LoginUser user = getLoggedInUser();
        if (user instanceof CookieLoginUser) {
            CookieLoginUser cookieLoginUser = (CookieLoginUser) getLoggedInUser();
            if (cookieLoginUser.getCookieLoginPassword() == null) {
                cookieLoginUser.setCookieLoginPassword(LoginUtils.generateAutoLoginPassword());
                this.writeableUserDAO.update(new LoginUser[] {cookieLoginUser});
            }
            return cookieLoginUser.getCookieLoginPassword();
        } else {
            return null;
        }
    }

    public LoginUser refreshLoggedInUserFromDB() {
        LoginUser user = getLoggedInUser();
        if (user != null) {
            // Check refresh interval
            long now = System.currentTimeMillis();
            Long lastRefreshedTimestamp = (Long) getSession().getAttribute("lastRefreshedTimestamp");
            if ((lastRefreshedTimestamp == null) ||
                ((now - lastRefreshedTimestamp.longValue()) > this.refreshIntervalSeconds * 1000L)) {
                
                // Refresh the logged in user by user id                
                DAOCriteria criteria = new DAOCriteria();
                criteria.addWhereClause(this.userIdParameter, user.getUserId());
                List<LoginUser> users = this.userDAO.selectByFilter(criteria);
                if (users.isEmpty()) {
                    log.warn("Logging out session user not found in the DB: " + user.getUserId());
                    logoutCurrentUser();
                    user = null;
                } else {
                    log.info("Refreshing session user " + user.getUserId() + " from the DB");
                    user = users.iterator().next();
                    setLoggedInUser(user);
                }
            }
            getSession().setAttribute("lastRefreshedTimestamp", new Long(now));
        }
        return user;
    }
    
    protected void updateLastLoginTimestamp(LoginUser user) {
        if (user instanceof LastLoginTimestamped) {
            ((LastLoginTimestamped) user).copyThisLoginTimestampToLastLoginTimestamp();
            ((LastLoginTimestamped) user).setThisLoginTimestamp(new Date());
            this.writeableUserDAO.update(new LoginUser[] {user});
        }        
    }
    
    /**
     * A simple wrapper around the spring attributes that we use to make
     * it play nice with the old session based api. We only need to support
     * the getId(), getAttribute(), setAttribute() and removeAttribute() methods
     */
    protected HttpSession getSession() {
        return new HttpSession() {

            private RequestAttributes getAtts() {
                if (atts == null) {
                    return RequestContextHolder.currentRequestAttributes();
                } else {
                    return atts;
                }
            }
            
            public Object getAttribute(String name) {
                return getAtts().getAttribute(name, RequestAttributes.SCOPE_SESSION);
            }

            public void removeAttribute(String name) {
                getAtts().removeAttribute(name, RequestAttributes.SCOPE_SESSION);
            }

            public void setAttribute(String name, Object value) {
                getAtts().setAttribute(name, value, RequestAttributes.SCOPE_SESSION);
            }

            public String getId() {
                return getAtts().getSessionId();
            }

            public Enumeration getAttributeNames() {
                throw new RuntimeException("Not supported - should never be called");
            }

            public long getCreationTime() {
                throw new RuntimeException("Not supported - should never be called");
            }

            public long getLastAccessedTime() {
                throw new RuntimeException("Not supported - should never be called");
            }

            public int getMaxInactiveInterval() {
                throw new RuntimeException("Not supported - should never be called");
            }

            public ServletContext getServletContext() {
                throw new RuntimeException("Not supported - should never be called");
            }

            public void setMaxInactiveInterval(int pArg0) {
                throw new RuntimeException("Not supported - should never be called");
            }

            public void invalidate() {
                throw new RuntimeException("Not supported - should never be called");
            }

            public boolean isNew() {
                throw new RuntimeException("Not supported - should never be called");
            }

            /**
             * @deprecated
             */
            public javax.servlet.http.HttpSessionContext getSessionContext() {
                throw new RuntimeException("Not supported - should never be called");
            }

            /**
             * @deprecated
             */
            public Object getValue(String pArg0) {
                throw new RuntimeException("Not supported - should never be called");
            }

            /**
             * @deprecated
             */
            public String[] getValueNames() {
                throw new RuntimeException("Not supported - should never be called");
            }

            /**
             * @deprecated
             */
            public void putValue(String pArg0, Object pArg1) {
                throw new RuntimeException("Not supported - should never be called");
            }

            /**
             * @deprecated
             */
            public void removeValue(String pArg0) {
                throw new RuntimeException("Not supported - should never be called");
            }
            
        };
    }
}
