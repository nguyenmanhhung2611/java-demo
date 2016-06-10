package jp.co.transcosmos.dm3.adminCore.request.form;

import java.math.BigDecimal;
import java.util.Date;

import jp.co.transcosmos.dm3.core.vo.HousingRequestInfo;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;

public class HousingRequestForm {

	/** 物件リクエストID */
	private String housingRequestId;

	/** ユーザID */
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

	/** 都道府県CD */
	private String prefCd;

	/** 市区町村CD */
	private String addressCd;

	/** 路線CD */
	private String routeCd;

	/** 駅CD */
	private String stationCd;

	/** 物件種類CD */
	private String housingKindCd;

	/** 間取りCD */
	private String layoutCd;

	/** こだわり条件CD */
	private String partSrchCd;

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
	 * @param housingRequestId 物件リクエストID
	 */
	public void setHousingRequestId(String housingRequestId) {
		this.housingRequestId = housingRequestId;
	}

	/**
	 * ユーザID を取得する。<br/>
	 * <br/>
	 * 
	 * @return ユーザID
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * ユーザID を設定する。<br/>
	 * <br/>
	 * 
	 * @param userId ユーザID
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
	 * @param housingRequestName リクエスト名称
	 */
	public void setRequestName(String housingRequestName) {
		this.requestName = housingRequestName;
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
	 * @param priceLower 賃料/価格・下限値
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
	 * @param priceUpper 賃料/価格・上限値
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
	 * @param personalAreaLower 専有面積・下限値
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
	 * @param personalAreaUpper 専有面積・上限値
	 */
	public void setPersonalAreaUpper(BigDecimal personalAreaUpper) {
		this.personalAreaUpper = personalAreaUpper;
	}

	/**
	 * 都道府県CD を取得する。<br/>
	 * <br/>
	 * 
	 * @return 都道府県CD
	 */
	public String getPrefCd() {
		return prefCd;
	}

	/**
	 * 都道府県CD を設定する。<br/>
	 * <br/>
	 * 
	 * @param prefCd 都道府県CD
	 */
	public void setPrefCd(String prefCd) {
		this.prefCd = prefCd;
	}

	/**
	 * 市区町村CD を取得する。<br/>
	 * <br/>
	 * 
	 * @return 市区町村CD
	 */
	public String getAddressCd() {
		return addressCd;
	}

	/**
	 * 市区町村CD を設定する。<br/>
	 * <br/>
	 * 
	 * @param prefCd 市区町村CD
	 */
	public void setAddressCd(String addressCd) {
		this.addressCd = addressCd;
	}

	/**
	 * 沿線CD を取得する。<br/>
	 * <br/>
	 * 
	 * @return 沿線CD
	 */
	public String getRouteCd() {
		return routeCd;
	}

	/**
	 * 沿線CD を設定する。<br/>
	 * <br/>
	 * 
	 * @param routeCd 沿線CD
	 */
	public void setRouteCd(String routeCd) {
		this.routeCd = routeCd;
	}

	/**
	 * 駅CD を取得する。<br/>
	 * <br/>
	 * 
	 * @return 駅CD
	 */
	public String getStationCd() {
		return stationCd;
	}

	/**
	 * 駅CD を設定する。<br/>
	 * <br/>
	 * 
	 * @param stationCd 駅CD
	 */
	public void setStationCd(String stationCd) {
		this.stationCd = stationCd;
	}

	/**
	 * 物件種類CD を取得する。<br/>
	 * <br/>
	 * 
	 * @return 物件種類CD
	 */
	public String getHousingKindCd() {
		return housingKindCd;
	}

	/**
	 * 物件種類CD を設定する。<br/>
	 * <br/>
	 * 
	 * @param housingKindCd 物件種類CD
	 */
	public void setHousingKindCd(String housingKindCd) {
		this.housingKindCd = housingKindCd;
	}

	/**
	 * 間取CD を取得する。<br/>
	 * <br/>
	 * 
	 * @return 間取CD
	 */
	public String getLayoutCd() {
		return layoutCd;
	}

	/**
	 * 間取CD を設定する。<br/>
	 * <br/>
	 * 
	 * @param layoutCd 間取CD
	 */
	public void setLayoutCd(String layoutCd) {
		this.layoutCd = layoutCd;
	}

	/**
	 * こだわり条件CD を取得する。<br/>
	 * <br/>
	 * 
	 * @return こだわり条件CD
	 */
	public String getPartSrchCd() {
		return partSrchCd;
	}

	/**
	 * こだわり条件CD を設定する。<br/>
	 * <br/>
	 * 
	 * @param layoutCd こだわり条件CD
	 */
	public void setPartSrchCd(String partSrchCd) {
		this.partSrchCd = partSrchCd;
	}

	/**
	 * 引数で渡された物件リクエスト情報のバリーオブジェクトにフォームの値を設定する。<br/>
	 * 更新処理でも使用する事を考慮し、タイムスタンプ情報に関しては、更新日、更新者のみ設定する。<br/>
	 * <br/>
	 * 
	 * @param userId ユーザID
	 * @param housingRequestInfo 物件リクエスト情報
	 */
	public void copyToHousingRequestInfo(String userId, HousingRequestInfo housingRequestInfo) {

		if (StringValidateUtil.isEmpty(userId)) {
			return;
		}

		housingRequestInfo.setUserId(userId);
		housingRequestInfo.setRequestName(this.requestName);
		housingRequestInfo.setPriceLower(this.priceLower);
		housingRequestInfo.setPriceUpper(this.priceUpper);
		housingRequestInfo.setPersonalAreaLower(this.personalAreaLower);
		housingRequestInfo.setPersonalAreaUpper(this.personalAreaUpper);

		Date sysDate = new Date();
		housingRequestInfo.setUpdDate(sysDate);
		housingRequestInfo.setUpdUserId(userId);
	}

}
