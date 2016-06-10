/**
 * 
 */
package jp.co.trancosmos.dm3.corePana.model.news.form;

import javax.servlet.http.HttpServletRequest;

import jp.co.transcosmos.dm3.core.model.housing.form.HousingFormFactory;
import jp.co.transcosmos.dm3.core.util.LengthValidationUtils;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingRequestForm;
import jp.co.transcosmos.dm3.form.FormPopulator;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * @author hiennt
 *
 */
public class NewsFormFactory {
	/** Form を生成する Factory の Bean ID */

	/** Form を生成する Factory の Bean ID */
	protected static String FACTORY_BEAN_ID = "newFormFactory";
	
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

	public static NewsFormFactory getInstance(HttpServletRequest request) {

		WebApplicationContext springContext = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
		return (NewsFormFactory)springContext.getBean(NewsFormFactory.FACTORY_BEAN_ID);
	}
	
	public NewsSearchForm createNewsSearchForm(){
		return new NewsSearchForm(this.lengthUtils);
	}

	public NewsSearchForm createNewSearchForm(HttpServletRequest request){
		NewsSearchForm form = createNewsSearchForm();
		FormPopulator.populateFormBeanFromRequest(request, form);	
		return form; 
	}
	
	public NewsRequestForm createNewsRequestForm(HttpServletRequest request){
		return new NewsRequestForm(this.lengthUtils, this.codeLookupManager);
	}

	public NewsRequestForm createNewForm(HttpServletRequest request){
		NewsRequestForm form = createNewsRequestForm(request);
		FormPopulator.populateFormBeanFromRequest(request, form);
		return form;
	}
	
	
}
