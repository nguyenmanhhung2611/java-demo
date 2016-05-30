package jp.co.transcosmos.dm3.validation;

import java.util.List;

/**
 * ValidationArrayChain �̒ǉ��ɔ����AValidationChain �̋��ʃC���^�[�t�F�[�X
 * �Ƃ��Ēǉ��B
 * @author H.Mizuno
 *
 */
public interface ValidationChainable {
	
	public void addValidation(Validation validation);
	
	public boolean validate(List<ValidationFailure> outputErrors);
}
