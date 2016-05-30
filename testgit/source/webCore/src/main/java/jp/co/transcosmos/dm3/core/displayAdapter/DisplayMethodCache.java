package jp.co.transcosmos.dm3.core.displayAdapter;

import java.lang.reflect.Method;

/**
 * DisplayAdapter �̃L���b�V���I�u�W�F�N�g.
 * <br>
 * @author H.Mizuno
 *
 */
class DisplayMethodCache {

	/** ���t���N�V�������Ď擾�������\�b�h */
	private Method method;

	/** true �̏ꍇ�A���t���N�V������͈Ϗ���o���[�I�u�W�F�N�g */
	private boolean isTargetValueObject;

	
	
	/**
	 * �R���X�g���N�^<br>
	 * <br>
	 * @param method ���s���郁�\�b�h
	 * @param isTargetValueObject �Ϗ���o���[�I�u�W�F�N�g���g�p����ꍇ�� true ��ݒ肷��B
	 */
	DisplayMethodCache(Method method, boolean isTargetValueObject){
		this.method = method;
		this.isTargetValueObject = isTargetValueObject;
	}

	
	/**
	 * ���t���N�V�����Ŏ��s���郁�\�b�h���擾����B<br>
	 * <br>
	 * @return ���s���郁�\�b�h
	 */
	public Method getMethod() {
		return method;
	}

	
	/**
	 * ���t���N�V��������I�u�W�F�N�g�𔻒肷��B<br>
	 * <br>
	 * @return true �����A���ꂽ�ꍇ�A�Ϗ���o���[�I�u�W�F�N�g���g�p����B
	 */
	public boolean isTargetValueObject() {
		return isTargetValueObject;
	}

}
