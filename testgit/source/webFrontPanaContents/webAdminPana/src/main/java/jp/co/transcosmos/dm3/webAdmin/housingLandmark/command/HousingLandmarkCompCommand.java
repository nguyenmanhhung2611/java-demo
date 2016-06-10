package jp.co.transcosmos.dm3.webAdmin.housingLandmark.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.adminCore.building.command.BuildingLandmarkCompCommand;
import jp.co.transcosmos.dm3.core.model.adminUser.AdminUserInterface;
import jp.co.transcosmos.dm3.core.model.exception.NotFoundException;
import jp.co.transcosmos.dm3.core.model.housing.Housing;
import jp.co.transcosmos.dm3.core.util.AdminLoginUserUtils;
import jp.co.transcosmos.dm3.corePana.model.housing.PanaHousingPartThumbnailProxy;

import org.springframework.web.servlet.ModelAndView;

/**
 * <pre>
 * 地域情報入力画面
 *
 *
 * 【更新登録の場合】
 *     ・リクエストパラメータの受取りのみを行う。
 *
 * 【リクエストパラメータ（command） が "back"の場合】
 *     ・リクエストパラメータで渡された入力値を入力画面に表示する。　（入力確認画面から復帰したケース。）
 *
 * 【復帰する View 名】
 *    ・"success" : 正常終了
 *
 * 担当者       修正日      修正内容
 * ------------ ----------- -----------------------------------------------------
 * liu.yandong		2015.03.17	新規作成
 *
 * 注意事項
 *
 * </pre>
 */
public class HousingLandmarkCompCommand extends BuildingLandmarkCompCommand {

	/** 物件情報用 Model オブジェクト */
	private PanaHousingPartThumbnailProxy panaHousingManager;


	/**
	 * @param panaHousingManager セットする panaHousingManager
	 */
	public void setPanaHousingManager(
			PanaHousingPartThumbnailProxy panaHousingManager) {
		this.panaHousingManager = panaHousingManager;
	}


	/**
	 * 地域情報入力画面表示処理<br>
	 * <br>
	 * @param request HTTP リクエスト
	 * @param response HTTP レスポンス
	*/
    @Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
    	String sysHousingCd = request.getParameter("sysHousingCd");

		// 物件情報を取得する。
		Housing housing = this.panaHousingManager.searchHousingPk(sysHousingCd, true);

		// データの存在しない場合。
		if (housing == null) {
			throw new NotFoundException();
		}
        // model オブジェクトを取得する。
        ModelAndView modelAndView = super.handleRequest(request, response);

		// ログインユーザーの情報を取得する。　（タイムスタンプの更新用）
		AdminUserInterface loginUser = AdminLoginUserUtils.getInstance(request).getLoginUserInfo(request, response);
		// 物件基本情報のタイムスタンプ情報を更新する
        this.panaHousingManager.updateEditTimestamp(sysHousingCd, String.valueOf(loginUser.getUserId()));

		return modelAndView;
	}

}
