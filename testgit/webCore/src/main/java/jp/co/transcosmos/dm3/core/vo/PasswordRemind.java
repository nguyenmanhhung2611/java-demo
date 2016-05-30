package jp.co.transcosmos.dm3.core.vo;

import java.util.Date;

/**
 * パスワード問合せ.
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
public class PasswordRemind {

	/** 問い合わせID */
	private String remindId;
	/** ユーザーID */
	private String userId;
	/** 変更確定フラグ */
	private String commitFlg;
	/** 問合せ登録日 */
	private Date insDate;
	/** 問合せ登録者 */
	private String insUserId;
	/** 変更日 */
	private Date updDate;
	/** 変更者 */
	private String updUserId;

	/**
	 * 問い合わせID を取得する。<br/>
	 * <br/>
	 *
	 * @return 問い合わせID
	 */
	public String getRemindId() {
		return remindId;
	}

	/**
	 * 問い合わせID を設定する。<br/>
	 * <br/>
	 *
	 * @param remindId
	 */
	public void setRemindId(String remindId) {
		this.remindId = remindId;
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
	 * 変更確定フラグ を取得する。<br/>
	 * <br/>
	 *
	 * @return 変更確定フラグ
	 */
	public String getCommitFlg() {
		return commitFlg;
	}

	/**
	 * 変更確定フラグ を設定する。<br/>
	 * <br/>
	 *
	 * @param commitFlg
	 */
	public void setCommitFlg(String commitFlg) {
		this.commitFlg = commitFlg;
	}

	/**
	 * 問合せ登録日 を取得する。<br/>
	 * <br/>
	 *
	 * @return 問合せ登録日
	 */
	public Date getInsDate() {
		return insDate;
	}

	/**
	 * 問合せ登録日 を設定する。<br/>
	 * <br/>
	 *
	 * @param insDate
	 */
	public void setInsDate(Date insDate) {
		this.insDate = insDate;
	}

	/**
	 * 問合せ登録者 を取得する。<br/>
	 * <br/>
	 *
	 * @return 問合せ登録者
	 */
	public String getInsUserId() {
		return insUserId;
	}

	/**
	 * 問合せ登録者 を設定する。<br/>
	 * <br/>
	 *
	 * @param insUserId
	 */
	public void setInsUserId(String insUserId) {
		this.insUserId = insUserId;
	}

	/**
	 * 変更日 を取得する。<br/>
	 * <br/>
	 *
	 * @return 変更日
	 */
	public Date getUpdDate() {
		return updDate;
	}

	/**
	 * 変更日 を設定する。<br/>
	 * <br/>
	 *
	 * @param updDate
	 */
	public void setUpdDate(Date updDate) {
		this.updDate = updDate;
	}

	/**
	 * 変更者 を取得する。<br/>
	 * <br/>
	 *
	 * @return 変更者
	 */
	public String getUpdUserId() {
		return updUserId;
	}

	/**
	 * 変更者 を設定する。<br/>
	 * <br/>
	 *
	 * @param updUserId
	 */
	public void setUpdUserId(String updUserId) {
		this.updUserId = updUserId;
	}
}
