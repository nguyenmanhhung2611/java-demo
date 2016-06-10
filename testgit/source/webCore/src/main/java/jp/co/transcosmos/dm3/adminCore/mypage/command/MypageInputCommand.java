package jp.co.transcosmos.dm3.adminCore.mypage.command;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.constant.CommonParameters;
import jp.co.transcosmos.dm3.core.model.MypageUserManage;
import jp.co.transcosmos.dm3.core.model.exception.NotFoundException;
import jp.co.transcosmos.dm3.core.model.mypage.MypageUserInterface;
import jp.co.transcosmos.dm3.core.model.mypage.form.MypageUserForm;
import jp.co.transcosmos.dm3.core.model.mypage.form.MypageUserFormFactory;
import jp.co.transcosmos.dm3.core.model.mypage.form.MypageUserSearchForm;
import jp.co.transcosmos.dm3.dao.JoinResult;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;

import org.springframework.web.servlet.ModelAndView;

/**
 * マイページ会員情報入力画面.
 * <p>
 * 【新規登録の場合】<br/>
 * <ul>
 * <li>リクエストパラメータで送信された値を受取る。（検索条件や、一覧の表示ページ位置のみ）</li>
 * <li>受け取った値を入力画面に表示する。</li>
 * </ul>
 * <br/>
 * 【更新登録の場合】<br/>
 * <ul>
 * <li>リクエストパラメータで送信された値を受取る。（検索条件や、一覧の表示ページ位置、および主キー値のみ）</li>
 * <li>リクエストパラメータ（userId）に該当するマイページ会員情報を取得する。</li>
 * <li>取得した値を入力画面に表示する。</li>
 * </ul>
 * <br/>
 * 【リクエストパラメータ（command） が "back"の場合】（確認画面からの復帰の場合）<br/>
 * <ul>
 * <li>リクエストパラメータで送信された値を受取る。</li>
 * <li>受け取った値を入力画面に表示する。</li>
 * </ul>
 * <br/>
 * 【復帰する View 名】<br/>
 * <ul>
 * <li>success<li>:検索処理正常終了
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
public class MypageInputCommand implements Command {

	/** マイページ会員メンテナンスを行う Model オブジェクト */
	protected MypageUserManage userManager;

	/** 処理モード (insert = 新規登録処理、 update=更新処理)*/
	protected String mode;

	

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
	 * 管理ユーザ入力画面表示処理<br>
	 * <br>
	 * @param request HTTP リクエスト
	 * @param response HTTP レスポンス
	*/
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// リクエストパラメータを格納した model オブジェクトを生成する。
		// このオブジェクトは View 層への値引渡しに使用される。
        Map<String, Object> model = createModel(request);
        MypageUserForm inputForm = (MypageUserForm) model.get("inputForm");
        MypageUserSearchForm searchForm = (MypageUserSearchForm) model.get("searchForm");

        
		// 更新処理の場合、処理対象となる主キー値がパラメータで渡されていない場合、例外をスローする。
		if (this.mode.equals("update") && StringValidateUtil.isEmpty(searchForm.getUserId())){
			throw new RuntimeException ("pk value is null.");
		}


		// command パラメータが "back" の場合、入力確認画面からの復帰なので、ＤＢから初期値を取得しない。
		// （リクエストパラメータから取得した値を使用する。）
		String command = inputForm.getCommand();
		if (command != null && command.equals("back")) return new ModelAndView("success", model);


        // 新規登録モードで初期画面表示の場合、データの取得は行わない。
        if (this.mode.equals("insert")) return new ModelAndView("success", model);
        
        
        // note
        // 検索条件パラメータの意図的改ざんに対するバリデーションはこのタイミングでは行わない。
        // 登録完了後の検索画面でのバリデーションに委ねる。


        // マイページ会員情報を取得
        JoinResult mypageUser = this.userManager.searchMyPageUserPk(searchForm.getUserId());

        // 該当するデータが存在しない場合
        // （例えば、マイページ会員の検索後に別のセッションから取得対象ユーザーを削除した場合）、
        // 処理対象データ無しの例外をスローする。
        if (mypageUser == null) {
        	throw new NotFoundException();
        }


        // 取得した値を入力用 Form へ格納する。
        // この値は入力フィールドの初期値として表示される。
        CommonParameters commonParameters = CommonParameters.getInstance(request);

		inputForm.setDefaultData(
				(MypageUserInterface) mypageUser.getItems().get(commonParameters.getMemberDbAlias()));

		return new ModelAndView("success", model);
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
		MypageUserFormFactory factory = MypageUserFormFactory.getInstance(request);

		// 検索条件、および、画面コントロールパラメータを取得する。
		MypageUserSearchForm searchForm = factory.createMypageUserSearchForm(request);
		model.put("searchForm", searchForm);

		
		// 入力フォームの受け取りは、command パラメータの値によって異なる。
		// command パラメータが渡されない場合、入力画面の初期表示とみなし、空のフォームを復帰する。
		// command パラメータで "back" が渡された場合、入力確認画面からの復帰を意味する。
		// その場合はリクエストパラメータを受け取り、入力画面へ初期表示する。
		// もし、"back" 以外の値が渡された場合、command パラメータが渡されない場合と同等に扱う。

		MypageUserForm inputForm = factory.createMypageUserForm(request);
		String command = inputForm.getCommand();
		
		if (command != null && command.equals("back")){
			model.put("inputForm", inputForm);
		} else {
			model.put("inputForm", factory.createMypageUserForm());
		}

		return model;

	}
	
}
