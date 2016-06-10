package jp.co.trancosmos.dm3.webFront.social.command;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.model.NewManage;
import jp.co.transcosmos.dm3.core.model.social.form.CommentForm;
import jp.co.transcosmos.dm3.core.model.social.form.CommentFormFactory;
import jp.co.transcosmos.dm3.core.model.social.form.CommentSearchForm;
import jp.co.transcosmos.dm3.core.model.social.form.NewsForm;
import jp.co.transcosmos.dm3.core.model.social.form.NewsFormFactory;
import jp.co.transcosmos.dm3.core.model.social.form.NewsSearchForm;
import jp.co.transcosmos.dm3.core.vo.MemberInfo;
import jp.co.transcosmos.dm3.core.vo.News;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;

/**
 * controller handle request form client
 * 
 * @author nhatlv
 *
 */
public class ViewNewsDetailCommand implements Command{
	
	/** service to handle business logic */
	private NewManage newsManage;
	
	/** number row of per page */
	private int rowsPerPage = 10;
	
	/** number page show on screen*/
	private int visibleNavigationPageCount = 5;

	/**
	 * function handle request from client
	 * 
	 * @param request
	 * @param response
	 */
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// call function to init data
		Map<String, Object> model = createModel(request);
		NewsSearchForm newsSearchForm = (NewsSearchForm) model.get("newsSearchForm");
		CommentSearchForm searchForm = (CommentSearchForm) model.get("searchForm");
		
		// if news id is null
		if (StringValidateUtil.isEmpty(newsSearchForm.getKeyNewsId())){
			throw new RuntimeException ("News Id value is null.");
		}
		
		// set key news id to get list comment
		searchForm.setKeyNewsId(newsSearchForm.getKeyNewsId());
		
		// get new follow new id
		News news = newsManage.getNew(newsSearchForm);
		
		// if news is not null
		if(news != null) {
			model.put("news", news);
			
			// put username add news
			model.put("userAddNew", newsManage.getInformationUser(news.getUpdUserId()));
			
			// put list comment of news
			model.put("listComment", newsManage.getListComment(searchForm));
			
			// put information user login form session
			model.put("userLogin", (MemberInfo) request.getSession().getAttribute("loggedInUser"));
		}
		return new ModelAndView("success", model);
	}

	/**
	 * Create model to save data
	 * Set data to form from request
	 * 
	 * @param request
	 * @return model
	 */
	private Map<String, Object> createModel(HttpServletRequest request) {
		// init new form
		NewsFormFactory factory = NewsFormFactory.getInstance(request);
		NewsForm inputForm = factory.createNewsForm(request);
		NewsSearchForm newsSearchForm = factory.createNewsSearchForm(request);
		
		// init comment form 
		CommentFormFactory factComment = CommentFormFactory.getInstance(request);
		CommentForm inpComForm = factComment.createCommentForm(request);
		CommentSearchForm searchForm = factComment.createCommentSearchForm(request);
		
		// set row of per page
		searchForm.setRowsPerPage(rowsPerPage);
		
		// set number page show on screen
		searchForm.setVisibleNavigationPageCount(visibleNavigationPageCount);
		Map<String, Object> model = new HashMap<String, Object>();
		// put new form
		model.put("inputForm", inputForm);
		model.put("newsSearchForm", newsSearchForm);
		
		// put comment form
		model.put("inpComForm", inpComForm);
		model.put("searchForm", searchForm);
		return model;
	}

	/**
	 * @return the newManage
	 */
	public NewManage getNewsManage() {
		return newsManage;
	}

	/**
	 * @param newManage the newManage to set
	 */
	public void setNewsManage(NewManage newsManage) {
		this.newsManage = newsManage;
	}

	/**
	 * @return the rowsPerPage
	 */
	public int getRowsPerPage() {
		return rowsPerPage;
	}

	/**
	 * @param rowsPerPage the rowsPerPage to set
	 */
	public void setRowsPerPage(int rowsPerPage) {
		this.rowsPerPage = rowsPerPage;
	}

	/**
	 * @return the visibleNavigationPageCount
	 */
	public int getVisibleNavigationPageCount() {
		return visibleNavigationPageCount;
	}

	/**
	 * @param visibleNavigationPageCount the visibleNavigationPageCount to set
	 */
	public void setVisibleNavigationPageCount(int visibleNavigationPageCount) {
		this.visibleNavigationPageCount = visibleNavigationPageCount;
	}
}
