package jp.co.transcosmos.dm3.webFront.housingRequest.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.model.HousingRequestManage;
import jp.co.transcosmos.dm3.core.model.housingRequest.HousingRequest;
import jp.co.transcosmos.dm3.core.model.mypage.MypageUserInterface;
import jp.co.transcosmos.dm3.core.util.FrontLoginUserUtils;
import jp.co.transcosmos.dm3.core.vo.PrefMst;
import jp.co.transcosmos.dm3.core.vo.RouteMst;
import jp.co.transcosmos.dm3.core.vo.StationMst;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonParameters;
import jp.co.transcosmos.dm3.corePana.model.PanaCommonManage;
import jp.co.transcosmos.dm3.corePana.model.housing.PanaHousingRequest;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingFormFactory;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingRequestForm;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;

/**
 * 物件リクエスト入力画面
 *
 * <pre>
 * 【復帰する View 名】
 *    ・"success" : 正常終了
 * 
 * 担当者       修正日      修正内容
 * ------------ ----------- -----------------------------------------------------
 *   焦		  2015.04.22    新規作成
 * 
 * 注意事項
 *
 * </pre>
 */
public class HousingRequestConfirmCommand implements Command {

	/** 共通パラメータオブジェクト */
	private PanaCommonParameters commonParameters;

	/** 共通コード変換処理 */
	protected CodeLookupManager codeLookupManager;

	/** 物件情報用 Model オブジェクト */
	private HousingRequestManage housingRequestManage;

	/**
	 * 共通パラメータオブジェクトを設定する。<br/>
	 * <br/>
	 * 
	 * @param commonParameters
	 *            共通パラメータオブジェクト
	 */
	public void setCommonParameters(PanaCommonParameters commonParameters) {
		this.commonParameters = commonParameters;
	}

	/**
	 * 物件リクエスト情報用 Model オブジェクトを設定する。<br/>
	 * <br/>
	 *
	 * @param housingRequestManage
	 *            物件リクエスト情報用 Model オブジェクト
	 */
	public void setHousingRequestManage(HousingRequestManage housingRequestManage) {
		this.housingRequestManage = housingRequestManage;
	}

	/**
	 * 共通コード変換オブジェクトを設定する。<br/>
	 * <br/>
	 *
	 * @param codeLookupManager
	 */
	public void setCodeLookupManager(CodeLookupManager codeLookupManager) {
		this.codeLookupManager = codeLookupManager;
	}

	/** 物件情報用 Model オブジェクト */
	private PanaCommonManage panaCommonManage;

	/**
	 * 共通情報取用 Model オブジェクトを設定する。<br/>
	 * <br/>
	 * 
	 * @param PanaCommonManage
	 *            共通情報取用 Model オブジェクト
	 */
	public void setPanaCommonManage(PanaCommonManage panaCommonManage) {
		this.panaCommonManage = panaCommonManage;
	}

	/**
	 * 物件リクエスト入力画面表示処理<br>
	 * <br>
	 *
	 * @param request
	 *            HTTP リクエスト
	 * @param response
	 *            HTTP レスポンス
	 */
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {

		Map<String, Object> model = new HashMap<String, Object>();

		PanaHousingFormFactory factory = PanaHousingFormFactory.getInstance(request);

		// ページ処理用のフォームオブジェクトを作成
		PanaHousingRequestForm housingRequestForm = factory.createPanaHousingRequestForm(request);

		System.out.println("startDate :" + housingRequestForm.getStartDate());
		System.out.println("endDate :" + housingRequestForm.getEndDate());

		// バリデーションを実行
		List<ValidationFailure> errors = new ArrayList<ValidationFailure>();

		if (!"confirm".equals(housingRequestForm.getModel())) {
			if (!housingRequestForm.validate(errors)) {

				// 都道府県リストの設定
				List<PrefMst> prefMstList = this.panaCommonManage.getPrefMstList();
				List<RouteMst> routeMstList = this.panaCommonManage
						.getPrefCdToRouteMstListWithGroupBy(housingRequestForm.getPrefCd());
				List<StationMst> stationList = this.panaCommonManage.getRouteCdToStationMstList(housingRequestForm
						.getRouteCd());

				model.put("prefMstList", prefMstList);
				model.put("routeMstList", routeMstList);
				model.put("stationMstList", stationList);

				// バリデーションエラーあり
				model.put("errors", errors);
				model.put("housingRequestForm", housingRequestForm);

				String prefName = this.panaCommonManage.getPrefName(housingRequestForm.getPrefCd());
				String routeName = this.panaCommonManage.getRouteName(housingRequestForm.getRouteCd());
				String stationName = this.panaCommonManage.getStationName(housingRequestForm.getStationCd());

				model.put("prefName", prefName);
				model.put("routeName", routeName);
				model.put("stationName", stationName);

				return new ModelAndView("validFail", model);
			}
		}
		model.put("housingRequestForm", housingRequestForm);
		// ログインユーザーの情報を取得
		MypageUserInterface loginUser = FrontLoginUserUtils.getInstance(request).getMypageLoginUserInfo(request,
				response);
		// ユーザIDを取得
		String userId = "";
		if (loginUser != null) {
			userId = loginUser.getUserId().toString();
			model.put("loginFlg", 0);
		} else {
			userId = FrontLoginUserUtils.getInstance(request).getAnonLoginUserInfo(request, response).getUserId()
					.toString();
			model.put("loginFlg", 1);
		}

		if ("confirm".equals(housingRequestForm.getModel())) {
			// 伝達情報の物件リクエストID（housing_request_id）
			if (StringUtils.isEmpty(housingRequestForm.getHousingRequestId())) {
				throw new RuntimeException("物件リクエストIDが指定されていません.");
			}

			// 物件リクエスト情報
			List<HousingRequest> requestList = new ArrayList<HousingRequest>();
			HousingRequest requestInfo = new PanaHousingRequest();
			String housingRequestId = housingRequestForm.getHousingRequestId();

			requestList = this.housingRequestManage.searchRequest(userId);

			int searchCount = 0;
			for (int i = 0; i < requestList.size(); i++) {
				if (!StringUtils.isEmpty(housingRequestId)) {
					if (housingRequestId.equals(requestList.get(i).getHousingRequestInfo().getHousingRequestId())) {
						requestInfo = requestList.get(i);
						searchCount++;
					}
				}
			}
			System.out.println("request form . route: " + requestInfo.getHousingRequestInfo().getHousingRequestId());
			model.put("housingRequestForm", housingRequestForm);

			String prefName = this.panaCommonManage.getPrefName(housingRequestForm.getPrefCd());
			String routeName = this.panaCommonManage.getRouteName(housingRequestForm.getRouteCd());
			String stationName = this.panaCommonManage.getStationName(housingRequestForm.getStationCd());

			model.put("prefName", prefName);
			model.put("routeName", routeName);
			model.put("stationName", stationName);

			if (searchCount == 0) {
				return new ModelAndView("404");
			}

			// Form へ初期値を設定する。
			if (requestList != null && requestList.size() > 0) {
				housingRequestForm.setDefaultData(requestInfo);
			}
			valueToKey(housingRequestForm);

		}

		if ("insert".equals(housingRequestForm.getModel())) {
			// 登録上限件数チェック
			int nowCnt = this.housingRequestManage.searchRequestCnt(userId);
			if (!(nowCnt < this.commonParameters.getMaxHousingRequestCnt())) {

				ValidationFailure vf = new ValidationFailure("maxAddError", String.valueOf(this.commonParameters
						.getMaxHousingRequestCnt()), "", null);
				errors.add(vf);
				// エラー処理
				// エラーオブジェクトと、フォームオブジェクトをModelAndView に渡している
				model.put("errors", errors);
				// 都道府県リストの設定
				List<PrefMst> prefMstList = this.panaCommonManage.getPrefMstList();
				model.put("prefMstList", prefMstList);
				// リストを取得する
				model.put("housingRequestForm", housingRequestForm);
				return new ModelAndView("validFail", model);
			}
		}

		// 都道府県リストの設定
		String prefName = this.panaCommonManage.getPrefName(housingRequestForm.getPrefCd());
		String routeName = this.panaCommonManage.getRouteName(housingRequestForm.getRouteCd());
		String stationName = this.panaCommonManage.getStationName(housingRequestForm.getStationCd());

		model.put("prefName", prefName);
		model.put("routeName", routeName);
		model.put("stationName", stationName);
		// 取得したデータをレンダリング層へ渡す
		return new ModelAndView("success", model);
	}

	protected void valueToKey(PanaHousingRequestForm housingRequestForm) {

		if ("01".equals(housingRequestForm.getHousingKindCd())) {

			// 予算
			Iterator<String> priceIte = this.codeLookupManager.getKeysByLookup("price");

			while (priceIte.hasNext()) {

				String price = priceIte.next();
				// 予算・下限
				if (!StringUtils.isEmpty(housingRequestForm.getPriceLowerMansion())) {

					String value = this.codeLookupManager.lookupValue("price", price);

					if (value.equals(housingRequestForm.getPriceLowerMansion())) {

						housingRequestForm.setPriceLowerMansion(price);
					}
				}

				// 予算・上限
				if (!StringUtils.isEmpty(housingRequestForm.getPriceUpperMansion())) {

					String value = this.codeLookupManager.lookupValue("price", price);

					if (value.equals(housingRequestForm.getPriceUpperMansion())) {

						housingRequestForm.setPriceUpperMansion(price);
					}
				}
			}

			// 専有面積
			Iterator<String> personalAreaIte = this.codeLookupManager.getKeysByLookup("area");

			while (personalAreaIte.hasNext()) {

				String personalArea = personalAreaIte.next();
				// 専有面積・下限
				if (!StringUtils.isEmpty(housingRequestForm.getPersonalAreaLowerMansion())) {

					String value = this.codeLookupManager.lookupValue("area", personalArea);

					if (value.equals(housingRequestForm.getPersonalAreaLowerMansion())) {

						housingRequestForm.setPersonalAreaLowerMansion(personalArea);
					}
				}

				// 専有面積・上限
				if (!StringUtils.isEmpty(housingRequestForm.getPersonalAreaUpperMansion())) {

					String value = this.codeLookupManager.lookupValue("area", personalArea);

					if (value.equals(housingRequestForm.getPersonalAreaUpperMansion())) {

						housingRequestForm.setPersonalAreaUpperMansion(personalArea);
					}
				}

			}
		}

		if ("02".equals(housingRequestForm.getHousingKindCd())) {
			// 予算
			Iterator<String> priceIte = this.codeLookupManager.getKeysByLookup("price");

			while (priceIte.hasNext()) {

				String price = priceIte.next();
				// 予算・下限
				if (!StringUtils.isEmpty(housingRequestForm.getPriceLowerHouse())) {

					String value = this.codeLookupManager.lookupValue("price", price);

					if (value.equals(housingRequestForm.getPriceLowerHouse())) {

						housingRequestForm.setPriceLowerHouse(price);
					}
				}

				// 予算・上限
				if (!StringUtils.isEmpty(housingRequestForm.getPriceUpperHouse())) {

					String value = this.codeLookupManager.lookupValue("price", price);

					if (value.equals(housingRequestForm.getPriceUpperHouse())) {

						housingRequestForm.setPriceUpperHouse(price);
					}
				}
			}

			// 建物面積
			Iterator<String> buildingAreaIte = this.codeLookupManager.getKeysByLookup("area");

			while (buildingAreaIte.hasNext()) {

				String buildingArea = buildingAreaIte.next();
				// 建物面積・下限
				if (!StringUtils.isEmpty(housingRequestForm.getBuildingAreaLowerHouse())) {

					String value = this.codeLookupManager.lookupValue("area", buildingArea);

					if (value.equals(housingRequestForm.getBuildingAreaLowerHouse())) {

						housingRequestForm.setBuildingAreaLowerHouse(buildingArea);
					}
				}

				// 建物面積・上限
				if (!StringUtils.isEmpty(housingRequestForm.getBuildingAreaUpperHouse())) {

					String value = this.codeLookupManager.lookupValue("area", buildingArea);

					if (value.equals(housingRequestForm.getBuildingAreaUpperHouse())) {

						housingRequestForm.setBuildingAreaUpperHouse(buildingArea);
					}
				}
			}

			// 土地面積
			Iterator<String> landAreaIte = this.codeLookupManager.getKeysByLookup("area");

			while (landAreaIte.hasNext()) {

				String landArea = landAreaIte.next();
				// 土地面積・下限
				if (!StringUtils.isEmpty(housingRequestForm.getLandAreaLowerHouse())) {

					String value = this.codeLookupManager.lookupValue("area", landArea);

					if (value.equals(housingRequestForm.getLandAreaLowerHouse())) {

						housingRequestForm.setLandAreaLowerHouse(landArea);
					}
				}

				// 土地面積・上限
				if (!StringUtils.isEmpty(housingRequestForm.getLandAreaUpperHouse())) {

					String value = this.codeLookupManager.lookupValue("area", landArea);

					if (value.equals(housingRequestForm.getLandAreaUpperHouse())) {

						housingRequestForm.setLandAreaUpperHouse(landArea);
					}
				}
			}

		}

		if ("03".equals(housingRequestForm.getHousingKindCd())) {
			// 予算
			Iterator<String> priceIte = this.codeLookupManager.getKeysByLookup("price");

			while (priceIte.hasNext()) {

				String price = priceIte.next();
				// 予算・下限
				if (!StringUtils.isEmpty(housingRequestForm.getPriceLowerLand())) {

					String value = this.codeLookupManager.lookupValue("price", price);

					if (value.equals(housingRequestForm.getPriceLowerLand())) {

						housingRequestForm.setPriceLowerLand(price);
					}
				}

				// 予算・上限
				if (!StringUtils.isEmpty(housingRequestForm.getPriceUpperLand())) {

					String value = this.codeLookupManager.lookupValue("price", price);

					if (value.equals(housingRequestForm.getPriceUpperLand())) {

						housingRequestForm.setPriceUpperLand(price);
					}
				}
			}

			// 建物面積
			Iterator<String> buildingAreaIte = this.codeLookupManager.getKeysByLookup("area");

			while (buildingAreaIte.hasNext()) {

				String buildingArea = buildingAreaIte.next();
				// 建物面積・下限
				if (!StringUtils.isEmpty(housingRequestForm.getBuildingAreaLowerLand())) {

					String value = this.codeLookupManager.lookupValue("area", buildingArea);

					if (value.equals(housingRequestForm.getBuildingAreaLowerLand())) {

						housingRequestForm.setBuildingAreaLowerLand(buildingArea);
					}
				}

				// 建物面積・上限
				if (!StringUtils.isEmpty(housingRequestForm.getBuildingAreaUpperLand())) {

					String value = this.codeLookupManager.lookupValue("area", buildingArea);

					if (value.equals(housingRequestForm.getBuildingAreaUpperLand())) {

						housingRequestForm.setBuildingAreaUpperLand(buildingArea);
					}
				}
			}

			// 土地面積
			Iterator<String> landAreaIte = this.codeLookupManager.getKeysByLookup("area");

			while (landAreaIte.hasNext()) {

				String landArea = landAreaIte.next();
				// 土地面積・下限
				if (!StringUtils.isEmpty(housingRequestForm.getLandAreaLowerLand())) {

					String value = this.codeLookupManager.lookupValue("area", landArea);

					if (value.equals(housingRequestForm.getLandAreaLowerLand())) {

						housingRequestForm.setLandAreaLowerLand(landArea);
					}
				}

				// 土地面積・上限
				if (!StringUtils.isEmpty(housingRequestForm.getLandAreaUpperLand())) {

					String value = this.codeLookupManager.lookupValue("area", landArea);

					if (value.equals(housingRequestForm.getLandAreaUpperLand())) {

						housingRequestForm.setLandAreaUpperLand(landArea);
					}
				}
			}
		}
	}

}
