/*
 * &copy; Trans-cosmos Inc. 2006, 2007.
 * All rights reserved.
 */
package jp.co.transcosmos.dm3.kana;

import java.util.Arrays;
import java.util.Comparator;

/**
 * A kana conversion tool which converts japanese characters between hiragana,
 * zenkaku (full-width katakana), hankaku (half-width katakana), and 
 * romaji. Uses a hard coded lookup table internal to the class to perform
 * the conversions.
 * 
 * @author <a href="mailto:rick@knowleses.org">Rick Knowles</a>
 * @version $Id: KanaConverter.java,v 1.3 2007/05/31 08:39:27 rick Exp $
 */
public class KanaConverter {
    
    public static String convertToHiragana(String input) {
        return convert(input, HIRAGANA, FROM_ALL);
    }
    
    public static String convertToZenkaku(String input) {
        return convert(input, ZENKAKU, FROM_ALL);
    }
    
    public static String convertToHankaku(String input) {
        return convert(input, HANKAKU, FROM_ALL);
    }
    
    public static String convertToRomaji(String input) {
        return convert(input, ROMAJI, FROM_ALL);
    }
    
    /**
     * Workhorse method (the other convert methods are wrappers for this). 
     * This actually uses the lookup table to change from the 
     */
    public static String convert(String input, int to, int supportedFrom[]) {
        if (input == null) {
            return input;
        } else if (supportedFrom == null) {
            supportedFrom = FROM_ALL;
        } else if (supportedFrom.length == 0) {
            return input;
        }
        
        StringBuilder builder = new StringBuilder();
        for (int index = 0; index < input.length(); index++) {
            String replace = null;
            int scanSize = Math.min(MAX_LOOKUP_FIELD_LENGTH, (input.length() - index));
            for ( ; (scanSize > 0) && (replace == null); scanSize--) {
                String subtext = input.substring(index, index + scanSize);
                
                // Check supported lookups
                for (int k = 0; (k < supportedFrom.length) && (replace == null); k++) {
                    if (supportedFrom[k] == HIRAGANA) {
                        replace = search(HIRAGANA_SORTED_LOOKUPS, HIRAGANA, to, subtext);
                    } else if (supportedFrom[k] == ZENKAKU) {
                        replace = search(ZENKAKU_SORTED_LOOKUPS, ZENKAKU, to, subtext);
                    } else if (supportedFrom[k] == HANKAKU) {
                        replace = search(HANKAKU_SORTED_LOOKUPS, HANKAKU, to, subtext);
                    } else if (supportedFrom[k] == ROMAJI) {
                        replace = search(ROMAJI_SORTED_LOOKUPS, ROMAJI, to, subtext);
                    }
                }
            }
            if (replace != null) {
                builder.append(replace);
                index += scanSize; // normal loop inc will add 1, but scansize is over-decremented by 1
            } else {
                builder.append(input.charAt(index));
            }
        }
        return builder.toString();
    }
    
    private static String search(String lookups[][], int from, int to, String text) {
        int position = Arrays.binarySearch(
                lookups, null, new KanaArrayComparator(from, text));
        if (position >= 0) {
            return lookups[position][to];
        }
        return null;
    }
    
    public static boolean isAllHiragana(String input) {
        return isAllOneType(input, HIRAGANA);
    }
    
    public static boolean isAllZenkaku(String input) {
        return isAllOneType(input, ZENKAKU);
    }
    
    public static boolean isAllHankaku(String input) {
        return isAllOneType(input, HANKAKU);
    }
    
    public static boolean isAllRomaji(String input) {
        return isAllOneType(input, ROMAJI);
    }
    
    /**
     * Workhorse method (the other convert methods are wrappers for this). 
     * This actually uses the lookup table to change from the 
     */
    public static boolean isAllOneType(String input, int type) {
        if (input == null) {
            return true;
        }
        for (int index = 0; index < input.length(); index++) {
            String replace = null;
            int scanSize = Math.min(MAX_LOOKUP_FIELD_LENGTH, (input.length() - index));
            for ( ; (scanSize > 0) && (replace == null); scanSize--) {
                String subtext = input.substring(index, index + scanSize);
                
                // Check supported lookups
                if (type == HIRAGANA) {
                    replace = search(HIRAGANA_SORTED_LOOKUPS, HIRAGANA, HIRAGANA, subtext);
                } else if (type == ZENKAKU) {
                    replace = search(ZENKAKU_SORTED_LOOKUPS, ZENKAKU, ZENKAKU, subtext);
                } else if (type == HANKAKU) {
                    replace = search(HANKAKU_SORTED_LOOKUPS, HANKAKU, HANKAKU, subtext);
                } else if (type == ROMAJI) {
                    replace = search(ROMAJI_SORTED_LOOKUPS, ROMAJI, ROMAJI, subtext);
                }
            }
            if (replace == null) {
                return false;
            } else {
                index += scanSize; // normal loop inc will add 1, but scansize is over-decremented by 1
            }
        }
        return true;
    }
    
    public static final int HIRAGANA = 0;
    public static final int ZENKAKU = 1;
    public static final int HANKAKU = 2;
    public static final int ROMAJI = 3;
    public static final int[] FROM_ALL = {HIRAGANA, ZENKAKU, HANKAKU, ROMAJI};
    public static final int[] FROM_NON_ROMAJI = {HIRAGANA, ZENKAKU, HANKAKU};
    
    private static final String HIRAGANA_SORTED_LOOKUPS[][];
    private static final String ZENKAKU_SORTED_LOOKUPS[][];
    private static final String HANKAKU_SORTED_LOOKUPS[][];
    private static final String ROMAJI_SORTED_LOOKUPS[][];
    
    private static final int MAX_LOOKUP_FIELD_LENGTH = 4;
    
    /**
     * Simple utility comparator for sorting the arrays in prep for a binary search
     */
    private static class KanaArrayComparator implements Comparator<String[]> {

        private int sortIndex;
        private String nullValue;
        
        KanaArrayComparator(int sortIndex) {
            this.sortIndex = sortIndex;
        }
        
        KanaArrayComparator(int sortIndex, String nullValue) {
            this.sortIndex = sortIndex;
            this.nullValue = nullValue;
        }
        
        public int compare(String[] array1, String[] array2) {
            String one = (array1 == null ? nullValue : array1[this.sortIndex]);
            String two = (array2 == null ? nullValue : array2[this.sortIndex]);
            return two.compareTo(one);
        }
    }
    
    ////////////////////////////////////////////////////////////////////////////////////
    // RAW DATA TABLE
    ////////////////////////////////////////////////////////////////////////////////////
    
    // columns: 1 = hiragana, 2 = zenkaku, 3 = hankaku, 4 = romaji
    private static final String RAW_LOOKUPS[][] = {
		{"�@", "�@", "�@", " "}, // zenkaku space
		{" ", " ", " ", " "}, // hankaku space
		{"�[", "�[", "�", "-"},

        { "��" , "�A", "�", "a"},
        { "��" , "�C", "�", "i"},
        { "��" , "�E", "�", "u"},
        { "��" , "�G", "�", "e"},
        { "��" , "�I", "�", "o"},
        { "��" , "�J", "�", "ka"},
        { "��" , "�L", "�", "ki"},
        { "��" , "�N", "�", "ku"},
        { "��" , "�P", "�", "ke"},
        { "��" , "�R", "�", "ko"},
        { "��" , "�T", "�", "sa"},
        { "��" , "�V", "�", "si"},
        { "��" , "�V", "�", "shi"},
        { "��" , "�X", "�", "su"},
        { "��" , "�Z", "�", "se"},
        { "��" , "�\", "�", "so"},
        { "��" , "�^", "�", "ta"},
        { "��" , "�`", "�", "ti"},
        { "��" , "�`", "�", "chi"},
        { "��" , "�c", "�", "tsu"},
        { "��" , "�e", "�", "te"},
        { "��" , "�g", "�", "to"},
        { "��" , "�i", "�", "na"},
        { "��" , "�j", "�", "ni"},
        { "��" , "�k", "�", "nu"},
        { "��" , "�l", "�", "ne"},
        { "��" , "�m", "�", "no"},
        { "��" , "�n", "�", "ha"},
        { "��" , "�q", "�", "hi"},
        { "��" , "�t", "�", "hu"},
        { "��" , "�w", "�", "he"},
        { "��" , "�z", "�", "ho"},
        { "��" , "�}", "�", "ma"},
        { "��" , "�~", "�", "mi"},
        { "��" , "��", "�", "mu"},
        { "��" , "��", "�", "me"},
        { "��" , "��", "�", "mo"},
        { "��" , "��", "�", "ya"},
        { "��" , "��", "�", "yu"},
        { "��" , "��", "�", "yo"},
        { "��" , "��", "�", "ra"},
        { "��" , "��", "�", "ri"},
        { "��" , "��", "�", "ru"},
        { "��" , "��", "�", "re"},
        { "��" , "��", "�", "ro"},
        { "��" , "��", "�", "wa"},
        { "��" , "��", "�", "wo"},
        { "��" , "��", "�", "nn"},


        { "��" , "�K", "��", "ga"},
        { "��" , "�M", "��", "gi"},
        { "��" , "�O", "��", "gu"},
        { "��" , "�Q", "��", "ge"},
        { "��" , "�S", "��", "go"},
        { "��" , "�U", "��", "za"},
        { "��" , "�U", "��", "ja"},
        { "��" , "�W", "��", "zi"},
        { "��" , "�W", "��", "ji"},
        { "��" , "�Y", "��", "zu"},
        { "��" , "�Y", "��", "ju"},
        { "��" , "�[", "��", "ze"},
        { "��" , "�[", "��", "je"},
        { "��" , "�]", "��", "zo"},
        { "��" , "�]", "��", "jo"},
        { "��" , "�_", "��", "da"},
        { "��" , "�a", "��", "di"},
        { "��" , "�d", "��", "du"},
        { "��" , "�f", "��", "de"},
        { "��" , "�h", "��", "do"},
        { "��" , "�o", "��", "ba"},
        { "��" , "�r", "��", "bi"},
        { "��" , "�u", "��", "bu"},
        { "��" , "�x", "��", "be"},
        { "��" , "�{", "��", "bo"},
        { "��" , "�p", "��", "pa"},
        { "��" , "�s", "��", "pi"},
        { "��" , "�v", "��", "pu"},
        { "��" , "�y", "��", "pe"},
        { "��" , "�|", "��", "po"},


        { "����" , "�b�J", "��", "kka"},
        { "����" , "�b�L", "��", "kki"},
        { "����" , "�b�N", "��", "kku"},
        { "����" , "�b�P", "��", "kke"},
        { "����" , "�b�R", "��", "kko"},
        { "����" , "�b�T", "��", "ssa"},
        { "����" , "�b�V", "��", "ssi"},
        { "����" , "�b�V", "���", "sshi"},
        { "����" , "�b�X", "��", "ssu"},
        { "����" , "�b�Z", "��", "sse"},
        { "����" , "�b�\", "��", "sso"},
        { "����" , "�b�^", "��", "tta"},
        { "����" , "�b�`", "��", "tti"},
        { "����" , "�b�`", "��", "cchi"},
        { "����" , "�b�c", "��", "ttu"},
        { "����" , "�b�e", "��", "tte"},
        { "����" , "�b�g", "��", "tto"},
        { "����" , "�b�n", "��", "hha"},
        { "����" , "�b�q", "��", "hhi"},
        { "����" , "�b�t", "��", "hhu"},
        { "����" , "�b�w", "��", "hhe"},
        { "����" , "�b�z", "��", "hho"},
        { "����" , "�b�}", "��", "mma"},
        { "����" , "�b�~", "��", "mmi"},
        { "����" , "�b��", "��", "mmu"},
        { "����" , "�b��", "��", "mme"},
        { "����" , "�b��", "��", "mmo"},
        { "����" , "�b��", "��", "yya"},
        { "����" , "�b��", "��", "yyu"},
        { "����" , "�b��", "��", "yyo"},
        { "����" , "�b��", "��", "rra"},
        { "����" , "�b��", "��", "rri"},
        { "����" , "�b��", "��", "rru"},
        { "����" , "�b��", "��", "rre"},
        { "����" , "�b��", "��", "rro"},
        { "����" , "�b��", "��", "wwa"},
        { "����" , "�b��", "��", "wwo"},


        { "����" , "�b�K", "���", "gga"},
        { "����" , "�b�M", "���", "ggi"},
        { "����" , "�b�O", "���", "ggu"},
        { "����" , "�b�Q", "���", "gge"},
        { "����" , "�b�S", "���", "ggo"},
        { "����" , "�b�U", "���", "zza"},
        { "����" , "�b�W", "���", "zzi"},
        { "����" , "�b�W", "���", "jji"},
        { "����" , "�b�Y", "���", "zzu"},
        { "����" , "�b�[", "���", "zze"},
        { "����" , "�b�]", "���", "zzo"},
        { "����" , "�b�_", "���", "dda"},
        { "����" , "�b�a", "���", "ddi"},
        { "����" , "�b�d", "���", "ddu"},
        { "����" , "�b�f", "���", "dde"},
        { "����" , "�b�h", "���", "ddo"},
        { "����" , "�b�o", "���", "bba"},
        { "����" , "�b�r", "���", "bbi"},
        { "����" , "�b�u", "���", "bbu"},
        { "����" , "�b�x", "���", "bbe"},
        { "����" , "�b�{", "���", "bbo"},


        { "����" , "�b�p", "���", "ppa"},
        { "����" , "�b�s", "���", "ppi"},
        { "����" , "�b�v", "���", "ppu"},
        { "����" , "�b�y", "���", "ppe"},
        { "����" , "�b�|", "���", "ppo"},


        { "��" , "�@", "�", "xa"},
        { "��" , "�B", "�", "xi"},
        { "��" , "�D", "�", "xu"},
        { "��" , "�F", "�", "xe"},
        { "��" , "�H", "�", "xo"},
        { "��" , "�@", "�", "la"},
        { "��" , "�B", "�", "li"},
        { "��" , "�D", "�", "lu"},
        { "��" , "�F", "�", "le"},
        { "��" , "�H", "�", "lo"},
        { "��" , "��", "��", "xka"},
        { "��" , "��", "��", "xwa"},
        { "��" , "��", "�", "xya"},
        { "��" , "��", "�", "xyu"},
        { "��" , "��", "�", "xyo"},
        { "��" , "�b", "�", "xtu"},
        { "��" , "�b", "�", "ltu"},

        { "���ӂ�" , "�b�t�@", "�̧", "ffa"},
        { "���ӂ�" , "�b�t�B", "�̨", "ffi"},
        { "���ӂ�" , "�b�t�F", "�̪", "ffe"},
        { "���ӂ�" , "�b�t�H", "�̫", "ffo"},


        { "����" , "�V��", "��", "sha"},
        { "����" , "�V��", "��", "shu"},
        { "����" , "�V�F", "��", "she"},
        { "����" , "�V��", "��", "sho"},

        { "�ɂ�" , "�j��", "Ƭ", "nya"},
        { "�ɂ�" , "�j�B", "ƨ", "nyi"},
        { "�ɂ�" , "�j��", "ƭ", "nyu"},
        { "�ɂ�" , "�j�F", "ƪ", "nye"},
        { "�ɂ�" , "�j��", "Ʈ", "nyo"},

        { "������" , "�b�V��", "���", "ssya"},
        { "������" , "�b�V��", "���", "ssha"},
        { "������" , "�b�V�B", "���", "ssyi"},
        { "������" , "�b�V�B", "���", "sshi"},
        { "������" , "�b�V��", "���", "ssyu"},
        { "������" , "�b�V��", "���", "sshu"},
        { "������" , "�b�V�F", "���", "ssye"},
        { "������" , "�b�V�F", "���", "sshe"},
        { "������" , "�b�V��", "���", "ssyo"},
        { "������" , "�b�V��", "���", "ssho"},
        { "������" , "�b�`��", "���", "ttya"},
        { "������" , "�b�`��", "���", "ccha"},
        { "������" , "�b�`�B", "���", "ttyi"},
        { "������" , "�b�`��", "���", "ttyu"},
        { "������" , "�b�`��", "���", "cchu"},
        { "������" , "�b�`�F", "���", "ttye"},
        { "������" , "�b�`�F", "���", "cche"},
        { "������" , "�b�`��", "���", "ttyo"},
        { "������" , "�b�`��", "���", "ccho"},


        { "���҂�" , "�b�s��", "��߬", "ppya"},
        { "���҂�" , "�b�s�B", "��ߨ", "ppyi"},
        { "���҂�" , "�b�s��", "��߭", "ppyu"},
        { "���҂�" , "�b�s�F", "��ߪ", "ppye"},
        { "���҂�" , "�b�s��", "��߮", "ppyo"},


        { "������" , "�b�W��", "��ެ", "jja"},
        { "������" , "�b�W��", "��ެ", "jjya"},
        { "������" , "�b�W�B", "��ި", "jjyi"},
        { "������" , "�b�W��", "��ޭ", "jju"},
        { "������" , "�b�W��", "��ޭ", "jjyu"},
        { "������" , "�b�W�F", "��ު", "jje"},
        { "������" , "�b�W�F", "��ު", "jjye"},
        { "������" , "�b�W��", "��ޮ", "jjo"},
        { "������" , "�b�W��", "��ޮ", "jjyo"},


        { "������" , "�b�a��", "��ެ", "ddya"},
        { "������" , "�b�a�B", "��ި", "ddyi"},
        { "������" , "�b�a��", "��ޭ", "ddyu"},
        { "������" , "�b�a�F", "��ު", "ddye"},
        { "������" , "�b�a��", "��ޮ", "ddyo"},


        { "����" , "���@", "�ާ", "va"},
        { "����" , "���B", "�ި", "vi"},
        { "��" , "��", "��", "vu"},
        { "����" , "���F", "�ު", "ve"},
        { "����" , "���H", "�ޫ", "vo"},
        { "������" , "�b����", "��ެ", "vvya"},
        { "������" , "�b���B", "��ި", "vvyi"},
        { "������" , "�b����", "��ޭ", "vvyu"},
        { "������" , "�b���F", "��ު", "vvye"},
        { "������",  "�b����", "��ޮ", "vvyo"}

    };
    
    static {
        HIRAGANA_SORTED_LOOKUPS = new String[RAW_LOOKUPS.length][RAW_LOOKUPS[0].length]; 
        ZENKAKU_SORTED_LOOKUPS = new String[RAW_LOOKUPS.length][RAW_LOOKUPS[0].length]; 
        HANKAKU_SORTED_LOOKUPS = new String[RAW_LOOKUPS.length][RAW_LOOKUPS[0].length]; 
        ROMAJI_SORTED_LOOKUPS = new String[RAW_LOOKUPS.length][RAW_LOOKUPS[0].length]; 
        
        System.arraycopy(RAW_LOOKUPS, 0, HIRAGANA_SORTED_LOOKUPS, 0, RAW_LOOKUPS.length);
        System.arraycopy(RAW_LOOKUPS, 0, ZENKAKU_SORTED_LOOKUPS, 0, RAW_LOOKUPS.length);
        System.arraycopy(RAW_LOOKUPS, 0, HANKAKU_SORTED_LOOKUPS, 0, RAW_LOOKUPS.length);
        System.arraycopy(RAW_LOOKUPS, 0, ROMAJI_SORTED_LOOKUPS, 0, RAW_LOOKUPS.length);
        
        Arrays.sort(HIRAGANA_SORTED_LOOKUPS, new KanaArrayComparator(HIRAGANA));
        Arrays.sort(ZENKAKU_SORTED_LOOKUPS, new KanaArrayComparator(ZENKAKU));
        Arrays.sort(HANKAKU_SORTED_LOOKUPS, new KanaArrayComparator(HANKAKU));
        Arrays.sort(ROMAJI_SORTED_LOOKUPS, new KanaArrayComparator(ROMAJI));
    }
}
