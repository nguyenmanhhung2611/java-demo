package jp.co.transcosmos.dm3.core.validation;

import jp.co.transcosmos.dm3.validation.Validation;
import jp.co.transcosmos.dm3.validation.ValidationFailure;


/**
 * 比較バリデーション.
 * <p>
 * target に指定された値と一致するかをチェックする。<br/>
 * <br/>
 * <pre>
 * 担当者		修正日		修正内容
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.02.23	Shamaison を参考に新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 * 
 */
public class CompareValidation implements Validation {

	/** 比較対象オブジェクト */
	private Object target;
	/** 対象ラベル （「・・・の値と一致しません」のメッセージに使用する値）*/
	private String targetLabel = null;
	/** true の場合、否定判定 */
	private boolean negative = false;
	
	
	
	/**
	 * コンストラクター<br/>
	 * <br/>
	 * @param target 比較対象オブジェクト
	 * @param targetLabel　エラーメッセージに表示する、比較対象項目名（または値）
	 */
	public CompareValidation(Object target, String targetLabel) {
        this.target = target;
        this.targetLabel = targetLabel;
    }

	/**
	 * コンストラクター<br/>
	 * <br/>
	 * @param target 比較対象オブジェクト
	 * @param targetLabel　エラーメッセージに表示する、比較対象項目名（または値）
	 * @param negative　true の場合否定判定 （デフォルト false）
	 */
    public CompareValidation(Object target, String targetLabel, boolean negative) {
        this.target = target;
        this.targetLabel = targetLabel;
        this.negative = negative;
    }



    @Override
    public ValidationFailure validate(String pName, Object pValue) {
    	// 未入力時は true とする。
    	if ((pValue == null) || pValue.equals("")) {
    		return null;
        }

    	if (!this.negative) {
        	if (pValue.equals(this.target)) return null;
        	return new ValidationFailure("compare", pName, pValue, new String[]{this.targetLabel});
    	
    	} else {
        	if (!pValue.equals(this.target)) return null;
        	return new ValidationFailure("notCompare", pName, pValue, new String[]{this.targetLabel});
    	}


    }

}
