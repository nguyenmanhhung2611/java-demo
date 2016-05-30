/*
 * &copy; Trans-cosmos Inc. 2006, 2007.
 * All rights reserved.
 */
package jp.co.transcosmos.dm3.transaction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * Triggers rollback of the request-scoped connection if there was any kind of exception.
 * <p>
 * This interceptor is an important part of the management of request-scoped connections.
 * It should be mounted in the spring-servlet.xml file as follows:
 * <p><code>
 * &lt;bean id="urlMapping" class="org.springframework.web.servlet.handler.SimpleUrlHandlerMapping"&gt;<br/>
 * &#0160;&#0160;&lt;property name="mappings"&gt;<br/>
 * &#0160;&#0160;&#0160;&#0160;&lt;props&gt;&lt;prop key="/*"&gt;commandDelegatingController&lt;/prop&gt;&lt;/props&gt;<br/>
 * &#0160;&#0160;&lt;/property&gt;<br/>
 * &#0160;&#0160;&lt;property name="interceptors" ref="rollbackOnExceptionInterceptor"/&gt;<br/>
 * &lt;/bean&gt;  <br/>
 * &lt;bean id="rollbackOnExceptionInterceptor" class="jp.co.transcosmos.dm3.transaction.RollbackOnExceptionInterceptor"/&gt;<br/>
 * </code>
 * <p>
 * which ensures that this interceptor is called to test for errors in each execution, and
 * mark the request so the transaction is rolled back at the end instead of committed.
 * 
 * @author <a href="mailto:rick@knowleses.org">Rick Knowles</a>
 * @version $Id: RollbackOnExceptionInterceptor.java,v 1.2 2007/05/31 06:30:53 rick Exp $
 */
public class RollbackOnExceptionInterceptor extends HandlerInterceptorAdapter {
    
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, 
            Object handler, Exception error) throws Exception {
        if (error != null) {
            request.setAttribute(RequestScopeDataSource.ERROR_CAUGHT, Boolean.TRUE);
        }
        super.afterCompletion(request, response, handler, error);
    }
}
