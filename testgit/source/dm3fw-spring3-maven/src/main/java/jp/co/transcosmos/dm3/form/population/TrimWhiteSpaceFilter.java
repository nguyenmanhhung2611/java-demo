/*
 * Copyright (C) 2006 Transcosmos INC.
 * All Rights Reserved.
 */
package jp.co.transcosmos.dm3.form.population;

import org.apache.commons.fileupload.FileItem;

import jp.co.transcosmos.dm3.form.FormPopulationFilter;

/**
 * Removes leading and trailing white space from form fields.
 * 
 * @author <a href="mailto:rick@knowleses.org">Rick Knowles</a>
 * @version $Id: TrimWhiteSpaceFilter.java,v 1.2 2007/05/31 10:18:25 rick Exp $
 */
public class TrimWhiteSpaceFilter implements FormPopulationFilter {

    public String[] filterString(String in[]) {
        if (in == null) {
            return in;
        } else {
            String out[] = new String[in.length];
            for (int n = 0; n < out.length; n++) {
                out[n] = (in[n] == null ? null : in[n].trim());
            }
            return out;
        }
    }

    public FileItem filterFileItem(FileItem pInput) {
        return pInput;
    }

}
