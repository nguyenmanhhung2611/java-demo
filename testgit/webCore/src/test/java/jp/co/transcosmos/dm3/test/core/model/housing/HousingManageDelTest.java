package jp.co.transcosmos.dm3.test.core.model.housing;

import java.util.Date;
import java.util.List;

import jp.co.transcosmos.dm3.core.model.HousingManage;
import jp.co.transcosmos.dm3.core.model.housing.form.HousingForm;
import jp.co.transcosmos.dm3.core.model.housing.form.HousingFormFactory;
import jp.co.transcosmos.dm3.core.vo.BuildingInfo;
import jp.co.transcosmos.dm3.core.vo.HousingDtlInfo;
import jp.co.transcosmos.dm3.core.vo.HousingEquipInfo;
import jp.co.transcosmos.dm3.core.vo.HousingExtInfo;
import jp.co.transcosmos.dm3.core.vo.HousingImageInfo;
import jp.co.transcosmos.dm3.core.vo.HousingInfo;
import jp.co.transcosmos.dm3.core.vo.HousingPartInfo;
import jp.co.transcosmos.dm3.core.vo.HousingStatusInfo;
import jp.co.transcosmos.dm3.dao.DAO;
import jp.co.transcosmos.dm3.dao.DAOCriteria;
import jp.co.transcosmos.dm3.utils.DateUtils;
import jp.co.transcosmos.dm3.utils.StringUtils;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * ������� model �폜�n�����̃e�X�g
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationContext-test.xml")
public class HousingManageDelTest {

	@Autowired
	private DAO<HousingInfo> housingInfoDAO;
	@Autowired
	private DAO<HousingDtlInfo> housingDtlInfoDAO;
	@Autowired
	private DAO<HousingStatusInfo> housingStatusInfoDAO;
	@Autowired
	private DAO<HousingImageInfo> housingImageInfoDAO;
	@Autowired
	private DAO<BuildingInfo> buildingInfoDAO;
	@Autowired
	private DAO<HousingEquipInfo> housingEquipInfoDAO;
	@Autowired
	private DAO<HousingExtInfo> housingExtInfoDAO;
	@Autowired
	private DAO<HousingPartInfo> housingPartInfoDAO;

	@Autowired
	private HousingFormFactory formFactory;

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
		
		// ������{���P
		BuildingInfo buildingInfo = new BuildingInfo();
		buildingInfo.setSysBuildingCd("SYSBLD0001");
		buildingInfo.setDisplayBuildingName("�������P");
		buildingInfo.setPrefCd("13");

		this.buildingInfoDAO.insert(new BuildingInfo[] {buildingInfo});

		
		// ������{���Q
		buildingInfo = new BuildingInfo();
		buildingInfo.setSysBuildingCd("SYSBLD0002");
		buildingInfo.setDisplayBuildingName("�������Q");
		buildingInfo.setPrefCd("14");

		this.buildingInfoDAO.insert(new BuildingInfo[] {buildingInfo});

	}



	/**
	 * �������̍폜
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�폜�ΏۂƂȂ镨����{��񂪍폜����Ă��鎖</li>
	 *     <li>�폜�ΏۂƂȂ镨���ڍ׏�񂪍폜����Ă��鎖</li>
	 *     <li>�폜�ΏۂƂȂ镨���X�e�[�^�X��񂪍폜����Ă��鎖</li>
	 *     <li>�폜�ΏۂƂȂ镨��������������񂪍폜����Ă��鎖</li>
	 *     <li>�폜�ΏۂƂȂ镨���ݔ���񂪍폜����Ă��鎖</li>
	 *     <li>�폜�ΏۂƂȂ镨���g����񂪍폜����Ă��鎖</li>
	 *     <li>�폜�ΏۂƂȂ镨���摜��񂪍폜����Ă��鎖</li>
	 * </ul>
	 */
	@Test
	public void delHousingTest() throws Exception {
		
		// �e�X�g�f�[�^�쐬
		initData();

		// �t�H�[���쐬
		HousingForm form = this.formFactory.createHousingForm();
		form.setSysHousingCd("SYSHOU00001");

		// �e�X�g�Ώۃ��\�b�h���s
		this.housingManage.delHousingInfo(form, "dummy");
		

		// �e�X�g���ʊm�F
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", "SYSHOU00001");
		
		// �폜�Ώۃf�[�^�����݂��Ȃ���
		Assert.assertEquals("�Y�����镨����{��񂪍폜����Ă��鎖", 0, this.housingInfoDAO.selectByFilter(criteria).size());
		Assert.assertEquals("�Y�����镨���ڍ׏�񂪍폜����Ă��鎖", 0, this.housingDtlInfoDAO.selectByFilter(criteria).size());
		Assert.assertEquals("�Y�����镨���X�e�[�^�X��񂪍폜����Ă��鎖", 0, this.housingStatusInfoDAO.selectByFilter(criteria).size());
		Assert.assertEquals("�Y�����镨����������񂪍폜����Ă��鎖", 0, this.housingPartInfoDAO.selectByFilter(criteria).size());
		Assert.assertEquals("�Y�����镨���ݔ���񂪍폜����Ă��鎖", 0, this.housingEquipInfoDAO.selectByFilter(criteria).size());
		Assert.assertEquals("�Y�����镨���g��������񂪍폜����Ă��鎖", 0, this.housingExtInfoDAO.selectByFilter(criteria).size());
		Assert.assertEquals("�Y�����镨���摜��񂪍폜����Ă��鎖", 0, this.housingImageInfoDAO.selectByFilter(criteria).size());

		
		// �폜�ΏۊO�f�[�^�����݂��鎖
		criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", "SYSHOU00002");
		
		Assert.assertEquals("�ΏۊO�̕�����{��񂪍폜����Ă��Ȃ���", 1, this.housingInfoDAO.selectByFilter(criteria).size());
		Assert.assertEquals("�ΏۊO�̕����ڍ׏�񂪍폜����Ă��Ȃ���", 1, this.housingDtlInfoDAO.selectByFilter(criteria).size());
		Assert.assertEquals("�ΏۊO�̕����X�e�[�^�X��񂪍폜����Ă��Ȃ���", 1, this.housingStatusInfoDAO.selectByFilter(criteria).size());
		Assert.assertEquals("�ΏۊO�̕�����������񂪍폜����Ă��Ȃ���", 2, this.housingPartInfoDAO.selectByFilter(criteria).size());
		Assert.assertEquals("�ΏۊO�̕����ݔ���񂪍폜����Ă��Ȃ���", 2, this.housingEquipInfoDAO.selectByFilter(criteria).size());
		Assert.assertEquals("�ΏۊO�̕����g��������񂪍폜����Ă��Ȃ���", 3, this.housingExtInfoDAO.selectByFilter(criteria).size());
		Assert.assertEquals("�ΏۊO�̕����摜��񂪍폜����Ă��Ȃ���", 2, this.housingImageInfoDAO.selectByFilter(criteria).size());
		
	}
	
	

	/**
	 * �����摜���̍폜
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�폜�t���O���ݒ肳��Ă��镨���摜��񂪍폜����Ă��鎖</li>
	 *     <li>�폜�t���O���ݒ肳��Ă��Ȃ������摜��񂪍폜����Ă��Ȃ���</li>
	 *     <li>�}�Ԃ��č\�z����Ă��鎖</li>
	 *     <li>�֘A���镨����{���̍X�V���A�X�V�҂��X�V����Ă��鎖�B</li>
	 *     <li>�֘A���Ȃ�������{���̍X�V���A�X�V�҂��X�V����Ă��Ȃ����B</li>
	 * </ul>
	 */
	@Test
	public void delHousingImgTest() throws Exception {
		
		// �e�X�g�f�[�^�쐬
		initData();
		

		// �e�X�g�Ώۃ��\�b�h���s
		this.housingManage.delHousingImg("SYSHOU00001", "00", 2, "editUserId1");


		// ���s���ʊm�F�@�i�폜�ΏۃV�X�e������CD�j
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", "SYSHOU00001");
		criteria.addOrderByClause("imageType");
		criteria.addOrderByClause("divNo");

		List<HousingImageInfo> list = this.housingImageInfoDAO.selectByFilter(criteria);

		Assert.assertEquals("�P���폜����Ă��鎖", 1, list.size());
		Assert.assertEquals("�폜���ꂽ�͎̂w�肳�ꂽ�}�Ԃ̃f�[�^�ł��鎖", "FILE00001_01", list.get(0).getFileName());


		// ���s���ʊm�F�@�i�폜�ΏۊO�̃V�X�e������CD�j
		criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", "SYSHOU00002");

		list = this.housingImageInfoDAO.selectByFilter(criteria);
		
		Assert.assertEquals("�폜����Ă��Ȃ���", 2, list.size());


	
		// ������{���̃^�C���X�^���v�m�F�i�폜�Ώہj
		HousingInfo housingInfo = this.housingInfoDAO.selectByPK("SYSHOU00001");
		String day10 = StringUtils.dateToString(DateUtils.addDays(new Date(), -10));
		String date = StringUtils.dateToString(housingInfo.getInsDate());
		Assert.assertEquals("�o�^�����X�V����Ă��Ȃ���", day10, date);
		Assert.assertEquals("�o�^�҂��X�V����Ă��Ȃ���", "dummInsID1", housingInfo.getInsUserId());
		
		String now = StringUtils.dateToString(new Date());
		date = StringUtils.dateToString(housingInfo.getUpdDate());
		Assert.assertEquals("�X�V�����X�V����Ă��鎖", now, date);
		Assert.assertEquals("�X�V�҂��X�V����Ă��鎖", "editUserId1", housingInfo.getUpdUserId());
		
		
		// ������{���̃^�C���X�^���v�m�F�i�폜�ΏۊO�j
		housingInfo = this.housingInfoDAO.selectByPK("SYSHOU00002");
		date = StringUtils.dateToString(housingInfo.getInsDate());
		Assert.assertEquals("�o�^�����X�V����Ă��Ȃ���", day10, date);
		Assert.assertEquals("�o�^�҂��X�V����Ă��Ȃ���", "dummInsID2", housingInfo.getInsUserId());
		
		Assert.assertNull("�X�V�����X�V����Ă��Ȃ���", housingInfo.getUpdDate());
		Assert.assertNull("�X�V�҂��X�V����Ă��Ȃ���", housingInfo.getUpdUserId());

	}
	

	
	/**
	 * �����摜���̍폜 �i���C���摜�t���O�̍X�V�m�F�j
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�폜�Ώۂ����C���摜�������ꍇ�A�ʂ̉摜�Ƀ��C���摜�t���O���ݒ肳��Ă��鎖</li>
	 * </ul>
	 */
	@Test
	public void delHousingImgMainFlgTest() throws Exception {
		
		// �e�X�g�f�[�^�쐬
		initData();
		

		// �e�X�g�Ώۃ��\�b�h���s
		this.housingManage.delHousingImg("SYSHOU00001", "00", 1, "editUserId1");


		// ���s���ʊm�F�@�i�폜�ΏۃV�X�e������CD�j
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", "SYSHOU00001");
		criteria.addWhereClause("imageType", "00");

		List<HousingImageInfo> list = this.housingImageInfoDAO.selectByFilter(criteria);

		Assert.assertEquals("�P���폜����Ă��鎖", 1, list.size());
		Assert.assertEquals("�폜���ꂽ�͎̂w�肳�ꂽ�}�Ԃ̃f�[�^�ł��鎖", "FILE00001_02", list.get(0).getFileName());
		Assert.assertEquals("�Q�Ԗڂ̎}�ԃf�[�^�����C���摜�ɐݒ肳��Ă��鎖", "1", list.get(0).getMainImageFlg());
		Assert.assertEquals("�}�Ԃ���O�̒l�ɍX�V����Ă��鎖", Integer.valueOf(1), list.get(0).getDivNo());

	}

	
	
	// �e�X�g�f�[�^�o�^
	private void initData() {

		HousingInfo housingInfo;
		HousingDtlInfo housingDtlInfo;
		HousingStatusInfo housingStatusInfo;
		HousingImageInfo[] housingImageInfo = new HousingImageInfo[2];
		HousingEquipInfo[] housingEquipInfo = new HousingEquipInfo[2];
		HousingExtInfo[] housingExtInfo = new HousingExtInfo[3];
		HousingPartInfo[] housingPartInfo = new HousingPartInfo[2]; 

		
		
		//�@������{���i�X�V�Ώہj
		housingInfo = new HousingInfo();
		housingInfo.setSysHousingCd("SYSHOU00001");
		housingInfo.setSysBuildingCd("SYSBLD0001");
		housingInfo.setDisplayHousingName("OLD�������P");
		Date date = new Date();
		housingInfo.setInsDate(DateUtils.addDays(date, -10));
		housingInfo.setInsUserId("dummInsID1");

		this.housingInfoDAO.insert(new HousingInfo[]{housingInfo});
		
		// �����ڍ׏��
		housingDtlInfo = new HousingDtlInfo();
		housingDtlInfo.setSysHousingCd("SYSHOU00001");

		this.housingDtlInfoDAO.insert(new HousingDtlInfo[]{housingDtlInfo});

		// �����X�e�[�^�X���
		housingStatusInfo = new HousingStatusInfo();
		housingStatusInfo.setSysHousingCd("SYSHOU00001");
		housingStatusInfo.setHiddenFlg("0");

		this.housingStatusInfoDAO.insert(new HousingStatusInfo[]{housingStatusInfo});

		// �����摜���@�i�Q���j
		housingImageInfo[0] = new HousingImageInfo();
		housingImageInfo[0].setSysHousingCd("SYSHOU00001");
		housingImageInfo[0].setImageType("00");
		housingImageInfo[0].setDivNo(1);
		housingImageInfo[0].setMainImageFlg("1");
		housingImageInfo[0].setPathName("PATH00001_01");
		housingImageInfo[0].setFileName("FILE00001_01");

		housingImageInfo[1] = new HousingImageInfo();
		housingImageInfo[1].setSysHousingCd("SYSHOU00001");
		housingImageInfo[1].setImageType("00");
		housingImageInfo[1].setDivNo(2);
		housingImageInfo[1].setMainImageFlg("0");
		housingImageInfo[1].setPathName("PATH00001_02");
		housingImageInfo[1].setFileName("FILE00001_02");

		this.housingImageInfoDAO.insert(housingImageInfo);

		// �����ݔ����
		housingEquipInfo[0] = new HousingEquipInfo();
		housingEquipInfo[0].setSysHousingCd("SYSHOU00001");
		housingEquipInfo[0].setEquipCd("000001");

		housingEquipInfo[1] = new HousingEquipInfo();
		housingEquipInfo[1].setSysHousingCd("SYSHOU00001");
		housingEquipInfo[1].setEquipCd("000002");
				
		this.housingEquipInfoDAO.insert(housingEquipInfo);;

		// �����g���������
		housingExtInfo[0] = new HousingExtInfo();
		housingExtInfo[0].setSysHousingCd("SYSHOU00001");
		housingExtInfo[0].setCategory("CATE0001");
		housingExtInfo[0].setKeyName("KEY0001");
		housingExtInfo[0].setDataValue("VALUE00001");

		housingExtInfo[1] = new HousingExtInfo();
		housingExtInfo[1].setSysHousingCd("SYSHOU00001");
		housingExtInfo[1].setCategory("CATE0001");
		housingExtInfo[1].setKeyName("KEY0002");
		housingExtInfo[1].setDataValue("VALUE00002");

		housingExtInfo[2] = new HousingExtInfo();
		housingExtInfo[2].setSysHousingCd("SYSHOU00001");
		housingExtInfo[2].setCategory("CATE0002");
		housingExtInfo[2].setKeyName("KEY0001");
		housingExtInfo[2].setDataValue("VALUE00003");

		this.housingExtInfoDAO.insert(housingExtInfo);

		// �����������������
		housingPartInfo[0] = new HousingPartInfo();
		housingPartInfo[0].setSysHousingCd("SYSHOU00001");
		housingPartInfo[0].setPartSrchCd("B05");

		housingPartInfo[1] = new HousingPartInfo();
		housingPartInfo[1].setSysHousingCd("SYSHOU00001");
		housingPartInfo[1].setPartSrchCd("B10");

		this.housingPartInfoDAO.insert(housingPartInfo);
		
		
		
		//�@������{���i�X�V�ΏۊO�j
		housingInfo = new HousingInfo();
		housingInfo.setSysHousingCd("SYSHOU00002");
		housingInfo.setSysBuildingCd("SYSBLD0001");
		housingInfo.setDisplayHousingName("OLD�������Q");
		housingInfo.setInsDate(DateUtils.addDays(date, -10));
		housingInfo.setInsUserId("dummInsID2");

		this.housingInfoDAO.insert(new HousingInfo[]{housingInfo});

		// �����ڍ׏��
		housingDtlInfo = new HousingDtlInfo();
		housingDtlInfo.setSysHousingCd("SYSHOU00002");
		
		this.housingDtlInfoDAO.insert(new HousingDtlInfo[]{housingDtlInfo});
		
		// �����X�e�[�^�X���
		housingStatusInfo = new HousingStatusInfo();
		housingStatusInfo.setSysHousingCd("SYSHOU00002");
		housingStatusInfo.setHiddenFlg("0");

		this.housingStatusInfoDAO.insert(new HousingStatusInfo[]{housingStatusInfo});

		// �����摜���@�i�Q���j
		housingImageInfo[0] = new HousingImageInfo();
		housingImageInfo[0].setSysHousingCd("SYSHOU00002");
		housingImageInfo[0].setImageType("00");
		housingImageInfo[0].setDivNo(1);
		housingImageInfo[0].setMainImageFlg("1");
		housingImageInfo[0].setPathName("PATH00002_01");
		housingImageInfo[0].setFileName("FILE00002_01");

		housingImageInfo[1] = new HousingImageInfo();
		housingImageInfo[1].setSysHousingCd("SYSHOU00002");
		housingImageInfo[1].setImageType("00");
		housingImageInfo[1].setDivNo(2);
		housingImageInfo[1].setMainImageFlg("0");
		housingImageInfo[1].setPathName("PATH00002_02");
		housingImageInfo[1].setFileName("FILE00002_02");

		this.housingImageInfoDAO.insert(housingImageInfo);

		// �����ݔ����
		housingEquipInfo[0] = new HousingEquipInfo();
		housingEquipInfo[0].setSysHousingCd("SYSHOU00002");
		housingEquipInfo[0].setEquipCd("000001");

		housingEquipInfo[1] = new HousingEquipInfo();
		housingEquipInfo[1].setSysHousingCd("SYSHOU00002");
		housingEquipInfo[1].setEquipCd("000002");
				
		this.housingEquipInfoDAO.insert(housingEquipInfo);;

		// �����g���������
		housingExtInfo[0] = new HousingExtInfo();
		housingExtInfo[0].setSysHousingCd("SYSHOU00002");
		housingExtInfo[0].setCategory("CATE0001");
		housingExtInfo[0].setKeyName("KEY0001");
		housingExtInfo[0].setDataValue("VALUE10001");

		housingExtInfo[1] = new HousingExtInfo();
		housingExtInfo[1].setSysHousingCd("SYSHOU00002");
		housingExtInfo[1].setCategory("CATE0001");
		housingExtInfo[1].setKeyName("KEY0002");
		housingExtInfo[1].setDataValue("VALUE10002");

		housingExtInfo[2] = new HousingExtInfo();
		housingExtInfo[2].setSysHousingCd("SYSHOU00002");
		housingExtInfo[2].setCategory("CATE0002");
		housingExtInfo[2].setKeyName("KEY0001");
		housingExtInfo[2].setDataValue("VALUE10003");

		this.housingExtInfoDAO.insert(housingExtInfo);

		// �����������������
		housingPartInfo[0] = new HousingPartInfo();
		housingPartInfo[0].setSysHousingCd("SYSHOU00002");
		housingPartInfo[0].setPartSrchCd("FRE");

		housingPartInfo[1] = new HousingPartInfo();
		housingPartInfo[1].setSysHousingCd("SYSHOU00002");
		housingPartInfo[1].setPartSrchCd("REI");

		this.housingPartInfoDAO.insert(housingPartInfo);

	}
}
