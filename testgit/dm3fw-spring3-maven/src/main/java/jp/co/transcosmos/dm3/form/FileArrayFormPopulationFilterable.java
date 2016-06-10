package jp.co.transcosmos.dm3.form;

/**
 * 現在の FormPopulator は、FileItem の配列をサポートしていない。<br/>
 * このインターフェースは、配列対応に拡張したインターフェースを提供する。<br/>
 * <br/>
 * FormPopulationFilterable インターフェースは、Form に Filter を実装する場合に使用
 * するインターフェースになる。<br/>
 * このインターフェースを実装してる Form クラスの場合、FormPopulator は、FormPopulationFilterChain
 * の代わりに FileArrayFormPopulationFilterChain を使用する。<br/>
 * FileArrayFormPopulationFilterChain は、FileItem の配列 プロパティをサポートしている。<br/>
 * <br/>
 * @author H.Mizuno
 *
 */
public interface FileArrayFormPopulationFilterable extends FormPopulationFilterable {

	// FormPopulationFilterable との構造的な違いは無い。
	// どちらの Chain を使用するかの識別用インターフェース。
}
