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
 * ������� model ���������̃e�X�g�P�[�X
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

	// �O����
	@Before
	public void init(){
		// �������S���폜
		DAOCriteria criteria = new DAOCriteria();
		this.housingStatusInfoDAO.deleteByFilter(criteria);
		this.housingPartInfoDAO.deleteByFilter(criteria);
		this.housingInfoDAO.deleteByFilter(criteria);
		this.buildingStationInfoDAO.deleteByFilter(criteria);
		this.buildingDtlInfoDAO.deleteByFilter(criteria);
		this.buildingInfoDAO.deleteByFilter(criteria);

		initTestData();
	}

	// �e�X�g�f�[�^�쐬
	private void initTestData(){

		BuildingInfo buildingInfo;
		BuildingDtlInfo buildingDtlInfo;
		HousingInfo housingInfo;
		HousingStatusInfo housingStatusInfo;
		HousingPartInfo housingPartInfo;
		BuildingStationInfo buildingStationInfo;
		
		Calendar calendar = Calendar.getInstance();
		calendar.clear();

		// ------ �擾�Ώۃf�[�^ ------

		// ------ �y001�z
		// ������{���
		buildingInfo = new BuildingInfo();
		buildingInfo.setSysBuildingCd("SBLD00001");
		buildingInfo.setBuildingCd("BLD00001");
		buildingInfo.setHousingKindCd("001");
		buildingInfo.setStructCd("002");
		buildingInfo.setDisplayBuildingName("�������O�P");
		buildingInfo.setDisplayBuildingNameKana("���Ă��̂߂��O�P");
		buildingInfo.setZip("1234567");
		buildingInfo.setPrefCd("01");
		buildingInfo.setAddressCd("01101");
		buildingInfo.setAddressName("�s�撬����");
		buildingInfo.setAddressOther1("�����Ԓn");
		buildingInfo.setAddressOther2("���������̑�");
		calendar.set(2015, 4, 1, 0, 0, 0);
		buildingInfo.setCompDate(calendar.getTime());
		buildingInfo.setCompTenDays("2015�N4����{");
		buildingInfo.setTotalFloors(5);
		calendar.set(2015, 4, 2, 0, 0, 0);
		buildingInfo.setInsDate(calendar.getTime());
		buildingInfo.setInsUserId("UID00001");
		calendar.set(2015, 4, 3, 0, 0, 0);
		buildingInfo.setUpdDate(calendar.getTime());
		buildingInfo.setUpdUserId("UID00002");
		this.buildingInfoDAO.insert(new BuildingInfo[] {buildingInfo});

		// �����ڍ׏��
		buildingDtlInfo = new BuildingDtlInfo();
		buildingDtlInfo.setSysBuildingCd("SBLD00001");
		buildingDtlInfo.setBuildingArea(new BigDecimal("50"));
		buildingDtlInfo.setBuildingAreaMemo("�����ʐϕ⑫");
		buildingDtlInfo.setCoverage(new BigDecimal("60"));
		buildingDtlInfo.setCoverageMemo("���؂����⑫");
		buildingDtlInfo.setBuildingRate(new BigDecimal("200"));
		buildingDtlInfo.setBuildingRateMemo("�e�ϗ��⑫");
		buildingDtlInfo.setTotalHouseCnt(10);
		buildingDtlInfo.setLeaseHouseCnt(9);
		buildingDtlInfo.setBuildingLatitude(new BigDecimal(123.4567890));
		buildingDtlInfo.setBuildingLongitude(new BigDecimal(223.4567890));
		this.buildingDtlInfoDAO.insert(new BuildingDtlInfo[] {buildingDtlInfo});

		// ������{���
		housingInfo = new HousingInfo();
		housingInfo.setSysHousingCd("SHOU00001");
		housingInfo.setHousingCd("HOU00001");
		housingInfo.setDisplayHousingName("�������O�P");
		housingInfo.setDisplayHousingNameKana("�Ԃ�����߂��O�P");
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
		housingInfo.setLayoutComment("�Ԏ�ڍ׃R�����g");
		housingInfo.setFloorNo(2);
		housingInfo.setFloorNoNote("�����̊K���R�����g");
		housingInfo.setLandArea(new BigDecimal("50"));
		housingInfo.setLandAreaMemo("�y�n�ʐϕ⑫");
		housingInfo.setPersonalArea(new BigDecimal("60"));
		housingInfo.setPersonalAreaMemo("��L�ʐϕ⑫");
		housingInfo.setMoveinFlg("0");
		housingInfo.setParkingSituation("05");
		housingInfo.setParkingEmpExist("1");
		housingInfo.setDisplayParkingInfo("�\���p���ԏ���");
		housingInfo.setWindowDirection("06");
		housingInfo.setIconCd("REI,FRE");
		housingInfo.setBasicComment("��{���R�����g");
		calendar.set(2015, 5, 1, 0, 0, 0);
		housingInfo.setInsDate(calendar.getTime());
		housingInfo.setInsUserId("UID00001");
		calendar.set(2015, 5, 2, 0, 0, 0);
		housingInfo.setUpdDate(calendar.getTime());
		housingInfo.setUpdUserId("UID00002");
		this.housingInfoDAO.insert(new HousingInfo[]{housingInfo});

		// �����X�e�[�^�X���
		housingStatusInfo = new HousingStatusInfo();
		housingStatusInfo.setSysHousingCd("SHOU00001");
		housingStatusInfo.setHiddenFlg("0");
		this.housingStatusInfoDAO.insert(new HousingStatusInfo[]{housingStatusInfo});

		// ------ �y002�z

		// ������{���
		buildingInfo = new BuildingInfo();
		buildingInfo.setSysBuildingCd("SBLD00002");
		buildingInfo.setBuildingCd("BLD00002");
		buildingInfo.setDisplayBuildingName("�������O�Q");
		buildingInfo.setPrefCd("02");
		buildingInfo.setAddressCd("02101");
		calendar.set(2015, 5, 1, 0, 0, 0);
		buildingInfo.setCompDate(calendar.getTime());
		this.buildingInfoDAO.insert(new BuildingInfo[] {buildingInfo});

		// �����ڍ׏��
		buildingDtlInfo = new BuildingDtlInfo();
		buildingDtlInfo.setSysBuildingCd("SBLD00002");
		this.buildingDtlInfoDAO.insert(new BuildingDtlInfo[] {buildingDtlInfo});

		// ������{���
		housingInfo = new HousingInfo();
		housingInfo.setSysHousingCd("SHOU00002");
		housingInfo.setHousingCd("HOU00002");
		housingInfo.setDisplayHousingName("�������O�Q");
		housingInfo.setSysBuildingCd("SBLD00002");
		housingInfo.setPrice(100001L);
		housingInfo.setPersonalArea(new BigDecimal("65"));
		this.housingInfoDAO.insert(new HousingInfo[]{housingInfo});

		// �����X�e�[�^�X���
		housingStatusInfo = new HousingStatusInfo();
		housingStatusInfo.setSysHousingCd("SHOU00002");
		housingStatusInfo.setHiddenFlg("0");
		this.housingStatusInfoDAO.insert(new HousingStatusInfo[]{housingStatusInfo});

		// ------ �y003�z

		// ������{���
		buildingInfo = new BuildingInfo();
		buildingInfo.setSysBuildingCd("SBLD00003");
		buildingInfo.setBuildingCd("BLD00003");
		buildingInfo.setDisplayBuildingName("�������O�R");
		buildingInfo.setPrefCd("03");
		buildingInfo.setAddressCd("03101");
		this.buildingInfoDAO.insert(new BuildingInfo[] {buildingInfo});

		// �����ڍ׏��
		buildingDtlInfo = new BuildingDtlInfo();
		buildingDtlInfo.setSysBuildingCd("SBLD00003");
		this.buildingDtlInfoDAO.insert(new BuildingDtlInfo[] {buildingDtlInfo});

		// ������{���
		housingInfo = new HousingInfo();
		housingInfo.setSysHousingCd("SHOU00003");
		housingInfo.setHousingCd("HOU00003");
		housingInfo.setDisplayHousingName("�������O�R");
		housingInfo.setSysBuildingCd("SBLD00003");
		this.housingInfoDAO.insert(new HousingInfo[]{housingInfo});

		// �����X�e�[�^�X���
		housingStatusInfo = new HousingStatusInfo();
		housingStatusInfo.setSysHousingCd("SHOU00003");
		housingStatusInfo.setHiddenFlg("0");
		this.housingStatusInfoDAO.insert(new HousingStatusInfo[]{housingStatusInfo});

		// ------ �y004�z

		// ������{���
		buildingInfo = new BuildingInfo();
		buildingInfo.setSysBuildingCd("SBLD00004");
		buildingInfo.setBuildingCd("BLD00004");
		buildingInfo.setDisplayBuildingName("�������O�S");
		buildingInfo.setPrefCd("04");
		buildingInfo.setAddressCd("04101");
		this.buildingInfoDAO.insert(new BuildingInfo[] {buildingInfo});

		// �����ڍ׏��
		buildingDtlInfo = new BuildingDtlInfo();
		buildingDtlInfo.setSysBuildingCd("SBLD00004");
		this.buildingDtlInfoDAO.insert(new BuildingDtlInfo[] {buildingDtlInfo});

		// ������{���
		housingInfo = new HousingInfo();
		housingInfo.setSysHousingCd("SHOU00004");
		housingInfo.setHousingCd("HOU00004");
		housingInfo.setDisplayHousingName("�������O�S");
		housingInfo.setSysBuildingCd("SBLD00004");
		this.housingInfoDAO.insert(new HousingInfo[]{housingInfo});

		// �����X�e�[�^�X���
		housingStatusInfo = new HousingStatusInfo();
		housingStatusInfo.setSysHousingCd("SHOU00004");
		housingStatusInfo.setHiddenFlg("0");
		this.housingStatusInfoDAO.insert(new HousingStatusInfo[]{housingStatusInfo});

		// ------ �y005�z

		// ������{���
		buildingInfo = new BuildingInfo();
		buildingInfo.setSysBuildingCd("SBLD00005");
		buildingInfo.setBuildingCd("BLD00005");
		buildingInfo.setDisplayBuildingName("�������O�T");
		buildingInfo.setPrefCd("05");
		buildingInfo.setAddressCd("05101");
		this.buildingInfoDAO.insert(new BuildingInfo[] {buildingInfo});

		// �����ڍ׏��
		buildingDtlInfo = new BuildingDtlInfo();
		buildingDtlInfo.setSysBuildingCd("SBLD00005");
		this.buildingDtlInfoDAO.insert(new BuildingDtlInfo[] {buildingDtlInfo});

		// ������{���
		housingInfo = new HousingInfo();
		housingInfo.setSysHousingCd("SHOU00005");
		housingInfo.setHousingCd("HOU00005");
		housingInfo.setDisplayHousingName("�������O�T");
		housingInfo.setSysBuildingCd("SBLD00005");
		this.housingInfoDAO.insert(new HousingInfo[]{housingInfo});

		// �����X�e�[�^�X���
		housingStatusInfo = new HousingStatusInfo();
		housingStatusInfo.setSysHousingCd("SHOU00005");
		housingStatusInfo.setHiddenFlg("0");
		this.housingStatusInfoDAO.insert(new HousingStatusInfo[]{housingStatusInfo});

		// �����Ŋ�w���
		buildingStationInfo = new BuildingStationInfo();
		buildingStationInfo.setSysBuildingCd("SBLD00005");
		buildingStationInfo.setDivNo(001);
		buildingStationInfo.setDefaultRouteCd("J002003");
		buildingStationInfo.setStationCd("21137");
		this.buildingStationInfoDAO.insert(new BuildingStationInfo[]{buildingStationInfo});

		// ------ �y006�z

		// ������{���
		buildingInfo = new BuildingInfo();
		buildingInfo.setSysBuildingCd("SBLD00006");
		buildingInfo.setBuildingCd("BLD00006");
		buildingInfo.setDisplayBuildingName("�������O�U");
		buildingInfo.setPrefCd("06");
		buildingInfo.setAddressCd("06101");
		this.buildingInfoDAO.insert(new BuildingInfo[] {buildingInfo});

		// �����ڍ׏��
		buildingDtlInfo = new BuildingDtlInfo();
		buildingDtlInfo.setSysBuildingCd("SBLD00006");
		this.buildingDtlInfoDAO.insert(new BuildingDtlInfo[] {buildingDtlInfo});

		// ������{���
		housingInfo = new HousingInfo();
		housingInfo.setSysHousingCd("SHOU00006");
		housingInfo.setHousingCd("HOU00006");
		housingInfo.setDisplayHousingName("�������O�U");
		housingInfo.setSysBuildingCd("SBLD00006");
		this.housingInfoDAO.insert(new HousingInfo[]{housingInfo});

		// �����X�e�[�^�X���
		housingStatusInfo = new HousingStatusInfo();
		housingStatusInfo.setSysHousingCd("SHOU00006");
		housingStatusInfo.setHiddenFlg("0");
		this.housingStatusInfoDAO.insert(new HousingStatusInfo[]{housingStatusInfo});

		// �����Ŋ�w���
		buildingStationInfo = new BuildingStationInfo();
		buildingStationInfo.setSysBuildingCd("SBLD00006");
		buildingStationInfo.setDivNo(001);
		buildingStationInfo.setDefaultRouteCd("J002002");
		buildingStationInfo.setStationCd("21392");
		this.buildingStationInfoDAO.insert(new BuildingStationInfo[]{buildingStationInfo});

		// ------ �y007�z

		// ������{���
		buildingInfo = new BuildingInfo();
		buildingInfo.setSysBuildingCd("SBLD00007");
		buildingInfo.setBuildingCd("BLD00007");
		buildingInfo.setDisplayBuildingName("�������O�V");
		buildingInfo.setPrefCd("07");
		buildingInfo.setAddressCd("07101");
		this.buildingInfoDAO.insert(new BuildingInfo[] {buildingInfo});

		// �����ڍ׏��
		buildingDtlInfo = new BuildingDtlInfo();
		buildingDtlInfo.setSysBuildingCd("SBLD00007");
		this.buildingDtlInfoDAO.insert(new BuildingDtlInfo[] {buildingDtlInfo});

		// ������{���
		housingInfo = new HousingInfo();
		housingInfo.setSysHousingCd("SHOU00007");
		housingInfo.setHousingCd("HOU00007");
		housingInfo.setDisplayHousingName("�������O�V");
		housingInfo.setSysBuildingCd("SBLD00007");
		housingInfo.setPrice(100000L);
		this.housingInfoDAO.insert(new HousingInfo[]{housingInfo});

		// �����X�e�[�^�X���
		housingStatusInfo = new HousingStatusInfo();
		housingStatusInfo.setSysHousingCd("SHOU00007");
		housingStatusInfo.setHiddenFlg("0");
		this.housingStatusInfoDAO.insert(new HousingStatusInfo[]{housingStatusInfo});

		// ------ �y008�z

		// ������{���
		buildingInfo = new BuildingInfo();
		buildingInfo.setSysBuildingCd("SBLD00008");
		buildingInfo.setBuildingCd("BLD00008");
		buildingInfo.setDisplayBuildingName("�������O�W");
		buildingInfo.setPrefCd("08");
		buildingInfo.setAddressCd("08101");
		this.buildingInfoDAO.insert(new BuildingInfo[] {buildingInfo});

		// �����ڍ׏��
		buildingDtlInfo = new BuildingDtlInfo();
		buildingDtlInfo.setSysBuildingCd("SBLD00008");
		this.buildingDtlInfoDAO.insert(new BuildingDtlInfo[] {buildingDtlInfo});

		// ������{���
		housingInfo = new HousingInfo();
		housingInfo.setSysHousingCd("SHOU00008");
		housingInfo.setHousingCd("HOU00008");
		housingInfo.setDisplayHousingName("�������O�W");
		housingInfo.setSysBuildingCd("SBLD00008");
		housingInfo.setLandArea(new BigDecimal("50"));
		this.housingInfoDAO.insert(new HousingInfo[]{housingInfo});

		// �����X�e�[�^�X���
		housingStatusInfo = new HousingStatusInfo();
		housingStatusInfo.setSysHousingCd("SHOU00008");
		housingStatusInfo.setHiddenFlg("0");
		this.housingStatusInfoDAO.insert(new HousingStatusInfo[]{housingStatusInfo});

		// ------ �y009�z

		// ������{���
		buildingInfo = new BuildingInfo();
		buildingInfo.setSysBuildingCd("SBLD00009");
		buildingInfo.setBuildingCd("BLD00009");
		buildingInfo.setDisplayBuildingName("�������O�X");
		buildingInfo.setPrefCd("09");
		buildingInfo.setAddressCd("09101");
		this.buildingInfoDAO.insert(new BuildingInfo[] {buildingInfo});

		// �����ڍ׏��
		buildingDtlInfo = new BuildingDtlInfo();
		buildingDtlInfo.setSysBuildingCd("SBLD00009");
		this.buildingDtlInfoDAO.insert(new BuildingDtlInfo[] {buildingDtlInfo});

		// ������{���
		housingInfo = new HousingInfo();
		housingInfo.setSysHousingCd("SHOU00009");
		housingInfo.setHousingCd("HOU00009");
		housingInfo.setDisplayHousingName("�������O�X");
		housingInfo.setSysBuildingCd("SBLD00009");
		housingInfo.setPersonalArea(new BigDecimal("60"));
		this.housingInfoDAO.insert(new HousingInfo[]{housingInfo});

		// �����X�e�[�^�X���
		housingStatusInfo = new HousingStatusInfo();
		housingStatusInfo.setSysHousingCd("SHOU00009");
		housingStatusInfo.setHiddenFlg("0");
		this.housingStatusInfoDAO.insert(new HousingStatusInfo[]{housingStatusInfo});

		// ------ �y010�z

		// ������{���
		buildingInfo = new BuildingInfo();
		buildingInfo.setSysBuildingCd("SBLD00010");
		buildingInfo.setBuildingCd("BLD00010");
		buildingInfo.setDisplayBuildingName("�������P�O");
		buildingInfo.setPrefCd("10");
		buildingInfo.setAddressCd("10101");
		buildingInfo.setHousingKindCd("001");
		this.buildingInfoDAO.insert(new BuildingInfo[] {buildingInfo});

		// �����ڍ׏��
		buildingDtlInfo = new BuildingDtlInfo();
		buildingDtlInfo.setSysBuildingCd("SBLD00010");
		this.buildingDtlInfoDAO.insert(new BuildingDtlInfo[] {buildingDtlInfo});

		// ������{���
		housingInfo = new HousingInfo();
		housingInfo.setSysHousingCd("SHOU00010");
		housingInfo.setHousingCd("HOU00010");
		housingInfo.setDisplayHousingName("�������P�O");
		housingInfo.setSysBuildingCd("SBLD00010");
		housingInfo.setPersonalArea(new BigDecimal("60"));
		this.housingInfoDAO.insert(new HousingInfo[]{housingInfo});

		// �����X�e�[�^�X���
		housingStatusInfo = new HousingStatusInfo();
		housingStatusInfo.setSysHousingCd("SHOU00010");
		housingStatusInfo.setHiddenFlg("0");
		this.housingStatusInfoDAO.insert(new HousingStatusInfo[]{housingStatusInfo});

		// ------ �y011�z

		// ������{���
		buildingInfo = new BuildingInfo();
		buildingInfo.setSysBuildingCd("SBLD00011");
		buildingInfo.setBuildingCd("BLD00011");
		buildingInfo.setDisplayBuildingName("�������P�P");
		buildingInfo.setPrefCd("11");
		buildingInfo.setAddressCd("11101");
		this.buildingInfoDAO.insert(new BuildingInfo[] {buildingInfo});

		// �����ڍ׏��
		buildingDtlInfo = new BuildingDtlInfo();
		buildingDtlInfo.setSysBuildingCd("SBLD00011");
		this.buildingDtlInfoDAO.insert(new BuildingDtlInfo[] {buildingDtlInfo});

		// ������{���
		housingInfo = new HousingInfo();
		housingInfo.setSysHousingCd("SHOU00011");
		housingInfo.setHousingCd("HOU00011");
		housingInfo.setDisplayHousingName("�������P�P");
		housingInfo.setSysBuildingCd("SBLD00011");
		housingInfo.setPersonalArea(new BigDecimal("60"));
		housingInfo.setLayoutCd("001");
		this.housingInfoDAO.insert(new HousingInfo[]{housingInfo});

		// �����������������
		housingPartInfo = new HousingPartInfo();
		housingPartInfo.setSysHousingCd("SHOU00011");
		housingPartInfo.setPartSrchCd("S05");
		this.housingPartInfoDAO.insert(new HousingPartInfo[] { housingPartInfo });

		// �����X�e�[�^�X���
		housingStatusInfo = new HousingStatusInfo();
		housingStatusInfo.setSysHousingCd("SHOU00011");
		housingStatusInfo.setHiddenFlg("0");
		this.housingStatusInfoDAO.insert(new HousingStatusInfo[]{housingStatusInfo});

		// ------ �y012�z

		// ������{���
		buildingInfo = new BuildingInfo();
		buildingInfo.setSysBuildingCd("SBLD00012");
		buildingInfo.setBuildingCd("BLD00012");
		buildingInfo.setDisplayBuildingName("�������P�Q");
		buildingInfo.setPrefCd("12");
		buildingInfo.setAddressCd("12101");
		this.buildingInfoDAO.insert(new BuildingInfo[] {buildingInfo});

		// �����ڍ׏��
		buildingDtlInfo = new BuildingDtlInfo();
		buildingDtlInfo.setSysBuildingCd("SBLD00012");
		buildingDtlInfo.setBuildingArea(new BigDecimal("50"));
		this.buildingDtlInfoDAO.insert(new BuildingDtlInfo[] {buildingDtlInfo});

		// ������{���
		housingInfo = new HousingInfo();
		housingInfo.setSysHousingCd("SHOU00012");
		housingInfo.setHousingCd("HOU00012");
		housingInfo.setDisplayHousingName("�������P�Q");
		housingInfo.setSysBuildingCd("SBLD00012");
		housingInfo.setPersonalArea(new BigDecimal("60"));
		housingInfo.setLayoutCd("001");
		this.housingInfoDAO.insert(new HousingInfo[]{housingInfo});

		// �����������������
		housingPartInfo = new HousingPartInfo();
		housingPartInfo.setSysHousingCd("SHOU00012");
		housingPartInfo.setPartSrchCd("FRE");
		this.housingPartInfoDAO.insert(new HousingPartInfo[] { housingPartInfo });

		// �����X�e�[�^�X���
		housingStatusInfo = new HousingStatusInfo();
		housingStatusInfo.setSysHousingCd("SHOU00012");
		housingStatusInfo.setHiddenFlg("0");
		this.housingStatusInfoDAO.insert(new HousingStatusInfo[]{housingStatusInfo});

		// ------ �y013�z

		// ������{���
		buildingInfo = new BuildingInfo();
		buildingInfo.setSysBuildingCd("SBLD00013");
		buildingInfo.setBuildingCd("BLD00013");
		buildingInfo.setDisplayBuildingName("�������P�R");
		buildingInfo.setPrefCd("13");
		buildingInfo.setAddressCd("13101");
		this.buildingInfoDAO.insert(new BuildingInfo[] {buildingInfo});

		// �����ڍ׏��
		buildingDtlInfo = new BuildingDtlInfo();
		buildingDtlInfo.setSysBuildingCd("SBLD00013");
		buildingDtlInfo.setBuildingArea(new BigDecimal("50"));
		this.buildingDtlInfoDAO.insert(new BuildingDtlInfo[] {buildingDtlInfo});

		// ������{���
		housingInfo = new HousingInfo();
		housingInfo.setSysHousingCd("SHOU00013");
		housingInfo.setHousingCd("HOU00013");
		housingInfo.setDisplayHousingName("�������P�R");
		housingInfo.setSysBuildingCd("SBLD00013");
		housingInfo.setPersonalArea(new BigDecimal("60"));
		housingInfo.setLayoutCd("001");
		this.housingInfoDAO.insert(new HousingInfo[]{housingInfo});

		// �����������������
		housingPartInfo = new HousingPartInfo();
		housingPartInfo.setSysHousingCd("SHOU00013");
		housingPartInfo.setPartSrchCd("REI");
		this.housingPartInfoDAO.insert(new HousingPartInfo[] { housingPartInfo });

		// �����X�e�[�^�X���
		housingStatusInfo = new HousingStatusInfo();
		housingStatusInfo.setSysHousingCd("SHOU00013");
		housingStatusInfo.setHiddenFlg("0");
		this.housingStatusInfoDAO.insert(new HousingStatusInfo[]{housingStatusInfo});

	}

	/**
	 * �������̌�������<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�������ʂ��������擾����Ă��鎖</li>
	 * </ul>
	 */
	@Test
	public void SearchHousingTest() throws Exception {

		HousingSearchForm searchForm = housingFormFactory.createHousingSearchForm();
		searchForm.setKeyHousingCd("HOU00001");

		int cnt = this.housingManage.searchHousing(searchForm);
		Assert.assertEquals("�������ʂ��擾�ł��Ă��鎖", 1 , cnt);

		Calendar calendar = Calendar.getInstance();
		calendar.clear();

		// ������{���
		Housing housing = searchForm.getRows().get(0);
		HousingInfo housingInfo = (HousingInfo)housing.getHousingInfo().getItems().get("housingInfo");
		Assert.assertEquals("������{���̃V�X�e������CD���������擾����Ă��鎖", "SHOU00001", housingInfo.getSysHousingCd());
		Assert.assertEquals("������{���̕����ԍ����������擾����Ă��鎖", "HOU00001", housingInfo.getHousingCd());
		Assert.assertEquals("������{���̕\���p���������������擾����Ă��鎖", "�������O�P", housingInfo.getDisplayHousingName());
		Assert.assertEquals("������{���̕\���p�������ӂ肪�Ȃ��������擾����Ă��鎖", "�Ԃ�����߂��O�P", housingInfo.getDisplayHousingNameKana());
		Assert.assertEquals("������{���̕����ԍ����������擾����Ă��鎖", "101", housingInfo.getRoomNo());
		Assert.assertEquals("������{���̃V�X�e������CD���������擾����Ă��鎖", "SBLD00001", housingInfo.getSysBuildingCd());
		Assert.assertEquals("������{���̒���/���i���������擾����Ă��鎖", 100000L, housingInfo.getPrice().longValue());
		Assert.assertEquals("������{���̊Ǘ���������擾����Ă��鎖", 5000L, housingInfo.getUpkeep().longValue());
		Assert.assertEquals("������{���̋��v��������擾����Ă��鎖", 6000L, housingInfo.getCommonAreaFee().longValue());
		Assert.assertEquals("������{���̏C�U�ϗ���������擾����Ă��鎖", 7000L, housingInfo.getMenteFee().longValue());
		Assert.assertTrue("������{���̕~�����������擾����Ă��鎖", new BigDecimal("200000.00").compareTo(housingInfo.getSecDeposit()) == 0);
		Assert.assertEquals("������{���̕~���P�ʂ��������擾����Ă��鎖", "01", housingInfo.getSecDepositCrs());
		Assert.assertTrue("������{���̕ۏ؋����������擾����Ă��鎖", new BigDecimal("300000.00").compareTo(housingInfo.getBondChrg()) == 0);
		Assert.assertEquals("������{���̕ۏ؋��P�ʂ��������擾����Ă��鎖", "02", housingInfo.getBondChrgCrs());
		Assert.assertEquals("������{���̕~������敪���������擾����Ă��鎖", "03", housingInfo.getDepositDiv());
		Assert.assertTrue("������{���̕~������z���������擾����Ă��鎖", new BigDecimal("400000.00").compareTo(housingInfo.getDeposit()) == 0);
		Assert.assertEquals("������{���̕~������P�ʂ��������擾����Ă��鎖", "04", housingInfo.getDepositCrs());
		Assert.assertEquals("������{���̊Ԏ�CD���������擾����Ă��鎖", "001", housingInfo.getLayoutCd());
		Assert.assertEquals("������{���̊Ԏ�ڍ׃R�����g���������擾����Ă��鎖", "�Ԏ�ڍ׃R�����g", housingInfo.getLayoutComment());
		Assert.assertEquals("������{���̕����̊K�����������擾����Ă��鎖", 2, housingInfo.getFloorNo().intValue());
		Assert.assertEquals("������{���̕����̊K���R�����g���������擾����Ă��鎖", "�����̊K���R�����g", housingInfo.getFloorNoNote());
		Assert.assertTrue("������{���̓y�n�ʐς��������擾����Ă��鎖", new BigDecimal("50.00").compareTo(housingInfo.getLandArea()) == 0);
		Assert.assertEquals("������{���̓y�n�ʐ�_�⑫���������擾����Ă��鎖", "�y�n�ʐϕ⑫", housingInfo.getLandAreaMemo());
		Assert.assertTrue("������{���̐�L�ʐς��������擾����Ă��鎖", new BigDecimal("60.00").compareTo(housingInfo.getPersonalArea()) == 0);
		Assert.assertEquals("������{���̐�L�ʐ�_�⑫���������擾����Ă��鎖", "��L�ʐϕ⑫", housingInfo.getPersonalAreaMemo());
		Assert.assertEquals("������{���̓�����ԃt���O���������擾����Ă��鎖", "0", housingInfo.getMoveinFlg());
		Assert.assertEquals("������{���̒��ԏ�̏󋵂��������擾����Ă��鎖", "05", housingInfo.getParkingSituation());
		Assert.assertEquals("������{���̒��ԏ��̗L�����������擾����Ă��鎖", "1", housingInfo.getParkingEmpExist());
		Assert.assertEquals("������{���̕\���p���ԏ��񂪐������擾����Ă��鎖", "�\���p���ԏ���", housingInfo.getDisplayParkingInfo());
		Assert.assertEquals("������{���̑��̌������������擾����Ă��鎖", "06", housingInfo.getWindowDirection());
		Assert.assertEquals("������{���̃A�C�R����񂪐������擾����Ă��鎖", "REI,FRE", housingInfo.getIconCd());
		Assert.assertEquals("������{���̊�{���R�����g���������擾����Ă��鎖", "��{���R�����g", housingInfo.getBasicComment());
		calendar.set(2015, 5, 1, 0, 0, 0);
		Assert.assertEquals("������{���̓o�^�����������擾����Ă��鎖", calendar.getTime(), housingInfo.getInsDate());
		Assert.assertEquals("������{���̓o�^�҂��������擾����Ă��鎖", "UID00001", housingInfo.getInsUserId());
		calendar.set(2015, 5, 2, 0, 0, 0);
		Assert.assertEquals("������{���̍X�V�҂��������擾����Ă��鎖", calendar.getTime(), housingInfo.getUpdDate());
		Assert.assertEquals("������{���̍X�V�����������擾����Ă��鎖", "UID00002", housingInfo.getUpdUserId());

		// ������{���
		Building building = housing.getBuilding();
		BuildingInfo buildingInfo = (BuildingInfo) building.getBuildingInfo().getItems().get("buildingInfo");
		Assert.assertEquals("������{���̃V�X�e������CD���������擾����Ă��鎖", "SBLD00001", buildingInfo.getSysBuildingCd());
		Assert.assertEquals("������{���̌����ԍ����������擾����Ă��鎖", "BLD00001", buildingInfo.getBuildingCd());
		Assert.assertEquals("������{���̕������CD���������擾����Ă��鎖", "001", buildingInfo.getHousingKindCd());
		Assert.assertEquals("������{���̌����\��CD���������擾����Ă��鎖", "002", buildingInfo.getStructCd());
		Assert.assertEquals("������{���̕\���p���������������擾����Ă��鎖", "�������O�P", buildingInfo.getDisplayBuildingName());
		Assert.assertEquals("������{���̕\���p�������ӂ肪�Ȃ��������擾����Ă��鎖", "���Ă��̂߂��O�P", buildingInfo.getDisplayBuildingNameKana());
		Assert.assertEquals("������{���̏��ݒn�E�X�֔ԍ����������擾����Ă��鎖", "1234567", buildingInfo.getZip());
		Assert.assertEquals("������{���̏��ݒn�E�s���{��CD���������擾����Ă��鎖", "01", buildingInfo.getPrefCd());
		Assert.assertEquals("������{���̏��ݒn�E�s�撬��CD���������擾����Ă��鎖", "01101", buildingInfo.getAddressCd());
		Assert.assertEquals("������{���̏��ݒn�E�s�撬�������������擾����Ă��鎖", "�s�撬����", buildingInfo.getAddressName());
		Assert.assertEquals("������{���̏��ݒn�E�����Ԓn���������擾����Ă��鎖", "�����Ԓn", buildingInfo.getAddressOther1());
		Assert.assertEquals("������{���̏��ݒn�E���������̑� ���������擾����Ă��鎖", "���������̑�", buildingInfo.getAddressOther2());
		calendar.set(2015, 4, 1, 0, 0, 0);
		Assert.assertEquals("������{���̏v�H�N�����������擾����Ă��鎖", calendar.getTime(), buildingInfo.getCompDate());
		Assert.assertEquals("������{���̏v�H�{���������擾����Ă��鎖", "2015�N4����{", buildingInfo.getCompTenDays());
		Assert.assertEquals("������{���̑��K�����������擾����Ă��鎖", 5, buildingInfo.getTotalFloors().intValue());
		calendar.set(2015, 4, 2, 0, 0, 0);
		Assert.assertEquals("������{���̓o�^�����������擾����Ă��鎖", calendar.getTime(), buildingInfo.getInsDate());
		Assert.assertEquals("������{���̓o�^�҂��������擾����Ă��鎖", "UID00001", buildingInfo.getInsUserId());
		calendar.set(2015, 4, 3, 0, 0, 0);
		Assert.assertEquals("������{���̍ŏI�X�V�����������擾����Ă��鎖", calendar.getTime(), buildingInfo.getUpdDate());
		Assert.assertEquals("������{���̍ŏI�X�V�҂��������擾����Ă��鎖", "UID00002", buildingInfo.getUpdUserId());

		// �����ڍ׏��
		BuildingDtlInfo buildingDtlInfo = (BuildingDtlInfo) building.getBuildingInfo().getItems().get("buildingDtlInfo");
		Assert.assertEquals("�����ڍ׏��̃V�X�e������CD���������擾����Ă��鎖", "SBLD00001", buildingDtlInfo.getSysBuildingCd());
		Assert.assertTrue("�����ڍ׏��̌����ʐς��������擾����Ă��鎖", new BigDecimal("50.00").compareTo(buildingDtlInfo.getBuildingArea()) == 0);
		Assert.assertEquals("�����ڍ׏��̌����ʐ�_�⑫���������擾����Ă��鎖", "�����ʐϕ⑫", buildingDtlInfo.getBuildingAreaMemo());
		Assert.assertTrue("�����ڍ׏��̌��؂������������擾����Ă��鎖", new BigDecimal("60.00").compareTo(buildingDtlInfo.getCoverage()) == 0);
		Assert.assertEquals("�����ڍ׏��̌��؂���_�׍����������擾����Ă��鎖", "���؂����⑫", buildingDtlInfo.getCoverageMemo());
		Assert.assertTrue("�����ڍ׏��̗e�ϗ����������擾����Ă��鎖", new BigDecimal("200.00").compareTo(buildingDtlInfo.getBuildingRate()) == 0);
		Assert.assertEquals("�����ڍ׏��̗e�ϗ�_�⑫���������擾����Ă��鎖", "�e�ϗ��⑫", buildingDtlInfo.getBuildingRateMemo());
		Assert.assertEquals("�����ڍ׏��̑��ː����������擾����Ă��鎖", 10, buildingDtlInfo.getTotalHouseCnt().intValue());
		Assert.assertEquals("�����ڍ׏��̒��݌ː����������擾����Ă��鎖", 9, buildingDtlInfo.getLeaseHouseCnt().intValue());
		Assert.assertTrue("�����ڍ׏��̌����ܓx���������擾����Ă��鎖", new BigDecimal("123.4567890").compareTo(buildingDtlInfo.getBuildingLatitude()) == 0);
		Assert.assertTrue("�����ڍ׏��̌����o�x���������擾����Ă��鎖", new BigDecimal("223.4567890").compareTo(buildingDtlInfo.getBuildingLongitude()) == 0);

	}

	/**
	 * �����ԍ��ɂ�镨�����̌�������<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�����ԍ��ɂ�錟�����ʂ��������擾����Ă��鎖</li>
	 * </ul>
	 */
	@Test
	public void SearchHousingKeyHousingCdTest() throws Exception {

		HousingSearchForm searchForm = housingFormFactory.createHousingSearchForm();
		searchForm.setKeyHousingCd("HOU00002");

		int cnt = this.housingManage.searchHousing(searchForm);
		Assert.assertEquals("�������ʂ��擾�ł��Ă��鎖", 1 , cnt);

		// ������{���
		Housing housing = searchForm.getRows().get(0);
		HousingInfo housingInfo = (HousingInfo)housing.getHousingInfo().getItems().get("housingInfo");
		Assert.assertEquals("������{���̃V�X�e������CD���������擾����Ă��鎖", "SHOU00002", housingInfo.getSysHousingCd());

		// ������{���
		Building building = housing.getBuilding();
		BuildingInfo buildingInfo = (BuildingInfo) building.getBuildingInfo().getItems().get("buildingInfo");
		Assert.assertEquals("������{���̃V�X�e������CD���������擾����Ă��鎖", "SBLD00002", buildingInfo.getSysBuildingCd());

		// �����ڍ׏��
		BuildingDtlInfo buildingDtlInfo = (BuildingDtlInfo) building.getBuildingInfo().getItems().get("buildingDtlInfo");
		Assert.assertEquals("�����ڍ׏��̃V�X�e������CD���������擾����Ă��鎖", "SBLD00002", buildingDtlInfo.getSysBuildingCd());

		// �����̌����l
		searchForm = housingFormFactory.createHousingSearchForm();
		searchForm.setKeyHousingCd("HOU00002,HOU00003");

		cnt = this.housingManage.searchHousing(searchForm);
		Assert.assertEquals("�������ʂ��擾�ł��Ă��鎖", 2 , cnt);

		// ������{���
		housing = searchForm.getRows().get(0);
		housingInfo = (HousingInfo)housing.getHousingInfo().getItems().get("housingInfo");
		Assert.assertEquals("������{���̃V�X�e������CD���������擾����Ă��鎖", "SHOU00002", housingInfo.getSysHousingCd());

		// ������{���
		building = housing.getBuilding();
		buildingInfo = (BuildingInfo) building.getBuildingInfo().getItems().get("buildingInfo");
		Assert.assertEquals("������{���̃V�X�e������CD���������擾����Ă��鎖", "SBLD00002", buildingInfo.getSysBuildingCd());

		// �����ڍ׏��
		buildingDtlInfo = (BuildingDtlInfo) building.getBuildingInfo().getItems().get("buildingDtlInfo");
		Assert.assertEquals("�����ڍ׏��̃V�X�e������CD���������擾����Ă��鎖", "SBLD00002", buildingDtlInfo.getSysBuildingCd());

		// ������{���
		housing = searchForm.getRows().get(1);
		housingInfo = (HousingInfo)housing.getHousingInfo().getItems().get("housingInfo");
		Assert.assertEquals("������{���̃V�X�e������CD���������擾����Ă��鎖", "SHOU00003", housingInfo.getSysHousingCd());

		// ������{���
		building = housing.getBuilding();
		buildingInfo = (BuildingInfo) building.getBuildingInfo().getItems().get("buildingInfo");
		Assert.assertEquals("������{���̃V�X�e������CD���������擾����Ă��鎖", "SBLD00003", buildingInfo.getSysBuildingCd());

		// �����ڍ׏��
		buildingDtlInfo = (BuildingDtlInfo) building.getBuildingInfo().getItems().get("buildingDtlInfo");
		Assert.assertEquals("�����ڍ׏��̃V�X�e������CD���������擾����Ă��鎖", "SBLD00003", buildingDtlInfo.getSysBuildingCd());

	}

	/**
	 * �������ɂ�镨�����̌�������<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�������ɂ�錟���i������v�j���ʂ��������擾����Ă��鎖</li>
	 * </ul>
	 */
	@Test
	public void SearchHousingKeyDisplayHousingNameTest() throws Exception {

		HousingSearchForm searchForm = housingFormFactory.createHousingSearchForm();
		searchForm.setKeyDisplayHousingName("�O�Q");

		int cnt = this.housingManage.searchHousing(searchForm);
		Assert.assertEquals("�������ʂ��擾�ł��Ă��鎖", 1 , cnt);

		// ������{���
		Housing housing = searchForm.getRows().get(0);
		HousingInfo housingInfo = (HousingInfo)housing.getHousingInfo().getItems().get("housingInfo");
		Assert.assertEquals("������{���̃V�X�e������CD���������擾����Ă��鎖", "SHOU00002", housingInfo.getSysHousingCd());

		// ������{���
		Building building = housing.getBuilding();
		BuildingInfo buildingInfo = (BuildingInfo) building.getBuildingInfo().getItems().get("buildingInfo");
		Assert.assertEquals("������{���̃V�X�e������CD���������擾����Ă��鎖", "SBLD00002", buildingInfo.getSysBuildingCd());

		// �����ڍ׏��
		BuildingDtlInfo buildingDtlInfo = (BuildingDtlInfo) building.getBuildingInfo().getItems().get("buildingDtlInfo");
		Assert.assertEquals("�����ڍ׏��̃V�X�e������CD���������擾����Ă��鎖", "SBLD00002", buildingDtlInfo.getSysBuildingCd());

	}

	/**
	 * �s���{��CD�ɂ�镨�����̌�������<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�s���{��CD�ɂ�錟�����ʂ��������擾����Ă��鎖</li>
	 * </ul>
	 */
	@Test
	public void SearchHousingKeyPrefCdTest() throws Exception {

		HousingSearchForm searchForm = housingFormFactory.createHousingSearchForm();
		searchForm.setKeyPrefCd("03");

		int cnt = this.housingManage.searchHousing(searchForm);
		Assert.assertEquals("�������ʂ��擾�ł��Ă��鎖", 1 , cnt);

		// ������{���
		Housing housing = searchForm.getRows().get(0);
		HousingInfo housingInfo = (HousingInfo)housing.getHousingInfo().getItems().get("housingInfo");
		Assert.assertEquals("������{���̃V�X�e������CD���������擾����Ă��鎖", "SHOU00003", housingInfo.getSysHousingCd());

		// ������{���
		Building building = housing.getBuilding();
		BuildingInfo buildingInfo = (BuildingInfo) building.getBuildingInfo().getItems().get("buildingInfo");
		Assert.assertEquals("������{���̃V�X�e������CD���������擾����Ă��鎖", "SBLD00003", buildingInfo.getSysBuildingCd());

		// �����ڍ׏��
		BuildingDtlInfo buildingDtlInfo = (BuildingDtlInfo) building.getBuildingInfo().getItems().get("buildingDtlInfo");
		Assert.assertEquals("�����ڍ׏��̃V�X�e������CD���������擾����Ă��鎖", "SBLD00003", buildingDtlInfo.getSysBuildingCd());

		// �����̌����l
		searchForm = housingFormFactory.createHousingSearchForm();
		searchForm.setKeyPrefCd("03,04");

		cnt = this.housingManage.searchHousing(searchForm);
		Assert.assertEquals("�������ʂ��擾�ł��Ă��鎖", 2 , cnt);

		// ������{���
		housing = searchForm.getRows().get(0);
		housingInfo = (HousingInfo)housing.getHousingInfo().getItems().get("housingInfo");
		Assert.assertEquals("������{���̃V�X�e������CD���������擾����Ă��鎖", "SHOU00003", housingInfo.getSysHousingCd());

		// ������{���
		building = housing.getBuilding();
		buildingInfo = (BuildingInfo) building.getBuildingInfo().getItems().get("buildingInfo");
		Assert.assertEquals("������{���̃V�X�e������CD���������擾����Ă��鎖", "SBLD00003", buildingInfo.getSysBuildingCd());

		// �����ڍ׏��
		buildingDtlInfo = (BuildingDtlInfo) building.getBuildingInfo().getItems().get("buildingDtlInfo");
		Assert.assertEquals("�����ڍ׏��̃V�X�e������CD���������擾����Ă��鎖", "SBLD00003", buildingDtlInfo.getSysBuildingCd());

		// ������{���
		housing = searchForm.getRows().get(1);
		housingInfo = (HousingInfo)housing.getHousingInfo().getItems().get("housingInfo");
		Assert.assertEquals("������{���̃V�X�e������CD���������擾����Ă��鎖", "SHOU00004", housingInfo.getSysHousingCd());

		// ������{���
		building = housing.getBuilding();
		buildingInfo = (BuildingInfo) building.getBuildingInfo().getItems().get("buildingInfo");
		Assert.assertEquals("������{���̃V�X�e������CD���������擾����Ă��鎖", "SBLD00004", buildingInfo.getSysBuildingCd());

		// �����ڍ׏��
		buildingDtlInfo = (BuildingDtlInfo) building.getBuildingInfo().getItems().get("buildingDtlInfo");
		Assert.assertEquals("�����ڍ׏��̃V�X�e������CD���������擾����Ă��鎖", "SBLD00004", buildingDtlInfo.getSysBuildingCd());

	}

	/**
	 * �s�撬��CD�ɂ�镨�����̌�������<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�s�撬��CD�ɂ�錟�����ʂ��������擾����Ă��鎖</li>
	 * </ul>
	 */
	@Test
	public void SearchHousingKeyAddressCdTest() throws Exception {

		HousingSearchForm searchForm = housingFormFactory.createHousingSearchForm();
		searchForm.setKeyAddressCd("04101");

		int cnt = this.housingManage.searchHousing(searchForm);
		Assert.assertEquals("�������ʂ��擾�ł��Ă��鎖", 1 , cnt);

		// ������{���
		Housing housing = searchForm.getRows().get(0);
		HousingInfo housingInfo = (HousingInfo)housing.getHousingInfo().getItems().get("housingInfo");
		Assert.assertEquals("������{���̃V�X�e������CD���������擾����Ă��鎖", "SHOU00004", housingInfo.getSysHousingCd());

		// ������{���
		Building building = housing.getBuilding();
		BuildingInfo buildingInfo = (BuildingInfo) building.getBuildingInfo().getItems().get("buildingInfo");
		Assert.assertEquals("������{���̃V�X�e������CD���������擾����Ă��鎖", "SBLD00004", buildingInfo.getSysBuildingCd());

		// �����ڍ׏��
		BuildingDtlInfo buildingDtlInfo = (BuildingDtlInfo) building.getBuildingInfo().getItems().get("buildingDtlInfo");
		Assert.assertEquals("�����ڍ׏��̃V�X�e������CD���������擾����Ă��鎖", "SBLD00004", buildingDtlInfo.getSysBuildingCd());

		// �����̌����l
		searchForm = housingFormFactory.createHousingSearchForm();
		searchForm.setKeyAddressCd("04101,05101");

		cnt = this.housingManage.searchHousing(searchForm);
		Assert.assertEquals("�������ʂ��擾�ł��Ă��鎖", 2 , cnt);

		// ������{���
		housing = searchForm.getRows().get(0);
		housingInfo = (HousingInfo)housing.getHousingInfo().getItems().get("housingInfo");
		Assert.assertEquals("������{���̃V�X�e������CD���������擾����Ă��鎖", "SHOU00004", housingInfo.getSysHousingCd());

		// ������{���
		building = housing.getBuilding();
		buildingInfo = (BuildingInfo) building.getBuildingInfo().getItems().get("buildingInfo");
		Assert.assertEquals("������{���̃V�X�e������CD���������擾����Ă��鎖", "SBLD00004", buildingInfo.getSysBuildingCd());

		// �����ڍ׏��
		buildingDtlInfo = (BuildingDtlInfo) building.getBuildingInfo().getItems().get("buildingDtlInfo");
		Assert.assertEquals("�����ڍ׏��̃V�X�e������CD���������擾����Ă��鎖", "SBLD00004", buildingDtlInfo.getSysBuildingCd());

		// ������{���
		housing = searchForm.getRows().get(1);
		housingInfo = (HousingInfo)housing.getHousingInfo().getItems().get("housingInfo");
		Assert.assertEquals("������{���̃V�X�e������CD���������擾����Ă��鎖", "SHOU00005", housingInfo.getSysHousingCd());

		// ������{���
		building = housing.getBuilding();
		buildingInfo = (BuildingInfo) building.getBuildingInfo().getItems().get("buildingInfo");
		Assert.assertEquals("������{���̃V�X�e������CD���������擾����Ă��鎖", "SBLD00005", buildingInfo.getSysBuildingCd());

		// �����ڍ׏��
		buildingDtlInfo = (BuildingDtlInfo) building.getBuildingInfo().getItems().get("buildingDtlInfo");
		Assert.assertEquals("�����ڍ׏��̃V�X�e������CD���������擾����Ă��鎖", "SBLD00005", buildingDtlInfo.getSysBuildingCd());

	}

	/**
	 * ����CD�ɂ�镨�����̌�������<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>����CD�ɂ�錟�����ʂ��������擾����Ă��鎖</li>
	 * </ul>
	 */
	@Test
	public void SearchHousingKeyRouteCdTest() throws Exception {

		HousingSearchForm searchForm = housingFormFactory.createHousingSearchForm();
		searchForm.setKeyRouteCd("J002003");

		int cnt = this.housingManage.searchHousing(searchForm);
		Assert.assertEquals("�������ʂ��擾�ł��Ă��鎖", 1 , cnt);

		// ������{���
		Housing housing = searchForm.getRows().get(0);
		HousingInfo housingInfo = (HousingInfo)housing.getHousingInfo().getItems().get("housingInfo");
		Assert.assertEquals("������{���̃V�X�e������CD���������擾����Ă��鎖", "SHOU00005", housingInfo.getSysHousingCd());

		// ������{���
		Building building = housing.getBuilding();
		BuildingInfo buildingInfo = (BuildingInfo) building.getBuildingInfo().getItems().get("buildingInfo");
		Assert.assertEquals("������{���̃V�X�e������CD���������擾����Ă��鎖", "SBLD00005", buildingInfo.getSysBuildingCd());

		// �����ڍ׏��
		BuildingDtlInfo buildingDtlInfo = (BuildingDtlInfo) building.getBuildingInfo().getItems().get("buildingDtlInfo");
		Assert.assertEquals("�����ڍ׏��̃V�X�e������CD���������擾����Ă��鎖", "SBLD00005", buildingDtlInfo.getSysBuildingCd());

	}

	/**
	 * �wCD�ɂ�镨�����̌�������<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�wCD�ɂ�錟�����ʂ��������擾����Ă��鎖</li>
	 * </ul>
	 */
	@Test
	public void SearchHousingKeyStationCdTest() throws Exception {

		HousingSearchForm searchForm = housingFormFactory.createHousingSearchForm();
		searchForm.setKeyRouteCd("J002002");
		searchForm.setKeyStationCd("21392");

		int cnt = this.housingManage.searchHousing(searchForm);
		Assert.assertEquals("�������ʂ��擾�ł��Ă��鎖", 1 , cnt);

		// ������{���
		Housing housing = searchForm.getRows().get(0);
		HousingInfo housingInfo = (HousingInfo)housing.getHousingInfo().getItems().get("housingInfo");
		Assert.assertEquals("������{���̃V�X�e������CD���������擾����Ă��鎖", "SHOU00006", housingInfo.getSysHousingCd());

		// ������{���
		Building building = housing.getBuilding();
		BuildingInfo buildingInfo = (BuildingInfo) building.getBuildingInfo().getItems().get("buildingInfo");
		Assert.assertEquals("������{���̃V�X�e������CD���������擾����Ă��鎖", "SBLD00006", buildingInfo.getSysBuildingCd());

		// �����ڍ׏��
		BuildingDtlInfo buildingDtlInfo = (BuildingDtlInfo) building.getBuildingInfo().getItems().get("buildingDtlInfo");
		Assert.assertEquals("�����ڍ׏��̃V�X�e������CD���������擾����Ă��鎖", "SBLD00006", buildingDtlInfo.getSysBuildingCd());

	}

	/**
	 * ����/���i�ɂ�镨�����̌�������<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>����/���i�ɂ�錟�����ʂ��������擾����Ă��鎖</li>
	 * </ul>
	 */
	@Test
	public void SearchHousingKeyPriceTest() throws Exception {

		// ����
		HousingSearchForm searchForm = housingFormFactory.createHousingSearchForm();
		searchForm.setKeyPrefCd("07");
		searchForm.setKeyPriceLower(100000L);

		int cnt = this.housingManage.searchHousing(searchForm);
		Assert.assertEquals("�������ʂ��擾�ł��Ă��鎖", 1 , cnt);

		// ������{���
		Housing housing = searchForm.getRows().get(0);
		HousingInfo housingInfo = (HousingInfo)housing.getHousingInfo().getItems().get("housingInfo");
		Assert.assertEquals("������{���̃V�X�e������CD���������擾����Ă��鎖", "SHOU00007", housingInfo.getSysHousingCd());

		// ������{���
		Building building = housing.getBuilding();
		BuildingInfo buildingInfo = (BuildingInfo) building.getBuildingInfo().getItems().get("buildingInfo");
		Assert.assertEquals("������{���̃V�X�e������CD���������擾����Ă��鎖", "SBLD00007", buildingInfo.getSysBuildingCd());

		// �����ڍ׏��
		BuildingDtlInfo buildingDtlInfo = (BuildingDtlInfo) building.getBuildingInfo().getItems().get("buildingDtlInfo");
		Assert.assertEquals("�����ڍ׏��̃V�X�e������CD���������擾����Ă��鎖", "SBLD00007", buildingDtlInfo.getSysBuildingCd());

		// �����i�͈͊O�j
		searchForm = housingFormFactory.createHousingSearchForm();
		searchForm.setKeyPrefCd("07");
		searchForm.setKeyPriceLower(100001L);

		cnt = this.housingManage.searchHousing(searchForm);
		Assert.assertEquals("�������ʂ�0���ł��鎖", 0 , cnt);

		// ���
		searchForm = housingFormFactory.createHousingSearchForm();
		searchForm.setKeyPrefCd("07");
		searchForm.setKeyPriceUpper(100000L);

		cnt = this.housingManage.searchHousing(searchForm);
		Assert.assertEquals("�������ʂ��擾�ł��Ă��鎖", 1 , cnt);

		// ������{���
		housing = searchForm.getRows().get(0);
		housingInfo = (HousingInfo)housing.getHousingInfo().getItems().get("housingInfo");
		Assert.assertEquals("������{���̃V�X�e������CD���������擾����Ă��鎖", "SHOU00007", housingInfo.getSysHousingCd());

		// ������{���
		building = housing.getBuilding();
		buildingInfo = (BuildingInfo) building.getBuildingInfo().getItems().get("buildingInfo");
		Assert.assertEquals("������{���̃V�X�e������CD���������擾����Ă��鎖", "SBLD00007", buildingInfo.getSysBuildingCd());

		// �����ڍ׏��
		buildingDtlInfo = (BuildingDtlInfo) building.getBuildingInfo().getItems().get("buildingDtlInfo");
		Assert.assertEquals("�����ڍ׏��̃V�X�e������CD���������擾����Ă��鎖", "SBLD00007", buildingDtlInfo.getSysBuildingCd());

		// ����i�͈͊O�j
		searchForm = housingFormFactory.createHousingSearchForm();
		searchForm.setKeyPrefCd("07");
		searchForm.setKeyPriceUpper(99999L);

		cnt = this.housingManage.searchHousing(searchForm);
		Assert.assertEquals("�������ʂ�0���ł��鎖", 0 , cnt);

	}

	/**
	 * �y�n�ʐςɂ�镨�����̌�������<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�y�n�ʐςɂ�錟�����ʂ��������擾����Ă��鎖</li>
	 * </ul>
	 */
	@Test
	public void SearchHousingKeyLandAreaTest() throws Exception {

		// ����
		HousingSearchForm searchForm = housingFormFactory.createHousingSearchForm();
		searchForm.setKeyPrefCd("08");
		searchForm.setKeyLandAreaLower(new BigDecimal("50"));

		int cnt = this.housingManage.searchHousing(searchForm);
		Assert.assertEquals("�������ʂ��擾�ł��Ă��鎖", 1 , cnt);

		// ������{���
		Housing housing = searchForm.getRows().get(0);
		HousingInfo housingInfo = (HousingInfo)housing.getHousingInfo().getItems().get("housingInfo");
		Assert.assertEquals("������{���̃V�X�e������CD���������擾����Ă��鎖", "SHOU00008", housingInfo.getSysHousingCd());

		// ������{���
		Building building = housing.getBuilding();
		BuildingInfo buildingInfo = (BuildingInfo) building.getBuildingInfo().getItems().get("buildingInfo");
		Assert.assertEquals("������{���̃V�X�e������CD���������擾����Ă��鎖", "SBLD00008", buildingInfo.getSysBuildingCd());

		// �����ڍ׏��
		BuildingDtlInfo buildingDtlInfo = (BuildingDtlInfo) building.getBuildingInfo().getItems().get("buildingDtlInfo");
		Assert.assertEquals("�����ڍ׏��̃V�X�e������CD���������擾����Ă��鎖", "SBLD00008", buildingDtlInfo.getSysBuildingCd());

		// �����i�͈͊O�j
		searchForm = housingFormFactory.createHousingSearchForm();
		searchForm.setKeyPrefCd("08");
		searchForm.setKeyLandAreaLower(new BigDecimal("51"));

		cnt = this.housingManage.searchHousing(searchForm);
		Assert.assertEquals("�������ʂ�0���ł��鎖", 0 , cnt);

		// ���
		searchForm = housingFormFactory.createHousingSearchForm();
		searchForm.setKeyPrefCd("08");
		searchForm.setKeyLandAreaUpper(new BigDecimal("50"));

		cnt = this.housingManage.searchHousing(searchForm);
		Assert.assertEquals("�������ʂ��擾�ł��Ă��鎖", 1 , cnt);

		// ������{���
		housing = searchForm.getRows().get(0);
		housingInfo = (HousingInfo)housing.getHousingInfo().getItems().get("housingInfo");
		Assert.assertEquals("������{���̃V�X�e������CD���������擾����Ă��鎖", "SHOU00008", housingInfo.getSysHousingCd());

		// ������{���
		building = housing.getBuilding();
		buildingInfo = (BuildingInfo) building.getBuildingInfo().getItems().get("buildingInfo");
		Assert.assertEquals("������{���̃V�X�e������CD���������擾����Ă��鎖", "SBLD00008", buildingInfo.getSysBuildingCd());

		// �����ڍ׏��
		buildingDtlInfo = (BuildingDtlInfo) building.getBuildingInfo().getItems().get("buildingDtlInfo");
		Assert.assertEquals("�����ڍ׏��̃V�X�e������CD���������擾����Ă��鎖", "SBLD00008", buildingDtlInfo.getSysBuildingCd());

		// ����i�͈͊O�j
		searchForm = housingFormFactory.createHousingSearchForm();
		searchForm.setKeyPrefCd("08");
		searchForm.setKeyLandAreaUpper(new BigDecimal("49"));

		cnt = this.housingManage.searchHousing(searchForm);
		Assert.assertEquals("�������ʂ�0���ł��鎖", 0 , cnt);

	}

	/**
	 * ��L�ʐςɂ�镨�����̌�������<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>��L�ʐςɂ�錟�����ʂ��������擾����Ă��鎖</li>
	 * </ul>
	 */
	@Test
	public void SearchHousingKeyPersonalAreaTest() throws Exception {

		// ����
		HousingSearchForm searchForm = housingFormFactory.createHousingSearchForm();
		searchForm.setKeyPrefCd("09");
		searchForm.setKeyPersonalAreaLower(new BigDecimal("60"));

		int cnt = this.housingManage.searchHousing(searchForm);
		Assert.assertEquals("�������ʂ��擾�ł��Ă��鎖", 1 , cnt);

		// ������{���
		Housing housing = searchForm.getRows().get(0);
		HousingInfo housingInfo = (HousingInfo)housing.getHousingInfo().getItems().get("housingInfo");
		Assert.assertEquals("������{���̃V�X�e������CD���������擾����Ă��鎖", "SHOU00009", housingInfo.getSysHousingCd());

		// ������{���
		Building building = housing.getBuilding();
		BuildingInfo buildingInfo = (BuildingInfo) building.getBuildingInfo().getItems().get("buildingInfo");
		Assert.assertEquals("������{���̃V�X�e������CD���������擾����Ă��鎖", "SBLD00009", buildingInfo.getSysBuildingCd());

		// �����ڍ׏��
		BuildingDtlInfo buildingDtlInfo = (BuildingDtlInfo) building.getBuildingInfo().getItems().get("buildingDtlInfo");
		Assert.assertEquals("�����ڍ׏��̃V�X�e������CD���������擾����Ă��鎖", "SBLD00009", buildingDtlInfo.getSysBuildingCd());

		// �����i�͈͊O�j
		searchForm = housingFormFactory.createHousingSearchForm();
		searchForm.setKeyPrefCd("09");
		searchForm.setKeyPersonalAreaLower(new BigDecimal("61"));

		cnt = this.housingManage.searchHousing(searchForm);
		Assert.assertEquals("�������ʂ�0���ł��鎖", 0 , cnt);

		// ���
		searchForm = housingFormFactory.createHousingSearchForm();
		searchForm.setKeyPrefCd("09");
		searchForm.setKeyPersonalAreaUpper(new BigDecimal("60"));

		cnt = this.housingManage.searchHousing(searchForm);
		Assert.assertEquals("�������ʂ��擾�ł��Ă��鎖", 1 , cnt);

		// ������{���
		housing = searchForm.getRows().get(0);
		housingInfo = (HousingInfo)housing.getHousingInfo().getItems().get("housingInfo");
		Assert.assertEquals("������{���̃V�X�e������CD���������擾����Ă��鎖", "SHOU00009", housingInfo.getSysHousingCd());

		// ������{���
		building = housing.getBuilding();
		buildingInfo = (BuildingInfo) building.getBuildingInfo().getItems().get("buildingInfo");
		Assert.assertEquals("������{���̃V�X�e������CD���������擾����Ă��鎖", "SBLD00009", buildingInfo.getSysBuildingCd());

		// �����ڍ׏��
		buildingDtlInfo = (BuildingDtlInfo) building.getBuildingInfo().getItems().get("buildingDtlInfo");
		Assert.assertEquals("�����ڍ׏��̃V�X�e������CD���������擾����Ă��鎖", "SBLD00009", buildingDtlInfo.getSysBuildingCd());

		// ����i�͈͊O�j
		searchForm = housingFormFactory.createHousingSearchForm();
		searchForm.setKeyPrefCd("09");
		searchForm.setKeyPersonalAreaUpper(new BigDecimal("59"));

		cnt = this.housingManage.searchHousing(searchForm);
		Assert.assertEquals("�������ʂ�0���ł��鎖", 0 , cnt);

	}

	/**
	 * �������CD�ɂ�镨�����̌�������<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�������CD�ɂ�錟�����ʂ��������擾����Ă��鎖</li>
	 * </ul>
	 */
	@Test
	public void SearchHousingKeyHousingKindCdTest() throws Exception {

		HousingSearchForm searchForm = housingFormFactory.createHousingSearchForm();
		searchForm.setKeyPrefCd("10");
		searchForm.setKeyHousingKindCd("001");

		int cnt = this.housingManage.searchHousing(searchForm);
		Assert.assertEquals("�������ʂ��擾�ł��Ă��鎖", 1 , cnt);

		// ������{���
		Housing housing = searchForm.getRows().get(0);
		HousingInfo housingInfo = (HousingInfo)housing.getHousingInfo().getItems().get("housingInfo");
		Assert.assertEquals("������{���̃V�X�e������CD���������擾����Ă��鎖", "SHOU00010", housingInfo.getSysHousingCd());

		// ������{���
		Building building = housing.getBuilding();
		BuildingInfo buildingInfo = (BuildingInfo) building.getBuildingInfo().getItems().get("buildingInfo");
		Assert.assertEquals("������{���̃V�X�e������CD���������擾����Ă��鎖", "SBLD00010", buildingInfo.getSysBuildingCd());

		// �����ڍ׏��
		BuildingDtlInfo buildingDtlInfo = (BuildingDtlInfo) building.getBuildingInfo().getItems().get("buildingDtlInfo");
		Assert.assertEquals("�����ڍ׏��̃V�X�e������CD���������擾����Ă��鎖", "SBLD00010", buildingDtlInfo.getSysBuildingCd());

	}

	/**
	 * �Ԏ��CD�ɂ�镨�����̌�������<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�Ԏ��CD�ɂ�錟�����ʂ��������擾����Ă��鎖</li>
	 * </ul>
	 */
	@Test
	public void SearchHousingKeyLayoutCdTest() throws Exception {

		HousingSearchForm searchForm = housingFormFactory.createHousingSearchForm();
		searchForm.setKeyPrefCd("11");
		searchForm.setKeyLayoutCd("001");

		int cnt = this.housingManage.searchHousing(searchForm);
		Assert.assertEquals("�������ʂ��擾�ł��Ă��鎖", 1 , cnt);

		// ������{���
		Housing housing = searchForm.getRows().get(0);
		HousingInfo housingInfo = (HousingInfo)housing.getHousingInfo().getItems().get("housingInfo");
		Assert.assertEquals("������{���̃V�X�e������CD���������擾����Ă��鎖", "SHOU00011", housingInfo.getSysHousingCd());

		// ������{���
		Building building = housing.getBuilding();
		BuildingInfo buildingInfo = (BuildingInfo) building.getBuildingInfo().getItems().get("buildingInfo");
		Assert.assertEquals("������{���̃V�X�e������CD���������擾����Ă��鎖", "SBLD00011", buildingInfo.getSysBuildingCd());

		// �����ڍ׏��
		BuildingDtlInfo buildingDtlInfo = (BuildingDtlInfo) building.getBuildingInfo().getItems().get("buildingDtlInfo");
		Assert.assertEquals("�����ڍ׏��̃V�X�e������CD���������擾����Ă��鎖", "SBLD00011", buildingDtlInfo.getSysBuildingCd());

	}

	/**
	 * �����ʐςɂ�镨�����̌�������<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�����ʐςɂ�錟�����ʂ��������擾����Ă��鎖</li>
	 * </ul>
	 */
	@Test
	public void SearchHousingKeyBuildingAreaTest() throws Exception {

		// ����
		HousingSearchForm searchForm = housingFormFactory.createHousingSearchForm();
		searchForm.setKeyPrefCd("12");
		searchForm.setKeyBuildingAreaLower(new BigDecimal("50"));

		int cnt = this.housingManage.searchHousing(searchForm);
		Assert.assertEquals("�������ʂ��擾�ł��Ă��鎖", 1 , cnt);

		// ������{���
		Housing housing = searchForm.getRows().get(0);
		HousingInfo housingInfo = (HousingInfo)housing.getHousingInfo().getItems().get("housingInfo");
		Assert.assertEquals("������{���̃V�X�e������CD���������擾����Ă��鎖", "SHOU00012", housingInfo.getSysHousingCd());

		// ������{���
		Building building = housing.getBuilding();
		BuildingInfo buildingInfo = (BuildingInfo) building.getBuildingInfo().getItems().get("buildingInfo");
		Assert.assertEquals("������{���̃V�X�e������CD���������擾����Ă��鎖", "SBLD00012", buildingInfo.getSysBuildingCd());

		// �����ڍ׏��
		BuildingDtlInfo buildingDtlInfo = (BuildingDtlInfo) building.getBuildingInfo().getItems().get("buildingDtlInfo");
		Assert.assertEquals("�����ڍ׏��̃V�X�e������CD���������擾����Ă��鎖", "SBLD00012", buildingDtlInfo.getSysBuildingCd());

		// �����i�͈͊O�j
		searchForm = housingFormFactory.createHousingSearchForm();
		searchForm.setKeyPrefCd("12");
		searchForm.setKeyBuildingAreaLower(new BigDecimal("51"));

		cnt = this.housingManage.searchHousing(searchForm);
		Assert.assertEquals("�������ʂ�0���ł��鎖", 0 , cnt);

		// ���
		searchForm = housingFormFactory.createHousingSearchForm();
		searchForm.setKeyPrefCd("12");
		searchForm.setKeyBuildingAreaUpper(new BigDecimal("50"));

		cnt = this.housingManage.searchHousing(searchForm);
		Assert.assertEquals("�������ʂ��擾�ł��Ă��鎖", 1 , cnt);

		// ������{���
		housing = searchForm.getRows().get(0);
		housingInfo = (HousingInfo)housing.getHousingInfo().getItems().get("housingInfo");
		Assert.assertEquals("������{���̃V�X�e������CD���������擾����Ă��鎖", "SHOU00012", housingInfo.getSysHousingCd());

		// ������{���
		building = housing.getBuilding();
		buildingInfo = (BuildingInfo) building.getBuildingInfo().getItems().get("buildingInfo");
		Assert.assertEquals("������{���̃V�X�e������CD���������擾����Ă��鎖", "SBLD00012", buildingInfo.getSysBuildingCd());

		// �����ڍ׏��
		buildingDtlInfo = (BuildingDtlInfo) building.getBuildingInfo().getItems().get("buildingDtlInfo");
		Assert.assertEquals("�����ڍ׏��̃V�X�e������CD���������擾����Ă��鎖", "SBLD00012", buildingDtlInfo.getSysBuildingCd());

		// ����i�͈͊O�j
		searchForm = housingFormFactory.createHousingSearchForm();
		searchForm.setKeyPrefCd("12");
		searchForm.setKeyBuildingAreaUpper(new BigDecimal("49"));

		cnt = this.housingManage.searchHousing(searchForm);
		Assert.assertEquals("�������ʂ�0���ł��鎖", 0 , cnt);

	}

	/**
	 * ����������CD�ɂ�镨�����̌�������<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>����������CD�ɂ�錟�����ʂ��������擾����Ă��鎖</li>
	 * </ul>
	 */
	@Test
	public void SearchHousingKeyPartSrchCdTest() throws Exception {

		HousingSearchForm searchForm = housingFormFactory.createHousingSearchForm();
		searchForm.setKeyPrefCd("13");
		searchForm.setKeyPartSrchCd("REI");

		int cnt = this.housingManage.searchHousing(searchForm);
		Assert.assertEquals("�������ʂ��擾�ł��Ă��鎖", 1 , cnt);

		// ������{���
		Housing housing = searchForm.getRows().get(0);
		HousingInfo housingInfo = (HousingInfo)housing.getHousingInfo().getItems().get("housingInfo");
		Assert.assertEquals("������{���̃V�X�e������CD���������擾����Ă��鎖", "SHOU00013", housingInfo.getSysHousingCd());

		// ������{���
		Building building = housing.getBuilding();
		BuildingInfo buildingInfo = (BuildingInfo) building.getBuildingInfo().getItems().get("buildingInfo");
		Assert.assertEquals("������{���̃V�X�e������CD���������擾����Ă��鎖", "SBLD00013", buildingInfo.getSysBuildingCd());

		// �����ڍ׏��
		BuildingDtlInfo buildingDtlInfo = (BuildingDtlInfo) building.getBuildingInfo().getItems().get("buildingDtlInfo");
		Assert.assertEquals("�����ڍ׏��̃V�X�e������CD���������擾����Ă��鎖", "SBLD00013", buildingDtlInfo.getSysBuildingCd());

		// ���O���[�v���̕�������
		searchForm = housingFormFactory.createHousingSearchForm();
		searchForm.setKeyPartSrchCd("REI,FRE");
		searchForm.setKeyOrderType("0");

		cnt = this.housingManage.searchHousing(searchForm);
		Assert.assertEquals("�������ʂ��擾�ł��Ă��鎖", 2 , cnt);

		// ������{���
		housing = searchForm.getRows().get(0);
		housingInfo = (HousingInfo)housing.getHousingInfo().getItems().get("housingInfo");
		Assert.assertEquals("������{���̃V�X�e������CD���������擾����Ă��鎖", "SHOU00012", housingInfo.getSysHousingCd());

		housing = searchForm.getRows().get(1);
		housingInfo = (HousingInfo)housing.getHousingInfo().getItems().get("housingInfo");
		Assert.assertEquals("������{���̃V�X�e������CD���������擾����Ă��鎖", "SHOU00013", housingInfo.getSysHousingCd());

		// �قȂ�O���[�v�̕��������i�r���I�Ɍ������ʂ�0���j
		searchForm = housingFormFactory.createHousingSearchForm();
		searchForm.setKeyPartSrchCd("REI,C15");
		searchForm.setKeyOrderType("0");

		cnt = this.housingManage.searchHousing(searchForm);
		Assert.assertEquals("�������ʂ��擾�ł��Ă��鎖", 0 , cnt);

	}

	@Test
	public void SearchHousingOrderByTest() throws Exception {

		// �V�X�e�������R�[�h
		HousingSearchForm searchForm = housingFormFactory.createHousingSearchForm();
		searchForm.setKeyOrderType("0");

		this.housingManage.searchHousing(searchForm);
		
		// ������{���
		Housing housing0 = searchForm.getRows().get(0);
		HousingInfo housingInfo0 = (HousingInfo) housing0.getHousingInfo().getItems().get("housingInfo");
		Housing housing1 = searchForm.getRows().get(1);
		HousingInfo housingInfo1 = (HousingInfo) housing1.getHousingInfo().getItems().get("housingInfo");
		Assert.assertTrue("�V�X�e�������R�[�h�Ń\�[�g����Ă��鎖", housingInfo0.getSysHousingCd().compareTo(housingInfo1.getSysHousingCd()) < 0);

		// ����/���i�i�����j
		searchForm = housingFormFactory.createHousingSearchForm();
		searchForm.setKeyOrderType("1");

		this.housingManage.searchHousing(searchForm);
		housing0 = searchForm.getRows().get(searchForm.getRows().size() - 2);
		housingInfo0 = (HousingInfo) housing0.getHousingInfo().getItems().get("housingInfo");
		housing1 = searchForm.getRows().get(searchForm.getRows().size() - 1);
		housingInfo1 = (HousingInfo) housing1.getHousingInfo().getItems().get("housingInfo");
		Assert.assertTrue("����/���i�̏����Ń\�[�g����Ă��鎖", housingInfo0.getPrice().compareTo(housingInfo1.getPrice()) < 0);

		// ����/���i�i�~���j
		searchForm = housingFormFactory.createHousingSearchForm();
		searchForm.setKeyOrderType("2");

		this.housingManage.searchHousing(searchForm);
		housing0 = searchForm.getRows().get(0);
		housingInfo0 = (HousingInfo) housing0.getHousingInfo().getItems().get("housingInfo");
		housing1 = searchForm.getRows().get(1);
		housingInfo1 = (HousingInfo) housing1.getHousingInfo().getItems().get("housingInfo");
		Assert.assertTrue("����/���i�̍~���Ń\�[�g����Ă��鎖", housingInfo0.getPrice().compareTo(housingInfo1.getPrice()) > 0);

		// ��L�ʐρi�����j
		searchForm = housingFormFactory.createHousingSearchForm();
		searchForm.setKeyOrderType("3");

		this.housingManage.searchHousing(searchForm);
		housing0 = searchForm.getRows().get(searchForm.getRows().size() - 2);
		housingInfo0 = (HousingInfo) housing0.getHousingInfo().getItems().get("housingInfo");
		housing1 = searchForm.getRows().get(searchForm.getRows().size() - 1);
		housingInfo1 = (HousingInfo) housing1.getHousingInfo().getItems().get("housingInfo");
		Assert.assertTrue("��L�ʐς̏����Ń\�[�g����Ă��鎖", housingInfo0.getPersonalArea().compareTo(housingInfo1.getPersonalArea()) < 0);

		// ��L�ʐρi�~���j
		searchForm = housingFormFactory.createHousingSearchForm();
		searchForm.setKeyOrderType("4");

		this.housingManage.searchHousing(searchForm);
		housing0 = searchForm.getRows().get(0);
		housingInfo0 = (HousingInfo) housing0.getHousingInfo().getItems().get("housingInfo");
		housing1 = searchForm.getRows().get(1);
		housingInfo1 = (HousingInfo) housing1.getHousingInfo().getItems().get("housingInfo");
		Assert.assertTrue("��L�ʐς̍~���Ń\�[�g����Ă��鎖", housingInfo0.getPersonalArea().compareTo(housingInfo1.getPersonalArea()) > 0);

		// �z�N���Â���
		searchForm = housingFormFactory.createHousingSearchForm();
		searchForm.setKeyOrderType("5");

		this.housingManage.searchHousing(searchForm);
		housing0 = searchForm.getRows().get(searchForm.getRows().size() - 2);
		BuildingInfo buildingInfo0 = (BuildingInfo) housing0.getBuilding().getBuildingInfo().getItems().get("buildingInfo");
		housing1 = searchForm.getRows().get(searchForm.getRows().size() - 1);
		BuildingInfo buildingInfo1 = (BuildingInfo) housing1.getBuilding().getBuildingInfo().getItems().get("buildingInfo");
		Assert.assertTrue("�z�N���V�������Ń\�[�g����Ă��鎖", buildingInfo0.getCompDate().compareTo(buildingInfo1.getCompDate()) < 0);

		// �z�N���V������
		searchForm = housingFormFactory.createHousingSearchForm();
		searchForm.setKeyOrderType("6");

		this.housingManage.searchHousing(searchForm);
		housing0 = searchForm.getRows().get(0);
		buildingInfo0 = (BuildingInfo) housing0.getBuilding().getBuildingInfo().getItems().get("buildingInfo");
		housing1 = searchForm.getRows().get(1);
		buildingInfo1 = (BuildingInfo) housing1.getBuilding().getBuildingInfo().getItems().get("buildingInfo");
		Assert.assertTrue("�z�N���Â����Ń\�[�g����Ă��鎖", buildingInfo0.getCompDate().compareTo(buildingInfo1.getCompDate()) > 0);

	}

}
