/*
 * &copy; Trans-cosmos Inc. 2006, 2007.
 * All rights reserved.
 */
package jp.co.transcosmos.dm3.servlet;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * Internal object used by the framework to fake additional request parameters using 
 * wildcards extracted from the URL pattern.
 * 
 * Contains a pattern match to a command, which we interpret as meaning that we
 * have to wrap the request in a wrapper (to support the extra matched parameters)
 * before forwarding to the command itself.
 * 
 * This class functions as a Filter proxy, wrapping the request only for the duration
 * of this filter.
 * 
 * @author <a href="mailto:rick_knowles@hotmail.com">Rick Knowles</a>
 * @version $Id: PatternMatchedFilterProxy.java,v 1.2 2007/05/31 07:44:43 rick Exp $
 */
public class PatternMatchedFilterProxy implements Filter {

    private String parameterNameValuePairs[][];
    private Filter proxiedFilter;
    
    public PatternMatchedFilterProxy(Filter proxiedFilter, String parameterNameValuePairs[][]) {
        this.proxiedFilter = proxiedFilter;
        this.parameterNameValuePairs = parameterNameValuePairs;
    }
    
    public String[][] getParameterNameValuePairs() {
        return parameterNameValuePairs;
    }

    public void init(FilterConfig config) throws ServletException {}
    
    public void destroy() {}

    public void doFilter(ServletRequest request, ServletResponse response, 
            FilterChain chain) throws IOException, ServletException {
        // Wrap the request and forward
        if (this.proxiedFilter != null) {
            this.proxiedFilter.doFilter(wrapRequest(request), response, chain);
        }
    }
    
    protected ServletRequest wrapRequest(ServletRequest request) {
        if ((this.parameterNameValuePairs == null) || (this.parameterNameValuePairs.length == 0)) {
            return request;
        } else {
            return new PatternMatchedRequestWrapper((HttpServletRequest) request, 
                    this.parameterNameValuePairs);
        }
    }
}
