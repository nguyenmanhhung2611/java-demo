/*
 * Copyright (C) 2006 Transcosmos INC.
 * All Rights Reserved.
 */
package jp.co.transcosmos.dm3.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.SqlTypeValue;
import org.springframework.jdbc.core.StatementCreatorUtils;

/**
 * Acts as a query builder that will assign values to the prepared statement, and also
 * set the options on the prepared statement we actually build. Only used internally.
 * 
 * @author <a href="mailto:rick@knowleses.org">Rick Knowles</a>
 * @version $Id: FragmentPreparedStatementCreator.java,v 1.3 2007/06/13 04:58:54 rick Exp $
 */
public class FragmentPreparedStatementCreator implements PreparedStatementCreator,
        PreparedStatementSetter {
    private String sql;
    private List<Object> args;
    
    /**
     * Supply the list of values and the sql to use
     */
    public FragmentPreparedStatementCreator(String sql, List<Object> args) {
        this.sql = sql;
        this.args = args;
    }

    /**
     * Creates the initial prepared statement instance we use from the connection class
     */
    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
        return connection.prepareStatement(sql);
    }

    /**
     * Set each value with type unknown on the preparestatemnet
     */
    public void setValues(PreparedStatement ps) throws SQLException {
        if (this.args != null) {
            int n = 0;
            for (Iterator<Object> i = args.iterator(); i.hasNext(); n++) {
                StatementCreatorUtils.setParameterValue(ps, n + 1, 
                        SqlTypeValue.TYPE_UNKNOWN, null, i.next());
            }
        }
    }
}