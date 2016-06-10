/*
 * Copyright (C) 2006 Transcosmos INC.
 * All Rights Reserved.
 */
package jp.co.transcosmos.dm3.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.RowMapper;

/**
 * Join result row mapper. This aggregates the results from joins into valueobjects contained
 * in a map keyed by the alias. This is only used internally by the JoinDAO and StarJoinDAO
 * classes.
 * 
 * @author <a href="mailto:rick@knowleses.org">Rick Knowles</a>
 * @version $Id: JoinRowMapper.java,v 1.3 2007/06/13 06:32:03 rick Exp $
 * @see JoinDAO, StarJoinDAO
 */
public class JoinRowMapper implements RowMapper {
    private static final Log log = LogFactory.getLog(JoinRowMapper.class);

    private String aliases[];
    private RowMapper rowMappers[];
    private int count;
    private boolean useHashMap;
    
    public JoinRowMapper(String aliases[], RowMapper rowMappers[], int count,
            boolean useHashMap) {
        this.aliases = aliases;
        this.rowMappers = rowMappers;
        this.count = count;
        this.useHashMap = useHashMap;
    }
    
    /**
     * Transfer the attributes from a single row into it's component valueobjects
     * by delegating to the child row mappers, and then aggregating their results into
     * a single JoinResult object. 
     */
    public Object mapRow(ResultSet resultset, int row) throws SQLException {
        JoinResult joinResult = new JoinResult(useHashMap ? 
                new HashMap<String,Object>() : 
                new JoinResultMap());
        for (int n = 0; n < this.count; n++) {
            if (this.rowMappers[n] != null) {
                Object result = this.rowMappers[n].mapRow(resultset, row);
                if (result instanceof JoinResult) {
                    joinResult.getItems().putAll(((JoinResult) result).getItems());
                    result = null;
                } else if (result != null) {
                    log.debug("Adding item " + this.aliases[n] + " to join result:" + result);
                    joinResult.getItems().put(this.aliases[n], result);
                }
            }            
        }
        return joinResult;
    }
}
