package jp.co.transcosmos.dm3.core.vo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * �����ڍ׏��.
 * <p>
 * �N���X���A����сA�t�B�[���h���͂c�a�e�[�u�����A�񖼂Ƀ}�b�s���O�����B
 * <p>
 *
 * <pre>
 * �S����        �C����        �C�����e
 * ------------ ----------- -----------------------------------------------------
 * Trans        2015.03.10     �V�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 * �ʃJ�X�^�}�C�Y�Ōp�������\��������̂ŁA���ڃC���X�^���X�𐶐����Ȃ����B<br/>
 * �C���X�^���X���擾����ꍇ�́AValueObjectFactory ����擾���鎖�B<br/>
 *
 */
public class HousingDtlInfo {

	/** �V�X�e������CD */
	private String sysHousingCd;
	/** �p�r�n��CD */
	private String usedAreaCd;
	/** ����`�ԋ敪 */
	private String transactTypeDiv;
	/** �\���p�_����� */
	private String displayContractTerm;
	/** �y�n���� */
	private String landRight;
	/** �����\�����t���O */
	private String moveinTiming;
	/** �����\���� */
	private Date moveinTimingDay;
	/** �����\�����R�����g */
	private String moveinNote;
	/** �\���p�����\���� */
	private String displayMoveinTiming;
	/** �\���p���������� */
	private String displayMoveinProviso;
	/** �Ǘ��`�ԁE���� */
	private String upkeepType;
	/** �Ǘ���� */
	private String upkeepCorp;
	/** �X�V�� */
	private BigDecimal renewChrg;
	/** �X�V���P�� */
	private String renewChrgCrs;
	/** �X�V���� */
	private String renewChrgName;
	/** �X�V�萔�� */
	private BigDecimal renewDue;
	/** �X�V�萔���P�� */
	private String renewDueCrs;
	/** ����萔�� */
	private BigDecimal brokerageChrg;
	/** ����萔���P�� */
	private String brokerageChrgCrs;
	/** �������� */
	private BigDecimal changeKeyChrg;
	/** ���ۗL�� */
	private String insurExist;
	/** ���ۗ��� */
	private Long insurChrg;
	/** ���۔N�� */
	private Integer insurTerm;
	/** ���۔F�胉���N */
	private String insurLank;
	/** ����p */
	private Long otherChrg;
	/** �ړ��� */
	private String contactRoad;
	/** �ړ�����/���� */
	private String contactRoadDir;
	/** �������S */
	private String privateRoad;
	/** �o���R�j�[�ʐ� */
	private BigDecimal balconyArea;
	/** ���L���� */
	private String specialInstruction;
	/** �ڍ׃R�����g */
	private String dtlComment;

	/**
	 * �V�X�e������CD ���擾����B<br/>
	 * <br/>
	 *
	 * @return �V�X�e������CD
	 */
	public String getSysHousingCd() {
		return sysHousingCd;
	}

	/**
	 * �V�X�e������CD ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param sysHousingCd
	 */
	public void setSysHousingCd(String sysHousingCd) {
		this.sysHousingCd = sysHousingCd;
	}

	/**
	 * �p�r�n��CD ���擾����B<br/>
	 * <br/>
	 *
	 * @return �p�r�n��CD
	 */
	public String getUsedAreaCd() {
		return usedAreaCd;
	}

	/**
	 * �p�r�n��CD ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param usedAreaCd
	 */
	public void setUsedAreaCd(String usedAreaCd) {
		this.usedAreaCd = usedAreaCd;
	}

	/**
	 * ����`�ԋ敪 ���擾����B<br/>
	 * <br/>
	 *
	 * @return ����`�ԋ敪
	 */
	public String getTransactTypeDiv() {
		return transactTypeDiv;
	}

	/**
	 * ����`�ԋ敪 ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param transactTypeDiv
	 */
	public void setTransactTypeDiv(String transactTypeDiv) {
		this.transactTypeDiv = transactTypeDiv;
	}

	/**
	 * �\���p�_����� ���擾����B<br/>
	 * <br/>
	 *
	 * @return �\���p�_�����
	 */
	public String getDisplayContractTerm() {
		return displayContractTerm;
	}

	/**
	 * �\���p�_����� ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param displayContractTerm
	 */
	public void setDisplayContractTerm(String displayContractTerm) {
		this.displayContractTerm = displayContractTerm;
	}

	/**
	 * �y�n���� ���擾����B<br/>
	 * <br/>
	 *
	 * @return �y�n����
	 */
	public String getLandRight() {
		return landRight;
	}

	/**
	 * �y�n���� ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param landRight
	 */
	public void setLandRight(String landRight) {
		this.landRight = landRight;
	}

	/**
	 * �����\�����t���O ���擾����B<br/>
	 * <br/>
	 *
	 * @return �����\�����t���O
	 */
	public String getMoveinTiming() {
		return moveinTiming;
	}

	/**
	 * �����\�����t���O ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param moveinTiming
	 */
	public void setMoveinTiming(String moveinTiming) {
		this.moveinTiming = moveinTiming;
	}

	/**
	 * �����\���� ���擾����B<br/>
	 * <br/>
	 *
	 * @return �����\����
	 */
	public Date getMoveinTimingDay() {
		return moveinTimingDay;
	}

	/**
	 * �����\���� ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param moveinTimingDay
	 */
	public void setMoveinTimingDay(Date moveinTimingDay) {
		this.moveinTimingDay = moveinTimingDay;
	}

	/**
	 * �����\�����R�����g ���擾����B<br/>
	 * <br/>
	 *
	 * @return �����\�����R�����g
	 */
	public String getMoveinNote() {
		return moveinNote;
	}

	/**
	 * �����\�����R�����g ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param moveinNote
	 */
	public void setMoveinNote(String moveinNote) {
		this.moveinNote = moveinNote;
	}

	/**
	 * �\���p�����\���� ���擾����B<br/>
	 * <br/>
	 *
	 * @return �\���p�����\����
	 */
	public String getDisplayMoveinTiming() {
		return displayMoveinTiming;
	}

	/**
	 * �\���p�����\���� ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param displayMoveinTiming
	 */
	public void setDisplayMoveinTiming(String displayMoveinTiming) {
		this.displayMoveinTiming = displayMoveinTiming;
	}

	/**
	 * �\���p���������� ���擾����B<br/>
	 * <br/>
	 *
	 * @return �\���p����������
	 */
	public String getDisplayMoveinProviso() {
		return displayMoveinProviso;
	}

	/**
	 * �\���p���������� ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param displayMoveinProviso
	 */
	public void setDisplayMoveinProviso(String displayMoveinProviso) {
		this.displayMoveinProviso = displayMoveinProviso;
	}

	/**
	 * �Ǘ��`�ԁE���� ���擾����B<br/>
	 * <br/>
	 *
	 * @return �Ǘ��`�ԁE����
	 */
	public String getUpkeepType() {
		return upkeepType;
	}

	/**
	 * �Ǘ��`�ԁE���� ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param upkeepType
	 */
	public void setUpkeepType(String upkeepType) {
		this.upkeepType = upkeepType;
	}

	/**
	 * �Ǘ���� ���擾����B<br/>
	 * <br/>
	 *
	 * @return �Ǘ����
	 */
	public String getUpkeepCorp() {
		return upkeepCorp;
	}

	/**
	 * �Ǘ���� ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param upkeepCorp
	 */
	public void setUpkeepCorp(String upkeepCorp) {
		this.upkeepCorp = upkeepCorp;
	}

	/**
	 * �X�V�� ���擾����B<br/>
	 * <br/>
	 *
	 * @return �X�V��
	 */
	public BigDecimal getRenewChrg() {
		return renewChrg;
	}

	/**
	 * �X�V�� ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param renewChrg
	 */
	public void setRenewChrg(BigDecimal renewChrg) {
		this.renewChrg = renewChrg;
	}

	/**
	 * �X�V���P�� ���擾����B<br/>
	 * <br/>
	 *
	 * @return �X�V���P��
	 */
	public String getRenewChrgCrs() {
		return renewChrgCrs;
	}

	/**
	 * �X�V���P�� ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param renewChrgCrs
	 */
	public void setRenewChrgCrs(String renewChrgCrs) {
		this.renewChrgCrs = renewChrgCrs;
	}

	/**
	 * �X�V���� ���擾����B<br/>
	 * <br/>
	 *
	 * @return �X�V����
	 */
	public String getRenewChrgName() {
		return renewChrgName;
	}

	/**
	 * �X�V���� ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param renewChrgName
	 */
	public void setRenewChrgName(String renewChrgName) {
		this.renewChrgName = renewChrgName;
	}

	/**
	 * �X�V�萔�� ���擾����B<br/>
	 * <br/>
	 *
	 * @return �X�V�萔��
	 */
	public BigDecimal getRenewDue() {
		return renewDue;
	}

	/**
	 * �X�V�萔�� ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param renewDue
	 */
	public void setRenewDue(BigDecimal renewDue) {
		this.renewDue = renewDue;
	}

	/**
	 * �X�V�萔���P�� ���擾����B<br/>
	 * <br/>
	 *
	 * @return �X�V�萔���P��
	 */
	public String getRenewDueCrs() {
		return renewDueCrs;
	}

	/**
	 * �X�V�萔���P�� ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param renewDueCrs
	 */
	public void setRenewDueCrs(String renewDueCrs) {
		this.renewDueCrs = renewDueCrs;
	}

	/**
	 * ����萔�� ���擾����B<br/>
	 * <br/>
	 *
	 * @return ����萔��
	 */
	public BigDecimal getBrokerageChrg() {
		return brokerageChrg;
	}

	/**
	 * ����萔�� ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param brokerageChrg
	 */
	public void setBrokerageChrg(BigDecimal brokerageChrg) {
		this.brokerageChrg = brokerageChrg;
	}

	/**
	 * ����萔���P�� ���擾����B<br/>
	 * <br/>
	 *
	 * @return ����萔���P��
	 */
	public String getBrokerageChrgCrs() {
		return brokerageChrgCrs;
	}

	/**
	 * ����萔���P�� ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param brokerageChrgCrs
	 */
	public void setBrokerageChrgCrs(String brokerageChrgCrs) {
		this.brokerageChrgCrs = brokerageChrgCrs;
	}

	/**
	 * �������� ���擾����B<br/>
	 * <br/>
	 *
	 * @return ��������
	 */
	public BigDecimal getChangeKeyChrg() {
		return changeKeyChrg;
	}

	/**
	 * �������� ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param changeKeyChrg
	 */
	public void setChangeKeyChrg(BigDecimal changeKeyChrg) {
		this.changeKeyChrg = changeKeyChrg;
	}

	/**
	 * ���ۗL�� ���擾����B<br/>
	 * <br/>
	 *
	 * @return ���ۗL��
	 */
	public String getInsurExist() {
		return insurExist;
	}

	/**
	 * ���ۗL�� ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param insurExist
	 */
	public void setInsurExist(String insurExist) {
		this.insurExist = insurExist;
	}

	/**
	 * ���ۗ��� ���擾����B<br/>
	 * <br/>
	 *
	 * @return ���ۗ���
	 */
	public Long getInsurChrg() {
		return insurChrg;
	}

	/**
	 * ���ۗ��� ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param insurChrg
	 */
	public void setInsurChrg(Long insurChrg) {
		this.insurChrg = insurChrg;
	}

	/**
	 * ���۔N�� ���擾����B<br/>
	 * <br/>
	 *
	 * @return ���۔N��
	 */
	public Integer getInsurTerm() {
		return insurTerm;
	}

	/**
	 * ���۔N�� ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param insurTerm
	 */
	public void setInsurTerm(Integer insurTerm) {
		this.insurTerm = insurTerm;
	}

	/**
	 * ���۔F�胉���N ���擾����B<br/>
	 * <br/>
	 *
	 * @return ���۔F�胉���N
	 */
	public String getInsurLank() {
		return insurLank;
	}

	/**
	 * ���۔F�胉���N ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param insurLank
	 */
	public void setInsurLank(String insurLank) {
		this.insurLank = insurLank;
	}

	/**
	 * ����p ���擾����B<br/>
	 * <br/>
	 *
	 * @return ����p
	 */
	public Long getOtherChrg() {
		return otherChrg;
	}

	/**
	 * ����p ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param otherChrg
	 */
	public void setOtherChrg(Long otherChrg) {
		this.otherChrg = otherChrg;
	}

	/**
	 * �ړ��� ���擾����B<br/>
	 * <br/>
	 *
	 * @return �ړ���
	 */
	public String getContactRoad() {
		return contactRoad;
	}

	/**
	 * �ړ��� ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param contactRoad
	 */
	public void setContactRoad(String contactRoad) {
		this.contactRoad = contactRoad;
	}

	/**
	 * �ړ�����/���� ���擾����B<br/>
	 * <br/>
	 *
	 * @return �ړ�����/����
	 */
	public String getContactRoadDir() {
		return contactRoadDir;
	}

	/**
	 * �ړ�����/���� ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param contactRoadDir
	 */
	public void setContactRoadDir(String contactRoadDir) {
		this.contactRoadDir = contactRoadDir;
	}

	/**
	 * �������S ���擾����B<br/>
	 * <br/>
	 *
	 * @return �������S
	 */
	public String getPrivateRoad() {
		return privateRoad;
	}

	/**
	 * �������S ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param privateRoad
	 */
	public void setPrivateRoad(String privateRoad) {
		this.privateRoad = privateRoad;
	}

	/**
	 * �o���R�j�[�ʐ� ���擾����B<br/>
	 * <br/>
	 *
	 * @return �o���R�j�[�ʐ�
	 */
	public BigDecimal getBalconyArea() {
		return balconyArea;
	}

	/**
	 * �o���R�j�[�ʐ� ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param balconyArea
	 */
	public void setBalconyArea(BigDecimal balconyArea) {
		this.balconyArea = balconyArea;
	}

	/**
	 * ���L���� ���擾����B<br/>
	 * <br/>
	 *
	 * @return ���L����
	 */
	public String getSpecialInstruction() {
		return specialInstruction;
	}

	/**
	 * ���L���� ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param specialInstruction
	 */
	public void setSpecialInstruction(String specialInstruction) {
		this.specialInstruction = specialInstruction;
	}

	/**
	 * �ڍ׃R�����g ���擾����B<br/>
	 * <br/>
	 *
	 * @return �ڍ׃R�����g
	 */
	public String getDtlComment() {
		return dtlComment;
	}

	/**
	 * �ڍ׃R�����g ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param dtlComment
	 */
	public void setDtlComment(String dtlComment) {
		this.dtlComment = dtlComment;
	}
}
