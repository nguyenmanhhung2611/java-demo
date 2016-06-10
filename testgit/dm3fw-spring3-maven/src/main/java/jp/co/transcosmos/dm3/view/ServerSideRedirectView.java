/*
 * &copy; Trans-cosmos Inc. 2006, 2007.
 * All rights reserved.
 */
package jp.co.transcosmos.dm3.view;

import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.utils.SimpleExpressionLanguage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.View;

/**
 * Provides a string replacing client side URL redirection view. This is used
 * in favour of the Spring internal one because it tries to append model and view
 * info to the query string even when we don't want it to.
 * <p>
 * Used when the url-mapping view definition is prefixed with "forward:". The URL also 
 * supports the Simple Expression Language, so URLs may contain wildcards from the model.
 * 
 * @author <a href="mailto:rick@knowleses.org">Rick Knowles</a>
 * @version $Id: ServerSideRedirectView.java,v 1.2 2007/05/31 03:46:47 rick Exp $
 */
public class ServerSideRedirectView implements View, ServletContextAware {
    private static final Log log = LogFactory.getLog(ServerSideRedirectView.class);

    private String uri;
    private ServletContext context;
    
    public ServerSideRedirectView(String uri) {
        this.uri = uri;
    }
    
    public ServerSideRedirectView(String uri, ServletContext context) {
        this(uri);
        this.context = context;
    }

    public ServletContext getServletContext() {
        return this.context;
    }
    public void setServletContext(ServletContext pContext) {
        this.context = pContext;
    }
    
    /**
     * Irrelevant because we send a redirect.
     */
    public String getContentType() {
        return null;
    }

    @SuppressWarnings("unchecked")
    public void render(Map model, HttpServletRequest request, 
            HttpServletResponse response) throws Exception {
        if (this.uri == null) {
            throw new RuntimeException("No url configured");
        }
        
        if (response.getCharacterEncoding() == null) {
            response.setCharacterEncoding(request.getCharacterEncoding());
        }

        String url = SimpleExpressionLanguage.evaluateWebExpressionPatternString(
                this.uri, request, response, getServletContext(), model, 
                true, response.getCharacterEncoding(), "");
        
        log.info("Server side redirecting to: " + url);
        RequestDispatcher dispatcher = request.getRequestDispatcher(url);
        if (dispatcher != null) {
            dispatcher.forward(request, response);
        } else {
            throw new RuntimeException("Request dispatcher was null: " + url);
        }
    }
}
