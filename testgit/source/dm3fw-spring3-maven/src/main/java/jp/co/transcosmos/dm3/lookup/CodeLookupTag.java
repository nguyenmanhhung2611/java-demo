/*
 * &copy; Trans-cosmos Inc. 2006, 2007.
 * All rights reserved.
 */
package jp.co.transcosmos.dm3.lookup;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Tag to do a simple lookup of a value from the code lookup manager inside JSP
 * 
 * @author <a href="mailto:rick@knowleses.org">Rick Knowles</a>
 * @version $Id: CodeLookupTag.java,v 1.2 2007/05/31 09:02:31 rick Exp $
 */
public class CodeLookupTag extends TagSupport {
    private static final long serialVersionUID = 1;
    private static final Log log = LogFactory.getLog(CodeLookupTag.class);

    private String codeLookupManagerParameterName;
    private String lookupName;
    private String lookupKey;
    private String wildcards[];
// 2015.02.27 H.Mizuno 該当データが無い場合のデフォルト値を設定する機能を追加 start
    private String defaultValue;
// 2015.02.27 H.Mizuno 該当データが無い場合のデフォルト値を設定する機能を追加 end

    
    
    public void setCodeLookupManagerParameterName(String pCodeLookupManagerParameterName) {
        this.codeLookupManagerParameterName = pCodeLookupManagerParameterName;
    }

    public void setLookupKey(String pLookupKey) {
        this.lookupKey = pLookupKey;
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

// 2015.02.27 H.Mizuno 該当データが無い場合のデフォルト値を設定する機能を追加 start
    public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
// 2015.02.27 H.Mizuno 該当データが無い場合のデフォルト値を設定する機能を追加 end



	public int doStartTag() throws JspException {
        String managerParam = this.codeLookupManagerParameterName;
        if (managerParam == null) {
            managerParam = "codeLookupManager";
        }
        CodeLookupManager manager = (CodeLookupManager) this.pageContext.getRequest().getAttribute(managerParam);
        if (manager != null) {
            String lookupValue = manager.lookupValue(lookupName, lookupKey, wildcards);
// 2015.02.27 H.Mizuno 該当データが無い場合のデフォルト値を設定する機能を追加 start
            // もし値が取得出来なくて、defaultValue 属性に初期値が設定されている場合、その値を使用する。
            if (lookupValue == null && this.defaultValue != null) {
            	lookupValue = this.defaultValue;
            }
// 2015.02.27 H.Mizuno 該当データが無い場合のデフォルト値を設定する機能を追加 end
            
            try {
                this.pageContext.getOut().write(lookupValue == null ? "" : lookupValue);
            } catch (IOException err) {
                throw new JspTagException("Error writing lookup value for lookup: " + lookupName + 
                        " key: " + lookupKey, err);
            }
        } else {
            log.warn("WARNING: Code lookup manager not found");
        }
        
        return EVAL_PAGE;
    }

    public int doEndTag() throws JspException {
        this.codeLookupManagerParameterName = null;
        this.lookupKey = null;
        this.lookupName = null;
        this.wildcards = null;
// 2015.02.27 H.Mizuno 該当データが無い場合のデフォルト値を設定する機能を追加 start
        this.defaultValue = null;
// 2015.02.27 H.Mizuno 該当データが無い場合のデフォルト値を設定する機能を追加 end

        return super.doEndTag();
    }
}
