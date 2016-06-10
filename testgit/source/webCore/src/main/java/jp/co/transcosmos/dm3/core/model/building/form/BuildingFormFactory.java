package jp.co.transcosmos.dm3.core.model.building.form;

import javax.servlet.http.HttpServletRequest;

import jp.co.transcosmos.dm3.core.util.LengthValidationUtils;
import jp.co.transcosmos.dm3.form.FormPopulator;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * <pre>
 * 建物情報検索・一覧用 Form の Factory クラス
 * getInstance() か、Spring からインスタンスを取得して使用する事。
 *
 * 担当者       修正日      修正内容
 * ------------ ----------- -----------------------------------------------------
 * I.Shu		2015.02.27	新規作成
 *
 * 注意事項
 * Factory のインスタンスを直接生成しない事。　必ずgetInstance() で取得する事。
 *
 * </pre>
 */
public class BuildingFormFactory {
	/** Form を生成する Factory の Bean ID */
	protected static String FACTORY_BEAN_ID = "buildingFormFactory";
	
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
	 * 建物情報の検索結果、および検索条件を格納する空の BuildingSearchForm のインスタンスを生成する。<br/>
	 * <br/>
	 * @return 空の BuildingSearchForm インスタンス 
	 */
	public BuildingSearchForm createBuildingSearchForm(){
		return new BuildingSearchForm(this.lengthUtils);
	}

	/**
	 * 建物情報の検索結果、および検索条件を格納する BuildingSearchForm のインスタンスを生成する。<br/>
	 * BuildingSearchForm には、リクエストパラメータに該当する値を設定する。<br/>
	 * <br/>
	 * @param request HTTP リクエスト
	 * @return リクエストパラメータを設定した BuildingSearchForm インスタンス 
	 */
	public BuildingSearchForm createBuildingSearchForm(HttpServletRequest request){
		BuildingSearchForm form = createBuildingSearchForm();
		FormPopulator.populateFormBeanFromRequest(request, form);	
		return form; 
	}
	
	/**
	 * BuildingFormFactory のインスタンスを取得する。<br/>
	 * Spring のコンテキストから、 BuildingFormFactory で定義された BuildingFormFactory の
	 * インスタンスを取得する。<br/>
	 * 取得されるインスタンスは、BuildingFormFactory を継承した拡張クラスが復帰される場合もある。<br/>
	 * <br/>
	 * @param request HTTP リクエスト
	 * @return BuildingFormFactory、または継承して拡張したクラスのインスタンス
	 */
	public static BuildingFormFactory getInstance(HttpServletRequest request) {

		WebApplicationContext springContext = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
		return (BuildingFormFactory)springContext.getBean(BuildingFormFactory.FACTORY_BEAN_ID);
	}
	
	/**
	 * 建物情報更新時の入力値を格納する空の BuildingForm のインスタンスを生成する。<br/>
	 * <br/>
	 * @return 空の BuildingForm のインスタンス
	 */
	public BuildingForm createBuildingForm(){
		return new BuildingForm(this.lengthUtils);
	}
	
	
	
	/**
	 * 建物情報更新時の入力値を格納する空の BuildingForm のインスタンスを生成する。<br/>
	 * <br/>
	 * @param request HTTP リクエスト
	 * @return リクエストパラメータを設定した BuildingForm のインスタンス
	 */
	public BuildingForm createBuildingForm(HttpServletRequest request){
		BuildingForm form = createBuildingForm();
		FormPopulator.populateFormBeanFromRequest(request, form);
		return form;
	}

	/**
	 * 最寄り駅情報更新時の入力値を格納する空の BuildingStationInfoForm のインスタンスを生成する。<br/>
	 * <br/>
	 * @param request HTTP リクエスト
	 * @return リクエストパラメータを設定した BuildingStationInfoForm のインスタンス
	 */
	public BuildingStationInfoForm createBuildingStationInfoForm(
			HttpServletRequest request) {
		BuildingStationInfoForm form = createBuildingStationInfoForm();
		FormPopulator.populateFormBeanFromRequest(request, form);
		return form;
	}
	
	/**
	 * 建物情報更新時の入力値を格納する空の BuildingForm のインスタンスを生成する。<br/>
	 * <br/>
	 * @return 空の BuildingForm のインスタンス
	 */
	public BuildingStationInfoForm createBuildingStationInfoForm(){
		return new BuildingStationInfoForm(this.lengthUtils, this.codeLookupManager);
	}
	
	/**
	 * 地域情報更新時の入力値を格納する空の BuildingLandmarkForm のインスタンスを生成する。<br/>
	 * <br/>
	 * @return 空の BuildingLandmarkForm のインスタンス
	 */
	public BuildingLandmarkForm createBuildingLandmarkForm(){
		return new BuildingLandmarkForm(this.lengthUtils, this.codeLookupManager);
	}
	
	/**
	 * 地域情報更新時の入力値を格納する空の BuildingLandmarkForm のインスタンスを生成する。<br/>
	 * <br/>
	 * @param request HTTP リクエスト
	 * @return リクエストパラメータを設定した BuildingLandmarkForm のインスタンス
	 */
	public BuildingLandmarkForm createBuildingLandmarkForm(
			HttpServletRequest request) {
		BuildingLandmarkForm form = createBuildingLandmarkForm();
		FormPopulator.populateFormBeanFromRequest(request, form);
		return form;
	}
}
