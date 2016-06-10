package jp.co.transcosmos.dm3.corePana.model.housing;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.core.model.exception.NotFoundException;
import jp.co.transcosmos.dm3.core.model.housing.Housing;
import jp.co.transcosmos.dm3.core.model.housing.HousingManageImpl;
import jp.co.transcosmos.dm3.core.model.housing.form.HousingDtlForm;
import jp.co.transcosmos.dm3.core.model.housing.form.HousingForm;
import jp.co.transcosmos.dm3.core.model.housing.form.HousingImgForm;
import jp.co.transcosmos.dm3.core.model.housing.form.HousingSearchForm;
import jp.co.transcosmos.dm3.core.vo.AdminLoginInfo;
import jp.co.transcosmos.dm3.core.vo.BuildingDtlInfo;
import jp.co.transcosmos.dm3.core.vo.HousingImageInfo;
import jp.co.transcosmos.dm3.corePana.model.PanaCommonManage;
import jp.co.transcosmos.dm3.corePana.model.ReformManage;
import jp.co.transcosmos.dm3.corePana.model.housing.dao.PanaSearchHousingDAO;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingInfoForm;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingInspectionForm;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingSearchForm;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingStatusForm;
import jp.co.transcosmos.dm3.corePana.vo.HousingInfo;
import jp.co.transcosmos.dm3.corePana.vo.HousingInspection;
import jp.co.transcosmos.dm3.corePana.vo.HousingStatusInfo;
import jp.co.transcosmos.dm3.corePana.vo.ReformPlan;
import jp.co.transcosmos.dm3.dao.DAO;
import jp.co.transcosmos.dm3.dao.DAOCriteria;
import jp.co.transcosmos.dm3.dao.JoinResult;
import jp.co.transcosmos.dm3.dao.NotEnoughRowsException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.util.StringUtils;

/**
 * ������񃁃��e�i���X�p Model �N���X.
 * <p>
 * �������𑀍삷�� model �N���X�͂��̃C���^�[�t�F�[�X���������鎖�B<br/>
 * <p>
 *
 * <pre>
 * �S����		�C����		�C�����e
 * ------------ ----------- -----------------------------------------------------
 * TRANS		2015.03.16	�V�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 * ���̃N���X�̓V���O���g���� DI �R���e�i�ɒ�`�����̂ŁA�X���b�h�Z�[�t�ł��鎖�B<br/>
 *
 */
public class PanaHousingManageImpl extends HousingManageImpl {

	/** ���t�H�[�������Ǘ��p Model */
	private ReformManage reformManager;

	/** �����X�e�[�^�X��� **/
	private DAO<jp.co.transcosmos.dm3.core.vo.HousingStatusInfo> housingStatusInfoDAO;

	/** �����C���X�y�N�V������� **/
	private DAO<HousingInspection> housingInspectionDAO;

	/** �����ڍ׏�� **/
	private DAO<BuildingDtlInfo> buildingDtlInfoDAO;

	/** �ݔ��}�X�^��� **/
	private DAO<JoinResult> equipListDAO;

	/** ������{���p DAO */
	private DAO<jp.co.transcosmos.dm3.core.vo.BuildingInfo> buildingInfoDAO;

	/** �����ꗗ�����p DAO */
	private PanaSearchHousingDAO panaSearchHousingDAO;

	/** �����摜���DAO */
	private DAO<jp.co.transcosmos.dm3.core.vo.HousingImageInfo> HousingImageInfoDAO;

	/** �Ǘ����[�U�[���DAO */
	private DAO<AdminLoginInfo> adminLoginInfoDAO;

	/**
	 * ���t�H�[�������Ǘ����� Model ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param reformManager
	 *            ���t�H�[�������Ǘ����� Model
	 */
	public void setReformManager(ReformManage reformManager) {
		this.reformManager = reformManager;
	}

	/**
	 * �����X�e�[�^�X��� DAO ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param housingStatusInfoDAO
	 *            �����X�e�[�^�X��� DAO
	 */
	public void setHousingStatusInfoDAO(
			DAO<jp.co.transcosmos.dm3.core.vo.HousingStatusInfo> housingStatusInfoDAO) {
		this.housingStatusInfoDAO = housingStatusInfoDAO;
	}

	/**
	 * �����C���X�y�N�V���� DAO ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param housingInspectionDAO
	 *            �����C���X�y�N�V���� DAO
	 */
	public void setHousingInspectionDAO(
			DAO<HousingInspection> housingInspectionDAO) {
		this.housingInspectionDAO = housingInspectionDAO;
	}

	/**
	 * �����ڍ׏�� DAO ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param buildingDtlInfoDAO
	 *            �����ڍ׏�� DAO
	 */
	public void setBuildingDtlInfoDAO(DAO<BuildingDtlInfo> buildingDtlInfoDAO) {
		this.buildingDtlInfoDAO = buildingDtlInfoDAO;
	}

	/**
	 * �ݔ��}�X�^��� DAO ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param equipMstDAO
	 *            �ݔ��}�X�^��� DAO
	 */
	public void setEquipListDAO(DAO<JoinResult> equipListDAO) {
		this.equipListDAO = equipListDAO;
	}

	/**
	 * ������� DAO ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param buildingInfoDAO
	 *            ������� DAO
	 */
	public void setBuildingInfoDAO(
			DAO<jp.co.transcosmos.dm3.core.vo.BuildingInfo> buildingInfoDAO) {
		this.buildingInfoDAO = buildingInfoDAO;
	}

	/**
	 * �����ꗗ�����p DAO�i�Ǘ��T�C�g�ƃt�����g�T�C�g�����j ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param panaSearchHousingDAO
	 *            �����ꗗ�����p DAO
	 */
	public void setPanaSearchHousingDAO(
			PanaSearchHousingDAO panaSearchHousingDAO) {
		this.panaSearchHousingDAO = panaSearchHousingDAO;
	}

	/**
	 * �����摜���p DAO ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param housingImageInfoDAO
	 *            �����摜���p DAO
	 */
	public void setHousingImageInfoDAO(
			DAO<jp.co.transcosmos.dm3.core.vo.HousingImageInfo> housingImageInfoDAO) {
		super.setHousingImageInfoDAO(housingImageInfoDAO);
		this.HousingImageInfoDAO = housingImageInfoDAO;
	}

	/**
	 * �Ǘ����[�U�[���DAO ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param adminLoginInfoDAO
	 *            �Ǘ����[�U�[���DAO
	 */
	public void setAdminLoginInfoDAO(DAO<AdminLoginInfo> adminLoginInfoDAO) {
		this.adminLoginInfoDAO = adminLoginInfoDAO;
	}

	/**
	 * �p�����[�^�œn���ꂽ Form �̏��ŕ�����{����V�K�o�^����B<br/>
	 * �o���f�[�V�������̃`�F�b�N�����͌Ăяo�����ŗ\�ߎ��{���Ă������B<br/>
	 * ���\���p�A�C�R���͐ݔ����ōX�V����̂ŁA���̃��\�b�h�ł͍X�V����Ȃ��B<br/>
	 * <br/>
	 *
	 * @param inputForm
	 *            ������{���̓��͒l���i�[���� Form �I�u�W�F�N�g
	 * @param editUserId
	 *            ���O�C�����[�U�[ID �i�X�V���p�j
	 *
	 * @return �̔Ԃ��ꂽ�V�X�e������CD
	 */
	@Override
	public String addHousing(HousingForm inputForm, String editUserId)
			throws Exception {

		return super.addHousing(inputForm, editUserId);

	}

	/**
	 * �p�����[�^�œn���ꂽ Form �̏��ŕ�����{�����X�V����B<br/>
	 * �o���f�[�V�������̃`�F�b�N�����͌Ăяo�����ŗ\�ߎ��{���Ă������B<br/>
	 * HousingForm �� sysHousingCd �v���p�e�B�ɐݒ肳�ꂽ�l����L�[�l�Ƃ��čX�V����B<br/>
	 * ���\���p�A�C�R���͐ݔ����ōX�V����̂ŁA���̃��\�b�h�ł͍X�V����Ȃ��B<br/>
	 * <br/>
	 *
	 * @param inputForm
	 *            ������{���̓��͒l���i�[���� Form �I�u�W�F�N�g
	 * @param editUserId
	 *            ���O�C�����[�U�[ID �i�X�V���p�j
	 *
	 * @exception NotFoundException
	 *                �X�V�Ώۂ����݂��Ȃ��ꍇ
	 */
	@Override
	public void updateHousing(HousingForm inputForm, String editUserId)
			throws Exception, NotFoundException {

		super.updateHousing(inputForm, editUserId);
	}

	/**
	 * �p�����[�^�œn���ꂽ Form �̏��ŕ�����{�����폜����B<br/>
	 * �o���f�[�V�������̃`�F�b�N�����͌Ăяo�����ŗ\�ߎ��{���Ă������B<br/>
	 * HousingForm �� sysHousingCd �v���p�e�B�ɐݒ肳�ꂽ�l����L�[�l�Ƃ��č폜����B
	 * �܂��A�폜�Ώۃ��R�[�h�����݂��Ȃ��ꍇ�ł�����I���Ƃ��Ĉ������B<br/>
	 * �폜���͕�����{���̏]���\���폜�ΏۂƂ���B<br/>
	 * �֘A����摜�t�@�C���̍폜�� Proxy �N���X���őΉ�����̂ŁA���̃N���X���ɂ͎������Ȃ��B<br/>
	 * <br/>
	 *
	 * @param inputForm
	 *            �폜�ΏۂƂȂ� sysHousingCd ���i�[���� Form �I�u�W�F�N�g
	 * @param editUserId
	 *            �폜�S����
	 */
	@Override
	public void delHousingInfo(HousingForm inputForm, String editUserId) {

		// super.delHousingInfo(inputForm, editUserId);

		// ���������폜��������𐶐�����B
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("sysBuildingCd", inputForm.getSysBuildingCd());
		// ������{�����폜����
		this.buildingInfoDAO.deleteByFilter(criteria);

	}

	/**
	 * �p�����[�^�œn���ꂽ Form �̏��ŕ����ڍ׏����X�V����B<br/>
	 * �Y�����镨���ڍ׏�񂪑��݂��Ȃ��Ă��A�Y�����镨����{��񂪑��݂���ꍇ�A���R�[�h��V���� �ǉ����ĕ����ڍ׏���o�^����B<br/>
	 * �o���f�[�V�������̃`�F�b�N�����͌Ăяo�����ŗ\�ߎ��{���Ă������B<br/>
	 * HousingDtlForm �� sysHousingCd �v���p�e�B�ɐݒ肳�ꂽ�l����L�[�l�Ƃ��čX�V����B<br/>
	 * <br/>
	 *
	 * @param inputForm
	 *            �����ڍ׏��̓��͒l���i�[���� Form �I�u�W�F�N�g
	 * @param editUserId
	 *            ���O�C�����[�U�[ID �i�X�V���p�j
	 *
	 */
	@Override
	public void updateHousingDtl(HousingDtlForm inputForm, String editUserId) {

		super.updateHousingDtl(inputForm, editUserId);

	}

	/**
	 * �ݔ�CD ����\���p�A�C�R�������X�V����B<br/>
	 * ���̃��\�b�h�͐ݔ����̍X�V������������s����A���͂����ݔ�CD �����ɕ\���p�A�C�R������ ��������B<br/>
	 * �\���p�A�C�R������ʂ̌��򂩂�쐬����ꍇ�A�����������Ȃ��l�ɂ��̃��\�b�h���I�[�o�[���C�h ����K�v������B<br/>
	 * <br/>
	 *
	 * @param sysHousingCd
	 * @param equipCds
	 */
	@Override
	protected void equipToiconData(String sysHousingCd, String[] equipCds,
			String editUserId) {

	}

	/**
	 * �p�����[�^�œn���ꂽ Form �̏��ŕ����摜����V�K�o�^����B<br/>
	 * �o���f�[�V�������̃`�F�b�N�����͌Ăяo�����ŗ\�ߎ��{���Ă������B<br/>
	 * <br/>
	 * �֘A����摜�t�@�C���̌��J������T���l�C���̍쐬�� Proxy �N���X���őΉ�����̂ŁA ���̃N���X���ł͑Ή����Ȃ��B<br/>
	 * <br/>
	 *
	 * @param inputForm
	 *            �����摜���̓��͒l���i�[���� Form �I�u�W�F�N�g
	 * @param editUserId
	 *            ���O�C�����[�U�[ID �i�X�V���p�j
	 *
	 * @return �V���ɒǉ������摜���̃��X�g
	 *
	 * @exception Exception
	 *                �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	@Override
	public List<jp.co.transcosmos.dm3.core.vo.HousingImageInfo> addHousingImg(
			HousingImgForm inputForm, String editUserId) throws Exception {

		return super.addHousingImg(inputForm, editUserId);
	}

	/**
	 * �p�����[�^�œn���ꂽ�V�X�e������CD�A�摜�^�C�v�A�}�Ԃŕ����摜�����폜����B<br/>
	 * �����폜�t���O�� 1 ���ݒ肳��Ă���ꍇ�͍X�V�����ɍ폜��������B<br/>
	 * �o���f�[�V�������̃`�F�b�N�����͌Ăяo�����ŗ\�ߎ��{���Ă������B<br/>
	 * <br/>
	 * �֘A����摜�t�@�C���̍폜�́AProxy �N���X���őΉ�����̂ŁA���̃N���X���ł͑Ή����Ȃ��B<br/>
	 * �܂��A�X�V�����́A�摜�p�X�A�摜�t�@�C�����̍X�V�̓T�|�[�g���Ă��Ȃ��B<br/>
	 * <br/>
	 *
	 * @param sysHousingCd
	 *            �V�X�e������CD
	 * @param imageType
	 *            �����摜�^�C�v
	 * @param divNo
	 *            �}��
	 * @param editUserId
	 *            ���O�C�����[�U�[ID �i�X�V���p�j
	 *
	 * @return �폜�����������摜���̃��X�g
	 */
	@Override
	public List<jp.co.transcosmos.dm3.core.vo.HousingImageInfo> updHousingImg(
			HousingImgForm inputForm, String editUserId) {

		// return super.updHousingImg(inputForm, editUserId);

		// �߂�l�ƂȂ�A�폜���������������摜���̃��X�g
		List<HousingImageInfo> delImgList = new ArrayList<>();

		// ���C���摜�t���O�����Z�b�g���A�V�X�e������CD �P�ʂŕ����摜�������b�N����B
		lockAndRestFlag(inputForm.getSysHousingCd());

		String[] divNo = inputForm.getDivNo();
		// �摜�t�@�C������Ɍ��������[�v����B
		for (int idx = 0; idx < inputForm.getFileName().length; ++idx) {

			// �폜�t���O���ݒ肳��Ă���ꍇ�͍폜������
			if ("1".equals(inputForm.getDelFlg()[idx])) {

				// �����摜�����폜
				HousingImageInfo imgInfo = delHousingImg(
						inputForm.getSysHousingCd(),
						inputForm.getImageType()[idx],
						Integer.valueOf(inputForm.getDivNo()[idx]));
				// ���ۂɍ폜�����񂪖��������ꍇ�A null �����A�����̂ŁA���̏ꍇ�̓��X�g�ɒǉ����Ȃ��B
				if (imgInfo != null) {
					delImgList.add(imgInfo);
				}

			} else {
				// �����𐶐�����B
				DAOCriteria criteria = new DAOCriteria();
				criteria.addWhereClause("sysHousingCd",
						inputForm.getSysHousingCd());
				criteria.addWhereClause("imageType",
						inputForm.getOldImageType()[idx]);
				criteria.addWhereClause("divNo",
						Integer.valueOf(inputForm.getDivNo()[idx]));

				List<HousingImageInfo> imgInfoList = this.HousingImageInfoDAO
						.selectByFilter(criteria);
				HousingImageInfo imgInfo = imgInfoList.get(0);


				// �����𐶐�����B
				DAOCriteria criteriaNew = new DAOCriteria();
				criteriaNew.addWhereClause("sysHousingCd",
						inputForm.getSysHousingCd());
				criteriaNew.addWhereClause("imageType",
						inputForm.getImageType()[idx]);
				criteriaNew.addOrderByClause("divNo", false);
				List<HousingImageInfo> newImgInfoList = this.HousingImageInfoDAO
						.selectByFilter(criteriaNew);

				if(newImgInfoList ==null || newImgInfoList.size()==0){
					divNo[idx]="1";
				}else{
					HousingImageInfo newImgInfo = newImgInfoList.get(0);
					divNo[idx] =String.valueOf(newImgInfo.getDivNo()+1);
				}
				inputForm.setDivNo(divNo);
				// Form ���� UpdateExpression �𐶐����ăf�[�^���X�V����B
				this.HousingImageInfoDAO.updateByCriteria(criteria,
						inputForm.buildUpdateExpression(idx));
				if (!((jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingImageInfoForm) inputForm)
						.getOldRoleId()[idx]
						.equals(((jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingImageInfoForm) inputForm)
								.getRoleId()[idx])) {
					delImgList.add(imgInfo);
				}

			}
		}

		// ���C���摜�t���O�A�}�Ԃ��X�V����B
		updateMainFlgAndDivNo(inputForm.getSysHousingCd());

		// ������{���̃^�C���X�^���v���X�V
		updateEditTimestamp(inputForm.getSysHousingCd(), editUserId);

		return delImgList;
	}

	/**
	 * �p�����[�^�œn���ꂽ�V�X�e������CD�A�摜�^�C�v�A�}�Ԃŕ����摜�����폜����B<br/>
	 * �o���f�[�V�������̃`�F�b�N�����͌Ăяo�����ŗ\�ߎ��{���Ă������B<br/>
	 * �X�V��ID ���ȗ��������̃��\�b�h�ꍇ�A���C���摜�t���O�A�}�ԁA������{���̃^�C���X�^���v�͍X�V����Ȃ��B <br/>
	 * �֘A����摜�t�@�C���̍폜�́AProxy �N���X���őΉ�����̂ŁA���̃N���X���ł͑Ή����Ȃ��B<br/>
	 * <br/>
	 *
	 * @param sysHousingCd
	 *            �V�X�e������CD
	 * @param imageType
	 *            �����摜�^�C�v
	 * @param divNo
	 *            �}��
	 *
	 * @return �폜�����������摜���
	 *
	 */
	protected jp.co.transcosmos.dm3.core.vo.HousingImageInfo delHousingImg(
			String sysHousingCd, String imageType, int divNo) {

		return super.delHousingImg(sysHousingCd, imageType, divNo);
	}

	/**
	 * �p�����[�^�œn���ꂽ�V�X�e������CD�A�摜�^�C�v�A�}�Ԃŕ����摜�����폜����B<br/>
	 * �o���f�[�V�������̃`�F�b�N�����͌Ăяo�����ŗ\�ߎ��{���Ă������B<br/>
	 * �폜��A���C���摜�t���O�ƕ�����{���̃^�C���X�^���v�����X�V����B<br/>
	 * <br/>
	 * �֘A����摜�t�@�C���̍폜�́AProxy �N���X���őΉ�����̂ŁA���̃N���X���ł͑Ή����Ȃ��B<br/>
	 * <br/>
	 *
	 * @param sysHousingCd
	 *            �V�X�e������CD
	 * @param imageType
	 *            �����摜�^�C�v
	 * @param divNo
	 *            �}��
	 * @param editUserId
	 *            ���O�C�����[�U�[ID �i�X�V���p�j
	 *
	 * @return �폜�����������摜���
	 */
	@Override
	public jp.co.transcosmos.dm3.core.vo.HousingImageInfo delHousingImg(
			String sysHousingCd, String imageType, int divNo, String editUserId) {

		return super.delHousingImg(sysHousingCd, imageType, divNo, editUserId);
	}

	/**
	 * �����摜���̃p�X���Ɋi�[����l���擾����B<br/>
	 * <br/>
	 *
	 * @param housing
	 *            �X�V�ΏۂƂȂ镨���I�u�W�F�N�g
	 *
	 * @return ���H���ꂽ�p�X��
	 */
	protected String createImagePath(Housing housing) {

		return super.createImagePath(housing);
	}

	/**
	 * �����ԍ��A�摜�^�C�v���ɁA�ő�̎}�Ԃ��擾����B<br/>
	 * <br/>
	 *
	 * @param sysHousingCd
	 *            �V�X�e������CD
	 * @param imageType
	 *            �摜�^�C�v
	 */
	protected int getMaxDivNo(String sysHousingCd, String imageType) {

		return super.getMaxDivNo(sysHousingCd, imageType);
	}

	/**
	 * �V�X�e������CD �P�ʂɕ����摜���̍s���b�N���擾����B<br/>
	 * �܂��A���̍ہA���C���摜�t���O�̃��Z�b�g���s���B<br/>
	 * <br/>
	 *
	 * @param sysHousingCd
	 *            �V�X�e������CD
	 */
	protected void lockAndRestFlag(String sysHousingCd) {

		super.lockAndRestFlag(sysHousingCd);

	}

	/**
	 * �����摜���̃��C���摜�t���O�A����ю}�Ԃ̍X�V���s��<br/>
	 * �摜��ʖ��Ɉ�ԍŏ��ɕ\�����镨���摜���ɑ΂��ă��C���摜�t���O��ݒ肷��B<br/>
	 * �܂��A�摜�^�C�v���ɕ\�����Ŏ}�Ԃ��X�V����B<br/>
	 * <br/>
	 *
	 * @param getSysHousingCd
	 *            �V�X�e������CD
	 */
	protected void updateMainFlgAndDivNo(String sysHousingCd) {
		super.updateMainFlgAndDivNo(sysHousingCd);
	}

	/**
	 * �������i�ꕔ�A�������j���������A���ʃ��X�g�𕜋A����B�i�ꗗ�p�j<br/>
	 * �����œn���ꂽ Form �p�����[�^�̒l�Ō��������𐶐����A����������������B<br/>
	 * �������ʂ� Form �I�u�W�F�N�g�Ɋi�[����A�擾�����Y��������߂�l�Ƃ��ĕ��A����B<br/>
	 * <br/>
	 * ���̃��\�b�h���g�p�����ꍇ�A�Öق̒��o�����i�Ⴆ�΁A����J�����̏��O�Ȃǁj���K�p�����B<br/>
	 * ����āA�t�����g���͊�{�I�ɂ��̃��\�b�h���g�p���鎖�B<br/>
	 * <br/>
	 *
	 * @param searchForm
	 *            ���������A����сA�������ʂ̊i�[�I�u�W�F�N�g
	 * @param full
	 *            false �̏ꍇ�A���J�ΏۊO�����O����B�@true �̏ꍇ�͏��O���Ȃ�
	 *
	 * @return �Y������
	 *
	 * @exception Exception
	 *                �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	@Override
	public int searchHousing(HousingSearchForm searchForm) throws Exception {
		return super.searchHousing(searchForm);
	}

	/**
	 * �������i�ꕔ�A�������j���������A���ʃ��X�g�𕜋A����B�i�ꗗ�p�j<br/>
	 * �����œn���ꂽ Form �p�����[�^�̒l�Ō��������𐶐����A����������������B<br/>
	 * �������ʂ� Form �I�u�W�F�N�g�Ɋi�[����A�擾�����Y��������߂�l�Ƃ��ĕ��A����B<br/>
	 * <br/>
	 *
	 * @param searchForm
	 *            ���������A����сA�������ʂ̊i�[�I�u�W�F�N�g
	 * @param full
	 *            false �̏ꍇ�A���J�ΏۊO�����O����B�@true �̏ꍇ�͏��O���Ȃ�
	 *
	 * @return �Y������
	 *
	 * @exception Exception
	 *                �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	@Override
	public int searchHousing(HousingSearchForm searchForm, boolean full)
			throws Exception {
		// return super.searchHousing(searchForm, full);

		PanaHousingSearchForm panaSearchForm = (PanaHousingSearchForm) searchForm;

		// ��������
		List<Housing> housingList = null;
		try {
			housingList = this.panaSearchHousingDAO
					.panaSearchHousing(panaSearchForm);
		} catch (NotEnoughRowsException err) {
			// �͈͊O�̃y�[�W���ݒ肳�ꂽ�ꍇ�A�Č���
			int pageNo = (err.getMaxRowCount() - 1)
					/ panaSearchForm.getRowsPerPage() + 1;
			panaSearchForm.setSelectedPage(pageNo);
			housingList = this.panaSearchHousingDAO
					.panaSearchHousing(panaSearchForm);
		}
		panaSearchForm.setRows(housingList);

		return housingList.size();
	}

	/**
	 * ���N�G�X�g�p�����[�^�œn���ꂽ�V�X�e������CD �i��L�[�l�j�ɊY�����镨�����𕜋A����B<br/>
	 * �����Y���f�[�^��������Ȃ��ꍇ�Anull �𕜋A����B<br/>
	 * <br/>
	 * ���̃��\�b�h���g�p�����ꍇ�A�Öق̒��o�����i�Ⴆ�΁A����J�����̏��O�Ȃǁj���K�p�����B<br/>
	 * ����āA�t�����g���͊�{�I�ɂ��̃��\�b�h���g�p���鎖�B<br/>
	 * <br/>
	 *
	 * @param sysHousingCd
	 *            �V�X�e������CD
	 * @param full
	 *            false �̏ꍇ�A���J�ΏۊO�����O����B�@true �̏ꍇ�͏��O���Ȃ�
	 *
	 * @return�@DB ����擾�����o���[�I�u�W�F�N�g���i�[�����R���|�W�b�g�N���X
	 *
	 * @exception Exception
	 *                �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	@Override
	public Housing searchHousingPk(String sysHousingCd) throws Exception {
		return searchHousingPk(sysHousingCd, false);
	}

	/**
	 * ���N�G�X�g�p�����[�^�œn���ꂽ�V�X�e������CD �i��L�[�l�j�ɊY�����镨�����𕜋A����B<br/>
	 * �����Y���f�[�^��������Ȃ��ꍇ�Anull �𕜋A����B<br/>
	 * <br/>
	 * ����{�I�ɁA�p�����[�^ full �� true �ɂ��Ďg�p����̂͊Ǘ���ʂ̂݁B<br/>
	 * <br/>
	 *
	 * @param sysHousingCd
	 *            �V�X�e������CD
	 * @param full
	 *            false �̏ꍇ�A���J�ΏۊO�����O����B�@true �̏ꍇ�͏��O���Ȃ�
	 *
	 * @return�@DB ����擾�����o���[�I�u�W�F�N�g���i�[�����R���|�W�b�g�N���X
	 *
	 * @exception Exception
	 *                �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	@Override
	public Housing searchHousingPk(String sysHousingCd, boolean full)
			throws Exception {

		PanaHousing panaHousing = (PanaHousing) super.searchHousingPk(
				sysHousingCd, full);

		if (panaHousing != null) {
			// �����C���X�y�N�V�����̃��X�g��ݒ肷��
			panaHousing.setHousingInspections(this
					.searchHousingInspection(sysHousingCd));

			// ���t�H�[���v������������
			List<ReformPlan> reformPlanList = this.reformManager
					.searchReformPlan(sysHousingCd, full);

			List<Map<String, Object>> reforms = new ArrayList<Map<String, Object>>();
			// ���t�H�[���̏���ݒ肷��
			for (ReformPlan rp : reformPlanList) {
				Map<String, Object> reformMap = this.reformManager
						.searchReform(rp.getSysReformCd());
				reforms.add(reformMap);
			}
			panaHousing.setReforms(reforms);

			JoinResult housingResult = panaHousing.getHousingInfo();
			HousingInfo housingInfo = (HousingInfo) housingResult.getItems().get(
					"housingInfo");
			if (!StringUtils.isEmpty(housingInfo.getUpdUserId())) {
				DAOCriteria criteria = new DAOCriteria();
				criteria.addWhereClause("adminUserId", housingInfo.getUpdUserId());

				List<AdminLoginInfo> adminLoginInfoList = this.adminLoginInfoDAO
						.selectByFilter(criteria);

				if (adminLoginInfoList != null && adminLoginInfoList.size() > 0) {
					panaHousing.setHousingInfoUpdUser(adminLoginInfoList.get(0));
				}
			}
		}

		return panaHousing;
	}

	/**
	 * �����ڍ׏��� �P�΂P�̊֌W�ɂ��郁�C���̊֘A�e�[�u���̏����擾���AHousing �I�u�W�F�N�g�֐ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param housing
	 *            �l�̐ݒ��ƂȂ� Housing �I�u�W�F�N�g
	 * @param sysHousingCd
	 *            �擾�ΏۃV�X�e������CD
	 * @param full
	 *            false �̏ꍇ�A���J�ΏۊO�����O����B�@true �̏ꍇ�͏��O���Ȃ�
	 *
	 * @return �擾���� �i�Y���Ȃ��̏ꍇ�Anull�j
	 * @throws Exception
	 *             �Ϗ��悪�X���[�����O
	 */
	protected JoinResult confMainData(Housing housing, String sysHousingCd,
			boolean full) throws Exception {

		return super.confMainData(housing, sysHousingCd, full);
	}

	/**
	 * �����摜�����擾����B<br/>
	 * �擾�������ʂ� housing �I�u�W�F�N�g�֊i�[����B<br/>
	 * <br/>
	 *
	 * @param housing
	 *            �l�̐ݒ��ƂȂ� Housing �I�u�W�F�N�g
	 * @param sysHousingCd
	 *            �擾�ΏۃV�X�e������CD
	 *
	 * @return �擾����
	 */
	@Override
	protected List<HousingImageInfo> confHousingImage(Housing housing,
			String sysHousingCd) {

		// �R�A�ƈႤ�̂̓\�[�g���ł��I
		// �����摜�����擾����B
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", sysHousingCd);
		criteria.addOrderByClause("sortOrder");
		criteria.addOrderByClause("imageType");
		criteria.addOrderByClause("divNo");
		List<HousingImageInfo> list = this.HousingImageInfoDAO
				.selectByFilter(criteria);

		housing.setHousingImageInfos(list);

		return list;

	}

	/**
	 * �����ݔ������擾����B<br/>
	 * �擾���ʂ́ALinkedHashMap �ɕϊ����� housing �֊i�[�����B<br/>
	 * �EKey = �ݔ�CD<br/>
	 * �EValue = �ݔ��}�X�^<br/>
	 * <br/>
	 *
	 * @param housing
	 *            �l�̐ݒ��ƂȂ� Housing �I�u�W�F�N�g
	 * @param sysHousingCd
	 *            �擾�ΏۃV�X�e������CD
	 *
	 * @return �擾����
	 */
	protected Map<String, jp.co.transcosmos.dm3.core.vo.EquipMst> confHousingEquip(
			Housing housing, String sysHousingCd) {

		return super.confHousingEquip(housing, sysHousingCd);
	}

	/**
	 * �����g���������Ɋi�[����Ă������ Map �I�u�W�F�N�g�ɕϊ����Ċi�[����B<br/>
	 * Map �I�u�W�F�N�g�̍\���́A�ȉ��̒ʂ�B<br/>
	 * �EKey = �����g�����̃J�e�S�����icategory�j �EValue = �J�e�S���̊Y������AKey�l���ݒ肳�ꂽ Map
	 * �I�u�W�F�N�g�iKey = keyName��AValue = dataValue��j <br/>
	 *
	 * @param housing
	 *            �i�[��ƂȂ� Housing �I�u�W�F�N�g
	 * @param sysHousingCd
	 *            �擾�ΏۃV�X�e������CD
	 *
	 */
	protected void confHousingExtInfo(Housing housing, String sysHousingCd) {

		super.confHousingExtInfo(housing, sysHousingCd);

	}

	/**
	 * �p�����[�^�œn���ꂽ Map �̏��ŕ����g�����������X�V����B�i�V�X�e������CD �P�ʁj<br/>
	 * �X�V�́ADelete & Insert �ŏ�������B<br/>
	 * <br/>
	 * inputData �� Map �̍\���͈ȉ��̒ʂ�B<br/>
	 * �Ekey = �J�e�S���� �i�g���������́Acategory �Ɋi�[����l�j �Evalue = �l���i�[���ꂽ Map �I�u�W�F�N�g
	 * inputData �� value �Ɋi�[����� Map �̍\���͈ȉ��̒ʂ�B<br/>
	 * �Ekey = Key�� �i�g���������́Akey_name �Ɋi�[����l�j �Evalue = ���͒l �i�g���������́Adata_value
	 * �Ɋi�[����l�j <br/>
	 *
	 * @param sysHousingCd
	 *            �X�V�ΏۃV�X�e������CD
	 * @param inputData
	 *            �o�^���ƂȂ� Map �I�u�W�F�N�g
	 * @param editUserId
	 *            ���O�C�����[�U�[ID �i�X�V���p�j
	 *
	 * @exception Exception
	 *                �����N���X�ɂ��X���[�����C�ӂ̗�O
	 * @exception NotFoundException
	 *                �e�ƂȂ镨����{��񂪑��݂��Ȃ��ꍇ
	 */
	@Override
	public void updExtInfo(String sysHousingCd,
			Map<String, Map<String, String>> inputData, String editUserId)
			throws Exception, NotFoundException {

		super.updExtInfo(sysHousingCd, inputData, editUserId);
	}

	/**
	 * �p�����[�^�œn���ꂽ�V�X�e������CD �ɊY�����镨���g�����������폜����B�i�V�X�e������CD �P�ʁj<br/>
	 * <br/>
	 *
	 * @param sysHousingCd
	 *            �폜�ΏۃV�X�e������CD
	 * @param editUserId
	 *            ���O�C�����[�U�[ID �i�X�V���p�j
	 *
	 */
	@Override
	public void delExtInfo(String sysHousingCd, String editUserId) {

		super.delExtInfo(sysHousingCd, editUserId);

	}

	/**
	 * �p�����[�^�œn���ꂽ Map �̏��ŕ����g�����������X�V����B�i�J�e�S���[ �P�ʁj<br/>
	 * �X�V�́ADelete & Insert �ŏ�������B<br/>
	 * <br/>
	 * inputData �� Map �̍\���͈ȉ��̒ʂ�B<br/>
	 * �Ekey = Key�� �i�g���������́Akey_name �Ɋi�[����l�j �Evalue = ���͒l �i�g���������́Adata_value
	 * �Ɋi�[����l�j <br/>
	 * <br/>
	 *
	 * @param sysHousingCd
	 *            �X�V�ΏۃV�X�e������CD
	 * @param category
	 *            �X�V�ΏۃJ�e�S����
	 * @param inputData
	 *            �o�^���ƂȂ� Map �I�u�W�F�N�g
	 * @param editUserId
	 *            ���O�C�����[�U�[ID �i�X�V���p�j
	 *
	 * @exception Exception
	 *                �����N���X�ɂ��X���[�����C�ӂ̗�O
	 * @exception NotFoundException
	 *                �e�ƂȂ镨����{��񂪑��݂��Ȃ��ꍇ
	 */
	@Override
	public void updExtInfo(String sysHousingCd, String category,
			Map<String, String> inputData, String editUserId) throws Exception,
			NotFoundException {

		super.updExtInfo(sysHousingCd, category, inputData, editUserId);

	}

	/**
	 * �p�����[�^�œn���ꂽ�V�X�e������CD �ɊY�����镨���g�����������폜����B�i�J�e�S���[ �P�ʁj<br/>
	 * <br/>
	 *
	 * @param sysHousingCd
	 *            �폜�ΏۃV�X�e������CD
	 * @param category
	 *            �폜�ΏۃJ�e�S����
	 * @param editUserId
	 *            ���O�C�����[�U�[ID �i�X�V���p�j
	 *
	 */
	@Override
	public void delExtInfo(String sysHousingCd, String category,
			String editUserId) {

		super.delExtInfo(sysHousingCd, category, editUserId);

	}

	/**
	 * �p�����[�^�œn���ꂽ Map �̏��ŕ����g�����������X�V����B�iKey �P�ʁj<br/>
	 * �X�V�́ADelete & Insert �ŏ�������B<br/>
	 * <br/>
	 *
	 * @param sysHousingCd
	 *            �X�V�ΏۃV�X�e������CD
	 * @param category
	 *            �X�V�ΏۃJ�e�S����
	 * @param key
	 *            �X�V�Ώ�Key
	 * @param value
	 *            �X�V����l
	 * @param editUserId
	 *            ���O�C�����[�U�[ID �i�X�V���p�j
	 *
	 * @exception Exception
	 *                �����N���X�ɂ��X���[�����C�ӂ̗�O
	 * @exception NotFoundException
	 *                �e�ƂȂ镨����{��񂪑��݂��Ȃ��ꍇ
	 */
	@Override
	public void updExtInfo(String sysHousingCd, String category, String key,
			String value, String editUserId) throws Exception,
			NotFoundException {

		super.updExtInfo(sysHousingCd, category, key, value, editUserId);

	}

	/**
	 * �p�����[�^�œn���ꂽ�V�X�e������CD �ɊY�����镨���g�����������폜����B�i�L�[ �P�ʁj<br/>
	 * <br/>
	 *
	 * @param sysHousingCd
	 *            �폜�ΏۃV�X�e������CD
	 * @param category
	 *            �폜�ΏۃJ�e�S����
	 * @param category
	 *            �폜�Ώ� Key
	 * @param editUserId
	 *            ���O�C�����[�U�[ID �i�X�V���p�j
	 *
	 */
	@Override
	public void delExtInfo(String sysHousingCd, String category, String key,
			String editUserId) {

		super.delExtInfo(sysHousingCd, category, key, editUserId);

	}

	/**
	 * ������{���̃^�C���X�^���v�����X�V����B<br/>
	 * <br/>
	 *
	 * @param sysHousingCd
	 *            �X�V�ΏۃV�X�e������CD
	 * @param editUserId
	 *            �X�V��ID
	 */
	protected void updateEditTimestamp(String sysHousingCd, String editUserId) {

		super.updateEditTimestamp(sysHousingCd, editUserId);

	}

	/**
	 * ����J�ƂȂ�����������œn���ꂽ���������I�u�W�F�N�g�ɐݒ肷��B<br/>
	 * ���̃��\�b�h�́AsearchHousingPk()�AsearchBuilding() �� full �p�����[�^�� false �̏ꍇ��
	 * ���s�����B<br/>
	 * <br/>
	 * �f�t�H���g�d�l�Ƃ��Ē��o�ΏۂɂȂ�ɂ͈ȉ��̏����𖞂����K�v������B<br/>
	 * <ul>
	 * <li>���J�����ł��鎖�B�i����J�t���O hidden_flg = 1 �̕����X�e�[�^�X��񂪑��݂��Ȃ����B�j</li>
	 * </ul>
	 * <br/>
	 * �������J�X�^�}�C�Y����ꍇ�́A���̃��\�b�h���I�[�o�[���C�h���鎖�B<br/>
	 * <br/>
	 *
	 * @param criteria
	 *            �����Ŏd�l���錟���I�u�W�F�N�g
	 *
	 */
	protected void addNegativeFilter(DAOCriteria criteria) {

		super.addNegativeFilter(criteria);

	}

	/**
	 * �������I�u�W�F�N�g�̃C���X�^���X�𐶐�����B<br/>
	 * �����A�J�X�^�}�C�Y�ŕ��������\������e�[�u����ǉ������ꍇ�A���̃��\�b�h���I�[�o�[���C�h���鎖�B<br/>
	 * <br/>
	 *
	 * @return PanaHousing �̃C���X�^���X
	 */
	public PanaHousing createHousingInstace() {
		return new PanaHousing();
	}

	/**
	 * �X�e�[�^�X�̐V�K���s��<br/>
	 * <br/>
	 *
	 * @param form
	 *            �X�e�[�^�X�̓��͒l���i�[���� Form �I�u�W�F�N�g
	 *
	 */
	public String addHousingStatus(PanaHousingStatusForm form)
			throws Exception {

		// �V�K�o�^�����̏ꍇ�A���̓t�H�[���̒l��ݒ肷��o���[�I�u�W�F�N�g�𐶐�����B
		// �o���[�I�u�W�F�N�g�́A�t�@�N�g���[���\�b�h�ȊO�ł͐������Ȃ����B
		// �i�p�����ꂽ�o���[�I�u�W�F�N�g���g�p����Ȃ��Ȃ�ׁB�j
		HousingStatusInfo housingStatusInfo = new HousingStatusInfo();

		// �t�H�[���̓��͒l���o���[�I�u�W�F�N�g�ɐݒ肷��B
		form.copyToHousingStatusInfo(housingStatusInfo);

		this.housingStatusInfoDAO
				.insert(new HousingStatusInfo[] { housingStatusInfo });

		return housingStatusInfo.getSysHousingCd();
	}

	/**
	 * �X�e�[�^�X�̍X�V���s��<br/>
	 * <br/>
	 *
	 * @param form
	 *            �X�e�[�^�X�̓��͒l���i�[���� Form �I�u�W�F�N�g
	 * @param editUserId
	 *            ���O�C�����[�U�[ID �i�X�V���p�j
	 * @exception NotFoundException
	 *                �X�V�Ώۂ����݂��Ȃ��ꍇ
	 */
	public void updateHousingStatus(PanaHousingStatusForm form,
			String editUserId) throws Exception, NotFoundException {
		// �X�V�����̏ꍇ�A�X�V�Ώۃf�[�^���擾����B
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", form.getSysHousingCd());

		HousingStatusInfo housingStatusInfo = (HousingStatusInfo) this.housingStatusInfoDAO
				.selectByPK(form.getSysHousingCd());

		// �Y������f�[�^�����݂��Ȃ��ꍇ�́A��O���X���[����B
		if (housingStatusInfo == null) {
			throw new NotFoundException();
		}

		// ���t�H�[���v���������擾���A���͂����l�ŏ㏑������B
		form.copyToHousingStatusInfo(housingStatusInfo);

		// ���t�H�[���v�������̍X�V
		this.housingStatusInfoDAO
				.update(new HousingStatusInfo[] { housingStatusInfo });

		// �������̃^�C���X�^���v���X�V
		updateEditTimestamp(form.getSysHousingCd(), editUserId);
	}

	/**
	 * �p�����[�^�œn���ꂽ�V�X�e������CD �ɊY������Z��f�f�����폜����B�i�V�X�e������CD �P�ʁj<br/>
	 * <br/>
	 *
	 * @param sysHousingCd
	 *            �폜�ΏۃV�X�e������CD
	 *
	 * @exception Exception
	 *                �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	public void delHousingInspection(String sysHousingCd) throws Exception {

		// �폜��������
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", sysHousingCd);
		this.housingInspectionDAO.deleteByFilter(criteria);

	}

	/**
	 * �Z��f�f����V�K�ǉ�����<br/>
	 * <br/>
	 *
	 * @param PanaHousingInspectionForm
	 *            ���͒l���i�[���� Form �I�u�W�F�N�g
	 * @param idx
	 *            ���͒l���i�[���� Form �I�u�W�F�N�g
	 *
	 */
	public String addHousingInspection(PanaHousingInspectionForm form, int idx)
			throws Exception {

		// �t�H�[���̓��͒l���o���[�I�u�W�F�N�g�ɐݒ肷��B
		HousingInspection housingInspection = new HousingInspection();
		form.copyToHousingInspection(housingInspection, idx);

		this.housingInspectionDAO
				.insert(new HousingInspection[] { housingInspection });

		return housingInspection.getSysHousingCd();
	}

	/**
	 * �Z��f�f�����擾����B<br/>
	 * <br/>
	 *
	 * @param sysHousingCd
	 *            �V�X�e������CD
	 *
	 * @exception Exception
	 *                �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	public List<HousingInspection> searchHousingInspection(String sysHousingCd)
			throws Exception {

		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", sysHousingCd);

		List<HousingInspection> housingInspection = this.housingInspectionDAO
				.selectByFilter(criteria);

		return housingInspection;
	}

	/**
	 * �p�����[�^�œn���ꂽ �����ڍ׏����X�V����B<br/>
	 * <br/>
	 *
	 * @param PanaHousingInfoForm
	 *            �����ڍ׏��
	 * @param editUserId
	 *            ���[�UID
	 *
	 */
	public void updateBuildingDtlInfo(PanaHousingInfoForm inputForm,
			String editUserId) {
		// �X�V�����̏ꍇ�A�X�V�Ώۃf�[�^���擾����B
		BuildingDtlInfo buildingDtlInfo = this.buildingDtlInfoDAO
				.selectByPK(inputForm.getSysBuildingCd());

		// �Y������f�[�^�����݂��Ȃ��ꍇ�́A��O���X���[����B
		if (buildingDtlInfo == null) {
			BuildingDtlInfo newBuildingDtlInfo = inputForm.newToBuildingDtlInfo();
			// �擾������L�[�l�Ń��t�H�[���v��������o�^
			try {
				this.buildingDtlInfoDAO
						.insert(new BuildingDtlInfo[] { newBuildingDtlInfo });
			} catch (DataIntegrityViolationException e) {
				// �����A�V�K�o�^���ɐe���R�[�h�����݂��Ȃ��ꍇ�͗�O���X���[����B
				// �����ڍ׏��̂ݑ��݂���P�[�X��DB�̐����L�蓾�Ȃ��̂ŁA�V�K�o�^���̂ݐ��䂷��B
				e.printStackTrace();
				throw new NotFoundException();
			}
		} else {
			// �X�V�Ώۃf�[�^���擾�o�����ꍇ�͍X�V����B

			// �擾�����o���[�I�u�W�F�N�g�� Form �̒l��ݒ肷��B
			inputForm.copyToBuildingDtlInfo(buildingDtlInfo);
			//
			this.buildingDtlInfoDAO
					.update(new BuildingDtlInfo[] { buildingDtlInfo });
		}

		// �������̃^�C���X�^���v���X�V
		updateEditTimestamp(inputForm.getSysHousingCd(), editUserId);

	}

	/**
	 * ���N�G�X�g�p�����[�^�œn���ꂽ�������CD �i��L�[�l�j�ɊY������ݔ��}�X�^���𕜋A����B<br/>
	 * �����Y���f�[�^��������Ȃ��ꍇ�Anull �𕜋A����B<br/>
	 * <br/>
	 *
	 * @param housingKindCd
	 *            �������CD
	 *
	 * @return�@DB ����擾�����ݔ��}�X�^�����i�[�������X�g
	 *
	 * @exception Exception
	 *                �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	public List<JoinResult> searchEquipMst(String housingKindCd)
			throws Exception {

		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("housingKindCd", housingKindCd);
		criteria.addOrderByClause("sortOrder");

		List<JoinResult> equipMstList = this.equipListDAO
				.selectByFilter(criteria);

		return equipMstList;
	}

	/**
	 * CSV�o�͏����������A���ʃ��X�g�𕜋A����B<br/>
	 * �����œn���ꂽ searchForm �p�����[�^�̒l�Ō��������𐶐����ACSV�o�͏�����������B<br/>
	 * �������ʂ� searchForm �I�u�W�F�N�g�Ɋi�[����A�擾�����Y��������߂�l�Ƃ��ĕ��A����B<br/>
	 * <br/>
	 *
	 * @param searchForm
	 *            ���������A����сA�������ʂ̊i�[�I�u�W�F�N�g
	 *
	 * @return �Y������
	 *
	 * @exception Exception
	 *                �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	public void searchCsvHousing(PanaHousingSearchForm searchForm,
			HttpServletResponse response,
			PanaHousingPartThumbnailProxy panaHousingManager, PanaCommonManage panamCommonManager) throws Exception {
		this.panaSearchHousingDAO.panaSearchHousing(searchForm, response,
				panaHousingManager, panamCommonManager);
	}

	/**
	 * �s�撬���l���擾����B<br/>
	 * <br/>
	 *
	 * @param housing
	 *            �X�V�ΏۂƂȂ镨���I�u�W�F�N�g
	 *
	 * @return ���H���ꂽ�p�X��
	 * @throws Exception
	 */
	public int searchHousingInfo(PanaHousingSearchForm areaForm)
			throws Exception {

		List<Housing> housingCount = this.panaSearchHousingDAO.panaSearchHousing(areaForm);

		return housingCount.size();
	}

}
