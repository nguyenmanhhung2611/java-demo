package jp.co.transcosmos.dm3.corePana.vo;

/**
 * リフォーム画像情報.
 * <p>
 * クラス名、および、フィールド名はＤＢテーブル名、列名にマッピングされる。
 * <p>
 * <pre>
 * 担当者        修正日        修正内容
 * ------------ ----------- -----------------------------------------------------
 * Trans        2015.03.10  新規作成
 * H.Mizuno		2015.03.16	枝番を String 型から Integer へ変更
 * </pre>
 * <p>
 * 注意事項<br/>
 * 個別カスタマイズで継承される可能性があるので、直接インスタンスを生成しない事。<br/>
 * インスタンスを取得する場合は、ValueObjectFactory から取得する事。<br/>
 *
 */
public class ReformImg {

    /** システムリフォームCD */
    private String sysReformCd;
    /** 枝番 */
    private Integer divNo;
    /** before 画像パス名 */
    private String beforePathName;
    /** before 画像ファイル名 */
    private String beforeFileName;
    /** before 画像コメント */
    private String beforeComment;
    /** after 画像パス名 */
    private String afterPathName;
    /** after 画像ファイル名 */
    private String afterFileName;
    /** after 画像コメント */
    private String afterComment;
    /** 表示順 */
    private Integer sortOrder;
    /** 画像閲覧権限 */
    private String roleId;

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
     * 枝番 を取得する。<br/>
     * <br/>
     * @return 枝番
     */
    public Integer getDivNo() {
        return divNo;
    }

    /**
     * 枝番 を設定する。<br/>
     * <br/>
     * @param divNo
     */
    public void setDivNo(Integer divNo) {
        this.divNo = divNo;
    }

    /**
     * before 画像パス名 を取得する。<br/>
     * <br/>
     * @return before 画像パス名
     */
    public String getBeforePathName() {
        return beforePathName;
    }

    /**
     * before 画像パス名 を設定する。<br/>
     * <br/>
     * @param beforePathName
     */
    public void setBeforePathName(String beforePathName) {
        this.beforePathName = beforePathName;
    }

    /**
     * before 画像ファイル名 を取得する。<br/>
     * <br/>
     * @return before 画像ファイル名
     */
    public String getBeforeFileName() {
        return beforeFileName;
    }

    /**
     * before 画像ファイル名 を設定する。<br/>
     * <br/>
     * @param beforeFileName
     */
    public void setBeforeFileName(String beforeFileName) {
        this.beforeFileName = beforeFileName;
    }

    /**
     * before 画像コメント を取得する。<br/>
     * <br/>
     * @return before 画像コメント
     */
    public String getBeforeComment() {
        return beforeComment;
    }

    /**
     * before 画像コメント を設定する。<br/>
     * <br/>
     * @param beforeComment
     */
    public void setBeforeComment(String beforeComment) {
        this.beforeComment = beforeComment;
    }

    /**
     * after 画像パス名 を取得する。<br/>
     * <br/>
     * @return after 画像パス名
     */
    public String getAfterPathName() {
        return afterPathName;
    }

    /**
     * after 画像パス名 を設定する。<br/>
     * <br/>
     * @param afterPathName
     */
    public void setAfterPathName(String afterPathName) {
        this.afterPathName = afterPathName;
    }

    /**
     * after 画像ファイル名 を取得する。<br/>
     * <br/>
     * @return after 画像ファイル名
     */
    public String getAfterFileName() {
        return afterFileName;
    }

    /**
     * after 画像ファイル名 を設定する。<br/>
     * <br/>
     * @param afterFileName
     */
    public void setAfterFileName(String afterFileName) {
        this.afterFileName = afterFileName;
    }

    /**
     * after 画像コメント を取得する。<br/>
     * <br/>
     * @return after 画像コメント
     */
    public String getAfterComment() {
        return afterComment;
    }

    /**
     * after 画像コメント を設定する。<br/>
     * <br/>
     * @param afterComment
     */
    public void setAfterComment(String afterComment) {
        this.afterComment = afterComment;
    }

    /**
     * 表示順 を取得する。<br/>
     * <br/>
     * @return 表示順
     */
    public Integer getSortOrder() {
        return sortOrder;
    }

    /**
     * 表示順 を設定する。<br/>
     * <br/>
     * @param sortOrder
     */
    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    /**
     * 画像閲覧権限 を取得する。<br/>
     * <br/>
     * @return 画像閲覧権限
     */
    public String getRoleId() {
        return roleId;
    }

    /**
     * 画像閲覧権限 を設定する。<br/>
     * <br/>
     * @param roleId
     */
    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
}
