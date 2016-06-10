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
 * 管理ユーザー model 新規登録テストケース
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
		// 管理ユーザー全件削除
		DAOCriteria criteria = new DAOCriteria();
		this.adminLoginInfoDAO.deleteByFilter(criteria);
		this.adminRoleInfoDAO.deleteByFilter(criteria);
	}



	/**
	 * 管理ユーザー追加テスト（正常系）<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>戻り値が登録データのログインID と一致する事</li>
	 *     <li>指定した内容で管理者ログイン情報が追加されている事</li>
	 *     <li>指定した内容で管理者権限情報が追加されている事</li>
	 * </ul>
	 */
	@Test
	public void addOkTest() throws Exception {
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
		inputForm.setNote("備考");

		// テスト対象メソッド実行
		String ret = this.adminUserManage.addAdminUser(inputForm, "testEdiId");


		// データ確認（管理者ログインID情報）
		List<AdminLoginInfo> list = this.adminLoginInfoDAO.selectByFilter(null);

		Assert.assertEquals("登録件数が正しい事", 1, list.size());
		AdminLoginInfo admin = (AdminLoginInfo)list.get(0);


		// 戻り値と、登録されたログインID が一致する事
		Assert.assertEquals("登録時の戻り値とログインID が一致する事", ret, admin.getUserId());
		
		
		// ユーザーＩＤは、桁数と、先頭１文字のみ評価
		Assert.assertEquals("ユーザーＩＤが採番されている事（先頭１文字チェック）", "A", admin.getAdminUserId().substring(0, 1));
		Assert.assertEquals("ユーザーＩＤが採番されている事（長さをチェック）", 20, admin.getAdminUserId().length());

		// ログインID
		Assert.assertEquals("ログインID が正しい事", "UID0001", admin.getLoginId());
		// パスワード
		Assert.assertEquals("パスワードのハッシュした値が正しい事", EncodingUtils.md5Encode("PWD0001"), admin.getPassword());
		// ユーザー名
		Assert.assertEquals("ユーザー名が正しい事", "ユーザー名１", admin.getUserName());
		// メールアドレス
		Assert.assertEquals("メールアドレスが正しい事", "test1@localhost", admin.getEmail());
		// ログイン失敗回数
		Assert.assertEquals("ログイン失敗回数が 0 である事", 0, (int)admin.getFailCnt());
		// 最終ログイン失敗日
		Assert.assertNull("最終ログイン失敗日が null である事", admin.getLastFailDate());
		// 前回ログイン日
		Assert.assertNull("前回ログイン日が null である事", admin.getPreLogin());
		// 最終ログイン日
		Assert.assertNull("最終ログイン日が null である事", admin.getLastLoginDate());
		// 最終パスワード変更日　（日付部のみチェック）
		String date = StringUtils.dateToString(admin.getLastPasswdChange());
		String now = StringUtils.dateToString(new Date());
		Assert.assertEquals("最終パスワード変更日が当日である事", now, date);
		// 備考
		Assert.assertEquals("備考が正しい事", "備考", admin.getNote());
		
		// 登録者
		Assert.assertEquals("登録者が正しい事", "testEdiId", admin.getInsUserId());
		// 登録日（日付部のみチェック）
		date = StringUtils.dateToString(admin.getInsDate());
		Assert.assertEquals("登録日が正しい事", now, date);
		// 更新者
		Assert.assertEquals("更新者が正しい事", "testEdiId", admin.getUpdUserId());
		// 更新日（日付部のみチェック）
		date = StringUtils.dateToString(admin.getUpdDate());
		Assert.assertEquals("登録日が正しい事", now, date);
		
		
		// データ確認（管理者ロール情報）
		List<AdminRoleInfo> list2 = this.adminRoleInfoDAO.selectByFilter(null);

		Assert.assertEquals("登録件数が正しい事", 1, list2.size());
		AdminRoleInfo role = (AdminRoleInfo)list2.get(0);

		// ユーザーID
		Assert.assertEquals("管理者ログイン情報と、管理者権限情報のユーザーID が一致する事）", role.getAdminUserId(), admin.getAdminUserId());

		// ロールID
		Assert.assertEquals("管理者権限情報のロールが正しい事）", "admin", role.getRoleId());

	}



	/**
	 * 管理ユーザー追加テスト （ログインID が重複する場合）<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>DuplicateException　の例外がスローされる事</li>
	 * </ul>
	 */
	@Test(expected=DuplicateException.class)
	public void addNgTest() throws Exception {
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
		inputForm.setNote("備考");

		try {
			// テスト対象メソッド実行
			this.adminUserManage.addAdminUser(inputForm, "testEdiId");
		} catch (Exception e) {
			// エラーは無視。　この後、登録件数でチェックする。
		}

		// 既存データが登録されている事をチェック
		List<AdminLoginInfo> list = this.adminLoginInfoDAO.selectByFilter(null);
		Assert.assertEquals("登録件数が正しい事", 1, list.size());


		// ログインID を重複させて登録
		inputForm = this.formFactory.createAdminUserForm();

		// ロールID
		inputForm.setRoleId("user");

		// ログインID
		inputForm.setLoginId("UID0001");
		// パスワード 
		inputForm.setPassword("PWD0002");
		// パスワード確認
		inputForm.setPasswordChk("PWD0002");
		// ユーザー名
		inputForm.setUserName("ユーザー名２");
		// メールアドレス
		inputForm.setEmail("test2@localhost");
		// 備考
		inputForm.setNote("備考２");

		this.adminUserManage.addAdminUser(inputForm, "testEdiId");
		// 例外がスローされる事

		Assert.fail("例外がスローされる事");
	}
	
}
