package jp.co.transcosmos.dm3.corePana.model.reform;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import jp.co.transcosmos.dm3.core.model.HousingPartCreator;
import jp.co.transcosmos.dm3.core.model.exception.NotFoundException;
import jp.co.transcosmos.dm3.core.model.housing.Housing;
import jp.co.transcosmos.dm3.core.util.ImgUtils;
import jp.co.transcosmos.dm3.core.util.ThumbnailCreator;
import jp.co.transcosmos.dm3.core.vo.BuildingInfo;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonConstant;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonParameters;
import jp.co.transcosmos.dm3.corePana.model.ReformManage;
import jp.co.transcosmos.dm3.corePana.model.housing.PanaHousing;
import jp.co.transcosmos.dm3.corePana.model.reform.form.ReformDtlForm;
import jp.co.transcosmos.dm3.corePana.model.reform.form.ReformImgForm;
import jp.co.transcosmos.dm3.corePana.model.reform.form.ReformInfoForm;
import jp.co.transcosmos.dm3.corePana.util.PanaFileUtil;
import jp.co.transcosmos.dm3.corePana.vo.HousingInfo;
import jp.co.transcosmos.dm3.corePana.vo.ReformDtl;
import jp.co.transcosmos.dm3.corePana.vo.ReformImg;
import jp.co.transcosmos.dm3.corePana.vo.ReformPlan;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.io.FileUtils;
import org.springframework.util.StringUtils;

/**
 * ���t�H�[�����p Model �� proxy �N���X. ���� proxy
 * �N���X�ł́A���t�H�[���摜�t�@�C���̃T���l�C���쐬��A���t�H�[���ڍ׏���PDF�A�b�v���[�h�Ȃǂ��s���B<br/>
 * ���t�H�[�����p Model �͌ʃJ�X�^�}�C�Y�Ōp���E�Ϗ��ɂ��g�������\��������B<br/>
 * �摜�t�@�C���̑���̓��[���o�b�N�ł��Ȃ��̂ŁA�S�Ă�DB�X�V����������Ɏ��s����K�v������B<br/>
 * ���ׁ̈A�����̏����� proxy ��������s���� Model �{�̂��番������B<br/>
 * <p>
 *
 * <pre>
 * �S����		�C����		�C�����e
 * ------------ ----------- -----------------------------------------------------
 * TRANS		2015.04.13	�V�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 * ���̃N���X�̓V���O���g���� DI �R���e�i�ɒ�`�����̂ŁA�X���b�h�Z�[�t�ł��鎖�B<br/>
 *
 */
public class ReformPartThumbnailProxy implements ReformManage {

	/** �Ϗ���ƂȂ郊�t�H�[�� Model �N���X */
	protected ReformManage reformManager;

	/** ���ʃp�����[�^�I�u�W�F�N�g */
	protected PanaCommonParameters commonParameters;

	/** �T���l�C���摜�쐬����Util */
	private ThumbnailCreator thumbnailCreator;
	
	/** ���������������N���X�̃��X�g */
	protected List<HousingPartCreator> partCreateors;

	/** �T���l�C���摜�쐬�N���X */
	private ImgUtils imgUtils;
	/**
	 * �T���l�C���摜�쐬�N���X��ݒ肷��B<br/>
	 * <br/>
	 * @param imgUtils �T���l�C���摜�쐬�N���X
	 */
	public void setImgUtils(ImgUtils imgUtils) {
		this.imgUtils = imgUtils;
	}

	/**
	 * �Ϗ���ƂȂ郊�t�H�[�� Model ��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param housingManage
	 *            �Ϗ���ƂȂ郊�t�H�[�� Model
	 */
	public void setReformManager(ReformManage reformManager) {
		this.reformManager = reformManager;
	}

	public void setPartCreateors(List<HousingPartCreator> partCreateors) {
		this.partCreateors = partCreateors;
	}

	/**
	 * ���ʃp�����[�^�I�u�W�F�N�g��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param commonParameters
	 *            ���ʃp�����[�^�I�u�W�F�N�g
	 */
	public void setCommonParameters(PanaCommonParameters commonParameters) {
		this.commonParameters = commonParameters;
	}

	/**
	 * �T���l�C���摜�쐬����Util��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param fileUtil
	 *            �p�i���ʃt�@�C������Util
	 */
	public void setThumbnailCreator(ThumbnailCreator thumbnailCreator) {
		this.thumbnailCreator = thumbnailCreator;
	}

	/**
	 * �p�����[�^�œn���ꂽ ���t�H�[���v��������V�K�ǉ�����B<br/>
	 * �V�X�e�����t�H�[��CD �͎����̔Ԃ����̂ŁAReformPlan �� sysReformCd �v���p�e�B�ɂ͒l��ݒ肵�Ȃ����B<br/>
	 * <br/>
	 *
	 * @param reformPlan
	 *            ���t�H�[���v�������
     * @param inputForm ���͒l���i�[���ꂽ Form �I�u�W�F�N�g
	 * @return �V�X�e�����t�H�[��CD
	 */
	@Override
	public String addReformPlan(ReformPlan reformPlan, ReformInfoForm inputForm, String userId) throws Exception {
		// ���t�H�[�����̓o�^�����ֈϏ�����B
		String sysReformCd = this.reformManager.addReformPlan(reformPlan, inputForm, userId);
		createPartInfo(inputForm.getSysHousingCd(), "addReform");

        // ������{�����擾����B
        BuildingInfo buildingInfo = this.reformManager.searchBuildingInfo(inputForm.getSysHousingCd());
        String uploadPath = ReformManageImpl.getUploadPath(buildingInfo, inputForm.getSysHousingCd());

		// Temp�p�X�̎擾
		String temPath = this.commonParameters.getHousImgTempPhysicalPath();
		temPath = PanaFileUtil.conPhysicalPath(temPath, inputForm.getTemPath());

		// base�p�X�̎擾
		String basePath = this.commonParameters.getHousImgOpenPhysicalMemberPath();
		// /�u�萔�l�v/[���t�H�[�����CD]/[�s���{��CD]/[�s�撬��CD]/�V�X�e�����t�H�[���ԍ�/chart/
		basePath = PanaFileUtil.conPhysicalPath(basePath, uploadPath);
		basePath = PanaFileUtil.conPhysicalPath(basePath, commonParameters.getAdminSiteChartFolder());

		// Temp�t�H���_�[�̃t�@�C���ˌ��J�t�H���_�[�ֈړ�
		this.createImgFile(inputForm, basePath);

		return sysReformCd;
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

		PanaHousing housing = (PanaHousing)this.reformManager.searchHousingByPk(sysHousingCd);

		for (HousingPartCreator creator : this.partCreateors){
			// ���s�ΏۊO�̏ꍇ�͎��̂����������쐬�N���X��
			if (!creator.isExecuteMethod(methodName)) continue;

			// �������������쐬����B
			creator.createPart(housing);
		}
	}

	/**
	 * ���t�H�[���v�������̍X�V���s��<br/>
	 * <br/>
	 *
	 * @param inputForm
	 *            ���t�H�[�����t�H�[��
	 * @param userId
	 *            ���O�C�����[�UID
	 *
	 * @exception NotFoundException
	 *                �X�V�Ώۂ����݂��Ȃ��ꍇ
	 */
	@Override
	public void updateReformPlan(ReformInfoForm inputForm, String userId) throws Exception {
		// ���t�H�[�����̍X�V�����ֈϏ�����B
		this.reformManager.updateReformPlan(inputForm, userId);
		
		createPartInfo(inputForm.getSysHousingCd(), "updReform");

        // ������{�����擾����B
        BuildingInfo buildingInfo = this.reformManager.searchBuildingInfo(inputForm.getSysHousingCd());
        String uploadPath = ReformManageImpl.getUploadPath(buildingInfo, inputForm.getSysHousingCd());

		// ���[�^�[�`�@�[�g�摜�t�@�C�����폜
		if ("on".equals(inputForm.getReformImgDel())) {
			String rootPath = this.commonParameters.getHousImgOpenPhysicalMemberPath();
			String file[] = inputForm.getImgFile2().split("/");
			String imgName = file[file.length-1];

			// /�u�萔�l�v/[���t�H�[�����CD]/[�s���{��CD]/[�s�撬��CD]/�V�X�e�������ԍ�/
			String delPath = PanaFileUtil.conPhysicalPath(rootPath, uploadPath);
			Map<String, String> delFiles = new HashMap<String, String>();
			delFiles.put(imgName, delPath);

			// �Ϗ���� model �����s���ă��t�H�[�������폜����B
			for (String keyFileName : delFiles.keySet()) {
				PanaFileUtil.delPhysicalPathFile(delFiles.get(keyFileName),
						keyFileName);
			}
		}
		// ���[�^�[�`�@�[�g�摜�t�@�C����ǉ�
		if("1".equals(inputForm.getImgSelFlg())){

			// Temp�p�X�̎擾
			String temPath = this.commonParameters.getHousImgTempPhysicalPath();
			temPath = PanaFileUtil.conPhysicalPath(temPath, inputForm.getTemPath());

			// base�p�X�̎擾
			String basePath = this.commonParameters.getHousImgOpenPhysicalMemberPath();
			// /�u�萔�l�v/[���t�H�[�����CD]/[�s���{��CD]/[�s�撬��CD]/�V�X�e�����t�H�[���ԍ�/chart/
			basePath = PanaFileUtil.conPhysicalPath(basePath, uploadPath);
			basePath = PanaFileUtil.conPhysicalPath(basePath, commonParameters.getAdminSiteChartFolder());

			// Temp�t�H���_�[�̃t�@�C���ˌ��J�t�H���_�[�ֈړ�
			this.createImgFile(inputForm, basePath);

			// �Ϗ���� model �����s���ă��t�H�[�������폜����B
			String file[] = inputForm.getImgFile2().split("/");
			String imgName = file[file.length-1];
			Map<String, String> delFiles = new HashMap<String, String>();
			delFiles.put(imgName, basePath);
			for (String keyFileName : delFiles.keySet()) {
				PanaFileUtil.delPhysicalPathFile(delFiles.get(keyFileName),
						keyFileName);
			}

		}
	}


	/**
	 * �T���l�C���摜���쐬����B<br/>
	 * �܂��A�I���W�i���T�C�Y�̉摜���A�摜�T�C�Y�̊K�w�� full �̃t�H���_�֔z�u����B<br/>
	 * thumbnailMap �̍\���͉��L�̒ʂ�B�@�t�@�C�����͌��̃t�@�C�������g�p�����B<br/>
	 * <ul>
	 * <li>Key : �T���l�C���쐬���̃t�@�C�����i�t���p�X�j</li>
	 * <li>value : �T���l�C���̏o�͐�p�X�i���[�g�`�V�X�e�������ԍ��܂ł̃p�X�B�@�T�C�Y��A�t�@�C�����͊܂܂Ȃ��B�j</li>
	 * </ul>
	 *
	 * @param inputForm ���t�H�[�����t�H�[��
	 * @param basePath base�p�X
	 * @throws IOException
	 * @throws Exception �Ϗ��悪�X���[����C�ӂ̗�O
	 */
	public void createImgFile(ReformInfoForm inputForm, String basePath)
			throws IOException, Exception {

		if (!StringUtils.isEmpty(inputForm.getImgName())) {
			String srcRoot = this.commonParameters.getHousImgTempPhysicalPath();
			if (!srcRoot.endsWith("/")) srcRoot += "/";
			srcRoot += PanaFileUtil.getUploadTempPath() + "/";

			// �T���l�C���쐬�p MAP �̍쐬
			Map<String, String> thumbnailMap = new HashMap<>();
			thumbnailMap.put(
					PanaFileUtil.conPhysicalPath(srcRoot,
							inputForm.getImgName()), basePath);

			// �쐬����t�@�C�����J��Ԃ�
			for (Entry<String, String> e : thumbnailMap.entrySet()){

				// �T���l�C���쐬���̃t�@�C���I�u�W�F�N�g���쐬����B
				File srcFile = new File(e.getKey());

				// �T���l�C���o�͐�̃��[�g�p�X �i�摜�T�C�Y�̒��O�܂ł̃t�H���_�K�w�j
				String destRootPath = e.getValue();
				if (!destRootPath.endsWith("/")) destRootPath += "/";

				// �I���W�i���摜���t���T�C�Y�摜�Ƃ��� copy ����B
				FileUtils.copyFileToDirectory(srcFile, new File(destRootPath));

			}
		}
	}


	/**
	 * ���t�H�[�����폜���̃t�B���^�[����<br/>
	 * ���t�H�[�������폜����O�ɁA�폜�ΏۂƂȂ郊�t�H�[���摜���A���t�H�[���ڍ׏����擾����B<br/>
	 * �Ϗ���̃��t�H�[�� model ���g�p���ă��t�H�[�����̍폜���s���A<br/>
	 * �Y�����郊�t�H�[���摜�t�@�C���ƃ��t�H�[���ڍ׃t�@�C���iPDF�j���폜����B<br/>
	 * <br/>
	 *
	 * @param sysReformCd
	 *            �V�X�e�����t�H�[��CD
	 * @throws Exception
	 */
	@Override
	@SuppressWarnings("unchecked")
	public void delReformPlan(String sysHousingCd, String sysReformCd,
			String userId) throws Exception {
		// ���t�H�[�����
		Map<String, Object> reform = this.reformManager
				.searchReform(sysReformCd);
		// ���t�H�[���v�������
	    ReformPlan reformPlan = (ReformPlan) reform.get("reformPlan");
		// ���t�H�[���ڍ׏��
		List<ReformDtl> dtlList = (List<ReformDtl>) reform.get("dtlList");
		// ���t�H�[���摜���
		List<ReformImg> imgList = (List<ReformImg>) reform.get("imgList");

		Map<String, String> delFiles = new HashMap<String, String>();

		// ���[�_�[�`���[�g�摜���폜
	    String rootPath = ""; // /�u�萔�l�v
	    if (reformPlan != null) {
	    	rootPath = PanaFileUtil.conPhysicalPath(
					this.commonParameters.getHousImgOpenPhysicalMemberPath(), reformPlan.getReformChartImagePathName());

			delFiles.put(reformPlan.getReformChartImageFileName(), rootPath);
	    }

		// ���t�H�[���ڍ׏��iPDF�t�@�C���j���폜
		for (ReformDtl dtl : dtlList) {
			rootPath = ""; // /�u�萔�l�v
			// �{������������݂̂̏ꍇ
			if (PanaCommonConstant.ROLE_ID_PRIVATE.equals(dtl.getRoleId())) {
				rootPath = this.commonParameters
						.getHousImgOpenPhysicalMemberPath();
			} else {
				// �{���������S���̏ꍇ
				rootPath = this.commonParameters.getHousImgOpenPhysicalPath();
			}
			// /�u�萔�l�v/[���t�H�[�����CD]/[�s���{��CD]/[�s�撬��CD]/�V�X�e�����t�H�[���ԍ�/
			rootPath = PanaFileUtil
					.conPhysicalPath(rootPath, dtl.getPathName());
			delFiles.put(dtl.getFileName(), rootPath);
		}

		// ���t�H�[���摜�t�@�C�����폜
		for (ReformImg img : imgList) {
			rootPath = ""; // /�u�萔�l�v
			// �{������������݂̂̏ꍇ
			if (PanaCommonConstant.ROLE_ID_PRIVATE.equals(img.getRoleId())) {
				rootPath = this.commonParameters
						.getHousImgOpenPhysicalMemberPath();
			} else {
				// �{���������S���̏ꍇ
				rootPath = this.commonParameters.getHousImgOpenPhysicalPath();
			}
			// /�u�萔�l�v/[���t�H�[�����CD]/[�s���{��CD]/[�s�撬��CD]/�V�X�e�������ԍ�/
			// After�摜�t�@�C���p�X
			String afterPath = PanaFileUtil.conPhysicalPath(rootPath,
					img.getAfterPathName());
			// Before�摜�t�@�C���p�X
			String beforePath = PanaFileUtil.conPhysicalPath(rootPath,
					img.getBeforePathName());

			delFiles.put(img.getAfterFileName(), afterPath);
			delFiles.put(img.getBeforeFileName(), beforePath);
		}

		// �Ϗ���� model �����s���ă��t�H�[�������폜����B
		this.reformManager.delReformPlan(sysHousingCd, sysReformCd, userId);
		
		createPartInfo(sysHousingCd, "delReform");

		for (String keyFileName : delFiles.keySet()) {
			if(keyFileName!= null){
				PanaFileUtil.delPhysicalPathFile(delFiles.get(keyFileName),
						keyFileName);
			}
		}
	}

	/**
	 * �p�����[�^�œn���ꂽ ���t�H�[���ڍ׏���V�K�ǉ�����B<br/>
	 * �}�� �͎����̔Ԃ����̂ŁAReformDtl �� divNo �v���p�e�B�ɂ͒l��ݒ肵�Ȃ����B<br/>
	 * <br/>
	 *
	 * @param inputForm
	 *            ���t�H�[���ڍ׏��t�H�[��
	 *
	 * @return �V���ɒǉ��������t�H�[���ڍ׏��̃��X�g
	 * @throws Exception
	 */
	@Override
	public List<ReformDtl> addReformDtl(ReformDtlForm inputForm)
			throws Exception {
		// �Ϗ���̏��������s����B
		// ���̃��\�b�h�̖߂�l�́A�V�K�ǉ��������t�H�[���ڍ׏��̃��X�g�����A�����B
		List<ReformDtl> dtlList = this.reformManager.addReformDtl(inputForm);

		// �T���l�C���̍쐬���t�H���_���@�i���t���w�肵���t�H���_�K�w�܂Łj
		String srcRoot = this.commonParameters.getHousImgTempPhysicalPath();
		if (!srcRoot.endsWith("/")) srcRoot += "/";
		srcRoot += PanaFileUtil.getUploadTempPath() + "/";

		for(int i=0;i<dtlList.size();i++){
			// Temp�t�H���_�[������J�t�H���_�[�ֈړ�
			String basePath = "";
			// �{������������݂̂̏ꍇ
			if (PanaCommonConstant.ROLE_ID_PRIVATE.equals(dtlList.get(i).getRoleId())) {
				basePath = this.commonParameters.getHousImgOpenPhysicalMemberPath();
			} else {
				// �{���������S���̏ꍇ
				basePath = this.commonParameters.getHousImgOpenPhysicalPath();
			}

			// /�u�萔�l�v/[���t�H�[�����CD]/[�s���{��CD]/[�s�撬��CD]/�V�X�e�����t�H�[���ԍ�/
			String afterPath = PanaFileUtil.conPhysicalPath(basePath,
					dtlList.get(i).getPathName());

			// �T���l�C���쐬�p MAP �̍쐬
			Map<String, String> thumbnailMap = new HashMap<>();
			thumbnailMap.put(
					PanaFileUtil.conPhysicalPath(srcRoot,
							dtlList.get(i).getFileName()), afterPath);

			// �e�X�g���\�b�h���s
			this.create(thumbnailMap);
		}

		return dtlList;
	}

	/**
	 * ���t�H�[���ڍ׏��X�V���̃t�B���^�[����<br/>
	 * �����t�H�[���ڍ׃t�@�C���̉{�������ɂ�����ւ��͔�������B<br/>
	 * <br/>
	 *
	 * @param inputForm
	 *            ���t�H�[���ڍ׏��̓��̓t�H�[��
	 *
	 * @return �폜���������� ���t�H�[���ڍ׏��
	 */
	@Override
	public List<ReformDtl> updateReformDtl(ReformDtlForm inputForm)
			throws Exception {
		// �Ϗ���̏��������s����B
		// �폜���ꂽ ���t�H�[���ڍ׏��̃��X�g���߂����B
		List<ReformDtl> dtlList = this.reformManager.updateReformDtl(inputForm);


		// null �̏ꍇ�A�폜�摜�����������Ƃ��� null �𕜋A����B
		if (dtlList == null)
			return null;

		for (int idx = 0; idx < inputForm.getDivNo().length; idx++) {
			if (!StringUtils.isEmpty(inputForm.getDivNo()[idx])) {
				String srcPath = "";
				// �{������������݂̂̏ꍇ
				if (PanaCommonConstant.ROLE_ID_PRIVATE.equals(inputForm.getOldRoleId()[idx])) {
					srcPath = this.commonParameters.getHousImgOpenPhysicalMemberPath();
				} else {
					// �{���������S���̏ꍇ
					srcPath = this.commonParameters.getHousImgOpenPhysicalPath();
				}

				// /�u�萔�l�v/[���t�H�[�����CD]/[�s���{��CD]/[�s�撬��CD]/�V�X�e�����t�H�[���ԍ�/
				String afterSrcPath = PanaFileUtil.conPhysicalPath(srcPath,
						inputForm.getPathName()[idx]);

				// �폜�����̏ꍇ
				if ("1".equals(inputForm.getDelFlg()[idx])) {
					// �ړ���A�ړ����̃C���[�W�t�@�C���Ƃ��̃T���l�C�����폜
					this.deleteImgFile(afterSrcPath,
							inputForm.getUpdHidFileName()[idx]);
				} else {
					// �X�V�����̏ꍇ
					// �{�������̐ݒ肪�ύX���ꂽ�ꍇ
					if (!inputForm.getOldRoleId()[idx].equals(inputForm
							.getRoleId()[idx])) {
						String uploadPath = "";
						// �{������������݂̂̏ꍇ
						if (PanaCommonConstant.ROLE_ID_PRIVATE.equals(inputForm.getRoleId()[idx])) {
							uploadPath = this.commonParameters
									.getHousImgOpenPhysicalMemberPath();
						} else {
							// �{���������S���̏ꍇ
							uploadPath = this.commonParameters
									.getHousImgOpenPhysicalPath();
						}

						// /�u�萔�l�v/[���t�H�[�����CD]/[�s���{��CD]/[�s�撬��CD]/�V�X�e�����t�H�[���ԍ�/
						String afterUploadPath = PanaFileUtil.conPhysicalPath(
								uploadPath,
								inputForm.getPathName()[idx]);

						// �T���l�C���쐬�p MAP �̍쐬
						Map<String, String> thumbnailMap = new HashMap<>();
						thumbnailMap.put(PanaFileUtil.conPhysicalPath(
								afterSrcPath,
								this.commonParameters.getAdminSitePdfFolder()+"/"+ inputForm.getUpdHidFileName()[idx]),
								afterUploadPath);

						// �e�X�g���\�b�h���s
						this.create(thumbnailMap);

						// �ړ���A�ړ����̃C���[�W�t�@�C���Ƃ��̃T���l�C�����폜
						this.deleteImgFile(afterSrcPath,
								inputForm.getUpdHidFileName()[idx]);
					}
				}
			}
		}

		return dtlList;
	}

	/**
	 * ���t�H�[���ڍ׍폜���̃t�B���^�[����<br/>
	 * ��updateReformDtl() ����ł��폜�B�@�ʍ폜�p�@�\�B<br/>
	 * <br/>
	 *
	 * @param sysReformCd
	 *            �V�X�e�����t�H�[��Cd
	 * @param divNo
	 *            �}��
	 * @return �폜���� ���t�H�[���ڍ׏��
	 */
	@Override
	public ReformDtl delReformDtl(String sysReformCd, int divNo) {
		// �Ϗ���̏��������s���ă��t�H�[���ڍ׏����폜����B
		ReformDtl reformDtl = this.reformManager.delReformDtl(sysReformCd,
				divNo);

		// �t�@�C���̍폜����
		String srcPath = "";
		// �{������������݂̂̏ꍇ
		if (PanaCommonConstant.ROLE_ID_PRIVATE.equals(reformDtl.getRoleId())) {
			srcPath = this.commonParameters.getHousImgOpenPhysicalMemberPath();
		} else {
			// �{���������S���̏ꍇ
			srcPath = this.commonParameters.getHousImgOpenPhysicalPath();
		}
		// /�u�萔�l�v/[���t�H�[�����CD]/[�s���{��CD]/[�s�撬��CD]/�V�X�e�����t�H�[���ԍ�/
		srcPath = PanaFileUtil
				.conPhysicalPath(srcPath, reformDtl.getPathName());

		// PDF�t�@�C�����폜
		PanaFileUtil.delPhysicalPathFile(srcPath, reformDtl.getFileName());

		return reformDtl;
	}

	/**
	 * �T���l�C���摜���쐬����B<br/>
	 * �܂��A�I���W�i���T�C�Y�̉摜���A�摜�T�C�Y�̊K�w�� full �̃t�H���_�֔z�u����B<br/>
	 * thumbnailMap �̍\���͉��L�̒ʂ�B�@�t�@�C�����͌��̃t�@�C�������g�p�����B<br/>
	 * <ul>
	 * <li>Key : �T���l�C���쐬���̃t�@�C�����i�t���p�X�j</li>
	 * <li>value : �T���l�C���̏o�͐�p�X�i���[�g�`�V�X�e�������ԍ��܂ł̃p�X�B�@�T�C�Y��A�t�@�C�����͊܂܂Ȃ��B�j</li>
	 * </ul>
	 * @param thumbnailMap �쐬����t�@�C���̏��
	 *
	 * @throws IOException
	 * @throws Exception �Ϗ��悪�X���[����C�ӂ̗�O
	 */
	public void create(Map<String, String> thumbnailMap)
			throws IOException, Exception {

		// �쐬����t�@�C�����J��Ԃ�
		for (Entry<String, String> e : thumbnailMap.entrySet()){

			// �T���l�C���쐬���̃t�@�C���I�u�W�F�N�g���쐬����B
			File srcFile = new File(e.getKey());

			// �T���l�C���o�͐�̃��[�g�p�X �i�摜�T�C�Y�̒��O�܂ł̃t�H���_�K�w�j
			String destRootPath = e.getValue();
			if (!destRootPath.endsWith("/")) destRootPath += "/";

			// �I���W�i���摜���t���T�C�Y�摜�Ƃ��� copy ����B
			FileUtils.copyFileToDirectory(srcFile, new File(destRootPath + this.commonParameters.getAdminSitePdfFolder()));

		}
	}
	/**
	 * �����摜�t�@�C�����ʂɍ폜����B<br/>
	 * filePath �Ŏw�肵���t�H���_���̃t�@�C������̏ꍇ�A�t�H���_���ƍ폜����B
	 * <br/>
	 * @param filePath ���[�g�`�V�X�e������CD �܂ł̃p�X�i�摜�T�C�Y�̉��܂ł̃p�X�j
	 * @param fileName�@�摜�t�@�C����
	 *
	 * @throws IOException
	 */
	public void deleteImgFile(String filePath, String fileName) throws IOException{

		// �I���W�i���摜�̍폜
		if(!StringValidateUtil.isEmpty(fileName)){
			(new File(filePath + this.commonParameters.getAdminSitePdfFolder() + "/" + fileName)).delete();
		}
	}

	/**
	 * �p�����[�^�œn���ꂽ ���t�H�[���摜����V�K�ǉ�����B<br/>
	 * �}�� �͎����̔Ԃ����̂ŁAReformDtl �� divNo �v���p�e�B�ɂ͒l��ݒ肵�Ȃ����B<br/>
	 * <br/>
	 *
	 * @param inputForm
	 *            ���t�H�[���摜���t�H�[��
	 *
	 * @return �V���ɒǉ��������t�H�[���摜���̃��X�g
	 * @throws Exception
	 */
	@Override
	public ReformImg addReformImg(ReformImgForm inputForm) throws Exception {
		// �Ϗ���̏��������s����B
		// ���̃��\�b�h�̖߂�l�́A�V�K�ǉ������摜���̃��X�g�����A�����B
		ReformImg reformImg = this.reformManager.addReformImg(inputForm);

		// �T���l�C���̍쐬���t�H���_���@�i���t���w�肵���t�H���_�K�w�܂Łj
		String srcRoot = this.commonParameters.getHousImgTempPhysicalPath();
		if (!srcRoot.endsWith("/")) srcRoot += "/";
		srcRoot += PanaFileUtil.getUploadTempPath() + "/";

		// Temp�t�H���_�[������J�t�H���_�[�ֈړ�
		String basePath = "";
		// �{������������݂̂̏ꍇ
		if (PanaCommonConstant.ROLE_ID_PRIVATE.equals(reformImg.getRoleId())) {
			basePath = this.commonParameters.getHousImgOpenPhysicalMemberPath();
		} else {
			// �{���������S���̏ꍇ
			basePath = this.commonParameters.getHousImgOpenPhysicalPath();
		}

		// /�u�萔�l�v/[���t�H�[�����CD]/[�s���{��CD]/[�s�撬��CD]/�V�X�e�����t�H�[���ԍ�/
		String afterPath = PanaFileUtil.conPhysicalPath(basePath,
				reformImg.getAfterPathName());
		// /�u�萔�l�v/[���t�H�[�����CD]/[�s���{��CD]/[�s�撬��CD]/�V�X�e�����t�H�[���ԍ�/
		String beforePath = PanaFileUtil.conPhysicalPath(basePath,
				reformImg.getBeforePathName());

		// �T���l�C���쐬�p MAP �̍쐬
		Map<String, String> thumbnailMap = new HashMap<>();
		thumbnailMap.put(
				PanaFileUtil.conPhysicalPath(srcRoot,
						reformImg.getAfterFileName()), afterPath);
		thumbnailMap.put(
				PanaFileUtil.conPhysicalPath(srcRoot,
						reformImg.getBeforeFileName()), beforePath);

		// �e�X�g���\�b�h���s
		this.thumbnailCreator.create(thumbnailMap);

		return reformImg;
	}

	/**
	 * ���t�H�[���摜���X�V���̃t�B���^�[����<br/>
	 * �����t�H�[���摜�t�@�C���̉{�������ɂ�����ւ��͔�������B<br/>
	 * <br/>
	 *
	 * @param inputForm
	 *            ���t�H�[���摜���̓��̓t�H�[��
	 *
	 * @return �폜���������� ���t�H�[���摜���
	 */
	@Override
	public List<ReformImg> updateReformImg(ReformImgForm inputForm)
			throws Exception {
		// �Ϗ���̏��������s����B
		// �폜���ꂽ���t�H�[���摜���̃��X�g���߂����B
		List<ReformImg> imgList = this.reformManager.updateReformImg(inputForm);

		// null �̏ꍇ�A�폜�摜�����������Ƃ��� null �𕜋A����B
		if (imgList == null)
			return null;

		for (int idx = 0; idx < inputForm.getDivNo().length; idx++) {
			if (!StringUtils.isEmpty(inputForm.getDivNo()[idx])) {
				String srcPath = "";
				// �{������������݂̂̏ꍇ
				if (PanaCommonConstant.ROLE_ID_PRIVATE.equals(inputForm.getEditOldRoleId()[idx])) {
					srcPath = this.commonParameters.getHousImgOpenPhysicalMemberPath();
				} else {
					// �{���������S���̏ꍇ
					srcPath = this.commonParameters.getHousImgOpenPhysicalPath();
				}

				// /�u�萔�l�v/[���t�H�[�����CD]/[�s���{��CD]/[�s�撬��CD]/�V�X�e�����t�H�[���ԍ�/
				String afterSrcPath = PanaFileUtil.conPhysicalPath(srcPath,
						inputForm.getEditAfterPathName()[idx]);
				// /�u�萔�l�v/[���t�H�[�����CD]/[�s���{��CD]/[�s�撬��CD]/�V�X�e�����t�H�[���ԍ�/
				String beforeSrcPath = PanaFileUtil.conPhysicalPath(srcPath,
						inputForm.getEditBeforePathName()[idx]);

				// �폜�����̏ꍇ
				if ("1".equals(inputForm.getDelFlg()[idx])) {

					this.thumbnailCreator.deleteImgFile(afterSrcPath,
							inputForm.getEditAfterFileName()[idx]);
					// �ړ���A�ړ����̃C���[�W�t�@�C���Ƃ��̃T���l�C�����폜
					this.thumbnailCreator.deleteImgFile(beforeSrcPath,
							inputForm.getEditBeforeFileName()[idx]);
				} else {
					// �X�V�����̏ꍇ
					// �{�������̐ݒ肪�ύX���ꂽ�ꍇ
					if (!inputForm.getEditOldRoleId()[idx].equals(inputForm
							.getEditRoleId()[idx])) {
						String uploadPath = "";
						// �{������������݂̂̏ꍇ
						if (PanaCommonConstant.ROLE_ID_PRIVATE.equals(inputForm.getEditRoleId()[idx])) {
							uploadPath = this.commonParameters
									.getHousImgOpenPhysicalMemberPath();
						} else {
							// �{���������S���̏ꍇ
							uploadPath = this.commonParameters
									.getHousImgOpenPhysicalPath();
						}

						// /�u�萔�l�v/[���t�H�[�����CD]/[�s���{��CD]/[�s�撬��CD]/�V�X�e�����t�H�[���ԍ�/
						String afterUploadPath = PanaFileUtil.conPhysicalPath(
								uploadPath,
								inputForm.getEditAfterPathName()[idx]);
						String beforeUploadPath = PanaFileUtil.conPhysicalPath(
								uploadPath,
								inputForm.getEditBeforePathName()[idx]);

						// �T���l�C���쐬�p MAP �̍쐬
						Map<String, String> thumbnailMap = new HashMap<>();
						thumbnailMap.put(PanaFileUtil.conPhysicalPath(
								afterSrcPath,
								this.commonParameters.getAdminSiteFullFolder()+"/"+ inputForm.getEditAfterFileName()[idx]),
								afterUploadPath);
						thumbnailMap.put(PanaFileUtil.conPhysicalPath(
								beforeSrcPath,
								this.commonParameters.getAdminSiteFullFolder()+"/"+ inputForm.getEditBeforeFileName()[idx]),
								beforeUploadPath);

						// �e�X�g���\�b�h���s
						this.thumbnailCreator.create(thumbnailMap);

						this.thumbnailCreator.deleteImgFile(afterSrcPath,
								inputForm.getEditAfterFileName()[idx]);
						// �ړ���A�ړ����̃C���[�W�t�@�C���Ƃ��̃T���l�C�����폜
						this.thumbnailCreator.deleteImgFile(beforeSrcPath,
								inputForm.getEditBeforeFileName()[idx]);
					}
				}
			}
		}

		return imgList;
	}

	/**
	 * ���t�H�[���摜�폜���̃t�B���^�[����<br/>
	 * ��updateReformImg() ����ł��폜�B�@�ʍ폜�p�@�\�B<br/>
	 * <br/>
	 *
	 * @param sysReformCd
	 *            �V�X�e�����t�H�[��Cd
	 * @param divNo
	 *            �}��
	 * @return �폜���� ���t�H�[���摜���
	 * @throws IOException
	 */
	@Override
	public ReformImg delReformImg(String sysReformCd, int divNo)
			throws IOException {
		// �Ϗ���̏��������s���ă��t�H�[���摜�����폜����B
		ReformImg reformImg = this.reformManager.delReformImg(sysReformCd,
				divNo);

		// �t�@�C���̍폜����
		String filePath = "";
		// �{������������݂̂̏ꍇ
		if (PanaCommonConstant.ROLE_ID_PRIVATE.equals(reformImg.getRoleId())) {
			filePath = this.commonParameters.getHousImgOpenPhysicalMemberPath();
		} else {
			// �{���������S���̏ꍇ
			filePath = this.commonParameters.getHousImgOpenPhysicalPath();
		}
		// /�u�萔�l�v/[���t�H�[�����CD]/[�s���{��CD]/[�s�撬��CD]/�V�X�e�����t�H�[���ԍ�/
		String afterSrcPath = PanaFileUtil.conPhysicalPath(filePath,
				reformImg.getAfterPathName());
		String beforeSrcPath = PanaFileUtil.conPhysicalPath(filePath,
				reformImg.getBeforePathName());

		this.thumbnailCreator.deleteImgFile(afterSrcPath,
				reformImg.getAfterPathName());
		// �ړ���A�ړ����̃C���[�W�t�@�C���Ƃ��̃T���l�C�����폜
		this.thumbnailCreator.deleteImgFile(beforeSrcPath,
				reformImg.getBeforePathName());

		return reformImg;
	}

	/**
	 * ���t�H�[���֘A���iReformPlan, ReformChart, ReformDtl, ReformImg)���������A����Map�𕜋A����B<br/>
	 * �����œn���ꂽ sysReformCd �p�����[�^�̒l�Ō��������𐶐����A���t�H�[��������������B<br/>
	 * <br/>
	 * ���������Ƃ��āA�ȉ��̃f�[�^�������ΏۂƂ���B<br/>
	 *
	 * @param sysReformCd
	 *            �V�X�e�����t�H�[��CD
	 *
	 * @return ���t�H�[���v��������Map
	 *
	 * @exception Exception
	 *                �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	@Override
	public Map<String, Object> searchReform(String sysReformCd) {
		// �Ϗ���̏��������s���ă��t�H�[���摜�����폜����B
		return this.reformManager.searchReform(sysReformCd);
	}

	/**
	 * �p�����[�^�œn���ꂽ ���t�H�[���E���[�_�[�`���[�g�ڍ׏����X�V�iupdate��insert)����B<br/>
	 * <br/>
	 *
	 * @param reformChart
	 *            ���t�H�[���E���[�_�[�`���[�g�ڍ׏��
	 *
	 */
	@Override
	public void updReformChart(ReformInfoForm form, int count) {
		// �Ϗ���̏��������s���ă��t�H�[���摜�����폜����B
		this.reformManager.updReformChart(form, count);

	}

	/**
	 * �p�����[�^�œn���ꂽ ���t�H�[���E���[�_�[�`���[�g�ڍ׏���V�K�ǉ�����B<br/>
	 * <br/>
	 *
	 * @param reformChart
	 *            ���t�H�[���E���[�_�[�`���[�g�ڍ׏��
	 *
	 */
	@Override
	public void addReformChart(ReformInfoForm form, int count) {
		// �Ϗ���̏��������s���ă��t�H�[���摜�����폜����B
		this.reformManager.addReformChart(form, count);

	}

	/**
	 * �p�����[�^�œn���ꂽ �V�X�e�����t�H�[��CD�Ń��t�H�[���E���[�_�[�`���[�g�����폜����B<br/>
	 * �o���f�[�V�������̃`�F�b�N�����͌Ăяo�����ŗ\�ߎ��{���Ă������B<br/>
	 * <br/>
	 *
	 * @param sysReformCd
	 *            �V�X�e�����t�H�[��CD
	 *
	 */
	@Override
	public void delReformChart(String sysReformCd) {
		// �Ϗ���̏��������s���ă��t�H�[���摜�����폜����B
		this.reformManager.delReformChart(sysReformCd);

	}

	/**
	 * ���t�H�[���摜���̎}�Ԃ��̔Ԃ��鏈���B<br/>
	 * <br/>
	 *
	 * @param sysReformCd
	 *            �V�X�e�����t�H�[��CD
	 *
	 */
	@Override
	public int getReformImgDivNo(String sysReformCd) {
		// �Ϗ���̏��������s���ă��t�H�[���摜�����폜����B
		return this.reformManager.getReformImgDivNo(sysReformCd);
	}

	/**
	 * ���t�H�[���ڍ׏��̎}�Ԃ��̔Ԃ��鏈���B<br/>
	 * <br/>
	 *
	 * @param sysReformCd
	 *            �V�X�e�����t�H�[��CD
	 *
	 */
	@Override
	public int getReformDtlDivNo(String sysReformCd) {
		// �Ϗ���̏��������s���ă��t�H�[���摜�����폜����B
		return this.reformManager.getReformDtlDivNo(sysReformCd);
	}

	/**
	 * �p�����[�^ �V�X�e������CD ���L�[�ɁA������{������������B<br/>
	 * <br/>
	 *
	 * @param sysHousingCd
	 *            �V�X�e������CD
	 *
	 * @return ������{���
	 *
	 * @exception Exception
	 *                �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	@Override
	public HousingInfo searchHousingInfo(String sysHousingCd) throws Exception {
		// �Ϗ���̏��������s���ă��t�H�[���摜�����폜����B
		return this.reformManager.searchHousingInfo(sysHousingCd);
	}

	/**
	 * �p�����[�^ �V�X�e������CD ���L�[�ɁA������{������������B<br/>
	 * <br/>
	 *
	 * @param sysHousingCd
	 *            �V�X�e������CD
	 *
	 * @return ������{���
	 *
	 * @exception Exception
	 *                �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	@Override
	public BuildingInfo searchBuildingInfo(String sysHousingCd)
			throws Exception {
		// �Ϗ���̏��������s���ă��t�H�[���摜�����폜����B
		return this.reformManager.searchBuildingInfo(sysHousingCd);
	}

	/**
	 * ���t�H�[���ڍ׏����������A���ʂ𕜋A����B<br/>
	 * �����œn���ꂽ Form �p�����[�^�̒l�Ō��������𐶐����A���t�H�[���ڍ׏�����������B<br/>
	 * �������ʂ� Form �I�u�W�F�N�g�Ɋi�[����A�擾�����Y�����R�[�h��߂�l�Ƃ��ĕ��A����B<br/>
	 * <br/>
	 *
	 * @param sysReformCd
	 *            �V�X�e�����t�H�[��CD
	 * @param divNo
	 *            �}��
	 *
	 * @return ���������ɊY�����郊�t�H�[���ڍ׏��
	 *
	 */
	@Override
	public ReformDtl searchReformDtlByPk(String sysReformCd, String divNo)
			throws Exception {
		// �Ϗ���̏��������s���ă��t�H�[���摜�����폜����B
		return this.reformManager.searchReformDtlByPk(sysReformCd, divNo);
	}

	/**
	 * ���t�H�[���v���������������A���ʂ𕜋A����B<br/>
	 * <br/>
	 *
	 * @param sysHousingCd
	 *            �V�X�e������CD
	 *
	 * @return ���������ɊY�����郊�t�H�[���v�������
	 *
	 * @exception Exception
	 *                �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	@Override
	public List<ReformPlan> searchReformPlan(String sysHousingCd)
			throws Exception {
		// �Ϗ���̏��������s���ă��t�H�[���摜�����폜����B
		return this.reformManager.searchReformPlan(sysHousingCd);
	}

	/**
	 * ���t�H�[���摜�t�@�C�������擾���郁�\�b�h.<br>
	 * <br>
	 *
	 * @return ���t�H�[���摜�t�@�C����
	 * @throws Exception
	 */
	public String getReformJpgFileName() throws Exception {
		return getSequenceFileName("jpg");
	}

	/**
	 * ���t�H�[��PDF�t�@�C�������擾���郁�\�b�h.<br>
	 * <br>
	 *
	 * @return ���t�H�[��PDF�t�@�C����
	 * @throws Exception
	 */
	public String getReformPdfFileName() throws Exception {
		return getSequenceFileName("pdf");
	}

	/**
	 * ���t�H�[���摜/PDF�t�@�C�������V�[�P���X����擾���ĕ��A����B<br/>
	 * <br/>
	 *
	 * @return �摜/PDF�t�@�C����
	 */
	public String getSequenceFileName(String extension) throws Exception {
		String fileName = this.thumbnailCreator.getFIleName();

		if (!StringUtils.isEmpty(fileName)) {
			fileName = fileName + "." + extension;
		}

		return fileName;
	}

	/**
	 * ������{���̃^�C���X�^���v�����X�V����B<br/>
	 * <br/>
	 *
	 * @param sysHousingCd
	 *            �X�V�ΏۃV�X�e������CD
	 * @param editUserId
	 *            �X�V��ID
	 * @throws Exception
	 */
	public void updateEditTimestamp(String sysHousingCd, String sysReformCd,
			String editUserId) throws Exception {
		this.reformManager.updateEditTimestamp(sysHousingCd, sysReformCd,
				editUserId);
	}

    /**
     * ���t�H�[���v���������������A���ʂ𕜋A����B<br/>
     * <br/>
     * @param sysHousingCd �V�X�e������CD
     *
     * @return ���������ɊY�����郊�t�H�[���v�������
     *
     * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
     */
	@Override
	public List<ReformPlan> searchReformPlan(String sysHousingCd, boolean full) throws Exception {
		return this.reformManager.searchReformPlan(sysHousingCd, full);
	}

	/**
	 * �T���l�C���摜���쐬����B<br/>
	 * �܂��A�I���W�i���T�C�Y�̉摜���A�摜�T�C�Y�̊K�w�� full �̃t�H���_�֔z�u����B<br/>
	 * thumbnailMap �̍\���͉��L�̒ʂ�B�@�t�@�C�����͌��̃t�@�C�������g�p�����B<br/>
	 * <ul>
	 * <li>Key : �T���l�C���쐬���̃t�@�C�����i�t���p�X�j</li>
	 * <li>value : �T���l�C���̏o�͐�p�X�i���[�g�`�V�X�e�������ԍ��܂ł̃p�X�B�@�T�C�Y��A�t�@�C�����͊܂܂Ȃ��B�j</li>
	 * </ul>
	 * @param thumbnailMap �쐬����t�@�C���̏��
	 *
	 * @throws Exception �Ϗ��悪�X���[����C�ӂ̗�O
	 */
	public void addTempFile(FileItem fileItem, String temPath, String fileName) throws Exception{

		String tempUploadPath=PanaFileUtil.conPhysicalPath(this.commonParameters.getHousImgTempPhysicalPath(), temPath+"/");
		PanaFileUtil.uploadFile(fileItem,tempUploadPath, fileName);

		// �T���l�C���쐬���̃t�@�C���I�u�W�F�N�g���쐬����B
		File srcFile = new File(tempUploadPath + fileName);

		// �T���l�C���o�͐�̃��[�g�p�X �i�摜�T�C�Y�̒��O�܂ł̃t�H���_�K�w�j
		String destRootPath = tempUploadPath;
		if (!destRootPath.endsWith("/")) destRootPath += "/";

		// �T�C�Y���X�g�����ݒ�̏ꍇ�̓T���l�C���摜���쐬���Ȃ��B
		if (this.commonParameters.getThumbnailSizes() == null) return;

		// �쐬����T���l�C���T�C�Y���J��Ԃ�
		for (Integer size : this.commonParameters.getThumbnailSizes()){

			// �o�͐�T�u�t�H���_�����݂��Ȃ��ꍇ�A�t�H���_���쐬����B
			// createImgFile() �́A�T�u�t�H���_���쐬���Ȃ��̂�..�B
			File subDir = new File(destRootPath + size.toString());
			if (!subDir.exists()){
				FileUtils.forceMkdir(subDir);
			}

			// �T���l�C���̏o�͐�̓t�@�C���T�C�Y���ɈقȂ�̂ŁA�T�C�Y���ɐ�������B
			File destFile = new File(destRootPath + size.toString() + "/" + srcFile.getName());
			// �T���l�C���摜���쐬
			this.imgUtils.createImgFile(srcFile, destFile, size.intValue());
		}
	}

	@Override
	public Housing searchHousingByPk(String sysHousingCd) throws Exception {
		return this.reformManager.searchHousingByPk(sysHousingCd);
	}
}
