package jp.co.transcosmos.dm3.webAdmin.news.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.model.NewsManage;
import jp.co.transcosmos.dm3.core.model.news.form.NewsForm;
import jp.co.transcosmos.dm3.core.model.news.form.NewsFormFactory;
import jp.co.transcosmos.dm3.core.model.news.form.NewsSearchForm;
import jp.co.transcosmos.dm3.utils.CommonLogging;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

import org.springframework.web.servlet.ModelAndView;

/**
 * 
 * ------------ ----------- -----------------------------------------------------
 * @author hiennt
 *
 * </pre>
 */
public class NewsListCommand implements Command {

	private String mode;

	/** お知らせメンテナンスを行う Model オブジェクト */
	
	private NewsManage newsManager;
	
	public void setNewsManager(NewsManage newsManager) {
		this.newsManager = newsManager;
	}

	/** １ページの表示件数 */
	private int rowsPerPage;

    /** 認証用ロギングクラス */

	/** ページの表示数 */
    private int visibleNavigationPageCount;

	/**
	 * 処理モードを設定する<br/>
	 * <br/>
	 * @param mode
	 *            search = 検索処理、 csv = CSV出力処理、 delete = 削除処理
	 */
	public void setMode(String mode) {
		this.mode = mode;
	}

	/**
	 * 1ページあたり表示数を設定する。<br>
	 *
	 * @param rowsPerPage
	 *            1ページあたり表示数
	 */
	public void setRowsPerPage(int rowsPerPage) {
		this.rowsPerPage = rowsPerPage;
	}

    /**
     * ページの表示数を設定する。<br>
     * @param visibleNavigationPageCount ページ数
     */
    public void setVisibleNavigationPageCount(int visibleNavigationPageCount) {
        this.visibleNavigationPageCount = visibleNavigationPageCount;
    }

	/**
	 * お知らせ情報リクエスト処理<br>
	 * お知らせ情報のリクエストがあったときに呼び出される。 <br>
	 *
	 * @param request
	 *            クライアントからのHttpリクエスト。
	 * @param response
	 *            クライアントに返すHttpレスポンス。
	 */
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
        Map<String, Object> model = createModel(request);
        
        NewsSearchForm form = (NewsSearchForm) model.get("searchForm");
        NewsForm inputForm = (NewsForm) model.get("inputForm");
        
        List<ValidationFailure> errors = new ArrayList<ValidationFailure>();
        
        if (!form.validate(errors)) {
			model.put("errors", errors);
			return new ModelAndView("validFail", model);
        }

        if ("delete".equals(this.mode)) { 
        	this.newsManager.delNews(inputForm);
        }
        
        model.put("hitcont", this.newsManager.searchAdminNews(form));
		model.put("command", "list");

		return new ModelAndView("success", model);
	}

	/**
	 * リクエストパラメータから Form オブジェクトを作成する。<br/>
	 * <br/>
	 * @param request HTTP リクエストパラメータ
	 * @return パラメータが設定されたフォームオブジェクト
	 */
	protected Map<String, Object> createModel(HttpServletRequest request) {

		Map<String, Object> model = new HashMap<String, Object>();

		// リクエストパラメータを取得して Form オブジェクトを作成する。
		NewsFormFactory factory = NewsFormFactory.getInstance(request);
		NewsSearchForm searchForm = factory.createInformationSearchForm(request);
		NewsForm inputForm = factory.createInformationForm(request);

		//set row in paging
		searchForm.setRowsPerPage(this.rowsPerPage);
		
        searchForm.setVisibleNavigationPageCount(this.visibleNavigationPageCount);
        model.put("searchForm", searchForm);
        model.put("inputForm", inputForm);

		return model;

	}
}
