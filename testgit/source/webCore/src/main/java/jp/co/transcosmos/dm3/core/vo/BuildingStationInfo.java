package jp.co.transcosmos.dm3.core.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import jp.co.transcosmos.dm3.validation.ValidationFailure;

/**
 * �����Ŋ��w���N���X.
 * <p>
 * �N���X���A����сA�t�B�[���h���͂c�a�e�[�u�����A�񖼂Ƀ}�b�s���O�����B
 * <p>
 * <pre>
 * �S����		�C����		�C�����e
 * ------------ ----------- -----------------------------------------------------
 * I.Shu		2015.03.09	�V�K�쐬
 * H.Mizuno		2015.03.16	�}�Ԃ̃f�[�^�^�� String ���� Integer �֕ύX�B�@�w���A��\�H�����ǉ�
 * </pre>
 * <p>
 * ���ӎ���<br/>
 * �ʃJ�X�^�}�C�Y�Ōp�������\��������̂ŁA���ڃC���X�^���X�𐶐����Ȃ����B<br/>
 * �C���X�^���X���擾����ꍇ�́AValueObjectFactory ����擾���鎖�B<br/>
 * 
 */
public class BuildingStationInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/** �V�X�e������CD */
	private String sysBuildingCd;
	
	/** �}�� */
	private Integer divNo;
	
	/** �\���� */
	private Integer sortOrder;
		
	/** ��\�H��CD */
	private String defaultRouteCd;
	
	/** ��\�H���� �i��\�H��CD �ɊY������f�[�^�������ꍇ�̕\���p�j*/
	private String defaultRouteName;
	
	/** �wCD */
	private String stationCd;
	
	/** �w���@�i�wCD �ɊY������f�[�^�������ꍇ�̕\���p�j */
	private String stationName;
	
	/** �Ŋ��w����̎�i */
	private String wayFromStation;
	
	/** �w����̋��� */
	private BigDecimal distanceFromStation;
	
	/** �w����̓k������ */
	private Integer timeFromStation;
	
	/** �o�X��Ж� */
	private String busCompany;
	
	/** �o�X�̏��v���� */
	private Integer busRequiredTime;
	
	/** �o�X�▼ */
	private String busStopName;
	
	/** �o�X�₩��̓k������ */
	private Integer timeFromBusStop;
	
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
	 * ��\�H��CD���擾����B<br/>
	 * <br/>
	 * @return ��\�H��CD
	 */
	public String getDefaultRouteCd() {
		return defaultRouteCd;
	}
	/**
	 * ��\�H��CD��ݒ肷��B<br/>
	 * <br/>
	 * @param defaultRouteCd ��\�H��CD
	 */
	public void setDefaultRouteCd(String defaultRouteCd) {
		this.defaultRouteCd = defaultRouteCd;
	}
	/**
	 * ��\�H�������擾����B<br/>
	 * ��\�H��CD �ɊY�����郌�R�[�h�����݂��Ȃ��ꍇ�Ɏg�p����B<br/>
	 * <br/>
	 * @return�@��\�H����
	 */
	public String getDefaultRouteName() {
		return defaultRouteName;
	}
	/**
	 * ��\�H������ݒ肷��B<br/>
	 * ���c�a�X�V���́A���̎��_�̃}�X�^�̒l��ݒ肷��B<br/>
	 * <br/>
	 * @param defaultRouteName�@��\�H����
	 */
	public void setDefaultRouteName(String defaultRouteName) {
		this.defaultRouteName = defaultRouteName;
	}
	/**
	 * �wCD���擾����B<br/>
	 * <br/>
	 * @return �wCD
	 */
	public String getStationCd() {
		return stationCd;
	}
	/**
	 * �wCD��ݒ肷��B<br/>
	 * <br/>
	 * @param stationCd �wCD
	 */
	public void setStationCd(String stationCd) {
		this.stationCd = stationCd;
	}
	/**
	 * �w�����擾����B<br/>
	 * �wCD �ɊY�����郌�R�[�h�����݂��Ȃ��ꍇ�Ɏg�p����B<br/>
	 * <br/>
	 * @return �w��
	 */
	public String getStationName() {
		return stationName;
	}
	/**
	 * �w����ݒ肷��B<br/>
	 * ���c�a�X�V���́A���̎��_�̃}�X�^�̒l��ݒ肷��B<br/>
	 * <br/>
	 * @param stationName �w��
	 */
	public void setStationName(String stationName) {
		this.stationName = stationName;
	}
	/**
	 * �Ŋ��w����̎�i���擾����B<br/>
	 * <br/>
	 * @return �Ŋ��w����̎�i
	 */
	public String getWayFromStation() {
		return wayFromStation;
	}


	/**
	 * �Ŋ��w����̎�i��ݒ肷��B<br/>
	 * <br/>
	 * @param wayFromStation �Ŋ��w����̎�i
	 */
	public void setWayFromStation(String wayFromStation) {
		this.wayFromStation = wayFromStation;
	}


	/**
	 * �w����̋������擾����B<br/>
	 * <br/>
	 * @return �w����̋���
	 */
	public BigDecimal getDistanceFromStation() {
		return distanceFromStation;
	}


	/**
	 * �w����̋�����ݒ肷��B<br/>
	 * <br/>
	 * @param distanceFromStation �w����̋���
	 */
	public void setDistanceFromStation(BigDecimal distanceFromStation) {
		this.distanceFromStation = distanceFromStation;
	}


	/**
	 * �w����̓k�����Ԃ��擾����B<br/>
	 * <br/>
	 * @return �w����̓k������
	 */
	public Integer getTimeFromStation() {
		return timeFromStation;
	}


	/**
	 * �w����̓k�����Ԃ�ݒ肷��B<br/>
	 * <br/>
	 * @param timeFromStation �w����̓k������
	 */
	public void setTimeFromStation(Integer timeFromStation) {
		this.timeFromStation = timeFromStation;
	}


	/**
	 * �o�X��Ж����擾����B<br/>
	 * <br/>
	 * @return �o�X��Ж�
	 */
	public String getBusCompany() {
		return busCompany;
	}


	/**
	 * �o�X��Ж���ݒ肷��B<br/>
	 * <br/>
	 * @param busCompany �o�X��Ж�
	 */
	public void setBusCompany(String busCompany) {
		this.busCompany = busCompany;
	}


	/**
	 * �o�X�̏��v���Ԃ��擾����B<br/>
	 * <br/>
	 * @return �o�X�̏��v����
	 */
	public Integer getBusRequiredTime() {
		return busRequiredTime;
	}


	/**
	 * �o�X�̏��v���Ԃ�ݒ肷��B<br/>
	 * <br/>
	 * @param busRequiredTime �o�X�̏��v����
	 */
	public void setBusRequiredTime(Integer busRequiredTime) {
		this.busRequiredTime = busRequiredTime;
	}


	/**
	 * �o�X�▼���擾����B<br/>
	 * <br/>
	 * @return �o�X�▼
	 */
	public String getBusStopName() {
		return busStopName;
	}


	/**
	 * �o�X�▼��ݒ肷��B<br/>
	 * <br/>
	 * @param busStopName �o�X�▼
	 */
	public void setBusStopName(String busStopName) {
		this.busStopName = busStopName;
	}


	/**
	 * �o�X�₩��̓k�����Ԃ��擾����B<br/>
	 * <br/>
	 * @return �o�X�₩��̓k������
	 */
	public Integer getTimeFromBusStop() {
		return timeFromBusStop;
	}


	/**
	 * �o�X�₩��̓k�����Ԃ�ݒ肷��B<br/>
	 * <br/>
	 * @param timeFromBusStop �o�X�₩��̓k������
	 */
	public void setTimeFromBusStop(Integer timeFromBusStop) {
		this.timeFromBusStop = timeFromBusStop;
	}

	public boolean validate(List<ValidationFailure> errors, String mode) {
        int startSize = errors.size();
        return (startSize == errors.size());
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

}