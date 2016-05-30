package jp.co.transcosmos.dm3.core.vo;

/**
 * <pre>
 * 駅・路線対応表クラス.
 * 
 * クラス名、および、フィールド名はＤＢテーブル名、列名にマッピングされる。
 *
 * 担当者		修正日		修正内容
 * ------------ ----------- -----------------------------------------------------
 * I.Shu		2015.02.18	新規作成
 *
 * 注意事項
 *
 * </pre>
 * <p>
 * 注意事項<br/>
 * 個別カスタマイズで継承される可能性があるので、直接インスタンスを生成しない事。<br/>
 * インスタンスを取得する場合は、ValueObjectFactory から取得する事。<br/>
 * 
 */

public class StationRouteInfo {

	/** 路線CD */
	private String routeCd;
	/** 駅CD */
	private String stationCd;
	/** 表示順 */
	private Integer sortOrder;
	
	
	
	/**
	 * 路線CD を取得する。<br/>
	 * <br/>
	 * @return 路線CD
	 */
	public String getRouteCd() {
		return routeCd;
	}
	
	/**
	 * 路線CD を設定する。<br/>
	 * <br/>
	 * @param routeCd 路線CD
	 */
	public void setRouteCd(String routeCd) {
		this.routeCd = routeCd;
	}

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
	 * 表示順を取得する。<br/>
	 * <br/>
	 * @return 表示順
	 */
	public Integer getSortOrder() {
		return sortOrder;
	}
	
	/**
	 * 表示順を設定する。<br/>
	 * <br/>
	 * @param sortOrder 表示順
	 */
	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}

}
