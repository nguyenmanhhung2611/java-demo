package jp.co.transcosmos.dm3.corePana.validation;

import jp.co.transcosmos.dm3.validation.Validation;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

public class HousingInspectionValueValidation implements Validation {

	private String inspectionTrustResult;
	private String inspectionValueLabel;

	public HousingInspectionValueValidation(String inspectionTrustResult, String inspectionValueLabel) {
		this.inspectionTrustResult = inspectionTrustResult;
		this.inspectionValueLabel = inspectionValueLabel;
	}

	@Override
	public ValidationFailure validate(String pName, Object pValue) {

		if ("01".equals(pValue)) {

			if("".equals(inspectionTrustResult) || "".equals(inspectionValueLabel)){
				return new ValidationFailure("inspectionValue", "Z‘îf’fÀ{—L–³", "f’fŒ‹‰Ê–”‚ÍŠm”FƒŒƒxƒ‹", null);
			}
			else{
				return null;
			}

		}
		else {
			return null;

		}
	}
}