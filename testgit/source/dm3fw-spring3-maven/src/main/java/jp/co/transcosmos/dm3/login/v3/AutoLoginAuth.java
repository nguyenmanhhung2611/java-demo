package jp.co.transcosmos.dm3.login.v3;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.transcosmos.dm3.dao.DAOCriteria;
import jp.co.transcosmos.dm3.login.CookieLoginUser;
import jp.co.transcosmos.dm3.login.LoginUser;
import jp.co.transcosmos.dm3.login.LoginUtils;
import jp.co.transcosmos.dm3.login.form.v3.AutoLoginForm;
import jp.co.transcosmos.dm3.login.form.v3.LoginForm;


/**
 * <pre>
 * V3 認証対応
 * 自動ログイン機能を追加した、DB認証処理クラス
 * ログイン済みチェックに失敗しても、Cookie の値を使ってチェックを継続する。
 * チェックに成功した場合、ログイン情報を生成して復帰する。 Cookie の操作や、暗号化は、V1、V2 と
 * 共通のクラスを使用している。
 *
 * 担当者            修正日               修正内容
 * ----------------------------------------------------------------------------
 * H.Mizuno  2013.04.05  新規作成
 * H.Mizuno  2013.04.11  認証チェックと、ユーザー情報取得を分離
 *                       ログアウト時に、認証用 Cookie を削除する処理を追加
 * H.Mizuno  2015.06.24  自動ログイン用トークンを、認証成功時に再作成する機能を追加
 *
 * </pre>
*/
public class AutoLoginAuth extends DefaultAuth {
	private static final Log log = LogFactory.getLog(AutoLoginAuth.class);

	// 自動ログインで使用する Cookie の有効期限（秒）
    protected Integer autoLoginCookieExpirySeconds;

    // 自動ログインで使用する Cookie の　Domain 名
    protected String cookieDomain = null;

	// 手動ログインのチェック用トークン名
    protected String manualLoginTokenName = "manualLoginToken";

    // true の場合、ログアウト時に認証処理用 Cookie を削除する
    protected boolean removeCookieWithLogout;

// 2015.04.20 H.Mizuno cookie の sequre 属性対応 start
    // true の場合、自動ログインで使用する cookie に sequre 属性を設定する
    protected boolean cookieSequre;
// 2015.04.20 H.Mizuno cookie の sequre 属性対応 end

// 2015.06.24 H.Mizuno 認証成功時に、自動ログイン用トークンを再作成する機能を追加 start
    // Cookie に格納する自動ログイン用トークンを認証成功時にリセットする場合、このプロパティを true に設定する。
    // （デフォルト false）
    // この機能を有効にした場合、認証が成功する都度、自動ログイン用トークンが再設定されるので、
    // 複数ブラウザを使用している場合、別のブラウザの自動ログインがキャンセルされる。
    // デフォルトではリセットする処理は無効化されているが、cookie が流出すると自動ログインが可能
    // なので、上記制限事項が許容範囲であれば有効化している方が安全と言える。
    protected boolean useAutoLoginTokenReset;
// 2015.06.24 H.Mizuno 認証成功時に、自動ログイン用トークンを再作成する機能を追加 end

    

// 2015.06.24 H.Mizuno デフォルトの振る舞いを改訂 start
    public AutoLoginAuth(){
    	this.removeCookieWithLogout = true;
    	// Ver 1.5.x 系までは、cookie のセキュア属性付与のデフォルトは無効だったが、
    	// セキュリティ的に危険なので Ver 1.6.x 系では、デフォルトを有効に変更。
    	this.cookieSequre = true;

    	// 自動ログイン用トークンを、認証成功時にリセットする機能のデフォルトは無効。
    	this.useAutoLoginTokenReset = false;
    }
// 2015.06.24 H.Mizuno デフォルトの振る舞いを改訂 end



    /**
     * ログイン処理（手動ログイン処理）<br/>
     * プロパティで設定された認証用クラスを使用してログイン処理を実行する。<br/>
     * ログインに失敗した場合、cookie の認証情報を削除する。<br/>
     * ログインが成功して、自動ログインがチェックされている場合、cookie に認証情報を追加する。<br/>
     * また、自動ログインがチェックされていない場合、cookie の認証情報を削除する。<br/>
     * <br/>
     * ※自動ログインの判定用に、ログインが成功すると、フラグをセッションに格納している。<br/>
     *   ログイン失敗時は、ログアウト処理が実行されるので、このメソッド内では削除していない。<br/>
     * <br/>
     * @param request HTTPリクエスト
     * @param　response HTTPレスポンス
     * @param　form ログイン画面のフォーム
     * @return ログインユーザーのオブジェクト
     */
	@Override
	public LoginUser login(HttpServletRequest request, HttpServletResponse response, LoginForm form) {

		// LoginComannd で使っている Form クラスを間違っている場合。　設定ミスなので、例外を上げて終了。
		if (!(form instanceof AutoLoginForm))
			throw new RuntimeException("form type is miss match");

		// 通常のログイン処理を実行
		LoginUser user = super.login(request, response, form);
		if (user == null) {
			// ログインに失敗した場合、 Cookie のログイン情報を削除する。
// 2015.05.20 H.Mizuno cookie の sequre 属性対応 start
//			LoginUtils.setAutoLoginCookies(response, request.getContextPath(), null, null,
//					this.cookieDomain);
			LoginUtils.setAutoLoginCookies(response, request.getContextPath(), null, null,
					this.cookieDomain, this.cookieSequre);
// 2015.05.20 H.Mizuno cookie の sequre 属性対応 end
			return null;
		}
		
		// 手動ログインチェック用のトークン名が設定されている場合、通常のログインが成功すると、セッション
		// にチェック用トークンを設定する。
		// この値は、ManualLoginCheckFilter で、手動ログインの有無をチェックする際に使用される。
        if (this.manualLoginTokenName != null) {
        	HttpSession session = request.getSession();
        	session.setAttribute(this.manualLoginTokenName, Boolean.TRUE);
        }

		// ログインが成功した場合のみ、Cookie に自動ログイン用のトークンを設定する。
		// 当然、自動ログインのチェックが外されている場合は Cookie から削除する。
        if (((AutoLoginForm)form).isAutoLoginSelected() && 
                (user instanceof CookieLoginUser)) {
// 2015.05.20 H.Mizuno cookie の sequre 属性対応 start
//            LoginUtils.setAutoLoginCookies(response, request.getContextPath(), user.getUserId(), 
//                    retrieveCookieLoginPassword(request, response), this.autoLoginCookieExpirySeconds,
//                    this.cookieDomain);
            LoginUtils.setAutoLoginCookies(response, request.getContextPath(), user.getUserId(), 
                    retrieveCookieLoginPassword(request, response), this.autoLoginCookieExpirySeconds,
                    this.cookieDomain, this.cookieSequre);
// 2015.05.20 H.Mizuno cookie の sequre 属性対応 end
        } else {
// 2015.05.20 H.Mizuno cookie の sequre 属性対応 start
//            LoginUtils.setAutoLoginCookies(response, request.getContextPath(), null, null,
//            		this.cookieDomain);
            LoginUtils.setAutoLoginCookies(response, request.getContextPath(), null, null,
            		this.cookieDomain, this.cookieSequre);
// 2015.05.20 H.Mizuno cookie の sequre 属性対応 end
        }

		return user;
	}



    /**
     * ログアウト処理<br/>
     * プロパティに設定された認証用クラスを使用してログアウト処理を行う。<br/>
     * また、自動ログイン判定用のフラグもセッションから削除する。<br/>
     * <br/>
     * @param request HTTPリクエスト
     * @param　response HTTPレスポンス
     */
	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response) {

		// 通常のログアウト処理を実行
		super.logout(request, response);
		
		// 手動ログインチェック用のトークン名が設定されている場合、ログアウト後にチェック用トークンを 削除する。
		if (this.manualLoginTokenName != null) {
        	HttpSession session = request.getSession(false);
        	if (session != null) {
        		session.removeAttribute(this.manualLoginTokenName);
        	}
        }
		
		// ログアウト後に、Cookie の認証情報を削除する。
		// 但し、プロパティの設定で無効化可能。　デフォルトは削除する。
        if (this.removeCookieWithLogout) {
// 2015.05.20 H.Mizuno cookie の sequre 属性対応 start
//        	LoginUtils.setAutoLoginCookies(response, request.getContextPath(), null, null,
//            		this.cookieDomain);
        	LoginUtils.setAutoLoginCookies(response, request.getContextPath(), null, null,
            		this.cookieDomain, this.cookieSequre);
// 2015.05.20 H.Mizuno cookie の sequre 属性対応 end
        }
	}

	
	
    /**
     * ログイン状態の判定処理<br/>
     * 通常のセッション情報によるチェック処理に失敗した場合、 Cookie に、暗号化されたたログイン<br/>
     * 情報が存在するかチェックする。<br/>
     * 存在する場合、値を複合化してDBの情報と照合する。<br/>
     * 照合に成功した場合、ログイン情報をセッションに作成してログイン済として復帰する。<br/>
     * <br/>
     * @param request HTTPリクエスト
     * @param　response HTTPレスポンス
     * @return ログインユーザーのオブジェクト。　ログイン済でない場合、null を復帰する。
     */
	@Override
	public LoginUser checkLoggedIn(HttpServletRequest request, HttpServletResponse response) {

		// 通常のログインチェックを実行、ログイン済の場合は、そのまま値を返す。
		LoginUser user = super.checkLoggedIn(request, response);
		if (user != null) return user;


		// 自動ログインの場合、cookie から、暗号化されたユーザーID（主キー）を取得する。
		// 取得出来ない場合、認証エラーとして、null を復帰する。
		String autoLoginSecret = LoginUtils.getAutoLoginPasswordFromCookie(request);
		if (autoLoginSecret == null) {
            log.info("No auto-login secret found");
            return null;
		}
		
		// 取得した暗号化されたユーザーID（主キー）を復号化する。
		Object userID = LoginUtils.getAutoLoginUserIdFromCookie(request);
		if( userID == null ){
            log.info("No auto-login user id found");
            return null;
		}

		// ユーザーID（主キー）が取得できた場合、Cookie による自動ログインを実行する。
// 2015.06.24 H.Mizuno 認証成功時に、自動ログイン用トークンを再作成する機能を追加 start
//        CookieLoginUser cookieLoginUser = cookieAutoLogin(request, userID, autoLoginSecret);
        CookieLoginUser cookieLoginUser = cookieAutoLogin(request, response, userID, autoLoginSecret);
// 2015.06.24 H.Mizuno 認証成功時に、自動ログイン用トークンを再作成する機能を追加 end
		if( cookieLoginUser == null ){
            log.info("Cookie login failed");
            return null;
		}

		return cookieLoginUser;
	}


    /**
     * ログインユーザーの情報取得<br/>
     * セッションからログイン情報が取得可能な場合、ログインユーザーの情報を復帰する。<br/>
     * 取得できない場合、null を復帰する。　要するに、親クラスのcheckLoggedIn()<br/>
     * と同じ処理。
     * <br/>
     * @param request HTTPリクエスト
     * @param　response HTTPレスポンス
     * @return ログインユーザーのオブジェクト。　ログイン済でない場合、null を復帰する。
     */
	public LoginUser getLoggedInUser(HttpServletRequest request, HttpServletResponse response){
		return super.checkLoggedIn(request, response);
	}


	/**
     * Cookie による自動ログイン処理<br/>
     * 基本的に、通常のログインと同じだが、パスワードの照合は、<br/>
     * CookieLoginUser#matchCookieLoginPassword() にて行う。<br/>
     * これは、通常のログインに使用するパスワードを Cookie に格納すると危険なので、<br/>
     * 別のハッシュした値で照合する事を可能にする為の処置である。<br/>
     * <br/>
     * @param request HTTPリクエスト
     * @param　userId Cookie から復号化したオブジェクト
     * @param　loginSecretCookie Cookie に格納されていた暗号化されているオブジェクト
     * @return ログインユーザーのオブジェクト（自動ログイン用）。　ログイン済でない場合、null を復帰する。
     */
// 2015.06.24 H.Mizuno 認証成功時に、自動ログイン用トークンを再作成する機能を追加 start
//	protected CookieLoginUser cookieAutoLogin(HttpServletRequest request,
//			Object userId, String loginSecretCookie){

	/**
	 * 自動ログイン処理<br/>
	 * 旧バージョンへの互換用メソッド。　非推奨。<br/>
	 * <br/>
	 * @deprecated
	 * @param request HTTP リクエスト
	 * @param userId　ユーザーID
	 * @param loginSecretCookie 自動ログイン用トークン
	 * @return
	 */
	protected CookieLoginUser cookieAutoLogin(HttpServletRequest request,
			Object userId, String loginSecretCookie){

		return cookieAutoLogin(request, null, userId, loginSecretCookie);
	}

	/**
	 * 自動ログイン処理<br/>
	 * 指定されたユーザーID をキーとして、ユーザー情報をDBから取得する。<br/>
	 * 取得したユーザー情報の自動ログイントークンが一致する場合、ログイン日時を更新、セッションにユーザー
	 * 情報を格納してからユーザー情報を復帰する。<br/>
	 * もし、useAutoLoginTokenReset　が有効な場合、自動ログインの認証後に自動ログイン用のトークン
	 * を再発行し、cookie と DB の値を更新する。
	 * <br/>
	 * @param request HTTP リクエスト
	 * @param response HTTP レスポンス
	 * @param userId　ユーザーID
	 * @param loginSecretCookie 自動ログイン用トークン
	 * @return
	 */
	protected CookieLoginUser cookieAutoLogin(HttpServletRequest request,
			HttpServletResponse response, Object userId, String loginSecretCookie){
// 2015.06.24 H.Mizuno 認証成功時に、自動ログイン用トークンを再作成する機能を追加 end

		// ユーザーID（主キー）を使用して、ユーザー情報を取得する。
        DAOCriteria criteria = new DAOCriteria();
        criteria.addWhereClause(this.userDAOUserIdField, userId);
        List<LoginUser> users = this.userDAO.selectByFilter(criteria);

        for (Iterator<LoginUser> i = users.iterator(); i.hasNext(); ) {
            LoginUser user = i.next();
            // Cookie から取得した暗号化された情報と、DBの情報を照合し、一致した場合、
            // ログイン処理を行う。
            if ((user instanceof CookieLoginUser) && 
                    ((CookieLoginUser) user).matchCookieLoginPassword(loginSecretCookie)) {

            	updateLastLoginTimestamp(user);
                setLoggedInUser(request, user);

// 2015.06.24 H.Mizuno 認証成功時に、自動ログイン用トークンを再作成する機能を追加 start
                if (this.useAutoLoginTokenReset) {

                	// 新たな自動ログイン用トークンを採番し、DB に格納する。
                	String newLoginSecretCookie = retrieveCookieLoginPassword(request, response);
                	
                	// 採番した自動ログイン用トークンを Cookie に書込む
                    LoginUtils.setAutoLoginCookies(response, request.getContextPath(), user.getUserId(), 
                    		newLoginSecretCookie, this.autoLoginCookieExpirySeconds,
                            this.cookieDomain, this.cookieSequre);
                    
                    // 復帰するユーザー情報の自動ログイン用トークンを、新たに採番した値に変更する。
                    ((CookieLoginUser) user).setCookieLoginPassword(newLoginSecretCookie);
                }
// 2015.06.24 H.Mizuno 認証成功時に、自動ログイン用トークンを再作成する機能を追加 end
                
                return (CookieLoginUser) user;
            }                
        }
        log.info("No user found with ID=" + userId + 
                " and matching cookie login password - Failing auto-login");
        return null;
	}


	// 注）
	// この実装だと、１度ログインすると、以降、Cookie に書き込まれるキーの値が固定化されてしまう
	// 様に思える。　通常ログインの場合位は、Key を生成し直した方が良いのでは？

	/**
     * Cookie に書き込むログイン用のトークンを生成<br/>
     * Cookie に書き込むログイン用のトークンは、フレームワーク側がランダムな文字列を暗号化して作成する。<br/>
     * この暗号化した文字列は、cookieLoginUser#setCookieLoginPassword() にマップされる
     * DB上のフィールドに格納される。　以降、DB上に暗号化されたトークンが存在する場合、そのトークンを使い
     * まわす。<br/>
     * <br/>
     * @param request HTTPリクエスト
     * @param　userId Cookie から復号化したオブジェクト
     * @param　loginSecretCookie Cookie に格納されていた暗号化されているオブジェクト
     * @return 自動ログイン用のトークン文字列。　ログイン済でない場合、null を復帰する。
     */
	protected String retrieveCookieLoginPassword(HttpServletRequest request, HttpServletResponse response){
		
		// ユーザー情報を取得
		// checkLoggedIn() 自体は、自動ログインの処理も存在するが、retrieveCookieLoginPassword()
		// は、ログインが成功した場合に実効されるので、セッションからログイン情報を引き出す事のみが行われる。
        LoginUser user = checkLoggedIn(request, response);


        // 自動ログイン機能が有効な場合のみ自動ログイン用のトークンを生成する。
        if (user instanceof CookieLoginUser) {
            CookieLoginUser cookieLoginUser = (CookieLoginUser) user;

// 2015.06.24 H.Mizuno 認証成功時に、自動ログイン用トークンを再作成する機能を追加 start
//            if (cookieLoginUser.getCookieLoginPassword() == null) {
            
            // DB に保管されている、自動ログイン用のトークンが設定されていない場合（初回ログイン時など）、
            // トークンを生成し、DBに格納する。
            // また、useAutoLoginTokenReset　プロパティが true の場合、常に自動ログイン用のトークン
            // を再設定する。
            if (cookieLoginUser.getCookieLoginPassword() == null || this.useAutoLoginTokenReset) {
// 2015.06.24 H.Mizuno 認証成功時に、自動ログイン用トークンを再作成する機能を追加 end
                cookieLoginUser.setCookieLoginPassword(LoginUtils.generateAutoLoginPassword());

                if (writeableUserDAO != null) {
                	this.writeableUserDAO.update(new LoginUser[] {cookieLoginUser});
                } else {
                	this.userDAO.update(new LoginUser[] {cookieLoginUser});
                }
            }
            return cookieLoginUser.getCookieLoginPassword();
        } else {
            return null;
        }
	}


	
    // setter、getter
    public void setAutoLoginCookieExpirySeconds(int pAutoLoginCookieExpirySeconds) {
        this.autoLoginCookieExpirySeconds = new Integer(pAutoLoginCookieExpirySeconds);
    }
	
	public void setCookieDomain(String cookieDomain) {
		this.cookieDomain = cookieDomain;
	}

	public void setManualLoginTokenName(String manualLoginTokenName) {
		this.manualLoginTokenName = manualLoginTokenName;
	}

	public void setRemoveCookieWithLogout(boolean removeCookieWithLogout) {
		this.removeCookieWithLogout = removeCookieWithLogout;
	}

// 2015.05.20 H.Mizuno cookie の sequre 属性対応 start
	/**
	 * 自動ログイン用トークンにセキュア属性を付与しない場合は false を設定する。<br/>
	 * ※セキュリティ的考慮により、Ver 1.6 系からデフォルトが true に変更されている。
	 * <br/>
	 * @param cookieSequre 属性を付けない場合は false を設定する。（デフォルト true）
	 */
	public void setCookieSequre(boolean cookieSequre) {
		this.cookieSequre = cookieSequre;
	}
// 2015.05.20 H.Mizuno cookie の sequre 属性対応 end


// 2015.06.24 H.Mizuno 認証成功時に、自動ログイン用トークンを再作成する機能を追加 start
	/**
	 * 認証成功時に自動ログイン用トークンを再設定する場合は true を設定する。<br/>
	 * <br/>
	 * @param useAutoLoginTokenReset 機能を有効にする場合 true （デフォルト false）
	 */
	public void setUseAutoLoginTokenReset(boolean useAutoLoginTokenReset) {
		this.useAutoLoginTokenReset = useAutoLoginTokenReset;
	}
// 2015.06.24 H.Mizuno 認証成功時に、自動ログイン用トークンを再作成する機能を追加 end
	
}
