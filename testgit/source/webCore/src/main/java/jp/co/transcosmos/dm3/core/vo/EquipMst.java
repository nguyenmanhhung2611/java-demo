package jp.co.transcosmos.dm3.core.vo;

import java.util.Date;

/**
 * �ݔ��}�X�^.
 * <p>
 * �N���X���A����сA�t�B�[���h���͂c�a�e�[�u�����A�񖼂Ƀ}�b�s���O�����B
 * <p>
 *
 * <pre>
 * �S����        �C����        �C�����e
 * ------------ ----------- -----------------------------------------------------
 * Trans        2015.03.10  �V�K�쐬
 * H.Mizuno		2015.03.13	�������ύX�ɔ����A�N���X����ύX
 * </pre>
 * <p>
 * ���ӎ���<br/>
 * �ʃJ�X�^�}�C�Y�Ōp�������\��������̂ŁA���ڃC���X�^���X�𐶐����Ȃ����B<br/>
 * �C���X�^���X���擾����ꍇ�́AValueObjectFactory ����擾���鎖�B<br/>
 *
 */
public class EquipMst {

	/** �ݔ�CD */
	private String equipCd;
	/** �ݔ����� */
	private String equipName;
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
	 * �ݔ�CD ���擾����B<br/>
	 * <br/>
	 *
	 * @return �ݔ�CD
	 */
	public String getEquipCd() {
		return equipCd;
	}

	/**
	 * �ݔ�CD ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param equipCd
	 */
	public void setEquipCd(String equipCd) {
		this.equipCd = equipCd;
	}

	/**
	 * �ݔ����� ���擾����B<br/>
	 * <br/>
	 *
	 * @return �ݔ�����
	 */
	public String getEquipName() {
		return equipName;
	}

	/**
	 * �ݔ����� ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param equipName
	 */
	public void setEquipName(String equipName) {
		this.equipName = equipName;
	}

	/**
	 * �\���� ���擾����B<br/>
	 * <br/>
	 *
	 * @return �\����
	 */
	public Integer getSortOrder() {
		return sortOrder;
	}

	/**
	 * �\���� ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param sortOrder
	 */
	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
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
