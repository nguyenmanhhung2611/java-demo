package jp.co.transcosmos.dm3.core.model.social.form;

import javax.servlet.http.HttpServletRequest;

import jp.co.transcosmos.dm3.core.util.LengthValidationUtils;
import jp.co.transcosmos.dm3.form.FormPopulator;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * comment form factory
 * 
 * @author nhatlv
 *
 */
public class CommentFormFactory {
	
	/** Form to create Factory of Bean ID */
	protected static String FACTORY_BEAN_ID = "commentFormFactory";
	
	/** lookup keyword */
	protected CodeLookupManager codeLookupManager;
	
	/** length validation */
    protected LengthValidationUtils lengthUtils;

	/**
	 * set lookup keywordÅB<br/>
	 * <br/>
	 * @param codeLookupManager
	 */
	public void setCodeLookupManager(CodeLookupManager codeLookupManager) {
		this.codeLookupManager = codeLookupManager;
	}
	
	/**
	 * set length validationÅB<br/>
	 * <br/>
	 * @param lengthUtils length validation
	 */
	public void setLengthUtils(LengthValidationUtils lengthUtils) {
		this.lengthUtils = lengthUtils;
	}

	/**
	 * create instance of comment form factory
	 * 
	 * @param request
	 * @return
	 */
	public static CommentFormFactory getInstance(HttpServletRequest request) {
		WebApplicationContext springContext = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
		return (CommentFormFactory)springContext.getBean(CommentFormFactory.FACTORY_BEAN_ID);
	}
	
	/**
	 * create search form to save condition search data
	 * 
	 * @return
	 */
	public CommentSearchForm createCommentSearchForm(){
		return new CommentSearchForm(this.lengthUtils);
	}

	/**
	 * create search form to get data from request
	 * 
	 * @param request
	 * @return
	 */
	public CommentSearchForm createCommentSearchForm(HttpServletRequest request){
		CommentSearchForm form = createCommentSearchForm();
		FormPopulator.populateFormBeanFromRequest(request, form);	
		return form; 
	}
	
	/**
	 * create comment form
	 * 
	 * @return
	 */
	public CommentForm createCommentForm(){
		return new CommentForm(this.lengthUtils, this.codeLookupManager);
	}

	/**
	 * create comment form to get data from request
	 * 
	 * @param request
	 * @return
	 */
	public CommentForm createCommentForm(HttpServletRequest request){
		CommentForm form = createCommentForm();
		FormPopulator.populateFormBeanFromRequest(request, form);
		return form;
	}
}
