package jp.co.transcosmos.dm3.corePana.model.housing;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.co.transcosmos.dm3.core.model.building.form.BuildingLandmarkForm;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonConstant;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonParameters;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingDtlInfoForm;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingImageInfoForm;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingInfoForm;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingInspectionForm;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingSpecialtyForm;
import jp.co.transcosmos.dm3.corePana.model.housing.form.RecommendPointForm;
import jp.co.transcosmos.dm3.corePana.model.reform.form.ReformDtlForm;
import jp.co.transcosmos.dm3.corePana.model.reform.form.ReformImgForm;
import jp.co.transcosmos.dm3.corePana.model.reform.form.ReformInfoForm;
import jp.co.transcosmos.dm3.corePana.util.HousingImageComparator;
import jp.co.transcosmos.dm3.corePana.util.PanaCalcUtil;
import jp.co.transcosmos.dm3.corePana.util.PanaFileUtil;
import jp.co.transcosmos.dm3.corePana.util.PanaStringUtils;
import jp.co.transcosmos.dm3.corePana.util.ReformDtlComparator;
import jp.co.transcosmos.dm3.corePana.util.ReformImageComparator;
import jp.co.transcosmos.dm3.corePana.vo.HousingImageInfo;
import jp.co.transcosmos.dm3.corePana.vo.ReformDtl;
import jp.co.transcosmos.dm3.corePana.vo.ReformImg;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;

import org.springframework.util.StringUtils;

/**
 * 物件プレビュー画面用フォーム.
 * <p>
 * <pre>
 * 担当者        修正日        修正内容
 * ------------ ----------- -----------------------------------------------------
 * Y.Zhang        2015.05.07     新規作成
 * </pre>
 * <p>
 *
 */
public class PanaHousingPreview extends PanaHousingDetailed {

	/**
	 * コンストラクター<br/>
	 * <br/>
	 */
	public PanaHousingPreview(CodeLookupManager codeLookupManager, PanaCommonParameters commonParameters, PanaFileUtil panaFileUtil) {
		super(codeLookupManager, commonParameters, panaFileUtil);
	}

	/**
	 * 渡された物件基本情報から 出力Form へプレビュー内容を設定する。<br/>
	 * <br/>
	 *
	 * @param inputForm 物件基本情報Form オブジェクト
	 * @param outPutForm 出力用Form オブジェクト
	 *
	 * @exception Exception 実装クラスによりスローされる任意の例外
	 */
	public void setHousingInfoPreviewData(PanaHousingInfoForm inputForm, PanaHousingPreview outPutForm) throws Exception {

		// 物件名称
		outPutForm.setDisplayHousingName(inputForm.getDisplayHousingName());

		// 価格
		outPutForm.setPrice(isEmpty(inputForm.getPrice()) ? "" : formatPrice(inputForm.getPrice(), true) + "万円");

		// 築年
		outPutForm.setCompDate(isEmpty(inputForm.getCompDate()) ? "" : new SimpleDateFormat("yyyy年M月築").format(new SimpleDateFormat("yyyyMM").parse(inputForm.getCompDate())));

		// 間取り
		outPutForm.setLayoutCd(inputForm.getLayoutCd());

		// 所在地
		outPutForm.setAddress(defaultString(inputForm.getPrefName()) + " " + defaultString(inputForm.getAddressName()) + " " + defaultString(inputForm.getAddressOther1()) + " "
				+ defaultString(inputForm.getAddressOther2()));

		// アクセス
		if (inputForm.getDefaultRouteCd() != null) {

			int cnt = inputForm.getDefaultRouteCd().length;
			String[] access = new String[cnt];

			for (int i = 0; i < cnt; i++) {
				StringBuilder sbAccess = new StringBuilder();
				// 代表路線名
				sbAccess.append(addString(defaultString(inputForm.getRouteNameRr()[i]), " "));
				// 駅名
				sbAccess.append(addString(defaultString(inputForm.getStationName()[i]), "駅 "));
				// バス会社名
				sbAccess.append(addString(defaultString(inputForm.getBusCompany()[i]), " "));
				// バス停からの徒歩時間
				sbAccess.append(defaultString(isEmpty(inputForm.getTimeFromBusStop()[i]) ? "" : "徒歩" + inputForm.getTimeFromBusStop()[i] + "分"));
				access[i] = sbAccess.toString();
			}

			// アクセス「配列」
			outPutForm.setAccess(access);

		} else {

			outPutForm.setAccess(null);
		}

		// 建物面積
		outPutForm.setBuildingArea(defaultString(inputForm.getBuildingArea()));

		// 建物面積 坪
		outPutForm.setBuildingAreaSquare(isEmpty(inputForm.getBuildingArea()) ? "" : "（約" + PanaCalcUtil.calcTsubo(new BigDecimal(inputForm.getBuildingArea())).toString() + "坪）");

		// 建物面積_補足
		outPutForm.setBuildingAreaMemo(inputForm.getBuildingAreaMemo());

		// 土地面積
		outPutForm.setLandArea(defaultString(inputForm.getLandArea()));

		// 土地面積 坪
		outPutForm.setLandAreaSquare(isEmpty(inputForm.getLandArea()) ? "" : "（約" + PanaCalcUtil.calcTsubo(new BigDecimal(inputForm.getLandArea())).toString() + "坪）");

		// 土地面積_補足
		outPutForm.setLandAreaMemo(inputForm.getLandAreaMemo());

		// 専有面積
		outPutForm.setPersonalArea(defaultString(inputForm.getPersonalArea()));

		// 専有面積 坪
		outPutForm.setPersonalAreaSquare(isEmpty(inputForm.getPersonalArea()) ? "" : "(約" + PanaCalcUtil.calcTsubo(new BigDecimal(inputForm.getPersonalArea())).toString() + "坪)");

		// 専有面積_補足
		outPutForm.setPersonalAreaMemo(inputForm.getPersonalAreaMemo());

		if (!isEmpty(outPutForm.getPlanNoHidden())) {

			int cnt = outPutForm.getPlanNoHidden().length;

			for (int i = 0; i < cnt; i++) {

				StringBuilder sbTotalPrice1 = new StringBuilder();
				StringBuilder sbTotalPrice2 = new StringBuilder();

				// 総額１「配列」
				if (!isEmpty(inputForm.getPrice()) || !isEmpty(outPutForm.getPlanPrice()[i])) {
					sbTotalPrice1.append("約");
					sbTotalPrice1.append(formatPrice(sumPrice(inputForm.getPrice(), outPutForm.getPlanPrice()[i]), true));
					sbTotalPrice1.append("万円");
				}

				// 総額２「配列」
				sbTotalPrice2.append((isEmpty(outPutForm.getPrice())) ? "" : "物件価格：" + outPutForm.getPrice());
				sbTotalPrice2.append(((isEmpty(outPutForm.getPrice())) || (isEmpty(outPutForm.getPlanPrice()[i]))) ? "" : "＋");
				sbTotalPrice2.append((isEmpty(outPutForm.getPlanPrice()[i])) ? "" : "リフォーム価格：約" + formatPrice(outPutForm.getPlanPrice()[i], true) + "万円");

				// 総額１「配列」
				outPutForm.setTotalPrice1(setValue(i, outPutForm.getTotalPrice1(), sbTotalPrice1.toString()));

				// 総額２「配列」
				outPutForm.setTotalPrice2(setValue(i, outPutForm.getTotalPrice2(), sbTotalPrice2.toString()));

			}

			// その他のリフォームプランを設定する。
			outPutForm.setOtherReformPlan();
		}
	}

	/**
	 * 渡された物件詳細情報から 出力Form へプレビュー内容を設定する。<br/>
	 * <br/>
	 *
	 * @param inputForm 物件詳細情報Form オブジェクト
	 * @param outPutForm 出力用Form オブジェクト
	 *
	 * @exception Exception 実装クラスによりスローされる任意の例外
	 */
	public void setHousingDtlPreviewData(PanaHousingDtlInfoForm inputForm, PanaHousingPreview outPutForm) throws Exception {

		// 「物件種類CD」 ＝ 「01：マンション」の場合
		if (PanaCommonConstant.HOUSING_KIND_CD_MANSION.equals(getHousingKindCd())) {
			// バルコニー面積
			outPutForm.setBalconyArea(defaultString(inputForm.getBalconyArea()));

			// 管理形態
			outPutForm.setUpkeepType(inputForm.getUpkeepType());

			// 敷地権利
			outPutForm.setLandRight(inputForm.getLandRight());

			// 用途地域
			outPutForm.setUsedAreaCd(inputForm.getUsedAreaCd());

			// 引渡し
			outPutForm.setMoveinTiming(inputForm.getMoveinTiming());

			// 引渡時期コメント
			outPutForm.setMoveinNote(inputForm.getMoveinNote());

			// 取引形態
			outPutForm.setTransactTypeDiv(inputForm.getTransactTypeDiv());

			// 特記事項
			outPutForm.setSpecialInstruction(inputForm.getSpecialInstruction());

			// 備考
			outPutForm.setUpkeepCorp(isEmpty(inputForm.getUpkeepCorp()) ? "" : "管理会社：" + inputForm.getUpkeepCorp());

			// 管理費等
			outPutForm.setUpkeep(isEmpty(inputForm.getUpkeep()) ? "" : formatPrice(inputForm.getUpkeep(), false) + "円 &frasl; 月");

			// 修繕積立金
			outPutForm.setMenteFee(isEmpty(inputForm.getMenteFee()) ? "" : formatPrice(inputForm.getMenteFee(), false) + "円 &frasl; 月");

			// 駐車場
			outPutForm.setParkingSituation(inputForm.getDisplayParkingInfo());

			// 階建／所在階
			StringBuilder sbFloor = new StringBuilder();
			// 総階数
			sbFloor.append(isEmpty(inputForm.getTotalFloors()) ? "" : inputForm.getTotalFloors() + "階建");
			sbFloor.append((isEmpty(inputForm.getTotalFloors()) && isEmpty(inputForm.getFloorNo())) ? "" : "　&frasl;　");
			// 物件の階数
			sbFloor.append(isEmpty(inputForm.getFloorNo()) ? "" : inputForm.getFloorNo() + "階");
			// 階建／所在階
			outPutForm.setFloor(sbFloor.toString());

			// 主要採光
			outPutForm.setDirection(inputForm.getOrientedDataValue());

			// 構造
			outPutForm.setStruct(inputForm.getBuildingDataValue());

			// 総戸数
			outPutForm.setTotalHouseCnt(inputForm.getTotalHouseCntDataValue());

			// 規模
			outPutForm.setScale(inputForm.getScaleDataValue());

			// 現況
			outPutForm.setStatus(inputForm.getPreDataValue());

			// インフラ
			outPutForm.setInfrastructure(inputForm.getInfDataValue());

		} else {

			// 私道負担
			outPutForm.setPrivateRoad(inputForm.getPrivateRoad());

			// 土地権利
			outPutForm.setLandRight(inputForm.getLandRight());

			// 用途地域
			outPutForm.setUsedAreaCd(inputForm.getUsedAreaCd());

			// 引渡し
			outPutForm.setMoveinTiming(inputForm.getMoveinTiming());

			// 引渡時期コメント
			outPutForm.setMoveinNote(inputForm.getMoveinNote());

			// 取引形態
			outPutForm.setTransactTypeDiv(inputForm.getTransactTypeDiv());

			// 接道
			outPutForm.setContactRoad(inputForm.getContactRoad());

			// 接道方向/幅員
			outPutForm.setContactRoadDir(inputForm.getContactRoadDir());

			// 瑕疵保険
			outPutForm.setInsurExist(inputForm.getInsurExist());

			// 特記事項
			outPutForm.setSpecialInstruction(inputForm.getSpecialInstruction());

			// 備考
			outPutForm.setUpkeepCorp(isEmpty(inputForm.getUpkeepCorp()) ? "" : "管理会社：" + inputForm.getUpkeepCorp());

			// 駐車場
			outPutForm.setParkingSituation(inputForm.getDisplayParkingInfo());

			// 構造
			outPutForm.setStruct(inputForm.getBuildingDataValue());

			// 現況
			outPutForm.setStatus(inputForm.getPreDataValue());

			// インフラ
			outPutForm.setInfrastructure(inputForm.getInfDataValue());

			// 建ぺい率
			outPutForm.setCoverage(inputForm.getCoverageMemo());

			// 容積率
			outPutForm.setBuildingRate(inputForm.getBuildingRateMemo());
		}

		// おすすめ画像パス【hidden】
		if ("1".equals(inputForm.getPictureDataDelete())) {
			outPutForm.setStaffimagePathName(null);
		} else {
			outPutForm.setStaffimagePathName(inputForm.getPreviewImgPath());
		}

		// 担当
		outPutForm.setStaffName(inputForm.getWorkerDataValue());

		// 会社名
		outPutForm.setCompanyName(inputForm.getCompanyDataValue());

		// 支店名
		outPutForm.setBranchName(inputForm.getBranchDataValue());

		// 免許番号
		outPutForm.setLicenseNo(inputForm.getFreeCdDataValue());

		// おすすめ内容
		outPutForm.setRecommendComment(PanaStringUtils.encodeHtml(inputForm.getBasicComment()));

		// おすすめポイントの訴求エリア1
		outPutForm.setDtlComment(PanaStringUtils.encodeHtml(inputForm.getDtlComment()));

		// 売主のコメント
		outPutForm.setSalesComment(PanaStringUtils.encodeHtml(inputForm.getVendorComment()));

		// リフォームプラン準備中文言
		outPutForm.setReformPlanReadyComment(PanaStringUtils.encodeHtml(inputForm.getReformComment()));

		// 動画パス【hidden】
		outPutForm.setMovieUrl(inputForm.getUrlDataValue());
	}

	/**
	 * 渡された物件特長から 出力Form へプレビュー内容を設定する。<br/>
	 * <br/>
	 *
	 * @param inputForm 物件特長Form オブジェクト
	 * @param outPutForm 出力用Form オブジェクト
	 *
	 * @exception Exception 実装クラスによりスローされる任意の例外
	 */
	public void setHousingSpecialtyPreviewData(PanaHousingSpecialtyForm inputForm, PanaHousingPreview outPutForm) throws Exception {

		// 「物件種類CD」 ＝ 「03：土地」以外の場合
		if (!PanaCommonConstant.HOUSING_KIND_CD_GROUND.equals(getHousingKindCd())) {

			if (inputForm.getEquipCd() != null) {

				// 物件設備情報Map
				Map<String, String> equipMstMap = new HashMap<String, String>();

				int cnt = inputForm.getMstEquipCd().length;

				// 物件設備情報を繰り返す、物件設備情報Mapにプットする。
				for (int i = 0; i < cnt; i++) {
					equipMstMap.put(inputForm.getMstEquipCd()[i], inputForm.getEquipName()[i]);
				}

				StringBuilder sbName = new StringBuilder();

				// 設備CDを繰り返す、物件特徴を作成する。
				for (String equipCd : inputForm.getEquipCd()) {
					sbName.append(equipMstMap.get(equipCd)).append("/");
				}

				// 物件特徴
				outPutForm.setEquipName(sbName.toString().substring(0, sbName.toString().lastIndexOf("/")));

			} else {

				// 物件特徴
				outPutForm.setEquipName(null);
			}

		} else {

			// 物件特徴の表示フラグ
			outPutForm.setHousingPropertyDisplayFlg(false);
		}
	}

	/**
	 * 渡されたおすすめポイント情報から 出力Form へプレビュー内容を設定する。<br/>
	 * <br/>
	 *
	 * @param inputForm おすすめポイント情報Form オブジェクト
	 * @param outPutForm 出力用Form オブジェクト
	 *
	 * @exception Exception 実装クラスによりスローされる任意の例外
	 */
	public void setRecommendPointPreviewData(RecommendPointForm inputForm, PanaHousingPreview outPutForm) throws Exception {

		// アイコン情報【hidden】「配列」
		outPutForm.setIconCd(inputForm.getIcon());
	}

	/**
	 * 渡された住宅診断情報から 出力Form へプレビュー内容を設定する。<br/>
	 * <br/>
	 *
	 * @param inputForm 住宅診断情報Form オブジェクト
	 * @param outPutForm 出力用Form オブジェクト
	 *
	 * @exception Exception 実装クラスによりスローされる任意の例外
	 */
	public void setHousingInspectionPreviewData(PanaHousingInspectionForm inputForm, PanaHousingPreview outPutForm) throws Exception {

		String[] inspectionNo = new String[inputForm.getInspectionKey().length];

		for (int i = 0; i < inputForm.getInspectionKey().length; i++) {
			// 住宅診断【hidden】「配列」
			inspectionNo[i] = String.valueOf(i);
		}

		// 住宅診断実施有無
		outPutForm.setInspectionExist(inputForm.getHousingInspection());

		outPutForm.setInspectionNoHidden(inspectionNo);
		outPutForm.setInspectionKey(inputForm.getInspectionKey());
		outPutForm.setInspectionTrust(inputForm.getInspectionTrust_result());

		if ("1".equals(inputForm.getImgFlg())) {
			outPutForm.setInspectionImagePathName(inputForm.getHidNewImgPath());
		} else {
			outPutForm.setInspectionImagePathName(inputForm.getHidImgPath());
		}
		if ("1".equals(inputForm.getLoadFlg())) {
			outPutForm.setInspectionPathName(inputForm.getHidNewPath());
		} else {
			if ("on".equals(inputForm.getHousingDel())) {
				outPutForm.setInspectionPathName(null);
			} else {
				outPutForm.setInspectionPathName(inputForm.getHidPath());
			}
		}
	}

	/**
	 * リフォーム情報から 出力Form へプレビュー内容を設定する。<br/>
	 * <br/>
	 *
	 * @param inputForm リフォーム情報Form オブジェクト
	 * @param outPutForm 出力用Form オブジェクト
	 *
	 * @exception Exception 実装クラスによりスローされる任意の例外
	 */
	public void setReformPlanPreviewData(ReformInfoForm inputForm, PanaHousingPreview outPutForm) throws Exception {

		// 物件のリフォームプランの表示フラグ
		outPutForm.setReformPlanDisplayFlg(true);

		// リフォームプラン準備中文言の表示フラグ
		outPutForm.setReformPlanReadyDisplayFlg(false);

		StringBuilder sbReformPlan = new StringBuilder();
		StringBuilder sbTotalPrice1 = new StringBuilder();
		StringBuilder sbTotalPrice2 = new StringBuilder();
		StringBuilder sbReformCdHidden = new StringBuilder();
		StringBuilder sbReformCategory = new StringBuilder();

		// リフォームプラン情報
		sbReformCdHidden.append(inputForm.getSysReformCd());

		// プランタイプ「配列」
		sbReformPlan.append(defaultString(inputForm.getPlanName()));
		
		// category 1 array
		sbReformCategory.append(inputForm.getPlanCategory1());

		// 総額１「配列」
		if (!isEmpty(outPutForm.getPriceHidden()) || !isEmpty(inputForm.getPlanPrice())) {
			sbTotalPrice1.append("約");
			sbTotalPrice1.append(formatPrice(sumPrice(outPutForm.getPriceHidden(), inputForm.getPlanPrice()), true));
			sbTotalPrice1.append("万円");
		}

		// 総額２「配列」
		sbTotalPrice2.append((isEmpty(outPutForm.getPrice())) ? "" : "物件価格：" + outPutForm.getPrice());
		sbTotalPrice2.append(((isEmpty(outPutForm.getPrice())) || (isEmpty(inputForm.getPlanPrice()))) ? "" : "＋");
		sbTotalPrice2.append((isEmpty(inputForm.getPlanPrice())) ? "" : "リフォーム価格：約" + formatPrice(inputForm.getPlanPrice(), true) + "万円");

		// リフォームプラン情報がある場合
		if (!isEmpty(inputForm.getSysReformCd()) && !isEmpty(outPutForm.getPlanNoHidden())) {

			int cnt = outPutForm.getPlanNoHidden().length;

			for (int i = 0; i < cnt; i++) {

				if (inputForm.getSysReformCd().equals(outPutForm.getReformCdHidden()[i])) {

					// プランタイプ「配列」
					outPutForm.setPlanType(setValue(i, outPutForm.getPlanType(), sbReformPlan.toString()));

					// 総額１「配列」
					outPutForm.setTotalPrice1(setValue(i, outPutForm.getTotalPrice1(), sbTotalPrice1.toString()));
					outPutForm.setTotalDtlPrice1(sbTotalPrice1.toString());

					// 総額２「配列」
					outPutForm.setTotalPrice2(setValue(i, outPutForm.getTotalPrice2(), sbTotalPrice2.toString()));
					outPutForm.setTotalDtlPrice2(sbTotalPrice2.toString());
					
					// reform category 1
					outPutForm.setReformCategory(setValue(i, outPutForm.getReformCategory(), sbReformCategory.toString()));
				}
			}

		} else if (isEmpty(inputForm.getSysReformCd()) && !isEmpty(outPutForm.getPlanNoHidden())) {

			int cnt = outPutForm.getPlanNoHidden().length;

			// プラン番号【hidden】「配列」
			outPutForm.setPlanNoHidden(setValue(outPutForm.getPlanNoHidden(), String.valueOf(cnt)));

			// プランタイプ「配列」
			outPutForm.setPlanType(setValue(outPutForm.getPlanType(), sbReformPlan.toString()));

			// 総額１「配列」
			outPutForm.setTotalPrice1(setValue(outPutForm.getTotalPrice1(), sbTotalPrice1.toString()));
			outPutForm.setTotalDtlPrice1(sbTotalPrice1.toString());

			// 総額２「配列」
			outPutForm.setTotalPrice2(setValue(outPutForm.getTotalPrice2(), sbTotalPrice2.toString()));
			outPutForm.setTotalDtlPrice2(sbTotalPrice2.toString());
			
			outPutForm.setReformCategory(setValue(outPutForm.getReformCategory(), sbReformCategory.toString()));

		} else if (isEmpty(inputForm.getSysReformCd()) && isEmpty(outPutForm.getPlanNoHidden())) {

			// プラン番号【hidden】「配列」
			outPutForm.setPlanNoHidden(new String[] { "0" });

			// プランタイプ「配列」
			outPutForm.setPlanType(new String[] { sbReformPlan.toString() });

			// 総額１「配列」
			outPutForm.setTotalPrice1(new String[] { sbTotalPrice1.toString() });
			outPutForm.setTotalDtlPrice1(sbTotalPrice1.toString());

			// 総額２「配列」
			outPutForm.setTotalPrice2(new String[] { sbTotalPrice2.toString() });
			outPutForm.setTotalDtlPrice2(sbTotalPrice2.toString());
			
			// reform category 1
			outPutForm.setReformCategory(new String[] { sbReformCategory.toString()});

		}

		// リフォームプラン名
		outPutForm.setPlanName(inputForm.getPlanName());

		// セールスポイント
		outPutForm.setSalesPoint(PanaStringUtils.encodeHtml(inputForm.getSalesPoint()));

		// 工期
		outPutForm.setConstructionPeriod(inputForm.getConstructionPeriod());

		// 備考
		outPutForm.setReformNote(PanaStringUtils.encodeHtml(inputForm.getNote()));

		// リフォーム後_動画用サムネイル【hidden】「配列」
		outPutForm.setAfterMovieUrl(inputForm.getAfterMovieUrl());

		// リフォーム前_動画用サムネイル【hidden】「配列」
		outPutForm.setBeforeMovieUrl(inputForm.getBeforeMovieUrl());
	}

	/**
	 * 渡されたリフォーム詳細から 出力Form へプレビュー内容を設定する。<br/>
	 * <br/>
	 *
	 * @param inputForm リフォーム詳細Form オブジェクト
	 * @param outPutForm 出力用Form オブジェクト
	 *
	 * @exception Exception 実装クラスによりスローされる任意の例外
	 */
	public void setReformDtlPreviewData(ReformDtlForm inputForm,
			PanaHousingDetailed outPutForm) throws Exception {

		// リフォーム画像情報リストを取得
		List<ReformDtl> reformDtlList = this.convertReformDtlList(inputForm);

		// 画像がない場合
		if (reformDtlList == null || reformDtlList.size() == 0) {

			// おすすめリフォームプラン例の表示フラグ
			outPutForm.setRecommendReformPlanDisplayFlg(false);

			// 画像の表示フラグ
			outPutForm.setReformImgDisplayFlg(false);

			// リフォーム後_外観・周辺写真番号【hidden】「配列」
			outPutForm.setAfterPathNoHidden(null);

			// リフォーム後_外観・周辺写真パス１【hidden】「配列」
			outPutForm.setAfterPath1(null);

			// リフォーム後_外観・周辺写真パス２【hidden】「配列」
			outPutForm.setAfterPath2(null);

			// リフォーム後_外観・周辺写真 コメント【hidden】「配列」
			outPutForm.setAfterPathComment(null);

			// リフォーム前_外観・周辺写真番号【hidden】「配列」
			outPutForm.setBeforePathNoHidden(null);

			// リフォーム前_外観・周辺写真パス１【hidden】「配列」
			outPutForm.setBeforePath1(null);

			// リフォーム前_外観・周辺写真パス２【hidden】「配列」
			outPutForm.setBeforePath2(null);

			// リフォーム後_外観・周辺写真 コメント【hidden】「配列」
			outPutForm.setBeforePathComment(null);

			return;
		}

		outPutForm.setRecommendReformPlanDisplayFlg(true);
		// 画像の表示フラグ
		outPutForm.setReformImgDisplayFlg(true);

		int cnt = reformDtlList.size();
		String[] imageNo = new String[cnt];
		String[] imgpath = new String[cnt];
		String[] imgName = new String[cnt];
		String[] imgprice = new String[cnt];

		for (int i = 0; i < cnt; i++) {

			// 画像情報
			ReformDtl reformDtlSort = (ReformDtl) reformDtlList.get(i);

			// 画像番号【hidden】「配列」
			imageNo[i] = String.valueOf(i);

			// パス名１【hidden】「配列」
			imgpath[i] = reformDtlSort.getPathName();

			// コメント【hidden】「配列」
			imgName[i] = reformDtlSort.getImgName();

			// 価格【hidden】「配列」
			imgprice[i] = (reformDtlSort.getReformPrice() == null) ? "" : "約" + formatPrice(reformDtlSort.getReformPrice(), true) + "万円";
		}

		// 画像番号【hidden】「配列」
		outPutForm.setReformNoHidden(imageNo);

		// 項目名称【hidden】「配列」
		outPutForm.setReformImgName(imgName);

		// パス名【hidden】「配列」
		outPutForm.setReformPathName(imgpath);

		// 価格【hidden】「配列」
		outPutForm.setReformPrice(imgprice);

	}

	/**
	 * 渡されたリフォーム詳細情報をリフォーム詳細情報リストに変換する。<br/>
	 * <br/>
	 *
	 * @param inputForm リフォーム詳細Form オブジェクト
	 *
	 */
	private List<ReformDtl> convertReformDtlList(ReformDtlForm inputForm) {

		// リフォーム画像
		ArrayList<ReformDtl> list = new ArrayList<ReformDtl>();

		ReformDtl image = null;

		if (inputForm.getAddHidFileName() != null) {
			for (int i = 0; i < inputForm.getAddHidFileName().length; i++) {
				if (inputForm.getAddHidFileName()[i] != "") {

					image = new ReformDtl();

					if (StringUtils.isEmpty(inputForm.getAddHidPath()[i])) {
						continue;
					}

					// ファイル名
					image.setFileName(inputForm.getAddHidFileName()[i]);

					// コメント
					image.setImgName(inputForm.getAddImgName()[i]);

					// tempパス
					image.setPathName(inputForm.getAddHidPath()[i]);

					// リフォーム価格
					image.setReformPrice(Long.valueOf(inputForm.getAddReformPrice()[i]));

					// 表示順
					image.setSortOrder(Integer.parseInt(inputForm.getAddSortOrder()[i]));
					// 閲覧権限
					image.setRoleId(inputForm.getAddRoleId()[i]);

					// 枝番
					image.setDivNo(999);

					list.add(image);
				}
			}
		}

		if (inputForm.getUpdHidFileName() != null) {
			for (int i = 0; i < inputForm.getUpdHidFileName().length; i++) {

				// 削除した画像は非表示
				if ("1".equals(inputForm.getDelFlg()[i])) {
					continue;
				}

				image = new ReformDtl();

				// ファイル名
				image.setFileName(inputForm.getUpdHidFileName()[i]);

				// コメント
				image.setImgName(inputForm.getImgName()[i]);

				// tempパス（大きい画像）
				image.setPathName(inputForm.getHidPath()[i]);

				// リフォーム価格
				image.setReformPrice(Long.valueOf(inputForm.getReformPrice()[i]));
				// 表示順
				image.setSortOrder(Integer.parseInt(inputForm.getSortOrder()[i]));
				// 閲覧権限
				image.setRoleId(inputForm.getRoleId()[i]);

				// 枝番
				image.setDivNo(Integer.parseInt(inputForm.getDivNo()[i]));

				list.add(image);
			}
		}

		// リフォーム詳細画面情報の枝番を設定
		setRdDivNo(list);

		// 表示順、画像タイプ、枝番の昇順の条件に従いソートする
		Collections.sort(list, new ReformDtlComparator(false));

		return list;
	}

	/**
	 * 渡されたリフォーム詳細画面情報の枝番を設定する。<br/>
	 * <br/>
	 *
	 * @param list リフォーム画像情報リスト
	 *
	 */
	private void setRdDivNo(ArrayList<ReformDtl> list) {

		// 枝番設定用ソート、表示順、枝番の昇順の条件に従いソートする
		Collections.sort(list, new ReformDtlComparator(true));

		Integer divNo = 1;

		for (int i = 0; i < list.size(); i++) {

			if (i == 0) {
				divNo = 1;
			} else {
				divNo++;
			}

			list.get(i).setDivNo(divNo);
		}
	}

	/**
	 * 渡されたリフォーム画像から 出力Form へプレビュー内容を設定する。<br/>
	 * <br/>
	 *
	 * @param inputForm リフォーム画像Form オブジェクト
	 * @param outPutForm 出力用Form オブジェクト
	 *
	 * @exception Exception 実装クラスによりスローされる任意の例外
	 */
	public void setReformImgPreviewData(ReformImgForm inputForm,
			PanaHousingDetailed outPutForm) throws Exception {

		// リフォーム画像情報リストを取得
		List<ReformImg> reformImgList = this.convertReformImgList(inputForm);

		// 画像がない場合
		if (reformImgList == null || reformImgList.size() == 0) {

			// おすすめリフォームプラン例の表示フラグ
			outPutForm.setRecommendReformPlanDisplayFlg(false);

			// 画像の表示フラグ
			outPutForm.setReformImgDisplayFlg(false);

			// リフォーム詳細_番号【hidden】「配列」
			outPutForm.setReformNoHidden(null);

			// リフォーム詳細_項目名称「配列」
			outPutForm.setReformImgName(null);

			// リフォーム詳細_画像パス名【hidden】「配列」
			outPutForm.setReformPathName(null);

			// リフォーム詳細_項目リフォーム価格「配列」
			outPutForm.setReformPrice(null);

			return;
		}

		outPutForm.setRecommendReformPlanDisplayFlg(true);
		// 画像の表示フラグ
		outPutForm.setReformImgDisplayFlg(true);

		int cnt = reformImgList.size();
		String[] imageNo = new String[cnt];
		String[] pathBefore1 = new String[cnt];
		String[] pathBefore2 = new String[cnt];
		String[] pathAfter1 = new String[cnt];
		String[] pathAfter2 = new String[cnt];
		String[] imgCommentBefore = new String[cnt];
		String[] imgCommentAfter = new String[cnt];

		for (int i = 0; i < cnt; i++) {

			// 画像情報
			ReformImg reformImgSort = (ReformImg) reformImgList.get(i);

			// 画像番号【hidden】「配列」
			imageNo[i] = String.valueOf(i);

			// パス名１【hidden】「配列」
			pathBefore1[i] = reformImgSort.getBeforePathName().replace("/800/", "/200/");

			// パス名２【hidden】「配列」
			pathBefore2[i] = reformImgSort.getBeforePathName();

			// パス名１【hidden】「配列」
			pathAfter1[i] = reformImgSort.getAfterPathName().replace("/800/", "/200/");

			// パス名２【hidden】「配列」
			pathAfter2[i] = reformImgSort.getAfterPathName();

			// コメント【hidden】「配列」
			imgCommentBefore[i] = reformImgSort.getBeforeComment();
			// コメント【hidden】「配列」
			imgCommentAfter[i] = reformImgSort.getAfterComment();
		}

		// 画像番号【hidden】「配列」
		outPutForm.setBeforePathNoHidden(imageNo);
		outPutForm.setAfterPathNoHidden(imageNo);

		// パス名１【hidden】「配列」
		outPutForm.setBeforePath1(pathBefore1);

		// パス名２【hidden】「配列」
		outPutForm.setBeforePath2(pathBefore2);

		// パス名１【hidden】「配列」
		outPutForm.setAfterPath1(pathAfter1);

		// パス名２【hidden】「配列」
		outPutForm.setAfterPath2(pathAfter2);

		// コメント【hidden】「配列」
		outPutForm.setBeforePathComment(imgCommentBefore);
		// コメント【hidden】「配列」
		outPutForm.setAfterPathComment(imgCommentAfter);

	}

	/**
	 * 渡されたリフォーム画像情報をリフォーム画像情報リストに変換する。<br/>
	 * <br/>
	 *
	 * @param inputForm リフォーム画像Form オブジェクト
	 *
	 */
	private List<ReformImg> convertReformImgList(ReformImgForm inputForm) {

		// リフォーム画像
		ArrayList<ReformImg> list = new ArrayList<ReformImg>();

		ReformImg image = null;

		if (inputForm.getUploadBeforeFileName() != null && inputForm.getUploadAfterFileName() != null) {
			for (int i = 0; i < inputForm.getUploadBeforeFileName().length; i++) {
				image = new ReformImg();

				if (StringUtils.isEmpty(inputForm.getUploadAfterHidPath()[i]) || StringUtils.isEmpty(inputForm.getUploadBeforeHidPath()[i])) {
					continue;
				}

				// ファイル名
				image.setBeforeFileName(inputForm.getUploadBeforeFileName()[i]);
				image.setAfterFileName(inputForm.getUploadAfterFileName()[i]);

				// コメント
				image.setBeforeComment(inputForm.getUploadBeforeComment()[i]);
				image.setAfterComment(inputForm.getUploadAfterComment()[i]);

				// tempパス（大きい画像）
				image.setBeforePathName(inputForm.getUploadBeforeHidPath()[i]);

				// tempパス（大きい画像）
				image.setAfterPathName(inputForm.getUploadAfterHidPath()[i]);

				// 表示順
				image.setSortOrder(Integer.parseInt(inputForm.getUploadSortOrder()[i]));
				// 閲覧権限
				image.setRoleId(inputForm.getUploadRoleId()[i]);

				// 枝番
				image.setDivNo(999);

				list.add(image);
			}
		}

		if (inputForm.getEditBeforeFileName() != null && inputForm.getEditAfterFileName() != null) {
			for (int i = 0; i < inputForm.getEditBeforeFileName().length; i++) {

				// 削除した画像は非表示
				if ("1".equals(inputForm.getDelFlg()[i])) {
					continue;
				}

				image = new ReformImg();

				// ファイル名
				image.setBeforeFileName(inputForm.getEditBeforeFileName()[i]);
				image.setAfterFileName(inputForm.getEditAfterFileName()[i]);

				// コメント
				image.setBeforeComment(inputForm.getEditBeforeComment()[i]);
				image.setAfterComment(inputForm.getEditAfterComment()[i]);

				// tempパス（大きい画像）
				image.setBeforePathName(inputForm.getBeforeHidPathMax()[i]);

				// tempパス（大きい画像）
				image.setAfterPathName(inputForm.getAfterHidPathMax()[i]);
				// 表示順
				image.setSortOrder(Integer.parseInt(inputForm.getEditSortOrder()[i]));
				// 閲覧権限
				image.setRoleId(inputForm.getEditRoleId()[i]);

				// 枝番
				image.setDivNo(Integer.parseInt(inputForm.getDivNo()[i]));

				list.add(image);
			}
		}

		// リフォーム画像画面情報の枝番を設定
		setRiDivNo(list);

		// 表示順、画像タイプ、枝番の昇順の条件に従いソートする
		Collections.sort(list, new ReformImageComparator(false));

		return list;
	}

	/**
	 * 渡されたリフォーム画像画面情報の枝番を設定する。<br/>
	 * <br/>
	 *
	 * @param list リフォーム画像情報リスト
	 *
	 */
	private void setRiDivNo(ArrayList<ReformImg> list) {

		// 枝番設定用ソート、表示順、枝番の昇順の条件に従いソートする
		Collections.sort(list, new ReformImageComparator(true));

		Integer divNo = 1;

		for (int i = 0; i < list.size(); i++) {

			if (i == 0) {
				divNo = 1;
			} else {
				divNo++;
			}

			list.get(i).setDivNo(divNo);
		}
	}

	/**
	 * 渡された物件画像画面情報から 出力Form へプレビュー内容を設定する。<br/>
	 * <br/>
	 *
	 * @param inputForm 物件画像画面Form オブジェクト
	 * @param outPutForm 出力用Form オブジェクト
	 *
	 * @exception Exception 実装クラスによりスローされる任意の例外
	 */
	public void setHousingImagePreviewData(PanaHousingImageInfoForm inputForm,
			PanaHousingDetailed outPutForm) throws Exception {

		// 物件画像情報リストを取得
		List<HousingImageInfo> housingImageInfoList = this.convertHousingImageList(inputForm);

		// 画像がない場合
		if (housingImageInfoList == null || housingImageInfoList.size() == 0) {

			// 画像の表示フラグ
			outPutForm.setImgDisplayFlg(false);

			// 内観画像フラグ
			outPutForm.setIntrospectImgFlg(false);

			// 画像番号【hidden】「配列」
			outPutForm.setImgNoHidden(null);

			// パス名１【hidden】「配列」
			outPutForm.setHousingImgPath1Hidden(null);

			// パス名２【hidden】「配列」
			outPutForm.setHousingImgPath2Hidden(null);

			// コメント【hidden】「配列」
			outPutForm.setHousingImgCommentHidden(null);

			return;
		}

		// 画像の表示フラグ
		outPutForm.setImgDisplayFlg(true);
		// 内観画像フラグ
		outPutForm.setIntrospectImgFlg(true);

		int cnt = housingImageInfoList.size();
		String[] imageNo = new String[cnt];
		String[] imageType = new String[cnt];
		String[] path1 = new String[cnt];
		String[] path2 = new String[cnt];
		String[] imgComment = new String[cnt];

		for (int i = 0; i < cnt; i++) {

			// 画像情報
			HousingImageInfo housingImageInfo = (HousingImageInfo) housingImageInfoList.get(i);

			// 画像番号【hidden】「配列」
			imageNo[i] = String.valueOf(i);

			// パス名１【hidden】「配列」
			path1[i] = housingImageInfo.getPathName().replace("/800/", "/200/");

			// パス名２【hidden】「配列」
			path2[i] = housingImageInfo.getPathName();

			// コメント【hidden】「配列」
			imgComment[i] = housingImageInfo.getImgComment();

			// 画像タイプ【hidden】「配列」
			imageType[i] = housingImageInfo.getImageType();
		}

		// 画像番号【hidden】「配列」
		outPutForm.setImgNoHidden(imageNo);

		// パス名１【hidden】「配列」
		outPutForm.setHousingImgPath1Hidden(path1);

		// パス名２【hidden】「配列」
		outPutForm.setHousingImgPath2Hidden(path2);

		// コメント【hidden】「配列」
		outPutForm.setHousingImgCommentHidden(imgComment);

	}

	/**
	 * 渡された物件画像画面情報を物件画像情報リストに変換する。<br/>
	 * <br/>
	 *
	 * @param inputForm 物件画像画面Form オブジェクト
	 *
	 */
	private List<HousingImageInfo> convertHousingImageList(PanaHousingImageInfoForm inputForm) {

		// 物件画像
		ArrayList<HousingImageInfo> list = new ArrayList<HousingImageInfo>();

		HousingImageInfo image = null;

		if (inputForm.getAddHidFileName() != null) {
			for (int i = 0; i < inputForm.getAddHidFileName().length; i++) {
				if (inputForm.getAddHidFileName()[i] != "") {

					image = new HousingImageInfo();

					if (StringUtils.isEmpty(inputForm.getAddHidPath()[i])) {
						continue;
					}

					// ファイル名
					image.setFileName(inputForm.getAddHidFileName()[i]);
					// 表示順
					image.setSortOrder(Integer.parseInt(inputForm.getAddSortOrder()[i]));
					// 閲覧権限
					image.setRoleId(inputForm.getAddRoleId()[i]);
					// 画像タイプ
					image.setImageType(inputForm.getAddImageType()[i]);
					// コメント
					image.setImgComment(inputForm.getAddImgComment()[i]);
					// tempパス（大きい画像）
					image.setPathName(inputForm.getAddHidPath()[i]);

					// 枝番
					image.setDivNo(999);

					list.add(image);
				}
			}
		}

		if (inputForm.getFileName() != null) {
			for (int i = 0; i < inputForm.getFileName().length; i++) {

				// 削除した画像は非表示
				if ("1".equals(inputForm.getDelFlg()[i])) {
					continue;
				}

				image = new HousingImageInfo();

				// ファイル名
				image.setFileName(inputForm.getFileName()[i]);
				// 表示順
				image.setSortOrder(Integer.parseInt(inputForm.getSortOrder()[i]));
				// 閲覧権限
				image.setRoleId(inputForm.getRoleId()[i]);
				// 画像タイプ
				image.setImageType(inputForm.getImageType()[i]);
				// コメント
				image.setImgComment(inputForm.getImgComment()[i]);
				// tempパス（大きい画像）
				image.setPathName(inputForm.getHidPathMax()[i]);

				// 枝番
				image.setDivNo(Integer.parseInt(inputForm.getDivNo()[i]));

				list.add(image);
			}
		}

		// 物件画像画面情報の枝番を設定
		setDivNo(list);

		// 表示順、画像タイプ、枝番の昇順の条件に従いソートする
		Collections.sort(list, new HousingImageComparator(false));

		return list;
	}

	/**
	 * 渡された物件画像画面情報の枝番を設定する。<br/>
	 * <br/>
	 *
	 * @param list 物件画像情報リスト
	 *
	 */
	private void setDivNo(ArrayList<HousingImageInfo> list) {

		// 枝番設定用ソート、画像タイプ、表示順、枝番の昇順の条件に従いソートする
		Collections.sort(list, new HousingImageComparator(true));

		String tempImageType = null;
		Integer divNo = 1;

		for (int i = 0; i < list.size(); i++) {

			if (tempImageType == null) {

				list.get(i).setDivNo(divNo);
				tempImageType = list.get(i).getImageType();
				continue;
			}

			// 画像種別が同じ場合
			if (tempImageType.equals(list.get(i).getImageType())) {

				divNo++;
				// 上記以外の場合
			} else {
				divNo = 1;
				tempImageType = list.get(i).getImageType();
			}

			list.get(i).setDivNo(divNo);

		}
	}

	/**
	 * 渡された地域情報から 出力Form へプレビュー内容を設定する。<br/>
	 * <br/>
	 *
	 * @param inputForm 地域情報Form オブジェクト
	 * @param outPutForm 出力用Form オブジェクト
	 *
	 * @exception Exception 実装クラスによりスローされる任意の例外
	 */
	public void setHousingLandmarkPreviewData(BuildingLandmarkForm inputForm, PanaHousingPreview outPutForm) throws Exception {

		if (inputForm.getLandmarkType() != null) {

			int cnt = inputForm.getLandmarkType().length;

			String[] landmarkNo = new String[cnt];
			String[] landmarkType = new String[cnt];
			String[] landmarkName = new String[cnt];
			String[] distanceFromLandmark = new String[cnt];

			int j = 0;

			for (int i = 0; i < cnt; i++) {

				StringBuilder sbLandmark = new StringBuilder();

				// 地域情報番号【hidden】「配列」
				landmarkNo[i] = String.valueOf(i);

				// ランドマークの種類「配列」
				landmarkType[i] = inputForm.getLandmarkType()[i];

				// 地域情報（名称）「配列」
				landmarkName[i] = inputForm.getLandmarkName()[i];

				if (!isEmpty(inputForm.getLandmarkName()[i])) {
					j++;
				}

				// 地域情報（所要時間/距離）「配列」
				if (!isEmpty(inputForm.getDistanceFromLandmark()[i])) {
					sbLandmark.append("徒歩");
					sbLandmark.append(inputForm.getTimeFromLandmark()[i]);
					sbLandmark.append("分（");
					sbLandmark.append(inputForm.getDistanceFromLandmark()[i]);
					sbLandmark.append("m）");
				}

				// ランドマークからの徒歩時間と距離
				distanceFromLandmark[i] = sbLandmark.toString();
			}

			if (j > 0) {

				// 地域情報番号【hidden】「配列」
				outPutForm.setLandmarkNoHidden(landmarkNo);

				// ランドマークの種類「配列」
				outPutForm.setLandmarkType(landmarkType);

				// 地域情報（名称）「配列」
				outPutForm.setLandmarkName(landmarkName);

				// 地域情報（所要時間/距離）「配列」
				outPutForm.setDistanceFromLandmark(distanceFromLandmark);

			} else {

				// 地域情報番号【hidden】「配列」
				outPutForm.setLandmarkNoHidden(null);

				// ランドマークの種類「配列」
				outPutForm.setLandmarkType(null);

				// 地域情報（名称）「配列」
				outPutForm.setLandmarkName(null);

				// 地域情報（所要時間/距離）「配列」
				outPutForm.setDistanceFromLandmark(null);
			}
		}
	}

	/**
	 * プレビューの新着フラグと更新日を設定する。<br/>
	 * <br/>
	 *
	 * @param outPutForm 出力用Form オブジェクト
	 *
	 * @exception Exception 実装クラスによりスローされる任意の例外
	 */
	public void setFreshAndUpdDate(PanaHousingPreview outPutForm) throws Exception {
		// 新着フラグ
		outPutForm.setFreshFlg(true);

		Date nowDate = new Date();

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");

		// 更新日
		outPutForm.setUpdDate(simpleDateFormat.format(nowDate));

		// 次回更新予定
		outPutForm.setNextUpdDate("（次回更新予定 ：" + simpleDateFormat.format(dateAdd(nowDate)) + "）");
	}

	private String[] setValue(int i, String[] str1, String str2) {
		if (str1 == null) {
			return null;
		}
		int cnt = str1.length;
		String[] strTemp = str1.clone();
		for (int j = 0; j < cnt; j++) {
			if (i == j) {
				strTemp[j] = str2;
			}
		}
		return strTemp;
	}

	private String[] setValue(String[] str1, String str2) {
		if (str1 == null) {
			return null;
		}
		int cnt = str1.length + 1;
		String[] strTemp = new String[cnt];
		for (int i = 0; i < cnt; i++) {
			if (i == cnt - 1) {
				strTemp[i] = str2;
			} else {
				strTemp[i] = str1[i];
			}
		}
		return strTemp;
	}
}
