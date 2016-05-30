/*
 * Copyright (C) 2006 Transcosmos INC.
 * All Rights Reserved.
 */
package jp.co.transcosmos.dm3.utils;

import java.nio.charset.Charset;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/** 
 * <pre>
 * 簡易暗号化ユーティリティ。
 * 
 * 担当者       修正日      修正内容
 * ------------ ----------- -----------------------------------------------------
 * t.hirose	    2006/08/18  新規作成
 *
 * 注意事項
 * 
 * </pre>
 * @author t.hirose
 */
public class CipherUtil {

	/** デフォルトの鍵 */
	private static final String DEFAULT_KEYWORD = "keyword";

	/** 秘密鍵 */
	private SecretKeySpec mSecretKey;

	/** 暗号化クラス */
	private Cipher mCipher;

	/** デフォルトの暗号化アルゴリズム */
	private static final String DEFAULT_ALGORITHM = "DES";
	// private static final String DEFAULT_ALGORITHM = "DESede";
	// private static final String DEFAULT_ALGORITHM = "AES";
	// private static final String DEFAULT_ALGORITHM = "Blowfish";
	// private static final String DEFAULT_ALGORITHM = "ECIES";
	// private static final String DEFAULT_ALGORITHM = "RC2";
	// private static final String DEFAULT_ALGORITHM = "RC4";
	// // private static final String DEFAULT_ALGORITHM = "RC5";
	// // private static final String DEFAULT_ALGORITHM = "RSA";

	/**
	 * コンストラクタ
	 * 
	 * @param pKey
	 *            このオブジェクトに使用する秘密鍵
	 * @throws FatalException
	 *             致命的エラー
	 */
	public CipherUtil(String pKey) {

		byte[] byteKey = { 
                (byte) 0x01, (byte) 0x02, (byte) 0x03, (byte) 0x04, 
                (byte) 0x05, (byte) 0x06, (byte) 0x07, (byte) 0x08 
            };
		// 鍵
		byte[] keyword = null;
		if (pKey != null) {
			keyword = pKey.getBytes();
		} else {
			keyword = DEFAULT_KEYWORD.getBytes();
		}

		for (int i = 0; i < keyword.length && i < byteKey.length; i++) {
			byteKey[i] = keyword[i];
		}

		// 秘密鍵生成
		mSecretKey = new SecretKeySpec(byteKey, DEFAULT_ALGORITHM);
		// インスタンスの取得
        try {
            mCipher = Cipher.getInstance(DEFAULT_ALGORITHM);
        } catch (Throwable e) {
            throw new RuntimeException("Error getting cipher: " + DEFAULT_ALGORITHM, e);
        }
	}

	/**
	 * 暗号化する
	 * 
	 * @param aPlane
	 *            暗号化する文字列
	 * @return String 暗号化した文字列
	 */
	public final String encode(String aPlane) {

		byte[] secret = null;
		// 初期化の実施
		synchronized (mCipher) {
            try {
                mCipher.init(Cipher.ENCRYPT_MODE, mSecretKey);
                // 暗号化
                secret = mCipher.doFinal(aPlane.getBytes());
            } catch (Throwable e) {
                throw new RuntimeException("Error encoding " + aPlane, e);
            }
		}
		return ByteUtil.toHexString(secret);

	}

	/**
	 * 復号化する
	 * @param pCrypt 複合化する文字列
	 * @return 複合化した文字列
	 */
	public final String decode(String pCrypt) {
		return decode(pCrypt, null);
	}

	/**
	 * 復号化する
	 * @param pCrypt  複合化する文字列
	 * @param pCharSetName キャラクタセットの名前
	 * @return 複合化した文字列
	 */
	public final String decode(String pCrypt, String pCharSetName) {

        try {
    		byte[] secret = ByteUtil.toBytes(pCrypt);
    		byte[] plane = null;
    		// 初期化の実施
    		synchronized (mCipher) {
                mCipher.init(Cipher.DECRYPT_MODE, mSecretKey);
                plane = mCipher.doFinal(secret);
    		}
    		// 復号化
    		String charSetName = pCharSetName;
    		if (charSetName == null) {
    			charSetName = Charset.forName(System.getProperty("file.encoding")).name();
    		}
    		return new String(plane, charSetName);
        } catch (Throwable e) {
            throw new RuntimeException("Error decoding " + pCrypt + " charset=" + pCharSetName, e);
        }
	}
}