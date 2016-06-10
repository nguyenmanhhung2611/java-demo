package jp.co.transcosmos.dm3.core.model.social.form;

import java.util.List;
import jp.co.transcosmos.dm3.core.util.LengthValidationUtils;
import jp.co.transcosmos.dm3.core.vo.NewsComment;
import jp.co.transcosmos.dm3.dao.DAOCriteria;
import jp.co.transcosmos.dm3.form.PagingListForm;
import jp.co.transcosmos.dm3.validation.Validateable;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

/**
 * new search form
 * 
 * @author nhatlv
 *
 */
public class NewsSearchForm extends PagingListForm<NewsComment> implements Validateable{
	/** set mode command search  */
	private String searchCommand;
	
	/** id of news */
	private String keyNewsId;
	
	/** title of news */
	private String keyNewsTitle;
	
	/** content of news */
	private String keyNewsContent;
	
	/** delete flag of news */
	private boolean keyDelFlg;
	
	/** length validation */
	protected LengthValidationUtils lengthUtils;

	protected NewsSearchForm(){
		super();
	}

	protected NewsSearchForm(LengthValidationUtils lengthUtils){
		super();
		this.lengthUtils = lengthUtils;
	}

	@Override
	public boolean validate(List<ValidationFailure> errors) {
        return true;
	}
	
	/**
	 * set criteria for condition sql query
	 * 
	 * @return
	 */
	public DAOCriteria buildNewsIdCriteria(){
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("newsId", this.keyNewsId);
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
	 * @return the keyNewsTitle
	 */
	public String getKeyNewsTitle() {
		return keyNewsTitle;
	}

	/**
	 * @param keyNewsTitle the keyNewsTitle to set
	 */
	public void setKeyNewsTitle(String keyNewsTitle) {
		this.keyNewsTitle = keyNewsTitle;
	}

	/**
	 * @return the keyNewsContent
	 */
	public String getKeyNewsContent() {
		return keyNewsContent;
	}

	/**
	 * @param keyNewsContent the keyNewsContent to set
	 */
	public void setKeyNewsContent(String keyNewsContent) {
		this.keyNewsContent = keyNewsContent;
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
