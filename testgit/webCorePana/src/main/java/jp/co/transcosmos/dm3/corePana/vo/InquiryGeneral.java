package jp.co.transcosmos.dm3.corePana.vo;

import java.util.Date;

/**
 * 汎用問合せ.
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
public class InquiryGeneral {

    /** お問合せID */
    private String inquiryId;
    /** イベント・セミナー名 */
    private String eventName;
    /** イベント・セミナー日時 */
    private Date eventDatetime;
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
     * イベント・セミナー名 を取得する。<br/>
     * <br/>
     * @return イベント・セミナー名
     */
    public String getEventName() {
        return eventName;
    }

    /**
     * イベント・セミナー名 を設定する。<br/>
     * <br/>
     * @param eventName
     */
    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    /**
     * イベント・セミナー日時 を取得する。<br/>
     * <br/>
     * @return イベント・セミナー日時
     */
    public Date getEventDatetime() {
        return eventDatetime;
    }

    /**
     * イベント・セミナー日時 を設定する。<br/>
     * <br/>
     * @param eventDatetime
     */
    public void setEventDatetime(Date eventDatetime) {
        this.eventDatetime = eventDatetime;
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
