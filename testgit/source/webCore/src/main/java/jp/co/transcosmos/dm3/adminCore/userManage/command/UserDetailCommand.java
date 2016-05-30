package jp.co.transcosmos.dm3.adminCore.userManage.command;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.constant.CommonParameters;
import jp.co.transcosmos.dm3.core.model.AdminUserManage;
import jp.co.transcosmos.dm3.core.model.adminUser.AdminUserInterface;
import jp.co.transcosmos.dm3.core.model.adminUser.form.AdminUserFormFactory;
import jp.co.transcosmos.dm3.core.model.adminUser.form.AdminUserSearchForm;
import jp.co.transcosmos.dm3.core.model.exception.NotFoundException;
import jp.co.transcosmos.dm3.dao.JoinResult;

import org.springframework.web.servlet.ModelAndView;

/**
 * 管理者ユーザ詳細画面　（削除処理の確認画面兼用）.
 * <p>
 * リクエストパラメータ（userId）で渡された値に該当する管理ユーザー情報、管理ユーザーロール情報を
 * 取得し画面表示する。<br/>
 * <br/>
 * 【復帰する View 名】<br/>
 * <ul>
 * <li>success<li>:検索処理正常終了
 * </ul>
 * <p>
 * <pre>
 * 担当者		修正日		修正内容
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.01.29	Shamaison を参考に新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 * 
 */
public class UserDetailCommand implements Command {

	/** 管理ユーザーメンテナンスを行う Model オブジェクト */
	protected AdminUserManage userManager;

	/**
	 * 管理ユーザーメンテナンスを行う Model　オブジェクトを設定する。<br/>
	 * <br/>
	 * @param userManager 管理ユーザーメンテナンスの model オブジェクト
	 */
	public void setUserManager(AdminUserManage userManager) {
		this.userManager = userManager;
	}

	
	
	/**
	 * 管理ユーザ詳細表示処理<br>
	 * <br>
	 * @param request HTTP リクエスト
	 * @param response HTTP レスポンス
	 * @return ModelAndView　のインスタンス
	*/
	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		// リクエストパラメータを格納した model オブジェクトを生成する。
		// このオブジェクトは View 層への値引渡しに使用される。
        Map<String, Object> model = createModel(request);
        AdminUserSearchForm searchForm = (AdminUserSearchForm) model.get("searchForm");
        

        // note
        // 検索条件パラメータの意図的改ざんに対するバリデーションはこのタイミングでは行わない。
        // 登録完了後の検索画面でのバリデーションに委ねる。


        // 指定されたユーザーID に該当する管理ユーザーの情報を取得する。
        JoinResult adminUser = this.userManager.searchAdminUserPk(searchForm);

        // 該当するデータが存在しない場合
        // （例えば、管理ユーザーの検索後に別のセッションから取得対象ユーザーを削除した場合）、
        // 処理対象データ無しの例外をスローする。
        if (adminUser == null) {
        	throw new NotFoundException();
        }

        // 取得できた場合は model に設定する。
		model.put("adminUser", adminUser);


		// 取得したユーザー情報のロック状況を model に設定する。
		String alias = CommonParameters.getInstance(request).getAdminUserDbAlias();
		AdminUserInterface userInfo = (AdminUserInterface) adminUser.getItems().get(alias);
		model.put("lockStatus", this.userManager.isLocked(userInfo));


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
		AdminUserFormFactory factory = AdminUserFormFactory.getInstance(request);
		model.put("searchForm", factory.createUserSearchForm(request));

		return model;
	}

}
