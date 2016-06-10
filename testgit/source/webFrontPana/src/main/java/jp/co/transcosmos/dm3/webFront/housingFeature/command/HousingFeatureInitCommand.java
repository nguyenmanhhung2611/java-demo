package jp.co.transcosmos.dm3.webFront.housingFeature.command;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.model.FeatureManage;
import jp.co.transcosmos.dm3.core.model.building.Building;
import jp.co.transcosmos.dm3.core.model.building.BuildingPartThumbnailProxy;
import jp.co.transcosmos.dm3.core.model.housing.Housing;
import jp.co.transcosmos.dm3.core.model.mypage.MypageUserInterface;
import jp.co.transcosmos.dm3.core.util.FrontLoginUserUtils;
import jp.co.transcosmos.dm3.core.vo.BuildingDtlInfo;
import jp.co.transcosmos.dm3.core.vo.BuildingInfo;
import jp.co.transcosmos.dm3.core.vo.BuildingStationInfo;
import jp.co.transcosmos.dm3.core.vo.FeaturePageInfo;
import jp.co.transcosmos.dm3.core.vo.HousingDtlInfo;
import jp.co.transcosmos.dm3.core.vo.RouteMst;
import jp.co.transcosmos.dm3.core.vo.RrMst;
import jp.co.transcosmos.dm3.core.vo.StationMst;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonConstant;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonParameters;
import jp.co.transcosmos.dm3.corePana.model.PanaCommonManage;
import jp.co.transcosmos.dm3.corePana.model.ReformManage;
import jp.co.transcosmos.dm3.corePana.model.housing.PanaHousingPartThumbnailProxy;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaFeatureSearchForm;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingFormFactory;
import jp.co.transcosmos.dm3.corePana.util.PanaCalcUtil;
import jp.co.transcosmos.dm3.corePana.util.PanaFileUtil;
import jp.co.transcosmos.dm3.corePana.util.PanaStringUtils;
import jp.co.transcosmos.dm3.corePana.vo.HousingInfo;
import jp.co.transcosmos.dm3.corePana.vo.ReformPlan;
import jp.co.transcosmos.dm3.dao.DAO;
import jp.co.transcosmos.dm3.dao.JoinResult;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;

/**
 * 特集テンプレート画面
 *
 * <pre>
 * 【復帰する View 名】
 *    ・"success" : 正常終了
 *
 * 担当者       修正日      修正内容
 * ------------ ----------- -----------------------------------------------------
 *   焦		  2015.04.10    新規作成
 *
 * 注意事項
 *
 * </pre>
 */
public class HousingFeatureInitCommand implements Command {

	/** 特集ページ情報 */
	private DAO<FeaturePageInfo> featurePageInfoDAO;

	/** １ページの表示件数 */
	private int rowsPerPage = 2;

    /** 共通パラメータオブジェクト */
    private PanaCommonParameters commonParameters;

	/** 共通コード変換処理 */
	protected CodeLookupManager codeLookupManager;

	/** 共通情報取得する Model オブジェクト */
	private PanaCommonManage panaCommonManage;

	/** 特集の情報を管理する Model オブジェクト */
	private FeatureManage featureManage;

	/** 物件情報メンテナンス用 Model オブジェクト */
	private PanaHousingPartThumbnailProxy panaHousingManager;

	/** リフォーム情報メンテナンスを行う Model オブジェクト */
	private ReformManage reformManager;

	/** 共通パラメータオブジェクト */
	private BuildingPartThumbnailProxy buildingManager;

	/**一覧画面の表示ページ数 */
	private int visibleNavigationPageCount = 5;

	/** ファイル処理クラス */
	private PanaFileUtil panaFileUtil;

	/**
	 * ファイル処理クラスを設定する。<br>
	 *
	 * @param panaFileUtil ファイル処理クラス
	 */
	public void setPanaFileUtil(PanaFileUtil panaFileUtil) {
		this.panaFileUtil = panaFileUtil;
	}

	/**
	 * 一覧画面の表示ページ数を設定する。<br>
	 *
	 * @param rowsPerPage
	 *            一覧画面の表示ページ数
	 */
	public void setVisibleNavigationPageCount(int visibleNavigationPageCount) {
		this.visibleNavigationPageCount = visibleNavigationPageCount;
	}

    /**
     * 特集ページ情報を設定する。<br/>
     * <br/>
     * @param featurePageInfoDAO 特集ページ情報
     */
	public void setFeaturePageInfoDAO(DAO<FeaturePageInfo> featurePageInfoDAO) {
		this.featurePageInfoDAO = featurePageInfoDAO;
	}

    /**
     * 共通パラメータオブジェクトを設定する。<br/>
     * <br/>
     * @param commonParameters 共通パラメータオブジェクト
     */
    public void setCommonParameters(PanaCommonParameters commonParameters) {
        this.commonParameters = commonParameters;
    }

	/**
	 * 特集の情報を管理する Model オブジェクトを設定する。<br/>
	 * <br/>
	 *
	 * @param featureManage
	 *            特集の情報を管理する Model オブジェクト
	 */
	public void setFeatureManage(FeatureManage featureManage) {
		this.featureManage = featureManage;
	}

	/**
     * 共通パラメータオブジェクトを設定する。<br/>
     * <br/>
     * @param buildingManager 共通パラメータオブジェクト
     */
	public void setBuildingManager(BuildingPartThumbnailProxy buildingManager) {
		this.buildingManager = buildingManager;
	}

	/**
	 * リフォームプラン用 Model オブジェクトを設定する。<br/>
	 * <br/>
	 *
	 * @param reformManager
	 *            リフォームプラン用 Model オブジェクト
	 */
	public void setReformManager(ReformManage reformManager) {
		this.reformManager = reformManager;
	}

    /**
     * 共通情報取得する Modelを設定する。<br/>
     * <br/>
     * @param PanaCommonManage 共通情報取得する Model オブジェクト
     */
	public void setPanaCommonManage(PanaCommonManage panaCommonManager) {
		this.panaCommonManage = panaCommonManager;
	}

	/**
	 * 物件情報メンテナンス用 Model オブジェクトを設定する。<br/>
	 * <br/>
	 *
	 * @param panaHousingManager
	 *            物件情報メンテナンス用 Model オブジェクト
	 */
	public void setPanaHousingManager(
			PanaHousingPartThumbnailProxy panaHousingManager) {
		this.panaHousingManager = panaHousingManager;
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
	 * 共通コード変換オブジェクトを設定する。<br/>
	 * <br/>
	 *
	 * @param codeLookupManager
	 */
	public void setCodeLookupManager(CodeLookupManager codeLookupManager) {
		this.codeLookupManager = codeLookupManager;
	}

	/**
	 * 物件一覧画面表示処理<br>
	 * <br>
	 *
	 * @param request
	 *            HTTP リクエスト
	 * @param response
	 *            HTTP レスポンス
	 */
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Map<String, Object> model = new HashMap<String, Object>();

		PanaHousingFormFactory factory = PanaHousingFormFactory
				.getInstance(request);

		// ページ処理用のフォームオブジェクトを作成
		PanaFeatureSearchForm housingFeatureForm = factory
				.createFeatureSearchForm(request);

		// ログインユーザーの情報を取得
		MypageUserInterface loginUser = FrontLoginUserUtils.getInstance(request).getMypageLoginUserInfo(request, response);

		// ユーザIDを取得
		int loginFlg = 1;
		if (loginUser != null) {
			loginFlg = 0;
		}else{
			loginFlg = 1;
		}
		model.put("loginFlg", loginFlg);

		housingFeatureForm.setRowsPerPage(this.rowsPerPage);

		housingFeatureForm.setVisibleNavigationPageCount(this.visibleNavigationPageCount);

		model.put("housingFeatureForm", housingFeatureForm);

		// URLから、特集ページIDを取得する。
		String featurePageId = request.getParameter("featurePageId");

		if(StringUtils.isEmpty(featurePageId)){
			throw new RuntimeException("特集ページIDが指定されていません.");
		}
		// 特集ページIDを設定する。
		housingFeatureForm.setFeaturePageId(featurePageId);

		housingFeatureForm.setDefaultData();

		FeaturePageInfo featurePageInfo = featurePageInfoDAO.selectByPK(featurePageId);

		model.put("featurePageInfo", featurePageInfo);

		String description = "";
		Iterator<String> descriptionIte = this.codeLookupManager.getKeysByLookup("feature_description");

		while (descriptionIte.hasNext()) {

			String descriptionCd = descriptionIte.next();
			if(descriptionCd.equals(featurePageId)){
				description = this.codeLookupManager.lookupValue("feature_description", descriptionCd);
			}
		}
		model.put("description", description);

		// 特集に該当する物件の情報を取得する。
		int listSize = this.featureManage.searchHousing(housingFeatureForm);
		model.put("listSize", listSize);

		// 建物基本情報
		List<BuildingInfo> buildingList = new ArrayList<BuildingInfo>();

		// 物件基本情報
		List<HousingInfo> housingList = new ArrayList<HousingInfo>();

		// 物件詳細情報
		List<HousingDtlInfo> housingDtlList = new ArrayList<HousingDtlInfo>();

		// 建物詳細情報
		List<BuildingDtlInfo> buildingDtlList = new ArrayList<BuildingDtlInfo>();

		// 物件画像情報リスト
		List<List<String>> searchHousingImgList = new ArrayList<List<String>>();

		// 専有面積坪数
		List<BigDecimal> personalTubosuuList = new ArrayList<BigDecimal>();

		// 建物面積坪数
		List<BigDecimal> houseAreaList = new ArrayList<BigDecimal>();

		// 土地面積坪数
		List<BigDecimal> landAreaList = new ArrayList<BigDecimal>();

		// 築年月
		List<String> compDateList = new ArrayList<String>();

		// リフォームプラン
		List<List<ReformPlan>> reformPlanList = new ArrayList<List<ReformPlan>>();

		// 建物最寄り駅情報
		List<List<BuildingStationInfo>> buildingStationInfoList = new ArrayList<List<BuildingStationInfo>>();

		// 鉄道会社マスタ
		List<List<RrMst>> rrMstInfoList = new ArrayList<List<RrMst>>();

		// 路線マスタ
		List<List<RouteMst>> routeMstInfoList = new ArrayList<List<RouteMst>>();

		// 駅名マスタ
		List<List<StationMst>> stationMstInfoList = new ArrayList<List<StationMst>>();

		// おすすめのポイント
		List<List<String>> iconInfoList = new ArrayList<List<String>>();

		// 新着
		List<String> dateList = new ArrayList<String>();

		// 都道府県名
		List<String> prefNameList = new ArrayList<String>();

		// 毎ページの表示データを設定する。
		int selectedPage = housingFeatureForm.getSelectedPage();

		int pageListSize = 0;

		if (listSize - ((selectedPage - 1) * this.rowsPerPage) < this.rowsPerPage) {
			pageListSize = listSize - ((selectedPage - 1) * this.rowsPerPage);
		} else {
			pageListSize = this.rowsPerPage;
		}

		for (int count = 0; count < pageListSize; count++) {
			// システム物件CDを取得する。
			String sysHousingCd = ((HousingInfo) housingFeatureForm
					.getVisibleRows().get(count).getHousingInfo().getItems()
					.get("housingInfo")).getSysHousingCd();

			// システム建物CDを取得する。
			String sysBuildingCd = ((BuildingInfo) housingFeatureForm
					.getVisibleRows().get(count).getBuilding()
					.getBuildingInfo().getItems().get("buildingInfo"))
					.getSysBuildingCd();

			// 最終更新日を取得する。
			Date updDate = ((HousingInfo) housingFeatureForm
					.getVisibleRows().get(count).getHousingInfo().getItems()
					.get("housingInfo")).getUpdDate();

			// システム日期
			Date now = new Date();

			Calendar cal = Calendar.getInstance();

			cal.setTime(now);
			double nowTime = cal.getTimeInMillis();
			double updTime = 0;
			if (updDate != null) {
				cal.setTime(updDate);
				updTime = cal.getTimeInMillis();
			} else {
				cal.setTime(now);
				updTime = cal.getTimeInMillis();
			}

			// 1週間以内（システム日付 - 最終更新日<= 7日）計算
			double betweenDays = (nowTime - updTime) / (1000 * 3600 * 24);

			String dateFlg = "0";
			// 物件に対して新着アイコンを出す。
			if (betweenDays <= 7) {
				dateFlg = "1";
			}

			dateList.add(dateFlg);
			// 建物基本情報
			BuildingInfo buildingInfo = (BuildingInfo) housingFeatureForm
					.getVisibleRows().get(count).getBuilding()
					.getBuildingInfo().getItems().get("buildingInfo");

			// 物件基本情報
			HousingInfo housingInfo = (HousingInfo) housingFeatureForm
					.getVisibleRows().get(count).getHousingInfo().getItems()
					.get("housingInfo");

			// 物件詳細情報
			HousingDtlInfo housingDtlInfo = (HousingDtlInfo) housingFeatureForm
					.getVisibleRows().get(count).getHousingInfo().getItems()
					.get("housingDtlInfo");

			// 建物詳細情報
			BuildingDtlInfo buildingDtlInfo = (BuildingDtlInfo) housingFeatureForm
					.getVisibleRows().get(count).getBuilding().getBuildingInfo().getItems()
					.get("buildingDtlInfo");

			// おすすめのポイント
			List<String> iconInfo = new ArrayList<String>();
			if (!StringUtils.isEmpty(housingInfo.getIconCd())) {
				String[] iconCount = housingInfo.getIconCd().split(",");
				for (int i = 0; i < iconCount.length; i++) {
					iconInfo.add(iconCount[i]);
				}
			}

			// おすすめのポイント
			iconInfoList.add(iconInfo);

			// 専有面積坪数計算
			BigDecimal personalArea = housingInfo.getPersonalArea();

			if (personalArea != null || "".equals(personalArea)) {
				BigDecimal personalTubosuu = PanaCalcUtil
						.calcTsubo(personalArea);
				personalTubosuuList.add(personalTubosuu);
			} else {
				personalTubosuuList.add(null);
			}

			// 建物面積坪数計算
			BigDecimal houseArea = buildingDtlInfo.getBuildingArea();

			if (houseArea != null || "".equals(houseArea)) {
				BigDecimal houseAreaValue = PanaCalcUtil
						.calcTsubo(houseArea);
				houseAreaList.add(houseAreaValue);
			} else {
				houseAreaList.add(null);
			}

			// 土地面積坪数計算
			BigDecimal landArea = housingInfo.getLandArea();

			if (landArea != null || "".equals(landArea)) {
				BigDecimal landAreaValue = PanaCalcUtil
						.calcTsubo(landArea);
				landAreaList.add(landAreaValue);
			} else {
				landAreaList.add(null);
			}

			// 築年月
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			String compDate = null;
			if (buildingInfo.getCompDate() != null) {
				compDate = String
						.valueOf(sdf.format(buildingInfo.getCompDate()));
			}

			compDateList.add(compDate);

			if(!StringUtils.isEmpty(housingInfo.getReformComment())){
				housingInfo.setReformComment(PanaStringUtils.encodeHtml(housingInfo.getReformComment().trim()));
			}

			if(!StringUtils.isEmpty(housingInfo.getBasicComment())){
				housingInfo.setBasicComment(PanaStringUtils.encodeHtml(housingInfo.getBasicComment().trim()));
			}

			if(!StringUtils.isEmpty(housingDtlInfo.getDtlComment())){
				housingDtlInfo.setDtlComment(PanaStringUtils.encodeHtml(housingDtlInfo.getDtlComment().trim()));
			}

			// 都道府県名
			String prefName = this.panaCommonManage.getPrefName(buildingInfo.getPrefCd());
			prefNameList.add(prefName);

			// 建物基本情報
			buildingList.add(buildingInfo);
			// 物件基本情報
			housingList.add(housingInfo);
			// 物件詳細情報
			housingDtlList.add(housingDtlInfo);
			// 建物詳細情報
			buildingDtlList.add(buildingDtlInfo);


			// 「システム建物CD」により、建物最寄り駅情報
			Building buildingResultList = this.buildingManager.searchBuildingPk(sysBuildingCd);
			List<JoinResult> srchStation = (List<JoinResult>) buildingResultList.getBuildingStationInfoList();

			// 建物最寄り駅情報
			List<BuildingStationInfo> buildingStationInfo = new ArrayList<BuildingStationInfo>();

			// 鉄道会社マスタ
			List<RrMst> rrMstInfo = new ArrayList<RrMst>();

			// 路線マスタ
			List<RouteMst> routeMstInfo = new ArrayList<RouteMst>();

			// 駅名マスタ
			List<StationMst> stationMstInfo = new ArrayList<StationMst>();

			for (int i = 0; i < srchStation.size(); i++) {

				// 建物最寄り駅情報
				BuildingStationInfo buildingStation = (BuildingStationInfo) srchStation
						.get(i).getItems().get("buildingStationInfo");
				buildingStationInfo.add(buildingStation);

				// 鉄道会社マスタ
				RrMst rrMst = (RrMst) srchStation.get(i)
						.getItems().get("rrMst");
				rrMstInfo.add(rrMst);

				// 路線マスタ
				RouteMst routeMst = (RouteMst) srchStation.get(i).getItems()
						.get("routeMst");
				routeMstInfo.add(routeMst);

				// 駅名マスタ
				StationMst stationMst = (StationMst) srchStation.get(i)
						.getItems().get("stationMst");
				stationMstInfo.add(stationMst);

			}

			// 建物最寄り駅情報
			if (buildingStationInfo.size() > 0) {
				buildingStationInfoList.add(buildingStationInfo);
			} else {
				buildingStationInfoList.add(null);
			}

			// 鉄道会社マスタ
			if (rrMstInfo.size() > 0) {
				rrMstInfoList.add(rrMstInfo);
			} else {
				rrMstInfoList.add(null);
			}

			// 路線マスタ
			if (routeMstInfo.size() > 0) {
				routeMstInfoList.add(routeMstInfo);
			} else {
				routeMstInfoList.add(null);
			}

			// 駅名マスタ
			if (stationMstInfo.size() > 0) {
				stationMstInfoList.add(stationMstInfo);
			} else {
				stationMstInfoList.add(null);
			}

			// 「システム物件CD」により、リフォームプラン情報の取得
			List<ReformPlan> searchReformPlan = this.reformManager.searchReformPlan(sysHousingCd);
			if (searchReformPlan.size() > 0) {
				reformPlanList.add(searchReformPlan);
			} else {
				reformPlanList.add(null);
			}

			// 「システム物件CD」により、物件画像情報の取得
			Housing housingresults = this.panaHousingManager.searchHousingPk(
					sysHousingCd, true);

			List<jp.co.transcosmos.dm3.core.vo.HousingImageInfo> searchHousingImg = housingresults.getHousingImageInfos();

			List<String> searchHousingImgInfo = setUrlList(searchHousingImg,model, loginFlg);

			if (searchHousingImgInfo.size() > 0) {

				searchHousingImgList.add(searchHousingImgInfo);
			} else {
				searchHousingImgList.add(null);
			}
		}

		// 新着
		model.put("dateList", dateList);

		// おすすめのポイント
		model.put("iconInfoList", iconInfoList);

		// 物件画像情報
		model.put("searchHousingImgList", searchHousingImgList);

		// 建物最寄り駅情報
		model.put("buildingStationInfoList", buildingStationInfoList);

		// 鉄道会社マスタ
		model.put("rrMstInfoList", rrMstInfoList);

		// 路線マスタ
		model.put("routeMstInfoList", routeMstInfoList);

		// 駅名マスタ
		model.put("stationMstInfoList", stationMstInfoList);

		// リフォームプラン
		model.put("reformPlanList", reformPlanList);

		// 築年月
		model.put("compDateList", compDateList);

		// 専有面積坪数
		model.put("personalTubosuuList", personalTubosuuList);

		// 建物面積坪数
		model.put("houseAreaList", houseAreaList);

		// 土地面積坪数
		model.put("landAreaList", landAreaList);

		// 建物基本情報
		model.put("buildingList", buildingList);

		// 物件基本情報
		model.put("housingList", housingList);

		// 物件詳細情報
		model.put("housingDtlList", housingDtlList);

		// 建物詳細情報
		model.put("buildingDtlList", buildingDtlList);

		// 都道府県名
		model.put("prefNameList", prefNameList);

		// 取得したデータをレンダリング層へ渡す
		return new ModelAndView("success", model);
	}

	/**
     * 画像表示用を設定する処理。 <br>
     *
     * @param housingImageInfoList
     * @param model
     *
     * @return
     */
    private List<String> setUrlList(List<jp.co.transcosmos.dm3.core.vo.HousingImageInfo> housingImageInfoList,Map<String, Object> model, int loginFlg) {

    	List<String> urlInfoList = new ArrayList<String>();

		for (jp.co.transcosmos.dm3.core.vo.HousingImageInfo coreHi : housingImageInfoList) {
			jp.co.transcosmos.dm3.corePana.vo.HousingImageInfo hi = (jp.co.transcosmos.dm3.corePana.vo.HousingImageInfo) coreHi;

			String urlInfo = null;

			// 閲覧権限が会員のみの場合
			String pathName = hi.getPathName();
			String fileName = hi.getFileName();
			String size = this.commonParameters.getHousingListImageSize();

			if (loginFlg == 0) {
				// 閲覧権限が会員のみの場合
				if (PanaCommonConstant.ROLE_ID_PRIVATE.equals(hi.getRoleId())) {
					urlInfo = panaFileUtil.getHousFileMemberUrl(pathName, fileName, size);
					urlInfoList.add(urlInfo);
					break;
				} else {
					// 閲覧権限が全員の場合
					urlInfo = panaFileUtil.getHousFileOpenUrl(pathName, fileName, size);
					urlInfoList.add(urlInfo);
					break;
				}
			} else {
				// 閲覧権限が全員の場合
				if (!PanaCommonConstant.ROLE_ID_PRIVATE.equals(hi.getRoleId())) {
					urlInfo = panaFileUtil.getHousFileOpenUrl(pathName, fileName, size);
					urlInfoList.add(urlInfo);
					break;
				}
			}

		}

 		 // 物件画像情報リスト
 		 return urlInfoList;
    }

}
