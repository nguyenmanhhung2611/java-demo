/*
 * &copy; Trans-cosmos Inc. 2006, 2007.
 * All rights reserved.
 */
package jp.co.transcosmos.dm3.supportcheck;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.retry.RequestRetryParams;
import jp.co.transcosmos.dm3.servlet.BaseFilter;
import jp.co.transcosmos.dm3.utils.ServletUtils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Checks that the current request is being accessed over HTTP, not HTTPS, and
 * redirects to the HTTP form of the URL if HTTPS access is detected.
 * 
 * @author <a href="mailto:rick@knowleses.org">Rick Knowles</a>
 * @version $Id: HttpOnlyCheckFilter.java,v 1.2 2007/05/31 06:51:24 rick Exp $
 */
public class HttpOnlyCheckFilter extends BaseFilter {
    private static final Log log = LogFactory.getLog(HttpOnlyCheckFilter.class);

    private static final String RETRY_PARAM = "ho";
    
    protected void filterAction(HttpServletRequest request, HttpServletResponse response, 
            FilterChain chain) throws IOException, ServletException {
        if (!request.isSecure()) {
            log.info("Request is not-secure - allowing");
            forwardSuccessfully(request, response, chain);
            return;
        }
        
        // If there is a retry marker on this request, it means we failed
        String qs = request.getQueryString();
        if ((qs != null) && (qs.indexOf(RETRY_PARAM) != -1)) {
            log.info("Found retry param but ssl detected, forwarding to error page");
            forwardToErrorPage(request, response);
            return;
        }
        
        // Try to rewrite the url, but only if the conditions are right
        String scheme = request.getScheme();
        int serverPort = request.getServerPort();
        
        if ((scheme.equalsIgnoreCase("https") && (serverPort == 443)) || 
                getInitParameter("switchNonStandardPorts", "false").equalsIgnoreCase("true")) {

            // Set a cookie, save the request, and add a parameter marking this as a retry
            RequestRetryParams savedRequest = new RequestRetryParams(request);
            request.getSession(true).setAttribute("savedRequest:" + 
                    savedRequest.getSavedRequestId(), savedRequest);
            
            StringBuffer url = new StringBuffer();
            url.append("http://").append(request.getServerName());
            if (!((serverPort == 80) && scheme.equalsIgnoreCase("http"))
                    && !((serverPort == 443) && scheme.equalsIgnoreCase("https"))) {
                url.append(':').append(serverPort);
            }
            url.append(request.getRequestURI()).append('?');
            if ((qs != null) && !qs.equals("")) {
                url.append(qs).append('&');
            }
            url.append(ServletUtils.encodeURLToken(RETRY_PARAM,
                    request.getCharacterEncoding()))
               .append('=')
               .append(ServletUtils.encodeURLToken(
                            savedRequest.getSavedRequestId(),
                            request.getCharacterEncoding()));
            
            // force the cached request to use the new values
            savedRequest.setScheme("http");
            savedRequest.setRequestURL(url.toString());
            
            log.info("Redirecting to HTTP-only URL: " + url.toString());
            response.sendRedirect(url.toString());
        } else {
            log.warn("Not-enforcing the HTTP check, because request came in on a " +
                    "non-standard port, and switchNonStandardPorts init-param <> true");
            chain.doFilter(request, response);
        }
    }
    
    protected void forwardToErrorPage(HttpServletRequest request, HttpServletResponse response) 
            throws IOException, ServletException {
        String noHttpSupportPage = getInitParameter("noHttpSupportPage", 
                "/noHttpSupport.html");
        log.info("Forwarding to no-http-support page: " + noHttpSupportPage);
        response.sendRedirect(ServletUtils.fixPartialURL(
                request.getContextPath() + noHttpSupportPage, request));
    }
    
    protected void forwardSuccessfully(HttpServletRequest request, 
            HttpServletResponse response, FilterChain chain) 
            throws IOException, ServletException {
        request = RequestRetryParams.reassociateSavedRequestIfAvailable(request, 
                "savedRequest:", RETRY_PARAM);
        chain.doFilter(request, response);
    }
}
