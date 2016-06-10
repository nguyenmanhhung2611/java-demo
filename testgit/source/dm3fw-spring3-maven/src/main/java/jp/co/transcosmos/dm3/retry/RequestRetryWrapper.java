/*
 * &copy; Trans-cosmos Inc. 2006, 2007.
 * All rights reserved.
 */
package jp.co.transcosmos.dm3.retry;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * Wrapper to allow a request to imitate a previous request that we have stored the 
 * details for in session. This is useful for retrying after a login failure to redirect
 * to the original page post-login
 * 
 * Note that this was largely drawn from the Generator Runtime library's PostLoginRetryRequestWrapper
 * and PostLoginRetryRequestParams classes.
 * 
 * @author <a href="mailto:rick@knowleses.org">Rick Knowles</a>
 * @version $Id: RequestRetryWrapper.java,v 1.3 2007/05/31 07:54:21 rick Exp $
 */
public class RequestRetryWrapper extends HttpServletRequestWrapper {
    
    // HTTP RFC requires date headers be formatted in US locale at GMT
    protected static final DateFormat headerDF = new SimpleDateFormat(
            "EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
    static {
        headerDF.setTimeZone(TimeZone.getTimeZone("GMT"));
    }

    private RequestRetryParams oldRequest;
    private ServletInputStream inputStream;
    private BufferedReader reader;
    
    public RequestRetryWrapper(HttpServletRequest request, RequestRetryParams params) {
        super(request);
        this.oldRequest = params;
    }
    
    private boolean hasBeenForwarded() {
        return (super.getAttribute("javax.servlet.forward.request_uri") != null);
    }
    
    public String getAuthType() {
        if (hasBeenForwarded()) {
            return super.getAuthType();            
        } else {
            return this.oldRequest.getAuthType();
        }
    }
    
    public long getDateHeader(String pArg0) {  
        if (hasBeenForwarded()) {
            return super.getDateHeader(pArg0);     
        } else {
            String header = getHeader(pArg0);
            try {
                return header == null ? -1L : headerDF.parse(header).getTime();
            } catch (ParseException err) {
                return -1L;
            }
        }      
    }
    
    public String getHeader(String pArg0) {    
        if (hasBeenForwarded()) {
            return super.getHeader(pArg0);     
        } else {
            Collection<?> headers = this.oldRequest.getHeaders().get(pArg0);
            return headers == null ? null : headers.iterator().next() + "";
        }
    }

// 2013.10.18 H.Mizuno Servlet API 3.0 対応（Tomcat7、Weblogic12c...） start
//    public Enumeration<?> getHeaderNames() {   
    public Enumeration<String> getHeaderNames() {   
// 2013.10.18 H.Mizuno Servlet API 3.0 対応（Tomcat7、Weblogic12c...） end
        if (hasBeenForwarded()) {
            return super.getHeaderNames();     
        } else {
            return Collections.enumeration(
                    this.oldRequest.getHeaders().keySet());
        }
    }

// 2013.10.18 H.Mizuno Servlet API 3.0 対応（Tomcat7、Weblogic12c...） start
//    public Enumeration<?> getHeaders(String pArg0) {  
    public Enumeration<String> getHeaders(String pArg0) {  
// 2013.10.18 H.Mizuno Servlet API 3.0 対応（Tomcat7、Weblogic12c...） end
        if (hasBeenForwarded()) {
            return super.getHeaders(pArg0);     
        } else {
            return Collections.enumeration(
                    this.oldRequest.getHeaders().get(pArg0));     
        }
    }
    
    public int getIntHeader(String pArg0) {     
        if (hasBeenForwarded()) {
            return super.getIntHeader(pArg0);     
        } else {
            String header = getHeader(pArg0);
            return header == null ? -1 : Integer.parseInt(header);
        }
    }
    
    public String getMethod() {        
        if (hasBeenForwarded()) {
            return super.getMethod();
        } else {
            return this.oldRequest.getMethod();
        }
    }
    
    public String getPathInfo() {           
        if (hasBeenForwarded()) {
            return super.getPathInfo();
        } else {
            return this.oldRequest.getPathInfo();
        }
    }
    
    public String getQueryString() {
        if (hasBeenForwarded()) {
            return super.getQueryString();
        } else {
            return this.oldRequest.getQueryString();
        }
    }
    
    public String getRequestURI() {  
        if (hasBeenForwarded()) {
            return super.getRequestURI();
        } else {
            return this.oldRequest.getRequestURI();
        }
    }
    
    public StringBuffer getRequestURL() {  
        if (hasBeenForwarded()) {
            return super.getRequestURL();
        } else {
            return new StringBuffer(this.oldRequest.getRequestURL());
        }
    }
    
    public String getServletPath() {  
        if (hasBeenForwarded()) {
            return super.getServletPath();
        } else {
            return this.oldRequest.getServletPath();
        }
    }
    
    public String getCharacterEncoding() {   
        if (hasBeenForwarded()) {
            return super.getCharacterEncoding();
        } else {
            return this.oldRequest.getCharacterEncoding();
        }
    }
    
    public int getContentLength() {   
        if (hasBeenForwarded()) {
            return super.getContentLength();
        } else {
            return this.oldRequest.getContentLength();
        }
    }
    
    public String getContentType() {   
        if (hasBeenForwarded()) {
            return super.getContentType();
        } else {
            return this.oldRequest.getContentType();
        }
    }
    
    public Locale getLocale() {   
        if (hasBeenForwarded()) {
            return super.getLocale();
        } else {
            return this.oldRequest.getLocale();
        }
    }
    
// 2013.10.18 H.Mizuno Servlet API 3.0 対応（Tomcat7、Weblogic12c...） start
//    public Enumeration<?> getLocales() {   
    public Enumeration<Locale> getLocales() {   
// 2013.10.18 H.Mizuno Servlet API 3.0 対応（Tomcat7、Weblogic12c...） end
        if (hasBeenForwarded()) {
            return super.getLocales();
        } else {
            return Collections.enumeration(this.oldRequest.getLocales());
        }
    }
    
    public String getParameter(String pArg0) {   
        if (hasBeenForwarded()) {
            return super.getParameter(pArg0);
        } else {
// 2013.10.18 H.Mizuno Servlet API 3.0 対応（Tomcat7、Weblogic12c...） start
//            Map<?,?> map = this.oldRequest.getParameterMap();
            Map<String,String[]> map = this.oldRequest.getParameterMap();
// 2013.10.18 H.Mizuno Servlet API 3.0 対応（Tomcat7、Weblogic12c...） end
            if (map == null) {
                throw new RuntimeException("The original request did not have " +
                        "a url-encoded body - denied getParameter()");
            }
            String values[] = (String[]) map.get(pArg0);
            return values == null ? null : values[0];
        }
    }
    
// 2013.10.18 H.Mizuno Servlet API 3.0 対応（Tomcat7、Weblogic12c...） start
//    public Map<?,?> getParameterMap() {   
    public Map<String, String[]> getParameterMap() {   
// 2013.10.18 H.Mizuno Servlet API 3.0 対応（Tomcat7、Weblogic12c...） end
        if (hasBeenForwarded()) {
            return super.getParameterMap();
        } else {
            return this.oldRequest.getParameterMap();
        }
    }
    
// 2013.10.18 H.Mizuno Servlet API 3.0 対応（Tomcat7、Weblogic12c...） start
//    @SuppressWarnings("unchecked")
//    public Enumeration<?> getParameterNames() {   
	public Enumeration<String> getParameterNames() {   
// 2013.10.18 H.Mizuno Servlet API 3.0 対応（Tomcat7、Weblogic12c...） end
        if (hasBeenForwarded()) {
            return super.getParameterNames();
        } else {
// 2013.10.18 H.Mizuno Servlet API 3.0 対応（Tomcat7、Weblogic12c...） start
//            Map<?,?> map = this.oldRequest.getParameterMap();
            Map<String,String[]> map = this.oldRequest.getParameterMap();
// 2013.10.18 H.Mizuno Servlet API 3.0 対応（Tomcat7、Weblogic12c...） end
            if (map == null) {
                throw new RuntimeException("The original request did not have " +
                        "a url-encoded body - denied getParameterNames()");
            }
            return Collections.enumeration(map.keySet());
        }
    }
    
    public String[] getParameterValues(String pArg0) {
        if (hasBeenForwarded()) {
            return super.getParameterValues(pArg0);
        } else {
            Map<?,?> map = this.oldRequest.getParameterMap();
            if (map == null) {
                throw new RuntimeException("The original request did not have " +
                        "a url-encoded body - denied getParameterValues()");
            }
            return (String[])map.get(pArg0);
        }
    }
    
    public String getProtocol() {
        if (hasBeenForwarded()) {
            return super.getProtocol();
        } else {
            return this.oldRequest.getProtocol();
        }
    }
    
    public String getScheme() {
        if (hasBeenForwarded()) {
            return super.getScheme();
        } else {
            return this.oldRequest.getScheme();
        }
    }
    
    public ServletInputStream getInputStream() throws IOException {  
        if (hasBeenForwarded()) {
            return super.getInputStream();
        } else {
            if (this.inputStream == null) {
                if (this.oldRequest.getBodyBytes() == null) {
                    throw new RuntimeException("The original request had " +
                            "a url-encoded body - denied getInputStream()");
                }
                this.inputStream = new FakeServletInputStream(this.oldRequest.getBodyBytes());
            }
            return this.inputStream;
        }
    }
    
    public BufferedReader getReader() throws IOException {   
        if (hasBeenForwarded()) {
            return super.getReader();
        } else {
            if (this.reader == null) {
                if (this.oldRequest.getBodyBytes() == null) {
                    throw new RuntimeException("The original request had " +
                            "a url-encoded body - denied getInputStream()");
                }
                if (this.inputStream == null) {
                    this.inputStream = new FakeServletInputStream(this.oldRequest.getBodyBytes());
                }
                this.reader = new BufferedReader(new InputStreamReader(this.inputStream,
                        this.oldRequest.getCharacterEncoding()));
            }
            return this.reader;
        }
    }
    
    /**
     * Fake implementation to pipe a byte array out as a servlet input stream
     */
    class FakeServletInputStream extends ServletInputStream {
        private InputStream byteStream;
        
        FakeServletInputStream(byte body[]) {
            this.byteStream = new ByteArrayInputStream(body);
        }
        
        public int read() throws IOException {
            return this.byteStream.read();
        }        
    }

    /**
     * @deprecated
     */
    public boolean isRequestedSessionIdFromUrl() {
        return super.isRequestedSessionIdFromUrl();
    }

    /**
     * @deprecated
     */
    public String getRealPath(String pArg0) {
        return super.getRealPath(pArg0);
    }
}
