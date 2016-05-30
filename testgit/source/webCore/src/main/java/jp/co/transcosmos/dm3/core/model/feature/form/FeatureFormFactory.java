package jp.co.transcosmos.dm3.core.model.feature.form;

import javax.servlet.http.HttpServletRequest;

import jp.co.transcosmos.dm3.form.FormPopulator;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;


/**
 * <pre>
 * 特集用 Form の Factory クラス
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
public class FeatureFormFactory {

	/** Form を生成する Factory の Bean ID */
	protected static String FACTORY_BEAN_ID = "featureFormFactory";



	/**
	 * FeatureFormFactory のインスタンスを取得する。<br/>
	 * Spring のコンテキストから、 featureFormFactory で定義された FeatureFormFactory の
	 * インスタンスを取得する。<br/>
	 * 取得されるインスタンスは、FeatureFormFactory を継承した拡張クラスが復帰される場合もある。<br/>
	 * <br/>
	 * @param request HTTP リクエスト
	 * @return FeatureFormFactory、または継承して拡張したクラスのインスタンス
	 */
	public static FeatureFormFactory getInstance(HttpServletRequest request){
		WebApplicationContext springContext = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
		return (FeatureFormFactory)springContext.getBean(FeatureFormFactory.FACTORY_BEAN_ID);
	}

	
	
	/**
	 * 特集に該当する物件の検索条件を格納する空の FeatureSearchForm のインスタンスを生成する。<br/>
	 * <br/>
	 * @return 空の FeatureSearchForm インスタンス 
	 */
	public FeatureSearchForm createFeatureSearchForm(){
		return new FeatureSearchForm();
	}

	
	
	/**
	 * 特集に該当する物件の検索条件を格納した FeatureSearchForm のインスタンスを生成する。<br/>
	 * FeatureSearchForm には、リクエストパラメータに該当する値を設定する。<br/>
	 * <br/>
	 * @param request HTTP リクエスト
	 * @return リクエストパラメータを設定した FeatureSearchForm インスタンス 
	 */
	public FeatureSearchForm createFeatureSearchForm(HttpServletRequest request){
		FeatureSearchForm form = createFeatureSearchForm();
		FormPopulator.populateFormBeanFromRequest(request, form);	
		return form; 
	}

}
