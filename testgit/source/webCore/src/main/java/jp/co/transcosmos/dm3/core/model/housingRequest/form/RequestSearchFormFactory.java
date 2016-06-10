package jp.co.transcosmos.dm3.core.model.housingRequest.form;

import javax.servlet.http.HttpServletRequest;
import jp.co.transcosmos.dm3.form.FormPopulator;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * <pre>
 * 物件リクエスト用 Form の Factory クラス
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
public class RequestSearchFormFactory {

	/** Form を生成する Factory の Bean ID */
	protected static String FACTORY_BEAN_ID = "requestSearchFormFactory";



	/**
	 * RequestSearchFormFactory のインスタンスを取得する。<br/>
	 * Spring のコンテキストから、 requestSearchFormFactory で定義された RequestSearchFormFactory の
	 * インスタンスを取得する。<br/>
	 * 取得されるインスタンスは、RequestSearchFormFactory を継承した拡張クラスが復帰される場合もある。<br/>
	 * <br/>
	 * @param request HTTP リクエスト
	 * @return RequestSearchFormFactory、または継承して拡張したクラスのインスタンス
	 */
	public static RequestSearchFormFactory getInstance(HttpServletRequest request){
		WebApplicationContext springContext = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
		return (RequestSearchFormFactory)springContext.getBean(RequestSearchFormFactory.FACTORY_BEAN_ID);
	}



	/**
	 * 物件リクエストに該当する物件を検索するパラメータを受取る空の RequestSearchForm のインスタンスを生成する。<br/>
	 * <br/>
	 * @return 空の RequestSearchForm インスタンス 
	 */
	public RequestSearchForm createRequestSearchForm(){
		return new RequestSearchForm();
	}



	/**
	 * 物件リクエストに該当する物件を検索するパラメータを格納した RequestSearchForm のインスタンスを生成する。<br/>
	 * RequestSearchForm には、リクエストパラメータに該当する値を設定する。<br/>
	 * <br/>
	 * @param request HTTP リクエスト
	 * @return リクエストパラメータを設定した RequestSearchForm インスタンス 
	 */
	public RequestSearchForm createRequestSearchForm(HttpServletRequest request){
		RequestSearchForm form = createRequestSearchForm();
		FormPopulator.populateFormBeanFromRequest(request, form);	
		return form; 
	}

}
