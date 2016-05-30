/*
 * &copy; Trans-cosmos Inc. 2006, 2007.
 * All rights reserved.
 */
package jp.co.transcosmos.dm3.report;

import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;

import org.eclipse.birt.report.engine.api.EngineConstants;
import org.eclipse.birt.report.engine.api.EngineException;
import org.eclipse.birt.report.engine.api.IReportEngine;
import org.eclipse.birt.report.engine.api.IReportRunnable;
import org.eclipse.birt.report.engine.api.IRunAndRenderTask;
import org.eclipse.birt.report.engine.api.PDFRenderContext;
import org.eclipse.birt.report.engine.api.RenderOptionBase;
import org.springframework.web.context.ServletContextAware;


/**
 * Holds the variables that need replacing in a BIRT report.
 * <p> 
 * NOTE: This class should ideally be mounted as a "prototype"
 * scope object in spring, because each use should create a new template to 
 * be populated from the request.
 * <p>
 * It should also be connected to a shared BIRTReportEngine instance using the 
 * "engine" property, so that caching can be enabled.
 * <p>
 * The birt template to use is defined in the reportTemplatePath property.
 * 
 * @author <a href="mailto:rick@knowleses.org">Rick Knowles</a>
 * @version $Id: ReplacingBIRTReport.java,v 1.3 2007/05/31 08:13:44 rick Exp $
 */
public class ReplacingBIRTReport implements ServletContextAware {

    private BIRTReportEngine engine;
    private ServletContext servletContext;
    
    private String reportTemplatePath;
//    private DataSource datasource; // add this later - tricky
    private Map<String,Object> parameters = new HashMap<String,Object>();
    private Map<String,Object> metadataParameters = new HashMap<String,Object>();

    public void setEngine(BIRTReportEngine engine) {
        this.engine = engine;
    }
    
    public void setReportTemplatePath(String pReportTemplatePath) {
        this.reportTemplatePath = pReportTemplatePath;
    }

//    public void setDatasource(DataSource pDatasource) {
//        this.datasource = pDatasource;
//    }

    public void setParameter(String key, Object value) {
        this.parameters.put(key, value);
    }

    public void setMetadataParameter(String key, Object value) {
        this.metadataParameters.put(key, value);
    }
    
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    public void writeOutPDFReport(OutputStream out) {
        try {
            IReportEngine birtEngine = this.engine.getEngine();
            
            // Open a report design - use design to modify design, retrieve embedded images etc.
            IReportRunnable design = birtEngine.openReportDesign(
                    this.servletContext == null ? this.reportTemplatePath :
                        this.servletContext.getRealPath("/") + this.reportTemplatePath); 
            
            // Create task to run the report and render the report
            IRunAndRenderTask task = birtEngine.createRunAndRenderTask(design); 
                
            // Set Render context to handle url and image locataions
            PDFRenderContext renderContext = new PDFRenderContext();
            Map<String,PDFRenderContext> contextMap = new HashMap<String,PDFRenderContext>();
            contextMap.put(EngineConstants.APPCONTEXT_PDF_RENDER_CONTEXT, renderContext);
            task.setAppContext(contextMap);
                
//          Set rendering options - such as file or stream output, 
//          output format, whether it is embeddable, etc
            RenderOptionBase options = new RenderOptionBase();
            options.setOutputStream(out);
            options.setOutputFormat("pdf");
            task.setRenderOption(options);
            
            task.setParameterValues(this.parameters);
            task.run();

        } catch (EngineException err) {
            throw new RuntimeException("Error in BIRT Report PDF conversion: " +
                    this.reportTemplatePath, err);  
        }
    }
}
