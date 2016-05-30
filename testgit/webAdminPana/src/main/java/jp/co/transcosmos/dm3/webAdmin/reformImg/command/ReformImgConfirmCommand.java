package jp.co.transcosmos.dm3.webAdmin.reformImg.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonParameters;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingSearchForm;
import jp.co.transcosmos.dm3.corePana.model.reform.ReformPartThumbnailProxy;
import jp.co.transcosmos.dm3.corePana.model.reform.form.ReformFormFactory;
import jp.co.transcosmos.dm3.corePana.model.reform.form.ReformImgForm;
import jp.co.transcosmos.dm3.corePana.util.PanaFileUtil;
import jp.co.transcosmos.dm3.corePana.vo.HousingInfo;
import jp.co.transcosmos.dm3.corePana.vo.ReformImg;
import jp.co.transcosmos.dm3.validation.Validateable;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

import org.apache.commons.fileupload.FileItem;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;

/**
 * ���t�H�[���摜�ҏW���
 *
 * <pre>
 * �y���A���� View ���z
 *    �E"success" : ����I��
 *
 * �S����       �C����      �C�����e
 * ------------ ----------- -----------------------------------------------------
 *   ��		  2015.03.10    �V�K�쐬
 *
 * ���ӎ���
 *
 * </pre>
 */
public class ReformImgConfirmCommand implements Command {


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
    /** ���t�H�[����񃁃��e�i���X���s�� Model �I�u�W�F�N�g */
    private ReformPartThumbnailProxy reformManager;

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

    /**
     * ���t�H�[���摜�ҏW��ʕ\������<br>
     * <br>
     *
     * @param request
     *            HTTP ���N�G�X�g
     * @param response
     *            HTTP ���X�|���X
     */
    @Override
    public ModelAndView handleRequest(HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        Map<String, Object> model = new HashMap<String, Object>();

    	ReformFormFactory factory = ReformFormFactory.getInstance(request);
        // ���N�G�X�g�p�����[�^���A�t�H�[���I�u�W�F�N�g�֐ݒ肷��B
    	Object[] forms = factory.createRefromImgForms(request);
    	ReformImgForm form = (ReformImgForm)forms[0];

    	// ���N�G�X�g�p�����[�^���擾���� Form �I�u�W�F�N�g���쐬����B
 		PanaHousingSearchForm searchForm = (PanaHousingSearchForm)forms[1];
 		model.put("searchForm", searchForm);

        // ���O�`�F�b�N:������{���e�[�u���̓Ǎ�
        HousingInfo housingInfo = this.reformManager.searchHousingInfo(form.getSysHousingCd());
        model.put("housingInfo", housingInfo);
        // ���t�H�[���֘A�����ꊇ�Ǎ�
        Map<String, Object> reform = this.reformManager.searchReform(form.getSysReformCd());

        // ���t�H�[���ڍׂ̌���
        List<ReformImg> reformImgresults = (List<ReformImg>) reform.get("imgList");
        // �{������
        String[] roleId = new String[reformImgresults.size()];
        for (int i = 0; i < reformImgresults.size(); i++) {
        	 // �{������
            roleId[i] = reformImgresults.get(i).getRoleId();
        }

        // �o���f�[�V��������
        Validateable validateableForm = (Validateable) form;
        // �G���[���b�Z�[�W�p�̃��X�g���쐬
        List<ValidationFailure> errors = new ArrayList<ValidationFailure>();

        // �o���f�[�V���������s
        if (!validateableForm.validate(errors)) {
            // �G���[����

            setResultLists(form);
            // �G���[�I�u�W�F�N�g�ƁA�t�H�[���I�u�W�F�N�g��ModelAndView �ɓn���Ă���
            model.put("errors", errors);
         // �{������
            form.setEditOldRoleId(roleId);
            model.put("ReformImgForm", form);
            return new ModelAndView("validationError", model);
        }
        // �X�V�����̏ꍇ
        if ("update".equals(form.getCommand())) {
            // ���폈��
            setResultLists(form);
            // �t�H�[���I�u�W�F�N�g�݂̂�ModelAndView �ɓn���Ă���
            model.put("ReformImgForm", form);
            return new ModelAndView("success", model);
        }

        // path = "\data\reform\temp\"
        String temPath = PanaFileUtil.getUploadTempPath();

        // �V�K�o�^�����̏ꍇ
        String[] addBeforePath = new String[form.getUploadBeforePathName().length];
        String[] addBeforeFileName = new String[form.getUploadBeforePathName().length];
        String[] addBeforePathMin = new String[form.getUploadBeforePathName().length];
        for (int idx = 0; idx < form.getUploadBeforePathName().length; ++idx) {
            FileItem fi = form.getUploadBeforePathName()[idx];
            if (fi != null && !StringUtils.isEmpty(fi.getName())) {

                // �C���[�W�P���A�b�v���[�h���A�߂�l�F�t�@�C�������擾
                String fileName = reformManager.getReformJpgFileName();
                // �A�b�v���[�h
                this.reformManager.addTempFile(fi, temPath, fileName);

                String urlPathMax = fileUtil.getHousFileTempUrl(temPath+"/"+this.commonParameters.getThumbnailSizes().get(this.commonParameters.getThumbnailSizes().size() - 1).toString(),fileName);
                String urlPathMin = fileUtil.getHousFileTempUrl(temPath+"/"+this.commonParameters.getThumbnailSizes().get(0).toString(),fileName);
                // �A�b�v���[�h�����T�[�o�[���̕����p�X��ێ�
                addBeforePath[idx] = urlPathMax;
                addBeforeFileName[idx] = fileName;
                addBeforePathMin[idx] = urlPathMin;
                // �A�b�v���[�h�����T�[�o�[����URL�p�X��ݒ�
            }
        }
        form.setUploadBeforeHidPathMin(addBeforePathMin);
        form.setUploadBeforeHidPath(addBeforePath);
        form.setUploadBeforeFileName(addBeforeFileName);

        // �V�K�o�^�����̏ꍇ
        String[] addAfterPath = new String[form.getUploadAfterPathName().length];
        String[] addAfterFileName = new String[form.getUploadAfterPathName().length];
        String[] addAfterPathMin = new String[form.getUploadAfterPathName().length];
        for (int idx = 0; idx < form.getUploadAfterPathName().length; ++idx) {
            FileItem fi = form.getUploadAfterPathName()[idx];
            if (fi != null && !StringUtils.isEmpty(fi.getName())) {

                // �C���[�W�P���A�b�v���[�h���A�߂�l�F�t�@�C�������擾
                String fileName = reformManager.getReformJpgFileName();

                // �A�b�v���[�h
                this.reformManager.addTempFile(fi, temPath, fileName);

                String urlPathMax = fileUtil.getHousFileTempUrl(temPath+"/"+this.commonParameters.getThumbnailSizes().get(this.commonParameters.getThumbnailSizes().size() - 1).toString(),fileName);
                String urlPathMin = fileUtil.getHousFileTempUrl(temPath+"/"+this.commonParameters.getThumbnailSizes().get(0).toString(),fileName);
                // �A�b�v���[�h�����T�[�o�[���̕����p�X��ێ�
                addAfterPath[idx] = urlPathMax;
                addAfterFileName[idx] = fileName;
                addAfterPathMin[idx] = urlPathMin;
                // �A�b�v���[�h�����T�[�o�[����URL�p�X��ݒ�
            }
        }
        form.setUploadAfterHidPath(addAfterPath);
        form.setUploadAfterFileName(addAfterFileName);
        form.setUploadAfterHidPathMin(addAfterPathMin);
        setResultLists(form);

        model.put("ReformImgForm", form);

        // ���폈��
        return new ModelAndView("success", model);

    }

    /**
     * �m�F��ʕ\���pform��ݒ肷�鏈���B <br>
     *
     * @param form
     *            �t�H�[���ڍ׏����form�B
     * @return �m�F��ʕ\���pform
     */
    private void setResultLists(ReformImgForm form) {
        if (form.getDivNo() != null) {
            String[] delLength = new String[form.getDivNo().length];
            for (int i = 0; i < form.getDivNo().length; i++) {
                if (form.getDivNo()[i] != null && form.getDivNo()[i] != "") {

                    if (form.getDelFlg() != null) {
                        for (int j = 0; j < form.getDelFlg().length; j++) {
                            if (i == Integer.valueOf(form.getDelFlg()[j])) {
                                delLength[i] = "1";
                                break;
                            } else {
                                delLength[i] = "0";
                            }
                        }
                    } else {
                        delLength[i] = "0";
                    }
                }
            }
            form.setDelFlg(delLength);
        }
    }
}
