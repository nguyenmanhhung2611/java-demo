package jp.co.transcosmos.dm3.corePana.model.housing;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import jp.co.transcosmos.dm3.core.vo.BuildingDtlInfo;
import jp.co.transcosmos.dm3.core.vo.BuildingInfo;
import jp.co.transcosmos.dm3.core.vo.BuildingLandmark;
import jp.co.transcosmos.dm3.core.vo.BuildingStationInfo;
import jp.co.transcosmos.dm3.core.vo.EquipMst;
import jp.co.transcosmos.dm3.core.vo.HousingDtlInfo;
import jp.co.transcosmos.dm3.core.vo.HousingImageInfo;
import jp.co.transcosmos.dm3.core.vo.PrefMst;
import jp.co.transcosmos.dm3.core.vo.RouteMst;
import jp.co.transcosmos.dm3.core.vo.RrMst;
import jp.co.transcosmos.dm3.core.vo.StationMst;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonConstant;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonParameters;
import jp.co.transcosmos.dm3.corePana.util.PanaCalcUtil;
import jp.co.transcosmos.dm3.corePana.util.PanaFileUtil;
import jp.co.transcosmos.dm3.corePana.util.PanaStringUtils;
import jp.co.transcosmos.dm3.corePana.vo.HousingInfo;
import jp.co.transcosmos.dm3.corePana.vo.HousingInspection;
import jp.co.transcosmos.dm3.corePana.vo.ReformDtl;
import jp.co.transcosmos.dm3.corePana.vo.ReformImg;
import jp.co.transcosmos.dm3.corePana.vo.ReformPlan;
import jp.co.transcosmos.dm3.dao.JoinResult;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;

/**
 * 物件詳細画面用フォーム.
 * <p>
 * <pre>
 * 担当者        修正日        修正内容
 * ------------ ----------- -----------------------------------------------------
 * gao.long        2015.04.13     新規作成
 * </pre>
 * <p>
 *
 */
public class PanaHousingDetailed {

	/** 物件単体モード */
	private static final String DETAIL_MODE = "detail";
	/** リフォームプランありモード */
	private static final String REFORM_MODE = "reform";
	/** 物件画像モード */
	private static final String HOUSING_IMG_MODE = "housingImg";
	/** リフォーム画像モード */
	private static final String REFORM_IMG_MODE = "reformImg";

	/** 共通コード変換処理 */
	private CodeLookupManager codeLookupManager;
	/** 共通パラメータ情報 */
	private PanaCommonParameters commonParameters;
	/** ファイル処理関連共通Util */
	private PanaFileUtil panaFileUtil;
	/** 処理モード */
	private String mode;
	/** プレビューフラグ */
	private boolean previewFlg;
	/** 会員フラグ */
	private boolean memberFlg;
	/** 物件種類CD */
	private String housingKindCd;
	/** リフォームCD */
	private String reformCd;
	/** タイトルの表示フラグ */
	private boolean titleDisplayFlg;
	/** 新着フラグ */
	private boolean freshFlg;
	/** 詳細コメント */
	private String dtlComment;
	/** システム物件CD */
	private String sysHousingCd;
	/** 物件番号 */
	private String housingCd;
	/** 物件名 */
	private String displayHousingName;
	/** この物件はおすすめリフォームプランがありますフラグ */
	private boolean reformPlanExists;
	/** 物件情報の表示フラグ */
	private boolean housingInfoDisplayFlg;
	/** アイコン情報【hidden】「配列」 */
	private String[] iconCd;
	/** 物件価格 */
	private String price;
	/** 物件価格【hidden】 */
	private String priceHidden;
	/** 都道府県名 */
	private String prefName;
	/** 所在地 */
	private String address;
	/** アクセス「配列」 */
	private String[] access;
	/** マンション面積フラグ */
	private boolean personalAreaFlg;
	/** 間取CD */
	private String layoutCd;
	/** 専有面積 */
	private String personalArea;
	/** 専有面積 坪 */
	private String personalAreaSquare;
	/** 専有面積_補足 */
	private String personalAreaMemo;
	/** 戸建 土地面積フラグ */
	private boolean buildingAreaFlg;
	/** 建物面積 */
	private String buildingArea;
	/** 建物面積 坪 */
	private String buildingAreaSquare;
	/** 建物面積_補足 */
	private String buildingAreaMemo;
	/** 土地面積 */
	private String landArea;
	/** 土地面積 坪 */
	private String landAreaSquare;
	/** 土地面積_補足 */
	private String landAreaMemo;
	/** 築年月 */
	private String compDate;
	/** 階建／所在階 */
	private String floor;
	/** 階建／所在階フラグ */
	private boolean floorFlg;
	/** 周辺地図 */
	private String mapUrl;
	/** 物件のリフォームプランの表示フラグ */
	private boolean reformPlanDisplayFlg;
	/** リフォームプラン準備中文言の表示フラグ */
	private boolean reformPlanReadyDisplayFlg;
	/** リフォームプラン準備中文言 */
	private String reformPlanReadyComment;
	/** プラン番号【hidden】「配列」 */
	private String[] planNoHidden;
	/** プランタイプ「配列」 */
	private String[] planType;
	/** リフォーム価格「配列」 */
	private String[] planPrice;
	/** 総額１「配列」 */
	private String[] totalPrice1;
	/** 総額２「配列」 */
	private String[] totalPrice2;
	/** システムリフォームCD【hidden】「配列」 */
	private String[] reformCdHidden;
	/** システムリフォームURL【hidden】「配列」 */
	private String[] reformUrl;
	/** List of reform categories*/
	private String[] reformCategory;
	/** 画像の表示フラグ */
	private boolean imgDisplayFlg;
	/** 画像番号【hidden】「配列」 */
	private String[] imgNoHidden;
	/** パス名１【hidden】「配列」 */
	private String[] housingImgPath1Hidden;
	/** パス名２【hidden】「配列」 */
	private String[] housingImgPath2Hidden;
	/** 動画パス【hidden】 */
	private String movieUrl;
	/** コメント【hidden】「配列」 */
	private String[] housingImgCommentHidden;
	/** お気に入り登録フラグの表示フラグ */
	private boolean favoriteDisplayFlg;
	/** 売主のコメントの表示フラグ */
	private boolean salesCommentDisplayFlg;
	/** 売主のコメント */
	private String salesComment;
	/** 担当者からのおすすめの表示フラグ */
	private boolean recommendDisplayFlg;
	/** おすすめ画像パス【hidden】 */
	private String staffImagePathName;
	/** おすすめ内容 */
	private String recommendComment;
	/** 担当 */
	private String staffName;
	/** 会社名 */
	private String companyName;
	/** 支店名 */
	private String branchName;
	/** 免許番号 */
	private String licenseNo;
	/** 内観画像フラグ */
	private boolean introspectImgFlg;
	/** 会員登録の表示フラグ */
	private boolean loginDisplayFlg;
	/** 物件詳細情報の表示フラグ */
	private boolean housingDtlInfoDisplayFlg;
	/** インフラ */
	private String infrastructure;
	/** 建ぺい率 */
	private String coverage;
	/** バルコニー面積 */
	private String balconyArea;
	/** 物件詳細情報 備考 */
	private String upkeepCorp;
	/** 引渡し */
	private String moveinTiming;
	/** 引渡時期コメント */
	private String moveinNote;
	/** 管理形態 */
	private String upkeepType;
	/** 管理費等 */
	private String upkeep;
	/** 規模 */
	private String scale;
	/** 現況 */
	private String status;
	/** 更新日 */
	private String updDate;
	/** 次回更新予定 */
	private String nextUpdDate;
	/** 構造 */
	private String struct;
	/** 私道負担 */
	private String privateRoad;
	/** 主要採光 */
	private String direction;
	/** 取引形態 */
	private String transactTypeDiv;
	/** 修繕積立金 */
	private String menteFee;
	/** 接道 */
	private String contactRoad;
	/** 接道方向/幅員 */
	private String contactRoadDir;
	/** 総戸数 */
	private String totalHouseCnt;
	/** 駐車場 */
	private String parkingSituation;
	/** 土地権利 */
	private String landRight;
	/** 特記事項 */
	private String specialInstruction;
	/** 容積率 */
	private String buildingRate;
	/** 用途地域 */
	private String usedAreaCd;
	/** 瑕疵保険 */
	private String insurExist;
	/** 物件特徴の表示フラグ */
	private boolean housingPropertyDisplayFlg;
	/** 物件特徴 */
	private String equipName;
	/** 地域情報の表示フラグ */
	private boolean landmarkDisplayFlg;
	/** 地域情報番号【hidden】「配列」 */
	private String[] landmarkNoHidden;
	/** ランドマークの種類「配列」 */
	private String[] landmarkType;
	/** 地域情報（名称）「配列」 */
	private String[] landmarkName;
	/** 地域情報（所要時間/距離）「配列」 */
	private String[] distanceFromLandmark;
	/** おすすめリフォームプラン例の表示フラグ */
	private boolean recommendReformPlanDisplayFlg;
	/** リフォームプラン名 */
	private String planName;
	/** セールスポイント */
	private String salesPoint;
	/** 総額１ */
	private String totalDtlPrice1;
	/** 総額２ */
	private String totalDtlPrice2;
	/** リフォーム詳細_番号【hidden】「配列」 */
	private String[] reformNoHidden;
	/** リフォーム詳細_項目名称「配列」 */
	private String[] reformImgName;
	/** リフォーム詳細_画像パス名【hidden】「配列」 */
	private String[] reformPathName;
	/** リフォーム詳細_項目リフォーム価格「配列」 */
	private String[] reformPrice;
	/** 工期 */
	private String constructionPeriod;
	/** リフォーム 備考 */
	private String reformNote;
	/** リフォームイメージの表示フラグ */
	private boolean reformImgDisplayFlg;
	/** リフォーム後_外観・周辺写真番号【hidden】「配列」 */
	private String[] afterPathNoHidden;
	/** リフォーム後_外観・周辺写真パス１【hidden】「配列」 */
	private String[] afterPath1;
	/** リフォーム後_外観・周辺写真パス２【hidden】「配列」 */
	private String[] afterPath2;
	/** リフォーム後_外観・周辺写真 コメント【hidden】「配列」 */
	private String[] afterPathComment;
	/** リフォーム後_動画用サムネイル【hidden】 */
	private String afterMovieUrl;
	/** リフォーム前_外観・周辺写真番号【hidden】「配列」 */
	private String[] beforePathNoHidden;
	/** リフォーム前_外観・周辺写真パス１【hidden】「配列」 */
	private String[] beforePath1;
	/** リフォーム前_外観・周辺写真パス２【hidden】「配列」 */
	private String[] beforePath2;
	/** リフォーム前_外観・周辺写真 コメント【hidden】「配列」 */
	private String[] beforePathComment;
	/** リフォーム前_動画用サムネイル【hidden】 */
	private String beforeMovieUrl;
	/** 住宅診断情報の表示フラグ */
	private boolean housingInspectionDisplayFlg;
	/** 住宅診断実施状況 */
	private String inspectionExist;
	/** 確認レベル番号【hidden】「配列」 */
	private String[] inspectionNoHidden;
	/** 確認レベル（名称）「配列」 */
	private String[] inspectionKey;
	/** 確認レベル「配列」 */
	private String[] inspectionTrust;
	/** 住宅診断情報図【hidden】 */
	private String inspectionImagePathName;
	/** 住宅診断ファイル【hidden】 */
	private String inspectionPathName;
	/** その他のリフォームプランの表示フラグ */
	private boolean otherReformPlanDisplayFlg;
	/** その他のプラン番号【hidden】「配列」 */
	private String[] otherPlanNoHidden;
	/** その他のプランタイプ「配列」 */
	private String[] otherPlanType;
	/** その他の総額１「配列」 */
	private String[] otherTotalPrice1;
	/** その他の総額２「配列」 */
	private String[] otherTotalPrice2;
	/** その他のシステムリフォームCD【hidden】「配列」 */
	private String[] otherReformCdHidden;
	/** その他のシステムリフォームURL【hidden】「配列」 */
	private String[] otherReformUrl;
	/** 最近見た物件の表示フラグ */
	private boolean recentlyDisplayFlg;
	/** 最近 物件番号【hidden】「配列」 */
	private String[] recentlyNoHidden;
	/** 最近 システム物件CD【hidden】「配列」 */
	private String[] recentlySysHousingCdHidden;
	/** 最近 物件番号【data-number】「配列」 */
	private String[] recentlyHousingCdHidden;
	/** 最近 物件画像「配列」 */
	private String[] recentlyPathName;
	/** 最近 物件種類CD「配列」 */
	private String[] recentlyHousingKindCd;
	/** 最近 物件名「配列」 */
	private String[] recentlyDisplayHousingName;
	/** 最近 物件名FULL「配列」 */
	private String[] recentlyDisplayHousingNameFull;
	/** 最近 物件詳細「配列」 */
	private String[] recentlyDtl;
	/** 最近 物件詳細FULL「配列」 */
	private String[] recentlyDtlFull;
	/** 最近 物件URL「配列」 */
	private String[] recentlyUrl;
	/** キーワード */
	private String keywords;
	/** 説明 */
	private String description;
	/** 本ページURL */
	private String currentUrl;
	/** 再検索URL「配列」 */
	private String[] researchUrl;
	/** 再検索都道府県名「配列」 */
	private String[] researchPrefName;

	/**
	 * 共通コード変換処理 を取得する。<br/>
	 * <br/>
	 * @return 共通コード変換処理
	 */
	public CodeLookupManager getCodeLookupManager() {
		return codeLookupManager;
	}

	/**
	 * 共通コード変換処理 を設定する。<br/>
	 * <br/>
	 * @param codeLookupManager
	 */
	public void setCodeLookupManager(CodeLookupManager codeLookupManager) {
		this.codeLookupManager = codeLookupManager;
	}

	/**
	 * 共通パラメータ情報 を取得する。<br/>
	 * <br/>
	 * @return 共通パラメータ情報
	 */
	public PanaCommonParameters getCommonParameters() {
		return commonParameters;
	}

	/**
	 * 共通パラメータ情報 を設定する。<br/>
	 * <br/>
	 * @param commonParameters
	 */
	public void setCommonParameters(PanaCommonParameters commonParameters) {
		this.commonParameters = commonParameters;
	}

	/**
	 * ファイル処理関連共通Util を取得する。<br/>
	 * <br/>
	 * @return ファイル処理関連共通Util
	 */
	public PanaFileUtil getPanaFileUtil() {
		return panaFileUtil;
	}

	/**
	 * ファイル処理関連共通Util を設定する。<br/>
	 * <br/>
	 * @param panaFileUtil
	 */
	public void setPanaFileUtil(PanaFileUtil panaFileUtil) {
		this.panaFileUtil = panaFileUtil;
	}

	/**
	 * 処理モード を取得する。<br/>
	 * <br/>
	 * @return 処理モード
	 */
	public String getMode() {
		return mode;
	}

	/**
	 * 処理モード を設定する。<br/>
	 * <br/>
	 * @param mode
	 */
	public void setMode(String mode) {
		this.mode = mode;
	}

	/**
	 * プレビューフラグ を取得する。<br/>
	 * <br/>
	 * @return プレビューフラグ
	 */
	public boolean isPreviewFlg() {
		return previewFlg;
	}

	/**
	 * プレビューフラグ を設定する。<br/>
	 * <br/>
	 * @param previewFlg
	 */
	public void setPreviewFlg(boolean previewFlg) {
		this.previewFlg = previewFlg;
	}

	/**
	 * 会員フラグ を取得する。<br/>
	 * <br/>
	 * @return 会員フラグ
	 */
	public boolean isMemberFlg() {
		return memberFlg;
	}

	/**
	 * 会員フラグ を設定する。<br/>
	 * <br/>
	 * @param memberFlg
	 */
	public void setMemberFlg(boolean memberFlg) {
		this.memberFlg = memberFlg;
	}

	/**
	 * 物件種類CD を取得する。<br/>
	 * <br/>
	 * @return 物件種類CD
	 */
	public String getHousingKindCd() {
		return housingKindCd;
	}

	/**
	 * 物件種類CD を設定する。<br/>
	 * <br/>
	 * @param housingKindCd
	 */
	public void setHousingKindCd(String housingKindCd) {
		this.housingKindCd = housingKindCd;
	}

	/**
	 * リフォームCD を取得する。<br/>
	 * <br/>
	 * @return リフォームCD
	 */
	public String getReformCd() {
		return reformCd;
	}

	/**
	 * リフォームCD を設定する。<br/>
	 * <br/>
	 * @param reformCd
	 */
	public void setReformCd(String reformCd) {
		this.reformCd = reformCd;
	}

	/**
	 * タイトルの表示フラグ を取得する。<br/>
	 * <br/>
	 * @return タイトルの表示フラグ
	 */
	public boolean isTitleDisplayFlg() {
		return titleDisplayFlg;
	}

	/**
	 * タイトルの表示フラグ を設定する。<br/>
	 * <br/>
	 * @param titleDisplayFlg
	 */
	public void setTitleDisplayFlg(boolean titleDisplayFlg) {
		this.titleDisplayFlg = titleDisplayFlg;
	}

	/**
	 * 新着フラグ を取得する。<br/>
	 * <br/>
	 * @return 新着フラグ
	 */
	public boolean isFreshFlg() {
		return freshFlg;
	}

	/**
	 * 新着フラグ を設定する。<br/>
	 * <br/>
	 * @param freshFlg
	 */
	public void setFreshFlg(boolean freshFlg) {
		this.freshFlg = freshFlg;
	}

	/**
	 * 詳細コメント を取得する。<br/>
	 * <br/>
	 * @return 詳細コメント
	 */
	public String getDtlComment() {
		return dtlComment;
	}

	/**
	 * 詳細コメント を設定する。<br/>
	 * <br/>
	 * @param dtlComment
	 */
	public void setDtlComment(String dtlComment) {
		this.dtlComment = dtlComment;
	}

	/**
	 * システム物件CD を取得する。<br/>
	 * <br/>
	 * @return システム物件CD
	 */
	public String getSysHousingCd() {
		return sysHousingCd;
	}

	/**
	 * システム物件CD を設定する。<br/>
	 * <br/>
	 * @param sysHousingCd
	 */
	public void setSysHousingCd(String sysHousingCd) {
		this.sysHousingCd = sysHousingCd;
	}

	/**
	 * 物件番号 を取得する。<br/>
	 * <br/>
	 * @return 物件番号
	 */
	public String getHousingCd() {
		return housingCd;
	}

	/**
	 * 物件番号 を設定する。<br/>
	 * <br/>
	 * @param housingCd
	 */
	public void setHousingCd(String housingCd) {
		this.housingCd = housingCd;
	}

	/**
	 * 物件名 を取得する。<br/>
	 * <br/>
	 * @return 物件名
	 */
	public String getDisplayHousingName() {
		return displayHousingName;
	}

	/**
	 * 物件名 を設定する。<br/>
	 * <br/>
	 * @param displayHousingName
	 */
	public void setDisplayHousingName(String displayHousingName) {
		this.displayHousingName = displayHousingName;
	}

	/**
	 * この物件はおすすめリフォームプランがありますフラグ を取得する。<br/>
	 * <br/>
	 * @return この物件はおすすめリフォームプランがありますフラグ
	 */
	public boolean isReformPlanExists() {
		return reformPlanExists;
	}

	/**
	 * この物件はおすすめリフォームプランがありますフラグ を設定する。<br/>
	 * <br/>
	 * @param reformPlanExists
	 */
	public void setReformPlanExists(boolean reformPlanExists) {
		this.reformPlanExists = reformPlanExists;
	}

	/**
	 * 物件情報の表示フラグ を取得する。<br/>
	 * <br/>
	 * @return 物件情報の表示フラグ
	 */
	public boolean isHousingInfoDisplayFlg() {
		return housingInfoDisplayFlg;
	}

	/**
	 * 物件情報の表示フラグ を設定する。<br/>
	 * <br/>
	 * @param housingInfoDisplayFlg
	 */
	public void setHousingInfoDisplayFlg(boolean housingInfoDisplayFlg) {
		this.housingInfoDisplayFlg = housingInfoDisplayFlg;
	}

	/**
	 * アイコン情報【hidden】「配列」 を取得する。<br/>
	 * <br/>
	 * @return アイコン情報【hidden】「配列」
	 */
	public String[] getIconCd() {
		return iconCd;
	}

	/**
	 * アイコン情報【hidden】「配列」 を設定する。<br/>
	 * <br/>
	 * @param iconCd
	 */
	public void setIconCd(String[] iconCd) {
		this.iconCd = iconCd;
	}

	/**
	 * 物件価格 を取得する。<br/>
	 * <br/>
	 * @return 物件価格
	 */
	public String getPrice() {
		return price;
	}

	/**
	 * 物件価格 を設定する。<br/>
	 * <br/>
	 * @param price
	 */
	public void setPrice(String price) {
		this.price = price;
	}

	/**
	 * 物件価格【hidden】 を取得する。<br/>
	 * <br/>
	 * @return 物件価格【hidden】
	 */
	public String getPriceHidden() {
		return priceHidden;
	}

	/**
	 * 物件価格【hidden】 を設定する。<br/>
	 * <br/>
	 * @param priceHidden
	 */
	public void setPriceHidden(String priceHidden) {
		this.priceHidden = priceHidden;
	}

	/**
	 * 都道府県名 を取得する。<br/>
	 * <br/>
	 * @return 都道府県名
	 */
	public String getPrefName() {
		return prefName;
	}

	/**
	 * 都道府県名 を設定する。<br/>
	 * <br/>
	 * @param prefName
	 */
	public void setPrefName(String prefName) {
		this.prefName = prefName;
	}

	/**
	 * 所在地 を取得する。<br/>
	 * <br/>
	 * @return 所在地
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * 所在地 を設定する。<br/>
	 * <br/>
	 * @param address
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * アクセス「配列」 を取得する。<br/>
	 * <br/>
	 * @return アクセス「配列」
	 */
	public String[] getAccess() {
		return access;
	}

	/**
	 * アクセス「配列」 を設定する。<br/>
	 * <br/>
	 * @param access
	 */
	public void setAccess(String[] access) {
		this.access = access;
	}

	/**
	 * マンション面積フラグ を取得する。<br/>
	 * <br/>
	 * @return マンション面積フラグ
	 */
	public boolean isPersonalAreaFlg() {
		return personalAreaFlg;
	}

	/**
	 * マンション面積フラグ を設定する。<br/>
	 * <br/>
	 * @param personalAreaFlg
	 */
	public void setPersonalAreaFlg(boolean personalAreaFlg) {
		this.personalAreaFlg = personalAreaFlg;
	}

	/**
	 * 間取CD を取得する。<br/>
	 * <br/>
	 * @return 間取CD
	 */
	public String getLayoutCd() {
		return layoutCd;
	}

	/**
	 * 間取CD を設定する。<br/>
	 * <br/>
	 * @param layoutCd
	 */
	public void setLayoutCd(String layoutCd) {
		this.layoutCd = layoutCd;
	}

	/**
	 * 専有面積 を取得する。<br/>
	 * <br/>
	 * @return 専有面積
	 */
	public String getPersonalArea() {
		return personalArea;
	}

	/**
	 * 専有面積 を設定する。<br/>
	 * <br/>
	 * @param personalArea
	 */
	public void setPersonalArea(String personalArea) {
		this.personalArea = personalArea;
	}

	/**
	 * 専有面積 坪 を取得する。<br/>
	 * <br/>
	 * @return 専有面積 坪
	 */
	public String getPersonalAreaSquare() {
		return personalAreaSquare;
	}

	/**
	 * 専有面積 坪 を設定する。<br/>
	 * <br/>
	 * @param personalAreaSquare
	 */
	public void setPersonalAreaSquare(String personalAreaSquare) {
		this.personalAreaSquare = personalAreaSquare;
	}

	/**
	 * 専有面積_補足 を取得する。<br/>
	 * <br/>
	 * @return 専有面積_補足
	 */
	public String getPersonalAreaMemo() {
		return personalAreaMemo;
	}

	/**
	 * 専有面積_補足 を設定する。<br/>
	 * <br/>
	 * @param personalAreaMemo
	 */
	public void setPersonalAreaMemo(String personalAreaMemo) {
		this.personalAreaMemo = personalAreaMemo;
	}

	/**
	 * 戸建 土地面積フラグ を取得する。<br/>
	 * <br/>
	 * @return 戸建 土地面積フラグ
	 */
	public boolean isBuildingAreaFlg() {
		return buildingAreaFlg;
	}

	/**
	 * 戸建 土地面積フラグ を設定する。<br/>
	 * <br/>
	 * @param buildingAreaFlg
	 */
	public void setBuildingAreaFlg(boolean buildingAreaFlg) {
		this.buildingAreaFlg = buildingAreaFlg;
	}

	/**
	 * 建物面積 を取得する。<br/>
	 * <br/>
	 * @return 建物面積
	 */
	public String getBuildingArea() {
		return buildingArea;
	}

	/**
	 * 建物面積 を設定する。<br/>
	 * <br/>
	 * @param buildingArea
	 */
	public void setBuildingArea(String buildingArea) {
		this.buildingArea = buildingArea;
	}

	/**
	 * 建物面積 坪 を取得する。<br/>
	 * <br/>
	 * @return 建物面積 坪
	 */
	public String getBuildingAreaSquare() {
		return buildingAreaSquare;
	}

	/**
	 * 建物面積 坪 を設定する。<br/>
	 * <br/>
	 * @param buildingAreaSquare
	 */
	public void setBuildingAreaSquare(String buildingAreaSquare) {
		this.buildingAreaSquare = buildingAreaSquare;
	}

	/**
	 * 建物面積_補足 を取得する。<br/>
	 * <br/>
	 * @return 建物面積_補足
	 */
	public String getBuildingAreaMemo() {
		return buildingAreaMemo;
	}

	/**
	 * 建物面積_補足 を設定する。<br/>
	 * <br/>
	 * @param buildingAreaMemo
	 */
	public void setBuildingAreaMemo(String buildingAreaMemo) {
		this.buildingAreaMemo = buildingAreaMemo;
	}

	/**
	 * 土地面積 を取得する。<br/>
	 * <br/>
	 * @return 土地面積
	 */
	public String getLandArea() {
		return landArea;
	}

	/**
	 * 土地面積 を設定する。<br/>
	 * <br/>
	 * @param landArea
	 */
	public void setLandArea(String landArea) {
		this.landArea = landArea;
	}

	/**
	 * 土地面積 坪 を取得する。<br/>
	 * <br/>
	 * @return 土地面積 坪
	 */
	public String getLandAreaSquare() {
		return landAreaSquare;
	}

	/**
	 * 土地面積 坪 を設定する。<br/>
	 * <br/>
	 * @param landAreaSquare
	 */
	public void setLandAreaSquare(String landAreaSquare) {
		this.landAreaSquare = landAreaSquare;
	}

	/**
	 * 土地面積_補足 を取得する。<br/>
	 * <br/>
	 * @return 土地面積_補足
	 */
	public String getLandAreaMemo() {
		return landAreaMemo;
	}

	/**
	 * 土地面積_補足 を設定する。<br/>
	 * <br/>
	 * @param landAreaMemo
	 */
	public void setLandAreaMemo(String landAreaMemo) {
		this.landAreaMemo = landAreaMemo;
	}

	/**
	 * 築年月 を取得する。<br/>
	 * <br/>
	 * @return 築年月
	 */
	public String getCompDate() {
		return compDate;
	}

	/**
	 * 築年月 を設定する。<br/>
	 * <br/>
	 * @param compDate
	 */
	public void setCompDate(String compDate) {
		this.compDate = compDate;
	}

	/**
	 * 階建／所在階 を取得する。<br/>
	 * <br/>
	 * @return 階建／所在階
	 */
	public String getFloor() {
		return floor;
	}

	/**
	 * 階建／所在階 を設定する。<br/>
	 * <br/>
	 * @param floor
	 */
	public void setFloor(String floor) {
		this.floor = floor;
	}

	/**
	 * 階建／所在階フラグ を取得する。<br/>
	 * <br/>
	 * @return 階建／所在階フラグ
	 */
	public boolean isFloorFlg() {
		return floorFlg;
	}

	/**
	 * 階建／所在階フラグ を設定する。<br/>
	 * <br/>
	 * @param floorFlg
	 */
	public void setFloorFlg(boolean floorFlg) {
		this.floorFlg = floorFlg;
	}

	/**
	 * 周辺地図 を取得する。<br/>
	 * <br/>
	 * @return 周辺地図
	 */
	public String getMapUrl() {
		return mapUrl;
	}

	/**
	 * 周辺地図 を設定する。<br/>
	 * <br/>
	 * @param mapUrl
	 */
	public void setMapUrl(String mapUrl) {
		this.mapUrl = mapUrl;
	}

	/**
	 * 物件のリフォームプランの表示フラグ を取得する。<br/>
	 * <br/>
	 * @return 物件のリフォームプランの表示フラグ
	 */
	public boolean isReformPlanDisplayFlg() {
		return reformPlanDisplayFlg;
	}

	/**
	 * 物件のリフォームプランの表示フラグ を設定する。<br/>
	 * <br/>
	 * @param reformPlanDisplayFlg
	 */
	public void setReformPlanDisplayFlg(boolean reformPlanDisplayFlg) {
		this.reformPlanDisplayFlg = reformPlanDisplayFlg;
	}

	/**
	 * リフォームプラン準備中文言の表示フラグ を取得する。<br/>
	 * <br/>
	 * @return リフォームプラン準備中文言の表示フラグ
	 */
	public boolean isReformPlanReadyDisplayFlg() {
		return reformPlanReadyDisplayFlg;
	}

	/**
	 * リフォームプラン準備中文言の表示フラグ を設定する。<br/>
	 * <br/>
	 * @param reformPlanReadyDisplayFlg
	 */
	public void setReformPlanReadyDisplayFlg(boolean reformPlanReadyDisplayFlg) {
		this.reformPlanReadyDisplayFlg = reformPlanReadyDisplayFlg;
	}

	/**
	 * リフォームプラン準備中文言 を取得する。<br/>
	 * <br/>
	 * @return リフォームプラン準備中文言
	 */
	public String getReformPlanReadyComment() {
		return reformPlanReadyComment;
	}

	/**
	 * リフォームプラン準備中文言 を設定する。<br/>
	 * <br/>
	 * @param reformPlanReadyComment
	 */
	public void setReformPlanReadyComment(String reformPlanReadyComment) {
		this.reformPlanReadyComment = reformPlanReadyComment;
	}

	/**
	 * プラン番号【hidden】「配列」 を取得する。<br/>
	 * <br/>
	 * @return プラン番号【hidden】「配列」
	 */
	public String[] getPlanNoHidden() {
		return planNoHidden;
	}

	/**
	 * プラン番号【hidden】「配列」 を設定する。<br/>
	 * <br/>
	 * @param planNoHidden
	 */
	public void setPlanNoHidden(String[] planNoHidden) {
		this.planNoHidden = planNoHidden;
	}

	/**
	 * プランタイプ「配列」 を取得する。<br/>
	 * <br/>
	 * @return プランタイプ「配列」
	 */
	public String[] getPlanType() {
		return planType;
	}

	/**
	 * プランタイプ「配列」 を設定する。<br/>
	 * <br/>
	 * @param planType
	 */
	public void setPlanType(String[] planType) {
		this.planType = planType;
	}

	/**
	 * リフォーム価格「配列」 を取得する。<br/>
	 * <br/>
	 * @return リフォーム価格「配列」
	 */
	public String[] getPlanPrice() {
		return planPrice;
	}

	/**
	 * リフォーム価格「配列」 を設定する。<br/>
	 * <br/>
	 * @param planPrice
	 */
	public void setPlanPrice(String[] planPrice) {
		this.planPrice = planPrice;
	}

	/**
	 * 総額１「配列」 を取得する。<br/>
	 * <br/>
	 * @return 総額１「配列」
	 */
	public String[] getTotalPrice1() {
		return totalPrice1;
	}

	/**
	 * 総額１「配列」 を設定する。<br/>
	 * <br/>
	 * @param totalPrice1
	 */
	public void setTotalPrice1(String[] totalPrice1) {
		this.totalPrice1 = totalPrice1;
	}

	/**
	 * 総額２「配列」 を取得する。<br/>
	 * <br/>
	 * @return 総額２「配列」
	 */
	public String[] getTotalPrice2() {
		return totalPrice2;
	}

	/**
	 * 総額２「配列」 を設定する。<br/>
	 * <br/>
	 * @param totalPrice2
	 */
	public void setTotalPrice2(String[] totalPrice2) {
		this.totalPrice2 = totalPrice2;
	}

	/**
	 * システムリフォームCD【hidden】「配列」 を取得する。<br/>
	 * <br/>
	 * @return システムリフォームCD【hidden】「配列」
	 */
	public String[] getReformCdHidden() {
		return reformCdHidden;
	}

	/**
	 * システムリフォームCD【hidden】「配列」 を設定する。<br/>
	 * <br/>
	 * @param reformCdHidden
	 */
	public void setReformCdHidden(String[] reformCdHidden) {
		this.reformCdHidden = reformCdHidden;
	}

	/**
	 * システムリフォームURL【hidden】「配列」 を取得する。<br/>
	 * <br/>
	 * @return システムリフォームURL【hidden】「配列」
	 */
	public String[] getReformUrl() {
		return reformUrl;
	}

	/**
	 * システムリフォームURL【hidden】「配列」 を設定する。<br/>
	 * <br/>
	 * @param reformUrl
	 */
	public void setReformUrl(String[] reformUrl) {
		this.reformUrl = reformUrl;
	}

	public void setReformCategory(String[] reformCategory){
	    this.reformCategory = reformCategory;
	}
	/**
     * @return the reformCategory
     */
    public String[] getReformCategory() {
        return reformCategory;
    }

    /**
	 * 画像の表示フラグ を取得する。<br/>
	 * <br/>
	 * @return 画像の表示フラグ
	 */
	public boolean isImgDisplayFlg() {
		return imgDisplayFlg;
	}

	/**
	 * 画像の表示フラグ を設定する。<br/>
	 * <br/>
	 * @param imgDisplayFlg
	 */
	public void setImgDisplayFlg(boolean imgDisplayFlg) {
		this.imgDisplayFlg = imgDisplayFlg;
	}

	/**
	 * 画像番号【hidden】「配列」 を取得する。<br/>
	 * <br/>
	 * @return 画像番号【hidden】「配列」
	 */
	public String[] getImgNoHidden() {
		return imgNoHidden;
	}

	/**
	 * 画像番号【hidden】「配列」 を設定する。<br/>
	 * <br/>
	 * @param imgNoHidden
	 */
	public void setImgNoHidden(String[] imgNoHidden) {
		this.imgNoHidden = imgNoHidden;
	}

	/**
	 * パス名１【hidden】「配列」 を取得する。<br/>
	 * <br/>
	 * @return パス名１【hidden】「配列」
	 */
	public String[] getHousingImgPath1Hidden() {
		return housingImgPath1Hidden;
	}

	/**
	 * パス名１【hidden】「配列」 を設定する。<br/>
	 * <br/>
	 * @param housingImgPath1Hidden
	 */
	public void setHousingImgPath1Hidden(String[] housingImgPath1Hidden) {
		this.housingImgPath1Hidden = housingImgPath1Hidden;
	}

	/**
	 * パス名２【hidden】「配列」 を取得する。<br/>
	 * <br/>
	 * @return パス名２【hidden】「配列」
	 */
	public String[] getHousingImgPath2Hidden() {
		return housingImgPath2Hidden;
	}

	/**
	 * パス名２【hidden】「配列」 を設定する。<br/>
	 * <br/>
	 * @param housingImgPath2Hidden
	 */
	public void setHousingImgPath2Hidden(String[] housingImgPath2Hidden) {
		this.housingImgPath2Hidden = housingImgPath2Hidden;
	}

	/**
	 * 動画パス【hidden】 を取得する。<br/>
	 * <br/>
	 * @return 動画パス【hidden】
	 */
	public String getMovieUrl() {
		return movieUrl;
	}

	/**
	 * 動画パス【hidden】 を設定する。<br/>
	 * <br/>
	 * @param movieUrl
	 */
	public void setMovieUrl(String movieUrl) {
		this.movieUrl = movieUrl;
	}

	/**
	 * コメント【hidden】「配列」 を取得する。<br/>
	 * <br/>
	 * @return コメント【hidden】「配列」
	 */
	public String[] getHousingImgCommentHidden() {
		return housingImgCommentHidden;
	}

	/**
	 * コメント【hidden】「配列」 を設定する。<br/>
	 * <br/>
	 * @param housingImgCommentHidden
	 */
	public void setHousingImgCommentHidden(String[] housingImgCommentHidden) {
		this.housingImgCommentHidden = housingImgCommentHidden;
	}

	/**
	 * お気に入り登録フラグの表示フラグ を取得する。<br/>
	 * <br/>
	 * @return お気に入り登録フラグの表示フラグ
	 */
	public boolean isFavoriteDisplayFlg() {
		return favoriteDisplayFlg;
	}

	/**
	 * お気に入り登録フラグの表示フラグ を設定する。<br/>
	 * <br/>
	 * @param favoriteDisplayFlg
	 */
	public void setFavoriteDisplayFlg(boolean favoriteDisplayFlg) {
		this.favoriteDisplayFlg = favoriteDisplayFlg;
	}

	/**
	 * 売主のコメントの表示フラグ を取得する。<br/>
	 * <br/>
	 * @return 売主のコメントの表示フラグ
	 */
	public boolean isSalesCommentDisplayFlg() {
		return salesCommentDisplayFlg;
	}

	/**
	 * 売主のコメントの表示フラグ を設定する。<br/>
	 * <br/>
	 * @param salesCommentDisplayFlg
	 */
	public void setSalesCommentDisplayFlg(boolean salesCommentDisplayFlg) {
		this.salesCommentDisplayFlg = salesCommentDisplayFlg;
	}

	/**
	 * 売主のコメント を取得する。<br/>
	 * <br/>
	 * @return 売主のコメント
	 */
	public String getSalesComment() {
		return salesComment;
	}

	/**
	 * 売主のコメント を設定する。<br/>
	 * <br/>
	 * @param salesComment
	 */
	public void setSalesComment(String salesComment) {
		this.salesComment = salesComment;
	}

	/**
	 * 担当者からのおすすめの表示フラグ を取得する。<br/>
	 * <br/>
	 * @return 担当者からのおすすめの表示フラグ
	 */
	public boolean isRecommendDisplayFlg() {
		return recommendDisplayFlg;
	}

	/**
	 * 担当者からのおすすめの表示フラグ を設定する。<br/>
	 * <br/>
	 * @param recommendDisplayFlg
	 */
	public void setRecommendDisplayFlg(boolean recommendDisplayFlg) {
		this.recommendDisplayFlg = recommendDisplayFlg;
	}

	/**
	 * おすすめ画像パス【hidden】 を取得する。<br/>
	 * <br/>
	 * @return おすすめ画像パス【hidden】
	 */
	public String getStaffimagePathName() {
		return staffImagePathName;
	}

	/**
	 * おすすめ画像パス【hidden】 を設定する。<br/>
	 * <br/>
	 * @param staffImagePathName
	 */
	public void setStaffimagePathName(String staffImagePathName) {
		this.staffImagePathName = staffImagePathName;
	}

	/**
	 * おすすめ内容 を取得する。<br/>
	 * <br/>
	 * @return おすすめ内容
	 */
	public String getRecommendComment() {
		return recommendComment;
	}

	/**
	 * おすすめ内容 を設定する。<br/>
	 * <br/>
	 * @param recommendComment
	 */
	public void setRecommendComment(String recommendComment) {
		this.recommendComment = recommendComment;
	}

	/**
	 * 担当 を取得する。<br/>
	 * <br/>
	 * @return 担当
	 */
	public String getStaffName() {
		return staffName;
	}

	/**
	 * 担当 を設定する。<br/>
	 * <br/>
	 * @param staffName
	 */
	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	/**
	 * 会社名 を取得する。<br/>
	 * <br/>
	 * @return 会社名
	 */
	public String getCompanyName() {
		return companyName;
	}

	/**
	 * 会社名 を設定する。<br/>
	 * <br/>
	 * @param companyName
	 */
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	/**
	 * 支店名 を取得する。<br/>
	 * <br/>
	 * @return 支店名
	 */
	public String getBranchName() {
		return branchName;
	}

	/**
	 * 支店名 を設定する。<br/>
	 * <br/>
	 * @param branchName
	 */
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	/**
	 * 免許番号 を取得する。<br/>
	 * <br/>
	 * @return 免許番号
	 */
	public String getLicenseNo() {
		return licenseNo;
	}

	/**
	 * 免許番号 を設定する。<br/>
	 * <br/>
	 * @param licenseNo
	 */
	public void setLicenseNo(String licenseNo) {
		this.licenseNo = licenseNo;
	}

	/**
	 * 内観画像フラグ を取得する。<br/>
	 * <br/>
	 * @return 内観画像フラグ
	 */
	public boolean isIntrospectImgFlg() {
		return introspectImgFlg;
	}

	/**
	 * 内観画像フラグ を設定する。<br/>
	 * <br/>
	 * @param introspectImgFlg
	 */
	public void setIntrospectImgFlg(boolean introspectImgFlg) {
		this.introspectImgFlg = introspectImgFlg;
	}

	/**
	 * 会員登録の表示フラグ を取得する。<br/>
	 * <br/>
	 * @return 会員登録の表示フラグ
	 */
	public boolean isLoginDisplayFlg() {
		return loginDisplayFlg;
	}

	/**
	 * 会員登録の表示フラグ を設定する。<br/>
	 * <br/>
	 * @param loginDisplayFlg
	 */
	public void setLoginDisplayFlg(boolean loginDisplayFlg) {
		this.loginDisplayFlg = loginDisplayFlg;
	}

	/**
	 * 物件詳細情報の表示フラグ を取得する。<br/>
	 * <br/>
	 * @return 物件詳細情報の表示フラグ
	 */
	public boolean isHousingDtlInfoDisplayFlg() {
		return housingDtlInfoDisplayFlg;
	}

	/**
	 * 物件詳細情報の表示フラグ を設定する。<br/>
	 * <br/>
	 * @param housingDtlInfoDisplayFlg
	 */
	public void setHousingDtlInfoDisplayFlg(boolean housingDtlInfoDisplayFlg) {
		this.housingDtlInfoDisplayFlg = housingDtlInfoDisplayFlg;
	}

	/**
	 * インフラ を取得する。<br/>
	 * <br/>
	 * @return インフラ
	 */
	public String getInfrastructure() {
		return infrastructure;
	}

	/**
	 * インフラ を設定する。<br/>
	 * <br/>
	 * @param infrastructure
	 */
	public void setInfrastructure(String infrastructure) {
		this.infrastructure = infrastructure;
	}

	/**
	 * 建ぺい率 を取得する。<br/>
	 * <br/>
	 * @return 建ぺい率
	 */
	public String getCoverage() {
		return coverage;
	}

	/**
	 * 建ぺい率 を設定する。<br/>
	 * <br/>
	 * @param coverage
	 */
	public void setCoverage(String coverage) {
		this.coverage = coverage;
	}

	/**
	 * バルコニー面積 を取得する。<br/>
	 * <br/>
	 * @return バルコニー面積
	 */
	public String getBalconyArea() {
		return balconyArea;
	}

	/**
	 * バルコニー面積 を設定する。<br/>
	 * <br/>
	 * @param balconyArea
	 */
	public void setBalconyArea(String balconyArea) {
		this.balconyArea = balconyArea;
	}

	/**
	 * 物件詳細情報 備考 を取得する。<br/>
	 * <br/>
	 * @return 物件詳細情報 備考
	 */
	public String getUpkeepCorp() {
		return upkeepCorp;
	}

	/**
	 * 物件詳細情報 備考 を設定する。<br/>
	 * <br/>
	 * @param upkeepCorp
	 */
	public void setUpkeepCorp(String upkeepCorp) {
		this.upkeepCorp = upkeepCorp;
	}

	/**
	 * 引渡し を取得する。<br/>
	 * <br/>
	 * @return 引渡し
	 */
	public String getMoveinTiming() {
		return moveinTiming;
	}

	/**
	 * 引渡し を設定する。<br/>
	 * <br/>
	 * @param moveinTiming
	 */
	public void setMoveinTiming(String moveinTiming) {
		this.moveinTiming = moveinTiming;
	}

	/**
	 * 引渡時期コメント を取得する。<br/>
	 * <br/>
	 * @return 引渡時期コメント
	 */
	public String getMoveinNote() {
		return moveinNote;
	}

	/**
	 * 引渡時期コメント を設定する。<br/>
	 * <br/>
	 * @param moveinNote
	 */
	public void setMoveinNote(String moveinNote) {
		this.moveinNote = moveinNote;
	}

	/**
	 * 管理形態 を取得する。<br/>
	 * <br/>
	 * @return 管理形態
	 */
	public String getUpkeepType() {
		return upkeepType;
	}

	/**
	 * 管理形態 を設定する。<br/>
	 * <br/>
	 * @param upkeepType
	 */
	public void setUpkeepType(String upkeepType) {
		this.upkeepType = upkeepType;
	}

	/**
	 * 管理費等 を取得する。<br/>
	 * <br/>
	 * @return 管理費等
	 */
	public String getUpkeep() {
		return upkeep;
	}

	/**
	 * 管理費等 を設定する。<br/>
	 * <br/>
	 * @param upkeep
	 */
	public void setUpkeep(String upkeep) {
		this.upkeep = upkeep;
	}

	/**
	 * 規模 を取得する。<br/>
	 * <br/>
	 * @return 規模
	 */
	public String getScale() {
		return scale;
	}

	/**
	 * 規模 を設定する。<br/>
	 * <br/>
	 * @param scale
	 */
	public void setScale(String scale) {
		this.scale = scale;
	}

	/**
	 * 現況 を取得する。<br/>
	 * <br/>
	 * @return 現況
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * 現況 を設定する。<br/>
	 * <br/>
	 * @param status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * 更新日 を取得する。<br/>
	 * <br/>
	 * @return 更新日
	 */
	public String getUpdDate() {
		return updDate;
	}

	/**
	 * 更新日 を設定する。<br/>
	 * <br/>
	 * @param updDate
	 */
	public void setUpdDate(String updDate) {
		this.updDate = updDate;
	}

	/**
	 * 次回更新予定 を取得する。<br/>
	 * <br/>
	 * @return 次回更新予定
	 */
	public String getNextUpdDate() {
		return nextUpdDate;
	}

	/**
	 * 次回更新予定 を設定する。<br/>
	 * <br/>
	 * @param nextUpdDate
	 */
	public void setNextUpdDate(String nextUpdDate) {
		this.nextUpdDate = nextUpdDate;
	}

	/**
	 * 構造 を取得する。<br/>
	 * <br/>
	 * @return 構造
	 */
	public String getStruct() {
		return struct;
	}

	/**
	 * 構造 を設定する。<br/>
	 * <br/>
	 * @param struct
	 */
	public void setStruct(String struct) {
		this.struct = struct;
	}

	/**
	 * 私道負担 を取得する。<br/>
	 * <br/>
	 * @return 私道負担
	 */
	public String getPrivateRoad() {
		return privateRoad;
	}

	/**
	 * 私道負担 を設定する。<br/>
	 * <br/>
	 * @param privateRoad
	 */
	public void setPrivateRoad(String privateRoad) {
		this.privateRoad = privateRoad;
	}

	/**
	 * 主要採光 を取得する。<br/>
	 * <br/>
	 * @return 主要採光
	 */
	public String getDirection() {
		return direction;
	}

	/**
	 * 主要採光 を設定する。<br/>
	 * <br/>
	 * @param direction
	 */
	public void setDirection(String direction) {
		this.direction = direction;
	}

	/**
	 * 取引形態 を取得する。<br/>
	 * <br/>
	 * @return 取引形態
	 */
	public String getTransactTypeDiv() {
		return transactTypeDiv;
	}

	/**
	 * 取引形態 を設定する。<br/>
	 * <br/>
	 * @param transactTypeDiv
	 */
	public void setTransactTypeDiv(String transactTypeDiv) {
		this.transactTypeDiv = transactTypeDiv;
	}

	/**
	 * 修繕積立金 を取得する。<br/>
	 * <br/>
	 * @return 修繕積立金
	 */
	public String getMenteFee() {
		return menteFee;
	}

	/**
	 * 修繕積立金 を設定する。<br/>
	 * <br/>
	 * @param menteFee
	 */
	public void setMenteFee(String menteFee) {
		this.menteFee = menteFee;
	}

	/**
	 * 接道 を取得する。<br/>
	 * <br/>
	 * @return 接道
	 */
	public String getContactRoad() {
		return contactRoad;
	}

	/**
	 * 接道 を設定する。<br/>
	 * <br/>
	 * @param contactRoad
	 */
	public void setContactRoad(String contactRoad) {
		this.contactRoad = contactRoad;
	}

	/**
	 * 接道方向/幅員 を取得する。<br/>
	 * <br/>
	 * @return 接道方向/幅員
	 */
	public String getContactRoadDir() {
		return contactRoadDir;
	}

	/**
	 * 接道方向/幅員 を設定する。<br/>
	 * <br/>
	 * @param contactRoadDir
	 */
	public void setContactRoadDir(String contactRoadDir) {
		this.contactRoadDir = contactRoadDir;
	}

	/**
	 * 総戸数 を取得する。<br/>
	 * <br/>
	 * @return 総戸数
	 */
	public String getTotalHouseCnt() {
		return totalHouseCnt;
	}

	/**
	 * 総戸数 を設定する。<br/>
	 * <br/>
	 * @param totalHouseCnt
	 */
	public void setTotalHouseCnt(String totalHouseCnt) {
		this.totalHouseCnt = totalHouseCnt;
	}

	/**
	 * 駐車場 を取得する。<br/>
	 * <br/>
	 * @return 駐車場
	 */
	public String getParkingSituation() {
		return parkingSituation;
	}

	/**
	 * 駐車場 を設定する。<br/>
	 * <br/>
	 * @param parkingSituation
	 */
	public void setParkingSituation(String parkingSituation) {
		this.parkingSituation = parkingSituation;
	}

	/**
	 * 土地権利 を取得する。<br/>
	 * <br/>
	 * @return 土地権利
	 */
	public String getLandRight() {
		return landRight;
	}

	/**
	 * 土地権利 を設定する。<br/>
	 * <br/>
	 * @param landRight
	 */
	public void setLandRight(String landRight) {
		this.landRight = landRight;
	}

	/**
	 * 特記事項 を取得する。<br/>
	 * <br/>
	 * @return 特記事項
	 */
	public String getSpecialInstruction() {
		return specialInstruction;
	}

	/**
	 * 特記事項 を設定する。<br/>
	 * <br/>
	 * @param specialInstruction
	 */
	public void setSpecialInstruction(String specialInstruction) {
		this.specialInstruction = specialInstruction;
	}

	/**
	 * 容積率 を取得する。<br/>
	 * <br/>
	 * @return 容積率
	 */
	public String getBuildingRate() {
		return buildingRate;
	}

	/**
	 * 容積率 を設定する。<br/>
	 * <br/>
	 * @param buildingRate
	 */
	public void setBuildingRate(String buildingRate) {
		this.buildingRate = buildingRate;
	}

	/**
	 * 用途地域 を取得する。<br/>
	 * <br/>
	 * @return 用途地域
	 */
	public String getUsedAreaCd() {
		return usedAreaCd;
	}

	/**
	 * 用途地域 を設定する。<br/>
	 * <br/>
	 * @param usedAreaCd
	 */
	public void setUsedAreaCd(String usedAreaCd) {
		this.usedAreaCd = usedAreaCd;
	}

	/**
	 * 瑕疵保険 を取得する。<br/>
	 * <br/>
	 * @return 瑕疵保険
	 */
	public String getInsurExist() {
		return insurExist;
	}

	/**
	 * 瑕疵保険 を設定する。<br/>
	 * <br/>
	 * @param insurExist
	 */
	public void setInsurExist(String insurExist) {
		this.insurExist = insurExist;
	}

	/**
	 * 物件特徴の表示フラグ を取得する。<br/>
	 * <br/>
	 * @return 物件特徴の表示フラグ
	 */
	public boolean isHousingPropertyDisplayFlg() {
		return housingPropertyDisplayFlg;
	}

	/**
	 * 物件特徴の表示フラグ を設定する。<br/>
	 * <br/>
	 * @param housingPropertyDisplayFlg
	 */
	public void setHousingPropertyDisplayFlg(boolean housingPropertyDisplayFlg) {
		this.housingPropertyDisplayFlg = housingPropertyDisplayFlg;
	}

	/**
	 * 物件特徴 を取得する。<br/>
	 * <br/>
	 * @return 物件特徴
	 */
	public String getEquipName() {
		return equipName;
	}

	/**
	 * 物件特徴 を設定する。<br/>
	 * <br/>
	 * @param equipName
	 */
	public void setEquipName(String equipName) {
		this.equipName = equipName;
	}

	/**
	 * 地域情報の表示フラグ を取得する。<br/>
	 * <br/>
	 * @return 地域情報の表示フラグ
	 */
	public boolean isLandmarkDisplayFlg() {
		return landmarkDisplayFlg;
	}

	/**
	 * 地域情報の表示フラグ を設定する。<br/>
	 * <br/>
	 * @param landmarkDisplayFlg
	 */
	public void setLandmarkDisplayFlg(boolean landmarkDisplayFlg) {
		this.landmarkDisplayFlg = landmarkDisplayFlg;
	}

	/**
	 * 地域情報番号【hidden】「配列」 を取得する。<br/>
	 * <br/>
	 * @return 地域情報番号【hidden】「配列」
	 */
	public String[] getLandmarkNoHidden() {
		return landmarkNoHidden;
	}

	/**
	 * 地域情報番号【hidden】「配列」 を設定する。<br/>
	 * <br/>
	 * @param landmarkNoHidden
	 */
	public void setLandmarkNoHidden(String[] landmarkNoHidden) {
		this.landmarkNoHidden = landmarkNoHidden;
	}

	/**
	 * ランドマークの種類「配列」 を取得する。<br/>
	 * <br/>
	 * @return ランドマークの種類「配列」
	 */
	public String[] getLandmarkType() {
		return landmarkType;
	}

	/**
	 * ランドマークの種類「配列」 を設定する。<br/>
	 * <br/>
	 * @param landmarkType
	 */
	public void setLandmarkType(String[] landmarkType) {
		this.landmarkType = landmarkType;
	}

	/**
	 * 地域情報（名称）「配列」 を取得する。<br/>
	 * <br/>
	 * @return 地域情報（名称）「配列」
	 */
	public String[] getLandmarkName() {
		return landmarkName;
	}

	/**
	 * 地域情報（名称）「配列」 を設定する。<br/>
	 * <br/>
	 * @param landmarkName
	 */
	public void setLandmarkName(String[] landmarkName) {
		this.landmarkName = landmarkName;
	}

	/**
	 * 地域情報（所要時間/距離）「配列」 を取得する。<br/>
	 * <br/>
	 * @return 地域情報（所要時間/距離）「配列」
	 */
	public String[] getDistanceFromLandmark() {
		return distanceFromLandmark;
	}

	/**
	 * 地域情報（所要時間/距離）「配列」 を設定する。<br/>
	 * <br/>
	 * @param distanceFromLandmark
	 */
	public void setDistanceFromLandmark(String[] distanceFromLandmark) {
		this.distanceFromLandmark = distanceFromLandmark;
	}

	/**
	 * おすすめリフォームプラン例の表示フラグ を取得する。<br/>
	 * <br/>
	 * @return おすすめリフォームプラン例の表示フラグ
	 */
	public boolean isRecommendReformPlanDisplayFlg() {
		return recommendReformPlanDisplayFlg;
	}

	/**
	 * おすすめリフォームプラン例の表示フラグ を設定する。<br/>
	 * <br/>
	 * @param recommendReformPlanDisplayFlg
	 */
	public void setRecommendReformPlanDisplayFlg(boolean recommendReformPlanDisplayFlg) {
		this.recommendReformPlanDisplayFlg = recommendReformPlanDisplayFlg;
	}

	/**
	 * リフォームプラン名 を取得する。<br/>
	 * <br/>
	 * @return リフォームプラン名
	 */
	public String getPlanName() {
		return planName;
	}

	/**
	 * リフォームプラン名 を設定する。<br/>
	 * <br/>
	 * @param planName
	 */
	public void setPlanName(String planName) {
		this.planName = planName;
	}

	/**
	 * セールスポイント を取得する。<br/>
	 * <br/>
	 * @return セールスポイント
	 */
	public String getSalesPoint() {
		return salesPoint;
	}

	/**
	 * セールスポイント を設定する。<br/>
	 * <br/>
	 * @param salesPoint
	 */
	public void setSalesPoint(String salesPoint) {
		this.salesPoint = salesPoint;
	}

	/**
	 * 総額１ を取得する。<br/>
	 * <br/>
	 * @return 総額１
	 */
	public String getTotalDtlPrice1() {
		return totalDtlPrice1;
	}

	/**
	 * 総額１ を設定する。<br/>
	 * <br/>
	 * @param totalDtlPrice1
	 */
	public void setTotalDtlPrice1(String totalDtlPrice1) {
		this.totalDtlPrice1 = totalDtlPrice1;
	}

	/**
	 * 総額２ を取得する。<br/>
	 * <br/>
	 * @return 総額２
	 */
	public String getTotalDtlPrice2() {
		return totalDtlPrice2;
	}

	/**
	 * 総額２ を設定する。<br/>
	 * <br/>
	 * @param totalDtlPrice2
	 */
	public void setTotalDtlPrice2(String totalDtlPrice2) {
		this.totalDtlPrice2 = totalDtlPrice2;
	}

	/**
	 * リフォーム詳細_番号【hidden】「配列」 を取得する。<br/>
	 * <br/>
	 * @return リフォーム詳細_番号【hidden】「配列」
	 */
	public String[] getReformNoHidden() {
		return reformNoHidden;
	}

	/**
	 * リフォーム詳細_番号【hidden】「配列」 を設定する。<br/>
	 * <br/>
	 * @param reformNoHidden
	 */
	public void setReformNoHidden(String[] reformNoHidden) {
		this.reformNoHidden = reformNoHidden;
	}

	/**
	 * リフォーム詳細_項目名称「配列」 を取得する。<br/>
	 * <br/>
	 * @return リフォーム詳細_項目名称「配列」
	 */
	public String[] getReformImgName() {
		return reformImgName;
	}

	/**
	 * リフォーム詳細_項目名称「配列」 を設定する。<br/>
	 * <br/>
	 * @param reformImgName
	 */
	public void setReformImgName(String[] reformImgName) {
		this.reformImgName = reformImgName;
	}

	/**
	 * リフォーム詳細_画像パス名【hidden】「配列」 を取得する。<br/>
	 * <br/>
	 * @return リフォーム詳細_画像パス名【hidden】「配列」
	 */
	public String[] getReformPathName() {
		return reformPathName;
	}

	/**
	 * リフォーム詳細_画像パス名【hidden】「配列」 を設定する。<br/>
	 * <br/>
	 * @param reformPathName
	 */
	public void setReformPathName(String[] reformPathName) {
		this.reformPathName = reformPathName;
	}

	/**
	 * リフォーム詳細_項目リフォーム価格「配列」 を取得する。<br/>
	 * <br/>
	 * @return リフォーム詳細_項目リフォーム価格「配列」
	 */
	public String[] getReformPrice() {
		return reformPrice;
	}

	/**
	 * リフォーム詳細_項目リフォーム価格「配列」 を設定する。<br/>
	 * <br/>
	 * @param reformPrice
	 */
	public void setReformPrice(String[] reformPrice) {
		this.reformPrice = reformPrice;
	}

	/**
	 * 工期 を取得する。<br/>
	 * <br/>
	 * @return 工期
	 */
	public String getConstructionPeriod() {
		return constructionPeriod;
	}

	/**
	 * 工期 を設定する。<br/>
	 * <br/>
	 * @param constructionPeriod
	 */
	public void setConstructionPeriod(String constructionPeriod) {
		this.constructionPeriod = constructionPeriod;
	}

	/**
	 * リフォーム 備考 を取得する。<br/>
	 * <br/>
	 * @return リフォーム 備考
	 */
	public String getReformNote() {
		return reformNote;
	}

	/**
	 * リフォーム 備考 を設定する。<br/>
	 * <br/>
	 * @param reformNote
	 */
	public void setReformNote(String reformNote) {
		this.reformNote = reformNote;
	}

	/**
	 * リフォームイメージの表示フラグ を取得する。<br/>
	 * <br/>
	 * @return リフォームイメージの表示フラグ
	 */
	public boolean isReformImgDisplayFlg() {
		return reformImgDisplayFlg;
	}

	/**
	 * リフォームイメージの表示フラグ を設定する。<br/>
	 * <br/>
	 * @param reformImgDisplayFlg
	 */
	public void setReformImgDisplayFlg(boolean reformImgDisplayFlg) {
		this.reformImgDisplayFlg = reformImgDisplayFlg;
	}

	/**
	 * リフォーム後_外観・周辺写真番号【hidden】「配列」 を取得する。<br/>
	 * <br/>
	 * @return リフォーム後_外観・周辺写真番号【hidden】「配列」
	 */
	public String[] getAfterPathNoHidden() {
		return afterPathNoHidden;
	}

	/**
	 * リフォーム後_外観・周辺写真番号【hidden】「配列」 を設定する。<br/>
	 * <br/>
	 * @param afterPathNoHidden
	 */
	public void setAfterPathNoHidden(String[] afterPathNoHidden) {
		this.afterPathNoHidden = afterPathNoHidden;
	}

	/**
	 * リフォーム後_外観・周辺写真パス１【hidden】「配列」 を取得する。<br/>
	 * <br/>
	 * @return リフォーム後_外観・周辺写真パス１【hidden】「配列」
	 */
	public String[] getAfterPath1() {
		return afterPath1;
	}

	/**
	 * リフォーム後_外観・周辺写真パス１【hidden】「配列」 を設定する。<br/>
	 * <br/>
	 * @param afterPath1
	 */
	public void setAfterPath1(String[] afterPath1) {
		this.afterPath1 = afterPath1;
	}

	/**
	 * リフォーム後_外観・周辺写真パス２【hidden】「配列」 を取得する。<br/>
	 * <br/>
	 * @return リフォーム後_外観・周辺写真パス２【hidden】「配列」
	 */
	public String[] getAfterPath2() {
		return afterPath2;
	}

	/**
	 * リフォーム後_外観・周辺写真パス２【hidden】「配列」 を設定する。<br/>
	 * <br/>
	 * @param afterPath2
	 */
	public void setAfterPath2(String[] afterPath2) {
		this.afterPath2 = afterPath2;
	}

	/**
	 * リフォーム後_外観・周辺写真 コメント【hidden】「配列」 を取得する。<br/>
	 * <br/>
	 * @return リフォーム後_外観・周辺写真 コメント【hidden】「配列」
	 */
	public String[] getAfterPathComment() {
		return afterPathComment;
	}

	/**
	 * リフォーム後_外観・周辺写真 コメント【hidden】「配列」 を設定する。<br/>
	 * <br/>
	 * @param afterPathComment
	 */
	public void setAfterPathComment(String[] afterPathComment) {
		this.afterPathComment = afterPathComment;
	}

	/**
	 * リフォーム後_動画用サムネイル【hidden】 を取得する。<br/>
	 * <br/>
	 * @return リフォーム後_動画用サムネイル【hidden】
	 */
	public String getAfterMovieUrl() {
		return afterMovieUrl;
	}

	/**
	 * リフォーム後_動画用サムネイル【hidden】 を設定する。<br/>
	 * <br/>
	 * @param afterMovieUrl
	 */
	public void setAfterMovieUrl(String afterMovieUrl) {
		this.afterMovieUrl = afterMovieUrl;
	}

	/**
	 * リフォーム前_外観・周辺写真番号【hidden】「配列」 を取得する。<br/>
	 * <br/>
	 * @return リフォーム前_外観・周辺写真番号【hidden】「配列」
	 */
	public String[] getBeforePathNoHidden() {
		return beforePathNoHidden;
	}

	/**
	 * リフォーム前_外観・周辺写真番号【hidden】「配列」 を設定する。<br/>
	 * <br/>
	 * @param beforePathNoHidden
	 */
	public void setBeforePathNoHidden(String[] beforePathNoHidden) {
		this.beforePathNoHidden = beforePathNoHidden;
	}

	/**
	 * リフォーム前_外観・周辺写真パス１【hidden】「配列」 を取得する。<br/>
	 * <br/>
	 * @return リフォーム前_外観・周辺写真パス１【hidden】「配列」
	 */
	public String[] getBeforePath1() {
		return beforePath1;
	}

	/**
	 * リフォーム前_外観・周辺写真パス１【hidden】「配列」 を設定する。<br/>
	 * <br/>
	 * @param beforePath1
	 */
	public void setBeforePath1(String[] beforePath1) {
		this.beforePath1 = beforePath1;
	}

	/**
	 * リフォーム前_外観・周辺写真パス２【hidden】「配列」 を取得する。<br/>
	 * <br/>
	 * @return リフォーム前_外観・周辺写真パス２【hidden】「配列」
	 */
	public String[] getBeforePath2() {
		return beforePath2;
	}

	/**
	 * リフォーム前_外観・周辺写真パス２【hidden】「配列」 を設定する。<br/>
	 * <br/>
	 * @param beforePath2
	 */
	public void setBeforePath2(String[] beforePath2) {
		this.beforePath2 = beforePath2;
	}

	/**
	 * リフォーム前_外観・周辺写真 コメント【hidden】「配列」 を取得する。<br/>
	 * <br/>
	 * @return リフォーム前_外観・周辺写真 コメント【hidden】「配列」
	 */
	public String[] getBeforePathComment() {
		return beforePathComment;
	}

	/**
	 * リフォーム前_外観・周辺写真 コメント【hidden】「配列」 を設定する。<br/>
	 * <br/>
	 * @param beforePathComment
	 */
	public void setBeforePathComment(String[] beforePathComment) {
		this.beforePathComment = beforePathComment;
	}

	/**
	 * リフォーム前_動画用サムネイル【hidden】 を取得する。<br/>
	 * <br/>
	 * @return リフォーム前_動画用サムネイル【hidden】
	 */
	public String getBeforeMovieUrl() {
		return beforeMovieUrl;
	}

	/**
	 * リフォーム前_動画用サムネイル【hidden】 を設定する。<br/>
	 * <br/>
	 * @param beforeMovieUrl
	 */
	public void setBeforeMovieUrl(String beforeMovieUrl) {
		this.beforeMovieUrl = beforeMovieUrl;
	}

	/**
	 * 住宅診断情報の表示フラグ を取得する。<br/>
	 * <br/>
	 * @return 住宅診断情報の表示フラグ
	 */
	public boolean isHousingInspectionDisplayFlg() {
		return housingInspectionDisplayFlg;
	}

	/**
	 * 住宅診断情報の表示フラグ を設定する。<br/>
	 * <br/>
	 * @param housingInspectionDisplayFlg
	 */
	public void setHousingInspectionDisplayFlg(boolean housingInspectionDisplayFlg) {
		this.housingInspectionDisplayFlg = housingInspectionDisplayFlg;
	}

	/**
	 * 住宅診断実施状況 を取得する。<br/>
	 * <br/>
	 * @return 住宅診断実施状況
	 */
	public String getInspectionExist() {
		return inspectionExist;
	}

	/**
	 * 住宅診断実施状況 を設定する。<br/>
	 * <br/>
	 * @param inspectionExist
	 */
	public void setInspectionExist(String inspectionExist) {
		this.inspectionExist = inspectionExist;
	}

	/**
	 * 確認レベル番号【hidden】「配列」 を取得する。<br/>
	 * <br/>
	 * @return 確認レベル番号【hidden】「配列」
	 */
	public String[] getInspectionNoHidden() {
		return inspectionNoHidden;
	}

	/**
	 * 確認レベル番号【hidden】「配列」 を設定する。<br/>
	 * <br/>
	 * @param inspectionNoHidden
	 */
	public void setInspectionNoHidden(String[] inspectionNoHidden) {
		this.inspectionNoHidden = inspectionNoHidden;
	}

	/**
	 * 確認レベル（名称）「配列」 を取得する。<br/>
	 * <br/>
	 * @return 確認レベル（名称）「配列」
	 */
	public String[] getInspectionKey() {
		return inspectionKey;
	}

	/**
	 * 確認レベル（名称）「配列」 を設定する。<br/>
	 * <br/>
	 * @param inspectionKey
	 */
	public void setInspectionKey(String[] inspectionKey) {
		this.inspectionKey = inspectionKey;
	}

	/**
	 * 確認レベル「配列」 を取得する。<br/>
	 * <br/>
	 * @return 確認レベル「配列」
	 */
	public String[] getInspectionTrust() {
		return inspectionTrust;
	}

	/**
	 * 確認レベル「配列」 を設定する。<br/>
	 * <br/>
	 * @param inspectionTrust
	 */
	public void setInspectionTrust(String[] inspectionTrust) {
		this.inspectionTrust = inspectionTrust;
	}

	/**
	 * 住宅診断情報図【hidden】 を取得する。<br/>
	 * <br/>
	 * @return 住宅診断情報図【hidden】
	 */
	public String getInspectionImagePathName() {
		return inspectionImagePathName;
	}

	/**
	 * 住宅診断情報図【hidden】 を設定する。<br/>
	 * <br/>
	 * @param inspectionImagePathName
	 */
	public void setInspectionImagePathName(String inspectionImagePathName) {
		this.inspectionImagePathName = inspectionImagePathName;
	}

	/**
	 * 住宅診断ファイル【hidden】 を取得する。<br/>
	 * <br/>
	 * @return 住宅診断ファイル【hidden】
	 */
	public String getInspectionPathName() {
		return inspectionPathName;
	}

	/**
	 * 住宅診断ファイル【hidden】 を設定する。<br/>
	 * <br/>
	 * @param inspectionPathName
	 */
	public void setInspectionPathName(String inspectionPathName) {
		this.inspectionPathName = inspectionPathName;
	}

	/**
	 * その他のリフォームプランの表示フラグ を取得する。<br/>
	 * <br/>
	 * @return その他のリフォームプランの表示フラグ
	 */
	public boolean isOtherReformPlanDisplayFlg() {
		return otherReformPlanDisplayFlg;
	}

	/**
	 * その他のリフォームプランの表示フラグ を設定する。<br/>
	 * <br/>
	 * @param otherReformPlanDisplayFlg
	 */
	public void setOtherReformPlanDisplayFlg(boolean otherReformPlanDisplayFlg) {
		this.otherReformPlanDisplayFlg = otherReformPlanDisplayFlg;
	}

	/**
	 * その他のプラン番号【hidden】「配列」 を取得する。<br/>
	 * <br/>
	 * @return その他のプラン番号【hidden】「配列」
	 */
	public String[] getOtherPlanNoHidden() {
		return otherPlanNoHidden;
	}

	/**
	 * その他のプラン番号【hidden】「配列」 を設定する。<br/>
	 * <br/>
	 * @param otherPlanNoHidden
	 */
	public void setOtherPlanNoHidden(String[] otherPlanNoHidden) {
		this.otherPlanNoHidden = otherPlanNoHidden;
	}

	/**
	 * その他のプランタイプ「配列」 を取得する。<br/>
	 * <br/>
	 * @return その他のプランタイプ「配列」
	 */
	public String[] getOtherPlanType() {
		return otherPlanType;
	}

	/**
	 * その他のプランタイプ「配列」 を設定する。<br/>
	 * <br/>
	 * @param otherPlanType
	 */
	public void setOtherPlanType(String[] otherPlanType) {
		this.otherPlanType = otherPlanType;
	}

	/**
	 * その他の総額１「配列」 を取得する。<br/>
	 * <br/>
	 * @return その他の総額１「配列」
	 */
	public String[] getOtherTotalPrice1() {
		return otherTotalPrice1;
	}

	/**
	 * その他の総額１「配列」 を設定する。<br/>
	 * <br/>
	 * @param otherTotalPrice1
	 */
	public void setOtherTotalPrice1(String[] otherTotalPrice1) {
		this.otherTotalPrice1 = otherTotalPrice1;
	}

	/**
	 * その他の総額２「配列」 を取得する。<br/>
	 * <br/>
	 * @return その他の総額２「配列」
	 */
	public String[] getOtherTotalPrice2() {
		return otherTotalPrice2;
	}

	/**
	 * その他の総額２「配列」 を設定する。<br/>
	 * <br/>
	 * @param otherTotalPrice2
	 */
	public void setOtherTotalPrice2(String[] otherTotalPrice2) {
		this.otherTotalPrice2 = otherTotalPrice2;
	}

	/**
	 * その他のシステムリフォームCD【hidden】「配列」 を取得する。<br/>
	 * <br/>
	 * @return その他のシステムリフォームCD【hidden】「配列」
	 */
	public String[] getOtherReformCdHidden() {
		return otherReformCdHidden;
	}

	/**
	 * その他のシステムリフォームCD【hidden】「配列」 を設定する。<br/>
	 * <br/>
	 * @param otherReformCdHidden
	 */
	public void setOtherReformCdHidden(String[] otherReformCdHidden) {
		this.otherReformCdHidden = otherReformCdHidden;
	}

	/**
	 * その他のシステムリフォームURL【hidden】「配列」 を取得する。<br/>
	 * <br/>
	 * @return その他のシステムリフォームURL【hidden】「配列」
	 */
	public String[] getOtherReformUrl() {
		return otherReformUrl;
	}

	/**
	 * その他のシステムリフォームURL【hidden】「配列」 を設定する。<br/>
	 * <br/>
	 * @param otherReformUrl
	 */
	public void setOtherReformUrl(String[] otherReformUrl) {
		this.otherReformUrl = otherReformUrl;
	}

	/**
	 * 最近見た物件の表示フラグ を取得する。<br/>
	 * <br/>
	 * @return 最近見た物件の表示フラグ
	 */
	public boolean isRecentlyDisplayFlg() {
		return recentlyDisplayFlg;
	}

	/**
	 * 最近見た物件の表示フラグ を設定する。<br/>
	 * <br/>
	 * @param recentlyDisplayFlg
	 */
	public void setRecentlyDisplayFlg(boolean recentlyDisplayFlg) {
		this.recentlyDisplayFlg = recentlyDisplayFlg;
	}

	/**
	 * 最近 物件番号【hidden】「配列」 を取得する。<br/>
	 * <br/>
	 * @return 最近 物件番号【hidden】「配列」
	 */
	public String[] getRecentlyNoHidden() {
		return recentlyNoHidden;
	}

	/**
	 * 最近 物件番号【hidden】「配列」 を設定する。<br/>
	 * <br/>
	 * @param recentlyNoHidden
	 */
	public void setRecentlyNoHidden(String[] recentlyNoHidden) {
		this.recentlyNoHidden = recentlyNoHidden;
	}

	/**
	 * 最近 システム物件CD【hidden】「配列」 を取得する。<br/>
	 * <br/>
	 * @return 最近 システム物件CD【hidden】「配列」
	 */
	public String[] getRecentlySysHousingCdHidden() {
		return recentlySysHousingCdHidden;
	}

	/**
	 * 最近 システム物件CD【hidden】「配列」 を設定する。<br/>
	 * <br/>
	 * @param recentlySysHousingCdHidden
	 */
	public void setRecentlySysHousingCdHidden(String[] recentlySysHousingCdHidden) {
		this.recentlySysHousingCdHidden = recentlySysHousingCdHidden;
	}

	/**
	 * 最近 物件番号【data-number】「配列」 を取得する。<br/>
	 * <br/>
	 * @return 最近 物件番号【data-number】「配列」
	 */
	public String[] getRecentlyHousingCdHidden() {
		return recentlyHousingCdHidden;
	}

	/**
	 * 最近 物件番号【data-number】「配列」 を設定する。<br/>
	 * <br/>
	 * @param recentlyHousingCdHidden
	 */
	public void setRecentlyHousingCdHidden(String[] recentlyHousingCdHidden) {
		this.recentlyHousingCdHidden = recentlyHousingCdHidden;
	}

	/**
	 * 最近 物件画像「配列」 を取得する。<br/>
	 * <br/>
	 * @return 最近 物件画像「配列」
	 */
	public String[] getRecentlyPathName() {
		return recentlyPathName;
	}

	/**
	 * 最近 物件画像「配列」 を設定する。<br/>
	 * <br/>
	 * @param recentlyPathName
	 */
	public void setRecentlyPathName(String[] recentlyPathName) {
		this.recentlyPathName = recentlyPathName;
	}

	/**
	 * 最近 物件種類CD「配列」 を取得する。<br/>
	 * <br/>
	 * @return 最近 物件種類CD「配列」
	 */
	public String[] getRecentlyHousingKindCd() {
		return recentlyHousingKindCd;
	}

	/**
	 * 最近 物件種類CD「配列」 を設定する。<br/>
	 * <br/>
	 * @param recentlyHousingKindCd
	 */
	public void setRecentlyHousingKindCd(String[] recentlyHousingKindCd) {
		this.recentlyHousingKindCd = recentlyHousingKindCd;
	}

	/**
	 * 最近 物件名「配列」 を取得する。<br/>
	 * <br/>
	 * @return 最近 物件名「配列」
	 */
	public String[] getRecentlyDisplayHousingName() {
		return recentlyDisplayHousingName;
	}

	/**
	 * 最近 物件名「配列」 を設定する。<br/>
	 * <br/>
	 * @param recentlyDisplayHousingName
	 */
	public void setRecentlyDisplayHousingName(String[] recentlyDisplayHousingName) {
		this.recentlyDisplayHousingName = recentlyDisplayHousingName;
	}

	/**
	 * 最近 物件名FULL「配列」 を取得する。<br/>
	 * <br/>
	 * @return 最近 物件名FULL「配列」
	 */
	public String[] getRecentlyDisplayHousingNameFull() {
		return recentlyDisplayHousingNameFull;
	}

	/**
	 * 最近 物件名FULL「配列」 を設定する。<br/>
	 * <br/>
	 * @param recentlyDisplayHousingNameFull
	 */
	public void setRecentlyDisplayHousingNameFull(String[] recentlyDisplayHousingNameFull) {
		this.recentlyDisplayHousingNameFull = recentlyDisplayHousingNameFull;
	}

	/**
	 * 最近 物件詳細「配列」 を取得する。<br/>
	 * <br/>
	 * @return 最近 物件詳細「配列」
	 */
	public String[] getRecentlyDtl() {
		return recentlyDtl;
	}

	/**
	 * 最近 物件詳細「配列」 を設定する。<br/>
	 * <br/>
	 * @param recentlyDtl
	 */
	public void setRecentlyDtl(String[] recentlyDtl) {
		this.recentlyDtl = recentlyDtl;
	}

	/**
	 * 最近 物件詳細FULL「配列」 を取得する。<br/>
	 * <br/>
	 * @return 最近 物件詳細FULL「配列」
	 */
	public String[] getRecentlyDtlFull() {
		return recentlyDtlFull;
	}

	/**
	 * 最近 物件詳細FULL「配列」 を設定する。<br/>
	 * <br/>
	 * @param recentlyDtlFull
	 */
	public void setRecentlyDtlFull(String[] recentlyDtlFull) {
		this.recentlyDtlFull = recentlyDtlFull;
	}

	/**
	 * 最近 物件URL「配列」 を取得する。<br/>
	 * <br/>
	 * @return 最近 物件URL「配列」
	 */
	public String[] getRecentlyUrl() {
		return recentlyUrl;
	}

	/**
	 * 最近 物件URL「配列」 を設定する。<br/>
	 * <br/>
	 * @param recentlyUrl
	 */
	public void setRecentlyUrl(String[] recentlyUrl) {
		this.recentlyUrl = recentlyUrl;
	}

	/**
	 * キーワード を取得する。<br/>
	 * <br/>
	 * @return キーワード
	 */
	public String getKeywords() {
		return keywords;
	}

	/**
	 * キーワード を設定する。<br/>
	 * <br/>
	 * @param keywords
	 */
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	/**
	 * 説明 を取得する。<br/>
	 * <br/>
	 * @return 説明
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * 説明 を設定する。<br/>
	 * <br/>
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * 本ページURL を取得する。<br/>
	 * <br/>
	 * @return 本ページURL
	 */
	public String getCurrentUrl() {
		return currentUrl;
	}

	/**
	 * 本ページURL を設定する。<br/>
	 * <br/>
	 * @param currentUrl
	 */
	public void setCurrentUrl(String currentUrl) {
		this.currentUrl = currentUrl;
	}

	/**
	 * 再検索URL「配列」 を取得する。<br/>
	 * <br/>
	 * @return 再検索URL「配列」
	 */
	public String[] getResearchUrl() {
		return researchUrl;
	}

	/**
	 * 再検索URL「配列」 を設定する。<br/>
	 * <br/>
	 * @param researchUrl
	 */
	public void setResearchUrl(String[] researchUrl) {
		this.researchUrl = researchUrl;
	}

	/**
	 * 再検索都道府県名「配列」 を取得する。<br/>
	 * <br/>
	 * @return 再検索都道府県名「配列」
	 */
	public String[] getResearchPrefName() {
		return researchPrefName;
	}

	/**
	 * 再検索都道府県名「配列」 を設定する。<br/>
	 * <br/>
	 * @param researchPrefName
	 */
	public void setResearchPrefName(String[] researchPrefName) {
		this.researchPrefName = researchPrefName;
	}

	/**
	 * コンストラクター<br/>
	 * <br/>
	 */
	public PanaHousingDetailed(CodeLookupManager codeLookupManager, PanaCommonParameters commonParameters, PanaFileUtil panaFileUtil) {
		this.codeLookupManager = codeLookupManager;
		this.commonParameters = commonParameters;
		this.panaFileUtil = panaFileUtil;
	}

	/**
	 * 渡されたバリーオブジェクトから Form へ初期値を設定する。<br/>
	 * <br/>
	 *
	 * @param detailedMap 物件詳細オブジェクト
	 *
	 * @exception Exception 実装クラスによりスローされる任意の例外
	 */
	@SuppressWarnings("unchecked")
	public void setDefaultData(Map<String, Object> detailedMap) throws Exception {

		// 処理モードより、フラグを設定する。
		if (DETAIL_MODE.equals(getMode())) {
			// おすすめリフォームプラン例の表示フラグ
			setRecommendReformPlanDisplayFlg(false);

			// リフォームイメージの表示フラグ
			setReformImgDisplayFlg(false);

			// この物件はおすすめリフォームプランがありますフラグ
			setReformPlanExists(false);

		} else if (REFORM_MODE.equals(getMode())) {

			// おすすめリフォームプラン例の表示フラグ
			setRecommendReformPlanDisplayFlg(true);

			// リフォームイメージの表示フラグ
			setReformImgDisplayFlg(true);

			// この物件はおすすめリフォームプランがありますフラグ
			setReformPlanExists(true);
		}

		// 情報を取得する。
		// 物件情報を取得する。
		PanaHousing housing = (PanaHousing) detailedMap.get("housing");

		// 物件基本情報を取得する。
		HousingInfo housingInfo = (HousingInfo) housing.getHousingInfo().getItems().get("housingInfo");

		// 物件詳細情報を取得する。
		HousingDtlInfo housingDtlInfo = (HousingDtlInfo) housing.getHousingInfo().getItems().get("housingDtlInfo");

		// 建物基本情報を取得する。
		BuildingInfo buildingInfo = (BuildingInfo) housing.getBuilding().getBuildingInfo().getItems().get("buildingInfo");

		// 建物詳細情報を取得する。
		BuildingDtlInfo buildingDtlInfo = (BuildingDtlInfo) housing.getBuilding().getBuildingInfo().getItems().get("buildingDtlInfo");

		// 都道府県マスタを取得する。
		PrefMst prefMst = (PrefMst) housing.getBuilding().getBuildingInfo().getItems().get("prefMst");

		// 建物最寄り駅情報を取得する。
		List<JoinResult> buildingStationInfoList = housing.getBuilding().getBuildingStationInfoList();

		// リフォームプラン情報を取得する。
		List<ReformPlan> reformPlanList = (List<ReformPlan>) detailedMap.get("reformPlanList");

		// 画像情報を取得する。
		List<HousingImageInfo> housingImageInfoList = housing.getHousingImageInfos();

		// 物件拡張属性情報を取得する。
		Map<String, Map<String, String>> housingExtInfosMap = housing.getHousingExtInfos();

		// 物件設備情報を取得する。
		Map<String, EquipMst> housingEquipInfos = housing.getHousingEquipInfos();

		// 建物ランドマーク情報を取得する。
		List<BuildingLandmark> buildingLandmarkList = housing.getBuilding().getBuildingLandmarkList();

		// リフォームプランを取得する。
		ReformPlan reformPlan = (ReformPlan) detailedMap.get("reformPlan");

		// リフォーム詳細情報を取得する。
		List<ReformDtl> reformDtlList = (List<ReformDtl>) detailedMap.get("reformDtlList");

		// リフォーム画像情報を取得する。
		List<ReformImg> reformImgList = (List<ReformImg>) detailedMap.get("reformImgList");

		// 物件インスペクションを取得する。
		List<HousingInspection> housingInspectionList = (List<HousingInspection>) detailedMap.get("housingInspectionList");

		// 都道府県リストを取得する。
		List<PrefMst> prefMstList = (List<PrefMst>) detailedMap.get("prefMstList");

		// 最近見た物件を取得する。
		List<PanaHousing> recentlyInfoList = (List<PanaHousing>) detailedMap.get("recentlyInfoList");

		// 情報を設定する。
		// タイトル部を設定する。
		setTitle(housingInfo, housingDtlInfo, buildingInfo);

		// 物件情報を設定する。
		setHousingInfo(housingInfo, buildingStationInfoList, buildingInfo, buildingDtlInfo, prefMst);

		// リフォームプラン情報を設定する。
		setReformPlan(housingInfo, buildingInfo, reformPlanList);

		// 画像情報を設定する。
		setHousingImageInfo(housingImageInfoList, housingExtInfosMap);

		// 売主のコメントを設定する。
		setSaleComment(housingExtInfosMap);

		// 担当者からのおすすめを設定する。
		setRecommend(housingInfo, housingExtInfosMap);

		// 物件詳細情報を設定する。
		setHousingDtlInfo(housingInfo, housingDtlInfo, housingExtInfosMap, buildingDtlInfo);

		// 物件特徴を設定する。
		setHousingEquipInfo(housingEquipInfos);

		// 地域情報を設定する。
		setBuildingLandmark(buildingLandmarkList);

		// おすすめリフォームプラン例を設定する。
		setRecommendReformPlan(housingInfo, reformPlan, reformDtlList);

		// リフォームイメージを設定する。
		setReformImg(reformPlan, reformImgList);

		// 住宅診断情報を設定する。
		setInspection(housingInspectionList, housingExtInfosMap);

		// その他のリフォームプランを設定する。
		setOtherReformPlan();

		// 最近見た物件を設定する。
		setRecentlyInfo(recentlyInfoList);

		// 再検索のリンクを設定する。
		setResearchLink(prefMst, prefMstList);

		// 会員登録の表示フラグを設定する。
		setMemberLoginFlg();

		// キーワードと説明を設定する。
		setMeta();
	}

	/**
	 * 渡されたバリーオブジェクトから タイトル部を設定する。<br/>
	 * <br/>
	 *
	 * @param housingInfo 物件基本情報
	 * @param housingDtlInfo 物件詳細情報
	 * @param buildingInfo 建物基本情報
	 *
	 * @exception Exception 実装クラスによりスローされる任意の例外
	 */
	private void setTitle(HousingInfo housingInfo, HousingDtlInfo housingDtlInfo, BuildingInfo buildingInfo) throws Exception {

		if (housingInfo != null) {

			// 新着フラグ
			if (housingInfo.getUpdDate() != null) {
				// システム日付
				Date nowDate = new Date();
				// 最終更新日
				Date updDate = housingInfo.getUpdDate();
				// 1週間以内（システム日付 - 最終更新日<= 7日）
				if ((nowDate.getTime() - updDate.getTime()) <= 7 * (24 * 60 * 60 * 1000)) {
					// 新着フラグ
					setFreshFlg(true);

				} else {

					// 新着フラグ
					setFreshFlg(false);
				}
			}
			if (buildingInfo != null) {
				// 物件種類CD
				setHousingKindCd(buildingInfo.getHousingKindCd());
			}

			// 物件番号
			setHousingCd(housingInfo.getHousingCd());

			// 物件名
			setDisplayHousingName(housingInfo.getDisplayHousingName());
		}

		if (housingDtlInfo != null) {
			// おすすめポイントの訴求エリア1
			setDtlComment(PanaStringUtils.encodeHtml(housingDtlInfo.getDtlComment()));
		}
	}

	/**
	 * 渡されたバリーオブジェクトから 物件情報を設定する。<br/>
	 * <br/>
	 *
	 * @param housingInfo 物件基本情報
	 * @param buildingStationInfoList 建物最寄り駅情報
	 * @param buildingInfo 建物基本情報
	 * @param buildingDtlInfo 建物詳細情報
	 * @param prefMst 都道府県マスタ
	 *
	 * @exception Exception 実装クラスによりスローされる任意の例外
	 */
	private void setHousingInfo(HousingInfo housingInfo, List<JoinResult> buildingStationInfoList, BuildingInfo buildingInfo, BuildingDtlInfo buildingDtlInfo, PrefMst prefMst)
			throws Exception {

		if (housingInfo != null) {

			// アイコン情報【hidden】「配列」
			if (!isEmpty(housingInfo.getIconCd())) {
				setIconCd(defaultString(housingInfo.getIconCd()).split(","));
			}

			// 物件価格
			setPrice((housingInfo.getPrice() == null) ? "" : formatPrice(housingInfo.getPrice(), true) + "万円");

			// 物件価格【hidden】
			setPriceHidden((housingInfo.getPrice() == null) ? "" : housingInfo.getPrice().toString());

			// 所在地
			StringBuilder sbAddress = new StringBuilder();
			if (prefMst != null) {
				// 都道府県名
				sbAddress.append(defaultString(prefMst.getPrefName()) + " ");

				// 都道府県名
				setPrefName(prefMst.getPrefName());
			}
			if (buildingInfo != null) {
				// 所在地・市区町村名
				sbAddress.append(defaultString(buildingInfo.getAddressName()) + " ");
				// 所在地・町名番地
				sbAddress.append(defaultString(buildingInfo.getAddressOther1()) + " ");
				// 所在地・建物名その他
				sbAddress.append(defaultString(buildingInfo.getAddressOther2()));
			}

			// 所在地
			setAddress(sbAddress.toString());

			if (buildingStationInfoList != null && buildingStationInfoList.size() > 0) {
				int cnt = buildingStationInfoList.size();
				String[] access = new String[cnt];

				for (int i = 0; i < cnt; i++) {
					StringBuilder sbAccess = new StringBuilder();
					// 建物最寄り駅情報
					BuildingStationInfo buildingStationInfo = (BuildingStationInfo) buildingStationInfoList.get(i).getItems().get("buildingStationInfo");
					// 鉄道会社マスタ
					RrMst rrMst = (RrMst) buildingStationInfoList.get(i).getItems().get("rrMst");
					// 路線マスタ
					RouteMst routeMst = (RouteMst) buildingStationInfoList.get(i).getItems().get("routeMst");
					// 駅名マスタ
					StationMst stationMst = (StationMst) buildingStationInfoList.get(i).getItems().get("stationMst");
					// 代表路線名
					sbAccess.append(addString(defaultString(defaultString(rrMst.getRrName()) + defaultString(routeMst.getRouteName()), buildingStationInfo.getDefaultRouteName()),
							" "));
					// 駅名
					sbAccess.append(addString(defaultString(stationMst.getStationName(), defaultString(buildingStationInfo.getStationName())), "駅 "));
					// バス会社名
					sbAccess.append(addString(defaultString(buildingStationInfo.getBusCompany()), " "));
					// バス停からの徒歩時間
					sbAccess.append(defaultString(buildingStationInfo.getTimeFromBusStop(), "徒歩" + buildingStationInfo.getTimeFromBusStop() + "分"));
					access[i] = sbAccess.toString();
				}

				// アクセス「配列」
				setAccess(access);
			}

			// 間取CD
			setLayoutCd(housingInfo.getLayoutCd());

			if (buildingInfo != null && buildingInfo.getCompDate() != null) {
				// 築年月
				setCompDate(new SimpleDateFormat("yyyy年M月築").format(buildingInfo.getCompDate()));
			}

			// 物件種類CDを判断
			if (PanaCommonConstant.HOUSING_KIND_CD_MANSION.equals(getHousingKindCd())) {
				// マンション面積フラグ
				setPersonalAreaFlg(true);

				// 戸建 土地面積フラグ
				setBuildingAreaFlg(false);

				// 階建／所在階フラグ
				setFloorFlg(true);

				// 専有面積
				setPersonalArea(defaultString(housingInfo.getPersonalArea()));

				// 専有面積 坪
				setPersonalAreaSquare((housingInfo.getPersonalArea() == null) ? "" : "(約" + PanaCalcUtil.calcTsubo(housingInfo.getPersonalArea()).toString() + "坪)");

				// 専有面積_補足
				setPersonalAreaMemo(housingInfo.getPersonalAreaMemo());

				if (buildingInfo != null) {
					// 階建／所在階
					StringBuilder sbFloor = new StringBuilder();
					// 総階数
					sbFloor.append((buildingInfo.getTotalFloors() == null) ? "" : buildingInfo.getTotalFloors() + "階建");
					sbFloor.append((isEmpty(buildingInfo.getTotalFloors()) && isEmpty(housingInfo.getFloorNo())) ? "" : "　&frasl;　");
					// 物件の階数
					sbFloor.append((housingInfo.getFloorNo() == null) ? "" : housingInfo.getFloorNo() + "階");
					// 階建／所在階
					setFloor(sbFloor.toString());
				}

			} else {

				// マンション面積フラグ
				setPersonalAreaFlg(false);

				// 戸建 土地面積フラグ
				setBuildingAreaFlg(true);

				// 階建／所在階フラグ
				setFloorFlg(false);

				if (buildingDtlInfo != null) {
					// 建物面積
					setBuildingArea(defaultString(buildingDtlInfo.getBuildingArea()));

					// 建物面積 坪
					setBuildingAreaSquare((buildingDtlInfo.getBuildingArea() == null) ? "" : "（約" + PanaCalcUtil.calcTsubo(buildingDtlInfo.getBuildingArea()).toString() + "坪）");

					// 建物面積_補足
					setBuildingAreaMemo(buildingDtlInfo.getBuildingAreaMemo());
				}

				// 土地面積
				setLandArea(defaultString(housingInfo.getLandArea()));

				// 土地面積 坪
				setLandAreaSquare((housingInfo.getLandArea() == null) ? "" : "（約" + PanaCalcUtil.calcTsubo(housingInfo.getLandArea()).toString() + "坪）");

				// 土地面積_補足
				setLandAreaMemo(housingInfo.getLandAreaMemo());
			}

			if (buildingInfo != null) {

				// 周辺地図
				setMapUrl(makeUrl(buildingInfo.getPrefCd(), housingInfo.getSysHousingCd(), getHousingKindCd()));
			}
		}
	}

	/**
	 * 渡されたバリーオブジェクトから リフォームプラン情報を設定する。<br/>
	 * <br/>
	 *
	 * @param housingInfo 物件基本情報
	 * @param buildingInfo 建物基本情報
	 * @param reformPlanList リフォームプラン情報
	 *
	 * @exception Exception 実装クラスによりスローされる任意の例外
	 */
	private void setReformPlan(HousingInfo housingInfo, BuildingInfo buildingInfo, List<ReformPlan> reformPlanList) throws Exception {

		if (housingInfo != null && buildingInfo != null) {
			// 「物件種類CD」 ＝ 「03：土地」の場合
			if (PanaCommonConstant.HOUSING_KIND_CD_GROUND.equals(getHousingKindCd())) {
				// 物件のリフォームプランの表示フラグ
				setReformPlanDisplayFlg(false);

				// リフォームプラン準備中文言の表示フラグ
				setReformPlanReadyDisplayFlg(false);

			} else {

				// リフォームプラン情報がある場合
				if (reformPlanList != null && reformPlanList.size() > 0) {
					// 物件のリフォームプランの表示フラグ
					setReformPlanDisplayFlg(true);

					// リフォームプラン準備中文言の表示フラグ
					setReformPlanReadyDisplayFlg(false);

					int cnt = reformPlanList.size();
					String[] planNo = new String[cnt];
					String[] planName = new String[cnt];
					String[] planPrice = new String[cnt];
					String[] totalPrice1 = new String[cnt];
					String[] totalPrice2 = new String[cnt];
					String[] reformCdHidden = new String[cnt];
					String[] reformUrl = new String[cnt];
					String[] reformCategory = new String[cnt];

					for (int i = 0; i < cnt; i++) {
						StringBuilder sbReformPlan = new StringBuilder();
						StringBuilder sbTotalPrice1 = new StringBuilder();
						StringBuilder sbTotalPrice2 = new StringBuilder();
						StringBuilder sbReformCdHidden = new StringBuilder();

						// リフォームプラン情報
						ReformPlan reformPlan = reformPlanList.get(i);

						// プラン番号【hidden】「配列」
						planNo[i] = String.valueOf(i);

						// プランタイプ「配列」
						sbReformPlan.append(defaultString(reformPlan.getPlanName()));
						planName[i] = sbReformPlan.toString();

						// リフォーム価格「配列」
						planPrice[i] = defaultString(reformPlan.getPlanPrice());

						// 総額１「配列」
						if (housingInfo.getPrice() != null || reformPlan.getPlanPrice() != null) {
							sbTotalPrice1.append("約");
							sbTotalPrice1.append(formatPrice(sumPrice(housingInfo.getPrice(), reformPlan.getPlanPrice()), true));
							sbTotalPrice1.append("万円");
						}
						totalPrice1[i] = sbTotalPrice1.toString();

						// 総額２「配列」
						sbTotalPrice2.append((housingInfo.getPrice() == null) ? "" : "物件価格：" + formatPrice(housingInfo.getPrice(), true) + "万円");
						sbTotalPrice2.append((housingInfo.getPrice() == null || reformPlan.getPlanPrice() == null) ? "" : "＋");
						sbTotalPrice2.append((reformPlan.getPlanPrice() == null) ? "" : "リフォーム価格：約" + formatPrice(reformPlan.getPlanPrice(), true) + "万円");
						totalPrice2[i] = sbTotalPrice2.toString();

						// システムリフォームCD【hidden】「配列」
						sbReformCdHidden.append(defaultString(reformPlan.getSysReformCd()));
						reformCdHidden[i] = sbReformCdHidden.toString();

						// システムリフォームURL【hidden】「配列」
						reformUrl[i] = (makeUrl(buildingInfo.getPrefCd(), housingInfo.getSysHousingCd(), getHousingKindCd(), reformPlan.getSysReformCd()));
						reformCategory[i] = reformPlan.getPlanCategory1();
					}

					// プラン番号【hidden】「配列」
					setPlanNoHidden(planNo);

					// プランタイプ「配列」
					setPlanType(planName);

					// リフォーム価格「配列」
					setPlanPrice(planPrice);

					// 総額１「配列」
					setTotalPrice1(totalPrice1);

					// 総額２「配列」
					setTotalPrice2(totalPrice2);

					// システムリフォームCD【hidden】「配列」
					setReformCdHidden(reformCdHidden);

					// システムリフォームURL【hidden】「配列」
					setReformUrl(reformUrl);
					
					setReformCategory(reformCategory);

				} else {

					// リフォームプラン準備中文言取得しなかった場合
					if (isEmpty(housingInfo.getReformComment())) {
						// 物件のリフォームプランの表示フラグ
						setReformPlanDisplayFlg(false);

						// リフォームプラン準備中文言の表示フラグ
						setReformPlanReadyDisplayFlg(false);

					} else {

						// 物件のリフォームプランの表示フラグ
						setReformPlanDisplayFlg(false);

						// リフォームプラン準備中文言の表示フラグ
						setReformPlanReadyDisplayFlg(true);

						// リフォームプラン準備中文言
						setReformPlanReadyComment(PanaStringUtils.encodeHtml(housingInfo.getReformComment()));
					}
				}
			}
		}
	}

	/**
	 * 渡されたバリーオブジェクトから 画像情報を設定する。<br/>
	 * <br/>
	 *
	 * @param housingImageInfoList 画像情報
	 * @param housingExtInfosMap 物件拡張属性情報
	 *
	 * @exception Exception 実装クラスによりスローされる任意の例外
	 */
	private void setHousingImageInfo(List<HousingImageInfo> housingImageInfoList, Map<String, Map<String, String>> housingExtInfosMap) throws Exception {

		if (housingImageInfoList != null && housingImageInfoList.size() > 0) {
			// 画像の表示フラグ
			setImgDisplayFlg(true);

			int cnt = housingImageInfoList.size();
			int j = 0;
			int k = 0;
			String[] imageNo = new String[cnt];
			String[] imageType = new String[cnt];
			String[] path1 = new String[cnt];
			String[] path2 = new String[cnt];
			String[] imgComment = new String[cnt];

			for (int i = 0; i < cnt; i++) {

				// 画像情報
				jp.co.transcosmos.dm3.corePana.vo.HousingImageInfo housingImageInfo = (jp.co.transcosmos.dm3.corePana.vo.HousingImageInfo) housingImageInfoList.get(i);

				// 非会員の場合
				if (!isMemberFlg()) {

					// 全員可の画像が閲覧とする。
					if (PanaCommonConstant.ROLE_ID_PUBLIC.equals(housingImageInfo.getRoleId())) {

						// 画像番号【hidden】「配列」
						imageNo[j] = String.valueOf(j);

						// パス名１【hidden】「配列」
						path1[j] = getImgPath(housingImageInfo.getRoleId(), housingImageInfo.getPathName(), getCommonParameters().getHousingDtlImageSmallSize(),
								housingImageInfo.getFileName());

						// パス名２【hidden】「配列」
						path2[j] = getImgPath(housingImageInfo.getRoleId(), housingImageInfo.getPathName(), getCommonParameters().getHousingDtlImageLargeSize(),
								housingImageInfo.getFileName());

						// コメント【hidden】「配列」
						imgComment[j] = housingImageInfo.getImgComment();

						j++;
					}

				} else {

					// 画像番号【hidden】「配列」
					imageNo[k] = String.valueOf(k);

					// パス名１【hidden】「配列」
					path1[k] = getImgPath(housingImageInfo.getRoleId(), housingImageInfo.getPathName(), getCommonParameters().getHousingDtlImageSmallSize(),
							housingImageInfo.getFileName());

					// パス名２【hidden】「配列」
					path2[k] = getImgPath(housingImageInfo.getRoleId(), housingImageInfo.getPathName(), getCommonParameters().getHousingDtlImageLargeSize(),
							housingImageInfo.getFileName());

					// コメント【hidden】「配列」
					imgComment[k] = housingImageInfo.getImgComment();

					k++;
				}

				// 画像タイプ【hidden】「配列」
				imageType[i] = housingImageInfo.getImageType();
			}

			// 内観画像フラグ
			setIntrospectImgFlg(false);

			// 物件画像情報を繰り返す、画像タイプを判断する。
			for (String type : imageType) {
				if (PanaCommonConstant.IMAGE_TYPE_03.equals(type)) {
					// 内観画像フラグ
					setIntrospectImgFlg(true);
					break;
				}
			}

			// 画像番号【hidden】「配列」
			setImgNoHidden(imageNo);

			// パス名１【hidden】「配列」
			setHousingImgPath1Hidden(path1);

			// パス名２【hidden】「配列」
			setHousingImgPath2Hidden(path2);

			// コメント【hidden】「配列」
			setHousingImgCommentHidden(imgComment);

		} else {

			// 画像の表示フラグ
			setImgDisplayFlg(false);

			// 内観画像フラグ
			setIntrospectImgFlg(false);
		}

		if (housingExtInfosMap != null) {

			// カテゴリ名に該当する Map を取得する。
			Map<String, String> housingDetailMap = housingExtInfosMap.get("housingDetail");

			if (housingDetailMap != null) {

				// 動画パス【hidden】
				setMovieUrl(housingDetailMap.get("movieUrl"));
			}
		}
	}

	/**
	 * 渡されたバリーオブジェクトから 売主のコメントを設定する。<br/>
	 * <br/>
	 *
	 * @param housingExtInfosMap 物件拡張属性情報
	 *
	 * @exception Exception 実装クラスによりスローされる任意の例外
	 */
	private void setSaleComment(Map<String, Map<String, String>> housingExtInfosMap) throws Exception {

		if (housingExtInfosMap != null) {

			// カテゴリ名に該当する Map を取得する。
			Map<String, String> housingDetailMap = housingExtInfosMap.get("housingDetail");

			if (housingDetailMap != null) {

				// 売主のコメント
				setSalesComment(PanaStringUtils.encodeHtml(housingDetailMap.get("vendorComment")));
			}
		}
	}

	/**
	 * 渡されたバリーオブジェクトから 担当者からのおすすめを設定する。<br/>
	 * <br/>
	 *
	 * @param housingInfo 物件基本情報
	 * @param housingExtInfosMap 物件拡張属性情報
	 *
	 * @exception Exception 実装クラスによりスローされる任意の例外
	 */
	private void setRecommend(HousingInfo housingInfo, Map<String, Map<String, String>> housingExtInfosMap) throws Exception {

		if (housingInfo != null && housingExtInfosMap != null) {

			// カテゴリ名に該当する Map を取得する。
			Map<String, String> housingDetailMap = housingExtInfosMap.get("housingDetail");

			if (housingDetailMap != null) {
				// おすすめ画像パス【hidden】
				setStaffimagePathName(getImgPath(PanaCommonConstant.ROLE_ID_PUBLIC, housingDetailMap.get("staffImagePathName"), getCommonParameters().getAdminSiteStaffFolder(),
						housingDetailMap.get("staffImageFileName")));

				// 担当
				setStaffName(housingDetailMap.get("staffName"));

				// 会社名
				setCompanyName(housingDetailMap.get("companyName"));

				// 支店名
				setBranchName(housingDetailMap.get("branchName"));

				// 免許番号
				setLicenseNo(housingDetailMap.get("licenseNo"));
			}

			// おすすめ内容
			setRecommendComment(PanaStringUtils.encodeHtml(housingInfo.getBasicComment()));
		}
	}

	/**
	 * 渡されたバリーオブジェクトから 物件詳細情報を設定する。<br/>
	 * <br/>
	 *
	 * @param housingInfo 物件基本情報
	 * @param housingDtlInfo 物件詳細情報
	 * @param housingExtInfosMap 物件拡張属性情報
	 * @param buildingDtlInfo 建物詳細情報
	 *
	 * @exception Exception 実装クラスによりスローされる任意の例外
	 */
	private void setHousingDtlInfo(HousingInfo housingInfo, HousingDtlInfo housingDtlInfo, Map<String, Map<String, String>> housingExtInfosMap, BuildingDtlInfo buildingDtlInfo)
			throws Exception {

		// 「物件種類CD」 ＝ 「01：マンション」の場合
		if (PanaCommonConstant.HOUSING_KIND_CD_MANSION.equals(getHousingKindCd())) {
			if (housingDtlInfo != null) {
				// バルコニー面積
				setBalconyArea(defaultString(housingDtlInfo.getBalconyArea()));

				// 管理形態
				setUpkeepType(housingDtlInfo.getUpkeepType());

				// 敷地権利
				setLandRight(housingDtlInfo.getLandRight());

				// 用途地域
				setUsedAreaCd(housingDtlInfo.getUsedAreaCd());

				// 引渡し
				setMoveinTiming(housingDtlInfo.getMoveinTiming());

				// 引渡時期コメント
				setMoveinNote(housingDtlInfo.getMoveinNote());

				// 取引形態
				setTransactTypeDiv(housingDtlInfo.getTransactTypeDiv());

				// 特記事項
				setSpecialInstruction(housingDtlInfo.getSpecialInstruction());

				// 備考
				setUpkeepCorp(isEmpty(housingDtlInfo.getUpkeepCorp()) ? "" : "管理会社：" + housingDtlInfo.getUpkeepCorp());
			}

			if (housingInfo != null) {
				// 管理費等
				setUpkeep((housingInfo.getUpkeep() == null) ? "" : formatPrice(housingInfo.getUpkeep(), false) + "円 &frasl; 月");

				// 修繕積立金
				setMenteFee((housingInfo.getMenteFee() == null) ? "" : formatPrice(housingInfo.getMenteFee(), false) + "円 &frasl; 月");

				// 駐車場
				setParkingSituation(housingInfo.getDisplayParkingInfo());

				if (housingInfo.getUpdDate() != null) {
					// 更新日
					setUpdDate(new SimpleDateFormat("yyyy年MM月dd日").format(housingInfo.getUpdDate()));

					// 次回更新予定
					setNextUpdDate("（次回更新予定 ：" + new SimpleDateFormat("yyyy年MM月dd日").format(dateAdd(housingInfo.getUpdDate())) + "）");
				}
			}

			if (housingExtInfosMap != null) {
				// カテゴリ名に該当する Map を取得する。
				Map<String, String> housingDetailMap = housingExtInfosMap.get("housingDetail");

				if (housingDetailMap != null) {
					// 主要採光
					setDirection(housingDetailMap.get("direction"));

					// 構造
					setStruct(housingDetailMap.get("struct"));

					// 総戸数
					setTotalHouseCnt(housingDetailMap.get("totalHouseCnt"));

					// 規模
					setScale(housingDetailMap.get("scale"));

					// 現況
					setStatus(housingDetailMap.get("status"));

					// インフラ
					setInfrastructure(housingDetailMap.get("infrastructure"));
				}
			}

		} else {

			if (housingDtlInfo != null) {
				// 私道負担
				setPrivateRoad(housingDtlInfo.getPrivateRoad());

				// 土地権利
				setLandRight(housingDtlInfo.getLandRight());

				// 用途地域
				setUsedAreaCd(housingDtlInfo.getUsedAreaCd());

				// 引渡し
				setMoveinTiming(housingDtlInfo.getMoveinTiming());

				// 引渡時期コメント
				setMoveinNote(housingDtlInfo.getMoveinNote());

				// 取引形態
				setTransactTypeDiv(housingDtlInfo.getTransactTypeDiv());

				// 接道
				setContactRoad(housingDtlInfo.getContactRoad());

				// 接道方向/幅員
				setContactRoadDir(housingDtlInfo.getContactRoadDir());

				// 瑕疵保険
				setInsurExist(housingDtlInfo.getInsurExist());

				// 特記事項
				setSpecialInstruction(housingDtlInfo.getSpecialInstruction());

				// 備考
				setUpkeepCorp(isEmpty(housingDtlInfo.getUpkeepCorp()) ? "" : "管理会社：" + housingDtlInfo.getUpkeepCorp());
			}

			if (housingInfo != null) {
				// 駐車場
				setParkingSituation(housingInfo.getDisplayParkingInfo());

				if (housingInfo.getUpdDate() != null) {
					// 更新日
					setUpdDate(new SimpleDateFormat("yyyy年MM月dd日").format(housingInfo.getUpdDate()));

					// 次回更新予定
					setNextUpdDate("（次回更新予定 ：" + new SimpleDateFormat("yyyy年MM月dd日").format(dateAdd(housingInfo.getUpdDate())) + "）");
				}
			}

			if (housingExtInfosMap != null) {
				// カテゴリ名に該当する Map を取得する。
				Map<String, String> housingDetailMap = housingExtInfosMap.get("housingDetail");

				if (housingDetailMap != null) {
					// 構造
					setStruct(housingDetailMap.get("struct"));

					// 現況
					setStatus(housingDetailMap.get("status"));

					// インフラ
					setInfrastructure(housingDetailMap.get("infrastructure"));
				}
			}

			if (buildingDtlInfo != null) {
				// 建ぺい率
				setCoverage(buildingDtlInfo.getCoverageMemo());

				// 容積率
				setBuildingRate(buildingDtlInfo.getBuildingRateMemo());
			}
		}
	}

	/**
	 * 渡されたバリーオブジェクトから 物件特徴を設定する。<br/>
	 * <br/>
	 *
	 * @param housingEquipInfos 物件設備情報
	 *
	 * @exception Exception 実装クラスによりスローされる任意の例外
	 */
	private void setHousingEquipInfo(Map<String, EquipMst> housingEquipInfos) throws Exception {

		// 「物件種類CD」 ＝ 「03：土地」以外の場合
		if (!PanaCommonConstant.HOUSING_KIND_CD_GROUND.equals(getHousingKindCd())) {

			if (housingEquipInfos != null && housingEquipInfos.size() > 0) {
				StringBuilder sbName = new StringBuilder();

				// 物件設備情報を繰り返す、物件特徴を作成する。
				for (String key : housingEquipInfos.keySet()) {
					EquipMst equip = housingEquipInfos.get(key);
					sbName.append(equip.getEquipName()).append("/");
				}

				// 物件特徴
				setEquipName(sbName.toString().substring(0, sbName.toString().lastIndexOf("/")));
			}

		} else {

			// 物件特徴の表示フラグ
			setHousingPropertyDisplayFlg(false);
		}
	}

	/**
	 * 渡されたバリーオブジェクトから 地域情報を設定する。<br/>
	 * <br/>
	 *
	 * @param buildingLandmarkList 建物ランドマーク情報
	 *
	 * @exception Exception 実装クラスによりスローされる任意の例外
	 */
	private void setBuildingLandmark(List<BuildingLandmark> buildingLandmarkList) throws Exception {

		if (buildingLandmarkList != null && buildingLandmarkList.size() > 0) {
			// 地域情報の表示フラグ
			setLandmarkDisplayFlg(true);

			int cnt = buildingLandmarkList.size();
			String[] landmarkNo = new String[cnt];
			String[] landmarkType = new String[cnt];
			String[] landmarkName = new String[cnt];
			String[] distanceFromLandmark = new String[cnt];
			for (int i = 0; i < cnt; i++) {

				// 地域情報
				BuildingLandmark buildingLandmark = buildingLandmarkList.get(i);

				StringBuilder sbLandmark = new StringBuilder();

				// 地域情報番号【hidden】「配列」
				landmarkNo[i] = String.valueOf(i);

				// ランドマークの種類「配列」
				landmarkType[i] = buildingLandmark.getLandmarkType();

				// 地域情報（名称）「配列」
				landmarkName[i] = buildingLandmark.getLandmarkName();

				// 地域情報（所要時間/距離）「配列」
				if (!isEmpty(buildingLandmark.getDistanceFromLandmark())) {
					sbLandmark.append("徒歩");
					sbLandmark.append(PanaCalcUtil.calcLandMarkTime(buildingLandmark.getDistanceFromLandmark()));
					sbLandmark.append("分（");
					sbLandmark.append(buildingLandmark.getDistanceFromLandmark());
					sbLandmark.append("m）");
				}

				// ランドマークからの徒歩時間と距離
				distanceFromLandmark[i] = sbLandmark.toString();
			}

			// 地域情報番号【hidden】「配列」
			setLandmarkNoHidden(landmarkNo);

			// ランドマークの種類「配列」
			setLandmarkType(landmarkType);

			// 地域情報（名称）「配列」
			setLandmarkName(landmarkName);

			// 地域情報（所要時間/距離）「配列」
			setDistanceFromLandmark(distanceFromLandmark);

		} else {

			// 地域情報の表示フラグ
			setLandmarkDisplayFlg(false);
		}
	}

	/**
	 * 渡されたバリーオブジェクトから おすすめリフォームプラン例を設定する。<br/>
	 * <br/>
	 *
	 * @param housingInfo 物件基本情報
	 * @param reformPlan リフォームプラン
	 * @param reformDtlList リフォーム詳細情報
	 *
	 * @exception Exception 実装クラスによりスローされる任意の例外
	 */
	private void setRecommendReformPlan(HousingInfo housingInfo, ReformPlan reformPlan, List<ReformDtl> reformDtlList) throws Exception {

		// おすすめリフォームプラン例の表示フラグ ＝ falseの場合
		if (!isRecommendReformPlanDisplayFlg()) {
			return;
		}

		if (reformPlan != null) {
			// リフォームプラン名
			setPlanName(reformPlan.getPlanName());

			// セールスポイント
			setSalesPoint(PanaStringUtils.encodeHtml(reformPlan.getSalesPoint()));

			if (housingInfo != null) {
				// 総額１
				if (housingInfo.getPrice() != null || reformPlan.getPlanPrice() != null) {
					setTotalDtlPrice1("約" + formatPrice(sumPrice(housingInfo.getPrice(), reformPlan.getPlanPrice()), true) + "万円");
				}

				StringBuilder sbTotalPrice2 = new StringBuilder();
				sbTotalPrice2.append((housingInfo.getPrice() == null) ? "" : "物件価格：" + formatPrice(housingInfo.getPrice(), true) + "万円");
				sbTotalPrice2.append((housingInfo.getPrice() == null || reformPlan.getPlanPrice() == null) ? "" : "＋");
				sbTotalPrice2.append((reformPlan.getPlanPrice() == null) ? "" : "リフォーム価格：約" + formatPrice(reformPlan.getPlanPrice(), true) + "万円");
				// 総額２
				setTotalDtlPrice2(sbTotalPrice2.toString());
			}

			// 工期
			setConstructionPeriod(reformPlan.getConstructionPeriod());

			// 備考
			setReformNote(PanaStringUtils.encodeHtml(reformPlan.getNote()));
		}

		if (reformDtlList != null && reformDtlList.size() > 0) {
			int cnt = reformDtlList.size();
			String[] planNo = new String[cnt];
			String[] planName = new String[cnt];
			String[] planPathName = new String[cnt];
			String[] price = new String[cnt];

			int i = 0;
			for (int j = 0; j < cnt; j++) {

				ReformDtl reformDtl = reformDtlList.get(j);

				// 非会員の場合
				if (!isMemberFlg()) {

					// 全員可の画像が閲覧とする。
					if (PanaCommonConstant.ROLE_ID_PUBLIC.equals(reformDtl.getRoleId())) {

						// リフォーム詳細_番号【hidden】「配列」
						planNo[i] = String.valueOf(i);

						// リフォーム詳細_項目名称「配列」
						planName[i] = reformDtl.getImgName();

						// リフォーム詳細_画像パス名【hidden】「配列」
						planPathName[i] = getImgPath(reformDtl.getRoleId(), reformDtl.getPathName(), getCommonParameters().getAdminSitePdfFolder(), reformDtl.getFileName(),
								REFORM_IMG_MODE);

						// リフォーム詳細_項目リフォーム価格「配列」
						price[i] = (reformDtl.getReformPrice() == null) ? "" : "約" + formatPrice(reformDtl.getReformPrice(), true) + "万円";

						i++;

					}

				} else {

					// リフォーム詳細_番号【hidden】「配列」
					planNo[j] = String.valueOf(j);

					// リフォーム詳細_項目名称「配列」
					planName[j] = reformDtl.getImgName();

					// リフォーム詳細_画像パス名【hidden】「配列」
					planPathName[j] = getImgPath(reformDtl.getRoleId(), reformDtl.getPathName(), getCommonParameters().getAdminSitePdfFolder(), reformDtl.getFileName(),
							REFORM_IMG_MODE);

					// リフォーム詳細_項目リフォーム価格「配列」
					price[j] = (reformDtl.getReformPrice() == null) ? "" : "約" + formatPrice(reformDtl.getReformPrice(), true) + "万円";
				}

			}
			// リフォーム詳細_番号【hidden】「配列」
			setReformNoHidden(planNo);

			// リフォーム詳細_項目名称「配列」
			setReformImgName(planName);

			// リフォーム詳細_画像パス名【hidden】「配列」
			setReformPathName(planPathName);

			// リフォーム詳細_項目リフォーム価格「配列」
			setReformPrice(price);
		}
	}

	/**
	 * 渡されたバリーオブジェクトから リフォームイメージを設定する。<br/>
	 * <br/>
	 *
	 * @param reformPlan リフォームプラン
	 * @param reformImgList リフォーム画像情報
	 *
	 * @exception Exception 実装クラスによりスローされる任意の例外
	 */
	private void setReformImg(ReformPlan reformPlan, List<ReformImg> reformImgList) throws Exception {

		// リフォームイメージの表示フラグ  ＝ falseの場合
		if (!isReformImgDisplayFlg()) {
			return;
		}

		if (reformImgList != null && reformImgList.size() > 0) {
			int cnt = reformImgList.size();
			String[] afterPathNo = new String[cnt];
			String[] afterPathName1 = new String[cnt];
			String[] afterPathName2 = new String[cnt];
			String[] afterPathComment = new String[cnt];
			String[] beforePathNo = new String[cnt];
			String[] beforePathName1 = new String[cnt];
			String[] beforePathName2 = new String[cnt];
			String[] beforePathComment = new String[cnt];

			int i = 0;

			for (int j = 0; j < cnt; j++) {

				ReformImg reformImg = reformImgList.get(j);

				// 非会員の場合
				if (!isMemberFlg()) {

					// 全員可の画像が閲覧とする。
					if (PanaCommonConstant.ROLE_ID_PUBLIC.equals(reformImg.getRoleId())) {

						// リフォーム後_外観・周辺写真番号【hidden】「配列」
						afterPathNo[i] = String.valueOf(i);

						// リフォーム後_外観・周辺写真パス１【hidden】「配列」
						afterPathName1[i] = getImgPath(reformImg.getRoleId(), reformImg.getAfterPathName(), getCommonParameters().getHousingDtlImageSmallSize(),
								reformImg.getAfterFileName(), REFORM_IMG_MODE);

						// リフォーム後_外観・周辺写真パス２【hidden】「配列」
						afterPathName2[i] = getImgPath(reformImg.getRoleId(), reformImg.getAfterPathName(), getCommonParameters().getHousingDtlImageLargeSize(),
								reformImg.getAfterFileName(), REFORM_IMG_MODE);

						// リフォーム後_外観・周辺写真 コメント【hidden】「配列」
						afterPathComment[i] = reformImg.getAfterComment();

						// リフォーム前_外観・周辺写真番号【hidden】「配列」
						beforePathNo[i] = String.valueOf(i);

						// リフォーム前_外観・周辺写真パス１【hidden】「配列」
						beforePathName1[i] = getImgPath(reformImg.getRoleId(), reformImg.getBeforePathName(), getCommonParameters().getHousingDtlImageSmallSize(),
								reformImg.getBeforeFileName(), REFORM_IMG_MODE);

						// リフォーム前_外観・周辺写真パス２【hidden】「配列」
						beforePathName2[i] = getImgPath(reformImg.getRoleId(), reformImg.getBeforePathName(), getCommonParameters().getHousingDtlImageLargeSize(),
								reformImg.getBeforeFileName(), REFORM_IMG_MODE);

						// リフォーム前_外観・周辺写真 コメント【hidden】「配列」
						beforePathComment[i] = reformImg.getBeforeComment();

						i++;
					}

				} else {

					// リフォーム後_外観・周辺写真番号【hidden】「配列」
					afterPathNo[j] = String.valueOf(j);

					// リフォーム後_外観・周辺写真パス１【hidden】「配列」
					afterPathName1[j] = getImgPath(reformImg.getRoleId(), reformImg.getAfterPathName(), getCommonParameters().getHousingDtlImageSmallSize(),
							reformImg.getAfterFileName(), REFORM_IMG_MODE);

					// リフォーム後_外観・周辺写真パス２【hidden】「配列」
					afterPathName2[j] = getImgPath(reformImg.getRoleId(), reformImg.getAfterPathName(), getCommonParameters().getHousingDtlImageLargeSize(),
							reformImg.getAfterFileName(), REFORM_IMG_MODE);

					// リフォーム後_外観・周辺写真 コメント【hidden】「配列」
					afterPathComment[j] = reformImg.getAfterComment();

					// リフォーム前_外観・周辺写真番号【hidden】「配列」
					beforePathNo[j] = String.valueOf(j);

					// リフォーム前_外観・周辺写真パス１【hidden】「配列」
					beforePathName1[j] = getImgPath(reformImg.getRoleId(), reformImg.getBeforePathName(), getCommonParameters().getHousingDtlImageSmallSize(),
							reformImg.getBeforeFileName(), REFORM_IMG_MODE);

					// リフォーム前_外観・周辺写真パス２【hidden】「配列」
					beforePathName2[j] = getImgPath(reformImg.getRoleId(), reformImg.getBeforePathName(), getCommonParameters().getHousingDtlImageLargeSize(),
							reformImg.getBeforeFileName(), REFORM_IMG_MODE);

					// リフォーム前_外観・周辺写真 コメント【hidden】「配列」
					beforePathComment[j] = reformImg.getBeforeComment();
				}
			}

			// リフォーム後_外観・周辺写真番号【hidden】「配列」
			setAfterPathNoHidden(afterPathNo);

			// リフォーム後_外観・周辺写真パス１【hidden】「配列」
			setAfterPath1(afterPathName1);

			// リフォーム後_外観・周辺写真パス２【hidden】「配列」
			setAfterPath2(afterPathName2);

			// リフォーム後_外観・周辺写真 コメント【hidden】「配列」
			setAfterPathComment(afterPathComment);

			// リフォーム前_外観・周辺写真番号【hidden】「配列」
			setBeforePathNoHidden(beforePathNo);

			// リフォーム前_外観・周辺写真パス１【hidden】「配列」
			setBeforePath1(beforePathName1);

			// リフォーム前_外観・周辺写真パス２【hidden】「配列」
			setBeforePath2(beforePathName2);

			// リフォーム後_外観・周辺写真 コメント【hidden】「配列」
			setBeforePathComment(beforePathComment);
		}

		if (reformPlan != null) {

			// 非会員の場合
			if (!isMemberFlg()) {

				// 全員可の動画が閲覧とする。
				if (PanaCommonConstant.ROLE_ID_PUBLIC.equals(reformPlan.getRoleId())) {

					// リフォーム後_動画用サムネイル【hidden】「配列」
					setAfterMovieUrl(reformPlan.getAfterMovieUrl());

					// リフォーム前_動画用サムネイル【hidden】「配列」
					setBeforeMovieUrl(reformPlan.getBeforeMovieUrl());
				}

			} else {

				// リフォーム後_動画用サムネイル【hidden】「配列」
				setAfterMovieUrl(reformPlan.getAfterMovieUrl());

				// リフォーム前_動画用サムネイル【hidden】「配列」
				setBeforeMovieUrl(reformPlan.getBeforeMovieUrl());
			}
		}
	}

	/**
	 * 渡されたバリーオブジェクトから 住宅診断情報を設定する。<br/>
	 * <br/>
	 *
	 * @param housingInspectionList 物件インスペクション
	 * @param housingExtInfosMap 物件拡張属性情報
	 *
	 * @exception Exception 実装クラスによりスローされる任意の例外
	 */
	private void setInspection(List<HousingInspection> housingInspectionList, Map<String, Map<String, String>> housingExtInfosMap) throws Exception {

		if (housingExtInfosMap != null) {

			// カテゴリ名に該当する Map を取得する。
			Map<String, String> housingInspectionMap = housingExtInfosMap.get("housingInspection");

			if (housingInspectionMap != null) {

				// 住宅診断実施状況
				setInspectionExist(housingInspectionMap.get("inspectionExist"));

				// 住宅診断情報図【hidden】
				setInspectionImagePathName(getImgPath(PanaCommonConstant.ROLE_ID_PRIVATE, housingInspectionMap.get("inspectionImagePathName"), getCommonParameters()
						.getAdminSiteChartFolder(),
						housingInspectionMap.get("inspectionImageFileName")));

				// 住宅診断ファイル【hidden】
				setInspectionPathName(getImgPath(PanaCommonConstant.ROLE_ID_PRIVATE, housingInspectionMap.get("inspectionPathName"), getCommonParameters()
						.getAdminSitePdfFolder(),
						housingInspectionMap.get("inspectionFileName")));
			}
		}

		if (housingInspectionList != null && housingInspectionList.size() > 0) {
			int cnt = housingInspectionList.size();
			String[] inspectionNo = new String[cnt];
			String[] inspectionKey = new String[cnt];
			String[] inspectionTrust = new String[cnt];

			for (int i = 0; i < cnt; i++) {

				HousingInspection housingInspection = housingInspectionList.get(i);

				// 確認レベル番号【hidden】「配列」
				inspectionNo[i] = String.valueOf(i);

				// 確認レベル（名称）「配列」
				inspectionKey[i] = housingInspection.getInspectionKey();

				// 確認レベル「配列」
				inspectionTrust[i] = defaultString(housingInspection.getInspectionTrust());
			}

			// 確認レベル番号【hidden】「配列」
			setInspectionNoHidden(inspectionNo);

			// 確認レベル（名称）「配列」
			setInspectionKey(inspectionKey);

			// 確認レベル「配列」
			setInspectionTrust(inspectionTrust);
		}
	}

	/**
	 * 渡されたバリーオブジェクトから 会員登録の表示フラグを設定する。<br/>
	 * <br/>
	 *
	 * @exception Exception 実装クラスによりスローされる任意の例外
	 */
	private void setMemberLoginFlg() throws Exception {

		// 会員フラグ ＝ falseかつ、（内観画像フラグ ＝ trueまたは、住宅診断実施有）かつ、「物件種類CD」 ≠ 「03：土地」の場合
		if (!isMemberFlg() && (isIntrospectImgFlg() || PanaCommonConstant.HOUSING_INSPECTION_EXIST.equals(getInspectionExist()))
				&& !PanaCommonConstant.HOUSING_KIND_CD_GROUND.equals(getHousingKindCd())) {
			// 会員登録の表示フラグ
			setLoginDisplayFlg(true);
		}
	}

	/**
	 * 渡されたバリーオブジェクトから その他のリフォームプランを設定する。<br/>
	 * <br/>
	 *
	 * @exception Exception 実装クラスによりスローされる任意の例外
	 */
	protected void setOtherReformPlan() throws Exception {

		// 「物件種類CD」 ＝ 「03：土地」の場合
		if (PanaCommonConstant.HOUSING_KIND_CD_GROUND.equals(getHousingKindCd())) {
			// その他のリフォームプランの表示フラグ
			setOtherReformPlanDisplayFlg(false);

		} else {

			// その他のリフォームプランの表示フラグ
			setOtherReformPlanDisplayFlg(true);

			// モード ＝ detailの場合
			if (DETAIL_MODE.equals(getMode())) {
				// その他のプラン番号【hidden】「配列」
				setOtherPlanNoHidden(getPlanNoHidden());

				// その他のプランタイプ「配列」
				setOtherPlanType(getPlanType());

				// その他の総額１「配列」
				setOtherTotalPrice1(getTotalPrice1());

				// その他の総額２「配列」
				setOtherTotalPrice2(getTotalPrice2());

				// その他のシステムリフォームCD【hidden】「配列」
				setOtherReformCdHidden(getReformCdHidden());

				// その他のシステムリフォームURL【hidden】「配列」
				setOtherReformUrl(getReformUrl());

			} else if (REFORM_MODE.equals(getMode())) {

				if (getPlanNoHidden() != null && getPlanNoHidden().length > 1) {

					int cnt = getPlanNoHidden().length;
					int j = 0;
					String[] planNo = new String[cnt];
					String[] planName = new String[cnt];
					String[] totalPrice1 = new String[cnt];
					String[] totalPrice2 = new String[cnt];
					String[] reformCdHidden = new String[cnt];
					String[] reformUrl = new String[cnt];
					String[] reformCategory = new String[cnt];
 
					for (int i = 0; i < getPlanNoHidden().length; i++) {
						if (getReformCdHidden()[i] != null && !getReformCdHidden()[i].equals(getReformCd())) {
							// その他のプラン番号【hidden】「配列」
							planNo[j] = String.valueOf(j);

							// その他のプランタイプ「配列」
							planName[j] = getPlanType()[i];

							// その他の総額１「配列」
							totalPrice1[j] = getTotalPrice1()[i];

							// その他の総額２「配列」
							totalPrice2[j] = getTotalPrice2()[i];

							// その他のシステムリフォームCD【hidden】「配列」
							reformCdHidden[j] = getReformCdHidden()[i];

							// その他のシステムリフォームURL【hidden】「配列」
							reformUrl[j] = getReformUrl()[i];
							
							// set reformCategory
							reformCategory[j] = getReformCategory()[i];

							j++;
						}
					}

					// その他のプラン番号【hidden】「配列」
					setOtherPlanNoHidden(planNo);

					// その他のプランタイプ「配列」
					setOtherPlanType(planName);

					// その他の総額１「配列」
					setOtherTotalPrice1(totalPrice1);

					// その他の総額２「配列」
					setOtherTotalPrice2(totalPrice2);

					// その他のシステムリフォームCD【hidden】「配列」
					setOtherReformCdHidden(reformCdHidden);

					// その他のシステムリフォームURL【hidden】「配列」
					setOtherReformUrl(reformUrl);
					
					setReformCategory(reformCategory);
					
				}
			}
		}
	}

	/**
	 * 渡されたバリーオブジェクトから 最近見た物件を設定する。<br/>
	 * <br/>
	 *
	 * @param recentlyInfoList 最近見た物件情報
	 *
	 * @exception Exception 実装クラスによりスローされる任意の例外
	 */
	private void setRecentlyInfo(List<PanaHousing> recentlyInfoList) throws Exception {

		if (recentlyInfoList != null && recentlyInfoList.size() > 0) {

			// 最近見た物件画面表示件数
			int cnt = 4;

			int i = 0;
			String[] recentlyNoHidden = new String[cnt];
			String[] recentlySysHousingCdHidden = new String[cnt];
			String[] recentlyHousingCdHidden = new String[cnt];
			String[] recentlyPathName = new String[cnt];
			String[] recentlyHousingKindCd = new String[cnt];
			String[] recentlyDisplayHousingName = new String[cnt];
			String[] recentlyDisplayHousingNameFull = new String[cnt];
			String[] recentlyTotalPrice = new String[cnt];
			String[] recentlyLayoutCd = new String[cnt];
			String[] recentlyAccess = new String[cnt];
			String[] recentlyDtl = new String[cnt];
			String[] recentlyDtlFull = new String[cnt];
			String[] recentlyUrl = new String[cnt];
			List<HousingImageInfo> housingImageInfoList;

			for (PanaHousing housing : recentlyInfoList) {

				if (housing != null) {

					// 最近 物件番号【hidden】「配列」
					recentlyNoHidden[i] = String.valueOf(i);

					// 物件画像情報のリストを取得する。
					housingImageInfoList = housing.getHousingImageInfos();

					if (housingImageInfoList != null && housingImageInfoList.size() > 0) {

						for (HousingImageInfo housingImageInfoTemp : housingImageInfoList) {

							// 画像情報
							jp.co.transcosmos.dm3.corePana.vo.HousingImageInfo housingImageInfo = (jp.co.transcosmos.dm3.corePana.vo.HousingImageInfo) housingImageInfoTemp;

							// 非会員の場合
							if (!isMemberFlg()) {

								// 全員可の画像が閲覧とする。
								if (PanaCommonConstant.ROLE_ID_PUBLIC.equals(housingImageInfo.getRoleId())) {

									// 最近 物件画像「配列」
									recentlyPathName[i] = getImgPath(housingImageInfo.getRoleId(), housingImageInfo.getPathName(), getCommonParameters()
											.getHousingDtlHistoryImageSize(), housingImageInfo.getFileName());

									break;
								}

							} else {

								// 最近 物件画像「配列」
								recentlyPathName[i] = getImgPath(housingImageInfo.getRoleId(), housingImageInfo.getPathName(), getCommonParameters()
										.getHousingDtlHistoryImageSize(), housingImageInfo.getFileName());

								break;
							}
						}
					}

					// 物件基本情報を取得する。
					HousingInfo housingInfo = (HousingInfo) housing.getHousingInfo().getItems().get("housingInfo");

					// 建物基本情報を取得する。
					BuildingInfo buildingInfo = (BuildingInfo) housing.getBuilding().getBuildingInfo().getItems().get("buildingInfo");

					// 建物最寄り駅情報を取得する。
					List<JoinResult> buildingStationInfoList = housing.getBuilding().getBuildingStationInfoList();

					if (buildingInfo != null) {
						// 物件種類CD
						recentlyHousingKindCd[i] = buildingInfo.getHousingKindCd();
					}

					if (housingInfo != null) {

						// 最近 システム物件CD【hidden】「配列」
						recentlySysHousingCdHidden[i] = housingInfo.getSysHousingCd();

						// 最近 物件番号【data-number】「配列」
						recentlyHousingCdHidden[i] = housingInfo.getHousingCd();

						// 最近 物件名FULL「配列」
						recentlyDisplayHousingNameFull[i] = housingInfo.getDisplayHousingName();

						// 最近 物件名「配列」
						recentlyDisplayHousingName[i] = (recentlyDisplayHousingNameFull[i] == null) ? ""
								: (recentlyDisplayHousingNameFull[i].length() > 6 ? recentlyDisplayHousingNameFull[i].substring(0, 6) + "…" : recentlyDisplayHousingNameFull[i]);

						// 最近 物件価格「配列」
						recentlyTotalPrice[i] = (housingInfo.getPrice() == null) ? "" : formatPrice(housingInfo.getPrice(), true) + "万円";

						// 最近 物件間取り「配列」
						recentlyLayoutCd[i] = getCodeLookupManager().lookupValue("layoutCd", defaultString(housingInfo.getLayoutCd()));

						if (buildingStationInfoList != null && buildingStationInfoList.size() > 0) {

							StringBuilder sbAccess = new StringBuilder();

							for (int j = 0; j < buildingStationInfoList.size(); j++) {

								// 建物最寄り駅情報
								BuildingStationInfo buildingStationInfo = (BuildingStationInfo) buildingStationInfoList.get(j).getItems().get("buildingStationInfo");
								// 駅名マスタ
								StationMst stationMst = (StationMst) buildingStationInfoList.get(j).getItems().get("stationMst");
								if (stationMst != null) {
									// 駅名
									sbAccess.append(defaultString(stationMst.getStationName(), buildingStationInfo.getStationName()) + " ");
								}
							}

							// 最近 物件アクセス「配列」
							recentlyAccess[i] = sbAccess.toString();
						}

						// 最近 物件詳細FULL「配列」
						if (!isEmpty(recentlyTotalPrice[i]) || !isEmpty(recentlyLayoutCd[i]) || !isEmpty(recentlyAccess[i])) {
							recentlyDtlFull[i] = defaultString(recentlyTotalPrice[i]) + " / " + defaultString(recentlyLayoutCd[i]) + " / " + defaultString(recentlyAccess[i]);
						}

						// 最近 物件詳細「配列」
						recentlyDtl[i] = (recentlyDtlFull[i] == null) ? "" : (recentlyDtlFull[i].length() > 18 ? recentlyDtlFull[i].substring(0, 18) + "…" : recentlyDtlFull[i]);

						// システムリフォームURL【hidden】「配列」
						recentlyUrl[i] = (makeUrl(buildingInfo.getPrefCd(), housingInfo.getSysHousingCd(), buildingInfo.getHousingKindCd()));
					}
				}

				i++;

				if (i >= cnt) {
					break;
				}
			}

			// 最近 物件番号【hidden】「配列」
			setRecentlyNoHidden(recentlyNoHidden);

			// 最近 システム物件CD【hidden】「配列」
			setRecentlySysHousingCdHidden(recentlySysHousingCdHidden);

			// 最近 物件番号【data-number】「配列」
			setRecentlyHousingCdHidden(recentlyHousingCdHidden);

			// 最近 物件画像「配列」
			setRecentlyPathName(recentlyPathName);

			// 最近 物件種類CD「配列」
			setRecentlyHousingKindCd(recentlyHousingKindCd);

			// 最近 物件名「配列」
			setRecentlyDisplayHousingName(recentlyDisplayHousingName);

			// 最近 物件名FULL「配列」
			setRecentlyDisplayHousingNameFull(recentlyDisplayHousingNameFull);

			// 最近 物件詳細「配列」
			setRecentlyDtl(recentlyDtl);

			// 最近 物件詳細FULL「配列」
			setRecentlyDtlFull(recentlyDtlFull);

			// 最近 物件URL
			setRecentlyUrl(recentlyUrl);
		}
	}

	/**
	 * 渡されたバリーオブジェクトから 再検索のリンクを設定する。<br/>
	 * <br/>
	 * @param prefMst 都道府県マスタ
	 * @param prefMstList 都道府県リスト
	 *
	 * @exception Exception 実装クラスによりスローされる任意の例外
	 */
	private void setResearchLink(PrefMst prefMst, List<PrefMst> prefMstList) throws Exception {

		if (prefMst != null && prefMstList != null) {

			int cnt = prefMstList.size();
			int i = 0;
			String[] researchUrl = new String[cnt];
			String[] researchPrefName = new String[cnt];

			// 都道府県リストを繰り返す、再検索URL「配列」と再検索都道府県名「配列」を作成する。
			for (PrefMst prefMstTmp : prefMstList) {

				// 地域CDが一致するレコードを取得
				if (prefMst.getAreaCd() != null && prefMst.getAreaCd().equals(prefMstTmp.getAreaCd())) {

					// 都道府県CDがCodeLookupで定義される場合
					if (!isEmpty(getCodeLookupManager().lookupValue("researchPrefCd", prefMstTmp.getPrefCd()))) {

						// 再検索URL「配列」
						researchUrl[i] = makeUrl(prefMstTmp.getPrefCd(), getHousingKindCd());

						// 再検索都道府県名「配列」
						researchPrefName[i] = prefMstTmp.getPrefName();

						i++;
					}
				}
			}

			// 再検索URL「配列」
			setResearchUrl(researchUrl);

			// 再検索都道府県名「配列」
			setResearchPrefName(researchPrefName);
		}
	}

	/**
	 * 画面の表示フラグを設定する<br/>
	 *
	 */
	public void setDisplayFlg() {

		// タイトルの表示フラグ
		setTitleDisplayFlg(!(isEmpty(getHousingKindCd()) && isEmpty(getSysHousingCd()) && isEmpty(getDisplayHousingName()) && isEmpty(getDtlComment())));

		if (PanaCommonConstant.HOUSING_KIND_CD_MANSION.equals(getHousingKindCd())) {
			// 物件情報の表示フラグ
			setHousingInfoDisplayFlg(!(isEmpty(getIconCd()) && isEmpty(getPrice()) && isEmpty(getAddress()) && isEmpty(getAccess()) && isEmpty(getLayoutCd())
					&& isEmpty(getCompDate()) && isEmpty(getPersonalArea()) && isEmpty(getPersonalAreaSquare()) && isEmpty(getPersonalAreaMemo()) && isEmpty(getFloor())));

			// 物件詳細情報の表示フラグ
			setHousingDtlInfoDisplayFlg(!(isEmpty(getBalconyArea()) && isEmpty(getUpkeepType()) && isEmpty(getLandRight()) && isEmpty(getUsedAreaCd())
					&& isEmpty(getMoveinTiming()) && isEmpty(getMoveinNote()) && isEmpty(getTransactTypeDiv()) && isEmpty(getSpecialInstruction()) && isEmpty(getUpkeepCorp())
					&& isEmpty(getUpkeep()) && isEmpty(getMenteFee()) && isEmpty(getParkingSituation()) && isEmpty(getUpdDate()) && isEmpty(getNextUpdDate())
					&& isEmpty(getDirection()) && isEmpty(getStruct()) && isEmpty(getTotalHouseCnt()) && isEmpty(getScale()) && isEmpty(getStatus()) && isEmpty(getInfrastructure())));
		} else {
			// 物件情報の表示フラグ
			setHousingInfoDisplayFlg(!(isEmpty(getIconCd()) && isEmpty(getPrice()) && isEmpty(getAddress()) && isEmpty(getAccess()) && isEmpty(getLayoutCd())
					&& isEmpty(getCompDate()) && isEmpty(getBuildingArea()) && isEmpty(getBuildingAreaSquare()) && isEmpty(getLandArea()) && isEmpty(getLandAreaSquare())));

			// 物件詳細情報の表示フラグ
			setHousingDtlInfoDisplayFlg(!(isEmpty(getPrivateRoad()) && isEmpty(getLandRight()) && isEmpty(getUsedAreaCd()) && isEmpty(getMoveinTiming())
					&& isEmpty(getMoveinNote()) && isEmpty(getTransactTypeDiv()) && isEmpty(getContactRoad()) && isEmpty(getContactRoadDir()) && isEmpty(getInsurExist())
					&& isEmpty(getSpecialInstruction()) && isEmpty(getUpkeepCorp()) && isEmpty(getParkingSituation()) && isEmpty(getUpdDate()) && isEmpty(getNextUpdDate())
					&& isEmpty(getStruct()) && isEmpty(getStatus()) && isEmpty(getInfrastructure()) && isEmpty(getCoverage()) && isEmpty(getBuildingRate())));
		}

		// 物件のリフォームプランの表示フラグ
		setReformPlanDisplayFlg(!(isEmpty(getPlanType()) && isEmpty(getTotalPrice1()) && isEmpty(getTotalPrice2()) && isEmpty(getReformCdHidden()) && isEmpty(getReformUrl())));

		// 画像の表示フラグ
		setImgDisplayFlg(!(isEmpty(getImgNoHidden()) && isEmpty(getHousingImgPath1Hidden()) && isEmpty(getMovieUrl())));

		// 売主のコメントの表示フラグ
		setSalesCommentDisplayFlg(!isEmpty(getSalesComment()));

		// 担当者からのおすすめの表示フラグ
		setRecommendDisplayFlg(!(isEmpty(getStaffimagePathName()) && isEmpty(getStaffName()) && isEmpty(getCompanyName()) && isEmpty(getBranchName()) && isEmpty(getLicenseNo()) && isEmpty(getRecommendComment())));

		// 物件特徴の表示フラグ
		setHousingPropertyDisplayFlg(!isEmpty(getEquipName()));

		// 地域情報の表示フラグ
		setLandmarkDisplayFlg(!(isEmpty(getLandmarkType()) && isEmpty(getLandmarkName()) && isEmpty(getDistanceFromLandmark())));

		// おすすめリフォームプラン例の表示フラグ
		setRecommendReformPlanDisplayFlg(!(isEmpty(getPlanName()) && isEmpty(getSalesPoint()) && isEmpty(getTotalDtlPrice1()) && isEmpty(getTotalDtlPrice2())
				&& isEmpty(getConstructionPeriod()) && isEmpty(getReformNote()) && isEmpty(getReformImgName()) && isEmpty(getReformPathName()) && isEmpty(getReformPrice())));

		// リフォームイメージの表示フラグ
		setReformImgDisplayFlg(!(isEmpty(getAfterPathNoHidden()) && isEmpty(getAfterPath1()) && isEmpty(getAfterMovieUrl()) && isEmpty(getBeforePathNoHidden())
				&& isEmpty(getBeforePath1()) && isEmpty(getBeforeMovieUrl())));

		// 住宅診断情報の表示フラグ
		setHousingInspectionDisplayFlg(!(isEmpty(getInspectionExist())) && PanaCommonConstant.HOUSING_INSPECTION_EXIST.equals(getInspectionExist()) && isMemberFlg()
				&& !PanaCommonConstant.HOUSING_KIND_CD_GROUND.equals(getHousingKindCd()));

		// その他のリフォームプランの表示フラグ
		setOtherReformPlanDisplayFlg(!(isEmpty(getOtherPlanType()) && isEmpty(getOtherTotalPrice1()) && isEmpty(getOtherTotalPrice2()) && isEmpty(getOtherReformCdHidden()) && isEmpty(getOtherReformUrl())));

		// 最近見た物件の表示フラグ
		setRecentlyDisplayFlg(!(isEmpty(getRecentlyPathName()) && isEmpty(getRecentlyHousingKindCd()) && isEmpty(getRecentlyDisplayHousingName()) && isEmpty(getRecentlyDtl()) && isEmpty(getRecentlyUrl())));
	}

	/**
	 * 渡されたバリーオブジェクトから meta部を設定する。<br/>
	 *
	 */
	public void setMeta() {

		// キーワード
		StringBuilder sbKeywords = new StringBuilder();
		sbKeywords.append(defaultString(getDisplayHousingName()));
		sbKeywords.append(",パナソニック,");
		sbKeywords.append(getCommonParameters().getPanasonicSiteEnglish());
		sbKeywords.append(",");
		sbKeywords.append(getCommonParameters().getPanasonicSiteJapan());
		sbKeywords.append(",Re2,リー・スクエア");

		// キーワード
		setKeywords(sbKeywords.toString());

		// 説明
		StringBuilder sbDescription = new StringBuilder();
		sbDescription.append("パナソニックの");
		sbDescription.append(getCommonParameters().getPanasonicSiteEnglish());
		sbDescription.append("(");
		sbDescription.append(getCommonParameters().getPanasonicSiteJapan());
		sbDescription.append(")｜");
		sbDescription.append(defaultString(getDisplayHousingName()));
		sbDescription.append("の物件情報。");
		sbDescription.append(defaultString(getDtlComment()));
		sbDescription.append("。");
		if (isReformPlanDisplayFlg() || isReformPlanReadyDisplayFlg()) {
			sbDescription.append("物件をもっと活かすリフォームプラン例のご提案も。");
		}

		// 説明
		setDescription(sbDescription.toString());
	}

	/**
	 * URL設定を行う。<br/>
	 * <br/>
	 * @param prefCd 都道府県CD
	 * @param housingKindCd 物件種類CD
	 * @return URL
	 */
	protected String makeUrl(String prefCd, String housingKindCd) {
		return makeUrl(prefCd, null, housingKindCd, null, "list");
	}

	/**
	 * URL設定を行う。<br/>
	 * <br/>
	 * @param prefCd 都道府県CD
	 * @param sysHousingCd システム物件CD
	 * @param housingKindCd 物件種類CD
	 * @return URL
	 */
	protected String makeUrl(String prefCd, String sysHousingCd, String housingKindCd) {
		return makeUrl(prefCd, sysHousingCd, housingKindCd, null, "detail");
	}

	/**
	 * URL設定を行う。<br/>
	 * <br/>
	 * @param prefCd 都道府県CD
	 * @param sysHousingCd システム物件CD
	 * @param housingKindCd 物件種類CD
	 * @param sysReformCd システムリフォームCD
	 * @return URL
	 */
	protected String makeUrl(String prefCd, String sysHousingCd, String housingKindCd, String sysReformCd) {
		return makeUrl(prefCd, sysHousingCd, housingKindCd, sysReformCd, "detail");
	}

	/**
	 * URL設定を行う。<br/>
	 * <br/>
	 * @param prefCd 都道府県CD
	 * @param sysHousingCd システム物件CD
	 * @param housingKindCd 物件種類CD
	 * @param sysReformCd システムリフォームCD
	 * @param mode モード
	 * @return URL
	 */
	protected String makeUrl(String prefCd, String sysHousingCd, String housingKindCd, String sysReformCd, String mode) {
		if (isEmpty(prefCd) || isEmpty(housingKindCd)) {
			return "/";
		}
		StringBuilder sb = new StringBuilder();
		sb.append("/buy/");
		if (PanaCommonConstant.HOUSING_KIND_CD_MANSION.equals(housingKindCd)) {
			sb.append("mansion/");

		} else if (PanaCommonConstant.HOUSING_KIND_CD_HOUSE.equals(housingKindCd)) {
			sb.append("house/");

		} else if (PanaCommonConstant.HOUSING_KIND_CD_GROUND.equals(housingKindCd)) {
			sb.append("ground/");

		}
		sb.append(prefCd);
		sb.append("/");
		sb.append(mode);
		if (!isEmpty(sysHousingCd)) {
			sb.append("/");
			sb.append(sysHousingCd);
		}
		if (!isEmpty(sysReformCd)) {
			sb.append("/");
			sb.append(sysReformCd);
		}
		sb.append("/");
		return sb.toString();
	}

	/**
	 * 画像パス設定を行う。<br/>
	 * <br/>
	 * @param permission 権限
	 * @param pathName 画像パス
	 * @param size 画像サイズ
	 * @param fileName 画像名
	 * @return 画像パス
	 */
	protected String getImgPath(String permission, String pathName, String size, String fileName) {
		return getImgPath(permission, pathName, size, fileName, HOUSING_IMG_MODE);
	}

	/**
	 * 画像パス設定を行う。<br/>
	 * <br/>
	 * @param permission 権限
	 * @param pathName 画像パス
	 * @param size 画像サイズ
	 * @param fileName 画像名
	 * @param mode モード
	 * @return 画像パス
	 */
	protected String getImgPath(String permission, String pathName, String size, String fileName, String mode) {

		String urlPath = "";

		if (isEmpty(pathName) || isEmpty(fileName)) {
			return urlPath;
		}

		if (HOUSING_IMG_MODE.equals(mode)) {
			if (PanaCommonConstant.ROLE_ID_PUBLIC.equals(permission)) {
				urlPath = getPanaFileUtil().getHousFileOpenUrl(pathName, fileName, size);
			} else if (PanaCommonConstant.ROLE_ID_PRIVATE.equals(permission)) {
				urlPath = getPanaFileUtil().getHousFileMemberUrl(pathName, fileName, size);
			}

		} else if (REFORM_IMG_MODE.equals(mode)) {
			if (PanaCommonConstant.ROLE_ID_PUBLIC.equals(permission)) {
				urlPath = getPanaFileUtil().getHousFileOpenUrl(pathName, fileName, size);
			} else if (PanaCommonConstant.ROLE_ID_PRIVATE.equals(permission)) {
				urlPath = getPanaFileUtil().getHousFileMemberUrl(pathName, fileName, size);
			}
		}

		return urlPath;

	}

	protected boolean isEmpty(Object str) {
		return org.springframework.util.StringUtils.isEmpty(str);
	}

	protected boolean isEmpty(Object[] str) {
		if (str == null) {
			return true;
		}
		long i = 0;
		long j = 0;
		for (Object target : str) {
			i++;
			if (org.springframework.util.StringUtils.isEmpty(target)) {
				j++;
			}
		}
		return i == j;
	}

	protected String defaultString(String str) {
		return str == null ? "" : str;
	}

	protected String defaultString(String str, String defaultStr) {
		return !isEmpty(str) ? str : defaultString(defaultStr);
	}

	protected String defaultString(Integer i) {
		return i == null ? "" : i.toString();
	}

	protected String defaultString(Integer i, String defaultStr) {
		return i == null ? "" : defaultStr;
	}

	protected Integer defaultString(Integer i, Integer defaultI) {
		return i == null ? defaultI : i;
	}

	protected String defaultString(Long l) {
		return l == null ? "" : l.toString();
	}

	protected String defaultString(Long l, String defaultStr) {
		return l == null ? "" : defaultStr;
	}

	protected Long defaultString(Long l, Long defaultL) {
		return l == null ? defaultL : l;
	}

	protected String defaultString(BigDecimal bd) {
		return bd == null ? "" : bd.toString();
	}

	protected String defaultString(BigDecimal bd, String defaultStr) {
		return bd == null ? "" : defaultStr;
	}

	protected BigDecimal defaultString(BigDecimal bd, BigDecimal defaultBd) {
		return bd == null ? defaultBd : bd;
	}

	protected String addString(String str1, String str2) {
		return !isEmpty(str1) && !isEmpty(str2) ? str1 + str2 : "";
	}

	protected BigDecimal divide(BigDecimal bd) {
		return bd == null ? BigDecimal.ZERO : bd.divide(new BigDecimal(10000), RoundingMode.CEILING);
	}

	protected Date dateAdd(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, 14);
		return calendar.getTime();
	}

	protected String formatPrice(BigDecimal price, boolean divideFlg) {
		if (isEmpty(price)) {
			return "";
		}
		return divideFlg ? new DecimalFormat(",###").format(divide(price)) : new DecimalFormat(",###").format(price);
	}

	protected String formatPrice(Long price, boolean divideFlg) {
		if (isEmpty(price)) {
			return "";
		}
		return divideFlg ? new DecimalFormat(",###").format(divide(new BigDecimal(price))) : new DecimalFormat(",###").format(new BigDecimal(price));
	}

	protected String formatPrice(String price, boolean divideFlg) {
		if (isEmpty(price)) {
			return "";
		}
		return divideFlg ? new DecimalFormat(",###").format(divide(new BigDecimal(price))) : new DecimalFormat(",###").format(new BigDecimal(price));
	}

	protected String sumPrice(Long l1, Long l2) {
		if (l1 == null && l2 == null) {
			return null;
		}
		return new Long((defaultString(l1, 0l) + defaultString(l2, 0l))).toString();
	}

	protected String sumPrice(String s1, String s2) {
		if (isEmpty(s1) && isEmpty(s2)) {
			return null;
		}
		return new Long((new Long(defaultString(s1, "0")) + new Long(defaultString(s2, "0")))).toString();
	}
}
