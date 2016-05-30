package jp.co.transcosmos.dm3.core.model.adminUser.form;

import javax.servlet.http.HttpServletRequest;

import jp.co.transcosmos.dm3.core.constant.CommonParameters;
import jp.co.transcosmos.dm3.core.util.LengthValidationUtils;
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
public class AdminUserFormFactory {

	/** Form �𐶐����� Factory �� Bean ID */
	protected static String FACTORY_BEAN_ID = "adminUserFormFactory";

	/** ���ʃR�[�h�ϊ����� */
	protected CodeLookupManager codeLookupManager;

	/** ���ʃp�����[�^�I�u�W�F�N�g */
    protected CommonParameters commonParameters;

	/** �����O�X�o���f�[�V�����p���[�e�B���e�B */
    protected LengthValidationUtils lengthUtils;


	
	/**
	 * ���ʃR�[�h�ϊ��I�u�W�F�N�g��ݒ肷��B<br/>
	 * <br/>
	 * @param codeLookupManager
	 */
	public void setCodeLookupManager(CodeLookupManager codeLookupManager) {
		this.codeLookupManager = codeLookupManager;
	}

	/**
	 * ���ʃp�����[�^�I�u�W�F�N�g��ݒ肷��B<br/>
	 * <br/>
	 * @param commonParameters ���ʃp�����[�^�I�u�W�F�N�g
	 */
	public void setCommonParameters(CommonParameters commonParameters) {
		this.commonParameters = commonParameters;
	}

	/**
	 * �����O�X�o���f�[�V�����p���[�e�B���e�B��ݒ肷��B<br/>
	 * <br/>
	 * @param lengthUtils �����O�X�o���f�[�V�����p���[�e�B���e�B
	 */
	public void setLengthUtils(LengthValidationUtils lengthUtils) {
		this.lengthUtils = lengthUtils;
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
	public static AdminUserFormFactory getInstance(HttpServletRequest request){
		WebApplicationContext springContext = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
		return (AdminUserFormFactory)springContext.getBean(AdminUserFormFactory.FACTORY_BEAN_ID);
	}



	/**
	 * �Ǘ����[�U�[�̌������ʁA����ь����������i�[������ AdminUserSearchForm �̃C���X�^���X�𐶐�����B<br/>
	 * <br/>
	 * @return ��� AdminUserSearchForm �C���X�^���X 
	 */
	public AdminUserSearchForm createUserSearchForm(){
		return new AdminUserSearchForm(this.lengthUtils);
	}



	/**
	 * �Ǘ����[�U�[�̌������ʁA����ь����������i�[���� AdminUserSearchForm �̃C���X�^���X�𐶐�����B<br/>
	 * AdminUserSearchForm �ɂ́A���N�G�X�g�p�����[�^�ɊY������l��ݒ肷��B<br/>
	 * <br/>
	 * @param request HTTP ���N�G�X�g
	 * @return ���N�G�X�g�p�����[�^��ݒ肵�� AdminUserSearchForm �C���X�^���X 
	 */
	public AdminUserSearchForm createUserSearchForm(HttpServletRequest request){
		AdminUserSearchForm form = createUserSearchForm();
		FormPopulator.populateFormBeanFromRequest(request, form);	
		return form; 
	}



	/**
	 * CSV �o�͗p�̊Ǘ����[�U�[�̌������ʁA����ь����������i�[������ AdminUserSearchCsvForm 
	 * �̃C���X�^���X�𐶐�����B<br/>
	 * <br/>
	 * @return ��� AdminUserSearchCsvForm �C���X�^���X 
	 */
	public AdminUserSearchCsvForm createUserSearchCsvForm(){
		return new AdminUserSearchCsvForm(this.lengthUtils);
	}



	/**
	 * CSV �o�͗p�̊Ǘ����[�U�[�̌������ʁA����ь����������i�[���� AdminUserSearchCsvForm 
	 * �̃C���X�^���X�𐶐�����B<br/>
	 * AdminUserSearchCsvForm �ɂ́A���N�G�X�g�p�����[�^�ɊY������l��ݒ肷��B<br/>
	 * <br/>
	 * @param request HTTP ���N�G�X�g
	 * @return ���N�G�X�g�p�����[�^��ݒ肵�� AdminUserSearchCsvForm �C���X�^���X 
	 */
	public AdminUserSearchCsvForm createUserSearchCsvForm(HttpServletRequest request){
		AdminUserSearchCsvForm form = createUserSearchCsvForm();
		FormPopulator.populateFormBeanFromRequest(request, form);	
		return form; 
	}



	/**
	 * �Ǘ����[�U�[�X�V���̓��͒l���i�[������ AdminUserForm �̃C���X�^���X�𐶐�����B<br/>
	 * <br/>
	 * @return ��� AdminUserForm �̃C���X�^���X
	 */
	public AdminUserForm createAdminUserForm(){
		return new AdminUserForm(this.lengthUtils, this.codeLookupManager, this.commonParameters);
	}



	/**
	 * �Ǘ����[�U�[�X�V���̓��͒l���i�[���� AdminUserForm �̃C���X�^���X�𐶐�����B<br/>
	 * <br/>
	 * @param request HTTP ���N�G�X�g
	 * @return ���N�G�X�g�p�����[�^��ݒ肵�� AdminUserForm �̃C���X�^���X
	 */
	public AdminUserForm createAdminUserForm(HttpServletRequest request){
		AdminUserForm form = createAdminUserForm();
		FormPopulator.populateFormBeanFromRequest(request, form);
		return form;
	}



	/**
	 * �Ǘ����[�U�[�p�X���[�h�ύX�̓��͒l���i�[������ PwdChangeForm �̃C���X�^���X���擾����B<br/>
	 * <br/>
	 * @return ��� PwdChangeForm �̃C���X�^���X
	 */
	public PwdChangeForm createPwdChangeForm(){
		return new PwdChangeForm(this.lengthUtils, this.commonParameters, this.codeLookupManager);
	}



	/**
	 * �Ǘ����[�U�[�p�X���[�h�ύX�̓��͒l���i�[���� PwdChangeForm �̃C���X�^���X�𐶐�����B<br/>
	 * <br/>
	 * @param request HTTP ���N�G�X�g
	 * @return ���N�G�X�g�p�����[�^��ݒ肵�� PwdChangeForm �̃C���X�^���X
	 */
	public PwdChangeForm createPwdChangeForm(HttpServletRequest request){
		PwdChangeForm form = createPwdChangeForm();
		FormPopulator.populateFormBeanFromRequest(request, form);
		return form;
	}

}
