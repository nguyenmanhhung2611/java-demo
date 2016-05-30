package jp.co.transcosmos.dm3.corePana.model.inquiry.dao;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.core.model.InquiryManage;
import jp.co.transcosmos.dm3.corePana.model.inquiry.form.PanaInquirySearchForm;
import jp.co.transcosmos.dm3.dao.JoinResult;

/**
 * 物件問合せ model の実装クラス.
 * <p>
 * <pre>
 * 担当者       修正日      修正内容
 * ------------ ----------- -----------------------------------------------------
 * 郭中レイ		2015.04.02	新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 *
 */
public interface HousingInquiryDAO {
	/**
	 * 問合一覧情報を検索し、結果リストを復帰する。（一覧用）<br/>
	 * 引数で渡された Form パラメータの値で検索条件を生成し、物件情報を検索する。<br/>
	 * 検索結果は List<Inquiry> オブジェクトに格納され、取得したList<Inquiry>を戻り値として復帰する。<br/>
	 * <br/>
	 *
	 * @param searchForm
	 *            検索条件の格納オブジェクト
	 *
	 * @exception Exception
	 *                実装クラスによりスローされる任意の例外
	 */
	public List<JoinResult> housingInquirySearch(PanaInquirySearchForm searchForm);

	/**
	 * 問合一覧情報を検索し、結果リストを復帰する。（一覧用）<br/>
	 * 引数で渡された Form パラメータの値で検索条件を生成し、物件情報を検索する。<br/>
	 * 検索結果は List<Housing> オブジェクトに格納され、取得したList<Housing>を戻り値として復帰する。<br/>
	 * <br/>
	 *
	 * @param searchForm
	 *            検索条件の格納オブジェクト
	 * @param response
	 *            クライアントに返すHttpレスポンス。
	 * @param inquiryManage
	 *            物件問合せ model
	 * @param generalInquiryManage
	 *            汎用問合せ model
	 * @param assessmentInquiryManage
	 *            査定問合せ model
	 * @throws IOException
	 *
	 * @exception Exception
	 *                実装クラスによりスローされる任意の例外
	 */
	public void housingInquirySearch(PanaInquirySearchForm searchForm,
			HttpServletResponse response,InquiryManage inquiryManage, InquiryManage generalInquiryManage,InquiryManage assessmentInquiryManage) throws IOException;

}
