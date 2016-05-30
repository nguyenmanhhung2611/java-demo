package jp.co.transcosmos.dm3.validation;

import jp.co.transcosmos.dm3.lookup.CodeLookupManager;

/**
 * 指定された codeLookup に該当する値が存在するかチェックする。<br/>
 * <br/>
 * @author H.Mizuno
 *
 */
public class CodeLookupValidation implements Validation {

	/** 共通コード変換処理 */
	private CodeLookupManager codeLookupManager;

	/** code lookup 名 */
	private String lookupName;

	public CodeLookupValidation(CodeLookupManager codeLookupManager, String lookupName) {
		this.codeLookupManager = codeLookupManager;
		this.lookupName = lookupName;
	}
	
	@Override
	public ValidationFailure validate(String name, Object value) {

		// データが空の場合、バリデーションＯＫ
		if ((value == null) || value.equals("")) {
            return null;
        }

		// 共通コード変換処理から値を取得する。
		if (this.codeLookupManager.lookupValue(this.lookupName, (String)value) == null){
			return new ValidationFailure("codeNotFound", name, value, null);
		}

		return null;
	}

}
