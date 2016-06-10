/*
 * &copy; Trans-cosmos Inc. 2006, 2007.
 * All rights reserved.
 */
package jp.co.transcosmos.dm3.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * Servlet for routing requests to the *.do pattern into the Spring managed space, so
 * that Commmand classes can be used.
 * <p>
 * This is a basically a little hack that lets us do our request wrapping before spring sees 
 * the request. We override the multipart check because that's the last step in the spring flow
 * before actually processing the request.
 * <p>
 * This servlet is mounted in the web.xml file, and accesses the Spring managed CommandURLMapper
 * object to find the patterns used to match URLs.
 * 
 * @author <a href="mailto:rick_knowles@hotmail.com">Rick Knowles</a>
 * @version $Id: PatternMatchingDispatcherServlet.java,v 1.2 2007/05/31 07:44:43 rick Exp $
 */
public class PatternMatchingDispatcherServlet extends DispatcherServlet {
    private static final Log log = LogFactory.getLog(PatternMatchingDispatcherServlet.class);
    
	static final long serialVersionUID = 1L;
	
    protected HttpServletRequest checkMultipart(HttpServletRequest request)
            throws MultipartException {
        // Try to do the extra parameter extraction here, and store the command chain we 
        // matched to in the request attributes (this will be removed by the command delegating
        // controller)
        CommandURLMapper mapper = getMapper();
        FilterCommandChain chain = mapper.findCommandAndFilters(request, false);
        if (chain != null) {
            String pairs[][] = chain.getMainParams();
            if ((pairs != null) && (pairs.length > 0)) {
                request = new PatternMatchedRequestWrapper(request, pairs);
            }
            // Attach the chain into the request attributes so we can see it later in the matching flow
            request.setAttribute(FilterCommandChain.REQUEST_ATTRIBUTE_NAME, chain);
        }
        return super.checkMultipart(request);
    }
    
    protected CommandURLMapper getMapper() {
        return (CommandURLMapper) getWebApplicationContext().getBean(getCommandURLMapperName());        
    }
    
    public String getCommandURLMapperName() {
        String mapperName = getServletConfig().getInitParameter("commandURLMapperName");
        return mapperName != null ? mapperName : "commandURLMapper";
    }

    public void service(ServletRequest req, ServletResponse response) 
            throws ServletException, IOException {

        HttpServletRequest request = (HttpServletRequest) req;
        
        log.info("-------------------------------------------------------------------");
        log.info("- Starting request URI: " + request.getRequestURI());
        log.info("-------------------------------------------------------------------");
        long start = System.currentTimeMillis();
        
        super.service(req, response);
        
        response.flushBuffer();
        
        log.info("-------------------------------------------------------------------");
        log.info("- Finished request URI: " + request.getRequestURI() + " in " +
                (System.currentTimeMillis() - start) + "ms");
        log.info("-------------------------------------------------------------------");
    }
}
