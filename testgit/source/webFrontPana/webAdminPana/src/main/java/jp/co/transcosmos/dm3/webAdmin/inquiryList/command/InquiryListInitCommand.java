package jp.co.transcosmos.dm3.webAdmin.inquiryList.command;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.corePana.model.inquiry.form.PanaInquiryFormFactory;
import jp.co.transcosmos.dm3.corePana.model.inquiry.form.PanaInquirySearchForm;

import org.springframework.web.servlet.ModelAndView;

/**
 * <pre>
 * お問合せの検索・一覧
 * 入力された検索条件を元にお問合せ情報を検索し、一覧表示する。
 * 検索条件の入力に問題がある場合、検索処理は行わない。
 *
 * 【復帰する View 名】
 *    ・"success" : 検索処理正常終了
 *    ・"validFail" : バリデーションエラー
 *
 * 担当者       修正日      修正内容
 * ------------ ----------- -----------------------------------------------------
 * Y.Cho		2015.04.07	新規作成
 *
 * 注意事項
 *
 * </pre>
 */
public class InquiryListInitCommand implements Command {

	/**
	 * 物件問合せ情報リクエスト処理<br>
	 * 物件問合せ情報のリクエストがあったときに呼び出される。 <br>
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
    	// リクエストパラメータを取得して Form オブジェクトを作成する。
		PanaInquiryFormFactory factory = PanaInquiryFormFactory.getInstance(request);
		PanaInquirySearchForm searchForm = factory.createPanaInquirySearchForm(request);

        // 都道府県Listの値を view 層へ渡すパラメータとして設定している。
        model.put("searchForm", searchForm);

        return new ModelAndView("success", model);
	}

}
