package jp.co.transcosmos.dm3.corePana.vo;

import java.util.Date;

/**
 * 最近見た物件.
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
public class RecentlyInfo {

    /** システム物件CD */
    private String sysHousingCd;
    /** ユーザーID */
    private String userId;
    /** 登録日 */
    private Date insDate;
    /** 登録者 */
    private String insUserId;
    /** 最終更新日 */
    private Date updDate;
    /** 最終更新者 */
    private String updUserId;

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
     * 登録日 を取得する。<br/>
     * <br/>
     * @return 登録日
     */
    public Date getInsDate() {
        return insDate;
    }

    /**
     * 登録日 を設定する。<br/>
     * <br/>
     * @param insDate
     */
    public void setInsDate(Date insDate) {
        this.insDate = insDate;
    }

    /**
     * 登録者 を取得する。<br/>
     * <br/>
     * @return 登録者
     */
    public String getInsUserId() {
        return insUserId;
    }

    /**
     * 登録者 を設定する。<br/>
     * <br/>
     * @param insUserId
     */
    public void setInsUserId(String insUserId) {
        this.insUserId = insUserId;
    }

    /**
     * 最終更新日 を取得する。<br/>
     * <br/>
     * @return 最終更新日
     */
    public Date getUpdDate() {
        return updDate;
    }

    /**
     * 最終更新日 を設定する。<br/>
     * <br/>
     * @param updDate
     */
    public void setUpdDate(Date updDate) {
        this.updDate = updDate;
    }

    /**
     * 最終更新者 を取得する。<br/>
     * <br/>
     * @return 最終更新者
     */
    public String getUpdUserId() {
        return updUserId;
    }

    /**
     * 最終更新者 を設定する。<br/>
     * <br/>
     * @param updUserId
     */
    public void setUpdUserId(String updUserId) {
        this.updUserId = updUserId;
    }
}
