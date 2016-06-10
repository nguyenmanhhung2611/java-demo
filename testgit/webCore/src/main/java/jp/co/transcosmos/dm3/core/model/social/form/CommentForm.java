package jp.co.transcosmos.dm3.core.model.social.form;

import java.util.Date;
import java.util.List;

import jp.co.transcosmos.dm3.core.util.LengthValidationUtils;
import jp.co.transcosmos.dm3.core.vo.UserComment;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;
import jp.co.transcosmos.dm3.validation.AlphanumericOnlyValidation;
import jp.co.transcosmos.dm3.validation.MaxLengthValidation;
import jp.co.transcosmos.dm3.validation.NullOrEmptyCheckValidation;
import jp.co.transcosmos.dm3.validation.Validateable;
import jp.co.transcosmos.dm3.validation.ValidationChain;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

/**
 * class comment form
 * 
 * @author nhatlv
 *
 */
public class CommentForm implements Validateable{
	/** mode of command */
	private String command;
	
	/** id of comment */
	private String commentId;
	
	/** content of comment */
	private String commentContent;
	
	/** id of news */
	private String newsId;
	
	/** date insert */
	private Date insDate;
	
	/** user id insert */
	private String insUserId;
	
	/** date update */
	private Date updDate;
	
	/** user id update */
	private String updUserId;
	
	/** delete flag of comment */
	private boolean delFlg;
	
	/** length validation */
	protected LengthValidationUtils lengthUtils;

	/** lookup keyword */
	private CodeLookupManager codeLookupManager;

	protected CommentForm(){
		super();
	}

	protected CommentForm(LengthValidationUtils lengthUtils, CodeLookupManager codeLookupManager){
		super();
		this.lengthUtils = lengthUtils;
		this.codeLookupManager = codeLookupManager;
	}

	/**
	 * validation
	 * @param errors list errors
	 */
	@Override
	public boolean validate(List<ValidationFailure> errors) {
		int startSize = errors.size();
		
		// validate content of comment
		validCommentContent(errors);
		
		// validate id of new
		validNewsId(errors);
		
		// validate user id insert 
		validInsUserId(errors);
		
		// validate user id update
		validUpUserId(errors);
		return (startSize == errors.size());
	}

	/**
	 * validate user id update
	 * 
	 * @param errors
	 */
	private void validUpUserId(List<ValidationFailure> errors) {
		ValidationChain validChain = new ValidationChain("social.input.insUserId", this.insUserId);
		validChain.addValidation(new AlphanumericOnlyValidation());
		validChain.addValidation(new MaxLengthValidation(this.lengthUtils.getLength("social.input.insUserId", 20)));
		validChain.validate(errors);
	}

	/**
	 * validate user id insert
	 * 
	 * @param errors
	 */
	private void validInsUserId(List<ValidationFailure> errors) {
		ValidationChain validChain = new ValidationChain("social.input.insUserId", this.insUserId);
		validChain.addValidation(new AlphanumericOnlyValidation());
		validChain.addValidation(new MaxLengthValidation(this.lengthUtils.getLength("social.input.insUserId", 20)));
		validChain.validate(errors);
	}

	/**
	 * validate id of new
	 * 
	 * @param errors
	 */
	private void validNewsId(List<ValidationFailure> errors) {
		ValidationChain validChain = new ValidationChain("social.input.newsId", this.newsId);
		validChain.addValidation(new NullOrEmptyCheckValidation());
		validChain.addValidation(new AlphanumericOnlyValidation());
		validChain.addValidation(new MaxLengthValidation(this.lengthUtils.getLength("social.input.newsId", 20)));
		validChain.validate(errors);
	}

	/**
	 * validate content of comment
	 * 
	 * @param errors
	 */
	private void validCommentContent(List<ValidationFailure> errors) {
		ValidationChain validChain = new ValidationChain("social.input.commentContent", this.commentContent);
		validChain.addValidation(new NullOrEmptyCheckValidation());
		validChain.addValidation(new MaxLengthValidation(this.lengthUtils.getLength("social.input.commentContent", 500)));
		validChain.validate(errors);
	} 
	
	/**
	 * copy data from comment form to object UserComment
	 * 
	 * @param userComment
	 */
	public void copyToUserComment(UserComment userComment) {
		if (!StringValidateUtil.isEmpty(this.commentId)) {
			userComment.setCommentId(this.commentId);
		}
		userComment.setCommentContent(this.commentContent);
		userComment.setNewsId(this.newsId);
		if (!StringValidateUtil.isEmpty(this.insDate.toString())) {
			userComment.setInsDate(this.insDate);
		} else {
			userComment.setInsDate(null);
		}
		if (!StringValidateUtil.isEmpty(this.updDate.toString())) {
			userComment.setUpdDate(this.updDate);
		} else {
			userComment.setUpdDate(null);
		}
		userComment.setInsUserId(this.insUserId);
		userComment.setUpdUserId(this.updUserId);
		userComment.setDelFlg(this.delFlg);
	}

	/**
	 * @return the command
	 */
	public String getCommand() {
		return command;
	}

	/**
	 * @param command the command to set
	 */
	public void setCommand(String command) {
		this.command = command;
	}

	/**
	 * @return the commentId
	 */
	public String getCommentId() {
		return commentId;
	}

	/**
	 * @param commentId the commentId to set
	 */
	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}

	/**
	 * @return the commentContent
	 */
	public String getCommentContent() {
		return commentContent;
	}

	/**
	 * @param commentContent the commentContent to set
	 */
	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}

	/**
	 * @return the newsId
	 */
	public String getNewsId() {
		return newsId;
	}

	/**
	 * @param newsId the newsId to set
	 */
	public void setNewsId(String newsId) {
		this.newsId = newsId;
	}

	/**
	 * @return the insDate
	 */
	public Date getInsDate() {
		return insDate;
	}

	/**
	 * @param insDate the insDate to set
	 */
	public void setInsDate(Date insDate) {
		this.insDate = insDate;
	}

	/**
	 * @param updUserId the updUserId to set
	 */
	public void setUpdUserId(String updUserId) {
		this.updUserId = updUserId;
	}

	/**
	 * @return the insUserId
	 */
	public String getInsUserId() {
		return insUserId;
	}

	/**
	 * @param insUserId the insUserId to set
	 */
	public void setInsUserId(String insUserId) {
		this.insUserId = insUserId;
	}

	/**
	 * @return the updDate
	 */
	public Date getUpdDate() {
		return updDate;
	}

	/**
	 * @param updDate the updDate to set
	 */
	public void setUpdDate(Date updDate) {
		this.updDate = updDate;
	}

	/**
	 * @return the delFlg
	 */
	public boolean isDelFlg() {
		return delFlg;
	}

	/**
	 * @param delFlg the delFlg to set
	 */
	public void setDelFlg(boolean delFlg) {
		this.delFlg = delFlg;
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

	/**
	 * @return the codeLookupManager
	 */
	public CodeLookupManager getCodeLookupManager() {
		return codeLookupManager;
	}

	/**
	 * @param codeLookupManager the codeLookupManager to set
	 */
	public void setCodeLookupManager(CodeLookupManager codeLookupManager) {
		this.codeLookupManager = codeLookupManager;
	}
}
