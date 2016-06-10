/*
 * &copy; Trans-cosmos Inc. 2006, 2007.
 * All rights reserved.
 */
package jp.co.transcosmos.dm3.supportcheck;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.co.transcosmos.dm3.retry.RequestRetryParams;
import jp.co.transcosmos.dm3.servlet.BaseFilter;
import jp.co.transcosmos.dm3.utils.ServletUtils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Does a javascript support check, forwards to warning page if disabled.
 * Warning page is configured in the init-param "noJavascriptSupportPage", which is
 * "/noJavascriptSupportPage.html" by default.
 * 
 * @author <a href="mailto:rick@knowleses.org">Rick Knowles</a>
 * @version $Id: JavascriptSupportCheckFilter.java,v 1.2 2007/05/31 06:51:24 rick Exp $
 */
public class JavascriptSupportCheckFilter extends BaseFilter {
    private static final Log log = LogFactory.getLog(JavascriptSupportCheckFilter.class);

    private static final String RETRY_PARAM = "jsc";
    private static final String SESSION_PARAM = "hasJavascriptSupport";

    protected void filterAction(HttpServletRequest request, 
            HttpServletResponse response, FilterChain chain) 
            throws IOException, ServletException {
        
        // Allow for bypass of failure page or non-js-required pages
        if (!applyJavascriptSupportCheck(request)) {
            log.info("Skipping javascript check support for URI: " + request.getRequestURI());
            forwardSuccessfully(request, response, chain);
            return;
        }
        
        HttpSession session = request.getSession(false);
        if (session != null) {
            String support = (String) session.getAttribute(SESSION_PARAM); 
            if ((support != null) && support.equals("true")) {
                log.info("Found previous successful detection of javascript support. Allowing ...");
                forwardSuccessfully(request, response, chain);
                return;
            }
        }
        
        // If there is a retry marker on this request, it means we succeeded
        String qs = request.getQueryString();
        if ((qs != null) && (qs.indexOf(RETRY_PARAM) != -1)) {
            log.info("Found javascript support succeeded token. Allowing ...");
            session.setAttribute(SESSION_PARAM, "true");
            forwardSuccessfully(request, response, chain);
            return;
        }
        
        // Send the redirect page
        log.info("Saving request, sending javascript support detection html...");
        String url = RequestRetryParams.saveRequest(request, "savedRequest:", RETRY_PARAM);
        response.setContentType("text/html");
        Writer out = response.getWriter(); 
// 2013.05.23 H.Mizuno リダイレクトURLにコンテキストが含まれない問題を修正 start
//        out.append(getSupportCheckHTML(url, getNoSupportPage(), response.getCharacterEncoding()));
        out.append(getSupportCheckHTML(url, request.getContextPath() + getNoSupportPage(), response.getCharacterEncoding()));
// 2013.05.23 H.Mizuno リダイレクトURLにコンテキストが含まれない問題を修正 end
        out.flush();
    }
    
    protected boolean applyJavascriptSupportCheck(HttpServletRequest request) {
        // apply to all pages except the failure page by default
        return !request.getRequestURI().startsWith(getNoSupportPage()); 
    }
    
    protected void forwardToErrorPage(HttpServletRequest request, HttpServletResponse response) 
            throws IOException, ServletException {
        String noCookieSupportPage = getNoSupportPage();
        log.info("Forwarding to no-cookie-support page: " + noCookieSupportPage);
        response.sendRedirect(ServletUtils.fixPartialURL(
                request.getContextPath() + noCookieSupportPage, request));
    }
    
    protected String getNoSupportPage() {
        return getInitParameter("noJavascriptSupportPage", "/noJavascriptSupport.html");
    }
    
    protected void forwardSuccessfully(HttpServletRequest request, 
            HttpServletResponse response, FilterChain chain) 
            throws IOException, ServletException {
        request = RequestRetryParams.reassociateSavedRequestIfAvailable(request, 
                "savedRequest:", RETRY_PARAM);
        chain.doFilter(request, response);
    }

    protected String getSupportCheckHTML(String successURL, String failureURL, String encoding) {
        StringBuilder out = new StringBuilder();
        out.append("<html>");
        out.append(  "<head>");
        out.append(    "<meta http-equiv=\"Content-Type\" content=\"text/html;charset=").append(encoding).append("\"/>");
        out.append(    "<meta http-equiv=\"Refresh\" content=\"3;url=").append(failureURL).append("\" />");
        out.append(    "<title>javascript対応チェック</title>");
        out.append(  "</head>");
        out.append(  "<body>");
        out.append(    "javascript対応チェック中: しばらくお待ち下さい ... ");
        out.append(    "<script language=\"javascript\">");
        out.append(      "metatags = document.getElementsByTagName(\"meta\"); ");
        out.append(      "for (n = 0; n < metatags.length; n++) {");
        out.append(        "if (metatags[n].getAttribute(\"http-equiv\") == 'Refresh') {");
        out.append(          "metatags[n].setAttribute(\"content\", \"1000\");"); // disable refresh with a huge timeout
        out.append(        "}");
        out.append(      "}");
        out.append(      "location.href = '" + successURL + "';");
        out.append(    "</script>");
        out.append(    "<noscript>javascript disabled. Redirecting to <a href=\"").append(failureURL).append("\">help page</a> in 3 seconds</noscript>");
        out.append(  "</body>");
        out.append("</html>");
        return out.toString();
    }
}
