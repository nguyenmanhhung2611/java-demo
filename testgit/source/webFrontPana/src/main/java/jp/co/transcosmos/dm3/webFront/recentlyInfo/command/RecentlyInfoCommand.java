package jp.co.transcosmos.dm3.webFront.recentlyInfo.command;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.util.FrontLoginUserUtils;
import jp.co.transcosmos.dm3.core.util.RecentlyCookieUtils;
import jp.co.transcosmos.dm3.core.vo.BuildingDtlInfo;
import jp.co.transcosmos.dm3.core.vo.BuildingInfo;
import jp.co.transcosmos.dm3.core.vo.BuildingStationInfo;
import jp.co.transcosmos.dm3.core.vo.HousingImageInfo;
import jp.co.transcosmos.dm3.core.vo.PrefMst;
import jp.co.transcosmos.dm3.core.vo.RouteMst;
import jp.co.transcosmos.dm3.core.vo.RrMst;
import jp.co.transcosmos.dm3.core.vo.StationMst;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonConstant;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonParameters;
import jp.co.transcosmos.dm3.corePana.model.RecentlyInfoManage;
import jp.co.transcosmos.dm3.corePana.model.housing.PanaHousing;
import jp.co.transcosmos.dm3.corePana.util.PanaCalcUtil;
import jp.co.transcosmos.dm3.corePana.util.PanaFileUtil;
import jp.co.transcosmos.dm3.corePana.vo.HousingInfo;
import jp.co.transcosmos.dm3.dao.DAO;
import jp.co.transcosmos.dm3.dao.DAOCriteria;
import jp.co.transcosmos.dm3.dao.JoinResult;
import jp.co.transcosmos.dm3.login.LoginUser;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;

import org.springframework.web.servlet.ModelAndView;

/**
 * <pre>
 * 最近見た物件一覧
 * 最近見た物件一覧画面を表示する。
 *
 * 【復帰する View 名】
 *    ・"success" : 正常終了
 *
 * 担当者       修正日     修正内容
 * ------------ ----------- -----------------------------------------------------
 * tang.tianyun 2015.05.07  新規作成
 *
 * 注意事項
 *
 * </pre>
 */
public class RecentlyInfoCommand implements Command {

    /** 共通パラメータオブジェクト */
    private PanaCommonParameters commonParameters;

	/** 最近見た物件情報を管理する Model オブジェクト */
	private RecentlyInfoManage recentlyInfoManage;

	/** 物件画像情報DAO */
	private DAO<HousingImageInfo> HousingImageInfoDAO;

	/** Panasonic用ファイル処理関連共通Util */
	private PanaFileUtil fileUtil;

    /**
     * 共通パラメータオブジェクトを設定する。<br/>
     * <br/>
     * @param commonParameters 共通パラメータオブジェクト
     */
    public void setCommonParameters(PanaCommonParameters commonParameters) {
        this.commonParameters = commonParameters;
    }

	/**
	 * 最近見た物件情報用 Model オブジェクトを設定する。<br/>
	 * <br/>
	 * @param recentlyInfoManage 最近見た物件情報用 Model オブジェクト
	 */
	public void setRecentlyInfoManage(RecentlyInfoManage recentlyInfoManage) {
		this.recentlyInfoManage = recentlyInfoManage;
	}

	/**
	 * 物件画像情報DAOを設定する。<br/>
	 * <br/>
	 * @param housingImageInfoDAO 物件画像情報DAO
	 */
	public void setHousingImageInfoDAO(DAO<HousingImageInfo> housingImageInfoDAO) {
		HousingImageInfoDAO = housingImageInfoDAO;
	}

	/**
	 *Panasonic用ファイル処理関連共通Utilを設定する。<br/>
	 * <br/>
	 * @param fileUtil Panasonic用ファイル処理関連共通Util
	 */
	public void setFileUtil(PanaFileUtil fileUtil) {
		this.fileUtil = fileUtil;
	}


	/**
	 * 最近見た物件一覧画面表示処理<br>
	 * <br>
	 *
	 * @param request
	 *            HTTP リクエスト
	 * @param response
	 *            HTTP レスポンス
	 */
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {

		Map<String, Object> model = new HashMap<String, Object>();

		// ソート順を設定する。
		String keyOrderType = request.getParameter("keyOrderType");
		String sortOrder1Label = request.getParameter("sortOrder1Label");
		String sortOrder2Label = request.getParameter("sortOrder2Label");
		String sortOrder3Label = request.getParameter("sortOrder3Label");
		String sortOrder4Label = request.getParameter("sortOrder4Label");
		String orderBy = "";
		boolean ascending = true;

		if ("1".equals(keyOrderType)) {
			orderBy = "1";
			ascending = true;
		} else if (StringValidateUtil.isEmpty(keyOrderType) || "2".equals(keyOrderType)){
			orderBy = "1";
			ascending = false;
		}else if ("3".equals(keyOrderType)) {
			orderBy = "2";
			ascending = true;
		} else if ("4".equals(keyOrderType)) {
			orderBy = "2";
			ascending = false;
		} else if ("5".equals(keyOrderType)) {
			orderBy = "3";
			ascending = false;
		} else if ("6".equals(keyOrderType)) {
			orderBy = "3";
			ascending = true;
		} else if ("7".equals(keyOrderType)) {
			orderBy = "4";
			ascending = true;
		} else if ("8".equals(keyOrderType)) {
			orderBy = "4";
			ascending = false;
		}

		// ログインユーザーの情報を取得
		LoginUser loginUser = FrontLoginUserUtils.getInstance(request).getMypageLoginUserInfo(request, response);

		// ログインFlg を設定
		String loginFlg;
		if (loginUser != null) {
			loginFlg = "0";
			model.put("loginFlg", loginFlg);
		// loginUserがNULLの場合、非ログイン状態となる。
		} else {
			loginUser = FrontLoginUserUtils.getInstance(request).getAnonLoginUserInfo(request, response);
			loginFlg = "1";
			model.put("loginFlg", loginFlg);
		}
		// ユーザーIDを取得
		String userId = (String) loginUser.getUserId();

		// 最近見た物件情報を削除
		this.recentlyInfoManage.delRecentlyInfo(userId);

		// 最近見た物件一覧検索
		List<PanaHousing> recentlyInfoList = this.recentlyInfoManage.getRecentlyInfoListMap(userId, orderBy, ascending);

		// 1週間以内計算情報
		List<String> dateList = new ArrayList<String>();
		// 建物基本情報
		List<BuildingInfo> buildingList = new ArrayList<BuildingInfo>();
		// 物件基本情報
		List<HousingInfo> housingList = new ArrayList<HousingInfo>();
		// 物件画像情報リスト
		List<String> searchHousingImgList = new ArrayList<String>();
		// 所在地リスト
		List<String> addressList = new ArrayList<String>();
		// アクセスリスト
		List<List<String>> nearStationLists = new ArrayList<List<String>>();
		// 専有面積リスト
		List<String> personalAreaList = new ArrayList<String>();
		// 専有面積 坪リスト
		List<String> personalAreaSquareList = new ArrayList<String>();
		// 建物面積リスト
		List<String> buildingAreaList = new ArrayList<String>();
		// 建物面積 坪リスト
		List<String> buildingAreaSquareList = new ArrayList<String>();
		// 土地面積リスト
		List<String> landAreaList = new ArrayList<String>();
		// 土地面積 坪リスト
		List<String> landAreaSquareList = new ArrayList<String>();
		// 築年月リスト
		List<String> compDateList = new ArrayList<String>();
		// 階建、所在階リスト
		List<String> totalFloorList = new ArrayList<String>();
		List<String> floorNoList = new ArrayList<String>();

		if (recentlyInfoList != null && recentlyInfoList.size() > 0) {

			// 最近見た物件一覧画面表示件数
			int cnt = this.commonParameters.getMaxRecentlyInfoCnt();
			int i = 0;

			for (PanaHousing housing : recentlyInfoList) {

				if (housing != null) {

					// 物件基本情報を取得する。
					HousingInfo housingInfo = (HousingInfo) housing.getHousingInfo().getItems().get("housingInfo");
					// 建物基本情報を取得する。
					BuildingInfo buildingInfo = (BuildingInfo) housing.getBuilding().getBuildingInfo().getItems().get("buildingInfo");
					// 建物最寄り駅情報を取得する。
					List<JoinResult> buildingStationInfoList = housing.getBuilding().getBuildingStationInfoList();
					// 建物詳細情報を取得
					BuildingDtlInfo buildingDtlInfo = (BuildingDtlInfo)housing.getBuilding().getBuildingInfo().getItems().get("buildingDtlInfo");
					// 都道府県マスタを取得
					PrefMst prefMst = (PrefMst)housing.getBuilding().getBuildingInfo().getItems().get("prefMst");


					// 1週間以内（システム日付 - 最終更新日<= 7日）に追加された物件に対して新着アイコンを出す。
					// 最終更新日を取得する。
					Date updDate = housingInfo.getUpdDate();
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


					// 物件画像情報を設定する。
					String pathName = "";
					// 閲覧権限があり、且つ、表示順、画像タイプ、枝番で昇順ソート後の1番目の画像を表示
					DAOCriteria criteria = new DAOCriteria();
					criteria.addWhereClause("sysHousingCd", housingInfo.getSysHousingCd());
					criteria.addWhereClause("roleId", null, DAOCriteria.IS_NULL, true);
					if(loginFlg == "1"){
						criteria.addWhereClause("roleId", PanaCommonConstant.ROLE_ID_PUBLIC);
					}
					criteria.addOrderByClause("sortOrder");
					criteria.addOrderByClause("imageType");
					criteria.addOrderByClause("divNo");
					List<HousingImageInfo> housingImageInfoList = this.HousingImageInfoDAO.selectByFilter(criteria);
					if (housingImageInfoList.size() > 0) {
						jp.co.transcosmos.dm3.corePana.vo.HousingImageInfo housingImageInfo = (jp.co.transcosmos.dm3.corePana.vo.HousingImageInfo)housingImageInfoList.get(0);
						if (PanaCommonConstant.ROLE_ID_PUBLIC.equals(housingImageInfo.getRoleId())) {
							// 閲覧権限が全員の場合
							pathName = this.fileUtil.getHousFileOpenUrl(housingImageInfo.getPathName(), housingImageInfo.getFileName(), this.commonParameters.getHousingListImageSize());
						} else if (PanaCommonConstant.ROLE_ID_PRIVATE.equals(housingImageInfo.getRoleId())) {
							// 閲覧権限が会員のみの場合
							pathName = this.fileUtil.getHousFileMemberUrl(housingImageInfo.getPathName(), housingImageInfo.getFileName(), this.commonParameters.getHousingListImageSize());
						}
					}
					searchHousingImgList.add(pathName);


					// 所在地を設定する。
					StringBuffer address = new StringBuffer();
					if (!StringValidateUtil.isEmpty(buildingInfo.getPrefCd())) {
						if (prefMst.getPrefName() != null) {
							address.append(prefMst.getPrefName()).append(" ");
							address.append(buildingInfo.getAddressName() == null?"":buildingInfo.getAddressName()).append(" ");
							address.append(buildingInfo.getAddressOther1() == null?"":buildingInfo.getAddressOther1()).append(" ");
							address.append(buildingInfo.getAddressOther2() == null?"":buildingInfo.getAddressOther2());
						}
					}
					// 所在地リスト
					addressList.add(address.toString());


					// アクセスを設定する。
					// 建物最寄り駅情報Listを取得
					 BuildingStationInfo buildingStationInfo = new BuildingStationInfo();
		     		 RrMst rrMst = new RrMst();
					 RouteMst routeMst = new RouteMst();
					 StationMst stationMst = new StationMst();
					 StringBuffer nearStation = new StringBuffer();
					 List<String> nearStationList = new ArrayList<String>();
					 for (int j = 0; j < buildingStationInfoList.size(); j++) {

						 // 建物最寄り駅情報の取得
						 buildingStationInfo = new BuildingStationInfo();
						 buildingStationInfo =  (BuildingStationInfo)buildingStationInfoList.get(j).getItems().get("buildingStationInfo");
		    			 // 鉄道会社マスタの取得
		    			 rrMst = new RrMst();
		    			 rrMst = (RrMst)buildingStationInfoList.get(j).getItems().get("rrMst");
						 // 路線マスタの取得
						 routeMst = new RouteMst();
						 routeMst = (RouteMst)buildingStationInfoList.get(j).getItems().get("routeMst");
						 // 駅マスタの取得
						 stationMst = new StationMst();
						 stationMst = (StationMst)buildingStationInfoList.get(j).getItems().get("stationMst");

						 // 代表路線名
						 nearStation = new StringBuffer();
		    			 if (!StringValidateUtil.isEmpty(rrMst.getRrName())) {
		    				 nearStation.append(rrMst.getRrName());
		    			 }
						 if (!StringValidateUtil.isEmpty(routeMst.getRouteName())) {
							 nearStation.append(routeMst.getRouteName()).append(" ");
						 } else {
							 if (!StringValidateUtil.isEmpty(buildingStationInfo.getDefaultRouteName())) {
								 nearStation.append(buildingStationInfo.getDefaultRouteName()).append(" ");
							 }
						 }
						 // 駅名
						 if (!StringValidateUtil.isEmpty(stationMst.getStationName())) {
							 nearStation.append(stationMst.getStationName()).append("駅 ");
						 } else {
							 if (!StringValidateUtil.isEmpty(buildingStationInfo.getStationName())) {
								 nearStation.append(buildingStationInfo.getStationName()).append("駅 ");
							 }
						 }
						 // バス会社名
						 if (!StringValidateUtil.isEmpty(buildingStationInfo.getBusCompany())) {
							 nearStation.append(buildingStationInfo.getBusCompany()).append(" ");
						 }
						 // バス停からの徒歩時間
						 if (buildingStationInfo.getTimeFromBusStop() != null) {
							 nearStation.append("徒歩").append(String.valueOf(buildingStationInfo.getTimeFromBusStop())).append("分");
						 }
						 nearStationList.add(nearStation.toString());
					 }
					// アクセスリスト
					nearStationLists.add(nearStationList);


					// 築年月
					String compDate = "";
					if (buildingInfo != null && buildingInfo.getCompDate() != null) {
						compDate = new SimpleDateFormat("yyyy年M月築").format(buildingInfo.getCompDate());
					}
					compDateList.add(compDate);


					// 物件種類CDを判断
					String personalArea = "";
					String personalAreaSquare = "";
					String buildingArea = "";
					String buildingAreaSquare = "";
					String landArea = "";
					String landAreaSquare = "";
					// 階建
					String totalFloor = "";
					// 所在階
					String floorNo = "";
					if (PanaCommonConstant.HOUSING_KIND_CD_MANSION.equals(buildingInfo.getHousingKindCd())) {
						if (housingInfo.getPersonalArea() != null) {
							// 専有面積
							personalArea = housingInfo.getPersonalArea().toString();
							// 専有面積 坪
							personalAreaSquare = ((housingInfo.getPersonalArea() == null) ? "" : "（約" + PanaCalcUtil.calcTsubo(housingInfo.getPersonalArea()).toString() + "坪）") +
										(housingInfo.getPersonalAreaMemo() == null? "" : housingInfo.getPersonalAreaMemo()) ;
						}
						if (buildingInfo != null) {
							// 階建
							totalFloor = (buildingInfo.getTotalFloors() == null) ? "" : buildingInfo.getTotalFloors() + "階建";
							// 所在階
							floorNo = (housingInfo.getFloorNo() == null) ? "" : housingInfo.getFloorNo() + "階";
						}
					} else {
						if (buildingDtlInfo != null && buildingDtlInfo.getBuildingArea() != null) {
							// 建物面積
							buildingArea = buildingDtlInfo.getBuildingArea().toString();
							// 建物面積　坪
							buildingAreaSquare = ((buildingDtlInfo.getBuildingArea() == null) ? "" : "（約" + PanaCalcUtil.calcTsubo(buildingDtlInfo.getBuildingArea()).toString() + "坪）") +
										(buildingDtlInfo.getBuildingAreaMemo() == null? "" : buildingDtlInfo.getBuildingAreaMemo()) ;
						}
						if (housingInfo.getLandArea() != null) {
							// 土地面積
							landArea = housingInfo.getLandArea().toString();
							// 土地面積　坪
							landAreaSquare = ((housingInfo.getLandArea() == null) ? "" : "（約" + PanaCalcUtil.calcTsubo(housingInfo.getLandArea()).toString() + "坪）") +
										(housingInfo.getLandAreaMemo() == null? "" : housingInfo.getLandAreaMemo()) ;
						}
					}
					// 専有面積
					personalAreaList.add(personalArea);
					// 専有面積 坪
					personalAreaSquareList.add(personalAreaSquare);
					// 階建／所在階
					totalFloorList.add(totalFloor);
					floorNoList.add(floorNo);
					// 建物面積
					buildingAreaList.add(buildingArea);
					// 建物面積 坪
					buildingAreaSquareList.add(buildingAreaSquare);
					// 土地面積
					landAreaList.add(landArea);
					// 土地面積 坪
					landAreaSquareList.add(landAreaSquare);
					// 建物基本情報
					buildingList.add(buildingInfo);
					// 物件基本情報
					housingList.add(housingInfo);

					i++;

					if (i >= cnt) {
						break;
					}
				}
			}
		}

		// 最近見た物件一覧リストSize
		model.put("recentlyInfoListSize", recentlyInfoList.size());
		// 最近見た物件一覧リストSize
		model.put("recentlyInfoList", recentlyInfoList);
		// 1週間以内計算情報
		model.put("dateList", dateList);
		// 物件画像情報
		model.put("searchHousingImgList", searchHousingImgList);
		// 所在地情報
		model.put("addressList", addressList);
		// アクセス情報
		model.put("nearStationLists", nearStationLists);
		// 築年月
		model.put("compDateList", compDateList);
		// 専有面積
		model.put("personalAreaList", personalAreaList);
		// 専有面積 坪
		model.put("personalAreaSquareList", personalAreaSquareList);
		// 建物面積
		model.put("buildingAreaList", buildingAreaList);
		// 建物面積 坪
		model.put("buildingAreaSquareList", buildingAreaSquareList);
		// 土地面積
		model.put("landAreaList", landAreaList);
		// 土地面積 坪
		model.put("landAreaSquareList", landAreaSquareList);
		// 階建、所在階
		model.put("totalFloorList", totalFloorList);
		model.put("floorNoList", floorNoList);
		// 建物基本情報
		model.put("buildingList", buildingList);
		// 物件基本情報
		model.put("housingList", housingList);
		// 並べ替え
		model.put("keyOrderType", keyOrderType);
		model.put("sortOrder1Label", sortOrder1Label == null? "最近見た日付 ▼" : sortOrder1Label);
		model.put("sortOrder2Label", sortOrder2Label == null? "物件価格 ▲" : sortOrder2Label);
		model.put("sortOrder3Label", sortOrder3Label == null? "築年数 ▲" : sortOrder3Label);
		model.put("sortOrder4Label", sortOrder4Label == null? "駅からの距離 ▲" : sortOrder4Label);

		// Cookieを設定する
		RecentlyCookieUtils.getInstance(request).setRecentlyCount(request, response, recentlyInfoList.size());
		request.setAttribute(RecentlyCookieUtils.HISTORY_COUNT_KEY, recentlyInfoList.size());

		return new ModelAndView("success", model);
	}
}
