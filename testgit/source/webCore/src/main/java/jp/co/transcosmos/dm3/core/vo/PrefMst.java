package jp.co.transcosmos.dm3.core.vo;

import java.util.Date;

/**
 * 都道府県マスタクラス.
 * <p>
 * クラス名、および、フィールド名はＤＢテーブル名、列名にマッピングされる。
 * <p>
 * <pre>
 * 担当者		修正日		修正内容
 * ------------ ----------- -----------------------------------------------------
 * 細島　崇		2006.12.19	新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 * 個別カスタマイズで継承される可能性があるので、直接インスタンスを生成しない事。<br/>
 * インスタンスを取得する場合は、ValueObjectFactory から取得する事。<br/>
 * 
 */
public class PrefMst {

	/** 都道府県CD */
	private String prefCd;
	/** 都道府県名 */
	private String prefName;
	/** 都道府県名ローマ字 */
	private String rPrefName;
	/** 地域CD */
	private String areaCd;
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
	 * 都道府県CD を取得する。<br/>
	 * <br/>
	 * @return 都道府県CD
	 */
	public String getPrefCd() {
		return prefCd;
	}
	
	/**
	 * 都道府県CD を設定する。<br/>
	 * <br/>
	 * @param prefCd
	 */
	public void setPrefCd(String prefCd) {
		this.prefCd = prefCd;
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
	 * 都道府県名ローマ字を取得する。<br/>
	 * <br/>
	 * @return 都道府県名ローマ
	 */
	public String getRPrefName() {
		return rPrefName;
	}
	
	/**
	 * 都道府県名ローマ字を取得する。<br/>
	 * <br/>
	 * @param rPrefName 都道府県名ローマ字
	 */
	public void setRPrefName(String rPrefName) {
		this.rPrefName = rPrefName;
	}
	
	/**
	 * 地域CD を取得する。<br/>
	 * <br/>
	 * @return 地域CD
	 */
	public String getAreaCd() {
		return areaCd;
	}
	
	/**
	 * 地域CD を設定する。<br/>
	 * <br/>
	 * @param areaCd 地域CD
	 */
	public void setAreaCd(String areaCd) {
		this.areaCd = areaCd;
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
