package jp.co.transcosmos.dm3.webFront.assessment.command;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.model.MypageUserManage;
import jp.co.transcosmos.dm3.core.util.FrontLoginUserUtils;
import jp.co.transcosmos.dm3.corePana.model.PanaCommonManage;
import jp.co.transcosmos.dm3.corePana.model.assessment.form.AssessmentFormFactory;
import jp.co.transcosmos.dm3.corePana.model.assessment.form.AssessmentInputForm;
import jp.co.transcosmos.dm3.corePana.model.inquiry.form.PanaInquiryFormFactory;
import jp.co.transcosmos.dm3.dao.JoinResult;
import jp.co.transcosmos.dm3.login.LoginUser;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;

import org.springframework.web.servlet.ModelAndView;

/**
 * <pre>
 * 査定申込画面の初期化
 * 都道府県情報を取得。
 *
 * 【復帰する View 名】
 *	・"success" : 正常終了
 *
 * 担当者	   修正日	  修正内容
 * ------------ ----------- -----------------------------------------------------
 * チョ夢	   2015.04.27  新規作成
 *
 * 注意事項
 *
 * </pre>
 */
public class AssessmentInputCommand implements Command {

	/** 共通情報取用 Model オブジェクト */
	private PanaCommonManage panamCommonManager;

	/** マイページユーザーの情報を管理する Model オブジェクト */
	private MypageUserManage mypageUserManage;

	/**
	 * 共通情報取用 Model オブジェクトを設定する。<br/>
	 * <br/>
	 * @param PanaCommonManage 共通情報取用 Model オブジェクト
	 */
	public void setPanamCommonManager(PanaCommonManage panamCommonManager) {
		this.panamCommonManager = panamCommonManager;
	}

	/**
	 * マイページユーザーの情報を管理する Model オブジェクトを設定する。<br/>
	 * <br/>
	 * @param MypageUserManage マイページユーザーの情報を管理する Model オブジェクト
	 */
	public void setMypageUserManage(MypageUserManage mypageUserManage) {
		this.mypageUserManage = mypageUserManage;
	}

	/**
	 * 査定申込画面の初期化表示処理<br>
	 * <br>
	 *
	 * @param request
	 *			クライアントからのHttpリクエスト。
	 * @param response
	 *			クライアントに返すHttpレスポンス。
	 */
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// リクエストパラメータを格納した model オブジェクトを生成する。
		// このオブジェクトは View 層への値引渡しに使用される。
		Map<String, Object> model = createModel(request);
		AssessmentInputForm inputForm = (AssessmentInputForm) model.get("inputForm");

		String command = inputForm.getCommand();
		if (StringValidateUtil.isEmpty(command)) {

			// 「売却物件と同じ」の初期化
			model.put("sameWithHousingFG", true);

			// マイページ会員情報を取得
			LoginUser loginUser = FrontLoginUserUtils.getInstance(request).getMypageLoginUserInfo(request, response);
			JoinResult userInfo = null;
			if (loginUser != null) {
				userInfo = mypageUserManage.searchMyPageUserPk((String)loginUser.getUserId());
			}

			// Form へ初期値を設定する。
			inputForm.setDefaultData(userInfo);

		}

		// 都道府県Listの値を view 層へ渡すパラメータとして設定している。
		model.put("prefMst", this.panamCommonManager.getPrefMstList());

		model.put("inputForm", inputForm);

		return new ModelAndView("success", model);
	}

	/**
	 * model オブジェクトを作成し、リクエストパラメータを格納した form オブジェクトを格納する。<br/>
	 * <br/>
	 * @param request HTTP リクエストパラメータ
	 * @return パラメータを設定した Form を格納した model オブジェクトを復帰する。
	 */
	protected Map<String, Object> createModel(HttpServletRequest request) {

		Map<String, Object> model = new HashMap<>();

		// リクエストパラメータを取得して Form オブジェクトを作成する。
		AssessmentFormFactory factory = AssessmentFormFactory.getInstance(request);
		PanaInquiryFormFactory headerFactory = PanaInquiryFormFactory.getInstance(request);

		AssessmentInputForm inputForm = factory.createAssessmentInputForm(request);
		inputForm.setCommonInquiryForm(headerFactory.createInquiryHeaderForm(request));

		model.put("inputForm", inputForm);

		return model;

	}
}
