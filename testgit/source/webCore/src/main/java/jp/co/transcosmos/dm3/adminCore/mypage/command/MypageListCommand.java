package jp.co.transcosmos.dm3.adminCore.mypage.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.model.MypageUserManage;
import jp.co.transcosmos.dm3.core.model.mypage.form.MypageUserFormFactory;
import jp.co.transcosmos.dm3.core.model.mypage.form.MypageUserSearchForm;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

/**
 * マイページ会員検索画面.
 * <p>
 * 入力された検索条件を元にマイページ会員を検索し、一覧表示する。<br/>
 * 検索条件の入力に問題がある場合、検索処理は行わない。<br/>
 * <br/>
 * 【復帰する View 名】<br/>
 * <ul>
 * <li>success<li>:検索処理正常終了
 * <li>validFail<li>:バリデーションエラー
 * </ul>
 * <p>
 * <pre>
 * 担当者		修正日		修正内容
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.03.04	新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 * 
 */
public class MypageListCommand implements Command {

	/** マイページ会員メンテナンスを行う Model オブジェクト */
	protected MypageUserManage userManager;

	/** １ページの表示件数 */
	protected int rowsPerPage = 20;

	
	
	/**
	 * マイページ会員メンテナンスを行う Model　オブジェクトを設定する。<br/>
	 * <br/>
	 * @param userManager マイページ会員メンテナンスの model オブジェクト
	 */
	public void setUserManager(MypageUserManage userManager) {
		this.userManager = userManager;
	}

	/**
	 * 1ページあたり表示数を設定する。<br>
	 * @param rowsPerPage 1ページあたり表示数
	 */
	public void setRowsPerPage(int rowsPerPage) {
		this.rowsPerPage = rowsPerPage;
	}



	/**
	 * マイページ会員検索リクエスト処理<br>
	 * マイページ会員検索のリクエストがあったときに呼び出される。
	 * <br>
	 * @param request クライアントからのHttpリクエスト。
	 * @param response クライアントに返すHttpレスポンス。
	 */
	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		// リクエストパラメータを格納した model オブジェクトを生成する。
		// このオブジェクトは View 層への値引渡しに使用される。
        Map<String, Object> model = createModel(request);
        MypageUserSearchForm searchForm = (MypageUserSearchForm) model.get("searchForm");


        // バリデーションを実行
        List<ValidationFailure> errors = new ArrayList<ValidationFailure>();

        if (!searchForm.validate(errors)) {
        	// バリデーションエラーあり
			model.put("errors", errors);
			return new ModelAndView("validFail", model);
        }


        // 検索実行
        // searchAdminUser() は、パラメータで渡された form の内容で管理ユーザーを検索し、
        // 検索した結果を form に格納する。
        // このメソッドの戻り値は該当件数なので、その値を view 層へ渡すパラメータとして設定している。
		model.put("hitcont", this.userManager.searchMyPageUser(searchForm));


		// バリデーションを通過し、検索処理が行われた場合、Form の command パラメータに list を設定
		// する。　このパラメータ値は、詳細画面や変更画面で searchCommand パラメータとして引き継がれ、
		// 再び検索画面へ復帰する際、command パラメータとして渡される。
		// （この設定により、検索済であれば、詳細画面から戻った時に検索済で画面が表示される。）
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
		MypageUserFormFactory factory = MypageUserFormFactory.getInstance(request);
		MypageUserSearchForm searchForm = factory.createMypageUserSearchForm(request); 

		// １ページに表示する行数を設定する。
		searchForm.setRowsPerPage(this.rowsPerPage);

		model.put("searchForm", searchForm);
		
		return model;
	}

}
