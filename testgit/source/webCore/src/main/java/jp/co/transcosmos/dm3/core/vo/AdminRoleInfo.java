package jp.co.transcosmos.dm3.core.vo;

import jp.co.transcosmos.dm3.core.model.adminUser.AdminUserRoleInterface;

/**
 * 管理者権限情報クラス.
 * <p>
 * クラス名、および、フィールド名はＤＢテーブル名、列名にマッピングされる。
 * <p>
 * <pre>
 * 担当者		修正日		修正内容
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.01.28	新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 * 個別カスタマイズで継承される可能性があるので、直接インスタンスを生成しない事。<br/>
 * インスタンスを取得する場合は、ValueObjectFactory から取得する事。<br/>
 * 
 */
public class AdminRoleInfo implements AdminUserRoleInterface {

	/** 管理者ユーザーID */
	private String adminUserId;
	/** ロールID */
	private String roleId;
	
	
	
	/**
	 * 管理者ユーザーID を取得する。<br/>
	 * <br/>
	 * @return 管理者ユーザーID
	 */
	public String getAdminUserId() {
		return adminUserId;
	}
	
	/**
	 * 管理者ユーザーID を設定する。<br/>
	 * <br/>
	 * @param adminUserId 管理者ユーザーID
	 */
	public void setAdminUserId(String adminUserId) {
		this.adminUserId = adminUserId;
	}
	
	/**
	 * AdminUserRoleInterface の実装メソッド<br/>
	 * ユーザーメンテナンス機能は、このメソッドを経由してロールID に対応する管理ユーザーID を設定する。<br/>
	 * <br/>
	 * @param 管理ユーザーID
	 */
	@Override
	public void setUserId(String userId) {
		this.adminUserId = userId;
	}

	/**
	 * AdminUserRoleInterface の実装メソッド<br/>
	 * ユーザーメンテナンス機能は、このメソッドを経由してロールID を取得する。<br/>
	 * <br/>
	 * @return ロールID
	 */
	@Override
	public String getRoleId() {
		return roleId;
	}
	
	/**
	 * AdminUserRoleInterface の実装メソッド<br/>
	 * ユーザーメンテナンス機能は、このメソッドを経由してロールID を設定する。<br/>
	 * <br/>
	 * @param roleId ロールID
	 */
	@Override
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

}
