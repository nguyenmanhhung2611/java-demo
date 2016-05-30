/*
 * &copy; Trans-cosmos Inc. 2006, 2007.
 * All rights reserved.
 */
package jp.co.transcosmos.dm3.validation;

import java.io.UnsupportedEncodingException;

/**
 * Throws a validation failure if the field is shorter than the supplied value 
 * when converted to bytes using the supplied encoding.
 * 
 * @author <a href="mailto:rick@knowleses.org">Rick Knowles</a>
 * @version $Id: MinLengthBytesValidation.java,v 1.2 2007/05/31 05:38:56 rick Exp $
 */
public class MinLengthBytesValidation implements Validation {

    private int length;
    private String encoding;
    
    public MinLengthBytesValidation(int length, String encoding) {
        this.length = length;
        this.encoding = encoding;
    }
    
    public ValidationFailure validate(String name, Object value) {
        if ((value == null) || value.equals("")) {
            return null;
        }
        String valueStr = value.toString();
        try {
            byte bytes[] = (this.encoding == null ? 
                    valueStr.getBytes() : valueStr.getBytes(this.encoding));
            if (bytes.length < this.length) {
                return new ValidationFailure("minLength", name, value, new String[] {"" + this.length});
            }
        } catch (UnsupportedEncodingException err) {
            throw new RuntimeException("");
        }
        return null;
    }
}
