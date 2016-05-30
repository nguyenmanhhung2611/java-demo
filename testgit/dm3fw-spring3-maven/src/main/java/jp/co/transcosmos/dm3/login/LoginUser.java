/*
 * &copy; Trans-cosmos Inc. 2006, 2007.
 * All rights reserved.
 */
package jp.co.transcosmos.dm3.login;

import java.io.Serializable;

/**
 * Valueobjects representing users that can login to the application 
 * should implement this interface.   
 * 
 * @author <a href="mailto:rick@knowleses.org">Rick Knowles</a>
 * @version $Id: LoginUser.java,v 1.3 2007/05/31 09:44:51 rick Exp $
 */
public interface LoginUser extends Serializable {

    public Object getUserId();
    public String getLoginId();    
    
    /**
     * Return true if the password was valid.
     */
	public boolean matchPassword(String pPassword);
}
