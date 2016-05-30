package jp.co.transcosmos.dm3.validation;

/**
 * 数字チェックバリデーション<br/>
 * 0 ～ 9 までの数字で構成されているかをチェックする。 マイナス記号もエラーとする。<br/>
 * <br/>
 * @author H.Mizuno
 *
 */
public class NumericValidation implements Validation {

	@Override
	public ValidationFailure validate(String name, Object value) {

        if ((value == null) || value.equals("")) {
            return null;
        }

        String valueStr = value.toString().toLowerCase();
        for (int n = 0; n < valueStr.length(); n++) {
            char thisChar = valueStr.charAt(n);
            if ((thisChar >= '0') && (thisChar <= '9')) {
                continue;
            } else {
                return new ValidationFailure("positNumberOnly", name, value, null);
            } 
        }
        return null;

	}

}
