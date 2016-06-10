package jp.co.transcosmos.dm3.corePana.model.inquiry.form;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jp.co.transcosmos.dm3.core.model.inquiry.InquiryInterface;
import jp.co.transcosmos.dm3.core.model.inquiry.form.InquiryStatusForm;
import jp.co.transcosmos.dm3.core.util.LengthValidationUtils;
import jp.co.transcosmos.dm3.core.vo.BuildingDtlInfo;
import jp.co.transcosmos.dm3.core.vo.BuildingInfo;
import jp.co.transcosmos.dm3.core.vo.BuildingStationInfo;
import jp.co.transcosmos.dm3.core.vo.HousingInfo;
import jp.co.transcosmos.dm3.core.vo.InquiryDtlInfo;
import jp.co.transcosmos.dm3.core.vo.PrefMst;
import jp.co.transcosmos.dm3.core.vo.RouteMst;
import jp.co.transcosmos.dm3.core.vo.StationMst;
import jp.co.transcosmos.dm3.corePana.model.inquiry.InquiryInfo;
import jp.co.transcosmos.dm3.corePana.vo.InquiryAssessment;
import jp.co.transcosmos.dm3.corePana.vo.InquiryGeneral;
import jp.co.transcosmos.dm3.corePana.vo.InquiryHeader;
import jp.co.transcosmos.dm3.corePana.vo.InquiryHousing;
import jp.co.transcosmos.dm3.corePana.vo.InquiryHousingQuestion;
import jp.co.transcosmos.dm3.dao.JoinResult;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;
import jp.co.transcosmos.dm3.validation.CodeLookupValidation;
import jp.co.transcosmos.dm3.validation.MaxLengthValidation;
import jp.co.transcosmos.dm3.validation.NullOrEmptyCheckValidation;
import jp.co.transcosmos.dm3.validation.ValidationChain;
import jp.co.transcosmos.dm3.validation.ValidationFailure;



/**
 * 問合せステータスの入力パラメータ受取り用フォーム.
 * <p>
 * <pre>
 * 担当者       修正日      修正内容
 * ------------ ----------- -----------------------------------------------------
 * 郭中レイ		2015.04.03	新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 *
 */
public class PanaInquiryStatusForm extends InquiryStatusForm {

	/**
	 * コンストラクター<br/>
	 * Factory 以外からのインスタンス生成を制限する為、コンストラクターを隠蔽する。<br/>
	 * <br/>
	 */
	protected PanaInquiryStatusForm(){
		super();
	}

	/**
	 * コンストラクター<br/>
	 * Factory 以外からのインスタンス生成を制限する為、コンストラクターを隠蔽する。<br/>
	 * <br/>
	 * @param lengthUtils レングスバリデーションで使用する文字列長を取得するユーティリティ
	 * @param codeLookupManager 共通コード変換処理
	 */
	protected PanaInquiryStatusForm(LengthValidationUtils lengthUtils, CodeLookupManager codeLookupManager){
		super();
		this.lengthUtils = lengthUtils;
		this.codeLookupManager = codeLookupManager;
	}

	/** 物件名称 */
	private String displayHousingName;
	/** 対応内容(表示用） */
	private String showAnswerText;

	/** 物件番号　（検索条件） */
	private String keyHousingCd;
	/** 物件名　（検索条件） */
	private String keyDisplayHousingName;
	/** 会員番号　（検索条件） */
	private String keyUserId;
	/** メールアドレス　（検索条件） */
	private String keyEmail;
	/** 問合日時開始日　（検索条件） */
	private String keyInquiryDateStart;
	/** 問合日時終了日　（検索条件） */
	private String keyInquiryDateEnd;
	/** 問合種別　（検索条件） */
	private String[] keyInquiryType;
	/** お問合せ内容種別　（検索条件） */
	private String[] keyInquiryDtlType;
	/** 対応ステータス　（検索条件） */
	private String keyAnswerStatus;
	/** お問合せID　（検索条件）*/
	private String keyInquiryId;

	/**
	 * 物件名称を取得する。<br/>
	 * <br/>
	 * @return 物件名称
	 */
	public String getDisplayHousingName() {
		return displayHousingName;
	}

	/**
	 * 物件名称を設定する。<br/>
	 * <br/>
	 * @param 物件名称
	 */
	public void setDisplayHousingName(String displayHousingName) {
		this.displayHousingName = displayHousingName;
	}

	/**
	 * 対応内容(表示用）を取得する。<br/>
	 * <br/>
	 * @return 対応内容(表示用）
	 */
	public String getShowAnswerText() {
		return showAnswerText;
	}

	/**
	 * 対応内容(表示用）を設定する。<br/>
	 * <br/>
	 * @param 対応内容(表示用）
	 */
	public void setShowAnswerText(String showAnswerText) {
		this.showAnswerText = showAnswerText;
	}

	/**
	 * 物件番号　（検索条件）を取得する。<br/>
	 * <br/>
	 * @return 物件番号　（検索条件）
	 */
	public String getKeyHousingCd() {
		return keyHousingCd;
	}

	/**
	 * 物件番号　（検索条件）を設定する。<br/>
	 * <br/>
	 * @param 物件番号　（検索条件）
	 */
	public void setKeyHousingCd(String keyHousingCd) {
		this.keyHousingCd = keyHousingCd;
	}

	/**
	 * 物件名　（検索条件）を取得する。<br/>
	 * <br/>
	 * @return 物件名　（検索条件）
	 */
	public String getKeyDisplayHousingName() {
		return keyDisplayHousingName;
	}

	/**
	 * 物件名　（検索条件）を設定する。<br/>
	 * <br/>
	 * @param 物件名　（検索条件）
	 */
	public void setKeyDisplayHousingName(String keyDisplayHousingName) {
		this.keyDisplayHousingName = keyDisplayHousingName;
	}

	/**
	 * 会員番号　（検索条件）を取得する。<br/>
	 * <br/>
	 * @return 会員番号　（検索条件）
	 */
	public String getKeyUserId() {
		return keyUserId;
	}

	/**
	 * 会員番号　（検索条件）を設定する。<br/>
	 * <br/>
	 * @param 会員番号　（検索条件）
	 */
	public void setKeyUserId(String keyUserId) {
		this.keyUserId = keyUserId;
	}

	/**
	 * メールアドレス　（検索条件）を取得する。<br/>
	 * <br/>
	 * @return メールアドレス　（検索条件）
	 */
	public String getKeyEmail() {
		return keyEmail;
	}

	/**
	 * メールアドレス　（検索条件）を設定する。<br/>
	 * <br/>
	 * @param メールアドレス　（検索条件）
	 */
	public void setKeyEmail(String keyEmail) {
		this.keyEmail = keyEmail;
	}

	/**
	 * 問合日時開始日　（検索条件）を取得する。<br/>
	 * <br/>
	 * @return 問合日時開始日　（検索条件）
	 */
	public String getKeyInquiryDateStart() {
		return keyInquiryDateStart;
	}

	/**
	 * 問合日時開始日　（検索条件）を設定する。<br/>
	 * <br/>
	 * @param 問合日時開始日　（検索条件）
	 */
	public void setKeyInquiryDateStart(String keyInquiryDateStart) {
		this.keyInquiryDateStart = keyInquiryDateStart;
	}

	/**
	 * 問合日時終了日　（検索条件）を取得する。<br/>
	 * <br/>
	 * @return 問合日時終了日　（検索条件）
	 */
	public String getKeyInquiryDateEnd() {
		return keyInquiryDateEnd;
	}

	/**
	 * 問合日時終了日　（検索条件）を設定する。<br/>
	 * <br/>
	 * @param 問合日時終了日　（検索条件）
	 */
	public void setKeyInquiryDateEnd(String keyInquiryDateEnd) {
		this.keyInquiryDateEnd = keyInquiryDateEnd;
	}

	/**
	 * 問合種別　（検索条件）を取得する。<br/>
	 * <br/>
	 * @return 問合種別　（検索条件）
	 */
	public String[] getKeyInquiryType() {
		return keyInquiryType;
	}

	/**
	 * 問合種別　（検索条件）を設定する。<br/>
	 * <br/>
	 * @param 問合種別　（検索条件）
	 */
	public void setKeyInquiryType(String[] keyInquiryType) {
		this.keyInquiryType = keyInquiryType;
	}

	/**
	 * お問合せ内容種別　（検索条件）を取得する。<br/>
	 * <br/>
	 * @return お問合せ内容種別　（検索条件）
	 */
	public String[] getKeyInquiryDtlType() {
		return keyInquiryDtlType;
	}

	/**
	 * お問合せ内容種別　（検索条件）を設定する。<br/>
	 * <br/>
	 * @param お問合せ内容種別　（検索条件）
	 */
	public void setKeyInquiryDtlType(String[] keyInquiryDtlType) {
		this.keyInquiryDtlType = keyInquiryDtlType;
	}

	/**
	 * 対応ステータス　（検索条件）を取得する。<br/>
	 * <br/>
	 * @return 対応ステータス　（検索条件）
	 */
	public String getKeyAnswerStatus() {
		return keyAnswerStatus;
	}

	/**
	 * 対応ステータス　（検索条件）を設定する。<br/>
	 * <br/>
	 * @param 対応ステータス　（検索条件）
	 */
	public void setKeyAnswerStatus(String keyAnswerStatus) {
		this.keyAnswerStatus = keyAnswerStatus;
	}

	/**
	 * お問合せID　（検索条件）を取得する。<br/>
	 * <br/>
	 * @return お問合せID　（検索条件）
	 */
	public String getKeyInquiryId() {
		return keyInquiryId;
	}

	/**
	 * お問合せID　（検索条件）を設定する。<br/>
	 * <br/>
	 * @param お問合せID　（検索条件）
	 */
	public void setKeyInquiryId(String keyInquiryId) {
		this.keyInquiryId = keyInquiryId;
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

		// 対応ステータス
		 ValidationChain valAnswerStatus = new ValidationChain("inquiry.input.answerStatus",this.getAnswerStatus());
		// 必須入力チェック
		 valAnswerStatus.addValidation(new NullOrEmptyCheckValidation());
		// パターンチェック
		 valAnswerStatus.addValidation(new CodeLookupValidation(this.codeLookupManager,"inquiry_answerStatus"));
		 valAnswerStatus.validate(errors);

		 // 対応内容
		 ValidationChain valAnswerText = new ValidationChain("inquiry.input.answerText",this.getAnswerText());
		 // 桁数チェック
		 valAnswerText.addValidation(new MaxLengthValidation(this.lengthUtils.getLength("inquiry.input.answerText", 1000)));
		 valAnswerText.validate(errors);

        return (startSize == errors.size());
	}
	/**
	 * 渡されたバリーオブジェクトから Form へ初期値を設定する。<br/>
	 * <br/>
	 * @param inquiryInfo InquiryInfo　を実装した問合せ情報管理用バリーオブジェクト
	 */
	public void setDefaultData(InquiryInterface inquiryInfo, String inquiryType) {

		//物件問合せ情報
		if (inquiryType.equalsIgnoreCase("00")) {
			if (((InquiryInfo)inquiryInfo).getHousings().size() > 0) {
				// 物件名称 を設定
				HousingInfo housingInfo = (HousingInfo) ((InquiryInfo)inquiryInfo).getHousings().get(0).getHousingInfo().getItems().get("housingInfo");
				this.displayHousingName = housingInfo.getDisplayHousingName();
			}
			//問合種別 を設定
			this.setInquiryType(inquiryInfo.getInquiryHeaderInfo().getInquiryHeader().getInquiryType());
			// 対応ステータス を設定
			this.setAnswerStatus(inquiryInfo.getInquiryHeaderInfo().getInquiryHeader().getAnswerStatus());
			// 対応内容 を設定
			this.setAnswerText(inquiryInfo.getInquiryHeaderInfo().getInquiryHeader().getAnswerText());

			//汎用問合せ情報
		} else if (inquiryType.equalsIgnoreCase("01")) {
			InquiryHeader inquiryHeader = (InquiryHeader)((InquiryInfo)inquiryInfo).getGeneralInquiry().get(0).getItems().get("inquiryHeader");
			//問合種別 を設定
			this.setInquiryType(inquiryHeader.getInquiryType());
			// 対応ステータス を設定
			this.setAnswerStatus(inquiryHeader.getAnswerStatus());
			// 対応内容 を設定
			this.setAnswerText(inquiryHeader.getAnswerText());

			//査定情報
		} else if (inquiryType.equalsIgnoreCase("02")) {
			InquiryHeader inquiryHeader = (InquiryHeader)((InquiryInfo)inquiryInfo).getAssessmentInquiry().get(0).getItems().get("inquiryHeader");
			//問合種別 を設定
			this.setInquiryType(inquiryHeader.getInquiryType());
			// 対応ステータス を設定
			this.setAnswerStatus(inquiryHeader.getAnswerStatus());
			// 対応内容 を設定
			this.setAnswerText(inquiryHeader.getAnswerText());
		}
	}

	/**
	 * 取得した査定情報をmodel へ格納する。<br/>
	 * <br/>
	 * @param inquiryInfo InquiryInfo　を実装した査定問合情報管理用バリーオブジェクト
	 * * @param model     model オブジェクト
	 */
	public void setAssessmentData(InquiryInfo inquiryInfo, Map<String, Object> model) {

		InquiryHeader inquiryHeader = (InquiryHeader)((InquiryInfo)inquiryInfo).getAssessmentInquiry().get(0).getItems().get("inquiryHeader");
		InquiryAssessment inquiryAssessment = (InquiryAssessment)((InquiryInfo)inquiryInfo).getAssessmentInquiry().get(0).getItems().get("inquiryAssessment");
		PrefMst headerPrefMst = (PrefMst)((InquiryInfo)inquiryInfo).getAssessmentInquiry().get(0).getItems().get("PrefMst");
		PrefMst assessmentPrefMst = (PrefMst)((InquiryInfo)inquiryInfo).getPrefMst();

		// 所在地
		 StringBuffer assessmentAddress = new StringBuffer();
		 // 問合者住所・郵便番号
		 if (inquiryAssessment.getZip() != null) {
			 assessmentAddress.append("〒");
			 assessmentAddress.append(inquiryAssessment.getZip()).append(" ");
		 }
		 // 問合者住所・都道府県CDより都道府県マスタから取得した都道府県名
		 if (assessmentPrefMst != null) {
			 if (assessmentPrefMst.getPrefName() != null) {
				 assessmentAddress.append(assessmentPrefMst.getPrefName());
			 }
		 }
		 // 問合者住所・市区町村名
		 if (inquiryAssessment.getAddress() != null) {
			 assessmentAddress.append(inquiryAssessment.getAddress());
		 }
		 // 問合者住所・町名番地その他
		 if (inquiryAssessment.getAddressOther() != null) {
			 assessmentAddress.append(inquiryAssessment.getAddressOther());
		 }

		 //現住所
		 StringBuffer headerAddress = new StringBuffer();
		 // 問合者住所・郵便番号
		 if (inquiryHeader.getZip() != null) {
			 headerAddress.append("〒");
			 headerAddress.append(inquiryHeader.getZip()).append(" ");
		 }
		 // 問合者住所・都道府県CDより都道府県マスタから取得した都道府県名
		 if (headerPrefMst.getPrefName() != null) {
			 headerAddress.append(headerPrefMst.getPrefName());
		 }
		 // 問合者住所・市区町村名
		 if (inquiryHeader.getAddress() != null) {
			 headerAddress.append(inquiryHeader.getAddress());
		 }
		 // 問合者住所・町名番地その他
		 if (inquiryHeader.getAddressOther() != null) {
			 headerAddress.append(inquiryHeader.getAddressOther());
		 }

		// 連絡可能な時間帯
		String[] contactTimes = null;
		if (inquiryAssessment.getContactTime() != null) {
			contactTimes = inquiryAssessment.getContactTime().split(",");
		}

		 // アンケート
		 int size = ((InquiryInfo)inquiryInfo).getAssessmentInquiry().size();
		 InquiryHousingQuestion[] inquiryHousingQuestions = new InquiryHousingQuestion[size];
		 for ( int i = 0; i < ((InquiryInfo)inquiryInfo).getAssessmentInquiry().size(); i++) {
			InquiryHousingQuestion inquiryHousingQuestion = (InquiryHousingQuestion)((InquiryInfo)inquiryInfo).
					getAssessmentInquiry().get(i).getItems().get("inquiryHousingQuestion");
			inquiryHousingQuestions[i] = inquiryHousingQuestion;
		}

		// 査定情報を格納する
		model.put("inquiryHeader", inquiryHeader);
		model.put("inquiryAssessment", inquiryAssessment);
		model.put("assessmentAddress", assessmentAddress.toString());
		model.put("headerAddress", headerAddress.toString());
		model.put("contactTimes", contactTimes);
		model.put("inquiryHousingQuestion", inquiryHousingQuestions);
	}

	/**
	 * 取得した汎用問合せ情報をmodel へ格納する。<br/>
	 * <br/>
	 * @param inquiryInfo InquiryInfo　を実装した汎用問合せ情報管理用バリーオブジェクト
	 * * @param model     model オブジェクト
	 */
	public void setGeneralData(InquiryInfo inquiryInfo, Map<String, Object> model) {

		InquiryHeader inquiryHeader = (InquiryHeader)((InquiryInfo)inquiryInfo).getGeneralInquiry().get(0).getItems().get("inquiryHeader");
		InquiryDtlInfo inquiryDtlInfo = (InquiryDtlInfo)((InquiryInfo)inquiryInfo).getGeneralInquiry().get(0).getItems().get("inquiryDtlInfo");
		InquiryGeneral inquiryGeneral = (InquiryGeneral)((InquiryInfo)inquiryInfo).getGeneralInquiry().get(0).getItems().get("inquiryGeneral");

		// 日時
		String fmtDate = null;
		if (inquiryGeneral.getEventDatetime() != null) {
			fmtDate = new SimpleDateFormat("M月d日　H:mm").format(inquiryGeneral.getEventDatetime());
		}

		// 連絡可能な時間帯
		String[] contactTimes = null;
		if (inquiryGeneral.getContactTime() != null) {
			contactTimes = inquiryGeneral.getContactTime().split(",");
		}

		 // アンケート
		 int size = ((InquiryInfo)inquiryInfo).getGeneralInquiry().size();
		 InquiryHousingQuestion[] inquiryHousingQuestions = new InquiryHousingQuestion[size];
		 for ( int i = 0; i < ((InquiryInfo)inquiryInfo).getGeneralInquiry().size(); i++) {
			InquiryHousingQuestion inquiryHousingQuestion = (InquiryHousingQuestion)((InquiryInfo)inquiryInfo).
					getGeneralInquiry().get(i).getItems().get("inquiryHousingQuestion");
			inquiryHousingQuestions[i] = inquiryHousingQuestion;
		}

		// 査定情報を格納する
		model.put("inquiryHeader", inquiryHeader);
		model.put("inquiryDtlInfo", inquiryDtlInfo);
		model.put("inquiryGeneral", inquiryGeneral);
		model.put("fmtDate", fmtDate);
		model.put("contactTimes", contactTimes);
		model.put("inquiryHousingQuestion", inquiryHousingQuestions);
	}

	/**
	 * 取得した物件問合せ情報をmodel へ格納する。<br/>
	 * <br/>
	 * @param inquiryInfo InquiryInfo　を実装した物件問合せ情報管理用バリーオブジェクト
	 * * @param model     model オブジェクト
	 */
	public void setHousingData(InquiryInfo inquiryInfo, Map<String, Object> model) {

		HousingInfo housingInfo = new HousingInfo();
		BuildingInfo buildingInfo = new BuildingInfo();
		BuildingDtlInfo buildingDtlInfo = new BuildingDtlInfo();
		String fmtDate = null;
		StringBuffer address1 = new StringBuffer();
		List<String> nearStationList = new ArrayList<String>();

		if (((InquiryInfo)inquiryInfo).getHousings().size() > 0){
			housingInfo = (HousingInfo)((InquiryInfo)inquiryInfo).getHousings().get(0).getHousingInfo().getItems().get("housingInfo");
			buildingInfo = (BuildingInfo)((InquiryInfo)inquiryInfo).getHousings().get(0).getBuilding().getBuildingInfo().getItems().get("buildingInfo");
			buildingDtlInfo = (BuildingDtlInfo)((InquiryInfo)inquiryInfo).getHousings().get(0).getBuilding().getBuildingInfo().getItems().get("buildingDtlInfo");

			// 築年
			if (buildingInfo.getCompDate() != null) {
				fmtDate = new SimpleDateFormat("yyyy年MM月").format(buildingInfo.getCompDate());
			}

			//住所1
			PrefMst prefMst1 = (PrefMst)((InquiryInfo)inquiryInfo).getHousings().get(0).getBuilding().getBuildingInfo().getItems().get("prefMst");
			 // 所在地・郵便番号
			 if (buildingInfo.getZip() != null) {
				 address1.append("〒");
				 address1.append(buildingInfo.getZip()).append(" ");
			 }
			 // 所在地・都道府県CDより都道府県マスタから取得した都道府県名
			 if (prefMst1.getPrefName() != null) {
				 address1.append(prefMst1.getPrefName());
			 }
			 // 所在地・市区町村名
			 if (buildingInfo.getAddressName() != null) {
				 address1.append(buildingInfo.getAddressName());
			 }
			 // 所在地・町名番地
			 if (buildingInfo.getAddressOther1() != null) {
				 address1.append(buildingInfo.getAddressOther1());
			 }
			// 所在地・建物名その他
			 if (buildingInfo.getAddressOther2() != null) {
				 address1.append(buildingInfo.getAddressOther2());
			 }

			 // 最寄り駅
			 List<JoinResult> buildingStationInfoList =((InquiryInfo)inquiryInfo).getHousings().get(0).getBuilding().getBuildingStationInfoList();
			 BuildingStationInfo buildingStationInfo = new BuildingStationInfo();
			 RouteMst routeMst = new RouteMst();
			 StationMst stationMst = new StationMst();
			 StringBuffer nearStation = new StringBuffer();
			 for (int i = 0; i < buildingStationInfoList.size(); i++) {

				 // 建物最寄り駅情報の取得
				 buildingStationInfo = new BuildingStationInfo();
				 buildingStationInfo =  (BuildingStationInfo)buildingStationInfoList.get(i).getItems().get("buildingStationInfo");
				 // 路線マストの取得
				 routeMst = new RouteMst();
				 routeMst = (RouteMst)buildingStationInfoList.get(i).getItems().get("routeMst");
				 // 駅マストの取得
				 stationMst = new StationMst();
				 stationMst = (StationMst)buildingStationInfoList.get(i).getItems().get("stationMst");

				 // 代表路線名
				 nearStation = new StringBuffer();
				 if (!StringValidateUtil.isEmpty(routeMst.getRouteName())) {
					 nearStation.append(routeMst.getRouteName());
				 } else {
					 if (!StringValidateUtil.isEmpty(buildingStationInfo.getDefaultRouteName())) {
						 nearStation.append(buildingStationInfo.getDefaultRouteName());
					 }
				 }
				 nearStation.append(" ");
				 // 駅名
				 if (!StringValidateUtil.isEmpty(stationMst.getStationName())) {
					 nearStation.append(stationMst.getStationName());
				 } else {
					 if (!StringValidateUtil.isEmpty(buildingStationInfo.getStationName())) {
						 nearStation.append(buildingStationInfo.getStationName());
					 }
				 }
				 nearStation.append(" ");
				 // バス会社名
				 if (!StringValidateUtil.isEmpty(buildingStationInfo.getBusCompany())) {
					 nearStation.append(buildingStationInfo.getBusCompany()).append(" ").append("徒歩");
				 }
				 nearStation.append(" ");
				 // バス停からの徒歩時間
				 if (buildingStationInfo.getTimeFromBusStop() != null) {
					 nearStation.append(String.valueOf(buildingStationInfo.getTimeFromBusStop())).append("分");
				 }
				 nearStationList.add(nearStation.toString());
			 }
		}

		InquiryHeader inquiryHeader = (InquiryHeader)((InquiryInfo)inquiryInfo).getInquiryHeaderInfo().getInquiryHeader();
		InquiryDtlInfo[] inquiryDtlInfo = (InquiryDtlInfo[])((InquiryInfo)inquiryInfo).getInquiryHeaderInfo().getInquiryDtlInfos();
		InquiryHousing inquiryHousing = (InquiryHousing)((InquiryInfo)inquiryInfo).getInquiryHousing();

		//住所2
		 PrefMst prefMst2 = (PrefMst)((InquiryInfo)inquiryInfo).getPrefMst();
		 StringBuffer address2 = new StringBuffer();
		 // 問合者住所・郵便番号
		 if (inquiryHeader.getZip() != null) {
			 address2.append("〒");
			 address2.append(inquiryHeader.getZip()).append(" ");
		 }
		// 問合者住所・都道府県CDより都道府県マスタから取得した都道府県名
		if (prefMst2 != null) {
			if (prefMst2.getPrefName() != null) {
				address2.append(prefMst2.getPrefName());
			}
		}
		 // 問合者住所・市区町村名
		 if (inquiryHeader.getAddress() != null) {
			 address2.append(inquiryHeader.getAddress());
		 }
		 // 問合者住所・町名番地その他
		 if (inquiryHeader.getAddressOther() != null) {
			 address2.append(inquiryHeader.getAddressOther());
		 }

		 // 連絡可能な時間帯
		 String[] contactTimes = null;
		if (inquiryHousing != null) {
			if (!StringValidateUtil.isEmpty(inquiryHousing.getContactTime())) {
				contactTimes = inquiryHousing.getContactTime().split(",");
			}
		}

		 // アンケート
		 InquiryHousingQuestion[] inquiryHousingQuestion = (InquiryHousingQuestion[])((InquiryInfo)inquiryInfo).getInquiryHousingQuestion();

		// 物件問合せ情報を格納する
		if (inquiryDtlInfo != null) {
			model.put("inquiryDtlInfo", inquiryDtlInfo[0]);
		}
		model.put("housingInfo", housingInfo);
		model.put("buildingInfo", buildingInfo);
		model.put("buildingDtlInfo", buildingDtlInfo);
		model.put("fmtDate", fmtDate);
		model.put("address1", address1.toString());
		model.put("nearStationList", nearStationList);
		model.put("inquiryHeader", inquiryHeader);
		model.put("inquiryHousing", inquiryHousing);
		model.put("address2", address2.toString());
		model.put("contactTimes", contactTimes);
		model.put("inquiryHousingQuestion", inquiryHousingQuestion);
	}
}
