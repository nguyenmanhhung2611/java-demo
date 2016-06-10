package jp.co.transcosmos.dm3.adminCore.building.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.model.BuildingManage;
import jp.co.transcosmos.dm3.core.model.building.form.BuildingFormFactory;
import jp.co.transcosmos.dm3.core.model.building.form.BuildingSearchForm;
import jp.co.transcosmos.dm3.core.vo.PrefMst;
import jp.co.transcosmos.dm3.dao.DAO;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

import org.springframework.web.servlet.ModelAndView;

/**
 * <pre>
 * 建物情報の検索・一覧
 * 入力された検索条件を元にお知らせ情報を検索し、一覧表示する。
 * 検索条件の入力に問題がある場合、検索処理は行わない。
 * 
 * 【復帰する View 名】
 *    ・"success" : 検索処理正常終了
 *    ・"validFail" : バリデーションエラー 
 * 
 * 担当者       修正日      修正内容
 * ------------ ----------- -----------------------------------------------------
 * I.Shu		2015.02.27	新規作成
 * 
 * 注意事項
 * 
 * </pre>
 */
public class BuildingListCommand implements Command {

	/** 建物情報メンテナンスを行う Model オブジェクト */
	protected BuildingManage buildingManager;
	
	/** １ページの表示件数 */
	protected int rowsPerPage = 50;

	
	/** 都道府県マスタ取得用 DAO */
	protected DAO<PrefMst> prefMstDAO;
	
	/**
	 * 都道府県マスタ用DAOを設定する。<br/>
	 * <br/>
	 * 
	 * @return 市区町村マスタ用 DAO
	 */
	public void setPrefMstDAO(DAO<PrefMst> prefMstDAO) {
		this.prefMstDAO = prefMstDAO;
	}
	
	/**
	 * 1ページあたり表示数を設定する。<br>
	 * 
	 * @param rowsPerPage
	 *            1ページあたり表示数
	 */
	public void setRowsPerPage(int rowsPerPage) {
		this.rowsPerPage = rowsPerPage;
	}

	/**
	 * 建物情報リクエスト処理<br>
	 * 建物情報のリクエストがあったときに呼び出される。 <br>
	 * 
	 * @param request
	 *            クライアントからのHttpリクエスト。
	 * @param response
	 *            クライアントに返すHttpレスポンス。
	 */
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		// 検索パラメータ受信
        Map<String, Object> model = createModel(request);
        BuildingSearchForm form = (BuildingSearchForm) model.get("searchForm");

		// 都道府県マスタを取得する
		List<PrefMst> prefMstList = this.prefMstDAO.selectByFilter(null);
		model.put("prefMstList", prefMstList);

        // バリデーションを実行
        List<ValidationFailure> errors = new ArrayList<ValidationFailure>();

        if (!form.validate(errors)) {
        	// バリデーションエラーあり
			model.put("errors", errors);
			return new ModelAndView("validFail", model);
        }

        // 検索実行
        // searchBuildingInfo() は、パラメータで渡された form の内容でお知らせ情報を検索し、
        // 検索した結果を form に格納する。
        // このメソッドの戻り値は該当件数なので、その値を view 層へ渡すパラメータとして設定している。
		model.put("hitcont", this.buildingManager.searchBuilding(form));


		// バリデーションを通過し、検索処理が行われた場合、Form の command パラメータに list を設定
		// する。　このパラメータ値は、詳細画面や変更画面で searchCommand パラメータとして引き継がれ、
		// 再び検索画面へ復帰する際、command パラメータとして渡される。
		model.put("command", "list");

		return new ModelAndView("success", model);
	}
	
	/**
	 * リクエストパラメータから Form オブジェクトを作成する。<br/>
	 * <br/>
	 * @param request HTTP リクエストパラメータ
	 * @return パラメータが設定されたフォームオブジェクト
	 */
	protected Map<String, Object> createModel(HttpServletRequest request) {

		Map<String, Object> model = new HashMap<String, Object>();
		
		// リクエストパラメータを取得して Form オブジェクトを作成する。
		BuildingFormFactory factory = BuildingFormFactory.getInstance(request);
		BuildingSearchForm searchFrom = factory.createBuildingSearchForm(request);
		
        // ページ内の表示件数を From に設定する。
        // この値は、フレームワークのページ処理が使用する。
		searchFrom.setRowsPerPage(this.rowsPerPage);
        model.put("searchForm", searchFrom);
		
		return model;

	}

	/**
	 * 建物情報メンテナンスを行う Model　オブジェクトを設定する。<br/>
	 * <br/>
	 * @param buildingManager 建物情報メンテナンスの model オブジェクト
	 */
	public void setBuildingManager(BuildingManage buildingManager) {
		this.buildingManager = buildingManager;
	}
}
