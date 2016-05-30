package jp.co.transcosmos.dm3.corePana.vo;

/**
 * リフォーム詳細情報.
 * <p>
 * クラス名、および、フィールド名はＤＢテーブル名、列名にマッピングされる。
 * <p>
 * <pre>
 * 担当者        修正日        修正内容
 * ------------ ----------- -----------------------------------------------------
 * Trans        2015.03.10  新規作成
 * H.Mizuno		2015.03.16	枝番のデータ型を String から Integer へ変更
 * </pre>
 * <p>
 * 注意事項<br/>
 * 個別カスタマイズで継承される可能性があるので、直接インスタンスを生成しない事。<br/>
 * インスタンスを取得する場合は、ValueObjectFactory から取得する事。<br/>
 *
 */
public class ReformDtl {

    /** システムリフォームCD */
    private String sysReformCd;
    /** 枝番 */
    private Integer divNo;
    /** パス名 */
    private String pathName;
    /** ファイル名 */
    private String fileName;
    /** 表示順 */
    private Integer sortOrder;
    /** 画像名称 */
    private String imgName;
    /** リフォーム価格 */
    private Long reformPrice;
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
     * パス名 を取得する。<br/>
     * <br/>
     * @return パス名
     */
    public String getPathName() {
        return pathName;
    }

    /**
     * パス名 を設定する。<br/>
     * <br/>
     * @param pathName
     */
    public void setPathName(String pathName) {
        this.pathName = pathName;
    }

    /**
     * ファイル名 を取得する。<br/>
     * <br/>
     * @return ファイル名
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * ファイル名 を設定する。<br/>
     * <br/>
     * @param fileName
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
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
     * 画像名称 を取得する。<br/>
     * <br/>
     * @return 画像名称
     */
    public String getImgName() {
        return imgName;
    }

    /**
     * 画像名称 を設定する。<br/>
     * <br/>
     * @param imgName
     */
    public void setImgName(String imgName) {
        this.imgName = imgName;
    }

    /**
     * リフォーム価格 を取得する。<br/>
     * <br/>
     * @return リフォーム価格
     */
    public Long getReformPrice() {
        return reformPrice;
    }

    /**
     * リフォーム価格 を設定する。<br/>
     * <br/>
     * @param reformPrice
     */
    public void setReformPrice(Long reformPrice) {
        this.reformPrice = reformPrice;
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
