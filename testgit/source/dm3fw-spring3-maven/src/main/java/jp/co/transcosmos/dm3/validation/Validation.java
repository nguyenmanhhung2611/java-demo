/*
 * &copy; Trans-cosmos Inc. 2006, 2007.
 * All rights reserved.
 */
package jp.co.transcosmos.dm3.validation;

/**
 * Interface defining validation check API. All validation checks should implement this
 * interface.
 * 
 * @author <a href="mailto:rick@knowleses.org">Rick Knowles</a>
 * @version $Id: Validation.java,v 1.2 2007/05/31 05:38:56 rick Exp $
 */
public interface Validation {
    /**
     * Returns null if the content is valid, otherwise returns a ValidationFailure
     * object describing the failure.
     */
    public ValidationFailure validate(String name, Object value);
}
