package jp.co.transcosmos.dm3.login.form.v3;

import jp.co.transcosmos.dm3.validation.Validateable;


/**
 * <pre>
 * Ver 3 �F�ؑΉ��p�t�H�[���̃C���^�[�t�F�[�X
 * �W���� DefaultLoginForm ��p�ӂ��Ă��邪�A�o���f�[�V�����`�F�b�N���A�J�X�^�}�C�Y����ꍇ�́A
 * ���̃C���^�[�t�F�[�X�����������N���X���쐬���A���R�ɍ����ւ��鎖���ł���B
 * �t�H�[���N���X�������ւ���ꍇ�́ALoginCommand �́@LoginForm�@�v���p�e�B�ɐV�����t�H�[��
 * �N���X��ݒ肷��B
 *
 * �S����            �C����               �C�����e
 * ----------------------------------------------------------------------------
 * H.Mizuno  2013.04.05  �V�K�쐬
 *
 * </pre>
*/
public interface LoginForm extends Validateable {

	// ���O�C��ID�̎擾
	public String getLoginID();

	// �p�X���[�h�̎擾
	public String getPassword();

}
