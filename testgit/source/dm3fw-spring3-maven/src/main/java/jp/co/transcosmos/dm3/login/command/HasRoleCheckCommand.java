/*
 * &copy; Trans-cosmos Inc. 2006, 2007.
 * All rights reserved.
 */
package jp.co.transcosmos.dm3.login.command;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.login.UserRoleSet;
import jp.co.transcosmos.dm3.login.support.LoginAwareSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;

/**
 * Checks that the logged in user has the user role required to access this url.
 * The logged in user must have one of these roles to be able to 
 *
 * @deprecated Please use the V2 authentication model instead.
 * 
 */
public class HasRoleCheckCommand extends LoginAwareSupport implements Command {
    private static final Log log = LogFactory.getLog(HasRoleCheckCommand.class);

    private Collection<String> roles;
    
    public void setRoles(Collection<String> pRoles) {
        this.roles = pRoles;
    }
    
    public void setRole(Collection<String> pRoles) {
        setRoles(pRoles);
    }

    public ModelAndView handleRequest(HttpServletRequest request, 
            HttpServletResponse response) throws Exception {
        UserRoleSet roleset = this.getRoleSetFromRequest(request);
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
