package jp.co.transcosmos.dm3.core.model.mypage;

import java.util.Date;

import jp.co.transcosmos.dm3.login.CookieLoginUser;
import jp.co.transcosmos.dm3.login.LastLoginTimestamped;
import jp.co.transcosmos.dm3.login.LoginUser;

public interface MypageUserInterface extends LoginUser, CookieLoginUser, LastLoginTimestamped {

	/**
	 * �}�C�y�[�W����̃��[�U�[�h�c�i��L�[�̒l�j��ݒ肷��B<br/>
	 * ���̃��\�b�h�Őݒ肷��t�B�[���h���A�}�C�y�[�W����̎�L�[�l�Ƃ��Ďg�p�����B<br/>
	 * <br/>
	 * @param userId ���[�U�[�h�c�i��L�[�l�j
	 */
	public void setUserId(Object userId);

	/**
	 * ������i���j���擾����B<br/>
	 * <br/>
	 * @return ������i���j
	 */
	public String getMemberLname();

	/**
	 * ������i���j��ݒ肷��B<br/>
	 * <br/>
	 * @param memberLname ������i���j
	 */
	public void setMemberLname(String memberLname);
	
	/**
	 * ������i���j���擾����B<br/>
	 * <br/>
	 * @return ������i���j
	 */
	public String getMemberFname();
	
	/**
	 * ������i���j��ݒ肷��B<br/>
	 * <br/>
	 * @param memberFname ������i���j
	 */
	public void setMemberFname(String memberFname);
	
	/**
	 * ������E�J�i�i���j���擾����B<br/>
	 * <br/>
	 * @return ������E�J�i�i���j
	 */
	public String getMemberLnameKana();

	/**
	 * ������E�J�i�i���j��ݒ肷��B<br/>
	 * <br/>
	 * @param memberLnameKana ������E�J�i�i���j
	 */
	public void setMemberLnameKana(String memberLnameKana);

	/**
	 * ������E�J�i�i���j���擾����B<br/>
	 * <br/>
	 * @return ������E�J�i�i���j
	 */
	public String getMemberFnameKana();
	
	/**
	 * ������E�J�i�i���j��ݒ肷��B<br/>
	 * <br/>
	 * @param memberFnameKana ������E�J�i�i���j
	 */
	public void setMemberFnameKana(String memberFnameKana);
	
	/**
	 * ���[���A�h���X���擾����B �i�}�C�y�[�W�̃��O�C��ID �Ƃ��Ďg�p����B�j<br/>
	 * <br/>
	 * @return ���[���A�h���X
	 */
	public String getEmail();
	
	/**
	 * ���[���A�h���X��ݒ肷��B �i�}�C�y�[�W�̃��O�C��ID �Ƃ��Ďg�p����B�j<br/>
	 * <br/>
	 * @param email ���[���A�h���X
	 */
	public void setEmail(String email);

	/**
	 * �p�X���[�h���擾����B<br/>
	 * <br/>
	 * @return �p�X���[�h �i�n�b�V���l�j
	 */
	public String getPassword();

	/**
	 * �p�X���[�h��ݒ肷��B<br/>
	 * <br/>
	 * @param password �p�X���[�h �i�n�b�V���l�j
	 */
	public void setPassword(String password);

	/** �O�񃍃O�C�������擾����B<br/>
	 * <br/>
	 * @return �O�񃍃O�C����
	 */
	public Date getPreLoginDate();
	
	/**
	 * �Ǘ����[�U�[�̓o�^�����擾����B<br/>
	 * <br/>
	 * @return �Ǘ����[�U�[�̓o�^��
	 */
	public Date getInsDate();
	
	/**
	 * �Ǘ����[�U�[�̓o�^����ݒ肷��B<br/>
	 * <br/>
	 * @param insDate �Ǘ����[�U�[�̓o�^��
	 */
	public void setInsDate(Date insDate);
	
	/**
	 * �Ǘ����[�U�[�̓o�^�S����ID ���擾����B<br/>
	 * <br/>
	 * @return �Ǘ����[�U�[�̓o�^�S����ID
	 */
	public String getInsUserId();
	
	/**
	 * �Ǘ����[�U�[�̓o�^�S����ID ��ݒ肷��B<br/>
	 * <br/>
	 * @param insUserId�@�Ǘ����[�U�[�̓o�^�S����ID
	 */
	public void setInsUserId(String insUserId);
	
	/**
	 * �Ǘ����[�U�[�̍X�V�����擾����B<br/>
	 * <br/>
	 * @return �Ǘ����[�U�[�̍X�V��
	 */
	public Date getUpdDate();
	
	/**
	 * �Ǘ����[�U�[�̍X�V����ݒ肷��B<br/>
	 * <br/>
	 * @param updDate �Ǘ����[�U�[�̍X�V��
	 */
	public void setUpdDate(Date updDate);
	
	/**
	 * �Ǘ����[�U�[�̍X�V��ID ���擾����B<br/>
	 * <br/>
	 * @return �Ǘ����[�U�[�̍X�V��ID
	 */
	public String getUpdUserId();
	
	/**
	 * �Ǘ����[�U�[�̍X�V��ID ��ݒ肷��B<br/>
	 * <br/>
	 * @param updUserId
	 */
	public void setUpdUserId(String updUserId);

}
