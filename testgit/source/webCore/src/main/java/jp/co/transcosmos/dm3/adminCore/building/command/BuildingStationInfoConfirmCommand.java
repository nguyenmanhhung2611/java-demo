package jp.co.transcosmos.dm3.adminCore.building.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.model.building.dao.RouteMstListDAO;
import jp.co.transcosmos.dm3.core.model.building.dao.StationMstListDAO;
import jp.co.transcosmos.dm3.core.model.building.form.BuildingFormFactory;
import jp.co.transcosmos.dm3.core.model.building.form.BuildingStationInfoForm;
import jp.co.transcosmos.dm3.core.vo.RouteMst;
import jp.co.transcosmos.dm3.core.vo.StationMst;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

import org.springframework.web.servlet.ModelAndView;

/**
 * <pre>
 * 最寄り駅情報入力確認画面
 * リクエストパラメータで渡された管理ユーザー情報のバリデーションを行い、確認画面を表示する。
 * もしバリデーションエラーが発生した場合は入力画面として表示する。
 * 
 * 【復帰する View 名】
 *    ・"success" : 正常終了
 *    ・"input" : バリデーションエラーによる再入力
 *
 * 担当者       修正日      修正内容
 * ------------ ----------- -----------------------------------------------------
 * I.Shu		2015.03.10	新規作成
 *
 * 注意事項
 * 
 * </pre>
 */
public class BuildingStationInfoConfirmCommand implements Command {

	/** 処理モード (insert = 新規登録処理、 update=更新処理)*/
	protected String mode;
	
	/** 路線マスタ取得用 DAO */
	protected RouteMstListDAO routeMstListDAO;
	
	/** 駅名マスタ取得用 DAO */
	protected StationMstListDAO stationMstListDAO;
	
	/**
	 * 処理モードを設定する<br/>
	 * <br/>
	 * @param mode "insert" = 新規登録処理、"update" = 更新処理
	 */
	public void setMode(String mode) {
		this.mode = mode;
	}

	/**
	 * 路線マスタ用DAOを設定する。<br/>
	 * <br/>
	 * 
	 * @return 路線マスタ用 DAO
	 */
	public void setRouteMstListDAO(RouteMstListDAO routeMstListDAO) {
		this.routeMstListDAO = routeMstListDAO;
	}

	/**
	 * 駅名マスタ用DAOを設定する。<br/>
	 * <br/>
	 * 
	 * @return 駅名マスタ用 DAO
	 */
	public void setStationMstListDAO(StationMstListDAO stationMstListDAO) {
		this.stationMstListDAO = stationMstListDAO;
	}
	
	/**
	 * 建物情報入力確認画面表示処理<br>
	 * <br>
	 * @param request HTTP リクエスト
	 * @param response HTTP レスポンス
	*/
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

 		// リクエストパラメータを格納した model オブジェクトを生成する。
 		// このオブジェクトは View 層への値引渡しに使用される。
         Map<String, Object> model = createModel(request);
         BuildingStationInfoForm inputForm = (BuildingStationInfoForm) model.get("inputForm"); 
         
         
        // 更新モードの場合、更新対象の主キー値がパラメータで渡されていない場合、例外をスローする。
        if ("update".equals(this.mode)){
        	if (StringValidateUtil.isEmpty(inputForm.getSysBuildingCd())) throw new RuntimeException ("pk value is null.");
        }

        // view 名の初期値を設定
        String viewName = "success"; 
        
		// バリデーションを実行
        List<ValidationFailure> errors = new ArrayList<ValidationFailure>();
        if (!inputForm.validate(errors)){
    		// 路線マスタを取得する
    		Object[] params = new Object[] {inputForm.getPrefCd()};
    		List<RouteMst> routeMstList = this.routeMstListDAO.listRouteMst(params);
    		model.put("routeMstList", routeMstList);
			// 駅マスタを取得する
			String[] routeCd = inputForm.getDefaultRouteCd();
			if (routeCd != null) {
				List<List<StationMst>> stationMstList = new ArrayList<List<StationMst>>();
				for (int i = 0; i < routeCd.length; i++) {
					if (StringValidateUtil.isEmpty(routeCd[i])) {
						stationMstList.add(null);
					} else {
						Object[] paramsStation = new Object[] {inputForm.getPrefCd(), routeCd[i]};
						stationMstList.add(getStationMstList(paramsStation));
					}
				}
				model.put("stationMstList", stationMstList);
			}
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
		BuildingFormFactory factory = BuildingFormFactory.getInstance(request);

		model.put("searchForm", factory.createBuildingSearchForm(request));
		model.put("inputForm", factory.createBuildingStationInfoForm(request));

		return model;

	}

	/**
	 * 線路CDで駅リストを取得する。<br/>
	 * <br/>
	 * @param routeCd 線路CD
	 * @return 駅リストを復帰する。
	 */
	protected List<StationMst> getStationMstList(Object[] params) {
		List<StationMst> stationMstList = this.stationMstListDAO.listStationMst(params);
		return stationMstList;
	}
}
