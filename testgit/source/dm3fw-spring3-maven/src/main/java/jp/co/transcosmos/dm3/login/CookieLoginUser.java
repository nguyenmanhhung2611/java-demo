/*
 * &copy; Trans-cosmos Inc. 2006, 2007.
 * All rights reserved.
 */
package jp.co.transcosmos.dm3.login;

/**
 * Implement this interface to support cookie-based auto-login for users that
 * have successfully authenticated in the past.
 * 
 * @author <a href="mailto:rick@knowleses.org">Rick Knowles</a>
 * @version $Id: CookieLoginUser.java,v 1.2 2007/05/31 09:44:51 rick Exp $
 */
public interface CookieLoginUser extends LoginUser {

    /**
     * Return true if the cookie login password supplied is valid. 
     */
	public boolean matchCookieLoginPassword(String pPassword);

    public String getCookieLoginPassword();
    public void setCookieLoginPassword(String cookieLoginPassword);
}
