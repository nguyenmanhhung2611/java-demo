package jp.co.transcosmos.dm3.webAdmin.userListPopup.command;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.adminCore.mypage.command.MypageListCommand;
import jp.co.transcosmos.dm3.core.model.mypage.form.MypageUserSearchForm;
import jp.co.transcosmos.dm3.core.vo.PrefMst;
import jp.co.transcosmos.dm3.corePana.model.PanaCommonManage;

import org.springframework.web.servlet.ModelAndView;

/**
 * <pre>
 * 会員情報の検索・一覧
 *
 * 【復帰する View 名】
 *    ・"success" : 検索処理正常終了
 *
 * 担当者         修正日      修正内容
 * -------------- ----------- -----------------------------------------------------
 * tang.tianyun	  2015.04.13	新規作成
 *
 * 注意事項
 *
 * </pre>
 */
public class UserListPopupCommand extends MypageListCommand {

	/** 共通情報取用 Model オブジェクト */
	private PanaCommonManage panamCommonManager;

	/**
	 * 共通情報取用 Model オブジェクトを設定する。<br/>
	 * <br/>
	 * @param PanaCommonManage 共通情報取用 Model オブジェクト
	 */
	public void setPanamCommonManager(PanaCommonManage panamCommonManager) {
		this.panamCommonManager = panamCommonManager;
	}

	/** ページの表示数 */
    private int visibleNavigationPageCount = 10;

    /**
     * ページの表示数を設定する。<br>
     * @param visibleNavigationPageCount ページ数
     */
    public void setVisibleNavigationPageCount(int visibleNavigationPageCount) {
        this.visibleNavigationPageCount = visibleNavigationPageCount;
    }

    /**
	 * 会員一覧表示処理<br>
	 * <br>
	 *
	 * @param request
	 *            クライアントからのHttpリクエスト。
	 * @param response
	 *            クライアントに返すHttpレスポンス。
	 */
    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // model オブジェクトを取得する。
        ModelAndView modelAndView = super.handleRequest(request, response);
        Map<String, Object> model = modelAndView.getModel();
        // 都道府県マスタを取得する
        List<PrefMst> prefMstList = this.panamCommonManager.getPrefMstList();
 		model.put("prefMstList", prefMstList);

 	    // リクエストパラメータを取得して Form オブジェクトを作成する。
 		MypageUserSearchForm searchForm = (MypageUserSearchForm) modelAndView.getModel()
				.get("searchForm");
 		// ページ数を設定する。
        searchForm.setVisibleNavigationPageCount(this.visibleNavigationPageCount);
		return modelAndView;

    }
}
