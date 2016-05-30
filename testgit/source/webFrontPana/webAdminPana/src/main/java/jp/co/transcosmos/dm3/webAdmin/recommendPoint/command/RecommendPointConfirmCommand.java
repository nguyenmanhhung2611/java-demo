package jp.co.transcosmos.dm3.webAdmin.recommendPoint.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingFormFactory;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingSearchForm;
import jp.co.transcosmos.dm3.corePana.model.housing.form.RecommendPointForm;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

import org.springframework.web.servlet.ModelAndView;

/**
 * <pre>
 * おすすめポイント編集入力確認画面
 * リクエストパラメータで渡された管理ユーザー情報のバリデーションを行い、確認画面を表示する。
 * もしバリデーションエラーが発生した場合は入力画面として表示する。
 *
 * 【復帰する View 名】
 *    ・"success" : 正常終了
 *    ・"input" : バリデーションエラーによる再入力
 *
 * 担当者       修正日      修正内容
 * ------------ ----------- -----------------------------------------------------
 * 郭中レイ     2015.4.10  新規作成
 *
 * 注意事項
 *
 * </pre>
*/
public class RecommendPointConfirmCommand implements Command {

	/**
	 * おすすめポイント編集入力確認画面表示処理<br>
	 * <br>
	 * @param request HTTP リクエスト
	 * @param response HTTP レスポンス
	*/
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

 		// リクエストパラメータを格納した model オブジェクトを生成する。
 		// このオブジェクトは View 層への値引渡しに使用される。
         Map<String, Object> model = createModel(request);
         RecommendPointForm form = (RecommendPointForm)model.get("inputForm");

        // view 名の初期値を設定
        String viewName = "success";

		// バリデーションを実行
		List<ValidationFailure> errors = new ArrayList<ValidationFailure>();
		if (!form.validate(errors)) {
			// バリデーションエラー時は、エラー情報を model に設定し、入力画面を表示する。
			model.put("errors", errors);
			viewName = "input";
		}

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
        RecommendPointForm form = factory.createRecommendPointForm(request);
        PanaHousingSearchForm searchForm = factory.createPanaHousingSearchForm(request);

		model.put("inputForm", form);
		model.put("searchForm", searchForm);

		return model;

	}


}
