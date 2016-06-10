/*
 * &copy; Trans-cosmos Inc. 2006, 2007.
 * All rights reserved.
 */
package jp.co.transcosmos.dm3.lookup;

import java.util.Iterator;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.jstl.core.LoopTagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Tag to do a simple loop over the key/value pairs defined as a code lookup 
 * manager from inside a JSP
 * 
 * @author <a href="mailto:rick@knowleses.org">Rick Knowles</a>
 * @version $Id: CodeLookupLoopTag.java,v 1.2 2007/05/31 09:02:31 rick Exp $
 */
public class CodeLookupLoopTag extends LoopTagSupport {
    private static final long serialVersionUID = 1;
    private static final Log log = LogFactory.getLog(CodeLookupLoopTag.class);

    private String codeLookupManagerParameterName = "codeLookupManager";
    private String lookupName;
    private String wildcards[];
    private Iterator<String> lookupKeys;
    
    private String keyParameter = "key";
    private String valueParameter = "value";
    
    private Object oldKey = null;
    private Object oldValue = null;

    public void setCodeLookupManagerParameterName(String pCodeLookupManagerParameterName) {
        this.codeLookupManagerParameterName = pCodeLookupManagerParameterName;
    }

    public void setLookupName(String pLookupName) {
        this.lookupName = pLookupName;
    }

    public void setWildcard(String pWildcard) {
        setWildcards(new String[] {pWildcard});
    }

    public void setWildcards(String[] pWildcards) {
        this.wildcards = pWildcards;
    }

    public void setWildcards(List<String> pWildcards) {
        setWildcards(pWildcards.toArray(new String[pWildcards.size()]));
    }

    public void setKeyParameter(String pKeyParameter) {
        this.keyParameter = pKeyParameter;
    }

    public void setValueParameter(String pValueParameter) {
        this.valueParameter = pValueParameter;
    }

    public int doStartTag() throws JspException {
        this.oldKey = this.pageContext.getAttribute(this.keyParameter);
        this.oldValue = this.pageContext.getAttribute(this.valueParameter);
        return super.doStartTag();
    }
    
    private CodeLookupManager getManager() {
        return (CodeLookupManager) this.pageContext.getRequest().getAttribute(
                this.codeLookupManagerParameterName);
    }

    protected boolean hasNext() throws JspTagException {
        return (this.lookupKeys != null) && this.lookupKeys.hasNext();
    }

    protected Object next() throws JspTagException {
        String key = this.lookupKeys.next();
        this.pageContext.setAttribute(this.keyParameter, key);
        this.pageContext.setAttribute(this.valueParameter, 
                getManager().lookupValue(lookupName, key, wildcards));
        return key;
    }

    protected void prepare() throws JspTagException {
        
        String managerParam = this.codeLookupManagerParameterName;
        if (managerParam == null) {
            managerParam = "codeLookupManager";
        }
        CodeLookupManager manager = (CodeLookupManager) this.pageContext.getRequest().getAttribute(
                managerParam);
        if (manager != null) {
            this.lookupKeys = manager.getKeysByLookup(lookupName);
        } else {
            log.warn("WARNING: Code lookup manager not found");
        }
    }

    public int doEndTag() throws JspException {
        this.codeLookupManagerParameterName = "codeLookupManager";
        this.lookupName = null;
        this.wildcards = null;
        this.lookupKeys = null;
        if (this.oldKey != null) {
            this.pageContext.setAttribute(this.keyParameter, oldKey);
        }
        if (this.oldValue != null) {
            this.pageContext.setAttribute(this.valueParameter, oldValue);
        }
        
        this.keyParameter = "key";
        this.valueParameter = "value";
        
        return super.doEndTag();
    }
}
