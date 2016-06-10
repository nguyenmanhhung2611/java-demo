/*
 * &copy; Trans-cosmos Inc. 2006, 2007.
 * All rights reserved.
 */
package jp.co.transcosmos.dm3.validation;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Performs a string to date to string conversion, and verifies that the
 * end date is exactly as it was before formatting. In this way, it verifies
 * that illegal dates (e.g. 30th February) are detected.
 * 
 * @author <a href="mailto:rick@knowleses.org">Rick Knowles</a>
 * @version $Id: ValidDateValidation.java,v 1.2 2007/05/31 05:38:56 rick Exp $
 */
public class ValidDateValidation implements Validation {

    private String pattern;
    
    public ValidDateValidation(String pattern) {
        this.pattern = pattern;
    }
    
    public ValidationFailure validate(String pName, Object pValue) {
        if ((pValue == null) || pValue.equals("")) {
            return null;
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            if (sdf.format(sdf.parse(pValue.toString())).equals(pValue.toString())) {
                return null;
            }
        } catch (ParseException err) {}
        
        // There was an error of some kind
        return new ValidationFailure("validDate", pName, 
                pValue.toString(), new String[] {pattern});
    }
}
