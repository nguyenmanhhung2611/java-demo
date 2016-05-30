package jp.co.trancosmos.dm3.corePana.model.news.form;

import javax.servlet.http.HttpServletRequest;

import jp.co.transcosmos.dm3.core.model.news.form.NewsForm;
import jp.co.transcosmos.dm3.core.model.news.form.NewsFormFactory;
import jp.co.transcosmos.dm3.core.model.news.form.NewsSearchForm;
import jp.co.transcosmos.dm3.form.FormPopulator;

/**
 * <pre>
 * お知らせ検索・一覧用 Form の Factory クラス
 * getInstance() か、Spring からインスタンスを取得して使用する事。
 *
 * 担当者       修正日      修正内容
 * ------------ ----------- -----------------------------------------------------
 * zhang		2015.04.21	新規作成
 *
 * 注意事項
 * Factory のインスタンスを直接生成しない事。　必ずgetInstance() で取得する事。
 *
 * </pre>
 */
public class PanaNewsFormFactory extends NewsFormFactory {

	
	/**
	 * お知らせの検索結果、および検索条件を格納する空の AdminUserSearchForm のインスタンスを生成する。<br/>
	 * <br/>
	 * @return 空の AdminUserSearchForm インスタンス
	 */
	public NewsSearchForm createInformationSearchForm(){
		return new PanaNewsSearchForm(lengthUtils);
	}

	/**
	 * お知らせの検索結果、および検索条件を格納する InformationSearchForm のインスタンスを生成する。<br/>
	 * InformationSearchForm には、リクエストパラメータに該当する値を設定する。<br/>
	 * <br/>
	 * @param request HTTP リクエスト
	 * @return リクエストパラメータを設定した InformationSearchForm インスタンス
	 */
	public NewsSearchForm createInformationSearchForm(HttpServletRequest request){
		PanaNewsSearchForm form = (PanaNewsSearchForm)createInformationSearchForm();
		FormPopulator.populateFormBeanFromRequest(request, form);
		return form;
	}

	/**
	 * お知らせ更新時の入力値を格納する空の InformationForm のインスタンスを生成する。<br/>
	 * <br/>
	 * @return 空の InformationForm のインスタンス
	 */
	public NewsForm createNewsForm(){
		return new PanaNewsForm(lengthUtils, codeLookupManager);
	}

	/**
	 * お知らせ更新時の入力値を格納する空の InformationForm のインスタンスを生成する。<br/>
	 * <br/>
	 * @param request HTTP リクエスト
	 * @return リクエストパラメータを設定した InformationForm のインスタンス
	 */
	
	
	public NewsForm createNewsForm(HttpServletRequest request){
		PanaNewsForm form = (PanaNewsForm)createNewsForm();
		FormPopulator.populateFormBeanFromRequest(request, form);
		return form;
	}

}
