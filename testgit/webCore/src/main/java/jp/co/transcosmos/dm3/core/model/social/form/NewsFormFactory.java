package jp.co.transcosmos.dm3.core.model.social.form;

import javax.servlet.http.HttpServletRequest;
import jp.co.transcosmos.dm3.core.util.LengthValidationUtils;
import jp.co.transcosmos.dm3.form.FormPopulator;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * form factory
 * 
 * @author nhatlv
 *
 */
public class NewsFormFactory {
	
	/** Form to create Factory of Bean ID */
	protected static String FACTORY_BEAN_ID = "newsFormFactory";
	
	/** manage lookup code */
	protected CodeLookupManager codeLookupManager;
	
	/** length validation */
    protected LengthValidationUtils lengthUtils;

	/**
	 * set object codeLookupManager<br/>
	 * <br/>
	 * @param codeLookupManager
	 */
	public void setCodeLookupManager(CodeLookupManager codeLookupManager) {
		this.codeLookupManager = codeLookupManager;
	}
	
	/**
	 * set length validation <br/>
	 * <br/>
	 * @param lengthUtils length validation
	 */
	public void setLengthUtils(LengthValidationUtils lengthUtils) {
		this.lengthUtils = lengthUtils;
	}

	/**
	 * create instance of form factory
	 * 
	 * @param request
	 * @return
	 */
	public static NewsFormFactory getInstance(HttpServletRequest request) {
		WebApplicationContext springContext = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
		return (NewsFormFactory)springContext.getBean(NewsFormFactory.FACTORY_BEAN_ID);
	}
	
	/**
	 * create search form
	 * 
	 * @return
	 */
	public NewsSearchForm createNewsSearchForm(){
		return new NewsSearchForm(this.lengthUtils);
	}

	/**
	 * create search form to receive data from request
	 * 
	 * @param request
	 * @return
	 */
	public NewsSearchForm createNewsSearchForm(HttpServletRequest request){
		NewsSearchForm form = createNewsSearchForm();
		FormPopulator.populateFormBeanFromRequest(request, form);	
		return form; 
	}
	
	/**
	 * create form input
	 * 
	 * @return
	 */
	public NewsForm createNewsForm(){
		return new NewsForm(this.lengthUtils, this.codeLookupManager);
	}

	/**
	 * create input form to receive data from request
	 * 
	 * @param request
	 * @return
	 */
	public NewsForm createNewsForm(HttpServletRequest request){
		NewsForm form = createNewsForm();
		FormPopulator.populateFormBeanFromRequest(request, form);
		return form;
	}
}
