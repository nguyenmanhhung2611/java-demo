package jp.co.transcosmos.dm3.core.model.feature.form;

import jp.co.transcosmos.dm3.core.model.housing.Housing;
import jp.co.transcosmos.dm3.form.PagingListForm;



/**
 * 特集ID に該当する物件を検索するパラメータの受取り用フォーム.
 * <p>
 * 検索条件となる特集ID、ページ位置の情報を取得し、検索結果を格納する。
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
public class FeatureSearchForm extends PagingListForm<Housing> {

	/** 特集ページID */
	private String featurePageId;



	/**
	 * コンストラクター<br/>
	 * Factory 以外からのインスタンス生成を制限する為、コンストラクターを隠蔽する。<br/>
	 * <br/>
	 */
	protected FeatureSearchForm(){
		super();
	}



	/**
	 * 特集ページID を取得する<br/>
	 * <br/>
	 * @return 特集ページID
	 */
	public String getFeaturePageId() {
		return featurePageId;
	}

	/**
	 * 特集ページID を設定する<br/>
	 * <br/>
	 * @param featurePageId 特集ページID
	 */
	public void setFeaturePageId(String featurePageId) {
		this.featurePageId = featurePageId;
	}

}
