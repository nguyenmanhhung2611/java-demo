package jp.co.transcosmos.dm3.webAdmin.housingInfo.command;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.vo.RouteMst;
import jp.co.transcosmos.dm3.corePana.model.PanaCommonManage;

import org.springframework.web.servlet.ModelAndView;

/**
 * <pre>
 * 路線の検索
 * 路線情報を取得
 *
 * 【復帰する View 名】
 *    ・"success" : 検索処理正常終了
 *
 * 担当者       修正日      修正内容
 * ------------ ----------- -----------------------------------------------------
 * fan			2015.04.06	新規作成
 *
 * 注意事項
 *
 * </pre>
 */
public class RouteMstListCommand implements Command {

	/** マスタ情報メンテナンスを行う Model オブジェクト */
	private PanaCommonManage panamCommonManager;

	/**
	 * マスタ情報メンテナンスを行う Model　オブジェクトを設定する。<br/>
	 * <br/>
	 *
	 * @param panaCommonManager
	 *            マスタ情報メンテナンスの model オブジェクト
	 */
	public void setPanamCommonManager(PanaCommonManage panamCommonManager) {
		this.panamCommonManager = panamCommonManager;
	}

	/**
	 * 路線マスタの検索処理<br>
	 * 路線マスタ情報のリクエストがあったときに呼び出される。 <br>
	 *
	 * @param request
	 *            クライアントからのHttpリクエスト。
	 * @param response
	 *            クライアントに返すHttpレスポンス。
	 */
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 検索パラメータ受信
        String prefCd = request.getParameter("prefCd");

		// 路線マスタを取得する
		List<RouteMst> routeMstList = panamCommonManager.getPrefCdToRouteMstList(prefCd);
		return new ModelAndView("success", "routeMstList", routeMstList);
	}
}
