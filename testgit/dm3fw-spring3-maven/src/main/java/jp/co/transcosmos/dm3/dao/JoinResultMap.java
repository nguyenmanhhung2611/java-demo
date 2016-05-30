/*
 * Copyright (C) 2006 Transcosmos INC.
 * All Rights Reserved.
 */
package jp.co.transcosmos.dm3.dao;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Attempt at a performance improvement for join result maps. This class simulates a 
 * map with the least possible memory usage, sacrificing a little bit of speed for a 
 * small no of objects.
 * 
 * NOTE: this should be disabled using the JoinDAO.setUseHashMapForResults(true) method
 * if the number of components in the join exceeds 10. This map simulation is memory efficient,
 * but cannot support more than 10 elements.
 * 
 * @author <a href="mailto:rick@knowleses.org">Rick Knowles</a>
 * @version $Id: JoinResultMap.java,v 1.3 2007/06/13 06:32:03 rick Exp $
 * @see JoinResult
 */
public class JoinResultMap implements Map<String,Object> {

    private int position = 0;
    
    private String key1;
    private String key2;
    private String key3;
    private String key4;
    private String key5;
    private String key6;
    private String key7;
    private String key8;
    private String key9;
    private String key10;
    
    private Object value1;
    private Object value2;
    private Object value3;
    private Object value4;
    private Object value5;
    private Object value6;
    private Object value7;
    private Object value8;
    private Object value9;
    private Object value10;
    
    private boolean isFound(Object local, Object candidate, int limit) {
        if (this.position < limit) {
            return false;
        } else if ((local == null) && (candidate == null)) {
            return true;
        } else if ((local == null) || (candidate == null)) {
            return false;
        } else {
            return local.equals(candidate);
        }
    }
    
    public void clear() {
        position = 0;
    }

    public boolean containsKey(Object pKey) {
        if (isFound(this.key1, pKey, 1)) {
            return true;
        } else if (isFound(this.key2, pKey, 2)) {
            return true;
        } else if (isFound(this.key3, pKey, 3)) {
            return true;
        } else if (isFound(this.key4, pKey, 4)) {
            return true;
        } else if (isFound(this.key5, pKey, 5)) {
            return true;
        } else if (isFound(this.key6, pKey, 6)) {
            return true;
        } else if (isFound(this.key7, pKey, 7)) {
            return true;
        } else if (isFound(this.key8, pKey, 8)) {
            return true;
        } else if (isFound(this.key9, pKey, 9)) {
            return true;
        } else if (isFound(this.key10, pKey, 10)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean containsValue(Object pValue) {
        if (isFound(this.value1, pValue, 1)) {
            return true;
        } else if (isFound(this.value2, pValue, 2)) {
            return true;
        } else if (isFound(this.value3, pValue, 3)) {
            return true;
        } else if (isFound(this.value4, pValue, 4)) {
            return true;
        } else if (isFound(this.value5, pValue, 5)) {
            return true;
        } else if (isFound(this.value6, pValue, 6)) {
            return true;
        } else if (isFound(this.value7, pValue, 7)) {
            return true;
        } else if (isFound(this.value8, pValue, 8)) {
            return true;
        } else if (isFound(this.value9, pValue, 9)) {
            return true;
        } else if (isFound(this.value10, pValue, 10)) {
            return true;
        } else {
            return false;
        }
    }

    public Set<Map.Entry<String,Object>> entrySet() {
        throw new RuntimeException("Entry-set not supported on JoinResultMap");
    }

    public Object get(Object pKey) {
        if (isFound(this.key1, pKey, 1)) {
            return this.value1;
        } else if (isFound(this.key2, pKey, 2)) {
            return this.value2;
        } else if (isFound(this.key3, pKey, 3)) {
            return this.value3;
        } else if (isFound(this.key4, pKey, 4)) {
            return this.value4;
        } else if (isFound(this.key5, pKey, 5)) {
            return this.value5;
        } else if (isFound(this.key6, pKey, 6)) {
            return this.value6;
        } else if (isFound(this.key7, pKey, 7)) {
            return this.value7;
        } else if (isFound(this.key8, pKey, 8)) {
            return this.value8;
        } else if (isFound(this.key9, pKey, 9)) {
            return this.value9;
        } else if (isFound(this.key10, pKey, 10)) {
            return this.value10;
        } else {
            return null;
        }
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public Set<String> keySet() {
        return new HashSet<String>(Arrays.asList(new String[] {
                key1, key2, key3, key4, key5, key6, key7, key8, key9, key10,
                }));
    }

    public Object put(String pKey, Object pValue) {
        Object previous = remove(pKey);
        if (this.position == 0) {
            this.key1 = pKey;
            this.value1 = pValue;
        } else if (this.position == 1) {
            this.key2 = pKey;
            this.value2 = pValue;
        } else if (this.position == 2) {
            this.key3 = pKey;
            this.value3 = pValue;
        } else if (this.position == 3) {
            this.key4 = pKey;
            this.value4 = pValue;
        } else if (this.position == 4) {
            this.key5 = pKey;
            this.value5 = pValue;
        } else if (this.position == 5) {
            this.key6 = pKey;
            this.value6 = pValue;
        } else if (this.position == 6) {
            this.key7 = pKey;
            this.value7 = pValue;
        } else if (this.position == 7) {
            this.key8 = pKey;
            this.value8 = pValue;
        } else if (this.position == 8) {
            this.key9 = pKey;
            this.value9 = pValue;
        } else if (this.position == 9) {
            this.key10 = pKey;
            this.value10 = pValue;
        } else {
            throw new RuntimeException("Entry-set only supports 10 rows max. " +
                    "Set useHashMap=true in the joinDAO configuration to use " +
                    "a normal hashmap");
        }
        this.position++;
        return previous;
    }

    public void putAll(Map<? extends String,? extends Object> pT) {
        for (Iterator<? extends String> i = pT.keySet().iterator(); i.hasNext(); ) {
            String key = i.next();
            put(key, pT.get(key));
        }
    }

    public Object remove(Object pKey) {
        Object previous = null;
        boolean deleted = false;
        if (isFound(this.key1, pKey, 1)) {
            previous = this.value1;
            deleted = true;
            this.key1 = this.key2;
            this.value1 = this.value2;
        }
        
        if (isFound(this.key2, pKey, 2)) {
            previous = this.value2;
            deleted = true;
        }
        if (deleted && (position <= 2)) {
            this.key2 = this.key3;
            this.value2 = this.value3;
        }
        
        if (isFound(this.key3, pKey, 3)) {
            previous = this.value3;
            deleted = true;
        }
        if (deleted && (position <= 3)) {
            this.key3 = this.key4;
            this.value3 = this.value4;
        }
        
        if (isFound(this.key4, pKey, 4)) {
            previous = this.value4;
            deleted = true;
        }
        if (deleted && (position <= 4)) {
            this.key4 = this.key5;
            this.value4 = this.value5;
        }
        
        if (isFound(this.key5, pKey, 5)) {
            previous = this.value5;
            deleted = true;
        }
        if (deleted && (position <= 5)) {
            this.key5 = this.key6;
            this.value5 = this.value6;
        }
        
        if (isFound(this.key6, pKey, 6)) {
            previous = this.value6;
            deleted = true;
        }
        if (deleted && (position <= 6)) {
            this.key6 = this.key7;
            this.value6 = this.value7;
        }
        
        if (isFound(this.key7, pKey, 7)) {
            previous = this.value7;
            deleted = true;
        }
        if (deleted && (position <= 7)) {
            this.key7 = this.key8;
            this.value7 = this.value8;
        }
        
        if (isFound(this.key8, pKey, 8)) {
            previous = this.value8;
            deleted = true;
        }
        if (deleted && (position <= 8)) {
            this.key8 = this.key9;
            this.value8 = this.value9;
        }
        
        if (isFound(this.key9, pKey, 9)) {
            previous = this.value9;
            deleted = true;
        }
        if (deleted && (position <= 9)) {
            this.key9 = this.key10;
            this.value9 = this.value10;
        }
        
        if (isFound(this.key10, pKey, 10)) {
            previous = this.value10;
            deleted = true;
        }
        if (deleted) {
            this.key10 = null;
            this.value10 = null;
        }
        
        if (deleted) {
            this.position--;
        }
        return previous;
    }

    public int size() {
        return this.position;
    }

    public Collection<Object> values() {
        return Arrays.asList(new Object[] {
                value1, value2, value3, value4, value5,
                value6, value7, value8, value9, value10,
                });
    }
}
