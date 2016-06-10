/*
 * Copyright (C) 2006 Transcosmos INC.
 * All Rights Reserved.
 */
package jp.co.transcosmos.dm3.form;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.fileupload.FileItem;

/**
 * Internal class used to maintain chains of FormPopulationFilters to apply to
 * a field within a form.
 * 
 * @author <a href="mailto:rick@knowleses.org">Rick Knowles</a>
 * @version $Id: FormPopulationFilterChain.java,v 1.5 2007/05/31 10:18:26 rick Exp $
 */
public class FormPopulationFilterChain { 

// 2015/03/24 H.Mizuno 機能拡張の為、可視属性を変更 start
//    private List<ChainElement> elements;
	protected List<ChainElement> elements;
// 2015/03/24 H.Mizuno 機能拡張の為、可視属性を変更 end
    
    public void addAllFieldFilter(FormPopulationFilter filter) {
        if (this.elements == null) {
            this.elements = new ArrayList<ChainElement>();
        }
        this.elements.add(new ChainElement(null, filter));
    }
    
    public void addPerFieldFilter(String fieldName, FormPopulationFilter filter) {
        if (this.elements == null) {
            this.elements = new ArrayList<ChainElement>();
        }
        elements.add(new ChainElement(fieldName, filter));
    }
    
    public String[] filterString(String fieldName, String[] values) {
        if (this.elements != null) {
            for (Iterator<ChainElement> i = this.elements.iterator(); i.hasNext(); ) {
                ChainElement elm = i.next();
                if ((elm.fieldName == null) || elm.fieldName.equals(fieldName)) {
                    values = elm.filter.filterString(values);
                }
            }
        }
        return values;
    }
    
    public FileItem filterFileItem(String fieldName, FileItem fileItem) {
        if (this.elements != null) {
            for (Iterator<ChainElement> i = this.elements.iterator(); i.hasNext(); ) {
                ChainElement elm = i.next();
                if ((elm.fieldName == null) || elm.fieldName.equals(fieldName)) {
                    fileItem = elm.filter.filterFileItem(fileItem);
                }
            }
        }
        return fileItem;
    }
    
    class ChainElement {
        String fieldName;
        FormPopulationFilter filter;

        ChainElement(String fieldName, FormPopulationFilter filter) {
            this.fieldName = fieldName;
            this.filter = filter;
        }
    }
}
