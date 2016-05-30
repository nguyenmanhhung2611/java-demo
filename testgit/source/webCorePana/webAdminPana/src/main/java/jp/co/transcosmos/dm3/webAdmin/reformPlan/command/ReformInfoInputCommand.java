package jp.co.transcosmos.dm3.webAdmin.reformPlan.command;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.model.exception.NotFoundException;
import jp.co.transcosmos.dm3.core.model.housing.Housing;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonParameters;
import jp.co.transcosmos.dm3.corePana.model.housing.PanaHousingPartThumbnailProxy;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingFormFactory;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingSearchForm;
import jp.co.transcosmos.dm3.corePana.model.reform.ReformPartThumbnailProxy;
import jp.co.transcosmos.dm3.corePana.model.reform.form.ReformFormFactory;
import jp.co.transcosmos.dm3.corePana.model.reform.form.ReformInfoForm;
import jp.co.transcosmos.dm3.corePana.util.PanaFileUtil;
import jp.co.transcosmos.dm3.corePana.vo.ReformDtl;
import jp.co.transcosmos.dm3.corePana.vo.ReformImg;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;

import org.springframework.web.servlet.ModelAndView;

/**
 * <pre>
 * ���t�H�[�����
 *
 * �y�V�K�o�^�̏ꍇ�z
 *  ���N�G�X�g�p�����[�^���󂯎��A�o���f�[�V���������s����B
 *  �J�ڐ��ʂ���擾�����V�X�e������CD�A�V�X�e�����t�H�[��CD�ɂ��A
 *  DB�������āA��ʊe���ڂ������ݒ�B
 *
 *
 * �S����       �C����      �C�����e
 * ------------ ----------- -----------------------------------------------------
 * Zhang.		2015.03.11	�V�K�쐬
 *
 * ���ӎ���
 *
 * </pre>
 */
public class ReformInfoInputCommand implements Command {

    /** ���t�H�[����񃁃��e�i���X���s�� Model �I�u�W�F�N�g */
    private ReformPartThumbnailProxy reformManager;

	/** ������񃁃��e�i���X�p Model */
	private PanaHousingPartThumbnailProxy panaHousingManager;

    /** ���ʃp�����[�^�I�u�W�F�N�g */
    private PanaCommonParameters commonParameters;

    /** Panasonic�p�t�@�C�������֘A���� */
    private PanaFileUtil fileUtil;
    
    /**
     * ���t�H�[����񃁃��e�i���X���s�� Model�@�I�u�W�F�N�g��ݒ肷��B<br/>
     * <br/>
     * @param reformManage ���t�H�[����񃁃��e�i���X�� model �I�u�W�F�N�g
     */
    public void setReformManager(ReformPartThumbnailProxy reformManager) {
        this.reformManager = reformManager;
    }

	/**
	 * ������񃁃��e�i���X���s�� Model�@�I�u�W�F�N�g��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param panaHousingManager
	 *            ������񃁃��e�i���X�� model �I�u�W�F�N�g
	 */
	public void setPanaHousingManager(PanaHousingPartThumbnailProxy panaHousingManager) {
		this.panaHousingManager = panaHousingManager;
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
     *���t�H�[�������͉�ʏ���<br>
     * <br>
     * @param request HTTP ���N�G�X�g
     * @param response HTTP ���X�|���X
    */
    @Override
    public ModelAndView handleRequest(HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        // ���N�G�X�g�p�����[�^���i�[���� model �I�u�W�F�N�g�𐶐�����B
        // ���̃I�u�W�F�N�g�� View �w�ւ̒l���n���Ɏg�p�����B
        Map<String, Object> model = createModel(request);
        ReformInfoForm form = (ReformInfoForm) model.get("inputForm");

        // �߂�{�^���������i�V�K�j
        if ("iBack".equals(form.getCommand())) {

        	// ��ʍ��ڊi�[
            form.setCommand("insert");
            form.setImgSelFlg("0");
            form.setImgFile1(form.getImgFile2());

            return new ModelAndView("success", model);

            // �߂�{�^���������i�X�V�j
        } else if ("uBack".equals(form.getCommand())) {

            // ��ʍ��ڊi�[
            updateFormat(model, form, "");
            form.setImgSelFlg("0");

            return new ModelAndView("success", model);
        }

        // �e�폈�������s
        execute(model, form);
        
        return new ModelAndView("success", model);
    }

    /**
     * �����̐U�蕪���Ǝ��s���s���B<br/>
     * <br/>
     * @param model View �w�ֈ����n�� model �I�u�W�F�N�g
     * @param inputForm ���͒l���i�[���ꂽ Form �I�u�W�F�N�g
     *
     * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
     */
    protected void execute(Map<String, Object> model,
            ReformInfoForm form) throws Exception {

    	// �������̂̎擾
        String displayHousingName = this.reformManager.searchHousingInfo(form.getSysHousingCd())
                .getDisplayHousingName();

        // ��ʍ��ڊi�[
        if (StringValidateUtil.isEmpty(form.getSysReformCd())) {

            // �V�K�̏ꍇ
            form.insertFormat(displayHousingName);

        } else {

            // �X�V�̏ꍇ
            updateFormat(model, form, displayHousingName);
        }
    }

    /**
     * ���t�H�[�����i�X�V�j���͉�ʊi�[<br/>
     * <br/>
     * @param model View �w�ֈ����n�� model �I�u�W�F�N�g
     * @param form ���͒l���i�[���ꂽ Form �I�u�W�F�N�g
     * @param displayHousingName ��������
     *
     * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
     */
    @SuppressWarnings("unchecked")
    private void updateFormat( Map<String, Object> model,
            ReformInfoForm form,
            String displayHousingName) throws Exception {

        // ���t�H�[���v���������擾
        Map<String, Object> result =
                (Map<String, Object>) this.reformManager.searchReform(form.getSysReformCd());

		// �������̎擾
        Housing housing =
        		panaHousingManager.searchHousingPk(form.getSysHousingCd(), true);
        if (housing == null) {
        	throw new NotFoundException();
        }

        // ���t�H�[���ڍ׃��X�g
        model.put("dtlList", form.setDtlList((List<ReformDtl>) result.get("dtlList"), commonParameters, fileUtil));
        // ���t�H�[���摜��񃊃X�g
        model.put("imgList", form.setImgList((List<ReformImg>) result.get("imgList"), commonParameters, fileUtil));
        // ���t�H�[�����i�X�V�j���͉�ʊi�[
        form.updateFormat(result, displayHousingName, housing, commonParameters, fileUtil);

    }


	/**
	 * model �I�u�W�F�N�g���쐬����B<br/>
	 * <br/>
	 * @param request HTTP ���N�G�X�g
	 * @return �p�����[�^��ݒ肵�� Form ���i�[���� model �I�u�W�F�N�g�𕜋A����B
	 */
	 protected Map<String, Object> createModel(HttpServletRequest request) {

		Map<String, Object> model = new HashMap<String, Object>();

		// ���N�G�X�g�p�����[�^���擾���� Form �I�u�W�F�N�g���쐬����B
		ReformFormFactory factory = ReformFormFactory.getInstance(request);
		ReformInfoForm requestForm = factory.createRefromInfoForm(request);
        model.put("inputForm", requestForm);

        PanaHousingFormFactory housingFactory = PanaHousingFormFactory.getInstance(request);
        PanaHousingSearchForm searchForm = housingFactory.createPanaHousingSearchForm(request);
        model.put("searchForm", searchForm);

        return model;
	}
}
