/*
 * &copy; Trans-cosmos Inc. 2006, 2007.
 * All rights reserved.
 */
package jp.co.transcosmos.dm3.lookup;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

/**
 * Holds a bunch of code/label associations, configured from Spring, and then makes the contents 
 * available in JSP tags.
 * 
 * This class is threadsafe, because it is accessed from JSP pages.
 * 
 * @author <a href="mailto:rick@knowleses.org">Rick Knowles</a>
 * @version $Id: CodeLookupManager.java,v 1.2 2007/05/31 09:02:31 rick Exp $
 */
public class CodeLookupManager {

    private Map<String, CodeLookup> lookups = new Hashtable<String,CodeLookup>();

    public void registerLookups(Map<String,CodeLookup> newLookups) {
        this.lookups.putAll(newLookups);
    }
    
    public Iterator<String> getKeysByLookup(String lookupName) {
        CodeLookup lookup = this.lookups.get(lookupName);
        if (lookup != null) {
            return lookup.keys();
        } else {
            return null;
        }
    }
    
    public String lookupValue(String lookupName, String key) {
        return lookupValue(lookupName, key, null);
    }

// 2015.02.27 H.Mizuno 該当データが無い場合のデフォルト値を設定する機能を追加 start
    public String lookupValueWithDefault(String lookupName, String key, String defaultValue) {
        String value = lookupValue(lookupName, key, null);
        if (value == null) return defaultValue;
        return value;
    }
// 2015.02.27 H.Mizuno 該当データが無い場合のデフォルト値を設定する機能を追加 end    

    public String lookupValue(String lookupName, String key, String wildcards[]) {
        CodeLookup lookup = this.lookups.get(lookupName);
        if (lookup != null) {
            return replace(lookup.get(key), wildcards);
        } else {
            return null;
        }
    }
    
    public static String replace(String lookupValue, String wildcards[]) {
        if ((wildcards != null) && (lookupValue != null)) {
            for (int n = 0; n < wildcards.length; n++) {
                lookupValue = lookupValue.replace("{" + n + "}", wildcards[n]);
            }
        }
        return lookupValue;
    }
}
