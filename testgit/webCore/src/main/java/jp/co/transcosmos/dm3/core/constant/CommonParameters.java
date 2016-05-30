package jp.co.transcosmos.dm3.core.constant;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * 共通パラメータ情報クラス.
 * <p>
 * アプリケーションが使用する共通パラーメータを管理する。<br/>
 * <p>
 * <pre>
 * 担当者       修正日      修正内容
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.01.29	Shamaison を参考に新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 * このクラスから値を取得するには、commonParameters のオブジェクトを DI コンテナから取得するか、
 * getInstance() で取得したインスタンスを使用する事。<br/>
 * また、アプリケーション側から setter を使用して値を変更しない事。<br/>
 * <br/>
 * 標準設定では JSP 用に、global.xml からリクエストスコープにも commonParameters の key で格納
 * されている。　利用可能かは、global.xml の設定を確認する事。<br/>
 * 
 */
public class CommonParameters {

	/** CommonParameters の Bean ID */
	protected static final String COMMON_PARAMETERS_BEAN_ID = "commonParameters";

	/** 画像ファイル、CSS などの、静的ファイルのルートパス */
	private String resourceRootUrl = "/admin_rsc/";

	/**　管理ユーザーパスワードで入力許可される半角記号文字 */
	private String adminPwdUseMarks = "-=+_*";

	/**　管理ユーザーパスワードでどれかひとつは必須となる半角記号文字 */
	private String adminPwdMastMarks = "";

	/** 管理ユーザーのログインＩＤ、パスワードの最小桁数 */
	private int minAdminPwdLength = 8;
	
	/**　マイページユーザーパスワードで入力許可される半角記号文字 */
	private String mypagePwdUseMarks = "-=+_*";

	/**　マイページユーザーパスワードでどれかひとつは必須となる半角記号文字 */
	private String mypagePwdMastMarks = "";

	/** マイページユーザーパスワードの最小桁数 */
	private int minMypagePwdLength = 8;
	
	/** 管理ページタイトル **/
	private String adminPageTitle = "";

	/** 管理ページログイン URL　（メール本文等で使用） **/
	private String adminLoginUrl = "";

	/** マイページログイン URL　（メール本文等で使用） **/
	private String mypageLoginUrl = "";

	/** 物件リクエスト登録上限件数 */
	private int maxHousingRequestCnt = 5;

	// note
	// 将来、建物画像用のプロパティも追加される予定。

	/** 物件画像の仮 root フォルダ（物理パス） */
	private String housImgTempPhysicalPath = "/data/img/temp/";

	/** 物件画像の公開 root フォルダ（物理パス） */
	private String housImgOpenPhysicalPath = "/data/img/open/";

	/** 物件画像の仮 root URL */
	private String housImgTempUrl = "/img/temp/";

	/** 物件画像の公開 root URL */
	private String housImgOpenUrl = "/img/open/";

	/** Commons Upload が使用するワークフォルダ */
	private String uploadWorkPath = "/data/work/";

	/** Cookie Domain　*/
	private String cookieDomain;

	/** サムネイル画像ファイルのサイズリスト */
	private List<Integer> thumbnailSizes; 

	/**
	 * RequestScopeDataSource の Bean ID 名<br/>
	 * 手動でトランザクションを制御する場合、このプロパティに設定されている Bean ID で
	 * 処理している。<br/>
	 */
	protected String requestScopeDataSourceId = "requestScopeDS";

	/**
	 *  管理ユーザーテーブルの Aliase 名<br/>
	 *  通常、このプロパティ値を変更する事はない。<br/>
	 *  認証に使用する DAO を変更した場合、テーブルの Alias 名を変更する必要性が発生した
	 *  場合、必要に応じて設定値を変更する。<br/>
	 *  （影響が出ないように JoinDAO を修正するのが望ましい。）
	 */
	private String adminUserDbAlias = "adminLoginInfo";

	/**
	 * 管理ユーザー権限テーブルの Role 名<br/>
	 *  通常、このプロパティ値を変更する事はない。<br/>
	 *  認証に使用する DAO を変更した場合、テーブルの Alias 名を変更する必要性が発生した
	 *  場合、必要に応じて設定値を変更する。<br/>
	 *  （影響が出ないように JoinDAO を修正するのが望ましい。）
	 */
	private String adminRoleDbAlias = "adminRoleInfo";

	/**
	 *  マイページ会員テーブルの Aliase 名<br/>
	 *  通常、このプロパティ値を変更する事はない。<br/>
	 *  認証に使用する DAO を変更した場合、テーブルの Alias 名を変更する必要性が発生した
	 *  場合、必要に応じて設定値を変更する。<br/>
	 *  （影響が出ないように JoinDAO を修正するのが望ましい。）
	 */
	private String memberDbAlias = "memberInfo";

	
	
	/**
	 * CommonParameters のインスタンスを取得する。<br/>
	 * Spring のコンテキストから、 commonParameters で定義された CommonParameters のインスタンスを取得する。<br/>
	 * 取得されるインスタンスは、CommonParameters を継承した拡張クラスが復帰される場合もある。<br/>
	 * <br/>
	 * @param request HTTP リクエスト
	 * @return CommonParameters、または継承して拡張したクラスのインスタンス
	 */
	public static CommonParameters getInstance(HttpServletRequest request){
		WebApplicationContext springContext = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
		return (CommonParameters)springContext.getBean(COMMON_PARAMETERS_BEAN_ID);
	}

	/**
	 * 画像ファイル、CSS などの、静的ファイルのルートパスを取得する。<br/>
	 * <br/>
	 * @return 静的ファイルのルートパス
	 */
	public String getResourceRootUrl() {
		return resourceRootUrl;
	}

	/**
	 * 画像ファイル、CSS などの、静的ファイルのルートパスを設定する。<br/>
	 * <br/>
	 * @param resourceRootUrl 静的ファイルのルートパス
	 */
	public void setResourceRootUrl(String resourceRootUrl) {
		this.resourceRootUrl = resourceRootUrl;
	}

	/**
	 * 管理者パスワードで利用可能な記号文字を取得する。<br/>
	 * <br/>
	 * @return 管理者パスワードで利用可能な記号文字
	 */
	public String getAdminPwdUseMarks() {
		return adminPwdUseMarks;
	}

	/**
	 * 管理者パスワードで利用可能な記号文字を設定する。<br/>
	 * <br/>
	 * @param adminPwdUseMarks 管理者パスワードで利用可能な記号文字
	 */
	public void setAdminPwdUseMarks(String adminPwdUseMarks) {
		this.adminPwdUseMarks = adminPwdUseMarks;
	}

	/**
	 * 管理者パスワードでどれかひとつは使用しなければならない記号文字を取得する。<br/>
	 * <br/>
	 * @return adminPwdUseMarks 管理者パスワードで利用可能な記号文字
	 */
	public String getAdminPwdMastMarks() {
		return adminPwdMastMarks;
	}

	/**
	 * 管理者パスワードでどれかひとつは使用しなければならない記号文字を設定する。<br/>
	 * <br/>
	 * @param adminPwdMastMarks adminPwdUseMarks 管理者パスワードで利用可能な記号文字
	 */
	public void setAdminPwdMastMarks(String adminPwdMastMarks) {
		this.adminPwdMastMarks = adminPwdMastMarks;
	}

	/**
	 * 管理者ログインＩＤ、パスワードの最小桁数を取得する。<br/>
	 * <br/>
	 * @return 管理者ログインＩＤ、パスワードの最小桁数
	 */
	public int getMinAdminPwdLength() {
		return minAdminPwdLength;
	}

	/**
	 * 管理者ログインＩＤ、パスワードの最大桁数を設定する。<br/>
	 * <br/>
	 * @param minAdminPwdLength 管理者ログインＩＤ，パスワードの最小桁数　（デフォルト 8）
	 */
	public void setMinAdminPwdLength(int minAdminPwdLength) {
		this.minAdminPwdLength = minAdminPwdLength;
	}

	/**
	 * マイページパスワードで使用可能な記号文字を取得する。<br/>
	 * <br/>
	 * @return マイページパスワードで使用可能な記号文字
	 */
	public String getMypagePwdUseMarks() {
		return mypagePwdUseMarks;
	}

	/**
	 * マイページパスワードで使用可能な記号文字を設定する。<br/>
	 * <br/>
	 * @param mypagePwdUseMarks マイページパスワードで使用可能な記号文字
	 */
	public void setMypagePwdUseMarks(String mypagePwdUseMarks) {
		this.mypagePwdUseMarks = mypagePwdUseMarks;
	}

	/**
	 * マイページパスワードでどれかひとつは使用しなければならない記号文字を取得する。<br/>
	 * <br/>
	 * @return マイページパスワードでどれかひとつは使用しなければならない記号文字
	 */
	public String getMypagePwdMastMarks() {
		return mypagePwdMastMarks;
	}

	/**
	 * マイページパスワードでどれかひとつは使用しなければならない記号文字を設定する。<br/>
	 * <br/>
	 * @param mypagePwdMastMarks マイページパスワードでどれかひとつは使用しなければならない記号文字
	 */
	public void setMypagePwdMastMarks(String mypagePwdMastMarks) {
		this.mypagePwdMastMarks = mypagePwdMastMarks;
	}

	/**
	 * マイページパスワードの最小桁数を取得する。<br/>
	 * <br/>
	 * @return マイページパスワードの最小桁数
	 */
	public int getMinMypagePwdLength() {
		return minMypagePwdLength;
	}

	/**
	 * マイページパスワードの最小桁数を設定する。<br/>
	 * <br/>
	 * @param minMypagePwdLength マイページパスワードの最小桁数 （デフォルト 8 ）
	 */
	public void setMinMypagePwdLength(int minMypagePwdLength) {
		this.minMypagePwdLength = minMypagePwdLength;
	}

	/**
	 * 管理ページタイトルを取得する。<br/>
	 * <br/>
	 * @return 管理ページタイトル
	 */
	public String getAdminPageTitle() {
		return adminPageTitle;
	}

	/**
	 * 管理ページタイトルを設定する。<br/>
	 * <br/>
	 * @param adminPageTitle 管理ページタイトル
	 */
	public void setAdminPageTitle(String adminPageTitle) {
		this.adminPageTitle = adminPageTitle;
	}

	/**
	 * 管理ページログイン URL を取得する。<br/>
	 * <br/>
	 * @return 管理ページログイン URL
	 */
	public String getAdminLoginUrl() {
		return adminLoginUrl;
	}

	/**
	 * 管理ページログイン URL を設定する。<br/>
	 * <br/>
	 * @param adminLoginUrl 管理ページログイン URL
	 */
	public void setAdminLoginUrl(String adminLoginUrl) {
		this.adminLoginUrl = adminLoginUrl;
	}

	/**
	 * マイページログイン URL を取得する。<br/>
	 * <br/>
	 * @return マイページログイン URL
	 */
	public String getMypageLoginUrl() {
		return mypageLoginUrl;
	}

	/**
	 * マイページログイン URL を設定する。<br/>
	 * <br/>
	 * @param adminLoginUrlマイページログイン URL
	 */
	public void setMypageLoginUrl(String mypageLoginUrl) {
		this.mypageLoginUrl = mypageLoginUrl;
	}

	/**
	 * 物件リクエスト登録上限件数 を取得する。<br/>
	 * <br/>
	 * @return 物件リクエスト登録上限件数
	 */
	public int getMaxHousingRequestCnt() {
		return maxHousingRequestCnt;
	}

	/**
	 * 物件リクエスト登録上限件数 を設定する。<br/>
	 * <br/>
	 * @param maxHousingRequestCnt 物件リクエスト登録上限件数
	 */
	public void setMaxHousingRequestCnt(int maxHousingRequestCnt) {
		this.maxHousingRequestCnt = maxHousingRequestCnt;
	}

	/**
	 * 物件画像の仮 root フォルダ（物理パス）を取得する。<br/>
	 * <br/>
	 * @return 物件画像の仮 root フォルダ（物理パス）
	 */
	public String getHousImgTempPhysicalPath() {
		return housImgTempPhysicalPath;
	}

	/**
	 * 物件画像の仮 root フォルダ（物理パス）を設定する。<br/>
	 * <br/>
	 * @param housImgTempPhysicalPath 物件画像の仮 root フォルダ（物理パス）
	 */
	public void setHousImgTempPhysicalPath(String housImgTempPhysicalPath) {
		this.housImgTempPhysicalPath = housImgTempPhysicalPath;
	}

	/**
	 * 物件画像の公開 root フォルダ（物理パス）を取得する。<br/>
	 * <br/>
	 * @return 物件画像の公開 root フォルダ（物理パス）
	 */
	public String getHousImgOpenPhysicalPath() {
		return housImgOpenPhysicalPath;
	}

	/**
	 * 物件画像の公開 root フォルダ（物理パス）を設定する。<br/>
	 * <br/>
	 * @param housImgOpenPhysicalPath 物件画像の公開 root フォルダ（物理パス）
	 */
	public void setHousImgOpenPhysicalPath(String housImgOpenPhysicalPath) {
		this.housImgOpenPhysicalPath = housImgOpenPhysicalPath;
	}

	/**
	 * 物件画像の仮 root URL を取得する。<br/>
	 * <br/>
	 * @return 物件画像の仮 root URL
	 */
	public String getHousImgTempUrl() {
		return housImgTempUrl;
	}

	/**
	 * 物件画像の仮 root URL を設定する。<br/>
	 * <br/>
	 * @param housImgTempUrl　物件画像の仮 root URL
	 */
	public void setHousImgTempUrl(String housImgTempUrl) {
		this.housImgTempUrl = housImgTempUrl;
	}

	/**
	 * 物件画像の公開 root URL を取得する。<br/>
	 * <br/>
	 * @return 物件画像の公開 root URL
	 */
	public String getHousImgOpenUrl() {
		return housImgOpenUrl;
	}

	/**
	 * 物件画像の公開 root URL を設定する。<br/>
	 * <br/>
	 * @param housImgOpenUrl 物件画像の公開 root URL
	 */
	public void setHousImgOpenUrl(String housImgOpenUrl) {
		this.housImgOpenUrl = housImgOpenUrl;
	}

	/**
	 * Commons Upload が使用するワークフォルダを取得する。<br/>
	 * <br/>
	 * @return Commons Upload が使用するワークフォルダ
	 */
	public String getUploadWorkPath() {
		return uploadWorkPath;
	}

	/**
	 * Commons Upload が使用するワークフォルダを設定する。<br/>
	 * <br/>
	 * @param uploadWorkPath Commons Upload が使用するワークフォルダ
	 */
	public void setUploadWorkPath(String uploadWorkPath) {
		this.uploadWorkPath = uploadWorkPath;
	}

	/**
	 * Cookie Domain を設定する。<br/>
	 * <br/>
	 * @return Cookie Domain
	 */
	public String getCookieDomain() {
		return cookieDomain;
	}

	/**
	 * Cookie Domain を設定する。<br/>
	 * <br/>
	 * @param cookieDomain Cookie Domain
	 */
	public void setCookieDomain(String cookieDomain) {
		this.cookieDomain = cookieDomain;
	}

	/**
	 * サムネイル画像ファイルのサイズリストを取得する。<br/>
	 * <br/>
	 * @return サムネイル画像ファイルのサイズリスト
	 */
	public List<Integer> getThumbnailSizes() {
		return thumbnailSizes;
	}

	/**
	 * サムネイル画像ファイルのサイズリストを設定する。<br/>
	 * <br/>
	 * @param thumbnailSizes　サムネイル画像ファイルのサイズリスト
	 */
	public void setThumbnailSizes(List<Integer> thumbnailSizes) {
		this.thumbnailSizes = thumbnailSizes;
	}

	/**
	 * RequestScopeDataSource の Bean ID を取得する。<br/>
	 * この値は、トランザクションを手動制御する場合に使用される。<br/>
	 * <br/>
	 * @return RequestScopeDataSource の Bean ID
	 */
	public String getRequestScopeDataSourceId() {
		return requestScopeDataSourceId;
	}

	/**
	 * RequestScopeDataSource の Bean ID を設定する。<br/>
	 * <br/>
	 * @param requestScopeDataSourceId RequestScopeDataSource の Bean ID
	 */
	public void setRequestScopeDataSourceId(String requestScopeDataSourceId) {
		this.requestScopeDataSourceId = requestScopeDataSourceId;
	}

	/**
	 * 管理ユーザーテーブルの Alias 名を取得する。<br/>
	 * <br/>
	 * @return 管理ユーザーテーブルの Alias 名
	 */
	public String getAdminUserDbAlias() {
		return adminUserDbAlias;
	}

	/**
	 * 管理ユーザーテーブルの Alias 名を設定する。<br/>
	 * 認証用 DAO をカスタマイズで変更した際、現状の Alias 名を維持できない場合、
	 * このプロパティ値を変更する。 通常、この設置値を修正する事はない。<br/>
	 * <br/>
	 * @param adminUserDbAlias　管理ユーザーテーブルの Alias 名
	 */
	public void setAdminUserDbAlias(String adminUserDbAlias) {
		this.adminUserDbAlias = adminUserDbAlias;
	}

	/**
	 * 管理ユーザー権限テーブルの Alias 名を取得する。<br/>
	 * <br/>
	 * @return 管理ユーザー権限テーブルの Alias 名
	 */
	public String getAdminRoleDbAlias() {
		return adminRoleDbAlias;
	}

	/**
	 * 管理ユーザー権限テーブルの Alias 名を設定する。<br/>
	 * 認証用 DAO をカスタマイズで変更した際、現状の Alias 名を維持できない場合、
	 * このプロパティ値を変更する。 通常、この設置値を修正する事はない。<br/>
	 * <br/>
	 * @param adminRoleDbAlias　管理ユーザー権限テーブルの Alias 名
	 */
	public void setAdminRoleDbAlias(String adminRoleDbAlias) {
		this.adminRoleDbAlias = adminRoleDbAlias;
	}

	/**
	 * マイページ会員情報テーブルの Alias 名を取得する。<br/>
	 * <br/>
	 * @return マイページ会員権限テーブルの Alias 名
	 */
	public String getMemberDbAlias() {
		return memberDbAlias;
	}

	/**
	 * マイページ会員情報テーブルの Alias 名を設定する。<br/>
	 * 認証用 DAO をカスタマイズで変更した際、現状の Alias 名を維持できない場合、
	 * このプロパティ値を変更する。 通常、この設置値を修正する事はない。<br/>
	 * <br/>
	 * @param memberDbAlias　マイページ会員情報テーブルの Alias 名
	 */
	public void setMemberDbAlias(String memberDbAlias) {
		this.memberDbAlias = memberDbAlias;
	}

}
