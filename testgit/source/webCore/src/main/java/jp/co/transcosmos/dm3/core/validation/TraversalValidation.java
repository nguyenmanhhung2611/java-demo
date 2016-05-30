package jp.co.transcosmos.dm3.core.validation;

import jp.co.transcosmos.dm3.validation.Validation;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

/**
 * �f�B���N�g���g���o�[�T���̋��ЂƂȂ�L�������̎g�p���`�F�b�N����.
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
public class TraversalValidation implements Validation {

	/** ���p�֎~������ */
	private String[] badStrings = new String[]{"\\","/",":","../"};



	@Override
	public ValidationFailure validate(String name, Object value) {

		// �󈽂���null�̏ꍇ�A����I��
		if (value == null || "".equals(value.toString())) {
			return null;
		}


		for (String bad : badStrings){
			if (value.toString().indexOf(bad) >= 0){
				return new ValidationFailure("traversal", name, value, null);
			}
		}

		return null;
	}

	
	
	
}
