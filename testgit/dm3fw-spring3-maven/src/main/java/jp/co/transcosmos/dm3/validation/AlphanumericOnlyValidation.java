/*
 * &copy; Trans-cosmos Inc. 2006, 2007.
 * All rights reserved.
 */
package jp.co.transcosmos.dm3.validation;

/**
 * Throws a validation failure if the field contains a character other than an 
 * alphabet character (outside of A-Z or a-z) or a numeric digit (0-9)
 * 
 * @author <a href="mailto:rick@knowleses.org">Rick Knowles</a>
 * @version $Id: AlphanumericOnlyValidation.java,v 1.2 2007/05/31 05:38:56 rick Exp $
 */
public class AlphanumericOnlyValidation implements Validation {

    public ValidationFailure validate(String name, Object value) {
        if ((value == null) || value.equals("")) {
            return null;
        }
        String valueStr = value.toString().toLowerCase();
        for (int n = 0; n < valueStr.length(); n++) {
            char thisChar = valueStr.charAt(n);
            if ((thisChar >= '0') && (thisChar <= '9')) {
                continue;
            } else if ((thisChar >= 'a') && (thisChar <= 'z')) {
                continue;
            } else {
                return new ValidationFailure("alphanumericOnly", name, value, null);
            } 
        }
        return null;
    }
}
