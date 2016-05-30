package jp.co.transcosmos.dm3.login.command.v3;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

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

import jp.co.transcosmos.dm3.login.UserRoleSet;
import jp.co.transcosmos.dm3.login.v3.Authentication;
import jp.co.transcosmos.dm3.servlet.BaseFilter;
import jp.co.transcosmos.dm3.servlet.FilterCommandChain;


/**
 * <pre>
 * V3 認証対応
 * ロールによるアクセス権チェックコマンド（フィルター）
 * ログインユーザーのロール情報を取得し、このフィルターの roles プロパティに設定されたロール名
 * と一致するかを照合する。
 * 
 * 担当者            修正日               修正内容
 * ----------------------------------------------------------------------------
 * H.Mizuno  2013.05.07  新規作成
 * 
 * 注意事項 
 *     実質的な処理内容は、V2 と全く同じです。
 * </pre>
*/
public class HasRoleCheckFilter extends BaseFilter {
    private static final Log log = LogFactory.getLog(HasRoleCheckFilter.class);

    // このフィルタが許可するロール名のコレクション
    private Collection<String> roles;

    // 認証ロジックオブジェクト
    private Authentication authentication;

	

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
    
    
		// アクセスを許可するロール名を取得する
		// ロール名は、カンマ区切りで複数指定する事が可能。
        String wkRoles = config.getInitParameter("roles");
        this.roles = Arrays.asList(wkRoles.split(",")); 

    }
	


    /**
     * フィルターのメインロジック<br/>
     * 提供された認証用クラスを使用してログインユーザーのロール情報を取得する。<br/>
     * 取得したロール情報が、このフィルターに設定されたロール情報にマッチする場合、次のフィルタ<br/>
     * へチェーンする。<br/>
     * 取得したロール情報が存在しない場合、ビュー名を failure にして処理を中断する。<br/>
     * （フィルターとして使用している場合は、403 のレスポンスを復帰する。）
     * <br/>
     * @param request HTTPリクエスト
     * @param　response HTTPレスポンス
     * @param　chain チェーン・オブジェクト
     * @return レンダリングで使用する ModelAndView オブジェクト
     * @throws Exception,　ServletException
     */
    @Override
	protected void filterAction(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		// ログインユーザーのロール情報を取得
        UserRoleSet roleset = this.authentication.getLoggedInUserRole(request, response);


		// ロールが取得できた場合は照合する。
        // 見付かれば、なにもせずに終了し、見付からない場合、レスポンスを設定してチェーンを中断 する。
        if (roleset == null) {
        	// 認証クラスの戻り値が null の場合、ロールチェック対象外として、認証OKとする。
        	// よって、ロールを１件も持っていない場合でも、空の UserRoleSet を返す必要がある。
        	// 将来、この仕様は変更する可能性があります。
        	log.warn("Roleset not found - not logged in ? Allowing, because we only" +
                    " want to apply this rolecheck to logged in users - wrap an extra " +
                    "CookieLoginCheckFilter around this when you need a login-only check");
        	
        	chain.doFilter(request, response);
        	return;

        } else if (roleset.hasRole(this.roles)) {
        	// ロールがあるので、先のフィルターへチェーンする。
            log.info("User has defined role - allowing");
        	chain.doFilter(request, response);
            return;

        } else {
            log.info("User does not have defined role - rejecting");
            
            if (chain instanceof FilterCommandChain) {
            	// 通常は、こちらの処理。　結果をセットして、チェーンを中断する。
            	((FilterCommandChain) chain).setResult(request, new ModelAndView("failure", "neededRole", this.roles));
            } else {
            	// web.xml から、通常のフィルターとして使用された場合の処理
            	((HttpServletResponse) response).sendError(HttpServletResponse.SC_FORBIDDEN);
            }
        }
	}

    

    // setter、getter
    public void setRoles(Collection<String> pRoles) {
        this.roles = pRoles;
    }

    public void setRole(String pRole) {
        this.roles = new ArrayList<String>();
        this.roles.add(pRole);
    }

    public void setAuthentication(Authentication pAuthentication) {
        this.authentication = pAuthentication;
    }

}
