/*
 * &copy; Trans-cosmos Inc. 2006, 2007.
 * All rights reserved.
 */
package jp.co.transcosmos.dm3.policy;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Servlet context listener that applies coding policy checks to the application,
 * and lists warnings at startup.
 * 
 * @author <a href="mailto:rick@knowleses.org">Rick Knowles</a>
 * @version $Id: PolicyCheckContextListener.java,v 1.3 2007/05/31 08:37:45 rick Exp $
 */
public class PolicyCheckContextListener implements ServletContextListener {
    private static final Log log = LogFactory.getLog(PolicyCheckContextListener.class);

    public void contextInitialized(ServletContextEvent event) {
        // Get policy class list from the context
        String policyClassList = event.getServletContext().getInitParameter(
                "policyCheckClasses");
        if (policyClassList == null) {
            return;
        }
        String list[] = policyClassList.split(",");
        List<String> warningMessages = new ArrayList<String>();
        for (int n = 0; n < list.length; n++) {
            PolicyCheck check = null;
            try {
                Class<?> policyCheckClass = Class.forName(list[n].trim());
                check = (PolicyCheck) policyCheckClass.newInstance();
            } catch (Throwable err) {
                log.warn("Could not load policy check class: " + list[n].trim());
            }
            
            if (check != null) {
                try {
                    warningMessages.clear();
                    check.assertPolicy(event.getServletContext(), warningMessages);
                    if (!warningMessages.isEmpty()) {
                        log.warn("*****************************************************");
                        log.warn("POLICY WARNINGS (" + warningMessages.size() + 
                                ") from " + list[n].trim());
                        for (Iterator<String> i = warningMessages.iterator(); i.hasNext(); ) {
                            log.warn("WARNING: " + i.next());
                        }                        
                        log.warn("*****************************************************");
                    }
                } catch (Throwable err) {
                    log.error("Error during policy check class: " + list[n].trim(), err);
                }
            }
        }
    }

    public void contextDestroyed(ServletContextEvent event) {}
}
