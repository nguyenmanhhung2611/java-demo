package jp.co.transcosmos.dm3.core.login.command;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;

import jp.co.transcosmos.dm3.login.command.v3.LoginCommand;
import jp.co.transcosmos.dm3.utils.CommonLogging;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

/**
 * ログ出力機能付き認証処理クラス.
 * <p>
 * V3 認証用のコマンドクラスを直接継承しているので、他のバージョンを使用する場合は別途作成する事。<br/>
 * ログ出力には Frame work の SimpleLogging を使用。<br/>
 * <p>
 * <pre>
 * 担当者		修正日		修正内容
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.03.31	新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 * 
 */
public class LoggingLoginCommand extends LoginCommand {

	private static final Log log = LogFactory.getLog(LoggingLoginCommand.class);

	/** ログ出力処理 */
	protected CommonLogging logging;
	
	/**
	 * ログ出力処理を設定する。<br/>
	 * <br/>
	 * @param logging ログ出力処理
	 */
	public void setLogging(CommonLogging logging) {
		this.logging = logging;
	}



	/**
	 * ログイン処理<br/>
	 * <br/>
	 * @param request HTTP リクエスト
	 * @param response HTTP レスポンス
	 * 
	 * @return 継承元が復帰する ModelAndView
	 */
	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		// 本来のログイン処理を実行
		ModelAndView modelAndView = super.handleRequest(request, response); 

		// View 名を取得する。
		// ログインが成功した場合は、"success" が設定される。　それ以外の場合は全てエラーとして扱う。
		String viewName = modelAndView.getViewName();

		// ログインID を取得する。
		// 本来は、V3 認証用フォームから取得すべきだが、ログイン失敗時と成功時で取得方法が異なる。
		// 欲しいのはログインID のみなので HTTP Request から直接取得する。
		String loginId = request.getParameter("loginID");

		//ログ出力
		@SuppressWarnings("unchecked")
		String msg = createMsg(request, viewName, loginId, (List<ValidationFailure>)modelAndView.getModel().get("errors"));
		log.info(msg);
		this.logging.write(msg);

		return modelAndView;
	}



	/**
	 * ログ出力文字列の生成<br/>
	 * <br/>
	 * @param request HTTP リクエスト
	 * @param viewName ビュー名
	 * @param loginId 入力されたログインID
	 * @param errors エラーオブジェクト
	 * 
	 * @return　ログ出力メッセージ
	 */
	protected String createMsg(HttpServletRequest request, String viewName, String loginId, List<ValidationFailure> errors){
		
		// 認証が成功している場合は、成功メッセージを復帰
		if ("success".equals(viewName)){
			return "login is success (id=" + loginId + ")";

		}
		

		// エラーメッセージが設定されている場合、エラーメッセージのタイプからアカウントロックが発生しているのかを
		// 判定する。
		if (errors != null) {
			boolean locked = false;
			for (ValidationFailure failuer : errors){
				if ("userlock".equals(failuer.getType())) {
					locked = true;
					break;
				}
			}
			
			if (locked){
				return "login user is locked (id=" + loginId + ")";
			}
		}


		// 通常のログインエラーとして復帰。
		return "login is failed (id=" + loginId + ")";
	}

}
