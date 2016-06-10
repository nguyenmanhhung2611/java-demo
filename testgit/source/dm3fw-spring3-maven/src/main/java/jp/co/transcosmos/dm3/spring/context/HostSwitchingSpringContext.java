/*
 * &copy; Trans-cosmos Inc. 2006, 2007.
 * All rights reserved.
 */
package jp.co.transcosmos.dm3.spring.context;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import javax.naming.InitialContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.web.context.support.XmlWebApplicationContext;

/**
 * Selects the spring context files most appropriate for this host, and automatically
 * resolves directories into list of the child files under that directory. Effectively,
 * this is the class that makes Spring's configuration easier to use in large groups,
 * allowing loosely coupled config files.
 * <p>
 * See the hostswitch tutorial for further details of how to configure this 
 * context: dm3fw-spring2-blog/docs/hostswitch.html
 * <p>
 * @author <a href="mailto:rick_knowles@hotmail.com">Rick Knowles</a>
 * @version $Id: HostSwitchingSpringContext.java,v 1.5 2007/05/31 07:44:43 rick Exp $
 */
public class HostSwitchingSpringContext extends XmlWebApplicationContext {
    private static final Log log = LogFactory.getLog(HostSwitchingSpringContext.class);
    private static final String SKIP_CHILD_FILES[] = new String [] {"CVS"};
    private static final String FILE_SEPARATOR = System.getProperty("file.separator");
    
    private static final String HOSTSWITCH_KEY = "dm3fw.hostswitch";
    
    protected String[] getSuffices() {
        // Check for JNDI hostswitch settings
        String hostswitch = null;
        try {
            InitialContext initial = new InitialContext();
            hostswitch = (String) initial.lookup("java:/comp/env/" + HOSTSWITCH_KEY);
        } catch (Throwable err) {
        }
        if (hostswitch == null) {
            hostswitch = "";
        }
        log.info("JNDI hostswitch:" + hostswitch);
        
        // Otherwise get from jvm-arg        
        if (hostswitch.equals("")) {
            hostswitch = System.getProperty(HOSTSWITCH_KEY, "");
            log.info("JVM-arg hostswitch:" + hostswitch);
        }
        if (hostswitch.equals("")) {
            hostswitch = "localhost";
            try {
                hostswitch = InetAddress.getLocalHost().getHostName();
            } catch (Throwable err) {}
            
            log.info("Default hostswitch:" + hostswitch);
            return new String[] {"-" + hostswitch};
        }
        // Parse on commas, add hyphens and reverse the order (to fix override hierarchy)
        List<String> tokens = new ArrayList<String>();
        StringTokenizer st = new StringTokenizer(hostswitch, ",");
        while (st.hasMoreTokens()) {
            tokens.add("-" + st.nextToken());
        }
        Collections.reverse(tokens);
        return tokens.toArray(new String[tokens.size()]);
    }
    
    protected String injectSuffix(String filename, String suffix) {
        int pos = filename.lastIndexOf('.');
        if (pos != -1) {
            return filename.substring(0, pos) + suffix + filename.substring(pos);
        } else { 
            return filename + suffix;
        }
    }
    
    protected void loadBeanDefinitions(XmlBeanDefinitionReader reader) throws BeansException, IOException {
        String[] configLocations = getConfigLocations();
        if (configLocations != null) {
            List<Resource> resolved = new ArrayList<Resource>();
            String suffices[] = getSuffices();
            for (int n = 0; n < configLocations.length; n++) {
                // Attempt resource resolution manually, so we can expand directory calls to multiple files
                resolved.clear();
                resolve(reader, configLocations[n], resolved, suffices);
                for (Iterator<Resource> i = resolved.iterator(); i.hasNext(); ) {
                    Resource res = i.next();
                    try {
                        reader.loadBeanDefinitions(res);
                    } catch (BeanDefinitionStoreException err) {
                        if (err.getCause() instanceof FileNotFoundException) {
                            log.warn("WARNING: Can't load config file: " + configLocations[n] +
                                    ", resource=" + res);
                        } else {
                            throw err;
                        }
                    }
                }
            }
        }
    }
    
    /**
     * Resolves directories to all of their child paths, then resolves to Spring's Resource objects
     */
    protected void resolve(XmlBeanDefinitionReader reader, String location, 
            List<Resource> resolved, String matchSuffices[]) throws IOException {
        ResourceLoader resourceLoader = reader.getResourceLoader();
        if (resourceLoader == null) {
            throw new BeanDefinitionStoreException(
                    "Cannot import bean definitions from location [" + location + 
                    "]: no ResourceLoader available");
        }
        
        if ((location != null) && location.endsWith("/")) {
            location = location.substring(0, location.length() - 1);
        }
        
        if (resourceLoader instanceof ResourcePatternResolver) {
            log.debug("Resolver matching resources for " + location);
            Resource[] resources = ((ResourcePatternResolver) resourceLoader).getResources(location);
            for (int n = 0; n < resources.length; n++) {
                resolveOne(reader, location, resources[n], resolved, matchSuffices);
            }
            // Resource pattern matching available.
            if (matchSuffices != null) {
                for (int k = 0; k < matchSuffices.length; k++) {
                    String suffixed = injectSuffix(location, matchSuffices[k]);
                    log.debug("Resolver matching resources for " + suffixed);
                    resources = ((ResourcePatternResolver) resourceLoader).getResources(suffixed);
                    for (int n = 0; n < resources.length; n++) {
                        resolveOne(reader, suffixed, resources[n], resolved, null);
                    }
                }
            }
        } else {
            log.debug("Resolver matching resources for " + location);
            resolveOne(reader, location, resourceLoader.getResource(location), resolved, matchSuffices);
            // Can only load single resources by absolute URL.
            if (matchSuffices != null) {
                for (int k = 0; k < matchSuffices.length; k++) {
                    String suffixed = injectSuffix(location, matchSuffices[k]);
                    log.debug("Resolver matching resources for " + suffixed);
                    resolveOne(reader, location, resourceLoader.getResource(suffixed), resolved, null);
                }
            }
        }
    }
    
    protected void resolveOne(XmlBeanDefinitionReader reader, String location, Resource resource, 
            List<Resource> resolved, String matchSuffices[]) throws IOException {
        if (resource.getFile() != null) {
            if (resource.getFile().isDirectory()) {
                String children[] = resource.getFile().list();
                if (children != null){
                	for (int k = 0; k < children.length; k++) {
                		if (Arrays.binarySearch(SKIP_CHILD_FILES, children[k]) < 0) {
                			// don't recurse the suffix match values
                			resolve(reader, location + FILE_SEPARATOR + children[k], resolved, null); 
                		}
                	}
            	}
                
            } else if (resource.getFile().exists()) {
                resolved.add(resource);
            }
        } else {
            resolved.add(resource);
        }
    }
}
