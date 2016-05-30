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
 * マイページ会員 model 更新テストケース
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

	// 更新対象ユーザーＩＤ
	private String targetId;
	// 更新対象外ユーザーＩＤ
	private String unTargetId1;
	private String unTargetId2;
	// 登録済お問合せID
	private String targetUUID;
	// 登録済お問合せID (処理対象外）
	private String unTargetUUID;
	

	
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
	 * マイページ会員パスワード変更テスト（該当 UUID あり）<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>指定したユーザーＩＤのマイページ会員情報のパスワードが更新されている事</li>
	 *     <li>指定されていないユーザーＩＤのマイページ会員情報のパスワードが更新されていない事</li>
	 *     <li>指定したUUIDのパスワード問合せ情報のステータス、更新日、更新者が更新されている事</li>
	 *     <li>指定されていないUUIDのパスワード問合せ情報のステータス、更新日、更新者が更新されていない事</li>
	 * </ul>
	 */
	@Test
	public void mypageUserManageImplOkTest() throws Exception{

		// テストデータ登録
		initTestData();
		
		// テストデータ確認
		List<MemberInfo> list = this.memberInfoDAO.selectByFilter(null);
		Assert.assertEquals("会員情報の登録件数が正しい事", 3, list.size());		

		List<UserInfo> list2 = this.userInfoDAO.selectByFilter(null);
		Assert.assertEquals("ユーザー情報の登録件数が正しい事", 3, list2.size());		

		// 登録日、最終更新日の更新チェック用にデータを UPDATE
		Date day30 = DateUtils.addDays(new Date(), -30);
		UpdateExpression[] updateExpression
			= new UpdateExpression[] {new UpdateValue("insDate", day30),
									  new UpdateValue("updDate", day30)};
		this.memberInfoDAO.updateByCriteria(new DAOCriteria(), updateExpression);
		
		Date day2 = DateUtils.addDays(new Date(), -2);
		updateExpression = new UpdateExpression[] {new UpdateValue("insDate", day2)};
		this.passwordRemindDAO.updateByCriteria(new DAOCriteria(), updateExpression);
		
		
		
		
		// 入力データ作成
		PwdChangeForm inputForm = this.formFactory.createPwdChangeForm();

		// 問合せID
		inputForm.setRemindId(this.targetUUID);
		// 新パスワード
		inputForm.setNewPassword("NEWPWD");
		// 新パスワード（確認）
		inputForm.setNewPasswordChk("NEWPWD");

		// テスト対象メソッド実行
		this.mypageUserManage.changePassword(inputForm, "anonUpdId");
		
		
		// パスワード更新結果の確認
		MemberInfo member = this.memberInfoDAO.selectByPK(this.targetId);
		
		// パスワード
		Assert.assertEquals("パスワードが入力値をハッシュした値で更新されている事", EncodingUtils.md5Encode("NEWPWD"), member.getPassword());
		// 登録日
		String date = StringUtils.dateToString(member.getInsDate());
		String day30str = StringUtils.dateToString(day30);
		Assert.assertEquals("登録日が変更されていない事", day30str, date);
		// 登録者
		Assert.assertEquals("登録者が変更されていない事", "editUserId1", member.getInsUserId());
		// 更新日
		date = StringUtils.dateToString(member.getUpdDate());
		String now = StringUtils.dateToString(new Date());
		Assert.assertEquals("更新日が変更されている事", now, date);
		// 更新者
		Assert.assertEquals("更新者が変更されている事", "anonUpdId", member.getUpdUserId());

		
		// パスワード問合せ情報の確認
		PasswordRemind remind = this.passwordRemindDAO.selectByPK(this.targetUUID);
		// 更新ステータス
		Assert.assertEquals("更新ステータスが 1 である事", "1", remind.getCommitFlg());
		// 登録日
		date = StringUtils.dateToString(remind.getInsDate());
		String day2str = StringUtils.dateToString(day2);
		Assert.assertEquals("登録日が変更されていない事", day2str, date);
		// 登録者
		Assert.assertEquals("登録者が変更されていない事", "anonUserId", remind.getInsUserId());
		// 更新日
		date = StringUtils.dateToString(remind.getUpdDate());
		Assert.assertEquals("更新日が変更されている事", now, date);
		// 更新者
		Assert.assertEquals("更新者が変更されている事", "anonUpdId", remind.getUpdUserId());
		
		
		// 更新対象外データのパスワードでマイページ会員情報が更新されていない事を確認
		member = this.memberInfoDAO.selectByPK(this.unTargetId1);
		Assert.assertEquals("更新対象外のマイページ会員情報が変更されていない事", EncodingUtils.md5Encode("password2"), member.getPassword());
		member = this.memberInfoDAO.selectByPK(this.unTargetId2);
		Assert.assertEquals("更新対象外のマイページ会員情報が変更されていない事", EncodingUtils.md5Encode("password3"), member.getPassword());

		// 更新対象外データのステータスでパスワード問合せ情報が更新されていない事を確認
		remind = this.passwordRemindDAO.selectByPK(this.unTargetUUID);
		Assert.assertEquals("更新対象外のパスワード問合せ情報が変更されていない事", "0", remind.getCommitFlg());

	}
	

	
	/**
	 * マイページ会員パスワード変更テスト（更新済 UUID）<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>同じ UUID で２回パスワード更新を行うと、NotFoundException がスローされる事</li>
	 * </ul>
	 */
	@Test(expected=NotFoundException.class)
	public void mypageUserManageImplNgTest1() throws Exception{

		// テストデータ登録
		initTestData();
		
		// テストデータ確認
		List<MemberInfo> list = this.memberInfoDAO.selectByFilter(null);
		Assert.assertEquals("会員情報の登録件数が正しい事", 3, list.size());		

		List<UserInfo> list2 = this.userInfoDAO.selectByFilter(null);
		Assert.assertEquals("ユーザー情報の登録件数が正しい事", 3, list2.size());		

		
		
		// 入力データ作成
		PwdChangeForm inputForm = this.formFactory.createPwdChangeForm();

		// 問合せID
		inputForm.setRemindId(this.targetUUID);
		// 新パスワード
		inputForm.setNewPassword("NEWPWD");
		// 新パスワード（確認）
		inputForm.setNewPasswordChk("NEWPWD");

		// テスト対象メソッド実行（１回目）
		try {
			this.mypageUserManage.changePassword(inputForm, "anonUpdId");
		} catch (Exception e){
			// １回目で例外が発生した場合はエラー
			Assert.fail("テストデータ作成の失敗");
		}
		
		// テスト対象メソッド実行２回目
		this.mypageUserManage.changePassword(inputForm, "anonUpdId");

	}


	
	/**
	 * マイページ会員パスワード変更テスト（存在しない UUID）<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>NotFoundException がスローされる事</li>
	 * </ul>
	 */
	@Test(expected=NotFoundException.class)
	public void mypageUserManageImplNgTest2() throws Exception{

		// テストデータ登録
		initTestData();
		
		// テストデータ確認
		List<MemberInfo> list = this.memberInfoDAO.selectByFilter(null);
		Assert.assertEquals("会員情報の登録件数が正しい事", 3, list.size());		

		List<UserInfo> list2 = this.userInfoDAO.selectByFilter(null);
		Assert.assertEquals("ユーザー情報の登録件数が正しい事", 3, list2.size());		

		
		
		// 入力データ作成
		PwdChangeForm inputForm = this.formFactory.createPwdChangeForm();

		// 問合せID
		inputForm.setRemindId("XXXXXXXXXXXXXXXXXXXXXX");
		// 新パスワード
		inputForm.setNewPassword("NEWPWD");
		// 新パスワード（確認）
		inputForm.setNewPasswordChk("NEWPWD");

		// テスト対象メソッド実行
		this.mypageUserManage.changePassword(inputForm, "anonUpdId");

	}


	
	/**
	 * マイページ会員パスワード変更テスト（UUIDの期限切れ）<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>NotFoundException がスローされる事</li>
	 * </ul>
	 */
	@Test(expected=NotFoundException.class)
	public void mypageUserManageImplNgTest3() throws Exception{

		// テストデータ登録
		initTestData();
		
		// テストデータ確認
		List<MemberInfo> list = this.memberInfoDAO.selectByFilter(null);
		Assert.assertEquals("会員情報の登録件数が正しい事", 3, list.size());		

		List<UserInfo> list2 = this.userInfoDAO.selectByFilter(null);
		Assert.assertEquals("ユーザー情報の登録件数が正しい事", 3, list2.size());		

		
		// 登録日を、11日前に設定
		DAOCriteria criterida = new DAOCriteria();
		criterida.addWhereClause("remindId", this.targetUUID);

		Date day11 = DateUtils.addDays(new Date(), -11);
		UpdateExpression[] updateExpression = new UpdateExpression[] {new UpdateValue("insDate", day11)};
		this.passwordRemindDAO.updateByCriteria(criterida, updateExpression);

		
		// 念のため、有効期限内の他のアップデートを実行
		PwdChangeForm inputForm = this.formFactory.createPwdChangeForm();

		// 問合せID
		inputForm.setRemindId(this.unTargetUUID);
		// 新パスワード
		inputForm.setNewPassword("NEWPWD");
		// 新パスワード（確認）
		inputForm.setNewPasswordChk("NEWPWD");

		// テスト対象メソッド実行
		try {
			this.mypageUserManage.changePassword(inputForm, "anonUpdId");
		} catch (Exception e){
			Assert.fail("テストデータ準備ミス");
		}

		
		// 期限切れ UUID を実行
		inputForm.setRemindId(this.targetUUID);
		this.mypageUserManage.changePassword(inputForm, "anonUpdId");

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
		

		// 更新対象外データ作成　（問合せなし）
		inputForm = this.formFactory.createMypageUserForm();
		
		this.unTargetId1 = (String)this.mypageUserManage.addLoginID("editUserId2").getUserId();

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

		this.mypageUserManage.addMyPageUser(inputForm, this.unTargetId1, "editUserId2");

		
		
		// 更新対象外データ作成　（問合せあり）
		inputForm = this.formFactory.createMypageUserForm();
		
		this.unTargetId2 = (String)this.mypageUserManage.addLoginID("editUserId3").getUserId();

		// メールアドレス
		inputForm.setEmail("test3@localhost");
		// 会員名(姓）
		inputForm.setMemberLname("会員姓３");
		// 会員名(名）
		inputForm.setMemberFname("会員名３");
		// 会員名・カナ(姓）
		inputForm.setMemberLnameKana("カイインセイ３");
		// 会員名・カナ(名）
		inputForm.setMemberFnameKana("カイインメイ３");
		// パスワード
		inputForm.setPassword("password3");
		// パスワード確認
		inputForm.setPasswordChk("password3");

		this.mypageUserManage.addMyPageUser(inputForm, this.unTargetId2, "editUserId3");


		// リマインダー登録データーの作成
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
