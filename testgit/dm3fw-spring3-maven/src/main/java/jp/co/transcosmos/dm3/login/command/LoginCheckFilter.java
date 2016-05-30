/*
 * &copy; Trans-cosmos Inc. 2006, 2007.
 * All rights reserved.
 */
package jp.co.transcosmos.dm3.login.command;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.co.transcosmos.dm3.dao.DAO;
import jp.co.transcosmos.dm3.dao.DAOCriteria;
import jp.co.transcosmos.dm3.login.CookieLoginUser;
import jp.co.transcosmos.dm3.login.LastLoginTimestamped;
import jp.co.transcosmos.dm3.login.LoginUser;
import jp.co.transcosmos.dm3.login.LoginUtils;
import jp.co.transcosmos.dm3.login.support.LoginModificationSupport;
import jp.co.transcosmos.dm3.retry.RequestRetryParams;
import jp.co.transcosmos.dm3.retry.RequestRetryWrapper;
import jp.co.transcosmos.dm3.servlet.FilterCommandChain;
import jp.co.transcosmos.dm3.utils.ServletUtils;
import jp.co.transcosmos.dm3.utils.SimpleExpressionLanguage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.ModelAndView;

/**
 * Filter for checking if there is a logged in user in the session. Note that this can be 
 * mounted inside the Spring container if desired (to make the allocation of DAOs easier)
 * 
 * @deprecated Please use the V2 authentication model instead.
 * 
 */
public class LoginCheckFilter extends LoginModificationSupport implements Filter, ServletContextAware {
    private static final Log log = LogFactory.getLog(LoginCheckFilter.class);
	
    private DAO<CookieLoginUser> userDAO;
    private boolean softLogin = false;
    private String userIdField = "userId";
    private String savedRequestSessionParameterPrefix = "savedRequest:";
    private String savedRequestRequestParameter = "redirectKey";
    private boolean addNoCacheHeaders = true;
    private String loggedInDisplaySwitchCookieName = "lidsc";
    private String cookieDomain = null;
    
    private ServletContext context;
    private String loginFormURL; // only used if mapped as a web.xml filter (not inside spring)
    
    public void init(FilterConfig config) throws ServletException {
        String loginFormURL = config.getInitParameter("loginFormURL");
        if (loginFormURL != null) {
            this.loginFormURL = loginFormURL;
        }
        if (config.getServletContext() != null) {
            setServletContext(config.getServletContext());
        }
        // maybe map the other init-params to spring calls later
    }
    public void destroy() {
        this.loginFormURL = null;
    }
    
    public void setServletContext(ServletContext context) {
        this.context = context;
    }    
    
    public ServletContext getContext() {
        return this.context;
    }
    
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
            throws IOException, ServletException {
		log.debug("Executing the LoginCheckFilter");
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        
        if (this.addNoCacheHeaders) {
            httpResponse.setHeader("Pragma", "No-cache");
            httpResponse.setHeader("Cache-Control", "No-cache");
            httpResponse.setDateHeader("Expires", 1000);
        }
        
        LoginUser user = getLoggedInUser(httpRequest);
       	if (user != null) {
            log.info("User: " + user + "(userId=" + user.getUserId() +
                    ") already logged in. URI=" + httpRequest.getRequestURI());
       		chain.doFilter(reassociateSavedRequestIfAvailable(httpRequest), response);
            return;
    	}
       	
        // If we don't have a cookie login secret, fail
		String autoLoginSecret = LoginUtils.getAutoLoginPasswordFromCookie(httpRequest);
		if (autoLoginSecret == null){
            log.info("No auto-login secret found");
	       	failedLogin(httpRequest, response, chain);
            return;
		}

        // If we don't have a cookie user id, fail
		Object userID = LoginUtils.getAutoLoginUserIdFromCookie(httpRequest);
		if( userID == null ){
            log.info("No auto-login user id found");
            failedLogin(httpRequest, response, chain);
            return;
		}
        
        CookieLoginUser cookieLoginUser = validateAutoLogin(httpRequest, userID, autoLoginSecret);
        if (cookieLoginUser == null) {
            failedLogin(httpRequest, response, chain);
        } else {
            successfulLogin(cookieLoginUser, httpRequest, httpResponse, chain);
        }
	}

    public void setSavedRequestRequestParameter(String pSavedRequestRequestParameter) {
        this.savedRequestRequestParameter = pSavedRequestRequestParameter;
    }

    public void setSavedRequestSessionParameterPrefix(String pSavedRequestSessionParameterPrefix) {
        this.savedRequestSessionParameterPrefix = pSavedRequestSessionParameterPrefix;
    }

    public void setUserDAO(DAO<CookieLoginUser> userDAO) {
        this.userDAO = userDAO;
    }

    protected DAO<CookieLoginUser> getUserDAO() {
        return this.userDAO;
    }

    public void setSoftLogin(boolean pSoftLogin) {
        this.softLogin = pSoftLogin;
    }

    public boolean isSoftLogin(HttpServletRequest req) throws IOException, ServletException {
        return this.softLogin;
    }

    public void setUserIdField(String pUserIdParameter) {
        this.userIdField = pUserIdParameter;
    }

    public void setUserIdParameter(String pUserIdParameter) {
        this.userIdField = pUserIdParameter;
    }
    
    public void setAddNoCacheHeaders(boolean pAddNoCacheHeaders) {
        this.addNoCacheHeaders = pAddNoCacheHeaders;
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
	
	/**
     * Validates the cookie login details against the DB
     */
    protected CookieLoginUser validateAutoLogin(HttpServletRequest req, 
            Object userId, String loginSecretCookie) throws IOException, ServletException {

        // Load the user by id
        DAO<CookieLoginUser> userDAO = getUserDAO();
        if (userDAO == null) {
            log.info("No DAO for loading CookieLoginUser value-objects - Failing auto-login");
            return null;
        }
        
        DAOCriteria criteria = new DAOCriteria();
        criteria.addWhereClause(this.userIdField, userId);
        
        List<CookieLoginUser> users = userDAO.selectByFilter(criteria);
        for (Iterator<CookieLoginUser> i = users.iterator(); i.hasNext(); ) {
            CookieLoginUser userBean = i.next();
            if (userBean.matchCookieLoginPassword(loginSecretCookie)) {
                return userBean;
            }                
        }
        log.info("No user found with ID=" + userId + 
                " and matching cookie login password - Failing auto-login");
        return null;
    }
    
    protected void successfulLogin(CookieLoginUser userBean, HttpServletRequest request, 
            HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        setLoggedInUser(request.getSession(true), userBean);
        addRolesetToSession(request.getSession(true), userBean);
        updateLastLoginTimestamp(userBean);
        log.info("Auto-login succeeded for user: " + userBean + " (userId=" + 
                userBean.getUserId() + ")");
        LoginUtils.setLoggedInDisplaySwitchCookie(request, response, 
                this.loggedInDisplaySwitchCookieName, true, this.cookieDomain);
        chain.doFilter(reassociateSavedRequestIfAvailable(request), response);
    }

    protected void updateLastLoginTimestamp(CookieLoginUser user) {
        if (user instanceof LastLoginTimestamped) {
            ((LastLoginTimestamped) user).copyThisLoginTimestampToLastLoginTimestamp();
            ((LastLoginTimestamped) user).setThisLoginTimestamp(new Date());
            this.userDAO.update(new CookieLoginUser[] {user});
        }        
    }
    
    protected void failedLogin(HttpServletRequest req, ServletResponse response, 
            FilterChain chain) throws IOException, ServletException {
        logoutCurrentUser(req.getSession(true));
        if (isSoftLogin(req)) {
            doSoftLogin(req, response, chain);
        } else {
            log.info("Login failed: saving request in session and forwarding to the login-form");
            String redirectURL = saveRequest(req);
            Map<String,Object> model = new HashMap<String,Object>(); 
            model.put("redirectURL", redirectURL);
            
            // Try to get the user type so we can redirect to the right page
            String userType = null;
            if ((getUserTypeCookieParameterName() != null) && 
                    (getUserTypeCookieName() != null)) {
                userType = ServletUtils.findFirstCookieValueByName(req, getUserTypeCookieName());
                if (userType != null) {
                    model.put("userType", userType);
                }
            }
            if (chain instanceof FilterCommandChain) {
                ((FilterCommandChain) chain).setResult(req, 
                        new ModelAndView(userType != null ? "failureWithUserType" : "failure", model));
            } else if (this.loginFormURL != null) {
                String replaced = SimpleExpressionLanguage.evaluateWebExpressionPatternString(
                        loginFormURL, req, response, getContext(), model, true, 
                        response.getCharacterEncoding(), "");
                ((HttpServletResponse) response).sendRedirect(
                        ServletUtils.fixPartialURL(replaced, req));
            } else {
                log.warn("Failed login, not inside spring, and no loginFormURL defined - sending 403");
                ((HttpServletResponse) response).sendError(HttpServletResponse.SC_FORBIDDEN);
            }
        }
	}
    
    protected String saveRequest(HttpServletRequest req) throws IOException, ServletException {
        //Save the request into the session
        RequestRetryParams savedRequest = new RequestRetryParams(req);
        HttpSession session = req.getSession(true);
        session.setAttribute(this.savedRequestSessionParameterPrefix + 
                savedRequest.getSavedRequestId(), savedRequest);
        log.info("Saved request in session for later replay: " + savedRequest.getSavedRequestId());
        
        // Build a URL we can redirect to after login that will pick up the body on the way back in
        StringBuffer redirectURL = req.getRequestURL();
        if (redirectURL.toString().indexOf("?") == -1) {
            redirectURL.append("?");
        }
        
        if ((req.getQueryString() != null) && !req.getQueryString().equals("")) {
            // Try to strip out duplicate redirectKeys
            int start = req.getQueryString().indexOf(this.savedRequestRequestParameter + "=");
            if (start != -1) {
                int end = req.getQueryString().indexOf("&", 
                        start + this.savedRequestRequestParameter.length() + 1);
                if (end != -1) {
                    redirectURL.append(req.getQueryString().substring(0, start))
                               .append(req.getQueryString().substring(end + 1));
                    if (!redirectURL.toString().endsWith("&")) {
                        redirectURL.append("&");
                    }
                } else if (start > 0) {
                    redirectURL.append(req.getQueryString().substring(0, start));
                    if (!redirectURL.toString().endsWith("&")) {
                        redirectURL.append("&");
                    }
                }
            } else {
                redirectURL.append(req.getQueryString());
                if (!redirectURL.toString().endsWith("&")) {
                    redirectURL.append("&");
                }
            }
        }
        redirectURL.append(this.savedRequestRequestParameter).append("=").append(
                savedRequest.getSavedRequestId());
        return redirectURL.toString();
    }
    
    protected void doSoftLogin(HttpServletRequest req, ServletResponse response, 
            FilterChain chain) throws IOException, ServletException {
        log.info("Soft-login enabled: forwarding request with no logged-in user");
        chain.doFilter(reassociateSavedRequestIfAvailable(req), response);
    }
    
    protected HttpServletRequest reassociateSavedRequestIfAvailable(HttpServletRequest req) {
        // Check query string for a savedRequestRequestParameter key
        HttpSession session = req.getSession(false);
        if ((req.getQueryString() != null) && (session != null)) {
            // Break on ampersands
            String tokens[] = req.getQueryString().split("&");
            boolean found = false;
            for (int n = 0; (n < tokens.length) && !found; n++) {
                if (tokens[n].startsWith(this.savedRequestRequestParameter + "=")) {
                    found = true;
                    // If there is one and the key identified is found in session, wrap and return
                    String key = tokens[n].substring(this.savedRequestRequestParameter.length() + 1); 
                    RequestRetryParams params = (RequestRetryParams) session.getAttribute(
                            this.savedRequestSessionParameterPrefix + key);
                    if (params != null) {
                        log.info("Using replay request found in session: " + key);
                        return new RequestRetryWrapper(req, params);
                    }
                }
            }
        }
        return req;
    }
}
