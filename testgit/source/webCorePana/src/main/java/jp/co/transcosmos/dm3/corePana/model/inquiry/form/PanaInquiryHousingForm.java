package jp.co.transcosmos.dm3.corePana.model.inquiry.form;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import jp.co.transcosmos.dm3.core.model.housing.Housing;
import jp.co.transcosmos.dm3.core.model.inquiry.form.HousingInquiryForm;
import jp.co.transcosmos.dm3.core.model.inquiry.form.InquiryHeaderForm;
import jp.co.transcosmos.dm3.core.util.LengthValidationUtils;
import jp.co.transcosmos.dm3.core.validation.ZenkakuKanaValidator;
import jp.co.transcosmos.dm3.core.vo.BuildingDtlInfo;
import jp.co.transcosmos.dm3.core.vo.BuildingInfo;
import jp.co.transcosmos.dm3.core.vo.BuildingStationInfo;
import jp.co.transcosmos.dm3.core.vo.HousingImageInfo;
import jp.co.transcosmos.dm3.core.vo.InquiryHousing;
import jp.co.transcosmos.dm3.core.vo.PrefMst;
import jp.co.transcosmos.dm3.core.vo.RouteMst;
import jp.co.transcosmos.dm3.core.vo.RrMst;
import jp.co.transcosmos.dm3.core.vo.StationMst;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonConstant;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonParameters;
import jp.co.transcosmos.dm3.corePana.util.PanaCalcUtil;
import jp.co.transcosmos.dm3.corePana.util.PanaFileUtil;
import jp.co.transcosmos.dm3.corePana.util.PanaStringUtils;
import jp.co.transcosmos.dm3.corePana.vo.HousingInfo;
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
import jp.co.transcosmos.dm3.validation.NumericValidation;
import jp.co.transcosmos.dm3.validation.Validateable;
import jp.co.transcosmos.dm3.validation.ValidationChain;
import jp.co.transcosmos.dm3.validation.ValidationFailure;
import jp.co.transcosmos.dm3.validation.ZenkakuOnlyValidation;

/**
 * 物件のお問い合わせ入力パラメータ受取り用フォーム.
 * <p>
 * <pre>
 * 担当者       修正日      修正内容
 * ------------ ----------- -----------------------------------------------------
 * 郭中レイ    2015.04.23 新規作成
 *
 * 注意事項
 *
 * </pre>
 */
public class PanaInquiryHousingForm extends HousingInquiryForm implements Validateable {

	/** レングスバリデーションで使用する文字列長を取得するユーティリティ */
	protected LengthValidationUtils lengthUtils;

	/** 共通コード変換処理 */
	protected CodeLookupManager codeLookupManager;

	/**
	* コンストラクター<br/>
	* Factory 以外からのインスタンス生成を制限する為、コンストラクターを隠蔽する。<br/>
	* <br/>
	*/
	protected PanaInquiryHousingForm(){
		super();
	}

	/**
	* コンストラクター<br/>
	* Factory 以外からのインスタンス生成を制限する為、コンストラクターを隠蔽する。<br/>
	* <br/>
	* @param lengthUtils レングスバリデーションで使用する文字列長を取得するユーティリティ
	* @param codeLookupManager 共通コード変換処理
	*/
	protected PanaInquiryHousingForm(LengthValidationUtils lengthUtils, CodeLookupManager codeLookupManager){
		super();
		this.lengthUtils = lengthUtils;
		this.codeLookupManager = codeLookupManager;
	}

	/** Urlパターン */
	private String urlPattern;
	/** お問い合わせ番号 */
	private String inquiryId;
	/** 物件番号 */
	private String housingCd;
	/** command パラメータ */
	private String command;
	/** お問合せ内容（表示用） */
	private String showInquiryText;
	/** 都道府県名 */
	private String prefName;
	/** 都道府県back */
	private String backPrefCd;
	/** 連絡方法 */
	private String contactType;
	/** 連絡可能な時間帯 */
	private String[] contactTime;
	/** アンケート回答格納 */
	private String[] ansCd;
	/** アンケート回答格納（入力１） */
	private String etcAnswer1;
	/** アンケート回答格納（入力２） */
	private String etcAnswer2;
	/** アンケート回答格納（入力３） */
	private String etcAnswer3;

	/** お問合せ内容（メール表示用） */
	private String inquiryDtlTypeMail;

	/**
	* Urlパターン を取得する。<br/>
	* <br/>
	* @return Urlパターン
	*/
	public String getUrlPattern() {
		return urlPattern;
	}

	/**
	* Urlパターン を設定する。<br/>
	* <br/>
	* @param urlPattern Urlパターン
	*/
	public void setUrlPattern(String urlPattern) {
		this.urlPattern = urlPattern;
	}

	/**
	* お問い合わせ番号 を取得する。<br/>
	* <br/>
	* @return お問い合わせ番号
	*/
	public String getInquiryId() {
		return inquiryId;
	}

	/**
	* お問い合わせ番号 を設定する。<br/>
	* <br/>
	* @param inquiryId お問い合わせ番号
	*/
	public void setInquiryId(String inquiryId) {
		this.inquiryId = inquiryId;
	}

	/**
	* 物件番号 を取得する。<br/>
	* <br/>
	* @return 物件番号
	*/
	public String getHousingCd() {
		return housingCd;
	}

	/**
	* 物件番号 を設定する。<br/>
	* <br/>
	* @param housingCd 物件番号
	*/
	public void setHousingCd(String housingCd) {
		this.housingCd = housingCd;
	}

	/**
	* command パラメータを取得する。<br/>
	* <br/>
	* @return command パラメータ
	*/
	public String getCommand() {
		return command;
	}

	/**
	* command パラメータを設定する。<br/>
	* <br/>
	* @param command command パラメータ
	*/
	public void setCommand(String command) {
		this.command = command;
	}

	/**
	* お問合せ内容（表示用） を取得する。<br/>
	* <br/>
	* @return お問合せ内容（表示用）
	*/
	public String getShowInquiryText() {
		return showInquiryText;
	}

	/**
	* お問合せ内容（表示用） を設定する。<br/>
	* <br/>
	* @param showInquiryText
	*/
	public void setShowInquiryText(String showInquiryText) {
		this.showInquiryText = showInquiryText;
	}

	/**
	* 都道府県名を取得する。<br/>
	* <br/>
	* @return 都道府県名
	*/
	public String getPrefName() {
		return prefName;
	}

	/**
	* 都道府県名を設定する。<br/>
	* <br/>
	* @param prefName 都道府県名
	*/
	public void setPrefName(String prefName) {
		this.prefName = prefName;
	}

	/**
	*都道府県backを取得する。<br/>
	* <br/>
	* @return 都道府県back
	*/
	public String getBackPrefCd() {
		return backPrefCd;
	}

	/**
	* 都道府県backを設定する。<br/>
	* <br/>
	* @param backPrefCd 都道府県back
	*/
	public void setBackPrefCd(String backPrefCd) {
		this.backPrefCd = backPrefCd;
	}

	/**
	* 連絡方法を取得する。<br/>
	* <br/>
	* @return 連絡方法
	*/
	public String getContactType() {
		return contactType;
	}

	/**
	* 連絡方法を設定する。<br/>
	* <br/>
	* @param contactType 連絡方法
	*/
	public void setContactType(String contactType) {
		this.contactType = contactType;
	}

	/**
	* 連絡可能な時間帯を取得する。<br/>
	* <br/>
	* @return 連絡可能な時間帯
	*/
	public String[] getContactTime() {
		return contactTime;
	}

	/**
	* 連絡可能な時間帯を設定する。<br/>
	* <br/>
	* @param contactTime 連絡可能な時間帯
	*/
	public void setContactTime(String[] contactTime) {
		this.contactTime = contactTime;
	}

	/**
	* アンケート回答格納を取得する。<br/>
	* <br/>
	* @return アンケート回答格納
	*/
	public String[] getAnsCd() {
		return ansCd;
	}

	/**
	* アンケート回答格納を設定する。<br/>
	* <br/>
	* @param ansCd アンケート回答格納
	*/
	public void setAnsCd(String[] ansCd) {
		this.ansCd = ansCd;
	}

	/**
	* アンケート回答格納（入力１）を取得する。<br/>
	* <br/>
	* @return アンケート回答格納（入力１）
	*/
	public String getEtcAnswer1() {
		return etcAnswer1;
	}

	/**
	* アンケート回答格納（入力１）を設定する。<br/>
	* <br/>
	* @param etcAnswer1 アンケート回答格納（入力１）
	*/
	public void setEtcAnswer1(String etcAnswer1) {
		this.etcAnswer1 = etcAnswer1;
	}

	/**
	* アンケート回答格納（入力２）を取得する。<br/>
	* <br/>
	* @return アンケート回答格納（入力２）
	*/
	public String getEtcAnswer2() {
		return etcAnswer2;
	}

	/**
	* アンケート回答格納（入力２）を設定する。<br/>
	* <br/>
	* @param etcAnswer2 アンケート回答格納（入力２）
	*/
	public void setEtcAnswer2(String etcAnswer2) {
		this.etcAnswer2 = etcAnswer2;
	}

	/**
	* アンケート回答格納（入力３）を取得する。<br/>
	* <br/>
	* @return アンケート回答格納（入力３）
	*/
	public String getEtcAnswer3() {
		return etcAnswer3;
	}

	/**
	* アンケート回答格納（入力３）を設定する。<br/>
	* <br/>
	* @param etcAnswer3 アンケート回答格納（入力３）
	*/
	public void setEtcAnswer3(String etcAnswer3) {
		this.etcAnswer3 = etcAnswer3;
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
	* バリデーション処理<br/>
	* リクエストのバリデーションを行う<br/>
	* <br/>
	*
	* @param errors
	*            エラー情報を格納するリストオブジェクト
	* @return 正常時 true、エラー時 false
	*/

	public boolean validate(List<ValidationFailure> errors) {
		int startSize = errors.size();

		// お問い合わせ内容は必須入力する。
		valInquiryDtlType(errors);

        // お問い合わせ内容詳細の桁数チェック
        valInquiryText(errors);

        // お名前の入力チェック
        valLnameFname(errors);

        // お名前（フリガナ）の入力チェック
        valLnameKanaFnameKana(errors);

        // メールアドレスの入力チェック
        valEmail(errors);

        // 電話番号の入力チェック
        valTel(errors);

		// FAX番号の入力チェック
		valFax(errors);

		// 郵便番号の入力チェック
		valZip(errors);

		// 都道府県名の入力チェック
		valPrefName(errors);

		// 市区町村番地の入力チェック
		valAddress(errors);

		// 建物名の入力チェック
		valAddressOther(errors);

		// ご希望の連絡方法の入力チェック
		valContactType(errors);

		// 連絡可能な時間帯の入力チェック
		valContactTime(errors);

		// アンケート回答チェック
		validAnsCd(errors);

		// アンケート_その他回答チェック
		validEtcAnswer1(errors);

		// アンケート_その他回答チェック
		validEtcAnswer2(errors);

		// アンケート_その他回答チェック
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
     * お問い合わせ種別 バリデーション<br/>
     * ・必須チェック
     * <br/>
     * @param errors エラー情報を格納するリストオブジェクト
     */
    protected void valInquiryDtlType(List<ValidationFailure> errors) {
    	// お問い合わせ種別
		String inquiryDtlType = null;
		if (this.getInquiryHeaderForm().getInquiryDtlType() != null && this.getInquiryHeaderForm().getInquiryDtlType().length > 0) {
			inquiryDtlType = this.getInquiryHeaderForm().getInquiryDtlType()[0];
		}

		// お問い合わせ種別
		ValidationChain valinquiryDtlType = new ValidationChain("inquiryHousing.input.valInquiryDtlType", inquiryDtlType);
		// 必須入力チェック
		valinquiryDtlType.addValidation(new NullOrEmptyCheckValidation());
		// パターンチェック
		valinquiryDtlType.addValidation(new CodeLookupValidation(this.codeLookupManager, "inquiry_housing_dtl_type"));
		valinquiryDtlType.validate(errors);
    }

	/**
     * お問い合わせ内容 バリデーション<br/>
     * ・桁数チェック
     * <br/>
     * @param errors エラー情報を格納するリストオブジェクト
     */
    protected void valInquiryText(List<ValidationFailure> errors) {
    	// お問い合わせ内容
    	ValidationChain valInquiryText = new ValidationChain("inquiryHousing.input.valInquiryText", this.getInquiryHeaderForm().getInquiryText());
        // 桁数チェック
    	valInquiryText.addValidation(new MaxLengthValidation(300));
    	valInquiryText.validate(errors);
    }

	/**
     * お名前 バリデーション<br/>
     * ・入力チェック
     * <br/>
     * @param errors エラー情報を格納するリストオブジェクト
     */
    protected void valLnameFname(List<ValidationFailure> errors) {
    	// お名前_姓
    	ValidationChain valLname = new ValidationChain("inquiryHousing.input.valLname", this.getInquiryHeaderForm().getLname());
    	// 必須入力チェック
    	valLname.addValidation(new NullOrEmptyCheckValidation());
    	// 桁数チェック
    	valLname.addValidation(new MaxLengthValidation(30));
    	// 全角チェック
    	valLname.addValidation(new ZenkakuOnlyValidation());
    	valLname.validate(errors);

    	// お名前_名
    	ValidationChain valFname = new ValidationChain("inquiryHousing.input.valFname", this.getInquiryHeaderForm().getFname());
    	// 必須入力チェック
    	valFname.addValidation(new NullOrEmptyCheckValidation());
    	// 桁数チェック
    	valFname.addValidation(new MaxLengthValidation(30));
    	// 全角チェック
    	valFname.addValidation(new ZenkakuOnlyValidation());
    	valFname.validate(errors);
    }

	/**
     * お名前（フリガナ） バリデーション<br/>
     * ・入力チェック
     * <br/>
     * @param errors エラー情報を格納するリストオブジェクト
     */
    protected void valLnameKanaFnameKana(List<ValidationFailure> errors) {
    	// お名前（フリガナ）_姓
    	ValidationChain valLnameKana = new ValidationChain("inquiryHousing.input.valLnameKana", this.getInquiryHeaderForm().getLnameKana());
    	// 必須入力チェック
    	valLnameKana.addValidation(new NullOrEmptyCheckValidation());
    	// 桁数チェック
    	valLnameKana.addValidation(new MaxLengthValidation(30));
    	// 全角チェック
    	valLnameKana.addValidation(new ZenkakuKanaValidator());
    	valLnameKana.validate(errors);

    	// お名前（フリガナ）_名
    	ValidationChain valFnameKana = new ValidationChain("inquiryHousing.input.valFnameKana", this.getInquiryHeaderForm().getFnameKana());
    	// 必須入力チェック
    	valFnameKana.addValidation(new NullOrEmptyCheckValidation());
    	// 桁数チェック
    	valFnameKana.addValidation(new MaxLengthValidation(30));
    	// 全角チェック
    	valFnameKana.addValidation(new ZenkakuKanaValidator());
    	valFnameKana.validate(errors);
    }

	/**
     * メールアドレス バリデーション<br/>
     * ・入力チェック
     * <br/>
     * @param errors エラー情報を格納するリストオブジェクト
     */
    protected void valEmail(List<ValidationFailure> errors) {
    	// 全角数字⇒半角数字
    	this.getInquiryHeaderForm().setEmail(PanaStringUtils.changeToHankakuNumber(this.getInquiryHeaderForm().getEmail()));
    	// メールアドレス
    	ValidationChain valEmail = new ValidationChain("inquiryHousing.input.valEmail", this.getInquiryHeaderForm().getEmail());
    	// 必須チェック
    	valEmail.addValidation(new NullOrEmptyCheckValidation());
        // 桁数チェック
    	valEmail.addValidation(new MaxLengthValidation(255));
        // メールアドレスの書式チェック
    	valEmail.addValidation(new EmailRFCValidation());
    	valEmail.validate(errors);
    }

	/**
     * 電話番号 バリデーション<br/>
     * ・入力チェック
     * <br/>
     * @param errors エラー情報を格納するリストオブジェクト
     */
    protected void valTel(List<ValidationFailure> errors) {
    	// 全角数字⇒半角数字
   		this.getInquiryHeaderForm().setTel(PanaStringUtils.changeToHankakuNumber(this.getInquiryHeaderForm().getTel()));
    	// 電話番号
    	ValidationChain valTel = new ValidationChain("inquiryHousing.input.valTel", this.getInquiryHeaderForm().getTel());
    	// 必須チェック
    	valTel.addValidation(new NullOrEmptyCheckValidation());
        // 桁数チェック
    	valTel.addValidation(new MaxLengthValidation(11));
        // 半角数字チェック
    	valTel.addValidation(new NumericValidation());
    	valTel.validate(errors);
    }

	/**
     * FAX番号 バリデーション<br/>
     * ・入力チェック
     * <br/>
     * @param errors エラー情報を格納するリストオブジェクト
     */
    protected void valFax(List<ValidationFailure> errors) {
    	// 全角数字⇒半角数字
    	((PanaInquiryHeaderForm)this.getInquiryHeaderForm()).setFax(PanaStringUtils.changeToHankakuNumber(((PanaInquiryHeaderForm)this.getInquiryHeaderForm()).getFax()));
    	// FAX番号
    	ValidationChain valFax = new ValidationChain("inquiryHousing.input.valFax", ((PanaInquiryHeaderForm)this.getInquiryHeaderForm()).getFax());
    	// 桁数チェック
    	valFax.addValidation(new MaxLengthValidation(11));
    	// 半角数字チェック
    	valFax.addValidation(new NumericValidation());
    	valFax.validate(errors);
    }

	/**
     * 郵便番号 バリデーション<br/>
     * ・入力チェック
     * <br/>
     * @param errors エラー情報を格納するリストオブジェクト
     */
    protected void valZip(List<ValidationFailure> errors) {
    	// 全角数字⇒半角数字
    	((PanaInquiryHeaderForm)this.getInquiryHeaderForm()).setZip(PanaStringUtils.changeToHankakuNumber(((PanaInquiryHeaderForm) this.getInquiryHeaderForm()).getZip()));
		// 郵便番号
		String zip = ((PanaInquiryHeaderForm) this.getInquiryHeaderForm()).getZip();
		ValidationChain valZip = new ValidationChain("inquiryHousing.input.valZip", zip);
    	// 必須チェック
		valZip.addValidation(new NullOrEmptyCheckValidation());
		// 桁数チェック
		valZip.addValidation(new LengthValidator(7));
		// 半角数字チェック
		valZip.addValidation(new NumericValidation());
		valZip.validate(errors);
    }

	/**
     * 都道府県名 バリデーション<br/>
     * ・必須チェック
     * <br/>
     * @param errors エラー情報を格納するリストオブジェクト
     */
    protected void valPrefName(List<ValidationFailure> errors) {
    	// 都道府県名
    	ValidationChain valPrefName = new ValidationChain("inquiryHousing.input.valPrefName", ((PanaInquiryHeaderForm)this.getInquiryHeaderForm()).getPrefCd());
    	// 必須入力チェック
    	valPrefName.addValidation(new NullOrEmptyCheckValidation());
    	valPrefName.validate(errors);
    }

	/**
     * 市区町村番地 バリデーション<br/>
     * ・入力チェック
     * <br/>
     * @param errors エラー情報を格納するリストオブジェクト
     */
    protected void valAddress(List<ValidationFailure> errors) {
    	// 市区町村番地
    	ValidationChain valAddress = new ValidationChain("inquiryHousing.input.valAddress", ((PanaInquiryHeaderForm)this.getInquiryHeaderForm()).getAddress());
    	// 必須入力チェック
    	valAddress.addValidation(new NullOrEmptyCheckValidation());
    	// 桁数チェック
    	valAddress.addValidation(new MaxLengthValidation(50));
    	valAddress.validate(errors);
    }

	/**
     * 建物名 バリデーション<br/>
     * ・入力チェック
     * <br/>
     * @param errors エラー情報を格納するリストオブジェクト
     */
    protected void valAddressOther(List<ValidationFailure> errors) {
    	// 建物名
    	ValidationChain valAddressOther = new ValidationChain("inquiryHousing.input.valAddressOther", ((PanaInquiryHeaderForm)this.getInquiryHeaderForm()).getAddressOther());
    	// 桁数チェック
    	valAddressOther.addValidation(new MaxLengthValidation(30));
    	valAddressOther.validate(errors);
    }

	/**
	* ご希望の連絡方法 バリデーション<br/>
	* ・入力チェック <br/>
	*
	* @param errors
	*            エラー情報を格納するリストオブジェクト
	*/
	protected void valContactType(List<ValidationFailure> errors) {
		// ご希望の連絡方法
		ValidationChain valContactType = new ValidationChain("inquiryHousing.input.valContactType", this.getContactType());
		// パターンチェック
		valContactType.addValidation(new CodeLookupValidation(this.codeLookupManager, "inquiry_contact_type"));
		valContactType.validate(errors);
	}

	/**
	* 連絡可能な時間帯 バリデーション<br/>
	* ・入力チェック <br/>
	*
	* @param errors
	*            エラー情報を格納するリストオブジェクト
	*/
	protected void valContactTime(List<ValidationFailure> errors) {

		String[] contactTimes = getContactTime();
		if (contactTimes != null) {
			for (int i = 0; i < contactTimes.length; i++) {
				String contactTime = contactTimes[i];
				// 連絡可能な時間帯
				ValidationChain valContactTime = new ValidationChain("inquiryHousing.input.valContactTime", contactTime);
				// パターンチェック
				valContactTime.addValidation(new CodeLookupValidation(this.codeLookupManager,"inquiry_contact_time"));
				valContactTime.validate(errors);
			}
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

		String label = "inquiryHousing.input.ansCd";
		for (String ans : this.ansCd) {
			ValidationChain valid = new ValidationChain(label,ans);

			// パターン入力チェック
			valid.addValidation(new CodeLookupValidation(this.codeLookupManager, "ans_all"));

			valid.validate(errors);
		}
	}

	/**
	* アンケート_その他回答 バリデーション<br/>
	* ・桁数チェック
	*
	* @param errors エラー情報を格納するリストオブジェクト
	*/
	protected void validEtcAnswer1(List<ValidationFailure> errors) {

		if (this.ansCd == null || this.ansCd.length == 0) return;
		boolean checkFG = false;
		for (String ans : this.ansCd) {
			if ("008".equals(ans)) checkFG = true;
		}
		if (!checkFG) return;

		ValidationChain valid = new ValidationChain("inquiryHousing.input.etcAnswer1",this.getEtcAnswer1());
		// 桁数チェック
		valid.addValidation(new MaxLengthValidation(50));

		valid.validate(errors);
	}

	/**
	* アンケート_その他回答 バリデーション<br/>
	* ・桁数チェック
	*
	* @param errors エラー情報を格納するリストオブジェクト
	*/
	protected void validEtcAnswer2(List<ValidationFailure> errors) {

		if (this.ansCd == null || this.ansCd.length == 0) return;
		boolean checkFG = false;
		for (String ans : this.ansCd) {
			if ("009".equals(ans)) checkFG = true;
		}
		if (!checkFG) return;

		ValidationChain valid = new ValidationChain("inquiryHousing.input.etcAnswer2",this.getEtcAnswer2());
		// 桁数チェック
		valid.addValidation(new MaxLengthValidation(50));

		valid.validate(errors);
	}

	/**
	* アンケート_その他回答 バリデーション<br/>
	* ・桁数チェック
	*
	* @param errors エラー情報を格納するリストオブジェクト
	*/
	protected void validEtcAnswer3(List<ValidationFailure> errors) {

		if (this.ansCd == null || this.ansCd.length == 0) return;
		boolean checkFG = false;
		for (String ans : this.ansCd) {
			if ("010".equals(ans)) checkFG = true;
		}
		if (!checkFG) return;

		ValidationChain valid = new ValidationChain("inquiryHousing.input.etcAnswer3",this.getEtcAnswer3());
		// 桁数チェック
		valid.addValidation(new MaxLengthValidation(50));

		valid.validate(errors);
	}

	/**
	* 物件問合せ情報のバリーオブジェクトを作成する。<br/>
	* <br/>
	* @param inquiryHousing　
	* @return 物件問合せ情報バリーオブジェクトの配列
	*/
    @Override
	public void copyToInquiryHousing(InquiryHousing inquiryHousing) {

    	super.copyToInquiryHousing(inquiryHousing);

		// 連絡方法を設定
		((jp.co.transcosmos.dm3.corePana.vo.InquiryHousing)inquiryHousing).setContactType(this.contactType);

		// 連絡可能な時間帯を設定
		StringBuffer contactTime = new StringBuffer();

		// お問合せ内容（メール表示用）を設定
		this.setInquiryDtlTypeMail(this.codeLookupManager.lookupValue("inquiry_housing_dtl_type", this.getInquiryHeaderForm().getInquiryDtlType()[0]));

		if(this.contactTime != null){
			for (int i = 0; i < this.contactTime.length; i++) {
				if (!"".equals(this.contactTime[i])) {
					contactTime.append(this.contactTime[i]).append(",");
				}
			}
			// アイコン情報を設定
			if (contactTime.length() > 0) {
				((jp.co.transcosmos.dm3.corePana.vo.InquiryHousing)inquiryHousing).setContactTime(contactTime.toString().substring(0, contactTime.toString().length() - 1));
			}
		} else {
			((jp.co.transcosmos.dm3.corePana.vo.InquiryHousing)inquiryHousing).setContactTime(contactTime.toString());
		}

	}


	/**
	* 問合せアンケートのバリーオブジェクトを作成する。<br/>
	* <br/>
	* @param inquiryHousingQuestion　
	* @return 問合せアンケートバリーオブジェクトの配列
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

	/**
	* 渡されたバリーオブジェクトから初期値を設定する。<br/>
	* <br/>
	*/
	public void setDefaultDataHousing(Housing housing, PanaCommonParameters commonParameters, List<HousingImageInfo> housingImageInfoList, Map<String, Object> model, PanaFileUtil fileUtil) {

		// 物件基本情報を取得
		HousingInfo housingInfo = (HousingInfo)housing.getHousingInfo().getItems().get("housingInfo");
		// 建物基本情報を取得
		BuildingInfo buildingInfo = (BuildingInfo)housing.getBuilding().getBuildingInfo().getItems().get("buildingInfo");
		// 建物詳細情報を取得
		BuildingDtlInfo buildingDtlInfo = (BuildingDtlInfo)housing.getBuilding().getBuildingInfo().getItems().get("buildingDtlInfo");
		// 都道府県マスタを取得
		PrefMst prefMst = (PrefMst)housing.getBuilding().getBuildingInfo().getItems().get("prefMst");

		// 1週間以内（システム日付 - 最終更新日<= 7日）に追加された物件に対して新着アイコンを出す。
		// 最終更新日を取得する。
		Date updDate = housingInfo.getUpdDate();
		// システム日期
		Date now = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(now);
		double nowTime = cal.getTimeInMillis();
		double updTime = 0;
		if (updDate != null) {
			cal.setTime(updDate);
			updTime = cal.getTimeInMillis();
		} else {
			cal.setTime(now);
			updTime = cal.getTimeInMillis();
		}
		// 1週間以内（システム日付 - 最終更新日<= 7日）計算
		double betweenDays = (nowTime - updTime) / (1000 * 3600 * 24);
		String dateFlg = "0";
		// 物件に対して新着アイコンを出す。
		if (betweenDays <= 7) {
			dateFlg = "1";
		}

		// 物件画像を設定する。
		String pathName = "";
		if (housingImageInfoList.size() > 0) {
			jp.co.transcosmos.dm3.corePana.vo.HousingImageInfo housingImageInfo = (jp.co.transcosmos.dm3.corePana.vo.HousingImageInfo) housingImageInfoList.get(0);
			if(housingImageInfo != null){
				if (!StringValidateUtil.isEmpty(housingImageInfo.getRoleId())) {
					if (PanaCommonConstant.ROLE_ID_PUBLIC.equals(housingImageInfo.getRoleId())) {
						// 閲覧権限が全員の場合
						pathName = fileUtil.getHousFileOpenUrl(housingImageInfo.getPathName(), housingImageInfo.getFileName(), commonParameters.getHousingInquiryImageSize());
					} else if (PanaCommonConstant.ROLE_ID_PRIVATE.equals(housingImageInfo.getRoleId())) {
						// 閲覧権限が会員のみの場合
						pathName = fileUtil.getHousFileMemberUrl(housingImageInfo.getPathName(), housingImageInfo.getFileName(), commonParameters.getHousingInquiryImageSize());
					}
				}
			}
		}


		// 所在地を設定する。
		StringBuffer address = new StringBuffer();
		if (!StringValidateUtil.isEmpty(buildingInfo.getPrefCd())) {
			if (prefMst.getPrefName() != null) {
				address.append(prefMst.getPrefName()).append(" ");
				address.append(buildingInfo.getAddressName() == null?"":buildingInfo.getAddressName()).append(" ");
				address.append(buildingInfo.getAddressOther1() == null?"":buildingInfo.getAddressOther1()).append(" ");
				address.append(buildingInfo.getAddressOther2() == null?"":buildingInfo.getAddressOther2());
			}
		}


		// アクセスを設定する。
		// 建物最寄り駅情報Listを取得
		List<JoinResult> buildingStationInfoList = housing.getBuilding().getBuildingStationInfoList();
		BuildingStationInfo buildingStationInfo = new BuildingStationInfo();
		RrMst rrMst = new RrMst();
		RouteMst routeMst = new RouteMst();
		StationMst stationMst = new StationMst();
		StringBuffer nearStation = new StringBuffer();
		List<String> nearStationList = new ArrayList<String>();
		for (int i = 0; i < buildingStationInfoList.size(); i++) {

			// 建物最寄り駅情報の取得
			buildingStationInfo = new BuildingStationInfo();
			buildingStationInfo =  (BuildingStationInfo)buildingStationInfoList.get(i).getItems().get("buildingStationInfo");
			// 鉄道会社マスタの取得
			rrMst = new RrMst();
			rrMst = (RrMst)buildingStationInfoList.get(i).getItems().get("rrMst");
			// 路線マストの取得
			routeMst = new RouteMst();
			routeMst = (RouteMst)buildingStationInfoList.get(i).getItems().get("routeMst");
			// 駅マストの取得
			stationMst = new StationMst();
			stationMst = (StationMst)buildingStationInfoList.get(i).getItems().get("stationMst");

			// アクセス
			nearStation = new StringBuffer();
			// 鉄道会社名
			if (!StringValidateUtil.isEmpty(rrMst.getRrName())) {
				nearStation.append(rrMst.getRrName());
			}

			// 代表路線名
			if (!StringValidateUtil.isEmpty(routeMst.getRouteName())) {
				nearStation.append(routeMst.getRouteName()).append(" ");
			} else {
				if (!StringValidateUtil.isEmpty(buildingStationInfo.getDefaultRouteName())) {
					nearStation.append(buildingStationInfo.getDefaultRouteName()).append(" ");
				}
			}
			// 駅名
			if (!StringValidateUtil.isEmpty(stationMst.getStationName())) {
				nearStation.append(stationMst.getStationName()).append("駅").append(" ");
			} else {
				if (!StringValidateUtil.isEmpty(buildingStationInfo.getStationName())) {
					nearStation.append(buildingStationInfo.getStationName()).append("駅").append(" ");
				}
			}
			// バス会社名
			if (!StringValidateUtil.isEmpty(buildingStationInfo.getBusCompany())) {
				nearStation.append(buildingStationInfo.getBusCompany()).append(" ");
			}
			// バス停からの徒歩時間
			if (buildingStationInfo.getTimeFromBusStop() != null) {
				nearStation.append("徒歩").append(String.valueOf(buildingStationInfo.getTimeFromBusStop())).append("分");
			}
			nearStationList.add(nearStation.toString());
		}


		// 築年月
		String compDate = "";
		if (buildingInfo != null && buildingInfo.getCompDate() != null) {
			compDate = new SimpleDateFormat("yyyy年M月築").format(buildingInfo.getCompDate());
		}

		// 物件種類CDを判断
		String personalArea = "";
		String personalAreaSquare = "";
		String buildingArea = "";
		String buildingAreaSquare = "";
		String landArea = "";
		String landAreaSquare = "";
		// 階建
		String totalFloor = "";
		// 所在階
		String floorNo = "";
		if (PanaCommonConstant.HOUSING_KIND_CD_MANSION.equals(buildingInfo.getHousingKindCd())) {
			if (housingInfo.getPersonalArea() != null) {
				// 専有面積
				personalArea = housingInfo.getPersonalArea().toString();
				// 専有面積 坪
				personalAreaSquare = ((housingInfo.getPersonalArea() == null) ? "" : "（約" + PanaCalcUtil.calcTsubo(housingInfo.getPersonalArea()).toString() + "坪）") +
						(housingInfo.getPersonalAreaMemo() == null? "" : housingInfo.getPersonalAreaMemo()) ;
			}
			// 階建／所在階
			if (buildingInfo != null) {
				// 階建
				totalFloor = (buildingInfo.getTotalFloors() == null) ? "" : buildingInfo.getTotalFloors() + "階建";
				// 所在階
				floorNo = (housingInfo.getFloorNo() == null) ? "" : housingInfo.getFloorNo() + "階";
			}
		} else {
			if (buildingDtlInfo != null && buildingDtlInfo.getBuildingArea() != null) {
				// 建物面積
				buildingArea = buildingDtlInfo.getBuildingArea().toString();
				// 建物面積　坪
				buildingAreaSquare = ((buildingDtlInfo.getBuildingArea() == null) ? "" : "（約" + PanaCalcUtil.calcTsubo(buildingDtlInfo.getBuildingArea()).toString() + "坪）") +
						(buildingDtlInfo.getBuildingAreaMemo() == null? "" : buildingDtlInfo.getBuildingAreaMemo()) ;
			}
			if (housingInfo.getLandArea() != null) {
				// 土地面積
				landArea = housingInfo.getLandArea().toString();
				// 土地面積　坪
				landAreaSquare = ((housingInfo.getLandArea() == null) ? "" : "（約" + PanaCalcUtil.calcTsubo(housingInfo.getLandArea()).toString() + "坪）") +
						(housingInfo.getLandAreaMemo() == null? "" : housingInfo.getLandAreaMemo()) ;
			}
		}

		// 都道府県back
		this.backPrefCd = buildingInfo.getPrefCd();

		// 1週間以内計算情報
		model.put("dateFlg", dateFlg);
		// 建物基本情報
		model.put("buildingInfo", buildingInfo);
		// 物件基本情報
		model.put("housingInfo", housingInfo);
		// 物件画像情報
		model.put("pathName", pathName);
		// 所在地情報
		model.put("address", address);
		// アクセス情報
		model.put("nearStationList", nearStationList);
		// 築年月
		model.put("compDate", compDate);
		// 専有面積
		model.put("personalArea", personalArea);
		// 専有面積 坪
		model.put("personalAreaSquare", personalAreaSquare);
		// 建物面積
		model.put("buildingArea", buildingArea);
		// 建物面積 坪
		model.put("buildingAreaSquare", buildingAreaSquare);
		// 土地面積
		model.put("landArea", landArea);
		// 土地面積 坪
		model.put("landAreaSquare", landAreaSquare);
		// 階建、所在階
		model.put("totalFloor", totalFloor);
		model.put("floorNo", floorNo);
	}

	/**
	* 渡されたバリーオブジェクトから Form へ初期値を設定する。<br/>
	* <br/>
	*/
	public void setDefaultDataUserInfo(JoinResult userInfo, InquiryHeaderForm inquiryHeaderForm) {

		// マイページ会員情報を取得
		if(userInfo != null){
			MemberInfo memberInfo = (MemberInfo)userInfo.getItems().get("memberInfo");
			PrefMst prefMst_member = (PrefMst)userInfo.getItems().get("prefMst");
			if(memberInfo != null){
				// お名前_姓
				inquiryHeaderForm.setLname(memberInfo.getMemberLname());
				// お名前_名
				inquiryHeaderForm.setFname(memberInfo.getMemberFname());
				// お名前（フリガナ）_姓
				inquiryHeaderForm.setLnameKana(memberInfo.getMemberLnameKana());
				// お名前（フリガナ）_名
				inquiryHeaderForm.setFnameKana(memberInfo.getMemberFnameKana());
				// メールアドレス
				inquiryHeaderForm.setEmail(memberInfo.getEmail());
				// 電話番号
				inquiryHeaderForm.setTel(memberInfo.getTel());
				// FAX番号
				((PanaInquiryHeaderForm)inquiryHeaderForm).setFax(memberInfo.getFax());
				// 郵便番号
				((PanaInquiryHeaderForm)inquiryHeaderForm).setZip(memberInfo.getZip());
				// 都道府県key
				((PanaInquiryHeaderForm)inquiryHeaderForm).setPrefCd(memberInfo.getPrefCd());
				// 都道府県名
				if(prefMst_member.getPrefName() != null){
					this.prefName = prefMst_member.getPrefName();
				}
				// 市区町村番地
				((PanaInquiryHeaderForm)inquiryHeaderForm).setAddress(memberInfo.getAddress());
				// 建物名
				((PanaInquiryHeaderForm)inquiryHeaderForm).setAddressOther(memberInfo.getAddressOther());
			}
		}
		this.setCommonInquiryForm(inquiryHeaderForm);
	}
}
