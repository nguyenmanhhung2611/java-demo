/*
 * &copy; Trans-cosmos Inc. 2006, 2007.
 * All rights reserved.
 */
package jp.co.transcosmos.dm3.login.logging;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.login.LoginUser;
import jp.co.transcosmos.dm3.login.support.LoginAwareSupport;
import jp.co.transcosmos.dm3.utils.SimpleExpressionLanguage;

/**
 * Adds user details to the response header, mostly so we can log the user .
 * id in the apache logs. The headerName and headerPattern init-params can be
 * set to define the desired header name and value to add.
 * <p>
 * See the SimpleExpressionLanguage class for a description of how to make the
 * headerPattern contain dynamic content.
 * 
 * @deprecated Please use the V2 authentication model instead.
 * 
 * @author <a href="mailto:rick@knowleses.org">Rick Knowles</a>
 * @version $Id: AddResponseHeadersFilter.java,v 1.2 2007/05/31 09:44:51 rick Exp $
 */
public class AddResponseHeadersFilter extends LoginAwareSupport 
        implements Filter {

    private String headerName;
    private String headerPattern;
    private ServletContext context;
    
    public void init(FilterConfig config) throws ServletException {
        this.headerName = config.getInitParameter("headerName");
        if ((this.headerName == null) || this.headerName.equals("")) {
            this.headerName = "X-LoginId";
        }
        
        this.headerPattern = config.getInitParameter("headerPattern");
        if ((this.headerPattern == null) || this.headerPattern.equals("")) {
            this.headerPattern = "###$user.loginId###";
        }
        
        String loginUserSessionParameter = config.getInitParameter(
                "loginUserSessionParameter");
        if (loginUserSessionParameter != null) {
            setLoginUserSessionParameter(loginUserSessionParameter);
        }
        
        this.context = config.getServletContext();
    }

    public void destroy() {
        this.headerName = "X-LoginId";
        this.headerPattern = "###user.loginId###";
        this.context = null;
        setLoginUserSessionParameter("loggedInUser");
    }

    public void doFilter(ServletRequest request, ServletResponse response, 
            FilterChain chain) throws IOException, ServletException {
        // Build the header value and add it to the response
        String headerValue = SimpleExpressionLanguage.evaluateWebExpressionPatternString(
                this.headerPattern, request, response, context, 
                buildExtraParamsMap((HttpServletRequest) request), 
                false, response.getCharacterEncoding(), "");
        if ((headerValue != null) && !headerValue.equals("")) {
            ((HttpServletResponse) response).addHeader(this.headerName, headerValue);
        }
        chain.doFilter(request, response);
    }
    
    private Map<String,Object> buildExtraParamsMap(HttpServletRequest request) {
        LoginUser user = getLoggedInUser(request);
        Map<String,Object> out = new HashMap<String,Object>();
        out.put("user", user);
        out.put("$user", user);
        return out;
    }

    public ServletContext getContext() {
        return this.context;
    }

    public void setContext(ServletContext pContext) {
        this.context = pContext;
    }
}
