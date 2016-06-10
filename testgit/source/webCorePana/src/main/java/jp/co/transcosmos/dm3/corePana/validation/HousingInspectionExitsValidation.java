package jp.co.transcosmos.dm3.corePana.validation;

import jp.co.transcosmos.dm3.utils.StringValidateUtil;
import jp.co.transcosmos.dm3.validation.Validation;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

public class HousingInspectionExitsValidation implements Validation {

	private String checkItem;

	public HousingInspectionExitsValidation(String checkItem) {
		this.checkItem = checkItem;
	}

	@Override
	public ValidationFailure validate(String pName, Object pValue) {

		if ((!StringValidateUtil.isEmpty((String)pValue) && StringValidateUtil.isEmpty(checkItem))) {
			return new ValidationFailure("eachExits", "f’fŒ‹‰Ê", "Šm”FƒŒƒxƒ‹", null);
		} else if((StringValidateUtil.isEmpty((String)pValue) && !StringValidateUtil.isEmpty(checkItem))){
			return new ValidationFailure("eachExits", "Šm”FƒŒƒxƒ‹", "f’fŒ‹‰Ê", null);
		}
		else {
			return null;

		}
	}
}
