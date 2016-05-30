package jp.co.transcosmos.dm3.test.core.model.housing;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.co.transcosmos.dm3.core.model.HousingManage;
import jp.co.transcosmos.dm3.core.model.exception.NotFoundException;
import jp.co.transcosmos.dm3.core.vo.BuildingInfo;
import jp.co.transcosmos.dm3.core.vo.HousingExtInfo;
import jp.co.transcosmos.dm3.core.vo.HousingInfo;
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
 * ������� model �����g���������̃e�X�g�P�[�X
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationContext-test.xml")
public class HousingManageExtInfoTest {

	@Autowired
	private DAO<BuildingInfo> buildingInfoDAO;

	@Autowired
	private DAO<HousingInfo> housingInfoDAO;
	
	@Autowired
	private DAO<HousingExtInfo> housingExtInfoDAO;
	
	@Qualifier("housingManager")
	@Autowired
	private HousingManage housingManage;
	
	
	
	// �e�X�g�Ώی����A�����̎�L�[
	Object buildingId[];
	Object housingId[];
	
	
	
	// ����������
	@Before
	public void init() {
		// �������S���폜
		DAOCriteria criteria = new DAOCriteria();
		this.housingInfoDAO.deleteByFilter(criteria);
		this.buildingInfoDAO.deleteByFilter(criteria);

		
		// �e�X�g�f�[�^�̎�L�[���Q�擾
		buildingId = this.buildingInfoDAO.allocatePrimaryKeyIds(2);
		housingId = this.housingInfoDAO.allocatePrimaryKeyIds(2);

		// note
		// core �ɑ΂���e�X�g�R�[�h�Ȃ̂ŁA VO �𒼐ڐ������Ă���B
		// �e�X�g�R�[�h�ȊO�ł́A���̗l�ȏ����͍s��Ȃ����B

		BuildingInfo buildingInfo = new BuildingInfo();

		// �_�~�[�������쐬�P����
		buildingInfo = new BuildingInfo();
		buildingInfo.setSysBuildingCd((String)buildingId[0]);
		buildingInfo.setDisplayBuildingName("�_�~�[�P");
		buildingInfo.setPrefCd("13");
		this.buildingInfoDAO.insert(new BuildingInfo[]{buildingInfo});

		// �_�~�[�������쐬�Q����
		buildingInfo = new BuildingInfo();
		buildingInfo.setSysBuildingCd((String)buildingId[1]);
		buildingInfo.setDisplayBuildingName("�_�~�[�Q");
		buildingInfo.setPrefCd("14");
		this.buildingInfoDAO.insert(new BuildingInfo[]{buildingInfo});

		
		HousingInfo housingInfo = new HousingInfo();
		
		// �_�~�[�������쐬�P����
		housingInfo = new HousingInfo();
		housingInfo.setSysHousingCd((String)housingId[0]);
		housingInfo.setSysBuildingCd((String)buildingId[0]);
		housingInfo.setDisplayHousingName("�_�~�[�P");
		this.housingInfoDAO.insert(new HousingInfo[]{housingInfo});

		// �_�~�[�������쐬�Q����
		housingInfo = new HousingInfo();
		housingInfo.setSysHousingCd((String)housingId[1]);
		housingInfo.setSysBuildingCd((String)buildingId[1]);
		housingInfo.setDisplayHousingName("�_�~�[�Q");
		this.housingInfoDAO.insert(new HousingInfo[]{housingInfo});

	}
	
	

	/**
	 * Key �P�ʂ̍폜�����̃e�X�g<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�w�肳�ꂽ�A�V�X�e������CD�A�J�e�S���AKey �̃��R�[�h���폜����鎖�B</li>
	 *     <li>key �Ⴂ�̃��R�[�h���폜����Ȃ����B</li>
	 *     <li>�J�e�S�� �Ⴂ�̃��R�[�h���폜����Ȃ����B</li>
	 *     <li>�V�X�e������CD �Ⴂ�̃��R�[�h���폜����Ȃ����B</li>
	 *     <li>�X�V�Ώە�����{���̍X�V���A�X�V�҂��ύX����Ă��鎖</li>
	 *     <li>�X�V�ΊO�̏ە�����{���̍X�V���A�X�V�҂��ύX����Ă��Ȃ���</li>
	 * </ul>
	 */
	@Test
	public void delExtInfoKeyLevelTest() throws Exception {
		
		// �_�~�[�f�[�^�쐬
		HousingExtInfo housingExtInfo;
		
		// �P���ځi�폜�Ώہj
		housingExtInfo = new HousingExtInfo();
		housingExtInfo.setSysHousingCd((String)housingId[0]);
		housingExtInfo.setCategory("category1");
		housingExtInfo.setKeyName("key1");
		this.housingExtInfoDAO.insert(new HousingExtInfo[]{housingExtInfo});
		
		// �Q���ځi�폜�ΏۊO key �Ⴂ�j
		housingExtInfo = new HousingExtInfo();
		housingExtInfo.setSysHousingCd((String)housingId[0]);
		housingExtInfo.setCategory("category1");
		housingExtInfo.setKeyName("key2");
		this.housingExtInfoDAO.insert(new HousingExtInfo[]{housingExtInfo});

		// �R���ځi�폜�ΏۊO category �Ⴂ�j
		housingExtInfo = new HousingExtInfo();
		housingExtInfo.setSysHousingCd((String)housingId[0]);
		housingExtInfo.setCategory("category2");
		housingExtInfo.setKeyName("key1");
		this.housingExtInfoDAO.insert(new HousingExtInfo[]{housingExtInfo});

		// �S���ځi�폜�ΏۊO sysHousingCd �Ⴂ�j
		housingExtInfo = new HousingExtInfo();
		housingExtInfo.setSysHousingCd((String)housingId[1]);
		housingExtInfo.setCategory("category1");
		housingExtInfo.setKeyName("key1");
		this.housingExtInfoDAO.insert(new HousingExtInfo[]{housingExtInfo});

		
		// �e�X�g�f�[�^�`�F�b�N
		List<HousingExtInfo> list = this.housingExtInfoDAO.selectByFilter(null);
		Assert.assertEquals("�e�X�g�f�[�^�̓o�^�m�F", 4, list.size());

		
		// �e�X�g�R�[�h���s
		this.housingManage.delExtInfo((String)housingId[0], "category1", "key1", "dummyID");

		
		// ���s���ʃ`�F�b�N
		list = this.housingExtInfoDAO.selectByFilter(null);
		Assert.assertEquals("�����̊m�F", 3, list.size());

		// �폜�Ώۂ̃f�[�^�����݂��Ȃ������`�F�b�N
		for (HousingExtInfo extInfo : list) {
			if (housingId[0].equals(extInfo.getSysHousingCd()) &&
				"category1".equals(extInfo.getCategory()) &&
				"key1".equals(extInfo.getKeyName())){
				Assert.fail("�폜�Ώۃ��R�[�h���폜����Ă��Ȃ�");
			}
		}

		// ������{���̃^�C���X�^���v���m�F
		HousingInfo housingInfo = this.housingInfoDAO.selectByPK(housingId[0]);

		// �^�C���X�^���v�̕��͎����`�F�b�N������̂ŁA�����e�X�g�ΏۊO
		Assert.assertEquals("�X�V�҂���������", "dummyID", housingInfo.getUpdUserId());
		Assert.assertNull("�V�K�o�^�҂��X�V����Ă��Ȃ���", housingInfo.getInsUserId());
		Assert.assertNull("�V�K�o�^�����X�V����Ă��Ȃ���", housingInfo.getInsDate());
		

		// �X�V�ΏۊO���R�[�h�̃^�C���X�^���v���`�F�b�N
		housingInfo = this.housingInfoDAO.selectByPK(housingId[1]);

		Assert.assertNull("�X�V�ΏۊO�̐V�K�o�^�҂��X�V����Ă��Ȃ���", housingInfo.getInsUserId());
		Assert.assertNull("�X�V�ΏۊO�̐V�K�o�^�����X�V����Ă��Ȃ���", housingInfo.getInsDate());
		Assert.assertNull("�X�V�ΏۊO�̍X�V�҂��X�V����Ă��Ȃ���", housingInfo.getUpdUserId());
		Assert.assertNull("�X�V�ΏۊO�̍X�V�����X�V����Ă��Ȃ���", housingInfo.getUpdDate());
		
	}
	
	
	
	/**
	 * Key �P�ʂ̍폜�����̃e�X�g�i�Y���e���R�[�h�����݂��Ȃ��ꍇ�j<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>NotFoundException ���X���[����Ȃ���</li>
	 * </ul>
	 */
	@Test
	public void delExtInfoKeyLevelFailTest() throws Exception {

		// �e�X�g�R�[�h���s�i���肦�Ȃ��e�L�[���w��j
		this.housingManage.delExtInfo("0000", "category1", "key1", "dummyID");

	}



	/**
	 * Key �P�ʂ̍X�V�����̃e�X�g�i�Y�����R�[�h�����݂��Ȃ��ꍇ�j<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>null �l���o�^�ł��鎖</li>
	 *     <li>�ʏ�̕����񂪓o�^�ł��鎖</li>
	 * </ul>
	 */
	@Test
	public void updExtInfoKeyLevelTest1() throws NotFoundException, Exception{

		// �e�X�g�R�[�h���s
		this.housingManage.updExtInfo((String)housingId[0], "category1", "key1", null, "dummyID");


		// ���ʂ̊m�F
		List<HousingExtInfo> list = this.housingExtInfoDAO.selectByFilter(null);

		HousingExtInfo info = list.get(0);
		Assert.assertEquals("�V�X�e������CD ����������", (String)housingId[0], info.getSysHousingCd());
		Assert.assertEquals("�J�e�S������������", "category1", info.getCategory());
		Assert.assertEquals("Key ����������", "key1", info.getKeyName());
		Assert.assertNull("value �� null �ł��鎖", info.getDataValue());

		
		// �����g�����S���폜
		DAOCriteria criteria = new DAOCriteria();
		this.housingExtInfoDAO.deleteByFilter(criteria);

		
		// �e�X�g�R�[�h���s
		this.housingManage.updExtInfo((String)housingId[0], "category1", "key1", "����������", "dummyID");
		
		// ���ʂ̊m�F
		list = this.housingExtInfoDAO.selectByFilter(null);

		info = list.get(0);
		Assert.assertEquals("�V�X�e������CD ����������", (String)housingId[0], info.getSysHousingCd());
		Assert.assertEquals("�J�e�S������������", "category1", info.getCategory());
		Assert.assertEquals("Key ����������", "key1", info.getKeyName());
		Assert.assertEquals("value ����������", "����������", info.getDataValue());

	}



	/**
	 * Key �P�ʂ̍X�V�����̃e�X�g�i�Y�����R�[�h�����݂���ꍇ�j<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�X�V�Ώۂ̒l�� null �ɍX�V�ł��鎖</li>
	 *     <li>�X�V�Ώۂ̒l���ʏ핶����ɍX�V�ł��鎖</li>
	 * </ul>
	 */
	@Test
	public void updExtInfoKeyLevelTest2() throws NotFoundException, Exception{

		// �_�~�[�f�[�^�쐬
		HousingExtInfo housingExtInfo;
		
		// �P���ځi�X�V�Ώہj
		housingExtInfo = new HousingExtInfo();
		housingExtInfo.setSysHousingCd((String)housingId[0]);
		housingExtInfo.setCategory("category1");
		housingExtInfo.setKeyName("key1");
		this.housingExtInfoDAO.insert(new HousingExtInfo[]{housingExtInfo});
		
		// �Q���ځi�X�V�ΏۊO key �Ⴂ�j
		housingExtInfo = new HousingExtInfo();
		housingExtInfo.setSysHousingCd((String)housingId[0]);
		housingExtInfo.setCategory("category1");
		housingExtInfo.setKeyName("key2");
		this.housingExtInfoDAO.insert(new HousingExtInfo[]{housingExtInfo});

		// �R���ځi�X�V�ΏۊO category �Ⴂ�j
		housingExtInfo = new HousingExtInfo();
		housingExtInfo.setSysHousingCd((String)housingId[0]);
		housingExtInfo.setCategory("category2");
		housingExtInfo.setKeyName("key1");
		this.housingExtInfoDAO.insert(new HousingExtInfo[]{housingExtInfo});

		// �S���ځi�X�V�ΏۊO sysHousingCd �Ⴂ�j
		housingExtInfo = new HousingExtInfo();
		housingExtInfo.setSysHousingCd((String)housingId[1]);
		housingExtInfo.setCategory("category1");
		housingExtInfo.setKeyName("key1");
		this.housingExtInfoDAO.insert(new HousingExtInfo[]{housingExtInfo});

		
		// �e�X�g�R�[�h���s
		this.housingManage.updExtInfo((String)housingId[0], "category1", "key1", "����������", "dummyID");


		// ���ʂ̊m�F
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", (String)housingId[0]);
		criteria.addWhereClause("category", "category1");
		criteria.addWhereClause("keyName", "key1");
		criteria.addWhereClause("dataValue", "����������");

		List<HousingExtInfo> list = this.housingExtInfoDAO.selectByFilter(criteria);
		Assert.assertEquals("�X�V�Ώۂ� value ����������", 1, list.size());

		
		criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", (String)housingId[0]);
		criteria.addWhereClause("category", "category1");
		criteria.addWhereClause("keyName", "key2");
		
		list = this.housingExtInfoDAO.selectByFilter(criteria);
		Assert.assertNull("�X�V�ΏۊO���X�V����Ă��Ȃ���", list.get(0).getDataValue());
		

		criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", (String)housingId[0]);
		criteria.addWhereClause("category", "category2");
		criteria.addWhereClause("keyName", "key1");
		
		list = this.housingExtInfoDAO.selectByFilter(criteria);
		Assert.assertNull("�X�V�ΏۊO���X�V����Ă��Ȃ���", list.get(0).getDataValue());

		
		criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", (String)housingId[1]);
		criteria.addWhereClause("category", "category1");
		criteria.addWhereClause("keyName", "key1");
		
		list = this.housingExtInfoDAO.selectByFilter(criteria);
		Assert.assertNull("�X�V�ΏۊO���X�V����Ă��Ȃ���", list.get(0).getDataValue());

	}



	/**
	 * �J�e�S���P�ʂ̍폜�����̃e�X�g<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�w�肳�ꂽ�A�V�X�e������CD�A�J�e�S���̃��R�[�h���폜����鎖�B</li>
	 *     <li>�J�e�S�� �Ⴂ�̃��R�[�h���폜����Ȃ����B</li>
	 *     <li>�V�X�e������CD �Ⴂ�̃��R�[�h���폜����Ȃ����B</li>
	 * </ul>
	 */
	@Test
	public void delExtInfoCategoryLevelTest() throws Exception {
		
		// �_�~�[�f�[�^�쐬
		HousingExtInfo housingExtInfo;
		
		// �P���ځi�폜�Ώہj
		housingExtInfo = new HousingExtInfo();
		housingExtInfo.setSysHousingCd((String)housingId[0]);
		housingExtInfo.setCategory("category1");
		housingExtInfo.setKeyName("key1");
		this.housingExtInfoDAO.insert(new HousingExtInfo[]{housingExtInfo});
		
		// �Q���ځi�폜�Ώ� key �Ⴂ�j
		housingExtInfo = new HousingExtInfo();
		housingExtInfo.setSysHousingCd((String)housingId[0]);
		housingExtInfo.setCategory("category1");
		housingExtInfo.setKeyName("key2");
		this.housingExtInfoDAO.insert(new HousingExtInfo[]{housingExtInfo});

		// �R���ځi�폜�ΏۊO category �Ⴂ�j
		housingExtInfo = new HousingExtInfo();
		housingExtInfo.setSysHousingCd((String)housingId[0]);
		housingExtInfo.setCategory("category2");
		housingExtInfo.setKeyName("key1");
		this.housingExtInfoDAO.insert(new HousingExtInfo[]{housingExtInfo});

		// �S���ځi�폜�ΏۊO sysHousingCd �Ⴂ�j
		housingExtInfo = new HousingExtInfo();
		housingExtInfo.setSysHousingCd((String)housingId[1]);
		housingExtInfo.setCategory("category1");
		housingExtInfo.setKeyName("key1");
		this.housingExtInfoDAO.insert(new HousingExtInfo[]{housingExtInfo});

		
		// �e�X�g�f�[�^�`�F�b�N
		List<HousingExtInfo> list = this.housingExtInfoDAO.selectByFilter(null);
		Assert.assertEquals("�e�X�g�f�[�^�̓o�^�m�F", 4, list.size());

		
		// �e�X�g�R�[�h���s
		this.housingManage.delExtInfo((String)housingId[0], "category1", "dummyID");


		// ���s���ʃ`�F�b�N
		list = this.housingExtInfoDAO.selectByFilter(null);
		Assert.assertEquals("�����̊m�F", 2, list.size());

		// �폜�Ώۂ̃f�[�^�����݂��Ȃ������`�F�b�N
		for (HousingExtInfo extInfo : list) {
			if (housingId[0].equals(extInfo.getSysHousingCd()) &&
				"category1".equals(extInfo.getCategory())){
				Assert.fail("�폜�Ώۃ��R�[�h���폜����Ă��Ȃ�");
			}
		}

	}
	
	
	
	/**
	 * �J�e�S���P�ʂ̍X�V�����̃e�X�g�i�Y�����R�[�h�����݂���ꍇ�j<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�Ώۂ̃J�e�S���z��������ւ�鎖</li>
	 *     <li>�ΏۊO�̃J�e�S���z��������ւ��Ȃ���</li>
	 * </ul>
	 */
	@Test
	public void updExtInfoCategoryLevelTest1() throws NotFoundException, Exception{

		// �_�~�[�f�[�^�쐬
		HousingExtInfo housingExtInfo;
		
		// �P���ځi�X�V�Ώہj
		housingExtInfo = new HousingExtInfo();
		housingExtInfo.setSysHousingCd((String)housingId[0]);
		housingExtInfo.setCategory("category1");
		housingExtInfo.setKeyName("key1");
		this.housingExtInfoDAO.insert(new HousingExtInfo[]{housingExtInfo});
		
		// �Q���ځi�X�V�Ώ� key �Ⴂ�j
		housingExtInfo = new HousingExtInfo();
		housingExtInfo.setSysHousingCd((String)housingId[0]);
		housingExtInfo.setCategory("category1");
		housingExtInfo.setKeyName("key2");
		this.housingExtInfoDAO.insert(new HousingExtInfo[]{housingExtInfo});

		// �R���ځi�X�V�ΏۊO category �Ⴂ�j
		housingExtInfo = new HousingExtInfo();
		housingExtInfo.setSysHousingCd((String)housingId[0]);
		housingExtInfo.setCategory("category2");
		housingExtInfo.setKeyName("key1");
		this.housingExtInfoDAO.insert(new HousingExtInfo[]{housingExtInfo});

		// �S���ځi�X�V�ΏۊO sysHousingCd �Ⴂ�j
		housingExtInfo = new HousingExtInfo();
		housingExtInfo.setSysHousingCd((String)housingId[1]);
		housingExtInfo.setCategory("category1");
		housingExtInfo.setKeyName("key1");
		this.housingExtInfoDAO.insert(new HousingExtInfo[]{housingExtInfo});

		
		// �e�X�g�R�[�h���s
		Map<String, String> testMap = new HashMap<>();
		testMap.put("key11", "����������");
		testMap.put("key12", "����������");

		this.housingManage.updExtInfo((String)housingId[0], "category1", testMap, "dummyID");


		// ���ʂ̊m�F
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", (String)housingId[0]);
		criteria.addWhereClause("category", "category1");
		criteria.addWhereClause("keyName", "key1");

		List<HousingExtInfo> list = this.housingExtInfoDAO.selectByFilter(criteria);
		Assert.assertEquals("�X�V�O�̃f�[�^�����݂��Ȃ���", 0, list.size());

		
		criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", (String)housingId[0]);
		criteria.addWhereClause("category", "category1");
		criteria.addWhereClause("keyName", "key2");
		
		list = this.housingExtInfoDAO.selectByFilter(criteria);
		Assert.assertEquals("�X�V�O�̃f�[�^�����݂��Ȃ���", 0, list.size());
		

		criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", (String)housingId[0]);
		criteria.addWhereClause("category", "category1");
		criteria.addWhereClause("keyName", "key11");
		criteria.addWhereClause("dataValue", "����������");
		
		list = this.housingExtInfoDAO.selectByFilter(criteria);
		Assert.assertEquals("�X�V��̃f�[�^�����݂��鎖", 1, list.size());
		

		criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", (String)housingId[0]);
		criteria.addWhereClause("category", "category1");
		criteria.addWhereClause("keyName", "key12");
		criteria.addWhereClause("dataValue", "����������");
		
		list = this.housingExtInfoDAO.selectByFilter(criteria);
		Assert.assertEquals("�X�V��̃f�[�^�����݂��鎖", 1, list.size());

		
		criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", (String)housingId[0]);
		criteria.addWhereClause("category", "category2");
		criteria.addWhereClause("keyName", "key1");
		
		list = this.housingExtInfoDAO.selectByFilter(criteria);
		Assert.assertNull("�X�V�ΏۊO���X�V����Ă��Ȃ���", list.get(0).getDataValue());

		
		criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", (String)housingId[1]);
		criteria.addWhereClause("category", "category1");
		criteria.addWhereClause("keyName", "key1");
		
		list = this.housingExtInfoDAO.selectByFilter(criteria);
		Assert.assertNull("�X�V�ΏۊO���X�V����Ă��Ȃ���", list.get(0).getDataValue());

	}


	
	
	/**
	 * �J�e�S���P�ʂ̍X�V�����̃e�X�g�i�Y�����R�[�h�����݂���ꍇ�j<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>Map ����̏ꍇ�A�Ώۂ̃J�e�S���z�����폜����鎖</li>
	 *     <li>�ΏۊO�̃J�e�S���z��������ւ��Ȃ���</li>
	 * </ul>
	 */
	@Test
	public void updExtInfoCategoryLevelTest2() throws NotFoundException, Exception{

		// �_�~�[�f�[�^�쐬
		HousingExtInfo housingExtInfo;
		
		// �P���ځi�X�V�Ώہj
		housingExtInfo = new HousingExtInfo();
		housingExtInfo.setSysHousingCd((String)housingId[0]);
		housingExtInfo.setCategory("category1");
		housingExtInfo.setKeyName("key1");
		this.housingExtInfoDAO.insert(new HousingExtInfo[]{housingExtInfo});
		
		// �Q���ځi�X�V�Ώ� key �Ⴂ�j
		housingExtInfo = new HousingExtInfo();
		housingExtInfo.setSysHousingCd((String)housingId[0]);
		housingExtInfo.setCategory("category1");
		housingExtInfo.setKeyName("key2");
		this.housingExtInfoDAO.insert(new HousingExtInfo[]{housingExtInfo});

		// �R���ځi�X�V�ΏۊO category �Ⴂ�j
		housingExtInfo = new HousingExtInfo();
		housingExtInfo.setSysHousingCd((String)housingId[0]);
		housingExtInfo.setCategory("category2");
		housingExtInfo.setKeyName("key1");
		this.housingExtInfoDAO.insert(new HousingExtInfo[]{housingExtInfo});

		// �S���ځi�X�V�ΏۊO sysHousingCd �Ⴂ�j
		housingExtInfo = new HousingExtInfo();
		housingExtInfo.setSysHousingCd((String)housingId[1]);
		housingExtInfo.setCategory("category1");
		housingExtInfo.setKeyName("key1");
		this.housingExtInfoDAO.insert(new HousingExtInfo[]{housingExtInfo});

		
		// �e�X�g�R�[�h���s�i�}�b�v������j
		Map<String, String> testMap = new HashMap<>();

		this.housingManage.updExtInfo((String)housingId[0], "category1", testMap, "dummyID");


		// ���ʂ̊m�F
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", (String)housingId[0]);
		criteria.addWhereClause("category", "category1");
		criteria.addWhereClause("keyName", "key1");

		List<HousingExtInfo> list = this.housingExtInfoDAO.selectByFilter(criteria);
		Assert.assertEquals("�X�V�O�̃f�[�^�����݂��Ȃ���", 0, list.size());

		
		criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", (String)housingId[0]);
		criteria.addWhereClause("category", "category1");
		criteria.addWhereClause("keyName", "key2");
		
		list = this.housingExtInfoDAO.selectByFilter(criteria);
		Assert.assertEquals("�X�V�O�̃f�[�^�����݂��Ȃ���", 0, list.size());
		

		criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", (String)housingId[0]);
		criteria.addWhereClause("category", "category2");
		criteria.addWhereClause("keyName", "key1");
		
		list = this.housingExtInfoDAO.selectByFilter(criteria);
		Assert.assertNull("�X�V�ΏۊO���X�V����Ă��Ȃ���", list.get(0).getDataValue());

		
		criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", (String)housingId[1]);
		criteria.addWhereClause("category", "category1");
		criteria.addWhereClause("keyName", "key1");
		
		list = this.housingExtInfoDAO.selectByFilter(criteria);
		Assert.assertNull("�X�V�ΏۊO���X�V����Ă��Ȃ���", list.get(0).getDataValue());

	}

	
	
	/**
	 * �J�e�S���P�ʂ̍X�V�����̃e�X�g�i�Y�����R�[�h�����݂���ꍇ�j<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>Map �� null �ꍇ�A�Ώۂ̃J�e�S���z�����폜����鎖</li>
	 *     <li>�ΏۊO�̃J�e�S���z��������ւ��Ȃ���</li>
	 * </ul>
	 */
	@Test
	public void updExtInfoCategoryLevelTest3() throws NotFoundException, Exception{

		// �_�~�[�f�[�^�쐬
		HousingExtInfo housingExtInfo;
		
		// �P���ځi�X�V�Ώہj
		housingExtInfo = new HousingExtInfo();
		housingExtInfo.setSysHousingCd((String)housingId[0]);
		housingExtInfo.setCategory("category1");
		housingExtInfo.setKeyName("key1");
		this.housingExtInfoDAO.insert(new HousingExtInfo[]{housingExtInfo});
		
		// �Q���ځi�X�V�Ώ� key �Ⴂ�j
		housingExtInfo = new HousingExtInfo();
		housingExtInfo.setSysHousingCd((String)housingId[0]);
		housingExtInfo.setCategory("category1");
		housingExtInfo.setKeyName("key2");
		this.housingExtInfoDAO.insert(new HousingExtInfo[]{housingExtInfo});

		// �R���ځi�X�V�ΏۊO category �Ⴂ�j
		housingExtInfo = new HousingExtInfo();
		housingExtInfo.setSysHousingCd((String)housingId[0]);
		housingExtInfo.setCategory("category2");
		housingExtInfo.setKeyName("key1");
		this.housingExtInfoDAO.insert(new HousingExtInfo[]{housingExtInfo});

		// �S���ځi�X�V�ΏۊO sysHousingCd �Ⴂ�j
		housingExtInfo = new HousingExtInfo();
		housingExtInfo.setSysHousingCd((String)housingId[1]);
		housingExtInfo.setCategory("category1");
		housingExtInfo.setKeyName("key1");
		this.housingExtInfoDAO.insert(new HousingExtInfo[]{housingExtInfo});

		
		// �e�X�g�R�[�h���s�i�}�b�v����null�j
		this.housingManage.updExtInfo((String)housingId[0], "category1", null, "dummyID");


		// ���ʂ̊m�F
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", (String)housingId[0]);
		criteria.addWhereClause("category", "category1");
		criteria.addWhereClause("keyName", "key1");

		List<HousingExtInfo> list = this.housingExtInfoDAO.selectByFilter(criteria);
		Assert.assertEquals("�X�V�O�̃f�[�^�����݂��Ȃ���", 0, list.size());

		
		criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", (String)housingId[0]);
		criteria.addWhereClause("category", "category1");
		criteria.addWhereClause("keyName", "key2");
		
		list = this.housingExtInfoDAO.selectByFilter(criteria);
		Assert.assertEquals("�X�V�O�̃f�[�^�����݂��Ȃ���", 0, list.size());
		

		criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", (String)housingId[0]);
		criteria.addWhereClause("category", "category2");
		criteria.addWhereClause("keyName", "key1");
		
		list = this.housingExtInfoDAO.selectByFilter(criteria);
		Assert.assertNull("�X�V�ΏۊO���X�V����Ă��Ȃ���", list.get(0).getDataValue());

		
		criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", (String)housingId[1]);
		criteria.addWhereClause("category", "category1");
		criteria.addWhereClause("keyName", "key1");
		
		list = this.housingExtInfoDAO.selectByFilter(criteria);
		Assert.assertNull("�X�V�ΏۊO���X�V����Ă��Ȃ���", list.get(0).getDataValue());

	}

	
	
	/**
	 * �V�X�e������CD�P�ʂ̍폜�����̃e�X�g<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�w�肳�ꂽ�A�V�X�e������CD�̃��R�[�h���폜����鎖�B</li>
	 *     <li>�V�X�e������CD �Ⴂ�̃��R�[�h���폜����Ȃ����B</li>
	 * </ul>
	 */
	@Test
	public void delExtInfoHCDLevelTest() throws Exception {
		
		// �_�~�[�f�[�^�쐬
		HousingExtInfo housingExtInfo;
		
		// �P���ځi�폜�Ώہj
		housingExtInfo = new HousingExtInfo();
		housingExtInfo.setSysHousingCd((String)housingId[0]);
		housingExtInfo.setCategory("category1");
		housingExtInfo.setKeyName("key1");
		this.housingExtInfoDAO.insert(new HousingExtInfo[]{housingExtInfo});
		
		// �Q���ځi�폜�Ώ� key �Ⴂ�j
		housingExtInfo = new HousingExtInfo();
		housingExtInfo.setSysHousingCd((String)housingId[0]);
		housingExtInfo.setCategory("category1");
		housingExtInfo.setKeyName("key2");
		this.housingExtInfoDAO.insert(new HousingExtInfo[]{housingExtInfo});

		// �R���ځi�폜�Ώ� category �Ⴂ�j
		housingExtInfo = new HousingExtInfo();
		housingExtInfo.setSysHousingCd((String)housingId[0]);
		housingExtInfo.setCategory("category2");
		housingExtInfo.setKeyName("key1");
		this.housingExtInfoDAO.insert(new HousingExtInfo[]{housingExtInfo});

		// �S���ځi�폜�ΏۊO sysHousingCd �Ⴂ�j
		housingExtInfo = new HousingExtInfo();
		housingExtInfo.setSysHousingCd((String)housingId[1]);
		housingExtInfo.setCategory("category1");
		housingExtInfo.setKeyName("key1");
		this.housingExtInfoDAO.insert(new HousingExtInfo[]{housingExtInfo});

		
		// �e�X�g�f�[�^�`�F�b�N
		List<HousingExtInfo> list = this.housingExtInfoDAO.selectByFilter(null);
		Assert.assertEquals("�e�X�g�f�[�^�̓o�^�m�F", 4, list.size());

		
		// �e�X�g�R�[�h���s
		this.housingManage.delExtInfo((String)housingId[0], "dummyID");


		// ���s���ʃ`�F�b�N
		list = this.housingExtInfoDAO.selectByFilter(null);
		Assert.assertEquals("�����̊m�F", 1, list.size());

		// �폜�Ώۂ̃f�[�^�����݂��Ȃ������`�F�b�N
		for (HousingExtInfo extInfo : list) {
			if (housingId[0].equals(extInfo.getSysHousingCd()) &&
				"category1".equals(extInfo.getCategory())){
				Assert.fail("�폜�Ώۃ��R�[�h���폜����Ă��Ȃ�");
			}
		}

	}

	
	
	/**
	 * �V�X�e������CD�P�ʂ̍X�V�����̃e�X�g�i�Y�����R�[�h�����݂���ꍇ�j<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�Ώۂ̃V�X�e������CD�z��������ւ�鎖</li>
	 *     <li>�ΏۊO�̃V�X�e������CD�z��������ւ��Ȃ���</li>
	 * </ul>
	 */
	@Test
	public void updExtInfoHCDLevelTest1() throws NotFoundException, Exception{

		// �_�~�[�f�[�^�쐬
		HousingExtInfo housingExtInfo;
		
		// �P���ځi�X�V�Ώہj
		housingExtInfo = new HousingExtInfo();
		housingExtInfo.setSysHousingCd((String)housingId[0]);
		housingExtInfo.setCategory("category1");
		housingExtInfo.setKeyName("key1");
		this.housingExtInfoDAO.insert(new HousingExtInfo[]{housingExtInfo});
		
		// �Q���ځi�X�V�Ώ� key �Ⴂ�j
		housingExtInfo = new HousingExtInfo();
		housingExtInfo.setSysHousingCd((String)housingId[0]);
		housingExtInfo.setCategory("category1");
		housingExtInfo.setKeyName("key2");
		this.housingExtInfoDAO.insert(new HousingExtInfo[]{housingExtInfo});

		// �R���ځi�X�V�Ώ� category �Ⴂ�j
		housingExtInfo = new HousingExtInfo();
		housingExtInfo.setSysHousingCd((String)housingId[0]);
		housingExtInfo.setCategory("category2");
		housingExtInfo.setKeyName("key1");
		this.housingExtInfoDAO.insert(new HousingExtInfo[]{housingExtInfo});

		// �S���ځi�X�V�ΏۊO sysHousingCd �Ⴂ�j
		housingExtInfo = new HousingExtInfo();
		housingExtInfo.setSysHousingCd((String)housingId[1]);
		housingExtInfo.setCategory("category1");
		housingExtInfo.setKeyName("key1");
		this.housingExtInfoDAO.insert(new HousingExtInfo[]{housingExtInfo});

		
		// �e�X�g�R�[�h���s
		Map<String, String> category11Map = new HashMap<>();
		category11Map.put("key111", "����������");

		Map<String, String> category21Map = new HashMap<>();
		category21Map.put("key211", "����������");
		category21Map.put("key212", "�����Ă�");
		
		
		Map<String, Map<String, String>> rootMap = new HashMap<>();
		rootMap.put("category11", category11Map);
		rootMap.put("category21", category21Map);


		this.housingManage.updExtInfo((String)housingId[0], rootMap, "dummyID");


		// ���ʂ̊m�F
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", (String)housingId[0]);
		criteria.addWhereClause("category", "category1");
		criteria.addWhereClause("keyName", "key1");

		List<HousingExtInfo> list = this.housingExtInfoDAO.selectByFilter(criteria);
		Assert.assertEquals("�X�V�O�̃f�[�^�����݂��Ȃ���", 0, list.size());

		
		criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", (String)housingId[0]);
		criteria.addWhereClause("category", "category1");
		criteria.addWhereClause("keyName", "key2");
		
		list = this.housingExtInfoDAO.selectByFilter(criteria);
		Assert.assertEquals("�X�V�O�̃f�[�^�����݂��Ȃ���", 0, list.size());
		

		criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", (String)housingId[0]);
		criteria.addWhereClause("category", "category2");
		criteria.addWhereClause("keyName", "key1");

		list = this.housingExtInfoDAO.selectByFilter(criteria);
		Assert.assertEquals("�X�V�O�̃f�[�^�����݂��Ȃ���", 0, list.size());


		criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", (String)housingId[0]);
		criteria.addWhereClause("category", "category11");
		criteria.addWhereClause("keyName", "key111");
		criteria.addWhereClause("dataValue", "����������");

		list = this.housingExtInfoDAO.selectByFilter(criteria);
		Assert.assertEquals("�X�V��̃f�[�^�����݂��鎖", 1, list.size());
		

		criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", (String)housingId[0]);
		criteria.addWhereClause("category", "category21");
		criteria.addWhereClause("keyName", "key211");
		criteria.addWhereClause("dataValue", "����������");

		list = this.housingExtInfoDAO.selectByFilter(criteria);
		Assert.assertEquals("�X�V��̃f�[�^�����݂��鎖", 1, list.size());


		criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", (String)housingId[0]);
		criteria.addWhereClause("category", "category21");
		criteria.addWhereClause("keyName", "key212");
		criteria.addWhereClause("dataValue", "�����Ă�");

		list = this.housingExtInfoDAO.selectByFilter(criteria);
		Assert.assertEquals("�X�V��̃f�[�^�����݂��鎖", 1, list.size());

		
		criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", (String)housingId[1]);
		criteria.addWhereClause("category", "category1");
		criteria.addWhereClause("keyName", "key1");
		
		list = this.housingExtInfoDAO.selectByFilter(criteria);
		Assert.assertNull("�X�V�ΏۊO���X�V����Ă��Ȃ���", list.get(0).getDataValue());

	}


	
	/**
	 * �V�X�e������CD�P�ʂ̍X�V�����̃e�X�g�i�Y�����R�[�h�����݂���ꍇ�j<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>map ����̏ꍇ�A�Ώۂ̃V�X�e������CD�z�����폜����鎖</li>
	 *     <li>�ΏۊO�̃V�X�e������CD�z��������ւ��Ȃ���</li>
	 * </ul>
	 */
	@Test
	public void updExtInfoHCDLevelTest2() throws NotFoundException, Exception{

		// �_�~�[�f�[�^�쐬
		HousingExtInfo housingExtInfo;
		
		// �P���ځi�X�V�Ώہj
		housingExtInfo = new HousingExtInfo();
		housingExtInfo.setSysHousingCd((String)housingId[0]);
		housingExtInfo.setCategory("category1");
		housingExtInfo.setKeyName("key1");
		this.housingExtInfoDAO.insert(new HousingExtInfo[]{housingExtInfo});
		
		// �Q���ځi�X�V�Ώ� key �Ⴂ�j
		housingExtInfo = new HousingExtInfo();
		housingExtInfo.setSysHousingCd((String)housingId[0]);
		housingExtInfo.setCategory("category1");
		housingExtInfo.setKeyName("key2");
		this.housingExtInfoDAO.insert(new HousingExtInfo[]{housingExtInfo});

		// �R���ځi�X�V�Ώ� category �Ⴂ�j
		housingExtInfo = new HousingExtInfo();
		housingExtInfo.setSysHousingCd((String)housingId[0]);
		housingExtInfo.setCategory("category2");
		housingExtInfo.setKeyName("key1");
		this.housingExtInfoDAO.insert(new HousingExtInfo[]{housingExtInfo});

		// �S���ځi�X�V�ΏۊO sysHousingCd �Ⴂ�j
		housingExtInfo = new HousingExtInfo();
		housingExtInfo.setSysHousingCd((String)housingId[1]);
		housingExtInfo.setCategory("category1");
		housingExtInfo.setKeyName("key1");
		this.housingExtInfoDAO.insert(new HousingExtInfo[]{housingExtInfo});

		
		// �e�X�g�R�[�h���s
		Map<String, Map<String, String>> rootMap = new HashMap<>();


		this.housingManage.updExtInfo((String)housingId[0], rootMap, "dummyID");


		// ���ʂ̊m�F
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", (String)housingId[0]);
		criteria.addWhereClause("category", "category1");
		criteria.addWhereClause("keyName", "key1");

		List<HousingExtInfo> list = this.housingExtInfoDAO.selectByFilter(criteria);
		Assert.assertEquals("�X�V�O�̃f�[�^�����݂��Ȃ���", 0, list.size());

		
		criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", (String)housingId[0]);
		criteria.addWhereClause("category", "category1");
		criteria.addWhereClause("keyName", "key2");
		
		list = this.housingExtInfoDAO.selectByFilter(criteria);
		Assert.assertEquals("�X�V�O�̃f�[�^�����݂��Ȃ���", 0, list.size());
		

		criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", (String)housingId[0]);
		criteria.addWhereClause("category", "category2");
		criteria.addWhereClause("keyName", "key1");

		list = this.housingExtInfoDAO.selectByFilter(criteria);
		Assert.assertEquals("�X�V�O�̃f�[�^�����݂��Ȃ���", 0, list.size());


		criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", (String)housingId[1]);
		criteria.addWhereClause("category", "category1");
		criteria.addWhereClause("keyName", "key1");
		
		list = this.housingExtInfoDAO.selectByFilter(criteria);
		Assert.assertNull("�X�V�ΏۊO���X�V����Ă��Ȃ���", list.get(0).getDataValue());

	}

	
	
	/**
	 * �V�X�e������CD�P�ʂ̍X�V�����̃e�X�g�i�Y�����R�[�h�����݂���ꍇ�j<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>map �� null �̏ꍇ�A�Ώۂ̃V�X�e������CD�z�����폜����鎖</li>
	 *     <li>�ΏۊO�̃V�X�e������CD�z��������ւ��Ȃ���</li>
	 * </ul>
	 */
	@Test
	public void updExtInfoHCDLevelTest3() throws NotFoundException, Exception{

		// �_�~�[�f�[�^�쐬
		HousingExtInfo housingExtInfo;
		
		// �P���ځi�X�V�Ώہj
		housingExtInfo = new HousingExtInfo();
		housingExtInfo.setSysHousingCd((String)housingId[0]);
		housingExtInfo.setCategory("category1");
		housingExtInfo.setKeyName("key1");
		this.housingExtInfoDAO.insert(new HousingExtInfo[]{housingExtInfo});
		
		// �Q���ځi�X�V�Ώ� key �Ⴂ�j
		housingExtInfo = new HousingExtInfo();
		housingExtInfo.setSysHousingCd((String)housingId[0]);
		housingExtInfo.setCategory("category1");
		housingExtInfo.setKeyName("key2");
		this.housingExtInfoDAO.insert(new HousingExtInfo[]{housingExtInfo});

		// �R���ځi�X�V�Ώ� category �Ⴂ�j
		housingExtInfo = new HousingExtInfo();
		housingExtInfo.setSysHousingCd((String)housingId[0]);
		housingExtInfo.setCategory("category2");
		housingExtInfo.setKeyName("key1");
		this.housingExtInfoDAO.insert(new HousingExtInfo[]{housingExtInfo});

		// �S���ځi�X�V�ΏۊO sysHousingCd �Ⴂ�j
		housingExtInfo = new HousingExtInfo();
		housingExtInfo.setSysHousingCd((String)housingId[1]);
		housingExtInfo.setCategory("category1");
		housingExtInfo.setKeyName("key1");
		this.housingExtInfoDAO.insert(new HousingExtInfo[]{housingExtInfo});

		
		// �e�X�g�R�[�h���s
		this.housingManage.updExtInfo((String)housingId[0], null, "dummyID");


		// ���ʂ̊m�F
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", (String)housingId[0]);
		criteria.addWhereClause("category", "category1");
		criteria.addWhereClause("keyName", "key1");

		List<HousingExtInfo> list = this.housingExtInfoDAO.selectByFilter(criteria);
		Assert.assertEquals("�X�V�O�̃f�[�^�����݂��Ȃ���", 0, list.size());

		
		criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", (String)housingId[0]);
		criteria.addWhereClause("category", "category1");
		criteria.addWhereClause("keyName", "key2");
		
		list = this.housingExtInfoDAO.selectByFilter(criteria);
		Assert.assertEquals("�X�V�O�̃f�[�^�����݂��Ȃ���", 0, list.size());
		

		criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", (String)housingId[0]);
		criteria.addWhereClause("category", "category2");
		criteria.addWhereClause("keyName", "key1");

		list = this.housingExtInfoDAO.selectByFilter(criteria);
		Assert.assertEquals("�X�V�O�̃f�[�^�����݂��Ȃ���", 0, list.size());


		criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", (String)housingId[1]);
		criteria.addWhereClause("category", "category1");
		criteria.addWhereClause("keyName", "key1");
		
		list = this.housingExtInfoDAO.selectByFilter(criteria);
		Assert.assertNull("�X�V�ΏۊO���X�V����Ă��Ȃ���", list.get(0).getDataValue());

	}



	/**
	 * �V�X�e������CD�P�ʂ̍X�V�����̃e�X�g�i�e���R�[�h�Ȃ��j<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>NotFoundException ���X���[����鎖</li>
	 * </ul>
	 */
	@Test(expected=NotFoundException.class)
	public void updExtInfoFailTest() throws NotFoundException, Exception{

		 Map<String, Map<String, String>> cateMap = new HashMap<>();
		 
		 Map<String, String> keyMap = new HashMap<>();
		 keyMap.put("key", "value");
		 cateMap.put("category", keyMap);
		 
		
		// ���݂��Ȃ��V�X�e������CD �Ńe�X�g�R�[�h���s
		this.housingManage.updExtInfo("XXXXXXXXXX", cateMap, "dummyID");
		
	}
}
