package jp.co.transcosmos.dm3.core.model.adminUser;

import java.util.Date;

import jp.co.transcosmos.dm3.login.LastLoginTimestamped;
import jp.co.transcosmos.dm3.login.LockSupportLoginUser;
import jp.co.transcosmos.dm3.login.PasswordExpire;

/**
 * <pre>
 * �Ǘ����[�U�[���Ǘ��o���[�I�u�W�F�N�g�p�C���^�[�t�F�[�X
 * �Ǘ����[�U�[���Ǘ����� DAO �̃o���[�I�u�W�F�N�g�́A���̃C���^�[�t�F�[�X����������K�v������B
 * �Ǘ����[�U�[�����e�i���X�@�\�́A���̃C���^�[�t�F�[�X�z���Ƀf�[�^�[�̑�����s���B
 * 
 * �S����       �C����      �C�����e
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.01.28	�V�K�쐬
 * H.Mizuno		2015.02.24	�p�X���[�h�L�������A�A�J�E���g���b�N�Ή�
 *
 * ���ӎ���
 *
 * </pre>
 */
public interface AdminUserInterface extends LockSupportLoginUser, PasswordExpire, LastLoginTimestamped {

	/**
	 * �Ǘ����[�U�[�̃��[�U�[�h�c�i��L�[�̒l�j��ݒ肷��B<br/>
	 * ���̃��\�b�h�Őݒ肷��t�B�[���h���A�Ǘ����[�U�[�̎�L�[�l�Ƃ��Ďg�p�����B<br/>
	 * <br/>
	 * @param userId ���[�U�[�h�c�i��L�[�l�j
	 */
	public void setUserId(Object userId);
	
	/**
	 * �Ǘ����[�U�[�̃��O�C��ID ��ݒ肷��B<br/>
	 * ���̃��\�b�h�Őݒ肷��t�B�[���h���A�Ǘ����[�U�[�̃��O�C��ID�@�Ƃ��Ďg�p�����B<br/>
	 * <br/>
	 * @param loginId
	 */
	public void setLoginId(String loginId);

	/**
	 * �Ǘ����[�U�[�̃p�X���[�h��ݒ肷��B<br/>
	 * ���̃��\�b�h�Őݒ肷��t�B�[���h���A�Ǘ����[�U�[�̃p�X���[�h�Ƃ��Ďg�p�����B<br/>
	 * <br/>
	 * @param password�@�p�X���[�h�i�n�b�V�����ꂽ�l�j
	 */
	public void setPassword(String password);

	/**
	 * �Ǘ����[�U�[�̃p�X���[�h���擾����B<br/>
	 * ���̃��\�b�h�Őݒ肷��t�B�[���h���A�Ǘ����[�U�[�̃p�X���[�h�Ƃ��Ďg�p�����B<br/>
	 * <br/>
	 * @return password�@�p�X���[�h�i�n�b�V�����ꂽ�l�j
	 */
	public String getPassword();
	
	/**
	 * �Ǘ����[�U�[�̃��[�U�[����ݒ肷��B<br/>
	 * ���̃��\�b�h�Őݒ肷��t�B�[���h���A�Ǘ����[�U�[�̃��[�U�[���Ƃ��Ďg�p�����B<br/>
	 * <br/>
	 * @param userName �Ǘ����[�U�[��
	 */
	public void setUserName(String userName);

	/**
	 * �Ǘ����[�U�[�����擾����B<br/>
	 * ���̃��\�b�h�����A����t�B�[���h���A�Ǘ����[�U�[�̃��[�U�[���Ƃ��Ďg�p����B<br/>
	 * <br/>
	 * @return String �Ǘ����[�U�[��
	 */
	public String getUserName();
	
	/**
	 * �Ǘ����[�U�[�̃��[���A�h���X��ݒ肷��B<br/>
	 * ���̃��\�b�h�Őݒ肷��t�B�[���h���A�Ǘ����[�U�[�̃��[���A�h���X�Ƃ��Ďg�p�����B<br/>
	 * <br/>
	 * @param email ���[���A�h���X
	 */
	public void setEmail(String email);
	
	/**
	 * �Ǘ����[�U�[�̃��[���A�h���X���擾����B<br/>
	 * ���̃��\�b�h�����A����t�B�[���h���A�Ǘ����[�U�[�̃��[���A�h���X�Ƃ��Ďg�p����B<br/>
	 * <br/>
	 * @return String ���[���A�h���X
	 */
	public String getEmail();
	
	/**
	 * �Ǘ����[�U�[�̔��l��ݒ肷��B<br/>
	 * ���̃��\�b�h���ݒ肷��t�B�[���h���A�Ǘ����[�U�[�̔��l�Ƃ��Ďg�p����B<br/>
	 * <br/>
	 * @param note ���l
	 */
	public void setNote(String note);
	
	/**
	 * �Ǘ����[�U�[�̔��l���擾����B<br/>
	 * ���̃��\�b�h�����A����t�B�[���h���A�Ǘ����[�U�[�̔��l�Ƃ��Ďg�p����B<br/>
	 * <br/>
	 * @return ���l
	 */
	public String getNote();

	/**
	 * �ŏI���O�C���������擾����B<br/>
	 * ���̃��\�b�h�����A����t�B�[���h���A�Ǘ����[�U�[�̍ŏI���O�C�����Ƃ��Ďg�p����B<br/>
	 * <br/>
	 * @return Date �ŏI���O�C����
	 */
	public Date getLastLoginDate();

	/**
	 * �O�񃍃O�C���������擾����B<br/>
	 * ���̃��\�b�h�����A����t�B�[���h���A�Ǘ����[�U�[�̑O�񃍃O�C�����Ƃ��Ďg�p����B<br/>
	 * <br/>
	 * @return Date �O�񃍃O�C����
	 */
	public Date getPreLoginDate();

	/**
	 * �ŏI�p�X���[�h�ύX������ݒ肷��B<br/>
	 * ���̃��\�b�h���g�p���āA�Ǘ����[�U�[�̍ŏI�p�X���[�h�ύX������ݒ肷��B<br/>
	 * <br/>
	 * @param changeDate �ŏI�p�X���[�h�ύX����
	 */
	public void setLastPasswdChange(Date changeDate);
	
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
