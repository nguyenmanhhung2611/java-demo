/*
 * &copy; Trans-cosmos Inc. 2006, 2007.
 * All rights reserved.
 */
package jp.co.transcosmos.dm3.login.command.v2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.form.FormPopulator;
import jp.co.transcosmos.dm3.login.Authentication;
import jp.co.transcosmos.dm3.login.CookieLoginUser;
import jp.co.transcosmos.dm3.login.LoginUser;
import jp.co.transcosmos.dm3.login.LoginUtils;
import jp.co.transcosmos.dm3.login.form.LoginForm;
import jp.co.transcosmos.dm3.utils.ServletUtils;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;

/**
 * Command for executing a login attempt. If the login and password supplied are
 * valid, the redirectURL specified is redirected too (i.e. they are allowed to go 
 * where they wanted to). If not, the authentication object defines a failure URL, to
 * which we redirect.
 * <p>
 * See the authentication tutorial for more details of authentication feature usage.
 * 
 * @author <a href="mailto:rick@knowleses.org">Rick Knowles</a>
 * @version $Id: LoginCommand.java,v 1.3 2007/07/27 07:06:13 abe Exp $
 */
public class LoginCommand implements Command {
    
    private static final Log log = LogFactory.getLog(LoginCommand.class);
    
    private Authentication authentication;
    private boolean cookieLoginEnabled;
    private String manualLoginTokenName = "manualLoginToken";
    private String redirectURLParameter = "redirectURL";
    private Integer autoLoginCookieExpirySeconds;
    private String cookieDomain = null;

    public void setAuthentication(Authentication pAuthentication) {
        this.authentication = pAuthentication;
    }
    public void setCookieLoginEnabled(boolean cookieLoginEnabled) {
        this.cookieLoginEnabled = cookieLoginEnabled;
    }
    public void setManualLoginTokenName(String manualLoginTokenName) {
		this.manualLoginTokenName = manualLoginTokenName;
	}    
    public void setAutoLoginCookieExpirySeconds(int pAutoLoginCookieExpirySeconds) {
        this.autoLoginCookieExpirySeconds = new Integer(pAutoLoginCookieExpirySeconds);
    }
    public void setRedirectURLParameter(String pOriginalURLParameter) {
        this.redirectURLParameter = pOriginalURLParameter;
    }
    
    public ModelAndView handleRequest(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
    	
        LoginForm loginForm = getPopulatedLoginForm(request);
        List<ValidationFailure> errors = new ArrayList<ValidationFailure>();
        if (!validateForm(loginForm, errors)) {
            return loginFailure(request, response, null, loginForm, errors);
        }
        LoginUser user = this.authentication.login(loginForm.getLoginID(), 
                loginForm.getPassword());
        if (user == null) {
            return loginFailure(request, response, user, loginForm, null);
		} else {
            return loginSuccess(request, response, user, loginForm);
        }
	}
    
    protected LoginForm getPopulatedLoginForm(HttpServletRequest request) {
        LoginForm loginForm = new LoginForm();  
        FormPopulator.populateFormBeanFromRequest(request, loginForm);
        return loginForm;
    }
    
    protected boolean validateForm(LoginForm loginForm, List<ValidationFailure> errors) {
        return loginForm.validate(errors);
    }
    
    protected ModelAndView loginSuccess(HttpServletRequest request, 
            HttpServletResponse response, LoginUser user, LoginForm loginForm) {
        log.info("set user: " + user);
        
        if (this.manualLoginTokenName != null) {
        	HttpSession session = request.getSession();
        	session.setAttribute(this.manualLoginTokenName, Boolean.TRUE);
        }

        if (this.cookieLoginEnabled && 
                loginForm.isAutoLoginSelected() && 
                (user instanceof CookieLoginUser)) {
            LoginUtils.setAutoLoginCookies(response, request.getContextPath(), user.getUserId(), 
                    this.authentication.retrieveCookieLoginPassword(), 
                    this.autoLoginCookieExpirySeconds, this.cookieDomain);
        } else {
            LoginUtils.setAutoLoginCookies(response, request.getContextPath(), null, null, this.cookieDomain);
        }
        return new ModelAndView("success", "redirectURL", getRedirectURL(request));        
    }
    
    protected ModelAndView loginFailure(HttpServletRequest request, 
            HttpServletResponse response, LoginUser user, LoginForm loginForm,
            List<ValidationFailure> errors) {
        LoginUtils.setAutoLoginCookies(response, request.getContextPath(), null, null, this.cookieDomain);
        this.authentication.logoutCurrentUser();
        Map<String,Object> model = new HashMap<String,Object>();
        model.put("redirectURL", getRedirectURL(request));
        model.put("loginForm", loginForm);
        if (errors != null) {
            model.put("errors", errors);
        }
        
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
        if (userType != null) {
            return new ModelAndView("failureWithUserType", model);
        } else {
            return new ModelAndView("failure", model);
        }
    }
    
    protected String getRedirectURL(HttpServletRequest request) {
        return request.getParameter(this.redirectURLParameter);
    }
}