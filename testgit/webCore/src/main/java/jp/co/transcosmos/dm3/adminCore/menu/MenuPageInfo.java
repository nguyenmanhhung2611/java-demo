package jp.co.transcosmos.dm3.adminCore.menu;

import java.util.LinkedHashMap;

/**
 * <pre>
 * メニューページ情報
 * メニュー管理情報の最上位に位置するオブジェクト
 * メニュー情報は、このオブジェクトを LinkedHashMap で管理する。　管理画面上段のメニュータブは、
 * この Map 情報から出力される。
 * 
 * 担当者       修正日      修正内容
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.01.29	Shamaison を参考に新規作成
 *
 * 注意事項
 * 
 * </pre>
*/
public class MenuPageInfo {

	/**
	 *  メニュー画面名称<br/>
	 *  メニュー画面名称。　ページタイトルや、メニュー画面選択タブの表示に使用される。<br/>
	 *  もし、MenuPageTitleImage、MenuTabImage が設定されている場合、設定されている画像ファイルを表示する。<br/>
	*/
	private String menuPageName;

	/**
	 *  メニュー画面タイトル画像<br/>
	 *  省略可能。　サイドメニューに表示されるメニュー画面名称画像を設定する。<br/>
	 *  もし、MenuTabOnImage が設定されている場合、設定されている画像ファイルを表示する。<br/>
	 *  画像パスには、CommonParameters#setResourceRootUrl() に指定したパス以降を記述する。<br/> 
	*/
	private String menuPageTitleIamge;

	/**
	 * メニュータブ画像ファイル名（アクティブな状態用）<br/>
	 * 省略可能。　省略した場合、 画像の変わりに MenuPageName が表示される。<br/>
	 *  画像パスには、CommonParameters#setResourceRootUrl() に指定したパス以降を記述する。<br/> 
	 */
	private String menuTabOnImage;

	/**
	 * メニュータブ画像ファイル名（非アクティブな状態用）<br/>
	 * 省略可能。　省略した場合、 画像の変わりに MenuPageName が表示される。<br/>
	 *  画像パスには、CommonParameters#setResourceRootUrl() に指定したパス以降を記述する。<br/> 
	 */
	private String menuTabOffImage;

	/**
	 * メニューカテゴリ情報<br/>
	 * このメニュータブに属するメニューカテゴリの Map 情報。<br/>
	 * Key = メニューカテゴリーID、Value メニューカテゴリーオブジェクト<br/>
	*/
	private LinkedHashMap<String, MenuCategoryInfo> menuCategorys;

	/**
	 * メニュー表示権限情報<br/>
	 * 省略した場合、AddMenuCommand クラスの defaultRoles プロパティに設定したロールが適用される。<br/>
	 *　ロール名を設定した場合、設定した Role を持つユーザーのみ表示される。<br/>
	 *　複数のロール名を設定する場合は、カンマ区切りで記述する。<br\>
	*/
	private String roles;

	

	public String getMenuPageName() {
		return this.menuPageName;
	}

	public void setMenuPageName(String menuPageName) {
		this.menuPageName = menuPageName;
	}

	public String getMenuPageTitleIamge() {
		return this.menuPageTitleIamge;
	}

	public void setMenuPageTitleIamge(String menuPageTitleIamge) {
		this.menuPageTitleIamge = menuPageTitleIamge;
	}

	public String getMenuTabOnImage() {
		return this.menuTabOnImage;
	}

	public void setMenuTabOnImage(String menuTabOnImage) {
		this.menuTabOnImage = menuTabOnImage;
	}

	public String getMenuTabOffImage() {
		return this.menuTabOffImage;
	}

	public void setMenuTabOffImage(String menuTabOffImage) {
		this.menuTabOffImage = menuTabOffImage;
	}

	public LinkedHashMap<String, MenuCategoryInfo> getMenuCategorys() {
		return this.menuCategorys;
	}

	public void setMenuCategorys(LinkedHashMap<String, MenuCategoryInfo> menuCategorys) {
		this.menuCategorys = menuCategorys;
	}

	public String getRoles() {
		return this.roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

}
