package jp.co.transcosmos.dm3.core.model.building;


import java.util.List;

import jp.co.transcosmos.dm3.core.model.BuildingManage;
import jp.co.transcosmos.dm3.core.model.building.form.BuildingForm;
import jp.co.transcosmos.dm3.core.model.building.form.BuildingImageInfoForm;
import jp.co.transcosmos.dm3.core.model.building.form.BuildingLandmarkForm;
import jp.co.transcosmos.dm3.core.model.building.form.BuildingSearchForm;
import jp.co.transcosmos.dm3.core.model.building.form.BuildingStationInfoForm;
import jp.co.transcosmos.dm3.core.model.exception.NotFoundException;
import jp.co.transcosmos.dm3.core.util.ValueObjectFactory;
import jp.co.transcosmos.dm3.core.vo.BuildingInfo;
import jp.co.transcosmos.dm3.core.vo.BuildingLandmark;
import jp.co.transcosmos.dm3.core.vo.BuildingStationInfo;
import jp.co.transcosmos.dm3.dao.DAO;
import jp.co.transcosmos.dm3.dao.DAOCriteria;
import jp.co.transcosmos.dm3.dao.JoinResult;
import jp.co.transcosmos.dm3.dao.NotEnoughRowsException;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataIntegrityViolationException;

/**
 * <pre>
 * ���������e�i���X�p Model �N���X
 * 
 * �S����       �C����      �C�����e
 * ------------ ----------- -----------------------------------------------------
 * I.Shu		2015.02.27	�V�K�쐬
 * 
 * ���ӎ���
 * 
 * </pre>
 */
public class BuildingManageImpl implements BuildingManage {

	private static final Log log = LogFactory.getLog(BuildingManageImpl.class);

	/** VO �̃C���X�^���X�𐶐�����ꍇ�̃t�@�N�g���[ */
	protected ValueObjectFactory valueObjectFactory;

	/** ������{���p DAO */
	protected DAO<BuildingInfo> buildingInfoDAO;
	
	/** �������ꗗ�擾�p DAO */
	protected DAO<JoinResult> buildingListDAO;
	
	/** ������{���擾�p DAO�i�����{���j */
	protected DAO<JoinResult> buildingInfoDetailDAO;
	
	/** �Ŋ��w���擾�p DAO�i�����{���j */
	protected DAO<JoinResult> buildingStationInfoListDAO;
	
	/** ���������h�}�[�N���擾�p DAO�i�����{���j */
	protected DAO<BuildingLandmark> buildingLandmarkDAO;
	
	/** �Ŋ��w���p DAO */
	protected DAO<BuildingStationInfo> buildingStationInfoDAO;
	
	/** �s���{���}�X�^�e�[�u���̕ʖ� */
	public static final String PREF_MST_ALIA = "prefMst";
	
	/** ������{���e�[�u���̕ʖ� */
	public static final String BUILDING_INFO_ALIA = "buildingInfo";
	
	/** �Ŋ��w���e�[�u���̕ʖ� */
	public static final String BUILDING_STATION_INFO_ALIA = "buildingStationInfo";
	
	/** �H���}�X�^�e�[�u���̕ʖ� */
	public static final String ROUTE_MST = "routeMst";
	
	/** �w�}�X�^�e�[�u���̕ʖ� */
	public static final String STATION_MST = "stationMst";
	
	/**
	 * �o���[�I�u�W�F�N�g�̃C���X�^���X�𐶐�����t�@�N�g���[��ݒ肷��B<br/>
	 * <br/>
	 * @param valueObjectFactory �o���[�I�u�W�F�N�g�̃t�@�N�g���[
	 */
	public void setValueObjectFactory(ValueObjectFactory valueObjectFactory) {
		this.valueObjectFactory = valueObjectFactory;
	}
	
	/**
	 * �p�����[�^�œn���ꂽ Form �̏��Ō�����{����V�K�o�^����B<br/>
	 * �o���f�[�V�������̃`�F�b�N�����͌Ăяo�����ŗ\�ߎ��{���Ă������B<br/>
	 * <br/>
	 * @param inputForm ������{���̓��͒l���i�[���� Form �I�u�W�F�N�g
	 * @param editUserId ���O�C�����[�U�[ID �i�X�V���p�j
	 * @return �̔Ԃ��ꂽ�V�X�e������CD
	 */
	@Override
	public String addBuilding(BuildingForm inputForm, String editUserId) {
    	// �V�K�o�^�����̏ꍇ�A���̓t�H�[���̒l��ݒ肷��o���[�I�u�W�F�N�g�𐶐�����B
		// �o���[�I�u�W�F�N�g�́A�t�@�N�g���[���\�b�h�ȊO�ł͐������Ȃ����B
		// �i�p�����ꂽ�o���[�I�u�W�F�N�g���g�p����Ȃ��Ȃ�ׁB�j
		BuildingInfo buildingInfo = (BuildingInfo) this.valueObjectFactory.getValueObject("BuildingInfo");


    	// �t�H�[���̓��͒l���o���[�I�u�W�F�N�g�ɐݒ肷��B
    	inputForm.copyToBuildingInfo(buildingInfo, editUserId);
		
    	// �V�K�o�p�̃^�C���X�^���v����ݒ肷��B�@�i�X�V���̐ݒ����]�L�j
    	buildingInfo.setInsDate(buildingInfo.getUpdDate());
    	buildingInfo.setInsUserId(editUserId);


		// ������L�[�l�œo�^����ˑ��\�����݂���ׁA�\�ߎ�L�[�̒l���擾���Ă����B
		String sysBuildingCd = (String) this.buildingInfoDAO
				.allocatePrimaryKeyIds(1)[0].toString();


		// �擾������L�[�l�Ō�����{����o�^
		buildingInfo.setSysBuildingCd(sysBuildingCd);
		this.buildingInfoDAO.insert(new BuildingInfo[] { buildingInfo });

		return sysBuildingCd;
	}

	/**
	 * �p�����[�^�œn���ꂽ Form �̏��Ō�����{�����X�V����B<br/>
	 * �o���f�[�V�������̃`�F�b�N�����͌Ăяo�����ŗ\�ߎ��{���Ă������B<br/>
	 * BuildingForm �� sysBuildingCd �v���p�e�B�ɐݒ肳�ꂽ�l����L�[�l�Ƃ��čX�V����B<br/>
	 * <br/>
	 * @param inputForm �������̓��͒l���i�[���� Form �I�u�W�F�N�g
	 * @param editUserId ���O�C�����[�U�[ID �i�X�V���p�j
	 * 
	 */
	@Override
	public void updateBuildingInfo(BuildingForm inputForm, String editUserId)
			throws NotFoundException {

    	// �X�V�����̏ꍇ�A�X�V�Ώۃf�[�^���擾����B
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("sysBuildingCd", inputForm.getSysBuildingCd());

		List<BuildingInfo> buildingInfos = this.buildingInfoDAO.selectByFilter(criteria);

        // �Y������f�[�^�����݂��Ȃ��ꍇ�́A��O���X���[����B
		if (buildingInfos == null || buildingInfos.size() == 0) {
			throw new NotFoundException();
		}    	

		
        // ���������擾���A���͂����l�ŏ㏑������B
		BuildingInfo buildingInfo = buildingInfos.get(0);

    	inputForm.copyToBuildingInfo(buildingInfo, editUserId);

		// �������̍X�V
		this.buildingInfoDAO.update(new BuildingInfo[]{buildingInfo});

	}

	/**
	 * �p�����[�^�œn���ꂽ Form �̏��Ō��������폜����B<br/>
	 * �o���f�[�V�������̃`�F�b�N�����͌Ăяo�����ŗ\�ߎ��{���Ă������B<br/>
	 * BuildingForm �� sysBuildingCd �v���p�e�B�ɐݒ肳�ꂽ�l����L�[�l�Ƃ��č폜����B
	 * �܂��A�폜�Ώۃ��R�[�h�����݂��Ȃ��ꍇ�ł�����I���Ƃ��Ĉ������B<br/>
	 * <br/>
	 * @param sysBuildingCd �폜�ΏۂƂȂ� sysBuildingCd
	 * 
	 */
	@Override
	public void delBuildingInfo(String sysBuildingCd) {
		// ���������폜��������𐶐�����B
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("sysBuildingCd", sysBuildingCd);
		
		// ������{�����폜����
		this.buildingInfoDAO.deleteByFilter(criteria);
	}

	/**
	 * ���������������A���ʃ��X�g�𕜋A����B�i�ꗗ�p�j<br/>
	 * �����œn���ꂽ Form �p�����[�^�̒l�Ō��������𐶐����A����������������B<br/>
	 * �������ʂ� Form �I�u�W�F�N�g�Ɋi�[����A�擾�����Y��������߂�l�Ƃ��ĕ��A����B<br/>
	 * <br/>
	 * @param searchForm ���������A����сA�������ʂ̊i�[�I�u�W�F�N�g
	 * @return �Y������
	 */
	@Override
	public int searchBuilding(BuildingSearchForm searchForm) {

		// ��������������������𐶐�����B
		DAOCriteria criteria = searchForm.buildCriteria();

		// �����̌���
		List<JoinResult> buildingList;
		try {
			buildingList = this.buildingListDAO.selectByFilter(criteria);

		} catch (NotEnoughRowsException err) {

			int pageNo = (err.getMaxRowCount() - 1)
					/ searchForm.getRowsPerPage() + 1;
			log.warn("resetting page to " + pageNo);
			searchForm.setSelectedPage(pageNo);
			criteria = searchForm.buildCriteria();
			buildingList = this.buildingListDAO.selectByFilter(criteria);
		}

		searchForm.setRows(buildingList);

		return buildingList.size();
	}

	/**
	 * ���N�G�X�g�p�����[�^�œn���ꂽ�V�X�e������CD �i��L�[�l�j�ɊY�����錚�����𕜋A����B<br/>
	 * BuildingSearchForm �� searchForm �v���p�e�B�ɐݒ肳�ꂽ�l����L�[�l�Ƃ��ď����擾����B
	 * �����Y���f�[�^��������Ȃ��ꍇ�Anull �𕜋A����B<br/>
	 * <br/>
	 * @param searchForm�@�������ʂƂȂ� JoinResult
	 * @return�@DB ����擾�����������̃o���[�I�u�W�F�N�g
	 */
	@Override
	public Building searchBuildingPk(String sysBuildingCd) {
		
		// �������̃C���X�^���X�𐶐�����B
		Building building = createBuildingInstace();
		

		// ������{�����擾
		// confMainData() �́A�P�΂P�̊֌W�ɂ���֘A�e�[�u���̏����擾���Abuilding �֐ݒ肷��B
		// �����Y������f�[�^�������ꍇ�Abuilding �։����ݒ肹���� null �𕜋A���Ă���̂ŁA�߂�l��
		// null �̏ꍇ�͏����𒆒f����B
		if (confMainData(building, sysBuildingCd) == null) return null; 
		
		// �����Ŋ��w�����擾����B
		// ���̏��́A�w�}�X�^�ƌ������ĕ\�����Ń\�[�g���� JoinResult ���i�[�����B
		confBuldingStation(building, sysBuildingCd);

		// ���������h�}�[�N�����擾����B
		confBuildingLandmark(building, sysBuildingCd);
		
		return building;

	}

	/**
	 * �����ڊ�{�������擾���ABuilding �I�u�W�F�N�g�֐ݒ肷��B<br/>
	 * <br/>
	 * @param building �l�̐ݒ��ƂȂ� building �I�u�W�F�N�g
	 * @param sysBuildingCd �擾�ΏۃV�X�e������CD
	 * 
	 * @return �擾���� �i�Y���Ȃ��̏ꍇ�Anull�j
	 */
	protected JoinResult confMainData(Building building, String sysBuildingCd){

		// ���������擾����ׂ̎�L�[��ΏۂƂ������������𐶐�����B
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("sysBuildingCd", sysBuildingCd);

		// ������{�����擾
		List<JoinResult> buildingInfoDetail = this.buildingInfoDetailDAO
				.selectByFilter(criteria);
		if (buildingInfoDetail.size() == 0) return null;

		building.setBuildingInfo(buildingInfoDetail.get(0));

		return buildingInfoDetail.get(0);
	}
	
	
	/**
	 * �������p DAO��ݒ肷��B<br/>
	 * <br/>
	 * 
	 * @return �������p DAO
	 */
	public void setBuildingInfoDAO(DAO<BuildingInfo> buildingInfoDAO) {
		this.buildingInfoDAO = buildingInfoDAO;
	}
	
	/**
	 * �������ꗗ�p DAO��ݒ肷��B<br/>
	 * <br/>
	 * 
	 * @return �������p DAO
	 */
	public void setBuildingListDAO(DAO<JoinResult> buildingListDAO) {
		this.buildingListDAO = buildingListDAO;
	}

	/**
	 * �Ŋ��w���pDAO��ݒ肷��B<br/>
	 * <br/>
	 * @param buildingStationInfoDao �Ŋ��w���pDAO
	 */
	public void setBuildingStationInfoDao(DAO<BuildingStationInfo> buildingStationInfoDao) {
		this.buildingStationInfoDAO = buildingStationInfoDao;
	}
	
	/**
	 * �p�����[�^�œn���ꂽ Form �̏��ōŊ��w�����X�V����B<br/>
	 * �o���f�[�V�������̃`�F�b�N�����͌Ăяo�����ŗ\�ߎ��{���Ă������B<br/>
	 * BuildingStationInfoForm �� sysBuildingCd �v���p�e�B�ɐݒ肳�ꂽ�l����L�[�l�Ƃ��čX�V����B<br/>
	 * <br/>
	 * @param inputForm �Ŋ��w���̓��͒l���i�[���� Form �I�u�W�F�N�g
	 * 
	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	@Override
	public void updateBuildingStationInfo(BuildingStationInfoForm inputForm) throws Exception {
		// ��x�폜
		// �폜����
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("sysBuildingCd", inputForm.getSysBuildingCd());
		this.buildingStationInfoDAO.deleteByFilter(criteria);
		// �ύX��̍Ŋ��w����V�K�o�^����
		// ���R�[�h�����擾����
		int length = 0;
		for (String routeCd : inputForm.getDefaultRouteCd()) {
			if (StringValidateUtil.isEmpty(routeCd)) {
				continue;
			}
			length++;
		}
		// �Ŋ��w���z��̍쐬
		BuildingStationInfo[] buildingStationInfos =(BuildingStationInfo[]) this.valueObjectFactory.getValueObject(
				"BuildingStationInfo", length); 
		
		inputForm.copyToBuildingStationInfo(buildingStationInfos, length);
		try {
			this.buildingStationInfoDAO.insert(buildingStationInfos);
		} catch (DataIntegrityViolationException e) {
			throw new NotFoundException();
		}

	}

	/**
	 * �p�����[�^�œn���ꂽ Form �̏��Œn������X�V����B<br/>
	 * �o���f�[�V�������̃`�F�b�N�����͌Ăяo�����ŗ\�ߎ��{���Ă������B<br/>
	 * BuildingLandmarkForm �� sysBuildingCd �v���p�e�B�ɐݒ肳�ꂽ�l����L�[�l�Ƃ��čX�V����B<br/>
	 * <br/>
	 * @param inputForm �n����̓��͒l���i�[���� Form �I�u�W�F�N�g
	 * 
	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	@Override
	public void updateBuildingLandmark(BuildingLandmarkForm inputForm)
			throws Exception {
		// ��x�폜
		// �폜����
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("sysBuildingCd", inputForm.getSysBuildingCd());
		this.buildingLandmarkDAO.deleteByFilter(criteria);
		// �ύX��̍Ŋ��w����V�K�o�^����
		// ���R�[�h�����擾����
		int length = 0;
		for (String landmarkName : inputForm.getLandmarkName()) {
			if (StringValidateUtil.isEmpty(landmarkName)) {
				continue;
			}
			length++;
		}
		// �n����z��̍쐬
		BuildingLandmark[] buildingLandmarks =(BuildingLandmark[]) this.valueObjectFactory.getValueObject(
				"BuildingLandmark", length); 
		
		// �n����z��̏�����
		inputForm.copyToBuildingLandmark(buildingLandmarks, length);
		try{
			this.buildingLandmarkDAO.insert(buildingLandmarks);
		} catch (DataIntegrityViolationException e){
			throw new NotFoundException();
		}
		
		
	}

	/**
	 * ������{���擾�p DAO�i�����{���j��ݒ肷��B<br/>
	 * <br/>
	 * @param buildingInfoDetailDAO ������{���擾�p DAO�i�����{���j
	 */
	public void setBuildingInfoDetailDAO(DAO<JoinResult> buildingInfoDetailDAO) {
		this.buildingInfoDetailDAO = buildingInfoDetailDAO;
	}

	/**
	 * ���w���擾�p DAO�i�����{���j��ݒ肷��B<br/>
	 * <br/>
	 * @param buildingStationInfoListDAO ���w���擾�p DAO�i�����{���j
	 */
	public void setBuildingStationInfoListDAO(
			DAO<JoinResult> buildingStationInfoListDAO) {
		this.buildingStationInfoListDAO = buildingStationInfoListDAO;
	}

	/**
	 * ���������h�}�[�N���擾�p DAO�i�����{���j��ݒ肷��B<br/>
	 * <br/>
	 * @param buildingLandmarkDAO ���������h�}�[�N���擾�p DAO�i�����{���j
	 */
	public void setBuildingLandmarkDAO(DAO<BuildingLandmark> buildingLandmarkDAO) {
		this.buildingLandmarkDAO = buildingLandmarkDAO;
	}

	/**
	 * �������I�u�W�F�N�g�̃C���X�^���X�𐶐�����B<br/>
	 * �����A�J�X�^�}�C�Y�Ō��������\������e�[�u����ǉ������ꍇ�A���̃��\�b�h���I�[�o�[���C�h���鎖�B<br/>
	 * <br/>
	 * @return Building �̃C���X�^���X
	 */
	public Building createBuildingInstace() {
		return new Building();
	}

	/**
	 * �����Ŋ��w�����擾����B<br/>
 	 * �����Ŋ��w���ƁA�w�}�X�^�A�Ŋ��w�}�X�^���������A�����Ŋ��w���̕\�����Ń\�[�g�������ʂ� building
 	 * �֐ݒ肷��B<br/>
	 * <br/>
	 * @param building �l�̐ݒ��ƂȂ� Building �I�u�W�F�N�g
	 * @param sysBuildingCd �擾�ΏۃV�X�e������CD �ɊY�����镨����{���̃V�X�e������CD
	 * 
	 * @return �擾����
	 */
	protected List<JoinResult> confBuldingStation(Building building, String sysBuildingCd){

		// �����Ŋ��w���̎擾
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("sysBuildingCd", sysBuildingCd);
		criteria.addOrderByClause("sortOrder");
		
		// �Ŋ��w�����擾
		List<JoinResult> list = this.buildingStationInfoListDAO
				.selectByFilter(criteria);
		building.setBuildingStationInfoList(list);

		return list;
	}

	/**
	 * ���������h�}�[�N�����擾����B<br/>
 	 * �擾�������������h�}�[�N���� building �֐ݒ肷��B<br/>
	 * <br/>
	 * @param building �l�̐ݒ��ƂȂ� Building �I�u�W�F�N�g
	 * @param sysBuildingCd �擾�ΏۃV�X�e������CD �ɊY�����錚����{���̃V�X�e������CD
	 * 
	 * @return �擾����
	 */
	protected List<BuildingLandmark> confBuildingLandmark(Building building, String sysBuildingCd){

		// ���������h�}�[�N���̎擾����
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("sysBuildingCd", sysBuildingCd);
		criteria.addOrderByClause("sortOrder");
		
		// ���������h�}�[�N���̎擾
		List<BuildingLandmark> list = this.buildingLandmarkDAO.selectByFilter(criteria);
		building.setBuildingLandmarkList(list);

		return list;
	}

	/**
	 * �p�����[�^�œn���ꂽ Form �̏��Ō����摜����V�K�o�^����B<br/>
	 * �o���f�[�V�������̃`�F�b�N�����͌Ăяo�����ŗ\�ߎ��{���Ă������B<br/>
	 * <br/>
	 * @param inputForm �����摜���̓��͒l���i�[���� Form �I�u�W�F�N�g
	 * 
	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	@Override
	public void addBuildingImageInfo(BuildingImageInfoForm inputForm)
			throws Exception {
		// ��U�������Ȃ�

	}

	/**
	 * �p�����[�^�œn���ꂽ Form �̏��Ō����摜�����X�V����B<br/>
	 * �o���f�[�V�������̃`�F�b�N�����͌Ăяo�����ŗ\�ߎ��{���Ă������B<br/>
	 * <br/>
	 * @param inputForm �����摜���̓��͒l���i�[���� Form �I�u�W�F�N�g
	 * 
	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	@Override
	public void updBuildingImageInfo(BuildingImageInfoForm inputForm)
			throws Exception {
		// ��U�������Ȃ�

	}
	
	/**
	 * �p�����[�^�œn���ꂽ Form �̏��Ō����摜�����폜����B<br/>
	 * �o���f�[�V�������̃`�F�b�N�����͌Ăяo�����ŗ\�ߎ��{���Ă������B<br/>
	 * <br/>
	 * @param inputForm �����摜���̓��͒l���i�[���� Form �I�u�W�F�N�g
	 * 
	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	@Override
	public void delBuildingImageInfo(BuildingImageInfoForm inputForm)
			throws Exception {
		// ��U�������Ȃ�

	}
}
