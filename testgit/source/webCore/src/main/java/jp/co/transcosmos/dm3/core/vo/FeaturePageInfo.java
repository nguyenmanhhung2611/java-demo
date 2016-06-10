package jp.co.transcosmos.dm3.core.vo;

import java.util.Date;

/**
 * 特集ページマスタクラス.
 * <p>
 * クラス名、および、フィールド名はＤＢテーブル名、列名にマッピングされる。
 * <p>
 * <pre>
 * 担当者		修正日		修正内容
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.02.19	新規作成
 * H.Mizuno		2015.03.12	画像関連属性追加
 * </pre>
 * <p>
 * 注意事項<br/>
 * 個別カスタマイズで継承される可能性があるので、直接インスタンスを生成しない事。<br/>
 * インスタンスを取得する場合は、ValueObjectFactory から取得する事。<br/>
 * 
 */
public class FeaturePageInfo {
	
	/** 特集ページＩＤ */
	private String featurePageId;
	/** 特集ページ名 */
	private String featurePageName;
	/** コメント */
	private String featureComment;
	/** クエリー文字列 */
	private String queryStrings;
	/** 表示フラグ  0: 非表示、1:表示　*/
	private String displayFlg;
// 2015.03.12 H.Mizuno 画像関連属性を追加 start
	/** 画像パス名 */
	private String pathName;
	/** 縦長・横長フラグ （0:横長画像、1:縦長画像、2:縦横同一） */
	private String hwFlg;
// 2015.03.12 H.Mizuno 画像関連属性を追加 end
	/** 表示開始日 */
	private Date displayStartDate;
	/** 表示終了日 */
	private Date displayEndDate;
	/** 登録日 */
	private Date insDate;
	/** 登録者 */
	private String insUserId;
	/** 最終更新日 */
	private Date updDate;
	/** 最終更新者 */
	private String updUserId; 



	/**
	 * 特集ページＩＤ を取得する。<br/>
	 * <br/>
	 * @return 特集ページＩＤ
	 */
	public String getFeaturePageId() {
		return featurePageId;
	}
	
	/**
	 * 特集ページＩＤ を設定する。<br/>
	 * <br/>
	 * @param featurePageId　特集ページＩＤ
	 */
	public void setFeaturePageId(String featurePageId) {
		this.featurePageId = featurePageId;
	}

	/**
	 * 特集ページ名を取得する。<br/>
	 * <br/>
	 * @return 特集ページ名
	 */
	public String getFeaturePageName() {
		return featurePageName;
	}
	
	/**
	 * 特集ページ名を設定する。<br/>
	 * <br/>
	 * @param featurePageName　特集ページ名
	 */
	public void setFeaturePageName(String featurePageName) {
		this.featurePageName = featurePageName;
	}

	/**
	 * コメントを取得する。<br/>
	 * <br/>
	 * @return コメント
	 */
	public String getFeatureComment() {
		return featureComment;
	}
	
	/**
	 * コメントを設定する。<br/>
	 * <br/>
	 * @param featureComment コメント
	 */
	public void setFeatureComment(String featureComment) {
		this.featureComment = featureComment;
	}

	/**
	 * クエリー文字列を取得する。<br/>
	 * <br/>
	 * @return クエリー文字列
	 */
	public String getQueryStrings() {
		return queryStrings;
	}
	
	/**
	 * クエリー文字列を設定する。<br/>
	 * <br/>
	 * @param queryStrings　クエリー文字列
	 */
	public void setQueryStrings(String queryStrings) {
		this.queryStrings = queryStrings;
	}

	/**
	 * 表示フラグを取得する。<br/>
	 * <br/>
	 * @return 表示フラグ（0: 非表示、1:表示）
	 */
	public String getDisplayFlg() {
		return displayFlg;
	}
	
	/**
	 * 表示フラグを設定する。<br/>
	 * <br/>
	 * @param displayFlg 表示フラグ（0: 非表示、1:表示）
	 */
	public void setDisplayFlg(String displayFlg) {
		this.displayFlg = displayFlg;
	}

// 2015.03.12 H.Mizuno 画像関連属性を追加 start
	/**
	 * 画像パス名を取得する。<br/>
	 * <br/>
	 * @return 画像パス名
	 */
	public String getPathName() {
		return pathName;
	}

	/**
	 * 画像パス名を設定する。<br/>
	 * <br/>
	 * @param pathName 画像パス名
	 */
	public void setPathName(String pathName) {
		this.pathName = pathName;
	}

	/**
	 * 縦長・横長フラグを取得する。<br/>
	 * <br/>
	 * @return 縦長・横長フラグ （0:横長画像、1:縦長画像、2:縦横同一）
	 */
	public String getHwFlg() {
		return hwFlg;
	}

	/**
	 * 縦長・横長フラグを設定する。<br/>
	 * <br/>
	 * @param hwFlg 縦長・横長フラグ （0:横長画像、1:縦長画像、2:縦横同一）
	 */
	public void setHwFlg(String hwFlg) {
		this.hwFlg = hwFlg;
	}
// 2015.03.12 H.Mizuno 画像関連属性を追加 end

	/**
	 * 表示開始日を取得する。<br/>
	 * <br/>
	 * @return 表示開始日
	 */
	public Date getDisplayStartDate() {
		return displayStartDate;
	}
	
	/**
	 * 表示開始日を設定する。<br/>
	 * <br/>
	 * @param displayStartDate　表示開始日
	 */
	public void setDisplayStartDate(Date displayStartDate) {
		this.displayStartDate = displayStartDate;
	}
	
	/**
	 * 表示終了日を取得する。<br/>
	 * <br/>
	 * @param displayEndDate　表示終了日
	 */
	public Date getDisplayEndDate() {
		return displayEndDate;
	}
	
	/**
	 * 表示終了日を設定する。<br/>
	 * <br/>
	 * @param displayEndDate 表示終了日
	 */
	public void setDisplayEndDate(Date displayEndDate) {
		this.displayEndDate = displayEndDate;
	}
	
	/**
	 * 登録日を取得する。<br/>
	 * <br/>
	 * @return 登録日
	 */
	public Date getInsDate() {
		return insDate;
	}
	
	/**
	 * 登録日を設定する。<br/>
	 * <br/>
	 * @return 登録日
	 */
	public void setInsDate(Date insDate) {
		this.insDate = insDate;
	}

	/**
	 * 登録者（管理者ユーザーID）を取得する。<br/>
	 * <br/>
	 * @return 登録者
	 */
	public String getInsUserId() {
		return insUserId;
	}
	
	/**
	 * 登録者（管理者ユーザーID）を設定する。<br/>
	 * <br/>
	 * @param insUserId 登録者
	 */
	public void setInsUserId(String insUserId) {
		this.insUserId = insUserId;
	}

	/**
	 * 最終更新日を取得する。<br/>
	 * <br/>
	 * @return 最終更新日
	 */
	public Date getUpdDate() {
		return updDate;
	}

	/**
	 * 最終更新日を設定する。<br/>
	 * <br/>
	 * @param updDate 最終更新日
	 */
	public void setUpdDate(Date updDate) {
		this.updDate = updDate;
	}
	
	/**
	 * 最終更新者を取得する。<br/>
	 * <br/>
	 * @return 最終更新者
	 */
	public String getUpdUserId() {
		return updUserId;
	}

	/**
	 * 最終更新者を設定する。<br/>
	 * <br/>
	 * @param updUserId 最終更新者
	 */
	public void setUpdUserId(String updUserId) {
		this.updUserId = updUserId;
	}

}
