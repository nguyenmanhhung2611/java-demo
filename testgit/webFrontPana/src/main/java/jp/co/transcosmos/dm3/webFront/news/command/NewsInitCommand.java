/**
 * 
 */
package jp.co.transcosmos.dm3.webFront.news.command;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.model.HousingRequestManage;
import jp.co.transcosmos.dm3.core.vo.PrefMst;
import jp.co.transcosmos.dm3.core.vo.TblProduct;
import jp.co.transcosmos.dm3.corePana.model.PanaCommonManage;

import org.springframework.web.servlet.ModelAndView;

/**
 * @author hiennt
 *
 */
public class NewsInitCommand implements Command {

	private HousingRequestManage housingRequestManage;
	private PanaCommonManage panaCommonManage;

	public void setPanaCommonManage(PanaCommonManage panaCommonManage) {
		this.panaCommonManage = panaCommonManage;
	}

	public void setHousingRequestManage(HousingRequestManage housingRequestManage) {
		this.housingRequestManage = housingRequestManage;
	}

	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		List<PrefMst> prefMstList = this.panaCommonManage.getPrefMstList();
		//List<TblProduct> productList = this.panaCommonManage.getListProduct();
		model.put("PrefMstList", prefMstList);
		//model.put("ProductList", productList);
		return new ModelAndView("success", model);
	}

}
