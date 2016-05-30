package jp.co.transcosmos.dm3.webFront.myPageTop.command;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.model.InformationManage;
import jp.co.transcosmos.dm3.core.model.MypageUserManage;
import jp.co.transcosmos.dm3.core.model.housingRequest.HousingRequest;
import jp.co.transcosmos.dm3.core.model.housingRequest.HousingRequestManageImpl;
import jp.co.transcosmos.dm3.core.util.FrontLoginUserUtils;
import jp.co.transcosmos.dm3.core.vo.HousingImageInfo;
import jp.co.transcosmos.dm3.core.vo.HousingInfo;
import jp.co.transcosmos.dm3.core.vo.HousingReqKind;
import jp.co.transcosmos.dm3.core.vo.HousingReqLayout;
import jp.co.transcosmos.dm3.core.vo.HousingReqPart;
import jp.co.transcosmos.dm3.core.vo.HousingRequestArea;
import jp.co.transcosmos.dm3.core.vo.Information;
import jp.co.transcosmos.dm3.core.vo.PartSrchMst;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonConstant;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonParameters;
import jp.co.transcosmos.dm3.corePana.model.PanaCommonManage;
import jp.co.transcosmos.dm3.corePana.model.RecentlyInfoManage;
import jp.co.transcosmos.dm3.corePana.model.favorite.PanaFavoriteManageImpl;
import jp.co.transcosmos.dm3.corePana.model.housing.PanaHousing;
import jp.co.transcosmos.dm3.corePana.util.PanaFileUtil;
import jp.co.transcosmos.dm3.corePana.vo.HousingRequestInfo;
import jp.co.transcosmos.dm3.corePana.vo.MemberInfo;
import jp.co.transcosmos.dm3.dao.DAO;
import jp.co.transcosmos.dm3.dao.DAOCriteria;
import jp.co.transcosmos.dm3.dao.JoinResult;
import jp.co.transcosmos.dm3.login.LoginUser;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;

/**
 * <pre>
 * マイページ画面
 * リクエストパラメータで渡された物件詳細のバリデーションを行い、物件詳細画面を表示する。
 *
 * 【復帰する View 名】
 *    ・"success" : 正常終了（ログイン済み）
 *    ・"nologin" : 正常終了（ログイン前マイページに遷移）
 *
 * 担当者       修正日     修正内容
 * ------------ ----------- -----------------------------------------------------
 * 郭中レイ     2015.04.13  新規作成
 * Duong.Nguyen     2015.08.19  Put housing_kind_cd into housing request model
 *
 * 注意事項
 *
 * </pre>
 */
public class MyPageTopCommand implements Command {

	/** お知らせメンテナンスを行う Model オブジェクト */
	private InformationManage informationManager;

	/** お気に入り情報取用 Model オブジェクト */
	private PanaFavoriteManageImpl panaFavoriteManager;

	/** 最近見た物件情報を管理する Model オブジェクト */
	private RecentlyInfoManage recentlyInfoManage;

	/** 物件リクエスト情報用 Model オブジェクト */
	private HousingRequestManageImpl housingRequestManage;

	/** 共通パラメータオブジェクト */
	private PanaCommonParameters commonParameters;

	/** マスタ情報メンテナンスを行う Model オブジェクト */
	private PanaCommonManage panamCommonManager;

	/** 共通コード変換処理 */
	private CodeLookupManager codeLookupManager;

	/** こだわり条件マスタ */
	private DAO<PartSrchMst> partSrchMstDAO;

	/** マイページユーザーの情報管理用 Model */
	private MypageUserManage mypageUserManager;

	/** 物件画像情報DAO */
	private DAO<HousingImageInfo> housingImageInfoDAO;

	/** Panasonic用ファイル処理関連共通Util */
	private PanaFileUtil fileUtil;

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

	/**
	 * お知らせメンテナンスを行う Model　オブジェクトを設定する。<br/>
	 * <br/>
	 * @param informationManager お知らせメンテナンスの model オブジェクト
	 */
	public void setInformationManager(InformationManage informationManager) {
		this.informationManager = informationManager;
	}

	/**
	 * お気に入り情報取用 Model オブジェクトを設定する。<br/>
	 * <br/>
	 * @param memberManager 共通情報取用 Model オブジェクト
	 */
	public void setPanaFavoriteManager(PanaFavoriteManageImpl panaFavoriteManager) {
		this.panaFavoriteManager = panaFavoriteManager;
	}

	/**
	 * 最近見た物件情報用 Model オブジェクトを設定する。<br/>
	 * <br/>
	 * @param RecentlyInfoManage 最近見た物件情報用 Model オブジェクト
	 */
	public void setRecentlyInfoManage(RecentlyInfoManage recentlyInfoManage) {
		this.recentlyInfoManage = recentlyInfoManage;
	}

	/**
	 * 物件リクエスト情報用 Model オブジェクトを設定する。<br/>
	 * <br/>
	 * @param HousingRequestManage 物件リクエスト情報情報用 Model オブジェクト
	 */
	public void setHousingRequestManage(
			HousingRequestManageImpl housingRequestManage) {
		this.housingRequestManage = housingRequestManage;
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
	 * コンストラクター<br/>
	 * Factory 以外からのインスタンス生成を制限する為、コンストラクターを隠蔽する。<br/>
	 * <br/>
	 */
	public void setCodeLookupManager(CodeLookupManager codeLookupManager) {
		this.codeLookupManager = codeLookupManager;
	}

	/**
	 * @param addressMstDAO
	 *            セットする addressMstDAO
	 */
	public void setPartSrchMstDAO(DAO<PartSrchMst> partSrchMstDAO) {
		this.partSrchMstDAO = partSrchMstDAO;
	}

	/**
	 * マイページユーザーの情報管理用 Model（core） を設定する。<br/>
	 * <br/>
	 *
	 * @param mypageUserManager
	 *            マイページユーザーの情報管理用 Model（core）
	 */
	public void setMypageUserManager(MypageUserManage mypageUserManager) {
		this.mypageUserManager = mypageUserManager;
	}

	/**
	 * @param housingImageInfoDAO セットする housingImageInfoDAO
	 */
	public void setHousingImageInfoDAO(DAO<HousingImageInfo> housingImageInfoDAO) {
		this.housingImageInfoDAO = housingImageInfoDAO;
	}

	/**
	 * Panasonic用ファイル処理関連共通Util。<br/>
	 * <br/>
	 *
	 * @param fileUtil セットする fileUtil
	 */
	public void setFileUtil(PanaFileUtil fileUtil) {
		this.fileUtil = fileUtil;
	}

	/**
	 * マイページ画面表示処理<br>
	 * <br>
	 *
	 * @param request
	 *            HTTP リクエスト
	 * @param response
	 *            HTTP レスポンス
	 */
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// リクエストパラメータを格納した model オブジェクトを生成する。
		// このオブジェクトは View 層への値引渡しに使用される。
		Map<String, Object> model = createModel(request);
		String command = "success";

		// 共通パラメータ情報
		model.put("commonParameters", this.commonParameters);

		// フロント側のユーザ情報
		LoginUser loginUser = FrontLoginUserUtils.getInstance(request).getMypageLoginUserInfo(request, response);
		if (loginUser == null) {
			loginUser = FrontLoginUserUtils.getInstance(request).getAnonLoginUserInfo(request, response);
			command = "nologin";
		}
		String loginUserId = (String)loginUser.getUserId();

		// ログイン済み　又は　未ログインでも必要な情報を取得する
		getCommon(loginUserId, model, command);
		if ("success".equals(command)) {
			// ログイン済み　必要な情報を取得する
			getLogin(loginUserId, model);
		}

		return new ModelAndView(command, model);
	}



	/**
	 * ログイン済み　又は　未ログインでも必要な情報を取得する
	 * @param loginUserId
	 * @param model
	 * @param command
	 * @throws Exception
	 */
	private void getCommon(String loginUserId, Map<String, Object> model, String command) throws Exception {

		// 最近見た物件情報結果リストを取得する。
		List<PanaHousing> recentlyInfoList = this.recentlyInfoManage.searchRecentlyInfo(loginUserId);
		// 最近見た物件画像情報リスト
		List<String> recentlyInfoImgList = new ArrayList<String>();
		HousingInfo housingInfo = new HousingInfo();
		for(PanaHousing housing: recentlyInfoList) {
			housingInfo = (HousingInfo)housing.getHousingInfo().getItems().get("housingInfo");
			String sysHousingCd = housingInfo.getSysHousingCd();
			// 閲覧権限があり、且つ、表示順、画像タイプ、枝番で昇順ソート後の1番目の画像を表示
			DAOCriteria criteria = new DAOCriteria();
			criteria.addWhereClause("sysHousingCd", sysHousingCd);
			if ("success".equals(command)) {
				criteria.addWhereClause("roleId", null, DAOCriteria.IS_NULL, true);
			} else {
				criteria.addWhereClause("roleId", PanaCommonConstant.ROLE_ID_PUBLIC);
			}
			criteria.addOrderByClause("sortOrder");
			criteria.addOrderByClause("imageType");
			criteria.addOrderByClause("divNo");
			List<HousingImageInfo> housingImageInfoList = this.housingImageInfoDAO.selectByFilter(criteria);
			/** 物件画像情報設定 */
			String pathName = "";
			if (housingImageInfoList.size() > 0) {
				jp.co.transcosmos.dm3.corePana.vo.HousingImageInfo housingImageInfo = (jp.co.transcosmos.dm3.corePana.vo.HousingImageInfo)housingImageInfoList.get(0);
				if (PanaCommonConstant.ROLE_ID_PUBLIC.equals(housingImageInfo.getRoleId())) {
					// 閲覧権限が全員の場合
					pathName = this.fileUtil.getHousFileOpenUrl(housingImageInfoList.get(0).getPathName(),
							housingImageInfoList.get(0).getFileName(),
							this.commonParameters.getMypageHistoryImageSize());
				} else if (PanaCommonConstant.ROLE_ID_PRIVATE.equals(housingImageInfo.getRoleId())) {
					// 閲覧権限が会員のみの場合
					pathName = this.fileUtil.getHousFileMemberUrl(housingImageInfoList.get(0).getPathName(),
							housingImageInfoList.get(0).getFileName(),
							this.commonParameters.getMypageHistoryImageSize());
				}
			}
			recentlyInfoImgList.add(pathName);
		}

		model.put("recentlyInfoList", recentlyInfoList);
		model.put("recentlyInfoImgList", recentlyInfoImgList);

	}

	/**
	 * ログイン済み　必要な情報を取得する
	 * @param loginUserId
	 * @param model
	 * @throws Exception
	 */
	private void getLogin(String loginUserId, Map<String, Object> model) throws Exception {

		// お客様へのお知らせの取得
		List<Information> infoList= this.informationManager.searchMyPageInformation(loginUserId);
		// お気に入り物件一覧情報の取得
		List<PanaHousing> favoriteInfoList = this.panaFavoriteManager.searchFavoriteInfo(loginUserId);
		// お気に入り物件画像情報リスト
		List<String> favoriteInfoImgList = new ArrayList<String>();
		List<jp.co.transcosmos.dm3.core.vo.HousingImageInfo> housingImageInfoList = new  ArrayList<jp.co.transcosmos.dm3.core.vo.HousingImageInfo>();
		for(PanaHousing housing : favoriteInfoList) {
			housingImageInfoList = housing.getHousingImageInfos();
			if(null != housingImageInfoList) {
				/** 物件画像情報設定 */
				String pathName = "";
				if (housingImageInfoList.size() > 0) {
					jp.co.transcosmos.dm3.corePana.vo.HousingImageInfo housingImageInfo = (jp.co.transcosmos.dm3.corePana.vo.HousingImageInfo)housingImageInfoList.get(0);
					if (PanaCommonConstant.ROLE_ID_PUBLIC.equals(housingImageInfo.getRoleId())) {
						// 閲覧権限が全員の場合
						pathName = this.fileUtil.getHousFileOpenUrl(housingImageInfoList.get(0).getPathName(),
								housingImageInfoList.get(0).getFileName(),
								this.commonParameters.getMypageHistoryImageSize());
					} else if (PanaCommonConstant.ROLE_ID_PRIVATE.equals(housingImageInfo.getRoleId())) {
						// 閲覧権限が会員のみの場合
						pathName = this.fileUtil.getHousFileMemberUrl(housingImageInfoList.get(0).getPathName(),
								housingImageInfoList.get(0).getFileName(),
								this.commonParameters.getMypageHistoryImageSize());
					}
				}
				favoriteInfoImgList.add(pathName);
			}
		}
		// 物件リクエストの一覧情報の取得
		List<HousingRequest> housingRequestList = this.housingRequestManage.searchRequest(loginUserId);
		// マイページユーザー情報の取得
		JoinResult joinResult = this.mypageUserManager.searchMyPageUserPk(loginUserId);
		MemberInfo memberInfo = (MemberInfo)joinResult.getItems().get("memberInfo");

		// 物件リクエスト情報
		HousingRequestInfo housingRequestInfo = new HousingRequestInfo();
		// 物件リクエストエリア情報のリスト
		List<HousingRequestArea> housingRequestAreas = new ArrayList<HousingRequestArea>();
		// 物件リクエスト間取り情報のリスト
		List<HousingReqLayout> housingReqLayouts = new ArrayList<HousingReqLayout>();
		// 物件リクエスト物件種類情報のリスト
		List<HousingReqKind> housingReqKinds = new ArrayList<HousingReqKind>();
		// 物件リクエストこだわり条件情報のリスト
		List<HousingReqPart> housingReqParts = new ArrayList<HousingReqPart>();
		// 画面用物件リクエスト情報
		List<JoinResult> housingRequestLst = new ArrayList<JoinResult>();
		HousingReqLayout housingReqLayout = new HousingReqLayout();

		for(HousingRequest housingRequest : housingRequestList) {
			Map<String, Object> hr = new HashMap<>();
			Map<String, Object> requestList = new HashMap<>();
			housingRequestInfo = (HousingRequestInfo)housingRequest.getHousingRequestInfo();
			housingRequestAreas = housingRequest.getHousingRequestAreas();
			housingReqLayouts = housingRequest.getHousingReqLayouts();

			housingReqKinds = housingRequest.getHousingReqKinds();
			HousingReqKind housingReqKind = new HousingReqKind();
			if(0 < housingReqKinds.size()) {
				housingReqKind = housingReqKinds.get(0);
			}
			housingReqParts = housingRequest.getHousingReqParts();
			// 都道府県名
			String prefName = "";
			// 市区町村名
			StringBuffer addressCd = new StringBuffer();
			// 検索条件
			StringBuffer requests = new StringBuffer();
			for(int i = 0; i < housingRequestAreas.size(); i++) {
				HousingRequestArea arer = housingRequestAreas.get(i);
				prefName = this.panamCommonManager.getPrefName(arer.getPrefCd());
				addressCd.append(this.panamCommonManager.getAddressName(arer.getAddressCd()));
				if(i < housingRequestAreas.size()) {
					addressCd.append("　");
				}
			}

			// 予算
			if(null != housingRequestInfo.getPriceLower() || null != housingRequestInfo.getPriceUpper()) {
				requests.append(castPrice(housingRequestInfo.getPriceLower(), housingRequestInfo.getPriceUpper()));
				if(!StringUtils.isEmpty(housingRequestInfo.getUseReform())) {
					if(!PanaCommonConstant.HOUSING_KIND_CD_GROUND.equals(housingReqKind.getHousingKindCd())) {
						requests.append("　リフォーム価格込み");
					}
				}
				requests.append("｜");
			}

			// 物件種別ＣＤ:マンション
			if(PanaCommonConstant.HOUSING_KIND_CD_MANSION.equals(housingReqKind.getHousingKindCd())) {
				// 専有面積
				if(null != housingRequestInfo.getPersonalAreaLower() || null != housingRequestInfo.getPersonalAreaUpper()) {
					requests.append(bigDecimalFormat(housingRequestInfo.getPersonalAreaLower(), housingRequestInfo.getPersonalAreaUpper(), "1"));
					requests.append("｜");
				}

				// 間取り
				if(0 < housingReqLayouts.size()) {
					housingReqLayout = new HousingReqLayout();
					for(int i = 0; i < housingReqLayouts.size(); i++) {
						housingReqLayout = housingReqLayouts.get(i);
						if(!StringUtils.isEmpty(housingReqLayout.getLayoutCd())) {
							requests.append(this.codeLookupManager.lookupValue("layoutCd", housingReqLayout.getLayoutCd()));
							if(i < housingReqLayouts.size() - 1) {
								requests.append("　");
							}
						}
					}
					requests.append("｜");
				}

				// 築年月
				if(null != housingRequestInfo.getBuiltMonth()) {
					if(housingRequestInfo.getBuiltMonth().compareTo(new Integer(0))  > 0) {
						requests.append("築年数");
						requests.append(this.codeLookupManager.lookupValue("compDate", housingRequestInfo.getBuiltMonth().toString()));
						requests.append("｜");
					}
				}

				// おすすめ
				if(0 < housingReqParts.size()) {
					for(HousingReqPart part : housingReqParts) {
						// こだわり条件名称
						PartSrchMst partSrchMst =  partSrchMstDAO.selectByPK(part.getPartSrchCd());
						requests.append(partSrchMst.getPartSrchName());
						requests.append("　");
					}
				}

			}

			// 物件種別ＣＤ:戸建 物件種別ＣＤ:土地
			if(PanaCommonConstant.HOUSING_KIND_CD_GROUND.equals(housingReqKind.getHousingKindCd()) ||
					PanaCommonConstant.HOUSING_KIND_CD_HOUSE.equals(housingReqKind.getHousingKindCd())) {

				// 建物面積
				if(null != housingRequestInfo.getBuildingAreaLower() || null != housingRequestInfo.getBuildingAreaUpper()) {
					requests.append(bigDecimalFormat(housingRequestInfo.getBuildingAreaLower(), housingRequestInfo.getBuildingAreaUpper(), "2"));
					requests.append("｜");
				}

				// 土地面積
				if(null != housingRequestInfo.getLandAreaLower() || null != housingRequestInfo.getLandAreaUpper()) {
					requests.append(bigDecimalFormat(housingRequestInfo.getLandAreaLower(), housingRequestInfo.getLandAreaUpper(), "3"));
					requests.append("｜");
				}
			}

			// 物件種別ＣＤ:戸建
			if(PanaCommonConstant.HOUSING_KIND_CD_HOUSE.equals(housingReqKind.getHousingKindCd())) {
				// 間取り
				if(0 < housingReqLayouts.size()) {
					housingReqLayout = new HousingReqLayout();
					for(int i = 0; i < housingReqLayouts.size(); i++) {
						housingReqLayout = housingReqLayouts.get(i);
						if(!StringUtils.isEmpty(housingReqLayout.getLayoutCd())) {
							requests.append(this.codeLookupManager.lookupValue("layoutCd", housingReqLayout.getLayoutCd()));
							if(i < housingReqLayouts.size() - 1) {
								requests.append("　");
							}
						}
					}
					requests.append("｜");
				}

				// 築年月
				if(null != housingRequestInfo.getBuiltMonth()) {
					if(housingRequestInfo.getBuiltMonth().compareTo(new Integer(0))  > 0) {
						requests.append("築年数");
						requests.append(this.codeLookupManager.lookupValue("compDate", housingRequestInfo.getBuiltMonth().toString()));
						requests.append("｜");
					}
				}

				// おすすめ
				if(0 < housingReqParts.size()) {
					for(HousingReqPart part : housingReqParts) {
						// こだわり条件名称
						PartSrchMst partSrchMst =  partSrchMstDAO.selectByPK(part.getPartSrchCd());
						requests.append(partSrchMst.getPartSrchName());
						requests.append("　");
					}
				}
			}

			// 2015.08.19 Duong.Nguyen Put housing_kind_cd into housing request model start
			hr.put("housingKindCd", housingReqKind.getHousingKindCd());
			// 2015.08.19 Duong.Nguyen Put housing_kind_cd into housing request model end
			hr.put("housingRequestId", housingRequestInfo.getHousingRequestId());
			hr.put("prefName", prefName);
			hr.put("addressCd", addressCd.toString());
			String requestStr = requests.toString();
			if(!StringUtils.isEmpty(requestStr)) {
				int len = requestStr.length();
				String latStr = requestStr.substring(len - 1, len);
				if(latStr.equals("　") || latStr.equals("｜")) {
					requestStr = requestStr.substring(0, len - 1);
				}
			}
			hr.put("requests", requestStr);

			requestList.put("housingRequest", hr);
			JoinResult requestLst = new JoinResult(requestList);
			housingRequestLst.add(requestLst);
		}

		// 処理実行を行う。
		model.put("infoList", infoList);
		model.put("favoriteInfoList", favoriteInfoList);
		model.put("favoriteInfoImgList", favoriteInfoImgList);
		model.put("housingRequestLst", housingRequestLst);
		model.put("memberInfo", memberInfo);
	}

	/**
	 * リクエストパラメータから outPutForm オブジェクトを作成する。<br/>
	 * 生成した outPutForm オブジェクトは Map に格納して復帰する。<br/>
	 * key = フォームクラス名（パッケージなし）、Value = フォームオブジェクト <br/>
	 *
	 * @param request
	 *            HTTP リクエストパラメータ
	 * @return パラメータが設定されたフォームオブジェクトを格納した Map オブジェクト
	 */
	private Map<String, Object> createModel(HttpServletRequest request) {

		Map<String, Object> model = new HashMap<>();

		return model;
	}

	/**
	 * 各面積の編集を行う。
	 *
	 * @param areaLower 面積・下限値
	 * @param areaUpper 面積・上限値
	 * @param areaFlg 面積フラグ(1:専有面積、2:建物面積、3:土地面積)
	 * @return パラメータが設定されたフォームオブジェクトを格納した Map オブジェクト
	 */
	private String bigDecimalFormat(BigDecimal areaLower, BigDecimal areaUpper, String areaFlg) {
		StringBuffer res = new StringBuffer();

		if("1".equals(areaFlg)) {
			res.append("専有面積");
		}

		if("2".equals(areaFlg)) {
			res.append("建物面積");
		}

		if("3".equals(areaFlg)) {
			res.append("土地面積");
		}

		if(null != areaLower) {
			if(areaLower.compareTo(BigDecimal.ZERO) > 0) {
				res.append(areaLower);
				res.append("㎡");
				res.append("以上");
			}
		}

		if(res.length() > 4) {
			res.append("　");
		}

		if(null != areaUpper) {
			if(areaUpper.compareTo(BigDecimal.ZERO) > 0) {
				res.append(areaUpper);
				res.append("㎡");
				res.append("以下");
			}
		}

		return res.toString();
	}

	/**
	 * 予算の編集を行う。
	 *
	 * @param priceLower 賃料/価格・下限値
	 * @param priceUpper 賃料/価格・上限値
	 * @return パラメータが設定されたフォームオブジェクトを格納した Map オブジェクト
	 */
	private String castPrice(Long priceLower, Long priceUpper) {
		// 返す結果
		StringBuffer res = new StringBuffer();
		Long longLower= 0L;
		Long longUpper= 0L;

		res.append("予算");

		if(priceLower != null) {
			if(priceLower.compareTo(longLower) > 0) {
				longLower = priceLower / 10000L;

				if(longLower.compareTo(10000L) == 0) {
					longLower = longLower / 10000L;
					res.append(longLower);
					res.append("億");
				} else {
					res.append(longLower);
					res.append("万円");
				}

				res.append("以上");
			}
		}

		if(res.length() > 2) {
			res.append("　");
		}

		if(priceUpper != null) {
			if(priceUpper.compareTo(longUpper) > 0) {
				longUpper = priceUpper / 10000L;

				if(longUpper.compareTo(10000L) == 0) {
					longUpper = longUpper / 10000L;
					res.append(longUpper);
					res.append("億");
				} else {
					res.append(longUpper);
					res.append("万円");
				}

				res.append("以下");
			}
		}
		return res.toString();
	}
}
