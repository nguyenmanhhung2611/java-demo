package jp.co.transcosmos.dm3.corePana.model.building.form;

import javax.servlet.http.HttpServletRequest;

import jp.co.transcosmos.dm3.core.model.building.form.BuildingFormFactory;
import jp.co.transcosmos.dm3.core.model.building.form.BuildingLandmarkForm;
import jp.co.transcosmos.dm3.core.model.building.form.BuildingSearchForm;
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
public class PanaBuildingFormFactory extends BuildingFormFactory {

    /**
     * InformationFormFactory のインスタンスを取得する。<br/>
     * Spring のコンテキストから、 HousingListFormFactory で定義された InformationFormFactory の
     * インスタンスを取得する。<br/>
     * 取得されるインスタンスは、HousingListFormFactory を継承した拡張クラスが復帰される場合もある。<br/>
     * <br/>
     * @param request HTTP リクエスト
     * @return HousingListFormFactory、または継承して拡張したクラスのインスタンス
     */
    public static PanaBuildingFormFactory getInstance(HttpServletRequest request) {
        WebApplicationContext springContext = WebApplicationContextUtils.getWebApplicationContext(request
                .getServletContext());
        return (PanaBuildingFormFactory) springContext.getBean(PanaBuildingFormFactory.FACTORY_BEAN_ID);
    }

    /**
     * 物件情報の検索結果、および検索条件を格納する空の HousingListForm のインスタンスを生成する。<br/>
     * <br/>
     * @return 空の HousingListForm インスタンス
     */
    public PanaBuildingForm createPanaBuildingForm() {
        return new PanaBuildingForm(this.lengthUtils);
    }

    /**
     * 物件情報の検索結果、および検索条件を格納する InformationSearchForm のインスタンスを生成する。<br/>
     * HousingListForm には、リクエストパラメータに該当する値を設定する。<br/>
     * <br/>
     * @param request HTTP リクエスト
     * @return リクエストパラメータを設定した HousingListForm インスタンス
     */
    public PanaBuildingForm createPanaBuildingForm(HttpServletRequest request) {
        PanaBuildingForm form = createPanaBuildingForm();
        FormPopulator.populateFormBeanFromRequest(request, form);
        return form;
    }

    /**
     * 物件情報の検索結果、および検索条件を格納する空の HousingListForm のインスタンスを生成する。<br/>
     * <br/>
     * @return 空の HousingListForm インスタンス
     */
    @Override
    public BuildingLandmarkForm createBuildingLandmarkForm() {
        return new PanaBuildingLandmarkForm(this.lengthUtils, this.codeLookupManager);
    }

    /**
     * 物件情報の検索結果、および検索条件を格納する InformationSearchForm のインスタンスを生成する。<br/>
     * HousingListForm には、リクエストパラメータに該当する値を設定する。<br/>
     * <br/>
     * @param request HTTP リクエスト
     * @return リクエストパラメータを設定した HousingListForm インスタンス
     */
    @Override
    public BuildingLandmarkForm createBuildingLandmarkForm(HttpServletRequest request) {
        BuildingLandmarkForm form = createBuildingLandmarkForm();
        FormPopulator.populateFormBeanFromRequest(request, form);
        return form;
    }

    /**
     * 物件情報の検索結果、および検索条件を格納する空の HousingListForm のインスタンスを生成する。<br/>
     * <br/>
     * @return 空の HousingListForm インスタンス
     */
    public PanaBuildingStationInfoForm createPanaBuildingStationInfoForm() {
        return new PanaBuildingStationInfoForm(this.lengthUtils, this.codeLookupManager);
    }

    /**
     * 物件情報の検索結果、および検索条件を格納する InformationSearchForm のインスタンスを生成する。<br/>
     * HousingListForm には、リクエストパラメータに該当する値を設定する。<br/>
     * <br/>
     * @param request HTTP リクエスト
     * @return リクエストパラメータを設定した HousingListForm インスタンス
     */
    public PanaBuildingStationInfoForm createPanaBuildingStationInfoForm(HttpServletRequest request) {
        PanaBuildingStationInfoForm form = createPanaBuildingStationInfoForm();
        FormPopulator.populateFormBeanFromRequest(request, form);
        return form;
    }

	/**
	 * 物件一覧の検索条件を格納する空の BuildingSearchForm のインスタンスを生成する。<br/>
	 * <br/>
	 * @return 空の BuildingSearchForm インスタンス
	 */
    @Override
	public BuildingSearchForm createBuildingSearchForm(){
		return new PanaBuildingSearchForm(this.lengthUtils);
	}

	/**
	 * 物件一覧の検索条件を格納する BuildingSearchForm のインスタンスを生成する。<br/>
	 * BuildingSearchForm には、リクエストパラメータに該当する値を設定する。<br/>
	 * <br/>
	 * @param request HTTP リクエスト
	 * @return リクエストパラメータを設定した BuildingSearchForm インスタンス
	 */
    @Override
	public BuildingSearchForm createBuildingSearchForm(HttpServletRequest request){
    	BuildingSearchForm form = createBuildingSearchForm();
		FormPopulator.populateFormBeanFromRequest(request, form);
		return form;
	}
}
