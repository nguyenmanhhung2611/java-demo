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
 * ���m�点�����e�i���X�p Model �N���X
 * 
 * �S����       �C����      �C�����e
 * ------------ ----------- -----------------------------------------------------
 *@author hiennt
 * 
 * </pre>
 */
public class NewsManageImpl implements NewsManage {

	private static final Log log = LogFactory.getLog(NewsManageImpl.class);

	/** VO �̃C���X�^���X�𐶐�����ꍇ�̃t�@�N�g���[ */
	protected ValueObjectFactory valueObjectFactory;
	
	/** ���m�点���擾�p DAO */
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
	 * �o���[�I�u�W�F�N�g�̃C���X�^���X�𐶐�����t�@�N�g���[��ݒ肷��B<br/>
	 * <br/>
	 * @param valueObjectFactory �o���[�I�u�W�F�N�g�̃t�@�N�g���[
	 */
	public void setValueObjectFactory(ValueObjectFactory valueObjectFactory) {
		this.valueObjectFactory = valueObjectFactory;
	}	

	@Override
	public String addNews(NewsForm inputForm, String editUserId){

    	// �V�K�o�^�����̏ꍇ�A���̓t�H�[���̒l��ݒ肷��o���[�I�u�W�F�N�g�𐶐�����B
		// �o���[�I�u�W�F�N�g�́A�t�@�N�g���[���\�b�h�ȊO�ł͐������Ȃ����B
		// �i�p�����ꂽ�o���[�I�u�W�F�N�g���g�p����Ȃ��Ȃ�ׁB�j
		News news = (News) this.valueObjectFactory.getValueObject("News");

    	// �t�H�[���̓��͒l���o���[�I�u�W�F�N�g�ɐݒ肷��B
    	inputForm.copyToNews(news, editUserId);
		
    	// �V�K�o�p�̃^�C���X�^���v����ݒ肷��B �i�X�V���̐ݒ����]�L�j
    	news.setInsDate(news.getUpdDate());
    	news.setInsUserId(editUserId);

		// �擾������L�[�l�ŊǗ����[�U�[����o�^
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

		// ���m�点����������������𐶐�����B
		DAOCriteria criteria = searchForm.buildCriteria();

		// ���m�点�̌���
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

		// ���m�点�����擾����ׂ̎�L�[��ΏۂƂ������������𐶐�����B
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("newsId", newsId);

		// ���J�Ώۋ敪 = �u�����܂ޑS����v
		criteria.addWhereClause("delFlg", "1");
		
		// ���m�点�����擾
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
