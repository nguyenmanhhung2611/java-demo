package jp.co.transcosmos.dm3.corePana.model.reform.form;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

import jp.co.transcosmos.dm3.corePana.constant.PanaCommonConstant;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonParameters;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingFormFactory;
import jp.co.transcosmos.dm3.form.FormPopulator;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * �Ǘ����[�U�[�����e�i���X�p Form �� Factory �N���X.
 * <p>
 * getInstance() ���ASpring ����C���X�^���X���擾���Ďg�p���鎖�B
 * <p>
 * <pre>
 * �S����       �C����      �C�����e
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.01.28	�V�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 * Factory �̃C���X�^���X�𒼐ڐ������Ȃ����B�@�K��getInstance() �Ŏ擾���鎖�B
 *
 */
public class ReformFormFactory {

    /** Form �𐶐����� Factory �� Bean ID */
    protected static String FACTORY_BEAN_ID = "reformFormFactory";

    /** ���ʃR�[�h�ϊ����� */
    protected CodeLookupManager codeLookupManager;

    /**
     * ���ʃR�[�h�ϊ��I�u�W�F�N�g��ݒ肷��B<br/>
     * <br/>
     * @param codeLookupManager
     */
    public void setCodeLookupManager(CodeLookupManager codeLookupManager) {
        this.codeLookupManager = codeLookupManager;
    }

	/** ���ʃp�����[�^�I�u�W�F�N�g */
	private PanaCommonParameters commonParameters;

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
     * AdminUserFormFactory �̃C���X�^���X���擾����B<br/>
     * Spring �̃R���e�L�X�g����A adminUserFormFactory �Œ�`���ꂽ AdminUserFormFactory ��
     * �C���X�^���X���擾����B<br/>
     * �擾�����C���X�^���X�́AadminUserFormFactory ���p�������g���N���X�����A�����ꍇ������B<br/>
     * <br/>
     * @param request HTTP ���N�G�X�g
     * @return AdminUserFormFactory�A�܂��͌p�����Ċg�������N���X�̃C���X�^���X
     */
    public static ReformFormFactory getInstance(HttpServletRequest request) {
        WebApplicationContext springContext = WebApplicationContextUtils.getWebApplicationContext(request
                .getServletContext());
        return (ReformFormFactory) springContext.getBean(ReformFormFactory.FACTORY_BEAN_ID);
    }

    /**
     * �Ǘ����[�U�[�̌������ʁA����ь����������i�[������ AdminUserSearchForm �̃C���X�^���X�𐶐�����B<br/>
     * <br/>
     * @return ��� AdminUserSearchForm �C���X�^���X
     */
    public ReformImgForm createRefromImgForm() {
        return new ReformImgForm(this.codeLookupManager);
    }

    public ReformImgForm createRefromImgForm(HttpServletRequest request) {
        ReformImgForm form = createRefromImgForm();
        FormPopulator.populateFormBeanFromRequest(request, form);
        return form;
    }

	/**
	 * �摜���̌������ʁA����ь����������i�[����t�H�[���̃C���X�^���X�z��𐶐�����B<br/>
	 * forms �ɂ́A���N�G�X�g�p�����[�^�ɊY������l��ݒ肷��B<br/>
	 * <br/>
	 * @param request HTTP ���N�G�X�g
	 * @return ���N�G�X�g�p�����[�^��ݒ肵���t�H�[���C���X�^���X�z��
	 */
	public Object[] createRefromImgForms(HttpServletRequest request) {
		Object[] forms = new Object[2];
		forms[0] = createRefromImgForm();
		PanaHousingFormFactory housingFactory = PanaHousingFormFactory.getInstance(request);
		forms[1] = housingFactory.createPanaHousingSearchForm();
		FormPopulator.populateFormBeanFromRequest(request, forms, PanaCommonConstant.maxFileSize, new File(commonParameters.getUploadWorkPath()));
		return forms;
	}

    /**
     * �Ǘ����[�U�[�̌������ʁA����ь����������i�[������ AdminUserSearchForm �̃C���X�^���X�𐶐�����B<br/>
     * <br/>
     * @return ��� AdminUserSearchForm �C���X�^���X
     */
    public ReformDtlForm createReformDtlForm() {
        return new ReformDtlForm(this.codeLookupManager);
    }

    public ReformDtlForm createReformDtlForm(HttpServletRequest request) {
        ReformDtlForm form = createReformDtlForm();
        FormPopulator.populateFormBeanFromRequest(request, form);
        return form;
    }

	/**
	 * �摜���̌������ʁA����ь����������i�[����t�H�[���̃C���X�^���X�z��𐶐�����B<br/>
	 * forms �ɂ́A���N�G�X�g�p�����[�^�ɊY������l��ݒ肷��B<br/>
	 * <br/>
	 * @param request HTTP ���N�G�X�g
	 * @return ���N�G�X�g�p�����[�^��ݒ肵���t�H�[���C���X�^���X�z��
	 */
	public Object[] createReformDtlForms(HttpServletRequest request) {
		Object[] forms = new Object[2];
		forms[0] = createReformDtlForm();
		PanaHousingFormFactory housingFactory = PanaHousingFormFactory.getInstance(request);
		forms[1] = housingFactory.createPanaHousingSearchForm();
		FormPopulator.populateFormBeanFromRequest(request, forms, PanaCommonConstant.maxFileSize, new File(commonParameters.getUploadWorkPath()));
		return forms;
	}

    /**
     * �Ǘ����[�U�[�̌������ʁA����ь����������i�[������ ReformInfoForm �̃C���X�^���X�𐶐�����B<br/>
     * <br/>
     * @return ��� ReformInfoForm �C���X�^���X
     */
    public ReformInfoForm createReformInfoForm() {
        return new ReformInfoForm(this.codeLookupManager);
    }

    public ReformInfoForm createRefromInfoForm(HttpServletRequest request) {
    	ReformInfoForm form = createReformInfoForm();
        FormPopulator.populateFormBeanFromRequest(request, form);
        return form;
    }


	/**
	 * �摜���̌������ʁA����ь����������i�[����t�H�[���̃C���X�^���X�z��𐶐�����B<br/>
	 * forms �ɂ́A���N�G�X�g�p�����[�^�ɊY������l��ݒ肷��B<br/>
	 * <br/>
	 * @param request HTTP ���N�G�X�g
	 * @return ���N�G�X�g�p�����[�^��ݒ肵���t�H�[���C���X�^���X�z��
	 */
	public Object[] createRefromInfoFormAndSearchForm(HttpServletRequest request) {
		Object[] forms = new Object[2];
		forms[0] = createReformInfoForm();
		PanaHousingFormFactory housingFactory = PanaHousingFormFactory.getInstance(request);
		forms[1] = housingFactory.createPanaHousingSearchForm();
		FormPopulator.populateFormBeanFromRequest(request, forms, PanaCommonConstant.maxFileSize, new File(commonParameters.getUploadWorkPath()));
		return forms;
	}
}
