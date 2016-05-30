package jp.co.transcosmos.dm3.corePana.validation;

import java.io.File;

import jp.co.transcosmos.dm3.validation.Validation;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

public class CsvFileValidation implements Validation {

	public CsvFileValidation() {
	}

	@Override
	public ValidationFailure validate(String pName, Object pValue) {

		if (null == pValue || "".equals(pValue.toString())) {
			return new ValidationFailure("checkCsv", pName, pValue, null);
		}

		try {
			File file = new File(pValue.toString());
			String fileName = file.getName().toLowerCase();

			if (!fileName.endsWith("csv")) {
				return new ValidationFailure("checkCsv", pName, pValue, null);
			}

			return null;
		} catch (Exception e) {
			return new ValidationFailure("checkCsv", pName, pValue, null);
		}
	}
}
