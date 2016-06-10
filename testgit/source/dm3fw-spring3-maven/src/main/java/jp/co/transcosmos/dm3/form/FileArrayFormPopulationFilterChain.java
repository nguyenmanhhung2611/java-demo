package jp.co.transcosmos.dm3.form;

import java.util.Iterator;
import org.apache.commons.fileupload.FileItem;


/**
 * 現在の FormPopulator は、FileItem の配列をサポートしていない。<br/>
 * この　Chain クラスは、配列対応に拡張したメソッドを提供する。<br/>
 * <br/>
 * @author H.Mizuno
 *
 */
public class FileArrayFormPopulationFilterChain extends FormPopulationFilterChain {

	// note
	// FileItem で Filter を使用する場合は、
	// addAllFieldFilter()、addPerFieldFilter() で Filter を追加する際い、
	// FileArrayFormPopulationFilter インターフェースを実装したクラスを使用する事。


	/**
	 * FileItem　配列プロパティ用の Filter 処理。<br/>
	 * <br/>
	 * @param fieldName フィールド名
	 * @param fileItem 取得した値
	 * @return
	 */
    public FileItem[] filterFileItem(String fieldName, FileItem[] fileItem) {
        if (this.elements != null) {
            for (Iterator<ChainElement> i = this.elements.iterator(); i.hasNext(); ) {
                ChainElement elm = i.next();
                if ((elm.fieldName == null) || elm.fieldName.equals(fieldName)) {
                    fileItem = ((FileArrayFormPopulationFilter)elm.filter).filterFileItem(fileItem);
                }
            }
        }
        return fileItem;
    }

}
