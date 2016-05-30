package jp.co.transcosmos.dm3.adminCore.inquiry.command;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.model.InquiryManage;
import jp.co.transcosmos.dm3.core.model.housing.Housing;
import jp.co.transcosmos.dm3.core.model.inquiry.HousingInquiry;
import jp.co.transcosmos.dm3.core.model.inquiry.InquiryInterface;
import jp.co.transcosmos.dm3.core.model.inquiry.form.InquiryFormFactory;
import jp.co.transcosmos.dm3.core.model.inquiry.form.InquirySearchForm;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;

import org.springframework.web.servlet.ModelAndView;

/**
 * お問合せ詳細画面.
 * <p>
 * リクエストパラメータ（inquiryId）で渡された値に該当するお知らせ情報を取得し画面表示する。
 * <br/>
 * 【復帰する View 名】<br/>
 * <ul>
 * <li>success<li>:検索処理正常終了
 * <li>notFound<li>:該当データが存在しない場合
 * </ul>
 * <p>
 * <pre>
 * 担当者		修正日		修正内容
 * ------------ ----------- -----------------------------------------------------
 * Y.Cho		2015.04.10	新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 * 
 */
public class InquiryStatusDetailCommand implements Command  {

	/** お問合せメンテナンスを行う Model オブジェクト */
	protected InquiryManage inquiryManager;
	
	/**
	 * お問合せメンテナンスを行う Model　オブジェクトを設定する。<br/>
	 * <br/>
	 * @param inquiryManager お問合せメンテナンスの model オブジェクト
	 */
	public void setInquiryManager(InquiryManage inquiryManager) {
		this.inquiryManager = inquiryManager;
	}

	/**
	 * お知らせ詳細表示処理<br>
	 * <br>
	 * @param request HTTP リクエスト
	 * @param response HTTP レスポンス
	 * @return ModelAndView　のインスタンス
	*/
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// リクエストパラメータを格納した model オブジェクトを生成する。
		// このオブジェクトは View 層への値引渡しに使用される。
        Map<String, Object> model = createModel(request);
        InquirySearchForm searchForm = (InquirySearchForm) model.get("searchForm");
        
		// 処理対象となる主キー値がパラメータで渡されていない場合、例外をスローする。
		if (StringValidateUtil.isEmpty(searchForm.getInquiryId())){
			throw new RuntimeException ("pk value is null.");
		}
        
        // お問合せ情報を取得する
		InquiryInterface inquiry = searchInquiryPk(searchForm);
        
        // 該当するデータが存在しない場合、該当無し画面を表示する。
        if (inquiry == null) {
        	return new ModelAndView("notFound", model);
        }
        
        // 取得できた場合は model に設定する。
        putModel(inquiry, model);

		return new ModelAndView("success", model);
	}
	
	
	/**
	 * 取得したお問合せ情報をmodel オブジェクトに格納する。<br/>
	 * <br/>
	 * @param model model オブジェクト
	 * @param inquiry お問合せ情報
	 */
	protected void putModel(InquiryInterface inquiry, Map<String, Object> model) {
		 // 問合せヘッダ情報を格納する
		 model.put("inquiryHeaderInfo", inquiry.getInquiryHeaderInfo());
		 
		 // 物件情報を格納する
		 List<Housing> housings =  ((HousingInquiry) inquiry).getHousings();
		 
		 if (housings != null && housings.size() > 0) {
			 model.put("housingInfo", housings.get(0).getHousingInfo());
		 }
	}
	
	
	/**
	 * リクエストパラメータで渡された問合せID （主キー値）に該当する物件問合せ情報を復帰する。<br/>
	 * InquirySearchForm の inquiryId プロパティに設定された値を主キー値として情報を取得する。
	 * もし該当データが見つからない場合、null を復帰する。<br/>
	 * <br/>
	 * @param inquiryId　取得対象お問合せID
	 * 
	 * @return　DB から取得した物件問合せのバリーオブジェクト
	 * 
 	 * @exception Exception 実装クラスによりスローされる任意の例外
	 */
	protected InquiryInterface searchInquiryPk(InquirySearchForm searchForm) throws Exception {
		return this.inquiryManager.searchInquiryPk(searchForm.getInquiryId());
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
        InquiryFormFactory factory = InquiryFormFactory.getInstance(request);

		// 検索条件、および、画面コントロールパラメータを取得する。
        InquirySearchForm searchForm = factory.createInquirySearchForm(request);
		model.put("searchForm", searchForm);

		return model;
	}

}
