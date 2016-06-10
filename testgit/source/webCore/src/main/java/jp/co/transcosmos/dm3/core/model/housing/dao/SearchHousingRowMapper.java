package jp.co.transcosmos.dm3.core.model.housing.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import jp.co.transcosmos.dm3.core.model.BuildingManage;
import jp.co.transcosmos.dm3.core.model.HousingManage;
import jp.co.transcosmos.dm3.core.model.building.Building;
import jp.co.transcosmos.dm3.core.model.housing.Housing;
import jp.co.transcosmos.dm3.core.util.ValueObjectFactory;
import jp.co.transcosmos.dm3.core.vo.BuildingDtlInfo;
import jp.co.transcosmos.dm3.core.vo.BuildingInfo;
import jp.co.transcosmos.dm3.core.vo.HousingInfo;
import jp.co.transcosmos.dm3.dao.JoinResult;

import org.springframework.jdbc.core.RowMapper;

public class SearchHousingRowMapper implements RowMapper<Housing> {

	/** Value �I�u�W�F�N�g�� Factory */
	protected ValueObjectFactory valueObjectFactory;

	/** �������model�i���ʂ��i�[����C���X�^���X�����j */
	protected HousingManage housingManager;
	/** �������Model�i���ʂ��i�[����C���X�^���X�����j */
	protected BuildingManage buildingManager;

	public void setValueObjectFactory(ValueObjectFactory valueObjectFactory) {
		this.valueObjectFactory = valueObjectFactory;
	}

	public void setHousingManager(HousingManage housingManager) {
		this.housingManager = housingManager;
	}

	public void setBuildingManager(BuildingManage buildingManager) {
		this.buildingManager = buildingManager;
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
		HousingInfo housingInfo = (HousingInfo) valueObjectFactory.getValueObject("HousingInfo");
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
		housingInfo.setPrice(result.getLong("housingInfo_price"));
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
		housingInfo.setFloorNo(result.getInt("housingInfo_floorNo"));
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

		// ������{���
		BuildingInfo buildingInfo = (BuildingInfo) valueObjectFactory.getValueObject("BuildingInfo");
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
		buildingInfo.setTotalFloors(result.getInt("buildingInfo_totalFloors"));
		// �o�^��
		buildingInfo.setInsDate(result.getDate("buildingInfo_insDate"));
		// �o�^��
		buildingInfo.setInsUserId(result.getString("buildingInfo_insUserId"));
		// �ŏI�X�V��
		buildingInfo.setUpdDate(result.getDate("buildingInfo_updDate"));
		// �ŏI�X�V��
		buildingInfo.setUpdUserId(result.getString("buildingInfo_updUserId"));

		// �����ڍ׏��
		BuildingDtlInfo buildingDtlInfo = (BuildingDtlInfo) valueObjectFactory.getValueObject("BuildingDtlInfo");
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

		// �evo�ɐݒ肵���������ʂ�Housing�Ɋi�[
		Housing housing = this.housingManager.createHousingInstace();

		Map<String, Object> housingItems = new HashMap<String, Object>();
		housingItems.put("housingInfo", housingInfo);
		JoinResult housingJoinResult = new JoinResult(housingItems);

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
