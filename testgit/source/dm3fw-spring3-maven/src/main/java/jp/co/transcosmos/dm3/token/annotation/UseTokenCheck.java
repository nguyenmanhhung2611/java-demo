package jp.co.transcosmos.dm3.token.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * <pre>
 * �g�[�N���`�F�b�N�A�m�e�[�V����
 * �g�[�N���`�F�b�N���s���R�}���h�N���X�͂��̃A�m�e�[�V������ݒ肷��B
 * 
 * �S����            �C����               �C�����e
 * ----------------------------------------------------------------------------
 * H.Mizuno  2013.05.17  �V�K�쐬
 * 
 * </pre>
*/
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface UseTokenCheck {
	// �g�[�N���G���[�����������ꍇ�̃��_�C���N�g��URL
	// �ݒ肷��ꍇ�̓R���e�L�X�g�����ȗ����Đݒ肷��B
	String value() default "/invalidToken.html";
}
