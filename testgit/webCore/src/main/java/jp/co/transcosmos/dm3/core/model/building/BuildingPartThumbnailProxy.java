package jp.co.transcosmos.dm3.core.model.building;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jp.co.transcosmos.dm3.core.constant.CommonParameters;
import jp.co.transcosmos.dm3.core.model.BuildingManage;
import jp.co.transcosmos.dm3.core.model.HousingManage;
import jp.co.transcosmos.dm3.core.model.HousingPartCreator;
import jp.co.transcosmos.dm3.core.model.building.form.BuildingForm;
import jp.co.transcosmos.dm3.core.model.building.form.BuildingImageInfoForm;
import jp.co.transcosmos.dm3.core.model.building.form.BuildingLandmarkForm;
import jp.co.transcosmos.dm3.core.model.building.form.BuildingSearchForm;
import jp.co.transcosmos.dm3.core.model.building.form.BuildingStationInfoForm;
import jp.co.transcosmos.dm3.core.model.exception.NotFoundException;
import jp.co.transcosmos.dm3.core.model.housing.Housing;
import jp.co.transcosmos.dm3.core.util.ThumbnailCreator;
import jp.co.transcosmos.dm3.core.vo.HousingImageInfo;
import jp.co.transcosmos.dm3.core.vo.HousingInfo;
import jp.co.transcosmos.dm3.dao.DAO;
import jp.co.transcosmos.dm3.dao.DAOCriteria;

/**
 * �������p Model �� proxy �N���X.
 * ���� proxy �N���X�ł́A�����摜�t�@�C���̃T���l�C���쐬��A�������������̍č\�z�Ȃǂ��s���B<br/>
 * �������p Model �͌ʃJ�X�^�}�C�Y�Ōp���E�Ϗ��ɂ��g�������\��������B<br/>
 * �摜�t�@�C���̑���̓��[���o�b�N�ł��Ȃ��̂ŁA�S�Ă�DB�X�V����������Ɏ��s����K�v������B<br/>
 * �܂��A�������������̍č\�z���S�Ă�DB�X�V���������Ă���K�v������B<br/>
 * ���ׁ̈A�����̏����� proxy ��������s���� Model �{�̂��番������B<br/>
 * <p>
 * <pre>
 * �S����		�C����		�C�����e
 * ------------ ----------- -----------------------------------------------------
 * I.Shu		2015.04.14	�V�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 * ���̃N���X�̓V���O���g���� DI �R���e�i�ɒ�`�����̂ŁA�X���b�h�Z�[�t�ł��鎖�B<br/>
 * 
 */
public class BuildingPartThumbnailProxy implements BuildingManage {
	
	/** �Ϗ���ƂȂ錚�� Model �N���X */
	protected BuildingManage buildingManage;
	
	/** ���������������N���X�̃��X�g */
	protected List<HousingPartCreator> partCreateors;
	
	/** ������{���p�@DAO */
	private DAO<HousingInfo> housingInfoDAO;
	
	/** ���� Model �N���X */
	protected HousingManage housingManage;

	/** �T���l�C���摜�쐬�N���X */
	protected ThumbnailCreator thumbnailCreator;

	/** ���ʃp�����[�^�I�u�W�F�N�g */
	protected CommonParameters commonParameters;
	
	/** �����摜���p�@DAO */
	protected DAO<HousingImageInfo> housingImageInfoDAO;
	
	/**
	 * ������{���p�@DAO��ݒ肷��B<br/>
	 * <br/>
	 * @param housingInfoDAO ������{���p�@DAO
	 */
	public void setHousingInfoDAO(DAO<HousingInfo> housingInfoDAO) {
		this.housingInfoDAO = housingInfoDAO;
	}

	/**
	 * �����摜���p�@DAO��ݒ肷��B<br/>
	 * <br/>
	 * @param housingImageInfoDAO �����摜���p�@DAO
	 */
	public void setHousingImageInfoDAO(DAO<HousingImageInfo> housingImageInfoDAO) {
		this.housingImageInfoDAO = housingImageInfoDAO;
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
	 * �T���l�C���摜�쐬�N���X��ݒ肷��B<br/>
	 * <br/>
	 * @param thumbnailCreator �T���l�C���摜�쐬�N���X
	 */
	public void setThumbnailCreator(ThumbnailCreator thumbnailCreator) {
		this.thumbnailCreator = thumbnailCreator;
	}

	/**
	 * ���� Model �N���X��ݒ肷��B<br/>
	 * <br/>
	 * @param housingManage ���� Model �N���X
	 */
	public void setHousingManage(HousingManage housingManage) {
		this.housingManage = housingManage;
	}

	/**
	 * ���������������N���X�̃��X�g��ݒ肷��B<br/>
	 * <br/>
	 * @param partCreateors  ���������������N���X�̃��X�g
	 */
	public void setPartCreateors(List<HousingPartCreator> partCreateors) {
		this.partCreateors = partCreateors;
	}

	/**
	 * �Ϗ���ƂȂ錚�� Model��ݒ肷��B<br/>
	 * <br/>
	 * @param buildingManage �Ϗ���ƂȂ錚�� Model
	 */
	public void setBuildingManage(BuildingManage buildingManage) {
		this.buildingManage = buildingManage;
	}

	/**
	 * ������{���V�K�o�^���̃t�B���^�[����<br/>
	 * ���̂܂܈Ϗ�����B<br/>
	 * <br/>
	 * @param inputForm ������{���̓��͒l���i�[���� Form �I�u�W�F�N�g
	 * @param editUserId ���O�C�����[�U�[ID �i�X�V���p�j
	 * @return �̔Ԃ��ꂽ�V�X�e������CD
	 */
	@Override
	public String addBuilding(BuildingForm inputForm, String editUserId)
			throws Exception {
		// ������{���̓o�^�����ֈϏ�����B
		String sysBuildingCd = this.buildingManage.addBuilding(inputForm, editUserId);
		
		return sysBuildingCd;
	}

	/**
	 * ������{���X�V���̃t�B���^�[����<br/>
	 * �Ϗ���̌��� model ���g�p���Č�����{�����X�V��A�������������č\�z����B<br/>
	 * <br/>
	 * @param inputForm �������̓��͒l���i�[���� Form �I�u�W�F�N�g
	 * @param editUserId ���O�C�����[�U�[ID �i�X�V���p�j
	 */
	@Override
	public void updateBuildingInfo(BuildingForm inputForm, String editUserId)
			throws Exception, NotFoundException {
		// ������{���̍X�V�����ֈϏ�����B
		this.buildingManage.updateBuildingInfo(inputForm, editUserId);
		
		// �������������č\�z����B
		createPartInfo(inputForm.getSysBuildingCd(), "updateBuildingInfo");
		
	}

	/**
	 * ������{���폜���̃t�B���^�[����<br/>
	 * ���������폜����O�ɁA�폜�ΏۂƂȂ镨���摜�����擾���A�p�X�����W�񂷂�B<br/>
	 * �Ϗ���̕��� model ���g�p���Č������̍폜���s���A�Y��������J�����摜�t�@�C�����폜����B<br/>
	 * <br/>
	 * @param sysBuildingCd �폜�ΏۂƂȂ� sysBuildingCd
	 */
	@Override
	public void delBuildingInfo(String sysBuildingCd) throws Exception {
		
		//�@�摜�폜�p�X���i�[���� Set �I�u�W�F�N�g
		Set<String> delPath = new HashSet<>();
		
		// �����̍쐬�ΏۂƂȂ镨�������擾����B
		DAOCriteria criteriaBuilding = new DAOCriteria();
		criteriaBuilding.addWhereClause("sysBuildingCd", sysBuildingCd);
		List<HousingInfo> housingInfoList = this.housingInfoDAO.selectByFilter(criteriaBuilding);
		
		for (HousingInfo housingInfo : housingInfoList) {
			// �폜�ΏۂƂȂ镨���摜�����Ɏ擾���Ă����B
			DAOCriteria criteriaHousing = new DAOCriteria();
			criteriaHousing.addWhereClause("sysHousingCd", housingInfo.getSysHousingCd());
			List<HousingImageInfo> delImgList = this.housingImageInfoDAO.selectByFilter(criteriaHousing);

			// �폜�̓t�H���_�P�ʂōs���̂ŁA�摜���̒l�� Set �I�u�W�F�N�g�Ɋi�[���ďd������菜��
			for (HousingImageInfo imgInfo : delImgList){
				if (!delPath.contains(imgInfo.getPathName())){
					delPath.add(imgInfo.getPathName());
				}
			}
		}

		// ������{���̍폜�����ֈϏ�����B
		this.buildingManage.delBuildingInfo(sysBuildingCd);
		
		// �������̍폜��A���J����Ă��镨���摜�t�@�C�����폜����B
		this.thumbnailCreator.deleteImageDir(getImgOpenRootPath(), delPath);
	}

	/**
	 * ���J��ƂȂ镨���摜�� Root �p�X���擾����B<br/>
	 * Form �I�u�W�F�N�g���ȗ����ꂽ�ꍇ�A�S�Ă̌��J�� Root �t�H���_�𕜋A����B<br/>
	 * <br/>
	 * @return ���J����Ă��镨���摜�� Root �p�X���X�g
	 */
	protected List<String> getImgOpenRootPath(){
		// ��{�@�\�Ƃ��ẮA���J��t�H���_�͂P�̂݁B
		List<String> imgOpenRootList = new ArrayList<>();
		imgOpenRootList.add(this.commonParameters.getHousImgOpenPhysicalPath());
		return imgOpenRootList;
	}
	
	/**
	 * �������������̃t�B���^�[����<br/>
	 * ���̂܂܈Ϗ�����B<br/>
	 * <br/>
	 * @param inputForm ���������t�H�[��
	 * @return �Y������
	 */
	@Override
	public int searchBuilding(BuildingSearchForm searchForm) throws Exception {
		// ������{���̌��������ֈϏ�����B
		return this.buildingManage.searchBuilding(searchForm);
	}

	/**
	 * �������擾�����̃t�B���^�[����<br/>
	 * ���̂܂܈Ϗ�����B<br/>
	 * <br/>
	 * @param inputForm ���������t�H�[��
	 * @return �Y������
	 */
	@Override
	public Building searchBuildingPk(String sysBuildingCd) throws Exception {
		// �������̌��������ֈϏ�����B
		return this.buildingManage.searchBuildingPk(sysBuildingCd);
	}

	
	/**
	 * �Ŋ��w���X�V���̃t�B���^�[����<br/>
	 * �Ϗ���̌��� model ���g�p���čŊ��w�����X�V��A�������������č\�z����B<br/>
	 * <br/>
	 * @param inputForm �Ŋ��w���̓��͒l���i�[���� Form �I�u�W�F�N�g
	 * 
	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	@Override
	public void updateBuildingStationInfo(BuildingStationInfoForm inputForm)
			throws Exception {
		// �Ŋ��w���̍X�V�����ֈϏ�����B
		this.buildingManage.updateBuildingStationInfo(inputForm);
		// �������������č\�z����B
		createPartInfo(inputForm.getSysBuildingCd(), "updateBuildingStationInfo");
	}

	/**
	 * �n����X�V���̃t�B���^�[����<br/>
	 * �Ϗ���̌��� model ���g�p���Ēn������X�V��A�������������č\�z����B<br/>
	 * <br/>
	 * @param inputForm �n����̓��͒l���i�[���� Form �I�u�W�F�N�g
	 * 
	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	@Override
	public void updateBuildingLandmark(BuildingLandmarkForm inputForm)
			throws Exception {
		// �n����̍X�V�����ֈϏ�����B
		this.buildingManage.updateBuildingLandmark(inputForm);
		// �������������č\�z����B
		createPartInfo(inputForm.getSysBuildingCd(), "updateBuildingLandmark");
		
	}

	/**
	 * �����摜�o�^�����̃t�B���^�[����<br/>
	 * ���̂܂܈Ϗ�����B<br/>
	 * <br/>
	 * @param inputForm �摜���t�H�[��
	 */
	@Override
	public void addBuildingImageInfo(BuildingImageInfoForm inputForm)
			throws Exception {
		// �����摜�̓o�^�����ֈϏ�����B
		this.buildingManage.addBuildingImageInfo(inputForm);
		
	}

	/**
	 * �����摜�X�V�����̃t�B���^�[����<br/>
	 * ���̂܂܈Ϗ�����B<br/>
	 * <br/>
	 * @param inputForm �摜���t�H�[��
	 */
	@Override
	public void updBuildingImageInfo(BuildingImageInfoForm inputForm)
			throws Exception {
		// �����摜�̍X�V�����ֈϏ�����B
		this.buildingManage.addBuildingImageInfo(inputForm);
		
	}

	/**
	 * �����摜�폜�����̃t�B���^�[����<br/>
	 * ���̂܂܈Ϗ�����B<br/>
	 * <br/>
	 * @param inputForm �摜���t�H�[��
	 */
	@Override
	public void delBuildingImageInfo(BuildingImageInfoForm inputForm)
			throws Exception {
		// �����摜�̍폜�����ֈϏ�����B
		this.buildingManage.delBuildingImageInfo(inputForm);
		
	}

	/**
	 * �������I�u�W�F�N�g�̃C���X�^���X�𐶐�����t�B���^�[����<br/>
	 * ���̂܂܈Ϗ�����B<br/>
	 * <br/>
	 */
	@Override
	public Building createBuildingInstace() {
		return this.buildingManage.createBuildingInstace();
	}
	
	/**
	 * �������������č쐬����B<br/>
	 * <br/>
	 * @param sysBuildingCd �V�X�e������CD
	 * @param methodName ���s���ꂽ���� model �̃��\�b�h��
	 * 
	 * @throws Exception 
	 */
	protected void createPartInfo(String sysBuildingCd, String methodName) throws Exception{

		// ���������������p�̃N���X���ݒ肳��Ă��Ȃ��ꍇ�͂Ȃɂ����Ȃ��B
		if (this.partCreateors == null || this.partCreateors.size() == 0) return;

		// �����̍쐬�ΏۂƂȂ镨�������擾����B
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("sysBuildingCd", sysBuildingCd);
		List<HousingInfo> housingInfoList = this.housingInfoDAO.selectByFilter(criteria);
		for (HousingInfo housingInfo : housingInfoList) {
			// �����̍쐬�ΏۂƂȂ镨�������擾����B
			Housing housing = this.housingManage.searchHousingPk(housingInfo.getSysHousingCd(), true);
			for (HousingPartCreator creator : this.partCreateors){
				// ���s�ΏۊO�̏ꍇ�͎��̂����������쐬�N���X��
				if (!creator.isExecuteMethod(methodName)) continue;
	
				// �������������쐬����B
				creator.createPart(housing);
			}
		}
	}

}
