package jp.co.transcosmos.dm3.webFront.mypage.command;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;

import jp.co.transcosmos.dm3.core.constant.CommonParameters;
import jp.co.transcosmos.dm3.core.model.mypage.MypageUserInterface;
import jp.co.transcosmos.dm3.core.model.mypage.form.MypageUserForm;
import jp.co.transcosmos.dm3.core.util.FrontLoginUserUtils;
import jp.co.transcosmos.dm3.core.vo.PrefMst;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonParameters;
import jp.co.transcosmos.dm3.corePana.model.PanaCommonManage;
import jp.co.transcosmos.dm3.corePana.model.member.form.MemberFormFactory;
import jp.co.transcosmos.dm3.corePana.util.AffiliateUtil;
import jp.co.transcosmos.dm3.dao.JoinResult;
import jp.co.transcosmos.dm3.login.LoginUser;

/**
 * <pre>
 * 会員情報確認画面
 * リクエストパラメータで渡された会員情報のバリデーションを行い、確認画面を表示する。
 * もしバリデーションエラーが発生した場合は入力画面として表示する。
 *
 * 【復帰する View 名】
 *    ・"success" : 正常終了
 *    ・"input" : バリデーションエラーによる再入力
 *
 * 担当者         修正日      修正内容
 * -------------- ----------- -----------------------------------------------------
 * zhang    	  2015.05.04	新規作成
 * Thi Tran       2015.10.16    Add 'janetUrl' attribute to model
 * Thi Tran       2015.10.22    Move 'janetUrl' to complete page
 * 注意事項
 *
 * </pre>
 */
public class MemberCompCommand extends jp.co.transcosmos.dm3.frontCore.mypage.command.MemberCompCommand {

	/** 共通情報取用 Model オブジェクト */
	private PanaCommonManage panamCommonManager;

	/**
	 * @param panamCommonManager セットする panamCommonManager
	 */
	public void setPanamCommonManager(PanaCommonManage panamCommonManager) {
		this.panamCommonManager = panamCommonManager;
	}

	/**
	 * 会員情報完了画面表示処理<br>
	 *
	 * @param request
	 *            クライアントからのHttpリクエスト。
	 * @param response
	 *            クライアントに返すHttpレスポンス。
	 */
	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// リクエストパラメータを格納した model オブジェクトを生成する。
		// このオブジェクトは View 層への値引渡しに使用される。
		Map<String, Object> model = createModel(request);
		MypageUserForm inputForm = (MypageUserForm) model.get("inputForm");

        // 完了画面でリロードした場合、更新処理が意図せず実行される問題が発生する。
        // その問題を解消する為、view 名で "success"　を指定すると自動リダイレクト画面が表示される。
        // このリダイレクト画面は、command パラメータを "redirect"　に設定して完了画面へリクエストを
        // 送信する。
        // よって、command = "redirect" の場合は、ＤＢ更新は行わず、完了画面を表示する。
        String command = inputForm.getCommand();
        if (command != null && "redirect".equals(command)){
        	return new ModelAndView("comp" , model);
        }
        
        // Display complete page with affiliate url if mode = comp
        if("comp".equals(this.mode)){
            // Get janet url
            LoginUser loginUser = FrontLoginUserUtils.getInstance(request).getMypageLoginUserInfo(request, response);
            PanaCommonParameters commonParamerers = PanaCommonParameters.getInstance(request);
            model.put("janetUrl", AffiliateUtil.getJanetURL(commonParamerers.getJanetUrl(),
                    loginUser.getUserId().toString(), commonParamerers.getJanetMemberSid()));
            return new ModelAndView("comp" , model);
        }
        // model オブジェクトを取得する。
        ModelAndView modelAndView = super.handleRequest(request, response);
        model = modelAndView.getModel();

        if ("input".equals(modelAndView.getViewName())) {
            // 都道府県マスタを取得する
            List<PrefMst> prefMstList = this.panamCommonManager.getPrefMstList();
            model.put("prefMstList", prefMstList);
        }
        // ログイン情報のセッション格納処理
        setLoggedInUser(request, response);

        return modelAndView;
	}

	/**
	 * パスワードの通知メールを送信する。<br/>
	 * <br/>
	 * @param request HTTP リクエスト
	 * @param inputForm 入力値
	 */
	@Override
	protected void sendPwdMail(HttpServletRequest request, MypageUserForm inputForm){
		if ("insert".equals(this.mode)) {
			super.sendPwdMail(request, inputForm);
		}
	}

	/**
	 * model オブジェクトを作成し、リクエストパラメータを格納した form オブジェクトを格納する。<br/>
	 * <br/>
	 * @param request HTTP リクエストパラメータ
	 * @return パラメータを設定した Form を格納した model オブジェクトを復帰する。
	 */
	@Override
	protected Map<String, Object> createModel(HttpServletRequest request) {

		Map<String, Object> model = new HashMap<String, Object>();

		// リクエストパラメータを取得して Form オブジェクトを作成する。
		MemberFormFactory factory = MemberFormFactory.getInstance(request);

		model.put("inputForm", factory.createMypageUserForm(request));

		return model;

	}

	/**
	 * ログイン情報のセッション格納処理<br/>
	 * <br/>
	 * @param request HTTP リクエスト
	 * @param response クライアントに返すHttpレスポンス。
	 */
	private void setLoggedInUser(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// 更新モードの場合、ログイン情報をセッションに格納
		if ("update".equals(this.mode)) {
			// ログインユーザーの旧情報を取得する
			MypageUserInterface oldLoginUser = FrontLoginUserUtils.getInstance(request).getMypageLoginUserInfo(
					request, response);

			// マイページ会員情報を取得
	        JoinResult mypageUser = this.userManager.searchMyPageUserPk((String)oldLoginUser.getUserId());

	        CommonParameters commonParameters = CommonParameters.getInstance(request);

	        MypageUserInterface newLoginUser =
	        		(MypageUserInterface) mypageUser.getItems().get(commonParameters.getMemberDbAlias());

	        HttpSession session = request.getSession();
			session.setAttribute("loggedInUser", newLoginUser);
		}
	}
}