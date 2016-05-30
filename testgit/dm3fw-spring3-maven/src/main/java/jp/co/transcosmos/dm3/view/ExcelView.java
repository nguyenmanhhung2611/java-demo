/*
 * &copy; Trans-cosmos Inc. 2006, 2007.
 * All rights reserved.
 */
package jp.co.transcosmos.dm3.view;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.springframework.web.servlet.view.AbstractView;

/**
 * Similar to the CSVView, but this file will output an Excel formatted response.
 * The style of the Excel sheet can be overridden using the getHeaderRowCellStyle()
 * and getHeaderRowCellStyle() methods.
 * <p>
 * This view is used when the url-mapping view is prefixed with "excel:"
 * <p>
 * Use this class in combination with the SpreadsheetModel class to create custom
 * excel contents.
 * 
 * @author <a href="mailto:rick@knowleses.org">Rick Knowles</a>
 * @version $Id: ExcelView.java,v 1.3 2012/08/01 09:28:36 tanaka Exp $
 */
public class ExcelView extends AbstractView {
    private static final Log log = LogFactory.getLog(ExcelView.class);
    
    private SpreadsheetModel layout;

    public ExcelView() {
        this(new SimpleSpreadsheetModel());
    }
    
    public ExcelView(SpreadsheetModel layout) {
        this.layout = layout;
    }
    
    protected void renderMergedOutputModel(Map model, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
            
        Object allRows[] = this.layout.getRows(model, request, response);
        if (allRows == null) {
            throw new RuntimeException("No data element found in the model");
        }
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition","attachment; filename=" + 
                getFilename(model, request, response) + ".xls");

        log.info("Generating Excel workbook with " + allRows.length + " data rows");

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        writeWorkbook(allRows, model, request, response, out);
        
        response.setContentLength(out.size());
        response.setBufferSize(out.size());
        out.writeTo(response.getOutputStream());
        response.getOutputStream().flush();
    }

    protected void writeWorkbook(Object allRows[], Map<?,?> model, HttpServletRequest request,
            HttpServletResponse response, OutputStream out) throws Exception {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet();
        workbook.setSheetName(0, this.getFilename(model, request, response));
        
        // Write the headers
        Map<String,HSSFCellStyle> styleCache = new HashMap<String,HSSFCellStyle>();
        Object labels[] = this.layout.getHeaders(allRows, model, request, response);
        if (labels != null) {
            HSSFRow hssfRow = sheet.createRow(0);
            writeLine(workbook, hssfRow, labels, styleCache, true, 0);
            for (short n = 0; n < labels.length; n++) {
                sheet.setColumnWidth(n, (short) (labels[n].toString().length() * 500));
            }
        }
        for (int n = 0; n < allRows.length; n++) {
            Object row[] =  this.layout.getOneRow(allRows, n, model, request, response);
            if (row != null) {
                HSSFRow hssfRow = sheet.createRow(n + 1);
                writeLine(workbook, hssfRow, row, styleCache, false, n);
            }
        }
        workbook.write(out);
    }
    
    protected void writeLine(HSSFWorkbook workbook, HSSFRow row, Object fields[], 
            Map<String,HSSFCellStyle> styleCache, boolean isHeader, int index) throws Exception {
        if (fields != null) {
            DateFormat sdf = null;
            for (short n = 0; n < fields.length; n++) {
                if (fields[n] != null) {
                    HSSFCell cell = row.createCell(n);
                    cell.setCellStyle(isHeader ? 
                            this.getHeaderRowCellStyle(workbook, fields[n], styleCache) : 
                            this.getDataRowCellStyle(workbook, index, fields[n], styleCache));
                    if (fields[n] instanceof Date) {
                        if (sdf == null) {
                            sdf = new SimpleDateFormat(DATE_FORMAT);
                        }
                        cell.setCellValue(sdf.format((Date) fields[n]));
                    } else if (fields[n] instanceof Number) {
                        cell.setCellValue(((Number) fields[n]).doubleValue());
                    } else if (fields[n] instanceof Boolean) {
                        cell.setCellValue(((Boolean) fields[n]).booleanValue());
                    } else {
                        cell.setCellValue(fields[n].toString());
                    }
                }
            }
        }
    }
    
    protected String getFilename(Map<?,?> model, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        String filename = this.layout.getFilename(model, request, response);
        if (filename == null) {
            filename = "download";
        }
        return filename;
    }
    
    /**
     * Override this method to return a custom header style
     */
    protected HSSFCellStyle getHeaderRowCellStyle(HSSFWorkbook workbook, 
            Object label, Map<String,HSSFCellStyle> styleCache) throws Exception {
        HSSFCellStyle cs = styleCache.get("header");
        if (cs == null) {
            cs = workbook.createCellStyle();
            cs.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
            cs.setFillForegroundColor(HSSFColor.BLUE_GREY.index);
            HSSFFont font = workbook.createFont();
            font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
            font.setColor(HSSFColor.WHITE.index);
            cs.setFont(font);
            cs.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            styleCache.put("header", cs);
        }
        return cs;
    }

    
    /**
     * Override this method to return a custom data row style
     */
    protected HSSFCellStyle getDataRowCellStyle(HSSFWorkbook workbook, 
            int index, Object field, Map<String,HSSFCellStyle> styleCache) throws Exception {
        String key = "row" + (index % 2) + field.getClass().getName();
        HSSFCellStyle cs = styleCache.get(key);
        if (cs == null) {
            cs = workbook.createCellStyle();
            cs.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
            cs.setFillForegroundColor(
                    (index % 2 == 1 
                            ? HSSFColor.GREY_25_PERCENT.index 
                            : HSSFColor.WHITE.index));
            if (field instanceof Date) {
                cs.setDataFormat(HSSFDataFormat.getBuiltinFormat(DATE_FORMAT));
                cs.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
            }
            styleCache.put(key, cs);
        }
        return cs;
    }
    
    private static final String DATE_FORMAT = "yyyy/MM/dd HH:mm:ss";
}
