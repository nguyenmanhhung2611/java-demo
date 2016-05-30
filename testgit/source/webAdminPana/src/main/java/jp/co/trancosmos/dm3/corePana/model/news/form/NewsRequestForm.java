/**
 * 
 */
package jp.co.trancosmos.dm3.corePana.model.news.form;

import java.util.Date;

import jp.co.transcosmos.dm3.core.util.LengthValidationUtils;
import jp.co.transcosmos.dm3.core.vo.News;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;

/**
 * @author hiennt
 *
 */
public class NewsRequestForm {
	
	protected LengthValidationUtils lengthUtils;

	private CodeLookupManager codeLookupManager;
	
	private String newsId;
	private String newsTitle;
	private String newsContent;
	private Date insDate;
	private String insUserId;
	private Date updDate;
	private String updUserId;
	private boolean delFlg;

	public String getNewsId() {
		return newsId;
	}

	public void setNewsId(String newsId) {
		this.newsId = newsId;
	}

	public String getNewsTitle() {
		return newsTitle;
	}

	public void setNewsTitle(String newsTitle) {
		this.newsTitle = newsTitle;
	}

	public String getNewsContent() {
		return newsContent;
	}

	public void setNewsContent(String newsContent) {
		this.newsContent = newsContent;
	}

	public Date getInsDate() {
		return insDate;
	}

	public void setInsDate(Date insDate) {
		this.insDate = insDate;
	}

	public String getInsUserId() {
		return insUserId;
	}

	public void setInsUserId(String insUserId) {
		this.insUserId = insUserId;
	}

	public Date getUpdDate() {
		return updDate;
	}

	public void setUpdDate(Date updDate) {
		this.updDate = updDate;
	}

	public String getUpdUserId() {
		return updUserId;
	}

	public void setUpdUserId(String updUserId) {
		this.updUserId = updUserId;
	}

	public boolean isDelFlg() {
		return delFlg;
	}

	public void setDelFlg(boolean delFlg) {
		this.delFlg = delFlg;
	}
	
	
	
	public LengthValidationUtils getLengthUtils() {
		return lengthUtils;
	}

	public void setLengthUtils(LengthValidationUtils lengthUtils) {
		this.lengthUtils = lengthUtils;
	}

	public CodeLookupManager getCodeLookupManager() {
		return codeLookupManager;
	}

	public void setCodeLookupManager(CodeLookupManager codeLookupManager) {
		this.codeLookupManager = codeLookupManager;
	}

	protected  NewsRequestForm(LengthValidationUtils lengthUtils, CodeLookupManager codeLookupManager){
		super();
		this.lengthUtils = lengthUtils;
		this.codeLookupManager = codeLookupManager;
	}
	
	public void copyToNews(String userId, News newsRequestInfo) {

		newsRequestInfo.setNewsId(newsId);
		newsRequestInfo.setNewsTitle(newsTitle);
		newsRequestInfo.setNewsContent(newsContent);
		newsRequestInfo.setInsDate(insDate);
		newsRequestInfo.setInsUserId(insUserId);
		newsRequestInfo.setUpdDate(updDate);
		newsRequestInfo.setUpdUserId(updUserId);
		newsRequestInfo.setDelFlg(delFlg);
	}

}
