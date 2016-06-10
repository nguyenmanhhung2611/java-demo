/*
 * &copy; Trans-cosmos Inc. 2006, 2007.
 * All rights reserved.
 */
package jp.co.transcosmos.dm3.login.form;

import java.util.List;

import jp.co.transcosmos.dm3.validation.NullOrEmptyCheckValidation;
import jp.co.transcosmos.dm3.validation.ValidationChain;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

/**
 * Standard form bean used to capture the login / password data when an authentication
 * request is received. This form is used internally by the LoginCommand class. 
 *  
 * @author <a href="mailto:rick@knowleses.org">Rick Knowles</a>
 * @version $Id: LoginForm.java,v 1.2 2007/05/31 09:44:52 rick Exp $
 */
public class LoginForm {
    
	private String loginID;
	private String password;
    private String autoLogin;
    
    public String getLoginID() {
        return loginID;
    }
    public String getPassword() {
        return password;
    }
    public String getAutoLogin() {
        return autoLogin;
    }
    public void setLoginID(String loginID) {
        this.loginID = loginID;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setAutoLogin(String autoLogin) {
        this.autoLogin = autoLogin;
    }
    
    public boolean validate(List<ValidationFailure> errors) {
        int startSize = errors.size();

        ValidationChain valLoginId = new ValidationChain("loginID", this.loginID);
        valLoginId.addValidation(new NullOrEmptyCheckValidation());
        valLoginId.validate(errors);

        ValidationChain valPassword = new ValidationChain("password", this.password);
        valPassword.addValidation(new NullOrEmptyCheckValidation());
        valPassword.validate(errors);
        
        return (startSize == errors.size());
    }
    
    public boolean isAutoLoginSelected() {
        String autoLogin = getAutoLogin();
        return (autoLogin != null) && autoLogin.equals("1");
    }
}
