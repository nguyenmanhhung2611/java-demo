package jp.co.transcosmos.dm3.webAdmin.housingSpecialty.command;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingFormFactory;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingSearchForm;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingSpecialtyForm;
import jp.co.transcosmos.dm3.form.FormPopulator;

import org.springframework.web.servlet.ModelAndView;

/**
 * <pre>
 * 物件特徴編集入力確認画面
 *
 * 【復帰する View 名】
 *    ・"success" : 正常終了
 *
 * 担当者       修正日      修正内容
 * ------------ ----------- -----------------------------------------------------
 * liu.yandong	2015.04.9	新規作成
 *
 * 注意事項
 *
 * </pre>
*/
public class HousingSpecialtyConfirmCommand implements Command  {

	/**
	 * 物件特徴編集確認画面表示処理<br>
	 * <br>
	 * @param request HTTP リクエスト
	 * @param response HTTP レスポンス
	*/
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

 		// リクエストパラメータを格納した model オブジェクトを生成する。
 		// このオブジェクトは View 層への値引渡しに使用される。
         Map<String, Object> model = createModel(request);

        // view 名の初期値を設定
        String viewName = "success";

        return new ModelAndView(viewName, model);
	}

	/**
	 * リクエストパラメータから Form オブジェクトを作成する。<br/>
	 * 生成した Form オブジェクトは Map に格納して復帰する。<br/>
	 * key = フォームクラス名（パッケージなし）、Value = フォームオブジェクト
	 * <br/>
	 * @param request HTTP リクエストパラメータ
	 * @return パラメータが設定されたフォームオブジェクトを格納した Map オブジェクト
	 */
	protected Map<String, Object> createModel(HttpServletRequest request) {

		Map<String, Object> model = new HashMap<>();

		// リクエストパラメータを取得して Form オブジェクトを作成する。
		PanaHousingFormFactory factory = PanaHousingFormFactory.getInstance(request);
		PanaHousingSpecialtyForm inputForm = factory.createPanaHousingSpecialtyForm();
        FormPopulator.populateFormBeanFromRequest(request, inputForm);
		model.put("inputForm", inputForm);

		// 検索条件、および、画面コントロールパラメータを取得する。
		PanaHousingSearchForm searchForm = factory.createPanaHousingSearchForm(request);
		model.put("searchForm", searchForm);

		return model;

	}
}
