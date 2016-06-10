package jp.co.transcosmos.dm3.corePana.model.assessment.form;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jp.co.transcosmos.dm3.core.constant.CommonParameters;
import jp.co.transcosmos.dm3.core.model.inquiry.form.HousingInquiryForm;
import jp.co.transcosmos.dm3.core.util.LengthValidationUtils;
import jp.co.transcosmos.dm3.core.validation.ZenkakuKanaValidator;
import jp.co.transcosmos.dm3.core.vo.PrefMst;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonConstant;
import jp.co.transcosmos.dm3.corePana.model.inquiry.form.PanaInquiryHeaderForm;
import jp.co.transcosmos.dm3.corePana.util.PanaStringUtils;
import jp.co.transcosmos.dm3.corePana.validation.PonitNumberValidation;
import jp.co.transcosmos.dm3.corePana.vo.InquiryAssessment;
import jp.co.transcosmos.dm3.corePana.vo.InquiryHousingQuestion;
import jp.co.transcosmos.dm3.corePana.vo.MemberInfo;
import jp.co.transcosmos.dm3.dao.JoinResult;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;
import jp.co.transcosmos.dm3.validation.CodeLookupValidation;
import jp.co.transcosmos.dm3.validation.EmailRFCValidation;
import jp.co.transcosmos.dm3.validation.LengthValidator;
import jp.co.transcosmos.dm3.validation.MaxLengthValidation;
import jp.co.transcosmos.dm3.validation.NullOrEmptyCheckValidation;
import jp.co.transcosmos.dm3.validation.NumberValidation;
import jp.co.transcosmos.dm3.validation.NumericValidation;
import jp.co.transcosmos.dm3.validation.Validateable;
import jp.co.transcosmos.dm3.validation.ValidationChain;
import jp.co.transcosmos.dm3.validation.ValidationFailure;
import jp.co.transcosmos.dm3.validation.ZenkakuOnlyValidation;

/**
 * 査定のお申し込みの入力内容受取り用フォーム.
 * <p>
 * <pre>
 * 担当者	   修正日	  修正内容
 * ------------ ----------- -----------------------------------------------------
 * チョ夢		2015.04.28	新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 *
 */
public class AssessmentInputForm extends HousingInquiryForm implements Validateable {

	/** 共通パラメータオブジェクト */
	protected CommonParameters commonParameters;
	/** レングスバリデーションで使用する文字列長を取得するユーティリティ */
	protected LengthValidationUtils lengthUtils;
	/** 共通コード変換処理 */
	protected CodeLookupManager codeLookupManager;

	/**
	 * デフォルトコンストラクター<br/>
	 * Factory 以外からのインスタンス生成を制限する為、コンストラクターを隠蔽する。<br/>
	 * <br/>
	 *
	 */
	protected AssessmentInputForm(){
		super();
	}



	/**
	 * コンストラクター<br/>
	 * Factory 以外からのインスタンス生成を制限する為、コンストラクターを隠蔽する。<br/>
	 * <br/>
	 * @param lengthUtils レングスバリデーションで使用する文字列長を取得するユーティリティ
	 * @param commonParameters 共通パラメータオブジェクト
	 * @param codeLookupManager 共通コード変換処理
	 *
	 */
	protected AssessmentInputForm(LengthValidationUtils lengthUtils,
							 CommonParameters commonParameters,
							 CodeLookupManager codeLookupManager){
		super();
		this.lengthUtils = lengthUtils;
		this.commonParameters = commonParameters;
		this.codeLookupManager = codeLookupManager;
	}

	/** command パラメータ */
	private String command;

	/** 売却物件の種別 */
	private String buyHousingType;
	/** 間取CD(マンション) */
	private String layoutCd1;
	/** 専有面積 */
	private String personalArea;
	/** 築年数(マンション) */
	private String buildAge1;
	/** 間取CD(戸建) */
	private String layoutCd2;
	/** 築年数(戸建) */
	private String buildAge2;
	/** 土地面積(戸建) */
	private String landArea2;
	/** 土地面積単位(戸建) */
	private String landAreaCrs2;
	/** 建物面積(戸建) */
	private String buildingArea;
	/** 建物面積単位(戸建) */
	private String buildingAreaCrs;
	/** 土地面積(土地) */
	private String landArea3;
	/** 土地面積単位(土地) */
	private String landAreaCrs3;
	/** 売却物件・郵便番号 */
	private String zip;
	/** 売却物件・都道府県CD */
	private String prefCd;
	/** 売却物件・都道府県名 */
	private String prefName;
	/** 売却物件・市区町村番地 */
	private String address;
	/** 売却物件・建物名 */
	private String addressOther;
	/** 現況 */
	private String presentCd;
	/** 売却予定時期 */
	private String buyTimeCd;
	/** 買い替えの有無 */
	private String replacementFlg;
	/** 要望・質問 */
	private String requestText;
	/** 要望・質問（表示用） */
	private String requestTextHyoji;
	/** 連絡方法 */
	private String contactType;
	/** 連絡可能な時間帯 */
	private String[] contactTime;
	/** 売却物件と同じ */
	private String sameWithHousing;
	/** 問合者住所・郵便番号 */
	private String userZip;
	/** 問合者住所・都道府県CD */
	private String userPrefCd;
	/** 問合者住所・都道府県名 */
	private String userPrefName;
	/** 問合者住所・市区町村番地 */
	private String userAddress;
	/** 問合者住所・建物名 */
	private String userAddressOther;
	/** アンケート回答格納 */
	private String[] ansCd;
	/** アンケート回答格納（入力１） */
	private String etcAnswer1;
	/** アンケート回答格納（入力２） */
	private String etcAnswer2;
	/** アンケート回答格納（入力３） */
	private String etcAnswer3;



	/**
	 * @return command
	 */
	public String getCommand() {
		return command;
	}

	/**
	 * @param command セットする command
	 */
	public void setCommand(String command) {
		this.command = command;
	}

	/**
	 * @return buyHousingType
	 */
	public String getBuyHousingType() {
		return buyHousingType;
	}
	/**
	 * @param buyHousingType セットする buyHousingType
	 */
	public void setBuyHousingType(String buyHousingType) {
		this.buyHousingType = buyHousingType;
	}
	/**
	 * @return layoutCd1
	 */
	public String getLayoutCd1() {
		return layoutCd1;
	}
	/**
	 * @param layoutCd1 セットする layoutCd1
	 */
	public void setLayoutCd1(String layoutCd1) {
		this.layoutCd1 = layoutCd1;
	}
	/**
	 * @return personalArea
	 */
	public String getPersonalArea() {
		return personalArea;
	}
	/**
	 * @param personalArea セットする personalArea
	 */
	public void setPersonalArea(String personalArea) {
		this.personalArea = personalArea;
	}
	/**
	 * @return buildAge1
	 */
	public String getBuildAge1() {
		return buildAge1;
	}
	/**
	 * @param buildAge1 セットする buildAge1
	 */
	public void setBuildAge1(String buildAge1) {
		this.buildAge1 = buildAge1;
	}
	/**
	 * @return layoutCd2
	 */
	public String getLayoutCd2() {
		return layoutCd2;
	}
	/**
	 * @param layoutCd2 セットする layoutCd2
	 */
	public void setLayoutCd2(String layoutCd2) {
		this.layoutCd2 = layoutCd2;
	}
	/**
	 * @return buildAge2
	 */
	public String getBuildAge2() {
		return buildAge2;
	}
	/**
	 * @param buildAge2 セットする buildAge2
	 */
	public void setBuildAge2(String buildAge2) {
		this.buildAge2 = buildAge2;
	}
	/**
	 * @return landArea2
	 */
	public String getLandArea2() {
		return landArea2;
	}
	/**
	 * @param landArea2 セットする landArea2
	 */
	public void setLandArea2(String landArea2) {
		this.landArea2 = landArea2;
	}
	/**
	 * @return landAreaCrs2
	 */
	public String getLandAreaCrs2() {
		return landAreaCrs2;
	}
	/**
	 * @param landAreaCrs2 セットする landAreaCrs2
	 */
	public void setLandAreaCrs2(String landAreaCrs2) {
		this.landAreaCrs2 = landAreaCrs2;
	}
	/**
	 * @return buildingArea
	 */
	public String getBuildingArea() {
		return buildingArea;
	}
	/**
	 * @param buildingArea セットする buildingArea
	 */
	public void setBuildingArea(String buildingArea) {
		this.buildingArea = buildingArea;
	}
	/**
	 * @return buildingAreaCrs
	 */
	public String getBuildingAreaCrs() {
		return buildingAreaCrs;
	}
	/**
	 * @param buildingAreaCrs セットする buildingAreaCrs
	 */
	public void setBuildingAreaCrs(String buildingAreaCrs) {
		this.buildingAreaCrs = buildingAreaCrs;
	}
	/**
	 * @return landArea3
	 */
	public String getLandArea3() {
		return landArea3;
	}
	/**
	 * @param landArea3 セットする landArea3
	 */
	public void setLandArea3(String landArea3) {
		this.landArea3 = landArea3;
	}
	/**
	 * @return landAreaCrs3
	 */
	public String getLandAreaCrs3() {
		return landAreaCrs3;
	}
	/**
	 * @param landAreaCrs3 セットする landAreaCrs3
	 */
	public void setLandAreaCrs3(String landAreaCrs3) {
		this.landAreaCrs3 = landAreaCrs3;
	}
	/**
	 * @return zip
	 */
	public String getZip() {
		return zip;
	}
	/**
	 * @param zip セットする zip
	 */
	public void setZip(String zip) {
		this.zip = zip;
	}
	/**
	 * @return prefCd
	 */
	public String getPrefCd() {
		return prefCd;
	}
	/**
	 * @param prefCd セットする prefCd
	 */
	public void setPrefCd(String prefCd) {
		this.prefCd = prefCd;
	}
	/**
	 * @return prefName
	 */
	public String getPrefName() {
		return prefName;
	}
	/**
	 * @param prefName セットする prefName
	 */
	public void setPrefName(String prefName) {
		this.prefName = prefName;
	}
	/**
	 * @return address
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * @param address セットする address
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * @return addressOther
	 */
	public String getAddressOther() {
		return addressOther;
	}
	/**
	 * @param addressOther セットする addressOther
	 */
	public void setAddressOther(String addressOther) {
		this.addressOther = addressOther;
	}
	/**
	 * @return presentCd
	 */
	public String getPresentCd() {
		return presentCd;
	}
	/**
	 * @param presentCd セットする presentCd
	 */
	public void setPresentCd(String presentCd) {
		this.presentCd = presentCd;
	}
	/**
	 * @return buyTimeCd
	 */
	public String getBuyTimeCd() {
		return buyTimeCd;
	}
	/**
	 * @param buyTimeCd セットする buyTimeCd
	 */
	public void setBuyTimeCd(String buyTimeCd) {
		this.buyTimeCd = buyTimeCd;
	}
	/**
	 * @return replacementFlg
	 */
	public String getReplacementFlg() {
		return replacementFlg;
	}
	/**
	 * @param replacementFlg セットする replacementFlg
	 */
	public void setReplacementFlg(String replacementFlg) {
		this.replacementFlg = replacementFlg;
	}
	/**
	 * @return requestText
	 */
	public String getRequestText() {
		return requestText;
	}
	/**
	 * @param requestText セットする requestText
	 */
	public void setRequestText(String requestText) {
		this.requestText = requestText;
	}
	/**
	 * @return requestTextHyoji
	 */
	public String getRequestTextHyoji() {
		return requestTextHyoji;
	}
	/**
	 * @param requestTextHyoji セットする requestTextHyoji
	 */
	public void setRequestTextHyoji(String requestTextHyoji) {
		this.requestTextHyoji = requestTextHyoji;
	}
	/**
	 * @return contactType
	 */
	public String getContactType() {
		return contactType;
	}
	/**
	 * @param contactType セットする contactType
	 */
	public void setContactType(String contactType) {
		this.contactType = contactType;
	}
	/**
	 * @return contactTime
	 */
	public String[] getContactTime() {
		return contactTime;
	}
	/**
	 * @param contactTime セットする contactTime
	 */
	public void setContactTime(String[] contactTime) {
		this.contactTime = contactTime;
	}
	/**
	 * @return sameWithHousing
	 */
	public String getSameWithHousing() {
		return sameWithHousing;
	}
	/**
	 * @param sameWithHousing セットする sameWithHousing
	 */
	public void setSameWithHousing(String sameWithHousing) {
		this.sameWithHousing = sameWithHousing;
	}
	/**
	 * @return userZip
	 */
	public String getUserZip() {
		return userZip;
	}
	/**
	 * @param userZip セットする userZip
	 */
	public void setUserZip(String userZip) {
		this.userZip = userZip;
	}
	/**
	 * @return userPrefCd
	 */
	public String getUserPrefCd() {
		return userPrefCd;
	}
	/**
	 * @param userPrefCd セットする userPrefCd
	 */
	public void setUserPrefCd(String userPrefCd) {
		this.userPrefCd = userPrefCd;
	}
	/**
	 * @return userPrefName
	 */
	public String getUserPrefName() {
		return userPrefName;
	}
	/**
	 * @param userPrefName セットする userPrefName
	 */
	public void setUserPrefName(String userPrefName) {
		this.userPrefName = userPrefName;
	}
	/**
	 * @return userAddress
	 */
	public String getUserAddress() {
		return userAddress;
	}
	/**
	 * @param userAddress セットする userAddress
	 */
	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}
	/**
	 * @return userAddressOther
	 */
	public String getUserAddressOther() {
		return userAddressOther;
	}
	/**
	 * @param userAddressOther セットする userAddressOther
	 */
	public void setUserAddressOther(String userAddressOther) {
		this.userAddressOther = userAddressOther;
	}
	/**
	 * @return ansCd
	 */
	public String[] getAnsCd() {
		return ansCd;
	}
	/**
	 * @param ansCd セットする ansCd
	 */
	public void setAnsCd(String[] ansCd) {
		this.ansCd = ansCd;
	}
	/**
	 * @return etcAnswer1
	 */
	public String getEtcAnswer1() {
		return etcAnswer1;
	}
	/**
	 * @param etcAnswer1 セットする etcAnswer1
	 */
	public void setEtcAnswer1(String etcAnswer1) {
		this.etcAnswer1 = etcAnswer1;
	}
	/**
	 * @return etcAnswer2
	 */
	public String getEtcAnswer2() {
		return etcAnswer2;
	}
	/**
	 * @param etcAnswer2 セットする etcAnswer2
	 */
	public void setEtcAnswer2(String etcAnswer2) {
		this.etcAnswer2 = etcAnswer2;
	}
	/**
	 * @return etcAnswer3
	 */
	public String getEtcAnswer3() {
		return etcAnswer3;
	}
	/**
	 * @param etcAnswer3 セットする etcAnswer3
	 */
	public void setEtcAnswer3(String etcAnswer3) {
		this.etcAnswer3 = etcAnswer3;
	}



	/**
	 *渡されたバリーオブジェクトから Form へ初期値を設定する。<br/>
	 * <br/>
	 */
	public void setDefaultData(JoinResult userInfo) {

		// マイページ会員情報を取得
		if(userInfo != null){
			MemberInfo memberInfo = (MemberInfo)userInfo.getItems().get("memberInfo");
			PrefMst prefMst_member = (PrefMst)userInfo.getItems().get("prefMst");
			if(memberInfo != null){
				// お名前_姓
				this.getInquiryHeaderForm().setLname(memberInfo.getMemberLname());
				// お名前_名
				this.getInquiryHeaderForm().setFname(memberInfo.getMemberFname());
				// お名前（フリガナ）_姓
				this.getInquiryHeaderForm().setLnameKana(memberInfo.getMemberLnameKana());
				// お名前（フリガナ）_名
				this.getInquiryHeaderForm().setFnameKana(memberInfo.getMemberFnameKana());
				// メールアドレス
				this.getInquiryHeaderForm().setEmail(memberInfo.getEmail());
				// 電話番号
				this.getInquiryHeaderForm().setTel(memberInfo.getTel());
				// FAX番号
				((PanaInquiryHeaderForm)this.getInquiryHeaderForm()).setFax(memberInfo.getFax());
				// 郵便番号
				this.setUserZip(memberInfo.getZip());
				// 都道府県CD
				this.setUserPrefCd(memberInfo.getPrefCd());
				// 都道府県名
				if(!StringValidateUtil.isEmpty(prefMst_member.getPrefName())){
					this.setUserPrefName(prefMst_member.getPrefName());
				}
				// 市区町村番地
				this.setUserAddress(memberInfo.getAddress());
				// 建物名
				this.setUserAddressOther(memberInfo.getAddressOther());
			}
		}
	}


	/**
	 * Form 値の自動変換をする。<br/>
	 * <br/>
	 */
	public void setAutoChange() {

		// 全角数字から半角数字への自動変換
		this.setZip(PanaStringUtils.changeToHankakuNumber(this.zip));
		this.setUserZip(PanaStringUtils.changeToHankakuNumber(this.userZip));

		// InquiryHeaderFormの自動変換
		if (this.getInquiryHeaderForm() != null) {
			PanaInquiryHeaderForm panaInquiryHeaderForm = (PanaInquiryHeaderForm)this.getInquiryHeaderForm();

			if (StringValidateUtil.isEmpty(this.sameWithHousing) || !"1".equals(this.sameWithHousing)) {

				panaInquiryHeaderForm.setZip(this.userZip);
				panaInquiryHeaderForm.setPrefCd(this.userPrefCd);
				panaInquiryHeaderForm.setAddress(this.userAddress);
				panaInquiryHeaderForm.setAddressOther(this.userAddressOther);

			}

			// 全角数字から半角数字への自動変換
			panaInquiryHeaderForm.setTel(PanaStringUtils.changeToHankakuNumber(panaInquiryHeaderForm.getTel()));
			panaInquiryHeaderForm.setFax(PanaStringUtils.changeToHankakuNumber(panaInquiryHeaderForm.getFax()));
			panaInquiryHeaderForm.setEmail(PanaStringUtils.changeToHankakuNumber(panaInquiryHeaderForm.getEmail()));
			panaInquiryHeaderForm.setZip(PanaStringUtils.changeToHankakuNumber(panaInquiryHeaderForm.getZip()));
		}
	}



	/**
	 * バリデーション処理<br/>
	 * リクエストパラメータのバリデーションを行う<br/>
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 * @return 正常時 true、エラー時 false
	 */
	@Override
	public boolean validate(List<ValidationFailure> errors) {

		int startSize = errors.size();

		// 売却物件の種別チェック
		validBuyHousingType(errors);
		// 間取CDチェック
		validLayoutCd(errors);
		// 専有面積チェック
		validPersonalArea(errors);
		// 築年数チェック
		validBuildAge(errors);
		// 土地面積チェック
		validLandArea(errors);
		// 土地面積単位チェック
		validLandAreaCrs(errors);
		// 建物面積チェック
		validBuildingArea(errors);
		// 建物面積単位チェック
		validBuildingAreaCrs(errors);
		// 郵便番号チェック
		validZip(errors);
		// 都道府県チェック
		validPrefCd(errors);
		// 市区町村番地チェック
		validAddress(errors);
		// 建物名チェック
		validAddressOther(errors);
		// 現況チェック
		validPresentCd(errors);
		// 売却予定時期チェック
		validBuyTimeCd(errors);
		// 買い替えの有無チェック
		validReplacementFlg(errors);
		// 要望・質問チェック
		validRequestText(errors);
		// 氏名(姓)チェック
		validLname(errors);
		// 氏名(名)チェック
		validFname(errors);
		// 氏名・カナ(姓)チェック
		validLnameKana(errors);
		// 氏名・カナ(名)チェック
		validFnameKana(errors);
		// メールアドレスチェック
		validEmail(errors);
		// 電話番号チェック
		validTel(errors);
		// FAX番号チェック
		validFax(errors);
		// 連絡方法チェック
		validContactType(errors);
		// 連絡可能な時間帯チェック
		validContactTime(errors);
		// 問合者住所・郵便番号チェック
		validUserZip(errors);
		// 問合者住所・都道府県チェック
		validUserPrefCd(errors);
		// 問合者住所・市区町村番地チェック
		validUserAddress(errors);
		// 問合者住所・建物名チェック
		validUserAddressOther(errors);
		// アンケート回答チェック
		validAnsCd(errors);
		// アンケート_その他回答１チェック
		validEtcAnswer1(errors);
		// アンケート_その他回答２チェック
		validEtcAnswer2(errors);
		// アンケート_その他回答３チェック
		validEtcAnswer3(errors);
		// アンケートチェックボックスとアンケート内容の制御チェック
		validAnsCdAndEtcAnswer(errors);


		return (startSize == errors.size());
	}

	/**
     * アンケートチェックボックスとアンケート内容の制御チェック<br/>
     * ・関連チェック
     * <br/>
     * @param errors エラー情報を格納するリストオブジェクト
     */
	protected void validAnsCdAndEtcAnswer(List<ValidationFailure> errors) {
        // アンケートチェックボックスとアンケート内容の制御チェック
        List<String> list = new ArrayList<String>();
        if (this.getAnsCd() != null) {
        	list =  Arrays.asList(this.getAnsCd());
        }
        if (!StringValidateUtil.isEmpty(this.getEtcAnswer1()) && !list.contains("008")) {
        	errors.add(new ValidationFailure("mustInput1", "アンケート内容1",  "パナソニックショップ", null));
        }
        if (!StringValidateUtil.isEmpty(this.getEtcAnswer2()) && !list.contains("009")) {
        	errors.add(new ValidationFailure("mustInput1", "アンケート内容2",  "その他", null));
        }
        if (!StringValidateUtil.isEmpty(this.getEtcAnswer3()) && !list.contains("010")) {
        	errors.add(new ValidationFailure("mustInput1", "アンケート内容3",  "プロモコード", null));
        }
	}
	/**
	 * 売却物件の種別 バリデーション<br/>
	 * ・必須チェック
	 * ・パターン入力チェック
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	protected void validBuyHousingType(List<ValidationFailure> errors) {
		String label = "assessment.input.buyHousingType";
		ValidationChain valid = new ValidationChain(label,this.buyHousingType);

		// 必須チェック
		valid.addValidation(new NullOrEmptyCheckValidation());
		// パターン入力チェック
		valid.addValidation(new CodeLookupValidation(this.codeLookupManager, "buy_housing_type"));

		valid.validate(errors);
	}

	/**
	 * 間取CD バリデーション<br/>
	 * ・必須チェック
	 * ・パターン入力チェック
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	protected void validLayoutCd(List<ValidationFailure> errors) {

		if (PanaCommonConstant.HOUSING_KIND_CD_MANSION.equals(this.buyHousingType)) {

			String label = "assessment.input.layoutCd1";
			ValidationChain valid = new ValidationChain(label,this.layoutCd1);

			// 必須チェック
			valid.addValidation(new NullOrEmptyCheckValidation());
			// パターン入力チェック
			valid.addValidation(new CodeLookupValidation(this.codeLookupManager, "layoutCd"));

			valid.validate(errors);

		} else if (PanaCommonConstant.HOUSING_KIND_CD_HOUSE.equals(this.buyHousingType)) {

			String label = "assessment.input.layoutCd2";
			ValidationChain valid = new ValidationChain(label,this.layoutCd2);

			// 必須チェック
			valid.addValidation(new NullOrEmptyCheckValidation());
			// パターン入力チェック
			valid.addValidation(new CodeLookupValidation(this.codeLookupManager, "layoutCd"));

			valid.validate(errors);

		}
	}

	/**
	 * 専有面積 バリデーション<br/>
	 * ・必須チェック
	 * ・半角数字チェック
	 * ・整数部、小数部桁数チェック
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	protected void validPersonalArea(List<ValidationFailure> errors) {

		if (PanaCommonConstant.HOUSING_KIND_CD_MANSION.equals(this.buyHousingType)) {

			String label = "assessment.input.personalArea";
			ValidationChain valid = new ValidationChain(label,this.personalArea);

			// 必須チェック
			valid.addValidation(new NullOrEmptyCheckValidation());
			// 半角数字チェック
			valid.addValidation(new NumberValidation());
			// 整数部、小数部桁数チェック
			valid.addValidation(new PonitNumberValidation(5,2));

			valid.validate(errors);

		}
	}

	/**
	 * 築年数 バリデーション<br/>
	 * ・半角数字チェック
	 * ・桁数チェック
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	protected void validBuildAge(List<ValidationFailure> errors) {

		if (PanaCommonConstant.HOUSING_KIND_CD_MANSION.equals(this.buyHousingType)) {

			String label = "assessment.input.buildAge1";
			ValidationChain valid = new ValidationChain(label,this.buildAge1);

			// 半角数字チェック
			valid.addValidation(new NumericValidation());
			// 桁数チェック
			valid.addValidation(new MaxLengthValidation(this.lengthUtils.getLength(label, 3)));

			valid.validate(errors);

		} else if (PanaCommonConstant.HOUSING_KIND_CD_HOUSE.equals(this.buyHousingType)) {

			String label = "assessment.input.buildAge2";
			ValidationChain valid = new ValidationChain(label,this.buildAge2);

			// 半角数字チェック
			valid.addValidation(new NumericValidation());
			// 桁数チェック
			valid.addValidation(new MaxLengthValidation(this.lengthUtils.getLength(label, 3)));

			valid.validate(errors);

		}
	}

	/**
	 * 土地面積 バリデーション<br/>
	 * ・必須チェック
	 * ・半角数字チェック
	 * ・整数部、小数部桁数チェック
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	protected void validLandArea(List<ValidationFailure> errors) {

		if (PanaCommonConstant.HOUSING_KIND_CD_HOUSE.equals(this.buyHousingType)) {

			String label = "assessment.input.landArea2";
			ValidationChain valid = new ValidationChain(label,this.landArea2);

			// 必須チェック
			valid.addValidation(new NullOrEmptyCheckValidation());
			// 半角数字チェック
			valid.addValidation(new NumberValidation());
			// 整数部、小数部桁数チェック
			valid.addValidation(new PonitNumberValidation(5,2));

			valid.validate(errors);

		} else if (PanaCommonConstant.HOUSING_KIND_CD_GROUND.equals(this.buyHousingType)) {

			String label = "assessment.input.landArea3";
			ValidationChain valid = new ValidationChain(label,this.landArea3);

			// 必須チェック
			valid.addValidation(new NullOrEmptyCheckValidation());
			// 半角数字チェック
			valid.addValidation(new NumberValidation());
			// 整数部、小数部桁数チェック
			valid.addValidation(new PonitNumberValidation(5,2));

			valid.validate(errors);

		}
	}

	/**
	 * 土地面積単位 バリデーション<br/>
	 * ・必須チェック
	 * ・パターン入力チェック
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	protected void validLandAreaCrs(List<ValidationFailure> errors) {

		if (PanaCommonConstant.HOUSING_KIND_CD_HOUSE.equals(this.buyHousingType)) {

			String label = "assessment.input.landAreaCrs2";
			ValidationChain valid = new ValidationChain(label,this.landAreaCrs2);

			// 必須チェック
			valid.addValidation(new NullOrEmptyCheckValidation());
			// パターン入力チェック
			valid.addValidation(new CodeLookupValidation(this.codeLookupManager, "area_crs"));

			valid.validate(errors);

		} else if (PanaCommonConstant.HOUSING_KIND_CD_GROUND.equals(this.buyHousingType)) {

			String label = "assessment.input.landAreaCrs3";
			ValidationChain valid = new ValidationChain(label,this.landAreaCrs3);

			// 必須チェック
			valid.addValidation(new NullOrEmptyCheckValidation());
			// パターン入力チェック
			valid.addValidation(new CodeLookupValidation(this.codeLookupManager, "area_crs"));

			valid.validate(errors);

		}
	}

	/**
	 * 建物面積 バリデーション<br/>
	 * ・必須チェック
	 * ・半角数字チェック
	 * ・整数部、小数部桁数チェック
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	protected void validBuildingArea(List<ValidationFailure> errors) {

		if (PanaCommonConstant.HOUSING_KIND_CD_HOUSE.equals(this.buyHousingType)) {

			String label = "assessment.input.buildingArea";
			ValidationChain valid = new ValidationChain(label,this.buildingArea);

			// 必須チェック
			valid.addValidation(new NullOrEmptyCheckValidation());
			// 半角数字チェック
			valid.addValidation(new NumberValidation());
			// 整数部、小数部桁数チェック
			valid.addValidation(new PonitNumberValidation(5,2));

			valid.validate(errors);

		}
	}

	/**
	 * 建物面積単位 バリデーション<br/>
	 * ・必須チェック
	 * ・パターン入力チェック
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	protected void validBuildingAreaCrs(List<ValidationFailure> errors) {

		if (PanaCommonConstant.HOUSING_KIND_CD_HOUSE.equals(this.buyHousingType)) {

			String label = "assessment.input.buildingAreaCrs";
			ValidationChain valid = new ValidationChain(label,this.buildingAreaCrs);

			// 必須チェック
			valid.addValidation(new NullOrEmptyCheckValidation());
			// パターン入力チェック
			valid.addValidation(new CodeLookupValidation(this.codeLookupManager, "area_crs"));

			valid.validate(errors);

		}
	}

	/**
	 * 郵便番号 バリデーション<br/>
	 * ・必須チェック
	 * ・半角数字チェック
	 * ・桁数チェック
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	protected void validZip(List<ValidationFailure> errors) {
		String label = "assessment.input.zip";
		ValidationChain valid = new ValidationChain(label,this.zip);

		// 必須チェック
		valid.addValidation(new NullOrEmptyCheckValidation());
		// 半角数字チェック
		valid.addValidation(new NumericValidation());
		// 桁数チェック
		valid.addValidation(new LengthValidator(this.lengthUtils.getLength(label, 7)));

		valid.validate(errors);
	}

	/**
	 * 都道府県 バリデーション<br/>
	 * ・必須チェック
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	protected void validPrefCd(List<ValidationFailure> errors) {
		String label = "assessment.input.prefCd";
		ValidationChain valid = new ValidationChain(label,this.prefCd);

		// 必須チェック
		valid.addValidation(new NullOrEmptyCheckValidation());

		valid.validate(errors);
	}

	/**
	 * 市区町村番地 バリデーション<br/>
	 * ・必須チェック
	 * ・桁数チェック
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	protected void validAddress(List<ValidationFailure> errors) {
		String label = "assessment.input.address";
		ValidationChain valid = new ValidationChain(label,this.address);

		// 必須チェック
		valid.addValidation(new NullOrEmptyCheckValidation());
		// 桁数チェック
		valid.addValidation(new MaxLengthValidation(this.lengthUtils.getLength(label, 50)));

		valid.validate(errors);
	}

	/**
	 * 建物名 バリデーション<br/>
	 * ・桁数チェック
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	protected void validAddressOther(List<ValidationFailure> errors) {
		String label = "assessment.input.addressOther";
		ValidationChain valid = new ValidationChain(label,this.addressOther);

		// 桁数チェック
		valid.addValidation(new MaxLengthValidation(this.lengthUtils.getLength(label, 30)));

		valid.validate(errors);
	}

	/**
	 * 現況 バリデーション<br/>
	 * ・パターン入力チェック
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	protected void validPresentCd(List<ValidationFailure> errors) {

		if (!StringValidateUtil.isEmpty(this.presentCd)) {

			String label = "assessment.input.presentCd";
			ValidationChain valid = new ValidationChain(label,this.presentCd);

			// パターン入力チェック
			valid.addValidation(new CodeLookupValidation(this.codeLookupManager, "inquiry_present_cd"));

			valid.validate(errors);

		}
	}

	/**
	 * 売却予定時期 バリデーション<br/>
	 * ・パターン入力チェック
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	protected void validBuyTimeCd(List<ValidationFailure> errors) {

		if (!StringValidateUtil.isEmpty(this.buyTimeCd)) {

			String label = "assessment.input.buyTimeCd";
			ValidationChain valid = new ValidationChain(label,this.buyTimeCd);

			// パターン入力チェック
			valid.addValidation(new CodeLookupValidation(this.codeLookupManager, "inquiry_buy_time_cd"));

			valid.validate(errors);

		}
	}

	/**
	 * 買い替えの有無 バリデーション<br/>
	 * ・パターン入力チェック
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	protected void validReplacementFlg(List<ValidationFailure> errors) {

		if (!StringValidateUtil.isEmpty(this.replacementFlg)) {

			String label = "assessment.input.replacementFlg";
			ValidationChain valid = new ValidationChain(label,this.replacementFlg);

			// パターン入力チェック
			valid.addValidation(new CodeLookupValidation(this.codeLookupManager, "replacement_flg"));

			valid.validate(errors);

		}
	}

	/**
	 * 要望・質問 バリデーション<br/>
	 * ・桁数チェック
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	protected void validRequestText(List<ValidationFailure> errors) {
		String label = "assessment.input.requestText";
		ValidationChain valid = new ValidationChain(label,this.requestText);

		// 桁数チェック
		valid.addValidation(new MaxLengthValidation(this.lengthUtils.getLength(label, 300)));

		valid.validate(errors);
	}

	/**
	 * 氏名(姓) バリデーション<br/>
	 * ・必須チェック
	 * ・全角チェック
	 * ・桁数チェック
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	protected void validLname(List<ValidationFailure> errors) {
		String label = "assessment.input.lname";
		ValidationChain valid = new ValidationChain(label,this.getInquiryHeaderForm().getLname());

		// 必須チェック
		valid.addValidation(new NullOrEmptyCheckValidation());
		// 全角チェック
		valid.addValidation(new ZenkakuOnlyValidation());
		// 桁数チェック
		valid.addValidation(new MaxLengthValidation(this.lengthUtils.getLength(label, 30)));

		valid.validate(errors);
	}

	/**
	 * 氏名(名) バリデーション<br/>
	 * ・必須チェック
	 * ・全角チェック
	 * ・桁数チェック
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	protected void validFname(List<ValidationFailure> errors) {
		String label = "assessment.input.fname";
		ValidationChain valid = new ValidationChain(label,this.getInquiryHeaderForm().getFname());

		// 必須チェック
		valid.addValidation(new NullOrEmptyCheckValidation());
		// 全角チェック
		valid.addValidation(new ZenkakuOnlyValidation());
		// 桁数チェック
		valid.addValidation(new MaxLengthValidation(this.lengthUtils.getLength(label, 30)));

		valid.validate(errors);
	}

	/**
	 * 氏名・カナ(姓) バリデーション<br/>
	 * ・必須チェック
	 * ・全角カタカナチェック
	 * ・桁数チェック
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	protected void validLnameKana(List<ValidationFailure> errors) {
		String label = "assessment.input.lnameKana";
		ValidationChain valid = new ValidationChain(label,this.getInquiryHeaderForm().getLnameKana());

		// 必須チェック
		valid.addValidation(new NullOrEmptyCheckValidation());
		// 全角カタカナチェック
		valid.addValidation(new ZenkakuKanaValidator());
		// 桁数チェック
		valid.addValidation(new MaxLengthValidation(this.lengthUtils.getLength(label, 30)));

		valid.validate(errors);
	}

	/**
	 * 氏名・カナ(名) バリデーション<br/>
	 * ・必須チェック
	 * ・全角カタカナチェック
	 * ・桁数チェック
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	protected void validFnameKana(List<ValidationFailure> errors) {
		String label = "assessment.input.fnameKana";
		ValidationChain valid = new ValidationChain(label,this.getInquiryHeaderForm().getFnameKana());

		// 必須チェック
		valid.addValidation(new NullOrEmptyCheckValidation());
		// 全角カタカナチェック
		valid.addValidation(new ZenkakuKanaValidator());
		// 桁数チェック
		valid.addValidation(new MaxLengthValidation(this.lengthUtils.getLength(label, 30)));

		valid.validate(errors);
	}

	/**
	 * メールアドレス バリデーション<br/>
	 * ・必須チェック
	 * ・メールアドレスの書式チェック
	 * ・桁数チェック
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	protected void validEmail(List<ValidationFailure> errors) {
		String label = "assessment.input.email";
		ValidationChain valid = new ValidationChain(label,this.getInquiryHeaderForm().getEmail());

		// 必須チェック
		valid.addValidation(new NullOrEmptyCheckValidation());
		// メールアドレスの書式チェック
		valid.addValidation(new EmailRFCValidation());
		// 桁数チェック
		valid.addValidation(new MaxLengthValidation(this.lengthUtils.getLength(label, 255)));

		valid.validate(errors);
	}

	/**
	 * 電話番号 バリデーション<br/>
	 * ・必須チェック
	 * ・数値チェック
	 * ・桁数チェック
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	protected void validTel(List<ValidationFailure> errors) {
		String label = "assessment.input.tel";
		ValidationChain valid = new ValidationChain(label,this.getInquiryHeaderForm().getTel());

		// 必須チェック
		valid.addValidation(new NullOrEmptyCheckValidation());
		// 数値チェック
		valid.addValidation(new NumericValidation());
		// 桁数チェック
		valid.addValidation(new MaxLengthValidation(this.lengthUtils.getLength(label, 11)));

		valid.validate(errors);
	}

	/**
	 * FAX番号 バリデーション<br/>
	 * ・数値チェック
	 * ・桁数チェック
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	protected void validFax(List<ValidationFailure> errors) {
		String label = "assessment.input.fax";
		ValidationChain valid = new ValidationChain(label,((PanaInquiryHeaderForm)this.getInquiryHeaderForm()).getFax());

		// 数値チェック
		valid.addValidation(new NumericValidation());
		// 桁数チェック
		valid.addValidation(new MaxLengthValidation(this.lengthUtils.getLength(label, 11)));

		valid.validate(errors);
	}

	/**
	 * 連絡方法 バリデーション<br/>
	 * ・パターン入力チェック
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	protected void validContactType(List<ValidationFailure> errors) {

		if (!StringValidateUtil.isEmpty(this.contactType)) {

			String label = "assessment.input.contactType";
			ValidationChain valid = new ValidationChain(label,this.contactType);

			// パターン入力チェック
			valid.addValidation(new CodeLookupValidation(this.codeLookupManager, "inquiry_contact_type"));

			valid.validate(errors);

		}
	}

	/**
	 * 連絡可能な時間帯 バリデーション<br/>
	 * ・パターン入力チェック
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	protected void validContactTime(List<ValidationFailure> errors) {

		if (this.contactTime != null && this.contactTime.length > 0) {

			String label = "assessment.input.contactTime";
			for (String time : this.contactTime) {

				ValidationChain valid = new ValidationChain(label,time);

				// パターン入力チェック
				valid.addValidation(new CodeLookupValidation(this.codeLookupManager, "inquiry_contact_time"));

				valid.validate(errors);
			}
		}
	}

	/**
	 * 問合者住所・郵便番号 バリデーション<br/>
	 * ・必須チェック
	 * ・半角数字チェック
	 * ・桁数チェック
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	protected void validUserZip(List<ValidationFailure> errors) {

		if (StringValidateUtil.isEmpty(this.sameWithHousing) || !"1".equals(this.sameWithHousing)) {

			String label = "assessment.input.userZip";
			ValidationChain valid = new ValidationChain(label,this.userZip);

			// 必須チェック
			valid.addValidation(new NullOrEmptyCheckValidation());
			// 半角数字チェック
			valid.addValidation(new NumericValidation());
			// 桁数チェック
			valid.addValidation(new LengthValidator(this.lengthUtils.getLength(label, 7)));

			valid.validate(errors);

		}
	}

	/**
	 * 問合者住所・都道府県 バリデーション<br/>
	 * ・必須チェック
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	protected void validUserPrefCd(List<ValidationFailure> errors) {

		if (StringValidateUtil.isEmpty(this.sameWithHousing) || !"1".equals(this.sameWithHousing)) {

			String label = "assessment.input.userPrefCd";
			ValidationChain valid = new ValidationChain(label,this.userPrefCd);

			// 必須チェック
			valid.addValidation(new NullOrEmptyCheckValidation());

			valid.validate(errors);

		}
	}

	/**
	 * 問合者住所・市区町村番地 バリデーション<br/>
	 * ・必須チェック
	 * ・桁数チェック
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	protected void validUserAddress(List<ValidationFailure> errors) {

		if (StringValidateUtil.isEmpty(this.sameWithHousing) || !"1".equals(this.sameWithHousing)) {

			String label = "assessment.input.userAddress";
			ValidationChain valid = new ValidationChain(label,this.userAddress);

			// 必須チェック
			valid.addValidation(new NullOrEmptyCheckValidation());
			// 桁数チェック
			valid.addValidation(new MaxLengthValidation(this.lengthUtils.getLength(label, 50)));

			valid.validate(errors);

		}
	}

	/**
	 * 問合者住所・建物名 バリデーション<br/>
	 * ・桁数チェック
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	protected void validUserAddressOther(List<ValidationFailure> errors) {

		if (StringValidateUtil.isEmpty(this.sameWithHousing) || !"1".equals(this.sameWithHousing)) {

			String label = "assessment.input.userAddressOther";
			ValidationChain valid = new ValidationChain(label,this.userAddressOther);

			// 桁数チェック
			valid.addValidation(new MaxLengthValidation(this.lengthUtils.getLength(label, 30)));

			valid.validate(errors);

		}
	}

	/**
	 * アンケート回答 バリデーション<br/>
	 * ・パターン入力チェック
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	protected void validAnsCd(List<ValidationFailure> errors) {

		if (this.ansCd == null || this.ansCd.length == 0) return;

		String label = "assessment.input.ansCd";
		for (String ans : this.ansCd) {
			ValidationChain valid = new ValidationChain(label,ans);

			// パターン入力チェック
			valid.addValidation(new CodeLookupValidation(this.codeLookupManager, "ans_all"));

			valid.validate(errors);
		}
	}

	/**
	 * アンケート_その他回答１ バリデーション<br/>
	 * ・桁数チェック
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	protected void validEtcAnswer1(List<ValidationFailure> errors) {

		if (this.ansCd == null || this.ansCd.length == 0) return;
		boolean checkFG = false;
		for (String ans : this.ansCd) {
			if ("008".equals(ans)) checkFG = true;
		}
		if (!checkFG) return;

		String label = "assessment.input.etcAnswer1";
		ValidationChain valid = new ValidationChain(label,this.etcAnswer1);

		// 桁数チェック
		valid.addValidation(new MaxLengthValidation(this.lengthUtils.getLength(label, 50)));

		valid.validate(errors);
	}

	/**
	 * アンケート_その他回答２ バリデーション<br/>
	 * ・桁数チェック
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	protected void validEtcAnswer2(List<ValidationFailure> errors) {

		if (this.ansCd == null || this.ansCd.length == 0) return;
		boolean checkFG = false;
		for (String ans : this.ansCd) {
			if ("009".equals(ans)) checkFG = true;
		}
		if (!checkFG) return;

		String label = "assessment.input.etcAnswer2";
		ValidationChain valid = new ValidationChain(label,this.etcAnswer2);

		// 桁数チェック
		valid.addValidation(new MaxLengthValidation(this.lengthUtils.getLength(label, 50)));

		valid.validate(errors);
	}

	/**
	 * アンケート_その他回答３ バリデーション<br/>
	 * ・桁数チェック
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	protected void validEtcAnswer3(List<ValidationFailure> errors) {

		if (this.ansCd == null || this.ansCd.length == 0) return;
		boolean checkFG = false;
		for (String ans : this.ansCd) {
			if ("010".equals(ans)) checkFG = true;
		}
		if (!checkFG) return;

		String label = "assessment.input.etcAnswer3";
		ValidationChain valid = new ValidationChain(label,this.etcAnswer3);

		// 桁数チェック
		valid.addValidation(new MaxLengthValidation(this.lengthUtils.getLength(label, 50)));

		valid.validate(errors);
	}



	/**
	 * 引数で渡された査定情報のバリーオブジェクトにフォームの値を設定する。<br/>
	 * 更新処理でも使用する事を考慮し、タイムスタンプ情報に関しては、更新日、更新者のみ設定する。<br/>
	 * <br/>
	 * @param inquiryAssessment 値を設定する査定情報のバリーオブジェクト
	 *
	 */
	public void copyToInquiryAssessment(InquiryAssessment inquiryAssessment) {

		// 売却物件種別を設定
		inquiryAssessment.setBuyHousingType(this.buyHousingType);

		if (PanaCommonConstant.HOUSING_KIND_CD_MANSION.equals(this.buyHousingType)) {
			// マンションの場合

			// 間取CDを設定
			inquiryAssessment.setLayoutCd(this.layoutCd1);

			// 専有面積を設定
			inquiryAssessment.setPersonalArea(new BigDecimal(this.personalArea));

			// 築年数を設定
			if (!StringValidateUtil.isEmpty(this.buildAge1)) {
				inquiryAssessment.setBuildAge(Integer.valueOf(this.buildAge1));
			}

		} else if (PanaCommonConstant.HOUSING_KIND_CD_HOUSE.equals(this.buyHousingType)) {
			// 戸建の場合

			// 間取CDを設定
			inquiryAssessment.setLayoutCd(this.layoutCd2);

			// 築年数を設定
			if (!StringValidateUtil.isEmpty(this.buildAge2)) {
				inquiryAssessment.setBuildAge(Integer.valueOf(this.buildAge2));
			}

			// 土地面積を設定
			inquiryAssessment.setLandArea(new BigDecimal(this.landArea2));

			// 土地面積単位を設定
			inquiryAssessment.setLandAreaCrs(this.landAreaCrs2);

			// 建物面積を設定
			inquiryAssessment.setBuildingArea(new BigDecimal(this.buildingArea));

			// 建物面積単位を設定
			inquiryAssessment.setBuildingAreaCrs(this.buildingAreaCrs);

		} else if (PanaCommonConstant.HOUSING_KIND_CD_GROUND.equals(this.buyHousingType)) {
			// 土地の場合

			// 土地面積を設定
			inquiryAssessment.setLandArea(new BigDecimal(this.landArea3));

			// 土地面積単位を設定
			inquiryAssessment.setLandAreaCrs(this.landAreaCrs3);

		}

		// 売却物件・郵便番号を設定
		inquiryAssessment.setZip(this.zip);

		// 売却物件・都道府県CDを設定
		inquiryAssessment.setPrefCd(this.prefCd);

		// 売却物件・市区町村番地を設定
		inquiryAssessment.setAddress(this.address);

		// 売却物件・建物名を設定
		inquiryAssessment.setAddressOther(this.addressOther);

		// 現状を設定
		inquiryAssessment.setPresentCd(this.presentCd);

		// 売却予定時期を設定
		inquiryAssessment.setBuyTimeCd(this.buyTimeCd);

		// 買い替えの有無を設定
		inquiryAssessment.setReplacementFlg(this.replacementFlg);

		// 要望・質問を設定
		inquiryAssessment.setRequestText(requestText);

		// 連絡方法を設定
		inquiryAssessment.setContactType(this.contactType);

		if (this.contactTime != null) {
			// 連絡方法を設定
			StringBuilder sb = new StringBuilder();
			for (String time : this.contactTime) {
				sb.append(time);
				sb.append(",");
			}
			sb.deleteCharAt(sb.lastIndexOf(","));
			inquiryAssessment.setContactTime(sb.toString());
		}

	}

	/**
	 * 引数で渡された査定情報のバリーオブジェクトにフォームの値を設定する。<br/>
	 * 更新処理でも使用する事を考慮し、タイムスタンプ情報に関しては、更新日、更新者のみ設定する。<br/>
	 * <br/>
	 * @param inquiryHousingQuestions 値を設定する査定情報のバリーオブジェクト
	 *
	 */
	public void copyToInquiryHousingQuestion(InquiryHousingQuestion[] inquiryHousingQuestions) {
		int i = 0;
		if (this.ansCd != null && this.ansCd.length > 0) {
			for (String questionId : this.ansCd) {
				inquiryHousingQuestions[i].setCategoryNo(PanaCommonConstant.COMMON_CATEGORY_NO);
				inquiryHousingQuestions[i].setAnsCd(questionId);
				if ("008".equals(questionId)) {
					inquiryHousingQuestions[i].setNote(this.etcAnswer1);
				} else if ("009".equals(questionId)) {
					inquiryHousingQuestions[i].setNote(this.etcAnswer2);
				} else if ("010".equals(questionId)) {
					inquiryHousingQuestions[i].setNote(this.etcAnswer3);
				}
				i++;
			}
		}
	}

}
