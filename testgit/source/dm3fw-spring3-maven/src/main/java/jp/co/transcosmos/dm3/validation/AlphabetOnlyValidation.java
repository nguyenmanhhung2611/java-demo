/*
 * &copy; Trans-cosmos Inc. 2006, 2007.
 * All rights reserved.
 */
package jp.co.transcosmos.dm3.validation;

/**
 * Throws a validation failure if the field contains a non-alphabet char 
 * (outside of A-Z or a-z)
 * 
 * @author <a href="mailto:rick@knowleses.org">Rick Knowles</a>
 * @version $Id: AlphabetOnlyValidation.java,v 1.2 2007/05/31 05:38:56 rick Exp $
 */
public class AlphabetOnlyValidation implements Validation {

    public ValidationFailure validate(String name, Object value) {
        if ((value == null) || value.equals("")) {
            return null;
        }
        String valueStr = value.toString().toLowerCase();
        for (int n = 0; n < valueStr.length(); n++) {
            char thisChar = valueStr.charAt(n);
            if ((thisChar >= 'a') && (thisChar <= 'z')) {
                continue;
            } else {
// 2013.05.02 H.Mizuno 例外オブジェクトのタイプの誤りを修正 Start
//                return new ValidationFailure("alphanumericOnly", name, value, null);
                return new ValidationFailure("alphabetOnly", name, value, null);
// 2013.05.02 H.Mizuno 例外オブジェクトのタイプの誤りを修正 End
            } 
        }
        return null;
    }
}
