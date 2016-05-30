package jp.co.transcosmos.dm3.webFront.auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.core.constant.CommonParameters;
import jp.co.transcosmos.dm3.login.LoginUser;
import jp.co.transcosmos.dm3.login.form.v3.LoginForm;
import jp.co.transcosmos.dm3.login.v3.LockSupportAuth;
import jp.co.transcosmos.dm3.utils.ServletUtils;


/**
 * マイページ認証クラス.
 * <p>
 * フレームワークが提供する機能を使用してログイン処理を行うが、以下の処理を追加している。<br/>
 * このクラスはパスワード入力ミス時のロック機能に対応した認証クラスを拡張している。<br/>
 * <ul>
 * <li>マイページにログインが成功した場合、GA 計測用の Cookie を出力する。</li>
 * </ul>
 * <br/>
 * <pre>
 * 担当者		修正日		修正内容
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.03.05	新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 * 
 */
public class PanaMypageAuth extends LockSupportAuth {

	/**
	 * Google Analytics の計測用 Cookie の Key 名<br/>
	 * Mypage にログイン済の場合、この Key 名で Cookie に書き込む。　（その際の値は 1 固定。）<br/>
	 * Mypage からログアウトした場合、この Key 名を Cookie から削除する。<br/>
	 */
	public static final String GA_MYPAGE_CHK_COOKIE_KEY = "my_coogle_analytics";

	/** 共通パラメータオブジェクト */
	private CommonParameters commonParameters;



	/**
	 * 共通パラメータオブジェクトを設定する。<br/>
	 * <br/>
	 * @param commonParameters
	 */
	public void setCommonParameters(CommonParameters commonParameters) {
		this.commonParameters = commonParameters;
	}



	/**
	 * マイページログイン処理<br/>
	 * 委譲先クラスにてログイン認証した後に、GA 計測用 Cookie を出力する。<br/>
	 * <br/>
	 * @param request HTTP リクエスト
	 * @param response HTTP レスポンス
	 * @param form 入力フォーム
	 * 
	 */
	@Override
	public LoginUser login(HttpServletRequest request, HttpServletResponse response, LoginForm form) {

		// ログイン処理
		LoginUser loginUser = super.login(request, response, form);
		
		// GA 用の Cookie を追加　（もしくは削除）
		writeGACookie(request, response, loginUser);

		// 最近見た物件、お気に入り保存の件数など、MyPage 関連 Cookie のリセット
		resetMypageCookie(request, response, loginUser);
		
		return loginUser;
	}


	
    /**
     * ログアウト処理<br/>
	 * 委譲先クラスにてログアウト処理した後に、GA 計測用 Cookie を削除する。<br/>
     * <br/>
     * @param request HTTPリクエスト
     * @param　response HTTPレスポンス
     */
	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response) {
		// ログアウト処理
		super.logout(request, response);

		// GA 用の Key を Cookie から削除する
		writeGACookie(request, response, null);
	}

	
	
	/**
	 * 認証チェック処理<br/>
	 * 認証チェック時に、認証していないと判断された場合、Cookie を削除する。<br/>
	 * （Java のセッションタイムアウト対策）<br/>
	 * <br/>
     * @param request HTTPリクエスト
     * @param　response HTTPレスポンス
	 * 
	 * @return ログインユーザー情報
	 */
	@Override
	public LoginUser checkLoggedIn(HttpServletRequest request, HttpServletResponse response){

		// 認証チェック処理
		// Cookie による自動ログインは、このタイミングで実行される。
		LoginUser loginUser = super.checkLoggedIn(request, response);

		// GA 用の Cookie を追加　（もしくは削除）
		writeGACookie(request, response, loginUser);

		return loginUser;
	}
	

	
	/**
	 * GA 計測用の Cookie を書込む　（もしくは、消去する。）<br/>
	 * 引数のユーザー情報が null 以外の場合、GA 計測用の Cookie を出力する。<br/>
	 * null の場合は、GA 計測用の Cookie を削除する。<br/>
	 * <br/>
	 * @param request HTTP リクエスト
	 * @param response　HTTP レスポンス
	 * @param loginUser 認証情報
	 */
	private void writeGACookie(HttpServletRequest request, HttpServletResponse response, LoginUser loginUser){
		
		if (loginUser != null) {
			// ログインが成功した場合、GA 用の Key を Cookie に書き込む （値は何でも良いので「１」を設定）
			// Cookie は、ブラウザーを閉じた時点で削除される様に設定する。
	    	ServletUtils.addCookie(response, request.getContextPath(), GA_MYPAGE_CHK_COOKIE_KEY, 
	    			"1", this.commonParameters.getCookieDomain());

		} else {
			// ログインが失敗した場合、GA 用の Key を Cookie から削除する
	    	ServletUtils.delCookie(response, request.getContextPath(), GA_MYPAGE_CHK_COOKIE_KEY, 
	    			this.commonParameters.getCookieDomain());

		}
	}
	


	/**
	 * お気に入り件数、最近見た物件などの、 Mypage 関連 Cookie の削除<br/>
	 * ※削除するのは、ログインが成功した場合のみ。<br/>
	 * <br/>
	 * @param request HTTP リクエスト
	 * @param response　HTTP レスポンス
	 * @param loginUser 認証情報
	 */
	private void resetMypageCookie(HttpServletRequest request, HttpServletResponse response, LoginUser loginUser){

		if (loginUser != null) {
	    	ServletUtils.delCookie(response, request.getContextPath(), "favoriteCount", 
	    			this.commonParameters.getCookieDomain());
	    	ServletUtils.delCookie(response, request.getContextPath(), "historyCount", 
	    			this.commonParameters.getCookieDomain());

		}
	}
	
}
