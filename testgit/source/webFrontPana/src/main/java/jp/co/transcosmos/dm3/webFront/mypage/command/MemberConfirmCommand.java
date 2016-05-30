package jp.co.transcosmos.dm3.webFront.mypage.command;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.core.vo.PrefMst;
import jp.co.transcosmos.dm3.corePana.model.PanaCommonManage;
import jp.co.transcosmos.dm3.corePana.model.member.form.MemberFormFactory;
import jp.co.transcosmos.dm3.corePana.model.member.form.MemberInfoForm;

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
 * zhang          2015.04.29	新規作成
 *
 * 注意事項
 *
 * </pre>
 */
public class MemberConfirmCommand extends jp.co.transcosmos.dm3.frontCore.mypage.command.MemberConfirmCommand {

	/** 共通情報取用 Model オブジェクト */
	private PanaCommonManage panamCommonManager;
	/** 会員情報用 Model(Core) オブジェクト */

	/**
	 * @param panamCommonManager セットする panamCommonManager
	 */
	public void setPanamCommonManager(PanaCommonManage panamCommonManager) {
		this.panamCommonManager = panamCommonManager;
	}


	/**
	 * 会員情報編集画面表示処理<br>
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

		if ("success".equals(modelAndView.getViewName())) {
			inputForm.setPrefName(this.panamCommonManager.getPrefName(inputForm.getPrefCd()));
			inputForm.setHopePrefName(this.panamCommonManager.getPrefName(inputForm.getHopePrefCd()));
		}
		// 都道府県マスタを取得する
 		List<PrefMst> prefMstList = this.panamCommonManager.getPrefMstList();
		model.put("prefMstList", prefMstList);

		model.put("inputForm", inputForm);

		return  modelAndView;
	}

	/**
	 * model オブジェクトを作成し、リクエストパラメータを格納した form オブジェクトを格納する。<br/>
	 * <br/>
	 * @param request HTTP リクエストパラメータ
	 * @return パラメータを設定した Form を格納した model オブジェクトを復帰する。
	 */
	@Override
	protected Map<String, Object> createModel(HttpServletRequest request) {

		Map<String, Object> model = new HashMap<>();

		// リクエストパラメータを取得して Form オブジェクトを作成する。
		MemberFormFactory factory = MemberFormFactory.getInstance(request);

		model.put("inputForm", factory.createMypageUserForm(request));

		return model;

	}


}