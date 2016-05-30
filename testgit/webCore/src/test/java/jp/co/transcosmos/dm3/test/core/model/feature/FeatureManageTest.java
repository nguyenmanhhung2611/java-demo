package jp.co.transcosmos.dm3.test.core.model.feature;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import jp.co.transcosmos.dm3.core.model.FeatureManage;
import jp.co.transcosmos.dm3.core.model.feature.form.FeatureFormFactory;
import jp.co.transcosmos.dm3.core.model.feature.form.FeatureSearchForm;
import jp.co.transcosmos.dm3.core.model.housing.Housing;
import jp.co.transcosmos.dm3.core.vo.BuildingDtlInfo;
import jp.co.transcosmos.dm3.core.vo.BuildingInfo;
import jp.co.transcosmos.dm3.core.vo.BuildingStationInfo;
import jp.co.transcosmos.dm3.core.vo.FeatureGroupInfo;
import jp.co.transcosmos.dm3.core.vo.FeatureGroupPage;
import jp.co.transcosmos.dm3.core.vo.FeaturePageInfo;
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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * ���W��� model �̃e�X�g�P�[�X
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext-test.xml")
public class FeatureManageTest {

	@Autowired
	private FeatureManage featureManage;
	@Autowired
	private FeatureFormFactory featureFormFactory;

	@Autowired
	private DAO<FeatureGroupInfo> featureGroupInfoDAO;
	@Autowired
	private DAO<FeaturePageInfo> featurePageInfoDAO;
	@Autowired
	private DAO<FeatureGroupPage> featureGroupPageDAO;

	@Autowired
	private DAO<HousingInfo> housingInfoDAO;
	@Autowired
	private DAO<HousingPartInfo> housingPartInfoDAO;
	@Autowired
	private DAO<HousingStatusInfo> housingStatusInfoDAO;
	@Autowired
	private DAO<BuildingInfo> buildingInfoDAO;
	@Autowired
	private DAO<BuildingDtlInfo> buildingDtlInfoDAO;
	@Autowired
	private DAO<BuildingStationInfo> buildingStationInfoDAO;

	// �O����
	@Before
	public void init() {
		// �֘A����f�[�^�̑S���폜
		DAOCriteria criteria = new DAOCriteria();
		this.featureGroupInfoDAO.deleteByFilter(criteria);
		this.featurePageInfoDAO.deleteByFilter(criteria);
		this.featureGroupPageDAO.deleteByFilter(criteria);

		this.housingStatusInfoDAO.deleteByFilter(criteria);
		this.housingPartInfoDAO.deleteByFilter(criteria);
		this.housingInfoDAO.deleteByFilter(criteria);
		this.buildingStationInfoDAO.deleteByFilter(criteria);
		this.buildingDtlInfoDAO.deleteByFilter(criteria);
		this.buildingInfoDAO.deleteByFilter(criteria);

		initTestData();
	}

	private void initTestData() {

		FeatureGroupInfo featureGroupInfo = new FeatureGroupInfo();
		FeaturePageInfo featurePageInfo = new FeaturePageInfo();
		FeatureGroupPage featureGroupPage = new FeatureGroupPage();

		Calendar calendar = Calendar.getInstance();
		Date now = calendar.getTime();
		calendar.clear();

		// ------ �o�^�f�[�^------

		// �yFGI00001�z
		featureGroupInfo.setFeatureGroupId("FGI00001");
		featureGroupInfo.setFeatureGroupName("���W�O���[�v���O�P");
		featureGroupInfoDAO.insert(new FeatureGroupInfo[] { featureGroupInfo });

		featurePageInfo.setFeaturePageId("FPI00001");
		featurePageInfo.setFeaturePageName("���W�y�[�W���̂O�P");
		featurePageInfo.setFeatureComment("���W�R�����g�O�P");
		featurePageInfo.setQueryStrings("prefCd=13&addressCd=13101");
		featurePageInfo.setDisplayFlg("1");
		featurePageInfo.setPathName("ImagePath");
		featurePageInfo.setHwFlg("2");
		calendar.setTime(now);
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		featurePageInfo.setDisplayStartDate(calendar.getTime());
		calendar.setTime(now);
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		featurePageInfo.setDisplayEndDate(calendar.getTime());
		featurePageInfoDAO.insert(new FeaturePageInfo[] { featurePageInfo });

		featureGroupPage.setFeatureGroupId("FGI00001");
		featureGroupPage.setFeaturePageId("FPI00001");
		featureGroupPageDAO.insert(new FeatureGroupPage[] { featureGroupPage });

		// �yFGI00002�z
		featureGroupInfo.setFeatureGroupId("FGI00002");
		featureGroupInfo.setFeatureGroupName("���W�O���[�v���O�Q");
		featureGroupInfoDAO.insert(new FeatureGroupInfo[] { featureGroupInfo });

		featurePageInfo.setFeaturePageId("FPI00021");
		featurePageInfo.setFeaturePageName("���W�y�[�W���̂Q�P");
		featurePageInfo.setQueryStrings("prefCd=13&addressCd=13101");
		featurePageInfo.setDisplayFlg("1");
		featurePageInfoDAO.insert(new FeaturePageInfo[] { featurePageInfo });

		featurePageInfo.setFeaturePageId("FPI00022");
		featurePageInfo.setFeaturePageName("���W�y�[�W���̂Q�Q");
		featurePageInfo.setQueryStrings("prefCd=13&addressCd=13101");
		featurePageInfo.setDisplayFlg("0");
		featurePageInfoDAO.insert(new FeaturePageInfo[] { featurePageInfo });

		featurePageInfo.setFeaturePageId("FPI00023");
		featurePageInfo.setFeaturePageName("���W�y�[�W���̂Q�R");
		featurePageInfo.setQueryStrings("prefCd=13&addressCd=13101");
		featurePageInfo.setDisplayFlg("1");
		featurePageInfoDAO.insert(new FeaturePageInfo[] { featurePageInfo });

		featureGroupPage.setFeatureGroupId("FGI00002");
		featureGroupPage.setFeaturePageId("FPI00023");
		featureGroupPage.setSortOrder(1);
		featureGroupPageDAO.insert(new FeatureGroupPage[] { featureGroupPage });

		featureGroupPage.setFeatureGroupId("FGI00002");
		featureGroupPage.setFeaturePageId("FPI00022");
		featureGroupPage.setSortOrder(2);
		featureGroupPageDAO.insert(new FeatureGroupPage[] { featureGroupPage });

		featureGroupPage.setFeatureGroupId("FGI00002");
		featureGroupPage.setFeaturePageId("FPI00021");
		featureGroupPage.setSortOrder(3);
		featureGroupPageDAO.insert(new FeatureGroupPage[] { featureGroupPage });

		// �����������ʑΏۂƂȂ镨�����֘A
		BuildingInfo buildingInfo;
		BuildingDtlInfo buildingDtlInfo;
		HousingInfo housingInfo;
		HousingPartInfo housingPartInfo;
		HousingStatusInfo housingStatusInfo;
		BuildingStationInfo buildingStationInfo;

		// ������{���
		buildingInfo = new BuildingInfo();
		buildingInfo.setSysBuildingCd("SBLD00001");
		buildingInfo.setBuildingCd("BLD00001");
		buildingInfo.setHousingKindCd("001");
		buildingInfo.setDisplayBuildingName("�������O�P");
		buildingInfo.setPrefCd("01");
		buildingInfo.setAddressCd("01101");
		this.buildingInfoDAO.insert(new BuildingInfo[] { buildingInfo });

		// �����ڍ׏��
		buildingDtlInfo = new BuildingDtlInfo();
		buildingDtlInfo.setSysBuildingCd("SBLD00001");
		buildingDtlInfo.setBuildingArea(new BigDecimal("50"));
		this.buildingDtlInfoDAO.insert(new BuildingDtlInfo[] { buildingDtlInfo });

		// ������{���
		housingInfo = new HousingInfo();
		housingInfo.setSysHousingCd("SHOU00001");
		housingInfo.setHousingCd("HOU00001");
		housingInfo.setDisplayHousingName("�������O�P");
		housingInfo.setSysBuildingCd("SBLD00001");
		housingInfo.setPrice(100000L);
		housingInfo.setLayoutCd("003");
		housingInfo.setLandArea(new BigDecimal("50"));
		housingInfo.setPersonalArea(new BigDecimal("60"));
		housingInfo.setMoveinFlg("0");
		this.housingInfoDAO.insert(new HousingInfo[] { housingInfo });

		// �����������������
		housingPartInfo = new HousingPartInfo();
		housingPartInfo.setSysHousingCd("SHOU00001");
		housingPartInfo.setPartSrchCd("REI");
		this.housingPartInfoDAO.insert(new HousingPartInfo[] { housingPartInfo });

		// �����X�e�[�^�X���
		housingStatusInfo = new HousingStatusInfo();
		housingStatusInfo.setSysHousingCd("SHOU00001");
		housingStatusInfo.setHiddenFlg("0");
		this.housingStatusInfoDAO.insert(new HousingStatusInfo[] { housingStatusInfo });

		// �����Ŋ�w���
		buildingStationInfo = new BuildingStationInfo();
		buildingStationInfo.setSysBuildingCd("SBLD00001");
		buildingStationInfo.setDivNo(001);
		buildingStationInfo.setDefaultRouteCd("J002003");
		buildingStationInfo.setStationCd("21137");
		this.buildingStationInfoDAO.insert(new BuildingStationInfo[] { buildingStationInfo });

		// �e�X�g�p�̌����f�[�^�ɊY�����Ȃ��͂��̏��

		// ������{���
		buildingInfo = new BuildingInfo();
		buildingInfo.setSysBuildingCd("SBLD00002");
		buildingInfo.setBuildingCd("BLD00002");
		buildingInfo.setHousingKindCd("000");
		buildingInfo.setDisplayBuildingName("�O");
		buildingInfo.setPrefCd("13");
		buildingInfo.setAddressCd("13101");
		this.buildingInfoDAO.insert(new BuildingInfo[] { buildingInfo });

		// �����ڍ׏��
		buildingDtlInfo = new BuildingDtlInfo();
		buildingDtlInfo.setSysBuildingCd("SBLD00002");
		buildingDtlInfo.setBuildingArea(new BigDecimal("10"));
		this.buildingDtlInfoDAO.insert(new BuildingDtlInfo[] { buildingDtlInfo });

		// ������{���
		housingInfo = new HousingInfo();
		housingInfo.setSysHousingCd("SHOU00002");
		housingInfo.setHousingCd("HOU00002");
		housingInfo.setDisplayHousingName("�O");
		housingInfo.setSysBuildingCd("SBLD00002");
		housingInfo.setPrice(100L);
		housingInfo.setLayoutCd("001");
		housingInfo.setLandArea(new BigDecimal("10"));
		housingInfo.setPersonalArea(new BigDecimal("10"));
		housingInfo.setMoveinFlg("0");
		housingInfo.setIconCd("S05");
		this.housingInfoDAO.insert(new HousingInfo[] { housingInfo });

		// �����������������
		housingPartInfo = new HousingPartInfo();
		housingPartInfo.setSysHousingCd("SHOU00002");
		housingPartInfo.setPartSrchCd("S10");
		this.housingPartInfoDAO.insert(new HousingPartInfo[] { housingPartInfo });

		// �����X�e�[�^�X���
		housingStatusInfo = new HousingStatusInfo();
		housingStatusInfo.setSysHousingCd("SHOU00002");
		housingStatusInfo.setHiddenFlg("0");
		this.housingStatusInfoDAO.insert(new HousingStatusInfo[] { housingStatusInfo });

		// �����Ŋ�w���
		buildingStationInfo = new BuildingStationInfo();
		buildingStationInfo.setSysBuildingCd("SBLD00002");
		buildingStationInfo.setDivNo(001);
		buildingStationInfo.setDefaultRouteCd("J002083");
		buildingStationInfo.setStationCd("22895");
		this.buildingStationInfoDAO.insert(new BuildingStationInfo[] { buildingStationInfo });

		// �yFPI003XX�z

		// ���������ɗ��p����Ȃ��p�����^
		featurePageInfo.setFeaturePageId("FPI00300");
		featurePageInfo.setFeaturePageName("���W�y�[�W���̂R�O�O");
		featurePageInfo.setQueryStrings("test=HOU00001");
		featurePageInfo.setDisplayFlg("1");
		featurePageInfoDAO.insert(new FeaturePageInfo[] { featurePageInfo });

		// �����ԍ�
		featurePageInfo.setFeaturePageId("FPI00301");
		featurePageInfo.setFeaturePageName("���W�y�[�W���̂R�O�P");
		featurePageInfo.setQueryStrings("housingCd=HOU00001");
		featurePageInfo.setDisplayFlg("1");
		featurePageInfoDAO.insert(new FeaturePageInfo[] { featurePageInfo });

		// ��������
		featurePageInfo.setFeaturePageId("FPI00302");
		featurePageInfo.setFeaturePageName("���W�y�[�W���̂R�O�Q");
		featurePageInfo.setQueryStrings("displayHousingName=������");
		featurePageInfo.setDisplayFlg("1");
		featurePageInfoDAO.insert(new FeaturePageInfo[] { featurePageInfo });

		// �s���{��CD
		featurePageInfo.setFeaturePageId("FPI00303");
		featurePageInfo.setFeaturePageName("���W�y�[�W���̂R�O�R");
		featurePageInfo.setQueryStrings("prefCd=01");
		featurePageInfo.setDisplayFlg("1");
		featurePageInfoDAO.insert(new FeaturePageInfo[] { featurePageInfo });

		// �s�撬��CD
		featurePageInfo.setFeaturePageId("FPI00304");
		featurePageInfo.setFeaturePageName("���W�y�[�W���̂R�O�S");
		featurePageInfo.setQueryStrings("addressCd=01101");
		featurePageInfo.setDisplayFlg("1");
		featurePageInfoDAO.insert(new FeaturePageInfo[] { featurePageInfo });

		// �H��CD
		featurePageInfo.setFeaturePageId("FPI00305");
		featurePageInfo.setFeaturePageName("���W�y�[�W���̂R�O�T");
		featurePageInfo.setQueryStrings("routeCd=J002003");
		featurePageInfo.setDisplayFlg("1");
		featurePageInfoDAO.insert(new FeaturePageInfo[] { featurePageInfo });

		// �wCD
		featurePageInfo.setFeaturePageId("FPI00306");
		featurePageInfo.setFeaturePageName("���W�y�[�W���̂R�O�U");
		featurePageInfo.setQueryStrings("routeCd=J002003&stationCd=21137");
		featurePageInfo.setDisplayFlg("1");
		featurePageInfoDAO.insert(new FeaturePageInfo[] { featurePageInfo });

		// �y�n�ʐρE�����A���
		featurePageInfo.setFeaturePageId("FPI00307");
		featurePageInfo.setFeaturePageName("���W�y�[�W���̂R�O�V");
		featurePageInfo.setQueryStrings("landAreaLower=50&landAreaUpper=60");
		featurePageInfo.setDisplayFlg("1");
		featurePageInfoDAO.insert(new FeaturePageInfo[] { featurePageInfo });

//		// �y�n�ʐρE���
//		featurePageInfo.setFeaturePageId("FPI00308");
//		featurePageInfo.setFeaturePageName("���W�y�[�W���̂R�O�W");
//		featurePageInfo.setQueryStrings("landAreaUpper=50");
//		featurePageInfo.setDisplayFlg("1");
//		featurePageInfoDAO.insert(new FeaturePageInfo[] { featurePageInfo });

		// ��L�ʐρE�����A���
		featurePageInfo.setFeaturePageId("FPI00309");
		featurePageInfo.setFeaturePageName("���W�y�[�W���̂R�O�X");
		featurePageInfo.setQueryStrings("personalAreaLower=60&personalAreaUpper=60");
		featurePageInfo.setDisplayFlg("1");
		featurePageInfoDAO.insert(new FeaturePageInfo[] { featurePageInfo });

//		// ��L�ʐρE���
//		featurePageInfo.setFeaturePageId("FPI00310");
//		featurePageInfo.setFeaturePageName("���W�y�[�W���̂R�P�O");
//		featurePageInfo.setQueryStrings("personalAreaUpper=60");
//		featurePageInfo.setDisplayFlg("1");
//		featurePageInfoDAO.insert(new FeaturePageInfo[] { featurePageInfo });

		// �������CD
		featurePageInfo.setFeaturePageId("FPI00311");
		featurePageInfo.setFeaturePageName("���W�y�[�W���̂R�P�P");
		featurePageInfo.setQueryStrings("housingKindCd=001");
		featurePageInfo.setDisplayFlg("1");
		featurePageInfoDAO.insert(new FeaturePageInfo[] { featurePageInfo });

		// �Ԏ�CD
		featurePageInfo.setFeaturePageId("FPI00312");
		featurePageInfo.setFeaturePageName("���W�y�[�W���̂R�P�Q");
		featurePageInfo.setQueryStrings("layoutCd=003");
		featurePageInfo.setDisplayFlg("1");
		featurePageInfoDAO.insert(new FeaturePageInfo[] { featurePageInfo });

		// ��L�ʐρE�����A���
		featurePageInfo.setFeaturePageId("FPI00313");
		featurePageInfo.setFeaturePageName("���W�y�[�W���̂R�P�R");
		featurePageInfo.setQueryStrings("buildingAreaLower=50&buildingAreaUpper=60");
		featurePageInfo.setDisplayFlg("1");
		featurePageInfoDAO.insert(new FeaturePageInfo[] { featurePageInfo });

//		// ��L�ʐρE���
//		featurePageInfo.setFeaturePageId("FPI00314");
//		featurePageInfo.setFeaturePageName("���W�y�[�W���̂R�P�S");
//		featurePageInfo.setQueryStrings("buildingAreaUpper=50");
//		featurePageInfo.setDisplayFlg("1");
//		featurePageInfoDAO.insert(new FeaturePageInfo[] { featurePageInfo });

		// ����������CD
		featurePageInfo.setFeaturePageId("FPI00315");
		featurePageInfo.setFeaturePageName("���W�y�[�W���̂R�P�T");
		featurePageInfo.setQueryStrings("partSrchCd=REI");
		featurePageInfo.setDisplayFlg("1");
		featurePageInfoDAO.insert(new FeaturePageInfo[] { featurePageInfo });

		// �\�[�g����
		featurePageInfo.setFeaturePageId("FPI00316");
		featurePageInfo.setFeaturePageName("���W�y�[�W���̂R�P�U");
		featurePageInfo.setQueryStrings("orderType=1");
		featurePageInfo.setDisplayFlg("1");
		featurePageInfoDAO.insert(new FeaturePageInfo[] { featurePageInfo });

	}

	/**
	 * ���W�y�[�W���̌�������<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�p�����^���������ݒ肳��Ă��Ȃ��ꍇ�A���W���͎擾�����A����I�����鎖</li>
	 * </ul>
	 */
	@Test
	public void searchFeatureParamCheckTest() throws Exception {

		List<FeaturePageInfo> featurePageInfos;

		featurePageInfos = featureManage.searchFeature(null);

		Assert.assertEquals("�p�����^���������ݒ肳��Ă��Ȃ��ꍇ�A���W���͎擾�����ɁA����I�����鎖", 0, featurePageInfos.size());

		featurePageInfos = featureManage.searchFeature("");

		Assert.assertEquals("�p�����^���������ݒ肳��Ă��Ȃ��ꍇ�A���W���͎擾�����ɁA����I�����鎖", 0, featurePageInfos.size());

	}

	/**
	 * ���W�y�[�W���̌�������<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>���W��񂪐������擾����Ă��鎖</li>
	 *     <li>���W��񂪐������擾����Ă��鎖�i�\���t���O�j</li>
	 *     <li>���W��񂪐������擾����Ă��鎖�i�\�����j</li>
	 * </ul>
	 */
	@Test
	public void searchFeatureTest() throws Exception {

		Date now = new Date();
		List<FeaturePageInfo> featurePageInfos;
		FeaturePageInfo featurePageInfo;

		// ���W��񂪐������擾����Ă��鎖
		featurePageInfos = featureManage.searchFeature("FGI00001");

		Assert.assertEquals("���W��񂪐����������擾����Ă��鎖", 1, featurePageInfos.size());

		featurePageInfo = featurePageInfos.get(0);
		Assert.assertEquals("���W�y�[�W���̓��W�y�[�WID���������擾����Ă��鎖", "FPI00001", featurePageInfo.getFeaturePageId());
		Assert.assertEquals("���W�y�[�W���̓��W�y�[�W�����������擾����Ă��鎖", "���W�y�[�W���̂O�P", featurePageInfo.getFeaturePageName());
		Assert.assertEquals("���W�y�[�W���̃R�����g���������擾����Ă��鎖", "���W�R�����g�O�P", featurePageInfo.getFeatureComment());
		Assert.assertEquals("���W�y�[�W���̃N�G���[�����񂪐������擾����Ă��鎖", "prefCd=13&addressCd=13101", featurePageInfo.getQueryStrings());
		Assert.assertEquals("���W�y�[�W���̕\���t���O���������擾����Ă��鎖", "1", featurePageInfo.getDisplayFlg());
		Assert.assertEquals("���W�y�[�W���̉摜�p�X�����������擾����Ă��鎖", "ImagePath", featurePageInfo.getPathName());
		Assert.assertEquals("���W�y�[�W���̏c���E�����t���O���������擾����Ă��鎖", "2", featurePageInfo.getHwFlg());
		Assert.assertTrue("���W�y�[�W���̕\���J�n�����������擾����Ă��鎖", now.compareTo(featurePageInfo.getDisplayStartDate()) >= 0);
		Assert.assertTrue("���W�y�[�W���̕\���I�������������擾����Ă��鎖", now.compareTo(featurePageInfo.getDisplayEndDate()) <= 0);

		// ���W��񂪐������擾����Ă��鎖�i�\���t���O�j
		// ���W��񂪐������擾����Ă��鎖�i�\�����j
		featurePageInfos = featureManage.searchFeature("FGI00002");

		Assert.assertEquals("���W��񂪐����������擾����Ă��鎖", 2, featurePageInfos.size());
		featurePageInfo = featurePageInfos.get(0);
		Assert.assertEquals("���W�y�[�W���̓��W�y�[�WID���������擾����Ă��鎖", "FPI00023", featurePageInfo.getFeaturePageId());
		featurePageInfo = featurePageInfos.get(1);
		Assert.assertEquals("���W�y�[�W���̓��W�y�[�WID���������擾����Ă��鎖", "FPI00021", featurePageInfo.getFeaturePageId());

	}

	/**
	 * ���W�y�[�W���ɂ�镨����������<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�p�����^���������ݒ肳��Ă��Ȃ��ꍇ�A�������͎擾�����A����I�����鎖</li>
	 * </ul>
	 */
	@Test
	public void searchHousingParamCheckTest() throws Exception {

		int cnt = 1;
		FeatureSearchForm searchForm;

		// searchForm �� null
		cnt = featureManage.searchHousing(null);

		// featurePageId �� null
		Assert.assertEquals("�p�����^���������ݒ肳��Ă��Ȃ��ꍇ�A�������͎擾�����ɁA����I�����鎖", 0, cnt);

		searchForm = featureFormFactory.createFeatureSearchForm();
		searchForm.setFeaturePageId(null);
		cnt = featureManage.searchHousing(searchForm);

		Assert.assertEquals("�p�����^���������ݒ肳��Ă��Ȃ��ꍇ�A�������͎擾�����ɁA����I�����鎖", 0, cnt);

		// featurePageId �� ��
		searchForm = featureFormFactory.createFeatureSearchForm();
		searchForm.setFeaturePageId("");
		cnt = featureManage.searchHousing(searchForm);

		Assert.assertEquals("�p�����^���������ݒ肳��Ă��Ȃ��ꍇ�A�������͎擾�����ɁA����I�����鎖", 0, cnt);

	}

	/**
	 * ���W�y�[�W���ɂ�镨����������<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>������񂪐�������������鎖</li>
	 * </ul>
	 */
	@Test
	public void searchHousingTest() throws Exception {

		int cnt = 0;
		FeatureSearchForm searchForm;

		// ������񂪐�������������鎖�i���������ɗ��p����Ȃ��p�����^�j
		searchForm = featureFormFactory.createFeatureSearchForm();
		searchForm.setFeaturePageId("FPI00300");
		cnt = featureManage.searchHousing(searchForm);

		Assert.assertEquals("������񂪐������擾����鎖", 2, cnt);

		// ������񂪐�������������鎖�i�����ԍ��j
		searchForm = featureFormFactory.createFeatureSearchForm();
		searchForm.setFeaturePageId("FPI00301");
		cnt = featureManage.searchHousing(searchForm);

		Assert.assertEquals("������񂪐������擾����鎖", 1, cnt);

		// ������񂪐�������������鎖�i�������́j
		searchForm = featureFormFactory.createFeatureSearchForm();
		searchForm.setFeaturePageId("FPI00302");
		cnt = featureManage.searchHousing(searchForm);

		Assert.assertEquals("������񂪐������擾����鎖", 1, cnt);

		// ������񂪐�������������鎖�i�s���{��CD�j
		searchForm = featureFormFactory.createFeatureSearchForm();
		searchForm.setFeaturePageId("FPI00303");
		cnt = featureManage.searchHousing(searchForm);

		Assert.assertEquals("������񂪐������擾����鎖", 1, cnt);

		// ������񂪐�������������鎖�i�s�撬��CD�j
		searchForm = featureFormFactory.createFeatureSearchForm();
		searchForm.setFeaturePageId("FPI00304");
		cnt = featureManage.searchHousing(searchForm);

		Assert.assertEquals("������񂪐������擾����鎖", 1, cnt);

		// ������񂪐�������������鎖�i�H��CD�j
		searchForm = featureFormFactory.createFeatureSearchForm();
		searchForm.setFeaturePageId("FPI00305");
		cnt = featureManage.searchHousing(searchForm);

		Assert.assertEquals("������񂪐������擾����鎖", 1, cnt);

		// ������񂪐�������������鎖�i�wCD�j
		searchForm = featureFormFactory.createFeatureSearchForm();
		searchForm.setFeaturePageId("FPI00306");
		cnt = featureManage.searchHousing(searchForm);

		Assert.assertEquals("������񂪐������擾����鎖", 1, cnt);

		// ������񂪐�������������鎖�i�y�n�ʐρE�����A����j
		searchForm = featureFormFactory.createFeatureSearchForm();
		searchForm.setFeaturePageId("FPI00307");
		cnt = featureManage.searchHousing(searchForm);

		Assert.assertEquals("������񂪐������擾����鎖", 1, cnt);

		// ������񂪐�������������鎖�i��L�ʐρE�����A����j
		searchForm = featureFormFactory.createFeatureSearchForm();
		searchForm.setFeaturePageId("FPI00309");
		cnt = featureManage.searchHousing(searchForm);

		Assert.assertEquals("������񂪐������擾����鎖", 1, cnt);

		// ������񂪐�������������鎖�i�������CD�j
		searchForm = featureFormFactory.createFeatureSearchForm();
		searchForm.setFeaturePageId("FPI00311");
		cnt = featureManage.searchHousing(searchForm);

		Assert.assertEquals("������񂪐������擾����鎖", 1, cnt);

		// ������񂪐�������������鎖�i�Ԏ�CD�j
		searchForm = featureFormFactory.createFeatureSearchForm();
		searchForm.setFeaturePageId("FPI00312");
		cnt = featureManage.searchHousing(searchForm);

		Assert.assertEquals("������񂪐������擾����鎖", 1, cnt);

		// ������񂪐�������������鎖�i��L�ʐρE�����A����j
		searchForm = featureFormFactory.createFeatureSearchForm();
		searchForm.setFeaturePageId("FPI00313");
		cnt = featureManage.searchHousing(searchForm);

		Assert.assertEquals("������񂪐������擾����鎖", 1, cnt);

		// ������񂪐�������������鎖�i����������CD�j
		searchForm = featureFormFactory.createFeatureSearchForm();
		searchForm.setFeaturePageId("FPI00315");
		cnt = featureManage.searchHousing(searchForm);

		Assert.assertEquals("������񂪐������擾����鎖", 1, cnt);

		// ������񂪐�������������鎖�i�\�[�g�����j
		searchForm = featureFormFactory.createFeatureSearchForm();
		searchForm.setFeaturePageId("FPI00316");
		cnt = featureManage.searchHousing(searchForm);

		Assert.assertEquals("������񂪐������擾����鎖", 2, cnt);
		HousingInfo housingInfo;
		housingInfo = (HousingInfo) searchForm.getRows().get(0).getHousingInfo().getItems().get("housingInfo");
		Assert.assertEquals("������񂪐������擾����鎖", "SHOU00002", housingInfo.getSysHousingCd());
		housingInfo = (HousingInfo) searchForm.getRows().get(1).getHousingInfo().getItems().get("housingInfo");
		Assert.assertEquals("������񂪐������擾����鎖", "SHOU00001", housingInfo.getSysHousingCd());

	}

	/**
	 * ���W�y�[�W���ɂ�镨����������<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�y�[�W���O�֘A�̃v���p�e�B�l�ɍ��v����������񂪐������擾����鎖</li>
	 * </ul>
	 */
	@Test
	public void searchHousingPagingTest() throws Exception {

		int cnt = 0;
		int actualRow =0;
		FeatureSearchForm searchForm;

		HousingInfo housingInfo;

		// 1�y�[�W��
		// FeatureSearchForm �ɐݒ肳�ꂽ�y�[�W���O�֘A�̃v���p�e�B�l�ɍ��v����������񂪎擾�ł��Ă��邱��
		searchForm = featureFormFactory.createFeatureSearchForm();
		searchForm.setRowsPerPage(1);
		searchForm.setSelectedPage(1);
		searchForm.setFeaturePageId("FPI00316");
		cnt = featureManage.searchHousing(searchForm);

		Assert.assertEquals("������񂪐������擾����鎖", 2, cnt);
		// ���Ԃ̃T�C�Y���擾����
		actualRow = 0;
		for (Housing housing : searchForm.getRows()) {
			actualRow++;
		}
		int pageRowIndex = searchForm.getRowsPerPage() * searchForm.getSelectedPage() - searchForm.getRowsPerPage();
		Assert.assertEquals("�y�[�W���O�֘A�̃v���p�e�B�l�ɍ��v����������񂪐������擾����鎖", 1, actualRow);
		housingInfo = (HousingInfo) searchForm.getRows().get(pageRowIndex + 0).getHousingInfo().getItems().get("housingInfo");
		Assert.assertEquals("�y�[�W���O�֘A�̃v���p�e�B�l�ɍ��v����������񂪐������擾����鎖", "SHOU00002", housingInfo.getSysHousingCd());

		// 2�y�[�W��
		// FeatureSearchForm �ɐݒ肳�ꂽ�y�[�W���O�֘A�̃v���p�e�B�l�ɍ��v����������񂪎擾�ł��Ă��邱��
		searchForm = featureFormFactory.createFeatureSearchForm();
		searchForm.setRowsPerPage(1);
		searchForm.setSelectedPage(2);
		searchForm.setFeaturePageId("FPI00316");
		cnt = featureManage.searchHousing(searchForm);

		Assert.assertEquals("������񂪐������擾����鎖", 2, cnt);
		// ���Ԃ̃T�C�Y���擾����
		actualRow = 0;
		for (Housing housing : searchForm.getRows()) {
			actualRow++;
		}
		pageRowIndex = searchForm.getRowsPerPage() * searchForm.getSelectedPage() - searchForm.getRowsPerPage();
		Assert.assertEquals("�y�[�W���O�֘A�̃v���p�e�B�l�ɍ��v����������񂪐������擾����鎖", 1, actualRow);
		housingInfo = (HousingInfo) searchForm.getRows().get(pageRowIndex + 0).getHousingInfo().getItems().get("housingInfo");
		Assert.assertEquals("�y�[�W���O�֘A�̃v���p�e�B�l�ɍ��v����������񂪐������擾����鎖", "SHOU00001", housingInfo.getSysHousingCd());

	}

}
