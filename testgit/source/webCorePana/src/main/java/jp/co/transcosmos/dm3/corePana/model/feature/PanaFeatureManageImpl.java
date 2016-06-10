package jp.co.transcosmos.dm3.corePana.model.feature;

import jp.co.transcosmos.dm3.core.model.HousingManage;
import jp.co.transcosmos.dm3.core.model.feature.FeatureManageImpl;
import jp.co.transcosmos.dm3.core.model.feature.form.FeatureSearchForm;
import jp.co.transcosmos.dm3.core.model.housing.form.HousingSearchForm;
import jp.co.transcosmos.dm3.core.model.information.InformationManageImpl;
import jp.co.transcosmos.dm3.core.vo.FeaturePageInfo;
import jp.co.transcosmos.dm3.dao.DAO;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 特集情報用 Model クラス.
 * <p>
 * <pre>
 * 担当者         修正日      修正内容
 * -------------- ----------- -----------------------------------------------------
 * 焦	  2015.04.20	新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 * このクラスはシングルトンで DI コンテナに定義されるので、スレッドセーフである事。<br/>
 *
 */
public class PanaFeatureManageImpl extends FeatureManageImpl {

	private static final Log log = LogFactory.getLog(InformationManageImpl.class);

	/** 特集ページ情報 */
	private DAO<FeaturePageInfo> featurePageInfoDAO;

	/** 物件情報Model（物件リクエストに該当する物件情報取得に使用） */
	private HousingManage housingManager;

	public void setFeaturePageInfoDAO(DAO<FeaturePageInfo> featurePageInfoDAO) {
		this.featurePageInfoDAO = featurePageInfoDAO;
	}

	public void setHousingManager(HousingManage housingManager) {
		this.housingManager = housingManager;
	}

	/**
	 * 指定された検索条件（特集ID、およびページ位置）で特集に該当する物件の情報を取得する。<br/>
	 * <br/>
	 * @param searchForm 特集の検索条件（特集ID、ページ位置）
	 *
	 * @return 該当件数
	 *
 	 * @exception Exception 実装クラスによりスローされる任意の例外
	 */
	@Override
	public int searchHousing(FeatureSearchForm searchForm) throws Exception {

		if (searchForm == null) {
			log.warn("searchForm Is Null");
			return 0;
		}

		if (StringValidateUtil.isEmpty(searchForm.getFeaturePageId())) {
			log.warn("featurePageId Is Null or Empty [featurePageId = " + searchForm.getFeaturePageId() + "]");
			return 0;
		}

		FeaturePageInfo featurePageInfo = featurePageInfoDAO.selectByPK(searchForm.getFeaturePageId());

		if (featurePageInfo == null) {
			log.warn("Selected FeaturePageInfo Is Null [featurePageId = " + searchForm.getFeaturePageId() + "]");
			return 0;
		}

		// ソート順を設定する
		featurePageInfo.setQueryStrings(featurePageInfo.getQueryStrings() + "&orderType=" + ((jp.co.transcosmos.dm3.corePana.model.housing.form.PanaFeatureSearchForm)searchForm).getKeyOrderType());

		// 物件検索Formの生成
		HousingSearchForm housingSearchForm = createHousingSearchForm(featurePageInfo);

		// 特集情報検索Formが保持するPagingListFormのプロパティ値を物件情報検索Formにコピーする
		housingSearchForm.setRowsPerPage(searchForm.getRowsPerPage());
		housingSearchForm.setVisibleNavigationPageCount(searchForm.getVisibleNavigationPageCount());
		housingSearchForm.setSelectedPage(searchForm.getSelectedPage());

		// 物件情報検索処理（HousingManageに処理を委譲）
		int cnt = this.housingManager.searchHousing(housingSearchForm);

		// 物件情報検索Formが受け取った物件一覧を物件リクエスト検索Formにコピーする
		searchForm.setRows(housingSearchForm.getRows());

		return cnt;

	}
}
