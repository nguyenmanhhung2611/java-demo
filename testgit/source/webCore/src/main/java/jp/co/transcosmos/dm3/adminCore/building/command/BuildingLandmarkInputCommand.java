package jp.co.transcosmos.dm3.adminCore.building.command;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.model.BuildingManage;
import jp.co.transcosmos.dm3.core.model.building.Building;
import jp.co.transcosmos.dm3.core.model.building.form.BuildingFormFactory;
import jp.co.transcosmos.dm3.core.model.building.form.BuildingLandmarkForm;
import jp.co.transcosmos.dm3.core.model.building.form.BuildingSearchForm;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;

import org.springframework.web.servlet.ModelAndView;

/**
 * <pre>
 * 建物地域情報入力画面
 * 
 *     
 * 【更新登録の場合】
 *     ・リクエストパラメータの受取りのみを行う。
 *      
 * 【リクエストパラメータ（command） が "back"の場合】
 *     ・リクエストパラメータで渡された入力値を入力画面に表示する。　（入力確認画面から復帰したケース。）
 *
 * 【復帰する View 名】
 *    ・"success" : 正常終了
 *    ・"notFound" : 該当データが存在しない場合
 *     
 * 担当者       修正日      修正内容
 * ------------ ----------- -----------------------------------------------------
 * I.Shu		2015.03.17	新規作成
 *
 * 注意事項
 *
 * </pre>
 */
public class BuildingLandmarkInputCommand implements Command {
	
	/** 処理モード (insert = 新規登録処理、 update=更新処理)*/
	protected String mode;
	
	/** 建物情報メンテナンスを行う Model オブジェクト */
	protected BuildingManage buildingManager;

	/**
	 * 処理モードを設定する<br/>
	 * <br/>
	 * @param mode "insert" = 新規登録処理、"update" = 更新処理
	 */
	public void setMode(String mode) {
		this.mode = mode;
	}
	
	/**
	 * 建物情報メンテナンスを行う Model　オブジェクトを設定する。<br/>
	 * <br/>
	 * @param userManager 建物メンテナンスの model オブジェクト
	 */
	public void setBuildingManager(BuildingManage buildingManager) {
		this.buildingManager = buildingManager;
	}
	
	/**
	 * 地域情報入力画面表示処理<br>
	 * <br>
	 * @param request HTTP リクエスト
	 * @param response HTTP レスポンス
	*/
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// リクエストパラメータを格納した model オブジェクトを生成する。
		// このオブジェクトは View 層への値引渡しに使用される。
        Map<String, Object> model = createModel(request);
        BuildingLandmarkForm inputForm = (BuildingLandmarkForm) model.get("inputForm"); 
        BuildingSearchForm searchForm = (BuildingSearchForm) model.get("searchForm"); 

        // システム建物番号
        String sysBuildingCd = searchForm.getSysBuildingCd();
        
		// 更新処理の場合、処理対象となる主キー値がパラメータで渡されていない場合、例外をスローする。
		if ("update".equals(this.mode) && StringValidateUtil.isEmpty(sysBuildingCd)){
			throw new RuntimeException ("pk value is null.");
		}
		
		// command パラメータが "back" の場合、入力確認画面からの復帰なので、ＤＢから初期値を取得しない。
		// （リクエストパラメータから取得した値を使用する。）
		String command = inputForm.getCommand();
		
		if (command != null && "back".equals(command)) {
			return new ModelAndView("success", model);
		}

        // 指定されたsysBuildingCdに該当する建物の情報を取得する。
        // 建物情報を取得
        Building building = this.buildingManager.searchBuildingPk(sysBuildingCd);
        // 地域情報の設定
        inputForm.setDefaultData(building);

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

		// 検索条件、および、画面コントロールパラメータを取得する。
		BuildingSearchForm searchForm = factory.createBuildingSearchForm(request);
		model.put("searchForm", searchForm);	

		
		// 入力フォームの受け取りは、command パラメータの値によって異なる。
		// command パラメータが渡されない場合、入力画面の初期表示とみなし、空のフォームを復帰する。
		// command パラメータで "back" が渡された場合、入力確認画面からの復帰を意味する。
		// その場合はリクエストパラメータを受け取り、入力画面へ初期表示する。
		// もし、"back" 以外の値が渡された場合、command パラメータが渡されない場合と同等に扱う。

		BuildingLandmarkForm inputForm = factory.createBuildingLandmarkForm(request);
		String command = inputForm.getCommand();
		
		if (command != null && ("back".equals(command) || "update".equals(command))){
			model.put("inputForm", inputForm);
		} else {
			model.put("inputForm", factory.createBuildingStationInfoForm());
		}

		return model;

	}
}
