package jp.co.transcosmos.dm3.webFront.filter;

import java.io.IOException;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.StringUtils;

import jp.co.transcosmos.dm3.core.util.FrontLoginUserUtils;
import jp.co.transcosmos.dm3.core.util.RecentlyCookieUtils;
import jp.co.transcosmos.dm3.corePana.vo.RecentlyInfo;
import jp.co.transcosmos.dm3.dao.DAO;
import jp.co.transcosmos.dm3.dao.DAOCriteria;
import jp.co.transcosmos.dm3.servlet.BaseFilter;

/**
 * 最近見た物件の件数を設定するフィルター処理<br/>
 * <br/>
 * 最近見た物件の件数のcookieが存在しない場合、DBから件数を取得し、最近見た物件の件数のcookieを設定する<br/>
 * <br/>
 * <pre>
 *
 * 担当者       修正日      修正内容
 * ------------ ----------- -----------------------------------------------------
 * I.Shu		2015.05.13	新規作成
 * </pre>
 */
public class RecentlyFilter extends BaseFilter {
	
	/** 最近見た物件情報用 DAO */
	private DAO<RecentlyInfo> recentlyInfoDAO;
	
	/**
	 * 最近見た物件情報用 DAOを設定する。<br/>
	 * <br/>
	 * @param recentlyInfoDAO 最近見た物件情報用 DAO
	 */
	public void setRecentlyInfoDAO(DAO<RecentlyInfo> recentlyInfoDAO) {
		this.recentlyInfoDAO = recentlyInfoDAO;
	}

	/**
	 * フィルターのメイン処理<br/>
	 * 最近見た物件の件数のcookieが存在しない場合、DBから件数を取得し、最近見た物件の件数のcookieを設定する<br/>
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
		// 最近見た物件の件数を取得
		String recentlyCnt = null;
		String cookieRecentlyCnt = RecentlyCookieUtils.getInstance(request).getRecentlyCount(request);
		if (cookieRecentlyCnt == null) {
			// 最近見た物件の件数のcookieが存在しない場合
			// ユーザーIDを取得する
			String userId = (String) FrontLoginUserUtils.getInstance(request)
					.getAnonLoginUserInfo(request, response).getUserId();

			// 最近見た物件の件数を取得する
			DAOCriteria criteria = new DAOCriteria();
			criteria.addWhereClause("userId", userId);
			List<RecentlyInfo> recentlyInfos = this.recentlyInfoDAO.selectByFilter(criteria);
			int numRows = 0;
			if (recentlyInfos != null) {
				numRows = recentlyInfos.size();
			}
			recentlyCnt = String.valueOf(numRows);
		}
		
		if (!StringUtils.isEmpty(recentlyCnt)) {
			// 最近見た物件の件数のcookieを設定する
			RecentlyCookieUtils.getInstance(request).setRecentlyCount(request,
					response, Integer.valueOf(recentlyCnt));
			// お気に入り件数を画面に返却
			request.setAttribute(RecentlyCookieUtils.HISTORY_COUNT_KEY, recentlyCnt);
		} else {
			// 最近見た物件の件数を画面に返却
			request.setAttribute(RecentlyCookieUtils.HISTORY_COUNT_KEY, cookieRecentlyCnt);
		}

		// 次の処理へ Chain する。
		chain.doFilter(request, response);
		
	}

}
