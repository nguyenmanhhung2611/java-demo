package jp.co.transcosmos.dm3.core.model.news.form;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import jp.co.transcosmos.dm3.core.util.LengthValidationUtils;
import jp.co.transcosmos.dm3.form.FormPopulator;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;

/**
 * <pre>
 * お知らせ検索・一覧用 Form の Factory クラス
 * getInstance() か、Spring からインスタンスを取得して使用する事。
 *
 * 担当者       修正日      修正内容
 * ------------ ----------- -----------------------------------------------------
 *@author hiennt
 *
 * </pre>
 */
public class NewsFormFactory {

	/** Form を生成する Factory の Bean ID */
	protected static String FACTORY_BEAN_ID = "newsFormFactory";
	
	/** 共通コード変換処理 */
	protected CodeLookupManager codeLookupManager;
	
	/** レングスバリデーション用ユーティリティ */
    protected LengthValidationUtils lengthUtils;


	/**
	 * 共通コード変換オブジェクトを設定する。<br/>
	 * <br/>
	 * @param codeLookupManager
	 */
	public void setCodeLookupManager(CodeLookupManager codeLookupManager) {
		this.codeLookupManager = codeLookupManager;
	}
	
	/**
	 * レングスバリデーション用ユーティリティを設定する。<br/>
	 * <br/>
	 * @param lengthUtils レングスバリデーション用ユーティリティ
	 */
	public void setLengthUtils(LengthValidationUtils lengthUtils) {
		this.lengthUtils = lengthUtils;
	}


	
	/**
	 * SearchFormFactory のインスタンスを取得する。<br/>
	 * Spring のコンテキストから、 SearchFormFactory で定義された InformationFormFactory の
	 * インスタンスを取得する。<br/>
	 * 取得されるインスタンスは、SearchFormFactory を継承した拡張クラスが復帰される場合もある。<br/>
	 * <br/>
	 * @param request HTTP リクエスト
	 * @return AdminUserFormFactory、または継承して拡張したクラスのインスタンス
	 */
	public static NewsFormFactory getInstance(HttpServletRequest request) {

		WebApplicationContext springContext = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
		return (NewsFormFactory)springContext.getBean(NewsFormFactory.FACTORY_BEAN_ID);
	}
	
	

	/**
	 * お知らせの検索結果、および検索条件を格納する空の AdminUserSearchForm のインスタンスを生成する。<br/>
	 * <br/>
	 * @return 空の AdminUserSearchForm インスタンス 
	 */
	public NewsSearchForm createInformationSearchForm(){
		return new NewsSearchForm(this.lengthUtils);
	}


	
	/**
	 * お知らせの検索結果、および検索条件を格納する InformationSearchForm のインスタンスを生成する。<br/>
	 * NewsSearchForm には、リクエストパラメータに該当する値を設定する。<br/>
	 * <br/>
	 * @param request HTTP リクエスト
	 * @return リクエストパラメータを設定した InformationSearchForm インスタンス 
	 */
	public NewsSearchForm createInformationSearchForm(HttpServletRequest request){
		NewsSearchForm form = createInformationSearchForm();
		FormPopulator.populateFormBeanFromRequest(request, form);	
		return form; 
	}

	
	
	/**
	 * お知らせ更新時の入力値を格納する空の NewsForm のインスタンスを生成する。<br/>
	 * <br/>
	 * @return 空の NewsForm のインスタンス
	 */
	public NewsForm createInformationForm(){
		return new NewsForm(this.lengthUtils, this.codeLookupManager);
	}

	
	/**
	 * お知らせ更新時の入力値を格納する空の NewsForm のインスタンスを生成する。<br/>
	 * <br/>
	 * @param request HTTP リクエスト
	 * @return リクエストパラメータを設定した NewsForm のインスタンス
	 */
	public NewsForm createInformationForm(HttpServletRequest request){
		NewsForm form = createInformationForm();
		FormPopulator.populateFormBeanFromRequest(request, form);
		return form;
	}
	
}
