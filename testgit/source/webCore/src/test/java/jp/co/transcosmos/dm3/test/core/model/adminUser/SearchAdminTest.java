package jp.co.transcosmos.dm3.test.core.model.adminUser;

import java.util.List;

import jp.co.transcosmos.dm3.core.model.AdminUserManage;
import jp.co.transcosmos.dm3.core.model.adminUser.form.AdminUserForm;
import jp.co.transcosmos.dm3.core.model.adminUser.form.AdminUserFormFactory;
import jp.co.transcosmos.dm3.core.model.adminUser.form.AdminUserSearchForm;
import jp.co.transcosmos.dm3.core.vo.AdminLoginInfo;
import jp.co.transcosmos.dm3.core.vo.AdminRoleInfo;
import jp.co.transcosmos.dm3.dao.DAO;
import jp.co.transcosmos.dm3.dao.DAOCriteria;
import jp.co.transcosmos.dm3.dao.JoinResult;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 管理ユーザー model 検索系テストケース
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationContext-test.xml")
public class SearchAdminTest {

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
	 * 管理ユーザーログインＩＤ利用可能チェック<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>未登録のログインID の場合、チェックメソッドの戻り値が true である事</li>
	 *     <li>登録済のログインID の場合、チェックメソッドの戻り値が false である事</li>
	 * </ul>
	 */
	@Test
	public void isFreeLoginIdTest() throws Exception{
		
		// テストデータ登録
		initDate();
		
		// テストデータ確認
		List<AdminLoginInfo> list = this.adminLoginInfoDAO.selectByFilter(null);
		Assert.assertEquals("登録件数が正しい事", 2, list.size());

		
		// 存在するログインID でテストを実施
		AdminUserForm inputForm = this.formFactory.createAdminUserForm();
		inputForm.setLoginId("UID0001");
		Assert.assertFalse("仕様中のログインID の場合、false が復帰される事", this.adminUserManage.isFreeLoginId(inputForm));
		

		// 未使用のログインID でテストを実施
		inputForm.setLoginId("UID0003");
		Assert.assertTrue("未使用のログインID の場合、true が復帰される事", this.adminUserManage.isFreeLoginId(inputForm));
		
	}
	
	
	
	/**
	 * 管理ユーザー検索処理テスト<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>ログインID による検索が正常に行える事</li>
	 *     <li>ユーザー名 による検索（部分一致）が正常に行える事</li>
	 *     <li>メールアドレス による検索（部分一致）が正常に行える事</li>
	 *     <li>権限 による検索が正常に行える事</li>
	 *     <li>全ての検索条件設定による検索が正常に行える事</li>
	 * </ul>
	 */
	@Test
	public void searchAdminUser() throws Exception {
		
		// テストデータ登録
		initDate();
		
		// テストデータ確認
		List<AdminLoginInfo> list = this.adminLoginInfoDAO.selectByFilter(null);
		Assert.assertEquals("登録件数が正しい事", 2, list.size());


		// 検索条件を設定　（ログインＩＤ）
		AdminUserSearchForm searchForm = this.formFactory.createUserSearchForm();
		searchForm.setKeyLoginId("UID0001");

		// 検索実行
		int ret = this.adminUserManage.searchAdminUser(searchForm);
		
		Assert.assertEquals("該当件数が正しい事", 1, ret);
		AdminLoginInfo admin = (AdminLoginInfo) searchForm.getRows().get(0).getItems().get("adminLoginInfo");
		Assert.assertEquals("取得されているデータが正しい事", "UID0001", admin.getLoginId());

		
		// 検索条件を設定　（ユーザー名部分一致）
		searchForm = this.formFactory.createUserSearchForm();
		searchForm.setKeyUserName("名2");

		// 検索実行
		ret = this.adminUserManage.searchAdminUser(searchForm);
		
		Assert.assertEquals("該当件数が正しい事", 1, ret);
		admin = (AdminLoginInfo) searchForm.getRows().get(0).getItems().get("adminLoginInfo");
		Assert.assertEquals("取得されているデータが正しい事", "UID0002", admin.getLoginId());
		

		// 検索条件を設定　（メールアドレス部分一致）
		searchForm = this.formFactory.createUserSearchForm();
		searchForm.setKeyEmail("t1@l");
		
		// 検索実行
		ret = this.adminUserManage.searchAdminUser(searchForm);
		
		Assert.assertEquals("該当件数が正しい事", 1, ret);
		admin = (AdminLoginInfo) searchForm.getRows().get(0).getItems().get("adminLoginInfo");
		Assert.assertEquals("取得されているデータが正しい事", "UID0001", admin.getLoginId());


		// 検索条件を設定　（権限）
		searchForm = this.formFactory.createUserSearchForm();
		searchForm.setKeyRoleId("user");

		// 検索実行
		ret = this.adminUserManage.searchAdminUser(searchForm);
		
		Assert.assertEquals("該当件数が正しい事", 1, ret);
		admin = (AdminLoginInfo) searchForm.getRows().get(0).getItems().get("adminLoginInfo");
		Assert.assertEquals("取得されているデータが正しい事", "UID0002", admin.getLoginId());

		
		// 検索条件を設定　（全て）
		searchForm = this.formFactory.createUserSearchForm();
		searchForm.setKeyLoginId("UID0001");
		searchForm.setKeyUserName("名１");
		searchForm.setKeyEmail("t1@l");
		searchForm.setKeyRoleId("admin");

		// 検索実行
		ret = this.adminUserManage.searchAdminUser(searchForm);
		
		Assert.assertEquals("該当件数が正しい事", 1, ret);
		admin = (AdminLoginInfo) searchForm.getRows().get(0).getItems().get("adminLoginInfo");
		Assert.assertEquals("取得されているデータが正しい事", "UID0001", admin.getLoginId());

	}

	
	
	/**
	 * 管理ユーザー主キー検索処理テスト<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>指定したユーザーID の管理者ログイン情報が取得できる事</li>
	 *     <li>指定したユーザーID の管理者権限情報が取得できる事</li>
	 * </ul>
	 */
	@Test
	public void searchAdminUserPkTest() throws Exception{
		
		// テストデータ登録
		initDate();
		
		// テストデータ確認
		List<AdminLoginInfo> list = this.adminLoginInfoDAO.selectByFilter(null);
		Assert.assertEquals("登録件数が正しい事", 2, list.size());

		
		// 検索条件を設定
		AdminUserSearchForm searchForm = this.formFactory.createUserSearchForm();
		searchForm.setUserId(this.targetId);;


		// 検索実行
		JoinResult result = this.adminUserManage.searchAdminUserPk(searchForm);

		// 結果取得
		AdminLoginInfo admin = (AdminLoginInfo)result.getItems().get("adminLoginInfo");
		AdminRoleInfo role = (AdminRoleInfo) result.getItems().get("adminRoleInfo");

		Assert.assertEquals("取得した管理者ログイン情報のユーザーＩＤが正しい事", this.targetId, admin.getUserId());
		Assert.assertEquals("取得した管理者権限情報のユーザーＩＤが正しい事", this.targetId, role.getAdminUserId());

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
		inputForm.setRoleId("user");

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
