package jp.co.transcosmos.dm3.adminCore.building.command;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.model.BuildingManage;
import jp.co.transcosmos.dm3.core.model.building.Building;
import jp.co.transcosmos.dm3.core.model.building.form.BuildingFormFactory;
import jp.co.transcosmos.dm3.core.model.building.form.BuildingSearchForm;

import org.springframework.web.servlet.ModelAndView;

/**
 * 建物閲覧画面.
 * <p>
 * リクエストパラメータ（sysBuildingCd）で渡された値に該当する建物情報を
 * 取得し画面表示する。<br/>
 * <br/>
 * 【復帰する View 名】<br/>
 * <ul>
 * <li>success<li>:検索処理正常終了
 * <li>notFound<li>:該当データが存在しない場合
 * </ul>
 * <p>
 * <pre>
 * 担当者		修正日		修正内容
 * ------------ ----------- -----------------------------------------------------
 * I.Shu		2015.03.06	に新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 * 
 */
public class BuildingDetailCommand implements Command {
	/** 建物情報メンテナンスを行う Model オブジェクト */
	protected BuildingManage buildingManager;

	/**
	 * 建物情報メンテナンスを行う Model　オブジェクトを設定する。<br/>
	 * <br/>
	 * @param userManager 建物メンテナンスの model オブジェクト
	 */
	public void setBuildingManager(BuildingManage buildingManager) {
		this.buildingManager = buildingManager;
	}
	
	/**
	 * 建物閲覧表示処理<br>
	 * <br>
	 * @param request HTTP リクエスト
	 * @param response HTTP レスポンス
	 * @return ModelAndView　のインスタンス
	*/
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// リクエストパラメータを格納した model オブジェクトを生成する。
		// このオブジェクトは View 層への値引渡しに使用される。
        Map<String, Object> model = createModel(request);
        BuildingSearchForm searchForm = (BuildingSearchForm) model.get("searchForm"); 
        
        // システム建物番号
        String sysBuildingCd = searchForm.getSysBuildingCd();
        
        // 指定されたsysBuildingCdに該当する建物の情報を取得する。
        // 建物情報を取得
        Building building = this.buildingManager.searchBuildingPk(sysBuildingCd);
        // 建物基本情報の設定
        model.put("building", building.getBuildingInfo());
        // 最寄り駅情報の設定
        model.put("buildingStationList", building.getBuildingStationInfoList());
        // 建物ランドマーク情報の設定
        model.put("buildingLandmarkList", building.getBuildingLandmarkList());
        
		return new ModelAndView("success", model);
	}

	/**
	 * model オブジェクトを作成し、リクエストパラメータを格納した form オブジェクトを格納する。<br/>
	 * <br/>
	 * @param request HTTP リクエストパラメータ
	 * @return パラメータを設定した Form を格納した model オブジェクトを復帰する。
	 */
	protected Map<String, Object> createModel(HttpServletRequest request) {

		Map<String, Object> model = new HashMap<String, Object>();

		// リクエストパラメータを取得して Form オブジェクトを作成する。
		BuildingFormFactory factory = BuildingFormFactory.getInstance(request);
		model.put("searchForm", factory.createBuildingSearchForm(request));

		return model;
	}
}
