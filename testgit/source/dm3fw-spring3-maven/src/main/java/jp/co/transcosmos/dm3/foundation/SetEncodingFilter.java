/*
 * Copyright (C) 2006 Transcosmos INC.
 * All Rights Reserved.
 */
package jp.co.transcosmos.dm3.foundation;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.servlet.BaseFilter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Very simple request encoding setter filter. This ensures that all requests are processed
 * according to a defined character encoding, regardless of the encoding they come in with.
 * 
 * @author <a href="mailto:rick@knowleses.org">Rick Knowles</a>
 * @version $Id: SetEncodingFilter.java,v 1.3 2007/05/31 09:46:37 rick Exp $
 */
public class SetEncodingFilter extends BaseFilter {
    private static final Log log = LogFactory.getLog(SetEncodingFilter.class);

    protected void filterAction(HttpServletRequest req, HttpServletResponse res, FilterChain chain) 
            throws IOException, ServletException {
        String encoding = this.getInitParameter("encoding", "Windows-31J");
        log.info(getFilterName() + ": Setting request encoding: " + encoding);
		req.setCharacterEncoding(encoding);
        res.setCharacterEncoding(encoding);
        chain.doFilter(req, res);
	}
}