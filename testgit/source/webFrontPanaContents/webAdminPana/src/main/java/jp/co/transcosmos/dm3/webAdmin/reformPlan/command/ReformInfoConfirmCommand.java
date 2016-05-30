package jp.co.transcosmos.dm3.webAdmin.reformPlan.command;

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
import jp.co.transcosmos.dm3.corePana.model.reform.form.ReformInfoForm;
import jp.co.transcosmos.dm3.corePana.util.PanaFileUtil;
import jp.co.transcosmos.dm3.corePana.util.PanaStringUtils;
import jp.co.transcosmos.dm3.corePana.vo.ReformDtl;
import jp.co.transcosmos.dm3.corePana.vo.ReformImg;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

import org.apache.commons.fileupload.FileItem;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;

/**
 * <pre>
 * ���t�H�[�������͊m�F���
 * ���N�G�X�g�p�����[�^�œn���ꂽ���t�H�[�����̃o���f�[�V�������s���A�m�F��ʂ�\������B
 * �����o���f�[�V�����G���[�����������ꍇ�͓��͉�ʂƂ��ĕ\������B
 *
 * �y���A���� View ���z
 *    �E"success" : ����I��
 *    �E"input" : �o���f�[�V�����G���[�ɂ��ē���
 *
 * �S����       �C����      �C�����e
 * ------------ ----------- -----------------------------------------------------
 * zhang		2015.03.11	�V�K�쐬
 *
 * ���ӎ���
 *
 * </pre>
*/
public class ReformInfoConfirmCommand implements Command {

    /** ���t�H�[����񃁃��e�i���X���s�� Model �I�u�W�F�N�g */
    private ReformPartThumbnailProxy reformManager;

    /** ���ʃp�����[�^�I�u�W�F�N�g */
    private PanaCommonParameters commonParameters;

    /** Panasonic�p�t�@�C�������֘A���� */
    private PanaFileUtil fileUtil;

    /**
     * ���t�H�[����񃁃��e�i���X���s�� Model�@�I�u�W�F�N�g��ݒ肷��B<br/>
     * <br/>
     * @param reformManager ���t�H�[����񃁃��e�i���X�� model �I�u�W�F�N�g
     */
    public void setReformManager(ReformPartThumbnailProxy reformManager) {
        this.reformManager = reformManager;
    }

    /**
     * ���ʃp�����[�^�I�u�W�F�N�g��ݒ肷��B<br/>
     * <br/>
     * @param commonParameters ���ʃp�����[�^�I�u�W�F�N�g
     */
    public void setCommonParameters(PanaCommonParameters commonParameters) {
        this.commonParameters = commonParameters;
    }

    /**
     * Panasonic�p�t�@�C�������֘A���ʂ�ݒ肷��B<br/>
     * <br/>
     * @param fileUtil Panasonic�p�t�@�C�������֘A����
     */
    public void setFileUtil(PanaFileUtil fileUtil) {
        this.fileUtil = fileUtil;
    }

    /**
     *���t�H�[�������͊m�F��ʕ\������<br>
     * <br>
     * @param request HTTP ���N�G�X�g
     * @param response HTTP ���X�|���X
    */
    public ModelAndView handleRequest(HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        // ���N�G�X�g�p�����[�^���i�[���� model �I�u�W�F�N�g�𐶐�����B
        // ���̃I�u�W�F�N�g�� View �w�ւ̒l���n���Ɏg�p�����B
        Map<String, Object> model = createModel(request);
        ReformInfoForm inputForm = (ReformInfoForm) model.get("inputForm");

        // view ���̏����l��ݒ�
        String viewName = "success";

        // �o���f�[�V���������s
        List<ValidationFailure> errors = new ArrayList<ValidationFailure>();
        if (!inputForm.validate(errors)) {

            // �Č���
            reSearch(model, inputForm);

            // �o���f�[�V�����G���[���́A�G���[���� model �ɐݒ肵�A���͉�ʂ�\������B
            model.put("errors", errors);

            viewName = "input";

        } else {
        	// �A�b�v���[�h�摜�i�[�AURL�p�X��ݒ�
            getImgPath(inputForm);
            inputForm.setSalesPoint1(PanaStringUtils.encodeHtml(inputForm.getSalesPoint()));
            inputForm.setNote1(PanaStringUtils.encodeHtml(inputForm.getNote()));
        }

        return new ModelAndView(viewName, model);
    }

    /**
     * ���t�H�[�������͊m�F��ʕ\������<br>
     * <br/>
     * @param model Map ���N�G�X�g�p�����[�^���i�[�p
     * @param inputForm ReformInfoForm ���t�H�[���v�������̓��͒l���i�[���� Form �I�u�W�F�N�g
     *
     * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
     */
    private void reSearch(Map<String, Object> model,
            ReformInfoForm inputForm) throws Exception {

        // �u�V�K�ǉ��v�{�^��������
        Map<String, Object> result = new HashMap<String, Object>();
        if ("insert".equals(inputForm.getCommand())) {

            // ��ʍ��ڊi�[
            pageFormat(result, model, inputForm);

            // �u�X�V�v�{�^��������
        } else if ("update".equals(inputForm.getCommand())) {

            // ���t�H�[���v���������擾
            result = (Map<String, Object>) this.reformManager.searchReform(inputForm.getSysReformCd());

            // ��ʍ��ڊi�[
            pageFormat(result, model, inputForm);
        }
    }

    /**
     * ���t�H�[�������͉�ʊi�[<br>
     * <br/>
     * @param result ���t�H�[���v�������
     * @param model Map ���N�G�X�g�p�����[�^���i�[�p
     * @param form ReformInfoForm ���t�H�[���v�������̓��͒l���i�[���� Form �I�u�W�F�N�g
     *
     */
    @SuppressWarnings("unchecked")
    private void pageFormat(Map<String, Object> result,
            Map<String, Object> model,
            ReformInfoForm form) {

        if ("update".equals(form.getCommand())) {
            // ���t�H�[���ڍ׃��X�g
            model.put("dtlList", form.setDtlList((List<ReformDtl>) result.get("dtlList"), commonParameters, fileUtil));
            // ���t�H�[���摜��񃊃X�g
            model.put("imgList", form.setImgList((List<ReformImg>) result.get("imgList"), commonParameters, fileUtil));
        }
    }

    /**
     * �A�b�v���[�h�摜�i�[�AURL�p�X��ݒ�<br>
     * <br/>
     * @param inputForm ReformInfoForm ���t�H�[���v�������̓��͒l���i�[���� Form �I�u�W�F�N�g
     *
     * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
     */
    private String getImgPath(ReformInfoForm inputForm) throws Exception {

    	String urlPath = "";
        FileItem fi = inputForm.getReformImgFile();
        if (fi != null && !StringUtils.isEmpty(fi.getName())) {

        	// tempPath = �u�N�����v
            String temPath = PanaFileUtil.getUploadTempPath();
        	// fileName = �uXXX.jpg�v
            String fileName = reformManager.getReformJpgFileName();

            // �A�b�v���[�h�摜�i�[
            PanaFileUtil.uploadFile(fi,
                    PanaFileUtil.conPhysicalPath(this.commonParameters.getHousImgTempPhysicalPath(), temPath),
                    fileName);

            // �摜URL�p�X��ݒ�
            urlPath = fileUtil.getHousFileTempUrl(temPath, fileName);

            inputForm.setImgName(fileName);
            inputForm.setTemPath(temPath);
            inputForm.setImgFile1(urlPath);
            inputForm.setImgSelFlg("1");
        }

        return urlPath;
    }

    /**
     * ���N�G�X�g�p�����[�^���� Form �I�u�W�F�N�g���쐬����B<br/>
     * �������� Form �I�u�W�F�N�g�� Map �Ɋi�[���ĕ��A����B<br/>
     * key = �t�H�[���N���X���i�p�b�P�[�W�Ȃ��j�AValue = �t�H�[���I�u�W�F�N�g
     * <br/>
     * @param request HTTP ���N�G�X�g�p�����[�^
     * @return �p�����[�^���ݒ肳�ꂽ�t�H�[���I�u�W�F�N�g���i�[���� Map �I�u�W�F�N�g
     */
    protected Map<String, Object> createModel(HttpServletRequest request) {

        Map<String, Object> model = new HashMap<String, Object>();

        // ���N�G�X�g�p�����[�^���擾���� Form �I�u�W�F�N�g���쐬����B
		ReformFormFactory factory = ReformFormFactory.getInstance(request);
		Object[] requestForms = factory.createRefromInfoFormAndSearchForm(request);

		ReformInfoForm reformForm = (ReformInfoForm)requestForms[0];
		PanaHousingSearchForm searchForm = (PanaHousingSearchForm)requestForms[1];

        model.put("inputForm", reformForm);
        model.put("searchForm", searchForm);

        return model;
    }
}
