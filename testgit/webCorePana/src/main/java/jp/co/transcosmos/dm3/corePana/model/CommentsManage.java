package jp.co.transcosmos.dm3.corePana.model;

import jp.co.transcosmos.dm3.core.vo.News;
import jp.co.transcosmos.dm3.corePana.model.comment.form.CommentSearchForm;

/**
 * 
 * @author thoph
 *
 */
public interface CommentsManage {
	
	/**
	 * 
	 * @param searchForm
	 * @return
	 * @throws Exception
	 */
	public int searchComment(CommentSearchForm searchForm) throws Exception;
	
	/**
	 * 
	 * @param commentId
	 * @throws Exception
	 */
	public void delComment(String commentId) throws Exception;
	
	/**
	 * Get 1 item by id
	 * @param newsId
	 * @return
	 * @throws Exception
	 */
	public News getNewsId(String newsId) throws Exception;
}
