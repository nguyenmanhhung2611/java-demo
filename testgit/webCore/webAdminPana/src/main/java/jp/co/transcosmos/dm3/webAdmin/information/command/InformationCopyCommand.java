package jp.co.transcosmos.dm3.webAdmin.information.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.adminCore.information.command.InformationInputCommand;
import jp.co.transcosmos.dm3.core.model.information.form.InformationForm;
import jp.co.transcosmos.dm3.core.vo.Information;

import org.springframework.web.servlet.ModelAndView;

/**
 * <pre>
 * お知らせコピー画面
 *
 * 既存のお知らせから、情報を取得し、新しいお知らせを作成すること。
 *
 *
 * 担当者       修正日      修正内容
 * ------------ ----------- -----------------------------------------------------
 * zh.xiaoting  2015.04.24	新規作成
 *
 * 注意事項
 *
 * </pre>
 */
public class InformationCopyCommand extends InformationInputCommand {

	/**
	 * リフォーム情報入力画面処理<br>
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

		ModelAndView modelAndView = super.handleRequest(request, response);
		// リクエストパラメータを取得して Form オブジェクトを作成する。
		InformationForm inputForm = (InformationForm) modelAndView.getModel()
				.get("inputForm");
		// お知らせ番号を空白に設定
		inputForm.setInformationNo("");

		// 登録日時を空白に設定
		Information information = (Information) modelAndView.getModel().get("informationInsDate");
		information.setInsDate(null);

		return modelAndView;
	}

}
