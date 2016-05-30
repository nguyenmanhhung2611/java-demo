package jp.co.transcosmos.dm3.corePana.vo;

/**
 * 物件画像情報（ソート用）.
 * <p>
 * クラス名、および、フィールド名はＤＢテーブル名、列名にマッピングされる。
 * <p>
 * <pre>
 * 担当者        修正日        修正内容
 * ------------ ----------- -----------------------------------------------------
 * Trans        2015.05.08     新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 * 個別カスタマイズで継承される可能性があるので、直接インスタンスを生成しない事。<br/>
 * インスタンスを取得する場合は、ValueObjectFactory から取得する事。<br/>
 *
 */
public class HousingImageSortInfo extends HousingImageInfo {

	/** tempパス（大きい画像） */
	private String tempPath1;
	/** tempパス（小さい画像） */
	private String tempPath2;

    /**
     * tempパス（大きい画像） を取得する。<br/>
     * <br/>
     * @return tempパス（大きい画像）
     */
    public String getTempPath1() {
        return tempPath1;
    }

    /**
     * tempパス（大きい画像） を設定する。<br/>
     * <br/>
     * @param tempPath1
     */
    public void setTempPath1(String tempPath1) {
        this.tempPath1 = tempPath1;
    }

    /**
     * tempパス（小さい画像） を取得する。<br/>
     * <br/>
     * @return tempパス（小さい画像）
     */
    public String getTempPath2() {
        return tempPath2;
    }

    /**
     * tempパス（小さい画像） を設定する。<br/>
     * <br/>
     * @param tempPath1
     */
    public void setTempPath2(String tempPath2) {
        this.tempPath2 = tempPath2;
    }

}
