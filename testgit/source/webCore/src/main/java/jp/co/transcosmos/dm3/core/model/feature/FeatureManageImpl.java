package jp.co.transcosmos.dm3.core.model.feature;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.co.transcosmos.dm3.core.model.FeatureManage;
import jp.co.transcosmos.dm3.core.model.HousingManage;
import jp.co.transcosmos.dm3.core.model.feature.form.FeatureSearchForm;
import jp.co.transcosmos.dm3.core.model.housing.form.HousingFormFactory;
import jp.co.transcosmos.dm3.core.model.housing.form.HousingSearchForm;
import jp.co.transcosmos.dm3.core.model.information.InformationManageImpl;
import jp.co.transcosmos.dm3.core.vo.FeaturePageInfo;
import jp.co.transcosmos.dm3.dao.DAO;
import jp.co.transcosmos.dm3.dao.DAOCriteria;
import jp.co.transcosmos.dm3.dao.JoinResult;
import jp.co.transcosmos.dm3.utils.ReflectionUtils;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * ���W���p Model �N���X.
 * <p>
 * <pre>
 * �S����		�C����		�C�����e
 * ------------ ----------- -----------------------------------------------------
 * T.Nakamura	2015.04.13	�V�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 * ���̃N���X�̓V���O���g���� DI �R���e�i�ɒ�`�����̂ŁA�X���b�h�Z�[�t�ł��鎖�B<br/>
 *
 */
public class FeatureManageImpl implements FeatureManage {

	private static final Log log = LogFactory.getLog(InformationManageImpl.class);

	/** ���W�y�[�W��� */
	protected DAO<FeaturePageInfo> featurePageInfoDAO;
	/** ���W�ꗗ�擾DAO */
	protected DAO<JoinResult> featureListDAO;

	/** �������Model�i�������N�G�X�g�ɊY�����镨�����擾�Ɏg�p�j */
	protected HousingManage housingManager;
	/** �������FormFactory�i�������N�G�X�g�ɊY�����镨�����擾�Ɏg�p�j */
	protected HousingFormFactory housingFormFactory;

	// DI seeter START
	public void setFeaturePageInfoDAO(DAO<FeaturePageInfo> featurePageInfoDAO) {
		this.featurePageInfoDAO = featurePageInfoDAO;
	}

	public void setFeatureListDAO(DAO<JoinResult> featureListDAO) {
		this.featureListDAO = featureListDAO;
	}

	public void setHousingManager(HousingManage housingManager) {
		this.housingManager = housingManager;
	}

	public void setHousingFormFactory(HousingFormFactory housingFormFactory) {
		this.housingFormFactory = housingFormFactory;
	}
	// DI seeter END

	/**
	 * �w�肳�ꂽ�O���[�v�h�c�œ��W���̃��X�g���擾����B<br/>
	 * <br/>
	 * @param featureGroupId ���W�O���[�vID
	 *
	 * @return ���W�y�[�W���̃��X�g�I�u�W�F�N�g
	 *
 	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	@Override
	public List<FeaturePageInfo> searchFeature(String featureGroupId) throws Exception {

		if (StringValidateUtil.isEmpty(featureGroupId)) {
			log.warn("featureGroupId Is Null or Empty [featureGroupId = " + featureGroupId + "]");
			return new ArrayList<FeaturePageInfo>();
		}

		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("featureGroupId", featureGroupId);
		// ���W�y�[�W���̕\���ۂɊւ�������ǉ�
		criteria.addWhereClause("featurePageInfo", "displayFlg", "1", DAOCriteria.EQUALS, false);
		Date now = new Date();
		criteria.addWhereClause("featurePageInfo", "displayStartDate", now, DAOCriteria.LESS_THAN_EQUALS, false);
		criteria.addWhereClause("featurePageInfo", "displayEndDate", now, DAOCriteria.GREATER_THAN_EQUALS, false);
		// ���W�O���[�v�Ή��\�̕\�����Ń\�[�g
		criteria.addOrderByClause("featureGroupPage", "sortOrder", true);
		List<JoinResult> results = featureListDAO.selectByFilter(criteria);

		List<FeaturePageInfo> featurePageInfos = new ArrayList<FeaturePageInfo>();

		for (JoinResult result : results) {
			featurePageInfos.add((FeaturePageInfo) result.getItems().get("featurePageInfo"));
		}

		return featurePageInfos;

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

	/**
	 * ���W�y�[�W���̃N�G���[�����񂩕�������Form�𐶐�����<br/>
	 * <br/>
	 *
	 * @param featurePageInfo �����y�[�W���
	 * @return ��������Form
	 */
	protected HousingSearchForm createHousingSearchForm(FeaturePageInfo featurePageInfo) {

		HousingSearchForm housingSearchForm = housingFormFactory.createHousingSearchForm();

		// DB����擾�������W���̌���������������擾����B
		String queryString = featurePageInfo.getQueryStrings();

		// �p�����[�^���u&�v�ŕ�������B
		String[] params = queryString.split("&");

		// �p�����^�����񂩂猟�������𐶐�
		param : for (String param : params){

			// ���������p�����[�^���A����Ɂu=�v�ŕ������ĕϐ����ƒl���ɕ�������B
			String[] variableAndValue = param.split("=");

			// �u�ϐ����v = �u�l�v�̍\���ɂȂ��Ă���ꍇ�̂݌��������Ƃ��Ďg�p����B
			if (variableAndValue.length == 2){

				// Setter �̃��\�b�h����
				String prefix = "Key";
				String name = prefix + variableAndValue[0].substring(0, 1).toUpperCase() + variableAndValue[0].substring(1);
				String methodName = ReflectionUtils.buildSetterName(name);

				// HousingSearchForm �̃��\�b�h�̂����A�p�����^�����狁�߂� Setter ���\�b�h���̂Ɉ�v������̂���������
				Method[] methods = housingSearchForm.getClass().getMethods();

				for (Method method : methods) {
					if (methodName.equals(method.getName())) {

						// Setter �̈����̃N���X�i ���v���p�e�B�̃N���X�j
						Class<?>[] parameterTypes = method.getParameterTypes();
						// ���߂�̂� Setter �Ȃ̂ň����͂ЂƂ�
						if (parameterTypes == null || parameterTypes.length != 1) {
							continue param;
						}

						// �������擾�A�y�� Setter �̌Ăяo��
						Class<?> parameterType = parameterTypes[0];
						if (parameterType.isPrimitive()) {
							// �������v���~�e�B�u�̃��\�b�h�͏������Ȃ��B
							// ����� HousingSearchForm �̒��ӎ����Ƃ��ċL�ڂ��Ă���B

							log.warn("parameter of method is primitive, is not set to property");
							continue param;

						} else {
							// ���b�p�[�N���X�̏ꍇ
							// String�������Ƃ��ăC���X�^���X�����ł���N���X�̂ݑΉ�����
							// �Œ�ł��AString, BigDecimal, BigInteger, Byte, Double, Float, Integer, Long, Short �ɑΉ��ł���B

							// �����ƂȂ�N���X�̃R���X�g���N�^���擾
							Constructor<?> constructor;
							try {
								constructor = parameterType.getConstructor(String.class);
							} catch (NoSuchMethodException | SecurityException e1) {

								log.warn("matching method is not found, Or can't access method.");
								log.warn("this method fails. [methodName : " + method.getName() + ", parameter : " + variableAndValue[1] + "]");
								e1.printStackTrace();
								continue param;
							}
							// �����̃C���X�^���X���擾
							Object[] args = { variableAndValue[1] };
							Object obj;
							try {
								obj = constructor.newInstance(args);
							} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
									| InvocationTargetException e1) {

								log.warn("initialization provoked by this method fails.");
								log.warn("this method fails. [methodName : " + method.getName() + ", parameter : " + variableAndValue[1] + "]");
								e1.printStackTrace();
								continue param;
							}

							// �v���p�e�B�l�̐ݒ�
							try {
								method.invoke(housingSearchForm, obj);
							} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e1) {

								log.warn("this method fails. [methodName : " + method.getName() + ", parameter : " + variableAndValue[1] + "]");
								e1.printStackTrace();
								continue param;
							}
						}
					}
				}

			} else {
				// �p�����[�^�ɒl���ݒ肳��Ă��Ȃ��ꍇ�A�x�����o�͂���B�i���������Ƃ��Ă͖����j

				log.warn("feature page parameter " + variableAndValue[0] + " dose not have value.");
			}
		}

		// �f�o�b�O�p���O�o�� �����p�t�H�[���̑S�Ă� getter �̒l���o�͂���B
		if (log.isDebugEnabled()) {

			String getterPrefix = "getKey";

			Method[] methods = housingSearchForm.getClass().getMethods();
			for (Method method : methods) {
				if (method.getName().startsWith(getterPrefix)) {
					// String �ȊO�̖߂�l�͖�������B
					Object ret;
					try {
						ret = method.invoke(housingSearchForm, (Object[]) null);
						if (ret instanceof String) {
							log.debug("form parameter " + method.getName() + "=" + ret);
						}
					} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
						log.debug("this method fails. [methodName : " + method.getName() + "]");
						e.printStackTrace();
						continue;
					}
				}
			}
		}

		return housingSearchForm;

	}

}
