package jp.co.transcosmos.dm3.webAdmin.reformPlan.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.model.adminUser.AdminUserInterface;
import jp.co.transcosmos.dm3.core.model.exception.NotFoundException;
import jp.co.transcosmos.dm3.core.util.AdminLoginUserUtils;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonConstant;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonParameters;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingFormFactory;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingSearchForm;
import jp.co.transcosmos.dm3.corePana.model.reform.ReformPartThumbnailProxy;
import jp.co.transcosmos.dm3.corePana.model.reform.form.ReformInfoForm;
import jp.co.transcosmos.dm3.corePana.util.PanaFileUtil;
import jp.co.transcosmos.dm3.corePana.vo.HousingInfo;
import jp.co.transcosmos.dm3.corePana.vo.ReformDtl;
import jp.co.transcosmos.dm3.corePana.vo.ReformImg;
import jp.co.transcosmos.dm3.corePana.vo.ReformPlan;
import jp.co.transcosmos.dm3.form.FormPopulator;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.servlet.ModelAndView;

/**
 * ���t�H�[�����̒ǉ��A�ύX�A�폜����.
 * <p>
 * �y�V�K�o�^�̏ꍇ�z<br/>
 * <ul>
 * <li>���N�G�X�g�p�����[�^���󂯎��A�o���f�[�V���������s����B</li>
 * <li>�o���f�[�V����������I�������ꍇ�A���t�H�[������V�K�o�^����B</li>
 * <li>�܂��A���J�悪����l�̏ꍇ�A���t�H�[�����J������V�K�o�^����B</li>
 * </ul>
 * <br/>
 * �y�X�V�o�^�̏ꍇ�z<br/>
 * <ul>
 * <li>���N�G�X�g�p�����[�^���󂯎��A�o���f�[�V���������s����B</li>
 * <li>�o���f�[�V����������I�������ꍇ�A���t�H�[�������X�V����B</li>
 * <li>���t�H�[�����J����͈�x�폜���A�ύX��̌��J�悪����l�ł���΁A���t�H�[�����J������V�K�o�^����B</li>
 * <li>�����A�X�V�Ώۃf�[�^�����݂��Ȃ��ꍇ�A�X�V�������p���ł��Ȃ��̂ŊY��������ʂ�\������B</li>
 * </ul>
 * <br/>
 * �y���A���� View ���z<br/>
 * <ul>
 * <li>success</li>:����I���i���_�C���N�g�y�[�W�j
 * <li>input</li>:�o���f�[�V�����G���[�ɂ��ē���
 * <li>notFound</li>:�Y���f�[�^�����݂��Ȃ��ꍇ�i�X�V�����̏ꍇ�j
 * <li>comp</li>:������ʕ\��
 * </ul>
 * <p>
 * <pre>
 * �S����		�C����		�C�����e
 * ------------ ----------- -----------------------------------------------------
 * Zhang.Yu		2015.03.12	�V�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 *
 */
public class ReformInfoCompCommand implements Command {

    /** ���ʃR�[�h�ϊ����� */
    private CodeLookupManager codeLookupManager;

    /** ���t�H�[����񃁃��e�i���X���s�� Model �I�u�W�F�N�g */
    private ReformPartThumbnailProxy reformManager;

    /** ���ʃp�����[�^�I�u�W�F�N�g */
    private PanaCommonParameters commonParameters;

    /** Panasonic�p�t�@�C�������֘A���� */
    private PanaFileUtil fileUtil;

    /**
     * ���ʃR�[�h�ϊ��I�u�W�F�N�g��ݒ肷��B<br/>
     * <br/>
     * @param codeLookupManager
     */
    public void setCodeLookupManager(CodeLookupManager codeLookupManager) {
        this.codeLookupManager = codeLookupManager;
    }

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
     * ���t�H�[�����̒ǉ��A�ύX����<br>
     * <br>
     * @param request �N���C�A���g�����Http���N�G�X�g�B
     * @param response �N���C�A���g�ɕԂ�Http���X�|���X�B
     */
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        // ���N�G�X�g�p�����[�^���i�[���� model �I�u�W�F�N�g�𐶐�����B
        // ���̃I�u�W�F�N�g�� View �w�ւ̒l���n���Ɏg�p�����B
        Map<String, Object> model = createModel(request);
        ReformInfoForm inputForm = (ReformInfoForm) model.get("inputForm");

        // ���O�C�����[�U�[�̏����擾����B�@�i�^�C���X�^���v�̍X�V�p�j
        AdminUserInterface loginUser = AdminLoginUserUtils.getInstance(request).getLoginUserInfo(request, response);

        // ������ʂŃ����[�h�����ꍇ�A�X�V�������Ӑ}�������s������肪��������B
        // ���̖�����������ׁAview ���� "success"�@���w�肷��Ǝ������_�C���N�g��ʂ��\�������B
        // ���̃��_�C���N�g��ʂ́Acommand �p�����[�^�� "redirect"�@�ɐݒ肵�Ċ�����ʂփ��N�G�X�g��
        // ���M����B
        // ����āAcommand = "redirect" �̏ꍇ�́A�c�a�X�V�͍s�킸�A������ʂ�\������B
        String command = inputForm.getCommand();
        if (command != null && "redirect".equals(command)) {
            return new ModelAndView("comp", model);
        }

        // ��ʍ��ڃ`�F�b�N�A���O�`�F�b�N���s��
        if ("insert".equals(command) || "update".equals(command)) {

            // �o���f�[�V���������s
            List<ValidationFailure> errors = new ArrayList<ValidationFailure>();
            inputForm.validate(errors);

            // �摜���݃`�F�b�N
           // inputForm.reformImgFileCheck(errors, commonParameters);

            if (errors.size() > 0) {

                // �o���f�[�V�����G���[���́A�G���[���� model �ɐݒ肵�A���͉�ʂ�\������B
                model.put("errors", errors);

                // ���t�H�[�������͉�ʊi�[
                pageFormat(model, inputForm);

                // ���O�C��ID �����݂��Ȃ��ꍇ�́A�Y���Ȃ���ʂ�
                return new ModelAndView("input", model);
            }
        }

        // ���O�`�F�b�N:������{���e�[�u���̓Ǎ�
        HousingInfo housingInfo = this.reformManager.searchHousingInfo(inputForm.getSysHousingCd());
        if (housingInfo.getSysHousingCd() == null) {
            // �f�[�^�̑��݂��Ȃ��ꍇ,���b�Z�[�W�F"�o0�p������񂪑��݂��Ȃ�"�\��
            throw new NotFoundException();
        }

        try {
            // �e�폈�������s
            execute(model, inputForm, loginUser);
        } catch (Exception e) {
            // ���O�C��ID �����݂��Ȃ��ꍇ�́A�Y���Ȃ���ʂ�
            return new ModelAndView("input", model);
        }

        return new ModelAndView("success", model);
    }

    /**
     * �����̐U�蕪���Ǝ��s���s���B<br/>
     * <br/>
     * @param model View �w�ֈ����n�� model �I�u�W�F�N�g
     * @param inputForm ���͒l���i�[���ꂽ Form �I�u�W�F�N�g
     * @param loginUser �^�C���X�^���v�X�V���Ɏg�p���郍�O�C�����[�U�[���
     *
     * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
     * @exception NotFoundException �X�V�Ώۂ����݂��Ȃ��ꍇ
     */
    protected void execute(Map<String, Object> model, ReformInfoForm inputForm, AdminUserInterface loginUser)
            throws Exception, NotFoundException {

        if ("insert".equals(inputForm.getCommand())) {

            insert(model, inputForm, loginUser);

        } else if ("update".equals(inputForm.getCommand())) {

            update(model, inputForm, loginUser);

        }
    }

    /**
     * �V�K�o�^����<br/>
     * �����œn���ꂽ���e�Ń��t�H�[������ǉ�����B<br/>
     * <br/>
     * @param model View �w�ֈ����n�� model �I�u�W�F�N�g
     * @param inputForm ���͒l���i�[���ꂽ Form �I�u�W�F�N�g
     * @param loginUser �^�C���X�^���v�X�V���Ɏg�p���郍�O�C�����[�U�[���
     *
     * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
     */
    protected void insert(Map<String, Object> model, ReformInfoForm inputForm, AdminUserInterface loginUser)
            throws Exception {
        ReformPlan reformPlan = new ReformPlan();

        inputForm.copyToReformPlan(reformPlan, String.valueOf(loginUser.getUserId()));
        // ���t�H�[���v�������o�^����
        String sysReformCd = this.reformManager.addReformPlan(reformPlan, inputForm, (String)loginUser.getUserId());

        // ���t�H�[���E���[�_�[�`���[�g���o�^����
        inputForm.setSysReformCd(sysReformCd);
        if (PanaCommonConstant.HOUSING_KIND_CD_MANSION.equals(inputForm.getHousingKindCd()) ||
        		PanaCommonConstant.HOUSING_KIND_CD_HOUSE.equals(inputForm.getHousingKindCd())) {
        	this.reformManager.updReformChart(inputForm, 8);
        }

        // ������{���̃^�C���X�^���v�����X�V
        this.reformManager.updateEditTimestamp(inputForm.getSysHousingCd(),
        		inputForm.getSysReformCd(), (String)loginUser.getUserId());
    }

    /**
     * �X�V�o�^����<br/>
     * �����œn���ꂽ���e�Ń��t�H�[�������X�V����B<br/>
     * <br/>
     * @param model View �w�ֈ����n�� model �I�u�W�F�N�g
     * @param inputForm ���͒l���i�[���ꂽ Form �I�u�W�F�N�g
     * @param loginUser �^�C���X�^���v�X�V���Ɏg�p���郍�O�C�����[�U�[���
     *
     * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
     * @exception NotFoundException �X�V�Ώۂ����݂��Ȃ��ꍇ
     */
    protected void update(Map<String, Object> model, ReformInfoForm inputForm, AdminUserInterface loginUser)
            throws Exception, NotFoundException {

        // ���t�H�[���v�������X�V����
        this.reformManager.updateReformPlan(inputForm, (String)loginUser.getUserId());

        // ���t�H�[���E���[�_�[�`���[�g���X�V����
        if (PanaCommonConstant.HOUSING_KIND_CD_MANSION.equals(inputForm.getHousingKindCd()) ||
        		PanaCommonConstant.HOUSING_KIND_CD_HOUSE.equals(inputForm.getHousingKindCd())) {
        	this.reformManager.updReformChart(inputForm, 8);
        }

        // ������{���̃^�C���X�^���v�����X�V
        try{
            this.reformManager.updateEditTimestamp(inputForm.getSysHousingCd(),
            		inputForm.getSysReformCd(), (String)loginUser.getUserId());
        } catch (DataIntegrityViolationException e) {
            // ���̗�O�́A�o�^���O�ɕύX��ƂȂ郊�t�H�[����񂪍폜���ꂽ�ꍇ�ɔ�������B
            e.printStackTrace();
            throw new NotFoundException();
        }
    }



    /**
     * model �I�u�W�F�N�g���쐬���A���N�G�X�g�p�����[�^���i�[���� form �I�u�W�F�N�g���i�[����B<br/>
     * <br/>
     * @param request HTTP ���N�G�X�g�p�����[�^
     * @return �p�����[�^��ݒ肵�� Form ���i�[���� model �I�u�W�F�N�g�𕜋A����B
     */
    protected Map<String, Object> createModel(HttpServletRequest request) {

        Map<String, Object> model = new HashMap<>();

        // ���N�G�X�g�p�����[�^���擾���� Form �I�u�W�F�N�g���쐬����B
        ReformInfoForm requestForm = new ReformInfoForm(this.codeLookupManager);
        FormPopulator.populateFormBeanFromRequest(request, requestForm);
        model.put("inputForm", requestForm);

        PanaHousingFormFactory housingFactory = PanaHousingFormFactory.getInstance(request);
        PanaHousingSearchForm searchForm = housingFactory.createPanaHousingSearchForm(request);
        model.put("searchForm", searchForm);

        return model;

    }

    /**
     * ���t�H�[�������͉�ʊi�[<br>
     * <br/>
     * @param model Map ���N�G�X�g�p�����[�^���i�[�p
     * @param form ReformInfoForm ���t�H�[���v�������̓��͒l���i�[���� Form �I�u�W�F�N�g
     *
     */
    @SuppressWarnings("unchecked")
    private void pageFormat(Map<String, Object> model, ReformInfoForm form) {

        if ("update".equals(form.getCommand())) {

            // ���t�H�[���v���������擾
        	Map<String, Object> result = (Map<String, Object>) this.reformManager.searchReform(form.getSysReformCd());
            // ���t�H�[���ڍ׃��X�g
            model.put("dtlList", form.setDtlList((List<ReformDtl>) result.get("dtlList"), commonParameters, fileUtil));
            // ���t�H�[���摜��񃊃X�g
            model.put("imgList", form.setImgList((List<ReformImg>) result.get("imgList"), commonParameters, fileUtil));
        }
    }

}
