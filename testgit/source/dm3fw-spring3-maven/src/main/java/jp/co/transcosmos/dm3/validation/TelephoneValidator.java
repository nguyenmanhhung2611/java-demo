package jp.co.transcosmos.dm3.validation;

import jp.co.transcosmos.dm3.validation.Validation;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

/**
 * <pre>
 * 電話番号Validationクラス
 * 入力された電話番号のチェックをします。
 * ・null、空白文字はチェック通過
 * ・半角数字、半角ハイフンのみの文字列の場合はチェック通過
 * ・半角数字、半角ハイフン以外の文字列が入ってきた場合はエラー
 * ・桁数のチェックはしません。必要に応じてMaxLengthValidation、
 * 　MinLengthValidatinと組み合わせて使用してください。
 * 
 * 担当者       修正日      修正内容
 * ------------ ----------- -----------------------------------------------------
 * 細島　崇     2006.12.22  新規作成
 *
 * </pre>
*/
public class TelephoneValidator implements Validation {
	public ValidationFailure validate(String name, Object value) {
		
		// null、空文字の場合は通過
		if ((value == null) || value.equals("")) {
			return null;
		}
		
		String telNo = value.toString();
		int len = telNo.length();
		for (int i = 0; i < len; i++) {
			char ch = telNo.charAt(i);
			
			// 各文字が数字かハイフンの場合は通過
			if (('0' <= ch && ch <= '9') || ch == '-') {
				continue;
			}
			
			// 数字・ハイフン以外の文字が入ってきた場合はエラー
			return new ValidationFailure("telephone", name, value, null);
		}
		
		// すべての文字が数字・ハイフンの場合は通過
		return null;
	}
}
