package jp.co.transcosmos.dm3.corePana.validation;

import jp.co.transcosmos.dm3.validation.Validation;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

public class DataCheckValidation implements Validation {

	private String strPriceUpper;
	private String objNameUpper;

	public DataCheckValidation(String strPriceUpper, String objNameUpper) {
		this.strPriceUpper = strPriceUpper;
		this.objNameUpper = objNameUpper;
	}

	@Override
	public ValidationFailure validate(String objLowerName, Object objLowerValue) {

		if (objLowerValue == null || "".toString().equals(objLowerValue)) {
			return null;
		}
		if (strPriceUpper == null || "".equals(strPriceUpper)) {
			return null;
		}

		if(Long.valueOf(objLowerValue.toString()) > Long.valueOf(strPriceUpper)){
			return new ValidationFailure("dataCheck", objLowerName, objNameUpper, null);
		}
		return null;
	}
}
