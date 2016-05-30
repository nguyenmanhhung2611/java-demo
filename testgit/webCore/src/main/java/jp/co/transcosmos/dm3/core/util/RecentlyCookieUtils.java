package jp.co.transcosmos.dm3.core.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.core.constant.CommonParameters;
import jp.co.transcosmos.dm3.utils.ServletUtils;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * 最近見た物件用cookie処理.
 * <p>
 * 最近見た物件のシステム物件IDの取得<br/>
 * 最近見た物件のシステム物件IDを設定する<br/>
 * 最近見た物件の件数プラス１<br/>
 * 最近見た物件の件数を設定する<br/>
 * <p>
 * <pre>
 * 担当者		修正日		修正内容
 * ------------ ----------- -----------------------------------------------------
 * I.Shu		2015.05.08	新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 * 
 */
public class RecentlyCookieUtils {

	/** このユーティリテ の Bean ID */
	protected static String FACTORY_BEAN_ID = "recentlyCookieUtils";
	
	/**
	 * 最近見た物件の件数保持用 Cookie の Key 名<br/>
	 */
	public static final String HISTORY_COUNT_KEY = "historyCount";
	
	/** 共通パラメータオブジェクト */
	protected CommonParameters commonParameters;
	
	/**
	 * 共通パラメータオブジェクトを設定する。<br/>
	 * <br/>
	 * @param commonParameters
	 */
	public void setCommonParameters(CommonParameters commonParameters) {
		this.commonParameters = commonParameters;
	}
	
	/**
	 * RecentlyCookieUtils のインスタンスを取得する。<br/>
	 * Spring のコンテキストから、 recentlyCookieUtils で定義された RecentlyCookieUtils の
	 * インスタンスを取得する。<br/>
	 * 取得されるインスタンスは、RecentlyCookieUtils を継承した拡張クラスが復帰される場合もある。<br/>
	 * <br/>
	 * @param request HTTP リクエスト
	 * @return recentlyCookieUtils、または継承して拡張したクラスのインスタンス
	 */
	public static RecentlyCookieUtils getInstance(HttpServletRequest request){
		WebApplicationContext springContext = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
		return (RecentlyCookieUtils)springContext.getBean(RecentlyCookieUtils.FACTORY_BEAN_ID);
	}
	
	/**
	 * 最近見た物件の件数をcookieに設定する。<br/>
	 * count件数をcookieに設定する。<br/>
	 * <br/>
	 * @param request HTTP リクエスト
	 * @param response HTTP レスポンス
	 * @param count お気に入り件数
	 */
	public void setRecentlyCount(HttpServletRequest request, HttpServletResponse response, int count){
		// count件数をcookieに設定する
    	ServletUtils.addCookie(response, request.getContextPath(), HISTORY_COUNT_KEY, 
    			String.valueOf(count), this.commonParameters.getCookieDomain());
	}
	
	/**
	 * 最近見た物件の件数をcookieから取得する。<br/>
	 * count件数を取得する。<br/>
	 * <br/>
	 * @param request HTTP リクエスト
	 */
	public String getRecentlyCount(HttpServletRequest request){
		// count件数を取得する
		return  ServletUtils.findFirstCookieValueByName(request, HISTORY_COUNT_KEY);
	}
	
	/**
	 * 最近見た物件の件数cookieを削除する。<br/>
	 * <br/>
	 * @param request HTTP リクエスト
	 * @param response HTTP レスポンス
	 */
	public void delFavoriteCount(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 最近見た物件の件数cookieを削除する
		ServletUtils.delCookie(response, request.getContextPath(),
				HISTORY_COUNT_KEY, this.commonParameters.getCookieDomain());
	}
	
}
