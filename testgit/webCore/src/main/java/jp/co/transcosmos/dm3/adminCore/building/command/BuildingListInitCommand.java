package jp.co.transcosmos.dm3.adminCore.building.command;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.vo.PrefMst;
import jp.co.transcosmos.dm3.dao.DAO;

import org.springframework.web.servlet.ModelAndView;

/**
 * <pre>
 * 建物情報の検索・一覧初期化
 * 都道府県情報を取得
 * 
 * 【復帰する View 名】
 *    ・"success" : 検索処理正常終了
 * 
 * 担当者       修正日      修正内容
 * ------------ ----------- -----------------------------------------------------
 * I.Shu		2015.03.03	新規作成
 * 
 * 注意事項
 * 
 * </pre>
 */
public class BuildingListInitCommand implements Command {
	
	/** 都道府県マスタ取得用 DAO */
	protected DAO<PrefMst> prefMstDAO;
	
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
	 * 建物情報リクエスト処理<br>
	 * 建物情報のリクエストがあったときに呼び出される。 <br>
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
		List<PrefMst> prefMstList = this.prefMstDAO.selectByFilter(null);
		model.put("prefMstList", prefMstList);
		return new ModelAndView("success", model);
	}

}
