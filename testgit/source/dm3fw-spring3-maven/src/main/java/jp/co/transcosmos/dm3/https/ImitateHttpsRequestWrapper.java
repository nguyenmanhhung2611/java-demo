/*
 * &copy; Trans-cosmos Inc. 2006, 2007.
 * All rights reserved.
 */
package jp.co.transcosmos.dm3.https;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * Wraps the request in a wrapper that causes this request to look like an HTTPS
 * request by replacing the procotol, settings isSecure=true, etc. The reason for this
 * is that the BigIP SSL accelerator acts as the SSL end point, and so the request comes 
 * in as an HTTP request with extra headers.
 * 
 * @author <a href="mailto:rick@knowleses.org">Rick Knowles</a>
 * @version $Id: ImitateHttpsRequestWrapper.java,v 1.2 2007/05/31 09:45:20 rick Exp $
 */
public class ImitateHttpsRequestWrapper extends HttpServletRequestWrapper {

    public ImitateHttpsRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    public String getScheme() {
        return "https";
    }

    public int getServerPort() {
        int superPort = super.getServerPort();
        if (superPort == 80) {
            return 443;
        }
        return superPort;
    }

    public boolean isSecure() {
        return true;
    }

    public StringBuffer getRequestURL() {
        StringBuffer out = new StringBuffer();
        out.append(getScheme()).append("://").append(getServerName());
        if (!((getServerPort() == 80) && getScheme().equals("http"))
                && !((getServerPort() == 443) && getScheme().equals("https"))) {
            out.append(':').append(getServerPort());
        }
        out.append(getRequestURI());
        return out;
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
