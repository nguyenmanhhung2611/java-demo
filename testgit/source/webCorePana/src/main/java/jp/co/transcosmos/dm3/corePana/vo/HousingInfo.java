package jp.co.transcosmos.dm3.corePana.vo;


/**
 * 物件基本情報.
 * <p>
 * クラス名、および、フィールド名はＤＢテーブル名、列名にマッピングされる。
 * <p>
 * <pre>
 * 担当者        修正日        修正内容
 * ------------ ----------- -----------------------------------------------------
 * Trans        2015.04.09     新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 * 個別カスタマイズで継承される可能性があるので、直接インスタンスを生成しない事。<br/>
 * インスタンスを取得する場合は、ValueObjectFactory から取得する事。<br/>
 *
 */
public class HousingInfo extends jp.co.transcosmos.dm3.core.vo.HousingInfo {

    /** リフォーム込価格（最小） */
    private Long priceFullMin;
    /** リフォーム込価格（最大） */
    private Long priceFullMax;
    /** リフォーム準備中コメント */
    private String reformComment;
    /** 最小徒歩時間 */
    private Integer minWalkingTime;
	/** 物件リクエスト処理対象フラグ */
	private String requestFlg;


    /**
     * リフォーム込価格（最小） を取得する。<br/>
     * <br/>
     * @return リフォーム込価格（最小）
     */
    public Long getPriceFullMin() {
        return priceFullMin;
    }

    /**
     * リフォーム込価格（最小） を設定する。<br/>
     * <br/>
     * @param priceFullMin
     */
    public void setPriceFullMin(Long priceFullMin) {
        this.priceFullMin = priceFullMin;
    }

    /**
     * リフォーム込価格（最大） を取得する。<br/>
     * <br/>
     * @return リフォーム込価格（最大）
     */
    public Long getPriceFullMax() {
        return priceFullMax;
    }

    /**
     * リフォーム込価格（最大） を設定する。<br/>
     * <br/>
     * @param priceFullMax
     */
    public void setPriceFullMax(Long priceFullMax) {
        this.priceFullMax = priceFullMax;
    }

    /**
     * リフォーム準備中コメント を取得する。<br/>
     * <br/>
     * @return リフォーム準備中コメント
     */
    public String getReformComment() {
        return reformComment;
    }

    /**
     * リフォーム準備中コメント を設定する。<br/>
     * <br/>
     * @param reformComment
     */
    public void setReformComment(String reformComment) {
        this.reformComment = reformComment;
    }

    /**
     * 最小徒歩時間 を取得する。<br/>
     * <br/>
     * @return 最小徒歩時間
     */
    public Integer getMinWalkingTime() {
        return minWalkingTime;
    }

    /**
     * 最小徒歩時間 を設定する。<br/>
     * <br/>
     * @param minWalkingTime
     */
    public void setMinWalkingTime(Integer minWalkingTime) {
        this.minWalkingTime = minWalkingTime;
    }

	/**
	 * 物件リクエスト処理対象フラグを取得する。<br/>
	 * <br/>
	 * @return 物件リクエスト処理対象フラグ
	 */
	public String getRequestFlg() {
		return requestFlg;
	}

	/**
	 * 物件リクエスト処理対象フラグを設定する。<br/>
	 * <br/>
	 * @param basicComment　物件リクエスト処理対象フラグ
	 */
	public void setRequestFlg(String requestFlg) {
		this.requestFlg = requestFlg;
	}
}
