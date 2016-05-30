package jp.co.transcosmos.dm3.adminCore.menu;

import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;

/**
 * <pre>
 * メニュー情報を生成し、リクエストスコープへ格納する Command Filter クラス
 * 
 * メニュー階層は、URL のフォルダ階層と依存関係にある。　下記はこのシステムにおける代表的な URL 階層
 * になる。
 *      /アプリケーション名/第1階層/第2階層/固有のフォルダ名...
 * 
 * メニューページの情報は、MenuPageInfo クラスのインスタンスで管理され、このオブジェクトを参照する Map
 * オブジェクトがメニュー構成情報プロパティ（menuInfo）に格納されている。 また、Map の Key 値は、URL
 * の第1階層の値にマッピングされている。
 * 
 * MenuPageInfo クラスは、そのメニューページの子要素となるメニューカテゴリー情報（MenuCategoryInfo）
 * を格納した Map オブジェクトを保持する。 メニューカテゴリー情報を管理している Key 値は URL とはマッピング
 * されていないが、メニュー構成全体を通して一意である必要がある。
 * 
 * メニューカテゴリー情報（MenuCategoryInfo）は、そのカテゴリーに含まれる、メニューItem （MenuItemInfo
 * クラス。　各機能へのリンク先情報などを持つ。）を格納した Map オブジェクトを保持する。
 * 
 * メニューItem 情報の Key 値は、URL の第2階層の値にマッピングされている。
 * メニュー情報を構成しているクラスを上位から順に並べると、下記の様な依存関係になる。
 * 
 *      MenuPageInfo -> MenuCategoryInfo -> MenuItemInfo
 * 
 * この Command Filter が、どのようにリクエストスコープに情報を格納しているかは handleRequest メソッド
 * の説明を参照する事。
 * 
 * 担当者       修正日      修正内容
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.01.29	Shamaison を参考に新規作成
 *
 * 注意事項
 * 
 * </pre>
*/
public class AddMenuCommand implements Command {

	private static final Log log = LogFactory.getLog(AddMenuCommand.class);

	/** リクエストスコープに格納する際の ID (メニュー情報) */
	protected String requestScopeMenuInfoId = "gcMenuInfo";
	
	/** リクエストスコープに格納する際の ID (現在のメニュー画面) */
	protected String requestScopeCurrentId = "gcCurrentMenu";

	/** リクエストスコープに格納する際の ID (現在のメニューItem 名) */
	protected String requestScopeCurrentItemId = "gcCurrentMenuItem";

	/** リクエストスコープに格納する際の ID (デフォルトロール名) */
	protected String requestScopeDefaultRoleId = "gcDefaultRole";

	/** メニュー構成情報 */
	protected Map<String, MenuPageInfo> menuInfo;
	
	/**
	 * デフォルトアクセス権<br/>
	 * 各オブジェクトの roles プロパティに値が設定されていない場合、このプロパティに設定したロール名が
	 * 適用される。<br/>
	 * プロパティ省略時に全ユーザーに表示させる場合、このプロパティに全ロール名をカンマ区切りで設定
	 * しておく必要がある。<br/> 
	 */
	private String defaultRoles;



	public void setRequestScopeMenuInfoId(String requestScopeMenuInfoId) {
		this.requestScopeMenuInfoId = requestScopeMenuInfoId;
	}

	public void setRequestScopeCurrentId(String requestScopeCurrentId) {
		this.requestScopeCurrentId = requestScopeCurrentId;
	}

	public void setMenuInfo(Map<String, MenuPageInfo> menuInfo) {
		this.menuInfo = menuInfo;
	}

	public void setDefaultRoles(String defaultRoles) {
		this.defaultRoles = defaultRoles;
	}

	
	
	/**
	 * メニュー情報を取得し、リクエストスコープへ格納する。<br/>
	 * <br/>
	 * <ul>
	 *   <li>リクエスト URL からアプリケーション名を取り除く。</li>
	 *   <li>もし URL が空の場合（URL が 「/アプリ名」 の場合）、メニュー情報は格納しない。</li>
	 *   <li>URL の第1階層が空の場合（URL が 「/アプリ名/」 の場合）、メニュー情報は格納しない。</li>
	 *   <li>URL の第1階層が存在する場合（URL が 「/アプリ名/第1階層」 の場合）、第1階層のフォルダ名を key
	 *   として、メニュー構成情報プロパティ（menuInfo）の Map から MenuPageInfo の情報を取得する。<br/>
	 *   この値は、現在のメニュー画面情報としてリクエストスコープへ格納する。</li>
	 *   <li>URL の第2階層が存在する場合（URL が 「/アプリ名/第1階層/第2階層」 の場合）、そのメニューペー
	 *   ジに含まれるメニューItem に、URL 第2階層に指定した Key値と一致するメニューItem が存在するかチェックする。</li>
	 *   <li>メニューItem が取得できた場合、現在のメニュー Item として Key 値をリクエストスコープへ格納する。</li>
	 * </ul>
	 * <br/>
	 * @param request HTTP リクエスト
	 * @param response HTTP レスポンス
	 * @return ModelAndView のインスタンス
	 */
	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// リクエストされた URL を取得　（コンテキスト名を除いた値を取得）
		String url = request.getRequestURI();

		String contextName = request.getServletContext().getContextPath();
		if (!StringValidateUtil.isEmpty(contextName)){
			url = url.substring(contextName.length());
		}


		// もし何も値が設定されていない場合、そのまま復帰する。　（メニュー表示対象外）
		if (StringValidateUtil.isEmpty(url)) return null;


		// フォルダを分割し、最初の階層の値を menu ID として使用する。
		// URL は / から始まるので、２個目の配列の値でチェックする。
		String[] dirs = url.split("/");
		if (dirs.length < 2 || StringValidateUtil.isEmpty(dirs[1])) return null;
		
		log.debug("menu Id is " + dirs[1]);

		// menu ID をキーにメニューページ情報を取得する。
		MenuPageInfo menuPageInfo = this.menuInfo.get(dirs[1]);

		
		// メニューページ内の メニュー Item の Map Key が URL 第2階層と一致する場合、現在のメニュー Item として取得する。
		String menuItemId = null;

		
		// URL の第2階層が存在する場合
		if (dirs.length >= 3 && !StringValidateUtil.isEmpty(dirs[2])) {

			// 第2階層を Key 値として取得
			String itemKey = dirs[2];
			
			// 一応、メニューページにメニューカテゴリーが存在する事をチェックする。
			if (menuPageInfo != null && menuPageInfo.getMenuCategorys() != null) {

				// メニューページに含まれるメニューカテゴリー情報を取得する。
				for (Entry<String, MenuCategoryInfo> ce : menuPageInfo.getMenuCategorys().entrySet()){
					MenuCategoryInfo category = ce.getValue();

					// メニューカテゴリーにメニュー Item が含まれる場合、URL 第2階層の値を Key として現在のメニュー
					// Item 名を取得する。
					if (category.getMenuItems() != null) {
						for (Entry<String, MenuItemInfo> ie : category.getMenuItems().entrySet()){

							// メニューItem の　Key 値が URL 第2階層と一致する場合
							if (ie.getKey().equals(itemKey)){
								menuItemId = ie.getKey();
								break;
							}
						}
					}
					if (menuItemId != null) break;
				}
			}
		}
		
		// その画面で使用するメニュー情報をリクエストスコープへ格納する。
		request.setAttribute(this.requestScopeMenuInfoId, this.menuInfo);			// メニュー構成
		request.setAttribute(this.requestScopeCurrentId, menuPageInfo);				// 現在のメニューページ
		request.setAttribute(this.requestScopeCurrentItemId, menuItemId);			// 現在のメニュー Item ページ
		request.setAttribute(this.requestScopeDefaultRoleId, this.defaultRoles);	// デフォルトロール

		return null;
	}

}
