package jp.co.transcosmos.dm3.core.command;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.vo.AddressMst;
import jp.co.transcosmos.dm3.dao.DAO;
import jp.co.transcosmos.dm3.dao.DAOCriteria;

import org.springframework.web.servlet.ModelAndView;

/**
 * <pre>
 * 市区町村の検索
 * 市区町村情報を取得
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
public class AddressMstListCommand implements Command {
	
	/** 市区町村マスタ取得用 DAO */
	protected DAO<AddressMst> addressMstDAO;
	
	/** エリア非表示を除外する場合、1に設定する　（デフォルト 0） */
	protected String areaNotDsp = "0";
	
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
	 * を設定する。<br/>
	 * <br/>
	 * @param areaNotDsp 
	 */
	public void setAreaNotDsp(String areaNotDsp) {
		this.areaNotDsp = areaNotDsp;
	}
	
	/**
	 * 市区町村の検索処理<br>
	 * 市区町村情報のリクエストがあったときに呼び出される。 <br>
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
        
		// 市区町村マスタを取得する
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("prefCd", prefCd);
		if ("1".equals(this.areaNotDsp)) {
			criteria.addWhereClause("areNotDsp", this.areaNotDsp);
		}
		List<AddressMst> addressMstList = this.addressMstDAO.selectByFilter(criteria);
				
		return new ModelAndView("success", "addressMstList", addressMstList);
	}


}
