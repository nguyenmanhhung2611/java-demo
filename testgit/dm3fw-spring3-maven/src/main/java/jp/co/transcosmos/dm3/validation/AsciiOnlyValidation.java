package jp.co.transcosmos.dm3.validation;

import java.io.UnsupportedEncodingException;

/**
 * 半角英数記号文字チェック<br/>
 * <br/>
 * @author H.Mizuno
 *
 */
public class AsciiOnlyValidation implements Validation {

	@Override
	public ValidationFailure validate(String name, Object value) {

		// データが空の場合、バリデーションＯＫ
		if ((value == null) || value.equals("")) {
            return null;
        }

		// 文字数と、バイト数を比較して判定する。
		// sjis で処理されると半角カタカナが識別できないので、utf8 を明示的に指定する。
		int strLen = ((String)value).length();
		int byteLen;
		try {
			byteLen = ((String)value).getBytes("utf8").length;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new RuntimeException("invalid enocde type");
		}

		if (strLen != byteLen) {
			return new ValidationFailure("asciiOnly", name, value, null);
		}

		return null;
	}

}
