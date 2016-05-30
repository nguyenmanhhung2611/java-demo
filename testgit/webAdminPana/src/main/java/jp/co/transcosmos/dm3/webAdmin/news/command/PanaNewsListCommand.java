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
 * <pre>
 * お知らせの検索・一覧
 * 入力された検索条件を元にお知らせ情報を検索し、一覧表示する。
 * 検索条件の入力に問題がある場合、検索処理は行わない。
 *
 * 【復帰する View 名】
 *    ・"success" : 検索処理正常終了
 *    ・"validFail" : バリデーションエラー
 *
 * 担当者        修正日       修正内容
 * ------------ ----------- -----------------------------------------------------
 * zh.xiaoting  2015.04.24  新規作成
 * Duong.Nguyen 2015.08.26  Change simple log by admin log for exporting csv file
 *
 * 注意事項
 *
 * </pre>
 */
public class PanaNewsListCommand implements Command {

	private String mode;

	/** お知らせメンテナンスを行う Model オブジェクト */
	
	private NewsManage newsManager;
	
	private CommonLogging authLogging;

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
	 * 認証用ロギングクラス Model オブジェクトを設定する。<br/>
	 * <br/>
	 * @param authLogging 認証用ロギングクラス Model オブジェクト
	 */
    public void setAuthLogging(CommonLogging authLogging) {
        this.authLogging = authLogging;
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
