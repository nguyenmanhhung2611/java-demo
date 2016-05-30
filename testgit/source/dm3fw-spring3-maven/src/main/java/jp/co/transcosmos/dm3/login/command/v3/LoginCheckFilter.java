package jp.co.transcosmos.dm3.login.command.v3;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.ModelAndView;

import jp.co.transcosmos.dm3.login.LoginUser;
import jp.co.transcosmos.dm3.login.v3.Authentication;
import jp.co.transcosmos.dm3.retry.RequestRetryParams;
import jp.co.transcosmos.dm3.servlet.BaseFilter;
import jp.co.transcosmos.dm3.servlet.FilterCommandChain;
import jp.co.transcosmos.dm3.utils.ServletUtils;
import jp.co.transcosmos.dm3.utils.SimpleExpressionLanguage;

/**
 * <pre>
 * V3 認証対応
 * ログイン認証チェックフィルター
 * authentication プロパティで指定されたクラスを使用して認証済のチェックを行う
 * 
 * 担当者            修正日               修正内容
 * ----------------------------------------------------------------------------
 * H.Mizuno  2013.04.05  新規作成
 * H.Mizuno  2013.04.11  認証チェックと、ユーザー情報取得を分離
 * 
 * 注意事項 
 *     実質的な処理内容は、V2 と全く同じです。
 * </pre>
*/
public class LoginCheckFilter extends BaseFilter {
    private static final Log log = LogFactory.getLog(LoginCheckFilter.class);

	// 認証ロジックオブジェクト
    private Authentication authentication;

    // リクエストパラメータをセッションに格納する際のキー名
    private String savedRequestSessionParameterPrefix = "savedRequest:";

    // リダイレクトURLに埋め込む、リダイレクトキーのパラメータ名
    // この値と、セッションの値を照合し、改竄されている場合はエラーにする
    private String savedRequestRequestParameter = "redirectKey";

    // キャッシュの無効化（ture=無効化する）
    private boolean addNoCacheHeaders = true;

    // 通常動作の場合、認証エラーが発生すると、View 名を failure にしてレンダリング層に復帰して
    // しまい、目的の Command クラスは実行されない。 
    // このフラグを true にすると、ログインエラーが発生しても、ログイン情報はセッションに作られないが、
    // 目的の Command クラスを実行してくれる。
    // ログインしていなくても画面の閲覧は可能だが、ログインしていないと機能が制限される場合などに使用
    // する。
    private boolean softLogin = false;


    
    /**
     * 通常のフィルターとして使用する場合の初期化処理<br/>
     * Web.xml から通常のフィルターとして使用された場合、認証用オブジェクトが自動的に DI されない
     * ので、フィルターの初期化メソッド内で Spring のコンテキストから自身で取得する。<br/>
     * <br/>
     * @param config フィルターの設定情報
     * @throws ServletException
     */
    @Override
	public void init(FilterConfig config) throws ServletException {
		super.init(config);
		
		// 認証処理クラスの bean ID の取得
        String beanName = config.getInitParameter("authentication");
        if ((beanName == null) || beanName.equals("")) {
        	beanName = "authentication";
        }

		// Spring の Application Context を取得
		WebApplicationContext springContext = WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());

		// Spring コンテキストから 認証クラスを取得
		try {
			this.authentication = (Authentication)springContext.getBean(beanName);
		} catch (NoSuchBeanDefinitionException e){
			this.authentication = null;
			log.warn("Authentication bean not found !!!");
		}
	}



	/**
     * フィルターのメインロジック<br/>
     * 提供された認証用クラスを使用してログイン済かのチェックを行う。<br/>
     * ログイン済の場合、次のフィルターへチェーンする。<br/>
     * ログイン済のチェックがエラーの場合、ログイン済チェックエラー処理を実行する。<br/>
     * なお、ログイン済みチェックが失敗した場合でも、softLogin が有効な場合、次のフィルターへチェーンする。<br/>
     * <br/>
     * @param request HTTPリクエスト
     * @param　response HTTPレスポンス
     * @param　chain チェーン・オブジェクト
     * @throws Exception,　ServletException
     */
	@Override
	protected void filterAction(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		log.debug("Executing the LoginCheckFilter");
		
        // 認証用オブジェクトが設定されていない場合、例外を上げて終了（システムエラー）
		if (this.authentication == null) {
        	throw new RuntimeException("authentication property not setting!!"); 
        }

		// キャッシュを無効化するヘッダ情報の生成
        if (this.addNoCacheHeaders) {
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "No-cache");
            response.setDateHeader("Expires", 1000);
        }

        // ログイン済みの場合は、次のコマンドへチェーンする。
        LoginUser user = authentication.checkLoggedIn(request, response);
        if (user != null) {
            log.info("User: " + user + "(userId=" + user.getUserId() +
                    ") already logged in. URI=" + request.getRequestURI());
       		chain.doFilter(reassociateSavedRequestIfAvailable(request), response);
            return;
    	}

        // ログインしていない場合の処理
        if (isSoftLogin()) {
            // ソフトログインが有効な場合（認証していなくても、画面を表示する場合）の処理
            doSoftLogin(authentication, request, response, chain);
        } else {
            // 通常の認証エラー処理
        	failedLogin(authentication, request, response, chain);
        }
	}


	
    /**
     * ソフトログイン処理<br/>
     * ログイン済チェックは失敗しているが、そのままチェーンを継続する。<br/>
     * この処理は、ログインが失敗していても、パーソナライズされていない画面を表示する場合に使用する。<br/>
     * <br/>
     * @param request HTTPリクエスト
     * @param　response HTTPレスポンス
     * @param　chain チェーン・オブジェクト
     * @throws IOException,　ServletException
     */
    protected void doSoftLogin(Authentication authentication, HttpServletRequest req, 
            HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("Soft-login enabled: forwarding request with no logged-in user");
        chain.doFilter(reassociateSavedRequestIfAvailable(req), response);
    }

	

    /**
     * ログイン済チェックエラー処理<br/>
     * ログアウト処理を行い、リクエスト・パラメータをセッションに格納する。<br/>
     * チェーン先がフレームワークのコマンドクラスの場合、View 名を　failure に設定してチェーンを中断する。<br/>
     * チェーン先が通常のフィルターの場合、コンテキスト・パラメータで指定されている URL へリダイレクトする。<br/>
     * <br/>
     * @param request HTTPリクエスト
     * @param　response HTTPレスポンス
     * @param　chain チェーン・オブジェクト
     * @throws IOException,　ServletException
     */
    protected void failedLogin(Authentication authentication, HttpServletRequest request, 
            HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

    	// ログアウト処理を実行
        authentication.logout(request, response);
        log.info("Login failed: saving request in session and forwarding to the login-form");

        
        // リクエスト情報をセッションに格納し、変わりに Key を埋め込んだリダイレクト先 URL を作成する。
        // ログイン後に元のリクエスト先に遷移したい場合、以下の設定も合わせて行う事。
        //  ・リダイレクト先URLを、ログイン画面の hidden パラメータ redirectURL に設定する。
        //  ・URL マッピングで、success 時のビュー名を、redirect:###!redirectURL### に設定する。
        String redirectURL = saveRequest(request);
        Map<String,Object> model = new HashMap<String,Object>(); 
        model.put("redirectURL", redirectURL);


        // ここで、Cookie から userType　を取得し、model に追加している。
        // 後で、このパラメータをどの様に使用しているのか考慮する。
        
        
        if (chain instanceof FilterCommandChain) {
        	// 通常は、こちらの処理。　結果をセットして、チェーンを中断する。
        	// ここも、userType　によっては、failureWithUserType を View 名にしている。
        	((FilterCommandChain) chain).setResult(request, new ModelAndView("failure", model));
        } else {
        	// web.xml から、通常のフィルターとして使用された場合の処理
            String loginFormURL = this.getLoginFormURL();
            if (loginFormURL != null) {
                String replaced = SimpleExpressionLanguage.evaluateWebExpressionPatternString(
                        loginFormURL, request, response, getContext(), model, true, 
                        response.getCharacterEncoding(), "");
                response.sendRedirect(ServletUtils.fixPartialURL(replaced, request));
            } else {
                log.warn("Failed login, not inside spring, and no loginFormURL defined - sending 403");
                ((HttpServletResponse) response).sendError(HttpServletResponse.SC_FORBIDDEN);
            }
        }
    }

    
    
    /**
     * コンテキスト・パラメータから、リグイン URL を取得<br/>
     * <br/>
     * @return ログイン画面の URL
     */
    protected String getLoginFormURL() {
        return getInitParameter("loginFormURL", null);
    }


    
    /**
     * リクエスト・パラメータをセッションに格納する。<br/>
     * リクエスト・パラメータをセッションに格納し、その際、ユニークなセッションキーを生成する。<br/>
     * <br/>
     * @param　request HTTPリクエスト
     * @return セッションキーを組み込んだリダイレクト先 URL。　リダイレクト先は、元のリクエスト先URL。
     * @throws IOException, ServletException
     */
    protected String saveRequest(HttpServletRequest request) throws IOException, ServletException {
        return RequestRetryParams.saveRequest(request, this.savedRequestSessionParameterPrefix, 
                this.savedRequestRequestParameter);
    }

    
    /**
     * セッションからリクエストパラメータを復元する。<br/>
     * セッションに格納されたリクエストパラメータを復元する。　その際、セッションキーの妥当性をチェックする。<br/>
     * （パラメータ改竄対策）<br/>
     * <br/>
     * @param　request HTTPリクエスト
     * @return セッションから取得した、HTTP リクエストオブジェクト
     */
    protected HttpServletRequest reassociateSavedRequestIfAvailable(HttpServletRequest req) {
        return RequestRetryParams.reassociateSavedRequestIfAvailable(req, 
                this.savedRequestSessionParameterPrefix,
                this.savedRequestRequestParameter);
    }

    
    
	// setter、getter
    public void setSavedRequestRequestParameter(String pSavedRequestRequestParameter) {
        this.savedRequestRequestParameter = pSavedRequestRequestParameter;
    }

    public void setSavedRequestSessionParameterPrefix(String pSavedRequestSessionParameterPrefix) {
        this.savedRequestSessionParameterPrefix = pSavedRequestSessionParameterPrefix;
    }

    public void setAddNoCacheHeaders(boolean pAddNoCacheHeaders) {
        this.addNoCacheHeaders = pAddNoCacheHeaders;
    }

    public void setAuthentication(Authentication pAuthentication) {
        this.authentication = pAuthentication;
    }

    public void setSoftLogin(boolean pSoftLogin) {
        this.softLogin = pSoftLogin;
    }

    public boolean isSoftLogin() throws IOException, ServletException {
        return this.softLogin;
    }
}
