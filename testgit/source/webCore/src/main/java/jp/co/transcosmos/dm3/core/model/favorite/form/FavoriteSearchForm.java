package jp.co.transcosmos.dm3.core.model.favorite.form;

import jp.co.transcosmos.dm3.dao.DAOCriteria;
import jp.co.transcosmos.dm3.dao.JoinResult;
import jp.co.transcosmos.dm3.form.PagingListForm;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;

/**
 * ユーザID に該当するお気に入り情報を検索するパラメータの受取り用フォーム.
 * <p>
 * 検索条件となるユーザID、システム物件CD、ページ位置の情報を取得し、検索結果を格納する。
 * <p>
 * 
 * <pre>
 * 担当者		修正日		修正内容
 * ------------ ----------- -----------------------------------------------------
 * T.Nakamura	2015.03.23	新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 * 
 */
public class FavoriteSearchForm extends PagingListForm<JoinResult> {

	/** システム物件CD（検索条件） */
	private String sysHousingCd;

	/**
	 * コンストラクター<br/>
	 * Factory 以外からのインスタンス生成を制限する為、コンストラクターを隠蔽する。<br/>
	 * <br/>
	 */
	protected FavoriteSearchForm(){
		super();
	}

	/**
	 * システム物件CD を取得する<br/>
	 * <br/>
	 * @return システム物件CD
	 */
	public String getSysHousingCd() {
		return sysHousingCd;
	}

	/**
	 * システム物件CD を設定する<br/>
	 * <br/>
	 * @param userId システム物件CD
	 */
	public void setSysHousingCd(String sysHousingCd) {
		this.sysHousingCd = sysHousingCd;
	}

	/* (非 Javadoc)
	 * @see jp.co.transcosmos.dm3.form.PagingListForm#buildCriteria()
	 */
	@Override
	public DAOCriteria buildCriteria() {

		DAOCriteria criteria = super.buildCriteria();

		// システム物件CDの検索条件生成
		if (!StringValidateUtil.isEmpty(this.sysHousingCd)) {
			criteria.addWhereClause("sysHousingCd", this.sysHousingCd);
		}

		return criteria;
	}

}
