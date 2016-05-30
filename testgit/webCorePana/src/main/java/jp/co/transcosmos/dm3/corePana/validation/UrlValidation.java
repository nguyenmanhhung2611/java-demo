package jp.co.transcosmos.dm3.corePana.validation;

import java.net.URL;

import jp.co.transcosmos.dm3.validation.Validation;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

public class UrlValidation implements Validation {

	public UrlValidation() {
	}

	@Override
	public ValidationFailure validate(String pName, Object pValue) {

		if (null == pValue || "".equals(pValue.toString())) {
			return new ValidationFailure("urlError", pName, pValue, null);
		}

		try {
			new URL(pValue.toString());

			return null;
		} catch(Exception e) {
			return new ValidationFailure("urlError", pName, pValue, null);
		}
	}
}
