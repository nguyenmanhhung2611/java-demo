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
 * ���W���p Model �N���X.
 * <p>
 * <pre>
 * �S����         �C����      �C�����e
 * -------------- ----------- -----------------------------------------------------
 * ��	  2015.04.20	�V�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 * ���̃N���X�̓V���O���g���� DI �R���e�i�ɒ�`�����̂ŁA�X���b�h�Z�[�t�ł��鎖�B<br/>
 *
 */
public class PanaFeatureManageImpl extends FeatureManageImpl {

	private static final Log log = LogFactory.getLog(InformationManageImpl.class);

	/** ���W�y�[�W��� */
	private DAO<FeaturePageInfo> featurePageInfoDAO;

	/** �������Model�i�������N�G�X�g�ɊY�����镨�����擾�Ɏg�p�j */
	private HousingManage housingManager;

	public void setFeaturePageInfoDAO(DAO<FeaturePageInfo> featurePageInfoDAO) {
		this.featurePageInfoDAO = featurePageInfoDAO;
	}

	public void setHousingManager(HousingManage housingManager) {
		this.housingManager = housingManager;
	}

	/**
	 * �w�肳�ꂽ���������i���WID�A����уy�[�W�ʒu�j�œ��W�ɊY�����镨���̏����擾����B<br/>
	 * <br/>
	 * @param searchForm ���W�̌��������i���WID�A�y�[�W�ʒu�j
	 *
	 * @return �Y������
	 *
 	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
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

		// �\�[�g����ݒ肷��
		featurePageInfo.setQueryStrings(featurePageInfo.getQueryStrings() + "&orderType=" + ((jp.co.transcosmos.dm3.corePana.model.housing.form.PanaFeatureSearchForm)searchForm).getKeyOrderType());

		// ��������Form�̐���
		HousingSearchForm housingSearchForm = createHousingSearchForm(featurePageInfo);

		// ���W��񌟍�Form���ێ�����PagingListForm�̃v���p�e�B�l�𕨌���񌟍�Form�ɃR�s�[����
		housingSearchForm.setRowsPerPage(searchForm.getRowsPerPage());
		housingSearchForm.setVisibleNavigationPageCount(searchForm.getVisibleNavigationPageCount());
		housingSearchForm.setSelectedPage(searchForm.getSelectedPage());

		// ������񌟍������iHousingManage�ɏ������Ϗ��j
		int cnt = this.housingManager.searchHousing(housingSearchForm);

		// ������񌟍�Form���󂯎���������ꗗ�𕨌����N�G�X�g����Form�ɃR�s�[����
		searchForm.setRows(housingSearchForm.getRows());

		return cnt;

	}
}
