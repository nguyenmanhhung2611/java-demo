package jp.co.transcosmos.dm3.webFront.housingListMation.command;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.corePana.model.housing.PanaHousingPartThumbnailProxy;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingFormFactory;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingSearchForm;
import jp.co.transcosmos.dm3.form.FormPopulator;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;

import org.springframework.web.servlet.ModelAndView;

import com.mysql.jdbc.StringUtils;

/**
 * 物件一覧画面
 *
 * <pre>
 * 【復帰する View 名】
 *    ・"success" : 正常終了
 *
 * 担当者       修正日      修正内容
 * ------------ ----------- -----------------------------------------------------
 *   焦		  2015.04.10    新規作成
 *
 * 注意事項
 *
 * </pre>
 */
public class HousingListMatinoSearchCommand implements Command {

	/** 物件情報用 Model オブジェクト */
	private PanaHousingPartThumbnailProxy panaHousingManager;

	/** 共通コード変換処理 */
	protected CodeLookupManager codeLookupManager;


	/** 物件種類 */
	private String housingKindCd = "";

	/**
	 * 物件種類を設定する。<br>
	 *
	 * @param housingKindCd
	 *            物件種類
	 */
	public void setHousingKindCd(String housingKindCd) {
		this.housingKindCd = housingKindCd;
	}

	/**
	 * 共通コード変換オブジェクトを設定する。<br/>
	 * <br/>
	 *
	 * @param codeLookupManager
	 */
	public void setCodeLookupManager(CodeLookupManager codeLookupManager) {
		this.codeLookupManager = codeLookupManager;
	}

	/**
	 * 物件情報用 Model オブジェクトを設定する。<br/>
	 * <br/>
	 *
	 * @param PanaHousingManage
	 *            物件情報用 Model オブジェクト
	 */
	public void setPanaHousingManager(
			PanaHousingPartThumbnailProxy panaHousingManager) {
		this.panaHousingManager = panaHousingManager;
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
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		PanaHousingFormFactory factory = PanaHousingFormFactory
				.getInstance(request);

		// ページ処理用のフォームオブジェクトを作成
		PanaHousingSearchForm housingListMationForm = factory
				.createPanaHousingSearchForm();

		FormPopulator.populateFormBeanFromRequest(request,
				housingListMationForm);

		// 予算下限
		String priceLower = request.getParameter("priceLower");
		housingListMationForm.setPriceLower(priceLower);
		// 予算上限
		String priceUpper = request.getParameter("priceUpper");
		// 「リフォーム価格込みで検索する」のチェック状態
		String reformPriceCheck = request.getParameter("reformPriceCheck");
		housingListMationForm.setReformPriceCheck(reformPriceCheck);
		// 専有面積下限
		String personalAreaLower = request.getParameter("personalAreaLower");
		// 専有面積上限
		String personalAreaUpper = request.getParameter("personalAreaUpper");
		// 建物面積下限
		String buildingAreaLower = request.getParameter("buildingAreaLower");
		// 建物面積上限
		String buildingAreaUpper = request.getParameter("buildingAreaUpper");
		// 土地面積下限
		String landAreaLower = request.getParameter("landAreaLower");
		// 土地面積上限
		String landAreaUpper = request.getParameter("landAreaUpper");
		// 間取り
		String layoutCd = request.getParameter("layoutCd");
		// 築年月
		String keyCompDate = request.getParameter("keyCompDate");
		if (!StringUtils.isNullOrEmpty(keyCompDate)) {
			housingListMationForm.setKeyCompDate(keyCompDate);
		}
		// おすすめのポイント
		String iconCd = request.getParameter("iconCd");
		if (!StringUtils.isNullOrEmpty(iconCd)) {
			housingListMationForm.setKeyIconCd(iconCd);
		}
		// 駅徒歩
		String partSrchCdWalk = request.getParameter("partSrchCdWalkArray");
		if (!StringUtils.isNullOrEmpty(partSrchCdWalk)) {
			housingListMationForm.setPartSrchCdWalkArray(partSrchCdWalk);
		}
		// 物件画像情報、物件特長
		String partSrchCd = request.getParameter("partSrchCdArray");
		if (!StringUtils.isNullOrEmpty(partSrchCd)) {
			housingListMationForm.setPartSrchCdArray(partSrchCd);
		}
		// 引渡時期
		String moveinTiming = request.getParameter("moveinTiming");
		if (!StringUtils.isNullOrEmpty(moveinTiming)) {
			housingListMationForm.setMoveinTiming(moveinTiming);
		}
		// 所在階数
		String partSrchCdFloor = request.getParameter("partSrchCdFloorArray");
		if (!StringUtils.isNullOrEmpty(partSrchCdFloor)) {
			housingListMationForm.setPartSrchCdFloorArray(partSrchCdFloor);
		}
		// 瑕疵保険
		String insurExist = request.getParameter("insurExist");
		if (!StringUtils.isNullOrEmpty(insurExist)) {
			housingListMationForm.setInsurExist(insurExist);
		}
		// 物件番号
		String keyHousingCd = request.getParameter("keyHousingCd");
		if (!StringUtils.isNullOrEmpty(keyHousingCd)) {
			housingListMationForm.setKeyHousingCd(keyHousingCd);
		}
		// keyRouteCd
		String keyRouteCd = request.getParameter("keyRouteCd");
		housingListMationForm.setKeyRouteCd(keyRouteCd);
		// keyStationCd
		String keyStationCd = request.getParameter("keyStationCd");
		housingListMationForm.setKeyStationCd(keyStationCd);
		// keyAddressCd
		String keyAddressCd = request.getParameter("keyAddressCd");
		housingListMationForm.setKeyAddressCd(keyAddressCd);

		// 予算
		Iterator<String> priceIte = this.codeLookupManager
				.getKeysByLookup("price");

		while (priceIte.hasNext()) {

			String price = priceIte.next();
			if (price.equals(priceLower)) {
				// 賃料/価格・下限（検索条件）
				if (!StringUtils.isNullOrEmpty(housingListMationForm
						.getPriceLower())) {

					String temp = this.codeLookupManager.lookupValue(
							"price", price);
					housingListMationForm.setKeyPriceLower(Long
							.valueOf(temp + "0000"));

				}
			}

			if (price.equals(priceUpper)) {
				// 賃料/価格・上限（検索条件）
				if (!StringUtils.isNullOrEmpty(housingListMationForm
						.getPriceUpper())) {

					String temp = this.codeLookupManager.lookupValue(
							"price", price);
					housingListMationForm.setKeyPriceUpper(Long
							.valueOf(temp + "0000"));
				}
			}
		}

		// 専有面積
		Iterator<String> personalAreaIte = this.codeLookupManager
				.getKeysByLookup("area");
		while (personalAreaIte.hasNext()) {
			String personalArea = personalAreaIte.next();
			if (personalArea.equals(personalAreaLower)) {
				// 専有面積・下限（検索条件）
				if (!StringUtils.isNullOrEmpty(personalAreaLower)) {

					String temp = this.codeLookupManager.lookupValue(
							"area", personalArea);
					housingListMationForm.setKeyPersonalAreaLower(BigDecimal
							.valueOf(Double.valueOf(temp)));
				}
			}
			if (personalArea.equals(personalAreaUpper)) {
				// 専有面積・下限（検索条件）
				if (!StringUtils.isNullOrEmpty(personalAreaUpper)) {

					String temp = this.codeLookupManager.lookupValue(
							"area", personalArea);
					housingListMationForm.setKeyPersonalAreaUpper(BigDecimal
							.valueOf(Double.valueOf(temp)));
				}
			}
		}

		// 建物面積
		Iterator<String> buildingAreaIte = this.codeLookupManager
				.getKeysByLookup("area");

		while (buildingAreaIte.hasNext()) {

			String buildingArea = buildingAreaIte.next();
			if (buildingArea.equals(buildingAreaLower)) {
				// 建物面積・下限（検索条件）
				if (!StringUtils.isNullOrEmpty(buildingAreaLower)) {

					String temp = this.codeLookupManager.lookupValue(
							"area", buildingArea);
					housingListMationForm.setKeyBuildingAreaLower(BigDecimal
							.valueOf(Double.valueOf(temp)));

				}
			}

			if (buildingArea.equals(buildingAreaUpper)) {
				// 建物面積・下限（検索条件）
				if (!StringUtils.isNullOrEmpty(buildingAreaUpper)) {

					String temp = this.codeLookupManager.lookupValue(
							"area", buildingArea);
					housingListMationForm.setKeyBuildingAreaUpper(BigDecimal
							.valueOf(Double.valueOf(temp)));

				}
			}

		}

		// 土地面積
		Iterator<String> landAreaIte = this.codeLookupManager
				.getKeysByLookup("area");

		while (landAreaIte.hasNext()) {

			String landArea = landAreaIte.next();
			if (landArea.equals(landAreaLower)) {
				// 建物面積・下限（検索条件）
				if (!StringUtils.isNullOrEmpty(landAreaLower)) {
					String temp = this.codeLookupManager.lookupValue(
							"area", landArea);
					housingListMationForm.setKeyLandAreaLower(BigDecimal
							.valueOf(Double.valueOf(temp)));
				}
			}

			if (landArea.equals(landAreaUpper)) {
				// 建物面積・下限（検索条件）
				if (!StringUtils.isNullOrEmpty(landAreaUpper)) {

					String temp = this.codeLookupManager.lookupValue(
							"area", landArea);
					housingListMationForm.setKeyLandAreaUpper(BigDecimal
							.valueOf(Double.valueOf(temp)));

				}
			}

		}
		// 間取り
		housingListMationForm.setKeyLayoutCd(layoutCd);

		// 都道府県CDを取得する。
		String prefCd = request.getParameter("prefCd");

		housingListMationForm.setKeyHiddenFlg("0");
		housingListMationForm.setHousingKindCd(this.housingKindCd);
		housingListMationForm.setKeyPrefCd(prefCd);

		// 都道府県CDにより、物件情報の取得
		int listSize = this.panaHousingManager
				.searchHousing(housingListMationForm);

		Map<String, String> map = null;
		map = new HashMap<String, String>();
		map.put("listSize", String.valueOf(listSize));

		// 取得したデータをレンダリング層へ渡す
		return new ModelAndView("success", "listSize", map);
	}

}
