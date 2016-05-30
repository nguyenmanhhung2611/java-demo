/*
 * &copy; Trans-cosmos Inc. 2006, 2007.
 * All rights reserved.
 */
package jp.co.transcosmos.dm3.login.command;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.dao.DAO;
import jp.co.transcosmos.dm3.dao.DAOCriteria;
import jp.co.transcosmos.dm3.form.FormPopulator;
import jp.co.transcosmos.dm3.login.CookieLoginUser;
import jp.co.transcosmos.dm3.login.LastLoginTimestamped;
import jp.co.transcosmos.dm3.login.LoginUser;
import jp.co.transcosmos.dm3.login.LoginUtils;
import jp.co.transcosmos.dm3.login.form.LoginForm;
import jp.co.transcosmos.dm3.login.support.LoginModificationSupport;
import jp.co.transcosmos.dm3.utils.ServletUtils;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;

public class LoginCommand extends LoginModificationSupport implements Command {
    
    private static final Log log = LogFactory.getLog(LoginCommand.class);
    
    private boolean cookieLoginEnabled;
    private DAO<LoginUser> userDAO;
    private String orderByParameter;
    private String loginIdParameter = "loginId";
    private String manualLoginTokenName = "manualLoginToken";
    private String redirectURLParameter = "redirectURL";
    private Integer autoLoginCookieExpirySeconds;
    private String loggedInDisplaySwitchCookieName = "lidsc";
    private String cookieDomain = null;
    
    public boolean isCookieLoginEnabled() {
        return cookieLoginEnabled;
    }
    public DAO<LoginUser> getUserDAO() {
        return userDAO;
    }
    public void setCookieLoginEnabled(boolean cookieLoginEnabled) {
        this.cookieLoginEnabled = cookieLoginEnabled;
    }
    public void setUserDAO(DAO<LoginUser> userDAO) {
        this.userDAO = userDAO;
    }	
    public void setOrderByParameter(String pOrderByParameter) {
        this.orderByParameter = pOrderByParameter;
    }
    public void setLoginIdParameter(String pLoginIdParameter) {
        this.loginIdParameter = pLoginIdParameter;
    }    
    public void setManualLoginTokenName(String manualLoginTokenName) {
		this.manualLoginTokenName = manualLoginTokenName;
	}    
    public void setAutoLoginCookieExpirySeconds(int pAutoLoginCookieExpirySeconds) {
        this.autoLoginCookieExpirySeconds = new Integer(pAutoLoginCookieExpirySeconds);
    }
    public void setRedirectURLParameter(String pOriginalURLParameter) {
        this.redirectURLParameter = pOriginalURLParameter;
    }
    public void setLoggedInDisplaySwitchCookieName(String pLoggedInDisplaySwitchCookieName) {
        this.loggedInDisplaySwitchCookieName = pLoggedInDisplaySwitchCookieName;
    }
    
    public String getCookieDomain() {
        return cookieDomain;
    }
    public void setCookieDomain(String cookieDomain) {
		this.cookieDomain = cookieDomain;
	}
	public ModelAndView handleRequest(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
    	
        LoginForm loginForm = getPopulatedLoginForm(request);
        List<ValidationFailure> errors = new ArrayList<ValidationFailure>();
        if (!validateForm(loginForm, errors)) {
            return loginFailure(request, response, null, loginForm, errors);
        }
        LoginUser user = queryDBForUser(loginForm);
        if (user == null) {
            return loginFailure(request, response, user, loginForm, null);
        } else if (user.matchPassword(loginForm.getPassword()))  {
			return loginSuccess(request, response, user, loginForm);
		} else {
            return loginFailure(request, response, user, loginForm, null);
        }
	}
    
    protected LoginForm getPopulatedLoginForm(HttpServletRequest request) {
        LoginForm loginForm = new LoginForm();  
        FormPopulator.populateFormBeanFromRequest(request, loginForm);
        return loginForm;
    }
    
    protected boolean validateForm(LoginForm loginForm, List<ValidationFailure> errors) {
        return loginForm.validate(errors);
    }

    protected LoginUser queryDBForUser(LoginForm loginForm) {
        // Check DB
        DAOCriteria criteria = new DAOCriteria();
        criteria.addWhereClause(this.loginIdParameter, loginForm.getLoginID());
        if (this.orderByParameter != null) {
            criteria.addOrderByClause(this.orderByParameter);
        }
        List<LoginUser> matchingUsers = this.userDAO.selectByFilter(criteria);
        
        // If no matching users found, return null
        LoginUser user = null;
        if (!matchingUsers.isEmpty()) {
            user = (LoginUser) matchingUsers.iterator().next();
        }
        return user;
    }
    
    protected ModelAndView loginSuccess(HttpServletRequest request, 
            HttpServletResponse response, LoginUser user, LoginForm loginForm) {
        setLoggedInUser(request.getSession(true), user);
        log.info("set user: " + getLoggedInUser(request));
        addRolesetToSession(request.getSession(true), user);
        
        if (this.manualLoginTokenName != null) {
        	HttpSession session = request.getSession();
        	session.setAttribute(this.manualLoginTokenName, Boolean.TRUE);
        }

        if (isCookieLoginEnabled() && 
                loginForm.isAutoLoginSelected() && 
                (user instanceof CookieLoginUser)) {
            
            // Generate a new auto-login cookie value
            CookieLoginUser cookieLoginUser = (CookieLoginUser) user;
            if (cookieLoginUser.getCookieLoginPassword() == null) {
                cookieLoginUser.setCookieLoginPassword(LoginUtils.generateAutoLoginPassword());
                this.userDAO.update(new LoginUser[] {cookieLoginUser});
            }
            LoginUtils.setAutoLoginCookies(response, request.getContextPath(), user.getUserId(), 
                    cookieLoginUser.getCookieLoginPassword(), this.autoLoginCookieExpirySeconds, this.cookieDomain);
        } else {
            LoginUtils.setAutoLoginCookies(response, request.getContextPath(), null, null, this.cookieDomain);
        }
        updateLastLoginTimestamp(user);
        LoginUtils.setLoggedInDisplaySwitchCookie(request, response, 
                this.loggedInDisplaySwitchCookieName, true, this.cookieDomain);
        return new ModelAndView("success", "redirectURL", getRedirectURL(request));        
    }

    protected void updateLastLoginTimestamp(LoginUser user) {
        if (user instanceof LastLoginTimestamped) {
            ((LastLoginTimestamped) user).copyThisLoginTimestampToLastLoginTimestamp();
            ((LastLoginTimestamped) user).setThisLoginTimestamp(new Date());
            this.userDAO.update(new LoginUser[] {user});
        }        
    }
    
    protected ModelAndView loginFailure(HttpServletRequest request, 
            HttpServletResponse response, LoginUser user, LoginForm loginForm,
            List<ValidationFailure> errors) {
        LoginUtils.setAutoLoginCookies(response, request.getContextPath(), null, null, this.cookieDomain);
        logoutCurrentUser(request.getSession(true));
        Map<String,Object> model = new HashMap<String,Object>();
        model.put("redirectURL", getRedirectURL(request));
        model.put("loginForm", loginForm);
        if (errors != null) {
            model.put("errors", errors);
        }
        
        // Try to get the user type so we can redirect to the right page
        String userType = null;
        if ((getUserTypeCookieParameterName() != null) && 
                (getUserTypeCookieName() != null)) {
            userType = ServletUtils.findFirstCookieValueByName(request, 
                    getUserTypeCookieName());
            if (userType != null) {
                model.put("userType", userType);
            }
        }
        if (userType != null) {
            return new ModelAndView("failureWithUserType", model);
        } else {
            return new ModelAndView("failure", model);
        }
    }
    
    protected String getRedirectURL(HttpServletRequest request) {
        return request.getParameter(this.redirectURLParameter);
    }
}