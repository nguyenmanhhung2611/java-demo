package jp.co.transcosmos.dm3.adminCore.building.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.model.BuildingManage;
import jp.co.transcosmos.dm3.core.model.adminUser.AdminUserInterface;
import jp.co.transcosmos.dm3.core.model.building.form.BuildingForm;
import jp.co.transcosmos.dm3.core.model.building.form.BuildingFormFactory;
import jp.co.transcosmos.dm3.core.model.exception.NotFoundException;
import jp.co.transcosmos.dm3.core.util.AdminLoginUserUtils;
import jp.co.transcosmos.dm3.core.vo.AddressMst;
import jp.co.transcosmos.dm3.core.vo.PrefMst;
import jp.co.transcosmos.dm3.dao.DAO;
import jp.co.transcosmos.dm3.dao.DAOCriteria;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

import org.springframework.web.servlet.ModelAndView;

/**
 * 建物情報の追加、変更、削除処理.
 * <p>
 * 【新規登録の場合】<br/>
 * <ul>
 * <li>リクエストパラメータを受け取り、バリデーションを実行する。</li>
 * <li>バリデーションが正常終了した場合、建物情報を新規登録する。</li>
 * <li>また、公開先が特定個人の場合、建物公開先情報も新規登録する。</li>
 * </ul>
 * <br/>
 * 【更新登録の場合】<br/>
 * <ul>
 * <li>リクエストパラメータを受け取り、バリデーションを実行する。</li>
 * <li>バリデーションが正常終了した場合、建物情報を更新する。</li>
 * <li>建物公開先情報は一度削除し、変更後の公開先が特定個人であれば、建物公開先情報も新規登録する。</li>
 * <li>もし、更新対象データが存在しない場合、更新処理が継続できないので該当無し画面を表示する。</li>
 * </ul>
 * <br/>
 * 【削除登録の場合】<br/>
 * <ul>
 * <li>リクエストパラメータを受け取り、バリデーションを実行する。（主キー値のみ）</li>
 * <li>バリデーションが正常終了した場合、建物情報を削除する。</li>
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
 * I.Shu		2015.03.2	新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 * 
 */
public class BuildingCompCommand implements Command  {

	/** 建物メンテナンスを行う Model オブジェクト */
	protected BuildingManage buildingManager;

	/** 処理モード (insert = 新規登録処理、 update=更新処理、delete=削除処理)*/
	protected String mode;

	/** Form のバリデーションを実行する場合、true を設定する。　（デフォルト true） */
	protected boolean useValidation = true;

	/** 都道府県マスタ取得用 DAO */
	protected DAO<PrefMst> prefMstDAO;
	
	/** 市区町村マスタ取得用 DAO */
	protected DAO<AddressMst> addressMstDAO;
	
	
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
	 * 都道府県マスタ用DAOを設定する。<br/>
	 * <br/>
	 * 
	 * @return 市区町村マスタ用 DAO
	 */
	public void setPrefMstDAO(DAO<PrefMst> prefMstDAO) {
		this.prefMstDAO = prefMstDAO;
	}

	/**
	 * 市区町村マスタ用DAOを設定する。<br/>
	 * <br/>
	 * 
	 * @return 市区町村マスタ用 DAO
	 */
	public void setAddressMstDAO(DAO<AddressMst> addressMstDAO) {
		this.addressMstDAO = addressMstDAO;
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
	 * 建物情報の追加、変更、削除処理<br>
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
        BuildingForm inputForm = (BuildingForm) model.get("inputForm"); 
        
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

		// バリデーションの実行モードが有効の場合、バリデーションを実行する。
        // 削除処理、ロック解除処理の場合はログインID 以外のパラメータは不要なので、spring 側から無効化している。
        if (this.useValidation){
        	List<ValidationFailure> errors = new ArrayList<ValidationFailure>();
        	if (!inputForm.validate(errors)){
        		// 都道府県マスタを取得する
        		List<PrefMst> prefMstList = this.prefMstDAO.selectByFilter(null);
        		model.put("prefMstList", prefMstList);
    			// 市区町村マスタを取得する
    			model.put("addressMstList", getAddressMstList(inputForm.getPrefCd()));
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
		model.put("inputForm", factory.createBuildingForm(request));

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
	protected void execute(Map<String, Object> model, BuildingForm inputForm, AdminUserInterface loginUser)
			throws Exception, NotFoundException {

		if (this.mode.equals("insert")){
			insert(inputForm, loginUser);
        } else if (this.mode.equals("update")) {
			update(inputForm, loginUser);
        } else if (this.mode.equals("delete")) {
			delete(inputForm);
        } else {
        	// 想定していない処理モードの場合、例外をスローする。
        	throw new RuntimeException ("execute mode bad setting.");
        }

	}
	
	/**
	 * 新規登録処理<br/>
	 * 引数で渡された内容でお知らせ情報を追加する。<br/>
	 * <br/>
	 * @param inputForm 入力値が格納された Form オブジェクト
	 * @param loginUser タイムスタンプ更新時に使用するログインユーザー情報
	 * 
	 * @exception Exception 実装クラスによりスローされる任意の例外
	 */
	protected void insert(BuildingForm inputForm, AdminUserInterface loginUser)
			throws Exception {

		// 新規登録処理
    	String sysBuildingCd = this.buildingManager.addBuilding(inputForm, (String)loginUser.getUserId());
    	
    	// 建物閲覧へのキー
    	inputForm.setSysBuildingCd(sysBuildingCd);
	}

	/**
	 * 更新登録処理<br/>
	 * 引数で渡された内容で建物情報を更新する。<br/>
	 * <br/>
	 * @param inputForm 入力値が格納された Form オブジェクト
	 * @param loginUser タイムスタンプ更新時に使用するログインユーザー情報
	 * 
	 * @exception Exception 実装クラスによりスローされる任意の例外
	 * @exception NotFoundException 更新対象が存在しない場合
	 */
	protected void update(BuildingForm inputForm, AdminUserInterface loginUser)
			throws Exception, NotFoundException {
    	
		// 更新処理
    	this.buildingManager.updateBuildingInfo(inputForm, (String)loginUser.getUserId());

	}
	
	/**
	 * 削除処理<br/>
	 * 引数で渡された内容で建物情報を削除する。<br/>
	 * <br/>
	 * @param inputForm 入力値が格納された Form オブジェクト
	 * 
	 * @exception Exception 実装クラスによりスローされる任意の例外
	 * @exception NotFoundException 更新対象が存在しない場合
	 */
	protected void delete(BuildingForm inputForm)
			throws Exception, NotFoundException {
    	
		// 削除処理
    	this.buildingManager.delBuildingInfo(inputForm.getSysBuildingCd());

	}
	
	/**
	 * 都道府県CDで市区町村リストを取得する。<br/>
	 * <br/>
	 * @param prefCd 都道府県CD
	 * @return 市区町村リストを復帰する。
	 */
	protected List<AddressMst> getAddressMstList(String prefCd) {
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("prefCd", prefCd);
		List<AddressMst> addressMstList = this.addressMstDAO.selectByFilter(criteria);
		return addressMstList;
		
	}
}
