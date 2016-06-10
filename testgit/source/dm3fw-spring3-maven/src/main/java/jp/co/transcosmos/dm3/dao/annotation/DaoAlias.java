package jp.co.transcosmos.dm3.dao.annotation;

/**
 * GroupByDAO で使用するバリーオブジェクトで 列に該当するテーブル別名を指定する場合、
 * このアノテーションを使用する。
 */
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DaoAlias {
	String value();		// フィールドのテーブル別名
}
