package jp.co.transcosmos.dm3.core.model.information;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import jp.co.transcosmos.dm3.core.model.InformationManage;
import jp.co.transcosmos.dm3.core.model.information.form.InformationForm;
import jp.co.transcosmos.dm3.core.model.information.form.InformationFormFactory;
import jp.co.transcosmos.dm3.core.model.information.form.InformationSearchForm;
import jp.co.transcosmos.dm3.core.vo.Information;
import jp.co.transcosmos.dm3.core.vo.InformationTarget;
import jp.co.transcosmos.dm3.dao.DAO;
import jp.co.transcosmos.dm3.dao.DAOCriteria;
import jp.co.transcosmos.dm3.utils.StringUtils;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * ���m�点��� model �̃e�X�g�P�[�X
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationContext-test.xml")
public class InformationManageTest {
	
	/** ���m�点���X�V�p DAO */
	@Autowired
	private DAO<Information> informationDAO;

	/** ���m�点���J����X�V�p DAO */
	@Autowired
	private DAO<InformationTarget> informationTargetDAO;
	
	@Autowired
	private InformationManage informationManage;
	
	@Autowired
	private InformationFormFactory informationFormFactory;
	
	// �O����
	@Before
	public void init() throws ParseException{
		// ���m�点���S���폜
		DAOCriteria criteria = new DAOCriteria();
		this.informationTargetDAO.deleteByFilter(criteria);
		this.informationDAO.deleteByFilter(criteria);
		initTestData();
	}
	
	// �e�X�g�f�[�^�쐬
	private void initTestData() throws ParseException{

		SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd");
		Information information;
		InformationTarget informationTarget;
		information = new Information();
		information.setInformationNo("0000000000001");
		information.setTitle("test01");
		information.setDspFlg("1");
		information.setInformationMsg("test���e01");
		information.setInsDate(StringUtils.stringToDate("2015/04/30 23:59:59", "yyyy/MM/dd HH:mm:ss"));
		informationDAO.insert(new Information[]{information});

		information = new Information();
		information.setInformationNo("0000000000002");
		information.setTitle("test02");
		information.setDspFlg("2");
		information.setInformationMsg("test���e02");
		information.setInsDate(StringUtils.stringToDate("2015/05/01 00:00:00", "yyyy/MM/dd HH:mm:ss"));
		informationDAO.insert(new Information[]{information});

		informationTarget = new InformationTarget();
		informationTarget.setInformationNo("0000000000002");
		informationTarget.setUserId("00000000000000000002");
		informationTargetDAO.insert(new InformationTarget[]{informationTarget});

		information = new Information();
		information.setInformationNo("0000000000003");
		information.setTitle("test03");
		information.setDspFlg("0");
		information.setInformationMsg("test���e03");
		information.setStartDate(sdf.parse("2015/01/01"));
		information.setEndDate(sdf.parse("2016/01/01"));
		information.setInsDate(StringUtils.stringToDate("2015/05/01 23:59:59", "yyyy/MM/dd HH:mm:ss"));
		informationDAO.insert(new Information[]{information});
		
		information = new Information();
		information.setInformationNo("0000000000004");
		information.setTitle("test04");
		information.setDspFlg("2");
		information.setInformationMsg("test���e04");
		informationTarget.setUserId("00000000000000000004");
		information.setInsDate(StringUtils.stringToDate("2015/06/01 00:00:00", "yyyy/MM/dd HH:mm:ss"));
		informationDAO.insert(new Information[]{information});
		
		information = new Information();
		information.setInformationNo("0000000000005");
		information.setTitle("test05");
		information.setDspFlg("1");
		information.setInformationMsg("test���e05");
		information.setStartDate(sdf.parse("2015/01/01"));
		information.setInsDate(StringUtils.stringToDate("2015/06/01 23:59:59", "yyyy/MM/dd HH:mm:ss"));
		informationDAO.insert(new Information[]{information});
		
		information = new Information();
		information.setInformationNo("0000000000006");
		information.setTitle("test06");
		information.setDspFlg("1");
		information.setInformationMsg("test���e06");
		information.setStartDate(sdf.parse("2016/01/01"));
		information.setInsDate(StringUtils.stringToDate("2015/05/31 00:00:00", "yyyy/MM/dd HH:mm:ss"));
		informationDAO.insert(new Information[]{information});
		
		information = new Information();
		information.setInformationNo("0000000000007");
		information.setTitle("test07");
		information.setDspFlg("1");
		information.setInformationMsg("test���e07");
		information.setEndDate(sdf.parse("2016/01/01"));
		information.setInsDate(StringUtils.stringToDate("2015/05/31 23:59:59", "yyyy/MM/dd HH:mm:ss"));
		informationDAO.insert(new Information[]{information});
		
		information = new Information();
		information.setInformationNo("0000000000008");
		information.setTitle("test08");
		information.setDspFlg("1");
		information.setInformationMsg("test���e08");
		information.setEndDate(sdf.parse("2015/01/01"));
		information.setInsDate(StringUtils.stringToDate("2015/05/20 00:00:00", "yyyy/MM/dd HH:mm:ss"));
		informationDAO.insert(new Information[]{information});
		
		information = new Information();
		information.setInformationNo("0000000000009");
		information.setTitle("test09");
		information.setDspFlg("1");
		information.setInformationMsg("test���e09");
		information.setStartDate(sdf.parse("2015/01/01"));
		information.setEndDate(sdf.parse("2016/01/01"));
		information.setInsDate(StringUtils.stringToDate("2015/06/20 00:00:00", "yyyy/MM/dd HH:mm:ss"));
		informationDAO.insert(new Information[]{information});
		
		information = new Information();
		information.setInformationNo("0000000000010");
		information.setTitle("test10");
		information.setDspFlg("1");
		information.setInformationMsg("test���e10");
		information.setStartDate(sdf.parse("2015/01/01"));
		information.setEndDate(sdf.parse("2015/02/01"));
		information.setInsDate(StringUtils.stringToDate("2014/05/20 00:00:00", "yyyy/MM/dd HH:mm:ss"));
		informationDAO.insert(new Information[]{information});
	}
	
	/**
	 * �T�C�g TOP �ɕ\�����邨�m�点�����擾����e�X�g<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>���J�Ώۋ敪 = �u�����܂ޑS����v�̃f�[�^���擾�ł��鎖</li>
	 * </ul>
	 */
	@Test
	public void searchTopInformationTest() throws Exception {

		List<Information> informationList = informationManage.searchTopInformation();
		
		Assert.assertNotNull("�Ώۃf�[�^���擾�ł��Ă��鎖", informationList.get(0));
		Assert.assertEquals("���m�点���̂��m�点�ԍ�����������", informationList.get(0).getInformationNo(), "0000000000003");
	}
	
	/**
	 * ���N�G�X�g�p�����[�^�œn���ꂽ�u���m�点�ԍ��v �i��L�[�l�j�ɊY�����邨�m�点���𕜋A����B�i�T�C�g TOP �p�j�e�X�g<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>���J�Ώۋ敪 = �u�����܂ޑS����v���擾�ΏۂɂȂ�B�܂��A�V�X�e�����t�����J���Ԓ��ł��邨�m�点��񂪎擾�ΏۂƂȂ�B�����Y���f�[�^��������Ȃ��ꍇ�Anull �𕜋A����B</li>
	 * </ul>
	 */
	@Test
	public void searchTopInformationPkTest() throws Exception {

		Information information = informationManage.searchTopInformationPk("0000000000003");
		
		Assert.assertNotNull("�Ώۃf�[�^���擾�ł��Ă��鎖", information);
		Assert.assertEquals("���m�点���̂��m�点�ԍ�����������", information.getInformationNo(), "0000000000003");
		
		information = informationManage.searchTopInformationPk("0000000000002");
		Assert.assertNull("�Ώۃf�[�^���擾�ł��Ă��Ȃ���", information);
	}
	
	/**
	 * �}�C�y�[�W TOP �ɕ\�����邨�m�点�����擾����e�X�g<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>���J�Ώۋ敪 = �u�S�{����v�A�܂��́A�u�l�v���擾�ΏۂɂȂ邪�A�u�l�v�̏ꍇ�A���m�点���J����̃��[�U�[ID �������ƈ�v����K�v������B�܂��A�V�X�e�����t�����J���Ԓ��ł��邨�m�点��񂪎擾�ΏۂƂȂ�B</li>
	 * </ul>
	 */
	@Test
	public void searchMyPageInformationTest() throws Exception {
		List<Information> informationList = informationManage.searchMyPageInformation("00000000000000000002");
		
		Assert.assertNotNull("�Ώۃf�[�^���擾�ł��Ă��鎖", informationList);
		Assert.assertEquals("���m�点���̂��m�点�ԍ�����������", informationList.get(0).getInformationNo(), "0000000000001");
		Assert.assertEquals("���m�点���̂��m�点�ԍ�����������", informationList.get(1).getInformationNo(), "0000000000002");
		Assert.assertEquals("���m�点���̂��m�点�ԍ�����������", informationList.get(2).getInformationNo(), "0000000000005");
		Assert.assertEquals("���m�点���̂��m�点�ԍ�����������", informationList.get(3).getInformationNo(), "0000000000007");
		Assert.assertEquals("���m�点���̂��m�点�ԍ�����������", informationList.get(4).getInformationNo(), "0000000000009");
	}
	
	/**
	 * �}�C�y�[�W TOP �ɕ\�����邨�m�点�����擾����e�X�g<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>���J�Ώۋ敪 = �u�S�{����v�A�܂��́A�u�l�v���擾�ΏۂɂȂ邪�A�u�l�v�̏ꍇ�A���m�点���J����̃��[�U�[ID �������ƈ�v����K�v������B�܂��A�V�X�e�����t�����J���Ԓ��ł��邨�m�点��񂪎擾�ΏۂƂȂ�B</li>
	 * </ul>
	 */
	@Test
	public void searchMyPageInformationPkTest() throws Exception {
		Information information = informationManage.searchMyPageInformationPk("0000000000001", "00000000000000000002");
		
		Assert.assertNotNull("�Ώۃf�[�^���擾�ł��Ă��鎖", information);
		Assert.assertEquals("���m�点���̂��m�点�ԍ�����������", information.getInformationNo(), "0000000000001");
		
		information = informationManage.searchMyPageInformationPk("0000000000002", "00000000000000000002");
		Assert.assertNotNull("�Ώۃf�[�^���擾�ł��Ă��鎖", information);
		Assert.assertEquals("���m�点���̂��m�点�ԍ�����������", information.getInformationNo(), "0000000000002");
		
		information = informationManage.searchMyPageInformationPk("0000000000005", "00000000000000000002");
		Assert.assertNotNull("�Ώۃf�[�^���擾�ł��Ă��鎖", information);
		Assert.assertEquals("���m�点���̂��m�点�ԍ�����������", information.getInformationNo(), "0000000000005");
		
		information = informationManage.searchMyPageInformationPk("0000000000007", "00000000000000000002");
		Assert.assertNotNull("�Ώۃf�[�^���擾�ł��Ă��鎖", information);
		Assert.assertEquals("���m�点���̂��m�点�ԍ�����������", information.getInformationNo(), "0000000000007");
		
		information = informationManage.searchMyPageInformationPk("0000000000009", "00000000000000000002");
		Assert.assertNotNull("�Ώۃf�[�^���擾�ł��Ă��鎖", information);
		Assert.assertEquals("���m�点���̂��m�点�ԍ�����������", information.getInformationNo(), "0000000000009");
		
		information = informationManage.searchMyPageInformationPk("0000000000010", "00000000000000000002");
		Assert.assertNull("�Ώۃf�[�^���擾�ł��Ă��Ȃ���", information);
	}
	
	/**
	 * �p�����[�^�œn���ꂽ Form �̏��ł��m�点����V�K�ǉ�����e�X�g<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>form�̒l�œo�^�ł��鎖</li>
	 * </ul>
	 */
	@Test
	public void addInformationTest() throws Exception {
		// ���N�G�X�g�p�����[�^���擾���� Form �I�u�W�F�N�g���쐬����B
		InformationForm informationForm = this.informationFormFactory.createInformationForm();
		informationForm.setInformationNo("0000000000011");
		informationForm.setTitle("test01");
		informationForm.setDspFlg("1");
		informationForm.setInformationMsg("test���e01");
		informationManage.addInformation(informationForm, "Test");
		
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("informationNo", informationForm.getInformationNo());
		List<Information> information = informationDAO.selectByFilter(criteria);
		
		Assert.assertEquals("form�̒l�œo�^�ł��鎖", information.get(0).getInformationNo(), "0000000000011");
	}
	
	/**
	 * �p�����[�^�œn���ꂽ Form �̏��ł��m�点�����폜����e�X�g<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>��L�[�ł��m�点�����폜�ł��鎖</li>
	 * </ul>
	 */
	@Test
	public void delInformationTest() throws Exception {
		InformationForm informationForm = this.informationFormFactory.createInformationForm();
		informationForm.setInformationNo("0000000000001");
		informationManage.delInformation(informationForm);
		
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("informationNo", informationForm.getInformationNo());
		List<Information> information = informationDAO.selectByFilter(criteria);
		
		Assert.assertEquals("��L�[�ł��m�点�����폜�ł��鎖", information.size(), 0);
	}
	
	/**
	 * ���m�点�̍X�V�e�X�g<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>form�ł��m�点�����X�V�ł��鎖</li>
	 * </ul>
	 */
	@Test
	public void updateInformationTest() throws Exception {
		InformationForm informationForm = this.informationFormFactory.createInformationForm();
		informationForm.setInformationNo("0000000000001");
		informationForm.setTitle("test11");
		informationForm.setDspFlg("1");
		informationForm.setInformationMsg("test���e11");
		
		informationManage.updateInformation(informationForm, "Test");
		
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("informationNo", informationForm.getInformationNo());
		List<Information> information = informationDAO.selectByFilter(criteria);
		
		Assert.assertEquals("form�ł��m�点�����X�V�ł��鎖", information.get(0).getTitle(), informationForm.getTitle());
		Assert.assertEquals("form�ł��m�点�����X�V�ł��鎖", information.get(0).getDspFlg(), informationForm.getDspFlg());
		Assert.assertEquals("form�ł��m�点�����X�V�ł��鎖", information.get(0).getInformationMsg(), informationForm.getInformationMsg());

	}
	
	/**
	 * ���m�点�����������A���ʃ��X�g�𕜋A����B�i�Ǘ���ʗp�j�e�X�g<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li> �����œn���ꂽ Form �p�����[�^�̒l�Ō��������𐶐����A���m�点���������ł��鎖</li>
	 * </ul>
	 */
	@Test
	public void searchAdminInformationTest() throws Exception {
		InformationSearchForm searchForm = this.informationFormFactory.createInformationSearchForm();
		int size = informationManage.searchAdminInformation(searchForm);
		Assert.assertEquals("form�ł��m�点���������ł��鎖",  size, 10);
		
		searchForm = this.informationFormFactory.createInformationSearchForm();
		searchForm.setKeyInformationNo("0000000000001");
		size = informationManage.searchAdminInformation(searchForm);
		Assert.assertEquals("form�ł��m�点���������ł��鎖",  size, 1);
		
		searchForm = this.informationFormFactory.createInformationSearchForm();
		searchForm.setKeyTitle("st");
		size = informationManage.searchAdminInformation(searchForm);
		Assert.assertEquals("form�ł��m�点���������ł��鎖",  size, 10);
		
		searchForm = this.informationFormFactory.createInformationSearchForm();
		searchForm.setKeyInsDateFrom("2015/05/01");
		size = informationManage.searchAdminInformation(searchForm);
		Assert.assertEquals("form�ł��m�点���������ł��鎖",  size, 8);
		
		searchForm = this.informationFormFactory.createInformationSearchForm();
		searchForm.setKeyInsDateTo("2015/06/01");
		size = informationManage.searchAdminInformation(searchForm);
		Assert.assertEquals("form�ł��m�点���������ł��鎖",  size, 9);
		
		searchForm = this.informationFormFactory.createInformationSearchForm();
		searchForm.setKeyInsDateFrom("2015/05/01");
		searchForm.setKeyInsDateTo("2015/05/31");
		size = informationManage.searchAdminInformation(searchForm);
		Assert.assertEquals("form�ł��m�点���������ł��鎖",  size, 5);
	}
}
