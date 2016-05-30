package jp.co.transcosmos.dm3.validation;

import jp.co.transcosmos.dm3.validation.Validation;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

/**
 * <pre>
 * 文字列長Validationクラス
 * 入力情報が文字列長と同じかチェックします。
 * ※バイト数チェックではありません
 * 
 * 担当者       修正日      修正内容
 * ------------ ----------- -----------------------------------------------------
 * 阿部　奈津江 2007.01.24  新規作成
 *
 * </pre>
*/
public class LengthValidator implements Validation {
    private int length;

    public  LengthValidator(int length) {
        this.length = length;
    }

    public ValidationFailure validate(String name, Object value) {
        if (value == null) {
            return null;
        }

        String valueStr = value.toString();
        if (valueStr.length() != this.length) {
            return new ValidationFailure("length", name, value, new String[] {"" + this.length});
        }
        return null;
    }
}
