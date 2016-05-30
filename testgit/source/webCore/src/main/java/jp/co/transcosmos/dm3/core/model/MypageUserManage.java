package jp.co.transcosmos.dm3.core.model;

import jp.co.transcosmos.dm3.core.model.exception.DuplicateException;
import jp.co.transcosmos.dm3.core.model.exception.NotFoundException;
import jp.co.transcosmos.dm3.core.model.mypage.form.MypageUserForm;
import jp.co.transcosmos.dm3.core.model.mypage.form.MypageUserSearchForm;
import jp.co.transcosmos.dm3.core.model.mypage.form.PwdChangeForm;
import jp.co.transcosmos.dm3.core.model.mypage.form.RemindForm;
import jp.co.transcosmos.dm3.dao.JoinResult;
import jp.co.transcosmos.dm3.login.LoginUser;


/**
 * �}�C�y�[�W���[�U�[�̏����Ǘ����� Model �N���X�p�C���^�[�t�F�[�X.
 * <p>
 * �}�C�y�[�W���[�U�[�̏��𑀍삷�� model �N���X�͂��̃C���^�[�t�F�[�X���������鎖�B<br/>
 * <p>
 * <pre>
 * �S����       �C����      �C�����e
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.02.16	�V�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 * ���̃N���X�̓V���O���g���� DI �R���e�i�ɒ�`�����̂ŁA�X���b�h�Z�[�t�ł��鎖�B<br/>
 * 
 */
public interface MypageUserManage {

	/**
	 * ���[�U�[ID ���̔Ԃ��A���[�U�[ID ���֒ǉ�����B<br/>
	 * �t�����g�T�C�g�̏ꍇ�A���̋@�\�͓����F�؂̃t�B���^�[����g�p�����B<br/>
	 * �Ǘ��@�\����}�C�y�[�W�����o�^����ꍇ�AaddMyPageUser() ���g�p����O�ɂ��̃��\�b�h��
	 * ���s���ă��[�U�[ID ���̔Ԃ���K�v������B<br/>
	 * <br/>
	 * @param editUserId �X�V���̃^�C���X�^���v�ƂȂ郆�[�U�[ID�@�i�t�����g���̏ꍇ�Anull�j
	 * 
	 * @return �ǉ����ꂽ���O�C�����[�U�[�I�u�W�F�N�g
	 * 
 	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	public LoginUser addLoginID(String editUserId)	throws Exception;



	/**
	 * �p�����[�^�œn���ꂽ Form �̏��Ǝw�肳�ꂽ���[�U�[ID�Ń}�C�y�[�W���[�U�[��V�K�o�^����B<br/>
	 * �o���f�[�V�������̃`�F�b�N�����͌Ăяo�����ŗ\�ߎ��{���Ă������B<br/>
	 * �t�����g������}�C�y�[�W�o�^����ꍇ�A���O�C��ID �͊��ɍ̔Ԃ���Ă���̂ŁAaddUserId �ɂ͂��̒l���w�肷��B<br/>
	 * �Ǘ��@�\����g�p����ꍇ�́AaddLoginID() ���g�p���ă��O�C��ID ���̔Ԃ��Ă������B<br/>
	 * �܂��A�t�����g������}�C�y�[�W�o�^����ꍇ�AeditUserId �́AaddUserId �Ɠ����l��ݒ肷��B<br/>
	 * <br/>
	 * @param inputForm �}�C�y�[�W���[�U�[�̓��͒l���i�[���� Form �I�u�W�F�N�g
	 * @param addUserId �}�C�y�[�W�ǉ��ΏۂƂȂ郆�[�U�[ID
	 * @param editUserId �X�V���̃^�C���X�^���v�ƂȂ郆�[�U�[ID
	 * 
	 * @return addUserId �Ɠ����l
	 * 
 	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
 	 * @exception DuplicateException ���[���A�h���X�i���O�C��ID�j �̏d��
	 */
	public String addMyPageUser(MypageUserForm inputForm, String addUserId, String editUserId)
			throws Exception, DuplicateException;



	/**
	 * �p�����[�^�œn���ꂽ Form �̏��Ń}�C�y�[�W���[�U�[���X�V����B<br/>
	 * �o���f�[�V�������̃`�F�b�N�����͌Ăяo�����ŗ\�ߎ��{���Ă������B<br/>
	 * MyPageUserForm �� userId �v���p�e�B�ɐݒ肳�ꂽ�l����L�[�l�Ƃ��čX�V����B<br/>
	 * �t�����g������}�C�y�[�W�����X�V����ꍇ�AeditUserId �́AaddUserId �Ɠ����l��ݒ肷��B<br/>
	 * <br/>
	 * @param inputForm �}�C�y�[�W���[�U�[�̓��͒l���i�[���� Form �I�u�W�F�N�g
	 * @param addUserId �}�C�y�[�W�X�V�ΏۂƂȂ郆�[�U�[ID
	 * @param editUserId �X�V���̃^�C���X�^���v�ƂȂ郆�[�U�[ID
	 * 
	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 * @exception DuplicateException ���O�C��ID �̏d��
	 * @exception NotFoundException�X�V�ΏۂȂ�
	 */
	public void updateMyPageUser(MypageUserForm inputForm, String updUserId, String editUserId)
			throws Exception, DuplicateException, NotFoundException;



	/**
	 * �����œn���ꂽ Form �̏��Ń}�C�y�[�W���[�U�[��މ������B<br/>
	 * �o���f�[�V�������̃`�F�b�N�����͌Ăяo�����ŗ\�ߎ��{���Ă������B<br/>
	 * �މ�����ɁA���[�U�[ID ���͍폜���Ȃ����B�@�܂��A�Z�L�����e�B�I��肩��AuserId �̒l�́A���N�G
	 * �X�g�p�����[�^����擾���Ȃ����B<br/>
	 * �Ȃ��A�폜�Ώۃ��R�[�h�����݂��Ȃ��ꍇ�ł�����I���Ƃ��Ĉ������B<br/>
	 * <br/>
	 * @param userId �މ�ΏۂƂȂ�}�C�y�[�W���[�U�[�̃��[�U�[ID
	 * 
 	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	public void delMyPageUser(String userId) throws Exception;


	
	/**
	 * ���[���A�h���X�i���O�C��ID�j �����g�p�����`�F�b�N����B<br/>
	 * �t�H�[���ɐݒ肳��Ă��郁�[���A�h���X�i���O�C��ID�j �����g�p�����`�F�b�N����B<br/>
	 * �����A���[�U�[�h�c �������œn���ꂽ�ꍇ�A���̃��[�U�[ID �����O���ă`�F�b�N����B<br/>
	 * <br/>
	 * @param inputForm �`�F�b�N�ΏۂƂȂ���͒l
	 * @param userId �`�F�b�N�ΏۂƂȂ�}�C�y�[�W���[�U�[�̃��[�U�[ID �i�V�K�o�^���� null�j
	 *  
	 * @return true = ���p�Afalse = ���p�s��
	 * 
	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	public boolean isFreeLoginId(MypageUserForm inputForm, String userId)
			throws Exception;



	/**
	 * �}�C�y�[�W���[�U�[���������A���ʃ��X�g�𕜋A����B<br/>
	 * �����œn���ꂽ Form �p�����[�^�̒l�Ō��������𐶐����A�}�C�y�[�W���[�U�[����������B<br/>
	 * �������ʂ� Form �I�u�W�F�N�g�Ɋi�[����A�擾�����Y��������߂�l�Ƃ��ĕ��A����B<br/>
	 * <br/>
	 * @param searchForm ���������A����сA�������ʂ̊i�[�I�u�W�F�N�g
	 * 
	 * @return �Y������
	 * 
 	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	public int searchMyPageUser(MypageUserSearchForm searchForm) throws Exception;



	/**
	 * �����œn���ꂽ���[�U�[ID �i��L�[�l�j�ɊY������}�C�y�[�W���[�U�[���𕜋A����B<br/>
	 * �t�����g���̃}�C�y�[�W����g�p����ꍇ�AuserId �̒l�́A���N�G�X�g�p�����[�^�Ŏ擾���Ȃ����B<br/>
	 * �����Y���f�[�^��������Ȃ��ꍇ�Anull �𕜋A����B<br/>
	 * <br/>
	 * @param userId �擾�ΏۂƂȂ�}�C�y�[�W���[�U�[�̃��[�U�[ID
	 * 
	 * @return�@DB ����擾�����}�C�y�[�W���[�U�[�̃o���[�I�u�W�F�N�g
	 * 
 	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	public JoinResult searchMyPageUserPk(String userId) throws Exception;



	/**
	 * �p�X���[�h�ύX�̓o�^�������s���B<br/>
	 * �w�肳�ꂽ���[���A�h���X�̑Ó������m�F���A��肪�Ȃ���΃p�X���[�h�⍇������ DB �ɓo�^
	 * ����B�@����ɏ������s�����ꍇ�A�̔Ԃ������₢���킹ID �iUUID�j�𕜋A����B<br/>
	 * ���g�����U�N�V�����̊Ǘ��͌ďo���Ő��䂷�鎖�B<br/>
	 *   �i���ׁ̈A���[�����M�͂��̃��\�b�h���Ŏ������Ȃ��B�j<br/>
	 * <br/>
	 * @param inputForm �p�X���[�h�⍇���̑ΏۂƂȂ���͏��
	 * @param entryUserId �����F�؎��ɕ����o����Ă��郆�[�U�[ID
	 * 
	 * @return ����I���� UUID
	 * 
 	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
 	 * @exception NotFoundException �o�^�ΏۂȂ�
	 */
	public String addPasswordChangeRequest(RemindForm inputForm, String entryUserId)
			throws Exception, NotFoundException;



	/**
	 * �p�X���[�h�̕ύX�������s���B<br/>
	 * �p�X���[�h�̓��͊m�F�ȂǁA��ʓI�ȃo���f�[�V�����͌ďo�����Ŏ��{���Ă������B<br/>
	 * ���N�G�X�g�p�����[�^�œn���ꂽ UUID �������؂�̏ꍇ�A�������́A�Y�����R�[�h���p�X���[�h�⍇��
	 * ���ɑ��݂��Ȃ��ꍇ�͗�O���X���[����B<br/>
	 * �p�X���[�h�̍X�V���Ɏg�p�����L�[�̒l�́A�p�X���[�h�⍇����񂩂�擾���鎖�B<br/>
	 * <br/>
	 * @param inputForm �V�����p�X���[�h�̓��͒l���i�[���ꂽ Form
	 * @param entryUserId �����F�؎��ɕ����o����Ă��郆�[�U�[ID
	 * 
 	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
 	 * @exception NotFoundException �X�V�ΏۂȂ�
	 */
	public void changePassword(PwdChangeForm inputForm, String entryUserId)
			throws Exception, NotFoundException;


}
