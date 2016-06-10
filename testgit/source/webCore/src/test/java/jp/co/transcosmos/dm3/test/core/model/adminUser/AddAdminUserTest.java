package jp.co.transcosmos.dm3.test.core.model.adminUser;

import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import jp.co.transcosmos.dm3.core.model.AdminUserManage;
import jp.co.transcosmos.dm3.core.model.adminUser.form.AdminUserForm;
import jp.co.transcosmos.dm3.core.model.adminUser.form.AdminUserFormFactory;
import jp.co.transcosmos.dm3.core.model.exception.DuplicateException;
import jp.co.transcosmos.dm3.core.vo.AdminLoginInfo;
import jp.co.transcosmos.dm3.core.vo.AdminRoleInfo;
import jp.co.transcosmos.dm3.dao.DAO;
import jp.co.transcosmos.dm3.dao.DAOCriteria;
import jp.co.transcosmos.dm3.utils.EncodingUtils;
import jp.co.transcosmos.dm3.utils.StringUtils;


/**
 * �Ǘ����[�U�[ model �V�K�o�^�e�X�g�P�[�X
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationContext-test.xml")
public class AddAdminUserTest {

	@Autowired
	private AdminUserManage adminUserManage;
	@Autowired
	private AdminUserFormFactory formFactory;
	@Autowired
	private DAO<AdminLoginInfo> adminLoginInfoDAO;
	@Autowired
	private DAO<AdminRoleInfo> adminRoleInfoDAO;



	@Before
	public void init(){
		// �Ǘ����[�U�[�S���폜
		DAOCriteria criteria = new DAOCriteria();
		this.adminLoginInfoDAO.deleteByFilter(criteria);
		this.adminRoleInfoDAO.deleteByFilter(criteria);
	}



	/**
	 * �Ǘ����[�U�[�ǉ��e�X�g�i����n�j<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�߂�l���o�^�f�[�^�̃��O�C��ID �ƈ�v���鎖</li>
	 *     <li>�w�肵�����e�ŊǗ��҃��O�C����񂪒ǉ�����Ă��鎖</li>
	 *     <li>�w�肵�����e�ŊǗ��Ҍ�����񂪒ǉ�����Ă��鎖</li>
	 * </ul>
	 */
	@Test
	public void addOkTest() throws Exception {
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
		inputForm.setNote("���l");

		// �e�X�g�Ώۃ��\�b�h���s
		String ret = this.adminUserManage.addAdminUser(inputForm, "testEdiId");


		// �f�[�^�m�F�i�Ǘ��҃��O�C��ID���j
		List<AdminLoginInfo> list = this.adminLoginInfoDAO.selectByFilter(null);

		Assert.assertEquals("�o�^��������������", 1, list.size());
		AdminLoginInfo admin = (AdminLoginInfo)list.get(0);


		// �߂�l�ƁA�o�^���ꂽ���O�C��ID ����v���鎖
		Assert.assertEquals("�o�^���̖߂�l�ƃ��O�C��ID ����v���鎖", ret, admin.getUserId());
		
		
		// ���[�U�[�h�c�́A�����ƁA�擪�P�����̂ݕ]��
		Assert.assertEquals("���[�U�[�h�c���̔Ԃ���Ă��鎖�i�擪�P�����`�F�b�N�j", "A", admin.getAdminUserId().substring(0, 1));
		Assert.assertEquals("���[�U�[�h�c���̔Ԃ���Ă��鎖�i�������`�F�b�N�j", 20, admin.getAdminUserId().length());

		// ���O�C��ID
		Assert.assertEquals("���O�C��ID ����������", "UID0001", admin.getLoginId());
		// �p�X���[�h
		Assert.assertEquals("�p�X���[�h�̃n�b�V�������l����������", EncodingUtils.md5Encode("PWD0001"), admin.getPassword());
		// ���[�U�[��
		Assert.assertEquals("���[�U�[������������", "���[�U�[���P", admin.getUserName());
		// ���[���A�h���X
		Assert.assertEquals("���[���A�h���X����������", "test1@localhost", admin.getEmail());
		// ���O�C�����s��
		Assert.assertEquals("���O�C�����s�񐔂� 0 �ł��鎖", 0, (int)admin.getFailCnt());
		// �ŏI���O�C�����s��
		Assert.assertNull("�ŏI���O�C�����s���� null �ł��鎖", admin.getLastFailDate());
		// �O�񃍃O�C����
		Assert.assertNull("�O�񃍃O�C������ null �ł��鎖", admin.getPreLogin());
		// �ŏI���O�C����
		Assert.assertNull("�ŏI���O�C������ null �ł��鎖", admin.getLastLoginDate());
		// �ŏI�p�X���[�h�ύX���@�i���t���̂݃`�F�b�N�j
		String date = StringUtils.dateToString(admin.getLastPasswdChange());
		String now = StringUtils.dateToString(new Date());
		Assert.assertEquals("�ŏI�p�X���[�h�ύX���������ł��鎖", now, date);
		// ���l
		Assert.assertEquals("���l����������", "���l", admin.getNote());
		
		// �o�^��
		Assert.assertEquals("�o�^�҂���������", "testEdiId", admin.getInsUserId());
		// �o�^���i���t���̂݃`�F�b�N�j
		date = StringUtils.dateToString(admin.getInsDate());
		Assert.assertEquals("�o�^������������", now, date);
		// �X�V��
		Assert.assertEquals("�X�V�҂���������", "testEdiId", admin.getUpdUserId());
		// �X�V���i���t���̂݃`�F�b�N�j
		date = StringUtils.dateToString(admin.getUpdDate());
		Assert.assertEquals("�o�^������������", now, date);
		
		
		// �f�[�^�m�F�i�Ǘ��҃��[�����j
		List<AdminRoleInfo> list2 = this.adminRoleInfoDAO.selectByFilter(null);

		Assert.assertEquals("�o�^��������������", 1, list2.size());
		AdminRoleInfo role = (AdminRoleInfo)list2.get(0);

		// ���[�U�[ID
		Assert.assertEquals("�Ǘ��҃��O�C�����ƁA�Ǘ��Ҍ������̃��[�U�[ID ����v���鎖�j", role.getAdminUserId(), admin.getAdminUserId());

		// ���[��ID
		Assert.assertEquals("�Ǘ��Ҍ������̃��[�������������j", "admin", role.getRoleId());

	}



	/**
	 * �Ǘ����[�U�[�ǉ��e�X�g �i���O�C��ID ���d������ꍇ�j<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>DuplicateException�@�̗�O���X���[����鎖</li>
	 * </ul>
	 */
	@Test(expected=DuplicateException.class)
	public void addNgTest() throws Exception {
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
		inputForm.setNote("���l");

		try {
			// �e�X�g�Ώۃ��\�b�h���s
			this.adminUserManage.addAdminUser(inputForm, "testEdiId");
		} catch (Exception e) {
			// �G���[�͖����B�@���̌�A�o�^�����Ń`�F�b�N����B
		}

		// �����f�[�^���o�^����Ă��鎖���`�F�b�N
		List<AdminLoginInfo> list = this.adminLoginInfoDAO.selectByFilter(null);
		Assert.assertEquals("�o�^��������������", 1, list.size());


		// ���O�C��ID ���d�������ēo�^
		inputForm = this.formFactory.createAdminUserForm();

		// ���[��ID
		inputForm.setRoleId("user");

		// ���O�C��ID
		inputForm.setLoginId("UID0001");
		// �p�X���[�h 
		inputForm.setPassword("PWD0002");
		// �p�X���[�h�m�F
		inputForm.setPasswordChk("PWD0002");
		// ���[�U�[��
		inputForm.setUserName("���[�U�[���Q");
		// ���[���A�h���X
		inputForm.setEmail("test2@localhost");
		// ���l
		inputForm.setNote("���l�Q");

		this.adminUserManage.addAdminUser(inputForm, "testEdiId");
		// ��O���X���[����鎖

		Assert.fail("��O���X���[����鎖");
	}
	
}
