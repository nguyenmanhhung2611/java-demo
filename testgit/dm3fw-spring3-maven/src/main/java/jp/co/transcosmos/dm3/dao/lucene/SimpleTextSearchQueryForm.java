/*
 * Copyright (C) 2006 Transcosmos INC.
 * All Rights Reserved.
 */
package jp.co.transcosmos.dm3.dao.lucene;

import java.util.List;

import jp.co.transcosmos.dm3.form.PagingListForm;
import jp.co.transcosmos.dm3.validation.Validateable;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;

/**
 * Captures the user's entered query string, as well as setting up the results for 
 * paging.
 * 
 * @author <a href="mailto:rick@knowleses.org">Rick Knowles</a>
 * @version $Id: SimpleTextSearchQueryForm.java,v 1.1 2007/06/27 11:03:19 rick Exp $
 */
public class SimpleTextSearchQueryForm<T> extends PagingListForm<T> implements Validateable {

    private String searchQueryString;
    private String filterQueryString;
    private String sortField;
    private String sortIsAscending;

    public String getFilterQueryString() {
        return filterQueryString;
    }

    public void setFilterQueryString(String filterQueryString) {
        this.filterQueryString = filterQueryString;
    }

    public String getSearchQueryString() {
        return searchQueryString;
    }

    public void setSearchQueryString(String searchQueryString) {
        this.searchQueryString = searchQueryString;
    }

    public String getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    public String getSortIsAscending() {
        return sortIsAscending;
    }

    public void setSortIsAscending(String sortIsAscending) {
        this.sortIsAscending = sortIsAscending;
    }

    public TextSearchCriteria buildTextSearchCriteria(QueryParser parser) 
            throws ParseException {
        Query query = parser.parse(getSearchQueryString());
        Query filterQuery = null;
        if (ifNull(getFilterQueryString(), "").equals("")) {
            filterQuery = parser.parse(getFilterQueryString());
        }
        Sort sort = null;
        if (ifNull(getSortField(), "").equals("")) {
            sort = new Sort(new SortField(getSortField(), 
                    ifNull(getFilterQueryString(), "false").equalsIgnoreCase("true")));
        }

        TextSearchCriteria criteria = new TextSearchCriteria();
        criteria.setQuery(query);
        criteria.setFilterQuery(filterQuery);
        criteria.setSort(sort);
        criteria.setFragmentOffset(getStartIndex());
        criteria.setFragmentLimit(getEndIndex());
        return criteria;
    }
    
    private static String ifNull(String text, String defaultValue) {
        return text == null ? defaultValue : text;
    }

    public boolean validate(List<ValidationFailure> errors) {
        return true;
    }
}
