package jp.co.transcosmos.dm3.core.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.core.constant.CommonParameters;
import jp.co.transcosmos.dm3.utils.ServletUtils;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * お気に入り用cookie処理.
 * <p>
 * お気に入り件数の取得<br/>
 * お気に入り件数をcookieに設定すること<br/>
 * お気に入り件数プラス１<br/>
 * お気に入り件数マイナス１<br/>
 * <p>
 * <pre>
 * 担当者		修正日		修正内容
 * ------------ ----------- -----------------------------------------------------
 * I.Shu		2015.05.07	新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 * 
 */
public class FavoriteCookieUtils {
	
	/** このユーティリテ の Bean ID */
	protected static String FACTORY_BEAN_ID = "favoriteCookieUtils";
	
	/**
	 * お気に入り件数用 Cookie の Key 名<br/>
	 * Mypage にログイン済の場合、この Key 名で Cookie に書き込む。<br/>
	 * Mypage からログアウトした場合、この Key 名を Cookie から削除する。<br/>
	 */
	public static final String FAVORITE_COUNT_KEY = "favoriteCount";

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
	 * FavoriteCookieUtils のインスタンスを取得する。<br/>
	 * Spring のコンテキストから、 favoriteCookieUtils で定義された FavoriteCookieUtils の
	 * インスタンスを取得する。<br/>
	 * 取得されるインスタンスは、FavoriteCookieUtils を継承した拡張クラスが復帰される場合もある。<br/>
	 * <br/>
	 * @param request HTTP リクエスト
	 * @return favoriteCookieUtils、または継承して拡張したクラスのインスタンス
	 */
	public static FavoriteCookieUtils getInstance(HttpServletRequest request){
		WebApplicationContext springContext = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
		return (FavoriteCookieUtils)springContext.getBean(FavoriteCookieUtils.FACTORY_BEAN_ID);
	}
	
	/**
	 * お気に入り登録件数をcookieに取得する。<br/>
	 * <br/>
	 * @param request HTTP リクエスト
	 */
	public String getFavoriteCount(HttpServletRequest request){
		// お気に入り登録件数をcookieに取得する
		return  ServletUtils.findFirstCookieValueByName(request, FAVORITE_COUNT_KEY);
	}

	/**
	 * お気に入り登録件数をcookieに設定する。<br/>
	 * count件数をcookieに設定する。<br/>
	 * <br/>
	 * @param request HTTP リクエスト
	 * @param response HTTP レスポンス
	 * @param count お気に入り件数
	 */
	public void setFavoriteCount(HttpServletRequest request, HttpServletResponse response, int count){
		// count件数をcookieに設定する
    	ServletUtils.addCookie(response, request.getContextPath(), FAVORITE_COUNT_KEY, 
    			String.valueOf(count), this.commonParameters.getCookieDomain());
	}

	/**
	 * cookie取得するお気に入り件数をプラス1にする。<br/>
	 * <br/>
	 * @param request HTTP リクエスト
	 * @param response HTTP レスポンス
	 */
	public void favoriteCountUp(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// cookieからお気に入り件数を取得する
		String favoriteCount = getFavoriteCount(request);
		int count = 0;
		if (favoriteCount != null) {
			count = Integer.valueOf(favoriteCount);
		}
		// count件数+1をcookieに設定する
		setFavoriteCount(request, response, count + 1);
	}
	
	/**
	 * cookie取得するお気に入り件数をマイナス1にする。<br/>
	 * <br/>
	 * @param request HTTP リクエスト
	 * @param response HTTP レスポンス
	 */
	public void favoriteCountDown(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// cookieからお気に入り件数を取得する
		String favoriteCount = getFavoriteCount(request);
		int count = 0;
		if (favoriteCount != null && Integer.valueOf(favoriteCount) > 0) {
			count = Integer.valueOf(favoriteCount) - 1;
		}
		// count件数-1をcookieに設定する
		setFavoriteCount(request, response, count);
	}
	
	/**
	 * お気に入り件数cookieを削除する。<br/>
	 * <br/>
	 * @param request HTTP リクエスト
	 * @param response HTTP レスポンス
	 */
	public void delFavoriteCount(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// お気に入り件数cookieを削除する
		ServletUtils.delCookie(response, request.getContextPath(),
				FAVORITE_COUNT_KEY, this.commonParameters.getCookieDomain());
	}
	
}
