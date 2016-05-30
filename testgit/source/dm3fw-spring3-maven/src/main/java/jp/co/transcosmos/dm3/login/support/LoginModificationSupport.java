/*
 * &copy; Trans-cosmos Inc. 2006, 2007.
 * All rights reserved.
 */
package jp.co.transcosmos.dm3.login.support;

import javax.servlet.http.HttpSession;

import jp.co.transcosmos.dm3.login.LoginUser;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * A utility class that can be extended by commands (or any spring 
 * managed object) managing rolesets, loading them from the DB etc. Normal
 * command classes don't need to extend this class (only classes actually
 * performing authentication and creating rolesets).
 * <p>
 * See the authentication tutorial for more details of authentication feature usage.
 * 
 * @author <a href="mailto:rick@knowleses.org">Rick Knowles</a>
 * @version $Id: LoginModificationSupport.java,v 1.3 2007/05/31 09:44:51 rick Exp $
 */
public class LoginModificationSupport extends LoginAwareSupport {
    private static final Log log = LogFactory.getLog(LoginModificationSupport.class);
    
    private String userTypeCookieParameterName = null; // "userType" is a common value, off by default
    private String userTypeCookieName = "lct"; // last contact type
    
    public void setUserTypeCookieParameterName(String pUserTypeCookieParameterName) {
        this.userTypeCookieParameterName = pUserTypeCookieParameterName;
    }
    
    public void setUserTypeCookieName(String pCookieName) {
        this.userTypeCookieName = pCookieName;
    }

    public String getUserTypeCookieName() {
        return this.userTypeCookieName;
    }

    public String getUserTypeCookieParameterName() {
        return this.userTypeCookieParameterName;
    }
    
    protected void setLoggedInUser(HttpSession session, LoginUser pUser){
        if (pUser == null) {
            if (session != null) {
                session.removeAttribute(getLoginUserSessionParameter());
                log.info("removed user from session");
                setLoggedInUserId(session, null);
            }
        } else { 
            session.setAttribute(getLoginUserSessionParameter(), pUser);
            log.info("add user to session; user=" + pUser);
            setLoggedInUserId(session, pUser.getUserId());
        }
    }
    
    protected void setLoggedInUserId(HttpSession session, 
            Object userId){
        if (userId == null) {
            if (session != null) {
                session.removeAttribute(getLoginUserIdSessionParameter());
                log.info("removed userid from session");
            }
        } else {
            session.setAttribute(getLoginUserIdSessionParameter(), userId);
            log.info("add userid to session; userid=" + userId);            
        }
    }
    
    protected void logoutCurrentUser(HttpSession session) {
        setLoggedInUser(session, null);
        addRolesetToSession(session, null);
    }
}
