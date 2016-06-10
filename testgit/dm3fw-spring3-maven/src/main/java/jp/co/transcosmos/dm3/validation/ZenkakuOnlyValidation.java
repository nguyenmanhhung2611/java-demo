/*
 * &copy; Trans-cosmos Inc. 2006, 2007.
 * All rights reserved.
 */
package jp.co.transcosmos.dm3.validation;

import java.io.UnsupportedEncodingException;

//import jp.co.transcosmos.dm3.kana.KanaConverter;

/**
 * Checks that only zenkaku characters were used in the input string. Uses the KanaTable
 * class to check.
 * 
 * @author <a href="mailto:rick@knowleses.org">Rick Knowles</a>
 * @version $Id: ZenkakuOnlyValidation.java,v 1.2 2007/05/31 05:38:56 rick Exp $
 */
public class ZenkakuOnlyValidation implements Validation {

// 2015.01.29 H.Mizuno 正常に動作しないので、積水のバリデーションから移植 start
/*
    public ValidationFailure validate(String name, Object value) {
        if ((value == null) || value.equals("")) {
            return null;
        } else if (KanaConverter.isAllZenkaku(value.toString())) {
            return null;
        } else {
            return new ValidationFailure("zenkakuOnly", name, value, null);
        }
    }
*/
	private static final String encode = "MS932";

	public ValidationFailure validate(String name, Object value) {
		if ((value == null) || value.equals("")) {
			return null;
		}
		
	    byte[] bytes;
	    bytes = value.toString().getBytes();
	    
	    try {
            bytes = value.toString().getBytes(encode);
        } catch (UnsupportedEncodingException uee) {
        	bytes = value.toString().getBytes();
    	}
    
	    int beams = value.toString().length() * 2;

	    if (beams != bytes.length) {
	    	return new ValidationFailure("zenkakuOnly", name, value, null);
	    }

		return  null;
	}
// 2015.01.29 H.Mizuno 正常に動作しないので、積水のバリデーションから移植 end
	
}
