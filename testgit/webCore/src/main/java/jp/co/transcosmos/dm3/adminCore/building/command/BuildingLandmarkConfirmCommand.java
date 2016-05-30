package jp.co.transcosmos.dm3.adminCore.building.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.model.building.form.BuildingFormFactory;
import jp.co.transcosmos.dm3.core.model.building.form.BuildingLandmarkForm;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

import org.springframework.web.servlet.ModelAndView;

/**
 * <pre>
 * 地域情報入力確認画面
 * リクエストパラメータで渡された管理ユーザー情報のバリデーションを行い、確認画面を表示する。
 * もしバリデーションエラーが発生した場合は入力画面として表示する。
 * 
 * 【復帰する View 名】
 *    ・"success" : 正常終了
 *    ・"input" : バリデーションエラーによる再入力
 *
 * 担当者       修正日      修正内容
 * ------------ ----------- -----------------------------------------------------
 * I.Shu		2015.03.18	新規作成
 *
 * 注意事項
 * 
 * </pre>
 */
public class BuildingLandmarkConfirmCommand implements Command {

	/** 処理モード (insert = 新規登録処理、 update=更新処理)*/
	protected String mode;
	
	/**
	 * 処理モードを設定する<br/>
	 * <br/>
	 * @param mode "insert" = 新規登録処理、"update" = 更新処理
	 */
	public void setMode(String mode) {
		this.mode = mode;
	}
	
	/**
	 * 地域情報入力確認画面表示処理<br>
	 * <br>
	 * @param request HTTP リクエスト
	 * @param response HTTP レスポンス
	*/
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// リクエストパラメータを格納した model オブジェクトを生成する。
 		// このオブジェクトは View 層への値引渡しに使用される。
         Map<String, Object> model = createModel(request);
         BuildingLandmarkForm inputForm = (BuildingLandmarkForm) model.get("inputForm"); 
         
         
        // 更新モードの場合、更新対象の主キー値がパラメータで渡されていない場合、例外をスローする。
        if ("update".equals(this.mode)){
        	if (StringValidateUtil.isEmpty(inputForm.getSysBuildingCd())) throw new RuntimeException ("pk value is null.");
        }
        
        // view 名の初期値を設定
        String viewName = "success"; 
        
		// バリデーションを実行
        List<ValidationFailure> errors = new ArrayList<ValidationFailure>();
        if (!inputForm.validate(errors)){
        	
        	// バリデーションエラー時は、エラー情報を model に設定し、入力画面を表示する。
        	model.put("errors", errors);
        	viewName = "input";
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
		BuildingFormFactory factory = BuildingFormFactory.getInstance(request);

		model.put("searchForm", factory.createBuildingSearchForm(request));
		model.put("inputForm", factory.createBuildingLandmarkForm(request));

		return model;

	}
}
