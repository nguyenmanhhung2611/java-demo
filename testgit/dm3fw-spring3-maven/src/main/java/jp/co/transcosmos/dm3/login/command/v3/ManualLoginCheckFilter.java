package jp.co.transcosmos.dm3.login.command.v3;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.ModelAndView;

import jp.co.transcosmos.dm3.login.v3.Authentication;
import jp.co.transcosmos.dm3.login.LoginUser;
import jp.co.transcosmos.dm3.retry.RequestRetryParams;
import jp.co.transcosmos.dm3.servlet.BaseFilter;
import jp.co.transcosmos.dm3.servlet.FilterCommandChain;
import jp.co.transcosmos.dm3.utils.ServletUtils;
import jp.co.transcosmos.dm3.utils.SimpleExpressionLanguage;



/**
 * <pre>
 * V3 認証対応
 * 手動ログインのチェックフィルター
 * 明示的なログイン処理を行ったかをチェックする。
 * このフィルターは、基本的は AutoLoginAuth との組み合わせで使用し、自動ログインの場合に、
 * アクセスを制限したい場合に使用する。
 * （例えば、個人情報の入力画面は、明確なログインが必要な場合など）
 * 
 * 担当者            修正日               修正内容
 * ----------------------------------------------------------------------------
 * H.Mizuno  2013.04.05  新規作成
 * H.Mizuno  2013.04.11  認証チェックと、ユーザー情報取得を分離
 * H.Mizuno  2013.05.10  Servlet Filter で使用した場合、ログイン処理にリダイレクト先が渡らない
 *                       問題を修正
 * 
 * 注意事項 
 *     実質的な処理内容は、V2 と同じですが、Servlet Filter でリダイレクト時の処理を少し変更。
 * </pre>
*/
public class ManualLoginCheckFilter extends BaseFilter {
    private static final Log log = LogFactory.getLog(ManualLoginCheckFilter.class);

    // 認証ロジックオブジェクト。　通常は、AUtoLoginAuth （自動ログイン）を使用する。
    // 型は、AutoLoginAuth の方が安全だが、自作した認証クラスによる拡張性が無くなるので、
    // Authentication インターフェースを使用している。
    protected Authentication authentication;

    // true の場合、レスポンスのキャッシュを無効にする
    protected boolean addNoCacheHeaders = true;

	// 手動ログインのチェック用トークン名
    protected String manualLoginTokenName = "manualLoginToken";

    // リダイレクト用のキー
    // 用途を後でチェックする。　（必要性が把握できていない為。）
    private String savedRequestSessionParameterPrefix = "savedRequest:";
    private String savedRequestRequestParameter = "redirectKey";

    // web.xml の Filter で使用された場合のリダイレクト先 URL
    private String loginFormURL;

    

    /**
     * 通常のフィルターとして使用する場合の初期化処理<br/>
     * Web.xml から通常のフィルターとして使用された場合、認証用オブジェクトが自動的に DI されない
     * ので、フィルターの初期化メソッド内で Spring のコンテキストから自身で取得する。<br/>
     * また、コンテキスト・パラメータから、ログイン画面の URL を取得する。<br/>
     * <br/>
     * @param config フィルターの設定情報
     * @throws ServletException
     */
    @Override
    public void init(FilterConfig config) throws ServletException {
    	super.init(config);

    	// web.xml のコンテキスト・パラメータで設定されている場合にエラー時のリダイレクト先を設定する。
    	String loginFormURL = config.getInitParameter("loginFormURL");
        if (loginFormURL != null) {
            this.loginFormURL = loginFormURL;
        }

		// 認証処理クラスの bean ID の取得
        String beanName = config.getInitParameter("authentication");
        if ((beanName == null) || beanName.equals("")) {
        	beanName = "authentication";
        }

		// Spring の Application Context を取得
		WebApplicationContext springContext = WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());

		// コンテキスト・パラメータから、認証クラスの Bean　ID　を取得
		try {
			this.authentication = (Authentication)springContext.getBean(beanName);
		} catch (NoSuchBeanDefinitionException e){
			this.authentication = null;
			log.warn("Authentication bean not found !!!");
		}
    }

    
    
    /**
     * 後処理<br/>
     * プロパティのリセット<br/>
     */
    public void destroy() {
        this.loginFormURL = null;
    }

    
    
    /**
     * フィルターのメインロジック<br/>
     * セッションに、manualLoginTokenName で指定された値が存在するかをチェックし、存在した場合、<br/>
     * 手動でログインした物とみなす。　手動ログインしていない場合、ログイン画面へ誘導する。<br/>
     * このトークンは、AutoLoginAuth で、明示的なログインを行った時に値が設定され、ログアウト時に<br/>
     * 削除される。<br/>
     * <br/>
     * @param request HTTPリクエスト
     * @param　response HTTPレスポンス
     * @param　chain チェーン・オブジェクト
     * @throws IOException,　ServletException
     */
	@Override
	protected void filterAction(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		log.debug("Executing the ManualLoginCheckFilter");
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        // キャッシュが無効な場合用のヘッダ設定
        if (this.addNoCacheHeaders) {
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.setHeader("Pragma", "No-cache");
            httpResponse.setHeader("Cache-Control", "No-cache");
            httpResponse.setDateHeader("Expires", 1000);
        }

        if (this.manualLoginTokenName != null) {
            HttpSession session = httpRequest.getSession(false);
            if ((session == null) || (session.getAttribute(this.manualLoginTokenName) == null)) {
                // 手動ログインのトークンがセッションから見付からない場合
            	noManualLoginTokenFound(httpRequest, response, chain);
                return;
            }
        }
        // 手動ログインのトークンがセッションから見付かった場合
        manualLoginTokenFound(httpRequest, response, chain);
	}



    /**
     * 手動ログインをしていた場合の処理<br/>
     * 次のフィルターへチェーンする。<br/>
     * <br/>
     * @param request HTTPリクエスト
     * @param　response HTTPレスポンス
     * @param　chain チェーン・オブジェクト
     * @throws IOException,　ServletException
     */
    protected void manualLoginTokenFound(HttpServletRequest request, HttpServletResponse response, 
            FilterChain chain) throws IOException, ServletException {

    	// 次のフィルターに処理を継続する
    	LoginUser userBean = this.authentication.getLoggedInUser(request, response);
        Object userId = userBean.getUserId();
        log.info("Manual login check succeeded for user: " + userBean + " (userId=" + userId + ")");
        chain.doFilter(reassociateSavedRequestIfAvailable(request), response);

    }



    /**
     * 手動ログインをしていない場合の処理<br/>
     * チェーン先がフレームワークのコマンドクラスの場合、ビュー名を failure に設定して処理を中断する。<br/>
     * チェーン先が通常のフィルターの場合、コンテキスト・パラメータで取得した　URL にリダイレクトする。<br/>
     * <br/>
     * @param request HTTPリクエスト
     * @param　response HTTPレスポンス
     * @param　chain チェーン・オブジェクト
     * @throws IOException,　ServletException
     */
    protected void noManualLoginTokenFound(HttpServletRequest request, HttpServletResponse response, 
            FilterChain chain) throws IOException, ServletException {

    	LoginUser userBean = this.authentication.getLoggedInUser(request, response);
    	if (userBean != null) {
    		Object userId = userBean.getUserId();
    		log.info("Manual login check failed for user: " + userBean + " (userId=" + userId + 
    				"): saving request in session and forwarding to the login-form");
    	} else {
    		log.info("Manual login check failed for user: not logined");
    	}
    	
        String redirectURL = saveRequest(request);
        Map<String,Object> model = new HashMap<String,Object>(); 
        model.put("redirectURL", redirectURL);
        
        
        if (chain instanceof FilterCommandChain) {
            // チェーン先がフレームワークのコマンドの場合、failure のレスポンスを復帰して処理を中断する
        	((FilterCommandChain) chain).setResult(request, new ModelAndView("failure", model));

        } else if (this.loginFormURL != null) {
        	// web.xml から、通常のフィルターとして使用された場合の処理
        	// 指定された URL へリダイレクトする
            String replaced = SimpleExpressionLanguage.evaluateWebExpressionPatternString(
                    loginFormURL, request, response, getContext(), model, true, 
                    response.getCharacterEncoding(), "");
            response.sendRedirect(ServletUtils.fixPartialURL(replaced, request));

        } else {
        	// 飛び先が指定されていないのでシステムエラー画面へ
        	log.warn("Failed login, not inside spring, and no loginFormURL defined - sending 403");
            ((HttpServletResponse) response).sendError(HttpServletResponse.SC_FORBIDDEN);
        }
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
    protected HttpServletRequest reassociateSavedRequestIfAvailable(HttpServletRequest request) {
        return RequestRetryParams.reassociateSavedRequestIfAvailable(request, 
                this.savedRequestSessionParameterPrefix,
                this.savedRequestRequestParameter);
    }

    
	
    // setter、getter
    public void setManualLoginTokenName(String manualLoginTokenName) {
        this.manualLoginTokenName = manualLoginTokenName;
    }
	
	public void setAuthentication(Authentication pAuthentication) {
        this.authentication = pAuthentication;
    }
	
    public void setSavedRequestRequestParameter(String pSavedRequestRequestParameter) {
        this.savedRequestRequestParameter = pSavedRequestRequestParameter;
    }

    public void setSavedRequestSessionParameterPrefix(String pSavedRequestSessionParameterPrefix) {
        this.savedRequestSessionParameterPrefix = pSavedRequestSessionParameterPrefix;
    }
	
    public void setAddNoCacheHeaders(boolean pAddNoCacheHeaders) {
        this.addNoCacheHeaders = pAddNoCacheHeaders;
    }
}
