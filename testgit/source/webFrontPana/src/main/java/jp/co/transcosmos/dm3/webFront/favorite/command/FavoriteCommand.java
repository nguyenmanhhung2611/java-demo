package jp.co.transcosmos.dm3.webFront.favorite.command;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.model.mypage.MypageUserInterface;
import jp.co.transcosmos.dm3.core.util.FavoriteCookieUtils;
import jp.co.transcosmos.dm3.core.util.FrontLoginUserUtils;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonConstant;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonParameters;
import jp.co.transcosmos.dm3.corePana.model.favorite.PanaFavoriteManageImpl;
import jp.co.transcosmos.dm3.corePana.vo.HousingStatusInfo;
import jp.co.transcosmos.dm3.dao.DAO;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;

import org.springframework.web.servlet.ModelAndView;

/**
 * <pre>
 * お気に入り登録
 * お気に入り登録画面を表示する。
 *
 * 【復帰する View 名】
 *    ・"success" : 正常終了
 *
 * 担当者       修正日     修正内容
 * ------------ ----------- -----------------------------------------------------
 * gao.long     2015.04.24  新規作成
 *
 * 注意事項
 *
 * </pre>
 */
public class FavoriteCommand implements Command {

	/** 物件情報用 Model オブジェクト */
	private PanaFavoriteManageImpl panaFavoriteManager;

	/** 物件ステータス情報用DAO **/
	private DAO<HousingStatusInfo> housingStatusInfoDAO;

	/** 共通コード変換処理 */
	private CodeLookupManager codeLookupManager;

	/** 共通パラメータオブジェクト */
	private PanaCommonParameters commonParameters;

	/**
	 * 物件情報用 Model オブジェクトを設定する。<br/>
	 * <br/>
	 *
	 * @param PanaFavoriteManageImpl
	 *            物件情報用 Model オブジェクト
	 */
	public void setPanaFavoriteManager(PanaFavoriteManageImpl panaFavoriteManager) {
		this.panaFavoriteManager = panaFavoriteManager;
	}

	/**
	 * 物件ステータス情報用DAO を設定する。<br/>
	 * <br/>
	 * @param housingInfoDAO 物件ステータス情報用 DAO
	 */
	public void setHousingStatusInfoDAO(DAO<HousingStatusInfo> housingStatusInfoDAO) {
		this.housingStatusInfoDAO = housingStatusInfoDAO;
	}

	/**
	 * 共通コード変換オブジェクトを設定する。<br/>
	 * <br/>
	 * @param codeLookupManager
	 */
	public void setCodeLookupManager(CodeLookupManager codeLookupManager) {
		this.codeLookupManager = codeLookupManager;
	}

	/**
	 * 共通パラメータオブジェクトを設定する。<br/>
	 * <br/>
	 * @param commonParameters 共通パラメータオブジェクト
	 */
	public void setCommonParameters(PanaCommonParameters commonParameters) {
		this.commonParameters = commonParameters;
	}

	/**
	 * 物件一覧画面表示処理<br>
	 * <br>
	 *
	 * @param request
	 *            HTTP リクエスト
	 * @param response
	 *            HTTP レスポンス
	 */
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// ログインユーザーの情報を取得
		MypageUserInterface loginUser = FrontLoginUserUtils.getInstance(request).getMypageLoginUserInfo(request, response);

		// ユーザIDを取得
		String userId = null;

		// 会員フラグ
		boolean memberFlg = false;

		if (loginUser != null) {
			// ユーザID
			userId = (String) loginUser.getUserId();

			// 会員フラグ
			memberFlg = true;
		}

		// 物件番号を取得
		String sysHousingCd = request.getParameter("sysHousingCd");

		// 物件ステータス情報
		HousingStatusInfo housingStatusInfo = housingStatusInfoDAO.selectByPK(sysHousingCd);

		// 非公開かどうか判断する。
		boolean hiddenFlg = housingStatusInfo != null && PanaCommonConstant.HIDDEN_FLG_PUBLIC.equals(housingStatusInfo.getHiddenFlg());

		// メッセージ
		String messageId = null;

		// 会員かつ公開の場合
		if (memberFlg && hiddenFlg) {

			// お気に入り物件テーブルから件数を取得する。
			int favoriteInfoCnt = this.panaFavoriteManager.getFavoriteInfoCnt(userId);

			// 選択した物件をお気に入り情報テーブルに登録する。
			messageId = this.panaFavoriteManager.addPanaFavorite(userId, sysHousingCd);

			if ("0".equals(messageId)) {
				// 登録成功、お気に入りの件数を保持するCookieの値 + 1
				FavoriteCookieUtils.getInstance(request).setFavoriteCount(request, response, favoriteInfoCnt + 1);
			}

		} else {

			// メッセージ
			messageId = "3";
		}

		// 取得したデータをレンダリング層へ渡す
		Map<String, String> map = new HashMap<String, String>();

		map.put("alertMessage", getMessage(messageId));

		return new ModelAndView("success", map);
	}

	/**
	 * エラーメッセージを取得する。<br/>
	 * <br/>
	 * @param messageId メッセージID
	 * @return メッセージ
	 */
	private String getMessage(String messageId) {
		String key = null;
		switch (messageId) {
		case "0":
			key = "favoriteAddSuccess";
			break;
		case "1":
			key = "favoriteAddOver";
			break;
		case "2":
			key = "favoriteAddAlready";
			break;
		default:
			key = "favoriteAddError";
			break;
		}
		return this.codeLookupManager.lookupValue("errorTemplates", key).replace("[#fieldName]", String.valueOf(this.commonParameters.getMaxFavoriteInfoCnt()));
	}
}
