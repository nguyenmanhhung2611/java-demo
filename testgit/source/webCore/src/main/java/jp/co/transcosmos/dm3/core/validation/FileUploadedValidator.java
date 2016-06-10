package jp.co.transcosmos.dm3.core.validation;

import jp.co.transcosmos.dm3.validation.Validation;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

import org.apache.commons.fileupload.FileItem;

/**
 * <pre>
 * ファイルアップロードチェック
 * ファイルがアップロードされたかどうかをチェックします。
 * 
 * 担当者       修正日      修正内容
 * ------------ ----------- -----------------------------------------------------
 * 細島　崇     2006.12.28  新規作成
 *
 * </pre>
 */
public class FileUploadedValidator implements Validation {

	public ValidationFailure validate(String name, Object value) {
		FileItem fileItem = (FileItem) value;
		
		if (fileItem == null || fileItem.getSize() <= 0) {
			return new ValidationFailure("noFileUploaded", name, value, null);
		}
		
		return null;
	}
}
