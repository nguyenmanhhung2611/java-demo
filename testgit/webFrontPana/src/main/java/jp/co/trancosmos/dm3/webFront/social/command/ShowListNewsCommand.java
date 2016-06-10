package jp.co.trancosmos.dm3.webFront.social.command;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.model.NewManage;
import jp.co.transcosmos.dm3.core.model.social.form.NewsForm;
import jp.co.transcosmos.dm3.core.model.social.form.NewsFormFactory;
import jp.co.transcosmos.dm3.core.model.social.form.NewsSearchForm;

/**
 * controller handle request from client
 * 
 * @author nhatlv
 *
 */
public class ShowListNewsCommand implements Command{
	
	/** service to handle business logic */
	private NewManage newsManage;
	
	/** number row of per page */
	private int rowsPerPage = 10;
	
	/** number page show in screen */
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
		NewsSearchForm searchForm = (NewsSearchForm) model.get("searchForm");
		
		// put list news and list comment of per news
		model.put("listNews", newsManage.getListNew(searchForm));
		
		// get top 10 views - function optional
		// TODO
		return new ModelAndView("success", model);
	}

	/**
	 * create model to get data from request
	 * set data to form
	 * 
	 * @param request 
	 * @return model
	 */
	private Map<String, Object> createModel(HttpServletRequest request) {
		// init form
		NewsFormFactory factory = NewsFormFactory.getInstance(request);
		NewsForm inputForm = factory.createNewsForm(request);
		NewsSearchForm searchForm = factory.createNewsSearchForm(request);
		
		// set row of per page 
		searchForm.setRowsPerPage(rowsPerPage);
		
		// set number page show on screen
		searchForm.setVisibleNavigationPageCount(visibleNavigationPageCount);
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("inputForm", inputForm);
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
