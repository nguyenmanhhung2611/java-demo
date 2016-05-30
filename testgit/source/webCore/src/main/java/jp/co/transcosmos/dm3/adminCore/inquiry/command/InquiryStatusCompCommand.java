package jp.co.transcosmos.dm3.adminCore.inquiry.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.model.InquiryManage;
import jp.co.transcosmos.dm3.core.model.adminUser.AdminUserInterface;
import jp.co.transcosmos.dm3.core.model.exception.NotFoundException;
import jp.co.transcosmos.dm3.core.model.inquiry.form.InquiryFormFactory;
import jp.co.transcosmos.dm3.core.model.inquiry.form.InquiryStatusForm;
import jp.co.transcosmos.dm3.core.util.AdminLoginUserUtils;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

import org.springframework.web.servlet.ModelAndView;

/**
 * お問合せの変更処理.
 * <p>
 * <ul>
 * <li>リクエストパラメータを受け取り、バリデーションを実行する。</li>
 * <li>バリデーションが正常終了した場合、お問合せｓ情報を更新する。</li>
 * <li>もし、更新対象データが存在しない場合、更新処理が継続できないので該当無し画面を表示する。</li>
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
 * Y.Cho		2015.04.09	新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 * 
 */
public class InquiryStatusCompCommand implements Command {
	
	/** お問合せメンテナンスを行う Model オブジェクト */
	protected InquiryManage inquiryManager;
	
	/** 主キーとなる UserId の必須チェックを行う場合、true を設定する。 （デフォルト true）　*/
	protected boolean useUserIdValidation = true;
	
	/** Form のバリデーションを実行する場合、true を設定する。　（デフォルト true） */
	protected boolean useValidation = true;
	
	/**
	 * お問合せメンテナンスを行う Model　オブジェクトを設定する。<br/>
	 * <br/>
	 * @param inquiryManager お問合せメンテナンスの model オブジェクト
	 */
	public void setInquiryManager(InquiryManage inquiryManager) {
		this.inquiryManager = inquiryManager;
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
	 * お問合せ情報の変更処理<br>
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
        InquiryStatusForm inputForm = (InquiryStatusForm) model.get("inputForm");
        
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
        	if (StringValidateUtil.isEmpty(inputForm.getInquiryId())) throw new RuntimeException ("pk value is null.");
        }


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
        
		return new ModelAndView("success" , model);
	}
	

	/**
	 * リクエストパラメータから Form オブジェクトを作成する。<br/>
	 * 生成した Form オブジェクトは Map に格納して復帰する。<br/>
	 * key = フォームクラス名（パッケージなし）、Value = フォームオブジェクト
	 * <br/>
	 * @param request HTTP リクエストパラメータ
	 * @return パラメータが設定されたフォームオブジェクトを格納した Map オブジェクト
	 */
	protected Map<String, Object> createModel(HttpServletRequest request) {

		Map<String, Object> model = new HashMap<>();

		// リクエストパラメータを取得して Form オブジェクトを作成する。
		InquiryFormFactory factory = InquiryFormFactory.getInstance(request);

		model.put("searchForm", factory.createInquirySearchForm(request));
		model.put("inputForm", factory.createInquiryStatusForm(request));

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
	protected void execute(Map<String, Object> model, InquiryStatusForm inputForm, AdminUserInterface loginUser)
			throws Exception, NotFoundException {

		update(model, inputForm, loginUser);
	}


	/**
	 * 更新登録処理<br/>
	 * 引数で渡された内容でお問合せ情報を更新する。<br/>
	 * <br/>
 	 * @param model View 層へ引き渡す model オブジェクト
	 * @param inputForm 入力値が格納された Form オブジェクト
	 * @param loginUser タイムスタンプ更新時に使用するログインユーザー情報
	 * 
	 * @exception Exception 実装クラスによりスローされる任意の例外
	 * @exception NotFoundException 更新対象が存在しない場合
	 */
	protected void update(Map<String, Object> model, InquiryStatusForm inputForm, AdminUserInterface loginUser)
			throws Exception, NotFoundException {
    	
		// 更新処理
    	this.inquiryManager.updateInquiryStatus(inputForm, (String)loginUser.getUserId());
	}

}
