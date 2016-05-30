package jp.co.transcosmos.dm3.corePana.model.inquiry.form;

import javax.servlet.http.HttpServletRequest;

import jp.co.transcosmos.dm3.core.model.inquiry.form.InquiryFormFactory;
import jp.co.transcosmos.dm3.core.model.inquiry.form.InquiryHeaderForm;
import jp.co.transcosmos.dm3.core.model.inquiry.form.InquirySearchForm;
import jp.co.transcosmos.dm3.core.model.inquiry.form.InquiryStatusForm;
import jp.co.transcosmos.dm3.form.FormPopulator;

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
 * 郭中レイ		2015.04.02	新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 * Factory のインスタンスを直接生成しない事。　必ずgetInstance() で取得する事。
 *
 */
public class PanaInquiryFormFactory extends InquiryFormFactory {

	/**
	 * InquiryFormFactory のインスタンスを取得する。<br/>
	 * Spring のコンテキストから、 inquiryFormFactory で定義された InquiryFormFactory の
	 * インスタンスを取得する。<br/>
	 * 取得されるインスタンスは、InquiryFormFactory を継承した拡張クラスが復帰される場合もある。<br/>
	 * <br/>
	 * @param request HTTP リクエスト
	 * @return InquiryFormFactory、または継承して拡張したクラスのインスタンス
	 */
	public static PanaInquiryFormFactory getInstance(HttpServletRequest request){
		WebApplicationContext springContext = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
		return (PanaInquiryFormFactory)springContext.getBean(PanaInquiryFormFactory.FACTORY_BEAN_ID);
	}

	/**
	 * 問合せステータスの入力値を格納する空の InquiryStatusForm のインスタンスを生成する。<br/>
	 * <br/>
	 * @return 空の InquiryStatusForm インスタンス
	 */
	@Override
	public InquiryStatusForm createInquiryStatusForm(){
		return new PanaInquiryStatusForm(this.lengthUtils, this.codeLookupManager);
	}

	/**
	 * 問合せステータスの入力値を格納する空の PanaInquiryStatusForm のインスタンスを生成する。<br/>
	 * <br/>
	 * @return 空の PanaInquiryStatusForm インスタンス
	 */
	public PanaInquiryStatusForm createPanaInquiryStatusForm(){
		return new PanaInquiryStatusForm(this.lengthUtils, this.codeLookupManager);
	}

	/**
	 * 問合せステータスの入力値を格納した PanaInquiryStatusForm のインスタンスを生成する。<br/>
	 * PanaInquiryStatusForm には、リクエストパラメータに該当する値を設定する。<br/>
	 * <br/>
	 * @param request HTTP リクエスト
	 * @return リクエストパラメータを設定した PanaInquiryStatusForm インスタンス
	 */
	public PanaInquiryStatusForm createPanaInquiryStatusForm(HttpServletRequest request){
		PanaInquiryStatusForm form = createPanaInquiryStatusForm();
		FormPopulator.populateFormBeanFromRequest(request, form);
		return form;
	}

	/**
	 * 汎用問合せの入力値を格納する空の PanaInquirySearchForm のインスタンスを生成する。<br/>
	 * <br/>
	 * @return 空の PanaInquirySearchForm インスタンス
	 */
	public PanaInquirySearchForm createPanaInquirySearchForm(){
		return new PanaInquirySearchForm(this.lengthUtils, this.codeLookupManager);
	}

	/**
	 * 汎用問合せヘッダの入力値を格納した PanaInquirySearchForm のインスタンスを生成する。<br/>
	 * PanaInquirySearchForm には、リクエストパラメータに該当する値を設定する。<br/>
	 * <br/>
	 * @param request HTTP リクエスト
	 * @return リクエストパラメータを設定した PanaInquirySearchForm インスタンス
	 */
	public PanaInquirySearchForm createPanaInquirySearchForm(HttpServletRequest request){
		PanaInquirySearchForm form = createPanaInquirySearchForm();
		FormPopulator.populateFormBeanFromRequest(request, form);
		return form;
	}

	/**
	 * 物件のお問い合わせ入力値を格納する空の PanaInquiryHousingForm のインスタンスを生成する。<br/>
	 * <br/>
	 * @return 空の PanaInquiryHousingForm インスタンス
	 */
	public PanaInquiryHousingForm createPanaInquiryHousingForm(){
		return new PanaInquiryHousingForm(this.lengthUtils, this.codeLookupManager);
	}

	/**
	 * 物件のお問い合わせ入力値を格納した PanaInquiryHousingForm のインスタンスを生成する。<br/>
	 * PanaInquiryHousingForm には、リクエストパラメータに該当する値を設定する。<br/>
	 * <br/>
	 * @param request HTTP リクエスト
	 * @return リクエストパラメータを設定した PanaInquiryHousingForm インスタンス
	 */
	public PanaInquiryHousingForm createPanaInquiryHousingForm(HttpServletRequest request){
		PanaInquiryHousingForm form = createPanaInquiryHousingForm();
		FormPopulator.populateFormBeanFromRequest(request, form);
		return form;
	}

	/**
	 * 汎用お問い合わせ入力値を格納する空の PanaInquiryGeneralForm のインスタンスを生成する。<br/>
	 * <br/>
	 * @return 空の PanaInquiryGeneralForm インスタンス
	 */
	public PanaInquiryGeneralForm createPanaInquiryGeneralForm(){
		return new PanaInquiryGeneralForm(this.lengthUtils, this.codeLookupManager);
	}

	/**
	 * 汎用お問い合わせ入力値を格納した PanaInquiryGeneralForm のインスタンスを生成する。<br/>
	 * PanaInquiryGeneralForm には、リクエストパラメータに該当する値を設定する。<br/>
	 * <br/>
	 * @param request HTTP リクエスト
	 * @return リクエストパラメータを設定した PanaInquiryGeneralForm インスタンス
	 */
	public PanaInquiryGeneralForm createPanaInquiryGeneralForm(HttpServletRequest request){
		PanaInquiryGeneralForm form = createPanaInquiryGeneralForm();
		FormPopulator.populateFormBeanFromRequest(request, form);
		return form;
	}

	/**
	 * お問合せ情報（ヘッダ）の検索の入力値を格納する空の InquirySearchForm のインスタンスを生成する。<br/>
	 * <br/>
	 * @return 空の InquirySearchForm インスタンス
	 */
	@Override
	public InquirySearchForm createInquirySearchForm(){
		return new PanaInquirySearchForm(this.lengthUtils, this.codeLookupManager);
	}

	/**
	 * 問合せヘッダの入力値を格納する空の InquiryHeaderForm のインスタンスを生成する。<br/>
	 * <br/>
	 * @return 空の PanaInquiryHeaderForm インスタンス
	 */
	@Override
	public InquiryHeaderForm createInquiryHeaderForm(){
		return new PanaInquiryHeaderForm();
	}

}
