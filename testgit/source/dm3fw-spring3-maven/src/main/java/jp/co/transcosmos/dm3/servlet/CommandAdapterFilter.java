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
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;

/**
 * Internal object that maps Command classes into the servlet filter pattern so they can 
 * be processed by a common API. Not relevant for the application developer.
 * 
 * @author <a href="mailto:rick@knowleses.org">Rick Knowles</a>
 * @version $Id: CommandAdapterFilter.java,v 1.3 2007/05/31 07:44:43 rick Exp $
 */
public class CommandAdapterFilter extends ModelAndViewRewriteSupport implements Filter {
    private static final Log log = LogFactory.getLog(CommandAdapterFilter.class);
    
    private Command command;
    private URLCommandViewMapping mapping;
    private String returnValueParameter;
    
    public CommandAdapterFilter(Command command, URLCommandViewMapping mapping, 
            String returnValueParameter) {
        this.command = command;
        this.mapping = mapping;
        this.returnValueParameter = returnValueParameter;
    }

    public void init(FilterConfig config) throws ServletException {
        setServletContext(config.getServletContext());
    }
    
    public void destroy() {
        setServletContext(null);
    }

    public void doFilter(ServletRequest request, ServletResponse response, 
            FilterChain chain) throws IOException, ServletException {
        try {
            ModelAndView mav = null;
            if (this.command != null) {
                mav = this.command.handleRequest((HttpServletRequest) request, 
                        (HttpServletResponse) response);
            }
            // We resolve here, because we need to know if there is a resolved view before 
            // calling chain.doFilter or stopping (in the case of command adapter filter)
            mav = resolveView((HttpServletRequest) request, (HttpServletResponse) response, 
                    mav, mapping);
            replaceViewIfPrefixed(mav);
            
            if (mav == null) {
                chain.doFilter(request, response);
            } else {
                log.info("Filter class returned redirect: " + this.command.getClass().getName());
                request.setAttribute(this.returnValueParameter, mav);
            }
        } catch (RuntimeException err) {
            throw err;
        } catch (Exception err) {
            throw new RuntimeException("Error executing filter command", err);
        }
    }
    
    public Command getNestedCommand() {
        return this.command;
    }
}
