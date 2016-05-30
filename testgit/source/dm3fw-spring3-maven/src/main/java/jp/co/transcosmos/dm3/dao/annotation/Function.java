package jp.co.transcosmos.dm3.dao.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Function {

	// 使用する関数名の列挙体
	public enum FunctionName {
		COUNT, SUM, MIN, MAX, AVG, PLAIN;

		public String toFunctionString(String col) {
			
			switch (this){
			case PLAIN:
				// PLAIN を指定した場合、指定されたフィールド名のみを復帰する。
				// これは、JoinDAO 使用時に同名のフィールドを異なるフィールドで受け取る場合に使用する。
				return col;

			default:
				// 指定された関数文字列を生成して復帰する。
				return name().toLowerCase() +"(" + col +")" ;

			}
		}

	};

	String columnName();				// 集計関数の対象フィールド名。
										// フィールドのテーブル別名を指定する場合は、DaoAlias アノテーションを使用する事。

	FunctionName functionName();		//　集計関数名。
}
