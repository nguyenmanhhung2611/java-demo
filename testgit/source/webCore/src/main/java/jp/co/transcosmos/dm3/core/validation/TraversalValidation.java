package jp.co.transcosmos.dm3.core.validation;

import jp.co.transcosmos.dm3.validation.Validation;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

/**
 * ディレクトリトラバーサルの脅威となる記号文字の使用をチェックする.
 * <p>
 * <pre>
 * 担当者		修正日		修正内容
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.04.07	新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 * 
 */
public class TraversalValidation implements Validation {

	/** 利用禁止文字列 */
	private String[] badStrings = new String[]{"\\","/",":","../"};



	@Override
	public ValidationFailure validate(String name, Object value) {

		// 空或いはnullの場合、正常終了
		if (value == null || "".equals(value.toString())) {
			return null;
		}


		for (String bad : badStrings){
			if (value.toString().indexOf(bad) >= 0){
				return new ValidationFailure("traversal", name, value, null);
			}
		}

		return null;
	}

	
	
	
}
