package jp.co.transcosmos.dm3.core.model.housing;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import jp.co.transcosmos.dm3.core.constant.CommonParameters;
import jp.co.transcosmos.dm3.core.model.BuildingManage;
import jp.co.transcosmos.dm3.core.model.HousingManage;
import jp.co.transcosmos.dm3.core.model.exception.NotFoundException;
import jp.co.transcosmos.dm3.core.model.housing.dao.SearchHousingDAO;
import jp.co.transcosmos.dm3.core.model.housing.form.HousingDtlForm;
import jp.co.transcosmos.dm3.core.model.housing.form.HousingEquipForm;
import jp.co.transcosmos.dm3.core.model.housing.form.HousingForm;
import jp.co.transcosmos.dm3.core.model.housing.form.HousingImgForm;
import jp.co.transcosmos.dm3.core.model.housing.form.HousingSearchForm;
import jp.co.transcosmos.dm3.core.util.ImgUtils;
import jp.co.transcosmos.dm3.core.util.ValueObjectFactory;
import jp.co.transcosmos.dm3.core.util.imgUtils.ImgInfo;
import jp.co.transcosmos.dm3.core.vo.BuildingInfo;
import jp.co.transcosmos.dm3.core.vo.EquipMst;
import jp.co.transcosmos.dm3.core.vo.HousingDtlInfo;
import jp.co.transcosmos.dm3.core.vo.HousingEquipInfo;
import jp.co.transcosmos.dm3.core.vo.HousingExtInfo;
import jp.co.transcosmos.dm3.core.vo.HousingImageInfo;
import jp.co.transcosmos.dm3.core.vo.HousingInfo;
import jp.co.transcosmos.dm3.core.vo.HousingStatusInfo;
import jp.co.transcosmos.dm3.dao.DAO;
import jp.co.transcosmos.dm3.dao.DAOCriteria;
import jp.co.transcosmos.dm3.dao.DAOCriteriaWhereClause;
import jp.co.transcosmos.dm3.dao.FormulaUpdateExpression;
import jp.co.transcosmos.dm3.dao.JoinResult;
import jp.co.transcosmos.dm3.dao.NotEnoughRowsException;
import jp.co.transcosmos.dm3.dao.OrCriteria;
import jp.co.transcosmos.dm3.dao.UpdateExpression;
import jp.co.transcosmos.dm3.dao.UpdateValue;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.util.StringUtils;

/**
 * �������p Model �N���X.
 * <p>
 * <pre>
 * �S����		�C����		�C�����e
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.03.12	�V�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 * ���̃N���X�̓V���O���g���� DI �R���e�i�ɒ�`�����̂ŁA�X���b�h�Z�[�t�ł��鎖�B<br/>
 * 
 */
public class HousingManageImpl implements HousingManage {

	private static final Log log = LogFactory.getLog(HousingManageImpl.class);
	
	/** Value �I�u�W�F�N�g�� Factory */
	protected ValueObjectFactory valueObjectFactory;

	/** ������񃁃��e�i���X�� model */
	protected BuildingManage buildingManager;

	/** ������{���pDAO */
	protected DAO<HousingInfo> housingInfoDAO;

	/** ������{�ڍ׏��pDAO */
	protected DAO<HousingDtlInfo> housingDtlInfoDAO;

	/** �����X�e�[�^�X���pDAO */
	protected DAO<HousingStatusInfo> housingStatusInfoDAO;
	
	/** �����ݔ����pDAO�i�����e�i���X�p�j */
	protected DAO<HousingEquipInfo> housingEquipInfoDAO;

	/**
	 * �����ݔ����pDAO�i���擾�p�B�}�X�^�[�̕\�����Ń\�[�g�j
	 * �����ݔ����A�ݔ��}�X�^���������A�ݔ��}�X�^�̕\�����Ń\�[�g��������<br/>
	 */
	protected DAO<JoinResult> housingEquipListDAO;

	/**
	 * ����������������� DAO�i���擾�p�B�}�X�^�[�̕\�����Ń\�[�g�j
	 * �����������������A�����������}�X�^���������A�����������}�X�^�̕\�����Ń\�[�g��������<br/>
	 */
	protected DAO<JoinResult> housingPartListDAO;

	/** �����摜���DAO */
	protected DAO<HousingImageInfo> HousingImageInfoDAO;
	
	/** �����g�������pDAO */
	protected DAO<HousingExtInfo> housingExtInfoDAO;
	
	/** ������{���ƂP�΂P�̊֌W�ɂ���֘A�e�[�u�����擾����ׂ� JoinDAO (�������\������A�S�f�[�^�̎擾�p) */
	protected DAO<JoinResult> housingMainJoinDAO;

	/** ���������p DAO */
	protected SearchHousingDAO searchHousingDAO;
	
	/** �T���l�C���摜�֘A���[�e�B���e�B */
	protected ImgUtils imgUtils;

	/** ���ʃp�����[�^�I�u�W�F�N�g */
	protected CommonParameters commonParameters;


	
	/**
	 * Value �I�u�W�F�N�g�� Factory ��ݒ肷��B<br/>
	 * <br/>
	 * @param valueObjectFactory Value �I�u�W�F�N�g�� Factory
	 */
	public void setValueObjectFactory(ValueObjectFactory valueObjectFactory) {
		this.valueObjectFactory = valueObjectFactory;
	}

	/**
	 * ������񃁃��e�i���X�p model ��ݒ肷��B<br/>
	 * <br/>
	 * @param buildingManage ������񃁃��e�i���X�p model
	 */
	public void setBuildingManager(BuildingManage buildingManager) {
		this.buildingManager = buildingManager;
	}

	/**
	 * ������{���pDAO ��ݒ肷��B<br/>
	 * <br/>
	 * @param housingInfoDAO ������{���p DAO
	 */
	public void setHousingInfoDAO(DAO<HousingInfo> housingInfoDAO) {
		this.housingInfoDAO = housingInfoDAO;
	}

	/**
	 * �����ڍ׏��DAO ��ݒ肷��B<br/>
	 * <br/>
	 * @param housingDtlInfoDAO �����ڍ׏��DAO
	 */
	public void setHousingDtlInfoDAO(DAO<HousingDtlInfo> housingDtlInfoDAO) {
		this.housingDtlInfoDAO = housingDtlInfoDAO;
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
	 * �����ݔ����pDAO ��ݒ肷��B<br/>
	 * <br/>
	 * @param housingEquipInfo �����ݔ����pDAO
	 */
	public void setHousingEquipInfoDAO(DAO<HousingEquipInfo> housingEquipInfoDAO) {
		this.housingEquipInfoDAO = housingEquipInfoDAO;
	}

	/**
	 * �����ݔ����pDAO�i���擾�p�B�}�X�^�[�̕\�����Ń\�[�g�j ��ݒ肷��B<br/>
	 * <br/>
	 * @param housingEquipListDAO �����ݔ����pDAO�i���擾�p�B�}�X�^�[�̕\�����Ń\�[�g�j
	 */
	public void setHousingEquipListDAO(DAO<JoinResult> housingEquipListDAO) {
		this.housingEquipListDAO = housingEquipListDAO;
	}

	/**
	 * ���������������pDAO�i���擾�p�B�}�X�^�[�̕\�����Ń\�[�g�j ��ݒ肷��B<br/>
	 * <br/>
	 * @return ���������������pDAO�i���擾�p�B�}�X�^�[�̕\�����Ń\�[�g�j
	 */
	public DAO<JoinResult> getHousingPartListDAO() {
		return housingPartListDAO;
	}

	/**
	 * �����摜���p DAO ��ݒ肷��B<br/>
	 * <br/>
	 * @param housingImageInfoDAO �����摜���p DAO
	 */
	public void setHousingImageInfoDAO(DAO<HousingImageInfo> housingImageInfoDAO) {
		HousingImageInfoDAO = housingImageInfoDAO;
	}

	/**
	 * �����g�������pDAO ��ݒ肷��B<br/>
	 * <br/>
	 * @param housingExtInfoDAO �����g�������p DAO
	 */
	public void setHousingExtInfoDAO(DAO<HousingExtInfo> housingExtInfoDAO) {
		this.housingExtInfoDAO = housingExtInfoDAO;
	}

	/**
	 * �������\������A�S�f�[�^�̎擾�p�� DAO ��ݒ肷��B<br/>
	 * <br/>
	 * @param housingMainJoinDAO �S�f�[�^�̎擾�p�� DAO
	 */
	public void setHousingMainJoinDAO(DAO<JoinResult> housingMainJoinDAO) {
		this.housingMainJoinDAO = housingMainJoinDAO;
	}

	/**
	 * ���������p DAO ��ݒ肷��B<br/>
	 * <br/>
	 * @param searchHousingDAO ���������p DAO
	 */
	public void setSearchHousingDAO(SearchHousingDAO searchHousingDAO) {
		this.searchHousingDAO = searchHousingDAO;
	}

	/**
	 * �摜�t�@�C�����[�e�B���e�B��ݒ肷��B<br/>
	 * <br/>
	 * @param imgUtils �摜�t�@�C�����[�e�B���e�B
	 */
	public void setImgUtils(ImgUtils imgUtils) {
		this.imgUtils = imgUtils;
	}

	/**
	 * ���ʃp�����[�^�I�u�W�F�N�g��ݒ肷��B<br/>
	 * <br/>
	 * @param commonParameters ���ʃp�����[�^�I�u�W�F�N�g
	 */
	public void setCommonParameters(CommonParameters commonParameters) {
		this.commonParameters = commonParameters;
	}

	/**
	 * �p�����[�^�œn���ꂽ Form �̏��ŕ�����{����V�K�o�^����B<br/>
	 * �o���f�[�V�������̃`�F�b�N�����͌Ăяo�����ŗ\�ߎ��{���Ă������B<br/>
	 * ���\���p�A�C�R���͐ݔ����ōX�V����̂ŁA���̃��\�b�h�ł͍X�V����Ȃ��B<br/>
	 * <br/>
	 * @param inputForm ������{���̓��͒l���i�[���� Form �I�u�W�F�N�g
	 * @param editUserId ���O�C�����[�U�[ID �i�X�V���p�j
	 * 
	 * @return �̔Ԃ��ꂽ�V�X�e������CD
	 */
	@Override
	public String addHousing(HousingForm inputForm, String editUserId)
			throws Exception {

		// ��̃o���[�I�u�W�F�N�g���쐬���t�H�[���̓��͒l��ݒ肷��B
		HousingInfo housingInfo = (HousingInfo)this.valueObjectFactory.getValueObject("HousingInfo");
		inputForm.copyToHousingInfo(housingInfo);

		// �^�C���X�^���v����ݒ�
		Date sysDate = new Date();
		housingInfo.setInsDate(sysDate);
		housingInfo.setInsUserId(editUserId);
		housingInfo.setUpdDate(sysDate);
		housingInfo.setUpdUserId(editUserId);

		// �f�[�^��ǉ�
		try {
			this.housingInfoDAO.insert(new HousingInfo[]{housingInfo});

		} catch (DataIntegrityViolationException e) {
			// ���̗�O�́A�o�^���O�Ɉˑ���ƂȂ錚����񂪍폜���ꂽ�ꍇ�ɔ�������B
			log.warn(e.getMessage(), e);
			throw new NotFoundException();
		}

		// �̔Ԃ��ꂽ�V�X�e������CD �𕜋A
		return housingInfo.getSysHousingCd();
		
	}



	/**
	 * �p�����[�^�œn���ꂽ Form �̏��ŕ�����{�����X�V����B<br/>
	 * �o���f�[�V�������̃`�F�b�N�����͌Ăяo�����ŗ\�ߎ��{���Ă������B<br/>
	 * HousingForm �� sysHousingCd �v���p�e�B�ɐݒ肳�ꂽ�l����L�[�l�Ƃ��čX�V����B<br/>
	 * ���\���p�A�C�R���͐ݔ����ōX�V����̂ŁA���̃��\�b�h�ł͍X�V����Ȃ��B<br/>
	 * <br/>
	 * @param inputForm ������{���̓��͒l���i�[���� Form �I�u�W�F�N�g
	 * @param editUserId ���O�C�����[�U�[ID �i�X�V���p�j
	 * 
	 * @exception NotFoundException �X�V�Ώۂ����݂��Ȃ��ꍇ
	 */
	@Override
	public void updateHousing (HousingForm inputForm, String editUserId)
			throws Exception, NotFoundException {

		// �X�V�Ώۂ̌��݂̒l���擾����B
		HousingInfo housingInfo = this.housingInfoDAO.selectByPK(inputForm.getSysHousingCd());
		if (housingInfo == null){
			// �X�V�Ώۂ��擾�o���Ȃ��ꍇ�͏������p���ł��Ȃ��̂ŗ�O���X���[���ďI������B
			throw new NotFoundException();
		}

		// �擾�����l�� Form �̓��͒l�ŏ㏑������B
		inputForm.copyToHousingInfo(housingInfo);

		// �^�C���X�^���v����ݒ�
		Date sysDate = new Date();
		housingInfo.setUpdDate(sysDate);
		housingInfo.setUpdUserId(editUserId);

		// �f�[�^���X�V
		try {
			this.housingInfoDAO.update(new HousingInfo[]{housingInfo});
		} catch (DataIntegrityViolationException e) {
			// ���̗�O�́A�o�^���O�ɕύX��ƂȂ錚����񂪍폜���ꂽ�ꍇ�ɔ�������B
			log.warn(e.getMessage(), e);
			throw new NotFoundException();
		}
	}



	/**
	 * �p�����[�^�œn���ꂽ Form �̏��ŕ�����{�����폜����B<br/>
	 * �o���f�[�V�������̃`�F�b�N�����͌Ăяo�����ŗ\�ߎ��{���Ă������B<br/>
	 * HousingForm �� sysHousingCd �v���p�e�B�ɐݒ肳�ꂽ�l����L�[�l�Ƃ��č폜����B
	 * �܂��A�폜�Ώۃ��R�[�h�����݂��Ȃ��ꍇ�ł�����I���Ƃ��Ĉ������B<br/>
	 * �폜���͕�����{���̏]���\���폜�ΏۂƂ���B<br/>
	 * �֘A����摜�t�@�C���̍폜�� Proxy �N���X���őΉ�����̂ŁA���̃N���X���ɂ͎������Ȃ��B<br/>
	 * <br/>
	 * @param inputForm �폜�ΏۂƂȂ� sysHousingCd ���i�[���� Form �I�u�W�F�N�g
	 * @param editUserId �폜�S����
	 */
	@Override
	public void delHousingInfo(HousingForm inputForm, String editUserId) {

		// ��L�[�l�ō폜���������s
		this.housingInfoDAO.deleteByPK(new String[]{inputForm.getSysHousingCd()});

		// �ˑ��\�͐���������ō폜���鎖��z�肵�Ă���̂ŁA�����I�ȍ폜�͍s��Ȃ��B
		// �������A�����X�e�[�^�X���͕����I�Ȉˑ��֌W�����݂��Ȃ��̂ō폜����B
		this.housingStatusInfoDAO.deleteByPK(new String[]{inputForm.getSysHousingCd()});

		// �摜�t�@�C���� Proxy ���ō폜����̂ł����ł͑Ή����Ȃ��B

	}

	

	/**
	 * �p�����[�^�œn���ꂽ Form �̏��ŕ����ڍ׏����X�V����B<br/>
	 * �Y�����镨���ڍ׏�񂪑��݂��Ȃ��Ă��A�Y�����镨����{��񂪑��݂���ꍇ�A���R�[�h��V����
	 * �ǉ����ĕ����ڍ׏���o�^����B<br/>
	 * �o���f�[�V�������̃`�F�b�N�����͌Ăяo�����ŗ\�ߎ��{���Ă������B<br/>
	 * HousingDtlForm �� sysHousingCd �v���p�e�B�ɐݒ肳�ꂽ�l����L�[�l�Ƃ��čX�V����B<br/>
	 * <br/>
	 * @param inputForm �����ڍ׏��̓��͒l���i�[���� Form �I�u�W�F�N�g
	 * @param editUserId ���O�C�����[�U�[ID �i�X�V���p�j
	 * 
	 */
	@Override
	public void updateHousingDtl(HousingDtlForm inputForm, String editUserId) {

		// �X�V�Ώۂ̌��݂̒l���擾����B
		HousingDtlInfo housingDtlInfo = this.housingDtlInfoDAO.selectByPK(inputForm.getSysHousingCd());

		if (housingDtlInfo == null){
			// �X�V�Ώۃf�[�^���擾�o���Ȃ��ꍇ�͐V�K�o�^����B

			// ��̃o���[�I�u�W�F�N�g���쐬���t�H�[���̓��͒l��ݒ肷��B
			housingDtlInfo = (HousingDtlInfo)this.valueObjectFactory.getValueObject("HousingDtlInfo");
			inputForm.copyToHousingDtlInfo(housingDtlInfo);
			// �f�[�^��ǉ�
			try {
				this.housingDtlInfoDAO.insert(new HousingDtlInfo[]{housingDtlInfo});
			} catch (DataIntegrityViolationException e){
				// �����A�V�K�o�^���ɐe���R�[�h�����݂��Ȃ��ꍇ�͗�O���X���[����B
				// �����ڍ׏��̂ݑ��݂���P�[�X��DB�̐����L�蓾�Ȃ��̂ŁA�V�K�o�^���̂ݐ��䂷��B
				log.warn(e.getMessage(), e);
				throw new NotFoundException();
			}

		} else {
			// �X�V�Ώۃf�[�^���擾�o�����ꍇ�͍X�V����B

			// �擾�����o���[�I�u�W�F�N�g�� Form �̒l��ݒ肷��B
			inputForm.copyToHousingDtlInfo(housingDtlInfo);

			// �f�[�^���X�V
			this.housingDtlInfoDAO.update(new HousingDtlInfo[]{housingDtlInfo});
		}

		// �������̃^�C���X�^���v���X�V
		updateEditTimestamp(inputForm.getSysHousingCd(), editUserId);

	}



	/**
	 * �p�����[�^�œn���ꂽ Form �̏��ŕ����ݔ������X�V����B<br/>
	 * �ݔ����� DELETE & INSERT �ňꊇ�X�V����B<br/>
	 * �o���f�[�V�������̃`�F�b�N�����͌Ăяo�����ŗ\�ߎ��{���Ă������B<br/>
	 * HousingDtlForm �� sysHousingCd �v���p�e�B�ɐݒ肳�ꂽ�l����L�[�l�Ƃ��čX�V����B<br/>
	 * <br/>
	 * ����{�d�l�ł́A�ݔ�����\���p�A�C�R���Ƃ��ēo�^����B�@���ׁ̈A���̃��\�b�h���ŕ�����{���
	 * �̃A�C�R�������X�V���Ă���B<br/>
	 * <br/>
	 * @param inputForm �����ڍ׏��̓��͒l���i�[���� Form �I�u�W�F�N�g
	 * @param editUserId ���O�C�����[�U�[ID �i�X�V���p�j
	 * 
	 */
	@Override
	public void updateHousingEquip(HousingEquipForm inputForm, String editUserId){

		// �����ݔ������폜����B
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", inputForm.getSysHousingCd());
		this.housingEquipInfoDAO.deleteByFilter(criteria);


		List<HousingEquipInfo> list = new ArrayList<>();			// �ݔ����o�^�p
		List<String> iconCdList = new ArrayList<>();				// ICON ���o�^�p

		// �����ݔ�����ǉ�����B
		if (inputForm.getEquipCd() != null ) {
			

			for (String equipCd : inputForm.getEquipCd()){

				if (!StringValidateUtil.isEmpty(equipCd)) {

					// �o���[�I�u�W�F�N�g�𐶐����Ēl��ݒ肷��B
					HousingEquipInfo housingEquipInfo = (HousingEquipInfo) this.valueObjectFactory.getValueObject("HousingEquipInfo");
					housingEquipInfo.setSysHousingCd(inputForm.getSysHousingCd());
					housingEquipInfo.setEquipCd(equipCd);

					list.add(housingEquipInfo);
					iconCdList.add(equipCd);
				}
			}

			if (list.size() > 0) {
				try {
					// �ݔ�CD ���ݒ肳��Ă���ꍇ�̓��R�[�h��ǉ�����B
					this.housingEquipInfoDAO.insert(list.toArray(new HousingEquipInfo[list.size()]));
				} catch (DataIntegrityViolationException e) {
					// �ǉ����ɐe���R�[�h���폜���ꂽ�ꍇ�ANotFoundException �̗�O���X���[����B
					// �Ⴆ�΁A�����̍폜�����Ƌ��������ꍇ�Ȃ�..�B
					log.warn(e.getMessage(), e);
					throw new NotFoundException();
				}
			}
		}

		// ��{�d�l�Ƃ��ẮA�ݔ���񂩂畨����{���̃A�C�R�������X�V����B
		equipToiconData(inputForm.getSysHousingCd(), iconCdList.toArray(new String[iconCdList.size()]), editUserId);

		// note
		// �A�C�R������������{���Ȃ̂ŁA�^�C���X�^���v�̍X�V�͓����ɍs�������������I�����A
		// �A�C�R�����̍X�V�̓I�[�o�[���C�h����Ė����������\��������̂ŁA�I�[�o�[�w�b�h�ł͂��邪�A
		// �^�C���X�^���v�͕ʂɍX�V����B

		// �������̃^�C���X�^���v���X�V
		updateEditTimestamp(inputForm.getSysHousingCd(), editUserId);

	}


	
	/**
	 * �ݔ�CD ����\���p�A�C�R�������X�V����B<br/>
	 * ���̃��\�b�h�͐ݔ����̍X�V������������s����A���͂����ݔ�CD �����ɕ\���p�A�C�R������
	 * ��������B<br/>
	 * �\���p�A�C�R������ʂ̌��򂩂�쐬����ꍇ�A�����������Ȃ��l�ɂ��̃��\�b�h���I�[�o�[���C�h
	 * ����K�v������B<br/>
	 * <br/>
	 * @param sysHousingCd
	 * @param equipCds
	 */
	protected void equipToiconData(String sysHousingCd, String[] equipCds, String editUserId) {
		
		StringBuffer buff = new StringBuffer(4000);
		boolean isFirst = true;
		
		if (equipCds != null) {
			for (String equipCd : equipCds) {
				if (!StringValidateUtil.isEmpty(equipCd)) {
					if (!isFirst) {
						buff.append(",");
					}
					buff.append(equipCd);
					isFirst = false;
				}
			}
		}


		// �X�V�����𐶐�
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", sysHousingCd);

		// �X�V�I�u�W�F�N�g���쐬
		UpdateExpression[] updateExpression
			= new UpdateExpression[] {new UpdateValue("iconCd", buff.toString())};

		// ������{���̃A�C�R�������X�V
		this.housingInfoDAO.updateByCriteria(criteria, updateExpression);

		// �����I�ȕ��i�Ȃ̂ŁA������{���̃^�C���X�^���v�͍X�V���Ȃ��B
		// �Ăяo�������ŕK�v�ɉ����čX�V���鎖�B
	}



	/**
	 * �p�����[�^�œn���ꂽ Form �̏��ŕ����摜����V�K�o�^����B<br/>
	 * �o���f�[�V�������̃`�F�b�N�����͌Ăяo�����ŗ\�ߎ��{���Ă������B<br/>
	 * <br/>
	 * �֘A����摜�t�@�C���̌��J������T���l�C���̍쐬�� Proxy �N���X���őΉ�����̂ŁA
	 * ���̃N���X���ł͑Ή����Ȃ��B<br/>
	 * <br/>
	 * @param inputForm �����摜���̓��͒l���i�[���� Form �I�u�W�F�N�g
	 * @param editUserId ���O�C�����[�U�[ID �i�X�V���p�j
	 * 
	 * @return �V���ɒǉ������摜���̃��X�g
	 * 
	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	@Override
	public List<HousingImageInfo> addHousingImg(HousingImgForm inputForm, String editUserId)
			throws Exception{

		// �߂�l�ƂȂ�A�V���ɒǉ����������摜���̃��X�g
		List<HousingImageInfo> imgList = new ArrayList<>();


		// �X�V�ΏۂƂȂ镨���ڍׂ��擾����B �i�摜�p�X�Ɏg�p����A�����A���������擾����ׁB
		// �����Y���f�[�^�����݂��Ȃ��ꍇ�i�����e���ɑ�����폜���ꂽ�ꍇ�Ȃǁj�͗�O���X���[����B
// 2015.05.01 H.Mizuno ����J�����̏ꍇ�A�擙�o�^����ƃG���[�ɂȂ�����C�� start
//		Housing housing = this.searchHousingPk(inputForm.getSysHousingCd());
		Housing housing = this.searchHousingPk(inputForm.getSysHousingCd(), true);
// 2015.05.01 H.Mizuno ����J�����̏ꍇ�A�擙�o�^����ƃG���[�ɂȂ�����C�� end
		if (housing == null){
			throw new NotFoundException();
		}


		// ���C���摜�t���O�����Z�b�g���A�V�X�e������CD �P�ʂŕ����摜�������b�N����B
		lockAndRestFlag(inputForm.getSysHousingCd());


		// �摜�t�@�C������Ɍ��������[�v����B
		for (int idx=0; idx < inputForm.getFileName().length; ++idx){

			// �t�@�C�����������ꍇ���̏�����
			if (StringUtils.isEmpty(inputForm.getFileName()[idx])) continue;

			// �t�H�[���̒l�� Value �I�u�W�F�N�g�� copy ����B
			HousingImageInfo housingImageInfo = (HousingImageInfo) this.valueObjectFactory.getValueObject("HousingImageInfo");
			inputForm.copyToHousingImageInfo(housingImageInfo, idx);

			// �p�X����ݒ肷��B
			housingImageInfo.setPathName(createImagePath(housing));

			// �c���E�����t���O�́A���t�H���_���̉摜�t�@�C����������擾���Đݒ肷��B
			String tempFileName = this.commonParameters.getHousImgTempPhysicalPath() +
								  inputForm.getTempDate() + "/" + inputForm.getFileName()[idx]; 
			ImgInfo info = imgUtils.getImgInfo(tempFileName);
			housingImageInfo.setHwFlg(info.getHwFlg().toString());


			// �}�Ԃ́A�Ăяo�������ŁA�V�X�e�������ԍ��A�摜�^�C�v���ŘA�Ԃ�ݒ肷��B
			housingImageInfo.setDivNo(getMaxDivNo(housingImageInfo.getSysHousingCd(), housingImageInfo.getImageType()));
			this.HousingImageInfoDAO.insert(new HousingImageInfo[]{housingImageInfo});

			// �ǉ������摜����߂�l�p�̃��X�g�֐ݒ肷��B
			imgList.add(housingImageInfo);
		}


		// ���C���摜�t���O�A�}�Ԃ��X�V����B
		updateMainFlgAndDivNo(inputForm.getSysHousingCd());

		// ������{���̃^�C���X�^���v���X�V
		updateEditTimestamp(inputForm.getSysHousingCd(), editUserId);

		return imgList;
	}



	/**
	 * �p�����[�^�œn���ꂽ�V�X�e������CD�A�摜�^�C�v�A�}�Ԃŕ����摜�����폜����B<br/>
	 * �����폜�t���O�� 1 ���ݒ肳��Ă���ꍇ�͍X�V�����ɍ폜��������B<br/>
	 * �o���f�[�V�������̃`�F�b�N�����͌Ăяo�����ŗ\�ߎ��{���Ă������B<br/>
	 * <br/>
	 * �֘A����摜�t�@�C���̍폜�́AProxy �N���X���őΉ�����̂ŁA���̃N���X���ł͑Ή����Ȃ��B<br/>
	 * �܂��A�X�V�����́A�摜�p�X�A�摜�t�@�C�����̍X�V�̓T�|�[�g���Ă��Ȃ��B<br/>
	 * <br/>
	 * @param sysHousingCd �V�X�e������CD
	 * @param imageType �����摜�^�C�v
	 * @param divNo �}��
	 * @param editUserId ���O�C�����[�U�[ID �i�X�V���p�j
	 * 
	 * @return �폜�����������摜���̃��X�g
	 */
	@Override
	public List<HousingImageInfo> updHousingImg(HousingImgForm inputForm, String editUserId){

		// �߂�l�ƂȂ�A�폜���������������摜���̃��X�g
		List<HousingImageInfo> delImgList = new ArrayList<>();

		
		// ���C���摜�t���O�����Z�b�g���A�V�X�e������CD �P�ʂŕ����摜�������b�N����B
		lockAndRestFlag(inputForm.getSysHousingCd());


		// �摜�t�@�C������Ɍ��������[�v����B
		for (int idx=0; idx < inputForm.getFileName().length; ++idx){

			// �폜�t���O���ݒ肳��Ă���ꍇ�͍폜������
			if ("1".equals(inputForm.getDelFlg()[idx])) {

				// �����摜�����폜
				HousingImageInfo imgInfo
							= delHousingImg(inputForm.getSysHousingCd(),
						     				inputForm.getImageType()[idx],
						      				Integer.valueOf(inputForm.getDivNo()[idx]));
				// ���ۂɍ폜�����񂪖��������ꍇ�A null �����A�����̂ŁA���̏ꍇ�̓��X�g�ɒǉ����Ȃ��B
				if (imgInfo != null){
					delImgList.add(imgInfo);
				}
					
			} else {

				// �����摜�̍X�V����

				// �����𐶐�����B
				DAOCriteria criteria = new DAOCriteria();
				criteria.addWhereClause("sysHousingCd", inputForm.getSysHousingCd());
				criteria.addWhereClause("imageType", inputForm.getOldImageType()[idx]);
				criteria.addWhereClause("divNo", Integer.valueOf(inputForm.getDivNo()[idx]));

				// Form ���� UpdateExpression �𐶐����ăf�[�^���X�V����B
				this.HousingImageInfoDAO.updateByCriteria(criteria, inputForm.buildUpdateExpression(idx));
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
	 * �X�V��ID ���ȗ��������̃��\�b�h�ꍇ�A���C���摜�t���O�A�}�ԁA������{���̃^�C���X�^���v�͍X�V����Ȃ��B
	 * <br/>
	 * �֘A����摜�t�@�C���̍폜�́AProxy �N���X���őΉ�����̂ŁA���̃N���X���ł͑Ή����Ȃ��B<br/>
	 * <br/>
	 * @param sysHousingCd �V�X�e������CD
	 * @param imageType �����摜�^�C�v
	 * @param divNo �}��
	 * 
	 * @return �폜�����������摜���
	 * 
	 */
	protected HousingImageInfo delHousingImg(String sysHousingCd, String imageType, int divNo)	{
		
		// �����摜�����폜����������쐬
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", sysHousingCd);
		criteria.addWhereClause("imageType", imageType);
		criteria.addWhereClause("divNo", divNo);

		// �폜�O�ɁA�폜�Ώۃf�[�^�̏����擾����B
		// �����擾�o���Ȃ��ꍇ�� null �𕜋A����B
		List<HousingImageInfo> imgInfo = this.HousingImageInfoDAO.selectByFilter(criteria);
		if (imgInfo.size() == 0) return null;

		this.HousingImageInfoDAO.deleteByFilter(criteria);

		// �摜�t�@�C���̕����폜�͊g�������l�����Ă��̃N���X���ł͍s��Ȃ��B
		// �����摜���J�A�T���l�C���쐬�A�����폜���̏����́A���̃N���X�� Proxy �N���X���őΉ�����B

		return imgInfo.get(0);
	}

	/**
	 * �p�����[�^�œn���ꂽ�V�X�e������CD�A�摜�^�C�v�A�}�Ԃŕ����摜�����폜����B<br/>
	 * �o���f�[�V�������̃`�F�b�N�����͌Ăяo�����ŗ\�ߎ��{���Ă������B<br/>
	 * �폜��A���C���摜�t���O�ƕ�����{���̃^�C���X�^���v�����X�V����B<br/>
	 * <br/>
	 * �֘A����摜�t�@�C���̍폜�́AProxy �N���X���őΉ�����̂ŁA���̃N���X���ł͑Ή����Ȃ��B<br/>
	 * <br/>
	 * @param sysHousingCd �V�X�e������CD
	 * @param imageType �����摜�^�C�v
	 * @param divNo �}��
	 * @param editUserId ���O�C�����[�U�[ID �i�X�V���p�j
	 * 
	 * @return �폜�����������摜���
	 */
	@Override
	public HousingImageInfo delHousingImg(String sysHousingCd, String imageType, int divNo, String editUserId)	{

		// ���C���摜�t���O�����Z�b�g���A�V�X�e������CD �P�ʂŕ����摜�������b�N����B
		lockAndRestFlag(sysHousingCd);
		
		// �w�肳�ꂽ�����摜�����폜����B
		HousingImageInfo imgInfo = delHousingImg(sysHousingCd, imageType, divNo);

		// ���C���摜�t���O���X�V����B
		updateMainFlgAndDivNo(sysHousingCd);

		// ������{���̃^�C���X�^���v���X�V
		updateEditTimestamp(sysHousingCd, editUserId);

		return imgInfo;
	}


	
	/**
	 * �����摜���̃p�X���Ɋi�[����l���擾����B<br/>
	 * <br/>
	 * @param housing �X�V�ΏۂƂȂ镨���I�u�W�F�N�g
	 * 
	 * @return ���H���ꂽ�p�X��
	 */
	protected String createImagePath(Housing housing){
	
		// �摜�p�X�́A���L�\���ƂȂ�B
		// /�u�萔�l�v/[�������CD]/[�s���{��CD]/[�s�撬��CD]/[�V�X�e�������ԍ�]/[�T�C�Y]/�V�[�P���X(10��).jpg

		// DB�̃p�X���Ɋi�[����̂́A/[�������CD]/[�s���{��CD]/[�s�撬��CD]/[�V�X�e�������ԍ�]/�@�܂ŁB
		
		// �����I�u�W�F�N�g�A�����I�u�W�F�N�g���擾���A�������CD�A�s���{��CD�A�s�撬��CD�A�V�X�e������CD ���擾����B
		// �����A�l�����ݒ�̏ꍇ�̓I�[��9 �̒l���g�p����B
		BuildingInfo buildingInfo = (BuildingInfo)housing.getBuilding().getBuildingInfo().getItems().get("buildingInfo");
		HousingInfo housingInfo = (HousingInfo)housing.getHousingInfo().getItems().get("housingInfo");


		String prefCd = buildingInfo.getPrefCd();
		if (StringValidateUtil.isEmpty(prefCd)) prefCd = "99";

		String addressCd = buildingInfo.getAddressCd();
		if (StringValidateUtil.isEmpty(addressCd)) addressCd = "99999";

		String housingKindCd = buildingInfo.getHousingKindCd();
		if (StringValidateUtil.isEmpty(housingKindCd)) housingKindCd = "999";

		
		return housingKindCd + "/" + prefCd + "/" + addressCd + "/" + housingInfo.getSysHousingCd() + "/";
	}

	
	
	/**
	 * �����ԍ��A�摜�^�C�v���ɁA�ő�̎}�Ԃ��擾����B<br/>
	 * <br/>
	 * @param sysHousingCd �V�X�e������CD
	 * @param imageType �摜�^�C�v
	 */
	protected int getMaxDivNo(String sysHousingCd, String imageType){

		// ���̏����́A�r��������s���Ă��Ȃ��B
		// ���̏������g�p����O�ɂ́AlockAndRestFlag() �ōs���b�N���s���Ă���K�v������B
		// �������AlockAndRestFlag() �́A���C���摜�t���O�����Z�b�g�X�V���鎖�ɂ��s���b�N�Ȃ̂ŁA
		// �g�p�ɂ͒��ӂ��鎖�B

		// �V�X�e������CD�A
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", sysHousingCd);
		criteria.addWhereClause("imageType", imageType);
		criteria.addOrderByClause("divNo", false);

		// ����V�X�e������CD�A�摜�^�C�v���Ŏ}�Ԃ��~���Ƀ\�[�g����B
		// ���̌��ʂ̍ő�l + 1 ���}�ԂƂ��ĕ��A����B
		// �Y���f�[�^�������ꍇ�� 1 �𕜋A����B
		List<HousingImageInfo> imgList = this.HousingImageInfoDAO.selectByFilter(criteria);

		if (imgList.size() == 0) return 0;
		return imgList.get(0).getDivNo() + 1;
	}

	
	
	/**
	 * �V�X�e������CD �P�ʂɕ����摜���̍s���b�N���擾����B<br/>
	 * �܂��A���̍ہA���C���摜�t���O�̃��Z�b�g���s���B<br/>
	 * <br/>
	 * @param sysHousingCd �V�X�e������CD
	 */
	protected void lockAndRestFlag(String sysHousingCd) {
		
		// �V�X�e������CD ���̃��C���摜�t���O����x���Z�b�g����B
		// ���̑���́A�V�X�e������CD �P�ʂł̕����摜���̍s���b�L���O�̎擾�ł�����B
		DAOCriteria tagetCri = new DAOCriteria();
		tagetCri.addWhereClause("sysHousingCd", sysHousingCd);

		UpdateExpression[] expression
			= new UpdateExpression[] {new UpdateValue("mainImageFlg", "0")};

		this.HousingImageInfoDAO.updateByCriteria(tagetCri, expression);

	}
	
	
	
	/**
	 * �����摜���̃��C���摜�t���O�A����ю}�Ԃ̍X�V���s��<br/>
	 * �摜��ʖ��Ɉ�ԍŏ��ɕ\�����镨���摜���ɑ΂��ă��C���摜�t���O��ݒ肷��B<br/>
	 * �܂��A�摜�^�C�v���ɕ\�����Ŏ}�Ԃ��X�V����B<br/>
	 * <br/>
	 * @param getSysHousingCd �V�X�e������CD
	 */
	protected void updateMainFlgAndDivNo(String sysHousingCd){
		DAOCriteria tagetCri = new DAOCriteria();
		tagetCri.addWhereClause("sysHousingCd", sysHousingCd);

		
		// �\�����݂̂�ύX�����ꍇ�A�}�Ԃ��d�����ăG���[����������B
		// �����ŁA�g���b�L�[�ȕ��@�����A��x�A�}�Ԃ��}�C�i�X�̒l�� UPDATE ������Ɏ}�Ԃ̍X�V�������s���B
		UpdateExpression[] initExpression
			= new UpdateExpression[] {new FormulaUpdateExpression("div_no = div_no * -1")};

		this.HousingImageInfoDAO.updateByCriteria(tagetCri, initExpression);


		// �\�[�g������ǉ�
		// �}�Ԃ̓}�C�i�X�̒l�ɍX�V����Ă���̂ŁADESC �Ń\�[�g����B
		tagetCri.addOrderByClause("imageType");
		tagetCri.addOrderByClause("sortOrder");
		tagetCri.addOrderByClause("divNo", false);

		// �V�X�e������CD ���̕����摜�����擾
		List<HousingImageInfo> imgList = this.HousingImageInfoDAO.selectByFilter(tagetCri);

		String oldImgType = "";					// ��s�O�̕����摜�^�C�v
		int divNo = 0;							// �}��
		for (HousingImageInfo imgInfo : imgList){
			++divNo;

			// �X�V�Ώۂ̃L�[�����쐬
			DAOCriteria updCri = new DAOCriteria();
			updCri.addWhereClause("sysHousingCd", sysHousingCd);
			updCri.addWhereClause("imageType", imgInfo.getImageType());
			updCri.addWhereClause("divNo", imgInfo.getDivNo());

			UpdateExpression[] expression;
			if (!oldImgType.equals(imgInfo.getImageType())){
				// �摜�^�C�v���O��̍s����ς�����ꍇ�A���̃��R�[�h�����C���摜�Ƃ��čX�V����B

				divNo = 1;
				expression = new UpdateExpression[] {new UpdateValue("mainImageFlg", "1"),
													 new UpdateValue("divNo", divNo)};
			} else {
				// �摜�^�C�v���O��̍s�Ɠ����ꍇ�A���C���摜���I�t�Ƃ��Ď}�Ԃ��X�V����B

				expression = new UpdateExpression[] {new UpdateValue("mainImageFlg", "0"),
						 new UpdateValue("divNo", divNo)};
			}

			this.HousingImageInfoDAO.updateByCriteria(updCri, expression);
			oldImgType = imgInfo.getImageType();

		}
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
	 * @param searchForm ���������A����сA�������ʂ̊i�[�I�u�W�F�N�g
	 * @param full false �̏ꍇ�A���J�ΏۊO�����O����B�@true �̏ꍇ�͏��O���Ȃ�
	 * 
	 * @return �Y������
	 * 
	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	@Override
	public int searchHousing(HousingSearchForm searchForm) throws Exception {
		return searchHousing(searchForm, false);
	}

	/**
	 * �������i�ꕔ�A�������j���������A���ʃ��X�g�𕜋A����B�i�ꗗ�p�j<br/>
	 * �����œn���ꂽ Form �p�����[�^�̒l�Ō��������𐶐����A����������������B<br/>
	 * �������ʂ� Form �I�u�W�F�N�g�Ɋi�[����A�擾�����Y��������߂�l�Ƃ��ĕ��A����B<br/>
	 * <br/>
	 * 
	 * @param searchForm ���������A����сA�������ʂ̊i�[�I�u�W�F�N�g
	 * @param full false �̏ꍇ�A���J�ΏۊO�����O����B�@true �̏ꍇ�͏��O���Ȃ�
	 * 
	 * @return �Y������
	 * 
	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	@Override
	public int searchHousing(HousingSearchForm searchForm, boolean full) throws Exception {

		// ��������
		List<Housing> housingList = null;
		try {
			housingList = this.searchHousingDAO.searchHousing(searchForm, full);
		} catch (NotEnoughRowsException err) {
			// �͈͊O�̃y�[�W���ݒ肳�ꂽ�ꍇ�A�Č���
			int pageNo = (err.getMaxRowCount() - 1) / searchForm.getRowsPerPage() + 1;
			searchForm.setSelectedPage(pageNo);
			housingList = this.searchHousingDAO.searchHousing(searchForm, full);
		}

		// �����摜�����擾
		for (Housing housing : housingList) {
			HousingInfo housingInfo = (HousingInfo) housing.getHousingInfo().getItems().get("housingInfo");
			String sysHousingCd = housingInfo.getSysHousingCd();
			DAOCriteria criteria = new DAOCriteria();
			criteria.addWhereClause("sysHousingCd", sysHousingCd);
			criteria.addOrderByClause("imageType");
			criteria.addOrderByClause("mainImageFlg", false);
			criteria.addOrderByClause("sortOrder");
			List<HousingImageInfo> imageList = HousingImageInfoDAO.selectByFilter(criteria);
			housing.setHousingImageInfos(imageList);
		}

		searchForm.setRows(housingList);

		return searchForm.getMaxRows();
	}



	/**
	 * ���N�G�X�g�p�����[�^�œn���ꂽ�V�X�e������CD �i��L�[�l�j�ɊY�����镨�����𕜋A����B<br/>
	 * �����Y���f�[�^��������Ȃ��ꍇ�Anull �𕜋A����B<br/>
	 * <br/>
	 * ���̃��\�b�h���g�p�����ꍇ�A�Öق̒��o�����i�Ⴆ�΁A����J�����̏��O�Ȃǁj���K�p�����B<br/>
	 * ����āA�t�����g���͊�{�I�ɂ��̃��\�b�h���g�p���鎖�B<br/>
	 * <br/>
	 * @param sysHousingCd �V�X�e������CD
	 * @param full false �̏ꍇ�A���J�ΏۊO�����O����B�@true �̏ꍇ�͏��O���Ȃ�
	 * 
	 * @return�@DB ����擾�����o���[�I�u�W�F�N�g���i�[�����R���|�W�b�g�N���X
	 * 
 	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
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
	 * @param sysHousingCd �V�X�e������CD
	 * @param full false �̏ꍇ�A���J�ΏۊO�����O����B�@true �̏ꍇ�͏��O���Ȃ�
	 * 
	 * @return�@DB ����擾�����o���[�I�u�W�F�N�g���i�[�����R���|�W�b�g�N���X
	 * 
 	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	@Override
	public Housing searchHousingPk(String sysHousingCd, boolean full)
			throws Exception {

		// �������̃C���X�^���X�𐶐�����B
		Housing housing = createHousingInstace();


		// confMainData() �́A�P�΂P�̊֌W�ɂ���֘A�e�[�u���̏����擾���Ahousing �֐ݒ肷��B
		// �����Y������f�[�^�������ꍇ�Ahousing �։����ݒ肹���� null �𕜋A���Ă���̂ŁA�߂�l��
		// null �̏ꍇ�͏����𒆒f����B
		if (confMainData(housing, sysHousingCd, full) == null) return null; 


		// �����ݔ������擾����B
		confHousingEquip(housing, sysHousingCd);

		// �����摜�����擾����B
		confHousingImage(housing, sysHousingCd);

		// �����g���������̎擾
		// ���̃e�[�u���̎擾���ʂ́AMap �I�u�W�F�N�g�ɕϊ����ĕ��A����B
		confHousingExtInfo(housing, sysHousingCd);
		
		return housing;
	}

	
	
	/**
	 * �����ڍ׏��� �P�΂P�̊֌W�ɂ��郁�C���̊֘A�e�[�u���̏����擾���AHousing �I�u�W�F�N�g�֐ݒ肷��B<br/>
	 * <br/>
	 * @param housing �l�̐ݒ��ƂȂ� Housing �I�u�W�F�N�g
	 * @param sysHousingCd �擾�ΏۃV�X�e������CD
	 * @param full false �̏ꍇ�A���J�ΏۊO�����O����B�@true �̏ꍇ�͏��O���Ȃ�
	 * 
	 * @return �擾���� �i�Y���Ȃ��̏ꍇ�Anull�j
	 * @throws Exception �Ϗ��悪�X���[�����O 
	 */
	protected JoinResult confMainData(Housing housing, String sysHousingCd, boolean full)
			throws Exception{

		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("housingInfo", "sysHousingCd", sysHousingCd, DAOCriteria.EQUALS, false);

		// ���� full = false �̏ꍇ�A�Öق̏��O������ǉ�����B
		// �ʏ�A�t�����g����������擾����ꍇ�͂��̈����� false �ɂ���B
		if (!full) {
			addNegativeFilter(criteria);
		}

		// �����ڍ׏��� 1 �� 1 �̊֌W�ɂ��镨�������擾����B
		// ���̍ہA�����֘A�̏��͌������Ȃ��B�@�����֘A�̏��͌������� model �֏������Ϗ����Ď擾����B
		List<JoinResult> result = this.housingMainJoinDAO.selectByFilter(criteria);
		if (result.size() == 0) return null;


		// ��������ݒ�
		housing.setHousingInfo(result.get(0));

		// ������񂪎擾�ł����ꍇ�́A�������� model ���g�p���Č��������擾����B
		String sysBuildingCd = ((HousingInfo)result.get(0).getItems().get("housingInfo")).getSysBuildingCd();
		housing.setBuilding(this.buildingManager.searchBuildingPk(sysBuildingCd));

		return result.get(0);
	}



	/**
	 * �����ݔ������擾����B<br/>
 	 * �擾���ʂ́ALinkedHashMap �ɕϊ����� housing �֊i�[�����B<br/>
 	 * �EKey = �ݔ�CD<br/>
 	 * �EValue = �ݔ��}�X�^<br/>
 	 * <br/>
	 * @param housing �l�̐ݒ��ƂȂ� Housing �I�u�W�F�N�g
	 * @param sysHousingCd �擾�ΏۃV�X�e������CD
	 * 
	 * @return �擾����
	 */
	protected Map<String, EquipMst> confHousingEquip(Housing housing, String sysHousingCd){

		// �����ݔ����̎擾
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("housingEquipInfo", "sysHousingCd", sysHousingCd, DAOCriteria.EQUALS, false);
		criteria.addOrderByClause("equipMst", "sortOrder", true);
		List<JoinResult> list = this.housingEquipListDAO.selectByFilter(criteria);

		
		// List �I�u�W�F�N�g���ƁA���͉�ʂȂǂőI����Ԃɂ���̂��ʓ|�Ȃ̂ŁA�ݔ�CD �� Key �Ƃ��� Map �ɕϊ�����B
		Map<String, EquipMst> equipMap = new LinkedHashMap<>();
		
		for (JoinResult result : list) {
			// Key �͐ݔ�CD ���g�p����B
			String key = ((HousingEquipInfo)result.getItems().get("housingEquipInfo")).getEquipCd();
			EquipMst value = (EquipMst)result.getItems().get("equipMst");
			equipMap.put(key, value);
		}

		housing.setHousingEquipInfos(equipMap);
		return equipMap;
	}


	
	/**
	 * �����摜�����擾����B<br/>
	 * �擾�������ʂ� housing �I�u�W�F�N�g�֊i�[����B<br/>
	 * <br/>
	 * @param housing �l�̐ݒ��ƂȂ� Housing �I�u�W�F�N�g
	 * @param sysHousingCd �擾�ΏۃV�X�e������CD
	 * 
	 * @return �擾����
	 */
	protected List<HousingImageInfo> confHousingImage(Housing housing, String sysHousingCd){

		// �����摜�����擾����B
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", sysHousingCd);
		criteria.addOrderByClause("sortOrder");
		List<HousingImageInfo> list = this.HousingImageInfoDAO.selectByFilter(criteria);

		housing.setHousingImageInfos(list);
		
		return list;
		
	}

	
	
	/**
	 * �����g���������Ɋi�[����Ă������ Map �I�u�W�F�N�g�ɕϊ����Ċi�[����B<br/>
	 * Map �I�u�W�F�N�g�̍\���́A�ȉ��̒ʂ�B<br/>
	 *     �EKey = �����g�����̃J�e�S�����icategory�j
	 *     �EValue = �J�e�S���̊Y������AKey�l���ݒ肳�ꂽ Map �I�u�W�F�N�g�iKey = keyName��AValue = dataValue��j
	 * <br/>
	 * @param housing �i�[��ƂȂ� Housing �I�u�W�F�N�g
	 * @param sysHousingCd �擾�ΏۃV�X�e������CD
	 * 
	 */
	protected void confHousingExtInfo(Housing housing, String sysHousingCd){

		// �����g�����������擾����B
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", sysHousingCd);
		criteria.addOrderByClause("category");
		criteria.addOrderByClause("keyName");

		List<HousingExtInfo> list = this.housingExtInfoDAO.selectByFilter(criteria);

		
		// �i�[��� Map ���擾����B
		// ���� Map �� LinedHashMap �Ȃ̂ŁA�ǉ��������Ԃ��ۏႳ���B
		Map<String, Map<String, String>> extMap = housing.getHousingExtInfos();


		// �����g���������́A�J�e�S�����AKey�� �̏��Ń\�[�g����Ă���B
		for (HousingExtInfo ext : list) {
			// �J�e�S�������擾
			String category = ext.getCategory();

			// �J�e�S�����ɊY������ Map ���擾����B
			Map<String, String> cateMap = extMap.get(category);

			// �������݂��Ȃ��ꍇ�̓J�e�S�����ɊY������ Map ���쐬����B
			if (cateMap == null){
				cateMap = new LinkedHashMap<>();
				extMap.put(category, cateMap);
			}

			// �J�e�S������ Map �Ɏ擾���� Key�AValue ��ǉ�����B
			cateMap.put(ext.getKeyName(), ext.getDataValue());
		}

	}


	
	/**
	 * �p�����[�^�œn���ꂽ Map �̏��ŕ����g�����������X�V����B�i�V�X�e������CD �P�ʁj<br/>
	 * �X�V�́ADelete & Insert �ŏ�������B<br/>
	 * <br/>
	 * inputData �� Map �̍\���͈ȉ��̒ʂ�B<br/>
	 *   �Ekey = �J�e�S���� �i�g���������́Acategory �Ɋi�[����l�j
	 *   �Evalue = �l���i�[���ꂽ Map �I�u�W�F�N�g
	 * inputData �� value �Ɋi�[����� Map �̍\���͈ȉ��̒ʂ�B<br/>
	 *   �Ekey = Key�� �i�g���������́Akey_name �Ɋi�[����l�j
	 *   �Evalue = ���͒l �i�g���������́Adata_value �Ɋi�[����l�j
	 * <br/>
	 * @param sysHousingCd �X�V�ΏۃV�X�e������CD
	 * @param inputData �o�^���ƂȂ� Map �I�u�W�F�N�g
	 * @param editUserId ���O�C�����[�U�[ID �i�X�V���p�j
	 * 
	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 * @exception NotFoundException �e�ƂȂ镨����{��񂪑��݂��Ȃ��ꍇ
	 */
	@Override
	public void updExtInfo(String sysHousingCd, Map<String, Map<String,String>> inputData, String editUserId)
			throws Exception, NotFoundException {

		// �J�e�S���P�ʂō폜���Ă���̓���ւ��Ȃ̂ŁA��x�f�[�^���폜����B
		// �^�C���X�^���v�́A���̎��ɍX�V����B
		delExtInfo(sysHousingCd, editUserId);


		// Map �� null �̏ꍇ�A�폜�݂̂����s���ĕ��A����B
		if (inputData == null) return;


		// Map �����[�v���A�J�e�S�������擾����B
		for (Map.Entry<String, Map<String,String>> e : inputData.entrySet()){
			// �J�e�S���� Map �Ɋi�[����Ă��� Map �I�u�W�F�N�g�����[�v���āA�J�e�S���P�ʂ̃f�[�^��o�^����B
			updExtInfo(sysHousingCd, e.getKey(), e.getValue(), editUserId);
		}
	}

	
	
	/**
	 * �p�����[�^�œn���ꂽ�V�X�e������CD �ɊY�����镨���g�����������폜����B�i�V�X�e������CD �P�ʁj<br/>
	 * <br/>
	 * @param sysHousingCd �폜�ΏۃV�X�e������CD
	 * @param editUserId ���O�C�����[�U�[ID �i�X�V���p�j
	 * 
	 */
	@Override
	public void delExtInfo(String sysHousingCd, String editUserId) {

		// �e���R�[�h�̃^�C���X�^���v���X�V
		updateEditTimestamp(sysHousingCd, editUserId);

		// �폜��������
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", sysHousingCd);
		this.housingExtInfoDAO.deleteByFilter(criteria);

	}

	
	
	/**
	 * �p�����[�^�œn���ꂽ Map �̏��ŕ����g�����������X�V����B�i�J�e�S���[ �P�ʁj<br/>
	 * �X�V�́ADelete & Insert �ŏ�������B<br/>
	 * <br/>
	 * inputData �� Map �̍\���͈ȉ��̒ʂ�B<br/>
	 *   �Ekey = Key�� �i�g���������́Akey_name �Ɋi�[����l�j
	 *   �Evalue = ���͒l �i�g���������́Adata_value �Ɋi�[����l�j
	 * <br/>
	 * <br/>
	 * @param sysHousingCd �X�V�ΏۃV�X�e������CD
	 * @param category �X�V�ΏۃJ�e�S����
	 * @param inputData �o�^���ƂȂ� Map �I�u�W�F�N�g
	 * @param editUserId ���O�C�����[�U�[ID �i�X�V���p�j
	 * 
	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 * @exception NotFoundException �e�ƂȂ镨����{��񂪑��݂��Ȃ��ꍇ
	 */
	@Override
	public void updExtInfo(String sysHousingCd, String category, Map<String, String> inputData, String editUserId)
			throws Exception, NotFoundException {

		// �J�e�S���P�ʂō폜���Ă���̓���ւ��Ȃ̂ŁA��x�f�[�^���폜����B
		// �^�C���X�^���v�́A���̎��ɍX�V����B
		delExtInfo(sysHousingCd, category, editUserId);
		
		
		// Map �� null �̏ꍇ�A�폜�݂̂����s���ĕ��A����B
		if (inputData == null) return;


		// Map �����[�v���A�����g����������ǉ�����B
		for (Map.Entry<String, String> e : inputData.entrySet()){
			updExtInfo(sysHousingCd, category, e.getKey(), e.getValue(), editUserId);
		}

	}


	
	/**
	 * �p�����[�^�œn���ꂽ�V�X�e������CD �ɊY�����镨���g�����������폜����B�i�J�e�S���[ �P�ʁj<br/>
	 * <br/>
	 * @param sysHousingCd �폜�ΏۃV�X�e������CD
	 * @param category �폜�ΏۃJ�e�S����
	 * @param editUserId ���O�C�����[�U�[ID �i�X�V���p�j
	 * 
	 */
	@Override
	public void delExtInfo(String sysHousingCd, String category, String editUserId)	{

		// �e���R�[�h�̃^�C���X�^���v���X�V
		updateEditTimestamp(sysHousingCd, editUserId);

		// �폜��������
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", sysHousingCd);
		criteria.addWhereClause("category", category);
		this.housingExtInfoDAO.deleteByFilter(criteria);
		
	}



	/**
	 * �p�����[�^�œn���ꂽ Map �̏��ŕ����g�����������X�V����B�iKey �P�ʁj<br/>
	 * �X�V�́ADelete & Insert �ŏ�������B<br/>
	 * <br/>
	 * @param sysHousingCd �X�V�ΏۃV�X�e������CD
	 * @param category �X�V�ΏۃJ�e�S����
	 * @param key �X�V�Ώ�Key
	 * @param value �X�V����l
	 * @param editUserId ���O�C�����[�U�[ID �i�X�V���p�j
	 * 
	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 * @exception NotFoundException �e�ƂȂ镨����{��񂪑��݂��Ȃ��ꍇ
	 */
	@Override
	public void updExtInfo(String sysHousingCd, String category, String key, String value, String editUserId)
			throws Exception, NotFoundException {

		// �e���R�[�h�̃^�C���X�^���v���X�V
		updateEditTimestamp(sysHousingCd, editUserId);

		
		// �X�V�ΏۃI�u�W�F�N�g���擾
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", sysHousingCd);
		criteria.addWhereClause("category", category);
		criteria.addWhereClause("keyName", key);
		List<HousingExtInfo> list =this.housingExtInfoDAO.selectByFilter(criteria);

		if (list.size() == 0) {
			// �Y���f�[�^�����݂��Ȃ��ꍇ�A�V�K�쐬
			HousingExtInfo housingExtInfo
				= (HousingExtInfo) this.valueObjectFactory.getValueObject("HousingExtInfo");

			housingExtInfo.setSysHousingCd(sysHousingCd);
			housingExtInfo.setCategory(category);
			housingExtInfo.setKeyName(key);
			housingExtInfo.setDataValue(value);

			try {
				this.housingExtInfoDAO.insert(new HousingExtInfo[]{housingExtInfo});
			} catch (DataIntegrityViolationException e){
				log.warn(e.getMessage(), e);
				throw new NotFoundException();
			}

		} else {
			// �Y���f�[�^�����݂����ꍇ�AValue �� �X�V
			HousingExtInfo housingExtInfo = list.get(0);
			housingExtInfo.setDataValue(value);

			this.housingExtInfoDAO.update(new HousingExtInfo[]{housingExtInfo});
		}

	}



	/**
	 * �p�����[�^�œn���ꂽ�V�X�e������CD �ɊY�����镨���g�����������폜����B�i�L�[ �P�ʁj<br/>
	 * <br/>
	 * @param sysHousingCd �폜�ΏۃV�X�e������CD
	 * @param category �폜�ΏۃJ�e�S����
	 * @param category �폜�Ώ� Key
	 * @param editUserId ���O�C�����[�U�[ID �i�X�V���p�j
	 * 
	 */
	@Override
	public void delExtInfo(String sysHousingCd, String category, String key, String editUserId){

		// �e���R�[�h�̃^�C���X�^���v���X�V
		updateEditTimestamp(sysHousingCd, editUserId);

		// �폜��������
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", sysHousingCd);
		criteria.addWhereClause("category", category);
		criteria.addWhereClause("keyName", key);
		this.housingExtInfoDAO.deleteByFilter(criteria);

	}

	
	
	/**
	 * ������{���̃^�C���X�^���v�����X�V����B<br/>
	 * <br/>
	 * @param sysHousingCd �X�V�ΏۃV�X�e������CD
	 * @param editUserId �X�V��ID
	 */
	protected void updateEditTimestamp(String sysHousingCd, String editUserId){

		// �X�V�����𐶐�
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", sysHousingCd);
		
		// �X�V�I�u�W�F�N�g���쐬
		UpdateExpression[] updateExpression
			= new UpdateExpression[] {new UpdateValue("updUserId", editUserId),
									  new UpdateValue("updDate", new Date())};

		// �e���R�[�h�̃^�C���X�^���v�X�V
		this.housingInfoDAO.updateByCriteria(criteria, updateExpression);

	}
	
	
	
	/**
	 * ����J�ƂȂ�����������œn���ꂽ���������I�u�W�F�N�g�ɐݒ肷��B<br/>
	 * ���̃��\�b�h�́AsearchHousingPk()�AsearchBuilding() �� full �p�����[�^�� false �̏ꍇ��
	 * ���s�����B<br/>
	 * <br/>
	 * �f�t�H���g�d�l�Ƃ��Ē��o�ΏۂɂȂ�ɂ͈ȉ��̏����𖞂����K�v������B<br/>
	 * <ul>
	 *   <li>���J�����ł��鎖�B�i����J�t���O hidden_flg = 1 �̕����X�e�[�^�X��񂪑��݂��Ȃ����B�j</li>
	 * </ul>
	 * <br/>
	 * �������J�X�^�}�C�Y����ꍇ�́A���̃��\�b�h���I�[�o�[���C�h���鎖�B<br/>
	 * <br/>
	 * @param criteria �����Ŏd�l���錟���I�u�W�F�N�g
	 * 
	 */
	protected void addNegativeFilter(DAOCriteria criteria) {

		// ����J�t���O�� null ���@0 �ł��鎖�B
		OrCriteria orCriteria = new OrCriteria();
		orCriteria.addWhereClause(new DAOCriteriaWhereClause("housingStatusInfo", "hiddenFlg", "0", DAOCriteria.EQUALS, false));
		orCriteria.addWhereClause(new DAOCriteriaWhereClause("housingStatusInfo", "hiddenFlg", null, DAOCriteria.IS_NULL, false));
		criteria.addSubCriteria(orCriteria);

	}



	/**
	 * �������I�u�W�F�N�g�̃C���X�^���X�𐶐�����B<br/>
	 * �����A�J�X�^�}�C�Y�ŕ��������\������e�[�u����ǉ������ꍇ�A���̃��\�b�h���I�[�o�[���C�h���鎖�B<br/>
	 * <br/>
	 * @return Housing �̃C���X�^���X
	 */
	public Housing createHousingInstace() {
		return new Housing();
	}
}
