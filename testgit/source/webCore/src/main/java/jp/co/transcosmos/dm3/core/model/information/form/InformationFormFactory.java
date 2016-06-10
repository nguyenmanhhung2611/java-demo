package jp.co.transcosmos.dm3.core.model.information.form;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import jp.co.transcosmos.dm3.core.util.LengthValidationUtils;
import jp.co.transcosmos.dm3.form.FormPopulator;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;

/**
 * <pre>
 * ���m�点�����E�ꗗ�p Form �� Factory �N���X
 * getInstance() ���ASpring ����C���X�^���X���擾���Ďg�p���鎖�B
 *
 * �S����       �C����      �C�����e
 * ------------ ----------- -----------------------------------------------------
 * I.Shu		2015.02.05	�V�K�쐬
 *
 * ���ӎ���
 * Factory �̃C���X�^���X�𒼐ڐ������Ȃ����B�@�K��getInstance() �Ŏ擾���鎖�B
 *
 * </pre>
 */
public class InformationFormFactory {

	/** Form �𐶐����� Factory �� Bean ID */
	protected static String FACTORY_BEAN_ID = "informationFormFactory";
	
	/** ���ʃR�[�h�ϊ����� */
	protected CodeLookupManager codeLookupManager;
	
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
	 * �����O�X�o���f�[�V�����p���[�e�B���e�B��ݒ肷��B<br/>
	 * <br/>
	 * @param lengthUtils �����O�X�o���f�[�V�����p���[�e�B���e�B
	 */
	public void setLengthUtils(LengthValidationUtils lengthUtils) {
		this.lengthUtils = lengthUtils;
	}


	
	/**
	 * InformationFormFactory �̃C���X�^���X���擾����B<br/>
	 * Spring �̃R���e�L�X�g����A InformationFormFactory �Œ�`���ꂽ InformationFormFactory ��
	 * �C���X�^���X���擾����B<br/>
	 * �擾�����C���X�^���X�́AInformationFormFactory ���p�������g���N���X�����A�����ꍇ������B<br/>
	 * <br/>
	 * @param request HTTP ���N�G�X�g
	 * @return AdminUserFormFactory�A�܂��͌p�����Ċg�������N���X�̃C���X�^���X
	 */
	public static InformationFormFactory getInstance(HttpServletRequest request) {

		WebApplicationContext springContext = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
		return (InformationFormFactory)springContext.getBean(InformationFormFactory.FACTORY_BEAN_ID);
	}
	
	

	/**
	 * ���m�点�̌������ʁA����ь����������i�[������ AdminUserSearchForm �̃C���X�^���X�𐶐�����B<br/>
	 * <br/>
	 * @return ��� AdminUserSearchForm �C���X�^���X 
	 */
	public InformationSearchForm createInformationSearchForm(){
		return new InformationSearchForm(this.lengthUtils);
	}


	
	/**
	 * ���m�点�̌������ʁA����ь����������i�[���� InformationSearchForm �̃C���X�^���X�𐶐�����B<br/>
	 * InformationSearchForm �ɂ́A���N�G�X�g�p�����[�^�ɊY������l��ݒ肷��B<br/>
	 * <br/>
	 * @param request HTTP ���N�G�X�g
	 * @return ���N�G�X�g�p�����[�^��ݒ肵�� InformationSearchForm �C���X�^���X 
	 */
	public InformationSearchForm createInformationSearchForm(HttpServletRequest request){
		InformationSearchForm form = createInformationSearchForm();
		FormPopulator.populateFormBeanFromRequest(request, form);	
		return form; 
	}

	
	
	/**
	 * ���m�点�X�V���̓��͒l���i�[������ InformationForm �̃C���X�^���X�𐶐�����B<br/>
	 * <br/>
	 * @return ��� InformationForm �̃C���X�^���X
	 */
	public InformationForm createInformationForm(){
		return new InformationForm(this.lengthUtils, this.codeLookupManager);
	}

	
	
	/**
	 * ���m�点�X�V���̓��͒l���i�[������ InformationForm �̃C���X�^���X�𐶐�����B<br/>
	 * <br/>
	 * @param request HTTP ���N�G�X�g
	 * @return ���N�G�X�g�p�����[�^��ݒ肵�� InformationForm �̃C���X�^���X
	 */
	public InformationForm createInformationForm(HttpServletRequest request){
		InformationForm form = createInformationForm();
		FormPopulator.populateFormBeanFromRequest(request, form);
		return form;
	}
	
}
