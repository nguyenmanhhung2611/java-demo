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
		{"　", "　", "　", " "}, // zenkaku space
		{" ", " ", " ", " "}, // hankaku space
		{"ー", "ー", "ｰ", "-"},

        { "あ" , "ア", "ｱ", "a"},
        { "い" , "イ", "ｲ", "i"},
        { "う" , "ウ", "ｳ", "u"},
        { "え" , "エ", "ｴ", "e"},
        { "お" , "オ", "ｵ", "o"},
        { "か" , "カ", "ｶ", "ka"},
        { "き" , "キ", "ｷ", "ki"},
        { "く" , "ク", "ｸ", "ku"},
        { "け" , "ケ", "ｹ", "ke"},
        { "こ" , "コ", "ｺ", "ko"},
        { "さ" , "サ", "ｻ", "sa"},
        { "し" , "シ", "ｼ", "si"},
        { "し" , "シ", "ｼ", "shi"},
        { "す" , "ス", "ｽ", "su"},
        { "せ" , "セ", "ｾ", "se"},
        { "そ" , "ソ", "ｿ", "so"},
        { "た" , "タ", "ﾀ", "ta"},
        { "ち" , "チ", "ﾁ", "ti"},
        { "ち" , "チ", "ﾁ", "chi"},
        { "つ" , "ツ", "ﾂ", "tsu"},
        { "て" , "テ", "ﾃ", "te"},
        { "と" , "ト", "ﾄ", "to"},
        { "な" , "ナ", "ﾅ", "na"},
        { "に" , "ニ", "ﾆ", "ni"},
        { "ぬ" , "ヌ", "ﾇ", "nu"},
        { "ね" , "ネ", "ﾈ", "ne"},
        { "の" , "ノ", "ﾉ", "no"},
        { "は" , "ハ", "ﾊ", "ha"},
        { "ひ" , "ヒ", "ﾋ", "hi"},
        { "ふ" , "フ", "ﾌ", "hu"},
        { "へ" , "ヘ", "ﾍ", "he"},
        { "ほ" , "ホ", "ﾎ", "ho"},
        { "ま" , "マ", "ﾏ", "ma"},
        { "み" , "ミ", "ﾐ", "mi"},
        { "む" , "ム", "ﾑ", "mu"},
        { "め" , "メ", "ﾒ", "me"},
        { "も" , "モ", "ﾓ", "mo"},
        { "や" , "ヤ", "ﾔ", "ya"},
        { "ゆ" , "ユ", "ﾕ", "yu"},
        { "よ" , "ヨ", "ﾖ", "yo"},
        { "ら" , "ラ", "ﾗ", "ra"},
        { "り" , "リ", "ﾘ", "ri"},
        { "る" , "ル", "ﾙ", "ru"},
        { "れ" , "レ", "ﾚ", "re"},
        { "ろ" , "ロ", "ﾛ", "ro"},
        { "わ" , "ワ", "ﾜ", "wa"},
        { "を" , "ヲ", "ｦ", "wo"},
        { "ん" , "ン", "ﾝ", "nn"},


        { "が" , "ガ", "ｶﾞ", "ga"},
        { "ぎ" , "ギ", "ｷﾞ", "gi"},
        { "ぐ" , "グ", "ｸﾞ", "gu"},
        { "げ" , "ゲ", "ｹﾞ", "ge"},
        { "ご" , "ゴ", "ｺﾞ", "go"},
        { "ざ" , "ザ", "ｻﾞ", "za"},
        { "ざ" , "ザ", "ｻﾞ", "ja"},
        { "じ" , "ジ", "ｼﾞ", "zi"},
        { "じ" , "ジ", "ｼﾞ", "ji"},
        { "ず" , "ズ", "ｽﾞ", "zu"},
        { "ず" , "ズ", "ｽﾞ", "ju"},
        { "ぜ" , "ゼ", "ｾﾞ", "ze"},
        { "ぜ" , "ゼ", "ｾﾞ", "je"},
        { "ぞ" , "ゾ", "ｿﾞ", "zo"},
        { "ぞ" , "ゾ", "ｿﾞ", "jo"},
        { "だ" , "ダ", "ﾀﾞ", "da"},
        { "ぢ" , "ヂ", "ﾁﾞ", "di"},
        { "づ" , "ヅ", "ﾂﾞ", "du"},
        { "で" , "デ", "ﾃﾞ", "de"},
        { "ど" , "ド", "ﾄﾞ", "do"},
        { "ば" , "バ", "ﾊﾞ", "ba"},
        { "び" , "ビ", "ﾋﾞ", "bi"},
        { "ぶ" , "ブ", "ﾌﾞ", "bu"},
        { "べ" , "ベ", "ﾍﾞ", "be"},
        { "ぼ" , "ボ", "ﾎﾞ", "bo"},
        { "ぱ" , "パ", "ﾊﾟ", "pa"},
        { "ぴ" , "ピ", "ﾋﾟ", "pi"},
        { "ぷ" , "プ", "ﾌﾟ", "pu"},
        { "ぺ" , "ペ", "ﾍﾟ", "pe"},
        { "ぽ" , "ポ", "ﾎﾟ", "po"},


        { "っか" , "ッカ", "ｯｶ", "kka"},
        { "っき" , "ッキ", "ｯｷ", "kki"},
        { "っく" , "ック", "ｯｸ", "kku"},
        { "っけ" , "ッケ", "ｯｹ", "kke"},
        { "っこ" , "ッコ", "ｯｺ", "kko"},
        { "っさ" , "ッサ", "ｯｻ", "ssa"},
        { "っし" , "ッシ", "ｯｼ", "ssi"},
        { "っし" , "ッシ", "ｯｼﾞ", "sshi"},
        { "っす" , "ッス", "ｯｽ", "ssu"},
        { "っせ" , "ッセ", "ｯｾ", "sse"},
        { "っそ" , "ッソ", "ｯｿ", "sso"},
        { "った" , "ッタ", "ｯﾀ", "tta"},
        { "っち" , "ッチ", "ｯﾁ", "tti"},
        { "っち" , "ッチ", "ｯﾁ", "cchi"},
        { "っつ" , "ッツ", "ｯﾂ", "ttu"},
        { "って" , "ッテ", "ｯﾃ", "tte"},
        { "っと" , "ット", "ｯﾄ", "tto"},
        { "っは" , "ッハ", "ｯﾊ", "hha"},
        { "っひ" , "ッヒ", "ｯﾋ", "hhi"},
        { "っふ" , "ッフ", "ｯﾌ", "hhu"},
        { "っへ" , "ッヘ", "ｯﾍ", "hhe"},
        { "っほ" , "ッホ", "ｯﾎ", "hho"},
        { "っま" , "ッマ", "ｯﾏ", "mma"},
        { "っみ" , "ッミ", "ｯﾐ", "mmi"},
        { "っむ" , "ッム", "ｯﾑ", "mmu"},
        { "っめ" , "ッメ", "ｯﾒ", "mme"},
        { "っも" , "ッモ", "ｯﾓ", "mmo"},
        { "っや" , "ッヤ", "ｯﾔ", "yya"},
        { "っゆ" , "ッユ", "ｯﾕ", "yyu"},
        { "っよ" , "ッヨ", "ｯﾖ", "yyo"},
        { "っら" , "ッラ", "ｯﾗ", "rra"},
        { "っり" , "ッリ", "ｯﾘ", "rri"},
        { "っる" , "ッル", "ｯﾙ", "rru"},
        { "っれ" , "ッレ", "ｯﾚ", "rre"},
        { "っろ" , "ッロ", "ｯﾛ", "rro"},
        { "っわ" , "ッワ", "ｯﾜ", "wwa"},
        { "っを" , "ッヲ", "ｯｦ", "wwo"},


        { "っが" , "ッガ", "ｯｶﾞ", "gga"},
        { "っぎ" , "ッギ", "ｯｷﾞ", "ggi"},
        { "っぐ" , "ッグ", "ｯｸﾞ", "ggu"},
        { "っげ" , "ッゲ", "ｯｹﾞ", "gge"},
        { "っご" , "ッゴ", "ｯｺﾞ", "ggo"},
        { "っざ" , "ッザ", "ｯｻﾞ", "zza"},
        { "っじ" , "ッジ", "ｯｼﾞ", "zzi"},
        { "っじ" , "ッジ", "ｯｼﾞ", "jji"},
        { "っず" , "ッズ", "ｯｽﾞ", "zzu"},
        { "っぜ" , "ッゼ", "ｯｾﾞ", "zze"},
        { "っぞ" , "ッゾ", "ｯｿﾞ", "zzo"},
        { "っだ" , "ッダ", "ｯﾀﾞ", "dda"},
        { "っぢ" , "ッヂ", "ｯﾁﾞ", "ddi"},
        { "っづ" , "ッヅ", "ｯﾂﾞ", "ddu"},
        { "っで" , "ッデ", "ｯﾃﾞ", "dde"},
        { "っど" , "ッド", "ｯﾄﾞ", "ddo"},
        { "っば" , "ッバ", "ｯﾊﾞ", "bba"},
        { "っび" , "ッビ", "ｯﾋﾞ", "bbi"},
        { "っぶ" , "ッブ", "ｯﾌﾞ", "bbu"},
        { "っべ" , "ッベ", "ｯﾍﾞ", "bbe"},
        { "っぼ" , "ッボ", "ｯﾎﾞ", "bbo"},


        { "っぱ" , "ッパ", "ｯﾊﾟ", "ppa"},
        { "っぴ" , "ッピ", "ｯﾋﾟ", "ppi"},
        { "っぷ" , "ップ", "ｯﾌﾟ", "ppu"},
        { "っぺ" , "ッペ", "ｯﾍﾟ", "ppe"},
        { "っぽ" , "ッポ", "ｯﾎﾟ", "ppo"},


        { "ぁ" , "ァ", "ｧ", "xa"},
        { "ぃ" , "ィ", "ｨ", "xi"},
        { "ぅ" , "ゥ", "ｩ", "xu"},
        { "ぇ" , "ェ", "ｪ", "xe"},
        { "ぉ" , "ォ", "ｫ", "xo"},
        { "ぁ" , "ァ", "ｧ", "la"},
        { "ぃ" , "ィ", "ｨ", "li"},
        { "ぅ" , "ゥ", "ｩ", "lu"},
        { "ぇ" , "ェ", "ｪ", "le"},
        { "ぉ" , "ォ", "ｫ", "lo"},
        { "ヵ" , "ヵ", "ヵ", "xka"},
        { "ゎ" , "ヮ", "ゎ", "xwa"},
        { "ゃ" , "ャ", "ｬ", "xya"},
        { "ゅ" , "ュ", "ｭ", "xyu"},
        { "ょ" , "ョ", "ｮ", "xyo"},
        { "っ" , "ッ", "ｯ", "xtu"},
        { "っ" , "ッ", "ｯ", "ltu"},

        { "っふぁ" , "ッファ", "ｯﾌｧ", "ffa"},
        { "っふぃ" , "ッフィ", "ｯﾌｨ", "ffi"},
        { "っふぇ" , "ッフェ", "ｯﾌｪ", "ffe"},
        { "っふぉ" , "ッフォ", "ｯﾌｫ", "ffo"},


        { "しゃ" , "シャ", "ｼｬ", "sha"},
        { "しゅ" , "シュ", "ｼｭ", "shu"},
        { "しぇ" , "シェ", "ｼｪ", "she"},
        { "しょ" , "ショ", "ｼｮ", "sho"},

        { "にゃ" , "ニャ", "ﾆｬ", "nya"},
        { "にぃ" , "ニィ", "ﾆｨ", "nyi"},
        { "にゅ" , "ニュ", "ﾆｭ", "nyu"},
        { "にぇ" , "ニェ", "ﾆｪ", "nye"},
        { "にょ" , "ニョ", "ﾆｮ", "nyo"},

        { "っしゃ" , "ッシャ", "ｯｼｬ", "ssya"},
        { "っしゃ" , "ッシャ", "ｯｼｬ", "ssha"},
        { "っしぃ" , "ッシィ", "ｯｼｨ", "ssyi"},
        { "っしぃ" , "ッシィ", "ｯｼｨ", "sshi"},
        { "っしゅ" , "ッシュ", "ｯｼｭ", "ssyu"},
        { "っしゅ" , "ッシュ", "ｯｼｭ", "sshu"},
        { "っしぇ" , "ッシェ", "ｯｼｪ", "ssye"},
        { "っしぇ" , "ッシェ", "ｯｼｪ", "sshe"},
        { "っしょ" , "ッショ", "ｯｼｮ", "ssyo"},
        { "っしょ" , "ッショ", "ｯｼｮ", "ssho"},
        { "っちゃ" , "ッチャ", "ｯﾁｬ", "ttya"},
        { "っちゃ" , "ッチャ", "ｯﾁｬ", "ccha"},
        { "っちぃ" , "ッチィ", "ｯﾁｨ", "ttyi"},
        { "っちゅ" , "ッチュ", "ｯﾁｭ", "ttyu"},
        { "っちゅ" , "ッチュ", "ｯﾁｭ", "cchu"},
        { "っちぇ" , "ッチェ", "ｯﾁｪ", "ttye"},
        { "っちぇ" , "ッチェ", "ｯﾁｪ", "cche"},
        { "っちょ" , "ッチョ", "ｯﾁｮ", "ttyo"},
        { "っちょ" , "ッチョ", "ｯﾁｮ", "ccho"},


        { "っぴゃ" , "ッピャ", "ｯﾋﾟｬ", "ppya"},
        { "っぴぃ" , "ッピィ", "ｯﾋﾟｨ", "ppyi"},
        { "っぴゅ" , "ッピュ", "ｯﾋﾟｭ", "ppyu"},
        { "っぴぇ" , "ッピェ", "ｯﾋﾟｪ", "ppye"},
        { "っぴょ" , "ッピョ", "ｯﾋﾟｮ", "ppyo"},


        { "っじゃ" , "ッジャ", "ｯｼﾞｬ", "jja"},
        { "っじゃ" , "ッジャ", "ｯｼﾞｬ", "jjya"},
        { "っじぃ" , "ッジィ", "ｯｼﾞｨ", "jjyi"},
        { "っじゅ" , "ッジュ", "ｯｼﾞｭ", "jju"},
        { "っじゅ" , "ッジュ", "ｯｼﾞｭ", "jjyu"},
        { "っじぇ" , "ッジェ", "ｯｼﾞｪ", "jje"},
        { "っじぇ" , "ッジェ", "ｯｼﾞｪ", "jjye"},
        { "っじょ" , "ッジョ", "ｯｼﾞｮ", "jjo"},
        { "っじょ" , "ッジョ", "ｯｼﾞｮ", "jjyo"},


        { "っぢゃ" , "ッヂャ", "ｯﾁﾞｬ", "ddya"},
        { "っぢぃ" , "ッヂィ", "ｯﾁﾞｨ", "ddyi"},
        { "っぢゅ" , "ッヂュ", "ｯﾁﾞｭ", "ddyu"},
        { "っぢぇ" , "ッヂェ", "ｯﾁﾞｪ", "ddye"},
        { "っぢょ" , "ッヂョ", "ｯﾁﾞｮ", "ddyo"},


        { "ヴぁ" , "ヴァ", "ｳﾞｧ", "va"},
        { "ヴぃ" , "ヴィ", "ｳﾞｨ", "vi"},
        { "ヴ" , "ヴ", "ｳﾞ", "vu"},
        { "ヴぇ" , "ヴェ", "ｳﾞｪ", "ve"},
        { "ヴぉ" , "ヴォ", "ｳﾞｫ", "vo"},
        { "っヴゃ" , "ッヴャ", "ｯｳﾞｬ", "vvya"},
        { "っヴぃ" , "ッヴィ", "ｯｳﾞｨ", "vvyi"},
        { "っヴゅ" , "ッヴュ", "ｯｳﾞｭ", "vvyu"},
        { "っヴぇ" , "ッヴェ", "ｯｳﾞｪ", "vvye"},
        { "っヴょ",  "ッヴョ", "ｯｳﾞｮ", "vvyo"}

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
