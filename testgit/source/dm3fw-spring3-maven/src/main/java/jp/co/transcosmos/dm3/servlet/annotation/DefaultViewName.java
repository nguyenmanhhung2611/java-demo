package jp.co.transcosmos.dm3.servlet.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <pre>
 * ���� URL �}�b�s���O�Ή�
 * DefaultViewName ���`����A�m�e�[�V����
 * DefaultViewName �ɌŗL�̐ݒ�l���g�p�������ꍇ�A���̃A�m�e�[�V�����Őݒ肷��B
 * �ݒ肵�Ȃ��ꍇ�AURL ���� JSP �t�@�C�����𐶐����� DefaultViewName �Ƃ��Ďg�p�����B
 *
 * �S����            �C����               �C�����e
 * ----------------------------------------------------------------------------
 * H.Mizuno  2013.04.23  �V�K�쐬
 *
 * </pre>
*/
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface DefaultViewName {
	String value();
}
