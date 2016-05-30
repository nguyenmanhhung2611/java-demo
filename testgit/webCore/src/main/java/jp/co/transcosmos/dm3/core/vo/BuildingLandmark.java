package jp.co.transcosmos.dm3.core.vo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * ���������h�}�[�N���N���X.
 * <p>
 * �N���X���A����сA�t�B�[���h���͂c�a�e�[�u�����A�񖼂Ƀ}�b�s���O�����B
 * <p>
 * <pre>
 * �S����		�C����		�C�����e
 * ------------ ----------- -----------------------------------------------------
 * I.Shu		2015.03.09	�V�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 * �ʃJ�X�^�}�C�Y�Ōp�������\��������̂ŁA���ڃC���X�^���X�𐶐����Ȃ����B<br/>
 * �C���X�^���X���擾����ꍇ�́AValueObjectFactory ����擾���鎖�B<br/>
 * 
 */
public class BuildingLandmark implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/** �V�X�e������CD */
	private String sysBuildingCd;
	/** �}�� */
	private Integer divNo;
	/** �����h�}�[�N�̎�� */
	private String landmarkType;
	/** �����h�}�[�N����̎�i */
	private String wayFromLandmark;
	/** �\���� */
	private Integer sortOrder;
	/** �����h�}�[�N�� */
	private String landmarkName;
	/** �����h�}�[�N����̓k������ */
	private Integer timeFromLandmark;
	/** �����h�}�[�N����̋��� */
	private BigDecimal distanceFromLandmark;

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
	 * �}�Ԃ��擾����B<br/>
	 * <br/>
	 * @return �}��
	 */
	public Integer getDivNo() {
		return divNo;
	}
	/**
	 * �}�Ԃ�ݒ肷��B<br/>
	 * <br/>
	 * @param divNo �}��
	 */
	public void setDivNo(Integer divNo) {
		this.divNo = divNo;
	}
	/**
	 * �����h�}�[�N�̎�ނ��擾����B<br/>
	 * <br/>
	 * @return �����h�}�[�N�̎��
	 */
	public String getLandmarkType() {
		return landmarkType;
	}
	/**
	 * �����h�}�[�N�̎�ނ�ݒ肷��B<br/>
	 * <br/>
	 * @param landmarkType �����h�}�[�N�̎��
	 */
	public void setLandmarkType(String landmarkType) {
		this.landmarkType = landmarkType;
	}
	/**
	 * �����h�}�[�N����̎�i���擾����B<br/>
	 * <br/>
	 * @return �����h�}�[�N����̎�i
	 */
	public String getWayFromLandmark() {
		return wayFromLandmark;
	}
	/**
	 * �����h�}�[�N����̎�i��ݒ肷��B<br/>
	 * <br/>
	 * @param wayFromLandmark �����h�}�[�N����̎�i
	 */
	public void setWayFromLandmark(String wayFromLandmark) {
		this.wayFromLandmark = wayFromLandmark;
	}
	/**
	 * �\�������擾����B<br/>
	 * <br/>
	 * @return �\����
	 */
	public Integer getSortOrder() {
		return sortOrder;
	}
	/**
	 * �\������ݒ肷��B<br/>
	 * <br/>
	 * @param sortOrder �\����
	 */
	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}
	/**
	 * �����h�}�[�N�����擾����B<br/>
	 * <br/>
	 * @return �����h�}�[�N��
	 */
	public String getLandmarkName() {
		return landmarkName;
	}
	/**
	 * �����h�}�[�N����ݒ肷��B<br/>
	 * <br/>
	 * @param landmarkName �����h�}�[�N��
	 */
	public void setLandmarkName(String landmarkName) {
		this.landmarkName = landmarkName;
	}
	/**
	 * �����h�}�[�N����̓k�����Ԃ��擾����B<br/>
	 * <br/>
	 * @return �����h�}�[�N����̓k������
	 */
	public Integer getTimeFromLandmark() {
		return timeFromLandmark;
	}
	/**
	 * �����h�}�[�N����̓k�����Ԃ�ݒ肷��B<br/>
	 * <br/>
	 * @param timeFromLandmark �����h�}�[�N����̓k������
	 */
	public void setTimeFromLandmark(Integer timeFromLandmark) {
		this.timeFromLandmark = timeFromLandmark;
	}
	/**
	 * �����h�}�[�N����̋������擾����B<br/>
	 * <br/>
	 * @return �����h�}�[�N����̋���
	 */
	public BigDecimal getDistanceFromLandmark() {
		return distanceFromLandmark;
	}
	/**
	 * �����h�}�[�N����̋�����ݒ肷��B<br/>
	 * <br/>
	 * @param distanceFromLandmark �����h�}�[�N����̋���
	 */
	public void setDistanceFromLandmark(BigDecimal distanceFromLandmark) {
		this.distanceFromLandmark = distanceFromLandmark;
	}
	
	
}
