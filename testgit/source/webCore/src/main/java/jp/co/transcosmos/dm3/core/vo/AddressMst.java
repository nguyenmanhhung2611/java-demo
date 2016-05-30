package jp.co.transcosmos.dm3.core.vo;

import java.util.Date;

/**
 * �s�撬���}�X�^�N���X.
 * <p>
 * �N���X���A����сA�t�B�[���h���͂c�a�e�[�u�����A�񖼂Ƀ}�b�s���O�����B
 * <p>
 * <pre>
 * �S����		�C����		�C�����e
 * ------------ ----------- -----------------------------------------------------
 * I.Shu		2006.12.19	�V�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 * �ʃJ�X�^�}�C�Y�Ōp�������\��������̂ŁA���ڃC���X�^���X�𐶐����Ȃ����B<br/>
 * �C���X�^���X���擾����ꍇ�́AValueObjectFactory ����擾���鎖�B<br/>
 * 
 */
public class AddressMst {

	/** �s���{��CD */
	private String prefCd;
	/** �s�撬��CD */
	private String addressCd;
	/** �s�撬���� */
	private String addressName;
	/** �G���A������\���t���O �i0:�ʏ�A1:�G���A�����Ŕ�\���j */
	private String areaNotDsp;
	/** �o�^�� */
	private Date insDate;
	/** �o�^�� */
	private String insUserId;
	/** �ŏI�X�V�� */
	private Date updDate;
	/** �ŏI�X�V�� */
	private String updUserId;
	
	/**
	 * �s���{��CD ���擾����B<br/>
	 * <br/>
	 * @return �s���{��CD
	 */
	public String getPrefCd() {
		return prefCd;
	}
	
	/**
	 * �s���{��CD ��ݒ肷��B<br/>
	 * <br/>
	 * @param prefCd
	 */
	public void setPrefCd(String prefCd) {
		this.prefCd = prefCd;
	}
	/**
	 * �s�撬��CD���擾����B<br/>
	 * <br/>
	 * @return �s�撬��CD
	 */
	public String getAddressCd() {
		return addressCd;
	}
	/**
	 * �s�撬��CD��ݒ肷��B<br/>
	 * <br/>
	 * @param addressCd �s�撬��CD
	 */
	public void setAddressCd(String addressCd) {
		this.addressCd = addressCd;
	}
	/**
	 * �s�撬�������擾����B<br/>
	 * <br/>
	 * @return �s�撬����
	 */
	public String getAddressName() {
		return addressName;
	}
	/**
	 * �s�撬������ݒ肷��B<br/>
	 * <br/>
	 * @param addressName �s�撬����
	 */
	public void setAddressName(String addressName) {
		this.addressName = addressName;
	}
	
	/**
	 * �G���A������\���t���O���擾����B<br/>
	 * <br/>
	 * @return �G���A������\���t���O�i0:�ʏ�A1:�G���A�����Ŕ�\���j
	 */
	public String getAreaNotDsp() {
		return areaNotDsp;
	}

	/**
	 * �G���A������\���t���O��ݒ肷��B<br/>
	 * <br/>
	 * @param �G���A������\���t���O�i0:�ʏ�A1:�G���A�����Ŕ�\���j
	 */
	public void setAreaNotDsp(String areaNotDsp) {
		this.areaNotDsp = areaNotDsp;
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
