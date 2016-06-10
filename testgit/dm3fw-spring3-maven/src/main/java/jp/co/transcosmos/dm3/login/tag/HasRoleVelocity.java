package jp.co.transcosmos.dm3.login.tag;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.co.transcosmos.dm3.login.UserRoleSet;
import jp.co.transcosmos.dm3.login.v3.Authentication;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.tools.Scope;
import org.apache.velocity.tools.ToolContext;
import org.apache.velocity.tools.config.DefaultKey;
import org.apache.velocity.tools.config.ValidScope;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;



/**
 * <pre>
 * Ver 3 認証対応、Velocity 用ロールチェック処理
 * dm3login 共通タグライブラリの Velocity 版。
 * velocity.tols.xml の、request スコープに登録して使用する事。
 *
 * 担当者            修正日               修正内容
 * ----------------------------------------------------------------------------
 * H.Mizuno  2013.04.18  新規作成
 *
 * </pre>
*/
@DefaultKey("dm3login")
@ValidScope(Scope.REQUEST)
public class HasRoleVelocity {
    private static final Log log = LogFactory.getLog(HasRoleVelocity.class);

    // ロールを管理している、セッションの Key （V1、V2 認証用）
    private String rolesetSessionParameter = "loggedInUserRoleset";

    // HTTP リクエストオブジェクト
    protected HttpServletRequest request;

    // HTTP レスポンスオブジェクト
    protected HttpServletResponse response;

    // サーブレット・コンテキスト
    protected ServletContext servletContext;

    
    /**
     * 共通関数初期化処理<br/>
     * このメソッドを定義すると、Velocity から ToolContext を受取る事ができる。<br />
     * ToolContext には、HttpRequest や、ServletContext を格納しているので、必要で<br />
     * あれば、このメソッドを定義する。<br />
     * <br/>
     * @param values Velocity から渡されるプロパティ情報
     */
    protected void configure(Map<?,?> values)
    {
		log.debug("velocity dm3login init");

		// ToolContext を取得し、ローカルコンテキストから、Web 関連のオブジェクトを取得。
		ToolContext toolContext = (ToolContext)values.get("velocityContext");
    	this.servletContext = (ServletContext)toolContext.get("servletContext");
    	this.request = (HttpServletRequest)toolContext.get("request");
    	this.response = (HttpServletResponse)toolContext.get("response");
    }

    
    
    /**
     * ロールチェック処理<br/>
     * 認証用 Bean ID を省略しているので、V1、V2 認証専用。<br />
     * <br/>
     * @param pRoleName チェック対象となるロール名（カンマ区切りで複数指定可）
     * @return ロール名が見付かった場合 true 、見付からない場合　false
     */
	public boolean hasRole(String pRoleName){
		return hasRole(pRoleName, null);
	}



    /**
     * ロールチェック処理<br/>
     * 認証用 Bean ID が省略された場合、V1、V2 認証としてセッションからロール名を検索する。<br />
     * 認証用 Bean ID が指定されている場合、Spring から認証用オブジェクトを取得し、認証チェックを<br />
     * 行う。<br />
     * <br/>
     * @param pRoleName チェック対象となるロール名（カンマ区切りで複数指定可）
     * @param pAuthId 認証用オブジェクトの Bean ID
     * @return ロール名が見付かった場合 true 、見付からない場合　false
     */
	public boolean hasRole(String pRoleName, String pAuthId){
		log.debug("Velocity has role check start. roleName:" +  pRoleName + "authId:" + pAuthId);

		UserRoleSet roleset;
		if (pAuthId != null && pAuthId.length() != 0){
			// Ver 3  認証
			roleset = getRoleSetFromAuthenticate(pAuthId);
		} else {
			// Ver 1、2 認証
			roleset = getRoleSetFromRequest();
		}

		// この判定は、HasRoleCheckCommand と比べると、若干、判定基準が異なる。
		// （HasRoleCheckCommand は、null を true 判定する。）
		// 将来、HasRoleCheckCommand 側を、この判定基準に合わせる可能性がある。
		if (roleset != null && roleset.hasRole(getRoles(pRoleName))) {
			log.debug("role name is found");
			return true;
        } else {
			log.debug("role name is not found");
            return false;
        }
	}



    /**
     * ロールオブジェクト取得処理（V1、V2 用）<br/>
     * セッション内から、ロールオブジェクトを取得して復帰する。<br />
     * <br/>
     * @return ロールオブジェクト
     */
    protected UserRoleSet getRoleSetFromRequest() {
		log.debug("check is v1, v2 authentication");

        HttpSession session = this.request.getSession();
        if (session != null) {
            return (UserRoleSet) session.getAttribute(this.rolesetSessionParameter);
        } else {
            log.warn("WARNING: Roleset not found in session scope - returning NULL");
            return null;
        }
    }

	
    
    /**
     * ロールオブジェクト取得処理（V3 用）<br/>
     * Spring から取得した認証用オブジェクトを使用してロールチェックを行う。<br />
     * 認証用オブジェクトが復帰するロールオブジェクトを復帰する。<br/>
     * @param pAuthId 認証用オブジェクトの Bean ID
     * @return ロールオブジェクト
     */
	protected UserRoleSet getRoleSetFromAuthenticate(String pAuthId){
		log.debug("check is v3 authentication");

		// Spring の Application Context を取得
		WebApplicationContext springContext = WebApplicationContextUtils.getWebApplicationContext(this.servletContext);
		
		// Spring コンテキストから 認証クラスを取得
		try {
			Authentication authentication = (Authentication)springContext.getBean(pAuthId);
			if (authentication == null) return null;

			return authentication.getLoggedInUserRole(this.request, this.response);
			
		} catch (NoSuchBeanDefinitionException e){
			log.warn("Authentication bean not found !!!");
		}
		return null;
    }


	
    /**
     * ロール名の分割処理<br/>
     * 指定されたロール名を、カンマ（,）記号で分割し、コレクション・オブジェクトを復帰する。<br />
     * @param ロール名（テンプレートから渡されたロール名）
     * @return リスト化されたロール名
     */
	protected Set<String> getRoles(String pRoleName) {
        // Tokenize the role names
        StringTokenizer st = new StringTokenizer(pRoleName, ",");
        Set<String> roles = new HashSet<String>();
        while (st.hasMoreTokens()) {
            String token = st.nextToken();
            if (token != null) {
                roles.add(token);
            }
        }
        return roles;
    }

}
