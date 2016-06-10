package jp.co.transcosmos.dm3.webFront.housingRequest.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.constant.CommonParameters;
import jp.co.transcosmos.dm3.core.model.HousingRequestManage;
import jp.co.transcosmos.dm3.core.model.mypage.MypageUserInterface;
import jp.co.transcosmos.dm3.core.util.FrontLoginUserUtils;
import jp.co.transcosmos.dm3.core.vo.PrefMst;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonConstant;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonParameters;
import jp.co.transcosmos.dm3.corePana.model.PanaCommonManage;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingFormFactory;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingRequestForm;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;
import jp.co.transcosmos.dm3.mail.ReplacingMail;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

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
public class HousingRequestCompCommand implements Command {

    /** 共通パラメータオブジェクト */
    private PanaCommonParameters commonParameters;

	/** 物件情報用 Model オブジェクト */
	private HousingRequestManage housingRequestManage;
	/** 物件情報用 Model オブジェクト */
	private PanaCommonManage panaCommonManage;

	/** 共通コード変換処理 */
	protected CodeLookupManager codeLookupManager;

	/** 新規通知メールテンプレート */
	private ReplacingMail sendRequestInsertMansion;
	/** 新規通知メールテンプレート */
	private ReplacingMail sendRequestInsertHouse;
	/** 新規通知メールテンプレート */
	private ReplacingMail sendRequestInsertLand;

	/** 更新通知メールテンプレート */
	private ReplacingMail sendRequestUpdateMansion;
	/** 更新通知メールテンプレート */
	private ReplacingMail sendRequestUpdateHouse;
	/** 更新通知メールテンプレート */
	private ReplacingMail sendRequestUpdateLand;

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
	 * @param sendUpdateTemplate セットする sendUpdateTemplate
	 */
	public void setSendRequestInsertMansion(
			ReplacingMail sendRequestInsertMansion) {
		this.sendRequestInsertMansion = sendRequestInsertMansion;
	}

	/**
	 * @param sendUpdateTemplate セットする sendUpdateTemplate
	 */
	public void setSendRequestInsertHouse(
			ReplacingMail sendRequestInsertHouse) {
		this.sendRequestInsertHouse = sendRequestInsertHouse;
	}

	/**
	 * @param sendUpdateTemplate セットする sendUpdateTemplate
	 */
	public void setSendRequestInsertLand(
			ReplacingMail sendRequestInsertLand) {
		this.sendRequestInsertLand = sendRequestInsertLand;
	}

	/**
	 * @param sendUpdateTemplate セットする sendInsertTemplate
	 */
	public void setSendRequestUpdateMansion(
			ReplacingMail sendRequestUpdateMansion) {
		this.sendRequestUpdateMansion = sendRequestUpdateMansion;
	}

	/**
	 * @param sendUpdateTemplate セットする sendInsertTemplate
	 */
	public void setSendRequestUpdateHouse(
			ReplacingMail sendRequestUpdateHouse) {
		this.sendRequestUpdateHouse = sendRequestUpdateHouse;
	}

	/**
	 * @param sendUpdateTemplate セットする sendInsertTemplate
	 */
	public void setSendRequestUpdateLand(
			ReplacingMail sendRequestUpdateLand) {
		this.sendRequestUpdateLand = sendRequestUpdateLand;
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
		// バリデーションを実行
		List<ValidationFailure> errors = new ArrayList<ValidationFailure>();

		String command = housingRequestForm.getCommand();		

		if (command != null && command.equals("redirect")) {
			model.put("housingRequestForm", housingRequestForm);
			return new ModelAndView("comp", model);
		}

		if (!housingRequestForm.validate(errors)) {

			// 都道府県リストの設定
			List<PrefMst> prefMstList = this.panaCommonManage.getPrefMstList();
			model.put("prefMstList", prefMstList);

			// バリデーションエラーあり
			model.put("errors", errors);

			model.put("housingRequestForm", housingRequestForm);

			return new ModelAndView("validFail", model);
		}

		keyToValue(housingRequestForm);

		model.put("housingRequestForm", housingRequestForm);

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

		if("update".equals(housingRequestForm.getModel())){
			this.housingRequestManage.updateRequest(userId, housingRequestForm);

			if(PanaCommonConstant.HOUSING_KIND_CD_MANSION.equals(housingRequestForm.getHousingKindCd())){
				// メールテンプレートで使用するパラメータを設定する。
				this.sendRequestUpdateMansion.setParameter("housingRequestForm", housingRequestForm);
				this.sendRequestUpdateMansion.setParameter("email", loginUser.getEmail());
				this.sendRequestUpdateMansion.setParameter("housingKindName", "マンション");
				this.sendRequestUpdateMansion.setParameter("prefName", this.panaCommonManage.getPrefName(housingRequestForm.getPrefCd()));
				this.sendRequestUpdateMansion.setParameter("mypagePath", this.commonParameters.getMypageTopUrl());
				this.sendRequestUpdateMansion.setParameter("addressName", this.panaCommonManage.getAddressName(housingRequestForm.getAddressCd()));
				this.sendRequestUpdateMansion.setParameter("stationName", this.panaCommonManage.getStationName(housingRequestForm.getStationCd()));
				this.sendRequestUpdateMansion.setParameter("rootName", this.panaCommonManage.getRouteName(housingRequestForm.getRouteCd()));
				this.sendRequestUpdateMansion.setParameter("hopeName", housingRequestForm.getHopeRequestTest());
				this.sendRequestUpdateMansion.setParameter("commonParameters", CommonParameters.getInstance(request));

				// 予算
				if(!StringUtils.isEmpty(housingRequestForm.getPriceLowerMansion())){
					this.sendRequestUpdateMansion.setParameter("priceLower", housingRequestForm.getPriceLowerMansion()+"万円");
				}else{
					this.sendRequestUpdateMansion.setParameter("priceLower", "");
				}
				if(!StringUtils.isEmpty(housingRequestForm.getPriceLowerMansion()) || !StringUtils.isEmpty(housingRequestForm.getPriceUpperMansion())){
					this.sendRequestUpdateMansion.setParameter("priceFromTo", "　～　");
				}else{
					this.sendRequestUpdateMansion.setParameter("priceFromTo", "");
				}
				if(!StringUtils.isEmpty(housingRequestForm.getPriceUpperMansion())){
					this.sendRequestUpdateMansion.setParameter("priceUpper", housingRequestForm.getPriceUpperMansion()+"万円");
				}else{
					this.sendRequestUpdateMansion.setParameter("priceUpper", "");
				}

				if("1".equals(housingRequestForm.getReformCheckMansion())){
					this.sendRequestUpdateMansion.setParameter("reformCheckValue", "（リフォーム価格込みで検索する）");
				}else{
					this.sendRequestUpdateMansion.setParameter("reformCheckValue", "");
				}

				// リフォーム価格込みで検索する
				if("1".equals(housingRequestForm.getReformCheckMansion())){
					this.sendRequestUpdateMansion.setParameter("reformCheckValue", "（リフォーム価格込みで検索する）");
				}else{
					this.sendRequestUpdateMansion.setParameter("reformCheckValue", "");
				}

				// 専有面積
				if(!StringUtils.isEmpty(housingRequestForm.getPersonalAreaLowerMansion())){
					this.sendRequestUpdateMansion.setParameter("personalAreaLower", housingRequestForm.getPersonalAreaLowerMansion()+"m2");
				}else{
					this.sendRequestUpdateMansion.setParameter("personalAreaLower", "");
				}
				if(!StringUtils.isEmpty(housingRequestForm.getPersonalAreaLowerMansion()) || !StringUtils.isEmpty(housingRequestForm.getPersonalAreaUpperMansion())){
					this.sendRequestUpdateMansion.setParameter("perAreaFromTo", "　～　");
				}else{
					this.sendRequestUpdateMansion.setParameter("perAreaFromTo", "");
				}
				if(!StringUtils.isEmpty(housingRequestForm.getPersonalAreaUpperMansion())){
					this.sendRequestUpdateMansion.setParameter("personalAreaUpper", housingRequestForm.getPersonalAreaUpperMansion()+"m2");
				}else{
					this.sendRequestUpdateMansion.setParameter("personalAreaUpper", "");
				}

				// 築年数
				if(!StringUtils.isEmpty(housingRequestForm.getCompDateMansion()) && !"999".equals(housingRequestForm.getCompDateMansion())){
					this.sendRequestUpdateMansion.setParameter("compDate", housingRequestForm.getCompDateMansion()+" 年以内");
				}else{
					this.sendRequestUpdateMansion.setParameter("compDate", "");
				}

				// 間取り
				String layoutName = "";

				Iterator<String> layoutIte = this.codeLookupManager
						.getKeysByLookup("layoutCd");

				while (layoutIte.hasNext()) {
					String layoutCdName = layoutIte.next();

					if(!StringUtils.isEmpty(housingRequestForm.getLayoutCd())){
						for(int i=0;i<housingRequestForm.getLayoutCd().split(",").length;i++){

							if (layoutCdName.equals(housingRequestForm.getLayoutCd().split(",")[i])) {

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
				this.sendRequestUpdateMansion.setParameter("layoutName", layoutName);

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
				this.sendRequestUpdateMansion.setParameter("iconName", iconName);

				// メール送信
				this.sendRequestUpdateMansion.send();
			}

			if(PanaCommonConstant.HOUSING_KIND_CD_HOUSE.equals(housingRequestForm.getHousingKindCd())){
				// メールテンプレートで使用するパラメータを設定する。
				this.sendRequestUpdateHouse.setParameter("housingRequestForm", housingRequestForm);
				this.sendRequestUpdateHouse.setParameter("email", loginUser.getEmail());
				this.sendRequestUpdateHouse.setParameter("housingKindName", "戸建");
				this.sendRequestUpdateHouse.setParameter("prefName", this.panaCommonManage.getPrefName(housingRequestForm.getPrefCd()));
				this.sendRequestUpdateHouse.setParameter("mypagePath", this.commonParameters.getMypageTopUrl());
				this.sendRequestUpdateHouse.setParameter("addressName", this.panaCommonManage.getAddressName(housingRequestForm.getAddressCd()));
				this.sendRequestUpdateHouse.setParameter("stationName", this.panaCommonManage.getStationName(housingRequestForm.getStationCd()));
				this.sendRequestUpdateHouse.setParameter("rootName", this.panaCommonManage.getRouteName(housingRequestForm.getRouteCd()));
				this.sendRequestUpdateHouse.setParameter("hopeName", housingRequestForm.getHopeRequestTest());
				this.sendRequestUpdateHouse.setParameter("commonParameters", CommonParameters.getInstance(request));
				
				// 予算
				if(!StringUtils.isEmpty(housingRequestForm.getPriceLowerHouse())){
					this.sendRequestUpdateHouse.setParameter("priceLower", housingRequestForm.getPriceLowerHouse()+"万円");
				}else{
					this.sendRequestUpdateHouse.setParameter("priceLower", "");
				}
				if(!StringUtils.isEmpty(housingRequestForm.getPriceLowerHouse()) || !StringUtils.isEmpty(housingRequestForm.getPriceUpperHouse())){
					this.sendRequestUpdateHouse.setParameter("priceFromTo", "　～　");
				}else{
					this.sendRequestUpdateHouse.setParameter("priceFromTo", "");
				}
				if(!StringUtils.isEmpty(housingRequestForm.getPriceUpperHouse())){
					this.sendRequestUpdateHouse.setParameter("priceUpper", housingRequestForm.getPriceUpperHouse()+"万円");
				}else{
					this.sendRequestUpdateHouse.setParameter("priceUpper", "");
				}

				// リフォーム価格込みで検索する
				if("1".equals(housingRequestForm.getReformCheckHouse())){
					this.sendRequestUpdateHouse.setParameter("reformCheckValue", "（リフォーム価格込みで検索する）");
				}else{
					this.sendRequestUpdateHouse.setParameter("reformCheckValue", "");
				}

				// 建物面積
				if(!StringUtils.isEmpty(housingRequestForm.getBuildingAreaLowerHouse())){
					this.sendRequestUpdateHouse.setParameter("buildingAreaLower", housingRequestForm.getBuildingAreaLowerHouse()+"m2");
				}else{
					this.sendRequestUpdateHouse.setParameter("buildingAreaLower", "");
				}
				if(!StringUtils.isEmpty(housingRequestForm.getBuildingAreaLowerHouse()) || !StringUtils.isEmpty(housingRequestForm.getBuildingAreaUpperHouse())){
					this.sendRequestUpdateHouse.setParameter("buildingFromTo", "　～　");
				}else{
					this.sendRequestUpdateHouse.setParameter("buildingFromTo", "");
				}
				if(!StringUtils.isEmpty(housingRequestForm.getBuildingAreaUpperHouse())){
					this.sendRequestUpdateHouse.setParameter("buildingAreaUpper", housingRequestForm.getBuildingAreaUpperHouse()+"m2");
				}else{
					this.sendRequestUpdateHouse.setParameter("buildingAreaUpper", "");
				}

				// 土地面積
				if(!StringUtils.isEmpty(housingRequestForm.getLandAreaLowerHouse())){
					this.sendRequestUpdateHouse.setParameter("landAreaLower", housingRequestForm.getLandAreaLowerHouse()+"m2");
				}else{
					this.sendRequestUpdateHouse.setParameter("landAreaLower", "");
				}
				if(!StringUtils.isEmpty(housingRequestForm.getLandAreaLowerHouse()) || !StringUtils.isEmpty(housingRequestForm.getLandAreaUpperHouse())){
					this.sendRequestUpdateHouse.setParameter("landFromTo", "　～　");
				}else{
					this.sendRequestUpdateHouse.setParameter("landFromTo", "");
				}
				if(!StringUtils.isEmpty(housingRequestForm.getLandAreaUpperHouse())){
					this.sendRequestUpdateHouse.setParameter("landAreaUpper", housingRequestForm.getLandAreaUpperHouse()+"m2");
				}else{
					this.sendRequestUpdateHouse.setParameter("landAreaUpper", "");
				}

				// 築年数
				if(!StringUtils.isEmpty(housingRequestForm.getCompDateHouse()) && !"999".equals(housingRequestForm.getCompDateHouse())){
					this.sendRequestUpdateHouse.setParameter("compDate", housingRequestForm.getCompDateHouse()+" 年以内");
				}else{
					this.sendRequestUpdateHouse.setParameter("compDate", "");
				}

				// 間取り
				String layoutName = "";

				Iterator<String> layoutIte = this.codeLookupManager
						.getKeysByLookup("layoutCd");

				while (layoutIte.hasNext()) {
					String layoutCdName = layoutIte.next();

					if(!StringUtils.isEmpty(housingRequestForm.getLayoutCd())){
						for(int i=0;i<housingRequestForm.getLayoutCd().split(",").length;i++){

							if (layoutCdName.equals(housingRequestForm.getLayoutCd().split(",")[i])) {

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
				this.sendRequestUpdateHouse.setParameter("layoutName", layoutName);

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
				this.sendRequestUpdateHouse.setParameter("iconName", iconName);

				// メール送信
				this.sendRequestUpdateHouse.send();
			}

			if(PanaCommonConstant.HOUSING_KIND_CD_GROUND.equals(housingRequestForm.getHousingKindCd())){
				// メールテンプレートで使用するパラメータを設定する。
				this.sendRequestUpdateLand.setParameter("housingRequestForm", housingRequestForm);
				this.sendRequestUpdateLand.setParameter("email", loginUser.getEmail());
				this.sendRequestUpdateLand.setParameter("housingKindName", "土地");
				this.sendRequestUpdateLand.setParameter("prefName", this.panaCommonManage.getPrefName(housingRequestForm.getPrefCd()));
				this.sendRequestUpdateLand.setParameter("mypagePath", this.commonParameters.getMypageTopUrl());
				this.sendRequestUpdateLand.setParameter("addressName", this.panaCommonManage.getAddressName(housingRequestForm.getAddressCd()));
				this.sendRequestUpdateLand.setParameter("stationName", this.panaCommonManage.getStationName(housingRequestForm.getStationCd()));
				this.sendRequestUpdateLand.setParameter("rootName", this.panaCommonManage.getRouteName(housingRequestForm.getRouteCd()));
				this.sendRequestUpdateLand.setParameter("hopeName", housingRequestForm.getHopeRequestTest());
				this.sendRequestUpdateLand.setParameter("commonParameters", CommonParameters.getInstance(request));

				// 予算
				if(!StringUtils.isEmpty(housingRequestForm.getPriceLowerLand())){
					this.sendRequestUpdateLand.setParameter("priceLower", housingRequestForm.getPriceLowerLand()+"万円");
				}else{
					this.sendRequestUpdateLand.setParameter("priceLower", "");
				}
				if(!StringUtils.isEmpty(housingRequestForm.getPriceLowerLand()) || !StringUtils.isEmpty(housingRequestForm.getPriceUpperLand())){
					this.sendRequestUpdateLand.setParameter("priceFromTo", "　～　");
				}else{
					this.sendRequestUpdateLand.setParameter("priceFromTo", "");
				}
				if(!StringUtils.isEmpty(housingRequestForm.getPriceUpperLand())){
					this.sendRequestUpdateLand.setParameter("priceUpper", housingRequestForm.getPriceUpperLand()+"万円");
				}else{
					this.sendRequestUpdateLand.setParameter("priceUpper", "");
				}

				// 建物面積
				if(!StringUtils.isEmpty(housingRequestForm.getBuildingAreaLowerLand())){
					this.sendRequestUpdateLand.setParameter("buildingAreaLower", housingRequestForm.getBuildingAreaLowerLand()+"m2");
				}else{
					this.sendRequestUpdateLand.setParameter("buildingAreaLower", "");
				}
				if(!StringUtils.isEmpty(housingRequestForm.getBuildingAreaLowerLand()) || !StringUtils.isEmpty(housingRequestForm.getBuildingAreaUpperLand())){
					this.sendRequestUpdateLand.setParameter("buildingFromTo", "　～　");
				}else{
					this.sendRequestUpdateLand.setParameter("buildingFromTo", "");
				}
				if(!StringUtils.isEmpty(housingRequestForm.getBuildingAreaUpperLand())){
					this.sendRequestUpdateLand.setParameter("buildingAreaUpper", housingRequestForm.getBuildingAreaUpperLand()+"m2");
				}else{
					this.sendRequestUpdateLand.setParameter("buildingAreaUpper", "");
				}

				// 土地面積
				if(!StringUtils.isEmpty(housingRequestForm.getLandAreaLowerLand())){
					this.sendRequestUpdateLand.setParameter("landAreaLower", housingRequestForm.getLandAreaLowerLand()+"m2");
				}else{
					this.sendRequestUpdateLand.setParameter("landAreaLower", "");
				}
				if(!StringUtils.isEmpty(housingRequestForm.getLandAreaLowerLand()) || !StringUtils.isEmpty(housingRequestForm.getLandAreaUpperLand())){
					this.sendRequestUpdateLand.setParameter("landFromTo", "　～　");
				}else{
					this.sendRequestUpdateLand.setParameter("landFromTo", "");
				}
				if(!StringUtils.isEmpty(housingRequestForm.getLandAreaUpperLand())){
					this.sendRequestUpdateLand.setParameter("landAreaUpper", housingRequestForm.getLandAreaUpperLand()+"m2");
				}else{
					this.sendRequestUpdateLand.setParameter("landAreaUpper", "");
				}

				// メール送信
				this.sendRequestUpdateLand.send();
			}


		}

		if("insert".equals(housingRequestForm.getModel())){
			// 登録上限件数チェック
			int nowCnt = this.housingRequestManage.searchRequestCnt(userId);
			if (!(nowCnt < this.commonParameters.getMaxHousingRequestCnt())) {

				ValidationFailure vf = new ValidationFailure(
				        "maxAddError", String.valueOf(this.commonParameters.getMaxHousingRequestCnt()), "", null);
				errors.add(vf);
				// エラー処理
				// エラーオブジェクトと、フォームオブジェクトをModelAndView に渡している
				model.put("errors", errors);
				// 都道府県リストの設定
				List<PrefMst> prefMstList = this.panaCommonManage.getPrefMstList();
				model.put("prefMstList", prefMstList);
				// リストを取得する
				model.put("housingRequestForm", housingRequestForm);
				return new ModelAndView("validFail", model);
			}
			String housingRequestId = this.housingRequestManage.addRequest(userId, housingRequestForm);

			housingRequestForm.setHousingRequestId(housingRequestId);

			if(PanaCommonConstant.HOUSING_KIND_CD_MANSION.equals(housingRequestForm.getHousingKindCd())){
				// メールテンプレートで使用するパラメータを設定する。
				this.sendRequestInsertMansion.setParameter("housingRequestForm", housingRequestForm);
				this.sendRequestInsertMansion.setParameter("email", loginUser.getEmail());
				this.sendRequestInsertMansion.setParameter("housingKindName", "マンション");
				this.sendRequestInsertMansion.setParameter("mypagePath", this.commonParameters.getMypageTopUrl());
				this.sendRequestInsertMansion.setParameter("prefName", this.panaCommonManage.getPrefName(housingRequestForm.getPrefCd()));
				this.sendRequestInsertMansion.setParameter("addressName", this.panaCommonManage.getAddressName(housingRequestForm.getAddressCd()));
				this.sendRequestInsertMansion.setParameter("stationName", this.panaCommonManage.getStationName(housingRequestForm.getStationCd()));
				this.sendRequestInsertMansion.setParameter("rootName", this.panaCommonManage.getRouteName(housingRequestForm.getRouteCd()));
				this.sendRequestInsertMansion.setParameter("hopeName", housingRequestForm.getHopeRequestTest());
				this.sendRequestInsertMansion.setParameter("commonParameters", CommonParameters.getInstance(request));

				// 予算
				if(!StringUtils.isEmpty(housingRequestForm.getPriceLowerMansion())){
					this.sendRequestInsertMansion.setParameter("priceLower", housingRequestForm.getPriceLowerMansion()+"万円");
				}else{
					this.sendRequestInsertMansion.setParameter("priceLower", "");
				}
				if(!StringUtils.isEmpty(housingRequestForm.getPriceLowerMansion()) || !StringUtils.isEmpty(housingRequestForm.getPriceUpperMansion())){
					this.sendRequestInsertMansion.setParameter("priceFromTo", "　～　");
				}else{
					this.sendRequestInsertMansion.setParameter("priceFromTo", "");
				}
				if(!StringUtils.isEmpty(housingRequestForm.getPriceUpperMansion())){
					this.sendRequestInsertMansion.setParameter("priceUpper", housingRequestForm.getPriceUpperMansion()+"万円");
				}else{
					this.sendRequestInsertMansion.setParameter("priceUpper", "");
				}

				if("1".equals(housingRequestForm.getReformCheckMansion())){
					this.sendRequestInsertMansion.setParameter("reformCheckValue", "（リフォーム価格込みで検索する）");
				}else{
					this.sendRequestInsertMansion.setParameter("reformCheckValue", "");
				}

				// リフォーム価格込みで検索する
				if("1".equals(housingRequestForm.getReformCheckMansion())){
					this.sendRequestInsertMansion.setParameter("reformCheckValue", "（リフォーム価格込みで検索する）");
				}else{
					this.sendRequestInsertMansion.setParameter("reformCheckValue", "");
				}

				// 専有面積
				if(!StringUtils.isEmpty(housingRequestForm.getPersonalAreaLowerMansion())){
					this.sendRequestInsertMansion.setParameter("personalAreaLower", housingRequestForm.getPersonalAreaLowerMansion()+"m2");
				}else{
					this.sendRequestInsertMansion.setParameter("personalAreaLower", "");
				}
				if(!StringUtils.isEmpty(housingRequestForm.getPersonalAreaLowerMansion()) || !StringUtils.isEmpty(housingRequestForm.getPersonalAreaUpperMansion())){
					this.sendRequestInsertMansion.setParameter("perAreaFromTo", "　～　");
				}else{
					this.sendRequestInsertMansion.setParameter("perAreaFromTo", "");
				}
				if(!StringUtils.isEmpty(housingRequestForm.getPersonalAreaUpperMansion())){
					this.sendRequestInsertMansion.setParameter("personalAreaUpper", housingRequestForm.getPersonalAreaUpperMansion()+"m2");
				}else{
					this.sendRequestInsertMansion.setParameter("personalAreaUpper", "");
				}

				// 築年数
				if(!StringUtils.isEmpty(housingRequestForm.getCompDateMansion()) && !"999".equals(housingRequestForm.getCompDateMansion())){
					this.sendRequestInsertMansion.setParameter("compDate", housingRequestForm.getCompDateMansion()+" 年以内");
				}else{
					this.sendRequestInsertMansion.setParameter("compDate", "");
				}

				// 間取り
				String layoutName = "";

				Iterator<String> layoutIte = this.codeLookupManager
						.getKeysByLookup("layoutCd");

				while (layoutIte.hasNext()) {
					String layoutCdName = layoutIte.next();

					if(!StringUtils.isEmpty(housingRequestForm.getLayoutCd())){
						for(int i=0;i<housingRequestForm.getLayoutCd().split(",").length;i++){

							if (layoutCdName.equals(housingRequestForm.getLayoutCd().split(",")[i])) {

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
				this.sendRequestInsertMansion.setParameter("layoutName", layoutName);

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
				this.sendRequestInsertMansion.setParameter("iconName", iconName);
				// メール送信
				this.sendRequestInsertMansion.send();
			}

			if(PanaCommonConstant.HOUSING_KIND_CD_HOUSE.equals(housingRequestForm.getHousingKindCd())){
				
				// メールテンプレートで使用するパラメータを設定する。
				this.sendRequestInsertHouse.setParameter("housingRequestForm", housingRequestForm);
				this.sendRequestInsertHouse.setParameter("email", loginUser.getEmail());
				this.sendRequestInsertHouse.setParameter("housingKindName", "戸建");
				this.sendRequestInsertHouse.setParameter("prefName", this.panaCommonManage.getPrefName(housingRequestForm.getPrefCd()));
				this.sendRequestInsertHouse.setParameter("mypagePath", this.commonParameters.getMypageTopUrl());
				this.sendRequestInsertHouse.setParameter("addressName", this.panaCommonManage.getAddressName(housingRequestForm.getAddressCd()));
				this.sendRequestInsertHouse.setParameter("stationName", this.panaCommonManage.getStationName(housingRequestForm.getStationCd()));
				this.sendRequestInsertHouse.setParameter("rootName", this.panaCommonManage.getRouteName(housingRequestForm.getRouteCd()));
				this.sendRequestInsertHouse.setParameter("hopeName", housingRequestForm.getHopeRequestTest());
				this.sendRequestInsertHouse.setParameter("commonParameters", CommonParameters.getInstance(request));

				// 予算
				if(!StringUtils.isEmpty(housingRequestForm.getPriceLowerHouse())){
					this.sendRequestInsertHouse.setParameter("priceLower", housingRequestForm.getPriceLowerHouse()+"万円");
				}else{
					this.sendRequestInsertHouse.setParameter("priceLower", "");
				}
				if(!StringUtils.isEmpty(housingRequestForm.getPriceLowerHouse()) || !StringUtils.isEmpty(housingRequestForm.getPriceUpperHouse())){
					this.sendRequestInsertHouse.setParameter("priceFromTo", "　～　");
				}else{
					this.sendRequestInsertHouse.setParameter("priceFromTo", "");
				}
				if(!StringUtils.isEmpty(housingRequestForm.getPriceUpperHouse())){
					this.sendRequestInsertHouse.setParameter("priceUpper", housingRequestForm.getPriceUpperHouse()+"万円");
				}else{
					this.sendRequestInsertHouse.setParameter("priceUpper", "");
				}

				// リフォーム価格込みで検索する
				if("1".equals(housingRequestForm.getReformCheckHouse())){
					this.sendRequestInsertHouse.setParameter("reformCheckValue", "（リフォーム価格込みで検索する）");
				}else{
					this.sendRequestInsertHouse.setParameter("reformCheckValue", "");
				}

				// 建物面積
				if(!StringUtils.isEmpty(housingRequestForm.getBuildingAreaLowerHouse())){
					this.sendRequestInsertHouse.setParameter("buildingAreaLower", housingRequestForm.getBuildingAreaLowerHouse()+"m2");
				}else{
					this.sendRequestInsertHouse.setParameter("buildingAreaLower", "");
				}
				if(!StringUtils.isEmpty(housingRequestForm.getBuildingAreaLowerHouse()) || !StringUtils.isEmpty(housingRequestForm.getBuildingAreaUpperHouse())){
					this.sendRequestInsertHouse.setParameter("buildingFromTo", "　～　");
				}else{
					this.sendRequestInsertHouse.setParameter("buildingFromTo", "");
				}
				if(!StringUtils.isEmpty(housingRequestForm.getBuildingAreaUpperHouse())){
					this.sendRequestInsertHouse.setParameter("buildingAreaUpper", housingRequestForm.getBuildingAreaUpperHouse()+"m2");
				}else{
					this.sendRequestInsertHouse.setParameter("buildingAreaUpper", "");
				}

				// 土地面積
				if(!StringUtils.isEmpty(housingRequestForm.getLandAreaLowerHouse())){
					this.sendRequestInsertHouse.setParameter("landAreaLower", housingRequestForm.getLandAreaLowerHouse()+"m2");
				}else{
					this.sendRequestInsertHouse.setParameter("landAreaLower", "");
				}
				if(!StringUtils.isEmpty(housingRequestForm.getLandAreaLowerHouse()) || !StringUtils.isEmpty(housingRequestForm.getLandAreaUpperHouse())){
					this.sendRequestInsertHouse.setParameter("landFromTo", "　～　");
				}else{
					this.sendRequestInsertHouse.setParameter("landFromTo", "");
				}
				if(!StringUtils.isEmpty(housingRequestForm.getLandAreaUpperHouse())){
					this.sendRequestInsertHouse.setParameter("landAreaUpper", housingRequestForm.getLandAreaUpperHouse()+"m2");
				}else{
					this.sendRequestInsertHouse.setParameter("landAreaUpper", "");
				}

				// 築年数
				if(!StringUtils.isEmpty(housingRequestForm.getCompDateHouse())  && !"999".equals(housingRequestForm.getCompDateHouse())){
					this.sendRequestInsertHouse.setParameter("compDate", housingRequestForm.getCompDateHouse()+" 年以内");
				}else{
					this.sendRequestInsertHouse.setParameter("compDate", "");
				}

				// 間取り
				String layoutName = "";

				Iterator<String> layoutIte = this.codeLookupManager
						.getKeysByLookup("layoutCd");

				while (layoutIte.hasNext()) {
					String layoutCdName = layoutIte.next();
					if(!StringUtils.isEmpty(housingRequestForm.getLayoutCd())){
						for(int i=0;i<housingRequestForm.getLayoutCd().split(",").length;i++){

							if (layoutCdName.equals(housingRequestForm.getLayoutCd().split(",")[i])) {

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
				this.sendRequestInsertHouse.setParameter("layoutName", layoutName);

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
				this.sendRequestInsertHouse.setParameter("iconName", iconName);

				// メール送信
				this.sendRequestInsertHouse.send();
			}

			if(PanaCommonConstant.HOUSING_KIND_CD_GROUND.equals(housingRequestForm.getHousingKindCd())){
				// メールテンプレートで使用するパラメータを設定する。
				this.sendRequestInsertLand.setParameter("housingRequestForm", housingRequestForm);
				this.sendRequestInsertLand.setParameter("email", loginUser.getEmail());
				this.sendRequestInsertLand.setParameter("housingKindName", "土地");
				this.sendRequestInsertLand.setParameter("prefName", this.panaCommonManage.getPrefName(housingRequestForm.getPrefCd()));
				this.sendRequestInsertLand.setParameter("mypagePath", this.commonParameters.getMypageTopUrl());
				this.sendRequestInsertLand.setParameter("addressName", this.panaCommonManage.getAddressName(housingRequestForm.getAddressCd()));
				this.sendRequestInsertLand.setParameter("stationName", this.panaCommonManage.getStationName(housingRequestForm.getStationCd()));
				this.sendRequestInsertLand.setParameter("rootName", this.panaCommonManage.getRouteName(housingRequestForm.getRouteCd()));
				this.sendRequestInsertLand.setParameter("hopeName", housingRequestForm.getHopeRequestTest());
				this.sendRequestInsertLand.setParameter("commonParameters", CommonParameters.getInstance(request));

				// 予算
				if(!StringUtils.isEmpty(housingRequestForm.getPriceLowerLand())){
					this.sendRequestInsertLand.setParameter("priceLower", housingRequestForm.getPriceLowerLand()+"万円");
				}else{
					this.sendRequestInsertLand.setParameter("priceLower", "");
				}
				if(!StringUtils.isEmpty(housingRequestForm.getPriceLowerLand()) || !StringUtils.isEmpty(housingRequestForm.getPriceUpperLand())){
					this.sendRequestInsertLand.setParameter("priceFromTo", "　～　");
				}else{
					this.sendRequestInsertLand.setParameter("priceFromTo", "");
				}
				if(!StringUtils.isEmpty(housingRequestForm.getPriceUpperLand())){
					this.sendRequestInsertLand.setParameter("priceUpper", housingRequestForm.getPriceUpperLand()+"万円");
				}else{
					this.sendRequestInsertLand.setParameter("priceUpper", "");
				}

				// 建物面積
				if(!StringUtils.isEmpty(housingRequestForm.getBuildingAreaLowerLand())){
					this.sendRequestInsertLand.setParameter("buildingAreaLower", housingRequestForm.getBuildingAreaLowerLand()+"m2");
				}else{
					this.sendRequestInsertLand.setParameter("buildingAreaLower", "");
				}
				if(!StringUtils.isEmpty(housingRequestForm.getBuildingAreaLowerLand()) || !StringUtils.isEmpty(housingRequestForm.getBuildingAreaUpperLand())){
					this.sendRequestInsertLand.setParameter("buildingFromTo", "　～　");
				}else{
					this.sendRequestInsertLand.setParameter("buildingFromTo", "");
				}
				if(!StringUtils.isEmpty(housingRequestForm.getBuildingAreaUpperLand())){
					this.sendRequestInsertLand.setParameter("buildingAreaUpper", housingRequestForm.getBuildingAreaUpperLand()+"m2");
				}else{
					this.sendRequestInsertLand.setParameter("buildingAreaUpper", "");
				}

				// 土地面積
				if(!StringUtils.isEmpty(housingRequestForm.getLandAreaLowerLand())){
					this.sendRequestInsertLand.setParameter("landAreaLower", housingRequestForm.getLandAreaLowerLand()+"m2");
				}else{
					this.sendRequestInsertLand.setParameter("landAreaLower", "");
				}
				if(!StringUtils.isEmpty(housingRequestForm.getLandAreaLowerLand()) || !StringUtils.isEmpty(housingRequestForm.getLandAreaUpperLand())){
					this.sendRequestInsertLand.setParameter("landFromTo", "　～　");
				}else{
					this.sendRequestInsertLand.setParameter("landFromTo", "");
				}
				if(!StringUtils.isEmpty(housingRequestForm.getLandAreaUpperLand())){
					this.sendRequestInsertLand.setParameter("landAreaUpper", housingRequestForm.getLandAreaUpperLand()+"m2");
				}else{
					this.sendRequestInsertLand.setParameter("landAreaUpper", "");
				}

				// メール送信
				this.sendRequestInsertLand.send();
			}

		}

		// 取得したデータをレンダリング層へ渡す
		return new ModelAndView("success", model);
	}

	protected void keyToValue(PanaHousingRequestForm housingRequestForm){

		if("01".equals(housingRequestForm.getHousingKindCd())){

			// 予算
			Iterator<String> priceIte = this.codeLookupManager
					.getKeysByLookup("price");

			while (priceIte.hasNext()) {

				String price = priceIte.next();
				if (price.equals(housingRequestForm.getPriceLowerMansion())) {
					// 予算・下限
					if (!StringUtils.isEmpty(housingRequestForm.getPriceLowerMansion())) {

						String value = this.codeLookupManager.lookupValue("price", price);
						housingRequestForm.setPriceLowerMansion(value);

					}
				}

				if (price.equals(housingRequestForm.getPriceUpperMansion())) {
					// 予算・上限
					if (!StringUtils.isEmpty(housingRequestForm.getPriceUpperMansion())) {

						String value = this.codeLookupManager.lookupValue("price", price);

						housingRequestForm.setPriceUpperMansion(value);

					}
				}
			}

			// 専有面積
			Iterator<String> personalAreaIte = this.codeLookupManager
					.getKeysByLookup("area");

			while (personalAreaIte.hasNext()) {

				String personalArea = personalAreaIte.next();
				if (personalArea.equals(housingRequestForm.getPersonalAreaLowerMansion())) {
					// 専有面積・下限
					if (!StringUtils.isEmpty(housingRequestForm.getPersonalAreaLowerMansion())) {

						String value = this.codeLookupManager.lookupValue("area", personalArea);
						housingRequestForm.setPersonalAreaLowerMansion(value);

					}
				}

				if (personalArea.equals(housingRequestForm.getPersonalAreaUpperMansion())) {
					// 専有面積・上限
					if (!StringUtils.isEmpty(housingRequestForm.getPersonalAreaUpperMansion())) {

						String value = this.codeLookupManager.lookupValue("area", personalArea);

						housingRequestForm.setPersonalAreaUpperMansion(value);

					}
				}
			}

		}

		if("02".equals(housingRequestForm.getHousingKindCd())){

			// 予算
			Iterator<String> priceIte = this.codeLookupManager
					.getKeysByLookup("price");

			while (priceIte.hasNext()) {

				String price = priceIte.next();
				if (price.equals(housingRequestForm.getPriceLowerHouse())) {
					// 予算・下限
					if (!StringUtils.isEmpty(housingRequestForm.getPriceLowerHouse())) {

						String value = this.codeLookupManager.lookupValue("price", price);
						housingRequestForm.setPriceLowerHouse(value);

					}
				}

				if (price.equals(housingRequestForm.getPriceUpperHouse())) {
					// 予算・上限
					if (!StringUtils.isEmpty(housingRequestForm.getPriceUpperHouse())) {

						String value = this.codeLookupManager.lookupValue("price", price);

						housingRequestForm.setPriceUpperHouse(value);

					}
				}
			}

			// 建物面積
			Iterator<String> buildingAreaIte = this.codeLookupManager
					.getKeysByLookup("area");

			while (buildingAreaIte.hasNext()) {

				String buildingArea = buildingAreaIte.next();
				if (buildingArea.equals(housingRequestForm.getBuildingAreaLowerHouse())) {
					// 建物面積・下限
					if (!StringUtils.isEmpty(housingRequestForm.getBuildingAreaLowerHouse())) {

						String value = this.codeLookupManager.lookupValue("area", buildingArea);
						housingRequestForm.setBuildingAreaLowerHouse(value);

					}
				}

				if (buildingArea.equals(housingRequestForm.getBuildingAreaUpperHouse())) {
					// 建物面積・上限
					if (!StringUtils.isEmpty(housingRequestForm.getBuildingAreaUpperHouse())) {

						String value = this.codeLookupManager.lookupValue("area", buildingArea);

						housingRequestForm.setBuildingAreaUpperHouse(value);

					}
				}
			}

			// 土地面積
			Iterator<String> landAreaIte = this.codeLookupManager
					.getKeysByLookup("area");

			while (landAreaIte.hasNext()) {

				String landArea = landAreaIte.next();
				if (landArea.equals(housingRequestForm.getLandAreaLowerHouse())) {
					// 建物面積・下限
					if (!StringUtils.isEmpty(housingRequestForm.getLandAreaLowerHouse())) {

						String value = this.codeLookupManager.lookupValue("area", landArea);
						housingRequestForm.setLandAreaLowerHouse(value);

					}
				}

				if (landArea.equals(housingRequestForm.getLandAreaUpperHouse())) {
					// 建物面積・上限
					if (!StringUtils.isEmpty(housingRequestForm.getLandAreaUpperHouse())) {

						String value = this.codeLookupManager.lookupValue("area", landArea);

						housingRequestForm.setLandAreaUpperHouse(value);

					}
				}
			}

		}

		if("03".equals(housingRequestForm.getHousingKindCd())){

			// 予算
			Iterator<String> priceIte = this.codeLookupManager
					.getKeysByLookup("price");

			while (priceIte.hasNext()) {

				String price = priceIte.next();
				if (price.equals(housingRequestForm.getPriceLowerLand())) {
					// 予算・下限
					if (!StringUtils.isEmpty(housingRequestForm.getPriceLowerLand())) {

						String value = this.codeLookupManager.lookupValue("price", price);
						housingRequestForm.setPriceLowerLand(value);

					}
				}

				if (price.equals(housingRequestForm.getPriceUpperLand())) {
					// 予算・上限
					if (!StringUtils.isEmpty(housingRequestForm.getPriceUpperLand())) {

						String value = this.codeLookupManager.lookupValue("price", price);

						housingRequestForm.setPriceUpperLand(value);

					}
				}
			}

			// 建物面積
			Iterator<String> buildingAreaIte = this.codeLookupManager
					.getKeysByLookup("area");

			while (buildingAreaIte.hasNext()) {

				String buildingArea = buildingAreaIte.next();
				if (buildingArea.equals(housingRequestForm.getBuildingAreaLowerLand())) {
					// 建物面積・下限
					if (!StringUtils.isEmpty(housingRequestForm.getBuildingAreaLowerLand())) {

						String value = this.codeLookupManager.lookupValue("area", buildingArea);
						housingRequestForm.setBuildingAreaLowerLand(value);

					}
				}

				if (buildingArea.equals(housingRequestForm.getBuildingAreaUpperLand())) {
					// 建物面積・上限
					if (!StringUtils.isEmpty(housingRequestForm.getBuildingAreaUpperLand())) {

						String value = this.codeLookupManager.lookupValue("area", buildingArea);

						housingRequestForm.setBuildingAreaUpperLand(value);

					}
				}
			}

			// 土地面積
			Iterator<String> landAreaIte = this.codeLookupManager
					.getKeysByLookup("area");

			while (landAreaIte.hasNext()) {

				String landArea = landAreaIte.next();
				if (landArea.equals(housingRequestForm.getLandAreaLowerLand())) {
					// 建物面積・下限
					if (!StringUtils.isEmpty(housingRequestForm.getLandAreaLowerLand())) {

						String value = this.codeLookupManager.lookupValue("area", landArea);
						housingRequestForm.setLandAreaLowerLand(value);

					}
				}

				if (landArea.equals(housingRequestForm.getLandAreaUpperLand())) {
					// 建物面積・上限
					if (!StringUtils.isEmpty(housingRequestForm.getLandAreaUpperLand())) {

						String value = this.codeLookupManager.lookupValue("area", landArea);

						housingRequestForm.setLandAreaUpperLand(value);

					}
				}
			}

		}

	}

}
