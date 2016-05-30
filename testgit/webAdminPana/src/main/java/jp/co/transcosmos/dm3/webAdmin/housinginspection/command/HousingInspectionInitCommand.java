package jp.co.transcosmos.dm3.webAdmin.housinginspection.command;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.model.exception.NotFoundException;
import jp.co.transcosmos.dm3.core.model.housing.Housing;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonParameters;
import jp.co.transcosmos.dm3.corePana.model.housing.PanaHousingPartThumbnailProxy;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingFormFactory;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingInspectionForm;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingSearchForm;
import jp.co.transcosmos.dm3.corePana.util.PanaFileUtil;
import jp.co.transcosmos.dm3.corePana.vo.HousingInspection;
import jp.co.transcosmos.dm3.dao.JoinResult;

import org.springframework.web.servlet.ModelAndView;

/**
 * <pre>
 * 住宅診断情報入力確認画面
 * リクエストパラメータで渡されたリフォーム情報のバリデーションを行い、確認画面を表示する。
 * もしバリデーションエラーが発生した場合は入力画面として表示する。
 *
 * 【復帰する View 名】
 *    ・"success" : 正常終了
 *    ・"input" : バリデーションエラーによる再入力
 *
 * 担当者       修正日      修正内容
 * ------------ ----------- -----------------------------------------------------
 * fan			2015.04.21	新規作成
 *
 * 注意事項
 *
 * </pre>
 */
public class HousingInspectionInitCommand implements Command {

	/** Panasonic用ファイル処理関連共通Util */
	private PanaFileUtil fileUtil;

	/**
	 * Panasonic用ファイル処理関連共通Utilを設定する。<br/>
	 * <br/>
	 *
	 * @param fileUtil
	 *            Panasonic用ファイル処理関連共通Util
	 */
	public void setFileUtil(PanaFileUtil fileUtil) {
		this.fileUtil = fileUtil;
	}
	/** 住宅診断情報メンテナンスを行う Model オブジェクト */
	private PanaHousingPartThumbnailProxy panaHousingManager;

	/**
	 * 住宅診断情報メンテナンスを行う Model　オブジェクトを設定する。<br/>
	 * <br/>
	 *
	 * @param panaHousingManager
	 *            住宅診断情報メンテナンスの model オブジェクト
	 */
	public void setPanaHousingManager(PanaHousingPartThumbnailProxy panaHousingManager) {
		this.panaHousingManager = panaHousingManager;
	}
	/** 共通パラメータオブジェクト */
    private PanaCommonParameters commonParameters;

    /**
     * 共通パラメータオブジェクトを設定する。<br/>
     * <br/>
     * @param commonParameters 共通パラメータオブジェクト
     */
    public void setCommonParameters(PanaCommonParameters commonParameters) {
        this.commonParameters = commonParameters;
    }

	/**
	 * 住宅診断情報画面表示処理<br>
	 * <br>
	 *
	 * @param request
	 *            HTTP リクエスト
	 * @param response
	 *            HTTP レスポンス
	 */
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// リクエストパラメータを取得して Form オブジェクトを作成する。
		PanaHousingFormFactory factory = PanaHousingFormFactory.getInstance(request);
		// リクエストパラメータを格納した form オブジェクトを生成する。
		PanaHousingInspectionForm form = factory.createPanaHousingInspectionForm(request);
		PanaHousingSearchForm searchForm = factory.createPanaHousingSearchForm(request);

		// 結果用のmodel
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("searchForm", searchForm);

		// 事前チェック
		Housing housingresults = this.panaHousingManager.searchHousingPk(form.getSysHousingCd(),true);
		if (housingresults == null) {
			throw new NotFoundException();
		}
		JoinResult housingInfo = housingresults.getHousingInfo();
        JoinResult buildingInfo = housingresults.getBuilding().getBuildingInfo();

		// 住宅診断実施有無の物件拡張属性情報の取得
		Map<String,String> housingExtInfo = housingresults.getHousingExtInfos().get("housingInspection");

		// 戻るのコメントの判断
		if ("back".equals(form.getCommand())) {
			model.put("HousingInspectionForm", form);
			return new ModelAndView("success", model);
		}

		// 診断結果の選択項目の定義hashtableを取得
		List<HousingInspection> HousingInspectionList = this.panaHousingManager.searchHousingInspection(form.getSysHousingCd());
		// 物件インスペクション情報の取得

		form.setDefaultData(housingInfo,buildingInfo,HousingInspectionList,housingExtInfo);

		setUrlList(model, form);
		model.put("HousingInspectionForm", form);

		return new ModelAndView("success", model);

	}

	 /**
     * 画像表示用を設定する処理。 <br>
     *
     * @param housingImageInfoList
     * @param model
     *
     * @return
     */
    private void setUrlList(Map<String, Object> model,PanaHousingInspectionForm form) {

		 String urlPathImg = "";
         String urlPathPdf = "";
         // 閲覧権限が会員のみの場合

         urlPathImg = fileUtil.getHousFileMemberUrl(form.getImgFilePath(),form.getImgFile(),this.commonParameters.getAdminSiteChartFolder());
         urlPathPdf = fileUtil.getHousFileMemberUrl(form.getLoadFilePath(),form.getLoadFile(),this.commonParameters.getAdminSitePdfFolder());

         form.setHidImgPath(urlPathImg);
         form.setHidPath(urlPathPdf);

    }
}