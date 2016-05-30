package jp.co.transcosmos.dm3.adminCore.userManage.command;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.constant.CommonParameters;
import jp.co.transcosmos.dm3.core.model.AdminUserManage;
import jp.co.transcosmos.dm3.core.model.adminUser.AdminUserInterface;
import jp.co.transcosmos.dm3.core.model.adminUser.AdminUserRoleInterface;
import jp.co.transcosmos.dm3.core.model.adminUser.form.AdminUserForm;
import jp.co.transcosmos.dm3.core.model.adminUser.form.AdminUserFormFactory;
import jp.co.transcosmos.dm3.core.model.adminUser.form.AdminUserSearchForm;
import jp.co.transcosmos.dm3.core.model.exception.NotFoundException;
import jp.co.transcosmos.dm3.dao.JoinResult;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;

import org.springframework.web.servlet.ModelAndView;

/**
 * 管理ユーザー情報入力画面.
 * <p>
 * 【新規登録の場合】<br/>
 * <ul>
 * <li>リクエストパラメータで送信された値を受取る。（検索条件や、一覧の表示ページ位置のみ）</li>
 * <li>受け取った値を入力画面に表示する。</li>
 * </ul>
 * <br/>
 * 【更新登録の場合】<br/>
 * <ul>
 * <li>リクエストパラメータで送信された値を受取る。（検索条件や、一覧の表示ページ位置、および主キー値のみ）</li>
 * <li>リクエストパラメータ（userId）に該当する管理ユーザー情報、管理ユーザーロール情報を取得する。</li>
 * <li>取得した値を入力画面に表示する。</li>
 * </ul>
 * <br/>
 * 【リクエストパラメータ（command） が "back"の場合】（確認画面からの復帰の場合）<br/>
 * <ul>
 * <li>リクエストパラメータで送信された値を受取る。</li>
 * <li>受け取った値を入力画面に表示する。</li>
 * </ul>
 * <br/>
 * 【復帰する View 名】<br/>
 * <ul>
 * <li>success<li>:検索処理正常終了
 * </ul>
 * <p>
 * <pre>
 * 担当者		修正日		修正内容
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.01.2	Shamaison を参考に新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 * 
 */
public class UserInputCommand implements Command {

	/** 管理ユーザーメンテナンスを行う Model オブジェクト */
	protected AdminUserManage userManager;

	/** 処理モード (insert = 新規登録処理、 update=更新処理)*/
	protected String mode;

	

	/**
	 * 管理ユーザーメンテナンスを行う Model　オブジェクトを設定する。<br/>
	 * <br/>
	 * @param userManager 管理ユーザーメンテナンスの model オブジェクト
	 */
	public void setUserManager(AdminUserManage userManager) {
		this.userManager = userManager;
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
	 * 管理ユーザ入力画面表示処理<br>
	 * <br>
	 * @param request HTTP リクエスト
	 * @param response HTTP レスポンス
	*/
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// リクエストパラメータを格納した model オブジェクトを生成する。
		// このオブジェクトは View 層への値引渡しに使用される。
        Map<String, Object> model = createModel(request);
		AdminUserForm inputForm = (AdminUserForm) model.get("inputForm");
        AdminUserSearchForm searchForm = (AdminUserSearchForm) model.get("searchForm");

        
		// 更新処理の場合、処理対象となる主キー値がパラメータで渡されていない場合、例外をスローする。
		if (this.mode.equals("update") && StringValidateUtil.isEmpty(searchForm.getUserId())){
			throw new RuntimeException ("pk value is null.");
		}

		
		// 新規登録以外の場合、管理ユーザー情報を取得する。
		// 更新処理時に確認画面から戻るボタンで戻った場合でもロックステータスを取得する必要があるので、
		// このタイミングで取得する。
		JoinResult adminUser = null;
		
		if (!this.mode.equals("insert")){
			adminUser = this.userManager.searchAdminUserPk(searchForm);

			// 該当するデータが存在しない場合
			// （例えば、管理ユーザーの検索後に別のセッションから取得対象ユーザーを削除した場合）、
			// 処理対象データ無しの例外をスローする。
			if (adminUser == null) {
				throw new NotFoundException();
			}

			// 取得したユーザー情報のロック状況を model に設定する。
			String alias = CommonParameters.getInstance(request).getAdminUserDbAlias();
			AdminUserInterface userInfo = (AdminUserInterface) adminUser.getItems().get(alias);
			model.put("lockStatus", this.userManager.isLocked(userInfo));
		}


		// command パラメータが "back" の場合、入力確認画面からの復帰なので、ＤＢから初期値を取得しない。
		// （リクエストパラメータから取得した値を使用する。）
		String command = inputForm.getCommand();
		if (command != null && command.equals("back")) return new ModelAndView("success", model);


        // 新規登録モードで初期画面表示の場合、データの取得は行わない。
        if (this.mode.equals("insert")) return new ModelAndView("success", model);


        // note
        // 検索条件パラメータの意図的改ざんに対するバリデーションはこのタイミングでは行わない。
        // 登録完了後の検索画面でのバリデーションに委ねる。


        // 取得した値を入力用 Form へ格納する。
		// 設定した値は、入力画面の初期値として使用される。
        // 現時点ではユーザー情報とロールID は１対１の関係にある。
        // 将来の拡張を考慮し、フォームのインターフェース的には配列になっている。
        CommonParameters commonParameters = CommonParameters.getInstance(request);

		inputForm.setDefaultData(
				(AdminUserInterface) adminUser.getItems().get(commonParameters.getAdminUserDbAlias()),
				(AdminUserRoleInterface) adminUser.getItems().get(commonParameters.getAdminRoleDbAlias()));

		return new ModelAndView("success", model);

	}
	

	
	/**
	 * model オブジェクトを作成し、リクエストパラメータを格納した form オブジェクトを格納する。<br/>
	 * <br/>
	 * @param request HTTP リクエストパラメータ
	 * @return パラメータを設定した Form を格納した model オブジェクトを復帰する。
	 */
	protected Map<String, Object> createModel(HttpServletRequest request) {

		Map<String, Object> model = new HashMap<>();
		
		// リクエストパラメータを取得して Form オブジェクトを作成する。
		AdminUserFormFactory factory = AdminUserFormFactory.getInstance(request);

		// 検索条件、および、画面コントロールパラメータを取得する。
		AdminUserSearchForm searchForm = factory.createUserSearchForm(request);
		model.put("searchForm", searchForm);

		
		// 入力フォームの受け取りは、command パラメータの値によって異なる。
		// command パラメータが渡されない場合、入力画面の初期表示とみなし、空のフォームを復帰する。
		// command パラメータで "back" が渡された場合、入力確認画面からの復帰を意味する。
		// その場合はリクエストパラメータを受け取り、入力画面へ初期表示する。
		// もし、"back" 以外の値が渡された場合、command パラメータが渡されない場合と同等に扱う。

		AdminUserForm inputForm = factory.createAdminUserForm(request);
		String command = inputForm.getCommand();
		
		if (command != null && command.equals("back")){
			model.put("inputForm", inputForm);
		} else {
			model.put("inputForm", factory.createAdminUserForm());
		}

		return model;

	}
	
}
