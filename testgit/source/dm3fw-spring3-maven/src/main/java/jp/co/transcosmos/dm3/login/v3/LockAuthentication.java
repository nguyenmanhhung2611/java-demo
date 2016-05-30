package jp.co.transcosmos.dm3.login.v3;

import jp.co.transcosmos.dm3.login.LockSupportLoginUser;

/**
 * �A�J�E���g���b�N�Ή��F�؃C���^�[�t�F�[�X.
 * <p>
 * �A�J�E���g���b�N�ɑΉ�����F�؏������쐬����ꍇ�A���̃C���^�[�t�F�[�X���������鎖�B<br>
 * <p>
 * <pre>
 * �S����		�C����		�C�����e
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.02.24	�V�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br>
 * V3 �F�؂̂ݑΉ�
 * 
 */
public interface LockAuthentication extends Authentication {

	/**
	 * ���b�N��Ԃ����`�F�b�N����B<br/>
	 * <br/>
	 * @param loginUser ���O�C�����[�U�[���
	 * @return ���b�N��Ԃ̏ꍇ�Atrue �𕜋A����B
	 */
	public boolean isLocked(LockSupportLoginUser loginUser);

	/**
	 * ���b�N��ԂɕύX����B<br/>
	 * <br/>
	 * @param loginUser �X�e�[�^�X�X�V�Ώۃ��[�U�[���
	 */
	public void changeToLock(LockSupportLoginUser loginUser);

	/**
	 * �ʏ��ԂɕύX����B<br/>
	 * <br/>
	 * @param loginUser �X�e�[�^�X�X�V�Ώۃ��[�U�[���
	 */
	public void changeToUnlock(LockSupportLoginUser loginUser);

}
