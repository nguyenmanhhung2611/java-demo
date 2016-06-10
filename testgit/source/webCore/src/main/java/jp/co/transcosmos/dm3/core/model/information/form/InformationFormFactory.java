package jp.co.transcosmos.dm3.core.model.information.form;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import jp.co.transcosmos.dm3.core.util.LengthValidationUtils;
import jp.co.transcosmos.dm3.form.FormPopulator;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;

/**
 * <pre>
 * お知らせ検索・一覧用 Form の Factory クラス
 * getInstance() か、Spring からインスタンスを取得して使用する事。
 *
 * 担当者       修正日      修正内容
 * ------------ ----------- -----------------------------------------------------
 * I.Shu		2015.02.05	新規作成
 *
 * 注意事項
 * Factory のインスタンスを直接生成しない事。　必ずgetInstance() で取得する事。
 *
 * </pre>
 */
public class InformationFormFactory {

	/** Form を生成する Factory の Bean ID */
	protected static String FACTORY_BEAN_ID = "informationFormFactory";
	
	/** 共通コード変換処理 */
	protected CodeLookupManager codeLookupManager;
	
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
	 * レングスバリデーション用ユーティリティを設定する。<br/>
	 * <br/>
	 * @param lengthUtils レングスバリデーション用ユーティリティ
	 */
	public void setLengthUtils(LengthValidationUtils lengthUtils) {
		this.lengthUtils = lengthUtils;
	}


	
	/**
	 * InformationFormFactory のインスタンスを取得する。<br/>
	 * Spring のコンテキストから、 InformationFormFactory で定義された InformationFormFactory の
	 * インスタンスを取得する。<br/>
	 * 取得されるインスタンスは、InformationFormFactory を継承した拡張クラスが復帰される場合もある。<br/>
	 * <br/>
	 * @param request HTTP リクエスト
	 * @return AdminUserFormFactory、または継承して拡張したクラスのインスタンス
	 */
	public static InformationFormFactory getInstance(HttpServletRequest request) {

		WebApplicationContext springContext = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
		return (InformationFormFactory)springContext.getBean(InformationFormFactory.FACTORY_BEAN_ID);
	}
	
	

	/**
	 * お知らせの検索結果、および検索条件を格納する空の AdminUserSearchForm のインスタンスを生成する。<br/>
	 * <br/>
	 * @return 空の AdminUserSearchForm インスタンス 
	 */
	public InformationSearchForm createInformationSearchForm(){
		return new InformationSearchForm(this.lengthUtils);
	}


	
	/**
	 * お知らせの検索結果、および検索条件を格納する InformationSearchForm のインスタンスを生成する。<br/>
	 * InformationSearchForm には、リクエストパラメータに該当する値を設定する。<br/>
	 * <br/>
	 * @param request HTTP リクエスト
	 * @return リクエストパラメータを設定した InformationSearchForm インスタンス 
	 */
	public InformationSearchForm createInformationSearchForm(HttpServletRequest request){
		InformationSearchForm form = createInformationSearchForm();
		FormPopulator.populateFormBeanFromRequest(request, form);	
		return form; 
	}

	
	
	/**
	 * お知らせ更新時の入力値を格納する空の InformationForm のインスタンスを生成する。<br/>
	 * <br/>
	 * @return 空の InformationForm のインスタンス
	 */
	public InformationForm createInformationForm(){
		return new InformationForm(this.lengthUtils, this.codeLookupManager);
	}

	
	
	/**
	 * お知らせ更新時の入力値を格納する空の InformationForm のインスタンスを生成する。<br/>
	 * <br/>
	 * @param request HTTP リクエスト
	 * @return リクエストパラメータを設定した InformationForm のインスタンス
	 */
	public InformationForm createInformationForm(HttpServletRequest request){
		InformationForm form = createInformationForm();
		FormPopulator.populateFormBeanFromRequest(request, form);
		return form;
	}
	
}
