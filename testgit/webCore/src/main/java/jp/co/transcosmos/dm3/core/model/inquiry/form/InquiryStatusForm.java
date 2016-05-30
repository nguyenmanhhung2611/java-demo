package jp.co.transcosmos.dm3.core.model.inquiry.form;

import java.util.Date;
import java.util.List;

import jp.co.transcosmos.dm3.core.model.inquiry.InquiryInterface;
import jp.co.transcosmos.dm3.core.util.LengthValidationUtils;
import jp.co.transcosmos.dm3.core.vo.InquiryHeader;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;
import jp.co.transcosmos.dm3.validation.CodeLookupValidation;
import jp.co.transcosmos.dm3.validation.MaxLengthValidation;
import jp.co.transcosmos.dm3.validation.Validateable;
import jp.co.transcosmos.dm3.validation.ValidationChain;
import jp.co.transcosmos.dm3.validation.ValidationFailure;


/**
 * 問合せステータスの入力パラメータ受取り用フォーム.
 * <p>
 * <pre>
 * 担当者       修正日      修正内容
 * ------------ ----------- -----------------------------------------------------
 * Y.Chou		2015.04.03	新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 * 
 */
public class InquiryStatusForm implements Validateable {
	
	/** command パラメータ */
	private String command;
	/** お問合せID */
	private String inquiryId;
	/** お問合せ区分 */
	private String inquiryType;
	/** 対応ステータス */
	private String answerStatus;
	/** 対応内容 */
	private String answerText;
	
	/** レングスバリデーションで使用する文字列長を取得するユーティリティ */
	protected LengthValidationUtils lengthUtils;
	
	/** 共通コード変換処理 */
	protected CodeLookupManager codeLookupManager;
	
	/**
	 * コンストラクター<br/>
	 * Factory 以外からのインスタンス生成を制限する為、コンストラクターを隠蔽する。<br/>
	 * <br/>
	 */
	protected InquiryStatusForm(){
		super();
	}
	
	/**
	 * コンストラクター<br/>
	 * Factory 以外からのインスタンス生成を制限する為、コンストラクターを隠蔽する。<br/>
	 * <br/>
	 * @param lengthUtils レングスバリデーションで使用する文字列長を取得するユーティリティ
	 * @param codeLookupManager 共通コード変換処理
	 */
	protected InquiryStatusForm(LengthValidationUtils lengthUtils, CodeLookupManager codeLookupManager){
		super();
		this.lengthUtils = lengthUtils;
		this.codeLookupManager = codeLookupManager;
	}
	
	/**
	 * command パラメータを設定する。<br/>
	 * <br/>
	 * @param command command パラメータ
	 */
	public String getCommand() {
		return command;
	}

	/**
	 * command パラメータを取得する。<br/>
	 * <br/>
	 * @return command パラメータ
	 */
	public void setCommand(String command) {
		this.command = command;
	}
	
	/**
	 * お問合せID を取得する。<br/>
	 * <br/>
	 *
	 * @return お問合せID
	 */
	public String getInquiryId() {
		return inquiryId;
	}

	/**
	 * お問合せID を設定する。<br/>
	 * <br/>
	 *
	 * @param inquiryId
	 */
	public void setInquiryId(String inquiryId) {
		this.inquiryId = inquiryId;
	}
	
	/**
	 * お問合せ区分 を取得する。<br/>
	 * <br/>
	 *
	 * @return お問合せ区分
	 */
	public String getInquiryType() {
		return inquiryType;
	}

	/**
	 * お問合せ区分 を設定する。<br/>
	 * <br/>
	 *
	 * @param inquiryType
	 */
	public void setInquiryType(String inquiryType) {
		this.inquiryType = inquiryType;
	}
	
	/**
	 * 対応ステータス を取得する。<br/>
	 * <br/>
	 *
	 * @return 対応ステータス
	 */
	public String getAnswerStatus() {
		return answerStatus;
	}

	/**
	 * 対応ステータス を設定する。<br/>
	 * <br/>
	 *
	 * @param answerStatus
	 */
	public void setAnswerStatus(String answerStatus) {
		this.answerStatus = answerStatus;
	}

	/**
	 * 対応内容 を取得する。<br/>
	 * <br/>
	 *
	 * @return 対応内容
	 */
	public String getAnswerText() {
		return answerText;
	}

	/**
	 * 対応内容 を設定する。<br/>
	 * <br/>
	 *
	 * @param answerText
	 */
	public void setAnswerText(String answerText) {
		this.answerText = answerText;
	}
	
	/**
	 * 引数で渡されたお知らせ情報のバリーオブジェクトにフォームの値を設定する。<br/>
	 * 更新処理でも使用する事を考慮し、タイムスタンプ情報に関しては、更新日、更新者のみ設定する。<br/>
	 * <br/>
	 * @param inquiryHeader 値を設定するお問合せヘッダ情報のバリーオブジェクト
	 * 
	 */
	public void copyToInquiryHeader(InquiryHeader inquiryHeader, String editUserId) {
		
		// 対応ステータスを設定
		inquiryHeader.setAnswerStatus(this.answerStatus);
		
		// 対応内容を設定
		inquiryHeader.setAnswerText(this.answerText);

		// 更新日付を設定
		inquiryHeader.setUpdDate(new Date());;

		// 更新担当者を設定
		inquiryHeader.setUpdUserId(editUserId);

	}
	
	/**
	 * 渡されたバリーオブジェクトから Form へ初期値を設定する。<br/>
	 * <br/>
	 * @param inquiry InquiryInterface　を実装した問合せ情報管理用バリーオブジェクト
	 */
	public void setDefaultData(InquiryInterface inquiry) {

		// バリーオブジェクトに格納されているパスワードはハッシュ値なので Form には設定しない。
		// パスワードの入力は新規登録時のみ必須で、更新処理時は任意の入力となる。
		// 更新処理でパスワードの入力が行われない場合、パスワードの更新は行わない。

		// 対応ステータス を設定
		this.answerStatus = inquiry.getInquiryHeaderInfo().getInquiryHeader().getAnswerStatus();
		// 対応内容 を設定
		this.answerText = inquiry.getInquiryHeaderInfo().getInquiryHeader().getAnswerText();
		
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
        
        // ステータス入力チェック
        validAnswerStatus(errors);
        
        // 対応内容入力チェック
        validAnswerText(errors);

        return (startSize == errors.size());
	}
	
	/**
	 * 対応ステータス バリデーション<br/>
	 * ・桁数チェック
	 * ・パターンチェック
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	protected void validAnswerStatus(List<ValidationFailure> errors) {
        ValidationChain valAnswerStatus = new ValidationChain("inquiry.input.answerStatus",this.answerStatus);
        // 桁数チェック
        valAnswerStatus.addValidation(
        		new MaxLengthValidation(this.lengthUtils.getLength("inquiry.input.answerStatus", 1)));
        // パターンチェック
        valAnswerStatus.addValidation(new CodeLookupValidation(this.codeLookupManager,"inquiry_answerStatus"));
        valAnswerStatus.validate(errors);
	}
	
	/**
	 * 対応内容  バリデーション<br/>
	 * ・桁数チェック
	 * <br/>
	 * @param errors エラー情報を格納するリストオブジェクト
	 */
	protected void validAnswerText(List<ValidationFailure> errors) {
        ValidationChain valAnswerText = new ValidationChain("inquiry.input.answerText",this.answerText);
        // 桁数チェック
        valAnswerText.addValidation(
        		new MaxLengthValidation(this.lengthUtils.getLength("inquiry.input.answerText", 1000)));
        valAnswerText.validate(errors);
	}

}
