package jp.co.transcosmos.dm3.dao;


/**
 * フィールド情報オブジェクト<br/>
 * DAOCriteria を使用して検索条件を生成する時に、バインド変数値の変わりにこのインスタンスを設定すると、
 * 固定フィールドへの条件を生成する事ができる。<br/>
 * <br/>
 * 例）<br/>
 * subCriteria.addWhereClause("column1", 20);<br/>
 * の場合、「column1 = ?」　が SQL として生成される。<br/>
 * subCriteria.addWhereClause("column1", new FieldExpression("s", "column2"));<br/>
 * の場合、「column1 = s.column2」　が SQL として生成される。<br/>
 * <br/>
 * @author H.Mizuno
 *
 */
public class FieldExpression {

	/** 
	 * テーブルの別名<br/>
	 * 別名ではなくテーブル名を指定する場合、キャメルスタイルではなく、実テーブル名を指定する事。<br/>
	 */
	private String alias;

	/** フィールド名 */
	private String fieldName;



	/**
	 * コンストラクタ<br/>
	 * <br/>
	 * @param alias テーブルの別名
	 * @param fieldName フィールド名
	 */
	public FieldExpression(String alias, String fieldName){
		this.alias = alias;
		this.fieldName = fieldName;
	}


	
	/**
	 * テーブルの別名を取得する。<br/>
	 * <br/>
	 * @return テーブルの別名
	 */
	public String getAlias() {
		return alias;
	}

	/**
	 * フィールド名を取得する。<br/>
	 * <br/>
	 * @return フィールド名
	 */
	public String getFieldName() {
		return fieldName;
	}

}
