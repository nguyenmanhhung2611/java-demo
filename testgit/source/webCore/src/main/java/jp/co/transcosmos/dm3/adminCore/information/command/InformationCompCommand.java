package jp.co.transcosmos.dm3.adminCore.information.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.adminCore.mypage.command.MypageCompCommand;
import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.model.InformationManage;
import jp.co.transcosmos.dm3.core.model.adminUser.AdminUserInterface;
import jp.co.transcosmos.dm3.core.model.exception.NotFoundException;
import jp.co.transcosmos.dm3.core.model.information.form.InformationForm;
import jp.co.transcosmos.dm3.core.model.information.form.InformationFormFactory;
import jp.co.transcosmos.dm3.core.util.AdminLoginUserUtils;
import jp.co.transcosmos.dm3.mail.ReplacingMail;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;

/**
 * お知らせ情報の追加、変更、削除処理.
 * <p>
 * 【新規登録の場合】<br/>
 * <ul>
 * <li>リクエストパラメータを受け取り、バリデーションを実行する。</li>
 * <li>バリデーションが正常終了した場合、お知らせ情報を新規登録する。</li>
 * <li>また、公開先が特定個人の場合、お知らせ公開先情報も新規登録する。</li>
 * </ul>
 * <br/>
 * 【更新登録の場合】<br/>
 * <ul>
 * <li>リクエストパラメータを受け取り、バリデーションを実行する。</li>
 * <li>バリデーションが正常終了した場合、お知らせ情報を更新する。</li>
 * <li>お知らせ公開先情報は一度削除し、変更後の公開先が特定個人であれば、お知らせ公開先情報も新規登録する。</li>
 * <li>もし、更新対象データが存在しない場合、更新処理が継続できないので該当無し画面を表示する。</li>
 * </ul>
 * <br/>
 * 【削除登録の場合】<br/>
 * <ul>
 * <li>リクエストパラメータを受け取り、バリデーションを実行する。（主キー値のみ）</li>
 * <li>バリデーションが正常終了した場合、お知らせ情報、お知らせ公開先情報を削除する。</li>
 * </ul>
 * <br/>
 * 【復帰する View 名】<br/>
 * <ul>
 * <li>success</li>:正常終了（リダイレクトページ）
 * <li>input</li>:バリデーションエラーによる再入力
 * <li>notFound</li>:該当データが存在しない場合（更新処理の場合）
 * <li>comp</li>:完了画面表示
 * </ul>
 * <p>
 * <pre>
 * 担当者		修正日		修正内容
 * ------------ ----------- -----------------------------------------------------
 * I.Shu		2015.02.10	新規作成
 * H.Mizuno		2015.02.27	インターフェースの改定にともない全体構成を変更
 * </pre>
 * <p>
 * 注意事項<br/>
 *
 */
public class InformationCompCommand implements Command  {

	private static final Log log = LogFactory.getLog(MypageCompCommand.class);

	/** お知らせメンテナンスを行う Model オブジェクト */
	protected InformationManage informationManager;

	/** 処理モード (insert = 新規登録処理、 update=更新処理、delete=削除処理)*/
	protected String mode;

	/** 主キーとなる UserId の必須チェックを行う場合、true を設定する。 （デフォルト true）　*/
	protected boolean useUserIdValidation = true;

	/** Form のバリデーションを実行する場合、true を設定する。　（デフォルト true） */
	protected boolean useValidation = true;

	/** お知らせ通知メールテンプレート */
	protected ReplacingMail sendInformationTemplate;

	/**
	 * お知らせ通知メールテンプレートを設定する。<br/>
	 * 未設定の場合、メール送信を行わない。<br/>
	 * <br/>
	 * @param sendInformationTemplate お知らせ通知メールテンプレート
	 */
	public void setSendInformationTemplate(ReplacingMail sendInformationTemplate) {
		this.sendInformationTemplate = sendInformationTemplate;
	}

	/**
	 * お知らせメンテナンスを行う Model　オブジェクトを設定する。<br/>
	 * <br/>
	 * @param informationManager お知らせメンテナンスの model オブジェクト
	 */
	public void setInformationManager(InformationManage informationManager) {
		this.informationManager = informationManager;
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
	 * お知らせ情報の追加、変更、削除処理<br>
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
        InformationForm inputForm = (InformationForm) model.get("inputForm");


        // ログインユーザーの情報を取得する。　（タイムスタンプの更新用）
        AdminUserInterface loginUser = AdminLoginUserUtils.getInstance(request).getLoginUserInfo(request, response);


        // 完了画面でリロードした場合、更新処理が意図せず実行される問題が発生する。
        // その問題を解消する為、view 名で "success"　を指定すると自動リダイレクト画面が表示される。
        // このリダイレクト画面は、command パラメータを "redirect"　に設定して完了画面へリクエストを
        // 送信する。
        // よって、command = "redirect" の場合は、ＤＢ更新は行わず、完了画面を表示する。
        String command = inputForm.getCommand();
        if (command != null && "redirect".equals(command)){
        	return new ModelAndView("comp" , model);
        }


        // 主キー値のバラメータチェックが必須の場合、チェックを行う。
        // 新規登録時以外は処理対象の主キー値がパラメータで渡されていない場合、例外をスローする。
        if (this.useUserIdValidation){
        	if (StringValidateUtil.isEmpty(inputForm.getInformationNo())) throw new RuntimeException ("pk value is null.");
        }


        // note
        // 検索条件パラメータの意図的改ざんに対するバリデーションはこのタイミングでは行わない。
        // 登録完了後の検索画面でのバリデーションに委ねる。


		// バリデーションの実行モードが有効の場合、バリデーションを実行する。
        // 削除処理、ロック解除処理の場合はログインID 以外のパラメータは不要なので、spring 側から無効化している。
        if (this.useValidation){
        	List<ValidationFailure> errors = new ArrayList<ValidationFailure>();
        	if (!inputForm.validate(errors)){

        		// バリデーションエラー時は、エラー情報を model に設定し、入力画面を表示する。
        		model.put("errors", errors);
        		return new ModelAndView("input" , model);
        	}
        }


        // 各種処理を実行
        try {
        	execute(model, inputForm, loginUser);

        } catch (NotFoundException e) {
            // ログインID が存在しない場合は、該当なし画面へ
        	return new ModelAndView("notFound", model);

        }

		// パスワードの通知メール送信
		sendInformationMail(request, inputForm);

		return new ModelAndView("success" , model);
	}

	/**
	 * お知らせの通知メールを送信する。<br/>
	 * <br/>
	 * @param request HTTP リクエスト
	 * @param inputForm 入力値
	 */
	protected void sendInformationMail(HttpServletRequest request,
			InformationForm inputForm) {

		// 削除する場合 且つ、送信しない
		if (!"delete".equals(inputForm.getCommand())
				&& "1".equals(inputForm.getMailFlg())) {
			// メールテンプレートが設定されていない場合はメールの通知を行わない。
			if (this.sendInformationTemplate == null) {
				log.warn("sendInformationTemplate is null.");
				return;
			}

			// メールテンプレートで使用するパラメータを設定する。
			this.sendInformationTemplate.setParameter("inputForm", inputForm);
			//
			// メール送信
			this.sendInformationTemplate.send();
		}
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
		InformationFormFactory factory = InformationFormFactory.getInstance(request);

		model.put("searchForm", factory.createInformationSearchForm(request));
		model.put("inputForm", factory.createInformationForm(request));

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
	 * @exception NotFoundException 更新対象が存在しない場合
	 */
	protected void execute(Map<String, Object> model, InformationForm inputForm, AdminUserInterface loginUser)
			throws Exception, NotFoundException {

		if (this.mode.equals("insert")){
			insert(model, inputForm, loginUser);
        } else if (this.mode.equals("update")) {
			update(model, inputForm, loginUser);
        } else if (this.mode.equals("delete")) {
			delete(model, inputForm, loginUser);
        } else {
        	// 想定していない処理モードの場合、例外をスローする。
        	throw new RuntimeException ("execute mode bad setting.");
        }

	}



	/**
	 * 新規登録処理<br/>
	 * 引数で渡された内容でお知らせ情報を追加する。<br/>
	 * <br/>
 	 * @param model View 層へ引き渡す model オブジェクト
	 * @param inputForm 入力値が格納された Form オブジェクト
	 * @param loginUser タイムスタンプ更新時に使用するログインユーザー情報
	 *
	 * @exception Exception 実装クラスによりスローされる任意の例外
	 */
	protected void insert(Map<String, Object> model, InformationForm inputForm, AdminUserInterface loginUser)
			throws Exception {

		// 新規登録処理
    	String informationNo = this.informationManager.addInformation(inputForm, (String)loginUser.getUserId());
    	inputForm.setInformationNo(informationNo);
	}



	/**
	 * 更新登録処理<br/>
	 * 引数で渡された内容でお知らせ情報を更新する。<br/>
	 * <br/>
 	 * @param model View 層へ引き渡す model オブジェクト
	 * @param inputForm 入力値が格納された Form オブジェクト
	 * @param loginUser タイムスタンプ更新時に使用するログインユーザー情報
	 *
	 * @exception Exception 実装クラスによりスローされる任意の例外
	 * @exception NotFoundException 更新対象が存在しない場合
	 */
	protected void update(Map<String, Object> model, InformationForm inputForm, AdminUserInterface loginUser)
			throws Exception, NotFoundException {

		// 更新処理
    	this.informationManager.updateInformation(inputForm, (String)loginUser.getUserId());
	}



	/**
	 * 削除処理<br/>
	 * 引数で渡された内容でお知らせ情報を削除する。<br/>
	 * <br/>
 	 * @param model View 層へ引き渡す model オブジェクト
	 * @param inputForm 入力値が格納された Form オブジェクト
	 * @param loginUser タイムスタンプ更新時に使用するログインユーザー情報
	 *
	 * @exception Exception 実装クラスによりスローされる任意の例外
	 */
	protected void delete(Map<String, Object> model, InformationForm inputForm, AdminUserInterface loginUser)
			throws Exception {

		// 削除処理
    	this.informationManager.delInformation(inputForm);
	}

}
