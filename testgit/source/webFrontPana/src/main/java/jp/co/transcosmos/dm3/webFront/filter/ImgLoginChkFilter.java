package jp.co.transcosmos.dm3.webFront.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import jp.co.transcosmos.dm3.login.LoginUser;
import jp.co.transcosmos.dm3.login.v3.Authentication;
import jp.co.transcosmos.dm3.servlet.BaseFilter;



/**
 * 会員限定画像のチェックフィルター.
 * マイページへの認証が完了しているかをチェックし、認証済の場合、Chain 先を実行する。<br/>
 * もし認証が完了していない場合、ステータス 403 を復帰する。<br/>
 * 画像フォルダの認証チェックは、通常のマイページ認証チェックフィルターとは異なり、自動ログインは行わない。<br/>
 * <p>
 * <pre>
 * 担当者		修正日		修正内容
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.05.19	新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 * 
 */
public class ImgLoginChkFilter extends BaseFilter {

	// 認証ロジックオブジェクト
    private Authentication authentication;

	
	
	/**
	 * Filter の初期化処理<br/>
	 * web.xml からパラメータが設定された場合、その値で Filter を初期化する。<br/>
	 * <br/>
	 * @param config パラメータオブジェクト
	 * @exception ServletException
	 * 
	 */
    @Override
	public void init(FilterConfig config) throws ServletException {

		// 認証処理クラスの bean ID の取得
        String beanName = config.getInitParameter("authentication");
        if ((beanName == null) || beanName.equals("")) {
        	beanName = "authentication";
        }

		// Spring の Application Context を取得
		WebApplicationContext springContext = WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());

		// Spring コンテキストから 認証クラスを取得
		this.authentication = (Authentication)springContext.getBean(beanName);
        
    }
	


    /**
     * Filter の実質的な処理<br/>
     * ログイン認証されていない場合、ステータス 403 を復帰する。<br/>
     * 認証済チェックの際、自動ログインは行わない。<br/>
     * <br/>
     * @param request HTTP リクエスト
     * @param response HTTP レスポンス
     * @param chain Chain オブジェクト
     * 
     * @exception IOException
     * @exception ServletException
     */
	@Override
	protected void filterAction(HttpServletRequest request,	HttpServletResponse response, FilterChain chain)
			throws IOException,	ServletException {

		// 特別なキャッシュコントロールはこのフィルター内からは行わない。
		// キャッシュコントロールは、Apache 側の環境設定にて行う事を前提とする。

        // ログイン済みの場合は、次のコマンドへチェーンする。
		// なお、画像の認証フィルターでは、checkLoggedIn() ではなく、getLoggedInUser() を使用する。
		// checkLoggedIn() は、自動ログインが実行される可能性があり、web.xml から Filter として使用
		// する事はできない。
        LoginUser user = authentication.getLoggedInUser(request, response);

		if (user != null){
			// 認証情報が取得できた場合、Chain 先を実行する。
			chain.doFilter(request, response);
		} else {
			// 認証先が取得できない場合、respose をリセットしてステータス 403 を復帰する。
			response.reset();
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		}

	}

}
