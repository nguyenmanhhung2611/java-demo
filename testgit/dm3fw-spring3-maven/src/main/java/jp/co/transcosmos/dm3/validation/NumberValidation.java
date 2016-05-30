/*
 * &copy; Trans-cosmos Inc. 2006, 2007.
 * All rights reserved.
 */
package jp.co.transcosmos.dm3.validation;

/**
 * Throws a validation failure if the field is not parseable as a number.
 * 
 * @author <a href="mailto:rick@knowleses.org">Rick Knowles</a>
 * @version $Id: NumberValidation.java,v 1.2 2007/05/31 05:38:56 rick Exp $
 */
public class NumberValidation implements Validation {

    public ValidationFailure validate(String name, Object value) {
        if ((value == null) || value.toString().equals("")) {
            return null; // null or empty is not an error
        } else {
            try {
                Float.parseFloat(value.toString());
                return null;
            } catch (Throwable err) {
                return new ValidationFailure("numericOnly", name, value, null);
            }
        }
    }
}
