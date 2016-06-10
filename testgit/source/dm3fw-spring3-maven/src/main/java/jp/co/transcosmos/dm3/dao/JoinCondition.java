/*
 * Copyright (C) 2006 Transcosmos INC.
 * All Rights Reserved.
 */
package jp.co.transcosmos.dm3.dao;

/**
 * Holds the mapping details for building a join clause between two DAOs. This element is
 * used internally only by the DAOUtils class. 
 * 
 * @author <a href="mailto:rick@knowleses.org">Rick Knowles</a>
 * @version $Id: JoinCondition.java,v 1.5 2007/06/13 06:32:03 rick Exp $
 */
public class JoinCondition implements OnClause {
    public static final int INNER_JOIN = 1;
    public static final int LEFT_JOIN = 2;
    public static final int RIGHT_JOIN = 3;
    
    private String alias1;
    private String alias2;
    private String field1;
    private String field2;
    private int joinType = INNER_JOIN;
    
    public String getField1() {
        return this.field1;
    }
    public String getField2() {
        return this.field2;
    }
    public int getJoinTypeIndex() {
        return this.joinType;
    }
    public String getAlias1() {
		return alias1;
	}
	public void setAlias1(String alias1) {
		this.alias1 = alias1;
	}
	public String getAlias2() {
		return alias2;
	}
	public void setAlias2(String alias2) {
		this.alias2 = alias2;
	}
	public void setField1(String pField1) {
        this.field1 = pField1;
    }
    public void setField2(String pField2) {
        this.field2 = pField2;
    }
    public void setJoinType(String pJoinType) {
        if (pJoinType != null) {
            this.joinType = lookupJoinType(pJoinType);
        }
    }
	
	public static int lookupJoinType(String typeString) {
        typeString = typeString.toLowerCase().trim();
        if (typeString.equals("inner") || typeString.equals("inner join")) {
            return INNER_JOIN;
        } else if (typeString.equals("left") || typeString.equals("left join")) {
            return LEFT_JOIN;
        } else if (typeString.equals("right") || typeString.equals("right join")) {
            return RIGHT_JOIN;
        } else {
            throw new RuntimeException("Unknown join type: " + typeString);
        }
    }
}
