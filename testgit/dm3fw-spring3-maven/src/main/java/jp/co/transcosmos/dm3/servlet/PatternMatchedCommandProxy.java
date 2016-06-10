/*
 * &copy; Trans-cosmos Inc. 2006, 2007.
 * All rights reserved.
 */
package jp.co.transcosmos.dm3.servlet;

import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;

import org.springframework.web.servlet.ModelAndView;

/**
 * Internal object used by the framework to fake additional request parameters using 
 * wildcards extracted from the URL pattern.
 * 
 * Contains a pattern match to a command, which we interpret as meaning that we
 * have to wrap the request in a wrapper (to support the extra matched parameters)
 * before forwarding to the command itself.
 * 
 * This class functions as a Command proxy, wrapping the request only for the duration
 * of this command.
 * 
 * @author <a href="mailto:rick_knowles@hotmail.com">Rick Knowles</a>
 * @version $Id: PatternMatchedCommandProxy.java,v 1.3 2007/05/31 07:44:43 rick Exp $
 */
public class PatternMatchedCommandProxy implements Command {

    private String parameterNameValuePairs[][];
    private Command proxiedCommand;
    
    public PatternMatchedCommandProxy(Command proxiedCommand, String parameterNameValuePairs[][]) {
        this.proxiedCommand = proxiedCommand;
        this.parameterNameValuePairs = parameterNameValuePairs;
    }
    
    public String[][] getParameterNameValuePairs() {
        return parameterNameValuePairs;
    }

    public void init(FilterConfig config) throws ServletException {}
    
    public void destroy() {}

    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) 
            throws Exception {
        // Wrap the request and forward
        if (this.proxiedCommand != null) {
            return this.proxiedCommand.handleRequest(wrapRequest(request), response);
        } else {
            return null;
        }
    }
    
    protected HttpServletRequest wrapRequest(HttpServletRequest request) {
        if ((this.parameterNameValuePairs == null) || (this.parameterNameValuePairs.length == 0)) {
            return request;
        } else {
            return new PatternMatchedRequestWrapper(request, 
                    this.parameterNameValuePairs);
        }
    }
    
    public Command getNestedCommand() {
        return this.proxiedCommand;
    }
}
