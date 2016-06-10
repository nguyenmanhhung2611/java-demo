/*
 * &copy; Trans-cosmos Inc. 2006, 2007.
 * All rights reserved.
 */
package jp.co.transcosmos.dm3.validation;

/**
 * Throws a validation failure if the field contains more than one instance of the 
 * same character. This is useful for checking passwords meet strictness rules.
 * 
 * @author <a href="mailto:rick@knowleses.org">Rick Knowles</a>
 * @version $Id: RepeatCharacterValidation.java,v 1.2 2007/05/31 05:38:56 rick Exp $
 */
public class RepeatCharacterValidation implements Validation {

    public ValidationFailure validate(String name, Object value) {
        if ((value == null) || value.equals("")) {
            return null;
        }
        String valueStr = value.toString();
        if (valueStr.length() == 1) {
            return null;
        }
        for (int n = 1; n < valueStr.length(); n++) {
            if (valueStr.charAt(n) != valueStr.charAt(n - 1)) {
                return null;
            } 
        }
        return new ValidationFailure("repeatCharacter", name, value, null);
    }
}
