package jp.co.transcosmos.dm3.core.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.core.model.adminUser.AdminUserInterface;
import jp.co.transcosmos.dm3.login.UserRoleSet;
import jp.co.transcosmos.dm3.login.v3.Authentication;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * 管理サイトへログインしているユーザー情報を取得する.
 * <p>
 * メニュー部やヘッダー部など、一部の共通 JSP では直接セッションからログインユーザー情報を参照している
 * 場合があるが、フレームワークの認証処理を変更した場合に対応できないので、特別な理由が無い限り、
 * このクラスを使用して値を取得する事。<br/>
 * <p>
 * <pre>
 * 担当者		修正日		修正内容
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.02.20	新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 * 
 */
public class AdminLoginUserUtils {

	/** このユーティリテ の Bean ID */
	protected static String FACTORY_BEAN_ID = "loginUserUtils";

	/** フレームワークの認証処理 */
	protected Authentication authentication;
	
	
	
	/**
	 * フレームワークの認証処理を設定する。<br/>
	 * <br/>
	 * @param authentication フレームワークの認証処理
	 */
	public void setAuthentication(Authentication authentication) {
		this.authentication = authentication;
	}



	/**
	 * AdminLoginUserUtils のインスタンスを取得する。<br/>
	 * Spring のコンテキストから、 loginUserUtils で定義された AdminLoginUserUtils の
	 * インスタンスを取得する。<br/>
	 * 取得されるインスタンスは、AdminLoginUserUtils を継承した拡張クラスが復帰される場合もある。<br/>
	 * <br/>
	 * @param request HTTP リクエスト
	 * @return AdminLoginUserUtils、または継承して拡張したクラスのインスタンス
	 */
	public static AdminLoginUserUtils getInstance(HttpServletRequest request){
		WebApplicationContext springContext = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
		return (AdminLoginUserUtils)springContext.getBean(AdminLoginUserUtils.FACTORY_BEAN_ID);
	}

	
	
	/**
	 * 現在ログインしているユーザー情報を取得する。<br/>
	 * <br/>
	 * @param request HTTP リクエスト
	 * @param response HTTP レスポンス
	 * @return ログインユーザー情報
	 */
	public AdminUserInterface getLoginUserInfo(HttpServletRequest request, HttpServletResponse response){
		return (AdminUserInterface)this.authentication.getLoggedInUser(request, response);
	}
	
	
	
	/**
	 * 現在ログインしているユーザーの権限情報を取得する。<br/>
	 * 権限があるかをチェックするには、戻り値の UserRoleSet オブジェクトの hasRole(ロール名)を実行する。<br/>
	 * 権限がある場合は true を復帰する。<br/>
	 * <br/>
	 * @param request HTTP リクエスト
	 * @param response HTTP レスポンス
	 * 
	 * @return ログインユーザーロール情報
	 */
	public UserRoleSet getLoginUserRole(HttpServletRequest request, HttpServletResponse response){
		return this.authentication.getLoggedInUserRole(request, response);
	}

}
