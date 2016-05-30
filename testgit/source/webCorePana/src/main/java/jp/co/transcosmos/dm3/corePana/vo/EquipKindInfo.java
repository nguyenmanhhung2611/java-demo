package jp.co.transcosmos.dm3.corePana.vo;

/**
 * 設備・物件種別対象表.
 * <p>
 * クラス名、および、フィールド名はＤＢテーブル名、列名にマッピングされる。
 * <p>
 * <pre>
 * 担当者        修正日        修正内容
 * ------------ ----------- -----------------------------------------------------
 * liu.yandong   2015.04.17  新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 * 個別カスタマイズで継承される可能性があるので、直接インスタンスを生成しない事。<br/>
 * インスタンスを取得する場合は、ValueObjectFactory から取得する事。<br/>
 *
 */
public class EquipKindInfo {

    /** 設備CD */
    private String equipCd;
    /** 物件種別CD */
    private String housingKindCd;

    /**
     * 設備CD を取得する。<br/>
     * <br/>
     * @return 設備CD
     */
    public String getEquipCd() {
        return equipCd;
    }

    /**
     * 設備CD を設定する。<br/>
     * <br/>
     * @param equipCd
     */
    public void setEquipCd(String equipCd) {
        this.equipCd = equipCd;
    }

    /**
     * 物件種別CD を取得する。<br/>
     * <br/>
     * @return 物件種別CD
     */
    public String getHousingKindCd() {
        return housingKindCd;
    }

    /**
     * 物件種別CD を設定する。<br/>
     * <br/>
     * @param housingKindCd
     */
    public void setHousingKindCd(String housingKindCd) {
        this.housingKindCd = housingKindCd;
    }
}
