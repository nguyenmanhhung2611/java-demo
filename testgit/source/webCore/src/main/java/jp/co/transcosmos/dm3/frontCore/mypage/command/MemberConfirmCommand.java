package jp.co.transcosmos.dm3.frontCore.mypage.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.model.MypageUserManage;
import jp.co.transcosmos.dm3.core.model.mypage.MypageUserInterface;
import jp.co.transcosmos.dm3.core.model.mypage.form.MypageUserForm;
import jp.co.transcosmos.dm3.core.model.mypage.form.MypageUserFormFactory;
import jp.co.transcosmos.dm3.core.util.FrontLoginUserUtils;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

import org.springframework.web.servlet.ModelAndView;

/**
 * マイページ会員入力確認画面.
 * <p>
 * リクエストパラメータで渡されたマイページ会員情報のバリデーションを行い、確認画面を表示する。<br>
 * もしバリデーションエラーが発生した場合は入力画面として表示する。<br>
 * <br>
 * 【復帰する View 名】<br>
 * <ul>
 * <li>success<li>:正常終了
 * <li>input<li>:バリデーションエラーによる再入力
 * </ul>
 * <p>
 * <pre>
 * 担当者		修正日		修正内容
 * ------------ ----------- -----------------------------------------------------
 * Y.Cho		2015.04.16	新規作成
 * H.Mizuno		2015.07.07	マイページログイン中に新たな会員を新規登録すると、自身のメールアドレスが
 * 							バリデーションを通過する問題を修正
 * </pre>
 * <p>
 * 注意事項<br>
 * 
 */
public class MemberConfirmCommand implements Command {

	/** マイページ会員メンテナンスを行う Model オブジェクト */
	protected MypageUserManage userManager;

	/** 処理モード (insert = 新規登録処理、 update=更新処理)*/
	protected String mode;


	
	/**
	 * マイページ会員メンテナンスを行う Model　オブジェクトを設定する。<br>
	 * <br>
	 * @param userManager マイページ会員メンテナンスの model オブジェクト
	 */
	public void setUserManager(MypageUserManage userManager) {
		this.userManager = userManager;
	}

	/**
	 * 処理モードを設定する<br>
	 * <br>
	 * @param mode "insert" = 新規登録処理、"update" = 更新処理
	 */
	public void setMode(String mode) {
		this.mode = mode;
	}

	
	
	/**
	 * マイページ会員情報入力確認画面表示処理<br>
	 * <br>
	 * @param request HTTP リクエスト
	 * @param response HTTP レスポンス
	*/
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		// リクエストパラメータを格納した model オブジェクトを生成する。
		// このオブジェクトは View 層への値引渡しに使用される。
        Map<String, Object> model = createModel(request);
        MypageUserForm inputForm = (MypageUserForm) model.get("inputForm");

        
		// note
		// このタイミングでマイページ認証情報を取得していた為、連続して会員登録を行うと、先に登録したメールアドレス
		// がバリデーションを通過していた。
		// 対策として、更新処理時のみマイページユーザーを取得する様に処理を変更した。

        // ログインユーザー情報
		MypageUserInterface loginUser = null;

        // 更新モードの場合、ログインユーザー情報を取得する。　もし取得できない場合は例外をスローする。
		// 認証フィルターの対象画面なので、ログインユーザー情報が取得出来ないのはセッションタイムアウト以外は
		// 発生しない。
        if (this.mode.equals("update")) {
    		loginUser = FrontLoginUserUtils.getInstance(request).getMypageLoginUserInfo(request, response);
    		if (loginUser == null) throw new RuntimeException ("not loggined.");
        }


        // view 名の初期値を設定
        String viewName = "success"; 
        
		// バリデーションを実行
        List<ValidationFailure> errors = new ArrayList<ValidationFailure>();
        if (!inputForm.validate(errors, this.mode)){
        	
        	// バリデーションエラー時は、エラー情報を model に設定し、入力画面を表示する。
        	model.put("errors", errors);
        	viewName = "input";
        } else if (!localValidation(inputForm, loginUser, errors)){

        	// Form のバリデーションが正常終了した場合は固有のバリデーションを実施する。
        	model.put("errors", errors);
        	viewName = "input";
        }
        
        return new ModelAndView(viewName, model);
	}



	/**
	 * model オブジェクトを作成し、リクエストパラメータを格納した form オブジェクトを格納する。<br>
	 * <br>
	 * @param request HTTP リクエストパラメータ
	 * @return パラメータを設定した Form を格納した model オブジェクトを復帰する。
	 */
	protected Map<String, Object> createModel(HttpServletRequest request) {

		Map<String, Object> model = new HashMap<>();

		// リクエストパラメータを取得して Form オブジェクトを作成する。
		MypageUserFormFactory factory = MypageUserFormFactory.getInstance(request);

		model.put("inputForm", factory.createMypageUserForm(request));

		return model;

	}

	
	
	/**
	 * Form では実装しない、ＤＢを使用したバリデーションを行う<br>
	 * <br>
	 * @param inputForm マイページ会員メンテナンスの入力パラメータ受取り用フォーム.
	 * @param loginUser ログインユーザー情報
	 * @param errors エラー情報
	 * @return 正常時 true、エラー時 false
	 * @throws Exception 委譲先クラスがスローする例外 
	 */
	protected boolean localValidation(MypageUserForm inputForm, MypageUserInterface loginUser, List<ValidationFailure> errors)
			throws Exception {

		int startSize = errors.size();

		// ユーザーＩＤ
		// ログインユーザー情報は、自身のメールアドレスが重複してエラーとなる事を避ける為、更新処理時のみ設定されている。
		// isFreeLoginId() メソッドは、ユーザーＩＤが指定されている場合は、そのＩＤに該当するメールアドレスを除外して
		// 重複チェックを行う。
		String userId = null;
		if (loginUser != null) {
			userId = (String) loginUser.getUserId();
		}

		// 入力したメールアドレスが利用可能かをチェックする。
		if (!this.userManager.isFreeLoginId(inputForm, userId)){
			errors.add(new ValidationFailure("duplicate", "mypage.input.email", inputForm.getEmail(), null));
		}

		return (startSize == errors.size());
	}


}
