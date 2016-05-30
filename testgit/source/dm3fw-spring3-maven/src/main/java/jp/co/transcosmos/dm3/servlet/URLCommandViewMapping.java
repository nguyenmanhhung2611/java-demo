/*
 * &copy; Trans-cosmos Inc. 2006, 2007.
 * All rights reserved.
 */
package jp.co.transcosmos.dm3.servlet;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Maps a single command instance as either a filter command or a main command. The attributes
 * of this object define a single url-command mapping, and the options on which it is matched.
 * <p>
 * <ul>
 * <li>If urlPatterns is defined, the url must match one of the defined patterns</li>
 * <li>If exceptionUrlPatterns is defined, the url must not match one of the defined patterns</li>
 * <li>If methods is defined, the request must match one of the defined methods.</li>
 * <li>If filter is true, the command is mapped as a filter, otherwise as a standard command.</li>
 * </ul>
 * <p>
 * Please see the advanced URL mapping tutorial for more information about this class.
 * (dm3fw-spring2-blog/docs/urlmapping.html)
 * 
 * @author <a href="mailto:rick_knowles@hotmail.com">Rick Knowles</a>
 * @version $Id: URLCommandViewMapping.java,v 1.4 2007/05/31 07:44:43 rick Exp $
 */
public class URLCommandViewMapping {

    private String urlPatterns[] = null;
    private String exceptionUrlPatterns[] = null;
    private String methods = null;
    private String defaultCommandName = null;
    private String defaultViewName = null;
    private Map<String,String> namedCommands = null;
    private Map<String,String> namedViews = null;
    private boolean filter = false;
    private boolean supportsAllMethods = false;
    private boolean regardOrder = true;
    private boolean requireAllPatterns = true;
    
    private String[] parsedMethods;
    
    public String[] getUrlPatterns() {
        return urlPatterns;
    }    
    public String[] getExceptionUrlPatterns() {
        return exceptionUrlPatterns;
    }
    public String getDefaultCommandName() {
        return defaultCommandName;
    }
    public boolean isFilter() {
        return filter;
    }
    public String getMethods() {
        return methods;
    }
    public String getDefaultViewName() {
        return defaultViewName;
    }
    public void setDefaultViewName(String toPage) {
        this.defaultViewName = toPage;
    }
    public void setMethods(String methods) {
        this.methods = methods;
    }
    public void setDefaultCommandName(String commandName) {
        this.defaultCommandName = commandName;
    }
    public void setFilter(boolean isFilter) {
        this.filter = isFilter;
    }
    public Map<String,String> getNamedCommands() {
        return namedCommands;
    }
    public Map<String,String> getNamedViews() {
        return namedViews;
    }
    public void setNamedCommands(Map<String,String> namedCommands) {
        this.namedCommands = namedCommands;
    }
    public void setNamedViews(Map<String,String> outputViews) {
        this.namedViews = outputViews;
    }    

    public boolean isRegardOrder() {
		return regardOrder;
	}
	public void setRegardOrder(boolean regardOrder) {
		this.regardOrder = regardOrder;
	}
	public boolean isRequireAllPatterns() {
		return requireAllPatterns;
	}
	public void setRequireAllPatterns(boolean allowOptionalWildcards) {
		this.requireAllPatterns = allowOptionalWildcards;
	}
	
	public void setUrlPatterns(List<String> urlPatterns) {
        if (urlPatterns == null) {
            this.urlPatterns = null;
        } else {
            this.urlPatterns = urlPatterns.toArray(new String[urlPatterns.size()]);
        }
    }

    public void setExceptionUrlPatterns(String exceptionUrlPatterns[]) {
        this.exceptionUrlPatterns = exceptionUrlPatterns;
    }
    public void setExceptionUrlPatterns(List<String> urlPatterns) {
        if (urlPatterns == null) {
            this.exceptionUrlPatterns = null;
        } else {
            this.exceptionUrlPatterns = urlPatterns.toArray(new String[urlPatterns.size()]);
        }
    }

    // Spring convenience methods (to avoid property naming problems)
    public void setUrlPattern(List<String> urlPatterns) {
        setUrlPatterns(urlPatterns);
    }
    public void setExceptionUrlPattern(List<String> exceptionUrlPatterns) {
        setExceptionUrlPatterns(exceptionUrlPatterns);
    }
    
    public boolean supportsMethod(String method) {
        ensureParsedMethods();
        if (this.supportsAllMethods) {
            return true;
        } else {
            return (Arrays.binarySearch(this.parsedMethods, method) >= 0);
        }
    }
    
    public String[] getSupportedMethods() {
        ensureParsedMethods();
        return this.parsedMethods;
    }
    
    private void ensureParsedMethods() {
        synchronized (this) {
            if (this.parsedMethods == null) {
                String methodStr = getMethods();
                if ((methodStr == null) || methodStr.equals("")) {
                    methodStr = isFilter() ? "ALL" : "GET,HEAD,POST";
                }
                
                // Tokenize
                this.supportsAllMethods = false;
                StringTokenizer st = new StringTokenizer(methodStr.toUpperCase(), ",");
                this.parsedMethods = new String[st.countTokens()];
                for (int n = 0; n < this.parsedMethods.length; n++) {
                    this.parsedMethods[n] = st.nextToken();
                    if (this.parsedMethods[n].equals("ALL")) {
                        this.supportsAllMethods = true;
                    }
                }
                Arrays.sort(this.parsedMethods);
            }
        }
    }
    public String toString() {
        String patterns = this.urlPatterns == null ? null : 
            Arrays.asList(this.urlPatterns).toString();
        String exceptionUrlPatterns = this.exceptionUrlPatterns == null ? null : 
            Arrays.asList(this.exceptionUrlPatterns).toString();
        return "[URLCommandViewMapping: patterns=" + patterns + 
                ", exceptions=" + exceptionUrlPatterns + 
                ", defaultCommandName=" + this.defaultCommandName + 
                ", defaultViewName=" + this.defaultViewName +
                ", namedCommands=" + this.namedCommands +
                ", namedViews=" + this.namedViews +
                ", allowOptionalWildcards=" + this.requireAllPatterns +
                ", filter=" + this.filter + "]";
    }
    
    public void validate() {
        if (!this.regardOrder || !this.requireAllPatterns) {
            if (this.urlPatterns != null) {
                // check for any tokens that have no static component (all wildcard tokens)
                for (int n = 0; n < this.urlPatterns.length; n++) {
                    if (CommandURLMapper.hasWildcardOnlyToken(this.urlPatterns[n])) {
                        throw new IllegalArgumentException("If regardOrder=false or requireAllPatterns=false, " +
                                "cannot have any pattern tokens with no static component: " + urlPatterns[n]);
                    }
                }
            }
            if (this.exceptionUrlPatterns != null) {
                // check for any tokens that have no static component (all wildcard tokens)
                for (int n = 0; n < this.exceptionUrlPatterns.length; n++) {
                    if (CommandURLMapper.hasWildcardOnlyToken(this.exceptionUrlPatterns[n])) {
                        throw new IllegalArgumentException("If regardOrder=false or requireAllPatterns=false, " +
                                "cannot have any pattern tokens with no static component: " + exceptionUrlPatterns[n]);
                    }
                }
            }
        }
    }
}
