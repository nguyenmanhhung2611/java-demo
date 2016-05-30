package jp.co.transcosmos.dm3.webAdmin.memberInfo.command;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.vo.PrefMst;
import jp.co.transcosmos.dm3.corePana.model.PanaCommonManage;
import jp.co.transcosmos.dm3.corePana.model.member.form.MemberFormFactory;
import jp.co.transcosmos.dm3.corePana.model.member.form.MemberSearchForm;

import org.springframework.web.servlet.ModelAndView;

/**
 * <pre>
 * 会員情報の検索・一覧初期化
 * 都道府県情報を取得
 *
 * 【復帰する View 名】
 *    ・"success" : 検索処理正常終了
 *
 * 担当者         修正日      修正内容
 * -------------- ----------- -----------------------------------------------------
 * tang.tianyun	  2015.04.15	新規作成
 *
 * 注意事項
 *
 * </pre>
 */
public class MemberInfoInitCommand implements Command {

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

	/**
	 * 会員情報リクエスト処理<br>
	 * 会員情報のリクエストがあったときに呼び出される。 <br>
	 *
	 * @param request
	 *            クライアントからのHttpリクエスト。
	 * @param response
	 *            クライアントに返すHttpレスポンス。
	 */
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 検索パラメータ受信
        Map<String, Object> model = new HashMap<String, Object>();

        // 都道府県マスタを取得する
        List<PrefMst> prefMstList = this.panamCommonManager.getPrefMstList();
 		model.put("prefMstList", prefMstList);

        // リクエストパラメータを取得して Form オブジェクトを作成する。
        MemberFormFactory factory = MemberFormFactory.getInstance(request);
        MemberSearchForm searchForm = factory.createMypageUserSearchForm(request);

        // 登録経路を設定する。
        searchForm.setKeyEntryRoute("000");

        model.put("searchForm", searchForm);

		return new ModelAndView("success", model);
	}

}