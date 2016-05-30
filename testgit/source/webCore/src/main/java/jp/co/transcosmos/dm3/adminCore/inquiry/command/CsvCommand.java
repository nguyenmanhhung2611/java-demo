package jp.co.transcosmos.dm3.adminCore.inquiry.command;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import jp.co.transcosmos.dm3.core.model.inquiry.form.InquiryFormFactory;
import jp.co.transcosmos.dm3.core.model.inquiry.form.InquirySearchForm;

/**
 * お問合せ CSV 出力処理.
 * <p>
 * 入力された検索条件を元にお問合せ情報を検索し、CSV 出力する。<br/>
 * お問合せの一覧出力と基本的に同一処理であり、使用する Form オブジェクトを CSV 用に
 * 変更しているのみの違いとなる。<br/>
 * この CSV 用 Form オブジェクトは、ページ処理を行わず、全データを取得対象とする。<br/>
 * <br/>
 * <pre>
 * 担当者		修正日		修正内容
 * ------------ ----------- -----------------------------------------------------
 * Y.Cho		2015.04.10	新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 * 
 */
public class CsvCommand extends InquiryListCommand {

	/**
	 * リクエストパラメータから Form オブジェクトを作成する。<br/>
	 * ※CSV 用の Form を生成する。
	 * <br/>
	 * @param request HTTP リクエストパラメータ
	 * @return パラメータが設定されたフォームオブジェクト
	 */
	@Override
	protected Map<String, Object> createModel(HttpServletRequest request) {

		Map<String, Object> model = new HashMap<String, Object>();
		
		// リクエストパラメータを取得して Form オブジェクトを作成する。
		InquiryFormFactory factory = InquiryFormFactory.getInstance(request);
		InquirySearchForm searchForm = factory.createInquirySearchForm(request);
		
        model.put("searchForm", searchForm);

		return model;
	}

}
