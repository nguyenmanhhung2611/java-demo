package jp.co.transcosmos.dm3.servlet;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

/**
 * @author <a href="mailto:rick_knowles@hotmail.com">Rick Knowles</a>
 * @version $Id: CommandURLMapperTest.java,v 1.2 2007/04/11 07:11:21 rick Exp $
 */
public class CommandURLMapperTest extends TestCase {

    public static void testTokenMatch() throws Exception {
        CommandURLMapper mapper = new CommandURLMapper(); 
        assertNotNull("Match failure: abc###def###", mapper.tokenMatch("abc###def###", "abc123"));
        assertNotNull("Match failure: ###def###abc", mapper.tokenMatch("###def###abc", "123abc"));
        assertNull("Illegal Match: ###def###abc", mapper.tokenMatch("###def###abc", "123abd"));
        assertNull("Illegal Match: abc###def###", mapper.tokenMatch("abc###def###", "aac123"));
    }
    
    public static void testFullURL() throws Exception {
        CommandURLMapper mapper = new CommandURLMapper(); 
        List<String[]> matchedPatterns = new ArrayList<String[]>();
        assertTrue("Match URL", mapper.treewalkPatternOrdered(
                "/abc###def###/###g###/###hij###abc", "/abc123/tokyo/petabc", 
                matchedPatterns, false, false, true));
        assertTrue("Match size", 3 == matchedPatterns.size());
        assertTrue("Match 1: def=123", matchedPatterns.get(0) instanceof String []);
        assertTrue("Match 1: def=123", ((String []) matchedPatterns.get(0))[0].equals("def"));
        assertTrue("Match 1: def=123", ((String []) matchedPatterns.get(0))[1].equals("123"));
        assertTrue("Match 2: g=tokyo", matchedPatterns.get(1) instanceof String []);
        assertTrue("Match 2: g=tokyo", ((String []) matchedPatterns.get(1))[0].equals("g"));
        assertTrue("Match 2: g=tokyo", ((String []) matchedPatterns.get(1))[1].equals("tokyo"));
        assertTrue("Match 3: hij=abc", matchedPatterns.get(2) instanceof String []);
        assertTrue("Match 3: hij=abc", ((String []) matchedPatterns.get(2))[0].equals("hij"));
        assertTrue("Match 3: hij=abc", ((String []) matchedPatterns.get(2))[1].equals("pet"));
    }
    
    public static void testWelcomePage() throws Exception {
        CommandURLMapper mapper = new CommandURLMapper();
        assertTrue("Match URL", mapper.treewalkPatternOrdered(
                "/abc###def###/###g###/###hij###abc", "/abc123/tokyo", 
                null, true, false, true));
        assertTrue("Match URL", mapper.treewalkPatternOrdered(
                "/abc###def###/###g###/###hij###abc", "/abc123/tokyo/", 
                null, true, false, true));
        assertFalse("Match URL", mapper.treewalkPatternOrdered(
                "/abc###def###/###g###/###hij###abc", "/abc123/tokyo/111", 
                null, true, false, true));
        assertTrue("Match URL", mapper.treewalkPatternOrdered(
                "/abc###def###/###g###/###hij###abc", "/abc123/tokyo/111abc", 
                null, true, false, true));
        assertFalse("Match URL", mapper.treewalkPatternOrdered(
                "/abc###def###/###g###/###hij###abc", "/abc123/tokyo/111/a", 
                null, true, false, true));
        assertTrue("Match URL", mapper.treewalkPatternOrdered(
                "/abc###def###/###g###/###hij###", "/abc123/tokyo/111/a", 
                null, false, true, true));
        assertFalse("Match URL", mapper.treewalkPatternOrdered(
                "/abc###def###/###g###/###hij###abc", "/abc123/tokyo/111/a", 
                null, false, true, true));
        
        // required = false setting test
        assertTrue("Match URL", mapper.treewalkPatternOrdered(
                "/abc###def###/efg###g###/jhi###k###", "/abc123/jhitokyo", 
                null, false, false, false));
        assertFalse("Match URL", mapper.treewalkPatternOrdered(
                "/abc###def###/efg/jhi###k###", "/abc123/jhitokyo", 
                null, false, false, false)); // false because constant efg doesn't match
        
        // regardOrder = false setting test
        assertTrue("Match URL", mapper.treewalkPatternUnordered(
                "/abc###def###/jhi###k###", "/abc123/jhitokyo", 
                null, false, true));
        assertTrue("Match URL", mapper.treewalkPatternUnordered(
                "/jhi###k###/abc###def###", "/abc123/jhitokyo", 
                null, false, true));
        assertFalse("Match URL", mapper.treewalkPatternUnordered(
                "/abc###def###/efg/jhi###k###", "/abc123/jhitokyo", 
                null, false, true)); // false because constant efg doesn't match
        assertFalse("Match URL", mapper.treewalkPatternUnordered(
                "/abc###def###/efg/jhi###k###", "/abc123/jhitokyo", 
                null, false, false)); // false because constant efg doesn't match
        
        assertTrue("Match URL", mapper.treewalkPatternUnordered(
                "/abc###def###/efg/kak/###k###jhi", "/efg/abc123/tokyojhi/kak", 
                null, false, true));
        
        //child/parent tests for ordered not required (but ordered)
        assertTrue("Match URL", mapper.treewalkPatternUnordered(
                "/abc###def###/###hij###abc", "/abc123", 
                null, false, false));
        assertTrue("Match URL", mapper.treewalkPatternUnordered(
                "/abc###def###/###hij###abc", "/abc123/", 
                null, false, false));
        assertTrue("Match URL", mapper.treewalkPatternUnordered(
                "/abc###def###/###hij###abc", "/abc123", 
                null, false, false));
        assertFalse("Match URL", mapper.treewalkPatternUnordered(
                "/abc###def###/###hij###abc", "/abc123/111", 
                null, false, false));
        assertTrue("Match URL", mapper.treewalkPatternUnordered(
                "/abc###def###/###hij###abc", "/abc123/111abc", 
                null, false, false));
        assertFalse("Match URL", mapper.treewalkPatternUnordered(
                "/abc###def###/###hij###abc", "/abc123/111/a", 
                null, true, true));
        assertTrue("Match URL", mapper.treewalkPatternUnordered(
                "/abc###def###/###hij###abc", "/abc123/111/a", 
                null, true, false));
    }
}
