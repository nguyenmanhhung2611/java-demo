package jp.co.transcosmos.dm3.csv;


/**
 * CSV 出力時の変換処理用インターフェース<br/>
 * <br/>
 * SimpleCSVModel を使用した CSV 出力時に、出力値の変換を行う場合、このインターフェース
 * を実装したクラスを作成し、CsvConfig の csvValueConverter プロパティに設定する。<br/>
 * <br/>
 * @author H.Mizuno
 *
 */
public interface CsvValueConverter {

	/**
	 * コンバート処理<br/>
	 * このメソッドは、CSV 出力時、１フィールド毎に実行される。
	 * 変換する場合は必要に応じて値を変換して復帰し、変換が不要な場合は元の値をそのまま
	 * 復帰する事。<br/>
	 * <br/>
	 * @param columnName 出力フィールド名
	 * @param value そのカラムの出力値
	 * @param thisOne CSV 行データ （Value オブジェクト、または、JoinResult）
	 * @return　変換した値
	 */
	public Object convert(String columnName, Object value, Object thisOne);

}
