package jp.co.transcosmos.dm3.corePana.vo;

/**
 * 物件画像情報.
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
public class HousingImageInfo extends jp.co.transcosmos.dm3.core.vo.HousingImageInfo {

    /** 閲覧権限 */
    private String roleId;

    /**
     * 閲覧権限 を取得する。<br/>
     * <br/>
     * @return 閲覧権限
     */
    public String getRoleId() {
        return roleId;
    }

    /**
     * 閲覧権限 を設定する。<br/>
     * <br/>
     * @param roleId
     */
    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
}
