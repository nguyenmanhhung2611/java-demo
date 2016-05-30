package jp.co.transcosmos.dm3.corePana.vo;

import java.util.Date;

/**
 * 管理サイトログ.
 * <p>
 * クラス名、および、フィールド名はＤＢテーブル名、列名にマッピングされる。
 * <p>
 * <pre>
 * 担当者        修正日        修正内容
 * ------------ ----------- -----------------------------------------------------
 * Tanaka Shinichi        2015.08.11  新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 * 個別カスタマイズで継承される可能性があるので、直接インスタンスを生成しない事。<br/>
 * インスタンスを取得する場合は、ValueObjectFactory から取得する事。<br/>
 *
 */
public class AdminLog {
	/** 管理サイトログID */
	private String adminLogId;
	/** 管理者ユーザーID */
	private String adminUserId;
	/** メッセージ */
	private String msg;
	/** 機能CD */
	private String functionCd;
	/** 登録日 */
	private Date insDate;


	public String getAdminLogId() {
		return adminLogId;
	}

	public void setAdminLogId(String adminLogId) {
		this.adminLogId = adminLogId;
	}

	public String getAdminUserId() {
		return adminUserId;
	}

	public void setAdminUserId(String adminUserId) {
		this.adminUserId = adminUserId;
	}

	public String getFunctionCd() {
		return functionCd;
	}

	public void setFunctionCd(String functionCd) {
		this.functionCd = functionCd;
	}

	public Date getInsDate() {
		return insDate;
	}

	public void setInsDate(Date insDate) {
		this.insDate = insDate;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
}
