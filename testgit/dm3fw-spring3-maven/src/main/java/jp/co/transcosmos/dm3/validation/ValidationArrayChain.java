package jp.co.transcosmos.dm3.validation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jp.co.transcosmos.dm3.validation.Validation;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

/**
 * <pre>
 * �z��ValidationChain�N���X
 * �����̔z���Ή���Validator��p���āA
 * �z��̊e���e���`�F�b�N����ValidationChain�ł��B
 * 
 * �S����       �C����      �C�����e
 * ------------ ----------- -----------------------------------------------------
 * �ד��@��		2006.01.12	�V�K�쐬
 * H.Mizuno		2014.10.22	�ϐ��Č��p�Ɏg�p���Ă����N���X���t���[�����[�N�ō̗p�B
 * 							�܂��A������ CalidationChain �Ƌ��p����ׁA�C���^�[�t�F�[�X��ǉ�
 *
 * </pre>
 */
public class ValidationArrayChain implements ValidationChainable {
    private String name;
    private Object[] values;
    private List<Validation> validations;

    public ValidationArrayChain(String name, Object[] values) {
        this.name = name;
        this.values = values;
    }
    
    @Override
    public void addValidation(Validation validation) {
        if (this.validations == null) {
            this.validations = new ArrayList<Validation>();
        }
        this.validations.add(validation);
    }

    @Override
    public boolean validate(List<ValidationFailure> outputErrors) {
        if (this.validations == null) {
            return true;
        }
        
        int startSize = outputErrors.size();
        
        int len = values.length;
        for (int arrIndex = 0; arrIndex < len; arrIndex++) {
        	Object value = this.values[arrIndex];
        	
	        for (Iterator<Validation> i = this.validations.iterator(); i.hasNext(); ) {
	            ValidationFailure error = ((Validation) i.next()).validate(this.name, value);
	            if (error != null) {
                	String extraInfo[] = error.getExtraInfo();
                	if (extraInfo == null) {
                		extraInfo = new String[0];
                	}
                	int extraInfoLen = extraInfo.length;
                	int newExtraInfoLen = extraInfoLen + 1;
                	String newExtraInfo[] = new String[newExtraInfoLen];
	                	
                	newExtraInfo[0] = Integer.toString(arrIndex + 1);
                	System.arraycopy(extraInfo, 0, newExtraInfo, 1, extraInfoLen);
	                	
                	ValidationFailure newError = new ValidationFailure(error.getType() + "Arr", error.getName(), error.getValue(), newExtraInfo);
                    outputErrors.add(newError);
	            }
	        }
        }
        return startSize == outputErrors.size();
	}
}
