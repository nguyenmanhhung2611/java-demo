package jp.co.transcosmos.dm3.core.model.mypage.form;

import javax.servlet.http.HttpServletRequest;

import jp.co.transcosmos.dm3.core.constant.CommonParameters;
import jp.co.transcosmos.dm3.core.util.LengthValidationUtils;
import jp.co.transcosmos.dm3.form.FormPopulator;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;


/**
 * <pre>
 * �}�C�y�[�W���[�U�[�p Form �� Factory �N���X
 * getInstance() ���ASpring ����C���X�^���X���擾���Ďg�p���鎖�B
 *
 * �S����       �C����      �C�����e
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.02.20	�V�K�쐬
 * H.Mizuno		2015.03.31	�p�X���[�h���}�C���_�p�� Form ��ǉ�
 *
 * ���ӎ���
 * Factory �̃C���X�^���X�𒼐ڐ������Ȃ����B�@�K��getInstance() �Ŏ擾���鎖�B
 *
 * </pre>
 */
public class MypageUserFormFactory {

	/** Form �𐶐����� Factory �� Bean ID */
	protected static String FACTORY_BEAN_ID = "mypageUserFormFactory";

	/** ���ʃR�[�h�ϊ����� */
	protected CodeLookupManager codeLookupManager;
	
	/** ���ʃp�����[�^�I�u�W�F�N�g */
    protected CommonParameters commonParameters;

	/** �����O�X�o���f�[�V�����p���[�e�B���e�B */
    protected LengthValidationUtils lengthUtils;


    
    /**
     * ���ʃR�[�h�ϊ�������ݒ肷��B<br/>
     * <br/>
     * @param codeLookupManager ���ʃR�[�h�ϊ�����
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
	 * MyPageUserFormFactory �̃C���X�^���X���擾����B<br/>
	 * Spring �̃R���e�L�X�g����A myPageUserFormFactory �Œ�`���ꂽ MyPageUserFormFactory ��
	 * �C���X�^���X���擾����B<br/>
	 * �擾�����C���X�^���X�́AMyPageUserFormFactory ���p�������g���N���X�����A�����ꍇ������B<br/>
	 * <br/>
	 * @param request HTTP ���N�G�X�g
	 * @return MyPageUserFormFactory�A�܂��͌p�����Ċg�������N���X�̃C���X�^���X
	 */
	public static MypageUserFormFactory getInstance(HttpServletRequest request){
		WebApplicationContext springContext = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
		return (MypageUserFormFactory)springContext.getBean(MypageUserFormFactory.FACTORY_BEAN_ID);
	}



	/**
	 * �}�C�y�[�W����̌������ʁA����ь����������i�[������ MypageUserSearchForm �̃C���X�^���X�𐶐�����B<br/>
	 * <br/>
	 * @return ��� MypageUserSearchForm �C���X�^���X 
	 */
	public MypageUserSearchForm createMypageUserSearchForm() {
		return new MypageUserSearchForm(this.lengthUtils);
	}



	/**
	 * �}�C�y�[�W����̌������ʁA����ь����������i�[���� MypageUserSearchForm �̃C���X�^���X�𐶐�����B<br/>
	 * MypageUserSearchForm �ɂ́A���N�G�X�g�p�����[�^�ɊY������l��ݒ肷��B<br/>
	 * <br/>
	 * @param request HTTP ���N�G�X�g
	 * @return ���N�G�X�g�p�����[�^��ݒ肵�� MypageUserSearchForm �C���X�^���X 
	 */
	public MypageUserSearchForm createMypageUserSearchForm(HttpServletRequest request) {
		MypageUserSearchForm form = createMypageUserSearchForm();
		FormPopulator.populateFormBeanFromRequest(request, form);	
		return form; 
	}



	/**
	 * CSV �o�͗p�̃}�C�y�[�W����̌������ʁA����ь����������i�[������ MypageUserSearchCsvForm 
	 * �̃C���X�^���X�𐶐�����B<br/>
	 * <br/>
	 * @return ��� MypageUserSearchCsvForm �C���X�^���X 
	 */
	public MypageUserSearchCsvForm createMypageUserSearchCsvForm() {
		return new MypageUserSearchCsvForm(this.lengthUtils);
	}
	
	

	/**
	 * CSV �o�͗p�̃}�C�y�[�W����̌������ʁA����ь����������i�[���� MypageUserSearchCsvForm 
	 * �̃C���X�^���X�𐶐�����B<br/>
	 * MypageUserSearchCsvForm �ɂ́A���N�G�X�g�p�����[�^�ɊY������l��ݒ肷��B<br/>
	 * <br/>
	 * @param request HTTP ���N�G�X�g
	 * @return ���N�G�X�g�p�����[�^��ݒ肵�� MypageUserSearchCsvForm �C���X�^���X 
	 */
	public MypageUserSearchCsvForm createMypageUserSearchCsvForm(HttpServletRequest request) {
		MypageUserSearchCsvForm form = createMypageUserSearchCsvForm();
		FormPopulator.populateFormBeanFromRequest(request, form);	
		return form; 
	}
	
	

	/**
	 * �}�C�y�[�W����X�V���̓��͒l���i�[������ MypageUserForm �̃C���X�^���X�𐶐�����B<br/>
	 * <br/>
	 * @return ��� MypageUserForm �̃C���X�^���X
	 */
	public MypageUserForm createMypageUserForm() {
		return new MypageUserForm(this.lengthUtils, this.commonParameters, this.codeLookupManager);
	}



	/**
	 * �}�C�y�[�W����X�V���̓��͒l���i�[���� MypageUserForm �̃C���X�^���X�𐶐�����B<br/>
	 * <br/>
	 * @param request HTTP ���N�G�X�g
	 * @return ���N�G�X�g�p�����[�^��ݒ肵�� MypageUserForm �̃C���X�^���X
	 */
	public MypageUserForm createMypageUserForm(HttpServletRequest request) {
		MypageUserForm form = createMypageUserForm();
		FormPopulator.populateFormBeanFromRequest(request, form);	
		return form; 
	}


	/**
	 * �p�X���[�h���}�C���_�o�^���̓��͒l���i�[������ RemindForm �̃C���X�^���X�𐶐�����B<br/>
	 * <br/>
	 * @return ��� RemindForm �̃C���X�^���X
	 */
	public RemindForm createRemindForm() {
		return new RemindForm();
	}

	
	
	/**
	 * �p�X���[�h���}�C���_�o�^���̓��͒l���i�[���� RemindForm �̃C���X�^���X�𐶐�����B<br/>
	 * <br/>
	 * @param request HTTP ���N�G�X�g
	 * @return ���N�G�X�g�p�����[�^��ݒ肵�� RemindForm �̃C���X�^���X
	 */
	public RemindForm createRemindForm(HttpServletRequest request) {
		RemindForm form = createRemindForm();
		FormPopulator.populateFormBeanFromRequest(request, form);	
		return form; 
	}

	
	
	/**
	 * �p�X���[�h�ύX���̓��͒l���i�[������ PwdChangeForm �̃C���X�^���X�𐶐�����B<br/>
	 * <br/>
	 * @return ��� PwdChangeForm �̃C���X�^���X
	 */
	public PwdChangeForm createPwdChangeForm() {
		return new PwdChangeForm();
	}

	
	
	/**
	 * �p�X���[�h�ύX�����̓��͒l���i�[���� PwdChangeForm �̃C���X�^���X�𐶐�����B<br/>
	 * <br/>
	 * @param request HTTP ���N�G�X�g
	 * @return ���N�G�X�g�p�����[�^��ݒ肵�� PwdChangeForm �̃C���X�^���X
	 */
	public PwdChangeForm createPwdChangeForm(HttpServletRequest request) {
		PwdChangeForm form = createPwdChangeForm();
		FormPopulator.populateFormBeanFromRequest(request, form);	
		return form; 
	}

}
