package jp.co.transcosmos.dm3.corePana.validation;

import jp.co.transcosmos.dm3.utils.StringValidateUtil;
import jp.co.transcosmos.dm3.validation.Validation;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

public class ReformImgPathJpgValidation implements Validation {
	String target;
	String target2;

	public ReformImgPathJpgValidation(String target, String target2) {
		this.target = target;
		this.target2 = target2;
	}

	@Override
	public ValidationFailure validate(String pName, Object pValue) {

		if (StringValidateUtil.isNotEmptyAll(target)) {
			if("JPG".equals(target2.toUpperCase()) || "jpg".equals(target2.toLowerCase())){
				if (!("." + target2.toUpperCase()).equals(target.substring(target.lastIndexOf("."), target.length())) &&
						!("." + target2.toLowerCase()).equals(target.substring(target.lastIndexOf("."), target.length()))) {
					String name="jpeg(JPG)";
					return new ValidationFailure("checkJpg", pName, name, null);
				}
			}
			if("pdf".equals(target2.toLowerCase())){
				if (!("." + target2.toLowerCase()).equals(target.substring(target.lastIndexOf("."), target.length()))) {
					String name="PDF";
					return new ValidationFailure("checkJpg", pName, name, null);
				}
			}
		}
		return null;

	}
}
