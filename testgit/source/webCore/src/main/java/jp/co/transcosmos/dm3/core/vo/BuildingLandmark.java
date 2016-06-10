package jp.co.transcosmos.dm3.core.vo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 建物ランドマーク情報クラス.
 * <p>
 * クラス名、および、フィールド名はＤＢテーブル名、列名にマッピングされる。
 * <p>
 * <pre>
 * 担当者		修正日		修正内容
 * ------------ ----------- -----------------------------------------------------
 * I.Shu		2015.03.09	新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 * 個別カスタマイズで継承される可能性があるので、直接インスタンスを生成しない事。<br/>
 * インスタンスを取得する場合は、ValueObjectFactory から取得する事。<br/>
 * 
 */
public class BuildingLandmark implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/** システム建物CD */
	private String sysBuildingCd;
	/** 枝番 */
	private Integer divNo;
	/** ランドマークの種類 */
	private String landmarkType;
	/** ランドマークからの手段 */
	private String wayFromLandmark;
	/** 表示順 */
	private Integer sortOrder;
	/** ランドマーク名 */
	private String landmarkName;
	/** ランドマークからの徒歩時間 */
	private Integer timeFromLandmark;
	/** ランドマークからの距離 */
	private BigDecimal distanceFromLandmark;

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
	 * ランドマークの種類を取得する。<br/>
	 * <br/>
	 * @return ランドマークの種類
	 */
	public String getLandmarkType() {
		return landmarkType;
	}
	/**
	 * ランドマークの種類を設定する。<br/>
	 * <br/>
	 * @param landmarkType ランドマークの種類
	 */
	public void setLandmarkType(String landmarkType) {
		this.landmarkType = landmarkType;
	}
	/**
	 * ランドマークからの手段を取得する。<br/>
	 * <br/>
	 * @return ランドマークからの手段
	 */
	public String getWayFromLandmark() {
		return wayFromLandmark;
	}
	/**
	 * ランドマークからの手段を設定する。<br/>
	 * <br/>
	 * @param wayFromLandmark ランドマークからの手段
	 */
	public void setWayFromLandmark(String wayFromLandmark) {
		this.wayFromLandmark = wayFromLandmark;
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
	/**
	 * ランドマーク名を取得する。<br/>
	 * <br/>
	 * @return ランドマーク名
	 */
	public String getLandmarkName() {
		return landmarkName;
	}
	/**
	 * ランドマーク名を設定する。<br/>
	 * <br/>
	 * @param landmarkName ランドマーク名
	 */
	public void setLandmarkName(String landmarkName) {
		this.landmarkName = landmarkName;
	}
	/**
	 * ランドマークからの徒歩時間を取得する。<br/>
	 * <br/>
	 * @return ランドマークからの徒歩時間
	 */
	public Integer getTimeFromLandmark() {
		return timeFromLandmark;
	}
	/**
	 * ランドマークからの徒歩時間を設定する。<br/>
	 * <br/>
	 * @param timeFromLandmark ランドマークからの徒歩時間
	 */
	public void setTimeFromLandmark(Integer timeFromLandmark) {
		this.timeFromLandmark = timeFromLandmark;
	}
	/**
	 * ランドマークからの距離を取得する。<br/>
	 * <br/>
	 * @return ランドマークからの距離
	 */
	public BigDecimal getDistanceFromLandmark() {
		return distanceFromLandmark;
	}
	/**
	 * ランドマークからの距離を設定する。<br/>
	 * <br/>
	 * @param distanceFromLandmark ランドマークからの距離
	 */
	public void setDistanceFromLandmark(BigDecimal distanceFromLandmark) {
		this.distanceFromLandmark = distanceFromLandmark;
	}
	
	
}
