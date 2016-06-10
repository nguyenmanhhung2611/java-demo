package jp.co.transcosmos.dm3.adminCore.mypage.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.constant.CommonParameters;
import jp.co.transcosmos.dm3.core.model.MypageUserManage;
import jp.co.transcosmos.dm3.core.model.adminUser.AdminUserInterface;
import jp.co.transcosmos.dm3.core.model.exception.DuplicateException;
import jp.co.transcosmos.dm3.core.model.exception.NotFoundException;
import jp.co.transcosmos.dm3.core.model.mypage.form.MypageUserForm;
import jp.co.transcosmos.dm3.core.model.mypage.form.MypageUserFormFactory;
import jp.co.transcosmos.dm3.core.model.mypage.form.MypageUserSearchForm;
import jp.co.transcosmos.dm3.core.util.AdminLoginUserUtils;
import jp.co.transcosmos.dm3.login.LoginUser;
import jp.co.transcosmos.dm3.mail.ReplacingMail;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;

/**
 * マイページ会員の追加、変更、削除、およびアカウントロックの解除処理.
 * <p>
 * 【新規登録の場合】<br/>
 * <ul>
 * <li>リクエストパラメータを受け取り、バリデーションを実行する。</li>
 * <li>バリデーションが正常終了した場合、ユーザーＩＤ情報、マイページ会員情報を新規登録する。</li>
 * <li>もし、新規登録時にメールアドレスで一意制約エラーが発生した場合はバリデーションエラーとして扱う。</li>
 * <li>登録完了後、登録したメールアドレス宛にパスワードの通知メールを送信する。</li>
 * </ul>
 * <br/>
 * 【更新登録の場合】<br/>
 * <ul>
 * <li>リクエストパラメータを受け取り、バリデーションを実行する。</li>
 * <li>バリデーションが正常終了した場合、マイページ会員情報を更新する。</li>
 * <li>もし、更新時にメールアドレス（ログインID） で一意制約エラーが発生した場合はバリデーションエラーとして扱う。</li>
 * <li>もし、更新対象データが存在しない場合、更新処理が継続できないので該当無し画面を表示する。</li>
 * <li>登録完了後、パスワードの変更があった場合、登録したメールアドレス宛にパスワードの通知メールを送信する。</li>
 * </ul>
 * <br/>
 * 【削除登録の場合】<br/>
 * <ul>
 * <li>リクエストパラメータを受け取り、バリデーションを実行する。（主キー値のみ）</li>
 * <li>バリデーションが正常終了した場合、マイページ会員情報を削除する。</li>
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
 * H.Mizuno		2015.03.05	新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 * 
 */
public class MypageCompCommand implements Command  {

	private static final Log log = LogFactory.getLog(MypageCompCommand.class);
	
	/** マイページ会員メンテナンスを行う Model オブジェクト */
	protected MypageUserManage userManager;

	/** 処理モード (insert=新規登録処理、 update=更新処理、delete=削除処理)*/
	protected String mode;

	/** 主キーとなる UserId の必須チェックを行う場合、true を設定する。 （デフォルト true）　*/
	protected boolean useUserIdValidation = true;

	/** Form のバリデーションを実行する場合、true を設定する。　（デフォルト true） */
	protected boolean useValidation = true;

	/** パスワード通知メールテンプレート */
	protected ReplacingMail sendMypagePasswordTemplate;



	/**
	 * マイページ会員メンテナンスを行う Model　オブジェクトを設定する。<br/>
	 * <br/>
	 * @param userManager マイページ会員メンテナンスの model オブジェクト
	 */
	public void setUserManager(MypageUserManage userManager) {
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
	 * @param sendMypagePasswordTemplate パスワード通知メールテンプレート
	 */
	public void setSendMypagePasswordTemplate(ReplacingMail sendMypagePasswordTemplate) {
		this.sendMypagePasswordTemplate = sendMypagePasswordTemplate;
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
		MypageUserForm inputForm = (MypageUserForm) model.get("inputForm");
		MypageUserSearchForm searchForm = (MypageUserSearchForm) model.get("searchForm");


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
        	if (StringValidateUtil.isEmpty(searchForm.getUserId())) throw new RuntimeException ("pk value is null.");
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
        	}

        	// メールアドレス（ログインID） の重複チェックは、整合性制約で行うので、通常のバリデーションとしては実施しない。
        }


        // 各種処理を実行
        try {
            execute(model, inputForm, searchForm, loginUser);
        	
        } catch (DuplicateException e1) {
            // メールアドレス（ログインID） が重複している場合、再入力へ。
        	List<ValidationFailure> errors = new ArrayList<ValidationFailure>();
        	errors.add(new ValidationFailure("duplicate", "mypage.input.email", inputForm.getEmail(), null));
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
		MypageUserFormFactory factory = MypageUserFormFactory.getInstance(request);

		model.put("searchForm", factory.createMypageUserSearchForm(request));
		model.put("inputForm", factory.createMypageUserForm(request));

		return model;

	}



	/**
	 * 処理の振り分けと実行を行う。<br/>
	 * <br/>
	 * @param model View 層へ引き渡す model オブジェクト
	 * @param inputForm 入力値が格納された Form オブジェクト
	 * @param searchForm 検索条件、および更新・削除対象 userId が格納された Form オブジェクト
	 * @param editUser タイムスタンプ更新時に使用するログインユーザー情報
	 * 
	 * @exception Exception 実装クラスによりスローされる任意の例外
	 * @exception DuplicateException 登録するログインID が重複した場合
	 * @exception NotFoundException 更新対象が存在しない場合
	 * @exception RuntimeException mode が指定されていない場合。　（DI コンテナの設定ミス）
	 */
	protected void execute(Map<String, Object> model, MypageUserForm inputForm,
						   MypageUserSearchForm searchForm, AdminUserInterface editUser)
			throws Exception, DuplicateException, RuntimeException {

		if (this.mode.equals("insert")){
			insert(model, inputForm, editUser);
        } else if (this.mode.equals("update")) {
			update(model, inputForm, (String)searchForm.getUserId(), editUser);
        } else if (this.mode.equals("delete")) {
			delete(model, (String)searchForm.getUserId(), editUser);
        } else {
        	// 想定していない処理モードの場合、例外をスローする。
        	throw new RuntimeException ("execute mode bad setting.");
        }

	}



	/**
	 * 新規登録処理<br/>
	 * 引数で渡された内容でユーザーID情報、マイページ会員情報を追加する。<br/>
	 * <br/>
	 * @param model View 層へ引き渡す model オブジェクト
	 * @param inputForm 入力値が格納された Form オブジェクト
	 * @param editUser タイムスタンプ更新時に使用するログインユーザー情報
	 * 
	 * @exception Exception 実装クラスによりスローされる任意の例外
	 * @exception DuplicateException 登録するログインID が重複した場合
	 */
	protected void insert(Map<String, Object> model, MypageUserForm inputForm, AdminUserInterface editUser)
			throws Exception, DuplicateException {

		// ユーザーID 情報の登録
		LoginUser loginUser = this.userManager.addLoginID((String)editUser.getUserId());

    	// 取得したユーザーID でマイページ会員情報の登録
    	this.userManager.addMyPageUser(inputForm, (String)loginUser.getUserId(), (String)editUser.getUserId());

	}



	/**
	 * 更新登録処理<br/>
	 * 引数で渡された内容で管理ユーザーの情報を更新する。<br/>
	 * <br/>
	 * @param model View 層へ引き渡す model オブジェクト
	 * @param inputForm 入力値が格納された Form オブジェクト
	 * @param updUserId 更新対象となるマイページ会員の userId
	 * @param loginUser タイムスタンプ更新時に使用するログインユーザー情報
	 * 
	 * @exception Exception 実装クラスによりスローされる任意の例外
	 * @exception DuplicateException 登録するログインID が重複した場合
	 * @exception NotFoundException 更新対象が存在しない場合
	 */
	protected void update(Map<String, Object> model, MypageUserForm inputForm, String updUserId, AdminUserInterface loginUser)
			throws Exception, DuplicateException, NotFoundException {

		// 更新処理
    	this.userManager.updateMyPageUser(inputForm, updUserId, (String)loginUser.getUserId());

	}



	/**
	 * 削除処理<br/>
	 * 引数で渡された内容でマイページ会員情報を削除する。<br/>
	 * <br/>
	 * @param model View 層へ引き渡す model オブジェクト
	 * @param delUserId 削除対象となるマイページ会員の userId
	 * @param loginUser タイムスタンプ更新時に使用するログインユーザー情報
	 * 
	 * @exception Exception 実装クラスによりスローされる任意の例外
	 */
	protected void delete(Map<String, Object> model, String delUserId, AdminUserInterface loginUser)
			throws Exception {

		// 削除処理
		this.userManager.delMyPageUser(delUserId);

	}

	
	
	/**
	 * パスワードの通知メールを送信する。<br/>
	 * <br/>
	 * @param request HTTP リクエスト
	 * @param inputForm 入力値
	 */
	protected void sendPwdMail(HttpServletRequest request, MypageUserForm inputForm){

		// メールテンプレートが設定されていない場合はメールの通知を行わない。
		if (this.sendMypagePasswordTemplate == null) {
			log.warn("sendMypagePasswordTemplate is null.");
			return;
		}

		// 新規登録時またはパスワード変更が発生した場合にメールを送信する。
		// 上記判定をパスワードの入力の有無で行う。　（入力値が実際に変更されたかまではチェックしない。）
		if (StringValidateUtil.isEmpty(inputForm.getPassword())){
			return;
		}

		// メールテンプレートで使用するパラメータを設定する。
		this.sendMypagePasswordTemplate.setParameter("inputForm", inputForm);
		this.sendMypagePasswordTemplate.setParameter("commonParameters", CommonParameters.getInstance(request));

		// メール送信
		this.sendMypagePasswordTemplate.send();
	}

}
