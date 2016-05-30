package jp.co.transcosmos.dm3.core.validation;

import jp.co.transcosmos.dm3.validation.Validation;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

/**
 * <pre>
 * 全角カナValidationクラス
 * 入力された文字が全角カナかチェックをします。
 * ・全角カナ(0x30A1～0x30F6)の場合、チェック通過
 * ・許可文字の場合、チェック通過
 * 
 * 担当者       修正日      修正内容
 * ------------ ----------- -----------------------------------------------------
 * 金野　哲也   2006.12.26  新規作成
 *
 * </pre>
*/

public class ZenkakuKanaValidator implements Validation {

	private static final char ALLOWED_CHARS[] = {'‐','－','ー'};

	public ValidationFailure validate(String name, Object value) {
		if ((value == null) || value.equals("")) {
			return null;
		}
		char[] valueStr = value.toString().toCharArray();
		for (int n = 0; n < valueStr.length; n++) {
			if ((valueStr[n] >= 0x30A1) && (valueStr[n] <= 0x30F6)) {
				continue;
			}
			
			boolean valid = false;
			for (int i = 0; i < ALLOWED_CHARS.length; i++) {
				if (valueStr[n] == ALLOWED_CHARS[i]) {
					valid = true;
					break;
				}
			}
			if (valid) {
				continue;
			}
			
			return new ValidationFailure("zenkakuKana", name, value, null);
		}
		return  null;
	}
	
}
