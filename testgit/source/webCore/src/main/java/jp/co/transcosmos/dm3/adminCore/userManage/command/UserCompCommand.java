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
import jp.co.transcosmos.dm3.core.model.exception.DuplicateException;
import jp.co.transcosmos.dm3.core.model.exception.NotFoundException;
import jp.co.transcosmos.dm3.core.util.AdminLoginUserUtils;
import jp.co.transcosmos.dm3.mail.ReplacingMail;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;

/**
 * 管理者ユーザの追加、変更、削除、およびアカウントロックの解除処理.
 * <p>
 * 【新規登録の場合】<br/>
 * <ul>
 * <li>リクエストパラメータを受け取り、バリデーションを実行する。</li>
 * <li>バリデーションが正常終了した場合、管理ユーザー情報、管理ユーザー権限情報を新規登録する。</li>
 * <li>もし、新規登録時にログインID で一意制約エラーが発生した場合はバリデーションエラーとして扱う。</li>
 * <li>登録完了後、登録したメールアドレス宛にパスワードの通知メールを送信する。</li>
 * </ul>
 * <br/>
 * 【更新登録の場合】<br/>
 * <ul>
 * <li>リクエストパラメータを受け取り、バリデーションを実行する。</li>
 * <li>バリデーションが正常終了した場合、管理ユーザーを更新する。　また、管理ユーザー権限情報も一度削除
 * した後再登録する。</li>
 * <li>もし、更新時にログインID で一意制約エラーが発生した場合はバリデーションエラーとして扱う。</li>
 * <li>もし、更新対象データが存在しない場合、更新処理が継続できないので該当無し画面を表示する。</li>
 * <li>登録完了後、パスワードの変更があった場合、登録したメールアドレス宛にパスワードの通知メールを送信する。</li>
 * </ul>
 * <br/>
 * 【削除登録の場合】<br/>
 * <ul>
 * <li>リクエストパラメータを受け取り、バリデーションを実行する。（主キー値のみ）</li>
 * <li>バリデーションが正常終了した場合、管理ユーザー情報、管理ユーザー権限情報を削除する。</li>
 * </ul>
 * <br/>
 * 【アカウントロック解除の場合】<br/>
 * <ul>
 * <li>リクエストパラメータを受け取り、バリデーションを実行する。（主キー値のみ）</li>
 * <li>バリデーションが正常終了した場合、フレームワークの認証処理を使用し、管理ユーザーのアカウントロックを解除する。</li>
 * </ul>
 * <br/>
 * 【復帰する View 名】<br/>
 * <ul>
 * <li>success</li>:正常終了（リダイレクトページ）
 * <li>input</li>:バリデーションエラーによる再入力
 * <li>comp</li>:完了画面表示
 * </ul>
 * <p>
 * <pre>
 * 担当者		修正日		修正内容
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.02.03	Shamaison を参考に新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 * 
 */
public class UserCompCommand implements Command  {

	private static final Log log = LogFactory.getLog(UserCompCommand.class);
	
	/** 管理ユーザーメンテナンスを行う Model オブジェクト */
	protected AdminUserManage userManager;

	/** 処理モード (insert=新規登録処理、 update=更新処理、delete=削除処理、unlock=ロック解除)*/
	protected String mode;

	/** 主キーとなる UserId の必須チェックを行う場合、true を設定する。 （デフォルト true）　*/
	protected boolean useUserIdValidation = true;

	/** Form のバリデーションを実行する場合、true を設定する。　（デフォルト true） */
	protected boolean useValidation = true;

	/** パスワード通知メールテンプレート */
	protected ReplacingMail sendAdminPasswordTemplate;

	

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
	 * 主キーとなる UserId の必須チェックを実行する場合、true を設定する。　（デフォルト true）<br/>
	 * <br/>
	 * @param useUserIdValidation true の場合、UserId の必須チェックを実行
	 */
	public void setUseUserIdValidation(boolean useUserIdValidation) {
		this.useUserIdValidation = useUserIdValidation;
	}

	/**
	 * Form のバリデーションを実行する場合、true を設定する。　（デフォルト true）<br/>
	 * <br/>
	 * @param useValidation true の場合、Form のバリデーションを実行
	 */
	public void setUseValidation(boolean useValidation) {
		this.useValidation = useValidation;
	}

	/**
	 * パスワード通知メールテンプレートを設定する。<br/>
	 * 未設定の場合、メール送信を行わない。<br/>
	 * <br/>
	 * @param sendAdminPasswordTemplate パスワード通知メールテンプレート
	 */
	public void setSendAdminPasswordTemplate(ReplacingMail sendAdminPasswordTemplate) {
		this.sendAdminPasswordTemplate = sendAdminPasswordTemplate;
	}



	/**
	 * 管理ユーザの追加・変更・削除処理<br>
	 * DI コンテナから設定されたプロパティ値（mode、useUserIdValidation、useValidation）
	 * の設定に従い管理ユーザー情報を追加・変更・削除する。<br/>
	 * <br>
	 * @param request クライアントからのHttpリクエスト。
	 * @param response クライアントに返すHttpレスポンス。
	 */
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
				throws Exception {

		// リクエストパラメータを格納した model オブジェクトを生成する。
		// このオブジェクトは View 層への値引渡しに使用される。
		Map<String, Object> model = createModel(request);
		AdminUserForm inputForm = (AdminUserForm) model.get("inputForm");
        AdminUserSearchForm searchForm = (AdminUserSearchForm) model.get("searchForm");


        // ログインユーザーの情報を取得する。　（タイムスタンプの更新用）
        AdminUserInterface loginUser = AdminLoginUserUtils.getInstance(request).getLoginUserInfo(request, response);


        // 完了画面でリロードした場合、更新処理が意図せず実行される問題が発生する。
        // その問題を解消する為、view 名で "success"　を指定すると自動リダイレクト画面が表示される。
        // このリダイレクト画面は、command パラメータを "redirect"　に設定して完了画面へリクエストを
        // 送信する。
        // よって、command = "redirect" の場合は、ＤＢ更新は行わず、完了画面を表示する。
        String command = inputForm.getCommand();
        if (command != null && command.equals("redirect")){
        	return new ModelAndView("comp" , model);
        }


        // 主キー値のバラメータチェックが必須の場合、チェックを行う。
        // 新規登録時以外は処理対象の主キー値がパラメータで渡されていない場合、例外をスローする。
        if (this.useUserIdValidation){
        	if (StringValidateUtil.isEmpty(inputForm.getUserId())) throw new RuntimeException ("pk value is null.");
        }

        // note
        // 検索条件パラメータの意図的改ざんに対するバリデーションはこのタイミングでは行わない。
        // 登録完了後の検索画面でのバリデーションに委ねる。

		// バリデーションの実行モードが有効の場合、バリデーションを実行する。
        // 削除処理、ロック解除処理の場合はログインID 以外のパラメータは不要なので、spring 側から無効化している。
        if (this.useValidation){
        	List<ValidationFailure> errors = new ArrayList<ValidationFailure>();
        	if (!inputForm.validate(errors, this.mode)){

        		// バリデーションエラー時は、エラー情報を model に設定し、入力画面を表示する。
        		model.put("errors", errors);
        		return new ModelAndView("input" , model);
        	} else if (!localValidation(inputForm, searchForm, errors)) {

        		// Form のバリデーションが正常終了した場合は固有のバリデーションを実施する。
        		// 現時点では、localValidation() は true 固定なので、この処理に流れる事はない。
        		// 拡張用のロジック。
        		model.put("errors", errors);
        		return new ModelAndView("input" , model);
        	}

        	// ログインID の重複チェックは、整合性制約で行うので、通常のバリデーションとしては実施しない。
        }


        // 各種処理を実行
        try {
            execute(model, inputForm, loginUser);
        	
        } catch (DuplicateException e1) {
            // ログインID が重複している場合、再入力へ。
        	List<ValidationFailure> errors = new ArrayList<ValidationFailure>();
        	errors.add(new ValidationFailure("duplicate", "user.input.loginId", inputForm.getLoginId(), null));
    		model.put("errors", errors);

        	return new ModelAndView("input", model);

        } catch (NotFoundException e2) {

            // 該当するデータが存在しない場合
            // （例えば、管理ユーザーの検索後に別のセッションから取得対象ユーザーを削除した場合）、
            // 処理対象データ無しの例外をスローする。
           	throw new NotFoundException();
        }

        // パスワードの通知メール送信
        sendPwdMail(request, inputForm);

		return new ModelAndView("success" , model);
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
		model.put("inputForm", factory.createAdminUserForm(request));

		return model;

	}



	/**
	 * 処理の振り分けと実行を行う。<br/>
	 * <br/>
	 * @param model View 層へ引き渡す model オブジェクト
	 * @param inputForm 入力値が格納された Form オブジェクト
	 * @param loginUser タイムスタンプ更新時に使用するログインユーザー情報
	 * 
	 * @exception Exception 実装クラスによりスローされる任意の例外
	 * @exception DuplicateException 登録するログインID が重複した場合
	 * @exception NotFoundException 更新対象が存在しない場合
	 * @exception RuntimeException mode が指定されていない場合。　（DI コンテナの設定ミス）
	 */
	protected void execute(Map<String, Object> model, AdminUserForm inputForm, AdminUserInterface loginUser)
			throws Exception, DuplicateException, RuntimeException {

		if (this.mode.equals("insert")){
			insert(model, inputForm, loginUser);
        } else if (this.mode.equals("update")) {
			update(model, inputForm, loginUser);
        } else if (this.mode.equals("delete")) {
			delete(model, inputForm, loginUser);
        } else if (this.mode.equals("unlock")) {
			unlock(model, inputForm, loginUser);
        } else {
        	// 想定していない処理モードの場合、例外をスローする。
        	throw new RuntimeException ("execute mode bad setting.");
        }

	}



	/**
	 * 新規登録処理<br/>
	 * 引数で渡された内容で管理ユーザーの情報を追加する。<br/>
	 * <br/>
	 * @param model View 層へ引き渡す model オブジェクト
	 * @param inputForm 入力値が格納された Form オブジェクト
	 * @param loginUser タイムスタンプ更新時に使用するログインユーザー情報
	 * 
	 * @exception Exception 実装クラスによりスローされる任意の例外
	 * @exception DuplicateException 登録するログインID が重複した場合
	 */
	protected void insert(Map<String, Object> model, AdminUserForm inputForm, AdminUserInterface loginUser)
			throws Exception, DuplicateException {

		// 新規登録処理
    	this.userManager.addAdminUser(inputForm, (String)loginUser.getUserId());

	}



	/**
	 * 更新登録処理<br/>
	 * 引数で渡された内容で管理ユーザーの情報を更新する。<br/>
	 * <br/>
	 * @param model View 層へ引き渡す model オブジェクト
	 * @param inputForm 入力値が格納された Form オブジェクト
	 * @param loginUser タイムスタンプ更新時に使用するログインユーザー情報
	 * 
	 * @exception Exception 実装クラスによりスローされる任意の例外
	 * @exception DuplicateException 登録するログインID が重複した場合
	 * @exception NotFoundException 更新対象が存在しない場合
	 */
	protected void update(Map<String, Object> model, AdminUserForm inputForm, AdminUserInterface loginUser)
			throws Exception, DuplicateException, NotFoundException {

		// 更新処理
    	this.userManager.updateAdminUser(inputForm, (String)loginUser.getUserId());

	}



	/**
	 * 削除処理<br/>
	 * 引数で渡された内容で管理ユーザーの情報を削除する。<br/>
	 * <br/>
	 * @param model View 層へ引き渡す model オブジェクト
	 * @param inputForm 入力値が格納された Form オブジェクト
	 * @param loginUser タイムスタンプ更新時に使用するログインユーザー情報
	 * 
	 * @exception Exception 実装クラスによりスローされる任意の例外
	 */
	protected void delete(Map<String, Object> model, AdminUserForm inputForm, AdminUserInterface loginUser)
			throws Exception {

		// 削除処理
		this.userManager.delAdminUser(inputForm);

	}



	/**
	 * アカウントロックの解除処理<br/>
	 * <br/>
	 * @param model View 層へ引き渡す model オブジェクト
	 * @param inputForm 入力値が格納された Form オブジェクト
	 * @param loginUser タイムスタンプ更新時に使用するログインユーザー情報
	 * 
	 * @exception Exception 実装クラスによりスローされる任意の例外
	 * @exception NotFoundException 更新対象が存在しない場合
	 */
	protected void unlock(Map<String, Object> model, AdminUserForm inputForm, AdminUserInterface loginUser)
			throws Exception, NotFoundException {

		// ロックの解除処理
		this.userManager.changeLockStatus(inputForm, false, (String)loginUser.getUserId());
		
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

		// 拡張用。　現時点では Form 以外でのバリデーションは不要なので、 戻り値は true 固定

		return true;
	}

	
	
	/**
	 * パスワードの通知メールを送信する。<br/>
	 * <br/>
	 * @param request HTTP リクエスト
	 * @param inputForm 入力値
	 */
	protected void sendPwdMail(HttpServletRequest request, AdminUserForm inputForm){

		// メールテンプレートが設定されていない場合はメールの通知を行わない。
		if (this.sendAdminPasswordTemplate == null) {
			log.warn("sendAdminPasswordTemplate is null.");
			return;
		}

		// 新規登録時またはパスワード変更が発生した場合にメールを送信する。
		// 上記判定をパスワードの入力の有無で行う。　（入力値が実際に変更されたかまではチェックしない。）
		if (StringValidateUtil.isEmpty(inputForm.getPassword())){
			return;
		}

		// メールテンプレートで使用するパラメータを設定する。
		this.sendAdminPasswordTemplate.setParameter("inputForm", inputForm);
		this.sendAdminPasswordTemplate.setParameter("commonParameters", CommonParameters.getInstance(request));

		// メール送信
		this.sendAdminPasswordTemplate.send();
	}
}
