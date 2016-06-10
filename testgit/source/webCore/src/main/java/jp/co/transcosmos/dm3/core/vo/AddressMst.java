package jp.co.transcosmos.dm3.core.vo;

import java.util.Date;

/**
 * 市区町村マスタクラス.
 * <p>
 * クラス名、および、フィールド名はＤＢテーブル名、列名にマッピングされる。
 * <p>
 * <pre>
 * 担当者		修正日		修正内容
 * ------------ ----------- -----------------------------------------------------
 * I.Shu		2006.12.19	新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 * 個別カスタマイズで継承される可能性があるので、直接インスタンスを生成しない事。<br/>
 * インスタンスを取得する場合は、ValueObjectFactory から取得する事。<br/>
 * 
 */
public class AddressMst {

	/** 都道府県CD */
	private String prefCd;
	/** 市区町村CD */
	private String addressCd;
	/** 市区町村名 */
	private String addressName;
	/** エリア検索非表示フラグ （0:通常、1:エリア検索で非表示） */
	private String areaNotDsp;
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
	 * 市区町村CDを取得する。<br/>
	 * <br/>
	 * @return 市区町村CD
	 */
	public String getAddressCd() {
		return addressCd;
	}
	/**
	 * 市区町村CDを設定する。<br/>
	 * <br/>
	 * @param addressCd 市区町村CD
	 */
	public void setAddressCd(String addressCd) {
		this.addressCd = addressCd;
	}
	/**
	 * 市区町村名を取得する。<br/>
	 * <br/>
	 * @return 市区町村名
	 */
	public String getAddressName() {
		return addressName;
	}
	/**
	 * 市区町村名を設定する。<br/>
	 * <br/>
	 * @param addressName 市区町村名
	 */
	public void setAddressName(String addressName) {
		this.addressName = addressName;
	}
	
	/**
	 * エリア検索非表示フラグを取得する。<br/>
	 * <br/>
	 * @return エリア検索非表示フラグ（0:通常、1:エリア検索で非表示）
	 */
	public String getAreaNotDsp() {
		return areaNotDsp;
	}

	/**
	 * エリア検索非表示フラグを設定する。<br/>
	 * <br/>
	 * @param エリア検索非表示フラグ（0:通常、1:エリア検索で非表示）
	 */
	public void setAreaNotDsp(String areaNotDsp) {
		this.areaNotDsp = areaNotDsp;
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
