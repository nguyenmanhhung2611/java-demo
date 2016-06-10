package jp.co.transcosmos.dm3.core.model.housingRequest.form;

import jp.co.transcosmos.dm3.core.model.housing.Housing;
import jp.co.transcosmos.dm3.form.PagingListForm;


/**
 * 物件リクエストID に該当する物件を検索するパラメータの受取り用フォーム.
 * <p>
 * 検索条件となる物件リクエストID、ページ位置の情報を取得し、検索結果を格納する。
 * <p>
 * <pre>
 * 担当者		修正日		修正内容
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.03.10	新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 * 
 */
public class RequestSearchForm extends PagingListForm<Housing> {

	/** 物件リクエストID */
	private String housingRequestId;
	
	
	
	/**
	 * コンストラクター<br/>
	 * Factory 以外からのインスタンス生成を制限する為、コンストラクターを隠蔽する。<br/>
	 * <br/>
	 */
	protected RequestSearchForm(){
		super();
	}



	/**
	 * 物件リクエストID を取得する。<br/>
	 * <br/>
	 * @return 物件リクエストID
	 */
	public String getHousingRequestId() {
		return housingRequestId;
	}

	/**
	 * 物件リクエストID を設定する。<br/>
	 * <br/>
	 * @param housingRequestId 物件リクエストID
	 */
	public void setHousingRequestId(String housingRequestId) {
		this.housingRequestId = housingRequestId;
	}

}
