package jp.co.transcosmos.dm3.adminCore.mypage.command;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.model.MypageUserManage;
import jp.co.transcosmos.dm3.core.model.exception.NotFoundException;
import jp.co.transcosmos.dm3.core.model.mypage.form.MypageUserFormFactory;
import jp.co.transcosmos.dm3.core.model.mypage.form.MypageUserSearchForm;
import jp.co.transcosmos.dm3.dao.JoinResult;


/**
 * マイページ会員詳細画面　（削除処理の確認画面兼用）.
 * <p>
 * リクエストパラメータ（userId）で渡された値に該当するマイページ会員情報を取得し画面表示する。
 * <br/>
 * 【復帰する View 名】<br/>
 * <ul>
 * <li>success<li>:検索処理正常終了
 * </ul>
 * <p>
 * <pre>
 * 担当者		修正日		修正内容
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.03.23	新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 * 
 */
public class MypageDetailCommand implements Command {

	/** マイページ会員メンテナンスを行う Model オブジェクト */
	private MypageUserManage userManager;

	/**
	 * マイページ会員メンテナンスを行う Model　オブジェクトを設定する。<br/>
	 * <br/>
	 * @param userManager マイページ会員メンテナンスの model オブジェクト
	 */
	public void setUserManager(MypageUserManage userManager) {
		this.userManager = userManager;
	}



	/**
	 * マイページ会員詳細表示処理<br>
	 * <br>
	 * @param request HTTP リクエスト
	 * @param response HTTP レスポンス
	 * @return ModelAndView　のインスタンス
	*/
	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		// リクエストパラメータを格納した model オブジェクトを生成する。
		// このオブジェクトは View 層への値引渡しに使用される。
        Map<String, Object> model = createModel(request);
        MypageUserSearchForm searchForm = (MypageUserSearchForm) model.get("searchForm");

		
        // note
        // 検索条件パラメータの意図的改ざんに対するバリデーションはこのタイミングでは行わない。
        // 登録完了後の検索画面でのバリデーションに委ねる。

		
        // 指定されたユーザーID に該当するマイページ会員の情報を取得する。
        JoinResult mypageUser = this.userManager.searchMyPageUserPk(searchForm.getUserId());

        // 該当するデータが存在しない場合
        // （例えば、管理ユーザーの検索後に別のセッションから取得対象ユーザーを削除した場合）、
        // 処理対象データ無しの例外をスローする。
        if (mypageUser == null) {
        	throw new NotFoundException();
        }

        // 取得できた場合は model に設定する。
		model.put("mypageUser", mypageUser);

		return new ModelAndView("success", model);

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
		model.put("searchForm", factory.createMypageUserSearchForm(request));

		return model;
	}

}
