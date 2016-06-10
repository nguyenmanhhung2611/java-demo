package jp.co.transcosmos.dm3.webFront.inquiryDivision.command;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.corePana.model.inquiry.PanaHousingInquiryManageImpl;
import jp.co.transcosmos.dm3.form.FormPopulator;
import jp.co.transcosmos.dm3.login.LoginUser;
import jp.co.transcosmos.dm3.login.command.v3.LoginCommand;
import jp.co.transcosmos.dm3.login.v3.LockException;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;
import jp.co.transcosmos.dm3.mail.ReplacingMail;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

import org.springframework.web.servlet.ModelAndView;

/**
 * 物件のお問い合わせ完了画面
 *
 * <pre>
 * 【復帰する View 名】
 *    ・"success" : 正常終了
 *
 * 担当者       修正日      修正内容
 * ------------ ----------- -----------------------------------------------------
 * 郭中レイ    2015.04.23   新規作成
 *
 * 注意事項
 *
 * </pre>
 */
public class InquiryDivisionCommand extends LoginCommand {

	/** 物件問合せメンテナンスを行う Model オブジェクト */
	private PanaHousingInquiryManageImpl panaInquiryManager;

	/** 共通コード変換処理 */
	protected CodeLookupManager codeLookupManager;

	/** 新規通知メールテンプレート */
	private ReplacingMail sendInquiryHousingMail;
	/**
	 * 物件問合せメンテナンスを行う Model　オブジェクトを設定する。<br/>
	 * <br/>
	 *
	 * @param panaInquiryManager
	 *            物件問合せメンテナンスの model オブジェクト
	 */
	public void setPanaInquiryManager(PanaHousingInquiryManageImpl panaInquiryManager) {
		this.panaInquiryManager = panaInquiryManager;
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
	 * @param sendInquiryHousingMail
	 *            セットする sendInquiryHousingMail
	 */
	public void setSendInquiryHousingMail(ReplacingMail sendInquiryHousingMail) {
		this.sendInquiryHousingMail = sendInquiryHousingMail;
	}

	/**
	 * 物件リクエスト入力画面表示処理<br>
	 * <br>
	 *
	 * @param request
	 *            HTTP リクエスト
	 * @param response
	 *            HTTP レスポンス
	 */
	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		FormPopulator.populateFormBeanFromRequest(request, this.loginForm);

		request.setAttribute("sysHousingCd", request.getParameter("sysHousingCd"));

		List<ValidationFailure> errors = new ArrayList();
		if (!this.loginForm.validate(errors)) {
			return loginFailure(request, response, null, this.loginForm, errors);
		}
		LoginUser user = null;
		try {
			user = this.authentication.login(request, response, this.loginForm);
		} catch (LockException e) {
			errors.add(new ValidationFailure("userlock", "logincheck", null,
					null));
			return loginFailure(request, response, user, this.loginForm, errors);
		}
		if (user == null) {
			errors.add(new ValidationFailure("notfound", "logincheck", null,
					null));
			return loginFailure(request, response, user, this.loginForm, errors);
		}
		return loginSuccess(request, response, user, this.loginForm);
	}

}
