package jp.co.transcosmos.dm3.core.model.mypage.form;

import javax.servlet.http.HttpServletRequest;

import jp.co.transcosmos.dm3.core.constant.CommonParameters;
import jp.co.transcosmos.dm3.core.util.LengthValidationUtils;
import jp.co.transcosmos.dm3.form.FormPopulator;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;


/**
 * <pre>
 * マイページユーザー用 Form の Factory クラス
 * getInstance() か、Spring からインスタンスを取得して使用する事。
 *
 * 担当者       修正日      修正内容
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.02.20	新規作成
 * H.Mizuno		2015.03.31	パスワードリマインダ用の Form を追加
 *
 * 注意事項
 * Factory のインスタンスを直接生成しない事。　必ずgetInstance() で取得する事。
 *
 * </pre>
 */
public class MypageUserFormFactory {

	/** Form を生成する Factory の Bean ID */
	protected static String FACTORY_BEAN_ID = "mypageUserFormFactory";

	/** 共通コード変換処理 */
	protected CodeLookupManager codeLookupManager;
	
	/** 共通パラメータオブジェクト */
    protected CommonParameters commonParameters;

	/** レングスバリデーション用ユーティリティ */
    protected LengthValidationUtils lengthUtils;


    
    /**
     * 共通コード変換処理を設定する。<br/>
     * <br/>
     * @param codeLookupManager 共通コード変換処理
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
	 * MyPageUserFormFactory のインスタンスを取得する。<br/>
	 * Spring のコンテキストから、 myPageUserFormFactory で定義された MyPageUserFormFactory の
	 * インスタンスを取得する。<br/>
	 * 取得されるインスタンスは、MyPageUserFormFactory を継承した拡張クラスが復帰される場合もある。<br/>
	 * <br/>
	 * @param request HTTP リクエスト
	 * @return MyPageUserFormFactory、または継承して拡張したクラスのインスタンス
	 */
	public static MypageUserFormFactory getInstance(HttpServletRequest request){
		WebApplicationContext springContext = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
		return (MypageUserFormFactory)springContext.getBean(MypageUserFormFactory.FACTORY_BEAN_ID);
	}



	/**
	 * マイページ会員の検索結果、および検索条件を格納する空の MypageUserSearchForm のインスタンスを生成する。<br/>
	 * <br/>
	 * @return 空の MypageUserSearchForm インスタンス 
	 */
	public MypageUserSearchForm createMypageUserSearchForm() {
		return new MypageUserSearchForm(this.lengthUtils);
	}



	/**
	 * マイページ会員の検索結果、および検索条件を格納する MypageUserSearchForm のインスタンスを生成する。<br/>
	 * MypageUserSearchForm には、リクエストパラメータに該当する値を設定する。<br/>
	 * <br/>
	 * @param request HTTP リクエスト
	 * @return リクエストパラメータを設定した MypageUserSearchForm インスタンス 
	 */
	public MypageUserSearchForm createMypageUserSearchForm(HttpServletRequest request) {
		MypageUserSearchForm form = createMypageUserSearchForm();
		FormPopulator.populateFormBeanFromRequest(request, form);	
		return form; 
	}



	/**
	 * CSV 出力用のマイページ会員の検索結果、および検索条件を格納する空の MypageUserSearchCsvForm 
	 * のインスタンスを生成する。<br/>
	 * <br/>
	 * @return 空の MypageUserSearchCsvForm インスタンス 
	 */
	public MypageUserSearchCsvForm createMypageUserSearchCsvForm() {
		return new MypageUserSearchCsvForm(this.lengthUtils);
	}
	
	

	/**
	 * CSV 出力用のマイページ会員の検索結果、および検索条件を格納する MypageUserSearchCsvForm 
	 * のインスタンスを生成する。<br/>
	 * MypageUserSearchCsvForm には、リクエストパラメータに該当する値を設定する。<br/>
	 * <br/>
	 * @param request HTTP リクエスト
	 * @return リクエストパラメータを設定した MypageUserSearchCsvForm インスタンス 
	 */
	public MypageUserSearchCsvForm createMypageUserSearchCsvForm(HttpServletRequest request) {
		MypageUserSearchCsvForm form = createMypageUserSearchCsvForm();
		FormPopulator.populateFormBeanFromRequest(request, form);	
		return form; 
	}
	
	

	/**
	 * マイページ会員更新時の入力値を格納する空の MypageUserForm のインスタンスを生成する。<br/>
	 * <br/>
	 * @return 空の MypageUserForm のインスタンス
	 */
	public MypageUserForm createMypageUserForm() {
		return new MypageUserForm(this.lengthUtils, this.commonParameters, this.codeLookupManager);
	}



	/**
	 * マイページ会員更新時の入力値を格納した MypageUserForm のインスタンスを生成する。<br/>
	 * <br/>
	 * @param request HTTP リクエスト
	 * @return リクエストパラメータを設定した MypageUserForm のインスタンス
	 */
	public MypageUserForm createMypageUserForm(HttpServletRequest request) {
		MypageUserForm form = createMypageUserForm();
		FormPopulator.populateFormBeanFromRequest(request, form);	
		return form; 
	}


	/**
	 * パスワードリマインダ登録時の入力値を格納する空の RemindForm のインスタンスを生成する。<br/>
	 * <br/>
	 * @return 空の RemindForm のインスタンス
	 */
	public RemindForm createRemindForm() {
		return new RemindForm();
	}

	
	
	/**
	 * パスワードリマインダ登録時の入力値を格納した RemindForm のインスタンスを生成する。<br/>
	 * <br/>
	 * @param request HTTP リクエスト
	 * @return リクエストパラメータを設定した RemindForm のインスタンス
	 */
	public RemindForm createRemindForm(HttpServletRequest request) {
		RemindForm form = createRemindForm();
		FormPopulator.populateFormBeanFromRequest(request, form);	
		return form; 
	}

	
	
	/**
	 * パスワード変更時の入力値を格納する空の PwdChangeForm のインスタンスを生成する。<br/>
	 * <br/>
	 * @return 空の PwdChangeForm のインスタンス
	 */
	public PwdChangeForm createPwdChangeForm() {
		return new PwdChangeForm();
	}

	
	
	/**
	 * パスワード変更時時の入力値を格納した PwdChangeForm のインスタンスを生成する。<br/>
	 * <br/>
	 * @param request HTTP リクエスト
	 * @return リクエストパラメータを設定した PwdChangeForm のインスタンス
	 */
	public PwdChangeForm createPwdChangeForm(HttpServletRequest request) {
		PwdChangeForm form = createPwdChangeForm();
		FormPopulator.populateFormBeanFromRequest(request, form);	
		return form; 
	}

}
