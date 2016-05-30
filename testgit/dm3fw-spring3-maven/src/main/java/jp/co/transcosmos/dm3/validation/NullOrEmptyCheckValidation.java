/*
 * &copy; Trans-cosmos Inc. 2006, 2007.
 * All rights reserved.
 */
package jp.co.transcosmos.dm3.validation;

/**
 * Throws a validation failure if the field is null or an empty string.
 * 
 * @author <a href="mailto:rick@knowleses.org">Rick Knowles</a>
 * @version $Id: NullOrEmptyCheckValidation.java,v 1.2 2007/05/31 05:38:56 rick Exp $
 */
public class NullOrEmptyCheckValidation implements Validation {

    public ValidationFailure validate(String name, Object value) {
        if ((value == null) || value.toString().equals("")) {
            return new ValidationFailure("nullOrEmpty", name, value, null);
        }
        return null;
    }
}
