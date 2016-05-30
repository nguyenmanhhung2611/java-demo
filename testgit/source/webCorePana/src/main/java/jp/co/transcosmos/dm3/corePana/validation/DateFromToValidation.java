package jp.co.transcosmos.dm3.corePana.validation;

import java.text.SimpleDateFormat;
import java.util.Date;

import jp.co.transcosmos.dm3.validation.Validation;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

public class DateFromToValidation implements Validation {

	private String pattern;
	private String endItem;
	private Object endValue;

	public DateFromToValidation(String pattern, String endItem, Object endValue) {
		this.pattern = pattern;
		this.endItem = endItem;
		this.endValue = endValue;
	}

	@Override
	public ValidationFailure validate(String pName, Object pValue) {
		if (pValue == null || pValue.toString().equals("")) {
			return null;
		}

		if (this.endValue == null || this.endValue.toString().equals("")) {
			return null;
		}

		try {
			SimpleDateFormat sdf = new SimpleDateFormat(this.pattern);
			sdf.setLenient(false);
			Date startD = sdf.parse(pValue.toString());
			Date endD = sdf.parse(this.endValue.toString());

			if (startD.compareTo(endD) > 0) {
				return new ValidationFailure("dateFromToCheck", pName, endItem, null);
			}

			return null;
		} catch (Exception e) {
			return null;
		}
	}
}
