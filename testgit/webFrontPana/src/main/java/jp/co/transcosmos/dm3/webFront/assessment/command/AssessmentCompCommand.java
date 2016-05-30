package jp.co.transcosmos.dm3.webFront.assessment.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.constant.CommonParameters;
import jp.co.transcosmos.dm3.core.util.FrontLoginUserUtils;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonParameters;
import jp.co.transcosmos.dm3.corePana.model.PanaCommonManage;
import jp.co.transcosmos.dm3.corePana.model.assessment.form.AssessmentFormFactory;
import jp.co.transcosmos.dm3.corePana.model.assessment.form.AssessmentInputForm;
import jp.co.transcosmos.dm3.corePana.model.inquiry.AssessmentInquiryManageImpl;
import jp.co.transcosmos.dm3.corePana.model.inquiry.form.PanaInquiryFormFactory;
import jp.co.transcosmos.dm3.corePana.util.AffiliateUtil;
import jp.co.transcosmos.dm3.login.LoginUser;
import jp.co.transcosmos.dm3.mail.ReplacingMail;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

import org.springframework.web.servlet.ModelAndView;

/**
 * <pre>
 * 査定申込確認画面から登録
 *
 * 【復帰する View 名】
 *	・"success" : 正常終了
 *	・"input"   : バリデーションエラーによる再入力
 *
 * 担当者                  修正日                         修正内容
 * ------------ ----------- -----------------------------------------------------
 * チョ夢                     2015.04.29   新規作成
 * Thi Tran   2015.10.16    Add 'janetUrl' attribute to model
 *
 * 注意事項
 *
 * </pre>
 */
public class AssessmentCompCommand implements Command {

	/** 共通情報取用 Model オブジェクト */
	private PanaCommonManage panamCommonManager;

	/** 査定情報メンテナンスを行う Model オブジェクト */
	private AssessmentInquiryManageImpl assessmentInquiryManager;

	/** パスワード通知メールテンプレート */
	private ReplacingMail sendAssessmentInquiryTemplate;

	/**
	 * 共通情報取用 Model オブジェクトを設定する。<br/>
	 * <br/>
	 * @param PanaCommonManage 共通情報取用 Model オブジェクト
	 */
	public void setPanamCommonManager(PanaCommonManage panamCommonManager) {
		this.panamCommonManager = panamCommonManager;
	}

	/**
	 * @param assessmentInquiryManage セットする assessmentInquiryManager
	 */
	public void setAssessmentInquiryManager(
			AssessmentInquiryManageImpl assessmentInquiryManager) {
		this.assessmentInquiryManager = assessmentInquiryManager;
	}

	/**
	 * 査定のお申し込み受領メールテンプレートを設定する。<br/>
	 * 未設定の場合、メール送信を行わない。<br/>
	 * <br/>
	 * @param sendAssessmentMailTemplate 査定のお申し込み受領メールテンプレート
	 */
	public void setSendAssessmentInquiryTemplate(ReplacingMail sendAssessmentInquiryTemplate) {
		this.sendAssessmentInquiryTemplate = sendAssessmentInquiryTemplate;
	}



	/**
	 * 査定申込確認画面表示処理<br>
	 * <br>
	 * @param request HTTP リクエスト
	 * @param response HTTP レスポンス
	*/
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// リクエストパラメータを格納した model オブジェクトを生成する。
		// このオブジェクトは View 層への値引渡しに使用される。
		Map<String, Object> model = createModel(request);
		AssessmentInputForm inputForm = (AssessmentInputForm) model.get("inputForm");

		// 完了画面でリロードした場合、更新処理が意図せず実行される問題が発生する。
		// その問題を解消する為、view 名で "success"　を指定すると自動リダイレクト画面が表示される。
		// このリダイレクト画面は、command パラメータを "redirect"　に設定して完了画面へリクエストを
		// 送信する。
		// よって、command = "redirect" の場合は、ＤＢ更新は行わず、完了画面を表示する。
		String command = inputForm.getCommand();
		if (command != null && command.equals("redirect")){
			return new ModelAndView("comp" , model);
		}

		// Form 値の自動変換をする。
		inputForm.setAutoChange();

		// バリデーションを実行
		List<ValidationFailure> errors = new ArrayList<ValidationFailure>();
		if (!inputForm.validate(errors)){

			// バリデーションエラー時は、エラー情報を model に設定し、入力画面を表示する。
			model.put("errors", errors);

			// 都道府県Listの値を view 層へ渡すパラメータとして設定している。
			model.put("prefMst", this.panamCommonManager.getPrefMstList());

			return new ModelAndView("input", model);
		}

		// ログインユーザーの情報を取得
		String mypageUserId = null;
		LoginUser loginUser = FrontLoginUserUtils.getInstance(request).getMypageLoginUserInfo(request, response);
		if (loginUser == null) {
			loginUser = FrontLoginUserUtils.getInstance(request).getAnonLoginUserInfo(request, response);
		} else {
			// マイページのユーザーIDを取得
			mypageUserId = (String)loginUser.getUserId();
		}
 		// ユーザID（更新情報用）を取得
 		String editUserId = (String)loginUser.getUserId();

		// 査定情報を登録する。
		String inquiryId = this.assessmentInquiryManager.addInquiry(inputForm, mypageUserId, editUserId);

		// メール送信
		this.sendAssessmentInquiryTemplate.setParameter("inputForm", inputForm);
		this.sendAssessmentInquiryTemplate.setParameter("inquiryId", inquiryId);
		this.sendAssessmentInquiryTemplate.setParameter("commonParameters", CommonParameters.getInstance(request));
		this.sendAssessmentInquiryTemplate.send();

        // Get janet url
        PanaCommonParameters commonParamerers = PanaCommonParameters.getInstance(request);
        model.put("janetUrl", AffiliateUtil.getJanetURL(commonParamerers.getJanetUrl(),
                commonParamerers.getInquiryIdPrefix() + inquiryId, commonParamerers.getJanetAssessmentSid()));
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


		// 連絡可能な時間帯の再作成
		String reqParam = request.getParameter("contactTime");
		if (!StringValidateUtil.isEmpty(reqParam)) {
			String[] contactTime = reqParam.split(",");
			inputForm.setContactTime(contactTime);
		}
		// アンケート回答格納の再作成
		reqParam = request.getParameter("ansCd");
		if (!StringValidateUtil.isEmpty(reqParam)) {
			String[] ansCd = reqParam.split(",");
			inputForm.setAnsCd(ansCd);
		}

		model.put("inputForm", inputForm);

		return model;

	}

}
