package jp.co.transcosmos.dm3.core.vo;

import java.util.Date;

/**
 * お問合せヘッダ.
 * <p>
 * クラス名、および、フィールド名はＤＢテーブル名、列名にマッピングされる。
 * <p>
 *
 * <pre>
 * 担当者        修正日        修正内容
 * ------------ ----------- -----------------------------------------------------
 * Trans        2015.03.10     新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 * 個別カスタマイズで継承される可能性があるので、直接インスタンスを生成しない事。<br/>
 * インスタンスを取得する場合は、ValueObjectFactory から取得する事。<br/>
 *
 */
public class InquiryHeader {

	/** お問合せID */
	private String inquiryId;
	/** お問合せ区分 */
	private String inquiryType;
	/** ユーザーID */
	private String userId;
	/** 氏名(姓) */
	private String lname;
	/** 氏名(名) */
	private String fname;
	/** 氏名・カナ(姓) */
	private String lnameKana;
	/** 氏名・カナ(名) */
	private String fnameKana;
	/** メールアドレス */
	private String email;
	/** 電話番号 */
	private String tel;
	/** お問合せ内容 */
	private String inquiryText;
	/** お問合せ日時 */
	private Date inquiryDate;
	/** 対応ステータス */
	private String answerStatus;
	/** 対応内容 */
	private String answerText;
	/** 開封日 */
	private Date openDate;
	/** 開封者 */
	private String openUserId;
	/** 対応完了日 */
	private Date answerCompDate;
	/** 対応完了者 */
	private String answerCompUserId;
	/** 登録日 */
	private Date insDate;
	/** 登録者 */
	private String insUserId;
	/** 最終更新日 */
	private Date updDate;
	/** 最終更新者 */
	private String updUserId;

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
	 * ユーザーID を取得する。<br/>
	 * <br/>
	 *
	 * @return ユーザーID
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * ユーザーID を設定する。<br/>
	 * <br/>
	 *
	 * @param userId
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * 氏名(姓) を取得する。<br/>
	 * <br/>
	 *
	 * @return 氏名(姓)
	 */
	public String getLname() {
		return lname;
	}

	/**
	 * 氏名(姓) を設定する。<br/>
	 * <br/>
	 *
	 * @param lname
	 */
	public void setLname(String lname) {
		this.lname = lname;
	}

	/**
	 * 氏名(名) を取得する。<br/>
	 * <br/>
	 *
	 * @return 氏名(名)
	 */
	public String getFname() {
		return fname;
	}

	/**
	 * 氏名(名) を設定する。<br/>
	 * <br/>
	 *
	 * @param fname
	 */
	public void setFname(String fname) {
		this.fname = fname;
	}

	/**
	 * 氏名・カナ(姓) を取得する。<br/>
	 * <br/>
	 *
	 * @return 氏名・カナ(姓)
	 */
	public String getLnameKana() {
		return lnameKana;
	}

	/**
	 * 氏名・カナ(姓) を設定する。<br/>
	 * <br/>
	 *
	 * @param lnameKana
	 */
	public void setLnameKana(String lnameKana) {
		this.lnameKana = lnameKana;
	}

	/**
	 * 氏名・カナ(名) を取得する。<br/>
	 * <br/>
	 *
	 * @return 氏名・カナ(名)
	 */
	public String getFnameKana() {
		return fnameKana;
	}

	/**
	 * 氏名・カナ(名) を設定する。<br/>
	 * <br/>
	 *
	 * @param fnameKana
	 */
	public void setFnameKana(String fnameKana) {
		this.fnameKana = fnameKana;
	}

	/**
	 * メールアドレス を取得する。<br/>
	 * <br/>
	 *
	 * @return メールアドレス
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * メールアドレス を設定する。<br/>
	 * <br/>
	 *
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * 電話番号 を取得する。<br/>
	 * <br/>
	 *
	 * @return 電話番号
	 */
	public String getTel() {
		return tel;
	}

	/**
	 * 電話番号 を設定する。<br/>
	 * <br/>
	 *
	 * @param tel
	 */
	public void setTel(String tel) {
		this.tel = tel;
	}

	/**
	 * お問合せ内容 を取得する。<br/>
	 * <br/>
	 *
	 * @return お問合せ内容
	 */
	public String getInquiryText() {
		return inquiryText;
	}

	/**
	 * お問合せ内容 を設定する。<br/>
	 * <br/>
	 *
	 * @param inquiryText
	 */
	public void setInquiryText(String inquiryText) {
		this.inquiryText = inquiryText;
	}

	/**
	 * お問合せ日時 を取得する。<br/>
	 * <br/>
	 *
	 * @return お問合せ日時
	 */
	public Date getInquiryDate() {
		return inquiryDate;
	}

	/**
	 * お問合せ日時 を設定する。<br/>
	 * <br/>
	 *
	 * @param inquiryDate
	 */
	public void setInquiryDate(Date inquiryDate) {
		this.inquiryDate = inquiryDate;
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
	 * 開封日 を取得する。<br/>
	 * <br/>
	 *
	 * @return 開封日
	 */
	public Date getOpenDate() {
		return openDate;
	}

	/**
	 * 開封日 を設定する。<br/>
	 * <br/>
	 *
	 * @param openDate
	 */
	public void setOpenDate(Date openDate) {
		this.openDate = openDate;
	}

	/**
	 * 開封者 を取得する。<br/>
	 * <br/>
	 *
	 * @return 開封者
	 */
	public String getOpenUserId() {
		return openUserId;
	}

	/**
	 * 開封者 を設定する。<br/>
	 * <br/>
	 *
	 * @param openUserId
	 */
	public void setOpenUserId(String openUserId) {
		this.openUserId = openUserId;
	}

	/**
	 * 対応完了日 を取得する。<br/>
	 * <br/>
	 *
	 * @return 対応完了日
	 */
	public Date getAnswerCompDate() {
		return answerCompDate;
	}

	/**
	 * 対応完了日 を設定する。<br/>
	 * <br/>
	 *
	 * @param answerCompDate
	 */
	public void setAnswerCompDate(Date answerCompDate) {
		this.answerCompDate = answerCompDate;
	}

	/**
	 * 対応完了者 を取得する。<br/>
	 * <br/>
	 *
	 * @return 対応完了者
	 */
	public String getAnswerCompUserId() {
		return answerCompUserId;
	}

	/**
	 * 対応完了者 を設定する。<br/>
	 * <br/>
	 *
	 * @param answerCompUserId
	 */
	public void setAnswerCompUserId(String answerCompUserId) {
		this.answerCompUserId = answerCompUserId;
	}

	/**
	 * 登録日 を取得する。<br/>
	 * <br/>
	 *
	 * @return 登録日
	 */
	public Date getInsDate() {
		return insDate;
	}

	/**
	 * 登録日 を設定する。<br/>
	 * <br/>
	 *
	 * @param insDate
	 */
	public void setInsDate(Date insDate) {
		this.insDate = insDate;
	}

	/**
	 * 登録者 を取得する。<br/>
	 * <br/>
	 *
	 * @return 登録者
	 */
	public String getInsUserId() {
		return insUserId;
	}

	/**
	 * 登録者 を設定する。<br/>
	 * <br/>
	 *
	 * @param insUserId
	 */
	public void setInsUserId(String insUserId) {
		this.insUserId = insUserId;
	}

	/**
	 * 最終更新日 を取得する。<br/>
	 * <br/>
	 *
	 * @return 最終更新日
	 */
	public Date getUpdDate() {
		return updDate;
	}

	/**
	 * 最終更新日 を設定する。<br/>
	 * <br/>
	 *
	 * @param updDate
	 */
	public void setUpdDate(Date updDate) {
		this.updDate = updDate;
	}

	/**
	 * 最終更新者 を取得する。<br/>
	 * <br/>
	 *
	 * @return 最終更新者
	 */
	public String getUpdUserId() {
		return updUserId;
	}

	/**
	 * 最終更新者 を設定する。<br/>
	 * <br/>
	 *
	 * @param updUserId
	 */
	public void setUpdUserId(String updUserId) {
		this.updUserId = updUserId;
	}
}
