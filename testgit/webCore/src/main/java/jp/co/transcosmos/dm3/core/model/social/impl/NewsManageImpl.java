package jp.co.transcosmos.dm3.core.model.social.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import jp.co.transcosmos.dm3.core.model.NewManage;
import jp.co.transcosmos.dm3.core.model.adminUser.form.AdminUserFormFactory;
import jp.co.transcosmos.dm3.core.model.adminUser.form.AdminUserSearchForm;
import jp.co.transcosmos.dm3.core.model.social.form.CommentForm;
import jp.co.transcosmos.dm3.core.model.social.form.CommentSearchForm;
import jp.co.transcosmos.dm3.core.model.social.form.NewsForm;
import jp.co.transcosmos.dm3.core.model.social.form.NewsSearchForm;
import jp.co.transcosmos.dm3.core.util.ValueObjectFactory;
import jp.co.transcosmos.dm3.core.vo.AdminLoginInfo;
import jp.co.transcosmos.dm3.core.vo.MemberInfo;
import jp.co.transcosmos.dm3.core.vo.News;
import jp.co.transcosmos.dm3.core.vo.NewsComment;
import jp.co.transcosmos.dm3.core.vo.UserComment;
import jp.co.transcosmos.dm3.dao.DAO;
import jp.co.transcosmos.dm3.dao.DAOCriteria;
import jp.co.transcosmos.dm3.dao.DAOCriteriaGroupByClause;
import jp.co.transcosmos.dm3.dao.GroupByDAO;
import jp.co.transcosmos.dm3.dao.JoinCondition;
import jp.co.transcosmos.dm3.dao.JoinDAO;
import jp.co.transcosmos.dm3.dao.JoinResult;
import jp.co.transcosmos.dm3.dao.NotEnoughRowsException;
import jp.co.transcosmos.dm3.dao.OnClause;
import jp.co.transcosmos.dm3.dao.ReflectingDAO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * implement interface newmanage handle business logic
 * 
 * @author nhatlv
 *
 */
public class NewsManageImpl implements NewManage{
	
	/** log information */
	private static final Log log = LogFactory.getLog(NewsManageImpl.class);

	/** value object handle mapping with table in database */
	protected ValueObjectFactory valueObjectFactory;
	
	/** join dao to join 2 table new and comment */
	protected JoinDAO newsListDAO;
	
	/** dao of new */
	protected ReflectingDAO<News> newsDAO;
	
	/** dao of table user_comment */
	protected DAO<UserComment> commentDAO;
	
	/** dao of table adminLoginInfor */
	protected DAO<AdminLoginInfo> adminLoginInfoDAO;
	
	/** dao of table memberInfo */
	protected DAO<MemberInfo> memberInfoDAO;
	
	/** dao to join table comment and adminLoginInfo */
	protected DAO<JoinResult> commentListDAO;
    
	/** alias of table new */
	public static final String NEW_ALIA = "news";
	
	/** alias of table user_comment */
	public static final String COMMENT_ALIA = "user_comment";

	/** 
	 * get list new
	 * 
	 * @param newSearchForm
	 */
	@Override
	public List<NewsComment> getListNew(NewsSearchForm newSearchForm) {
		// create group by dao to set condition group by to sql query 
		GroupByDAO<NewsComment> grpDao =  new GroupByDAO<NewsComment>();
		
		// set condition group by
		grpDao.setGroupByClauses(Arrays.asList(new DAOCriteriaGroupByClause("newsId")));
		
		// value object to mapping data get from database
		grpDao.setValueObjectClassName("jp.co.transcosmos.dm3.core.vo.NewsComment");
		
		// set condition join
		JoinCondition joinCondion = new JoinCondition();
		joinCondion.setField1("newsId");
		joinCondion.setField2("newsId");
		
		// add condition for join
		DAOCriteria criteriaJoin = new DAOCriteria();
		criteriaJoin.addWhereClause(COMMENT_ALIA, "delFlg", true, DAOCriteria.EQUALS, false);
		
		List<OnClause> listOnClause= Arrays.asList(joinCondion, criteriaJoin);
		newsListDAO.setConditionList(listOnClause);
		
		// set datasource
		grpDao.setDataSource(newsDAO.getDataSource());
		
		// set dao need group by
		grpDao.setTargetDAO(newsListDAO);
		
		// set condition to execute sql query
		DAOCriteria criteria = newSearchForm.buildCriteria();
		criteria.addOrderByClause("updDate", false);
		criteria.addWhereClause(NEW_ALIA, "delFlg", true, DAOCriteria.EQUALS, false);
		List<NewsComment> newList;
		try {
			newList = grpDao.selectByFilter(criteria);
		} catch (NotEnoughRowsException err) {
			// if list is not enough row
			int pageNo = (err.getMaxRowCount() - 1)
					/ newSearchForm.getRowsPerPage() + 1;
			log.warn("resetting page to " + pageNo);
			newSearchForm.setSelectedPage(pageNo);
			criteria = newSearchForm.buildCriteria();
			newList = grpDao.selectByFilter(criteria);
		}
		
		// set list new to new search form
		newSearchForm.setRows(newList);
		return newList;
	}

	/**
	 * get new
	 * 
	 * @param newSearchForm
	 */
	@Override
	public News getNew(NewsSearchForm newSearchForm) {
		// set condition to sql query
		DAOCriteria criteria = newSearchForm.buildNewsIdCriteria();
		List<News> newsList = newsDAO.selectByFilter(criteria);
		News news = new News();
		if(newsList.size() != 0) {
			news = newsList.get(0);
		}
		return news;
	}
	
	/**
	 * get list comment join with adminLoginInfo
	 * 
	 * @param searchForm comment search form
	 */
	@Override
	public List<JoinResult> getListComment(CommentSearchForm searchForm) {
		// set condition to paging
		DAOCriteria criteria = searchForm.buildCriteria();
		
		// set condition for sql query
		criteria.addOrderByClause("updDate", false);
		criteria.addWhereClause("delFlg", true);
		criteria.addWhereClause("newsId", searchForm.getKeyNewsId());
		List<JoinResult> listComments;
		try {
			listComments = commentListDAO.selectByFilter(criteria);
		} catch (NotEnoughRowsException err) {
			// if result search is not enough row
			int pageNo = (err.getMaxRowCount() - 1)
					/ searchForm.getRowsPerPage() + 1;
			log.warn("resetting page to " + pageNo);
			searchForm.setSelectedPage(pageNo);
			criteria = searchForm.buildCriteria();
			listComments = commentListDAO.selectByFilter(criteria);
		}
		
		// set list search to search form
		searchForm.setRows(listComments);
		return listComments;
	}

	/**
	 * get information user follow userId
	 * 
	 * @param udpUserId
	 */
	@Override
	public String getInformationUser(String udpUserId) {
		// create admin user search form
		AdminUserSearchForm adminSearchForm = new AdminUserFormFactory().createUserSearchForm();
		adminSearchForm.setUserId(udpUserId);
		DAOCriteria criteria = adminSearchForm.buildPkCriteria();
		List<AdminLoginInfo> adminLoginInfo = adminLoginInfoDAO.selectByFilter(criteria);
		String userName = null;
		if(adminLoginInfo.size() != 0) {
			userName = adminLoginInfo.get(0).getUserName();
		}
		return userName;
	}
	
	/**
	 * add comment
	 * 
	 * @param inputForm
	 * @param userId
	 */
	@Override
	public String addComment(CommentForm inputForm, String userId) {
		// set user id insert, update of comment
		inputForm.setInsUserId(userId);
		inputForm.setUpdUserId(userId);
		
		// set date insert, update of comment
		Date dateNow = new Date();
		inputForm.setInsDate(dateNow);
		inputForm.setUpdDate(dateNow);
		
		// set delete flag
		inputForm.setDelFlg(true);
		UserComment userComment = (UserComment) valueObjectFactory.getValueObject("UserComment");
		inputForm.copyToUserComment(userComment);
		commentDAO.insert(new UserComment[] { userComment });
		return userComment.getCommentId();
	}

	/**
	 * add new 
	 * 
	 * @param inputForm
	 */
	@Override
	public void addNew(NewsForm inputForm) {
		// TODO Auto-generated method stub
	}

	/**
	 * edit new
	 * 
	 * @param inputForm 
	 * @param newId
	 */
	@Override
	public void editNew(NewsForm inputForm, String newId) {
		// TODO Auto-generated method stub
	}

	/**
	 * delete new 
	 * 
	 * @param newId
	 */
	@Override
	public void deleteNew(String newId) {
		// TODO Auto-generated method stub
	}
	
	/**
	 * @return the newsDAO
	 */
	public ReflectingDAO<News> getNewsDAO() {
		return newsDAO;
	}

	/**
	 * @param newsDAO the newsDAO to set
	 */
	public void setNewsDAO(ReflectingDAO<News> newsDAO) {
		this.newsDAO = newsDAO;
	}

	/**
	 * @return the valueObjectFactory
	 */
	public ValueObjectFactory getValueObjectFactory() {
		return valueObjectFactory;
	}

	/**
	 * @param valueObjectFactory the valueObjectFactory to set
	 */
	public void setValueObjectFactory(ValueObjectFactory valueObjectFactory) {
		this.valueObjectFactory = valueObjectFactory;
	}

	/**
	 * @return the newsListDAO
	 */
	public JoinDAO getNewsListDAO() {
		return newsListDAO;
	}

	/**
	 * @param newsListDAO the newsListDAO to set
	 */
	public void setNewsListDAO(JoinDAO newsListDAO) {
		this.newsListDAO = newsListDAO;
	}

	/**
	 * @return the commentDAO
	 */
	public DAO<UserComment> getCommentDAO() {
		return commentDAO;
	}

	/**
	 * @param commentDAO the commentDAO to set
	 */
	public void setCommentDAO(DAO<UserComment> commentDAO) {
		this.commentDAO = commentDAO;
	}
	
	/**
	 * @return the adminLoginInfoDAO
	 */
	public DAO<AdminLoginInfo> getAdminLoginInfoDAO() {
		return adminLoginInfoDAO;
	}

	/**
	 * @param adminLoginInfoDAO the adminLoginInfoDAO to set
	 */
	public void setAdminLoginInfoDAO(DAO<AdminLoginInfo> adminLoginInfoDAO) {
		this.adminLoginInfoDAO = adminLoginInfoDAO;
	}

	/**
	 * @return the memberInfoDAO
	 */
	public DAO<MemberInfo> getMemberInfoDAO() {
		return memberInfoDAO;
	}

	/**
	 * @param memberInfoDAO the memberInfoDAO to set
	 */
	public void setMemberInfoDAO(DAO<MemberInfo> memberInfoDAO) {
		this.memberInfoDAO = memberInfoDAO;
	}

	/**
	 * @return the commentListDAO
	 */
	public DAO<JoinResult> getCommentListDAO() {
		return commentListDAO;
	}

	/**
	 * @param commentListDAO the commentListDAO to set
	 */
	public void setCommentListDAO(DAO<JoinResult> commentListDAO) {
		this.commentListDAO = commentListDAO;
	}
}
