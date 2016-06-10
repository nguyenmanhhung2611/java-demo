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
import jp.co.transcosmos.dm3.core.model.social.form.NewsFormFactory;
import jp.co.transcosmos.dm3.core.model.social.form.NewsSearchForm;
import jp.co.transcosmos.dm3.core.vo.MemberInfo;
import jp.co.transcosmos.dm3.core.vo.News;

/**
 * controller handle request from client
 * 
 * @author nhatlv
 *
 */
public class AddCommentInputCommand implements Command{
	
	/** service handle business logic */
	private NewManage newsManage;
	
	/** mode screen */ 
	private String mode;
	
	/** number row of per page */
	private int rowsPerPage = 10;
	
	/** number page show on screen */
	private int visibleNavigationPageCount = 5;

	/**
	 * handle request from client
	 * 
	 * @param request
	 * @param response
	 */
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// call function to init data
		Map<String, Object> model = createModel(request);
		CommentForm inputForm = (CommentForm) model.get("inputForm");
		CommentSearchForm searchForm = (CommentSearchForm) model.get("searchForm");
		NewsSearchForm newsSearhForm = (NewsSearchForm) model.get("newsSearchForm");
		
		// get new follow new id
		newsSearhForm.setKeyNewsId(inputForm.getNewsId());
		News news = newsManage.getNew(newsSearhForm);
		
		// if news is not null
		if(news != null) {
			model.put("news", news);
			
			// put username add news
			model.put("userAddNews", newsManage.getInformationUser(news.getUpdUserId()));
			
			// put information user login from session
			model.put("userLogin", (MemberInfo) request.getSession().getAttribute("loggedInUser"));
			
			// put list comment
			searchForm.setKeyNewsId(inputForm.getNewsId());
			model.put("listComment", newsManage.getListComment(searchForm));
		}
		return new ModelAndView("success", model);
	}

	/**
	 * create model to get data from request
	 * 
	 * @param request
	 * @return model
	 */
	private Map<String, Object> createModel(HttpServletRequest request) {
		// init form
		CommentFormFactory factory = CommentFormFactory.getInstance(request);
		CommentForm inputForm = factory.createCommentForm(request);
		CommentSearchForm searchForm = factory.createCommentSearchForm(request);
		
		// init new form
		NewsFormFactory newsFactory = NewsFormFactory.getInstance(request);
		NewsSearchForm newsSearchForm = newsFactory.createNewsSearchForm(request);
		
		// set row of per page
		searchForm.setRowsPerPage(rowsPerPage);
		
		// set number page show on screen
		searchForm.setVisibleNavigationPageCount(visibleNavigationPageCount);
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("inputForm", inputForm);
		model.put("searchForm", searchForm);
		model.put("newsSearchForm", newsSearchForm);
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
	 * @return the mode
	 */
	public String getMode() {
		return mode;
	}

	/**
	 * @param mode the mode to set
	 */
	public void setMode(String mode) {
		this.mode = mode;
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
