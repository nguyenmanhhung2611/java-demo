package jp.co.transcosmos.dm3.webAdmin.housingdtl.command;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.util.ImgUtils;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonParameters;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingDtlInfoForm;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingFormFactory;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingSearchForm;
import jp.co.transcosmos.dm3.corePana.model.reform.ReformPartThumbnailProxy;
import jp.co.transcosmos.dm3.corePana.util.PanaFileUtil;
import jp.co.transcosmos.dm3.corePana.util.PanaStringUtils;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

import org.apache.commons.io.FileUtils;
import org.springframework.web.servlet.ModelAndView;

/**
 * <pre>
 * �����ڍ׏��ҏW���
 * ���N�G�X�g�p�����[�^�œn���ꂽ�����ڍ׏��̃o���f�[�V�������s���A�m�F��ʂ�\������B
 * �����o���f�[�V�����G���[�����������ꍇ�͓��͉�ʂƂ��ĕ\������B
 *
 * �y���A���� View ���z
 *    �E"success" : ����I��
 *    �E"input" : �o���f�[�V�����G���[�ɂ��ē���
 *
 * �S����       �C����     �C�����e
 * ------------ ----------- -----------------------------------------------------
 * liu.yandong  2015.04.06  �V�K�쐬
 *
 * ���ӎ���
 *
 * </pre>
 */
public class HousingdtlConfirmCommand implements Command {
	/** ���ʃp�����[�^�I�u�W�F�N�g */
	protected PanaCommonParameters commonParameters;

    /** ���t�H�[����񃁃��e�i���X���s�� Model �I�u�W�F�N�g */
    private ReformPartThumbnailProxy reformManager;

	/** �T���l�C���摜�쐬�N���X */
	private ImgUtils imgUtils;

	/** Panasonic�p�t�@�C�������֘A����Util */
	private PanaFileUtil panaFileUtil;

    /**
     * ���t�H�[����񃁃��e�i���X���s�� Model�@�I�u�W�F�N�g��ݒ肷��B<br/>
     * <br/>
     *
     * @param reformManage
     *            ���t�H�[����񃁃��e�i���X�� model �I�u�W�F�N�g
     */
    public void setReformManager(ReformPartThumbnailProxy reformManager) {
        this.reformManager = reformManager;
    }

	/**
	 * @param commonParameters �Z�b�g���� commonParameters
	 */
	public void setCommonParameters(PanaCommonParameters commonParameters) {
		this.commonParameters = commonParameters;
	}

	/**
	 * @param imgUtils �Z�b�g���� imgUtils
	 */
	public void setImgUtils(ImgUtils imgUtils) {
		this.imgUtils = imgUtils;
	}

	/**
	 * @param panaFileUtil �Z�b�g���� panaFileUtil
	 */
	public void setPanaFileUtil(PanaFileUtil panaFileUtil) {
		this.panaFileUtil = panaFileUtil;
	}

	/**
	 * �����ڍ׏��m�F��ʕ\������<br>
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

		// view ���̏����l��ݒ�
		String viewName = "success";

		// �o���f�[�V���������s
		List<ValidationFailure> errors = new ArrayList<ValidationFailure>();
		if (!inputForm.validate(errors)) {
			// �o���f�[�V�����G���[���́A�G���[���� model �ɐݒ肵�A���͉�ʂ�\������B
			model.put("errors", errors);
			model.put("inputForm", inputForm);
			viewName = "input";
			return new ModelAndView(viewName, model);
		}

		//�S���Ҏʐ^�A�b�v���[�h
		if (StringValidateUtil.isEmpty(inputForm.getPictureDataDelete()) && inputForm.getPictureDataValue().getSize() > 0) {
			// �T���l�C���̍쐬���t�H���_���@�i���t���w�肵���t�H���_�K�w�܂Łj
			String srcRoot = this.commonParameters.getHousImgTempPhysicalPath();
            // ���t�H���_���@�i���t�����j
            String tempDate = PanaFileUtil.getUploadTempPath();
            // �t�@�C�������擾
            String fileName = reformManager.getReformJpgFileName();

			inputForm.setPictureDataPath(srcRoot + tempDate + "/");
			inputForm.setPictureDataFileName(fileName);
			inputForm.setPictureUpFlg("1");
			// �e���v���[�g�t�H���_�փA�b�v���[�h
			PanaFileUtil.uploadFile(inputForm.getPictureDataValue(),
							PanaFileUtil.conPhysicalPath(srcRoot, tempDate),
							fileName, 1);

			/** �T���l�C�����쐬����t�@�C�����̃}�b�v�I�u�W�F�N�g���쐬����B **/
			Map<String, String> thumbnailMap = new HashMap<>();

			// Map �� Key �́A�T���l�C���쐬���̃t�@�C�����i�t���p�X�j
			String key = inputForm.getPictureDataPath() + inputForm.getPictureDataFileName();
			// Map �� Value �́A�T���l�C���̏o�͐�p�X�i���[�g�`�V�X�e�������ԍ��܂ł̃p�X�j
			String value = inputForm.getPictureDataPath();

			thumbnailMap.put(key, value);
			// �T���l�C�����쐬
			createStaffImage(thumbnailMap, PanaStringUtils.toInteger(this.commonParameters.getAdminSiteStaffImageSize()));
			// �v���r���[�摜�p�p�X�ݒ�
			String previewImgPath = this.panaFileUtil.getHousFileTempUrl(tempDate + "/" + this.commonParameters.getAdminSiteStaffFolder(), fileName);
			inputForm.setPreviewImgPath(previewImgPath);
		}
		inputForm.setDtlComment1(PanaStringUtils.encodeHtml(inputForm.getDtlComment()));
		inputForm.setBasicComment1(PanaStringUtils.encodeHtml(inputForm.getBasicComment()));
		inputForm.setVendorComment1(PanaStringUtils.encodeHtml(inputForm.getVendorComment()));
		inputForm.setReformComment1(PanaStringUtils.encodeHtml(inputForm.getReformComment()));
		model.put("inputForm", inputForm);

		return new ModelAndView(viewName, model);

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
		Object[] forms = factory.createPanaHousingDtlInfoFormAndSearchForm(request);

		model.put("inputForm", (PanaHousingDtlInfoForm)forms[0]);
		model.put("searchForm", (PanaHousingSearchForm)forms[1]);

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
