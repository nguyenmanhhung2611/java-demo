package jp.co.transcosmos.dm3.core.vo;

import java.util.Date;

/**
 * 鉄道会社マスタクラス.
 * <p>
 * クラス名、および、フィールド名はＤＢテーブル名、列名にマッピングされる。
 * <p>
 * <pre>
 * 担当者		修正日		修正内容
 * ------------ ----------- -----------------------------------------------------
 * 細島　崇		2006.12.29	新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 * 個別カスタマイズで継承される可能性があるので、直接インスタンスを生成しない事。<br/>
 * インスタンスを取得する場合は、ValueObjectFactory から取得する事。<br/>
 * 
 */
public class RrMst {
	/** 鉄道会社CD */
	private String rrCd;
	
	/** 鉄道会社名 */
	private String rrName;
	
	/** 表示順 */
	private Integer sortOrder;
	
	/** 登録日 */
	private Date insDate;
	
	/** 登録者 */
	private String insUserId;
	
	/** 最終更新日 */
	private Date updDate;
	
	/** 最終更新者 */
	private String updUserId;
	
	
	
	/**
	 * 鉄道会社CD を取得する。<br/>
	 * <br/>
	 * @return 鉄道会社CD
	 */
	public String getRrCd() {
		return this.rrCd;
	}

	/**
	 * 鉄道会社CD を設定する。<br/>
	 * <br/>
	 * @param rrCd 鉄道会社CD
	 */
	public void setRrCd(String rrCd) {
		this.rrCd = rrCd;
	}

	/**
	 * 鉄道会社名を取得する。<br/>
	 * <br/>
	 * @return 鉄道会社名
	 */
	public String getRrName() {
		return this.rrName;
	}
	
	/**
	 * 鉄道会社名を設定する。<br/>
	 * <br/>
	 * @param rrName 鉄道会社名
	 */
	public void setRrName(String rrName) {
		this.rrName = rrName;
	}

	/**
	 * 表示順を取得する。<br/>
	 * <br/>
	 * @return 表示順
	 */
	public Integer getSortOrder() {
		return sortOrder;
	}
	
	/**
	 * 表示順を設定する。<br/>
	 * <br/>
	 * @param sortOrder 表示順
	 */
	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}

	/**
	 * 登録日を取得する。<br/>
	 * <br/>
	 * @return 登録日
	 */
	public Date getInsDate() {
		return insDate;
	}
	
	/**
	 * 登録日を設定する。<br/>
	 * <br/>
	 * @param insDate 登録日
	 */
	public void setInsDate(Date insDate) {
		this.insDate = insDate;
	}
	
	/**
	 * 登録者を取得する。<br/>
	 * <br/>
	 * @return 登録者
	 */
	public String getInsUserId() {
		return insUserId;
	}

	/**
	 * 登録者を設定する。<br/>
	 * <br/>
	 * @param insUserId 登録者
	 */
	public void setInsUserId(String insUserId) {
		this.insUserId = insUserId;
	}
	
	/**
	 * 最終更新日を取得する。<br/>
	 * <br/>
	 * @return 最終更新日
	 */
	public Date getUpdDate() {
		return updDate;
	}
	
	/**
	 * 最終更新日を設定する。<br/>
	 * <br/>
	 * @param updDate 最終更新日
	 */
	public void setUpdDate(Date updDate) {
		this.updDate = updDate;
	}
	
	/**
	 * 最終更新者を取得する。<br/>
	 * <br/>
	 * @return 最終更新者
	 */
	public String getUpdUserId() {
		return updUserId;
	}
	
	/**
	 * 最終更新者を設定する。<br/>
	 * <br/>
	 * @param updUserId 最終更新者
	 */
	public void setUpdUserId(String updUserId) {
		this.updUserId = updUserId;
	}
}
