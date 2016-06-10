package jp.co.transcosmos.dm3.login.command.v3;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import jp.co.transcosmos.dm3.command.Command;


/**
 * <pre>
 * Ver 3 認証対応用ログイン初期表示コマンドクラス
 * ログイン画面を初期表示する場合に使用するコマンドクラス。
 * LoginCheckFilter を 通常 Filter として使用する場合、ログイン画面のリダイレクト先として
 * 使用する。
 *
 * 担当者            修正日               修正内容
 * ----------------------------------------------------------------------------
 * H.Mizuno  2013.05.07  新規作成
 *
 * </pre>
*/
public class InitLoginCommand  implements Command {

	// リダイレクト先 URL パラメータ名
	protected String redirectURLParameter = "redirectURL";

	
	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("init", "redirectURL", getRedirectURL(request));
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

}
