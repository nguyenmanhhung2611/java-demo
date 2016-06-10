/*
 * &copy; Trans-cosmos Inc. 2006, 2007.
 * All rights reserved.
 */
package jp.co.transcosmos.dm3.login.command.v2;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.login.Authentication;

import org.springframework.web.servlet.ModelAndView;

/**
 * Sets a cookie and redirects, so we can show the appropriate contact details
 * on the login page. Useful for tracking which login portal the user entered through,
 * so that when a logout occurs, we can ensure the user is redirected to the same
 * login portal they used previously.
 * <p>
 * See the authentication tutorial for more details of authentication feature usage.
 * 
 * @author <a href="mailto:rick@knowleses.org">Rick Knowles</a>
 * @version $Id: SetLoginTypeCookieCommand.java,v 1.2 2007/05/31 09:44:52 rick Exp $
 */
public class SetLoginTypeCookieCommand implements Command {

    private Authentication authentication;
    private int maxAge = 30 * 24 * 60 * 60; // 30 days by default
    private String domain;
    

    public void setAuthentication(Authentication pAuthentication) {
        this.authentication = pAuthentication;
    }

    public void setDomain(String pDomain) {
        this.domain = pDomain;
    }

    public void setMaxAge(int pMaxAge) {
        this.maxAge = pMaxAge;
    }

    public ModelAndView handleRequest(HttpServletRequest request, 
            HttpServletResponse response) throws Exception {
        Cookie cookie = new Cookie(this.authentication.getUserTypeCookieName(), 
                request.getParameter(this.authentication.getUserTypeCookieParameterName()));
        cookie.setMaxAge(this.maxAge);
        cookie.setPath(request.getContextPath());
        response.addCookie(cookie);
        if (this.domain != null) {
            cookie.setDomain(this.domain);
        }
        return null;
    }

}
