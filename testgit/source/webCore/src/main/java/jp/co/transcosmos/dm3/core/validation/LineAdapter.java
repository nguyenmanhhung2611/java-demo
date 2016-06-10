package jp.co.transcosmos.dm3.core.validation;

import jp.co.transcosmos.dm3.validation.Validation;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

/**
 * バリデーションメッセージに行数を表示する為の Adapter クラス.
 * <p>
 * エラーメッセージに行番号を含める場合、バリデーションクラスをこの Adapter クラスでラップする。<br/>
 * <br/>
 * １０行目のエラーとして出力する場合の例）<br/>
 * ValidationChain chain = new ValidationChain("label", value);<br/>
 * chain.addValidation(new LineAdapter(new NullOrEmptyCheckValidation(),10));<br/>
 * <p>
 * <pre>
 * 担当者		修正日		修正内容
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.03.20	新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 * 
 */
public class LineAdapter implements Validation {

	/** 委譲先バリデーションクラス */
	private Validation validation;
	/** 行番号 */
	private int lineNo;

	

	/**
	 * コンストラクター<br/>
	 * <br/>
	 * @param validation 委譲先バリデーション
	 * @param lineNo 行番号
	 */
	public LineAdapter(Validation validation, int lineNo){
		this.validation = validation;
		this.lineNo = lineNo;
	}


	
	/**
	 * バリデーション処理<br/>
	 * <br/>
	 * @param name エラーフィールドのラベル名
	 * @param value チェック対象値
	 */
	@Override
	public ValidationFailure validate(String name, Object value) {

		// 委譲先のバリデーションを実行する。
		// もし例外が発生した場合は、LineValidationFailure のインスタンスを生成して復帰する。
		// LineValidationFailure は、ValidationFailure　の拡張クラスで行番号のプロパティ
		// を保持する。
		ValidationFailure failure = this.validation.validate(name, value);
		if (failure == null) return null;

		return new LineValidationFailure(failure, this.lineNo);
	}

}
