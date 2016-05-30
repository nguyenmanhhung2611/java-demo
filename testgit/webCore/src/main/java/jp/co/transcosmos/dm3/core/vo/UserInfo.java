package jp.co.transcosmos.dm3.core.vo;

import java.io.Serializable;
import java.util.Date;

import jp.co.transcosmos.dm3.login.LastLoginTimestamped;
import jp.co.transcosmos.dm3.login.LoginUser;

/**
 * ���[�U�[ID���.
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
public class UserInfo implements LoginUser, LastLoginTimestamped, Serializable {

	private static final long serialVersionUID = 1L;

	/** ���[�U�[ID */
	private String userId;
	/** �o�^�� */
	private Date insDate;
	/** �O�񃍃O�C���� */
	private Date preLogin;
	/** �ŏI���O�C���� */
	private Date lastLogin;

	/**
	 * ���[�U�[ID ���擾����B<br/>
	 * �F�ؗp�C���^�[�t�F�[�X�iLoginUser�j�̎������\�b�h�ŁA�����F�؂Ŏg�p�����B<br/>
	 * <br/>
	 *
	 * @return ���[�U�[ID
	 */
	@Override
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
	 * �O�񃍃O�C���� ���擾����B<br/>
	 * <br/>
	 *
	 * @return �O�񃍃O�C����
	 */
	public Date getPreLogin() {
		return preLogin;
	}

	/**
	 * �O�񃍃O�C���� ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param preLogin
	 */
	public void setPreLogin(Date preLogin) {
		this.preLogin = preLogin;
	}

	/**
	 * �ŏI���O�C���� ���擾����B<br/>
	 * <br/>
	 *
	 * @return �ŏI���O�C����
	 */
	public Date getLastLogin() {
		return lastLogin;
	}

	/**
	 * �ŏI���O�C���� ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param lastLogin
	 */
	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	/**
	 * ���O�C��ID ���擾����B<br/>
	 * �F�ؗp�C���^�[�t�F�[�X�iLoginUser�j�̎������\�b�h�ŁA�����F�؂Ŏg�p�����B<br/>
	 * �������O�C���ł́A���O�C��ID = ���[�U�[ID �ƂȂ�<br/>
	 * <br/>
	 * @return null �Œ�
	 */
	@Override
	public String getLoginId() {
		return this.userId;
	}

	/**
	 * �p�X���[�h�̏ƍ����s���B<br/>
	 * �F�ؗp�C���^�[�t�F�[�X�iLoginUser�j�̎������\�b�h�ŁA�����F�؂Ŏg�p�����B<br/>
	 * �������O�C���ł́A�����I�ȃ��O�C���͍s��Ȃ��̂Ńp�X���[�h�ƍ��͏�� true �𕜋A����B<br/>
	 * <br/>
	 * @return true �Œ�
	 */
	@Override
	public boolean matchPassword(String password) {
		// TODO ��ŁADB�ɕۑ������g�[�N���ɕύX����B

		return true;
	}

	/**
	 * LastLoginTimestamped �C���^�[�t�F�[�X�̎������\�b�h<br/>
	 * ���̃��\�b�h�́A�ŏI���O�C���������X�V����O�Ɏ��s�����B<br/>
	 * �Ⴆ�΁A�O�񃍃O�C��������ʕ\������ꍇ�A�ŏI���O�C�����̓��O�C���������ɍX�V����Ă��܂��̂ŁA
	 * ���̃��\�b�h���I�[�o�[���C�h���Ēl��ޔ����Ă����K�v������B<br/>
	 * <br/>
	 */
	@Override
	public void copyThisLoginTimestampToLastLoginTimestamp() {
		setPreLogin(getLastLogin());
	}

	/**
	 * LastLoginTimestamped �C���^�[�t�F�[�X�̎������\�b�h<br/>
	 * ���̃��\�b�h�ɊY������t�B�[���h���g�p���čŏI���O�C���������X�V����B<br/>
	 * <br/>
	 * @param lLastLoginTimestamp �ŏI���O�C����
	 */
	@Override
	public void setThisLoginTimestamp(Date lLastLoginTimestamp) {
		this.lastLogin = lLastLoginTimestamp;		
	}
}
