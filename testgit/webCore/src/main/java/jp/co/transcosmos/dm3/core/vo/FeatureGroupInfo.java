package jp.co.transcosmos.dm3.core.vo;

import java.util.Date;

/**
 * ���W�O���[�v���.
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
public class FeatureGroupInfo {

	/** ���W�O���[�vID */
	private String featureGroupId;
	/** ���W�O���[�v�� */
	private String featureGroupName;
	/** �o�^�� */
	private Date insDate;
	/** �o�^�� */
	private String insUserId;
	/** �ŏI�X�V�� */
	private Date updDate;
	/** �ŏI�o�^�� */
	private String updUserId;

	/**
	 * ���W�O���[�vID ���擾����B<br/>
	 * <br/>
	 *
	 * @return ���W�O���[�vID
	 */
	public String getFeatureGroupId() {
		return featureGroupId;
	}

	/**
	 * ���W�O���[�vID ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param featureGroupId
	 */
	public void setFeatureGroupId(String featureGroupId) {
		this.featureGroupId = featureGroupId;
	}

	/**
	 * ���W�O���[�v�� ���擾����B<br/>
	 * <br/>
	 *
	 * @return ���W�O���[�v��
	 */
	public String getFeatureGroupName() {
		return featureGroupName;
	}

	/**
	 * ���W�O���[�v�� ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param featureGroupName
	 */
	public void setFeatureGroupName(String featureGroupName) {
		this.featureGroupName = featureGroupName;
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
	 * �ŏI�o�^�� ���擾����B<br/>
	 * <br/>
	 *
	 * @return �ŏI�o�^��
	 */
	public String getUpdUserId() {
		return updUserId;
	}

	/**
	 * �ŏI�o�^�� ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param updUserId
	 */
	public void setUpdUserId(String updUserId) {
		this.updUserId = updUserId;
	}
}
