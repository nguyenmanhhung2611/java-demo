package jp.co.transcosmos.dm3.login;

import java.util.Date;

/**
 * ���O�C�����[�U�[�̃��b�N�`�F�b�N�p�C���^�[�t�F�[�X.
 * <p>
 * �p�X���[�h�̓��̓~�X���J��Ԃ����ꍇ�A�A�J�E���g���b�N����@�\���g�p����ꍇ�A�A�J�E���g���b�N�Ɋւ���
 * �����Ǘ�����e�[�u���̃o���[�I�u�W�F�N�g�͂��̃C���^�[�t�F�[�X����������K�v������B<br/>
 * LockCheckFilter �́A�Z�b�V�����Ɋi�[���ꂽ���O�C�����[�U�[��񂩂炱�̃C���^�[�t�F�[�X���o�R����
 * �A�J�E���g���b�N�̏�Ԃ��`�F�b�N����B<br/>
 * <p>
 * <pre>
 * �S����		�C����		�C�����e
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.02.24	�V�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 * V3 �F�؂̂ݑΉ�
 * 
 */
public interface LockSupportLoginUser extends LoginUser {

	/**
	 * �A�J�E���g�̃��O�C�����s�񐔂��擾����B<br/>
	 * <br/>
	 * @return �A�J�E���g�̃��O�C�����s��
	 */
	public Integer getFailCnt();

	/**
	 * �A�J�E���g�̍ŏI���O�C�����s�����擾����B<br/>
	 * <br/>
	 * @return �A�J�E���g�̍ŏI���O�C�����s��
	 */
	public Date getLastFailDate();
	
}
