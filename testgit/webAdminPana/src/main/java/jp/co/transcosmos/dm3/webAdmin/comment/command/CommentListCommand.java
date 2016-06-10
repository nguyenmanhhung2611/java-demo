package jp.co.transcosmos.dm3.webAdmin.comment.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.vo.News;
import jp.co.transcosmos.dm3.corePana.model.comment.form.CommentFormFactory;
import jp.co.transcosmos.dm3.corePana.model.comment.form.CommentSearchForm;
import jp.co.transcosmos.dm3.corePana.model.comment.manage.impl.CommentsManageImpl;
import jp.co.transcosmos.dm3.validation.ValidationFailure;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 * @author thoph
 *
 */
public class CommentListCommand implements Command{
	/**(search - delete) */
	private String mode;
    protected CommentsManageImpl commentManager;
    /** row page  */
    private int rowsPerPage;

    /** number pageｰ */
    private int visibleNavigationPageCount;

	public void setMode(String mode) {
		this.mode = mode;
	}

	public CommentsManageImpl getCommentManager() {
		return commentManager;
	}

	/**
	 * @param commentManager
	 */
	public void setCommentManager(CommentsManageImpl commentManager) {
		this.commentManager = commentManager;
	}

    public void setRowsPerPage(int rowsPerPage) {
        this.rowsPerPage = rowsPerPage;
    }

    public void setVisibleNavigationPageCount(int visibleNavigationPageCount) {
        this.visibleNavigationPageCount = visibleNavigationPageCount;
    }

   /**
    * 
    */
    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        Map<String, Object> model = createModel(request);
        CommentSearchForm searchForm = (CommentSearchForm) model.get("searchForm");

 		String newsId = request.getParameter("newsId");
 		searchForm.setKeyNewId(request.getParameter("newsId"));
  		News news = this.commentManager.getNewsId(newsId);
  		model.put("news", news);

        // validate errror
        List<ValidationFailure> errors = new ArrayList<ValidationFailure>();
        if (!searchForm.validate(errors)) {
            model.put("errors", errors);
            return new ModelAndView("validFail", model);
        }
        
        //delete comment
        if ("delete".equals(this.mode)) {
        	String commentId = searchForm.getKeyCommentId();
        	this.commentManager.delComment(commentId);
        	model.put("commentId", commentId);
        	return new ModelAndView("success", model);
        }
        
        model.put("hitcont", this.commentManager.searchComment(searchForm));
        return new ModelAndView("success", model);
    }

	/**
	 * 
	 * @param request
	 * @return
	 */
    protected Map<String, Object> createModel(HttpServletRequest request) {

        Map<String, Object> model = new HashMap<String, Object>();

        // To create a Form object to get the request parameters.
        CommentFormFactory factory = CommentFormFactory.getInstance(request);
        CommentSearchForm searchForm = factory.createCommentSearchForm(request);
        // Sets number of rows to be displayed on one page.
        searchForm.setRowsPerPage(this.rowsPerPage);
        // To set the number of pages.
        searchForm.setVisibleNavigationPageCount(this.visibleNavigationPageCount);
        /**
         *  Passing through the validation, if the search process has been performed, set the list to searchCommand parameters of Form
         *  To. The value of this parameter, taken over as searchCommand parameters in the detail screen or change the screen
         *   When you return to again search screen, it is passed as a parameter searchCommand.
         *   (This setting, if the search already, the screen in the search already in when you return from the editing screen is displayed.)
         */
        searchForm.setSearchCommand("list");
        model.put("searchForm", searchForm);
        return model;
    }
}
