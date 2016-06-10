package jp.co.transcosmos.dm3.login.form.v3;

import jp.co.transcosmos.dm3.validation.Validateable;


/**
 * <pre>
 * Ver 3 認証対応用フォームのインターフェース
 * 標準の DefaultLoginForm を用意しているが、バリデーションチェック等、カスタマイズする場合は、
 * このインターフェースを実装したクラスを作成し、自由に差し替える事ができる。
 * フォームクラスを差し替える場合は、LoginCommand の　LoginForm　プロパティに新しいフォーム
 * クラスを設定する。
 *
 * 担当者            修正日               修正内容
 * ----------------------------------------------------------------------------
 * H.Mizuno  2013.04.05  新規作成
 *
 * </pre>
*/
public interface LoginForm extends Validateable {

	// ログインIDの取得
	public String getLoginID();

	// パスワードの取得
	public String getPassword();

}
