/*
 * Copyright (C) 2006 Transcosmos INC.
 * All Rights Reserved.
 */
package jp.co.transcosmos.dm3.dao.lucene;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.dao.ReadOnlyDAO;
import jp.co.transcosmos.dm3.form.FormPopulator;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.queryParser.QueryParser;
import org.springframework.web.servlet.ModelAndView;

/**
 * Does a simple lucene search on a database text index object
 * 
 * @author <a href="mailto:rick@knowleses.org">Rick Knowles</a>
 * @version $Id: SimpleTextSearchQueryCommand.java,v 1.1 2007/06/27 11:03:19 rick Exp $
 */
public class SimpleTextSearchQueryCommand<T> implements Command {

    private ReadOnlyDAO<T> dao;
    private DatabaseTextIndex<T> index;
    private QueryParser queryParser;
    private Analyzer analyzer = new StandardAnalyzer();
    private int rowsPerPage = 20;
    
    public void setIndex(DatabaseTextIndex<T> index) {
        this.index = index;
    }
    
    public void setSourceDAO(ReadOnlyDAO<T> sourceDAO) {
        this.dao = sourceDAO;
    }

    public void setQueryParser(QueryParser queryParser) {
        this.queryParser = queryParser;
    }

    public void setAnalyzer(Analyzer analyzer) {
        this.analyzer = analyzer;
    }

    public void setRowsPerPage(int rowsPerPage) {
        this.rowsPerPage = rowsPerPage;
    }

    public ModelAndView handleRequest(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        
        // Parse the query
        SimpleTextSearchQueryForm<T> form = getForm(request);
        form.setRowsPerPage(this.rowsPerPage);
        
        List<ValidationFailure> errors = new ArrayList<ValidationFailure>();
        if (!form.validate(errors)) {
            // Validation error - return errors and form
            Map<String,Object> model = new HashMap<String,Object>();
            model.put("errors", errors);
            model.put("searchForm", form);
            return new ModelAndView("validationError", model);
        } else {
            List<T> rows = this.index.search(form.buildTextSearchCriteria(
                    getQueryParser()), this.dao);
            form.setRows(rows);
            return new ModelAndView("success", "searchForm", form);
        }
    }
    
    protected SimpleTextSearchQueryForm<T> getForm(HttpServletRequest request) throws Exception {
        SimpleTextSearchQueryForm<T> form = new SimpleTextSearchQueryForm<T>();
        FormPopulator.populateFormBeanFromRequest(request, form);
        return form;
    }
    
    protected QueryParser getQueryParser() {
        if (this.queryParser != null) {
            return new org.apache.lucene.queryParser.MultiFieldQueryParser(
                    this.index.getAllIndexedFieldNames(), this.analyzer);
        } else {
            return this.queryParser;
        }
    }
}
