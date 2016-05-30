package jp.co.transcosmos.dm3.corePana.validation;

import jp.co.transcosmos.dm3.validation.Validation;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

/**
 * 権限バリデーション.
 * <p>
 * target に指定された権限以外の場合
 * 指定項目が入力したかをチェックする。<br/>
 * <br/>
 * <pre>
 * 担当者		修正日		修正内容
 * ------------ ----------- -----------------------------------------------------
 * Trans		2015.03.24	新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 *
 */
public class RoleValidation  implements Validation {

	/** 権限判定オブジェクト */
	private Object target;


    public RoleValidation(Object target) {
        this.target = target;
    }


    @Override
    public ValidationFailure validate(String pName, Object pValue) {

    	// 権限が指定権限ではない場合
    	if (!(Boolean) this.target) {
    		if ((pValue != null) && (!pValue.toString().equals(""))) {
    			return new ValidationFailure("role", pName, pValue, null);
    		}
    	}
    	return null;
    }

}
