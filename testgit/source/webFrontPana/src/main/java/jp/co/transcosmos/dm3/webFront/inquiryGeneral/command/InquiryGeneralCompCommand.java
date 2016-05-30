package jp.co.transcosmos.dm3.webFront.inquiryGeneral.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.constant.CommonParameters;
import jp.co.transcosmos.dm3.core.util.FrontLoginUserUtils;
import jp.co.transcosmos.dm3.corePana.model.inquiry.GeneralInquiryManageImpl;
import jp.co.transcosmos.dm3.corePana.model.inquiry.form.PanaInquiryFormFactory;
import jp.co.transcosmos.dm3.corePana.model.inquiry.form.PanaInquiryGeneralForm;
import jp.co.transcosmos.dm3.login.LoginUser;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;
import jp.co.transcosmos.dm3.mail.ReplacingMail;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

import org.springframework.web.servlet.ModelAndView;

/**
 * 汎用お問合せ完了画面
 *
 * <pre>
 * 【復帰する View 名】
 *    ・"success" : 正常終了
 *
 * 担当者       修正日      修正内容
 * ------------ ----------- -----------------------------------------------------
 * qiao.meng	 2015.04.28   新規作成
 *
 * 注意事項
 *
 * </pre>
 */
public class InquiryGeneralCompCommand implements Command {

	/** 汎用問合せメンテナンスを行う Model オブジェクト */
	private GeneralInquiryManageImpl generalInquiryManager;

	/** 共通コード変換処理 */
	protected CodeLookupManager codeLookupManager;

	/** 新規通知メールテンプレート */
	private ReplacingMail sendInquiryGeneralMail;



	/**
	 * @param generalInquiryManager セットする generalInquiryManager
	 */
	public void setGeneralInquiryManager(
			GeneralInquiryManageImpl generalInquiryManager) {
		this.generalInquiryManager = generalInquiryManager;
	}


	/**
	 * 共通コード変換オブジェクトを設定する。<br/>
	 * <br/>
	 *
	 * @param codeLookupManager
	 */
	public void setCodeLookupManager(CodeLookupManager codeLookupManager) {
		this.codeLookupManager = codeLookupManager;
	}


	/**
	 * @param sendInquiryGeneralMail セットする sendInquiryGeneralMail
	 */
	public void setSendInquiryGeneralMail(ReplacingMail sendInquiryGeneralMail) {
		this.sendInquiryGeneralMail = sendInquiryGeneralMail;
	}

	/**
	 * 汎用お問合せ完了画面表示処理<br>
	 * <br>
	 *
	 * @param request
	 *            HTTP リクエスト
	 * @param response
	 *            HTTP レスポンス
	 */
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Map<String, Object> model = createModel(request);
		PanaInquiryGeneralForm inputForm = (PanaInquiryGeneralForm)model.get("inputForm");

		String command = inputForm.getCommand();
		if (command !=null && "redirect".equals(command)){
        	return new ModelAndView("comp" , model);
		}
		// バリデーションを実行
		List<ValidationFailure> errors = new ArrayList<ValidationFailure>();
		if (!inputForm.validate(errors)) {

			// バリデーションエラーあり
			model.put("errors", errors);
			model.put("errorsSingle", errors);

			model.put("inputForm", inputForm);

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

		// 汎用お問い合わせのDB登録処理
		this.generalInquiryManager.addInquiry(inputForm, mypageUserId, editUserId);

		// メールテンプレートで使用するパラメータを設定する。
		this.sendInquiryGeneralMail.setParameter("inputForm",inputForm);
		this.sendInquiryGeneralMail.setParameter("commonParameters",CommonParameters.getInstance(request));

		// メール送信
		this.sendInquiryGeneralMail.send();

		// 取得したデータをレンダリング層へ渡す
		return new ModelAndView("success", model);
	}

	/**
	 * リクエストパラメータから Form オブジェクトを作成する。<br/>
	 * 生成した Form オブジェクトは Map に格納して復帰する。<br/>
	 * key = フォームクラス名（パッケージなし）、Value = フォームオブジェクト <br/>
	 *
	 * @param request
	 *            HTTP リクエストパラメータ
	 * @return パラメータが設定されたフォームオブジェクトを格納した Map オブジェクト
	 */
	private Map<String, Object> createModel(HttpServletRequest request) {

		Map<String, Object> model = new HashMap<>();
        // リクエストパラメータを取得して Form オブジェクトを作成する。
		PanaInquiryFormFactory factory = PanaInquiryFormFactory.getInstance(request);
		PanaInquiryFormFactory headerFactory = PanaInquiryFormFactory.getInstance(request);

		PanaInquiryGeneralForm inputForm = factory.createPanaInquiryGeneralForm(request);
		inputForm.setCommonInquiryForm(headerFactory.createInquiryHeaderForm(request));

		model.put("inputForm", inputForm);

		return model;

	}
}
