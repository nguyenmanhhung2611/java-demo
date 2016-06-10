package jp.co.transcosmos.dm3.servlet.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <pre>
 * 自動 URL マッピング対応
 * DefaultViewName を定義するアノテーション
 * DefaultViewName に固有の設定値を使用したい場合、このアノテーションで設定する。
 * 設定しない場合、URL から JSP ファイル名を生成して DefaultViewName として使用される。
 *
 * 担当者            修正日               修正内容
 * ----------------------------------------------------------------------------
 * H.Mizuno  2013.04.23  新規作成
 *
 * </pre>
*/
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface DefaultViewName {
	String value();
}
