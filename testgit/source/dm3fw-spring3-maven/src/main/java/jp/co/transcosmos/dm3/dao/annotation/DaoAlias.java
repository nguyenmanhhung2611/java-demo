package jp.co.transcosmos.dm3.dao.annotation;

/**
 * GroupByDAO �Ŏg�p����o���[�I�u�W�F�N�g�� ��ɊY������e�[�u���ʖ����w�肷��ꍇ�A
 * ���̃A�m�e�[�V�������g�p����B
 */
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DaoAlias {
	String value();		// �t�B�[���h�̃e�[�u���ʖ�
}
