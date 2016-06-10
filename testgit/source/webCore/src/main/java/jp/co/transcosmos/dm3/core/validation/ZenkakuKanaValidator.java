package jp.co.transcosmos.dm3.core.validation;

import jp.co.transcosmos.dm3.validation.Validation;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

/**
 * <pre>
 * �S�p�J�iValidation�N���X
 * ���͂��ꂽ�������S�p�J�i���`�F�b�N�����܂��B
 * �E�S�p�J�i(0x30A1�`0x30F6)�̏ꍇ�A�`�F�b�N�ʉ�
 * �E�������̏ꍇ�A�`�F�b�N�ʉ�
 * 
 * �S����       �C����      �C�����e
 * ------------ ----------- -----------------------------------------------------
 * ����@�N��   2006.12.26  �V�K�쐬
 *
 * </pre>
*/

public class ZenkakuKanaValidator implements Validation {

	private static final char ALLOWED_CHARS[] = {'�]','�|','�['};

	public ValidationFailure validate(String name, Object value) {
		if ((value == null) || value.equals("")) {
			return null;
		}
		char[] valueStr = value.toString().toCharArray();
		for (int n = 0; n < valueStr.length; n++) {
			if ((valueStr[n] >= 0x30A1) && (valueStr[n] <= 0x30F6)) {
				continue;
			}
			
			boolean valid = false;
			for (int i = 0; i < ALLOWED_CHARS.length; i++) {
				if (valueStr[n] == ALLOWED_CHARS[i]) {
					valid = true;
					break;
				}
			}
			if (valid) {
				continue;
			}
			
			return new ValidationFailure("zenkakuKana", name, value, null);
		}
		return  null;
	}
	
}
