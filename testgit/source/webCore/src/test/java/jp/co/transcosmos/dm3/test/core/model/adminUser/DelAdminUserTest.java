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
 * 管理ユーザー model 削除テストケース
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
	 * 管理ユーザー削除テスト<br/>
	 * <br/>
	 * 【確認ポイント】
	 * <ul>
	 *     <li>指定したユーザーＩＤの管理者ログイン情報が削除されている事</li>
	 *     <li>指定したユーザーＩＤの管理者権限情報が削除されている事</li>
	 *     <li>指定されていないユーザーＩＤの管理者ログイン情報が削除されていない事</li>
	 *     <li>指定されていないユーザーＩＤの管理者権限情報が削除されていない事</li>
	 * </ul>
	 */
	@Test
	public void delOkTest() throws Exception {

		// テストデータ登録
		initDate();

		// テストデータ確認
		List<AdminLoginInfo> list = this.adminLoginInfoDAO.selectByFilter(null);
		Assert.assertEquals("登録件数が正しい事", 2, list.size());

		
		// 入力データ作成
		AdminUserForm inputForm = this.formFactory.createAdminUserForm();
		// 処理対象ユーザーID
		inputForm.setUserId(this.targetId);

		// 削除処理実行
		this.adminUserManage.delAdminUser(inputForm);

		
		// 更新結果を確認
		list = this.adminLoginInfoDAO.selectByFilter(null);
		
		Assert.assertEquals("該当件数が正しい事", 1, list.size());
		

		for (AdminLoginInfo admin : list){
			if (admin.getAdminUserId().equals(this.targetId)){

				// 削除対処レコードの結果確認
				Assert.fail("削除対象レコードが削除されている事");
			}
		}
		
		
		// 更新結果を確認（権限情報）
		List<AdminRoleInfo> list2 = this.adminRoleInfoDAO.selectByFilter(null);
		
		Assert.assertEquals("該当件数が正しい事", 1, list.size());
		
		for (AdminRoleInfo role : list2){
			if (role.getAdminUserId().equals(this.targetId)){

				// 削除対処レコードの結果確認
				Assert.fail("削除対象レコードが削除されている事");
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
