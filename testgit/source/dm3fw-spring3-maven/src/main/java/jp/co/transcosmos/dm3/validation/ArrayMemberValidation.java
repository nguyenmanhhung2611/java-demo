/*
 * &copy; Trans-cosmos Inc. 2006, 2007.
 * All rights reserved.
 */
package jp.co.transcosmos.dm3.validation;

/**
 * Verifies that the entered field value is a member of an array supplied. This is often
 * used to check for restricted values (e.g. status value is one of "OPEN", "CLOSED", or 
 * "PENDING")
 * 
 * @author <a href="mailto:rick@knowleses.org">Rick Knowles</a>
 * @version $Id: ArrayMemberValidation.java,v 1.2 2007/05/31 05:38:56 rick Exp $
 */
public class ArrayMemberValidation implements Validation {
    
    private String array[];
    
    public ArrayMemberValidation(String array[]) {
        this.array = array;
    }
    
    public ValidationFailure validate(String pName, Object pValue) {
        if ((pValue == null) || pValue.equals("")) {
            return null;
        }
        
        if (array != null) {
            for (int n = 0; n < array.length; n++) {
                if (array[n].equals(pValue)) {
                    return null;
                }
            }
            return new ValidationFailure("arrayMember", pName, pValue, array);
        } else {
            return null;
        }
    }
}
