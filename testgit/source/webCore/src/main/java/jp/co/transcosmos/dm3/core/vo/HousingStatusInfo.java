package jp.co.transcosmos.dm3.core.vo;


/**
 * 物件ステータス情報.
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
public class HousingStatusInfo {

	/** システム物件CD */
	private String sysHousingCd;
	/** 非公開フラグ */
	private String hiddenFlg;

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
	 * 非公開フラグ を取得する。<br/>
	 * <br/>
	 *
	 * @return 非公開フラグ
	 */
	public String getHiddenFlg() {
		return hiddenFlg;
	}

	/**
	 * 非公開フラグ を設定する。<br/>
	 * <br/>
	 *
	 * @param hiddenFlg
	 */
	public void setHiddenFlg(String hiddenFlg) {
		this.hiddenFlg = hiddenFlg;
	}
}
