package jp.co.transcosmos.dm3.webFront.housingListMation.command;

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
import jp.co.transcosmos.dm3.core.model.building.Building;
import jp.co.transcosmos.dm3.core.model.building.BuildingPartThumbnailProxy;
import jp.co.transcosmos.dm3.core.model.housing.Housing;
import jp.co.transcosmos.dm3.core.model.mypage.MypageUserInterface;
import jp.co.transcosmos.dm3.core.util.FrontLoginUserUtils;
import jp.co.transcosmos.dm3.core.vo.BuildingDtlInfo;
import jp.co.transcosmos.dm3.core.vo.BuildingInfo;
import jp.co.transcosmos.dm3.core.vo.BuildingStationInfo;
import jp.co.transcosmos.dm3.core.vo.HousingDtlInfo;
import jp.co.transcosmos.dm3.core.vo.PartSrchMst;
import jp.co.transcosmos.dm3.core.vo.RouteMst;
import jp.co.transcosmos.dm3.core.vo.RrMst;
import jp.co.transcosmos.dm3.core.vo.StationMst;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonConstant;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonParameters;
import jp.co.transcosmos.dm3.corePana.model.PanaCommonManage;
import jp.co.transcosmos.dm3.corePana.model.ReformManage;
import jp.co.transcosmos.dm3.corePana.model.housing.PanaHousingPartThumbnailProxy;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingFormFactory;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingSearchForm;
import jp.co.transcosmos.dm3.corePana.util.PanaCalcUtil;
import jp.co.transcosmos.dm3.corePana.util.PanaFileUtil;
import jp.co.transcosmos.dm3.corePana.util.PanaStringUtils;
import jp.co.transcosmos.dm3.corePana.vo.HousingInfo;
import jp.co.transcosmos.dm3.corePana.vo.ReformPlan;
import jp.co.transcosmos.dm3.dao.JoinResult;
import jp.co.transcosmos.dm3.form.FormPopulator;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;

/**
 * 物件一覧画面
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
public class HousingListMatinoInitCommand implements Command {

	/** ディフォルト順（ソート条件） */
	public static final String SORT_DEFAULT = "4";

	/** リフォーム情報メンテナンスを行う Model オブジェクト */
	private ReformManage reformManager;

	/** １ページの表示件数 */
	private int rowsPerPage = 15;

	/** ファイル処理クラス */
	private PanaFileUtil panaFileUtil;

	/** 物件種類 */
	private String housingKindCd = "";

	/** 物件情報用 Model オブジェクト */
	private PanaHousingPartThumbnailProxy panaHousingManager;

	/** 共通 Model オブジェクト */
	private PanaCommonManage panaCommonManage;

    /** 共通パラメータオブジェクト */
    private PanaCommonParameters commonParameters;

	/** 共通コードオブジェクト */
	protected CodeLookupManager codeLookupManager;

	/** 建物情報用 Model オブジェクト */
	private BuildingPartThumbnailProxy buildingManager;

	/**一覧画面の表示ページ数 */
	private int visibleNavigationPageCount = 5;

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
     * 共通パラメータオブジェクトを設定する。<br/>
     * <br/>
     * @param commonParameters 共通パラメータオブジェクト
     */
    public void setCommonParameters(PanaCommonParameters commonParameters) {
        this.commonParameters = commonParameters;
    }

    /**
     * 建物情報用オブジェクトを設定する。<br/>
     * <br/>
     * @param buildingManager 建物情報用オブジェクト
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
	 * 物件情報用 Model オブジェクトを設定する。<br/>
	 * <br/>
	 *
	 * @param panaHousingManager
	 *            物件情報用 Model オブジェクト
	 */
	public void setPanaHousingManager(
			PanaHousingPartThumbnailProxy panaHousingManager) {
		this.panaHousingManager = panaHousingManager;
	}

    /**
     * 共通 Modelオブジェクトを設定する。<br/>
     * <br/>
     * @param PanaCommonManage 共通 Modelオブジェクト
     */
	public void setPanaCommonManage(PanaCommonManage panaCommonManager) {
		this.panaCommonManage = panaCommonManager;
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
	 * 物件種類を設定する。<br>
	 *
	 * @param housingKindCd
	 *            物件種類
	 */
	public void setHousingKindCd(String housingKindCd) {
		this.housingKindCd = housingKindCd;
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
		PanaHousingSearchForm housingListMationForm = factory
				.createPanaHousingSearchForm();

		FormPopulator.populateFormBeanFromRequest(request,
				housingListMationForm);

		housingListMationForm.setVisibleNavigationPageCount(this.visibleNavigationPageCount);

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

		String clearFlg = housingListMationForm.getClearFlg();

		// URLから、検索条件を取得する。
		String prefCd = request.getParameter("prefCd");

		if(StringUtils.isEmpty(prefCd)){
			throw new RuntimeException("都道府県CDが指定されていません.");
		}

		model.put("prefCd", prefCd);

		String addressCd = request.getParameter("addressCd");
		String routeCd = request.getParameter("routeCd");
		String stationCd = request.getParameter("stationCd");

		// URL中で、取得の条件をフォームに設定する。
		housingListMationForm.setRowsPerPage(this.rowsPerPage);
		housingListMationForm.setKeyHiddenFlg(PanaCommonConstant.HIDDEN_FLG_PUBLIC);
		housingListMationForm.setKeyPrefCd(prefCd);
		housingListMationForm.setHousingKindCd(this.housingKindCd);
		housingListMationForm.setKeyHousingKindCd(this.housingKindCd);
		housingListMationForm.setKeyAddressCd(addressCd);
		housingListMationForm.setKeyRouteCd(routeCd);
		housingListMationForm.setKeyStationCd(stationCd);

		// 戸建とマンションを遷移時、検索条件をクリア。
		if ("1".equals(clearFlg)) {

			housingListMationForm = factory
					.createPanaHousingSearchForm();

			housingListMationForm.setKeyHousingKindCd(this.housingKindCd);
			housingListMationForm.setHousingKindCd(this.housingKindCd);
			housingListMationForm.setKeyPrefCd(prefCd);

		}

		// ディフォルトのソート順を設定する。
		if (StringUtils.isEmpty(housingListMationForm
				.getSortUpdDateValue())) {
			housingListMationForm.setSortUpdDateValue("3");
			housingListMationForm.setSortPriceValue("1");
			housingListMationForm.setSortBuildDateValue("6");
			housingListMationForm.setSortWalkTimeValue("7");

			housingListMationForm.setKeyOrderType(SORT_DEFAULT);
		}

		// 予算
		Iterator<String> priceIte = this.codeLookupManager
				.getKeysByLookup("price");

		while (priceIte.hasNext()) {

			String price = priceIte.next();
			if (price.equals(housingListMationForm.getPriceLower())) {
				// 賃料/価格・下限（検索条件）
				if (!StringUtils.isEmpty(housingListMationForm
						.getPriceLower())) {

					String temp = this.codeLookupManager.lookupValue(
							"price", price);
					housingListMationForm.setKeyPriceLower(Long.valueOf(temp + "0000"));

				}
			}

			if (price.equals(housingListMationForm.getPriceUpper())) {
				// 賃料/価格・上限（検索条件）
				if (!StringUtils.isEmpty(housingListMationForm
						.getPriceUpper())) {

					String temp = this.codeLookupManager.lookupValue(
							"price", price);

					housingListMationForm.setKeyPriceUpper(Long
							.valueOf(temp + "0000"));

				}
			}
		}

		// 専有面積
		Iterator<String> personalAreaIte = this.codeLookupManager
				.getKeysByLookup("area");

		while (personalAreaIte.hasNext()) {

			String personalArea = personalAreaIte.next();
			if (personalArea.equals(housingListMationForm
					.getPersonalAreaLower())) {
				// 専有面積・下限（検索条件）
				if (!StringUtils.isEmpty(housingListMationForm
						.getPersonalAreaLower())) {

					String temp = this.codeLookupManager.lookupValue(
							"area", personalArea);
					housingListMationForm.setKeyPersonalAreaLower(BigDecimal
							.valueOf(Double.valueOf(temp)));

				}
			}

			if (personalArea.equals(housingListMationForm
					.getPersonalAreaUpper())) {
				// 専有面積・下限（検索条件）
				if (!StringUtils.isEmpty(housingListMationForm
						.getPersonalAreaUpper())) {

					String temp = this.codeLookupManager.lookupValue(
							"area", personalArea);
					housingListMationForm.setKeyPersonalAreaUpper(BigDecimal
							.valueOf(Double.valueOf(temp)));

				}
			}

		}

		// 建物面積
		Iterator<String> buildingAreaIte = this.codeLookupManager
				.getKeysByLookup("area");

		while (buildingAreaIte.hasNext()) {

			String buildingArea = buildingAreaIte.next();
			if (buildingArea.equals(housingListMationForm
					.getBuildingAreaLower())) {
				// 建物面積・下限（検索条件）
				if (!StringUtils.isEmpty(housingListMationForm
						.getBuildingAreaLower())) {

					String temp = this.codeLookupManager.lookupValue(
							"area", buildingArea);
					housingListMationForm.setKeyBuildingAreaLower(BigDecimal
							.valueOf(Double.valueOf(temp)));

				}
			}

			if (buildingArea.equals(housingListMationForm
					.getBuildingAreaUpper())) {
				// 建物面積・下限（検索条件）
				if (!StringUtils.isEmpty(housingListMationForm
						.getBuildingAreaUpper())) {

					String temp = this.codeLookupManager.lookupValue(
							"area", buildingArea);
					housingListMationForm.setKeyBuildingAreaUpper(BigDecimal
							.valueOf(Double.valueOf(temp)));

				}
			}

		}

		// 土地面積
		Iterator<String> landAreaIte = this.codeLookupManager
				.getKeysByLookup("area");

		while (landAreaIte.hasNext()) {

			String landArea = landAreaIte.next();
			if (landArea.equals(housingListMationForm.getLandAreaLower())) {
				// 建物面積・下限（検索条件）
				if (!StringUtils.isEmpty(housingListMationForm
						.getLandAreaLower())) {
					String temp = this.codeLookupManager.lookupValue(
							"area", landArea);
					housingListMationForm.setKeyLandAreaLower(BigDecimal
							.valueOf(Double.valueOf(temp)));
				}
			}

			if (landArea.equals(housingListMationForm.getLandAreaUpper())) {
				// 建物面積・下限（検索条件）
				if (!StringUtils.isEmpty(housingListMationForm
						.getLandAreaUpper())) {

					String temp = this.codeLookupManager.lookupValue(
							"area", landArea);
					housingListMationForm.setKeyLandAreaUpper(BigDecimal
							.valueOf(Double.valueOf(temp)));

				}
			}

		}

		// 間取り
		StringBuffer layoutCd = new StringBuffer();
		if (housingListMationForm.getLayoutCd() != null) {
			for (int i = 0; i < housingListMationForm.getLayoutCd().length; i++) {
				layoutCd.append(housingListMationForm.getLayoutCd()[i] + ",");
			}
			housingListMationForm.setKeyLayoutCd(layoutCd.toString());
		}

		// おすすめのポイント
		StringBuffer iconCd = new StringBuffer();
		if (housingListMationForm.getIconCd() != null) {
			for (int i = 0; i < housingListMationForm.getIconCd().length; i++) {
				iconCd.append(housingListMationForm.getIconCd()[i] + ",");
			}
			housingListMationForm.setKeyIconCd(iconCd.toString());
		}

		// 物件種類をモデルに設定する。
		model.put("housingKindCd", housingListMationForm.getHousingKindCd());

		// 都道府県マスタの取得
		String prefName = this.panaCommonManage.getPrefName(prefCd);

		// 都道府県名をモデルに設定する。
		model.put("prefName", prefName);

		// キーワードと説明の値を設定する。
		String keyWrods = "";
		String description = "";
		if(PanaCommonConstant.HOUSING_KIND_CD_MANSION.equals(housingKindCd)){
			keyWrods = "パナソニック," + this.commonParameters.getPanasonicSiteEnglish() + "," + this.commonParameters.getPanasonicSiteJapan() + ",Re2,リー・スクエア," + prefName + ",中古マンション,中古マンション購入,リフォーム";
			description = prefName + "の中古マンション購入はパナソニックの" + this.commonParameters.getPanasonicSiteEnglish() + "(" + this.commonParameters.getPanasonicSiteJapan() + ")へご相談下さい。リフォームプランと中古マンション紹介をワンストップでご提案いたします。もちろんご希望にあわせたリフォームもご相談をお受けします。";
		}

		if(PanaCommonConstant.HOUSING_KIND_CD_HOUSE.equals(housingKindCd)){
			keyWrods = "パナソニック," + this.commonParameters.getPanasonicSiteEnglish() + "," + this.commonParameters.getPanasonicSiteJapan() + ",Re2,リー・スクエア," + prefName + ",中古戸建,中古一戸建て,中古戸建購入,リフォーム";
			description = prefName + "の中古戸建購入はパナソニックの" + this.commonParameters.getPanasonicSiteEnglish() + "(" + this.commonParameters.getPanasonicSiteJapan() + ")へご相談下さい。リフォームプランと中古戸建紹介をワンストップでご提案いたします。もちろんご希望にあわせたリフォームもご相談をお受けします。";
		}

		if(PanaCommonConstant.HOUSING_KIND_CD_GROUND.equals(housingKindCd)){
			keyWrods = "パナソニック," + this.commonParameters.getPanasonicSiteEnglish() + "," + this.commonParameters.getPanasonicSiteJapan() + ",Re2,リー・スクエア," + prefName + ",土地,土地購入";
			description = prefName + "の土地購入はパナソニックの" + this.commonParameters.getPanasonicSiteEnglish() + "(" + this.commonParameters.getPanasonicSiteJapan() + ")へご相談下さい。パナホームの新築住宅とあわせてご提案もいたします。";
		}

		housingListMationForm.setKeywords(keyWrods);
		housingListMationForm.setDescription(description);
		// 都道府県CDにより、物件情報の取得
		int listSize = this.panaHousingManager
				.searchHousing(housingListMationForm);

		// 建物基本情報
		List<BuildingInfo> buildingList = new ArrayList<BuildingInfo>();

		// 建物詳細情報
		List<BuildingDtlInfo> buildingDtlList = new ArrayList<BuildingDtlInfo>();

		// 物件基本情報
		List<HousingInfo> housingList = new ArrayList<HousingInfo>();

		// 物件詳細情報
		List<HousingDtlInfo> housingDtlList = new ArrayList<HousingDtlInfo>();

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

		List<String> dateList = new ArrayList<String>();

		// 毎ページの表示データを設定する。
		int selectedPage = housingListMationForm.getSelectedPage();

		int pageListSize = 0;

		if (listSize - ((selectedPage - 1) * this.rowsPerPage) < this.rowsPerPage) {
			pageListSize = listSize - ((selectedPage - 1) * this.rowsPerPage);
		} else {
			pageListSize = this.rowsPerPage;
		}

		for (int count = 0; count < pageListSize; count++) {

			// システム物件CDを取得する。
			String sysHousingCd = ((HousingInfo) housingListMationForm
					.getVisibleRows().get(count).getHousingInfo().getItems()
					.get("housingInfo")).getSysHousingCd();

			// システム建物CDを取得する。
			String sysBuildingCd = ((BuildingInfo) housingListMationForm
					.getVisibleRows().get(count).getBuilding()
					.getBuildingInfo().getItems().get("buildingInfo"))
					.getSysBuildingCd();

			// 最終更新日を取得する。
			Date updDate = ((HousingInfo) housingListMationForm
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
			BuildingInfo buildingInfo = (BuildingInfo) housingListMationForm
					.getVisibleRows().get(count).getBuilding()
					.getBuildingInfo().getItems().get("buildingInfo");

			// 物件基本情報
			HousingInfo housingInfo = (HousingInfo) housingListMationForm
					.getVisibleRows().get(count).getHousingInfo().getItems()
					.get("housingInfo");

			// 建物詳細情報
			BuildingDtlInfo buildingDtlInfo = (BuildingDtlInfo) housingListMationForm
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

			// 物件詳細情報
			HousingDtlInfo housingDtlInfo = (HousingDtlInfo) housingListMationForm
					.getVisibleRows().get(count).getHousingInfo().getItems()
					.get("housingDtlInfo");

			if(!StringUtils.isEmpty(housingInfo.getReformComment())){
				housingInfo.setReformComment(PanaStringUtils.encodeHtml(housingInfo.getReformComment().trim()));
			}

			if(!StringUtils.isEmpty(housingInfo.getBasicComment())){
				housingInfo.setBasicComment(PanaStringUtils.encodeHtml(housingInfo.getBasicComment().trim()));
			}

			if(!StringUtils.isEmpty(housingDtlInfo.getDtlComment())){
				housingDtlInfo.setDtlComment(PanaStringUtils.encodeHtml(housingDtlInfo.getDtlComment().trim()));
			}

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
			List<ReformPlan> searchReformPlan = this.reformManager
					.searchReformPlan(sysHousingCd);
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

		// 建物詳細情報
		model.put("buildingDtlList", buildingDtlList);

		// 物件基本情報
		model.put("housingList", housingList);

		// 物件詳細情報
		model.put("housingDtlList", housingDtlList);

		// 物件情報件数
		model.put("housingDtlListSize", listSize);

		model.put("housingListMationForm", housingListMationForm);

		// こだわり条件マスタの読込、すべてのデータを取得する。
		List<JoinResult> searchPartSrchMstList = this.panaHousingManager
				.searchPartSrchMst(housingListMationForm.getHousingKindCd());
		List<PartSrchMst> searchPartSrchMst = new ArrayList<PartSrchMst>();
		for (int i = 0; i < searchPartSrchMstList.size(); i++) {
			PartSrchMst searchPartSrchMstInfo = (PartSrchMst) searchPartSrchMstList
					.get(i).getItems().get("partSrchMst");
			searchPartSrchMst.add(searchPartSrchMstInfo);
		}
		model.put("searchPartSrchMst", searchPartSrchMst);

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
