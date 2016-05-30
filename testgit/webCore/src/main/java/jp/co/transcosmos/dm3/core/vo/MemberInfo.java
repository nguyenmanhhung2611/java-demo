package jp.co.transcosmos.dm3.core.vo;

import java.io.Serializable;
import java.util.Date;

import jp.co.transcosmos.dm3.core.model.mypage.MypageUserInterface;
import jp.co.transcosmos.dm3.utils.EncodingUtils;

/**
 * �}�C�y�[�W������N���X.
 * <p>
 * �N���X���A����сA�t�B�[���h���͂c�a�e�[�u�����A�񖼂Ƀ}�b�s���O�����B
 * <p>
 * <pre>
 * �S����		�C����		�C�����e
 * ------------ ----------- -----------------------------------------------------
 * I.Shu		2015.02.18	�V�K�쐬
 * H.Mizuno		2015.03.02	�F�ؗp�ɃC���^�[�t�F�[�X������
 * H.Mizuno		2015.04.14	Cookie �������O�C���ɑΉ�
 * </pre>
 * <p>
 * ���ӎ���<br/>
 * �ʃJ�X�^�}�C�Y�Ōp�������\��������̂ŁA���ڃC���X�^���X�𐶐����Ȃ����B<br/>
 * �C���X�^���X���擾����ꍇ�́AValueObjectFactory ����擾���鎖�B<br/>
 * 
 */
public class MemberInfo implements MypageUserInterface, Serializable {

	private static final long serialVersionUID = 1L;
	
	/** ���[�U�[ID */
	private String userId;
	/** ������i���j */
	private String memberLname;
	/** ������i���j */
	private String memberFname;
	/** ������E�J�i�i���j */
	private String memberLnameKana;
	/** ������E�J�i�i���j */
	private String memberFnameKana;
	/** ���[���A�h���X */
	private String email;
	/** �p�X���[�h */
	private String password;
	/** �������O�C���p�g�[�N�� */
	private String cookieLoginToken;
	/** �O�񃍃O�C���� */
	private Date preLogin;
	/** �ŏI���O�C���� */
	private Date lastLogin;
	/** �o�^�� */
	private Date insDate;
	/** �o�^�� */
	private String insUserId;
	/** �ŏI�X�V�� */
	private Date updDate;
	/** �ŏI�X�V�� */
	private String updUserId;
	


	/**
	 * MypageUserInterface�C���^�[�t�F�[�X(LoginUser�@�C���^�[�t�F�[�X)�̎������\�b�h<br/>
	 * �t���[�����[�N��A�}�C�y�[�W��������e�i���X�@�\�͂��̃��\�b�h�̖߂�l���}�C�y�[�W����̎�L�[
	 * �Ƃ��Ĉ����B<br/>
	 * <br/>
	 * @param userId ���[�U�[�h�c�i��L�[�l�j
	 */
	@Override
	public String getUserId() {
		return userId;
	}

	/**
	 * MypageUserInterface�C���^�[�t�F�[�X�̎������\�b�h<br/>
	 * ��{�@�\�͂��̃t�B�[���h�����[�U�[ID�Ƃ��Đݒ肷��B<br/>
	 * <br/>
	 * @param userId ���[�U�[ID
	 */
	@Override
	public void setUserId(Object userId) {
		this.userId = (String)userId;
	}

	/**
	 * ���[�U�[ID��ݒ肷��B<br/>
	 * <br/>
	 * @param userId ���[�U�[ID
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * MypageUserInterface�C���^�[�t�F�[�X�̎������\�b�h<br/>
	 * ��{�@�\�͂��̃t�B�[���h��������i���j�Ƃ��Ď擾����B<br/>
	 * <br/>
	 * @return ������i���j
	 */
	@Override
	public String getMemberLname() {
		return memberLname;
	}

	/**
	 * MypageUserInterface�C���^�[�t�F�[�X�̎������\�b�h<br/>
	 * ��{�@�\�͂��̃t�B�[���h��������i���j�Ƃ��Đݒ肷��B<br/>
	 * <br/>
	 * @param memberLname ������i���j
	 */
	@Override
	public void setMemberLname(String memberLname) {
		this.memberLname = memberLname;
	}

	/**
	 * MypageUserInterface�C���^�[�t�F�[�X�̎������\�b�h<br/>
	 * ��{�@�\�͂��̃t�B�[���h��������i���j�Ƃ��Ď擾����B<br/>
	 * <br/>
	 * @return ������i���j
	 */
	@Override
	public String getMemberFname() {
		return memberFname;
	}

	/**
	 * MypageUserInterface�C���^�[�t�F�[�X�̎������\�b�h<br/>
	 * ��{�@�\�͂��̃t�B�[���h��������i���j�Ƃ��Đݒ肷��B<br/>
	 * <br/>
	 * @param memberFname ������i���j
	 */
	@Override
	public void setMemberFname(String memberFname) {
		this.memberFname = memberFname;
	}

	/**
	 * MypageUserInterface�C���^�[�t�F�[�X�̎������\�b�h<br/>
	 * ��{�@�\�͂��̃t�B�[���h��������E�J�i�i���j�Ƃ��Ď擾����B<br/>
	 * <br/>
	 * @return ������E�J�i�i���j
	 */
	@Override
	public String getMemberLnameKana() {
		return memberLnameKana;
	}

	/**
	 * MypageUserInterface�C���^�[�t�F�[�X�̎������\�b�h<br/>
	 * ��{�@�\�͂��̃t�B�[���h��������E�J�i�i���j�Ƃ��Đݒ肷��B<br/>
	 * <br/>
	 * @param memberLnameKana ������E�J�i�i���j
	 */
	@Override
	public void setMemberLnameKana(String memberLnameKana) {
		this.memberLnameKana = memberLnameKana;
	}

	/**
	 * MypageUserInterface�C���^�[�t�F�[�X�̎������\�b�h<br/>
	 * ��{�@�\�͂��̃t�B�[���h��������E�J�i�i���j�Ƃ��Ď擾����B<br/>
	 * <br/>
	 * @return ������E�J�i�i���j
	 */
	@Override
	public String getMemberFnameKana() {
		return memberFnameKana;
	}

	/**
	 * MypageUserInterface�C���^�[�t�F�[�X�̎������\�b�h<br/>
	 * ��{�@�\�͂��̃t�B�[���h��������E�J�i�i���j�Ƃ��Đݒ肷��B<br/>
	 * <br/>
	 * @param memberFnameKana ������E�J�i�i���j
	 */
	@Override
	public void setMemberFnameKana(String memberFnameKana) {
		this.memberFnameKana = memberFnameKana;
	}

	/**
	 * MypageUserInterface�C���^�[�t�F�[�X�̎������\�b�h<br/>
	 * ��{�@�\�͂��̃t�B�[���h�����[���A�h���X�Ƃ��Ď擾����B<br/>
	 * <br/>
	 * @return ���[���A�h���X
	 */
	@Override
	public String getEmail() {
		return email;
	}

	/**
	 * MypageUserInterface�C���^�[�t�F�[�X�̎������\�b�h<br/>
	 * ��{�@�\�͂��̃t�B�[���h�����[���A�h���X���Đݒ肷��B<br/>
	 * <br/>
	 * @param email ���[���A�h���X
	 */
	@Override
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * MypageUserInterface�C���^�[�t�F�[�X(LoginUser�@�C���^�[�t�F�[�X)�̎������\�b�h<br/>
	 * �t���[�����[�N��A�}�C�y�[�W��������e�i���X�@�\�͂��̃��\�b�h�̖߂�l���}�C�y�[�W����̃��O�C���h�c
	 * �Ƃ��Ĉ����B<br/>
	 * <br/>
	 * @return ���O�C��ID
	 */
	@Override
	public String getLoginId() {
		return email;
	}

	/**
	 * ���O�C��ID��ݒ肷��B<br/>
	 * <br/>
	 * @param loginId ���O�C��ID
	 */
	public void setLoginId(String loginId) {
		this.email = loginId;
	}

	/**
	 * MypageUserInterface�C���^�[�t�F�[�X�̎������\�b�h<br/>
	 * ��{�@�\�͂��̃t�B�[���h���p�X���[�h�Ƃ��Ď擾����B<br/>
	 * <br/>
	 * @return �p�X���[�h �i�n�b�V���l�j
	 */
	@Override
	public String getPassword() {
		return password;
	}

	/**
	 * MypageUserInterface�C���^�[�t�F�[�X�̎������\�b�h<br/>
	 * ��{�@�\�͂��̃t�B�[���h���p�X���[�h�Ƃ��Đݒ肷��B<br/>
	 * <br/>
	 * @param password �p�X���[�h �i�n�b�V���l�j
	 */
	@Override
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * �������O�C���p�g�[�N�����擾����B<br/>
	 * <br/>
	 * @return �������O�C���p�g�[�N��
	 */
	public String getCookieLoginToken() {
		return cookieLoginToken;
	}

	/**
	 * �������O�C���p�g�[�N����ݒ肷��B<br/>
	 * <br/>
	 * @param cookieLoginToken �������O�C���p�g�[�N��
	 */
	public void setCookieLoginToken(String cookieLoginToken) {
		this.cookieLoginToken = cookieLoginToken;
	}

	/**
	 * MypageUserInterface�C���^�[�t�F�[�X�̎������\�b�h<br/>
	 * ��{�@�\�͂��̃t�B�[���h��O�񃍃O�C�����Ƃ��Ď擾����B<br/>
	 * <br/>
	 * @return �O�񃍃O�C����
	 */
	@Override
	public Date getPreLoginDate() {
		return preLogin;
	}

	/**
	 * �O�񃍃O�C�������擾����B<br/>
	 * <br/>
	 * @return �O�񃍃O�C����
	 */
	public Date getPreLogin() {
		return preLogin;
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
	 * �ŏI���O�C�������擾����B<br/>
	 * <br/>
	 * @return �ŏI���O�C����
	 */
	public Date getLastLogin() {
		return lastLogin;
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
	 * MypageUserInterface�C���^�[�t�F�[�X�̎������\�b�h<br/>
	 * ��{�@�\�͂��̃t�B�[���h��o�^���Ƃ��Đݒ肷��B<br/>
	 * <br/>
	 * @param insDate �o�^��
	 */
	@Override
	public Date getInsDate() {
		return insDate;
	}
	
	/**
	 * MypageUserInterface�C���^�[�t�F�[�X�̎������\�b�h<br/>
	 * ��{�@�\�͂��̃t�B�[���h��o�^���Ƃ��Ď擾����B<br/>
	 * <br/>
	 * @return �o�^��
	 */
	@Override
	public void setInsDate(Date insDate) {
		this.insDate = insDate;
	}
	
	/**
	 * MypageUserInterface�C���^�[�t�F�[�X�̎������\�b�h<br/>
	 * ��{�@�\�͂��̃t�B�[���h��o�^�҂Ƃ��Đݒ肷��B<br/>
	 * <br/>
	 * @param insUserId �o�^��
	 */
	@Override
	public String getInsUserId() {
		return insUserId;
	}
	
	/**
	 * MypageUserInterface�C���^�[�t�F�[�X�̎������\�b�h<br/>
	 * ��{�@�\�͂��̃t�B�[���h��o�^�҂Ƃ��Ď擾����B<br/>
	 * <br/>
	 * @return �o�^��
	 */
	@Override
	public void setInsUserId(String insUserId) {
		this.insUserId = insUserId;
	}
	
	/**
	 * MypageUserInterface�C���^�[�t�F�[�X�̎������\�b�h<br/>
	 * ��{�@�\�͂��̃t�B�[���h���ŏI�X�V���Ƃ��Đݒ肷��B<br/>
	 * <br/>
	 * @param updDate �ŏI�X�V��
	 */
	@Override
	public Date getUpdDate() {
		return updDate;
	}
	
	/**
	 * MypageUserInterface�C���^�[�t�F�[�X�̎������\�b�h<br/>
	 * ��{�@�\�͂��̃t�B�[���h���ŏI�X�V���Ƃ��Ď擾����B<br/>
	 * <br/>
	 * @return �ŏI�X�V��
	 */
	@Override
	public void setUpdDate(Date updDate) {
		this.updDate = updDate;
	}
	
	/**
	 * MypageUserInterface�C���^�[�t�F�[�X�̎������\�b�h<br/>
	 * ��{�@�\�͂��̃t�B�[���h���ŏI�X�V�҂Ƃ��Đݒ肷��B<br/>
	 * <br/>
	 * @param updUserId �ŏI�X�V��
	 */
	@Override
	public String getUpdUserId() {
		return updUserId;
	}
	
	/**
	 * MypageUserInterface�C���^�[�t�F�[�X�̎������\�b�h<br/>
	 * ��{�@�\�͂��̃t�B�[���h���ŏI�X�V�҂Ƃ��Ď擾����B<br/>
	 * <br/>
	 * @return �ŏI�X�V��
	 */
	@Override
	public void setUpdUserId(String updUserId) {
		this.updUserId = updUserId;
	}

	/**
	 * MypageUserInterface�C���^�[�t�F�[�X�iLoginUser �C���^�[�t�F�[�X�j�̎������\�b�h<br/>
	 * �p�X���[�h��v�m�F�p���\�b�h<br>
	 * �p�X���[�h��DB����擾�����p�X���[�h�ƈ�v���Ă��邩���`�F�b�N�����ʂ𕜋A����B<br>
	 * <br>
	 * @param pPassword ���͂��ꂽ�p�X���[�h
	 * @return true ��v false �s��v
	 */
	@Override
	public boolean matchPassword(String pPassword) {
		return getPassword().equals(EncodingUtils.md5Encode(pPassword));
	}

	/**
	 * MypageUserInterface�C���^�[�t�F�[�X�iLastLoginTimestamped �C���^�[�t�F�[�X�j�̎������\�b�h<br/>
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
	 * MypageUserInterface�C���^�[�t�F�[�X�iLastLoginTimestamped �C���^�[�t�F�[�X�j�̎������\�b�h<br/>
	 * ���̃��\�b�h�ɊY������t�B�[���h���g�p���čŏI���O�C���������X�V����B<br/>
	 * <br/>
	 * @param lLastLoginTimestamp �ŏI���O�C����
	 */
	@Override
	public void setThisLoginTimestamp(Date lLastLoginTimestamp) {
		this.lastLogin = lLastLoginTimestamp;
	}

	/**
	 * MypageUserInterface�C���^�[�t�F�[�X�iCookieLoginUser �C���^�[�t�F�[�X�j�̎������\�b�h<br/>
	 * �t���[�����[�N�̔F�؏����́A���̃��\�b�h�̖߂�l�� Cookie �ɂ�鎩�����O�C���̃g�[�N���Ƃ��Ďg�p����B<br/>
	 * <br/>
	 * @return �������O�C���̃g�[�N��
	 */
	@Override
	public String getCookieLoginPassword() {
		return this.cookieLoginToken;
	}

	/**
	 * MypageUserInterface�C���^�[�t�F�[�X�iCookieLoginUser �C���^�[�t�F�[�X�j�̎������\�b�h<br/>
	 * �t���[�����[�N�̔F�؏����́A���̃��\�b�h�� Cookie �ɂ�鎩�����O�C���̃g�[�N����ݒ肷��B<br/>
	 * <br/>
	 * @param �������O�C���̃g�[�N��
	 */
	@Override
	public void setCookieLoginPassword(String token) {
		this.cookieLoginToken = token;
	}

	/**
	 * MypageUserInterface�C���^�[�t�F�[�X�iCookieLoginUser �C���^�[�t�F�[�X�j�̎������\�b�h<br/>
	 * �t���[�����[�N�́A���̃��\�b�h�̖߂�l�� Token �̏ƍ����s���B<br/>
	 * <br/>
	 * @return token ���������ꍇ�� true
	 */
	@Override
	public boolean matchCookieLoginPassword(String myToken) {
		return (this.cookieLoginToken != null) && this.cookieLoginToken.equals(myToken);
	}

}
