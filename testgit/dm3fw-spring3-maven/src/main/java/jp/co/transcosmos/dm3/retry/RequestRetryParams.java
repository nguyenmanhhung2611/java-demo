/*
 * &copy; Trans-cosmos Inc. 2006, 2007.
 * All rights reserved.
 */
package jp.co.transcosmos.dm3.retry;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import jp.co.transcosmos.dm3.utils.EncodingUtils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Reads all the request attributes into a valueobject, so we can store it into the session and
 * imitate it later after we succeed in authenticating.
 * 
 * Note that this was largely drawn from the Generator Runtime library's PostLoginRetryRequestWrapper
 * and PostLoginRetryRequestParams classes.
 * 
 * @author <a href="mailto:rick@knowleses.org">Rick Knowles</a>
 * @version $Id: RequestRetryParams.java,v 1.5 2007/05/31 07:54:21 rick Exp $
 */
public class RequestRetryParams implements Serializable {

	private static final long serialVersionUID = -2297808614277865388L;

	private static final Log log = LogFactory.getLog(RequestRetryParams.class);

    private static final Random rnd = new Random();
    
    private String savedRequestId;
    private String authType;
    private String characterEncoding;
    private int contentLength;
    private String contentType;
    private Locale locale;
// 2013.10.18 H.Mizuno Servlet API 3.0 対応（Tomcat7、Weblogic12c...） start
//    private Collection<?> locales;
    private Collection<Locale> locales;
// 2013.10.18 H.Mizuno Servlet API 3.0 対応（Tomcat7、Weblogic12c...） end
    private String method;
    private String pathInfo;
    private String protocol;
    private String queryString;
    private String scheme;
    private String servletPath;
    private String requestURI;
    private String requestURL;
// 2013.10.18 H.Mizuno Servlet API 3.0 対応（Tomcat7、Weblogic12c...） start
//    private Map<String,List<?>> headers;
//    private Map<?,?> parameterMap; //if parsed
    private Map<String,List<String>> headers;
    private Map<String, String[]> parameterMap; //if parsed
// 2013.10.18 H.Mizuno Servlet API 3.0 対応（Tomcat7、Weblogic12c...） end
    private byte bodyBytes[];

    public RequestRetryParams(HttpServletRequest request) throws IOException {
        synchronized (rnd) {
            this.savedRequestId = EncodingUtils.md5Encode(
                    request.getLocalAddr() + " " + 
                    request.getLocalPort() + " " +
                    System.currentTimeMillis() + " " + 
                    request.getServerPort() + " " + rnd.nextLong());
        }
        
        this.authType = request.getAuthType();
        this.characterEncoding = request.getCharacterEncoding();
        this.contentLength = request.getContentLength();
        this.contentType = request.getContentType();
        this.locale = request.getLocale();
// 2013.10.18 H.Mizuno Servlet API 3.0 対応（Tomcat7、Weblogic12c...） start
//        this.locales = Collections.list((Enumeration<?>) request.getLocales());
        this.locales = Collections.list((Enumeration<Locale>) request.getLocales());
// 2013.10.18 H.Mizuno Servlet API 3.0 対応（Tomcat7、Weblogic12c...） end
        this.method = request.getMethod();
        this.pathInfo = request.getPathInfo();
        this.protocol = request.getProtocol();
        this.queryString = request.getQueryString();
        this.scheme = request.getScheme();
        this.servletPath = request.getServletPath();
        this.requestURI = request.getRequestURI();
        this.requestURL = request.getRequestURL().toString();
        
//        this.headers = new Hashtable<String,List<?>>();
        this.headers = new Hashtable<String,List<String>>();
// 2013.10.18 H.Mizuno Servlet API 3.0 対応（Tomcat7、Weblogic12c...） start
//        for (Enumeration<?> e = request.getHeaderNames(); e.hasMoreElements(); ) {
//            String name = (String) e.nextElement();
//        this.headers.put(name.toLowerCase(), Collections.list(
//        		    (Enumeration<?>) request.getHeaders(name)));
        for (Enumeration<String> e = request.getHeaderNames(); e.hasMoreElements(); ) {
            String name = (String) e.nextElement();
            this.headers.put(name.toLowerCase(), Collections.list(
            		(Enumeration<String>) request.getHeaders(name)));
// 2013.10.18 H.Mizuno Servlet API 3.0 対応（Tomcat7、Weblogic12c...） end
        }
        
        if (this.method.equalsIgnoreCase("GET") || this.method.equalsIgnoreCase("HEAD") ||
                (this.method.equalsIgnoreCase("POST") && (this.contentType != null) &&
                        this.contentType.equalsIgnoreCase("application/x-www-form-urlencoded"))) {

        	// 2013.04.11 H.Mizuno ParameterMap がセッションに保存できない問題を修正 start
        	// 2013.10.18 H.Mizuno Servlet API 3.0 対応の為、キャストを追加
        	//this.parameterMap = request.getParameterMap();
        	this.parameterMap = new HashMap<String,String[]>(request.getParameterMap());
        	// 2013.04.11 H.Mizuno ParameterMap がセッションに保存できない問題を修正  end
        } else if (this.contentLength >= 0) {
            InputStream in = request.getInputStream();
            this.bodyBytes = new byte[this.contentLength];
            int position = 0;
            while (position < this.contentLength) {
                position += in.read(this.bodyBytes, position, this.contentLength - position);
            }
            in.close();
        } else {
            log.warn("WARNING: dropping body for request with contentLength=-1");
            this.bodyBytes = new byte[0];
        }
    }

    public void setScheme(String pScheme) {
        this.scheme = pScheme;
    }

    public void setRequestURL(String pRequestURL) {
        this.requestURL = pRequestURL;
    }

    public String getSavedRequestId() {
        return this.savedRequestId;
    }

    public String getAuthType() {
        return this.authType;
    }

    public String getCharacterEncoding() {
        return this.characterEncoding;
    }

    public int getContentLength() {
        return this.contentLength;
    }

    public String getContentType() {
        return this.contentType;
    }

    public Locale getLocale() {
        return this.locale;
    }

// 2013.10.18 H.Mizuno Servlet API 3.0 対応（Tomcat7、Weblogic12c...） start
//    public Collection<?> getLocales() {
    public Collection<Locale> getLocales() {
// 2013.10.18 H.Mizuno Servlet API 3.0 対応（Tomcat7、Weblogic12c...） end
        return this.locales;
    }

    public String getMethod() {
        return this.method;
    }

    public String getPathInfo() {
        return this.pathInfo;
    }

    public String getProtocol() {
        return this.protocol;
    }

    public String getQueryString() {
        return this.queryString;
    }

    public String getScheme() {
        return this.scheme;
    }

    public String getServletPath() {
        return this.servletPath;
    }

// 2013.10.18 H.Mizuno Servlet API 3.0 対応（Tomcat7、Weblogic12c...） start
//    public Map<String, List<?>> getHeaders() {
    public Map<String, List<String>> getHeaders() {
// 2013.10.18 H.Mizuno Servlet API 3.0 対応（Tomcat7、Weblogic12c...） end
        return this.headers;
    }

// 2013.10.18 H.Mizuno Servlet API 3.0 対応（Tomcat7、Weblogic12c...） start
//    public Map<?, ?> getParameterMap() {
    public Map<String, String[]> getParameterMap() {
// 2013.10.18 H.Mizuno Servlet API 3.0 対応（Tomcat7、Weblogic12c...） end
        return this.parameterMap;
    }

    public byte[] getBodyBytes() {
        return this.bodyBytes;
    }

    public String getRequestURI() {
        return this.requestURI;
    }

    public String getRequestURL() {
        return this.requestURL;
    }
    
    public static String saveRequest(HttpServletRequest req, String prefix, String parameter) throws IOException, ServletException {
        //Save the request into the session
        RequestRetryParams savedRequest = new RequestRetryParams(req);
        HttpSession session = req.getSession(true);
        session.setAttribute(prefix + savedRequest.getSavedRequestId(), savedRequest);
        log.info("Saved request in session for later replay: " + savedRequest.getSavedRequestId());
        
        // Build a URL we can redirect to after login that will pick up the body on the way back in
        StringBuffer redirectURL = req.getRequestURL();
        if (redirectURL.toString().indexOf("?") == -1) {
            redirectURL.append("?");
        }
        
        if ((req.getQueryString() != null) && !req.getQueryString().equals("")) {
            // Try to strip out duplicate redirectKeys
            int start = req.getQueryString().indexOf(parameter + "=");
            if (start != -1) {
                int end = req.getQueryString().indexOf("&", 
                        start + parameter.length() + 1);
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
        redirectURL.append(parameter).append("=").append(savedRequest.getSavedRequestId());
        return redirectURL.toString();
    }
    
    public static HttpServletRequest reassociateSavedRequestIfAvailable(HttpServletRequest req,
            String prefix, String parameter) {
        // Check query string for a savedRequestRequestParameter key
        HttpSession session = req.getSession(false);
        if ((req.getQueryString() != null) && (session != null)) {
            // Break on ampersands
            String tokens[] = req.getQueryString().split("&");
            boolean found = false;
            for (int n = 0; (n < tokens.length) && !found; n++) {
                if (tokens[n].startsWith(parameter + "=")) {
                    found = true;
                    // If there is one and the key identified is found in session, wrap and return
                    String key = tokens[n].substring(parameter.length() + 1); 
                    RequestRetryParams params = (RequestRetryParams) session.getAttribute(prefix + key);
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
