package jp.co.transcosmos.dm3.adminCore.news.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.model.InformationManage;
import jp.co.transcosmos.dm3.core.model.NewsManage;
import jp.co.transcosmos.dm3.core.model.information.form.InformationForm;
import jp.co.transcosmos.dm3.core.model.information.form.InformationFormFactory;
import jp.co.transcosmos.dm3.core.model.news.form.NewsForm;
import jp.co.transcosmos.dm3.core.model.news.form.NewsFormFactory;
import jp.co.transcosmos.dm3.core.vo.MemberInfo;
import jp.co.transcosmos.dm3.dao.DAO;
import jp.co.transcosmos.dm3.dao.JoinResult;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;

/**
 * <pre>
 * お知らせ情報入力確認画面
 * リクエストパラメータで渡された管理ユーザー情報のバリデーションを行い、確認画面を表示する。
 * もしバリデーションエラーが発生した場合は入力画面として表示する。
 * 
 * 【復帰する View 名】
 *    ・"success" : 正常終了
 *    ・"input" : バリデーションエラーによる再入力
 *
 * 担当者       修正日      修正内容
 * ------------ ----------- -----------------------------------------------------
 * I.Shu		2015.02.09	新規作成
 *
 * 注意事項
 * 
 * </pre>
*/
public class NewsConfirmCommand implements Command {

	/** お知らせメンテナンスを行う Model オブジェクト */
	protected NewsManage newsManager;
	
	/** 処理モード (insert = 新規登録処理、 update=更新処理、 delete=削除処理)*/
	protected String mode;

	/** マイページ会員情報取得用 DAO */
	protected DAO<MemberInfo> memberInfoDAO;
	
	/** Form のバリデーションを実行する場合、true を設定する。　（デフォルト true） */
	protected boolean useValidation = true;
	
	/**
	 * お知らせメンテナンスを行う Model　オブジェクトを設定する。<br/>
	 * <br/>
	 * @param newsManager お知らせメンテナンスの model オブジェクト
	 */
	public void setNewsManager(NewsManage newsManager) {
		this.newsManager = newsManager;
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
	 * Form のバリデーションを実行する場合、true を設定する。　（デフォルト true）<br/>
	 * <br/>
	 * @param useValidation true の場合、Form のバリデーションを実行
	 */
	public void setUseValidation(boolean useValidation) {
		this.useValidation = useValidation;
	}

	
	/**
	 * マイページ会員情報取得用 DAOを設定する。<br/>
	 * <br/>
	 * @param memberInfoDAO マイページ会員情報取得用 DAO
	 */
	public void setMemberInfoDAO(DAO<MemberInfo> memberInfoDAO) {
		this.memberInfoDAO = memberInfoDAO;
	}
	
	/**
	 * お知らせ入力確認画面表示処理<br>
	 * <br>
	 * @param request HTTP リクエスト
	 * @param response HTTP レスポンス
	*/
	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

 		// このオブジェクトは View 層への値引渡しに使用される。
         Map<String, Object> model = createModel(request);
         NewsForm inputForm = (NewsForm) model.get("inputForm"); 
         
         
        // 更新モードの場合、更新対象の主キー値がパラメータで渡されていない場合、例外をスローする。
        if ("update".equals(this.mode)){
        	if (StringValidateUtil.isEmpty(inputForm.getNewsId())) throw new RuntimeException ("pk value is null.");
        }
        
        // view 名の初期値を設定
        String viewName = "success"; 
        
		// バリデーションを実行
        if (this.useValidation){
	        List<ValidationFailure> errors = new ArrayList<ValidationFailure>();
	        if (!inputForm.validate(errors)){
	        	// バリデーションエラー時は、エラー情報を model に設定し、入力画面を表示する。
	        	model.put("errors", errors);
	        	viewName = "input";
	        	return new ModelAndView(viewName , model);
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
		NewsFormFactory factory = NewsFormFactory.getInstance(request);

		model.put("searchForm", factory.createInformationSearchForm(request));
		model.put("inputForm", factory.createInformationForm(request));

		return model;

	}

}
