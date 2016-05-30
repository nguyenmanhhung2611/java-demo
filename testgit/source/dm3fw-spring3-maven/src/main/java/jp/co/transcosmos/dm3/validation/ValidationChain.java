/*
 * &copy; Trans-cosmos Inc. 2006, 2007.
 * All rights reserved.
 */
package jp.co.transcosmos.dm3.validation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Chain that we assign a name and value and a set of conditions to. When
 * validate() is called, it executes the chain of conditions in sequence and
 * adds the first error it comes to into the list argument supplied
 * 
 * @author <a href="mailto:rick@knowleses.org">Rick Knowles</a>
 * @version $Id: ValidationChain.java,v 1.2 2007/05/31 05:38:56 rick Exp $
 */
public class ValidationChain implements ValidationChainable {

    private String name;
    private Object value;
    private List<Validation> validations;

    public ValidationChain(String name, Object value) {
        this.name = name;
        this.value = value;
    }
    
    /**
     * Add one more validation check to the chain.
     */
    @Override
    public void addValidation(Validation validation) {
        if (this.validations == null) {
            this.validations = new ArrayList<Validation>();
        }
        this.validations.add(validation);
    }

    /**
     * Check for invalid content using the validations on this chain. Aborts with the
     * appropriate ValidationFailure instance added to the list at the first check failure
     * encountered. The boolean value indicates validation success or failure.
     */
    @Override
    public boolean validate(List<ValidationFailure> outputErrors) {
        if (this.validations == null) {
            return true;
        }
        for (Iterator<Validation> i = this.validations.iterator(); i.hasNext(); ) {
            ValidationFailure error = ((Validation) i.next()).validate(this.name, this.value);
            if (error != null) {
                if (outputErrors != null) {
                    outputErrors.add(error);
                }
                return false;
            }
        }
        return true;
	}
}
