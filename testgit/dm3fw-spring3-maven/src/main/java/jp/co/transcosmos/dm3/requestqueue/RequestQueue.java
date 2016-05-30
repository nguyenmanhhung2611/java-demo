/*
 * &copy; Trans-cosmos Inc. 2006, 2007.
 * All rights reserved.
 */
package jp.co.transcosmos.dm3.requestqueue;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.utils.EncodingUtils;
import jp.co.transcosmos.dm3.utils.ServletUtils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * A queue that we keep in the session scope to try to limit concurrent requests. This is useful for 
 * reducing the effect of users who repeatedly press a form submit button, getting impatient
 * while waiting for a long running request, and inadvertantly causing additional server load.
 * 
 * Ideally we would keep one instance of this class in each user's session, and optionally one 
 * extra instance in application scope, presumably with a much larger runningRequest size 
 * (so as not to limit the server's concurrent processing ability). 
 * 
 * @author <a href="mailto:rick@knowleses.org">Rick Knowles</a>
 * @version $Id: RequestQueue.java,v 1.2 2007/05/31 07:54:21 rick Exp $
 */
public class RequestQueue {
    private static final Log log = LogFactory.getLog(RequestQueue.class);

    private RequestResponsePair runningRequests[];
    private int runningRequestCount;
    
    private RequestResponsePair queuedRequests[];
    private int queuedRequestCount;
    
    private String headerNames[];
    private boolean useCachedErrorResponse;
    
    public RequestQueue(int runningSetLimit, int waitingSetLimit, boolean useCachedErrorResponse) {
        this(runningSetLimit, waitingSetLimit, useCachedErrorResponse, null);
    }
    
    public RequestQueue(int runningSetLimit, int waitingSetLimit, boolean useCachedErrorResponse, String[] headerNames) {
        if (runningSetLimit < 1) {
            throw new IllegalArgumentException("Running set size must be more than zero");
        } else if (waitingSetLimit < 1) {
            throw new IllegalArgumentException("Waiting set size must be more than zero");
        }
        this.runningRequests = new RequestResponsePair[runningSetLimit];
        this.queuedRequests = new RequestResponsePair[waitingSetLimit];
        
        this.useCachedErrorResponse = useCachedErrorResponse;
        if (headerNames == null) {
            headerNames = new String[0];
        }
        this.headerNames = headerNames;
    }
    
    /**
     * Called by the filter when a blocking request is required. 
     */
    public void executeBlockingRequest(HttpServletRequest request, HttpServletResponse response,
            FilterChain chain) throws ServletException, IOException {
        String key = calculateKey(request, this.headerNames);
        Object waitingFor = null;
        RequestResponsePair waiting = null;
        
        synchronized (this) {
            RequestResponsePair running = find(key, this.runningRequests, 
                    this.runningRequestCount);
            if (running != null) {
                // Found a running request that has the same key. attach to it and 
                // wait for it to finish
                log.info("Found running request with matching key: blocking");
                waiting = new RequestResponsePair(key, request, response, chain);
                waitingFor = running;
            } else {
                RequestResponsePair queued = find(key, this.queuedRequests, 
                        this.queuedRequestCount);
                if (queued != null) {
                    // Found a queued request that has the same key. attach to it and 
                    // wait for it to finish
                    log.info("Found queued request with matching key: blocking");
                    waiting = new RequestResponsePair(key, request, response, chain);
                    waitingFor = queued;
                } else if (this.runningRequestCount < this.runningRequests.length) {
                    log.info("No matching request in queue, execution slot available - running");
                    // Not found in either queue - add and continue without blocking
                    waiting = new RequestResponsePair(key, request, response, chain);
                    this.runningRequests[this.runningRequestCount++] = waiting;
                } else if (this.queuedRequestCount < this.runningRequests.length) {
                    log.info("No matching request in queue, execution slot available - running");
                    waiting = new RequestResponsePair(key, request, response, chain);
                    this.queuedRequests[this.queuedRequestCount++] = waiting;
                    
                    // Waiting until an empty running slot opens. Block on something other 
                    // than the pair (we want a different lock)
                    waitingFor = this.runningRequests;
                } else {
                    log.warn("RequestQueue rejected request because the running and waiting " +
                            "queues were both full: " + request.getRequestURI());
                    response.sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE, 
                            "RequestQueue rejected request because the running and waiting " +
                            "queues were both full: " + request.getRequestURI());
                    return;
                }
            }
        }
        
        if (waitingFor == null) {
            runRequest(waiting);
        } else if (waitingFor == this.runningRequests) {
            // Special case. We queued a request, wait for the running requests to open a slot
            int waitCount = 0;
            while (waitingFor != null) {
                synchronized (this) {
                    // Remove from the waiting set, move to the running
                    if (this.runningRequestCount < this.runningRequests.length) {
                        if (remove(waiting, this.queuedRequests, this.queuedRequestCount)) {
                            this.queuedRequestCount--;
                        }
                        this.runningRequests[this.runningRequestCount++] = waiting;
                        waitingFor = null;
                    }
                }
                log.debug("Waiting for free space in running queue: attempt " + waitCount++);
                try {
                    synchronized (waitingFor) {
                        waitingFor.wait(500);
                    }
                } catch (InterruptedException err) {}
            }
            runRequest(waiting);
        } else {
            // Block on the pair object. 
            try {
                synchronized (waitingFor) {
                    waitingFor.wait();
                }
            } catch (InterruptedException err) {}
            
            // The removeFromRunningSet() call on the actual executed request will 
            // have removed the waitingFor object from the running queue, so we should just
            // copy the results of the saved request into this response and continue, leaving
            // the queue element for GC
            RequestResponsePair result = (RequestResponsePair) waitingFor;
            if (result.saved != null) {
                log.info("Writing out saved request");
                result.saved.dumpIntoResponse(response);
            } else {
                log.info("Blocked on a request that failed - giving up on queue and executing normally");
                chain.doFilter(request, response);
            }
        }
    }
    
    protected void runRequest(RequestResponsePair waiting) 
            throws ServletException, IOException {
        // Build a response saving wrapper
        SavedResponseData saved = new SavedResponseData();
        HttpServletResponse saver = new SavingResponseWrapper(waiting.response, saved);
        
        // run the request, using the saving response wrapper, then unlock 
        boolean hadError = false;
        try {        
            waiting.chain.doFilter(waiting.request, saver);
        } catch (IOException err) {
            hadError = true;
            throw err;
        } catch (ServletException err) {
            hadError = true;
            throw err;
        } catch (RuntimeException err) {
            hadError = true;
            throw err;
        } catch (Error err) {
            hadError = true;
            throw err;
        } finally {
            // Remove from running queue and notify those waiting for execution space
            synchronized (this) {
                if (remove(waiting, this.runningRequests, this.runningRequestCount)) {
                    this.runningRequestCount--;
                }
            }
            synchronized (this.runningRequests) {
                this.runningRequests.notifyAll(); // the running queue has opened a spot
            }
            
            // notify all waiting for this response (so they can copy it)
            if (hadError && !this.useCachedErrorResponse) {
                waiting.saved = null;
            } else {
                waiting.saved = saved;
            }
            synchronized (waiting) {
                waiting.notifyAll();
            }
        }        
    }
    
    private static String calculateKey(HttpServletRequest request, String headerNames[]) {
        // expand this later to include body params and relevant headers
        StringBuilder key = new StringBuilder();
        key.append(request.getRequestURI()).append("_")
           .append(ServletUtils.writeAllParamsAsQueryString(request)).append("_");
        for (int n = 0; n < headerNames.length; n++) {
            key.append(headerNames[n]).append(":_");
            for (Enumeration<?> e = request.getHeaders(headerNames[n]); (e != null) && e.hasMoreElements(); ) {
                key.append(e.nextElement()).append("_").append(e.nextElement()).append("_");
            }
        }        
        return EncodingUtils.md5Encode(key.toString());
    }
    
    private static RequestResponsePair find(String key, 
            RequestResponsePair[] pairs, int pairCount) {
        for (int n = 0; n < pairCount; n++) {
            if (pairs[n].key.equals(key)) {
                return pairs[n];
            }
        }
        return null;
    }
    
    private static boolean remove(RequestResponsePair item, 
            RequestResponsePair[] pairs, int pairCount) {
        for (int n = 0; n < pairCount; n++) {
            if (pairs[n].key.equals(item.key)) {
                for (int k = n; k < pairCount - 1; k++) {
                    pairs[k] = pairs[k + 1];
                }
                return true;
            }
        }
        return false;
    }
    
    /**
     * Local value-object for holding queued items. We build lists of these tuples.
     */
    class RequestResponsePair {
        String key;
        HttpServletRequest request;
        HttpServletResponse response;
        FilterChain chain;
        SavedResponseData saved;
        
        RequestResponsePair(String key, HttpServletRequest request, 
                HttpServletResponse response, FilterChain chain) {
            this.key = key;
            this.request = request;
            this.response = response;
            this.chain = chain;
        }
    }
}
