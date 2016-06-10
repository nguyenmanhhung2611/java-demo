/*
 * &copy; Trans-cosmos Inc. 2006, 2007.
 * All rights reserved.
 */
package jp.co.transcosmos.dm3.login.command.v2;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.co.transcosmos.dm3.login.Authentication;
import jp.co.transcosmos.dm3.login.LoginUser;
import jp.co.transcosmos.dm3.retry.RequestRetryParams;
import jp.co.transcosmos.dm3.servlet.BaseFilter;
import jp.co.transcosmos.dm3.servlet.FilterCommandChain;
import jp.co.transcosmos.dm3.utils.ServletUtils;
import jp.co.transcosmos.dm3.utils.SimpleExpressionLanguage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;

/**
 * Filter for checking if the user logged in did so with a manual login attempt (as
 * opposed to automatic cookie-login). This filter is usually mounted inside of spring, 
 * to make injection with the authentication object simpler.
 * <p>
 * Internally, a manualLogin token is checked for in the session, and we rely on the 
 * LoginCommand to set the token (and the LogoutCommand to remove it). 
 * <p>
 * See the authentication tutorial for more details of authentication feature usage.
 * 
 * @author <a href="mailto:rick@knowleses.org">Rick Knowles</a>
 * @version $Id: ManualLoginCheckFilter.java,v 1.2 2007/05/31 09:44:52 rick Exp $
 */
public class ManualLoginCheckFilter extends BaseFilter {
    private static final Log log = LogFactory.getLog(ManualLoginCheckFilter.class);

    private Authentication authentication;
    private boolean addNoCacheHeaders = true;
    private String manualLoginTokenName = "manualLoginToken";
    private String savedRequestSessionParameterPrefix = "savedRequest:";
    private String savedRequestRequestParameter = "redirectKey";
    private String loginFormURL; // only used if mapped as a web.xml filter (not inside spring)

    public void setManualLoginTokenName(String manualLoginTokenName) {
        this.manualLoginTokenName = manualLoginTokenName;
    }
    
    public void init(FilterConfig config) throws ServletException {
        String loginFormURL = config.getInitParameter("loginFormURL");
        if (loginFormURL != null) {
            this.loginFormURL = loginFormURL;
        }
        // maybe map the other init-params to spring calls later
    }
    
    public void destroy() {
        this.loginFormURL = null;
    }
    
    public void filterAction(HttpServletRequest request, HttpServletResponse response, FilterChain chain) 
            throws IOException, ServletException {
		log.debug("Executing the ManualLoginCheckFilter");
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        
// 2013.05.10 H.Mizuno Servlet Filter での使用時の問題を修正 Start
        Authentication authentication = this.authentication;
        if (authentication == null) {
            authentication = buildAuthentication(request);
        }
// 2013.05.10 H.Mizuno Servlet Filter での使用時の問題を修正 End

        if (this.addNoCacheHeaders) {
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.setHeader("Pragma", "No-cache");
            httpResponse.setHeader("Cache-Control", "No-cache");
            httpResponse.setDateHeader("Expires", 1000);
        }

        if (this.manualLoginTokenName != null) {
            HttpSession session = httpRequest.getSession(false);
            if ((session == null) || (session.getAttribute(this.manualLoginTokenName) == null)) {
// 2013.05.10 H.Mizuno Servlet Filter での使用時の問題を修正 Start
//                noManualLoginTokenFound(httpRequest, response, chain);
                noManualLoginTokenFound(authentication, httpRequest, response, chain);
// 2013.05.10 H.Mizuno Servlet Filter での使用時の問題を修正 End
                return;
            }
        }
// 2013.05.10 H.Mizuno Servlet Filter での使用時の問題を修正 Start
//        manualLoginTokenFound(httpRequest, response, chain);
        manualLoginTokenFound(authentication, httpRequest, response, chain);
// 2013.05.10 H.Mizuno Servlet Filter での使用時の問題を修正 End
	}

// 2013.05.10 H.Mizuno Servlet Filter での使用時の問題を修正 Start
    protected Authentication buildAuthentication(HttpServletRequest httpRequest) {
        return new Authentication(getSpringRequestAttributes(httpRequest));
    }
// 2013.05.10 H.Mizuno Servlet Filter での使用時の問題を修正 End
    
    public void setAuthentication(Authentication pAuthentication) {
        this.authentication = pAuthentication;
    }

    public void setSavedRequestRequestParameter(String pSavedRequestRequestParameter) {
        this.savedRequestRequestParameter = pSavedRequestRequestParameter;
    }

    public void setSavedRequestSessionParameterPrefix(String pSavedRequestSessionParameterPrefix) {
        this.savedRequestSessionParameterPrefix = pSavedRequestSessionParameterPrefix;
    }
    
    public void setAddNoCacheHeaders(boolean pAddNoCacheHeaders) {
        this.addNoCacheHeaders = pAddNoCacheHeaders;
    }
    
// 2013.05.10 H.Mizuno Servlet Filter での使用時の問題を修正 Start
//    protected void manualLoginTokenFound(HttpServletRequest req, ServletResponse response, 
//        LoginUser userBean = this.authentication.getLoggedInUser();
//        Object userId = this.authentication.getLoggedInUserId();
    protected void manualLoginTokenFound(Authentication authentication, HttpServletRequest req,
    		ServletResponse response, 
            FilterChain chain) throws IOException, ServletException {
        LoginUser userBean = authentication.getLoggedInUser();
        Object userId = authentication.getLoggedInUserId();
// 2013.05.10 H.Mizuno Servlet Filter での使用時の問題を修正 End
        log.info("Manual login check succeeded for user: " + userBean + " (userId=" + userId + ")");
        chain.doFilter(reassociateSavedRequestIfAvailable(req), response);
    }
    
// 2013.05.10 H.Mizuno Servlet Filter での使用時の問題を修正 Start
//    protected void noManualLoginTokenFound(HttpServletRequest req, ServletResponse response, 
//            FilterChain chain) throws IOException, ServletException {
//        LoginUser userBean = this.authentication.getLoggedInUser();
//        Object userId = this.authentication.getLoggedInUserId();
    protected void noManualLoginTokenFound(Authentication authentication, HttpServletRequest req,
    		ServletResponse response, 
            FilterChain chain) throws IOException, ServletException {
        LoginUser userBean = authentication.getLoggedInUser();
        Object userId = authentication.getLoggedInUserId();
// 2013.05.10 H.Mizuno Servlet Filter での使用時の問題を修正 End
        log.info("Manual login check failed for user: " + userBean + " (userId=" + userId + 
                "): saving request in session and forwarding to the login-form");
        String redirectURL = saveRequest(req);
        if (chain instanceof FilterCommandChain) {
            ((FilterCommandChain) chain).setResult(req, new ModelAndView(
                    "failure", "redirectURL", redirectURL));
        } else if (this.loginFormURL != null) {
// 2013.05.10 H.Mizuno Servlet Filter での使用時の問題を修正 Start
//        	String replaced = this.loginFormURL.replaceAll("###redirectURL###", redirectURL);
//            ((HttpServletResponse) response).sendRedirect(
//                    ServletUtils.fixPartialURL(replaced, req));
            Map<String,Object> model = new HashMap<String,Object>(); 
            model.put("redirectURL", redirectURL);

            String replaced = SimpleExpressionLanguage.evaluateWebExpressionPatternString(
                    loginFormURL, req, response, getContext(), model, true, 
                    response.getCharacterEncoding(), "");
            ((HttpServletResponse) response).sendRedirect(ServletUtils.fixPartialURL(replaced, req));
// 2013.05.10 H.Mizuno Servlet Filter での使用時の問題を修正 End
        } else {
            log.warn("Failed login, not inside spring, and no loginFormURL defined - sending 403");
            ((HttpServletResponse) response).sendError(HttpServletResponse.SC_FORBIDDEN);
        }
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
