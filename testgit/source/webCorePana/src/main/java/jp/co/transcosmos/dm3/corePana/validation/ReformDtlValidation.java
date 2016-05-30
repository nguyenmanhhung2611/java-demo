package jp.co.transcosmos.dm3.corePana.validation;

import jp.co.transcosmos.dm3.validation.Validation;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

public class ReformDtlValidation implements Validation {

	private String target;
	private String target2;
	private String target3;
	private String target4;

	public ReformDtlValidation(String target, String target2, String target3, String target4) {
		this.target = target;
		this.target2 = target2;
		this.target3 = target3;
		this.target4 = target4;
	}

	@Override
	public ValidationFailure validate(String pName, Object pValue) {

		if (!"".equals(pValue) || !"".equals(this.target.toString())
				|| !"".equals(this.target2.toString())
				|| !"".equals(this.target3.toString())

		) {
			if ("".equals(pValue) || "".equals(this.target.toString())
					|| "".equals(this.target2.toString())
					|| "".equals(this.target3.toString())
					|| "".equals(this.target4.toString())
					) {
				return new ValidationFailure("reformDtlNotNull", pName, pValue, null);
			}
			return null;
		} else {
			return null;

		}
	}
}
