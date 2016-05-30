/*
 * Copyright (C) 2006 Transcosmos INC.
 * All Rights Reserved.
 */
package jp.co.transcosmos.dm3.utils;



/** 
 * <pre>
 * Byteユーティリティ。
 * 
 * 担当者       修正日      修正内容
 * ------------ ----------- -----------------------------------------------------
 * t.hirose	    2006/08/23  新規作成
 *
 * 注意事項
 * 
 * </pre>
 * @author t.hirose
 */
public class ByteUtil {
	
    /**
	 * byte[] ⇒ String
	 * @param pBs
	 * @return String
	 */
	public static final String toHexString(byte[] pBs) {
		StringBuilder buffer = new StringBuilder(pBs.length * 2);
		for (int i = 0; i < pBs.length; i++) {
			if (pBs[i] >= 0 && pBs[i] < 0x10) {
				buffer.append('0');
			}
			buffer.append(Integer.toHexString(0xff & pBs[i]));
		}
		return buffer.toString();
	}

	/**
	 * String ⇒ byte[]
	 * @param pHexString
	 * @return byte[]
	 * @throws NumberFormatException
	 */
	public static final byte[] toBytes(String pHexString) throws NumberFormatException {

		if (pHexString.length() % 2 == 1) {
			pHexString = '0' + pHexString;
		}
		byte[] bytes = new byte[pHexString.length() / 2];
		for (int i = bytes.length - 1; i >= 0; i--) {
			String b = pHexString.substring(i * 2, i * 2 + 2);
			bytes[i] = (byte) Integer.parseInt(b, 16);
		}
		return bytes;
	}

}
