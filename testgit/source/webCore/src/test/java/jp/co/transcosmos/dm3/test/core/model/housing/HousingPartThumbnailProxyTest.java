package jp.co.transcosmos.dm3.test.core.model.housing;

import java.math.BigDecimal;
import java.util.List;

import jp.co.transcosmos.dm3.core.model.HousingManage;
import jp.co.transcosmos.dm3.core.model.housing.form.HousingDtlForm;
import jp.co.transcosmos.dm3.core.model.housing.form.HousingEquipForm;
import jp.co.transcosmos.dm3.core.model.housing.form.HousingForm;
import jp.co.transcosmos.dm3.core.model.housing.form.HousingFormFactory;
import jp.co.transcosmos.dm3.core.vo.BuildingInfo;
import jp.co.transcosmos.dm3.core.vo.HousingDtlInfo;
import jp.co.transcosmos.dm3.core.vo.HousingEquipInfo;
import jp.co.transcosmos.dm3.core.vo.HousingInfo;
import jp.co.transcosmos.dm3.core.vo.HousingPartInfo;
import jp.co.transcosmos.dm3.dao.DAO;
import jp.co.transcosmos.dm3.dao.DAOCriteria;
import jp.co.transcosmos.dm3.testUtil.mock.MockPartDataCreator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


/**
 * ������� model �����������쐬�A�T���l�C���쐬 Proxy �̃e�X�g�P�[�X
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationContext-test.xml")
public class HousingPartThumbnailProxyTest {

	@Qualifier("housingManager")
	@Autowired
	private HousingManage housingManage;

	@Autowired
	private DAO<HousingInfo> housingInfoDAO;
	@Autowired
	private DAO<HousingDtlInfo> housingDtlInfoDAO;
	@Autowired
	private DAO<HousingPartInfo> housingPartInfoDAO;
	@Autowired
	private DAO<HousingEquipInfo> housingEquipInfoDAO;
	@Autowired
	private DAO<BuildingInfo> buildingInfoDAO;

	@Autowired
	private HousingFormFactory formFactory;

	// �e�X�g�R�[�h�p�́A�������������쐬���� mock �N���X
	@Qualifier("mockPartCreator")
	@Autowired
	private MockPartDataCreator mockPartDataCreator;

	
	
	// �O����
	@Before
	public void init(){
		// �������S���폜
		DAOCriteria criteria = new DAOCriteria();
		this.housingPartInfoDAO.deleteByFilter(criteria);
		this.housingInfoDAO.deleteByFilter(criteria);
		this.buildingInfoDAO.deleteByFilter(criteria);

		initTestData();
	}



	/**
	 * �����V�K�o�^���̂����������쐬�����̃e�X�g
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>������{��񂪓o�^����Ă��鎖</li>
	 *     <li>�������쐬���ɂ������������쐬����Ă��鎖</li>
	 * </ul>
	 */
	@Test
	public void addHousingMakeTest() throws Exception{

		// �������������쐬���� mock �ɁA�e�X�g�Ώۃ��\�b�h�����s�ΏۂƂ��Đݒ肷��B
		this.mockPartDataCreator.setExecuteMethod("addHousing");
		

		HousingForm inputForm = this.formFactory.createHousingForm();
		inputForm.setDisplayHousingName("HNAME0003");					// �\���p������
		inputForm.setSysBuildingCd("SBLD00001");						// �V�X�e������CD

		// �e�X�g�Ώۃ��\�b�h���s
		String id = this.housingManage.addHousing(inputForm, "editUserId1");


		// DB�̐���������ɂ��A������{��񂪓o�^����Ă��Ȃ��Ƃ������������o�^�ł��Ȃ��̂�
		// ������{���o�^�̖����I�ȃ`�F�b�N�͍s��Ȃ��B
		
		// ���s���ʂ̎擾
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", id);

		List<HousingPartInfo> list = this.housingPartInfoDAO.selectByFilter(criteria);
		Assert.assertEquals("�������������쐬����Ă��鎖", list.get(0).getPartSrchCd(), "S20");
	}

	
	
	/**
	 * �����V�K�o�^���̂����������쐬�X�L�b�v�����̃e�X�g
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�������쐬���ɂ������������쐬����Ă��Ȃ���</li>
	 * </ul>
	 */
	@Test
	public void addHousingSkipTest() throws Exception{

		// �������������쐬���� mock �ɁA�e�X�g�Ώۃ��\�b�h�����s�ΏۂƂ��Đݒ肵�Ȃ��B
		this.mockPartDataCreator.setExecuteMethod("addHousing_");
		

		HousingForm inputForm = this.formFactory.createHousingForm();
		inputForm.setDisplayHousingName("HNAME0003");					// �\���p������
		inputForm.setSysBuildingCd("SBLD00001");						// �V�X�e������CD

		// �e�X�g�Ώۃ��\�b�h���s
		String id = this.housingManage.addHousing(inputForm, "editUserId1");


		// ���s���ʂ̎擾
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", id);

		List<HousingInfo> infoList = this.housingInfoDAO.selectByFilter(criteria);
		Assert.assertEquals("�Ϗ���̃��\�b�h�����s����Ă��鎖", 1, infoList.size());

		List<HousingPartInfo> partList = this.housingPartInfoDAO.selectByFilter(criteria);
		Assert.assertEquals("�������������쐬����Ă��Ȃ���", 0, partList.size());
	}


	
	/**
	 * ������{���X�V���̂����������쐬�����̃e�X�g
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�Ϗ���i������{���̍X�V�����j�����s����Ă��鎖</li>
	 *     <li>������{���X�V���ɂ������������쐬����Ă��鎖</li>
	 * </ul>
	 */
	@Test
	public void updateHousingMakeTest() throws Exception{

		// �������������쐬���� mock �ɁA�e�X�g�Ώۃ��\�b�h�����s�ΏۂƂ��Đݒ肷��B
		this.mockPartDataCreator.setExecuteMethod("updateHousing");
		

		// ���������X�V
		HousingForm inputForm = this.formFactory.createHousingForm();
		inputForm.setSysHousingCd("SHOU00001");							// �V�X�e������CD
		inputForm.setDisplayHousingName("HNAME1001");					// �\���p������
		inputForm.setSysBuildingCd("SBLD00001");						// �V�X�e������CD

		// �e�X�g�Ώۃ��\�b�h���s
		this.housingManage.updateHousing(inputForm, "editUserId1");


		// ���s���ʂ̎擾
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", inputForm.getSysHousingCd());

		
		List<HousingInfo> infoList = this.housingInfoDAO.selectByFilter(criteria);
		Assert.assertEquals("�Ϗ���̃��\�b�h�����s����Ă��鎖", infoList.get(0).getDisplayHousingName(), "HNAME1001");

		List<HousingPartInfo> partList = this.housingPartInfoDAO.selectByFilter(criteria);
		Assert.assertEquals("�������������쐬����Ă��鎖", partList.get(0).getPartSrchCd(), "S20");
	}

	
	
	/**
	 * ������{���X�V���̂����������쐬�X�L�b�v�����̃e�X�g
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>������{���X�V���ɂ������������쐬����Ă��Ȃ���</li>
	 * </ul>
	 */
	@Test
	public void updateHousingSkipTest() throws Exception{

		// �������������쐬���� mock �ɁA�e�X�g�Ώۃ��\�b�h�����s�ΏۂƂ��Đݒ肵�Ȃ��B
		this.mockPartDataCreator.setExecuteMethod("updateHousing_");


		// ���������X�V
		HousingForm inputForm = this.formFactory.createHousingForm();
		inputForm.setSysHousingCd("SHOU00001");							// �V�X�e������CD
		inputForm.setDisplayHousingName("HNAME1001");					// �\���p������
		inputForm.setSysBuildingCd("SBLD00001");						// �V�X�e������CD

		// �e�X�g�Ώۃ��\�b�h���s
		this.housingManage.updateHousing(inputForm, "editUserId1");


		// ���s���ʂ̎擾
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", inputForm.getSysHousingCd());

		
		List<HousingInfo> infoList = this.housingInfoDAO.selectByFilter(criteria);
		Assert.assertEquals("�Ϗ���̃��\�b�h�����s����Ă��鎖", infoList.get(0).getDisplayHousingName(), "HNAME1001");

		List<HousingPartInfo> partList = this.housingPartInfoDAO.selectByFilter(criteria);
		Assert.assertEquals("�������������쐬����Ă��Ȃ���", 0, partList.size());
	}



	/**
	 * �������폜���̂����������쐬�����̃e�X�g
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�Ϗ���i������{���̍폜�����j�����s����Ă��鎖</li>
	 * </ul>
	 */
	@Test
	public void delHousingInfoTest() throws Exception{

		// �폜���́A�������������쐬�ł��Ȃ��̂ŁA�Ϗ��悪���s����Ă��鎖�̂݃`�F�b�N����B
		this.mockPartDataCreator.setExecuteMethod("delHousingInfo");
		
		HousingForm inputForm = this.formFactory.createHousingForm();
		inputForm.setSysHousingCd("SHOU00001");							// �V�X�e������CD

		// �e�X�g�Ώۃ��\�b�h���s
		this.housingManage.delHousingInfo(inputForm, "editUserId1");


		// ���s���ʂ̎擾
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", inputForm.getSysHousingCd());

		
		List<HousingInfo> infoList = this.housingInfoDAO.selectByFilter(criteria);
		Assert.assertEquals("�Ϗ���̃��\�b�h�����s����Ă��鎖", 0, infoList.size());

	}



	/**
	 * �����ڍ׏��X�V���̂����������쐬�����̃e�X�g
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�Ϗ���i�����ڍ׏��̍X�V�����j�����s����Ă��鎖</li>
	 *     <li>�����ڍ׏��X�V���ɂ������������쐬����Ă��鎖</li>
	 * </ul>
	 */
	@Test
	public void updateHousingDtlMakeTest() throws Exception{

		// �������������쐬���� mock �ɁA�e�X�g�Ώۃ��\�b�h�����s�ΏۂƂ��Đݒ肷��B
		this.mockPartDataCreator.setExecuteMethod("updateHousingDtl");
		

		// ���������X�V
		HousingDtlForm inputForm = this.formFactory.createHousingDtlForm();
		inputForm.setSysHousingCd("SHOU00001");							// �V�X�e������CD
		inputForm.setRenewDue("100.5");

		// �e�X�g�Ώۃ��\�b�h���s
		this.housingManage.updateHousingDtl(inputForm, "editUserId1");


		// ���s���ʂ̎擾
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", inputForm.getSysHousingCd());

		
		List<HousingDtlInfo> dtlList = this.housingDtlInfoDAO.selectByFilter(criteria);
		Assert.assertEquals("�Ϗ���̃��\�b�h�����s����Ă��鎖", dtlList.get(0).getRenewDue(), new BigDecimal("100.50"));

		List<HousingPartInfo> partList = this.housingPartInfoDAO.selectByFilter(criteria);
		Assert.assertEquals("�������������쐬����Ă��鎖", partList.get(0).getPartSrchCd(), "S20");
	}

	
	
	
	/**
	 * �����ڍ׏��X�V���̂����������쐬�X�L�b�v�����̃e�X�g
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�����ڍ׏��X�V���ɂ������������쐬����Ă��Ȃ���</li>
	 * </ul>
	 */
	@Test
	public void updateHousingDtlSkipTest() throws Exception{

		// �������������쐬���� mock �ɁA�e�X�g�Ώۃ��\�b�h�����s�ΏۂƂ��Đݒ肵�Ȃ��B
		this.mockPartDataCreator.setExecuteMethod("updateHousingDtl_");


		HousingDtlForm inputForm = this.formFactory.createHousingDtlForm();
		inputForm.setSysHousingCd("SHOU00001");							// �V�X�e������CD
		inputForm.setRenewDue("100.5");

		// �e�X�g�Ώۃ��\�b�h���s
		this.housingManage.updateHousingDtl(inputForm, "editUserId1");


		// ���s���ʂ̎擾
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", inputForm.getSysHousingCd());

		
		List<HousingDtlInfo> dtlList = this.housingDtlInfoDAO.selectByFilter(criteria);
		Assert.assertEquals("�Ϗ���̃��\�b�h�����s����Ă��鎖", dtlList.get(0).getRenewDue(), new BigDecimal("100.50"));

		List<HousingPartInfo> partList = this.housingPartInfoDAO.selectByFilter(criteria);
		Assert.assertEquals("�������������쐬����Ă��Ȃ���", 0, partList.size());
	}



	/**
	 * �����ݔ����X�V���̂����������쐬�����̃e�X�g
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�Ϗ���i�����ݔ����̍X�V�����j�����s����Ă��鎖</li>
	 *     <li>�����ݔ����X�V���ɂ������������쐬����Ă��鎖</li>
	 * </ul>
	 */
	@Test
	public void updateHousingEquipMakeTest() throws Exception{

		// �������������쐬���� mock �ɁA�e�X�g�Ώۃ��\�b�h�����s�ΏۂƂ��Đݒ肷��B
		this.mockPartDataCreator.setExecuteMethod("updateHousingEquip");


		HousingEquipForm inputForm = this.formFactory.createHousingEquipForm();
		inputForm.setSysHousingCd("SHOU00001");							// �V�X�e������CD
		inputForm.setEquipCd(new String[]{"000001"});					// �ݔ�CD


		// �e�X�g�Ώۃ��\�b�h���s
		this.housingManage.updateHousingEquip(inputForm, "editUserId1");


		// ���s���ʂ̎擾
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", inputForm.getSysHousingCd());


		List<HousingEquipInfo> equList = this.housingEquipInfoDAO.selectByFilter(criteria);
		Assert.assertEquals("�Ϗ���̃��\�b�h�����s����Ă��鎖", equList.get(0).getEquipCd(), "000001");

		criteria.addWhereClause("partSrchCd", "S20");
		List<HousingPartInfo> partList = this.housingPartInfoDAO.selectByFilter(criteria);
		Assert.assertEquals("�������������쐬����Ă��鎖", 1, partList.size());
	}
	
	
	
	/**
	 * �����ݔ����X�V���̂����������쐬�X�L�b�v�����̃e�X�g
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�����ڍ׏��X�V���ɂ������������쐬����Ă��Ȃ���</li>
	 * </ul>
	 */
	@Test
	public void updateHousingEquipSkipTest() throws Exception{

		// �������������쐬���� mock �ɁA�e�X�g�Ώۃ��\�b�h�����s�ΏۂƂ��Đݒ肵�Ȃ��B
		this.mockPartDataCreator.setExecuteMethod("updateHousingEquip_");


		HousingEquipForm inputForm = this.formFactory.createHousingEquipForm();
		inputForm.setSysHousingCd("SHOU00001");							// �V�X�e������CD
		inputForm.setEquipCd(new String[]{"000001"});					// �ݔ�CD


		// �e�X�g�Ώۃ��\�b�h���s
		this.housingManage.updateHousingEquip(inputForm, "editUserId1");


		// ���s���ʂ̎擾
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", inputForm.getSysHousingCd());


		List<HousingEquipInfo> equList = this.housingEquipInfoDAO.selectByFilter(criteria);
		Assert.assertEquals("�Ϗ���̃��\�b�h�����s����Ă��鎖", equList.get(0).getEquipCd(), "000001");

		criteria.addWhereClause("partSrchCd", "S20");
		List<HousingPartInfo> partList = this.housingPartInfoDAO.selectByFilter(criteria);
		Assert.assertEquals("�������������쐬����Ă��Ȃ���", 0, partList.size());
	}
	
	
	
	
	

	// �e�X�g�f�[�^�쐬
	private void initTestData(){

		BuildingInfo buildingInfo;
		HousingInfo housingInfo;


		// ������{���i�X�V�Ώہj
		buildingInfo = new BuildingInfo();

		buildingInfo.setSysBuildingCd("SBLD00001");
		buildingInfo.setDisplayBuildingName("�������O�P");
		buildingInfo.setPrefCd("01");
		this.buildingInfoDAO.insert(new BuildingInfo[] {buildingInfo});

		// ������{���
		housingInfo = new HousingInfo();

		housingInfo.setSysHousingCd("SHOU00001");
		housingInfo.setSysBuildingCd("SBLD00001");
		housingInfo.setDisplayHousingName("�������O�P");
		this.housingInfoDAO.insert(new HousingInfo[]{housingInfo});
		


		// ������{���i�X�V�ΏۊO�j
		buildingInfo = new BuildingInfo();

		buildingInfo.setSysBuildingCd("SBLD00002");
		buildingInfo.setDisplayBuildingName("�������O�Q");
		buildingInfo.setPrefCd("02");
		this.buildingInfoDAO.insert(new BuildingInfo[] {buildingInfo});
		
		// ������{���
		housingInfo = new HousingInfo();

		housingInfo.setSysHousingCd("SHOU00002");
		housingInfo.setSysBuildingCd("SBLD00002");
		housingInfo.setDisplayHousingName("�������O�Q");
		this.housingInfoDAO.insert(new HousingInfo[]{housingInfo});

		
	}
	
}
