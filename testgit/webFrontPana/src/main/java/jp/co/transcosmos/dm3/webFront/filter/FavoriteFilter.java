package jp.co.transcosmos.dm3.webFront.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.core.model.FavoriteManage;
import jp.co.transcosmos.dm3.core.model.information.InformationManageImpl;
import jp.co.transcosmos.dm3.core.util.FavoriteCookieUtils;
import jp.co.transcosmos.dm3.core.util.FrontLoginUserUtils;
import jp.co.transcosmos.dm3.login.LoginUser;
import jp.co.transcosmos.dm3.servlet.BaseFilter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

/**
 * お気に入り件数を設定するフィルター処理<br/>
 * <br/>
 * お気に入り件数のcookieが存在しない場合、DBから件数を取得し、お気に入り件数のcookieを設定する<br/>
 * <br/>
 * <pre>
 *
 * 担当者       修正日      修正内容
 * ------------ ----------- -----------------------------------------------------
 * I.Shu		2015.05.13	新規作成
 *
 * </pre>
 */
public class FavoriteFilter extends BaseFilter {

	private static final Log log = LogFactory.getLog(InformationManageImpl.class);
	
	/** お気に入りメンテナンスを行う Model オブジェクト */
	private FavoriteManage favoriteManager;
	
	/**
	 * お気に入りメンテナンスを行う Model オブジェクトを設定する。<br/>
	 * <br/>
	 * @param favoriteManager お気に入りメンテナンスを行う Model オブジェクト
	 */
	public void setFavoriteManager(FavoriteManage favoriteManager) {
		this.favoriteManager = favoriteManager;
	}
	
	/**
	 * フィルターのメイン処理<br/>
	 * お気に入り件数のcookieが存在しない場合、DBから件数を取得し、お気に入り件数のcookieを設定する<br/>
	 * <br/>
	 * @param request HTTP リクエスト
	 * @param response HTTP レスポンス
	 * @param chain チェーン先オブジェクト
	 * @exception IOException
	 * @exception ServletException
	 */
	@Override
	protected void filterAction(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// お気に入りの登録件数の表示
		String favoriteCnt = null;
		String cookieFavoriteCnt = (FavoriteCookieUtils.getInstance(request).getFavoriteCount(request));
		if (cookieFavoriteCnt == null) {
			// お気に入り件数のcookieが存在しない場合
			// ログイン済みのユーザ情報を取得する
			LoginUser loginUser = FrontLoginUserUtils.getInstance(request).getMypageLoginUserInfo(request, response);
			
			if (loginUser != null) {
				// ログイン済みの場合、ユーザーIDを取得する
				String userId =(String) loginUser.getUserId();
				try {
					favoriteCnt = String.valueOf(favoriteManager.getFavoriteCnt(userId));
				} catch (Exception e) {
					log.warn("Failed To Set favoriteCount");
				}
			}
		}
		if (!StringUtils.isEmpty(favoriteCnt)) {
			// お気に入り件数のcookieを設定する
			FavoriteCookieUtils.getInstance(request).setFavoriteCount(
					request, response, Integer.valueOf(favoriteCnt));
			// お気に入り件数を画面に返却
			request.setAttribute(FavoriteCookieUtils.FAVORITE_COUNT_KEY, favoriteCnt);
		} else {
			request.setAttribute(FavoriteCookieUtils.FAVORITE_COUNT_KEY, cookieFavoriteCnt);
		}

		// お気に入り件数のcookieが存在する場合、何もしない
		// 次の処理へ Chain する。
		chain.doFilter(request, response);
	}
	

}
