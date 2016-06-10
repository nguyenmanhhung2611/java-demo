package jp.co.transcosmos.dm3.core.model.social.form;

import java.util.List;

import jp.co.transcosmos.dm3.core.util.LengthValidationUtils;
import jp.co.transcosmos.dm3.dao.DAOCriteria;
import jp.co.transcosmos.dm3.dao.JoinResult;
import jp.co.transcosmos.dm3.form.PagingListForm;
import jp.co.transcosmos.dm3.validation.Validateable;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

/**
 * comment search form
 * 
 * @author nhatlv
 *
 */
public class CommentSearchForm extends PagingListForm<JoinResult> implements Validateable{
	/** mode of command */
	private String searchCommand;
	
	/** id of comment */
	private String keyCommentId;
	
	/** content of comment */
	private String keyCommentContent;
	
	/** id of new */
	private String keyNewsId;
	
	/** delete flag of comment */
	private boolean keyDelFlg;
	
	/** length validation */
	protected LengthValidationUtils lengthUtils;

	protected CommentSearchForm(){
		super();
	}

	protected CommentSearchForm(LengthValidationUtils lengthUtils){
		super();
		this.lengthUtils = lengthUtils;
	}

	/**
	 * validation form
	 * @param errors list errors
	 */
	@Override
	public boolean validate(List<ValidationFailure> errors) {
        return true;
	}
	
	/**
	 * set criteria for condition search
	 * @return
	 */
	public DAOCriteria buildDelFlagCriteria(){
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("del_flg", this.keyDelFlg);
		return criteria;
	}

	/**
	 * @return the searchCommand
	 */
	public String getSearchCommand() {
		return searchCommand;
	}

	/**
	 * @param searchCommand the searchCommand to set
	 */
	public void setSearchCommand(String searchCommand) {
		this.searchCommand = searchCommand;
	}

	/**
	 * @return the keyCommentId
	 */
	public String getKeyCommentId() {
		return keyCommentId;
	}

	/**
	 * @param keyCommentId the keyCommentId to set
	 */
	public void setKeyCommentId(String keyCommentId) {
		this.keyCommentId = keyCommentId;
	}

	/**
	 * @return the keyCommentContent
	 */
	public String getKeyCommentContent() {
		return keyCommentContent;
	}

	/**
	 * @param keyCommentContent the keyCommentContent to set
	 */
	public void setKeyCommentContent(String keyCommentContent) {
		this.keyCommentContent = keyCommentContent;
	}

	/**
	 * @return the keyNewsId
	 */
	public String getKeyNewsId() {
		return keyNewsId;
	}

	/**
	 * @param keyNewsId the keyNewsId to set
	 */
	public void setKeyNewsId(String keyNewsId) {
		this.keyNewsId = keyNewsId;
	}

	/**
	 * @return the keyDelFlg
	 */
	public boolean isKeyDelFlg() {
		return keyDelFlg;
	}

	/**
	 * @param keyDelFlg the keyDelFlg to set
	 */
	public void setKeyDelFlg(boolean keyDelFlg) {
		this.keyDelFlg = keyDelFlg;
	}

	/**
	 * @return the lengthUtils
	 */
	public LengthValidationUtils getLengthUtils() {
		return lengthUtils;
	}

	/**
	 * @param lengthUtils the lengthUtils to set
	 */
	public void setLengthUtils(LengthValidationUtils lengthUtils) {
		this.lengthUtils = lengthUtils;
	}
}
