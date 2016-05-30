package jp.co.transcosmos.dm3.core.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import jp.co.transcosmos.dm3.validation.ValidationFailure;

/**
 * 建物最寄り駅情報クラス.
 * <p>
 * クラス名、および、フィールド名はＤＢテーブル名、列名にマッピングされる。
 * <p>
 * <pre>
 * 担当者		修正日		修正内容
 * ------------ ----------- -----------------------------------------------------
 * I.Shu		2015.03.09	新規作成
 * H.Mizuno		2015.03.16	枝番のデータ型を String から Integer へ変更。　駅名、代表路線名追加
 * </pre>
 * <p>
 * 注意事項<br/>
 * 個別カスタマイズで継承される可能性があるので、直接インスタンスを生成しない事。<br/>
 * インスタンスを取得する場合は、ValueObjectFactory から取得する事。<br/>
 * 
 */
public class BuildingStationInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/** システム建物CD */
	private String sysBuildingCd;
	
	/** 枝番 */
	private Integer divNo;
	
	/** 表示順 */
	private Integer sortOrder;
		
	/** 代表路線CD */
	private String defaultRouteCd;
	
	/** 代表路線名 （代表路線CD に該当するデータが無い場合の表示用）*/
	private String defaultRouteName;
	
	/** 駅CD */
	private String stationCd;
	
	/** 駅名　（駅CD に該当するデータが無い場合の表示用） */
	private String stationName;
	
	/** 最寄り駅からの手段 */
	private String wayFromStation;
	
	/** 駅からの距離 */
	private BigDecimal distanceFromStation;
	
	/** 駅からの徒歩時間 */
	private Integer timeFromStation;
	
	/** バス会社名 */
	private String busCompany;
	
	/** バスの所要時間 */
	private Integer busRequiredTime;
	
	/** バス停名 */
	private String busStopName;
	
	/** バス停からの徒歩時間 */
	private Integer timeFromBusStop;
	
	/**
	 * システム建物CDを取得する。<br/>
	 * <br/>
	 * @return システム建物CD
	 */
	public String getSysBuildingCd() {
		return sysBuildingCd;
	}
	/**
	 * システム建物CDを設定する。<br/>
	 * <br/>
	 * @param sysBuildingCd システム建物CD
	 */
	public void setSysBuildingCd(String sysBuildingCd) {
		this.sysBuildingCd = sysBuildingCd;
	}
	/**
	 * 枝番を取得する。<br/>
	 * <br/>
	 * @return 枝番
	 */
	public Integer getDivNo() {
		return divNo;
	}
	/**
	 * 枝番を設定する。<br/>
	 * <br/>
	 * @param divNo 枝番
	 */
	public void setDivNo(Integer divNo) {
		this.divNo = divNo;
	}
	/**
	 * 代表路線CDを取得する。<br/>
	 * <br/>
	 * @return 代表路線CD
	 */
	public String getDefaultRouteCd() {
		return defaultRouteCd;
	}
	/**
	 * 代表路線CDを設定する。<br/>
	 * <br/>
	 * @param defaultRouteCd 代表路線CD
	 */
	public void setDefaultRouteCd(String defaultRouteCd) {
		this.defaultRouteCd = defaultRouteCd;
	}
	/**
	 * 代表路線名を取得する。<br/>
	 * 代表路線CD に該当するレコードが存在しない場合に使用する。<br/>
	 * <br/>
	 * @return　代表路線名
	 */
	public String getDefaultRouteName() {
		return defaultRouteName;
	}
	/**
	 * 代表路線名を設定する。<br/>
	 * ※ＤＢ更新時は、その時点のマスタの値を設定する。<br/>
	 * <br/>
	 * @param defaultRouteName　代表路線名
	 */
	public void setDefaultRouteName(String defaultRouteName) {
		this.defaultRouteName = defaultRouteName;
	}
	/**
	 * 駅CDを取得する。<br/>
	 * <br/>
	 * @return 駅CD
	 */
	public String getStationCd() {
		return stationCd;
	}
	/**
	 * 駅CDを設定する。<br/>
	 * <br/>
	 * @param stationCd 駅CD
	 */
	public void setStationCd(String stationCd) {
		this.stationCd = stationCd;
	}
	/**
	 * 駅名を取得する。<br/>
	 * 駅CD に該当するレコードが存在しない場合に使用する。<br/>
	 * <br/>
	 * @return 駅名
	 */
	public String getStationName() {
		return stationName;
	}
	/**
	 * 駅名を設定する。<br/>
	 * ※ＤＢ更新時は、その時点のマスタの値を設定する。<br/>
	 * <br/>
	 * @param stationName 駅名
	 */
	public void setStationName(String stationName) {
		this.stationName = stationName;
	}
	/**
	 * 最寄り駅からの手段を取得する。<br/>
	 * <br/>
	 * @return 最寄り駅からの手段
	 */
	public String getWayFromStation() {
		return wayFromStation;
	}


	/**
	 * 最寄り駅からの手段を設定する。<br/>
	 * <br/>
	 * @param wayFromStation 最寄り駅からの手段
	 */
	public void setWayFromStation(String wayFromStation) {
		this.wayFromStation = wayFromStation;
	}


	/**
	 * 駅からの距離を取得する。<br/>
	 * <br/>
	 * @return 駅からの距離
	 */
	public BigDecimal getDistanceFromStation() {
		return distanceFromStation;
	}


	/**
	 * 駅からの距離を設定する。<br/>
	 * <br/>
	 * @param distanceFromStation 駅からの距離
	 */
	public void setDistanceFromStation(BigDecimal distanceFromStation) {
		this.distanceFromStation = distanceFromStation;
	}


	/**
	 * 駅からの徒歩時間を取得する。<br/>
	 * <br/>
	 * @return 駅からの徒歩時間
	 */
	public Integer getTimeFromStation() {
		return timeFromStation;
	}


	/**
	 * 駅からの徒歩時間を設定する。<br/>
	 * <br/>
	 * @param timeFromStation 駅からの徒歩時間
	 */
	public void setTimeFromStation(Integer timeFromStation) {
		this.timeFromStation = timeFromStation;
	}


	/**
	 * バス会社名を取得する。<br/>
	 * <br/>
	 * @return バス会社名
	 */
	public String getBusCompany() {
		return busCompany;
	}


	/**
	 * バス会社名を設定する。<br/>
	 * <br/>
	 * @param busCompany バス会社名
	 */
	public void setBusCompany(String busCompany) {
		this.busCompany = busCompany;
	}


	/**
	 * バスの所要時間を取得する。<br/>
	 * <br/>
	 * @return バスの所要時間
	 */
	public Integer getBusRequiredTime() {
		return busRequiredTime;
	}


	/**
	 * バスの所要時間を設定する。<br/>
	 * <br/>
	 * @param busRequiredTime バスの所要時間
	 */
	public void setBusRequiredTime(Integer busRequiredTime) {
		this.busRequiredTime = busRequiredTime;
	}


	/**
	 * バス停名を取得する。<br/>
	 * <br/>
	 * @return バス停名
	 */
	public String getBusStopName() {
		return busStopName;
	}


	/**
	 * バス停名を設定する。<br/>
	 * <br/>
	 * @param busStopName バス停名
	 */
	public void setBusStopName(String busStopName) {
		this.busStopName = busStopName;
	}


	/**
	 * バス停からの徒歩時間を取得する。<br/>
	 * <br/>
	 * @return バス停からの徒歩時間
	 */
	public Integer getTimeFromBusStop() {
		return timeFromBusStop;
	}


	/**
	 * バス停からの徒歩時間を設定する。<br/>
	 * <br/>
	 * @param timeFromBusStop バス停からの徒歩時間
	 */
	public void setTimeFromBusStop(Integer timeFromBusStop) {
		this.timeFromBusStop = timeFromBusStop;
	}

	public boolean validate(List<ValidationFailure> errors, String mode) {
        int startSize = errors.size();
        return (startSize == errors.size());
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