package jp.co.transcosmos.dm3.core.validation;

import jp.co.transcosmos.dm3.validation.Validation;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

import org.apache.commons.fileupload.FileItem;

/**
 * <pre>
 * �t�@�C���A�b�v���[�h�`�F�b�N
 * �t�@�C�����A�b�v���[�h���ꂽ���ǂ������`�F�b�N���܂��B
 * 
 * �S����       �C����      �C�����e
 * ------------ ----------- -----------------------------------------------------
 * �ד��@��     2006.12.28  �V�K�쐬
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
