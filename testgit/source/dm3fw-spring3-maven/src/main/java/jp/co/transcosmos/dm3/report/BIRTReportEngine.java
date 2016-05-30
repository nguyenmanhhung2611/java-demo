/*
 * &copy; Trans-cosmos Inc. 2006, 2007.
 * All rights reserved.
 */
package jp.co.transcosmos.dm3.report;

import java.io.File;
import java.util.logging.Level;

import javax.servlet.ServletContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.birt.core.framework.Platform;
import org.eclipse.birt.report.engine.api.EngineConfig;
import org.eclipse.birt.report.engine.api.IReportEngine;
import org.eclipse.birt.report.engine.api.IReportEngineFactory;
import org.springframework.web.context.ServletContextAware;


/**
 * The application scope object that maintains a single BIRT ReportEngine instance. This
 * facilitates caching of frequently generated reports etc.
 * <p>
 * This object should be mounted once per application, and referenced from each of the 
 * ReplacingBIRTReport instances mounted.
 * <p>
 * The reportHome setting is used to configure the ReportEngine's home directory 
 * relative to the servlet context root.
 * 
 * @author <a href="mailto:rick@knowleses.org">Rick Knowles</a>
 * @version $Id: BIRTReportEngine.java,v 1.2 2007/05/31 08:13:44 rick Exp $
 */
public class BIRTReportEngine implements ServletContextAware {
    private static final Log log = LogFactory.getLog(BIRTReportEngine.class);
    
    private ServletContext servletContext;
    private String home = "/WEB-INF/report"; 
    private IReportEngine engine;

    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }
    
    public void setReportHome(String home) {
        this.home = home;
    }
    
    public IReportEngine getEngine() {
        synchronized (this) {
            if (this.engine == null) {
                try {
                    EngineConfig config = new EngineConfig();
                    if (this.servletContext != null) {
                        config.setEngineHome(this.servletContext.getRealPath(this.home));
                        config.setLogConfig(((File) this.servletContext.getAttribute(
                                "javax.servlet.context.tempdir")).getAbsolutePath(), Level.INFO);
                    } else {
                        config.setEngineHome(this.home);
                        config.setLogConfig(System.getProperty("java.io.tmpdir"), Level.INFO);
                    }

                    Platform.startup(config);
                    IReportEngineFactory factory = (IReportEngineFactory) Platform
                            .createFactoryObject(IReportEngineFactory
                                    .EXTENSION_REPORT_ENGINE_FACTORY );
                    this.engine = factory.createReportEngine(config);
                    this.engine.changeLogLevel(Level.WARNING);
                } catch (Throwable err) {
                    shutdown();
                    if (err instanceof RuntimeException) {
                        throw (RuntimeException) err;
                    } else if (err instanceof Error){
                        throw (Error) err;
                    } else {
                        throw new RuntimeException("Error initializing BIRT report engine", err);
                    }
                }
            }
            return this.engine;
        }
    }
    
    public void shutdown() {
        try {
            if (this.engine != null) {
                this.engine.shutdown(); 
                this.engine = null;
                Platform.shutdown();   
            }
        } catch (Throwable err) {
            log.error("Error found shutting down report engine", err);
        }
    }
}
