/*
 * &copy; Trans-cosmos Inc. 2006, 2007.
 * All rights reserved.
 */
package jp.co.transcosmos.dm3.policy;

import java.util.List;

import javax.servlet.ServletContext;

/**
 * Gets the list of spring config files from the web.xml, and tries to parse the files,
 * extracting bean ids. If we discover a bad usage, such as:
 * <ol>
 * <li>repeated ids
 * <li>DAOs that use the same table
 * </ol>
 * we report a policy warning with the file name and id of the bean(s) in question
 * <p>
 * Currently unfinished.
 * 
 * @author <a href="mailto:rick@knowleses.org">Rick Knowles</a>
 * @version $Id: SpringDeclarationPolicyCheck.java,v 1.3 2007/05/31 08:37:45 rick Exp $
 */
public class SpringDeclarationPolicyCheck implements PolicyCheck {

    public void assertPolicy(ServletContext context, List<String> warningMessages) {
        // Get the list of spring contexts from the web.xml file
        
        // For each context, get the list of files and paths in that context
        
        // Resolve the beans in the context, and throw warnings on:
        // 1) repeated ids
        // 2) DAOs that use the same table
        // 3) declared classes that don't exist
        // 4) bean ids for commands that don't end in "Command"
        // 5) bean ids for daos that don't end in "DAO"
        // 6) bean ids for mailTemplates that don't end in "MailTemplate"
    }
}
