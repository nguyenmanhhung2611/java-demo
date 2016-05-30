package jp.co.transcosmos.dm3.core.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * �������N���X.
 * <p>
 * �N���X���A����сA�t�B�[���h���͂c�a�e�[�u�����A�񖼂Ƀ}�b�s���O�����B
 * <p>
 * <pre>
 * �S����		�C����		�C�����e
 * ------------ ----------- -----------------------------------------------------
 * I.Shu		2015.02.27	�V�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 * �ʃJ�X�^�}�C�Y�Ōp�������\��������̂ŁA���ڃC���X�^���X�𐶐����Ȃ����B<br/>
 * �C���X�^���X���擾����ꍇ�́AValueObjectFactory ����擾���鎖�B<br/>
 * 
 */
public class BuildingInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/** �V�X�e������CD */
	private String sysBuildingCd;
	/** �����ԍ� */
	private String buildingCd;
	/** �������CD */
	private String housingKindCd;
	/** �����\��CD */
	private String structCd;
	/** �\���p������ */
	private String displayBuildingName;
	/** �\���p�������ӂ肪�� */
	private String displayBuildingNameKana;
	/** ���ݒn�E�X�֔ԍ� */
	private String zip;
	/** ���ݒn�E�s���{��CD */
	private String prefCd;
	/** ���ݒn�E�s�撬��CD */
	private String addressCd;
	/** ���ݒn�E�s�撬���� */
	private String addressName;
	/** ���ݒn�E�����Ԓn */
	private String addressOther1;
	/** ���ݒn�E���������̑�  */
	private String addressOther2;
	/** �v�H�N�� */
	private Date compDate;
	/** �v�H�{ */
	private String compTenDays;
	/** ���K�� */
	private Integer totalFloors;
	/** �o�^�� */
	private Date insDate;
	/** �o�^�� */
	private String insUserId;
	/** �ŏI�X�V�� */
	private Date updDate;
	/** �ŏI�X�V�� */
	private String updUserId;
	
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
	 * �����ԍ����擾����B<br/>
	 * <br/>
	 * @return �����ԍ�
	 */
	public String getBuildingCd() {
		return buildingCd;
	}
	/**
	 * �����ԍ���ݒ肷��B<br/>
	 * <br/>
	 * @param buildingCd �����ԍ�
	 */
	public void setBuildingCd(String buildingCd) {
		this.buildingCd = buildingCd;
	}
	/**
	 * �������CD���擾����B<br/>
	 * <br/>
	 * @return �������CD
	 */
	public String getHousingKindCd() {
		return housingKindCd;
	}
	/**
	 * �������CD��ݒ肷��B<br/>
	 * <br/>
	 * @param housingKindCd �������CD
	 */
	public void setHousingKindCd(String housingKindCd) {
		this.housingKindCd = housingKindCd;
	}
	/**
	 * �����\��CD���擾����B<br/>
	 * <br/>
	 * @return �����\��CD
	 */
	public String getStructCd() {
		return structCd;
	}
	/**
	 * �����\��CD��ݒ肷��B<br/>
	 * <br/>
	 * @param structCd �����\��CD
	 */
	public void setStructCd(String structCd) {
		this.structCd = structCd;
	}
	/**
	 * �\���p���������擾����B<br/>
	 * <br/>
	 * @return �\���p������
	 */
	public String getDisplayBuildingName() {
		return displayBuildingName;
	}
	/**
	 * �\���p��������ݒ肷��B<br/>
	 * <br/>
	 * @param displayBuildingName �\���p������
	 */
	public void setDisplayBuildingName(String displayBuildingName) {
		this.displayBuildingName = displayBuildingName;
	}
	/**
	 * �\���p�������ӂ肪�Ȃ��擾����B<br/>
	 * <br/>
	 * @return �\���p�������ӂ肪��
	 */
	public String getDisplayBuildingNameKana() {
		return displayBuildingNameKana;
	}
	/**
	 * �\���p�������ӂ肪�Ȃ�ݒ肷��B<br/>
	 * <br/>
	 * @param displayBuildingNameKana �\���p�������ӂ肪��
	 */
	public void setDisplayBuildingNameKana(String displayBuildingNameKana) {
		this.displayBuildingNameKana = displayBuildingNameKana;
	}
	/**
	 * ���ݒn�E�X�֔ԍ����擾����B<br/>
	 * <br/>
	 * @return ���ݒn�E�X�֔ԍ�
	 */
	public String getZip() {
		return zip;
	}
	/**
	 * ���ݒn�E�X�֔ԍ���ݒ肷��B<br/>
	 * <br/>
	 * @param zip ���ݒn�E�X�֔ԍ�
	 */
	public void setZip(String zip) {
		this.zip = zip;
	}
	/**
	 * ���ݒn�E�s���{��CD���擾����B<br/>
	 * <br/>
	 * @return ���ݒn�E�s���{��CD
	 */
	public String getPrefCd() {
		return prefCd;
	}
	/**
	 * ���ݒn�E�s���{��CD��ݒ肷��B<br/>
	 * <br/>
	 * @param prefCd ���ݒn�E�s���{��CD
	 */
	public void setPrefCd(String prefCd) {
		this.prefCd = prefCd;
	}
	/**
	 * ���ݒn�E�s�撬��CD���擾����B<br/>
	 * <br/>
	 * @return ���ݒn�E�s�撬��CD
	 */
	public String getAddressCd() {
		return addressCd;
	}
	/**
	 * ���ݒn�E�s�撬��CD��ݒ肷��B<br/>
	 * <br/>
	 * @param addressCd ���ݒn�E�s�撬��CD
	 */
	public void setAddressCd(String addressCd) {
		this.addressCd = addressCd;
	}
	/**
	 * ���ݒn�E�s�撬�������擾����B<br/>
	 * <br/>
	 * @return ���ݒn�E�s�撬����
	 */
	public String getAddressName() {
		return addressName;
	}
	/**
	 * ���ݒn�E�s�撬������ݒ肷��B<br/>
	 * <br/>
	 * @param addressName ���ݒn�E�s�撬����
	 */
	public void setAddressName(String addressName) {
		this.addressName = addressName;
	}
	/**
	 * ���ݒn�E�����Ԓn���擾����B<br/>
	 * <br/>
	 * @return ���ݒn�E�����Ԓn
	 */
	public String getAddressOther1() {
		return addressOther1;
	}
	/**
	 * ���ݒn�E�����Ԓn��ݒ肷��B<br/>
	 * <br/>
	 * @param addressOther1 ���ݒn�E�����Ԓn
	 */
	public void setAddressOther1(String addressOther1) {
		this.addressOther1 = addressOther1;
	}
	/**
	 * ���ݒn�E���������̑����擾����B<br/>
	 * <br/>
	 * @return ���ݒn�E���������̑�
	 */
	public String getAddressOther2() {
		return addressOther2;
	}
	/**
	 * ���ݒn�E���������̑���ݒ肷��B<br/>
	 * <br/>
	 * @param addressOther2 ���ݒn�E���������̑�
	 */
	public void setAddressOther2(String addressOther2) {
		this.addressOther2 = addressOther2;
	}
	/**
	 * �v�H�N�����擾����B<br/>
	 * <br/>
	 * @return �v�H�N��
	 */
	public Date getCompDate() {
		return compDate;
	}
	/**
	 * �v�H�N����ݒ肷��B<br/>
	 * <br/>
	 * @param compDate �v�H�N��
	 */
	public void setCompDate(Date compDate) {
		this.compDate = compDate;
	}
	/**
	 * �v�H�{���擾����B<br/>
	 * <br/>
	 * @return �v�H�{
	 */
	public String getCompTenDays() {
		return compTenDays;
	}
	/**
	 * �v�H�{��ݒ肷��B<br/>
	 * <br/>
	 * @param compTenDays �v�H�{
	 */
	public void setCompTenDays(String compTenDays) {
		this.compTenDays = compTenDays;
	}
	/**
	 * ���K�����擾����B<br/>
	 * <br/>
	 * @return ���K��
	 */
	public Integer getTotalFloors() {
		return totalFloors;
	}
	/**
	 * ���K����ݒ肷��B<br/>
	 * <br/>
	 * @param totalFloors ���K��
	 */
	public void setTotalFloors(Integer totalFloors) {
		this.totalFloors = totalFloors;
	}

	/**
	 * �o�^�����擾����B<br/>
	 * <br/>
	 * @return �o�^��
	 */
	public Date getInsDate() {
		return insDate;
	}
	/**
	 * �o�^����ݒ肷��B<br/>
	 * <br/>
	 * @param insDate �o�^��
	 */
	public void setInsDate(Date insDate) {
		this.insDate = insDate;
	}
	/**
	 * �o�^�҂��擾����B<br/>
	 * <br/>
	 * @return �o�^��
	 */
	public String getInsUserId() {
		return insUserId;
	}
	/**
	 * �o�^�҂�ݒ肷��B<br/>
	 * <br/>
	 * @param insUserId �o�^��
	 */
	public void setInsUserId(String insUserId) {
		this.insUserId = insUserId;
	}
	/**
	 * �ŏI�X�V�����擾����B<br/>
	 * <br/>
	 * @return �ŏI�X�V��
	 */
	public Date getUpdDate() {
		return updDate;
	}
	/**
	 * �ŏI�X�V����ݒ肷��B<br/>
	 * <br/>
	 * @param updDate �ŏI�X�V��
	 */
	public void setUpdDate(Date updDate) {
		this.updDate = updDate;
	}
	/**
	 * �ŏI�X�V�҂��擾����B<br/>
	 * <br/>
	 * @return �ŏI�X�V��
	 */
	public String getUpdUserId() {
		return updUserId;
	}
	/**
	 * �ŏI�X�V�҂�ݒ肷��B<br/>
	 * <br/>
	 * @param updUserId �ŏI�X�V��
	 */
	public void setUpdUserId(String updUserId) {
		this.updUserId = updUserId;
	}
	
	
}
