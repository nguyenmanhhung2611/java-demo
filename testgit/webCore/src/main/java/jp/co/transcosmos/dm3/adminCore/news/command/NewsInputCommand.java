package jp.co.transcosmos.dm3.adminCore.news.command;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.constant.CommonParameters;
import jp.co.transcosmos.dm3.core.model.InformationManage;
import jp.co.transcosmos.dm3.core.model.NewsManage;
import jp.co.transcosmos.dm3.core.model.NewsManageImpl;
import jp.co.transcosmos.dm3.core.model.information.InformationManageImpl;
import jp.co.transcosmos.dm3.core.model.information.form.InformationForm;
import jp.co.transcosmos.dm3.core.model.information.form.InformationFormFactory;
import jp.co.transcosmos.dm3.core.model.information.form.InformationSearchForm;
import jp.co.transcosmos.dm3.core.model.mypage.MypageUserInterface;
import jp.co.transcosmos.dm3.core.model.news.form.NewsForm;
import jp.co.transcosmos.dm3.core.model.news.form.NewsFormFactory;
import jp.co.transcosmos.dm3.core.model.news.form.NewsSearchForm;
import jp.co.transcosmos.dm3.core.vo.Information;
import jp.co.transcosmos.dm3.core.vo.InformationTarget;
import jp.co.transcosmos.dm3.core.vo.News;
import jp.co.transcosmos.dm3.core.vo.NewsTarget;
import jp.co.transcosmos.dm3.dao.JoinResult;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;

import org.springframework.web.servlet.ModelAndView;

/**
 * <pre>
 * お知らせ情報入力画面
 * 
 * 【新規登録の場合】
 *     ・リクエストパラメータ（検索画面で入力した検索条件、表示ページ位置等）の受取りのみを行う。
 *     
 * 【更新登録の場合】
 *     ・リクエストパラメータ（検索画面で入力した検索条件、表示ページ位置等）の受取を行う。
 *     ・リクエストパラメータ（informationNo）に該当するお知らせ情報、マイページ会員情報を取得し、画面
 *      表示する。
 *      
 * 【リクエストパラメータ（command） が "back"の場合】
 *     ・リクエストパラメータで渡された入力値を入力画面に表示する。　（入力確認画面から復帰したケース。）
 *
 * 【復帰する View 名】
 *    ・"success" : 正常終了
 *    ・"notFound" : 該当データが存在しない場合
 *     
 * 担当者       修正日      修正内容
 * ------------ ----------- -----------------------------------------------------
 * I.Shu		2015.02.6	新規作成
 *
 * 注意事項
 *
 * </pre>
 */
public class NewsInputCommand implements Command {

	/** お知らせメンテナンスを行う Model オブジェクト */
	protected NewsManage newsManager;;
	
	/** 処理モード (insert = 新規登録処理、 update=更新処理)*/
	protected String mode;
	
	
	/**
	 * お知らせメンテナンスを行う Model　オブジェクトを設定する。<br/>
	 * <br/>
	 * @param newsManager お知らせメンテナンスの model オブジェクト
	 */
	public void setNewsManager(NewsManage newsManager) {
		this.newsManager = newsManager;
	}

	/**
	 * 処理モードを設定する<br/>
	 * <br/>
	 * @param mode "insert" = 新規登録処理、"update" = 更新処理
	 */
	public void setMode(String mode) {
		this.mode = mode;
	}

	/**
	 * お知らせ情報入力画面表示処理<br>
	 * <br>
	 * @param request HTTP リクエスト
	 * @param response HTTP レスポンス
	*/
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// リクエストパラメータを格納した model オブジェクトを生成する。
		// このオブジェクトは View 層への値引渡しに使用される。
        Map<String, Object> model = createModel(request);
        NewsForm inputForm = (NewsForm) model.get("inputForm"); 
        NewsSearchForm searchForm = (NewsSearchForm) model.get("searchForm"); 

		// 更新処理の場合、処理対象となる主キー値がパラメータで渡されていない場合、例外をスローする。
		if ("update".equals(this.mode) && StringValidateUtil.isEmpty(searchForm.getNewsId())){
			throw new RuntimeException ("pk value is null.");
		}

        // 新規登録モードで初期画面表示の場合、データの取得は行わない。
        if ("insert".equals(this.mode)) {
        	return new ModelAndView("success", model);
        }
        
       // お知らせ情報を取得
        News news = this.newsManager.searchTopNewsPk(searchForm.getNewsId());
        
        // 該当するデータが存在しない場合、該当無し画面を表示する。
        if (news == null) {
        	return new ModelAndView("notFound", model);
        }
        
		// command パラメータが "back" の場合、入力確認画面からの復帰なので、ＤＢから初期値を取得しない。
		// （リクエストパラメータから取得した値を使用する。）
		String command = inputForm.getCommand();
		if (command != null && "back".equals(command)) {
			
			return new ModelAndView("success", model);
		}
		
		inputForm.setDefaultData(news);
		
		return new ModelAndView("success", model);
	}


	/**
	 * model オブジェクトを作成し、リクエストパラメータを格納した form オブジェクトを格納する。<br/>
	 * <br/>
	 * @param request HTTP リクエストパラメータ
	 * @return パラメータを設定した Form を格納した model オブジェクトを復帰する。
	 */
	protected Map<String, Object> createModel(HttpServletRequest request) {

        Map<String, Object> model = new HashMap<String, Object>();

		// リクエストパラメータを取得して Form オブジェクトを作成する。
		NewsFormFactory factory = NewsFormFactory.getInstance(request);

		// 検索条件、および、画面コントロールパラメータを取得する。
		NewsSearchForm searchForm = factory.createInformationSearchForm(request);
		model.put("searchForm", searchForm);

		
		// 入力フォームの受け取りは、command パラメータの値によって異なる。
		// command パラメータが渡されない場合、入力画面の初期表示とみなし、空のフォームを復帰する。
		// command パラメータで "back" が渡された場合、入力確認画面からの復帰を意味する。
		// その場合はリクエストパラメータを受け取り、入力画面へ初期表示する。
		// もし、"back" 以外の値が渡された場合、command パラメータが渡されない場合と同等に扱う。

		NewsForm inputForm = factory.createInformationForm(request);
		String command = inputForm.getCommand();
		
		if ("back".equals(command)){
			// 戻るボタンの場合は、リクエストパラメータの値を設定した Form を復帰する。
			model.put("inputForm", inputForm);
		} 
		model.put("inputForm", inputForm);
		return model;

	}

}
