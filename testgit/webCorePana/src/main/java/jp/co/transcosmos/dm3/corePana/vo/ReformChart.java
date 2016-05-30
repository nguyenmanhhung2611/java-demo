package jp.co.transcosmos.dm3.corePana.vo;

/**
 * リフォーム・レーダーチャート.
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
public class ReformChart {

    /** システムリフォームCD */
    private String sysReformCd;
    /** チャートキー値 */
    private String chartKey;
    /** チャート設定値 */
    private Integer chartValue;

    /**
     * システムリフォームCD を取得する。<br/>
     * <br/>
     * @return システムリフォームCD
     */
    public String getSysReformCd() {
        return sysReformCd;
    }

    /**
     * システムリフォームCD を設定する。<br/>
     * <br/>
     * @param sysReformCd
     */
    public void setSysReformCd(String sysReformCd) {
        this.sysReformCd = sysReformCd;
    }

    /**
     * チャートキー値 を取得する。<br/>
     * <br/>
     * @return チャートキー値
     */
    public String getChartKey() {
        return chartKey;
    }

    /**
     * チャートキー値 を設定する。<br/>
     * <br/>
     * @param chartKey
     */
    public void setChartKey(String chartKey) {
        this.chartKey = chartKey;
    }

    /**
     * チャート設定値 を取得する。<br/>
     * <br/>
     * @return チャート設定値
     */
    public Integer getChartValue() {
        return chartValue;
    }

    /**
     * チャート設定値 を設定する。<br/>
     * <br/>
     * @param chartValue
     */
    public void setChartValue(Integer chartValue) {
        this.chartValue = chartValue;
    }
}
