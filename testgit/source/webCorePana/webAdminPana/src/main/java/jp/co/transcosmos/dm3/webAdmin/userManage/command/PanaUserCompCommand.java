package jp.co.transcosmos.dm3.webAdmin.userManage.command;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.adminCore.userManage.command.UserCompCommand;
import jp.co.transcosmos.dm3.corePana.model.adminUser.form.PanaAdminUserSearchForm;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

import org.springframework.web.servlet.ModelAndView;

/**
 * 管理者ユーザの削除、再検索処理。
 * <p>
 * リクエストパラメータを受け取り、バリデーションを実行する。（主キー値のみ）<br/>
 * バリデーションが正常終了した場合、管理ユーザー情報、管理ユーザー権限情報を削除する。<br/>
 * 入力された検索条件を元に管理者ユーザーを再検索し、一覧表示する。<br/>
 * <br/>
 * 【復帰する View 名】<br/>
 * <ul>
 * <li>success</li>:正常終了
 * <li>validFail<li>:バリデーションエラー
 * </ul>
 * <p>
 * <pre>
 * 担当者		修正日		修正内容
 * ------------ ----------- -----------------------------------------------------
 * チョ夢		2015/04/21	新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 *
 */
public class PanaUserCompCommand extends UserCompCommand  {

	/** １ページの表示件数 */
	private int rowsPerPage = 50;



	/**
	 * 1ページあたり表示数を設定する。<br>
	 * @param rowsPerPage 1ページあたり表示数
	 */
	public void setRowsPerPage(int rowsPerPage) {
		this.rowsPerPage = rowsPerPage;
	}



	/**
	 * 管理ユーザの削除、再検索処理<br>
	 * DI コンテナから設定されたプロパティ値（mode、useUserIdValidation、useValidation）
	 * の設定に従い管理ユーザー情報を削除、再検索する。<br/>
	 * <br>
	 * @param request クライアントからのHttpリクエスト。
	 * @param response クライアントに返すHttpレスポンス。
	 */
	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
				throws Exception {

		ModelAndView modelAndView = super.handleRequest(request, response);

		// これから再検索を実行する。
		Map<String, Object> model = modelAndView.getModel();
		PanaAdminUserSearchForm searchForm = (PanaAdminUserSearchForm) model.get("searchForm");

		// １ページに表示する行数を設定する。
		searchForm.setRowsPerPage(this.rowsPerPage);

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
		model.put("hitcont", this.userManager.searchAdminUser(searchForm));


		// バリデーションを通過し、検索処理が行われた場合、Form の command パラメータに list を設定
		// する。　このパラメータ値は、詳細画面や変更画面で searchCommand パラメータとして引き継がれ、
		// 再び検索画面へ復帰する際、command パラメータとして渡される。
		// （この設定により、検索済であれば、詳細画面から戻った時に検索済で画面が表示される。）
		model.put("command", "list");

		return modelAndView;
	}
}
