package jp.co.transcosmos.dm3.webFront.favorite.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.model.mypage.MypageUserInterface;
import jp.co.transcosmos.dm3.core.util.FrontLoginUserUtils;
import jp.co.transcosmos.dm3.corePana.model.favorite.PanaFavoriteManageImpl;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;

import org.springframework.web.servlet.ModelAndView;

/**
 * <pre>
 * お気に入り物件削除
 *
 *
 * 担当者       修正日     修正内容
 * ------------ ----------- -----------------------------------------------------
 * tan.tianyun   2015.05.06  新規作成
 *
 * 注意事項
 *
 * </pre>
 */
public class FavoriteDeleteCommand implements Command {

	/** お気に入り情報用 Model オブジェクト */
	private PanaFavoriteManageImpl panaFavoriteManager;

	/**
	 * お気に入り情報用 Model オブジェクトを設定する。<br/>
	 * <br/>
	 *
	 * @param PanaFavoriteManageImpl
	 *            お気に入り情報用 Model オブジェクト
	 */
	public void setPanaFavoriteManager(PanaFavoriteManageImpl panaFavoriteManager) {
		this.panaFavoriteManager = panaFavoriteManager;
	}

	/**
	 * お気に入り物件削除<br>
	 * <br>
	 *
	 * @param request
	 *            HTTP リクエスト
	 * @param response
	 *            HTTP レスポンス
	 */
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String sysHousingCd = request.getParameter("sysHousingCd");

		// ログインユーザーの情報を取得
		MypageUserInterface loginUser = FrontLoginUserUtils.getInstance(request).getMypageLoginUserInfo(request, response);

		// ユーザIDを取得
		String userId = String.valueOf(loginUser.getUserId());

		if(StringValidateUtil.isEmpty(sysHousingCd) || StringValidateUtil.isEmpty(userId)) {
			throw new RuntimeException("システム物件CDまたはユーザーIDが指定されていません.");
		}

		// お気に入り物件削除
		this.panaFavoriteManager.delFavorite(userId, sysHousingCd);

		return new ModelAndView("success", "delete", "ok");
	}

}
