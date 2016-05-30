package jp.co.transcosmos.dm3.foundation;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * Replaces characters inside any calls to get parameter, which does a kind
 * of input-side clean-up. Useful for avoiding encoding problems with 
 * unmappable characters. 
 * 
 * @author <a href="mailto:rick@knowleses.org">Rick Knowles</a>
 * @version $Id: CharacterReplaceHttpServletRequestWrapper.java,v 1.3 2012/08/01 09:28:36 tanaka Exp $
 */
public class CharacterReplaceHttpServletRequestWrapper extends
        HttpServletRequestWrapper {

    private String[][] replacementTable;
    
    /**
     * Needs a 2 column table of mapping strings to replace from and to.
     */
    public CharacterReplaceHttpServletRequestWrapper(HttpServletRequest request, 
            String replacementTable[][]) {
        super(request);
        this.replacementTable = replacementTable;
    }

    public String getParameter(String name) {
        return stringReplace(super.getParameter(name), this.replacementTable, 0);
    }

    @Override
// 2013.10.18 H.Mizuno Servlet API 3.0 対応　(Tomcat7、Weblogic12c..） start
//    public Map<?,?> getParameterMap() {
//    Map<?,?> values = super.getParameterMap();
    public Map<String, String[]> getParameterMap() {
    	Map<String, String[]> values = super.getParameterMap();
// 2013.10.18 H.Mizuno Servlet API 3.0 対応　(Tomcat7、Weblogic12c..） end
    	if ((values == null) || (this.replacementTable == null)){
            return values;
        } else {
            Map<String,String[]> replaced = new Hashtable<String,String[]>();
            for (Iterator<?> i = values.keySet().iterator(); i.hasNext(); ) {
                String name = (String) i.next();
                String valueArr[] = (String []) values.get(name);
                if (valueArr != null) {
                    for (int n = 0; n < valueArr.length; n++) {
                        valueArr[n] = stringReplace(valueArr[n], this.replacementTable, 0);
                    }
                }
                replaced.put(name, valueArr);
            }
            return replaced;
        }
    }

    public String[] getParameterValues(String name) {
        String valueArr[] = super.getParameterValues(name);
        if (valueArr != null) {
            for (int n = 0; n < valueArr.length; n++) {
                valueArr[n] = stringReplace(valueArr[n], this.replacementTable, 0);
            }
        }
        return valueArr;
    }

    public static String stringReplace(String input, String table[][], int fromIndex) {
        if ((input == null) || (table == null) || (table.length <= fromIndex)) {
            return input;
        } else {
            int pos = input.indexOf(table[fromIndex][0]);
            if (pos == -1) {
                return stringReplace(input, table, fromIndex + 1);
            } else {
                return stringReplace(input.substring(0, pos), table, fromIndex + 1) + 
                       table[fromIndex][1] +
                       stringReplace(input.substring(pos + 
                               table[fromIndex][0].length()), table, fromIndex);
            }
        }
    }

    /**
     * @deprecated
     */
    public boolean isRequestedSessionIdFromUrl() {
        return super.isRequestedSessionIdFromUrl();
    }

    /**
     * @deprecated
     */
    public String getRealPath(String pArg0) {
        return super.getRealPath(pArg0);
    } 
    
}
