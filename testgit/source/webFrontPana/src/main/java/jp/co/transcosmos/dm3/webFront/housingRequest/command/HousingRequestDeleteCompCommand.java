package jp.co.transcosmos.dm3.webFront.housingRequest.command;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.constant.CommonParameters;
import jp.co.transcosmos.dm3.core.model.HousingRequestManage;
import jp.co.transcosmos.dm3.core.model.mypage.MypageUserInterface;
import jp.co.transcosmos.dm3.core.util.FrontLoginUserUtils;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonConstant;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonParameters;
import jp.co.transcosmos.dm3.corePana.model.PanaCommonManage;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingFormFactory;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingRequestForm;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;
import jp.co.transcosmos.dm3.mail.ReplacingMail;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;

/**
 * 物件リクエスト入力画面
 *
 * <pre>
 * 【復帰する View 名】
 *    ・"success" : 正常終了
 *
 * 担当者       修正日      修正内容
 * ------------ ----------- -----------------------------------------------------
 *   焦		  2015.04.22    新規作成
 *
 * 注意事項
 *
 * </pre>
 */
public class HousingRequestDeleteCompCommand implements Command {

	/** 物件情報用 Model オブジェクト */
	private HousingRequestManage housingRequestManage;

    /** 共通パラメータオブジェクト */
    private PanaCommonParameters commonParameters;

	/** 物件情報用 Model オブジェクト */
	private PanaCommonManage panaCommonManage;

	/** 共通コード変換処理 */
	protected CodeLookupManager codeLookupManager;

	/** 削除通知メールテンプレート */
	private ReplacingMail sendRequestDeleteMansion;

	/** 削除通知メールテンプレート */
	private ReplacingMail sendRequestDeleteHouse;

	/** 削除通知メールテンプレート */
	private ReplacingMail sendRequestDeleteLand;

    /**
     * 共通パラメータオブジェクトを設定する。<br/>
     * <br/>
     * @param commonParameters 共通パラメータオブジェクト
     */
    public void setCommonParameters(PanaCommonParameters commonParameters) {
        this.commonParameters = commonParameters;
    }

	/**
	 * 共通情報取用 Model オブジェクトを設定する。<br/>
	 * <br/>
	 * @param PanaCommonManage 共通情報取用 Model オブジェクト
	 */
	public void setPanaCommonManage(PanaCommonManage panaCommonManage) {
		this.panaCommonManage = panaCommonManage;
	}

	/**
	 * @param sendDeleteTemplate セットする sendUpdateTemplate
	 */
	public void setSendRequestDeleteMansion(
			ReplacingMail sendRequestDeleteMansion) {
		this.sendRequestDeleteMansion = sendRequestDeleteMansion;
	}

	/**
	 * @param sendDeleteTemplate セットする sendUpdateTemplate
	 */
	public void setSendRequestDeleteHouse(
			ReplacingMail sendRequestDeleteHouse) {
		this.sendRequestDeleteHouse = sendRequestDeleteHouse;
	}

	/**
	 * @param sendDeleteTemplate セットする sendUpdateTemplate
	 */
	public void setSendRequestDeleteLand(
			ReplacingMail sendRequestDeleteLand) {
		this.sendRequestDeleteLand = sendRequestDeleteLand;
	}

	/**
	 * 物件リクエスト情報用 Model オブジェクトを設定する。<br/>
	 * <br/>
	 *
	 * @param housingRequestManage
	 *            物件リクエスト情報用 Model オブジェクト
	 */
	public void setHousingRequestManage(HousingRequestManage housingRequestManage) {
		this.housingRequestManage = housingRequestManage;
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
	 * 物件リクエスト入力画面表示処理<br>
	 * <br>
	 *
	 * @param request
	 *            HTTP リクエスト
	 * @param response
	 *            HTTP レスポンス
	 */
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Map<String, Object> model = new HashMap<String, Object>();

		PanaHousingFormFactory factory = PanaHousingFormFactory
				.getInstance(request);

		// ページ処理用のフォームオブジェクトを作成
		PanaHousingRequestForm housingRequestForm = factory
				.createPanaHousingRequestForm(request);

		String command = housingRequestForm.getCommand();
		if (command != null && command.equals("redirect")) {
			model.put("housingRequestForm", housingRequestForm);
			return new ModelAndView("comp", model);
		}

		// 伝達情報の物件リクエストID（housing_request_id）
		if(StringUtils.isEmpty(housingRequestForm.getHousingRequestId())){
			throw new RuntimeException("物件リクエストIDが指定されていません.");
		}

		// ログインユーザーの情報を取得
		MypageUserInterface loginUser = FrontLoginUserUtils.getInstance(request).getMypageLoginUserInfo(request, response);
		// ユーザIDを取得
		String userId = "";
		if (loginUser != null) {
			userId = loginUser.getUserId().toString();
			model.put("loginFlg", 0);
		}else{
			userId = FrontLoginUserUtils.getInstance(request).getAnonLoginUserInfo(request, response).getUserId().toString();
			model.put("loginFlg", 1);
		}

		this.housingRequestManage.delRequest(userId, housingRequestForm);

		// メールテンプレートで使用するパラメータを設定する。
		if(PanaCommonConstant.HOUSING_KIND_CD_MANSION.equals(housingRequestForm.getHousingKindCd())){
			this.sendRequestDeleteMansion.setParameter("housingRequestForm", housingRequestForm);
			this.sendRequestDeleteMansion.setParameter("email", loginUser.getEmail());
			this.sendRequestDeleteMansion.setParameter("housingKindName", "マンション");
			this.sendRequestDeleteMansion.setParameter("mypagePath", this.commonParameters.getMypageTopUrl());
			this.sendRequestDeleteMansion.setParameter("prefName", this.panaCommonManage.getPrefName(housingRequestForm.getPrefCd()));
			this.sendRequestDeleteMansion.setParameter("addressName", this.panaCommonManage.getAddressName(housingRequestForm.getAddressCd()));
			this.sendRequestDeleteMansion.setParameter("stationName", this.panaCommonManage.getStationName(housingRequestForm.getStationCd()));
			this.sendRequestDeleteMansion.setParameter("rootName", this.panaCommonManage.getRouteName(housingRequestForm.getRouteCd()));
			this.sendRequestDeleteMansion.setParameter("commonParameters", CommonParameters.getInstance(request));

			// 予算
			if(!StringUtils.isEmpty(housingRequestForm.getPriceLowerMansion())){
				this.sendRequestDeleteMansion.setParameter("priceLower", housingRequestForm.getPriceLowerMansion()+"万円");
			}else{
				this.sendRequestDeleteMansion.setParameter("priceLower", "");
			}
			if(!StringUtils.isEmpty(housingRequestForm.getPriceLowerMansion()) || !StringUtils.isEmpty(housingRequestForm.getPriceUpperMansion())){
				this.sendRequestDeleteMansion.setParameter("priceFromTo", "　～　");
			}else{
				this.sendRequestDeleteMansion.setParameter("priceFromTo", "");
			}
			if(!StringUtils.isEmpty(housingRequestForm.getPriceUpperMansion())){
				this.sendRequestDeleteMansion.setParameter("priceUpper", housingRequestForm.getPriceUpperMansion()+"万円");
			}else{
				this.sendRequestDeleteMansion.setParameter("priceUpper", "");
			}

			if("1".equals(housingRequestForm.getReformCheckMansion())){
				this.sendRequestDeleteMansion.setParameter("reformCheckValue", "（リフォーム価格込みで検索する）");
			}else{
				this.sendRequestDeleteMansion.setParameter("reformCheckValue", "");
			}

			// リフォーム価格込みで検索する
			if("1".equals(housingRequestForm.getReformCheckMansion())){
				this.sendRequestDeleteMansion.setParameter("reformCheckValue", "（リフォーム価格込みで検索する）");
			}else{
				this.sendRequestDeleteMansion.setParameter("reformCheckValue", "");
			}

			// 専有面積
			if(!StringUtils.isEmpty(housingRequestForm.getPersonalAreaLowerMansion())){
				this.sendRequestDeleteMansion.setParameter("personalAreaLower", housingRequestForm.getPersonalAreaLowerMansion()+"m2");
			}else{
				this.sendRequestDeleteMansion.setParameter("personalAreaLower", "");
			}
			if(!StringUtils.isEmpty(housingRequestForm.getPersonalAreaLowerMansion()) || !StringUtils.isEmpty(housingRequestForm.getPersonalAreaUpperMansion())){
				this.sendRequestDeleteMansion.setParameter("perAreaFromTo", "　～　");
			}else{
				this.sendRequestDeleteMansion.setParameter("perAreaFromTo", "");
			}
			if(!StringUtils.isEmpty(housingRequestForm.getPersonalAreaUpperMansion())){
				this.sendRequestDeleteMansion.setParameter("personalAreaUpper", housingRequestForm.getPersonalAreaUpperMansion()+"m2");
			}else{
				this.sendRequestDeleteMansion.setParameter("personalAreaUpper", "");
			}

			// 築年数
			if(!StringUtils.isEmpty(housingRequestForm.getCompDateMansion()) && !"999".equals(housingRequestForm.getCompDateMansion())){
				this.sendRequestDeleteMansion.setParameter("compDate", housingRequestForm.getCompDateMansion()+" 年以内");
			}else{
				this.sendRequestDeleteMansion.setParameter("compDate", "");
			}

			// 間取り
			String layoutName = "";

			Iterator<String> layoutIte = this.codeLookupManager
					.getKeysByLookup("layoutCd");

			while (layoutIte.hasNext()) {
				String layoutCdName = layoutIte.next();

				if(!StringUtils.isEmpty(housingRequestForm.getLayoutCdMansion())){

					for(int i=0;i<housingRequestForm.getLayoutCdMansion().length;i++){

						if (layoutCdName.equals(housingRequestForm.getLayoutCdMansion()[i])) {

							String temp = this.codeLookupManager.lookupValue(
									"layoutCd", layoutCdName);

							if(StringUtils.isEmpty(layoutName)){
								layoutName = layoutName + temp;
							}else{
								layoutName = layoutName + ", " + temp;
							}

						}

					}
				}

			}
			this.sendRequestDeleteMansion.setParameter("layoutName", layoutName);

			// おすすめのポイント
			String iconName = "";

			Iterator<String> iconIte = this.codeLookupManager
					.getKeysByLookup("recommend_point_icon_list");

			while (iconIte.hasNext()) {
				String iconCdName = iconIte.next();

				if(!StringUtils.isEmpty(housingRequestForm.getIconCdMansion())){
					for(int i=0;i<housingRequestForm.getIconCdMansion().length;i++){

						if (iconCdName.equals(housingRequestForm.getIconCdMansion()[i])) {

							String temp = this.codeLookupManager.lookupValue(
									"recommend_point_icon_list", iconCdName);

							if(StringUtils.isEmpty(iconName)){
								iconName = iconName + temp;
							}else{
								iconName = iconName + "," + temp;
							}

						}

					}
				}

			}
			this.sendRequestDeleteMansion.setParameter("iconName", iconName);

			// メール送信
			this.sendRequestDeleteMansion.send();
		}
		if(PanaCommonConstant.HOUSING_KIND_CD_HOUSE.equals(housingRequestForm.getHousingKindCd())){
			this.sendRequestDeleteHouse.setParameter("housingRequestForm", housingRequestForm);
			this.sendRequestDeleteHouse.setParameter("email", loginUser.getEmail());
			this.sendRequestDeleteHouse.setParameter("housingKindName", "戸建");
			this.sendRequestDeleteHouse.setParameter("mypagePath", this.commonParameters.getMypageTopUrl());
			this.sendRequestDeleteHouse.setParameter("prefName", this.panaCommonManage.getPrefName(housingRequestForm.getPrefCd()));
			this.sendRequestDeleteHouse.setParameter("addressName", this.panaCommonManage.getAddressName(housingRequestForm.getAddressCd()));
			this.sendRequestDeleteHouse.setParameter("stationName", this.panaCommonManage.getStationName(housingRequestForm.getStationCd()));
			this.sendRequestDeleteHouse.setParameter("rootName", this.panaCommonManage.getRouteName(housingRequestForm.getRouteCd()));
			this.sendRequestDeleteHouse.setParameter("commonParameters", CommonParameters.getInstance(request));

			// 予算
			if(!StringUtils.isEmpty(housingRequestForm.getPriceLowerHouse())){
				this.sendRequestDeleteHouse.setParameter("priceLower", housingRequestForm.getPriceLowerHouse()+"万円");
			}else{
				this.sendRequestDeleteHouse.setParameter("priceLower", "");
			}
			if(!StringUtils.isEmpty(housingRequestForm.getPriceLowerHouse()) || !StringUtils.isEmpty(housingRequestForm.getPriceUpperHouse())){
				this.sendRequestDeleteHouse.setParameter("priceFromTo", "　～　");
			}else{
				this.sendRequestDeleteHouse.setParameter("priceFromTo", "");
			}
			if(!StringUtils.isEmpty(housingRequestForm.getPriceUpperHouse())){
				this.sendRequestDeleteHouse.setParameter("priceUpper", housingRequestForm.getPriceUpperHouse()+"万円");
			}else{
				this.sendRequestDeleteHouse.setParameter("priceUpper", "");
			}

			// リフォーム価格込みで検索する
			if("1".equals(housingRequestForm.getReformCheckHouse())){
				this.sendRequestDeleteHouse.setParameter("reformCheckValue", "（リフォーム価格込みで検索する）");
			}else{
				this.sendRequestDeleteHouse.setParameter("reformCheckValue", "");
			}

			// 建物面積
			if(!StringUtils.isEmpty(housingRequestForm.getBuildingAreaLowerHouse())){
				this.sendRequestDeleteHouse.setParameter("buildingAreaLower", housingRequestForm.getBuildingAreaLowerHouse()+"m2");
			}else{
				this.sendRequestDeleteHouse.setParameter("buildingAreaLower", "");
			}
			if(!StringUtils.isEmpty(housingRequestForm.getBuildingAreaLowerHouse()) || !StringUtils.isEmpty(housingRequestForm.getBuildingAreaUpperHouse())){
				this.sendRequestDeleteHouse.setParameter("buildingFromTo", "　～　");
			}else{
				this.sendRequestDeleteHouse.setParameter("buildingFromTo", "");
			}
			if(!StringUtils.isEmpty(housingRequestForm.getBuildingAreaUpperHouse())){
				this.sendRequestDeleteHouse.setParameter("buildingAreaUpper", housingRequestForm.getBuildingAreaUpperHouse()+"m2");
			}else{
				this.sendRequestDeleteHouse.setParameter("buildingAreaUpper", "");
			}

			// 土地面積
			if(!StringUtils.isEmpty(housingRequestForm.getLandAreaLowerHouse())){
				this.sendRequestDeleteHouse.setParameter("landAreaLower", housingRequestForm.getLandAreaLowerHouse()+"m2");
			}else{
				this.sendRequestDeleteHouse.setParameter("landAreaLower", "");
			}
			if(!StringUtils.isEmpty(housingRequestForm.getLandAreaLowerHouse()) || !StringUtils.isEmpty(housingRequestForm.getLandAreaUpperHouse())){
				this.sendRequestDeleteHouse.setParameter("landFromTo", "　～　");
			}else{
				this.sendRequestDeleteHouse.setParameter("landFromTo", "");
			}
			if(!StringUtils.isEmpty(housingRequestForm.getLandAreaUpperHouse())){
				this.sendRequestDeleteHouse.setParameter("landAreaUpper", housingRequestForm.getLandAreaUpperHouse()+"m2");
			}else{
				this.sendRequestDeleteHouse.setParameter("landAreaUpper", "");
			}

			// 築年数
			if(!StringUtils.isEmpty(housingRequestForm.getCompDateHouse()) && !"999".equals(housingRequestForm.getCompDateMansion())){
				this.sendRequestDeleteHouse.setParameter("compDate", housingRequestForm.getCompDateHouse()+" 年以内");
			}else{
				this.sendRequestDeleteHouse.setParameter("compDate", "");
			}

			// 間取り
			String layoutName = "";

			Iterator<String> layoutIte = this.codeLookupManager
					.getKeysByLookup("layoutCd");

			while (layoutIte.hasNext()) {
				String layoutCdName = layoutIte.next();

				if(!StringUtils.isEmpty(housingRequestForm.getLayoutCdHouse())){
					for(int i=0;i<housingRequestForm.getLayoutCdHouse().length;i++){

						if (layoutCdName.equals(housingRequestForm.getLayoutCdHouse()[i])) {

							String temp = this.codeLookupManager.lookupValue(
									"layoutCd", layoutCdName);

							if(StringUtils.isEmpty(layoutName)){
								layoutName = layoutName + temp;
							}else{
								layoutName = layoutName + ", " + temp;
							}

						}

					}
				}

			}
			this.sendRequestDeleteHouse.setParameter("layoutName", layoutName);

			// おすすめのポイント
			String iconName = "";

			Iterator<String> iconIte = this.codeLookupManager
					.getKeysByLookup("recommend_point_icon_list");

			while (iconIte.hasNext()) {
				String iconCdName = iconIte.next();

				if(!StringUtils.isEmpty(housingRequestForm.getIconCdHouse())){
					for(int i=0;i<housingRequestForm.getIconCdHouse().length;i++){

						if (iconCdName.equals(housingRequestForm.getIconCdHouse()[i])) {

							String temp = this.codeLookupManager.lookupValue(
									"recommend_point_icon_list", iconCdName);

							if(StringUtils.isEmpty(iconName)){
								iconName = iconName + temp;
							}else{
								iconName = iconName + "," + temp;
							}

						}

					}
				}

			}
			this.sendRequestDeleteHouse.setParameter("iconName", iconName);

			// メール送信
			this.sendRequestDeleteHouse.send();
		}
		if(PanaCommonConstant.HOUSING_KIND_CD_GROUND.equals(housingRequestForm.getHousingKindCd())){
			this.sendRequestDeleteLand.setParameter("housingRequestForm", housingRequestForm);
			this.sendRequestDeleteLand.setParameter("email", loginUser.getEmail());
			this.sendRequestDeleteLand.setParameter("housingKindName", "土地");
			this.sendRequestDeleteLand.setParameter("mypagePath", this.commonParameters.getMypageTopUrl());
			this.sendRequestDeleteLand.setParameter("prefName", this.panaCommonManage.getPrefName(housingRequestForm.getPrefCd()));
			this.sendRequestDeleteLand.setParameter("addressName", this.panaCommonManage.getAddressName(housingRequestForm.getAddressCd()));
			this.sendRequestDeleteLand.setParameter("stationName", this.panaCommonManage.getStationName(housingRequestForm.getStationCd()));
			this.sendRequestDeleteLand.setParameter("rootName", this.panaCommonManage.getRouteName(housingRequestForm.getRouteCd()));
			this.sendRequestDeleteLand.setParameter("commonParameters", CommonParameters.getInstance(request));

			// 予算
			if(!StringUtils.isEmpty(housingRequestForm.getPriceLowerLand())){
				this.sendRequestDeleteLand.setParameter("priceLower", housingRequestForm.getPriceLowerLand()+"万円");
			}else{
				this.sendRequestDeleteLand.setParameter("priceLower", "");
			}
			if(!StringUtils.isEmpty(housingRequestForm.getPriceLowerLand()) || !StringUtils.isEmpty(housingRequestForm.getPriceUpperLand())){
				this.sendRequestDeleteLand.setParameter("priceFromTo", "　～　");
			}else{
				this.sendRequestDeleteLand.setParameter("priceFromTo", "");
			}
			if(!StringUtils.isEmpty(housingRequestForm.getPriceUpperLand())){
				this.sendRequestDeleteLand.setParameter("priceUpper", housingRequestForm.getPriceUpperLand()+"万円");
			}else{
				this.sendRequestDeleteLand.setParameter("priceUpper", "");
			}

			// 建物面積
			if(!StringUtils.isEmpty(housingRequestForm.getBuildingAreaLowerLand())){
				this.sendRequestDeleteLand.setParameter("buildingAreaLower", housingRequestForm.getBuildingAreaLowerLand()+"m2");
			}else{
				this.sendRequestDeleteLand.setParameter("buildingAreaLower", "");
			}
			if(!StringUtils.isEmpty(housingRequestForm.getBuildingAreaLowerLand()) || !StringUtils.isEmpty(housingRequestForm.getBuildingAreaUpperLand())){
				this.sendRequestDeleteLand.setParameter("buildingFromTo", "　～　");
			}else{
				this.sendRequestDeleteLand.setParameter("buildingFromTo", "");
			}
			if(!StringUtils.isEmpty(housingRequestForm.getBuildingAreaUpperLand())){
				this.sendRequestDeleteLand.setParameter("buildingAreaUpper", housingRequestForm.getBuildingAreaUpperLand()+"m2");
			}else{
				this.sendRequestDeleteLand.setParameter("buildingAreaUpper", "");
			}

			// 土地面積
			if(!StringUtils.isEmpty(housingRequestForm.getLandAreaLowerLand())){
				this.sendRequestDeleteLand.setParameter("landAreaLower", housingRequestForm.getLandAreaLowerLand()+"m2");
			}else{
				this.sendRequestDeleteLand.setParameter("landAreaLower", "");
			}
			if(!StringUtils.isEmpty(housingRequestForm.getLandAreaLowerLand()) || !StringUtils.isEmpty(housingRequestForm.getLandAreaUpperLand())){
				this.sendRequestDeleteLand.setParameter("landFromTo", "　～　");
			}else{
				this.sendRequestDeleteLand.setParameter("landFromTo", "");
			}
			if(!StringUtils.isEmpty(housingRequestForm.getLandAreaUpperLand())){
				this.sendRequestDeleteLand.setParameter("landAreaUpper", housingRequestForm.getLandAreaUpperLand()+"m2");
			}else{
				this.sendRequestDeleteLand.setParameter("landAreaUpper", "");
			}
			// メール送信
			this.sendRequestDeleteLand.send();
		}




		// 取得したデータをレンダリング層へ渡す
		return new ModelAndView("success", model);
	}

}
