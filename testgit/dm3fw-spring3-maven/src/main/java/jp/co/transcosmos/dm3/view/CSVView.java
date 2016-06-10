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

import jp.co.transcosmos.dm3.csv.SimpleCSVModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.view.AbstractView;

/**
 * Writes out the contents of the Model map as a CSV file. The SpreadsheetModel implementation
 * class provides the instructions for how to map the Model contents into a table, and this class
 * takes the table and writes it out in CSV format.
 * <p>
 * This can be used directly (through the new ModelAndView(view) call) or by using the "csv:"
 * prefix in the url-mapping files when declaring a render target.
 * <p>
 * Use in combination with the SpreadsheetModel class to generate custom content CSV files.
 * 
 * @author <a href="mailto:rick@knowleses.org">Rick Knowles</a>
 * @version $Id: CSVView.java,v 1.4 2012/08/01 09:28:36 tanaka Exp $
 */
public class CSVView extends AbstractView {
    private static final Log log = LogFactory.getLog(CSVView.class);
    
    private SpreadsheetModel layout;
    private String delimiter;
    private String stringDelimiter;
    private String fileExtension;

    public CSVView() {
        this(null);
    }
    
    public CSVView(SpreadsheetModel layout) {
        this(layout, ",", "\"", ".csv");
    }
    
    public CSVView(SpreadsheetModel layout, String delimiter, String stringDelimiter, String fileExtension) {
        this.layout = layout;
        if (this.layout == null) {
            this.layout = new SimpleSpreadsheetModel();
        }
        this.delimiter = delimiter;
        this.stringDelimiter = stringDelimiter;
        this.fileExtension = fileExtension;
    }
    
    protected void renderMergedOutputModel(Map model, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
            
        Object rows[] = this.layout.getRows(model, request, response);
        if (rows == null) {
            throw new RuntimeException("No data element found in the model");
        }

        log.info("Generating CSV file with " + rows.length + " data rows");

        
// 2013.12.20 H.Mizuno CSV 出力時の文字コードを設定可能に変更 start        
//        String encoding = getEncoding(request, response);

        // レイアウト情報から文字コードを取得する。
        // 値が未設定だった場合、従来の方法でエンコード文字列を取得する。
        String encoding = layout.getEncoding();
        if (encoding == null || encoding.length() == 0) {
        	encoding = getEncoding(request, response);
        }
// 2013.12.20 H.Mizuno CSV 出力時の文字コードを設定可能に変更 end        
        log.info("Using encoding: " + encoding);

        response.setCharacterEncoding(encoding);
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition","attachment; filename=" + 
                getFilename(model, request, response) + this.fileExtension);
        
        StringWriter writer = new StringWriter();

        Object row[] = this.layout.getHeaders(rows, model, request, response);
        if (row != null) {
            writeLine(writer, row, delimiter, stringDelimiter);
        }
        for (int n = 0; n < rows.length; n++) {
            row =  this.layout.getOneRow(rows, n, model, request, response);
            if (row != null) {
                writeLine(writer, row, delimiter, stringDelimiter);
            }
        }
        byte out[] = writer.toString().getBytes(encoding);

// 2015.05.19 H.Mizuno CSV 出力時の BOM 対応 start
//        response.setContentLength(out.length);
//        response.setBufferSize(out.length);
    	byte[] bom = null;
        if (this.layout instanceof SimpleCSVModel){
        	bom = ((SimpleCSVModel)this.layout).getBOM(encoding);
        }

    	if (bom != null) {
        	response.setContentLength(out.length + bom.length);
        	response.setBufferSize(out.length + bom.length);
    		response.getOutputStream().write(bom);
    	} else {
        	response.setContentLength(out.length);
        	response.setBufferSize(out.length);
    	}
// 2015.05.19 H.Mizuno CSV 出力時の BOM 対応 end

        response.getOutputStream().write(out);
        response.getOutputStream().flush();
    }

    protected void writeLine(Writer writer, Object fields[], String delimiter, String stringDelimiter) throws IOException {
        if (fields != null) {
            for (int n = 0; n < fields.length; n++) {
                if ((n > 0) && (delimiter != null)) { 
                    writer.append(delimiter);
                }
                if (stringDelimiter != null) {
                    writer.append(stringDelimiter);
                }
                if (fields[n] != null) {
                    writer.append(fields[n].toString());
                }
                if (stringDelimiter != null) {
                    writer.append(stringDelimiter);
                }
            }
            writer.write("\r\n");
        }
    }
    
    protected String getFilename(Map<?,?> model, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        String filename = this.layout.getFilename(model, request, response);
        if (filename == null) {
// 2013.12.20 H.Mizuno ダウンロード時のデフォルトファイル名が download.csv.csv になる問題を修正 start
//        	filename = "download.csv";
        	filename = "download";
// 2013.12.20 H.Mizuno ダウンロード時のデフォルトファイル名が download.csv.csv になる問題を修正 end
        }
        return filename;
    }
    
    protected String getEncoding(HttpServletRequest request, HttpServletResponse response) {
		// CSVView は、特に指定が無ければ HttpServletResponse の文字コードを使用する。
		// そこからも取得出来ない場合、Windows-31J　を使用している。
		// この流れは既存システムの互換性の問題から変更する事はできない。

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
