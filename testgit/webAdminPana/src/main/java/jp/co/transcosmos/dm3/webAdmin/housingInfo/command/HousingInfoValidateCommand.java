package jp.co.transcosmos.dm3.webAdmin.housingInfo.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.model.housing.Housing;
import jp.co.transcosmos.dm3.core.vo.AddressMst;
import jp.co.transcosmos.dm3.core.vo.BuildingInfo;
import jp.co.transcosmos.dm3.core.vo.PrefMst;
import jp.co.transcosmos.dm3.core.vo.RouteMst;
import jp.co.transcosmos.dm3.core.vo.StationMst;
import jp.co.transcosmos.dm3.corePana.model.PanaCommonManage;
import jp.co.transcosmos.dm3.corePana.model.housing.PanaHousingPartThumbnailProxy;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingFormFactory;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingInfoForm;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingSearchForm;
import jp.co.transcosmos.dm3.dao.JoinResult;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;
import jp.co.transcosmos.dm3.validation.Validateable;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

import org.springframework.web.servlet.ModelAndView;
/**
 * <pre>
 * 物件基本情報入力確認画面
 * リクエストパラメータで渡されたリフォーム情報のバリデーションを行い、確認画面を表示する。
 * もしバリデーションエラーが発生した場合は入力画面として表示する。
 *
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
public class HousingInfoValidateCommand implements Command {
	/** Housingメンテナンスを行う Model オブジェクト */
	private PanaHousingPartThumbnailProxy panaHousingManager;

	/**
	 * Housingメンテナンスを行う Model　オブジェクトを設定する。<br/>
	 * <br/>
	 *
	 * @param panaHousingManager
	 *            Housingメンテナンスの model オブジェクト
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

		// 物件基本情報テーブルの読込
		Housing housingresults = this.panaHousingManager.searchHousingPk(form
				.getSysHousingCd(),true);

		JoinResult buildingInfo = housingresults.getBuilding().getBuildingInfo();
		String housingKindCd = "";
		if(buildingInfo !=null){
			//物件種別
			housingKindCd = ((BuildingInfo)buildingInfo.getItems().get("buildingInfo")).getHousingKindCd();
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

		// 住所
		String address = "";
		if(!StringValidateUtil.isEmpty(form.getZip())){
			address = address+"〒"+form.getZip()+" ";
		}
		address = address+form.getPrefName()+form.getAddressName()+form.getAddressOther1()+form.getAddressOther2();
		model.put("address", address);

		// 築年
		String compDate = form.getCompDate();
		String year ="";
		String month ="";
		if(!StringValidateUtil.isEmpty(compDate)){
			year = compDate.substring(0, 4);
			month = compDate.substring(4, 6);
		}
		model.put("year", year);
		model.put("month", month);

		if(form.getStationName() != null){
			String[] stationName = new String[form.getStationName().length];
			for(int i=0;i<form.getStationName().length;i++){
				if(!StringValidateUtil.isEmpty(form.getStationName()[i])){
					stationName[i]=form.getStationName()[i]+"駅";
				}
			}
			model.put("stationName", stationName);
		}

		// 郵便番号
		String zip = form.getZip();
		String zipBef ="";
		String zipAft ="";
		if(!StringValidateUtil.isEmpty(zip)){
			zipBef = zip.substring(0, 3);
			zipAft = zip.substring(3, 7);
		}
		model.put("zipBef", zipBef);
		model.put("zipAft", zipAft);

		if(form.getDefaultRouteCd() !=null){
			String[] routeName = new String[form.getDefaultRouteCd().length];
			for(int i=0;i<form.getDefaultRouteCd().length;i++){
				String wkRouteName = panamCommonManager.getRouteNameRr(form.getDefaultRouteCd()[i]);
				if(!StringValidateUtil.isEmpty(wkRouteName)){
					routeName[i]=wkRouteName;
				}
				else{
					routeName[i]=form.getRouteName()[i];
				}
			}
			form.setRouteNameRr(routeName);
		}

		model.put("housingInfoForm", form);
		return new ModelAndView("success", model);
	}
	/**
	 * リストを取得する。<br/>
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
