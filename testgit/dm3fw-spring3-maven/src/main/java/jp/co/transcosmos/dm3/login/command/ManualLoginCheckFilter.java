/*
 * &copy; Trans-cosmos Inc. 2006, 2007.
 * All rights reserved.
 */
package jp.co.transcosmos.dm3.login.command;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.co.transcosmos.dm3.login.LoginUser;
import jp.co.transcosmos.dm3.login.support.LoginModificationSupport;
import jp.co.transcosmos.dm3.retry.RequestRetryParams;
import jp.co.transcosmos.dm3.retry.RequestRetryWrapper;
import jp.co.transcosmos.dm3.servlet.FilterCommandChain;
import jp.co.transcosmos.dm3.utils.ServletUtils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;

/**
 * Filter for checking if there is a logged in user in the session. Note that this can be 
 * mounted inside the Spring container if desired (to make the allocation of DAOs easier)
 * 
 * @deprecated Please use the V2 authentication model instead.
 * 
 */
public class ManualLoginCheckFilter extends LoginModificationSupport implements Filter {
    private static final Log log = LogFactory.getLog(ManualLoginCheckFilter.class);
    
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
    
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
            throws IOException, ServletException {
		log.debug("Executing the ManualLoginCheckFilter");
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        
        if (this.addNoCacheHeaders) {
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.setHeader("Pragma", "No-cache");
            httpResponse.setHeader("Cache-Control", "No-cache");
            httpResponse.setDateHeader("Expires", 1000);
        }

        if (this.manualLoginTokenName != null) {
            HttpSession session = httpRequest.getSession(false);
            if ((session == null) || (session.getAttribute(this.manualLoginTokenName) == null)) {
                noManualLoginTokenFound(httpRequest, response, chain);
                return;
            }
        }
        manualLoginTokenFound(httpRequest, response, chain);
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
    
    protected void manualLoginTokenFound(HttpServletRequest req, ServletResponse response, 
            FilterChain chain) throws IOException, ServletException {
        LoginUser userBean = getLoggedInUser(req);
        Object userId = getLoggedInUserId(req);
        log.info("Manual login check succeeded for user: " + userBean + " (userId=" + userId + ")");
        chain.doFilter(reassociateSavedRequestIfAvailable(req), response);
    }
    
    protected void noManualLoginTokenFound(HttpServletRequest req, ServletResponse response, 
            FilterChain chain) throws IOException, ServletException {
        LoginUser userBean = getLoggedInUser(req);
        Object userId = getLoggedInUserId(req);
        log.info("Manual login check failed for user: " + userBean + " (userId=" + userId + 
                "): saving request in session and forwarding to the login-form");
        String redirectURL = saveRequest(req);
        if (chain instanceof FilterCommandChain) {
            ((FilterCommandChain) chain).setResult(req, new ModelAndView(
                    "failure", "redirectURL", redirectURL));
        } else if (this.loginFormURL != null) {
            String replaced = this.loginFormURL.replaceAll("###redirectURL###", redirectURL);
            ((HttpServletResponse) response).sendRedirect(
                    ServletUtils.fixPartialURL(replaced, req));
        } else {
            log.warn("Failed login, not inside spring, and no loginFormURL defined - sending 403");
            ((HttpServletResponse) response).sendError(HttpServletResponse.SC_FORBIDDEN);
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
