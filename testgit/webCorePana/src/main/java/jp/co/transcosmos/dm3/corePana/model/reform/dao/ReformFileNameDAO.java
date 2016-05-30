package jp.co.transcosmos.dm3.corePana.model.reform.dao;

/**
 * リフォーム画像ファイル名生成用 DAO のインターフェース.
 * リフォーム画像ファイルを生成するクラスはDB使用の有無にかかわらず、このインターゲースを実装する必要がある。<br/>
 * 復帰する画像ファイル名には拡張子、およびファイルパスの情報は含まない。<br/>
 * <p>
 * <pre>
 * 担当者		修正日		修正内容
 * ------------ ----------- -----------------------------------------------------
 * TRANS		2015.04.06	新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 *
 */
public interface ReformFileNameDAO {

    /**
     * リフォーム画像ファイル名をシーケンスから取得して復帰する。<br/>
     * 拡張子、およびファイルパスの情報は復帰するデータに含まない。<br/>
     * <br/>
     * @return 画像ファイル名　（拡張子、パスなし）
     *
     * @exception Exception インターフェースの実装クラスが復帰する任意の例外オブジェクト
     */
    public String createFileName() throws Exception;
}
