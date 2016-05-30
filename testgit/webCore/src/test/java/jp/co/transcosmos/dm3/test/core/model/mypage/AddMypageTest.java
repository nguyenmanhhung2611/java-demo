package jp.co.transcosmos.dm3.test.core.model.mypage;

import java.util.Date;
import java.util.List;

import jp.co.transcosmos.dm3.core.model.MypageUserManage;
import jp.co.transcosmos.dm3.core.model.exception.NotFoundException;
import jp.co.transcosmos.dm3.core.model.mypage.form.MypageUserForm;
import jp.co.transcosmos.dm3.core.model.mypage.form.MypageUserFormFactory;
import jp.co.transcosmos.dm3.core.model.mypage.form.RemindForm;
import jp.co.transcosmos.dm3.core.vo.MemberInfo;
import jp.co.transcosmos.dm3.core.vo.PasswordRemind;
import jp.co.transcosmos.dm3.core.vo.UserInfo;
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
 * �}�C�y�[�W��� model �V�K�o�^�e�X�g�P�[�X
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationContext-test.xml")
public class AddMypageTest {

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
	private String unTargetId;
	
	
	
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
	 * �p�X���[�h���}�C���_�[�o�^�e�X�g<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>�w�肵�����e�Ńp�X���[�h�p�X���[�h�⍇�����ɓo�^����Ă��鎖</li>
	 *     <li>�X�V���A����сA�X�V�҂� null �ł��鎖</li>
	 *     <li>�X�V�m��t���O�� 0 �ł��鎖</li>
	 * </ul>
	 */
	@Test
	public void addPasswordChangeRequestOkTest() throws Exception {
		
		// �e�X�g�f�[�^�o�^
		initTestData();
		
		// �e�X�g�f�[�^�m�F
		List<MemberInfo> list = this.memberInfoDAO.selectByFilter(null);
		Assert.assertEquals("������̓o�^��������������", 2, list.size());		

		List<UserInfo> list2 = this.userInfoDAO.selectByFilter(null);
		Assert.assertEquals("���[�U�[���̓o�^��������������", 2, list2.size());		

		// ���}�C���_�[�o�^�O�̓o�^�����擾����B
		int remindCntBefore = this.passwordRemindDAO.getRowCountMatchingFilter(null);
		
		
		// ���}�C���_�[�o�^�f�[�^�[�̍쐬
		RemindForm inputForm = this.formFactory.createRemindForm();
		inputForm.setEmail("test1@localhost");

		// �e�X�g���s
		String id = this.mypageUserManage.addPasswordChangeRequest(inputForm, "anonUserId");
		
		// ���s���ꂽ�h�c �̃f�[�^�����݂��邩���`�F�b�N����B
		PasswordRemind remind = this.passwordRemindDAO.selectByPK(id);

		// ���[�U�[�h�c
		Assert.assertEquals("�o�^���ꂽ���[�U�[�h�c����������", this.targetId, remind.getUserId());
		// �X�V�m��t���O
		Assert.assertEquals("�o�^���ꂽ�X�V�m��t���O�� 0 �ł��鎖", "0", remind.getCommitFlg());
		// �⍇���o�^���i���t���̂݃`�F�b�N�j
		String date = StringUtils.dateToString(remind.getInsDate());
		String now = StringUtils.dateToString(new Date());
		Assert.assertEquals("�o�^���ꂽ�⍇���o�^������������", now, date);
		// �⍇���o�^��
		Assert.assertEquals("�o�^���ꂽ�⍇���o�^�҂���������", "anonUserId", remind.getInsUserId());
		// �ύX��
		Assert.assertNull("�ύX���� null �ł��鎖", remind.getUpdDate());
		// �ύX��
		Assert.assertNull("�ύX�҂� null �ł��鎖", remind.getUpdUserId());

		// �o�^��̃��}�C���_�������m�F����B
		int remindCntAfter = this.passwordRemindDAO.getRowCountMatchingFilter(null);
		Assert.assertEquals("�o�^���ꂽ�⍇���o�^���P���ł��鎖", remindCntAfter, remindCntBefore + 1);

	}
	
	

	/**
	 * �p�X���[�h���}�C���_�[�o�^�e�X�g�i���[���A�h���X�����݂��Ȃ��ꍇ�j<br/>
	 * <br/>
	 * �y�m�F�|�C���g�z
	 * <ul>
	 *     <li>NotFoundException ���X���[����鎖</li>
	 * </ul>
	 */
	@Test(expected=NotFoundException.class)
	public void addPasswordChangeRequestNgTest() throws Exception {

		// �e�X�g�f�[�^�o�^
		initTestData();

		// �e�X�g�f�[�^�m�F
		List<MemberInfo> list = this.memberInfoDAO.selectByFilter(null);
		Assert.assertEquals("������̓o�^��������������", 2, list.size());		

		List<UserInfo> list2 = this.userInfoDAO.selectByFilter(null);
		Assert.assertEquals("���[�U�[���̓o�^��������������", 2, list2.size());		


		// ���}�C���_�[�o�^�f�[�^�[�̍쐬
		RemindForm inputForm = this.formFactory.createRemindForm();
		inputForm.setEmail("test3@localhost");

		// �e�X�g���s
		this.mypageUserManage.addPasswordChangeRequest(inputForm, "anonUserId");

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
		

		// �X�V�ΏۊO�f�[�^�쐬
		inputForm = this.formFactory.createMypageUserForm();
		
		this.unTargetId = (String)this.mypageUserManage.addLoginID("editUserId2").getUserId();

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

		this.mypageUserManage.addMyPageUser(inputForm, this.unTargetId, "editUserId2");
		
	}
	
}
