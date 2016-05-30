/*
 * Copyright (C) 2006 Transcosmos INC.
 * All Rights Reserved.
 */
package jp.co.transcosmos.dm3.form.population;

import jp.co.transcosmos.dm3.form.FormPopulationFilter;

import org.apache.commons.fileupload.FileItem;

/**
 * Auto-correct common mistakes in email addresses as entered in the browser.
 * 
 * @author <a href="mailto:rick@knowleses.org">Rick Knowles</a>
 * @version $Id: EmailAddressFilter.java,v 1.2 2007/05/31 10:18:25 rick Exp $
 */
public class EmailAddressFilter implements FormPopulationFilter {

    public static String fixEmail(String in) {
        // Remove any whitespace
        in = in.replace("Å@", "");
        in = in.replaceAll("\\p{Space}", "");
        
        // Remove any control chars and make dots replace commas
        in = in.replaceAll("\\p{Cntrl}", "");
        in = in.replace(',', '.');
        
        // Check for an at-sign
        int atPos = in.indexOf("@");
        if (atPos == -1) {
            return in; // Not much we can do, since we can't gurarantee the format of this address
        }
        
        // Remove dots either side of the at-sign
        in = in.replace(".@", "@");
        in = in.replace("@.", "@");
        
        // If we end in a dot, strip it
        in = in.replaceAll("\\.$", "");
        in = in.replaceAll("^\\.", "");
        return in;
    }
    
    public String[] filterString(String in[]) {
        if (in == null) {
            return in;
        } else {
            String out[] = new String[in.length];
            for (int n = 0; n < out.length; n++) {
                out[n] = (in[n] == null ? null : fixEmail(in[n]));
            }
            return out;
        }
    }

    public FileItem filterFileItem(FileItem pInput) {
        return pInput;
    }
}
