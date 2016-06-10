package jp.co.transcosmos.dm3.corePana.vo;

import java.util.Date;

/**
 * リフォームプラン.
 * <p>
 * クラス名、および、フィールド名はＤＢテーブル名、列名にマッピングされる。
 * <p>
 * <pre>
 * 担当者        修正日        修正内容
 * ------------ ----------- -----------------------------------------------------
 * Trans        2015.03.10     新規作成
 * Thi Tran     2015.12.18     Update to add categories to reform plan
 * </pre>
 * <p>
 * 注意事項<br/>
 * 個別カスタマイズで継承される可能性があるので、直接インスタンスを生成しない事。<br/>
 * インスタンスを取得する場合は、ValueObjectFactory から取得する事。<br/>
 *
 */
public class ReformPlan {

    /** システムリフォームCD */
    private String sysReformCd;
    /** システム物件CD */
    private String sysHousingCd;
    /** リフォームプラン名 */
    private String planName;
    /** リフォーム価格 */
    private Long planPrice;
    /** セールスポイント */
    private String salesPoint;
    /** 工期 */
    private String constructionPeriod;
    /** 備考 */
    private String note;
    /** before 動画URL */
    private String beforeMovieUrl;
    /** after 動画URL */
    private String afterMovieUrl;
    /** 動画閲覧権限 */
    private String roleId;
    /** 非公開フラグ */
    private String hiddenFlg;
    /** レーダーチャート画像パス名 */
    private String reformChartImagePathName;
    /** レーダーチャート画像ファイル名 */
    private String reformChartImageFileName;
    /** 登録日 */
    private Date insDate;
    /** 登録者 */
    private String insUserId;
    /** 最終更新日 */
    private Date updDate;
    /** 最終更新者 */
    private String updUserId;

    /** Category 1 of reform plan **/
    private String planCategory1;

    /** Category 2 of reform_plan **/
    private String planCategory2;

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
     * リフォームプラン名 を取得する。<br/>
     * <br/>
     * @return リフォームプラン名
     */
    public String getPlanName() {
        return planName;
    }

    /**
     * リフォームプラン名 を設定する。<br/>
     * <br/>
     * @param planName
     */
    public void setPlanName(String planName) {
        this.planName = planName;
    }

    /**
     * リフォーム価格 を取得する。<br/>
     * <br/>
     * @return リフォーム価格
     */
    public Long getPlanPrice() {
        return planPrice;
    }

    /**
     * リフォーム価格 を設定する。<br/>
     * <br/>
     * @param planPrice
     */
    public void setPlanPrice(Long planPrice) {
        this.planPrice = planPrice;
    }

    /**
     * セールスポイント を取得する。<br/>
     * <br/>
     * @return セールスポイント
     */
    public String getSalesPoint() {
        return salesPoint;
    }

    /**
     * セールスポイント を設定する。<br/>
     * <br/>
     * @param salesPoint
     */
    public void setSalesPoint(String salesPoint) {
        this.salesPoint = salesPoint;
    }

    /**
     * 工期 を取得する。<br/>
     * <br/>
     * @return 工期
     */
    public String getConstructionPeriod() {
        return constructionPeriod;
    }

    /**
     * 工期 を設定する。<br/>
     * <br/>
     * @param constructionPeriod
     */
    public void setConstructionPeriod(String constructionPeriod) {
        this.constructionPeriod = constructionPeriod;
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

    /**
     * before 動画URL を取得する。<br/>
     * <br/>
     * @return before 動画URL
     */
    public String getBeforeMovieUrl() {
        return beforeMovieUrl;
    }

    /**
     * before 動画URL を設定する。<br/>
     * <br/>
     * @param beforeMovieUrl
     */
    public void setBeforeMovieUrl(String beforeMovieUrl) {
        this.beforeMovieUrl = beforeMovieUrl;
    }

    /**
     * after 動画URL を取得する。<br/>
     * <br/>
     * @return after 動画URL
     */
    public String getAfterMovieUrl() {
        return afterMovieUrl;
    }

    /**
     * after 動画URL を設定する。<br/>
     * <br/>
     * @param afterMovieUrl
     */
    public void setAfterMovieUrl(String afterMovieUrl) {
        this.afterMovieUrl = afterMovieUrl;
    }

    /**
     * 動画閲覧権限 を取得する。<br/>
     * <br/>
     * @return 動画閲覧権限
     */
    public String getRoleId() {
        return roleId;
    }

    /**
     * 動画閲覧権限 を設定する。<br/>
     * <br/>
     * @param roleId
     */
    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    /**
     * 非公開フラグ を取得する。<br/>
     * <br/>
     * @return 非公開フラグ
     */
    public String getHiddenFlg() {
        return hiddenFlg;
    }

    /**
     * 非公開フラグ を設定する。<br/>
     * <br/>
     * @param hiddenFlg
     */
    public void setHiddenFlg(String hiddenFlg) {
        this.hiddenFlg = hiddenFlg;
    }

    /**
     * レーダーチャート画像パス名 を取得する。<br/>
     * <br/>
     * @return レーダーチャート画像パス名
     */
    public String getReformChartImagePathName() {
        return reformChartImagePathName;
    }

    /**
     * レーダーチャート画像パス名 を設定する。<br/>
     * <br/>
     * @param reformChartImagePathName
     */
    public void setReformChartImagePathName(String reformChartImagePathName) {
        this.reformChartImagePathName = reformChartImagePathName;
    }

    /**
     * レーダーチャート画像ファイル名 を取得する。<br/>
     * <br/>
     * @return レーダーチャート画像ファイル名
     */
    public String getReformChartImageFileName() {
        return reformChartImageFileName;
    }

    /**
     * レーダーチャート画像ファイル名 を設定する。<br/>
     * <br/>
     * @param reformChartImageFileName
     */
    public void setReformChartImageFileName(String reformChartImageFileName) {
        this.reformChartImageFileName = reformChartImageFileName;
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

    /**
     * @return the planCategory1
     */
    public String getPlanCategory1() {
        return planCategory1;
    }

    /**
     * @param planCategory1
     *            the planCategory1 to set
     */
    public void setPlanCategory1(String planCategory1) {
        this.planCategory1 = planCategory1;
    }

    /**
     * @return the planCategory2
     */
    public String getPlanCategory2() {
        return planCategory2;
    }

    /**
     * @param planCategory2
     *            the planCategory2 to set
     */
    public void setPlanCategory2(String planCategory2) {
        this.planCategory2 = planCategory2;
    }
}
