package jp.co.transcosmos.dm3.webAdmin.equipDtl.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.corePana.model.equipDtl.form.EquipDtlInfoForm;
import jp.co.transcosmos.dm3.corePana.model.equipDtl.form.EquipDtlInfoFormFactory;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingFormFactory;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingSearchForm;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

import org.springframework.web.servlet.ModelAndView;

/**
 * <pre>
 * 管理者用設備情報編集確認画面
 * リクエストパラメータで渡されたリフォーム情報のバリデーションを行い、完了画面を表示する。
 * もしバリデーションエラーが発生した場合は入力画面として表示する。
 *
 * 【復帰する View 名】
 *    ・"success" : 正常終了
 *    ・"input" : バリデーションエラーによる再入力
 *
 * 担当者       修正日     修正内容
 * ------------ ----------- -----------------------------------------------------
 * 郭中レイ     2015.4.14  新規作成
 *
 * 注意事項
 *
 * </pre>
 */
public class EquipDtlConfirmCommand implements Command {

	/**
	 * 管理者用設備情報編集確認画面表示処理<br>
	 * <br>
	 *
	 * @param request
	 *            HTTP リクエスト
	 * @param response
	 *            HTTP レスポンス
	 */
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// リクエストパラメータを格納した model オブジェクトを生成する。
		// このオブジェクトは View 層への値引渡しに使用される。
		Map<String, Object> model = createModel(request);
		EquipDtlInfoForm inputForm = (EquipDtlInfoForm) model.get("inputForm");

		// view 名の初期値を設定
		String viewName = "success";

		// バリデーションを実行
		List<ValidationFailure> errors = new ArrayList<ValidationFailure>();
		if (!inputForm.validate(errors)) {
			// バリデーションエラー時は、エラー情報を model に設定し、入力画面を表示する。
			model.put("errors", errors);
			viewName = "input";
		}

		model.put("inputForm", inputForm);

		return new ModelAndView(viewName, model);
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
		EquipDtlInfoFormFactory factory = EquipDtlInfoFormFactory.getInstance(request);
		EquipDtlInfoForm requestForm = factory.createEquipDtlInfoForm(request);

		model.put("inputForm", requestForm);

		PanaHousingFormFactory housingFactory = PanaHousingFormFactory.getInstance(request);
		PanaHousingSearchForm searchForm = housingFactory.createPanaHousingSearchForm(request);
		model.put("searchForm", searchForm);

		return model;
	}
}
