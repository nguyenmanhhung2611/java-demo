package jp.co.transcosmos.dm3.core.model.housing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jp.co.transcosmos.dm3.core.constant.CommonParameters;
import jp.co.transcosmos.dm3.core.model.HousingManage;
import jp.co.transcosmos.dm3.core.model.HousingPartCreator;
import jp.co.transcosmos.dm3.core.model.exception.NotFoundException;
import jp.co.transcosmos.dm3.core.model.housing.form.HousingDtlForm;
import jp.co.transcosmos.dm3.core.model.housing.form.HousingEquipForm;
import jp.co.transcosmos.dm3.core.model.housing.form.HousingForm;
import jp.co.transcosmos.dm3.core.model.housing.form.HousingImgForm;
import jp.co.transcosmos.dm3.core.model.housing.form.HousingSearchForm;
import jp.co.transcosmos.dm3.core.util.ThumbnailCreator;
import jp.co.transcosmos.dm3.core.vo.HousingImageInfo;
import jp.co.transcosmos.dm3.dao.DAO;
import jp.co.transcosmos.dm3.dao.DAOCriteria;

/**
 * �������p Model �̂����������A�T���l�C���摜�쐬 proxy �N���X.
 * ���� proxy �N���X�ł́A�����摜�t�@�C���̃T���l�C���쐬��A�������������̍č\�z�Ȃǂ��s���B<br/>
 * �������p Model �͌ʃJ�X�^�}�C�Y�Ōp���E�Ϗ��ɂ��g�������\��������B<br/>
 * �摜�t�@�C���̑���̓��[���o�b�N�ł��Ȃ��̂ŁA�S�Ă�DB�X�V����������Ɏ��s����K�v������B<br/>
 * �܂��A�������������̍č\�z���S�Ă�DB�X�V���������Ă���K�v������B<br/>
 * ���ׁ̈A�����̏����� proxy ��������s���� Model �{�̂��番������B<br/>
 * <p>
 * <pre>
 * �S����		�C����		�C�����e
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.04.09	�V�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 * ���̃N���X�̓V���O���g���� DI �R���e�i�ɒ�`�����̂ŁA�X���b�h�Z�[�t�ł��鎖�B<br/>
 * 
 */
public class HousingPartThumbnailProxy implements HousingManage {

	/** �Ϗ���ƂȂ镨�� Model �N���X */
	protected HousingManage housingManage;

	/** �����摜���p�@DAO */
	protected DAO<HousingImageInfo> housingImageInfoDAO;

	/** ���ʃp�����[�^�I�u�W�F�N�g */
	protected CommonParameters commonParameters;

	/** ���������������N���X�̃��X�g */
	protected List<HousingPartCreator> partCreateors;

	/** �T���l�C���摜�쐬�N���X */
	protected ThumbnailCreator thumbnailCreator;



	/**
	 * �Ϗ���ƂȂ镨�� Model ��ݒ肷��B<br/>
	 * <br/>
	 * @param housingManage �Ϗ���ƂȂ镨�� Model
	 */
	public void setHousingManage(HousingManage housingManage) {
		this.housingManage = housingManage;
	}

	/**
	 * �����摜���p DAO ��ݒ肷��B<br/>
	 * <br/>
	 * @param housingImageInfoDAO �����摜���p DAO
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
	 * ���������������N���X�̃��X�g��ݒ肷��B<br/>
	 * <br/>
	 * @param partCreateors  ���������������N���X�̃��X�g
	 */
	public void setPartCreateors(List<HousingPartCreator> partCreateors) {
		this.partCreateors = partCreateors;
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
	 * ������{���V�K�o�^���̃t�B���^�[����<br/>
	 * �Ϗ���̕��� model ���g�p���ĕ�����{����o�^��A�������������č\�z����B<br/>
	 * <br/>
	 * @param inputForm ������{���̓��̓t�H�[��
	 * @param editUserId �X�V��ID
	 * 
	 * @return �̔Ԃ��ꂽ�V�X�e������CD
	 */
	@Override
	public String addHousing(HousingForm inputForm, String editUserId)
			throws Exception {

		// ������{���̓o�^�����ֈϏ�����B
		String sysHousingCd = this.housingManage.addHousing(inputForm, editUserId);

		// �������������č\�z����B
// 2015.04.28 H.Mizuno �V�K�o�^���̂����������쐬���Ɏg�p����L�[�̌����C��
//		createPartInfo(inputForm.getSysHousingCd(), "addHousing");
		createPartInfo(sysHousingCd, "addHousing");

		return sysHousingCd;
	}



	/**
	 * ������{���X�V���̃t�B���^�[����<br/>
	 * �Ϗ���̕��� model ���g�p���ĕ�����{�����X�V��A�������������č\�z����B<br/>
	 * <br/>
	 * @param inputForm ������{���̓��̓t�H�[��
	 * @param editUserId �X�V��ID
	 */
	@Override
	public void updateHousing(HousingForm inputForm, String editUserId)
			throws Exception, NotFoundException {

		// ������{���̍X�V�����ֈϏ�����B
		this.housingManage.updateHousing(inputForm, editUserId);

		// �������������č\�z����B
		createPartInfo(inputForm.getSysHousingCd(), "updateHousing");
	}



	/**
	 * �������폜���̃t�B���^�[����<br/>
	 * ���������폜����O�ɁA�폜�ΏۂƂȂ镨���摜�����擾���A�p�X�����W�񂷂�B<br/>
	 * �Ϗ���̕��� model ���g�p���ĕ������̍폜���s���A�Y��������J�����摜�t�@�C�����폜����B<br/>
	 * <br/>
	 * @param inputForm ������{���̓��̓t�H�[��
	 * @param editUserId �X�V��ID
	 */
	@Override
	public void delHousingInfo(HousingForm inputForm, String editUserId)
			throws Exception{

		//�@�摜�폜�p�X���i�[���� Set �I�u�W�F�N�g
		Set<String> delPath = new HashSet<>();

		// �폜�ΏۂƂȂ镨���摜�����Ɏ擾���Ă����B
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", inputForm.getSysHousingCd());
		List<HousingImageInfo> delImgList = this.housingImageInfoDAO.selectByFilter(criteria);

		// �폜�̓t�H���_�P�ʂōs���̂ŁA�摜���̒l�� Set �I�u�W�F�N�g�Ɋi�[���ďd������菜��
		for (HousingImageInfo imgInfo : delImgList){
			if (!delPath.contains(imgInfo.getPathName())){
				delPath.add(imgInfo.getPathName());
			}
		}


		// �Ϗ���� model �����s���ĕ��������폜����B
		this.housingManage.delHousingInfo(inputForm, editUserId);


		// �������̍폜��A���J����Ă��镨���摜�t�@�C�����폜����B
		this.thumbnailCreator.deleteImageDir(getImgOpenRootPath(), delPath);
	}

	
	
	/**
	 * �����ڍ׍X�V���̃t�B���^�[����<br/>
	 * �Ϗ���̕��� model ���g�p���ĕ����ڍ׏���ǉ��E�X�V��A�������������č\�z����B<br/>
	 * <br/>
	 * @param inputForm �����ڍ׏��̓��̓t�H�[��
	 * @param editUserId �X�V��ID
	 */
	@Override
	public void updateHousingDtl(HousingDtlForm inputForm, String editUserId)
			throws Exception, NotFoundException {

		// �����ڍ׏��̍X�V�����ֈϏ�����B
		this.housingManage.updateHousingDtl(inputForm, editUserId);

		// �������������č\�z����B
		createPartInfo(inputForm.getSysHousingCd(), "updateHousingDtl");
	}



	/**
	 * �����ݔ��X�V���̃t�B���^�[����<br/>
	 * �Ϗ���̕��� model ���g�p���ĕ����ݔ������X�V��A�������������č\�z����B<br/>
	 * <br/>
	 * @param inputForm �����ڍ׏��̓��̓t�H�[��
	 * @param editUserId �X�V��ID
	 */
	@Override
	public void updateHousingEquip(HousingEquipForm inputForm, String editUserId)
			throws Exception, NotFoundException {

		// �����ݔ����̍X�V�����ֈϏ�����B
		this.housingManage.updateHousingEquip(inputForm, editUserId);

		// �������������č\�z����B
		createPartInfo(inputForm.getSysHousingCd(), "updateHousingEquip");
	}



	/**
	 * �����摜�ǉ����̃t�B���^�[����<br/>
	 * <br/>
	 * @param inputForm �����ڍ׏��̓��̓t�H�[��
	 * @param editUserId �X�V��ID
	 * 
	 * @return �V���ɒǉ������摜���̃��X�g
	 */
	@Override
	public List<HousingImageInfo> addHousingImg(HousingImgForm inputForm, String editUserId)
			throws Exception, NotFoundException {

		// �Ϗ���̏��������s����B
		// ���̃��\�b�h�̖߂�l�́A�V�K�ǉ������摜���̃��X�g�����A�����B
		List<HousingImageInfo> imgList = this.housingManage.addHousingImg(inputForm, editUserId);

		// �������������č\�z����B
		createPartInfo(inputForm.getSysHousingCd(), "addHousingImg");

		// note
		// �����摜�̃p�X�́A���L�̒ʂ�B
		// /�u�萔�l�v/[�������CD]/[�s���{��CD]/[�s�撬��CD]/[�V�X�e�������ԍ�]/[�T�C�Y]/�V�[�P���X(10��).jpg
		// DB �Ɋi�[����Ă���̂́A[�������CD]�@�` [�V�X�e�������ԍ�]�@�܂ł̒l�B


		// �T���l�C���̍쐬���t�H���_���@�i���t���w�肵���t�H���_�K�w�܂Łj
		String srcRoot = this.commonParameters.getHousImgTempPhysicalPath();
		if (!srcRoot.endsWith("/")) srcRoot += "/";
		srcRoot += inputForm.getTempDate() + "/";


		// �ǉ�������̓f�[�^�O���ł��̃��\�b�h�����s���鎖�͒ʏ�L�蓾�Ȃ����A�ꉞ�A�`�F�b�N���Ă����B
		// �ǉ����ꂽ�����摜��񂪑��݂��Ȃ��ꍇ�� null �𕜋A����B
		if (imgList == null) return null;
		
		
		// �T���l�C�����쐬����t�@�C�����̃}�b�v�I�u�W�F�N�g���쐬����B
		Map<String, String> thumbnailMap = new HashMap<>();
		for (HousingImageInfo imgInfo : imgList){

			// Map �� Key �́A�T���l�C���쐬���̃t�@�C�����i�t���p�X�j
			String key = srcRoot + imgInfo.getFileName();
			// Map �� Value �́A�T���l�C���̏o�͐�p�X�i���[�g�`�V�X�e�������ԍ��܂ł̃p�X�j
			String value = getImgOpenRootPath(imgInfo) + imgInfo.getPathName();

			thumbnailMap.put(key, value);
		}
		// ���t�H���_�̃t�@�C�����g�p���Č��J�t�H���_�փT���l�C�����쐬
		this.thumbnailCreator.create(thumbnailMap);

		return imgList;
	}

	
	
	/**
	 * �����摜�X�V���̃t�B���^�[����<br/>
	 * ���摜�t�@�C���̓���ւ��͔������Ȃ��B�@�폜�̂݁B<br/>
	 * <br/>
	 * @param inputForm �����ڍ׏��̓��̓t�H�[��
	 * @param editUserId �X�V��ID
	 * 
	 * @return �폜�����������摜���
	 */
	@Override
	public List<HousingImageInfo> updHousingImg(HousingImgForm inputForm, String editUserId)
			throws Exception, NotFoundException {

		// �Ϗ���̏��������s����B
		// �폜���ꂽ�����摜���̃��X�g���߂����B
		List<HousingImageInfo> imgList = this.housingManage.updHousingImg(inputForm, editUserId);

		// �������������č\�z����B
		createPartInfo(inputForm.getSysHousingCd(), "updHousingImg");

		
		// null �̏ꍇ�A�폜�摜�����������Ƃ��� null �𕜋A����B
		if (imgList == null) return null;


		// �����摜�̍X�V�����ł́A�V���ȉ摜�t�@�C�����ǉ�����鎖�͖������A
		// �摜�t�@�C���̍폜���s����ꍇ����B
		//�@�Ϗ��惁�\�b�h�̖߂�l�́A�폜���ꂽ�����摜���̃��X�g�Ȃ̂ŁA�Y�����镨���̉摜�t�@�C�����폜����B 
		for (HousingImageInfo imgInfo : imgList){

			String filePath = getImgOpenRootPath(imgInfo) + imgInfo.getPathName();

			// �摜�t�@�C���̍폜���������s����B
			this.thumbnailCreator.deleteImgFile(filePath, imgInfo.getFileName());
		}
		
		return imgList;
	}

	
	
	/**
	 * �����摜�폜���̃t�B���^�[����<br/>
	 * ��updHousingImg() ����ł��폜�B�@�ʍ폜�p�@�\�B<br/>
	 * <br/>
	 * @param inputForm �����ڍ׏��̓��̓t�H�[��
	 * @param editUserId �X�V��ID
	 */
	@Override
	public HousingImageInfo delHousingImg(String sysHousingCd, String imageType, int divNo,	String editUserId)
			throws Exception {

		// �Ϗ���̏��������s���ĕ����摜�����폜����B
		HousingImageInfo imgInfo = this.housingManage.delHousingImg(sysHousingCd, imageType, divNo, editUserId);


		// �������������č\�z����B
		createPartInfo(sysHousingCd, "delHousingImg");


		// �摜�t�@�C���̍폜����
		String filePath = getImgOpenRootPath(imgInfo) + imgInfo.getPathName();
		this.thumbnailCreator.deleteImgFile(filePath, imgInfo.getFileName());

		return imgInfo;
	}

	
	
	/**
	 * �������������̃t�B���^�[����<br/>
	 * ���̂܂܈Ϗ�����B<br/>
	 * <br/>
	 * @param searchForm ���������t�H�[��
	 */
	@Override
	public int searchHousing(HousingSearchForm searchForm) throws Exception {
		// �������̌��������ł́A�Ϗ�������̂܂܎��s����B
		return this.housingManage.searchHousing(searchForm);
	}
	
	/**
	 * �������������̃t�B���^�[����<br/>
	 * ���̂܂܈Ϗ�����B<br/>
	 * <br/>
	 * @param searchForm ���������t�H�[��
	 * @param full false �̏ꍇ�A���J�ΏۊO�����O����B�@true �̏ꍇ�͏��O���Ȃ�
	 */
	@Override
	public int searchHousing(HousingSearchForm searchForm, boolean full)
			throws Exception {
		// �������̌��������ł́A�Ϗ�������̂܂܎��s����B
		return this.housingManage.searchHousing(searchForm, full);
	}


	/**
	 * �����ڍ׎擾�����̃t�B���^�[����<br/>
	 * ���̂܂܈Ϗ�����B<br/>
	 * <br/>
	 * @param sysHousingCd �擾�ΏۃV�X�e������CD
	 */
	@Override
	public Housing searchHousingPk(String sysHousingCd) throws Exception {
		// �������̎�L�[�ɂ�錟�������ł́A�Ϗ�������̂܂܎��s����B
		return this.housingManage.searchHousingPk(sysHousingCd);
	}

	/**
	 * �����ڍ׎擾�����̃t�B���^�[����<br/>
	 * ���̂܂܈Ϗ�����B<br/>
	 * <br/>
	 * @param sysHousingCd �擾�ΏۃV�X�e������CD
	 * @param full false �̏ꍇ�A���J�ΏۊO�����O����B�@true �̏ꍇ�͏��O���Ȃ�
	 */
	@Override
	public Housing searchHousingPk(String sysHousingCd, boolean full)
			throws Exception {
		// �������̎�L�[�ɂ�錟�������ł́A�Ϗ�������̂܂܎��s����B
		return this.housingManage.searchHousingPk(sysHousingCd, full);
	}


	/**
	 * �����g�������X�V�̃t�B���^�[����<br/>
	 * ���̂܂܈Ϗ�����B<br/>
	 * <br/>
	 */
	@Override
	public void updExtInfo(String sysHousingCd,	Map<String, Map<String, String>> inputData, String editUserId)
			throws Exception, NotFoundException {

		// �����g�������̍X�V�����ł́A�Ϗ���̏��������̂܂܎��s����B
		this.housingManage.updExtInfo(sysHousingCd,	inputData, editUserId);
	}

	/**
	 * �����g�������폜�̃t�B���^�[����<br/>
	 * ���̂܂܈Ϗ�����B<br/>
	 * <br/>
	 */
	@Override
	public void delExtInfo(String sysHousingCd, String editUserId)
			throws Exception {

		// �����g�������̍폜�����ł́A�Ϗ���̏��������̂܂܎��s����B
		this.housingManage.delExtInfo(sysHousingCd, editUserId);
	}

	/**
	 * �����g�������X�V�̃t�B���^�[����<br/>
	 * ���̂܂܈Ϗ�����B<br/>
	 * <br/>
	 */
	@Override
	public void updExtInfo(String sysHousingCd, String category, Map<String, String> inputData, String editUserId)
			throws Exception, NotFoundException {

		// �����g�������̍X�V�����ł́A�Ϗ���̏��������̂܂܎��s����B
		this.housingManage.updExtInfo(sysHousingCd, category, inputData, editUserId);
	}

	/**
	 * �����g�������폜�̃t�B���^�[����<br/>
	 * ���̂܂܈Ϗ�����B<br/>
	 * <br/>
	 */
	@Override
	public void delExtInfo(String sysHousingCd, String category, String editUserId)
			throws Exception {

		// �����g�������̍폜�����ł́A�Ϗ���̏��������̂܂܎��s����B
		this.housingManage.delExtInfo(sysHousingCd, category, editUserId);
	}

	/**
	 * �����g�������X�V�̃t�B���^�[����<br/>
	 * ���̂܂܈Ϗ�����B<br/>
	 * <br/>
	 */
	@Override
	public void updExtInfo(String sysHousingCd, String category, String key, String value, String editUserId)
			throws Exception,NotFoundException {

		// �����g�������̍X�V�����ł́A�Ϗ���̏��������̂܂܎��s����B
		this.housingManage.updExtInfo(sysHousingCd, category, key, value, editUserId);
	}

	/**
	 * �����g�������폜�̃t�B���^�[����<br/>
	 * ���̂܂܈Ϗ�����B<br/>
	 * <br/>
	 */
	@Override
	public void delExtInfo(String sysHousingCd, String category, String key, String editUserId)
			throws Exception {

		// �����g�������̍폜�����ł́A�Ϗ���̏��������̂܂܎��s����B
		this.housingManage.delExtInfo(sysHousingCd, category, key, editUserId);
	}


	
	/**
	 * �������I�u�W�F�N�g�̃C���X�^���X�����̃t�B���^�[����<br/>
	 * ���̂܂܈Ϗ�����B<br/>
	 * <br/>
	 * @return Housing �̃C���X�^���X
	 */
	@Override
	public Housing createHousingInstace() {
		// �����I�u�W�F�N�g�̃C���X�^���X�����́A�Ϗ���̏��������̂܂܎��s����B
		return this.housingManage.createHousingInstace();
	}



	/**
	 * �������������č쐬����B<br/>
	 * <br/>
	 * @param sysHousingCd �V�X�e������CD
	 * @param methodName ���s���ꂽ���� model �̃��\�b�h��
	 * 
	 * @throws Exception 
	 */
	protected void createPartInfo(String sysHousingCd, String methodName) throws Exception{

		// ���������������p�̃N���X���ݒ肳��Ă��Ȃ��ꍇ�͂Ȃɂ����Ȃ��B
		if (this.partCreateors == null || this.partCreateors.size() == 0) return;


		// �����̍쐬�ΏۂƂȂ镨�������擾����B
		Housing housing = this.housingManage.searchHousingPk(sysHousingCd, true);

		for (HousingPartCreator creator : this.partCreateors){
			// ���s�ΏۊO�̏ꍇ�͎��̂����������쐬�N���X��
			if (!creator.isExecuteMethod(methodName)) continue;

			// �������������쐬����B
			creator.createPart(housing);
		}
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
	 * �w�肳�ꂽ�����摜���ɊY��������J��ƂȂ镨���摜�� Root �p�X���擾����B<br/>
	 * <br/>
	 * @return ���J����Ă��镨���摜�� Root �p�X���X�g
	 */
	protected String getImgOpenRootPath(HousingImageInfo housingImageInfo){
		// ��{�@�\�ł́A�T���l�C���̏o�͐惋�[�g�t�H���_�͌Œ�l
		String destRoot = this.commonParameters.getHousImgOpenPhysicalPath();
		if (!destRoot.endsWith("/")) destRoot += "/";

		return destRoot;
	}
}
