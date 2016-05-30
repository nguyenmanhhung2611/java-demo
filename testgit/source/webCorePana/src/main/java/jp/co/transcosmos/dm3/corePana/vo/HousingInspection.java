package jp.co.transcosmos.dm3.corePana.vo;

/**
 * 物件インスペクション.
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
public class HousingInspection {

    /** システム物件CD */
    private String sysHousingCd;
    /** インスペクションキー値 */
    private String inspectionKey;
    /** 設定値の信頼度 */
    private Integer inspectionTrust;
    /** インスペクション設定値 */
    private Integer inspectionValue;

    /**
     * システム物件CD を取得する。<br/>
     * <br/>
     * @return システム物件CD
     */
    public String getSysHousingCd() {
        return sysHousingCd;
    }

    /**
     * システム物件CD を設定する。<br/>
     * <br/>
     * @param sysHousingCd
     */
    public void setSysHousingCd(String sysHousingCd) {
        this.sysHousingCd = sysHousingCd;
    }

    /**
     * インスペクションキー値 を取得する。<br/>
     * <br/>
     * @return インスペクションキー値
     */
    public String getInspectionKey() {
        return inspectionKey;
    }

    /**
     * インスペクションキー値 を設定する。<br/>
     * <br/>
     * @param inspectionKey
     */
    public void setInspectionKey(String inspectionKey) {
        this.inspectionKey = inspectionKey;
    }

    /**
     * 設定値の信頼度 を取得する。<br/>
     * <br/>
     * @return 設定値の信頼度
     */
    public Integer getInspectionTrust() {
        return inspectionTrust;
    }

    /**
     * 設定値の信頼度 を設定する。<br/>
     * <br/>
     * @param inspectionTrust
     */
    public void setInspectionTrust(Integer inspectionTrust) {
        this.inspectionTrust = inspectionTrust;
    }

    /**
     * インスペクション設定値 を取得する。<br/>
     * <br/>
     * @return インスペクション設定値
     */
    public Integer getInspectionValue() {
        return inspectionValue;
    }

    /**
     * インスペクション設定値 を設定する。<br/>
     * <br/>
     * @param inspectionValue
     */
    public void setInspectionValue(Integer inspectionValue) {
        this.inspectionValue = inspectionValue;
    }
}
