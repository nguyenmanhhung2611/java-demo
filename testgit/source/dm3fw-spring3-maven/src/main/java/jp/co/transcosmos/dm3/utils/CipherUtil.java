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
 * �ȈՈÍ������[�e�B���e�B�B
 * 
 * �S����       �C����      �C�����e
 * ------------ ----------- -----------------------------------------------------
 * t.hirose	    2006/08/18  �V�K�쐬
 *
 * ���ӎ���
 * 
 * </pre>
 * @author t.hirose
 */
public class CipherUtil {

	/** �f�t�H���g�̌� */
	private static final String DEFAULT_KEYWORD = "keyword";

	/** �閧�� */
	private SecretKeySpec mSecretKey;

	/** �Í����N���X */
	private Cipher mCipher;

	/** �f�t�H���g�̈Í����A���S���Y�� */
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
	 * �R���X�g���N�^
	 * 
	 * @param pKey
	 *            ���̃I�u�W�F�N�g�Ɏg�p����閧��
	 * @throws FatalException
	 *             �v���I�G���[
	 */
	public CipherUtil(String pKey) {

		byte[] byteKey = { 
                (byte) 0x01, (byte) 0x02, (byte) 0x03, (byte) 0x04, 
                (byte) 0x05, (byte) 0x06, (byte) 0x07, (byte) 0x08 
            };
		// ��
		byte[] keyword = null;
		if (pKey != null) {
			keyword = pKey.getBytes();
		} else {
			keyword = DEFAULT_KEYWORD.getBytes();
		}

		for (int i = 0; i < keyword.length && i < byteKey.length; i++) {
			byteKey[i] = keyword[i];
		}

		// �閧������
		mSecretKey = new SecretKeySpec(byteKey, DEFAULT_ALGORITHM);
		// �C���X�^���X�̎擾
        try {
            mCipher = Cipher.getInstance(DEFAULT_ALGORITHM);
        } catch (Throwable e) {
            throw new RuntimeException("Error getting cipher: " + DEFAULT_ALGORITHM, e);
        }
	}

	/**
	 * �Í�������
	 * 
	 * @param aPlane
	 *            �Í������镶����
	 * @return String �Í�������������
	 */
	public final String encode(String aPlane) {

		byte[] secret = null;
		// �������̎��{
		synchronized (mCipher) {
            try {
                mCipher.init(Cipher.ENCRYPT_MODE, mSecretKey);
                // �Í���
                secret = mCipher.doFinal(aPlane.getBytes());
            } catch (Throwable e) {
                throw new RuntimeException("Error encoding " + aPlane, e);
            }
		}
		return ByteUtil.toHexString(secret);

	}

	/**
	 * ����������
	 * @param pCrypt ���������镶����
	 * @return ����������������
	 */
	public final String decode(String pCrypt) {
		return decode(pCrypt, null);
	}

	/**
	 * ����������
	 * @param pCrypt  ���������镶����
	 * @param pCharSetName �L�����N�^�Z�b�g�̖��O
	 * @return ����������������
	 */
	public final String decode(String pCrypt, String pCharSetName) {

        try {
    		byte[] secret = ByteUtil.toBytes(pCrypt);
    		byte[] plane = null;
    		// �������̎��{
    		synchronized (mCipher) {
                mCipher.init(Cipher.DECRYPT_MODE, mSecretKey);
                plane = mCipher.doFinal(secret);
    		}
    		// ������
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