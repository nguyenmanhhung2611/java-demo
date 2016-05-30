package jp.co.transcosmos.dm3.webFront.auth;

import jp.co.transcosmos.dm3.login.LockSupportLoginUser;
import jp.co.transcosmos.dm3.login.v3.DefaultLockChecker;
import jp.co.transcosmos.dm3.corePana.vo.MemberInfo;


public class PanaLockChecker extends DefaultLockChecker {

	
	/**
	 * �}�C�y�[�W���b�N��ԃ`�F�b�N����.
	 * <p>
	 * �t���[�����[�N�̃��b�N��ԃ`�F�b�N���g�����A������̃��b�N�t���O�� 1 �̏ꍇ�����b�N��ԂƂ���B<br/>
	 * </ul>
	 * <br/>
	 * <pre>
	 * �S����		�C����		�C�����e
	 * ------------ ----------- -----------------------------------------------------
	 * H.Mizuno		2015.03.05	�V�K�쐬
	 * </pre>
	 * <p>
	 * ���ӎ���<br/>
	 * 
	 */
	@Override
	public boolean isLocked(LockSupportLoginUser loginUser) {
		// �I���W�i���̏������g�p���āA���O�C�����s�ɂ�郍�b�N��Ԃ��`�F�b�N����B
		boolean ret = super.isLocked(loginUser);

		// ���O�C�����s�񐔂ɂ�胍�b�N����Ă��Ȃ��ꍇ�A���[�U�[�������b�N�t���O���`�F�b�N����B
		// �������b�N��Ԃ̏ꍇ�Atrue �𕜋A����B
		if (!ret) {
			if ("1".equals(((MemberInfo)loginUser).getLockFlg())) return true;
		}

		return ret;
	}
}
