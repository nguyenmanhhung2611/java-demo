package jp.co.transcosmos.dm3.webFront.assessment.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.corePana.model.PanaCommonManage;
import jp.co.transcosmos.dm3.corePana.model.assessment.form.AssessmentFormFactory;
import jp.co.transcosmos.dm3.corePana.model.assessment.form.AssessmentInputForm;
import jp.co.transcosmos.dm3.corePana.model.inquiry.form.PanaInquiryFormFactory;
import jp.co.transcosmos.dm3.corePana.util.PanaStringUtils;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

import org.springframework.web.servlet.ModelAndView;

/**
 * <pre>
 * 査定申込画面の新規登録
 *
 * 【復帰する View 名】
 *	・"success" : 正常終了
 *	・"input"   : バリデーションエラーによる再入力
 *
 * 担当者	   修正日	  修正内容
 * ------------ ----------- -----------------------------------------------------
 * チョ夢	   2015.04.29  新規作成
 *
 * 注意事項
 *
 * </pre>
 */
public class AssessmentConfirmCommand implements Command {

	/** 共通情報取用 Model オブジェクト */
	private PanaCommonManage panamCommonManager;

	/**
	 * 共通情報取用 Model オブジェクトを設定する。<br/>
	 * <br/>
	 * @param PanaCommonManage 共通情報取用 Model オブジェクト
	 */
	public void setPanamCommonManager(PanaCommonManage panamCommonManager) {
		this.panamCommonManager = panamCommonManager;
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

		// 要望・質問（表示用）を設定
		inputForm.setRequestTextHyoji(PanaStringUtils.encodeHtml(inputForm.getRequestText()));
		// Form 値の自動変換をする。
		inputForm.setAutoChange();

		// view 名の初期値を設定
		String viewName = "success";

		// バリデーションを実行
		List<ValidationFailure> errors = new ArrayList<ValidationFailure>();
		if (!inputForm.validate(errors)){

			// バリデーションエラー時は、エラー情報を model に設定し、入力画面を表示する。
			model.put("errors", errors);

			// 都道府県Listの値を view 層へ渡すパラメータとして設定している。
			model.put("prefMst", this.panamCommonManager.getPrefMstList());

			viewName = "input";
		}

		// 都道府県名を取得し設定する。
		inputForm.setPrefName(this.panamCommonManager.getPrefName(inputForm.getPrefCd()));
		if (!StringValidateUtil.isEmpty(inputForm.getUserPrefCd())) {
			inputForm.setUserPrefName(this.panamCommonManager.getPrefName(inputForm.getUserPrefCd()));
		}

		return new ModelAndView(viewName, model);

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
