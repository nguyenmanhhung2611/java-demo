package jp.co.transcosmos.dm3.corePana.model.housing;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import jp.co.transcosmos.dm3.core.vo.BuildingDtlInfo;
import jp.co.transcosmos.dm3.core.vo.BuildingInfo;
import jp.co.transcosmos.dm3.core.vo.BuildingLandmark;
import jp.co.transcosmos.dm3.core.vo.BuildingStationInfo;
import jp.co.transcosmos.dm3.core.vo.EquipMst;
import jp.co.transcosmos.dm3.core.vo.HousingDtlInfo;
import jp.co.transcosmos.dm3.core.vo.PrefMst;
import jp.co.transcosmos.dm3.core.vo.RouteMst;
import jp.co.transcosmos.dm3.core.vo.RrMst;
import jp.co.transcosmos.dm3.core.vo.StationMst;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonConstant;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonParameters;
import jp.co.transcosmos.dm3.corePana.util.PanaCalcUtil;
import jp.co.transcosmos.dm3.corePana.util.PanaFileUtil;
import jp.co.transcosmos.dm3.corePana.util.PanaStringUtils;
import jp.co.transcosmos.dm3.corePana.vo.HousingImageInfo;
import jp.co.transcosmos.dm3.corePana.vo.HousingInfo;
import jp.co.transcosmos.dm3.corePana.vo.HousingInspection;
import jp.co.transcosmos.dm3.dao.JoinResult;
import jp.co.transcosmos.dm3.login.UserRoleSet;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;

/**
 * リフォーム情報メンテナンスの入力パラメータ受取り用フォーム.
 * <p>
 *
 * <pre>
 * 担当者      修正日     修正内容
 * ------------ ----------- -----------------------------------------------------
 * TRANS     2015.03.10  新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 *
 */
public class PanaHousingBrowse {

	PanaHousingBrowse() {
	}

	public PanaHousingBrowse(CodeLookupManager codeLookupManager) {
		this.codeLookupManager = codeLookupManager;
	}

	/** 共通コード変換処理 */
	private CodeLookupManager codeLookupManager;

	/** 物件番号 */
	private String housingCd;

	/** 登録日時 */
	private String insDate;

	/** 更新日時 */
	private String updDate;

	/** 最終更新者（ID） */
	private String updUserId;

	/** 最終更新者（氏名） */
	private String userName;

	/** 公開区分 */
	private String hiddenFlg;

	/** ステータス */
	private String statusCd;

	/** 会員番号 */
	private String userId;

	/** 氏名 */
	private String memberName;

	/** 電話番号 */
	private String tel;

	/** メールアドレス */
	private String email;

	/** 備考 */
	private String note;

	/** 物件名称 */
	private String displayHousingName;

	/** 物件種別 */
	private String housingKindCd;

	/** 価格 */
	private String price;

	/** 築年 */
	private String compDate;

	/** 間取り */
	private String layoutCd;

	/** 住所 */
	private String address;

	/** 最寄り駅 */
	private String nearStation;

	/** 建物面積 */
	private String buildingArea1;

	/** 建物面積_坪 */
	private String buildingArea2;

	/** 建物面積_補足 */
	private String buildingArea3;

	/** 土地面積 */
	private String landArea1;

	/** 土地面積_坪 */
	private String landArea2;

	/** 土地面積_補足 */
	private String landArea3;

	/** 専有面積 */
	private String personalArea1;

	/** 専有面積_坪 */
	private String personalArea2;

	/** 専有面積_補足 */
	private String personalArea3;

	/** 土地権利 */
	private String landRight;

	/** 建物構造 */
	private String structCd;

	/** 取引形態 */
	private String transactTypeDiv;

	/** 駐車場 */
	private String displayParkingInfo;

	/** 現況 */
	private String status;

	/** 用途地域 */
	private String usedAreaCd;

	/** 引渡時期 */
	private String moveinTiming;

	/** 引渡時期コメント */
	private String moveinNote;

	/** 接道状況 */
	private String contactRoad;

	/** 接道方向/幅員 */
	private String contactRoadDir;

	/** 私道負担 */
	private String privateRoad;

	/** 建ぺい率 */
	private String coverageMemo;

	/** 容積率 */
	private String buildingRateMemo;

	/** 総戸数 */
	private String totalHouseCnt;

	/** 建物階数 */
	private String totalFloorsTxt;

	/** 規模 */
	private String scale;

	/** 所在階数 */
	private String floorCd;

	/** 所在階数コメント */
	private String floorNoNote;

	/** 向き */
	private String direction;

	/** バルコニー面積 */
	private String balconyArea;

	/** 管理費 */
	private String upkeep;

	/** 修繕積立費 */
	private String menteFee;

	/** 管理形態・方式 */
	private String upkeepType;

	/** 管理会社 */
	private String upkeepCorp;

	/** 瑕疵保険 */
	private String insurExist;

	/** 担当者名 */
	private String staffName;

	/** 担当者写真ありフラグ */
	private String staffImageFlg;

	/** 担当者写真プレビューパス */
	private String staffImagePreviewPath;

	/** 会社名 */
	private String companyName;

	/** 支社名 */
	private String branchName;

	/** 免許番号 */
	private String licenseNo;

	/** インフラ */
	private String infrastructure;

	/** 特記事項 */
	private String specialInstruction;

	/** 物件コメント */
	private String dtlComment;

	/** 担当者のコメント */
	private String basicComment;

	/** 売主コメント */
	private String vendorComment;

	/** リフォーム準備中コメント */
	private String reformComment;

	/** 動画リンクURL */
	private String movieUrl;

	/** 設備CD */
	private String equipCd;

	/** 設備名称 */
	private String equipName;

	/** 項目数量 */
	private final static int _CONT = 30;

	/** 設備項目 */
	private String[] adminEquipName = new String[_CONT];

	/** 設備 */
	private String[] adminEquipInfo = new String[_CONT];

	/** リフォーム */
	private String[] adminEquipReform = new String[_CONT];

	/** キー番号 */
	private String[] keyCd = new String[_CONT];

	/** おすすめポイント情報 */
	private String[] iconCd;

	/** 住宅診断実施有無 */
	private String inspectionExist;

	/** 診断結果（名称） */
	private String[] inspectionKey;

	/** 診断結果 */
	private String[] inspectionResult;

	/** 確認レベル */
	private String[] inspectionLevel;

	/** 住宅診断ファイルありフラグ */
	private String inspectionFileFlg;

	/** レーダーチャート画像ありフラグ */
	private String reformChartImageFlg;

	/** 住宅診断ファイルプレビューパス */
	private String inspectionFilePath;

	/** レーダーチャート画像プレビューパス */
	private String inspectionImagePath;

	/** システム物件CD */
	private String sysHousingCd;

	/** システムリフォームCD */
	private String sysReformCd;

	/** イベントフラグ */
	private String command;

	/** システム建物番号 */
	private String sysBuildingCd;

	public String getSysHousingCd() {
		return sysHousingCd;
	}

	public void setSysHousingCd(String sysHousingCd) {
		this.sysHousingCd = sysHousingCd;
	}

	public String getDisplayHousingName() {
		return displayHousingName;
	}

	public void setDisplayHousingName(String displayHousingName) {
		this.displayHousingName = displayHousingName;
	}

	public String getUpdDate() {
		return updDate;
	}

	public void setUpdDate(String updDate) {
		this.updDate = updDate;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public CodeLookupManager getCodeLookupManager() {
		return codeLookupManager;
	}

	public void setCodeLookupManager(CodeLookupManager codeLookupManager) {
		this.codeLookupManager = codeLookupManager;
	}

	public String getHousingCd() {
		return housingCd;
	}

	public void setHousingCd(String housingCd) {
		this.housingCd = housingCd;
	}

	public String getInsDate() {
		return insDate;
	}

	public void setInsDate(String insDate) {
		this.insDate = insDate;
	}

	public String getHiddenFlg() {
		return hiddenFlg;
	}

	public void setHiddenFlg(String hiddenFlg) {
		this.hiddenFlg = hiddenFlg;
	}

	public String getStatusCd() {
		return statusCd;
	}

	public void setStatusCd(String statusCd) {
		this.statusCd = statusCd;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getUpdUserId() {
		return updUserId;
	}

	public void setUpdUserId(String updUserId) {
		this.updUserId = updUserId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getHousingKindCd() {
		return housingKindCd;
	}

	public void setHousingKindCd(String housingKindCd) {
		this.housingKindCd = housingKindCd;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getCompDate() {
		return compDate;
	}

	public void setCompDate(String compDate) {
		this.compDate = compDate;
	}

	public String getLayoutCd() {
		return layoutCd;
	}

	public void setLayoutCd(String layoutCd) {
		this.layoutCd = layoutCd;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getNearStation() {
		return nearStation;
	}

	public void setNearStation(String nearStation) {
		this.nearStation = nearStation;
	}

	public String getBuildingArea1() {
		return buildingArea1;
	}

	public void setBuildingArea1(String buildingArea1) {
		this.buildingArea1 = buildingArea1;
	}

	public String getBuildingArea2() {
		return buildingArea2;
	}

	public void setBuildingArea2(String buildingArea2) {
		this.buildingArea2 = buildingArea2;
	}

	public String getBuildingArea3() {
		return buildingArea3;
	}

	public void setBuildingArea3(String buildingArea3) {
		this.buildingArea3 = buildingArea3;
	}

	public String getLandArea1() {
		return landArea1;
	}

	public void setLandArea1(String landArea1) {
		this.landArea1 = landArea1;
	}

	public String getLandArea2() {
		return landArea2;
	}

	public void setLandArea2(String landArea2) {
		this.landArea2 = landArea2;
	}

	public String getLandArea3() {
		return landArea3;
	}

	public void setLandArea3(String landArea3) {
		this.landArea3 = landArea3;
	}

	public String getPersonalArea1() {
		return personalArea1;
	}

	public void setPersonalArea1(String personalArea1) {
		this.personalArea1 = personalArea1;
	}

	public String getPersonalArea2() {
		return personalArea2;
	}

	public void setPersonalArea2(String personalArea2) {
		this.personalArea2 = personalArea2;
	}

	public String getPersonalArea3() {
		return personalArea3;
	}

	public void setPersonalArea3(String personalArea3) {
		this.personalArea3 = personalArea3;
	}

	public String getLandRight() {
		return landRight;
	}

	public void setLandRight(String landRight) {
		this.landRight = landRight;
	}

	public String getStructCd() {
		return structCd;
	}

	public void setStructCd(String structCd) {
		this.structCd = structCd;
	}

	public String getTransactTypeDiv() {
		return transactTypeDiv;
	}

	public void setTransactTypeDiv(String transactTypeDiv) {
		this.transactTypeDiv = transactTypeDiv;
	}

	public String getDisplayParkingInfo() {
		return displayParkingInfo;
	}

	public void setDisplayParkingInfo(String displayParkingInfo) {
		this.displayParkingInfo = displayParkingInfo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUsedAreaCd() {
		return usedAreaCd;
	}

	public void setUsedAreaCd(String usedAreaCd) {
		this.usedAreaCd = usedAreaCd;
	}

	public String getMoveinTiming() {
		return moveinTiming;
	}

	public void setMoveinTiming(String moveinTiming) {
		this.moveinTiming = moveinTiming;
	}

	public String getMoveinNote() {
		return moveinNote;
	}

	public void setMoveinNote(String moveinNote) {
		this.moveinNote = moveinNote;
	}

	public String getContactRoad() {
		return contactRoad;
	}

	public void setContactRoad(String contactRoad) {
		this.contactRoad = contactRoad;
	}

	public String getContactRoadDir() {
		return contactRoadDir;
	}

	public void setContactRoadDir(String contactRoadDir) {
		this.contactRoadDir = contactRoadDir;
	}

	public String getPrivateRoad() {
		return privateRoad;
	}

	public void setPrivateRoad(String privateRoad) {
		this.privateRoad = privateRoad;
	}

	public String getCoverageMemo() {
		return coverageMemo;
	}

	public void setCoverageMemo(String coverageMemo) {
		this.coverageMemo = coverageMemo;
	}

	public String getBuildingRateMemo() {
		return buildingRateMemo;
	}

	public void setBuildingRateMemo(String buildingRateMemo) {
		this.buildingRateMemo = buildingRateMemo;
	}

	public String getTotalHouseCnt() {
		return totalHouseCnt;
	}

	public void setTotalHouseCnt(String totalHouseCnt) {
		this.totalHouseCnt = totalHouseCnt;
	}

	public String getTotalFloorsTxt() {
		return totalFloorsTxt;
	}

	public void setTotalFloorsTxt(String totalFloorsTxt) {
		this.totalFloorsTxt = totalFloorsTxt;
	}

	public String getScale() {
		return scale;
	}

	public void setScale(String scale) {
		this.scale = scale;
	}

	public String getFloorCd() {
		return floorCd;
	}

	public void setFloorCd(String floorCd) {
		this.floorCd = floorCd;
	}

	public String getFloorNoNote() {
		return floorNoNote;
	}

	public void setFloorNoNote(String floorNoNote) {
		this.floorNoNote = floorNoNote;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public String getBalconyArea() {
		return balconyArea;
	}

	public void setBalconyArea(String balconyArea) {
		this.balconyArea = balconyArea;
	}

	public String getUpkeep() {
		return upkeep;
	}

	public void setUpkeep(String upkeep) {
		this.upkeep = upkeep;
	}

	public String getMenteFee() {
		return menteFee;
	}

	public void setMenteFee(String menteFee) {
		this.menteFee = menteFee;
	}

	public String getUpkeepType() {
		return upkeepType;
	}

	public void setUpkeepType(String upkeepType) {
		this.upkeepType = upkeepType;
	}

	public String getUpkeepCorp() {
		return upkeepCorp;
	}

	public void setUpkeepCorp(String upkeepCorp) {
		this.upkeepCorp = upkeepCorp;
	}

	public String getInsurExist() {
		return insurExist;
	}

	public void setInsurExist(String insurExist) {
		this.insurExist = insurExist;
	}

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public String getStaffImageFlg() {
		return staffImageFlg;
	}

	public void setStaffImageFlg(String staffImageFlg) {
		this.staffImageFlg = staffImageFlg;
	}

	/**
	 * @return staffImagePreviewPath
	 */
	public String getStaffImagePreviewPath() {
		return staffImagePreviewPath;
	}

	/**
	 * @param staffImagePreviewPath セットする staffImagePreviewPath
	 */
	public void setStaffImagePreviewPath(String staffImagePreviewPath) {
		this.staffImagePreviewPath = staffImagePreviewPath;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getLicenseNo() {
		return licenseNo;
	}

	public void setLicenseNo(String licenseNo) {
		this.licenseNo = licenseNo;
	}

	public String getInfrastructure() {
		return infrastructure;
	}

	public void setInfrastructure(String infrastructure) {
		this.infrastructure = infrastructure;
	}

	public String getSpecialInstruction() {
		return specialInstruction;
	}

	public void setSpecialInstruction(String specialInstruction) {
		this.specialInstruction = specialInstruction;
	}

	public String getDtlComment() {
		return dtlComment;
	}

	public void setDtlComment(String dtlComment) {
		this.dtlComment = dtlComment;
	}

	public String getBasicComment() {
		return basicComment;
	}

	public void setBasicComment(String basicComment) {
		this.basicComment = basicComment;
	}

	public String getReformComment() {
		return reformComment;
	}

	public void setReformComment(String reformComment) {
		this.reformComment = reformComment;
	}

	public String getMovieUrl() {
		return movieUrl;
	}

	public void setMovieUrl(String movieUrl) {
		this.movieUrl = movieUrl;
	}

	public String getEquipCd() {
		return equipCd;
	}

	public void setEquipCd(String equipCd) {
		this.equipCd = equipCd;
	}

	public String getEquipName() {
		return equipName;
	}

	public void setEquipName(String equipName) {
		this.equipName = equipName;
	}

	public String[] getAdminEquipName() {
		return adminEquipName;
	}

	public void setAdminEquipName(String[] adminEquipName) {
		this.adminEquipName = adminEquipName;
	}

	public String[] getAdminEquipInfo() {
		return adminEquipInfo;
	}

	public void setAdminEquipInfo(String[] adminEquipInfo) {
		this.adminEquipInfo = adminEquipInfo;
	}

	public String[] getAdminEquipReform() {
		return adminEquipReform;
	}

	public void setAdminEquipReform(String[] adminEquipReform) {
		this.adminEquipReform = adminEquipReform;
	}

	public String getVendorComment() {
		return vendorComment;
	}

	public void setVendorComment(String vendorComment) {
		this.vendorComment = vendorComment;
	}

	public String[] getKeyCd() {
		return keyCd;
	}

	public void setKeyCd(String[] keyCd) {
		this.keyCd = keyCd;
	}

	public String[] getIconCd() {
		return iconCd;
	}

	public void setIconCd(String[] iconCd) {
		this.iconCd = iconCd;
	}

	public String getInspectionExist() {
		return inspectionExist;
	}

	public void setInspectionExist(String inspectionExist) {
		this.inspectionExist = inspectionExist;
	}

	public String getInspectionFileFlg() {
		return inspectionFileFlg;
	}

	public void setInspectionFileFlg(String inspectionFileFlg) {
		this.inspectionFileFlg = inspectionFileFlg;
	}

	public String getReformChartImageFlg() {
		return reformChartImageFlg;
	}

	public void setReformChartImageFlg(String reformChartImageFlg) {
		this.reformChartImageFlg = reformChartImageFlg;
	}

	/**
	 * @return inspectionFilePath
	 */
	public String getInspectionFilePath() {
		return inspectionFilePath;
	}

	/**
	 * @param inspectionFilePath セットする inspectionFilePath
	 */
	public void setInspectionFilePath(String inspectionFilePath) {
		this.inspectionFilePath = inspectionFilePath;
	}

	/**
	 * @return inspectionImagePath
	 */
	public String getInspectionImagePath() {
		return inspectionImagePath;
	}

	/**
	 * @param inspectionImagePath セットする inspectionImagePath
	 */
	public void setInspectionImagePath(String inspectionImagePath) {
		this.inspectionImagePath = inspectionImagePath;
	}

	public String[] getInspectionKey() {
		return inspectionKey;
	}

	public void setInspectionKey(String[] inspectionKey) {
		this.inspectionKey = inspectionKey;
	}

	public String[] getInspectionResult() {
		return inspectionResult;
	}

	public void setInspectionResult(String[] inspectionResult) {
		this.inspectionResult = inspectionResult;
	}

	public String[] getInspectionLevel() {
		return inspectionLevel;
	}

	public void setInspectionLevel(String[] inspectionLevel) {
		this.inspectionLevel = inspectionLevel;
	}

	public String getSysReformCd() {
		return sysReformCd;
	}

	public void setSysReformCd(String sysReformCd) {
		this.sysReformCd = sysReformCd;
	}

	public String getSysBuildingCd() {
		return sysBuildingCd;
	}

	public void setSysBuildingCd(String sysBuildingCd) {
		this.sysBuildingCd = sysBuildingCd;
	}

	/**
	 * 物件閲覧（編集時）画面格納<br/>
	 * <br/>
	 *
	 * @param housing
	 *            入力値が格納された Housing オブジェクト
	 * @param loginUser
	 *            入力値が格納された ユーザー情報 オブジェクト
	 * @param userRole
	 *            入力値が格納された ユーザー情報 オブジェクト
	 * @param memberInfo
	 *            入力値が格納された マイページ会員 オブジェクト
	 * @param reformPlanList
	 *            入力値が格納された リフォームプラン オブジェクト
	 * @param housingImageList
	 *            入力値が格納された リフォームプラン オブジェクト
	 * @param commonParameters
	 *            共通パラメータオブジェクト
	 * @return resultMap 物件閲覧情報
	 * @return fileUtil Panasonic用ファイル処理関連共通
	 */
	public void setPanaHousingBrowse(Map<String, Object> model,
			UserRoleSet userRole, PanaCommonParameters commonParameters, PanaFileUtil fileUtil) {
		PanaHousing housing = (PanaHousing) model.get("housing");

		// 物件基礎情報の格納
		setHousingBaseInfo(housing);

		// 物件基本情報の格納
		List<String> nearStationList = setHousingInfo(housing);
		model.put("nearStationList", nearStationList);

		// 物件詳細情報の格納
		setHousingDtlInfo(housing, commonParameters, fileUtil);

		// 物件特長の格納
		setHousingEquipInfo(housing);

		// 管理者用設備情報の格納
		setAdminEquipInfo(housing);

		// おすすめポイント情報の格納
		setRecommendPointInfo(housing);

		// 住宅診断情報の格納
		setHousingDiagnoseInfo(housing, commonParameters, fileUtil);

		// 物件画像情報の格納
		List<HousingImageInfo> panaHousingImageList = setHousingImage(housing,
				commonParameters, fileUtil);
		model.put("housingImageList", panaHousingImageList);

		// 地域情報の格納
		List<BuildingLandmark> buildingLandmarkList = setBuildingLandmark(housing);
		model.put("buildingLandmarkList", buildingLandmarkList);
	}

	/**
	 * 物件基礎情報の格納<br/>
	 * <br/>
	 *
	 * @param housing
	 *            入力値が格納された Housing オブジェクト
	 * @param loginUser
	 *            入力値が格納された ユーザー情報 オブジェクト
	 */
	private void setHousingBaseInfo(PanaHousing housing) {

		// 物件基本情報の取得
		JoinResult housingResult = housing.getHousingInfo();
		HousingInfo housingInfo = (HousingInfo) housingResult.getItems().get(
				"housingInfo");

		// 物件番号
		this.setHousingCd(housingInfo.getHousingCd());

		// 登録日時
		String fmtDate = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		if (housingInfo.getInsDate() != null) {
			fmtDate = sdf.format(housingInfo.getInsDate());
		}
		this.setInsDate(fmtDate);

		// 更新日時
		fmtDate = "";
		if (housingInfo.getUpdDate() != null) {
			fmtDate = sdf.format(housingInfo.getUpdDate());
		}
		this.setUpdDate(fmtDate);

		// 最終更新者（ID）
		this.setUpdUserId(housingInfo.getUpdUserId());

		if (housing.getHousingInfoUpdUser() != null) {
			// 最終更新者（氏名）
			this.setUserName(housing.getHousingInfoUpdUser().getUserName());
		}

		// システム物件CD
		this.setSysHousingCd(housingInfo.getSysHousingCd());
	}

	/**
	 * 物件基本情報の格納<br/>
	 * <br/>
	 *
	 * @param housing
	 *            入力値が格納された Housing オブジェクト
	 * @return nearStationList 最寄り駅リスト
	 */
	private List<String> setHousingInfo(PanaHousing housing) {

		// 物件基本情報の取得
		JoinResult housingResult = housing.getHousingInfo();
		HousingInfo housingInfo = (HousingInfo) housingResult.getItems().get(
				"housingInfo");

		// 建物基本情報の取得
		BuildingInfo buildingInfo = (BuildingInfo) housing.getBuilding()
				.getBuildingInfo().getItems().get("buildingInfo");

		// 都道府県マスタの取得
		PrefMst prefMst = (PrefMst) housing.getBuilding().getBuildingInfo()
				.getItems().get("prefMst");

		// 建物詳細情報の取得
		BuildingDtlInfo buildingDtlInfo = (BuildingDtlInfo) housing
				.getBuilding().getBuildingInfo().getItems()
				.get("buildingDtlInfo");

		// 最寄り駅情報の取得
		List<JoinResult> buildingStationInfoList = housing.getBuilding()
				.getBuildingStationInfoList();

		// 物件名称
		this.setDisplayHousingName(housingInfo.getDisplayHousingName());

		// 物件種別
		this.setHousingKindCd(buildingInfo.getHousingKindCd());

		// 価格
		if (housingInfo.getPrice() != null) {
			this.setPrice(String.valueOf(housingInfo.getPrice()));
		}

		// 築年
		String fmtDate = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月");
		if (buildingInfo.getCompDate() != null) {
			fmtDate = sdf.format(buildingInfo.getCompDate());
		}
		this.setCompDate(fmtDate);

		// 間取り
		this.setLayoutCd(housingInfo.getLayoutCd());

		// 住所
		StringBuffer address = new StringBuffer();
		// 所在地・郵便番号
		if (buildingInfo.getZip() != null) {
			address.append("〒").append(buildingInfo.getZip()).append(" ");
		}
		// 都道府県名
		if (prefMst.getPrefName() != null) {
			address.append(prefMst.getPrefName());
		}
		// 所在地・市区町村名
		if (buildingInfo.getAddressName() != null) {
			address.append(buildingInfo.getAddressName());
		}
		// 所在地・町名番地
		if (buildingInfo.getAddressOther1() != null) {
			address.append(buildingInfo.getAddressOther1());
		}
		// 所在地・建物名その他
		if (buildingInfo.getAddressOther2() != null) {
			address.append(buildingInfo.getAddressOther2());
		}
		this.setAddress(address.toString());

		// 最寄り駅
		List<String> nearStationList = new ArrayList<String>();
		for (int i = 0; i < buildingStationInfoList.size(); i++) {

			// 建物最寄り駅情報の取得
			BuildingStationInfo buildingStationInfo = (BuildingStationInfo) buildingStationInfoList
					.get(i).getItems().get("buildingStationInfo");
			// 路線マストの取得
			RouteMst routeMst = (RouteMst) buildingStationInfoList.get(i)
					.getItems().get("routeMst");
			// 駅マストの取得
			StationMst stationMst = (StationMst) buildingStationInfoList.get(i)
					.getItems().get("stationMst");
			// 鉄道会社マストの取得
			RrMst rrMst = (RrMst) buildingStationInfoList.get(i)
					.getItems().get("rrMst");

			// 代表路線名
			StringBuffer nearStation = new StringBuffer();
			if (!StringValidateUtil.isEmpty(routeMst.getRouteName())) {
				nearStation.append(rrMst.getRrName()+routeMst.getRouteNameFull());
			} else {
				if (!StringValidateUtil.isEmpty(buildingStationInfo
						.getDefaultRouteName())) {
					nearStation.append(buildingStationInfo
							.getDefaultRouteName());
				}
			}
			nearStation.append(" ");
			// 駅名
			if (!StringValidateUtil.isEmpty(stationMst.getStationName())) {
				nearStation.append(stationMst.getStationName()+"駅");
			} else {
				if (!StringValidateUtil.isEmpty(buildingStationInfo
						.getStationName())) {
					nearStation.append(buildingStationInfo.getStationName()+"駅");
				}
			}
			nearStation.append(" ");
			// バス会社名
			if (!StringValidateUtil
					.isEmpty(buildingStationInfo.getBusCompany())) {
				nearStation.append(buildingStationInfo.getBusCompany());
			}

			// バス停からの徒歩時間
			if (buildingStationInfo.getTimeFromBusStop() != null) {
				nearStation.append(" 徒歩");
				nearStation.append(String.valueOf(buildingStationInfo.getTimeFromBusStop())).append("分");
			}
			nearStationList.add(nearStation.toString());
		}

		// 建物面積
		if (buildingDtlInfo.getBuildingArea() != null) {
			BigDecimal strP = PanaCalcUtil.calcTsubo(buildingDtlInfo
					.getBuildingArea());
			this.setBuildingArea1(String.valueOf(buildingDtlInfo
					.getBuildingArea()));
			this.setBuildingArea2(String.valueOf(strP));
		}
		this.setBuildingArea3(buildingDtlInfo.getBuildingAreaMemo());

		// 土地面積
		if (housingInfo.getLandArea() != null) {
			BigDecimal strP = PanaCalcUtil.calcTsubo(housingInfo.getLandArea());
			this.setLandArea1(String.valueOf(housingInfo.getLandArea()));
			this.setLandArea2(String.valueOf(strP));
		}
		this.setLandArea3(housingInfo.getLandAreaMemo());

		// 専有面積
		if (housingInfo.getPersonalArea() != null) {
			BigDecimal strP = PanaCalcUtil.calcTsubo(housingInfo
					.getPersonalArea());
			this.setPersonalArea1(String.valueOf(housingInfo.getPersonalArea()));
			this.setPersonalArea2(String.valueOf(strP));
		}
		this.setPersonalArea3(housingInfo.getPersonalAreaMemo());

		this.setSysBuildingCd(buildingInfo.getSysBuildingCd());

		return nearStationList;
	}

	/**
	 * 物件詳細情報の格納<br/>
	 * <br/>
	 *
	 * @param housing
	 *            入力値が格納された Housing オブジェクト
	 * @param commonParameters
	 *            共通パラメータオブジェクト
	 */
	/**
	 * @param housing
	 * @param commonParameters
	 */
	private void setHousingDtlInfo(PanaHousing housing,
			PanaCommonParameters commonParameters, PanaFileUtil fileUtil) {

		// 物件基本情報の取得
		JoinResult housingResult = housing.getHousingInfo();
		HousingInfo housingInfo = (HousingInfo) housingResult.getItems().get(
				"housingInfo");

		// 物件詳細情報の取得
		HousingDtlInfo housingDtlInfo = (HousingDtlInfo) housingResult
				.getItems().get("housingDtlInfo");

		// 建物基本情報の取得
		BuildingInfo buildingInfo = (BuildingInfo) housing.getBuilding()
				.getBuildingInfo().getItems().get("buildingInfo");

		// 建物詳細情報の取得
		BuildingDtlInfo buildingDtlInfo = (BuildingDtlInfo) housing
				.getBuilding().getBuildingInfo().getItems()
				.get("buildingDtlInfo");

		// 物件拡張属性情報のマップの取得
		Map<String, Map<String, String>> housingExtInfos = housing
				.getHousingExtInfos();

		// 土地権利
		this.setLandRight(housingDtlInfo.getLandRight());
		// 取引形態
		this.setTransactTypeDiv(housingDtlInfo.getTransactTypeDiv());
		// 駐車場
		this.setDisplayParkingInfo(housingInfo.getDisplayParkingInfo());

		// 用途地域
		this.setUsedAreaCd(housingDtlInfo.getUsedAreaCd());
		// 引渡時期
		this.setMoveinTiming(housingDtlInfo.getMoveinTiming());
		// 引渡時期コメント
		this.setMoveinNote(housingDtlInfo.getMoveinNote());
		// 接道状況
		this.setContactRoad(housingDtlInfo.getContactRoad());
		// 接道方向/幅員
		this.setContactRoadDir(housingDtlInfo.getContactRoadDir());
		// 私道負担
		this.setPrivateRoad(housingDtlInfo.getPrivateRoad());
		// 建ぺい率
		this.setCoverageMemo(buildingDtlInfo.getCoverageMemo());
		// 容積率
		this.setBuildingRateMemo(buildingDtlInfo.getBuildingRateMemo());
		// 建物階数
		if (buildingInfo.getTotalFloors() != null) {
			this.setTotalFloorsTxt(String.valueOf(buildingInfo.getTotalFloors()));
		}
		// 所在階数
		if (housingInfo.getFloorNo() != null) {
			this.setFloorCd(String.valueOf(housingInfo.getFloorNo()));
		}
		// 所在階数コメント
		this.setFloorNoNote(housingInfo.getFloorNoNote());
		// バルコニー面積
		if (housingDtlInfo.getBalconyArea() != null) {
			this.setBalconyArea(housingDtlInfo.getBalconyArea().toString());
		}
		// 管理費
		if (housingInfo.getUpkeep() != null) {
			this.setUpkeep(String.valueOf(housingInfo.getUpkeep()));
		}
		// 修繕積立費
		if (housingInfo.getMenteFee() != null) {
			this.setMenteFee(String.valueOf(housingInfo.getMenteFee()));
		}
		// 管理形態・方式
		this.setUpkeepType(housingDtlInfo.getUpkeepType());
		// 管理会社
		this.setUpkeepCorp(housingDtlInfo.getUpkeepCorp());
		// 瑕疵保険
		this.setInsurExist(housingDtlInfo.getInsurExist());
		// 特記事項
		this.setSpecialInstruction(housingDtlInfo.getSpecialInstruction());
		// 物件コメント
		this.setDtlComment(PanaStringUtils.encodeHtml(housingDtlInfo
				.getDtlComment()));
		// 担当者のコメント
		this.setBasicComment(PanaStringUtils.encodeHtml(housingInfo
				.getBasicComment()));

		// リフォーム準備中コメント
		this.setReformComment(PanaStringUtils.encodeHtml(housingInfo
				.getReformComment()));

		if (housingExtInfos != null
				&& housingExtInfos.get("housingDetail") != null) {
			// 売主コメント
			this.setVendorComment(PanaStringUtils.encodeHtml(housingExtInfos
					.get("housingDetail").get("vendorComment")));
			// 建物構造
			this.setStructCd(housingExtInfos.get("housingDetail").get("struct"));
			// 現況
			this.setStatus(housingExtInfos.get("housingDetail").get("status"));
			// 総戸数
			this.setTotalHouseCnt(housingExtInfos.get("housingDetail").get(
					"totalHouseCnt"));
			// 規模
			this.setScale(housingExtInfos.get("housingDetail").get("scale"));
			// 向き
			this.setDirection(housingExtInfos.get("housingDetail").get(
					"direction"));
			// 担当者名
			this.setStaffName(housingExtInfos.get("housingDetail").get(
					"staffName"));

			// 担当者写真
			if (housingExtInfos.get("housingDetail").get("staffImageFileName") == null) {
				this.setStaffImageFlg("0");
			} else {
				this.setStaffImageFlg("1");
				String staffImagePreviewPath = fileUtil.getHousFileOpenUrl(
						housingExtInfos.get("housingDetail").get(
								"staffImagePathName"),
						housingExtInfos.get("housingDetail").get(
								"staffImageFileName"),
						commonParameters.getAdminSiteStaffFolder());
				this.setStaffImagePreviewPath(staffImagePreviewPath);
			}

			// 会社名
			this.setCompanyName(housingExtInfos.get("housingDetail").get(
					"companyName"));
			// 支社名
			this.setBranchName(housingExtInfos.get("housingDetail").get(
					"branchName"));
			// 免許番号
			this.setLicenseNo(housingExtInfos.get("housingDetail").get(
					"licenseNo"));
			// インフラ
			this.setInfrastructure(housingExtInfos.get("housingDetail").get(
					"infrastructure"));
			// 動画リンクURL
			this.setMovieUrl(housingExtInfos.get("housingDetail").get(
					"movieUrl"));
		}
	}

	/**
	 * 物件特長の格納<br/>
	 * <br/>
	 *
	 * @param housing
	 *            入力値が格納された Housing オブジェクト
	 */
	private void setHousingEquipInfo(PanaHousing housing) {

		// 物件設備の取得
		Map<String, EquipMst> housingEquipInfos = housing
				.getHousingEquipInfos();

		if (housingEquipInfos != null) {

			StringBuilder cd = new StringBuilder();
			StringBuilder name = new StringBuilder();
			Iterator<Map.Entry<String, EquipMst>> it = housingEquipInfos
					.entrySet().iterator();
			// 物件設備情報の初期化
			EquipMst equip = new EquipMst();

			// 設備名称は複数の場合
			if (housingEquipInfos.size() > 1) {
				while (it.hasNext()) {
					// 物件設備情報の取得
					equip = it.next().getValue();
					cd.append(equip.getEquipCd()).append(" ／ ");
					name.append(equip.getEquipName()).append(" ／ ");
				}
				this.setEquipCd(cd.toString().substring(0,
						cd.toString().lastIndexOf(" ／ ")));
				this.setEquipName(name.toString().substring(0,
						name.toString().lastIndexOf(" ／ ")));

				// 上記以外の場合：
			} else if (housingEquipInfos.size() == 1) {
				// 物件設備情報の取得
				equip = it.next().getValue();
				// 設備CD
				this.setEquipCd(equip.getEquipCd());
				// 設備名称
				this.setEquipName(equip.getEquipName());
			}
		}
	}

	/**
	 * 管理者用設備情報の格納<br/>
	 * <br/>
	 *
	 * @param housing
	 *            入力値が格納された Housing オブジェクト
	 */
	private void setAdminEquipInfo(PanaHousing housing) {

		// 物件基本情報を取得する。
		HousingInfo housingInfo = ((HousingInfo) housing.getHousingInfo()
				.getItems().get("housingInfo"));

		// 表示用物件名をセットする。
		this.displayHousingName = housingInfo.getDisplayHousingName();

		// 物件拡張属性情報を取得する。
		Map<String, Map<String, String>> extMap = housing.getHousingExtInfos();

		// カテゴリ名に該当する Map を取得する。
		Map<String, String> cateMap = extMap.get("adminEquip");

		if (cateMap == null) {
			return;
		}

		// 設備項目
		String[] equipCategory = new String[_CONT];
		// 設備
		String[] equip = new String[_CONT];
		// リフォーム
		String[] reform = new String[_CONT];

		for (int i = 0; i < _CONT; i++) {
			this.keyCd[i] = String.valueOf(i + 1);
		}

		// 管理者用設備情報の取得
		for (String key : cateMap.keySet()) {
			// Key名
			String keyName = "";

			// Key名番号
			int keyNameNm = 0;

			try {
				// Key名
				keyName = key.substring(0, key.length() - 2);

				// Key名番号
				keyNameNm = Integer.parseInt(key.substring(key.length() - 2));
			} catch (Exception e) {
			}

			// Key名が設備項目の場合
			if ("adminEquipName".equals(keyName)) {
				if (keyNameNm > _CONT) {
					continue;
				}

				equipCategory[keyNameNm - 1] = (cateMap.get(key));

			} else if ("adminEquipInfo".equals(keyName)) {
				if (keyNameNm > _CONT) {
					continue;
				}
				// Key名が設備の場合
				equip[keyNameNm - 1] = (cateMap.get(key));

			} else if ("adminEquipReform".equals(keyName)) {
				if (keyNameNm > _CONT) {
					continue;
				}
				// Key名がリフォームの場合
				reform[keyNameNm - 1] = (cateMap.get(key));
			}
		}

		// 設備項目
		this.setAdminEquipName(equipCategory);
		// 設備
		this.setAdminEquipInfo(equip);
		// リフォーム
		this.setAdminEquipReform(reform);
	}

	/**
	 * おすすめポイント情報の格納<br/>
	 * <br/>
	 *
	 * @param housing
	 *            入力値が格納された Housing オブジェクト
	 */
	private void setRecommendPointInfo(PanaHousing housing) {

		// 物件基本情報の取得
		JoinResult housingResult = housing.getHousingInfo();
		HousingInfo housingInfo = (HousingInfo) housingResult.getItems().get(
				"housingInfo");

		// おすすめポイント情報
		if (housingInfo.getIconCd() != null) {
			this.iconCd = housingInfo.getIconCd().split(",");
		}
	}

	/**
	 * 住宅診断情報の格納<br/>
	 * <br/>
	 *
	 * @param housing
	 *            入力値が格納された Housing オブジェクト
	 * @param commonParameters
	 *            共通パラメータオブジェクト
	 */
	private void setHousingDiagnoseInfo(PanaHousing housing,
			PanaCommonParameters commonParameters, PanaFileUtil fileUtil) {

		// 物件拡張属性情報のマップの取得
		Map<String, Map<String, String>> housingExtInfos = housing
				.getHousingExtInfos();

		if (housingExtInfos != null) {

			if (housingExtInfos.get("housingInspection") != null) {

				// 住宅診断実施有無
				this.setInspectionExist(housingExtInfos
						.get("housingInspection").get("inspectionExist"));

				// 住宅診断ファイル
				if (housingExtInfos.get("housingInspection").get(
						"inspectionFileName") == null) {
					this.setInspectionFileFlg("0");
				} else {
					this.setInspectionFileFlg("1");
					String inspectionFilePath = fileUtil.getHousFileMemberUrl(
							housingExtInfos.get("housingInspection").get(
									"inspectionPathName"),
							housingExtInfos.get("housingInspection").get(
									"inspectionFileName"),
							commonParameters.getAdminSitePdfFolder());
					this.setInspectionFilePath(inspectionFilePath);
				}

				// レーダーチャート画像
				if (housingExtInfos.get("housingInspection").get(
						"inspectionImageFileName") == null) {
					this.setReformChartImageFlg("0");
				} else {
					this.setReformChartImageFlg("1");
					String inspectionImagePath = fileUtil.getHousFileMemberUrl(
							housingExtInfos.get("housingInspection").get(
									"inspectionImagePathName"),
							housingExtInfos.get("housingInspection").get(
									"inspectionImageFileName"),
							commonParameters.getAdminSiteChartFolder());
					this.setInspectionImagePath(inspectionImagePath);
				}
			}
		}

		// 診断結果、確認レベル
		getInspectionInfo(housing.getHousingInspections());
	}

	/**
	 * 物件画像情報の格納<br/>
	 * <br/>
	 *
	 * @param housing
	 *            入力値が格納された PanaHousing オブジェクト
	 * @param commonParameters
	 *            共通パラメータオブジェクト
	 * @return buildingLandmarkList 地域情報リスト
	 * @return fileUtil
	 * 			Panasonic用ファイル処理関連共通
	 */
	private List<HousingImageInfo> setHousingImage(PanaHousing housing,
			PanaCommonParameters commonParameters, PanaFileUtil fileUtil) {

		List<HousingImageInfo> panaHousingImageList = new ArrayList<HousingImageInfo>();

		// 物件画像情報の取得
		List<jp.co.transcosmos.dm3.core.vo.HousingImageInfo> housingImageList = housing
				.getHousingImageInfos();
		if (housingImageList == null) {
			return panaHousingImageList;
		}

		for (jp.co.transcosmos.dm3.core.vo.HousingImageInfo tempImg : housingImageList) {
			HousingImageInfo imgInfo = (HousingImageInfo) tempImg;

			String thumURL = "";
			String fullImgURL = "";
			// 閲覧権限が会員のみの場合
			if (PanaCommonConstant.ROLE_ID_PRIVATE.equals(imgInfo.getRoleId())) {

				// /「定数値」/[物件種別CD]/[都道府県CD]/[市区町村CD]/システム物件番号/
				// サムネイル画像パスの設定
				thumURL = fileUtil.getHousFileMemberUrl(imgInfo.getPathName(), imgInfo.getFileName(),
						String.valueOf(commonParameters.getThumbnailSizes().get(0)));
				// オリジナルサイズの画像の設定
				fullImgURL = fileUtil.getHousFileMemberUrl(imgInfo.getPathName(),
						imgInfo.getFileName(),commonParameters.getAdminSiteFullFolder());

				// 閲覧権限が全員の場合
			} else {

				// /「定数値」/[物件種別CD]/[都道府県CD]/[市区町村CD]/システム物件番号/
				// サムネイル画像パスの設定
				thumURL = fileUtil.getHousFileOpenUrl(imgInfo.getPathName(), imgInfo.getFileName(),
						String.valueOf(commonParameters.getThumbnailSizes().get(0)));
				// オリジナルサイズの画像の設定
				fullImgURL = fileUtil.getHousFileOpenUrl(imgInfo.getPathName(),
						imgInfo.getFileName(),commonParameters.getAdminSiteFullFolder());
			}

			// オリジナルサイズの画像表示用URLをpathNameに一時保存
			imgInfo.setPathName(fullImgURL);
			// サムネイルの画像表示用URLをfileNameに一時保存
			imgInfo.setFileName(thumURL);

			panaHousingImageList.add(imgInfo);
		}
		return panaHousingImageList;
	}

	/**
	 * 地域情報の格納<br/>
	 * <br/>
	 *
	 * @param housing
	 *            入力値が格納された Housing オブジェクト
	 * @return buildingLandmarkList 地域情報リスト
	 */
	private List<BuildingLandmark> setBuildingLandmark(PanaHousing housing) {

		List<BuildingLandmark> resultList = housing.getBuilding()
				.getBuildingLandmarkList();
		List<BuildingLandmark> buildingLandmarkList = new ArrayList<BuildingLandmark>();

		BuildingLandmark inputEntity = new BuildingLandmark();
		BuildingLandmark ontputEntity = new BuildingLandmark();

		for (int i = 0; i < resultList.size(); i++) {

			ontputEntity = new BuildingLandmark();

			// 地域情報の取得
			inputEntity = resultList.get(i);
			// 施設名の取得
			ontputEntity.setLandmarkType(inputEntity.getLandmarkType());
			ontputEntity.setLandmarkName(inputEntity.getLandmarkName());
			// 施設情報（名称/所要時間/距離）の取得
			ontputEntity.setDistanceFromLandmark(inputEntity
					.getDistanceFromLandmark());
			if (inputEntity.getDistanceFromLandmark() != null) {
				BigDecimal time = PanaCalcUtil.calcLandMarkTime(inputEntity
						.getDistanceFromLandmark());
				if (time != null) {
					ontputEntity.setTimeFromLandmark(time.intValue());
				}
			}
			buildingLandmarkList.add(ontputEntity);
		}

		return buildingLandmarkList;
	}

	/**
	 * 診断結果、確認レベルリストを作成する。<br>
	 * <br/>
	 *
	 * @param housingInspectionList
	 *            入力値が格納された 物件インスペクション オブジェクト
	 */
	private void getInspectionInfo(List<HousingInspection> housingInspectionList) {

		if ((PanaCommonConstant.HOUSING_KIND_CD_MANSION
				.equals(this.housingKindCd) || PanaCommonConstant.HOUSING_KIND_CD_HOUSE
				.equals(this.housingKindCd))
				&& housingInspectionList != null) {

			// codeLookupNameの取得
			String lookUpType = "";
			if (PanaCommonConstant.HOUSING_KIND_CD_MANSION
					.equals(this.housingKindCd)) {
				lookUpType = "inspectionTrustMansion";
			} else if (PanaCommonConstant.HOUSING_KIND_CD_HOUSE
					.equals(this.housingKindCd)) {
				lookUpType = "inspectionTrustHouse";
			}
			// 診断結果、確認レベルキー値の取得
			Iterator<String> iterator = this.codeLookupManager
					.getKeysByLookup(lookUpType);
			List<String> keyList = new ArrayList<String>();
			if (iterator != null) {
				while (iterator.hasNext()) {
					keyList.add(iterator.next());
				}
			}

			// 画面項目の初期化
			String[] inspectionKey = new String[keyList.size()];
			String[] inspectionResult = new String[keyList.size()];
			String[] inspectionLevel = new String[keyList.size()];
			HousingInspection inspection = new HousingInspection();
			for (int i = 0; i < keyList.size(); i++) {

				// codeLookupより診断結果キー値の取得
				inspectionKey[i] = keyList.get(i);

				inspection = new HousingInspection();
				for (int j = 0; j < housingInspectionList.size(); j++) {

					// DBより診断結果キー値の取得
					inspection = (HousingInspection) housingInspectionList
							.get(j);

					// 診断結果Value値の設定
					inspectionResult[i] = "";
					inspectionLevel[i] = "";
					if (inspectionKey[i].equals(inspection.getInspectionKey())) {
						// 診断結果
						inspectionResult[i] = String.valueOf(inspection
								.getInspectionValue());
						// 確認レベル
						inspectionLevel[i] = String.valueOf(inspection
								.getInspectionTrust());
						break;
					}
				}
			}

			// キー
			this.setInspectionKey(inspectionKey);
			// 診断結果
			this.setInspectionResult(inspectionResult);
			// 診断レベル
			this.setInspectionLevel(inspectionLevel);
		}
	}
}
