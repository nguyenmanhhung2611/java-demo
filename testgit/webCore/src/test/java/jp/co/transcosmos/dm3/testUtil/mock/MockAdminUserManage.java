package jp.co.transcosmos.dm3.testUtil.mock;

import jp.co.transcosmos.dm3.core.model.adminUser.AdminUserManageImpl;

// �e�X�g�R�[�h�p Mock �N���X
public class MockAdminUserManage extends AdminUserManageImpl {

	@Override
	protected void manualRollback(){
		// RequestScopeTransaction ���e�X�g�R�[�h�ł͎g�p�ł��Ȃ��̂ŁA
		// ���[���o�b�N�������������Ȃ��l�ɕύX����B
	}
	
}
