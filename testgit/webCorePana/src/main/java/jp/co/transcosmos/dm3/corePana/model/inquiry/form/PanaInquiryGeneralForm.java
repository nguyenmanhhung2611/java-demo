package jp.co.transcosmos.dm3.corePana.model.inquiry.form;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import jp.co.transcosmos.dm3.core.model.inquiry.form.HousingInquiryForm;
import jp.co.transcosmos.dm3.core.util.LengthValidationUtils;
import jp.co.transcosmos.dm3.core.validation.ZenkakuKanaValidator;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonConstant;
import jp.co.transcosmos.dm3.corePana.util.PanaStringUtils;
import jp.co.transcosmos.dm3.corePana.vo.InquiryGeneral;
import jp.co.transcosmos.dm3.corePana.vo.InquiryHousingQuestion;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;
import jp.co.transcosmos.dm3.validation.CodeLookupValidation;
import jp.co.transcosmos.dm3.validation.EmailRFCValidation;
import jp.co.transcosmos.dm3.validation.MaxLengthValidation;
import jp.co.transcosmos.dm3.validation.NullOrEmptyCheckValidation;
import jp.co.transcosmos.dm3.validation.NumericValidation;
import jp.co.transcosmos.dm3.validation.ValidationChain;
import jp.co.transcosmos.dm3.validation.ValidationFailure;
import jp.co.transcosmos.dm3.validation.ZenkakuOnlyValidation;

/**
 * 物件のお問い合わせ入力パラメータ受取り用フォーム.
 * <p>
 * <pre>
 * 担当者       修正日      修正内容
 * ------------ ----------- -----------------------------------------------------
 * qiao.meng	2015.04.28   新規作成
 *
 * 注意事項
 *
 * </pre>
 */
public class PanaInquiryGeneralForm extends HousingInquiryForm {

	/** レングスバリデーションで使用する文字列長を取得するユーティリティ */
	private LengthValidationUtils lengthUtils;

	/** 共通コード変換処理 */
	private CodeLookupManager codeLookupManager;

	/** お問合せID */
	private String inquiryId;

	/** セミナー・イベント名 */
	private String eventName;

	/** 日時 */
	private String eventDatetime;
	/** 日時（MM月DD日　hh：mm） */
	private String eventDatetimeWithFormat;

	/** 日時（月） */
	private String eventDatetimeMonth;

	/** 日時（日） */
	private String eventDatetimeDay;

	/** 日時（時） */
	private String eventDatetimeHour;

	/** 日時（分） */
	private String eventDatetimeMinute;

	/** ご希望の連絡方法 */
	private String inquiryContactType;

	/** 連絡可能な時間帯 */
	private String[] inquiryContactTime;

	/** アンケート */
	private String[] ansCd;

	/** アンケート内容1 */
	private String etcAnswer1;

	/** アンケート内容2 */
	private String etcAnswer2;

	/** アンケート内容3 */
	private String etcAnswer3;


	/** お問合せ内容詳細（画面表示用） */
	private String inquiryText1;

	/** お問合せ内容（メール表示用） */
	private String inquiryDtlTypeMail;

	/** ご希望の連絡方法（メール表示用） */
	private String inquiryContactTypeMail;

	/** 連絡可能な時間帯（メール表示用） */
	private String inquiryContactTimeMail;

	/** Urlパターン */
	private String urlPattern;

	/** command パラメータ */
	private String command;
	/**
	 * コンストラクター<br/>
	 * Factory 以外からのインスタンス生成を制限する為、コンストラクターを隠蔽する。<br/>
	 * <br/>
	 */
	PanaInquiryGeneralForm(){
		super();
	}

	/**
	 * コンストラクター<br/>
	 * Factory 以外からのインスタンス生成を制限する為、コンストラクターを隠蔽する。<br/>
	 * <br/>
	 * @param lengthUtils レングスバリデーションで使用する文字列長を取得するユーティリティ
	 * @param codeLookupManager 共通コード変換処理
	 */
	PanaInquiryGeneralForm(LengthValidationUtils lengthUtils, CodeLookupManager codeLookupManager){
		super();
		this.lengthUtils = lengthUtils;
		this.codeLookupManager = codeLookupManager;
	}

	/**
	 * @return inquiryId
	 */
	public String getInquiryId() {
		return inquiryId;
	}

	/**
	 * @param inquiryId セットする inquiryId
	 */
	public void setInquiryId(String inquiryId) {
		this.inquiryId = inquiryId;
	}

	/**
	 * @return eventName
	 */
	public String getEventName() {
		return eventName;
	}

	/**
	 * @param eventName セットする eventName
	 */
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	/**
	 * @return eventDatetime
	 */
	public String getEventDatetime() {
		return eventDatetime;
	}


	/**
	 * @param eventDatetime セットする eventDatetime
	 */
	public void setEventDatetime(String eventDatetime) {
		this.eventDatetime = eventDatetime;
	}

	/**
	 * @return eventDatetimeWithFormat
	 */
	public String getEventDatetimeWithFormat() {
		return eventDatetimeWithFormat;
	}

	/**
	 * @param eventDatetimeWithFormat セットする eventDatetimeWithFormat
	 */
	public void setEventDatetimeWithFormat(String eventDatetimeWithFormat) {
		this.eventDatetimeWithFormat = eventDatetimeWithFormat;
	}

	/**
	 * @return eventDatetimeMonth
	 */
	public String getEventDatetimeMonth() {
		return eventDatetimeMonth;
	}

	/**
	 * @param eventDatetimeMonth セットする eventDatetimeMonth
	 */
	public void setEventDatetimeMonth(String eventDatetimeMonth) {
		this.eventDatetimeMonth = eventDatetimeMonth;
	}

	/**
	 * @return eventDatetimeDay
	 */
	public String getEventDatetimeDay() {
		return eventDatetimeDay;
	}

	/**
	 * @param eventDatetimeDay セットする eventDatetimeDay
	 */
	public void setEventDatetimeDay(String eventDatetimeDay) {
		this.eventDatetimeDay = eventDatetimeDay;
	}

	/**
	 * @return eventDatetimeHour
	 */
	public String getEventDatetimeHour() {
		return eventDatetimeHour;
	}

	/**
	 * @param eventDatetimeHour セットする eventDatetimeHour
	 */
	public void setEventDatetimeHour(String eventDatetimeHour) {
		this.eventDatetimeHour = eventDatetimeHour;
	}

	/**
	 * @return eventDatetimeMinute
	 */
	public String getEventDatetimeMinute() {
		return eventDatetimeMinute;
	}

	/**
	 * @param eventDatetimeMinute セットする eventDatetimeMinute
	 */
	public void setEventDatetimeMinute(String eventDatetimeMinute) {
		this.eventDatetimeMinute = eventDatetimeMinute;
	}

	/**
	 * @return inquiryContactType
	 */
	public String getInquiryContactType() {
		return inquiryContactType;
	}

	/**
	 * @param inquiryContactType セットする inquiryContactType
	 */
	public void setInquiryContactType(String inquiryContactType) {
		this.inquiryContactType = inquiryContactType;
	}


	/**
	 * @return inquiryContactTime
	 */
	public String[] getInquiryContactTime() {
		return inquiryContactTime;
	}

	/**
	 * @param inquiryContactTime セットする inquiryContactTime
	 */
	public void setInquiryContactTime(String[] inquiryContactTime) {
		this.inquiryContactTime = inquiryContactTime;
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
	 * @return inquiryText1
	 */
	public String getInquiryText1() {
		return inquiryText1;
	}

	/**
	 * @param inquiryText1 セットする inquiryText1
	 */
	public void setInquiryText1(String inquiryText1) {
		this.inquiryText1 = inquiryText1;
	}


	/**
	 * @return inquiryDtlTypeMail
	 */
	public String getInquiryDtlTypeMail() {
		return inquiryDtlTypeMail;
	}

	/**
	 * @param inquiryDtlTypeMail セットする inquiryDtlTypeMail
	 */
	public void setInquiryDtlTypeMail(String inquiryDtlTypeMail) {
		this.inquiryDtlTypeMail = inquiryDtlTypeMail;
	}

	/**
	 * @return inquiryContactTypeMail
	 */
	public String getInquiryContactTypeMail() {
		return inquiryContactTypeMail;
	}

	/**
	 * @param inquiryContactTypeMail セットする inquiryContactTypeMail
	 */
	public void setInquiryContactTypeMail(String inquiryContactTypeMail) {
		this.inquiryContactTypeMail = inquiryContactTypeMail;
	}

	/**
	 * @return inquiryContactTimeMail
	 */
	public String getInquiryContactTimeMail() {
		return inquiryContactTimeMail;
	}

	/**
	 * @param inquiryContactTimeMail セットする inquiryContactTimeMail
	 */
	public void setInquiryContactTimeMail(String inquiryContactTimeMail) {
		this.inquiryContactTimeMail = inquiryContactTimeMail;
	}

	/**
	 * @return urlPattern
	 */
	public String getUrlPattern() {
		return urlPattern;
	}

	/**
	 * @param urlPattern セットする urlPattern
	 */
	public void setUrlPattern(String urlPattern) {
		this.urlPattern = urlPattern;
	}

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
	 * バリデーション処理<br/>
	 * リクエストのバリデーションを行う<br/>
	 * <br/>
	 *
	 * @param errors
	 *            エラー情報を格納するリストオブジェクト
	 * @return 正常時 true、エラー時 false
	 * @throws ParseException
	 */
	public boolean validate(List<ValidationFailure> errors) throws ParseException {
		int startSize = errors.size();

		// お問い合わせ内容のバリデーション処理
		validInquiryDtlType(errors);

		// セミナー・イベント名のバリデーション処理
		validEventName(errors);

		// 日時のバリデーション処理
		validEventDateTime(errors);

		// お問い合わせ内容詳細のバリデーション処理
		validInquiryText(errors);

		// お名前_姓のバリデーション処理
		validLname(errors);

		// お名前_名のバリデーション処理
		validFname(errors);

		// お名前（フリガナ）_姓のバリデーション処理
		validLnameKana(errors);

		// お名前（フリガナ）_名のバリデーション処理
		validFnameKana(errors);

		// メールアドレスのバリデーション処理
		validEmail(errors);

		// 電話番号のバリデーション処理
		validTel(errors);

		// ご希望の連絡方法のバリデーション処理
		validInquiryContactType(errors);

		// 連絡可能な時間帯のバリデーション処理
		validInquiryContactTime(errors);

		// アンケート回答のバリデーション処理
		validAnsCd(errors);

		// アンケート_その他回答のバリデーション処理
		validEtcAnswer(errors);

		return (startSize == errors.size());
	}

	/**
	 * アンケート回答 バリデーション<br/>
	 * ・パターン入力チェック
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	protected void validAnsCd(List<ValidationFailure> errors) {

		if (this.getAnsCd() != null) {
			for (String ansCd : this.getAnsCd()) {
				ValidationChain valid = new ValidationChain("inquiryGeneral.input.ansCd", ansCd);

				// パターン入力チェック
				valid.addValidation(new CodeLookupValidation(this.codeLookupManager, "ans_all"));

				valid.validate(errors);
			}
		}
	}

	/**
	 * アンケート_その他回答 バリデーション<br/>
	 * <ul>
	 * <li>桁数チェック</li>
	 * </ul>
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	private void validEtcAnswer(List<ValidationFailure> errors) {

		if (this.getAnsCd() != null) {
			for (String ansCd : this.getAnsCd()) {
				if ("008".equals(ansCd)) {
					/** アンケート1内容1 */
			        ValidationChain etcAnswer1 = new ValidationChain("inquiryGeneral.input.etcAnswer1", this.getEtcAnswer1());
			        // 桁数チェック
			        etcAnswer1.addValidation(new MaxLengthValidation(50));
			        etcAnswer1.validate(errors);
				}

				if ("009".equals(ansCd)) {
					/** アンケート1内容2 */
			        ValidationChain etcAnswer2 = new ValidationChain("inquiryGeneral.input.etcAnswer2", this.getEtcAnswer2());
			        // 桁数チェック
			        etcAnswer2.addValidation(new MaxLengthValidation(50));
			        etcAnswer2.validate(errors);
				}

				if ("010".equals(ansCd)) {
					/** アンケート1内容3 */
			        ValidationChain etcAnswer3 = new ValidationChain("inquiryGeneral.input.etcAnswer3", this.getEtcAnswer3());
			        // 桁数チェック
			        etcAnswer3.addValidation(new MaxLengthValidation(50));
			        etcAnswer3.validate(errors);
				}
			}
		}

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
	 * 連絡可能な時間帯 バリデーション<br/>
	 * <ul>
	 * <li>パターンチェック</li>
	 * </ul>
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	private void validInquiryContactTime(List<ValidationFailure> errors) {

		if (this.getInquiryContactTime() == null) return;

		for (int i = 0; i < this.getInquiryContactTime().length; i++) {
	        ValidationChain valid = new ValidationChain("inquiryGeneral.input.inquiryContactTime", this.getInquiryContactTime()[i]);
	        // パターン入力チェック
	        valid.addValidation(new CodeLookupValidation(this.codeLookupManager, "inquiry_contact_time"));
	        valid.validate(errors);
		}
	}

	/**
	 * ご希望の連絡方法 バリデーション<br/>
	 * <ul>
	 * <li>パターンチェック</li>
	 * </ul>
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	private void validInquiryContactType(List<ValidationFailure> errors) {
        ValidationChain valid = new ValidationChain("inquiryGeneral.input.inquiryContactType", this.getInquiryContactType());
        // パターン入力チェック
        valid.addValidation(new CodeLookupValidation(this.codeLookupManager, "inquiry_contact_type"));
        valid.validate(errors);
	}

	/**
	 * 電話番号 バリデーション<br/>
	 * <ul>
	 * <li>必須チェック</li>
	 * <li>桁数チェック</li>
	 * <li>半角数字チェック</li>
	 * </ul>
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	private void validTel(List<ValidationFailure> errors) {
		// 全角数字⇒半角数字
		this.getInquiryHeaderForm().setTel(PanaStringUtils.changeToHankakuNumber(this.getInquiryHeaderForm().getTel()));
        ValidationChain valid = new ValidationChain("inquiryGeneral.input.tel", this.getInquiryHeaderForm().getTel());

        // 必須チェック
        valid.addValidation(new NullOrEmptyCheckValidation());
        // 桁数チェック
        valid.addValidation(new MaxLengthValidation(11));
        // 半角数字チェック
        valid.addValidation(new NumericValidation());

        valid.validate(errors);
	}

	/**
	 * メールアドレス バリデーション<br/>
	 * <ul>
	 * <li>必須チェック</li>
	 * <li>桁数チェック</li>
	 * <li>メールアドレスの書式チェック</li>
	 * </ul>
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	private void validEmail(List<ValidationFailure> errors) {
		// 全角数字⇒半角数字
		this.getInquiryHeaderForm().setEmail(PanaStringUtils.changeToHankakuNumber(this.getInquiryHeaderForm().getEmail()));
        ValidationChain valid = new ValidationChain("inquiryGeneral.input.email", this.getInquiryHeaderForm().getEmail());

        // 必須チェック
        valid.addValidation(new NullOrEmptyCheckValidation());
        // 桁数チェック
        valid.addValidation(new MaxLengthValidation(255));
        // メールアドレスの書式チェック
        valid.addValidation(new EmailRFCValidation());

        valid.validate(errors);
	}

	/**
	 * お名前_姓 バリデーション<br/>
	 * <ul>
	 * <li>必須チェック</li>
	 * <li>全角チェック</li>
	 * <li>桁数チェック</li>
	 * </ul>
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	private void validLname(List<ValidationFailure> errors) {
        ValidationChain valid = new ValidationChain("inquiryGeneral.input.lname", this.getInquiryHeaderForm().getLname());

        // 必須チェック
        valid.addValidation(new NullOrEmptyCheckValidation());
        // 桁数チェック
        valid.addValidation(new MaxLengthValidation(30));
		// 全角チェック
        valid.addValidation(new ZenkakuOnlyValidation());

        valid.validate(errors);
	}

	/**
	 * お名前_名 バリデーション<br/>
	 * <ul>
	 * <li>必須チェック</li>
	 * <li>全角チェック</li>
	 * <li>桁数チェック</li>
	 * </ul>
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	private void validFname(List<ValidationFailure> errors) {
        ValidationChain valid = new ValidationChain("inquiryGeneral.input.fname", this.getInquiryHeaderForm().getFname());

        // 必須チェック
        valid.addValidation(new NullOrEmptyCheckValidation());
        // 桁数チェック
        valid.addValidation(new MaxLengthValidation(30));
		// 全角チェック
        valid.addValidation(new ZenkakuOnlyValidation());

        valid.validate(errors);
	}

	/**
	 * お名前（フリガナ）_姓 バリデーション<br/>
	 * <ul>
	 * <li>必須チェック</li>
	 * <li>全角カタカナチェック</li>
	 * <li>桁数チェック</li>
	 * </ul>
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	private void validLnameKana(List<ValidationFailure> errors) {
        ValidationChain valid = new ValidationChain("inquiryGeneral.input.lnameKana", this.getInquiryHeaderForm().getLnameKana());

        // 必須チェック
        valid.addValidation(new NullOrEmptyCheckValidation());
        // 桁数チェック
        valid.addValidation(new MaxLengthValidation(30));
		// 全角カタカナチェック
        valid.addValidation(new ZenkakuKanaValidator());

        valid.validate(errors);
	}

	/**
	 * お名前（フリガナ）_名 バリデーション<br/>
	 * <ul>
	 * <li>必須チェック</li>
	 * <li>全角カタカナチェック</li>
	 * <li>桁数チェック</li>
	 * </ul>
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	private void validFnameKana(List<ValidationFailure> errors) {
        ValidationChain valid = new ValidationChain("inquiryGeneral.input.fnameKana", this.getInquiryHeaderForm().getFnameKana());

        // 必須チェック
        valid.addValidation(new NullOrEmptyCheckValidation());
        // 桁数チェック
        valid.addValidation(new MaxLengthValidation(30));
		// 全角カタカナチェック
        valid.addValidation(new ZenkakuKanaValidator());

        valid.validate(errors);
	}

	/**
     * お問い合わせ内容詳細 バリデーション<br/>
     * ・桁数チェック
     * <br/>
     * @param errors エラー情報を格納するリストオブジェクト
     */
	private void validInquiryText(List<ValidationFailure> errors) {
        ValidationChain valid = new ValidationChain("inquiryGeneral.input.inquiryText", this.getInquiryHeaderForm().getInquiryText());
        // 桁数チェック
        valid.addValidation(new MaxLengthValidation(1000));
        valid.validate(errors);

	}

	/**
     * 日時 バリデーション<br/>
     * ・「お問い合わせ内容」で「セミナー・イベントに関して」を選択した場合のみ必須チェック
     * ・桁数チェック
     * ・半角数字チェック
     * ・日時の有効チェック
     * <br/>
     * @param errors エラー情報を格納するリストオブジェクト
     */
	private void validEventDateTime(List<ValidationFailure> errors) throws ParseException {
		int size = errors.size();

		String inquiryDtlType = null;
		if (this.getInquiryHeaderForm().getInquiryDtlType() != null && this.getInquiryHeaderForm().getInquiryDtlType().length > 0) {
			inquiryDtlType = this.getInquiryHeaderForm().getInquiryDtlType()[0];
		}

        if (PanaCommonConstant.INQUIRY_DTL_TYPE_SEMINAR.equals(inquiryDtlType)){
        	/** 日時（月）のバリデーション */
            ValidationChain eventDateTimeMonth = new ValidationChain("inquiryGeneral.input.eventDateTimeMonth", this.getEventDatetimeMonth());
            // 必須入力チェック
            eventDateTimeMonth.addValidation(new NullOrEmptyCheckValidation());
            // 桁数チェック
            eventDateTimeMonth.addValidation(new MaxLengthValidation(2));
            // 半角数字チェック
            eventDateTimeMonth.addValidation(new NumericValidation());
            eventDateTimeMonth.validate(errors);

        	/** 日時（日）のバリデーション */
            ValidationChain eventDateTimeDay = new ValidationChain("inquiryGeneral.input.eventDateTimeDay", this.getEventDatetimeDay());
            // 必須入力チェック
            eventDateTimeDay.addValidation(new NullOrEmptyCheckValidation());
            // 桁数チェック
            eventDateTimeDay.addValidation(new MaxLengthValidation(2));
            // 半角数字チェック
            eventDateTimeDay.addValidation(new NumericValidation());
            eventDateTimeDay.validate(errors);

        	/** 日時（時）のバリデーション */
            ValidationChain eventDateTimeHour = new ValidationChain("inquiryGeneral.input.eventDateTimeHour", this.getEventDatetimeHour());
            // 必須入力チェック
            eventDateTimeHour.addValidation(new NullOrEmptyCheckValidation());
            // 桁数チェック
            eventDateTimeHour.addValidation(new MaxLengthValidation(2));
            // 半角数字チェック
            eventDateTimeHour.addValidation(new NumericValidation());
            eventDateTimeHour.validate(errors);

        	/** 日時（分）のバリデーション */
            ValidationChain eventDateTimeMinute = new ValidationChain("inquiryGeneral.input.eventDateTimeMinute", this.getEventDatetimeMinute());
            // 必須入力チェック
            eventDateTimeMinute.addValidation(new NullOrEmptyCheckValidation());
            // 桁数チェック
            eventDateTimeMinute.addValidation(new MaxLengthValidation(2));
            // 半角数字チェック
            eventDateTimeMinute.addValidation(new NumericValidation());
            eventDateTimeMinute.validate(errors);

        	/** 日時の有効チェック */
            // 上記日時バリデーションにエラーがない場合、チェックする
            if (size == errors.size())
            {
        		DateFormat format =  new SimpleDateFormat("yyyy");
        		String year = format.format(new Date());

				String datetime = year
						+ "-"
						+ (this.getEventDatetimeMonth().length() == 1 ? ("0" + this
								.getEventDatetimeMonth()) : this
								.getEventDatetimeMonth())
						+ "-"
						+ (this.getEventDatetimeDay().length() == 1 ? ("0" + this
								.getEventDatetimeDay()) : this
								.getEventDatetimeDay())
						+ " "
						+ (this.getEventDatetimeHour().length() == 1 ? ("0" + this
								.getEventDatetimeHour()) : this
								.getEventDatetimeHour())
						+ ":"
						+ (this.getEventDatetimeMinute().length() == 1 ? ("0" + this
								.getEventDatetimeMinute()) : this
								.getEventDatetimeMinute()) + ":00";

        		this.setEventDatetime(datetime);
        		try {
        			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        			if (!sdf.format(sdf.parse(this.getEventDatetime())).equals(
        					this.getEventDatetime())) {
            			errors.add(new ValidationFailure("validDateTime", "日時",  "YYYY/MM/DD hh:mm", null));
        			}
        		} catch (ParseException localParseException) {
        			errors.add(new ValidationFailure("validDateTime", "日時", "YYYY/MM/DD hh:mm", null ));
        		}

            }
        }

	}

	/**
     * セミナー・イベント名 バリデーション<br/>
     * ・「お問い合わせ内容」で「セミナー・イベントに関して」を選択した場合のみ必須チェック
     * ・桁数チェック
     * <br/>
     * @param errors エラー情報を格納するリストオブジェクト
     */
	private void validEventName(List<ValidationFailure> errors) {
		String inquiryDtlType = null;
		if (this.getInquiryHeaderForm().getInquiryDtlType() != null && this.getInquiryHeaderForm().getInquiryDtlType().length > 0) {
			inquiryDtlType = this.getInquiryHeaderForm().getInquiryDtlType()[0];
		}
        if (PanaCommonConstant.INQUIRY_DTL_TYPE_SEMINAR.equals(inquiryDtlType)){
            ValidationChain valid = new ValidationChain("inquiryGeneral.input.eventName", this.getEventName());
            // 必須入力チェック
            valid.addValidation(new NullOrEmptyCheckValidation());
            // 桁数チェック
            valid.addValidation(new MaxLengthValidation(50));
            valid.validate(errors);
        }

	}

	/**
     * お問い合わせ種別 バリデーション<br/>
     * ・必須チェック
     * <br/>
     * @param errors エラー情報を格納するリストオブジェクト
     */
	private void validInquiryDtlType(List<ValidationFailure> errors) {
		String inquiryDtlType = null;
		if (this.getInquiryHeaderForm().getInquiryDtlType() != null && this.getInquiryHeaderForm().getInquiryDtlType().length > 0) {
			inquiryDtlType = this.getInquiryHeaderForm().getInquiryDtlType()[0];
		}
        ValidationChain valid = new ValidationChain("inquiryGeneral.input.inquiryDtlType", inquiryDtlType);
        // 必須入力チェック
        valid.addValidation(new NullOrEmptyCheckValidation());
        // パターン入力チェック
        valid.addValidation(new CodeLookupValidation(this.codeLookupManager, "inquiry_dtl_type"));
        valid.validate(errors);

	}



	/**
	 * 汎用問合せ情報のバリーオブジェクトを作成する。<br/>
	 * <br/>
	 * @param inquiryGeneral
	 * @param InquiryId
	 * @return 汎用問合せ情報バリーオブジェクトの配列
	 */
	public void copyToInquiryGeneral(InquiryGeneral[] inquiryGeneral, String InquiryId) throws ParseException {

		// お問合せIDを設定
		inquiryGeneral[0].setInquiryId(InquiryId);

        if (PanaCommonConstant.INQUIRY_DTL_TYPE_SEMINAR.equals(this.getInquiryHeaderForm().getInquiryDtlType()[0])){
			// イベント・セミナー名
			inquiryGeneral[0].setEventName(this.eventName);

			// イベント・セミナー日時
			DateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			inquiryGeneral[0].setEventDatetime(format.parse(this.eventDatetime));
        }

		// 連絡方法を設定
		inquiryGeneral[0].setContactType(this.inquiryContactType);
		this.setInquiryContactTypeMail(this.codeLookupManager.lookupValue("inquiry_contact_type", this.inquiryContactType));
		this.setInquiryDtlTypeMail(this.codeLookupManager.lookupValue("inquiry_dtl_type", this.getInquiryHeaderForm().getInquiryDtlType()[0]));

		// 連絡可能な時間帯を設定
		StringBuffer contactTime = new StringBuffer();
		StringBuffer contactTimeMail = new StringBuffer();

		if(this.inquiryContactTime != null){
			for (int i = 0; i < this.inquiryContactTime.length; i++) {
				if (!"".equals(this.inquiryContactTime[i])) {
					contactTime.append(this.inquiryContactTime[i]).append(",");
					contactTimeMail.append(this.codeLookupManager.lookupValue("inquiry_contact_time", this.inquiryContactTime[i])).append("  ");
				}
			}
			// アイコン情報を設定
			if (contactTime.length() > 0) {
				inquiryGeneral[0].setContactTime(contactTime.toString().substring(0, contactTime.toString().length() - 1));
				this.setInquiryContactTimeMail(contactTimeMail.toString());
			}
		} else {
			inquiryGeneral[0].setContactTime(contactTime.toString());
		}
	}

	/**
	 * 問合せアンケートのバリーオブジェクトを作成する。<br/>
	 * <br/>
	 * @param inquiryHousingQuestion　
	 * @return 問合せアンケートバリーオブジェクトの配列
	 */
	public void copyToInquiryHousingQuestion(InquiryHousingQuestion[] inquiryHousingQuestion, String inquiryId) {

		int i = 0;

		if (this.getAnsCd() != null) {
			for (String ansCd : this.getAnsCd()) {
				InquiryHousingQuestion question = new InquiryHousingQuestion();
				// お問合せIDを設定
				question.setInquiryId(inquiryId);
				// アンケート番号
				question.setCategoryNo(PanaCommonConstant.COMMON_CATEGORY_NO);
				// 回答CD
				question.setAnsCd(ansCd);
				// その他入力
				if ("008".equals(ansCd)) {
					question.setNote(this.getEtcAnswer1());
				}

				if ("009".equals(ansCd)) {
					question.setNote(this.getEtcAnswer2());
				}

				if ("010".equals(ansCd)) {
					question.setNote(this.getEtcAnswer3());
				}

				inquiryHousingQuestion[i] = question;
				i++;
			}
		}

	}

}
