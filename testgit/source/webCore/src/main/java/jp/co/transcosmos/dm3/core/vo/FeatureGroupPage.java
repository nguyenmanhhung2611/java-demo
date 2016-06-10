package jp.co.transcosmos.dm3.core.vo;

/**
 * 特集グループ対応表.
 * <p>
 * クラス名、および、フィールド名はＤＢテーブル名、列名にマッピングされる。
 * <p>
 *
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
public class FeatureGroupPage {

	/** 特集グループID */
	private String featureGroupId;
	/** 特集ページID */
	private String featurePageId;
	/** 表示順 */
	private Integer sortOrder;

	/**
	 * 特集グループID を取得する。<br/>
	 * <br/>
	 *
	 * @return 特集グループID
	 */
	public String getFeatureGroupId() {
		return featureGroupId;
	}

	/**
	 * 特集グループID を設定する。<br/>
	 * <br/>
	 *
	 * @param featureGroupId
	 */
	public void setFeatureGroupId(String featureGroupId) {
		this.featureGroupId = featureGroupId;
	}

	/**
	 * 特集ページID を取得する。<br/>
	 * <br/>
	 *
	 * @return 特集ページID
	 */
	public String getFeaturePageId() {
		return featurePageId;
	}

	/**
	 * 特集ページID を設定する。<br/>
	 * <br/>
	 *
	 * @param featurePageId
	 */
	public void setFeaturePageId(String featurePageId) {
		this.featurePageId = featurePageId;
	}

	/**
	 * 表示順 を取得する。<br/>
	 * <br/>
	 *
	 * @return 表示順
	 */
	public Integer getSortOrder() {
		return sortOrder;
	}

	/**
	 * 表示順 を設定する。<br/>
	 * <br/>
	 *
	 * @param sortOrder
	 */
	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}
}
