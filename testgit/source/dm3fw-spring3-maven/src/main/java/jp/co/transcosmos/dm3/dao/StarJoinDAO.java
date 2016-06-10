/*
 * Copyright (C) 2006 Transcosmos INC.
 * All Rights Reserved.
 */
package jp.co.transcosmos.dm3.dao;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

/**
 * Implements a star style join with a mainDAO in the centre, and many childDAOs joined
 * on foreign key fields.
 * <p>
 * Supports INNER/LEFT/RIGHT JOIN syntax, but only between a center DAO and each of the
 * childDAOs, and all DAOs must implement the SQLClauseBuilder interface. This DAO is 
 * itself an SQLClauseBuilder implementation, so these joins can be nested inside each 
 * other (i.e. A JOIN (B STAR JOIN C,D,E) is possible, as long as it is a valid SQL join).
 * <p>
 * Each component DAO has an alias supplied, which is used to separate the output results
 * from each component DAO inside the JoinResult object. The type of any select query 
 * results is List&lt;JoinResult&gt;, and each JoinResult represents a single row in the output.
 * The JoinResult's getItems() call returns a map, with a single key/valueobject pair for
 * each component dao in the join. Thus, if the join is (A JOIN (B JOIN C,D,E)) as above, then 
 * each JoinResult's getItems() map will have 5 key/valueobject pairs: A, B, C, D and E.
 * 
 * @author <a href="mailto:rick_knowles@hotmail.com">Rick Knowles</a>
 * @version $Id: StarJoinDAO.java,v 1.15 2012/08/01 09:28:36 tanaka Exp $
 */
public class StarJoinDAO implements DAO<JoinResult>, SQLClauseBuilder, SubQueryable, HasDataSource {
    private static final Log log = LogFactory.getLog(StarJoinDAO.class);

    private DataSource dataSource;
    private SQLClauseBuilder mainDAO;
    private String mainAlias;
    private StarJoinDAODescriptor[] childDAOs;
    private boolean allowUnknownCriteriaField;
    private boolean useCountQueryForMaxRows;
    private boolean useHashMapForResults;
    private boolean nullResultIfAllFieldsAreNull;
    
    public void setMainDAO(SQLClauseBuilder mainDAO) {
        this.mainDAO = mainDAO;
    }
    public void setMainAlias(String pMainAlias) {
        this.mainAlias = pMainAlias;
    }
    public void setChildDAOs(List<StarJoinDAODescriptor> childDAOs) {
        this.childDAOs = childDAOs.toArray(new StarJoinDAODescriptor[childDAOs.size()]);
    }
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    public void setAllowUnknownCriteriaField(boolean pAllowUnknownCriteriaField) {
        this.allowUnknownCriteriaField = pAllowUnknownCriteriaField;
    }
    /**
     * If true, when paging results issue a count query to get the max rows instead
     * of iterating to the end. This is a performance optimisation (default is false). 
     */
    public void setUseCountQueryForMaxRows(boolean pUseCountQueryForMaxRows) {
        this.useCountQueryForMaxRows = pUseCountQueryForMaxRows;
    }    
    /**
     * Set true if the join is especially large (default is false, for performance reasons)
     */
    public void setUseHashMapForResults(boolean pUseHashMapForResults) {
        this.useHashMapForResults = pUseHashMapForResults;
    }   
    /**
     * Set true if valueobject should be null if all the component fields for that 
     * valueobject are null. This is usually the case when an outer join has not 
     * matched anything in the outer table. Default of false will insert a blank 
     * valueobject in the JoinResult output if all fields are null.
     */
    public void setNullResultIfAllFieldsAreNull(boolean pNullResultIfAllFieldsAreNull) {
        this.nullResultIfAllFieldsAreNull = pNullResultIfAllFieldsAreNull;
    }
    
    public DataSource getDataSource() {
        if (this.dataSource != null) {
            return this.dataSource;
        }
        DataSource ds = null;
        if (this.mainDAO instanceof HasDataSource) {
            ds = ((HasDataSource) this.mainDAO).getDataSource();
        }
        for (int n = 0; ds == null && n < this.childDAOs.length; n++) {
            if (this.childDAOs[n].getDao() instanceof HasDataSource) {
                ds = ((HasDataSource) this.childDAOs[n].getDao()).getDataSource();
            }
        }
        return ds;
    }
    
    public int getRowCountMatchingFilter(DAOCriteria criteria) {
        criteria = prepareCriteria(criteria, null);
        return getRowCountMatchingFilterInternal(criteria);
    }
    
    protected int getRowCountMatchingFilterInternal(DAOCriteria criteria) {
        long start = System.currentTimeMillis();
        int rowCount = 0;
        List<Object> params = new ArrayList<Object>();
        JdbcTemplate template = new JdbcTemplate(getDataSource());
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT count(1) FROM ");
        addFromClause(sql, "", params);
        DAOUtils.addWhereClauses(this, criteria, "", sql, params, this.allowUnknownCriteriaField);
        DAOUtils.dumpSQL(sql.toString(), params);
        rowCount = template.queryForInt(sql.toString(), params.toArray());
        log.info("Row count query found " + rowCount + " rows in " + 
                (System.currentTimeMillis() - start) + "ms");
        return rowCount;
    }

    @SuppressWarnings("unchecked")
    public List<JoinResult> selectByFilter(DAOCriteria criteria) {
        long start = System.currentTimeMillis();
        criteria = prepareCriteria(criteria, null);
        List<Object> params = new ArrayList<Object>();
        
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ");
        addSelectClause(sql, "", null);
        sql.append(" FROM ");
        addFromClause(sql, "", params);
        
        // Add restrictions as sql
        DAOUtils.addWhereClauses(this, criteria, "", sql, params, 
                this.allowUnknownCriteriaField);
        DAOUtils.addOrderByClauses(this, criteria, "", sql, 
                this.allowUnknownCriteriaField);
        DAOUtils.dumpSQL(sql.toString(), params);
        JdbcTemplate template = new JdbcTemplate(getDataSource());
        if ((criteria != null) && (criteria.getMaxRows() >= 0)) {
            template.setMaxRows(criteria.getMaxRows());
        }

        FragmentPreparedStatementCreator psc = new FragmentPreparedStatementCreator(
                sql.toString(), params);
        FragmentResultSetExtractor<JoinResult> rse = new FragmentResultSetExtractor<JoinResult>(
                getRowMapper("", null, this.nullResultIfAllFieldsAreNull), criteria);
        if (rse.isFragmentMode() && this.useCountQueryForMaxRows) {
            rse.setRowCountHint(getRowCountMatchingFilterInternal(criteria));
        }
        
        List<JoinResult> results = (List<JoinResult>) template.query(psc, psc, rse);
        log.info("SelectByFilter query found " + results.size() + " rows in " + 
                (System.currentTimeMillis() - start) + "ms");
        return results;
    }
    
    public DAOCriteria buildImplicitCriteria() {
    	boolean nested = false;
    	DAOCriteria top = this.mainDAO.buildImplicitCriteria();
        for (int n =0; n < this.childDAOs.length; n++) {
            StarJoinDAODescriptor descriptor = this.childDAOs[n];
            DAOCriteria implicit = descriptor.getDao().buildImplicitCriteria();
            if (top == null) {
            	top = implicit;
            } else if (implicit != null) {
            	if (!nested) {
            		DAOCriteria newTop = new DAOCriteria();
            		newTop.addSubCriteria(top);
            		newTop.addSubCriteria(implicit);
            		top = newTop;
            		nested = true;
            	} else {
            		top.addSubCriteria(implicit);
            	}
            }
        }
		return top;
	}

    public void addSelectClause(StringBuilder sql, String alias, String[] filterFields) {
        int startLength = sql.length();
        this.mainDAO.addSelectClause(sql, this.mainAlias, filterFields); 
        for (int n =0; n < this.childDAOs.length; n++) {
            StarJoinDAODescriptor descriptor = this.childDAOs[n];
            if (descriptor.getDao() != null) {
                if (startLength != sql.length()) {
                    sql.append(", ");
                }
                descriptor.getDao().addSelectClause(sql, descriptor.getAlias(), filterFields); 
            }
        }
    }
    
    public void addFromClause(StringBuilder sql, String alias, List<Object> params) {
        this.mainDAO.addFromClause(sql, this.mainAlias, params);
        for (int n = 0; n < this.childDAOs.length; n++) {
            StarJoinDAODescriptor descriptor = this.childDAOs[n];
            if (descriptor.getDao() != null) {
                int joinType = descriptor.getJoinTypeIndex();
                if (joinType == JoinCondition.RIGHT_JOIN) {
                    sql.append(" RIGHT JOIN ");
                } else if (joinType == JoinCondition.LEFT_JOIN) {
                    sql.append(" LEFT JOIN ");
                } else {
                    sql.append(" INNER JOIN ");
                }
                descriptor.getDao().addFromClause(sql, descriptor.getAlias(), params);
                String joinOnClause = DAOUtils.buildJoinOnCriteria(
                        this.mainAlias, this.mainDAO, 
                        descriptor.getAlias(), descriptor.getDao(), 
                        descriptor.getConditions(), params, this, 
                        this.allowUnknownCriteriaField).trim();
                if (!joinOnClause.equals("")) {
                    sql.append(" ON ").append(joinOnClause);
                }
            }
        }
    }

    public boolean addOneWhereClause(StringBuilder sql, String alias, 
            DAOCriteriaWhereClause where, List<Object> params) {
        if (!this.mainDAO.addOneWhereClause(sql, this.mainAlias, where, params)) {
            for (int n =0; n < this.childDAOs.length; n++) {
                StarJoinDAODescriptor descriptor = this.childDAOs[n];
                if (descriptor.getDao().addOneWhereClause(sql, 
                        descriptor.getAlias(), where, params)) {
                    return true;
                }
            }
            return false;
        } else {
            return true;
        }
    }

    public boolean addOneOrderByClause(StringBuilder sql, String alias, 
            DAOCriteriaOrderByClause orderBy) {
        if (!this.mainDAO.addOneOrderByClause(sql, this.mainAlias, orderBy)) {
            for (int n =0; n < this.childDAOs.length; n++) {
                StarJoinDAODescriptor descriptor = this.childDAOs[n];
                if (descriptor.getDao().addOneOrderByClause(sql, descriptor.getAlias(), orderBy)) {
                    return true;
                }
            }
            return false;
        } else {
            return true;
        }
    }

    public RowMapper getRowMapper(String fieldPrefix, String filterFields[], boolean isLeftJoinOutsideTable) {
        RowMapper mappers[] = new RowMapper[this.childDAOs.length + 1];
        String aliases[] = new String[this.childDAOs.length + 1];
        
        for (int n = 0; n < this.childDAOs.length; n++) {
            StarJoinDAODescriptor descriptor = this.childDAOs[n];
            aliases[n + 1] = descriptor.getAlias();
            mappers[n + 1] = descriptor.getDao().getRowMapper(
                    descriptor.getAlias(), filterFields,
                    this.nullResultIfAllFieldsAreNull);  
        }
        mappers[0] = this.mainDAO.getRowMapper(this.mainAlias, filterFields, 
                this.nullResultIfAllFieldsAreNull);
        aliases[0] = this.mainAlias;
        return new JoinRowMapper(aliases, mappers, mappers.length, this.useHashMapForResults);
    }
    
    public DAOCriteria prepareCriteria(DAOCriteria criteria, String thisAlias) {
        criteria = this.mainDAO.prepareCriteria(criteria, this.mainAlias);
        for (int n = 0; n < this.childDAOs.length; n++) {
            criteria = this.childDAOs[n].getDao().prepareCriteria(criteria, this.childDAOs[n].getAlias());
        }
        return criteria;
    }

    public String lookupAliasDotColumnName(String fieldName, String fieldAlias, String thisAlias) {
    	String out = this.mainDAO.lookupAliasDotColumnName(fieldName, fieldAlias, this.mainAlias);
        for (int n = 0; n < this.childDAOs.length && (out == null); n++) {
            out = this.childDAOs[n].getDao().lookupAliasDotColumnName(
                    fieldName, fieldAlias, this.childDAOs[n].getAlias());
        }
        return out;
    }

    @Override
    public String lookupLabelledName(String fieldName, String fieldAlias, String thisAlias) {
    	String out = this.mainDAO.lookupLabelledName(fieldName, fieldAlias, this.mainAlias);
        for (int n = 0; n < this.childDAOs.length && (out == null); n++) {
            out = this.childDAOs[n].getDao().lookupLabelledName(
                    fieldName, fieldAlias, this.childDAOs[n].getAlias());
        }
        return out;
    }

    public void buildSubQuerySQL(StringBuilder sql, DAOCriteria subCriteria, 
            List<Object> params, String[] selectFields, String[] selectFieldAliases, String thisAlias) {
        subCriteria = prepareCriteria(subCriteria, null);
        sql.append("SELECT ");
        if (selectFields == null) {
            sql.append("1");
        } else {
            for (int n = 0; n < selectFields.length; n++) {
                // find a matching dao, then label the field with that dao and it's alias
                if (n > 0) {
                    sql.append(", ");
                }
                String columnName = lookupAliasDotColumnName(selectFields[n], 
                        (selectFieldAliases == null || selectFieldAliases.length <= n) 
                        ? null : selectFieldAliases[n], thisAlias);
                if (columnName != null) {
                	sql.append(columnName);
                	String label = lookupLabelledName(selectFields[n], 
                            (selectFieldAliases == null || selectFieldAliases.length <= n) 
                            ? null : selectFieldAliases[n], thisAlias);
                	if ((label != null) && !label.equals(columnName)) {
                        sql.append(" AS ").append(label);
                	}
                } else {
                	log.warn("WARNING: Skipping missing column name: " + selectFields[n]);
                }
            }
        }
        sql.append(" FROM ");
        addFromClause(sql, "", params);
        
        // Add restrictions as sql
        if (subCriteria != null) {
            DAOUtils.addWhereClauses(this, subCriteria, "", sql, params, 
                    this.allowUnknownCriteriaField);
        }
    }

    public JoinResult selectByPK(Object pk) {
        throw new RuntimeException("Can't selectByPK from a StarJoinDAO");
    }

    public List<JoinResult> selectByPK(Object[] pks) {
        throw new RuntimeException("Can't selectByPK from a StarJoinDAO");
    }

    // ideally these shouldn't be implemented - this is a backwards compatibility fix
    public Object[] allocatePrimaryKeyIds(int count) {
        throw new RuntimeException("Can't selectByPK from a StarJoinDAO");
    }
    
    public void insert(JoinResult[] toBeInserted) {
        throw new RuntimeException("Can't insert from a StarJoinDAO");
    }
    
    public int update(JoinResult[] toBeUpdated) {
        throw new RuntimeException("Can't update from a StarJoinDAO");
    }
    
    public void delete(JoinResult[] toBeDeleted) {
        throw new RuntimeException("Can't delete from a StarJoinDAO");
    }
    
    public void deleteByPK(Object[] pArg0) {
        throw new RuntimeException("Can't deleteByPK from a StarJoinDAO");
    }
    
    public void deleteByFilter(DAOCriteria criteria) {
        throw new RuntimeException("Can't deleteByFilter from a StarJoinDAO");
    }

    public int updateByCriteria(DAOCriteria criteria, UpdateExpression[] setClauses) {
        throw new RuntimeException("Can't deleteByFilter from a StarJoinDAO");
    }
}
