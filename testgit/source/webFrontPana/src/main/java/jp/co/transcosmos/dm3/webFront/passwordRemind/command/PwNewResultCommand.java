package jp.co.transcosmos.dm3.webFront.passwordRemind.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.constant.CommonParameters;
import jp.co.transcosmos.dm3.core.model.MypageUserManage;
import jp.co.transcosmos.dm3.core.model.exception.NotFoundException;
import jp.co.transcosmos.dm3.core.util.FrontLoginUserUtils;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonParameters;
import jp.co.transcosmos.dm3.corePana.model.member.PanaMypageUserManageImpl;
import jp.co.transcosmos.dm3.corePana.model.passwordRemind.form.PasswordChangeForm;
import jp.co.transcosmos.dm3.corePana.model.passwordRemind.form.PasswordFormFactory;
import jp.co.transcosmos.dm3.mail.ReplacingMail;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

import org.springframework.web.servlet.ModelAndView;

/**
 * <pre>
 * パスワードリマインダー
 * パスワードリマインダー画面を表示する。
 *
 * 【復帰する View 名】
 *    ・"success" : 正常終了
 *
 * 担当者       修正日     修正内容
 * ------------ ----------- -----------------------------------------------------
 * 焦     2015.04.24  新規作成
 *
 * 注意事項
 *
 * </pre>
 */
public class PwNewResultCommand implements Command {

	public static final String RESULT_URL = "/mypage/login/";

	/** マイページユーザーの情報を管理する Model オブジェクト */
	private MypageUserManage mypageUserManage;

	/** 更新通知メールテンプレート */
	private ReplacingMail sendPasswordResult;

    /** 共通パラメータオブジェクト */
    private PanaCommonParameters commonParameters;

    /** 会員情報用 Model オブジェクト */
	private PanaMypageUserManageImpl panaMypageUserManager;

	/**
	 * @param mypageUserManager セットする mypageUserManager
	 */
	public void setPanaMypageUserManager(PanaMypageUserManageImpl panaMypageUserManager) {
		this.panaMypageUserManager = panaMypageUserManager;
	}

    /**
     * 共通パラメータオブジェクトを設定する。<br/>
     * <br/>
     * @param commonParameters 共通パラメータオブジェクト
     */
    public void setCommonParameters(PanaCommonParameters commonParameters) {
        this.commonParameters = commonParameters;
    }

	/**
	 * @param sendUpdateTemplate セットする sendUpdateTemplate
	 */
	public void setSendPasswordResult(
			ReplacingMail sendPasswordResult) {
		this.sendPasswordResult = sendPasswordResult;
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
	 * パスワードリマインダー画面表示処理<br>
	 * <br>
	 *
	 * @param request
	 *            HTTP リクエスト
	 * @param response
	 *            HTTP レスポンス
	 */
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// 取得したデータをレンダリング層へ渡す
		Map<String, Object> model = new HashMap<String, Object>();

		PasswordFormFactory factory = PasswordFormFactory
				.getInstance(request);

		// ページ処理用のフォームオブジェクトを作成
		PasswordChangeForm pwdChangeForm = factory.createPasswordChangeForm(request);

		// バリデーションを実行
		List<ValidationFailure> errors = new ArrayList<ValidationFailure>();
		if (!pwdChangeForm.validate(errors)) {

			// バリデーションエラーあり
			model.put("errors", errors);

			model.put("remindId", pwdChangeForm.getRemindId());

			model.put("email", pwdChangeForm.getEmail());

			model.put("pwdChangeForm", pwdChangeForm);

			return new ModelAndView("validFail", model);
		}

		// 匿名認証時に払い出されているユーザーID
		String entryUserId = FrontLoginUserUtils.getInstance(request).getAnonLoginUserInfo(request, response).getUserId().toString();

		// パスワードの変更処理を行う。
		try{
			mypageUserManage.changePassword(pwdChangeForm, entryUserId);
		}catch(NotFoundException e){
			return new ModelAndView("404", model);
		}

		String email = this.panaMypageUserManager.getResultMail(pwdChangeForm.getRemindId());

		pwdChangeForm.setEmail(email);

		String HttpPath = this.commonParameters.getSiteURL();

		String mypageURL = HttpPath + RESULT_URL;

		// メールテンプレートで使用するパラメータを設定する。
		this.sendPasswordResult.setParameter("inputForm", pwdChangeForm);
		this.sendPasswordResult.setParameter("mypageURL", mypageURL);
		this.sendPasswordResult.setParameter("commonParameters", CommonParameters.getInstance(request));

		// メール送信
		this.sendPasswordResult.send();

		model.put("pwdChangeForm", pwdChangeForm);

		return new ModelAndView("success", model);
	}

}
