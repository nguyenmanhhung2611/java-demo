package jp.co.transcosmos.dm3.test.core.model.adminUser;

import java.util.List;

import jp.co.transcosmos.dm3.core.model.AdminUserManage;
import jp.co.transcosmos.dm3.core.model.adminUser.form.AdminUserForm;
import jp.co.transcosmos.dm3.core.model.adminUser.form.AdminUserFormFactory;
import jp.co.transcosmos.dm3.core.vo.AdminLoginInfo;
import jp.co.transcosmos.dm3.core.vo.AdminRoleInfo;
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
 * �Ǘ����[�U�[ model ���b�N�X�e�[�^�X�̃e�X�g�P�[�X
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationContext-test.xml")
public class LockStatusTest {

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

	// �X�V�Ώۃ��[�U�[�h�c
	private String unTargetId;


	
	@Before
	public void init(){
		// �Ǘ����[�U�[�S���폜
		DAOCriteria criteria = new DAOCriteria();
		this.adminLoginInfoDAO.deleteByFilter(criteria);
		this.adminRoleInfoDAO.deleteByFilter(criteria);
	}

	
	
	/**
	 * �Ǘ����[�U�[�̃��b�N�X�e�[�^�X�ύX�A����уX�e�[�^�X�m�F<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�w�肵�����[�U�[�h�c�̃��b�N�X�e�[�^�X���A�ʏ킩�烍�b�N���ɕύX�ł��鎖</li>
	 *     <li>�w�肵�Ă��Ȃ����[�U�[�h�c�̃��b�N�X�e�[�^�X���A�ʏ킩�烍�b�N���ɕύX����Ă��Ȃ���</li>
	 *     <li>�w�肵�����[�U�[�h�c�̃��b�N�X�e�[�^�X���A���b�N������ʏ�ɕύX�ł��鎖</li>
	 * </ul>
	 */
	@Test
	public void changeLockStatusTest() throws Exception {

		// �e�X�g�f�[�^�o�^
		initDate();

		// �e�X�g�f�[�^�m�F
		List<AdminLoginInfo> list = this.adminLoginInfoDAO.selectByFilter(null);
		Assert.assertEquals("�o�^��������������", 2, list.size());
		
		// �����ΏۃA�J�E���g�����擾
		AdminLoginInfo targetAdmin = this.adminLoginInfoDAO.selectByPK(this.targetId);
		Assert.assertNotNull("�Ώۃe�X�g�f�[�^�����݂��鎖", targetAdmin);
		
		// �����ΏۊO�A�J�E���g�����擾
		AdminLoginInfo unTargetAdmin = this.adminLoginInfoDAO.selectByPK(this.unTargetId);
		Assert.assertNotNull("�ΏۊO�e�X�g�f�[�^�����݂��鎖", unTargetAdmin);
		
		// �X�e�[�^�X���ʏ�ł��鎖���`�F�b�N
		Assert.assertFalse("�Ώۃe�X�g�f�[�^���ʏ�X�e�[�^�X�ł��鎖", this.adminUserManage.isLocked(targetAdmin));
		Assert.assertFalse("�ΏۊO�e�X�g�f�[�^���ʏ�X�e�[�^�X�ł��鎖", this.adminUserManage.isLocked(unTargetAdmin));


		// ���̓f�[�^�쐬
		AdminUserForm inputForm = this.formFactory.createAdminUserForm();
		inputForm.setUserId(this.targetId);

		// �X�e�[�^�X�����b�N��Ԃ֕ύX
		this.adminUserManage.changeLockStatus(inputForm, true, "lockEditId");

		// �A�J�E���g�����擾
		targetAdmin = this.adminLoginInfoDAO.selectByPK(this.targetId);
		unTargetAdmin = this.adminLoginInfoDAO.selectByPK(this.unTargetId);
		
		// ���b�N��Ԃ̊m�F
		Assert.assertTrue("�Ώۃe�X�g�f�[�^�����b�N���ł��鎖", this.adminUserManage.isLocked(targetAdmin));
		Assert.assertFalse("�Ώۑ΃e�X�g�f�[�^���ʏ�ł��鎖", this.adminUserManage.isLocked(unTargetAdmin));

		
		// �X�e�[�^�X��ʏ��Ԃ֕ύX
		this.adminUserManage.changeLockStatus(inputForm, false, "lockEditId");

		// �A�J�E���g�����擾
		targetAdmin = this.adminLoginInfoDAO.selectByPK(this.targetId);
		unTargetAdmin = this.adminLoginInfoDAO.selectByPK(this.unTargetId);
		
		// ���b�N��Ԃ̊m�F
		Assert.assertFalse("�Ώۃe�X�g�f�[�^���ʏ�ł��鎖", this.adminUserManage.isLocked(targetAdmin));
		Assert.assertFalse("�Ώۑ΃e�X�g�f�[�^���ʏ�ł��鎖", this.adminUserManage.isLocked(unTargetAdmin));
		
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
		inputForm.setRoleId("admin");

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
		this.unTargetId = this.adminUserManage.addAdminUser(inputForm, "testEdiId2");

	}

}
