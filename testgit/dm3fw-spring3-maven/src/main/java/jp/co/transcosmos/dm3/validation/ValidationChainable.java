package jp.co.transcosmos.dm3.validation;

import java.util.List;

/**
 * ValidationArrayChain の追加に伴い、ValidationChain の共通インターフェース
 * として追加。
 * @author H.Mizuno
 *
 */
public interface ValidationChainable {
	
	public void addValidation(Validation validation);
	
	public boolean validate(List<ValidationFailure> outputErrors);
}
