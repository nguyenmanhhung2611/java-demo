package jp.co.transcosmos.dm3.corePana.vo;

/**
 * こだわり条件・物件種別対象表.
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
public class PartKindInfo {

    /** こだわり条件CD */
    private String partSrchCd;
    /** 物件種別CD */
    private String housingKindCd;

    /**
     * こだわり条件CD を取得する。<br/>
     * <br/>
     * @return こだわり条件CD
     */
    public String getPartSrchCd() {
        return partSrchCd;
    }

    /**
     * こだわり条件CD を設定する。<br/>
     * <br/>
     * @param partSrchCd
     */
    public void setPartSrchCd(String partSrchCd) {
        this.partSrchCd = partSrchCd;
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
