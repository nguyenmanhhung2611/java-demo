package jp.co.transcosmos.dm3.core.vo;


/**
 * 物件拡張属性情報.
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
public class HousingExtInfo {

	/** システム物件CD */
	private String sysHousingCd;
	/** カテゴリー名 */
	private String category;
	/** Key 名 */
	private String keyName;
	/** 値 */
	private String dataValue;

	/**
	 * システム物件CD を取得する。<br/>
	 * <br/>
	 *
	 * @return システム物件CD
	 */
	public String getSysHousingCd() {
		return sysHousingCd;
	}

	/**
	 * システム物件CD を設定する。<br/>
	 * <br/>
	 *
	 * @param sysHousingCd
	 */
	public void setSysHousingCd(String sysHousingCd) {
		this.sysHousingCd = sysHousingCd;
	}

	/**
	 * カテゴリー名 を取得する。<br/>
	 * <br/>
	 *
	 * @return カテゴリー名
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * カテゴリー名 を設定する。<br/>
	 * <br/>
	 *
	 * @param category
	 */
	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * Key 名 を取得する。<br/>
	 * <br/>
	 *
	 * @return Key 名
	 */
	public String getKeyName() {
		return keyName;
	}

	/**
	 * Key 名 を設定する。<br/>
	 * <br/>
	 *
	 * @param keyName
	 */
	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}

	/**
	 * 値 を取得する。<br/>
	 * <br/>
	 *
	 * @return 値
	 */
	public String getDataValue() {
		return dataValue;
	}

	/**
	 * 値 を設定する。<br/>
	 * <br/>
	 *
	 * @param dataValue
	 */
	public void setDataValue(String dataValue) {
		this.dataValue = dataValue;
	}
}
