package jp.co.transcosmos.dm3.corePana.model.recentlyInfo;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import jp.co.transcosmos.dm3.core.util.ValueObjectFactory;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonConstant;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonParameters;
import jp.co.transcosmos.dm3.corePana.model.RecentlyInfoManage;
import jp.co.transcosmos.dm3.corePana.model.housing.PanaHousing;
import jp.co.transcosmos.dm3.corePana.model.housing.PanaHousingPartThumbnailProxy;
import jp.co.transcosmos.dm3.corePana.model.recentlyInfo.form.RecentlyInfoForm;
import jp.co.transcosmos.dm3.corePana.vo.HousingInfo;
import jp.co.transcosmos.dm3.corePana.vo.HousingStatusInfo;
import jp.co.transcosmos.dm3.corePana.vo.RecentlyInfo;
import jp.co.transcosmos.dm3.dao.DAO;
import jp.co.transcosmos.dm3.dao.DAOCriteria;
import jp.co.transcosmos.dm3.dao.JoinResult;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;

import org.springframework.util.StringUtils;

/**
 * �ŋߌ������������Ǘ����� Model �N���X.
 * <p>
 * �ŋߌ����������𑀍삷�� model �N���X�B<br/>
 * <p>
 * <pre>
 * �S����       �C����      �C�����e
 * ------------ ----------- -----------------------------------------------------
 * gao.long		2015.04.27	�V�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 * ���̃N���X�̓V���O���g���� DI �R���e�i�ɒ�`�����̂ŁA�X���b�h�Z�[�t�ł��鎖�B<br/>
 *
 */
public class RecentlyInfoManageImpl implements RecentlyInfoManage {

	/** �ŋߌ����������擾�p DAO */
	private DAO<JoinResult> recentlyInfoHousingListDAO;

	/** �ŋߌ������� **/
	private DAO<RecentlyInfo> recentlyInfoDAO;

	/** ������{��� **/
	private DAO<HousingInfo> housingInfoDAO;

	/** �����X�e�[�^�X���pDAO */
	private DAO<HousingStatusInfo> housingStatusInfoDAO;

	/** �������p Model �I�u�W�F�N�g */
	private PanaHousingPartThumbnailProxy panaHousingManage;

	/** VO �̃C���X�^���X�𐶐�����ꍇ�̃t�@�N�g���[ */
	private ValueObjectFactory valueObjectFactory;

	/** ���ʃp�����[�^�I�u�W�F�N�g */
	private PanaCommonParameters commonParameters;

	/**
	 * �ŋߌ����������擾�p DAO ��ݒ肷��B<br/>
	 * <br/>
	 * @param recentlyInfoHousingListDAO �ŋߌ����������擾�p DAO
	 */
	public void setRecentlyInfoHousingListDAO(DAO<JoinResult> recentlyInfoHousingListDAO) {
		this.recentlyInfoHousingListDAO = recentlyInfoHousingListDAO;
	}

	/**
	 * �ŋߌ��������p��� DAO ��ݒ肷��B<br/>
	 * <br/>
	 * @param recentlyInfoDAO �ŋߌ��������p��� DAO
	 */
	public void setRecentlyInfoDAO(DAO<RecentlyInfo> recentlyInfoDAO) {
		this.recentlyInfoDAO = recentlyInfoDAO;
	}

	/**
	 * ������{���p�@DAO��ݒ肷��B<br/>
	 * <br/>
	 * @param housingInfoDAO ������{���p�@DAO
	 */
	public void setHousingInfoDAO(DAO<HousingInfo> housingInfoDAO) {
		this.housingInfoDAO = housingInfoDAO;
	}

	/**
	 * �����X�e�[�^�X���pDAO ��ݒ肷��B<br/>
	 * <br/>
	 * @param housingStatusInfoDAO �����X�e�[�^�X���pDAO
	 */
	public void setHousingStatusInfoDAO(DAO<HousingStatusInfo> housingStatusInfoDAO) {
		this.housingStatusInfoDAO = housingStatusInfoDAO;
	}

	/**
	 * �������p Model �I�u�W�F�N�g��ݒ肷��B<br/>
	 * <br/>
	 * @param PanaHousingManage �������p Model �I�u�W�F�N�g
	 */
	public void setPanaHousingManage(PanaHousingPartThumbnailProxy panaHousingManage) {
		this.panaHousingManage = panaHousingManage;
	}

	/**
	 * �o���[�I�u�W�F�N�g�̃C���X�^���X�𐶐�����t�@�N�g���[��ݒ肷��B<br/>
	 * <br/>
	 * @param valueObjectFactory �o���[�I�u�W�F�N�g�̃t�@�N�g���[
	 */
	public void setValueObjectFactory(ValueObjectFactory valueObjectFactory) {
		this.valueObjectFactory = valueObjectFactory;
	}

	/**
	 * ���ʃp�����[�^�I�u�W�F�N�g��ݒ肷��B<br/>
	 * <br/>
	 * @param commonParameters ���ʃp�����[�^�I�u�W�F�N�g
	 */
	public void setCommonParameters(PanaCommonParameters commonParameters) {
		this.commonParameters = commonParameters;
	}

	/**
	 * �p�����[�^�œn���ꂽ Form �̏��ōŋߌ�����������V�K�ǉ�����B<br/>
	 * �o���f�[�V�������̃`�F�b�N�����͌Ăяo�����ŗ\�ߎ��{���Ă������B<br/>
	 * <br/>
	 * @param paramMap �ŋߌ����������̃V�X�e������CD�A���[�U�[ID���i�[���� Map �I�u�W�F�N�g
	 * @param editUserId ���O�C�����[�U�[ID �i�X�V���p�j
	 * @return 0�ˍX�V�����A�܂��͍ő匏���ɒB�����A
	 *         1�˒ǉ�����
	 *
	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	public int addRecentlyInfo(Map<String, Object> paramMap, String editUserId) throws Exception {

		int ret = 0;

		DAOCriteria criteria;

		// �ŋߌ����������擾����B
		criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", (String) paramMap.get("sysHousingCd"));
		criteria.addWhereClause("userId", (String) paramMap.get("userId"));
		List<RecentlyInfo> recentlyInfo = this.recentlyInfoDAO.selectByFilter(criteria);

		// �擾�����ꍇ
		if (recentlyInfo != null && recentlyInfo.size() > 0) {

			// �ŋߌ�������
			RecentlyInfo recentlyInfoUpdate = recentlyInfo.get(0);

			// �ŋߌ����������X�V����B
			recentlyInfoUpdate.setUpdDate(new Date());
			recentlyInfoUpdate.setUpdUserId(editUserId);
			this.recentlyInfoDAO.update(new RecentlyInfo[] { recentlyInfoUpdate });

			// �X�V�����ꍇ
			ret = 0;

		} else {

			// �ŋߌ��������̓o�^�������擾����B
			int cnt = getRecentlyInfoCnt((String) paramMap.get("userId"));

			// �擾�����������w�肵�������𒴂���ꍇ
			if (cnt >= commonParameters.getMaxRecentlyInfoCnt()) {

				// �ŌÂ��ŋߌ����������擾����B
				criteria = new DAOCriteria();
				criteria.addWhereClause("userId", (String) paramMap.get("userId"));
				criteria.addOrderByClause("updDate");
				List<RecentlyInfo> recentlyInfoByUserIdList = this.recentlyInfoDAO.selectByFilter(criteria);

				if (recentlyInfoByUserIdList != null & recentlyInfoByUserIdList.size() > 0) {

					// �ŌÂ��ŋߌ����������폜����B
					RecentlyInfo recentlyInfoDelete = recentlyInfoByUserIdList.get(0);
					this.recentlyInfoDAO.delete(new RecentlyInfo[] { recentlyInfoDelete });

					// �폜�����ꍇ
					ret--;
				}
			}

			// �ŋߌ���������o�^����B
			RecentlyInfo recentlyInfoInsert = (RecentlyInfo) this.valueObjectFactory.getValueObject("RecentlyInfo");
			recentlyInfoInsert.setSysHousingCd((String) paramMap.get("sysHousingCd"));
			recentlyInfoInsert.setUserId((String) paramMap.get("userId"));
			recentlyInfoInsert.setInsDate(new Date());
			recentlyInfoInsert.setInsUserId(editUserId);
			recentlyInfoInsert.setUpdDate(new Date());
			recentlyInfoInsert.setUpdUserId(editUserId);
			this.recentlyInfoDAO.insert(new RecentlyInfo[] { recentlyInfoInsert });

			// �o�^�����ꍇ
			ret++;
		}

		return ret;
	}

	/**
	 * �ŋߌ������������������A���ʃ��X�g�𕜋A����B<br/>
	 * �����œn���ꂽ Form �p�����[�^�̒l�Ō��������𐶐����A�ŋߌ�������������������B<br/>
	 * �������ʂ� Form �I�u�W�F�N�g�Ɋi�[����A�擾�����Y��������߂�l�Ƃ��ĕ��A����B<br/>
	 * <br/>
	 * @param searchForm ���������A����сA�������ʂ̊i�[�I�u�W�F�N�g
	 *
	 * @return �Y������
	 *
	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	public List<Map<String, PanaHousing>> searchRecentlyInfo(RecentlyInfoForm searchForm) throws Exception {
		return null;
	}

	/**
	 * �ŋߌ������������������A���ʃ��X�g�𕜋A����B<br/>
	 * �����œn���ꂽ Form �p�����[�^�̒l�Ō��������𐶐����A�ŋߌ�������������������B<br/>
	 * �������ʂ� Form �I�u�W�F�N�g�Ɋi�[����A�擾�����Y��������߂�l�Ƃ��ĕ��A����B<br/>
	 * <br/>
	 * @param userId ���[�U�[ID
	 *
	 * @return ���ʃ��X�g
	 *
	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	public List<PanaHousing> searchRecentlyInfo(String userId) throws Exception {

		// �ŋߌ����������擾����B
		Map<String, RecentlyInfo> recentlyInfo = getRecentlyInfoMap(userId, true);

		// �V�X�e������CD
		String sysHousingCd = null;

		// �������
		PanaHousing housing = null;

		// �ŋߌ���������񌋉ʃ��X�g
		List<PanaHousing> recentlyInfoList = new ArrayList<PanaHousing>();

		// �ŋߌ����������J��Ԃ��A���������擾����B
		for (String key : recentlyInfo.keySet()) {

			if (!StringUtils.isEmpty(key) && key.split(":").length == 2) {

				sysHousingCd = key.split(":")[0];

				// ���������擾����B
				housing = (PanaHousing) this.panaHousingManage.searchHousingPk(sysHousingCd);

				recentlyInfoList.add(housing);
			}
		}
		return recentlyInfoList;
	}

	/**
	 * ���[�U�[ID���ŋߌ��������擾
	 * ��isCheck��false�̏ꍇ�́A��W�I�����������i������񂪖����ꍇ�A����сA����J�ɐݒ肳��Ă���ꍇ�j���܂܂Ȃ�<br/>
	 *
	 * @param userId ���[�U�[ID
	 * @param isCheck �`�F�b�N�t���O
	 *
	 * @return �ŋߌ�������
	 * @throws Exception
	 */
	public Map<String, RecentlyInfo> getRecentlyInfoMap(String userId, boolean isCheck)
			throws Exception {
		Map<String, RecentlyInfo> recentlyInfoMap = new LinkedHashMap<String, RecentlyInfo>();

		if (null != userId) {
			DAOCriteria paramDAOCriteria = new DAOCriteria();
			paramDAOCriteria.addWhereClause("userId", userId);
			paramDAOCriteria.addOrderByClause("updDate", false);
			List<RecentlyInfo> recentlyInfoList = recentlyInfoDAO
					.selectByFilter(paramDAOCriteria);

			if (null != recentlyInfoList && recentlyInfoList.size() > 0) {
				String sysHousingCd;// �V�X�e������CD
				RecentlyInfo recentlyInfo;

				for (int i = 0; i < recentlyInfoList.size(); i++) {
					recentlyInfo = recentlyInfoList.get(i);

					if (null != recentlyInfo) {
						// �V�X�e������CD�擾
						sysHousingCd = recentlyInfo.getSysHousingCd();

						if (isCheck) {
							if (!StringValidateUtil.isEmpty(sysHousingCd)) {
								HousingInfo housingInfo = housingInfoDAO
										.selectByPK(sysHousingCd);

								if (null != housingInfo
										&& !StringValidateUtil.isEmpty(housingInfo
												.getSysHousingCd())) {// ������񂪂��邩�ǂ������f
									HousingStatusInfo housingStatusInfo = housingStatusInfoDAO
											.selectByPK(sysHousingCd);

									if (null != housingStatusInfo
											&& !PanaCommonConstant.HIDDEN_FLG_PRIVATE
													.equals(housingStatusInfo
															.getHiddenFlg())) {// ����J���ǂ������f
										recentlyInfoMap.put(sysHousingCd + ":"
												+ userId, recentlyInfo);
									}
								}
							}
						} else {
							recentlyInfoMap.put(sysHousingCd + ":" + userId,
									recentlyInfo);
						}
					}
				}
			}

		}

		return recentlyInfoMap;
	}

	/**
	 * ���[�U�[ID���ŋߌ��������e�[�u�����猏���擾
	 *
	 * @param userId
	 *            ���[�U�[ID
	 * @return �ŋߌ�����������
	 * @throws Exception
	 */
	public int getRecentlyInfoCnt(String userId) throws Exception {

		return this.getRecentlyInfoMap(userId, false).size();
	}

	/**
	 * �ŋߌ��������̓o�^�������������A�o�^�����𕜋A����B<br/>
	 * �����œn���ꂽ Map �p�����[�^�̒l�Ō��������𐶐����A�ŋߌ��������̓o�^��������������B<br/>
	 * �擾�����Y��������߂�l�Ƃ��ĕ��A����B<br/>
	 * <br/>
	 * @param paramMap ���������̊i�[�I�u�W�F�N�g
	 *
	 * @return �o�^����
	 *
	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	public int searchRecentlyInfo(Map<String, Object> paramMap) throws Exception {
		return 0;
	}

	/**
	 * ���[�U�[ID���ŋߌ����������擾<br/>
	 * ����W�I�����������i������񂪖����ꍇ�A����сA����J�ɐݒ肳��Ă���ꍇ�j���܂�<br/>
	 *
	 * @param userId
	 *            ���[�U�[ID
	 * @return �ŋߌ����������
	 * @throws Exception
	 */
	public List<PanaHousing> getRecentlyInfoListMap(String userId, String orderBy, boolean ascending) throws Exception {

		DAOCriteria paramDAOCriteria = new DAOCriteria();
		paramDAOCriteria.addWhereClause("recentlyInfo", "userId", userId, DAOCriteria.EQUALS, false);
		paramDAOCriteria.addWhereClause("hiddenFlg", PanaCommonConstant.HIDDEN_FLG_PUBLIC);
		switch (orderBy) {
		case "1":
			// ���בւ��F�ŏI�X�V��
			paramDAOCriteria.addOrderByClause("recentlyInfo", "updDate", ascending);
			break;
		case "2":
			// ���בւ��F�������i
			paramDAOCriteria.addOrderByClause("housingInfo", "price", ascending);
			break;
		case "3":
			// ���בւ��F�z�N��
			paramDAOCriteria.addOrderByClause("buildingInfo", "compDate", ascending);
			break;
		case "4":
			// ���בւ��F�w����̋���
			paramDAOCriteria.addOrderByClause("housingInfo", "minWalkingTime", ascending);
			break;
		}

		List<JoinResult> recentlyInfoList = this.recentlyInfoHousingListDAO.selectByFilter(paramDAOCriteria);

		// �������
		PanaHousing housing = null;

		// �ŋߌ���������񌋉ʃ��X�g
		List<PanaHousing> recentlyInfoHousingList = new ArrayList<PanaHousing>();

		if (null != recentlyInfoList && recentlyInfoList.size() > 0) {
			// �V�X�e������CD
			String sysHousingCd;
			RecentlyInfo recentlyInfo;

			for (int i = 0; i < recentlyInfoList.size(); i++) {
				recentlyInfo = (RecentlyInfo) recentlyInfoList.get(i).getItems().get("recentlyInfo");

				if (null != recentlyInfo) {
					// �V�X�e������CD�擾
					sysHousingCd = recentlyInfo.getSysHousingCd();

					// ���������擾����B
					housing = (PanaHousing) this.panaHousingManage.searchHousingPk(sysHousingCd);

					recentlyInfoHousingList.add(housing);
				}
			}
		}

		return recentlyInfoHousingList;
	}

	/**
	 * �����œn���ꂽ�l�ōŋߌ������������폜����B<br/>
	 * �o���f�[�V�������̃`�F�b�N�����͌Ăяo�����ŗ\�ߎ��{���Ă������B<br/>
	 * �w�肳�ꂽ�ŋߌ���������񂪑��݂��Ȃ��ꍇ�́A���̂܂ܐ���I���Ƃ��Ĉ����B<br/>
	 * <br/>
	 *
	 * @param userId ���[�U�[ID
	 *
	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	public void delRecentlyInfo(String userId) throws Exception {

		DAOCriteria paramDAOCriteria = new DAOCriteria();
		paramDAOCriteria.addWhereClause("userId", userId);

		List<RecentlyInfo> recentlyInfoList = this.recentlyInfoDAO.selectByFilter(paramDAOCriteria);

		//��W�I�������������X�g
		List<RecentlyInfo> listPrivate = new ArrayList<>();

		if (null != recentlyInfoList && recentlyInfoList.size() > 0) {
			String sysHousingCd;// �V�X�e������CD
			RecentlyInfo recentlyInfo;

			for (int i = 0; i < recentlyInfoList.size(); i++) {
				recentlyInfo = (RecentlyInfo) recentlyInfoList.get(i);

				if (null != recentlyInfo) {
					// �V�X�e������CD�擾
					sysHousingCd = recentlyInfo.getSysHousingCd();

					if (!StringValidateUtil.isEmpty(sysHousingCd)) {
						HousingInfo housingInfo = housingInfoDAO.selectByPK(sysHousingCd);

						if (null != housingInfo && !StringValidateUtil.isEmpty(housingInfo.getSysHousingCd())) {
							// ������񂪂��邩�ǂ������f
							HousingStatusInfo housingStatusInfo = housingStatusInfoDAO.selectByPK(sysHousingCd);

							if (housingStatusInfo == null) {
								listPrivate.add(recentlyInfo);
							} else if (null != housingStatusInfo && PanaCommonConstant.HIDDEN_FLG_PRIVATE.equals(housingStatusInfo.getHiddenFlg())) {
								// ����J���ǂ������f
								listPrivate.add(recentlyInfo);
							}
						} else {
							listPrivate.add(recentlyInfo);
						}
					}
				}
			}
		}

		for (RecentlyInfo f : listPrivate) {
			DAOCriteria criteria = new DAOCriteria();
			criteria.addWhereClause("sysHousingCd", f.getSysHousingCd());
			criteria.addWhereClause("userId", f.getUserId());
			this.recentlyInfoDAO.deleteByFilter(criteria);
		}
	}
}
