package jp.co.transcosmos.dm3.core.vo;

import java.util.Date;

/**
 * 設備マスタ.
 * <p>
 * クラス名、および、フィールド名はＤＢテーブル名、列名にマッピングされる。
 * <p>
 *
 * <pre>
 * 担当者        修正日        修正内容
 * ------------ ----------- -----------------------------------------------------
 * Trans        2015.03.10  新規作成
 * H.Mizuno		2015.03.13	物理名変更に伴い、クラス名を変更
 * </pre>
 * <p>
 * 注意事項<br/>
 * 個別カスタマイズで継承される可能性があるので、直接インスタンスを生成しない事。<br/>
 * インスタンスを取得する場合は、ValueObjectFactory から取得する事。<br/>
 *
 */
public class EquipMst {

	/** 設備CD */
	private String equipCd;
	/** 設備名称 */
	private String equipName;
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
	 * 設備CD を取得する。<br/>
	 * <br/>
	 *
	 * @return 設備CD
	 */
	public String getEquipCd() {
		return equipCd;
	}

	/**
	 * 設備CD を設定する。<br/>
	 * <br/>
	 *
	 * @param equipCd
	 */
	public void setEquipCd(String equipCd) {
		this.equipCd = equipCd;
	}

	/**
	 * 設備名称 を取得する。<br/>
	 * <br/>
	 *
	 * @return 設備名称
	 */
	public String getEquipName() {
		return equipName;
	}

	/**
	 * 設備名称 を設定する。<br/>
	 * <br/>
	 *
	 * @param equipName
	 */
	public void setEquipName(String equipName) {
		this.equipName = equipName;
	}

	/**
	 * 表示順 を取得する。<br/>
	 * <br/>
	 *
	 * @return 表示順
	 */
	public Integer getSortOrder() {
		return sortOrder;
	}

	/**
	 * 表示順 を設定する。<br/>
	 * <br/>
	 *
	 * @param sortOrder
	 */
	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
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
