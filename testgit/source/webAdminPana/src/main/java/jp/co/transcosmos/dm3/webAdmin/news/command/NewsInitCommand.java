/**
 * 
 */
package jp.co.transcosmos.dm3.webAdmin.news.command;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.trancosmos.dm3.corePana.model.news.form.NewsFormFactory;
import jp.co.trancosmos.dm3.corePana.model.news.form.NewsRequestForm;
import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.vo.News;
import jp.co.transcosmos.dm3.core.vo.PrefMst;
import jp.co.transcosmos.dm3.core.vo.TblProduct;
import jp.co.transcosmos.dm3.corePana.model.PanaCommonManage;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingFormFactory;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingRequestForm;
import jp.co.transcosmos.dm3.corePana.model.inquiry.form.PanaInquiryFormFactory;
import jp.co.transcosmos.dm3.corePana.model.inquiry.form.PanaInquirySearchForm;

import org.springframework.web.servlet.ModelAndView;

/**
 * @author hiennt
 *
 */
public class NewsInitCommand implements Command {

	
	private PanaCommonManage panaCommonManage;

	public void setPanaCommonManage(PanaCommonManage panaCommonManage) {
		this.panaCommonManage = panaCommonManage;
	}


	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();		
		
		PanaInquiryFormFactory factory = PanaInquiryFormFactory.getInstance(request);
		PanaInquirySearchForm searchForm = factory.createPanaInquirySearchForm(request);

		/*NewsFormFactory factory = NewsFormFactory.getInstance(request);

		// ページ処理用のフォームオブジェクトを作成
		NewsRequestForm newsRequestForm = factory.createNewsRequestForm(request);
		
		
		System.out.println("thanh hien " + newsRequestForm.getNewsId());
		
		System.out.println("enter: ");
		*/
		List<PrefMst> prefMstList = this.panaCommonManage.getPrefMstList();
		List<TblProduct> productList = this.panaCommonManage.getListProduct();
		List<News> newsList = this.panaCommonManage.getListNews();
		
		System.out.println("enter 2: " + prefMstList.size());
		model.put("PrefMstList", prefMstList);
		model.put("ProductList", productList);
		model.put("NewsList", newsList);
		
		//System.out.println("thanh hien " + newsRequestForm.getNewsId());
		return new ModelAndView("success", model);
	}

}
