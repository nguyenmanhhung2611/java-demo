package jp.co.transcosmos.dm3.core.vo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * ���������ۑ����.
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
public class SaveSrchData {

	/** ��������ID */
	private String srchId;
	/** ������������ */
	private String srchName;
	/** ���[�U�[ID */
	private String userId;
	/** �������� */
	private String srchMethod;
	/** ����/���i�E�����l */
	private Long priceLower;
	/** ����/���i�E����l */
	private Long priceUpper;
	/** �Ǘ���t���O */
	private String upkeepFlg;
	/** ��L�ʐρE�����l */
	private BigDecimal personalAreaLower;
	/** ��L�ʐρE����l */
	private BigDecimal personalAreaUpper;
	/** �o�^�� */
	private Date insDate;
	/** �o�^�� */
	private String insUserId;
	/** �ŏI�X�V�� */
	private Date updDate;
	/** �ŏI�X�V�� */
	private String updUserId;

	/**
	 * ��������ID ���擾����B<br/>
	 * <br/>
	 *
	 * @return ��������ID
	 */
	public String getSrchId() {
		return srchId;
	}

	/**
	 * ��������ID ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param srchId
	 */
	public void setSrchId(String srchId) {
		this.srchId = srchId;
	}

	/**
	 * ������������ ���擾����B<br/>
	 * <br/>
	 *
	 * @return ������������
	 */
	public String getSrchName() {
		return srchName;
	}

	/**
	 * ������������ ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param srchName
	 */
	public void setSrchName(String srchName) {
		this.srchName = srchName;
	}

	/**
	 * ���[�U�[ID ���擾����B<br/>
	 * <br/>
	 *
	 * @return ���[�U�[ID
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * ���[�U�[ID ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param userId
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * �������� ���擾����B<br/>
	 * <br/>
	 *
	 * @return ��������
	 */
	public String getSrchMethod() {
		return srchMethod;
	}

	/**
	 * �������� ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param srchMethod
	 */
	public void setSrchMethod(String srchMethod) {
		this.srchMethod = srchMethod;
	}

	/**
	 * ����/���i�E�����l ���擾����B<br/>
	 * <br/>
	 *
	 * @return ����/���i�E�����l
	 */
	public Long getPriceLower() {
		return priceLower;
	}

	/**
	 * ����/���i�E�����l ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param priceLower
	 */
	public void setPriceLower(Long priceLower) {
		this.priceLower = priceLower;
	}

	/**
	 * ����/���i�E����l ���擾����B<br/>
	 * <br/>
	 *
	 * @return ����/���i�E����l
	 */
	public Long getPriceUpper() {
		return priceUpper;
	}

	/**
	 * ����/���i�E����l ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param priceUpper
	 */
	public void setPriceUpper(Long priceUpper) {
		this.priceUpper = priceUpper;
	}

	/**
	 * �Ǘ���t���O ���擾����B<br/>
	 * <br/>
	 *
	 * @return �Ǘ���t���O
	 */
	public String getUpkeepFlg() {
		return upkeepFlg;
	}

	/**
	 * �Ǘ���t���O ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param upkeepFlg
	 */
	public void setUpkeepFlg(String upkeepFlg) {
		this.upkeepFlg = upkeepFlg;
	}

	/**
	 * ��L�ʐρE�����l ���擾����B<br/>
	 * <br/>
	 *
	 * @return ��L�ʐρE�����l
	 */
	public BigDecimal getPersonalAreaLower() {
		return personalAreaLower;
	}

	/**
	 * ��L�ʐρE�����l ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param personalAreaLower
	 */
	public void setPersonalAreaLower(BigDecimal personalAreaLower) {
		this.personalAreaLower = personalAreaLower;
	}

	/**
	 * ��L�ʐρE����l ���擾����B<br/>
	 * <br/>
	 *
	 * @return ��L�ʐρE����l
	 */
	public BigDecimal getPersonalAreaUpper() {
		return personalAreaUpper;
	}

	/**
	 * ��L�ʐρE����l ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param personalAreaUpper
	 */
	public void setPersonalAreaUpper(BigDecimal personalAreaUpper) {
		this.personalAreaUpper = personalAreaUpper;
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
