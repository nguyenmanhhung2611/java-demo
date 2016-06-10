/*
 * &copy; Trans-cosmos Inc. 2006, 2007.
 * All rights reserved.
 */
package jp.co.transcosmos.dm3.validation;

import java.util.List;

/**
 * Marks an object as supporting validation. This is used by various parts of the framework to
 * enforce validation rules at certain points in the life of the system, such as before saving
 * into the database, or when validating form input. 
 * 
 * @author <a href="mailto:rick@knowleses.org">Rick Knowles</a>
 * @version $Id: Validateable.java,v 1.2 2007/05/31 05:38:56 rick Exp $
 */
public interface Validateable {
    
    /**
     * Validate the object contents, and insert a ValidationFailure object for each 
     * error to be displayed.
     * @return true if there are no errors (the object is valid), or false otherwise
     */
    public boolean validate(List<ValidationFailure> errors);
}
