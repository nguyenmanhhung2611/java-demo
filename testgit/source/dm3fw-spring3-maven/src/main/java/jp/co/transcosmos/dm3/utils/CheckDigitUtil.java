/*
 * Copyright (C) 2006 Transcosmos INC.
 * All Rights Reserved.
 */
package jp.co.transcosmos.dm3.utils;

import java.util.Map;

import org.apache.commons.collections.map.LRUMap;

/** 
 * <pre>
 * チェックディジット計算ユーティリティ。
 * 
 * 担当者       修正日      修正内容
 * ------------ ----------- -----------------------------------------------------
 * t.hirose	    2006/08/25  新規作成
 *
 * 注意事項
 * 
 * </pre>
 * @author t.hirose
 */
public class CheckDigitUtil {

	/** デフォルトのキャッシュサイズ */
	private static final int DEFAULT_CACHE_SIZE = 512;

	/** デフォルトチェックディジットの除数 */
	private static final int DEFAULT_CHAR_DIGIT = 27;

	/** チェックディジット文字列の開始位置 */
	private static final char DEFAULT_CHAR_DIGIT_OFFSET = 'A';

	/** チェック結果のキャッシュ チェックの高速化のため 答えをキャッシュする */
    @SuppressWarnings("unchecked")
	private static Map<Object,Object> mLRUCache = new LRUMap(DEFAULT_CACHE_SIZE);

	/**
	 * チェックディジットを計算する。
	 * A-Zのチェックディジットを計算して返す。
	 * @param pSrc
	 *            チェックディジットを取得する文字列
	 * @return String 計算したチェックディジットの文字列
	 */
	public static String getCheckDigit(String pSrc) {

		byte[] bchars = pSrc.getBytes(); // null - invoke NullPointerException 
		int len = bchars.length;

		int j = DEFAULT_CHAR_DIGIT;
		int sum = 0;
		for (int i = 0; i < len; i++) {
			sum += bchars[i] * j--;
			sum = sum % 100;
			if (j <= 0) {
				j = DEFAULT_CHAR_DIGIT;
			}
		}
		j = j % DEFAULT_CHAR_DIGIT;
		char ch = (char) (DEFAULT_CHAR_DIGIT_OFFSET + j);
		return String.valueOf(ch);
	}

	/**
	 * チェックディジットを末尾に付与された文字列をチェックする。
	 * @param pCombined チェックする文字列
	 * @return チェックディジットが正しい場合、trueを返す。
	 */
	public static boolean checkTail(String pCombined) {
		if (pCombined == null || pCombined.length() <= 1) {
			return false;
		}

		Boolean bInCache = null;
		// Find in Cache
		synchronized (mLRUCache) {
			// エントリをキャッシュから探す。
			bInCache = (Boolean) mLRUCache.get(pCombined);
		}
		if (bInCache != null) {
			return bInCache.booleanValue();
		}

		// 計算する
		String digit = pCombined.substring(pCombined.length() - 1);
		String src = pCombined.substring(0, pCombined.length() - 1);
		String cd = getCheckDigit(src);

		if (digit.equals(cd) == true) {
			synchronized (mLRUCache) {
				// エントリをキャッシュへ入れる。
				mLRUCache.put(pCombined, Boolean.TRUE);
			}
			return true;
		}

		synchronized (mLRUCache) {
			// エントリをキャッシュへ入れる。
			mLRUCache.put(pCombined, Boolean.FALSE);
		}
		return false;
	}

	/**
	 * チェックディジットを末尾に付与した文字列を作成する。
	 * @param pSrc チェックディジットを付与する文字列
	 * @return チェックディジットを末尾に付与した文字列。
	 */
	public static String addDigitToTail(String pSrc) {
		String cd = CheckDigitUtil.getCheckDigit(pSrc);
		return pSrc + cd;
	}

	/**
	 * テスト
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		String src = args[0];
		System.out.println("src=" + src);

		String cd = CheckDigitUtil.getCheckDigit(src);

		System.out.println("cd=" + cd);

		src = src + cd;

		System.out.println("check=" + CheckDigitUtil.checkTail(src));

		System.out.println("check=" + CheckDigitUtil.checkTail(src + "l"));
	}
}