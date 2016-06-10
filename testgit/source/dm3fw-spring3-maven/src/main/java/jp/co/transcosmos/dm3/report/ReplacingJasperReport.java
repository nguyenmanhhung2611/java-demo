/*
 * &copy; Trans-cosmos Inc. 2006, 2007.
 * All rights reserved.
 */
package jp.co.transcosmos.dm3.report;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.sql.DataSource;

import jp.co.transcosmos.dm3.utils.ReflectionUtils;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.ServletContextAware;


/**
 * Holds the variables that need replacing in a report.
 * <p> 
 * NOTE: This class should ideally be mounted as a "prototype"
 * scope object in spring, because each use should create a new template to 
 * be populated from the request.
 * <p>
 * The jasper template to use is defined in the reportTemplatePath property.
 * 
 * @author <a href="mailto:rick@knowleses.org">Rick Knowles</a>
 * @version $Id: ReplacingJasperReport.java,v 1.2 2007/05/31 08:13:44 rick Exp $
 */
public class ReplacingJasperReport implements ServletContextAware {
    private static final Log log = LogFactory.getLog(ReplacingJasperReport.class);

    private ServletContext context; //used to specify webroot-relative paths in spring
    private String reportTemplatePath;
    private String listDataField;
    private DataSource datasource;
    private Map<String,Object> parameters = new HashMap<String,Object>();
    private Map<String,Object> metadataParameters = new HashMap<String,Object>();

    public void setServletContext(ServletContext context) {
        this.context = context;
    }
    
    public void setReportTemplatePath(String pReportTemplatePath) {
        this.reportTemplatePath = pReportTemplatePath;
    }

    public void setDatasource(DataSource pDatasource) {
        this.datasource = pDatasource;
    }

    public void setParameter(String key, Object value) {
        this.parameters.put(key, value);
    }

    public void setMetadataParameter(String key, Object value) {
        this.metadataParameters.put(key, value);
    }
    
    public void setListDataField(String listDataField) {
        this.listDataField = listDataField;
    }

    public void writeOutPDFReport(OutputStream out) {
        try {
            // Get the compiled report
            JasperReport jasperReport = compileReport();
            
            // Evaluate the report's contents
            log.info("Evaluating report template: " + this.reportTemplatePath);
            JasperPrint jasperPrint = evaluateReport(jasperReport);
            
            log.info("Exporting report to PDF: " + this.reportTemplatePath);
            JRPdfExporter exporter = new JRPdfExporter();
            for (Iterator<String> i = this.metadataParameters.keySet().iterator(); i.hasNext(); ) {
                String key = i.next();
                exporter.setParameter(
                        ReflectionUtils.getStaticAttribute(key, 
                                JRPdfExporterParameter.class,
                                JRPdfExporterParameter.class), 
                        this.metadataParameters.get(key));
            }
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, out);
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            exporter.exportReport();
        } catch (JRException err) {
            throw new RuntimeException("Error in Jasper Report PDF conversion: " +
                    this.reportTemplatePath, err);  
        }
    }
    
    protected JasperReport compileReport() {
        InputStream definitionStream = null;
        InputStream compiledInStream = null;
        OutputStream compiledOutStream = null;
        ObjectInputStream objectIn = null;
        ObjectOutputStream objectOut = null;
        try {
            File definitionFile = null;
            if (this.context != null) {
                definitionFile = new File(this.context.getRealPath("/"), 
                        this.reportTemplatePath);
            } else {
                definitionFile = new File(this.reportTemplatePath);
            }
            
            // Open files 
            File compiledFile = new File(definitionFile.getParentFile(), 
                    definitionFile.getName() + ".compiled");
            
            if (compiledFile.exists() && compiledFile.isFile() && 
                    (compiledFile.lastModified() >= definitionFile.lastModified())) {
                // If the compiled definition exists and is newer than the source, load it and return it
                compiledInStream = new FileInputStream(compiledFile);
                objectIn = new ObjectInputStream(compiledInStream);
                return (JasperReport) objectIn.readObject();
            } else if (definitionFile.exists() && definitionFile.isFile()) {
                // If the compiled definition doesn't exist, compile it and write
                definitionStream = new FileInputStream(definitionFile);
                JasperReport report = JasperCompileManager.compileReport(definitionStream);
                compiledOutStream = new FileOutputStream(compiledFile);
                objectOut = new ObjectOutputStream(compiledOutStream);
                objectOut.writeObject(report);
                objectOut.flush();
                return report;
            } else {
                throw new FileNotFoundException("Couldn't find report definition file: " + 
                        definitionFile);
            }
        } catch (JRException err) {
            throw new RuntimeException("Error in Jasper Report compilation/loading: " +
                    this.reportTemplatePath, err);  
        } catch (IOException err) {
            throw new RuntimeException("Report I/O error in Jasper Report compilation/loading: " +
                    this.reportTemplatePath, err);
        } catch (ClassNotFoundException err) {
            throw new RuntimeException("Report I/O error in Jasper Report compilation/loading: " +
                    this.reportTemplatePath, err);
        } finally {
            if (objectIn != null) {
                try {objectIn.close();} catch (IOException err) {}
            }
            if (objectOut != null) {
                try {objectOut.close();} catch (IOException err) {}
            }
            if (definitionStream != null) {
                try {definitionStream.close();} catch (IOException err) {}
            }
            if (compiledInStream != null) {
                try {compiledInStream.close();} catch (IOException err) {}
            }
            if (compiledOutStream != null) {
                try {compiledOutStream.close();} catch (IOException err) {}
            }
        }
    }
    
    protected JasperPrint evaluateReport(JasperReport jasperReport) {
        Connection connection = null;
        try {
            if (this.datasource != null) {
                connection = this.datasource.getConnection();
                return JasperFillManager.fillReport(
                        jasperReport, this.parameters, connection);
            } else {
                Object data = null;
                if (this.listDataField != null) {
                    data = this.parameters.get(this.listDataField);
                } else {
                    Object modelKeys[] = this.parameters.keySet().toArray();
                    Arrays.sort(modelKeys);
                    for (int n = 0; (n < modelKeys.length) && (data == null); n++) {
                        Object candidate = this.parameters.get(modelKeys[n]);
                        if (candidate instanceof Object[]) {
                            data = candidate;
                        } else if (candidate instanceof Collection) {
                            data = candidate;
                        } else if (candidate instanceof JRDataSource) {
                            data = candidate;
                        }
                    }
                }
                if (data == null) {
                    return JasperFillManager.fillReport(
                            jasperReport, this.parameters, new JREmptyDataSource());
                } else if (data instanceof JRDataSource) {
                    return JasperFillManager.fillReport(
                            jasperReport, this.parameters, (JRDataSource) data);
                } else if (data instanceof Collection) {
                    return JasperFillManager.fillReport(
                            jasperReport, this.parameters, new JRBeanCollectionDataSource((Collection<?>) data));
                } else if (data instanceof Object[]) {
                    return JasperFillManager.fillReport(
                            jasperReport, this.parameters, new JRBeanArrayDataSource((Object[]) data));
                } else {
                    log.warn("Data source not found for (listDataField=" + this.listDataField + ") using empty DS for report");
                    return JasperFillManager.fillReport(
                            jasperReport, this.parameters, new JREmptyDataSource());
                }
            }      
        } catch (SQLException err) {
            throw new RuntimeException("DBConnection Error in Jasper Report evaluation: " +
                    this.reportTemplatePath, err);
        } catch (JRException err) {
            throw new RuntimeException("Error in Jasper Report evaluation: " +
                    this.reportTemplatePath, err);  
        } finally {
            if (connection != null) {
                try {connection.close();} catch (SQLException err) {}
            }
        }        
    }
}
