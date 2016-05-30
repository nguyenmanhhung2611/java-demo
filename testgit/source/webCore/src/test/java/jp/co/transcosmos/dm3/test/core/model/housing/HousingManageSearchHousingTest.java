package jp.co.transcosmos.dm3.test.core.model.housing;

import java.math.BigDecimal;
import java.util.Calendar;

import jp.co.transcosmos.dm3.core.model.HousingManage;
import jp.co.transcosmos.dm3.core.model.building.Building;
import jp.co.transcosmos.dm3.core.model.housing.Housing;
import jp.co.transcosmos.dm3.core.model.housing.form.HousingFormFactory;
import jp.co.transcosmos.dm3.core.model.housing.form.HousingSearchForm;
import jp.co.transcosmos.dm3.core.vo.BuildingDtlInfo;
import jp.co.transcosmos.dm3.core.vo.BuildingInfo;
import jp.co.transcosmos.dm3.core.vo.BuildingStationInfo;
import jp.co.transcosmos.dm3.core.vo.HousingDtlInfo;
import jp.co.transcosmos.dm3.core.vo.HousingInfo;
import jp.co.transcosmos.dm3.core.vo.HousingPartInfo;
import jp.co.transcosmos.dm3.core.vo.HousingStatusInfo;
import jp.co.transcosmos.dm3.dao.DAO;
import jp.co.transcosmos.dm3.dao.DAOCriteria;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 物件情報 model 物件検索のテストケース
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationContext-test.xml")
public class HousingManageSearchHousingTest {

	@Qualifier("housingManager")
	@Autowired
	private HousingManage housingManage;
	@Autowired
	private HousingFormFactory housingFormFactory;

	@Autowired
	private DAO<HousingInfo> housingInfoDAO;
	@Autowired
	private DAO<HousingPartInfo> housingPartInfoDAO;
	@Autowired
	private DAO<HousingDtlInfo> housingDtlInfoDAO;
	@Autowired
	private DAO<HousingStatusInfo> housingStatusInfoDAO;
	@Autowired
	private DAO<HousingPartInfo> partSrchMstDAO;

	@Autowired
	private DAO<BuildingInfo> buildingInfoDAO;
	@Autowired
	private DAO<BuildingDtlInfo> buildingDtlInfoDAO;
	@Autowired
	private DAO<BuildingStationInfo> buildingStationInfoDAO;

	// 前処理
	@Before
	public void init(){
		// 物件情報全件削除
		DAOCriteria criteria = new DAOCriteria();
		this.housingStatusInfoDAO.deleteByFilter(criteria);
		this.housingPartInfoDAO.deleteByFilter(criteria);
		this.housingInfoDAO.deleteByFilter(criteria);
		this.buildingStationInfoDAO.deleteByFilter(criteria);
		this.buildingDtlInfoDAO.deleteByFilter(criteria);
		this.buildingInfoDAO.deleteByFilter(criteria);

		initTestData();
	}

	// テストデータ作成
	private void initTestData(){

		BuildingInfo buildingInfo;
		BuildingDtlInfo buildingDtlInfo;
		HousingInfo housingInfo;
		HousingStatusInfo housingStatusInfo;
		HousingPartInfo housingPartInfo;
		BuildingStationInfo buildingStationInfo;
		
		Calendar calendar = Calendar.getInstance();
		calendar.clear();

		// ------ 取得対象データ ------

		// ------ 【001】
		// 建物基本情報
		buildingInfo = new BuildingInfo();
		buildingInfo.setSysBuildingCd("SBLD00001");
		buildingInfo.setBuildingCd("BLD00001");
		buildingInfo.setHousingKindCd("001");
		buildingInfo.setStructCd("002");
		buildingInfo.setDisplayBuildingName("建物名０１");
		buildingInfo.setDisplayBuildingNameKana("たてものめい０１");
		buildingInfo.setZip("1234567");
		buildingInfo.setPrefCd("01");
		buildingInfo.setAddressCd("01101");
		buildingInfo.setAddressName("市区町村名");
		buildingInfo.setAddressOther1("町名番地");
		buildingInfo.setAddressOther2("建物名その他");
		calendar.set(2015, 4, 1, 0, 0, 0);
		buildingInfo.setCompDate(calendar.getTime());
		buildingInfo.setCompTenDays("2015年4月上旬");
		buildingInfo.setTotalFloors(5);
		calendar.set(2015, 4, 2, 0, 0, 0);
		buildingInfo.setInsDate(calendar.getTime());
		buildingInfo.setInsUserId("UID00001");
		calendar.set(2015, 4, 3, 0, 0, 0);
		buildingInfo.setUpdDate(calendar.getTime());
		buildingInfo.setUpdUserId("UID00002");
		this.buildingInfoDAO.insert(new BuildingInfo[] {buildingInfo});

		// 建物詳細情報
		buildingDtlInfo = new BuildingDtlInfo();
		buildingDtlInfo.setSysBuildingCd("SBLD00001");
		buildingDtlInfo.setBuildingArea(new BigDecimal("50"));
		buildingDtlInfo.setBuildingAreaMemo("建物面積補足");
		buildingDtlInfo.setCoverage(new BigDecimal("60"));
		buildingDtlInfo.setCoverageMemo("建ぺい率補足");
		buildingDtlInfo.setBuildingRate(new BigDecimal("200"));
		buildingDtlInfo.setBuildingRateMemo("容積率補足");
		buildingDtlInfo.setTotalHouseCnt(10);
		buildingDtlInfo.setLeaseHouseCnt(9);
		buildingDtlInfo.setBuildingLatitude(new BigDecimal(123.4567890));
		buildingDtlInfo.setBuildingLongitude(new BigDecimal(223.4567890));
		this.buildingDtlInfoDAO.insert(new BuildingDtlInfo[] {buildingDtlInfo});

		// 物件基本情報
		housingInfo = new HousingInfo();
		housingInfo.setSysHousingCd("SHOU00001");
		housingInfo.setHousingCd("HOU00001");
		housingInfo.setDisplayHousingName("物件名０１");
		housingInfo.setDisplayHousingNameKana("ぶっけんめい０１");
		housingInfo.setRoomNo("101");
		housingInfo.setSysBuildingCd("SBLD00001");
		housingInfo.setPrice(100000L);
		housingInfo.setUpkeep(5000L);
		housingInfo.setCommonAreaFee(6000L);
		housingInfo.setMenteFee(7000L);
		housingInfo.setSecDeposit(new BigDecimal("200000"));
		housingInfo.setSecDepositCrs("01");
		housingInfo.setBondChrg(new BigDecimal("300000"));
		housingInfo.setBondChrgCrs("02");
		housingInfo.setDepositDiv("03");
		housingInfo.setDeposit(new BigDecimal("400000"));
		housingInfo.setDepositCrs("04");
		housingInfo.setLayoutCd("001");
		housingInfo.setLayoutComment("間取詳細コメント");
		housingInfo.setFloorNo(2);
		housingInfo.setFloorNoNote("物件の階数コメント");
		housingInfo.setLandArea(new BigDecimal("50"));
		housingInfo.setLandAreaMemo("土地面積補足");
		housingInfo.setPersonalArea(new BigDecimal("60"));
		housingInfo.setPersonalAreaMemo("専有面積補足");
		housingInfo.setMoveinFlg("0");
		housingInfo.setParkingSituation("05");
		housingInfo.setParkingEmpExist("1");
		housingInfo.setDisplayParkingInfo("表示用駐車場情報");
		housingInfo.setWindowDirection("06");
		housingInfo.setIconCd("REI,FRE");
		housingInfo.setBasicComment("基本情報コメント");
		calendar.set(2015, 5, 1, 0, 0, 0);
		housingInfo.setInsDate(calendar.getTime());
		housingInfo.setInsUserId("UID00001");
		calendar.set(2015, 5, 2, 0, 0, 0);
		housingInfo.setUpdDate(calendar.getTime());
		housingInfo.setUpdUserId("UID00002");
		this.housingInfoDAO.insert(new HousingInfo[]{housingInfo});

		// 物件ステータス情報
		housingStatusInfo = new HousingStatusInfo();
		housingStatusInfo.setSysHousingCd("SHOU00001");
		housingStatusInfo.setHiddenFlg("0");
		this.housingStatusInfoDAO.insert(new HousingStatusInfo[]{housingStatusInfo});

		// ------ 【002】

		// 建物基本情報
		buildingInfo = new BuildingInfo();
		buildingInfo.setSysBuildingCd("SBLD00002");
		buildingInfo.setBuildingCd("BLD00002");
		buildingInfo.setDisplayBuildingName("建物名０２");
		buildingInfo.setPrefCd("02");
		buildingInfo.setAddressCd("02101");
		calendar.set(2015, 5, 1, 0, 0, 0);
		buildingInfo.setCompDate(calendar.getTime());
		this.buildingInfoDAO.insert(new BuildingInfo[] {buildingInfo});

		// 建物詳細情報
		buildingDtlInfo = new BuildingDtlInfo();
		buildingDtlInfo.setSysBuildingCd("SBLD00002");
		this.buildingDtlInfoDAO.insert(new BuildingDtlInfo[] {buildingDtlInfo});

		// 物件基本情報
		housingInfo = new HousingInfo();
		housingInfo.setSysHousingCd("SHOU00002");
		housingInfo.setHousingCd("HOU00002");
		housingInfo.setDisplayHousingName("物件名０２");
		housingInfo.setSysBuildingCd("SBLD00002");
		housingInfo.setPrice(100001L);
		housingInfo.setPersonalArea(new BigDecimal("65"));
		this.housingInfoDAO.insert(new HousingInfo[]{housingInfo});

		// 物件ステータス情報
		housingStatusInfo = new HousingStatusInfo();
		housingStatusInfo.setSysHousingCd("SHOU00002");
		housingStatusInfo.setHiddenFlg("0");
		this.housingStatusInfoDAO.insert(new HousingStatusInfo[]{housingStatusInfo});

		// ------ 【003】

		// 建物基本情報
		buildingInfo = new BuildingInfo();
		buildingInfo.setSysBuildingCd("SBLD00003");
		buildingInfo.setBuildingCd("BLD00003");
		buildingInfo.setDisplayBuildingName("建物名０３");
		buildingInfo.setPrefCd("03");
		buildingInfo.setAddressCd("03101");
		this.buildingInfoDAO.insert(new BuildingInfo[] {buildingInfo});

		// 建物詳細情報
		buildingDtlInfo = new BuildingDtlInfo();
		buildingDtlInfo.setSysBuildingCd("SBLD00003");
		this.buildingDtlInfoDAO.insert(new BuildingDtlInfo[] {buildingDtlInfo});

		// 物件基本情報
		housingInfo = new HousingInfo();
		housingInfo.setSysHousingCd("SHOU00003");
		housingInfo.setHousingCd("HOU00003");
		housingInfo.setDisplayHousingName("物件名０３");
		housingInfo.setSysBuildingCd("SBLD00003");
		this.housingInfoDAO.insert(new HousingInfo[]{housingInfo});

		// 物件ステータス情報
		housingStatusInfo = new HousingStatusInfo();
		housingStatusInfo.setSysHousingCd("SHOU00003");
		housingStatusInfo.setHiddenFlg("0");
		this.housingStatusInfoDAO.insert(new HousingStatusInfo[]{housingStatusInfo});

		// ------ 【004】

		// 建物基本情報
		buildingInfo = new BuildingInfo();
		buildingInfo.setSysBuildingCd("SBLD00004");
		buildingInfo.setBuildingCd("BLD00004");
		buildingInfo.setDisplayBuildingName("建物名０４");
		buildingInfo.setPrefCd("04");
		buildingInfo.setAddressCd("04101");
		this.buildingInfoDAO.insert(new BuildingInfo[] {buildingInfo});

		// 建物詳細情報
		buildingDtlInfo = new BuildingDtlInfo();
		buildingDtlInfo.setSysBuildingCd("SBLD00004");
		this.buildingDtlInfoDAO.insert(new BuildingDtlInfo[] {buildingDtlInfo});

		// 物件基本情報
		housingInfo = new HousingInfo();
		housingInfo.setSysHousingCd("SHOU00004");
		housingInfo.setHousingCd("HOU00004");
		housingInfo.setDisplayHousingName("物件名０４");
		housingInfo.setSysBuildingCd("SBLD00004");
		this.housingInfoDAO.insert(new HousingInfo[]{housingInfo});

		// 物件ステータス情報
		housingStatusInfo = new HousingStatusInfo();
		housingStatusInfo.setSysHousingCd("SHOU00004");
		housingStatusInfo.setHiddenFlg("0");
		this.housingStatusInfoDAO.insert(new HousingStatusInfo[]{housingStatusInfo});

		// ------ 【005】

		// 建物基本情報
		buildingInfo = new BuildingInfo();
		buildingInfo.setSysBuildingCd("SBLD00005");
		buildingInfo.setBuildingCd("BLD00005");
		buildingInfo.setDisplayBuildingName("建物名０５");
		buildingInfo.setPrefCd("05");
		buildingInfo.setAddressCd("05101");
		this.buildingInfoDAO.insert(new BuildingInfo[] {buildingInfo});

		// 建物詳細情報
		buildingDtlInfo = new BuildingDtlInfo();
		buildingDtlInfo.setSysBuildingCd("SBLD00005");
		this.buildingDtlInfoDAO.insert(new BuildingDtlInfo[] {buildingDtlInfo});

		// 物件基本情報
		housingInfo = new HousingInfo();
		housingInfo.setSysHousingCd("SHOU00005");
		housingInfo.setHousingCd("HOU00005");
		housingInfo.setDisplayHousingName("物件名０５");
		housingInfo.setSysBuildingCd("SBLD00005");
		this.housingInfoDAO.insert(new HousingInfo[]{housingInfo});

		// 物件ステータス情報
		housingStatusInfo = new HousingStatusInfo();
		housingStatusInfo.setSysHousingCd("SHOU00005");
		housingStatusInfo.setHiddenFlg("0");
		this.housingStatusInfoDAO.insert(new HousingStatusInfo[]{housingStatusInfo});

		// 建物最寄駅情報
		buildingStationInfo = new BuildingStationInfo();
		buildingStationInfo.setSysBuildingCd("SBLD00005");
		buildingStationInfo.setDivNo(001);
		buildingStationInfo.setDefaultRouteCd("J002003");
		buildingStationInfo.setStationCd("21137");
		this.buildingStationInfoDAO.insert(new BuildingStationInfo[]{buildingStationInfo});

		// ------ 【006】

		// 建物基本情報
		buildingInfo = new BuildingInfo();
		buildingInfo.setSysBuildingCd("SBLD00006");
		buildingInfo.setBuildingCd("BLD00006");
		buildingInfo.setDisplayBuildingName("建物名０６");
		buildingInfo.setPrefCd("06");
		buildingInfo.setAddressCd("06101");
		this.buildingInfoDAO.insert(new BuildingInfo[] {buildingInfo});

		// 建物詳細情報
		buildingDtlInfo = new BuildingDtlInfo();
		buildingDtlInfo.setSysBuildingCd("SBLD00006");
		this.buildingDtlInfoDAO.insert(new BuildingDtlInfo[] {buildingDtlInfo});

		// 物件基本情報
		housingInfo = new HousingInfo();
		housingInfo.setSysHousingCd("SHOU00006");
		housingInfo.setHousingCd("HOU00006");
		housingInfo.setDisplayHousingName("物件名０６");
		housingInfo.setSysBuildingCd("SBLD00006");
		this.housingInfoDAO.insert(new HousingInfo[]{housingInfo});

		// 物件ステータス情報
		housingStatusInfo = new HousingStatusInfo();
		housingStatusInfo.setSysHousingCd("SHOU00006");
		housingStatusInfo.setHiddenFlg("0");
		this.housingStatusInfoDAO.insert(new HousingStatusInfo[]{housingStatusInfo});

		// 建物最寄駅情報
		buildingStationInfo = new BuildingStationInfo();
		buildingStationInfo.setSysBuildingCd("SBLD00006");
		buildingStationInfo.setDivNo(001);
		buildingStationInfo.setDefaultRouteCd("J002002");
		buildingStationInfo.setStationCd("21392");
		this.buildingStationInfoDAO.insert(new BuildingStationInfo[]{buildingStationInfo});

		// ------ 【007】

		// 建物基本情報
		buildingInfo = new BuildingInfo();
		buildingInfo.setSysBuildingCd("SBLD00007");
		buildingInfo.setBuildingCd("BLD00007");
		buildingInfo.setDisplayBuildingName("建物名０７");
		buildingInfo.setPrefCd("07");
		buildingInfo.setAddressCd("07101");
		this.buildingInfoDAO.insert(new BuildingInfo[] {buildingInfo});

		// 建物詳細情報
		buildingDtlInfo = new BuildingDtlInfo();
		buildingDtlInfo.setSysBuildingCd("SBLD00007");
		this.buildingDtlInfoDAO.insert(new BuildingDtlInfo[] {buildingDtlInfo});

		// 物件基本情報
		housingInfo = new HousingInfo();
		housingInfo.setSysHousingCd("SHOU00007");
		housingInfo.setHousingCd("HOU00007");
		housingInfo.setDisplayHousingName("物件名０７");
		housingInfo.setSysBuildingCd("SBLD00007");
		housingInfo.setPrice(100000L);
		this.housingInfoDAO.insert(new HousingInfo[]{housingInfo});

		// 物件ステータス情報
		housingStatusInfo = new HousingStatusInfo();
		housingStatusInfo.setSysHousingCd("SHOU00007");
		housingStatusInfo.setHiddenFlg("0");
		this.housingStatusInfoDAO.insert(new HousingStatusInfo[]{housingStatusInfo});

		// ------ 【008】

		// 建物基本情報
		buildingInfo = new BuildingInfo();
		buildingInfo.setSysBuildingCd("SBLD00008");
		buildingInfo.setBuildingCd("BLD00008");
		buildingInfo.setDisplayBuildingName("建物名０８");
		buildingInfo.setPrefCd("08");
		buildingInfo.setAddressCd("08101");
		this.buildingInfoDAO.insert(new BuildingInfo[] {buildingInfo});

		// 建物詳細情報
		buildingDtlInfo = new BuildingDtlInfo();
		buildingDtlInfo.setSysBuildingCd("SBLD00008");
		this.buildingDtlInfoDAO.insert(new BuildingDtlInfo[] {buildingDtlInfo});

		// 物件基本情報
		housingInfo = new HousingInfo();
		housingInfo.setSysHousingCd("SHOU00008");
		housingInfo.setHousingCd("HOU00008");
		housingInfo.setDisplayHousingName("物件名０８");
		housingInfo.setSysBuildingCd("SBLD00008");
		housingInfo.setLandArea(new BigDecimal("50"));
		this.housingInfoDAO.insert(new HousingInfo[]{housingInfo});

		// 物件ステータス情報
		housingStatusInfo = new HousingStatusInfo();
		housingStatusInfo.setSysHousingCd("SHOU00008");
		housingStatusInfo.setHiddenFlg("0");
		this.housingStatusInfoDAO.insert(new HousingStatusInfo[]{housingStatusInfo});

		// ------ 【009】

		// 建物基本情報
		buildingInfo = new BuildingInfo();
		buildingInfo.setSysBuildingCd("SBLD00009");
		buildingInfo.setBuildingCd("BLD00009");
		buildingInfo.setDisplayBuildingName("建物名０９");
		buildingInfo.setPrefCd("09");
		buildingInfo.setAddressCd("09101");
		this.buildingInfoDAO.insert(new BuildingInfo[] {buildingInfo});

		// 建物詳細情報
		buildingDtlInfo = new BuildingDtlInfo();
		buildingDtlInfo.setSysBuildingCd("SBLD00009");
		this.buildingDtlInfoDAO.insert(new BuildingDtlInfo[] {buildingDtlInfo});

		// 物件基本情報
		housingInfo = new HousingInfo();
		housingInfo.setSysHousingCd("SHOU00009");
		housingInfo.setHousingCd("HOU00009");
		housingInfo.setDisplayHousingName("物件名０９");
		housingInfo.setSysBuildingCd("SBLD00009");
		housingInfo.setPersonalArea(new BigDecimal("60"));
		this.housingInfoDAO.insert(new HousingInfo[]{housingInfo});

		// 物件ステータス情報
		housingStatusInfo = new HousingStatusInfo();
		housingStatusInfo.setSysHousingCd("SHOU00009");
		housingStatusInfo.setHiddenFlg("0");
		this.housingStatusInfoDAO.insert(new HousingStatusInfo[]{housingStatusInfo});

		// ------ 【010】

		// 建物基本情報
		buildingInfo = new BuildingInfo();
		buildingInfo.setSysBuildingCd("SBLD00010");
		buildingInfo.setBuildingCd("BLD00010");
		buildingInfo.setDisplayBuildingName("建物名１０");
		buildingInfo.setPrefCd("10");
		buildingInfo.setAddressCd("10101");
		buildingInfo.setHousingKindCd("001");
		this.buildingInfoDAO.insert(new BuildingInfo[] {buildingInfo});

		// 建物詳細情報
		buildingDtlInfo = new BuildingDtlInfo();
		buildingDtlInfo.setSysBuildingCd("SBLD00010");
		this.buildingDtlInfoDAO.insert(new BuildingDtlInfo[] {buildingDtlInfo});

		// 物件基本情報
		housingInfo = new HousingInfo();
		housingInfo.setSysHousingCd("SHOU00010");
		housingInfo.setHousingCd("HOU00010");
		housingInfo.setDisplayHousingName("物件名１０");
		housingInfo.setSysBuildingCd("SBLD00010");
		housingInfo.setPersonalArea(new BigDecimal("60"));
		this.housingInfoDAO.insert(new HousingInfo[]{housingInfo});

		// 物件ステータス情報
		housingStatusInfo = new HousingStatusInfo();
		housingStatusInfo.setSysHousingCd("SHOU00010");
		housingStatusInfo.setHiddenFlg("0");
		this.housingStatusInfoDAO.insert(new HousingStatusInfo[]{housingStatusInfo});

		// ------ 【011】

		// 建物基本情報
		buildingInfo = new BuildingInfo();
		buildingInfo.setSysBuildingCd("SBLD00011");
		buildingInfo.setBuildingCd("BLD00011");
		buildingInfo.setDisplayBuildingName("建物名１１");
		buildingInfo.setPrefCd("11");
		buildingInfo.setAddressCd("11101");
		this.buildingInfoDAO.insert(new BuildingInfo[] {buildingInfo});

		// 建物詳細情報
		buildingDtlInfo = new BuildingDtlInfo();
		buildingDtlInfo.setSysBuildingCd("SBLD00011");
		this.buildingDtlInfoDAO.insert(new BuildingDtlInfo[] {buildingDtlInfo});

		// 物件基本情報
		housingInfo = new HousingInfo();
		housingInfo.setSysHousingCd("SHOU00011");
		housingInfo.setHousingCd("HOU00011");
		housingInfo.setDisplayHousingName("物件名１１");
		housingInfo.setSysBuildingCd("SBLD00011");
		housingInfo.setPersonalArea(new BigDecimal("60"));
		housingInfo.setLayoutCd("001");
		this.housingInfoDAO.insert(new HousingInfo[]{housingInfo});

		// 物件こだわり条件情報
		housingPartInfo = new HousingPartInfo();
		housingPartInfo.setSysHousingCd("SHOU00011");
		housingPartInfo.setPartSrchCd("S05");
		this.housingPartInfoDAO.insert(new HousingPartInfo[] { housingPartInfo });

		// 物件ステータス情報
		housingStatusInfo = new HousingStatusInfo();
		housingStatusInfo.setSysHousingCd("SHOU00011");
		housingStatusInfo.setHiddenFlg("0");
		this.housingStatusInfoDAO.insert(new HousingStatusInfo[]{housingStatusInfo});

		// ------ 【012】

		// 建物基本情報
		buildingInfo = new BuildingInfo();
		buildingInfo.setSysBuildingCd("SBLD00012");
		buildingInfo.setBuildingCd("BLD00012");
		buildingInfo.setDisplayBuildingName("建物名１２");
		buildingInfo.setPrefCd("12");
		buildingInfo.setAddressCd("12101");
		this.buildingInfoDAO.insert(new BuildingInfo[] {buildingInfo});

		// 建物詳細情報
		buildingDtlInfo = new BuildingDtlInfo();
		buildingDtlInfo.setSysBuildingCd("SBLD00012");
		buildingDtlInfo.setBuildingArea(new BigDecimal("50"));
		this.buildingDtlInfoDAO.insert(new BuildingDtlInfo[] {buildingDtlInfo});

		// 物件基本情報
		housingInfo = new HousingInfo();
		housingInfo.setSysHousingCd("SHOU00012");
		housingInfo.setHousingCd("HOU00012");
		housingInfo.setDisplayHousingName("物件名１２");
		housingInfo.setSysBuildingCd("SBLD00012");
		housingInfo.setPersonalArea(new BigDecimal("60"));
		housingInfo.setLayoutCd("001");
		this.housingInfoDAO.insert(new HousingInfo[]{housingInfo});

		// 物件こだわり条件情報
		housingPartInfo = new HousingPartInfo();
		housingPartInfo.setSysHousingCd("SHOU00012");
		housingPartInfo.setPartSrchCd("FRE");
		this.housingPartInfoDAO.insert(new HousingPartInfo[] { housingPartInfo });

		// 物件ステータス情報
		housingStatusInfo = new HousingStatusInfo();
		housingStatusInfo.setSysHousingCd("SHOU00012");
		housingStatusInfo.setHiddenFlg("0");
		this.housingStatusInfoDAO.insert(new HousingStatusInfo[]{housingStatusInfo});

		// ------ 【013】

		// 建物基本情報
		buildingInfo = new BuildingInfo();
		buildingInfo.setSysBuildingCd("SBLD00013");
		buildingInfo.setBuildingCd("BLD00013");
		buildingInfo.setDisplayBuildingName("建物名１３");
		buildingInfo.setPrefCd("13");
		buildingInfo.setAddressCd("13101");
		this.buildingInfoDAO.insert(new BuildingInfo[] {buildingInfo});

		// 建物詳細情報
		buildingDtlInfo = new BuildingDtlInfo();
		buildingDtlInfo.setSysBuildingCd("SBLD00013");
		buildingDtlInfo.setBuildingArea(new BigDecimal("50"));
		this.buildingDtlInfoDAO.insert(new BuildingDtlInfo[] {buildingDtlInfo});

		// 物件基本情報
		housingInfo = new HousingInfo();
		housingInfo.setSysHousingCd("SHOU00013");
		housingInfo.setHousingCd("HOU00013");
		housingInfo.setDisplayHousingName("物件名１３");
		housingInfo.setSysBuildingCd("SBLD00013");
		housingInfo.setPersonalArea(new BigDecimal("60"));
		housingInfo.setLayoutCd("001");
		this.housingInfoDAO.insert(new HousingInfo[]{housingInfo});

		// 物件こだわり条件情報
		housingPartInfo = new HousingPartInfo();
		housingPartInfo.setSysHousingCd("SHOU00013");
		housingPartInfo.setPartSrchCd("REI");
		this.housingPartInfoDAO.insert(new HousingPartInfo[] { housingPartInfo });

		// 物件ステータス情報
		housingStatusInfo = new HousingStatusInfo();
		housingStatusInfo.setSysHousingCd("SHOU00013");
		housingStatusInfo.setHiddenFlg("0");
		this.housingStatusInfoDAO.insert(new HousingStatusInfo[]{housingStatusInfo});

	}

	/**
	 * 物件情報の検索処理<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>検索結果が正しく取得されている事</li>
	 * </ul>
	 */
	@Test
	public void SearchHousingTest() throws Exception {

		HousingSearchForm searchForm = housingFormFactory.createHousingSearchForm();
		searchForm.setKeyHousingCd("HOU00001");

		int cnt = this.housingManage.searchHousing(searchForm);
		Assert.assertEquals("検索結果が取得できている事", 1 , cnt);

		Calendar calendar = Calendar.getInstance();
		calendar.clear();

		// 物件基本情報
		Housing housing = searchForm.getRows().get(0);
		HousingInfo housingInfo = (HousingInfo)housing.getHousingInfo().getItems().get("housingInfo");
		Assert.assertEquals("物件基本情報のシステム物件CDが正しく取得されている事", "SHOU00001", housingInfo.getSysHousingCd());
		Assert.assertEquals("物件基本情報の物件番号が正しく取得されている事", "HOU00001", housingInfo.getHousingCd());
		Assert.assertEquals("物件基本情報の表示用物件名が正しく取得されている事", "物件名０１", housingInfo.getDisplayHousingName());
		Assert.assertEquals("物件基本情報の表示用物件名ふりがなが正しく取得されている事", "ぶっけんめい０１", housingInfo.getDisplayHousingNameKana());
		Assert.assertEquals("物件基本情報の部屋番号が正しく取得されている事", "101", housingInfo.getRoomNo());
		Assert.assertEquals("物件基本情報のシステム建物CDが正しく取得されている事", "SBLD00001", housingInfo.getSysBuildingCd());
		Assert.assertEquals("物件基本情報の賃料/価格が正しく取得されている事", 100000L, housingInfo.getPrice().longValue());
		Assert.assertEquals("物件基本情報の管理費が正しく取得されている事", 5000L, housingInfo.getUpkeep().longValue());
		Assert.assertEquals("物件基本情報の共益費が正しく取得されている事", 6000L, housingInfo.getCommonAreaFee().longValue());
		Assert.assertEquals("物件基本情報の修繕積立費が正しく取得されている事", 7000L, housingInfo.getMenteFee().longValue());
		Assert.assertTrue("物件基本情報の敷金が正しく取得されている事", new BigDecimal("200000.00").compareTo(housingInfo.getSecDeposit()) == 0);
		Assert.assertEquals("物件基本情報の敷金単位が正しく取得されている事", "01", housingInfo.getSecDepositCrs());
		Assert.assertTrue("物件基本情報の保証金が正しく取得されている事", new BigDecimal("300000.00").compareTo(housingInfo.getBondChrg()) == 0);
		Assert.assertEquals("物件基本情報の保証金単位が正しく取得されている事", "02", housingInfo.getBondChrgCrs());
		Assert.assertEquals("物件基本情報の敷引礼金区分が正しく取得されている事", "03", housingInfo.getDepositDiv());
		Assert.assertTrue("物件基本情報の敷引礼金額が正しく取得されている事", new BigDecimal("400000.00").compareTo(housingInfo.getDeposit()) == 0);
		Assert.assertEquals("物件基本情報の敷引礼金単位が正しく取得されている事", "04", housingInfo.getDepositCrs());
		Assert.assertEquals("物件基本情報の間取CDが正しく取得されている事", "001", housingInfo.getLayoutCd());
		Assert.assertEquals("物件基本情報の間取詳細コメントが正しく取得されている事", "間取詳細コメント", housingInfo.getLayoutComment());
		Assert.assertEquals("物件基本情報の物件の階数が正しく取得されている事", 2, housingInfo.getFloorNo().intValue());
		Assert.assertEquals("物件基本情報の物件の階数コメントが正しく取得されている事", "物件の階数コメント", housingInfo.getFloorNoNote());
		Assert.assertTrue("物件基本情報の土地面積が正しく取得されている事", new BigDecimal("50.00").compareTo(housingInfo.getLandArea()) == 0);
		Assert.assertEquals("物件基本情報の土地面積_補足が正しく取得されている事", "土地面積補足", housingInfo.getLandAreaMemo());
		Assert.assertTrue("物件基本情報の専有面積が正しく取得されている事", new BigDecimal("60.00").compareTo(housingInfo.getPersonalArea()) == 0);
		Assert.assertEquals("物件基本情報の専有面積_補足が正しく取得されている事", "専有面積補足", housingInfo.getPersonalAreaMemo());
		Assert.assertEquals("物件基本情報の入居状態フラグが正しく取得されている事", "0", housingInfo.getMoveinFlg());
		Assert.assertEquals("物件基本情報の駐車場の状況が正しく取得されている事", "05", housingInfo.getParkingSituation());
		Assert.assertEquals("物件基本情報の駐車場空の有無が正しく取得されている事", "1", housingInfo.getParkingEmpExist());
		Assert.assertEquals("物件基本情報の表示用駐車場情報が正しく取得されている事", "表示用駐車場情報", housingInfo.getDisplayParkingInfo());
		Assert.assertEquals("物件基本情報の窓の向きが正しく取得されている事", "06", housingInfo.getWindowDirection());
		Assert.assertEquals("物件基本情報のアイコン情報が正しく取得されている事", "REI,FRE", housingInfo.getIconCd());
		Assert.assertEquals("物件基本情報の基本情報コメントが正しく取得されている事", "基本情報コメント", housingInfo.getBasicComment());
		calendar.set(2015, 5, 1, 0, 0, 0);
		Assert.assertEquals("物件基本情報の登録日が正しく取得されている事", calendar.getTime(), housingInfo.getInsDate());
		Assert.assertEquals("物件基本情報の登録者が正しく取得されている事", "UID00001", housingInfo.getInsUserId());
		calendar.set(2015, 5, 2, 0, 0, 0);
		Assert.assertEquals("物件基本情報の更新者が正しく取得されている事", calendar.getTime(), housingInfo.getUpdDate());
		Assert.assertEquals("物件基本情報の更新日が正しく取得されている事", "UID00002", housingInfo.getUpdUserId());

		// 建物基本情報
		Building building = housing.getBuilding();
		BuildingInfo buildingInfo = (BuildingInfo) building.getBuildingInfo().getItems().get("buildingInfo");
		Assert.assertEquals("建物基本情報のシステム建物CDが正しく取得されている事", "SBLD00001", buildingInfo.getSysBuildingCd());
		Assert.assertEquals("建物基本情報の建物番号が正しく取得されている事", "BLD00001", buildingInfo.getBuildingCd());
		Assert.assertEquals("建物基本情報の物件種類CDが正しく取得されている事", "001", buildingInfo.getHousingKindCd());
		Assert.assertEquals("建物基本情報の建物構造CDが正しく取得されている事", "002", buildingInfo.getStructCd());
		Assert.assertEquals("建物基本情報の表示用建物名が正しく取得されている事", "建物名０１", buildingInfo.getDisplayBuildingName());
		Assert.assertEquals("建物基本情報の表示用建物名ふりがなが正しく取得されている事", "たてものめい０１", buildingInfo.getDisplayBuildingNameKana());
		Assert.assertEquals("建物基本情報の所在地・郵便番号が正しく取得されている事", "1234567", buildingInfo.getZip());
		Assert.assertEquals("建物基本情報の所在地・都道府県CDが正しく取得されている事", "01", buildingInfo.getPrefCd());
		Assert.assertEquals("建物基本情報の所在地・市区町村CDが正しく取得されている事", "01101", buildingInfo.getAddressCd());
		Assert.assertEquals("建物基本情報の所在地・市区町村名が正しく取得されている事", "市区町村名", buildingInfo.getAddressName());
		Assert.assertEquals("建物基本情報の所在地・町名番地が正しく取得されている事", "町名番地", buildingInfo.getAddressOther1());
		Assert.assertEquals("建物基本情報の所在地・建物名その他 が正しく取得されている事", "建物名その他", buildingInfo.getAddressOther2());
		calendar.set(2015, 4, 1, 0, 0, 0);
		Assert.assertEquals("建物基本情報の竣工年月が正しく取得されている事", calendar.getTime(), buildingInfo.getCompDate());
		Assert.assertEquals("建物基本情報の竣工旬が正しく取得されている事", "2015年4月上旬", buildingInfo.getCompTenDays());
		Assert.assertEquals("建物基本情報の総階数が正しく取得されている事", 5, buildingInfo.getTotalFloors().intValue());
		calendar.set(2015, 4, 2, 0, 0, 0);
		Assert.assertEquals("建物基本情報の登録日が正しく取得されている事", calendar.getTime(), buildingInfo.getInsDate());
		Assert.assertEquals("建物基本情報の登録者が正しく取得されている事", "UID00001", buildingInfo.getInsUserId());
		calendar.set(2015, 4, 3, 0, 0, 0);
		Assert.assertEquals("建物基本情報の最終更新日が正しく取得されている事", calendar.getTime(), buildingInfo.getUpdDate());
		Assert.assertEquals("建物基本情報の最終更新者が正しく取得されている事", "UID00002", buildingInfo.getUpdUserId());

		// 建物詳細情報
		BuildingDtlInfo buildingDtlInfo = (BuildingDtlInfo) building.getBuildingInfo().getItems().get("buildingDtlInfo");
		Assert.assertEquals("建物詳細情報のシステム建物CDが正しく取得されている事", "SBLD00001", buildingDtlInfo.getSysBuildingCd());
		Assert.assertTrue("建物詳細情報の建物面積が正しく取得されている事", new BigDecimal("50.00").compareTo(buildingDtlInfo.getBuildingArea()) == 0);
		Assert.assertEquals("建物詳細情報の建物面積_補足が正しく取得されている事", "建物面積補足", buildingDtlInfo.getBuildingAreaMemo());
		Assert.assertTrue("建物詳細情報の建ぺい率が正しく取得されている事", new BigDecimal("60.00").compareTo(buildingDtlInfo.getCoverage()) == 0);
		Assert.assertEquals("建物詳細情報の建ぺい率_細項が正しく取得されている事", "建ぺい率補足", buildingDtlInfo.getCoverageMemo());
		Assert.assertTrue("建物詳細情報の容積率が正しく取得されている事", new BigDecimal("200.00").compareTo(buildingDtlInfo.getBuildingRate()) == 0);
		Assert.assertEquals("建物詳細情報の容積率_補足が正しく取得されている事", "容積率補足", buildingDtlInfo.getBuildingRateMemo());
		Assert.assertEquals("建物詳細情報の総戸数が正しく取得されている事", 10, buildingDtlInfo.getTotalHouseCnt().intValue());
		Assert.assertEquals("建物詳細情報の賃貸戸数が正しく取得されている事", 9, buildingDtlInfo.getLeaseHouseCnt().intValue());
		Assert.assertTrue("建物詳細情報の建物緯度が正しく取得されている事", new BigDecimal("123.4567890").compareTo(buildingDtlInfo.getBuildingLatitude()) == 0);
		Assert.assertTrue("建物詳細情報の建物経度が正しく取得されている事", new BigDecimal("223.4567890").compareTo(buildingDtlInfo.getBuildingLongitude()) == 0);

	}

	/**
	 * 物件番号による物件情報の検索処理<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>物件番号による検索結果が正しく取得されている事</li>
	 * </ul>
	 */
	@Test
	public void SearchHousingKeyHousingCdTest() throws Exception {

		HousingSearchForm searchForm = housingFormFactory.createHousingSearchForm();
		searchForm.setKeyHousingCd("HOU00002");

		int cnt = this.housingManage.searchHousing(searchForm);
		Assert.assertEquals("検索結果が取得できている事", 1 , cnt);

		// 物件基本情報
		Housing housing = searchForm.getRows().get(0);
		HousingInfo housingInfo = (HousingInfo)housing.getHousingInfo().getItems().get("housingInfo");
		Assert.assertEquals("物件基本情報のシステム物件CDが正しく取得されている事", "SHOU00002", housingInfo.getSysHousingCd());

		// 建物基本情報
		Building building = housing.getBuilding();
		BuildingInfo buildingInfo = (BuildingInfo) building.getBuildingInfo().getItems().get("buildingInfo");
		Assert.assertEquals("建物基本情報のシステム建物CDが正しく取得されている事", "SBLD00002", buildingInfo.getSysBuildingCd());

		// 建物詳細情報
		BuildingDtlInfo buildingDtlInfo = (BuildingDtlInfo) building.getBuildingInfo().getItems().get("buildingDtlInfo");
		Assert.assertEquals("建物詳細情報のシステム建物CDが正しく取得されている事", "SBLD00002", buildingDtlInfo.getSysBuildingCd());

		// 複数個の検索値
		searchForm = housingFormFactory.createHousingSearchForm();
		searchForm.setKeyHousingCd("HOU00002,HOU00003");

		cnt = this.housingManage.searchHousing(searchForm);
		Assert.assertEquals("検索結果が取得できている事", 2 , cnt);

		// 物件基本情報
		housing = searchForm.getRows().get(0);
		housingInfo = (HousingInfo)housing.getHousingInfo().getItems().get("housingInfo");
		Assert.assertEquals("物件基本情報のシステム物件CDが正しく取得されている事", "SHOU00002", housingInfo.getSysHousingCd());

		// 建物基本情報
		building = housing.getBuilding();
		buildingInfo = (BuildingInfo) building.getBuildingInfo().getItems().get("buildingInfo");
		Assert.assertEquals("建物基本情報のシステム建物CDが正しく取得されている事", "SBLD00002", buildingInfo.getSysBuildingCd());

		// 建物詳細情報
		buildingDtlInfo = (BuildingDtlInfo) building.getBuildingInfo().getItems().get("buildingDtlInfo");
		Assert.assertEquals("建物詳細情報のシステム建物CDが正しく取得されている事", "SBLD00002", buildingDtlInfo.getSysBuildingCd());

		// 物件基本情報
		housing = searchForm.getRows().get(1);
		housingInfo = (HousingInfo)housing.getHousingInfo().getItems().get("housingInfo");
		Assert.assertEquals("物件基本情報のシステム物件CDが正しく取得されている事", "SHOU00003", housingInfo.getSysHousingCd());

		// 建物基本情報
		building = housing.getBuilding();
		buildingInfo = (BuildingInfo) building.getBuildingInfo().getItems().get("buildingInfo");
		Assert.assertEquals("建物基本情報のシステム建物CDが正しく取得されている事", "SBLD00003", buildingInfo.getSysBuildingCd());

		// 建物詳細情報
		buildingDtlInfo = (BuildingDtlInfo) building.getBuildingInfo().getItems().get("buildingDtlInfo");
		Assert.assertEquals("建物詳細情報のシステム建物CDが正しく取得されている事", "SBLD00003", buildingDtlInfo.getSysBuildingCd());

	}

	/**
	 * 物件名による物件情報の検索処理<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>物件名による検索（部分一致）結果が正しく取得されている事</li>
	 * </ul>
	 */
	@Test
	public void SearchHousingKeyDisplayHousingNameTest() throws Exception {

		HousingSearchForm searchForm = housingFormFactory.createHousingSearchForm();
		searchForm.setKeyDisplayHousingName("０２");

		int cnt = this.housingManage.searchHousing(searchForm);
		Assert.assertEquals("検索結果が取得できている事", 1 , cnt);

		// 物件基本情報
		Housing housing = searchForm.getRows().get(0);
		HousingInfo housingInfo = (HousingInfo)housing.getHousingInfo().getItems().get("housingInfo");
		Assert.assertEquals("物件基本情報のシステム物件CDが正しく取得されている事", "SHOU00002", housingInfo.getSysHousingCd());

		// 建物基本情報
		Building building = housing.getBuilding();
		BuildingInfo buildingInfo = (BuildingInfo) building.getBuildingInfo().getItems().get("buildingInfo");
		Assert.assertEquals("建物基本情報のシステム建物CDが正しく取得されている事", "SBLD00002", buildingInfo.getSysBuildingCd());

		// 建物詳細情報
		BuildingDtlInfo buildingDtlInfo = (BuildingDtlInfo) building.getBuildingInfo().getItems().get("buildingDtlInfo");
		Assert.assertEquals("建物詳細情報のシステム建物CDが正しく取得されている事", "SBLD00002", buildingDtlInfo.getSysBuildingCd());

	}

	/**
	 * 都道府県CDによる物件情報の検索処理<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>都道府県CDによる検索結果が正しく取得されている事</li>
	 * </ul>
	 */
	@Test
	public void SearchHousingKeyPrefCdTest() throws Exception {

		HousingSearchForm searchForm = housingFormFactory.createHousingSearchForm();
		searchForm.setKeyPrefCd("03");

		int cnt = this.housingManage.searchHousing(searchForm);
		Assert.assertEquals("検索結果が取得できている事", 1 , cnt);

		// 物件基本情報
		Housing housing = searchForm.getRows().get(0);
		HousingInfo housingInfo = (HousingInfo)housing.getHousingInfo().getItems().get("housingInfo");
		Assert.assertEquals("物件基本情報のシステム物件CDが正しく取得されている事", "SHOU00003", housingInfo.getSysHousingCd());

		// 建物基本情報
		Building building = housing.getBuilding();
		BuildingInfo buildingInfo = (BuildingInfo) building.getBuildingInfo().getItems().get("buildingInfo");
		Assert.assertEquals("建物基本情報のシステム建物CDが正しく取得されている事", "SBLD00003", buildingInfo.getSysBuildingCd());

		// 建物詳細情報
		BuildingDtlInfo buildingDtlInfo = (BuildingDtlInfo) building.getBuildingInfo().getItems().get("buildingDtlInfo");
		Assert.assertEquals("建物詳細情報のシステム建物CDが正しく取得されている事", "SBLD00003", buildingDtlInfo.getSysBuildingCd());

		// 複数個の検索値
		searchForm = housingFormFactory.createHousingSearchForm();
		searchForm.setKeyPrefCd("03,04");

		cnt = this.housingManage.searchHousing(searchForm);
		Assert.assertEquals("検索結果が取得できている事", 2 , cnt);

		// 物件基本情報
		housing = searchForm.getRows().get(0);
		housingInfo = (HousingInfo)housing.getHousingInfo().getItems().get("housingInfo");
		Assert.assertEquals("物件基本情報のシステム物件CDが正しく取得されている事", "SHOU00003", housingInfo.getSysHousingCd());

		// 建物基本情報
		building = housing.getBuilding();
		buildingInfo = (BuildingInfo) building.getBuildingInfo().getItems().get("buildingInfo");
		Assert.assertEquals("建物基本情報のシステム建物CDが正しく取得されている事", "SBLD00003", buildingInfo.getSysBuildingCd());

		// 建物詳細情報
		buildingDtlInfo = (BuildingDtlInfo) building.getBuildingInfo().getItems().get("buildingDtlInfo");
		Assert.assertEquals("建物詳細情報のシステム建物CDが正しく取得されている事", "SBLD00003", buildingDtlInfo.getSysBuildingCd());

		// 物件基本情報
		housing = searchForm.getRows().get(1);
		housingInfo = (HousingInfo)housing.getHousingInfo().getItems().get("housingInfo");
		Assert.assertEquals("物件基本情報のシステム物件CDが正しく取得されている事", "SHOU00004", housingInfo.getSysHousingCd());

		// 建物基本情報
		building = housing.getBuilding();
		buildingInfo = (BuildingInfo) building.getBuildingInfo().getItems().get("buildingInfo");
		Assert.assertEquals("建物基本情報のシステム建物CDが正しく取得されている事", "SBLD00004", buildingInfo.getSysBuildingCd());

		// 建物詳細情報
		buildingDtlInfo = (BuildingDtlInfo) building.getBuildingInfo().getItems().get("buildingDtlInfo");
		Assert.assertEquals("建物詳細情報のシステム建物CDが正しく取得されている事", "SBLD00004", buildingDtlInfo.getSysBuildingCd());

	}

	/**
	 * 市区町村CDによる物件情報の検索処理<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>市区町村CDによる検索結果が正しく取得されている事</li>
	 * </ul>
	 */
	@Test
	public void SearchHousingKeyAddressCdTest() throws Exception {

		HousingSearchForm searchForm = housingFormFactory.createHousingSearchForm();
		searchForm.setKeyAddressCd("04101");

		int cnt = this.housingManage.searchHousing(searchForm);
		Assert.assertEquals("検索結果が取得できている事", 1 , cnt);

		// 物件基本情報
		Housing housing = searchForm.getRows().get(0);
		HousingInfo housingInfo = (HousingInfo)housing.getHousingInfo().getItems().get("housingInfo");
		Assert.assertEquals("物件基本情報のシステム物件CDが正しく取得されている事", "SHOU00004", housingInfo.getSysHousingCd());

		// 建物基本情報
		Building building = housing.getBuilding();
		BuildingInfo buildingInfo = (BuildingInfo) building.getBuildingInfo().getItems().get("buildingInfo");
		Assert.assertEquals("建物基本情報のシステム建物CDが正しく取得されている事", "SBLD00004", buildingInfo.getSysBuildingCd());

		// 建物詳細情報
		BuildingDtlInfo buildingDtlInfo = (BuildingDtlInfo) building.getBuildingInfo().getItems().get("buildingDtlInfo");
		Assert.assertEquals("建物詳細情報のシステム建物CDが正しく取得されている事", "SBLD00004", buildingDtlInfo.getSysBuildingCd());

		// 複数個の検索値
		searchForm = housingFormFactory.createHousingSearchForm();
		searchForm.setKeyAddressCd("04101,05101");

		cnt = this.housingManage.searchHousing(searchForm);
		Assert.assertEquals("検索結果が取得できている事", 2 , cnt);

		// 物件基本情報
		housing = searchForm.getRows().get(0);
		housingInfo = (HousingInfo)housing.getHousingInfo().getItems().get("housingInfo");
		Assert.assertEquals("物件基本情報のシステム物件CDが正しく取得されている事", "SHOU00004", housingInfo.getSysHousingCd());

		// 建物基本情報
		building = housing.getBuilding();
		buildingInfo = (BuildingInfo) building.getBuildingInfo().getItems().get("buildingInfo");
		Assert.assertEquals("建物基本情報のシステム建物CDが正しく取得されている事", "SBLD00004", buildingInfo.getSysBuildingCd());

		// 建物詳細情報
		buildingDtlInfo = (BuildingDtlInfo) building.getBuildingInfo().getItems().get("buildingDtlInfo");
		Assert.assertEquals("建物詳細情報のシステム建物CDが正しく取得されている事", "SBLD00004", buildingDtlInfo.getSysBuildingCd());

		// 物件基本情報
		housing = searchForm.getRows().get(1);
		housingInfo = (HousingInfo)housing.getHousingInfo().getItems().get("housingInfo");
		Assert.assertEquals("物件基本情報のシステム物件CDが正しく取得されている事", "SHOU00005", housingInfo.getSysHousingCd());

		// 建物基本情報
		building = housing.getBuilding();
		buildingInfo = (BuildingInfo) building.getBuildingInfo().getItems().get("buildingInfo");
		Assert.assertEquals("建物基本情報のシステム建物CDが正しく取得されている事", "SBLD00005", buildingInfo.getSysBuildingCd());

		// 建物詳細情報
		buildingDtlInfo = (BuildingDtlInfo) building.getBuildingInfo().getItems().get("buildingDtlInfo");
		Assert.assertEquals("建物詳細情報のシステム建物CDが正しく取得されている事", "SBLD00005", buildingDtlInfo.getSysBuildingCd());

	}

	/**
	 * 沿線CDによる物件情報の検索処理<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>沿線CDによる検索結果が正しく取得されている事</li>
	 * </ul>
	 */
	@Test
	public void SearchHousingKeyRouteCdTest() throws Exception {

		HousingSearchForm searchForm = housingFormFactory.createHousingSearchForm();
		searchForm.setKeyRouteCd("J002003");

		int cnt = this.housingManage.searchHousing(searchForm);
		Assert.assertEquals("検索結果が取得できている事", 1 , cnt);

		// 物件基本情報
		Housing housing = searchForm.getRows().get(0);
		HousingInfo housingInfo = (HousingInfo)housing.getHousingInfo().getItems().get("housingInfo");
		Assert.assertEquals("物件基本情報のシステム物件CDが正しく取得されている事", "SHOU00005", housingInfo.getSysHousingCd());

		// 建物基本情報
		Building building = housing.getBuilding();
		BuildingInfo buildingInfo = (BuildingInfo) building.getBuildingInfo().getItems().get("buildingInfo");
		Assert.assertEquals("建物基本情報のシステム建物CDが正しく取得されている事", "SBLD00005", buildingInfo.getSysBuildingCd());

		// 建物詳細情報
		BuildingDtlInfo buildingDtlInfo = (BuildingDtlInfo) building.getBuildingInfo().getItems().get("buildingDtlInfo");
		Assert.assertEquals("建物詳細情報のシステム建物CDが正しく取得されている事", "SBLD00005", buildingDtlInfo.getSysBuildingCd());

	}

	/**
	 * 駅CDによる物件情報の検索処理<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>駅CDによる検索結果が正しく取得されている事</li>
	 * </ul>
	 */
	@Test
	public void SearchHousingKeyStationCdTest() throws Exception {

		HousingSearchForm searchForm = housingFormFactory.createHousingSearchForm();
		searchForm.setKeyRouteCd("J002002");
		searchForm.setKeyStationCd("21392");

		int cnt = this.housingManage.searchHousing(searchForm);
		Assert.assertEquals("検索結果が取得できている事", 1 , cnt);

		// 物件基本情報
		Housing housing = searchForm.getRows().get(0);
		HousingInfo housingInfo = (HousingInfo)housing.getHousingInfo().getItems().get("housingInfo");
		Assert.assertEquals("物件基本情報のシステム物件CDが正しく取得されている事", "SHOU00006", housingInfo.getSysHousingCd());

		// 建物基本情報
		Building building = housing.getBuilding();
		BuildingInfo buildingInfo = (BuildingInfo) building.getBuildingInfo().getItems().get("buildingInfo");
		Assert.assertEquals("建物基本情報のシステム建物CDが正しく取得されている事", "SBLD00006", buildingInfo.getSysBuildingCd());

		// 建物詳細情報
		BuildingDtlInfo buildingDtlInfo = (BuildingDtlInfo) building.getBuildingInfo().getItems().get("buildingDtlInfo");
		Assert.assertEquals("建物詳細情報のシステム建物CDが正しく取得されている事", "SBLD00006", buildingDtlInfo.getSysBuildingCd());

	}

	/**
	 * 賃料/価格による物件情報の検索処理<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>賃料/価格による検索結果が正しく取得されている事</li>
	 * </ul>
	 */
	@Test
	public void SearchHousingKeyPriceTest() throws Exception {

		// 下限
		HousingSearchForm searchForm = housingFormFactory.createHousingSearchForm();
		searchForm.setKeyPrefCd("07");
		searchForm.setKeyPriceLower(100000L);

		int cnt = this.housingManage.searchHousing(searchForm);
		Assert.assertEquals("検索結果が取得できている事", 1 , cnt);

		// 物件基本情報
		Housing housing = searchForm.getRows().get(0);
		HousingInfo housingInfo = (HousingInfo)housing.getHousingInfo().getItems().get("housingInfo");
		Assert.assertEquals("物件基本情報のシステム物件CDが正しく取得されている事", "SHOU00007", housingInfo.getSysHousingCd());

		// 建物基本情報
		Building building = housing.getBuilding();
		BuildingInfo buildingInfo = (BuildingInfo) building.getBuildingInfo().getItems().get("buildingInfo");
		Assert.assertEquals("建物基本情報のシステム建物CDが正しく取得されている事", "SBLD00007", buildingInfo.getSysBuildingCd());

		// 建物詳細情報
		BuildingDtlInfo buildingDtlInfo = (BuildingDtlInfo) building.getBuildingInfo().getItems().get("buildingDtlInfo");
		Assert.assertEquals("建物詳細情報のシステム建物CDが正しく取得されている事", "SBLD00007", buildingDtlInfo.getSysBuildingCd());

		// 下限（範囲外）
		searchForm = housingFormFactory.createHousingSearchForm();
		searchForm.setKeyPrefCd("07");
		searchForm.setKeyPriceLower(100001L);

		cnt = this.housingManage.searchHousing(searchForm);
		Assert.assertEquals("検索結果が0件である事", 0 , cnt);

		// 上限
		searchForm = housingFormFactory.createHousingSearchForm();
		searchForm.setKeyPrefCd("07");
		searchForm.setKeyPriceUpper(100000L);

		cnt = this.housingManage.searchHousing(searchForm);
		Assert.assertEquals("検索結果が取得できている事", 1 , cnt);

		// 物件基本情報
		housing = searchForm.getRows().get(0);
		housingInfo = (HousingInfo)housing.getHousingInfo().getItems().get("housingInfo");
		Assert.assertEquals("物件基本情報のシステム物件CDが正しく取得されている事", "SHOU00007", housingInfo.getSysHousingCd());

		// 建物基本情報
		building = housing.getBuilding();
		buildingInfo = (BuildingInfo) building.getBuildingInfo().getItems().get("buildingInfo");
		Assert.assertEquals("建物基本情報のシステム建物CDが正しく取得されている事", "SBLD00007", buildingInfo.getSysBuildingCd());

		// 建物詳細情報
		buildingDtlInfo = (BuildingDtlInfo) building.getBuildingInfo().getItems().get("buildingDtlInfo");
		Assert.assertEquals("建物詳細情報のシステム建物CDが正しく取得されている事", "SBLD00007", buildingDtlInfo.getSysBuildingCd());

		// 上限（範囲外）
		searchForm = housingFormFactory.createHousingSearchForm();
		searchForm.setKeyPrefCd("07");
		searchForm.setKeyPriceUpper(99999L);

		cnt = this.housingManage.searchHousing(searchForm);
		Assert.assertEquals("検索結果が0件である事", 0 , cnt);

	}

	/**
	 * 土地面積による物件情報の検索処理<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>土地面積による検索結果が正しく取得されている事</li>
	 * </ul>
	 */
	@Test
	public void SearchHousingKeyLandAreaTest() throws Exception {

		// 下限
		HousingSearchForm searchForm = housingFormFactory.createHousingSearchForm();
		searchForm.setKeyPrefCd("08");
		searchForm.setKeyLandAreaLower(new BigDecimal("50"));

		int cnt = this.housingManage.searchHousing(searchForm);
		Assert.assertEquals("検索結果が取得できている事", 1 , cnt);

		// 物件基本情報
		Housing housing = searchForm.getRows().get(0);
		HousingInfo housingInfo = (HousingInfo)housing.getHousingInfo().getItems().get("housingInfo");
		Assert.assertEquals("物件基本情報のシステム物件CDが正しく取得されている事", "SHOU00008", housingInfo.getSysHousingCd());

		// 建物基本情報
		Building building = housing.getBuilding();
		BuildingInfo buildingInfo = (BuildingInfo) building.getBuildingInfo().getItems().get("buildingInfo");
		Assert.assertEquals("建物基本情報のシステム建物CDが正しく取得されている事", "SBLD00008", buildingInfo.getSysBuildingCd());

		// 建物詳細情報
		BuildingDtlInfo buildingDtlInfo = (BuildingDtlInfo) building.getBuildingInfo().getItems().get("buildingDtlInfo");
		Assert.assertEquals("建物詳細情報のシステム建物CDが正しく取得されている事", "SBLD00008", buildingDtlInfo.getSysBuildingCd());

		// 下限（範囲外）
		searchForm = housingFormFactory.createHousingSearchForm();
		searchForm.setKeyPrefCd("08");
		searchForm.setKeyLandAreaLower(new BigDecimal("51"));

		cnt = this.housingManage.searchHousing(searchForm);
		Assert.assertEquals("検索結果が0件である事", 0 , cnt);

		// 上限
		searchForm = housingFormFactory.createHousingSearchForm();
		searchForm.setKeyPrefCd("08");
		searchForm.setKeyLandAreaUpper(new BigDecimal("50"));

		cnt = this.housingManage.searchHousing(searchForm);
		Assert.assertEquals("検索結果が取得できている事", 1 , cnt);

		// 物件基本情報
		housing = searchForm.getRows().get(0);
		housingInfo = (HousingInfo)housing.getHousingInfo().getItems().get("housingInfo");
		Assert.assertEquals("物件基本情報のシステム物件CDが正しく取得されている事", "SHOU00008", housingInfo.getSysHousingCd());

		// 建物基本情報
		building = housing.getBuilding();
		buildingInfo = (BuildingInfo) building.getBuildingInfo().getItems().get("buildingInfo");
		Assert.assertEquals("建物基本情報のシステム建物CDが正しく取得されている事", "SBLD00008", buildingInfo.getSysBuildingCd());

		// 建物詳細情報
		buildingDtlInfo = (BuildingDtlInfo) building.getBuildingInfo().getItems().get("buildingDtlInfo");
		Assert.assertEquals("建物詳細情報のシステム建物CDが正しく取得されている事", "SBLD00008", buildingDtlInfo.getSysBuildingCd());

		// 上限（範囲外）
		searchForm = housingFormFactory.createHousingSearchForm();
		searchForm.setKeyPrefCd("08");
		searchForm.setKeyLandAreaUpper(new BigDecimal("49"));

		cnt = this.housingManage.searchHousing(searchForm);
		Assert.assertEquals("検索結果が0件である事", 0 , cnt);

	}

	/**
	 * 専有面積による物件情報の検索処理<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>専有面積による検索結果が正しく取得されている事</li>
	 * </ul>
	 */
	@Test
	public void SearchHousingKeyPersonalAreaTest() throws Exception {

		// 下限
		HousingSearchForm searchForm = housingFormFactory.createHousingSearchForm();
		searchForm.setKeyPrefCd("09");
		searchForm.setKeyPersonalAreaLower(new BigDecimal("60"));

		int cnt = this.housingManage.searchHousing(searchForm);
		Assert.assertEquals("検索結果が取得できている事", 1 , cnt);

		// 物件基本情報
		Housing housing = searchForm.getRows().get(0);
		HousingInfo housingInfo = (HousingInfo)housing.getHousingInfo().getItems().get("housingInfo");
		Assert.assertEquals("物件基本情報のシステム物件CDが正しく取得されている事", "SHOU00009", housingInfo.getSysHousingCd());

		// 建物基本情報
		Building building = housing.getBuilding();
		BuildingInfo buildingInfo = (BuildingInfo) building.getBuildingInfo().getItems().get("buildingInfo");
		Assert.assertEquals("建物基本情報のシステム建物CDが正しく取得されている事", "SBLD00009", buildingInfo.getSysBuildingCd());

		// 建物詳細情報
		BuildingDtlInfo buildingDtlInfo = (BuildingDtlInfo) building.getBuildingInfo().getItems().get("buildingDtlInfo");
		Assert.assertEquals("建物詳細情報のシステム建物CDが正しく取得されている事", "SBLD00009", buildingDtlInfo.getSysBuildingCd());

		// 下限（範囲外）
		searchForm = housingFormFactory.createHousingSearchForm();
		searchForm.setKeyPrefCd("09");
		searchForm.setKeyPersonalAreaLower(new BigDecimal("61"));

		cnt = this.housingManage.searchHousing(searchForm);
		Assert.assertEquals("検索結果が0件である事", 0 , cnt);

		// 上限
		searchForm = housingFormFactory.createHousingSearchForm();
		searchForm.setKeyPrefCd("09");
		searchForm.setKeyPersonalAreaUpper(new BigDecimal("60"));

		cnt = this.housingManage.searchHousing(searchForm);
		Assert.assertEquals("検索結果が取得できている事", 1 , cnt);

		// 物件基本情報
		housing = searchForm.getRows().get(0);
		housingInfo = (HousingInfo)housing.getHousingInfo().getItems().get("housingInfo");
		Assert.assertEquals("物件基本情報のシステム物件CDが正しく取得されている事", "SHOU00009", housingInfo.getSysHousingCd());

		// 建物基本情報
		building = housing.getBuilding();
		buildingInfo = (BuildingInfo) building.getBuildingInfo().getItems().get("buildingInfo");
		Assert.assertEquals("建物基本情報のシステム建物CDが正しく取得されている事", "SBLD00009", buildingInfo.getSysBuildingCd());

		// 建物詳細情報
		buildingDtlInfo = (BuildingDtlInfo) building.getBuildingInfo().getItems().get("buildingDtlInfo");
		Assert.assertEquals("建物詳細情報のシステム建物CDが正しく取得されている事", "SBLD00009", buildingDtlInfo.getSysBuildingCd());

		// 上限（範囲外）
		searchForm = housingFormFactory.createHousingSearchForm();
		searchForm.setKeyPrefCd("09");
		searchForm.setKeyPersonalAreaUpper(new BigDecimal("59"));

		cnt = this.housingManage.searchHousing(searchForm);
		Assert.assertEquals("検索結果が0件である事", 0 , cnt);

	}

	/**
	 * 物件種類CDによる物件情報の検索処理<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>物件種類CDによる検索結果が正しく取得されている事</li>
	 * </ul>
	 */
	@Test
	public void SearchHousingKeyHousingKindCdTest() throws Exception {

		HousingSearchForm searchForm = housingFormFactory.createHousingSearchForm();
		searchForm.setKeyPrefCd("10");
		searchForm.setKeyHousingKindCd("001");

		int cnt = this.housingManage.searchHousing(searchForm);
		Assert.assertEquals("検索結果が取得できている事", 1 , cnt);

		// 物件基本情報
		Housing housing = searchForm.getRows().get(0);
		HousingInfo housingInfo = (HousingInfo)housing.getHousingInfo().getItems().get("housingInfo");
		Assert.assertEquals("物件基本情報のシステム物件CDが正しく取得されている事", "SHOU00010", housingInfo.getSysHousingCd());

		// 建物基本情報
		Building building = housing.getBuilding();
		BuildingInfo buildingInfo = (BuildingInfo) building.getBuildingInfo().getItems().get("buildingInfo");
		Assert.assertEquals("建物基本情報のシステム建物CDが正しく取得されている事", "SBLD00010", buildingInfo.getSysBuildingCd());

		// 建物詳細情報
		BuildingDtlInfo buildingDtlInfo = (BuildingDtlInfo) building.getBuildingInfo().getItems().get("buildingDtlInfo");
		Assert.assertEquals("建物詳細情報のシステム建物CDが正しく取得されている事", "SBLD00010", buildingDtlInfo.getSysBuildingCd());

	}

	/**
	 * 間取りCDによる物件情報の検索処理<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>間取りCDによる検索結果が正しく取得されている事</li>
	 * </ul>
	 */
	@Test
	public void SearchHousingKeyLayoutCdTest() throws Exception {

		HousingSearchForm searchForm = housingFormFactory.createHousingSearchForm();
		searchForm.setKeyPrefCd("11");
		searchForm.setKeyLayoutCd("001");

		int cnt = this.housingManage.searchHousing(searchForm);
		Assert.assertEquals("検索結果が取得できている事", 1 , cnt);

		// 物件基本情報
		Housing housing = searchForm.getRows().get(0);
		HousingInfo housingInfo = (HousingInfo)housing.getHousingInfo().getItems().get("housingInfo");
		Assert.assertEquals("物件基本情報のシステム物件CDが正しく取得されている事", "SHOU00011", housingInfo.getSysHousingCd());

		// 建物基本情報
		Building building = housing.getBuilding();
		BuildingInfo buildingInfo = (BuildingInfo) building.getBuildingInfo().getItems().get("buildingInfo");
		Assert.assertEquals("建物基本情報のシステム建物CDが正しく取得されている事", "SBLD00011", buildingInfo.getSysBuildingCd());

		// 建物詳細情報
		BuildingDtlInfo buildingDtlInfo = (BuildingDtlInfo) building.getBuildingInfo().getItems().get("buildingDtlInfo");
		Assert.assertEquals("建物詳細情報のシステム建物CDが正しく取得されている事", "SBLD00011", buildingDtlInfo.getSysBuildingCd());

	}

	/**
	 * 建物面積による物件情報の検索処理<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>建物面積による検索結果が正しく取得されている事</li>
	 * </ul>
	 */
	@Test
	public void SearchHousingKeyBuildingAreaTest() throws Exception {

		// 下限
		HousingSearchForm searchForm = housingFormFactory.createHousingSearchForm();
		searchForm.setKeyPrefCd("12");
		searchForm.setKeyBuildingAreaLower(new BigDecimal("50"));

		int cnt = this.housingManage.searchHousing(searchForm);
		Assert.assertEquals("検索結果が取得できている事", 1 , cnt);

		// 物件基本情報
		Housing housing = searchForm.getRows().get(0);
		HousingInfo housingInfo = (HousingInfo)housing.getHousingInfo().getItems().get("housingInfo");
		Assert.assertEquals("物件基本情報のシステム物件CDが正しく取得されている事", "SHOU00012", housingInfo.getSysHousingCd());

		// 建物基本情報
		Building building = housing.getBuilding();
		BuildingInfo buildingInfo = (BuildingInfo) building.getBuildingInfo().getItems().get("buildingInfo");
		Assert.assertEquals("建物基本情報のシステム建物CDが正しく取得されている事", "SBLD00012", buildingInfo.getSysBuildingCd());

		// 建物詳細情報
		BuildingDtlInfo buildingDtlInfo = (BuildingDtlInfo) building.getBuildingInfo().getItems().get("buildingDtlInfo");
		Assert.assertEquals("建物詳細情報のシステム建物CDが正しく取得されている事", "SBLD00012", buildingDtlInfo.getSysBuildingCd());

		// 下限（範囲外）
		searchForm = housingFormFactory.createHousingSearchForm();
		searchForm.setKeyPrefCd("12");
		searchForm.setKeyBuildingAreaLower(new BigDecimal("51"));

		cnt = this.housingManage.searchHousing(searchForm);
		Assert.assertEquals("検索結果が0件である事", 0 , cnt);

		// 上限
		searchForm = housingFormFactory.createHousingSearchForm();
		searchForm.setKeyPrefCd("12");
		searchForm.setKeyBuildingAreaUpper(new BigDecimal("50"));

		cnt = this.housingManage.searchHousing(searchForm);
		Assert.assertEquals("検索結果が取得できている事", 1 , cnt);

		// 物件基本情報
		housing = searchForm.getRows().get(0);
		housingInfo = (HousingInfo)housing.getHousingInfo().getItems().get("housingInfo");
		Assert.assertEquals("物件基本情報のシステム物件CDが正しく取得されている事", "SHOU00012", housingInfo.getSysHousingCd());

		// 建物基本情報
		building = housing.getBuilding();
		buildingInfo = (BuildingInfo) building.getBuildingInfo().getItems().get("buildingInfo");
		Assert.assertEquals("建物基本情報のシステム建物CDが正しく取得されている事", "SBLD00012", buildingInfo.getSysBuildingCd());

		// 建物詳細情報
		buildingDtlInfo = (BuildingDtlInfo) building.getBuildingInfo().getItems().get("buildingDtlInfo");
		Assert.assertEquals("建物詳細情報のシステム建物CDが正しく取得されている事", "SBLD00012", buildingDtlInfo.getSysBuildingCd());

		// 上限（範囲外）
		searchForm = housingFormFactory.createHousingSearchForm();
		searchForm.setKeyPrefCd("12");
		searchForm.setKeyBuildingAreaUpper(new BigDecimal("49"));

		cnt = this.housingManage.searchHousing(searchForm);
		Assert.assertEquals("検索結果が0件である事", 0 , cnt);

	}

	/**
	 * こだわり条件CDによる物件情報の検索処理<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>こだわり条件CDによる検索結果が正しく取得されている事</li>
	 * </ul>
	 */
	@Test
	public void SearchHousingKeyPartSrchCdTest() throws Exception {

		HousingSearchForm searchForm = housingFormFactory.createHousingSearchForm();
		searchForm.setKeyPrefCd("13");
		searchForm.setKeyPartSrchCd("REI");

		int cnt = this.housingManage.searchHousing(searchForm);
		Assert.assertEquals("検索結果が取得できている事", 1 , cnt);

		// 物件基本情報
		Housing housing = searchForm.getRows().get(0);
		HousingInfo housingInfo = (HousingInfo)housing.getHousingInfo().getItems().get("housingInfo");
		Assert.assertEquals("物件基本情報のシステム物件CDが正しく取得されている事", "SHOU00013", housingInfo.getSysHousingCd());

		// 建物基本情報
		Building building = housing.getBuilding();
		BuildingInfo buildingInfo = (BuildingInfo) building.getBuildingInfo().getItems().get("buildingInfo");
		Assert.assertEquals("建物基本情報のシステム建物CDが正しく取得されている事", "SBLD00013", buildingInfo.getSysBuildingCd());

		// 建物詳細情報
		BuildingDtlInfo buildingDtlInfo = (BuildingDtlInfo) building.getBuildingInfo().getItems().get("buildingDtlInfo");
		Assert.assertEquals("建物詳細情報のシステム建物CDが正しく取得されている事", "SBLD00013", buildingDtlInfo.getSysBuildingCd());

		// 同グループ内の複数条件
		searchForm = housingFormFactory.createHousingSearchForm();
		searchForm.setKeyPartSrchCd("REI,FRE");
		searchForm.setKeyOrderType("0");

		cnt = this.housingManage.searchHousing(searchForm);
		Assert.assertEquals("検索結果が取得できている事", 2 , cnt);

		// 物件基本情報
		housing = searchForm.getRows().get(0);
		housingInfo = (HousingInfo)housing.getHousingInfo().getItems().get("housingInfo");
		Assert.assertEquals("物件基本情報のシステム物件CDが正しく取得されている事", "SHOU00012", housingInfo.getSysHousingCd());

		housing = searchForm.getRows().get(1);
		housingInfo = (HousingInfo)housing.getHousingInfo().getItems().get("housingInfo");
		Assert.assertEquals("物件基本情報のシステム物件CDが正しく取得されている事", "SHOU00013", housingInfo.getSysHousingCd());

		// 異なるグループの複数条件（排他的に検索結果は0件）
		searchForm = housingFormFactory.createHousingSearchForm();
		searchForm.setKeyPartSrchCd("REI,C15");
		searchForm.setKeyOrderType("0");

		cnt = this.housingManage.searchHousing(searchForm);
		Assert.assertEquals("検索結果が取得できている事", 0 , cnt);

	}

	@Test
	public void SearchHousingOrderByTest() throws Exception {

		// システム物件コード
		HousingSearchForm searchForm = housingFormFactory.createHousingSearchForm();
		searchForm.setKeyOrderType("0");

		this.housingManage.searchHousing(searchForm);
		
		// 物件基本情報
		Housing housing0 = searchForm.getRows().get(0);
		HousingInfo housingInfo0 = (HousingInfo) housing0.getHousingInfo().getItems().get("housingInfo");
		Housing housing1 = searchForm.getRows().get(1);
		HousingInfo housingInfo1 = (HousingInfo) housing1.getHousingInfo().getItems().get("housingInfo");
		Assert.assertTrue("システム物件コードでソートされている事", housingInfo0.getSysHousingCd().compareTo(housingInfo1.getSysHousingCd()) < 0);

		// 賃料/価格（昇順）
		searchForm = housingFormFactory.createHousingSearchForm();
		searchForm.setKeyOrderType("1");

		this.housingManage.searchHousing(searchForm);
		housing0 = searchForm.getRows().get(searchForm.getRows().size() - 2);
		housingInfo0 = (HousingInfo) housing0.getHousingInfo().getItems().get("housingInfo");
		housing1 = searchForm.getRows().get(searchForm.getRows().size() - 1);
		housingInfo1 = (HousingInfo) housing1.getHousingInfo().getItems().get("housingInfo");
		Assert.assertTrue("賃料/価格の昇順でソートされている事", housingInfo0.getPrice().compareTo(housingInfo1.getPrice()) < 0);

		// 賃料/価格（降順）
		searchForm = housingFormFactory.createHousingSearchForm();
		searchForm.setKeyOrderType("2");

		this.housingManage.searchHousing(searchForm);
		housing0 = searchForm.getRows().get(0);
		housingInfo0 = (HousingInfo) housing0.getHousingInfo().getItems().get("housingInfo");
		housing1 = searchForm.getRows().get(1);
		housingInfo1 = (HousingInfo) housing1.getHousingInfo().getItems().get("housingInfo");
		Assert.assertTrue("賃料/価格の降順でソートされている事", housingInfo0.getPrice().compareTo(housingInfo1.getPrice()) > 0);

		// 専有面積（昇順）
		searchForm = housingFormFactory.createHousingSearchForm();
		searchForm.setKeyOrderType("3");

		this.housingManage.searchHousing(searchForm);
		housing0 = searchForm.getRows().get(searchForm.getRows().size() - 2);
		housingInfo0 = (HousingInfo) housing0.getHousingInfo().getItems().get("housingInfo");
		housing1 = searchForm.getRows().get(searchForm.getRows().size() - 1);
		housingInfo1 = (HousingInfo) housing1.getHousingInfo().getItems().get("housingInfo");
		Assert.assertTrue("専有面積の昇順でソートされている事", housingInfo0.getPersonalArea().compareTo(housingInfo1.getPersonalArea()) < 0);

		// 専有面積（降順）
		searchForm = housingFormFactory.createHousingSearchForm();
		searchForm.setKeyOrderType("4");

		this.housingManage.searchHousing(searchForm);
		housing0 = searchForm.getRows().get(0);
		housingInfo0 = (HousingInfo) housing0.getHousingInfo().getItems().get("housingInfo");
		housing1 = searchForm.getRows().get(1);
		housingInfo1 = (HousingInfo) housing1.getHousingInfo().getItems().get("housingInfo");
		Assert.assertTrue("専有面積の降順でソートされている事", housingInfo0.getPersonalArea().compareTo(housingInfo1.getPersonalArea()) > 0);

		// 築年が古い順
		searchForm = housingFormFactory.createHousingSearchForm();
		searchForm.setKeyOrderType("5");

		this.housingManage.searchHousing(searchForm);
		housing0 = searchForm.getRows().get(searchForm.getRows().size() - 2);
		BuildingInfo buildingInfo0 = (BuildingInfo) housing0.getBuilding().getBuildingInfo().getItems().get("buildingInfo");
		housing1 = searchForm.getRows().get(searchForm.getRows().size() - 1);
		BuildingInfo buildingInfo1 = (BuildingInfo) housing1.getBuilding().getBuildingInfo().getItems().get("buildingInfo");
		Assert.assertTrue("築年が新しい順でソートされている事", buildingInfo0.getCompDate().compareTo(buildingInfo1.getCompDate()) < 0);

		// 築年が新しい順
		searchForm = housingFormFactory.createHousingSearchForm();
		searchForm.setKeyOrderType("6");

		this.housingManage.searchHousing(searchForm);
		housing0 = searchForm.getRows().get(0);
		buildingInfo0 = (BuildingInfo) housing0.getBuilding().getBuildingInfo().getItems().get("buildingInfo");
		housing1 = searchForm.getRows().get(1);
		buildingInfo1 = (BuildingInfo) housing1.getBuilding().getBuildingInfo().getItems().get("buildingInfo");
		Assert.assertTrue("築年が古い順でソートされている事", buildingInfo0.getCompDate().compareTo(buildingInfo1.getCompDate()) > 0);

	}

}
