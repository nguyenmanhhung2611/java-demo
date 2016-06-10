package jp.co.transcosmos.dm3.core.vo;

import java.util.Date;

/**
 * ���⍇���w�b�_.
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
public class InquiryHeader {

	/** ���⍇��ID */
	private String inquiryId;
	/** ���⍇���敪 */
	private String inquiryType;
	/** ���[�U�[ID */
	private String userId;
	/** ����(��) */
	private String lname;
	/** ����(��) */
	private String fname;
	/** �����E�J�i(��) */
	private String lnameKana;
	/** �����E�J�i(��) */
	private String fnameKana;
	/** ���[���A�h���X */
	private String email;
	/** �d�b�ԍ� */
	private String tel;
	/** ���⍇�����e */
	private String inquiryText;
	/** ���⍇������ */
	private Date inquiryDate;
	/** �Ή��X�e�[�^�X */
	private String answerStatus;
	/** �Ή����e */
	private String answerText;
	/** �J���� */
	private Date openDate;
	/** �J���� */
	private String openUserId;
	/** �Ή������� */
	private Date answerCompDate;
	/** �Ή������� */
	private String answerCompUserId;
	/** �o�^�� */
	private Date insDate;
	/** �o�^�� */
	private String insUserId;
	/** �ŏI�X�V�� */
	private Date updDate;
	/** �ŏI�X�V�� */
	private String updUserId;

	/**
	 * ���⍇��ID ���擾����B<br/>
	 * <br/>
	 *
	 * @return ���⍇��ID
	 */
	public String getInquiryId() {
		return inquiryId;
	}

	/**
	 * ���⍇��ID ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param inquiryId
	 */
	public void setInquiryId(String inquiryId) {
		this.inquiryId = inquiryId;
	}

	/**
	 * ���⍇���敪 ���擾����B<br/>
	 * <br/>
	 *
	 * @return ���⍇���敪
	 */
	public String getInquiryType() {
		return inquiryType;
	}

	/**
	 * ���⍇���敪 ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param inquiryType
	 */
	public void setInquiryType(String inquiryType) {
		this.inquiryType = inquiryType;
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
	 * ����(��) ���擾����B<br/>
	 * <br/>
	 *
	 * @return ����(��)
	 */
	public String getLname() {
		return lname;
	}

	/**
	 * ����(��) ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param lname
	 */
	public void setLname(String lname) {
		this.lname = lname;
	}

	/**
	 * ����(��) ���擾����B<br/>
	 * <br/>
	 *
	 * @return ����(��)
	 */
	public String getFname() {
		return fname;
	}

	/**
	 * ����(��) ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param fname
	 */
	public void setFname(String fname) {
		this.fname = fname;
	}

	/**
	 * �����E�J�i(��) ���擾����B<br/>
	 * <br/>
	 *
	 * @return �����E�J�i(��)
	 */
	public String getLnameKana() {
		return lnameKana;
	}

	/**
	 * �����E�J�i(��) ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param lnameKana
	 */
	public void setLnameKana(String lnameKana) {
		this.lnameKana = lnameKana;
	}

	/**
	 * �����E�J�i(��) ���擾����B<br/>
	 * <br/>
	 *
	 * @return �����E�J�i(��)
	 */
	public String getFnameKana() {
		return fnameKana;
	}

	/**
	 * �����E�J�i(��) ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param fnameKana
	 */
	public void setFnameKana(String fnameKana) {
		this.fnameKana = fnameKana;
	}

	/**
	 * ���[���A�h���X ���擾����B<br/>
	 * <br/>
	 *
	 * @return ���[���A�h���X
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * ���[���A�h���X ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * �d�b�ԍ� ���擾����B<br/>
	 * <br/>
	 *
	 * @return �d�b�ԍ�
	 */
	public String getTel() {
		return tel;
	}

	/**
	 * �d�b�ԍ� ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param tel
	 */
	public void setTel(String tel) {
		this.tel = tel;
	}

	/**
	 * ���⍇�����e ���擾����B<br/>
	 * <br/>
	 *
	 * @return ���⍇�����e
	 */
	public String getInquiryText() {
		return inquiryText;
	}

	/**
	 * ���⍇�����e ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param inquiryText
	 */
	public void setInquiryText(String inquiryText) {
		this.inquiryText = inquiryText;
	}

	/**
	 * ���⍇������ ���擾����B<br/>
	 * <br/>
	 *
	 * @return ���⍇������
	 */
	public Date getInquiryDate() {
		return inquiryDate;
	}

	/**
	 * ���⍇������ ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param inquiryDate
	 */
	public void setInquiryDate(Date inquiryDate) {
		this.inquiryDate = inquiryDate;
	}

	/**
	 * �Ή��X�e�[�^�X ���擾����B<br/>
	 * <br/>
	 *
	 * @return �Ή��X�e�[�^�X
	 */
	public String getAnswerStatus() {
		return answerStatus;
	}

	/**
	 * �Ή��X�e�[�^�X ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param answerStatus
	 */
	public void setAnswerStatus(String answerStatus) {
		this.answerStatus = answerStatus;
	}

	/**
	 * �Ή����e ���擾����B<br/>
	 * <br/>
	 *
	 * @return �Ή����e
	 */
	public String getAnswerText() {
		return answerText;
	}

	/**
	 * �Ή����e ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param answerText
	 */
	public void setAnswerText(String answerText) {
		this.answerText = answerText;
	}

	/**
	 * �J���� ���擾����B<br/>
	 * <br/>
	 *
	 * @return �J����
	 */
	public Date getOpenDate() {
		return openDate;
	}

	/**
	 * �J���� ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param openDate
	 */
	public void setOpenDate(Date openDate) {
		this.openDate = openDate;
	}

	/**
	 * �J���� ���擾����B<br/>
	 * <br/>
	 *
	 * @return �J����
	 */
	public String getOpenUserId() {
		return openUserId;
	}

	/**
	 * �J���� ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param openUserId
	 */
	public void setOpenUserId(String openUserId) {
		this.openUserId = openUserId;
	}

	/**
	 * �Ή������� ���擾����B<br/>
	 * <br/>
	 *
	 * @return �Ή�������
	 */
	public Date getAnswerCompDate() {
		return answerCompDate;
	}

	/**
	 * �Ή������� ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param answerCompDate
	 */
	public void setAnswerCompDate(Date answerCompDate) {
		this.answerCompDate = answerCompDate;
	}

	/**
	 * �Ή������� ���擾����B<br/>
	 * <br/>
	 *
	 * @return �Ή�������
	 */
	public String getAnswerCompUserId() {
		return answerCompUserId;
	}

	/**
	 * �Ή������� ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param answerCompUserId
	 */
	public void setAnswerCompUserId(String answerCompUserId) {
		this.answerCompUserId = answerCompUserId;
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
