package jp.co.transcosmos.dm3.login.v3;

import jp.co.transcosmos.dm3.login.LockSupportLoginUser;

/**
 * LockSupportAuth ���g�p����A�J�E���g���b�N�����̃C���^�[�t�F�[�X.
 * LockSupportAuth�@�́A�F�؂̎��s�񐔂ɉ����ăA�J�E���g�����b�N���ꂽ�̂Ɠ��l�̓������s���B<br>
 * ���̃C���^�[�t�F�[�X�́ALockSupportAuth �N���X����g�p����A���b�N�̔��菈����A���s��
 * ��臒l���擾����C���^�[�t�F�[�X��񋟂���B<br>
 * ���b�N�̔��胍�W�b�N���J�X�^�}�C�Y����ꍇ�͂��̃C���^�[�t�F�[�X����������K�v������B<br>
 * <br>
 * @author H.Mizuno
 *
 */
public interface LockSupportChecker {

	/**
	 * �w�肳�ꂽ�A�J�E���g�����b�N�����`�F�b�N����B<br/>
	 * <br/>
	 * @param loginUser �`�F�b�N�Ώۃ��[�U�[���
	 * 
	 * @return ���b�N���̏ꍇ�Atrue �𕜋A����B
	 */
	public boolean isLocked(LockSupportLoginUser loginUser);



	/**
	 * ���O�C�����s�񐔂�臒l���擾����B<br/>
	 * ���̐ݒ�l�𒴂����񐔃��O�C�������s����ƃA�J�E���g�����b�N��ԂɂȂ�B<br/>
	 * <br/>
	 * @return ���O�C�����s�񐔂�臒l
	 */
	public Integer getMaxFailCount();



	/**
	 * ���O�C�����s�̃C���^�[�o���i�P�ʕ��j���擾����B<br/>
	 * ���̐ݒu�l�ȓ��Ƀ��O�C�������s�����ꍇ�A���O�C�����s�Ƃ��ăJ�E���g�A�b�v����B<br/>
	 * ���̐ݒ�l�𒴂��Ă���ꍇ�͎��s�񐔂��P�Ƀ��Z�b�g����B<br/>
	 * <br/>
	 * @return ���O�C�����s�̃C���^�[�o���i�P�ʕ��j
	 */
	public Integer getFailInterval();
	
}
