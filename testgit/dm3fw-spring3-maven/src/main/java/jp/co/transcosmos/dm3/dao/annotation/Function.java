package jp.co.transcosmos.dm3.dao.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Function {

	// �g�p����֐����̗񋓑�
	public enum FunctionName {
		COUNT, SUM, MIN, MAX, AVG, PLAIN;

		public String toFunctionString(String col) {
			
			switch (this){
			case PLAIN:
				// PLAIN ���w�肵���ꍇ�A�w�肳�ꂽ�t�B�[���h���݂̂𕜋A����B
				// ����́AJoinDAO �g�p���ɓ����̃t�B�[���h���قȂ�t�B�[���h�Ŏ󂯎��ꍇ�Ɏg�p����B
				return col;

			default:
				// �w�肳�ꂽ�֐�������𐶐����ĕ��A����B
				return name().toLowerCase() +"(" + col +")" ;

			}
		}

	};

	String columnName();				// �W�v�֐��̑Ώۃt�B�[���h���B
										// �t�B�[���h�̃e�[�u���ʖ����w�肷��ꍇ�́ADaoAlias �A�m�e�[�V�������g�p���鎖�B

	FunctionName functionName();		//�@�W�v�֐����B
}
