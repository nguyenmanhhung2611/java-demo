package jp.co.transcosmos.dm3.core.validation;

import jp.co.transcosmos.dm3.validation.Validation;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

/**
 * �t�@�C�����̊g���q���w�肳�ꂽ�g���q�����`�F�b�N����.
 * <p>
 * <pre>
 * �S����		�C����		�C�����e
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.04.07	�V�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 * 
 */
public class FileExtensionValidation implements Validation {

	/** ���͂�������g���q�i�s���I�h�t���j�@ */
	private String[] extList = null;


	
	/**
	 * �f�t�H���g�R���X�g���N�^<br/>
	 * <br/>
	 * 
	 */
	public FileExtensionValidation() {

	}

	/**
	 * �R���X�g���N�^<br/>
	 * ���͂�������g���q�i�s���I�h�t���j���w�肷��B<br/>
	 * <br/>
	 * @param extList�@���͂�������g���q�i�s���I�h�t���j
	 */
	public FileExtensionValidation(String[] extList) {
		this.extList = extList;
	}


	/**
	 * �o���f�[�V���������s<br/>
	 * <br/>
	 * @param name �t�B�[���h��
	 * @param ���͒l
	 * 
	 * @return �G���[�������́@ValidationFailure�@�̃C���X�^���X�𕜋A
	 */
	@Override
	public ValidationFailure validate(String name, Object value) {

		// �󈽂���null�̏ꍇ�A����I��
		if (value == null || "".equals(value.toString())) {
			return null;
		}

		// �����ꂽ�g���q���J��Ԃ��`�F�b�N���s���B
		// ���̍ہA�啶���A�������̈Ⴂ�͖�������B
		for (String ext : extList){
			if (value.toString().toUpperCase().endsWith(ext.toUpperCase())){
				return null;
			}
		}

		// extraInfo �́A�z��̐擪�������Ă���Ȃ��̂ŁA������g���q���J���}��؂�ŉ��H����B
		StringBuffer buff = new StringBuffer(256);
		for (String ext : extList){
			buff.append("," + ext);
		}

		return new ValidationFailure("fileExtNG", name, value, new String[]{buff.toString().substring(1)});
	}
}
