/*
 * &copy; Trans-cosmos Inc. 2006, 2007.
 * All rights reserved.
 */
package jp.co.transcosmos.dm3.requestqueue;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.co.transcosmos.dm3.servlet.BaseFilter;

/**
 * Processes incoming requests into a queue (per-session), so that we can
 * limit incoming requests to one concurrent request of each type.
 * 
 * @author <a href="mailto:rick@knowleses.org">Rick Knowles</a>
 * @version $Id: RequestQueueingFilter.java,v 1.2 2007/05/31 07:54:21 rick Exp $
 */
public class RequestQueueingFilter extends BaseFilter {

    private Boolean enableApplicationScopeRequestQueue;
    private String applicationScopeRequestQueueName;
    private Integer applicationScopeQueueRunningSize;
    
    private Boolean enableSessionScopeRequestQueue;
    private String sessionScopeRequestQueueName;
    private Integer sessionScopeQueueRunningSize;

    private Boolean useCachedErrorResponse;
    
    protected void filterAction(HttpServletRequest request, 
            HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        // Don't try to block/sync requests that can't be keyed (eg non-GET/POST, plus multiparts)
        String method = request.getMethod();
        if (!method.equalsIgnoreCase("HEAD") && 
                !method.equalsIgnoreCase("GET") && 
                !method.equalsIgnoreCase("POST")) {
            chain.doFilter(request, response);
            return;
        } else if (method.equalsIgnoreCase("POST") && 
                !request.getContentType().toLowerCase().startsWith(
                        "application/x-www-form-urlencoded")) {
            chain.doFilter(request, response);
            return;
        }
        
        // Try the session queue for blocking first
        HttpSession session = null;
        if (isEnableSessionScopeRequestQueue() && 
                ((session = request.getSession(false)) != null)) {
            
            // Get the session queue (make one if none)
            RequestQueue queue = null;
            synchronized (session) {
                queue = (RequestQueue) session.getAttribute(
                        getSessionScopeRequestQueueName());
                if (queue == null) {
                    queue = new RequestQueue(getSessionScopeQueueRunningSize(), 100, 
                            isUseCachedErrorResponse());
                    session.setAttribute(getSessionScopeRequestQueueName(), queue);
                }
            }
            queue.executeBlockingRequest(request, response, chain);
            return;
        }

        // If no session or session queueing disabled, try blocking on the application scope queue
        if (isEnableApplicationScopeRequestQueue()) {
            // Get the session queue (make one if none)
            ServletContext context = this.getContext();
            RequestQueue queue = null;
            synchronized (context) {
                queue = (RequestQueue) context.getAttribute(
                        getApplicationScopeRequestQueueName());
                if (queue == null) {
                    queue = new RequestQueue(getApplicationScopeQueueRunningSize(), 1000, 
                            isUseCachedErrorResponse());
                    context.setAttribute(getApplicationScopeRequestQueueName(), queue);
                }
            }
            queue.executeBlockingRequest(request, response, chain);
            return;
        }
        
        // If we are here, it is because both the session and application scope queues were 
        // unavailable, so just run the request normally
        chain.doFilter(request, response);
    }
    
    public String getApplicationScopeRequestQueueName() {
        if (this.applicationScopeRequestQueueName != null) {
            return this.applicationScopeRequestQueueName; 
        } else {
            return getInitParameter("applicationScopeRequestQueueName", "requestQueue");
        }
    }

    public String getSessionScopeRequestQueueName() {
        if (this.sessionScopeRequestQueueName != null) {
            return this.sessionScopeRequestQueueName; 
        } else {
            return getInitParameter("sessionScopeRequestQueueName", "requestQueue");
        }
    }

    public int getSessionScopeQueueRunningSize() {
        if (this.sessionScopeQueueRunningSize != null) {
            return this.sessionScopeQueueRunningSize.intValue(); 
        } else {
            return Integer.parseInt(getInitParameter("sessionScopeQueueRunningSize", "1"));
        }
    }

    public boolean isEnableApplicationScopeRequestQueue() {
        if (this.enableApplicationScopeRequestQueue != null) {
            return this.enableApplicationScopeRequestQueue.booleanValue(); 
        } else {
            return Boolean.parseBoolean(getInitParameter("enableApplicationScopeRequestQueue", "true"));
        }
    }

    public boolean isEnableSessionScopeRequestQueue() {
        if (this.enableSessionScopeRequestQueue != null) {
            return this.enableSessionScopeRequestQueue.booleanValue(); 
        } else {
            return Boolean.parseBoolean(getInitParameter("enableSessionScopeRequestQueue", "true"));
        }
    }

    public int getApplicationScopeQueueRunningSize() {
        if (this.applicationScopeQueueRunningSize != null) {
            return this.applicationScopeQueueRunningSize.intValue(); 
        } else {
            return Integer.parseInt(getInitParameter("applicationScopeQueueRunningSize", "1000"));
        }
    }

    public boolean isUseCachedErrorResponse() {
        if (this.useCachedErrorResponse != null) {
            return this.useCachedErrorResponse.booleanValue(); 
        } else {
            return Boolean.parseBoolean(getInitParameter("useCachedErrorResponse", "true"));
        }
    }

    public void setUseCachedErrorResponse(boolean pUseCachedErrorResponse) {
        this.useCachedErrorResponse = new Boolean(pUseCachedErrorResponse);
    }

    public void setApplicationScopeQueueRunningSize(int pApplicationScopeQueueRunningSize) {
        this.applicationScopeQueueRunningSize = new Integer(pApplicationScopeQueueRunningSize);
    }

    public void setEnableApplicationScopeRequestQueue(boolean pEnableApplicationScopeRequestQueue) {
        this.enableApplicationScopeRequestQueue = new Boolean(pEnableApplicationScopeRequestQueue);
    }

    public void setEnableSessionScopeRequestQueue(boolean pEnableSessionScopeRequestQueue) {
        this.enableSessionScopeRequestQueue = new Boolean(pEnableSessionScopeRequestQueue);
    }

    public void setApplicationScopeRequestQueueName(String pApplicationScopeRequestQueueName) {
        this.applicationScopeRequestQueueName = pApplicationScopeRequestQueueName;
    }

    public void setSessionScopeRequestQueueName(String pSessionScopeRequestQueueName) {
        this.sessionScopeRequestQueueName = pSessionScopeRequestQueueName;
    }

    public void setSessionScopeQueueRunningSize(Integer pSessionScopeQueueRunningSize) {
        this.sessionScopeQueueRunningSize = new Integer(pSessionScopeQueueRunningSize);
    }
}
