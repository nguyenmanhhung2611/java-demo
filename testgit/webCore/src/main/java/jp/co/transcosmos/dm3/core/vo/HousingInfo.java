package jp.co.transcosmos.dm3.core.vo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * ������{���.
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
public class HousingInfo {

	/** �V�X�e������CD */
	private String sysHousingCd;
	/** �����ԍ� */
	private String housingCd;
	/** �\���p������ */
	private String displayHousingName;
	/** �\���p�������ӂ肪�� */
	private String displayHousingNameKana;
	/** �����ԍ� */
	private String roomNo;
	/** �V�X�e������CD */
	private String sysBuildingCd;
	/** ����/���i */
	private Long price;
	/** �Ǘ��� */
	private Long upkeep;
	/** ���v�� */
	private Long commonAreaFee;
	/** �C�U�ϗ��� */
	private Long menteFee;
	/** �~�� */
	private BigDecimal secDeposit;
	/** �~���P�� */
	private String secDepositCrs;
	/** �ۏ؋� */
	private BigDecimal bondChrg;
	/** �ۏ؋��P�� */
	private String bondChrgCrs;
	/** �~������敪 */
	private String depositDiv;
	/** �~������z */
	private BigDecimal deposit;
	/** �~������P�� */
	private String depositCrs;
	/** �Ԏ�CD */
	private String layoutCd;
	/** �Ԏ�ڍ׃R�����g */
	private String layoutComment;
	/** �����̊K�� */
	private Integer floorNo;
	/** �����̊K���R�����g */
	private String floorNoNote;
	/** �y�n�ʐ� */
	private BigDecimal landArea;
	/** �y�n�ʐ�_�⑫ */
	private String landAreaMemo;
	/** ��L�ʐ� */
	private BigDecimal personalArea;
	/** ��L�ʐ�_�⑫ */
	private String personalAreaMemo;
	/** ������ԃt���O */
	private String moveinFlg;
	/** ���ԏ�̏� */
	private String parkingSituation;
	/** ���ԏ��̗L�� */
	private String parkingEmpExist;
	/** �\���p���ԏ��� */
	private String displayParkingInfo;
	/** ���̌��� */
	private String windowDirection;
	/** �A�C�R����� */
	private String iconCd;
	/** ��{���R�����g */
	private String basicComment;
	/** �o�^�� */
	private Date insDate;
	/** �o�^�� */
	private String insUserId;
	/** �ŏI�X�V�� */
	private Date updDate;
	/** �ŏI�X�V�� */
	private String updUserId;

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
	 * �����ԍ� ���擾����B<br/>
	 * <br/>
	 *
	 * @return �����ԍ�
	 */
	public String getHousingCd() {
		return housingCd;
	}

	/**
	 * �����ԍ� ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param housingCd
	 */
	public void setHousingCd(String housingCd) {
		this.housingCd = housingCd;
	}

	/**
	 * �\���p������ ���擾����B<br/>
	 * <br/>
	 *
	 * @return �\���p������
	 */
	public String getDisplayHousingName() {
		return displayHousingName;
	}

	/**
	 * �\���p������ ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param displayHousingName
	 */
	public void setDisplayHousingName(String displayHousingName) {
		this.displayHousingName = displayHousingName;
	}

	/**
	 * �\���p�������ӂ肪�� ���擾����B<br/>
	 * <br/>
	 *
	 * @return �\���p�������ӂ肪��
	 */
	public String getDisplayHousingNameKana() {
		return displayHousingNameKana;
	}

	/**
	 * �\���p�������ӂ肪�� ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param displayHousingNameKana
	 */
	public void setDisplayHousingNameKana(String displayHousingNameKana) {
		this.displayHousingNameKana = displayHousingNameKana;
	}

	/**
	 * �����ԍ� ���擾����B<br/>
	 * <br/>
	 *
	 * @return �����ԍ�
	 */
	public String getRoomNo() {
		return roomNo;
	}

	/**
	 * �����ԍ� ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param roomNo
	 */
	public void setRoomNo(String roomNo) {
		this.roomNo = roomNo;
	}

	/**
	 * �V�X�e������CD ���擾����B<br/>
	 * <br/>
	 *
	 * @return �V�X�e������CD
	 */
	public String getSysBuildingCd() {
		return sysBuildingCd;
	}

	/**
	 * �V�X�e������CD ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param sysBuildingCd
	 */
	public void setSysBuildingCd(String sysBuildingCd) {
		this.sysBuildingCd = sysBuildingCd;
	}

	/**
	 * ����/���i ���擾����B<br/>
	 * <br/>
	 *
	 * @return ����/���i
	 */
	public Long getPrice() {
		return price;
	}

	/**
	 * ����/���i ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param price
	 */
	public void setPrice(Long price) {
		this.price = price;
	}

	/**
	 * �Ǘ��� ���擾����B<br/>
	 * <br/>
	 *
	 * @return �Ǘ���
	 */
	public Long getUpkeep() {
		return upkeep;
	}

	/**
	 * �Ǘ��� ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param upkeep
	 */
	public void setUpkeep(Long upkeep) {
		this.upkeep = upkeep;
	}

	/**
	 * ���v�� ���擾����B<br/>
	 * <br/>
	 *
	 * @return ���v��
	 */
	public Long getCommonAreaFee() {
		return commonAreaFee;
	}

	/**
	 * ���v�� ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param commonAreaFee
	 */
	public void setCommonAreaFee(Long commonAreaFee) {
		this.commonAreaFee = commonAreaFee;
	}

	/**
	 * �C�U�ϗ��� ���擾����B<br/>
	 * <br/>
	 *
	 * @return �C�U�ϗ���
	 */
	public Long getMenteFee() {
		return menteFee;
	}

	/**
	 * �C�U�ϗ��� ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param menteFee
	 */
	public void setMenteFee(Long menteFee) {
		this.menteFee = menteFee;
	}

	/**
	 * �~�� ���擾����B<br/>
	 * <br/>
	 *
	 * @return �~��
	 */
	public BigDecimal getSecDeposit() {
		return secDeposit;
	}

	/**
	 * �~�� ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param secDeposit
	 */
	public void setSecDeposit(BigDecimal secDeposit) {
		this.secDeposit = secDeposit;
	}

	/**
	 * �~���P�� ���擾����B<br/>
	 * <br/>
	 *
	 * @return �~���P��
	 */
	public String getSecDepositCrs() {
		return secDepositCrs;
	}

	/**
	 * �~���P�� ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param secDepositCrs
	 */
	public void setSecDepositCrs(String secDepositCrs) {
		this.secDepositCrs = secDepositCrs;
	}

	/**
	 * �ۏ؋� ���擾����B<br/>
	 * <br/>
	 *
	 * @return �ۏ؋�
	 */
	public BigDecimal getBondChrg() {
		return bondChrg;
	}

	/**
	 * �ۏ؋� ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param bondChrg
	 */
	public void setBondChrg(BigDecimal bondChrg) {
		this.bondChrg = bondChrg;
	}

	/**
	 * �ۏ؋��P�� ���擾����B<br/>
	 * <br/>
	 *
	 * @return �ۏ؋��P��
	 */
	public String getBondChrgCrs() {
		return bondChrgCrs;
	}

	/**
	 * �ۏ؋��P�� ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param bondChrgCrs
	 */
	public void setBondChrgCrs(String bondChrgCrs) {
		this.bondChrgCrs = bondChrgCrs;
	}

	/**
	 * �~������敪 ���擾����B<br/>
	 * <br/>
	 *
	 * @return �~������敪
	 */
	public String getDepositDiv() {
		return depositDiv;
	}

	/**
	 * �~������敪 ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param depositDiv
	 */
	public void setDepositDiv(String depositDiv) {
		this.depositDiv = depositDiv;
	}

	/**
	 * �~������z ���擾����B<br/>
	 * <br/>
	 *
	 * @return �~������z
	 */
	public BigDecimal getDeposit() {
		return deposit;
	}

	/**
	 * �~������z ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param deposit
	 */
	public void setDeposit(BigDecimal deposit) {
		this.deposit = deposit;
	}

	/**
	 * �~������P�� ���擾����B<br/>
	 * <br/>
	 *
	 * @return �~������P��
	 */
	public String getDepositCrs() {
		return depositCrs;
	}

	/**
	 * �~������P�� ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param depositCrs
	 */
	public void setDepositCrs(String depositCrs) {
		this.depositCrs = depositCrs;
	}

	/**
	 * �Ԏ�CD ���擾����B<br/>
	 * <br/>
	 *
	 * @return �Ԏ�CD
	 */
	public String getLayoutCd() {
		return layoutCd;
	}

	/**
	 * �Ԏ�CD ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param layoutCd
	 */
	public void setLayoutCd(String layoutCd) {
		this.layoutCd = layoutCd;
	}

	/**
	 * �Ԏ�ڍ׃R�����g ���擾����B<br/>
	 * <br/>
	 *
	 * @return �Ԏ�ڍ׃R�����g
	 */
	public String getLayoutComment() {
		return layoutComment;
	}

	/**
	 * �Ԏ�ڍ׃R�����g ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param layoutComment
	 */
	public void setLayoutComment(String layoutComment) {
		this.layoutComment = layoutComment;
	}

	/**
	 * �����̊K�� ���擾����B<br/>
	 * <br/>
	 *
	 * @return �����̊K��
	 */
	public Integer getFloorNo() {
		return floorNo;
	}

	/**
	 * �����̊K�� ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param floorNo
	 */
	public void setFloorNo(Integer floorNo) {
		this.floorNo = floorNo;
	}

	/**
	 * �����̊K���R�����g ���擾����B<br/>
	 * <br/>
	 *
	 * @return �����̊K���R�����g
	 */
	public String getFloorNoNote() {
		return floorNoNote;
	}

	/**
	 * �����̊K���R�����g ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param floorNoNote
	 */
	public void setFloorNoNote(String floorNoNote) {
		this.floorNoNote = floorNoNote;
	}

	/**
	 * �y�n�ʐ� ���擾����B<br/>
	 * <br/>
	 *
	 * @return �y�n�ʐ�
	 */
	public BigDecimal getLandArea() {
		return landArea;
	}

	/**
	 * �y�n�ʐ� ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param landArea
	 */
	public void setLandArea(BigDecimal landArea) {
		this.landArea = landArea;
	}

	/**
	 * �y�n�ʐ�_�⑫ ���擾����B<br/>
	 * <br/>
	 *
	 * @return �y�n�ʐ�_�⑫
	 */
	public String getLandAreaMemo() {
		return landAreaMemo;
	}

	/**
	 * �y�n�ʐ�_�⑫ ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param landAreaMemo
	 */
	public void setLandAreaMemo(String landAreaMemo) {
		this.landAreaMemo = landAreaMemo;
	}

	/**
	 * ��L�ʐ� ���擾����B<br/>
	 * <br/>
	 *
	 * @return ��L�ʐ�
	 */
	public BigDecimal getPersonalArea() {
		return personalArea;
	}

	/**
	 * ��L�ʐ� ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param personalArea
	 */
	public void setPersonalArea(BigDecimal personalArea) {
		this.personalArea = personalArea;
	}

	/**
	 * ��L�ʐ�_�⑫ ���擾����B<br/>
	 * <br/>
	 *
	 * @return ��L�ʐ�_�⑫
	 */
	public String getPersonalAreaMemo() {
		return personalAreaMemo;
	}

	/**
	 * ��L�ʐ�_�⑫ ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param personalAreaMemo
	 */
	public void setPersonalAreaMemo(String personalAreaMemo) {
		this.personalAreaMemo = personalAreaMemo;
	}

	/**
	 * ������ԃt���O ���擾����B<br/>
	 * <br/>
	 *
	 * @return ������ԃt���O
	 */
	public String getMoveinFlg() {
		return moveinFlg;
	}

	/**
	 * ������ԃt���O ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param moveinFlg
	 */
	public void setMoveinFlg(String moveinFlg) {
		this.moveinFlg = moveinFlg;
	}

	/**
	 * ���ԏ�̏� ���擾����B<br/>
	 * <br/>
	 *
	 * @return ���ԏ�̏�
	 */
	public String getParkingSituation() {
		return parkingSituation;
	}

	/**
	 * ���ԏ�̏� ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param parkingSituation
	 */
	public void setParkingSituation(String parkingSituation) {
		this.parkingSituation = parkingSituation;
	}

	/**
	 * ���ԏ��̗L�� ���擾����B<br/>
	 * <br/>
	 *
	 * @return ���ԏ��̗L��
	 */
	public String getParkingEmpExist() {
		return parkingEmpExist;
	}

	/**
	 * ���ԏ��̗L�� ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param parkingEmpExist
	 */
	public void setParkingEmpExist(String parkingEmpExist) {
		this.parkingEmpExist = parkingEmpExist;
	}

	/**
	 * �\���p���ԏ��� ���擾����B<br/>
	 * <br/>
	 *
	 * @return �\���p���ԏ���
	 */
	public String getDisplayParkingInfo() {
		return displayParkingInfo;
	}

	/**
	 * �\���p���ԏ��� ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param displayParkingInfo
	 */
	public void setDisplayParkingInfo(String displayParkingInfo) {
		this.displayParkingInfo = displayParkingInfo;
	}

	/**
	 * ���̌��� ���擾����B<br/>
	 * <br/>
	 *
	 * @return ���̌���
	 */
	public String getWindowDirection() {
		return windowDirection;
	}

	/**
	 * ���̌��� ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param windowDirection
	 */
	public void setWindowDirection(String windowDirection) {
		this.windowDirection = windowDirection;
	}

	/**
	 * �A�C�R����� ���擾����B<br/>
	 * <br/>
	 *
	 * @return �A�C�R�����
	 */
	public String getIconCd() {
		return iconCd;
	}

	/**
	 * �A�C�R����� ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param iconCd
	 */
	public void setIconCd(String iconCd) {
		this.iconCd = iconCd;
	}

	/**
	 * ��{���R�����g ���擾����B<br/>
	 * <br/>
	 *
	 * @return ��{���R�����g
	 */
	public String getBasicComment() {
		return basicComment;
	}

	/**
	 * ��{���R�����g ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param basicComment
	 */
	public void setBasicComment(String basicComment) {
		this.basicComment = basicComment;
	}

	/**
	 * �o�^�� ���擾����B<br/>
	 * <br/>
	 *
	 * @return �o�^��
	 */
	public Date getInsDate() {
		return insDate;
	}

	/**
	 * �o�^�� ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param insDate
	 */
	public void setInsDate(Date insDate) {
		this.insDate = insDate;
	}

	/**
	 * �o�^�� ���擾����B<br/>
	 * <br/>
	 *
	 * @return �o�^��
	 */
	public String getInsUserId() {
		return insUserId;
	}

	/**
	 * �o�^�� ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param insUserId
	 */
	public void setInsUserId(String insUserId) {
		this.insUserId = insUserId;
	}

	/**
	 * �ŏI�X�V�� ���擾����B<br/>
	 * <br/>
	 *
	 * @return �ŏI�X�V��
	 */
	public Date getUpdDate() {
		return updDate;
	}

	/**
	 * �ŏI�X�V�� ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param updDate
	 */
	public void setUpdDate(Date updDate) {
		this.updDate = updDate;
	}

	/**
	 * �ŏI�X�V�� ���擾����B<br/>
	 * <br/>
	 *
	 * @return �ŏI�X�V��
	 */
	public String getUpdUserId() {
		return updUserId;
	}

	/**
	 * �ŏI�X�V�� ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param updUserId
	 */
	public void setUpdUserId(String updUserId) {
		this.updUserId = updUserId;
	}
}
