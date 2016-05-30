package jp.co.transcosmos.dm3.foundation;

import junit.framework.TestCase;

/**
 * @author <a href="mailto:rick_knowles@hotmail.com">Rick Knowles</a>
 * @version $Id: CharacterReplaceTest.java,v 1.1 2007/09/28 08:04:23 rick Exp $
 */
public class CharacterReplaceTest extends TestCase {

    private static final String[][] BAD_MAPPINGS = {
        {"‡@", "1"}, {"‡A", "2"}, {"a", "q"}
    };
    
    public static void testTokenMatch() throws Exception {
        assertEquals(CharacterReplaceHttpServletRequestWrapper.stringReplace(
                "ab‡@‡A1ab", BAD_MAPPINGS, 0), "qb121qb");
    }
}
