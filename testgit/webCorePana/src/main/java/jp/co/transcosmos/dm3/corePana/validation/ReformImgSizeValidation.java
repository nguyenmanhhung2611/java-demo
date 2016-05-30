package jp.co.transcosmos.dm3.corePana.validation;

import jp.co.transcosmos.dm3.validation.Validation;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

public class ReformImgSizeValidation implements Validation {

	private int length;

	public ReformImgSizeValidation(int length) {
		this.length = length;

	}

	@Override
	public ValidationFailure validate(String pName, Object pValue) {

		if (Long.valueOf(pValue.toString()) > Long.valueOf(length)) {
			return new ValidationFailure("fileSizeError", pName, length/1024/1024, null);
		} else {
			return null;

		}
	}
}
