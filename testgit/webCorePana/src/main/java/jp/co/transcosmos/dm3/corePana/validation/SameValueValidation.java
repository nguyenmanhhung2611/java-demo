package jp.co.transcosmos.dm3.corePana.validation;

import jp.co.transcosmos.dm3.validation.Validation;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

public class SameValueValidation implements Validation {

	private String item;
	private Object value;

	public SameValueValidation(String item, Object value) {
		this.value = value;
		this.item = item;
	}

	@Override
	public ValidationFailure validate(String pName, Object pValue) {
		if (pValue == null || pValue.toString().equals("")) {
			return null;
		}

		if (this.value == null || this.value.toString().equals("")) {
			return null;
		}

		try {

			if (pValue.toString().compareTo(value.toString()) != 0) {
				return new ValidationFailure("sameValueError", pName, item, null);
			}

			return null;
		} catch (Exception e) {
			return null;
		}
	}
}
