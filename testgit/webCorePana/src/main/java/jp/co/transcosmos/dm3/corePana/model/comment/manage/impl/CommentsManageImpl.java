package jp.co.transcosmos.dm3.corePana.model.comment.manage.impl;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import jp.co.transcosmos.dm3.core.model.mypage.MypageUserManageImpl;
import jp.co.transcosmos.dm3.core.util.ValueObjectFactory;
import jp.co.transcosmos.dm3.corePana.model.CommentsManage;
import jp.co.transcosmos.dm3.corePana.model.comment.form.CommentFormFactory;
import jp.co.transcosmos.dm3.corePana.model.comment.form.CommentSearchForm;
import jp.co.transcosmos.dm3.core.vo.News;
import jp.co.transcosmos.dm3.core.vo.UserComment;
import jp.co.transcosmos.dm3.dao.DAO;
import jp.co.transcosmos.dm3.dao.DAOCriteria;
import jp.co.transcosmos.dm3.dao.NotEnoughRowsException;

/**
 * 
 * @author thoph
 *
 */
public class CommentsManageImpl implements CommentsManage{

private static final Log log = LogFactory.getLog(MypageUserManageImpl.class);
	
	protected ValueObjectFactory valueObjectFactory;

	/** user_comment DAO */
	protected DAO<UserComment> commentListDAO;
	
	/**newsDAO */
	private DAO<News> newsDAO;	
	
	/** The number of retries if the UUID is duplicated */
	protected int maxRetry = 100;

	/** Of password inquiry expiration */
	protected int maxEntryDay = 10;

	/** Form Factory for the comment information */
	protected CommentFormFactory commentFormFactory;

	/**
	 * 
	 * @return commentFormFactory
	 */
	public CommentFormFactory getCommentFormFactory() {
		return commentFormFactory;
	}
	
	/**
	 * 
	 * @param commentFormFactory
	 */
	public void setCommentFormFactory(CommentFormFactory commentFormFactory) {
		this.commentFormFactory = commentFormFactory;
	}
	
	/**
	 * @param valueObjectFactory 
	 */
	public void setValueObjectFactory(ValueObjectFactory valueObjectFactory) {
		this.valueObjectFactory = valueObjectFactory;
	}

	/**
	 * 
	 * @param commentListDAO
	 */
	public void setCommentListDAO(DAO<UserComment> commentListDAO) {
		this.commentListDAO = commentListDAO;
	}

	
	public void setMaxRetry(int maxRetry) {
		this.maxRetry = maxRetry;
	}

	public void setMaxEntryDay(int maxEntryDay) {
		this.maxEntryDay = maxEntryDay;
	}
	
	/**
	 * 
	 * @param newsDAO
	 */
	public void setNewsDAO(DAO<News> newsDAO) {
		this.newsDAO = newsDAO;
	}


	/**
	* Find the My Page comment, to return the results list. <br/>
	* Generate a search by the value of the Form parameters passed in the argument conditions, to find my page comment. <br/>
	* The search results are stored in the Form object, to return the acquired corresponding number as a return value. <br/>
	* <br/>
	* @param SearchForm search conditions, and, of the search result storage object
	* @return The appropriate number
	*/
	@Override
	public int searchComment(CommentSearchForm searchForm) {

        // To generate the conditions to search for my page comment.
        DAOCriteria criteria = searchForm.buildCriteria();

        // Search comment list 
        List<UserComment> commentList;
        try {
        	commentList = this.commentListDAO.selectByFilter(criteria);
        } catch(NotEnoughRowsException err) {
			int pageNo = (err.getMaxRowCount() - 1) / searchForm.getRowsPerPage() + 1;
			log.warn("resetting page to " + pageNo);
			searchForm.setSelectedPage(pageNo);
			criteria = searchForm.buildCriteria();
			commentList = this.commentListDAO.selectByFilter(criteria);
        }
        searchForm.setRows(commentList);
        return commentList.size();
	}

	/**
	 * @param commentId 
	 * get commenid to delete
	 */
	@Override
	public void delComment(String commentId) throws Exception {
		CommentSearchForm searchForm = this.commentFormFactory.createCommentSearchForm();
		searchForm.setKeyCommentId(commentId);
		DAOCriteria criteria = searchForm.buildPkCriteria();
		this.commentListDAO.deleteByFilter(criteria);
	}
	
	/**
	 * @param newsId 
	 */
	@Override
	public News getNewsId(String newsId) throws Exception {
		log.debug(">>>PanaCommonManageImpl.getNewsList()<<<");
		DAOCriteria paramDAOCriteria = new DAOCriteria();
		paramDAOCriteria.addOrderByClause("newsId");
		paramDAOCriteria.addWhereClause("newsId", newsId);
		List<News> listNews = this.newsDAO.selectByFilter(paramDAOCriteria);
		//if id null it will show exception error ( page error)
		if(listNews == null || listNews.size() <= 0){
			throw new RuntimeException();
		}
		 return listNews.get(0);
	}



	

}
