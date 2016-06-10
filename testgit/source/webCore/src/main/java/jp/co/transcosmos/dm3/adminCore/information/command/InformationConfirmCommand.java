package jp.co.transcosmos.dm3.adminCore.information.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.model.InformationManage;
import jp.co.transcosmos.dm3.core.model.information.form.InformationForm;
import jp.co.transcosmos.dm3.core.model.information.form.InformationFormFactory;
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
public class InformationConfirmCommand implements Command {

	/** お知らせメンテナンスを行う Model オブジェクト */
	protected InformationManage informationManager;
	
	/** 処理モード (insert = 新規登録処理、 update=更新処理、 delete=削除処理)*/
	protected String mode;

	/** マイページ会員情報取得用 DAO */
	protected DAO<MemberInfo> memberInfoDAO;
	
	/** Form のバリデーションを実行する場合、true を設定する。　（デフォルト true） */
	protected boolean useValidation = true;
	
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

 		// リクエストパラメータを格納した model オブジェクトを生成する。
 		// このオブジェクトは View 層への値引渡しに使用される。
         Map<String, Object> model = createModel(request);
         InformationForm inputForm = (InformationForm) model.get("inputForm"); 
         
         
        // 更新モードの場合、更新対象の主キー値がパラメータで渡されていない場合、例外をスローする。
        if ("update".equals(this.mode)){
        	if (StringValidateUtil.isEmpty(inputForm.getInformationNo())) throw new RuntimeException ("pk value is null.");
        }


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
	        	return new ModelAndView(viewName , model);
	        }
        }

        // ユーザー情報が指定された場合、DB との照合チェックを行う
        // 特定会員の場合
        if (!"delete".equals(this.mode) && !StringUtils.isEmpty(inputForm.getDspFlg()) && "2".equals(inputForm.getDspFlg())) {
        	MemberInfo memberInfo = memberInfoDAO.selectByPK(inputForm.getUserId());
        	if (memberInfo == null) {
        		throw new RuntimeException ("指定された会員は存在していません。");
        	}
        }
        
        if ("delete".equals(this.mode)){
            // お知らせ番号
            String informationNo = inputForm.getInformationNo();
            
            // 指定されたinformationNoに該当するお知らせの情報を取得する。
            // 建物情報を取得
            JoinResult informationDetail = this.informationManager.searchAdminInformationPk(informationNo);
            
            // 該当するデータが存在しない場合
            // 該当無し画面を表示する。
            if (informationDetail == null) {
            	return new ModelAndView("notFound", model);
            }
            
            // 取得できた場合は model に設定する。
    		model.put("informationDetail", informationDetail);
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
		InformationFormFactory factory = InformationFormFactory.getInstance(request);

		model.put("searchForm", factory.createInformationSearchForm(request));
		model.put("inputForm", factory.createInformationForm(request));

		return model;

	}

}
