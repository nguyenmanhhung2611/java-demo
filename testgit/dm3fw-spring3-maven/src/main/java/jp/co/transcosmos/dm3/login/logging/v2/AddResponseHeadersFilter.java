/*
 * &copy; Trans-cosmos Inc. 2006, 2007.
 * All rights reserved.
 */
package jp.co.transcosmos.dm3.login.logging.v2;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.login.Authentication;
import jp.co.transcosmos.dm3.login.LoginUser;
import jp.co.transcosmos.dm3.servlet.BaseFilter;
import jp.co.transcosmos.dm3.utils.SimpleExpressionLanguage;

/**
 * Adds user details to the response header, mostly so we can log the user .
 * id in the apache logs. The headerName and headerPattern init-params can be
 * set to define the desired header name and value to add.
 * <p>
 * See the SimpleExpressionLanguage class for a description of how to make the
 * headerPattern contain dynamic content.
 * 
 * @author <a href="mailto:rick@knowleses.org">Rick Knowles</a>
 * @version $Id: AddResponseHeadersFilter.java,v 1.2 2007/05/31 09:44:52 rick Exp $
 */
public class AddResponseHeadersFilter extends BaseFilter {

    private Authentication authentication;

    public void filterAction(HttpServletRequest request, HttpServletResponse response, 
            FilterChain chain) throws IOException, ServletException {
        // Build the header value and add it to the response
        String headerValue = SimpleExpressionLanguage.evaluateWebExpressionPatternString(
                getHeaderPattern(), request, response, getContext(), 
                buildExtraParamsMap((HttpServletRequest) request), 
                false, response.getCharacterEncoding(), "");
        if ((headerValue != null) && !headerValue.equals("")) {
            response.addHeader(getHeaderName(), headerValue);
        }
        chain.doFilter(request, response);
    }
    
    protected String getHeaderName() {
        return getInitParameter("headerName", "X-LoginId");
    }
    
    protected String getHeaderPattern() {
        return getInitParameter("headerPattern", "$user.loginId");
    }
    
    public void setAuthentication(Authentication pAuthentication) {
        this.authentication = pAuthentication;
    }

    private Map<String,Object> buildExtraParamsMap(HttpServletRequest request) {

        Authentication authentication = this.authentication;
        if (authentication == null) {
            authentication = buildAuthentication(request);
        }
        
// 2013.05.28 H.Mizuno Filter として使用した時に例外が発生する問題を修正 start        
//        LoginUser user = this.authentication.getLoggedInUser();
        LoginUser user = authentication.getLoggedInUser();
// 2013.05.28 H.Mizuno Filter として使用した時に例外が発生する問題を修正 end        
        Map<String,Object> out = new HashMap<String,Object>();
        out.put("user", user);
        out.put("$user", user);
        return out;
    }
    
    protected Authentication buildAuthentication(HttpServletRequest httpRequest) {
        return new Authentication(getSpringRequestAttributes(httpRequest));
    }
}
