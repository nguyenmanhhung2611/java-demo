/*
 * &copy; Trans-cosmos Inc. 2006, 2007.
 * All rights reserved.
 */
package jp.co.transcosmos.dm3.login.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.login.LoginUtils;
import jp.co.transcosmos.dm3.login.support.LoginModificationSupport;

import org.springframework.web.servlet.ModelAndView;

public class LogoutCommand extends LoginModificationSupport implements Command {

    private String loggedInDisplaySwitchCookieName = "lidsc";
    private String manualLoginTokenName = "manualLoginToken";
    private boolean removeAutoLoginCookies = false;
    private String cookieDomain = null;

    public void setManualLoginTokenName(String manualLoginTokenName) {
		this.manualLoginTokenName = manualLoginTokenName;
	}
    
    public void setRemoveAutoLoginCookies(boolean pRemoveAutoLoginCookies) {
        this.removeAutoLoginCookies = pRemoveAutoLoginCookies;
    }

    public void setLoggedInDisplaySwitchCookieName(String pLoggedInDisplaySwitchCookieName) {
        this.loggedInDisplaySwitchCookieName = pLoggedInDisplaySwitchCookieName;
    }
    
    public String getCookieDomain() {
        return cookieDomain;
    }

    public void setCookieDomain(String cookieDomain) {
		this.cookieDomain = cookieDomain;
	}

	public ModelAndView handleRequest(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        logoutCurrentUser(request.getSession(true));
        addRolesetToSession(request.getSession(true), null);
        if (this.manualLoginTokenName != null) {
        	HttpSession session = request.getSession(false);
        	if (session != null) {
        		session.removeAttribute(this.manualLoginTokenName);
        	}
        }
        if (this.removeAutoLoginCookies) {
            LoginUtils.setAutoLoginCookies(response, request.getContextPath(), null, null, this.cookieDomain);
        }
        LoginUtils.setLoggedInDisplaySwitchCookie(request, response, 
                this.loggedInDisplaySwitchCookieName, false, this.cookieDomain);
        
		return new ModelAndView("success");
	}
}
