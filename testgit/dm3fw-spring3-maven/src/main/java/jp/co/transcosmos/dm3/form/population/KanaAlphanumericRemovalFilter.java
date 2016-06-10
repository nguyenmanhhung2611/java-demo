/*
 * Copyright (C) 2006 Transcosmos INC.
 * All Rights Reserved.
 */
package jp.co.transcosmos.dm3.form.population;

import org.apache.commons.fileupload.FileItem;

import jp.co.transcosmos.dm3.form.FormPopulationFilter;

/**
 * Replace any detected double-byte whitespace, alphabet or number characters
 * with the corresponding single-byte versions. This helps prevent
 * number parsing errors if double-byte numbers are added.
 * 
 * @author <a href="mailto:rick@knowleses.org">Rick Knowles</a>
 * @version $Id: KanaAlphanumericRemovalFilter.java,v 1.2 2007/05/31 10:18:25 rick Exp $
 */
public class KanaAlphanumericRemovalFilter implements FormPopulationFilter {

    private static final char OFFSET = 0xfee0;
    private static final char ZERO_CHAR = 0xff01;
    private static final char Z_CHAR = 0xff5e;
    private static final char SPACE_CHAR = 0x3000;
    
    public static String convert(String in) {
        if (in == null) {
            return in;
        } else {
            StringBuilder returnValue = new StringBuilder();
            for (int n = 0; n < in.length(); n++) {
                char thisChar = in.charAt(n);
                if ((thisChar >= ZERO_CHAR) && (thisChar <= Z_CHAR)) {
                    returnValue.append((char) (thisChar - OFFSET));
                } else if (thisChar == SPACE_CHAR) {
                    returnValue.append(' ');
                } else {
                    returnValue.append(thisChar);
                }
            }
            return returnValue.toString();
        }
    }
    
    public String[] filterString(String in[]) {
        if (in == null) {
            return in;
        } else {
            String out[] = new String[in.length];
            for (int n = 0; n < out.length; n++) {
                out[n] = convert(in[n]);
            }
            return out;
        }
    }

    public FileItem filterFileItem(FileItem input) {
        return input;
    }
}
