package jp.co.transcosmos.dm3.core.validation;

import jp.co.transcosmos.dm3.validation.Validation;
import jp.co.transcosmos.dm3.validation.ValidationFailure;


/**
 * ��r�o���f�[�V����.
 * <p>
 * target �Ɏw�肳�ꂽ�l�ƈ�v���邩���`�F�b�N����B<br/>
 * <br/>
 * <pre>
 * �S����		�C����		�C�����e
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.02.23	Shamaison ���Q�l�ɐV�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 * 
 */
public class CompareValidation implements Validation {

	/** ��r�ΏۃI�u�W�F�N�g */
	private Object target;
	/** �Ώۃ��x�� �i�u�E�E�E�̒l�ƈ�v���܂���v�̃��b�Z�[�W�Ɏg�p����l�j*/
	private String targetLabel = null;
	/** true �̏ꍇ�A�ے蔻�� */
	private boolean negative = false;
	
	
	
	/**
	 * �R���X�g���N�^�[<br/>
	 * <br/>
	 * @param target ��r�ΏۃI�u�W�F�N�g
	 * @param targetLabel�@�G���[���b�Z�[�W�ɕ\������A��r�Ώۍ��ږ��i�܂��͒l�j
	 */
	public CompareValidation(Object target, String targetLabel) {
        this.target = target;
        this.targetLabel = targetLabel;
    }

	/**
	 * �R���X�g���N�^�[<br/>
	 * <br/>
	 * @param target ��r�ΏۃI�u�W�F�N�g
	 * @param targetLabel�@�G���[���b�Z�[�W�ɕ\������A��r�Ώۍ��ږ��i�܂��͒l�j
	 * @param negative�@true �̏ꍇ�ے蔻�� �i�f�t�H���g false�j
	 */
    public CompareValidation(Object target, String targetLabel, boolean negative) {
        this.target = target;
        this.targetLabel = targetLabel;
        this.negative = negative;
    }



    @Override
    public ValidationFailure validate(String pName, Object pValue) {
    	// �����͎��� true �Ƃ���B
    	if ((pValue == null) || pValue.equals("")) {
    		return null;
        }

    	if (!this.negative) {
        	if (pValue.equals(this.target)) return null;
        	return new ValidationFailure("compare", pName, pValue, new String[]{this.targetLabel});
    	
    	} else {
        	if (!pValue.equals(this.target)) return null;
        	return new ValidationFailure("notCompare", pName, pValue, new String[]{this.targetLabel});
    	}


    }

}
