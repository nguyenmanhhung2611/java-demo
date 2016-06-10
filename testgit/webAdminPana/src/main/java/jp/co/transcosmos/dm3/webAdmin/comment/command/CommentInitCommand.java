/**
 * 
 */
package jp.co.transcosmos.dm3.webAdmin.comment.command;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.corePana.model.comment.form.CommentFormFactory;
import jp.co.transcosmos.dm3.corePana.model.comment.form.CommentSearchForm;
import jp.co.transcosmos.dm3.corePana.model.comment.manage.impl.CommentsManageImpl;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 * @author thoph
 *
 */
public class CommentInitCommand implements Command{
	/**
	* Set the Model object preparative common information. <br/>
	* <br/>
	* @param CommentManager common information Model object preparative
	*/
	  protected CommentsManageImpl commentManager;

	
	public CommentsManageImpl getCommentManager() {
		return commentManager;
	}

	/**
	 * @param commentManager
	 */
	public void setCommentManager(CommentsManageImpl commentManager) {
		this.commentManager = commentManager;
	}

	/**
	* Comment information request processing <br>
	* It will be called when there is a request of the comment information. <br>
	*
	* @param Request
	* Http request from the client.
	* @param Response
	* Http response to return to the client.
	*/
	
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		Map<String, Object> model = new HashMap<String, Object>();
 		//get id news 
 		model.put("news", this.commentManager.getNewsId(request.getParameter("newsId")));
        // To create a Form object to get the request parameters.
        CommentFormFactory factory = CommentFormFactory.getInstance(request);
        CommentSearchForm searchForm = factory.createCommentSearchForm(request);
        model.put("searchForm", searchForm);
		return new ModelAndView("success", model);
	}
}
