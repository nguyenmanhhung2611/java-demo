package jp.co.transcosmos.dm3.corePana.model.housing;

import jp.co.transcosmos.dm3.core.model.housingRequest.HousingRequest;


/**
 * <pre>
 * 物件リクエスト情報クラス
 *
 * 担当者       修正日      修正内容
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.03.11	新規作成
 *
 * 注意事項
 * Model 以外から直接インスタンスを生成しない事。
 *
 * </pre>
 */
public class PanaHousingRequest extends HousingRequest{

	/**
	 * コンストラクター<br/>
	 * 物件リクエストのモデル以外からインスタンスを生成出来ない様にコンストラクタを制限する。<br/>
	 * <br/>
	 */
	public PanaHousingRequest() {
		super();
	}

}
