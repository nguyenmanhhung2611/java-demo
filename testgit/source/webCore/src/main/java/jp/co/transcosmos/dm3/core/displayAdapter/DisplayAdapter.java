package jp.co.transcosmos.dm3.core.displayAdapter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.transcosmos.dm3.utils.ReflectionUtils;


/**
 * DisplayAdapter �̒��ۃN���X.
 * �o���[�I�u�W�F�N�g����擾�����l��\���p�ɉ��H���� Adapter �̒��ۃN���X�B<br>
 * ���̃N���X���p�����č쐬���� DisplayAdapter �́A�Ϗ���̃��\�b�h���I�[�o�[���C�h�������ꍇ�A
 * �Ϗ���� getter �Ɠ����ŁA������ T ��n�����\�b�h��ǉ�����B<br>
 * <br>
 * �Ⴆ�΁A�Ϗ��悪�APrefMst �N���X�ŁAgetPrefName() ���\�b�h�����݂���ꍇ�ŁA<br>
 * DisplayAdapter �� getDisplayValue(prefMst, "prefName") �����s����ƁA<br>
 * DisplayAdapter�@�ɁAgetPrefName(PrefMst prefMst)�@�����݂���΂��̃��\�b�h
 * ���g�p����A������� PrefMst �́@getPrerName() ���g�p�����B<br>
 * <br>
 * �L���b�V����L���ɋ@�\������ׁADI �R���e�i�� Bean ��`���鎞�́A�V���O���g���Œ�`���鎖�B<br> 
 * @param <T>
 * @param <T> �Ϗ���o���[�I�u�W�F�N�g�̌^
 */
public abstract class DisplayAdapter {

	private static final Log log = LogFactory.getLog(DisplayAdapter.class);
	

	/** getter ���\�b�h�L���b�V�� */
	private ConcurrentMap<String, DisplayMethodCache> methodCache
			= new ConcurrentHashMap<>();



	/**
	 * �\���p���H����<br>
	 * �\�����H�Ɏg�p����郁�\�b�h�́A�w�肳�ꂽ�t�B�[���h���� getter �`���̃��\�b�h���ɕϊ����A
	 * �����Ƃ��ĈϏ���o���[�I�u�W�F�N�g�����\���ł���K�v������B<br>
	 * <br>
	 * <ol>
	 * <li>���g�̃N���X�ɊY�����郁�\�b�h�����݂��邩���`�F�b�N����B</li>
	 * <li>���݂���ꍇ�͎��g�̃��\�b�h���g�p���ĕ��A����B�i�ϊ����ꂽ���ʂ𕜋A�j</li>
	 * <li>���݂��Ȃ��ꍇ�͈Ϗ���̃��\�b�h���g�p���ĕ��A����B�i�ϊ����Ȃ����ʂ𕜋A�j</li>
	 * </ol>
	 * @param <T>
	 * @param fieldName �擾�Ώۃt�B�[���h��
	 * @return�@�\���p�I�u�W�F�N�g
	 * @throws IllegalArgumentException �s���Ȉ����Ń��\�b�h�����s 
	 * @throws IllegalAccessException �s���ȃ��\�b�h�A�N�Z�X
	 * @throws NoSuchMethodException ���\�b�h�����݂��Ȃ�
	 */
	public <T> Object getDisplayValue(T targetVo, String fieldName)
			throws IllegalAccessException, IllegalArgumentException, NoSuchMethodException {

		// getter �L���b�V���̃L�[���擾 �i�Ϗ���N���X�� + "#" + �t�B�[���h��
		String key = targetVo.getClass().getName() + "#" + fieldName;

		DisplayMethodCache methodAdapter = methodCache.computeIfAbsent(key, k -> {
			// �L���b�V���ɑ��݂��Ȃ��ꍇ�A���\�b�h�쐬���ăL���b�V���Ɋi�[����B
			try {
				// ���\�b�h���L���b�V���ɖ����ꍇ�ADisplayAdapter �̃��\�b�h���擾����B
				Method method = getAdapterMethod(fieldName, targetVo.getClass());
				return new DisplayMethodCache(method, false);

			} catch (NoSuchMethodException e) {
				// ���\�b�h�� DisplayAdapter �ɑ��݂��Ȃ��ꍇ�A�Ϗ���̃o���[�I�u�W�F�N�g���烁�\�b�h���擾����B
				try {
					Method method = ReflectionUtils.getGetterMethod(targetVo.getClass(), fieldName);
					return new DisplayMethodCache(method, true);
				} catch (Exception e1) {
					// �Ϗ���ɂ����\�b�h�����݂��Ȃ��ꍇ�͂��̂܂ܗ�O���X���[����B
					throw new RuntimeException("field name " + fieldName + " is not found.");
				}
			}
		});


		// �擾�������\�b�h�����s
		try {
			if (methodAdapter.isTargetValueObject()){
				return methodAdapter.getMethod().invoke(targetVo);
			} else {
				return methodAdapter.getMethod().invoke(this, targetVo);
			}
		} catch (InvocationTargetException e){
			Throwable cause = e.getCause();
			log.error(cause.getMessage(), cause);
			throw new RuntimeException(fieldName + " execute error " + cause.getMessage());
		}
	}



	/**
	 * DisplayAdapter �ɒ�`���ꂽ���s�\�ȃ��\�b�h���擾����B<br>
	 * <br>
	 * @param fieldName �t�B�[���h��
	 * @param voClass �Ϗ���o���[�I�u�W�F�N�g�N���X
	 * @return �擾���� Method �I�u�W�F�N�g
	 * @throws NoSuchMethodException�@�Y�����郁�\�b�h��������Ȃ�
	 */
	private Method getAdapterMethod(String fieldName, Class<?> voClass)
			throws NoSuchMethodException {

		Class<?>[] paramTypes = new Class[1];
		
		// �o���[�I�u�W�F�N�g���p������Ă���\��������̂ŁA�����̃o���[�I�u�W�F�N�g�̌^�ł͎��s�\��
		// ���\�b�h���������Ȃ��ꍇ����B
		// ���̏ꍇ�̓o���[�I�u�W�F�N�g�̐e�N���X���ċA�I�ɂ��ǂ�A���s�\�ȃ��\�b�h�����o����B
		Class<?> thisParamType = voClass;
		while ((thisParamType != null) && !thisParamType.equals(Object.class)){
			paramTypes[0] = thisParamType;
			try {
				return ReflectionUtils.getGetterMethod(this.getClass(), fieldName, paramTypes);
			} catch (NoSuchMethodException e) {
				thisParamType = thisParamType.getSuperclass();
			}
		}

		throw new NoSuchMethodException();
	}

}
