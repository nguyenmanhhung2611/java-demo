/*
 * Copyright (C) 2006 Transcosmos INC.
 * All Rights Reserved.
 */
package jp.co.transcosmos.dm3.dao;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Internal utility methods for building SQL clauses and combining them together
 * when DAOs are joined. Not for external use.
 * 
 * @author <a href="mailto:rick@knowleses.org">Rick Knowles</a>
 * @version $Id: DAOUtils.java,v 1.5 2007/06/13 03:51:36 rick Exp $
 */
public class DAOUtils {
    private static final Log log = LogFactory.getLog(DAOUtils.class);
    
    /**
     * Utility method for DAO classes to add order-by's to the end of their SQL statement. Walks through
     * the criteria object, and delegates each clause to the Joinable supplied.
     */
    public static void addOrderByClauses(SQLClauseBuilder dao, DAOCriteria criteria, String alias, 
    		StringBuilder sql, boolean allowUnmapped) {
    	if (criteria == null) {
    		return;
    	}
    	
        // Add order-bys
        sql.append(" ORDER BY ");
        int startLength = sql.length();
        for (int n = 0; n < criteria.getOrderByClauseCount(); n++) {
            DAOCriteriaOrderByClause orderBy = criteria.getOrderByClause(n);

            if (n > 0) {
                sql.append(", ");
            }
            if (!dao.addOneOrderByClause(sql, alias, orderBy)) {
                if (n > 0) {
                    sql.setLength(sql.length() - 2); // remove comma
                }
                if (!allowUnmapped) {
                    throw new RuntimeException("Error: unknown column name requested for order by: " + 
                            orderBy.getFieldName());
                } else {
                    log.warn("WARNING: Skipping ORDER BY " + orderBy.getFieldName() + 
                    " because there's no mapped field for that name");
                }
            }
        }

        if (sql.length() == startLength) {
            sql.setLength(sql.length() - 10); // remove order by
        }
    }

    public static void addWhereClauses(SQLClauseBuilder dao, DAOCriteria criteria, String alias, 
    		StringBuilder sql, List<Object> params, boolean allowUnmapped) {
    	// Add in implicit criteria
    	DAOCriteria implicit = dao.buildImplicitCriteria();
    	if (criteria == null) {
    		criteria = implicit;
    	} else if (implicit != null) {
    		DAOCriteria top = new DAOCriteria();
    		top.addSubCriteria(criteria);
    		top.addSubCriteria(implicit);
    		criteria = top;
    	}

    	StringBuilder whereClause = new StringBuilder();
        processCriteriaIntoWhereClause(dao, criteria, alias, whereClause, params, allowUnmapped);
        if (whereClause.length() > 0) {
            sql.append(" WHERE ").append(whereClause);
        }
    }

    protected static void processCriteriaIntoWhereClause(SQLClauseBuilder dao, DAOCriteria criteria, 
            String alias, StringBuilder whereClause, List<Object> params, boolean allowUnmapped) {
    	if (criteria == null) {
    		return;
    	}
        for (int n = 0; n < criteria.getWhereClauseCount(); n++) {
            if (whereClause.length() > 0) {
                if (criteria instanceof OrCriteria) {
                    whereClause.append(" OR ");
                } else {
                    whereClause.append(" AND ");
                }
            }
            if (!dao.addOneWhereClause(whereClause, alias, criteria.getWhereClause(n), params)) {
                if (whereClause.length() > 0) {
                    if (criteria instanceof OrCriteria) {
                        whereClause.setLength(whereClause.length() - 4); // remove " OR "
                    } else {
                        whereClause.setLength(whereClause.length() - 5); // remove " AND "
                    };
                }
                // Write out warning or exception
                if (!allowUnmapped) {
                    throw new RuntimeException("Error: unknown column name requested for where clause: " + 
                            criteria.getWhereClause(n).getFieldName());
                } else {
                    log.warn("WARNING: Skipping WHERE " + criteria.getWhereClause(n).getFieldName() + 
                            " because there's no mapped field for that name");
                }
            }
        }
        for (int n = 0; n < criteria.getSubQueryCount(); n++) {
            DAOCriteriaSubQuery subQuery = criteria.getSubQuery(n);
            
            StringBuilder thisClause = new StringBuilder();
            subQuery.writeSubQuerySQL(dao, thisClause, params);
            if (thisClause.length() > 0) {
                if (whereClause.length() == 0) {
                    // do nothing
                } else if (criteria instanceof OrCriteria) {
                    whereClause.append(" OR ");
                } else {
                    whereClause.append(" AND ");
                }
                whereClause.append(thisClause.toString());
            }
        }
        for (int n = 0; n < criteria.getSubCriteriaCount(); n++) {
        	StringBuilder thisClause = new StringBuilder();
            processCriteriaIntoWhereClause(dao, criteria.getSubCriteria(n), alias, 
                    thisClause, params, allowUnmapped);
            if (thisClause.length() > 0) {
                if (whereClause.length() == 0) {
                    whereClause.append('(');
                } else if (criteria instanceof OrCriteria) {
                    whereClause.append(" OR (");
                } else {
                    whereClause.append(" AND (");
                }
                whereClause.append(thisClause.toString()).append(")");
            }
        }
    }

    public static String buildJoinOnCriteria(String alias1, SQLClauseBuilder joinable1, 
            String alias2, SQLClauseBuilder joinable2, OnClause conditions[], 
            List<Object> params, SQLClauseBuilder fixedCriteriaResolverDAO,
            boolean allowUnmapped) {
        StringBuffer out = new StringBuffer();
        if (conditions != null) {
            for (int n = 0; n < conditions.length; n++) {
                if (out.length() > 0) {
                    out.append(" AND ");
                }
            	if (conditions[n] instanceof JoinCondition) {
            		JoinCondition jc = (JoinCondition) conditions[n];
                    String column1 = joinable1.lookupAliasDotColumnName(jc.getField1(), 
                    		jc.getAlias1(), alias1);
                    String column2 = joinable2.lookupAliasDotColumnName(jc.getField2(), 
                    		jc.getAlias2(), alias2);
                    out.append(column1).append(" = ").append(column2);
            	} else if (conditions[n] instanceof DAOCriteria) {
            		// try to render the criteria here
            		DAOCriteria crit = (DAOCriteria) conditions[n];
            		if (crit instanceof OrCriteria) {
            			out.append("(");
            		}
                    StringBuilder subClause = new StringBuilder();
            		processCriteriaIntoWhereClause(fixedCriteriaResolverDAO, crit, 
            				null, subClause, params, allowUnmapped);
            		out.append(subClause.toString());
            		if (crit instanceof OrCriteria) {
            			out.append(")");
            		}
            	} else {
            		throw new RuntimeException("Unknown join condition type: " + conditions[n]);
            	}
            }
        }
        return out.toString();
    }

    public static int getJoinType(OnClause conditions[]) {
        int type = JoinCondition.INNER_JOIN;
        for (int n = 0; n < conditions.length; n++) {
        	if (!(conditions[n] instanceof JoinCondition)) {
        		continue; // ignore anything not a JoinCondition instance (eg hard where clause)
        	}
    		JoinCondition jc = (JoinCondition) conditions[n];        	
            if (jc.getJoinTypeIndex() == JoinCondition.INNER_JOIN) {
                continue; // INNER_JOIN is only available if all joins are inner
            } else if (type != JoinCondition.INNER_JOIN) {
                if (type != jc.getJoinTypeIndex()) {
                    throw new RuntimeException("Illegal join syntax (conflicting LEFT/RIGHT joins");
                }
            } else {
                type = jc.getJoinTypeIndex();
            }
        }
        return type;
    }
    
    public static void dumpSQL(String sql, List<Object> params) {
        log.info("Issuing SQL: " + sql);
        int n = 0;
        if (params != null) {
            for (Iterator<Object> i = params.iterator(); i.hasNext(); n++) {
                Object param = i.next();
                log.info("Parameter " + (n+1) + ": " + param + (param != null ? 
                        " type=" + param.getClass().getName() : ""));
            }
        }        
    }
    

    /**
     * 重複 alias 名のリネーム処理<br/>
     * <br/>
     * 指定された場所の alias　名が、その場所より前と重複している場合、重複名を変更する。<br/>
     * このメソッドは、配列の末尾に値を設定する都度実行され、追加された alias 名が重複して
     * いないかをチェックする。　よって、追加位置よる前の場所では重複は存在しない事が前提となる。<br/>
     * <br/>
     * 例えば、ABCDEFG,ABCDEFG で、checkIdx = 1 の場合、ABCDEFG,ABCD000 となる。<br/>
     * さらに、checkIdx = 2 で ABCDEFG を追加すると、ABCDEFG,ABCD000,ABCD001 となる。<br/>
     * <br/>
     * @param aliases 別名が格納されている配列
     * @param チェック位置（ 0 ～）
     */
    public static void dupliateAliasRename(String[] aliases, int checkIdx){
        boolean found = true;
        while (found) {
            found = false;
            for (int k = 0; (k < checkIdx) && !found; k++) {
                found = aliases[k].equalsIgnoreCase(aliases[checkIdx]);
            }
            if (found) {
                String lastThree = aliases[checkIdx].substring(aliases[checkIdx].length() - 3);
                int index = 0;
                try {
                    index = Integer.parseInt(lastThree);
                    index++;
                } catch (NumberFormatException err) {
                    index = 0;
                }
                aliases[checkIdx] = aliases[checkIdx].substring(0, aliases[checkIdx].length() - 3) + 
                        (index < 10 ? ("00" + index) : 
                            (index < 100 ? ("0" + index) : index));
            }
        }

    }

    
//    public static Connection unwrapToRootConnection(Connection connection) {
//        try {
//            if (RequestScopeConnection.class.isAssignableFrom(connection.getClass())) {
//                Connection delegate = ((RequestScopeConnection) connection).getDelegate();
//                if (delegate != null) {
//                    log.info("Connection unwrapped to: " + delegate.getClass().getName());
//                    return unwrapToRootConnection(delegate);
//                } else {
//                    return connection;
//                }
//            } else if (connection.getClass().getName().startsWith("org.apache.commons.dbcp.")) {
//                Method meth = Class.forName("org.apache.commons.dbcp.DelegatingConnection")
//                        .getMethod("getInnermostDelegate", new Class[0]);
//                Connection delegate = (Connection) meth.invoke(connection, new Object[0]);
//                if (delegate != null) {
//                    log.info("Connection unwrapped to: " + delegate.getClass().getName());
//                    return unwrapToRootConnection(delegate);
//                } else {
//                    return connection;
//                }
//            }
//        } catch (Throwable err) {
//            log.info("Error walking to get default connection", err);
//        }
//        return connection;
//    }
}
