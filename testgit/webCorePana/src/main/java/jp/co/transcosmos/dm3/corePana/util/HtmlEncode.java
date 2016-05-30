package jp.co.transcosmos.dm3.corePana.util;

/**
 * Panasonic用HTMLエンコード共通クラス.
 *
 * <pre>
 *
 * 担当者       修正日      修正内容
 * ------------ ----------- -----------------------------------------------------
 *   Trans    2015.03.31    新規作成
 *
 * 注意事項
 *
 * </pre>
 */
public class HtmlEncode {

    /** HTMLエンコードが必要な文字 **/
    static char[] htmlChar = { '&', '"', '<', '>' };

    /** HTMLエンコードした文字列 **/
    static String[] htmlEncStr = { "&amp;", "&quot;", "&lt;", "&gt;" };

    /**
    * HTMLエンコード処理。
    *   &,",<,>の置換
    **/
    public String encode(String pStr) {
        if (pStr == null) {
            return (null);
        }

        // HTMLエンコード処理
        StringBuffer strOut = new StringBuffer(pStr);

        // エンコードが必要な文字を順番に処理
        for (int i = 0; i < htmlChar.length; i++) {
            // エンコードが必要な文字の検索
            int idx = strOut.toString().indexOf(htmlChar[i]);

            while (idx != -1) {
                // エンコードが必要な文字の置換
                strOut.setCharAt(idx, htmlEncStr[i].charAt(0));
                strOut.insert(idx + 1, htmlEncStr[i].substring(1));

                // 次のエンコードが必要な文字の検索
                idx = idx + htmlEncStr[i].length();
                idx = strOut.toString().indexOf(htmlChar[i], idx);
            }
        }
        return (strOut.toString());
    }

}
