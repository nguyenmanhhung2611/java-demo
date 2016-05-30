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
import jp.co.transcosmos.dm3.core.model.mypage.form.MypageUserFormFactory;
import jp.co.transcosmos.dm3.core.model.mypage.form.RemindForm;
import jp.co.transcosmos.dm3.core.util.FrontLoginUserUtils;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonParameters;
import jp.co.transcosmos.dm3.corePana.model.passwordRemind.form.PasswordFormFactory;
import jp.co.transcosmos.dm3.corePana.model.passwordRemind.form.PasswordRemindForm;
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
public class PwRemindCommand implements Command {

	public static final String NEWPW_URL = "/account/pw/new/input/";

	/** マイページユーザーの情報を管理する Model オブジェクト */
	private MypageUserManage mypageUserManage;

	/** 更新通知メールテンプレート */
	private ReplacingMail sendPasswordRemind;

    /** 共通パラメータオブジェクト */
    private PanaCommonParameters commonParameters;

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
	public void setSendPasswordRemind(
			ReplacingMail sendPasswordRemind) {
		this.sendPasswordRemind = sendPasswordRemind;
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
		PasswordRemindForm pwRemindForm = factory
				.createPasswordRemindForm(request);

		MypageUserFormFactory mpfactory = MypageUserFormFactory
				.getInstance(request);

		RemindForm remindForm = mpfactory.createRemindForm(request);

		remindForm.setEmail(pwRemindForm.getMailAddress());

		// バリデーションを実行
		List<ValidationFailure> errors = new ArrayList<ValidationFailure>();
		if (!pwRemindForm.validate(errors)) {

			// バリデーションエラーあり
			model.put("errors", errors);

			model.put("pwRemindForm", pwRemindForm);

			return new ModelAndView("validFail", model);
		}

		pwRemindForm.setEmail(pwRemindForm.getMailAddress());

		// 該当メールのユーザ存在チェック
		boolean emailExitsFlg = this.mypageUserManage.isFreeLoginId(pwRemindForm,"");

		if(emailExitsFlg){

			ValidationFailure vf = new ValidationFailure(
	                    "emailExitsError", "", "", null);
            errors.add(vf);
	        // エラー処理
			// エラーオブジェクトと、フォームオブジェクトをModelAndView に渡している
			model.put("errors", errors);
			// リストを取得する
			model.put("pwRemindForm", pwRemindForm);
			return new ModelAndView("validFail", model);
		}

		// 匿名認証時に払い出されているユーザーID
		String entryUserId = FrontLoginUserUtils.getInstance(request).getAnonLoginUserInfo(request, response).getUserId().toString();

		// パスワード変更の登録処理を行う。
		String returnId = mypageUserManage.addPasswordChangeRequest(remindForm, entryUserId);

		String HttpPath = this.commonParameters.getSiteURL();

		String url = HttpPath + NEWPW_URL + returnId;

		// メールテンプレートで使用するパラメータを設定する。
		this.sendPasswordRemind.setParameter("inputForm", pwRemindForm);
		this.sendPasswordRemind.setParameter("mailUrl", url);
		this.sendPasswordRemind.setParameter("commonParameters", CommonParameters.getInstance(request));

		// メール送信
		this.sendPasswordRemind.send();

		model.put("pwRemindForm", pwRemindForm);

		return new ModelAndView("success", model);
	}

}
