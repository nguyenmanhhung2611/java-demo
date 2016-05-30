package jp.co.transcosmos.dm3.corePana.model.information.form;

import javax.servlet.http.HttpServletRequest;

import jp.co.transcosmos.dm3.core.model.information.form.InformationForm;
import jp.co.transcosmos.dm3.core.model.information.form.InformationFormFactory;
import jp.co.transcosmos.dm3.core.model.information.form.InformationSearchForm;
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
public class PanaInformationFormFactory extends InformationFormFactory {

	/**
	 * お知らせの検索結果、および検索条件を格納する空の AdminUserSearchForm のインスタンスを生成する。<br/>
	 * <br/>
	 * @return 空の AdminUserSearchForm インスタンス
	 */
	@Override
	public InformationSearchForm createInformationSearchForm(){
		return new PanaInformationSearchForm(lengthUtils);
	}

	/**
	 * お知らせの検索結果、および検索条件を格納する InformationSearchForm のインスタンスを生成する。<br/>
	 * InformationSearchForm には、リクエストパラメータに該当する値を設定する。<br/>
	 * <br/>
	 * @param request HTTP リクエスト
	 * @return リクエストパラメータを設定した InformationSearchForm インスタンス
	 */
	@Override
	public InformationSearchForm createInformationSearchForm(HttpServletRequest request){
		PanaInformationSearchForm form = (PanaInformationSearchForm)createInformationSearchForm();
		FormPopulator.populateFormBeanFromRequest(request, form);
		return form;
	}

	/**
	 * お知らせ更新時の入力値を格納する空の InformationForm のインスタンスを生成する。<br/>
	 * <br/>
	 * @return 空の InformationForm のインスタンス
	 */
	@Override
	public InformationForm createInformationForm(){
		return new PanaInformationForm(lengthUtils, codeLookupManager);
	}

	/**
	 * お知らせ更新時の入力値を格納する空の InformationForm のインスタンスを生成する。<br/>
	 * <br/>
	 * @param request HTTP リクエスト
	 * @return リクエストパラメータを設定した InformationForm のインスタンス
	 */
	@Override
	public InformationForm createInformationForm(HttpServletRequest request){
		PanaInformationForm form = (PanaInformationForm)createInformationForm();
		FormPopulator.populateFormBeanFromRequest(request, form);
		return form;
	}

}
