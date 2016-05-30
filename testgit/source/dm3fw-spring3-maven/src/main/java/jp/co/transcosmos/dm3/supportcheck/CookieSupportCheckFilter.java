/*
 * &copy; Trans-cosmos Inc. 2006, 2007.
 * All rights reserved.
 */
package jp.co.transcosmos.dm3.supportcheck;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.retry.RequestRetryParams;
import jp.co.transcosmos.dm3.servlet.BaseFilter;
import jp.co.transcosmos.dm3.utils.ServletUtils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Does a cookie support check, forwards to warning page if disabled.
 * Warning page is configured in the init-param "noCookieSupportPage", which is
 * "/noCookieSupport.html" by default.
 * 
 * @author <a href="mailto:rick@knowleses.org">Rick Knowles</a>
 * @version $Id: CookieSupportCheckFilter.java,v 1.2 2007/05/31 06:51:24 rick Exp $
 */
public class CookieSupportCheckFilter extends BaseFilter {
    private static final Log log = LogFactory.getLog(CookieSupportCheckFilter.class);

    private static final String RETRY_PARAM = "cc";

    protected void filterAction(HttpServletRequest request, 
            HttpServletResponse response, FilterChain chain) 
            throws IOException, ServletException {
        
        Cookie cookies[] = request.getCookies();
        if ((cookies != null) && (cookies.length > 0)) {
            log.info("Found cookies in the request, Cookie support assumed.");
            forwardSuccessfully(request, response, chain);
            return;
        }
        
        // If there is a retry marker on this request, it means we failed
        String qs = request.getQueryString();
        if ((qs != null) && (qs.indexOf(RETRY_PARAM) != -1)) {
            log.info("Found retry param but no cookies detected, forwarding to error page");
            forwardToErrorPage(request, response);
            return;
        }
        
        // Set a cookie, save the request, and add a parameter marking this as a retry
        log.info("Saving request, adding a cookie and retrying in order to detect cookie support...");
        String url = RequestRetryParams.saveRequest(request, "savedRequest:", RETRY_PARAM);
        response.sendRedirect(url);
        return;
    }
    
    protected void forwardToErrorPage(HttpServletRequest request, HttpServletResponse response) 
            throws IOException, ServletException {
        String noCookieSupportPage = getInitParameter("noCookieSupportPage", 
                "/noCookieSupport.html");
        log.info("Forwarding to no-cookie-support page: " + noCookieSupportPage);
        response.sendRedirect(ServletUtils.fixPartialURL(
                request.getContextPath() + noCookieSupportPage, request));
    }
    
    protected void forwardSuccessfully(HttpServletRequest request, 
            HttpServletResponse response, FilterChain chain) 
            throws IOException, ServletException {
        request = RequestRetryParams.reassociateSavedRequestIfAvailable(request, 
                "savedRequest:", RETRY_PARAM);
        chain.doFilter(request, response);
    }

}
