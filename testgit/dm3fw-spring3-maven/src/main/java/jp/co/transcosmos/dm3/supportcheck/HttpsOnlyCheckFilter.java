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
 * Checks that the current request is being accessed over HTTPS, not HTTP, and
 * redirects to the HTTPS form of the URL if HTTP access is detected.
 * <p>
 * If HTTPS is unsupported the warning page configured as init-param "noHttpsSupportPage"
 * is forwarded to.
 * 
 * @author <a href="mailto:rick@knowleses.org">Rick Knowles</a>
 * @version $Id: HttpsOnlyCheckFilter.java,v 1.2 2007/05/31 06:51:24 rick Exp $
 */
public class HttpsOnlyCheckFilter extends BaseFilter {
    private static final Log log = LogFactory.getLog(HttpsOnlyCheckFilter.class);

    private static final String RETRY_PARAM = "sec";
    
    protected void filterAction(HttpServletRequest request, HttpServletResponse response, 
            FilterChain chain) throws IOException, ServletException {
        if (request.isSecure()) {
            log.info("Request is secure - allowing");
            forwardSuccessfully(request, response, chain);
            return;
        }
        
        // If there is a retry marker on this request, it means we failed
        String qs = request.getQueryString();
        if ((qs != null) && (qs.indexOf(RETRY_PARAM) != -1)) {
            log.info("Found retry param but ssl not detected, forwarding to error page");
            forwardToErrorPage(request, response);
            return;
        }
        
        // Try to rewrite the url, but only if the conditions are right
        String scheme = request.getScheme();
        int serverPort = request.getServerPort();
        
        if ((scheme.equalsIgnoreCase("http") && (serverPort == 80)) || 
                getInitParameter("switchNonStandardPorts", "false").equalsIgnoreCase("true")) {

            // Set a cookie, save the request, and add a parameter marking this as a retry
            RequestRetryParams savedRequest = new RequestRetryParams(request);
            request.getSession(true).setAttribute("savedRequest:" + 
                    savedRequest.getSavedRequestId(), savedRequest);
            
            StringBuffer url = new StringBuffer();
            url.append("https://").append(request.getServerName());
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
            savedRequest.setScheme("https");
            savedRequest.setRequestURL(url.toString());
            
            log.info("Redirecting to HTTPS-only URL: " + url.toString());
            response.sendRedirect(url.toString());
        } else {
            log.warn("Not-enforcing the HTTPS check, because request came in on a " +
                    "non-standard port, and switchNonStandardPorts init-param <> true");
            chain.doFilter(request, response);
        }
    }
    
    protected void forwardToErrorPage(HttpServletRequest request, HttpServletResponse response) 
            throws IOException, ServletException {
        String noHttpsSupportPage = getInitParameter("noHttpsSupportPage", 
                "/noHttpsSupport.html");
        log.info("Forwarding to no-https-support page: " + noHttpsSupportPage);
        response.sendRedirect(ServletUtils.fixPartialURL(
                request.getContextPath() + noHttpsSupportPage, request));
    }
    
    protected void forwardSuccessfully(HttpServletRequest request, 
            HttpServletResponse response, FilterChain chain) 
            throws IOException, ServletException {
        request = RequestRetryParams.reassociateSavedRequestIfAvailable(request, 
                "savedRequest:", RETRY_PARAM);
        chain.doFilter(request, response);
    }
}
