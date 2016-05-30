package jp.co.transcosmos.dm3.core.vo;

/**
 * お知らせ公開先情報クラス.
 * <p>
 * クラス名、および、フィールド名はＤＢテーブル名、列名にマッピングされる。
 * <p>
 * <pre>
 * 担当者		修正日		修正内容
 * ------------ ----------- -----------------------------------------------------
 * I.Shu		2015.02.06	新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 * 個別カスタマイズで継承される可能性があるので、直接インスタンスを生成しない事。<br/>
 * インスタンスを取得する場合は、ValueObjectFactory から取得する事。<br/>
 * 
 */
public class NewsTarget {

	/** お知らせ番号 */
	private String newsId;
	
	/** ユーザーID */
	private String userId;

	

	public String getNewsId() {
		return newsId;
	}

	public void setNewsId(String newsId) {
		this.newsId = newsId;
	}

	/**
	 * ユーザーIDを設定する。<br/>
	 * <br/>
	 * @param userId ユーザーID
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * ユーザーIDを取得する。<br/>
	 * <br/>
	 * @return ユーザーID
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

}
