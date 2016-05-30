package jp.co.transcosmos.dm3.test.core.model.housing;

import java.util.Map;
import java.util.Map.Entry;

import jp.co.transcosmos.dm3.core.model.HousingManage;
import jp.co.transcosmos.dm3.core.model.building.Building;
import jp.co.transcosmos.dm3.core.model.housing.Housing;
import jp.co.transcosmos.dm3.core.vo.BuildingDtlInfo;
import jp.co.transcosmos.dm3.core.vo.BuildingInfo;
import jp.co.transcosmos.dm3.core.vo.BuildingLandmark;
import jp.co.transcosmos.dm3.core.vo.BuildingStationInfo;
import jp.co.transcosmos.dm3.core.vo.EquipMst;
import jp.co.transcosmos.dm3.core.vo.HousingDtlInfo;
import jp.co.transcosmos.dm3.core.vo.HousingEquipInfo;
import jp.co.transcosmos.dm3.core.vo.HousingExtInfo;
import jp.co.transcosmos.dm3.core.vo.HousingImageInfo;
import jp.co.transcosmos.dm3.core.vo.HousingInfo;
import jp.co.transcosmos.dm3.core.vo.HousingPartInfo;
import jp.co.transcosmos.dm3.core.vo.HousingStatusInfo;
import jp.co.transcosmos.dm3.core.vo.StationMst;
import jp.co.transcosmos.dm3.dao.DAO;
import jp.co.transcosmos.dm3.dao.DAOCriteria;
import jp.co.transcosmos.dm3.dao.JoinResult;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


/**
 * ������� model ��L�[�ɂ�镨�����擾�����̃e�X�g
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationContext-test.xml")
public class HousingManageSearchHousingPkTest {

	@Autowired
	private DAO<BuildingInfo> buildingInfoDAO;
	@Autowired
	private DAO<BuildingDtlInfo> buildingDtlInfoDAO;
	@Autowired
	private DAO<BuildingStationInfo> buildingStationInfoDAO;
	@Autowired
	private DAO<BuildingLandmark> buildingLandmarkDAO;

	@Autowired
	private DAO<HousingInfo> housingInfoDAO;
	@Autowired
	private DAO<HousingDtlInfo> housingDtlInfoDAO;
	@Autowired
	private DAO<HousingStatusInfo> housingStatusInfoDAO;
	@Autowired
	private DAO<HousingImageInfo> housingImageInfoDAO;
	@Autowired
	private DAO<HousingEquipInfo> housingEquipInfoDAO;
	@Autowired
	private DAO<HousingExtInfo> housingExtInfoDAO;
	@Autowired
	private DAO<HousingPartInfo> housingPartInfoDAO;

	@Qualifier("housingManager")
	@Autowired
	private HousingManage housingManage;

	
	
	// �O����
	@Before
	public void init(){
		// �������S���폜
		DAOCriteria criteria = new DAOCriteria();
		this.housingInfoDAO.deleteByFilter(criteria);
		this.housingStatusInfoDAO.deleteByFilter(criteria);
		this.buildingInfoDAO.deleteByFilter(criteria);

		initTestData();
	}



	/**
	 * �������̎擾
	 * �E������{���A������{���̂�
	 * �E���� full = true �i����J�����擾�j
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�Œ���̍\���f�[�^�i������{���ƁA������{���̂݁j�ł��������l���擾�ł��鎖</li>
	 * </ul>
	 */
	@Test
	public void getPkMinDataTest() throws Exception {

		// �ŏ��\���̃e�X�g�Ȃ̂ŁA������{���A������{���ȊO���폜����B
		DAOCriteria criteria = new DAOCriteria();
		this.buildingDtlInfoDAO.deleteByFilter(criteria);
		this.buildingStationInfoDAO.deleteByFilter(criteria);
		this.buildingLandmarkDAO.deleteByFilter(criteria);

		this.housingDtlInfoDAO.deleteByFilter(criteria);
		this.housingStatusInfoDAO.deleteByFilter(criteria);
		this.housingImageInfoDAO.deleteByFilter(criteria);
		this.housingEquipInfoDAO.deleteByFilter(criteria);
		this.housingExtInfoDAO.deleteByFilter(criteria);


		// �e�X�g���\�b�h�����s
		Housing housing = this.housingManage.searchHousingPk("HOU00001", true);
		Assert.assertNotNull("�Ώۃf�[�^���擾�ł��Ă��鎖", housing);

		// �擾�f�[�^�̊m�F
		Building building = housing.getBuilding();
		BuildingInfo buildingInfo = (BuildingInfo) building.getBuildingInfo().getItems().get("buildingInfo");
		Assert.assertEquals("������{���̃V�X�e������CD����������", buildingInfo.getSysBuildingCd(), "BULD00001");

		BuildingDtlInfo buildingDtlInfo = (BuildingDtlInfo)building.getBuildingInfo().getItems().get("buildingDtlInfo");
		Assert.assertNull("�����ڍ׏��̃V�X�e������CD �� null �ł��鎖", buildingDtlInfo.getSysBuildingCd());

		Assert.assertEquals("���������h�}�[�N���̌����� 0 �ł��鎖", 0, building.getBuildingLandmarkList().size());
		Assert.assertEquals("�����Ŋ��w���̌����� 0 �ł��鎖", 0, building.getBuildingStationInfoList().size());


		HousingInfo housingInfo = (HousingInfo) housing.getHousingInfo().getItems().get("housingInfo");
		Assert.assertEquals("������{���̃V�X�e������CD����������", housingInfo.getSysHousingCd(), "HOU00001");

		HousingDtlInfo housingDtlInfo = (HousingDtlInfo)housing.getHousingInfo().getItems().get("housingDtlInfo");
		Assert.assertNull("�����ڍ׏��̃V�X�e������CD �� null �ł��鎖", housingDtlInfo.getSysHousingCd());

		HousingStatusInfo housingStatuslInfo = (HousingStatusInfo) housing.getHousingInfo().getItems().get("housingStatusInfo");
		Assert.assertNull("�����X�e�[�^�X���̃V�X�e������CD �� null ���鎖", housingStatuslInfo.getSysHousingCd());

		Assert.assertEquals("�����摜���̌����� 0 �ł��鎖", 0, housing.getHousingImageInfos().size());
		Assert.assertEquals("�����ݔ����̌����� 0 �ł��鎖", 0, housing.getHousingEquipInfos().size());
		Assert.assertEquals("�����g���������̌����� 0 �ł��鎖", 0, housing.getHousingExtInfos().size());

	}


	
	/**
	 * �������̎擾<br/>
	 * �E�S�Ă̈ˑ��e�[�u��
	 * �E���� full = true �i����J�����擾�j
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�w�肵���V�X�e������CD �ɊY�����錚����{��񂪎擾�ł��Ă��鎖</li>
	 *     <li>�w�肵���V�X�e������CD �ɊY�����錚���ڍ׏�񂪎擾�ł��Ă��鎖</li>
	 *     <li>�w�肵���V�X�e������CD �ɊY�����镨����{��񂪎擾�ł��Ă��鎖</li>
	 *     <li>�w�肵���V�X�e������CD �ɊY�����镨���ڍ׏�񂪎擾�ł��Ă��鎖</li>
	 *     <li>�w�肵���V�X�e������CD �ɊY�����镨���X�e�[�^�X��񂪎擾�ł��Ă��鎖</li>
	 * </ul>
	 */
	@Test
	public void confMainDataFullTest() throws Exception {

		// �e�X�g���\�b�h�����s
		Housing housing = this.housingManage.searchHousingPk("HOU00001", true);
		Assert.assertNotNull("�Ώۃf�[�^���擾�ł��Ă��鎖", housing);

		
		// �擾�f�[�^�̊m�F
		Building building = housing.getBuilding();
		BuildingInfo buildingInfo = (BuildingInfo) building.getBuildingInfo().getItems().get("buildingInfo");
		Assert.assertEquals("������{���̃V�X�e������CD����������", buildingInfo.getSysBuildingCd(), "BULD00001");

		BuildingDtlInfo buildingDtlInfo = (BuildingDtlInfo)building.getBuildingInfo().getItems().get("buildingDtlInfo");
		Assert.assertEquals("�����ڍ׏��̃V�X�e������CD����������", buildingDtlInfo.getSysBuildingCd(), "BULD00001");

		Assert.assertEquals("���������h�}�[�N���̌�������������", 2, building.getBuildingLandmarkList().size());
		for (BuildingLandmark landmark : building.getBuildingLandmarkList()){
		
			Assert.assertEquals("���������h�}�[�N���̃V�X�e������CD ����������", "BULD00001", landmark.getSysBuildingCd());
			
			// �擾�f�[�^�����������������h�}�[�N���Ń`�F�b�N
			if (landmark.getLandmarkName().equals("LANDNM0001_1")) continue;
			if (landmark.getLandmarkName().equals("LANDNM0001_2")) continue;
			Assert.fail("�����h�}�[�N�����������Ȃ��@" + landmark.getLandmarkName());
		}

		Assert.assertEquals("�����Ŋ��w���̌�������������", 2, building.getBuildingStationInfoList().size());
		for (JoinResult result : building.getBuildingStationInfoList()){
			BuildingStationInfo buildingStationInfo = (BuildingStationInfo) result.getItems().get("buildingStationInfo");
			StationMst stationMst = (StationMst) result.getItems().get("stationMst");

			Assert.assertEquals("�����Ŋ��w���̃V�X�e������CD ����������", "BULD00001", buildingStationInfo.getSysBuildingCd());

			// �擾�f�[�^�������������wCD�A�w���Ń`�F�b�N
			if (buildingStationInfo.getStationCd().equals("29726") && stationMst.getStationName().equals("��Ղ̈�{��")) continue;
			if (buildingStationInfo.getStationCd().equals("29695") && stationMst.getStationName().equals("�א��̗�")) continue;

			Assert.fail("�Ŋ��w��񂪐������Ȃ��@" + buildingStationInfo.getStationCd() + "," + stationMst.getStationName());
		}


		HousingInfo housingInfo = (HousingInfo) housing.getHousingInfo().getItems().get("housingInfo");
		Assert.assertEquals("������{���̃V�X�e������CD����������", housingInfo.getSysHousingCd(), "HOU00001");

		HousingDtlInfo housingDtlInfo = (HousingDtlInfo) housing.getHousingInfo().getItems().get("housingDtlInfo");
		Assert.assertEquals("�����ڍ׏��̃V�X�e������CD����������", housingDtlInfo.getSysHousingCd(), "HOU00001");

		HousingStatusInfo housingStatusInfo = (HousingStatusInfo) housing.getHousingInfo().getItems().get("housingStatusInfo");
		Assert.assertEquals("�����X�e�[�^�X���̃V�X�e������CD����������", housingStatusInfo.getSysHousingCd(), "HOU00001");

		Assert.assertEquals("�����摜���̌�������������", 2, housing.getHousingImageInfos().size());
		for (HousingImageInfo img : housing.getHousingImageInfos()){
			
			Assert.assertEquals("�����摜���̃V�X�e������CD ����������", "HOU00001", img.getSysHousingCd());
			
			// �擾�f�[�^�������������摜�p�X�Ń`�F�b�N
			if (img.getPathName().equals("PATH00001_01")) continue;
			if (img.getPathName().equals("PATH00001_02")) continue;
			
			Assert.fail("�����摜��񂪐������Ȃ��@" + img.getPathName());
		}

		Assert.assertEquals("�����ݔ����̌�������������", 2, housing.getHousingEquipInfos().size());
		EquipMst equip = housing.getHousingEquipInfos().get("000001");
		Assert.assertEquals("�擾���������ݔ��̐ݔ�������������", "�Q�S�g���C�V�X�e��", equip.getEquipName());
		equip = housing.getHousingEquipInfos().get("000002");
		Assert.assertEquals("�擾���������ݔ��̐ݔ�������������", "���֌��i�f�W�^�����b�N�j", equip.getEquipName());

		// �����g�������̊m�F
		Map<String, Map<String, String>> categoryMaps = housing.getHousingExtInfos();
		for (Entry<String, Map<String, String>> e : categoryMaps.entrySet()){
			String categoryName = e.getKey();
			Map<String, String> keyMap = e.getValue();

			if (categoryName.equals("CATE0001")) {
				if (keyMap.size() != 2) {
					Assert.fail("�擾�����J�e�S���ɊY������ key �̐����������Ȃ� " + categoryName + "," + keyMap.size());
				}

				Assert.assertEquals("key �ɊY������l����������", "VALUE00001", keyMap.get("KEY0001"));
				Assert.assertEquals("key �ɊY������l����������", "VALUE00002", keyMap.get("KEY0002"));

			} else if (categoryName.equals("CATE0002")) {	
				if (keyMap.size() != 1) {
					Assert.fail("�擾�����J�e�S���ɊY������ key �̐����������Ȃ� " + categoryName + "," + keyMap.size());
				}
				Assert.assertEquals("key �ɊY������l����������", "VALUE00003", keyMap.get("KEY0001"));

			} else {
				Assert.fail("�擾�����J�e�S�������������Ȃ� " + categoryName);
			}
		}
	}



	/**
	 * ���� full �ɂ�����J�����̎擾����e�X�g<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>full = false �ŕ����X�e�[�^�X��񂪑��݂��Ȃ��ꍇ�A�擾����鎖</li>
	 *     <li>full = true �ŕ����X�e�[�^�X��񂪑��݂��Ȃ��ꍇ�A�擾����鎖</li>
	 *     <li>full = false �ŕ����X�e�[�^�X��񂪑��݂��A����J�t���O�� 0 �̏ꍇ�A�擾����鎖</li>
	 *     <li>full = true �ŕ����X�e�[�^�X��񂪑��݂��A����J�t���O�� 0 �̏ꍇ�A�擾����鎖</li>
	 *     <li>full = false �ŕ����X�e�[�^�X��񂪑��݂��A����J�t���O�� 1 �̏ꍇ�A�擾����Ȃ���</li>
	 *     <li>full = true �ŕ����X�e�[�^�X��񂪑��݂��A����J�t���O�� 1 �̏ꍇ�A�擾����鎖</li>
	 * </ul>
	 */
	@Test
	public void getPkStatusTest() throws Exception {
		

		// full = false �ŃX�e�[�^�X��񂪖����ꍇ�A�擾����鎖
		Housing housing = this.housingManage.searchHousingPk("HOU00004", false);
		HousingInfo housingInfo = (HousingInfo) housing.getHousingInfo().getItems().get("housingInfo");
		String sysHousingCd = housingInfo.getSysHousingCd();
		Assert.assertEquals("full = false �ŕ����X�e�[�^�X��񂪖����ꍇ�A�擾�o���Ă��鎖�B", sysHousingCd, "HOU00004");

		
		// full = true �ŃX�e�[�^�X��񂪖����ꍇ�A�擾����鎖
		housing = this.housingManage.searchHousingPk("HOU00004", true);
		housingInfo = (HousingInfo) housing.getHousingInfo().getItems().get("housingInfo");
		sysHousingCd = housingInfo.getSysHousingCd();
		Assert.assertEquals("full = true �ŕ����X�e�[�^�X��񂪖����ꍇ�A�擾�o���Ă��鎖�B", sysHousingCd, "HOU00004");


		// full = false �ŃX�e�[�^�X��񂪌��J�̏ꍇ�A�擾����鎖
		housing = this.housingManage.searchHousingPk("HOU00001", false);
		housingInfo = (HousingInfo) housing.getHousingInfo().getItems().get("housingInfo");
		sysHousingCd = housingInfo.getSysHousingCd();
		Assert.assertEquals("�����X�e�[�^�X��񂪌��J�̏ꍇ�A�擾�o���Ă��鎖�B", sysHousingCd, "HOU00001");

		
		// full = true �ŃX�e�[�^�X��񂪌��J�̏ꍇ�A�擾����鎖
		housing = this.housingManage.searchHousingPk("HOU00001", true);
		housingInfo = (HousingInfo) housing.getHousingInfo().getItems().get("housingInfo");
		sysHousingCd = housingInfo.getSysHousingCd();
		Assert.assertEquals("�����X�e�[�^�X��񂪌��J�̏ꍇ�A�擾�o���Ă��鎖�B", sysHousingCd, "HOU00001");


		// full = false �X�e�[�^�X��񂪔���J�̏ꍇ�A�擾����Ȃ���
		housing = this.housingManage.searchHousingPk("HOU00005", false);
		Assert.assertNull("�����X�e�[�^�X��񂪔���J�̏ꍇ�A�擾�ΏۊO�ł��鎖�B", housing);

		
		// full = true �ŃX�e�[�^�X��񂪔���J�̏ꍇ�A�擾����鎖
		housing = this.housingManage.searchHousingPk("HOU00005", true);
		housingInfo = (HousingInfo) housing.getHousingInfo().getItems().get("housingInfo");
		sysHousingCd = housingInfo.getSysHousingCd();
		Assert.assertEquals("�����X�e�[�^�X��񂪌��J�̏ꍇ�A�擾�o���Ă��鎖�B", sysHousingCd, "HOU00005");

	}

	


	
	/**
	 * �Y������f�[�^�����݂��Ȃ��ꍇ
	 * �E���� full = true �i����J�����擾�j
	 * �E���݂��Ȃ��V�X�e�������ԍ����w��
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>null �𕜋A���鎖�B</li>
	 * </ul>
	 */
	@Test
	public void getPkNotHousingCdTest() throws Exception {

		// �e�X�g���\�b�h�����s
		Housing housing = this.housingManage.searchHousingPk("HOU10001", true);
		Assert.assertNull("�߂�l�� null �ł��鎖", housing);

	}



	// �e�X�g�f�[�^�쐬
	private void initTestData(){

		BuildingInfo buildingInfo;
		BuildingDtlInfo buildingDtlInfo;
		BuildingStationInfo[] buildingStationInfo = new BuildingStationInfo[2];
		BuildingLandmark[] buildingLandmark = new BuildingLandmark[2];
		
		HousingInfo housingInfo;
		HousingDtlInfo housingDtlInfo;
		HousingStatusInfo housingStatusInfo;
		HousingImageInfo[] housingImageInfo = new HousingImageInfo[2];
		HousingEquipInfo[] housingEquipInfo = new HousingEquipInfo[2];
		HousingExtInfo[] housingExtInfo = new HousingExtInfo[3];

		
		// ------ �擾�Ώۃf�[�^ ------
		// ������{���
		buildingInfo = new BuildingInfo();
		buildingInfo.setSysBuildingCd("BULD00001");
		buildingInfo.setDisplayBuildingName("�������P");
		buildingInfo.setPrefCd("13");

		this.buildingInfoDAO.insert(new BuildingInfo[] {buildingInfo});

		// �����ڍ׏��
		buildingDtlInfo = new BuildingDtlInfo();
		buildingDtlInfo.setSysBuildingCd("BULD00001");

		this.buildingDtlInfoDAO.insert(new BuildingDtlInfo[] {buildingDtlInfo});

		// �����Ŋ��w���@�i2���j
		buildingStationInfo[0] = new BuildingStationInfo();
		buildingStationInfo[0].setSysBuildingCd("BULD00001");
		buildingStationInfo[0].setDivNo(1);
		buildingStationInfo[0].setStationCd("29726");	// ��Ղ̈�{��

		buildingStationInfo[1] = new BuildingStationInfo();
		buildingStationInfo[1].setSysBuildingCd("BULD00001");
		buildingStationInfo[1].setDivNo(2);
		buildingStationInfo[1].setStationCd("29695");	// �א��̗�

		this.buildingStationInfoDAO.insert(buildingStationInfo);;

		// ���������h�}�[�N���@�i�Q���j
		buildingLandmark[0] = new BuildingLandmark();
		buildingLandmark[0].setSysBuildingCd("BULD00001");
		buildingLandmark[0].setDivNo(1);
		buildingLandmark[0].setWayFromLandmark("1");
		buildingLandmark[0].setLandmarkName("LANDNM0001_1");

		buildingLandmark[1] = new BuildingLandmark();
		buildingLandmark[1].setSysBuildingCd("BULD00001");
		buildingLandmark[1].setDivNo(2);
		buildingLandmark[1].setWayFromLandmark("1");
		buildingLandmark[1].setLandmarkName("LANDNM0001_2");

		this.buildingLandmarkDAO.insert(buildingLandmark);

		//�@������{���
		housingInfo = new HousingInfo();
		housingInfo.setSysHousingCd("HOU00001");
		housingInfo.setSysBuildingCd("BULD00001");
		housingInfo.setDisplayHousingName("�������P");

		this.housingInfoDAO.insert(new HousingInfo[]{housingInfo});

		// �����ڍ׏��
		housingDtlInfo = new HousingDtlInfo();
		housingDtlInfo.setSysHousingCd("HOU00001");

		this.housingDtlInfoDAO.insert(new HousingDtlInfo[]{housingDtlInfo});

		// �����X�e�[�^�X���
		housingStatusInfo = new HousingStatusInfo();
		housingStatusInfo.setSysHousingCd("HOU00001");
		housingStatusInfo.setHiddenFlg("0");

		this.housingStatusInfoDAO.insert(new HousingStatusInfo[]{housingStatusInfo});

		// �����摜���@�i�Q���j
		housingImageInfo[0] = new HousingImageInfo();
		housingImageInfo[0].setSysHousingCd("HOU00001");
		housingImageInfo[0].setImageType("00");
		housingImageInfo[0].setDivNo(1);
		housingImageInfo[0].setPathName("PATH00001_01");
		housingImageInfo[0].setFileName("FILE00001_01");

		housingImageInfo[1] = new HousingImageInfo();
		housingImageInfo[1].setSysHousingCd("HOU00001");
		housingImageInfo[1].setImageType("00");
		housingImageInfo[1].setDivNo(2);
		housingImageInfo[1].setPathName("PATH00001_02");
		housingImageInfo[1].setFileName("FILE00001_02");

		this.housingImageInfoDAO.insert(housingImageInfo);

		// �����ݔ����
		housingEquipInfo[0] = new HousingEquipInfo();
		housingEquipInfo[0].setSysHousingCd("HOU00001");
		housingEquipInfo[0].setEquipCd("000001");

		housingEquipInfo[1] = new HousingEquipInfo();
		housingEquipInfo[1].setSysHousingCd("HOU00001");
		housingEquipInfo[1].setEquipCd("000002");
				
		this.housingEquipInfoDAO.insert(housingEquipInfo);;

		// �����g���������
		housingExtInfo[0] = new HousingExtInfo();
		housingExtInfo[0].setSysHousingCd("HOU00001");
		housingExtInfo[0].setCategory("CATE0001");
		housingExtInfo[0].setKeyName("KEY0001");
		housingExtInfo[0].setDataValue("VALUE00001");

		housingExtInfo[1] = new HousingExtInfo();
		housingExtInfo[1].setSysHousingCd("HOU00001");
		housingExtInfo[1].setCategory("CATE0001");
		housingExtInfo[1].setKeyName("KEY0002");
		housingExtInfo[1].setDataValue("VALUE00002");

		housingExtInfo[2] = new HousingExtInfo();
		housingExtInfo[2].setSysHousingCd("HOU00001");
		housingExtInfo[2].setCategory("CATE0002");
		housingExtInfo[2].setKeyName("KEY0001");
		housingExtInfo[2].setDataValue("VALUE00003");

		this.housingExtInfoDAO.insert(housingExtInfo);



		// ------ �擾�ΏۊO�f�[�^�i���ꌚ���ŕʕ����j------
		//�@������{���
		housingInfo = new HousingInfo();
		housingInfo.setSysHousingCd("HOU00002");
		housingInfo.setSysBuildingCd("BULD00001");
		housingInfo.setDisplayHousingName("�������Q");
		this.housingInfoDAO.insert(new HousingInfo[]{housingInfo});

		// �����ڍ׏��
		housingDtlInfo = new HousingDtlInfo();
		housingDtlInfo.setSysHousingCd("HOU00002");

		this.housingDtlInfoDAO.insert(new HousingDtlInfo[]{housingDtlInfo});

		// �����X�e�[�^�X���
		housingStatusInfo = new HousingStatusInfo();
		housingStatusInfo.setSysHousingCd("HOU00002");
		housingStatusInfo.setHiddenFlg("0");

		this.housingStatusInfoDAO.insert(new HousingStatusInfo[]{housingStatusInfo});

		// �����摜���@�i�Q���j
		housingImageInfo[0] = new HousingImageInfo();
		housingImageInfo[0].setSysHousingCd("HOU00002");
		housingImageInfo[0].setImageType("00");
		housingImageInfo[0].setDivNo(1);
		housingImageInfo[0].setPathName("PATH00002_01");
		housingImageInfo[0].setFileName("FILE00002_01");

		housingImageInfo[1] = new HousingImageInfo();
		housingImageInfo[1].setSysHousingCd("HOU00002");
		housingImageInfo[1].setImageType("00");
		housingImageInfo[1].setDivNo(2);
		housingImageInfo[1].setPathName("PATH00002_02");
		housingImageInfo[1].setFileName("FILE00002_02");

		this.housingImageInfoDAO.insert(housingImageInfo);

		// �����ݔ����
		housingEquipInfo[0] = new HousingEquipInfo();
		housingEquipInfo[0].setSysHousingCd("HOU00002");
		housingEquipInfo[0].setEquipCd("000003");

		housingEquipInfo[1] = new HousingEquipInfo();
		housingEquipInfo[1].setSysHousingCd("HOU00002");
		housingEquipInfo[1].setEquipCd("000004");
				
		this.housingEquipInfoDAO.insert(housingEquipInfo);;

		// �����g���������
		housingExtInfo[0] = new HousingExtInfo();
		housingExtInfo[0].setSysHousingCd("HOU00002");
		housingExtInfo[0].setCategory("CATE0001");
		housingExtInfo[0].setKeyName("KEY0001");
		housingExtInfo[0].setDataValue("VALUE00021");

		housingExtInfo[1] = new HousingExtInfo();
		housingExtInfo[1].setSysHousingCd("HOU00002");
		housingExtInfo[1].setCategory("CATE0001");
		housingExtInfo[1].setKeyName("KEY0002");
		housingExtInfo[1].setDataValue("VALUE00022");

		housingExtInfo[2] = new HousingExtInfo();
		housingExtInfo[2].setSysHousingCd("HOU00002");
		housingExtInfo[2].setCategory("CATE0002");
		housingExtInfo[2].setKeyName("KEY0001");
		housingExtInfo[2].setDataValue("VALUE00023");

		this.housingExtInfoDAO.insert(housingExtInfo);


		
		// ------ �擾�ΏۊO�f�[�^�i����CD�Ⴂ�j------
		// ������{���
		buildingInfo = new BuildingInfo();
		buildingInfo.setSysBuildingCd("BULD00003");
		buildingInfo.setDisplayBuildingName("�������R");
		buildingInfo.setPrefCd("14");

		this.buildingInfoDAO.insert(new BuildingInfo[] {buildingInfo});

		// �����ڍ׏��
		buildingDtlInfo = new BuildingDtlInfo();
		buildingDtlInfo.setSysBuildingCd("BULD00003");
		
		this.buildingDtlInfoDAO.insert(new BuildingDtlInfo[] {buildingDtlInfo});

		// �����Ŋ��w���@�i2���j
		buildingStationInfo[0] = new BuildingStationInfo();
		buildingStationInfo[0].setSysBuildingCd("BULD00003");
		buildingStationInfo[0].setDivNo(1);
		buildingStationInfo[0].setStationCd("29696");	// ����

		buildingStationInfo[1] = new BuildingStationInfo();
		buildingStationInfo[1].setSysBuildingCd("BULD00003");
		buildingStationInfo[1].setDivNo(2);
		buildingStationInfo[1].setStationCd("29705");	// �x�C�T�C�h�A���[�i

		this.buildingStationInfoDAO.insert(buildingStationInfo);;

		// ���������h�}�[�N���@�i�Q���j
		buildingLandmark[0] = new BuildingLandmark();
		buildingLandmark[0].setSysBuildingCd("BULD00003");
		buildingLandmark[0].setDivNo(1);
		buildingLandmark[0].setWayFromLandmark("1");
		buildingLandmark[0].setLandmarkName("LANDNM0003_1");

		buildingLandmark[1] = new BuildingLandmark();
		buildingLandmark[1].setSysBuildingCd("BULD00003");
		buildingLandmark[1].setDivNo(2);
		buildingLandmark[1].setWayFromLandmark("1");
		buildingLandmark[1].setLandmarkName("LANDNM0003_2");

		this.buildingLandmarkDAO.insert(buildingLandmark);

		//�@������{���
		housingInfo = new HousingInfo();
		housingInfo.setSysHousingCd("HOU00003");
		housingInfo.setSysBuildingCd("BULD00003");
		housingInfo.setDisplayHousingName("�������R");
		this.housingInfoDAO.insert(new HousingInfo[]{housingInfo});

		// �����ڍ׏��
		housingDtlInfo = new HousingDtlInfo();
		housingDtlInfo.setSysHousingCd("HOU00003");

		this.housingDtlInfoDAO.insert(new HousingDtlInfo[]{housingDtlInfo});

		// �����X�e�[�^�X���
		housingStatusInfo = new HousingStatusInfo();
		housingStatusInfo.setSysHousingCd("HOU00003");
		housingStatusInfo.setHiddenFlg("0");

		this.housingStatusInfoDAO.insert(new HousingStatusInfo[]{housingStatusInfo});

		// �����摜���@�i�Q���j
		housingImageInfo[0] = new HousingImageInfo();
		housingImageInfo[0].setSysHousingCd("HOU00003");
		housingImageInfo[0].setImageType("00");
		housingImageInfo[0].setDivNo(1);
		housingImageInfo[0].setPathName("PATH00003_01");
		housingImageInfo[0].setFileName("FILE00003_01");

		housingImageInfo[1] = new HousingImageInfo();
		housingImageInfo[1].setSysHousingCd("HOU00003");
		housingImageInfo[1].setImageType("00");
		housingImageInfo[1].setDivNo(2);
		housingImageInfo[1].setPathName("PATH00003_02");
		housingImageInfo[1].setFileName("FILE00003_02");

		this.housingImageInfoDAO.insert(housingImageInfo);

		// �����ݔ����
		housingEquipInfo[0] = new HousingEquipInfo();
		housingEquipInfo[0].setSysHousingCd("HOU00003");
		housingEquipInfo[0].setEquipCd("000005");

		housingEquipInfo[1] = new HousingEquipInfo();
		housingEquipInfo[1].setSysHousingCd("HOU00003");
		housingEquipInfo[1].setEquipCd("000006");
				
		this.housingEquipInfoDAO.insert(housingEquipInfo);;

		// �����g���������
		housingExtInfo[0] = new HousingExtInfo();
		housingExtInfo[0].setSysHousingCd("HOU00003");
		housingExtInfo[0].setCategory("CATE0001");
		housingExtInfo[0].setKeyName("KEY0001");
		housingExtInfo[0].setDataValue("VALUE00031");

		housingExtInfo[1] = new HousingExtInfo();
		housingExtInfo[1].setSysHousingCd("HOU00003");
		housingExtInfo[1].setCategory("CATE0001");
		housingExtInfo[1].setKeyName("KEY0002");
		housingExtInfo[1].setDataValue("VALUE00032");

		housingExtInfo[2] = new HousingExtInfo();
		housingExtInfo[2].setSysHousingCd("HOU00003");
		housingExtInfo[2].setCategory("CATE0002");
		housingExtInfo[2].setKeyName("KEY0001");
		housingExtInfo[2].setDataValue("VALUE00033");

		this.housingExtInfoDAO.insert(housingExtInfo);

		
		
		// ------ �擾�Ώۃf�[�^�i�X�e�[�^�X���Ȃ��ɂ����J�j ------
		//�@������{���
		housingInfo = new HousingInfo();
		housingInfo.setSysHousingCd("HOU00004");
		housingInfo.setSysBuildingCd("BULD00001");
		housingInfo.setDisplayHousingName("�������S");

		this.housingInfoDAO.insert(new HousingInfo[]{housingInfo});

		// �����摜���@�i�Q���j
		housingImageInfo[0] = new HousingImageInfo();
		housingImageInfo[0].setSysHousingCd("HOU00004");
		housingImageInfo[0].setImageType("00");
		housingImageInfo[0].setDivNo(1);
		housingImageInfo[0].setPathName("PATH00004_01");
		housingImageInfo[0].setFileName("FILE00004_01");

		housingImageInfo[1] = new HousingImageInfo();
		housingImageInfo[1].setSysHousingCd("HOU00004");
		housingImageInfo[1].setImageType("00");
		housingImageInfo[1].setDivNo(2);
		housingImageInfo[1].setPathName("PATH00004_02");
		housingImageInfo[1].setFileName("FILE00004_02");

		this.housingImageInfoDAO.insert(housingImageInfo);

		// �����ݔ����
		housingEquipInfo[0] = new HousingEquipInfo();
		housingEquipInfo[0].setSysHousingCd("HOU00004");
		housingEquipInfo[0].setEquipCd("000007");

		housingEquipInfo[1] = new HousingEquipInfo();
		housingEquipInfo[1].setSysHousingCd("HOU00004");
		housingEquipInfo[1].setEquipCd("000008");
				
		this.housingEquipInfoDAO.insert(housingEquipInfo);;
		
		// �����g���������
		housingExtInfo[0] = new HousingExtInfo();
		housingExtInfo[0].setSysHousingCd("HOU00004");
		housingExtInfo[0].setCategory("CATE0001");
		housingExtInfo[0].setKeyName("KEY0001");
		housingExtInfo[0].setDataValue("VALUE00041");

		housingExtInfo[1] = new HousingExtInfo();
		housingExtInfo[1].setSysHousingCd("HOU00004");
		housingExtInfo[1].setCategory("CATE0001");
		housingExtInfo[1].setKeyName("KEY0002");
		housingExtInfo[1].setDataValue("VALUE00042");

		housingExtInfo[2] = new HousingExtInfo();
		housingExtInfo[2].setSysHousingCd("HOU00004");
		housingExtInfo[2].setCategory("CATE0002");
		housingExtInfo[2].setKeyName("KEY0001");
		housingExtInfo[2].setDataValue("VALUE00043");

		this.housingExtInfoDAO.insert(housingExtInfo);

		
		
		// ------ �擾�Ώۃf�[�^�i����J�j ------
		//�@������{���
		housingInfo = new HousingInfo();
		housingInfo.setSysHousingCd("HOU00005");
		housingInfo.setSysBuildingCd("BULD00001");
		housingInfo.setDisplayHousingName("�������T");

		this.housingInfoDAO.insert(new HousingInfo[]{housingInfo});

		// �����X�e�[�^�X���
		housingStatusInfo = new HousingStatusInfo();
		housingStatusInfo.setSysHousingCd("HOU00005");
		housingStatusInfo.setHiddenFlg("1");

		this.housingStatusInfoDAO.insert(new HousingStatusInfo[]{housingStatusInfo});

		// �����摜���@�i�Q���j
		housingImageInfo[0] = new HousingImageInfo();
		housingImageInfo[0].setSysHousingCd("HOU00005");
		housingImageInfo[0].setImageType("00");
		housingImageInfo[0].setDivNo(1);
		housingImageInfo[0].setPathName("PATH00005_01");
		housingImageInfo[0].setFileName("FILE00005_01");

		housingImageInfo[1] = new HousingImageInfo();
		housingImageInfo[1].setSysHousingCd("HOU00005");
		housingImageInfo[1].setImageType("00");
		housingImageInfo[1].setDivNo(2);
		housingImageInfo[1].setPathName("PATH00005_02");
		housingImageInfo[1].setFileName("FILE00005_02");

		this.housingImageInfoDAO.insert(housingImageInfo);

		// �����ݔ����
		housingEquipInfo[0] = new HousingEquipInfo();
		housingEquipInfo[0].setSysHousingCd("HOU00005");
		housingEquipInfo[0].setEquipCd("000001");

		housingEquipInfo[1] = new HousingEquipInfo();
		housingEquipInfo[1].setSysHousingCd("HOU00005");
		housingEquipInfo[1].setEquipCd("000002");
				
		this.housingEquipInfoDAO.insert(housingEquipInfo);;

		// �����g���������
		housingExtInfo[0] = new HousingExtInfo();
		housingExtInfo[0].setSysHousingCd("HOU00005");
		housingExtInfo[0].setCategory("CATE0001");
		housingExtInfo[0].setKeyName("KEY0001");
		housingExtInfo[0].setDataValue("VALUE00051");

		housingExtInfo[1] = new HousingExtInfo();
		housingExtInfo[1].setSysHousingCd("HOU00005");
		housingExtInfo[1].setCategory("CATE0001");
		housingExtInfo[1].setKeyName("KEY0002");
		housingExtInfo[1].setDataValue("VALUE00052");

		housingExtInfo[2] = new HousingExtInfo();
		housingExtInfo[2].setSysHousingCd("HOU00005");
		housingExtInfo[2].setCategory("CATE0002");
		housingExtInfo[2].setKeyName("KEY0001");
		housingExtInfo[2].setDataValue("VALUE00053");

		this.housingExtInfoDAO.insert(housingExtInfo);

	}

}
