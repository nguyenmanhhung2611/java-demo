package jp.co.transcosmos.dm3.token.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * <pre>
 * トークンチェックアノテーション
 * トークンチェックを行うコマンドクラスはこのアノテーションを設定する。
 * 
 * 担当者            修正日               修正内容
 * ----------------------------------------------------------------------------
 * H.Mizuno  2013.05.17  新規作成
 * 
 * </pre>
*/
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface UseTokenCheck {
	// トークンエラーが発生した場合のリダイレクト先URL
	// 設定する場合はコンテキスト名を省略して設定する。
	String value() default "/invalidToken.html";
}
