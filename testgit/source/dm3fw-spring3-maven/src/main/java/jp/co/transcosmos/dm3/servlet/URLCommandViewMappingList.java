/*
 * &copy; Trans-cosmos Inc. 2006, 2007.
 * All rights reserved.
 */
package jp.co.transcosmos.dm3.servlet;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Used to associate the URLCommandViewMapping instances in the list with the CommandURLMapper
 * class defined. This object is intended only as an association mechanism, making the declarations
 * a little more convenient and easy to read.
 * 
 * @author <a href="mailto:rick_knowles@hotmail.com">Rick Knowles</a>
 * @version $Id: URLCommandViewMappingList.java,v 1.2 2007/05/31 07:44:43 rick Exp $
 */
public class URLCommandViewMappingList {
    private static final Log log = LogFactory.getLog(URLCommandViewMappingList.class);

    private CommandURLMapper commandURLMapper;
    private List<URLCommandViewMapping> mappings; 

    public CommandURLMapper getCommandURLMapper() {
        return commandURLMapper;
    }

    public void setCommandURLMapper(CommandURLMapper commandURLMapper) {
        this.commandURLMapper = commandURLMapper;
        updateMappings();
    }

    public List<URLCommandViewMapping> getMappings() {
        return mappings;
    }

    public void setMappings(List<URLCommandViewMapping> mappings) {
        this.mappings = mappings;
        updateMappings();
    }
    
    private void updateMappings() {
        if ((this.mappings != null) && (this.commandURLMapper != null)) {
            log.info("Adding " + this.mappings.size() + " mappings to CommandURLMapper");
            for (Iterator<URLCommandViewMapping> i = this.mappings.iterator(); i.hasNext(); ) {
                this.commandURLMapper.registerMapping((URLCommandViewMapping) i.next());
            }
        }        
    }
}
