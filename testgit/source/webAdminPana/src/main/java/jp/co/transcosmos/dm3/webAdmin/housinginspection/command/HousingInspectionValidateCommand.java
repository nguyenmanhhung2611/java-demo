package jp.co.transcosmos.dm3.webAdmin.housinginspection.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonParameters;
import jp.co.transcosmos.dm3.corePana.model.housing.PanaHousingPartThumbnailProxy;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingFormFactory;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingInspectionForm;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingSearchForm;
import jp.co.transcosmos.dm3.corePana.util.PanaFileUtil;
import jp.co.transcosmos.dm3.validation.Validateable;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

import org.apache.commons.fileupload.FileItem;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;
/**
 * <pre>
 * �Z��f�f�����͊m�F���
 * ���N�G�X�g�p�����[�^�œn���ꂽ���t�H�[�����̃o���f�[�V�������s���A�m�F��ʂ�\������B
 * �����o���f�[�V�����G���[�����������ꍇ�͓��͉�ʂƂ��ĕ\������B
 *
 * �y���A���� View ���z
 *    �E"success" : ����I��
 *
 * �S����       �C����      �C�����e
 * ------------ ----------- -----------------------------------------------------
 * fan			2015.04.21	�V�K�쐬
 *
 * ���ӎ���
 *
 * </pre>
 */
public class HousingInspectionValidateCommand implements Command {

	/** Panasonic�p�t�@�C�������֘A����Util */
	private PanaFileUtil fileUtil;

	/**
	 * Panasonic�p�t�@�C�������֘A����Util��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param fileUtil
	 *            Panasonic�p�t�@�C�������֘A����Util
	 */
	public void setFileUtil(PanaFileUtil fileUtil) {
		this.fileUtil = fileUtil;
	}
	/** ���ʃp�����[�^�I�u�W�F�N�g */
    private PanaCommonParameters commonParameters;

    /**
     * ���ʃp�����[�^�I�u�W�F�N�g��ݒ肷��B<br/>
     * <br/>
     * @param commonParameters ���ʃp�����[�^�I�u�W�F�N�g
     */
    public void setCommonParameters(PanaCommonParameters commonParameters) {
        this.commonParameters = commonParameters;
    }
    /** �Z��f�f��񃁃��e�i���X���s�� Model �I�u�W�F�N�g */
	private PanaHousingPartThumbnailProxy panaHousingManager;

	/**
	 * �Z��f�f��񃁃��e�i���X���s�� Model�@�I�u�W�F�N�g��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param panaHousingManager
	 *            �Z��f�f��񃁃��e�i���X�� model �I�u�W�F�N�g
	 */
	public void setPanaHousingManager(PanaHousingPartThumbnailProxy panaHousingManager) {
		this.panaHousingManager = panaHousingManager;
	}

	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Map<String, Object> model = new HashMap<String, Object>();
		// ���N�G�X�g�p�����[�^���擾���� Form �I�u�W�F�N�g���쐬����B
		PanaHousingFormFactory factory = PanaHousingFormFactory.getInstance(request);
		// ���N�G�X�g�p�����[�^���i�[���� form �I�u�W�F�N�g�𐶐�����B
		Object[] forms = factory.createPanaHousingInspectionFormAndSearchForm(request);

		// ���N�G�X�g�p�����[�^���i�[���� form �I�u�W�F�N�g�𐶐�����B
		PanaHousingInspectionForm form = (PanaHousingInspectionForm)forms[0];
		PanaHousingSearchForm searchForm = (PanaHousingSearchForm)forms[1];

		model.put("searchForm", searchForm);

		// path = "\data\reform\temp\"
        String temPath = PanaFileUtil.getUploadTempPath();
        FileItem fi = form.getHousingFile();
        if (fi != null && !StringUtils.isEmpty(fi.getName())) {

            // �C���[�W�P���A�b�v���[�h���A�߂�l�F�t�@�C�������擾
            String fileName = panaHousingManager.getReformPdfFileName();
            PanaFileUtil.uploadFile(fi,
                    PanaFileUtil.conPhysicalPath(this.commonParameters.getHousImgTempPhysicalPath(), temPath),
                    fileName);

            String urlPath = fileUtil.getHousFileTempUrl(temPath,fileName);
            form.setHidNewPath(urlPath);
            form.setLoadFlg("1");
            form.setAddHidFileName(fileName);
        }
        FileItem imgFi = form.getHousingImgFile();
        if (imgFi != null && !StringUtils.isEmpty(imgFi.getName())) {

            // �C���[�W�P���A�b�v���[�h���A�߂�l�F�t�@�C�������擾
            String fileName = panaHousingManager.getReformJpgFileName();
            PanaFileUtil.uploadFile(imgFi,
                    PanaFileUtil.conPhysicalPath(this.commonParameters.getHousImgTempPhysicalPath(), temPath),
                    fileName);

            String urlPath = fileUtil.getHousFileTempUrl(temPath,fileName);
            form.setHidNewImgPath(urlPath);
            form.setImgFlg("1");
            form.setAddHidImgName(fileName);
        }

        // �o���f�[�V��������
		Validateable validateableForm = (Validateable) form;
		// �G���[���b�Z�[�W�p�̃��X�g���쐬
		List<ValidationFailure> errors = new ArrayList<ValidationFailure>();

		// �o���f�[�V���������s
		if (!validateableForm.validate(errors)) {
			// �G���[����
			// �G���[�I�u�W�F�N�g�ƁA�t�H�[���I�u�W�F�N�g��ModelAndView �ɓn���Ă���
			model.put("errors", errors);
			model.put("HousingInspectionForm", form);
			return new ModelAndView("validationError", model);
		}
		model.put("HousingInspectionForm", form);
		return new ModelAndView("success", model);
	}

}
