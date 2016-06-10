package jp.co.transcosmos.dm3.corePana.model.comment.form;

import javax.servlet.http.HttpServletRequest;
import jp.co.transcosmos.dm3.core.util.LengthValidationUtils;
import jp.co.transcosmos.dm3.form.FormPopulator;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * 
 * @author thoph
 *
 */
public class CommentFormFactory {
	
	/** To generate a Form Factory of Bean ID */
	private static String FACTORY_BEAN_ID = "commentFormFactory";

	/** Common code conversion process */
	private CodeLookupManager codeLookupManager;
	
	/** Length validation for the utility */
	private LengthValidationUtils lengthUtils;

	public void setCodeLookupManager(CodeLookupManager codeLookupManager) {
		this.codeLookupManager = codeLookupManager;
	}

	public void setLengthUtils(LengthValidationUtils lengthUtils) {
		this.lengthUtils = lengthUtils;
	}
	
	/**
	      * Get an instance of CommentFormFactory. <br/>
	      * From Spring of context, of CommentFormFactory defined in CommentFormFactory
	      * Get the instance. <br/>
	      * Instance that is acquired, there is a case where the extended class that inherits the CommentFormFactory is restored. <br/>
	      * <br/>
	      * @param Request HTTP request
	      * @return CommentFormFactory or inheritance to an instance of the class that extends,
	*/
    public static CommentFormFactory getInstance(HttpServletRequest request) {
        WebApplicationContext springContext = WebApplicationContextUtils.getWebApplicationContext(request
                .getServletContext());
        return (CommentFormFactory) springContext.getBean(CommentFormFactory.FACTORY_BEAN_ID);
    }

    /**
    * comment page of the search results, and to create an instance of an empty CommentSearchForm that stores the search condition. <br/>
    * <br/>
    * @return Empty CommentSearchForm instance
    */
	public CommentSearchForm createCommentSearchForm() {
		return new CommentSearchForm(this.lengthUtils, this.codeLookupManager);
	}

	/**
	* comment page of the search results, and to create an instance of CommentSearchForm that stores the search condition. <br/>
	* The CommentSearchForm, set a value that corresponds to the request parameters. <br/>
	* <br/>
	* @param Request HTTP request
	* @return CommentSearchForm instance you set the request parameters
	*/
	public CommentSearchForm createCommentSearchForm(HttpServletRequest request) {
		CommentSearchForm form = createCommentSearchForm();
		FormPopulator.populateFormBeanFromRequest(request, form);
		return form;
	}
}