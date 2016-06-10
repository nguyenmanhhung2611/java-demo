package jp.co.transcosmos.dm3.core.model;

import java.util.List;

import jp.co.transcosmos.dm3.core.model.exception.NotFoundException;
import jp.co.transcosmos.dm3.core.model.news.form.NewsForm;
import jp.co.transcosmos.dm3.core.model.news.form.NewsSearchForm;
import jp.co.transcosmos.dm3.core.model.social.form.CommentSearchForm;
import jp.co.transcosmos.dm3.core.util.ValueObjectFactory;
import jp.co.transcosmos.dm3.core.vo.News;
import jp.co.transcosmos.dm3.core.vo.UserComment;
import jp.co.transcosmos.dm3.dao.DAO;
import jp.co.transcosmos.dm3.dao.DAOCriteria;
import jp.co.transcosmos.dm3.dao.JoinResult;
import jp.co.transcosmos.dm3.dao.NotEnoughRowsException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <pre>
 * お知らせメンテナンス用 Model クラス
 * 
 * 担当者       修正日      修正内容
 * ------------ ----------- -----------------------------------------------------
 *@author hiennt
 * 
 * </pre>
 */
public class NewsManageImpl implements NewsManage {

	private static final Log log = LogFactory.getLog(NewsManageImpl.class);

	/** VO のインスタンスを生成する場合のファクトリー */
	protected ValueObjectFactory valueObjectFactory;
	
	/** お知らせ情報取得用 DAO */
	protected DAO<JoinResult> newsListDAO;
	
	protected DAO<News> newsDAO;
	
	protected DAO<UserComment> commentsDAO;

	public void setCommentsDAO(DAO<UserComment> commentsDAO) {
		this.commentsDAO = commentsDAO;
	}

	public void setNewsDAO(DAO<News> newsDAO) {
		this.newsDAO = newsDAO;
	}

	/**
	 * バリーオブジェクトのインスタンスを生成するファクトリーを設定する。<br/>
	 * <br/>
	 * @param valueObjectFactory バリーオブジェクトのファクトリー
	 */
	public void setValueObjectFactory(ValueObjectFactory valueObjectFactory) {
		this.valueObjectFactory = valueObjectFactory;
	}	

	@Override
	public String addNews(NewsForm inputForm, String editUserId){

    	// 新規登録処理の場合、入力フォームの値を設定するバリーオブジェクトを生成する。
		// バリーオブジェクトは、ファクトリーメソッド以外では生成しない事。
		// （継承されたバリーオブジェクトが使用されなくなる為。）
		News news = (News) this.valueObjectFactory.getValueObject("News");

    	// フォームの入力値をバリーオブジェクトに設定する。
    	inputForm.copyToNews(news, editUserId);
		
    	// 新規登用のタイムスタンプ情報を設定する。 （更新日の設定情報を転記）
    	news.setInsDate(news.getUpdDate());
    	news.setInsUserId(editUserId);

		// 取得した主キー値で管理ユーザー情報を登録
		this.newsDAO.insert(new News[] { news });

		return news.getNewsId();
	}
		
	
	/**
	 * Search data with condition
	 * @param searchForm 
	 * 			+ keyNewsTitle
	 * 			+ keyNewsContent
	 * @return list news
	 */
	@Override
	public int searchAdminNews(NewsSearchForm searchForm) {

		// お知らせ情報を検索する条件を生成する。
		DAOCriteria criteria = searchForm.buildCriteria();

		// お知らせの検索
		List<News> newsList;
		try {
			newsList = this.newsDAO.selectByFilter(criteria);

		} catch (NotEnoughRowsException err) {

			int pageNo = (err.getMaxRowCount() - 1)/ searchForm.getRowsPerPage() + 1;
			log.warn("resetting page to " + pageNo);
			searchForm.setSelectedPage(pageNo);
			criteria = searchForm.buildCriteria();
			newsList = this.newsDAO.selectByFilter(criteria);
		}

		searchForm.setRows(newsList);

		return newsList.size();
	}


	/**
	 * Search data with condition
	 * @param newsId
	 * 			
	 * @return item news
	 */
	@Override
	public News searchTopNewsPk(String newsId) {

		// お知らせ情報を取得する為の主キーを対象とした検索条件を生成する。
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("newsId", newsId);

		// 公開対象区分 = 「仮を含む全会員」
		criteria.addWhereClause("delFlg", "1");
		
		// お知らせ情報を取得
		List<News> newsList = this.newsDAO.selectByFilter(criteria);

		if (newsList == null || newsList.size() == 0) {
			return null;
		}

		return newsList.get(0);
	}

	
	@Override
	public void delNews(NewsForm inputForm) {
		this.commentsDAO.deleteByFilter(inputForm.buildPkCriteria());
		this.newsDAO.deleteByFilter(inputForm.buildPkCriteria());
	}

	/**
	 * Update one record in list news
	 * @param inputForm, userId
	 * @return news
	 */
	@Override
	public void updateNews(NewsForm inputForm, String editUserId) throws Exception, NotFoundException {
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("newsId", inputForm.getNewsId());

		List<News> listNews = this.newsDAO.selectByFilter(criteria);

		if (listNews == null || listNews.size() == 0) {
			throw new NotFoundException();
		}    	

		News news = (News) listNews.get(0);

    	inputForm.copyToNews(news, editUserId);

		this.newsDAO.update(new News[]{news});
		
	}
}
