package jp.co.transcosmos.dm3.core.model.inquiry.form;

import java.util.List;

import jp.co.transcosmos.dm3.core.util.LengthValidationUtils;
import jp.co.transcosmos.dm3.dao.DAOCriteria;
import jp.co.transcosmos.dm3.dao.JoinResult;
import jp.co.transcosmos.dm3.form.PagingListForm;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;
import jp.co.transcosmos.dm3.validation.CodeLookupValidation;
import jp.co.transcosmos.dm3.validation.EmailRFCValidation;
import jp.co.transcosmos.dm3.validation.MaxLengthValidation;
import jp.co.transcosmos.dm3.validation.NumberValidation;
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
 * H.Mizuno		2015.03.18	新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 * 
 */
public class InquirySearchForm extends PagingListForm<JoinResult> implements Validateable {
	
	/** 検索画面の command */
	private String searchCommand;
	/** お問合せID */
	private String inquiryId;
	/** 氏名（姓）　（検索条件） */
	private String keyLname;
	/** 氏名（名）　（検索条件） */
	private String keyFname;
	/** 氏名・カナ（セイ）　（検索条件） */
	private String keyLnameKana;
	/** 氏名・カナ（メイ）　（検索条件） */
	private String keyFnameKana;
	/** メールアドレス　（検索条件） */
	private String keyEmail;
	/** 電話番号　（検索条件） */
	private String keyTel;
	/** 対応ステータス　（検索条件） */
	private String keyAnswerStatus;
	
	/** レングスバリデーションで使用する文字列長を取得するユーティリティ */
	protected LengthValidationUtils lengthUtils;
	
	/** 共通コード変換処理 */
	protected CodeLookupManager codeLookupManager;


	/**
	 * コンストラクター<br/>
	 * Factory 以外からのインスタンス生成を制限する為、コンストラクターを隠蔽する。<br/>
	 * <br/>
	 */
	protected InquirySearchForm(){
		super();
	}
	
	/**
	 * コンストラクター<br/>
	 * Factory 以外からのインスタンス生成を制限する為、コンストラクターを隠蔽する。<br/>
	 * <br/>
	 * @param lengthUtils レングスバリデーションで使用する文字列長を取得するユーティリティ
	 * @param codeLookupManager 共通コード変換処理
	 */
	protected InquirySearchForm(LengthValidationUtils lengthUtils, CodeLookupManager codeLookupManager){
		super();
		this.lengthUtils = lengthUtils;
		this.codeLookupManager = codeLookupManager;
	}
	
	/**
	 * お問合せIDを取得する。<br/>
	 * <br/>
	 * @return お問合せID
	 */
	public String getInquiryId() {
		return inquiryId;
	}


	/**
	 * お問合せIDを設定する。<br/>
	 * <br/>
	 * @param inquiryId お問合せID
	 */
	public void setInquiryId(String inquiryId) {
		this.inquiryId = inquiryId;
	}
	
	/**
	 * 検索画面の commandを取得する。<br/>
	 * <br/>
	 * @return 検索画面の command
	 */
	public String getSearchCommand() {
		return searchCommand;
	}

	/**
	 * 検索画面の commandを設定する。<br/>
	 * <br/>
	 * @param searchCommand 検索画面の command
	 */
	public void setSearchCommand(String searchCommand) {
		this.searchCommand = searchCommand;
	}

	/**
	 * 氏名（姓）　（検索条件）を取得する。<br/>
	 * <br/>
	 * @return 氏名（姓）　（検索条件）
	 */
	public String getKeyLname() {
		return keyLname;
	}

	/**
	 * 氏名（姓）　（検索条件）を設定する。<br/>
	 * <br/>
	 * @param keyLname 氏名（姓）　（検索条件）
	 */
	public void setKeyLname(String keyLname) {
		this.keyLname = keyLname;
	}
	
	/**
	 * 氏名（名）　（検索条件）を取得する。<br/>
	 * <br/>
	 * @return　氏名（名）　（検索条件）
	 */
	public String getKeyFname() {
		return keyFname;
	}

	/**
	 * 氏名（名）　（検索条件）を設定する。<br/>
	 * <br/>
	 * @param keyFname 氏名（名）　（検索条件）
	 */
	public void setKeyFname(String keyFname) {
		this.keyFname = keyFname;
	}

	/**
	 * 氏名・カナ（セイ）　（検索条件）を取得する。<br/>
	 * <br/>
	 * @return 氏名・カナ（セイ）　（検索条件）
	 */
	public String getKeyLnameKana() {
		return keyLnameKana;
	}

	/**
	 * 氏名・カナ（セイ）　（検索条件）を設定する。<br/>
	 * <br/>
	 * @param keyLnameKana 氏名・カナ（セイ）　（検索条件）
	 */
	public void setKeyLnameKana(String keyLnameKana) {
		this.keyLnameKana = keyLnameKana;
	}
	
	/**
	 * 氏名・カナ（メイ）　（検索条件）を取得する。<br/>
	 * <br/>
	 * @return 氏名・カナ（メイ）　（検索条件）
	 */
	public String getKeyFnameKana() {
		return keyFnameKana;
	}

	/**
	 * 氏名・カナ（メイ）　（検索条件）を設定する。<br/>
	 * <br/>
	 * @param keyFnameKana
	 */
	public void setKeyFnameKana(String keyFnameKana) {
		this.keyFnameKana = keyFnameKana;
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
	 * @param keyEmail メールアドレス　（検索条件）
	 */
	public void setKeyEmail(String keyEmail) {
		this.keyEmail = keyEmail;
	}

	/**
	 * 電話番号　（検索条件）を取得する。<br/>
	 * <br/>
	 * @return 電話番号　（検索条件）
	 */
	public String getKeyTel() {
		return keyTel;
	}

	/**
	 * 電話番号　（検索条件）を設定する。<br/>
	 * <br/>
	 * @param keyTel 電話番号　（検索条件）
	 */
	public void setKeyTel(String keyTel) {
		this.keyTel = keyTel;
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
	 * @param keyAnswerStatus　対応ステータス　（検索条件）
	 */
	public void setKeyAnswerStatus(String keyAnswerStatus) {
		this.keyAnswerStatus = keyAnswerStatus;
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
	@Override
	public DAOCriteria buildCriteria(){

		// 検索用オブジェクトの生成
		// ページ処理を行う場合、PagingListForm　インターフェースを実装した Form から
		// DAOCriteria を生成する必要がある。 よって、自身の buildCriteria() を
		// 使用して検索条件を生成している。
		DAOCriteria criteria = super.buildCriteria();
		
		// 氏名（姓）の検索条件文生成
		if (!StringValidateUtil.isEmpty(this.keyLname)) {
			criteria.addWhereClause("lname", "%" + this.keyLname + "%",
					DAOCriteria.LIKE_CASE_INSENSITIVE);
		}
		
		// 氏名（名）の検索条件文生成
		if (!StringValidateUtil.isEmpty(this.keyFname)) {
			criteria.addWhereClause("fname", "%" + this.keyFname + "%",
					DAOCriteria.LIKE_CASE_INSENSITIVE);
		}
		
		// 氏名・カナ(姓)の検索条件文生成
		if (!StringValidateUtil.isEmpty(this.keyLnameKana)) {
			criteria.addWhereClause("lnameKana", "%" + this.keyLnameKana + "%",
					DAOCriteria.LIKE_CASE_INSENSITIVE);
		}
		
		// 氏名・カナ(名)の検索条件文生成
		if (!StringValidateUtil.isEmpty(this.keyFnameKana)) {
			criteria.addWhereClause("fnameKana", "%" + this.keyFnameKana + "%",
					DAOCriteria.LIKE_CASE_INSENSITIVE);
		}

		// メールアドレスの検索条件文生成
		if (!StringValidateUtil.isEmpty(this.keyEmail)) {
			criteria.addWhereClause("email", this.keyEmail);
		}
		
		// 電話番号の検索条件文生成
		if (!StringValidateUtil.isEmpty(this.keyTel)) {
			criteria.addWhereClause("tel", this.keyTel);
		}
		
		// 対応ステータスの検索条件文生成
		if (!StringValidateUtil.isEmpty(this.keyAnswerStatus)) {
			criteria.addWhereClause("answerStatus", this.keyAnswerStatus);
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

        // 氏名（姓）入力チェック
        validKeyLname(errors);

        // 氏名（名）入力チェック
        validKeyFname(errors);

        // 氏名・カナ（セイ）入力チェック
        validKeyLnameKana(errors);

        // 氏名・カナ（メイ）入力チェック
        validKeyFnameKana(errors);

        // メールアドレス入力チェック
        validKeyEmail(errors);
        
        // 電話番号入力チェック
        validKeyTel(errors);
        
        // 対応ステータス入力チェック
        validKeyAnswerStatus(errors);

        return (startSize == errors.size());
	}
	
	/**
	 * 氏名（姓） バリデーション<br/>
	 * ・桁数チェック
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	protected void validKeyLname(List<ValidationFailure> errors) {
        ValidationChain valLname = new ValidationChain("inquiry.search.keyLname",this.keyLname);
        // 桁数チェック
        valLname.addValidation(new MaxLengthValidation(this.lengthUtils.getLength("inquiry.search.keyLname", 30)));
        valLname.validate(errors);
	}
	
	/**
	 * 氏名（名） バリデーション<br/>
	 * ・桁数チェック
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	protected void validKeyFname(List<ValidationFailure> errors) {
        ValidationChain valFname = new ValidationChain("inquiry.search.keyFname",this.keyFname);
        // 桁数チェック
        valFname.addValidation(new MaxLengthValidation(this.lengthUtils.getLength("inquiry.search.keyFname", 30)));
        valFname.validate(errors);
	}
	
	/**
	 * 氏名・カナ（セイ） バリデーション<br/>
	 * ・桁数チェック
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	protected void validKeyLnameKana(List<ValidationFailure> errors) {
        ValidationChain valLnameKana = new ValidationChain("inquiry.search.keyLnameKana",this.keyLnameKana);
        // 桁数チェック
        valLnameKana.addValidation(new MaxLengthValidation(this.lengthUtils.getLength("inquiry.search.keyLnameKana", 30)));
        valLnameKana.validate(errors);
	}
	
	/**
	 * 氏名・カナ（メイ） バリデーション<br/>
	 * ・桁数チェック
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	protected void validKeyFnameKana(List<ValidationFailure> errors) {
        ValidationChain valFnameKana = new ValidationChain("inquiry.search.keyFnameKana",this.keyFnameKana);
        // 桁数チェック
        valFnameKana.addValidation(new MaxLengthValidation(this.lengthUtils.getLength("inquiry.search.keyFnameKana", 30)));
        valFnameKana.validate(errors);
	}
	
	/**
	 * メールアドレス バリデーション<br/>
	 * ・桁数チェック
	 * ・メールアドレスの書式チェック
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	protected void validKeyEmail(List<ValidationFailure> errors) {
        ValidationChain valEmail = new ValidationChain("inquiry.search.keyEmail",this.keyEmail);
        // 桁数チェック
        valEmail.addValidation(new MaxLengthValidation(this.lengthUtils.getLength("inquiry.search.keyEmail", 255)));
		// メールアドレスの書式チェック
        valEmail.addValidation(new EmailRFCValidation());
        valEmail.validate(errors);
	}
	
	/**
	 * 電話番号 バリデーション<br/>
	 * ・桁数チェック
	 * ・数値チェック
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	protected void validKeyTel(List<ValidationFailure> errors) {
        ValidationChain valTel = new ValidationChain("inquiry.search.keyTel",this.keyTel);
        // 桁数チェック
        valTel.addValidation(new MaxLengthValidation(this.lengthUtils.getLength("inquiry.search.keyTel", 13)));
		// 数値チェック
        valTel.addValidation(new NumberValidation());
        valTel.validate(errors);
	}
	
	/**
	 * 対応ステータス バリデーション<br/>
	 * ・桁数チェック
	 * ・パターンチェック
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	protected void validKeyAnswerStatus(List<ValidationFailure> errors) {
        ValidationChain valAnswerStatus = new ValidationChain("inquiry.search.keyAnswerStatus",this.keyAnswerStatus);
        // 桁数チェック
        valAnswerStatus.addValidation(
        		new MaxLengthValidation(this.lengthUtils.getLength("inquiry.search.keyAnswerStatus", 1)));
        // パターンチェック
        valAnswerStatus.addValidation(new CodeLookupValidation(this.codeLookupManager,"inquiry_answerStatus"));
        valAnswerStatus.validate(errors);
	}


}
