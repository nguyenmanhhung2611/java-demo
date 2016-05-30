package jp.co.transcosmos.dm3.webAdmin.memberInfo.command;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.adminCore.mypage.command.MypageCompCommand;
import jp.co.transcosmos.dm3.core.constant.CommonParameters;
import jp.co.transcosmos.dm3.core.model.mypage.form.MypageUserForm;
import jp.co.transcosmos.dm3.core.vo.PrefMst;
import jp.co.transcosmos.dm3.corePana.model.PanaCommonManage;
import jp.co.transcosmos.dm3.corePana.model.member.form.MemberInfoForm;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;

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
 * tang.tianyun	  2015.04.13	新規作成
 *
 * 注意事項
 *
 * </pre>
 */
public class MemberInfoCompCommand extends MypageCompCommand {

	private static final Log log = LogFactory.getLog(MemberInfoCompCommand.class);

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
        // model オブジェクトを取得する。
        ModelAndView modelAndView = super.handleRequest(request, response);
        Map<String, Object> model = modelAndView.getModel();

		MemberInfoForm inputForm = (MemberInfoForm) model.get("inputForm");

        String command = inputForm.getCommand();
        if (command != null && command.equals("redirect")){
        	return new ModelAndView("comp" , model);
        }


		if ("input".equals(modelAndView.getViewName())) {
			// 都道府県マスタを取得する
	 		List<PrefMst> prefMstList = this.panamCommonManager.getPrefMstList();
			model.put("prefMstList", prefMstList);
		}
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

		// メールテンプレートが設定されていない場合はメールの通知を行わない。
		if (this.sendMypagePasswordTemplate == null) {
			log.warn("sendMypagePasswordTemplate is null.");
			return;
		}

		// 新規登録時メールを送信する。
		if (!"insert".equals(this.mode)){
			return;
		}

		// メールテンプレートで使用するパラメータを設定する。
		this.sendMypagePasswordTemplate.setParameter("inputForm", inputForm);
		this.sendMypagePasswordTemplate.setParameter("commonParameters", CommonParameters.getInstance(request));

		// メール送信
		this.sendMypagePasswordTemplate.send();
	}
}