package jp.co.transcosmos.dm3.core.model.social.form;

import java.util.Date;
import java.util.List;
import jp.co.transcosmos.dm3.core.util.LengthValidationUtils;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;
import jp.co.transcosmos.dm3.validation.Validateable;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

/**
 * create new form
 * 
 * @author nhatlv
 *
 */
public class NewsForm implements Validateable{
	/** type command */
	private String command;
	
	/** id of news */
	private String newId;
	
	/** title of news */
	private String titleNew;
	
	/** content of news */
	private String contentNew;
	
	/** user id insert */
	private String insUserId;
	
	/** user id update */
	private String updUserId;
	
	/** date insert */
	private Date insDate;
	
	/** date update */
	private Date updDate;
	
	/** delete flag to set mode block */
	private boolean delFlg;
	
	/** length validation */
	protected LengthValidationUtils lengthUtils;

	/** lookup code */
	private CodeLookupManager codeLookupManager;

	protected NewsForm(){
		super();
	}
	
	protected NewsForm(LengthValidationUtils lengthUtils, CodeLookupManager codeLookupManager){
		super();
		this.lengthUtils = lengthUtils;
		this.codeLookupManager = codeLookupManager;
	}

	/**
	 * validation form
	 * 
	 * @param errors list errors
	 */
	@Override
	public boolean validate(List<ValidationFailure> errors) {
		return false;
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
	 * @return the newId
	 */
	public String getNewId() {
		return newId;
	}

	/**
	 * @param newId the newId to set
	 */
	public void setNewId(String newId) {
		this.newId = newId;
	}

	/**
	 * @return the titleNew
	 */
	public String getTitleNew() {
		return titleNew;
	}

	/**
	 * @param titleNew the titleNew to set
	 */
	public void setTitleNew(String titleNew) {
		this.titleNew = titleNew;
	}

	/**
	 * @return the contentNew
	 */
	public String getContentNew() {
		return contentNew;
	}

	/**
	 * @param contentNew the contentNew to set
	 */
	public void setContentNew(String contentNew) {
		this.contentNew = contentNew;
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
	 * @return the updUserId
	 */
	public String getUpdUserId() {
		return updUserId;
	}

	/**
	 * @param updUserId the updUserId to set
	 */
	public void setUpdUserId(String updUserId) {
		this.updUserId = updUserId;
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
