/*
 * Copyright (c) 2004-2006 T.Hirose 
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */
package jp.co.transcosmos.dm3.utils;


// �ɗ́A����������̂܂܃`�F�b�N���邱�ƁB
/**
 * ������̃`�F�b�N�̂��߂̃��[�e�B���e�B�֐��B
 * 
 * @author T.Hirose
 * @version $Id: StringValidateUtil.java,v 1.2 2012/08/01 09:28:36 tanaka Exp $
 */
public final class StringValidateUtil {
//	/** Version Identifier */ @SuppressWarnings("unused")
	public static final String _VERSION = "$Id: StringValidateUtil.java,v 1.2 2012/08/01 09:28:36 tanaka Exp $"; //$NON-NLS-1$

	/** �Q�o�C�g�󔒕����̃L�����N�^�^ */
	private static final char CHAR_SPACE_2BYTE = '\u3000';

//	/** ����I�� */
//	public static final int true = 0;
//	/** �ُ�I�� */
//	public static final int false = -1;

	/**
	 * �����̕���(1-9)���ǂ����𔻒肷��B
	 * @param ch ����Ώە���
	 * @return ���������̏ꍇtrue��Ԃ��B
	 */
	private static final boolean isAsciiDigit(char ch){
		if( ch > '9' || ch < '0'){
			return false;
		}
		return true;
	}
	
	/**
	 * ���ׂĎw�肳�ꂽ�z��̒��̕����ō\������Ă��邩�ǂ����`�F�b�N����
	 * @param aStr �`�F�b�N�Ώۂ̕�����
	 * @param aAnyChars �����񂪍\�������ׂ������̔z��
	 * @return �����񂪔z�񒆂̕�������̂ݍ\�������ꍇ�A<tt>true</tt>��Ԃ��B<br>
	 * 		�z�񒆂ɂȂ��������������ꍇ��<tt>false</tt>��Ԃ��B
	 */
	public static boolean isAnyCharAll(String aStr , char[] aAnyChars) {
		if (aStr == null){
			return false;
		}
		char srcChar;
		int ilen = aStr.length();
		if( ilen <= 0){
			return false; //�ُ�I��
		}
		int iIndex = 0;
		while (iIndex < ilen) {
			srcChar = aStr.charAt(iIndex);
			int i = 0;
			for(; i < aAnyChars.length ; i++){
				if( srcChar == aAnyChars[i] ){
					break;
				}
			}
			if( i >= aAnyChars.length  ){
				break;
			}
			iIndex++;
		}
		if (ilen != iIndex) {
			return false; //�ُ�I��
		}
		return true; //����I��
	}
//
//	/**
//	 * ���ׂċ󔒕����ǂ����`�F�b�N����
//	 * �Q�o�C�g�󔒂�1�o�C�g�󔒂̗������`�F�b�N����
//	 * @param aStr �`�F�b�N�Ώۂ̕�����
//	 * @return ������1�o�C�g�󔒂܂���2�o�C�g�󔒂����̏ꍇ�A<tt>true</tt>��Ԃ�
//	 */
//	public static int isAllJPSpace(String aStr) {
//		if (aStr == null){
//			return false;
//		}
//		
//		char srcChar;
//		int ilen = aStr.length();
//		if( ilen <= 0){
//			return false; //�ُ�I��
//		}
//		int iIndex = 0;
//		while (iIndex < ilen) {
//			srcChar = aStr.charAt(iIndex);
//			if (srcChar == ' ') {
//				// 1�o�C�g��
//			} else if (srcChar == CHAR_SPACE_2BYTE) {
//				// 2�o�C�g��
//			} else {
//				//�󔒈ȊO�̕���
//				break;
//			}
//			iIndex++;
//		}
//		if (ilen != iIndex) {
//			return false; //�ُ�I��
//		}
//		return true; //����I��
//	}

	/**
	 * �����񂪐������ǂ����`�F�b�N���� �B
	 * �����̏ꍇ�̓G���[�ƂȂ�B
	 * ����(-)�t��0��OK�B����(+)�t��������̓G���[�B
	 * 
	 * @param aStr �`�F�b�N�Ώۂ̕�����
	 * @return �����񂪐����̏ꍇ<tt>true</tt>��Ԃ�
	 */
	public static boolean isInteger(String aStr) {
		//		try {
		//			Long.parseLong(aStr);
		//		} catch (Exception ex) {
		//			return false; //�ϊ��G���[
		//		}
		////
		if (aStr == null){
			return false;
		}
		int length = aStr.length();
		if( length <= 0){
			return false; //�G���[
		}

		int i = 0;
		char ch = aStr.charAt(0);
		if (ch == '-' /*|| ch == '+'*/) {
			i++;
		}
		if (length > i) {
			if (aStr.charAt(i) == '0') {
				i++;
				if (length == i) {
					return true; // -0 ����
				}
				
				// �擪0�̐����̓G���[
				return false; //�G���[
			}
		} else {
			// �����̂�
			return false; //�G���[
		}
		while (i < length) {
			if (isAsciiDigit(aStr.charAt(i++)) == false) {
				return false; //�G���[
			}
		}
		return true;
	}

	/**
	 * ���̐������ǂ����`�F�b�N����
	 * @param aStr �`�F�b�N�Ώۂ̕�����
	 * @return �����񂪐��̐����̏ꍇ<tt>true</tt>��Ԃ�
	 */
	public static boolean isPositiveInteger(String aStr) {
		if (aStr == null){
			return false;
		}
		int length = aStr.length();
		if( length <= 0){
			return false; //�G���[
		}
		int i = 0;
		char ch = aStr.charAt(i);
		if (ch == '-') {
			return false; //���̐��̓G���[
		}
//		if (ch == '+') {		// ���̐��̕����͂Ȃ�
//			i++;
//		}
		if (length > i) {
			if (aStr.charAt(i) == '0') {
				i++;
				if (length == i) {
					return true; // +0 ����
				}
				
				// �擪0�̐����̓G���[
				return false; //�G���[
			}
		} else {
			return false; //�G���[
		}
		while (i < length) {
			if (isAsciiDigit(aStr.charAt(i++)) == false) {
				return false; //�G���[
			}
		}
		return true;

	}

	/**
	 * ���̐������ǂ����`�F�b�N����
	 * @param aStr �`�F�b�N�Ώۂ̕�����
	 * @return �����񂪕��̐����̏ꍇ<tt>true</tt>��Ԃ�
	 */
	public static boolean isNegativeInteger(String aStr) {
		if (aStr == null){
			return false;
		}
		int length = aStr.length();
		if( length <= 0){
			return false; //�G���[
		}
		int i = 0;
		char ch = aStr.charAt(i);
//		if (ch == '-') {
//			return false; //���̐��̓G���[
//		}
		if (ch == '-') {		// ���̐�
			i++;
		}else{
			return false; //�����Ȃ��̓G���[
		}
		if (length > i) {
			if (aStr.charAt(i) == '0') {
				i++;
				if (length == i) {
					return true; // +0 ����
				}
				
				// �擪0�̐����̓G���[
				return false; //�G���[
			}
		} else {
			return false; //�G���[
		}
		while (i < length) {
			if (isAsciiDigit(aStr.charAt(i++)) == false) {
				return false; //�G���[
			}
		}
		return true;

	}

	/**
	 * ���\��r�p
	 * @param aStr �`�F�b�N�Ώۂ̕�����
	 * @return �����񂪐��̏����̏ꍇ<tt>true</tt>��Ԃ�
	 */
	public static boolean isFloatA(String aStr) {
		if (aStr == null) {
			return false;
		}
		try{
			/*Double d =*/ Double.parseDouble(aStr);
		}catch(NumberFormatException e){
			return false;
		}
		return true;
	}
	
	/**
	 * �����܂��͏������ǂ����`�F�b�N����B
	 * �������̐擪��0���܂ސ����̕�����̓G���[�ɂȂ�܂��B
	 * @param aStr �`�F�b�N�Ώۂ̕�����
	 * @return �����񂪐��̏����̏ꍇ<tt>true</tt>��Ԃ�
	 */
	public static boolean isNumeric(String aStr) {
		if (aStr == null) {
			return false;
		}
		int ilen = aStr.length();
		if (ilen == 0) {
			return false;
		}

		char srcChar;
		int iIndex = 0;
		boolean bFindFlag = false;
		boolean bFindDecimalPoint = false;
		int iLeftLen = 0;
		int iRightLen = 0;

		srcChar = aStr.charAt(iIndex);
		if (srcChar == '-' /*|| srcChar == '+' */) {
			bFindFlag = true;
			iIndex++;
			if (ilen == 1) {
				return false; //���������̓G���[
			}
		}
		if (ilen > iIndex ){
			if(aStr.charAt(iIndex) == '0') {
				if (ilen > (iIndex + 1) && aStr.charAt(iIndex + 1) != '.') {
					//�擪0�Ŏ���'.'�łȂ����̂̓G���[
					return false;
				} else if (ilen > (iIndex + 1) && aStr.charAt(iIndex + 1) == '.') {
					// .���オ�Ȃ����̂̓G���[
					iIndex++;
					iLeftLen++;
					if (ilen == iIndex + 1) {
						return false;
					}
				} else if (ilen == (iIndex + 1)) {
					//0�̂��̂�OK
					return true;
				}
			}else
			if(aStr.charAt(iIndex) == '.') {
				if( bFindFlag == true){
					// �����̌�ɂ�0���K�v
					return false;
				}
				// .���オ�Ȃ����̂̓G���[
				bFindDecimalPoint = true;
				iIndex++;
				iLeftLen++;
				if (ilen == iIndex + 1) {
					return false;
				}
			}
		}
		while (iIndex < ilen) {
			srcChar = aStr.charAt(iIndex);
			if (isAsciiDigit(srcChar) == true) { //���l�������ǂ���
				if (bFindDecimalPoint == true) {
					iRightLen++;
					if (iLeftLen <= 0) {
						break;
					}
				} else {
					iLeftLen++;
				}
				//
			} else if (bFindDecimalPoint == false && srcChar == '.') { //�����������ǂ���
				bFindDecimalPoint = true;
				// '.'���Q�̓G���[
			} else {
				// �G���[
				break;
			}
			iIndex++;
		}
		if (ilen != iIndex) {
			return false;
		}
		if (bFindDecimalPoint == true) {
			if (iLeftLen <= 0) {
				return false;
			}
			if (iRightLen <= 0) {
				return false;
			}
		}
		return true;
	}

	/**
	 * �������ǂ����`�F�b�N����B
	 * @param aStr �`�F�b�N�Ώۂ̕�����
	 * @return �����񂪐��̏����̏ꍇ<tt>true</tt>��Ԃ�
	 */
	public static boolean isFloat(String aStr) {
		if (aStr == null) {
			return false;
		}
		int ilen = aStr.length();
		if (ilen == 0) {
			return false;
		}

		char srcChar;
		int iIndex = 0;
		boolean bFindFlag = false;
		boolean bFindDecimalPoint = false;
		int iLeftLen = 0;
		int iRightLen = 0;

		srcChar = aStr.charAt(iIndex);
		if (srcChar == '-' || srcChar == '+') {
			bFindFlag = true;
			iIndex++;
			if (ilen == 1) {
				return false; //���������̓G���[
			}
		}
		if (ilen > iIndex ){
			if(aStr.charAt(iIndex) == '0') {
				if (ilen > (iIndex + 1) && aStr.charAt(iIndex + 1) != '.') {
					//�擪0�Ŏ���'.'�łȂ����̂̓G���[
					return false;
				} else if (ilen > (iIndex + 1) && aStr.charAt(iIndex + 1) == '.') {
					// .���オ�Ȃ����̂̓G���[
					iIndex++;
					iLeftLen++;
					if (ilen == iIndex + 1) {
						return false;
					}
				} else if (ilen == (iIndex + 1)) {
					//0�̂��̂�OK
					return true;
				}
			}else
			if(aStr.charAt(iIndex) == '.') {
				if( bFindFlag == true){
					// �����̌�ɂ�0���K�v
					return false;
				}
				// .���オ�Ȃ����̂̓G���[
				bFindDecimalPoint = true;
				iIndex++;
				iLeftLen++;
				if (ilen == iIndex + 1) {
					return false;
				}
			}
		}
		while (iIndex < ilen) {
			srcChar = aStr.charAt(iIndex);
			if (isAsciiDigit(srcChar) == true) { //���l�������ǂ���
				if (bFindDecimalPoint == true) {
					iRightLen++;
					if (iLeftLen <= 0) {
						break;
					}
				} else {
					iLeftLen++;
				}
				//
			} else if (bFindDecimalPoint == false && srcChar == '.') { //�����������ǂ���
				bFindDecimalPoint = true;
				// '.'���Q�̓G���[
			} else {
				// �G���[
				break;
			}
			iIndex++;
		}
		if (ilen != iIndex) {
			return false;
		}
		if (bFindDecimalPoint == true) {
			if (iLeftLen <= 0) {
				return false;
			}
			if (iRightLen <= 0) {
				return false;
			}
		}else{
			//�����_�Ȃ��̓G���[
			return false;
		}
		return true;
	}
	
	/**
	 * ���̐����܂��͐��̏������ǂ����`�F�b�N����
	 * �������̐擪��0���܂ސ����̕�����̓G���[�ɂȂ�܂��B
	 * @param aStr �`�F�b�N�Ώۂ̕�����
	 * @return �����񂪐��̏����̏ꍇ<tt>true</tt>��Ԃ�
	 */
	public static boolean isPositiveNumeric(String aStr) {
		if (aStr == null) {
			return false;
		}
		int ilen = aStr.length();
		if (ilen == 0) {
			return false;
		}

		char srcChar;
		int iIndex = 0;
		boolean bFindFlag = false;
		boolean bFindDecimalPoint = false;
		int iLeftLen = 0;
		int iRightLen = 0;

		srcChar = aStr.charAt(iIndex);
		if (srcChar == '-' /* || srcChar == '+' */) {
			iIndex++;
			if (ilen == 1) {
				return false; //���������̓G���[
			}
			if( srcChar == '-'){
				return false; //�}�C�i�X�̓G���[
			}
		}
		if (ilen > iIndex ){
			if(aStr.charAt(iIndex) == '0') {
				if (ilen > (iIndex + 1) && aStr.charAt(iIndex + 1) != '.') {
					//�擪0�Ŏ���'.'�łȂ����̂̓G���[
					return false;
				} else if (ilen > (iIndex + 1) && aStr.charAt(iIndex + 1) == '.') {
					// .���オ�Ȃ����̂̓G���[
					iIndex++;
					iLeftLen++;
					if (ilen == iIndex + 1) {
						return false;
					}
				} else if (ilen == (iIndex + 1)) {
					//0�̂��̂�OK
					return true;
				}
			}else
			if(aStr.charAt(iIndex) == '.') {
				if( bFindFlag == true){
					// �����̌�ɂ�0���K�v
					return false;
				}
				// .���オ�Ȃ����̂̓G���[
				bFindDecimalPoint = true;
				iIndex++;
				iLeftLen++;
				if (ilen == iIndex + 1) {
					return false;
				}
			}
		}
		while (iIndex < ilen) {
			srcChar = aStr.charAt(iIndex);
			if (isAsciiDigit(srcChar) == true) { //���l�������ǂ���
				if (bFindDecimalPoint == true) {
					iRightLen++;
					if (iLeftLen <= 0) {
						break;
					}
				} else {
					iLeftLen++;
				}
				//
			} else if (bFindDecimalPoint == false && srcChar == '.') { //�����������ǂ���
				bFindDecimalPoint = true;
				// '.'���Q�̓G���[
			} else {
				break;
			}
			iIndex++;
		}
		if (ilen != iIndex) {
			return false;
		}
		if (bFindDecimalPoint == true) {
			if (iLeftLen <= 0) {
				return false;
			}
			if (iRightLen <= 0) {
				return false;
			}
		}
		return true;
	}

	/**
	 * ���̐����܂��͕��̏������ǂ����`�F�b�N����
	 * �������̐擪��0���܂ސ����̕�����̓G���[�ɂȂ�܂��B
	 * @param aStr �`�F�b�N�Ώۂ̕�����
	 * @return �����񂪕��̐��̏ꍇ<tt>true</tt>��Ԃ�
	 */
	public static boolean isNegativeNumeric(String aStr) {
		if (aStr == null) {
			return false;
		}
		int ilen = aStr.length();
		if (ilen == 0) {
			return false;
		}

		char srcChar;
		int iIndex = 0;
		boolean bFindFlag = false;
		boolean bFindDecimalPoint = false;
		int iLeftLen = 0;
		int iRightLen = 0;

		srcChar = aStr.charAt(iIndex);
		if (srcChar == '-' /* || srcChar == '+' */) {
			iIndex++;
			if (ilen == 1) {
				return false; //���������̓G���[
			}
//			if( srcChar == '-'){
//				return false; //�}�C�i�X�̓G���[
//			}
		}else{
			return false; //�����Ȃ��̓G���[
		}
		if (ilen > iIndex ){
			if(aStr.charAt(iIndex) == '0') {
				if (ilen > (iIndex + 1) && aStr.charAt(iIndex + 1) != '.') {
					//�擪0�Ŏ���'.'�łȂ����̂̓G���[
					return false;
				} else if (ilen > (iIndex + 1) && aStr.charAt(iIndex + 1) == '.') {
					// .���オ�Ȃ����̂̓G���[
					iIndex++;
					iLeftLen++;
					if (ilen == iIndex + 1) {
						return false;
					}
				} else if (ilen == (iIndex + 1)) {
					//0�̂��̂�OK
					return true;
				}
			}else
			if(aStr.charAt(iIndex) == '.') {
				if( bFindFlag == true){
					// �����̌�ɂ�0���K�v
					return false;
				}
				// .���オ�Ȃ����̂̓G���[
				bFindDecimalPoint = true;
				iIndex++;
				iLeftLen++;
				if (ilen == iIndex + 1) {
					return false;
				}
			}
		}
		while (iIndex < ilen) {
			srcChar = aStr.charAt(iIndex);
			if (isAsciiDigit(srcChar) == true) { //���l�������ǂ���
				if (bFindDecimalPoint == true) {
					iRightLen++;
					if (iLeftLen <= 0) {
						break;
					}
				} else {
					iLeftLen++;
				}
				//
			} else if (bFindDecimalPoint == false && srcChar == '.') { //�����������ǂ���
				bFindDecimalPoint = true;
				// '.'���Q�̓G���[
			} else {
				break;
			}
			iIndex++;
		}
		if (ilen != iIndex) {
			return false;
		}
		if (bFindDecimalPoint == true) {
			if (iLeftLen <= 0) {
				return false;
			}
			if (iRightLen <= 0) {
				return false;
			}
		}
		return true;
	}

	/**
	 * ���̏������ǂ����`�F�b�N����
	 * @param aStr �`�F�b�N�Ώۂ̕�����
	 * @return �����񂪐��̏����̏ꍇ<tt>true</tt>��Ԃ�
	 */
	public static boolean isPositiveFloat(String aStr) {
		if (aStr == null) {
			return false;
		}
		int ilen = aStr.length();
		if (ilen == 0) {
			return false;
		}

		char srcChar;
		int iIndex = 0;
		boolean bFindFlag = false;
		boolean bFindDecimalPoint = false;
		int iLeftLen = 0;
		int iRightLen = 0;

		srcChar = aStr.charAt(iIndex);
		if (srcChar == '-' || srcChar == '+') {
			iIndex++;
			if (ilen == 1) {
				return false; //���������̓G���[
			}
			if( srcChar == '-'){
				return false; //�}�C�i�X�̓G���[
			}
		}
		if (ilen > iIndex ){
			if(aStr.charAt(iIndex) == '0') {
				if (ilen > (iIndex + 1) && aStr.charAt(iIndex + 1) != '.') {
					//�擪0�Ŏ���'.'�łȂ����̂̓G���[
					return false;
				} else if (ilen > (iIndex + 1) && aStr.charAt(iIndex + 1) == '.') {
					// .���オ�Ȃ����̂̓G���[
					iIndex++;
					iLeftLen++;
					if (ilen == iIndex + 1) {
						return false;
					}
				} else if (ilen == (iIndex + 1)) {
					//0�̂��̂�OK
					return true;
				}
			}else
			if(aStr.charAt(iIndex) == '.') {
				if( bFindFlag == true){
					// �����̌�ɂ�0���K�v
					return false;
				}
				// .���オ�Ȃ����̂̓G���[
				bFindDecimalPoint = true;
				iIndex++;
				iLeftLen++;
				if (ilen == iIndex + 1) {
					return false;
				}
			}
		}
		while (iIndex < ilen) {
			srcChar = aStr.charAt(iIndex);
			if (isAsciiDigit(srcChar) == true) { //���l�������ǂ���
				if (bFindDecimalPoint == true) {
					iRightLen++;
					if (iLeftLen <= 0) {
						break;
					}
				} else {
					iLeftLen++;
				}
				//
			} else if (bFindDecimalPoint == false && srcChar == '.') { //�����������ǂ���
				bFindDecimalPoint = true;
				// '.'���Q�̓G���[
			} else {
				break;
			}
			iIndex++;
		}
		if (ilen != iIndex) {
			return false;
		}
		if (bFindDecimalPoint == true) {
			if (iLeftLen <= 0) {
				return false;
			}
			if (iRightLen <= 0) {
				return false;
			}
		}else{
			//�����_�Ȃ��̓G���[
			return false;
		}
		return true;
	}

	/**
	 * ���̏������ǂ����`�F�b�N����
	 * @param aStr �`�F�b�N�Ώۂ̕�����
	 * @return �����񂪕��̏����̏ꍇ<tt>true</tt>��Ԃ�
	 */
	public static boolean isNegativeFloat(String aStr) {
		if (aStr == null) {
			return false;
		}
		int ilen = aStr.length();
		if (ilen == 0) {
			return false;
		}

		char srcChar;
		int iIndex = 0;
		boolean bFindFlag = false;
		boolean bFindDecimalPoint = false;
		int iLeftLen = 0;
		int iRightLen = 0;

		srcChar = aStr.charAt(iIndex);
		if (srcChar == '-' || srcChar == '+') {
			iIndex++;
			if (ilen == 1) {
				return false; //���������̓G���[
			}
//			if( srcChar == '-'){
//				return false; //�}�C�i�X�̓G���[
//			}
		}else{
			return false; //�����Ȃ��̓G���[
		}
		if (ilen > iIndex ){
			if(aStr.charAt(iIndex) == '0') {
				if (ilen > (iIndex + 1) && aStr.charAt(iIndex + 1) != '.') {
					//�擪0�Ŏ���'.'�łȂ����̂̓G���[
					return false;
				} else if (ilen > (iIndex + 1) && aStr.charAt(iIndex + 1) == '.') {
					// .���オ�Ȃ����̂̓G���[
					iIndex++;
					iLeftLen++;
					if (ilen == iIndex + 1) {
						return false;
					}
				} else if (ilen == (iIndex + 1)) {
					//0�̂��̂�OK
					return true;
				}
			}else
			if(aStr.charAt(iIndex) == '.') {
				if( bFindFlag == true){
					// �����̌�ɂ�0���K�v
					return false;
				}
				// .���オ�Ȃ����̂̓G���[
				bFindDecimalPoint = true;
				iIndex++;
				iLeftLen++;
				if (ilen == iIndex + 1) {
					return false;
				}
			}
		}
		while (iIndex < ilen) {
			srcChar = aStr.charAt(iIndex);
			if (isAsciiDigit(srcChar) == true) { //���l�������ǂ���
				if (bFindDecimalPoint == true) {
					iRightLen++;
					if (iLeftLen <= 0) {
						break;
					}
				} else {
					iLeftLen++;
				}
				//
			} else if (bFindDecimalPoint == false && srcChar == '.') { //�����������ǂ���
				bFindDecimalPoint = true;
				// '.'���Q�̓G���[
			} else {
				break;
			}
			iIndex++;
		}
		if (ilen != iIndex) {
			return false;
		}
		if (bFindDecimalPoint == true) {
			if (iLeftLen <= 0) {
				return false;
			}
			if (iRightLen <= 0) {
				return false;
			}
		}else{
			//�����_�Ȃ��̓G���[
			return false;
		}
		return true;
	}

	/**
	 * ���l�͈͂��`�F�b�N����
	 * @param aStr �`�F�b�N�Ώۂ̕�����
	 * @param aMin �ŏ��l
	 * @param aMax �ő�l
	 * @return �����񂪎w�肳�ꂽ���l�͈�(�ŏ��l<=�`�F�b�N�Ώ�<=�ő�l)
	 * �Ɏ��܂�ꍇ<tt>true</tt>��Ԃ�
	 */
	public static boolean isIntegerRangeIn(String aStr, int aMin, int aMax) {
		if (isInteger(aStr) == false) {
			return false;
		}
		long lValue = 0;
		try {
			lValue = Long.parseLong(aStr);
		} catch (Exception ex) {
			return false; //�ϊ��G���[
		}
		if (lValue > aMax || lValue < aMin) {
			return false; //�͈͊O
		}
		return true;
	}
	
	/**
	 * ���l�͈͂��`�F�b�N����
	 * @param aStr �`�F�b�N�Ώۂ̕�����
	 * @param aMin �ŏ��l
	 * @param aMax �ő�l
	 * @return �����񂪎w�肳�ꂽ���l�͈�(�ŏ��l>�`�F�b�N�Ώ� or �`�F�b�N�Ώ�>�ő�l)
	 * �ɂ�����ꍇ<tt>true</tt>��Ԃ�
	 */
	public static boolean isIntegerRangeOut(String aStr, int aMin, int aMax) {
		if (isInteger(aStr) == false) {
			return false;
		}
		long lValue = 0;
		try {
			lValue = Long.parseLong(aStr);
		} catch (Exception ex) {
			return false; //�ϊ��G���[
		}
		if (lValue > aMax || lValue < aMin) {
			return true; //�͈͓�
		}
		return false;
	}

	/**
	 * ���l���r�`�F�b�N����
	 * @param aStr �`�F�b�N�Ώۂ̕�����
	 * @param aMax �ő�l
	 * @return �����񂪎w�肳�ꂽ���l���傫���ꍇ<tt>true</tt>��Ԃ�
	 */
	public static boolean isGreaterThan(String aStr, int aMax) {
		if (isNumeric(aStr) == false) {
			return false;
		}
		long lValue = 0;
		try {
			lValue = Long.parseLong(aStr);
		} catch (Exception ex) {
			return false; //�ϊ��G���[
		}
		if (lValue <= aMax ) {
			return false; //�͈͊O
		}
		return true;
	}

	/**
	 * ���l���r�`�F�b�N����
	 * @param aStr �`�F�b�N�Ώۂ̕�����
	 * @param aMax �ő�l
	 * @return �����񂪎w�肳�ꂽ���l���傫���ꍇ<tt>true</tt>��Ԃ�
	 */
	public static boolean isGreaterThan(String aStr, double aMax) {
		if (isNumeric(aStr) == false) {
			return false;
		}
		double dValue = 0;
		try {
			dValue = Double.parseDouble(aStr);
		} catch (Exception ex) {
			return false; //�ϊ��G���[
		}
		if (dValue <= aMax ) {
			return false; //�͈͊O
		}
		return true;
	}

	/**
	 * ���l���r�`�F�b�N����
	 * @param aStr �`�F�b�N�Ώۂ̕�����
	 * @param aMin �ŏ��l
	 * @return �����񂪎w�肳�ꂽ���l��菬�����ꍇ<tt>true</tt>��Ԃ�
	 */
	public static boolean isLessThan(String aStr, int aMin) {
		if (isNumeric(aStr) == false) {
			return false;
		}
		long lValue = 0;
		try {
			lValue = Long.parseLong(aStr);
		} catch (Exception ex) {
			return false; //�ϊ��G���[
		}
		if (lValue >= aMin ) {
			return false; //�͈͊O
		}
		return true;
	}

	/**
	 * ���l���r�`�F�b�N����
	 * @param aStr �`�F�b�N�Ώۂ̕�����
	 * @param aMin �ŏ��l
	 * @return int �����񂪎w�肳�ꂽ���l��菬�����ꍇ<tt>true</tt>��Ԃ�
	 */
	public static boolean isLessThan(String aStr, double aMin) {
		if (isNumeric(aStr) == false) {
			return false;
		}
		double dValue = 0;
		try {
			dValue = Double.parseDouble(aStr);
		} catch (Exception ex) {
			return false; //�ϊ��G���[
		}
		if (dValue >= aMin ) {
			return false; //�͈͊O
		}
		return true;
	}

	/**
	 * �������̌������`�F�b�N����
	 * @param aStr �`�F�b�N�Ώۂ̕�����
	 * @param aScaleMin �ŏ�����
	 * @param aScaleMax �ő包��
	 * @return ������̐��������w�肳�ꂽ����(�ŏ�����<=�`�F�b�N�Ώی���<=�ő包��)
	 * �Ɏ��܂�ꍇ<tt>true</tt>��Ԃ�
	 */
	public static boolean integerPartScaleIn(String aStr, int aScaleMin,int aScaleMax) {
		if (isNumeric(aStr) == false) {
			return false;
		}
		//���������擾����
		int length = aStr.indexOf('.');
		if( length < 0){
			length = aStr.length();
		}
		int iScaleCount = 0;
		int lastNonZeroScale = 0;
		int iIndex = length;
		char ch;
		while( iIndex > 0 ){
			ch = aStr.charAt(--iIndex);
			if (isAsciiDigit(ch) == true) { //���l�������ǂ���
				iScaleCount++;
				if( ch != '0'){
					lastNonZeroScale = iScaleCount;
				}
			}else{
				break;
			}
		}
		if(lastNonZeroScale <= aScaleMax && lastNonZeroScale >= aScaleMin) {
			return true; //�͈͓�
		}
		return false;
	}

	/**
	 * �������̌������`�F�b�N����
	 * @param aStr �`�F�b�N�Ώۂ̕�����
	 * @param aScaleMin �ŏ�����
	 * @param aScaleMax �ő包��
	 * @return ������̏��������w�肳�ꂽ����(�ŏ�����<=�`�F�b�N�Ώی���<=�ő包��)
	 * �Ɏ��܂�ꍇ<tt>true</tt>��Ԃ�
	 */
	public static boolean decimalPartScaleIn(String aStr, int aScaleMin,int aScaleMax) {
		if (isNumeric(aStr) == false) {
			return false;
		}
		//���������擾����
		int iIndex = aStr.indexOf('.');
		if( iIndex < 0){
			iIndex = 0;
			// �������Ȃ�
			if(iIndex <= aScaleMax && iIndex >= aScaleMin) {
				return true; //�͈͓�
			}
			return false; //�͈͊O
		}
		int iScaleCount = 0;
		int lastNonZeroScale = 0;
		int iLength = aStr.length() -1;
		char ch;
		while( iIndex < iLength ){
			ch = aStr.charAt(++iIndex);
			if (isAsciiDigit(ch) == true) { //���l�������ǂ���
				iScaleCount++;
				if( ch != '0'){
					lastNonZeroScale = iScaleCount;
				}
			}else{
				break;
			}
		}
		if(lastNonZeroScale <= aScaleMax && lastNonZeroScale >= aScaleMin) {
			return true; //�͈͓�
		}
		return false;
	}

	/**
	 * ���ׂĐ��l�����ǂ����`�F�b�N����B<br>
	 * �����t���̕�����̓G���[�B<br>
	 * ���l�\���̃`�F�b�N�͂��Ȃ��B<br>
	 * @param aStr �`�F�b�N�Ώ̕�����
	 * @return int �����񂪐���(0-9)�����̏ꍇ<tt>true</tt>��Ԃ�
	 */
	public static boolean isAllNumericChar(String aStr) {
		if (aStr == null){
			return false;
		}
		char srcChar;
		int ilen = aStr.length();
		if (ilen == 0){
			return false;
		}
		int iIndex = 0;
//		int iLoopIndex = 0;
		while (iIndex < ilen) {
			srcChar = aStr.charAt(iIndex);
			if (isAsciiDigit(srcChar) == false) { //���l�������ǂ���
				break;
			}
			iIndex++;
//			iLoopIndex++;
		}
		if (ilen != iIndex) {
			return false;
		}
		return true;
	}

	/**
	 * �J���}��؂���͂��ꂽ�����ǂ����`�F�b�N����B
	 * �J���}��؂�̊Ԋu��3���B
	 * 3���ł͂Ȃ���؂�Ԋu�̓G���[�ƂȂ�B
	 * @param aStr �`�F�b�N�Ώۂ̕�����
	 * @return int �J���}��؂���͂��ꂽ����������̏ꍇ<tt>true</tt>��Ԃ�
	 */
	public static boolean isFormattedInteger(String aStr) {
		if (aStr == null){
			return false;
		}
		int length = aStr.length();
		if( length <= 0){
			return false; //�G���[
		}

		int i = 0;
		char ch = aStr.charAt(0);
		if (ch == '-' /*|| ch == '+'*/) {
			i++;
		}
		if (length > i) {
			if (aStr.charAt(i) == '0') {
				i++;
				if (length == i) {
					return true; // -0 ����
				}
				
				// �擪0�̐����̓G���[
				return false; //�G���[
			}
		} else {
			// �����̂�
			return false; //�G���[
		}
		int iCount = 0;
		int iLoopCount = length;
		int j = length;
//		int iDelimCount = 0;
		while (i < length) {
			i++;
			iLoopCount++;
			ch = aStr.charAt(--j);
			if (ch == ',') { //��؂蕶�����ǂ���
				if(iLoopCount != 1 && iCount != 3){
					//3�����̊Ԃ��󂢂Ă��Ȃ��ꍇ
					return false; //�G���[
				}
				iCount = 0;
//				iDelimCount++;
			}else
			if (isAsciiDigit(ch) == false) {
				return false; //�G���[
			}else{
				iCount++;
			}
			if( iCount > 3){
				//3�����ȏ�̊Ԃ��󂢂��ꍇ
				return false; //�G���[
			}
		}
		return true;
	}

	/**
	 * �����܂��́A�J���}��؂���͂��ꂽ�����ǂ����`�F�b�N����B
	 * �J���}��؂���͂̏ꍇ�A��؂�Ԋu��3���B3���ł͂Ȃ���؂�Ԋu�̓G���[�ƂȂ�B
	 * @param aStr �`�F�b�N�Ώۂ̕�����
	 * @return int �����܂��̓J���}��؂���͂��ꂽ����������̏ꍇ<tt>true</tt>��Ԃ�
	 */
	public static boolean isIntegerOrFormattedInteger(String aStr) {
		if (aStr == null){
			return false;
		}
		if( aStr.indexOf(',') > 0){
			//�t�H�[�}�b�g����
			return isFormattedInteger(aStr);
		}
		//�t�H�[�}�b�g�Ȃ�
		return isInteger(aStr);
	}

	/**
	 * �����܂������ŁA�������̓J���}��؂肩�ǂ����`�F�b�N����B
	 * �J���}��؂���͂̏ꍇ�A��؂�Ԋu��3���B3���ł͂Ȃ���؂�Ԋu�̓G���[�ƂȂ�B
	 * @param aStr �`�F�b�N�Ώۂ̕�����
	 * @return int �����܂������ŁA�������̓J���}��؂�̏ꍇ<tt>true</tt>��Ԃ�
	 */
	public static boolean isFormattedNumeric(String aStr) {
		if (aStr == null){
			return false;
		}
		int length = aStr.length();
		if( length <= 0){
			return false; //�G���[
		}

		int i = 0;
		char ch = aStr.charAt(0);
		if (ch == '-' /*|| ch == '+'*/) {
			i++;
		}
		if (length > i) {
			if (aStr.charAt(i) == '0') {
				i++;
				if (length == i) {
					return true; // -0 ����
				}
				
				// �擪0�̐����̓G���[
				return false; //�G���[
			}
		} else {
			// �����̂�
			return false; //�G���[
		}
		int iLoopLength = length;
		int j = length;
		
		int k = i;
		while (k < length) {
			ch = aStr.charAt(k++);
			if (ch == '.') { //�����_���ǂ���
				iLoopLength = k-1;
				break;	//�����_��������
			}
		}
		//�����_�ȉ�
		while (k < length) {
			ch = aStr.charAt(k++);
			if (isAsciiDigit(ch) == false) {
				return false; //�G���[
			}
		}

		// ������
//		int iDelimCount = 0;
		int iCount = 0;
		int iLoopCount = 0;
		j = iLoopLength ;
		while (i < iLoopLength) {
			i++;
			iLoopCount++;
			ch = aStr.charAt(--j);
			if (ch == ',') { //��؂蕶�����ǂ���
				if(iLoopCount != 1 && iCount != 3){
					//3�����̊Ԃ��󂢂Ă��Ȃ��ꍇ
					return false; //�G���[
				}
				iCount = 0;
//				iDelimCount++;
			}else
			if (isAsciiDigit(ch) == false) {
				return false; //�G���[
			}else{
				iCount++;
			}
			if( iCount > 3){
				//3�����ȏ�̊Ԃ��󂢂��ꍇ
				return false; //�G���[
			}
		}
		return true;
	}

	/**
	 * �����܂������܂��͐��������J���}��؂肳�ꂽ���l�����񂩂ǂ����`�F�b�N����B
	 * �J���}��؂���͂̏ꍇ�A��؂�Ԋu��3���B3���ł͂Ȃ���؂�Ԋu�̓G���[�ƂȂ�B
	 * @param aStr �`�F�b�N�Ώۂ̕�����
	 * @return ���l������܂��͐��������J���}��؂肳�ꂽ���l������̏ꍇ<tt>true</tt>��Ԃ�
	 */
	public static boolean isNumericOrFormattedNumeric(String aStr) {
		if (aStr == null){
			return false;
		}
		if( aStr.indexOf(',') > 0){
			//�t�H�[�}�b�g����
			return isFormattedNumeric(aStr);
		}
		//�t�H�[�}�b�g�Ȃ�
		return isNumeric(aStr);
	}

	/**
	 * ���ׂĂQ�o�C�g�J�i�����ǂ����`�F�b�N����
	 * @param aStr �`�F�b�N�Ώە�����
	 * @return �Ώە�����2�o�C�g�J�i���������̏ꍇ<tt>true</tt>��Ԃ�
	 */
	public static boolean isAll2ByteKana(String aStr) {
		if( aStr == null ){
			return false;
		}
		int ilen = aStr.length();
		if( ilen <=0 ){
			return false;
		}
		int iIndex = 0;
		char checkChar;

		while (iIndex < ilen) {
			checkChar = aStr.charAt(iIndex);
			//�R�[�h�͈͂Ń`�F�b�N
			if (checkChar >= '\u30a1' && checkChar <= '\u30ff') {
				//�J�i ����
			} else if (checkChar == '\u309b' || checkChar == '\u309c') {
				// ���_�A���_
			} else if (checkChar == CHAR_SPACE_2BYTE) {
				//��
			} else {
				//�J�i�ł͂Ȃ�
				break;
			}
			iIndex++;
		}
		if (ilen != iIndex) {
			return false; //�ُ�I��
		}
		return true; //����I��
	}

	/**
	 * ���ׂĔ��p�J�i�����ǂ����`�F�b�N����B
	 * @param aStr �`�F�b�N�Ώە�����
	 * @return �Ώە����񂪔��p�J�i���������̏ꍇ<tt>true</tt>��Ԃ�
	 */
	public static boolean isAll1ByteKana(String aStr) {
		if( aStr == null ){
			return false;
		}
		int ilen = aStr.length();
		if( ilen <=0 ){
			return false;
		}
		int iIndex = 0;
		char checkChar;

		while (iIndex < ilen) {
			checkChar = aStr.charAt(iIndex);
			//�R�[�h�͈͂Ń`�F�b�N
			if (checkChar >= '\uff61' && checkChar <= '\uff9f') {
				// JIS �J�i
//			}else
//			if (checkChar >= '\u0020' && checkChar <= '\u007d') {
//				// ASCII ����
//			}else
//			if (checkChar == '\u00a5') {
//				//�~�L��
			}else{
				//���p�J�i�ł͂Ȃ�
				break;
			}
			iIndex++;
		}
		if (ilen != iIndex) {
			return false; //�ُ�I��
		}
		return true; //����I��
	}

	
	/**
	 * ���p�J�i�������܂ނ��ǂ����`�F�b�N����
	 * @param aStr �`�F�b�N�Ώە�����
	 * @return �Ώە�����ɔ��p�J�i�������܂ޏꍇ<tt>true</tt>��Ԃ�
	 */
	public static boolean contains1ByteKana(String aStr) {
		if( aStr == null ){
			return false;
		}
		int ilen = aStr.length();
		if( ilen <=0 ){
			return false;
		}
		int iIndex = 0;
		char checkChar;

		while (iIndex < ilen) {
			checkChar = aStr.charAt(iIndex);
			//�R�[�h�͈͂Ń`�F�b�N
			if (checkChar >= '\uff61' && checkChar <= '\uff9f') {
				// JIS �J�i
				return true;
//			}else
//			if (checkChar >= '\u0020' && checkChar <= '\u007d') {
//				// ASCII ����
//			}else
//			if (checkChar == '\u00a5') {
//				//�~�L��
//			}else{
//				//���p�J�i�ł͂Ȃ�
//				//break;
			}
			iIndex++;
		}
		if (ilen != iIndex) {
			return true; //����I��
		}
		return false; //�ُ�I��
	}

	/**
	 * ���ׂ�1�o�C�g�����ǂ����`�F�b�N����
	 * �L���Ȃǂ��܂�
	 * @param aStr �`�F�b�N�Ώە�����
	 * @return int �P�o�C�g���������̏ꍇ 0��Ԃ�
	 */
	public static boolean isAll1ByteAlpha(String aStr) {
		if( aStr == null ){
			return false;
		}
		int ilen = aStr.length();
		if( ilen <=0 ){
			return false;
		}
		int iIndex = 0;
		char checkChar;

		while (iIndex < ilen) {
			checkChar = aStr.charAt(iIndex);
			//�R�[�h�͈͂Ń`�F�b�N
			if (checkChar >= '\u0020' && checkChar <= '\u00ff') {
				// Do Nothing
			} else {
				//�P�o�C�g�����ł͂Ȃ�
				break;
			}
			iIndex++;
		}
		if (ilen != iIndex) {
			return false; //�ُ�I��
		}
		return true; //����I��
	}

	/**
	 * 1�o�C�g�������܂ނ��ǂ����`�F�b�N����
	 * @param aStr �`�F�b�N�Ώە�����
	 * @return �P�o�C�g�������܂ޏꍇ<tt>true</tt>��Ԃ�
	 */
	public static boolean contains1ByteAlpha(String aStr) {
		int ilen = aStr.length();
		int iIndex = 0;
		char checkChar;

		while (iIndex < ilen) {
			checkChar = aStr.charAt(iIndex);
			//�R�[�h�͈͂Ń`�F�b�N
			if (checkChar >= '\u0020' && checkChar <= '\u00ff') {
				break;
			}
			iIndex++;
		}
		if (ilen != iIndex) {
			return true; //�܂�
		}
		return false; //�܂܂Ȃ�
	}

//	/**
//	 * �d�b�ԍ����ǂ����`�F�b�N����
//	 * @param aStr �`�F�b�N�Ώە�����
//	 * @return int �d�b�ԍ��̏ꍇ 0��Ԃ�
//	 */
//	public static boolean isTelNumber(String aStr) {
//		int ilen = aStr.length();
//		int iIndex = 0;
//		char checkChar;
//
//		while (iIndex < ilen) {
//			checkChar = aStr.charAt(iIndex);
//			//�R�[�h�͈͂Ń`�F�b�N
//			if (checkChar >= '\u0030' && checkChar <= '\u0039') {
//				//����
//			} else if (checkChar == '\u002D') {
//				// �n�C�t��
//				if (iIndex == 0) {
//					// �擪	
//					break;
//				} else if (iIndex == (ilen - 1)) {
//					//����
//					break;
//				}
//			} else {
//				//�ł͂Ȃ�
//				break;
//			}
//			iIndex++;
//		}
//		if (ilen != iIndex) {
//			return false; //�ُ�I��
//		}
//		return true; //����I��
//	}

//	/**
//	 * ���[���A�h���X�`�F�b�N����
//	 * @param aStr �`�F�b�N�Ώە�����
//	 * @return int ���[���A�h���X�̏ꍇ<tt>true</tt>��Ԃ�
//	 */
//	public static boolean isMailAddress(String aStr) {
//		int ilen = aStr.length();
//		int iIndex = 0;
//		int iAtMarkCount = 0;
//		int iDotIndex = 0;
//		char checkChar;
//
//		// @�̌�̓h�b�g�C���Ȃ��ł�OK aaa@aaa�`���ŉ�
//
//		while (iIndex < ilen) {
//			checkChar = aStr.charAt(iIndex);
//			//�R�[�h�͈͂Ń`�F�b�N
//			if (checkChar >= '\u005E' && checkChar <= '\u007E') {
//				// 0x5E(^),0x5F(_),0x60(`),0x61-0x7A(a-z),0x7b-0x7e({,|,},~),
//			} else if (checkChar >= '\u0030' && checkChar <= '\u0039') {
//				//0x30-0x39(0-9),
//			} else if (checkChar >= '\u0041' && checkChar <= '\u005A') {
//				// 0x41-0x5A(A-Z),
//			} else if (checkChar == '\u002D' || checkChar == '\u002F') {
//				// 2D(-),2F(/)
//			} else if (checkChar == '\u002E') {
//				// .
//				if (iIndex == 0) {
//					// �擪
//					break;
//				}
//				iDotIndex = iIndex;
//			} else if (checkChar == '\u0040') {
//				// @
//				if (iIndex == 0) {
//					// �擪
//					break;
//				} else if (iIndex == (ilen - 1)) {
//					//����
//					break;
//				} else if (iDotIndex > 0 && (iDotIndex + 1) == iIndex) {
//					//�h�b�g�̎�
//					break;
//				}
//				if (iAtMarkCount > 1) {
//					break;
//				}
//				iAtMarkCount++;
//			} else if (checkChar >= '\u0023' && checkChar <= '\u0026') {
//				// 23(#),24($),25(%),26(&),
//			} else if (checkChar >= '\u003D' && checkChar <= '\u003F') {
//				// 0x3D(=),0x3F(?),
//			} else if (checkChar == '\u0021' || checkChar == '\'' || checkChar == '\u002A' || checkChar == '\u002B') {
//				//0x21(!),27('),2A(*),2B(+),
//			} else {
//				//�ł͂Ȃ�
//				break;
//			}
//			iIndex++;
//		}
//		if (ilen != iIndex) {
//			return false; //�ُ�I��
//		}
//		if (iAtMarkCount != 1) {
//			return false; //�ُ�I��
//		}
//		return true; //����I��
//	}

//	/**
//	 * ���ׂ�0�̕����񂩂ǂ����`�F�b�N����
//	 * @param aStr �`�F�b�N�Ώە�����
//	 * @param aLength �����񒷂��A0�̏ꍇ�������肵�Ȃ�
//	 * @return int ���ׂ�0�̕�����̏ꍇ<tt>true</tt>��Ԃ��B
//	 */
//	public static boolean isAllZero(String aStr, int aLength) {
//		int iLength = aStr.length();
//		int i = 0;
//		for (; i < iLength; i++) {
//			if (aStr.charAt(i) != '0') {
//				return false; //�ُ�I��
//			}
//		}
//		if (aLength != 0 && i != aLength) {
//			return false; //�ُ�I��
//		}
//		return true; //����I��
//	}

	/**
	 * �󕶎��񂩂ǂ����`�F�b�N����B
	 * @param aStr �`�F�b�N�Ώە�����
	 * @return null�܂��͋󕶎���܂��͂��ׂĔ��p��/�S�p�󔒂̏ꍇtrue��Ԃ�
	 */
	public static boolean isEmpty(String aStr) {
		if(aStr == null) {
			return true;
		}
		int iLength = aStr.length();
		if(iLength <= 0) {
			return true;
		}
		char ch;
		for(int i = 0; i < iLength ; i++){
			ch = aStr.charAt(i++);
			if( ch > ' ' && ch != CHAR_SPACE_2BYTE ){
				//�󔒈ȊO������
				return false;
			}
		}
		return true;
	}

	/**
	 * �����ŗ^����ꂽ�����񂪂��ׂċ󕶎��񂩂ǂ����`�F�b�N����B
	 * @param aStrings �`�F�b�N�Ώە�����(�ϒ�)
	 * @return ���ׂĂ̕�����isEmpty�̌Ăяo���ł�true��Ԃ��ꍇ�A�ꍇ<tt>true</tt>��Ԃ�
	 */
	public static boolean isEmptyAll(String... aStrings) {
		if(aStrings == null) {
			return true;
		}
		for(String aStr: aStrings){
			if( isEmpty(aStr) == false){
				return false;
			}
		}
		return true;
	}

	/**
	 * �����ŗ^����ꂽ�����񂪂��ׂċ󕶎���ł͂Ȃ����`�F�b�N����B
	 * @param aStrings �`�F�b�N�Ώە�����(�ϒ�)
	 * @return ���ׂĂ̕�����isEmpty�̌Ăяo���ł�false��Ԃ��ꍇ�A�ꍇ<tt>true</tt>��Ԃ�
	 */
	public static boolean isNotEmptyAll(String... aStrings) {
		if(aStrings == null || aStrings.length <= 0) {
			return false;
		}
		for(String aStr: aStrings){
			if( isEmpty(aStr) == true){
				return false;
			}
		}
		return true;
	}

	/**
	 * �����ŗ^����ꂽ������̂ǂꂩ���󕶎���ł͂Ȃ����`�F�b�N����B
	 * @param aStrings �`�F�b�N�Ώە�����(�ϒ�)
	 * @return �ǂꂩ�̕�����isEmpty�̌Ăяo���ł�false��Ԃ��ꍇ�A�ꍇ<tt>true</tt>��Ԃ�
	 */
	public static boolean isNotEmptyAny(String... aStrings) {
		if(aStrings == null || aStrings.length <= 0) {
			return false;
		}
		for(String aStr: aStrings){
			if( isEmpty(aStr) == false){
				return true;
			}
		}
		// ���ׂċ�
		return false;
	}
	
	
	/**
	 * ������̒����͈̔͂��`�F�b�N����B
	 * @param aStr �`�F�b�N�Ώە�����
	 * @param aMin �����񒷍ŏ��l
	 * @param aMax �����񒷍ő�l
	 * @return �����񒷂��w�肳�ꂽ�͈�(�ŏ��l<=�`�F�b�N�Ώ�<=�ő�l)
	 * �Ɏ��܂�ꍇ<tt>true</tt>��Ԃ�
	 */
	public static boolean isLengthRangeIn(String aStr,int aMin,int aMax) {
		if(aStr == null) {
			if( aMin <= 0){
				return true;
			}
			return false;
		}
		int iLength = aStr.length();
		if(iLength <= 0) {
			if( aMin <= 0){
				return true;
			}
			return false;
		}
		if (iLength > aMax || iLength < aMin) {
			return false; //�͈͊O
		}
		return true;
	}

	/**
	 * ������̒����͈̔͂��`�F�b�N����B<br>
	 * �O��̋󔒂��g�������āA�L���ȕ�����̒����𑪂�B<br>
	 * @param aStr �`�F�b�N�Ώە�����
	 * @param aMin �����񒷍ŏ��l
	 * @param aMax �����񒷍ő�l
	 * @return �����񒷂��w�肳�ꂽ�͈�(�ŏ��l<=�`�F�b�N�Ώ�<=�ő�l)
	 * �Ɏ��܂�ꍇ<tt>true</tt>��Ԃ�
	 */
	public static boolean isTrimmedLengthRangeIn(String aStr,int aMin,int aMax) {
		if(aStr == null) {
			if( aMin <= 0){
				return true;
			}
			return false;
		}
		int iLength = aStr.length();
		if(iLength <= 0) {
			if( aMin <= 0){
				return true;
			}
			return false;
		}
		int iEnd = iLength -1;	//�I���ʒu
		int iStart = iEnd;			//�J�n�ʒu
		
		char ch;
		//�O�g����
		for(int i = 0 ; i < iLength ; i++){
			ch = aStr.charAt(i);
			if( ch > ' ' && ch != CHAR_SPACE_2BYTE ){
				//�󔒈ȊO������
				iStart = i;
				break;
			}
		}
		if( iStart == iEnd){
			//���ׂċ�
			if( aMin <= 0){
				return true;
			}
			return false;
		}
		//���g����
		for(int i = iEnd ; i >= 0 ; i--){
			ch = aStr.charAt(i);
			if( ch > ' ' && ch != CHAR_SPACE_2BYTE ){
				//�󔒈ȊO������
				iEnd = i;
				break;
			}
		}
		iLength = iEnd - iStart + 1;
		if (iLength > aMax || iLength < aMin) {
			return false; //�͈͊O
		}
		return true;
	}

	/**
	 * ������̍ő咷�����`�F�b�N����B
	 * @param aStr �`�F�b�N�Ώە�����
	 * @param aMax �����񒷍ő�l
	 * @return �����񒷂��w�肳�ꂽ�ő�l�ȉ��̏ꍇ<tt>true</tt>��Ԃ�
	 */
	public static boolean isLengthLessThan(String aStr,int aMax) {
		return isLengthRangeIn(aStr,0,aMax);
	}


	/**
	 * ������̍ŏ��������`�F�b�N����B
	 * @param aStr �`�F�b�N�Ώە�����
	 * @param aMin �����񒷍ŏ��l
	 * @return �����񒷂��w�肳�ꂽ�ŏ��l�ȏ�̏ꍇ<tt>true</tt>��Ԃ�
	 */
	public static boolean isLengthGreaterThan(String aStr,int aMin) {
		return isLengthRangeIn(aStr,aMin,Integer.MAX_VALUE);
	}

	/**
	 * ������̍ő咷�����`�F�b�N����B<br>
	 * �O��̋󔒂��g�������āA�L���ȕ�����̒����𑪂�B<br>
	 * @param aStr �`�F�b�N�Ώە�����
	 * @param aMax �����񒷍ő�l
	 * @return �����񒷂��w�肳�ꂽ�ő�l�ȉ��̏ꍇ<tt>true</tt>��Ԃ�
	 */
	public static boolean isTrimmedLengthLessThan(String aStr,int aMax) {
		return isTrimmedLengthRangeIn(aStr,0,aMax);
	}


	/**
	 * ������̍ŏ��������`�F�b�N����B<br>
	 * �O��̋󔒂��g�������āA�L���ȕ�����̒����𑪂�B<br>
	 * @param aStr �`�F�b�N�Ώە�����
	 * @param aMin �����񒷍ŏ��l
	 * @return �����񒷂��w�肳�ꂽ�ŏ��l�ȏ�̏ꍇ<tt>true</tt>��Ԃ�
	 */
	public static boolean isTrimmedLengthGreaterThan(String aStr,int aMin) {
		return isTrimmedLengthRangeIn(aStr,aMin,Integer.MAX_VALUE);
	}

}