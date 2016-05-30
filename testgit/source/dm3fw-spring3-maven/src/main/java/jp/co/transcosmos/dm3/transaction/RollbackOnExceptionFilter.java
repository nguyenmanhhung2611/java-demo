/*
 * &copy; Trans-cosmos Inc. 2006, 2007.
 * All rights reserved.
 */
package jp.co.transcosmos.dm3.transaction;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.servlet.BaseFilter;

/**
 * Commits/rolls back the currently used connection on this request and returns it
 * to the connection pool.
 * <p>
 * NOTE: this filter has been deprecated in favour of the RollbackOnExceptionInterceptor
 * 
 * @deprecated
 */
public class RollbackOnExceptionFilter extends BaseFilter {
    
    protected void filterAction(HttpServletRequest request, 
            HttpServletResponse response, FilterChain chain) 
            throws IOException, ServletException {
        boolean errorCaught = false;
        try {
            chain.doFilter(request, response);
        } catch (ServletException err) {
            errorCaught = true;
            throw err;
        } catch (IOException err) {
            errorCaught = true;
            throw err;
        } catch (RuntimeException err) {
            errorCaught = true;
            throw err;
        } catch (Error err) {
            errorCaught = true;
            throw err;
        } finally {
            RequestScopeDataSource.closeCurrentTransaction(
                    getInitParameter("dataSourceName", 
                            "requestScopeDataSource"), errorCaught);
        }
    }
}
