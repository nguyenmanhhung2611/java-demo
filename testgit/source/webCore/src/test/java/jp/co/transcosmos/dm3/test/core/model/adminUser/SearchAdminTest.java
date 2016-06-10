package jp.co.transcosmos.dm3.test.core.model.adminUser;

import java.util.List;

import jp.co.transcosmos.dm3.core.model.AdminUserManage;
import jp.co.transcosmos.dm3.core.model.adminUser.form.AdminUserForm;
import jp.co.transcosmos.dm3.core.model.adminUser.form.AdminUserFormFactory;
import jp.co.transcosmos.dm3.core.model.adminUser.form.AdminUserSearchForm;
import jp.co.transcosmos.dm3.core.vo.AdminLoginInfo;
import jp.co.transcosmos.dm3.core.vo.AdminRoleInfo;
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
 * �Ǘ����[�U�[ model �����n�e�X�g�P�[�X
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationContext-test.xml")
public class SearchAdminTest {

	@Autowired
	private AdminUserManage adminUserManage;
	@Autowired
	private AdminUserFormFactory formFactory;
	@Autowired
	private DAO<AdminLoginInfo> adminLoginInfoDAO;
	@Autowired
	private DAO<AdminRoleInfo> adminRoleInfoDAO;

	// �X�V�Ώۃ��[�U�[�h�c
	private String targetId;

	
	
	@Before
	public void init(){
		// �Ǘ����[�U�[�S���폜
		DAOCriteria criteria = new DAOCriteria();
		this.adminLoginInfoDAO.deleteByFilter(criteria);
		this.adminRoleInfoDAO.deleteByFilter(criteria);
	}

	
	
	/**
	 * �Ǘ����[�U�[���O�C���h�c���p�\�`�F�b�N<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>���o�^�̃��O�C��ID �̏ꍇ�A�`�F�b�N���\�b�h�̖߂�l�� true �ł��鎖</li>
	 *     <li>�o�^�ς̃��O�C��ID �̏ꍇ�A�`�F�b�N���\�b�h�̖߂�l�� false �ł��鎖</li>
	 * </ul>
	 */
	@Test
	public void isFreeLoginIdTest() throws Exception{
		
		// �e�X�g�f�[�^�o�^
		initDate();
		
		// �e�X�g�f�[�^�m�F
		List<AdminLoginInfo> list = this.adminLoginInfoDAO.selectByFilter(null);
		Assert.assertEquals("�o�^��������������", 2, list.size());

		
		// ���݂��郍�O�C��ID �Ńe�X�g�����{
		AdminUserForm inputForm = this.formFactory.createAdminUserForm();
		inputForm.setLoginId("UID0001");
		Assert.assertFalse("�d�l���̃��O�C��ID �̏ꍇ�Afalse �����A����鎖", this.adminUserManage.isFreeLoginId(inputForm));
		

		// ���g�p�̃��O�C��ID �Ńe�X�g�����{
		inputForm.setLoginId("UID0003");
		Assert.assertTrue("���g�p�̃��O�C��ID �̏ꍇ�Atrue �����A����鎖", this.adminUserManage.isFreeLoginId(inputForm));
		
	}
	
	
	
	/**
	 * �Ǘ����[�U�[���������e�X�g<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>���O�C��ID �ɂ�錟��������ɍs���鎖</li>
	 *     <li>���[�U�[�� �ɂ�錟���i������v�j������ɍs���鎖</li>
	 *     <li>���[���A�h���X �ɂ�錟���i������v�j������ɍs���鎖</li>
	 *     <li>���� �ɂ�錟��������ɍs���鎖</li>
	 *     <li>�S�Ă̌��������ݒ�ɂ�錟��������ɍs���鎖</li>
	 * </ul>
	 */
	@Test
	public void searchAdminUser() throws Exception {
		
		// �e�X�g�f�[�^�o�^
		initDate();
		
		// �e�X�g�f�[�^�m�F
		List<AdminLoginInfo> list = this.adminLoginInfoDAO.selectByFilter(null);
		Assert.assertEquals("�o�^��������������", 2, list.size());


		// ����������ݒ�@�i���O�C���h�c�j
		AdminUserSearchForm searchForm = this.formFactory.createUserSearchForm();
		searchForm.setKeyLoginId("UID0001");

		// �������s
		int ret = this.adminUserManage.searchAdminUser(searchForm);
		
		Assert.assertEquals("�Y����������������", 1, ret);
		AdminLoginInfo admin = (AdminLoginInfo) searchForm.getRows().get(0).getItems().get("adminLoginInfo");
		Assert.assertEquals("�擾����Ă���f�[�^����������", "UID0001", admin.getLoginId());

		
		// ����������ݒ�@�i���[�U�[��������v�j
		searchForm = this.formFactory.createUserSearchForm();
		searchForm.setKeyUserName("��2");

		// �������s
		ret = this.adminUserManage.searchAdminUser(searchForm);
		
		Assert.assertEquals("�Y����������������", 1, ret);
		admin = (AdminLoginInfo) searchForm.getRows().get(0).getItems().get("adminLoginInfo");
		Assert.assertEquals("�擾����Ă���f�[�^����������", "UID0002", admin.getLoginId());
		

		// ����������ݒ�@�i���[���A�h���X������v�j
		searchForm = this.formFactory.createUserSearchForm();
		searchForm.setKeyEmail("t1@l");
		
		// �������s
		ret = this.adminUserManage.searchAdminUser(searchForm);
		
		Assert.assertEquals("�Y����������������", 1, ret);
		admin = (AdminLoginInfo) searchForm.getRows().get(0).getItems().get("adminLoginInfo");
		Assert.assertEquals("�擾����Ă���f�[�^����������", "UID0001", admin.getLoginId());


		// ����������ݒ�@�i�����j
		searchForm = this.formFactory.createUserSearchForm();
		searchForm.setKeyRoleId("user");

		// �������s
		ret = this.adminUserManage.searchAdminUser(searchForm);
		
		Assert.assertEquals("�Y����������������", 1, ret);
		admin = (AdminLoginInfo) searchForm.getRows().get(0).getItems().get("adminLoginInfo");
		Assert.assertEquals("�擾����Ă���f�[�^����������", "UID0002", admin.getLoginId());

		
		// ����������ݒ�@�i�S�āj
		searchForm = this.formFactory.createUserSearchForm();
		searchForm.setKeyLoginId("UID0001");
		searchForm.setKeyUserName("���P");
		searchForm.setKeyEmail("t1@l");
		searchForm.setKeyRoleId("admin");

		// �������s
		ret = this.adminUserManage.searchAdminUser(searchForm);
		
		Assert.assertEquals("�Y����������������", 1, ret);
		admin = (AdminLoginInfo) searchForm.getRows().get(0).getItems().get("adminLoginInfo");
		Assert.assertEquals("�擾����Ă���f�[�^����������", "UID0001", admin.getLoginId());

	}

	
	
	/**
	 * �Ǘ����[�U�[��L�[���������e�X�g<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�w�肵�����[�U�[ID �̊Ǘ��҃��O�C����񂪎擾�ł��鎖</li>
	 *     <li>�w�肵�����[�U�[ID �̊Ǘ��Ҍ�����񂪎擾�ł��鎖</li>
	 * </ul>
	 */
	@Test
	public void searchAdminUserPkTest() throws Exception{
		
		// �e�X�g�f�[�^�o�^
		initDate();
		
		// �e�X�g�f�[�^�m�F
		List<AdminLoginInfo> list = this.adminLoginInfoDAO.selectByFilter(null);
		Assert.assertEquals("�o�^��������������", 2, list.size());

		
		// ����������ݒ�
		AdminUserSearchForm searchForm = this.formFactory.createUserSearchForm();
		searchForm.setUserId(this.targetId);;


		// �������s
		JoinResult result = this.adminUserManage.searchAdminUserPk(searchForm);

		// ���ʎ擾
		AdminLoginInfo admin = (AdminLoginInfo)result.getItems().get("adminLoginInfo");
		AdminRoleInfo role = (AdminRoleInfo) result.getItems().get("adminRoleInfo");

		Assert.assertEquals("�擾�����Ǘ��҃��O�C�����̃��[�U�[�h�c����������", this.targetId, admin.getUserId());
		Assert.assertEquals("�擾�����Ǘ��Ҍ������̃��[�U�[�h�c����������", this.targetId, role.getAdminUserId());

	}
	
	
	
	// �e�X�g�f�[�^�쐬
	private void initDate() throws Exception {
		
		AdminUserForm inputForm = this.formFactory.createAdminUserForm();

		// ���[��ID
		inputForm.setRoleId("admin");

		// ���O�C��ID
		inputForm.setLoginId("UID0001");
		// �p�X���[�h 
		inputForm.setPassword("PWD0001");
		// �p�X���[�h�m�F
		inputForm.setPasswordChk("PWD0001");
		// ���[�U�[��
		inputForm.setUserName("���[�U�[���P");
		// ���[���A�h���X
		inputForm.setEmail("test1@localhost");
		// ���l
		inputForm.setNote("���l1");

		// �e�X�g�f�[�^�쐬�i�X�V�Ώہj
		this.targetId = this.adminUserManage.addAdminUser(inputForm, "testEdiId1");

		
		
		inputForm = this.formFactory.createAdminUserForm();

		// ���[��ID
		inputForm.setRoleId("user");

		// ���O�C��ID
		inputForm.setLoginId("UID0002");
		// �p�X���[�h 
		inputForm.setPassword("PWD0002");
		// �p�X���[�h�m�F
		inputForm.setPasswordChk("PWD0002");
		// ���[�U�[��
		inputForm.setUserName("���[�U�[��2");
		// ���[���A�h���X
		inputForm.setEmail("test2@localhost");
		// ���l
		inputForm.setNote("���l2");

		// �e�X�g�f�[�^�쐬�i�X�V�ΏۊO�j
		this.adminUserManage.addAdminUser(inputForm, "testEdiId2");

	}

	
	
}
