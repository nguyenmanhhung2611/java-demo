package jp.co.transcosmos.dm3.corePana.model.favorite;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import jp.co.transcosmos.dm3.core.model.favorite.FavoriteManageImpl;
import jp.co.transcosmos.dm3.core.model.favorite.form.FavoriteSearchForm;
import jp.co.transcosmos.dm3.core.model.information.InformationManageImpl;
import jp.co.transcosmos.dm3.core.util.ValueObjectFactory;
import jp.co.transcosmos.dm3.core.vo.FavoriteInfo;
import jp.co.transcosmos.dm3.core.vo.HousingImageInfo;
import jp.co.transcosmos.dm3.core.vo.HousingInfo;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonConstant;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonParameters;
import jp.co.transcosmos.dm3.corePana.model.housing.PanaHousing;
import jp.co.transcosmos.dm3.corePana.model.housing.PanaHousingPartThumbnailProxy;
import jp.co.transcosmos.dm3.corePana.vo.HousingStatusInfo;
import jp.co.transcosmos.dm3.dao.DAO;
import jp.co.transcosmos.dm3.dao.DAOCriteria;
import jp.co.transcosmos.dm3.dao.JoinResult;
import jp.co.transcosmos.dm3.dao.NotEnoughRowsException;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

/**
 * ���C�ɓ�����p Model �N���X.
 * <p>
 * <pre>
 * �S����         �C����      �C�����e
 * -------------- ----------- -----------------------------------------------------
 * tang.tianyun	  2015.04.20	�V�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 * ���̃N���X�̓V���O���g���� DI �R���e�i�ɒ�`�����̂ŁA�X���b�h�Z�[�t�ł��鎖�B<br/>
 *
 */
public class PanaFavoriteManageImpl extends FavoriteManageImpl {

	private static final Log log = LogFactory.getLog(InformationManageImpl.class);

	/** ���C�ɓ�����X�V�p DAO */
	private DAO<FavoriteInfo> favoriteInfoDAO;

	/** ������{��� **/
	private DAO<HousingInfo> housingInfoDAO;

	/** �����X�e�[�^�X��� **/
	private DAO<HousingStatusInfo> housingStatusInfoDAO;

	/** VO �̃C���X�^���X�𐶐�����ꍇ�̃t�@�N�g���[ */
	private ValueObjectFactory valueObjectFactory;

	/** ���ʃp�����[�^�I�u�W�F�N�g */
	private PanaCommonParameters commonParameters;

	/** �������p Model �I�u�W�F�N�g */
	private PanaHousingPartThumbnailProxy panaHousingManage;

	/** ���C�ɓ��蕨���ꗗ���ɊY�����镨����{���̎擾�p */
	private DAO<JoinResult> favoritePublicHousingListDAO;

	/** �����摜���DAO */
	private DAO<HousingImageInfo> housingImageInfoDAO;

	/**
	 * ���C�ɓ�����X�V�p  DAO ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param ���C�ɓ�����X�V�p DAO
	 */
	public void setFavoriteInfoDAO(DAO<FavoriteInfo> favoriteInfoDAO) {
		this.favoriteInfoDAO = favoriteInfoDAO;
	}

	/**
	 * ������{��񌟍��p DAO ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param ������{��񌟍��p DAO
	 */
	public void setHousingInfoDAO(DAO<HousingInfo> housingInfoDAO) {
		this.housingInfoDAO = housingInfoDAO;
	}

	/**
	 * �����X�e�[�^�X��񌟍��p DAO ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param �����X�e�[�^�X��񌟍��p DAO
	 */
	public void setHousingStatusInfoDAO(DAO<HousingStatusInfo> housingStatusInfoDAO) {
		this.housingStatusInfoDAO = housingStatusInfoDAO;
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
	 * �������p Model �I�u�W�F�N�g��ݒ肷��B<br/>
	 * <br/>
	 * @param PanaHousingManage �������p Model �I�u�W�F�N�g
	 */
	public void setPanaHousingManage(PanaHousingPartThumbnailProxy panaHousingManage) {
		this.panaHousingManage = panaHousingManage;
	}

	/**
	 * @param favoritePublicHousingListDAO �Z�b�g���� favoritePublicHousingListDAO
	 */
	public void setFavoritePublicHousingListDAO(
			DAO<JoinResult> favoritePublicHousingListDAO) {
		this.favoritePublicHousingListDAO = favoritePublicHousingListDAO;
	}

	/**
	 * @param housingImageInfoDAO �Z�b�g���� housingImageInfoDAO
	 */
	public void setHousingImageInfoDAO(DAO<HousingImageInfo> housingImageInfoDAO) {
		this.housingImageInfoDAO = housingImageInfoDAO;
	}

	/**
	 * �����œn���ꂽ�l�ł��C�ɓ�������폜����B<br/>
	 * �o���f�[�V�������̃`�F�b�N�����͌Ăяo�����ŗ\�ߎ��{���Ă������B<br/>
	 * �w�肳�ꂽ���C�ɓ����񂪑��݂��Ȃ��ꍇ�́A���̂܂ܐ���I���Ƃ��Ĉ����B<br/>
	 * <br/>
	 *
	 * @param userId ���[�U�[ID
	 *
	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */

	public void delFavorite(String userId) throws Exception {

		// �p�����^�`�F�b�N
		if (StringValidateUtil.isEmpty(userId)) {
			log.warn("userId Is Null or Empty [userId = " + userId + "]");
			return;
		}

		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("userId", userId);
		this.favoriteInfoDAO.deleteByFilter(criteria);

		log.debug("Deleted FavoriteInfo [userId = " + userId + "]");

	}

	/**
	 * �����œn���ꂽ�l�ł��C�ɓ�������폜����B<br/>
	 * �o���f�[�V�������̃`�F�b�N�����͌Ăяo�����ŗ\�ߎ��{���Ă������B<br/>
	 * �w�肳�ꂽ���C�ɓ����񂪑��݂��Ȃ��ꍇ�́A���̂܂ܐ���I���Ƃ��Ĉ����B<br/>
	 * <br/>
	 *
	 * @param userId ���[�U�[ID
	 * @param sysHousingCd �V�X�e������CD
	 *
	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	@Override
	public void delFavorite(String userId, String sysHousingCd) throws Exception {
		super.setFavoriteInfoDAO(this.favoriteInfoDAO);
		super.delFavorite(userId, sysHousingCd);

	}

	/**
	 * ���[�U�[ID��肨�C�ɓ�����擾<br/>
	 * ��isCheck��false�̏ꍇ�́A��W�I�����������i������񂪖����ꍇ�A����сA����J�ɐݒ肳��Ă���ꍇ�j���܂܂Ȃ�<br/>
	 *
	 * @param userId ���[�U�[ID
	 * @param isCheck �`�F�b�N�t���O
	 *
	 * @return ���C�ɓ�����
	 * @throws Exception
	 */
	private Map<String, FavoriteInfo> getFavoriteInfoMap(String userId,
			boolean isCheck) throws Exception {
		Map<String, FavoriteInfo> favoriteInfoMap = new TreeMap<String, FavoriteInfo>();

		if (null != userId) {
			DAOCriteria paramDAOCriteria = new DAOCriteria();
			paramDAOCriteria.addWhereClause("userId", userId);
			paramDAOCriteria.addOrderByClause("insDate", true);
			List<FavoriteInfo> favoriteInfoList = favoriteInfoDAO
					.selectByFilter(paramDAOCriteria);

			if (null != favoriteInfoList && favoriteInfoList.size() > 0) {
				String sysHousingCd;// �V�X�e������CD
				FavoriteInfo favoriteInfo;

				for (int i = 0; i < favoriteInfoList.size(); i++) {
					favoriteInfo = favoriteInfoList.get(i);

					if (null != favoriteInfo) {
						// �V�X�e������CD�擾
						sysHousingCd = favoriteInfo.getSysHousingCd();

						if (isCheck) {
							if (!StringValidateUtil.isEmpty(sysHousingCd)) {
								HousingInfo housingInfo = housingInfoDAO
										.selectByPK(sysHousingCd);

								if (null != housingInfo
										&& !StringValidateUtil
												.isEmpty(housingInfo
														.getSysHousingCd())) {// ������񂪂��邩�ǂ������f
									HousingStatusInfo housingStatusInfo = housingStatusInfoDAO
											.selectByPK(sysHousingCd);

									if (null != housingStatusInfo
											&& !PanaCommonConstant.HIDDEN_FLG_PRIVATE
													.equals(housingStatusInfo
															.getHiddenFlg())) {// ����J���ǂ������f
										favoriteInfoMap.put(sysHousingCd + ":"
												+ userId, favoriteInfo);
									}
								}
							}
						} else {
							favoriteInfoMap.put(sysHousingCd + ":" + userId,
									favoriteInfo);
						}
					}
				}
			}

		}

		return favoriteInfoMap;
	}

	/**
	 * ���[�U�[ID��肨�C�ɓ�����擾<br/>
	 * ����W�I�����������i������񂪖����ꍇ�A����сA����J�ɐݒ肳��Ă���ꍇ�j���܂�<br/>
	 *
	 * @param userId
	 *            ���[�U�[ID
	 * @return ���C�ɓ�����
	 * @throws Exception
	 */
	public Map<String, List<FavoriteInfo>> searchPrivateFavorite(String userId) throws Exception {

		Map<String, List<FavoriteInfo>> map = new HashMap<String, List<FavoriteInfo>>();

		DAOCriteria paramDAOCriteria = new DAOCriteria();
		paramDAOCriteria.addWhereClause("userId", userId);
		paramDAOCriteria.addOrderByClause("insDate", true);

		List<FavoriteInfo> favoriteInfoList = this.favoriteInfoDAO.selectByFilter(paramDAOCriteria);

		//��W�I�������������X�g
		List<FavoriteInfo> listPrivate = new ArrayList<>();

		if (null != favoriteInfoList && favoriteInfoList.size() > 0) {
			String sysHousingCd;// �V�X�e������CD
			FavoriteInfo favoriteInfo;

			for (int i = 0; i < favoriteInfoList.size(); i++) {
				favoriteInfo = (FavoriteInfo) favoriteInfoList.get(i);

				if (null != favoriteInfo) {
					// �V�X�e������CD�擾
					sysHousingCd = favoriteInfo.getSysHousingCd();

					if (!StringValidateUtil.isEmpty(sysHousingCd)) {
						HousingInfo housingInfo = housingInfoDAO
								.selectByPK(sysHousingCd);

						if (null != housingInfo
								&& !StringValidateUtil.isEmpty(housingInfo.getSysHousingCd())) {
							// ������񂪂��邩�ǂ������f
							HousingStatusInfo housingStatusInfo = housingStatusInfoDAO
									.selectByPK(sysHousingCd);

							if (housingStatusInfo == null) {
								listPrivate.add(favoriteInfo);
							} else if (null != housingStatusInfo && PanaCommonConstant.HIDDEN_FLG_PRIVATE
									.equals(housingStatusInfo.getHiddenFlg())) {
								// ����J���ǂ������f
								listPrivate.add(favoriteInfo);
							}
						} else {
							listPrivate.add(favoriteInfo);
						}
					}
				}
			}
		}
		map.put("private", listPrivate);

		return map;
	}

	/**
	 * ���[�U�[ID��肨�C�ɓ�����擾<br/>
	 * ����W�I�����������i������񂪖����ꍇ�A����сA����J�ɐݒ肳��Ă���ꍇ�j���܂܂Ȃ�<br/>
	 *
	 * @param FavoriteSearchForm
	 * @param userId ���[�U�[ID
	 * @param orderBy �\�[�g��
	 * @param ascending true�F���� false�F�~��
	 *
	 * @return ���C�ɓ����񌏐�
	 * @throws Exception
	 */
	public int searchPublicFavorite(FavoriteSearchForm searchForm, String userId, String orderBy, boolean ascending) throws Exception {

		DAOCriteria paramDAOCriteria = new DAOCriteria();
		paramDAOCriteria.addWhereClause("favoriteInfo", "userId", userId, DAOCriteria.EQUALS, false);
		paramDAOCriteria.addWhereClause("hiddenFlg", PanaCommonConstant.HIDDEN_FLG_PUBLIC);

		switch (orderBy) {
		case "1":
			// ���בւ��F���C�ɓ���o�^��
			paramDAOCriteria.addOrderByClause("favoriteInfo", "insDate", ascending);
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

		List<JoinResult> favoriteInfoList;
		try {
			favoriteInfoList = this.favoritePublicHousingListDAO.selectByFilter(paramDAOCriteria);
		} catch (NotEnoughRowsException err) {
			int pageNo = (err.getMaxRowCount() - 1) / searchForm.getRowsPerPage() + 1;
			searchForm.setSelectedPage(pageNo);
			favoriteInfoList = this.favoritePublicHousingListDAO.selectByFilter(paramDAOCriteria);
		}
		searchForm.setRows(favoriteInfoList);
		return favoriteInfoList.size();

	}

	/**
	 * �����œn���ꂽ�l�ł��C�ɓ������o�^����B<br/>
	 * �o���f�[�V�������̃`�F�b�N�����͌Ăяo�����ŗ\�ߎ��{���Ă������B<br/>
	 * �w�肳�ꂽ���[�U�[ID�A�V�X�e������CD �����ɑ��݂���ꍇ�A�㏑���ۑ�����B<br/>
	 * �@�o�^�������o�^�����̏���Ȃ��B<br/>
	 * <br/>
	 *
	 * @param userId �}�C�y�[�W�̃��[�U�[ID
	 * @param sysHousingCd ���C�ɓ���o�^����V�X�e������CD
	 *
	 * @return messageId 0�˒ǉ�����
	 *                   1�ˍő匏���ɒB����
	 *                   2�ˊ��ɑ��݂���
	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	public String addPanaFavorite(String userId, String sysHousingCd)
			throws Exception {
		log.debug(">>>addFavoriteInfo(String userId, String sysHousingCd)<<<");
		log.debug(">>>userId is : " + userId + "<<<");
		log.debug(">>>sysHousingCd is : " + sysHousingCd + "<<<");

		String message = "";

		if (!StringValidateUtil.isEmpty(userId)
				&& !StringValidateUtil.isEmpty(sysHousingCd)) {
			Map<String, FavoriteInfo> favoriteInfoMap = this
					.getFavoriteInfoMap(userId, false);

			if (favoriteInfoMap.size() >= commonParameters.getMaxFavoriteInfoCnt()) {// ��������𒴂����ꍇ
				message = "1";
			} else {
				if (null != favoriteInfoMap.get(sysHousingCd + ":" + userId)) {// ���ɑ��݂���ꍇ
					message = "2";
				} else {
					// �Y�����镨�����
					HousingInfo housingInfo = this.housingInfoDAO
							.selectByPK(sysHousingCd);

					// �d�v
					// �������C�ɓ�����e�[�u���� FavoriteInfo �ȊO�̃I�u�W�F�N�g�ɕύX�����ꍇ�A
					// ���̃��\�b�h��K�؂ȃo���[�I�u�W�F�N�g�𐶐�����l�ɃI�[�o�[���C�h���鎖�B
					FavoriteInfo favoriteInfo = (FavoriteInfo) this.valueObjectFactory
							.getValueObject("FavoriteInfo");

					// �o�^���A�ŏI�X�V���̒l
					Date sysDate = new Date();

					favoriteInfo.setSysHousingCd(sysHousingCd);
					favoriteInfo.setUserId(userId);
					favoriteInfo.setDisplayHousingName(housingInfo
							.getDisplayHousingName());
					favoriteInfo.setInsDate(sysDate);
					favoriteInfo.setInsUserId(userId);
					favoriteInfo.setUpdDate(sysDate);
					favoriteInfo.setUpdUserId(userId);

					this.favoriteInfoDAO
							.insert(new FavoriteInfo[] { favoriteInfo });

					message = "0";
				}
			}
		}

		return message;
	}

	/**
	 * ���C�ɓ��蕨�������������A���ʃ��X�g�𕜋A����B<br/>
	 * �����œn���ꂽ Form �p�����[�^�̒l�Ō��������𐶐����A���C�ɓ��蕨��������������B<br/>
	 * �������ʂ� Form �I�u�W�F�N�g�Ɋi�[����A�擾�����Y��������߂�l�Ƃ��ĕ��A����B<br/>
	 * <br/>
	 * @param userId ���[�U�[ID
	 *
	 * @return ���ʃ��X�g
	 *
	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	public List<PanaHousing> searchFavoriteInfo(String userId) throws Exception {

		// ���C�ɓ��蕨�����擾����B
		Map<String, FavoriteInfo> favoriteInfo = this.getFavoriteInfoMap(userId, true);

		// �V�X�e������CD
		String sysHousingCd = null;

		// �������
		PanaHousing housing = null;

		// ���C�ɓ��蕨����񌋉ʃ��X�g
		List<PanaHousing> favoriteInfoList = new ArrayList<PanaHousing>();

		// ���C�ɓ��蕨�����J��Ԃ��A���������擾����B
		for (String key : favoriteInfo.keySet()) {

			if (!StringUtils.isEmpty(key) && key.split(":").length == 2) {

				sysHousingCd = key.split(":")[0];

				// ���������擾����B
				housing = (PanaHousing) this.panaHousingManage.searchHousingPk(sysHousingCd);

				// �{������������A���A�\�����A�摜�^�C�v�A�}�Ԃŏ����\�[�g���1�Ԗڂ̉摜��\��
				DAOCriteria criteria = new DAOCriteria();
				criteria.addWhereClause("sysHousingCd", sysHousingCd);
				criteria.addWhereClause("roleId", null, DAOCriteria.IS_NULL, true);
				criteria.addOrderByClause("sortOrder");
				criteria.addOrderByClause("imageType");
				criteria.addOrderByClause("divNo");
				List<HousingImageInfo> housingImageInfoList = this.housingImageInfoDAO.selectByFilter(criteria);
				housing.setHousingImageInfos(housingImageInfoList);

				favoriteInfoList.add(housing);
			}
		}
		return favoriteInfoList;
	}

	/**
	 * ���[�U�[ID��肨�C�ɓ��蕨���e�[�u�����猏���擾
	 *
	 * @param userId
	 *            ���[�U�[ID
	 * @return ���C�ɓ��蕨������
	 * @throws Exception
	 */
	public int getFavoriteInfoCnt(String userId) throws Exception {

		return this.getFavoriteInfoMap(userId, false).size();
	}
}
