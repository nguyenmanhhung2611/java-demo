package jp.co.transcosmos.dm3.webFront.inquiryDivision.command;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.login.command.v3.LoginCommand;

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
public class InquiryDivisionInitCommand extends LoginCommand {

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

		Map<String, Object> model = new HashMap<String, Object>();

		model.put("sysHousingCd", request.getParameter("sysHousingCd"));


		return new ModelAndView("success", model);
	}


}
