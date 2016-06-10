/*
 * &copy; Trans-cosmos Inc. 2006, 2007.
 * All rights reserved.
 */
package jp.co.transcosmos.dm3.utils;

import java.security.MessageDigest;

/**
 * Some useful utilities for encoding in MD5 or hex format.
 * 
 * @author <a href="mailto:rick@knowleses.org">Rick Knowles</a>
 * @version $Id: EncodingUtils.java,v 1.2 2007/05/31 05:55:40 rick Exp $
 */
public class EncodingUtils {
    
	/** HTMLエンコードが必要な文字 **/
	private static char[] htmlChar = {'&', '"', '<', '>'};

	/** HTMLエンコードした文字列 **/
	private static String[] htmlEncStr = {"&amp;", "&quot;", "&lt;", "&gt;"};

	
    public static final String md5Encode(String input) {
        try {
            byte[] digest = MessageDigest.getInstance("MD5").digest(input.getBytes("UTF-8"));
            return hexEncode(digest);
        } catch (Throwable err) {
            throw new RuntimeException("Error doing md5 encode: " + input, err);
        }
    }

    public static String hexEncode(byte input[]) {
        StringBuffer out = new StringBuffer();
        for (int i = 0; i < input.length; i++) {
            out.append(Integer.toString((input[i] & 0xf0) >> 4, 16))
               .append(Integer.toString(input[i] & 0x0f, 16));
        }
        return out.toString();
    }
    
    
	/**
	 * HTMLエンコード処理。
	 *   &,",<,>の置換
	**/
	public static String htmlEncode (String input) {
		if (input == null) {
			return(null);
		}

		// HTMLエンコード処理
		StringBuffer strOut = new StringBuffer(input);

		// エンコードが必要な文字を順番に処理
		for (int i=0; i<htmlChar.length; i++) {
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
		return(strOut.toString());
	}
}