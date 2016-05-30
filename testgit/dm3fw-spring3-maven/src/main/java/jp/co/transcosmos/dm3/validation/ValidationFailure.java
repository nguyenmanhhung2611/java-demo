/*
 * &copy; Trans-cosmos Inc. 2006, 2007.
 * All rights reserved.
 */
package jp.co.transcosmos.dm3.validation;

/**
 * The valueobject describing validation errors returned by Validation implementation 
 * classes. This object is used by the error messages JSP fragment to write user-friendly
 * error messages together with the code-lookup feature (which provides the templates for
 * each kind of error message).
 * 
 * @author <a href="mailto:rick@knowleses.org">Rick Knowles</a>
 * @version $Id: ValidationFailure.java,v 1.2 2007/05/31 05:38:56 rick Exp $
 */
public class ValidationFailure {

    private String type;
    private String name;
    private Object value;
    private String[] extraInfo;
    
    public ValidationFailure(String type, String name, Object value, String[] extraInfo) {
        this.type = type;
        this.name = name;
        this.value = value;
        this.extraInfo = extraInfo;
    }

    /**
     * An identification of the validation check type that failed.
     */
    public String getType() {
        return type;
    }

    /**
     * The field name of the value that failed the check
     */
    public String getName() {
        return name;
    }

    /**
     * The illegal value
     */
    public Object getValue() {
        return value;
    }

    /**
     * Any extra metadata about the failure (e.g. field length if the type was a maxLength validation)
     * @return
     */
    public String[] getExtraInfo() {
        return extraInfo;
    }
}
