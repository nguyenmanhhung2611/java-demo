/*
 * &copy; Trans-cosmos Inc. 2006, 2007.
 * All rights reserved.
 */
package jp.co.transcosmos.dm3.login.command.v2;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.login.Authentication;
import jp.co.transcosmos.dm3.login.CookieLoginUser;
import jp.co.transcosmos.dm3.login.LoginUser;
import jp.co.transcosmos.dm3.login.LoginUtils;
import jp.co.transcosmos.dm3.retry.RequestRetryParams;
import jp.co.transcosmos.dm3.servlet.BaseFilter;
import jp.co.transcosmos.dm3.servlet.FilterCommandChain;
import jp.co.transcosmos.dm3.utils.ServletUtils;
import jp.co.transcosmos.dm3.utils.SimpleExpressionLanguage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;

/**
 * Filter for checking if there is a logged in user in the session. This filter
 * should be mounted inside the spring container, to allow injection of the 
 * Authentication object.
 * <p>
 * See the authentication tutorial for more details of authentication feature usage.
 * 
 * @author <a href="mailto:rick@knowleses.org">Rick Knowles</a>
 * @version $Id: LoginCheckFilter.java,v 1.3 2007/05/31 09:44:52 rick Exp $
 */
public class LoginCheckFilter extends BaseFilter {
    private static final Log log = LogFactory.getLog(LoginCheckFilter.class);
	
    private Authentication authentication;
    private boolean softLogin = false;
    private String savedRequestSessionParameterPrefix = "savedRequest:";
    private String savedRequestRequestParameter = "redirectKey";
    private boolean addNoCacheHeaders = true;
    
    protected String getLoginFormURL() {
        return getInitParameter("loginFormURL", null);
    }
    
    public void filterAction(HttpServletRequest request, HttpServletResponse response, FilterChain chain) 
            throws IOException, ServletException {
		log.debug("Executing the LoginCheckFilter");
        
        Authentication authentication = this.authentication;
        if (authentication == null) {
            authentication = buildAuthentication(request);
        }
        
        if (this.addNoCacheHeaders) {
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "No-cache");
            response.setDateHeader("Expires", 1000);
        }
        
        LoginUser user = authentication.getLoggedInUser();
       	if (user != null) {
            log.info("User: " + user + "(userId=" + user.getUserId() +
                    ") already logged in. URI=" + request.getRequestURI());
       		chain.doFilter(reassociateSavedRequestIfAvailable(request), response);
            return;
    	}
       	
        // If we don't have a cookie login secret, fail
		String autoLoginSecret = LoginUtils.getAutoLoginPasswordFromCookie(request);
		if (autoLoginSecret == null){
            log.info("No auto-login secret found");
	       	failedLogin(authentication, request, response, chain);
            return;
		}

        // If we don't have a cookie user id, fail
		Object userID = LoginUtils.getAutoLoginUserIdFromCookie(request);
		if( userID == null ){
            log.info("No auto-login user id found");
            failedLogin(authentication, request, response, chain);
            return;
		}
        
        CookieLoginUser cookieLoginUser = authentication.cookieAutoLogin(userID, autoLoginSecret);
        if (cookieLoginUser == null) {
            failedLogin(authentication, request, response, chain);
        } else {
            successfulLogin(cookieLoginUser, authentication, request, response, chain);
        }
	}

    /**
     * This is only called when run in standard servlet filter mode, and expected to be overridden
     */
    
    protected Authentication buildAuthentication(HttpServletRequest httpRequest) {
        return new Authentication(getSpringRequestAttributes(httpRequest));
    }
    
    public void setSavedRequestRequestParameter(String pSavedRequestRequestParameter) {
        this.savedRequestRequestParameter = pSavedRequestRequestParameter;
    }

    public void setSavedRequestSessionParameterPrefix(String pSavedRequestSessionParameterPrefix) {
        this.savedRequestSessionParameterPrefix = pSavedRequestSessionParameterPrefix;
    }

    public void setSoftLogin(boolean pSoftLogin) {
        this.softLogin = pSoftLogin;
    }

    public boolean isSoftLogin(HttpServletRequest req) throws IOException, ServletException {
        return this.softLogin;
    }
    
    public void setAddNoCacheHeaders(boolean pAddNoCacheHeaders) {
        this.addNoCacheHeaders = pAddNoCacheHeaders;
    }
    
    public void setAuthentication(Authentication pAuthentication) {
        this.authentication = pAuthentication;
    }
    
    protected void successfulLogin(CookieLoginUser userBean, Authentication authentication,
            HttpServletRequest req, HttpServletResponse response, FilterChain chain) 
            throws IOException, ServletException {
        log.info("Auto-login succeeded for user: " + userBean + " (userId=" + 
                userBean.getUserId() + ")");
        chain.doFilter(reassociateSavedRequestIfAvailable(req), response);
    }
    
    protected void failedLogin(Authentication authentication, HttpServletRequest req, 
            HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        authentication.logoutCurrentUser();
        if (isSoftLogin(req)) {
            doSoftLogin(authentication, req, response, chain);
        } else {
            log.info("Login failed: saving request in session and forwarding to the login-form");
            String redirectURL = saveRequest(req);
            Map<String,Object> model = new HashMap<String,Object>(); 
            model.put("redirectURL", redirectURL);
            
            // Try to get the user type so we can redirect to the right page
            String userType = null;
/* 2013.05.27 H.Mizuno Filter Ç∆ÇµÇƒégópÇµÇΩéûÇÃñ‚ëËÇèCê≥ start
            if ((this.authentication.getUserTypeCookieParameterName() != null) && 
                    (this.authentication.getUserTypeCookieName() != null)) {
                userType = ServletUtils.findFirstCookieValueByName(req, 
                        this.authentication.getUserTypeCookieName());
                if (userType != null) {
                    model.put("userType", userType);
                }
            }
*/
            if ((authentication.getUserTypeCookieParameterName() != null) && 
                    (authentication.getUserTypeCookieName() != null)) {
                userType = ServletUtils.findFirstCookieValueByName(req, 
                        authentication.getUserTypeCookieName());
                if (userType != null) {
                    model.put("userType", userType);
                }
            }
/* 2013.05.27 H.Mizuno Filter Ç∆ÇµÇƒégópÇµÇΩéûÇÃñ‚ëËÇèCê≥ start */

            if (chain instanceof FilterCommandChain) {
                ((FilterCommandChain) chain).setResult(req, 
                        new ModelAndView(userType != null ? "failureWithUserType" : "failure", model));
            } else {
                String loginFormURL = this.getLoginFormURL();
                if (loginFormURL != null) {
                    String replaced = SimpleExpressionLanguage.evaluateWebExpressionPatternString(
                            loginFormURL, req, response, getContext(), model, true, 
                            response.getCharacterEncoding(), "");
                    response.sendRedirect(ServletUtils.fixPartialURL(replaced, req));
                } else {
                    log.warn("Failed login, not inside spring, and no loginFormURL defined - sending 403");
                    ((HttpServletResponse) response).sendError(HttpServletResponse.SC_FORBIDDEN);
                }
            }
        }
	}
    
    protected void doSoftLogin(Authentication authentication, HttpServletRequest req, 
            HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("Soft-login enabled: forwarding request with no logged-in user");
        chain.doFilter(reassociateSavedRequestIfAvailable(req), response);
    }
    
    protected String saveRequest(HttpServletRequest req) throws IOException, ServletException {
        return RequestRetryParams.saveRequest(req, this.savedRequestSessionParameterPrefix, 
                this.savedRequestRequestParameter);
    }
    
    protected HttpServletRequest reassociateSavedRequestIfAvailable(HttpServletRequest req) {
        return RequestRetryParams.reassociateSavedRequestIfAvailable(req, 
                this.savedRequestSessionParameterPrefix,
                this.savedRequestRequestParameter);
    }
}
