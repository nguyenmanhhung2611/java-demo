/*
 * &copy; Trans-cosmos Inc. 2006, 2007.
 * All rights reserved.
 */
package jp.co.transcosmos.dm3.login.command.v2;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.login.Authentication;
import jp.co.transcosmos.dm3.login.LoginUser;
import jp.co.transcosmos.dm3.retry.RequestRetryParams;
import jp.co.transcosmos.dm3.utils.ServletUtils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;

/**
 * If we determine that the logged in user object in the session has aged 
 * beyond a certain time limit, a reload from the database is triggered by this
 * command. It should be mapped as a filter to all authentication-required URLs,
 * as a way of preventing old data from being kept in the session after the DB
 * has been updated.
 * <p>
 * See the authentication tutorial for more details of authentication feature usage.
 * 
 * @author <a href="mailto:rick@knowleses.org">Rick Knowles</a>
 * @version $Id: RefreshLoggedInUserCommand.java,v 1.2 2007/05/31 09:44:52 rick Exp $
 */
public class RefreshLoggedInUserCommand implements Command {
    private static final Log log = LogFactory.getLog(RefreshLoggedInUserCommand.class);

    private Authentication authentication;
    private String savedRequestSessionParameterPrefix = "savedRequest:";
    private String savedRequestRequestParameter = "redirectKey";
    
    public void setAuthentication(Authentication pAuthentication) {
        this.authentication = pAuthentication;
    }

    public ModelAndView handleRequest(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        LoginUser beforeRefresh = authentication.getLoggedInUser();
        if (beforeRefresh != null) {
            LoginUser afterRefresh = authentication.refreshLoggedInUserFromDB();
            if (afterRefresh == null) {
                log.warn("Logging out, because the user disappeared after refreshing");
                String redirectURL = saveRequest(request);
                Map<String,Object> model = new HashMap<String,Object>(); 
                model.put("redirectURL", redirectURL);
                
                // Try to get the user type so we can redirect to the right page
                String userType = null;
                if ((this.authentication.getUserTypeCookieParameterName() != null) && 
                        (this.authentication.getUserTypeCookieName() != null)) {
                    userType = ServletUtils.findFirstCookieValueByName(request, 
                            this.authentication.getUserTypeCookieName());
                    if (userType != null) {
                        model.put("userType", userType);
                    }
                }
                return new ModelAndView(userType != null ? "failureWithUserType" : "failure", model);
            }
        }
		return null;
	}
    
    protected String saveRequest(HttpServletRequest req) throws IOException, ServletException {
        return RequestRetryParams.saveRequest(req, this.savedRequestSessionParameterPrefix, 
                this.savedRequestRequestParameter);
    }
    
    public void setSavedRequestRequestParameter(String pSavedRequestRequestParameter) {
        this.savedRequestRequestParameter = pSavedRequestRequestParameter;
    }

    public void setSavedRequestSessionParameterPrefix(String pSavedRequestSessionParameterPrefix) {
        this.savedRequestSessionParameterPrefix = pSavedRequestSessionParameterPrefix;
    }
}
