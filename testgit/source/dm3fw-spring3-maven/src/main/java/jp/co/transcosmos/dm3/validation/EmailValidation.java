/*
 * &copy; Trans-cosmos Inc. 2006, 2007.
 * All rights reserved.
 */
package jp.co.transcosmos.dm3.validation;

import java.util.Arrays;

/**
 * Checks the entered field is a valid email address, confirming the absence of 
 * illegal characters, the length limit, and the presence of at-mark and dots
 * in appropriate places.
 * 
 * @author <a href="mailto:rick@knowleses.org">Rick Knowles</a>
 * @version $Id: EmailValidation.java,v 1.2 2007/05/31 05:38:56 rick Exp $
 */
public class EmailValidation implements Validation {
    
    private static final char ALLOWED_CHARS[] = {'-', '.', '@', '_'};

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
            } else if (Arrays.binarySearch(ALLOWED_CHARS, thisChar) >= 0) {
                continue;
            } else {
                return new ValidationFailure("email", name, value, null);
            } 
        }
        int at = valueStr.indexOf('@');
        int lastAt = valueStr.lastIndexOf('@');
        int dot = valueStr.lastIndexOf('.');
        if ((at == -1) || (at != lastAt) || (dot == -1) || (at > dot - 1) || 
                (valueStr.length() < 5) || valueStr.endsWith(".")) {
            return new ValidationFailure("email", name, value, null);
        }
        return null;
    }
}
