package jp.co.transcosmos.dm3.test.core.model.favorite;

import java.util.Date;
import java.util.List;

import jp.co.transcosmos.dm3.core.model.FavoriteManage;
import jp.co.transcosmos.dm3.core.model.favorite.form.FavoriteFormFactory;
import jp.co.transcosmos.dm3.core.model.favorite.form.FavoriteSearchForm;
import jp.co.transcosmos.dm3.core.vo.BuildingInfo;
import jp.co.transcosmos.dm3.core.vo.FavoriteInfo;
import jp.co.transcosmos.dm3.core.vo.HousingInfo;
import jp.co.transcosmos.dm3.core.vo.UserInfo;
import jp.co.transcosmos.dm3.dao.DAO;
import jp.co.transcosmos.dm3.dao.DAOCriteria;
import jp.co.transcosmos.dm3.dao.JoinResult;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * ���C�ɓ����� model �̃e�X�g�P�[�X
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationContext-test.xml")

public class FavoriteManageTest {

	@Autowired
	private DAO<FavoriteInfo> favoriteInfoDAO;

	@Autowired
	private DAO<UserInfo> userInfoDAO;

	@Autowired
	private DAO<BuildingInfo> buildingInfoDAO;

	@Autowired
	private DAO<HousingInfo> housingInfoDAO;

	@Autowired
	private FavoriteManage favoriteManage;

	@Autowired
	private FavoriteFormFactory favoriteFormFactory;

	// �O����
	@Before
	public void init() {
		// �֘A����f�[�^�̑S���폜
		DAOCriteria criteria = new DAOCriteria();
		this.favoriteInfoDAO.deleteByFilter(criteria);
		this.userInfoDAO.deleteByFilter(criteria);
		this.buildingInfoDAO.deleteByFilter(criteria);
		this.housingInfoDAO.deleteByFilter(criteria);

		initTestData();
	}

	// �e�X�g�f�[�^�쐬
	private void initTestData() {

		UserInfo userInfo;
		BuildingInfo buildingInfo;
		HousingInfo housingInfo;

		// ------ �o�^�f�[�^------

		// ���[�UID���
		userInfo = new UserInfo();
		userInfo.setUserId("UID00001");
		userInfoDAO.insert(new UserInfo[] { userInfo });

		userInfo = new UserInfo();
		userInfo.setUserId("UID00002");
		userInfoDAO.insert(new UserInfo[] { userInfo });

		userInfo = new UserInfo();
		userInfo.setUserId("UID00003");
		userInfoDAO.insert(new UserInfo[] { userInfo });

		// ������{���
		buildingInfo = new BuildingInfo();
		buildingInfo.setSysBuildingCd("BULD00001");
		buildingInfo.setDisplayBuildingName("�������P");
		buildingInfo.setPrefCd("13");
		this.buildingInfoDAO.insert(new BuildingInfo[] { buildingInfo });

		buildingInfo = new BuildingInfo();
		buildingInfo.setSysBuildingCd("BULD00002");
		buildingInfo.setDisplayBuildingName("�������Q");
		buildingInfo.setPrefCd("13");
		this.buildingInfoDAO.insert(new BuildingInfo[] { buildingInfo });

		buildingInfo = new BuildingInfo();
		buildingInfo.setSysBuildingCd("BULD00003");
		buildingInfo.setDisplayBuildingName("�������R");
		buildingInfo.setPrefCd("13");
		this.buildingInfoDAO.insert(new BuildingInfo[] { buildingInfo });

		// ������{���
		housingInfo = new HousingInfo();
		housingInfo.setSysHousingCd("HOU00001");
		housingInfo.setSysBuildingCd("BULD00001");
		housingInfo.setDisplayHousingName("�������P");
		this.housingInfoDAO.insert(new HousingInfo[] { housingInfo });

		housingInfo = new HousingInfo();
		housingInfo.setSysHousingCd("HOU00002");
		housingInfo.setSysBuildingCd("BULD00002");
		housingInfo.setDisplayHousingName("�������Q");
		this.housingInfoDAO.insert(new HousingInfo[] { housingInfo });

		housingInfo = new HousingInfo();
		housingInfo.setSysHousingCd("HOU00003");
		housingInfo.setSysBuildingCd("BULD00003");
		housingInfo.setDisplayHousingName("�������R");
		this.housingInfoDAO.insert(new HousingInfo[] { housingInfo });

	}

	/**
	 * ���C�ɓ�����̓o�^����<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�p�����^���������ݒ肳��Ă��Ȃ��ꍇ�A���C�ɓ�����͓o�^�����A����I�����鎖</li>
	 *     <li>���݂��Ȃ����[�UID�ł͓o�^���s��ꂸ�A����I�����鎖</li>
	 *     <li>���݂��Ȃ��V�X�e������ID�ł͓o�^���s��ꂸ�A����I�����鎖</li>
	 * </ul>
	 */
	@Test
	public void addFavoriteParamCheckTest() throws Exception {

		DAOCriteria criteria = null;
		List<FavoriteInfo> favoriteInfos = null;

		// �e�X�g�R�[�h���s�i���[�UID����j
		this.favoriteManage.addFavorite("", "HOU00001");

		criteria = new DAOCriteria();
		criteria.addWhereClause("userId", "");
		criteria.addWhereClause("sysHousingCd", "");
		favoriteInfos = this.favoriteInfoDAO.selectByFilter(criteria);
		Assert.assertEquals("�p�����^���������ݒ肳��Ă��Ȃ��ꍇ�A���C�ɓ�����͓o�^�����A����I�����鎖", 0, favoriteInfos.size());

		// �e�X�g�R�[�h���s�i���[�UID��null�j
		this.favoriteManage.addFavorite(null, "HOU00001");

		criteria = new DAOCriteria();
		criteria.addWhereClause("userId", null);
		criteria.addWhereClause("sysHousingCd", "");
		favoriteInfos = this.favoriteInfoDAO.selectByFilter(criteria);
		Assert.assertEquals("�p�����^���������ݒ肳��Ă��Ȃ��ꍇ�A���C�ɓ�����͓o�^�����A����I�����鎖", 0, favoriteInfos.size());

		// �e�X�g�R�[�h���s�i�V�X�e������CD����j
		this.favoriteManage.addFavorite("UID00001", "");

		criteria = new DAOCriteria();
		criteria.addWhereClause("userId", "UID00001");
		criteria.addWhereClause("sysHousingCd", "");
		favoriteInfos = this.favoriteInfoDAO.selectByFilter(criteria);
		Assert.assertEquals("�p�����^���������ݒ肳��Ă��Ȃ��ꍇ�A���C�ɓ�����͓o�^�����A����I�����鎖", 0, favoriteInfos.size());

		// �e�X�g�R�[�h���s�i�V�X�e������CD��null�j
		this.favoriteManage.addFavorite("UID00001", null);

		criteria = new DAOCriteria();
		criteria.addWhereClause("userId", "UID00001");
		criteria.addWhereClause("sysHousingCd", null);
		favoriteInfos = this.favoriteInfoDAO.selectByFilter(criteria);
		Assert.assertEquals("�p�����^���������ݒ肳��Ă��Ȃ��ꍇ�A���C�ɓ�����͓o�^�����A����I�����鎖", 0, favoriteInfos.size());

		// �e�X�g�R�[�h���s�i���݂��Ȃ����[�UID�j
		this.favoriteManage.addFavorite("UID00000", "HOU00001");

		// �e�X�g���ʊm�F
		criteria = new DAOCriteria();
		criteria.addWhereClause("userId", "UID00000");
		criteria.addWhereClause("sysHousingCd", "HOU00001");
		favoriteInfos = this.favoriteInfoDAO.selectByFilter(criteria);
		Assert.assertEquals("���݂��Ȃ����[�UID�œo�^����Ȃ����B", 0, favoriteInfos.size());

		// �e�X�g�R�[�h���s�i���݂��Ȃ��V�X�e������CD�j
		this.favoriteManage.addFavorite("UID00001", "HOU00000");

		// �e�X�g���ʊm�F
		criteria.addWhereClause("userId", "UID00001");
		criteria.addWhereClause("sysHousingCd", "HOU00000");
		favoriteInfos = this.favoriteInfoDAO.selectByFilter(criteria);
		Assert.assertEquals("���݂��Ȃ��V�X�e������CD�œo�^����Ȃ����B", 0, favoriteInfos.size());

	}

	/**
	 * ���C�ɓ�����̓o�^����<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>���C�ɓ����񂪐������o�^����鎖</li>
	 *     <li>���C�ɓ����񂪐������X�V����鎖</li>
	 * </ul>
	 */
	@Test
	public void addFavoriteTest() throws Exception {

		DAOCriteria criteria = null;
		List<FavoriteInfo> favoriteInfos = null;

		// �e�X�g�P�[�X���{�O�m�F
		criteria = new DAOCriteria();
		criteria.addWhereClause("userId", "UID00001");
		favoriteInfos = this.favoriteInfoDAO.selectByFilter(criteria);
		Assert.assertEquals("�y�e�X�g�P�[�X���{�O�m�F�z���C�ɓ���̓o�^������0���ł��鎖�B", 0, favoriteInfos.size());

		// �e�X�g�R�[�h���s�i���C�ɓ�����̐V�K�o�^�j
		this.favoriteManage.addFavorite("UID00001", "HOU00001");

		// �e�X�g���ʊm�F
		favoriteInfos = this.favoriteInfoDAO.selectByFilter(criteria);
		Assert.assertEquals("���C�ɓ����񂪐������o�^����Ă��鎖�B", 1, favoriteInfos.size());
		Assert.assertEquals("���C�ɓ�����̃��[�UID���������o�^����Ă��鎖�B", "UID00001", favoriteInfos.get(0).getUserId());
		Assert.assertEquals("���C�ɓ�����̃V�X�e������CD���������o�^����Ă��鎖�B", "HOU00001", favoriteInfos.get(0).getSysHousingCd());
		Assert.assertEquals("���C�ɓ�����̕\���p���������������o�^����Ă��鎖�B", "�������P", favoriteInfos.get(0).getDisplayHousingName());

		// �X�V�e�X�g�p�Ɏ����擾
		Date insDate1 = favoriteInfos.get(0).getInsDate();
		Date updData1 = favoriteInfos.get(0).getUpdDate();

		// �X�V�e�X�g�p�ɕ��������X�V
		HousingInfo housingInfo = this.housingInfoDAO.selectByPK("HOU00001");
		housingInfo.setDisplayHousingName("�����P�|�P");
		this.housingInfoDAO.update(new HousingInfo[] { housingInfo });

		// �������e�X�g�ɂčX�V�����������ɂȂ��Ă��܂�Ȃ��悤�Ɉꎞ��~
		Thread.sleep(1000);

		// �e�X�g�R�[�h���s�i���C�ɓ�����̊����f�[�^�X�V�j
		this.favoriteManage.addFavorite("UID00001", "HOU00001");

		// �e�X�g���ʊm�F
		favoriteInfos = this.favoriteInfoDAO.selectByFilter(criteria);
		Assert.assertEquals("���C�ɓ����񂪐������o�^����Ă��鎖�B", 1, favoriteInfos.size());
		Assert.assertEquals("���C�ɓ�����̃��[�UID���������o�^����Ă��鎖�B", "UID00001", favoriteInfos.get(0).getUserId());
		Assert.assertEquals("���C�ɓ�����̃V�X�e������CD���������o�^����Ă��鎖�B", "HOU00001", favoriteInfos.get(0).getSysHousingCd());
		Assert.assertEquals("���C�ɓ�����̕\���p���������������o�^����Ă��鎖�B", "�����P�|�P", favoriteInfos.get(0).getDisplayHousingName());

		Date insDate2 = favoriteInfos.get(0).getInsDate();
		Date updData2 = favoriteInfos.get(0).getUpdDate();
		Assert.assertTrue("���R�[�h���X�V����Ă��鎖�B", insDate2.compareTo(insDate1) == 0);
		Assert.assertTrue("���R�[�h���X�V����Ă��鎖�B", updData2.compareTo(updData1) > 0);

	}

	/**
	 * ���C�ɓ�����̍폜����<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�p�����^���������ݒ肳��Ă��Ȃ��ꍇ�A���C�ɓ�����͍폜�����A����I�����鎖</li>
	 *     <li>���݂��Ȃ����[�UID�ł͍폜���s��ꂸ�A����I�����鎖</li>
	 *     <li>���݂��Ȃ��V�X�e������ID�ł͍폜���s��ꂸ�A����I�����鎖</li>
	 * </ul>
	 */
	@Test
	public void delFavoriteParamCheckTest() throws Exception {

		DAOCriteria criteria = null;
		List<FavoriteInfo> favoriteInfos = null;
		FavoriteInfo favoriteInfo = null;

		// �e�X�g�p�̂��C�ɓ�����o�^
		favoriteInfo = new FavoriteInfo();
		favoriteInfo.setUserId("UID00001");
		favoriteInfo.setSysHousingCd("HOU00001");
		favoriteInfo.setDisplayHousingName("�������P");
		favoriteInfo.setInsUserId("UID00001");
		favoriteInfo.setUpdUserId("UID00001");
		Date date = new Date();
		favoriteInfo.setInsDate(date);
		favoriteInfo.setUpdDate(date);
		this.favoriteInfoDAO.insert(new FavoriteInfo[] { favoriteInfo} );

		// �e�X�g�R�[�h���s�i���[�UID����j
		this.favoriteManage.delFavorite("", "HOU00001");

		criteria = new DAOCriteria();
		criteria.addWhereClause("userId", "UID00001");
		criteria.addWhereClause("sysHousingCd", "HOU00001");
		favoriteInfos = this.favoriteInfoDAO.selectByFilter(criteria);
		Assert.assertEquals("�p�����^���������ݒ肳��Ă��Ȃ��ꍇ�A���C�ɓ�����͍폜�����A����I�����鎖", 1, favoriteInfos.size());

		// �e�X�g�R�[�h���s�i���[�UID��null�j
		this.favoriteManage.delFavorite(null, "HOU00001");

		criteria = new DAOCriteria();
		criteria.addWhereClause("userId", "UID00001");
		criteria.addWhereClause("sysHousingCd", "HOU00001");
		favoriteInfos = this.favoriteInfoDAO.selectByFilter(criteria);
		Assert.assertEquals("�p�����^���������ݒ肳��Ă��Ȃ��ꍇ�A���C�ɓ�����͍폜�����A����I�����鎖", 1, favoriteInfos.size());

		// �e�X�g�R�[�h���s�i�V�X�e������CD����j
		this.favoriteManage.delFavorite("UID00001", "");

		criteria = new DAOCriteria();
		criteria.addWhereClause("userId", "UID00001");
		criteria.addWhereClause("sysHousingCd", "HOU00001");
		favoriteInfos = this.favoriteInfoDAO.selectByFilter(criteria);
		Assert.assertEquals("�p�����^���������ݒ肳��Ă��Ȃ��ꍇ�A���C�ɓ�����͍폜�����A����I�����鎖", 1, favoriteInfos.size());

		// �e�X�g�R�[�h���s�i�V�X�e������CD��null�j
		this.favoriteManage.delFavorite("UID00001", null);

		criteria = new DAOCriteria();
		criteria.addWhereClause("userId", "UID00001");
		criteria.addWhereClause("sysHousingCd", "HOU00001");
		favoriteInfos = this.favoriteInfoDAO.selectByFilter(criteria);
		Assert.assertEquals("�p�����^���������ݒ肳��Ă��Ȃ��ꍇ�A���C�ɓ�����͍폜�����A����I�����鎖", 1, favoriteInfos.size());

		// �e�X�g�R�[�h���s�i���݂��Ȃ����[�UID�j
		this.favoriteManage.delFavorite("UID00000", "HOU00001");

		// �e�X�g���ʊm�F
		criteria = new DAOCriteria();
		criteria.addWhereClause("userId", "UID00001");
		criteria.addWhereClause("sysHousingCd", "HOU00001");
		favoriteInfos = this.favoriteInfoDAO.selectByFilter(criteria);
		Assert.assertEquals("���݂��Ȃ����[�UID�ō폜����Ȃ����B", 1, favoriteInfos.size());

		// �e�X�g�R�[�h���s�i���݂��Ȃ��V�X�e������CD�j
		this.favoriteManage.delFavorite("UID00001", "HOU00000");

		// �e�X�g���ʊm�F
		criteria.addWhereClause("userId", "UID00001");
		criteria.addWhereClause("sysHousingCd", "HOU00001");
		favoriteInfos = this.favoriteInfoDAO.selectByFilter(criteria);
		Assert.assertEquals("���݂��Ȃ��V�X�e������CD�ō폜����Ȃ����B", 1, favoriteInfos.size());

	}

	/**
	 * ���C�ɓ�����̍폜����<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>���C�ɓ����񂪐������폜����鎖</li>
	 * </ul>
	 */
	@Test
	public void delFavoriteTest() throws Exception {

		DAOCriteria criteria = null;
		List<FavoriteInfo> favoriteInfos = null;
		FavoriteInfo favoriteInfo = null;

		// �e�X�g�p�̂��C�ɓ�����o�^
		favoriteInfo = new FavoriteInfo();
		favoriteInfo.setUserId("UID00001");
		favoriteInfo.setSysHousingCd("HOU00001");
		favoriteInfo.setDisplayHousingName("�������P");
		favoriteInfo.setInsUserId("UID00001");
		favoriteInfo.setUpdUserId("UID00001");
		Date date = new Date();
		favoriteInfo.setInsDate(date);
		favoriteInfo.setUpdDate(date);
		this.favoriteInfoDAO.insert(new FavoriteInfo[] { favoriteInfo} );

		// �e�X�g�P�[�X���{�O�m�F
		criteria = new DAOCriteria();
		criteria.addWhereClause("userId", "UID00001");
		criteria.addWhereClause("sysHousingCd", "HOU00001");
		favoriteInfos = this.favoriteInfoDAO.selectByFilter(criteria);
		Assert.assertEquals("�y�e�X�g�P�[�X���{�O�m�F�z���C�ɓ���̓o�^������1���ł��鎖�B", 1, favoriteInfos.size());

		// �e�X�g�R�[�h���s
		this.favoriteManage.delFavorite("UID00001", "HOU00001");

		// �e�X�g���ʊm�F
		favoriteInfos = this.favoriteInfoDAO.selectByFilter(criteria);
		Assert.assertEquals("���C�ɓ����񂪐������폜����Ă��鎖�B", 0, favoriteInfos.size());

	}

	/**
	 * ���C�ɓ�����̌����擾����<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�p�����^���������ݒ肳��Ă��Ȃ��ꍇ������I�����鎖</li>
	 *     <li>���[�UID�����݂��Ȃ��ꍇ������I�����鎖</li>
	 * </ul>
	 */
	@Test
	public void getFavoriteCntParamCheckTest() throws Exception {

		int cnt = 0;

		// �e�X�g�R�[�h���s�i���[�UID����j
		cnt = this.favoriteManage.getFavoriteCnt("");

		// �e�X�g���ʊm�F
		Assert.assertEquals("�p�����^���������ݒ肳��Ă��Ȃ��ꍇ������I�����鎖�B", 0, cnt);

		// �e�X�g�R�[�h���s�i���[�UID��Null�j
		cnt = this.favoriteManage.getFavoriteCnt(null);

		// �e�X�g���ʊm�F
		Assert.assertEquals("�p�����^���������ݒ肳��Ă��Ȃ��ꍇ������I�����鎖�B", 0, cnt);

		// �e�X�g�R�[�h���s�i���݂��Ȃ����[�UID�j
		cnt = this.favoriteManage.getFavoriteCnt("UID00000");

		// �e�X�g���ʊm�F
		Assert.assertEquals("���[�UID�����݂��Ȃ��ꍇ������I�����鎖�B", 0, cnt);

	}

	/**
	 * ���C�ɓ�����̌����擾����<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>���C�ɓ�����̌������������擾�ł��鎖</li>
	 * </ul>
	 */
	@Test
	public void getFavoriteCntTest() throws Exception {

		DAOCriteria criteria = null;
		List<FavoriteInfo> favoriteInfos = null;
		FavoriteInfo favoriteInfo = null;
		int cnt = 0;

		// �e�X�g�p�̂��C�ɓ�����o�^
		favoriteInfo = new FavoriteInfo();
		favoriteInfo.setUserId("UID00001");
		favoriteInfo.setSysHousingCd("HOU00001");
		favoriteInfo.setDisplayHousingName("�������P");
		favoriteInfo.setInsUserId("UID00001");
		favoriteInfo.setUpdUserId("UID00001");
		Date date = new Date();
		favoriteInfo.setInsDate(date);
		favoriteInfo.setUpdDate(date);
		this.favoriteInfoDAO.insert(new FavoriteInfo[] { favoriteInfo });

		// �e�X�g�P�[�X���{�O�m�F
		criteria = new DAOCriteria();
		criteria.addWhereClause("userId", "UID00001");
		criteria.addWhereClause("sysHousingCd", "HOU00001");
		favoriteInfos = this.favoriteInfoDAO.selectByFilter(criteria);
		Assert.assertEquals("�y�e�X�g�P�[�X���{�O�m�F�z���C�ɓ���̓o�^������1���ł��鎖�B", 1, favoriteInfos.size());

		// �e�X�g�R�[�h���s�i���C�ɓ�����L��j
		cnt = this.favoriteManage.getFavoriteCnt("UID00001");

		// �e�X�g���ʊm�F
		Assert.assertEquals("���C�ɓ�����̌������������擾�ł��鎖�B", 1, cnt);

		// �e�X�g�R�[�h���s�i���C�ɓ����񖳂��j
		cnt = this.favoriteManage.getFavoriteCnt("UID00002");

		// �e�X�g���ʊm�F
		Assert.assertEquals("���C�ɓ�����̌������������擾�ł��鎖�B", 0, cnt);

	}

	/**
	 * ���C�ɓ�����̌�������<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�p�����^���������ݒ肳��Ă��Ȃ��ꍇ������I�����鎖</li>
	 *     <li>���[�UID�����݂��Ȃ��ꍇ������I�����鎖</li>
	 * </ul>
	 */
	@Test
	public void searchFavoriteParamCheckTest() throws Exception {

		FavoriteSearchForm searchForm = null;
		int cnt = 0;

		// �e�X�g�R�[�h���s�i���[�UID����j
		searchForm = this.favoriteFormFactory.createFavoriteSearchForm();
		cnt = this.favoriteManage.searchFavorite("", searchForm);
		Assert.assertEquals("�p�����^���������ݒ肳��Ă��Ȃ��ꍇ������I�����鎖�B", 0, cnt);

		// �e�X�g�R�[�h���s�i���[�UID��null�j
		searchForm = this.favoriteFormFactory.createFavoriteSearchForm();
		cnt = this.favoriteManage.searchFavorite(null, searchForm);
		Assert.assertEquals("�p�����^���������ݒ肳��Ă��Ȃ��ꍇ������I�����鎖�B", 0, cnt);

		// �e�X�g�R�[�h���s�iFavoriteSearchForm��null�j
		cnt = this.favoriteManage.searchFavorite("UID00001", null);
		Assert.assertEquals("�p�����^���������ݒ肳��Ă��Ȃ��ꍇ������I�����鎖�B", 0, cnt);

		// �e�X�g�R�[�h���s�i���[�UID�����݂��Ȃ��j
		searchForm = this.favoriteFormFactory.createFavoriteSearchForm();
		cnt = this.favoriteManage.searchFavorite("UID00000", searchForm);
		Assert.assertEquals("���[�UID�����݂��Ȃ��ꍇ������I�����鎖�B", 0, cnt);

	}

	/**
	 * ���C�ɓ�����̌�������<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�������̑��݂��Ȃ����C�ɓ�����͍폜����Č�������鎖</li>
	 * </ul>
	 */
	@Test
	public void searchFavoriteTest1() throws Exception {

		DAOCriteria criteria = null;
		List<FavoriteInfo> favoriteInfos = null;
		FavoriteInfo favoriteInfo = null;
		HousingInfo housingInfo = null;
		Date date = null;
		int cnt = 0;
		List<JoinResult> results = null;

		// �e�X�g�p�̂��C�ɓ�����o�^
		favoriteInfo = new FavoriteInfo();
		favoriteInfo.setUserId("UID00001");
		favoriteInfo.setSysHousingCd("HOU00000");
		favoriteInfo.setDisplayHousingName("�������O");
		favoriteInfo.setInsUserId("UID00001");
		favoriteInfo.setUpdUserId("UID00001");
		date = new Date();
		favoriteInfo.setInsDate(date);
		favoriteInfo.setUpdDate(date);
		this.favoriteInfoDAO.insert(new FavoriteInfo[] { favoriteInfo });

		favoriteInfo = new FavoriteInfo();
		favoriteInfo.setUserId("UID00001");
		favoriteInfo.setSysHousingCd("HOU00001");
		favoriteInfo.setDisplayHousingName("�������P");
		favoriteInfo.setInsUserId("UID00001");
		favoriteInfo.setUpdUserId("UID00001");
		date = new Date();
		favoriteInfo.setInsDate(date);
		favoriteInfo.setUpdDate(date);
		this.favoriteInfoDAO.insert(new FavoriteInfo[] { favoriteInfo });

		// �e�X�g�P�[�X���{�O�m�F
		criteria = new DAOCriteria();
		criteria.addWhereClause("userId", "UID00001");
		favoriteInfos = this.favoriteInfoDAO.selectByFilter(criteria);
		Assert.assertEquals("�y���O�m�F�z�e�X�g���{�O�A���C�ɓ���̓o�^������2���ł��鎖�B", 2, favoriteInfos.size());

		// �e�X�g�R�[�h���s
		FavoriteSearchForm searchForm = this.favoriteFormFactory.createFavoriteSearchForm();
		cnt = this.favoriteManage.searchFavorite("UID00001", searchForm);

		// �e�X�g���ʊm�F
		Assert.assertEquals("���C�ɓ����񂪐������擾����Ă��鎖�B", 1, cnt);

		results = searchForm.getRows();
		favoriteInfo = (FavoriteInfo) results.get(0).getItems().get("favoriteInfo");
		housingInfo = (HousingInfo) results.get(0).getItems().get("housingInfo");

		Assert.assertEquals("���C�ɓ�����̃��[�UID�����������B", "UID00001", favoriteInfo.getUserId());
		Assert.assertEquals("���C�ɓ�����̃V�X�e������CD�����������B", "HOU00001", favoriteInfo.getSysHousingCd());
		Assert.assertEquals("���C�ɓ�����̕\���p�����������������B", "�������P", favoriteInfo.getDisplayHousingName());
		Assert.assertEquals("������{���̃V�X�e������CD�����������B", "HOU00001", housingInfo.getSysHousingCd());
		Assert.assertEquals("������{���̕\���p�����������������B", "�������P", housingInfo.getDisplayHousingName());

	}

	/**
	 * ���C�ɓ�����̌�������<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>���C�ɓ����񂪐�������������鎖</li>
	 *     <li>�擾���ꂽ���C�ɓ����񂪃��[�UID�̏����A�ŏI�X�V���̍~���ŕ���ł��鎖</li>
	 * </ul>
	 */
	@Test
	public void searchFavoriteTest2() throws Exception {

		DAOCriteria criteria = null;
		List<FavoriteInfo> favoriteInfos = null;
		FavoriteInfo favoriteInfo = null;
		HousingInfo housingInfo = null;
		Date date = null;
		FavoriteSearchForm searchForm = null;
		int cnt = 0;
		List<JoinResult> results = null;

		// �e�X�g�p�̂��C�ɓ�����o�^
		favoriteInfo = new FavoriteInfo();
		favoriteInfo.setUserId("UID00001");
		favoriteInfo.setSysHousingCd("HOU00003");
		favoriteInfo.setDisplayHousingName("�������R");
		favoriteInfo.setInsUserId("UID00001");
		favoriteInfo.setUpdUserId("UID00001");
		date = new Date();
		favoriteInfo.setInsDate(date);
		favoriteInfo.setUpdDate(date);
		this.favoriteInfoDAO.insert(new FavoriteInfo[] { favoriteInfo });

		favoriteInfo = new FavoriteInfo();
		favoriteInfo.setUserId("UID00001");
		favoriteInfo.setSysHousingCd("HOU00001");
		favoriteInfo.setDisplayHousingName("�������P");
		favoriteInfo.setInsUserId("UID00001");
		favoriteInfo.setUpdUserId("UID00001");
		date = new Date();
		favoriteInfo.setInsDate(date);
		favoriteInfo.setUpdDate(date);
		this.favoriteInfoDAO.insert(new FavoriteInfo[] { favoriteInfo });

		favoriteInfo = new FavoriteInfo();
		favoriteInfo.setUserId("UID00001");
		favoriteInfo.setSysHousingCd("HOU00002");
		favoriteInfo.setDisplayHousingName("�������Q");
		favoriteInfo.setInsUserId("UID00001");
		favoriteInfo.setUpdUserId("UID00001");
		date = new Date();
		favoriteInfo.setInsDate(date);
		favoriteInfo.setUpdDate(date);
		this.favoriteInfoDAO.insert(new FavoriteInfo[] { favoriteInfo });

		// �e�X�g�P�[�X���{�O�m�F
		criteria = new DAOCriteria();
		criteria.addWhereClause("userId", "UID00001");
		favoriteInfos = this.favoriteInfoDAO.selectByFilter(criteria);
		Assert.assertEquals("�y�e�X�g�P�[�X���{�O�m�F�z���C�ɓ���̓o�^������2���ł��鎖�B", 3, favoriteInfos.size());

		// �e�X�g�R�[�h���s
		searchForm = this.favoriteFormFactory.createFavoriteSearchForm();
		cnt = this.favoriteManage.searchFavorite("UID00001", searchForm);

		// �e�X�g���ʊm�F
		Assert.assertEquals("���C�ɓ����񂪐������擾����Ă��鎖�B", 3, cnt);

		results = searchForm.getRows();
		favoriteInfo = (FavoriteInfo) results.get(0).getItems().get("favoriteInfo");
		housingInfo = (HousingInfo) results.get(0).getItems().get("housingInfo");

		Assert.assertEquals("���C�ɓ�����̃��[�UID�����������B", "UID00001", favoriteInfo.getUserId());
		Assert.assertEquals("���C�ɓ�����̃V�X�e������CD�����������B", "HOU00001", favoriteInfo.getSysHousingCd());
		Assert.assertEquals("���C�ɓ�����̕\���p�����������������B", "�������P", favoriteInfo.getDisplayHousingName());
		Assert.assertEquals("������{���̃V�X�e������CD�����������B", "HOU00001", housingInfo.getSysHousingCd());
		Assert.assertEquals("������{���̕\���p�����������������B", "�������P", housingInfo.getDisplayHousingName());

		favoriteInfo = (FavoriteInfo) results.get(1).getItems().get("favoriteInfo");
		housingInfo = (HousingInfo) results.get(1).getItems().get("housingInfo");

		Assert.assertEquals("���C�ɓ�����̃��[�UID�����������B", "UID00001", favoriteInfo.getUserId());
		Assert.assertEquals("���C�ɓ�����̃V�X�e������CD�����������B", "HOU00002", favoriteInfo.getSysHousingCd());
		Assert.assertEquals("���C�ɓ�����̕\���p�����������������B", "�������Q", favoriteInfo.getDisplayHousingName());
		Assert.assertEquals("������{���̃V�X�e������CD�����������B", "HOU00002", housingInfo.getSysHousingCd());
		Assert.assertEquals("������{���̕\���p�����������������B", "�������Q", housingInfo.getDisplayHousingName());

		favoriteInfo = (FavoriteInfo) results.get(2).getItems().get("favoriteInfo");
		housingInfo = (HousingInfo) results.get(2).getItems().get("housingInfo");

		Assert.assertEquals("���C�ɓ�����̃��[�UID�����������B", "UID00001", favoriteInfo.getUserId());
		Assert.assertEquals("���C�ɓ�����̃V�X�e������CD�����������B", "HOU00003", favoriteInfo.getSysHousingCd());
		Assert.assertEquals("���C�ɓ�����̕\���p�����������������B", "�������R", favoriteInfo.getDisplayHousingName());
		Assert.assertEquals("������{���̃V�X�e������CD�����������B", "HOU00003", housingInfo.getSysHousingCd());
		Assert.assertEquals("������{���̕\���p�����������������B", "�������R", housingInfo.getDisplayHousingName());

		// �e�X�g�R�[�h���s
		searchForm = this.favoriteFormFactory.createFavoriteSearchForm();
		searchForm.setSysHousingCd("HOU00002");
		cnt = this.favoriteManage.searchFavorite("UID00001", searchForm);

		// �e�X�g���ʊm�F
		Assert.assertEquals("���C�ɓ����񂪐������擾����Ă��鎖�B", 1, cnt);

		results = searchForm.getRows();
		favoriteInfo = (FavoriteInfo) results.get(0).getItems().get("favoriteInfo");
		housingInfo = (HousingInfo) results.get(0).getItems().get("housingInfo");

		Assert.assertEquals("���C�ɓ�����̃��[�UID�����������B", "UID00001", favoriteInfo.getUserId());
		Assert.assertEquals("���C�ɓ�����̃V�X�e������CD�����������B", "HOU00002", favoriteInfo.getSysHousingCd());
		Assert.assertEquals("���C�ɓ�����̕\���p�����������������B", "�������Q", favoriteInfo.getDisplayHousingName());
		Assert.assertEquals("������{���̃V�X�e������CD�����������B", "HOU00002", housingInfo.getSysHousingCd());
		Assert.assertEquals("������{���̕\���p�����������������B", "�������Q", housingInfo.getDisplayHousingName());

	}

	/**
	 * ���C�ɓ�����̌�������<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�������̑��݂��Ȃ����C�ɓ�����͍폜����Č�������鎖</li>
	 * </ul>
	 */
	@Test
	public void searchFavoriteTest3() throws Exception {

		// buildCriteria���g�p�������Ƃɂ���ăy�[�W�\�������O��
		// ���C�ɓ����񂪐������폜����Ă��Ȃ����Ƃ����������߁A�e�X�g�P�[�X�ǉ�

		DAOCriteria criteria = null;
		List<FavoriteInfo> favoriteInfos = null;
		FavoriteInfo favoriteInfo = null;
		HousingInfo housingInfo = null;
		Date date = null;
		int cnt = 0;
		List<JoinResult> results = null;

		// �e�X�g�p�̂��C�ɓ�����o�^
		favoriteInfo = new FavoriteInfo();
		favoriteInfo.setUserId("UID00001");
		favoriteInfo.setSysHousingCd("HOU00000");
		favoriteInfo.setDisplayHousingName("�������O");
		favoriteInfo.setInsUserId("UID00001");
		favoriteInfo.setUpdUserId("UID00001");
		date = new Date();
		favoriteInfo.setInsDate(date);
		favoriteInfo.setUpdDate(date);
		this.favoriteInfoDAO.insert(new FavoriteInfo[] { favoriteInfo });

		// �e�X�g�p�̂��C�ɓ�����o�^
		favoriteInfo = new FavoriteInfo();
		favoriteInfo.setUserId("UID00001");
		favoriteInfo.setSysHousingCd("HOU09999");
		favoriteInfo.setDisplayHousingName("�������X�X�X�X");
		favoriteInfo.setInsUserId("UID00001");
		favoriteInfo.setUpdUserId("UID00001");
		date = new Date();
		favoriteInfo.setInsDate(date);
		favoriteInfo.setUpdDate(date);
		this.favoriteInfoDAO.insert(new FavoriteInfo[] { favoriteInfo });

		favoriteInfo = new FavoriteInfo();
		favoriteInfo.setUserId("UID00001");
		favoriteInfo.setSysHousingCd("HOU00001");
		favoriteInfo.setDisplayHousingName("�������P");
		favoriteInfo.setInsUserId("UID00001");
		favoriteInfo.setUpdUserId("UID00001");
		date = new Date();
		favoriteInfo.setInsDate(date);
		favoriteInfo.setUpdDate(date);
		this.favoriteInfoDAO.insert(new FavoriteInfo[] { favoriteInfo });

		// �e�X�g�P�[�X���{�O�m�F
		criteria = new DAOCriteria();
		criteria.addWhereClause("userId", "UID00001");
		favoriteInfos = this.favoriteInfoDAO.selectByFilter(criteria);
		Assert.assertEquals("�y���O�m�F�z�e�X�g���{�O�A���C�ɓ���̓o�^������3���ł��鎖�B", 3, favoriteInfos.size());

		// �e�X�g�R�[�h���s
		FavoriteSearchForm searchForm = this.favoriteFormFactory.createFavoriteSearchForm();
		searchForm.setRowsPerPage(1);
		cnt = this.favoriteManage.searchFavorite("UID00001", searchForm);

		// �e�X�g���ʊm�F
		Assert.assertEquals("���C�ɓ����񂪐������擾����Ă��鎖�B", 1, cnt);

		results = searchForm.getRows();
		favoriteInfo = (FavoriteInfo) results.get(0).getItems().get("favoriteInfo");
		housingInfo = (HousingInfo) results.get(0).getItems().get("housingInfo");

		Assert.assertEquals("���C�ɓ�����̃��[�UID�����������B", "UID00001", favoriteInfo.getUserId());
		Assert.assertEquals("���C�ɓ�����̃V�X�e������CD�����������B", "HOU00001", favoriteInfo.getSysHousingCd());
		Assert.assertEquals("���C�ɓ�����̕\���p�����������������B", "�������P", favoriteInfo.getDisplayHousingName());
		Assert.assertEquals("������{���̃V�X�e������CD�����������B", "HOU00001", housingInfo.getSysHousingCd());
		Assert.assertEquals("������{���̕\���p�����������������B", "�������P", housingInfo.getDisplayHousingName());

	}

}
