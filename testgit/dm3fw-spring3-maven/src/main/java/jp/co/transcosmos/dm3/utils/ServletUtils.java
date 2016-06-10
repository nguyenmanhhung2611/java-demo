/*
 * &copy; Trans-cosmos Inc. 2006, 2007.
 * All rights reserved.
 */
package jp.co.transcosmos.dm3.utils;

import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Utility methods to do with servlet spec features and url-encoding or decoding.
 * 
 * @author <a href="mailto:rick_knowles@hotmail.com">Rick Knowles</a>
 * @version $Id: ServletUtils.java,v 1.4 2007/07/27 07:06:13 abe Exp $
 */
public class ServletUtils {
    private static final Log log = LogFactory.getLog(ServletUtils.class);

    public static String getDecodedUrlPath(HttpServletRequest request) {
        String servletPath = request.getServletPath();
        String pathInfo = request.getPathInfo();
        String urlPath = (servletPath != null ? servletPath : "") + (pathInfo != null ? pathInfo : "");
        
        // Strip url to non-query string bits - probably never happens, just to be safe
        if (urlPath.indexOf('?') != -1) {
            urlPath = urlPath.substring(0, urlPath.indexOf('?'));
        }
        return decodeURLToken(urlPath, request.getCharacterEncoding());
    }

    public static String decodeURLToken(String token, String encoding) {
        if (token == null) {
            return null;
        }
        if (encoding == null) {
            encoding = "ISO-8859-1";
        }
        try {
            return URLDecoder.decode(token, encoding);
        } catch (UnsupportedEncodingException err) {
            return token;
        }
    }

    public static String encodeURLToken(String token, String encoding) {
        if (token == null) {
            return null;
        }
        if (encoding == null) {
            encoding = "ISO-8859-1";
        }
        try {
            return URLEncoder.encode(token, encoding);
        } catch (UnsupportedEncodingException err) {
            return token;
        }
    }

    /**
     * Added because weblogic doesn't follow the spec with regard to redirect URL rewriting. It should
     * call the current request's request.getRequestURL() method to build the scheme://hostname:port prefix
     * on relative URLs, but instead uses the initially incoming value.
     * 
     * Yet another example of just how the box Weblogic comes packaged in is a better container 
     * than the software inside it. 
     */ 
    public static String fixPartialURL(String partialURL, HttpServletRequest request) {
        if (partialURL.indexOf("://") == -1) {
            String prefix = request.getScheme() + "://" + request.getServerName();
            if (!((request.getServerPort() == 80) && request.getScheme().equals("http"))
                    && !((request.getServerPort() == 443) && request.getScheme().equals("https"))) {
                prefix  = prefix + ":" + request.getServerPort();
            }
            
            String replaced = null;
            if (partialURL.startsWith("/")) {
                replaced = prefix + partialURL;
            } else {
                String currentURI = request.getRequestURI();
                if (currentURI.indexOf("?") != -1) {
                    currentURI = currentURI.substring(currentURI.indexOf("?"));
                }
                int lastSlashPos = currentURI.lastIndexOf("/");
                if (lastSlashPos == -1) {
                    replaced = prefix + partialURL;
                } else {
                    replaced = prefix + currentURI.substring(0, lastSlashPos + 1) + partialURL;
                }
            }
            log.info("Rewrote partial RedirectURL to " + replaced);
            return replaced;
        } else {
            return partialURL;
        }
    }
    
    public static String getLocalhost() {
        String localhost = "localhost";
        try {
            localhost = InetAddress.getLocalHost().getHostName();
        } catch (Throwable err) {
            log.error("Error looking up server name", err);
        }
        return localhost;
    }
    
    public static String writeBaseURL(HttpServletRequest request, boolean https) {
        if (((request.getServerPort() == 80) && !request.isSecure()) ||
            ((request.getServerPort() == 443) && request.isSecure())) {
            return (https ? "https" : "http") + "://" + request.getServerName() + request.getContextPath();
        } else {
            log.debug("Ignoring https parameter because request port was non-standard: port=" + 
                    request.getServerPort() + ", isSecure=" + request.isSecure());
            return request.getScheme() + "://" + request.getServerName() + ":" + 
                    request.getServerPort() + request.getContextPath();
        }
    }

    public static String findFirstCookieValueByName(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (int n = 0; n < cookies.length; n++) {
                if (cookies[n].getName().equals(name)) {
                    return cookies[n].getValue();
                }
            }
        }
        return null;
    }

// 2015.05.20 H.Mizuno 有効期限を指定しない Cookie 追加のインターフェース、sequre 属性を追加 start
// これらのメソッドの場合、有効期限を設定しないので、ブラウザを閉じると削除される。
    public static void addCookie(HttpServletResponse response, String path,
            String name, String value) {
    	addCookie(response, path, name, value, -1, null);
    }

    public static void addCookie(HttpServletResponse response, String path,
            String name, String value, boolean isSequre) {
    	addCookie(response, path, name, value, -1, null, isSequre);
    }

    public static void addCookie(HttpServletResponse response, String path,
            String name, String value, String cookieDomain) {
    	addCookie(response, path, name, value, -1, cookieDomain);
    }

    public static void addCookie(HttpServletResponse response, String path,
            String name, String value, String cookieDomain, boolean isSequre) {
    	addCookie(response, path, name, value, -1, cookieDomain, isSequre);
    }
// 2015.04.17 H.Mizuno 有効期限を指定しない Cookie 追加のインターフェース、sequre 属性を追加 end

    public static void addCookie(HttpServletResponse response, String path,
            String name, String value, int expiry) {
    	addCookie(response, path, name, value, expiry, null);
    }

// 2015.05.20 H.Mizuno cookie の sequre 属性対応 start
    public static void addCookie(HttpServletResponse response, String path,
            String name, String value, int expiry, boolean isSequre) {
    	addCookie(response, path, name, value, expiry, null, isSequre);
    }
// 2015.05.20 H.Mizuno cookie の sequre 属性対応 end
    
// 2015.05.20 H.Mizuno cookie の sequre 属性対応 start
/*
    public static void addCookie(HttpServletResponse response, String path,
            String name, String value, int expiry, String cookieDomain) {
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(expiry);
        cookie.setPath(path != null && !path.equals("") ? path : "/");
        if(cookieDomain != null) {
        	cookie.setDomain(cookieDomain);
        }
        response.addCookie(cookie);
    }
*/

    public static void addCookie(HttpServletResponse response, String path,
            String name, String value, int expiry, String cookieDomain) {
    	addCookie(response, path, name, value, expiry, cookieDomain, false);
    }

    public static void addCookie(HttpServletResponse response, String path,
            String name, String value, int expiry, String cookieDomain, boolean isSequre) {
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(expiry);
        cookie.setPath(path != null && !path.equals("") ? path : "/");
        cookie.setSecure(isSequre);
        if(cookieDomain != null) {
        	cookie.setDomain(cookieDomain);
        }
        response.addCookie(cookie);
    }
// 2015.05.20 H.Mizuno cookie の sequre 属性対応 end

// 2015.04.17 H.Mizuno Cookie を削除するメソッドを追加
// これらのメソッドの場合、有効期限が 0 秒になるので Cookie が削除される。
    public static void delCookie(HttpServletResponse response, String path,
            String name) {
    	addCookie(response, path, name, "", 0, null);
    }

    public static void delCookie(HttpServletResponse response, String path,
            String name, boolean isSequre) {
    	addCookie(response, path, name, "", 0, null, isSequre);
    }

    public static void delCookie(HttpServletResponse response, String path,
            String name, String cookieDomain) {
    	addCookie(response, path, name, "", 0, cookieDomain);
    }

    public static void delCookie(HttpServletResponse response, String path,
            String name, String cookieDomain, boolean isSequre) {
    	addCookie(response, path, name, "", 0, cookieDomain, isSequre);
    }

// 2015.04.17 H.Mizuno Cookie を削除するメソッドを追加 end


    public static String writeAllParamsAsQueryString(HttpServletRequest request) {
        String encoding = request.getCharacterEncoding();
        StringBuilder out = new StringBuilder();
        Collection<?> paramList = Collections.list(request.getParameterNames());
        if (paramList.isEmpty()) {
            return "";
        }
        String paramNames[] = paramList.toArray(new String[paramList.size()]);
        Arrays.sort(paramNames);
        for (int n = 0; n < paramNames.length; n++) {
            String values[] = request.getParameterValues(paramNames[n]);
            Arrays.sort(values);
            for (int j = 0; j < values.length; j++) {
                out.append(ServletUtils.encodeURLToken(paramNames[n], encoding)).append("=");
                out.append(ServletUtils.encodeURLToken(values[j], encoding)).append("&");
            }
        }
        return out.substring(0, out.length() - 1);        
    }

    
    
    // 改行コードを br タグに変換する。
    // また、JSTL の c:out を使用する事ができないので、引数の値は自身でエスケープする。
    // このメソッドは、dm3-functions.tld で function 登録されているので JSP から使用可能。
    public static String crToHtmlTag(String stringValue) {

    	if (StringValidateUtil.isEmpty(stringValue)) return "";

    	// 元の文字列を無害化する。
    	String resultValue = EncodingUtils.htmlEncode(stringValue);
    	
    	// CR + LF を先に置換
    	resultValue = resultValue.replace("\r\n", "<br/>");

    	// CR を置換
    	resultValue = resultValue.replace("\r", "<br/>");

    	// LF を置換
    	resultValue = resultValue.replace("\n", "<br/>");

    	return resultValue;
    }
    
    

    // 文字列を置換する。
    // targetString に replaceFrom に指定された文字列が含まれる場合、replaceTo に指定された
    // 文字列に置換して復帰する。
    // targetString が null の場合、空文字列を復帰する。
    // このメソッドは、dm3-functions.tld で function 登録されているので JSP から使用可能。
    public static String replace(String targetString, String replaceFrom, String replaceTo){

    	if (StringValidateUtil.isEmpty(targetString)) return "";

    	// 元の文字列を無害化する。
    	String resultValue = EncodingUtils.htmlEncode(targetString);

    	// 文字列を置換する。
    	return resultValue.replace(replaceFrom, replaceTo);

    }

}
