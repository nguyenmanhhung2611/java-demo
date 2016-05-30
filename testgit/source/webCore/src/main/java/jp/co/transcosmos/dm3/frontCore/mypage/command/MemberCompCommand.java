package jp.co.transcosmos.dm3.frontCore.mypage.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.adminCore.mypage.command.MypageCompCommand;
import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.constant.CommonParameters;
import jp.co.transcosmos.dm3.core.model.MypageUserManage;
import jp.co.transcosmos.dm3.core.model.exception.DuplicateException;
import jp.co.transcosmos.dm3.core.model.exception.NotFoundException;
import jp.co.transcosmos.dm3.core.model.mypage.form.MypageUserForm;
import jp.co.transcosmos.dm3.core.model.mypage.form.MypageUserFormFactory;
import jp.co.transcosmos.dm3.core.util.FrontLoginUserUtils;
import jp.co.transcosmos.dm3.core.vo.MemberInfo;
import jp.co.transcosmos.dm3.dao.JoinResult;
import jp.co.transcosmos.dm3.login.LoginUser;
import jp.co.transcosmos.dm3.mail.ReplacingMail;
import jp.co.transcosmos.dm3.utils.EncodingUtils;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;

/**
 * マイページ会員の追加、変更、およびアカウントロックの解除処理.
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
 * 【復帰する View 名】<br/>
 * <ul>
 * <li>success</li>:正常終了（リダイレクトページ）
 * <li>input</li>:バリデーションエラーによる再入力
 * </ul>
 * <p>
 * <pre>
 * 担当者		修正日		修正内容
 * ------------ ----------- -----------------------------------------------------
 * Y.Cho		2015.04.16	新規作成
 * H.Mizuno		2015.07.07	更新処理時の主キー値取得判定に問題があったのでチェック方法を変更。
 * 							セッションのユーザー情報を更新する処理を追加
 * </pre>
 * <p>
 * 注意事項<br/>
 *
 */
public class MemberCompCommand implements Command {

	private static final Log log = LogFactory.getLog(MypageCompCommand.class);

	/** マイページ会員メンテナンスを行う Model オブジェクト */
	protected MypageUserManage userManager;

	/** 処理モード (insert=新規登録処理、 update=更新処理)*/
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
	 * 管理ユーザの追加・変更処理<br>
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


		// ログインユーザーの情報を取得する。
		// 連続して会員登録を行う場合、マイページにログイン済なので、getAnonLoginUserInfo() は、
		// 匿名ユーザーではなく、現在ログインしているマイページ会員の情報を復帰するので、loginUser　
		// の扱いには注意する事。
		LoginUser loginUser = null;
		if (mode != null && mode.equals("insert")) {
			loginUser = FrontLoginUserUtils.getInstance(request).getAnonLoginUserInfo(
					request, response);
		} else {
			loginUser = FrontLoginUserUtils.getInstance(request).getMypageLoginUserInfo(
					request, response);
		}


        // 主キー値のバラメータチェックが必須の場合、ログインユーザー情報から取得する。
        // 新規登録時以外はログインユーザー情報が取得出来ていない場合はエラーとする。
        if (this.useUserIdValidation) {
        	if (loginUser == null) throw new RuntimeException ("not loggined.");
        }


		// バリデーションの実行モードが有効の場合、バリデーションを実行する。
        // 削除処理、ロック解除処理の場合はログインID 以外のパラメータは不要なので、spring 側から無効化している。
        if (this.useValidation) {
        	List<ValidationFailure> errors = new ArrayList<ValidationFailure>();
        	if (!inputForm.validate(errors, this.mode)) {

        		// バリデーションエラー時は、エラー情報を model に設定し、入力画面を表示する。
        		model.put("errors", errors);
        		return new ModelAndView("input" , model);
        	}

        	// メールアドレス（ログインID） の重複チェックは、整合性制約で行うので、通常のバリデーションとしては実施しない。
        }


        // 各種処理を実行
        try {
            execute(model, inputForm, loginUser);

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

		model.put("inputForm", factory.createMypageUserForm(request));

		return model;

	}



	/**
	 * 処理の振り分けと実行を行う。<br/>
	 * <br/>
	 * @param model View 層へ引き渡す model オブジェクト
	 * @param inputForm 入力値が格納された Form オブジェクト
	 * @param editUser ログインユーザー情報
	 *
	 * @exception Exception 実装クラスによりスローされる任意の例外
	 * @exception DuplicateException 登録するログインID が重複した場合
	 * @exception NotFoundException 更新対象が存在しない場合
	 * @exception RuntimeException mode が指定されていない場合。　（DI コンテナの設定ミス）
	 */
	protected void execute(Map<String, Object> model, MypageUserForm inputForm, LoginUser editUser)
			throws Exception, DuplicateException, RuntimeException {

		if (this.mode.equals("insert")) {
			insert(model, inputForm, editUser);
        } else if (this.mode.equals("update")) {
			update(model, inputForm, (MemberInfo)editUser);
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
	 * @param editUser 新規登録に使用する匿名ユーザー（もしくは会員情報）
	 *
	 * @exception Exception 実装クラスによりスローされる任意の例外
	 * @exception DuplicateException 登録するログインID が重複した場合
	 */
	protected void insert(Map<String, Object> model, MypageUserForm inputForm, LoginUser editUser)
			throws Exception, DuplicateException {

		String addUserId = (String) editUser.getUserId(); // 登録用ユーザーID

		// note
		// 連続して会員情報を登録した場合、もしくは、マイページにログイン済で会員登録した場合、ログインしている
		// マイページ会員情報の userId が渡されてくる。
		// その為、そのユーザーＩＤにマイページ会員情報が登録されているかをチェックし、もし登録されている場合は
		//　新たなユーザーＩＤを採番してから会員登録を行う。

    	// ログインユーザーIDで会員情報を取得する。
		JoinResult userInfo = this.userManager.searchMyPageUserPk(addUserId);

        // 該当する会員情報が存在する場合は、新たなユーザーＩＤを発行し、そのユーザーＩＤに
		// 関連付けしてマイページ会員情報を登録する。
		if (userInfo != null) {
			LoginUser loginUser = this.userManager.addLoginID(null);
			addUserId = (String)loginUser.getUserId();
		}

    	// マイページ会員情報の登録
    	this.userManager.addMyPageUser(inputForm, addUserId, addUserId);

	}



	/**
	 * 更新登録処理<br/>
	 * 引数で渡された内容で管理ユーザーの情報を更新する。<br/>
	 * <br/>
	 * @param model View 層へ引き渡す model オブジェクト
	 * @param inputForm 入力値が格納された Form オブジェクト
	 * @param editUser 更新対象となるマイページ会員情報
	 *
	 * @exception Exception 実装クラスによりスローされる任意の例外
	 * @exception DuplicateException 登録するログインID が重複した場合
	 * @exception NotFoundException 更新対象が存在しない場合
	 */
	protected void update(Map<String, Object> model, MypageUserForm inputForm, MemberInfo editUser)
			throws Exception, DuplicateException, NotFoundException {

		String userId = (String) editUser.getUserId();
		
		// 更新処理
    	this.userManager.updateMyPageUser(inputForm, userId, userId);

    	// セッションに格納されてる、ログインユーザー情報を変更する。
    	updateSessionUserId(inputForm, editUser);
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

	
	
	/**
	 * セッションに格納されているログイン情報を更新する。<br>
	 * <br>
	 * @param inputForm 入力された会員情報
	 * @param sessionUser セッションから取得した会員情報
	 */
	protected void updateSessionUserId(MypageUserForm inputForm, MemberInfo sessionUser){

		sessionUser.setEmail(inputForm.getEmail());							// メールアドレス
		sessionUser.setMemberLname(inputForm.getMemberLname());				// 会員名（姓）
		sessionUser.setMemberFname(inputForm.getMemberFname());				// 会員名（名）
		sessionUser.setMemberLnameKana(inputForm.getMemberLnameKana());		// 会員名・カナ（姓） 
		sessionUser.setMemberFnameKana(inputForm.getMemberFnameKana());		// 会員名・カナ（名）

		// パスワードは、入力があった場合のみ変更
		if (!StringValidateUtil.isEmpty(inputForm.getPassword())){
			// パスワードを暗号化して格納する。
			sessionUser.setPassword(EncodingUtils.md5Encode(inputForm.getPassword()));
		}
		
		// 最終更新日は変更していないが、害が無いレベルなので、無理に更新は行わない。
	}
}
