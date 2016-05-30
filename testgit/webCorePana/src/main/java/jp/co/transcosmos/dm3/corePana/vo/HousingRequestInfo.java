package jp.co.transcosmos.dm3.corePana.vo;

import java.math.BigDecimal;

/**
 * 物件リクエスト情報.
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
public class HousingRequestInfo extends jp.co.transcosmos.dm3.core.vo.HousingRequestInfo {

    /** 建物面積・下限値 */
    private BigDecimal buildingAreaLower;
    /** 建物面積・上限値 */
    private BigDecimal buildingAreaUpper;
    /** 土地面積・下限値 */
    private BigDecimal landAreaLower;
    /** 土地面積・上限値 */
    private BigDecimal landAreaUpper;
    /** リフォーム価格込 */
    private String useReform;
    /** 築年月 */
    private Integer builtMonth;

    /**
     * 建物面積・下限値 を取得する。<br/>
     * <br/>
     * @return 建物面積・下限値
     */
    public BigDecimal getBuildingAreaLower() {
        return buildingAreaLower;
    }

    /**
     * 建物面積・下限値 を設定する。<br/>
     * <br/>
     * @param buildingAreaLower
     */
    public void setBuildingAreaLower(BigDecimal buildingAreaLower) {
        this.buildingAreaLower = buildingAreaLower;
    }

    /**
     * 建物面積・上限値 を取得する。<br/>
     * <br/>
     * @return 建物面積・上限値
     */
    public BigDecimal getBuildingAreaUpper() {
        return buildingAreaUpper;
    }

    /**
     * 建物面積・上限値 を設定する。<br/>
     * <br/>
     * @param buildingAreaUpper
     */
    public void setBuildingAreaUpper(BigDecimal buildingAreaUpper) {
        this.buildingAreaUpper = buildingAreaUpper;
    }

    /**
     * 土地面積・下限値 を取得する。<br/>
     * <br/>
     * @return 土地面積・下限値
     */
    public BigDecimal getLandAreaLower() {
        return landAreaLower;
    }

    /**
     * 土地面積・下限値 を設定する。<br/>
     * <br/>
     * @param landAreaLower
     */
    public void setLandAreaLower(BigDecimal landAreaLower) {
        this.landAreaLower = landAreaLower;
    }

    /**
     * 土地面積・上限値 を取得する。<br/>
     * <br/>
     * @return 土地面積・上限値
     */
    public BigDecimal getLandAreaUpper() {
        return landAreaUpper;
    }

    /**
     * 土地面積・上限値 を設定する。<br/>
     * <br/>
     * @param landAreaUpper
     */
    public void setLandAreaUpper(BigDecimal landAreaUpper) {
        this.landAreaUpper = landAreaUpper;
    }

    /**
     * リフォーム価格込 を取得する。<br/>
     * <br/>
     * @return リフォーム価格込
     */
    public String getUseReform() {
        return useReform;
    }

    /**
     * リフォーム価格込 を設定する。<br/>
     * <br/>
     * @param useReform
     */
    public void setUseReform(String useReform) {
        this.useReform = useReform;
    }

    /**
     * 築年月 を取得する。<br/>
     * <br/>
     * @return 築年月
     */
    public Integer getBuiltMonth() {
        return builtMonth;
    }

    /**
     * 築年月 を設定する。<br/>
     * <br/>
     * @param builtMonth
     */
    public void setBuiltMonth(Integer builtMonth) {
        this.builtMonth = builtMonth;
    }
}
