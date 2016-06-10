package jp.co.transcosmos.dm3.core.vo;

import java.util.Date;

/**
 * 
 * @author thoph
 *
 */
public class UserComment {
	
	/** id of comment */
	private String commentId;
	
	/** content of comment */
	private String commentContent;
	
	/** id of news */
	private String newsId;
	
	/** date insert of comment */
	private Date insDate;
	
	/** user id insert of comment */
	private String insUserId;
	
	/** date update of comment */
	private Date updDate;
	
	/** user id udpate of comment */
	private String updUserId;
	
	/** delete flag of comment */
	private boolean delFlg;

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
	 * @return the newId
	 */
	public String getNewsId() {
		return newsId;
	}

	/**
	 * @param newId the newId to set
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
}
