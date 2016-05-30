package jp.co.transcosmos.dm3.corePana.validation;

import jp.co.transcosmos.dm3.validation.Validation;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

import org.springframework.util.StringUtils;

public class PriceReformCheckValidation implements Validation {

	private String strPriceUpper;

	public PriceReformCheckValidation(String strPriceUpper) {
		this.strPriceUpper = strPriceUpper;
	}

	@Override
	public ValidationFailure validate(String pName, Object pValue) {
		if (pValue == null || pValue.toString().equals("")
				&& StringUtils.isEmpty(strPriceUpper)) {

			return new ValidationFailure("reformPrice", "ƒŠƒtƒH[ƒ€‰¿Ši‚İ‚ÅŒŸõ‚·‚é", "—\Z", null);
		}else{
			return null;
		}

	}
}
