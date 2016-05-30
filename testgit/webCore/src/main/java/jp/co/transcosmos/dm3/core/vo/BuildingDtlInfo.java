package jp.co.transcosmos.dm3.core.vo;

import java.io.Serializable;
import java.math.BigDecimal;

public class BuildingDtlInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/** �V�X�e������CD */
	private String sysBuildingCd;
	/** �����ʐ� */
	private BigDecimal buildingArea;
	/** �����ʐ�_�⑫ */
	private String buildingAreaMemo;
	/** ���؂��� */
	private BigDecimal coverage;
	/** ���؂���_�⑫ */
	private String coverageMemo;
	/** �e�ϗ� */
	private BigDecimal buildingRate;
	/** �e�ϗ�_�⑫ */
	private String buildingRateMemo;
	/** ���ː� */
	private Integer totalHouseCnt;
	/** ���݌ː� */
	private Integer leaseHouseCnt;
	/** �����ܓx */
	private BigDecimal buildingLatitude;
	/** �����o�x */
	private BigDecimal buildingLongitude;
	
	/**
	 * �V�X�e������CD���擾����B<br/>
	 * <br/>
	 * @return �V�X�e������CD
	 */
	public String getSysBuildingCd() {
		return sysBuildingCd;
	}
	/**
	 * �V�X�e������CD��ݒ肷��B<br/>
	 * <br/>
	 * @param sysBuildingCd �V�X�e������CD
	 */
	public void setSysBuildingCd(String sysBuildingCd) {
		this.sysBuildingCd = sysBuildingCd;
	}
	/**
	 * �����ʐς��擾����B<br/>
	 * <br/>
	 * @return �����ʐ�
	 */
	public BigDecimal getBuildingArea() {
		return buildingArea;
	}
	/**
	 * �����ʐς�ݒ肷��B<br/>
	 * <br/>
	 * @param buildingArea �����ʐ�
	 */
	public void setBuildingArea(BigDecimal buildingArea) {
		this.buildingArea = buildingArea;
	}
	/**
	 * �����ʐ�_�⑫���擾����B<br/>
	 * <br/>
	 * @return �����ʐ�_�⑫
	 */
	public String getBuildingAreaMemo() {
		return buildingAreaMemo;
	}
	/**
	 * �����ʐ�_�⑫��ݒ肷��B<br/>
	 * <br/>
	 * @param buildingAreaMemo �����ʐ�_�⑫
	 */
	public void setBuildingAreaMemo(String buildingAreaMemo) {
		this.buildingAreaMemo = buildingAreaMemo;
	}

	/**
	 * ���ː����擾����B<br/>
	 * <br/>
	 * @return ���ː�
	 */
	public Integer getTotalHouseCnt() {
		return totalHouseCnt;
	}
	/**
	 * ���ː���ݒ肷��B<br/>
	 * <br/>
	 * @param totalHouseCnt ���ː�
	 */
	public void setTotalHouseCnt(Integer totalHouseCnt) {
		this.totalHouseCnt = totalHouseCnt;
	}
	/**
	 * ���݌ː����擾����B<br/>
	 * <br/>
	 * @return ���݌ː�
	 */
	public Integer getLeaseHouseCnt() {
		return leaseHouseCnt;
	}
	/**
	 * ���݌ː���ݒ肷��B<br/>
	 * <br/>
	 * @param leaseHouseCnt ���݌ː�
	 */
	public void setLeaseHouseCnt(Integer leaseHouseCnt) {
		this.leaseHouseCnt = leaseHouseCnt;
	}
	/**
	 * �����ܓx���擾����B<br/>
	 * <br/>
	 * @return �����ܓx
	 */
	public BigDecimal getBuildingLatitude() {
		return buildingLatitude;
	}
	/**
	 * �����ܓx��ݒ肷��B<br/>
	 * <br/>
	 * @param buildingLatitude �����ܓx
	 */
	public void setBuildingLatitude(BigDecimal buildingLatitude) {
		this.buildingLatitude = buildingLatitude;
	}
	/**
	 * �����o�x���擾����B<br/>
	 * <br/>
	 * @return �����o�x
	 */
	public BigDecimal getBuildingLongitude() {
		return buildingLongitude;
	}
	/**
	 * �����o�x��ݒ肷��B<br/>
	 * <br/>
	 * @param buildingLongitude �����o�x
	 */
	public void setBuildingLongitude(BigDecimal buildingLongitude) {
		this.buildingLongitude = buildingLongitude;
	}
	/**
	 * ���؂������擾����B<br/>
	 * <br/>
	 * @return ���؂���
	 */
	public BigDecimal getCoverage() {
		return coverage;
	}
	/**
	 * ���؂�����ݒ肷��B<br/>
	 * <br/>
	 * @param coverage ���؂���
	 */
	public void setCoverage(BigDecimal coverage) {
		this.coverage = coverage;
	}
	/**
	 * ���؂���_�⑫���擾����B<br/>
	 * <br/>
	 * @return ���؂���_�⑫
	 */
	public String getCoverageMemo() {
		return coverageMemo;
	}
	/**
	 * ���؂���_�⑫��ݒ肷��B<br/>
	 * <br/>
	 * @param coverageMemo ���؂���_�⑫
	 */
	public void setCoverageMemo(String coverageMemo) {
		this.coverageMemo = coverageMemo;
	}
	/**
	 * �e�ϗ����擾����B<br/>
	 * <br/>
	 * @return �e�ϗ�
	 */
	public BigDecimal getBuildingRate() {
		return buildingRate;
	}
	/**
	 * �e�ϗ���ݒ肷��B<br/>
	 * <br/>
	 * @param buildingRate �e�ϗ�
	 */
	public void setBuildingRate(BigDecimal buildingRate) {
		this.buildingRate = buildingRate;
	}
	/**
	 * �e�ϗ�_�⑫���擾����B<br/>
	 * <br/>
	 * @return �e�ϗ�_�⑫
	 */
	public String getBuildingRateMemo() {
		return buildingRateMemo;
	}
	/**
	 * �e�ϗ�_�⑫��ݒ肷��B<br/>
	 * <br/>
	 * @param buildingRateMemo �e�ϗ�_�⑫
	 */
	public void setBuildingRateMemo(String buildingRateMemo) {
		this.buildingRateMemo = buildingRateMemo;
	}
}
