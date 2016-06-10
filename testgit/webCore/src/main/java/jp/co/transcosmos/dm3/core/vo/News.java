package jp.co.transcosmos.dm3.core.vo;

import java.util.Date;

/**
 * value object news
 * 
 * @author nhatlv
 *
 */
public class News {
	
	/** id of news */
	private String newsId;
	
	/** title of new */
	private String newsTitle;
	
	/** content of news */
	private String newsContent;
	
	/** date insert of news */
	private Date insDate;
	
	/** user id insert of news */
	private String insUserId;
	
	/** date update of news */
	private Date updDate;
	
	/** user id update of news */
	private String updUserId;
	
	/** delete flag of news */
	private boolean delFlg;
	
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
	 * @return the newsTitle
	 */
	public String getNewsTitle() {
		return newsTitle;
	}

	/**
	 * @param newsTitle the newsTitle to set
	 */
	public void setNewsTitle(String newsTitle) {
		this.newsTitle = newsTitle;
	}

	/**
	 * @return the newsContent
	 */
	public String getNewsContent() {
		return newsContent;
	}

	/**
	 * @param newsContent the newsContent to set
	 */
	public void setNewsContent(String newsContent) {
		this.newsContent = newsContent;
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
