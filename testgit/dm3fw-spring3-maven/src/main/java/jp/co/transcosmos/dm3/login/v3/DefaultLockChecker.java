package jp.co.transcosmos.dm3.login.v3;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.transcosmos.dm3.login.LockSupportLoginUser;


/**
 * �W�������ƂȂ�A�A�J�E���g���b�N�̔��菈��.
 * ���O�C�����s�񐔂�臒l�Ɣ�r���A���s�񐔂�臒l�𒴂��Ă���ꍇ�̓��b�N��ԂƔ��肷��B<br>
 * 
 * <br>
 * @author H.Mizuno
 *
 */
public class DefaultLockChecker implements LockSupportChecker {

	private static final Log log = LogFactory.getLog(DefaultLockChecker.class);

	// note
	// ���̃N���X�̃v���p�e�B�́A�S�C���X�^���X�ŋ��L����Ă���̂ŁADI �R���e�i�ȊO����͕ύX���Ȃ����B
	// �ϑ��I�ȍ\�������ALockSupportLoginUser�@�ƁACookieLoginUser�@��g�ݍ��킹�Ďg�p�����ꍇ�A
	// CookieLoginUser#matchCookieLoginPassword() �����炱�̃N���X�� isLocked() ���g�p���āA
	// �A�J�E���g�̃��b�N��Ԃ��`�F�b�N����K�v������B
	// �����ACookieLoginUser ���Ń`�F�b�N���Ȃ������ꍇ�A��x�A�������O�C�����������Ă��܂��̂ŁA�ŏI���O�C
	// �������X�V����Ă��܂��B

	/** ���O�C�����s�񐔂�臒l �i�f�t�H���g 5�j*/
//	private static Integer maxFailCount = 5;
	private Integer maxFailCount = 5;

	/**
	 * ���O�C�����s�̃C���^�[�o�� �i�P�ʕ��j�@�f�t�H���g 10 ��<br/>
	 * �����Ŏw�肵�����Ԃ��o�߂��Ă��烍�O�C���Ɏ��s�����ꍇ�A���s�񐔂͂P�Ƀ��Z�b�g�����B<br/>
	 * 0 �̏ꍇ�A���O�C������������܂Ń��Z�b�g�͍s��Ȃ��B<br/>
	 */
//	private static Integer failInterval = 10;
	private Integer failInterval = 10;

	/** ���b�N���ԁ@�i�P�ʕ��j  �f�t�H���g�@0 �̏ꍇ�A�i���I�Ƀ��b�N����B */
//	private static Integer lockInterval = 0;
	private Integer lockInterval = 0;



	/**
	 * ���O�C�����s�񐔂�臒l�𒴂��Ă��邩���`�F�b�N����B<br/>
	 * <ul>
	 * <li>���O�C�����s�񐔂�臒l��菬�����ꍇ�A�ʏ��ԂƂ��� false �𕜋A����B</li>
	 * <li>���O�C�����s�񐔂�臒l�𒴂��Ă��āA���b�N���Ԃ� 0 �̏ꍇ�A�������Ƀ��b�N����̂� true �𕜋A����B</li> 
	 * <li>���O�C�����s�񐔂�臒l�𒴂��Ă��āA���b�N���Ԃ��w�肳��Ă���ꍇ�A�ŏI���O�C�����s���ƃ��b�N����
	 * ���r���A���b�N���Ԃ��o�߂��Ă���ꍇ�͒ʏ��ԂƂ��� false �𕜋A����B</li>
	 * <li>���O�C�����s�񐔂�臒l�𒴂��Ă��āA�ŏI���O�C�����s�������b�N���ԓ��̏ꍇ�� true �𕜋A����B</li>
	 * </ul>
	 * <br/>
	 * @param loginUser�@���O�C�����[�U�[���
	 * 
	 * @return 臒l�𒴂��Ă���ꍇ�i���b�N��ԁj true �𕜋A
	 */
	@Override
	public boolean isLocked(LockSupportLoginUser loginUser) {

		// ���O�C�����s�񐔂�臒l�𒴂��Ă��Ȃ��ꍇ�A�ʏ��ԂƂ��ĕ��A����B
//		if (DefaultLockChecker.maxFailCount > convFailCount(loginUser)) return false;
		if (this.maxFailCount > convFailCount(loginUser)) return false;
		
		
		// �ȍ~�A�~�n�l�ɒB���Ă���ꍇ�̏���

		// ���b�N���Ԃ� 0 �̏ꍇ�A�i���I�Ƀ��b�N����̂ŁA���b�N��ԂƂ��ĕ��A����B
//		if (DefaultLockChecker.lockInterval == 0) return true;
		if (this.lockInterval == 0) return true;
		
		// ���O�C�����s�񐔂�臒l�𒴂��Ă��āA�ŏI���O�C�����s������̃P�[�X�͒ʏ�͑��݂��Ȃ��B
		// ���̗l�ȏꍇ�́A���b�N�J�����Ԃ̔��f���o���Ȃ��̂ŁA���b�N���Ƃ��ĕ��A����B
		if (loginUser.getLastFailDate() == null) return true;


        // ���b�N�񐔂�臒l�𒴂��Ă���ꍇ�A���b�N���ԂƏƍ�����B
       	// �����ŏI���O�C�����s���{���b�N���� ���V�X�e�����t��菬�����ꍇ�A�ʏ��ԂƂ��ĕ��A����B
       	long sysDateTime = (new Date()).getTime();

       	Calendar calendar = Calendar.getInstance();
       	calendar.setTime(loginUser.getLastFailDate());
//       	calendar.add(Calendar.MINUTE, DefaultLockChecker.lockInterval);
       	calendar.add(Calendar.MINUTE, this.lockInterval);
       	long lockDateTime = calendar.getTimeInMillis();

//       	if (lockDateTime < sysDateTime && DefaultLockChecker.lockInterval != 0){
       	if (lockDateTime < sysDateTime && this.lockInterval != 0){
       		log.info("Lock duration has been passed.");
       		return false;
       	}

       	log.info("failed count is upper limit already.");
       	return true;
	}

	
	
	/**
	 * ���O�C�����[�U�[��񂩂烍�O�C�����s�񐔂��擾����B<br/>
	 * ���O�C�����s�񐔂� null �̏ꍇ�A0 �Ƃ��ĕ��A����B<br/>
	 * @param loginUser
	 * @return
	 */
	private int convFailCount(LockSupportLoginUser loginUser) {
		Integer failCnt = ((LockSupportLoginUser)loginUser).getFailCnt();
		if (failCnt == null) return 0;
		return failCnt;
	}

	
	
	/**
	 * ���O�C�����s�񐔂�臒l���擾����B<br/>
	 * <br/>
	 * @return ���O�C�����s�񐔂�臒l
	 */
	@Override
	public Integer getMaxFailCount() {
//		return DefaultLockChecker.maxFailCount;
		return this.maxFailCount;
	}

	/**
	 * ���O�C�����s�񐔂�臒l��ݒ肷��B<br/>
	 * �����l�Ƃ��� 5�@�񂪐ݒ肳��Ă���B�@�������O�C���̎��s�񐔂�臒l�ɒB�����ꍇ�A�A�J�E���g�����b�N��ԂɂȂ�B<br/>
	 * <br/>
	 * @param maxFailCount ���O�C�����s��
	 */
	public void setMaxFailCount(Integer maxFailCount) {
//		DefaultLockChecker.maxFailCount = maxFailCount;
		this.maxFailCount = maxFailCount;
	}

	/**
	 * ���O�C�����s�̃C���^�[�o���i�P�ʕ��j���擾����B<br/>
	 * <br/>
	 * @return ���O�C�����s�̃C���^�[�o���i�P�ʕ��j
	 */
	@Override
	public Integer getFailInterval() {
//		return DefaultLockChecker.failInterval;
		return this.failInterval;
	}

	/**
	 * ���O�C�����s�̃C���^�[�o���i�P�ʕ��j��ݒ肷��B<br/>
	 * �����l�Ƃ��� 10 �����ݒ肳��Ă���B�@�Ō�Ƀ��O�C�������s���Ă���w��l�𒴂����ꍇ�A���s�񐔂��P�Ƀ��Z�b�g�����B<br/>
	 * null �܂��́A0 ���w�肵���ꍇ�A���O�C������������܂Ń��Z�b�g�͍s��Ȃ��B
	 * <br/>
	 * @param maxFailCount ���O�C�����s��
	 */
	public void setFailInterval(Integer failInterval) {
		if (failInterval == null) {
//			DefaultLockChecker.failInterval = 0;
			this.failInterval = 0;
		} else {
//			DefaultLockChecker.failInterval = failInterval;			
			this.failInterval = failInterval;			
		}
	}

	/**
	 * ���b�N���ԁi�P�ʕ��j��ݒ肷��B<br/>
	 * �����l�Ƃ��� 0 ���ݒ肳��Ă���B�@null �܂��� 0 �̏ꍇ�A�i���I�Ƀ��b�N��ԂɂȂ�B<br/>
	 * <br/>
	 * @param lockInterval ���b�N���ԁi�P�ʕ��j
	 */
	public void setLockInterval(Integer lockInterval) {
		if (lockInterval == null) {
//			DefaultLockChecker.lockInterval = 0;
			this.lockInterval = 0;
		} else {
//			DefaultLockChecker.lockInterval = lockInterval;
			this.lockInterval = lockInterval;
		}
	}


}
