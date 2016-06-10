/*
 * Copyright (C) 2006 Transcosmos INC.
 * All Rights Reserved.
 */
package jp.co.transcosmos.dm3.dao;

import java.util.Map;

/**
 * Holds the valueobjects that were the result of a join query. The key to the items
 * map is the alias of the DAO specified in the join configuration.
 * 
 * @author <a href="mailto:rick@knowleses.org">Rick Knowles</a>
 * @version $Id: JoinResult.java,v 1.3 2007/06/13 06:32:03 rick Exp $
 * @see JoinDAO, StarJoinDAO
 */
public class JoinResult {

    private Map<String,Object> items;
    
    public JoinResult(Map<String,Object> items) {
        this.items = items;
    }
    
    public Map<String,Object> getItems() {
        return this.items;
    }
}
