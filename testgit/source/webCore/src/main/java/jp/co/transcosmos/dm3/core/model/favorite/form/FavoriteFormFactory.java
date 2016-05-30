package jp.co.transcosmos.dm3.core.model.favorite.form;

import javax.servlet.http.HttpServletRequest;

import jp.co.transcosmos.dm3.form.FormPopulator;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * <pre>
 * お気に入り情報検索用 Form の Factory クラス
 * getInstance() か、Spring からインスタンスを取得して使用する事。
 * 
 * 担当者       修正日      修正内容
 * ------------ ----------- -----------------------------------------------------
 * T.Nakamura	2015.03.23	新規作成
 * 
 * 注意事項
 * Factory のインスタンスを直接生成しない事。　必ずgetInstance() で取得する事。
 * 
 * </pre>
 */
public class FavoriteFormFactory {

	/** Form を生成する Factory の Bean ID */
	protected static String FACTORY_BEAN_ID = "favoriteFormFactory";

	/**
	 * FavoriteFormFactory のインスタンスを取得する。<br/>
	 * Spring のコンテキストから、 favoriteFormFactory で定義された FavoriteFormFactory の
	 * インスタンスを取得する。<br/>
	 * 取得されるインスタンスは、FavoriteFormFactory を継承した拡張クラスが復帰される場合もある。<br/>
	 * <br/>
	 * 
	 * @param request HTTP リクエスト
	 * @return FavoriteFormFactory、または継承して拡張したクラスのインスタンス
	 */
	public static FavoriteFormFactory getInstance(HttpServletRequest request) {
		WebApplicationContext springContext = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
		return (FavoriteFormFactory) springContext.getBean(FavoriteFormFactory.FACTORY_BEAN_ID);
	}

	/**
	 * お気に入り情報の検索条件を格納する空の FavoriteSearchForm のインスタンスを生成する。<br/>
	 * <br/>
	 * 
	 * @return 空の FavoriteSearchForm インスタンス
	 */
	public FavoriteSearchForm createFavoriteSearchForm() {
		return new FavoriteSearchForm();
	}

	/**
	 * お気に入り情報の検索条件を格納した FavoriteSearchForm のインスタンスを生成する。<br/>
	 * FavoriteSearchForm には、リクエストパラメータに該当する値を設定する。<br/>
	 * <br/>
	 * 
	 * @param request HTTP リクエスト
	 * @return リクエストパラメータを設定した FavoriteSearchForm インスタンス
	 */
	public FavoriteSearchForm createFavoriteSearchForm(HttpServletRequest request) {
		FavoriteSearchForm form = createFavoriteSearchForm();
		FormPopulator.populateFormBeanFromRequest(request, form);
		return form;
	}

}
