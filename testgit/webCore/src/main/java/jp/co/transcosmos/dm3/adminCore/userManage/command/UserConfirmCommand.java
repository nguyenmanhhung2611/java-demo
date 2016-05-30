package jp.co.transcosmos.dm3.adminCore.userManage.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.constant.CommonParameters;
import jp.co.transcosmos.dm3.core.model.AdminUserManage;
import jp.co.transcosmos.dm3.core.model.adminUser.AdminUserInterface;
import jp.co.transcosmos.dm3.core.model.adminUser.form.AdminUserForm;
import jp.co.transcosmos.dm3.core.model.adminUser.form.AdminUserFormFactory;
import jp.co.transcosmos.dm3.core.model.adminUser.form.AdminUserSearchForm;
import jp.co.transcosmos.dm3.core.model.exception.NotFoundException;
import jp.co.transcosmos.dm3.dao.JoinResult;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

import org.springframework.web.servlet.ModelAndView;

/**
 * 管理者ユーザ入力確認画面.
 * <p>
 * リクエストパラメータで渡された管理ユーザー情報のバリデーションを行い、確認画面を表示する。<br/>
 * もしバリデーションエラーが発生した場合は入力画面として表示する。<br/>
 * <br/>
 * 【復帰する View 名】<br/>
 * <ul>
 * <li>success<li>:正常終了
 * <li>input<li>:バリデーションエラーによる再入力
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
public class UserConfirmCommand implements Command {

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
	 * 管理ユーザ入力確認画面表示処理<br>
	 * <br>
	 * @param request HTTP リクエスト
	 * @param response HTTP レスポンス
	*/
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		// リクエストパラメータを格納した model オブジェクトを生成する。
		// このオブジェクトは View 層への値引渡しに使用される。
        Map<String, Object> model = createModel(request);
        AdminUserForm inputForm = (AdminUserForm) model.get("inputForm");
        AdminUserSearchForm searchForm = (AdminUserSearchForm) model.get("searchForm");


        // ロックステータスをデフォルトとして false （通常）を設定しておく
		model.put("lockStatus", false);
        
        
        // 更新モードの場合、更新対象の主キー値がパラメータで渡されていない場合、例外をスローする。
        if (this.mode.equals("update")){
        	if (StringValidateUtil.isEmpty(inputForm.getUserId())) throw new RuntimeException ("pk value is null.");


            // 管理ユーザー情報を取得
            JoinResult adminUser = this.userManager.searchAdminUserPk(searchForm);

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


        // note
        // 検索条件パラメータの意図的改ざんに対するバリデーションはこのタイミングでは行わない。
        // 登録完了後の検索画面でのバリデーションに委ねる。


        // view 名の初期値を設定
        String viewName = "success"; 
        
		// バリデーションを実行
        List<ValidationFailure> errors = new ArrayList<ValidationFailure>();
        if (!inputForm.validate(errors, this.mode)){
        	
        	// バリデーションエラー時は、エラー情報を model に設定し、入力画面を表示する。
        	model.put("errors", errors);
        	viewName = "input";
        } else if (!localValidation(inputForm, searchForm, errors)){

        	// Form のバリデーションが正常終了した場合は固有のバリデーションを実施する。
        	model.put("errors", errors);
        	viewName = "input";
        }
        
        return new ModelAndView(viewName, model);
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

		model.put("searchForm", factory.createUserSearchForm(request));
		model.put("inputForm", factory.createAdminUserForm(request));

		return model;

	}



	/**
	 * Form では実装しない、ＤＢを使用したバリデーションを行う<br/>
	 * <br/>
	 * @param errors
	 * 
	 * @return 正常時 true、エラー時 false
	 * @throws Exception 
	 */
	protected boolean localValidation(AdminUserForm inputForm, AdminUserSearchForm searchForm, List<ValidationFailure> errors)
			throws Exception{

		int startSize = errors.size();

		// 入力したログインID が利用可能かをチェックする。
		if (!this.userManager.isFreeLoginId(inputForm)){
			errors.add(new ValidationFailure("duplicate", "user.input.loginId", inputForm.getLoginId(), null));
		}

		return (startSize == errors.size());
	}
	
}
