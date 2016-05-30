package jp.co.transcosmos.dm3.login.command.v3;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.login.v3.Authentication;
import jp.co.transcosmos.dm3.login.UserRoleSet;

/**
 * <pre>
 * V3 認証対応
 * ロールによるアクセス権チェックコマンド（フィルター）
 * ログインユーザーのロール情報を取得し、このフィルターの roles プロパティに設定されたロール名
 * と一致するかを照合する。
 * 
 * 担当者            修正日               修正内容
 * ----------------------------------------------------------------------------
 * H.Mizuno  2013.04.05  新規作成
 * H.Mizuno  2013.04.05  ロール名をコレクションで設定する場合、オーバーロードが正しく機能しない
 *                       ので、role = 単一、roles = リストに固定化
 * H.Mizuno  2013.05.07  Ver 1.1 から非推奨になったのでコメントを変更
 * 
 * 注意事項 
 *     実質的な処理内容は、V2 と全く同じです。
 * </pre>
 * @deprecated HasRoleCheckFilter の使用を推奨します。今後、メンテナンスされない可能性があります。
*/
public class HasRoleCheckCommand implements Command {
    private static final Log log = LogFactory.getLog(HasRoleCheckCommand.class);

    // このフィルタが許可するロール名のコレクション
    private Collection<String> roles;

    // 認証ロジックオブジェクト
    private Authentication authentication;

    
    /**
     * メインロジック<br/>
     * 提供された認証用クラスを使用してログインユーザーのロール情報を取得する。<br/>
     * 取得したロール情報が、このフィルターに設定されたロール情報にマッチする場合、そのまま<br/>
     * 復帰する。　（先の処理に進む）<br/>
     * 取得したロール情報が存在しない場合、ビュー名を failure にして処理を中断する。<br/>
     * <br/>
     * @param request HTTPリクエスト
     * @param　response HTTPレスポンス
     * @return レンダリングで使用する ModelAndView オブジェクト
     * @throws Exception
     */
	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

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
            return null;
        } else if (roleset.hasRole(this.roles)) {
            log.info("User has defined role - allowing");
            return null;
        } else {
            log.info("User does not have defined role - rejecting");
            return new ModelAndView("failure", "neededRole", this.roles);
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
