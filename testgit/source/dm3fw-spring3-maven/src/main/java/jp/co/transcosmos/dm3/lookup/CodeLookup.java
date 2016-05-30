/*
 * &copy; Trans-cosmos Inc. 2006, 2007.
 * All rights reserved.
 */
package jp.co.transcosmos.dm3.lookup;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

/**
 * Internally used representation of a set of key/value pairs used in the code-lookup function.
 * 
 * @author <a href="mailto:rick@knowleses.org">Rick Knowles</a>
 * @version $Id: CodeLookup.java,v 1.2 2007/05/31 09:02:31 rick Exp $
 */
public class CodeLookup {

    private List<String> keys;
    private Map<String,String> values;
    
    public CodeLookup() {
        this.keys = new Vector<String>();
        this.values = new Hashtable<String,String>();
    }
    
    public void setItems(List<String> items) {
        for (Iterator<String> i = items.iterator(); i.hasNext(); ) {
            String item = i.next();
            int pos = item.indexOf(':');
            if (pos == -1) {
                throw new RuntimeException("Invalid code lookup item: " + item);
            } else {
                addItem(item.substring(0, pos), item.substring(pos + 1));
            }
        }
    }
    
    public void addItem(String key, String value) {
        if (!this.values.containsKey(key)) {
            this.keys.add(key);
        }
        this.values.put(key, value);
    }
    
    public Object removeItem(String key) {
        if (!this.values.containsKey(key)) {
            return null;
        } else {
            Object retVal = this.values.get(key);
            this.values.remove(key);
            this.keys.remove(key);
            return retVal;
        }
    }
    
    public Iterator<String> keys() {
        return keys.iterator();
    }
    
    public String get(String key) {
        return values.get(key);
    }
}
