package jp.co.transcosmos.dm3.core.vo;


/**
 * 郵便番号マスタ.
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
public class ZipMst {

	/** 郵便番号 */
	private String zip;
	/** 都道府県CD */
	private String prefCd;
	/** 市区町村CD */
	private String addressCd;

	/**
	 * 郵便番号 を取得する。<br/>
	 * <br/>
	 *
	 * @return 郵便番号
	 */
	public String getZip() {
		return zip;
	}

	/**
	 * 郵便番号 を設定する。<br/>
	 * <br/>
	 *
	 * @param zip
	 */
	public void setZip(String zip) {
		this.zip = zip;
	}

	/**
	 * 都道府県CD を取得する。<br/>
	 * <br/>
	 *
	 * @return 都道府県CD
	 */
	public String getPrefCd() {
		return prefCd;
	}

	/**
	 * 都道府県CD を設定する。<br/>
	 * <br/>
	 *
	 * @param prefCd
	 */
	public void setPrefCd(String prefCd) {
		this.prefCd = prefCd;
	}

	/**
	 * 市区町村CD を取得する。<br/>
	 * <br/>
	 *
	 * @return 市区町村CD
	 */
	public String getAddressCd() {
		return addressCd;
	}

	/**
	 * 市区町村CD を設定する。<br/>
	 * <br/>
	 *
	 * @param addressCd
	 */
	public void setAddressCd(String addressCd) {
		this.addressCd = addressCd;
	}
}
