package jp.co.transcosmos.dm3.core.vo;


/**
 * 物件設備情報.
 * <p>
 * クラス名、および、フィールド名はＤＢテーブル名、列名にマッピングされる。
 * <p>
 *
 * <pre>
 * 担当者        修正日        修正内容
 * ------------ ----------- -----------------------------------------------------
 * Trans        2015.03.10	新規作成
 * H.Mizuno		2015.03.18	設備コメント削除
 * </pre>
 * <p>
 * 注意事項<br/>
 * 個別カスタマイズで継承される可能性があるので、直接インスタンスを生成しない事。<br/>
 * インスタンスを取得する場合は、ValueObjectFactory から取得する事。<br/>
 *
 */
public class HousingEquipInfo {

	/** システム物件CD */
	private String sysHousingCd;
	/** 設備CD */
	private String equipCd;

	
	
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
	 * 設備CD を取得する。<br/>
	 * <br/>
	 *
	 * @return 設備CD
	 */
	public String getEquipCd() {
		return equipCd;
	}

	/**
	 * 設備CD を設定する。<br/>
	 * <br/>
	 *
	 * @param equipCd
	 */
	public void setEquipCd(String equipCd) {
		this.equipCd = equipCd;
	}

}
