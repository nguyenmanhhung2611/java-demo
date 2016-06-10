/*
 * Copyright (C) 2006 Transcosmos INC.
 * All Rights Reserved.
 */
package jp.co.transcosmos.dm3.dao.lucene;

import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.dao.DAOCriteria;
import jp.co.transcosmos.dm3.dao.NotEnoughRowsException;
import jp.co.transcosmos.dm3.dao.ReadOnlyDAO;

import org.springframework.web.servlet.ModelAndView;

/**
 * Rewrites the contents of the injected index with a query to the injected
 * DAO.
 * 
 * @author <a href="mailto:rick@knowleses.org">Rick Knowles</a>
 * @version $Id: RebuildTextSearchIndexCommand.java,v 1.1 2007/06/27 11:03:19 rick Exp $
 */
public class RebuildTextSearchIndexCommand<T> implements Command {

    private ReadOnlyDAO<T> sourceDAO;
    private DatabaseTextIndex<T> index;
    private DAOCriteria criteria;
    private int pagingSize = 1000;
    
    public void setIndex(DatabaseTextIndex<T> index) {
        this.index = index;
    }
    
    public void setSourceDAO(ReadOnlyDAO<T> sourceDAO) {
        this.sourceDAO = sourceDAO;
    }
    
    public void setCriteria(DAOCriteria criteria) {
        this.criteria = criteria;
    }

    public void setPagingSize(int pagingSize) {
        this.pagingSize = pagingSize;
    }

    public ModelAndView handleRequest(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        Iterator<T> dataIterator = new RebuildIterator(this.sourceDAO, this.criteria,
                this.pagingSize);
        this.index.rebuildAll(dataIterator);
        return new ModelAndView("success");
    }
    
    /**
     * Implements a paging iterator over the resultset
     */
    class RebuildIterator implements Iterator<T> {

    	private Iterator<T> onePage;
    	private int index;
    	
    	private int pagingSize;
    	private ReadOnlyDAO<T> sourceDAO;
    	private DAOCriteria criteria;
    	
    	RebuildIterator(ReadOnlyDAO<T> sourceDAO, DAOCriteria criteria,
    			int pagingSize) {
    		this.sourceDAO = sourceDAO;
    		this.criteria = criteria.cloneMe();
    		this.pagingSize = pagingSize;
    		this.index = 0;
    		
    		this.criteria.setFragmentOffset(0);
    		this.criteria.setFragmentLimit(this.pagingSize);
    		this.onePage = this.sourceDAO.selectByFilter(this.criteria).iterator();
    	}
    	
		public boolean hasNext() {
			if (this.onePage == null) {
				return false; // mark that we have reached the end
			} else if (this.onePage.hasNext()) {
				return true;
			} else {
				// check for another page
	    		this.criteria.setFragmentOffset(this.index);
	    		this.criteria.setFragmentLimit(this.index + this.pagingSize);
	    		try {
		    		this.onePage = this.sourceDAO.selectByFilter(this.criteria).iterator();
					return this.onePage.hasNext();
	    		} catch (NotEnoughRowsException err) {
	    			// hit the limit
	    			this.onePage = null;
	    			return false;
	    		}
			}
		}

		public T next() {
			if (hasNext()) {
				return this.onePage.next();
			} else {
				return null;
			}
		}

		public void remove() {
			throw new RuntimeException("Not implemented");
		}    	
    }
}
