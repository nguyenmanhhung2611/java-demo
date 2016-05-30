package jp.co.transcosmos.dm3.kana;

import junit.framework.TestCase;

/**
 * @author <a href="mailto:rick_knowles@hotmail.com">Rick Knowles</a>
 * @version $Id: KanaConversionTest.java,v 1.1 2007/02/27 01:47:45 rick Exp $
 */
public class KanaConversionTest extends TestCase {

    public static void testTokenMatch() throws Exception {
        assertEquals("��������ck", KanaConverter.convertToHiragana("���A��rick"));
        assertEquals("aaarick", KanaConverter.convertToRomaji("���A��rick"));
        assertEquals("���A����ck", KanaConverter.convert("���A��rick", 
                KanaConverter.HIRAGANA, new int[] {KanaConverter.ROMAJI}));
        
        assertFalse(KanaConverter.isAllHiragana("�������������������J"));
        assertFalse(KanaConverter.isAllHiragana("�����������J��������"));
        assertTrue(KanaConverter.isAllHiragana("������������������"));

        assertEquals("�m�[���Y ���b�N", KanaConverter.convertToZenkaku("no-ruzu rikku"));
        assertEquals("�������΂��@����", KanaConverter.convertToHiragana("�R�C�^�o�V�@�T��"));
    }
}
