package jp.co.transcosmos.dm3.corePana.model.inquiry.form;

import java.util.List;

import jp.co.transcosmos.dm3.core.model.inquiry.form.InquirySearchForm;
import jp.co.transcosmos.dm3.core.util.LengthValidationUtils;
import jp.co.transcosmos.dm3.corePana.validation.DateFromToValidation;
import jp.co.transcosmos.dm3.dao.DAOCriteria;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;
import jp.co.transcosmos.dm3.validation.AlphanumericOnlyValidation;
import jp.co.transcosmos.dm3.validation.AsciiOnlyValidation;
import jp.co.transcosmos.dm3.validation.CodeLookupValidation;
import jp.co.transcosmos.dm3.validation.MaxLengthValidation;
import jp.co.transcosmos.dm3.validation.ValidDateValidation;
import jp.co.transcosmos.dm3.validation.Validateable;
import jp.co.transcosmos.dm3.validation.ValidationChain;
import jp.co.transcosmos.dm3.validation.ValidationFailure;
/**
 * お問合せ情報（ヘッダ）の検索パラメータ、および、画面コントロールパラメータ受取り用フォーム.
 * 検索条件の取得や、ＤＢ検索オブジェクトの生成を行う。<br/>
 * <p>
 * <pre>
 * 担当者       修正日      修正内容
 * ------------ ----------- -----------------------------------------------------
 * 郭中レイ		2015.03.18	新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 *
 */
public class PanaInquirySearchForm extends InquirySearchForm implements Validateable {

	/** 検索画面の command */
	private String command;
	/** 物件番号　（検索条件） */
	private String keyHousingCd;
	/** 物件名　（検索条件） */
	private String keyDisplayHousingName;
	/** 会員番号　（検索条件） */
	private String keyUserId;
	/** 問合日時開始日　（検索条件） */
	private String keyInquiryDateStart;
	/** 問合日時終了日　（検索条件） */
	private String keyInquiryDateEnd;
	/** 問合種別　（検索条件） */
	private String[] keyInquiryType;
	/** お問合せ内容種別　（検索条件） */
	private String[] keyInquiryDtlType;
	/** お問合せID_削除用 */
	private String keyInquiryId;

	/** レングスバリデーションで使用する文字列長を取得するユーティリティ */
	protected LengthValidationUtils lengthUtils;

	/** 共通コード変換処理 */
	protected CodeLookupManager codeLookupManager;


	/**
	 * コンストラクター<br/>
	 * Factory 以外からのインスタンス生成を制限する為、コンストラクターを隠蔽する。<br/>
	 * <br/>
	 */
	protected PanaInquirySearchForm(){
		super();
	}

	/**
	 * コンストラクター<br/>
	 * Factory 以外からのインスタンス生成を制限する為、コンストラクターを隠蔽する。<br/>
	 * <br/>
	 * @param lengthUtils レングスバリデーションで使用する文字列長を取得するユーティリティ
	 * @param codeLookupManager 共通コード変換処理
	 */
	protected PanaInquirySearchForm(LengthValidationUtils lengthUtils, CodeLookupManager codeLookupManager){
		super();
		this.lengthUtils = lengthUtils;
		this.codeLookupManager = codeLookupManager;
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
	 * @return keyHousingCd
	 */
	public String getKeyHousingCd() {
		return keyHousingCd;
	}

	/**
	 * @param keyHousingCd セットする keyHousingCd
	 */
	public void setKeyHousingCd(String keyHousingCd) {
		this.keyHousingCd = keyHousingCd;
	}

	/**
	 * @return keyDisplayHousingName
	 */
	public String getKeyDisplayHousingName() {
		return keyDisplayHousingName;
	}

	/**
	 * @param keyDisplayHousingName セットする keyDisplayHousingName
	 */
	public void setKeyDisplayHousingName(String keyDisplayHousingName) {
		this.keyDisplayHousingName = keyDisplayHousingName;
	}

	/**
	 * @return keyUserId
	 */
	public String getKeyUserId() {
		return keyUserId;
	}

	/**
	 * @param keyUserId セットする keyUserId
	 */
	public void setKeyUserId(String keyUserId) {
		this.keyUserId = keyUserId;
	}

	/**
	 * @return keyInquiryDateStart
	 */
	public String getKeyInquiryDateStart() {
		return keyInquiryDateStart;
	}

	/**
	 * @param keyInquiryDateStart セットする keyInquiryDateStart
	 */
	public void setKeyInquiryDateStart(String keyInquiryDateStart) {
		this.keyInquiryDateStart = keyInquiryDateStart;
	}

	/**
	 * @return keyInquiryDateEnd
	 */
	public String getKeyInquiryDateEnd() {
		return keyInquiryDateEnd;
	}

	/**
	 * @param keyInquiryDateEnd セットする keyInquiryDateEnd
	 */
	public void setKeyInquiryDateEnd(String keyInquiryDateEnd) {
		this.keyInquiryDateEnd = keyInquiryDateEnd;
	}

	/**
	 * @return keyInquiryType
	 */
	public String[] getKeyInquiryType() {
		return keyInquiryType;
	}

	/**
	 * @param keyInquiryType セットする keyInquiryType
	 */
	public void setKeyInquiryType(String[] keyInquiryType) {
		this.keyInquiryType = keyInquiryType;
	}

	/**
	 * @return keyInquiryDtlType
	 */
	public String[] getKeyInquiryDtlType() {
		return keyInquiryDtlType;
	}

	/**
	 * @param keyInquiryDtlType セットする keyInquiryDtlType
	 */
	public void setKeyInquiryDtlType(String[] keyInquiryDtlType) {
		this.keyInquiryDtlType = keyInquiryDtlType;
	}

	/**
	 * @return keyInquiryId
	 */
	public String getKeyInquiryId() {
		return keyInquiryId;
	}

	/**
	 * @param keyInquiryId セットする keyInquiryId
	 */
	public void setKeyInquiryId(String keyInquiryId) {
		this.keyInquiryId = keyInquiryId;
	}

	/**
     * 検索条件オブジェクトを作成する。<br/>
     * PagingListForm に実装されている、ページ処理用の検索条件生成処理を拡張し、受け取ったリクエスト
     * パラメータによる検索条件を生成する。<br/>
     * <br/>
     * @return 検索条件オブジェクト
     */
    @Override
    public DAOCriteria buildCriteria() {

        // 検索用オブジェクトの生成
        // ページ処理を行う場合、PagingListForm　インターフェースを実装した Form から
        // DAOCriteria を生成する必要がある。 よって、自身の buildCriteria() を
        // 使用して検索条件を生成している。
        return buildCriteria(super.buildCriteria());

    }

	/**
	 * 検索条件オブジェクトを作成する。<br/>
	 * PagingListForm に実装されている、ページ処理用の検索条件生成処理を拡張し、受け取ったリクエスト
	 * パラメータによる検索条件を生成する。<br/>
	 * <br/>
	 * このメソッド内では、DB の物理フィールド名を指定しているコードが存在する。<br/>
	 * その為、、管理ユーザー情報のテーブルとして、Information 以外を使用した場合、このメソッドを
	 * オーバーライドする必要がある。<br/>
	 * <br/>
	 * @return 検索条件オブジェクト
	 */
	public DAOCriteria buildCriteria(DAOCriteria criteria){

		// 物件番号の検索条件文生成
		if (!StringValidateUtil.isEmpty(this.keyHousingCd)) {
			criteria.addWhereClause("housingCd", this.keyHousingCd);
		}

		// 物件名の検索条件文生成
		if (!StringValidateUtil.isEmpty(this.keyDisplayHousingName)) {
			criteria.addWhereClause("displayHousingName", "%" + this.keyDisplayHousingName + "%",
					DAOCriteria.LIKE_CASE_INSENSITIVE);
		}

		// 会員番号の検索条件文生成
		if (!StringValidateUtil.isEmpty(this.keyUserId)) {
			criteria.addWhereClause("userId", this.keyUserId);
		}

		// メールアドレスの検索条件文生成
		if (!StringValidateUtil.isEmpty(this.getKeyEmail())) {
			criteria.addWhereClause("email", this.getKeyEmail());
		}

		// 問合日時開始日の検索条件文生成
        if (!StringValidateUtil.isEmpty(this.keyInquiryDateStart)) {
            criteria.addWhereClause("inquiryDate", this.keyInquiryDateStart + " 00:00:00", DAOCriteria.GREATER_THAN_EQUALS);
        }

        // 問合日時開始日の検索条件文生成
        if (!StringValidateUtil.isEmpty(this.keyInquiryDateStart)) {
            criteria.addWhereClause("inquiryDate", this.keyInquiryDateStart + " 00:00:00", DAOCriteria.LESS_THAN_EQUALS);
        }

		// 対応ステータスの検索条件文生成
		if (!StringValidateUtil.isEmpty(this.getKeyAnswerStatus())) {
			criteria.addWhereClause("answerStatus", this.getKeyAnswerStatus());
		}

		// 問合種別の検索条件文生成

		// お問合せ内容種別の検索条件文生成

		// 物件問合番号の検索条件文生成
		if (!StringValidateUtil.isEmpty(this.getInquiryId())) {
			criteria.addWhereClause("inquiryId", this.getInquiryId());
		}

        return criteria;

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

        // 物件番号入力チェック
        validHousingCd(errors);

        // 物件名入力チェック
        validDisplayHousingName(errors);

        // 会員番号入力チェック
        validUserId(errors);

        // メールアドレス入力チェック
        validKeyEmail(errors);

        // 問合日時開始日入力チェック
        validInquiryDateStart(errors);

        // 問合日時終了日入力チェック
        validInquiryDateEnd(errors);

        // 問合種別入力チェック
        validInquiryType(errors);

        // 問合番号入力チェック
        validInquiryId(errors);

        // 対応ステータス入力チェック
        validKeyAnswerStatus(errors);

        // 問合日時開始日 < 問合日時終了日チェック
        validInquiryDateCom(errors);

        // 物件番号、物件名、問合種別の入力チェック
        validInquiryTypeJoin(errors);

        return (startSize == errors.size());
	}

	/**
	 * 物件番号バリデーション<br/>
	 * ・桁数チェック
	 * ・半角英数字チェック
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	protected void validHousingCd(List<ValidationFailure> errors) {
        ValidationChain valHousingCd = new ValidationChain("inquiryList.search.housingCd",this.keyHousingCd);
        // 桁数チェック
        valHousingCd.addValidation(new MaxLengthValidation(this.lengthUtils.getLength("inquiryList.search.housingCd", 10)));
        // 半角英数字チェック
        valHousingCd.addValidation(new AlphanumericOnlyValidation());
        valHousingCd.validate(errors);
	}

	/**
	 * 物件名バリデーション<br/>
	 * ・桁数チェック
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	protected void validDisplayHousingName(List<ValidationFailure> errors) {
        ValidationChain valDisplayHousingName = new ValidationChain("inquiryList.search.displayHousingName",this.keyDisplayHousingName);
        // 桁数チェック
        valDisplayHousingName.addValidation(new MaxLengthValidation(this.lengthUtils.getLength("inquiryList.search.displayHousingName", 25)));
        valDisplayHousingName.validate(errors);
	}

	/**
	 * 会員番号 バリデーション<br/>
	 * ・桁数チェック
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	protected void validUserId(List<ValidationFailure> errors) {
        ValidationChain valUserId = new ValidationChain("inquiryList.search.userId",this.keyUserId);
        // 桁数チェック
        valUserId.addValidation(new MaxLengthValidation(this.lengthUtils.getLength("inquiryList.search.userId", 20)));
        // 半角英数字チェック
        valUserId.addValidation(new AlphanumericOnlyValidation());
        valUserId.validate(errors);
	}

	/**
	 * メールアドレス バリデーション<br/>
	 * ・桁数チェック
	 * ・メールアドレスの書式チェック
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	protected void validKeyEmail(List<ValidationFailure> errors) {
        ValidationChain valEmail = new ValidationChain("inquiryList.search.keyEmail",this.getKeyEmail());
        // 桁数チェック
        valEmail.addValidation(new MaxLengthValidation(this.lengthUtils.getLength("inquiryList.search.keyEmail", 255)));
        // 半角文字チェック
        valEmail.addValidation(new AsciiOnlyValidation());
        valEmail.validate(errors);
	}

	/**
	 * 問合日時開始日 バリデーション<br/>
	 * ・日付書式チェック
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	protected void validInquiryDateStart(List<ValidationFailure> errors) {
        ValidationChain valInquiryDateStart = new ValidationChain("inquiryList.search.inquiryDateStart",this.keyInquiryDateStart);
        // 日付書式チェック
        valInquiryDateStart.addValidation(new ValidDateValidation("yyyy/MM/dd"));
        valInquiryDateStart.validate(errors);
	}

	/**
	 *  問合日時終了日 バリデーション<br/>
	 * ・日付書式チェック
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	protected void validInquiryDateEnd(List<ValidationFailure> errors) {
        ValidationChain valInquiryDateEnd = new ValidationChain("inquiryList.search.inquiryDateEnd",this.keyInquiryDateEnd);
        // 日付書式チェック
        valInquiryDateEnd.addValidation(new ValidDateValidation("yyyy/MM/dd"));
        valInquiryDateEnd.validate(errors);
	}

	/**
	 *  問合種別 バリデーション<br/>
	 * ・パターンチェック
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	protected void validInquiryType(List<ValidationFailure> errors) {
		// 問合種別
		String[] inquiryType = this.keyInquiryType;

		if(null != inquiryType) {
			for(String str : inquiryType) {
		        ValidationChain valInquiryType = new ValidationChain("inquiryList.search.inquiryType", str);
		        // パターンチェック
		        valInquiryType.addValidation(new CodeLookupValidation(this.codeLookupManager, "inquiry_type"));
		        valInquiryType.validate(errors);
			}
		}

		// お問合せ内容種別
		String[] inquiryDtlType = this.keyInquiryDtlType;

		if(null != inquiryDtlType) {
			for(String str : inquiryDtlType) {
		        ValidationChain valInquiryDtlType = new ValidationChain("inquiryList.search.inquiryDtlType", str);
		        // パターンチェック
		        valInquiryDtlType.addValidation(new CodeLookupValidation(this.codeLookupManager, "inquiry_dtl_type"));
		        valInquiryDtlType.validate(errors);
			}
		}
	}

	/**
	 *  物件問合番号 バリデーション<br/>
	 * ・桁数チェック
	 * ・書式チェック
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	protected void validInquiryId(List<ValidationFailure> errors) {
        ValidationChain valInquiryId = new ValidationChain("inquiryList.search.inquiryId",this.keyInquiryId);
        // 桁数チェック
        valInquiryId.addValidation(new MaxLengthValidation(this.lengthUtils.getLength("inquiryList.search.inquiryId", 13)));
        // 半角英数字チェック
        valInquiryId.addValidation(new AlphanumericOnlyValidation());
        valInquiryId.validate(errors);
	}

	/**
	 * 対応ステータス バリデーション<br/>
	 * ・桁数チェック
	 * ・パターンチェック
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	protected void validKeyAnswerStatus(List<ValidationFailure> errors) {
        ValidationChain valAnswerStatus = new ValidationChain("inquiryList.search.keyAnswerStatus",this.getKeyAnswerStatus());
        // 桁数チェック
        valAnswerStatus.addValidation(
        		new MaxLengthValidation(this.lengthUtils.getLength("inquiryList.search.keyAnswerStatus", 1)));
        // パターンチェック
        valAnswerStatus.addValidation(new CodeLookupValidation(this.codeLookupManager,"inquiry_answerStatus"));
        valAnswerStatus.validate(errors);
	}

    /**
     * 問合日時開始日 < 問合日時終了日 バリデーション<br/>
     * ・日付比較チェック
     * <br/>
     * @param errors エラー情報を格納するリストオブジェクト
     */
    protected void validInquiryDateCom(List<ValidationFailure> errors) {
        ValidationChain valid = new ValidationChain("inquiryList.search.inquiryDateStart", this.keyInquiryDateStart);
        // 日付比較チェック
        valid.addValidation(new DateFromToValidation("yyyy/MM/dd", "問合日時終了日", this.keyInquiryDateEnd));
        valid.validate(errors);
    }

    /**
     * 物件番号、物件名、問合種別 バリデーション<br/>
     * ・物件番号、物件名、問合種別の入力チェック
     * <br/>
     * @param errors エラー情報を格納するリストオブジェクト
     */
    protected void validInquiryTypeJoin(List<ValidationFailure> errors) {
    	// 物件番号又は物件名を入力した場合
    	if(!StringValidateUtil.isEmpty(this.keyHousingCd) || !StringValidateUtil.isEmpty(this.keyDisplayHousingName)) {
    		// 問合種別に"査定問合せ"又は"汎用問合せ"を選択した場合
    		if(null != this.keyInquiryType) {
	    		for(String inquiryType : this.keyInquiryType) {
		    		if("01".equals(inquiryType) || "02".equals(inquiryType)) {
			            // 日付比較チェック
			    		String param[] = new String[] { "物件問合せ" };
		                ValidationFailure vf = new ValidationFailure(
		                        "inquiryTypeJoinError", "物件番号", "物件名", param);
		                errors.add(vf);
		                break;
		    		}
	    		}
	    	}
    	}
    }

}
