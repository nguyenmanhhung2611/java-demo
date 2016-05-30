package jp.co.transcosmos.dm3.webFront.favorite.command;

import java.net.URLEncoder;
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
import jp.co.transcosmos.dm3.core.model.favorite.form.FavoriteFormFactory;
import jp.co.transcosmos.dm3.core.model.favorite.form.FavoriteSearchForm;
import jp.co.transcosmos.dm3.core.model.housing.Housing;
import jp.co.transcosmos.dm3.core.model.mypage.MypageUserInterface;
import jp.co.transcosmos.dm3.core.util.FavoriteCookieUtils;
import jp.co.transcosmos.dm3.core.util.FrontLoginUserUtils;
import jp.co.transcosmos.dm3.core.vo.BuildingDtlInfo;
import jp.co.transcosmos.dm3.core.vo.BuildingInfo;
import jp.co.transcosmos.dm3.core.vo.BuildingStationInfo;
import jp.co.transcosmos.dm3.core.vo.FavoriteInfo;
import jp.co.transcosmos.dm3.core.vo.HousingImageInfo;
import jp.co.transcosmos.dm3.core.vo.RouteMst;
import jp.co.transcosmos.dm3.core.vo.RrMst;
import jp.co.transcosmos.dm3.core.vo.StationMst;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonConstant;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonParameters;
import jp.co.transcosmos.dm3.corePana.model.PanaCommonManage;
import jp.co.transcosmos.dm3.corePana.model.favorite.PanaFavoriteManageImpl;
import jp.co.transcosmos.dm3.corePana.model.housing.PanaHousingPartThumbnailProxy;
import jp.co.transcosmos.dm3.corePana.util.PanaCalcUtil;
import jp.co.transcosmos.dm3.corePana.util.PanaFileUtil;
import jp.co.transcosmos.dm3.corePana.vo.HousingInfo;
import jp.co.transcosmos.dm3.dao.DAO;
import jp.co.transcosmos.dm3.dao.DAOCriteria;
import jp.co.transcosmos.dm3.dao.JoinResult;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;

import org.springframework.web.servlet.ModelAndView;

/**
 * <pre>
 * お気に入り物件一覧
 * お気に入り物件一覧を表示する。
 *
 * 【復帰する View 名】
 *    ・"success" : 正常終了
 *
 * 担当者       修正日     修正内容
 * ------------ ----------- -----------------------------------------------------
 * tan.tianyun   2015.05.06  新規作成
 *
 * 注意事項
 *
 * </pre>
 */
public class FavoriteListCommand implements Command {

	/** お気に入り情報用 Model オブジェクト */
	private PanaFavoriteManageImpl panaFavoriteManager;

    /** 共通パラメータオブジェクト */
    private PanaCommonParameters commonParameters;


	/** 物件情報用 Model オブジェクト */
	private PanaHousingPartThumbnailProxy panaHousingManager;

	/** 物件画像情報DAO */
	private DAO<HousingImageInfo> housingImageInfoDAO;

	/** 共通 Model オブジェクト */
	private PanaCommonManage panaCommonManager;

	/** Panasonic用ファイル処理関連共通Util */
	private PanaFileUtil panaFileUtil;

    /** １ページの表示件数 */
    private int rowsPerPage = 15;

	/**
	 * 物件情報用 Model オブジェクトを設定する。<br/>
	 * <br/>
	 *
	 * @param PanaFavoriteManageImpl
	 *            物件情報用 Model オブジェクト
	 */
	public void setPanaFavoriteManager(PanaFavoriteManageImpl panaFavoriteManager) {
		this.panaFavoriteManager = panaFavoriteManager;
	}

	/**
	 * @param panaHousingManager セットする panaHousingManager
	 */
	public void setPanaHousingManager(
			PanaHousingPartThumbnailProxy panaHousingManager) {
		this.panaHousingManager = panaHousingManager;
	}

	/**
	 * @param commonParameters セットする commonParameters
	 */
	public void setCommonParameters(PanaCommonParameters commonParameters) {
		this.commonParameters = commonParameters;
	}


	/**
	 * @param housingImageInfoDAO セットする housingImageInfoDAO
	 */
	public void setHousingImageInfoDAO(DAO<HousingImageInfo> housingImageInfoDAO) {
		this.housingImageInfoDAO = housingImageInfoDAO;
	}

	/**
	 * @param panaCommonManager セットする panaCommonManager
	 */
	public void setPanaCommonManager(PanaCommonManage panaCommonManager) {
		this.panaCommonManager = panaCommonManager;
	}

	/**
	 * @param rowsPerPage セットする rowsPerPage
	 */
	public void setRowsPerPage(int rowsPerPage) {
		this.rowsPerPage = rowsPerPage;
	}

	/**
	 * @param panaFileUtil セットする panaFileUtil
	 */
	public void setPanaFileUtil(PanaFileUtil panaFileUtil) {
		this.panaFileUtil = panaFileUtil;
	}

	/**
	 * お気に入り物件一覧画面表示処理<br>
	 * <br>
	 *
	 * @param request
	 *            HTTP リクエスト
	 * @param response
	 *            HTTP レスポンス
	 */
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// お気に入り削除エラーの場合、runtimeエラー
		String error = request.getParameter("error");
		if ("RuntimeError".equals(error)) {
			throw new RuntimeException("システム物件CDまたはユーザーIDが指定されていません.");
		}

		Map<String, Object> model = new HashMap<>();
		FavoriteFormFactory factory = FavoriteFormFactory.getInstance(request);

		// ページ処理用のフォームオブジェクトを作成
		FavoriteSearchForm form = factory.createFavoriteSearchForm(request);

        // １ページに表示する行数を設定する。
		form.setRowsPerPage(this.rowsPerPage);

		String keyOrderType = request.getParameter("keyOrderType");
		String sortOrder1Label = request.getParameter("sortOrder1Label");
		String sortOrder2Label = request.getParameter("sortOrder2Label");
		String sortOrder3Label = request.getParameter("sortOrder3Label");
		String sortOrder4Label = request.getParameter("sortOrder4Label");
		String orderBy = "";
		boolean ascending = true;

		if (StringValidateUtil.isEmpty(keyOrderType)) {
			orderBy = "1";
			ascending = false;
		} else if ("1".equals(keyOrderType)) {
			orderBy = "1";
			ascending = true;
		} else if ("2".equals(keyOrderType)) {
			orderBy = "1";
			ascending = false;
		} else if ("3".equals(keyOrderType)) {
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
		MypageUserInterface loginUser = FrontLoginUserUtils.getInstance(request).getMypageLoginUserInfo(request, response);

		// ユーザIDを取得
		String userId = String.valueOf(loginUser.getUserId());

		if(userId == null) {
			throw new RuntimeException("ユーザーIDが指定されていません.");
		}
		// お気に入り物件一覧検索
		int publicListSize = this.panaFavoriteManager.searchPublicFavorite(form, userId, orderBy, ascending);

		// お気に入りテーブルから「削除、非公開」の物件を削除する
		List<FavoriteInfo> listPrivate =  this.panaFavoriteManager.searchPrivateFavorite(userId).get("private");
		for (FavoriteInfo f : listPrivate) {
			this.panaFavoriteManager.delFavorite(f.getUserId(), f.getSysHousingCd());
		}

		/** 物件基本情報リスト */
		List<HousingInfo> housingInfoList = new ArrayList<HousingInfo>();

		/** 建物基本情報リスト */
		List<BuildingInfo> buildingInfoList = new ArrayList<BuildingInfo>();

		/** 建物詳細情報リスト */
		List<BuildingDtlInfo> buildingDtlInfoList = new ArrayList<BuildingDtlInfo>();

		/** 新着アイコンリスト */
		List<String> dateList = new ArrayList<String>();

		/** 物件画像情報リスト */
		List<String> housingImgList = new ArrayList<String>();

		/** 所在地リスト */
		List<String> prefAddressList = new ArrayList<String>();

		/** 建物最寄り駅情報 */
		List<List<String>> buildingStationList = new ArrayList<List<String>>();

		/** 築年月情報 */
		List<String> compDateList = new ArrayList<String>();;

		/** 専有面積 坪リスト */
		List<String> personalAreaSquareList = new ArrayList<String>();

		/** 土地面積 坪リスト */
		List<String> landAreaSquareList = new ArrayList<String>();

		/** 建物面積 坪リスト */
		List<String> buildingAreaSquareList = new ArrayList<String>();

		/** 階建リスト */
		List<String> totalFloorList = new ArrayList<String>();

		/** 所在階リスト */
		List<String> floorNoList = new ArrayList<String>();

		/** URL Encodeリスト */
		List<String> encodeHousingNameList = new ArrayList<String>();

		for (int count = 0; count < form.getVisibleRows().size(); count++) {
			FavoriteInfo favoriteInfo = (FavoriteInfo) form.getVisibleRows().get(count).getItems().get("favoriteInfo");
			String sysHousingCd = favoriteInfo.getSysHousingCd();
			Housing housing = this.panaHousingManager.searchHousingPk(sysHousingCd, true);

	        // 物件基本情報を取得する。
	        HousingInfo housingInfo = ((HousingInfo) housing.getHousingInfo().getItems().get("housingInfo"));
	        encodeHousingNameList.add(URLEncoder.encode(housingInfo.getDisplayHousingName(), "UTF-8"));

			// 建物基本情報を取得する。
			BuildingInfo buildingInfo = (BuildingInfo)housing.getBuilding().getBuildingInfo().getItems().get("buildingInfo");

			// 建物詳細情報を取得する。
			BuildingDtlInfo buildingDtlInfo = (BuildingDtlInfo)housing.getBuilding().getBuildingInfo().getItems().get("buildingDtlInfo");

			housingInfoList.add(housingInfo);
			buildingInfoList.add(buildingInfo);
			buildingDtlInfoList.add(buildingDtlInfo);

			/** 新着アイコン設定 */
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


			/** 物件画像情報設定 */
			String pathName = "";
			// 閲覧権限があり、且つ、表示順、画像タイプ、枝番で昇順ソート後の1番目の画像を表示
			DAOCriteria criteria = new DAOCriteria();
			criteria.addWhereClause("sysHousingCd", sysHousingCd);
			criteria.addWhereClause("roleId", null, DAOCriteria.IS_NULL, true);
			criteria.addOrderByClause("sortOrder");
			criteria.addOrderByClause("imageType");
			criteria.addOrderByClause("divNo");
			List<HousingImageInfo> housingImageInfoList = this.housingImageInfoDAO.selectByFilter(criteria);
			if (housingImageInfoList.size() > 0) {
				jp.co.transcosmos.dm3.corePana.vo.HousingImageInfo housingImageInfo = (jp.co.transcosmos.dm3.corePana.vo.HousingImageInfo)housingImageInfoList.get(0);
				if (PanaCommonConstant.ROLE_ID_PUBLIC.equals(housingImageInfo.getRoleId())) {
					// 閲覧権限が全員の場合
					pathName = this.panaFileUtil.getHousFileOpenUrl(housingImageInfoList.get(0).getPathName(), housingImageInfoList.get(0).getFileName(), this.commonParameters.getHousingListImageSize());
				} else if (PanaCommonConstant.ROLE_ID_PRIVATE.equals(housingImageInfo.getRoleId())) {
					// 閲覧権限が会員のみの場合
					pathName = this.panaFileUtil.getHousFileMemberUrl(housingImageInfoList.get(0).getPathName(), housingImageInfoList.get(0).getFileName(), this.commonParameters.getHousingListImageSize());
				}
			}
             housingImgList.add(pathName);

             /** 所在地設定 */
     		StringBuffer prefAddressName = new StringBuffer();
     		if (!StringValidateUtil.isEmpty(buildingInfo.getPrefCd())) {
     			prefAddressName.append(this.panaCommonManager.getPrefName(buildingInfo.getPrefCd())).append(" ");
     			prefAddressName.append(buildingInfo.getAddressName() == null?"":buildingInfo.getAddressName()).append(" ");
     			prefAddressName.append(buildingInfo.getAddressOther1() == null?"":buildingInfo.getAddressOther1()).append(" ");
     			prefAddressName.append(buildingInfo.getAddressOther2() == null?"":buildingInfo.getAddressOther2());
     		}
             prefAddressList.add(prefAddressName.toString());


             /** アクセスを設定する  */
     		// 建物最寄り駅情報Listを取得
     		 List<JoinResult> buildingStationInfoList = housing.getBuilding().getBuildingStationInfoList();
     		 BuildingStationInfo buildingStationInfo = new BuildingStationInfo();
     		 RrMst rrMst = new RrMst();
     		 RouteMst routeMst = new RouteMst();
     		 StationMst stationMst = new StationMst();
     		 StringBuffer nearStation = new StringBuffer();
     		 List<String> nearStationList = new ArrayList<String>();
    		 for (int i = 0; i < buildingStationInfoList.size(); i++) {

    			 // 建物最寄り駅情報の取得
    			 buildingStationInfo = new BuildingStationInfo();
    			 buildingStationInfo =  (BuildingStationInfo)buildingStationInfoList.get(i).getItems().get("buildingStationInfo");
    			 // 鉄道会社マスタの取得
    			 rrMst = new RrMst();
    			 rrMst = (RrMst)buildingStationInfoList.get(i).getItems().get("rrMst");
    			 // 路線マスタの取得
    			 routeMst = new RouteMst();
    			 routeMst = (RouteMst)buildingStationInfoList.get(i).getItems().get("routeMst");
    			 // 駅マスタの取得
    			 stationMst = new StationMst();
    			 stationMst = (StationMst)buildingStationInfoList.get(i).getItems().get("stationMst");

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
    		 buildingStationList.add(nearStationList);

			/** 築年月を設定する  */
    		String compDate = "";
			if (buildingInfo != null && buildingInfo.getCompDate() != null) {
				compDate = new SimpleDateFormat("yyyy年M月築").format(buildingInfo.getCompDate());
			}
			compDateList.add(compDate);

			/** 専有面積／建物面積／土地面積、階建／所在階を設定する  */
			// 専有面積 坪
			String personalAreaSquare = "";
			// 土地面積 坪
			String landAreaSquare = "";
			// 建物面積 坪
			String buildingAreaSquare = "";
			// 階建
			String totalFloor = "";
			// 所在階
			String floorNo = "";


			// 物件種類CDを判断
			if (PanaCommonConstant.HOUSING_KIND_CD_MANSION.equals(buildingInfo.getHousingKindCd())) {
				if (housingInfo.getPersonalArea() != null) {
					// 専有面積 坪
					personalAreaSquare = ((housingInfo.getPersonalArea() == null) ? "" : "（約" + PanaCalcUtil.calcTsubo(housingInfo.getPersonalArea()).toString() + "坪）") +
								(housingInfo.getPersonalAreaMemo() == null ? "" : housingInfo.getPersonalAreaMemo());
				}
				if (buildingInfo != null) {
					// 階建
					totalFloor = (buildingInfo.getTotalFloors() == null) ? "" : buildingInfo.getTotalFloors() + "階建";
					// 所在階
					floorNo = (housingInfo.getFloorNo() == null) ? "" : housingInfo.getFloorNo() + "階";
				}
			} else if (PanaCommonConstant.HOUSING_KIND_CD_GROUND.equals(buildingInfo.getHousingKindCd()) ||
					PanaCommonConstant.HOUSING_KIND_CD_HOUSE.equals(buildingInfo.getHousingKindCd())) {
				if (buildingDtlInfo != null && buildingDtlInfo.getBuildingArea() != null) {
					// 建物面積 坪
					buildingAreaSquare = ((buildingDtlInfo.getBuildingArea() == null) ? "" : "（約" + PanaCalcUtil.calcTsubo(buildingDtlInfo.getBuildingArea()).toString() + "坪）") +
								(buildingDtlInfo.getBuildingAreaMemo() == null? "" : buildingDtlInfo.getBuildingAreaMemo()) ;
				}
				if (housingInfo.getLandArea() != null) {
					// 土地面積　坪
					landAreaSquare = ((housingInfo.getLandArea() == null) ? "" : "（約" + PanaCalcUtil.calcTsubo(housingInfo.getLandArea()).toString() + "坪）") +
								(housingInfo.getLandAreaMemo() == null? "" : housingInfo.getLandAreaMemo()) ;
				}
			}
			personalAreaSquareList.add(personalAreaSquare);
			landAreaSquareList.add(landAreaSquare);
			buildingAreaSquareList.add(buildingAreaSquare);
			totalFloorList.add(totalFloor);
			floorNoList.add(floorNo);
		}

		model.put("housingInfoList", housingInfoList);
		model.put("buildingInfoList", buildingInfoList);
		model.put("buildingDtlInfoList", buildingDtlInfoList);
		model.put("dateList", dateList);
		model.put("housingImgList", housingImgList);
		model.put("prefAddressList", prefAddressList);
		model.put("buildingStationList", buildingStationList);
		model.put("compDateList", compDateList);
		model.put("personalAreaSquareList", personalAreaSquareList);
		model.put("landAreaSquareList", landAreaSquareList);
		model.put("buildingAreaSquareList", buildingAreaSquareList);
		model.put("totalFloorList", totalFloorList);
		model.put("floorNoList", floorNoList);
		model.put("keyOrderType", keyOrderType);
		model.put("sortOrder1Label", sortOrder1Label == null? "お気に入り登録日 ▼" : sortOrder1Label);
		model.put("sortOrder2Label", sortOrder2Label == null? "物件価格 ▲" : sortOrder2Label);
		model.put("sortOrder3Label", sortOrder3Label == null? "築年数 ▲" : sortOrder3Label);
		model.put("sortOrder4Label", sortOrder4Label == null? "駅からの距離 ▲" : sortOrder4Label);
		model.put("privateFavoriteHousingList", listPrivate);
		model.put("publicFavoriteHousingListForm", form);
		model.put("encodeHousingNameList", encodeHousingNameList);

		// Cookieを設定する
		FavoriteCookieUtils.getInstance(request).setFavoriteCount(request, response, publicListSize);
		request.setAttribute(FavoriteCookieUtils.FAVORITE_COUNT_KEY, publicListSize);

		return new ModelAndView("success", model);
	}

}
