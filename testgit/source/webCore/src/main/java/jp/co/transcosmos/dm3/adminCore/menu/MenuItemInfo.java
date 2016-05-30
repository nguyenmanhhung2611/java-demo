package jp.co.transcosmos.dm3.adminCore.menu;


/**
 * <pre>
 * メニューアイテム情報
 * メニューカテゴリー内に表示する Link 情報を管理する。
 * メニューカテゴリ情報の子要素となるクラス。
 * 
 * 担当者       修正日      修正内容
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.01.29	Shamaison を参考に新規作成
 *
 * 注意事項
 * 
 * </pre>
*/
public class MenuItemInfo {

	/** メニュー項目表示名称 */
	private String menuItemName;
	
	/** メニュー項目のリンク先 */
	private String menuItemUrl;

	/**
	 * メニュー Item 表示権限情報<br/>
	 * 省略した場合、AddMenuCommand クラスの defaultRoles プロパティに設定したロールが適用される。<br/>
	 *　ロール名を設定した場合、設定した Role を持つユーザーのみ表示される。<br/>
	 *　複数のロール名を設定する場合は、カンマ区切りで記述する。<br\>
	*/
	private String roles;



	public String getMenuItemName() {
		return menuItemName;
	}

	public void setMenuItemName(String menuItemName) {
		this.menuItemName = menuItemName;
	}

	public String getMenuItemUrl() {
		return menuItemUrl;
	}

	public void setMenuItemUrl(String menuItemUrl) {
		this.menuItemUrl = menuItemUrl;
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

}
