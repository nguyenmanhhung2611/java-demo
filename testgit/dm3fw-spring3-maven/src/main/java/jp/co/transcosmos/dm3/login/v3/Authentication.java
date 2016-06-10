package jp.co.transcosmos.dm3.login.v3;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.login.LoginUser;
import jp.co.transcosmos.dm3.login.UserRoleSet;
import jp.co.transcosmos.dm3.login.form.v3.LoginForm;

/**
 * <pre>
 * V3 �F�ؑΉ�
 * �F�؏������s���T�[�r�X����������C���^�[�t�F�[�X
 * �J�X�^�}�C�Y�����F�؏������쐬����ꍇ�́A���̃C���^�[�t�F�[�X����������K�v������B
 *
 * �S����            �C����               �C�����e
 * ----------------------------------------------------------------------------
 * H.Mizuno  2013.04.04  �V�K�쐬
 * H.Mizuno  2013.04.11  �F�؃`�F�b�N�ƁA���[�U�[���擾�𕪗�
 * 
 * </pre>
*/
public interface Authentication {

	// �蓮���O�C�������B�@����������A���O�C�����[�U�[�́ALoginUser �𕜋A���鎖�B
	public LoginUser login(HttpServletRequest request, HttpServletResponse response, LoginForm form);

	// ���O�A�E�g����
	public void logout(HttpServletRequest request, HttpServletResponse response);

	// ���O�C����Ԃ̔��菈��
	// LoginCheckFilter�@�́A���̃��\�b�h���g�p���ĔF�؍σ`�F�b�N���s���B�@����āA�������O�C���Ȃǂ�
	// ��������ꍇ�͂�����̏����Ɏ�������B
	// ���O�C����Ԃ̏ꍇ�A���O�C�����[�U�[�̏��𕜋A����B
	// ���O�C�����Ă��Ȃ��ꍇ�Anull �𕜋A����B
	public LoginUser checkLoggedIn(HttpServletRequest request, HttpServletResponse response);
	
	
	// ���O�C�����[�U�[�̏����擾����B
	// �ʏ�̃��O�C�������擾���鏈���B�@�ʏ�̃��[�U�[���擾�͂�������g�p���Ă���B
	// ���O�C����Ԃ̏ꍇ�A���O�C�����[�U�[�̏��𕜋A����B
	// ���O�C�����Ă��Ȃ��ꍇ�Anull �𕜋A����B
	public LoginUser getLoggedInUser(HttpServletRequest request, HttpServletResponse response);
	
	
	// ���O�C�����Ă����Ԃ̏ꍇ�A���O�C�����[�U�[�̃��[�����𕜋A����B
	// HasRoleCheckCommand �́A���̃��\�b�h�̖߂�l�����ď�����U�蕪����B
	// �A���AHasRoleCheckCommand �� null ������ƁA���[���`�F�b�N�ΏۊO�Ƃ݂Ȃ��A�F��OK�ɂ���B
	// �G���[�ɂ���ɂ͋�́@UserRoleSet�@�I�u�W�F�N�g�𕜋A����K�v������B
	public UserRoleSet getLoggedInUserRole(HttpServletRequest request, HttpServletResponse response);

}
