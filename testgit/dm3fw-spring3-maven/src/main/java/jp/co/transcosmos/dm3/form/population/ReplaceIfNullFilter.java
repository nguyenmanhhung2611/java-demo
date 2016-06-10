/*
 * Copyright (C) 2006 Transcosmos INC.
 * All Rights Reserved.
 */
package jp.co.transcosmos.dm3.form.population;

import jp.co.transcosmos.dm3.form.FormPopulationFilter;

import org.apache.commons.fileupload.FileItem;

/**
 * If a form field is null, replace it with the defined values. This is useful for 
 * checkboxes, where it's handy to insert "false" or "unselected" if a checkbox field
 * is not supplied in the request (indicating it was unselected in the browser).
 * 
 * @author <a href="mailto:rick@knowleses.org">Rick Knowles</a>
 * @version $Id: ReplaceIfNullFilter.java,v 1.2 2007/05/31 10:18:25 rick Exp $
 */
public class ReplaceIfNullFilter implements FormPopulationFilter {

    private String replaceValues[];
    private FileItem replaceFile;
    
    public ReplaceIfNullFilter(String replaceValue) {
        this.replaceValues = new String[] {replaceValue};
    }
    public ReplaceIfNullFilter(String replaceValues[]) {
        this.replaceValues = replaceValues;
    }
    public ReplaceIfNullFilter(FileItem replaceFile) {
        this.replaceFile = replaceFile;
    }
    public ReplaceIfNullFilter(String replaceValues[], FileItem replaceFile) {
        this.replaceValues = replaceValues;
        this.replaceFile = replaceFile;
    }
    
    public String[] filterString(String pInput[]) {
        boolean isNull = true;
        if (pInput != null) {
            for (int n = 0; (n < pInput.length) && isNull; n++) {
                isNull = (pInput[n] == null);
            }
        }
        return isNull ? this.replaceValues : pInput;
    }

    public FileItem filterFileItem(FileItem pInput) {
        return pInput == null ? this.replaceFile : pInput;
    }
}
