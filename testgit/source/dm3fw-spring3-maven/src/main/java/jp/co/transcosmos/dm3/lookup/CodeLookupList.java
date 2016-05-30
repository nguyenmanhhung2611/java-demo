/*
 * &copy; Trans-cosmos Inc. 2006, 2007.
 * All rights reserved.
 */
package jp.co.transcosmos.dm3.lookup;

import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Spring configuration class that is used to assign lists of properties to a specific
 * code-lookup label.
 * <p>
 * The "lookups" property is a map, which keys a name to either a Map (key/value pairs),
 * a Collection of Strings (which is parsed as "key:value"), or a CodeLookup object.
 * <p>
 * Using the Collection of Strings version is recommended when using the lookupForEach tag,
 * because the ordering is preserved as declared. This is especially good for combo boxes.
 * <p>
 * The codeLookupManager property defines a CodeLookupManager class that acts as a container
 * for the lookups we defined here.
 * 
 * @author <a href="mailto:rick@knowleses.org">Rick Knowles</a>
 * @version $Id: CodeLookupList.java,v 1.3 2007/05/31 09:02:31 rick Exp $
 */
public class CodeLookupList {
    private static final Log log = LogFactory.getLog(CodeLookupList.class);

    private CodeLookupManager codeLookupManager;
    private Map<String, CodeLookup> lookups;

    public void setCodeLookupManager(CodeLookupManager pCodeLookupManager) {
        this.codeLookupManager = pCodeLookupManager;
        updateLookups();
    }

    /**
     * @deprecated
     */
    public void setCodeLookups(Map<String,CodeLookup> lookups) {
        log.warn("DEPRECATED: The codeLookups attribute is deprecated - use " +
                "lookups with the same argument instead");
        setLookups(lookups);
    }

    /**
     * @deprecated
     */
    public void setListLookups(Map<String,List<String>> lookups) {
        log.warn("DEPRECATED: The listLookups attribute is deprecated - use " +
                "lookups with the same argument instead");
        setLookups(lookups);
    }

    @SuppressWarnings("unchecked")
    public void setLookups(Map<String,?> lookups) {
        this.lookups = new Hashtable<String,CodeLookup>();
        for (Iterator<String> i = lookups.keySet().iterator(); i.hasNext(); ) {
            String key = i.next();
            Object element = lookups.get(key);
            if (element instanceof CodeLookup) {
                this.lookups = (Map<String,CodeLookup>) element;
            } else if (element instanceof Map) {
                Map<?,?> props = (Map<?,?>) element;
                CodeLookup lookup = new CodeLookup();
                for (Iterator<?> j = props.keySet().iterator(); j.hasNext(); ) {
                    Object name = j.next();
                    lookup.addItem("" + name, "" + props.get(name));
                }
                this.lookups.put(key, lookup);
            } else if (element instanceof Collection) {
                Collection<?> props = (Collection<?>) element;
                CodeLookup lookup = new CodeLookup();
                for (Iterator<?> j = props.iterator(); j.hasNext(); ) {
                    String item = "" + j.next();
                    int pos = item.indexOf(':');
                    if (pos == -1) {
                        throw new RuntimeException("Invalid code lookup item: " + item);
                    } else {
                        lookup.addItem(item.substring(0, pos), item.substring(pos + 1));
                    }
                }
                this.lookups.put(key, lookup);
            }
        }
        updateLookups();
    }
    
    private void updateLookups() {
        if ((this.lookups != null) && (this.codeLookupManager != null)) {
            log.info("Adding " + this.lookups.size() + " lookups to CodeLookupManager");
            this.codeLookupManager.registerLookups(this.lookups);
        }        
    }
}
