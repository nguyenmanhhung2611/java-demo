package jp.co.transcosmos.dm3.adminCore.menu;

import java.util.LinkedHashMap;


/**
 * <pre>
 * メニューカテゴリー情報
 * メニューページに表示するメニューカテゴリーを管理する。
 * メニューページ情報の子要素となるクラス。
 * 
 * 担当者       修正日      修正内容
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.01.29	Shamaison を参考に新規作成
 *
 * 注意事項
 * 
 * </pre>
*/
public class MenuCategoryInfo {

	/** メニューカテゴリー名称 */
	private String menuItemCategoryName;

	/**
	 *  メニュー Item<br/>
	 *  メニューカテゴリー内に表示するメニュー項目情報の Map オブジェクト<br/>
	*/
	private LinkedHashMap<String, MenuItemInfo> menuItems;

	/**
	 * メニューカテゴリー表示権限情報<br/>
	 * 省略した場合、AddMenuCommand クラスの defaultRoles プロパティに設定したロールが適用される。<br/>
	 *　ロール名を設定した場合、設定した Role を持つユーザーのみ表示される。<br/>
	 *　複数のロール名を設定する場合は、カンマ区切りで記述する。<br\>
	*/
	private String roles;

	
	
	public String getMenuItemCategoryName() {
		return menuItemCategoryName;
	}

	public void setMenuItemCategoryName(String menuItemCategoryName) {
		this.menuItemCategoryName = menuItemCategoryName;
	}

	public LinkedHashMap<String, MenuItemInfo> getMenuItems() {
		return menuItems;
	}

	public void setMenuItems(LinkedHashMap<String, MenuItemInfo> menuItems) {
		this.menuItems = menuItems;
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

}
