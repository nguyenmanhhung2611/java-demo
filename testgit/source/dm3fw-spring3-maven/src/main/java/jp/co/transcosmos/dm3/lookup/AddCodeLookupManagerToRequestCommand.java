/*
 * &copy; Trans-cosmos Inc. 2006, 2007.
 * All rights reserved.
 */
package jp.co.transcosmos.dm3.lookup;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;

/**
 * Adds the code lookup manager defined into the request attributes, so it will be 
 * visible in the JSP (accessible from the CodeLookupTag)
 * 
 * @author <a href="mailto:rick@knowleses.org">Rick Knowles</a>
 * @version $Id: AddCodeLookupManagerToRequestCommand.java,v 1.2 2007/05/31 09:02:31 rick Exp $
 */
public class AddCodeLookupManagerToRequestCommand implements Command {
    private static final Log log = LogFactory.getLog(AddCodeLookupManagerToRequestCommand.class);

    private CodeLookupManager codeLookupManager;
    private String requestAttributeName = "codeLookupManager";

    public void setCodeLookupManager(CodeLookupManager pCodeLookupManager) {
        this.codeLookupManager = pCodeLookupManager;
    }
    public void setRequestAttributeName(String pRequestAttributeName) {
        this.requestAttributeName = pRequestAttributeName;
    }
    
    public ModelAndView handleRequest(HttpServletRequest request, 
            HttpServletResponse response) throws Exception {
        if (this.codeLookupManager != null) {
            request.setAttribute(this.requestAttributeName, this.codeLookupManager);
            log.info("Added CodeLookupManager to request attribute: " + requestAttributeName);
        }
        return null;
    }

}
