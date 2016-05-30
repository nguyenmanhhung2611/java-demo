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
 * 管理ユーザー model 更新テストケース
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

	// 更新対象ユーザーＩＤ
	private String targetId;



	@Before
	public void init(){
		// 管理ユーザー全件削除
		DAOCriteria criteria = new DAOCriteria();
		this.adminLoginInfoDAO.deleteByFilter(criteria);
		this.adminRoleInfoDAO.deleteByFilter(criteria);
	}



	/**
	 * 管理ユーザー更新テスト（パスワード変更あり）<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>指定したユーザーＩＤの管理者ログイン情報が更新されている事</li>
	 *     <li>指定したユーザーＩＤの管理者権限情報が更新されている事</li>
	 *     <li>指定されていないユーザーＩＤの管理者ログイン情報が更新されていない事</li>
	 *     <li>指定されていないユーザーＩＤの管理者権限情報が更新されていない事</li>
	 *     <li>パスワード変更があったので、パスワードがハッシュされた値で更新されている事</li>
	 *     <li>パスワード変更があったので、最終パスワード変更日が更新されている事</li>
	 * </ul>
	 */
	@Test
	public void updOkTest1() throws Exception {

		// テストデータ登録
		initDate();
		
		// テストデータ確認
		List<AdminLoginInfo> list = this.adminLoginInfoDAO.selectByFilter(null);
		Assert.assertEquals("登録件数が正しい事", 2, list.size());

		
		// 登録日、最終更新日、最終ログイン日の更新チェック用にデータを UPDATE
		Date day30 = DateUtils.addDays(new Date(), -30);
		UpdateExpression[] updateExpression
			= new UpdateExpression[] {new UpdateValue("lastPwdChangeDate", day30),
									  new UpdateValue("insDate", day30),
									  new UpdateValue("updDate", day30)};
		this.adminLoginInfoDAO.updateByCriteria(new DAOCriteria(), updateExpression);
		
		
		// 入力データ作成
		AdminUserForm inputForm = this.formFactory.createAdminUserForm();
		
		// ロールID
		inputForm.setRoleId("user");

		// 処理対象ユーザーID
		inputForm.setUserId(this.targetId);

		// ログインID
		inputForm.setLoginId("NEWUID");
		// パスワード
		inputForm.setPassword("NEWPWD");
		// パスワード確認
		inputForm.setPasswordChk("NEWPWD");
		// ユーザー名
		inputForm.setUserName("NEWUSER");
		// メールアドレス
		inputForm.setEmail("NEWMAIL");
		// 備考
		inputForm.setNote("NEWNOTE");


		// 更新処理実行
		this.adminUserManage.updateAdminUser(inputForm, "newTestEdiId");

		// 更新結果を確認
		list = this.adminLoginInfoDAO.selectByFilter(null);

		for (AdminLoginInfo admin : list){
			if (admin.getAdminUserId().equals(this.targetId)){

				// 更新対象レコードの結果確認
				// ログインID
				Assert.assertEquals("ログインID が更新されている事", "NEWUID", admin.getLoginId());
				// パスワード（ハッシュ値）
				Assert.assertEquals("パスワード（ハッシュ値）が更新されている事", EncodingUtils.md5Encode("NEWPWD"), admin.getPassword());
				// ユーザー名称
				Assert.assertEquals("ユーザー名称が更新されている事", "NEWUSER", admin.getUserName());
				// メールアドレス
				Assert.assertEquals("メールアドレスが更新されている事", "NEWMAIL", admin.getEmail());
				// ログイン失敗回数
				Assert.assertEquals("ログイン失敗回数が更新されていない事", 0, (int)admin.getFailCnt());
				// 最終ログイン失敗日
				Assert.assertNull("ログイン失敗日が更新されていない事", admin.getLastFailDate());
				// 前回ログイン日
				Assert.assertNull("前回ログイン日が更新されていない事", admin.getPreLogin());
				// 最終ログイン日
				Assert.assertNull("最終ログイン日が更新されていない事", admin.getLastLogin());
				// 最終パスワード変更日　（日付部のみチェック）
				String date = StringUtils.dateToString(admin.getLastPasswdChange());
				String now = StringUtils.dateToString(new Date());
				Assert.assertEquals("最終パスワード変更日が更新されている事", now, date);
				// 備考
				Assert.assertEquals("備考が更新されている事", "NEWNOTE", admin.getNote());
				// 登録日　（日付部のみチェック）
				date = StringUtils.dateToString(admin.getInsDate());
				String day30Str = StringUtils.dateToString(day30);
				Assert.assertEquals("登録日が更新されていない事", day30Str, date);
				// 登録者
				Assert.assertEquals("登録者が更新されていない事", "testEdiId1", admin.getInsUserId());
				// 最終更新日　（日付部のみチェック）
				date = StringUtils.dateToString(admin.getUpdDate());
				Assert.assertEquals("更新日が更新されている事", now, date);
				// 最終更新者
				admin.getUpdUserId();
				Assert.assertEquals("更新者が更新されてる事", "newTestEdiId", admin.getUpdUserId());

			} else {

				// 更新対象レコードの結果確認
				// ログインID
				Assert.assertEquals("ログインID が更新されていない事", "UID0002", admin.getLoginId());
				// パスワード（ハッシュ値）
				Assert.assertEquals("パスワード（ハッシュ値）が更新されていない事", EncodingUtils.md5Encode("PWD0002"), admin.getPassword());
				// ユーザー名称
				Assert.assertEquals("ユーザー名が更新されていない事", "ユーザー名2", admin.getUserName());
				// メールアドレス
				Assert.assertEquals("メールアドレスが更新されていない事", "test2@localhost", admin.getEmail());
				// ログイン失敗回数
				Assert.assertEquals("ログイン失敗回数が更新されていない事", 0, (int)admin.getFailCnt());
				// 最終ログイン失敗日
				Assert.assertNull("ログイン失敗日が更新されていない事", admin.getLastFailDate());
				// 前回ログイン日
				Assert.assertNull("前回ログイン日が更新されていない事", admin.getPreLogin());
				// 最終ログイン日
				Assert.assertNull("最終ログイン日が更新されていない事", admin.getLastLogin());
				// 最終パスワード変更日
				String date = StringUtils.dateToString(admin.getLastPwdChangeDate());
				String day30Str = StringUtils.dateToString(day30);
				Assert.assertEquals("最終パスワード変更日が更新されていない事", day30Str, date);
				// 備考
				Assert.assertEquals("備考が更新されていない事", "備考2", admin.getNote());
				// 登録日　（日付部のみチェック）
				date = StringUtils.dateToString(admin.getInsDate());
				Assert.assertEquals("登録日が更新されていない事", day30Str, date);
				// 登録者
				Assert.assertEquals("登録者が更新されていない事", "testEdiId2", admin.getInsUserId());
				// 最終更新日
				date = StringUtils.dateToString(admin.getUpdDate());
				Assert.assertEquals("更新日が更新されていない事", day30Str, date);
				// 最終更新者
				Assert.assertEquals("更新者が更新されていない事", "testEdiId2", admin.getUpdUserId());

			}
		}
		
		
		// 更新結果を確認　（権限情報）
		List<AdminRoleInfo> list2 = this.adminRoleInfoDAO.selectByFilter(null);

		for (AdminRoleInfo role : list2){
			if (role.getAdminUserId().equals(this.targetId)){
				// 更新対象レコード
				// ロールID
				Assert.assertEquals("ロールID が更新されている事", "user", role.getRoleId());

			} else {
				// 更新対象外レコード
				// ロールID
				Assert.assertEquals("ロールID が更新されていない事", "admin", role.getRoleId());
				
			}
			
		}
	}



	/**
	 * 管理ユーザー更新テスト（パスワード変更なし）<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>指定したユーザーＩＤの管理者ログイン情報が更新されている事</li>
	 *     <li>パスワード変更がなかったので、パスワードが更新されていない事</li>
	 *     <li>パスワード変更がなかったので、最終パスワード変更日が更新されていない事</li>
	 * </ul>
	 */
	@Test
	public void updOkTest2() throws Exception {

		// テストデータ登録
		initDate();
		
		// テストデータ確認
		List<AdminLoginInfo> list = this.adminLoginInfoDAO.selectByFilter(null);
		Assert.assertEquals("登録件数が正しい事", 2, list.size());


		// 入力データ作成
		AdminUserForm inputForm = this.formFactory.createAdminUserForm();

		// 登録日、最終更新日、最終ログイン日の更新チェック用にデータを UPDATE
		Date day30 = DateUtils.addDays(new Date(), -30);
		UpdateExpression[] updateExpression
			= new UpdateExpression[] {new UpdateValue("lastPwdChangeDate", day30),
									  new UpdateValue("insDate", day30),
									  new UpdateValue("updDate", day30)};
		this.adminLoginInfoDAO.updateByCriteria(new DAOCriteria(), updateExpression);
		
		
		// ロールID
		inputForm.setRoleId("user");

		// 処理対象ユーザーID
		inputForm.setUserId(this.targetId);

		// ログインID
		inputForm.setLoginId("NEWUID");

		// パスワードの値は設定しない。

		// ユーザー名
		inputForm.setUserName("NEWUSER");
		// メールアドレス
		inputForm.setEmail("NEWMAIL");
		// 備考
		inputForm.setNote("NEWNOTE");


		// 更新処理実行
		this.adminUserManage.updateAdminUser(inputForm, "newTestEdiId");

		// 更新結果を確認
		list = this.adminLoginInfoDAO.selectByFilter(null);

		for (AdminLoginInfo admin : list){
			if (admin.getAdminUserId().equals(this.targetId)){

				// 更新対象レコードの結果確認
				// ログインID
				Assert.assertEquals("ログインID が更新されている事", "NEWUID", admin.getLoginId());
				// パスワード（ハッシュ値）
				Assert.assertEquals("パスワード（ハッシュ値）が更新されていない事", EncodingUtils.md5Encode("PWD0001"), admin.getPassword());
				// ユーザー名称
				Assert.assertEquals("ユーザー名称が更新されている事", "NEWUSER", admin.getUserName());
				// メールアドレス
				Assert.assertEquals("メールアドレスが更新されている事", "NEWMAIL", admin.getEmail());
				// ログイン失敗回数
				Assert.assertEquals("ログイン失敗回数が更新されていない事", 0, (int)admin.getFailCnt());
				// 最終ログイン失敗日
				Assert.assertNull("ログイン失敗日が更新されていない事", admin.getLastFailDate());
				// 前回ログイン日
				Assert.assertNull("前回ログイン日が更新されていない事", admin.getPreLogin());
				// 最終ログイン日
				Assert.assertNull("最終ログイン日が更新されていない事", admin.getLastLogin());
				// 最終パスワード変更日　（日付部のみチェック）
				String date = StringUtils.dateToString(admin.getLastPasswdChange());
				String day30Str = StringUtils.dateToString(day30);
				Assert.assertEquals("最終パスワード変更日が更新されいない事", day30Str, date);
				// 備考
				Assert.assertEquals("備考が更新されている事", "NEWNOTE", admin.getNote());
				// 登録日　（日付部のみチェック）
				date = StringUtils.dateToString(admin.getInsDate());
				Assert.assertEquals("登録日が更新されていない事", day30Str, date);
				// 登録者
				Assert.assertEquals("登録者が更新されていない事", "testEdiId1", admin.getInsUserId());
				// 最終更新日　（日付部のみチェック）
				date = StringUtils.dateToString(admin.getUpdDate());
				String now = StringUtils.dateToString(new Date());
				Assert.assertEquals("更新日が更新されている事", now, date);
				// 最終更新者
				admin.getUpdUserId();
				Assert.assertEquals("更新者が更新されてる事", "newTestEdiId", admin.getUpdUserId());

			}
		}
	}


	
	/**
	 * 管理ユーザー更新テスト（ログインID重複）<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>DuplicateException　の例外がスローされる事</li>
	 * </ul>
	 */
	@Test(expected=DuplicateException.class)
	public void updNgTest() throws Exception {

		// テストデータ登録
		initDate();
		
		// テストデータ確認
		List<AdminLoginInfo> list = this.adminLoginInfoDAO.selectByFilter(null);
		Assert.assertEquals("登録件数が正しい事", 2, list.size());


		// 入力データ作成
		AdminUserForm inputForm = this.formFactory.createAdminUserForm();

		// 登録日、最終更新日、最終ログイン日の更新チェック用にデータを UPDATE
		Date day30 = DateUtils.addDays(new Date(), -30);
		UpdateExpression[] updateExpression
			= new UpdateExpression[] {new UpdateValue("lastPwdChangeDate", day30),
									  new UpdateValue("insDate", day30),
									  new UpdateValue("updDate", day30)};
		this.adminLoginInfoDAO.updateByCriteria(new DAOCriteria(), updateExpression);
		
		
		// ロールID
		inputForm.setRoleId("user");

		// 処理対象ユーザーID
		inputForm.setUserId(this.targetId);

		// ログインID　（他で使用済ＩＤ）
		inputForm.setLoginId("UID0002");

		// パスワードの値は設定しない。

		// ユーザー名
		inputForm.setUserName("NEWUSER");
		// メールアドレス
		inputForm.setEmail("NEWMAIL");
		// 備考
		inputForm.setNote("NEWNOTE");


		// 更新処理実行
		this.adminUserManage.updateAdminUser(inputForm, "newTestEdiId");

	}


	
	@Test
	public void changePasswordOkTest() throws Exception{

		// テストデータ登録
		initDate();
		
		// テストデータ確認
		List<AdminLoginInfo> list = this.adminLoginInfoDAO.selectByFilter(null);
		Assert.assertEquals("登録件数が正しい事", 2, list.size());

		// 登録日、最終更新日、最終ログイン日の更新チェック用にデータを UPDATE
		Date day30 = DateUtils.addDays(new Date(), -30);
		UpdateExpression[] updateExpression
			= new UpdateExpression[] {new UpdateValue("lastPwdChangeDate", day30),
									  new UpdateValue("insDate", day30),
									  new UpdateValue("updDate", day30)};
		this.adminLoginInfoDAO.updateByCriteria(new DAOCriteria(), updateExpression);


		// 存在するログインID でパスワード変更を実施
		PwdChangeForm inputForm = this.formFactory.createPwdChangeForm();
		inputForm.setNewPassword("NEWPWD");
		inputForm.setNewRePassword("NEWPWD");

		// パスワード変更を実施
		this.adminUserManage.changePassword(inputForm, this.targetId, "chgEditUser");

		
		// 更新結果を確認
		list = this.adminLoginInfoDAO.selectByFilter(null);

		for (AdminLoginInfo admin : list){
			if (admin.getAdminUserId().equals(this.targetId)){

				// 更新対象レコードの結果確認
				// パスワード（ハッシュ値）
				Assert.assertEquals("パスワード（ハッシュ値）が更新されている事", EncodingUtils.md5Encode("NEWPWD"), admin.getPassword());
				// 最終パスワード変更日　（日付部のみチェック）
				String date = StringUtils.dateToString(admin.getLastPasswdChange());
				String now = StringUtils.dateToString(new Date());
				Assert.assertEquals("最終パスワード変更日が更新されている事", now, date);
				// 登録日　（日付部のみチェック）
				date = StringUtils.dateToString(admin.getInsDate());
				String day30Str = StringUtils.dateToString(day30);
				Assert.assertEquals("登録日が更新されていない事", day30Str, date);
				// 登録者
				Assert.assertEquals("登録者が更新されていない事", "testEdiId1", admin.getInsUserId());
				// 最終更新日　（日付部のみチェック）
				date = StringUtils.dateToString(admin.getUpdDate());
				Assert.assertEquals("更新日が更新されている事", now, date);
				// 最終更新者
				admin.getUpdUserId();
				Assert.assertEquals("更新者が更新されてる事", "chgEditUser", admin.getUpdUserId());

			} else {

				// 更新対象レコードの結果確認
				// ログインID
				Assert.assertEquals("パスワード（ハッシュ値）が更新されていない事", EncodingUtils.md5Encode("PWD0002"), admin.getPassword());
				// 最終パスワード変更日
				String date = StringUtils.dateToString(admin.getLastPwdChangeDate());
				String day30Str = StringUtils.dateToString(day30);
				Assert.assertEquals("最終パスワード変更日が更新されていない事", day30Str, date);
				// 登録日　（日付部のみチェック）
				date = StringUtils.dateToString(admin.getInsDate());
				Assert.assertEquals("登録日が更新されていない事", day30Str, date);
				// 登録者
				Assert.assertEquals("登録者が更新されていない事", "testEdiId2", admin.getInsUserId());
				// 最終更新日
				date = StringUtils.dateToString(admin.getUpdDate());
				Assert.assertEquals("更新日が更新されていない事", day30Str, date);
				// 最終更新者
				Assert.assertEquals("更新者が更新されていない事", "testEdiId2", admin.getUpdUserId());

			}
		}
		
	}
	
	
	
	// テストデータ作成
	private void initDate() throws Exception {
		
		AdminUserForm inputForm = this.formFactory.createAdminUserForm();

		// ロールID
		inputForm.setRoleId("admin");

		// ログインID
		inputForm.setLoginId("UID0001");
		// パスワード 
		inputForm.setPassword("PWD0001");
		// パスワード確認
		inputForm.setPasswordChk("PWD0001");
		// ユーザー名
		inputForm.setUserName("ユーザー名１");
		// メールアドレス
		inputForm.setEmail("test1@localhost");
		// 備考
		inputForm.setNote("備考1");

		// テストデータ作成（更新対象）
		this.targetId = this.adminUserManage.addAdminUser(inputForm, "testEdiId1");

		
		
		inputForm = this.formFactory.createAdminUserForm();

		// ロールID
		inputForm.setRoleId("admin");

		// ログインID
		inputForm.setLoginId("UID0002");
		// パスワード 
		inputForm.setPassword("PWD0002");
		// パスワード確認
		inputForm.setPasswordChk("PWD0002");
		// ユーザー名
		inputForm.setUserName("ユーザー名2");
		// メールアドレス
		inputForm.setEmail("test2@localhost");
		// 備考
		inputForm.setNote("備考2");

		// テストデータ作成（更新対象外）
		this.adminUserManage.addAdminUser(inputForm, "testEdiId2");

	}

}
