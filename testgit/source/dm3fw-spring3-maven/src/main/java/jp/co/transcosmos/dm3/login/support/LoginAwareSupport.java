/*
 * &copy; Trans-cosmos Inc. 2006, 2007.
 * All rights reserved.
 */
package jp.co.transcosmos.dm3.login.support;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import jp.co.transcosmos.dm3.dao.DAOCriteria;
import jp.co.transcosmos.dm3.dao.JoinResult;
import jp.co.transcosmos.dm3.dao.ReadOnlyDAO;
import jp.co.transcosmos.dm3.login.LoginUser;
import jp.co.transcosmos.dm3.login.UserRoleSet;
import jp.co.transcosmos.dm3.utils.ReflectionUtils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Designed to allow subclasses to support configurable options on where the logged-in user
 * is stored within the session, etc.
 * <p>
 * See the authentication tutorial for more details of authentication feature usage.
 * 
 * @author <a href="mailto:rick@knowleses.org">Rick Knowles</a>
 * @version $Id: LoginAwareSupport.java,v 1.4 2007/05/31 09:44:51 rick Exp $
 */
public class LoginAwareSupport {
    private static final Log log = LogFactory.getLog(LoginAwareSupport.class);
    
    private String rolesetSessionParameter = "loggedInUserRoleset";
    private String loginUserSessionParameter = "loggedInUser";
    private String loginUserIdSessionParameter = "loggedInUserId";

    private ReadOnlyDAO<?> userRoleDAO;
    private String userRoleDAOUserIdField = "userId";
    private String userRoleDAORolenameField = "rolename";

// 2013.04.03 H.Mizuno JoinDAO 対応 Start
    private String userRoleDAOUserIdAlias = "";
    private String userRoleDAORolenameAlias = "";
// 2013.04.03 H.Mizuno JoinDAO 対応 End

    public void setRolesetSessionParameter(String rolesetSessionParameter) {
        this.rolesetSessionParameter = rolesetSessionParameter;
    }
    public void setLoginUserSessionParameter(String pLoginUserSessionParameter) {
        this.loginUserSessionParameter = pLoginUserSessionParameter;
    }
    public void setLoginUserIdSessionParameter(String pLoginUserIdSessionParameter) {
        this.loginUserIdSessionParameter = pLoginUserIdSessionParameter;
    }
    protected String getRolesetSessionParameter() {
        return this.rolesetSessionParameter;
    } 
    protected String getLoginUserSessionParameter() {
        return this.loginUserSessionParameter;
    }
    protected String getLoginUserIdSessionParameter() {
        return this.loginUserIdSessionParameter;
    }

    public void setUserRoleDAO(ReadOnlyDAO<?> pUserRoleDAO) {
        this.userRoleDAO = pUserRoleDAO;
    }
    public void setUserRoleDAOUserIdField(String userRoleDAOUserIdField) {
        this.userRoleDAOUserIdField = userRoleDAOUserIdField;
    }
    public void setUserRoleDAORolenameField(String userRoleDAORolenameField) {
        this.userRoleDAORolenameField = userRoleDAORolenameField;
    } 
    
// 2013.04.03 H.Mizuno JoinDAO 対応 Start
    public void setUserRoleDAOUserIdAlias(String userRoleDAOUserIdAlias) {
		this.userRoleDAOUserIdAlias = userRoleDAOUserIdAlias;
	}
	public void setUserRoleDAORolenameAlias(String userRoleDAORolenameAlias) {
		this.userRoleDAORolenameAlias = userRoleDAORolenameAlias;
	}
// 2013.04.03 H.Mizuno JoinDAO 対応 End

	protected LoginUser getLoggedInUser(HttpServletRequest request) {
        return getLoggedInUser(request.getSession(false));
    }
    
    protected LoginUser getLoggedInUser(HttpSession session) {
        LoginUser user = null;
        if (session != null) {
            log.info("User has a session: id=" + session.getId());
            user = (LoginUser) session.getAttribute(getLoginUserSessionParameter());
        } else {
            log.info("No session available");
        }
        log.info("Checked session variable: " + getLoginUserSessionParameter() + 
                " for logged in user: found " + user);
        return user;
    }

    protected Object getLoggedInUserId(HttpServletRequest request) {
        return getLoggedInUserId(request.getSession(false));
    }

    protected Object getLoggedInUserId(HttpSession session) {
        Object userId = null;
        if (session != null) {
        	// 2013.05.08 H.Mizuno ManualLoginCheckFilter を使用した時に例外が発生する問題に対応 start
        	try {
            // 2013.05.08 H.Mizuno ManualLoginCheckFilter を使用した時に例外が発生する問題に対応 end
        		log.info("User has a session: id=" + session.getId() + " contents=" +
					Collections.list(session.getAttributeNames()) + " maxAge=" + 
					session.getMaxInactiveInterval());
            // 2013.05.08 H.Mizuno ManualLoginCheckFilter を使用した時に例外が発生する問題に対応 start
        	} catch (RuntimeException e){
        		log.debug("unsupport method call ",e);
        	}
            // 2013.05.08 H.Mizuno ManualLoginCheckFilter を使用した時に例外が発生する問題に対応 end
            userId = session.getAttribute(getLoginUserIdSessionParameter());
        } else {
            log.info("No session available");
        }
        log.info("Checking session variable: " + getLoginUserSessionParameter() + 
                " for logged in userId: " + userId);
        return userId;
    }

    protected UserRoleSet getRoleSetFromRequest(HttpServletRequest request) {
        return getRoleSetFromSession(request.getSession(false));
    }
    
    protected UserRoleSet getRoleSetFromSession(HttpSession session) {
        UserRoleSet roleset = null;
        if (roleset == null) {
            if (session != null) {
                roleset = (UserRoleSet) session.getAttribute(this.rolesetSessionParameter);
                if (roleset == null) {
                    log.warn("WARNING: Roleset not found in session scope - returning NULL");
                }
            } else {
                log.warn("WARNING: Roleset not found in session scope - returning NULL");
            }
        }
        return roleset;
    }

    
    protected void addRolesetToSession(HttpSession session, LoginUser user) {
        if (user == null) {
            session.removeAttribute(getRolesetSessionParameter());
        } else {
            UserRoleSet roleset = makeRolesetForThisUser(user.getUserId());
            if (roleset != null) {
                session.setAttribute(getRolesetSessionParameter(), roleset);
            } else {
                session.removeAttribute(getRolesetSessionParameter());
            }
        }
    }
    
    protected UserRoleSet makeRolesetForThisUser(Object userId) {
        if (this.userRoleDAO != null) {
            Set<String> myRoles = getMyRolenamesFromDB(userId);
            log.info("Found roles: " + myRoles + " for userId=" + userId);
            return new UserRoleSet(userId, myRoles);
        } else {
            return null;
        }
    }
    
    protected Set<String> getMyRolenamesFromDB(Object userId) {
// 2013.04.03 H.Mizuno JoinDAO 対応 Start
//        DAOCriteria criteria = new DAOCriteria(this.userRoleDAOUserIdField, userId);
    	DAOCriteria criteria;
    	if (this.userRoleDAOUserIdAlias != null && this.userRoleDAOUserIdAlias.length() > 0)
    		criteria = new DAOCriteria(this.userRoleDAOUserIdAlias, this.userRoleDAOUserIdField, userId, DAOCriteria.EQUALS, false);
    	else
        	criteria = new DAOCriteria(this.userRoleDAOUserIdField, userId);
// 2013.04.03 H.Mizuno JoinDAO 対応 End
        
        List<?> results = this.userRoleDAO.selectByFilter(criteria);
        Set<String> myRoles = new HashSet<String>();
        for (Iterator<?> i = results.iterator(); i.hasNext(); ) {
            Object role = i.next();
            if (role == null) {
                continue;
            } else if (role instanceof String) {
                myRoles.add((String) role);
            } else try {

// 2013.04.03 H.Mizuno JoinDAO 対応 Start
            	if (role instanceof JoinResult) {
            		if (this.userRoleDAORolenameAlias == null || this.userRoleDAORolenameAlias.length() == 0)
            			throw new RuntimeException("Error : userRoleDAORolenameAlias is null"); 
            		role = ((JoinResult)role).getItems().get(this.userRoleDAORolenameAlias);
            	}
//2013.04.03 H.Mizuno JoinDAO 対応 End

            	myRoles.add("" + ReflectionUtils.getFieldValueByGetter(role, 
                       this.userRoleDAORolenameField));
            } catch (Throwable err) {
                throw new RuntimeException("Error trying to get the field " + 
                        this.userRoleDAORolenameField + " from the DAO result " + role, err);
            }
        }
        return myRoles; 
    }
}
