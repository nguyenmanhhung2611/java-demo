package jp.co.transcosmos.dm3.webAdmin.information.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.adminCore.information.command.InformationCompCommand;
import jp.co.transcosmos.dm3.adminCore.mypage.command.MypageCompCommand;
import jp.co.transcosmos.dm3.core.constant.CommonParameters;
import jp.co.transcosmos.dm3.core.model.adminUser.AdminUserInterface;
import jp.co.transcosmos.dm3.core.model.information.form.InformationForm;
import jp.co.transcosmos.dm3.core.util.AdminLoginUserUtils;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonConstant;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;

/**
 * お知らせ情報の追加、変更、削除処理.
 * <p>
 * 【新規登録の場合】<br/>
 * <ul>
 * <li>リクエストパラメータを受け取り、バリデーションを実行する。</li>
 * <li>バリデーションが正常終了した場合、お知らせ情報を新規登録する。</li>
 * <li>また、公開先が特定個人の場合、お知らせ公開先情報も新規登録する。</li>
 * </ul>
 * <br/>
 * 【更新登録の場合】<br/>
 * <ul>
 * <li>リクエストパラメータを受け取り、バリデーションを実行する。</li>
 * <li>バリデーションが正常終了した場合、お知らせ情報を更新する。</li>
 * <li>お知らせ公開先情報は一度削除し、変更後の公開先が特定個人であれば、お知らせ公開先情報も新規登録する。</li>
 * <li>もし、更新対象データが存在しない場合、更新処理が継続できないので該当無し画面を表示する。</li>
 * </ul>
 * <br/>
 * 【削除登録の場合】<br/>
 * <ul>
 * <li>リクエストパラメータを受け取り、バリデーションを実行する。（主キー値のみ）</li>
 * <li>バリデーションが正常終了した場合、お知らせ情報、お知らせ公開先情報を削除する。</li>
 * </ul>
 * <br/>
 * 【復帰する View 名】<br/>
 * <ul>
 * <li>success</li>:正常終了（リダイレクトページ）
 * <li>input</li>:バリデーションエラーによる再入力
 * <li>notFound</li>:該当データが存在しない場合（更新処理の場合）
 * <li>comp</li>:完了画面表示
 * </ul>
 * <p>
 * <pre>
 * 担当者		修正日		修正内容
 * ------------ ----------- -----------------------------------------------------
 * I.Shu		2015.02.10	新規作成
 * H.Mizuno		2015.02.27	インターフェースの改定にともない全体構成を変更
 * </pre>
 * <p>
 * 注意事項<br/>
 *
 */
public class PanaInformationCompCommand extends InformationCompCommand  {

	private static final Log log = LogFactory.getLog(MypageCompCommand.class);

	//管理ユーザメール
	private String loginUserEmail;

	/**
	 * お知らせ情報の追加、変更、削除処理<br>
	 * <br>
	 * @param request クライアントからのHttpリクエスト。
	 * @param response クライアントに返すHttpレスポンス。
	 */
	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
        // ログインユーザーの情報を取得する。　（管理ユーザメール取得用）
        AdminUserInterface loginUser = AdminLoginUserUtils.getInstance(request).getLoginUserInfo(request, response);
        this.loginUserEmail = loginUser.getEmail();
		ModelAndView modelAndView = super.handleRequest(request, response);
		return modelAndView;
	}

	/**
	 * お知らせの通知メールを送信する。<br/>
	 * <br/>
	 * @param request HTTP リクエスト
	 * @param inputForm 入力値
	 */
	@Override
	protected void sendInformationMail(HttpServletRequest request,
			InformationForm inputForm) {

		// 削除する場合 且つ、送信しない
		if (!"delete".equals(inputForm.getCommand())
				&& PanaCommonConstant.SEND_FLG_1.equals(inputForm.getMailFlg())
				&& PanaCommonConstant.DSP_FLG_PRIVATE.equals(inputForm.getDspFlg())) {
			// メールテンプレートが設定されていない場合はメールの通知を行わない。
			if (this.sendInformationTemplate == null) {
				log.warn("sendInformationTemplate is null.");
				return;
			}

			// メールテンプレートで使用するパラメータを設定する。
			this.sendInformationTemplate.setParameter("loginUserEmail", this.loginUserEmail);
			this.sendInformationTemplate.setParameter("inputForm", inputForm);
			this.sendInformationTemplate.setParameter("commonParameters", CommonParameters.getInstance(request));

			// メール送信
			this.sendInformationTemplate.send();
		}
	}
}
