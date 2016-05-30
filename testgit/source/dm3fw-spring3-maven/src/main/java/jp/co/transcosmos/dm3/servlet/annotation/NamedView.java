package jp.co.transcosmos.dm3.servlet.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * <pre>
 * ���� URL �}�b�s���O�Ή�
 * NamedView ���`����A�m�e�[�V����
 * NamedView ��ݒ肷��K�v������ꍇ�ɂ��̃A�m�e�[�V������ݒ肷��B
 *
 * �S����            �C����               �C�����e
 * ----------------------------------------------------------------------------
 * H.Mizuno  2013.04.23  �V�K�쐬
 *
 * </pre>
*/
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface NamedView {
	String[] value();
}
