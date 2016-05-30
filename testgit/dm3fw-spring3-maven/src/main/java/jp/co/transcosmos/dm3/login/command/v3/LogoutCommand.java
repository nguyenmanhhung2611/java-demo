package jp.co.transcosmos.dm3.login.command.v3;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.login.support.SessionIdResetter;
import jp.co.transcosmos.dm3.login.v3.Authentication;


/**
 * <pre>
 * Ver 3 認証対応用ログアウトコマンドクラス
 * Authentication プロパティのクラスを使用 してログアウト処理を行う。
 *
 * 担当者            修正日               修正内容
 * ----------------------------------------------------------------------------
 * H.Mizuno  2013.04.05  新規作成
 * H.Mizuno  2015.06.22  ログアウト時にセッションID を切り替える機能を追加
 *
 * </pre>
*/
public class LogoutCommand implements Command {

	// 認証処理クラス
	private Authentication authentication;

// 2015.06.22 H.Mizuno ログアウト時のセッション破棄対応 start
 	// ログアウト時に新たなセッションID を払い出さない場合は false を設定する。　（デフォルト true）
	// セッションオブジェクトの値は新しいセッションに引き継がれるので、通常は変更する必要が無い。
	// 何かしらの問題が発生した場合に無効化する。
    private boolean useSessionIdReset;

    // セッション ID をリセットするオブジェクト
    // SessionIdResetter クラスは、旧セッションの値を新セッションに引き継ぐが、もし、引継ぎ
    // ぐオブジェクトを制限したい場合などは、このプロパティに設定するオブジェクトを変更する。
 	protected SessionIdResetter sessionIdResetter = new SessionIdResetter();

 	/**
 	 * デフォルトコンストラクタ
 	 */
 	public LogoutCommand(){
 		this.useSessionIdReset = true;
 	}
// 2015.06.22 H.Mizuno ログアウト時のセッション破棄対応 start



    /**
     * メインロジック<br/>
     * プロパティで設定された認証用クラスでログアウト処理を行う。<br/>
     * <br/>
     * @param request HTTPリクエスト
     * @param　response HTTPレスポンス
     * @return レンダリングで使用する ModelAndView オブジェクト
     * @throws Exception
     */
    @Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// ログアウト処理
		this.authentication.logout(request, response);


        // セッションID の破棄が有効な場合、セッション ID を破棄する。
    	if (this.useSessionIdReset && this.sessionIdResetter != null) {
           	this.sessionIdResetter.resetSession(request);
    	}

		return new ModelAndView("success");

	}



	// setter、getter
    public void setAuthentication(Authentication pAuthentication) {
        this.authentication = pAuthentication;
    }

// 2015.06.22 H.Mizuno ログアウト時のセッション破棄対応 start
	/**
	 * ログイン時のセッションID 切り替えを無効化する場合は false を設定する。<br/>
	 * Ver 1.5.x 系までは、デフォルトでは false （無効）だったが、セキュリティ的な考慮により、
	 * Ver 1.6.x 系からは、デフォルトが true （有効）に変更されている。<br/>
	 * また、メソッド名も、useSessionReset から変更されている。
	 * <br/>
	 * @param useSessionIdReset 無効化する場合 false を設定　（デフォルト true）
	 */
	public void setUseSessionIdReset(boolean useSessionIdReset) {
		this.useSessionIdReset = useSessionIdReset;
	}

	/**
	 * セッションID のリセットクラスを設定する。<br/>
	 * デフォルトでは、SessionIdResetter のインスタンスが使用されるが、クラスを拡張した場合
	 * は、このメソッドでオブジェクトを設定する。<br/>
	 * Ver 1.5.x からメソッド名が、setSessionResetter から変更されている。<br/>
	 * <br/>
	 * @param sessionIdResetter セッションID リセットオブジェクト
	 */
	public void setSessionIdResetter(SessionIdResetter sessionIdResetter) {
		this.sessionIdResetter = sessionIdResetter;
	}
// 2015.06.22 H.Mizuno ログアウト時のセッション破棄対応 end

}
