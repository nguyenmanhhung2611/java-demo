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
 * �Ǘ����[�U�[ model �폜�e�X�g�P�[�X
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationContext-test.xml")
public class DelAdminUserTest {
	
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
	 * �Ǘ����[�U�[�폜�e�X�g<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�w�肵�����[�U�[�h�c�̊Ǘ��҃��O�C����񂪍폜����Ă��鎖</li>
	 *     <li>�w�肵�����[�U�[�h�c�̊Ǘ��Ҍ�����񂪍폜����Ă��鎖</li>
	 *     <li>�w�肳��Ă��Ȃ����[�U�[�h�c�̊Ǘ��҃��O�C����񂪍폜����Ă��Ȃ���</li>
	 *     <li>�w�肳��Ă��Ȃ����[�U�[�h�c�̊Ǘ��Ҍ�����񂪍폜����Ă��Ȃ���</li>
	 * </ul>
	 */
	@Test
	public void delOkTest() throws Exception {

		// �e�X�g�f�[�^�o�^
		initDate();

		// �e�X�g�f�[�^�m�F
		List<AdminLoginInfo> list = this.adminLoginInfoDAO.selectByFilter(null);
		Assert.assertEquals("�o�^��������������", 2, list.size());

		
		// ���̓f�[�^�쐬
		AdminUserForm inputForm = this.formFactory.createAdminUserForm();
		// �����Ώۃ��[�U�[ID
		inputForm.setUserId(this.targetId);

		// �폜�������s
		this.adminUserManage.delAdminUser(inputForm);

		
		// �X�V���ʂ��m�F
		list = this.adminLoginInfoDAO.selectByFilter(null);
		
		Assert.assertEquals("�Y����������������", 1, list.size());
		

		for (AdminLoginInfo admin : list){
			if (admin.getAdminUserId().equals(this.targetId)){

				// �폜�Ώ����R�[�h�̌��ʊm�F
				Assert.fail("�폜�Ώۃ��R�[�h���폜����Ă��鎖");
			}
		}
		
		
		// �X�V���ʂ��m�F�i�������j
		List<AdminRoleInfo> list2 = this.adminRoleInfoDAO.selectByFilter(null);
		
		Assert.assertEquals("�Y����������������", 1, list.size());
		
		for (AdminRoleInfo role : list2){
			if (role.getAdminUserId().equals(this.targetId)){

				// �폜�Ώ����R�[�h�̌��ʊm�F
				Assert.fail("�폜�Ώۃ��R�[�h���폜����Ă��鎖");
			}
		}

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
		this.adminUserManage.addAdminUser(inputForm, "testEdiId2");

	}

}
