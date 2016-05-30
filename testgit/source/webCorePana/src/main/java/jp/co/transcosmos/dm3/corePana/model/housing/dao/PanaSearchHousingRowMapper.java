package jp.co.transcosmos.dm3.corePana.model.housing.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import jp.co.transcosmos.dm3.core.model.BuildingManage;
import jp.co.transcosmos.dm3.core.model.HousingManage;
import jp.co.transcosmos.dm3.core.model.building.Building;
import jp.co.transcosmos.dm3.core.model.housing.Housing;
import jp.co.transcosmos.dm3.core.vo.BuildingDtlInfo;
import jp.co.transcosmos.dm3.core.vo.BuildingInfo;
import jp.co.transcosmos.dm3.core.vo.HousingDtlInfo;
import jp.co.transcosmos.dm3.core.vo.PrefMst;
import jp.co.transcosmos.dm3.corePana.util.PanaValueObjectFactory;
import jp.co.transcosmos.dm3.corePana.vo.HousingInfo;
import jp.co.transcosmos.dm3.corePana.vo.HousingStatusInfo;
import jp.co.transcosmos.dm3.dao.JoinResult;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.StringUtils;

public class PanaSearchHousingRowMapper implements RowMapper<Housing> {

	/** PanaValue �I�u�W�F�N�g�� Factory */
	private PanaValueObjectFactory panaValueObjectFactory;

	/** �������model�i���ʂ��i�[����C���X�^���X�����j */
	private HousingManage housingManager;
	/** �������Model�i���ʂ��i�[����C���X�^���X�����j */
	private BuildingManage buildingManager;

	public void setHousingManager(HousingManage housingManager) {
		this.housingManager = housingManager;
	}

	public void setBuildingManager(BuildingManage buildingManager) {
		this.buildingManager = buildingManager;
	}

	/**
     * PanaValue �I�u�W�F�N�g�� Factory��ݒ肷��B<br/>
     * <br/>
     * @param panaValueObjectFactory PanaValue �I�u�W�F�N�g�� Factory
     */
	public void setPanaValueObjectFactory(PanaValueObjectFactory panaValueObjectFactory) {
		this.panaValueObjectFactory = panaValueObjectFactory;
	}

	/**
	 * �������ʂ��i�[����<br/>
	 *
	 * @param result
	 * @param rowNum
	 * @return ��������
	 */
	@Override
	public Housing mapRow(ResultSet result, int rowNum)
			throws SQLException {
		// ������{���
		HousingInfo housingInfo = (HousingInfo) panaValueObjectFactory.getValueObject("HousingInfo");
		// �V�X�e������CD
		housingInfo.setSysHousingCd(result.getString("housingInfo_sysHousingCd"));
		// �����ԍ�
		housingInfo.setHousingCd(result.getString("housingInfo_housingCd"));
		// �\���p������
		housingInfo.setDisplayHousingName(result.getString("housingInfo_displayHousingName"));
		// �\���p�������ӂ肪��
		housingInfo.setDisplayHousingNameKana(result.getString("housingInfo_displayHousingNameKana"));
		// �����ԍ�
		housingInfo.setRoomNo(result.getString("housingInfo_roomNo"));
		// �V�X�e������CD
		housingInfo.setSysBuildingCd(result.getString("housingInfo_sysBuildingCd"));
		// ����/���i
		if(!StringUtils.isEmpty(result.getString("housingInfo_price"))){
			housingInfo.setPrice(result.getLong("housingInfo_price"));
		}

		// �Ǘ���
		housingInfo.setUpkeep(result.getLong("housingInfo_upkeep"));
		// ���v��
		housingInfo.setCommonAreaFee(result.getLong("housingInfo_commonAreaFee"));
		// �C�U�ϗ���
		housingInfo.setMenteFee(result.getLong("housingInfo_menteFee"));
		// �~��
		housingInfo.setSecDeposit(result.getBigDecimal("housingInfo_secDeposit"));
		// �~���P��
		housingInfo.setSecDepositCrs(result.getString("housingInfo_secDepositCrs"));
		// �ۏ؋�
		housingInfo.setBondChrg(result.getBigDecimal("housingInfo_bondChrg"));
		// �ۏ؋��P��
		housingInfo.setBondChrgCrs(result.getString("housingInfo_bondChrgCrs"));
		// �~������敪
		housingInfo.setDepositDiv(result.getString("housingInfo_depositDiv"));
		// �~������z
		housingInfo.setDeposit(result.getBigDecimal("housingInfo_deposit"));
		// �~������P��
		housingInfo.setDepositCrs(result.getString("housingInfo_depositCrs"));
		// �Ԏ�CD
		housingInfo.setLayoutCd(result.getString("housingInfo_layoutCd"));
		// �Ԏ�ڍ׃R�����g
		housingInfo.setLayoutComment(result.getString("housingInfo_layoutComment"));
		// �����̊K��
		if(!StringUtils.isEmpty(result.getString("housingInfo_floorNo"))){
			housingInfo.setFloorNo(result.getInt("housingInfo_floorNo"));
		}

		// �����̊K���R�����g
		housingInfo.setFloorNoNote(result.getString("housingInfo_floorNoNote"));
		// �y�n�ʐ�
		housingInfo.setLandArea(result.getBigDecimal("housingInfo_landArea"));
		// �y�n�ʐ�_�⑫
		housingInfo.setLandAreaMemo(result.getString("housingInfo_landAreaMemo"));
		// ��L�ʐ�
		housingInfo.setPersonalArea(result.getBigDecimal("housingInfo_personalArea"));
		// ��L�ʐ�_�⑫
		housingInfo.setPersonalAreaMemo(result.getString("housingInfo_personalAreaMemo"));
		// ������ԃt���O
		housingInfo.setMoveinFlg(result.getString("housingInfo_moveinFlg"));
		// ���ԏ�̏�
		housingInfo.setParkingSituation(result.getString("housingInfo_parkingSituation"));
		// ���ԏ��̗L��
		housingInfo.setParkingEmpExist(result.getString("housingInfo_parkingEmpExist"));
		// �\���p���ԏ���
		housingInfo.setDisplayParkingInfo(result.getString("housingInfo_displayParkingInfo"));
		// ���̌���
		housingInfo.setWindowDirection(result.getString("housingInfo_windowDirection"));
		// �A�C�R�����
		housingInfo.setIconCd(result.getString("housingInfo_iconCd"));
		// ��{���R�����g
		housingInfo.setBasicComment(result.getString("housingInfo_basicComment"));
		// �o�^��
		housingInfo.setInsDate(result.getDate("housingInfo_insDate"));
		// �o�^��
		housingInfo.setInsUserId(result.getString("housingInfo_insUserId"));
		// �ŏI�X�V��
		housingInfo.setUpdDate(result.getDate("housingInfo_updDate"));
		// �ŏI�X�V��
		housingInfo.setUpdUserId(result.getString("housingInfo_updUserId"));
		// ���t�H�[���v��������������
		housingInfo.setReformComment(result.getString("housingInfo_reformComment"));

		// �����ڍ׏��
		HousingDtlInfo housingDtlInfo = (HousingDtlInfo) panaValueObjectFactory.getValueObject("HousingDtlInfo");
		// �ڍׂ��R�����g
		housingDtlInfo.setDtlComment(result.getString("housingDtlInfo_dtlComment"));

		// ������{���
		BuildingInfo buildingInfo = (BuildingInfo) panaValueObjectFactory.getValueObject("BuildingInfo");
		// �V�X�e������CD
		buildingInfo.setSysBuildingCd(result.getString("buildingInfo_sysBuildingCd"));
		// �����ԍ�
		buildingInfo.setBuildingCd(result.getString("buildingInfo_buildingCd"));
		// �������CD
		buildingInfo.setHousingKindCd(result.getString("buildingInfo_housingKindCd"));
		// �����\��CD
		buildingInfo.setStructCd(result.getString("buildingInfo_structCd"));
		// �\���p������
		buildingInfo.setDisplayBuildingName(result.getString("buildingInfo_displayBuildingName"));
		// �\���p�������ӂ肪��
		buildingInfo.setDisplayBuildingNameKana(result.getString("buildingInfo_displayBuildingNameKana"));
		// ���ݒn�E�X�֔ԍ�
		buildingInfo.setZip(result.getString("buildingInfo_zip"));
		// ���ݒn�E�s���{��CD
		buildingInfo.setPrefCd(result.getString("buildingInfo_prefCd"));
		// ���ݒn�E�s�撬��CD
		buildingInfo.setAddressCd(result.getString("buildingInfo_addressCd"));
		// ���ݒn�E�s�撬����
		buildingInfo.setAddressName(result.getString("buildingInfo_addressName"));
		// ���ݒn�E�����Ԓn
		buildingInfo.setAddressOther1(result.getString("buildingInfo_addressOther1"));
		// ���ݒn�E���������̑�
		buildingInfo.setAddressOther2(result.getString("buildingInfo_addressOther2"));
		// �v�H�N��
		buildingInfo.setCompDate(result.getDate("buildingInfo_compDate"));
		// �v�H�{
		buildingInfo.setCompTenDays(result.getString("buildingInfo_compTenDays"));
		// ���K��
		if(!StringUtils.isEmpty(result.getString("buildingInfo_totalFloors"))){
			buildingInfo.setTotalFloors(result.getInt("buildingInfo_totalFloors"));
		}

		// �o�^��
		buildingInfo.setInsDate(result.getDate("buildingInfo_insDate"));
		// �o�^��
		buildingInfo.setInsUserId(result.getString("buildingInfo_insUserId"));
		// �ŏI�X�V��
		buildingInfo.setUpdDate(result.getDate("buildingInfo_updDate"));
		// �ŏI�X�V��
		buildingInfo.setUpdUserId(result.getString("buildingInfo_updUserId"));

		// �����ڍ׏��
		BuildingDtlInfo buildingDtlInfo = (BuildingDtlInfo) panaValueObjectFactory.getValueObject("BuildingDtlInfo");
		// �V�X�e������CD
		buildingDtlInfo.setSysBuildingCd(result.getString("buildingDtlInfo_sysBuildingCd"));
		// �����ʐ�
		buildingDtlInfo.setBuildingArea(result.getBigDecimal("buildingDtlInfo_buildingArea"));
		// �����ʐ�_�⑫
		buildingDtlInfo.setBuildingAreaMemo(result.getString("buildingDtlInfo_buildingAreaMemo"));
		// ���؂���
		buildingDtlInfo.setCoverage(result.getBigDecimal("buildingDtlInfo_coverage"));
		// ���؂���_�⑫
		buildingDtlInfo.setCoverageMemo(result.getString("buildingDtlInfo_coverageMemo"));
		// �e�ϗ�
		buildingDtlInfo.setBuildingRate(result.getBigDecimal("buildingDtlInfo_buildingRate"));
		// �e�ϗ�_�⑫
		buildingDtlInfo.setBuildingRateMemo(result.getString("buildingDtlInfo_buildingRateMemo"));
		// ���ː�
		buildingDtlInfo.setTotalHouseCnt(result.getInt("buildingDtlInfo_totalHouseCnt"));
		// ���݌ː�
		buildingDtlInfo.setLeaseHouseCnt(result.getInt("buildingDtlInfo_leaseHouseCnt"));
		// �����ܓx
		buildingDtlInfo.setBuildingLatitude(result.getBigDecimal("buildingDtlInfo_buildingLatitude"));
		// �����o�x
		buildingDtlInfo.setBuildingLongitude(result.getBigDecimal("buildingDtlInfo_buildingLongitude"));

		// �����X�e�[�^�X���
		HousingStatusInfo housingStatusInfo = (HousingStatusInfo) panaValueObjectFactory.getValueObject("HousingStatusInfo");
		// �V�X�e������CD
		housingStatusInfo.setSysHousingCd(result.getString("housingStatusInfo_sysHousingCd"));
		// ����J�t���O
		housingStatusInfo.setHiddenFlg(result.getString("housingStatusInfo_hiddenFlg"));
		// �X�e�[�^�XCD
		housingStatusInfo.setStatusCd(result.getString("housingStatusInfo_statusCd"));
		// ���[�U�[ID
		housingStatusInfo.setUserId(result.getString("housingStatusInfo_userId"));
		// ���l
		housingStatusInfo.setNote(result.getString("housingStatusInfo_note"));

		// �s���{���}�X�^
		PrefMst prefMst = (PrefMst) panaValueObjectFactory.getValueObject("PrefMst");
		// �s���{��CD
		prefMst.setPrefCd(result.getString("prefMst_prefCd"));
		// �s���{����
		prefMst.setPrefName(result.getString("prefMst_prefName"));
		// �s���{�������[�}��
		prefMst.setRPrefName(result.getString("prefMst_rPrefName"));
		// �n��CD
		prefMst.setAreaCd(result.getString("prefMst_areaCd"));
		// �\����
		prefMst.setSortOrder(result.getInt("prefMst_sortOrder"));
		// �o�^��
		prefMst.setInsDate(result.getDate("prefMst_insDate"));
		// �o�^��
		prefMst.setInsUserId(result.getString("prefMst_insUserId"));
		// �ŏI�X�V��
		prefMst.setUpdDate(result.getDate("prefMst_updDate"));
		// �ŏI�X�V��
		prefMst.setUpdUserId(result.getString("prefMst_updUserId"));

		Housing housing = this.housingManager.createHousingInstace();
		Map<String, Object> housingItems = new HashMap<String, Object>();
		housingItems.put("housingInfo", housingInfo);
		housingItems.put("housingStatusInfo", housingStatusInfo);
		housingItems.put("housingDtlInfo", housingDtlInfo);
		housingItems.put("prefMst", prefMst);
		JoinResult housingJoinResult = new JoinResult(housingItems);
		housing.setHousingInfo(housingJoinResult);

		Building building = this.buildingManager.createBuildingInstace();

		Map<String, Object> buildingItems = new HashMap<String, Object>();
		buildingItems.put("buildingInfo", buildingInfo);
		buildingItems.put("buildingDtlInfo", buildingDtlInfo);
		JoinResult buildingJoinResult = new JoinResult(buildingItems);
		building.setBuildingInfo(buildingJoinResult);

		housing.setHousingInfo(housingJoinResult);
		housing.setBuilding(building);

		return housing;
	}

}
