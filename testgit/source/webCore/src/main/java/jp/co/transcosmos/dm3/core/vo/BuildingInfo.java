package jp.co.transcosmos.dm3.core.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 建物情報クラス.
 * <p>
 * クラス名、および、フィールド名はＤＢテーブル名、列名にマッピングされる。
 * <p>
 * <pre>
 * 担当者		修正日		修正内容
 * ------------ ----------- -----------------------------------------------------
 * I.Shu		2015.02.27	新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 * 個別カスタマイズで継承される可能性があるので、直接インスタンスを生成しない事。<br/>
 * インスタンスを取得する場合は、ValueObjectFactory から取得する事。<br/>
 * 
 */
public class BuildingInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/** システム建物CD */
	private String sysBuildingCd;
	/** 建物番号 */
	private String buildingCd;
	/** 物件種類CD */
	private String housingKindCd;
	/** 建物構造CD */
	private String structCd;
	/** 表示用建物名 */
	private String displayBuildingName;
	/** 表示用建物名ふりがな */
	private String displayBuildingNameKana;
	/** 所在地・郵便番号 */
	private String zip;
	/** 所在地・都道府県CD */
	private String prefCd;
	/** 所在地・市区町村CD */
	private String addressCd;
	/** 所在地・市区町村名 */
	private String addressName;
	/** 所在地・町名番地 */
	private String addressOther1;
	/** 所在地・建物名その他  */
	private String addressOther2;
	/** 竣工年月 */
	private Date compDate;
	/** 竣工旬 */
	private String compTenDays;
	/** 総階数 */
	private Integer totalFloors;
	/** 登録日 */
	private Date insDate;
	/** 登録者 */
	private String insUserId;
	/** 最終更新日 */
	private Date updDate;
	/** 最終更新者 */
	private String updUserId;
	
	/**
	 * システム建物CDを取得する。<br/>
	 * <br/>
	 * @return システム建物CD
	 */
	public String getSysBuildingCd() {
		return sysBuildingCd;
	}
	/**
	 * システム建物CDを設定する。<br/>
	 * <br/>
	 * @param sysBuildingCd システム建物CD
	 */
	public void setSysBuildingCd(String sysBuildingCd) {
		this.sysBuildingCd = sysBuildingCd;
	}
	/**
	 * 建物番号を取得する。<br/>
	 * <br/>
	 * @return 建物番号
	 */
	public String getBuildingCd() {
		return buildingCd;
	}
	/**
	 * 建物番号を設定する。<br/>
	 * <br/>
	 * @param buildingCd 建物番号
	 */
	public void setBuildingCd(String buildingCd) {
		this.buildingCd = buildingCd;
	}
	/**
	 * 物件種類CDを取得する。<br/>
	 * <br/>
	 * @return 物件種類CD
	 */
	public String getHousingKindCd() {
		return housingKindCd;
	}
	/**
	 * 物件種類CDを設定する。<br/>
	 * <br/>
	 * @param housingKindCd 物件種類CD
	 */
	public void setHousingKindCd(String housingKindCd) {
		this.housingKindCd = housingKindCd;
	}
	/**
	 * 建物構造CDを取得する。<br/>
	 * <br/>
	 * @return 建物構造CD
	 */
	public String getStructCd() {
		return structCd;
	}
	/**
	 * 建物構造CDを設定する。<br/>
	 * <br/>
	 * @param structCd 建物構造CD
	 */
	public void setStructCd(String structCd) {
		this.structCd = structCd;
	}
	/**
	 * 表示用建物名を取得する。<br/>
	 * <br/>
	 * @return 表示用建物名
	 */
	public String getDisplayBuildingName() {
		return displayBuildingName;
	}
	/**
	 * 表示用建物名を設定する。<br/>
	 * <br/>
	 * @param displayBuildingName 表示用建物名
	 */
	public void setDisplayBuildingName(String displayBuildingName) {
		this.displayBuildingName = displayBuildingName;
	}
	/**
	 * 表示用建物名ふりがなを取得する。<br/>
	 * <br/>
	 * @return 表示用建物名ふりがな
	 */
	public String getDisplayBuildingNameKana() {
		return displayBuildingNameKana;
	}
	/**
	 * 表示用建物名ふりがなを設定する。<br/>
	 * <br/>
	 * @param displayBuildingNameKana 表示用建物名ふりがな
	 */
	public void setDisplayBuildingNameKana(String displayBuildingNameKana) {
		this.displayBuildingNameKana = displayBuildingNameKana;
	}
	/**
	 * 所在地・郵便番号を取得する。<br/>
	 * <br/>
	 * @return 所在地・郵便番号
	 */
	public String getZip() {
		return zip;
	}
	/**
	 * 所在地・郵便番号を設定する。<br/>
	 * <br/>
	 * @param zip 所在地・郵便番号
	 */
	public void setZip(String zip) {
		this.zip = zip;
	}
	/**
	 * 所在地・都道府県CDを取得する。<br/>
	 * <br/>
	 * @return 所在地・都道府県CD
	 */
	public String getPrefCd() {
		return prefCd;
	}
	/**
	 * 所在地・都道府県CDを設定する。<br/>
	 * <br/>
	 * @param prefCd 所在地・都道府県CD
	 */
	public void setPrefCd(String prefCd) {
		this.prefCd = prefCd;
	}
	/**
	 * 所在地・市区町村CDを取得する。<br/>
	 * <br/>
	 * @return 所在地・市区町村CD
	 */
	public String getAddressCd() {
		return addressCd;
	}
	/**
	 * 所在地・市区町村CDを設定する。<br/>
	 * <br/>
	 * @param addressCd 所在地・市区町村CD
	 */
	public void setAddressCd(String addressCd) {
		this.addressCd = addressCd;
	}
	/**
	 * 所在地・市区町村名を取得する。<br/>
	 * <br/>
	 * @return 所在地・市区町村名
	 */
	public String getAddressName() {
		return addressName;
	}
	/**
	 * 所在地・市区町村名を設定する。<br/>
	 * <br/>
	 * @param addressName 所在地・市区町村名
	 */
	public void setAddressName(String addressName) {
		this.addressName = addressName;
	}
	/**
	 * 所在地・町名番地を取得する。<br/>
	 * <br/>
	 * @return 所在地・町名番地
	 */
	public String getAddressOther1() {
		return addressOther1;
	}
	/**
	 * 所在地・町名番地を設定する。<br/>
	 * <br/>
	 * @param addressOther1 所在地・町名番地
	 */
	public void setAddressOther1(String addressOther1) {
		this.addressOther1 = addressOther1;
	}
	/**
	 * 所在地・建物名その他を取得する。<br/>
	 * <br/>
	 * @return 所在地・建物名その他
	 */
	public String getAddressOther2() {
		return addressOther2;
	}
	/**
	 * 所在地・建物名その他を設定する。<br/>
	 * <br/>
	 * @param addressOther2 所在地・建物名その他
	 */
	public void setAddressOther2(String addressOther2) {
		this.addressOther2 = addressOther2;
	}
	/**
	 * 竣工年月を取得する。<br/>
	 * <br/>
	 * @return 竣工年月
	 */
	public Date getCompDate() {
		return compDate;
	}
	/**
	 * 竣工年月を設定する。<br/>
	 * <br/>
	 * @param compDate 竣工年月
	 */
	public void setCompDate(Date compDate) {
		this.compDate = compDate;
	}
	/**
	 * 竣工旬を取得する。<br/>
	 * <br/>
	 * @return 竣工旬
	 */
	public String getCompTenDays() {
		return compTenDays;
	}
	/**
	 * 竣工旬を設定する。<br/>
	 * <br/>
	 * @param compTenDays 竣工旬
	 */
	public void setCompTenDays(String compTenDays) {
		this.compTenDays = compTenDays;
	}
	/**
	 * 総階数を取得する。<br/>
	 * <br/>
	 * @return 総階数
	 */
	public Integer getTotalFloors() {
		return totalFloors;
	}
	/**
	 * 総階数を設定する。<br/>
	 * <br/>
	 * @param totalFloors 総階数
	 */
	public void setTotalFloors(Integer totalFloors) {
		this.totalFloors = totalFloors;
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
