package jp.co.transcosmos.dm3.form;

import org.apache.commons.fileupload.FileItem;

/**
 * 現在の FormPopulator は、FileItem の配列をサポートしていない。<br/>
 * このインターフェースは、配列対応に拡張したインターフェースを提供する。<br/>
 * <br/>
 * @author H.Mizuno
 *
 */
public interface FileArrayFormPopulationFilter extends FormPopulationFilter {

	/**
	 * FileItem が配列で宣言されている場合用の Filter インターフェース<br/>
	 * <br/>
	 * @param input FileImte の配列オブジェクト
	 * @return FileImte の配列オブジェクト
	 */
    public FileItem[] filterFileItem(FileItem[] input);

}
