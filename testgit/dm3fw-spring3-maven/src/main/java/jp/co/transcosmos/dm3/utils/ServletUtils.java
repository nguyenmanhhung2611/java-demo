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

// 2015.05.20 H.Mizuno �L���������w�肵�Ȃ� Cookie �ǉ��̃C���^�[�t�F�[�X�Asequre ������ǉ� start
// �����̃��\�b�h�̏ꍇ�A�L��������ݒ肵�Ȃ��̂ŁA�u���E�U�����ƍ폜�����B
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
// 2015.04.17 H.Mizuno �L���������w�肵�Ȃ� Cookie �ǉ��̃C���^�[�t�F�[�X�Asequre ������ǉ� end

    public static void addCookie(HttpServletResponse response, String path,
            String name, String value, int expiry) {
    	addCookie(response, path, name, value, expiry, null);
    }

// 2015.05.20 H.Mizuno cookie �� sequre �����Ή� start
    public static void addCookie(HttpServletResponse response, String path,
            String name, String value, int expiry, boolean isSequre) {
    	addCookie(response, path, name, value, expiry, null, isSequre);
    }
// 2015.05.20 H.Mizuno cookie �� sequre �����Ή� end
    
// 2015.05.20 H.Mizuno cookie �� sequre �����Ή� start
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
// 2015.05.20 H.Mizuno cookie �� sequre �����Ή� end

// 2015.04.17 H.Mizuno Cookie ���폜���郁�\�b�h��ǉ�
// �����̃��\�b�h�̏ꍇ�A�L�������� 0 �b�ɂȂ�̂� Cookie ���폜�����B
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

// 2015.04.17 H.Mizuno Cookie ���폜���郁�\�b�h��ǉ� end


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

    
    
    // ���s�R�[�h�� br �^�O�ɕϊ�����B
    // �܂��AJSTL �� c:out ���g�p���鎖���ł��Ȃ��̂ŁA�����̒l�͎��g�ŃG�X�P�[�v����B
    // ���̃��\�b�h�́Adm3-functions.tld �� function �o�^����Ă���̂� JSP ����g�p�\�B
    public static String crToHtmlTag(String stringValue) {

    	if (StringValidateUtil.isEmpty(stringValue)) return "";

    	// ���̕�����𖳊Q������B
    	String resultValue = EncodingUtils.htmlEncode(stringValue);
    	
    	// CR + LF ���ɒu��
    	resultValue = resultValue.replace("\r\n", "<br/>");

    	// CR ��u��
    	resultValue = resultValue.replace("\r", "<br/>");

    	// LF ��u��
    	resultValue = resultValue.replace("\n", "<br/>");

    	return resultValue;
    }
    
    

    // �������u������B
    // targetString �� replaceFrom �Ɏw�肳�ꂽ�����񂪊܂܂��ꍇ�AreplaceTo �Ɏw�肳�ꂽ
    // ������ɒu�����ĕ��A����B
    // targetString �� null �̏ꍇ�A�󕶎���𕜋A����B
    // ���̃��\�b�h�́Adm3-functions.tld �� function �o�^����Ă���̂� JSP ����g�p�\�B
    public static String replace(String targetString, String replaceFrom, String replaceTo){

    	if (StringValidateUtil.isEmpty(targetString)) return "";

    	// ���̕�����𖳊Q������B
    	String resultValue = EncodingUtils.htmlEncode(targetString);

    	// �������u������B
    	return resultValue.replace(replaceFrom, replaceTo);

    }

}
