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

	/** Value オブジェクトの Factory */
	protected ValueObjectFactory valueObjectFactory;

	/** 物件情報model（結果を格納するインスタンス生成） */
	protected HousingManage housingManager;
	/** 建物情報Model（結果を格納するインスタンス生成） */
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
	 * 検索結果を格納する<br/>
	 * 
	 * @param result
	 * @param rowNum
	 * @return 検索結果
	 */
	@Override
	public Housing mapRow(ResultSet result, int rowNum)
			throws SQLException {

		// 物件基本情報
		HousingInfo housingInfo = (HousingInfo) valueObjectFactory.getValueObject("HousingInfo");
		// システム物件CD
		housingInfo.setSysHousingCd(result.getString("housingInfo_sysHousingCd"));
		// 物件番号
		housingInfo.setHousingCd(result.getString("housingInfo_housingCd"));
		// 表示用物件名
		housingInfo.setDisplayHousingName(result.getString("housingInfo_displayHousingName"));
		// 表示用物件名ふりがな
		housingInfo.setDisplayHousingNameKana(result.getString("housingInfo_displayHousingNameKana"));
		// 部屋番号
		housingInfo.setRoomNo(result.getString("housingInfo_roomNo"));
		// システム建物CD
		housingInfo.setSysBuildingCd(result.getString("housingInfo_sysBuildingCd"));
		// 賃料/価格
		housingInfo.setPrice(result.getLong("housingInfo_price"));
		// 管理費
		housingInfo.setUpkeep(result.getLong("housingInfo_upkeep"));
		// 共益費
		housingInfo.setCommonAreaFee(result.getLong("housingInfo_commonAreaFee"));
		// 修繕積立費
		housingInfo.setMenteFee(result.getLong("housingInfo_menteFee"));
		// 敷金
		housingInfo.setSecDeposit(result.getBigDecimal("housingInfo_secDeposit"));
		// 敷金単位
		housingInfo.setSecDepositCrs(result.getString("housingInfo_secDepositCrs"));
		// 保証金
		housingInfo.setBondChrg(result.getBigDecimal("housingInfo_bondChrg"));
		// 保証金単位
		housingInfo.setBondChrgCrs(result.getString("housingInfo_bondChrgCrs"));
		// 敷引礼金区分
		housingInfo.setDepositDiv(result.getString("housingInfo_depositDiv"));
		// 敷引礼金額
		housingInfo.setDeposit(result.getBigDecimal("housingInfo_deposit"));
		// 敷引礼金単位
		housingInfo.setDepositCrs(result.getString("housingInfo_depositCrs"));
		// 間取CD
		housingInfo.setLayoutCd(result.getString("housingInfo_layoutCd"));
		// 間取詳細コメント
		housingInfo.setLayoutComment(result.getString("housingInfo_layoutComment"));
		// 物件の階数
		housingInfo.setFloorNo(result.getInt("housingInfo_floorNo"));
		// 物件の階数コメント
		housingInfo.setFloorNoNote(result.getString("housingInfo_floorNoNote"));
		// 土地面積
		housingInfo.setLandArea(result.getBigDecimal("housingInfo_landArea"));
		// 土地面積_補足
		housingInfo.setLandAreaMemo(result.getString("housingInfo_landAreaMemo"));
		// 専有面積
		housingInfo.setPersonalArea(result.getBigDecimal("housingInfo_personalArea"));
		// 専有面積_補足
		housingInfo.setPersonalAreaMemo(result.getString("housingInfo_personalAreaMemo"));
		// 入居状態フラグ
		housingInfo.setMoveinFlg(result.getString("housingInfo_moveinFlg"));
		// 駐車場の状況
		housingInfo.setParkingSituation(result.getString("housingInfo_parkingSituation"));
		// 駐車場空の有無
		housingInfo.setParkingEmpExist(result.getString("housingInfo_parkingEmpExist"));
		// 表示用駐車場情報
		housingInfo.setDisplayParkingInfo(result.getString("housingInfo_displayParkingInfo"));
		// 窓の向き
		housingInfo.setWindowDirection(result.getString("housingInfo_windowDirection"));
		// アイコン情報
		housingInfo.setIconCd(result.getString("housingInfo_iconCd"));
		// 基本情報コメント
		housingInfo.setBasicComment(result.getString("housingInfo_basicComment"));
		// 登録日
		housingInfo.setInsDate(result.getDate("housingInfo_insDate"));
		// 登録者
		housingInfo.setInsUserId(result.getString("housingInfo_insUserId"));
		// 最終更新日
		housingInfo.setUpdDate(result.getDate("housingInfo_updDate"));
		// 最終更新者
		housingInfo.setUpdUserId(result.getString("housingInfo_updUserId"));

		// 建物基本情報
		BuildingInfo buildingInfo = (BuildingInfo) valueObjectFactory.getValueObject("BuildingInfo");
		// システム建物CD
		buildingInfo.setSysBuildingCd(result.getString("buildingInfo_sysBuildingCd"));
		// 建物番号
		buildingInfo.setBuildingCd(result.getString("buildingInfo_buildingCd"));
		// 物件種類CD
		buildingInfo.setHousingKindCd(result.getString("buildingInfo_housingKindCd"));
		// 建物構造CD
		buildingInfo.setStructCd(result.getString("buildingInfo_structCd"));
		// 表示用建物名
		buildingInfo.setDisplayBuildingName(result.getString("buildingInfo_displayBuildingName"));
		// 表示用建物名ふりがな
		buildingInfo.setDisplayBuildingNameKana(result.getString("buildingInfo_displayBuildingNameKana"));
		// 所在地・郵便番号
		buildingInfo.setZip(result.getString("buildingInfo_zip"));
		// 所在地・都道府県CD
		buildingInfo.setPrefCd(result.getString("buildingInfo_prefCd"));
		// 所在地・市区町村CD
		buildingInfo.setAddressCd(result.getString("buildingInfo_addressCd"));
		// 所在地・市区町村名
		buildingInfo.setAddressName(result.getString("buildingInfo_addressName"));
		// 所在地・町名番地
		buildingInfo.setAddressOther1(result.getString("buildingInfo_addressOther1"));
		// 所在地・建物名その他 
		buildingInfo.setAddressOther2(result.getString("buildingInfo_addressOther2"));
		// 竣工年月
		buildingInfo.setCompDate(result.getDate("buildingInfo_compDate"));
		// 竣工旬
		buildingInfo.setCompTenDays(result.getString("buildingInfo_compTenDays"));
		// 総階数
		buildingInfo.setTotalFloors(result.getInt("buildingInfo_totalFloors"));
		// 登録日
		buildingInfo.setInsDate(result.getDate("buildingInfo_insDate"));
		// 登録者
		buildingInfo.setInsUserId(result.getString("buildingInfo_insUserId"));
		// 最終更新日
		buildingInfo.setUpdDate(result.getDate("buildingInfo_updDate"));
		// 最終更新者
		buildingInfo.setUpdUserId(result.getString("buildingInfo_updUserId"));

		// 建物詳細情報
		BuildingDtlInfo buildingDtlInfo = (BuildingDtlInfo) valueObjectFactory.getValueObject("BuildingDtlInfo");
		// システム建物CD
		buildingDtlInfo.setSysBuildingCd(result.getString("buildingDtlInfo_sysBuildingCd"));
		// 建物面積
		buildingDtlInfo.setBuildingArea(result.getBigDecimal("buildingDtlInfo_buildingArea"));
		// 建物面積_補足
		buildingDtlInfo.setBuildingAreaMemo(result.getString("buildingDtlInfo_buildingAreaMemo"));
		// 建ぺい率
		buildingDtlInfo.setCoverage(result.getBigDecimal("buildingDtlInfo_coverage"));
		// 建ぺい率_補足
		buildingDtlInfo.setCoverageMemo(result.getString("buildingDtlInfo_coverageMemo"));
		// 容積率
		buildingDtlInfo.setBuildingRate(result.getBigDecimal("buildingDtlInfo_buildingRate"));
		// 容積率_補足
		buildingDtlInfo.setBuildingRateMemo(result.getString("buildingDtlInfo_buildingRateMemo"));
		// 総戸数
		buildingDtlInfo.setTotalHouseCnt(result.getInt("buildingDtlInfo_totalHouseCnt"));
		// 賃貸戸数
		buildingDtlInfo.setLeaseHouseCnt(result.getInt("buildingDtlInfo_leaseHouseCnt"));
		// 建物緯度
		buildingDtlInfo.setBuildingLatitude(result.getBigDecimal("buildingDtlInfo_buildingLatitude"));
		// 建物経度
		buildingDtlInfo.setBuildingLongitude(result.getBigDecimal("buildingDtlInfo_buildingLongitude"));

		// 各voに設定した検索結果をHousingに格納
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
