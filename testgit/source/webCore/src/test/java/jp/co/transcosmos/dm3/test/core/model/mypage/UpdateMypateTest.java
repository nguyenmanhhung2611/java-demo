package jp.co.transcosmos.dm3.test.core.model.mypage;

import java.util.Date;
import java.util.List;

import jp.co.transcosmos.dm3.core.model.MypageUserManage;
import jp.co.transcosmos.dm3.core.model.exception.NotFoundException;
import jp.co.transcosmos.dm3.core.model.mypage.form.MypageUserForm;
import jp.co.transcosmos.dm3.core.model.mypage.form.MypageUserFormFactory;
import jp.co.transcosmos.dm3.core.model.mypage.form.PwdChangeForm;
import jp.co.transcosmos.dm3.core.model.mypage.form.RemindForm;
import jp.co.transcosmos.dm3.core.vo.MemberInfo;
import jp.co.transcosmos.dm3.core.vo.PasswordRemind;
import jp.co.transcosmos.dm3.core.vo.UserInfo;
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
 * �}�C�y�[�W��� model �X�V�e�X�g�P�[�X
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationContext-test.xml")
public class UpdateMypateTest {

	@Autowired
	private MypageUserManage mypageUserManage;
	@Autowired
	private MypageUserFormFactory formFactory;
	@Autowired
	private DAO<MemberInfo> memberInfoDAO;
	@Autowired
	private DAO<PasswordRemind> passwordRemindDAO;
	@Autowired
	private DAO<UserInfo> userInfoDAO;

	// �X�V�Ώۃ��[�U�[�h�c
	private String targetId;
	// �X�V�ΏۊO���[�U�[�h�c
	private String unTargetId1;
	private String unTargetId2;
	// �o�^�ς��⍇��ID
	private String targetUUID;
	// �o�^�ς��⍇��ID (�����ΏۊO�j
	private String unTargetUUID;
	

	
	@Before
	public void init(){
		// �}�C�y�[�W����S���폜
		DAOCriteria criteria = new DAOCriteria();
		this.userInfoDAO.deleteByFilter(criteria);
		this.memberInfoDAO.deleteByFilter(criteria);

		// �p�X���[�h�⍇�����͏�������\�肪�����i�̔Ԃ��ꂽ UUID ���m���Ɍ��Ԃɂ���ׁB�j
		// �̂ŁA�e�X�g�R�[�h�ł����Z�b�g�͍s��Ȃ�
	}


	
	/**
	 * �}�C�y�[�W����p�X���[�h�ύX�e�X�g�i�Y�� UUID ����j<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�w�肵�����[�U�[�h�c�̃}�C�y�[�W������̃p�X���[�h���X�V����Ă��鎖</li>
	 *     <li>�w�肳��Ă��Ȃ����[�U�[�h�c�̃}�C�y�[�W������̃p�X���[�h���X�V����Ă��Ȃ���</li>
	 *     <li>�w�肵��UUID�̃p�X���[�h�⍇�����̃X�e�[�^�X�A�X�V���A�X�V�҂��X�V����Ă��鎖</li>
	 *     <li>�w�肳��Ă��Ȃ�UUID�̃p�X���[�h�⍇�����̃X�e�[�^�X�A�X�V���A�X�V�҂��X�V����Ă��Ȃ���</li>
	 * </ul>
	 */
	@Test
	public void mypageUserManageImplOkTest() throws Exception{

		// �e�X�g�f�[�^�o�^
		initTestData();
		
		// �e�X�g�f�[�^�m�F
		List<MemberInfo> list = this.memberInfoDAO.selectByFilter(null);
		Assert.assertEquals("������̓o�^��������������", 3, list.size());		

		List<UserInfo> list2 = this.userInfoDAO.selectByFilter(null);
		Assert.assertEquals("���[�U�[���̓o�^��������������", 3, list2.size());		

		// �o�^���A�ŏI�X�V���̍X�V�`�F�b�N�p�Ƀf�[�^�� UPDATE
		Date day30 = DateUtils.addDays(new Date(), -30);
		UpdateExpression[] updateExpression
			= new UpdateExpression[] {new UpdateValue("insDate", day30),
									  new UpdateValue("updDate", day30)};
		this.memberInfoDAO.updateByCriteria(new DAOCriteria(), updateExpression);
		
		Date day2 = DateUtils.addDays(new Date(), -2);
		updateExpression = new UpdateExpression[] {new UpdateValue("insDate", day2)};
		this.passwordRemindDAO.updateByCriteria(new DAOCriteria(), updateExpression);
		
		
		
		
		// ���̓f�[�^�쐬
		PwdChangeForm inputForm = this.formFactory.createPwdChangeForm();

		// �⍇��ID
		inputForm.setRemindId(this.targetUUID);
		// �V�p�X���[�h
		inputForm.setNewPassword("NEWPWD");
		// �V�p�X���[�h�i�m�F�j
		inputForm.setNewPasswordChk("NEWPWD");

		// �e�X�g�Ώۃ��\�b�h���s
		this.mypageUserManage.changePassword(inputForm, "anonUpdId");
		
		
		// �p�X���[�h�X�V���ʂ̊m�F
		MemberInfo member = this.memberInfoDAO.selectByPK(this.targetId);
		
		// �p�X���[�h
		Assert.assertEquals("�p�X���[�h�����͒l���n�b�V�������l�ōX�V����Ă��鎖", EncodingUtils.md5Encode("NEWPWD"), member.getPassword());
		// �o�^��
		String date = StringUtils.dateToString(member.getInsDate());
		String day30str = StringUtils.dateToString(day30);
		Assert.assertEquals("�o�^�����ύX����Ă��Ȃ���", day30str, date);
		// �o�^��
		Assert.assertEquals("�o�^�҂��ύX����Ă��Ȃ���", "editUserId1", member.getInsUserId());
		// �X�V��
		date = StringUtils.dateToString(member.getUpdDate());
		String now = StringUtils.dateToString(new Date());
		Assert.assertEquals("�X�V�����ύX����Ă��鎖", now, date);
		// �X�V��
		Assert.assertEquals("�X�V�҂��ύX����Ă��鎖", "anonUpdId", member.getUpdUserId());

		
		// �p�X���[�h�⍇�����̊m�F
		PasswordRemind remind = this.passwordRemindDAO.selectByPK(this.targetUUID);
		// �X�V�X�e�[�^�X
		Assert.assertEquals("�X�V�X�e�[�^�X�� 1 �ł��鎖", "1", remind.getCommitFlg());
		// �o�^��
		date = StringUtils.dateToString(remind.getInsDate());
		String day2str = StringUtils.dateToString(day2);
		Assert.assertEquals("�o�^�����ύX����Ă��Ȃ���", day2str, date);
		// �o�^��
		Assert.assertEquals("�o�^�҂��ύX����Ă��Ȃ���", "anonUserId", remind.getInsUserId());
		// �X�V��
		date = StringUtils.dateToString(remind.getUpdDate());
		Assert.assertEquals("�X�V�����ύX����Ă��鎖", now, date);
		// �X�V��
		Assert.assertEquals("�X�V�҂��ύX����Ă��鎖", "anonUpdId", remind.getUpdUserId());
		
		
		// �X�V�ΏۊO�f�[�^�̃p�X���[�h�Ń}�C�y�[�W�����񂪍X�V����Ă��Ȃ������m�F
		member = this.memberInfoDAO.selectByPK(this.unTargetId1);
		Assert.assertEquals("�X�V�ΏۊO�̃}�C�y�[�W�����񂪕ύX����Ă��Ȃ���", EncodingUtils.md5Encode("password2"), member.getPassword());
		member = this.memberInfoDAO.selectByPK(this.unTargetId2);
		Assert.assertEquals("�X�V�ΏۊO�̃}�C�y�[�W�����񂪕ύX����Ă��Ȃ���", EncodingUtils.md5Encode("password3"), member.getPassword());

		// �X�V�ΏۊO�f�[�^�̃X�e�[�^�X�Ńp�X���[�h�⍇����񂪍X�V����Ă��Ȃ������m�F
		remind = this.passwordRemindDAO.selectByPK(this.unTargetUUID);
		Assert.assertEquals("�X�V�ΏۊO�̃p�X���[�h�⍇����񂪕ύX����Ă��Ȃ���", "0", remind.getCommitFlg());

	}
	

	
	/**
	 * �}�C�y�[�W����p�X���[�h�ύX�e�X�g�i�X�V�� UUID�j<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>���� UUID �łQ��p�X���[�h�X�V���s���ƁANotFoundException ���X���[����鎖</li>
	 * </ul>
	 */
	@Test(expected=NotFoundException.class)
	public void mypageUserManageImplNgTest1() throws Exception{

		// �e�X�g�f�[�^�o�^
		initTestData();
		
		// �e�X�g�f�[�^�m�F
		List<MemberInfo> list = this.memberInfoDAO.selectByFilter(null);
		Assert.assertEquals("������̓o�^��������������", 3, list.size());		

		List<UserInfo> list2 = this.userInfoDAO.selectByFilter(null);
		Assert.assertEquals("���[�U�[���̓o�^��������������", 3, list2.size());		

		
		
		// ���̓f�[�^�쐬
		PwdChangeForm inputForm = this.formFactory.createPwdChangeForm();

		// �⍇��ID
		inputForm.setRemindId(this.targetUUID);
		// �V�p�X���[�h
		inputForm.setNewPassword("NEWPWD");
		// �V�p�X���[�h�i�m�F�j
		inputForm.setNewPasswordChk("NEWPWD");

		// �e�X�g�Ώۃ��\�b�h���s�i�P��ځj
		try {
			this.mypageUserManage.changePassword(inputForm, "anonUpdId");
		} catch (Exception e){
			// �P��ڂŗ�O�����������ꍇ�̓G���[
			Assert.fail("�e�X�g�f�[�^�쐬�̎��s");
		}
		
		// �e�X�g�Ώۃ��\�b�h���s�Q���
		this.mypageUserManage.changePassword(inputForm, "anonUpdId");

	}


	
	/**
	 * �}�C�y�[�W����p�X���[�h�ύX�e�X�g�i���݂��Ȃ� UUID�j<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>NotFoundException ���X���[����鎖</li>
	 * </ul>
	 */
	@Test(expected=NotFoundException.class)
	public void mypageUserManageImplNgTest2() throws Exception{

		// �e�X�g�f�[�^�o�^
		initTestData();
		
		// �e�X�g�f�[�^�m�F
		List<MemberInfo> list = this.memberInfoDAO.selectByFilter(null);
		Assert.assertEquals("������̓o�^��������������", 3, list.size());		

		List<UserInfo> list2 = this.userInfoDAO.selectByFilter(null);
		Assert.assertEquals("���[�U�[���̓o�^��������������", 3, list2.size());		

		
		
		// ���̓f�[�^�쐬
		PwdChangeForm inputForm = this.formFactory.createPwdChangeForm();

		// �⍇��ID
		inputForm.setRemindId("XXXXXXXXXXXXXXXXXXXXXX");
		// �V�p�X���[�h
		inputForm.setNewPassword("NEWPWD");
		// �V�p�X���[�h�i�m�F�j
		inputForm.setNewPasswordChk("NEWPWD");

		// �e�X�g�Ώۃ��\�b�h���s
		this.mypageUserManage.changePassword(inputForm, "anonUpdId");

	}


	
	/**
	 * �}�C�y�[�W����p�X���[�h�ύX�e�X�g�iUUID�̊����؂�j<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>NotFoundException ���X���[����鎖</li>
	 * </ul>
	 */
	@Test(expected=NotFoundException.class)
	public void mypageUserManageImplNgTest3() throws Exception{

		// �e�X�g�f�[�^�o�^
		initTestData();
		
		// �e�X�g�f�[�^�m�F
		List<MemberInfo> list = this.memberInfoDAO.selectByFilter(null);
		Assert.assertEquals("������̓o�^��������������", 3, list.size());		

		List<UserInfo> list2 = this.userInfoDAO.selectByFilter(null);
		Assert.assertEquals("���[�U�[���̓o�^��������������", 3, list2.size());		

		
		// �o�^�����A11���O�ɐݒ�
		DAOCriteria criterida = new DAOCriteria();
		criterida.addWhereClause("remindId", this.targetUUID);

		Date day11 = DateUtils.addDays(new Date(), -11);
		UpdateExpression[] updateExpression = new UpdateExpression[] {new UpdateValue("insDate", day11)};
		this.passwordRemindDAO.updateByCriteria(criterida, updateExpression);

		
		// �O�̂��߁A�L���������̑��̃A�b�v�f�[�g�����s
		PwdChangeForm inputForm = this.formFactory.createPwdChangeForm();

		// �⍇��ID
		inputForm.setRemindId(this.unTargetUUID);
		// �V�p�X���[�h
		inputForm.setNewPassword("NEWPWD");
		// �V�p�X���[�h�i�m�F�j
		inputForm.setNewPasswordChk("NEWPWD");

		// �e�X�g�Ώۃ��\�b�h���s
		try {
			this.mypageUserManage.changePassword(inputForm, "anonUpdId");
		} catch (Exception e){
			Assert.fail("�e�X�g�f�[�^�����~�X");
		}

		
		// �����؂� UUID �����s
		inputForm.setRemindId(this.targetUUID);
		this.mypageUserManage.changePassword(inputForm, "anonUpdId");

	}

	

	// �e�X�g�f�[�^�쐬
	private void initTestData() throws Exception {
		
		// �X�V�Ώۃf�[�^�쐬
		MypageUserForm inputForm = this.formFactory.createMypageUserForm();
		
		this.targetId = (String)this.mypageUserManage.addLoginID("editUserId1").getUserId();

		// ���[���A�h���X
		inputForm.setEmail("test1@localhost");
		// �����(���j
		inputForm.setMemberLname("������P");
		// �����(���j
		inputForm.setMemberFname("������P");
		// ������E�J�i(���j
		inputForm.setMemberLnameKana("�J�C�C���Z�C�P");
		// ������E�J�i(���j
		inputForm.setMemberFnameKana("�J�C�C�����C�P");
		// �p�X���[�h
		inputForm.setPassword("password1");
		// �p�X���[�h�m�F
		inputForm.setPasswordChk("password1");

		this.mypageUserManage.addMyPageUser(inputForm, this.targetId, "editUserId1");
		

		// �X�V�ΏۊO�f�[�^�쐬�@�i�⍇���Ȃ��j
		inputForm = this.formFactory.createMypageUserForm();
		
		this.unTargetId1 = (String)this.mypageUserManage.addLoginID("editUserId2").getUserId();

		// ���[���A�h���X
		inputForm.setEmail("test2@localhost");
		// �����(���j
		inputForm.setMemberLname("������Q");
		// �����(���j
		inputForm.setMemberFname("������Q");
		// ������E�J�i(���j
		inputForm.setMemberLnameKana("�J�C�C���Z�C�Q");
		// ������E�J�i(���j
		inputForm.setMemberFnameKana("�J�C�C�����C�Q");
		// �p�X���[�h
		inputForm.setPassword("password2");
		// �p�X���[�h�m�F
		inputForm.setPasswordChk("password2");

		this.mypageUserManage.addMyPageUser(inputForm, this.unTargetId1, "editUserId2");

		
		
		// �X�V�ΏۊO�f�[�^�쐬�@�i�⍇������j
		inputForm = this.formFactory.createMypageUserForm();
		
		this.unTargetId2 = (String)this.mypageUserManage.addLoginID("editUserId3").getUserId();

		// ���[���A�h���X
		inputForm.setEmail("test3@localhost");
		// �����(���j
		inputForm.setMemberLname("������R");
		// �����(���j
		inputForm.setMemberFname("������R");
		// ������E�J�i(���j
		inputForm.setMemberLnameKana("�J�C�C���Z�C�R");
		// ������E�J�i(���j
		inputForm.setMemberFnameKana("�J�C�C�����C�R");
		// �p�X���[�h
		inputForm.setPassword("password3");
		// �p�X���[�h�m�F
		inputForm.setPasswordChk("password3");

		this.mypageUserManage.addMyPageUser(inputForm, this.unTargetId2, "editUserId3");


		// ���}�C���_�[�o�^�f�[�^�[�̍쐬
		RemindForm remindForm = this.formFactory.createRemindForm();
		remindForm.setEmail("test1@localhost");
		this.targetUUID = this.mypageUserManage.addPasswordChangeRequest(remindForm, "anonUserId");
		if (this.targetUUID == null) throw new RuntimeException();

		remindForm = this.formFactory.createRemindForm();
		remindForm.setEmail("test3@localhost");
		this.unTargetUUID = this.mypageUserManage.addPasswordChangeRequest(remindForm, "anonUserId2");
		if (this.unTargetUUID == null) throw new RuntimeException();

		

	}

}
