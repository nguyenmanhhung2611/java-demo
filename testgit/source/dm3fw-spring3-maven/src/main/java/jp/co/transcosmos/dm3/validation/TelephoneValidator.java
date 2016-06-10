package jp.co.transcosmos.dm3.validation;

import jp.co.transcosmos.dm3.validation.Validation;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

/**
 * <pre>
 * �d�b�ԍ�Validation�N���X
 * ���͂��ꂽ�d�b�ԍ��̃`�F�b�N�����܂��B
 * �Enull�A�󔒕����̓`�F�b�N�ʉ�
 * �E���p�����A���p�n�C�t���݂̂̕�����̏ꍇ�̓`�F�b�N�ʉ�
 * �E���p�����A���p�n�C�t���ȊO�̕����񂪓����Ă����ꍇ�̓G���[
 * �E�����̃`�F�b�N�͂��܂���B�K�v�ɉ�����MaxLengthValidation�A
 * �@MinLengthValidatin�Ƒg�ݍ��킹�Ďg�p���Ă��������B
 * 
 * �S����       �C����      �C�����e
 * ------------ ----------- -----------------------------------------------------
 * �ד��@��     2006.12.22  �V�K�쐬
 *
 * </pre>
*/
public class TelephoneValidator implements Validation {
	public ValidationFailure validate(String name, Object value) {
		
		// null�A�󕶎��̏ꍇ�͒ʉ�
		if ((value == null) || value.equals("")) {
			return null;
		}
		
		String telNo = value.toString();
		int len = telNo.length();
		for (int i = 0; i < len; i++) {
			char ch = telNo.charAt(i);
			
			// �e�������������n�C�t���̏ꍇ�͒ʉ�
			if (('0' <= ch && ch <= '9') || ch == '-') {
				continue;
			}
			
			// �����E�n�C�t���ȊO�̕����������Ă����ꍇ�̓G���[
			return new ValidationFailure("telephone", name, value, null);
		}
		
		// ���ׂĂ̕����������E�n�C�t���̏ꍇ�͒ʉ�
		return null;
	}
}
