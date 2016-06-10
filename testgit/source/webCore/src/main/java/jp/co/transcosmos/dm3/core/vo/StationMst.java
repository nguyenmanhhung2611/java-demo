package jp.co.transcosmos.dm3.core.vo;

import java.util.Date;

/**
 * 駅名マスタクラス.
 * <p>
 * クラス名、および、フィールド名はＤＢテーブル名、列名にマッピングされる。
 * <p>
 * <pre>
 * 担当者		修正日		修正内容
 * ------------ ----------- -----------------------------------------------------
 * 細島　崇		2006.12.26	新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 * 個別カスタマイズで継承される可能性があるので、直接インスタンスを生成しない事。<br/>
 * インスタンスを取得する場合は、ValueObjectFactory から取得する事。<br/>
 * 
 */
public class StationMst {
	/** 駅CD */
	private String stationCd;
	/** 駅名 */
	private String stationName;
	/** 駅名・カッコ付き */
	private String stationNameFull;
	/** 都道府県CD */
	private String prefCd;
	/** 登録日 */
	private Date insDate;
	/** 登録者 */
	private String insUserId;
	/**　最終更新日 */
	private Date updDate;
	/** 最終更新者 */
	private String updUserId;
	
	

	/**
	 * 駅CD を取得する。<br/>
	 * <br/>
	 * @return 駅CD
	 */
	public String getStationCd() {
		return stationCd;
	}
	
	/**
	 * 駅CD を設定する。<br/>
	 * <br/>
	 * @param stationCd 駅CD
	 */
	public void setStationCd(String stationCd) {
		this.stationCd = stationCd;
	}

	/**
	 * 駅名を取得する。<br/>
	 * <br/>
	 * @return 駅名
	 */
	public String getStationName() {
		return stationName;
	}
	
	/**
	 * 駅名を設定する。<br/>
	 * <br/>
	 * @param stationName 駅名
	 */
	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	/**
	 * 駅名・カッコ付きを取得する。<br/>
	 * <br/>
	 * @return 駅名・カッコ付き
	 */
	public String getStationNameFull() {
		return stationNameFull;
	}
	
	/**
	 * 駅名・カッコ付きを設定する。<br/>
	 * <br/>
	 * @param stationNameFull 駅名・カッコ付き
	 */
	public void setStationNameFull(String stationNameFull) {
		this.stationNameFull = stationNameFull;
	}

	/**
	 * 都道府県CD を取得する。<br/>
	 * <br/>
	 * @return 都道府県CD
	 */
	public String getPrefCd() {
		return prefCd;
	}
	
	/**
	 * 都道府県CD を設定する。<br/>
	 * <br/>
	 * @param prefCd 都道府県CD
	 */
	public void setPrefCd(String prefCd) {
		this.prefCd = prefCd;
	}
	
	/**
	 * 登録日を取得する。<br/>
	 * <br/>
	 * @return 登録日
	 */
	public Date getInsDate() {
		return insDate;
	}
	
	/**
	 * 登録日を設定する。<br/>
	 * <br/>
	 * @param insDate 登録日
	 */
	public void setInsDate(Date insDate) {
		this.insDate = insDate;
	}

	/**
	 * 登録者を取得する。<br/>
	 * <br/>
	 * @return 登録者
	 */
	public String getInsUserId() {
		return insUserId;
	}

	/**
	 * 登録者を設定する。<br/>
	 * <br/>
	 * @param insUserId 登録者
	 */
	public void setInsUserId(String insUserId) {
		this.insUserId = insUserId;
	}

	/**
	 * 最終更新日を取得する。<br/>
	 * <br/>
	 * @return 最終更新日
	 */
	public Date getUpdDate() {
		return updDate;
	}
	
	/**
	 * 最終更新日を設定する。<br/>
	 * <br/>
	 * @param updDate 最終更新日
	 */
	public void setUpdDate(Date updDate) {
		this.updDate = updDate;
	}


	/**
	 * 最終更新者を取得する。<br/>
	 * <br/>
	 * @return 最終更新者
	 */
	public String getUpdUserId() {
		return updUserId;
	}
	
	/**
	 * 最終更新者を設定する。<br/>
	 * <br/>
	 * @param updUserId 最終更新者
	 */
	public void setUpdUserId(String updUserId) {
		this.updUserId = updUserId;
	}
	
}
