package jp.co.transcosmos.dm3.webAdmin.memberInfo.command;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import jp.co.transcosmos.dm3.corePana.model.member.form.MemberFormFactory;
import jp.co.transcosmos.dm3.corePana.model.member.form.MemberSearchForm;

/**
 * 会員 CSV 出力処理.
 * <p>
 * 入力された検索条件を元に会員を検索し、CSV 出力する。<br/>
 * 会員の一覧出力と基本的に同一処理であり、使用する Form オブジェクトを CSV 用に
 * 変更しているのみの違いとなる。<br/>
 * この CSV 用 Form オブジェクトは、ページ処理を行わず、全データを取得対象とする。<br/>
 * <br/>
 * <pre>
 * 担当者		  修正日		修正内容
 * -------------- ----------- -----------------------------------------------------
 * tang.tianyun	  2015.04.20	新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 *
 */
public class CsvCommand extends MemberInfoListCommand {

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
		MemberFormFactory factory = MemberFormFactory.getInstance(request);
		MemberSearchForm searchForm = factory.createMypageUserSearchForm(request);

		// ページ内の表示件数を From に設定する。
		searchForm.setRowsPerPage(Integer.MAX_VALUE);
		model.put("searchForm", searchForm);

		return model;
	}

}
