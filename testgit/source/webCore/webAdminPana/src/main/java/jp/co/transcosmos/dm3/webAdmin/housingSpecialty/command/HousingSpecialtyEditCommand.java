package jp.co.transcosmos.dm3.webAdmin.housingSpecialty.command;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.model.exception.NotFoundException;
import jp.co.transcosmos.dm3.core.model.housing.Housing;
import jp.co.transcosmos.dm3.corePana.model.housing.PanaHousingPartThumbnailProxy;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingFormFactory;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingSearchForm;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingSpecialtyForm;
import jp.co.transcosmos.dm3.dao.JoinResult;
import jp.co.transcosmos.dm3.form.FormPopulator;

import org.springframework.web.servlet.ModelAndView;

/**
 * <pre>
 * 物件特徴編集画面
 *
 * 【復帰する View 名】
 *    ・"success" : 正常終了
 *
 * 担当者       修正日     修正内容
 * ------------ ----------- -----------------------------------------------------
 * liu.yandong  2015.04.9  新規作成
 *
 * 注意事項
 *
 * </pre>
 */
public class HousingSpecialtyEditCommand implements Command {

	/** 物件情報用 Model オブジェクト */
    protected PanaHousingPartThumbnailProxy panaHousingManager;

    /**
	 * 物件情報用 Model オブジェクトを設定する。<br/>
	 * <br/>
	 * @param PanaHousingPartThumbnailProxy 物件情報用 Model オブジェクト
	 */
    public void setPanaHousingManager(PanaHousingPartThumbnailProxy panaHousingManager) {
        this.panaHousingManager = panaHousingManager;
    }

	/**
	 * 物件特徴編集画面表示処理<br>
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
		// リクエストパラメータを取得して Form オブジェクトを作成する。
		PanaHousingFormFactory factory = PanaHousingFormFactory.getInstance(request);
		PanaHousingSpecialtyForm inputForm = factory.createPanaHousingSpecialtyForm();

		inputForm = (PanaHousingSpecialtyForm) model.get("inputForm");

		// 入力フォームの受け取りは、command パラメータの値によって異なる。
		// command パラメータが渡されない場合、入力画面の初期表示とみなし、空のフォームを復帰する。
		// command パラメータで "back" が渡された場合、入力確認画面からの復帰を意味する。
		// その場合はリクエストパラメータを受け取り、入力画面へ初期表示する。
		// もし、"back" 以外の値が渡された場合、command パラメータが渡されない場合と同等に扱う。
		String command = inputForm.getCommand();

		if (!"back".equals(command)) {
			// 処理実行を行う。
			execute(inputForm);
		}
		model.put("inputForm", inputForm);

		return new ModelAndView("success", model);
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
		PanaHousingFormFactory factory = PanaHousingFormFactory.getInstance(request);
		PanaHousingSpecialtyForm requestForm = factory.createPanaHousingSpecialtyForm();
		FormPopulator.populateFormBeanFromRequest(request, requestForm);
		model.put("inputForm", requestForm);

		// 検索条件、および、画面コントロールパラメータを取得する。
		PanaHousingSearchForm searchForm = factory.createPanaHousingSearchForm(request);
		model.put("searchForm", searchForm);

		return model;
	}

	/**
	 * 処理実行を行う。<br/>
	 * <br/>
	 *
	 * @param inputForm
	 *            入力値が格納された Form オブジェクト
	 *
	 * @exception Exception
	 *                実装クラスによりスローされる任意の例外
	 * @exception NotFoundException
	 *                更新対象が存在しない場合
	 */
	private void execute(PanaHousingSpecialtyForm inputForm) throws Exception, NotFoundException {

		// 物件基本情報を取得する。
		Housing housing = this.panaHousingManager.searchHousingPk(inputForm.getSysHousingCd(), true);

		// データの存在しない場合。
		if (housing == null) {
			throw new NotFoundException();
		}

		// 設備マスタを取得する。
		List<JoinResult> equipList = this.panaHousingManager.searchEquipMst(inputForm.getHousingKindCd());

		// Formをセットする。
		inputForm.setDefaultData(housing, equipList);
	}
}
