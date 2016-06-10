/*
 * &copy; Trans-cosmos Inc. 2006, 2007.
 * All rights reserved.
 */
package jp.co.transcosmos.dm3.validation;

/**
 * Throws a validation failure if the field is longer than the supplied length in characters.
 * 
 * @author <a href="mailto:rick@knowleses.org">Rick Knowles</a>
 * @version $Id: MaxLengthValidation.java,v 1.2 2007/05/31 05:38:56 rick Exp $
 */
public class MaxLengthValidation implements Validation {

    private int length;
    
    public MaxLengthValidation(int length) {
        this.length = length;
    }
    
    public ValidationFailure validate(String name, Object value) {
        if (value == null) {
            return null;
        }
        String valueStr = value.toString();
        if (valueStr.length() > this.length) {
            return new ValidationFailure("maxLength", name, value, new String[] {"" + this.length});
        }
        return null;
    }
}
