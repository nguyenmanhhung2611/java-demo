package jp.co.transcosmos.dm3.core.validation;

import jp.co.transcosmos.dm3.utils.StringValidateUtil;
import jp.co.transcosmos.dm3.validation.Validation;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

/**
 * <pre>
 * パスワード強度チェック処理
 * 
 * 担当者		修正日		修正内容
 * ------------ ----------- -----------------------------------------------------
 * I.Shu		2015.02.04	新規作成
 * 
 * 注意事項
 *
 * </pre>
 */
public class PasswordValidation implements Validation {

	/** 入力を強制する記号文字列　*/
	private String signList = null;



	/**
	 * デフォルトコンストラクタ<br/>
	 * <br/>
	 * 
	 */
	public PasswordValidation() {

	}

	/**
	 * コンストラクタ<br/>
	 * 入力を強制する記号文字列を指定する。<br/>
	 * <br/>
	 * 
	 * @param signList
	 *            記号リスト
	 */
	public PasswordValidation(String signList) {
		this.signList = signList;
	}



	/**
	 * パスワード強度チェック。<br/>
	 * <br/>
	 * 
	 */
	public ValidationFailure validate(String name, Object value) {
		// 空或いはnullの場合、正常終了
		if (value == null || "".equals(value.toString())) {
			return null;
		}

		// 数字ありフラグ
		boolean numFlg = false;
		// 半角英字小文字ありフラグ
		boolean ｓCharFlg = false;
		// 半角英字大文字ありフラグ
		boolean lCharFlg = false;
		// 記号ありフラグ
		boolean signFlg = false;
		if (StringValidateUtil.isEmpty(this.signList)){
			// 記号文字列が未設定の場合、記号文字の入力を強制しない。
			signFlg = true;
		}


		String valueStr = value.toString();
		// パスワードの桁数より、ループでチェックします
		for (int i = 0; i < valueStr.length(); i++) {
			if (valueStr.substring(i, i + 1).matches("[0-9]")) {
				// 数字あり
				numFlg = true;
			} else if (valueStr.substring(i, i + 1).matches("[a-z]")) {
				// 半角英字小文字あり
				ｓCharFlg = true;
			} else if (valueStr.substring(i, i + 1).matches("[A-Z]")) {
				// 半角英字大文字あり
				lCharFlg = true;
			} else if (signMatches(valueStr.substring(i, i + 1))) {
				// 利用できる記号あり
				signFlg = true;
			}
		}
		// パスワードに文字種類がそろっている場合、正常終了
		// 記号が利用できない場合、利用できる記号ありのチェックが要らない
		if (numFlg && ｓCharFlg && lCharFlg && signFlg) {
			return null;
		}
		// パスワードに文字種類がそろっていない場合
		if (StringValidateUtil.isEmpty(this.signList)) {
			return new ValidationFailure("passwordNG", name, value, null);
		} else {
			return new ValidationFailure("passwordMarkNG", name, value, new String[]{this.signList});
		}
	}



	/**
	 * 利用できる記号チェック。<br/>
	 * <br/>
	 */
	private boolean signMatches(String valueStr) {
		if (signList != null && signList.contains(valueStr)) {
			return true;
		}
		return false;
	}

}
