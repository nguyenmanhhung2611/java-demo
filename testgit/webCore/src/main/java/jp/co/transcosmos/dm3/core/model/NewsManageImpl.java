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
 * お知らせメンテナンス用 Model クラス
 * 
 * 担当者       修正日      修正内容
 * ------------ ----------- -----------------------------------------------------
 * I.Shu		2015.02.05	新規作成
 * H.Mizuno		2015.06.17	お知らせ情報取得時の条件を効率的にオーバーライド出来るようにリファクタリング
 * 
 * 注意事項
 * このクラスはシングルトンで DI コンテナに定義されるので、スレッドセーフである事。
 * 
 * </pre>
 */
public class NewsManageImpl implements NewsManage {

	private static final Log log = LogFactory.getLog(NewsManageImpl.class);

	/** VO のインスタンスを生成する場合のファクトリー */
	protected ValueObjectFactory valueObjectFactory;
	
	/** お知らせ情報取得用 DAO */
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

	/** お知らせ情報テーブルの別名 */
	public static final String IMFORMATION_ALIA = "news";

	/** お知らせ公開先情報ロールテーブルの別名 */
	public static final String IMFORMATION_TARGET_ALIA = "newsTarget";
	
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
	public String addInformation(NewsForm inputForm, String editUserId){

    	// 新規登録処理の場合、入力フォームの値を設定するバリーオブジェクトを生成する。
		// バリーオブジェクトは、ファクトリーメソッド以外では生成しない事。
		// （継承されたバリーオブジェクトが使用されなくなる為。）
		News news = (News) this.valueObjectFactory.getValueObject("News");
		NewsTarget[] newsTargets = new NewsTarget[] {(NewsTarget) this.valueObjectFactory.getValueObject("NewsTarget")};


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
	 * お知らせ情報を検索し、結果リストを復帰する。（管理画面用）<br/>
	 * 引数で渡された Form パラメータの値で検索条件を生成し、お知らせ情報を検索する。<br/>
	 * 検索結果は Form オブジェクトに格納され、取得した該当件数を戻り値として復帰する。<br/>
	 * <br/>
	 * @param searchForm 検索条件、および、検索結果の格納オブジェクト
	 * @return 該当件数
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
	 * リクエストパラメータで渡された「お知らせ番号」 （主キー値）に該当するお知らせ情報を復帰する。（サイト TOP 用）<br/>
	 * 公開対象区分 = 「仮を含む全会員」が取得対象になる。<br/>
	 * また、システム日付が公開期間中であるお知らせ情報が取得対象となる。<br/>
	 * もし該当データが見つからない場合、null を復帰する。<br/>
	 * <br/>
	 * @param informationNo 取得対象となるお知らせ番号
	 * @return　お知らせ情報
	 */
	@Override
	public News searchTopNewsPk(String newsId) {

		// お知らせ情報を取得する為の主キーを対象とした検索条件を生成する。
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("newsId", newsId);

		// 公開対象区分 = 「仮を含む全会員」
		criteria.addWhereClause("delFlg", "0");
		
		// お知らせ情報を取得
		List<News> newsList = this.newsDAO.selectByFilter(criteria);

		if (newsList == null || newsList.size() == 0) {
			return null;
		}

		return newsList.get(0);
	}

	
	@Override
	public void delNews(NewsForm inputForm) {
		// お知らせ情報の更新
		this.newsDAO.deleteByFilter(inputForm.buildPkCriteria());
	}

	@Override
	public void updateInformation(NewsForm inputForm, String editUserId) throws Exception, NotFoundException {
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("newsId", inputForm.getNewsId());

		List<News> informations = this.newsDAO.selectByFilter(criteria);

        // 該当するデータが存在しない場合は、例外をスローする。
		if (informations == null || informations.size() == 0) {
			throw new NotFoundException();
		}    	

        // お知らせ情報を取得し、入力した値で上書きする。
		News information = (News) informations.get(0);

    	inputForm.copyToNews(information, editUserId);

		// お知らせ情報の更新
		this.newsDAO.update(new News[]{information});
		
	}

}
