package jp.co.transcosmos.dm3.core.model;

import java.util.List;

import jp.co.transcosmos.dm3.core.model.exception.NotFoundException;
import jp.co.transcosmos.dm3.core.model.news.form.NewsForm;
import jp.co.transcosmos.dm3.core.model.news.form.NewsSearchForm;
import jp.co.transcosmos.dm3.core.util.ValueObjectFactory;
import jp.co.transcosmos.dm3.core.vo.News;
import jp.co.transcosmos.dm3.core.vo.NewsTarget;
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
 * I.Shu		2015.02.05	�V�K�쐬
 * H.Mizuno		2015.06.17	���m�点���擾���̏����������I�ɃI�[�o�[���C�h�o����悤�Ƀ��t�@�N�^�����O
 * 
 * ���ӎ���
 * ���̃N���X�̓V���O���g���� DI �R���e�i�ɒ�`�����̂ŁA�X���b�h�Z�[�t�ł��鎖�B
 * 
 * </pre>
 */
public class NewsManageImpl implements NewsManage {

	private static final Log log = LogFactory.getLog(NewsManageImpl.class);

	/** VO �̃C���X�^���X�𐶐�����ꍇ�̃t�@�N�g���[ */
	protected ValueObjectFactory valueObjectFactory;
	
	/** ���m�点���擾�p DAO */
	/*protected DAO<JoinResult> informationListDAO;*/
	protected DAO<JoinResult> newsListDAO;
	
	protected DAO<News> newsDAO;
	
	protected DAO<News> newsTargetDAO;
	
	

	public DAO<News> getNewsTargetDAO() {
		return newsTargetDAO;
	}

	public void setNewsTargetDAO(DAO<News> newsTargetDAO) {
		this.newsTargetDAO = newsTargetDAO;
	}

	/** ���m�点���e�[�u���̕ʖ� */
	public static final String IMFORMATION_ALIA = "news";

	/** ���m�点���J���񃍁[���e�[�u���̕ʖ� */
	public static final String IMFORMATION_TARGET_ALIA = "newsTarget";
	
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
	public String addInformation(NewsForm inputForm, String editUserId){

    	// �V�K�o�^�����̏ꍇ�A���̓t�H�[���̒l��ݒ肷��o���[�I�u�W�F�N�g�𐶐�����B
		// �o���[�I�u�W�F�N�g�́A�t�@�N�g���[���\�b�h�ȊO�ł͐������Ȃ����B
		// �i�p�����ꂽ�o���[�I�u�W�F�N�g���g�p����Ȃ��Ȃ�ׁB�j
		News news = (News) this.valueObjectFactory.getValueObject("News");
		NewsTarget[] newsTargets = new NewsTarget[] {(NewsTarget) this.valueObjectFactory.getValueObject("NewsTarget")};


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
	 * ���m�点�����������A���ʃ��X�g�𕜋A����B�i�Ǘ���ʗp�j<br/>
	 * �����œn���ꂽ Form �p�����[�^�̒l�Ō��������𐶐����A���m�点������������B<br/>
	 * �������ʂ� Form �I�u�W�F�N�g�Ɋi�[����A�擾�����Y��������߂�l�Ƃ��ĕ��A����B<br/>
	 * <br/>
	 * @param searchForm ���������A����сA�������ʂ̊i�[�I�u�W�F�N�g
	 * @return �Y������
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
	 * ���N�G�X�g�p�����[�^�œn���ꂽ�u���m�点�ԍ��v �i��L�[�l�j�ɊY�����邨�m�点���𕜋A����B�i�T�C�g TOP �p�j<br/>
	 * ���J�Ώۋ敪 = �u�����܂ޑS����v���擾�ΏۂɂȂ�B<br/>
	 * �܂��A�V�X�e�����t�����J���Ԓ��ł��邨�m�点��񂪎擾�ΏۂƂȂ�B<br/>
	 * �����Y���f�[�^��������Ȃ��ꍇ�Anull �𕜋A����B<br/>
	 * <br/>
	 * @param informationNo �擾�ΏۂƂȂ邨�m�点�ԍ�
	 * @return�@���m�点���
	 */
	@Override
	public News searchTopNewsPk(String newsId) {

		// ���m�点�����擾����ׂ̎�L�[��ΏۂƂ������������𐶐�����B
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("newsId", newsId);

		// ���J�Ώۋ敪 = �u�����܂ޑS����v
		criteria.addWhereClause("delFlg", "0");
		
		// ���m�点�����擾
		List<News> newsList = this.newsDAO.selectByFilter(criteria);

		if (newsList == null || newsList.size() == 0) {
			return null;
		}

		return newsList.get(0);
	}

	
	@Override
	public void delNews(NewsForm inputForm) {
		// ���m�点���̍X�V
		this.newsDAO.deleteByFilter(inputForm.buildPkCriteria());
	}

	@Override
	public void updateInformation(NewsForm inputForm, String editUserId) throws Exception, NotFoundException {
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("newsId", inputForm.getNewsId());

		List<News> informations = this.newsDAO.selectByFilter(criteria);

        // �Y������f�[�^�����݂��Ȃ��ꍇ�́A��O���X���[����B
		if (informations == null || informations.size() == 0) {
			throw new NotFoundException();
		}    	

        // ���m�点�����擾���A���͂����l�ŏ㏑������B
		News information = (News) informations.get(0);

    	inputForm.copyToNews(information, editUserId);

		// ���m�点���̍X�V
		this.newsDAO.update(new News[]{information});
		
	}

}
