package jp.co.transcosmos.dm3.login.command.v3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.form.FormPopulator;
import jp.co.transcosmos.dm3.login.LoginUser;
import jp.co.transcosmos.dm3.login.form.v3.DefaultLoginForm;
import jp.co.transcosmos.dm3.login.form.v3.LoginForm;
import jp.co.transcosmos.dm3.login.support.SessionIdResetter;
import jp.co.transcosmos.dm3.login.v3.Authentication;
import jp.co.transcosmos.dm3.login.v3.LockException;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

/**
 * <pre>
 * Ver 3 認証対応用ログインコマンドクラス
 * リクエストパラメータのバリデーションチェックを行い、Authentication プロパティのクラスを使用
 * してログイン処理を行う。
 *
 * 担当者            修正日               修正内容
 * ----------------------------------------------------------------------------
 * H.Mizuno  2013.04.05  新規作成
 * H.Mizuno  2013.04.19  互換性の為、validationerros.jsh を元の仕様に戻した為の修正
 * H.Mizuno  2015.02.24  アカウントロック対応
 * H.Mizuno  2015.06.22  ログイン成功時にセッションID を切り替える機能を追加
 *
 * </pre>
*/
public class LoginCommand implements Command {

	private static final Log log = LogFactory.getLog(LoginCommand.class);

	// 認証処理クラス
	protected Authentication authentication;
	
	// リダイレクト先 URL パラメータ名
	protected String redirectURLParameter = "redirectURL";

	// ログインフォームのオブジェクト
	// バリデーションロジックの拡張等でLoginForm を変える場合、Spring から DI する。
	// AutoLoginAuth を使用する場合、このプロパティを、 AutoLoginForm　に設定する。
	protected LoginForm loginForm = new DefaultLoginForm();

// 2015.06.22 H.Mizuno ログアウト時のセッション破棄対応 start
 	// ログイン後にセッションID を切り替えない場合は false を設定する。 （デフォルト true）
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
 	public LoginCommand (){
 		this.useSessionIdReset = true;
 	}
// 2015.06.22 H.Mizuno ログアウト時のセッション破棄対応 start
 	
 	
	
    /**
     * メインロジック<br/>
     * リクエスト・パラメータを受取り、バリデーションを行う。<br/>
     * バリデーションが正常終了した場合、指定された認証用クラスによりログイン処理を行う。<br/>
     * ログイン処理の成功・失敗に応じて、後続処理を実行する。<br/>
     * <br/>
     * @param request HTTPリクエスト
     * @param　response HTTPレスポンス
     * @return レンダリングで使用する ModelAndView オブジェクト
     * @throws Exception
     */
	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

        FormPopulator.populateFormBeanFromRequest(request, loginForm);

        // バリデーションエラーがある場合、View 名を failure で復帰
        List<ValidationFailure> errors = new ArrayList<ValidationFailure>();
        if (!loginForm.validate(errors)) {
            return loginFailure(request, response, null, loginForm, errors);
        }

        // ログイン処理を実行
// 2015.02.24 H.Mizuno アカウントロック機能に対応 start
//        LoginUser user = this.authentication.login(request, response, loginForm);

        LoginUser user = null;
        try {
            user = this.authentication.login(request, response, loginForm);
        } catch (LockException e){
        	// アカウントロックの例外がスローされた場合。
        	errors.add(new ValidationFailure("userlock","logincheck",null,null));
        	return loginFailure(request, response, user, loginForm, errors);
        }
// 2015.02.24 H.Mizuno アカウントロック機能に対応 start

        if (user == null) {
        	// ログイン失敗
        	errors.add(new ValidationFailure("notfound","logincheck",null,null));
        	return loginFailure(request, response, user, loginForm, errors);
		} else {
        	// ログイン成功
            return loginSuccess(request, response, user, loginForm);
        }
	}

	
	
    /**
     * ログイン成功処理<br/>
     * View 名を success に設定して復帰する。<br/>
     * <br/>
     * @param request HTTPリクエスト
     * @param　response HTTPレスポンス
     * @param　user　ログインユーザーのオブジェクト
     * @param　loginForm ログインフォーム
     * @return ビュー名 success の ModelAndView オブジェクト
     */
    protected ModelAndView loginSuccess(HttpServletRequest request, 
            HttpServletResponse response, LoginUser user, LoginForm loginForm) {
        log.info("set user: " + user);

        // セッションID の切り替えが有効な場合、セッション を新セッションへ切り替える
    	if (this.useSessionIdReset && this.sessionIdResetter != null) {
           	this.sessionIdResetter.resetSession(request);
    	}

        // リダイレクト先URLの情報を、リクエスト・パラメータから取得する。
        return new ModelAndView("success", "redirectURL", getRedirectURL(request));        
    }
	

    
    /**
     * ログイン失敗処理<br/>
     * View 名を failure に設定して復帰する。<br/>
     * ログアウト処理を実行し、ModelAndView にログインフォームと、エラーメッセージを格納する。
     * <br/>
     * @param request HTTPリクエスト
     * @param　response HTTPレスポンス
     * @param　user　ログインユーザーのオブジェクト
     * @param　loginForm ログインフォーム
     * @return ビュー名 failure の ModelAndView オブジェクト
     */
	protected ModelAndView loginFailure(HttpServletRequest request, 
            HttpServletResponse response, LoginUser user, LoginForm loginForm,
            List<ValidationFailure> errors) {

    	// ログアウト処理
    	this.authentication.logout(request, response);

        Map<String,Object> model = new HashMap<String,Object>();

        // リダイレクト先情報、入力値、エラーメッセージを ModelAndView へ渡す。
        model.put("redirectURL", getRedirectURL(request));
        model.put("loginForm", loginForm);
        model.put("errors", errors);

        // ここでも、userType の処理を行っているが、とりあえず削除
        
        return new ModelAndView("failure", model);
	}

	
	
    /**
     * リダイレクト先URLの取得<br/>
     * リクエスト・パラメータから、リダイレクト先 URL を取得する。
     * <br/>
     * @param request HTTPリクエスト
     * @return リダイレクト先 URL
     */
    protected String getRedirectURL(HttpServletRequest request) {
        return request.getParameter(this.redirectURLParameter);
    }

	
	
	// setter、getter
	public void setAuthentication(Authentication pAuthentication) {
        this.authentication = pAuthentication;
    }

	public void setLoginForm(LoginForm loginForm) {
		this.loginForm = loginForm;
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
