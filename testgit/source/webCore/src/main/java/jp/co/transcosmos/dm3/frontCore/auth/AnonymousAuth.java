package jp.co.transcosmos.dm3.frontCore.auth;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.transcosmos.dm3.core.constant.CommonParameters;
import jp.co.transcosmos.dm3.core.model.MypageUserManage;
import jp.co.transcosmos.dm3.login.LoginUser;
import jp.co.transcosmos.dm3.login.UserRoleSet;
import jp.co.transcosmos.dm3.login.form.v3.DefaultLoginForm;
import jp.co.transcosmos.dm3.login.form.v3.LoginForm;
import jp.co.transcosmos.dm3.login.v3.Authentication;
import jp.co.transcosmos.dm3.login.v3.DefaultAuth;
import jp.co.transcosmos.dm3.utils.CipherUtil;
import jp.co.transcosmos.dm3.utils.ServletUtils;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;


/**
 * フロントサイトの匿名ログイン処理.
 * <p>
 * <pre>
 * 担当者		修正日		修正内容
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.04.17	新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 * 
 */
public class AnonymousAuth extends DefaultAuth {

	private static final Log log = LogFactory.getLog(AnonymousAuth.class);
	
	/** マイページ用認証クラス */
	protected Authentication mypageAuth;
	/** 共通パラメータオブジェクト */
	protected CommonParameters commonParameters;
	/** マイページ会員 Model */
	protected MypageUserManage mypageUserManage;
	/** 匿名ログインのユーザーIDを格納しいている Cookie 名 */
	protected String anonymousUidCookieName = "anonUserId";
	/** Cookie 有効期限 （デフォルト１０年）*/
	protected Integer cookieExpires = 60 * 60 * 24 * 365 * 10 ;
	/** Cookie セキュア属性 （デフォルト false） */
	protected boolean cookieSequre = false;

	/** 暗号化クラス */
	private static final CipherUtil userIdCookieCipher = new CipherUtil(";tj1t89483jhzffH"); 



	/**
	 * マイページ用認証クラスを設定する。<br/>
	 * <br/>
	 * @param mypageAuth マイページ用認証クラス
	 */
	public void setMypageAuth(Authentication mypageAuth) {
		this.mypageAuth = mypageAuth;
	}

	/**
	 * 共通パラメータオブジェクトを設定する。<br/>
	 * <br/>
	 * @param commonParameters
	 */
	public void setCommonParameters(CommonParameters commonParameters) {
		this.commonParameters = commonParameters;
	}

	/**
	 * マイページ会員用 Model　を設定する。<br/>
	 * <br/>
	 * @param mypageUserManage マイページ会員用 Model
	 */
	public void setMypageUserManage(MypageUserManage mypageUserManage) {
		this.mypageUserManage = mypageUserManage;
	}

	/**
	 * 匿名ログインのユーザーIDを格納しいている Cookie 名<br/>
	 * <br/>
	 * @param anonymousUidCookieName 匿名ログインのユーザーIDを格納しいている Cookie 名
	 */
	public void setAnonymousUidCookieName(String anonymousUidCookieName) {
		this.anonymousUidCookieName = anonymousUidCookieName;
	}

	/**
	 * Cookie 有効期限を設定する。<br/>
	 * <br/>
	 * @param cookieExpires Cookie 有効期限
	 */
	public void setCookieExpires(Integer cookieExpires) {
		this.cookieExpires = cookieExpires;
	}

	/**
	 * Cookie のセキュア属性を設定する。（デフォルト false）<br/>
	 * <br/>
	 * @param cookieSequre Cookie のセキュア属性
	 */
	public void setCookieSequre(boolean cookieSequre) {
		this.cookieSequre = cookieSequre;
	}



	/**
	 * 匿名ログイン処理<br/>
	 * Cookie から匿名ログイン用の ID を取得し、取得できた場合はその ID で匿名認証を試みる。<br/>
	 * Cookie が無い場合や、認証に失敗した場合は新たにユーザーID を登録してから匿名認証を行う。<br/>
	 * ログインが成功した場合は匿名ログイン用の ID を Cookie へ書き込む。<br/>
	 * <br/>
	 * @param request HTTP リクエスト
	 * @param response HTTP レスポンス
	 * 
	 * @return 認証情報。　認証済でない場合は null
	 */
	@Override
	public LoginUser login(HttpServletRequest request, HttpServletResponse response, LoginForm form) {

		// 匿名ログインの場合、LoginForm が渡される事はない。
		// Cookie からユーザーID を取得して匿名認証を行う。
		String userId = readUserIdFromCookie(request, response);

		if (userId != null){
			DefaultLoginForm inputForm = new DefaultLoginForm();
			inputForm.setLoginID(userId);

			// フレームワークの機能を使用してログイン処理
			LoginUser loginUser = super.login(request, response, inputForm);

			// Cookie から取得したユーザーID で認証が成功した場合、ユーザー情報を復帰する。
			// また、Cookie の有効期限を延長する為、匿名ログイン時は Cookie に値を書き込む
			if (loginUser != null) {
				writeUserIdToCookie(request, response, loginUser);
				return loginUser;
			}
		}


		// Cookie から値が取得出来ない場合、もしくは、Cookie から取得した値で認証に失敗した場合は、
		// 新たな UserId を採番して DB に登録する。　その後、ログイン処理を実行してセッション情報を作成する。
		try {
			LoginUser loginUser = this.mypageUserManage.addLoginID(null);
			DefaultLoginForm inputForm = new DefaultLoginForm();
			inputForm.setLoginID(loginUser.getLoginId());

			// フレームワークの機能を使用してログイン処理
			loginUser = super.login(request, response, inputForm);

			// Cookie に UID を設定する。
			writeUserIdToCookie(request, response, loginUser);

			return loginUser;

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new RuntimeException(e.getMessage());
		}

	}


	
	/**
	 * 匿名ログインの認証チェック処理<br/>
	 * マイページにログイン済の場合はマイページの認証情報をログイン情報として復帰する。<br/>
	 * この場合、戻り値は MypageUserInterface の実装クラス（通常は、MemberInfo）になる。<br/>
	 * <br/>
	 * マイページにログインしていない場合、匿名ログインの認証チェックを行う。 もし、匿名ログイン済でない場合、
	 * 匿名ログインを行ってから認証情報を復帰する。　その場合、戻り値は LoginUser の実装クラス
	 * （通常は、UserInfo）になる。<br/>
	 * <br/>
	 * このメソッドの場合、マイページの自動ログインが設定されていいる場合、自動ログインが発生する。<br/>
	 * <br/>
	 * @param request HTTP リクエスト
	 * @param response HTTP レスポンス
	 * 
	 * @return 認証情報。　認証済でない場合は null
	 */
	@Override
	public LoginUser checkLoggedIn(HttpServletRequest request, HttpServletResponse response) {

		// マイページの認証チェックを実行し、ログイン済かをチェックする。
		// 認証情報が取得できた場合はその値を復帰する。
		LoginUser loginUser = this.mypageAuth.checkLoggedIn(request, response);
		if (loginUser != null){
			// Cookie の匿名ログインの値を取得する。
			// 取得出来ない場合や、ID が異なる場合は Cookie の値を変更する。
			// （次回来訪時に、マイページとは別の ID を匿名ログインの ID としてしまう為。）
			String userId = readUserIdFromCookie(request, response);
			if (userId == null || !userId.equals(loginUser.getUserId())){
				writeUserIdToCookie(request, response, loginUser);
			}
			return loginUser;
		}


		// 匿名認証が完了しているかをチェックする。
		loginUser = super.checkLoggedIn(request, response);
		if (loginUser != null) return loginUser;


		// 匿名ログインを実行する。
		return login(request, response, null);
	}



	/**
	 * ログイン中のユーザー情報を復帰する。<br/>
	 * マイページのログイン情報を取得して復帰する。<br/>
	 * マイページのログイン情報が取得出来ない場合、匿名ログインのログイン情報を取得する。<br/>
	 * <br/>
	 * @param request HTTP リクエスト
	 * @param response HTTP レスポンス
	 * 
	 * @return 認証情報。　認証済でない場合は null
	 */
	@Override
	public LoginUser getLoggedInUser(HttpServletRequest request, HttpServletResponse response) {

		// マイページの認証情報を取得する。
		// 認証情報が取得できた場合はマイページの認証情報を復帰する。
		LoginUser loginUser = this.mypageAuth.getLoggedInUser(request, response);
		if (loginUser != null) return loginUser;

		// 匿名認証のログイン情報を取得する。
		return super.getLoggedInUser(request, response);
	}


	
    /**
     * ログインユーザーのロール情報を取得<br/>
     * マイページにログイン済であれば、マイページのロール情報を復帰する。<br/>
     * マイページにログイン済でない場合、匿名ログインのロール情報を復帰する。
     * <br/>
     * @param request HTTPリクエスト
     * @param　response HTTPレスポンス
     * @return ロール名のリストを復帰する。
     */
	@Override
	public UserRoleSet getLoggedInUserRole(HttpServletRequest request, HttpServletResponse response) {

		// マイページのロール情報取得処理を実行する。
		// 取得できた場合はその値を復帰する。
		UserRoleSet roleSet = this.mypageAuth.getLoggedInUserRole(request, response);
		if (roleSet != null) return roleSet;

		// 匿名ログインのロール情報を取得する。
		return super.getLoggedInUserRole(request, response);
	}

	

	/**
	 * ログイン情報のセッション格納処理<br/>
	 * <br/>
	 * @param request HTTP リクエスト
	 * @param user ログインユーザー情報
	 */
	@Override
	protected void setLoggedInUser(HttpServletRequest request, LoginUser user) {
		// 継承元の処理を利用してセッション情報にログイン情報を書き込む
		super.setLoggedInUser(request, user);
		
		// フレームワークでは、ロール用の DAO が設定されない場合、ROLE 情報をセッションに設定する処理は
		// 行われない。
		// マイページのログイン処理では、強制的にロール情報を設定する。
		HttpSession session = request.getSession();
        Set<String> myRoles = getMyRolenamesFromDB(user.getUserId());
        session.setAttribute(this.rolesetSessionParameter, new UserRoleSet(user.getUserId(), myRoles));
	}


	
    /**
     * ロール情報を取得<br/>
     * 匿名ログイン処理の場合、空の Set オブジェクトを復帰する。<br/>
     * <br/>
     * @param userId キーとなるユーザーID
     * @return 空の Set オブジェクト
     */
	@Override
    protected Set<String> getMyRolenamesFromDB(Object userId) {

        return new HashSet<String>();
    }



	/**
	 * Cookie から匿名ログイン用のユーザーID を取得する。<br/>
	 * Cookie の値は暗号化されているので複合化してから復帰する。<br/>
	 * <br/>
	 * @param request HTTP リクエスト
	 * @param response HTTP レスポンス
	 * 
	 * @return 複合化されたユーザーID
	 */
	protected String readUserIdFromCookie(HttpServletRequest request, HttpServletResponse response){
		
		// Cookie から暗号化された UserId を取得する。　値が取得出来ない場合は null を復帰する。
        String hashedId = ServletUtils.findFirstCookieValueByName(request, this.anonymousUidCookieName);
        if (StringValidateUtil.isEmpty(hashedId)) return null;

        // 値が取得できた場合は複合化して復帰
        return userIdCookieCipher.decode(hashedId);
	}

	/**
	 * 匿名認証のユーザー情報を Cookie へ書き込む。<br\>
	 * Cookie へ書き込むユーザーIDは暗号化して書き込む。<br/>
	 * <br/>
	 * @param request HTTP リクエスト
	 * @param response HTTP レスポンス
	 * @param loginUser　認証済ユーザー情報 
	 */
	protected void writeUserIdToCookie(HttpServletRequest request, HttpServletResponse response, LoginUser loginUser){

		// ユーザー情報が取得出来ている場合、暗号化した値を生成する。
		if (loginUser != null) {
			// Cookie オブジェクトを作成し、暗号化した文字列を設定する。
	    	ServletUtils.addCookie(response, request.getContextPath(), this.anonymousUidCookieName, 
	    			userIdCookieCipher.encode((String)loginUser.getUserId()), 
	    			cookieExpires.intValue(), this.commonParameters.getCookieDomain(), this.cookieSequre);
	
		} else {
			// ユーザー情報が取得できなかった場合は Cookie を削除する。
	    	ServletUtils.delCookie(response, request.getContextPath(), this.anonymousUidCookieName, 
	    			this.commonParameters.getCookieDomain(), this.cookieSequre);
	    	
		}

	}

}
