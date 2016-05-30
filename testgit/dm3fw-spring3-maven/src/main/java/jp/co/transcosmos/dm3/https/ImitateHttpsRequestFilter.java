/*
 * &copy; Trans-cosmos Inc. 2006, 2007.
 * All rights reserved.
 */
package jp.co.transcosmos.dm3.https;

import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.servlet.BaseFilter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * If a configured header is supplied (by default, the BigIP X-Use-Protocol header)
 * and has a specified value, we wrap the incoming request in a wrapper that fakes like
 * a secure request.
 * 
 * @author <a href="mailto:rick@knowleses.org">Rick Knowles</a>
 * @version $Id: ImitateHttpsRequestFilter.java,v 1.5 2007/05/31 09:45:20 rick Exp $
 */
public class ImitateHttpsRequestFilter extends BaseFilter {
    private static final Log log = LogFactory.getLog(ImitateHttpsRequestFilter.class);
 
    @SuppressWarnings("unchecked")
    protected void filterAction(HttpServletRequest pRequest, 
            HttpServletResponse pResponse, FilterChain pChain)
            throws IOException, ServletException {
        String headerName = getInitParameter("headerName", "X-Use-Protocol");
        String headerValue = getInitParameter("headerValue", "HTTPS");
        
        for (Enumeration<?> e = pRequest.getHeaderNames(); e.hasMoreElements(); ) {
            String header = (String) e.nextElement();
            if (header.equals(headerName)) {
                log.info("BIGIP filter found matching header: " + header + " = " + 
                        Collections.list(pRequest.getHeaders(header)));
            } else {
                log.debug("BIGIP filter found header: " + header + " = " + 
                        Collections.list(pRequest.getHeaders(header)));
            }
        }
        
        String actualValue = pRequest.getHeader(headerName);
        if ((actualValue != null) && actualValue.equalsIgnoreCase(headerValue)) {
            log.info("Found request that needs to fake as HTTPS - wrapping");
            pRequest = new ImitateHttpsRequestWrapper(pRequest);
        }
        pChain.doFilter(pRequest, pResponse);
    }
}
