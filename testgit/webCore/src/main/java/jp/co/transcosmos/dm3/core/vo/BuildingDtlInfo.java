package jp.co.transcosmos.dm3.core.vo;

import java.io.Serializable;
import java.math.BigDecimal;

public class BuildingDtlInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/** システム建物CD */
	private String sysBuildingCd;
	/** 建物面積 */
	private BigDecimal buildingArea;
	/** 建物面積_補足 */
	private String buildingAreaMemo;
	/** 建ぺい率 */
	private BigDecimal coverage;
	/** 建ぺい率_補足 */
	private String coverageMemo;
	/** 容積率 */
	private BigDecimal buildingRate;
	/** 容積率_補足 */
	private String buildingRateMemo;
	/** 総戸数 */
	private Integer totalHouseCnt;
	/** 賃貸戸数 */
	private Integer leaseHouseCnt;
	/** 建物緯度 */
	private BigDecimal buildingLatitude;
	/** 建物経度 */
	private BigDecimal buildingLongitude;
	
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
	 * 建物面積を取得する。<br/>
	 * <br/>
	 * @return 建物面積
	 */
	public BigDecimal getBuildingArea() {
		return buildingArea;
	}
	/**
	 * 建物面積を設定する。<br/>
	 * <br/>
	 * @param buildingArea 建物面積
	 */
	public void setBuildingArea(BigDecimal buildingArea) {
		this.buildingArea = buildingArea;
	}
	/**
	 * 建物面積_補足を取得する。<br/>
	 * <br/>
	 * @return 建物面積_補足
	 */
	public String getBuildingAreaMemo() {
		return buildingAreaMemo;
	}
	/**
	 * 建物面積_補足を設定する。<br/>
	 * <br/>
	 * @param buildingAreaMemo 建物面積_補足
	 */
	public void setBuildingAreaMemo(String buildingAreaMemo) {
		this.buildingAreaMemo = buildingAreaMemo;
	}

	/**
	 * 総戸数を取得する。<br/>
	 * <br/>
	 * @return 総戸数
	 */
	public Integer getTotalHouseCnt() {
		return totalHouseCnt;
	}
	/**
	 * 総戸数を設定する。<br/>
	 * <br/>
	 * @param totalHouseCnt 総戸数
	 */
	public void setTotalHouseCnt(Integer totalHouseCnt) {
		this.totalHouseCnt = totalHouseCnt;
	}
	/**
	 * 賃貸戸数を取得する。<br/>
	 * <br/>
	 * @return 賃貸戸数
	 */
	public Integer getLeaseHouseCnt() {
		return leaseHouseCnt;
	}
	/**
	 * 賃貸戸数を設定する。<br/>
	 * <br/>
	 * @param leaseHouseCnt 賃貸戸数
	 */
	public void setLeaseHouseCnt(Integer leaseHouseCnt) {
		this.leaseHouseCnt = leaseHouseCnt;
	}
	/**
	 * 建物緯度を取得する。<br/>
	 * <br/>
	 * @return 建物緯度
	 */
	public BigDecimal getBuildingLatitude() {
		return buildingLatitude;
	}
	/**
	 * 建物緯度を設定する。<br/>
	 * <br/>
	 * @param buildingLatitude 建物緯度
	 */
	public void setBuildingLatitude(BigDecimal buildingLatitude) {
		this.buildingLatitude = buildingLatitude;
	}
	/**
	 * 建物経度を取得する。<br/>
	 * <br/>
	 * @return 建物経度
	 */
	public BigDecimal getBuildingLongitude() {
		return buildingLongitude;
	}
	/**
	 * 建物経度を設定する。<br/>
	 * <br/>
	 * @param buildingLongitude 建物経度
	 */
	public void setBuildingLongitude(BigDecimal buildingLongitude) {
		this.buildingLongitude = buildingLongitude;
	}
	/**
	 * 建ぺい率を取得する。<br/>
	 * <br/>
	 * @return 建ぺい率
	 */
	public BigDecimal getCoverage() {
		return coverage;
	}
	/**
	 * 建ぺい率を設定する。<br/>
	 * <br/>
	 * @param coverage 建ぺい率
	 */
	public void setCoverage(BigDecimal coverage) {
		this.coverage = coverage;
	}
	/**
	 * 建ぺい率_補足を取得する。<br/>
	 * <br/>
	 * @return 建ぺい率_補足
	 */
	public String getCoverageMemo() {
		return coverageMemo;
	}
	/**
	 * 建ぺい率_補足を設定する。<br/>
	 * <br/>
	 * @param coverageMemo 建ぺい率_補足
	 */
	public void setCoverageMemo(String coverageMemo) {
		this.coverageMemo = coverageMemo;
	}
	/**
	 * 容積率を取得する。<br/>
	 * <br/>
	 * @return 容積率
	 */
	public BigDecimal getBuildingRate() {
		return buildingRate;
	}
	/**
	 * 容積率を設定する。<br/>
	 * <br/>
	 * @param buildingRate 容積率
	 */
	public void setBuildingRate(BigDecimal buildingRate) {
		this.buildingRate = buildingRate;
	}
	/**
	 * 容積率_補足を取得する。<br/>
	 * <br/>
	 * @return 容積率_補足
	 */
	public String getBuildingRateMemo() {
		return buildingRateMemo;
	}
	/**
	 * 容積率_補足を設定する。<br/>
	 * <br/>
	 * @param buildingRateMemo 容積率_補足
	 */
	public void setBuildingRateMemo(String buildingRateMemo) {
		this.buildingRateMemo = buildingRateMemo;
	}
}
