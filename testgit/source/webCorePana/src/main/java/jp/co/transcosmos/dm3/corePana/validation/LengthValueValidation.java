package jp.co.transcosmos.dm3.corePana.validation;

import jp.co.transcosmos.dm3.validation.Validation;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

public class LengthValueValidation implements Validation {

	private int length;

	public LengthValueValidation(int length) {
		this.length = length;
	}
	@Override
	public ValidationFailure validate(String name, Object value) {
		if (value == null || value =="") {
			return null;
		}
		String valueStr = value.toString();
		if (valueStr.length() > this.length || valueStr.length() < this.length) {
			return new ValidationFailure("eqLength", name, value,
					new String[] { "" + this.length });
		}
		return null;
	}
}
