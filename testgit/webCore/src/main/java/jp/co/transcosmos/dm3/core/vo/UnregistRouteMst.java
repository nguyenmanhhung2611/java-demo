package jp.co.transcosmos.dm3.core.vo;

/**
 * 登録除外路線マスタクラス.
 * <p>
 * このマスタに設定された路線CD は、CSV によるマスタメンテナンスの対象外になる。<br/>
 * クラス名、および、フィールド名はＤＢテーブル名、列名にマッピングされる。
 * <p>
 * <pre>
 * 担当者		修正日		修正内容
 * ------------ ----------- -----------------------------------------------------
 * 細島　崇		2006.12.29	新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 * 個別カスタマイズで継承される可能性があるので、直接インスタンスを生成しない事。<br/>
 * インスタンスを取得する場合は、ValueObjectFactory から取得する事。<br/>
 * 
 */
public class UnregistRouteMst {

	/**　CSV 取り込み対象外となる路線CD */
	private String routeCd;
	
	/** CSV 取り込み対象外となる路線名 */	
	private String routeName;



	/**
	 * CSV 取り込み対象外となる路線CD を取得する。<br/>
	 * <br/>
	 * @return 取り込み対象外となる路線CD
	 */
	public String getRouteCd() {
		return routeCd;
	}
	
	/**
	 * CSV 取り込み対象外となる路線CD を設定する。<br/>
	 * <br/>
	 * @param routeCd 取り込み対象外となる路線CD
	 */
	public void setRouteCd(String routeCd) {
		this.routeCd = routeCd;
	}
	
	/**
	 * CSV 取り込み対象外となる路線名を取得する。<br/>
	 * <br/>
	 * @return CSV 取り込み対象外となる路線名
	 */
	public String getRouteName() {
		return routeName;
	}
	
	/**
	 * CSV 取り込み対象外となる路線名を設定する。<br/>
	 * <br/>
	 * @param routeName CSV 取り込み対象外となる路線名
	 */
	public void setRouteName(String routeName) {
		this.routeName = routeName;
	}

}
