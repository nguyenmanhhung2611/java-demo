package jp.co.transcosmos.dm3.core.vo;

import java.io.Serializable;
import java.util.Date;

import jp.co.transcosmos.dm3.core.model.adminUser.AdminUserInterface;
import jp.co.transcosmos.dm3.utils.EncodingUtils;

/**
 * �Ǘ��҃��O�C���h�c���N���X.
 * <p>
 * �N���X���A����сA�t�B�[���h���͂c�a�e�[�u�����A�񖼂Ƀ}�b�s���O�����B
 * <p>
 * <pre>
 * �S����		�C����		�C�����e
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.01.28	Shamaison �T�C�g���x�[�X�ɐV�K�쐬
 * H.Mizuno		2015.02.20	PasswordExpire�@�i�p�X���[�h�L�������`�F�b�N�j �֑Ή�
 * </pre>
 * <p>
 * ���ӎ���<br/>
 * �ʃJ�X�^�}�C�Y�Ōp�������\��������̂ŁA���ڃC���X�^���X�𐶐����Ȃ����B<br/>
 * �C���X�^���X���擾����ꍇ�́AValueObjectFactory ����擾���鎖�B<br/>
 * 
 */
public class AdminLoginInfo implements AdminUserInterface, Serializable {

	private static final long serialVersionUID = 1L;

	/** �Ǘ��҃��[�U�[ID */
	private String adminUserId;
	/** ���O�C��ID */
	private String loginId;
	/** �p�X���[�h�i�n�b�V���l�j */
	private String password;
	/** ���[�U�[���� */
	private String userName;
	/** ���[���A�h���X */
	private String email;
	/** ���O�C�����s�� */
	private Integer failCnt;
	/** �ŏI���O�C�����s�� */
	private Date lastFailDate;
	/** �O�񃍃O�C���� */
	private Date preLogin;
	/** �ŏI���O�C���� */
	private Date lastLogin;
	/** �ŏI�p�X���[�h�ύX�� */
	private Date lastPwdChangeDate;
	/** ���l */
	private String note;
	/** �o�^�� */
	private Date insDate;
	/** �o�^�� */
	private String insUserId;
	/** �ŏI�X�V�� */
	private Date updDate;
	/** �ŏI�X�V�� */
	private String updUserId;

	
	
	/**
	 * AdminUserInterface�C���^�[�t�F�[�X(LoginUser�@�C���^�[�t�F�[�X)�̎������\�b�h<br/>
	 * �t���[�����[�N��A�Ǘ����[�U�[�����e�i���X�@�\�͂��̃��\�b�h�̖߂�l���Ǘ����[�U�[�̎�L�[
	 * �Ƃ��Ĉ����B<br/>
	 * <br/>
	 * @param userId ���[�U�[�h�c�i��L�[�l�j
	 */
	@Override
	public void setUserId(Object userId) {
		this.adminUserId = (String) userId;
	}

	/**
	 * AdminUserInterface�C���^�[�t�F�[�X(LoginUser�@�C���^�[�t�F�[�X)�̎������\�b�h<br/>
	 * �t���[�����[�N��A�Ǘ����[�U�[�����e�i���X�@�\�͂��̃��\�b�h�̖߂�l���Ǘ����[�U�[�̎�L�[
	 * �Ƃ��Ĉ����B<br/>
	 * <br/>
	 * @return ���[�U�[ID�i��L�[�l�j
	 */
	@Override
	public Object getUserId() {
		return adminUserId;
	}

	/**
	 * �Ǘ��҃��[�U�[ID ���擾����B<br/>
	 * <br/>
	 * @return  �Ǘ��҃��[�U�[ID
	 */
	public String getAdminUserId() {
		return this.adminUserId;
	}

	/**
	 * �Ǘ��҃��[�U�[ID ��ݒ肷��B<br/>
	 * <br/>
	 * @param adminUserId�@�Ǘ��҃��[�U�[ID
	 */
	public void setAdminUserId(String adminUserId) {
		this.adminUserId = adminUserId;
	}
	
	/**
	 * AdminUserInterface�C���^�[�t�F�[�X(LoginUser�@�C���^�[�t�F�[�X)�̎������\�b�h<br/>
	 * �t���[�����[�N��A�Ǘ����[�U�[�����e�i���X�@�\�͂��̃��\�b�h�̖߂�l���Ǘ����[�U�[�̃��O�C���h�c
	 * �Ƃ��Ĉ����B<br/>
	 * <br/>
	 * @return ���O�C��ID
	 */
	@Override
	public String getLoginId() {
		return this.loginId;
	}

	/**
	 * AdminUserInterface�C���^�[�t�F�[�X�̎������\�b�h<br/>
	 * �Ǘ����[�U�[�����e�i���X�@�\�͂��̃��\�b�h���g�p���ă��O�C��ID�@��ݒ肷��B<br/>
	 * <br/>
	 * @param loginId ���O�C��ID
	 */
	@Override
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	
	/**
	 * AdminUserInterface�C���^�[�t�F�[�X�̎������\�b�h<br/>
	 * �Ǘ����[�U�[�����e�i���X�@�\�͂��̃��\�b�h���g�p���ăp�X���[�h���擾����B<br/>
	 * <br/>
	 * @return �p�X���[�h�i�n�b�V���l�j
	 */
	@Override
	public String getPassword() {
		return password;
	}
	
	/**
	 * AdminUserInterface�C���^�[�t�F�[�X�̎������\�b�h<br/>
	 * �Ǘ����[�U�[�����e�i���X�@�\�͂��̃��\�b�h���g�p���ăp�X���[�h��ݒ肷��B<br/>
	 * <br/>
	 * @param password�@�p�X���[�h�i�n�b�V���l�j
	 */
	@Override
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * AdminUserInterface�C���^�[�t�F�[�X�̎������\�b�h<br/>
	 * �Ǘ����[�U�[�����e�i���X�@�\�͂��̃��\�b�h�̖߂�l���Ǘ����[�U�[���Ƃ��Ĉ����B<br/>
	 * <br/>
	 * @return ���[�U�[��
	 */
	@Override
	public String getUserName() {
		return userName;
	}
	
	/**
	 * AdminUserInterface�C���^�[�t�F�[�X�̎������\�b�h<br/>
	 * �Ǘ����[�U�[�����e�i���X�@�\�͂��̃��\�b�h���ݒ肷��t�B�[���h�����[�U�[���Ƃ��Đݒ肷��B<br/>
	 * <br/>
	 * @param userName ���[�U�[��
	 */
	@Override
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * AdminUserInterface�C���^�[�t�F�[�X�̎������\�b�h<br/>
	 * �Ǘ����[�U�[�����e�i���X�@�\�͂��̃��\�b�h�̖߂�l���Ǘ����[�U�[�̃��[���A�h���X�Ƃ��Ĉ����B<br/>
	 * <br/>
	 * @return ���[���A�h���X
	 */
	@Override
	public String getEmail() {
		return this.email;
	}
	
	/**
	 * AdminUserInterface�C���^�[�t�F�[�X�̎������\�b�h<br/>
	 * �Ǘ����[�U�[�����e�i���X�@�\�͂��̃��\�b�h���ݒ肷��t�B�[���h���Ǘ����[�U�[�̃��[���A�h���X�Ƃ��Đݒ肷��B<br/>
	 * <br/>
	 * @param email ���[���A�h���X
	 */
	@Override
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * AdminUserInterface�iUserLock�j�C���^�[�t�F�[�X�̎������\�b�h<br/>
	 * �t���[�����[�N�A����сA�Ǘ����[�U�[�����e�i���X�@�\�͂��̃��\�b�h���Q�Ƃ���t�B�[���h�����O�C�����s�񐔂Ƃ��Ĉ����B<br/>
	 * <br/>
	 * @return ���O�C�����s��
	 */
	@Override
	public Integer getFailCnt() {
		return failCnt;
	}

	/**
	 * ���O�C�����s�񐔂�ݒ肷��B<br/>
	 * <br/>
	 * @param failCnt ���O�C�����s��
	 */
	public void setFailCnt(Integer failCnt) {
		this.failCnt = failCnt;
	}

	/**
	 * �ŏI���O�C�����s�����擾����B<br/>
	 * <br/>
	 * @return �ŏI���O�C�����s��
	 */
	public Date getLastFailDate() {
		return lastFailDate;
	}

	/**
	 * �ŏI���O�C�����s����ݒ肷��B<br/>
	 * <br/>
	 * @param lastFailDate �ŏI���O�C�����s��
	 */
	public void setLastFailDate(Date lastFailDate) {
		this.lastFailDate = lastFailDate;
	}

	/**
	 * AdminUserInterface�C���^�[�t�F�[�X�̎������\�b�h<br/>
	 * �Ǘ����[�U�[�����e�i���X�@�\�͂��̃��\�b�h�̖߂�l���Ǘ����[�U�[�̍ŏI���O�C�����Ƃ��Ĉ����B<br/>
	 */
	@Override
	public Date getLastLoginDate() {
		return this.lastLogin;
	}

	/**
	 * �ŏI���O�C�������擾����B<br/>
	 * <br/>
	 * @return �ŏI���O�C����
	 */
	public Date getLastLogin() {
		return this.lastLogin;
	}
	
	/**
	 * �ŏI���O�C������ݒ肷��B<br/>
	 * <br/>
	 * @param lastLogin �ŏI���O�C����
	 */
	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	/**
	 *�@dminUserInterface�C���^�[�t�F�[�X(LoginUser�@�C���^�[�t�F�[�X)�̎������\�b�h<br/>
	 *�@�O�񃍃O�C�������擾����B<br/>
	 * <br/>
	 * @return �O�񃍃O�C���� 
	 */
	@Override
	public Date getPreLoginDate() {
		return this.preLogin;
	}

	/** �O�񃍃O�C�������擾����B<br/>
	 * <br/>
	 * @return �O�񃍃O�C����
	 */
	public Date getPreLogin() {
		return this.preLogin;
	}

	/**
	 * �O�񃍃O�C������ݒ肷��B<br/>
	 * <br/>
	 * @param preLogin �O�񃍃O�C����
	 */
	public void setPreLogin(Date preLogin) {
		this.preLogin = preLogin;
	}

	/**
	 * �ŏI�p�X���[�h�ύX�����擾����B<br/>
	 * <br/>
	 * @return �ŏI�p�X���[�h�ύX��
	 */
	public Date getLastPwdChangeDate() {
		return lastPwdChangeDate;
	}

	/**
	 * �ŏI�p�X���[�h�ύX����ݒ肷��B<br/>
	 * <br/>
	 * @param lastPwdChangeDate �ŏI�p�X���[�h�ύX��
	 */
	public void setLastPwdChangeDate(Date lastPwdChangeDate) {
		this.lastPwdChangeDate = lastPwdChangeDate;
	}

	/**
	 * AdminUserInterface�iPasswordExpire�j �C���^�[�t�F�[�X�̎������\�b�h<br/>
	 * �ŏI�p�X���[�h�ύX�����Ǘ����Ă���v���p�e�B�̒l�𕜋A����B<br/>
	 * �t���[�����[�N�͂��̓��t�Ńp�X���[�h�̗L���������`�F�b�N����B<br/>
	 * <br/>
	 * @return �ŏI�p�X���[�h�ύX��
	 */
	@Override
	public Date getLastPasswdChange() {
		return lastPwdChangeDate;
	}

	/**
	 * AdminUserInterface �C���^�[�t�F�[�X�̎������\�b�h<br/>
	 * �ŏI�p�X���[�h�ύX�����Ǘ����Ă���v���p�e�B�ɒl��ݒ肷��B<br/>
	 * �Ǘ��@�\�͂��̃v���p�e�B�Őݒ肷��t�B�[���h���ŏI�p�X���[�h�ύX���̃t�B�[���h�Ƃ��Ĉ����B<br/>
	 * <br/>
	 * @return �ŏI�p�X���[�h�ύX��
	 */
	@Override
	public void setLastPasswdChange(Date changeDate) {
		this.lastPwdChangeDate = changeDate;
	}

	/**
	 * AdminUserInterface�C���^�[�t�F�[�X�̎������\�b�h<br/>
	 * �Ǘ����[�U�[�����e�i���X�@�\�͂��̃��\�b�h�̖߂�l����l�Ƃ��Ĉ����B<br/>
	 * <br/>
	 * @return ���l
	 */
	@Override
	public String getNote() {
		return note;
	}

	/**
	 * ���l��ݒ肷��B<br/>
	 * <br/>
	 * @param note ���l
	 */
	@Override
	public void setNote(String note) {
		this.note = note;
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
	 * @return �o�^��
	 */
	public void setInsDate(Date insDate) {
		this.insDate = insDate;
	}

	/**
	 * �o�^�ҁi�Ǘ��҃��[�U�[ID�j���擾����B<br/>
	 * <br/>
	 * @return �o�^��
	 */
	public String getInsUserId() {
		return insUserId;
	}
	
	/**
	 * �o�^�ҁi�Ǘ��҃��[�U�[ID�j��ݒ肷��B<br/>
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

	/**
	 * AdminUserInterface�C���^�[�t�F�[�X�iLoginUser �C���^�[�t�F�[�X�j�̎������\�b�h<br/>
	 * �p�X���[�h��v�m�F�p���\�b�h<br>
	 * �p�X���[�h��DB����擾�����p�X���[�h�ƈ�v���Ă��邩���`�F�b�N�����ʂ𕜋A����B<br>
	 * <br>
	 * @param pPassword ���͂��ꂽ�p�X���[�h
	 * @return true ��v false �s��v
	 */
	public boolean matchPassword(String pPassword) {
		return getPassword().equals(EncodingUtils.md5Encode(pPassword));
	}

	/**
	 * AdminUserInterface�C���^�[�t�F�[�X�iLastLoginTimestamped �C���^�[�t�F�[�X�j�̎������\�b�h<br/>
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
	 * AdminUserInterface�C���^�[�t�F�[�X�iLastLoginTimestamped �C���^�[�t�F�[�X�j�̎������\�b�h<br/>
	 * ���̃��\�b�h�ɊY������t�B�[���h���g�p���čŏI���O�C���������X�V����B<br/>
	 * <br/>
	 * @param lLastLoginTimestamp �ŏI���O�C����
	 */
	@Override
	public void setThisLoginTimestamp(Date lLastLoginTimestamp) {
		this.lastLogin = lLastLoginTimestamp;
	}

}
