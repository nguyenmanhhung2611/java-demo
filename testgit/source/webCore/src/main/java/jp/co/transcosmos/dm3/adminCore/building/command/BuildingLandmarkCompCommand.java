package jp.co.transcosmos.dm3.adminCore.building.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.model.BuildingManage;
import jp.co.transcosmos.dm3.core.model.building.form.BuildingFormFactory;
import jp.co.transcosmos.dm3.core.model.building.form.BuildingLandmarkForm;
import jp.co.transcosmos.dm3.core.model.exception.NotFoundException;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

import org.springframework.web.servlet.ModelAndView;

/**
 * 地域情報の変更処理.
 * <p>
 * 【更新登録の場合】<br/>
 * <ul>
 * <li>リクエストパラメータを受け取り、バリデーションを実行する。</li>
 * <li>地域情報は一度削除し、変更後の地域情報を新規登録する。</li>
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
 * I.Shu		2015.03.18	新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 * 
 */
public class BuildingLandmarkCompCommand implements Command  {

	/** 建物メンテナンスを行う Model オブジェクト */
	protected BuildingManage buildingManager;

	/** 処理モード (insert = 新規登録処理、 update=更新処理、delete=削除処理)*/
	protected String mode;

	/** Form のバリデーションを実行する場合、true を設定する。　（デフォルト true） */
	protected boolean useValidation = true;

	
	/**
	 * 建物メンテナンスを行う Model　オブジェクトを設定する。<br/>
	 * <br/>
	 * @param buildingManager 建物メンテナンスの model オブジェクト
	 */
	public void setBuildingManager(BuildingManage buildingManager) {
		this.buildingManager = buildingManager;
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
	 * 最寄り駅情報の変更処理<br>
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
        BuildingLandmarkForm inputForm = (BuildingLandmarkForm) model.get("inputForm");
        
        // 完了画面でリロードした場合、更新処理が意図せず実行される問題が発生する。
        // その問題を解消する為、view 名で "success"　を指定すると自動リダイレクト画面が表示される。
        // このリダイレクト画面は、command パラメータを "redirect"　に設定して完了画面へリクエストを
        // 送信する。
        // よって、command = "redirect" の場合は、ＤＢ更新は行わず、完了画面を表示する。
        String command = inputForm.getCommand();
        if (command != null && "redirect".equals(command)){
        	return new ModelAndView("comp" , model);
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
        	execute(inputForm);

        } catch (NotFoundException e) {
            // 更新対象が存在しない場合は、該当なし画面へ
        	return new ModelAndView("notFound", model);

        }

		return new ModelAndView("success" , model);
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
		BuildingFormFactory factory = BuildingFormFactory.getInstance(request);

		model.put("searchForm", factory.createBuildingSearchForm(request));
		model.put("inputForm", factory.createBuildingLandmarkForm(request));

		return model;

	}
	
	/**
	 * 処理の振り分けと実行を行う。<br/>
	 * <br/>
 	 * @param model View 層へ引き渡す model オブジェクト
	 * @param inputForm 入力値が格納された Form オブジェクト
	 * 
	 * @exception Exception 実装クラスによりスローされる任意の例外
	 * @exception NotFoundException 更新対象が存在しない場合
	 */
	protected void execute(BuildingLandmarkForm inputForm)
			throws Exception, NotFoundException {

		if (this.mode.equals("update")){
			// 更新
	    	this.buildingManager.updateBuildingLandmark(inputForm);
        }  else {
        	// 想定していない処理モードの場合、例外をスローする。
        	throw new RuntimeException ("execute mode bad setting.");
        }

	}
}
