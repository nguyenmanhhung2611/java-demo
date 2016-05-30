package jp.co.transcosmos.dm3.validation;

import jp.co.transcosmos.dm3.lookup.CodeLookupManager;

/**
 * �w�肳�ꂽ codeLookup �ɊY������l�����݂��邩�`�F�b�N����B<br/>
 * <br/>
 * @author H.Mizuno
 *
 */
public class CodeLookupValidation implements Validation {

	/** ���ʃR�[�h�ϊ����� */
	private CodeLookupManager codeLookupManager;

	/** code lookup �� */
	private String lookupName;

	public CodeLookupValidation(CodeLookupManager codeLookupManager, String lookupName) {
		this.codeLookupManager = codeLookupManager;
		this.lookupName = lookupName;
	}
	
	@Override
	public ValidationFailure validate(String name, Object value) {

		// �f�[�^����̏ꍇ�A�o���f�[�V�����n�j
		if ((value == null) || value.equals("")) {
            return null;
        }

		// ���ʃR�[�h�ϊ���������l���擾����B
		if (this.codeLookupManager.lookupValue(this.lookupName, (String)value) == null){
			return new ValidationFailure("codeNotFound", name, value, null);
		}

		return null;
	}

}
