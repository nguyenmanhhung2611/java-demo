package jp.co.transcosmos.dm3.core.model.adminUser;


/**
 * <pre>
 * 管理ユーザー情報管理バリーオブジェクト用インターフェース
 * 管理ユーザーのロールを管理する DAO のバリーオブジェクトは、このインターフェースを実装する事。
 * 管理ユーザーメンテナンス機能は、このインターフェース越しにロール操作を行う。
 *
 * 担当者       修正日      修正内容
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.01.28	新規作成
 *
 * 注意事項
 *
 * </pre>
 */
public interface AdminUserRoleInterface {

	/** 管理ユーザーID を設定する。<br/>
	 * このメソッドの設定先が、ロール情報に対応する管理ユーザーＩＤのフィールド（キー列）となる。<br/>
	 * <br/>
	 * param 管理ユーザーID
	 */
	public void setUserId(String userId);
	
	
	/**
	 * 管理ユーザーのロールID を取得する。<br/>
	 * このメソッドの設定先が、システムが権限管理で使用するロールID のフィールドとなる。<br/>
	 * <br/>
	 * @return ロールID
	 */
	public String getRoleId();
	
	/**
	 * 管理ユーザーのロールID を設定する。<br/>
	 * このメソッドの設定先が、システムが権限管理で使用するロールID のフィールドとなる。<br/>
	 * <br/>
	 * @param ロールID
	 */
	public void setRoleId(String roleId);
}
