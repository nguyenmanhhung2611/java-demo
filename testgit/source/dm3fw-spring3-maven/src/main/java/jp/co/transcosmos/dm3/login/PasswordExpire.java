package jp.co.transcosmos.dm3.login;

import java.util.Date;

/**
 * �p�X���[�h�ύX�L�������`�F�b�N�p�C���^�[�t�F�[�X.
 * <p>
 * �p�X���[�h�ύX�̗L�������`�F�b�N���s���ꍇ�A�Z�b�V�����Ɋi�[����郍�O�C�����[�U�[���̃o���[�I�u�W�F�N�g��
 * ���̃C���^�[�t�F�[�X����������K�v������B<br/>
 * PasswordExpireFilter �́A�Z�b�V�����Ɋi�[���ꂽ���O�C�����[�U�[��񂩂炱�̃C���^�[�t�F�[�X���o�R����
 * �p�X���[�h�̍ŏI�X�V�������擾���ă`�F�b�N����B<br/>
 * <p>
 * <pre>
 * �S����		�C����		�C�����e
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.02.20	�V�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 * V3 �F�؂̂ݑΉ�
 * 
 */
public interface PasswordExpire {

	/**
	 * �p�X���[�h�̍ŏI�ύX���t�𕜋A����B<br/>
	 * <br/>
	 * @return �p�X���[�h�̍ŏI�ύX���t
	 */
	public Date getLastPasswdChange();

}
