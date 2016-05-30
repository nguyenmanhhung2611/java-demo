/**
 * 
 */
package jp.co.trancosmos.dm3.corePana.model.news.form;

import java.util.List;

import jp.co.transcosmos.dm3.core.util.LengthValidationUtils;
import jp.co.transcosmos.dm3.dao.JoinResult;
import jp.co.transcosmos.dm3.form.PagingListForm;
import jp.co.transcosmos.dm3.validation.Validateable;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

/**
 * @author hiennt
 *
 */
public class NewsSearchForm extends PagingListForm<JoinResult> implements Validateable{
	private String searchCommand;
	
	private String keyTitleNew;
	
	private String keyContentNew;
	
	protected LengthValidationUtils lengthUtils;
	
	

	public String getSearchCommand() {
		return searchCommand;
	}

	public void setSearchCommand(String searchCommand) {
		this.searchCommand = searchCommand;
	}

	public String getKeyTitleNew() {
		return keyTitleNew;
	}

	public void setKeyTitleNew(String keyTitleNew) {
		this.keyTitleNew = keyTitleNew;
	}

	public String getKeyContentNew() {
		return keyContentNew;
	}

	public void setKeyContentNew(String keyContentNew) {
		this.keyContentNew = keyContentNew;
	}

	public LengthValidationUtils getLengthUtils() {
		return lengthUtils;
	}

	public void setLengthUtils(LengthValidationUtils lengthUtils) {
		this.lengthUtils = lengthUtils;
	}

	protected NewsSearchForm(){
		super();
	}

	protected NewsSearchForm(LengthValidationUtils lengthUtils){
		super();
		this.lengthUtils = lengthUtils;
	}

	@Override
	public boolean validate(List<ValidationFailure> errors) {
		// TODO Auto-generated method stub
		return false;
	}
}
