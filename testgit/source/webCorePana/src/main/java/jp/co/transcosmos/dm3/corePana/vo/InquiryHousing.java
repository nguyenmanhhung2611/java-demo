package jp.co.transcosmos.dm3.corePana.vo;

/**
 * 物件問合せ情報.
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
public class InquiryHousing extends jp.co.transcosmos.dm3.core.vo.InquiryHousing {

    /** 連絡方法 */
    private String contactType;
    /** 連絡可能な時間帯 */
    private String contactTime;
    
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
