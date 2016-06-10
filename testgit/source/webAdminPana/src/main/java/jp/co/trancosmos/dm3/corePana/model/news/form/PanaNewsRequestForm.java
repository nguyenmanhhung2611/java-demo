/**
 * 
 */
package jp.co.trancosmos.dm3.corePana.model.news.form;

import java.util.List;

import jp.co.transcosmos.dm3.core.util.LengthValidationUtils;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;
import jp.co.transcosmos.dm3.validation.Validateable;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

/**
 * @author hiennt
 *
 */
public class PanaNewsRequestForm extends NewsRequestForm implements Validateable{

	protected PanaNewsRequestForm(LengthValidationUtils lengthUtils, CodeLookupManager codeLookupManager) {
		super(lengthUtils, codeLookupManager);
		// TODO Auto-generated constructor stub
	}



	/** 共通コード変換処理 */
	private CodeLookupManager codeLookupManager;
	/** レングスバリデーションで使用する文字列長を取得するユーティリティ */
	protected LengthValidationUtils lengthUtils;
	
	

	@Override
	public boolean validate(List<ValidationFailure> errors) {
		// TODO Auto-generated method stub
		return false;
	}

}
