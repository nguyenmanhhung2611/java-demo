package jp.co.transcosmos.dm3.webAdmin.housingLandmark.command;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.adminCore.building.command.BuildingLandmarkInputCommand;
import jp.co.transcosmos.dm3.core.model.exception.NotFoundException;
import jp.co.transcosmos.dm3.core.model.housing.Housing;
import jp.co.transcosmos.dm3.corePana.model.building.form.PanaBuildingLandmarkForm;
import jp.co.transcosmos.dm3.corePana.model.housing.PanaHousingPartThumbnailProxy;
import jp.co.transcosmos.dm3.corePana.vo.HousingInfo;

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
public class HousingLandmarkInputCommand extends BuildingLandmarkInputCommand {

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
        // model オブジェクトを取得する。
        ModelAndView modelAndView = super.handleRequest(request, response);
        Map<String, Object> model = modelAndView.getModel();

        // Form取得
        PanaBuildingLandmarkForm inputForm = (PanaBuildingLandmarkForm) model.get("inputForm");

		// 物件情報を取得する。
		Housing housing = this.panaHousingManager.searchHousingPk(inputForm.getSysHousingCd(), true);

		// データの存在しない場合。
		if (housing == null) {
			throw new NotFoundException();
		}

        // 物件基本情報を取得する。
        HousingInfo housingInfo = ((HousingInfo) housing.getHousingInfo().getItems().get("housingInfo"));

        // システム物件CD、物件名称を設定
        inputForm.setHousingCd(housingInfo.getHousingCd());
        inputForm.setDisplayHousingName(housingInfo.getDisplayHousingName());

		return modelAndView;
	}

}
