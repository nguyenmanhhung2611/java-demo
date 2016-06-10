package jp.co.transcosmos.dm3.core.vo;

import java.util.Date;

/**
 * �S����Ѓ}�X�^�N���X.
 * <p>
 * �N���X���A����сA�t�B�[���h���͂c�a�e�[�u�����A�񖼂Ƀ}�b�s���O�����B
 * <p>
 * <pre>
 * �S����		�C����		�C�����e
 * ------------ ----------- -----------------------------------------------------
 * �ד��@��		2006.12.29	�V�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 * �ʃJ�X�^�}�C�Y�Ōp�������\��������̂ŁA���ڃC���X�^���X�𐶐����Ȃ����B<br/>
 * �C���X�^���X���擾����ꍇ�́AValueObjectFactory ����擾���鎖�B<br/>
 * 
 */
public class RrMst {
	/** �S�����CD */
	private String rrCd;
	
	/** �S����Ж� */
	private String rrName;
	
	/** �\���� */
	private Integer sortOrder;
	
	/** �o�^�� */
	private Date insDate;
	
	/** �o�^�� */
	private String insUserId;
	
	/** �ŏI�X�V�� */
	private Date updDate;
	
	/** �ŏI�X�V�� */
	private String updUserId;
	
	
	
	/**
	 * �S�����CD ���擾����B<br/>
	 * <br/>
	 * @return �S�����CD
	 */
	public String getRrCd() {
		return this.rrCd;
	}

	/**
	 * �S�����CD ��ݒ肷��B<br/>
	 * <br/>
	 * @param rrCd �S�����CD
	 */
	public void setRrCd(String rrCd) {
		this.rrCd = rrCd;
	}

	/**
	 * �S����Ж����擾����B<br/>
	 * <br/>
	 * @return �S����Ж�
	 */
	public String getRrName() {
		return this.rrName;
	}
	
	/**
	 * �S����Ж���ݒ肷��B<br/>
	 * <br/>
	 * @param rrName �S����Ж�
	 */
	public void setRrName(String rrName) {
		this.rrName = rrName;
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
