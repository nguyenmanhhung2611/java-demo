/*
 * Copyright (C) 2006 Transcosmos INC.
 * All Rights Reserved.
 */
package jp.co.transcosmos.dm3.utils;

import java.util.Map;

import org.apache.commons.collections.map.LRUMap;

/** 
 * <pre>
 * �`�F�b�N�f�B�W�b�g�v�Z���[�e�B���e�B�B
 * 
 * �S����       �C����      �C�����e
 * ------------ ----------- -----------------------------------------------------
 * t.hirose	    2006/08/25  �V�K�쐬
 *
 * ���ӎ���
 * 
 * </pre>
 * @author t.hirose
 */
public class CheckDigitUtil {

	/** �f�t�H���g�̃L���b�V���T�C�Y */
	private static final int DEFAULT_CACHE_SIZE = 512;

	/** �f�t�H���g�`�F�b�N�f�B�W�b�g�̏��� */
	private static final int DEFAULT_CHAR_DIGIT = 27;

	/** �`�F�b�N�f�B�W�b�g������̊J�n�ʒu */
	private static final char DEFAULT_CHAR_DIGIT_OFFSET = 'A';

	/** �`�F�b�N���ʂ̃L���b�V�� �`�F�b�N�̍������̂��� �������L���b�V������ */
    @SuppressWarnings("unchecked")
	private static Map<Object,Object> mLRUCache = new LRUMap(DEFAULT_CACHE_SIZE);

	/**
	 * �`�F�b�N�f�B�W�b�g���v�Z����B
	 * A-Z�̃`�F�b�N�f�B�W�b�g���v�Z���ĕԂ��B
	 * @param pSrc
	 *            �`�F�b�N�f�B�W�b�g���擾���镶����
	 * @return String �v�Z�����`�F�b�N�f�B�W�b�g�̕�����
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
	 * �`�F�b�N�f�B�W�b�g�𖖔��ɕt�^���ꂽ��������`�F�b�N����B
	 * @param pCombined �`�F�b�N���镶����
	 * @return �`�F�b�N�f�B�W�b�g���������ꍇ�Atrue��Ԃ��B
	 */
	public static boolean checkTail(String pCombined) {
		if (pCombined == null || pCombined.length() <= 1) {
			return false;
		}

		Boolean bInCache = null;
		// Find in Cache
		synchronized (mLRUCache) {
			// �G���g�����L���b�V������T���B
			bInCache = (Boolean) mLRUCache.get(pCombined);
		}
		if (bInCache != null) {
			return bInCache.booleanValue();
		}

		// �v�Z����
		String digit = pCombined.substring(pCombined.length() - 1);
		String src = pCombined.substring(0, pCombined.length() - 1);
		String cd = getCheckDigit(src);

		if (digit.equals(cd) == true) {
			synchronized (mLRUCache) {
				// �G���g�����L���b�V���֓����B
				mLRUCache.put(pCombined, Boolean.TRUE);
			}
			return true;
		}

		synchronized (mLRUCache) {
			// �G���g�����L���b�V���֓����B
			mLRUCache.put(pCombined, Boolean.FALSE);
		}
		return false;
	}

	/**
	 * �`�F�b�N�f�B�W�b�g�𖖔��ɕt�^������������쐬����B
	 * @param pSrc �`�F�b�N�f�B�W�b�g��t�^���镶����
	 * @return �`�F�b�N�f�B�W�b�g�𖖔��ɕt�^����������B
	 */
	public static String addDigitToTail(String pSrc) {
		String cd = CheckDigitUtil.getCheckDigit(pSrc);
		return pSrc + cd;
	}

	/**
	 * �e�X�g
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