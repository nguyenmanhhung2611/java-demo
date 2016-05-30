package jp.co.transcosmos.dm3.core.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import jp.co.transcosmos.dm3.core.model.mypage.MypageUserInterface;
import jp.co.transcosmos.dm3.login.LoginUser;
import jp.co.transcosmos.dm3.login.v3.Authentication;

/**
 * マイページ、および、匿名ログインしているユーザー情報を取得する。
 * <p>
 * <pre>
 * 担当者		修正日		修正内容
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.04.16	新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 * 
 */
public class FrontLoginUserUtils {

	/** このユーティリテ の Bean ID */
	protected static String FACTORY_BEAN_ID = "loginUserUtils";

	/** 匿名ログイン用の認証クラスを設定する */
	protected Authentication anonymousAuth;

	/** マイページログイン用の認証クラスを設定する。 */
	protected Authentication mypageAuth;
	
	

	/**
	 * 匿名ログイン用の認証クラスを設定する。<br/>
	 * <br/>
	 * @param anonymousAuth 匿名ログイン用の認証クラス
	 */
	public void setAnonymousAuth(Authentication anonymousAuth) {
		this.anonymousAuth = anonymousAuth;
	}

	/**
	 * マイページログイン用の認証クラスを設定する。<br/>
	 * <br/>
	 * @param mypageAuth マイページログイン用の認証クラス
	 */
	public void setMypageAuth(Authentication mypageAuth) {
		this.mypageAuth = mypageAuth;
	}



	/**
	 * FrontLoginUserUtils のインスタンスを取得する。<br/>
	 * Spring のコンテキストから、 loginUserUtils で定義された FrontLoginUserUtils の
	 * インスタンスを取得する。<br/>
	 * 取得されるインスタンスは、FrontLoginUserUtils を継承した拡張クラスが復帰される場合もある。<br/>
	 * <br/>
	 * @param request HTTP リクエスト
	 * @return FrontLoginUserUtils、または継承して拡張したクラスのインスタンス
	 */
	public static FrontLoginUserUtils getInstance(HttpServletRequest request){
		WebApplicationContext springContext = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
		return (FrontLoginUserUtils)springContext.getBean(FrontLoginUserUtils.FACTORY_BEAN_ID);
	}

	
	
	/**
	 * 現在ログインしている匿名ログインユーザー情報を取得する。<br/>
	 * マイページにログインしている場合は、MypageUserInterface インターフェースを実装した、マイページ会員情報
	 * （通常は、MemberInfo）を復帰する。<br/>
	 * マイページにログインしていない場合は、LoginUser インターフェースを実装した、匿名ログイン情報（通常は、
	 * UserInfo）を復帰する。<br/>
	 * <br/>
	 * @param request HTTP リクエスト
	 * @param response HTTP レスポンス
	 * @return ログインユーザー情報
	 */
	public LoginUser getAnonLoginUserInfo(HttpServletRequest request, HttpServletResponse response){

		// 匿名認証用のログインユーザー取得処理を実行する。
		// このクラスのメソッドは、マイページにログイン済の場合はマイページの認証情報を復帰し、
		// マイページにログインしていない場合は匿名ログインの認証情報を復帰する。

		// 特別な事情が無い限り、匿名ログインの認証情報は存在する。
		return this.anonymousAuth.getLoggedInUser(request, response);
	}

	
	
	/**
	 * 現在ログインしているマイページのユーザー情報を取得する。<br/>
	 * マイページにログインしている場合は、MypageUserInterface インターフェースを実装した、マイページ会員情報
	 * （通常は、MemberInfo）を復帰する。<br/>
	 * マイページにログインしていない場合は、null を復帰する。<br/>
	 * <br/>
	 * @param request HTTP リクエスト
	 * @param response HTTP レスポンス
	 * @return ログインユーザー情報
	 */
	public MypageUserInterface getMypageLoginUserInfo(HttpServletRequest request, HttpServletResponse response){

		// マイページの認証情報を取得する。
		return (MypageUserInterface) this.mypageAuth.getLoggedInUser(request, response);
	}

}
