/*
 * &copy; Trans-cosmos Inc. 2006, 2007.
 * All rights reserved.
 */
package jp.co.transcosmos.dm3.view;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.utils.SimpleExpressionLanguage;

import org.springframework.web.servlet.view.AbstractView;

/**
 * Writes an error response to browser (equivalent to calling response.sendError()).
 * This view is used when the view definition in the url-mapping file is prefixed 
 * with "error:" or directly from the Command class with 
 * "return new ModelAndView(new ErrorView(500, "error msg"));".
 * 
 * The error message supports the simple Expression language, so it is possible to 
 * use model data in the wildcards in the message.
 * 
 * @author <a href="mailto:rick@knowleses.org">Rick Knowles</a>
 * @version $Id: ErrorView.java,v 1.2 2007/05/31 03:46:47 rick Exp $
 */
public class ErrorView extends AbstractView {
    
    private int errorNo = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
    private String messageString;

    public ErrorView() {
        this(null);
    }
    
    public ErrorView(String errorNoColonMessageString) {
        super();
        if (errorNoColonMessageString != null) {
            errorNoColonMessageString = errorNoColonMessageString.trim();
            if (errorNoColonMessageString.startsWith("[")) {
                // get the close bracket pos
                int closeBracket = errorNoColonMessageString.indexOf(']', 1);
                this.errorNo = Integer.parseInt(errorNoColonMessageString.substring(1, closeBracket));
                this.messageString = errorNoColonMessageString.substring(closeBracket + 1).trim();
            } else {
                this.messageString = errorNoColonMessageString;
            } 
        }
    }
    
    public ErrorView(int errorNo, String messageString) {
        super();
        this.errorNo = errorNo;
        this.messageString = messageString;
    }
    
    @SuppressWarnings("unchecked") 
    protected void renderMergedOutputModel(Map model, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        String errorMessage = "";
        if (this.messageString != null) {
            errorMessage = SimpleExpressionLanguage.evaluateWebExpressionPatternString(
                    this.messageString, request, response, getServletContext(), model, 
                    false, response.getCharacterEncoding(), "");
        }
        if (!errorMessage.equals("")) {
            response.sendError(this.errorNo, errorMessage);
        } else {
            response.sendError(this.errorNo);
        }
    }
}
