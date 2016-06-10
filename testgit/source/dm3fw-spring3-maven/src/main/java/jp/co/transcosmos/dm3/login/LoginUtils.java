/*
 * &copy; Trans-cosmos Inc. 2006, 2007.
 * All rights reserved.
 */
package jp.co.transcosmos.dm3.login;

import java.security.SecureRandom;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.utils.CheckDigitUtil;
import jp.co.transcosmos.dm3.utils.CipherUtil;
import jp.co.transcosmos.dm3.utils.EncodingUtils;
import jp.co.transcosmos.dm3.utils.ServletUtils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Internally used login cookie/session utility functions.
 * @author <a href="mailto:rick@knowleses.org">Rick Knowles</a>
 * @version $Id: LoginUtils.java,v 1.4 2007/07/31 09:32:26 rick Exp $
 */
public class LoginUtils {
    private static final Log log = LogFactory.getLog(LoginUtils.class);
    
    private static final int EXPIRY_REMOVE = 0 ;
    private static final int EXPIRY_SESSION = -1;
    private static final int EXPIRY_ENTIRE = 60 * 60 * 24 * 365 * 10 ;
    
    private static final String MAGIC_PREFIX = "mMaA6Gi1cC";

    private static final String AUTOLOGIN_USERID_COOKIE_NAME = "auto-login.ci";
    private static final String AUTOLOGIN_SECRET_COOKIE_NAME = "auto-login.cp";

    private static CipherUtil userIdCookieCipher = new CipherUtil("_cid1234");   

// 2015.07.07 H.Mizuno 乱数生成セキュリティ対応 start
    private static SecureRandom rand = new SecureRandom();
// 2015.07.07 H.Mizuno 乱数生成セキュリティ対応 start
    
    public static void setAutoLoginCookies(HttpServletResponse pResponse, String contextPath, 
            Object autoLoginUserId, String autoLoginSecret, String cookieDomain) {
        setAutoLoginCookies(pResponse, contextPath, autoLoginUserId, autoLoginSecret, null, cookieDomain);
    }

// 2015.05.20 H.Mizuno cookie の sequre 属性対応 start
/*
    public static void setAutoLoginCookies(HttpServletResponse pResponse, String contextPath,
            Object autoLoginUserId, String autoLoginSecret, Integer expiry, String cookieDomain) {
    	if ((autoLoginSecret == null) || autoLoginSecret.equals("")) {
            // remove cookies
            ServletUtils.addCookie(pResponse, contextPath, AUTOLOGIN_USERID_COOKIE_NAME, 
        	        null, EXPIRY_REMOVE, cookieDomain);
        	ServletUtils.addCookie(pResponse, contextPath, AUTOLOGIN_SECRET_COOKIE_NAME, 
        	        null, EXPIRY_REMOVE, cookieDomain);

        } else {
        	ServletUtils.addCookie(pResponse, contextPath, AUTOLOGIN_USERID_COOKIE_NAME, 
        	        encryptAutoLoginUserId(autoLoginUserId), 
        	        expiry != null ? expiry.intValue() : EXPIRY_ENTIRE, cookieDomain);
        	ServletUtils.addCookie(pResponse, contextPath, AUTOLOGIN_SECRET_COOKIE_NAME, 
        	        autoLoginSecret, expiry != null ? expiry.intValue() : EXPIRY_ENTIRE, cookieDomain);
        }
    }
*/
    public static void setAutoLoginCookies(HttpServletResponse pResponse, String contextPath, 
            Object autoLoginUserId, String autoLoginSecret, String cookieDomain, boolean isSequre) {
        setAutoLoginCookies(pResponse, contextPath, autoLoginUserId, autoLoginSecret, null,
        		cookieDomain, isSequre);
    }

    public static void setAutoLoginCookies(HttpServletResponse pResponse, String contextPath,
            Object autoLoginUserId, String autoLoginSecret, Integer expiry, String cookieDomain) {

    	setAutoLoginCookies(pResponse, contextPath, autoLoginUserId, autoLoginSecret, expiry, cookieDomain,
    			false);
    }

    public static void setAutoLoginCookies(HttpServletResponse pResponse, String contextPath,
            Object autoLoginUserId, String autoLoginSecret, Integer expiry, String cookieDomain,
            boolean isSequre) {

    	if ((autoLoginSecret == null) || autoLoginSecret.equals("")) {
            // remove cookies
            ServletUtils.addCookie(pResponse, contextPath, AUTOLOGIN_USERID_COOKIE_NAME, 
        	        null, EXPIRY_REMOVE, cookieDomain, isSequre);
        	ServletUtils.addCookie(pResponse, contextPath, AUTOLOGIN_SECRET_COOKIE_NAME, 
        	        null, EXPIRY_REMOVE, cookieDomain, isSequre);

        } else {
        	ServletUtils.addCookie(pResponse, contextPath, AUTOLOGIN_USERID_COOKIE_NAME, 
        	        encryptAutoLoginUserId(autoLoginUserId), 
        	        expiry != null ? expiry.intValue() : EXPIRY_ENTIRE, cookieDomain,
        	        isSequre);
        	ServletUtils.addCookie(pResponse, contextPath, AUTOLOGIN_SECRET_COOKIE_NAME, 
        	        autoLoginSecret, expiry != null ? expiry.intValue() : EXPIRY_ENTIRE, cookieDomain,
        	        isSequre);
        }
    }
// 2015.05.20 H.Mizuno cookie の sequre 属性対応 end
    
    public static String getAutoLoginUserIdFromCookie(HttpServletRequest request) { 
        return decryptAutoLoginUserId(
                    ServletUtils.decodeURLToken(
                            ServletUtils.findFirstCookieValueByName(
                                    request, AUTOLOGIN_USERID_COOKIE_NAME), 
                            request.getCharacterEncoding()));
    }
    
    public static String getAutoLoginPasswordFromCookie(HttpServletRequest pRequest) {
        return ServletUtils.findFirstCookieValueByName(pRequest, AUTOLOGIN_SECRET_COOKIE_NAME);
    }

    public static String generateAutoLoginPassword() {
// 2015.07.07 H.Mizuno 乱数生成セキュリティ対応 start
//        return EncodingUtils.md5Encode("your mama so fat" + new Random().nextLong());
        return EncodingUtils.md5Encode("your mama so fat" + rand.nextLong());
// 2015.07.07 H.Mizuno 乱数生成セキュリティ対応 end
    }

    private static String decryptAutoLoginUserId(String encrypted) {
        if (encrypted == null) {
            return null;
        }
        if (!CheckDigitUtil.checkTail(encrypted)) {
            log.error("CookieAuthUtil CheckDigit error::" + encrypted);
            return null;
        }

        // decode
        String userID = userIdCookieCipher.decode(encrypted.substring(0, encrypted.length() - 1));

        // Remove Magic Prefix
        if (userID.startsWith(MAGIC_PREFIX)) {
            return userID.substring(MAGIC_PREFIX.length());
        } else {
            log.error("CookieAuthUtil Can't Remove Magic::" + userID);
            return null;
        }
    }
    
    private static String encryptAutoLoginUserId(Object userID) {
       return CheckDigitUtil.addDigitToTail(userIdCookieCipher.encode(MAGIC_PREFIX + userID));
    }
    
    public static void setLoggedInDisplaySwitchCookie(HttpServletRequest request, 
            HttpServletResponse response, String name, boolean loggedIn, String cookieDomain) {
        if (name != null) {
            ServletUtils.addCookie(response, request.getContextPath(), 
                    name == null ? "loggedInDisplayCookie" : name, 
                    loggedIn ? "true" : "false", 
                    loggedIn ? EXPIRY_SESSION : EXPIRY_REMOVE, cookieDomain);
        }
    }
}
