package jp.co.transcosmos.dm3.core.model.inquiry.form;

import javax.servlet.http.HttpServletRequest;

import jp.co.transcosmos.dm3.core.util.LengthValidationUtils;
import jp.co.transcosmos.dm3.form.FormPopulator;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * お問合せ機能用 Form の Factory クラス.
 * <p>
 * getInstance() か、Spring からインスタンスを取得して使用する事。
 * <p>
 * <pre>
 * 担当者       修正日      修正内容
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.04.02	新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 * Factory のインスタンスを直接生成しない事。　必ずgetInstance() で取得する事。
 * 
 */
public class InquiryFormFactory {

	/** Form を生成する Factory の Bean ID */
	protected static String FACTORY_BEAN_ID = "inquiryFormFactory";
	
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
	 * InquiryFormFactory のインスタンスを取得する。<br/>
	 * Spring のコンテキストから、 inquiryFormFactory で定義された InquiryFormFactory の
	 * インスタンスを取得する。<br/>
	 * 取得されるインスタンスは、InquiryFormFactory を継承した拡張クラスが復帰される場合もある。<br/>
	 * <br/>
	 * @param request HTTP リクエスト
	 * @return InquiryFormFactory、または継承して拡張したクラスのインスタンス
	 */
	public static InquiryFormFactory getInstance(HttpServletRequest request){
		WebApplicationContext springContext = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
		return (InquiryFormFactory)springContext.getBean(InquiryFormFactory.FACTORY_BEAN_ID);
	}



	/**
	 * 問合せヘッダの入力値を格納する空の InquiryHeaderForm のインスタンスを生成する。<br/>
	 * <br/>
	 * @return 空の InquiryHeaderForm インスタンス 
	 */
	public InquiryHeaderForm createInquiryHeaderForm(){
		return new InquiryHeaderForm();
	}

	/**
	 * 問合せヘッダの入力値を格納した InquiryHeaderForm のインスタンスを生成する。<br/>
	 * InquiryHeaderForm には、リクエストパラメータに該当する値を設定する。<br/>
	 * <br/>
	 * @param request HTTP リクエスト
	 * @return リクエストパラメータを設定した InquiryHeaderForm インスタンス 
	 */
	public InquiryHeaderForm createInquiryHeaderForm(HttpServletRequest request){
		InquiryHeaderForm form = createInquiryHeaderForm();
		FormPopulator.populateFormBeanFromRequest(request, form);	
		return form; 
	}

	
	
	/**
	 * 物件問合せの入力値を格納する空の HousingInquiryForm のインスタンスを生成する。<br/>
	 * <br/>
	 * @return 空の HousingInquiryForm インスタンス 
	 */
	public HousingInquiryForm createHousingInquiryForm(){
		return new HousingInquiryForm();
	}

	/**
	 * 物件問合せヘッダの入力値を格納した HousingInquiryForm のインスタンスを生成する。<br/>
	 * HousingInquiryForm には、リクエストパラメータに該当する値を設定する。<br/>
	 * <br/>
	 * @param request HTTP リクエスト
	 * @return リクエストパラメータを設定した HousingInquiryForm インスタンス 
	 */
	public HousingInquiryForm createHousingInquiryForm(HttpServletRequest request){
		HousingInquiryForm form = createHousingInquiryForm();
		FormPopulator.populateFormBeanFromRequest(request, form);	
		return form; 
	}
	
	/**
	 * 問合せステータスの入力値を格納する空の InquiryStatusForm のインスタンスを生成する。<br/>
	 * <br/>
	 * @return 空の InquiryStatusForm インスタンス 
	 */
	public InquiryStatusForm createInquiryStatusForm(){
		return new InquiryStatusForm(this.lengthUtils, this.codeLookupManager);
	}
	
	/**
	 * 問合せステータスの入力値を格納した InquiryStatusForm のインスタンスを生成する。<br/>
	 * InquiryStatusForm には、リクエストパラメータに該当する値を設定する。<br/>
	 * <br/>
	 * @param request HTTP リクエスト
	 * @return リクエストパラメータを設定した InquiryStatusForm インスタンス 
	 */
	public InquiryStatusForm createInquiryStatusForm(HttpServletRequest request){
		InquiryStatusForm form = createInquiryStatusForm();
		FormPopulator.populateFormBeanFromRequest(request, form);	
		return form; 
	}
	
	/**
	 * お問合せ情報（ヘッダ）の検索の入力値を格納する空の InquirySearchForm のインスタンスを生成する。<br/>
	 * <br/>
	 * @return 空の InquirySearchForm インスタンス 
	 */
	public InquirySearchForm createInquirySearchForm(){
		return new InquirySearchForm(this.lengthUtils, this.codeLookupManager);
	}
	
	/**
	 * お問合せ情報（ヘッダ）の検索の入力値を格納した InquiryStatusForm のインスタンスを生成する。<br/>
	 * InquirySearchForm には、リクエストパラメータに該当する値を設定する。<br/>
	 * <br/>
	 * @param request HTTP リクエスト
	 * @return リクエストパラメータを設定した InquirySearchForm インスタンス 
	 */
	public InquirySearchForm createInquirySearchForm(HttpServletRequest request){
		InquirySearchForm form = createInquirySearchForm();
		FormPopulator.populateFormBeanFromRequest(request, form);	
		return form; 
	}

}
