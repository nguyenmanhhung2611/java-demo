package jp.co.transcosmos.dm3.core.model;

import jp.co.transcosmos.dm3.core.model.adminUser.AdminUserInterface;
import jp.co.transcosmos.dm3.core.model.adminUser.form.AdminUserForm;
import jp.co.transcosmos.dm3.core.model.adminUser.form.AdminUserSearchForm;
import jp.co.transcosmos.dm3.core.model.adminUser.form.PwdChangeForm;
import jp.co.transcosmos.dm3.core.model.exception.DuplicateException;
import jp.co.transcosmos.dm3.core.model.exception.NotFoundException;
import jp.co.transcosmos.dm3.dao.JoinResult;


/**
 * �Ǘ����[�U�[�����Ǘ����� Model �N���X�p�C���^�[�t�F�[�X.
 * <p>
 * �Ǘ����[�U�[���𑀍삷�� model �N���X�͂��̃C���^�[�t�F�[�X���������鎖�B<br/>
 * <p>
 * <pre>
 * �S����       �C����      �C�����e
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.02.03	�V�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 * ���̃N���X�̓V���O���g���� DI �R���e�i�ɒ�`�����̂ŁA�X���b�h�Z�[�t�ł��鎖�B<br/>
 * 
 */
public interface AdminUserManage {

	
	/**
	 * �p�����[�^�œn���ꂽ Form �̏��ŊǗ����[�U�[��V�K�ǉ�����B<br/>
	 * �o���f�[�V�������̃`�F�b�N�����͌Ăяo�����ŗ\�ߎ��{���Ă������B<br/>
	 * �Ǘ����[�U�[ID �͎����̔Ԃ����̂ŁAAdminUserForm �� userId �v���p�e�B�ɂ͒l��ݒ肵�Ȃ����B<br/>
	 * <br/>
	 * @param inputForm �Ǘ����[�U�[�̓��͒l���i�[���� Form �I�u�W�F�N�g
	 * @param editUserId ���O�C�����[�U�[ID �i�X�V���p�j
	 * 
	 * @return �̔Ԃ��ꂽ�Ǘ��҃��[�U�[ID
	 * 
	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 * @exception DuplicateException �o�^���郍�O�C��ID ���d�������ꍇ
	 */
	public String addAdminUser(AdminUserForm inputForm, String editUserId)
			throws Exception, DuplicateException;


	/**
	 * �p�����[�^�œn���ꂽ Form �̏��ŊǗ����[�U�[���X�V����B<br/>
	 * �o���f�[�V�������̃`�F�b�N�����͌Ăяo�����ŗ\�ߎ��{���Ă������B<br/>
	 * AdminUserForm �� userId �v���p�e�B�ɐݒ肳�ꂽ�l����L�[�l�Ƃ��čX�V����B<br/>
	 * <br/>
	 * @param inputForm �Ǘ����[�U�[�̓��͒l���i�[���� Form �I�u�W�F�N�g
	 * @param editUserId ���O�C�����[�U�[ID �i�X�V���p�j
	 * 
	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 * @exception DuplicateException �o�^���郍�O�C��ID ���d�������ꍇ
	 * @exception NotFoundException �X�V�Ώۂ����݂��Ȃ��ꍇ
	 */
	public void updateAdminUser(AdminUserForm inputForm, String editUserId)
			throws Exception, DuplicateException, NotFoundException;

	
	
	/**
	 * �p�����[�^�œn���ꂽ Form �̏��ŊǗ����[�U�[���폜����B<br/>
	 * �o���f�[�V�������̃`�F�b�N�����͌Ăяo�����ŗ\�ߎ��{���Ă������B<br/>
	 * AdminUserForm �� userId �v���p�e�B�ɐݒ肳�ꂽ�l����L�[�l�Ƃ��č폜����B
	 * �܂��A�폜�Ώۃ��R�[�h�����݂��Ȃ��ꍇ�ł�����I���Ƃ��Ĉ������B<br/>
	 * <br/>
	 * @param inputForm �폜�ΏۂƂȂ�Ǘ����[�U�[�̎�L�[�l���i�[���� Form �I�u�W�F�N�g
	 * 
	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	public void delAdminUser(AdminUserForm inputForm)
			throws Exception;

	
	
	/**
	 * �A�J�E���g���b�N�X�e�[�^�X��ύX����B<br/>
	 * <br/>
	 * @param inputForm �ύX�ΏۂƂȂ�Ǘ����[�U�[�̎�L�[�l���i�[���� Form �I�u�W�F�N�g
	 * @param locked false = �ʏ탂�[�h�ɐݒ�Atrue = ���b�N���[�h�ɐݒ�
	 * @param editUserId ���O�C�����[�U�[ID �i�X�V���p�j
	 * 
	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 * @exception NotFoundException �X�V�Ώۂ����݂��Ȃ��ꍇ
	 */
	public void changeLockStatus (AdminUserForm inputForm, boolean locked, String editUserId)
			throws Exception, NotFoundException;



	/**
	 * �A�J�E���g���b�N�X�e�[�^�X���`�F�b�N����B<br/>
	 * <br/>
	 * @param targetUser �`�F�b�N�Ώۃ��[�U�[���
	 *  
	 * @return true = ���b�N���Afalse = ����
	 * 
	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	public boolean isLocked(AdminUserInterface targetUser)
			throws Exception;
	
	
	
	/**
	 * ���O�C��ID �����g�p�����`�F�b�N����B<br/>
	 * �t�H�[���ɐݒ肳��Ă��郍�O�C��ID �����g�p�����`�F�b�N����B<br/>
	 * �����A���[�U�[�h�c ���ݒ肳��Ă���ꍇ�A���̃��[�U�[ID �����O���ă`�F�b�N����B<br/>
	 * <br/>
	 * @param inputForm �`�F�b�N�ΏۂƂȂ���͒l
	 *  
	 * @return true = ���p�Afalse = ���p�s��
	 * 
	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	public boolean isFreeLoginId(AdminUserForm inputForm)
			throws Exception;



	/**
	 * �Ǘ����[�U�[���������A���ʃ��X�g�𕜋A����B<br/>
	 * �����œn���ꂽ Form �p�����[�^�̒l�Ō��������𐶐����A�Ǘ����[�U�[����������B<br/>
	 * �������ʂ� Form �I�u�W�F�N�g�Ɋi�[����A�擾�����Y��������߂�l�Ƃ��ĕ��A����B<br/>
	 * <br/>
	 * @param searchForm ���������A����сA�������ʂ̊i�[�I�u�W�F�N�g
	 * 
	 * @return �Y������
	 * 
	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	public int searchAdminUser(AdminUserSearchForm searchForm)
			throws Exception;



	/**
	 * ���N�G�X�g�p�����[�^�œn���ꂽ���[�U�[ID �i��L�[�l�j�ɊY������Ǘ����[�U�[���𕜋A����B<br/>
	 * AdminUserSearchForm �� userId �v���p�e�B�ɐݒ肳�ꂽ�l����L�[�l�Ƃ��ď����擾����B
	 * �����Y���f�[�^��������Ȃ��ꍇ�Anull �𕜋A����B<br/>
	 * <br/>
	 * @param searchForm�@�������ʂƂȂ� JoinResult
	 * 
	 * @return�@DB ����擾�����Ǘ����[�U�[�̃o���[�I�u�W�F�N�g
	 * 
	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	public JoinResult searchAdminUserPk(AdminUserSearchForm searchForm)
			throws Exception;



	/**
	 * �p�X���[�h�̕ύX�������s���B<br/>
	 * �o���f�[�V�������̃`�F�b�N�����͌Ăяo�����ŗ\�ߎ��{���Ă������B<br/>
	 * AdminUserForm �� userId �v���p�e�B�ɐݒ肳�ꂽ�l����L�[�l�Ƃ��ăp�X���[�h���X�V����B<br/>
	 * <br/>
	 * @param inputForm �p�X���[�h�ύX�̓��͏��
	 * @param updUserId �X�V�Ώۃ��[�U�[ID
	 * @param editUserId ���O�C�����[�U�[ID �i�X�V���p�j
	 * 
	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 * @exception NotFoundException �X�V�Ώۂ����݂��Ȃ��ꍇ
	 */
	public void changePassword(PwdChangeForm inputForm, String updUserId, String editUserId)
			throws Exception, NotFoundException;


	// TODO �Ǘ��@�\�p�̃��}�C���_��ǉ�����\������B�@�i�D��x��j

}
