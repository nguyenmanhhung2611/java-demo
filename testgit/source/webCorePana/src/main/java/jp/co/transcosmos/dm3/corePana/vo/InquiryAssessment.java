package jp.co.transcosmos.dm3.corePana.vo;

import java.math.BigDecimal;

/**
 * 査定情報.
 * <p>
 * クラス名、および、フィールド名はＤＢテーブル名、列名にマッピングされる。
 * <p>
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
public class InquiryAssessment {

    /** お問合せID */
    private String inquiryId;
    /** 売却物件種別 */
    private String buyHousingType;
    /** 間取CD */
    private String layoutCd;
    /** 専有面積 */
    private BigDecimal personalArea;
    /** 築年数 */
    private Integer buildAge;
    /** 土地面積 */
    private BigDecimal landArea;
    /** 土地面積単位 */
    private String landAreaCrs;
    /** 建物面積 */
    private BigDecimal buildingArea;
    /** 建物面積単位 */
    private String buildingAreaCrs;
    /** 売却物件・郵便番号 */
    private String zip;
    /** 売却物件・都道府県CD */
    private String prefCd;
    /** 売却物件・市区町村番地 */
    private String address;
    /** 売却物件・建物名 */
    private String addressOther;
    /** 現状 */
    private String presentCd;
    /** 売却予定時期 */
    private String buyTimeCd;
    /** 買い替えの有無 */
    private String replacementFlg;
    /** 要望・質問 */
    private String requestText;
    /** 連絡方法 */
    private String contactType;
    /** 連絡可能な時間帯 */
    private String contactTime;

    /**
     * お問合せID を取得する。<br/>
     * <br/>
     * @return お問合せID
     */
    public String getInquiryId() {
        return inquiryId;
    }

    /**
     * お問合せID を設定する。<br/>
     * <br/>
     * @param inquiryId
     */
    public void setInquiryId(String inquiryId) {
        this.inquiryId = inquiryId;
    }

    /**
     * 売却物件種別 を取得する。<br/>
     * <br/>
     * @return 売却物件種別
     */
    public String getBuyHousingType() {
        return buyHousingType;
    }

    /**
     * 売却物件種別 を設定する。<br/>
     * <br/>
     * @param buyHousingType
     */
    public void setBuyHousingType(String buyHousingType) {
        this.buyHousingType = buyHousingType;
    }

    /**
     * 間取CD を取得する。<br/>
     * <br/>
     * @return 間取CD
     */
    public String getLayoutCd() {
        return layoutCd;
    }

    /**
     * 間取CD を設定する。<br/>
     * <br/>
     * @param layoutCd
     */
    public void setLayoutCd(String layoutCd) {
        this.layoutCd = layoutCd;
    }

    /**
     * 専有面積 を取得する。<br/>
     * <br/>
     * @return 専有面積
     */
    public BigDecimal getPersonalArea() {
        return personalArea;
    }

    /**
     * 専有面積 を設定する。<br/>
     * <br/>
     * @param personalArea
     */
    public void setPersonalArea(BigDecimal personalArea) {
        this.personalArea = personalArea;
    }

    /**
     * 築年数 を取得する。<br/>
     * <br/>
     * @return 築年数
     */
    public Integer getBuildAge() {
        return buildAge;
    }

    /**
     * 築年数 を設定する。<br/>
     * <br/>
     * @param buildAge
     */
    public void setBuildAge(Integer buildAge) {
        this.buildAge = buildAge;
    }

    /**
     * 土地面積 を取得する。<br/>
     * <br/>
     * @return 土地面積
     */
    public BigDecimal getLandArea() {
        return landArea;
    }

    /**
     * 土地面積 を設定する。<br/>
     * <br/>
     * @param landArea
     */
    public void setLandArea(BigDecimal landArea) {
        this.landArea = landArea;
    }

    /**
     * 土地面積単位 を取得する。<br/>
     * <br/>
     * @return 土地面積単位
     */
    public String getLandAreaCrs() {
        return landAreaCrs;
    }

    /**
     * 土地面積単位 を設定する。<br/>
     * <br/>
     * @param landAreaCrs
     */
    public void setLandAreaCrs(String landAreaCrs) {
        this.landAreaCrs = landAreaCrs;
    }

    /**
     * 建物面積 を取得する。<br/>
     * <br/>
     * @return 建物面積
     */
    public BigDecimal getBuildingArea() {
        return buildingArea;
    }

    /**
     * 建物面積 を設定する。<br/>
     * <br/>
     * @param buildingArea
     */
    public void setBuildingArea(BigDecimal buildingArea) {
        this.buildingArea = buildingArea;
    }

    /**
     * 建物面積単位 を取得する。<br/>
     * <br/>
     * @return 建物面積単位
     */
    public String getBuildingAreaCrs() {
        return buildingAreaCrs;
    }

    /**
     * 建物面積単位 を設定する。<br/>
     * <br/>
     * @param buildingAreaCrs
     */
    public void setBuildingAreaCrs(String buildingAreaCrs) {
        this.buildingAreaCrs = buildingAreaCrs;
    }
    
    /**
     * 売却物件・郵便番号 を取得する。<br/>
     * <br/>
     * @return 売却物件・郵便番号
     */
    public String getZip() {
        return zip;
    }

    /**
     * 売却物件・郵便番号 を設定する。<br/>
     * <br/>
     * @param zip
     */
    public void setZip(String zip) {
        this.zip = zip;
    }

    /**
     * 売却物件・都道府県CD を取得する。<br/>
     * <br/>
     * @return 売却物件・都道府県CD
     */
    public String getPrefCd() {
        return prefCd;
    }

    /**
     * 売却物件・都道府県CD を設定する。<br/>
     * <br/>
     * @param prefCd
     */
    public void setPrefCd(String prefCd) {
        this.prefCd = prefCd;
    }

    /**
     * 売却物件・市区町村番地 を取得する。<br/>
     * <br/>
     * @return 売却物件・市区町村番地
     */
    public String getAddress() {
        return address;
    }

    /**
     * 売却物件・市区町村番地 を設定する。<br/>
     * <br/>
     * @param address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 売却物件・建物名 を取得する。<br/>
     * <br/>
     * @return 売却物件・建物名
     */
    public String getAddressOther() {
        return addressOther;
    }

    /**
     * 売却物件・建物名 を設定する。<br/>
     * <br/>
     * @param addressOther
     */
    public void setAddressOther(String addressOther) {
        this.addressOther = addressOther;
    }

    /**
     * 現状 を取得する。<br/>
     * <br/>
     * @return 現状
     */
    public String getPresentCd() {
        return presentCd;
    }

    /**
     * 現状 を設定する。<br/>
     * <br/>
     * @param presentCd
     */
    public void setPresentCd(String presentCd) {
        this.presentCd = presentCd;
    }

    /**
     * 売却予定時期 を取得する。<br/>
     * <br/>
     * @return 売却予定時期
     */
    public String getBuyTimeCd() {
        return buyTimeCd;
    }

    /**
     * 売却予定時期 を設定する。<br/>
     * <br/>
     * @param buyTimeCd
     */
    public void setBuyTimeCd(String buyTimeCd) {
        this.buyTimeCd = buyTimeCd;
    }

    /**
     * 買い替えの有無 を取得する。<br/>
     * <br/>
     * @return 買い替えの有無
     */
    public String getReplacementFlg() {
        return replacementFlg;
    }

    /**
     * 買い替えの有無 を設定する。<br/>
     * <br/>
     * @param replacementFlg
     */
    public void setReplacementFlg(String replacementFlg) {
        this.replacementFlg = replacementFlg;
    }

    /**
     * 要望・質問 を取得する。<br/>
     * <br/>
     * @return 要望・質問
     */
    public String getRequestText() {
        return requestText;
    }

    /**
     * 要望・質問 を設定する。<br/>
     * <br/>
     * @param requestText
     */
    public void setRequestText(String requestText) {
        this.requestText = requestText;
    }

    /**
     * 連絡方法 を取得する。<br/>
     * <br/>
     * @return 連絡方法
     */
    public String getContactType() {
        return contactType;
    }

    /**
     * 連絡方法 を設定する。<br/>
     * <br/>
     * @param contactType
     */
    public void setContactType(String contactType) {
        this.contactType = contactType;
    }

    /**
     * 連絡可能な時間帯 を取得する。<br/>
     * <br/>
     * @return 連絡可能な時間帯
     */
    public String getContactTime() {
        return contactTime;
    }

    /**
     * 連絡可能な時間帯 を設定する。<br/>
     * <br/>
     * @param contactTime
     */
    public void setContactTime(String contactTime) {
        this.contactTime = contactTime;
    }

}
