package jp.co.transcosmos.dm3.corePana.validation;

import jp.co.transcosmos.dm3.validation.Validation;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

/**
 * �����o���f�[�V����.
 * <p>
 * target �Ɏw�肳�ꂽ�����ȊO�̏ꍇ
 * �w�荀�ڂ����͂��������`�F�b�N����B<br/>
 * <br/>
 * <pre>
 * �S����		�C����		�C�����e
 * ------------ ----------- -----------------------------------------------------
 * Trans		2015.03.24	�V�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 *
 */
public class RoleValidation  implements Validation {

	/** ��������I�u�W�F�N�g */
	private Object target;


    public RoleValidation(Object target) {
        this.target = target;
    }


    @Override
    public ValidationFailure validate(String pName, Object pValue) {

    	// �������w�茠���ł͂Ȃ��ꍇ
    	if (!(Boolean) this.target) {
    		if ((pValue != null) && (!pValue.toString().equals(""))) {
    			return new ValidationFailure("role", pName, pValue, null);
    		}
    	}
    	return null;
    }

}
