package jp.co.transcosmos.dm3.corePana.model.housing.form;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

import jp.co.transcosmos.dm3.core.model.housing.form.HousingFormFactory;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonConstant;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonParameters;
import jp.co.transcosmos.dm3.form.FormPopulator;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * <pre>
 * 物件情報検索・一覧用 Form の Factory クラス
 * getInstance() か、Spring からインスタンスを取得して使用する事。
 *
 * 担当者       修正日      修正内容
 * ------------ ----------- -----------------------------------------------------
 * guo.zhonglei		2015.04.08	新規作成
 *
 * 注意事項
 * Factory のインスタンスを直接生成しない事。　必ずgetInstance() で取得する事。
 *
 * </pre>
 */
public class PanaHousingFormFactory extends HousingFormFactory {


	/** 共通パラメータオブジェクト */
	private PanaCommonParameters commonParameters;

	/**
	 * 共通パラメータオブジェクトを設定する。<br/>
	 * <br/>
	 *
	 * @param commonParameters
	 *            共通パラメータオブジェクト
	 */
	public void setCommonParameters(PanaCommonParameters commonParameters) {
		this.commonParameters = commonParameters;
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
	public static PanaHousingFormFactory getInstance(HttpServletRequest request){
		WebApplicationContext springContext = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
		return (PanaHousingFormFactory)springContext.getBean(HousingFormFactory.FACTORY_BEAN_ID);
	}

	/**
	 * 物件情報の検索結果、および検索条件を格納する空の HousingListForm のインスタンスを生成する。<br/>
	 * <br/>
	 * @return 空の HousingListForm インスタンス
	 */
	public PanaHousingStatusForm createPanaHousingStatusForm() {
		return new PanaHousingStatusForm(this.codeLookupManager);
	}

	/**
	 * 物件情報の検索結果、および検索条件を格納する InformationSearchForm のインスタンスを生成する。<br/>
	 * HousingListForm には、リクエストパラメータに該当する値を設定する。<br/>
	 * <br/>
	 * @param request HTTP リクエスト
	 * @return リクエストパラメータを設定した HousingListForm インスタンス
	 */
	public PanaHousingStatusForm createPanaHousingStatusForm(HttpServletRequest request) {
		PanaHousingStatusForm form = createPanaHousingStatusForm();
		FormPopulator.populateFormBeanFromRequest(request, form);
		return form;
	}

	/**
	 * 物件詳細情報の検索結果を格納する空の PanaHousingDtlInfoForm のインスタンスを生成する。<br/>
	 * <br/>
	 * @return 空の PanaHousingDtlInfoForm インスタンス
	 */
	public PanaHousingDtlInfoForm createPanaHousingDtlInfoForm() {
		return new PanaHousingDtlInfoForm(this.lengthUtils, this.codeLookupManager);
	}

	/**
	 * 物件詳細情報の検索結果を格納する PanaHousingDtlInfoForm のインスタンスを生成する。<br/>
	 * PanaHousingDtlInfoForm には、リクエストパラメータに該当する値を設定する。<br/>
	 * <br/>
	 * @param request HTTP リクエスト
	 * @return リクエストパラメータを設定した PanaHousingDtlInfoForm インスタンス
	 */
	public PanaHousingDtlInfoForm createPanaHousingDtlInfoForm(HttpServletRequest request) {
		PanaHousingDtlInfoForm form = createPanaHousingDtlInfoForm();
		FormPopulator.populateFormBeanFromRequest(request, form);
		return form;
	}

	/**
	 * 物件詳細情報の検索結果、および検索条件を格納するフォームのインスタンス配列を生成する。<br/>
	 * forms には、リクエストパラメータに該当する値を設定する。<br/>
	 * <br/>
	 * @param request HTTP リクエスト
	 * @return リクエストパラメータを設定したフォームインスタンス配列
	 */
	public Object[] createPanaHousingDtlInfoFormAndSearchForm(HttpServletRequest request) {
		Object[] forms = new Object[2];
		forms[0] = createPanaHousingDtlInfoForm();
		forms[1] = createPanaHousingSearchForm();
		FormPopulator.populateFormBeanFromRequest(request, forms, PanaCommonConstant.maxFileSize, new File(commonParameters.getUploadWorkPath()));
		return forms;
	}

	/**
	 * 物件画像情報の検索結果、および検索条件を格納するフォームのインスタンス配列を生成する。<br/>
	 * forms には、リクエストパラメータに該当する値を設定する。<br/>
	 * <br/>
	 * @param request HTTP リクエスト
	 * @return リクエストパラメータを設定したフォームインスタンス配列
	 */
	public Object[] createPanaHousingImgInfoFormAndSearchForm(HttpServletRequest request) {
		Object[] forms = new Object[2];
		forms[0] = createPanaHousingImageInfoForm();
		forms[1] = createPanaHousingSearchForm();
		FormPopulator.populateFormBeanFromRequest(request, forms, PanaCommonConstant.maxFileSize, new File(commonParameters.getUploadWorkPath()));
		return forms;
	}

	/**
	 * 物件画像情報の検索結果、および検索条件を格納するフォームのインスタンス配列を生成する。<br/>
	 * forms には、リクエストパラメータに該当する値を設定する。<br/>
	 * <br/>
	 * @param request HTTP リクエスト
	 * @return リクエストパラメータを設定したフォームインスタンス配列
	 */
	public Object[] createPanaHousingInspectionFormAndSearchForm(HttpServletRequest request) {
		Object[] forms = new Object[2];
		forms[0] = createPanaHousingInspectionForm();
		forms[1] = createPanaHousingSearchForm();
		FormPopulator.populateFormBeanFromRequest(request, forms, PanaCommonConstant.maxFileSize, new File(commonParameters.getUploadWorkPath()));
		return forms;
	}

	/**
	 * 物件情報の検索結果、および検索条件を格納する空の HousingListForm のインスタンスを生成する。<br/>
	 * <br/>
	 * @return 空の HousingListForm インスタンス
	 */
	public PanaHousingForm createPanaHousingForm() {
		return new PanaHousingForm(this.lengthUtils, this.codeLookupManager);
	}

	/**
	 * 物件情報の検索結果、および検索条件を格納する InformationSearchForm のインスタンスを生成する。<br/>
	 * HousingListForm には、リクエストパラメータに該当する値を設定する。<br/>
	 * <br/>
	 * @param request HTTP リクエスト
	 * @return リクエストパラメータを設定した HousingListForm インスタンス
	 */
	public PanaHousingForm createPanaHousingForm(HttpServletRequest request) {
		PanaHousingForm form = createPanaHousingForm();
		FormPopulator.populateFormBeanFromRequest(request, form);
		return form;
	}

	/**
	 * 物件情報の検索結果、および検索条件を格納する空の HousingListForm のインスタンスを生成する。<br/>
	 * <br/>
	 * @return 空の HousingListForm インスタンス
	 */
	public PanaHousingInfoForm createPanaHousingInfoForm() {
		return new PanaHousingInfoForm(this.codeLookupManager);
	}

	/**
	 * 物件情報の検索結果、および検索条件を格納する InformationSearchForm のインスタンスを生成する。<br/>
	 * HousingListForm には、リクエストパラメータに該当する値を設定する。<br/>
	 * <br/>
	 * @param request HTTP リクエスト
	 * @return リクエストパラメータを設定した HousingListForm インスタンス
	 */
	public PanaHousingInfoForm createPanaHousingInfoForm(HttpServletRequest request) {
		PanaHousingInfoForm form = createPanaHousingInfoForm();
		FormPopulator.populateFormBeanFromRequest(request, form);
		return form;
	}

	/**
	 * 物件画像情報の検索結果、および検索条件を格納する空の PanaHousingImageInfoForm のインスタンスを生成する。<br/>
	 * <br/>
	 * @return 空の PanaHousingImageInfoForm インスタンス
	 */
	public PanaHousingImageInfoForm createPanaHousingImageInfoForm() {
		return new PanaHousingImageInfoForm(this.codeLookupManager);
	}

	/**
	 * 物件画像情報の検索結果、および検索条件を格納する PanaHousingImageInfoForm のインスタンスを生成する。<br/>
	 * PanaHousingImageInfoForm には、リクエストパラメータに該当する値を設定する。<br/>
	 * <br/>
	 * @param request HTTP リクエスト
	 * @return リクエストパラメータを設定した PanaHousingImageInfoForm インスタンス
	 */
	public PanaHousingImageInfoForm createPanaHousingImageInfoForm(HttpServletRequest request) {
		PanaHousingImageInfoForm form = createPanaHousingImageInfoForm();
		FormPopulator.populateFormBeanFromRequest(request, form);
		return form;
	}

	/**
	 * 物件情報の検索結果、および検索条件を格納する空の HousingListForm のインスタンスを生成する。<br/>
	 * <br/>
	 * @return 空の HousingListForm インスタンス
	 */
	public PanaHousingInspectionForm createPanaHousingInspectionForm() {
		return new PanaHousingInspectionForm(this.codeLookupManager);
	}

	/**
	 * 物件情報の検索結果、および検索条件を格納する InformationSearchForm のインスタンスを生成する。<br/>
	 * HousingListForm には、リクエストパラメータに該当する値を設定する。<br/>
	 * <br/>
	 * @param request HTTP リクエスト
	 * @return リクエストパラメータを設定した HousingListForm インスタンス
	 */
	public PanaHousingInspectionForm createPanaHousingInspectionForm(HttpServletRequest request) {
		PanaHousingInspectionForm form = createPanaHousingInspectionForm();
		FormPopulator.populateFormBeanFromRequest(request, form);
		return form;
	}

	/**
	 * 物件情報の検索結果、および検索条件を格納する空の HousingListForm のインスタンスを生成する。<br/>
	 * <br/>
	 * @return 空の HousingListForm インスタンス
	 */
	public PanaHousingSearchForm createPanaHousingSearchForm() {
		return new PanaHousingSearchForm(this.codeLookupManager, this.lengthUtils);
	}

	/**
	 * 物件情報の検索結果、および検索条件を格納する InformationSearchForm のインスタンスを生成する。<br/>
	 * HousingListForm には、リクエストパラメータに該当する値を設定する。<br/>
	 * <br/>
	 * @param request HTTP リクエスト
	 * @return リクエストパラメータを設定した HousingListForm インスタンス
	 */
	public PanaHousingSearchForm createPanaHousingSearchForm(HttpServletRequest request) {
		PanaHousingSearchForm form = createPanaHousingSearchForm();
		FormPopulator.populateFormBeanFromRequest(request, form);
		return form;
	}

	/**
	 * 物件情報の検索結果、および検索条件を格納する空の HousingListForm のインスタンスを生成する。<br/>
	 * <br/>
	 * @return 空の HousingListForm インスタンス
	 */
	public PanaHousingSpecialtyForm createPanaHousingSpecialtyForm() {
		return new PanaHousingSpecialtyForm();
	}

	/**
	 * 物件情報の検索結果、および検索条件を格納する InformationSearchForm のインスタンスを生成する。<br/>
	 * HousingListForm には、リクエストパラメータに該当する値を設定する。<br/>
	 * <br/>
	 * @param request HTTP リクエスト
	 * @return リクエストパラメータを設定した HousingListForm インスタンス
	 */
	public PanaHousingSpecialtyForm createPanaHousingSpecialtyForm(HttpServletRequest request) {
		PanaHousingSpecialtyForm form = createPanaHousingSpecialtyForm();
		FormPopulator.populateFormBeanFromRequest(request, form);
		return form;
	}

	/**
	 * 物件情報の検索結果、および検索条件を格納する空の HousingListForm のインスタンスを生成する。<br/>
	 * <br/>
	 * @return 空の HousingListForm インスタンス
	 */
	public RecommendPointForm createRecommendPointForm() {
		return new RecommendPointForm(this.lengthUtils, this.codeLookupManager);
	}

	/**
	 * 物件情報の検索結果、および検索条件を格納する InformationSearchForm のインスタンスを生成する。<br/>
	 * HousingListForm には、リクエストパラメータに該当する値を設定する。<br/>
	 * <br/>
	 * @param request HTTP リクエスト
	 * @return リクエストパラメータを設定した HousingListForm インスタンス
	 */
	public RecommendPointForm createRecommendPointForm(HttpServletRequest request) {
		RecommendPointForm form = createRecommendPointForm();
		FormPopulator.populateFormBeanFromRequest(request, form);
		return form;
	}

	/**
	 * 物件リクエスト入力、確認、完了画面の PanaHousingRequestForm のインスタンスを生成する。<br/>
	 * <br/>
	 * @return 空の PanaHousingRequestForm インスタンス
	 */
	public PanaHousingRequestForm createPanaHousingRequestForm() {
		return new PanaHousingRequestForm(this.codeLookupManager);
	}

	/**
	 * 物件リクエスト入力、確認、完了画面のインスタンスを生成する。<br/>
	 * PanaHousingRequestForm には、リクエストパラメータに該当する値を設定する。<br/>
	 * <br/>
	 * @param request HTTP リクエスト
	 * @return リクエストパラメータを設定した PanaHousingRequestForm インスタンス
	 */
	public PanaHousingRequestForm createPanaHousingRequestForm(HttpServletRequest request) {
		PanaHousingRequestForm form = createPanaHousingRequestForm();
		FormPopulator.populateFormBeanFromRequest(request, form);
		return form;
	}

	/**
	 * 物件検索条件の入力値を格納する空の HousingSearchForm のインスタンスを生成する。<br/>
	 * <br/>
	 * @return 空の HousingSearchForm インスタンス
	 */
	public PanaHousingSearchForm createHousingSearchForm(){
		return new PanaHousingSearchForm(codeLookupManager, lengthUtils);

	}



	/**
	 * 物件検索情報の入力値を格納した HousingSearchForm のインスタンスを生成する。<br/>
	 * HousingSearchForm には、リクエストパラメータに該当する値を設定する。<br/>
	 * <br/>
	 * @param request HTTP リクエスト
	 * @return リクエストパラメータを設定した HousingSearchForm インスタンス
	 */
	public PanaHousingSearchForm createHousingSearchForm(HttpServletRequest request){
		PanaHousingSearchForm form = createHousingSearchForm();
		FormPopulator.populateFormBeanFromRequest(request, form);
		return form;
	}

	/**
	 * 特集に該当する物件の検索条件を格納する空の FeatureSearchForm のインスタンスを生成する。<br/>
	 * <br/>
	 * @return 空の FeatureSearchForm インスタンス
	 */
	public PanaFeatureSearchForm createFeatureSearchForm(){
		return new PanaFeatureSearchForm(codeLookupManager, lengthUtils);
	}



	/**
	 * 特集に該当する物件の検索条件を格納した FeatureSearchForm のインスタンスを生成する。<br/>
	 * FeatureSearchForm には、リクエストパラメータに該当する値を設定する。<br/>
	 * <br/>
	 * @param request HTTP リクエスト
	 * @return リクエストパラメータを設定した FeatureSearchForm インスタンス
	 */
	public PanaFeatureSearchForm createFeatureSearchForm(HttpServletRequest request){
		PanaFeatureSearchForm form = createFeatureSearchForm();
		FormPopulator.populateFormBeanFromRequest(request, form);
		return form;
	}

}
