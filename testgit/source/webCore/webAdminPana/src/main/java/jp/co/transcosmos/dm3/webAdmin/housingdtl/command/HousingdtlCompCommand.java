package jp.co.transcosmos.dm3.webAdmin.housingdtl.command;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.model.adminUser.AdminUserInterface;
import jp.co.transcosmos.dm3.core.model.building.BuildingPartThumbnailProxy;
import jp.co.transcosmos.dm3.core.model.exception.NotFoundException;
import jp.co.transcosmos.dm3.core.model.housing.Housing;
import jp.co.transcosmos.dm3.core.util.AdminLoginUserUtils;
import jp.co.transcosmos.dm3.core.util.ImgUtils;
import jp.co.transcosmos.dm3.core.vo.BuildingDtlInfo;
import jp.co.transcosmos.dm3.core.vo.BuildingInfo;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonParameters;
import jp.co.transcosmos.dm3.corePana.model.building.form.PanaBuildingForm;
import jp.co.transcosmos.dm3.corePana.model.building.form.PanaBuildingFormFactory;
import jp.co.transcosmos.dm3.corePana.model.housing.PanaHousingPartThumbnailProxy;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingDtlInfoForm;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingForm;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingFormFactory;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingInfoForm;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingSearchForm;
import jp.co.transcosmos.dm3.corePana.util.PanaCommonUtil;
import jp.co.transcosmos.dm3.corePana.util.PanaFileUtil;
import jp.co.transcosmos.dm3.corePana.util.PanaStringUtils;
import jp.co.transcosmos.dm3.corePana.vo.HousingInfo;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

import org.apache.commons.io.FileUtils;
import org.springframework.web.servlet.ModelAndView;

/**
 * <pre>
 * �����ڍ׏��o�^����
 * <p>
 * <ul>
 * <li>���N�G�X�g�p�����[�^���󂯎��A�o���f�[�V���������s����B</li>
 * <li>�o���f�[�V����������I�������ꍇ�A�����ڍ׏���o�^����B</li>
 * </ul>
 * <br/>
 * �y���A���� View ���z<br/>
 * <ul>
 * <li>success</li>:����I���i���_�C���N�g�y�[�W�j
 * <li>input</li>:�o���f�[�V�����G���[�ɂ��ē���
 * <li>redirect</li>:redirect��ʕ\��
 * <li>comp</li>:������ʕ\��
 * </ul>
 * <p>
 *
 * �S����       �C����     �C�����e
 * ------------ ----------- -----------------------------------------------------
 * liu.yandong  2015.04.06  �V�K�쐬
 *
 * ���ӎ���
 *
 * </pre>
 */
public class HousingdtlCompCommand implements Command {
	/** �������[�h (edit = �ҏW�AeditBack = �ĕҏW) */
	private String mode;

	/** �������p Model �I�u�W�F�N�g */
	private PanaHousingPartThumbnailProxy panaHousingManage;


	private BuildingPartThumbnailProxy buildingManager;


	/** ���ʃp�����[�^�I�u�W�F�N�g */
	protected PanaCommonParameters commonParameters;


	/** �T���l�C���摜�쐬�N���X */
	private ImgUtils imgUtils;


	/**
	 * @param commonParameters �Z�b�g���� commonParameters
	 */
	public void setCommonParameters(PanaCommonParameters commonParameters) {
		this.commonParameters = commonParameters;
	}

	public void setBuildingManager(BuildingPartThumbnailProxy buildingManager) {
		this.buildingManager = buildingManager;
	}

	/**
	 * @param imgUtils �Z�b�g���� imgUtils
	 */
	public void setImgUtils(ImgUtils imgUtils) {
		this.imgUtils = imgUtils;
	}

	/**
	 * �������[�h��ݒ肷��<br/>
	 * <br/>
	 *
	 * @param mode "edit" = �ҏW "editBack" = �ĕҏW
	 */
	public void setMode(String mode) {
		this.mode = mode;
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
	 * �����ڍ׏��o�^����<br>
	 * <br>
	 *
	 * @param request
	 *            HTTP ���N�G�X�g
	 * @param response
	 *            HTTP ���X�|���X
	 */
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// ���N�G�X�g�p�����[�^���i�[���� model �I�u�W�F�N�g�𐶐�����B
		// ���̃I�u�W�F�N�g�� View �w�ւ̒l���n���Ɏg�p�����B
		Map<String, Object> model = createModel(request);
		PanaHousingDtlInfoForm inputForm = (PanaHousingDtlInfoForm) model.get("inputForm");

		// ���O�C�����[�U�[�̏����擾����B�@�i�^�C���X�^���v�̍X�V�p�j
		AdminUserInterface loginUser = AdminLoginUserUtils.getInstance(request).getLoginUserInfo(request, response);

		// view ���̏����l��ݒ�
		String viewName = "success";

		String command = inputForm.getCommand();

		if (command != null && command.equals("redirect")) {
			return new ModelAndView("comp", model);
		}
		// �o���f�[�V���������s
		List<ValidationFailure> errors = new ArrayList<ValidationFailure>();
		if (!inputForm.validate(errors)) {
			// �o���f�[�V�����G���[���́A�G���[���� model �ɐݒ肵�A���͉�ʂ�\������B
			model.put("inputForm", inputForm);
			model.put("errors", errors);
			viewName = "input";
			return new ModelAndView(viewName, model);
		}


		// �e�폈�������s
		execute(inputForm, loginUser);

		model.put("inputForm", inputForm);

		return new ModelAndView(viewName, model);

	}

	/**
	 * �����̐U�蕪���Ǝ��s���s���B<br/>
	 * <br/>
	 *
	 * @param inputForm
	 *            ���͒l���i�[���ꂽ Form �I�u�W�F�N�g
	 * @param loginUser
	 *            �^�C���X�^���v�X�V���Ɏg�p���郍�O�C�����[�U�[���
	 *
	 * @exception Exception
	 *                �����N���X�ɂ��X���[�����C�ӂ̗�O
	 * @exception NotFoundException
	 *                �X�V�Ώۂ����݂��Ȃ��ꍇ
	 */
	private void execute(PanaHousingDtlInfoForm inputForm, AdminUserInterface loginUser)
			throws Exception, NotFoundException {

		if ("update".equals(this.mode)) {
			// �o�^����
			update(inputForm, loginUser);
		}
	}


	/**
	 * �o�^����<br/>
	 * �����œn���ꂽ���e�Ń��t�H�[������ǉ�����B<br/>
	 * <br/>
	 *
	 * @param inputForm
	 *            ���͒l���i�[���ꂽ Form �I�u�W�F�N�g
	 * @param loginUser
	 *            �^�C���X�^���v�X�V���Ɏg�p���郍�O�C�����[�U�[���
	 *
	 * @exception Exception
	 *                �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	private void update(PanaHousingDtlInfoForm inputForm, AdminUserInterface loginUser)
			throws Exception {

		// ���O�`�F�b�N
		// ���������擾����B
		Housing housing = this.panaHousingManage.searchHousingPk(inputForm.getSysHousingCd(), true);
		// �f�[�^�̑��݂��Ȃ��ꍇ�B
		if (housing == null) {
			throw new NotFoundException();
		}


		/** ������{���e�[�u���X�V����  **/
        // ������{�����擾����B
        HousingInfo housingInfo = ((HousingInfo) housing.getHousingInfo().getItems().get("housingInfo"));
		// ���N�G�X�g�p�����[�^���擾���� Form �I�u�W�F�N�g���쐬����B
		PanaHousingFormFactory panaHousingFormFactory = new PanaHousingFormFactory();
		// PanaBuildingForm��ݒ肷��
		PanaHousingForm panaHousingForm = panaHousingFormFactory.createPanaHousingForm();
		PanaCommonUtil.copyProperties(panaHousingForm, housingInfo);
		//��ʃt�H�[���l��ݒ�
		panaHousingForm.setSysHousingCd(inputForm.getSysHousingCd());
		panaHousingForm.setDisplayParkingInfo(inputForm.getDisplayParkingInfo());
		panaHousingForm.setFloorNo(inputForm.getFloorNo());
		panaHousingForm.setFloorNoNote(inputForm.getFloorNoNote());
		panaHousingForm.setUpkeep(inputForm.getUpkeep());
		panaHousingForm.setMenteFee(inputForm.getMenteFee());
		panaHousingForm.setBasicComment(inputForm.getBasicComment());
		panaHousingForm.setReformComment(inputForm.getReformComment());
		//������{���e�[�u����update����
		this.panaHousingManage.updateHousing(panaHousingForm, String.valueOf(loginUser.getUserId()));


		/** ������{���e�[�u���X�V����  **/
		// ������{�����擾����B
		BuildingInfo buildingInfo = (BuildingInfo)housing.getBuilding().getBuildingInfo().getItems().get("buildingInfo");
		// ���N�G�X�g�p�����[�^���擾���� Form �I�u�W�F�N�g���쐬����B
		PanaBuildingFormFactory panaBuildingFormFactory = new PanaBuildingFormFactory();
		// PanaBuildingForm��ݒ肷��
		PanaBuildingForm panaBuildingForm = panaBuildingFormFactory.createPanaBuildingForm();
		PanaCommonUtil.copyProperties(panaBuildingForm, buildingInfo);
		//��ʃt�H�[���l��ݒ�
		panaBuildingForm.setSysBuildingCd(inputForm.getSysBuildingCd());
		panaBuildingForm.setTotalFloors(inputForm.getTotalFloors());
		// �v�H�N����ݒ�
		SimpleDateFormat beforeFormartDate = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat afterFormartDate = new SimpleDateFormat("yyyyMM");
		String strDate = panaBuildingForm.getCompDate();
		if (!StringValidateUtil.isEmpty(strDate)) {
			Date date = beforeFormartDate.parse(strDate);
			panaBuildingForm.setCompDate(afterFormartDate.format(date));
		}

		//������{���e�[�u����update����
		this.buildingManager.updateBuildingInfo(panaBuildingForm, String.valueOf(loginUser.getUserId()));


		/** �����ڍ׏��e�[�u���X�V����  **/
		//�����ڍ׏��e�[�u����update����
		this.panaHousingManage.updateHousingDtl(inputForm, String.valueOf(loginUser.getUserId()));


		/** �����ڍ׏��e�[�u���X�V����  **/
		// �����ڍ׏����擾����B
		BuildingDtlInfo buildingDtlInfo = (BuildingDtlInfo)housing.getBuilding().getBuildingInfo().getItems().get("buildingDtlInfo");
		// ���N�G�X�g�p�����[�^���擾���� Form �I�u�W�F�N�g���쐬����B
		PanaHousingInfoForm panaHousingInfoForm = panaHousingFormFactory.createPanaHousingInfoForm();
		PanaCommonUtil.copyProperties(panaHousingInfoForm, buildingDtlInfo);
		//��ʃt�H�[���l��ݒ�
		panaHousingInfoForm.setSysBuildingCd(inputForm.getSysBuildingCd());
		panaHousingInfoForm.setCoverageMemo(inputForm.getCoverageMemo());
		panaHousingInfoForm.setBuildingRateMemo(inputForm.getBuildingRateMemo());
		//�����ڍ׏��e�[�u���̍X�V����
		this.panaHousingManage.updateBuildingDtlInfo(panaHousingInfoForm, String.valueOf(loginUser.getUserId()));

		// [�������CD]/[�s���{��CD]/[�s�撬��CD]/�V�X�e�������ԍ�/
		String prefCd="";
		String addressCd="";
		if (StringValidateUtil.isEmpty(buildingInfo.getPrefCd())) {
			prefCd = "99";
		} else {
			prefCd = buildingInfo.getPrefCd();
		}
		if (StringValidateUtil.isEmpty(buildingInfo.getAddressCd())) {
			addressCd = "99999";
		} else {
			addressCd = buildingInfo.getAddressCd();
		}
		String upPath = buildingInfo.getHousingKindCd() + "/" +
						prefCd + "/" +
						addressCd + "/" +
						housingInfo.getSysHousingCd() + "/";

		/** ���X�^�b�t�摜�̃t�@�C�����폜���邽�߁A�����g�����������擾����B **/
        Map<String, Map<String, String>> extMap = housing.getHousingExtInfos();
        // �J�e�S�����ɊY������ Map ���擾����B
        Map<String, String> cateMap = extMap.get("housingDetail");
        String oldStaffImagePathName = "";
        String oldStaffImageFileName = "";
        if (cateMap != null) {
            oldStaffImagePathName = cateMap.get("staffImagePathName");
            oldStaffImageFileName = cateMap.get("staffImageFileName");
        }

		//�S���Ҏʐ^�A�b�v���[�h
		if (StringValidateUtil.isEmpty(inputForm.getPictureDataDelete()) &&
				!StringValidateUtil.isEmpty(inputForm.getPictureUpFlg()) &&
				!StringValidateUtil.isEmpty(inputForm.getPictureDataPath()) &&
				!StringValidateUtil.isEmpty(inputForm.getPictureDataFileName())) {

			/** �T���l�C�����쐬����t�@�C�����̃}�b�v�I�u�W�F�N�g���쐬����B **/
			Map<String, String> thumbnailMap = new HashMap<>();

			// Map �� Key �́A�T���l�C���쐬���̃t�@�C�����i�t���p�X�j
			String key = inputForm.getPictureDataPath() + inputForm.getPictureDataFileName();
			// Map �� Value �́A�T���l�C���̏o�͐�p�X�i���[�g�`�V�X�e�������ԍ��܂ł̃p�X�j
			String value = this.commonParameters.getHousImgOpenPhysicalPath() + upPath;

			thumbnailMap.put(key, value);
			// ���t�H���_�̃t�@�C�����g�p���Č��J�t�H���_�փT���l�C�����쐬
			createStaffImage(thumbnailMap, PanaStringUtils.toInteger(this.commonParameters.getAdminSiteStaffImageSize()));

			/** ���X�^�b�t�摜�̃t�@�C�����폜 **/
			if (!StringValidateUtil.isEmpty(oldStaffImagePathName)) {
				PanaFileUtil.delPhysicalPathFile(this.commonParameters.getHousImgOpenPhysicalPath() + oldStaffImagePathName, oldStaffImageFileName);
			}

			/** ���t�H���_�̃t�@�C�����폜 **/
			PanaFileUtil.delPhysicalPathFile(inputForm.getPictureDataPath(), inputForm.getPictureDataFileName());

			inputForm.setPictureDataPath(upPath);
			inputForm.setPictureDataFileName(inputForm.getPictureDataFileName());
		}

		//�摜�폜�`�F�b�N�{�b�N�X��I���ꍇ�A�摜�t�@�C�����폜����B
		if(!StringValidateUtil.isEmpty(inputForm.getPictureDataDelete())) {
			// ���t�H���_�̃t�@�C�����폜
			PanaFileUtil.delPhysicalPathFile(this.commonParameters.getHousImgTempPhysicalPath() + PanaFileUtil.getUploadTempPath(), inputForm.getPictureDataFileName());
			// ���J�t�H���_�̃t�@�C�����폜
			if (!StringValidateUtil.isEmpty(oldStaffImagePathName)) {
				PanaFileUtil.delPhysicalPathFile(this.commonParameters.getHousImgOpenPhysicalPath() + oldStaffImagePathName, oldStaffImageFileName);
			}
			inputForm.setPictureDataPath(null);
			inputForm.setPictureDataFileName(null);
		}

		/** �����g���������e�[�u���X�V����  **/
		// �o�^���ƂȂ� Map �I�u�W�F�N�g
		Map<String, String> inputData = new HashMap<String, String>();
		inputData.put("struct", inputForm.getBuildingDataValue());
		inputData.put("status", inputForm.getPreDataValue());
		inputData.put("totalHouseCnt", inputForm.getTotalHouseCntDataValue());
		inputData.put("scale", inputForm.getScaleDataValue());
		inputData.put("direction", inputForm.getOrientedDataValue());
		inputData.put("staffName", inputForm.getWorkerDataValue());
		inputData.put("staffImagePathName", inputForm.getPictureDataPath());
		inputData.put("staffImageFileName", inputForm.getPictureDataFileName());
		inputData.put("companyName", inputForm.getCompanyDataValue());
		inputData.put("branchName", inputForm.getBranchDataValue());
		inputData.put("licenseNo", inputForm.getFreeCdDataValue());
		inputData.put("infrastructure", inputForm.getInfDataValue());
		inputData.put("movieUrl", inputForm.getUrlDataValue());
		inputData.put("vendorComment", inputForm.getVendorComment());

		// �����g���������e�[�u����update����
		this.panaHousingManage.updExtInfo(inputForm.getSysHousingCd(), "housingDetail", inputData,
				String.valueOf(loginUser.getUserId()));


	}
	/**
	 * ���N�G�X�g�p�����[�^���� Form �I�u�W�F�N�g���쐬����B<br/>
	 * �������� Form �I�u�W�F�N�g�� Map �Ɋi�[���ĕ��A����B<br/>
	 * key = �t�H�[���N���X���i�p�b�P�[�W�Ȃ��j�AValue = �t�H�[���I�u�W�F�N�g <br/>
	 *
	 * @param request
	 *            HTTP ���N�G�X�g�p�����[�^
	 * @return �p�����[�^���ݒ肳�ꂽ�t�H�[���I�u�W�F�N�g���i�[���� Map �I�u�W�F�N�g
	 */
	private Map<String, Object> createModel(HttpServletRequest request) {

		Map<String, Object> model = new HashMap<>();

		// ���N�G�X�g�p�����[�^���擾���� Form �I�u�W�F�N�g���쐬����B
		PanaHousingFormFactory factory = PanaHousingFormFactory.getInstance(request);
		PanaHousingDtlInfoForm requestForm = factory.createPanaHousingDtlInfoForm(request);
		PanaHousingSearchForm searchForm = factory.createPanaHousingSearchForm(request);

		model.put("inputForm", requestForm);
		model.put("searchForm", searchForm);

		return model;
	}


	/**
	 * �X�^�b�t�T���l�C���摜���쐬����B<br/>
	 * �܂��A�I���W�i���T�C�Y�̉摜���A�摜�T�C�Y�̊K�w�� full �̃t�H���_�֔z�u����B<br/>
	 * thumbnailMap �̍\���͉��L�̒ʂ�B�@�t�@�C�����͌��̃t�@�C�������g�p�����B<br/>
	 * <ul>
	 * <li>Key : �T���l�C���쐬���̃t�@�C�����i�t���p�X�j</li>
	 * <li>value : �T���l�C���̏o�͐�p�X�i���[�g�`�V�X�e�������ԍ��܂ł̃p�X�B�@�T�C�Y��A�t�@�C�����͊܂܂Ȃ��B�j</li>
	 * </ul>
	 * @param thumbnailMap �쐬����t�@�C���̏��
	 * @param size �쐬����t�@�C���̃T�C�Y
	 *
	 * @throws IOException
	 * @throws Exception �Ϗ��悪�X���[����C�ӂ̗�O
	 */
	private void createStaffImage(Map<String, String> thumbnailMap, Integer size)
			throws IOException, Exception {

		// �쐬����t�@�C�����J��Ԃ�
		for (Entry<String, String> e : thumbnailMap.entrySet()){

			// �T���l�C���쐬���̃t�@�C���I�u�W�F�N�g���쐬����B
			File srcFile = new File(e.getKey());

			// �T���l�C���o�͐�̃��[�g�p�X �i�摜�T�C�Y�̒��O�܂ł̃t�H���_�K�w�j
			String destRootPath = e.getValue();
			if (!destRootPath.endsWith("/")) destRootPath += "/";

			// �I���W�i���摜���t���T�C�Y�摜�Ƃ��� copy ����B
			FileUtils.copyFileToDirectory(srcFile, new File(destRootPath + this.commonParameters.getAdminSiteFullFolder()));


			// �T�C�Y���X�g�����ݒ�̏ꍇ�̓T���l�C���摜���쐬���Ȃ��B
			if (size == null) return;


			// �o�͐�T�u�t�H���_�����݂��Ȃ��ꍇ�A�t�H���_���쐬����B
			File subDir = new File(destRootPath + this.commonParameters.getAdminSiteStaffFolder());
			if (!subDir.exists()){
				FileUtils.forceMkdir(subDir);
			}

			// �T���l�C���̏o�͐�́`/staff/�ɐ�������B
			File destFile = new File(destRootPath + this.commonParameters.getAdminSiteStaffFolder() + "/" + srcFile.getName());
			// �T���l�C���摜���쐬
			this.imgUtils.createImgFile(srcFile, destFile, size.intValue());
		}
	}
}
