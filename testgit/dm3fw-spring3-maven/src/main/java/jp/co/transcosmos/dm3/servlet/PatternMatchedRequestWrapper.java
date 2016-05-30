/*
 * &copy; Trans-cosmos Inc. 2006, 2007.
 * All rights reserved.
 */
package jp.co.transcosmos.dm3.servlet;

import java.util.Arrays;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * Internal object used by the framework to fake additional request parameters using 
 * wildcards extracted from the URL pattern.
 * 
 * Wraps a request so that it an simulate extra parameter matches inside this request
 * 
 * @author <a href="mailto:rick_knowles@hotmail.com">Rick Knowles</a>
 * @version $Id: PatternMatchedRequestWrapper.java,v 1.4 2007/05/31 07:44:43 rick Exp $
 */
public class PatternMatchedRequestWrapper extends HttpServletRequestWrapper {

    private String parameterPairs[][];
    private Map<String,String []> overriddenParameterValues;
    
    public PatternMatchedRequestWrapper(HttpServletRequest request, String parameterPairs[][]) {
        super(request);
        this.parameterPairs = parameterPairs;
    }
    
    public String[][] getParameterPairs() {
        return this.parameterPairs; // for use in form population
    }

    public String getParameter(String name) {
        ensureParentParameterNames();
        String overriddenValues[] = this.overriddenParameterValues.get(name);
        if ((overriddenValues == null) || (overriddenValues.length == 0)) {
            return super.getParameter(name);        
        } else if (overriddenValues.length == 1) {
            return overriddenValues[0];
        } else {
            throw new IllegalArgumentException("Parameter " + name + " has " + 
                    overriddenValues.length + " values: " + Arrays.asList(overriddenValues));
        }
    }

// 2013.10.18 H.Mizuno Servlet API 3.0 対応（Tomcat7、Weblogic12c...） start
//    @SuppressWarnings("unchecked")
//    public Map<?,?> getParameterMap() {
//        ensureParentParameterNames();
//        Map<Object,Object> copy = new Hashtable<Object,Object>(super.getParameterMap());
    public Map<String, String[]> getParameterMap() {
        ensureParentParameterNames();
        Map<String, String[]> copy = new Hashtable<String, String[]>(super.getParameterMap());
// 2013.10.18 H.Mizuno Servlet API 3.0 対応（Tomcat7、Weblogic12c...） end
        copy.putAll(this.overriddenParameterValues);
        return copy;
    }

// 2013.10.18 H.Mizuno Servlet API 3.0 対応（Tomcat7、Weblogic12c...） start
//    public Enumeration<?> getParameterNames() {
    public Enumeration<String> getParameterNames() {
// 2013.10.18 H.Mizuno Servlet API 3.0 対応（Tomcat7、Weblogic12c...） start
        return super.getParameterNames();
    }

    public String[] getParameterValues(String name) {
        ensureParentParameterNames();
        String returnValues[] = (String []) this.overriddenParameterValues.get(name);
        if (returnValues == null) {
            returnValues = super.getParameterValues(name);
        }
        return returnValues;
    }

    /**
     * Builds a local map we use to override the parent parameters when we have both
     */
    private void ensureParentParameterNames() {
        synchronized (super.getRequest()) {
            if (this.overriddenParameterValues == null) {
                this.overriddenParameterValues = new Hashtable<String,String []>();
                
                for (int n = 0; n < this.parameterPairs.length; n++) {
                    String name = this.parameterPairs[n][0];
                    String value = this.parameterPairs[n][1];
                    String parentValues[] = (String []) this.overriddenParameterValues.get(name);
                    if (parentValues == null) {
                        parentValues = super.getParameterValues(name);
                    }
                    if (parentValues == null) {
                        this.overriddenParameterValues.put(name, new String[] {value});
                    } else {
                        // Write a new array with the new value pre-pended
                        String newValues[] = new String[parentValues.length + 1];
                        newValues[0] = value;
                        System.arraycopy(parentValues, 0, newValues, 1, parentValues.length);
                        this.overriddenParameterValues.put(name, newValues);
                    }   
                }
            }            
        }
    }

    /**
     * @deprecated
     */
    public boolean isRequestedSessionIdFromUrl() {
        return super.isRequestedSessionIdFromUrl();
    }

    /**
     * @deprecated
     */
    public String getRealPath(String pArg0) {
        return super.getRealPath(pArg0);
    }
}
