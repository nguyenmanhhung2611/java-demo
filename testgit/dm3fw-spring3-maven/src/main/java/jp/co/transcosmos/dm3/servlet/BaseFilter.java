/*
 * &copy; Trans-cosmos Inc. 2006, 2007.
 * All rights reserved.
 */
package jp.co.transcosmos.dm3.servlet;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * Base filter class - adds some simple features like exception lists and 
 * server side forward
 * 
 * @author <a href="mailto:rick@knowleses.org">Rick Knowles</a>
 * @version $Id: BaseFilter.java,v 1.3 2007/05/31 07:44:43 rick Exp $
 */
public abstract class BaseFilter implements Filter, ServletContextAware, BeanNameAware {
    private static final Log log = LogFactory.getLog(BaseFilter.class);

    private ServletContext servletContext;
    private String name;
    private FilterConfig config;
    private String matchPatterns[];
    private String exceptionPatterns[];
    private boolean useRegexPatterns;

    protected abstract void filterAction(HttpServletRequest request, 
            HttpServletResponse response, FilterChain chain) 
            throws IOException, ServletException;
    
    public void doFilter(ServletRequest req, ServletResponse rsp, 
            FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) rsp;
        
        // Check exceptions
        if ((this.exceptionPatterns != null) && (this.exceptionPatterns.length > 0) && 
                matches(request.getRequestURI(), this.exceptionPatterns)) {
            log.info(getFilterName() + ": Skipping filter because matched exception pattern");
            chain.doFilter(req, rsp);
            return;
        }
        
        // If mappings defined and doesn't match a defined mapping, don't execute filter
        if ((this.matchPatterns != null) && (this.matchPatterns.length > 0) && 
                !matches(request.getRequestURI(), this.matchPatterns)) {
            log.info(getFilterName() + ": Skipping filter because didn't match pattern");
            chain.doFilter(req, rsp);        
            return;
        }
        
        // Otherwise execute
        log.info("Executing filter " + getFilterName() + " on URL: " + request.getRequestURL());
        filterAction(request, response, chain);
    }

    public void init(FilterConfig config) throws ServletException {
        this.config = config;
        this.servletContext = config.getServletContext();
        this.name = config.getFilterName();
        this.useRegexPatterns = getInitParameter("useRegexPatterns", 
                "false").equalsIgnoreCase("true");
        String match = getInitParameter("matchPatterns", "");
        if (!match.equals("")) {
            this.matchPatterns = match.split(",");
            for (int n = 0; n < this.matchPatterns.length; n++) {
                this.matchPatterns[n] = this.matchPatterns[n].trim();
                log.info(config.getFilterName() + ": Found match pattern \"" + this.matchPatterns[n] + "\"");
            }
        }
        String exception = getInitParameter("exceptionPatterns", "");
        if (!exception.equals("")) {
            this.exceptionPatterns = exception.split(",");
            for (int n = 0; n < this.exceptionPatterns.length; n++) {
                this.exceptionPatterns[n] = this.exceptionPatterns[n].trim();
                log.info(config.getFilterName() + ": Found exception pattern \"" + this.exceptionPatterns[n] + "\"");
            }
        }
    }

    public void destroy() {
        this.config = null;
        this.matchPatterns = null;
        this.exceptionPatterns = null;
        this.useRegexPatterns = false;
    }
    
    protected ServletContext getContext() {
        return this.servletContext;
    }
    
    protected FilterConfig getFilterConfig() {
        return this.config;
    }
    
    protected String getFilterName() {
        return this.name;
    }
    
    public void setBeanName(String beanName) {
        this.name = beanName;
    }

    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    public String getInitParameter(String key, String defaultValue) {
        if (this.config == null) {
            return defaultValue;
        }
        String value = this.config.getInitParameter(key);
        if (value == null) {
            value = defaultValue;
        }
        return value;
    }
    
    public boolean matches(String requestURI, String patterns[]) {
        for (int n = 0; n < patterns.length; n++) {
            if (this.useRegexPatterns) {
                if (requestURI.matches(patterns[n])) {
                    return true;
                }
            } else if (patterns[n].equals("*")) {
                return true;
            } else if (patterns[n].startsWith("*") && patterns[n].endsWith("*")) {
                String innerPattern = patterns[n].substring(1, patterns[n].length() - 1);
                if (requestURI.indexOf(innerPattern) != -1) {
                    return true;
                }
            } else if (patterns[n].startsWith("*")) {
                if (requestURI.endsWith(patterns[n].substring(1))) {
                    return true;
                }
            } else if (patterns[n].endsWith("*")) {
/* 2013/11/21 H.Mizuno 前方一致のパターンマッチングのバグを修正
            	if (requestURI.startsWith(patterns[n].substring(patterns[n].length() - 1))) {
*/
                if (requestURI.startsWith(patterns[n].substring(0, patterns[n].length() - 1))) {
                    return true;
                }
            } else if (patterns[n].equals(requestURI)) {
                return true;
            }
        }
        return false;
    }
    
    protected RequestAttributes getSpringRequestAttributes(HttpServletRequest httpRequest) {
        RequestAttributes atts = null;
        try {
            atts = RequestContextHolder.currentRequestAttributes();
        } catch (Throwable err) {
            log.warn("Error retrieving request attributes through spring lookup");
            log.debug("Actual error", err);
        }
        if (atts == null) {
            atts = new ServletRequestAttributes(httpRequest);
        }
        return atts;
    }
}
