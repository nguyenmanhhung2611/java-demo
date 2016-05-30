package jp.co.transcosmos.dm3.webFront.inquiryGeneral.command;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.model.MypageUserManage;
import jp.co.transcosmos.dm3.core.util.FrontLoginUserUtils;
import jp.co.transcosmos.dm3.corePana.model.inquiry.form.PanaInquiryFormFactory;
import jp.co.transcosmos.dm3.corePana.model.inquiry.form.PanaInquiryGeneralForm;
import jp.co.transcosmos.dm3.corePana.vo.MemberInfo;
import jp.co.transcosmos.dm3.dao.JoinResult;
import jp.co.transcosmos.dm3.login.LoginUser;

import org.springframework.web.servlet.ModelAndView;

/**
 * 汎用お問い合わせ入力画面
 *
 * <pre>
 * 【復帰する View 名】
 *    ・"success" : 正常終了
 *
 * 担当者       修正日      修正内容
 * ------------ ----------- -----------------------------------------------------
 * qiao.meng	 2015.04.28    新規作成
 *
 * 注意事項
 *
 * </pre>
 */
public class InquiryGeneralInitCommand implements Command {

	/** 処理モード (init = 画面初期化、editBack = 再編集) */
	private String mode;

	/** マイページユーザーの情報を管理する Model オブジェクト */
	private MypageUserManage mypageUserManage;

	/**
	 * @param mode セットする mode
	 */
	public void setMode(String mode) {
		this.mode = mode;
	}

	/**
	 * @param mypageUserManage セットする mypageUserManage
	 */
	public void setMypageUserManage(MypageUserManage mypageUserManage) {
		this.mypageUserManage = mypageUserManage;
	}

	/**
	 * 汎用お問い合わせ入力画面表示処理<br>
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

		// command パラメータが "back" の場合、入力確認画面からの復帰なので、ＤＢから初期値を取得しない。
		// （リクエストパラメータから取得した値を使用する。）
		String command = inputForm.getCommand();
		if (command != null && "back".equals(command)) {

			return new ModelAndView("success", model);
		}

		// 初期表示の場合
		if ("init".equals(this.mode)) {
			String[] inquiryDtlType = {"001"};
			inputForm.getInquiryHeaderForm().setInquiryDtlType(inquiryDtlType);

			// ログインユーザーの情報を取得
			LoginUser loginUser = FrontLoginUserUtils.getInstance(request).getMypageLoginUserInfo(request, response);

			JoinResult userInfo = null;
			if (loginUser != null) {
				userInfo = this.mypageUserManage.searchMyPageUserPk(String.valueOf(loginUser.getUserId()));
			}

			// マイページ会員情報を取得
			if (userInfo != null){
				MemberInfo memberInfo = (MemberInfo)userInfo.getItems().get("memberInfo");
				if (memberInfo != null) {
					// お名前_姓
					inputForm.getInquiryHeaderForm().setLname(memberInfo.getMemberLname());
					// お名前_名
					inputForm.getInquiryHeaderForm().setFname(memberInfo.getMemberFname());
					// お名前（フリガナ）_姓
					inputForm.getInquiryHeaderForm().setLnameKana(memberInfo.getMemberLnameKana());
					// お名前（フリガナ）_名
					inputForm.getInquiryHeaderForm().setFnameKana(memberInfo.getMemberFnameKana());
					// メールアドレス
					inputForm.getInquiryHeaderForm().setEmail(memberInfo.getEmail());
					// 電話番号
					inputForm.getInquiryHeaderForm().setTel(memberInfo.getTel());
				}
			}
		}

		model.put("inputForm", inputForm);

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
