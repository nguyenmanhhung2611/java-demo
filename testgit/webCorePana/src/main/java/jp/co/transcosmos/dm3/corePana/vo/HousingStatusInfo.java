package jp.co.transcosmos.dm3.corePana.vo;

/**
 * 物件ステータス情報.
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
public class HousingStatusInfo extends jp.co.transcosmos.dm3.core.vo.HousingStatusInfo {

    /** ステータスCD */
    private String statusCd;
    /** ユーザーID */
    private String userId;
    /** 備考 */
    private String note;

    /**
     * ステータスCD を取得する。<br/>
     * <br/>
     * @return ステータスCD
     */
    public String getStatusCd() {
        return statusCd;
    }

    /**
     * ステータスCD を設定する。<br/>
     * <br/>
     * @param statusCd
     */
    public void setStatusCd(String statusCd) {
        this.statusCd = statusCd;
    }

    /**
     * ユーザーID を取得する。<br/>
     * <br/>
     * @return ユーザーID
     */
    public String getUserId() {
        return userId;
    }

    /**
     * ユーザーID を設定する。<br/>
     * <br/>
     * @param userId
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 備考 を取得する。<br/>
     * <br/>
     * @return 備考
     */
    public String getNote() {
        return note;
    }

    /**
     * 備考 を設定する。<br/>
     * <br/>
     * @param note
     */
    public void setNote(String note) {
        this.note = note;
    }
}
