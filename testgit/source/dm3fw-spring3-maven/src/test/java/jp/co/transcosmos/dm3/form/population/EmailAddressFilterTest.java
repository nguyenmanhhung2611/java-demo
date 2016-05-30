package jp.co.transcosmos.dm3.form.population;

import junit.framework.TestCase;

/**
 * @author <a href="mailto:rick_knowles@hotmail.com">Rick Knowles</a>
 * @version $Id: EmailAddressFilterTest.java,v 1.1 2007/02/27 01:47:45 rick Exp $
 */
public class EmailAddressFilterTest extends TestCase {

    public static void testMatch() throws Exception {
        assertEquals("rick@knowleses.org", EmailAddressFilter.fixEmail(",rick,@,knowleses,org,"));
    }
}
