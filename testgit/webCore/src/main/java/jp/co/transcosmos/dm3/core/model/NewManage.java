package jp.co.transcosmos.dm3.core.model;

import java.util.List;

import jp.co.transcosmos.dm3.core.model.social.form.CommentForm;
import jp.co.transcosmos.dm3.core.model.social.form.CommentSearchForm;
import jp.co.transcosmos.dm3.core.model.social.form.NewsForm;
import jp.co.transcosmos.dm3.core.model.social.form.NewsSearchForm;
import jp.co.transcosmos.dm3.core.vo.News;
import jp.co.transcosmos.dm3.core.vo.NewsComment;
import jp.co.transcosmos.dm3.dao.JoinResult;

/**
 * interface handle business logic
 * 
 * @author nhatlv
 *
 */
public interface NewManage {
	
	/**
	 * get list news
	 * 
	 * @param newSearchForm
	 * @return
	 */
	public List<NewsComment> getListNew(NewsSearchForm newSearchForm);
	
	/**
	 *  get news
	 *  
	 * @param newSearchForm
	 * @return
	 */
	public News getNew(NewsSearchForm newSearchForm);
	
	/**
	 * add news
	 * @param inputForm
	 */
	public void addNew(NewsForm inputForm);
	
	/**
	 * edit news
	 * 
	 * @param inputForm
	 * @param newId
	 */
	public void editNew(NewsForm inputForm, String newId);
	
	/**
	 * delete news
	 * 
	 * @param newId
	 */
	public void deleteNew(String newId);
	
	/**
	 * get list comment
	 * 
	 * @param searchForm
	 * @return
	 */
	public List<JoinResult> getListComment(CommentSearchForm searchForm);
	
	/**
	 * get information user
	 * 
	 * @param udpUserId
	 * @return
	 */
	public String getInformationUser(String udpUserId);

	/**
	 * Add comment 
	 * 
	 * @param inputForm
	 * @param userId
	 * @return
	 */
	public String addComment(CommentForm inputForm, String userId);
}
