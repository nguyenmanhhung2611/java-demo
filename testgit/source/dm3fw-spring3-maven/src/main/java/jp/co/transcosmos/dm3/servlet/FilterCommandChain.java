/*
 * &copy; Trans-cosmos Inc. 2006, 2007.
 * All rights reserved.
 */
package jp.co.transcosmos.dm3.servlet;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.token.annotation.UseTokenCheck;
import jp.co.transcosmos.dm3.utils.ReflectionUtils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.web.servlet.ModelAndView;

/**
 * The execution chain for a flow we determined in our url-mapping table. Internal
 * object used by the framework.
 * 
 * @author <a href="mailto:rick_knowles@hotmail.com">Rick Knowles</a>
 * @version $Id: FilterCommandChain.java,v 1.5 2007/05/31 07:44:43 rick Exp $
 */
public class FilterCommandChain extends ModelAndViewRewriteSupport implements FilterChain {
    private static final Log timingLog = LogFactory.getLog(FilterCommandChain.class.getName() + ".TIMING");

    public static final String REQUEST_ATTRIBUTE_NAME = FilterCommandChain.class.getName() + "_ATTR";
    
    private String returnResultAttribute;
    private Filter filters[];
    private URLCommandViewMapping filterMappings[];
    private int currentFilterPosition;
    
    private Command mainCommand; 
    private URLCommandViewMapping mainMapping;
    private String mainParams[][];
    private boolean executedMainCommand = false;
    private long lastFilterExecutionStartedTimestamp = 0;
    private String requestLogMask;
    
    public FilterCommandChain(Filter filters[], URLCommandViewMapping filterMappings[],
            Command mainCommand, URLCommandViewMapping mainMapping, String mainParams[][], 
            String returnResultAttribute, BeanFactory beanFactory, ServletContext servletContext) {
        this.filters = filters;
        this.filterMappings = filterMappings;
        this.currentFilterPosition = 0;
        
        this.mainCommand = mainCommand;
        this.mainMapping = mainMapping;
        
        this.mainParams = mainParams;
        this.returnResultAttribute = returnResultAttribute;
        this.executedMainCommand = false;
        setBeanFactory(beanFactory);
        setServletContext(servletContext);
    }
    
    public String[][] getMainParams() {
        return mainParams;
    }

    public void doFilter(ServletRequest request, ServletResponse response) 
            throws ServletException, IOException {
        // Process the assigned filters until we receive a non-null response
        if ((this.filters != null) && (this.currentFilterPosition < this.filters.length)) {
            int savedPosition = this.currentFilterPosition;
            this.currentFilterPosition++;

            // Log the timing on the last filter execution
            long last = this.lastFilterExecutionStartedTimestamp;
            this.lastFilterExecutionStartedTimestamp = System.currentTimeMillis();
            if (last != 0L) {
                long filterTime = this.lastFilterExecutionStartedTimestamp - last;
                timingLog(request, this.filters[savedPosition - 1], filterTime, 
                        "Filter[" + (savedPosition - 1) + "]-Pre");
            }
            
            this.filters[savedPosition].doFilter(request, response, this);
            
            if (!(this.filters[savedPosition] instanceof CommandAdapterFilter)) {
                last = this.lastFilterExecutionStartedTimestamp;
                this.lastFilterExecutionStartedTimestamp = System.currentTimeMillis();
                if (last != 0L) {
                    long filterTime = this.lastFilterExecutionStartedTimestamp - last;
                    timingLog(request, this.filters[savedPosition], filterTime, 
                            "Filter[" + savedPosition + "]-Post");
                }
                
                // If we exited here, resolve the view
                if (!this.executedMainCommand && 
                        (savedPosition + 1 == this.currentFilterPosition)) {
                    ModelAndView mav = getResult(request);
                    mav = resolveView((HttpServletRequest) request, (HttpServletResponse) response, 
                            mav, this.filterMappings[savedPosition]);
                    replaceViewIfPrefixed(mav);
                    setResult(request, mav);
                }
            } else {
                last = this.lastFilterExecutionStartedTimestamp;
                this.lastFilterExecutionStartedTimestamp = System.currentTimeMillis();
                if (last != 0L) {
                    long filterTime = this.lastFilterExecutionStartedTimestamp - last;
                    timingLog(request, this.filters[savedPosition], filterTime, 
                            "Filter[" + savedPosition + "]-Post");
                }
            }
        } else if (!this.executedMainCommand) {
            this.executedMainCommand = true;
            ModelAndView mav = null;
            
            // Log the timing on the last filter execution
            long last = this.lastFilterExecutionStartedTimestamp;
            this.lastFilterExecutionStartedTimestamp = System.currentTimeMillis();
            if (last != 0L) {
                long filterTime = this.lastFilterExecutionStartedTimestamp - last;
                timingLog(request, this.filters[this.filters.length - 1], filterTime, 
                        "Filter[" + (this.filters.length - 1) + "]-Pre");
            }

            long startTime = System.currentTimeMillis();
            try {
                if (this.mainCommand != null) {
                    mav = this.mainCommand.handleRequest((HttpServletRequest) request, 
                            (HttpServletResponse) response);
                }
                
                mav = resolveView((HttpServletRequest) request, (HttpServletResponse) response, 
                        mav, this.mainMapping);
                replaceViewIfPrefixed(mav);
                setResult(request, mav);
            } catch (RuntimeException err) {
                throw err;
            } catch (Exception err) {
                throw new RuntimeException("Wrapped error - see below", err);
            } finally {
                this.lastFilterExecutionStartedTimestamp = System.currentTimeMillis();
                long filterTime = this.lastFilterExecutionStartedTimestamp - startTime;
                if (this.mainCommand != null) {
                    timingLog(request, this.mainCommand, filterTime, "Main");
                }
            }
        }
    }
    
    public ModelAndView getResult(ServletRequest req) {
        return (ModelAndView) req.getAttribute(this.returnResultAttribute);
    }
    
    public void setResult(ServletRequest req, ModelAndView mav) {
        req.setAttribute(this.returnResultAttribute, mav);
    }
    
    protected void timingLog(ServletRequest request, Object execution, long time, String label) {
        String className = ReflectionUtils.getClassNameWithoutPackage(execution.getClass());
        if (execution instanceof CommandAdapterFilter) {
            Command command = ((CommandAdapterFilter) execution).getNestedCommand();
            while (command instanceof PatternMatchedCommandProxy) {
                command = ((PatternMatchedCommandProxy) command).getNestedCommand();
            }
            className = ReflectionUtils.getClassNameWithoutPackage(command.getClass());
        }
        if (this.requestLogMask == null) {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            String uniqueId = (String) httpRequest.getAttribute("UNIQUE_ID");
            synchronized (sdf) {
                this.requestLogMask = "\"" + httpRequest.getRequestURI() + "\" " + 
                        (uniqueId == null ? "-" : uniqueId) + 
                        " \"" + sdf.format(new Date()) + "\"";
            }
        }
        
        // Log in apache style
        timingLog.info(this.requestLogMask + " " + label + " " + className + " " + time);
    }
    
    private static final DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
    
    // 1013.5.17 H.Mizuno トークンチェック対応 (CSRF 対応） start
    public UseTokenCheck getUseTokenCheckAnnotation(){
// 2013.6.18 H.Mizuno コマンドクラスをバイパスした際の問題を修正 Start
//    	return mainCommand.getClass().getAnnotation(UseTokenCheck.class);
    	if (mainCommand != null) {
        	return mainCommand.getClass().getAnnotation(UseTokenCheck.class);
    	}
    	return null;
// 2013.6.18 H.Mizuno コマンドクラスをバイパスした際の問題を修正 Start
    }
    // 1013.5.17 H.Mizuno トークンチェック対応 (CSRF 対応） end
}
