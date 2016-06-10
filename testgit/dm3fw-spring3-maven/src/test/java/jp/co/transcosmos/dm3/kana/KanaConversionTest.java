package jp.co.transcosmos.dm3.kana;

import junit.framework.TestCase;

/**
 * @author <a href="mailto:rick_knowles@hotmail.com">Rick Knowles</a>
 * @version $Id: KanaConversionTest.java,v 1.1 2007/02/27 01:47:45 rick Exp $
 */
public class KanaConversionTest extends TestCase {

    public static void testTokenMatch() throws Exception {
        assertEquals("ああありck", KanaConverter.convertToHiragana("あアあrick"));
        assertEquals("aaarick", KanaConverter.convertToRomaji("あアあrick"));
        assertEquals("あアありck", KanaConverter.convert("あアあrick", 
                KanaConverter.HIRAGANA, new int[] {KanaConverter.ROMAJI}));
        
        assertFalse(KanaConverter.isAllHiragana("あああああええええカ"));
        assertFalse(KanaConverter.isAllHiragana("あああああカええええ"));
        assertTrue(KanaConverter.isAllHiragana("あああああええええ"));

        assertEquals("ノールズ リック", KanaConverter.convertToZenkaku("no-ruzu rikku"));
        assertEquals("こいたばし　さん", KanaConverter.convertToHiragana("コイタバシ　サン"));
    }
}
