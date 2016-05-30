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
 * Wraps the incoming request with the CharacterReplace request wrapper. By default, the
 * table used is a list of characters that don't map well from EUC-JP to SJIS.
 * 
 * @author <a href="mailto:rick@knowleses.org">Rick Knowles</a>
 * @version $Id: CharacterReplaceFilter.java,v 1.1 2007/09/28 08:04:23 rick Exp $
 */
public class CharacterReplaceFilter extends BaseFilter {
    private static final Log log = LogFactory.getLog(CharacterReplaceFilter.class);
    
    private static final String[][] BAD_MAPPINGS = {
        {"‡@", "1"}, {"‡A", "2"}
    };
    
    protected String[][] getTable() {
        return BAD_MAPPINGS;
    }

    protected void filterAction(HttpServletRequest req, HttpServletResponse res, FilterChain chain) 
            throws IOException, ServletException {
        String table[][] = getTable();
        
        log.info(getFilterName() + ": Wrapping with character replacer, table size=" + table.length);
        chain.doFilter(new CharacterReplaceHttpServletRequestWrapper(req, table), res);
	}
}