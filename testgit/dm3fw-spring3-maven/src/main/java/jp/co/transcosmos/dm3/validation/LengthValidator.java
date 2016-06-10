package jp.co.transcosmos.dm3.validation;

import jp.co.transcosmos.dm3.validation.Validation;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

/**
 * <pre>
 * ������Validation�N���X
 * ���͏�񂪕����񒷂Ɠ������`�F�b�N���܂��B
 * ���o�C�g���`�F�b�N�ł͂���܂���
 * 
 * �S����       �C����      �C�����e
 * ------------ ----------- -----------------------------------------------------
 * �����@�ޒÍ] 2007.01.24  �V�K�쐬
 *
 * </pre>
*/
public class LengthValidator implements Validation {
    private int length;

    public  LengthValidator(int length) {
        this.length = length;
    }

    public ValidationFailure validate(String name, Object value) {
        if (value == null) {
            return null;
        }

        String valueStr = value.toString();
        if (valueStr.length() != this.length) {
            return new ValidationFailure("length", name, value, new String[] {"" + this.length});
        }
        return null;
    }
}
