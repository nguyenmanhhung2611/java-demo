/*
 * &copy; Trans-cosmos Inc. 2006, 2007.
 * All rights reserved.
 */
package jp.co.transcosmos.dm3.login.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.login.support.LoginModificationSupport;

import org.springframework.web.servlet.ModelAndView;

/**
 * This is intended to be used as a filter, mapped to areas where we want to force the user
 * to have manually logged in during this session. The LoginCommand sets a manualLoginToken
 * ojbect in the session (the auto-login by the CookieLoginCheckCommand does not set this token),
 * so this filter can redirect to the login page if the login was not manual by checking for the
 * token.
 * 
 * @deprecated Please use the V2 authentication model instead.
 * 
 */
public class ManualLoginCheckCommand extends LoginModificationSupport implements Command {

    private String manualLoginTokenName = "manualLoginToken";

    public void setManualLoginTokenName(String manualLoginTokenName) {
		this.manualLoginTokenName = manualLoginTokenName;
	}
    
    public ModelAndView handleRequest(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        if (this.manualLoginTokenName != null) {
        	HttpSession session = request.getSession(false);
        	if (session != null) {
        		if (session.getAttribute(this.manualLoginTokenName) != null) {
        			return null;
        		}
        	}
    		return new ModelAndView("failure");
        }
		return null;
	}
}
