package jp.co.transcosmos.dm3.core.model.inquiry;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import jp.co.transcosmos.dm3.core.model.InquiryManage;
import jp.co.transcosmos.dm3.core.model.inquiry.form.HousingInquiryForm;
import jp.co.transcosmos.dm3.core.model.inquiry.form.InquiryFormFactory;
import jp.co.transcosmos.dm3.core.model.inquiry.form.InquiryHeaderForm;
import jp.co.transcosmos.dm3.core.vo.InquiryDtlInfo;
import jp.co.transcosmos.dm3.core.vo.InquiryHeader;
import jp.co.transcosmos.dm3.core.vo.InquiryHousing;
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
 * �����⍇�� model �̃e�X�g�P�[�X
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationContext-test.xml")
public class HousingInquiryManageTest {
	
	// �⍇���w�b�_���pDAO
	@Autowired
	private DAO<InquiryHeader> inquiryHeaderDAO;
	
	// ���⍇�����e��ʏ��pDAO
	@Autowired
	private DAO<InquiryDtlInfo> inquiryDtlInfoDAO;
	
	// �����₢���킹��� DAO
	@Autowired
	private DAO<InquiryHousing> inquiryHousingDAO;

	// �⍇���t�H�[���� Factory �N���X
	@Autowired
	private InquiryFormFactory formFactory;
	
	@Autowired
	private InquiryManage inquiryManage;

	
	
	// �O����
	@Before
	public void init() throws ParseException{
		// �⍇�����S���폜
		DAOCriteria criteria = new DAOCriteria();
		this.inquiryHeaderDAO.deleteByFilter(criteria);
		this.inquiryDtlInfoDAO.deleteByFilter(criteria);
	}
	
	
	
	/**
	 * �p�����[�^�œn���ꂽ Form �̏��Ŗ⍇������V�K�ǉ��i���⍇���e���CD �P�j<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>form�̒l�œo�^�ł��鎖</li>
	 *     <li>���⍇���w�b�_�̂��⍇���敪�� 00 �ł��鎖</li>
	 *     <li>���⍇���w�b�_�̂��⍇�������ɃV�X�e�����t���ݒ肳��Ă��鎖</li>
	 *     <li>���⍇���w�b�_�̑Ή��X�e�[�^�X�� 0 �ł��鎖</li>
	 *     <li>���⍇���w�b�_�̑Ή����e�A�J�����A�J���ҁA�Ή��������A�΍R�����҂� null �ł��鎖</li>
	 *     <li>�}�C�y�[�W���[�U�[ID ���w�肳��Ă���ꍇ�A���⍇���w�b�_�̃��[�U�[ID ���ݒ肳��鎖</li>
	 * </ul>
	 */
	@Test
	public void addInquiryTest1() throws Exception {
		// ���N�G�X�g�p�����[�^���擾���� Form �I�u�W�F�N�g���쐬����B
		HousingInquiryForm inquiryForm = this.formFactory.createHousingInquiryForm();
		InquiryHeaderForm inquiryHeaderForm = this.formFactory.createInquiryHeaderForm();

		// ����(��)��ݒ�
		inquiryHeaderForm.setLname("�R��");
		// ����(��)��ݒ�
		inquiryHeaderForm.setFname("�O");
		// �����E�J�i(��)��ݒ�
		inquiryHeaderForm.setLnameKana("���}�N�`");
		// �����E�J�i(��)��ݒ�
		inquiryHeaderForm.setFnameKana("�J�I��");
		// ���[���A�h���X��ݒ�
		inquiryHeaderForm.setEmail("yamakuchi@163.com");
		// �d�b�ԍ���ݒ�
		inquiryHeaderForm.setTel("12345678");
		// ���⍇�����e��ݒ�
		inquiryHeaderForm.setInquiryText("���⍇�����etest");
		inquiryHeaderForm.setInquiryDtlType(new String[]{"1"});

		inquiryForm.setCommonInquiryForm(inquiryHeaderForm);
		// �V�X�e�������ԍ���ݒ�
		inquiryForm.setSysHousingCd(new String[]{"HOU000005"});

		String inquiryId = inquiryManage.addInquiry(inquiryForm, "Test1", "Test2");

		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("inquiryId", inquiryId);
		List<InquiryHeader> inquiryHeaders = inquiryHeaderDAO.selectByFilter(criteria);

		List<InquiryDtlInfo> inquiryDtlInfos = inquiryDtlInfoDAO.selectByFilter(criteria);

		Assert.assertEquals("���⍇���w�b�_�̂��⍇���敪�� 00 �ł��鎖", "00", inquiryHeaders.get(0).getInquiryType());
		Assert.assertEquals("���⍇���w�b�_�̃��[�U�[ID�ɁAMyPage ���[�U�[ID�@���ݒ肳��Ă��鎖", "Test1", inquiryHeaders.get(0).getUserId());
		Assert.assertEquals("���⍇���w�b�_�̎����i���j�ɁAForm �̎����i���j���ݒ肳��Ă��鎖", inquiryHeaderForm.getLname(), inquiryHeaders.get(0).getLname());
		Assert.assertEquals("���⍇���w�b�_�̎����i���j�ɁAForm �̎����i���j���ݒ肳��Ă��鎖", inquiryHeaderForm.getFname(), inquiryHeaders.get(0).getFname());
		Assert.assertEquals("���⍇���w�b�_�̎����E�J�i�i���j�ɁAForm �̎����E�J�i�i���j���ݒ肳��Ă��鎖", inquiryHeaderForm.getLnameKana(), inquiryHeaders.get(0).getLnameKana());
		Assert.assertEquals("���⍇���w�b�_�̎����E�J�i�i���j�ɁAForm �̎����E�J�i�i���j���ݒ肳��Ă��鎖", inquiryHeaderForm.getFnameKana(), inquiryHeaders.get(0).getFnameKana());
		Assert.assertEquals("���⍇���w�b�_�̃��[���A�h���X�ɁAForm �̃��[���A�h���X���ݒ肳��Ă��鎖", inquiryHeaderForm.getEmail(), inquiryHeaders.get(0).getEmail());
		Assert.assertEquals("���⍇���w�b�_�� TEL �ɁAForm �� TEL ���ݒ肳��Ă��鎖", inquiryHeaderForm.getTel(), inquiryHeaders.get(0).getTel());
		Assert.assertEquals("���⍇���w�b�_�̂��⍇�����e�ɁAForm ���⍇���Ȃ��悤���ݒ肳��Ă��鎖", inquiryHeaderForm.getInquiryText(), inquiryHeaders.get(0).getInquiryText());

		// ���⍇�������́A���t���̂݃`�F�b�N
		String now = StringUtils.dateToString(new Date());
		String date = StringUtils.dateToString(inquiryHeaders.get(0).getInquiryDate());
		Assert.assertEquals("���⍇���w�b�_�̂��⍇�������ɁA�V�X�e�����t���ݒ肳��Ă��鎖", now, date);

		Assert.assertEquals("���⍇���w�b�_�̑Ή��X�e�[�^�X�� 0 �ł��鎖", "0", inquiryHeaders.get(0).getAnswerStatus());
		Assert.assertNull("���⍇���w�b�_�̑Ή����e�� null �ł��鎖", inquiryHeaders.get(0).getAnswerText());
		Assert.assertNull("���⍇���w�b�_�̊J������ null �ł��鎖", inquiryHeaders.get(0).getOpenDate());
		Assert.assertNull("���⍇���w�b�_�̊J���҂� null �ł��鎖", inquiryHeaders.get(0).getOpenUserId());
		Assert.assertNull("���⍇���w�b�_�̑Ή��������� null �ł��鎖", inquiryHeaders.get(0).getAnswerCompDate());
		Assert.assertNull("���⍇���w�b�_�̑Ή������҂� null �ł��鎖", inquiryHeaders.get(0).getAnswerCompUserId());
		
		Assert.assertEquals("form�̒l�œo�^�ł��鎖", inquiryDtlInfos.get(0).getInquiryId(), inquiryId);
		Assert.assertEquals("form�̒l�œo�^�ł��鎖", inquiryDtlInfos.get(0).getInquiryDtlType(), inquiryHeaderForm.getInquiryDtlType()[0]);
		
	}


	
	/**
	 * �p�����[�^�œn���ꂽ Form �̏��Ŗ⍇������V�K�ǉ��i���⍇���e���CD �P�j<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�}�C�y�[�W���[�U�[ID ���w�肳��Ă��Ȃ��ꍇ�A���⍇���w�b�_�̃��[�U�[ID �� null �ł��鎖</li>
	 * </ul>
	 */
	@Test
	public void addInquiryTest2() throws Exception {
		// ���N�G�X�g�p�����[�^���擾���� Form �I�u�W�F�N�g���쐬����B
		HousingInquiryForm inquiryForm = this.formFactory.createHousingInquiryForm();
		InquiryHeaderForm inquiryHeaderForm = this.formFactory.createInquiryHeaderForm();

		// ����(��)��ݒ�
		inquiryHeaderForm.setLname("�R��");
		// ����(��)��ݒ�
		inquiryHeaderForm.setFname("�O");
		// �����E�J�i(��)��ݒ�
		inquiryHeaderForm.setLnameKana("���}�N�`");
		// �����E�J�i(��)��ݒ�
		inquiryHeaderForm.setFnameKana("�J�I��");
		// ���[���A�h���X��ݒ�
		inquiryHeaderForm.setEmail("yamakuchi@163.com");
		// �d�b�ԍ���ݒ�
		inquiryHeaderForm.setTel("12345678");
		// ���⍇�����e��ݒ�
		inquiryHeaderForm.setInquiryText("���⍇�����etest");
		inquiryHeaderForm.setInquiryDtlType(new String[]{"1"});

		inquiryForm.setCommonInquiryForm(inquiryHeaderForm);
		// �V�X�e�������ԍ���ݒ�
		inquiryForm.setSysHousingCd(new String[]{"HOU000005"});


		String inquiryId = inquiryManage.addInquiry(inquiryForm, null, "Test2");

		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("inquiryId", inquiryId);
		List<InquiryHeader> inquiryHeaders = inquiryHeaderDAO.selectByFilter(criteria);

		List<InquiryDtlInfo> inquiryDtlInfos = inquiryDtlInfoDAO.selectByFilter(criteria);

		Assert.assertEquals("form�̒l�œo�^�ł��鎖", inquiryHeaders.get(0).getInquiryId(), inquiryId);
		Assert.assertEquals("form�̒l�œo�^�ł��鎖", inquiryHeaders.get(0).getInquiryText(), inquiryHeaderForm.getInquiryText());
		Assert.assertNull("MyPage ���[�U�[ID�@�� null �ł��鎖", inquiryHeaders.get(0).getUserId());

		Assert.assertEquals("form�̒l�œo�^�ł��鎖", inquiryDtlInfos.get(0).getInquiryId(), inquiryId);
		Assert.assertEquals("form�̒l�œo�^�ł��鎖", inquiryDtlInfos.get(0).getInquiryDtlType(), inquiryHeaderForm.getInquiryDtlType()[0]);
		
	}

	
	
	/**
	 * �p�����[�^�œn���ꂽ Form �̏��Ŗ⍇������V�K�ǉ��i���⍇���e���CD �������j<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�����̂��⍇���e���CD ���o�^�ł��鎖</li>
	 *     <li>�������⍇���e���CD �͖�������鎖</li>
	 * </ul>
	 */
	@Test
	public void addInquiryMultiTest() throws Exception {
		// ���N�G�X�g�p�����[�^���擾���� Form �I�u�W�F�N�g���쐬����B
		HousingInquiryForm inquiryForm = this.formFactory.createHousingInquiryForm();
		InquiryHeaderForm inquiryHeaderForm = this.formFactory.createInquiryHeaderForm();

		// ����(��)��ݒ�
		inquiryHeaderForm.setLname("�R��");
		// ����(��)��ݒ�
		inquiryHeaderForm.setFname("�O");
		// �����E�J�i(��)��ݒ�
		inquiryHeaderForm.setLnameKana("���}�N�`");
		// �����E�J�i(��)��ݒ�
		inquiryHeaderForm.setFnameKana("�J�I��");
		// ���[���A�h���X��ݒ�
		inquiryHeaderForm.setEmail("yamakuchi@163.com");
		// �d�b�ԍ���ݒ�
		inquiryHeaderForm.setTel("12345678");
		// ���⍇�����e��ݒ�
		inquiryHeaderForm.setInquiryText("���⍇�����etest");
		// ���⍇�����e���CD�@�i�����j
		inquiryHeaderForm.setInquiryDtlType(new String[]{"1", "1", "2"});

		inquiryForm.setCommonInquiryForm(inquiryHeaderForm);
		// �V�X�e�������ԍ���ݒ�
		inquiryForm.setSysHousingCd(new String[]{"HOU000004"});


		String inquiryId = inquiryManage.addInquiry(inquiryForm, "Test", "Test");


		// ���⍇���w�b�_�̊m�F
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("inquiryId", inquiryId);
		List<InquiryHeader> inquiryHeaders = inquiryHeaderDAO.selectByFilter(criteria);

		Assert.assertEquals("form�̒l�œo�^�ł��鎖", inquiryHeaders.get(0).getInquiryId(), inquiryId);
		Assert.assertEquals("form�̒l�œo�^�ł��鎖", inquiryHeaders.get(0).getInquiryText(), inquiryHeaderForm.getInquiryText());


		// ���⍇�����e��ʏ��̊m�F
		criteria.addOrderByClause("inquiryDtlType");
		List<InquiryDtlInfo> inquiryDtlInfos = inquiryDtlInfoDAO.selectByFilter(criteria);

		Assert.assertEquals("�d���������⍇�����e��ʂ��r������Ă��鎖", 2, inquiryDtlInfos.size());
		Assert.assertEquals("�d���������⍇�����e��ʂ��r������Ă��鎖", inquiryDtlInfos.get(0).getInquiryDtlType(), inquiryHeaderForm.getInquiryDtlType()[0]);
		Assert.assertEquals("�d���������⍇�����e��ʂ��r������Ă��鎖", inquiryDtlInfos.get(1).getInquiryDtlType(), inquiryHeaderForm.getInquiryDtlType()[2]);


		// �����⍇�����̊m�F
		criteria = new DAOCriteria();
		criteria.addWhereClause("inquiryId", inquiryId);
		List<InquiryHousing> inquiryHousings = inquiryHousingDAO.selectByFilter(criteria);

		Assert.assertEquals("�����₢���킹���̌�������������", 1, inquiryHousings.size());
		Assert.assertEquals("form �̒l�ŕ����⍇����񂪓o�^����Ă��鎖", "HOU000004", inquiryHousings.get(0).getSysHousingCd());

	}

	
	
	/**
	 * �p�����[�^�œn���ꂽ Form �̏��Ŗ⍇������V�K�ǉ��i���⍇���e���CD �� null �̏ꍇ�j<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>���e���CD �� null �ł��A���⍇���w�b�_��񂪓o�^�ł��鎖</li>
	 *     <li>���e���CD �� null �̏ꍇ�A���⍇�����e��ʏ��̃f�[�^����ł��鎖</li>
	 *     <li>���e���CD �� null �ł��A���⍇��������񂪓o�^�ł��鎖</li>
	 * </ul>
	 */
	@Test
	public void addInquiryNonDtlTest1() throws Exception {
		// ���N�G�X�g�p�����[�^���擾���� Form �I�u�W�F�N�g���쐬����B
		HousingInquiryForm inquiryForm = this.formFactory.createHousingInquiryForm();
		InquiryHeaderForm inquiryHeaderForm = this.formFactory.createInquiryHeaderForm();

		// ����(��)��ݒ�
		inquiryHeaderForm.setLname("�R��");
		// ����(��)��ݒ�
		inquiryHeaderForm.setFname("�O");
		// �����E�J�i(��)��ݒ�
		inquiryHeaderForm.setLnameKana("���}�N�`");
		// �����E�J�i(��)��ݒ�
		inquiryHeaderForm.setFnameKana("�J�I��");
		// ���[���A�h���X��ݒ�
		inquiryHeaderForm.setEmail("yamakuchi@163.com");
		// �d�b�ԍ���ݒ�
		inquiryHeaderForm.setTel("12345678");
		// ���⍇�����e��ݒ�
		inquiryHeaderForm.setInquiryText("���⍇�����etest");
		// ���⍇�����e���CD�@�inull�j
		inquiryHeaderForm.setInquiryDtlType(null);

		inquiryForm.setCommonInquiryForm(inquiryHeaderForm);
		// �V�X�e�������ԍ���ݒ�
		inquiryForm.setSysHousingCd(new String[]{"HOU000001"});

		String inquiryId = inquiryManage.addInquiry(inquiryForm, "Test", "Test");


		// ���⍇���w�b�_�̊m�F
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("inquiryId", inquiryId);
		List<InquiryHeader> inquiryHeaders = inquiryHeaderDAO.selectByFilter(criteria);

		Assert.assertEquals("form�̒l�œo�^�ł��鎖", inquiryHeaders.get(0).getInquiryId(), inquiryId);
		Assert.assertEquals("form�̒l�œo�^�ł��鎖", inquiryHeaders.get(0).getInquiryText(), inquiryHeaderForm.getInquiryText());

		
		// ���⍇�����e��ʏ��̊m�F
		criteria.addOrderByClause("inquiryDtlType");
		List<InquiryDtlInfo> inquiryDtlInfos = inquiryDtlInfoDAO.selectByFilter(criteria);

		Assert.assertEquals("���e���CD �� null �̏ꍇ�A���⍇�����e��ʏ��̃f�[�^����ł��鎖", 0, inquiryDtlInfos.size());

		
		// �����⍇�����̊m�F
		criteria = new DAOCriteria();
		criteria.addWhereClause("inquiryId", inquiryId);
		List<InquiryHousing> inquiryHousings = inquiryHousingDAO.selectByFilter(criteria);

		Assert.assertEquals("�����₢���킹���̌�������������", 1, inquiryHousings.size());
		Assert.assertEquals("form �̒l�ŕ����⍇����񂪓o�^����Ă��鎖", "HOU000001", inquiryHousings.get(0).getSysHousingCd());

	}

	
	
	/**
	 * �p�����[�^�œn���ꂽ Form �̏��Ŗ⍇������V�K�ǉ��i���⍇���e���CD �� ��̔z��j<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>���e���CD ����̔z��ł��A���⍇���w�b�_��񂪓o�^�ł��鎖</li>
	 *     <li>���e���CD ����̔z��̏ꍇ�A���⍇�����e��ʏ��̃f�[�^����ł��鎖</li>
	 *     <li>���e���CD ����̔z��ł��A���⍇��������񂪓o�^�ł��鎖</li>
	 * </ul>
	 */
	@Test
	public void addInquiryNonDtlTest2() throws Exception {
		// ���N�G�X�g�p�����[�^���擾���� Form �I�u�W�F�N�g���쐬����B
		HousingInquiryForm inquiryForm = this.formFactory.createHousingInquiryForm();
		InquiryHeaderForm inquiryHeaderForm = this.formFactory.createInquiryHeaderForm();

		// ����(��)��ݒ�
		inquiryHeaderForm.setLname("�R��");
		// ����(��)��ݒ�
		inquiryHeaderForm.setFname("�O");
		// �����E�J�i(��)��ݒ�
		inquiryHeaderForm.setLnameKana("���}�N�`");
		// �����E�J�i(��)��ݒ�
		inquiryHeaderForm.setFnameKana("�J�I��");
		// ���[���A�h���X��ݒ�
		inquiryHeaderForm.setEmail("yamakuchi@163.com");
		// �d�b�ԍ���ݒ�
		inquiryHeaderForm.setTel("12345678");
		// ���⍇�����e��ݒ�
		inquiryHeaderForm.setInquiryText("���⍇�����etest");
		// ���⍇�����e���CD �i��̔z��j
		inquiryHeaderForm.setInquiryDtlType(new String[0]);

		inquiryForm.setCommonInquiryForm(inquiryHeaderForm);
		// �V�X�e�������ԍ���ݒ�
		inquiryForm.setSysHousingCd(new String[]{"HOU000002"});


		String inquiryId = inquiryManage.addInquiry(inquiryForm, "Test", "Test");


		// ���⍇���w�b�_�̊m�F
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("inquiryId", inquiryId);
		List<InquiryHeader> inquiryHeaders = inquiryHeaderDAO.selectByFilter(criteria);

		Assert.assertEquals("form�̒l�œo�^�ł��鎖", inquiryHeaders.get(0).getInquiryId(), inquiryId);
		Assert.assertEquals("form�̒l�œo�^�ł��鎖", inquiryHeaders.get(0).getInquiryText(), inquiryHeaderForm.getInquiryText());
		
		
		// ���⍇�����e��ʏ��̊m�F
		criteria.addOrderByClause("inquiryDtlType");
		List<InquiryDtlInfo> inquiryDtlInfos = inquiryDtlInfoDAO.selectByFilter(criteria);

		Assert.assertEquals("���e���CD ����̔z��̏ꍇ�A���⍇�����e��ʏ��̃f�[�^����ł��鎖", 0, inquiryDtlInfos.size());


		// �����⍇�����̊m�F
		criteria = new DAOCriteria();
		criteria.addWhereClause("inquiryId", inquiryId);
		List<InquiryHousing> inquiryHousings = inquiryHousingDAO.selectByFilter(criteria);

		Assert.assertEquals("�����₢���킹���̌�������������", 1, inquiryHousings.size());
		Assert.assertEquals("form �̒l�ŕ����⍇����񂪓o�^����Ă��鎖", "HOU000002", inquiryHousings.get(0).getSysHousingCd());

	}


	
	/**
	 * �p�����[�^�œn���ꂽ Form �̏��Ŗ⍇������V�K�ǉ��i���⍇���e���CD �z������󕶎���j<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>���e���CD ���󕶎���ł��A���⍇���w�b�_��񂪓o�^�ł��鎖</li>
	 *     <li>���e���CD ���󕶎���̏ꍇ�A���⍇�����e��ʏ��̃f�[�^����ł��鎖</li>
	 *     <li>���e���CD ���󕶎���ł��A���⍇��������񂪓o�^�ł��鎖</li>
	 * </ul>
	 */
	@Test
	public void addInquiryNonDtlTest3() throws Exception {
		// ���N�G�X�g�p�����[�^���擾���� Form �I�u�W�F�N�g���쐬����B
		HousingInquiryForm inquiryForm = this.formFactory.createHousingInquiryForm();
		InquiryHeaderForm inquiryHeaderForm = this.formFactory.createInquiryHeaderForm();

		// ����(��)��ݒ�
		inquiryHeaderForm.setLname("�R��");
		// ����(��)��ݒ�
		inquiryHeaderForm.setFname("�O");
		// �����E�J�i(��)��ݒ�
		inquiryHeaderForm.setLnameKana("���}�N�`");
		// �����E�J�i(��)��ݒ�
		inquiryHeaderForm.setFnameKana("�J�I��");
		// ���[���A�h���X��ݒ�
		inquiryHeaderForm.setEmail("yamakuchi@163.com");
		// �d�b�ԍ���ݒ�
		inquiryHeaderForm.setTel("12345678");
		// ���⍇�����e��ݒ�
		inquiryHeaderForm.setInquiryText("���⍇�����etest");
		// ���⍇�����e���CD �i�󕶎���j
		inquiryHeaderForm.setInquiryDtlType(new String[]{""});

		inquiryForm.setCommonInquiryForm(inquiryHeaderForm);
		// �V�X�e�������ԍ���ݒ�
		inquiryForm.setSysHousingCd(new String[]{"HOU000003"});


		String inquiryId = inquiryManage.addInquiry(inquiryForm, "Test", "Test");

		
		// ���⍇���w�b�_�̊m�F
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("inquiryId", inquiryId);
		List<InquiryHeader> inquiryHeaders = inquiryHeaderDAO.selectByFilter(criteria);

		Assert.assertEquals("form�̒l�œo�^�ł��鎖", inquiryHeaders.get(0).getInquiryId(), inquiryId);
		Assert.assertEquals("form�̒l�œo�^�ł��鎖", inquiryHeaders.get(0).getInquiryText(), inquiryHeaderForm.getInquiryText());


		// ���⍇�����e��ʏ��̊m�F
		criteria.addOrderByClause("inquiryDtlType");
		List<InquiryDtlInfo> inquiryDtlInfos = inquiryDtlInfoDAO.selectByFilter(criteria);

		Assert.assertEquals("�d���������⍇�����e��ʂ��r������Ă��鎖", 0, inquiryDtlInfos.size());


		// �����⍇�����̊m�F
		criteria = new DAOCriteria();
		criteria.addWhereClause("inquiryId", inquiryId);
		List<InquiryHousing> inquiryHousings = inquiryHousingDAO.selectByFilter(criteria);

		Assert.assertEquals("�����₢���킹���̌�������������", 1, inquiryHousings.size());
		Assert.assertEquals("form �̒l�ŕ����⍇����񂪓o�^����Ă��鎖", "HOU000003", inquiryHousings.get(0).getSysHousingCd());
	}

}
