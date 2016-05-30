package jp.co.transcosmos.dm3.webAdmin.housingInfo.command;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.corePana.util.PanaCalcUtil;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;

import org.springframework.web.servlet.ModelAndView;

/**
 * <pre>
 * 坪計算を取得
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
public class PanaCalcUtilCommand implements Command {


	/**
	 * 坪計算の処理<br>
	 * 坪計算のリクエストがあったときに呼び出される。 <br>
	 *
	 * @param request
	 *            クライアントからのHttpリクエスト。
	 * @param response
	 *            クライアントに返すHttpレスポンス。
	 */
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String wkarea = request.getParameter("area");
		String areaCon = "";
		if(!StringValidateUtil.isEmpty(wkarea)){
			try{
				areaCon =String.valueOf(PanaCalcUtil.calcTsubo(BigDecimal.valueOf(Double.valueOf(wkarea))));
			}catch (Exception e){
				areaCon=null;
			}
		}
		Map<String,String> map = null;
	    map = new HashMap<String,String>();
	    map.put("areaCon", areaCon);


		return new ModelAndView("success", "areaCon", map);
	}
}
