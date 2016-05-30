package jp.co.transcosmos.dm3.core.vo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 検索条件保存情報.
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
public class SaveSrchData {

	/** 検索条件ID */
	private String srchId;
	/** 検索条件名称 */
	private String srchName;
	/** ユーザーID */
	private String userId;
	/** 検索方式 */
	private String srchMethod;
	/** 賃料/価格・下限値 */
	private Long priceLower;
	/** 賃料/価格・上限値 */
	private Long priceUpper;
	/** 管理費フラグ */
	private String upkeepFlg;
	/** 専有面積・下限値 */
	private BigDecimal personalAreaLower;
	/** 専有面積・上限値 */
	private BigDecimal personalAreaUpper;
	/** 登録日 */
	private Date insDate;
	/** 登録者 */
	private String insUserId;
	/** 最終更新日 */
	private Date updDate;
	/** 最終更新者 */
	private String updUserId;

	/**
	 * 検索条件ID を取得する。<br/>
	 * <br/>
	 *
	 * @return 検索条件ID
	 */
	public String getSrchId() {
		return srchId;
	}

	/**
	 * 検索条件ID を設定する。<br/>
	 * <br/>
	 *
	 * @param srchId
	 */
	public void setSrchId(String srchId) {
		this.srchId = srchId;
	}

	/**
	 * 検索条件名称 を取得する。<br/>
	 * <br/>
	 *
	 * @return 検索条件名称
	 */
	public String getSrchName() {
		return srchName;
	}

	/**
	 * 検索条件名称 を設定する。<br/>
	 * <br/>
	 *
	 * @param srchName
	 */
	public void setSrchName(String srchName) {
		this.srchName = srchName;
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
	 * 検索方式 を取得する。<br/>
	 * <br/>
	 *
	 * @return 検索方式
	 */
	public String getSrchMethod() {
		return srchMethod;
	}

	/**
	 * 検索方式 を設定する。<br/>
	 * <br/>
	 *
	 * @param srchMethod
	 */
	public void setSrchMethod(String srchMethod) {
		this.srchMethod = srchMethod;
	}

	/**
	 * 賃料/価格・下限値 を取得する。<br/>
	 * <br/>
	 *
	 * @return 賃料/価格・下限値
	 */
	public Long getPriceLower() {
		return priceLower;
	}

	/**
	 * 賃料/価格・下限値 を設定する。<br/>
	 * <br/>
	 *
	 * @param priceLower
	 */
	public void setPriceLower(Long priceLower) {
		this.priceLower = priceLower;
	}

	/**
	 * 賃料/価格・上限値 を取得する。<br/>
	 * <br/>
	 *
	 * @return 賃料/価格・上限値
	 */
	public Long getPriceUpper() {
		return priceUpper;
	}

	/**
	 * 賃料/価格・上限値 を設定する。<br/>
	 * <br/>
	 *
	 * @param priceUpper
	 */
	public void setPriceUpper(Long priceUpper) {
		this.priceUpper = priceUpper;
	}

	/**
	 * 管理費フラグ を取得する。<br/>
	 * <br/>
	 *
	 * @return 管理費フラグ
	 */
	public String getUpkeepFlg() {
		return upkeepFlg;
	}

	/**
	 * 管理費フラグ を設定する。<br/>
	 * <br/>
	 *
	 * @param upkeepFlg
	 */
	public void setUpkeepFlg(String upkeepFlg) {
		this.upkeepFlg = upkeepFlg;
	}

	/**
	 * 専有面積・下限値 を取得する。<br/>
	 * <br/>
	 *
	 * @return 専有面積・下限値
	 */
	public BigDecimal getPersonalAreaLower() {
		return personalAreaLower;
	}

	/**
	 * 専有面積・下限値 を設定する。<br/>
	 * <br/>
	 *
	 * @param personalAreaLower
	 */
	public void setPersonalAreaLower(BigDecimal personalAreaLower) {
		this.personalAreaLower = personalAreaLower;
	}

	/**
	 * 専有面積・上限値 を取得する。<br/>
	 * <br/>
	 *
	 * @return 専有面積・上限値
	 */
	public BigDecimal getPersonalAreaUpper() {
		return personalAreaUpper;
	}

	/**
	 * 専有面積・上限値 を設定する。<br/>
	 * <br/>
	 *
	 * @param personalAreaUpper
	 */
	public void setPersonalAreaUpper(BigDecimal personalAreaUpper) {
		this.personalAreaUpper = personalAreaUpper;
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
