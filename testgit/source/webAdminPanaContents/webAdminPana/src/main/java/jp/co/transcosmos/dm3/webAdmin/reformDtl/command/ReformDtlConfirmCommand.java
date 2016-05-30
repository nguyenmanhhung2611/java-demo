package jp.co.transcosmos.dm3.webAdmin.reformDtl.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.model.exception.NotFoundException;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonParameters;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingSearchForm;
import jp.co.transcosmos.dm3.corePana.model.reform.ReformPartThumbnailProxy;
import jp.co.transcosmos.dm3.corePana.model.reform.form.ReformDtlForm;
import jp.co.transcosmos.dm3.corePana.model.reform.form.ReformFormFactory;
import jp.co.transcosmos.dm3.corePana.util.PanaFileUtil;
import jp.co.transcosmos.dm3.corePana.vo.HousingInfo;
import jp.co.transcosmos.dm3.corePana.vo.ReformDtl;
import jp.co.transcosmos.dm3.corePana.vo.ReformPlan;
import jp.co.transcosmos.dm3.validation.Validateable;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

import org.apache.commons.fileupload.FileItem;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;

/**
 * <pre>
 * ���t�H�[���ڍ׏����͊m�F���
 * ���N�G�X�g�p�����[�^�œn���ꂽ���t�H�[�����̃o���f�[�V�������s���A�m�F��ʂ�\������B
 * �����o���f�[�V�����G���[�����������ꍇ�͓��͉�ʂƂ��ĕ\������B
 *
 * �y���A���� View ���z
 *    �E"success" : ����I��
 *    �E"input" : �o���f�[�V�����G���[�ɂ��ē���
 *
 * �S����       �C����      �C�����e
 * ------------ ----------- -----------------------------------------------------
 * fan			2015.03.11	�V�K�쐬
 *
 * ���ӎ���
 *
 * </pre>
 */
public class ReformDtlConfirmCommand implements Command {

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
     * ���͊m�F��ʕ\������<br>
     * <br>
     *
     * @param request
     *            HTTP ���N�G�X�g
     * @param response
     *            HTTP ���X�|���X
     */
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // ���N�G�X�g�p�����[�^���A�t�H�[���I�u�W�F�N�g�֐ݒ肷��B
        Map<String, Object> model = new HashMap<String, Object>();

        // ���N�G�X�g�p�����[�^���A�t�H�[���I�u�W�F�N�g�֐ݒ肷��B
        ReformFormFactory factory = ReformFormFactory.getInstance(request);

     	Object[] forms = factory.createReformDtlForms(request);
        ReformDtlForm form = (ReformDtlForm)forms[0];

        // ���N�G�X�g�p�����[�^���擾���� Form �I�u�W�F�N�g���쐬����B
 		PanaHousingSearchForm searchForm = (PanaHousingSearchForm)forms[1];
 		model.put("searchForm", searchForm);

        // ���O�`�F�b�N:������{���e�[�u���̓Ǎ�
        HousingInfo housingInfo = this.reformManager.searchHousingInfo(form.getSysHousingCd());
        if (housingInfo.getSysHousingCd() == null) {
            // �f�[�^�̑��݂��Ȃ��ꍇ,���b�Z�[�W�F"�o0�p������񂪑��݂��Ȃ�"�\��
            throw new NotFoundException();
        }
        model.put("housingInfo", housingInfo);
        // ���t�H�[���֘A�����ꊇ�Ǎ�
        Map<String, Object> reform = this.reformManager.searchReform(form.getSysReformCd());

        // ���t�H�[���v�������̓Ǎ�
        ReformPlan reformPlan = (ReformPlan) reform.get("reformPlan");

        // ���O�`�F�b�N
        if (reformPlan == null) {
            // �f�[�^�̑��݂��Ȃ��ꍇ,���b�Z�[�W�F"�o0�p�V�X�e�����t�H�[����񂪑��݂��Ȃ�"�\��
            throw new NotFoundException();
        }

        // ���t�H�[���ڍׂ̌���
        List<ReformDtl> reformDtlList = (List<ReformDtl>) reform.get("dtlList");
        // �{������
        String[] roleId = new String[reformDtlList.size()];
        for (int i = 0; i < reformDtlList.size(); i++) {
            roleId[i] = reformDtlList.get(i).getRoleId();
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
            form.setOldRoleId(roleId);
            model.put("reformDtlForm", form);

            return new ModelAndView("validationError", model);
        }

        // �X�V�����̏ꍇ
        if ("update".equals(form.getCommand())) {
            // ���폈��
            setResultLists(form);
            // �t�H�[���I�u�W�F�N�g�݂̂�ModelAndView �ɓn���Ă���
            model.put("reformDtlForm", form);
            return new ModelAndView("success", model);
        }

        // path = "\data\reform\temp\"
        String temPath = PanaFileUtil.getUploadTempPath();

        // �V�K�o�^�����̏ꍇ
        String[] addHidPath = new String[form.getAddFilePath().length];
        String[] addHidFileName = new String[form.getAddFilePath().length];
        for (int idx = 0; idx < form.getAddFilePath().length; ++idx) {
            FileItem fi = form.getAddFilePath()[idx];
            if (fi != null && !StringUtils.isEmpty(fi.getName())) {

                // �C���[�W�P���A�b�v���[�h���A�߂�l�F�t�@�C�������擾
                String fileName = reformManager.getReformPdfFileName();
                PanaFileUtil.uploadFile(fi,
                        PanaFileUtil.conPhysicalPath(this.commonParameters.getHousImgTempPhysicalPath(), temPath),
                        fileName);

                String urlPath = fileUtil.getHousFileTempUrl(temPath,fileName);
                // �A�b�v���[�h�����T�[�o�[���̕����p�X��ێ�
                addHidPath[idx] =  urlPath;
                addHidFileName[idx] = fileName;
            }
        }
        form.setAddHidPath(addHidPath);
        form.setAddHidFileName(addHidFileName);

        setResultLists(form);

        // �t�H�[���I�u�W�F�N�g�݂̂�ModelAndView �ɓn���Ă���
        model.put("reformDtlForm", form);

        return new ModelAndView("success", model);
    }

    /**
     * �m�F��ʕ\���pform��ݒ肷�鏈���B <br>
     *
     * @param form
     *            �t�H�[���ڍ׏����form�B
     * @return �m�F��ʕ\���pform
     */
    private void setResultLists(ReformDtlForm form) {
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
