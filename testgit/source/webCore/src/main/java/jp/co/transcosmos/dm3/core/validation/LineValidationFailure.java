package jp.co.transcosmos.dm3.core.validation;

import jp.co.transcosmos.dm3.validation.ValidationFailure;

/**
 * 行数を表示する為の拡張エラーオブジェクト.
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
public class LineValidationFailure extends ValidationFailure {

	/** 行番号（エラーメッセージに使用する。） */
	private int lineNo;

	
	/**
	 * コンストラクター<br/>
	 * <br/>
	 * @param type エラーの種類
	 * @param name バリデーションの入力ラベル名
	 * @param value チェック対象値
	 * @param extraInfo オブション情報
	 */
	protected LineValidationFailure(String type, String name, Object value, String[] extraInfo) {
		super(type, name, value, extraInfo);
	}

	/**
	 * コンストラクター<br/>
	 * 通常はこのコンストラクターを使用する。<br/>
	 * <br/>
	 * @param failure 委譲先のバリデーションクラス
	 * @param lineNo 行番号
	 */
	public LineValidationFailure(ValidationFailure failure, int lineNo) {
		super(failure.getType(), failure.getName(), failure.getValue(), failure.getExtraInfo());
		this.lineNo = lineNo;
	}


	
	/**
	 * 行番号を取得する。<br/>
	 * <br/>
	 * @return 行番号
	 */
	public int getLineNo() {
		return lineNo;
	}

	/**
	 * 行番号を設定する。<br/>
	 * <br/>
	 * @param lineNo 行番号
	 */
	public void setLineNo(int lineNo) {
		this.lineNo = lineNo;
	}
}
