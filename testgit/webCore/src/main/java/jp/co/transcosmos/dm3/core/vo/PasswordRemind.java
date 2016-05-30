package jp.co.transcosmos.dm3.core.vo;

import java.util.Date;

/**
 * �p�X���[�h�⍇��.
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
public class PasswordRemind {

	/** �₢���킹ID */
	private String remindId;
	/** ���[�U�[ID */
	private String userId;
	/** �ύX�m��t���O */
	private String commitFlg;
	/** �⍇���o�^�� */
	private Date insDate;
	/** �⍇���o�^�� */
	private String insUserId;
	/** �ύX�� */
	private Date updDate;
	/** �ύX�� */
	private String updUserId;

	/**
	 * �₢���킹ID ���擾����B<br/>
	 * <br/>
	 *
	 * @return �₢���킹ID
	 */
	public String getRemindId() {
		return remindId;
	}

	/**
	 * �₢���킹ID ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param remindId
	 */
	public void setRemindId(String remindId) {
		this.remindId = remindId;
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
	 * �ύX�m��t���O ���擾����B<br/>
	 * <br/>
	 *
	 * @return �ύX�m��t���O
	 */
	public String getCommitFlg() {
		return commitFlg;
	}

	/**
	 * �ύX�m��t���O ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param commitFlg
	 */
	public void setCommitFlg(String commitFlg) {
		this.commitFlg = commitFlg;
	}

	/**
	 * �⍇���o�^�� ���擾����B<br/>
	 * <br/>
	 *
	 * @return �⍇���o�^��
	 */
	public Date getInsDate() {
		return insDate;
	}

	/**
	 * �⍇���o�^�� ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param insDate
	 */
	public void setInsDate(Date insDate) {
		this.insDate = insDate;
	}

	/**
	 * �⍇���o�^�� ���擾����B<br/>
	 * <br/>
	 *
	 * @return �⍇���o�^��
	 */
	public String getInsUserId() {
		return insUserId;
	}

	/**
	 * �⍇���o�^�� ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param insUserId
	 */
	public void setInsUserId(String insUserId) {
		this.insUserId = insUserId;
	}

	/**
	 * �ύX�� ���擾����B<br/>
	 * <br/>
	 *
	 * @return �ύX��
	 */
	public Date getUpdDate() {
		return updDate;
	}

	/**
	 * �ύX�� ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param updDate
	 */
	public void setUpdDate(Date updDate) {
		this.updDate = updDate;
	}

	/**
	 * �ύX�� ���擾����B<br/>
	 * <br/>
	 *
	 * @return �ύX��
	 */
	public String getUpdUserId() {
		return updUserId;
	}

	/**
	 * �ύX�� ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param updUserId
	 */
	public void setUpdUserId(String updUserId) {
		this.updUserId = updUserId;
	}
}
