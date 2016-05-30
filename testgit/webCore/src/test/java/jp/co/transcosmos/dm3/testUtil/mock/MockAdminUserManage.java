package jp.co.transcosmos.dm3.testUtil.mock;

import jp.co.transcosmos.dm3.core.model.adminUser.AdminUserManageImpl;

// テストコード用 Mock クラス
public class MockAdminUserManage extends AdminUserManageImpl {

	@Override
	protected void manualRollback(){
		// RequestScopeTransaction がテストコードでは使用できないので、
		// ロールバック処理を何もしない様に変更する。
	}
	
}
