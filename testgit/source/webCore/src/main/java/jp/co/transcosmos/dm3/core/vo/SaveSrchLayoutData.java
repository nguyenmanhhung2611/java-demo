package jp.co.transcosmos.dm3.core.vo;


/**
 * 検索条件保存間取り情報.
 * <p>
 * クラス名、および、フィールド名はＤＢテーブル名、列名にマッピングされる。
 * <p>
 *
 * <pre>
 * 担当者        修正日        修正内容
 * ------------ ----------- -----------------------------------------------------
 * Trans        2015.03.10     新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 * 個別カスタマイズで継承される可能性があるので、直接インスタンスを生成しない事。<br/>
 * インスタンスを取得する場合は、ValueObjectFactory から取得する事。<br/>
 *
 */
public class SaveSrchLayoutData {

	/** 検索条件ID */
	private String srchId;
	/** 間取CD */
	private String layoutCd;

	/**
	 * 検索条件ID を取得する。<br/>
	 * <br/>
	 *
	 * @return 検索条件ID
	 */
	public String getSrchId() {
		return srchId;
	}

	/**
	 * 検索条件ID を設定する。<br/>
	 * <br/>
	 *
	 * @param srchId
	 */
	public void setSrchId(String srchId) {
		this.srchId = srchId;
	}

	/**
	 * 間取CD を取得する。<br/>
	 * <br/>
	 *
	 * @return 間取CD
	 */
	public String getLayoutCd() {
		return layoutCd;
	}

	/**
	 * 間取CD を設定する。<br/>
	 * <br/>
	 *
	 * @param layoutCd
	 */
	public void setLayoutCd(String layoutCd) {
		this.layoutCd = layoutCd;
	}
}
