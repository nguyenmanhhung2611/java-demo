package jp.co.transcosmos.dm3.core.vo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 物件リクエスト情報.
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
public class HousingRequestInfo {

	/** 物件リクエストID */
	private String housingRequestId;
	/** ユーザーID */
	private String userId;
	/** リクエスト名称 */
	private String requestName;
	/** 賃料/価格・下限値 */
	private Long priceLower;
	/** 賃料/価格・上限値 */
	private Long priceUpper;
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
	 * 物件リクエストID を取得する。<br/>
	 * <br/>
	 *
	 * @return 物件リクエストID
	 */
	public String getHousingRequestId() {
		return housingRequestId;
	}

	/**
	 * 物件リクエストID を設定する。<br/>
	 * <br/>
	 *
	 * @param housingRequestId
	 */
	public void setHousingRequestId(String housingRequestId) {
		this.housingRequestId = housingRequestId;
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
	 * リクエスト名称 を取得する。<br/>
	 * <br/>
	 *
	 * @return リクエスト名称
	 */
	public String getRequestName() {
		return requestName;
	}

	/**
	 * リクエスト名称 を設定する。<br/>
	 * <br/>
	 *
	 * @param requestName
	 */
	public void setRequestName(String requestName) {
		this.requestName = requestName;
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
