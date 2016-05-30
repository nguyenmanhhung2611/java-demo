/*
 * Copyright (C) 2006 Transcosmos INC.
 * All Rights Reserved.
 */
package jp.co.transcosmos.dm3.form;

import org.apache.commons.fileupload.FileItem;

/**
 * Processes a form field during population, so we can modify values before they go into
 * the form field.
 * 
 * @author <a href="mailto:rick@knowleses.org">Rick Knowles</a>
 * @version $Id: FormPopulationFilter.java,v 1.4 2007/05/31 10:18:26 rick Exp $
 */
public interface FormPopulationFilter {
    /**
     * Apply the filter to a string field value
     */
    public String[] filterString(String input[]);
    
    /**
     * Apply the filter to an uploaded file
     */
    public FileItem filterFileItem(FileItem input);
}
