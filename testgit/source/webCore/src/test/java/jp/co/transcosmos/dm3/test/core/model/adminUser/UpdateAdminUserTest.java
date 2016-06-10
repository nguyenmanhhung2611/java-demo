package jp.co.transcosmos.dm3.test.core.model.adminUser;

import java.util.Date;
import java.util.List;

import jp.co.transcosmos.dm3.core.model.AdminUserManage;
import jp.co.transcosmos.dm3.core.model.adminUser.form.AdminUserForm;
import jp.co.transcosmos.dm3.core.model.adminUser.form.AdminUserFormFactory;
import jp.co.transcosmos.dm3.core.model.exception.DuplicateException;
import jp.co.transcosmos.dm3.core.model.adminUser.form.PwdChangeForm;
import jp.co.transcosmos.dm3.core.vo.AdminLoginInfo;
import jp.co.transcosmos.dm3.core.vo.AdminRoleInfo;
import jp.co.transcosmos.dm3.dao.DAO;
import jp.co.transcosmos.dm3.dao.DAOCriteria;
import jp.co.transcosmos.dm3.dao.UpdateExpression;
import jp.co.transcosmos.dm3.dao.UpdateValue;
import jp.co.transcosmos.dm3.utils.DateUtils;
import jp.co.transcosmos.dm3.utils.EncodingUtils;
import jp.co.transcosmos.dm3.utils.StringUtils;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


/**
 * �Ǘ����[�U�[ model �X�V�e�X�g�P�[�X
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationContext-test.xml")
public class UpdateAdminUserTest {

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
	 * �Ǘ����[�U�[�X�V�e�X�g�i�p�X���[�h�ύX����j<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�w�肵�����[�U�[�h�c�̊Ǘ��҃��O�C����񂪍X�V����Ă��鎖</li>
	 *     <li>�w�肵�����[�U�[�h�c�̊Ǘ��Ҍ�����񂪍X�V����Ă��鎖</li>
	 *     <li>�w�肳��Ă��Ȃ����[�U�[�h�c�̊Ǘ��҃��O�C����񂪍X�V����Ă��Ȃ���</li>
	 *     <li>�w�肳��Ă��Ȃ����[�U�[�h�c�̊Ǘ��Ҍ�����񂪍X�V����Ă��Ȃ���</li>
	 *     <li>�p�X���[�h�ύX���������̂ŁA�p�X���[�h���n�b�V�����ꂽ�l�ōX�V����Ă��鎖</li>
	 *     <li>�p�X���[�h�ύX���������̂ŁA�ŏI�p�X���[�h�ύX�����X�V����Ă��鎖</li>
	 * </ul>
	 */
	@Test
	public void updOkTest1() throws Exception {

		// �e�X�g�f�[�^�o�^
		initDate();
		
		// �e�X�g�f�[�^�m�F
		List<AdminLoginInfo> list = this.adminLoginInfoDAO.selectByFilter(null);
		Assert.assertEquals("�o�^��������������", 2, list.size());

		
		// �o�^���A�ŏI�X�V���A�ŏI���O�C�����̍X�V�`�F�b�N�p�Ƀf�[�^�� UPDATE
		Date day30 = DateUtils.addDays(new Date(), -30);
		UpdateExpression[] updateExpression
			= new UpdateExpression[] {new UpdateValue("lastPwdChangeDate", day30),
									  new UpdateValue("insDate", day30),
									  new UpdateValue("updDate", day30)};
		this.adminLoginInfoDAO.updateByCriteria(new DAOCriteria(), updateExpression);
		
		
		// ���̓f�[�^�쐬
		AdminUserForm inputForm = this.formFactory.createAdminUserForm();
		
		// ���[��ID
		inputForm.setRoleId("user");

		// �����Ώۃ��[�U�[ID
		inputForm.setUserId(this.targetId);

		// ���O�C��ID
		inputForm.setLoginId("NEWUID");
		// �p�X���[�h
		inputForm.setPassword("NEWPWD");
		// �p�X���[�h�m�F
		inputForm.setPasswordChk("NEWPWD");
		// ���[�U�[��
		inputForm.setUserName("NEWUSER");
		// ���[���A�h���X
		inputForm.setEmail("NEWMAIL");
		// ���l
		inputForm.setNote("NEWNOTE");


		// �X�V�������s
		this.adminUserManage.updateAdminUser(inputForm, "newTestEdiId");

		// �X�V���ʂ��m�F
		list = this.adminLoginInfoDAO.selectByFilter(null);

		for (AdminLoginInfo admin : list){
			if (admin.getAdminUserId().equals(this.targetId)){

				// �X�V�Ώۃ��R�[�h�̌��ʊm�F
				// ���O�C��ID
				Assert.assertEquals("���O�C��ID ���X�V����Ă��鎖", "NEWUID", admin.getLoginId());
				// �p�X���[�h�i�n�b�V���l�j
				Assert.assertEquals("�p�X���[�h�i�n�b�V���l�j���X�V����Ă��鎖", EncodingUtils.md5Encode("NEWPWD"), admin.getPassword());
				// ���[�U�[����
				Assert.assertEquals("���[�U�[���̂��X�V����Ă��鎖", "NEWUSER", admin.getUserName());
				// ���[���A�h���X
				Assert.assertEquals("���[���A�h���X���X�V����Ă��鎖", "NEWMAIL", admin.getEmail());
				// ���O�C�����s��
				Assert.assertEquals("���O�C�����s�񐔂��X�V����Ă��Ȃ���", 0, (int)admin.getFailCnt());
				// �ŏI���O�C�����s��
				Assert.assertNull("���O�C�����s�����X�V����Ă��Ȃ���", admin.getLastFailDate());
				// �O�񃍃O�C����
				Assert.assertNull("�O�񃍃O�C�������X�V����Ă��Ȃ���", admin.getPreLogin());
				// �ŏI���O�C����
				Assert.assertNull("�ŏI���O�C�������X�V����Ă��Ȃ���", admin.getLastLogin());
				// �ŏI�p�X���[�h�ύX���@�i���t���̂݃`�F�b�N�j
				String date = StringUtils.dateToString(admin.getLastPasswdChange());
				String now = StringUtils.dateToString(new Date());
				Assert.assertEquals("�ŏI�p�X���[�h�ύX�����X�V����Ă��鎖", now, date);
				// ���l
				Assert.assertEquals("���l���X�V����Ă��鎖", "NEWNOTE", admin.getNote());
				// �o�^���@�i���t���̂݃`�F�b�N�j
				date = StringUtils.dateToString(admin.getInsDate());
				String day30Str = StringUtils.dateToString(day30);
				Assert.assertEquals("�o�^�����X�V����Ă��Ȃ���", day30Str, date);
				// �o�^��
				Assert.assertEquals("�o�^�҂��X�V����Ă��Ȃ���", "testEdiId1", admin.getInsUserId());
				// �ŏI�X�V���@�i���t���̂݃`�F�b�N�j
				date = StringUtils.dateToString(admin.getUpdDate());
				Assert.assertEquals("�X�V�����X�V����Ă��鎖", now, date);
				// �ŏI�X�V��
				admin.getUpdUserId();
				Assert.assertEquals("�X�V�҂��X�V����Ă鎖", "newTestEdiId", admin.getUpdUserId());

			} else {

				// �X�V�Ώۃ��R�[�h�̌��ʊm�F
				// ���O�C��ID
				Assert.assertEquals("���O�C��ID ���X�V����Ă��Ȃ���", "UID0002", admin.getLoginId());
				// �p�X���[�h�i�n�b�V���l�j
				Assert.assertEquals("�p�X���[�h�i�n�b�V���l�j���X�V����Ă��Ȃ���", EncodingUtils.md5Encode("PWD0002"), admin.getPassword());
				// ���[�U�[����
				Assert.assertEquals("���[�U�[�����X�V����Ă��Ȃ���", "���[�U�[��2", admin.getUserName());
				// ���[���A�h���X
				Assert.assertEquals("���[���A�h���X���X�V����Ă��Ȃ���", "test2@localhost", admin.getEmail());
				// ���O�C�����s��
				Assert.assertEquals("���O�C�����s�񐔂��X�V����Ă��Ȃ���", 0, (int)admin.getFailCnt());
				// �ŏI���O�C�����s��
				Assert.assertNull("���O�C�����s�����X�V����Ă��Ȃ���", admin.getLastFailDate());
				// �O�񃍃O�C����
				Assert.assertNull("�O�񃍃O�C�������X�V����Ă��Ȃ���", admin.getPreLogin());
				// �ŏI���O�C����
				Assert.assertNull("�ŏI���O�C�������X�V����Ă��Ȃ���", admin.getLastLogin());
				// �ŏI�p�X���[�h�ύX��
				String date = StringUtils.dateToString(admin.getLastPwdChangeDate());
				String day30Str = StringUtils.dateToString(day30);
				Assert.assertEquals("�ŏI�p�X���[�h�ύX�����X�V����Ă��Ȃ���", day30Str, date);
				// ���l
				Assert.assertEquals("���l���X�V����Ă��Ȃ���", "���l2", admin.getNote());
				// �o�^���@�i���t���̂݃`�F�b�N�j
				date = StringUtils.dateToString(admin.getInsDate());
				Assert.assertEquals("�o�^�����X�V����Ă��Ȃ���", day30Str, date);
				// �o�^��
				Assert.assertEquals("�o�^�҂��X�V����Ă��Ȃ���", "testEdiId2", admin.getInsUserId());
				// �ŏI�X�V��
				date = StringUtils.dateToString(admin.getUpdDate());
				Assert.assertEquals("�X�V�����X�V����Ă��Ȃ���", day30Str, date);
				// �ŏI�X�V��
				Assert.assertEquals("�X�V�҂��X�V����Ă��Ȃ���", "testEdiId2", admin.getUpdUserId());

			}
		}
		
		
		// �X�V���ʂ��m�F�@�i�������j
		List<AdminRoleInfo> list2 = this.adminRoleInfoDAO.selectByFilter(null);

		for (AdminRoleInfo role : list2){
			if (role.getAdminUserId().equals(this.targetId)){
				// �X�V�Ώۃ��R�[�h
				// ���[��ID
				Assert.assertEquals("���[��ID ���X�V����Ă��鎖", "user", role.getRoleId());

			} else {
				// �X�V�ΏۊO���R�[�h
				// ���[��ID
				Assert.assertEquals("���[��ID ���X�V����Ă��Ȃ���", "admin", role.getRoleId());
				
			}
			
		}
	}



	/**
	 * �Ǘ����[�U�[�X�V�e�X�g�i�p�X���[�h�ύX�Ȃ��j<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�w�肵�����[�U�[�h�c�̊Ǘ��҃��O�C����񂪍X�V����Ă��鎖</li>
	 *     <li>�p�X���[�h�ύX���Ȃ������̂ŁA�p�X���[�h���X�V����Ă��Ȃ���</li>
	 *     <li>�p�X���[�h�ύX���Ȃ������̂ŁA�ŏI�p�X���[�h�ύX�����X�V����Ă��Ȃ���</li>
	 * </ul>
	 */
	@Test
	public void updOkTest2() throws Exception {

		// �e�X�g�f�[�^�o�^
		initDate();
		
		// �e�X�g�f�[�^�m�F
		List<AdminLoginInfo> list = this.adminLoginInfoDAO.selectByFilter(null);
		Assert.assertEquals("�o�^��������������", 2, list.size());


		// ���̓f�[�^�쐬
		AdminUserForm inputForm = this.formFactory.createAdminUserForm();

		// �o�^���A�ŏI�X�V���A�ŏI���O�C�����̍X�V�`�F�b�N�p�Ƀf�[�^�� UPDATE
		Date day30 = DateUtils.addDays(new Date(), -30);
		UpdateExpression[] updateExpression
			= new UpdateExpression[] {new UpdateValue("lastPwdChangeDate", day30),
									  new UpdateValue("insDate", day30),
									  new UpdateValue("updDate", day30)};
		this.adminLoginInfoDAO.updateByCriteria(new DAOCriteria(), updateExpression);
		
		
		// ���[��ID
		inputForm.setRoleId("user");

		// �����Ώۃ��[�U�[ID
		inputForm.setUserId(this.targetId);

		// ���O�C��ID
		inputForm.setLoginId("NEWUID");

		// �p�X���[�h�̒l�͐ݒ肵�Ȃ��B

		// ���[�U�[��
		inputForm.setUserName("NEWUSER");
		// ���[���A�h���X
		inputForm.setEmail("NEWMAIL");
		// ���l
		inputForm.setNote("NEWNOTE");


		// �X�V�������s
		this.adminUserManage.updateAdminUser(inputForm, "newTestEdiId");

		// �X�V���ʂ��m�F
		list = this.adminLoginInfoDAO.selectByFilter(null);

		for (AdminLoginInfo admin : list){
			if (admin.getAdminUserId().equals(this.targetId)){

				// �X�V�Ώۃ��R�[�h�̌��ʊm�F
				// ���O�C��ID
				Assert.assertEquals("���O�C��ID ���X�V����Ă��鎖", "NEWUID", admin.getLoginId());
				// �p�X���[�h�i�n�b�V���l�j
				Assert.assertEquals("�p�X���[�h�i�n�b�V���l�j���X�V����Ă��Ȃ���", EncodingUtils.md5Encode("PWD0001"), admin.getPassword());
				// ���[�U�[����
				Assert.assertEquals("���[�U�[���̂��X�V����Ă��鎖", "NEWUSER", admin.getUserName());
				// ���[���A�h���X
				Assert.assertEquals("���[���A�h���X���X�V����Ă��鎖", "NEWMAIL", admin.getEmail());
				// ���O�C�����s��
				Assert.assertEquals("���O�C�����s�񐔂��X�V����Ă��Ȃ���", 0, (int)admin.getFailCnt());
				// �ŏI���O�C�����s��
				Assert.assertNull("���O�C�����s�����X�V����Ă��Ȃ���", admin.getLastFailDate());
				// �O�񃍃O�C����
				Assert.assertNull("�O�񃍃O�C�������X�V����Ă��Ȃ���", admin.getPreLogin());
				// �ŏI���O�C����
				Assert.assertNull("�ŏI���O�C�������X�V����Ă��Ȃ���", admin.getLastLogin());
				// �ŏI�p�X���[�h�ύX���@�i���t���̂݃`�F�b�N�j
				String date = StringUtils.dateToString(admin.getLastPasswdChange());
				String day30Str = StringUtils.dateToString(day30);
				Assert.assertEquals("�ŏI�p�X���[�h�ύX�����X�V���ꂢ�Ȃ���", day30Str, date);
				// ���l
				Assert.assertEquals("���l���X�V����Ă��鎖", "NEWNOTE", admin.getNote());
				// �o�^���@�i���t���̂݃`�F�b�N�j
				date = StringUtils.dateToString(admin.getInsDate());
				Assert.assertEquals("�o�^�����X�V����Ă��Ȃ���", day30Str, date);
				// �o�^��
				Assert.assertEquals("�o�^�҂��X�V����Ă��Ȃ���", "testEdiId1", admin.getInsUserId());
				// �ŏI�X�V���@�i���t���̂݃`�F�b�N�j
				date = StringUtils.dateToString(admin.getUpdDate());
				String now = StringUtils.dateToString(new Date());
				Assert.assertEquals("�X�V�����X�V����Ă��鎖", now, date);
				// �ŏI�X�V��
				admin.getUpdUserId();
				Assert.assertEquals("�X�V�҂��X�V����Ă鎖", "newTestEdiId", admin.getUpdUserId());

			}
		}
	}


	
	/**
	 * �Ǘ����[�U�[�X�V�e�X�g�i���O�C��ID�d���j<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>DuplicateException�@�̗�O���X���[����鎖</li>
	 * </ul>
	 */
	@Test(expected=DuplicateException.class)
	public void updNgTest() throws Exception {

		// �e�X�g�f�[�^�o�^
		initDate();
		
		// �e�X�g�f�[�^�m�F
		List<AdminLoginInfo> list = this.adminLoginInfoDAO.selectByFilter(null);
		Assert.assertEquals("�o�^��������������", 2, list.size());


		// ���̓f�[�^�쐬
		AdminUserForm inputForm = this.formFactory.createAdminUserForm();

		// �o�^���A�ŏI�X�V���A�ŏI���O�C�����̍X�V�`�F�b�N�p�Ƀf�[�^�� UPDATE
		Date day30 = DateUtils.addDays(new Date(), -30);
		UpdateExpression[] updateExpression
			= new UpdateExpression[] {new UpdateValue("lastPwdChangeDate", day30),
									  new UpdateValue("insDate", day30),
									  new UpdateValue("updDate", day30)};
		this.adminLoginInfoDAO.updateByCriteria(new DAOCriteria(), updateExpression);
		
		
		// ���[��ID
		inputForm.setRoleId("user");

		// �����Ώۃ��[�U�[ID
		inputForm.setUserId(this.targetId);

		// ���O�C��ID�@�i���Ŏg�p�ςh�c�j
		inputForm.setLoginId("UID0002");

		// �p�X���[�h�̒l�͐ݒ肵�Ȃ��B

		// ���[�U�[��
		inputForm.setUserName("NEWUSER");
		// ���[���A�h���X
		inputForm.setEmail("NEWMAIL");
		// ���l
		inputForm.setNote("NEWNOTE");


		// �X�V�������s
		this.adminUserManage.updateAdminUser(inputForm, "newTestEdiId");

	}


	
	@Test
	public void changePasswordOkTest() throws Exception{

		// �e�X�g�f�[�^�o�^
		initDate();
		
		// �e�X�g�f�[�^�m�F
		List<AdminLoginInfo> list = this.adminLoginInfoDAO.selectByFilter(null);
		Assert.assertEquals("�o�^��������������", 2, list.size());

		// �o�^���A�ŏI�X�V���A�ŏI���O�C�����̍X�V�`�F�b�N�p�Ƀf�[�^�� UPDATE
		Date day30 = DateUtils.addDays(new Date(), -30);
		UpdateExpression[] updateExpression
			= new UpdateExpression[] {new UpdateValue("lastPwdChangeDate", day30),
									  new UpdateValue("insDate", day30),
									  new UpdateValue("updDate", day30)};
		this.adminLoginInfoDAO.updateByCriteria(new DAOCriteria(), updateExpression);


		// ���݂��郍�O�C��ID �Ńp�X���[�h�ύX�����{
		PwdChangeForm inputForm = this.formFactory.createPwdChangeForm();
		inputForm.setNewPassword("NEWPWD");
		inputForm.setNewRePassword("NEWPWD");

		// �p�X���[�h�ύX�����{
		this.adminUserManage.changePassword(inputForm, this.targetId, "chgEditUser");

		
		// �X�V���ʂ��m�F
		list = this.adminLoginInfoDAO.selectByFilter(null);

		for (AdminLoginInfo admin : list){
			if (admin.getAdminUserId().equals(this.targetId)){

				// �X�V�Ώۃ��R�[�h�̌��ʊm�F
				// �p�X���[�h�i�n�b�V���l�j
				Assert.assertEquals("�p�X���[�h�i�n�b�V���l�j���X�V����Ă��鎖", EncodingUtils.md5Encode("NEWPWD"), admin.getPassword());
				// �ŏI�p�X���[�h�ύX���@�i���t���̂݃`�F�b�N�j
				String date = StringUtils.dateToString(admin.getLastPasswdChange());
				String now = StringUtils.dateToString(new Date());
				Assert.assertEquals("�ŏI�p�X���[�h�ύX�����X�V����Ă��鎖", now, date);
				// �o�^���@�i���t���̂݃`�F�b�N�j
				date = StringUtils.dateToString(admin.getInsDate());
				String day30Str = StringUtils.dateToString(day30);
				Assert.assertEquals("�o�^�����X�V����Ă��Ȃ���", day30Str, date);
				// �o�^��
				Assert.assertEquals("�o�^�҂��X�V����Ă��Ȃ���", "testEdiId1", admin.getInsUserId());
				// �ŏI�X�V���@�i���t���̂݃`�F�b�N�j
				date = StringUtils.dateToString(admin.getUpdDate());
				Assert.assertEquals("�X�V�����X�V����Ă��鎖", now, date);
				// �ŏI�X�V��
				admin.getUpdUserId();
				Assert.assertEquals("�X�V�҂��X�V����Ă鎖", "chgEditUser", admin.getUpdUserId());

			} else {

				// �X�V�Ώۃ��R�[�h�̌��ʊm�F
				// ���O�C��ID
				Assert.assertEquals("�p�X���[�h�i�n�b�V���l�j���X�V����Ă��Ȃ���", EncodingUtils.md5Encode("PWD0002"), admin.getPassword());
				// �ŏI�p�X���[�h�ύX��
				String date = StringUtils.dateToString(admin.getLastPwdChangeDate());
				String day30Str = StringUtils.dateToString(day30);
				Assert.assertEquals("�ŏI�p�X���[�h�ύX�����X�V����Ă��Ȃ���", day30Str, date);
				// �o�^���@�i���t���̂݃`�F�b�N�j
				date = StringUtils.dateToString(admin.getInsDate());
				Assert.assertEquals("�o�^�����X�V����Ă��Ȃ���", day30Str, date);
				// �o�^��
				Assert.assertEquals("�o�^�҂��X�V����Ă��Ȃ���", "testEdiId2", admin.getInsUserId());
				// �ŏI�X�V��
				date = StringUtils.dateToString(admin.getUpdDate());
				Assert.assertEquals("�X�V�����X�V����Ă��Ȃ���", day30Str, date);
				// �ŏI�X�V��
				Assert.assertEquals("�X�V�҂��X�V����Ă��Ȃ���", "testEdiId2", admin.getUpdUserId());

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
