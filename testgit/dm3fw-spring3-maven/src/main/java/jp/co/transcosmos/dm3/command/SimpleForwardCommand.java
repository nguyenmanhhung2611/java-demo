/*
 * Copyright (C) 2006 Transcosmos INC.
 * All Rights Reserved.
 */
package jp.co.transcosmos.dm3.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

/**
 * Performs a simple forward, using the url mapping we specified. Effectively 
 * a do-thing, forward to JSP operation.
 * 
 * @author <a href="mailto:rick_knowles@hotmail.com">Rick Knowles</a>
 * @version $Id: SimpleForwardCommand.java,v 1.2 2007/05/31 10:37:57 rick Exp $
 */
public class SimpleForwardCommand implements Command {

    private String toPage;
    
    public String getToPage() {
        return toPage;
    }
    public void setToPage(String toPage) {
        this.toPage = toPage;
    }

    public ModelAndView handleRequest(HttpServletRequest request, 
            HttpServletResponse response) throws Exception {
        return new ModelAndView(this.toPage, null);
    }
}
