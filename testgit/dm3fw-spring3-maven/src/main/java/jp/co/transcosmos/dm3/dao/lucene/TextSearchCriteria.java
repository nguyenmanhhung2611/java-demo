/*
 * Copyright (C) 2006 Transcosmos INC.
 * All Rights Reserved.
 */
package jp.co.transcosmos.dm3.dao.lucene;

import org.apache.lucene.search.Query;
import org.apache.lucene.search.Sort;

/**
 * Parameters for a query on the text-search index. See the lucene documentation
 * for more details on the Query and Sort classes. The fragmentOffset and fragmentLimit
 * allow hints for a range of interest in the output (i.e. the search only needs return
 * the rows between startIndex = fragmentOffset and endIndex = fragmentLimit)
 * 
 * @author <a href="mailto:rick@knowleses.org">Rick Knowles</a>
 * @version $Id: TextSearchCriteria.java,v 1.3 2007/06/27 04:37:05 rick Exp $
 */
public class TextSearchCriteria {
    private Query query;
    private Query filterQuery;
    private Sort sort;
    private int fragmentOffset = 0;
    private int fragmentLimit = -1;
    
    public TextSearchCriteria() {}
    
    public TextSearchCriteria(Query query) {
        this();
        this.query = query;
    }
    
    public TextSearchCriteria(Query query, Sort sort) {
        this();
        this.query = query;
        this.sort = sort;
    }
    
    public TextSearchCriteria(Query query, Query filterQuery, Sort sort, 
            int fragmentOffset, int fragmentLimit) {
        this();
        this.query = query;
        this.filterQuery = filterQuery;
        this.sort = sort;
        this.fragmentOffset = fragmentOffset;
        this.fragmentLimit = fragmentLimit;
    }
    
    public Query getFilterQuery() {
        return filterQuery;
    }
    public void setFilterQuery(Query filterQuery) {
        this.filterQuery = filterQuery;
    }
    public int getFragmentLimit() {
        return fragmentLimit;
    }
    public void setFragmentLimit(int limit) {
        this.fragmentLimit = limit;
    }
    public int getFragmentOffset() {
        return fragmentOffset;
    }
    public void setFragmentOffset(int offset) {
        this.fragmentOffset = offset;
    }
    public Query getQuery() {
        return query;
    }
    public void setQuery(Query query) {
        this.query = query;
    }
    public Sort getSort() {
        return sort;
    }
    public void setSort(Sort sort) {
        this.sort = sort;
    }
    
    
}
