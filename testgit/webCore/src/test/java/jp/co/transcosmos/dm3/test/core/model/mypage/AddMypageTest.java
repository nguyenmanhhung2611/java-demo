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
 * マイページ会員 model 新規登録テストケース
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
	
	// 更新対象ユーザーＩＤ
	private String targetId;
	// 更新対象外ユーザーＩＤ
	private String unTargetId;
	
	
	
	@Before
	public void init(){
		// マイページ会員全件削除
		DAOCriteria criteria = new DAOCriteria();
		this.userInfoDAO.deleteByFilter(criteria);
		this.memberInfoDAO.deleteByFilter(criteria);
		
		// パスワード問合せ情報は消去する予定が無い（採番された UUID を確実に欠番にする為。）
		// ので、テストコードでもリセットは行わない
	}



	/**
	 * パスワードリマインダー登録テスト<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>指定した内容でパスワードパスワード問合せ情報に登録されている事</li>
	 *     <li>更新日、および、更新者が null である事</li>
	 *     <li>更新確定フラグが 0 である事</li>
	 * </ul>
	 */
	@Test
	public void addPasswordChangeRequestOkTest() throws Exception {
		
		// テストデータ登録
		initTestData();
		
		// テストデータ確認
		List<MemberInfo> list = this.memberInfoDAO.selectByFilter(null);
		Assert.assertEquals("会員情報の登録件数が正しい事", 2, list.size());		

		List<UserInfo> list2 = this.userInfoDAO.selectByFilter(null);
		Assert.assertEquals("ユーザー情報の登録件数が正しい事", 2, list2.size());		

		// リマインダー登録前の登録数を取得する。
		int remindCntBefore = this.passwordRemindDAO.getRowCountMatchingFilter(null);
		
		
		// リマインダー登録データーの作成
		RemindForm inputForm = this.formFactory.createRemindForm();
		inputForm.setEmail("test1@localhost");

		// テスト実行
		String id = this.mypageUserManage.addPasswordChangeRequest(inputForm, "anonUserId");
		
		// 発行されたＩＤ のデータが存在するかをチェックする。
		PasswordRemind remind = this.passwordRemindDAO.selectByPK(id);

		// ユーザーＩＤ
		Assert.assertEquals("登録されたユーザーＩＤが正しい事", this.targetId, remind.getUserId());
		// 更新確定フラグ
		Assert.assertEquals("登録された更新確定フラグが 0 である事", "0", remind.getCommitFlg());
		// 問合せ登録日（日付部のみチェック）
		String date = StringUtils.dateToString(remind.getInsDate());
		String now = StringUtils.dateToString(new Date());
		Assert.assertEquals("登録された問合せ登録日が正しい事", now, date);
		// 問合せ登録者
		Assert.assertEquals("登録された問合せ登録者が正しい事", "anonUserId", remind.getInsUserId());
		// 変更日
		Assert.assertNull("変更日が null である事", remind.getUpdDate());
		// 変更者
		Assert.assertNull("変更者が null である事", remind.getUpdUserId());

		// 登録後のリマインダ件数を確認する。
		int remindCntAfter = this.passwordRemindDAO.getRowCountMatchingFilter(null);
		Assert.assertEquals("登録された問合せ登録が１件である事", remindCntAfter, remindCntBefore + 1);

	}
	
	

	/**
	 * パスワードリマインダー登録テスト（メールアドレスが存在しない場合）<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>NotFoundException がスローされる事</li>
	 * </ul>
	 */
	@Test(expected=NotFoundException.class)
	public void addPasswordChangeRequestNgTest() throws Exception {

		// テストデータ登録
		initTestData();

		// テストデータ確認
		List<MemberInfo> list = this.memberInfoDAO.selectByFilter(null);
		Assert.assertEquals("会員情報の登録件数が正しい事", 2, list.size());		

		List<UserInfo> list2 = this.userInfoDAO.selectByFilter(null);
		Assert.assertEquals("ユーザー情報の登録件数が正しい事", 2, list2.size());		


		// リマインダー登録データーの作成
		RemindForm inputForm = this.formFactory.createRemindForm();
		inputForm.setEmail("test3@localhost");

		// テスト実行
		this.mypageUserManage.addPasswordChangeRequest(inputForm, "anonUserId");

	}

	
	
	// テストデータ作成
	private void initTestData() throws Exception {
		
		// 更新対象データ作成
		MypageUserForm inputForm = this.formFactory.createMypageUserForm();
		
		this.targetId = (String)this.mypageUserManage.addLoginID("editUserId1").getUserId();

		// メールアドレス
		inputForm.setEmail("test1@localhost");
		// 会員名(姓）
		inputForm.setMemberLname("会員姓１");
		// 会員名(名）
		inputForm.setMemberFname("会員名１");
		// 会員名・カナ(姓）
		inputForm.setMemberLnameKana("カイインセイ１");
		// 会員名・カナ(名）
		inputForm.setMemberFnameKana("カイインメイ１");
		// パスワード
		inputForm.setPassword("password1");
		// パスワード確認
		inputForm.setPasswordChk("password1");

		this.mypageUserManage.addMyPageUser(inputForm, this.targetId, "editUserId1");
		

		// 更新対象外データ作成
		inputForm = this.formFactory.createMypageUserForm();
		
		this.unTargetId = (String)this.mypageUserManage.addLoginID("editUserId2").getUserId();

		// メールアドレス
		inputForm.setEmail("test2@localhost");
		// 会員名(姓）
		inputForm.setMemberLname("会員姓２");
		// 会員名(名）
		inputForm.setMemberFname("会員名２");
		// 会員名・カナ(姓）
		inputForm.setMemberLnameKana("カイインセイ２");
		// 会員名・カナ(名）
		inputForm.setMemberFnameKana("カイインメイ２");
		// パスワード
		inputForm.setPassword("password2");
		// パスワード確認
		inputForm.setPasswordChk("password2");

		this.mypageUserManage.addMyPageUser(inputForm, this.unTargetId, "editUserId2");
		
	}
	
}
