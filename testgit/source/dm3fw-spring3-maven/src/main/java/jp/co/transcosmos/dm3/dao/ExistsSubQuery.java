/*
 * Copyright (C) 2006 Transcosmos INC.
 * All Rights Reserved.
 */
package jp.co.transcosmos.dm3.dao;

import java.util.List;

/**
 * Implements a simple exists sub-query. This uses a DAO and criteria to create the
 * sub query SQL, so:
 * <pre> SELECT * FROM abc WHERE EXISTS (SELECT 1 FROM def WHERE def.status = 'OK');</pre>
 * would look like this:
 * <pre> DAOCriteria crit = new DAOCriteria();
 * crit.addExistsSubQuery(new ExistsSubQuery(defDAO, new DAOCriteria("status", "OK"), false));
 * List results = abcDAO.selectByFilter(crit);</pre>
 * 
 * @author <a href="mailto:rick@knowleses.org">Rick Knowles</a>
 * @version $Id: ExistsSubQuery.java,v 1.6 2007/06/13 04:58:54 rick Exp $
 */
public class ExistsSubQuery implements DAOCriteriaSubQuery {

    private boolean negate;
    private SubQueryable subQueryDAO;
    private DAOCriteria subCriteria;
// 2015.06.10 H.Mizuno サブクエリ拡張 start
    /** サブクエリで使用するテーブル名に別名を指定する場合に設定する。 */
    private String subAlias;
// 2015.06.10 H.Mizuno サブクエリ拡張 end

    public ExistsSubQuery() {}

    public ExistsSubQuery(SubQueryable subQueryDAO, DAOCriteria subCriteria, boolean negate) {
        this();
        this.negate = negate;
        this.subQueryDAO = subQueryDAO;
        this.subCriteria = subCriteria;
    }

// 2015.06.10 H.Mizuno サブクエリ拡張 start
    public ExistsSubQuery(SubQueryable subQueryDAO, DAOCriteria subCriteria, String subAlias, boolean negate) {
        this(subQueryDAO, subCriteria, negate);
        this.subAlias = subAlias;
    }
// 2015.06.10 H.Mizuno サブクエリ拡張 end

    public SubQueryable getSubQueryDAO() {
        return this.subQueryDAO;
    }

    public DAOCriteria getSubCriteria() {
        return this.subCriteria;
    }

    public void setSubQueryDAO(SubQueryable pSubQuery) {
        this.subQueryDAO = pSubQuery;
    }

    public void setSubCriteria(DAOCriteria pSubCriteria) {
        this.subCriteria = pSubCriteria;
    }
    
    public boolean isNegate() {
        return this.negate;
    }

    public void setNegate(boolean negate) {
        this.negate = negate;
    }
    
// 2015.06.10 H.Mizuno サブクエリ拡張 start
	public String getSubAlias() {
		return subAlias;
	}

	public void setSubAlias(String subAlias) {
		this.subAlias = subAlias;
	}
// 2015.06.10 H.Mizuno サブクエリ拡張 end

    public void writeSubQuerySQL(SQLClauseBuilder parent, StringBuilder sql, List<Object> params) {
        if (isNegate()) {
            sql.append("NOT ");
        }
        sql.append("EXISTS (");
        // null selectFields[] means constant "1" is selected
// 2015.06.10 H.Mizuno サブクエリ拡張 start
//        getSubQueryDAO().buildSubQuerySQL(sql, getSubCriteria(), params, null, null, null);
        getSubQueryDAO().buildSubQuerySQL(sql, getSubCriteria(), params, null, null, this.subAlias);
// 2015.06.10 H.Mizuno サブクエリ拡張 end
        sql.append(")");
    }

}
