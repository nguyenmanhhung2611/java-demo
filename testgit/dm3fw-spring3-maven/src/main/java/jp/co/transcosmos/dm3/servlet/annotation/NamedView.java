package jp.co.transcosmos.dm3.servlet.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * <pre>
 * 自動 URL マッピング対応
 * NamedView を定義するアノテーション
 * NamedView を設定する必要がある場合にこのアノテーションを設定する。
 *
 * 担当者            修正日               修正内容
 * ----------------------------------------------------------------------------
 * H.Mizuno  2013.04.23  新規作成
 *
 * </pre>
*/
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface NamedView {
	String[] value();
}
