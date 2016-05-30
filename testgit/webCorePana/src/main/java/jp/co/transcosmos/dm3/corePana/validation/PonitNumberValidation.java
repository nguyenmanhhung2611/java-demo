package jp.co.transcosmos.dm3.corePana.validation;

import java.math.BigDecimal;

import jp.co.transcosmos.dm3.validation.Validation;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

public class PonitNumberValidation implements Validation {

	private int byte1;
	private int byte2;

	public PonitNumberValidation(int byte1, int byte2) {
		this.byte1 = byte1;
		this.byte2 = byte2;
	}

	@Override
	public ValidationFailure validate(String pName, Object pValue) {
		if (pValue == null || pValue.toString().equals("")) {
			return null;
		}

		String valueString = pValue.toString();

		try {
			new BigDecimal(valueString);

			if (valueString.indexOf(".") == -1) {
				if (valueString.length() > this.byte1) {
					return new ValidationFailure("pointNumberError", pName,
							String.valueOf(this.byte1),
							new String[] { String.valueOf(this.byte2) });
				}
			} else {
				String[] values = valueString.split("\\.");

				if (null == values || 2 != values.length
						|| values[0].length() > this.byte1
						|| values[1].length() > this.byte2) {
					return new ValidationFailure("pointNumberError", pName,
							String.valueOf(this.byte1),
							new String[] { String.valueOf(this.byte2) });
				}
			}

			return null;
		} catch (Exception e) {
			return new ValidationFailure("pointNumberError", pName,
					String.valueOf(this.byte1),
					new String[] { String.valueOf(this.byte2) });
		}
	}
}
