/*
 * &copy; Trans-cosmos Inc. 2006, 2007.
 * All rights reserved.
 */
package jp.co.transcosmos.dm3.view;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.view.AbstractView;

/**
 * @deprecated Please use the SpreadsheetModel and CSVView classes together instead
 */
public abstract class AbstractCSVView extends AbstractView {
    private static final Log log = LogFactory.getLog(AbstractCSVView.class);
    
    protected abstract Object[] getRows(Map<?,?> model, HttpServletRequest request,
            HttpServletResponse response) throws Exception;

    protected abstract String[] getHeaders(Object rows[]);
    
    protected abstract String[] getOneRow(Object rows[], int index);
    
    protected void renderMergedOutputModel(Map model, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
            
        Object rows[] = getRows(model, request, response);
        if (rows == null) {
            throw new RuntimeException("No data element found in the model");
        }
        String encoding = getEncoding(request, response);
        log.info("Using encoding: " + encoding);
        
        response.setCharacterEncoding(encoding);
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition","attachment; filename=" + getFilename());
        
        StringWriter writer = new StringWriter();
        String row[] = getHeaders(rows);
        if (row != null) {
            writeLine(writer, row);
        }
        for (int n = 0; n < rows.length; n++) {
            row = getOneRow(rows, n);
            if (row != null) {
                writeLine(writer, row);
            }
        }
        byte out[] = writer.toString().getBytes(encoding);
        
        response.setBufferSize(out.length);
        response.getOutputStream().write(out);
        response.getOutputStream().flush();
    }

    protected void writeLine(Writer writer, String fields[]) throws IOException {
        if (fields != null) {
            for (int n = 0; n < fields.length; n++) {
                if (n > 0) { 
                    writer.append(',');
                }
                writer.append('"').append(fields[n]).append('"');
            }
            writer.write("\r\n");
        }
    }
    
    protected String getFilename() {
        return "download.csv";
    }
    protected String getEncoding(HttpServletRequest request, HttpServletResponse response) {
        String encoding = response.getCharacterEncoding();
        if (encoding == null) {
            encoding = request.getCharacterEncoding();
            log.info("Getting response encoding from the request: " + encoding);
        }
        if (encoding == null) {
            encoding = "Windows-31J";
            log.info("Using default response encoding: " + encoding);
        }
        return encoding;
    }
}
