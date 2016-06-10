package jp.co.transcosmos.dm3.corePana.model.housing;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.core.model.exception.NotFoundException;
import jp.co.transcosmos.dm3.core.model.housing.Housing;
import jp.co.transcosmos.dm3.core.model.housing.HousingPartThumbnailProxy;
import jp.co.transcosmos.dm3.core.model.housing.form.HousingDtlForm;
import jp.co.transcosmos.dm3.core.model.housing.form.HousingEquipForm;
import jp.co.transcosmos.dm3.core.model.housing.form.HousingForm;
import jp.co.transcosmos.dm3.core.model.housing.form.HousingImgForm;
import jp.co.transcosmos.dm3.core.model.housing.form.HousingSearchForm;
import jp.co.transcosmos.dm3.core.util.ImgUtils;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonConstant;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonParameters;
import jp.co.transcosmos.dm3.corePana.model.PanaCommonManage;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingInfoForm;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingInspectionForm;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingSearchForm;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingStatusForm;
import jp.co.transcosmos.dm3.corePana.model.reform.ReformPartThumbnailProxy;
import jp.co.transcosmos.dm3.corePana.util.PanaFileUtil;
import jp.co.transcosmos.dm3.corePana.vo.HousingImageInfo;
import jp.co.transcosmos.dm3.corePana.vo.HousingInfo;
import jp.co.transcosmos.dm3.corePana.vo.HousingInspection;
import jp.co.transcosmos.dm3.corePana.vo.ReformDtl;
import jp.co.transcosmos.dm3.corePana.vo.ReformImg;
import jp.co.transcosmos.dm3.corePana.vo.ReformPlan;
import jp.co.transcosmos.dm3.dao.DAO;
import jp.co.transcosmos.dm3.dao.DAOCriteria;
import jp.co.transcosmos.dm3.dao.JoinResult;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.io.FileUtils;
import org.springframework.util.StringUtils;

public class PanaHousingPartThumbnailProxy extends HousingPartThumbnailProxy {

	/** ���t�H�[�����p Model �� proxy �N���X */
	protected ReformPartThumbnailProxy reformManage;

	/** �����������}�X�^DAO */
	private DAO<JoinResult> partSrchMstListDAO;
	/** �����摜���p�@DAO */
	protected DAO<HousingImageInfo> panaHousingImageInfoDAO;
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
	 * �����������}�X�^�p Model �� proxy �N���X��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param partSrchMstDAO
	 *            �����������}�X�^�p Model �� proxy �N���X
	 */
	public void setPartSrchMstListDAO(DAO<JoinResult> partSrchMstListDAO) {
		this.partSrchMstListDAO = partSrchMstListDAO;
	}

	/**
	 * ���t�H�[�����p Model �� proxy �N���X��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param reformManage
	 *            ���t�H�[�����p Model �� proxy �N���X
	 */
	public void setReformManage(ReformPartThumbnailProxy reformManage) {
		this.reformManage = reformManage;
	}

	/**
	 * �����摜���p�@DAO��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param panaHousingImageInfoDAO
	 *            �����摜���p�@DAO
	 */
	public void setPanaHousingImageInfoDAO(DAO<HousingImageInfo> panaHousingImageInfoDAO) {
		this.panaHousingImageInfoDAO = panaHousingImageInfoDAO;
	}

	/**
	 * ������{���V�K�o�^���̃t�B���^�[����<br/>
	 * �Ϗ���̕��� model ���g�p���ĕ�����{����o�^��A�������������č\�z����B<br/>
	 * <br/>
	 *
	 * @param inputForm
	 *            ������{���̓��̓t�H�[��
	 * @param editUserId
	 *            �X�V��ID
	 *
	 * @return �̔Ԃ��ꂽ�V�X�e������CD
	 */
	@Override
	public String addHousing(HousingForm inputForm, String editUserId)
			throws Exception {

		return super.addHousing(inputForm, editUserId);
	}

	/**
	 * ������{���X�V���̃t�B���^�[����<br/>
	 * �Ϗ���̕��� model ���g�p���ĕ�����{�����X�V��A�������������č\�z����B<br/>
	 * <br/>
	 *
	 * @param inputForm
	 *            ������{���̓��̓t�H�[��
	 * @param editUserId
	 *            �X�V��ID
	 */
	@Override
	public void updateHousing(HousingForm inputForm, String editUserId)
			throws Exception, NotFoundException {

		super.updateHousing(inputForm, editUserId);
	}

	/**
	 * �������폜���̃t�B���^�[����<br/>
	 * ���������폜����O�ɁA�폜�ΏۂƂȂ镨���摜�����擾���A�p�X�����W�񂷂�B<br/>
	 * �Ϗ���̕��� model ���g�p���ĕ������̍폜���s���A�Y��������J�����摜�t�@�C�����폜����B<br/>
	 * <br/>
	 *
	 * @param inputForm
	 *            ������{���̓��̓t�H�[��
	 * @param editUserId
	 *            �X�V��ID
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void delHousingInfo(HousingForm inputForm, String editUserId)
			throws Exception {
		Set<String> delPath = new HashSet<>();
		PanaHousing housing = (PanaHousing)this.housingManage.searchHousingPk(inputForm.getSysHousingCd(), true);
		if (housing != null) {
			// ���t�H�[���v���������������A���ʂ𕜋A����
			List<Map<String, Object>>  reformList = housing.getReforms();
			for (Map<String, Object> reforms : reformList) {
				// ���t�H�[���v�������
			    ReformPlan reformPlan = (ReformPlan) reforms.get("reformPlan");
				// ���t�H�[���ڍ׏��
			    List<ReformDtl> dtlList = (List<ReformDtl>) reforms.get("dtlList");
			    // ���t�H�[���摜���
			    List<ReformImg> imgList = (List<ReformImg>) reforms.get("imgList");
			    // ���[�_�[�`���[�g�摜���폜
			    String rootPath = ""; // /�u�萔�l�v
			    if (reformPlan != null) {
			    	if(!StringValidateUtil.isEmpty(reformPlan.getReformChartImageFileName())){
				    	rootPath = PanaFileUtil.conPhysicalPath(((PanaCommonParameters)this.commonParameters).getHousImgOpenPhysicalMemberPath(), reformPlan.getReformChartImagePathName());
				    	if (!delPath.contains(rootPath)) {
				    		delPath.add(rootPath);
				    	}
			    	}
			    }

			    // ���t�H�[���ڍ׏��iPDF�t�@�C���j���폜
			    for (ReformDtl dtl : dtlList) {
			    	rootPath = ""; // /�u�萔�l�v
			        // �{������������݂̂̏ꍇ
			        if (PanaCommonConstant.ROLE_ID_PRIVATE.equals(dtl.getRoleId())) {
			           rootPath = ((PanaCommonParameters)this.commonParameters).getHousImgOpenPhysicalMemberPath();
			        } else {
			           // �{���������S���̏ꍇ
			        	rootPath = ((PanaCommonParameters)this.commonParameters).getHousImgOpenPhysicalPath();
			        }
			        if(!StringValidateUtil.isEmpty(dtl.getFileName())){
				        // /�u�萔�l�v/[�������CD]/[�s���{��CD]/[�s�撬��CD]/�V�X�e������CD/
				        rootPath = PanaFileUtil.conPhysicalPath(rootPath, dtl.getPathName());

				        if (!delPath.contains(rootPath)) {
				        	delPath.add(rootPath);
				        }
			        }
			    }

			    // ���t�H�[���摜�t�@�C�����폜
			    for (ReformImg img : imgList) {
			    	rootPath = ""; // /�u�萔�l�v
			        // �{������������݂̂̏ꍇ
			        if (PanaCommonConstant.ROLE_ID_PRIVATE.equals(img.getRoleId())) {
			        	rootPath = ((PanaCommonParameters)this.commonParameters).getHousImgOpenPhysicalMemberPath();
			        } else {
			        	// �{���������S���̏ꍇ
			        	rootPath = ((PanaCommonParameters)this.commonParameters).getHousImgOpenPhysicalPath();
			        }
			        // /�u�萔�l�v/[�������CD]/[�s���{��CD]/[�s�撬��CD]/�V�X�e������CD/
			        // After�摜�t�@�C���p�X
			        String afterPath = PanaFileUtil.conPhysicalPath(rootPath, img.getAfterPathName());
			        // Before�摜�t�@�C���p�X
			        String beforePath = PanaFileUtil.conPhysicalPath(rootPath, img.getBeforePathName());

			        if (!delPath.contains(afterPath)) {
			                delPath.add(afterPath);
			        }
			        if (!delPath.contains(beforePath)) {
			           delPath.add(beforePath);
			        }
			    }
			}

		    // �����摜���
		    DAOCriteria criteria = new DAOCriteria();
			criteria.addWhereClause("sysHousingCd", inputForm.getSysHousingCd());
			List<HousingImageInfo> delImgList = this.panaHousingImageInfoDAO.selectByFilter(criteria);

		    // �����g���������
		    Map<String, Map<String,String>> housingExtInfos = housing.getHousingExtInfos();

		    // �����摜���摜�t�@�C�����폜
		    for (HousingImageInfo img : delImgList) {
		    	String rootPath = ""; ///�u�萔�l�v
		        // �{������������݂̂̏ꍇ
		        if (PanaCommonConstant.ROLE_ID_PRIVATE.equals(img.getRoleId())) {
		        	rootPath = ((PanaCommonParameters)this.commonParameters).getHousImgOpenPhysicalMemberPath();
		        } else {
		        	// �{���������S���̏ꍇ
		        	rootPath = ((PanaCommonParameters)this.commonParameters).getHousImgOpenPhysicalPath();
		        }
		        // /�u�萔�l�v/[�������CD]/[�s���{��CD]/[�s�撬��CD]/�V�X�e������CD/
		        rootPath = PanaFileUtil.conPhysicalPath(rootPath, img.getPathName());

		        if (!delPath.contains(rootPath)) {
		                delPath.add(rootPath);
		        }
		    }

		    // �����g���������摜�t�@�C�����폜
		    if (housingExtInfos != null) {
		    	String rootPath = "";
		    	// �Z��f�f�t�@�C��
		    	if(housingExtInfos.get("housingInspection") != null){
		    		Map<String,String> housingInspection = housingExtInfos.get("housingInspection");

// 2015.06.26 H.Mizuno �摜���[�g����폜���������C�� start
//		    		rootPath = PanaFileUtil.conPhysicalPath(((PanaCommonParameters)this.commonParameters).getHousImgOpenPhysicalPath(), housingInspection.get("inspectionPathName"));
//		    		if (!delPath.contains(rootPath)) {
//		                delPath.add(rootPath);
//		    		}

		    		// housingInspection �� Key �����݂��邪�A���̃J�e�S������ inspectionPathName �����݂��Ȃ��P�[�X������ƁA
		    		// �摜���[�g����폜�����댯��������B�@inspectionPathName�@���擾�ł����ꍇ�̂ݍ폜����悤�ɕύX�B
		    		String inspectionPathName = housingInspection.get("inspectionPathName");
		    		if (!StringValidateUtil.isEmpty(inspectionPathName)){
		    			rootPath = PanaFileUtil.conPhysicalPath(((PanaCommonParameters)this.commonParameters).getHousImgOpenPhysicalMemberPath(), inspectionPathName);
		    			if (!delPath.contains(rootPath)) {
		    				delPath.add(rootPath);
		    			}
		    		}
// 2015.06.26 H.Mizuno �摜���[�g����폜���������C�� end
		    		String inspectionImagePathName = housingInspection.get("inspectionImagePathName");
		    		if (!StringValidateUtil.isEmpty(inspectionImagePathName)){
		    			rootPath = PanaFileUtil.conPhysicalPath(((PanaCommonParameters)this.commonParameters).getHousImgOpenPhysicalMemberPath(), inspectionImagePathName);
		    			if (!delPath.contains(rootPath)) {
		    				delPath.add(rootPath);
		    			}
		    		}
		    	}
		    	// �S���Ҏʐ^
		    	if(housingExtInfos.get("housingDetail") != null){
		    		Map<String,String> housingDetail = housingExtInfos.get("housingDetail");

// 2015.06.26 H.Mizuno �摜���[�g����폜���������C�� start
//		    		rootPath = PanaFileUtil.conPhysicalPath(((PanaCommonParameters)this.commonParameters).getHousImgOpenPhysicalPath(), housingDetail.get("staffImagePathName"));
//		    		if (!delPath.contains(rootPath)) {
//		                delPath.add(rootPath);
//		    		}

		    		// housingDetail �� Key �����݂��邪�A���̃J�e�S������ staffImagePathName �����݂��Ȃ��P�[�X������ƁA
		    		// �摜���[�g����폜�����댯��������B�@staffImagePathName�@���擾�ł����ꍇ�̂ݍ폜����悤�ɕύX�B
		    		String staffImagePathName = housingDetail.get("staffImagePathName");
		    		if (!StringValidateUtil.isEmpty(staffImagePathName)){
		    			rootPath = PanaFileUtil.conPhysicalPath(((PanaCommonParameters)this.commonParameters).getHousImgOpenPhysicalPath(), staffImagePathName);
		    			if (!delPath.contains(rootPath)) {
		    				delPath.add(rootPath);
		    			}
		    		}
// 2015.06.26 H.Mizuno �摜���[�g����폜���������C�� start
		    	}
		    }

		    // �Ϗ���� model �����s���ĕ��������폜����B
		    HousingInfo housingInfo = (HousingInfo)housing.getHousingInfo().getItems().get("housingInfo");
		    if (housingInfo != null) {
		    	inputForm.setSysBuildingCd(housingInfo.getSysBuildingCd());
		    }
		 	this.housingManage.delHousingInfo(inputForm, editUserId);

		 	for (String path : delPath){
				if (StringValidateUtil.isEmpty(path) || path.equals("/")){
					continue;
				}
// 2015.06.26 H.Mizuno �摜���[�g����폜���������C�� start
				// �폜�Ώۂ��摜���J���[�g�p�X�������ꍇ�A�폜���Ȃ��l�Ƀ`�F�b�N��ǉ�
				if (path.equals(((PanaCommonParameters)this.commonParameters).getHousImgOpenPhysicalPath()) ||
					path.equals(((PanaCommonParameters)this.commonParameters).getHousImgOpenPhysicalMemberPath())){
					continue;
				}
// 2015.06.26 H.Mizuno �摜���[�g����폜���������C�� end

				// �w�肳�ꂽ�t�H���_�z�����폜
				FileUtils.deleteDirectory(new File(path));
			}
		}
	}

	/**
	 * �����ڍ׍X�V���̃t�B���^�[����<br/>
	 * �Ϗ���̕��� model ���g�p���ĕ����ڍ׏���ǉ��E�X�V��A�������������č\�z����B<br/>
	 * <br/>
	 *
	 * @param inputForm
	 *            �����ڍ׏��̓��̓t�H�[��
	 * @param editUserId
	 *            �X�V��ID
	 */
	@Override
	public void updateHousingDtl(HousingDtlForm inputForm, String editUserId)
			throws Exception, NotFoundException {

		super.updateHousingDtl(inputForm, editUserId);
	}

	/**
	 * �����ݔ��X�V���̃t�B���^�[����<br/>
	 * �Ϗ���̕��� model ���g�p���ĕ����ݔ������X�V��A�������������č\�z����B<br/>
	 * <br/>
	 *
	 * @param inputForm
	 *            �����ڍ׏��̓��̓t�H�[��
	 * @param editUserId
	 *            �X�V��ID
	 */
	@Override
	public void updateHousingEquip(HousingEquipForm inputForm, String editUserId)
			throws Exception, NotFoundException {

		super.updateHousingEquip(inputForm, editUserId);
	}
	/**
	 * �w�肳�ꂽ�����摜���ɊY��������J��ƂȂ镨���摜�� Root �p�X���擾����B<br/>
	 * <br/>
	 * @return ���J����Ă��镨���摜�� Root �p�X���X�g
	 */
	@Override
	protected String getImgOpenRootPath(jp.co.transcosmos.dm3.core.vo.HousingImageInfo housingImageInfo){
		// ��{�@�\�ł́A�T���l�C���̏o�͐惋�[�g�t�H���_�͌Œ�l
		String destRoot = "";
		if(PanaCommonConstant.ROLE_ID_PUBLIC.equals(((jp.co.transcosmos.dm3.corePana.vo.HousingImageInfo)housingImageInfo).getRoleId())){
			destRoot = ((jp.co.transcosmos.dm3.corePana.constant.PanaCommonParameters)this.commonParameters).getHousImgOpenPhysicalPath();
		}
		else if(PanaCommonConstant.ROLE_ID_PRIVATE.equals(((jp.co.transcosmos.dm3.corePana.vo.HousingImageInfo)housingImageInfo).getRoleId())){
			destRoot = ((jp.co.transcosmos.dm3.corePana.constant.PanaCommonParameters)this.commonParameters).getHousImgOpenPhysicalMemberPath();
		}
		if (!destRoot.endsWith("/")) destRoot += "/";

		return destRoot;
	}
	/**
	 * �����摜�ǉ����̃t�B���^�[����<br/>
	 * <br/>
	 *
	 * @param inputForm
	 *            �����ڍ׏��̓��̓t�H�[��
	 * @param editUserId
	 *            �X�V��ID
	 *
	 * @return �V���ɒǉ������摜���̃��X�g
	 */
	@Override
	public List<jp.co.transcosmos.dm3.core.vo.HousingImageInfo> addHousingImg(HousingImgForm inputForm,
			String editUserId) throws Exception, NotFoundException {

		return super.addHousingImg(inputForm, editUserId);

	}

	/**
	 * �����摜�X�V���̃t�B���^�[����<br/>
	 * ���摜�t�@�C���̓���ւ��͔������Ȃ��B�@�폜�̂݁B<br/>
	 * <br/>
	 *
	 * @param inputForm
	 *            �����ڍ׏��̓��̓t�H�[��
	 * @param editUserId
	 *            �X�V��ID
	 *
	 * @return �폜�����������摜���
	 */
	@Override
	public List<jp.co.transcosmos.dm3.core.vo.HousingImageInfo> updHousingImg(HousingImgForm inputForm,
			String editUserId) throws Exception, NotFoundException {

		// �Ϗ���̏��������s����B
		// �폜���ꂽ�����摜���̃��X�g���߂����B
		List<jp.co.transcosmos.dm3.core.vo.HousingImageInfo> imgList = this.housingManage.updHousingImg(inputForm, editUserId);

		// �������������č\�z����B
		createPartInfo(inputForm.getSysHousingCd(), "updHousingImg");

		// null �̏ꍇ�A�폜�摜�����������Ƃ��� null �𕜋A����B
		if (imgList == null)
			return null;

		for (int idx = 0; idx < inputForm.getDivNo().length; idx++) {
			if (!StringUtils.isEmpty(inputForm.getDivNo()[idx])) {

				HousingImageInfo imgInfo = new HousingImageInfo();

				imgInfo.setRoleId(((jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingImageInfoForm)inputForm).getOldRoleId()[idx]);

				String srcPath = getImgOpenRootPath(imgInfo);
				String filePath = PanaFileUtil.conPhysicalPath(srcPath , ((jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingImageInfoForm)inputForm).getPathName()[idx]);

				// �폜�����̏ꍇ
				if ("1".equals(inputForm.getDelFlg()[idx])) {

					// �ړ���A�ړ����̃C���[�W�t�@�C���Ƃ��̃T���l�C�����폜
					this.thumbnailCreator.deleteImgFile(filePath,
							inputForm.getFileName()[idx]);
				} else {
					// �X�V�����̏ꍇ
					// �{�������̐ݒ肪�ύX���ꂽ�ꍇ
					if (!((jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingImageInfoForm)inputForm).getOldRoleId()[idx].equals(
							((jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingImageInfoForm)inputForm).getRoleId()[idx])) {

						imgInfo.setRoleId(((jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingImageInfoForm)inputForm).getRoleId()[idx]);

						String NewsrcPath = getImgOpenRootPath(imgInfo);
						String NewfilePath = PanaFileUtil.conPhysicalPath(NewsrcPath , ((jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingImageInfoForm)inputForm).getPathName()[idx]);

						// �T���l�C���쐬�p MAP �̍쐬
						Map<String, String> thumbnailMap = new HashMap<>();
						thumbnailMap.put(PanaFileUtil.conPhysicalPath(
								filePath,
								((jp.co.transcosmos.dm3.corePana.constant.PanaCommonParameters)this.commonParameters).getAdminSiteFullFolder()+"/"+ inputForm.getFileName()[idx]),
								NewfilePath);

						// �e�X�g���\�b�h���s
						this.thumbnailCreator.create(thumbnailMap);

						// �ړ���A�ړ����̃C���[�W�t�@�C���Ƃ��̃T���l�C�����폜
						this.thumbnailCreator.deleteImgFile(filePath,
								inputForm.getFileName()[idx]);
					}
				}
			}
		}

		return imgList;
	}

	/**
	 * �����摜�폜���̃t�B���^�[����<br/>
	 * ��updHousingImg() ����ł��폜�B�@�ʍ폜�p�@�\�B<br/>
	 * <br/>
	 *
	 * @param inputForm
	 *            �����ڍ׏��̓��̓t�H�[��
	 * @param editUserId
	 *            �X�V��ID
	 */
	@Override
	public jp.co.transcosmos.dm3.core.vo.HousingImageInfo delHousingImg(String sysHousingCd,
			String imageType, int divNo, String editUserId) throws Exception {

		return super.delHousingImg(sysHousingCd, imageType, divNo, editUserId);
	}

	/**
	 * �������������̃t�B���^�[����<br/>
	 * ���̂܂܈Ϗ�����B<br/>
	 * <br/>
	 *
	 * @param searchForm
	 *            ���������t�H�[��
	 */
	@Override
	public int searchHousing(HousingSearchForm searchForm) throws Exception {
		// �������̌��������ł́A�Ϗ�������̂܂܎��s����B
		return super.searchHousing(searchForm);
	}

	/**
	 * �������������̃t�B���^�[����<br/>
	 * ���̂܂܈Ϗ�����B<br/>
	 * <br/>
	 *
	 * @param searchForm
	 *            ���������t�H�[��
	 * @param full
	 *            false �̏ꍇ�A���J�ΏۊO�����O����B�@true �̏ꍇ�͏��O���Ȃ�
	 */
	@Override
	public int searchHousing(HousingSearchForm searchForm, boolean full)
			throws Exception {
		// �������̌��������ł́A�Ϗ�������̂܂܎��s����B
		return super.searchHousing(searchForm, full);
	}

	/**
	 * �����ڍ׎擾�����̃t�B���^�[����<br/>
	 * ���̂܂܈Ϗ�����B<br/>
	 * <br/>
	 *
	 * @param sysHousingCd
	 *            �擾�ΏۃV�X�e������CD
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
	 *
	 * @param sysHousingCd
	 *            �擾�ΏۃV�X�e������CD
	 * @param full
	 *            false �̏ꍇ�A���J�ΏۊO�����O����B�@true �̏ꍇ�͏��O���Ȃ�
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
	public void updExtInfo(String sysHousingCd,
			Map<String, Map<String, String>> inputData, String editUserId)
			throws Exception, NotFoundException {

		// �����g�������̍X�V�����ł́A�Ϗ���̏��������̂܂܎��s����B
		super.updExtInfo(sysHousingCd, inputData, editUserId);
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
		super.delExtInfo(sysHousingCd, editUserId);
	}

	/**
	 * �����g�������X�V�̃t�B���^�[����<br/>
	 * ���̂܂܈Ϗ�����B<br/>
	 * <br/>
	 */
	@Override
	public void updExtInfo(String sysHousingCd, String category,
			Map<String, String> inputData, String editUserId) throws Exception,
			NotFoundException {

		// �����g�������̍X�V�����ł́A�Ϗ���̏��������̂܂܎��s����B
		super.updExtInfo(sysHousingCd, category, inputData, editUserId);
	}

	/**
	 * �����g�������폜�̃t�B���^�[����<br/>
	 * ���̂܂܈Ϗ�����B<br/>
	 * <br/>
	 */
	@Override
	public void delExtInfo(String sysHousingCd, String category,
			String editUserId) throws Exception {

		// �����g�������̍폜�����ł́A�Ϗ���̏��������̂܂܎��s����B
		super.delExtInfo(sysHousingCd, category, editUserId);
	}

	/**
	 * �����g�������X�V�̃t�B���^�[����<br/>
	 * ���̂܂܈Ϗ�����B<br/>
	 * <br/>
	 */
	@Override
	public void updExtInfo(String sysHousingCd, String category, String key,
			String value, String editUserId) throws Exception,
			NotFoundException {

		// �����g�������̍X�V�����ł́A�Ϗ���̏��������̂܂܎��s����B
		super.updExtInfo(sysHousingCd, category, key, value, editUserId);
	}

	/**
	 * �����g�������폜�̃t�B���^�[����<br/>
	 * ���̂܂܈Ϗ�����B<br/>
	 * <br/>
	 */
	@Override
	public void delExtInfo(String sysHousingCd, String category, String key,
			String editUserId) throws Exception {

		// �����g�������̍폜�����ł́A�Ϗ���̏��������̂܂܎��s����B
		super.delExtInfo(sysHousingCd, category, key, editUserId);
	}

	/**
	 * �������I�u�W�F�N�g�̃C���X�^���X�����̃t�B���^�[����<br/>
	 * ���̂܂܈Ϗ�����B<br/>
	 * <br/>
	 *
	 * @return Housing �̃C���X�^���X
	 */
	@Override
	public Housing createHousingInstace() {
		// �����I�u�W�F�N�g�̃C���X�^���X�����́A�Ϗ���̏��������̂܂܎��s����B
		return super.createHousingInstace();
	}

	/**
	 * �������������č쐬����B<br/>
	 * <br/>
	 *
	 * @param sysHousingCd
	 *            �V�X�e������CD
	 * @param methodName
	 *            ���s���ꂽ���� model �̃��\�b�h��
	 *
	 * @throws Exception
	 */
	protected void createPartInfo(String sysHousingCd, String methodName)
			throws Exception {

		super.createPartInfo(sysHousingCd, methodName);
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

		return ((PanaHousingManageImpl)this.housingManage).addHousingStatus(form);
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
		((PanaHousingManageImpl)this.housingManage).updateHousingStatus(form, editUserId);
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
	 * @exception Exception
	 *                �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	public void delHousingInspection(String sysHousingCd) throws Exception {

		((PanaHousingManageImpl)this.housingManage).delHousingInspection(sysHousingCd);

	}

	/**
	 * �X�e�[�^�X�̐V�K���s��<br/>
	 * <br/>
	 *
	 * @param form
	 *            �X�e�[�^�X�̓��͒l���i�[���� Form �I�u�W�F�N�g
	 *
	 */
	public String addHousingInspection(PanaHousingInspectionForm form, int idx)
			throws Exception {

		return ((PanaHousingManageImpl)this.housingManage).addHousingInspection(form, idx);
	}

	/**
	 * �p�����[�^�œn���ꂽ Form �̏��Ń��t�H�[���v��������V�K�ǉ�����B<br/>
	 * �o���f�[�V�������̃`�F�b�N�����͌Ăяo�����ŗ\�ߎ��{���Ă������B<br/>
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

		return ((PanaHousingManageImpl)this.housingManage).searchHousingInspection(sysHousingCd);
	}

	/**
	 * �p�����[�^�œn���ꂽ �����ڍ׏���V�K�ǉ�����B<br/>
	 * �}�� �͎����̔Ԃ����̂ŁAReformDtl �� divNo �v���p�e�B�ɂ͒l��ݒ肵�Ȃ����B<br/>
	 * <br/>
	 *
	 * @param reformDtl
	 *            �����ڍ׏��
	 *
	 */
	public void updateBuildingDtlInfo(PanaHousingInfoForm inputForm,
			String editUserId) {

		((PanaHousingManageImpl)this.housingManage).updateBuildingDtlInfo(inputForm, editUserId);

	}

	/**
	 * ���N�G�X�g�p�����[�^�œn���ꂽ�������CD �i��L�[�l�j�ɊY������ݔ��}�X�^���𕜋A����B<br/>
	 * �����Y���f�[�^��������Ȃ��ꍇ�Anull �𕜋A����B<br/>
	 * <br/>
	 * ���̃��\�b�h���g�p�����ꍇ�A�Öق̒��o�����i�Ⴆ�΁A����J�����̏��O�Ȃǁj���K�p�����B<br/>
	 * ����āA�t�����g���͊�{�I�ɂ��̃��\�b�h���g�p���鎖�B<br/>
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
	public List<JoinResult> searchEquipMst(String housingKindCd) throws Exception {

		return ((PanaHousingManageImpl)this.housingManage).searchEquipMst(housingKindCd);
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
	public void searchCsvHousing(PanaHousingSearchForm searchForm,HttpServletResponse response,
			PanaHousingPartThumbnailProxy panaHousingManager, PanaCommonManage panamCommonManager)throws Exception {

		((PanaHousingManageImpl)this.housingManage).searchCsvHousing(searchForm, response, panaHousingManager, panamCommonManager);
	}

	/**
	 * �������̃^�C���X�^���v���X�V�B<br/>
	 * <br/>
	 *
	 * @param sysHousingCd
	 *            �V�X�e������CD
	 * @param editUserId
	 *            ���[�UID
	 *
	 * @return �Ȃ�
	 *
	 * @exception Exception
	 *                �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	public void updateEditTimestamp(String sysHousingCd, String editUserId)
			throws Exception {
		((PanaHousingManageImpl)this.housingManage).updateEditTimestamp(sysHousingCd, editUserId);
	}

	/**
	 * �����������}�X�^�����擾�B<br/>
	 * <br/>
	 *
	 * @param housingKindCd
	 *            �������
	 * @return �����������}�X�^���
	 *
	 * @exception Exception
	 *                �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	public List<JoinResult> searchPartSrchMst(String housingKindCd) throws Exception {


		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("housingKindCd", housingKindCd);

		return this.partSrchMstListDAO.selectByFilter(criteria);
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
	public void deleteImgFile(String filePathName, String fileName, jp.co.transcosmos.dm3.core.vo.HousingImageInfo imgInfo, String fileType) throws IOException{
		String filePath = getImgOpenRootPath(imgInfo) + filePathName;
		if(((jp.co.transcosmos.dm3.corePana.constant.PanaCommonParameters)this.commonParameters).getAdminSitePdfFolder().equals(fileType)){
			// �摜�t�@�C���̍폜���������s����B
			this.deleteImgFile(filePath, fileName , ((jp.co.transcosmos.dm3.corePana.constant.PanaCommonParameters)this.commonParameters).getAdminSitePdfFolder());
		}
		if(((jp.co.transcosmos.dm3.corePana.constant.PanaCommonParameters)this.commonParameters).getAdminSiteChartFolder().equals(fileType)){
			// �摜�t�@�C���̍폜���������s����B
			this.deleteImgFile(filePath, fileName , ((jp.co.transcosmos.dm3.corePana.constant.PanaCommonParameters)this.commonParameters).getAdminSiteChartFolder());
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
	 * @param thumbnailMap �쐬����t�@�C���̏��
	 *
	 * @throws Exception �Ϗ��悪�X���[����C�ӂ̗�O
	 */
	public void addImgFile(String sysHousingCd, String fileName, jp.co.transcosmos.dm3.core.vo.HousingImageInfo imgInfo , String fileType) throws Exception{

		// �����Y���f�[�^�����݂��Ȃ��ꍇ�i�����e���ɑ�����폜���ꂽ�ꍇ�Ȃǁj�͗�O���X���[����B
		Housing housing = this.searchHousingPk(sysHousingCd,true);
		if (housing == null){
			throw new NotFoundException();
		}
		// �p�X����ݒ肷��B
		String filePath = ((PanaHousingManageImpl)this.housingManage).createImagePath(housing);
		// �T���l�C���̍쐬���t�H���_���@�i���t���w�肵���t�H���_�K�w�܂Łj
		String srcRoot = this.commonParameters.getHousImgTempPhysicalPath();
		if (!srcRoot.endsWith("/")) srcRoot += "/";
		srcRoot += PanaFileUtil.getUploadTempPath() + "/";

		// �T���l�C�����쐬����t�@�C�����̃}�b�v�I�u�W�F�N�g���쐬����B
		Map<String, String> thumbnailMap = new HashMap<>();

		// Map �� Key �́A�T���l�C���쐬���̃t�@�C�����i�t���p�X�j
		String key = srcRoot + fileName;
		// Map �� Value �́A�T���l�C���̏o�͐�p�X�i���[�g�`�V�X�e�������ԍ��܂ł̃p�X�j
		String value = getImgOpenRootPath(imgInfo) + filePath;

		thumbnailMap.put(key, value);

		if(((jp.co.transcosmos.dm3.corePana.constant.PanaCommonParameters)this.commonParameters).getAdminSitePdfFolder().equals(fileType)){
			this.create(thumbnailMap, ((jp.co.transcosmos.dm3.corePana.constant.PanaCommonParameters)this.commonParameters).getAdminSitePdfFolder());
		}
		if(((jp.co.transcosmos.dm3.corePana.constant.PanaCommonParameters)this.commonParameters).getAdminSiteChartFolder().equals(fileType)){
			this.create(thumbnailMap, ((jp.co.transcosmos.dm3.corePana.constant.PanaCommonParameters)this.commonParameters).getAdminSiteChartFolder());
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
	 * @param thumbnailMap �쐬����t�@�C���̏��
	 *
	 * @throws IOException
	 * @throws Exception �Ϗ��悪�X���[����C�ӂ̗�O
	 */
	public void create(Map<String, String> thumbnailMap,String filetype)
			throws IOException, Exception {

		// �쐬����t�@�C�����J��Ԃ�
		for (Entry<String, String> e : thumbnailMap.entrySet()){

			// �T���l�C���쐬���̃t�@�C���I�u�W�F�N�g���쐬����B
			File srcFile = new File(e.getKey());

			// �T���l�C���o�͐�̃��[�g�p�X �i�摜�T�C�Y�̒��O�܂ł̃t�H���_�K�w�j
			String destRootPath = e.getValue();
			if (!destRootPath.endsWith("/")) destRootPath += "/";
			// �I���W�i���摜���t���T�C�Y�摜�Ƃ��� copy ����B
			if(((jp.co.transcosmos.dm3.corePana.constant.PanaCommonParameters)this.commonParameters).getAdminSitePdfFolder().equals(filetype)){
				FileUtils.copyFileToDirectory(srcFile, new File(destRootPath + ((jp.co.transcosmos.dm3.corePana.constant.PanaCommonParameters)this.commonParameters).getAdminSitePdfFolder()));
			}
			if(((jp.co.transcosmos.dm3.corePana.constant.PanaCommonParameters)this.commonParameters).getAdminSiteChartFolder().equals(filetype)){
				FileUtils.copyFileToDirectory(srcFile, new File(destRootPath + ((jp.co.transcosmos.dm3.corePana.constant.PanaCommonParameters)this.commonParameters).getAdminSiteChartFolder()));
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

	/**
	 * �����摜�t�@�C�����ʂɍ폜����B<br/>
	 * filePath �Ŏw�肵���t�H���_���̃t�@�C������̏ꍇ�A�t�H���_���ƍ폜����B
	 * <br/>
	 * @param filePath ���[�g�`�V�X�e������CD �܂ł̃p�X�i�摜�T�C�Y�̉��܂ł̃p�X�j
	 * @param fileName�@�摜�t�@�C����
	 *
	 * @throws IOException
	 */
	public void deleteImgFile(String filePath, String fileName , String filetype) throws IOException{

		// �I���W�i���摜�̍폜
		if(((jp.co.transcosmos.dm3.corePana.constant.PanaCommonParameters)this.commonParameters).getAdminSitePdfFolder().equals(filetype)){
			(new File(filePath + ((jp.co.transcosmos.dm3.corePana.constant.PanaCommonParameters)this.commonParameters).getAdminSitePdfFolder() + "/" + fileName)).delete();
		}
		if(((jp.co.transcosmos.dm3.corePana.constant.PanaCommonParameters)this.commonParameters).getAdminSiteChartFolder().equals(filetype)){
			(new File(filePath + ((jp.co.transcosmos.dm3.corePana.constant.PanaCommonParameters)this.commonParameters).getAdminSiteChartFolder() + "/" + fileName)).delete();
		}
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
	public String createImagePath(Housing housing) {

		return ((PanaHousingManageImpl)this.housingManage).createImagePath(housing);
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
	 * �s�撬���l���擾����B<br/>
	 * <br/>
	 *
	 * @param housing
	 *            �X�V�ΏۂƂȂ镨���I�u�W�F�N�g
	 *
	 * @return ���H���ꂽ�p�X��
	 * @throws Exception
	 */
	public int searchHousingInfo(PanaHousingSearchForm areaForm) throws Exception {

		return ((PanaHousingManageImpl)this.housingManage).searchHousingInfo(areaForm);
	}
}
