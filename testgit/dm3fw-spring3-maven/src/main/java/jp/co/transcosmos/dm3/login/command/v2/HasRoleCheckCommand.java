/*
 * &copy; Trans-cosmos Inc. 2006, 2007.
 * All rights reserved.
 */
package jp.co.transcosmos.dm3.login.command.v2;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.login.Authentication;
import jp.co.transcosmos.dm3.login.UserRoleSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;

/**
 * Checks that the logged in user has the user role required to access this url.
 * The logged in user must have one of these roles to be able to proceed to the page.
 * <p>
 * This command should be mapped as a filter to a protected URL, and injected with 
 * an Authentication instance scoped to the session to provide the required authentication
 * logic.
 * 
 * @author <a href="mailto:rick@knowleses.org">Rick Knowles</a>
 * @version $Id: HasRoleCheckCommand.java,v 1.2 2007/05/31 09:44:52 rick Exp $
 */
public class HasRoleCheckCommand implements Command {
    private static final Log log = LogFactory.getLog(HasRoleCheckCommand.class);

    private Collection<String> roles;
    private Authentication authentication;
    
    public void setRoles(Collection<String> pRoles) {
        this.roles = pRoles;
    }
    
    public void setRoles(String pRole) {
        this.roles = new ArrayList<String>();
        this.roles.add(pRole);
    }
    
    public void setRole(Collection<String> pRoles) {
        setRoles(pRoles);
    }
    
    public void setRole(String pRole) {
        setRoles(pRole);
    }

    public void setAuthentication(Authentication pAuthentication) {
        this.authentication = pAuthentication;
    }

    public ModelAndView handleRequest(HttpServletRequest request, 
            HttpServletResponse response) throws Exception {
        UserRoleSet roleset = this.authentication.getRoleSet();
        if (roleset == null) {
            log.warn("Roleset not found - not logged in ? Allowing, because we only" +
                    " want to apply this rolecheck to logged in users - wrap an extra " +
                    "CookieLoginCheckFilter around this when you need a login-only check");
            return null;
        } else if (roleset.hasRole(this.roles)) {
            log.info("User has defined role - allowing");
            return null;
        } else {
            log.info("User does not have defined role - rejecting");
            return new ModelAndView("failure", "neededRole", this.roles);
        }
    }

}
