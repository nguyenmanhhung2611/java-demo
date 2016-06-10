package jp.co.transcosmos.dm3.core.vo;


/**
 * 物件リクエスト物件種類情報.
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
public class HousingReqKind {

	/** 物件リクエストID */
	private String housingRequestId;
	/** 物件種類CD */
	private String housingKindCd;

	/**
	 * 物件リクエストID を取得する。<br/>
	 * <br/>
	 *
	 * @return 物件リクエストID
	 */
	public String getHousingRequestId() {
		return housingRequestId;
	}

	/**
	 * 物件リクエストID を設定する。<br/>
	 * <br/>
	 *
	 * @param housingRequestId
	 */
	public void setHousingRequestId(String housingRequestId) {
		this.housingRequestId = housingRequestId;
	}

	/**
	 * 物件種類CD を取得する。<br/>
	 * <br/>
	 *
	 * @return 物件種類CD
	 */
	public String getHousingKindCd() {
		return housingKindCd;
	}

	/**
	 * 物件種類CD を設定する。<br/>
	 * <br/>
	 *
	 * @param housingKindCd
	 */
	public void setHousingKindCd(String housingKindCd) {
		this.housingKindCd = housingKindCd;
	}
}
