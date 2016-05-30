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
 * 管理ユーザー model ロックステータスのテストケース
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

	// 更新対象ユーザーＩＤ
	private String targetId;

	// 更新対象ユーザーＩＤ
	private String unTargetId;


	
	@Before
	public void init(){
		// 管理ユーザー全件削除
		DAOCriteria criteria = new DAOCriteria();
		this.adminLoginInfoDAO.deleteByFilter(criteria);
		this.adminRoleInfoDAO.deleteByFilter(criteria);
	}

	
	
	/**
	 * 管理ユーザーのロックステータス変更、およびステータス確認<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>指定したユーザーＩＤのロックステータスが、通常からロック中に変更できる事</li>
	 *     <li>指定していないユーザーＩＤのロックステータスが、通常からロック中に変更されていない事</li>
	 *     <li>指定したユーザーＩＤのロックステータスが、ロック中から通常に変更できる事</li>
	 * </ul>
	 */
	@Test
	public void changeLockStatusTest() throws Exception {

		// テストデータ登録
		initDate();

		// テストデータ確認
		List<AdminLoginInfo> list = this.adminLoginInfoDAO.selectByFilter(null);
		Assert.assertEquals("登録件数が正しい事", 2, list.size());
		
		// 処理対象アカウント情報を取得
		AdminLoginInfo targetAdmin = this.adminLoginInfoDAO.selectByPK(this.targetId);
		Assert.assertNotNull("対象テストデータが存在する事", targetAdmin);
		
		// 処理対象外アカウント情報を取得
		AdminLoginInfo unTargetAdmin = this.adminLoginInfoDAO.selectByPK(this.unTargetId);
		Assert.assertNotNull("対象外テストデータが存在する事", unTargetAdmin);
		
		// ステータスが通常である事をチェック
		Assert.assertFalse("対象テストデータが通常ステータスである事", this.adminUserManage.isLocked(targetAdmin));
		Assert.assertFalse("対象外テストデータが通常ステータスである事", this.adminUserManage.isLocked(unTargetAdmin));


		// 入力データ作成
		AdminUserForm inputForm = this.formFactory.createAdminUserForm();
		inputForm.setUserId(this.targetId);

		// ステータスをロック状態へ変更
		this.adminUserManage.changeLockStatus(inputForm, true, "lockEditId");

		// アカウント情報を取得
		targetAdmin = this.adminLoginInfoDAO.selectByPK(this.targetId);
		unTargetAdmin = this.adminLoginInfoDAO.selectByPK(this.unTargetId);
		
		// ロック状態の確認
		Assert.assertTrue("対象テストデータがロック中である事", this.adminUserManage.isLocked(targetAdmin));
		Assert.assertFalse("対象対テストデータが通常である事", this.adminUserManage.isLocked(unTargetAdmin));

		
		// ステータスを通常状態へ変更
		this.adminUserManage.changeLockStatus(inputForm, false, "lockEditId");

		// アカウント情報を取得
		targetAdmin = this.adminLoginInfoDAO.selectByPK(this.targetId);
		unTargetAdmin = this.adminLoginInfoDAO.selectByPK(this.unTargetId);
		
		// ロック状態の確認
		Assert.assertFalse("対象テストデータが通常である事", this.adminUserManage.isLocked(targetAdmin));
		Assert.assertFalse("対象対テストデータが通常である事", this.adminUserManage.isLocked(unTargetAdmin));
		
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
		this.unTargetId = this.adminUserManage.addAdminUser(inputForm, "testEdiId2");

	}

}
