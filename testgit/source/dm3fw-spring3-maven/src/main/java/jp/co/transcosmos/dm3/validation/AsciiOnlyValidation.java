package jp.co.transcosmos.dm3.validation;

import java.io.UnsupportedEncodingException;

/**
 * ���p�p���L�������`�F�b�N<br/>
 * <br/>
 * @author H.Mizuno
 *
 */
public class AsciiOnlyValidation implements Validation {

	@Override
	public ValidationFailure validate(String name, Object value) {

		// �f�[�^����̏ꍇ�A�o���f�[�V�����n�j
		if ((value == null) || value.equals("")) {
            return null;
        }

		// �������ƁA�o�C�g�����r���Ĕ��肷��B
		// sjis �ŏ��������Ɣ��p�J�^�J�i�����ʂł��Ȃ��̂ŁAutf8 �𖾎��I�Ɏw�肷��B
		int strLen = ((String)value).length();
		int byteLen;
		try {
			byteLen = ((String)value).getBytes("utf8").length;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new RuntimeException("invalid enocde type");
		}

		if (strLen != byteLen) {
			return new ValidationFailure("asciiOnly", name, value, null);
		}

		return null;
	}

}
