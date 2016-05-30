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


// 極力、文字列をそのままチェックすること。
/**
 * 文字列のチェックのためのユーティリティ関数。
 * 
 * @author T.Hirose
 * @version $Id: StringValidateUtil.java,v 1.2 2012/08/01 09:28:36 tanaka Exp $
 */
public final class StringValidateUtil {
//	/** Version Identifier */ @SuppressWarnings("unused")
	public static final String _VERSION = "$Id: StringValidateUtil.java,v 1.2 2012/08/01 09:28:36 tanaka Exp $"; //$NON-NLS-1$

	/** ２バイト空白文字のキャラクタ型 */
	private static final char CHAR_SPACE_2BYTE = '\u3000';

//	/** 正常終了 */
//	public static final int true = 0;
//	/** 異常終了 */
//	public static final int false = -1;

	/**
	 * 数字の文字(1-9)かどうかを判定する。
	 * @param ch 判定対象文字
	 * @return 数字文字の場合trueを返す。
	 */
	private static final boolean isAsciiDigit(char ch){
		if( ch > '9' || ch < '0'){
			return false;
		}
		return true;
	}
	
	/**
	 * すべて指定された配列の中の文字で構成されているかどうかチェックする
	 * @param aStr チェック対象の文字列
	 * @param aAnyChars 文字列が構成されるべき文字の配列
	 * @return 文字列が配列中の文字からのみ構成される場合、<tt>true</tt>を返す。<br>
	 * 		配列中にない文字を見つけた場合は<tt>false</tt>を返す。
	 */
	public static boolean isAnyCharAll(String aStr , char[] aAnyChars) {
		if (aStr == null){
			return false;
		}
		char srcChar;
		int ilen = aStr.length();
		if( ilen <= 0){
			return false; //異常終了
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
			return false; //異常終了
		}
		return true; //正常終了
	}
//
//	/**
//	 * すべて空白文字どうかチェックする
//	 * ２バイト空白と1バイト空白の両方をチェックする
//	 * @param aStr チェック対象の文字列
//	 * @return 文字列が1バイト空白または2バイト空白だけの場合、<tt>true</tt>を返す
//	 */
//	public static int isAllJPSpace(String aStr) {
//		if (aStr == null){
//			return false;
//		}
//		
//		char srcChar;
//		int ilen = aStr.length();
//		if( ilen <= 0){
//			return false; //異常終了
//		}
//		int iIndex = 0;
//		while (iIndex < ilen) {
//			srcChar = aStr.charAt(iIndex);
//			if (srcChar == ' ') {
//				// 1バイト空白
//			} else if (srcChar == CHAR_SPACE_2BYTE) {
//				// 2バイト空白
//			} else {
//				//空白以外の文字
//				break;
//			}
//			iIndex++;
//		}
//		if (ilen != iIndex) {
//			return false; //異常終了
//		}
//		return true; //正常終了
//	}

	/**
	 * 文字列が整数かどうかチェックする 。
	 * 小数の場合はエラーとなる。
	 * 負号(-)付き0はOK。負号(+)付き文字列はエラー。
	 * 
	 * @param aStr チェック対象の文字列
	 * @return 文字列が整数の場合<tt>true</tt>を返す
	 */
	public static boolean isInteger(String aStr) {
		//		try {
		//			Long.parseLong(aStr);
		//		} catch (Exception ex) {
		//			return false; //変換エラー
		//		}
		////
		if (aStr == null){
			return false;
		}
		int length = aStr.length();
		if( length <= 0){
			return false; //エラー
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
					return true; // -0 正常
				}
				
				// 先頭0の整数はエラー
				return false; //エラー
			}
		} else {
			// 負号のみ
			return false; //エラー
		}
		while (i < length) {
			if (isAsciiDigit(aStr.charAt(i++)) == false) {
				return false; //エラー
			}
		}
		return true;
	}

	/**
	 * 正の整数かどうかチェックする
	 * @param aStr チェック対象の文字列
	 * @return 文字列が正の整数の場合<tt>true</tt>を返す
	 */
	public static boolean isPositiveInteger(String aStr) {
		if (aStr == null){
			return false;
		}
		int length = aStr.length();
		if( length <= 0){
			return false; //エラー
		}
		int i = 0;
		char ch = aStr.charAt(i);
		if (ch == '-') {
			return false; //負の数はエラー
		}
//		if (ch == '+') {		// 正の数の負号はなし
//			i++;
//		}
		if (length > i) {
			if (aStr.charAt(i) == '0') {
				i++;
				if (length == i) {
					return true; // +0 正常
				}
				
				// 先頭0の整数はエラー
				return false; //エラー
			}
		} else {
			return false; //エラー
		}
		while (i < length) {
			if (isAsciiDigit(aStr.charAt(i++)) == false) {
				return false; //エラー
			}
		}
		return true;

	}

	/**
	 * 負の整数かどうかチェックする
	 * @param aStr チェック対象の文字列
	 * @return 文字列が負の整数の場合<tt>true</tt>を返す
	 */
	public static boolean isNegativeInteger(String aStr) {
		if (aStr == null){
			return false;
		}
		int length = aStr.length();
		if( length <= 0){
			return false; //エラー
		}
		int i = 0;
		char ch = aStr.charAt(i);
//		if (ch == '-') {
//			return false; //負の数はエラー
//		}
		if (ch == '-') {		// 負の数
			i++;
		}else{
			return false; //負号なしはエラー
		}
		if (length > i) {
			if (aStr.charAt(i) == '0') {
				i++;
				if (length == i) {
					return true; // +0 正常
				}
				
				// 先頭0の整数はエラー
				return false; //エラー
			}
		} else {
			return false; //エラー
		}
		while (i < length) {
			if (isAsciiDigit(aStr.charAt(i++)) == false) {
				return false; //エラー
			}
		}
		return true;

	}

	/**
	 * 性能比較用
	 * @param aStr チェック対象の文字列
	 * @return 文字列が正の小数の場合<tt>true</tt>を返す
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
	 * 整数または小数かどうかチェックする。
	 * 整数部の先頭に0を含む整数の文字列はエラーになります。
	 * @param aStr チェック対象の文字列
	 * @return 文字列が正の小数の場合<tt>true</tt>を返す
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
				return false; //負号だけはエラー
			}
		}
		if (ilen > iIndex ){
			if(aStr.charAt(iIndex) == '0') {
				if (ilen > (iIndex + 1) && aStr.charAt(iIndex + 1) != '.') {
					//先頭0で次が'.'でないものはエラー
					return false;
				} else if (ilen > (iIndex + 1) && aStr.charAt(iIndex + 1) == '.') {
					// .より後がないものはエラー
					iIndex++;
					iLeftLen++;
					if (ilen == iIndex + 1) {
						return false;
					}
				} else if (ilen == (iIndex + 1)) {
					//0のものはOK
					return true;
				}
			}else
			if(aStr.charAt(iIndex) == '.') {
				if( bFindFlag == true){
					// 負号の後には0が必要
					return false;
				}
				// .より後がないものはエラー
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
			if (isAsciiDigit(srcChar) == true) { //数値文字かどうか
				if (bFindDecimalPoint == true) {
					iRightLen++;
					if (iLeftLen <= 0) {
						break;
					}
				} else {
					iLeftLen++;
				}
				//
			} else if (bFindDecimalPoint == false && srcChar == '.') { //小数文字かどうか
				bFindDecimalPoint = true;
				// '.'が２つはエラー
			} else {
				// エラー
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
	 * 小数かどうかチェックする。
	 * @param aStr チェック対象の文字列
	 * @return 文字列が正の小数の場合<tt>true</tt>を返す
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
				return false; //負号だけはエラー
			}
		}
		if (ilen > iIndex ){
			if(aStr.charAt(iIndex) == '0') {
				if (ilen > (iIndex + 1) && aStr.charAt(iIndex + 1) != '.') {
					//先頭0で次が'.'でないものはエラー
					return false;
				} else if (ilen > (iIndex + 1) && aStr.charAt(iIndex + 1) == '.') {
					// .より後がないものはエラー
					iIndex++;
					iLeftLen++;
					if (ilen == iIndex + 1) {
						return false;
					}
				} else if (ilen == (iIndex + 1)) {
					//0のものはOK
					return true;
				}
			}else
			if(aStr.charAt(iIndex) == '.') {
				if( bFindFlag == true){
					// 負号の後には0が必要
					return false;
				}
				// .より後がないものはエラー
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
			if (isAsciiDigit(srcChar) == true) { //数値文字かどうか
				if (bFindDecimalPoint == true) {
					iRightLen++;
					if (iLeftLen <= 0) {
						break;
					}
				} else {
					iLeftLen++;
				}
				//
			} else if (bFindDecimalPoint == false && srcChar == '.') { //小数文字かどうか
				bFindDecimalPoint = true;
				// '.'が２つはエラー
			} else {
				// エラー
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
			//小数点なしはエラー
			return false;
		}
		return true;
	}
	
	/**
	 * 正の整数または正の小数かどうかチェックする
	 * 整数部の先頭に0を含む整数の文字列はエラーになります。
	 * @param aStr チェック対象の文字列
	 * @return 文字列が正の小数の場合<tt>true</tt>を返す
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
				return false; //負号だけはエラー
			}
			if( srcChar == '-'){
				return false; //マイナスはエラー
			}
		}
		if (ilen > iIndex ){
			if(aStr.charAt(iIndex) == '0') {
				if (ilen > (iIndex + 1) && aStr.charAt(iIndex + 1) != '.') {
					//先頭0で次が'.'でないものはエラー
					return false;
				} else if (ilen > (iIndex + 1) && aStr.charAt(iIndex + 1) == '.') {
					// .より後がないものはエラー
					iIndex++;
					iLeftLen++;
					if (ilen == iIndex + 1) {
						return false;
					}
				} else if (ilen == (iIndex + 1)) {
					//0のものはOK
					return true;
				}
			}else
			if(aStr.charAt(iIndex) == '.') {
				if( bFindFlag == true){
					// 負号の後には0が必要
					return false;
				}
				// .より後がないものはエラー
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
			if (isAsciiDigit(srcChar) == true) { //数値文字かどうか
				if (bFindDecimalPoint == true) {
					iRightLen++;
					if (iLeftLen <= 0) {
						break;
					}
				} else {
					iLeftLen++;
				}
				//
			} else if (bFindDecimalPoint == false && srcChar == '.') { //小数文字かどうか
				bFindDecimalPoint = true;
				// '.'が２つはエラー
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
	 * 負の整数または負の小数かどうかチェックする
	 * 整数部の先頭に0を含む整数の文字列はエラーになります。
	 * @param aStr チェック対象の文字列
	 * @return 文字列が負の数の場合<tt>true</tt>を返す
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
				return false; //負号だけはエラー
			}
//			if( srcChar == '-'){
//				return false; //マイナスはエラー
//			}
		}else{
			return false; //負号なしはエラー
		}
		if (ilen > iIndex ){
			if(aStr.charAt(iIndex) == '0') {
				if (ilen > (iIndex + 1) && aStr.charAt(iIndex + 1) != '.') {
					//先頭0で次が'.'でないものはエラー
					return false;
				} else if (ilen > (iIndex + 1) && aStr.charAt(iIndex + 1) == '.') {
					// .より後がないものはエラー
					iIndex++;
					iLeftLen++;
					if (ilen == iIndex + 1) {
						return false;
					}
				} else if (ilen == (iIndex + 1)) {
					//0のものはOK
					return true;
				}
			}else
			if(aStr.charAt(iIndex) == '.') {
				if( bFindFlag == true){
					// 負号の後には0が必要
					return false;
				}
				// .より後がないものはエラー
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
			if (isAsciiDigit(srcChar) == true) { //数値文字かどうか
				if (bFindDecimalPoint == true) {
					iRightLen++;
					if (iLeftLen <= 0) {
						break;
					}
				} else {
					iLeftLen++;
				}
				//
			} else if (bFindDecimalPoint == false && srcChar == '.') { //小数文字かどうか
				bFindDecimalPoint = true;
				// '.'が２つはエラー
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
	 * 正の小数かどうかチェックする
	 * @param aStr チェック対象の文字列
	 * @return 文字列が正の小数の場合<tt>true</tt>を返す
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
				return false; //符号だけはエラー
			}
			if( srcChar == '-'){
				return false; //マイナスはエラー
			}
		}
		if (ilen > iIndex ){
			if(aStr.charAt(iIndex) == '0') {
				if (ilen > (iIndex + 1) && aStr.charAt(iIndex + 1) != '.') {
					//先頭0で次が'.'でないものはエラー
					return false;
				} else if (ilen > (iIndex + 1) && aStr.charAt(iIndex + 1) == '.') {
					// .より後がないものはエラー
					iIndex++;
					iLeftLen++;
					if (ilen == iIndex + 1) {
						return false;
					}
				} else if (ilen == (iIndex + 1)) {
					//0のものはOK
					return true;
				}
			}else
			if(aStr.charAt(iIndex) == '.') {
				if( bFindFlag == true){
					// 負号の後には0が必要
					return false;
				}
				// .より後がないものはエラー
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
			if (isAsciiDigit(srcChar) == true) { //数値文字かどうか
				if (bFindDecimalPoint == true) {
					iRightLen++;
					if (iLeftLen <= 0) {
						break;
					}
				} else {
					iLeftLen++;
				}
				//
			} else if (bFindDecimalPoint == false && srcChar == '.') { //小数文字かどうか
				bFindDecimalPoint = true;
				// '.'が２つはエラー
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
			//小数点なしはエラー
			return false;
		}
		return true;
	}

	/**
	 * 負の小数かどうかチェックする
	 * @param aStr チェック対象の文字列
	 * @return 文字列が負の小数の場合<tt>true</tt>を返す
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
				return false; //負号だけはエラー
			}
//			if( srcChar == '-'){
//				return false; //マイナスはエラー
//			}
		}else{
			return false; //負号なしはエラー
		}
		if (ilen > iIndex ){
			if(aStr.charAt(iIndex) == '0') {
				if (ilen > (iIndex + 1) && aStr.charAt(iIndex + 1) != '.') {
					//先頭0で次が'.'でないものはエラー
					return false;
				} else if (ilen > (iIndex + 1) && aStr.charAt(iIndex + 1) == '.') {
					// .より後がないものはエラー
					iIndex++;
					iLeftLen++;
					if (ilen == iIndex + 1) {
						return false;
					}
				} else if (ilen == (iIndex + 1)) {
					//0のものはOK
					return true;
				}
			}else
			if(aStr.charAt(iIndex) == '.') {
				if( bFindFlag == true){
					// 負号の後には0が必要
					return false;
				}
				// .より後がないものはエラー
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
			if (isAsciiDigit(srcChar) == true) { //数値文字かどうか
				if (bFindDecimalPoint == true) {
					iRightLen++;
					if (iLeftLen <= 0) {
						break;
					}
				} else {
					iLeftLen++;
				}
				//
			} else if (bFindDecimalPoint == false && srcChar == '.') { //小数文字かどうか
				bFindDecimalPoint = true;
				// '.'が２つはエラー
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
			//小数点なしはエラー
			return false;
		}
		return true;
	}

	/**
	 * 数値範囲をチェックする
	 * @param aStr チェック対象の文字列
	 * @param aMin 最小値
	 * @param aMax 最大値
	 * @return 文字列が指定された数値範囲(最小値<=チェック対象<=最大値)
	 * に収まる場合<tt>true</tt>を返す
	 */
	public static boolean isIntegerRangeIn(String aStr, int aMin, int aMax) {
		if (isInteger(aStr) == false) {
			return false;
		}
		long lValue = 0;
		try {
			lValue = Long.parseLong(aStr);
		} catch (Exception ex) {
			return false; //変換エラー
		}
		if (lValue > aMax || lValue < aMin) {
			return false; //範囲外
		}
		return true;
	}
	
	/**
	 * 数値範囲をチェックする
	 * @param aStr チェック対象の文字列
	 * @param aMin 最小値
	 * @param aMax 最大値
	 * @return 文字列が指定された数値範囲(最小値>チェック対象 or チェック対象>最大値)
	 * にあたる場合<tt>true</tt>を返す
	 */
	public static boolean isIntegerRangeOut(String aStr, int aMin, int aMax) {
		if (isInteger(aStr) == false) {
			return false;
		}
		long lValue = 0;
		try {
			lValue = Long.parseLong(aStr);
		} catch (Exception ex) {
			return false; //変換エラー
		}
		if (lValue > aMax || lValue < aMin) {
			return true; //範囲内
		}
		return false;
	}

	/**
	 * 数値を比較チェックする
	 * @param aStr チェック対象の文字列
	 * @param aMax 最大値
	 * @return 文字列が指定された数値より大きい場合<tt>true</tt>を返す
	 */
	public static boolean isGreaterThan(String aStr, int aMax) {
		if (isNumeric(aStr) == false) {
			return false;
		}
		long lValue = 0;
		try {
			lValue = Long.parseLong(aStr);
		} catch (Exception ex) {
			return false; //変換エラー
		}
		if (lValue <= aMax ) {
			return false; //範囲外
		}
		return true;
	}

	/**
	 * 数値を比較チェックする
	 * @param aStr チェック対象の文字列
	 * @param aMax 最大値
	 * @return 文字列が指定された数値より大きい場合<tt>true</tt>を返す
	 */
	public static boolean isGreaterThan(String aStr, double aMax) {
		if (isNumeric(aStr) == false) {
			return false;
		}
		double dValue = 0;
		try {
			dValue = Double.parseDouble(aStr);
		} catch (Exception ex) {
			return false; //変換エラー
		}
		if (dValue <= aMax ) {
			return false; //範囲外
		}
		return true;
	}

	/**
	 * 数値を比較チェックする
	 * @param aStr チェック対象の文字列
	 * @param aMin 最小値
	 * @return 文字列が指定された数値より小さい場合<tt>true</tt>を返す
	 */
	public static boolean isLessThan(String aStr, int aMin) {
		if (isNumeric(aStr) == false) {
			return false;
		}
		long lValue = 0;
		try {
			lValue = Long.parseLong(aStr);
		} catch (Exception ex) {
			return false; //変換エラー
		}
		if (lValue >= aMin ) {
			return false; //範囲外
		}
		return true;
	}

	/**
	 * 数値を比較チェックする
	 * @param aStr チェック対象の文字列
	 * @param aMin 最小値
	 * @return int 文字列が指定された数値より小さい場合<tt>true</tt>を返す
	 */
	public static boolean isLessThan(String aStr, double aMin) {
		if (isNumeric(aStr) == false) {
			return false;
		}
		double dValue = 0;
		try {
			dValue = Double.parseDouble(aStr);
		} catch (Exception ex) {
			return false; //変換エラー
		}
		if (dValue >= aMin ) {
			return false; //範囲外
		}
		return true;
	}

	/**
	 * 整数部の桁数をチェックする
	 * @param aStr チェック対象の文字列
	 * @param aScaleMin 最小桁数
	 * @param aScaleMax 最大桁数
	 * @return 文字列の整数部が指定された桁数(最小桁数<=チェック対象桁数<=最大桁数)
	 * に収まる場合<tt>true</tt>を返す
	 */
	public static boolean integerPartScaleIn(String aStr, int aScaleMin,int aScaleMax) {
		if (isNumeric(aStr) == false) {
			return false;
		}
		//整数部を取得する
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
			if (isAsciiDigit(ch) == true) { //数値文字かどうか
				iScaleCount++;
				if( ch != '0'){
					lastNonZeroScale = iScaleCount;
				}
			}else{
				break;
			}
		}
		if(lastNonZeroScale <= aScaleMax && lastNonZeroScale >= aScaleMin) {
			return true; //範囲内
		}
		return false;
	}

	/**
	 * 小数部の桁数をチェックする
	 * @param aStr チェック対象の文字列
	 * @param aScaleMin 最小桁数
	 * @param aScaleMax 最大桁数
	 * @return 文字列の小数部が指定された桁数(最小桁数<=チェック対象桁数<=最大桁数)
	 * に収まる場合<tt>true</tt>を返す
	 */
	public static boolean decimalPartScaleIn(String aStr, int aScaleMin,int aScaleMax) {
		if (isNumeric(aStr) == false) {
			return false;
		}
		//整数部を取得する
		int iIndex = aStr.indexOf('.');
		if( iIndex < 0){
			iIndex = 0;
			// 小数部なし
			if(iIndex <= aScaleMax && iIndex >= aScaleMin) {
				return true; //範囲内
			}
			return false; //範囲外
		}
		int iScaleCount = 0;
		int lastNonZeroScale = 0;
		int iLength = aStr.length() -1;
		char ch;
		while( iIndex < iLength ){
			ch = aStr.charAt(++iIndex);
			if (isAsciiDigit(ch) == true) { //数値文字かどうか
				iScaleCount++;
				if( ch != '0'){
					lastNonZeroScale = iScaleCount;
				}
			}else{
				break;
			}
		}
		if(lastNonZeroScale <= aScaleMax && lastNonZeroScale >= aScaleMin) {
			return true; //範囲内
		}
		return false;
	}

	/**
	 * すべて数値文字どうかチェックする。<br>
	 * 符号付きの文字列はエラー。<br>
	 * 数値表現のチェックはしない。<br>
	 * @param aStr チェック対称文字列
	 * @return int 文字列が数字(0-9)だけの場合<tt>true</tt>を返す
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
			if (isAsciiDigit(srcChar) == false) { //数値文字かどうか
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
	 * カンマ区切り入力された整数どうかチェックする。
	 * カンマ区切りの間隔は3桁。
	 * 3桁ではない区切り間隔はエラーとなる。
	 * @param aStr チェック対象の文字列
	 * @return int カンマ区切り入力された整数文字列の場合<tt>true</tt>を返す
	 */
	public static boolean isFormattedInteger(String aStr) {
		if (aStr == null){
			return false;
		}
		int length = aStr.length();
		if( length <= 0){
			return false; //エラー
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
					return true; // -0 正常
				}
				
				// 先頭0の整数はエラー
				return false; //エラー
			}
		} else {
			// 負号のみ
			return false; //エラー
		}
		int iCount = 0;
		int iLoopCount = length;
		int j = length;
//		int iDelimCount = 0;
		while (i < length) {
			i++;
			iLoopCount++;
			ch = aStr.charAt(--j);
			if (ch == ',') { //区切り文字かどうか
				if(iLoopCount != 1 && iCount != 3){
					//3文字の間が空いていない場合
					return false; //エラー
				}
				iCount = 0;
//				iDelimCount++;
			}else
			if (isAsciiDigit(ch) == false) {
				return false; //エラー
			}else{
				iCount++;
			}
			if( iCount > 3){
				//3文字以上の間が空いた場合
				return false; //エラー
			}
		}
		return true;
	}

	/**
	 * 整数または、カンマ区切り入力された整数どうかチェックする。
	 * カンマ区切り入力の場合、区切り間隔は3桁。3桁ではない区切り間隔はエラーとなる。
	 * @param aStr チェック対象の文字列
	 * @return int 整数またはカンマ区切り入力された整数文字列の場合<tt>true</tt>を返す
	 */
	public static boolean isIntegerOrFormattedInteger(String aStr) {
		if (aStr == null){
			return false;
		}
		if( aStr.indexOf(',') > 0){
			//フォーマットあり
			return isFormattedInteger(aStr);
		}
		//フォーマットなし
		return isInteger(aStr);
	}

	/**
	 * 整数また小数で、整数部はカンマ区切りかどうかチェックする。
	 * カンマ区切り入力の場合、区切り間隔は3桁。3桁ではない区切り間隔はエラーとなる。
	 * @param aStr チェック対象の文字列
	 * @return int 整数また小数で、整数部はカンマ区切りの場合<tt>true</tt>を返す
	 */
	public static boolean isFormattedNumeric(String aStr) {
		if (aStr == null){
			return false;
		}
		int length = aStr.length();
		if( length <= 0){
			return false; //エラー
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
					return true; // -0 正常
				}
				
				// 先頭0の整数はエラー
				return false; //エラー
			}
		} else {
			// 負号のみ
			return false; //エラー
		}
		int iLoopLength = length;
		int j = length;
		
		int k = i;
		while (k < length) {
			ch = aStr.charAt(k++);
			if (ch == '.') { //小数点かどうか
				iLoopLength = k-1;
				break;	//小数点を見つけた
			}
		}
		//小数点以下
		while (k < length) {
			ch = aStr.charAt(k++);
			if (isAsciiDigit(ch) == false) {
				return false; //エラー
			}
		}

		// 整数部
//		int iDelimCount = 0;
		int iCount = 0;
		int iLoopCount = 0;
		j = iLoopLength ;
		while (i < iLoopLength) {
			i++;
			iLoopCount++;
			ch = aStr.charAt(--j);
			if (ch == ',') { //区切り文字かどうか
				if(iLoopCount != 1 && iCount != 3){
					//3文字の間が空いていない場合
					return false; //エラー
				}
				iCount = 0;
//				iDelimCount++;
			}else
			if (isAsciiDigit(ch) == false) {
				return false; //エラー
			}else{
				iCount++;
			}
			if( iCount > 3){
				//3文字以上の間が空いた場合
				return false; //エラー
			}
		}
		return true;
	}

	/**
	 * 整数また小数または整数部がカンマ区切りされた数値文字列かどうかチェックする。
	 * カンマ区切り入力の場合、区切り間隔は3桁。3桁ではない区切り間隔はエラーとなる。
	 * @param aStr チェック対象の文字列
	 * @return 数値文字列または整数部がカンマ区切りされた数値文字列の場合<tt>true</tt>を返す
	 */
	public static boolean isNumericOrFormattedNumeric(String aStr) {
		if (aStr == null){
			return false;
		}
		if( aStr.indexOf(',') > 0){
			//フォーマットあり
			return isFormattedNumeric(aStr);
		}
		//フォーマットなし
		return isNumeric(aStr);
	}

	/**
	 * すべて２バイトカナ文字どうかチェックする
	 * @param aStr チェック対象文字列
	 * @return 対象文字列が2バイトカナ文字だけの場合<tt>true</tt>を返す
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
			//コード範囲でチェック
			if (checkChar >= '\u30a1' && checkChar <= '\u30ff') {
				//カナ ずる
			} else if (checkChar == '\u309b' || checkChar == '\u309c') {
				// 濁点、撥点
			} else if (checkChar == CHAR_SPACE_2BYTE) {
				//空白
			} else {
				//カナではない
				break;
			}
			iIndex++;
		}
		if (ilen != iIndex) {
			return false; //異常終了
		}
		return true; //正常終了
	}

	/**
	 * すべて半角カナ文字どうかチェックする。
	 * @param aStr チェック対象文字列
	 * @return 対象文字列が半角カナ文字だけの場合<tt>true</tt>を返す
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
			//コード範囲でチェック
			if (checkChar >= '\uff61' && checkChar <= '\uff9f') {
				// JIS カナ
//			}else
//			if (checkChar >= '\u0020' && checkChar <= '\u007d') {
//				// ASCII 数字
//			}else
//			if (checkChar == '\u00a5') {
//				//円記号
			}else{
				//半角カナではない
				break;
			}
			iIndex++;
		}
		if (ilen != iIndex) {
			return false; //異常終了
		}
		return true; //正常終了
	}

	
	/**
	 * 半角カナ文字を含むかどうかチェックする
	 * @param aStr チェック対象文字列
	 * @return 対象文字列に半角カナ文字を含む場合<tt>true</tt>を返す
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
			//コード範囲でチェック
			if (checkChar >= '\uff61' && checkChar <= '\uff9f') {
				// JIS カナ
				return true;
//			}else
//			if (checkChar >= '\u0020' && checkChar <= '\u007d') {
//				// ASCII 数字
//			}else
//			if (checkChar == '\u00a5') {
//				//円記号
//			}else{
//				//半角カナではない
//				//break;
			}
			iIndex++;
		}
		if (ilen != iIndex) {
			return true; //正常終了
		}
		return false; //異常終了
	}

	/**
	 * すべて1バイト文字どうかチェックする
	 * 記号なども含む
	 * @param aStr チェック対象文字列
	 * @return int １バイト文字だけの場合 0を返す
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
			//コード範囲でチェック
			if (checkChar >= '\u0020' && checkChar <= '\u00ff') {
				// Do Nothing
			} else {
				//１バイト文字ではない
				break;
			}
			iIndex++;
		}
		if (ilen != iIndex) {
			return false; //異常終了
		}
		return true; //正常終了
	}

	/**
	 * 1バイト文字を含むかどうかチェックする
	 * @param aStr チェック対象文字列
	 * @return １バイト文字を含む場合<tt>true</tt>を返す
	 */
	public static boolean contains1ByteAlpha(String aStr) {
		int ilen = aStr.length();
		int iIndex = 0;
		char checkChar;

		while (iIndex < ilen) {
			checkChar = aStr.charAt(iIndex);
			//コード範囲でチェック
			if (checkChar >= '\u0020' && checkChar <= '\u00ff') {
				break;
			}
			iIndex++;
		}
		if (ilen != iIndex) {
			return true; //含む
		}
		return false; //含まない
	}

//	/**
//	 * 電話番号かどうかチェックする
//	 * @param aStr チェック対象文字列
//	 * @return int 電話番号の場合 0を返す
//	 */
//	public static boolean isTelNumber(String aStr) {
//		int ilen = aStr.length();
//		int iIndex = 0;
//		char checkChar;
//
//		while (iIndex < ilen) {
//			checkChar = aStr.charAt(iIndex);
//			//コード範囲でチェック
//			if (checkChar >= '\u0030' && checkChar <= '\u0039') {
//				//数字
//			} else if (checkChar == '\u002D') {
//				// ハイフン
//				if (iIndex == 0) {
//					// 先頭	
//					break;
//				} else if (iIndex == (ilen - 1)) {
//					//末尾
//					break;
//				}
//			} else {
//				//ではない
//				break;
//			}
//			iIndex++;
//		}
//		if (ilen != iIndex) {
//			return false; //異常終了
//		}
//		return true; //正常終了
//	}

//	/**
//	 * メールアドレスチェックする
//	 * @param aStr チェック対象文字列
//	 * @return int メールアドレスの場合<tt>true</tt>を返す
//	 */
//	public static boolean isMailAddress(String aStr) {
//		int ilen = aStr.length();
//		int iIndex = 0;
//		int iAtMarkCount = 0;
//		int iDotIndex = 0;
//		char checkChar;
//
//		// @の後はドット修飾なしでもOK aaa@aaa形式で可
//
//		while (iIndex < ilen) {
//			checkChar = aStr.charAt(iIndex);
//			//コード範囲でチェック
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
//					// 先頭
//					break;
//				}
//				iDotIndex = iIndex;
//			} else if (checkChar == '\u0040') {
//				// @
//				if (iIndex == 0) {
//					// 先頭
//					break;
//				} else if (iIndex == (ilen - 1)) {
//					//末尾
//					break;
//				} else if (iDotIndex > 0 && (iDotIndex + 1) == iIndex) {
//					//ドットの次
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
//				//ではない
//				break;
//			}
//			iIndex++;
//		}
//		if (ilen != iIndex) {
//			return false; //異常終了
//		}
//		if (iAtMarkCount != 1) {
//			return false; //異常終了
//		}
//		return true; //正常終了
//	}

//	/**
//	 * すべて0の文字列かどうかチェックする
//	 * @param aStr チェック対象文字列
//	 * @param aLength 文字列長さ、0の場合長さ判定しない
//	 * @return int すべて0の文字列の場合<tt>true</tt>を返す。
//	 */
//	public static boolean isAllZero(String aStr, int aLength) {
//		int iLength = aStr.length();
//		int i = 0;
//		for (; i < iLength; i++) {
//			if (aStr.charAt(i) != '0') {
//				return false; //異常終了
//			}
//		}
//		if (aLength != 0 && i != aLength) {
//			return false; //異常終了
//		}
//		return true; //正常終了
//	}

	/**
	 * 空文字列かどうかチェックする。
	 * @param aStr チェック対象文字列
	 * @return nullまたは空文字列またはすべて半角空白/全角空白の場合trueを返す
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
				//空白以外がある
				return false;
			}
		}
		return true;
	}

	/**
	 * 引数で与えられた文字列がすべて空文字列かどうかチェックする。
	 * @param aStrings チェック対象文字列(可変長)
	 * @return すべての文字列がisEmptyの呼び出しででtrueを返す場合、場合<tt>true</tt>を返す
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
	 * 引数で与えられた文字列がすべて空文字列ではないかチェックする。
	 * @param aStrings チェック対象文字列(可変長)
	 * @return すべての文字列がisEmptyの呼び出しででfalseを返す場合、場合<tt>true</tt>を返す
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
	 * 引数で与えられた文字列のどれかが空文字列ではないかチェックする。
	 * @param aStrings チェック対象文字列(可変長)
	 * @return どれかの文字列がisEmptyの呼び出しででfalseを返す場合、場合<tt>true</tt>を返す
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
		// すべて空
		return false;
	}
	
	
	/**
	 * 文字列の長さの範囲をチェックする。
	 * @param aStr チェック対象文字列
	 * @param aMin 文字列長最小値
	 * @param aMax 文字列長最大値
	 * @return 文字列長が指定された範囲(最小値<=チェック対象<=最大値)
	 * に収まる場合<tt>true</tt>を返す
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
			return false; //範囲外
		}
		return true;
	}

	/**
	 * 文字列の長さの範囲をチェックする。<br>
	 * 前後の空白をトリムして、有効な文字列の長さを測る。<br>
	 * @param aStr チェック対象文字列
	 * @param aMin 文字列長最小値
	 * @param aMax 文字列長最大値
	 * @return 文字列長が指定された範囲(最小値<=チェック対象<=最大値)
	 * に収まる場合<tt>true</tt>を返す
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
		int iEnd = iLength -1;	//終了位置
		int iStart = iEnd;			//開始位置
		
		char ch;
		//前トリム
		for(int i = 0 ; i < iLength ; i++){
			ch = aStr.charAt(i);
			if( ch > ' ' && ch != CHAR_SPACE_2BYTE ){
				//空白以外がある
				iStart = i;
				break;
			}
		}
		if( iStart == iEnd){
			//すべて空白
			if( aMin <= 0){
				return true;
			}
			return false;
		}
		//後ろトリム
		for(int i = iEnd ; i >= 0 ; i--){
			ch = aStr.charAt(i);
			if( ch > ' ' && ch != CHAR_SPACE_2BYTE ){
				//空白以外がある
				iEnd = i;
				break;
			}
		}
		iLength = iEnd - iStart + 1;
		if (iLength > aMax || iLength < aMin) {
			return false; //範囲外
		}
		return true;
	}

	/**
	 * 文字列の最大長さをチェックする。
	 * @param aStr チェック対象文字列
	 * @param aMax 文字列長最大値
	 * @return 文字列長が指定された最大値以下の場合<tt>true</tt>を返す
	 */
	public static boolean isLengthLessThan(String aStr,int aMax) {
		return isLengthRangeIn(aStr,0,aMax);
	}


	/**
	 * 文字列の最小長さをチェックする。
	 * @param aStr チェック対象文字列
	 * @param aMin 文字列長最小値
	 * @return 文字列長が指定された最小値以上の場合<tt>true</tt>を返す
	 */
	public static boolean isLengthGreaterThan(String aStr,int aMin) {
		return isLengthRangeIn(aStr,aMin,Integer.MAX_VALUE);
	}

	/**
	 * 文字列の最大長さをチェックする。<br>
	 * 前後の空白をトリムして、有効な文字列の長さを測る。<br>
	 * @param aStr チェック対象文字列
	 * @param aMax 文字列長最大値
	 * @return 文字列長が指定された最大値以下の場合<tt>true</tt>を返す
	 */
	public static boolean isTrimmedLengthLessThan(String aStr,int aMax) {
		return isTrimmedLengthRangeIn(aStr,0,aMax);
	}


	/**
	 * 文字列の最小長さをチェックする。<br>
	 * 前後の空白をトリムして、有効な文字列の長さを測る。<br>
	 * @param aStr チェック対象文字列
	 * @param aMin 文字列長最小値
	 * @return 文字列長が指定された最小値以上の場合<tt>true</tt>を返す
	 */
	public static boolean isTrimmedLengthGreaterThan(String aStr,int aMin) {
		return isTrimmedLengthRangeIn(aStr,aMin,Integer.MAX_VALUE);
	}

}