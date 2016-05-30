package jp.co.transcosmos.dm3.webAdmin.housingStatus.command;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.util.AdminLoginUserUtils;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingFormFactory;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingSearchForm;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingStatusForm;
import jp.co.transcosmos.dm3.login.UserRoleSet;

import org.springframework.web.servlet.ModelAndView;

/**
 * <pre>
 * 物件閲覧画面
 *
 * 【新規登録の場合】
 *  リクエストパラメータを受け取り、バリデーションを実行する。
 *  バリデーションが正常終了した場合、物件閲覧情報を新規登録する。
 *  公開区分が特定個人の場合、物件閲覧情報も新規登録する。
 *
 *
 * 担当者       修正日      修正内容
 * ------------ ----------- -----------------------------------------------------
 * Liu.Yandong		2015.03.11	新規作成
 *
 * 注意事項
 *
 * </pre>
 */
public class HousingStatusAddCommand implements Command {
	/**
	 * 新規登録画面表示処理<br>
	 * <br>
	 *
	 * @param request
	 *            HTTP リクエスト
	 * @param response
	 *            HTTP レスポンス
	 */
	// パターンチェック
	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// リクエストパラメータを格納した model オブジェクトを生成する。
		// このオブジェクトは View 層への値引渡しに使用される。
		Map<String, Object> model = createModel(request);
		PanaHousingStatusForm form = (PanaHousingStatusForm) model
				.get("inputForm");

		// ユーザー情報取得
		UserRoleSet userRole = AdminLoginUserUtils.getInstance(request)
				.getLoginUserRole(request, response);

		// 画面格納
		form.setDefaultData(userRole);

		return new ModelAndView("success", model);
	}

	/**
	 * model オブジェクトを作成する。<br/>
	 * <br/>
	 *
	 * @param request
	 *            HTTP リクエスト
	 * @return パラメータを設定した Form を格納した model オブジェクトを復帰する。
	 */
	protected Map<String, Object> createModel(HttpServletRequest request) {

		Map<String, Object> model = new HashMap<String, Object>();

		// リクエストパラメータを取得して Form オブジェクトを作成する。
		PanaHousingFormFactory factory = PanaHousingFormFactory
				.getInstance(request);
		PanaHousingStatusForm form = factory
				.createPanaHousingStatusForm(request);
		model.put("inputForm", form);

		// 検索条件、および、画面コントロールパラメータを取得する。
		PanaHousingSearchForm searchForm = factory.createPanaHousingSearchForm(request);
		model.put("searchForm", searchForm);

		return model;
	}
}
