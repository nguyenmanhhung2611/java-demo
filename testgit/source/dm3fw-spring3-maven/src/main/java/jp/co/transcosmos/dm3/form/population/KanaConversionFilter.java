/*
 * Copyright (C) 2006 Transcosmos INC.
 * All Rights Reserved.
 */
package jp.co.transcosmos.dm3.form.population;

import jp.co.transcosmos.dm3.form.FormPopulationFilter;
import jp.co.transcosmos.dm3.kana.KanaConverter;

import org.apache.commons.fileupload.FileItem;

/**
 * Apply a kana type conversion to the form field filtered. 
 * 
 * @author <a href="mailto:rick@knowleses.org">Rick Knowles</a>
 * @version $Id: KanaConversionFilter.java,v 1.2 2007/05/31 10:18:25 rick Exp $
 */
public class KanaConversionFilter implements FormPopulationFilter {

    private int to;
    private int[] fromTypes;
    
    public KanaConversionFilter() {
        this(KanaConverter.ZENKAKU);
    }
    
    public KanaConversionFilter(int toType) {
        this(toType, KanaConverter.FROM_ALL);
    }
    
    public KanaConversionFilter(int toType, int fromTypes[]) {
        this.to = toType;
        this.fromTypes = fromTypes;
    }
    
    public String convert(String in) {
        if (in == null) {
            return in;
        } else {
            return KanaConverter.convert(in, this.to, this.fromTypes);
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
