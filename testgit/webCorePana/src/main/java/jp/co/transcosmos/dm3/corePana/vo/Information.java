package jp.co.transcosmos.dm3.corePana.vo;


/**
 * お知らせ情報.
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
public class Information extends jp.co.transcosmos.dm3.core.vo.Information {

    /** メール送信フラグ */
    private String sendFlg;

    /**
     * メール送信フラグ を取得する。<br/>
     * <br/>
     * @return メール送信フラグ
     */
    public String getSendFlg() {
        return sendFlg;
    }

    /**
     * メール送信フラグ を設定する。<br/>
     * <br/>
     * @param sendFlg
     */
    public void setSendFlg(String sendFlg) {
        this.sendFlg = sendFlg;
    }
}
