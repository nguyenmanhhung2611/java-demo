package jp.co.transcosmos.dm3.webAdmin.housingImageInfo.command;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.model.exception.NotFoundException;
import jp.co.transcosmos.dm3.core.model.housing.Housing;
import jp.co.transcosmos.dm3.core.vo.HousingInfo;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonConstant;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonParameters;
import jp.co.transcosmos.dm3.corePana.model.housing.PanaHousingPartThumbnailProxy;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingFormFactory;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingImageInfoForm;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingSearchForm;
import jp.co.transcosmos.dm3.corePana.util.PanaFileUtil;
import jp.co.transcosmos.dm3.dao.JoinResult;

import org.springframework.web.servlet.ModelAndView;

/**
 * 物件画像情報検索画面.
 * <p>
 * 入力された検索条件を元に物件画像情報を検索し、一覧表示する。<br/>
 * 検索条件の入力に問題がある場合、検索処理は行わない。<br/>
 * <br/>
 * 【復帰する View 名】<br/>
 * <ul>
 * <li>success
 * <li>:検索処理正常終了
 * <li>validFail
 * <li>:バリデーションエラー
 * </ul>
 * <p>
 *
 * <pre>
 * 担当者       修正日      修正内容
 * ------------ ----------- -----------------------------------------------------
 * fan	        2015.04.10  新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 *
 */
public class HousingImageInfoInputCommand implements Command {


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

	/** 共通パラメータオブジェクト */
	private PanaCommonParameters commonParameters;

	/**
	 * 共通パラメータオブジェクトを設定する。<br/>
	 * <br/>
	 *
	 * @param commonParameters
	 *            共通パラメータオブジェクト
	 */
	public void setCommonParameters(PanaCommonParameters commonParameters) {
		this.commonParameters = commonParameters;
	}

	/** Housingメンテナンスを行う Model オブジェクト */
	private PanaHousingPartThumbnailProxy panaHousingManager;

	/**
	 * Housingメンテナンスを行う Model　オブジェクトを設定する。<br/>
	 * <br/>
	 *
	 * @param panaHousingManager
	 *            Housingメンテナンスの model オブジェクト
	 */
	public void setPanaHousingManager(
			PanaHousingPartThumbnailProxy panaHousingManager) {
		this.panaHousingManager = panaHousingManager;
	}

	/**
	 * 物件画像情報検索リクエスト処理<br>
	 * 物件画像情報検索のリクエストがあったときに呼び出される。 <br>
	 *
	 * @param request
	 *            クライアントからのHttpリクエスト。
	 * @param response
	 *            クライアントに返すHttpレスポンス。
	 */
	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Map<String, Object> model = new HashMap<String, Object>();

		// リクエストパラメータを取得して Form オブジェクトを作成する。
		PanaHousingFormFactory factory = PanaHousingFormFactory.getInstance(request);

		// リクエストパラメータを格納した form オブジェクトを生成する。
		PanaHousingImageInfoForm form = factory.createPanaHousingImageInfoForm(request);
		PanaHousingSearchForm searchForm = factory.createPanaHousingSearchForm(request);
		model.put("searchForm", searchForm);

		// 事前チェック:物件基本情報テーブルの読込
		Housing housingresults = this.panaHousingManager.searchHousingPk(
				form.getSysHousingCd(), true);

		if (housingresults == null) {
			// データの存在しない場合,メッセージ："｛0｝物件情報が存在しない"表示
			throw new NotFoundException();
		}

		List<jp.co.transcosmos.dm3.core.vo.HousingImageInfo> housingImageInfoList = housingresults
				.getHousingImageInfos();

		// リフォーム詳細情報入力確認画面の「戻る」ボタンから返す場合
		if ("back".equals(form.getCommand())) {
			// 画面表示用アップロード画像編集情報の再設定
			model.put("housingImageInfoForm", form);
			// 取得したデータをレンダリング層へ渡す
			return new ModelAndView("success", model);
		}

		JoinResult housingInfo = housingresults.getHousingInfo();
		if (housingInfo != null) {
			// 物件名称
			form.setDisplayHousingName(((HousingInfo) housingInfo.getItems()
					.get("housingInfo")).getDisplayHousingName());
			// 物件番号
			form.setHousingCd(((HousingInfo) housingInfo.getItems().get(
					"housingInfo")).getHousingCd());
		}

		form.setDefaultData(housingImageInfoList);

		setUrlList(housingImageInfoList, model, form);

		model.put("housingImageInfoForm", form);

		// 取得したデータをレンダリング層へ渡す
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
	private void setUrlList(
			List<jp.co.transcosmos.dm3.core.vo.HousingImageInfo> housingImageInfoList,
			Map<String, Object> model, PanaHousingImageInfoForm form) {

		String[] hidPathMax;
		String[] hidPathMin;
		if (null != form.getDivNo()) {
			hidPathMax = new String[form.getDivNo().length];
			hidPathMin = new String[form.getDivNo().length];
		} else {
			hidPathMax = new String[0];
			hidPathMin = new String[0];
		}

		int count = 0;
		for (jp.co.transcosmos.dm3.core.vo.HousingImageInfo coreHi : housingImageInfoList) {
			jp.co.transcosmos.dm3.corePana.vo.HousingImageInfo hi = (jp.co.transcosmos.dm3.corePana.vo.HousingImageInfo) coreHi;
			String urlPathMin = "";
			String urlPathMax = "";
			// 閲覧権限が会員のみの場合
			if (PanaCommonConstant.ROLE_ID_PRIVATE.equals(hi.getRoleId())) {
				urlPathMin = fileUtil.getHousFileMemberUrl(hi.getPathName(),hi.getFileName(),this.commonParameters.getThumbnailSizes().get(0).toString());
				urlPathMax = fileUtil.getHousFileMemberUrl(hi.getPathName(),hi.getFileName(),this.commonParameters.getThumbnailSizes().get(this.commonParameters.getThumbnailSizes().size() - 1).toString());
			} else {
				// 閲覧権限が全員の場合
				urlPathMin = fileUtil.getHousFileOpenUrl(hi.getPathName(),hi.getFileName(),this.commonParameters.getThumbnailSizes().get(0).toString());
				urlPathMax = fileUtil.getHousFileOpenUrl(hi.getPathName(),hi.getFileName(),this.commonParameters.getThumbnailSizes().get(this.commonParameters.getThumbnailSizes().size() - 1).toString());
			}

			hidPathMin[count] = urlPathMin;

			hidPathMax[count] = urlPathMax;

			count++;
		}
		form.setHidPathMax(hidPathMax);
		form.setHidPathMin(hidPathMin);
	}
}
