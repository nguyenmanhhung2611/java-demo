package jp.co.transcosmos.dm3.adminCore.building.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.model.BuildingManage;
import jp.co.transcosmos.dm3.core.model.building.Building;
import jp.co.transcosmos.dm3.core.model.building.form.BuildingForm;
import jp.co.transcosmos.dm3.core.model.building.form.BuildingFormFactory;
import jp.co.transcosmos.dm3.core.model.building.form.BuildingSearchForm;
import jp.co.transcosmos.dm3.core.vo.AddressMst;
import jp.co.transcosmos.dm3.core.vo.PrefMst;
import jp.co.transcosmos.dm3.dao.DAO;
import jp.co.transcosmos.dm3.dao.DAOCriteria;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

import org.springframework.web.servlet.ModelAndView;

/**
 * <pre>
 * 建物情報入力確認画面
 * リクエストパラメータで渡された管理ユーザー情報のバリデーションを行い、確認画面を表示する。
 * もしバリデーションエラーが発生した場合は入力画面として表示する。
 * 
 * 【復帰する View 名】
 *    ・"success" : 正常終了
 *    ・"input" : バリデーションエラーによる再入力
 *
 * 担当者       修正日      修正内容
 * ------------ ----------- -----------------------------------------------------
 * I.Shu		2015.03.02	新規作成
 *
 * 注意事項
 * 
 * </pre>
 */
public class BuildingConfirmCommand implements Command {

	/** 処理モード (insert = 新規登録処理、 update=更新処理、 delete=削除処理)*/
	protected String mode;

	/** 建物情報メンテナンスを行う Model オブジェクト */
	protected BuildingManage buildingManager;

	/** 都道府県マスタ取得用 DAO */
	protected DAO<PrefMst> prefMstDAO;
	
	/** 市区町村マスタ取得用 DAO */
	protected DAO<AddressMst> addressMstDAO;
	
	/** Form のバリデーションを実行する場合、true を設定する。　（デフォルト true） */
	protected boolean useValidation = true;
	
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
	 * 市区町村マスタ用DAOを設定する。<br/>
	 * <br/>
	 * 
	 * @return 市区町村マスタ用 DAO
	 */
	public void setAddressMstDAO(DAO<AddressMst> addressMstDAO) {
		this.addressMstDAO = addressMstDAO;
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
	 * 処理モードを設定する<br/>
	 * <br/>
	 * @param mode "insert" = 新規登録処理、"update" = 更新処理、"delete" = 削除処理
	 */
	public void setMode(String mode) {
		this.mode = mode;
	}
	
	/**
	 * Form のバリデーションを実行する場合、true を設定する。　（デフォルト true）<br/>
	 * <br/>
	 * @param useValidation true の場合、Form のバリデーションを実行
	 */
	public void setUseValidation(boolean useValidation) {
		this.useValidation = useValidation;
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
         BuildingForm inputForm = (BuildingForm) model.get("inputForm"); 
         BuildingSearchForm searchForm = (BuildingSearchForm) model.get("searchForm"); 
         
        // 更新モードの場合、更新対象の主キー値がパラメータで渡されていない場合、例外をスローする。
        if ("update".equals(this.mode)){
        	if (StringValidateUtil.isEmpty(inputForm.getSysBuildingCd())) throw new RuntimeException ("pk value is null.");
        }
        
        // view 名の初期値を設定
        String viewName = "success"; 
        
		// バリデーションを実行
        List<ValidationFailure> errors = new ArrayList<ValidationFailure>();
        
		// バリデーションの実行モードが有効の場合、バリデーションを実行する。
        // 削除処理、ロック解除処理の場合はログインID 以外のパラメータは不要なので、spring 側から無効化している。
        if (this.useValidation){
	        if (!inputForm.validate(errors)){
	        	
	        	// バリデーションエラー時は、エラー情報を model に設定し、入力画面を表示する。
	    		// 都道府県マスタを取得する
	    		List<PrefMst> prefMstList = this.prefMstDAO.selectByFilter(null);
	    		model.put("prefMstList", prefMstList);
				// 市区町村マスタを取得する
				model.put("addressMstList", getAddressMstList(inputForm.getPrefCd()));
	        	model.put("errors", errors);
	        	viewName = "input";
	        }
        }
        
        if ("delete".equals(this.mode)){
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
		model.put("inputForm", factory.createBuildingForm(request));

		return model;

	}
	
	/**
	 * 都道府県CDで市区町村リストを取得する。<br/>
	 * <br/>
	 * @param prefCd 都道府県CD
	 * @return 市区町村リストを復帰する。
	 */
	protected List<AddressMst> getAddressMstList(String prefCd) {
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("prefCd", prefCd);
		List<AddressMst> addressMstList = this.addressMstDAO.selectByFilter(criteria);
		return addressMstList;
		
	}
}
