/*
 * Copyright (C) 2006 Transcosmos INC.
 * All Rights Reserved.
 */
package jp.co.transcosmos.dm3.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.InvalidResultSetAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

/**
 * A resultset population class employs a RowMapper to do the actual association, but 
 * provides from/to index extraction optimisations that lighten the load on the database
 * server, Only used internally.
 * 
 * @author <a href="mailto:rick@knowleses.org">Rick Knowles</a>
 * @version $Id: FragmentResultSetExtractor.java,v 1.6 2007/06/13 04:58:54 rick Exp $
 */
public class FragmentResultSetExtractor<E> implements ResultSetExtractor {
    private static final Log log = LogFactory.getLog(FragmentResultSetExtractor.class);
    
    private RowMapper rowMapper;
    private int rowCountHint = -1;
    
    private int fragmentStart = 0;
    private int fragmentEnd = -1;
    
    public FragmentResultSetExtractor(RowMapper rowMapper, int fragmentStart, 
            int fragmentEnd) {
        this.rowMapper = rowMapper;
        this.fragmentStart = Math.max(0, fragmentStart);
        if (fragmentEnd >= 0) {
            this.fragmentEnd = Math.max(fragmentEnd, this.fragmentStart);
        } else {
            this.fragmentEnd = -1;
        }
    }
    
    public FragmentResultSetExtractor(RowMapper rowMapper, DAOCriteria criteria) {
        this(rowMapper, criteria != null ? criteria.getFragmentOffset() : 0,
                criteria != null ? criteria.getFragmentLimit() : -1);
    }
    
    public void setRowCountHint(int rowCountHint) {
        this.rowCountHint = rowCountHint;
    }

    public boolean isFragmentMode() {
        return (fragmentStart > 0) || (fragmentEnd >= 0);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object extractData(ResultSet resultset) throws SQLException, DataAccessException {
        long start = System.currentTimeMillis();
        log.info("Starting result extraction (fragmentStart=" + fragmentStart + 
                ", fragmentEnd=" + fragmentEnd + ")");
        if (isFragmentMode()) {
            // Navigate to the fragment start
            if (fragmentStart > 0) {
                if (resultset.next()) {
                    try {
                        resultset.relative(fragmentStart - 1);
                    } catch (SQLException err) {
                        if (resultset.getType() == ResultSet.TYPE_FORWARD_ONLY) {
                            // this is because oracle can write drivers that (barely) comply 
                            // with the spec, but simple common sense is a bit too difficult 
                            // for them. Oracle's driver doesn't support last() or relative() on 
                            // forward only result sets, so we iterate manually (heavy)
                            log.warn("WARNING: forward-only resultset, so calling relative() " +
                                    "requires iterating to the end of the resultset manually (heavy)");
                            for (int n = 0; (n < fragmentStart - 1) && resultset.next(); n++) {
                                // do nothing, resultset.next() is called above
                            }
                        } else {
                            throw err;
                        }
                    }
                } else {
                    log.info("Query returned 0 rows");
                    return new ArrayList<E>();
                }
            }
            int counter = resultset.getRow();
            log.debug("Starting from row no: " + counter + 
            		", range=" + fragmentStart + "-" + fragmentEnd);
            
            List<E> fragment = new ArrayList<E>();
            boolean keepGoing = ((fragmentEnd < 0) || (counter < fragmentEnd));
            while (keepGoing && resultset.next()) {
                counter++;
                try {
                    E mapped = (E) this.rowMapper.mapRow(resultset, resultset.getRow());
                    fragment.add(mapped);
                } catch (SQLException err) {
                    throw new InvalidResultSetAccessException(err);
                }
                
                keepGoing = ((fragmentEnd < 0) || (counter < fragmentEnd));
                if (!keepGoing && (rowCountHint == -1)) {
                    try {
                        log.info("Scrolling to last from counter=" + counter);
                        resultset.last();
                        rowCountHint = resultset.getRow();
                        log.info("Scrolling to last finished, rowCount=" + rowCountHint);
                    } catch (SQLException err) {
                        if (resultset.getType() == ResultSet.TYPE_FORWARD_ONLY) {
                            // this is because oracle can write drivers that (barely) comply 
                            // with the spec, but simple common sense is a bit too difficult 
                            // for them. Oracle's driver doesn't support last() or relative() on 
                            // forward only result sets, so we iterate manually (heavy)
                            log.warn("WARNING: forward-only resultset, so calling last() " +
                                    "requires iterating to the end of the resultset manually (heavy)");
                            while (resultset.next()) {
                                counter++;
                            }
                        } else {
                            throw err;
                        }
                    }
                }
            }
            log.info("Finished at row no: " + counter + 
                    ", range=" + fragmentStart + "-" +
                    fragmentEnd);

            if ((fragmentStart > 0) && fragment.isEmpty()) {
            	// not enough rows
            	throw new NotEnoughRowsException("range=" + fragmentStart + "-" + 
            			fragmentEnd + ", rowCount=" + counter, counter);
            }
            
            if (rowCountHint == -1) {
                rowCountHint = counter;
                log.debug("Set row count to " + rowCountHint);
            }
            // Return fragment wrapper
            log.info("Query found " + rowCountHint + " rows (returned rows " + (fragmentStart + 1) + 
                    "-" + Math.max(fragmentStart, Math.min(rowCountHint, fragmentEnd)) + 
                    " as fragment, iterated in " + (System.currentTimeMillis() - start) + "ms)");
            return new ResultFragmentList<E>(fragment, fragmentStart, rowCountHint);
        } else {
            // Do a straight retrieval, because we don't care about fragments
            List<E> results = new ArrayList<E>();
            while (resultset.next()) {
                results.add((E) this.rowMapper.mapRow(resultset, resultset.getRow()));
            }
            log.info("Query returned " + results.size() + " rows, " +
                    "iterated in " + (System.currentTimeMillis() - start) + "ms");
            return results;
        }
    }
}
