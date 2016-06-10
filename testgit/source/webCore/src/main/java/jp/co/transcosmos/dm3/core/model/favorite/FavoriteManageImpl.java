package jp.co.transcosmos.dm3.core.model.favorite;

import java.util.Date;
import java.util.List;

import jp.co.transcosmos.dm3.core.model.FavoriteManage;
import jp.co.transcosmos.dm3.core.model.exception.MaxEntryOverException;
import jp.co.transcosmos.dm3.core.model.favorite.form.FavoriteSearchForm;
import jp.co.transcosmos.dm3.core.model.information.InformationManageImpl;
import jp.co.transcosmos.dm3.core.util.ValueObjectFactory;
import jp.co.transcosmos.dm3.core.vo.FavoriteInfo;
import jp.co.transcosmos.dm3.core.vo.HousingInfo;
import jp.co.transcosmos.dm3.core.vo.UserInfo;
import jp.co.transcosmos.dm3.dao.DAO;
import jp.co.transcosmos.dm3.dao.DAOCriteria;
import jp.co.transcosmos.dm3.dao.JoinResult;
import jp.co.transcosmos.dm3.dao.NotEnoughRowsException;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * ���C�ɓ�����p Model �N���X.
 * <p>
 * <pre>
 * �S����		�C����		�C�����e
 * ------------ ----------- -----------------------------------------------------
 * T.Nakamura	2015.03.16	�V�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 * ���̃N���X�̓V���O���g���� DI �R���e�i�ɒ�`�����̂ŁA�X���b�h�Z�[�t�ł��鎖�B<br/>
 * 
 */
public class FavoriteManageImpl implements FavoriteManage {

	private static final Log log = LogFactory.getLog(InformationManageImpl.class);

	/** VO �̃C���X�^���X�𐶐�����ꍇ�̃t�@�N�g���[ */
	protected ValueObjectFactory valueObjectFactory;

	/** ���C�ɓ�����X�V�p DAO */
	protected DAO<FavoriteInfo> favoriteInfoDAO;

	/** ������{��񌟍��p DAO */
	protected DAO<HousingInfo> housingInfoDAO;

	/** ���C�ɓ����񌟍��p�i������{���܂ށj DAO */
	protected DAO<JoinResult> favoriteListDAO;

	/** ���[�U��񌟍��p DAO */
	protected DAO<UserInfo> userInfoDAO;

	/**
	 * �o���[�I�u�W�F�N�g�̃C���X�^���X�𐶐�����t�@�N�g���[��ݒ肷��B<br/>
	 * <br/>
	 * @param valueObjectFactory �o���[�I�u�W�F�N�g�̃t�@�N�g���[
	 */
	public void setValueObjectFactory(ValueObjectFactory valueObjectFactory) {
		this.valueObjectFactory = valueObjectFactory;
	}

	/**
	 * ���C�ɓ�����X�V�p DAO���擾����B<br/>
	 * <br/>
	 * 
	 * @return ���C�ɓ�����X�V�p DAO
	 */
	public void setFavoriteInfoDAO(DAO<FavoriteInfo> favoriteInfoDAO) {
		this.favoriteInfoDAO = favoriteInfoDAO;
	}

	/**
	 * ������{��񌟍��p DAO���擾����B<br/>
	 * <br/>
	 * 
	 * @return ������{��񌟍��p DAO
	 */
	public void setHousingInfoDAO(DAO<HousingInfo> housingInfoDAO) {
		this.housingInfoDAO = housingInfoDAO;
	}

	/**
	 * ���C�ɓ����񌟍��p DAO���擾����B<br/>
	 * <br/>
	 * 
	 * @return ���C�ɓ����񌟍��p DAO
	 */
	public void setFavoriteListDAO(DAO<JoinResult> favoriteListDAO) {
		this.favoriteListDAO = favoriteListDAO;
	}

	/**
	 * ���[�U��񌟍��p DAO���擾����B<br/>
	 * <br/>
	 * 
	 * @param ���[�U��񌟍��p DAO
	 */
	public void setUserInfoDAO(DAO<UserInfo> userInfoDAO) {
		this.userInfoDAO = userInfoDAO;
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
	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 * @exception MaxEntryOverException�@�ő�o�^���I�[�o�[
	 */
	@Override
	public void addFavorite(String userId, String sysHousingCd) throws Exception {

		// �p�����^�`�F�b�N
		if (StringValidateUtil.isEmpty(userId)) {
			log.warn("userId Is Null or Empty [userId = " + userId + "]");
			return;
		}
		if (StringValidateUtil.isEmpty(sysHousingCd)) {
			log.warn("sysHousingCd Is Null or Empty [sysHousingCd = " + sysHousingCd + "]");
			return;
		}

		// �o�^�Ώۏ��̑��݃`�F�b�N
		UserInfo existUserInfo = this.userInfoDAO.selectByPK(userId);
		if (existUserInfo == null) {
			log.warn("Target UserInfo Is Null [userId = " + userId + "]");
			return;
		}
		HousingInfo existHousingInfo = this.housingInfoDAO.selectByPK(sysHousingCd);
		if (existHousingInfo == null) {
			log.warn("Target HousingInfo Is Null [sysHousingCd = " + sysHousingCd + "]");
			return;
		}

		// �����̂��C�ɓ��背�R�[�h�̌���
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", sysHousingCd);
		criteria.addWhereClause("userId", userId);
		List<FavoriteInfo> favoriteInfos = this.favoriteInfoDAO.selectByFilter(criteria);

		// �������R�[�h�����݂���ꍇ�͏������f
		if (favoriteInfos != null && favoriteInfos.size() > 1) {
			log.warn("Selected FavoriteInfo Is Not Unique [userId = " + userId + ", sysHousingCd = " + sysHousingCd + "]");
			return;
		}

		// �Y�����镨�����
		HousingInfo housingInfo = this.housingInfoDAO.selectByPK(sysHousingCd);

		// �Y�����镨����񂪎擾�ł��Ȃ��ꍇ�͏������f
		if (housingInfo == null) {
			log.warn("Selected HousingInfo Is Null [sysHousingCd = " + sysHousingCd + "]");
			return;
		}

		FavoriteInfo favoriteInfo = null;
		// �o�^���A�ŏI�X�V���̒l
		Date sysDate = new Date();

		if (favoriteInfos == null || favoriteInfos.isEmpty()) {
			// �V�K�o�^

			favoriteInfo = buildFavoriteInfo();
			favoriteInfo.setSysHousingCd(sysHousingCd);
			favoriteInfo.setUserId(userId);
			favoriteInfo.setDisplayHousingName(housingInfo.getDisplayHousingName());
			favoriteInfo.setInsDate(sysDate);
			favoriteInfo.setInsUserId(userId);
			favoriteInfo.setUpdDate(sysDate);
			favoriteInfo.setUpdUserId(userId);

			this.favoriteInfoDAO.insert(new FavoriteInfo[] { favoriteInfo });

			log.debug("Add FavoriteInfo [userId = " + userId + ", sysHousingCd = " + sysHousingCd + "]");

		} else if (favoriteInfos.size() == 1) {
			// �X�V

			favoriteInfo = favoriteInfos.get(0);
			// �X�V���_�̕\���p���������Đݒ�
			favoriteInfo.setDisplayHousingName(housingInfo.getDisplayHousingName());
			favoriteInfo.setUpdDate(sysDate);
			favoriteInfo.setUpdUserId(userId);

			this.favoriteInfoDAO.update(new FavoriteInfo[] { favoriteInfo });

			log.debug("Updata FavoriteInfo [userId = " + userId + ", sysHousingCd = " + sysHousingCd + "]");

		}
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

		// �p�����^�`�F�b�N
		if (StringValidateUtil.isEmpty(userId)) {
			log.warn("userId Is Null or Empty [userId = " + userId + "]");
			return;
		}
		if (StringValidateUtil.isEmpty(sysHousingCd)) {
			log.warn("sysHousingCd Is Null or Empty [sysHousingCd = " + sysHousingCd + "]");
			return;
		}

		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", sysHousingCd);
		criteria.addWhereClause("userId", userId);
		this.favoriteInfoDAO.deleteByFilter(criteria);

		log.debug("Deleted FavoriteInfo [userId = " + userId + ", sysHousingCd = " + sysHousingCd + "]");

	}

	/**
	 * ���C�ɓ�������������A���ʃ��X�g�𕜋A����B<br/>
	 * �����œn���ꂽ Form �p�����[�^�̒l�Ō��������𐶐����A���C�ɓ��������������B<br/>
	 * �������ʂ� Form �I�u�W�F�N�g�Ɋi�[����A�擾�����Y��������߂�l�Ƃ��ĕ��A����B<br/>
	 * �������ɑ��݂��Ȃ����C�ɓ�����͍폜������ŁA�������ʂ��擾����<br/>
	 * <br/>
	 * @param userId �}�C�y�[�W�̃��[�U�[ID
	 * @param searchForm ���������A����сA�������ʂ̊i�[�I�u�W�F�N�g
	 * 
	 * @return �擾����
	 * 
 	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	@Override
	public int searchFavorite(String userId, FavoriteSearchForm searchForm) throws Exception {

		// �p�����^�`�F�b�N
		if (StringValidateUtil.isEmpty(userId)) {
			log.warn("userId Is Null or Empty [userId = " + userId + "]");
			return 0;
		}
		if (searchForm == null) {
			log.warn("FavoriteSearchForm Is Null");
			return 0;
		}

		// �R�t��������񂪑��݂��Ȃ��ꍇ�A�Y���̂��C�ɓ�������폜����
		DAOCriteria criteria = new DAOCriteria();
		if (!StringValidateUtil.isEmpty(searchForm.getSysHousingCd())) {
			criteria.addWhereClause("sysHousingCd", searchForm.getSysHousingCd());
		}
		criteria.addWhereClause("userId", userId);

		List<FavoriteInfo> favoriteInfos = this.favoriteInfoDAO.selectByFilter(criteria);

		for (FavoriteInfo favoriteInfo : favoriteInfos) {
			HousingInfo housingInfo = this.housingInfoDAO.selectByPK(favoriteInfo.getSysHousingCd());
			if (housingInfo == null) {
				delFavorite(userId, favoriteInfo.getSysHousingCd());
			}
		}

		// ���������̐���
		DAOCriteria pagingCriteria = searchForm.buildCriteria();
		pagingCriteria.addWhereClause("userId", userId);

		// ������{��������������ԂŁA���߂Ă��C�ɓ�����̌���
		// ���[�UID�̏����A�ŏI�X�V���̍~���Ń\�[�g
		pagingCriteria.addOrderByClause("favoriteInfo", "userId", true);
		pagingCriteria.addOrderByClause("favoriteInfo", "updDate", false);

		List<JoinResult> results;
		try {
			results = this.favoriteListDAO.selectByFilter(pagingCriteria);
		} catch (NotEnoughRowsException err) {
			int pageNo = (err.getMaxRowCount() - 1) / searchForm.getRowsPerPage() + 1;
			log.warn("resetting page to " + pageNo);
			searchForm.setSelectedPage(pageNo);

			// criteria����蒼��
			pagingCriteria = searchForm.buildCriteria();
			pagingCriteria.addWhereClause("userId", userId);
			pagingCriteria.addOrderByClause("favoriteInfo", "userId", true);
			pagingCriteria.addOrderByClause("favoriteInfo", "updDate", false);

			results = this.favoriteListDAO.selectByFilter(pagingCriteria);
		}

		// �������ʂ̊i�[�A�����̕ԋp
		searchForm.setRows(results);
		return results.size();

		
	}

	/**
	 * �����œn���ꂽ userId �ɊY�����邨�C�ɓ�����̌������擾����B<br/>
	 * <br/>
	 * 
	 * @param userId �}�C�y�[�W�̃��[�U�[ID
	 * 
	 * @return ���C�ɓ���o�^���������̌���
	 * 
	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	@Override
	public int getFavoriteCnt(String userId) throws Exception {

		// �p�����^�`�F�b�N
		if (StringValidateUtil.isEmpty(userId)) {
			log.warn("userId Is Null or Empty [userId = " + userId + "]");
			return 0;
		}

		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("userId", userId);
		List<FavoriteInfo> favoriteInfos = this.favoriteInfoDAO.selectByFilter(criteria);

		if (favoriteInfos == null) {
			return 0;
		} else {
			return favoriteInfos.size();
		}

	}

	/**
	 * ���C�ɓ�����p�̃o���[�I�u�W�F�N�g���쐬����t�@�N�g���[���\�b�h<br/>
	 * <br/>
	 * @return FavoriteInfo ���p���������C�ɓ�����I�u�W�F�N�g
	 */
	protected FavoriteInfo buildFavoriteInfo() {

		// �d�v
		// �������C�ɓ�����e�[�u���� FavoriteInfo �ȊO�̃I�u�W�F�N�g�ɕύX�����ꍇ�A
		// ���̃��\�b�h��K�؂ȃo���[�I�u�W�F�N�g�𐶐�����l�ɃI�[�o�[���C�h���鎖�B

		return (FavoriteInfo) this.valueObjectFactory.getValueObject("FavoriteInfo"); 
	}

}
