/*
 * &copy; Trans-cosmos Inc. 2006, 2007.
 * All rights reserved.
 */
package jp.co.transcosmos.dm3.login.command.v2;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.login.Authentication;
import jp.co.transcosmos.dm3.login.LoginUtils;

import org.springframework.web.servlet.ModelAndView;

/**
 * Logs out the user currently logged in. This command is mapped to the logout URL,
 * and is injected with an Authentication object to perform the actual logout operation.
 * <p>
 * See the authentication tutorial for more details of authentication feature usage.
 * 
 * @author <a href="mailto:rick@knowleses.org">Rick Knowles</a>
 * @version $Id: LogoutCommand.java,v 1.3 2007/07/27 07:06:13 abe Exp $
 */
public class LogoutCommand implements Command {

    private Authentication authentication;
    private String manualLoginTokenName = "manualLoginToken";
    private boolean removeAutoLoginCookies = false;
    private String cookieDomain = null;

    public void setAuthentication(Authentication pAuthentication) {
        this.authentication = pAuthentication;
    }
    public void setManualLoginTokenName(String manualLoginTokenName) {
		this.manualLoginTokenName = manualLoginTokenName;
	}
    
    public void setRemoveAutoLoginCookies(boolean pRemoveAutoLoginCookies) {
        this.removeAutoLoginCookies = pRemoveAutoLoginCookies;
    }

    public void setCookieDomain(String cookieDomain) {
		this.cookieDomain = cookieDomain;
	}
	public ModelAndView handleRequest(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        this.authentication.logoutCurrentUser();
        if (this.manualLoginTokenName != null) {
        	HttpSession session = request.getSession(false);
        	if (session != null) {
        		session.removeAttribute(this.manualLoginTokenName);
        	}
        }
        if (this.removeAutoLoginCookies) {
            LoginUtils.setAutoLoginCookies(response, request.getContextPath(), null, null, this.cookieDomain);
        }
		return new ModelAndView("success");
	}
}
