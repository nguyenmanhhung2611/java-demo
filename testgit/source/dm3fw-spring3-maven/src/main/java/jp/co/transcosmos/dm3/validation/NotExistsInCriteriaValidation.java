package jp.co.transcosmos.dm3.validation;

import jp.co.transcosmos.dm3.dao.DAOCriteria;
import jp.co.transcosmos.dm3.dao.ReadOnlyDAO;
import jp.co.transcosmos.dm3.validation.Validation;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

/**
 * <pre>
 * 存在チェックValidationクラス
 * 指定された値(CD)が存在するかをチェックします。
 * 
 * 担当者       修正日      修正内容
 * ------------ ----------- -----------------------------------------------------
 * 阿部　奈津江 2007.01.25  新規作成
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
