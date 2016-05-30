/*
 * &copy; Trans-cosmos Inc. 2006, 2007.
 * All rights reserved.
 */
package jp.co.transcosmos.dm3.view;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Defines how to get the layout of a spreadsheet so that we can use it to
 * generate a CSV or Excel document view.
 * <p>
 * Implement this method if you wish to create custom Model to Spreadsheet contents
 * mappings.
 * 
 * @author <a href="mailto:rick@knowleses.org">Rick Knowles</a>
 * @version $Id: SpreadsheetModel.java,v 1.2 2007/05/31 03:46:47 rick Exp $
 */
public interface SpreadsheetModel {
    public Object[] getRows(Map<?,?> model, HttpServletRequest request,
            HttpServletResponse response) throws Exception;

    public Object[] getHeaders(Object rows[], Map<?,?> model, 
            HttpServletRequest request, HttpServletResponse response) throws Exception;
    
    public Object[] getOneRow(Object rows[], int index, Map<?,?> model, 
            HttpServletRequest request, HttpServletResponse response) throws Exception;

    public String getFilename(Map<?,?> model, HttpServletRequest request,
            HttpServletResponse response) throws Exception;

// 2013.12.20 H.Mizuno CSV エンコーディング文字列を外部から設定可能に変更 start
    public String getEncoding();
// 2013.12.20 H.Mizuno CSV エンコーディング文字列を外部から設定可能に変更 end
    
}
