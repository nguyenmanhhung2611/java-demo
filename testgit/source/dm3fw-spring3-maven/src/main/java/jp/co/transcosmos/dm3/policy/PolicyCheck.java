/*
 * &copy; Trans-cosmos Inc. 2006, 2007.
 * All rights reserved.
 */
package jp.co.transcosmos.dm3.policy;

import java.util.List;

import javax.servlet.ServletContext;

/**
 * Implementations can be executed by the PolicyChecker to enforce coding
 * conventions on application startup.
 * 
 * @author <a href="mailto:rick@knowleses.org">Rick Knowles</a>
 * @version $Id: PolicyCheck.java,v 1.3 2007/05/31 08:37:45 rick Exp $
 */
public interface PolicyCheck {
    /**
     * Adds a warning message if the application source code violates some 
     * policy.
     */
    public void assertPolicy(ServletContext context, List<String> warningMessages);
}
