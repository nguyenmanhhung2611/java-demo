package jp.co.transcosmos.dm3.login.v3;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.login.LoginUser;
import jp.co.transcosmos.dm3.login.UserRoleSet;
import jp.co.transcosmos.dm3.login.form.v3.LoginForm;

/**
 * <pre>
 * V3 認証対応
 * 認証処理を行うサービスが実装するインターフェース
 * カスタマイズした認証処理を作成する場合は、このインターフェースを実装する必要がある。
 *
 * 担当者            修正日               修正内容
 * ----------------------------------------------------------------------------
 * H.Mizuno  2013.04.04  新規作成
 * H.Mizuno  2013.04.11  認証チェックと、ユーザー情報取得を分離
 * 
 * </pre>
*/
public interface Authentication {

	// 手動ログイン処理。　成功したら、ログインユーザーの、LoginUser を復帰する事。
	public LoginUser login(HttpServletRequest request, HttpServletResponse response, LoginForm form);

	// ログアウト処理
	public void logout(HttpServletRequest request, HttpServletResponse response);

	// ログイン状態の判定処理
	// LoginCheckFilter　は、このメソッドを使用して認証済チェックを行う。　よって、自動ログインなどを
	// 実装する場合はこちらの処理に実装する。
	// ログイン状態の場合、ログインユーザーの情報を復帰する。
	// ログインしていない場合、null を復帰する。
	public LoginUser checkLoggedIn(HttpServletRequest request, HttpServletResponse response);
	
	
	// ログインユーザーの情報を取得する。
	// 通常のログイン情報を取得する処理。　通常のユーザー情報取得はこちらを使用している。
	// ログイン状態の場合、ログインユーザーの情報を復帰する。
	// ログインしていない場合、null を復帰する。
	public LoginUser getLoggedInUser(HttpServletRequest request, HttpServletResponse response);
	
	
	// ログインしている状態の場合、ログインユーザーのロール情報を復帰する。
	// HasRoleCheckCommand は、このメソッドの戻り値を見て処理を振り分ける。
	// 但し、HasRoleCheckCommand は null を受取ると、ロールチェック対象外とみなし、認証OKにする。
	// エラーにするには空の　UserRoleSet　オブジェクトを復帰する必要がある。
	public UserRoleSet getLoggedInUserRole(HttpServletRequest request, HttpServletResponse response);

}
