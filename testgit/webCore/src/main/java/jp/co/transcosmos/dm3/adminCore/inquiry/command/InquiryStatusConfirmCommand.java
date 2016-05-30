package jp.co.transcosmos.dm3.adminCore.inquiry.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.model.inquiry.form.InquiryFormFactory;
import jp.co.transcosmos.dm3.core.model.inquiry.form.InquiryStatusForm;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

import org.springframework.web.servlet.ModelAndView;

/**
 * <pre>
 * お問合せ情報入力確認画面
 * リクエストパラメータで渡されたお問合せ情報のバリデーションを行い、確認画面を表示する。
 * もしバリデーションエラーが発生した場合は入力画面として表示する。
 * 
 * 【復帰する View 名】
 *    ・"success" : 正常終了
 *    ・"input" : バリデーションエラーによる再入力
 *
 * 担当者       修正日      修正内容
 * ------------ ----------- -----------------------------------------------------
 * Y.Cho		2015.04.09	新規作成
 *
 * 注意事項
 * 
 * </pre>
*/
public class InquiryStatusConfirmCommand implements Command {
	
	/** Form のバリデーションを実行する場合、true を設定する。　（デフォルト true） */
	protected boolean useValidation = true;
	
	/**
	 * Form のバリデーションを実行する場合、true を設定する。　（デフォルト true）<br/>
	 * <br/>
	 * @param useValidation true の場合、Form のバリデーションを実行
	 */
	public void setUseValidation(boolean useValidation) {
		this.useValidation = useValidation;
	}

	/**
	 * お問合せ情報入力確認画面表示処理<br>
	 * <br>
	 * @param request HTTP リクエスト
	 * @param response HTTP レスポンス
	*/
	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

 		// リクエストパラメータを格納した model オブジェクトを生成する。
 		// このオブジェクトは View 層への値引渡しに使用される。
         Map<String, Object> model = createModel(request);
         InquiryStatusForm inputForm = (InquiryStatusForm) model.get("inputForm"); 
         
         
        // 更新対象の主キー値がパラメータで渡されていない場合、例外をスローする。
       	if (StringValidateUtil.isEmpty(inputForm.getInquiryId())) throw new RuntimeException ("pk value is null.");

        // note
        // 検索条件パラメータの意図的改ざんに対するバリデーションはこのタイミングでは行わない。
        // 登録完了後の検索画面でのバリデーションに委ねる。

        
        // view 名の初期値を設定
        String viewName = "success"; 
        
		// バリデーションを実行
        if (this.useValidation){
            List<ValidationFailure> errors = new ArrayList<ValidationFailure>();
            if (!inputForm.validate(errors)){
            	// バリデーションエラー時は、エラー情報を model に設定し、入力画面を表示する。
            	model.put("errors", errors);
            	viewName = "input";
            }
        }

        return new ModelAndView(viewName, model);
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
	
}
