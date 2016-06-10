package jp.co.transcosmos.dm3.login.logging.v3;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.FilterConfig;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import jp.co.transcosmos.dm3.login.v3.Authentication;
import jp.co.transcosmos.dm3.login.LoginUser;
import jp.co.transcosmos.dm3.servlet.BaseFilter;
import jp.co.transcosmos.dm3.utils.SimpleExpressionLanguage;


/**
 * <pre>
 * V3 認証対応
 * レスポンスヘッダー追加フィルター
 * 
 * 担当者            修正日               修正内容
 * ----------------------------------------------------------------------------
 * H.Mizuno  2013.04.11  新規作成
 * 
 * 注意事項 
 *     実質的な処理内容は、V2 と全く同じです。
 * </pre>
*/
public class AddResponseHeadersFilter extends BaseFilter {
    private static final Log log = LogFactory.getLog(AddResponseHeadersFilter.class);

	// 認証ロジックオブジェクト
    private Authentication authentication;

    // 追加する、ヘッダー名
    private String headerName;

    // 追加するヘッダーの出力パターン
    private String headerPattern;



    /**
     * 通常のフィルターとして使用する場合の初期化処理<br/>
     * Spring の Application Context を取得し、認証ロジックオブジェクトを取得する。<br/>
     * <br/>
     * @param config フィルター設定情報
     * @throws ServletException
     */
	@Override
    public void init(FilterConfig config) throws ServletException {
		super.init(config);

		// 追加するヘッダー名の取得
        this.headerName = config.getInitParameter("headerName");
        if ((this.headerName == null) || this.headerName.equals("")) {
            this.headerName = "X-LoginId";
        }
        
		// 追加するヘッダー情報の出力パターンの取得
        this.headerPattern = config.getInitParameter("headerPattern");
        if ((this.headerPattern == null) || this.headerPattern.equals("")) {
            this.headerPattern = "###$user.loginId###";
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
     * フィルターのメイン処理<br/>
     * ヘッダー情報を作成し、レスポンスヘッダに追加する。<br/>
     * <br/>
     * @param config フィルター設定情報
     * @throws ServletException
     */
	@Override
	protected void filterAction(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
        // Build the header value and add it to the response
        String headerValue = SimpleExpressionLanguage.evaluateWebExpressionPatternString(
        		this.headerPattern, request, response, getContext(), 
                buildExtraParamsMap((HttpServletRequest) request, (HttpServletResponse) response), 
                false, response.getCharacterEncoding(), "");
        if ((headerValue != null) && !headerValue.equals("")) {
            response.addHeader(this.headerName, headerValue);
        }
        chain.doFilter(request, response);
	}


	
	/**
     * ログイン情報の取得処理<br/>
     * 認証用クラスを使用してログイン情報を取得する。<br/>
     * 認証用クラスが設定されていない場合、未ログイン時と同じ扱いとする。
     * <br/>
     * @param config フィルター設定情報
     * @throws ServletException
     */
	private Map<String,Object> buildExtraParamsMap(HttpServletRequest request, HttpServletResponse response) {

        Map<String,Object> out = new HashMap<String,Object>();

    	if (this.authentication == null){
            out.put("user", null);
            out.put("$user", null);
        } else {
            LoginUser user = this.authentication.getLoggedInUser(request, response);
            out.put("user", user);
            out.put("$user", user);
        }
        
        return out;
    }



	// setter、getter
	public void setAuthentication(Authentication authentication) {
		this.authentication = authentication;
	}

	public void setHeaderName(String headerName) {
		this.headerName = headerName;
	}

	public void setHeaderPattern(String headerPattern) {
		this.headerPattern = headerPattern;
	}

}
