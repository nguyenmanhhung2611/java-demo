package jp.co.transcosmos.dm3.webAdmin.housingInfo.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.model.exception.NotFoundException;
import jp.co.transcosmos.dm3.core.model.housing.Housing;
import jp.co.transcosmos.dm3.core.vo.AddressMst;
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
 * 物件基本情報編集画面コマンドクラス
 * 物件基本情報編集画面を表示する際に呼び出されるコマンド。
 *
 * 担当者		修正日		修正内容
 * ------------ ----------- -----------------------------------------------------
 * fan			2015.04.06	新規作成
 *
 * </pre>
 */
public class HousingInfoInputCommand implements Command {
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

		// 事前チェック:物件基本情報テーブルの読込
		Housing housingresults = this.panaHousingManager.searchHousingPk(form
				.getSysHousingCd(),true);

		if (housingresults == null) {
			// データの存在しない場合,メッセージ："｛0｝物件情報が存在しない"表示
			throw new NotFoundException();
		}

		JoinResult housingInfo = housingresults.getHousingInfo();
		JoinResult buildingInfo = housingresults.getBuilding().getBuildingInfo();
		List<JoinResult> buildingStationInfoList = housingresults.getBuilding().getBuildingStationInfoList();

		// 入力確認画面の「戻る」ボタンから返す場合
		if ("back".equals(form.getCommand())) {
			// 画面表示用アップロード画像編集情報の再設定
			// リストを取得する
			getMstList(model ,  form);
			model.put("housingInfoForm", form);
			form.setCommand(form.getComflg());

			// 取得したデータをレンダリング層へ渡す
			return new ModelAndView("success", model);
		}

		// 入力確認画面の「住所検索」ボタンの場合
		if("address".equals(form.getCommand())){

			// バリデーション処理
			Validateable validateableForm = (Validateable) form;
			// エラーメッセージ用のリストを作成
			List<ValidationFailure> errors = new ArrayList<ValidationFailure>();

			// バリデーションを実行
			if (!validateableForm.validate(errors)) {
				// エラー処理
				// エラーオブジェクトと、フォームオブジェクトをModelAndView に渡している
				model.put("errors", errors);
				// リストを取得する
	            getMstList(model ,  form);
				model.put("housingInfoForm", form);

				// 取得したデータをレンダリング層へ渡す
				return new ModelAndView("validationError", model);
			}

			// 市区町村マスタを取得する
			String zip = form.getZip();

			String[] zipMst = panamCommonManager.getZipToAddress(zip);
			// 郵便番号の対する住所がない場合
			if(zipMst == null || zipMst[0] != "0"){

				ValidationFailure vf = new ValidationFailure(
	                    "housingInfoZipInput", "", "", null);
	            errors.add(vf);
	            model.put("errors", errors);
	            // リストを取得する
	            getMstList(model ,  form);
				model.put("housingInfoForm", form);
				form.setCommand(form.getComflg());


				// 取得したデータをレンダリング層へ渡す
				return new ModelAndView("validationError", model);
			}
			String prefCd = form.getPrefCd();

			form.setPrefCd(zipMst[1]);
			form.setAddressCd(zipMst[2]);

			//都道府県CD
			if(!prefCd.equals(form.getPrefCd())){
				form.setDefaultRouteCd(null);
				form.setRouteName(null);
				form.setStationCd(null);
				form.setStationName(null);
			}
			// リストを取得する
 			getMstList(model ,  form);
			model.put("housingInfoForm", form);
			form.setCommand(form.getComflg());

			// 取得したデータをレンダリング層へ渡す
			return new ModelAndView("success", model);
		}

		form.setDefaultData(housingInfo, buildingInfo,buildingStationInfoList);

		// Model設定
		String wkModel;
		if (housingresults.getBuilding() == null) {
			wkModel = "insert";
		} else {
			wkModel = "update";
		}
		form.setCommand(wkModel);
		form.setComflg(wkModel);

		// リストを取得する
		getMstList(model ,  form);
		model.put("housingInfoForm", form);
		// 取得したデータをレンダリング層へ渡す
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
