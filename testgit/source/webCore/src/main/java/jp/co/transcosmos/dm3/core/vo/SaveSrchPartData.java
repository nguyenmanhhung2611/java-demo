package jp.co.transcosmos.dm3.core.vo;


/**
 * 検索条件保存こだわり条件情報.
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
public class SaveSrchPartData {

	/** 検索条件ID */
	private String srchId;
	/** こだわり条件CD */
	private String partSrchCd;

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
	 * こだわり条件CD を取得する。<br/>
	 * <br/>
	 *
	 * @return こだわり条件CD
	 */
	public String getPartSrchCd() {
		return partSrchCd;
	}

	/**
	 * こだわり条件CD を設定する。<br/>
	 * <br/>
	 *
	 * @param partSrchCd
	 */
	public void setPartSrchCd(String partSrchCd) {
		this.partSrchCd = partSrchCd;
	}
}
