package jp.co.transcosmos.dm3.corePana.vo;

/**
 * 物件問合せアンケート.
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
public class InquiryHousingQuestion {

    /** お問合せID */
    private String inquiryId;
    /** アンケート番号 */
    private String categoryNo;
    /** 回答CD */
    private String ansCd;
    /** その他入力 */
    private String note;

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
     * アンケート番号 を取得する。<br/>
     * <br/>
     * @return アンケート番号
     */
    public String getCategoryNo() {
        return categoryNo;
    }
    
    /**
     * アンケート番号 を設定する。<br/>
     * <br/>
     * @param categoryNo
     */
    public void setCategoryNo(String categoryNo) {
        this.categoryNo = categoryNo;
    }
    
    /**
     * 回答CD を取得する。<br/>
     * <br/>
     * @return 回答CD
     */
    public String getAnsCd() {
        return ansCd;
    }

    /**
     * 回答CD を設定する。<br/>
     * <br/>
     * @param ansCd
     */
    public void setAnsCd(String ansCd) {
        this.ansCd = ansCd;
    }

    /**
     * その他入力 を取得する。<br/>
     * <br/>
     * @return その他入力
     */
    public String getNote() {
        return note;
    }

    /**
     * その他入力 を設定する。<br/>
     * <br/>
     * @param note
     */
    public void setNote(String note) {
        this.note = note;
    }
}
