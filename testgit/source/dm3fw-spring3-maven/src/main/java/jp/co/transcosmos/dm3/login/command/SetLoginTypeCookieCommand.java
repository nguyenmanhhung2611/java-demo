/*
 * &copy; Trans-cosmos Inc. 2006, 2007.
 * All rights reserved.
 */
package jp.co.transcosmos.dm3.login.command;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;

import org.springframework.web.servlet.ModelAndView;

/**
 * Sets a cookie and redirects, so we can show the appropriate contact details
 * on the login page
 */
public class SetLoginTypeCookieCommand implements Command {

    private String parameterName = "userType";
    private String cookieName = "lct"; // last contact type
    private int maxAge = 30 * 24 * 60 * 60; // 30 days by default
    private String domain;
    
    public void setParameterName(String pParameterName) {
        this.parameterName = pParameterName;
    }

    public void setCookieName(String pCookieName) {
        this.cookieName = pCookieName;
    }

    public void setDomain(String pDomain) {
        this.domain = pDomain;
    }

    public void setMaxAge(int pMaxAge) {
        this.maxAge = pMaxAge;
    }

    public ModelAndView handleRequest(HttpServletRequest request, 
            HttpServletResponse response) throws Exception {
        Cookie cookie = new Cookie(this.cookieName, request.getParameter(this.parameterName));
        cookie.setMaxAge(this.maxAge);
        cookie.setPath(request.getContextPath());
        response.addCookie(cookie);
        if (this.domain != null) {
            cookie.setDomain(this.domain);
        }
        return null;
    }

}
