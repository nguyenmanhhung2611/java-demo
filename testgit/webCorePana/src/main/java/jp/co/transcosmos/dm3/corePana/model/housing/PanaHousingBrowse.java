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
 * ���t�H�[����񃁃��e�i���X�̓��̓p�����[�^����p�t�H�[��.
 * <p>
 *
 * <pre>
 * �S����      �C����     �C�����e
 * ------------ ----------- -----------------------------------------------------
 * TRANS     2015.03.10  �V�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 *
 */
public class PanaHousingBrowse {

	PanaHousingBrowse() {
	}

	public PanaHousingBrowse(CodeLookupManager codeLookupManager) {
		this.codeLookupManager = codeLookupManager;
	}

	/** ���ʃR�[�h�ϊ����� */
	private CodeLookupManager codeLookupManager;

	/** �����ԍ� */
	private String housingCd;

	/** �o�^���� */
	private String insDate;

	/** �X�V���� */
	private String updDate;

	/** �ŏI�X�V�ҁiID�j */
	private String updUserId;

	/** �ŏI�X�V�ҁi�����j */
	private String userName;

	/** ���J�敪 */
	private String hiddenFlg;

	/** �X�e�[�^�X */
	private String statusCd;

	/** ����ԍ� */
	private String userId;

	/** ���� */
	private String memberName;

	/** �d�b�ԍ� */
	private String tel;

	/** ���[���A�h���X */
	private String email;

	/** ���l */
	private String note;

	/** �������� */
	private String displayHousingName;

	/** ������� */
	private String housingKindCd;

	/** ���i */
	private String price;

	/** �z�N */
	private String compDate;

	/** �Ԏ�� */
	private String layoutCd;

	/** �Z�� */
	private String address;

	/** �Ŋ��w */
	private String nearStation;

	/** �����ʐ� */
	private String buildingArea1;

	/** �����ʐ�_�� */
	private String buildingArea2;

	/** �����ʐ�_�⑫ */
	private String buildingArea3;

	/** �y�n�ʐ� */
	private String landArea1;

	/** �y�n�ʐ�_�� */
	private String landArea2;

	/** �y�n�ʐ�_�⑫ */
	private String landArea3;

	/** ��L�ʐ� */
	private String personalArea1;

	/** ��L�ʐ�_�� */
	private String personalArea2;

	/** ��L�ʐ�_�⑫ */
	private String personalArea3;

	/** �y�n���� */
	private String landRight;

	/** �����\�� */
	private String structCd;

	/** ����`�� */
	private String transactTypeDiv;

	/** ���ԏ� */
	private String displayParkingInfo;

	/** ���� */
	private String status;

	/** �p�r�n�� */
	private String usedAreaCd;

	/** ���n���� */
	private String moveinTiming;

	/** ���n�����R�����g */
	private String moveinNote;

	/** �ړ��� */
	private String contactRoad;

	/** �ړ�����/���� */
	private String contactRoadDir;

	/** �������S */
	private String privateRoad;

	/** ���؂��� */
	private String coverageMemo;

	/** �e�ϗ� */
	private String buildingRateMemo;

	/** ���ː� */
	private String totalHouseCnt;

	/** �����K�� */
	private String totalFloorsTxt;

	/** �K�� */
	private String scale;

	/** ���݊K�� */
	private String floorCd;

	/** ���݊K���R�����g */
	private String floorNoNote;

	/** ���� */
	private String direction;

	/** �o���R�j�[�ʐ� */
	private String balconyArea;

	/** �Ǘ��� */
	private String upkeep;

	/** �C�U�ϗ��� */
	private String menteFee;

	/** �Ǘ��`�ԁE���� */
	private String upkeepType;

	/** �Ǘ���� */
	private String upkeepCorp;

	/** ���r�ی� */
	private String insurExist;

	/** �S���Җ� */
	private String staffName;

	/** �S���Ҏʐ^����t���O */
	private String staffImageFlg;

	/** �S���Ҏʐ^�v���r���[�p�X */
	private String staffImagePreviewPath;

	/** ��Ж� */
	private String companyName;

	/** �x�Ж� */
	private String branchName;

	/** �Ƌ��ԍ� */
	private String licenseNo;

	/** �C���t�� */
	private String infrastructure;

	/** ���L���� */
	private String specialInstruction;

	/** �����R�����g */
	private String dtlComment;

	/** �S���҂̃R�����g */
	private String basicComment;

	/** ����R�����g */
	private String vendorComment;

	/** ���t�H�[���������R�����g */
	private String reformComment;

	/** ���惊���NURL */
	private String movieUrl;

	/** �ݔ�CD */
	private String equipCd;

	/** �ݔ����� */
	private String equipName;

	/** ���ڐ��� */
	private final static int _CONT = 30;

	/** �ݔ����� */
	private String[] adminEquipName = new String[_CONT];

	/** �ݔ� */
	private String[] adminEquipInfo = new String[_CONT];

	/** ���t�H�[�� */
	private String[] adminEquipReform = new String[_CONT];

	/** �L�[�ԍ� */
	private String[] keyCd = new String[_CONT];

	/** �������߃|�C���g��� */
	private String[] iconCd;

	/** �Z��f�f���{�L�� */
	private String inspectionExist;

	/** �f�f���ʁi���́j */
	private String[] inspectionKey;

	/** �f�f���� */
	private String[] inspectionResult;

	/** �m�F���x�� */
	private String[] inspectionLevel;

	/** �Z��f�f�t�@�C������t���O */
	private String inspectionFileFlg;

	/** ���[�_�[�`���[�g�摜����t���O */
	private String reformChartImageFlg;

	/** �Z��f�f�t�@�C���v���r���[�p�X */
	private String inspectionFilePath;

	/** ���[�_�[�`���[�g�摜�v���r���[�p�X */
	private String inspectionImagePath;

	/** �V�X�e������CD */
	private String sysHousingCd;

	/** �V�X�e�����t�H�[��CD */
	private String sysReformCd;

	/** �C�x���g�t���O */
	private String command;

	/** �V�X�e�������ԍ� */
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
	 * @param staffImagePreviewPath �Z�b�g���� staffImagePreviewPath
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
	 * @param inspectionFilePath �Z�b�g���� inspectionFilePath
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
	 * @param inspectionImagePath �Z�b�g���� inspectionImagePath
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
	 * �����{���i�ҏW���j��ʊi�[<br/>
	 * <br/>
	 *
	 * @param housing
	 *            ���͒l���i�[���ꂽ Housing �I�u�W�F�N�g
	 * @param loginUser
	 *            ���͒l���i�[���ꂽ ���[�U�[��� �I�u�W�F�N�g
	 * @param userRole
	 *            ���͒l���i�[���ꂽ ���[�U�[��� �I�u�W�F�N�g
	 * @param memberInfo
	 *            ���͒l���i�[���ꂽ �}�C�y�[�W��� �I�u�W�F�N�g
	 * @param reformPlanList
	 *            ���͒l���i�[���ꂽ ���t�H�[���v���� �I�u�W�F�N�g
	 * @param housingImageList
	 *            ���͒l���i�[���ꂽ ���t�H�[���v���� �I�u�W�F�N�g
	 * @param commonParameters
	 *            ���ʃp�����[�^�I�u�W�F�N�g
	 * @return resultMap �����{�����
	 * @return fileUtil Panasonic�p�t�@�C�������֘A����
	 */
	public void setPanaHousingBrowse(Map<String, Object> model,
			UserRoleSet userRole, PanaCommonParameters commonParameters, PanaFileUtil fileUtil) {
		PanaHousing housing = (PanaHousing) model.get("housing");

		// ������b���̊i�[
		setHousingBaseInfo(housing);

		// ������{���̊i�[
		List<String> nearStationList = setHousingInfo(housing);
		model.put("nearStationList", nearStationList);

		// �����ڍ׏��̊i�[
		setHousingDtlInfo(housing, commonParameters, fileUtil);

		// ���������̊i�[
		setHousingEquipInfo(housing);

		// �Ǘ��җp�ݔ����̊i�[
		setAdminEquipInfo(housing);

		// �������߃|�C���g���̊i�[
		setRecommendPointInfo(housing);

		// �Z��f�f���̊i�[
		setHousingDiagnoseInfo(housing, commonParameters, fileUtil);

		// �����摜���̊i�[
		List<HousingImageInfo> panaHousingImageList = setHousingImage(housing,
				commonParameters, fileUtil);
		model.put("housingImageList", panaHousingImageList);

		// �n����̊i�[
		List<BuildingLandmark> buildingLandmarkList = setBuildingLandmark(housing);
		model.put("buildingLandmarkList", buildingLandmarkList);
	}

	/**
	 * ������b���̊i�[<br/>
	 * <br/>
	 *
	 * @param housing
	 *            ���͒l���i�[���ꂽ Housing �I�u�W�F�N�g
	 * @param loginUser
	 *            ���͒l���i�[���ꂽ ���[�U�[��� �I�u�W�F�N�g
	 */
	private void setHousingBaseInfo(PanaHousing housing) {

		// ������{���̎擾
		JoinResult housingResult = housing.getHousingInfo();
		HousingInfo housingInfo = (HousingInfo) housingResult.getItems().get(
				"housingInfo");

		// �����ԍ�
		this.setHousingCd(housingInfo.getHousingCd());

		// �o�^����
		String fmtDate = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		if (housingInfo.getInsDate() != null) {
			fmtDate = sdf.format(housingInfo.getInsDate());
		}
		this.setInsDate(fmtDate);

		// �X�V����
		fmtDate = "";
		if (housingInfo.getUpdDate() != null) {
			fmtDate = sdf.format(housingInfo.getUpdDate());
		}
		this.setUpdDate(fmtDate);

		// �ŏI�X�V�ҁiID�j
		this.setUpdUserId(housingInfo.getUpdUserId());

		if (housing.getHousingInfoUpdUser() != null) {
			// �ŏI�X�V�ҁi�����j
			this.setUserName(housing.getHousingInfoUpdUser().getUserName());
		}

		// �V�X�e������CD
		this.setSysHousingCd(housingInfo.getSysHousingCd());
	}

	/**
	 * ������{���̊i�[<br/>
	 * <br/>
	 *
	 * @param housing
	 *            ���͒l���i�[���ꂽ Housing �I�u�W�F�N�g
	 * @return nearStationList �Ŋ��w���X�g
	 */
	private List<String> setHousingInfo(PanaHousing housing) {

		// ������{���̎擾
		JoinResult housingResult = housing.getHousingInfo();
		HousingInfo housingInfo = (HousingInfo) housingResult.getItems().get(
				"housingInfo");

		// ������{���̎擾
		BuildingInfo buildingInfo = (BuildingInfo) housing.getBuilding()
				.getBuildingInfo().getItems().get("buildingInfo");

		// �s���{���}�X�^�̎擾
		PrefMst prefMst = (PrefMst) housing.getBuilding().getBuildingInfo()
				.getItems().get("prefMst");

		// �����ڍ׏��̎擾
		BuildingDtlInfo buildingDtlInfo = (BuildingDtlInfo) housing
				.getBuilding().getBuildingInfo().getItems()
				.get("buildingDtlInfo");

		// �Ŋ��w���̎擾
		List<JoinResult> buildingStationInfoList = housing.getBuilding()
				.getBuildingStationInfoList();

		// ��������
		this.setDisplayHousingName(housingInfo.getDisplayHousingName());

		// �������
		this.setHousingKindCd(buildingInfo.getHousingKindCd());

		// ���i
		if (housingInfo.getPrice() != null) {
			this.setPrice(String.valueOf(housingInfo.getPrice()));
		}

		// �z�N
		String fmtDate = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy�NMM��");
		if (buildingInfo.getCompDate() != null) {
			fmtDate = sdf.format(buildingInfo.getCompDate());
		}
		this.setCompDate(fmtDate);

		// �Ԏ��
		this.setLayoutCd(housingInfo.getLayoutCd());

		// �Z��
		StringBuffer address = new StringBuffer();
		// ���ݒn�E�X�֔ԍ�
		if (buildingInfo.getZip() != null) {
			address.append("��").append(buildingInfo.getZip()).append(" ");
		}
		// �s���{����
		if (prefMst.getPrefName() != null) {
			address.append(prefMst.getPrefName());
		}
		// ���ݒn�E�s�撬����
		if (buildingInfo.getAddressName() != null) {
			address.append(buildingInfo.getAddressName());
		}
		// ���ݒn�E�����Ԓn
		if (buildingInfo.getAddressOther1() != null) {
			address.append(buildingInfo.getAddressOther1());
		}
		// ���ݒn�E���������̑�
		if (buildingInfo.getAddressOther2() != null) {
			address.append(buildingInfo.getAddressOther2());
		}
		this.setAddress(address.toString());

		// �Ŋ��w
		List<String> nearStationList = new ArrayList<String>();
		for (int i = 0; i < buildingStationInfoList.size(); i++) {

			// �����Ŋ��w���̎擾
			BuildingStationInfo buildingStationInfo = (BuildingStationInfo) buildingStationInfoList
					.get(i).getItems().get("buildingStationInfo");
			// �H���}�X�g�̎擾
			RouteMst routeMst = (RouteMst) buildingStationInfoList.get(i)
					.getItems().get("routeMst");
			// �w�}�X�g�̎擾
			StationMst stationMst = (StationMst) buildingStationInfoList.get(i)
					.getItems().get("stationMst");
			// �S����Ѓ}�X�g�̎擾
			RrMst rrMst = (RrMst) buildingStationInfoList.get(i)
					.getItems().get("rrMst");

			// ��\�H����
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
			// �w��
			if (!StringValidateUtil.isEmpty(stationMst.getStationName())) {
				nearStation.append(stationMst.getStationName()+"�w");
			} else {
				if (!StringValidateUtil.isEmpty(buildingStationInfo
						.getStationName())) {
					nearStation.append(buildingStationInfo.getStationName()+"�w");
				}
			}
			nearStation.append(" ");
			// �o�X��Ж�
			if (!StringValidateUtil
					.isEmpty(buildingStationInfo.getBusCompany())) {
				nearStation.append(buildingStationInfo.getBusCompany());
			}

			// �o�X�₩��̓k������
			if (buildingStationInfo.getTimeFromBusStop() != null) {
				nearStation.append(" �k��");
				nearStation.append(String.valueOf(buildingStationInfo.getTimeFromBusStop())).append("��");
			}
			nearStationList.add(nearStation.toString());
		}

		// �����ʐ�
		if (buildingDtlInfo.getBuildingArea() != null) {
			BigDecimal strP = PanaCalcUtil.calcTsubo(buildingDtlInfo
					.getBuildingArea());
			this.setBuildingArea1(String.valueOf(buildingDtlInfo
					.getBuildingArea()));
			this.setBuildingArea2(String.valueOf(strP));
		}
		this.setBuildingArea3(buildingDtlInfo.getBuildingAreaMemo());

		// �y�n�ʐ�
		if (housingInfo.getLandArea() != null) {
			BigDecimal strP = PanaCalcUtil.calcTsubo(housingInfo.getLandArea());
			this.setLandArea1(String.valueOf(housingInfo.getLandArea()));
			this.setLandArea2(String.valueOf(strP));
		}
		this.setLandArea3(housingInfo.getLandAreaMemo());

		// ��L�ʐ�
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
	 * �����ڍ׏��̊i�[<br/>
	 * <br/>
	 *
	 * @param housing
	 *            ���͒l���i�[���ꂽ Housing �I�u�W�F�N�g
	 * @param commonParameters
	 *            ���ʃp�����[�^�I�u�W�F�N�g
	 */
	/**
	 * @param housing
	 * @param commonParameters
	 */
	private void setHousingDtlInfo(PanaHousing housing,
			PanaCommonParameters commonParameters, PanaFileUtil fileUtil) {

		// ������{���̎擾
		JoinResult housingResult = housing.getHousingInfo();
		HousingInfo housingInfo = (HousingInfo) housingResult.getItems().get(
				"housingInfo");

		// �����ڍ׏��̎擾
		HousingDtlInfo housingDtlInfo = (HousingDtlInfo) housingResult
				.getItems().get("housingDtlInfo");

		// ������{���̎擾
		BuildingInfo buildingInfo = (BuildingInfo) housing.getBuilding()
				.getBuildingInfo().getItems().get("buildingInfo");

		// �����ڍ׏��̎擾
		BuildingDtlInfo buildingDtlInfo = (BuildingDtlInfo) housing
				.getBuilding().getBuildingInfo().getItems()
				.get("buildingDtlInfo");

		// �����g���������̃}�b�v�̎擾
		Map<String, Map<String, String>> housingExtInfos = housing
				.getHousingExtInfos();

		// �y�n����
		this.setLandRight(housingDtlInfo.getLandRight());
		// ����`��
		this.setTransactTypeDiv(housingDtlInfo.getTransactTypeDiv());
		// ���ԏ�
		this.setDisplayParkingInfo(housingInfo.getDisplayParkingInfo());

		// �p�r�n��
		this.setUsedAreaCd(housingDtlInfo.getUsedAreaCd());
		// ���n����
		this.setMoveinTiming(housingDtlInfo.getMoveinTiming());
		// ���n�����R�����g
		this.setMoveinNote(housingDtlInfo.getMoveinNote());
		// �ړ���
		this.setContactRoad(housingDtlInfo.getContactRoad());
		// �ړ�����/����
		this.setContactRoadDir(housingDtlInfo.getContactRoadDir());
		// �������S
		this.setPrivateRoad(housingDtlInfo.getPrivateRoad());
		// ���؂���
		this.setCoverageMemo(buildingDtlInfo.getCoverageMemo());
		// �e�ϗ�
		this.setBuildingRateMemo(buildingDtlInfo.getBuildingRateMemo());
		// �����K��
		if (buildingInfo.getTotalFloors() != null) {
			this.setTotalFloorsTxt(String.valueOf(buildingInfo.getTotalFloors()));
		}
		// ���݊K��
		if (housingInfo.getFloorNo() != null) {
			this.setFloorCd(String.valueOf(housingInfo.getFloorNo()));
		}
		// ���݊K���R�����g
		this.setFloorNoNote(housingInfo.getFloorNoNote());
		// �o���R�j�[�ʐ�
		if (housingDtlInfo.getBalconyArea() != null) {
			this.setBalconyArea(housingDtlInfo.getBalconyArea().toString());
		}
		// �Ǘ���
		if (housingInfo.getUpkeep() != null) {
			this.setUpkeep(String.valueOf(housingInfo.getUpkeep()));
		}
		// �C�U�ϗ���
		if (housingInfo.getMenteFee() != null) {
			this.setMenteFee(String.valueOf(housingInfo.getMenteFee()));
		}
		// �Ǘ��`�ԁE����
		this.setUpkeepType(housingDtlInfo.getUpkeepType());
		// �Ǘ����
		this.setUpkeepCorp(housingDtlInfo.getUpkeepCorp());
		// ���r�ی�
		this.setInsurExist(housingDtlInfo.getInsurExist());
		// ���L����
		this.setSpecialInstruction(housingDtlInfo.getSpecialInstruction());
		// �����R�����g
		this.setDtlComment(PanaStringUtils.encodeHtml(housingDtlInfo
				.getDtlComment()));
		// �S���҂̃R�����g
		this.setBasicComment(PanaStringUtils.encodeHtml(housingInfo
				.getBasicComment()));

		// ���t�H�[���������R�����g
		this.setReformComment(PanaStringUtils.encodeHtml(housingInfo
				.getReformComment()));

		if (housingExtInfos != null
				&& housingExtInfos.get("housingDetail") != null) {
			// ����R�����g
			this.setVendorComment(PanaStringUtils.encodeHtml(housingExtInfos
					.get("housingDetail").get("vendorComment")));
			// �����\��
			this.setStructCd(housingExtInfos.get("housingDetail").get("struct"));
			// ����
			this.setStatus(housingExtInfos.get("housingDetail").get("status"));
			// ���ː�
			this.setTotalHouseCnt(housingExtInfos.get("housingDetail").get(
					"totalHouseCnt"));
			// �K��
			this.setScale(housingExtInfos.get("housingDetail").get("scale"));
			// ����
			this.setDirection(housingExtInfos.get("housingDetail").get(
					"direction"));
			// �S���Җ�
			this.setStaffName(housingExtInfos.get("housingDetail").get(
					"staffName"));

			// �S���Ҏʐ^
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

			// ��Ж�
			this.setCompanyName(housingExtInfos.get("housingDetail").get(
					"companyName"));
			// �x�Ж�
			this.setBranchName(housingExtInfos.get("housingDetail").get(
					"branchName"));
			// �Ƌ��ԍ�
			this.setLicenseNo(housingExtInfos.get("housingDetail").get(
					"licenseNo"));
			// �C���t��
			this.setInfrastructure(housingExtInfos.get("housingDetail").get(
					"infrastructure"));
			// ���惊���NURL
			this.setMovieUrl(housingExtInfos.get("housingDetail").get(
					"movieUrl"));
		}
	}

	/**
	 * ���������̊i�[<br/>
	 * <br/>
	 *
	 * @param housing
	 *            ���͒l���i�[���ꂽ Housing �I�u�W�F�N�g
	 */
	private void setHousingEquipInfo(PanaHousing housing) {

		// �����ݔ��̎擾
		Map<String, EquipMst> housingEquipInfos = housing
				.getHousingEquipInfos();

		if (housingEquipInfos != null) {

			StringBuilder cd = new StringBuilder();
			StringBuilder name = new StringBuilder();
			Iterator<Map.Entry<String, EquipMst>> it = housingEquipInfos
					.entrySet().iterator();
			// �����ݔ����̏�����
			EquipMst equip = new EquipMst();

			// �ݔ����͕̂����̏ꍇ
			if (housingEquipInfos.size() > 1) {
				while (it.hasNext()) {
					// �����ݔ����̎擾
					equip = it.next().getValue();
					cd.append(equip.getEquipCd()).append(" �^ ");
					name.append(equip.getEquipName()).append(" �^ ");
				}
				this.setEquipCd(cd.toString().substring(0,
						cd.toString().lastIndexOf(" �^ ")));
				this.setEquipName(name.toString().substring(0,
						name.toString().lastIndexOf(" �^ ")));

				// ��L�ȊO�̏ꍇ�F
			} else if (housingEquipInfos.size() == 1) {
				// �����ݔ����̎擾
				equip = it.next().getValue();
				// �ݔ�CD
				this.setEquipCd(equip.getEquipCd());
				// �ݔ�����
				this.setEquipName(equip.getEquipName());
			}
		}
	}

	/**
	 * �Ǘ��җp�ݔ����̊i�[<br/>
	 * <br/>
	 *
	 * @param housing
	 *            ���͒l���i�[���ꂽ Housing �I�u�W�F�N�g
	 */
	private void setAdminEquipInfo(PanaHousing housing) {

		// ������{�����擾����B
		HousingInfo housingInfo = ((HousingInfo) housing.getHousingInfo()
				.getItems().get("housingInfo"));

		// �\���p���������Z�b�g����B
		this.displayHousingName = housingInfo.getDisplayHousingName();

		// �����g�����������擾����B
		Map<String, Map<String, String>> extMap = housing.getHousingExtInfos();

		// �J�e�S�����ɊY������ Map ���擾����B
		Map<String, String> cateMap = extMap.get("adminEquip");

		if (cateMap == null) {
			return;
		}

		// �ݔ�����
		String[] equipCategory = new String[_CONT];
		// �ݔ�
		String[] equip = new String[_CONT];
		// ���t�H�[��
		String[] reform = new String[_CONT];

		for (int i = 0; i < _CONT; i++) {
			this.keyCd[i] = String.valueOf(i + 1);
		}

		// �Ǘ��җp�ݔ����̎擾
		for (String key : cateMap.keySet()) {
			// Key��
			String keyName = "";

			// Key���ԍ�
			int keyNameNm = 0;

			try {
				// Key��
				keyName = key.substring(0, key.length() - 2);

				// Key���ԍ�
				keyNameNm = Integer.parseInt(key.substring(key.length() - 2));
			} catch (Exception e) {
			}

			// Key�����ݔ����ڂ̏ꍇ
			if ("adminEquipName".equals(keyName)) {
				if (keyNameNm > _CONT) {
					continue;
				}

				equipCategory[keyNameNm - 1] = (cateMap.get(key));

			} else if ("adminEquipInfo".equals(keyName)) {
				if (keyNameNm > _CONT) {
					continue;
				}
				// Key�����ݔ��̏ꍇ
				equip[keyNameNm - 1] = (cateMap.get(key));

			} else if ("adminEquipReform".equals(keyName)) {
				if (keyNameNm > _CONT) {
					continue;
				}
				// Key�������t�H�[���̏ꍇ
				reform[keyNameNm - 1] = (cateMap.get(key));
			}
		}

		// �ݔ�����
		this.setAdminEquipName(equipCategory);
		// �ݔ�
		this.setAdminEquipInfo(equip);
		// ���t�H�[��
		this.setAdminEquipReform(reform);
	}

	/**
	 * �������߃|�C���g���̊i�[<br/>
	 * <br/>
	 *
	 * @param housing
	 *            ���͒l���i�[���ꂽ Housing �I�u�W�F�N�g
	 */
	private void setRecommendPointInfo(PanaHousing housing) {

		// ������{���̎擾
		JoinResult housingResult = housing.getHousingInfo();
		HousingInfo housingInfo = (HousingInfo) housingResult.getItems().get(
				"housingInfo");

		// �������߃|�C���g���
		if (housingInfo.getIconCd() != null) {
			this.iconCd = housingInfo.getIconCd().split(",");
		}
	}

	/**
	 * �Z��f�f���̊i�[<br/>
	 * <br/>
	 *
	 * @param housing
	 *            ���͒l���i�[���ꂽ Housing �I�u�W�F�N�g
	 * @param commonParameters
	 *            ���ʃp�����[�^�I�u�W�F�N�g
	 */
	private void setHousingDiagnoseInfo(PanaHousing housing,
			PanaCommonParameters commonParameters, PanaFileUtil fileUtil) {

		// �����g���������̃}�b�v�̎擾
		Map<String, Map<String, String>> housingExtInfos = housing
				.getHousingExtInfos();

		if (housingExtInfos != null) {

			if (housingExtInfos.get("housingInspection") != null) {

				// �Z��f�f���{�L��
				this.setInspectionExist(housingExtInfos
						.get("housingInspection").get("inspectionExist"));

				// �Z��f�f�t�@�C��
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

				// ���[�_�[�`���[�g�摜
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

		// �f�f���ʁA�m�F���x��
		getInspectionInfo(housing.getHousingInspections());
	}

	/**
	 * �����摜���̊i�[<br/>
	 * <br/>
	 *
	 * @param housing
	 *            ���͒l���i�[���ꂽ PanaHousing �I�u�W�F�N�g
	 * @param commonParameters
	 *            ���ʃp�����[�^�I�u�W�F�N�g
	 * @return buildingLandmarkList �n���񃊃X�g
	 * @return fileUtil
	 * 			Panasonic�p�t�@�C�������֘A����
	 */
	private List<HousingImageInfo> setHousingImage(PanaHousing housing,
			PanaCommonParameters commonParameters, PanaFileUtil fileUtil) {

		List<HousingImageInfo> panaHousingImageList = new ArrayList<HousingImageInfo>();

		// �����摜���̎擾
		List<jp.co.transcosmos.dm3.core.vo.HousingImageInfo> housingImageList = housing
				.getHousingImageInfos();
		if (housingImageList == null) {
			return panaHousingImageList;
		}

		for (jp.co.transcosmos.dm3.core.vo.HousingImageInfo tempImg : housingImageList) {
			HousingImageInfo imgInfo = (HousingImageInfo) tempImg;

			String thumURL = "";
			String fullImgURL = "";
			// �{������������݂̂̏ꍇ
			if (PanaCommonConstant.ROLE_ID_PRIVATE.equals(imgInfo.getRoleId())) {

				// /�u�萔�l�v/[�������CD]/[�s���{��CD]/[�s�撬��CD]/�V�X�e�������ԍ�/
				// �T���l�C���摜�p�X�̐ݒ�
				thumURL = fileUtil.getHousFileMemberUrl(imgInfo.getPathName(), imgInfo.getFileName(),
						String.valueOf(commonParameters.getThumbnailSizes().get(0)));
				// �I���W�i���T�C�Y�̉摜�̐ݒ�
				fullImgURL = fileUtil.getHousFileMemberUrl(imgInfo.getPathName(),
						imgInfo.getFileName(),commonParameters.getAdminSiteFullFolder());

				// �{���������S���̏ꍇ
			} else {

				// /�u�萔�l�v/[�������CD]/[�s���{��CD]/[�s�撬��CD]/�V�X�e�������ԍ�/
				// �T���l�C���摜�p�X�̐ݒ�
				thumURL = fileUtil.getHousFileOpenUrl(imgInfo.getPathName(), imgInfo.getFileName(),
						String.valueOf(commonParameters.getThumbnailSizes().get(0)));
				// �I���W�i���T�C�Y�̉摜�̐ݒ�
				fullImgURL = fileUtil.getHousFileOpenUrl(imgInfo.getPathName(),
						imgInfo.getFileName(),commonParameters.getAdminSiteFullFolder());
			}

			// �I���W�i���T�C�Y�̉摜�\���pURL��pathName�Ɉꎞ�ۑ�
			imgInfo.setPathName(fullImgURL);
			// �T���l�C���̉摜�\���pURL��fileName�Ɉꎞ�ۑ�
			imgInfo.setFileName(thumURL);

			panaHousingImageList.add(imgInfo);
		}
		return panaHousingImageList;
	}

	/**
	 * �n����̊i�[<br/>
	 * <br/>
	 *
	 * @param housing
	 *            ���͒l���i�[���ꂽ Housing �I�u�W�F�N�g
	 * @return buildingLandmarkList �n���񃊃X�g
	 */
	private List<BuildingLandmark> setBuildingLandmark(PanaHousing housing) {

		List<BuildingLandmark> resultList = housing.getBuilding()
				.getBuildingLandmarkList();
		List<BuildingLandmark> buildingLandmarkList = new ArrayList<BuildingLandmark>();

		BuildingLandmark inputEntity = new BuildingLandmark();
		BuildingLandmark ontputEntity = new BuildingLandmark();

		for (int i = 0; i < resultList.size(); i++) {

			ontputEntity = new BuildingLandmark();

			// �n����̎擾
			inputEntity = resultList.get(i);
			// �{�ݖ��̎擾
			ontputEntity.setLandmarkType(inputEntity.getLandmarkType());
			ontputEntity.setLandmarkName(inputEntity.getLandmarkName());
			// �{�ݏ��i����/���v����/�����j�̎擾
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
	 * �f�f���ʁA�m�F���x�����X�g���쐬����B<br>
	 * <br/>
	 *
	 * @param housingInspectionList
	 *            ���͒l���i�[���ꂽ �����C���X�y�N�V���� �I�u�W�F�N�g
	 */
	private void getInspectionInfo(List<HousingInspection> housingInspectionList) {

		if ((PanaCommonConstant.HOUSING_KIND_CD_MANSION
				.equals(this.housingKindCd) || PanaCommonConstant.HOUSING_KIND_CD_HOUSE
				.equals(this.housingKindCd))
				&& housingInspectionList != null) {

			// codeLookupName�̎擾
			String lookUpType = "";
			if (PanaCommonConstant.HOUSING_KIND_CD_MANSION
					.equals(this.housingKindCd)) {
				lookUpType = "inspectionTrustMansion";
			} else if (PanaCommonConstant.HOUSING_KIND_CD_HOUSE
					.equals(this.housingKindCd)) {
				lookUpType = "inspectionTrustHouse";
			}
			// �f�f���ʁA�m�F���x���L�[�l�̎擾
			Iterator<String> iterator = this.codeLookupManager
					.getKeysByLookup(lookUpType);
			List<String> keyList = new ArrayList<String>();
			if (iterator != null) {
				while (iterator.hasNext()) {
					keyList.add(iterator.next());
				}
			}

			// ��ʍ��ڂ̏�����
			String[] inspectionKey = new String[keyList.size()];
			String[] inspectionResult = new String[keyList.size()];
			String[] inspectionLevel = new String[keyList.size()];
			HousingInspection inspection = new HousingInspection();
			for (int i = 0; i < keyList.size(); i++) {

				// codeLookup���f�f���ʃL�[�l�̎擾
				inspectionKey[i] = keyList.get(i);

				inspection = new HousingInspection();
				for (int j = 0; j < housingInspectionList.size(); j++) {

					// DB���f�f���ʃL�[�l�̎擾
					inspection = (HousingInspection) housingInspectionList
							.get(j);

					// �f�f����Value�l�̐ݒ�
					inspectionResult[i] = "";
					inspectionLevel[i] = "";
					if (inspectionKey[i].equals(inspection.getInspectionKey())) {
						// �f�f����
						inspectionResult[i] = String.valueOf(inspection
								.getInspectionValue());
						// �m�F���x��
						inspectionLevel[i] = String.valueOf(inspection
								.getInspectionTrust());
						break;
					}
				}
			}

			// �L�[
			this.setInspectionKey(inspectionKey);
			// �f�f����
			this.setInspectionResult(inspectionResult);
			// �f�f���x��
			this.setInspectionLevel(inspectionLevel);
		}
	}
}
