package jp.co.transcosmos.dm3.core.vo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * �������N�G�X�g���.
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
public class HousingRequestInfo {

	/** �������N�G�X�gID */
	private String housingRequestId;
	/** ���[�U�[ID */
	private String userId;
	/** ���N�G�X�g���� */
	private String requestName;
	/** ����/���i�E�����l */
	private Long priceLower;
	/** ����/���i�E����l */
	private Long priceUpper;
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
	 * �������N�G�X�gID ���擾����B<br/>
	 * <br/>
	 *
	 * @return �������N�G�X�gID
	 */
	public String getHousingRequestId() {
		return housingRequestId;
	}

	/**
	 * �������N�G�X�gID ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param housingRequestId
	 */
	public void setHousingRequestId(String housingRequestId) {
		this.housingRequestId = housingRequestId;
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
	 * ���N�G�X�g���� ���擾����B<br/>
	 * <br/>
	 *
	 * @return ���N�G�X�g����
	 */
	public String getRequestName() {
		return requestName;
	}

	/**
	 * ���N�G�X�g���� ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param requestName
	 */
	public void setRequestName(String requestName) {
		this.requestName = requestName;
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
