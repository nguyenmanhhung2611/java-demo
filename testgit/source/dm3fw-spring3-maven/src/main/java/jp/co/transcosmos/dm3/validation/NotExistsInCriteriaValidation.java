package jp.co.transcosmos.dm3.validation;

import jp.co.transcosmos.dm3.dao.DAOCriteria;
import jp.co.transcosmos.dm3.dao.ReadOnlyDAO;
import jp.co.transcosmos.dm3.validation.Validation;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

/**
 * <pre>
 * ���݃`�F�b�NValidation�N���X
 * �w�肳�ꂽ�l(CD)�����݂��邩���`�F�b�N���܂��B
 * 
 * �S����       �C����      �C�����e
 * ------------ ----------- -----------------------------------------------------
 * �����@�ޒÍ] 2007.01.25  �V�K�쐬
 *
 * </pre>
*/
public class NotExistsInCriteriaValidation implements Validation {
	
	private ReadOnlyDAO<?> dao;
	private DAOCriteria criteria;
	
	public NotExistsInCriteriaValidation(ReadOnlyDAO<?> dao, DAOCriteria criteria) {
		this.dao = dao;
		this.criteria = criteria;
	}
	
	public ValidationFailure validate(String name, Object value) {
		if ((value == null) || value.equals("")) {
            return null;
        }
		
		if (this.dao.getRowCountMatchingFilter(criteria) == 0) {
            return new ValidationFailure("notExistsInDB", name, value, null);
        }
		return null;
	}
	
}
