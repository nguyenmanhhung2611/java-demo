package jp.co.transcosmos.dm3.servlet;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ForceBufferSizeFilter extends BaseFilter {
    
    private final Log log = LogFactory.getLog(ForceBufferSizeFilter.class);

    @Override
    protected void filterAction(HttpServletRequest request,
            HttpServletResponse response, FilterChain chain) throws IOException,
            ServletException {
        // default 100M buffer: designed to allow jsp errors to cause proper 500s if they happen after the normal 8kb flush
        final int forcedSize = Integer.valueOf(getInitParameter("bufferSize", "" + 100*1024*1024));
        final String filtername = getFilterName();
        
        response.setBufferSize(forcedSize); 
        response = new HttpServletResponseWrapper(response) {
            @Override
            public void setBufferSize(int size) {
                log.info("Suppressing setBufferSize(" + size + "), forcing " + forcedSize + 
                        " bytes buffer size instead (set in filter " + filtername + ")");
            }
        };
        chain.doFilter(request, response);
    }

}
