package jp.co.transcosmos.dm3.core.command;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.model.building.dao.StationMstListDAO;
import jp.co.transcosmos.dm3.core.vo.StationMst;

import org.springframework.web.servlet.ModelAndView;

/**
 * <pre>
 * 駅マスタの検索
 * 駅情報を取得
 * 
 * 【復帰する View 名】
 *    ・"success" : 検索処理正常終了
 * 
 * 担当者       修正日      修正内容
 * ------------ ----------- -----------------------------------------------------
 * I.Shu		2015.03.10	新規作成
 * 
 * 注意事項
 * 
 * </pre>
 */
public class StationMstListCommand implements Command {
	
	/** 駅マスタ取得用 DAO */
	protected StationMstListDAO stationMstListDAO;

	/**
	 * 駅マスタ取得用 DAOを設定する。<br/>
	 * <br/>
	 * @param stationMstListDAO 駅マスタ取得用 DAO
	 */
	public void setStationMstListDAO(StationMstListDAO stationMstListDAO) {
		this.stationMstListDAO = stationMstListDAO;
	}
	
	
	/**
	 * 駅マスタの検索処理<br>
	 * 駅情報のリクエストがあったときに呼び出される。 <br>
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
        String routeCd = request.getParameter("routeCd");
        
		// 市区町村マスタを取得する
        Object[] params = new Object[] {prefCd, routeCd};
		List<StationMst> stationMstList = this.stationMstListDAO.listStationMst(params);

		return new ModelAndView("success", "stationMstList", stationMstList);
	}


}
