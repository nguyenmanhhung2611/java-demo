package jp.co.transcosmos.dm3.webAdmin.housingInfo.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.model.adminUser.AdminUserInterface;
import jp.co.transcosmos.dm3.core.model.building.BuildingPartThumbnailProxy;
import jp.co.transcosmos.dm3.core.model.housing.Housing;
import jp.co.transcosmos.dm3.core.util.AdminLoginUserUtils;
import jp.co.transcosmos.dm3.core.vo.AddressMst;
import jp.co.transcosmos.dm3.core.vo.BuildingInfo;
import jp.co.transcosmos.dm3.core.vo.PrefMst;
import jp.co.transcosmos.dm3.core.vo.RouteMst;
import jp.co.transcosmos.dm3.core.vo.StationMst;
import jp.co.transcosmos.dm3.corePana.model.PanaCommonManage;
import jp.co.transcosmos.dm3.corePana.model.building.form.PanaBuildingForm;
import jp.co.transcosmos.dm3.corePana.model.building.form.PanaBuildingFormFactory;
import jp.co.transcosmos.dm3.corePana.model.building.form.PanaBuildingStationInfoForm;
import jp.co.transcosmos.dm3.corePana.model.housing.PanaHousingPartThumbnailProxy;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingForm;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingFormFactory;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingInfoForm;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingSearchForm;
import jp.co.transcosmos.dm3.corePana.util.PanaCommonUtil;
import jp.co.transcosmos.dm3.corePana.vo.HousingInfo;
import jp.co.transcosmos.dm3.dao.JoinResult;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;
import jp.co.transcosmos.dm3.validation.Validateable;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

import org.springframework.web.servlet.ModelAndView;
/**
 * 物件基本情報変更完了画面
 * <p>
 * 【新規登録の場合】<br/>
 * <ul>
 * <li>リクエストパラメータを受け取り、バリデーションを実行する。</li>
 * <li>バリデーションが正常終了した場合、ステータスを新規登録する。</li>
 * </ul>
 * <br/>
 * 【更新登録の場合】<br/>
 * <ul>
 * <li>リクエストパラメータを受け取り、バリデーションを実行する。</li>
 * <li>バリデーションが正常終了した場合、ステータス情報を更新する。</li>
 * <li>もし、更新対象データが存在しない場合、更新処理が継続できないので該当無し画面を表示する。</li>
 * </ul>
 * <br/>
 * 【復帰する View 名】<br/>
 * <ul>
 * <li>success</li>:正常終了（リダイレクトページ）
 * <li>notFound</li>:該当データが存在しない場合（更新処理の場合）
 * <li>comp</li>:完了画面表示
 * </ul>
 * <p>
 *
 * <pre>
 * 【復帰する View 名】
 *    ・"success" : 正常終了
 *
 * 担当者       修正日      修正内容
 * ------------ ----------- -----------------------------------------------------
 * fan			2015.04.06	新規作成
 *
 * 注意事項
 *
 * </pre>
 */
public class HousingInfoUpdateCommand implements Command {

	/** 住宅診断情報メンテナンスを行う Model オブジェクト */
	private PanaHousingPartThumbnailProxy panaHousingManager;

	/**
	 * 住宅診断情報メンテナンスを行う Model　オブジェクトを設定する。<br/>
	 * <br/>
	 *
	 * @param housingManager
	 *            住宅診断情報メンテナンスの model オブジェクト
	 */
	public void setPanaHousingManager(PanaHousingPartThumbnailProxy panaHousingManager) {
		this.panaHousingManager = panaHousingManager;
	}

	/** マスタ情報メンテナンスを行う Model オブジェクト */
	private PanaCommonManage panamCommonManager;

	/**
	 * マスタ情報メンテナンスを行う Model　オブジェクトを設定する。<br/>
	 * <br/>
	 *
	 * @param panaCommonManager
	 *            マスタ情報メンテナンスの model オブジェクト
	 */
	public void setPanamCommonManager(PanaCommonManage panamCommonManager) {
		this.panamCommonManager = panamCommonManager;
	}

	private BuildingPartThumbnailProxy buildingManager;


	public void setBuildingManager(BuildingPartThumbnailProxy buildingManager) {
		this.buildingManager = buildingManager;
	}

	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Map<String, Object> model = new HashMap<String, Object>();
		// リクエストパラメータを取得して Form オブジェクトを作成する。
		PanaHousingFormFactory factory = PanaHousingFormFactory.getInstance(request);

		// リクエストパラメータを格納した form オブジェクトを生成する。
		PanaHousingInfoForm form = factory.createPanaHousingInfoForm(request);
		PanaHousingSearchForm searchForm = factory.createPanaHousingSearchForm(request);
		model.put("searchForm", searchForm);

		// ログインユーザーの情報を取得する。　（タイムスタンプの更新用）
		AdminUserInterface loginUser = AdminLoginUserUtils.getInstance(request)
				.getLoginUserInfo(request, response);

		// ユーザIDを取得
		String userId = String.valueOf(loginUser.getUserId().toString());

		String command = form.getCommand();
		String sysHousingCd = form.getSysHousingCd();
		if (command != null && command.equals("redirect")) {
			model.put("sysHousingCd", sysHousingCd);
			return new ModelAndView("comp", model);
		}

		// 物件基本情報テーブルの読込
		Housing housingresults = this.panaHousingManager.searchHousingPk(form
				.getSysHousingCd(),true);

		JoinResult buildingInfoResults = housingresults.getBuilding().getBuildingInfo();
		String housingKindCd = "";
		if(buildingInfoResults !=null){
			//物件種別
			housingKindCd = ((BuildingInfo)buildingInfoResults.getItems().get("buildingInfo")).getHousingKindCd();
		}

		// バリデーション処理
		Validateable validateableForm = (Validateable) form;
		// エラーメッセージ用のリストを作成
		List<ValidationFailure> errors = new ArrayList<ValidationFailure>();

		if(!housingKindCd.equals(form.getHousingKindCd())){
            ValidationFailure vf = new ValidationFailure(
                    "housingKindCdError", "housingInfo.input.housingKindCd", "", null);
            errors.add(vf);
	        // エラー処理
			// エラーオブジェクトと、フォームオブジェクトをModelAndView に渡している
			model.put("errors", errors);
			// リストを取得する
	         getMstList(model ,  form);
			model.put("housingInfoForm", form);
			return new ModelAndView("validationError", model);
		}

		// バリデーションを実行
		if (!validateableForm.validate(errors)) {
			// エラー処理
			// エラーオブジェクトと、フォームオブジェクトをModelAndView に渡している
			model.put("errors", errors);
			// リストを取得する
            getMstList(model ,  form);
			model.put("housingInfoForm", form);
			return new ModelAndView("validationError", model);
		}
		// リクエストパラメータを取得して Form オブジェクトを作成する。
		PanaHousingFormFactory panaHousingFormFactory = new PanaHousingFormFactory();

		 // 物件基本情報を取得する。
        HousingInfo housingInfo = ((HousingInfo) housingresults.getHousingInfo().getItems().get("housingInfo"));
		// PanaHousingFormを設定する
		PanaHousingForm panaHousingForm = panaHousingFormFactory.createPanaHousingForm();
		PanaCommonUtil.copyProperties(panaHousingForm, housingInfo);
		panaHousingForm.setSysHousingCd(form.getSysHousingCd());
		panaHousingForm.setSysBuildingCd(form.getSysBuildingCd());
		panaHousingForm.setHousingCd(form.getHousingCd());
		panaHousingForm.setDisplayHousingName(form.getDisplayHousingName());
		panaHousingForm.setPrice(form.getPrice());
		panaHousingForm.setLayoutCd(form.getLayoutCd());
		panaHousingForm.setLandArea(form.getLandArea());
		panaHousingForm.setLandAreaMemo(form.getLandAreaMemo());
		panaHousingForm.setPersonalArea(form.getPersonalArea());
		panaHousingForm.setPersonalAreaMemo(form.getPersonalAreaMemo());
		if(form.getTimeFromBusStop() !=null && form.getTimeFromBusStop().length>0){
			String wkminWalkingTime = form.getTimeFromBusStop()[0];

			for(int i=0;i<form.getTimeFromBusStop().length;i++){
				if(form.getTimeFromBusStop()[i] != ""){
					if(Integer.valueOf(wkminWalkingTime==""?"0":wkminWalkingTime) > Integer.valueOf(form.getTimeFromBusStop()[i])){
						wkminWalkingTime = form.getTimeFromBusStop()[i];
					}
				}
			}
			panaHousingForm.setMinWalkingTime(wkminWalkingTime);
		}

		//「物件基本情報」テーブル更新時、「リフォーム込価格（最小）」、「リフォーム込価格（最大）」も更新する。リフォームプランが存在しない場合、画面項目｢価格｣の値を設定する
		//　　リフォーム込価格（最小）＝画面項目「価格」＋「リフォームプラン」テーブルの最小の「リフォーム価格」
		//　　リフォーム込価格（最大）＝画面項目「価格」＋「リフォームプラン」テーブルの最大の「リフォーム価格」
//		List<ReformPlan> reformPlanList = this.reformManager.searchReformPlan(form.getSysHousingCd());
//		if(reformPlanList!=null){
//			Long wkPriceFullMin = reformPlanList.get(0).getPlanPrice();
//			Long wkPriceFullMax = reformPlanList.get(0).getPlanPrice();
//			for(int i=0;i<reformPlanList.size();i++){
//				if(wkPriceFullMin > reformPlanList.get(i).getPlanPrice()){
//					wkPriceFullMin = reformPlanList.get(i).getPlanPrice();
//				}
//				if(wkPriceFullMax < reformPlanList.get(i).getPlanPrice()){
//					wkPriceFullMax = reformPlanList.get(i).getPlanPrice();
//				}
//			}
//			panaHousingForm.setPriceFullMin(String.valueOf(wkPriceFullMin));
//			panaHousingForm.setPriceFullMax(String.valueOf(wkPriceFullMax));
//		}
//		else{
//			panaHousingForm.setPriceFullMin(form.getPrice());
//			panaHousingForm.setPriceFullMax(form.getPrice());
//		}

		// リクエストパラメータを取得して Form オブジェクトを作成する。
		PanaBuildingFormFactory panaBuildingFormFactory = new PanaBuildingFormFactory();

		// 建物基本情報を取得する。
		BuildingInfo buildingInfo = (BuildingInfo)housingresults.getBuilding().getBuildingInfo().getItems().get("buildingInfo");
		// PanaBuildingFormを設定する
		PanaBuildingForm panaBuildingForm = panaBuildingFormFactory.createPanaBuildingForm();
		PanaCommonUtil.copyProperties(panaBuildingForm, buildingInfo);
		panaBuildingForm.setSysBuildingCd(form.getSysBuildingCd());
		panaBuildingForm.setBuildingCd(form.getSysBuildingCd());
		panaBuildingForm.setHousingKindCd(form.getHousingKindCd());
		panaBuildingForm.setCompDate(form.getCompDate());
		panaBuildingForm.setZip(form.getZip());
		panaBuildingForm.setPrefCd(form.getPrefCd());
		panaBuildingForm.setPrefName(form.getPrefName());
		panaBuildingForm.setAddressCd(form.getAddressCd());
		panaBuildingForm.setAddressName(form.getAddressName());
		panaBuildingForm.setAddressOther1(form.getAddressOther1());
		panaBuildingForm.setAddressOther2(form.getAddressOther2());

		// PanaBuildingStationInfoFormを設定する
		PanaBuildingStationInfoForm panaBuildingStationInfoForm = panaBuildingFormFactory.createPanaBuildingStationInfoForm();
		panaBuildingStationInfoForm.setSysBuildingCd(form.getSysBuildingCd());
		panaBuildingStationInfoForm.setDefaultRouteCd(form.getDefaultRouteCd()==null?new String[1]:form.getDefaultRouteCd());

		String[] routeName = form.getRouteName();
		if(form.getRouteName() != null){
			for(int i=0;i<form.getRouteName().length;i++){
				String wkRouteName = panamCommonManager.getRouteName(form.getDefaultRouteCd()[i]);
				if(!StringValidateUtil.isEmpty(wkRouteName)){
					routeName[i]=wkRouteName;
				}
			}
		}

		panaBuildingStationInfoForm.setRouteName(routeName);

//		panaBuildingStationInfoForm.setRouteName(form.getRouteName());
		panaBuildingStationInfoForm.setStationCd(form.getStationCd());
		panaBuildingStationInfoForm.setStationName(form.getStationName());
		panaBuildingStationInfoForm.setBusCompany(form.getBusCompany());
		panaBuildingStationInfoForm.setTimeFromBusStop(form.getTimeFromBusStop());
		// 物件基本情報テーブルのupdate処理
		this.panaHousingManager.updateHousing(panaHousingForm , userId);
		// 建物基本情報テーブルのupdate処理
		this.buildingManager.updateBuildingInfo(panaBuildingForm, userId);
		// 建物最寄り駅情報テーブルのupdate処理
		this.buildingManager.updateBuildingStationInfo(panaBuildingStationInfoForm);


		// 処理モード【hidden】 = insertの場合
		if("insert".equals(command)){
			// フォームの入力値をバリーオブジェクトに設定する。
            this.panaHousingManager.updateBuildingDtlInfo(form,userId);
		}
		// 処理モード【hidden】 = updateの場合、
		if("update".equals(command)){
			// フォームの入力値をバリーオブジェクトに設定する。
			this.panaHousingManager.updateBuildingDtlInfo(form,userId);
		}

		model.put("housingInfoForm", form);
		return new ModelAndView("success", model);

	}
	/**
	 * 都道府県、市区町村、路線、駅リストを取得する。<br/>
	 * <br/>
	 * @param model modelD
	 * @param form form
	 */
	protected void getMstList(Map<String, Object> model , PanaHousingInfoForm form)  throws Exception{

		//沿線CD
		String[] defaultRouteCd = new String[3];
		//沿線名
		String[] routeName = new String[3];
		//駅CD
		String[] stationCd = new String[3];
		//駅名
		String[] stationName = new String[3];
		if(form.getDefaultRouteCd() != null){
			for(int i=0;i<form.getDefaultRouteCd().length;i++){
				defaultRouteCd[i]=form.getDefaultRouteCd()[i];
				routeName[i]=form.getRouteName()[i];
				stationCd[i]=form.getStationCd()[i];
				stationName[i]=form.getStationName()[i];
			}
		}

		form.setOldRouteName(routeName);
		form.setOldStationName(stationName);
		form.setOldDefaultRouteCd(defaultRouteCd);
		form.setOldStationCd(stationCd);

		List<PrefMst> prefMstList = panamCommonManager.getPrefMstList();
		if(prefMstList!=null&&prefMstList.size()>0){
			// 都道府県マスタを取得する
			model.put("prefMstList", prefMstList);
		}

		String[] oldRouteName = new String[3];
		String[] oldRouteCd = new String[3];
		for(int i=0;i<form.getOldDefaultRouteCd().length;i++){
			oldRouteCd[i]=form.getOldDefaultRouteCd()[i];
			oldRouteName[i]=form.getOldRouteName()[i];
		}

		if (form.getPrefCd() != null) {
			List<AddressMst> addressMstList= panamCommonManager.getPrefCdToAddressMstList(form.getPrefCd());
			List<RouteMst> routeMstList = panamCommonManager.getPrefCdToRouteMstList(form.getPrefCd());

            // 市区町村マスタを取得する
    		model.put("addressMstList", addressMstList);
    		// 路線マスタを取得する
    		model.put("routeMstList", routeMstList);

    		// 路線CD に該当するデータが無い場合に代表路線名を使用
    		if(form.getOldDefaultRouteCd() != null){
    			for(int j=0;j<form.getOldDefaultRouteCd().length;j++){
        			for(int i=0;i<routeMstList.size();i++){
        				if(routeMstList.get(i).getRouteCd().equals(form.getOldDefaultRouteCd()[j]) ){
        					oldRouteName[j] = "";
        					oldRouteCd[j] = "";
            			}
        			}
        		}
    		}
		}

		String[] oldStationName = new String[form.getOldStationCd().length];
		String[] oldStationCd = new String[form.getOldStationCd().length];
		for(int i=0;i<form.getOldDefaultRouteCd().length;i++){
			oldStationName[i]=form.getOldStationName()[i];
			oldStationCd[i]=form.getOldStationCd()[i];
		}

		// 駅マスタを取得する
		String[] routeCd = form.getDefaultRouteCd();
		if (routeCd != null) {
			List<List<StationMst>> stationMstList = new ArrayList<List<StationMst>>();
			for (int i = 0; i < routeCd.length; i++) {
				if (StringValidateUtil.isEmpty(routeCd[i])) {
					stationMstList.add(null);
				} else {
					stationMstList.add(panamCommonManager.getRouteCdToStationMstList(routeCd[i]));
				}
			}
			model.put("stationMstList", stationMstList);

			// 駅CD に該当するデータが無い場合に駅名を使用
    		for(int j=0;j<form.getOldStationCd().length;j++){
    			List<StationMst> stationMst = panamCommonManager.getRouteCdToStationMstList(form.getOldDefaultRouteCd()[j]);
    			for(int jj=0;jj<stationMst.size();jj++){
    				if(stationMst.get(jj).getStationCd().equals(form.getOldStationCd()[j]) ){
    					oldStationName[j] = "";
    					oldStationCd[j] = "";
        			}
    			}
    		}
		}
		form.setOldStationName(oldStationName);
		form.setOldStationCd(oldStationCd);
		form.setOldRouteName(oldRouteName);
		form.setOldDefaultRouteCd(oldRouteCd);
	}
}