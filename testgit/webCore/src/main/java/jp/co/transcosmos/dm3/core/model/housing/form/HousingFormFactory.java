package jp.co.transcosmos.dm3.core.model.housing.form;

import javax.servlet.http.HttpServletRequest;

import jp.co.transcosmos.dm3.core.util.LengthValidationUtils;
import jp.co.transcosmos.dm3.form.FormPopulator;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;


/**
 * <pre>
 * 物件情報用 Form の Factory クラス
 * getInstance() か、Spring からインスタンスを取得して使用する事。
 *
 * 担当者       修正日      修正内容
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.03.11	新規作成
 *
 * 注意事項
 * Factory のインスタンスを直接生成しない事。　必ずgetInstance() で取得する事。
 *
 * </pre>
 */
public class HousingFormFactory {

	/** Form を生成する Factory の Bean ID */
	protected static String FACTORY_BEAN_ID = "housingFormFactory";

	/** レングスバリデーション用ユーティリティ */
    protected LengthValidationUtils lengthUtils;

	/** 共通コード変換処理 */
	protected CodeLookupManager codeLookupManager;



	/**
	 * レングスバリデーション用ユーティリティを設定する。<br/>
	 * <br/>
	 * @param lengthUtils レングスバリデーション用ユーティリティ
	 */
	public void setLengthUtils(LengthValidationUtils lengthUtils) {
		this.lengthUtils = lengthUtils;
	}

	/**
	 * 共通コード変換オブジェクトを設定する。<br/>
	 * <br/>
	 * @param codeLookupManager
	 */
	public void setCodeLookupManager(CodeLookupManager codeLookupManager) {
		this.codeLookupManager = codeLookupManager;
	}


    
	/**
	 * HousingFormFactory のインスタンスを取得する。<br/>
	 * Spring のコンテキストから、 housingFormFactory で定義された HousingFormFactory の
	 * インスタンスを取得する。<br/>
	 * 取得されるインスタンスは、HousingFormFactory を継承した拡張クラスが復帰される場合もある。<br/>
	 * <br/>
	 * @param request HTTP リクエスト
	 * @return HousingFormFactory、または継承して拡張したクラスのインスタンス
	 */
	public static HousingFormFactory getInstance(HttpServletRequest request){
		WebApplicationContext springContext = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
		return (HousingFormFactory)springContext.getBean(HousingFormFactory.FACTORY_BEAN_ID);
	}

	
	
	
	/**
	 * 物件基本情報の入力値を格納する空の HousingForm のインスタンスを生成する。<br/>
	 * <br/>
	 * @return 空の HousingForm インスタンス 
	 */
	public HousingForm createHousingForm(){
		return new HousingForm(this.lengthUtils, this.codeLookupManager);
	}


	
	/**
	 * 物件基本情報の入力値を格納した HousingForm のインスタンスを生成する。<br/>
	 * HousingForm には、リクエストパラメータに該当する値を設定する。<br/>
	 * <br/>
	 * @param request HTTP リクエスト
	 * @return リクエストパラメータを設定した HousingForm インスタンス 
	 */
	public HousingForm createHousingForm(HttpServletRequest request){
		HousingForm form = createHousingForm();
		FormPopulator.populateFormBeanFromRequest(request, form);	
		return form; 
	}

	
	
	/**
	 * 物件詳細情報の入力値を格納する空の HousingDtlForm のインスタンスを生成する。<br/>
	 * <br/>
	 * @return 空の HousingDtlForm インスタンス 
	 */
	public HousingDtlForm createHousingDtlForm(){
		return new HousingDtlForm(this.lengthUtils, this.codeLookupManager);
	}



	/**
	 * 物件基本情報の入力値を格納した HousingDtlForm のインスタンスを生成する。<br/>
	 * HousingDtlForm には、リクエストパラメータに該当する値を設定する。<br/>
	 * <br/>
	 * @param request HTTP リクエスト
	 * @return リクエストパラメータを設定した HousingDtlForm インスタンス 
	 */
	public HousingDtlForm createHousingDtlForm(HttpServletRequest request){
		HousingDtlForm form = createHousingDtlForm();
		FormPopulator.populateFormBeanFromRequest(request, form);	
		return form; 
	}
	
	
	
	/**
	 * 物件設備情報の入力値を格納する空の HousingEquipForm のインスタンスを生成する。<br/>
	 * <br/>
	 * @return 空の HousingEquipForm インスタンス 
	 */
	public HousingEquipForm createHousingEquipForm(){
		return new HousingEquipForm();
	}
	

	
	/**
	 * 物件設備情報の入力値を格納した HousingEquipForm のインスタンスを生成する。<br/>
	 * HousingEquipForm には、リクエストパラメータに該当する値を設定する。<br/>
	 * <br/>
	 * @param request HTTP リクエスト
	 * @return リクエストパラメータを設定した HousingEquipForm インスタンス 
	 */
	public HousingEquipForm createHousingEquipForm(HttpServletRequest request){
		HousingEquipForm form = createHousingEquipForm();
		FormPopulator.populateFormBeanFromRequest(request, form);	
		return form; 
	}

	
	
	/**
	 * 物件画像情報の入力値を格納する空の HousingImgForm のインスタンスを生成する。<br/>
	 * <br/>
	 * @return 空の HousingImgForm インスタンス 
	 */
	public HousingImgForm createHousingImgForm(){
		return new HousingImgForm(this.lengthUtils, this.codeLookupManager);
	}

	
	
	/**
	 * 物件画像情報の入力値を格納した HousingImgForm のインスタンスを生成する。<br/>
	 * HousingImgForm には、リクエストパラメータに該当する値を設定する。<br/>
	 * <br/>
	 * @param request HTTP リクエスト
	 * @return リクエストパラメータを設定した HousingImgForm インスタンス 
	 */
	public HousingImgForm createHousingImgForm(HttpServletRequest request){
		HousingImgForm form = createHousingImgForm();
		FormPopulator.populateFormBeanFromRequest(request, form);	
		return form; 
	}

	
	
	/**
	 * 物件検索条件の入力値を格納する空の HousingSearchForm のインスタンスを生成する。<br/>
	 * <br/>
	 * @return 空の HousingSearchForm インスタンス 
	 */
	public HousingSearchForm createHousingSearchForm(){
		return new HousingSearchForm();
	}

	
	
	/**
	 * 物件検索情報の入力値を格納した HousingSearchForm のインスタンスを生成する。<br/>
	 * HousingSearchForm には、リクエストパラメータに該当する値を設定する。<br/>
	 * <br/>
	 * @param request HTTP リクエスト
	 * @return リクエストパラメータを設定した HousingSearchForm インスタンス 
	 */
	public HousingSearchForm createHousingSearchForm(HttpServletRequest request){
		HousingSearchForm form = createHousingSearchForm();
		FormPopulator.populateFormBeanFromRequest(request, form);	
		return form; 
	}

}
