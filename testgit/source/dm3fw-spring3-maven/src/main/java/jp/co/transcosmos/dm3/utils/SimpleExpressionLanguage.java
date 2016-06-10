/*
 * &copy; Trans-cosmos Inc. 2006, 2007.
 * All rights reserved.
 */
package jp.co.transcosmos.dm3.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Simple expression language interpreter. Syntax is slightly different to the 
 * official JSTL EL syntax, but offers many of the same features.
 */
public class SimpleExpressionLanguage {
    private static final Log log = LogFactory.getLog(ReflectionUtils.class);

    /**
     * Works like a simple EL, where abc.def means almost model.getAbc().getDef().
     * If model is a map, it does model.get("abc") instead. Calls recursively 
     * based on the dot separator. This is the very primitive form - recommend using
     * the pattern string form in most cases.
     */
    public static Object evaluateExpression(Object model, String key) {
        if (model == null) {
            return null;
        }
    
        int dotPos = key.indexOf('.');
        while ((dotPos > 0) && (key.charAt(dotPos - 1) == '\\')) {
            dotPos = key.indexOf('.', dotPos + 1); // loop until we find an unescaped dot
        }
        
        if (dotPos != -1) {
            String preKey = key.substring(0, dotPos).replaceAll("\\\\.", ".");
            String postKey = key.substring(dotPos + 1);
            if (model instanceof Map) {
                Map<?,?> modelMap = (Map<?,?>) model;
                if (modelMap.containsKey(key)) {
                    return modelMap.get(key);
                }
                return evaluateExpression(modelMap.get(preKey), postKey);
            } else {
                Object value = null;
                try {
                    value = ReflectionUtils.getFieldValueByGetter(model, preKey);
                } catch (Throwable err) {
                    log.warn("Error getting value " + preKey + " from object " +
                            model + " error=" + err.toString(), err);
                    return null;
                }
                return evaluateExpression(value, postKey);
            }
        } else {
            if (model instanceof Map) {
                return ((Map<?,?>) model).get(key);
            } else {
                try {
                    return ReflectionUtils.getFieldValueByGetter(model, key);
                } catch (Throwable err) {
                    log.warn("Error getting value " + key + " from object " +
                            model + " error=" + err.toString());
                    return null;
                }
            }
        }
    }
    
    /**
     * Web object aware wrapper for the evaluateExpression() method. This version knows
     * how to interpret keys such as $request or $session in the expression language. Usually
     * called as part of the pattern string method below.
     */
    public static Object evaluateWebExpression(String key, HttpServletRequest request, 
            HttpServletResponse response, ServletContext context, 
            Map<String,Object> unknownScope) 
            throws IOException, ServletException {
        String firstSegment = getFirstKey(key);
        String remainderKey = getRemainderKey(key);
        
        unknownScope = addDefaultExpressionsIfMissing(unknownScope);
        
        if (firstSegment.equalsIgnoreCase("$request")) {
            if (remainderKey == null) {
                return request;
            } else  {
                return evaluateExpression(request, remainderKey);
            }
        } else if (firstSegment.equalsIgnoreCase("$requestAtts")) {
            if (remainderKey == null) {
                return request;
            } else  {
                return evaluateExpression(
                        request.getAttribute(getFirstKey(remainderKey)), 
                        getRemainderKey(remainderKey));
            }
        } else if (firstSegment.equalsIgnoreCase("$requestParams")) {
            if (remainderKey == null) {
                return request;
            } else  {
                return request.getParameter(remainderKey);
            }
        } else if (firstSegment.equalsIgnoreCase("$allRequestParams")) {
            return ServletUtils.writeAllParamsAsQueryString(request);
        } else if (firstSegment.equalsIgnoreCase("$session")) {
            HttpSession session = ((HttpServletRequest) request).getSession(false);
            if (session == null) {
                return null;
            } else if (remainderKey == null) {
                return session;
            } else  {
                return evaluateExpression(session, remainderKey);
            }
        } else if (firstSegment.equalsIgnoreCase("$sessionAtts")) {
            HttpSession session = ((HttpServletRequest) request).getSession(false);
            if (session == null) {
                return null;
            } else if (remainderKey == null) {
                return session;
            } else  {
                return evaluateExpression(
                        session.getAttribute(getFirstKey(remainderKey)), 
                        getRemainderKey(remainderKey));
            }
        } else if (firstSegment.equalsIgnoreCase("$response")) {
            if (remainderKey == null) {
                return response;
            } else  {
                return evaluateExpression(response, remainderKey);
            }
        } else if (firstSegment.equalsIgnoreCase("$context")) {
            if (remainderKey == null) {
                return context;
            } else  {
                return evaluateExpression(context, remainderKey);
            }
        } else if (firstSegment.equalsIgnoreCase("$webroot") ||
                firstSegment.equalsIgnoreCase("webroot")) {
            return request.getContextPath();
        } else if (firstSegment.equalsIgnoreCase("$querystring") ||
                firstSegment.equalsIgnoreCase("querystring")) {
            return request.getQueryString();
        } else if (unknownScope != null) {
            if (remainderKey == null) {
                return unknownScope.get(firstSegment);
            } else  {
                return evaluateExpression(unknownScope.get(firstSegment), remainderKey);
            }
        } else {
            throw new IllegalArgumentException("Bad pattern defined: firstKey must be " +
                    "one of [$request, $requestAtts, $requestParams, $allRequestParams, " +
                    "$session, $sessionAtts, $response, $context], not " + firstSegment);
        }
    }
    
    /**
     * Adds a couple of variables that are always available
     */
    protected static Map<String,Object> addDefaultExpressionsIfMissing(Map<String,Object> input) {
        Map<String,Object> output = null;
        if ((input == null) || !input.containsKey("now")) {
            output = (input == null ? new HashMap<String,Object>() : new HashMap<String,Object>(input));
            output.put("now", "" + System.currentTimeMillis());
        }
        if ((input == null) || !input.containsKey("hostname")) {
            if (output == null) {
                output = (input == null ? new HashMap<String,Object>() : new HashMap<String,Object>(input));
            }
            output.put("hostname", ServletUtils.getLocalhost());
        }
        return output == null ? input : output;
    }
    
    public static String getFirstKey(String key) {
        int firstDot = key.indexOf(".");
        return (firstDot != -1 ? key.substring(0, firstDot) : key);       
    }
    
    public static String getRemainderKey(String key) {
        int firstDot = key.indexOf(".");
        return (firstDot != -1 ? key.substring(firstDot + 1) : null);   
    }
    
    /**
     * The most commonly used form of the SimpleEL function. This method is web-object
     * aware, and also can handle multiple tokens, with wildcards embedded a pattern
     * string, such as "###$requestAtts.myAtt### is a ###$requestAtts.myValue###". Also
     * allows specification of url encoding requirements, since pattern strings are 
     * often URLs.
     */
    public static String evaluateWebExpressionPatternString(String pattern, 
            ServletRequest request, ServletResponse response, 
            ServletContext context, Map<String,Object> unknownScope, 
            boolean urlEncodeByDefault, String encoding, String valueIfNull) throws IOException, ServletException {
        
        unknownScope = addDefaultExpressionsIfMissing(unknownScope);
        
        StringBuilder out = new StringBuilder();
        int startPos = 0;
        int lastStartPos = 0;
        
        while ((startPos = pattern.indexOf("###", lastStartPos)) != -1) {
            // Check for expression end
            int endPos = pattern.indexOf("###", startPos + 3);
            if (endPos == -1) {
                throw new IllegalArgumentException("Bad pattern defined");
            }
            
            String key = pattern.substring(startPos + 3, endPos);
            boolean escape = urlEncodeByDefault;
            if (key.startsWith("!")) {
                key = key.substring(1);
                escape = !urlEncodeByDefault;
            }
            Object value = evaluateWebExpression(key, 
                    (HttpServletRequest) request, 
                    (HttpServletResponse) response, 
                    context, unknownScope);
            
            out.append(pattern.substring(lastStartPos, startPos));
            if (escape) {
                value = ServletUtils.encodeURLToken(
                        value == null ? valueIfNull : value.toString(), 
                        response.getCharacterEncoding());
            }
            out.append(value == null ? valueIfNull : value.toString());
            lastStartPos = endPos + 3;
        }
        out.append(pattern.substring(lastStartPos));
        return out.toString();
    }
}
