package jp.co.transcosmos.dm3.core.model.adminUser.form;

import javax.servlet.http.HttpServletRequest;

import jp.co.transcosmos.dm3.core.constant.CommonParameters;
import jp.co.transcosmos.dm3.core.util.LengthValidationUtils;
import jp.co.transcosmos.dm3.form.FormPopulator;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * 管理ユーザーメンテナンス用 Form の Factory クラス.
 * <p>
 * getInstance() か、Spring からインスタンスを取得して使用する事。
 * <p>
 * <pre>
 * 担当者       修正日      修正内容
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.01.28	新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 * Factory のインスタンスを直接生成しない事。　必ずgetInstance() で取得する事。
 * 
 */
public class AdminUserFormFactory {

	/** Form を生成する Factory の Bean ID */
	protected static String FACTORY_BEAN_ID = "adminUserFormFactory";

	/** 共通コード変換処理 */
	protected CodeLookupManager codeLookupManager;

	/** 共通パラメータオブジェクト */
    protected CommonParameters commonParameters;

	/** レングスバリデーション用ユーティリティ */
    protected LengthValidationUtils lengthUtils;


	
	/**
	 * 共通コード変換オブジェクトを設定する。<br/>
	 * <br/>
	 * @param codeLookupManager
	 */
	public void setCodeLookupManager(CodeLookupManager codeLookupManager) {
		this.codeLookupManager = codeLookupManager;
	}

	/**
	 * 共通パラメータオブジェクトを設定する。<br/>
	 * <br/>
	 * @param commonParameters 共通パラメータオブジェクト
	 */
	public void setCommonParameters(CommonParameters commonParameters) {
		this.commonParameters = commonParameters;
	}

	/**
	 * レングスバリデーション用ユーティリティを設定する。<br/>
	 * <br/>
	 * @param lengthUtils レングスバリデーション用ユーティリティ
	 */
	public void setLengthUtils(LengthValidationUtils lengthUtils) {
		this.lengthUtils = lengthUtils;
	}



	/**
	 * AdminUserFormFactory のインスタンスを取得する。<br/>
	 * Spring のコンテキストから、 adminUserFormFactory で定義された AdminUserFormFactory の
	 * インスタンスを取得する。<br/>
	 * 取得されるインスタンスは、adminUserFormFactory を継承した拡張クラスが復帰される場合もある。<br/>
	 * <br/>
	 * @param request HTTP リクエスト
	 * @return AdminUserFormFactory、または継承して拡張したクラスのインスタンス
	 */
	public static AdminUserFormFactory getInstance(HttpServletRequest request){
		WebApplicationContext springContext = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
		return (AdminUserFormFactory)springContext.getBean(AdminUserFormFactory.FACTORY_BEAN_ID);
	}



	/**
	 * 管理ユーザーの検索結果、および検索条件を格納する空の AdminUserSearchForm のインスタンスを生成する。<br/>
	 * <br/>
	 * @return 空の AdminUserSearchForm インスタンス 
	 */
	public AdminUserSearchForm createUserSearchForm(){
		return new AdminUserSearchForm(this.lengthUtils);
	}



	/**
	 * 管理ユーザーの検索結果、および検索条件を格納する AdminUserSearchForm のインスタンスを生成する。<br/>
	 * AdminUserSearchForm には、リクエストパラメータに該当する値を設定する。<br/>
	 * <br/>
	 * @param request HTTP リクエスト
	 * @return リクエストパラメータを設定した AdminUserSearchForm インスタンス 
	 */
	public AdminUserSearchForm createUserSearchForm(HttpServletRequest request){
		AdminUserSearchForm form = createUserSearchForm();
		FormPopulator.populateFormBeanFromRequest(request, form);	
		return form; 
	}



	/**
	 * CSV 出力用の管理ユーザーの検索結果、および検索条件を格納する空の AdminUserSearchCsvForm 
	 * のインスタンスを生成する。<br/>
	 * <br/>
	 * @return 空の AdminUserSearchCsvForm インスタンス 
	 */
	public AdminUserSearchCsvForm createUserSearchCsvForm(){
		return new AdminUserSearchCsvForm(this.lengthUtils);
	}



	/**
	 * CSV 出力用の管理ユーザーの検索結果、および検索条件を格納する AdminUserSearchCsvForm 
	 * のインスタンスを生成する。<br/>
	 * AdminUserSearchCsvForm には、リクエストパラメータに該当する値を設定する。<br/>
	 * <br/>
	 * @param request HTTP リクエスト
	 * @return リクエストパラメータを設定した AdminUserSearchCsvForm インスタンス 
	 */
	public AdminUserSearchCsvForm createUserSearchCsvForm(HttpServletRequest request){
		AdminUserSearchCsvForm form = createUserSearchCsvForm();
		FormPopulator.populateFormBeanFromRequest(request, form);	
		return form; 
	}



	/**
	 * 管理ユーザー更新時の入力値を格納する空の AdminUserForm のインスタンスを生成する。<br/>
	 * <br/>
	 * @return 空の AdminUserForm のインスタンス
	 */
	public AdminUserForm createAdminUserForm(){
		return new AdminUserForm(this.lengthUtils, this.codeLookupManager, this.commonParameters);
	}



	/**
	 * 管理ユーザー更新時の入力値を格納した AdminUserForm のインスタンスを生成する。<br/>
	 * <br/>
	 * @param request HTTP リクエスト
	 * @return リクエストパラメータを設定した AdminUserForm のインスタンス
	 */
	public AdminUserForm createAdminUserForm(HttpServletRequest request){
		AdminUserForm form = createAdminUserForm();
		FormPopulator.populateFormBeanFromRequest(request, form);
		return form;
	}



	/**
	 * 管理ユーザーパスワード変更の入力値を格納する空の PwdChangeForm のインスタンスを取得する。<br/>
	 * <br/>
	 * @return 空の PwdChangeForm のインスタンス
	 */
	public PwdChangeForm createPwdChangeForm(){
		return new PwdChangeForm(this.lengthUtils, this.commonParameters, this.codeLookupManager);
	}



	/**
	 * 管理ユーザーパスワード変更の入力値を格納した PwdChangeForm のインスタンスを生成する。<br/>
	 * <br/>
	 * @param request HTTP リクエスト
	 * @return リクエストパラメータを設定した PwdChangeForm のインスタンス
	 */
	public PwdChangeForm createPwdChangeForm(HttpServletRequest request){
		PwdChangeForm form = createPwdChangeForm();
		FormPopulator.populateFormBeanFromRequest(request, form);
		return form;
	}

}
