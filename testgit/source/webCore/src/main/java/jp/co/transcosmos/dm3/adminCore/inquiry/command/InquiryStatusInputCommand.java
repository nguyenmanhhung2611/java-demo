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
import jp.co.transcosmos.dm3.core.model.inquiry.form.InquiryStatusForm;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;

import org.springframework.web.servlet.ModelAndView;

/**
 * <pre>
 * お問合せ情報入力画面
 * 
 * ・リクエストパラメータ（検索画面で入力した検索条件、表示ページ位置等）の受取を行う。
 * ・リクエストパラメータ（inquiryId）に該当するお問合せ情報、マイページ会員情報を取得し、画面
 *      表示する。
 *      
 * 【リクエストパラメータ（command） が "back"の場合】
 *     ・リクエストパラメータで渡された入力値を入力画面に表示する。　（入力確認画面から復帰したケース。）
 *
 * 【復帰する View 名】
 *    ・"success" : 正常終了
 *    ・"notFound" : 該当データが存在しない場合
 *     
 * 担当者       修正日      修正内容
 * ------------ ----------- -----------------------------------------------------
 * Y.Cho		2015.04.08	新規作成
 *
 * 注意事項
 *
 * </pre>
 */
public class InquiryStatusInputCommand implements Command {
	
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
	 * お問合せ情報入力画面表示処理<br>
	 * <br>
	 * @param request HTTP リクエスト
	 * @param response HTTP レスポンス
	*/
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// リクエストパラメータを格納した model オブジェクトを生成する。
		// このオブジェクトは View 層への値引渡しに使用される。
        Map<String, Object> model = createModel(request);
        InquiryStatusForm inputForm = (InquiryStatusForm) model.get("inputForm"); 
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

        // modelにお問合せ情報を格納する
        putModel(inquiry, model);
               
		// command パラメータが "back" の場合、入力確認画面からの復帰なので、ＤＢから初期値を取得しない。
		// （リクエストパラメータから取得した値を使用する。）
		String command = inputForm.getCommand();
		if (command != null && "back".equals(command)) {
			
			return new ModelAndView("success", model);
		}
		
        // 取得した値を入力用 Form へ格納する。 （入力値の初期値用）
		inputForm.setDefaultData(inquiry);
		
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

		
		// 入力フォームの受け取りは、command パラメータの値によって異なる。
		// command パラメータが渡されない場合、入力画面の初期表示とみなし、空のフォームを復帰する。
		// command パラメータで "back" が渡された場合、入力確認画面からの復帰を意味する。
		// その場合はリクエストパラメータを受け取り、入力画面へ初期表示する。
		// もし、"back" 以外の値が渡された場合、command パラメータが渡されない場合と同等に扱う。

		InquiryStatusForm inputForm = factory.createInquiryStatusForm(request);
		String command = inputForm.getCommand();
		
		if ("back".equals(command)){
			// 戻るボタンの場合は、リクエストパラメータの値を設定した Form を復帰する。
			model.put("inputForm", inputForm);
		} else {

			// 初期画面表示の場合、空フォームを生成し、デフォルトの入力値を設定する。
			inputForm = factory.createInquiryStatusForm(request);

			model.put("inputForm", inputForm);
		}

		return model;

	}

}
