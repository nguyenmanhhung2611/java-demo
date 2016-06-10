package jp.co.transcosmos.dm3.webAdmin.recommendPoint.command;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.model.exception.NotFoundException;
import jp.co.transcosmos.dm3.core.model.housing.Housing;
import jp.co.transcosmos.dm3.corePana.model.housing.PanaHousingPartThumbnailProxy;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingFormFactory;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingSearchForm;
import jp.co.transcosmos.dm3.corePana.model.housing.form.RecommendPointForm;

import org.springframework.web.servlet.ModelAndView;

/**
 * <pre>
 * おすすめポイント編集画面の初期表示
 * システム物件CDよりおすすめポイント情報データを抽出し、画面に表示される。
 *
 *
 * 【復帰する View 名】
 *    ・"success" : 検索処理正常終了
 *    ・"validFail" : バリデーションエラー
 *
 * 担当者       修正日      修正内容
 * ------------ ----------- -----------------------------------------------------
 * 郭中レイ     2015.4.10  新規作成
 *
 * 注意事項
 *
 * </pre>
 */
public class RecommendPointInputCommand implements Command {

	/** 物件情報を行う Model オブジェクト */
	private PanaHousingPartThumbnailProxy panaHousingManager;

	/**
	 * 物件情報リクエスト処理<br>
	 * 物件情報のリクエストがあったときに呼び出される。 <br>
	 *
	 * @param request
	 *            クライアントからのHttpリクエスト。
	 * @param response
	 *            クライアントに返すHttpレスポンス。
	 */
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// リクエストパラメータを格納した model オブジェクトを生成する。
		// このオブジェクトは View 層への値引渡しに使用される。
        Map<String, Object> model = createModel(request);

		return new ModelAndView("success", model);
	}

	/**
	 * 物件情報を行う Model　オブジェクトを設定する。<br/>
	 * <br/>
	 * @param panaHousingManager 物件情報の model オブジェクト
	 */
	public void setPanaHousingManager(PanaHousingPartThumbnailProxy panaHousingManager) {
		this.panaHousingManager = panaHousingManager;
	}

	/**
	 * model オブジェクトを作成し、リクエストパラメータを格納した form オブジェクトを格納する。<br/>
	 * <br/>
	 * @param request HTTP リクエストパラメータ
	 * @return パラメータを設定した Form を格納した model オブジェクトを復帰する。
	 * @exception Exception 実装クラスによりスローされる任意の例外
	 */
	protected Map<String, Object> createModel(HttpServletRequest request) throws Exception {

        Map<String, Object> model = new HashMap<String, Object>();

		// リクエストパラメータを取得して Form オブジェクトを作成する。
        PanaHousingFormFactory factory = PanaHousingFormFactory.getInstance(request);
        RecommendPointForm inputForm = factory.createRecommendPointForm(request);
        PanaHousingSearchForm searchForm = factory.createPanaHousingSearchForm(request);


		// 入力フォームの受け取りは、command パラメータの値によって異なる。
		// command パラメータが渡されない場合、入力画面の初期表示とみなし、空のフォームを復帰する。
		// command パラメータで "back" が渡された場合、入力確認画面からの復帰を意味する。
		// その場合はリクエストパラメータを受け取り、入力画面へ初期表示する。
		// もし、"back" 以外の値が渡された場合、command パラメータが渡されない場合と同等に扱う。
		String command = inputForm.getCommand();

		if ("back".equals(command)){

			// 戻るボタンの場合は、リクエストパラメータの値を設定した Form を復帰する。
			model.put("inputForm", inputForm);
			model.put("searchForm", searchForm);
		} else {

		       // システム物件CD
	        String sysHousingCd = request.getParameter("sysHousingCd");

	        // システム物件CDを設定する
	        inputForm.setSysHousingCd(sysHousingCd);

	        // システム物件CD （主キー値）に該当する物件情報を取得する
	        Housing housing = this.panaHousingManager.searchHousingPk(sysHousingCd, true);

			// データの存在しない場合。
			if (housing == null) {
				throw new NotFoundException();
			}

	        // 取得した値を入力用 Form へ格納する。 （入力値の初期値用）
			inputForm.setDefaultData(housing, inputForm);

	        model.put("inputForm", inputForm);
	        model.put("searchForm", searchForm);
		}

		return model;

	}

}
